package br.com.sensor.process.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sensor.process.repository.AlertRepository;
import br.com.sensor.temperature.model.Alert;

@Service
public class AlertService {

	@Autowired
	private AlertRepository alertRepository;
	
	public boolean save(String description, Double temperatureValue) {
		
		Alert alert = new Alert();
		alert.setDescription(description);
		alert.setDtCreate(new Date());
		alert.setTemperatureValue(temperatureValue);
		alertRepository.save(alert);
		return true;
		
	}
}
