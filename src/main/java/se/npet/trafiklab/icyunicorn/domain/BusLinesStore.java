package se.npet.trafiklab.icyunicorn.domain;

import java.util.Map;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;

public class BusLinesStore {

  private final Map<String, BusLine> busLineMap;

  public BusLinesStore(Map<String, BusLine> busLineMap) {
    this.busLineMap = busLineMap;
  }

  public BusLine getBusLineByLineId(String lineId) {
    return getBusLineMap().get(lineId);
  }

  public Map<String, BusLine> getBusLineMap() {
    return busLineMap;
  }

}
