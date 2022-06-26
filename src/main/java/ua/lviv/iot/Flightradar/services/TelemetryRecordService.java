package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.TelemetryRecordDataService;
import ua.lviv.iot.flightradar.records.TelemetryRecord;


@Service
public class TelemetryRecordService {
  @Autowired
  private TelemetryRecordDataService telemetryRecordDataService;

  private final HashMap<Integer, TelemetryRecord> telemetryRecords = new HashMap<>();
  private static int idCounter = 0;

  public List<TelemetryRecord> getAllTelemetryRecords() {
    return new ArrayList<>(this.telemetryRecords.values());
  }

  public TelemetryRecord getTelemetryRecord(int id) {
    return telemetryRecords.get(id);
  }

  public void createTelemetryRecord(TelemetryRecord telemetryRecord) {
    idCounter += 1;
    telemetryRecord.setId(idCounter);
    telemetryRecords.put(idCounter, telemetryRecord);

    telemetryRecordDataService.writeTelemetryRecord(telemetryRecord);
  }

  @PostConstruct
  public void loadTelemetryRecords() {
    List<TelemetryRecord> telemetryRecords = telemetryRecordDataService.currentMonthTelemetryRecords();
    for (TelemetryRecord telemetryRecord : telemetryRecords) {
      this.telemetryRecords.put(telemetryRecord.getId(), telemetryRecord);
    }
  }
}
