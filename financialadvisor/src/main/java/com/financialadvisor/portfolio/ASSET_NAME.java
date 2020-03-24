package com.financialadvisor.portfolio;

public enum ASSET_NAME {
	
	BOND ("Bond"),
	LARGE_CAP ("Large Cap"),
	MID_CAP ("Mid Cap"),
	FOREIGN ("Foreign"),
	SMALL_CAP ("Small Cap");
		
	ASSET_NAME(String displayName)
	{
		this.displayName = displayName;
	}
	
	private String displayName;
	
	public String getDisplayName() {
		return displayName;
	}
	
}
