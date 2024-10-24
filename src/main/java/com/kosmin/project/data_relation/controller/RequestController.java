package com.kosmin.project.data_relation.controller;

import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.service.DataRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("table_queries/v1")
@RequiredArgsConstructor
public class RequestController {

  private final DataRelationService dataRelationService;

  @PutMapping("insert")
  public ResponseEntity<Response> insertTableRecords(@RequestParam("file") MultipartFile file) {
    return dataRelationService.insertTableRecords(file);
  }
}
