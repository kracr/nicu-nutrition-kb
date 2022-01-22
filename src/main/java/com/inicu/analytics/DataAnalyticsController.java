package com.inicu.analytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.models.DeviceGraphAxisJson;
import com.inicu.models.DeviceGraphDataJson;
import com.inicu.postgres.utility.BasicUtils;

@Repository
public class DataAnalyticsController  {
	//implements Runnable
	@Autowired
	DeviceDataService deviceDataService;
	//10 minute sliding window for rolling mean
	int WINDOW_SIZE =10;
	// Create a DescriptiveStats instance and set the window size to 180
	int TOTAL_DATA_SIZE = 180;
	//rolling mean is of 10 points so additional points needed to make two array of same size
	int TOTAL_DATA_SIZE_MEAN = 190;
	
	public DeviceGraphDataJson execute(DeviceGraphDataJson obj) throws Exception {
		System.out.println("in AnalyticsController - calculate Physiscore");
		//For a given baby find its dob and calculate physiscore on first 3 hour data
//		DeviceGraphDataJson graphDataJson = new DeviceGraphDataJson();
//		//Test demo patient -RSHI.0000008539 data upto  2018-01-17 10:15:30.902+05:30
//		String uhid = "RSHI.0000008539";
//		String fromDate = "2018-01-17";
//		String toDate = "2018-01-17";
//		boolean isDaily = false;
//		String startTime = "04:45:30";
//		String endTime = "10:02:58";
//		
//		String entryDate = "Wed Jan 17 2018 10:15:30 GMT+0530 (IST)";
//		graphDataJson.setUhid(uhid);
//		graphDataJson.setFromdate(fromDate);
//		graphDataJson.setTodate(toDate);
//		graphDataJson.setDaily(isDaily);
//		graphDataJson.setStartTime(startTime);
//		graphDataJson.setEndTime(endTime);
//		DeviceGraphDataJson obj = deviceDataService.getDevGraphData(graphDataJson, entryDate);
		
		int intervalHR = 0;
		int intervalPR = 0;
		int intervalRR = 0;

		if(!BasicUtils.isEmpty(obj.getHR()) && !BasicUtils.isEmpty(obj.getHR().getyAxis()) && obj.getHR().getyAxis().size() > 0) {
			intervalHR = obj.getHR().getyAxis().size();
		}
		if(BasicUtils.isEmpty(obj.getPR()) && !BasicUtils.isEmpty(obj.getPR().getyAxis()) && obj.getPR().getyAxis().size() > 0) {
			intervalPR = obj.getPR().getyAxis().size();
		}
		if(BasicUtils.isEmpty(obj.getRR()) && !BasicUtils.isEmpty(obj.getRR().getyAxis()) && obj.getRR().getyAxis().size() > 0) {
			intervalRR = obj.getRR().getyAxis().size();
		}

		//Right now code is generating physiscore for first 3 hours of life. We need to change it as per user query (moving average)
		//handle ten extra points for rolling mean of ten minutes
		TOTAL_DATA_SIZE_MEAN = intervalHR;
		TOTAL_DATA_SIZE = TOTAL_DATA_SIZE_MEAN -10;

		List<String> xAxisLabelData = new ArrayList<String>();
		//first find HR related base signal and residual signal
		if(!BasicUtils.isEmpty(obj.getHR()) && !BasicUtils.isEmpty(obj.getHR().getyAxis()) && obj.getHR().getyAxis().size() > 0) {
			List<Float> originalHRData = getWindowData(obj.getHR().getyAxis(),TOTAL_DATA_SIZE);
			//first find HR related base signal and residual signal
			List<Float> baseSignalHRData = getRollingMean(getWindowData(obj.getHR().getyAxis(),TOTAL_DATA_SIZE_MEAN));
			List<Float> residualSignalHRData = subtractTwoSignals(originalHRData,baseSignalHRData);
			List<Float> physiScoreyAxisData = generatePhysiScore(residualSignalHRData);


			//Find statistics of base and residual HR signals
			DescriptiveStatistics baseSignalStatisticsHR = DataAnalyticsUtilities.getSignalStatistics(baseSignalHRData);
			DescriptiveStatistics residualSignalStatisticsHR = DataAnalyticsUtilities.getSignalStatistics(residualSignalHRData);
			
			double meanBaseSignalHR = baseSignalStatisticsHR.getMean();
			double varianceBaseSignalMeanHR = baseSignalStatisticsHR.getVariance();
			double varianceResidualSignalHR = residualSignalStatisticsHR.getVariance();
			
			double minResidualHR = residualSignalStatisticsHR.getMin();
			double maxResidualHR = residualSignalStatisticsHR.getMax();

			System.out.println("Returned object for UHID"+obj.getUhid()+" original HR value counts="+originalHRData.size()+
			          " baseSignalHRData count=" + baseSignalHRData.size() + 
			          " residualSignalHRData count=" + residualSignalHRData.size()+
			          " residualSignal HR max=" + maxResidualHR+
			          " residualSignal HR min=" + minResidualHR+
			          " residualSignal varianceHR=" + varianceResidualSignalHR
			          );
			//Set Physiscore specific values in the graph object
			DeviceGraphAxisJson pHYSISCORE = new DeviceGraphAxisJson();
			pHYSISCORE.setxAxis(obj.getHR().getxAxis());
			pHYSISCORE.setyAxis(physiScoreyAxisData);
			
			//Set Short term variability of HR
			DeviceGraphAxisJson sT_HRV = new DeviceGraphAxisJson();
			sT_HRV.setxAxis(obj.getHR().getxAxis());
			sT_HRV.setyAxis(residualSignalHRData);

			//Set Long term variability of HR
			DeviceGraphAxisJson lT_HRV = new DeviceGraphAxisJson();
			lT_HRV.setxAxis(obj.getHR().getxAxis());
			lT_HRV.setyAxis(baseSignalHRData);
			
			//Set residual values for range draw
			obj.setMaxResidualVarianceHR(maxResidualHR);
			obj.setMinResidualVarianceHR(minResidualHR);
			obj.setVarianceResidualVarianceHR(varianceResidualSignalHR);
			//set physiscore specific graph values
			obj.setPHYSISCORE(pHYSISCORE);
			obj.setST_HRV(sT_HRV);
			obj.setLT_HRV(lT_HRV);
			
		}
		
		if(BasicUtils.isEmpty(obj.getRR()) && !BasicUtils.isEmpty(obj.getRR().getyAxis()) && obj.getRR().getyAxis().size() > 0) {
			TOTAL_DATA_SIZE_MEAN = intervalRR;
			TOTAL_DATA_SIZE = TOTAL_DATA_SIZE_MEAN -10;
			
			List<Float> originalRRData = getWindowData(obj.getRR().getyAxis(),TOTAL_DATA_SIZE);		
			//second find RR related base signal and residual signal
			List<Float> baseSignalRRData = getRollingMean(getWindowData(obj.getRR().getyAxis(),TOTAL_DATA_SIZE_MEAN));
			
			List<Float> residualSignalRRData = subtractTwoSignals(originalRRData,baseSignalRRData);
			System.out.println("Returned object for UHID"+obj.getUhid()+
			        originalRRData.size()
			          );
			
			//Find statistics of base and residual RR signals
			DescriptiveStatistics baseSignalStatisticsRR = DataAnalyticsUtilities.getSignalStatistics(baseSignalRRData);		
			DescriptiveStatistics residualSignalStatisticsRR = DataAnalyticsUtilities.getSignalStatistics(residualSignalRRData);
	
			double baseSignalMeanRR = baseSignalStatisticsRR.getMean();
		    double varianceBaseSignalRR = baseSignalStatisticsRR.getVariance();
		    double varianceResidualSignalRR = residualSignalStatisticsRR.getVariance();
		}
		if(BasicUtils.isEmpty(obj.getSPO2()) && !BasicUtils.isEmpty(obj.getSPO2().getyAxis()) && obj.getSPO2().getyAxis().size() > 0) {
			List<Float> originalSPO2Data = getWindowData(obj.getSPO2().getyAxis(),TOTAL_DATA_SIZE);
		    //Find how many times SPO2 is below 85% -  length of below array will tell how many time sp02 was below 85%
	  		List<Float> spO2LessThan85List = getSPO2LessThan85(originalSPO2Data);
	  		//find mean of sp02 value
	  		DescriptiveStatistics spO2SignalStatistics = DataAnalyticsUtilities.getSignalStatistics(originalSPO2Data);
		}
	
			
		//second find SPO2 related values
  		if(BasicUtils.isEmpty(obj.getPR()) && !BasicUtils.isEmpty(obj.getPR().getyAxis()) && obj.getPR().getyAxis().size() > 0) {
  			TOTAL_DATA_SIZE_MEAN = intervalPR;
			TOTAL_DATA_SIZE = TOTAL_DATA_SIZE_MEAN -10;
			
			//Setting Pr Parameters
			List<Float> originalPRData = getWindowData(obj.getPR().getyAxis(),TOTAL_DATA_SIZE);
			List<Float> baseSignalPRData = getRollingMean(getWindowData(obj.getPR().getyAxis(),TOTAL_DATA_SIZE_MEAN));
			List<Float> residualSignalPRData = subtractTwoSignals(originalPRData,baseSignalPRData);
	
			//Set Short term variability of PR
			DeviceGraphAxisJson sT_PRV = new DeviceGraphAxisJson();
			sT_PRV.setxAxis(obj.getPR().getxAxis());
			sT_PRV.setyAxis(residualSignalPRData);
	
			//Set Long term variability of PR
			DeviceGraphAxisJson lT_PRV = new DeviceGraphAxisJson();
			lT_PRV.setxAxis(obj.getPR().getxAxis());
			lT_PRV.setyAxis(baseSignalPRData);
	
			obj.setST_PRV(sT_PRV);
			obj.setLT_PRV(lT_PRV);
  		}
  		
  		
  		//Calculate p(v1|HM) and p(v1|LM) , also store odds ratio in database
//		double[] nonLinear_HR;
//		nonLinear_HR = generateCurveLogOddsRatio(bestResultMeanHR_HM, bestResultMeanHR_LM,
//				RegressionConstants.BaseSignal_MEAN_HR);
//		plotRegressionResult(nonLinear_HR, "Base Mean HR Curve", "Mean HR", "Odds Ratio");
//
//		// Getting the base and residual signal for the complete data set individually
//		// for HM and LM
//		List<Double> baseVarSignalHRData_HM = receivelSignalToFit(signalMap_HM.getBaseSignalVar());
//		List<Double> baseVarSignalHRData_LM = receivelSignalToFit(signalMap_LM.getBaseSignalVar());
//
//		FitDataIntoDistribution baseVarFitDistHR_HM = new FitDataIntoDistribution(baseVarSignalHRData_HM);
//		RegressionResult bestResultBaseVarHR_HM = baseVarFitDistHR_HM
//				.performPhysiScoreSignalAnalysis("Var_Base_HR");
//
//		FitDataIntoDistribution baseVarFitDistHR_LM = new FitDataIntoDistribution(baseVarSignalHRData_LM);
//		RegressionResult bestResultBaseVarHR_LM = baseVarFitDistHR_LM
//				.performPhysiScoreSignalAnalysis("Var_Base_HR");
//
//		nonLinear_HR = generateCurveLogOddsRatio(bestResultBaseVarHR_HM, bestResultBaseVarHR_LM,
//				RegressionConstants.BaseSignal_MEAN_HR);
//		plotRegressionResult(nonLinear_HR, "Base Variance HR Curve", "Base HR", "Odds Ratio");
//
//		// Getting the residual signal for the complete data set individually for HM and
//		// LM
//		List<Double> residualSignalHRData_HM = receivelSignalToFit(signalMap_HM.getResidualSignalVar());
//		List<Double> residualSignalHRData_LM = receivelSignalToFit(signalMap_LM.getResidualSignalVar());
//		System.out.println("residualSignalHRData_HM length=" + residualSignalHRData_HM.size()
//				+ "   residualSignalHRData_LM length=" + residualSignalHRData_LM.size());
//		FitDataIntoDistribution fitDistResidualHR_HM = new FitDataIntoDistribution(residualSignalHRData_HM);
//		RegressionResult bestResultResidualHR_HM = fitDistResidualHR_HM.performPhysiScoreSignalAnalysis("STV_HR");
//		FitDataIntoDistribution fitDistResidualHR_LM = new FitDataIntoDistribution(residualSignalHRData_LM);
//		RegressionResult bestResultResidualHR_LM = fitDistResidualHR_LM.performPhysiScoreSignalAnalysis("STV_HR");
//		nonLinear_HR = generateCurveLogOddsRatio(bestResultResidualHR_HM, bestResultResidualHR_LM,
//				RegressionConstants.Residual_VARIANCE_HR);
//		plotRegressionResult(nonLinear_HR, "Residual HR Curve", "Residual HR", "Odds Ratio");	
  		
  		
  		//To set the variablities in the different format according to the new graph  
		List<List<Object>> stHrFinalData = new ArrayList<>();
  		if(!BasicUtils.isEmpty(obj.getST_HRV().getxAxis()) && !BasicUtils.isEmpty(obj.getST_HRV().getyAxis()) && !BasicUtils.isEmpty(obj.getHRObject())) {
  			for(int i = 0; i < obj.getST_HRV().getyAxis().size(); i++) {
  				List<Object> stHrData = new ArrayList<>();
  				
  				stHrData.add(obj.getHRObject().get(i).get(0));
  				stHrData.add(obj.getST_HRV().getyAxis().get(i));
  				stHrFinalData.add(stHrData);
  			}
  		}
  		obj.setST_HRVObject(stHrFinalData);
  		
		List<List<Object>> ltHrFinalData = new ArrayList<>();
  		if(!BasicUtils.isEmpty(obj.getLT_HRV().getxAxis()) && !BasicUtils.isEmpty(obj.getLT_HRV().getyAxis()) && !BasicUtils.isEmpty(obj.getHRObject())) {
  			for(int i = 0; i < obj.getLT_HRV().getyAxis().size(); i++) {
  				List<Object> ltHrData = new ArrayList<>();
  				
  				ltHrData.add(obj.getHRObject().get(i).get(0));
  				ltHrData.add(obj.getLT_HRV().getyAxis().get(i));
  				ltHrFinalData.add(ltHrData);
  			}
  		}
  		obj.setLT_HRVObject(ltHrFinalData);
  		
		List<List<Object>> ltPrFinalData = new ArrayList<>();
  		if(!BasicUtils.isEmpty(obj.getLT_PRV().getxAxis()) && !BasicUtils.isEmpty(obj.getLT_PRV().getyAxis()) && !BasicUtils.isEmpty(obj.getPRObject())) {
  			for(int i = 0; i < obj.getLT_PRV().getyAxis().size(); i++) {
  				List<Object> ltPrData = new ArrayList<>();
  				
  				ltPrData.add(obj.getPRObject().get(i).get(0));
  				ltPrData.add(obj.getLT_PRV().getyAxis().get(i));
  				ltPrFinalData.add(ltPrData);
  			}
  		}
  		obj.setLT_PRVObject(ltHrFinalData);
  		
		List<List<Object>> stPrFinalData = new ArrayList<>();
  		if(!BasicUtils.isEmpty(obj.getST_PRV().getxAxis()) && !BasicUtils.isEmpty(obj.getST_PRV().getyAxis()) && !BasicUtils.isEmpty(obj.getPRObject())) {
  			for(int i = 0; i < obj.getST_PRV().getyAxis().size(); i++) {
  				List<Object> stPrData = new ArrayList<>();
  				
  				stPrData.add(obj.getPRObject().get(i).get(0));
  				stPrData.add(obj.getST_PRV().getyAxis().get(i));
  				stPrFinalData.add(stPrData);
  			}
  		}
  		obj.setST_PRVObject(stPrFinalData);

  		//This is temporary to reduce the payload
//  		obj.setHR(null);
//  		obj.setPR(null);
//  		obj.setSPO2(null);
//  		obj.getST_HRV().setxAxis(null);
//  		obj.getST_HRV().setyAxis(null);
//  		obj.getLT_HRV().setxAxis(null);
//  		obj.getLT_HRV().setyAxis(null);
//  		obj.getST_PRV().setxAxis(null);
//  		obj.getST_PRV().setyAxis(null);
//  		obj.getLT_PRV().setxAxis(null);
//  		obj.getLT_PRV().setyAxis(null);

  		
		return obj;
	}


