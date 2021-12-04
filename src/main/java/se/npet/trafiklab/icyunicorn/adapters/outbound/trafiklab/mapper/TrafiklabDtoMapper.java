package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusLineDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.icyunicorn.domain.BusLine;
import se.npet.trafiklab.icyunicorn.domain.BusStop;
import se.npet.trafiklab.icyunicorn.domain.BusStopOnLine;

@Mapper(componentModel = "spring")
public interface TrafiklabDtoMapper {

  @Mapping(source = "lineNumber", target = "lineId")
  BusLine toBusLine(BusLineDto busLineDto);

  @Mapping(source = "stopPointNumber", target = "stopPointId")
  @Mapping(source = "stopPointName", target = "stopPointName")
  @Mapping(source = "locationNorthingCoordinate", target = "northingCoord")
  @Mapping(source = "locationEastingCoordinate", target = "eastingCoord")
  @Mapping(source = "zoneShortName", target = "zone")
  BusStop toBusStop(BusStopDto busStopDto);

  default List<BusStopOnLine> mapBusStopsOnLines(List<BusStopOnLineDto> busStopOnLineDtos) {
    BusStopsOnLinesOrderContext orderContext = new BusStopsOnLinesOrderContext();
    return busStopOnLineDtos.stream()
        .map(dto -> toBusStopOnLine(dto, orderContext))
        .collect(Collectors.toList());
  }

  @Mapping(source = "lineNumber", target = "lineId")
  @Mapping(source = "journeyPatternPointNumber", target = "stopPointId")
  @Mapping(target = "order", ignore = true)
  BusStopOnLine toBusStopOnLine(BusStopOnLineDto busStopOnLineDto, @Context BusStopsOnLinesOrderContext orderContext);
}
