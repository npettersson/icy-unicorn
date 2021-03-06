package se.npet.trafiklab.buslines.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusLineAndDirection {
  private final String lineId;
  private final RouteDirection direction;

  public static BusLineAndDirection from(String lineId, RouteDirection direction) {
    return new BusLineAndDirection(lineId, direction);
  }
}
