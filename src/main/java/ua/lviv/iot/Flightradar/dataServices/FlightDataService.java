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
import ua.lviv.iot.flightradar.util.CsvIO;

@Repository
public class FlightDataService {
  public void writeFlight(Flight flight) {
    try {
      CsvIO csvIO = new CsvIO("flights");

      // TODO rename to current day file
      boolean newFileCreated = csvIO.createCurrentMonthFile();

      File file = csvIO.dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        flight.writeCsvHeader(out);
      }

      flight.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<Flight> currentMonthFlights() {
    List<Flight> flights = new ArrayList<>();

    CsvIO csvIO = new CsvIO("flights");
    for (Path path : csvIO.currentMonthPaths()) {
      for (Map flightMap : csvIO.read(path.toFile())) {
        flights.add(Flight.fromMap(flightMap));
      }
    }

    return flights;
  }
}


