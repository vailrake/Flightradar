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
public class AirlineDataAccessService {
  @SuppressWarnings("checkstyle:JavadocVariable")
  private final ResourceLoader resourceLoader;

  @Autowired
  public AirlineDataAccessService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public List<Map> getAllAirlinesData() {
    Resource resource = resourceLoader.getResource("airlines.csv");

    File file = new File(resource.getFilename());
    CsvReader csvReader = new CsvReader(file);

    return csvReader.read();
  }

  public Map getAirlineData(int id) throws RecordNotFoundException {
    for (Map map : getAllAirlinesData()) {
      if (Integer.parseInt((String) map.get(Airline.ID_PROPERTY)) == id) {
        return map;
      }
    }

    throw new RecordNotFoundException();
  }

  public void createAirline(Airline airline) {
    Resource resource = resourceLoader.getResource("airlines.csv");

    try {
      File file = new File(resource.getFilename());
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (file.createNewFile()) {
        CSVFormat.DEFAULT.withHeader("id", "name").print(out);
      }

      try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
        printer.printRecord(airline.getId(), airline.getName());
      }
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}
