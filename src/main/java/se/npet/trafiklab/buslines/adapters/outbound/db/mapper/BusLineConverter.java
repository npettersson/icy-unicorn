package se.npet.trafiklab.buslines.adapters.outbound.db.mapper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopOnLineEntity;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusRoute;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;

@Slf4j
public class BusLineConverter {

  private final DbMapper mapper;

  public BusLineConverter(DbMapper mapper) {
    this.mapper = mapper;
  }

  public BusLine toBusLine(BusLineEntity busLineEntity, List<BusStopOnLineEntity> busStopOnLineEntities, List<BusStopEntity> busStopEntities) {

    // Make map with bus stops on this line
    Map<Integer, BusStop> busStopMap = busStopEntities.stream()
        .map(mapper::toBusStop)
        .collect(Collectors.toMap(BusStop::getId, Function.identity()));

    return toBusLine(busLineEntity, busStopOnLineEntities, busStopMap);
  }

  public List<BusLine> toBusLines(Map<Integer, BusLineEntity> busLineEntityMap,
      Map<Integer, List<BusStopOnLineEntity>> busStopOnLineEntitiesMap, Map<Integer, BusStopEntity> busStopEntityMap) {

    // Convert BusStopEntity to BusStop in map
    Map<Integer, BusStop> busStopMap = busStopEntityMap.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey, e -> mapper.toBusStop(e.getValue())));

    return busLineEntityMap.values().stream()
        .map(busLineEntity -> {
          List<BusStopOnLineEntity> busStopOnLineEntities = busStopOnLineEntitiesMap.get(busLineEntity.getId());
          return toBusLine(busLineEntity, busStopOnLineEntities, busStopMap);
        })
        .sorted(Comparator.comparing(BusLine::getNumberOfDistinctStops).reversed())
        .collect(Collectors.toList());
  }

  BusLine toBusLine(BusLineEntity busLineEntity, List<BusStopOnLineEntity> busStopOnLineEntities, Map<Integer, BusStop> busStopMap) {

    BusLine busLine = mapper.toBusLine(busLineEntity);

    // Group stop point on line by direction
    Map<RouteDirection, List<BusStopOnLine>> busStopOnLineEntityMap = busStopOnLineEntities.stream()
        .map(mapper::toBusStopOnLine)
        .collect(Collectors.groupingBy(BusStopOnLine::getRouteDirection));

    // Process the bus stop point map and create route for each direction, add the routes to the bus line
    busStopOnLineEntityMap.forEach((key, value) -> {
      BusRoute busRoute = createBusRoute(key, value, busStopMap);
      busLine.addRoute(busRoute);
    });

    return busLine;
  }

  BusRoute createBusRoute(RouteDirection routeDirection, List<BusStopOnLine> busStopsOnLine, Map<Integer, BusStop> busStopMap) {
    List<BusStop> busStopsOnRoute = busStopsOnLine.stream()
        .sorted(Comparator.comparing(BusStopOnLine::getOrder))
        .map(busStopOnLine -> {
          BusStop busStop = busStopMap.get(busStopOnLine.getBusStopId());
          if (busStop == null) {
            log.warn("Could not find bus stop with id <{}> when creating route <{}> on line <{}>, ", routeDirection,
                busStopOnLine.getBusLineId(), busStopOnLine.getBusStopId());
          }
          return busStop;
        }).filter(Objects::nonNull)
        .collect(Collectors.toList());

    return new BusRoute(routeDirection, busStopsOnRoute);
  }
}
