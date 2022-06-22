package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Plane {
  public static final String ID_PROPERTY = "id";
  public static final String AIRLINE_ID_PROPERTY = "airlineId";
  public static final String INFORMATION_ID_PROPERTY = "registrationInformationId";
  public static final String TELEMETRY_ID_PROPERTY = "telemetryRecordId";

  private final int id;
  public final RegistrationInformation registrationInformation;
  public final TelemetryRecord telemetryRecord;
  public final Airline airline;

  public Plane(int id, Airline airline, RegistrationInformation registrationInformation,
               TelemetryRecord telemetryRecord) {
    this.id = id;
    this.registrationInformation = registrationInformation;
    this.telemetryRecord = telemetryRecord;
    this.airline = airline;
  }

  public int getId() {
    return id;
  }

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      Plane.ID_PROPERTY,
      Plane.AIRLINE_ID_PROPERTY,
      Plane.INFORMATION_ID_PROPERTY,
      Plane.TELEMETRY_ID_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
      try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
        printer.printRecord(
          getId(),
          airline.getId(),
          registrationInformation.getId(),
          telemetryRecord.getId()
        );
      };
  }
}
