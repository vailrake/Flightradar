package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
public class RegistrationInformation extends Record {
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

  public RegistrationInformation(Map map) {
    this.id = Integer.parseInt((String) map.get(RegistrationInformation.ID_PROPERTY));
    this.inventoryNumber = (String) RegistrationInformation.INVENTORY_NUMBER_PROPERTY;
    this.modelName = (String) RegistrationInformation.MODEL_NAME_PROPERTY;
    this.maxSpeed = Integer.parseInt((String) map.get(RegistrationInformation.MAX_SPEED_PROPERTY));
    this.weight = Integer.parseInt((String) map.get(RegistrationInformation.WEIGHT_PROPERTY));
  }

  @Override
  public List<String> csvHeaders() {
    return Arrays.asList(
      RegistrationInformation.ID_PROPERTY,
      RegistrationInformation.INVENTORY_NUMBER_PROPERTY,
      RegistrationInformation.MODEL_NAME_PROPERTY,
      RegistrationInformation.MAX_SPEED_PROPERTY,
      RegistrationInformation.WEIGHT_PROPERTY
    );
  }

  @Override
  public List<String> csvRows() {
    return Arrays.asList(
      Integer.toString(getId()),
      getInventoryNumber(),
      getModelName(),
      Integer.toString(getMaxSpeed()),
      Integer.toString(getWeight())
    );
  }
}
