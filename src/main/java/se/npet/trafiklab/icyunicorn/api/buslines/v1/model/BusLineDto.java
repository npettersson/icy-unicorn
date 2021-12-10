package se.npet.trafiklab.icyunicorn.api.buslines.v1.model;

import java.util.List;
import lombok.Data;

@Data
public class BusLineDto {
  private String lineId;
  private List<BusRouteDto> routes;
}
