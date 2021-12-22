package se.npet.trafiklab.buslines.api.buslines.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import se.npet.trafiklab.buslines.api.buslines.v1.model.BusLineAndNbrOfStopsDto;
import se.npet.trafiklab.buslines.api.buslines.v1.model.BusLineDto;
import se.npet.trafiklab.buslines.testutils.BusLinesTestUtils;

class BusLinesV1ApiMapperTest {

  private final BusLinesV1ApiMapper mapper = Mappers.getMapper(BusLinesV1ApiMapper.class);

  @Test
  void toBusLinesDto() {
    BusLineDto busLineDto = mapper.toBusLinesDto(BusLinesTestUtils.makeBusLine(42, 4));
    assertThat(busLineDto).isNotNull();
    assertThat(busLineDto.getId()).isEqualTo(42);
    assertThat(busLineDto.getDesignation()).isNotNull().containsIgnoringCase("42");
    assertThat(busLineDto.getRoutes()).isNotEmpty().hasSize(2);
  }

  @Test
  void toBusLineAndNbrOfStopsDto() {
    BusLineAndNbrOfStopsDto nbrOfStopsDto = mapper.toBusLineAndNbrOfStopsDto(BusLinesTestUtils.makeBusLine(42, 4));
    assertThat(nbrOfStopsDto).isNotNull();
    assertThat(nbrOfStopsDto.getId()).isEqualTo(42);
    assertThat(nbrOfStopsDto.getDesignation()).isNotNull().containsIgnoringCase("42");
    assertThat(nbrOfStopsDto.getNumberOfDistinctStops()).isEqualTo(4);
  }
}
