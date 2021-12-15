package se.npet.trafiklab.buslines.api.buslines.v1.model;

import java.util.List;
import lombok.Data;

@Data
public class BusLineDto {
  private String lineId;
  private int numberOfDistinctStops;
  private List<BusRouteDto> routes;
}
