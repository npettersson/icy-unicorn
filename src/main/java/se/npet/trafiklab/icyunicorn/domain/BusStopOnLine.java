package se.npet.trafiklab.icyunicorn.domain;

import lombok.Data;

@Data
public class BusStopOnLine {
  private String lineId;
  private String stopPointId;
  public String directionCode;
  private int order;

  public String getLineIdAndDirectionCode() {
    return this.lineId + "-" + this.directionCode;
  }
}
