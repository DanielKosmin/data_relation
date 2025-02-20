package com.kosmin.project.data_relation.service;

import static com.kosmin.project.data_relation.util.DataRelationUtil.isValidCsvFile;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.acceptedResponse;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.badRequestResponse;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.createdResponse;

import com.kosmin.project.data_relation.exception.InvalidQueryParamException;
import com.kosmin.project.data_relation.model.ForeignKeyMappingPayload;
import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.service.async.service.AsyncCsvProcessingService;
import com.kosmin.project.data_relation.service.database.operations.DbOperationsService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataRelationService {

  private final AsyncCsvProcessingService asyncCsvProcessingService;
  private final DbOperationsService dbOperationsService;

  public ResponseEntity<Response> createTables() {
    dbOperationsService.createTables();
    return createdResponse("Tables(s) Created Successfully");
  }

  public ResponseEntity<Response> insertTableRecords(MultipartFile file) {
    if (isValidCsvFile(file)) {
      asyncCsvProcessingService
          .handleCsvProcessing(file)
          .exceptionally(
              e -> {
                log.error(e.getCause().getMessage());
                return null;
              });
      return acceptedResponse("CSV File Successfully received and processing");
    } else {
      return badRequestResponse(
          "Input must be a non empty csv file with filename including either "
              + "'credit' or 'checking' to indicate which table to insert");
    }
  }

  public ResponseEntity<Response> deleteTableRecords(
      Boolean credit, Boolean checking, Boolean dropTables) {
    boolean clearCreditTable = Optional.ofNullable(credit).orElse(false);
    boolean clearCheckingTable = Optional.ofNullable(checking).orElse(false);
    boolean isDropTablesRequest = Optional.ofNullable(dropTables).orElse(false);
    if (clearCreditTable || clearCheckingTable) {
      dbOperationsService.clearTablesRecords(
          clearCreditTable, clearCheckingTable, isDropTablesRequest);
      return ResponseEntity.noContent().build();
    } else {
      throw new InvalidQueryParamException("Invalid Query Param Combo", credit, checking);
    }
  }

  public ResponseEntity<Response> updateTableRecords(ForeignKeyMappingPayload payload) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(dbOperationsService.updateForeignKeys(payload));
  }
}
