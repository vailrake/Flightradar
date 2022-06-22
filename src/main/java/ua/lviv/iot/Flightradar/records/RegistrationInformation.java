package ua.lviv.iot.flightradar.records;

import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class RegistrationInformation {
  public static final String ID_PROPERTY = "id";
  public static final String PLANE_ID_PROPERTY = "planeId";
  public static final String INVENTORY_NUMBER_PROPERTY = "inventoryNumber";
  public static final String MODEL_NAME_PROPERTY = "modelName";
  public static final String MAX_SPEED_PROPERTY = "maxSpeed";
  public static final String WEIGHT_PROPERTY = "weight";

  private final int id;
  private final String inventoryNumber;
  private final String modelName;
  private final int maxSpeed;
  private final int weight;


  public RegistrationInformation(int id, String inventoryNumber, String modelName,
                                 int maxSpeed, int weight) {
    this.id = id;
    this.inventoryNumber = inventoryNumber;
    this.modelName = modelName;
    this.maxSpeed = maxSpeed;
    this.weight = weight;
  }

  public int getId() {
    return id;
  }

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public String getModelName() {
    return modelName;
  }

  public int getMaxSpeed() {
    return maxSpeed;
  }

  public int getWeight() {
    return weight;
  }

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
      printer.printRecord(
        getId(),
        getInventoryNumber(),
        getModelName(),
        getMaxSpeed(),
        getWeight()
      );
    };
  }
}
