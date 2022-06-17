package ua.lviv.iot.Flightradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

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
      } catch(RecordNotFoundException e) {
          throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, "flight not found"
          );
      }
    }
}
