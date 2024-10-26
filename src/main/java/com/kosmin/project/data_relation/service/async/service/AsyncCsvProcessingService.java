package com.kosmin.project.data_relation.service.async.service;

import static com.kosmin.project.data_relation.util.DataRelationUtil.fileType;
import static com.kosmin.project.data_relation.util.DataRelationUtil.parseTransactionDate;
import static com.kosmin.project.data_relation.util.DbModelBuilderUtil.buildCheckingDbModel;
import static com.kosmin.project.data_relation.util.DbModelBuilderUtil.buildCreditDbModel;

import com.kosmin.project.data_relation.exception.AsyncProcessingException;
import com.kosmin.project.data_relation.model.CsvModel;
import com.kosmin.project.data_relation.model.Type;
import com.kosmin.project.data_relation.service.database.operations.DbOperationsService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsyncCsvProcessingService {
  private final DbOperationsService dbOperationsService;

  public CompletableFuture<Void> handleCsvProcessing(MultipartFile file) {
    return CompletableFuture.runAsync(
        () -> {
          try (BufferedReader reader =
              new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            final CsvToBean<CsvModel> csvToBean =
                new CsvToBeanBuilder<CsvModel>(reader)
                    .withType(CsvModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            csvToBean
                .parse()
                .forEach(
                    csvModel ->
                        saveTableRow(
                            csvModel,
                            parseTransactionDate(csvModel.getTransactionDate()),
                            fileType(file)));
          } catch (Exception e) {
            if (e instanceof RuntimeException
                && e.getMessage()
                    .equalsIgnoreCase(
                        "Driver org.postgresql.Driver claims to not accept jdbcUrl, ${POSTGRESQL_URL}")) {
              throw new AsyncProcessingException(
                  "DB Connection Strings not setup correctly for " + file.getOriginalFilename());
            }
            throw new AsyncProcessingException(e.getMessage());
          }
          log.info("Completed Table Insertions for {}", file.getOriginalFilename());
        });
  }

  private void saveTableRow(CsvModel csvModel, Date formattedDate, Type type) {
    final boolean validInsertModel = formattedDate != null && type != null;
    if (validInsertModel) {
      switch (type) {
        case CHECKING ->
            dbOperationsService.insertCheckingInformation(
                buildCheckingDbModel(csvModel, formattedDate));
        case CREDIT ->
            dbOperationsService.insertCreditInformation(
                buildCreditDbModel(csvModel, formattedDate));
      }
    } else {
      log.error(
          "Invalid Insert Model for Model: {}, Date: {}, Type: {}", csvModel, formattedDate, type);
    }
  }
}
