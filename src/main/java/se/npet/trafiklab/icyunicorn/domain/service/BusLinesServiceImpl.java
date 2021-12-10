package se.npet.trafiklab.icyunicorn.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.icyunicorn.domain.BusLinesStore;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;

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
    return this.busLinesStore.getBusLineMap().values().stream()
        .sorted(Comparator.comparing(BusLine::getTotalNumberOfStops))
        .limit(limit)
        .collect(Collectors.toList());
  }

}
