package ua.lviv.iot.flightradar.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.errors.RecordNotFoundException;
import ua.lviv.iot.flightradar.services.*;
import ua.lviv.iot.flightradar.records.*;

@RestController
@RequestMapping("airlines")
public class AirlineController {
  private final AirlineService airlineService;

  @Autowired
  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }

  @GetMapping
  public List<Airline> getAllAirlines() {
    return airlineService.getAllAirlines();
  }

  @GetMapping(path = "{id}")
  public Airline getAirline(@PathVariable("id") int id) {
    try {
      return airlineService.getAirline(id);
    } catch (RecordNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "airline not found");
    }
  }

  @PostMapping
  public void createAirline(@RequestBody Airline airline) {
    try {
      airlineService.createAirline(airline);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }
}
