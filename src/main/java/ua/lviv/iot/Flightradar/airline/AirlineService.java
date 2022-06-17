package ua.lviv.iot.Flightradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AirlineService {
    @Autowired
    private AirlineDataAccessService airlineDataAccessService;

    List<Airline> getAllAirlines() {
        List<Map> mapList = airlineDataAccessService.getAllAirlinesData();
        List<Airline> airlines = new ArrayList<>();

        for(Map map : mapList) {
            int airlineId = Integer.parseInt((String) map.get(Airline.ID_PROPERTY));
            airlines.add(getAirline(airlineId));
        }

        return airlines;
    }

    Airline getAirline(int id) {
      Map airlineData = airlineDataAccessService.getAirlineData(id);

      return new Airline(id, (String) airlineData.get(Airline.NAME_PROPERTY));
    }
}
