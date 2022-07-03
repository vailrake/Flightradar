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
import ua.lviv.iot.flightradar.records.Airline;

class AirlineDataServiceTest {
  private AirlineDataService dataService;
  private Path todayFileName;

  @BeforeEach
  void setup() throws IOException {
    AirlineDataService dataService = new AirlineDataService();
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
    this.dataService.write(new Airline(15, "example"));
    this.dataService.write(new Airline(16, "example2"));

    File expected = new File("src/test/resources/files/test-airlines.csv");
    File actual = this.todayFileName.toFile();
    Assertions.assertTrue(FileUtils.contentEquals(expected, actual));
  }

  @Test
  void writeAll() throws IOException, RecordInvalidException {
    List<Airline> airlines = new ArrayList<>();
    airlines.add(new Airline(15, "example"));
    airlines.add(new Airline(16, "example2"));
    this.dataService.writeAll(airlines);

    File expected = new File("src/test/resources/files/test-airlines.csv");
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
    writer.write("id, name\n");
    writer.write("555, example555");
    writer.close();

    this.dataService.write(new Airline(15, "example"));
    this.dataService.write(new Airline(16, "example2"));
    this.dataService.write(new Airline(17, "example3"));

    Assertions.assertTrue(this.dataService.currentMonthRecords().size() == 3);
  }
}
