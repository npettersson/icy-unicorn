package se.npet.trafiklab.buslines.api.buslines.v1.model;

import lombok.Data;

@Data
public class BusLineAndNbrOfStopsDto {
  private Integer id;
  private String designation;
  private long numberOfDistinctStops;
}
