package com.kosmin.project.data_relation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name = "credit_records")
@Data
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
  private double transactionAmount;
}
