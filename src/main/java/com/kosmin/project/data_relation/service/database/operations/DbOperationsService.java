package com.kosmin.project.data_relation.service.database.operations;

import com.kosmin.project.data_relation.model.repository.CheckingModel;
import com.kosmin.project.data_relation.model.repository.CreditModel;

public interface DbOperationsService {

  void createTables();

  void insertCheckingInformation(CheckingModel checkingModel);

  void insertCreditInformation(CreditModel creditModel);
}
