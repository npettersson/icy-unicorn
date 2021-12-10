package se.npet.trafiklab.icyunicorn.adapters.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.npet.trafiklab.icyunicorn.api.buslines.v1.BusLinesV1ApiMapper;
import se.npet.trafiklab.icyunicorn.api.buslines.v1.model.BusLineDto;
import se.npet.trafiklab.icyunicorn.domain.service.BusLinesService;

@RestController
@RequestMapping(value = "/busline", produces = "application/json")
@Tag(name = "Bus lines service v1", description = "REST API to get info about SL bus lines")
public class BusLinesControllerV1 {

  private final BusLinesService busLinesService;
  private final BusLinesV1ApiMapper mapper;

  public BusLinesControllerV1(BusLinesService busLinesService, BusLinesV1ApiMapper mapper) {
    this.busLinesService = busLinesService;
    this.mapper = mapper;
  }

  @Operation(summary = "Get a bus line with their routes and stops by the line id")
  @GetMapping("/{lineId}")
  public BusLineDto getBusLineByLineId(@PathVariable String lineId) {
    return mapper.toBusLinesDto(busLinesService.getBusLineByLineId(lineId));
  }

  @Operation(summary = "Get the top bus lines that has the most stops on their routes")
  @GetMapping("/stops/top/{limit}")
  public List<BusLineDto> getTopBusLinesByNumberOfStops(@PathVariable int limit){
    return busLinesService.getTopBusLinesByNumberOfStops(limit).stream()
        .map(mapper::toBusLinesDto)
        .collect(Collectors.toList());
  }

}
