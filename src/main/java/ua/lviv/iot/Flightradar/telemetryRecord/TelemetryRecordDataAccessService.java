package ua.lviv.iot.flightradar.telemetryRecord;

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
import ua.lviv.iot.flightradar.util.CsvReader;


@Repository
public class TelemetryRecordDataAccessService {
  private final ResourceLoader resourceLoader;

  @Autowired
  public TelemetryRecordDataAccessService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  public List<Map> getAllTelemetryRecordsData() {
    Resource resource = resourceLoader.getResource("telemetries.csv");

    File file = new File(resource.getFilename());
    CsvReader csvReader = new CsvReader(file);

    return csvReader.read();
  }


  public Map getTelemetryRecordData(int id) throws RecordNotFoundException {
    for (Map map : getAllTelemetryRecordsData()) {
      if (Integer.parseInt((String) map.get(TelemetryRecord.ID_PROPERTY)) == id) {
        return map;
      }
    }

    throw new RecordNotFoundException();
  }

  public void createTelemetryRecord(TelemetryRecord telemetryRecord) {
    Resource resource = resourceLoader.getResource("telemetries.csv");

    try {
      File file = new File(resource.getFilename());
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (file.createNewFile()) {
        CSVFormat.DEFAULT.withHeader(
          TelemetryRecord.ID_PROPERTY,
          TelemetryRecord.SPEED_PROPERTY,
          TelemetryRecord.DISTANCE_PROPERTY,
          TelemetryRecord.LOCATION_ID_PROPERTY
        ).print(out);
      }

      try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
        printer.printRecord(
          telemetryRecord.getId(),
          telemetryRecord.getCurrentSpeed(),
          telemetryRecord.getTotalDistanceTraveled(),
          telemetryRecord.location.getId()
        );
      };
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}

