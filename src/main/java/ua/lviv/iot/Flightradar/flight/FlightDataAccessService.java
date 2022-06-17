package ua.lviv.iot.Flightradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import com.opencsv.bean.CsvToBeanBuilder;
import java.util.stream.IntStream;
import org.apache.commons.csv.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Repository
public class FlightDataAccessService {
    private final ResourceLoader resourceLoader;

    @Autowired
    public FlightDataAccessService(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
    }

    public List<Map> getAllFlightsData() {
        Resource resource = resourceLoader.getResource("classpath:flights.csv");

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

    public Map getFlightData(int id) throws RecordNotFoundException {
      for(Map map : getAllFlightsData()) {
        if(Integer.parseInt((String) map.get(Flight.ID_PROPERTY)) == id) {
          return map;
        }
      }

      throw new RecordNotFoundException();
    }
}
