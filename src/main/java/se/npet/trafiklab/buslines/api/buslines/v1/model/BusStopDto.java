package se.npet.trafiklab.buslines.api.buslines.v1.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BusStopDto {
  private Integer id;
  private Integer stopAreaId;
  private String stopPointName;
  private String northingCoord;
  private String eastingCoord;
  private String zone;
  private LocalDate existsFrom;
}
