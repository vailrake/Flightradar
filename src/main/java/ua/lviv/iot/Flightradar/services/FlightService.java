package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.FlightDataService;
import ua.lviv.iot.flightradar.records.Flight;
import ua.lviv.iot.flightradar.util.IdCounter;


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

    flightDataService.write(flight);
  }

  public Flight updateFlight(int flightId, Flight flight) {
    flight.setId(flightId);
    flights.put(flightId, flight);

    List<Flight> records = flights.values().stream().toList();
    flightDataService.writeAll(records);

    return flight;
  }

  public Flight deleteFlight(int flightId) {
    Flight flight = flights.remove(flightId);
    List<Flight> records = flights.values().stream().toList();
    flightDataService.writeAll(records);

    return flight;
  }


  @PostConstruct
  public void loadFlights() {
    List<Flight> flights = flightDataService.currentMonthRecords();
    for (Flight flight : flights) {
      this.flights.put(flight.getId(), flight);
    }
  }

  @PostConstruct
  public void initIdCounter() {
    idCounter = IdCounter.startCountingFrom(flights.keySet());
  }
}
