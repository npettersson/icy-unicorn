package se.npet.trafiklab.buslines.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusLineAndDirection {
  private final Integer busLineId;
  private final RouteDirection direction;

  public static BusLineAndDirection from(Integer busLineId, RouteDirection direction) {
    return new BusLineAndDirection(busLineId, direction);
  }
}
