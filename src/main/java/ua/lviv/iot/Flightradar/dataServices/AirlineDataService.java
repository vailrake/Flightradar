package ua.lviv.iot.flightradar.dataServices;

import java.util.Map;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.flightradar.records.Airline;

@Repository
public class AirlineDataService extends AbstractDataService<Airline> {
  @Override
  protected String resourceName() {
    return "airlines";
  }

  @Override
  protected Airline recordFromMap(Map map) {
    return new Airline(map);
  }
}


