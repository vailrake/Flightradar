package ua.lviv.iot.flightradar.records;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TelemetryRecord extends Record {
  public static final String ID_PROPERTY = "id";
  public static final String LOCATION_ID_PROPERTY = "locationId";
  public static final String SPEED_PROPERTY = "currentSpeed";
  public static final String DISTANCE_PROPERTY = "totalDistanceTraveled";

  private int id;
  private int locationId;
  private int currentSpeed;
  private int totalDistanceTraveled;

  public TelemetryRecord(Map map) {
    this.id = Integer.parseInt((String) map.get(TelemetryRecord.ID_PROPERTY));
    this.locationId = Integer.parseInt((String) map.get(TelemetryRecord.LOCATION_ID_PROPERTY));
    this.currentSpeed = Integer.parseInt((String) map.get(TelemetryRecord.SPEED_PROPERTY));
    this.totalDistanceTraveled = Integer.parseInt((String) map.get(TelemetryRecord.DISTANCE_PROPERTY));
  }

  @Override
  public List<String> csvHeaders() {
    return Arrays.asList(
      TelemetryRecord.ID_PROPERTY,
      TelemetryRecord.LOCATION_ID_PROPERTY,
      TelemetryRecord.SPEED_PROPERTY,
      TelemetryRecord.DISTANCE_PROPERTY
    );
  }

  @Override
  public List<String> csvRows() {
    return Arrays.asList(
      Integer.toString(getId()),
      Integer.toString(getLocationId()),
      Integer.toString(getCurrentSpeed()),
      Integer.toString(getTotalDistanceTraveled())
    );
  }
}
