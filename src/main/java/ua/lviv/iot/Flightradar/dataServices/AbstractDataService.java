package ua.lviv.iot.flightradar.dataServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.records.Record;

public abstract class AbstractDataService<T extends Record> {
  protected abstract String resourceName();

  protected abstract T recordFromMap(Map map);

  public void write(T record) {
    try {
      boolean newFileCreated = createCurrentMonthDayFile();

      File file = dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        record.writeCsvHeader(out);
      }

      record.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public void writeAll(List<T> records) {
    try {
      File file = dayOfCurrentMonthPath().toFile();
      file.delete();

      if (records.isEmpty()) {
        return;
      }

      createCurrentMonthDayFile();
      file = dayOfCurrentMonthPath().toFile();

      try (FileWriter out = new FileWriter(file.getAbsolutePath(), true)) {
        records.get(0).writeCsvHeader(out);
        try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
          for (Record record : records) {
            printer.printRecord(record.csvRows());
          }
        }
      }
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<T> currentMonthRecords() {
    List<T> records = new ArrayList<>();

    for (Path path : currentMonthPaths()) {
      for (Map map : read(path.toFile())) {
        records.add(recordFromMap(map));
      }
    }

    return records;
  }

  public Path dayOfCurrentMonthPath() {
    String year = Integer.toString(LocalDate.now().getYear());
    String month = Integer.toString(LocalDate.now().getMonthValue());
    int dayOfMonth = LocalDate.now().getDayOfMonth();

    return pathFromDate(year, month, dayOfMonth);
  }

  private List<Map> read(File file) {
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

  private boolean createCurrentMonthDayFile() throws IOException {
    return dayOfCurrentMonthPath().toFile().createNewFile();
  }

  private List<Path> currentMonthPaths() {
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

  private Path pathFromDate(String year, String month, int dayOfMonth) {
    return Paths.get("src/main/resources/files/" + resourceName() + "-" + year + "-" + month + "-" + dayOfMonth + ".csv");
  }
}




