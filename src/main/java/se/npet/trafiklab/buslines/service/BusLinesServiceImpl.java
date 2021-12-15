package se.npet.trafiklab.buslines.service;

import java.util.List;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.buslines.domain.BusLinesStore;
import se.npet.trafiklab.buslines.domain.entities.BusLine;

@Component
public class BusLinesServiceImpl implements BusLinesService {

  private final BusLinesStore busLinesStore;

  public BusLinesServiceImpl(BusLinesStore busLinesStore) {
    this.busLinesStore = busLinesStore;
  }

  @Override
  public BusLine getBusLineByLineId(String lineId) {
    return this.busLinesStore.getBusLineByLineId(lineId);
  }

  @Override
  public List<BusLine> getTopBusLinesByNumberOfStops(int limit) {
    return this.busLinesStore.getBusLinesWithMostStops(limit);
  }

}
