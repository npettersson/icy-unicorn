package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class BusStopsOnLineResponseDataDto {
  @JsonProperty("Version")
  public String version;
  @JsonProperty("Type")
  public String type;
  @JsonProperty("Result")
  public List<BusStopOnLineDto> result;
}
