package se.npet.trafiklab.buslines.adapters.outbound.db.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;
import se.npet.trafiklab.buslines.testutils.DbEntityTestUtils;

class DbMapperTest {

  private final DbMapper dbMapper = Mappers.getMapper(DbMapper.class);

  @Test
  void toBusStopOnLine() {
    BusStopOnLine busStopOnLine = dbMapper.toBusStopOnLine(DbEntityTestUtils.makeBusStopOnLineEntity(4, 42, DbEntityTestUtils.DIRECTION_A));
    assertThat(busStopOnLine).isNotNull();
    assertThat(busStopOnLine.getBusLineId()).isEqualTo(42);
    assertThat(busStopOnLine.getBusStopId()).isEqualTo(4);
    assertThat(busStopOnLine.getRouteDirection()).isEqualTo(RouteDirection.A);
    assertThat(busStopOnLine.getExistsFrom()).isEqualTo(DbEntityTestUtils.DEFAULT_EXIST_FROM);
  }

  @Test
  void toRouteDirection() {
    RouteDirection routeDirection = dbMapper.toRouteDirection(DbEntityTestUtils.DIRECTION_A);
    assertThat(routeDirection).isNotNull().isEqualTo(RouteDirection.A);
  }

  @Test
  void toBusStop() {
    BusStop busStop = dbMapper.toBusStop(DbEntityTestUtils.makeBusStopEntity(4));
    assertThat(busStop).isNotNull();
    assertThat(busStop.getId()).isEqualTo(4);
    assertThat(busStop.getStopAreaId()).isEqualTo(4);
    assertThat(busStop.getStopPointName()).containsIgnoringCase("bus stop 4");
    assertThat(busStop.getZone()).isEqualTo(DbEntityTestUtils.ZONE);
    assertThat(busStop.getNorthingCoord()).isEqualTo(DbEntityTestUtils.NORTH_COORD);
    assertThat(busStop.getEastingCoord()).isEqualTo(DbEntityTestUtils.EAST_COORD);
    assertThat(busStop.getExistsFrom()).isEqualTo(DbEntityTestUtils.DEFAULT_EXIST_FROM);
  }

  @Test
  void toBusLine() {
    BusLine busLine = dbMapper.toBusLine(DbEntityTestUtils.makeBusLineEntity(42));
    assertThat(busLine).isNotNull();
    assertThat(busLine.getId()).isEqualTo(42);
    assertThat(busLine.getDesignation()).contains("42");
    assertThat(busLine.getExistsFrom()).isEqualTo(DbEntityTestUtils.DEFAULT_EXIST_FROM);
  }
}
