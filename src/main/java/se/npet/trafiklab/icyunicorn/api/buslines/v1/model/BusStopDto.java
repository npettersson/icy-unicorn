package se.npet.trafiklab.icyunicorn.api.buslines.v1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BusStopDto {
  private String stopPointId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;
}