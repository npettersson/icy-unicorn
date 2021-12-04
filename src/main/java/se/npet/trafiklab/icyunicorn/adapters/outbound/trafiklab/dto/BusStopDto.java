package se.npet.trafiklab.icyunicorn.adapters.outbound.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusStopDto {
  @JsonProperty("StopPointNumber")
  public String stopPointNumber;
  @JsonProperty("StopPointName")
  public String stopPointName;
  @JsonProperty("StopAreaNumber")
  public String stopAreaNumber;
  @JsonProperty("LocationNorthingCoordinate")
  public String locationNorthingCoordinate;
  @JsonProperty("LocationEastingCoordinate")
  public String locationEastingCoordinate;
  @JsonProperty("ZoneShortName")
  public String zoneShortName;
  @JsonProperty("StopAreaTypeCode")
  public String stopAreaTypeCode;
  @JsonProperty("LastModifiedUtcDateTime")
  public String lastModifiedUtcDateTime;
  @JsonProperty("ExistsFromDate")
  public String existsFromDate;
}
