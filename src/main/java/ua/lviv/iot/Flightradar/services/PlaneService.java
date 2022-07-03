package ua.lviv.iot.flightradar.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.flightradar.dataServices.PlaneDataService;
import ua.lviv.iot.flightradar.records.Plane;
import ua.lviv.iot.flightradar.util.IdCounter;


@Service
public class PlaneService {
  @Autowired
  private PlaneDataService planeDataService;

  private final HashMap<Integer, Plane> planes = new HashMap<>();
  private static int idCounter = 0;

  public List<Plane> getAllPlanes() {
    return new ArrayList<>(this.planes.values());
  }

  public Plane getPlane(int id) {
    return planes.get(id);
  }

  public void createPlane(Plane plane) {
    idCounter += 1;
    plane.setId(idCounter);
    planes.put(idCounter, plane);

    planeDataService.write(plane);
  }

  public Plane updatePlane(int planeId, Plane plane) {
    plane.setId(planeId);
    planes.put(planeId, plane);

    List<Plane> records = planes.values().stream().toList();
    planeDataService.writeAll(records);

    return plane;
  }

  public Plane deletePlane(int planeId) {
    Plane plane = planes.remove(planeId);
    List<Plane> records = planes.values().stream().toList();
    planeDataService.writeAll(records);

    return plane;
  }

  @PostConstruct
  public void loadPlanes() {
    List<Plane> planes = planeDataService.currentMonthRecords();
    for (Plane plane : planes) {
      this.planes.put(plane.getId(), plane);
    }
  }

  @PostConstruct
  public void initIdCounter() {
    idCounter = IdCounter.startCountingFrom(planes.keySet());
  }
}

