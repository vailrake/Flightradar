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
public class Plane {
  public static final String ID_PROPERTY = "id";
  public static final String AIRLINE_ID_PROPERTY = "airlineId";
  public static final String INFORMATION_ID_PROPERTY = "registrationInformationId";
  public static final String TELEMETRY_ID_PROPERTY = "telemetryRecordId";

  private int id;
  public int registrationInformationId;
  public int telemetryRecordId;
  public int airlineId;

  public int getId() {
    return id;
  }

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      Plane.ID_PROPERTY,
      Plane.AIRLINE_ID_PROPERTY,
      Plane.INFORMATION_ID_PROPERTY,
      Plane.TELEMETRY_ID_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(
          getId(),
          getAirlineId(),
          getRegistrationInformationId(),
          getTelemetryRecordId()
      );
    }
  }

  public static Plane fromMap(Map map) {
    return new Plane(
      Integer.parseInt((String) map.get(Plane.ID_PROPERTY)),
      Integer.parseInt((String) map.get(Plane.AIRLINE_ID_PROPERTY)),
      Integer.parseInt((String) map.get(Plane.INFORMATION_ID_PROPERTY)),
      Integer.parseInt((String) map.get(Plane.TELEMETRY_ID_PROPERTY))
    );
  }
}
