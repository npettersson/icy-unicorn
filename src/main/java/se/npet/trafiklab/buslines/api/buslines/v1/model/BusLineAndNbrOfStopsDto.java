package se.npet.trafiklab.buslines.api.buslines.v1.model;

import lombok.Data;

@Data
public class BusLineAndNbrOfStopsDto {
  private String lineId;
  private String designation;
  private int numberOfDistinctStops;
}
