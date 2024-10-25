package com.kosmin.project.data_relation.exception;

public class DateParserException extends RuntimeException {
  public DateParserException(String message, String validFormats, String dateString) {
    super(
        String.format(
            "%s: Valid Input Formats: %s; Input Date: %s", message, validFormats, dateString));
  }
}
