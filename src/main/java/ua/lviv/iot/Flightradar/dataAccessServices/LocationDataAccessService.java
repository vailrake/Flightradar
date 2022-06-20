package ua.lviv.iot.flightradar.dataAccessServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.errors.RecordNotFoundException;
import ua.lviv.iot.flightradar.records.*;
import ua.lviv.iot.flightradar.util.CsvReader;


@Repository
public class LocationDataAccessService {
  private final ResourceLoader resourceLoader;

  @Autowired
  public LocationDataAccessService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  public List<Map> getAllLocationsData() {
    Resource resource = resourceLoader.getResource("locations.csv");

    File file = new File(resource.getFilename());
    CsvReader csvReader = new CsvReader(file);

    return csvReader.read();
  }

  public Map getLocationData(int id) throws RecordNotFoundException {
    for (Map map : getAllLocationsData()) {
      if (Integer.parseInt((String) map.get(Location.ID_PROPERTY)) == id) {
        return map;
      }
    }

    throw new RecordNotFoundException();
  }

  public void createLocation(Location location) {
    Resource resource = resourceLoader.getResource("locations.csv");

    try {
      File file = new File(resource.getFilename());
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (file.createNewFile()) {
        CSVFormat.DEFAULT.withHeader("id", "latitude", "longitude").print(out);
      }

      try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
        printer.printRecord(location.getId(), location.getLatitude(), location.getLongitude());
      };
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}


