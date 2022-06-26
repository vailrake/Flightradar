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
import ua.lviv.iot.flightradar.records.TelemetryRecord;
import ua.lviv.iot.flightradar.util.CsvIO;


@Repository
public class TelemetryRecordDataService {
  public void writeTelemetryRecord(TelemetryRecord telemetryRecord) {
    try {
      CsvIO csvIO = new CsvIO("telemetry_records");

      // TODO rename to current day file
      boolean newFileCreated = csvIO.createCurrentMonthFile();

      File file = csvIO.dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        telemetryRecord.writeCsvHeader(out);
      }

      telemetryRecord.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<TelemetryRecord> currentMonthTelemetryRecords() {
    List<TelemetryRecord> telemetryRecords = new ArrayList<>();

    CsvIO csvIO = new CsvIO("telemetryRecords");
    for (Path path : csvIO.currentMonthPaths()) {
      for (Map telemetryRecordMap : csvIO.read(path.toFile())) {
        telemetryRecords.add(TelemetryRecord.fromMap(telemetryRecordMap));
      }
    }

    return telemetryRecords;
  }
}

