package ua.lviv.iot.Flightradar;

public class RegistrationInformation {
  private final int id;
  private final int inventoryNumber;
  private final String modelName;
  // private final Date producedAt;
  private final int maxSpeed;
  private final int weight;

                                 // Date producedAt,
  public RegistrationInformation(int id, int inventoryNumber, String modelName,
                                 int maxSpeed, int weight) {
    this.id = id;
    this.inventoryNumber = inventoryNumber;
    this.modelName = modelName;
    // this.producedAt = producedAt;
    this.maxSpeed = maxSpeed;
    this.weight = weight;
  }

  public int getID() {
    return id;
  }
}
