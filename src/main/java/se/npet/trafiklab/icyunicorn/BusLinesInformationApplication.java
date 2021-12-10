package se.npet.trafiklab.icyunicorn;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
		title = "API for SL bus information",
		version = "1.0"
))
@SpringBootApplication
public class BusLinesInformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusLinesInformationApplication.class, args);
	}

}
