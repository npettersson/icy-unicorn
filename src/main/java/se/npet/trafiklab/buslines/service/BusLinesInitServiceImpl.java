package se.npet.trafiklab.buslines.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.ports.BusLinesDataPort;
import se.npet.trafiklab.buslines.domain.ports.BusLinesDbPort;

@Slf4j
@Component
public class BusLinesInitServiceImpl implements InitializingBean {

  private final BusLinesDataPort busLinesDataPort;
  private final BusLinesDbPort busLinesDbPort;

  public BusLinesInitServiceImpl(BusLinesDataPort busLinesDataPort, BusLinesDbPort busLinesDbPort) {
    this.busLinesDataPort = busLinesDataPort;
    this.busLinesDbPort = busLinesDbPort;
  }

  public void initBusLinesDb() {
    log.info("Retrieving bus lines data...");
    List<BusLine> busLines = busLinesDataPort.getBusLines();
    log.info("Got <{}> bus lines", busLines.size());
    List<BusStop> busStops = busLinesDataPort.getBusStops();
    log.info("Got <{}> bus stops", busStops.size());
    List<BusStopOnLine> busStopsOnLines = busLinesDataPort.getBusStopsOnLines();
    log.info("Got <{}> bus stops on lines", busStopsOnLines.size());
    populateBusLinesDb(busLines, busStops, busStopsOnLines);
  }

  private void populateBusLinesDb(List<BusLine> busLines, List<BusStop> busStops, List<BusStopOnLine> busStopsOnLines) {
    log.info("Populating data base with bus lines data...");
    Map<String, BusLine> busLineMap = prepareBusLinesMap(busLines);

    log.info("Persisting <{}> bus lines", busLineMap.size());
    busLinesDbPort.persistBusLines(busLineMap.values());

    Map<String, BusStop> busStopMap = busStops.stream()
        .collect(Collectors.toMap(BusStop::getStopPointId, Function.identity()));

    log.info("Persisting <{}> bus stops", busStopMap.size());
    busLinesDbPort.persistBusStops(busStopMap.values());

    Set<BusStopOnLine> busStopSet = ensureBusStopsAndBusLinesExists(busStopsOnLines, busLineMap, busStopMap);
    log.info("Persisting <{}> bus stop points", busStopSet.size());
    busLinesDbPort.persistBusStopOnLine(busStopSet);
  }

  Set<BusStopOnLine> ensureBusStopsAndBusLinesExists(List<BusStopOnLine> busStopOnLines, Map<String, BusLine> busLineMap, Map<String, BusStop> busStopMap) {
    return busStopOnLines.stream()
        .filter(busStopOnLine -> {
          boolean stopAndLineExists = busStopMap.containsKey(busStopOnLine.getStopPointId()) && busLineMap.containsKey(busStopOnLine.getLineId());
          if (!stopAndLineExists) {
            log.warn("Could not find bus stop with id <{}> when persisting stop on line <{}>", busStopOnLine.getStopPointId(),
                busStopOnLine.getLineId());
          }
          return stopAndLineExists;
        })
        .collect(Collectors.toSet());
  }

  Map<String, BusLine> prepareBusLinesMap(List<BusLine> busLines) {
    Map<String, BusLine> busLinesMap = new TreeMap<>();
    // Data quality problem, duplicate lines exist in data set, make sure to include only the most current bus line
    busLines.forEach(busLine -> busLinesMap.merge(busLine.getLineId(), busLine,
        (currLine, newLine) -> (newLine.getExistsFrom().isAfter(currLine.getExistsFrom())) ? newLine : currLine));
    return busLinesMap;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.initBusLinesDb();
  }
}
