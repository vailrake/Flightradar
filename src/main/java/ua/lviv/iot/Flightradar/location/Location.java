package ua.lviv.iot.Flightradar;

public class Location {
  public static final String ID_PROPERTY = "id";
  public static final String LATITUDE_PROPERTY = "latitude";
  public static final String LONGITUDE_PROPERTY = "longitude";

  private final int id;
  private final double latitude;
  private final double longitude;

  public Location(int id, double latitude, double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public int getID() {
    return id;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
