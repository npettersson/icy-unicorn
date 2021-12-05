package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper;

import java.util.concurrent.atomic.AtomicInteger;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusLineAndDirection;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStopOnLine;

public class BusStopsOnLinesOrderDecorator {
  private final AtomicInteger counter = new AtomicInteger();
  private BusLineAndDirection previousLineAndDirection;

  @AfterMapping
  public void setBusStopOrder(@MappingTarget BusStopOnLine busStopOnLine) {
    busStopOnLine.setOrder(getNextOrderInt(busStopOnLine.getBusLineAndDirection()));
  }

  int getNextOrderInt(BusLineAndDirection currentLineAndDirection) {
    if (!currentLineAndDirection.equals(this.previousLineAndDirection)) {
      counter.set(0);
    }
    this.previousLineAndDirection = currentLineAndDirection;
    return counter.getAndIncrement();
  }

}
