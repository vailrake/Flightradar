package ua.lviv.iot.flightradar.telemetryRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.location.Location;
import ua.lviv.iot.flightradar.location.LocationService;


@Service
public class TelemetryRecordService {
  @Autowired
  private TelemetryRecordDataAccessService telemetryRecordDataAccessService;
  @Autowired
  private LocationService locationService;

  List<TelemetryRecord> getAllTelemetryRecords() {
    List<Map> mapList = telemetryRecordDataAccessService.getAllTelemetryRecordsData();
    List<TelemetryRecord> telemetryRecords = new ArrayList<>();

    for (Map map : mapList) {
      int telemetryRecordId = Integer.parseInt((String) map.get(TelemetryRecord.ID_PROPERTY));
      telemetryRecords.add(getTelemetryRecord(telemetryRecordId));
    }

    return telemetryRecords;
  }

  public TelemetryRecord getTelemetryRecord(int id) {
    Map trData = telemetryRecordDataAccessService.getTelemetryRecordData(id);

    int locationId = Integer.parseInt((String) trData.get(TelemetryRecord.LOCATION_ID_PROPERTY));
    Location location = locationService.getLocation(locationId);

    return new TelemetryRecord(
      id,
      Integer.parseInt((String) trData.get(TelemetryRecord.SPEED_PROPERTY)),
      Integer.parseInt((String) trData.get(TelemetryRecord.DISTANCE_PROPERTY)),
      location
    );
  }

  public void createTelemetryRecord(TelemetryRecord telemetryRecord) {
    for (TelemetryRecord existingTelemetryRecord : getAllTelemetryRecords()) {
      if (existingTelemetryRecord.getId() == telemetryRecord.getId()) {
        throw new RecordInvalidException();
      }
    }

    try {
      locationService.createLocation(telemetryRecord.location);
    } catch (RecordInvalidException e) {
      // already exists with such locationId
    }

    telemetryRecordDataAccessService.createTelemetryRecord(telemetryRecord);
  }
}
