package se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class BusLineResponseDataDto {
  @JsonProperty("Version")
  public String version;
  @JsonProperty("Type")
  public String type;
  @JsonProperty("Result")
  public List<BusLineDto> result;
}
