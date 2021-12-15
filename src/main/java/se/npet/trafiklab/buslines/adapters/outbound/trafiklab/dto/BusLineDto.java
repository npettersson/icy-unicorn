package se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusLineDto {
  @JsonProperty("LineNumber")
  public String lineNumber;
  @JsonProperty("LineDesignation")
  public String lineDesignation;
  @JsonProperty("DefaultTransportMode")
  public String defaultTransportMode;
  @JsonProperty("DefaultTransportModeCode")
  public String defaultTransportModeCode;
  @JsonProperty("LastModifiedUtcDateTime")
  public String lastModifiedUtcDateTime;
  @JsonProperty("ExistsFromDate")
  public String existsFromDate;
}
