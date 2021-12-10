package se.npet.trafiklab.icyunicorn.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BusLine {
  private final String lineId;
  private final String designation;
  private final LocalDate existsFrom;
  private final List<BusRoute> routes = new ArrayList<>();

  public void addRoute(BusRoute busRoute) {
    this.routes.add(busRoute);
  }

  public int getTotalNumberOfStops() {
    return this.routes.stream()
        .reduce(0, (subtotal, route) -> subtotal + route.getNumberOfStops(), Integer::sum);
  }
}
