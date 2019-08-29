package br.com.sensor.process.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sensor.process.repository.TemperatureRepository;
import br.com.sensor.temperature.model.Temperature;

@Service
public class TemperatureService {

	@Autowired
	private TemperatureRepository temperatureRepository;
	
	@Autowired
	private AlertService alertService;
	
	public boolean save(Temperature temperature) {
		
		try {
			
			if(temperature.getValue() < 14.00) {
				alertService.save("Temperatura abaixo do normal.", temperature.getValue());
				
			}else if(temperature.getValue() > 16.00) {
				alertService.save("Temperatura acima do normal.", temperature.getValue());
			}
			
			temperatureRepository.save(temperature);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
}
