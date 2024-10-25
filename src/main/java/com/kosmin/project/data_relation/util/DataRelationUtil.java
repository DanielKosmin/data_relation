package com.kosmin.project.data_relation.util;

import com.kosmin.project.data_relation.model.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public class DataRelationUtil {

  public static boolean isValidCsvFile(MultipartFile file) {
    return Optional.ofNullable(file)
        .filter(f -> !f.isEmpty())
        .map(MultipartFile::getOriginalFilename)
        .filter(
            fileName -> {
              final boolean isFileCsv = fileName.endsWith(".csv");
              final boolean filenameContainsCredit =
                  fileName.toLowerCase().contains(Type.CREDIT.getValue().toLowerCase());
              final boolean filenameContainsChecking =
                  fileName.toLowerCase().contains(Type.CHECKING.getValue().toLowerCase());
              return isFileCsv && (filenameContainsCredit || filenameContainsChecking);
            })
        .isPresent();
  }

  public static Optional<Date> parseTransactionDate(String dateString) {
    final String[] formats = {"MM/dd/yy", "MM/dd/yyyy"};
    for (String format : formats) {
      try {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return Optional.of(sdf.parse(dateString));
      } catch (ParseException e) {
        // Continue trying other formats
      }
    }
    return Optional.empty();
  }
}
