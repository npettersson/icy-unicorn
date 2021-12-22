package se.npet.trafiklab.buslines.domain.entities;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusStop {
  private Integer id;
  private Integer stopAreaId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;
  private LocalDate existsFrom;

  public BusStop(Integer id, Integer stopAreaId, String stopPointName) {
    this.id = id;
    this.stopAreaId = stopAreaId;
    this.stopPointName = stopPointName;
  }
}
