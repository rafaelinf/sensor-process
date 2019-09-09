package br.com.sensor.process.aws;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.sensor.process.service.TemperatureService;
import br.com.sensor.temperature.model.Temperature;

@Configuration
public class AwsListen {
	private static Logger LOG = LoggerFactory.getLogger(AwsListen.class);

	@Autowired
	private TemperatureService temperatureService;

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
}
