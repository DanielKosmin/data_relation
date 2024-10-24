package com.kosmin.project.data_relation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checking_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckingDbModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bankingRecordId;

  @Column(nullable = false)
  private String transactionDescription;

  @Column(nullable = false)
  private Date transactionDate;

  @Column(nullable = false)
  private String transactionType;

  @Column(nullable = false)
  private BigDecimal transactionAmount;

  @Column(nullable = false)
  private BigDecimal balance;
}
