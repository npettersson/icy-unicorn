package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.util.ResourceUtils;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopOnLineDto;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto.BusStopsOnLineResponseDto;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStopOnLine;

class TrafiklabDtoMapperTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final TrafiklabDtoMapper mapper = Mappers.getMapper(TrafiklabDtoMapper.class);

  private List<BusStopOnLineDto> busStopOnLineDtos;

  @BeforeEach
  void setUp() throws Exception {
    BusStopsOnLineResponseDto responseDto = objectMapper
        .readValue(getTestDataFile("bus-journey-api-response.json"), BusStopsOnLineResponseDto.class);
    this.busStopOnLineDtos = responseDto.getResponseData().getResult();
  }

  @Test
  void testMapBusStopsOnLines() {
    List<BusStopOnLine> busStopOnLines = mapper.mapBusStopsOnLines(busStopOnLineDtos);
    assertThat(busStopOnLines).map(BusStopOnLine::getOrder).containsExactly(0, 1, 2, 0, 1, 2);
  }

  private File getTestDataFile(String fileName) throws IOException {
    return ResourceUtils.getFile("classpath:api-data/" + fileName);
  }
}
