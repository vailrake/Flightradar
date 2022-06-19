package ua.lviv.iot.flightradar.airline;

public class Airline {
  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";

  private final int id;
  private final String name;

  @SuppressWarnings("checkstyle:HiddenField")
  public Airline(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}


