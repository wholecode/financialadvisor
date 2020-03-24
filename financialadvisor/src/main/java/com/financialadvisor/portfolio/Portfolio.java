package com.financialadvisor.portfolio;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.financialadvisor.exception.UserException;



public class Portfolio {
	
	
	double totalPortfolioValue;
	
	Map<ASSET_NAME, Asset> assets;
	
	public Portfolio()
	{
		assets = new HashMap<>();
		assets.put(ASSET_NAME.BOND, 	 new Asset(ASSET_NAME.BOND, 0));
		assets.put(ASSET_NAME.LARGE_CAP, new Asset(ASSET_NAME.LARGE_CAP, 0));
		assets.put(ASSET_NAME.MID_CAP, 	 new Asset(ASSET_NAME.MID_CAP, 0));
		assets.put(ASSET_NAME.FOREIGN, 	 new Asset(ASSET_NAME.FOREIGN, 0));
		assets.put(ASSET_NAME.SMALL_CAP, new Asset(ASSET_NAME.SMALL_CAP, 0));		
	}
	
		
	public Portfolio(
		double bondValue,
		double largeCapValue,
		double midCapValue,
		double foreignValue,
		double smallCap)
	{
		assets = new HashMap<>();
		assets.put(ASSET_NAME.BOND, 	 new Asset(ASSET_NAME.BOND, bondValue));
		assets.put(ASSET_NAME.LARGE_CAP, new Asset(ASSET_NAME.LARGE_CAP, largeCapValue));
		assets.put(ASSET_NAME.MID_CAP, 	 new Asset(ASSET_NAME.MID_CAP, midCapValue));
		assets.put(ASSET_NAME.FOREIGN, 	 new Asset(ASSET_NAME.FOREIGN, foreignValue));
		assets.put(ASSET_NAME.SMALL_CAP, new Asset(ASSET_NAME.SMALL_CAP, smallCap));
		
		totalPortfolioValue =  bondValue
							 + largeCapValue
							 + midCapValue
							 + foreignValue
							 + smallCap;
		
		for (ASSET_NAME name : ASSET_NAME.values()) {
			Asset asset = assets.get(name);
			asset.percentageOfPortfolio = asset.value/totalPortfolioValue * 100;
		}
	}
	
	
	public synchronized void setAssetValue(
		ASSET_NAME name,
		double value) 
	{	
		if (name == null) {
			throw new IllegalArgumentException("Asset name cannot be null");
		}
		
		if (value < 0) {
			throw new UserException("Asset cannot have a negative value");
		}
		
		Asset asset = assets.get(name);
		double currentAssetValue = asset.getValue();
		
		totalPortfolioValue -= currentAssetValue;
		
		totalPortfolioValue += value;
		asset.setValue(value, totalPortfolioValue);
	}

	
	
	public synchronized double getAssetValue(ASSET_NAME name) {
		
		if (name == null) {
			throw new IllegalArgumentException("Asset name cannot be null");
		}

		return assets.get(name).getValue();
	}
	
	
	public synchronized double getAssetPercentage (ASSET_NAME name) 
	throws Exception 
	{
		if (name == null) {
			throw new IllegalArgumentException("Asset name cannot be null");
		}

		if (totalPortfolioValue == 0) {
			throw new UserException("No assets in the portfolio");
		}
		return assets.get(name).getPercentageOfPortfolio();
	}
	
	
	
