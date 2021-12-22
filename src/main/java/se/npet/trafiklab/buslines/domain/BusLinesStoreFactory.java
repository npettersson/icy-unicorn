package se.npet.trafiklab.buslines.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusLineAndDirection;
import se.npet.trafiklab.buslines.domain.entities.BusRoute;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;
import se.npet.trafiklab.buslines.domain.ports.BusLinesDataPort;

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

    Map<Integer, BusLine> busLineMap = prepareBusLinesMap(busLines);

    Map<Integer, BusStop> busStopMap = busStops.stream().collect(Collectors.toMap(BusStop::getId, Function.identity()));

    // Group all bus stop points according to line and direction
    Map<BusLineAndDirection, List<BusStopOnLine>> busLineAndDirectionListMap = busStopOnLines.stream()
        .collect(Collectors.groupingBy(BusStopOnLine::getBusLineAndDirection));

    // Process the bus stop point map and create route for each direction, add the routes to the bus line
    busLineAndDirectionListMap.forEach((key, value) -> {
      BusRoute busRoute = createBusRoute(key.getDirection(), value, busStopMap);
      busLineMap.get(key.getBusLineId()).addRoute(busRoute);
    });

    return new BusLinesStore(busLineMap);
  }

  BusRoute createBusRoute(RouteDirection routeDirection, List<BusStopOnLine> busStopsOnLine, Map<Integer, BusStop> busStopMap) {
    List<BusStop> busStopsOnRoute = busStopsOnLine.stream()
        .sorted(Comparator.comparing(BusStopOnLine::getOrder))
        .map(busStopOnLine -> {
          BusStop busStop = busStopMap.get(busStopOnLine.getBusStopId());
          // Data quality problem, some stops doesn't exist in the bus stop data set
          if (busStop == null) {
            log.warn("Could not find bus stop with id <{}> when creating route <{}> on line <{}>, ", routeDirection,
                busStopOnLine.getBusLineId(), busStopOnLine.getBusStopId());
          }
          return busStop;
        }).filter(Objects::nonNull)
        .collect(Collectors.toList());

    return new BusRoute(routeDirection, busStopsOnRoute);
  }

  Map<Integer, BusLine> prepareBusLinesMap(List<BusLine> busLines) {
    Map<Integer, BusLine> busLinesMap = new TreeMap<>();
    // Data quality problem, duplicate lines exist in data set, make sure to include only the most current bus line
    busLines.forEach(busLine -> busLinesMap.merge(busLine.getId(), busLine,
        (currLine, newLine) -> (newLine.getExistsFrom().isAfter(currLine.getExistsFrom())) ? newLine : currLine));
    return busLinesMap;
  }
}
