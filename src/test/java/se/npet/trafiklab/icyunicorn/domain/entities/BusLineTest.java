package se.npet.trafiklab.icyunicorn.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.npet.trafiklab.icyunicorn.testutils.BusLinesTestUtils;

class BusLineTest {

  @Test
  void testCreate_allOk(){
    BusLine busLine = new BusLine("4", "Bus 4", LocalDate.of(2021, 1, 1));
    assertThat(busLine).isNotNull();
    assertThat(busLine.getLineId()).isEqualTo("4");
    assertThat(busLine.getDesignation()).isEqualTo("Bus 4");
    assertThat(busLine.getBusRoutes()).isEmpty();
    assertThat(busLine.getNumberOfDistinctStops()).isZero();
  }

  @Test
  void testCreateAndAddRoute_shouldWorkOk() {
    BusLine busLine = new BusLine("4", "Bus 4", LocalDate.of(2021, 1, 1));
    busLine.addRoute(BusLinesTestUtils.makeBusRoute(4, RouteDirection.A));
    assertThat(busLine.getBusRoutes()).hasSize(1);
    assertThat(busLine.getBusRouteByDirection(RouteDirection.A)).isNotNull();
    assertThat(busLine.getNumberOfDistinctStops()).isEqualTo(4);
  }

  @Test
  void calcNbrOfDistinctStops_shouldReturn5DistinctStops() {
    BusLine busLine = new BusLine("4", "Bus 4", LocalDate.of(2021, 1, 1));
    List<BusRoute> busRoutes = List
        .of(BusLinesTestUtils.makeBusRoute(4, RouteDirection.A), BusLinesTestUtils.makeBusRoute(5, RouteDirection.B));
    assertThat(busLine.calcNumberOfDistinctStops(busRoutes)).isEqualTo(5);
  }
}
