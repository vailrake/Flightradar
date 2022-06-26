package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationInformation {
  // TODO maybe move id conunter to each record?
  public static final String ID_PROPERTY = "id";
  public static final String INVENTORY_NUMBER_PROPERTY = "inventoryNumber";
  public static final String MODEL_NAME_PROPERTY = "modelName";
  public static final String MAX_SPEED_PROPERTY = "maxSpeed";
  public static final String WEIGHT_PROPERTY = "weight";

  private int id;
  private String inventoryNumber;
  private String modelName;
  private int maxSpeed;
  private int weight;

  public void writeCsvHeader(FileWriter out) throws IOException {
    CSVFormat.DEFAULT.withHeader(
      RegistrationInformation.ID_PROPERTY,
      RegistrationInformation.INVENTORY_NUMBER_PROPERTY,
      RegistrationInformation.MODEL_NAME_PROPERTY,
      RegistrationInformation.MAX_SPEED_PROPERTY,
      RegistrationInformation.WEIGHT_PROPERTY
    ).print(out);
  }

  public void writeCsvRow(FileWriter out) throws IOException {
    try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
      printer.printRecord(getId(), getInventoryNumber(), getModelName(), getMaxSpeed(), getWeight());
    }
  }

  public static RegistrationInformation fromMap(Map map) {
    return new RegistrationInformation(
      Integer.parseInt((String) map.get(RegistrationInformation.ID_PROPERTY)),
      (String) RegistrationInformation.INVENTORY_NUMBER_PROPERTY,
      (String) RegistrationInformation.MODEL_NAME_PROPERTY,
      Integer.parseInt((String) map.get(RegistrationInformation.MAX_SPEED_PROPERTY)),
      Integer.parseInt((String) map.get(RegistrationInformation.WEIGHT_PROPERTY))
    );
  }
}
