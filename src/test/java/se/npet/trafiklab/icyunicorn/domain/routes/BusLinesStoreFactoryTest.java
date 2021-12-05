package se.npet.trafiklab.icyunicorn.domain.routes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.BusStopOnLine;
import se.npet.trafiklab.icyunicorn.domain.routes.entities.RouteDirection;

class BusLinesStoreFactoryTest {

  private List<BusLine> busLines;
  private List<BusStop> busStops;
  private List<BusStopOnLine> busStopOnLines;

  @BeforeEach
  void setUp() {
    BusLine b1 = new BusLine("b1");
    BusLine b2 = new BusLine("b2");
    busLines = List.of(b1, b2);

    BusStop s1 = new BusStop("s1","Stop 1");
    BusStop s2 = new BusStop("s2","Stop 2");
    BusStop s3 = new BusStop("s3","Stop 3");
    BusStop s4 = new BusStop("s4","Stop 4");
    BusStop s5 = new BusStop("s5","Stop 5");
    BusStop s6 = new BusStop("s6","Stop 6");
    busStops = List.of(s1, s2, s3, s4, s5, s6);

    busStopOnLines = List.of(
      new BusStopOnLine("b1", "s1", RouteDirection.A),
      new BusStopOnLine("b1", "s2", RouteDirection.A),
      new BusStopOnLine("b1", "s3", RouteDirection.A),
      new BusStopOnLine("b1", "s3", RouteDirection.B),
      new BusStopOnLine("b1", "s2", RouteDirection.B),
      new BusStopOnLine("b1", "s1", RouteDirection.B),
      new BusStopOnLine("b2", "s4", RouteDirection.A),
      new BusStopOnLine("b2", "s5", RouteDirection.A),
      new BusStopOnLine("b2", "s5", RouteDirection.B),
      new BusStopOnLine("b2", "s4", RouteDirection.B)
    );
  }

  @Test
  void createBusLinesStore() {


  }
}
