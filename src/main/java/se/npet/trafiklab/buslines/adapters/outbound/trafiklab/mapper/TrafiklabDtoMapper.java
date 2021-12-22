package se.npet.trafiklab.buslines.adapters.outbound.trafiklab.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusLineDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;

@Mapper(componentModel = "spring")
public interface TrafiklabDtoMapper {

  default List<BusLine> mapBusLines(List<BusLineDto> busLineDtos) {
    return busLineDtos.stream()
        .map(this::toBusLine)
        .collect(Collectors.toList());
  }

  @Mapping(source = "lineNumber", target = "id")
  @Mapping(source = "lineDesignation", target = "designation")
  @Mapping(source = "existsFromDate", target = "existsFrom")
  @Mapping(target = "busRoutes", ignore = true)
  @Mapping(target = "numberOfDistinctStops", ignore = true)
  BusLine toBusLine(BusLineDto busLineDto);

  default List<BusStop> mapBusStops(List<BusStopDto> busStopDtos) {
    return busStopDtos.stream()
        .map(this::toBusStop)
        .collect(Collectors.toList());
  }

  @Mapping(source = "stopPointNumber", target = "id")
  @Mapping(source = "stopAreaNumber", target = "stopAreaId")
  @Mapping(source = "stopPointName", target = "stopPointName")
  @Mapping(source = "locationNorthingCoordinate", target = "northingCoord")
  @Mapping(source = "locationEastingCoordinate", target = "eastingCoord")
  @Mapping(source = "zoneShortName", target = "zone")
  @Mapping(source = "existsFromDate", target = "existsFrom")
  BusStop toBusStop(BusStopDto busStopDto);

  default List<BusStopOnLine> mapBusStopsOnLines(List<BusStopOnLineDto> busStopOnLineDtos) {
    BusStopsOnLinesOrderDecorator orderDecorator = new BusStopsOnLinesOrderDecorator();
    return busStopOnLineDtos.stream()
        .map(dto -> toBusStopOnLine(dto, orderDecorator))
        .collect(Collectors.toList());
  }

  @Mapping(source = "lineNumber", target = "busLineId")
  @Mapping(source = "journeyPatternPointNumber", target = "busStopId")
  @Mapping(source = "directionCode", target = "routeDirection")
  @Mapping(source = "existsFromDate", target = "existsFrom")
  @Mapping(target = "order", ignore = true)
  BusStopOnLine toBusStopOnLine(BusStopOnLineDto busStopOnLineDto, @Context BusStopsOnLinesOrderDecorator orderDecorator);

  default RouteDirection toRouteDirection(String directionCode) {
    if ( directionCode == null ) {
      return null;
    }

    return switch (directionCode) {
      case "1" -> RouteDirection.A;
      case "2" -> RouteDirection.B;
      default -> throw new IllegalArgumentException("Unexpected enum constant: " + directionCode);
    };
  }

  // Dates are in format "2007-08-24 00:00:00.000"
  default LocalDate toLocalDateFromString(String dateStr) {
    return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
  }
}
