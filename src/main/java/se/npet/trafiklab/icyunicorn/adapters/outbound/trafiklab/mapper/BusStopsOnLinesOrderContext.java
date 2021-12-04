package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper;

import java.util.concurrent.atomic.AtomicInteger;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import se.npet.trafiklab.icyunicorn.domain.BusStopOnLine;

public class BusStopsOnLinesOrderContext {
  private final AtomicInteger counter = new AtomicInteger();
  private String currentLineAndDirection;

  @AfterMapping
  public void setBusStopOrder(@MappingTarget BusStopOnLine busStopOnLine) {
    String lineAndDirection = busStopOnLine.getLineIdAndDirectionCode();
    if (currentLineAndDirection == null) {
      currentLineAndDirection = lineAndDirection;
    } else if (!currentLineAndDirection.equals(lineAndDirection)) {
      counter.set(0);
      currentLineAndDirection = lineAndDirection;
    }

    busStopOnLine.setOrder(counter.getAndIncrement());
  }

}
