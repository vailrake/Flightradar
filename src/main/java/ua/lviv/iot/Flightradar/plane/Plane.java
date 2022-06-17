package ua.lviv.iot.Flightradar;

public class Plane {
  private final int id;
  public final RegistrationInformation registrationInformation;
  public final PlaneTelemetryRecord planeTelemetryRecord;
  public final Airline airline;

  public Plane(int id, RegistrationInformation registrationInformation,
               PlaneTelemetryRecord planeTelemetryRecord, Airline airline) {
    this.id = id;
    this.registrationInformation = registrationInformation;
    this.planeTelemetryRecord = planeTelemetryRecord;
    this.airline = airline;
  }

  public int getID() {
    return id;
  }
}
