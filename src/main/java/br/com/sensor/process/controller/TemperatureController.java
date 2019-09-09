package br.com.sensor.process.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sensor.process.service.TemperatureService;
import br.com.sensor.temperature.model.Temperature;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {
	
	@Autowired
	private TemperatureService temperatureService;
	
	@PostMapping(value = "/save")
	public ResponseEntity<Temperature> save(@RequestBody Temperature temperature) {
		
		Temperature result = temperatureService.save(temperature);
		
		if(result == null)
			return ResponseEntity.noContent().build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/temperature/findTemperatureById/{id}").build()
				.expand(result.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}

	@GetMapping("/findTemperatureAll")
	public List<Temperature> findTemperatureAll(){
		return temperatureService.findTemperatureAll();
	}
	
	@GetMapping("/findTemperatureBySensor/{sensor}")
	public List<Temperature> findTemperatureBySensor(@PathVariable String sensor){
		return temperatureService.findBySensor(sensor);
	}
	
	@GetMapping("/findTemperatureById/{id}")
	public Temperature findTemperatureById(@PathVariable String id){
		return temperatureService.findById(id);
	}
	
}
