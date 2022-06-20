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
public class AirlineService {
  @Autowired
  private AirlineDataAccessService airlineDataAccessService;

  public List<Airline> getAllAirlines() {
    List<Map> mapList = airlineDataAccessService.getAllAirlinesData();
    List<Airline> airlines = new ArrayList<>();

    for (Map map : mapList) {
      int airlineId = Integer.parseInt((String) map.get(Airline.ID_PROPERTY));
      airlines.add(getAirline(airlineId));
    }

    return airlines;
  }

  public Airline getAirline(int id) {
    Map airlineData = airlineDataAccessService.getAirlineData(id);

    return new Airline(id, (String) airlineData.get(Airline.NAME_PROPERTY));
  }

  public void createAirline(Airline airline) {
    for (Airline existingAirline : getAllAirlines()) {
      if (existingAirline.getId() == airline.getId()) {
        throw new RecordInvalidException();
      }
    }

    airlineDataAccessService.createAirline(airline);
  }
}
