package com.kosmin.project.data_relation.service;

import static com.kosmin.project.data_relation.util.DataRelationUtil.isValidCsvFile;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.acceptedResponse;
import static com.kosmin.project.data_relation.util.ResponseEntityUtil.badRequestResponse;

import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.service.asyncService.AsyncCsvProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DataRelationService {

  private final AsyncCsvProcessingService asyncCsvProcessingService;

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
