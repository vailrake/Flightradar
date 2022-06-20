package ua.lviv.iot.flightradar.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.lviv.iot.flightradar.errors.RecordNotFoundException;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.services.*;
import ua.lviv.iot.flightradar.records.*;


@RestController
@RequestMapping("telemetries")
public class TelemetryRecordController {
  private final TelemetryRecordService telemetryRecordService;

  @Autowired
  public TelemetryRecordController(TelemetryRecordService telemetryRecordService) {
    this.telemetryRecordService = telemetryRecordService;
  }

  @GetMapping
  public List<TelemetryRecord> getAllTelemetryRecords() {
    return telemetryRecordService.getAllTelemetryRecords();
  }


  @GetMapping(path = "{id}")
  public TelemetryRecord getTelemetryRecord(@PathVariable("id") int id) {
    try {
      return telemetryRecordService.getTelemetryRecord(id);
    } catch (RecordNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "telemetryRecord not found");
    }
  }

  @PostMapping
  public void createTelemetryRecord(@RequestBody TelemetryRecord telemetryRecord) {
    try {
      telemetryRecordService.createTelemetryRecord(telemetryRecord);
    } catch (RecordInvalidException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid data");
    }
  }
}


