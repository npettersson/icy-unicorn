package se.npet.trafiklab.icyunicorn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.npet.trafiklab.icyunicorn.domain.BusLinesStore;
import se.npet.trafiklab.icyunicorn.domain.BusLinesStoreFactory;

@Configuration
public class BusLinesStoreConfig {

  private final BusLinesStoreFactory busLinesStoreFactory;

  public BusLinesStoreConfig(BusLinesStoreFactory busLinesStoreFactory) {
    this.busLinesStoreFactory = busLinesStoreFactory;
  }

  @Bean(name = "busLinesStore")
  public BusLinesStore createBusLinesStore() {
    return busLinesStoreFactory.createBusLinesStore();
  }
}
