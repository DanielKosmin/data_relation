package com.kosmin.project.data_relation.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckingAccountModel {

  @CsvBindByName(column = "Transaction Description")
  private String transactionDescription;

  @CsvBindByName(column = "Transaction Date")
  private String transactionDate;

  @CsvBindByName(column = "Transaction Type")
  private String transactionType;

  @CsvBindByName(column = "Transaction Amount")
  private Double transactionAmount;

  @CsvBindByName(column = "Balance")
  private Double balance;
}
