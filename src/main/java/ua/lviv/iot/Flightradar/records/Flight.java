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
public class Flight {
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

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      Flight.ID_PROPERTY,
      Flight.START_LOC_ID_PROPERTY,
      Flight.DEST_LOC_ID_PROPERTY,
      Flight.PLANE_ID_PROPERTY,
      Flight.AIRLINE_ID_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(
          getId(),
          getStartingPointId(),
          getDestinationId(),
          getPlaneId(),
          getAirlineId()
      );
    }
  }

  public static Flight fromMap(Map map) {
    return new Flight(
      Integer.parseInt((String) map.get(Flight.ID_PROPERTY)),
      Integer.parseInt((String) map.get(Flight.START_LOC_ID_PROPERTY)),
      Integer.parseInt((String) map.get(Flight.DEST_LOC_ID_PROPERTY)),
      Integer.parseInt((String) map.get(Flight.PLANE_ID_PROPERTY)),
      Integer.parseInt((String) map.get(Flight.AIRLINE_ID_PROPERTY))
    );
  }
}

