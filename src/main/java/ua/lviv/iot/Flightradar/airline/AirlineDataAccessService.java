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
public class AirlineDataAccessService {
    private final ResourceLoader resourceLoader;

    @Autowired
    public AirlineDataAccessService(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
    }

    public List<Map> getAllAirlinesData() {
        Resource resource = resourceLoader.getResource("classpath:airlines.csv");

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

    public Map getAirlineData(int id) throws RecordNotFoundException {
        for(Map map : getAllAirlinesData()) {
            if(Integer.parseInt((String) map.get(Airline.ID_PROPERTY)) == id) {
                return map;
            }
        }

      throw new RecordNotFoundException();
    }
}
