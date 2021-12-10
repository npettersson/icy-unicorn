package se.npet.trafiklab.icyunicorn.api.buslines.v1;

import org.mapstruct.Mapper;
import se.npet.trafiklab.icyunicorn.api.buslines.v1.model.BusLineDto;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;

@Mapper(componentModel = "spring")
public interface BusLinesV1ApiMapper {

  BusLineDto toBusLinesDto(BusLine busLine);

}
