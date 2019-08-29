package br.com.sensor.process;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import com.fasterxml.jackson.core.JsonProcessingException;

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

	@SqsListener("myQueue")
	public void listen(String payload) {
		LOG.info("!!!! received message {} ", payload);
		
		Temperature temperature = null;
		try {
			temperature = new Temperature().fromJSON(payload);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		temperatureService.save(temperature);
		
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
