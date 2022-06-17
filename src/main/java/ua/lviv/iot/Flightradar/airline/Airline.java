package ua.lviv.iot.Flightradar;

public class Airline {
  public static final String ID_PROPERTY = "id";
  public static final String NAME_PROPERTY = "name";

  private int id;
  private String name;

  public Airline(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getID() {
    return id;
  }

  public String getName() {
    return name;
  }
}
