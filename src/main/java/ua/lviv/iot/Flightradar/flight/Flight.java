package ua.lviv.iot.Flightradar;
import java.util.Map;

public class Flight {
  public static final String ID_PROPERTY = "id";
  public static final String AIRLINE_ID_PROPERTY = "airlineId";
  public static final String START_LOC_ID_PROPERTY = "startingPointLocationId";
  public static final String DEST_LOC_ID_PROPERTY = "destinationLocationId";

  private int id;
  public Location startingPoint;
  public Location destination;
  // public Plane plane;
  public Airline airline;

  public Flight(int id, Location startingPoint, Location destination, Airline airline) {
    this.id = id;
    this.startingPoint = startingPoint;
    this.destination = destination;
    // this.plane = plane;
    this.airline = airline;
  }

  public int getID() {
    return id;
  }
}
