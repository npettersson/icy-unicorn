package se.npet.trafiklab.buslines.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import se.npet.trafiklab.buslines.domain.entities.BusLine;

@Slf4j
public class BusLinesStore {

  private final Map<Integer, BusLine> busLineMap;

  public BusLinesStore(Map<Integer, BusLine> busLineMap) {
    this.busLineMap = busLineMap;
  }

  public BusLine getBusLineByLineId(Integer busLineId) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("Fetch bus line by line id");
    BusLine busLine = getBusLineMap().get(busLineId);
    stopWatch.stop();

    log.info("Found bus line <{}> in <{}> ms", busLine.getId(), stopWatch.getLastTaskTimeMillis());
    return busLine;
  }

  public Map<Integer, BusLine> getBusLineMap() {
    return busLineMap;
  }

  public List<BusLine> getBusLinesWithMostStops(int limit) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("Fetch top bus lines");

    List<BusLine> busLines = this.busLineMap.values().stream()
        .sorted(Comparator.comparing(BusLine::getNumberOfDistinctStops).reversed())
        .limit(limit)
        .collect(Collectors.toList());
    stopWatch.stop();

    log.info("Found top <{}> bus lines in <{}> ms", busLines.size(), stopWatch.getLastTaskTimeMillis());

    return busLines;
  }

}
