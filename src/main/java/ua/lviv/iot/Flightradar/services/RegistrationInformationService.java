package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.RegistrationInfoDataService;
import ua.lviv.iot.flightradar.records.RegistrationInformation;


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

    registrationInfoDataService.writeRegistrationInformation(registrationInformation);
  }

  @PostConstruct
  public void loadRegistrationInformations() {
    List<RegistrationInformation> registrationInformations = registrationInfoDataService.currentMonthRegistrationInformations();
    for (RegistrationInformation registrationInformation : registrationInformations) {
      this.registrationInformations.put(registrationInformation.getId(), registrationInformation);
    }
  }
}
