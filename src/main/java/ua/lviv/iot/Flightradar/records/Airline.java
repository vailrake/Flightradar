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
public class Airline {
  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";

  private int id;
  private String name;

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(Airline.ID_PROPERTY, Airline.NAME_PROPERTY).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(getId(), getName());
    }
  }

  public static Airline fromMap(Map map) {
    return new Airline(
      Integer.parseInt((String) map.get(Airline.ID_PROPERTY)),
      (String) map.get(Airline.NAME_PROPERTY)
    );
  }
}


