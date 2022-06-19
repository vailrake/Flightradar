package ua.lviv.iot.flightradar.flight;

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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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
        CSVFormat.DEFAULT.withHeader(
          Flight.ID_PROPERTY,
          Flight.AIRLINE_ID_PROPERTY,
          Flight.START_LOC_ID_PROPERTY,
          Flight.DEST_LOC_ID_PROPERTY,
          Flight.PLANE_ID_PROPERTY
        ).print(out);
      }

      try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
        printer.printRecord(
          flight.getId(),
          flight.airline.getId(),
          flight.startingPoint.getId(),
          flight.destination.getId(),
          flight.plane.getId()
        );
      };
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}


