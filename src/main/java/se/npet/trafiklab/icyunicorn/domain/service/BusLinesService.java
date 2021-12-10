package se.npet.trafiklab.icyunicorn.domain.service;

import java.util.List;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;

public interface BusLinesService {

  BusLine getBusLineByLineId(String lineId);

  List<BusLine> getTopBusLinesByNumberOfStops(int limit);
}
