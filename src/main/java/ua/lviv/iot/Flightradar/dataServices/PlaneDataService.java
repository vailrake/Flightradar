package ua.lviv.iot.flightradar.dataServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.flightradar.errors.RecordInvalidException;
import ua.lviv.iot.flightradar.records.Plane;

@Repository
public class PlaneDataService extends AbstractDataService<Plane> {
  @Override
  protected String resourceName() {
    return "planes";
  }

  @Override
  protected Plane recordFromMap(Map map) {
    return new Plane(map);
  }
}


