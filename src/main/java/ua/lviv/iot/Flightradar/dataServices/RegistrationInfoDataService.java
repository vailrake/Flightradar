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
import ua.lviv.iot.flightradar.records.RegistrationInformation;
import ua.lviv.iot.flightradar.util.CsvIO;

@Repository
public class RegistrationInfoDataService {
  public void writeRegistrationInformation(RegistrationInformation registrationInformation) {
    try {
      CsvIO csvIO = new CsvIO("registration_information");

      // TODO rename to current day file
      boolean newFileCreated = csvIO.createCurrentMonthFile();

      File file = csvIO.dayOfCurrentMonthPath().toFile();
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (newFileCreated) {
        registrationInformation.writeCsvHeader(out);
      }

      registrationInformation.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }

  public List<RegistrationInformation> currentMonthRegistrationInformations() {
    List<RegistrationInformation> registrationInformations = new ArrayList<>();

    CsvIO csvIO = new CsvIO("registrationInformations");
    for (Path path : csvIO.currentMonthPaths()) {
      for (Map registrationInformationMap : csvIO.read(path.toFile())) {
        registrationInformations.add(RegistrationInformation.fromMap(registrationInformationMap));
      }
    }

    return registrationInformations;
  }
}


