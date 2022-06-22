package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class TelemetryRecord {
  public static final String ID_PROPERTY = "id";
  public static final String LOCATION_ID_PROPERTY = "locationId";
  public static final String SPEED_PROPERTY = "currentSpeed";
  public static final String DISTANCE_PROPERTY = "totalDistanceTraveled";

  private final int id;
  private final int currentSpeed;
  private final int totalDistanceTraveled;
  public final Location location;


  public TelemetryRecord(int id, int currentSpeed, int totalDistanceTraveled, Location location) {
    this.id = id;
    this.currentSpeed = currentSpeed;
    this.totalDistanceTraveled = totalDistanceTraveled;
    this.location = location;
  }

  public int getId() {
    return id;
  }

  public int getCurrentSpeed() {
    return currentSpeed;
  }

  public int getTotalDistanceTraveled() {
    return totalDistanceTraveled;
  }

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      TelemetryRecord.ID_PROPERTY,
      TelemetryRecord.SPEED_PROPERTY,
      TelemetryRecord.DISTANCE_PROPERTY,
      TelemetryRecord.LOCATION_ID_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(
        getId(),
        getCurrentSpeed(),
        getTotalDistanceTraveled(),
        location.getId()
      );
    };
  }
}
