package com.kosmin.project.data_relation.service;

import static com.kosmin.project.data_relation.util.DataRelationUtil.isValidCsvFile;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.acceptedResponse;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.badRequestResponse;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.createdResponse;

import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.service.async.service.AsyncCsvProcessingService;
import com.kosmin.project.data_relation.service.database.operations.DbOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DataRelationService {

  private final AsyncCsvProcessingService asyncCsvProcessingService;
  private final DbOperationsService dbOperationsService;

  public ResponseEntity<Response> createTables() {
    dbOperationsService.createTables();
    return createdResponse("Tables(s) Created Successfully");
  }

  @SneakyThrows
  public ResponseEntity<Response> insertTableRecords(MultipartFile file) {
    if (isValidCsvFile(file)) {
      asyncCsvProcessingService.handleCsvProcessing(file);
      return acceptedResponse("CSV File Successfully received and processing");
    } else {
      return badRequestResponse(
          "Input must be a non empty csv file with filename including either "
              + "'credit' or 'checking' to indicate which table to insert");
    }
  }
}
