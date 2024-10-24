package com.kosmin.project.data_relation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class DataRelationUtil {

  public static Optional<Date> parseTransactionDate(String dateString) {
    final String[] formats = {"MM/dd/yy", "MM/dd/yyyy"}; // Add more formats if necessary
    for (String format : formats) {
      try {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return Optional.of(sdf.parse(dateString));
      } catch (ParseException e) {
        // Continue trying other formats
      }
    }
    // Return an empty Optional if all formats fail
    return Optional.empty();
  }
}
