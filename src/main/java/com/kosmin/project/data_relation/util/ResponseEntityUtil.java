package com.kosmin.project.data_relation.util;

import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.model.Status;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

  public static ResponseEntity<Response> acceptedResponse(String message) {
    return ResponseEntity.accepted()
        .body(Response.builder().status(Status.SUCCESS.getValue()).message(message).build());
  }

  public static ResponseEntity<Response> badRequestResponse(String errorMessage) {
    return ResponseEntity.badRequest()
        .body(
            Response.builder().status(Status.FAILED.getValue()).errorMessage(errorMessage).build());
  }

  public static ResponseEntity<Response> internalServerErrorResponse(String errorMessage) {
    return ResponseEntity.internalServerError()
        .body(
            Response.builder().status(Status.FAILED.getValue()).errorMessage(errorMessage).build());
  }
}
