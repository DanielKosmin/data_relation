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
public class CreditCardModel {

  @CsvBindByName(column = "Transaction Date")
  private String transactionDate;

  @CsvBindByName(column = "Description")
  private String transactionDescription;

  @CsvBindByName(column = "Category")
  private String transactionCategory;

  @CsvBindByName(column = "Type")
  private String transactionType;

  @CsvBindByName(column = "Amount")
  private Double transactionAmount;
}
