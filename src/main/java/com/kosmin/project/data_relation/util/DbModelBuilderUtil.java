package com.kosmin.project.data_relation.util;

import com.kosmin.project.data_relation.model.CheckingDbModel;
import com.kosmin.project.data_relation.model.CreditDbModel;
import com.kosmin.project.data_relation.model.CsvModel;
import java.math.BigDecimal;
import java.util.Date;

public class DbModelBuilderUtil {

  public static CheckingDbModel buildCheckingDbModel(CsvModel csvModel, Date date) {
    return CheckingDbModel.builder()
        .transactionDescription(csvModel.getCheckingTransactionDescription())
        .transactionDate(date)
        .transactionType(csvModel.getCheckingTransactionType())
        .transactionAmount(BigDecimal.valueOf(csvModel.getCheckingTransactionAmount()))
        .balance(BigDecimal.valueOf(csvModel.getCheckingBalance()))
        .build();
  }

  public static CreditDbModel buildCreditDbModel(CsvModel csvModel, Date date) {
    return CreditDbModel.builder()
        .transactionDate(date)
        .transactionDescription(csvModel.getCreditTransactionDescription())
        .transactionCategory(csvModel.getCreditTransactionCategory())
        .transactionType(csvModel.getCreditTransactionType())
        .transactionAmount(BigDecimal.valueOf(csvModel.getCreditTransactionAmount()))
        .build();
  }
}
