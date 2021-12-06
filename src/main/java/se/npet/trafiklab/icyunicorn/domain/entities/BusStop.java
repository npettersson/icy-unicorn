package se.npet.trafiklab.icyunicorn.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusStop {
  private String stopPointId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;

  public BusStop(String stopPointId, String stopPointName) {
    this.stopPointId = stopPointId;
    this.stopPointName = stopPointName;
  }
}
