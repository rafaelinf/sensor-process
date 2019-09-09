package br.com.sensor.process.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sensor.process.exception.TemperatureException;
import br.com.sensor.process.repository.TemperatureRepository;
import br.com.sensor.temperature.model.Alert;
import br.com.sensor.temperature.model.Temperature;

@Service
public class TemperatureService {
	private static Logger LOG = LoggerFactory.getLogger(TemperatureService.class);
	
	@Autowired
	private TemperatureRepository temperatureRepository;
	
	@Autowired
	private AlertService alertService;
	
	public Temperature save(Temperature temperature) 
			throws TemperatureException {
		
		if(temperature != null) {
			
			if(temperature.getSensor() != null && !temperature.getSensor().equals("")) {
				
				if(temperature.getValue() != null) {
					
					if(temperature.getValue() > 0.00) {
						
						if(temperature.getDateCreate() != null) {
							
							verifyNeedGenerateAlert(temperature);
							
							temperatureRepository.save(temperature);
							return temperature;
							
						}else {
							LOG.warn("O campo Data está vazio");
							throw new TemperatureException("O campo Data está vazio");
						}
						
					}else {
						LOG.warn("O campo Valor tem que ser maior que 0.00");
						throw new TemperatureException("O campo Valor tem que ser maior que 0.00");
					}					
					
				}else {
					LOG.warn("O campo Valor é obrigatório");
					throw new TemperatureException("O campo Valor é obrigatório");
				}
				
			}else {
				LOG.warn("O campo Sensor é obrigatório");
				throw new TemperatureException("O campo Sensor é obrigatório");
			}
			
		}else {
			LOG.warn("A temperatura está vazia");
			throw new TemperatureException("A temperatura está vazia");
		}
	}

	private void verifyNeedGenerateAlert(Temperature temperature) {
		
		Alert alert = new Alert();
		alert.setDtCreate(new Date());
		alert.setTemperatureValue(temperature.getValue());								

		if(temperature.getValue() < 14.00) {
			alert.setDescription("Temperatura abaixo do normal");
			alertService.save(alert);
			
		}else if(temperature.getValue() > 16.00) {
			alert.setDescription("Temperatura acima do normal");
			alertService.save(alert);
		}
		
	}
	
	public boolean delete(Temperature temperature)
			throws TemperatureException {
		
		if(temperature != null) {
			temperatureRepository.delete(temperature);
			return true;
		}else {
			throw new TemperatureException("A temperatura está vazia");
		}
		
	}
	
	public Temperature findById(String id) { 
		return temperatureRepository.findById(id).orElse(null);
	}
	
	public List<Temperature> findTemperatureAll(){
		return temperatureRepository.findAll();
	}

	public List<Temperature> findBySensor(String sensor) { 
		return temperatureRepository.findBySensor(sensor);
	}
	
}
