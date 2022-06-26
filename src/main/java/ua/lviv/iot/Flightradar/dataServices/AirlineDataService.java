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
import ua.lviv.iot.flightradar.records.Airline;
import ua.lviv.iot.flightradar.util.CsvIO;

@Repository
public class AirlineDataService {
  public void writeAirline(Airline airline) {
    try {
      CsvIO csvIO = new CsvIO("airlines");

      // TODO rename to current day file
      boolean newFileCreated = csvIO.createCurrentMonthFile();

      File file = csvIO.dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        airline.writeCsvHeader(out);
      }

      airline.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<Airline> currentMonthAirlines() {
    List<Airline> airlines = new ArrayList<>();

    CsvIO csvIO = new CsvIO("airlines");
    for (Path path : csvIO.currentMonthPaths()) {
      for (Map airlineMap : csvIO.read(path.toFile())) {
        airlines.add(Airline.fromMap(airlineMap));
      }
    }

    return airlines;
  }
}
