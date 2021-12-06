package se.npet.trafiklab.icyunicorn.domain.ports;

import java.util.List;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStopOnLine;

public interface BusLinesDataPort {

  List<BusLine> getBusLines();

  List<BusStop> getBusStops();

  List<BusStopOnLine> getBusStopsOnLines();
}
