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
public class PlaneService {
  @Autowired
  private PlaneDataAccessService planeDataAccessService;
  @Autowired
  private AirlineService airlineService;
  @Autowired
  private RegistrationInformationService registrationInformationService;
  @Autowired
  private TelemetryRecordService telemetryRecordService;

  public List<Plane> getAllPlanes() {
    List<Map> mapList = planeDataAccessService.getAllPlanesData();
    List<Plane> planes = new ArrayList<>();

    for (Map map : mapList) {
      int planeId = Integer.parseInt((String) map.get(Plane.ID_PROPERTY));
      planes.add(getPlane(planeId));
    }

    return planes;
  }

  public Plane getPlane(int id) {
    Map planeData = planeDataAccessService.getPlaneData(id);
    int airlineId = Integer.parseInt((String) planeData.get(Plane.AIRLINE_ID_PROPERTY));
    int registrationInformationId = Integer.parseInt(
        (String) planeData.get(Plane.INFORMATION_ID_PROPERTY)
    );
    int telemetryRecordId = Integer.parseInt((String) planeData.get(Plane.TELEMETRY_ID_PROPERTY));

    Airline airline = airlineService.getAirline(airlineId);
    RegistrationInformation registrationInformation = registrationInformationService
        .getRegistrationInformation(registrationInformationId);
    TelemetryRecord telemetryRecord = telemetryRecordService.getTelemetryRecord(telemetryRecordId);

    return new Plane(id, airline, registrationInformation, telemetryRecord);
  }

  public void createPlane(Plane plane) {
    for (Plane existingPlane : getAllPlanes()) {
      if (existingPlane.getId() == plane.getId()) {
        throw new RecordInvalidException();
      }
    }

    try {
      airlineService.createAirline(plane.airline);
    } catch (RecordInvalidException e) {
      // already exists with such id
    }

    try {
      registrationInformationService.createRegistrationInformation(
        plane.registrationInformation
      );
    } catch (RecordInvalidException e) {
      // already exists with such id
    }

    try {
      telemetryRecordService.createTelemetryRecord(
        plane.telemetryRecord
      );
    } catch (RecordInvalidException e) {
      // already exists with such airlineId
    }

    planeDataAccessService.createPlane(plane);
  }
}

