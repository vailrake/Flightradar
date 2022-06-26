package ua.lviv.iot.flightradar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

// TODO rename
public class CsvIO {
  private final String resourceName;

  public CsvIO(String resourceName) {
    this.resourceName = resourceName;
  }

  public List<Map> read(File file) {
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

  public boolean createCurrentMonthFile() throws IOException {
    return dayOfCurrentMonthPath().toFile().createNewFile();
  }

  public List<Path> currentMonthPaths() {
    List<Path> paths = new ArrayList<>();

    String year = Integer.toString(LocalDate.now().getYear());
    String month = Integer.toString(LocalDate.now().getMonthValue());
    int dayOfMonth = LocalDate.now().getDayOfMonth();

    for (int day = 1; day <= dayOfMonth; day++) {
      Path path = pathFromDate(year, month, day);

      if (Files.exists(path)) {
        paths.add(path);
      }
    }

    return paths;
  }

  public Path dayOfCurrentMonthPath() {
    String year = Integer.toString(LocalDate.now().getYear());
    String month = Integer.toString(LocalDate.now().getMonthValue());
    int dayOfMonth = LocalDate.now().getDayOfMonth();

    return pathFromDate(year, month, dayOfMonth);
  }

  private Path pathFromDate(String year, String month, int dayOfMonth) {
    return Paths.get("src/main/resources/files/" + resourceName + "-" + year + "-" + month + "-" + dayOfMonth + ".csv");
  }

}

