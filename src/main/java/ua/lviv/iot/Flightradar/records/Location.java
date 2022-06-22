package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Location {
  public static final String ID_PROPERTY = "id";
  public static final String LATITUDE_PROPERTY = "latitude";
  public static final String LONGITUDE_PROPERTY = "longitude";

  private final int id;
  private final double latitude;
  private final double longitude;

  public Location(int id, double latitude, double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public int getId() {
    return id;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      Location.ID_PROPERTY,
      Location.LATITUDE_PROPERTY,
      Location.LONGITUDE_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(getId(), getLatitude(), getLongitude());
    }
  }
}
