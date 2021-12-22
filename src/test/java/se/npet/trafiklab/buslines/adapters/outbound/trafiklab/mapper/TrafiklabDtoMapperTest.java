package se.npet.trafiklab.buslines.adapters.outbound.trafiklab.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.util.ResourceUtils;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusLineDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusLineResponseDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopResponseDto;
import se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto.BusStopsOnLineResponseDto;
import se.npet.trafiklab.buslines.domain.entities.BusLine;
import se.npet.trafiklab.buslines.domain.entities.BusStop;
import se.npet.trafiklab.buslines.domain.entities.BusStopOnLine;

class TrafiklabDtoMapperTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final TrafiklabDtoMapper mapper = Mappers.getMapper(TrafiklabDtoMapper.class);

  private List<BusLineDto> busLineDtos;
  private List<BusStopDto> busStopDtos;
  private List<BusStopOnLineDto> busStopOnLineDtos;

  @BeforeEach
  void setUp() throws Exception {
    BusStopsOnLineResponseDto busStopsOnLineResponseDto = objectMapper
        .readValue(getTestDataFile("bus-journey-api-response.json"), BusStopsOnLineResponseDto.class);
    this.busStopOnLineDtos = busStopsOnLineResponseDto.getResponseData().getResult();

    BusLineResponseDto busLineResponseDto = objectMapper
        .readValue(getTestDataFile("bus-lines-api-response.json"), BusLineResponseDto.class);
    this.busLineDtos = busLineResponseDto.getResponseData().getResult();

    BusStopResponseDto busStopResponseDto = objectMapper
        .readValue(getTestDataFile("bus-stops-api-response.json"), BusStopResponseDto.class);
    this.busStopDtos = busStopResponseDto.getResponseData().getResult();
  }

  @Test
  void mapBusLines_withJsonData_shouldMapOk() {
    List<BusLine> busLines = mapper.mapBusLines(busLineDtos);
    assertThat(busLines).hasSize(5).allSatisfy(busLine -> {
      assertThat(busLine.getId()).isNotNull();
      assertThat(busLine.getDesignation()).isNotNull();
      assertThat(busLine.getExistsFrom()).isNotNull();
    });
  }

  @Test
  void mapBusStops_withJsonData_shouldMapOk() {
    List<BusStop> busStops = mapper.mapBusStops(this.busStopDtos);
    assertThat(busStops).hasSize(3).allSatisfy(busStop -> {
      assertThat(busStop.getId()).isNotNull();
      assertThat(busStop.getStopAreaId()).isNotNull();
      assertThat(busStop.getStopPointName()).isNotNull();
      assertThat(busStop.getNorthingCoord()).isNotNull();
      assertThat(busStop.getEastingCoord()).isNotNull();
      assertThat(busStop.getZone()).isNotNull();
    });
  }

  @Test
  void mapBusStopsOnLines_withJsonData_shouldMapOk() {
    List<BusStopOnLine> busStopOnLines = mapper.mapBusStopsOnLines(busStopOnLineDtos);
    assertThat(busStopOnLines).map(BusStopOnLine::getOrder).containsExactly(0, 1, 2, 0, 1, 2);
  }

  private File getTestDataFile(String fileName) throws IOException {
    return ResourceUtils.getFile("classpath:api-data/" + fileName);
  }
}
