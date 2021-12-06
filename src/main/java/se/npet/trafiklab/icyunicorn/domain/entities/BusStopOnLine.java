package se.npet.trafiklab.icyunicorn.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusStopOnLine {
  private String lineId;
  private String stopPointId;
  private RouteDirection routeDirection;
  private int order;

  public BusStopOnLine(String lineId, String stopPointId, RouteDirection routeDirection) {
    this.lineId = lineId;
    this.stopPointId = stopPointId;
    this.routeDirection = routeDirection;
  }

  public BusLineAndDirection getBusLineAndDirection() {
    return BusLineAndDirection.from(lineId, routeDirection);
  }
}
