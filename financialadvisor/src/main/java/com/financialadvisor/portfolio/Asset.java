package com.financialadvisor.portfolio;

public class Asset {
	
	ASSET_NAME name;
	double value;
	double percentageOfPortfolio;
	
	
	public Asset(
		ASSET_NAME name, 
		double value) 
	{
		super();
		this.name = name;
		this.value = value;
	}


	public double getValue() {
		return value;
	}


	public void setValue(
		double assetValue,
		double portfolioValue) 
	{
		this.value = assetValue;
		percentageOfPortfolio = (value/portfolioValue) * 100;
	}

	public void addValue(
		double addValue,
		double portfolioValue) 
	{
		this.value += addValue;
		percentageOfPortfolio = (value/portfolioValue) * 100;
	}


	public double getPercentageOfPortfolio() {
		return percentageOfPortfolio;
	}
	
	
	
	
	
}
