package com.inicu.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.analytics.entities.FittingResult;

public class SignalStructure {

	public SignalStructure()
	{
		uhidList = new ArrayList<String>();
	}
	List<String> uhidList = null;
	HashMap<String, String> recommendationStatusUHID = new HashMap<String, String>();
	HashMap<String, List<Double>> probabilityBaseMeanHR = new HashMap<String, List<Double>>();
	HashMap<String, List<Double>> probabilityBaseVarHR = new HashMap<String, List<Double>>();
	HashMap<String, List<Double>> probabilityResidualVarHR = new HashMap<String, List<Double>>();

	
	HashMap<String, Double> baseSignalMean = new HashMap<String, Double>();
	HashMap<String, Double> baseSignalVar = new HashMap<String, Double>();
	HashMap<String, Double> residualSignalVar= new HashMap<String, Double>();
	
	HashMap<String, List<Float>> residualListMap = new HashMap<String, List<Float>>();
	HashMap<String, List<Float>> baseSignalListMap = new HashMap<String, List<Float>>();
	HashMap<String, List<Float>> originalListMap = new HashMap<String, List<Float>>();
	
	RegressionResult fittingResultBaseMeanHR_LM = null;
	RegressionResult fittingResultBaseVarHR_LM = null;
	RegressionResult fittingResultResidualVarHR_LM = null;	

	RegressionResult fittingResultBaseMeanHR_HM = null;
	RegressionResult fittingResultBaseVarHR_HM = null;
	RegressionResult fittingResultResidualVarHR_HM = null;	

	
	public HashMap<String, String> getRecommendationStatusUHID() {
		return recommendationStatusUHID;
	}
	public void setRecommendationStatusUHID(HashMap<String, String> recommendationStatusUHID) {
		this.recommendationStatusUHID = recommendationStatusUHID;
	}
	public RegressionResult getFittingResultBaseMeanHR_HM() {
		return fittingResultBaseMeanHR_HM;
	}
	public void setFittingResultBaseMeanHR_HM(RegressionResult fittingResultBaseMeanHR_HM) {
		this.fittingResultBaseMeanHR_HM = fittingResultBaseMeanHR_HM;
	}
	public RegressionResult getFittingResultBaseVarHR_HM() {
		return fittingResultBaseVarHR_HM;
	}
	public void setFittingResultBaseVarHR_HM(RegressionResult fittingResultBaseVarHR_HM) {
		this.fittingResultBaseVarHR_HM = fittingResultBaseVarHR_HM;
	}
	public RegressionResult getFittingResultResidualVarHR_HM() {
		return fittingResultResidualVarHR_HM;
	}
	public void setFittingResultResidualVarHR_HM(RegressionResult fittingResultResidualVarHR_HM) {
		this.fittingResultResidualVarHR_HM = fittingResultResidualVarHR_HM;
	}
	public List<String> getUhidList() {
		return uhidList;
	}
	public void setUhid(List<String> uhidList) {
		this.uhidList = uhidList;
	}
	public RegressionResult getFittingResultBaseMeanHR_LM() {
		return fittingResultBaseMeanHR_LM;
	}
	public void setFittingResultBaseMeanHR_LM(RegressionResult fittingResultBaseMeanHR_LM) {
		this.fittingResultBaseMeanHR_LM = fittingResultBaseMeanHR_LM;
	}
	public RegressionResult getFittingResultBaseVarHR_LM() {
		return fittingResultBaseVarHR_LM;
	}
	public void setFittingResultBaseVarHR_LM(RegressionResult fittingResultBaseVarHR_LM) {
		this.fittingResultBaseVarHR_LM = fittingResultBaseVarHR_LM;
	}
	public RegressionResult getFittingResultResidualVarHR_LM() {
		return fittingResultResidualVarHR_LM;
	}
	public void setFittingResultResidualVarHR_LM(RegressionResult fittingResultResidualVarHR_LM) {
		this.fittingResultResidualVarHR_LM = fittingResultResidualVarHR_LM;
	}
	public HashMap<String, List<Double>> getProbabilityBaseVarHR() {
		return probabilityBaseVarHR;
	}
	public void setProbabilityBaseVarHR(HashMap<String, List<Double>> probabilityBaseVarHR) {
		this.probabilityBaseVarHR = probabilityBaseVarHR;
	}
	public HashMap<String, List<Double>> getProbabilityResidualVarHR() {
		return probabilityResidualVarHR;
	}
	public void setProbabilityResidualVarHR(HashMap<String, List<Double>> probabilityResidualVarHR) {
		this.probabilityResidualVarHR = probabilityResidualVarHR;
	}
	public HashMap<String, List<Double>> getProbabilityBaseMeanHR() {
		return probabilityBaseMeanHR;
	}
	public void setProbabilityBaseMeanHR(HashMap<String, List<Double>> probabilityMeanHR) {
		this.probabilityBaseMeanHR = probabilityMeanHR;
	}
	public HashMap<String, Double> getBaseSignalMean() {
		return baseSignalMean;
	}
	public void setBaseSignalMean(HashMap<String, Double> baseSignalMean) {
		this.baseSignalMean = baseSignalMean;
	}
	public HashMap<String, Double> getBaseSignalVar() {
		return baseSignalVar;
	}
	public void setBaseSignalVar(HashMap<String, Double> baseSignalVar) {
		this.baseSignalVar = baseSignalVar;
	}
	public HashMap<String, Double> getResidualSignalVar() {
		return residualSignalVar;
	}
	public void setResidualSignalVar(HashMap<String, Double> residualSignalVar) {
		this.residualSignalVar = residualSignalVar;
	}
	public HashMap<String, List<Float>> getResidualListMap() {
		return residualListMap;
	}
	public void setResidualListMap(HashMap<String, List<Float>> residualListMap) {
		this.residualListMap = residualListMap;
	}
	public HashMap<String, List<Float>> getBaseSignalListMap() {
		return baseSignalListMap;
	}
	public void setBaseSignalListMap(HashMap<String, List<Float>> baseSignalListMap) {
		this.baseSignalListMap = baseSignalListMap;
	}
	public HashMap<String, List<Float>> getOriginalListMap() {
		return originalListMap;
	}
	public void setOriginalListMap(HashMap<String, List<Float>> originalListMap) {
		this.originalListMap = originalListMap;
	}

	
}
