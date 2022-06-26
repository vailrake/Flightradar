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
import ua.lviv.iot.flightradar.records.Plane;
import ua.lviv.iot.flightradar.util.CsvIO;

@Repository
public class PlaneDataService {
  public void writePlane(Plane plane) {
    try {
      CsvIO csvIO = new CsvIO("planes");

      // TODO rename to current day file
      boolean newFileCreated = csvIO.createCurrentMonthFile();

      File file = csvIO.dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        plane.writeCsvHeader(out);
      }

      plane.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<Plane> currentMonthPlanes() {
    List<Plane> planes = new ArrayList<>();

    CsvIO csvIO = new CsvIO("planes");
    for (Path path : csvIO.currentMonthPaths()) {
      for (Map planeMap : csvIO.read(path.toFile())) {
        planes.add(Plane.fromMap(planeMap));
      }
    }

    return planes;
  }
}


