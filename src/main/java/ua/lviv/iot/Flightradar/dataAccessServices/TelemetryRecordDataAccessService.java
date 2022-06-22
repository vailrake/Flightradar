package ua.lviv.iot.flightradar.dataAccessServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.errors.RecordNotFoundException;
import ua.lviv.iot.flightradar.records.*;
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
        telemetryRecord.writeCsvHeader(out);
      }

      telemetryRecord.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}

