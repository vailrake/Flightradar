package ua.lviv.iot.flightradar.records;

public class Flight {
  public static final String ID_PROPERTY = "id";
  public static final String START_LOC_ID_PROPERTY = "startingPointLocationId";
  public static final String DEST_LOC_ID_PROPERTY = "destinationLocationId";
  public static final String PLANE_ID_PROPERTY = "planeId";
  public static final String AIRLINE_ID_PROPERTY = "airlineId";

  private final int id;
  public final Location startingPoint;
  public final Location destination;
  public final Plane plane;
  public final Airline airline;


  public Flight(int id, Location startingPoint, Location destination,
                Airline airline, Plane plane) {
    this.id = id;
    this.startingPoint = startingPoint;
    this.destination = destination;
    this.plane = plane;
    this.airline = airline;
  }

  public int getId() {
    return id;
  }
}

