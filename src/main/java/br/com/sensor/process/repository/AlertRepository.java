package br.com.sensor.process.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.sensor.temperature.model.Alert;

public interface AlertRepository extends MongoRepository<Alert, String>{

}
