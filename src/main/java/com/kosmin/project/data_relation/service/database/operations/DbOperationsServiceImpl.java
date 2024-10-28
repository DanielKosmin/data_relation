package com.kosmin.project.data_relation.service.database.operations;

import com.kosmin.project.data_relation.model.ForeignKeyMappingPayload;
import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.model.repository.CheckingModel;
import com.kosmin.project.data_relation.model.repository.CreditModel;
import com.kosmin.project.data_relation.repository.create.CreateTables;
import com.kosmin.project.data_relation.repository.delete.DeleteTableRows;
import com.kosmin.project.data_relation.repository.insert.InsertCheckingRecords;
import com.kosmin.project.data_relation.repository.insert.InsertCreditRecords;
import com.kosmin.project.data_relation.repository.update.UpdateForeignKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbOperationsServiceImpl implements DbOperationsService {

  private final CreateTables createTables;
  private final InsertCheckingRecords insertCheckingRecords;
  private final InsertCreditRecords insertCreditRecords;
  private final DeleteTableRows deleteTableRows;
  private final UpdateForeignKeys updateForeignKeys;

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

  @Override
  public void clearTablesRecords(
      boolean clearCreditTable, boolean clearCheckingTable, boolean isDropTablesRequest) {
    deleteTableRows.clearTableRows(clearCreditTable, clearCheckingTable, isDropTablesRequest);
  }

  @Override
  public Response updateForeignKeys(ForeignKeyMappingPayload payload) {
    return updateForeignKeys.updateForeignKeys(payload);
  }
}
