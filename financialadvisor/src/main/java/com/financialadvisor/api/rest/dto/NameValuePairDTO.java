package com.financialadvisor.api.rest.dto;

public class NameValuePairDTO {
	
	public String name;
	public double number;
	
	
	public NameValuePairDTO(String name, double number) {
		super();
		this.name = name;
		this.number = number;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getNumber() {
		return number;
	}


	public void setNumber(double number) {
		this.number = number;
	}
	
	
}
