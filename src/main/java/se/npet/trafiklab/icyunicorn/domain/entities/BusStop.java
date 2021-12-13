package se.npet.trafiklab.icyunicorn.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusStop {
  private String stopPointId;
  private String stopAreaId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;

  public BusStop(String stopPointId, String stopAreaId, String stopPointName) {
    this.stopPointId = stopPointId;
    this.stopAreaId = stopAreaId;
    this.stopPointName = stopPointName;
  }
}
