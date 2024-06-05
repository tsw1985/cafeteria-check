package com.gabrielglez.cafeteria.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CheckSheetReplacement {

	@DatabaseField(generatedId=true,columnName="idCheckSheetReplacement")
	private Integer id;
	
	@DatabaseField(foreign=true , columnName="idCheckSheetFore")
	private CheckSheet checkSheet;
	
	@DatabaseField(foreign=true , columnName="idReplacement")
	private ReplacementNotEntity replacement;

	@DatabaseField(columnName="observationInCheck")
	private String observationInCheck;


	public CheckSheetReplacement(){}
	
	
	public CheckSheetReplacement(CheckSheet cheekSheet , ReplacementNotEntity replacement){
		this.checkSheet = cheekSheet;
		this.replacement = replacement;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CheckSheet getCheckSheet() {
		return checkSheet;
	}
	public void setCheckSheet(CheckSheet checkSheet) {
		this.checkSheet = checkSheet;
	}
	public ReplacementNotEntity getReplacement() {
		return replacement;
	}
	public void setReplacement(ReplacementNotEntity replacement) {
		this.replacement = replacement;
	}
	public String getObservationInCheck() {
		return observationInCheck;
	}
	public void setObservationInCheck(String observationInCheck) {
		this.observationInCheck = observationInCheck;
	}
}