package se.npet.trafiklab.buslines.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusStopResponseDto {
  @JsonProperty("StatusCode")
  public int statusCode;
  @JsonProperty("Message")
  public String message;
  @JsonProperty("ExecutionTime")
  public int executionTime;
  @JsonProperty("ResponseData")
  public BusStopResponseDataDto responseData;
}
