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
import ua.lviv.iot.flightradar.records.Plane;
import ua.lviv.iot.flightradar.services.PlaneService;


@RestController
@RequestMapping("planes")
public class PlaneController {
  private final PlaneService planeService;

  @Autowired
  public PlaneController(PlaneService planeService) {
    this.planeService = planeService;
  }

  @GetMapping
  public List<Plane> getAllPlanes() {
    return planeService.getAllPlanes();
  }


  @GetMapping(path = "{id}")
  public Plane getPlane(@PathVariable("id") int id) {
    try {
      return planeService.getPlane(id);
    } catch (RecordNotFoundException e) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "plane not found"
          );
    }
  }

  // TODO add or remove?
  //
  // @GetMapping(path = "{id}/information")
  // public RegistrationInformation getPlaneRegistrationInformation(@PathVariable("id") int id) {
  //   try {
  //     Plane plane = planeService.getPlane(id);
  //
  //     return plane.registrationInformation;
  //   } catch (RecordNotFoundException e) {
  //     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "plane not found");
  //   }
  // }
  //
  //
  // @GetMapping(path = "{id}/telemetry")
  // public TelemetryRecord getPlaneTelemetry(@PathVariable("id") int id) {
  //   try {
  //     Plane plane = planeService.getPlane(id);
  //
  //     return plane.telemetryRecord;
  //   } catch (RecordNotFoundException e) {
  //     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "plane not found");
  //   }
  // }
  //
  @PostMapping
  public void createPlane(@RequestBody Plane plane) {
    try {
      planeService.createPlane(plane);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }
}


