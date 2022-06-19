package ua.lviv.iot.flightradar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvReader {
  private final File file;

  public CsvReader(File file) {
    this.file = file;
  }

  public List<Map> read() {
    try {
      if (!file.isFile()) {
        throw new IOException("Error - path is not a file " + file.getAbsolutePath());
      }

      FileInputStream fis = new FileInputStream(file);
      InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
      BufferedReader reader = new BufferedReader(isr);
      Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);

      List<Map> listOfMaps = new ArrayList<>();
      for (CSVRecord record : records) {
        listOfMaps.add(record.toMap());
      }

      reader.close();
      return listOfMaps;
    } catch (IOException e) {
      return new ArrayList<Map>();
    }
  }
}

