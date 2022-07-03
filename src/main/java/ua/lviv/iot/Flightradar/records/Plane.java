package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
public class Plane extends Record {
  public static final String ID_PROPERTY = "id";
  public static final String AIRLINE_ID_PROPERTY = "airlineId";
  public static final String INFORMATION_ID_PROPERTY = "registrationInformationId";
  public static final String TELEMETRY_ID_PROPERTY = "telemetryRecordId";

  private int id;
  public int airlineId;
  public int registrationInformationId;
  public int telemetryRecordId;

  public Plane(Map map) {
    this.id = Integer.parseInt((String) map.get(Plane.ID_PROPERTY));
    this.airlineId = Integer.parseInt((String) map.get(Plane.AIRLINE_ID_PROPERTY));
    this.registrationInformationId = Integer.parseInt((String) map.get(Plane.INFORMATION_ID_PROPERTY));
    this.telemetryRecordId = Integer.parseInt((String) map.get(Plane.TELEMETRY_ID_PROPERTY));
  }

  @Override
  public List<String> csvHeaders() {
    return Arrays.asList(
      Plane.ID_PROPERTY,
      Plane.AIRLINE_ID_PROPERTY,
      Plane.INFORMATION_ID_PROPERTY,
      Plane.TELEMETRY_ID_PROPERTY
    );
  }

  @Override
  public List<String> csvRows() {
    return Arrays.asList(
      Integer.toString(getId()),
      Integer.toString(getAirlineId()),
      Integer.toString(getRegistrationInformationId()),
      Integer.toString(getTelemetryRecordId())
    );
  }
}
