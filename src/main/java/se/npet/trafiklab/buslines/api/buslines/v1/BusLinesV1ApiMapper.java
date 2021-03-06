package se.npet.trafiklab.buslines.api.buslines.v1;

import org.mapstruct.Mapper;
import se.npet.trafiklab.buslines.api.buslines.v1.model.BusLineAndNbrOfStopsDto;
import se.npet.trafiklab.buslines.api.buslines.v1.model.BusLineDto;
import se.npet.trafiklab.buslines.domain.entities.BusLine;

@Mapper(componentModel = "spring")
public interface BusLinesV1ApiMapper {

  BusLineDto toBusLinesDto(BusLine busLine);

  BusLineAndNbrOfStopsDto toBusLineAndNbrOfStopsDto(BusLine busLine);
}
