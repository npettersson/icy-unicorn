package se.npet.trafiklab.icyunicorn.domain;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.icyunicorn.domain.ports.BusLinesDataPort;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLineAndDirection;
import se.npet.trafiklab.icyunicorn.domain.entities.BusRoute;
import se.npet.trafiklab.icyunicorn.domain.entities.RouteDirection;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStopOnLine;

@Slf4j
@Component
public class BusLinesStoreFactory {

  private final BusLinesDataPort busLinesDataPort;

  public BusLinesStoreFactory(BusLinesDataPort busLinesDataPort) {
    this.busLinesDataPort = busLinesDataPort;
  }

  public BusLinesStore createBusLinesStore() {
    log.info("Creating BusLinesStore");
    List<BusLine> busLines = busLinesDataPort.getBusLines();
    log.info("Got <{}> bus lines", busLines.size());
    List<BusStop> busStops = busLinesDataPort.getBusStops();
    log.info("Got <{}> bus stops", busStops.size());
    List<BusStopOnLine> busStopsOnLines = busLinesDataPort.getBusStopsOnLines();
    log.info("Got <{}> bus stops on lines", busStopsOnLines.size());
    return createBusLinesStore(busLines, busStops, busStopsOnLines);
  }

  BusLinesStore createBusLinesStore(List<BusLine> busLines, List<BusStop> busStops, List<BusStopOnLine> busStopOnLines) {

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
