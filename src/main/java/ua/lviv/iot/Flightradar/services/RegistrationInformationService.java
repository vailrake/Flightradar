package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.RegistrationInfoDataService;
import ua.lviv.iot.flightradar.records.RegistrationInformation;
import ua.lviv.iot.flightradar.util.IdCounter;


@Service
public class RegistrationInformationService {
  @Autowired
  private RegistrationInfoDataService registrationInfoDataService;

  private final HashMap<Integer, RegistrationInformation> registrationInformations = new HashMap<>();
  private static int idCounter = 0;

  public List<RegistrationInformation> getAllRegistrationInformations() {
    return new ArrayList<>(this.registrationInformations.values());
  }

  public RegistrationInformation getRegistrationInformation(int id) {
    return registrationInformations.get(id);
  }

  public void createRegistrationInformation(RegistrationInformation registrationInformation) {
    idCounter += 1;
    registrationInformation.setId(idCounter);
    registrationInformations.put(idCounter, registrationInformation);

    registrationInfoDataService.write(registrationInformation);
  }

  public RegistrationInformation updateRegistrationInformation(int registrationInformationId, RegistrationInformation registrationInformation) {
    registrationInformation.setId(registrationInformationId);
    registrationInformations.put(registrationInformationId, registrationInformation);

    List<RegistrationInformation> records = registrationInformations.values().stream().toList();
    registrationInfoDataService.writeAll(records);

    return registrationInformation;
  }

  public RegistrationInformation deleteRegistrationInformation(int registrationInformationId) {
    RegistrationInformation registrationInformation = registrationInformations.remove(registrationInformationId);
    List<RegistrationInformation> records = registrationInformations.values().stream().toList();
    registrationInfoDataService.writeAll(records);

    return registrationInformation;
  }

  @PostConstruct
  public void loadRegistrationInformations() {
    List<RegistrationInformation> registrationInformations = registrationInfoDataService.currentMonthRecords();
    for (RegistrationInformation registrationInformation : registrationInformations) {
      this.registrationInformations.put(registrationInformation.getId(), registrationInformation);
    }
  }

  @PostConstruct
  public void initIdCounter() {
    idCounter = IdCounter.startCountingFrom(registrationInformations.keySet());
  }
}
