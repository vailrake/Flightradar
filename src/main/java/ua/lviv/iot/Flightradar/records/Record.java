package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public abstract class Record {
  public abstract List<String> csvHeaders();

  public abstract List<String> csvRows();

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(csvHeaders().stream().toArray(String[]::new)).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(csvRows());
    }
  }
}

