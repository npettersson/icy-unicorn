package se.npet.trafiklab.buslines.adapters.outbound.db.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static se.npet.trafiklab.buslines.testutils.DbEntityTestUtils.DIRECTION_A;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.BusStopOnLine;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopOnLineEntity;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusRoute;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;
import se.npet.trafiklab.buslines.testutils.DbEntityTestUtils;

class BusLineConverterTest {

  private final BusLineConverter busLineConverter;

  public BusLineConverterTest() {
    DbMapper dbMapper = Mappers.getMapper(DbMapper.class);
    this.busLineConverter = new BusLineConverter(dbMapper);
  }

  @Test
  void toBusLine() {

    BusLineEntity busLineEntity = DbEntityTestUtils.makeBusLineEntity(42);

    List<BusStopEntity> busStopEntities = List.of(
        DbEntityTestUtils.makeBusStopEntity(1),
        DbEntityTestUtils.makeBusStopEntity(2),
        DbEntityTestUtils.makeBusStopEntity(3),
        DbEntityTestUtils.makeBusStopEntity(4),
        DbEntityTestUtils.makeBusStopEntity(5),
        DbEntityTestUtils.makeBusStopEntity(6)
    );

    List<BusStopOnLineEntity> busStopOnLineEntities = List.of(
        DbEntityTestUtils.makeBusStopOnLineEntity(1, 42, DbEntityTestUtils.DIRECTION_A),
        DbEntityTestUtils.makeBusStopOnLineEntity(2, 42, DbEntityTestUtils.DIRECTION_A),
        DbEntityTestUtils.makeBusStopOnLineEntity(3, 42, DbEntityTestUtils.DIRECTION_A),
        DbEntityTestUtils.makeBusStopOnLineEntity(4, 42, DbEntityTestUtils.DIRECTION_A),
        DbEntityTestUtils.makeBusStopOnLineEntity(5, 42, DbEntityTestUtils.DIRECTION_A),
        DbEntityTestUtils.makeBusStopOnLineEntity(6, 42, DbEntityTestUtils.DIRECTION_A),
        DbEntityTestUtils.makeBusStopOnLineEntity(6, 42, DbEntityTestUtils.DIRECTION_B),
        DbEntityTestUtils.makeBusStopOnLineEntity(4, 42, DbEntityTestUtils.DIRECTION_B),
        DbEntityTestUtils.makeBusStopOnLineEntity(3, 42, DbEntityTestUtils.DIRECTION_B),
        DbEntityTestUtils.makeBusStopOnLineEntity(2, 42, DbEntityTestUtils.DIRECTION_B),
        DbEntityTestUtils.makeBusStopOnLineEntity(1, 42, DbEntityTestUtils.DIRECTION_B)
    );

    BusLine busLine = busLineConverter.toBusLine(busLineEntity, busStopOnLineEntities, busStopEntities);
    assertThat(busLine).isNotNull();
    assertThat(busLine.getId()).isEqualTo(42);
    assertThat(busLine.getDesignation()).contains("42");
    assertThat(busLine.getExistsFrom()).isEqualTo(DbEntityTestUtils.DEFAULT_EXIST_FROM);

    assertThat(busLine.getBusRouteMap()).isNotEmpty().hasSize(2);

    BusRoute busRouteA = busLine.getBusRouteByDirection(RouteDirection.A);
    assertThat(busRouteA).isNotNull();
    assertThat(busRouteA.getDirection()).isEqualTo(RouteDirection.A);
    assertThat(busRouteA.getStops()).isNotEmpty().hasSize(6);

    BusRoute busRouteB = busLine.getBusRouteByDirection(RouteDirection.B);
    assertThat(busRouteB).isNotNull();
    assertThat(busRouteB.getDirection()).isEqualTo(RouteDirection.B);
    assertThat(busRouteB.getStops()).isNotEmpty().hasSize(5);
  }

  @Test
  void createBusRoute_withAllParams_shouldWorkOK() {

  }
}
