package ua.lviv.iot.flightradar.dataServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.records.TelemetryRecord;

class TelemetryRecordDataServiceTest {
  private TelemetryRecordDataService dataService;
  private Path todayFileName;

  @BeforeEach
  void setup() throws IOException {
    TelemetryRecordDataService dataService = new TelemetryRecordDataService();
    this.dataService = dataService;
    this.todayFileName = dataService.dayOfCurrentMonthPath();
    FileUtils.cleanDirectory(new File("src/main/resources/files"));
  }

  @AfterEach
  void destroy() throws IOException {
    FileUtils.cleanDirectory(new File("src/main/resources/files"));
  }

  @Test
  void write() throws IOException, RecordInvalidException {
    this.dataService.write(new TelemetryRecord(15, 22, 33, 44));
    this.dataService.write(new TelemetryRecord(16, 22, 33, 44));

    File expected = new File("src/test/resources/files/test-telemetry-records.csv");
    File actual = this.todayFileName.toFile();
    Assertions.assertTrue(FileUtils.contentEquals(expected, actual));
  }

  @Test
  void writeAll() throws IOException, RecordInvalidException {
    List<TelemetryRecord> telemetryRecords = new ArrayList<>();
    telemetryRecords.add(new TelemetryRecord(15, 22, 33, 44));
    telemetryRecords.add(new TelemetryRecord(16, 22, 33, 44));
    this.dataService.writeAll(telemetryRecords);

    File expected = new File("src/test/resources/files/test-telemetry-records.csv");
    File actual = this.todayFileName.toFile();
    Assertions.assertTrue(FileUtils.contentEquals(expected, actual));
  }

  @Test
  void currentMonthRecords() throws IOException {
    String year = Integer.toString(LocalDate.now().getYear());
    String month = Integer.toString(LocalDate.now().getMonthValue() - 1);
    int dayOfMonth = 1;

    File file = new File("src/main/resources/files/" + this.dataService.resourceName() + "-" +
        year + "-" + month + "-" + dayOfMonth + ".csv");
    file.delete();

    FileWriter writer = new FileWriter(file);
    writer.write("id,locationId,currentSpeed,totalDistanceTraveled\n");
    writer.write("115,122,133,144");
    writer.close();

    this.dataService.write(new TelemetryRecord(15, 22, 33, 44));
    this.dataService.write(new TelemetryRecord(16, 22, 33, 44));
    this.dataService.write(new TelemetryRecord(17, 22, 33, 44));

    Assertions.assertTrue(this.dataService.currentMonthRecords().size() == 3);
  }
}
