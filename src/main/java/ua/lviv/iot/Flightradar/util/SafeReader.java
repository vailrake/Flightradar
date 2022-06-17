package ua.lviv.iot.Flightradar;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.csv.*;

// https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
// https://www.baeldung.com/spring-classpath-file-access
// https://stackoverflow.com/questions/33107163/how-to-convert-a-csv-file-to-listmapstring-string
public class SafeReader {
    private final File file;

    public SafeReader(File file) {
        this.file = file;
    }

    public List<Map> read() {
        List<Map> records = new ArrayList<>();

        try {
            records = readFile();
        } catch(IOException e) {
            records = createFile();
        }

        return records;
    }

    private List<Map> readFile() throws IOException {
        if (!file.isFile() && !file.createNewFile()) {
          throw new IOException("Error creating new file: " + file.getAbsolutePath());
        }

        Reader reader = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);

        List<Map> listOfMaps = new ArrayList<>();
        for (CSVRecord record : records) {
            listOfMaps.add(record.toMap());
        }
        return listOfMaps;
    }

    private List<Map> createFile() {
        // ...
        return new ArrayList<Map>();
    }
}

