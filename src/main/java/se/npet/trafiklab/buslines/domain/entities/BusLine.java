package se.npet.trafiklab.buslines.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import lombok.Data;

@Data
public class BusLine {

  private final Integer id;
  private final String designation;
  private final LocalDate existsFrom;
  private final EnumMap<RouteDirection, BusRoute> busRouteMap = new EnumMap<>(RouteDirection.class);
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
