package se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusStopOnLineDto {
  @JsonProperty("LineNumber")
  public String lineNumber;
  @JsonProperty("DirectionCode")
  public String directionCode;
  @JsonProperty("JourneyPatternPointNumber")
  public String journeyPatternPointNumber;
  @JsonProperty("LastModifiedUtcDateTime")
  public String lastModifiedUtcDateTime;
  @JsonProperty("ExistsFromDate")
  public String existsFromDate;
}
