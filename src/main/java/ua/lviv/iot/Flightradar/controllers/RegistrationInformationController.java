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
import ua.lviv.iot.flightradar.records.RegistrationInformation;
import ua.lviv.iot.flightradar.services.RegistrationInformationService;


@RestController
@RequestMapping("registration-information")
public class RegistrationInformationController {
  private final RegistrationInformationService registrationInformationService;

  @Autowired
  public RegistrationInformationController(
      RegistrationInformationService registrationInformationService
  ) {
    this.registrationInformationService = registrationInformationService;
  }

  @GetMapping
  public List<RegistrationInformation> getAllRegistrationInformations() {
    return registrationInformationService.getAllRegistrationInformations();
  }

  @GetMapping(path = "{id}")
  public RegistrationInformation getRegistrationInformation(@PathVariable("id") int id) {
    try {
      return registrationInformationService.getRegistrationInformation(id);
    } catch (RecordNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "registrationInformation not found");
    }
  }

  @PostMapping
  public void createRegistrationInformation(@RequestBody RegistrationInformation registrationInformation) {
    try {
      registrationInformationService.createRegistrationInformation(registrationInformation);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }

  @PutMapping("/{registrationInformationId}")
  public RegistrationInformation updateRegistrationInformation(@PathVariable int registrationInformationId, @RequestBody RegistrationInformation registrationInformation) {
    try {
      return registrationInformationService.updateRegistrationInformation(registrationInformationId, registrationInformation);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }

  @DeleteMapping("/{registrationInformationId}")
  public RegistrationInformation deleteRegistrationInformation(@PathVariable int registrationInformationId) {
    try {
      return registrationInformationService.deleteRegistrationInformation(registrationInformationId);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }
}
