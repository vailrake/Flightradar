package ua.lviv.iot.Flightradar;

public class PlaneTelemetryRecord {
  private final int id;
  private final int currentSpeed;
  private final int totalDistanceTraveled;
  private final Location location;

  public PlaneTelemetryRecord(int id, int currentSpeed, int totalDistanceTraveled, Location location) {
    this.id = id;
    this.currentSpeed = currentSpeed;
    this.totalDistanceTraveled = totalDistanceTraveled;
    this.location = location;
  }

  public int getID() {
    return id;
  }
}
