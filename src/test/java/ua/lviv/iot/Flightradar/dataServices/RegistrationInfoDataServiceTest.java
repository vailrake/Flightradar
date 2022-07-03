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
import ua.lviv.iot.flightradar.records.RegistrationInformation;

class RegistrationInfoDataServiceTest {
  private RegistrationInfoDataService dataService;
  private Path todayFileName;

  @BeforeEach
  void setup() throws IOException {
    RegistrationInfoDataService dataService = new RegistrationInfoDataService();
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
    this.dataService.write(new RegistrationInformation(15, "example", "example2", 188, 3000));
    this.dataService.write(new RegistrationInformation(16, "example", "example2", 188, 3000));

    File expected = new File("src/test/resources/files/test-registration-info.csv");
    File actual = this.todayFileName.toFile();
    Assertions.assertTrue(FileUtils.contentEquals(expected, actual));
  }

  @Test
  void writeAll() throws IOException, RecordInvalidException {
    List<RegistrationInformation> registrationInformations = new ArrayList<>();
    registrationInformations.add(new RegistrationInformation(15, "example", "example2", 188, 3000));
    registrationInformations.add(new RegistrationInformation(16, "example", "example2", 188, 3000));
    this.dataService.writeAll(registrationInformations);

    File expected = new File("src/test/resources/files/test-registration-info.csv");
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
    writer.write("id,inventoryNumber,modelName,maxSpeed,weight\n");
    writer.write("115,example,example2,188,3000");
    writer.close();

    this.dataService.write(new RegistrationInformation(15, "example", "example2", 188, 3000));
    this.dataService.write(new RegistrationInformation(16, "example", "example2", 188, 3000));
    this.dataService.write(new RegistrationInformation(17, "example", "example2", 188, 3000));

    Assertions.assertTrue(this.dataService.currentMonthRecords().size() == 3);
  }
}
