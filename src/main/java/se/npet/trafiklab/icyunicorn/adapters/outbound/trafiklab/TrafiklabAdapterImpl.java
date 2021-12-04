package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusLineResponseDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopResponseDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopsOnLineResponseDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper.TrafiklabDtoMapper;
import se.npet.trafiklab.icyunicorn.domain.BusLine;
import se.npet.trafiklab.icyunicorn.domain.BusStop;
import se.npet.trafiklab.icyunicorn.domain.BusStopOnLine;

@Service
public class TrafiklabAdapterImpl {

  private final RestTemplate restTemplate;
  private final TrafiklabDtoMapper mapper;
  private final String apiKey;
  private final String apiBaseUrl;


  public TrafiklabAdapterImpl(RestTemplate restTemplate, TrafiklabDtoMapper mapper,
      @Value("${trafiklab.sl.linjerhallplatser.api.key}") String apiKey,
      @Value("${trafiklab.sl.linjerhallplatser.api.url}") String apiUrl) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
    this.apiKey = apiKey;
    this.apiBaseUrl = apiUrl;
  }

  public List<BusLine> getBusLines() {
    BusLineResponseDto busLineResponse = restTemplate.getForObject(getApiUrl(ApiModes.LINES), BusLineResponseDto.class);
    return busLineResponse.getResponseData().getResult().stream()
        .map(mapper::toBusLine)
        .collect(Collectors.toList());
  }

  public List<BusStop> getBusStops() {
    BusStopResponseDto busStopResponseDto = restTemplate.getForObject(getApiUrl(ApiModes.STOPS), BusStopResponseDto.class);
    return busStopResponseDto.getResponseData().getResult().stream()
        .map(mapper::toBusStop)
        .collect(Collectors.toList());
  }

  public List<BusStopOnLine> getBusStopsOnLines() {
    BusStopsOnLineResponseDto busStopsOnLineResponseDto = restTemplate.getForObject(getApiUrl(ApiModes.JOURNEYS), BusStopsOnLineResponseDto.class);
    List<BusStopOnLineDto> busStopOnLineDtos = busStopsOnLineResponseDto.getResponseData().getResult();
    return mapper.mapBusStopsOnLines(busStopOnLineDtos);
  }

  private String getApiUrl(ApiModes model) {
    return UriComponentsBuilder.fromHttpUrl(this.apiBaseUrl)
        .queryParam("key", this.apiKey)
        .queryParam("model", model.getMode())
        .queryParam("DefaultTransportModeCode", "BUS")
        .build().toString();
  }

  private enum ApiModes {
    LINES("line"),
    JOURNEYS("jour"),
    STOPS("stop") ;

    private final String mode;

    ApiModes(String mode) {
      this.mode = mode;
    }

    public String getMode() {
      return mode;
    }
  }
}
