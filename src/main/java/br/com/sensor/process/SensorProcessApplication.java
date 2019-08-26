package br.com.sensor.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SpringBootApplication
public class SensorProcessApplication implements CommandLineRunner {
	private static Logger LOG = LoggerFactory.getLogger(SensorProcessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SensorProcessApplication.class, args);
	}

	@SqsListener("myQueue")
	public void listen(String payload) {
		LOG.info("!!!! received message {} ", payload);
	}

	// public void listen(DataObject message) {
	// LOG.info("!!!! received message {} {}", message.getSensor(),
	// message.getValue());
	// }

	@Override
	public void run(String... args) throws Exception {
	}

	public static class DataObject {
		private String sensor;
		private Double value;

		@JsonCreator
		public DataObject(@JsonProperty("sensor") String sensor, @JsonProperty("value") Double value) {
			this.sensor = sensor;
			this.value = value;
		}

		public String getSensor() {
			return sensor;
		}

		public Double getValue() {
			return value;
		}
	}

}
