package ua.lviv.iot.flightradar.dataServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.records.Flight;

@Repository
public class FlightDataService extends AbstractDataService<Flight> {
  @Override
  protected String resourceName() {
    return "flights";
  }

  @Override
  protected Flight recordFromMap(Map map) {
    return new Flight(map);
  }
}


