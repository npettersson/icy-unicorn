package se.npet.trafiklab.icyunicorn.api.buslines.v1.model;

import java.util.List;
import lombok.Data;

@Data
public class BusRouteDto {
  private RouteDirection direction;
  private List<BusStopDto> stops;
}
