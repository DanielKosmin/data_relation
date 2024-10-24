package com.kosmin.project.data_relation.service.asyncService;

import com.kosmin.project.data_relation.model.CheckingDbModel;
import com.kosmin.project.data_relation.model.CreditDbModel;
import com.kosmin.project.data_relation.model.CsvModel;
import com.kosmin.project.data_relation.model.Type;
import com.kosmin.project.data_relation.repository.CheckingAccountRepository;
import com.kosmin.project.data_relation.repository.CreditCardRepository;
import com.kosmin.project.data_relation.util.DataRelationUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsyncCsvProcessingService {

  private final CheckingAccountRepository checkingAccountRepository;
  private final CreditCardRepository creditCardRepository;

  @Async
  @SneakyThrows
  public void handleCsvProcessing(MultipartFile file, Type type) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      final CsvToBean<CsvModel> csvToBean =
          new CsvToBeanBuilder<CsvModel>(reader)
              .withType(CsvModel.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build();
      csvToBean
          .parse()
          .forEach(
              csvModel ->
                  saveTableRow(
                      csvModel,
                      DataRelationUtil.parseTransactionDate(csvModel.getTransactionDate()),
                      type));
    }
    log.info("Completed Insertion(s) into {} Table", type);
  }

  private void saveTableRow(CsvModel csvModel, Optional<Date> formattedDate, Type type) {
    formattedDate.ifPresentOrElse(
        date -> {
          if (Type.CHECKING.equals(type)) {
            checkingAccountRepository.save(buildCheckingDbModel(csvModel, date));
          } else {
            creditCardRepository.save(buildCreditDbModel(csvModel, date));
          }
        },
        () ->
            log.error(
                "Unable to parse transaction date for Type: {} Date: {}", type, formattedDate));
  }

  private CheckingDbModel buildCheckingDbModel(CsvModel csvModel, Date date) {
    return CheckingDbModel.builder()
        .transactionDescription(csvModel.getCheckingTransactionDescription())
        .transactionDate(date)
        .transactionType(csvModel.getCheckingTransactionType())
        .transactionAmount(BigDecimal.valueOf(csvModel.getCheckingTransactionAmount()))
        .balance(BigDecimal.valueOf(csvModel.getCheckingBalance()))
        .build();
  }

  private CreditDbModel buildCreditDbModel(CsvModel csvModel, Date date) {
    return CreditDbModel.builder()
        .transactionDate(date)
        .transactionDescription(csvModel.getCreditTransactionDescription())
        .transactionCategory(csvModel.getCreditTransactionCategory())
        .transactionType(csvModel.getCreditTransactionType())
        .transactionAmount(BigDecimal.valueOf(csvModel.getCreditTransactionAmount()))
        .build();
  }
}
