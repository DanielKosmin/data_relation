package com.kosmin.project.data_relation.model;

import com.kosmin.project.data_relation.validator.DateValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DateValidator
public class ForeignKeyMappingPayload {

  private String checkingStartDate;
  private String checkingEndDate;

  @NotBlank(message = "checkingTransactionType must not be empty")
  @Pattern(
      regexp = "^(?i)(credit|debit)$",
      message = "checkingTransactionType must be either 'credit' or 'debit'")
  private String checkingTransactionType;

  @NotBlank(message = "checkingTransactionDescription must not be empty")
  private String checkingTransactionDescription;

  @NotBlank(message = "creditTransactionType must not be empty")
  @Pattern(
      regexp = "^(?i)(sale|payment)$",
      message = "checkingTransactionType must be either 'sale' or 'payment'")
  private String creditTransactionType;
}
