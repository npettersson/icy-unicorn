package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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

@Component
@Profile("!integration-test")
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

    ResponseEntity<BusLineResponseDto> responseEntity = restTemplate.exchange(apiUrlFactory.getBusLinesUrl(), HttpMethod.GET,
        new HttpEntity(getCommonHeaders()), BusLineResponseDto.class);

    BusLineResponseDto busLineResponseDto = responseEntity.getBody();

    return mapper.mapBusLines(busLineResponseDto.getResponseData().getResult());
  }

  @Override
  public List<BusStop> getBusStops() {
    ResponseEntity<BusStopResponseDto> responseEntity = restTemplate.exchange(apiUrlFactory.getBusStopsUrl(), HttpMethod.GET,
        new HttpEntity(getCommonHeaders()), BusStopResponseDto.class);

    BusStopResponseDto busStopResponseDto = responseEntity.getBody();

    return mapper.mapBusStops(busStopResponseDto.getResponseData().getResult());
  }

  @Override
  public List<BusStopOnLine> getBusStopsOnLines() {
    ResponseEntity<BusStopsOnLineResponseDto> responseEntity = restTemplate.exchange(apiUrlFactory.getBusStopsOnLinesUrl(), HttpMethod.GET,
        new HttpEntity(getCommonHeaders()), BusStopsOnLineResponseDto.class);

    BusStopsOnLineResponseDto busStopsOnLineResponseDto = responseEntity.getBody();
    List<BusStopOnLineDto> busStopOnLineDtos = busStopsOnLineResponseDto.getResponseData().getResult();
    return mapper.mapBusStopsOnLines(busStopOnLineDtos);
  }

  private HttpHeaders getCommonHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept-Encoding","gzip,deflate");
    return headers;
  }
}
