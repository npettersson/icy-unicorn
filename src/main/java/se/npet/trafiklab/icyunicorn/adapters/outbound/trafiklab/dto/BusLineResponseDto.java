package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusLineResponseDto {

  @JsonProperty("StatusCode")
  public int statusCode;
  @JsonProperty("Message")
  public Object message;
  @JsonProperty("ExecutionTime")
  public int executionTime;
  @JsonProperty("ResponseData")
  public BusLineResponseDataDto responseData;

}
