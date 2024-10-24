package com.kosmin.project.data_relation.service.asyncService;

import com.kosmin.project.data_relation.model.CheckingAccountModel;
import com.kosmin.project.data_relation.model.CheckingRecord;
import com.kosmin.project.data_relation.model.CreditCardModel;
import com.kosmin.project.data_relation.model.CreditRecord;
import com.kosmin.project.data_relation.model.Type;
import com.kosmin.project.data_relation.repository.CheckingAccountRepository;
import com.kosmin.project.data_relation.repository.CreditCardRepository;
import com.kosmin.project.data_relation.util.DataRelationUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
      switch (type) {
        case CHECKING -> {
          final CsvToBean<CheckingAccountModel> csvToBean =
              new CsvToBeanBuilder<CheckingAccountModel>(reader)
                  .withType(CheckingAccountModel.class)
                  .withIgnoreLeadingWhiteSpace(true)
                  .build();
          csvToBean
              .parse()
              .forEach(
                  model ->
                      DataRelationUtil.parseTransactionDate(model.getTransactionDate())
                          .ifPresentOrElse(
                              date ->
                                  checkingAccountRepository.save(
                                      CheckingRecord.builder()
                                          .transactionDescription(model.getTransactionDescription())
                                          .transactionDate(date)
                                          .transactionType(model.getTransactionType())
                                          .transactionAmount(
                                              BigDecimal.valueOf(model.getTransactionAmount()))
                                          .balance(BigDecimal.valueOf(model.getBalance()))
                                          .build()),
                              () ->
                                  log.error(
                                      "Unable to parse transaction date for Date: {}",
                                      model.getTransactionDate())));
        }
        case CREDIT -> {
          final CsvToBean<CreditCardModel> csvToBean =
              new CsvToBeanBuilder<CreditCardModel>(reader)
                  .withType(CreditCardModel.class)
                  .withIgnoreLeadingWhiteSpace(true)
                  .build();
          csvToBean
              .parse()
              .forEach(
                  model ->
                      DataRelationUtil.parseTransactionDate(model.getTransactionDate())
                          .ifPresentOrElse(
                              date ->
                                  creditCardRepository.save(
                                      CreditRecord.builder()
                                          .transactionDate(date)
                                          .transactionDescription(model.getTransactionDescription())
                                          .transactionCategory(model.getTransactionCategory())
                                          .transactionType(model.getTransactionType())
                                          .transactionAmount(
                                              BigDecimal.valueOf(model.getTransactionAmount()))
                                          .build()),
                              () ->
                                  log.error(
                                      "Unable to parse transaction date for Date: {}",
                                      model.getTransactionDate())));
        }
      }
    }
    log.info("Completed Insertion(s) into {} Table", type);
  }
}
