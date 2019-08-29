package br.com.sensor.temperature.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alert")
public class Alert {

	@Id
	private String id;
	private String description;
	private Date dtCreate;
	private Double temperatureValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Double getTemperatureValue() {
		return temperatureValue;
	}

	public void setTemperatureValue(Double temperatureValue) {
		this.temperatureValue = temperatureValue;
	}

}
