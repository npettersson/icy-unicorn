package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

class BusLineResponseTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testUnmarshallBusLineResponseJson() throws IOException {

    BusLineResponseDto responseDto = objectMapper.readValue(getTestDataFile("bus-lines-api-response.json"), BusLineResponseDto.class);
    assertThat(responseDto).isNotNull();
    assertThat(responseDto.getResponseData()).isNotNull();
    assertThat(responseDto.getResponseData().getResult())
        .isNotEmpty()
        .hasSize(5);
    assertThat(responseDto.getResponseData().getResult())
        .map(BusLineDto::getLineNumber)
        .containsExactlyInAnyOrder("1", "112", "113", "114", "998");
  }

  @Test
  void testUnmarshallBusStopResponseJson() throws IOException {
    BusStopResponseDto responseDto = objectMapper
        .readValue(getTestDataFile("bus-stops-api-response.json"), BusStopResponseDto.class);
    assertThat(responseDto).isNotNull();
    assertThat(responseDto.getResponseData()).isNotNull();
    assertThat(responseDto.getResponseData().getResult())
        .isNotEmpty()
        .hasSize(3);
    assertThat(responseDto.getResponseData().getResult())
        .map(BusStopDto::getStopPointName)
        .containsExactlyInAnyOrder("Bus stop A", "Bus stop B", "Bus stop C");
  }

  @Test
  void testUnmarshallBusStopsOnLinesJson() throws Exception {
    BusStopsOnLineResponseDto responseDto = objectMapper
        .readValue(getTestDataFile("bus-journey-api-response.json"), BusStopsOnLineResponseDto.class);
    assertThat(responseDto).isNotNull();
    assertThat(responseDto.getResponseData()).isNotNull();
    assertThat(responseDto.getResponseData().getResult())
        .isNotEmpty()
        .hasSize(6);
  }

  File getTestDataFile(String fileName) throws IOException {
    return ResourceUtils.getFile("classpath:api-data/" + fileName);
  }
}