	public List<Float> getWindowData(List<Float> inputSignal, int size)
	{
		List<Float> yWindowData = new ArrayList<Float>();	    
		for (int i=0;i<inputSignal.size();i++) {	    	 
	         if (i == size)
	        	 break;
	         yWindowData.add(i,inputSignal.get(i));
		}	
		return yWindowData;
	}
	public List<Float> getRollingMean(List<Float> originalSignal)
	{
		DescriptiveStatistics stats = new DescriptiveStatistics();		
		//Window size is 3 hour of data
		stats.setWindowSize(WINDOW_SIZE);		
	    long nLines = 0;
	    int jCounter =0;
		List<Float> yMeanRollingData = new ArrayList<Float>();	    
		for (int i=0;i<originalSignal.size();i++) {	    	 
        	nLines++;
			stats.addValue(originalSignal.get(i));
	         if (nLines > WINDOW_SIZE) {
	        	 yMeanRollingData.add(jCounter,(float)stats.getMean());
	        	 jCounter++;
	        }
		}	
		return yMeanRollingData;
		
	}
	
	public List<Float> getSPO2LessThan85(List<Float> originalSignal)
	{
		List<Float> ySP02LessThan85Data = new ArrayList<Float>();	
		int jCounter =0;
		for (int i=0;i<originalSignal.size();i++) {	  
			float valueSpO2 = originalSignal.get(i);
	         if (valueSpO2 < 85) {
	        	 ySP02LessThan85Data.add(jCounter,valueSpO2);
	        	 jCounter++;
	        }
		}	
		return ySP02LessThan85Data;
		
	}
	
	public List<Float> generatePhysiScore(List<Float> inputSignal)
	{
		//HS: populate physiscore from database
		
		List<Float> yWindowData = new ArrayList<Float>();	    
		for (int i=0;i<inputSignal.size();i++) {	    	 
	         yWindowData.add(i,new Float(1));
		}	
		return yWindowData;
	}
	
	public List<Float> subtractTwoSignals(List<Float> signal1, List<Float> signal2) throws Exception
	{
		if(signal1.size() != signal2.size())
		{
			throw new Exception("Two list are of non-equal size so can not subtract signal1="+signal1.size()+" signal2="+signal2.size());
		}
		List<Float> yMeanSubtractData = new ArrayList<Float>();	    
		for (int i=0;i<signal1.size();i++) {	    	 
			yMeanSubtractData.add(i,(float)(signal1.get(i) - signal2.get(i)));
		}	
		return yMeanSubtractData;	
	}
}
