package ua.lviv.iot.Flightradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Repository
public class LocationDataAccessService {
    private final ResourceLoader resourceLoader;

    @Autowired
    public LocationDataAccessService(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
    }

    public List<Map> getAllLocationsData() {
        Resource resource = resourceLoader.getResource("classpath:locations.csv");

        File file;
        SafeReader csvReader;
        try {
          file = resource.getFile();
          csvReader = new SafeReader(file);
        } catch(IOException e) {
          return new ArrayList<>();
        }

        return csvReader.read();
    }

    public Map getLocationData(int id) throws RecordNotFoundException {
        for(Map map : getAllLocationsData()) {
            if(Integer.parseInt((String) map.get(Location.ID_PROPERTY)) == id) {
                return map;
            }
        }

      throw new RecordNotFoundException();
    }
}
