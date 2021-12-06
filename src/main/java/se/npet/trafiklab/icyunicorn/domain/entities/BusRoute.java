package se.npet.trafiklab.icyunicorn.domain.entities;

import java.util.List;
import lombok.Data;

@Data
public class BusRoute {
  private final RouteDirection direction;
  private final List<BusStop> stops;

  public int getNumberOfStops() {
    return this.stops.size();
  }
}
