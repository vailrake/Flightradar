package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.AirlineDataService;
import ua.lviv.iot.flightradar.records.Airline;


@Service
public class AirlineService {
  @Autowired
  private AirlineDataService airlineDataService;

  private final HashMap<Integer, Airline> airlines = new HashMap<>();
  private static int idCounter = 0;

  public List<Airline> getAllAirlines() {
    return new ArrayList<>(this.airlines.values());
  }

  public Airline getAirline(int id) {
    return airlines.get(id);
  }

  public void createAirline(Airline airline) {
    idCounter += 1;
    airline.setId(idCounter);
    airlines.put(idCounter, airline);

    airlineDataService.writeAirline(airline);
  }

  @PostConstruct
  public void loadAirlines() {
    List<Airline> airlines = airlineDataService.currentMonthAirlines();
    for (Airline airline : airlines) {
      this.airlines.put(airline.getId(), airline);
    }
  }
}
