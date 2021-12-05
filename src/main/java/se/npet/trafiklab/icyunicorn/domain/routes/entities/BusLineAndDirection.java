package se.npet.trafiklab.icyunicorn.domain.routes.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusLineAndDirection {
  private final String lineId;
  private final RouteDirection direction;

  public static BusLineAndDirection from(String lineId, RouteDirection direction) {
    return new BusLineAndDirection(lineId, direction);
  }
}
