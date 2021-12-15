package se.npet.trafiklab.buslines.adapters.outbound.trafiklab;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusLineResponseDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopResponseDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopsOnLineResponseDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.mapper.TrafiklabDtoMapper;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;
import se.npet.trafiklab.buslines.domain.ports.BusLinesDataPort;

@Slf4j
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
        getHttpEntityWithCommonHeaders(), BusLineResponseDto.class);

    BusLineResponseDto busLineResponseDto = responseEntity.getBody();

    if (busLineResponseDto == null || busLineResponseDto.getResponseData() == null) {
      throw new IllegalStateException("Call to bus lines API didn't return a valid response");
    }

    return mapper.mapBusLines(busLineResponseDto.getResponseData().getResult());
  }

  @Override
  public List<BusStop> getBusStops() {
    ResponseEntity<BusStopResponseDto> responseEntity = restTemplate.exchange(apiUrlFactory.getBusStopsUrl(), HttpMethod.GET,
        getHttpEntityWithCommonHeaders(), BusStopResponseDto.class);

    BusStopResponseDto busStopResponseDto = responseEntity.getBody();

    if (busStopResponseDto == null || busStopResponseDto.getResponseData() == null) {
      throw new IllegalStateException("Call to bus stop API didn't return a valid response");
    }

    return mapper.mapBusStops(busStopResponseDto.getResponseData().getResult());
  }

  @Override
  public List<BusStopOnLine> getBusStopsOnLines() {
    ResponseEntity<BusStopsOnLineResponseDto> responseEntity = restTemplate.exchange(apiUrlFactory.getBusStopsOnLinesUrl(), HttpMethod.GET,
        getHttpEntityWithCommonHeaders(), BusStopsOnLineResponseDto.class);

    BusStopsOnLineResponseDto busStopsOnLineResponseDto = responseEntity.getBody();

    if (busStopsOnLineResponseDto == null || busStopsOnLineResponseDto.getResponseData() == null) {
      throw new IllegalStateException("Call to bus stops on line API didn't return a valid response");
    }

    List<BusStopOnLineDto> busStopOnLineDtos = busStopsOnLineResponseDto.getResponseData().getResult();
    return mapper.mapBusStopsOnLines(busStopOnLineDtos);
  }

  private HttpEntity<String> getHttpEntityWithCommonHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept-Encoding","gzip,deflate");
    return new HttpEntity<>(headers);
  }
}
