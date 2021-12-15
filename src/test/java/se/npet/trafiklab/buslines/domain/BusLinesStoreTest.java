package se.npet.trafiklab.buslines.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static se.npet.trafiklab.buslines.testutils.BusLinesTestUtils.makeBusLine;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.npet.trafiklab.buslines.domain.entities.BusLine;

class BusLinesStoreTest {

  private BusLine b1, b2, b3, b4, b5, b6;

  private BusLinesStore busLinesStore;

  @BeforeEach
  void setUp() {
    this.b1 = makeBusLine("1", 9);
    this.b2 = makeBusLine("2", 7);
    this.b3 = makeBusLine("3", 8);
    this.b4 = makeBusLine("4", 5);
    this.b5 = makeBusLine("5", 10);
    this.b6 = makeBusLine("6", 6);

    Map<String, BusLine> busLinesMap = new TreeMap<>();
    List.of(b1, b2, b3, b4, b5, b6).forEach(b -> busLinesMap.put(b.getLineId(), b));
    this.busLinesStore = new BusLinesStore(busLinesMap);
  }

  @Test
  void getBusLinesWithMostStops_shouldContainTopFour() {
    assertThat(busLinesStore.getBusLinesWithMostStops(4))
      .hasSize(4)
      .containsExactly(b5, b1, b3, b2);
  }

  @Test
  void getBusLineByLineId_shouldReturnBusLine() {
    assertThat(busLinesStore.getBusLineByLineId("4"))
        .isNotNull()
        .extracting("lineId")
        .isEqualTo("4");
  }

}
