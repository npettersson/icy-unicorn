package se.npet.trafiklab.buslines.testutils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusRoute;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.RouteDirection;

public final class BusLinesTestUtils {

  public static BusLine makeBusLine(Integer busLineId, int nbrOfStops) {
    BusLine busLine = new BusLine(busLineId, "Bus " + busLineId, LocalDate.of(2021, 1, 1));
    busLine.addRoute(makeBusRoute(nbrOfStops, RouteDirection.A));
    busLine.addRoute(makeBusRoute(nbrOfStops, RouteDirection.B));
    return busLine;
  }

  public static BusRoute makeBusRoute(int nbrOfStops, RouteDirection routeDirection) {
    List<BusStop> busStops = IntStream.rangeClosed(1, nbrOfStops)
        .mapToObj(stopId ->
            new BusStop(stopId, stopId, "Bus stop " + stopId)
        )
        .collect(Collectors.toList());

    if (RouteDirection.B.equals(routeDirection)) {
      Collections.reverse(busStops);
    }

    return new BusRoute(routeDirection, busStops);
  }

}
