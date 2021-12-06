package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusLineResponseDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopResponseDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopsOnLineResponseDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper.TrafiklabDtoMapper;
import se.npet.trafiklab.icyunicorn.domain.ports.BusLinesDataPort;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStop;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStopOnLine;

@Service
public class TrafiklabAdapterImpl implements BusLinesDataPort {

  private final RestTemplate restTemplate;
  private final TrafiklabDtoMapper mapper;
  private final TrafiklabApiUrlFactory apiUrlFactory;

  public TrafiklabAdapterImpl(RestTemplate restTemplate, TrafiklabDtoMapper mapper, TrafiklabApiUrlFactory apiUrlFactory) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
    this.apiUrlFactory = apiUrlFactory;
  }

  @Override
  public List<BusLine> getBusLines() {
    BusLineResponseDto busLineResponse = restTemplate.getForObject(apiUrlFactory.getBusLinesUrl(), BusLineResponseDto.class);
    return busLineResponse.getResponseData().getResult().stream()
        .map(mapper::toBusLine)
        .collect(Collectors.toList());
  }

  @Override
  public List<BusStop> getBusStops() {
    BusStopResponseDto busStopResponseDto = restTemplate.getForObject(apiUrlFactory.getBusStopsUrl(), BusStopResponseDto.class);
    return busStopResponseDto.getResponseData().getResult().stream()
        .map(mapper::toBusStop)
        .collect(Collectors.toList());
  }

  @Override
  public List<BusStopOnLine> getBusStopsOnLines() {
    BusStopsOnLineResponseDto busStopsOnLineResponseDto = restTemplate.getForObject(apiUrlFactory.getBusStopsOnLinesUrl(), BusStopsOnLineResponseDto.class);
    List<BusStopOnLineDto> busStopOnLineDtos = busStopsOnLineResponseDto.getResponseData().getResult();
    return mapper.mapBusStopsOnLines(busStopOnLineDtos);
  }
}
