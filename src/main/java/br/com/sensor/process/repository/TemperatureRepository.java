package br.com.sensor.process.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.sensor.temperature.model.Temperature;

public interface TemperatureRepository extends MongoRepository<Temperature, String>{

}
