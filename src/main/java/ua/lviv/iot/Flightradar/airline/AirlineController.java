package ua.lviv.iot.Flightradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

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
      } catch(RecordNotFoundException e) {
          throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, "airline not found"
          );
      }
    }
}
