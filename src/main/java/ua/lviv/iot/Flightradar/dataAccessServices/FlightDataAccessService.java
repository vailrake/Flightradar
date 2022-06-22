package ua.lviv.iot.flightradar.dataAccessServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.flightradar.errors.RecordNotFoundException;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.records.*;
import ua.lviv.iot.flightradar.util.CsvReader;


@Repository
public class FlightDataAccessService {
  private final ResourceLoader resourceLoader;

  @Autowired
  public FlightDataAccessService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public List<Map> getAllFlightsData() {
    Resource resource = resourceLoader.getResource("flights.csv");

    File file = new File(resource.getFilename());
    CsvReader csvReader = new CsvReader(file);

    return csvReader.read();
  }


  public Map getFlightData(int id) throws RecordNotFoundException {
    for (Map map : getAllFlightsData()) {
      if (Integer.parseInt((String) map.get(Flight.ID_PROPERTY)) == id) {
        return map;
      }
    }

    throw new RecordNotFoundException();
  }

  public void createFlight(Flight flight) {
    Resource resource = resourceLoader.getResource("flights.csv");

    try {
      File file = new File(resource.getFilename());
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (file.createNewFile()) {
        flight.writeCsvHeader(out);
      }

      flight.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}


