package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusLineDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStopOnLine;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.RouteDirection;

@Mapper(componentModel = "spring")
public interface TrafiklabDtoMapper {

  @Mapping(source = "lineNumber", target = "lineId")
  @Mapping(target = "routes", ignore = true)
  BusLine toBusLine(BusLineDto busLineDto);

  @Mapping(source = "stopPointNumber", target = "stopPointId")
  @Mapping(source = "stopPointName", target = "stopPointName")
  @Mapping(source = "locationNorthingCoordinate", target = "northingCoord")
  @Mapping(source = "locationEastingCoordinate", target = "eastingCoord")
  @Mapping(source = "zoneShortName", target = "zone")
  BusStop toBusStop(BusStopDto busStopDto);

  default List<BusStopOnLine> mapBusStopsOnLines(List<BusStopOnLineDto> busStopOnLineDtos) {
    BusStopsOnLinesOrderDecorator orderDecorator = new BusStopsOnLinesOrderDecorator();
    return busStopOnLineDtos.stream()
        .map(dto -> toBusStopOnLine(dto, orderDecorator))
        .collect(Collectors.toList());
  }

  @Mapping(source = "lineNumber", target = "lineId")
  @Mapping(source = "journeyPatternPointNumber", target = "stopPointId")
  @Mapping(source = "directionCode", target = "routeDirection")
  @Mapping(target = "order", ignore = true)
  BusStopOnLine toBusStopOnLine(BusStopOnLineDto busStopOnLineDto, @Context BusStopsOnLinesOrderDecorator orderDecorator);

  default RouteDirection toRouteDirection(String directionCode) {
    if ( directionCode == null ) {
      return null;
    }

    return switch (directionCode) {
      case "0" -> RouteDirection.A;
      case "1" -> RouteDirection.B;
      default -> throw new IllegalArgumentException("Unexpected enum constant: " + directionCode);
    };
  }
}
