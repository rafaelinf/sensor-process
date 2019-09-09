package br.com.sensor.process.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.sensor.temperature.model.Temperature;

public interface TemperatureRepository extends MongoRepository<Temperature, String>{

	List<Temperature> findBySensor(String sensor);
	
	//@Query("SELECT s FROM Sensor s WHERE s.List<Temperature> findBySensor(String sensor);")
	//List<Temperature> findTemperatureBySensor(String sensor);
	
}
