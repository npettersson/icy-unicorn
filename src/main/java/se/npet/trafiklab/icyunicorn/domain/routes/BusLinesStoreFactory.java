package se.npet.trafiklab.icyunicorn.domain.routes;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.TrafiklabAdapterImpl;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusLineAndDirection;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusRoute;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.RouteDirection;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStopOnLine;

@Component
public class BusLinesStoreFactory {

  private final TrafiklabAdapterImpl trafficAdapter;

  public BusLinesStoreFactory(TrafiklabAdapterImpl trafficAdapter) {
    this.trafficAdapter = trafficAdapter;
  }

  public BusLinesStore createBusLinesStore() {
    return null;
  }

  public BusLinesStore createBusLinesStore(List<BusLine> busLines, List<BusStop> busStops, List<BusStopOnLine> busStopOnLines) {

    Map<String, BusLine> busLineMap = busLines.stream()
        .collect(Collectors.toMap(BusLine::getLineId, Function.identity()));

    Map<String, BusStop> busStopMap = busStops.stream().collect(Collectors.toMap(BusStop::getStopPointId, Function.identity()));

    Map<BusLineAndDirection, List<BusStopOnLine>> busLineAndDirectionListMap = busStopOnLines.stream()
        .collect(Collectors.groupingBy(BusStopOnLine::getBusLineAndDirection));

    busLineAndDirectionListMap.forEach((key, value) -> {
      BusRoute busRoute = createBusRoute(key.getDirection(), value, busStopMap);
      busLineMap.get(key.getLineId()).addRoute(busRoute);
    });

    return new BusLinesStore(busLineMap);
  }

  BusRoute createBusRoute(RouteDirection routeDirection, List<BusStopOnLine> busStopsOnLine, Map<String, BusStop> busStopMap) {
    List<BusStop> busStops = busStopsOnLine.stream()
        .map(busStopOnLine -> busStopMap.get(busStopOnLine.getStopPointId()))
        .collect(Collectors.toList());

    return new BusRoute(routeDirection, busStops);
  }
}
