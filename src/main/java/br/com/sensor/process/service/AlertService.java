package br.com.sensor.process.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sensor.process.exception.AlertException;
import br.com.sensor.process.repository.AlertRepository;
import br.com.sensor.temperature.model.Alert;

@Service
public class AlertService {
	private static Logger LOG = LoggerFactory.getLogger(AlertService.class);

	@Autowired
	private AlertRepository alertRepository;
	
	public Alert save(Alert alert) 
			throws AlertException{
		
		if(alert != null) {
			
			if(alert.getDescription() != null && !alert.getDescription().equals("")) {
				
				if(alert.getTemperatureValue() != null) {
					
					if(alert.getTemperatureValue() > 0.00) {
						
						alertRepository.save(alert);
						return alert;
						
					}else {
						LOG.info("O campo Valor tem que ser maior que 0.00");
						throw new AlertException("O campo Valor tem que ser maior que 0.00");					
					}					
				}else {
					LOG.info("O valor da temperatura é obrigatório");
					throw new AlertException("O valor da temperatura é obrigatório");					
				}
				
			}else {
				LOG.info("A descrição é obrigatória");
				throw new AlertException("A descrição é obrigatória");
			}
			
		}else {
			LOG.info("O Alerta está vazio");
			throw new AlertException("O Alerta está vazio");
		}

	}	
	
}
