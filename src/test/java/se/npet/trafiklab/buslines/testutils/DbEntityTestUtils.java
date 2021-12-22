package se.npet.trafiklab.buslines.testutils;

import java.time.LocalDate;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopOnLineEntity;

public final class DbEntityTestUtils {

  public static BusLineEntity makeBusLineEntity(Integer busLineId) {
    return new BusLineEntity(busLineId, "Bus " + busLineId, DEFAULT_EXIST_FROM);
  }

  public static BusStopEntity makeBusStopEntity(Integer busStopId) {
    return new BusStopEntity(busStopId, busStopId, "Bus stop " + busStopId, NORTH_COORD, EAST_COORD, ZONE, DEFAULT_EXIST_FROM);
  }

  public static BusStopOnLineEntity makeBusStopOnLineEntity(Integer busStopId, Integer busLineId, String direction) {
    return new BusStopOnLineEntity(busLineId, busStopId, direction, busStopId, DEFAULT_EXIST_FROM);
  }

  public static final String NORTH_COORD = "SOME NORTH COORD";
  public static final String EAST_COORD = "SOME EAST COORD";
  public static final String ZONE = "A";
  public static final String DIRECTION_A = "A";
  public static final String DIRECTION_B = "B";
  public static final LocalDate DEFAULT_EXIST_FROM = LocalDate.of(2021, 1, 1);

}