	public synchronized void optimize(
		double minAbsoluteDifferenceToReallocate,
		RiskBasedRecommendation recommended) 
	throws Exception
	{
		if (recommended == null) {
			throw new IllegalArgumentException("recommended cannot be null");
		}
		
		if (totalPortfolioValue == 0) {
			throw new UserException("No assets in the portfolio");
		}


		List<ASSET_NAME> assetsToMoveFrom = new LinkedList<>();
		List<ASSET_NAME> assetsToMoveTo = new LinkedList<>();
		
		separateBetweenFromAndToReallacation(minAbsoluteDifferenceToReallocate,
											 recommended,
											 assetsToMoveFrom,
											 assetsToMoveTo);						

		reallocateAssets(recommended, assetsToMoveFrom, assetsToMoveTo);
	}
	
	
	
	
	private  void reallocateAssets(
		RiskBasedRecommendation recommended,
		List<ASSET_NAME> assetsToMoveFrom,
		List<ASSET_NAME> assetsToMoveTo) 
	{
				
		double totalMoneyToMove = 0;
		
		for (ASSET_NAME name : assetsToMoveFrom) {
			
			double recommendedPercentage = recommended.getAssetPercentage(name);
			double recommendedAmount = (totalPortfolioValue / 100) * recommendedPercentage;
			double moneyToTakeOut = assets.get(name).getValue() - recommendedAmount;
			
			totalMoneyToMove += moneyToTakeOut;
			
			assets.get(name).setValue(recommendedAmount, totalPortfolioValue);			
		}
		
		AssetAllocationDifference[] assetsAndExtraAmountRequired = new AssetAllocationDifference[assetsToMoveTo.size()];
		int i=0;
		
		for (ASSET_NAME name : assetsToMoveTo)  {
			double recommendedPercentage = recommended.getAssetPercentage(name);
			double recommendedAmount = (totalPortfolioValue / 100) * recommendedPercentage;
			double extraNeeded = recommendedAmount - assets.get(name).getValue();
			
			assetsAndExtraAmountRequired[i++] = new AssetAllocationDifference(name, extraNeeded);			
		}
		
		
		// get the asset that needs the most on top
		Arrays.sort(assetsAndExtraAmountRequired, Collections.reverseOrder());
		
		for (AssetAllocationDifference assetDifference : assetsAndExtraAmountRequired) {
			
			ASSET_NAME name = assetDifference.assetName;
			
			double needed = assetDifference.amount;
			
			if (totalMoneyToMove > needed) {
				assets.get(name).addValue(needed, totalPortfolioValue);
				totalMoneyToMove -= needed;			
			}
			else if (totalMoneyToMove <= needed) {
				assets.get(name).addValue(totalMoneyToMove, totalPortfolioValue);
				totalMoneyToMove = 0;
				break;
			}
		}
		
		
		// add anything left over to the one that needed the most - the one on the top of the sorted array
		// it will now be over subscribed.
		if  (totalMoneyToMove > 0) {
			ASSET_NAME name = assetsAndExtraAmountRequired[0].assetName;
			assets.get(name).addValue(totalMoneyToMove, totalPortfolioValue);
		}
	}
	
	
	
	private void separateBetweenFromAndToReallacation(
		double minAbsoluteDifferenceToReallocate,
		RiskBasedRecommendation  advised,
		List<ASSET_NAME> assetsToMoveFrom,
		List<ASSET_NAME> assetsToMoveTo) 
	throws Exception 
	{	
		
		for (ASSET_NAME name : ASSET_NAME.values()) {
			double currentPercentage = this.getAssetPercentage(name);
			double advisedPercentage = advised.getAssetPercentage(name);
			
			if (Math.abs(currentPercentage - advisedPercentage) < minAbsoluteDifferenceToReallocate){
				continue;
			}
			
			if (currentPercentage - advisedPercentage < 0) {
				assetsToMoveTo.add(name);
			}
			else {
				assetsToMoveFrom.add(name);						
			}			
		}		
	}



	
	/**
	 * class to keep track of the portfolio asset and difference between its 
	 * allocation and recommended allocation
	 */
	public class AssetAllocationDifference implements Comparable{
		
		ASSET_NAME assetName;
		double amount;
		
				
		public AssetAllocationDifference(
			ASSET_NAME assetName, 
			double amount) 
		{
			super();
			this.assetName = assetName;
			this.amount = amount;
		}


		@Override
		public int compareTo(Object object) {
			
			if (null == object) {
				throw new IllegalArgumentException("Object passed in null");
			}
			
			if ( !(object instanceof AssetAllocationDifference) ) {
				throw new IllegalArgumentException("Object passed is not an PortfolioDifference object");
			}
			
			AssetAllocationDifference passedInObject = (AssetAllocationDifference) object;
			
			if (this.amount < passedInObject.amount) {
				return -1;
			}
			else if (this.amount > passedInObject.amount) {
				return 1;
			}
			
			return 0;			
		}		
		
	}

}
