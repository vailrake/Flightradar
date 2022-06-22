package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

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

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      Flight.ID_PROPERTY,
      Flight.AIRLINE_ID_PROPERTY,
      Flight.START_LOC_ID_PROPERTY,
      Flight.DEST_LOC_ID_PROPERTY,
      Flight.PLANE_ID_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(
        getId(),
        airline.getId(),
        startingPoint.getId(),
        destination.getId(),
        plane.getId()
      );
    };
  }
}

