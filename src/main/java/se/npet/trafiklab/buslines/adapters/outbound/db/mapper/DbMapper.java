package se.npet.trafiklab.buslines.adapters.outbound.db.mapper;

import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusLineEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopEntity;
import se.npet.trafiklab.buslines.adapters.outbound.db.jooq.tables.pojos.BusStopOnLineEntity;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;


@Mapper(componentModel = "spring")
public interface DbMapper {

  Collection<BusLineEntity> toBusLineDbPojos(Collection<se.npet.trafiklab.buslines.domain.entities.BusLine> busLines);

  @Mapping(target = "id", source = "lineId")
  BusLineEntity toBusLineDbPojo(se.npet.trafiklab.buslines.domain.entities.BusLine busLineDomainEntity);

  Collection<BusStopEntity> toBusStopDbPojos(Collection<se.npet.trafiklab.buslines.domain.entities.BusStop> busStops);

  @Mapping(target = "id", source = "stopPointId")
  @Mapping(target = "name", source = "stopPointName")
  @Mapping(target = "northCoord", source = "northingCoord")
  @Mapping(target = "eastCoord", source = "eastingCoord")
  BusStopEntity toBusStopDbPojo(se.npet.trafiklab.buslines.domain.entities.BusStop busStopDomainEntity);

  Collection<BusStopOnLineEntity> toBusStopOnLineDbPojos(Collection<se.npet.trafiklab.buslines.domain.entities.BusStopOnLine> busStopSet);

  @Mapping(target = "busLineId", source = "lineId")
  @Mapping(target = "busStopId", source = "stopPointId")
  @Mapping(target = "direction", source = "routeDirection")
  @Mapping(target = "stopOrder", source = "order")
  BusStopOnLineEntity toBusStopOnLineDbPojo(se.npet.trafiklab.buslines.domain.entities.BusStopOnLine busStopOnLineDomainEntity);

  @Mapping(target = "lineId", source = "busLineId")
  @Mapping(target = "stopPointId", source = "busStopId")
  @Mapping(target = "routeDirection", source = "direction")
  @Mapping(target = "order", source = "stopOrder")
  BusStopOnLine toBusStopOnLine(BusStopOnLineEntity busStopOnLineEntity);

  RouteDirection toRouteDirection(String routeDirectionString);
}
