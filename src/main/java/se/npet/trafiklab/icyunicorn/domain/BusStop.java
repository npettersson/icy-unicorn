package se.npet.trafiklab.icyunicorn.domain;

import lombok.Data;

@Data
public class BusStop {
  private String stopPointId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;
}
