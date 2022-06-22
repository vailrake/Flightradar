package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Airline {
  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";

  private final int id;
  private final String name;

  @SuppressWarnings("checkstyle:HiddenField")
  public Airline(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(Airline.ID_PROPERTY, Airline.NAME_PROPERTY).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(getId(), getName());
    }
  }
}


