package se.npet.trafiklab.icyunicorn.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class BusLine {

  private final String lineId;
  private final String designation;
  private final LocalDate existsFrom;
  private final Map<RouteDirection, BusRoute> busRouteMap = new HashMap<>();
  private long numberOfDistinctStops = 0;

  public void addRoute(BusRoute busRoute) {
    this.busRouteMap.put(busRoute.getDirection(), busRoute);
    this.numberOfDistinctStops = calcNumberOfDistinctStops(this.busRouteMap.values());
  }

  public List<BusRoute> getBusRoutes() {
    return new ArrayList<>(this.busRouteMap.values());
  }

  public BusRoute getBusRouteByDirection(RouteDirection routeDirection) {
    return this.busRouteMap.get(routeDirection);
  }

  long calcNumberOfDistinctStops(Collection<BusRoute> busRoutes) {
    return busRoutes.stream()
        .flatMap(route -> route.getStops().stream())
        .map(BusStop::getStopAreaId)
        .distinct()
        .count();
  }
}
