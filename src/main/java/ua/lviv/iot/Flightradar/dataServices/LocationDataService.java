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
import ua.lviv.iot.flightradar.records.Location;
import ua.lviv.iot.flightradar.util.CsvIO;

@Repository
public class LocationDataService {
  public void writeLocation(Location location) {
    try {
      CsvIO csvIO = new CsvIO("locations");

      // TODO rename to current day file
      boolean newFileCreated = csvIO.createCurrentMonthFile();

      File file = csvIO.dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        location.writeCsvHeader(out);
      }

      location.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<Location> currentMonthLocations() {
    List<Location> locations = new ArrayList<>();

    CsvIO csvIO = new CsvIO("locations");
    for (Path path : csvIO.currentMonthPaths()) {
      for (Map locationMap : csvIO.read(path.toFile())) {
        locations.add(Location.fromMap(locationMap));
      }
    }

    return locations;
  }
}


