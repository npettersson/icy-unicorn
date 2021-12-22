package se.npet.trafiklab.buslines.domain.entities;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusStopOnLine {
  private Integer busLineId;
  private Integer busStopId;
  private RouteDirection routeDirection;
  private LocalDate existsFrom;
  private int order;

  public BusStopOnLine(Integer busLineId, Integer busStopId, RouteDirection routeDirection) {
    this.busLineId = busLineId;
    this.busStopId = busStopId;
    this.routeDirection = routeDirection;
  }

  public BusLineAndDirection getBusLineAndDirection() {
    return BusLineAndDirection.from(busLineId, routeDirection);
  }
}
