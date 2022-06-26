package ua.lviv.iot.flightradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightradarApplication {
  public static void main(String[] args) {
    // TODO read from files and populate cache
    SpringApplication.run(FlightradarApplication.class, args);
  }
}
