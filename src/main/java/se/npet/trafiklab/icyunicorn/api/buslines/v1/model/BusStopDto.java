package se.npet.trafiklab.icyunicorn.api.buslines.v1.model;

import lombok.Data;

@Data
public class BusStopDto {
  private String stopPointId;
  private String stopAreaId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;
}
