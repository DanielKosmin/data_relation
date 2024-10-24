package com.kosmin.project.data_relation.service;

import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.model.Status;
import com.kosmin.project.data_relation.model.Type;
import com.kosmin.project.data_relation.service.asyncService.AsyncCsvProcessingService;
import java.util.Optional;
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
    final boolean validFile =
        Optional.ofNullable(file)
            .filter(f -> !f.isEmpty())
            .map(MultipartFile::getOriginalFilename)
            .filter(
                fileName ->
                    fileName.endsWith(".csv")
                        && (fileName.toLowerCase().contains(Type.CREDIT.getValue().toLowerCase())
                            || fileName
                                .toLowerCase()
                                .contains(Type.CHECKING.getValue().toLowerCase())))
            .isPresent();
    if (validFile) {
      asyncCsvProcessingService.handleCsvProcessing(
          file,
          (file.getOriginalFilename().toLowerCase().contains(Type.CREDIT.getValue().toLowerCase())
              ? Type.CREDIT
              : Type.CHECKING));
      return ResponseEntity.accepted()
          .body(
              Response.builder()
                  .status(Status.SUCCESS.getValue())
                  .message("CSV File Successfully received and processing")
                  .build());
    } else {
      return ResponseEntity.badRequest()
          .body(
              Response.builder()
                  .status(Status.FAILED.getValue())
                  .errorMessage(
                      "Input must be a non empty csv file with filename including either "
                          + "'credit' or 'checking' to indicate which table to insert")
                  .build());
    }
  }
}
