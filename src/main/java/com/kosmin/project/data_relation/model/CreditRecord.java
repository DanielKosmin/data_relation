package com.kosmin.project.data_relation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long creditRecordId;

  @ManyToOne
  @JoinColumn(name = "banking_record_id")
  private CheckingRecord checkingRecord;

  @Column(nullable = false)
  private Date transactionDate;

  @Column(nullable = false)
  private String transactionDescription;

  @Column(nullable = false)
  private String transactionCategory;

  @Column(nullable = false)
  private String transactionType;

  @Column(nullable = false)
  private BigDecimal transactionAmount;
}
