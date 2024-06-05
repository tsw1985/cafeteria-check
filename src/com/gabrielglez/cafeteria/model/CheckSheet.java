package com.gabrielglez.cafeteria.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CheckSheet implements Serializable{
	
	@DatabaseField(generatedId=true , columnName="idCheckSheet")
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true , columnName="idOperator")
	private Operator operator;

	@DatabaseField(foreign = true, foreignAutoRefresh = true , columnName="idCustomer")
	private Customer customer;
	
	@ForeignCollectionField(eager=true)
	private Collection<CheckSheetReplacement> checkSheetReplacementList = new ArrayList<CheckSheetReplacement>();

	
	@DatabaseField(dataType = DataType.DATE_LONG)
	private Date date;
	
	private boolean selected;
	
	public CheckSheet(){}

	
	public CheckSheet(Operator operator, Customer customer,	Collection<CheckSheetReplacement> checkSheetReplacementList,Date date) {
		this.operator = operator;
		this.customer = customer;
		this.checkSheetReplacementList = checkSheetReplacementList;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Collection<CheckSheetReplacement> getCheckSheetReplacementList() {
		return checkSheetReplacementList;
	}
	public void setCheckSheetReplacementList(
			Collection<CheckSheetReplacement> checkSheetReplacementList) {
		this.checkSheetReplacementList = checkSheetReplacementList;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}