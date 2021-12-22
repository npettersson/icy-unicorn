package se.npet.trafiklab.buslines.adapters.outbound.db;

import static se.npet.trafiklab.buslines.adapters.outbound.db.jooq.Tables.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.Tables;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos.BusLineDao;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos.BusStopDao;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos.BusStopOnLineDao;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopOnLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.mapper.DbMapper;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusRoute;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;

@Component
public class BusLinesDbAdapter implements se.npet.trafiklab.buslines.domain.ports.BusLinesDbPort {

  private final DSLContext dslContext;
  private final DbMapper mapper;

  private final BusLineDao busLineDao;
  private final BusStopDao busStopDao;
  private final BusStopOnLineDao busStopOnLineDao;

  public BusLinesDbAdapter(DSLContext dslContext, Configuration jooqConfig, DbMapper dbMapper) {
    this.dslContext = dslContext;
    this.mapper = dbMapper;
    this.busLineDao = new BusLineDao(jooqConfig);
    this.busStopDao = new BusStopDao(jooqConfig);
    this.busStopOnLineDao = new BusStopOnLineDao(jooqConfig);
  }

  @Override
  public void persistBusLines(Collection<BusLine> busLines) {
    busLineDao.insert(mapper.toBusLineDbPojos(busLines));
  }

  @Override
  public void persistBusStops(Collection<BusStop> busStops) {
    busStopDao.insert(mapper.toBusStopDbPojos(busStops));
  }

  @Override
  public void persistBusStopOnLine(Collection<BusStopOnLine> busStopSet) {
    busStopOnLineDao.insert(mapper.toBusStopOnLineDbPojos(busStopSet));
  }

  public BusLine fetchBusLineById(Integer busLineId) {

    BusLineEntity busLineEntity = busLineDao.fetchOneById(busLineId);

    List<BusStopOnLine> busStopOnLineEntities = busStopOnLineDao.fetchByBusLineId(busLineEntity.getId()).stream()
        .map(mapper::toBusStopOnLine)
        .collect(Collectors.toList());

    Map<RouteDirection, List<BusStopOnLine>> busStopOnLineEntityMap = busStopOnLineEntities.stream()
        .collect(Collectors.groupingBy(BusStopOnLine::getRouteDirection));

    Set<String> busStopIds = busStopOnLineEntityMap.values()
        .stream()
        .flatMap(Collection::stream)
        .map(BusStopOnLine::getStopPointId)
        .collect(Collectors.toSet());

    Map<Integer, BusStopEntity> busStopEntityMap = dslContext.selectFrom(BUS_STOP)
        .where(BUS_STOP.ID.in(busStopIds))
        .collect(Collectors.mapping(r -> r.into(BusStopEntity.class), Collectors.toMap(BusStopEntity::getId, Function.identity())));


    return null;
  }

  BusRoute createBusRoute(RouteDirection routeDirection, List<BusStopOnLine> busStopsOnLine, Map<String, BusStop> busStopMap) {
    List<BusStop> busStopsOnRoute = busStopsOnLine.stream()
        .sorted(Comparator.comparing(BusStopOnLine::getOrder))
        .map(busStopOnLine -> {
          BusStop busStop = busStopMap.get(busStopOnLine.getStopPointId());
          // Data quality problem, some stops doesn't exist in the bus stop data set
          if (busStop == null) {
            log.warn("Could not find bus stop with id <{}> when creating route <{}> on line <{}>, ", routeDirection,
                busStopOnLine.getLineId(), busStopOnLine.getStopPointId());
          }
          return busStop;
        }).filter(Objects::nonNull)
        .collect(Collectors.toList());

    return new BusRoute(routeDirection, busStopsOnRoute);
  }
}
