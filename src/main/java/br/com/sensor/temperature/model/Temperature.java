package br.com.sensor.temperature.model;

import java.io.IOException;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Document(collection = "temperature")
public class Temperature {

	@Id
	private String id;
	private String sensor;
	private Double value;
	private Date dateCreate;

	public Temperature() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Override
	public String toString() {
		return "Temperature{" + "sensor='" + sensor + '\'' + ", value=" + value + '}';
	}

	public Temperature fromJSON(String json) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, Temperature.class);
	}
	
}
