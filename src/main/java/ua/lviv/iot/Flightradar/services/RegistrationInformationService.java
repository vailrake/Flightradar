package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.dataAccessServices.*;
import ua.lviv.iot.flightradar.records.*;


@Service
public class RegistrationInformationService {
  @Autowired
  private RegistrationInformationDataAccessService registrationInformationDataAccessService;


  public List<RegistrationInformation> getAllRegistrationInformations() {
    List<Map> mapList = registrationInformationDataAccessService.getAllRegistrationInformationsData();
    List<RegistrationInformation> registrationInformations = new ArrayList<>();

    for (Map map : mapList) {
      int registrationInformationId = Integer.parseInt((String) map.get(RegistrationInformation.ID_PROPERTY));
      registrationInformations.add(getRegistrationInformation(registrationInformationId));
    }

    return registrationInformations;
  }


  public RegistrationInformation getRegistrationInformation(int id) {
    Map riData = registrationInformationDataAccessService.getRegistrationInformationData(id);

    return new RegistrationInformation(
      id,
      (String) riData.get(RegistrationInformation.INVENTORY_NUMBER_PROPERTY),
      (String) riData.get(RegistrationInformation.MODEL_NAME_PROPERTY),
      Integer.parseInt((String) riData.get(RegistrationInformation.MAX_SPEED_PROPERTY)),
      Integer.parseInt((String) riData.get(RegistrationInformation.WEIGHT_PROPERTY))
    );
  }

  public void createRegistrationInformation(RegistrationInformation registrationInformation) {
    for (RegistrationInformation existingRegistrationInformation : getAllRegistrationInformations()) {
      if (existingRegistrationInformation.getId() == registrationInformation.getId()) {
        throw new RecordInvalidException();
      }
    }

    registrationInformationDataAccessService.createRegistrationInformation(registrationInformation);
  }
}
