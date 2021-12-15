package se.npet.trafiklab.buslines.adapters.outbound.trafiklab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TrafiklabApiUrlFactory {

  private final String apiBaseUrl;
  private final String apiKey;

  public TrafiklabApiUrlFactory(@Value("${trafiklab.sl.linjerhallplatser.api.key}") String apiKey,
      @Value("${trafiklab.sl.linjerhallplatser.api.url}") String apiBaseUrl) {
    this.apiBaseUrl = apiBaseUrl;
    this.apiKey = apiKey;
  }

  public String getBusStopsUrl() {
    return getApiUrl(ApiModes.STOPS);
  }

  public String getBusLinesUrl() {
    return getApiUrl(ApiModes.LINES);
  }

  public String getBusStopsOnLinesUrl() {
    return getApiUrl(ApiModes.JOURNEYS);
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
