package com.kosmin.project.data_relation.repository;

import com.kosmin.project.data_relation.model.CreditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditRecord, Long> {}
