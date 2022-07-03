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
public class Flight extends Record {
  public static final String ID_PROPERTY = "id";
  public static final String START_LOC_ID_PROPERTY = "startingPointLocationId";
  public static final String DEST_LOC_ID_PROPERTY = "destinationLocationId";
  public static final String PLANE_ID_PROPERTY = "planeId";
  public static final String AIRLINE_ID_PROPERTY = "airlineId";

  private int id;
  public int startingPointId;
  public int destinationId;
  public int planeId;
  public int airlineId;

  public Flight(Map map) {
    this.id = Integer.parseInt((String) map.get(Flight.ID_PROPERTY));
    this.startingPointId = Integer.parseInt((String) map.get(Flight.START_LOC_ID_PROPERTY));
    this.destinationId = Integer.parseInt((String) map.get(Flight.DEST_LOC_ID_PROPERTY));
    this.planeId = Integer.parseInt((String) map.get(Flight.PLANE_ID_PROPERTY));
    this.airlineId = Integer.parseInt((String) map.get(Flight.AIRLINE_ID_PROPERTY));
  }

  @Override
  public List<String> csvHeaders() {
    return Arrays.asList(
      Flight.ID_PROPERTY,
      Flight.START_LOC_ID_PROPERTY,
      Flight.DEST_LOC_ID_PROPERTY,
      Flight.PLANE_ID_PROPERTY,
      Flight.AIRLINE_ID_PROPERTY
    );
  }

  @Override
  public List<String> csvRows() {
    return Arrays.asList(
      Integer.toString(getId()),
      Integer.toString(getStartingPointId()),
      Integer.toString(getDestinationId()),
      Integer.toString(getPlaneId()),
      Integer.toString(getAirlineId())
    );
  }
}

