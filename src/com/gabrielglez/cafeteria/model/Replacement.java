package com.gabrielglez.cafeteria.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Replacement implements Serializable{
	
	@DatabaseField(generatedId=true , columnName="idReplacement")
	private Integer id;
		
	@DatabaseField(canBeNull=false , columnName="name")
	private String name;
	
	@DatabaseField(canBeNull=true , columnName="observation")
	private String observation;
	
	@DatabaseField(canBeNull=true , columnName="used" ,defaultValue="NO")
	private String used;
	
	@DatabaseField(canBeNull=false , columnName="deleted")
	private String deleted;
	
	private boolean selected;
	
	private String observationInCheck;
	
	public Replacement(String name, String observation, String deleted) {
		this.name = name;
		this.observation = observation;
		this.deleted = deleted;
	}

	public Replacement(){}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getObservationInCheck() {
		return observationInCheck;
	}
	public void setObservationInCheck(String observationInCheck) {
		this.observationInCheck = observationInCheck;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}