package ua.lviv.iot.flightradar.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.errors.RecordNotFoundException;
import ua.lviv.iot.flightradar.records.Flight;
import ua.lviv.iot.flightradar.services.FlightService;

@RestController
@RequestMapping("flights")
public class FlightController {
  private final FlightService flightService;

  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping
  public List<Flight> getAllFlights() {
    return flightService.getAllFlights();
  }


  @GetMapping(path = "{id}")
  public Flight getFlight(@PathVariable("id") int id) {
    try {
      return flightService.getFlight(id);
    } catch (RecordNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "flight not found");
    }
  }

  @PostMapping
  public void createFlight(@RequestBody Flight flight) {
    try {
      flightService.createFlight(flight);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }

  @PutMapping("/{flightId}")
  public Flight updateFlight(@PathVariable int flightId, @RequestBody Flight flight) {
    try {
      return flightService.updateFlight(flightId, flight);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }

  @DeleteMapping("/{flightId}")
  public Flight deleteFlight(@PathVariable int flightId) {
    try {
      return flightService.deleteFlight(flightId);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }
}

