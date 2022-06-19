package ua.lviv.iot.flightradar.flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.airline.Airline;
import ua.lviv.iot.flightradar.airline.AirlineService;
import ua.lviv.iot.flightradar.location.Location;
import ua.lviv.iot.flightradar.location.LocationService;
import ua.lviv.iot.flightradar.plane.Plane;
import ua.lviv.iot.flightradar.plane.PlaneService;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;


@Service
public class FlightService {
  @Autowired
  private FlightDataAccessService flightDataAccessService;
  @Autowired
  private AirlineService airlineService;
  @Autowired
  private LocationService locationService;
  @Autowired
  private PlaneService planeService;

  public List<Flight> getAllFlights() {
    List<Map> mapList = flightDataAccessService.getAllFlightsData();
    List<Flight> flights = new ArrayList<>();

    for (Map map : mapList) {
      int flightId = Integer.parseInt((String) map.get(Flight.ID_PROPERTY));
      flights.add(getFlight(flightId));
    }

    return flights;
  }

  public Flight getFlight(int id) {
    Map flightData = flightDataAccessService.getFlightData(id);

    int startLocationId = Integer.parseInt((String) flightData.get(Flight.START_LOC_ID_PROPERTY));
    int destLocationId = Integer.parseInt((String) flightData.get(Flight.DEST_LOC_ID_PROPERTY));

    Location startLocation = locationService.getLocation(startLocationId);
    Location destinationLocation = locationService.getLocation(destLocationId);

    int airlineId = Integer.parseInt((String) flightData.get(Flight.AIRLINE_ID_PROPERTY));
    int planeId = Integer.parseInt((String) flightData.get(Flight.PLANE_ID_PROPERTY));

    Airline airline = airlineService.getAirline(airlineId);
    Plane plane = planeService.getPlane(planeId);

    return new Flight(id, startLocation, destinationLocation, airline, plane);
  }


  public void createFlight(Flight flight) {
    for (Flight existingFlight : getAllFlights()) {
      if (existingFlight.getId() == flight.getId()) {
        throw new RecordInvalidException();
      }
    }

    try {
      locationService.createLocation(flight.startingPoint);
    } catch (RecordInvalidException e) {
      // already exists with such id
    }

    try {
      locationService.createLocation(flight.destination);
    } catch (RecordInvalidException e) {
      // already exists with such id
    }

    try {
      planeService.createPlane(flight.plane);
    } catch (RecordInvalidException e) {
      // already exists with such id
    }

    try {
      airlineService.createAirline(flight.airline);
    } catch (RecordInvalidException e) {
      // already exists with such id
    }

    flightDataAccessService.createFlight(flight);
  }

}
