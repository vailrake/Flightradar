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
public class Location {
  public static final String ID_PROPERTY = "id";
  public static final String LATITUDE_PROPERTY = "latitude";
  public static final String LONGITUDE_PROPERTY = "longitude";

  private int id;
  private double latitude;
  private double longitude;

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

  public static Location fromMap(Map map) {
    return new Location(
      Integer.parseInt((String) map.get(Location.ID_PROPERTY)),
      Double.parseDouble((String) map.get(Location.LATITUDE_PROPERTY)),
      Double.parseDouble((String) map.get(Location.LONGITUDE_PROPERTY))
    );
  }
}
