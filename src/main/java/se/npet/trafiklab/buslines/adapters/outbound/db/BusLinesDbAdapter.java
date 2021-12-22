package se.npet.trafiklab.buslines.adapters.outbound.db;

import static se.npet.trafiklab.buslines.adapters.outbound.db.jooq.Tables.BUS_STOP;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos.BusLineDao;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos.BusStopDao;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.daos.BusStopOnLineDao;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopOnLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.mapper.BusLineConverter;
import se.npet.trafiklab.buslines.adapters.outbound.db.mapper.DbMapper;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;

@Slf4j
@Component
public class BusLinesDbAdapter implements se.npet.trafiklab.buslines.domain.ports.BusLinesDbPort {

  private final DSLContext dslContext;
  private final DbMapper mapper;

  private final BusLineDao busLineDao;
  private final BusStopDao busStopDao;
  private final BusStopOnLineDao busStopOnLineDao;
  private final BusLineConverter busLineConverter;

  public BusLinesDbAdapter(DSLContext dslContext, Configuration jooqConfig, DbMapper dbMapper) {
    this.dslContext = dslContext;
    this.mapper = dbMapper;
    this.busLineConverter = new BusLineConverter(dbMapper);
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

    BusLineEntity busLineEntity = Optional.ofNullable(busLineDao.fetchOneById(busLineId))
        .orElseThrow(() -> new IllegalStateException("Data not found"));

    // Fetch all bus stop points on this bus line
    List<BusStopOnLineEntity> busStopOnLineEntities = busStopOnLineDao.fetchByBusLineId(busLineEntity.getId());

    Set<Integer> busStopIds = busStopOnLineEntities.stream()
        .map(BusStopOnLineEntity::getBusStopId)
        .collect(Collectors.toSet());

    // Fetch all bus stops present on this line
    List<BusStopEntity> busStopEntities = dslContext.selectFrom(BUS_STOP)
        .where(BUS_STOP.ID.in(busStopIds))
        .fetchStreamInto(BusStopEntity.class)
        .collect(Collectors.toList());

    return busLineConverter.toBusLine(busLineEntity, busStopOnLineEntities, busStopEntities);
  }
}
