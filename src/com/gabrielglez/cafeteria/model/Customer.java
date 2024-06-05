package com.gabrielglez.cafeteria.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Customer implements Serializable{
	
	@DatabaseField(generatedId=true , columnName="idCustomer")
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName="name")
	private String name;
	
	@DatabaseField(canBeNull=true , columnName="comercialName")
	private String comercialName;
	
	@DatabaseField(canBeNull=false , columnName="cif")
	private String cif;
	
	@DatabaseField(canBeNull=false , columnName="address")
	private String address;
	
	@DatabaseField(canBeNull=false , columnName="phone")
	private String phone;
	
	@DatabaseField(canBeNull=true , columnName="observation")
	private String observation;
	
	@DatabaseField(canBeNull=false , columnName="deleted")
	private String deleted;

	
	private boolean checked;
	
	private boolean showBackGroundColor;
	
	
	
	public boolean isShowBackGroundColor() {
		return showBackGroundColor;
	}
	public void setShowBackGroundColor(boolean showBackGroundColor) {
		this.showBackGroundColor = showBackGroundColor;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
	
	
	
	public Customer(){}
	
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
	public String getComercialName() {
		return comercialName;
	}
	public void setComercialName(String comercialName) {
		this.comercialName = comercialName;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
}