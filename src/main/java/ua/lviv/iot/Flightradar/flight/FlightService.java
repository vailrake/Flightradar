package ua.lviv.iot.Flightradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class FlightService {
    @Autowired
    private FlightDataAccessService flightDataAccessService;
    @Autowired
    private AirlineService airlineService;
    @Autowired
    private LocationService locationService;

    // https://stackoverflow.com/questions/20289972/create-an-object-instance-from-a-map
    List<Flight> getAllFlights() {
        List<Map> mapList = flightDataAccessService.getAllFlightsData();
        List<Flight> flights = new ArrayList<>();

        for(Map map : mapList) {
            int flightId = Integer.parseInt((String) map.get(Flight.ID_PROPERTY));
            flights.add(getFlight(flightId));
        }

        return flights;
    }

    Flight getFlight(int id) {
        Map flightData = flightDataAccessService.getFlightData(id);

        int startLocationId = Integer.parseInt((String) flightData.get(Flight.START_LOC_ID_PROPERTY));
        int DestLocationId = Integer.parseInt((String) flightData.get(Flight.DEST_LOC_ID_PROPERTY));

        Location startLocation = locationService.getLocation(startLocationId);
        Location destinationLocation = locationService.getLocation(DestLocationId);

        int airlineId = Integer.parseInt((String) flightData.get(Flight.AIRLINE_ID_PROPERTY));
        Airline airline = airlineService.getAirline(airlineId);

        return new Flight(id, startLocation, destinationLocation, airline);
    }
}
