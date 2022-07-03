package ua.lviv.iot.flightradar.util;

import java.util.Collections;
import java.util.Set;

public class IdCounter {
  public static int startCountingFrom(Set<Integer> ids) {
    if (ids.isEmpty()) {
      return 0;
    }

    return Collections.max(ids);
  }
}
