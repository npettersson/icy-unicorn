package se.npet.trafiklab.buslines.domain.ports;

import java.util.List;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;

public interface BusLinesDataPort {

  List<BusLine> getBusLines();

  List<BusStop> getBusStops();

  List<BusStopOnLine> getBusStopsOnLines();
}
