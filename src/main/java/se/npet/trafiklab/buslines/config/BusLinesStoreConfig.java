package se.npet.trafiklab.buslines.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.npet.trafiklab.buslines.domain.BusLinesStore;
import se.npet.trafiklab.buslines.domain.BusLinesStoreFactory;

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
