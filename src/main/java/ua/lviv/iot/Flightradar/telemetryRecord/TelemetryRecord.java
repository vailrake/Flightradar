package ua.lviv.iot.flightradar.telemetryRecord;

import ua.lviv.iot.flightradar.location.Location;

public class TelemetryRecord {
  public static final String ID_PROPERTY = "id";
  public static final String LOCATION_ID_PROPERTY = "locationId";
  public static final String SPEED_PROPERTY = "currentSpeed";
  public static final String DISTANCE_PROPERTY = "totalDistanceTraveled";

  private final int id;
  private final int currentSpeed;
  private final int totalDistanceTraveled;
  public final Location location;

  
  public TelemetryRecord(int id, int currentSpeed, int totalDistanceTraveled, Location location) {
    this.id = id;
    this.currentSpeed = currentSpeed;
    this.totalDistanceTraveled = totalDistanceTraveled;
    this.location = location;
  }

  public int getId() {
    return id;
  }

  public int getCurrentSpeed() {
    return currentSpeed;
  }

  public int getTotalDistanceTraveled() {
    return totalDistanceTraveled;
  }
}
