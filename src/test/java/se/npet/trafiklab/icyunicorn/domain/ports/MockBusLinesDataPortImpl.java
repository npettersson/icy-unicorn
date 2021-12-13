package se.npet.trafiklab.icyunicorn.domain.ports;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStopOnLine;
import se.npet.trafiklab.icyunicorn.domain.entities.RouteDirection;

@Component
@Profile("integration-test")
public class MockBusLinesDataPortImpl implements BusLinesDataPort, InitializingBean {

  private List<BusLine> busLines;
  private List<BusStop> busStops;
  private List<BusStopOnLine> busStopOnLines;

  @Override
  public List<BusLine> getBusLines() {
    return this.busLines;
  }

  @Override
  public List<BusStop> getBusStops() {
    return this.busStops;
  }

  @Override
  public List<BusStopOnLine> getBusStopsOnLines() {
    return this.busStopOnLines;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

    this.busLines = List.of(
        new BusLine("1", "b1", LocalDate.of(2021, 1, 1)),
        new BusLine("2", "b2", LocalDate.of(2021, 1, 1))
    );

    this.busStops = List.of(
        new BusStop("1", "1", "Stop 1"),
        new BusStop("2", "2", "Stop 2"),
        new BusStop("3", "3", "Stop 3"),
        new BusStop("4", "4", "Stop 4"),
        new BusStop("5", "5", "Stop 5"),
        new BusStop("6", "6", "Stop 6")
    );

    busStopOnLines = List.of(
        new BusStopOnLine("1", "1", RouteDirection.A),
        new BusStopOnLine("1", "2", RouteDirection.A),
        new BusStopOnLine("1", "3", RouteDirection.A),
        new BusStopOnLine("1", "3", RouteDirection.B),
        new BusStopOnLine("1", "2", RouteDirection.B),
        new BusStopOnLine("1", "1", RouteDirection.B),
        new BusStopOnLine("2", "4", RouteDirection.A),
        new BusStopOnLine("2", "5", RouteDirection.A),
        new BusStopOnLine("2", "5", RouteDirection.B),
        new BusStopOnLine("2", "4", RouteDirection.B)
    );
  }
}
