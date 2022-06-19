package ua.lviv.iot.flightradar.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;

@Service
public class LocationService {
  @Autowired
  private LocationDataAccessService locationDataAccessService;

  List<Location> getAllLocations() {
    List<Map> mapList = locationDataAccessService.getAllLocationsData();
    List<Location> locations = new ArrayList<>();

    for (Map map : mapList) {
      int locationId = Integer.parseInt((String) map.get(Location.ID_PROPERTY));
      locations.add(getLocation(locationId));
    }

    return locations;
  }


  public Location getLocation(int id) {
    Map locationData = locationDataAccessService.getLocationData(id);

    double latitude = Double.parseDouble((String) locationData.get(Location.LATITUDE_PROPERTY));
    double longitude = Double.parseDouble((String) locationData.get(Location.LONGITUDE_PROPERTY));

    return new Location(id, latitude, longitude);
  }

  public void createLocation(Location location) {
    for (Location existingLocation : getAllLocations()) {
      if (existingLocation.getId() == location.getId()) {
        throw new RecordInvalidException();
      }
    }

    locationDataAccessService.createLocation(location);
  }
}
