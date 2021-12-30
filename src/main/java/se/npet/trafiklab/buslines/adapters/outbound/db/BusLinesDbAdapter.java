package se.npet.trafiklab.buslines.adapters.outbound.db;

import static se.npet.trafiklab.buslines.adapters.outbound.db.jooq.Tables.BUS_LINE;
import static se.npet.trafiklab.buslines.adapters.outbound.db.jooq.Tables.BUS_STOP;
import static se.npet.trafiklab.buslines.adapters.outbound.db.jooq.Tables.BUS_STOP_ON_LINE;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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

    Assert.notNull(busLineId, "Bus Line id can not be null");

    Result<Record> recordResult = dslContext.select()
        .from(BUS_LINE)
        .join(BUS_STOP_ON_LINE).on(BUS_STOP_ON_LINE.BUS_LINE_ID.eq(BUS_LINE.ID))
        .join(BUS_STOP).on(BUS_STOP.ID.eq(BUS_STOP_ON_LINE.BUS_STOP_ID))
        .where(BUS_LINE.ID.eq(busLineId))
        .fetch();

    BusLineEntity busLineEntity = recordResult.into(BUS_LINE).into(BusLineEntity.class).stream()
        .findAny()
        .orElseThrow(() -> new IllegalStateException("Bus line with id <" + busLineId + "> not found"));
    List<BusStopOnLineEntity> busStopOnLineEntities = recordResult.into(BUS_STOP_ON_LINE).into(BusStopOnLineEntity.class).stream()
        .distinct()
        .collect(Collectors.toList());
    List<BusStopEntity> busStopEntities = recordResult.into(BUS_STOP).into(BusStopEntity.class).stream()
        .distinct()
        .collect(Collectors.toList());

    return busLineConverter.toBusLine(busLineEntity, busStopOnLineEntities, busStopEntities);
  }

  @Override
  public List<BusLine> getBusLinesWithMostStops(Integer fetchLimit) {

    Set<Integer> busLineIds = dslContext.select(BUS_STOP_ON_LINE.BUS_LINE_ID)
        .from(BUS_STOP_ON_LINE)
        .groupBy(BUS_STOP_ON_LINE.BUS_LINE_ID)
        .orderBy(DSL.count().desc())
        .limit(fetchLimit)
        .fetchStreamInto(Integer.class)
        .collect(Collectors.toSet());

    Result<Record> recordResult = dslContext.select()
        .from(BUS_LINE)
        .join(BUS_STOP_ON_LINE).on(BUS_STOP_ON_LINE.BUS_LINE_ID.eq(BUS_LINE.ID))
        .join(BUS_STOP).on(BUS_STOP.ID.eq(BUS_STOP_ON_LINE.BUS_STOP_ID))
        .where(BUS_LINE.ID.in(busLineIds))
        .fetch();

    Map<Integer, BusLineEntity> busLineEntityMap = recordResult.into(BUS_LINE).into(BusLineEntity.class).stream()
        .distinct()
        .collect(Collectors.toMap(BusLineEntity::getId, Function.identity()));

    Map<Integer, List<BusStopOnLineEntity>> busStopOnLineEntitiesMap = recordResult.into(BUS_STOP_ON_LINE).into(BusStopOnLineEntity.class)
        .stream()
        .distinct()
        .collect(Collectors.groupingBy(BusStopOnLineEntity::getBusLineId));

    Map<Integer, BusStopEntity> busStopEntityMap = recordResult.into(BUS_STOP).into(BusStopEntity.class).stream()
        .distinct()
        .collect(Collectors.toMap(BusStopEntity::getId, Function.identity()));

    return busLineConverter.toBusLines(busLineEntityMap, busStopOnLineEntitiesMap, busStopEntityMap);
  }
}
