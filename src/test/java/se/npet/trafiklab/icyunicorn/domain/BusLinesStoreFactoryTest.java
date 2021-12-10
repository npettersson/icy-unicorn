package se.npet.trafiklab.icyunicorn.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStopOnLine;
import se.npet.trafiklab.icyunicorn.domain.entities.RouteDirection;
import se.npet.trafiklab.icyunicorn.domain.ports.BusLinesDataPort;

@ExtendWith(MockitoExtension.class)
class BusLinesStoreFactoryTest {

  private BusLine b1, b2a, b2b;

  private BusStop s1, s2,s3, s4, s5, s6;

  private List<BusLine> busLines;
  private List<BusStop> busStops;
  private List<BusStopOnLine> busStopOnLines;

  @Mock
  private BusLinesDataPort busLinesDataPort;

  @InjectMocks
  private BusLinesStoreFactory storeFactory;

  @BeforeEach
  void setUp() {
    this.b1 = new BusLine("1", "b1", LocalDate.of(2021,1,1));
    this.b2a = new BusLine("2", "b2", LocalDate.of(2021, 1, 1));
    this.b2b = new BusLine("2", "b2", LocalDate.of(2021, 3, 1));
    this.busLines = List.of(b1, b2a, b2b);

    this.s1 = new BusStop("1","Stop 1");
    this.s2 = new BusStop("2","Stop 2");
    this.s3 = new BusStop("3","Stop 3");
    this.s4 = new BusStop("4","Stop 4");
    this.s5 = new BusStop("5","Stop 5");
    this.s6 = new BusStop("6","Stop 6");
    this.busStops = List.of(s1, s2, s3, s4, s5, s6);

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

  @Test
  void createBusLinesStore() {

    when(busLinesDataPort.getBusLines()).thenReturn(busLines);
    when(busLinesDataPort.getBusStops()).thenReturn(busStops);
    when(busLinesDataPort.getBusStopsOnLines()).thenReturn(busStopOnLines);

    BusLinesStore store = storeFactory.createBusLinesStore();
    assertThat(store).isNotNull();
    assertThat(store.getBusLineMap()).isNotEmpty().hasSize(2);

    BusLine b1 = store.getBusLineByLineId("1");

    assertThat(b1).isNotNull();
    assertThat(b1.getDesignation()).isEqualTo("b1");
    assertThat(b1.getRoutes()).hasSize(2);
  }

  @Test
  void testPrepBusLinesMap() {
    Map<String, BusLine> busLineMap = storeFactory.prepareBusLinesMap(this.busLines);
    assertThat(busLineMap).isNotNull().hasSize(2);
    assertThat(busLineMap.values()).contains(b1, b2b);
  }

}