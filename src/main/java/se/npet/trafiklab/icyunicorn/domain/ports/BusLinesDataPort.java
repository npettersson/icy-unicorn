package se.npet.trafiklab.icyunicorn.domain.ports;

import java.util.List;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStopOnLine;

public interface BusLinesDataPort {

  List<BusLine> getBusLines();

  List<BusStop> getBusStops();

  List<BusStopOnLine> getBusStopsOnLines();
}
