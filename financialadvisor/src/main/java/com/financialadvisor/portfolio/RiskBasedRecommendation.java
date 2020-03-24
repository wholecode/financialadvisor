package com.financialadvisor.portfolio;

import java.util.HashMap;
import java.util.Map;

public class RiskBasedRecommendation {

	int riskLevel;
	
	Map<ASSET_NAME, Double> assetPercentages = new HashMap<>();
	
	public RiskBasedRecommendation (
		int riskLevel,
		double bondPercentage, 
		double largeCapPercentage, 
		double midCapPercentage, 
		double foreignPercentage, 
		double smallCapPercentage) 
	{
		assetPercentages.put(ASSET_NAME.BOND, 		bondPercentage);
		assetPercentages.put(ASSET_NAME.LARGE_CAP, 	largeCapPercentage);
		assetPercentages.put(ASSET_NAME.MID_CAP, 	midCapPercentage);
		assetPercentages.put(ASSET_NAME.FOREIGN, 	foreignPercentage);
		assetPercentages.put(ASSET_NAME.SMALL_CAP, 	smallCapPercentage);		
	}
	
		
	public double getAssetPercentage(ASSET_NAME assetName) {
		
		return assetPercentages.get(assetName).doubleValue();
		
	}
	
}