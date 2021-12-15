package se.npet.trafiklab.buslines.service;

import java.util.List;
import se.npet.trafiklab.buslines.domain.entities.BusLine;

public interface BusLinesService {

  BusLine getBusLineByLineId(String lineId);

  List<BusLine> getTopBusLinesByNumberOfStops(int limit);
}
