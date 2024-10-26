package com.kosmin.project.data_relation.service.database.operations;

import com.kosmin.project.data_relation.model.repository.CheckingModel;
import com.kosmin.project.data_relation.model.repository.CreditModel;
import com.kosmin.project.data_relation.repository.create.CreateTables;
import com.kosmin.project.data_relation.repository.insert.InsertCheckingRecords;
import com.kosmin.project.data_relation.repository.insert.InsertCreditRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbOperationsServiceImpl implements DbOperationsService {

  private final CreateTables createTables;
  private final InsertCheckingRecords insertCheckingRecords;
  private final InsertCreditRecords insertCreditRecords;

  @Override
  public void createTables() {
    createTables.createTables();
  }

  @Override
  public void insertCheckingInformation(CheckingModel checkingModel) {
    insertCheckingRecords.insertCheckingRecords(checkingModel);
  }

  @Override
  public void insertCreditInformation(CreditModel creditModel) {
    insertCreditRecords.insertCreditRecords(creditModel);
  }
}