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
public class Location extends Record {
  public static final String ID_PROPERTY = "id";
  public static final String LATITUDE_PROPERTY = "latitude";
  public static final String LONGITUDE_PROPERTY = "longitude";

  private int id;
  private double latitude;
  private double longitude;

  public Location(Map map) {
    this.id = Integer.parseInt((String) map.get(Location.ID_PROPERTY));
    this.latitude = Double.parseDouble((String) map.get(Location.LATITUDE_PROPERTY));
    this.longitude = Double.parseDouble((String) map.get(Location.LONGITUDE_PROPERTY));
  }

  @Override
  public List<String> csvHeaders() {
    return Arrays.asList(
      Location.ID_PROPERTY,
      Location.LATITUDE_PROPERTY,
      Location.LONGITUDE_PROPERTY
    );
  }

  @Override
  public List<String> csvRows() {
    return Arrays.asList(
      Integer.toString(getId()),
      Double.toString(getLatitude()),
      Double.toString(getLongitude())
    );
  }
}
