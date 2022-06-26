package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.FlightDataService;
import ua.lviv.iot.flightradar.records.Flight;


@Service
public class FlightService {
  @Autowired
  private FlightDataService flightDataService;

  private final HashMap<Integer, Flight> flights = new HashMap<>();
  private static int idCounter = 0;

  public List<Flight> getAllFlights() {
    return new ArrayList<>(this.flights.values());
  }

  public Flight getFlight(int id) {
    return flights.get(id);
  }


  public void createFlight(Flight flight) {
    idCounter += 1;
    flight.setId(idCounter);
    flights.put(idCounter, flight);

    flightDataService.writeFlight(flight);
  }

  @PostConstruct
  public void loadFlights() {
    List<Flight> flights = flightDataService.currentMonthFlights();
    for (Flight flight : flights) {
      this.flights.put(flight.getId(), flight);
    }
  }
}
