package com.gabrielglez.cafeteria.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Operator implements Serializable{
	
	@DatabaseField(generatedId=true, columnName="idOperator")
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName="name")
	private String name;
	
	@DatabaseField(canBeNull=true , columnName="dni")
	private String dni;
	
	@DatabaseField(canBeNull=false , columnName="user")
	private String user;
	
	@DatabaseField(canBeNull=false , columnName="password")
	private String password;
	
	@DatabaseField(canBeNull=false , columnName="deleted")
	private String deleted;

	@Override
	public String toString(){
		return name;
	}
	
	
	
	public Operator(){}
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}