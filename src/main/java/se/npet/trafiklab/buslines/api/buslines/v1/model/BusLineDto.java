package se.npet.trafiklab.buslines.api.buslines.v1.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class BusLineDto {
  private Integer id;
  private String designation;
  private LocalDate existsFrom;
  private long numberOfDistinctStops;
  private List<BusRouteDto> routes;
}
