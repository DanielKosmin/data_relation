package com.kosmin.project.data_relation.exception;

public class InvalidQueryParamException extends RuntimeException {
  public InvalidQueryParamException(String message, Boolean credit, Boolean checking) {
    super(String.format("%s: Credit Value: %s; Checking Value: %s", message, credit, checking));
  }
}
