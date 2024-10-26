package com.kosmin.project.data_relation.service.repository.service;

import static com.kosmin.project.data_relation.util.DbModelBuilderUtil.buildCheckingDbModel;
import static com.kosmin.project.data_relation.util.DbModelBuilderUtil.buildCreditDbModel;

import com.kosmin.project.data_relation.model.CsvModel;
import com.kosmin.project.data_relation.model.Type;
import com.kosmin.project.data_relation.repository.CheckingAccountRepository;
import com.kosmin.project.data_relation.repository.CreditCardRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DbInsertionService {
  private final CheckingAccountRepository checkingAccountRepository;
  private final CreditCardRepository creditCardRepository;

  public void saveTableRow(CsvModel csvModel, Date formattedDate, Type type) {
    final boolean validInsertModel = formattedDate != null && type != null;
    if (validInsertModel) {
      switch (type) {
        case CHECKING ->
            checkingAccountRepository.save(buildCheckingDbModel(csvModel, formattedDate));
        case CREDIT -> creditCardRepository.save(buildCreditDbModel(csvModel, formattedDate));
      }
    } else {
      log.error(
          "Invalid Insert Model for Model: {}, Date: {}, Type: {}", csvModel, formattedDate, type);
    }
  }
}
