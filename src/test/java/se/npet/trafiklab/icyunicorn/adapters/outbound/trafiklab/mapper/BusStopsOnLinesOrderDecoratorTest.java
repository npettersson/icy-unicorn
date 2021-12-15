package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLineAndDirection;
import se.npet.trafiklab.icyunicorn.domain.entities.RouteDirection;

class BusStopsOnLinesOrderDecoratorTest {

  private final BusStopsOnLinesOrderDecorator decorator = new BusStopsOnLinesOrderDecorator();

  @Test
  void getNextOrderInt_shouldIncrementAndReset() {
    assertThat(decorator.getNextOrderInt(BusLineAndDirection.from("1", RouteDirection.A))).isZero();
    assertThat(decorator.getNextOrderInt(BusLineAndDirection.from("1", RouteDirection.A))).isEqualTo(1);
    assertThat(decorator.getNextOrderInt(BusLineAndDirection.from("1", RouteDirection.A))).isEqualTo(2);
    assertThat(decorator.getNextOrderInt(BusLineAndDirection.from("1", RouteDirection.B))).isZero();
    assertThat(decorator.getNextOrderInt(BusLineAndDirection.from("1", RouteDirection.B))).isEqualTo(1);
    assertThat(decorator.getNextOrderInt(BusLineAndDirection.from("2", RouteDirection.A))).isZero();
  }
}
