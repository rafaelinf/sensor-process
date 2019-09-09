package br.com.sensor.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.sensor.process.service.TemperatureService;
import br.com.sensor.temperature.model.Temperature;

@SpringBootApplication
public class SensorProcessApplication implements CommandLineRunner {
	private static Logger LOG = LoggerFactory.getLogger(SensorProcessApplication.class);

	@Autowired
	private TemperatureService temperatureService;
	
	public static void main(String[] args) {
		SpringApplication.run(SensorProcessApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("!!!! Start application SensorProcessApplication !!!!");
		
		//List<Temperature> list = temperatureService.findTemperatureAll();
		//List<Temperature> list = temperatureService.findBySensor("Temperatura");
		//LOG.info("Total de Registros: " + list.size());
	}

}
