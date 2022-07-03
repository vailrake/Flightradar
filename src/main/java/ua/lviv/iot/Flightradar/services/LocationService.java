package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.LocationDataService;
import ua.lviv.iot.flightradar.records.Location;
import ua.lviv.iot.flightradar.util.IdCounter;

@Service
public class LocationService {
  @Autowired
  private LocationDataService locationDataService;

  private final HashMap<Integer, Location> locations = new HashMap<>();
  private static int idCounter = 0;

  public List<Location> getAllLocations() {
    return new ArrayList<>(this.locations.values());
  }

  public Location getLocation(int id) {
    return locations.get(id);
  }

  public void createLocation(Location location) {
    idCounter += 1;
    location.setId(idCounter);
    locations.put(idCounter, location);

    locationDataService.write(location);
  }

  public Location updateLocation(int locationId, Location location) {
    location.setId(locationId);
    locations.put(locationId, location);

    List<Location> records = locations.values().stream().toList();
    locationDataService.writeAll(records);

    return location;
  }

  public Location deleteLocation(int locationId) {
    Location location = locations.remove(locationId);
    List<Location> records = locations.values().stream().toList();
    locationDataService.writeAll(records);

    return location;
  }

  @PostConstruct
  public void loadLocations() {
    List<Location> locations = locationDataService.currentMonthRecords();
    for (Location location : locations) {
      this.locations.put(location.getId(), location);
    }
  }

  @PostConstruct
  public void initIdCounter() {
    idCounter = IdCounter.startCountingFrom(locations.keySet());
  }
}
