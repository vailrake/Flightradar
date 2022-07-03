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
public class Airline extends Record {
  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";

  private int id;
  private String name;

  public Airline(Map map) {
    this.id = Integer.parseInt((String) map.get(Airline.ID_PROPERTY));
    this.name = (String) map.get(Airline.NAME_PROPERTY);
  }

  @Override
  public List<String> csvHeaders() {
    return Arrays.asList(Airline.ID_PROPERTY, Airline.NAME_PROPERTY);
  }

  @Override
  public List<String> csvRows() {
    List<String> rows = Arrays.asList(
        Integer.toString(getId()),
        getName()
    );
    return rows;
  }
}


