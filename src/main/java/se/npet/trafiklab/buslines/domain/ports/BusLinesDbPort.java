package se.npet.trafiklab.buslines.domain.ports;

import java.util.Collection;
import java.util.Set;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;

public interface BusLinesDbPort {

  void persistBusLines(Collection<BusLine> busLines);

  void persistBusStops(Collection<BusStop> busStops);

  void persistBusStopOnLine(Collection<BusStopOnLine> busStopSet);
}