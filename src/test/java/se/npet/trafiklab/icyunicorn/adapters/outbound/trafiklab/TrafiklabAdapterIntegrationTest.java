package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import se.npet.trafiklab.icyunicorn.SpringTestConfiguration;
import se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.mapper.TrafiklabDtoMapper;
import se.npet.trafiklab.icyunicorn.domain.ports.BusLinesDataPort;
import se.npet.trafiklab.icyunicorn.domain.entities.BusLine;
import se.npet.trafiklab.icyunicorn.domain.entities.BusStop;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { SpringTestConfiguration.class })
class TrafiklabAdapterIntegrationTest {

  private final BusLinesDataPort adapter;
  private final TrafiklabApiUrlFactory apiUrlFactory;

  private final MockRestServiceServer mockServer;

  @Autowired
  public TrafiklabAdapterIntegrationTest(RestTemplate restTemplate,
      TrafiklabApiUrlFactory apiUrlFactory, TrafiklabDtoMapper mapper) {
    this.adapter = new TrafiklabAdapterImpl(restTemplate, mapper, apiUrlFactory);
    this.apiUrlFactory = apiUrlFactory;

    this.mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  void fetchBusLines_shouldOk() {
    mockServer.expect(ExpectedCount.once(), requestTo(apiUrlFactory.getBusLinesUrl()))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(new ClassPathResource("api-data/bus-lines-api-response.json"),
            MediaType.APPLICATION_JSON));

    List<BusLine> busLines = adapter.getBusLines();

    assertThat(busLines).isNotEmpty().hasSize(5);
    assertThat(busLines).map(BusLine::getDesignation)
        .containsExactlyInAnyOrder("1", "112", "113", "114", "UL870");

    mockServer.verify();
  }

  @Test
  void fetchBusStops_shouldOk() {
    mockServer.expect(ExpectedCount.once(), requestTo(apiUrlFactory.getBusStopsUrl()))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(new ClassPathResource("api-data/bus-stops-api-response.json"),
            MediaType.APPLICATION_JSON));

    List<BusStop> busStops = adapter.getBusStops();

    assertThat(busStops).isNotEmpty().hasSize(3);
    assertThat(busStops).map(BusStop::getStopPointName)
        .containsExactlyInAnyOrder("Bus stop A", "Bus stop B", "Bus stop C");

    mockServer.verify();
  }

}
