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
public class RegistrationInformationDataAccessService {
  private final ResourceLoader resourceLoader;

  @Autowired
  public RegistrationInformationDataAccessService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public List<Map> getAllRegistrationInformationsData() {
    Resource resource = resourceLoader.getResource("registration_information.csv");

    File file = new File(resource.getFilename());
    CsvReader csvReader = new CsvReader(file);

    return csvReader.read();
  }

  public Map getRegistrationInformationData(int id) throws RecordNotFoundException {
    for (Map map : getAllRegistrationInformationsData()) {
      if (Integer.parseInt((String) map.get(RegistrationInformation.ID_PROPERTY)) == id) {
        return map;
      }
    }

    throw new RecordNotFoundException();
  }

  public void createRegistrationInformation(RegistrationInformation registrationInformation) {
    Resource resource = resourceLoader.getResource("registration_information.csv");

    try {
      File file = new File(resource.getFilename());
      FileWriter out = new FileWriter(file.getAbsolutePath(), true);

      if (file.createNewFile()) {
        registrationInformation.writeCsvHeader(out);
      }

      registrationInformation.writeCsvRow(out);
    } catch (IOException e) {
      throw new RecordInvalidException();
    }
  }
}


