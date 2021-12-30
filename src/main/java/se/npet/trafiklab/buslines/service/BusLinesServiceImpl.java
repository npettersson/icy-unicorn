package se.npet.trafiklab.buslines.service;

import java.util.List;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.ports.BusLinesDbPort;

@Component
public class BusLinesServiceImpl implements BusLinesService {

  private final BusLinesDbPort busLinesDbPort;

  public BusLinesServiceImpl(BusLinesDbPort busLinesDbPort) {
    this.busLinesDbPort = busLinesDbPort;
  }

  @Override
  public BusLine getBusLineByLineId(Integer busLineId) {
    return this.busLinesDbPort.fetchBusLineById(busLineId);
  }

  @Override
  public List<BusLine> getTopBusLinesByNumberOfStops(int limit) {
    return this.busLinesDbPort.getBusLinesWithMostStops(limit);
  }

}
