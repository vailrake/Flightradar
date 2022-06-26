package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TelemetryRecord {
  public static final String ID_PROPERTY = "id";
  public static final String LOCATION_ID_PROPERTY = "locationId";
  public static final String SPEED_PROPERTY = "currentSpeed";
  public static final String DISTANCE_PROPERTY = "totalDistanceTraveled";

  private int id;
  private int locationId;
  private int currentSpeed;
  private int totalDistanceTraveled;

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      TelemetryRecord.ID_PROPERTY,
      TelemetryRecord.LOCATION_ID_PROPERTY,
      TelemetryRecord.SPEED_PROPERTY,
      TelemetryRecord.DISTANCE_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(
          getId(),
          getLocationId(),
          getCurrentSpeed(),
          getTotalDistanceTraveled()
      );
    }
  }

  public static TelemetryRecord fromMap(Map map) {
    return new TelemetryRecord(
      Integer.parseInt((String) map.get(TelemetryRecord.ID_PROPERTY)),
      Integer.parseInt((String) map.get(TelemetryRecord.LOCATION_ID_PROPERTY)),
      Integer.parseInt((String) map.get(TelemetryRecord.SPEED_PROPERTY)),
      Integer.parseInt((String) map.get(TelemetryRecord.DISTANCE_PROPERTY))
    );
  }
}
