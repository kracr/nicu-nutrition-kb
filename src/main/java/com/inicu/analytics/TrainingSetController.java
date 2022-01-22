package com.inicu.analytics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.inicu.analytics.entities.ClassifierWeights;
import com.inicu.analytics.entities.FittingResult;
import com.inicu.analytics.entities.HighMorbidityScore;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.models.DeviceGraphAxisJson;
import com.inicu.models.DeviceGraphDataJson;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.CalendarUtility;

import flanagan.analysis.Stat;
import flanagan.interpolation.CubicSpline;
import flanagan.math.Fmath;
import flanagan.plot.PlotGraph;
import jsat.distributions.Exponential;
import jsat.distributions.Gamma;
import jsat.distributions.LogNormal;
import jsat.distributions.Normal;
import jsat.distributions.Weibull;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

@Repository
public class TrainingSetController extends Thread {
	// implements Runnable
	@Autowired
	DeviceDataService deviceDataService;

	@Autowired
	InicuDao inicuDao;

	// Change the path folder below for reading file and running the program in a
	// different system
	String csvLMFile = "/iNICU/Data/csv/LM_patient_new.csv";
	String csvHMFile = "/iNICU/Data/csv/HM_patient_new.csv";

	static String trainingDataFile = "/iNICU/Data/csv/Training_Data.csv";
	static String testingDataFile = "/iNICU/Data/csv/Testing_Data.csv";

//	static String trainingDataFile = "/Users/aman/dev/iNICU/Data/csv/TrainingData.csv";

	// 10 minute sliding window for rolling mean
	int WINDOW_SIZE = 10;
	// Create a DescriptiveStats instance and set the window size to 180
	int TOTAL_DATA_SIZE = 180;
	// rolling mean is of 10 points so additional points needed to make two array of
	// same size
	int TOTAL_DATA_SIZE_MEAN = 190;

	public TrainingSetController() {
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		boolean plotPDFCurve = true;
		boolean plotResponseCurve = true;
		System.out.println("in AnalyticsController - calculate Physiscore");
		try {
			// For a given baby find its dob and calculate physiscore on first 3 hour data
			// Test demo patient -RSHI.0000008539 data upto 2018-01-17 10:15:30.902+05:30
			File lmFile = new File(csvLMFile);
			File hmFile = new File(csvHMFile);
			lmFile.createNewFile();
			hmFile.createNewFile();
			FileWriter writerLM = new FileWriter(lmFile);
			FileWriter writerHM = new FileWriter(hmFile);

			// Retrieve the list of babies based on type(HM, LM)
			HashMap<String, DeviceGraphDataJson> hmListMap = retrieveBabyListMap(RegressionConstants.HM);
			HashMap<String, DeviceGraphDataJson> lmListMap = retrieveBabyListMap(RegressionConstants.LM);

			TOTAL_DATA_SIZE_MEAN = 190;
			// handle 10 minute rolling mean
			TOTAL_DATA_SIZE = TOTAL_DATA_SIZE_MEAN - 10;

			// Retrieve the base, residual signal & corresponding mean & variance of all
			// babies based on type(HM, LM)
			SignalStructure signalMap_HM = retrieveResidualMap(hmListMap, RegressionConstants.HM);
			SignalStructure signalMap_LM = retrieveResidualMap(lmListMap, RegressionConstants.LM);

			signalProbabilityAndFitting(signalMap_HM, signalMap_LM);
			createPDFCurveForUHID(signalMap_HM, signalMap_LM, plotPDFCurve);

			// Calculate P(HM) and P(LM)
			int totalNumberOfPatients = signalMap_HM.originalListMap.size() + signalMap_LM.originalListMap.size();
			double probabilityHM = 0;
			double probabilityLM = 0;
			probabilityHM = ((double) (signalMap_HM.originalListMap.size()) / totalNumberOfPatients);
			probabilityLM = ((double) (signalMap_LM.originalListMap.size()) / totalNumberOfPatients);
//			createResponseCurveHM(signalMap_HM, signalMap_LM, probabilityHM, probabilityLM, plotResponseCurve);
			createResponseCurveHM(signalMap_HM, signalMap_LM, 0.5, 0.5, plotResponseCurve);
			
			Classifier logisticClassifier = generateOddsRatioWeightsLogisticRegression(signalMap_LM, signalMap_HM);

			DataAnalyticsUtilities.writeIntoCSV(signalMap_LM, writerLM);
			DataAnalyticsUtilities.writeIntoCSV(signalMap_HM, writerHM);
			writerLM.close();
			writerHM.close();

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// List<Float> originalSPO2Data =
		// getWindowData(obj.getSPO2().getyAxis(),TOTAL_DATA_SIZE);
		// List<Float> originalRRData =
		// getWindowData(obj.getRR().getyAxis(),TOTAL_DATA_SIZE);
		//
		List<String> xAxisLabelData = new ArrayList<String>();

		// second find RR related base signal and residual signal
		// List<Float> baseSignalRRData =
		// getRollingMean(getWindowData(obj.getRR().getyAxis(),TOTAL_DATA_SIZE_MEAN));
		// List<Float> residualSignalRRData =
		// subtractTwoSignals(originalRRData,baseSignalRRData);
		//

		// second find SPO2 related values
		// List<Float> physiScoreyAxisData =
		// generatePhysiScore(residualSignalHRData_HM);
		// Find statistics of base and residual HR signals

		// DescriptiveStatistics baseSignalStatisticsHR =
		// getSignalStatistics(baseSignalHRData_HM);
		// DescriptiveStatistics residualSignalStatisticsHR =
		// getSignalStatistics(residualSignalHRData_HM);
		// Find statistics of base and residual RR signals
		// DescriptiveStatistics baseSignalStatisticsRR =
		// getSignalStatistics(baseSignalRRData);
		// DescriptiveStatistics residualSignalStatisticsRR =
		// getSignalStatistics(residualSignalRRData);
		//
		// Below are eight variables for physiscore calculations
		// double meanBaseSignalHR = baseSignalStatisticsHR.getMean();
		// double varianceBaseSignalMeanHR = baseSignalStatisticsHR.getVariance();
		// double varianceResidualSignalHR = residualSignalStatisticsHR.getVariance();
		// double baseSignalMeanRR = baseSignalStatisticsRR.getMean();
		// double varianceBaseSignalRR = baseSignalStatisticsRR.getVariance();
		// double varianceResidualSignalRR = residualSignalStatisticsRR.getVariance();
		// Find how many times SPO2 is below 85% - length of below array will tell how
		// many time sp02 was below 85%
		// List<Float> spO2LessThan85List = getSPO2LessThan85(originalSPO2Data);
		// find mean of sp02 value
		// DescriptiveStatistics spO2SignalStatistics =
		// getSignalStatistics(originalSPO2Data);

		//
		// double minResidualHR = residualSignalStatisticsHR.getMin();
		// double maxResidualHR = residualSignalStatisticsHR.getMax();
		//

		// System.out.println("Returned object for UHID"+obj.getUhid()+" original HR
		// value counts="+originalHRData_HM.size()+" RR value counts="+
		// originalRRData.size()+" SPO2 value counts="+originalSPO2Data.size()+
		// " baseSignalHRData count=" + baseSignalHRData_HM.size() +
		// " residualSignalHRData count=" + residualSignalHRData_HM.size()+
		// " residualSignal HR max=" + maxResidualHR+
		// " residualSignal HR min=" + minResidualHR+
		// " residualSignal varianceHR=" + varianceResidualSignalHR
		// );
		// Set Physiscore specific values in the graph object
		// DeviceGraphAxisJson pHYSISCORE = new DeviceGraphAxisJson();
		// pHYSISCORE.setxAxis(obj.getHR().getxAxis());
		// pHYSISCORE.setyAxis(physiScoreyAxisData);

		// Set Short term variability
		// DeviceGraphAxisJson sT_HRV = new DeviceGraphAxisJson();
		// sT_HRV.setxAxis(obj.getHR().getxAxis());
		// sT_HRV.setyAxis(residualSignalHRData_HM);
		// Set Long term variability
		// DeviceGraphAxisJson lT_HRV = new DeviceGraphAxisJson();
		// lT_HRV.setxAxis(obj.getHR().getxAxis());
		// lT_HRV.setyAxis(baseSignalHRData_HM);

		// Set residual values for range draw
		// obj.setMaxResidualVarianceHR(maxResidualHR);
		// obj.setMinResidualVarianceHR(minResidualHR);
		// obj.setVarianceResidualVarianceHR(varianceResidualSignalHR);

		// set physiscore specific graph values
		// obj.setPHYSISCORE(pHYSISCORE);
		// obj.setST_HRV(sT_HRV);
		// obj.setLT_HRV(lT_HRV);
		// return obj;
	}

	/**
	 * This method takes will create the P(HM|vi) based on bayesian first principle
	 * We already have P(vi|HM) from fitting step and also know P(HM) and P(LM)
	 * based on input dataset size. Now we will put these into the equation P(HM|vi)
	 * = 1/(1 + (P(vi|LM).P(LM))/(P(vi|HM).P(HM)) and save the plots
	 */
	void createResponseCurveHM(SignalStructure signalMap_HM, SignalStructure signalMap_LM, double probabilityHM,
			double probabilityLM, boolean plotResponseCurve) {

		int lowerValueRange = 60;
		int higherValueRange = 200;
		int incrementValue = 10;

		
		
		List<Double> calculateHM_ProbabilityBase_MeanHR = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR = new ArrayList<Double>();

		List<Double> calculateHM_ProbabilityBase_MeanHR_HM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR_HM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR_HM = new ArrayList<Double>();

		List<Double> calculateHM_ProbabilityBase_MeanHR_LM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR_LM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR_LM = new ArrayList<Double>();

		try {

			System.out.println("\n Printing response curve --------");
			RegressionResult fittingResultBaseMeanHR_HM = signalMap_HM.getFittingResultBaseMeanHR_HM();
			RegressionResult fittingResultBaseVarR_HM = signalMap_HM.getFittingResultBaseVarHR_HM();
			RegressionResult fittingResultResidualVarHR_HM = signalMap_HM.getFittingResultResidualVarHR_HM();

			RegressionResult fittingResultBaseMeanHR_LM = signalMap_LM.getFittingResultBaseMeanHR_LM();
			RegressionResult fittingResultBaseVarR_LM = signalMap_LM.getFittingResultBaseVarHR_LM();
			RegressionResult fittingResultResidualVarHR_LM = signalMap_LM.getFittingResultResidualVarHR_LM();

			for (int i = lowerValueRange; i < higherValueRange; i = i + incrementValue) {
				double probabilityBaseMeanHRLM = findProbability(fittingResultBaseMeanHR_LM, i);
				double probabilityBaseMeanHRHM = findProbability(fittingResultBaseMeanHR_HM, i);
				
				calculateHM_ProbabilityBase_MeanHR_HM.add(new Double(probabilityBaseMeanHRHM));
				calculateHM_ProbabilityBase_MeanHR_LM.add(new Double(probabilityBaseMeanHRLM));

				double denomBaseMeanHRHM = (probabilityBaseMeanHRHM * probabilityHM);
				double numeratorBaseMeanHRHM = (probabilityBaseMeanHRLM * probabilityLM);
				double calculateHM_BaseMeanHRProb = 0;
				if (denomBaseMeanHRHM != 0)
					calculateHM_BaseMeanHRProb = 1 / (1 + (numeratorBaseMeanHRHM / denomBaseMeanHRHM));
				calculateHM_ProbabilityBase_MeanHR.add(new Double(calculateHM_BaseMeanHRProb));

				double probabilityBaseVarLM = findProbability(fittingResultBaseVarR_LM, i);
				double probabilityBaseVarHM = findProbability(fittingResultBaseVarR_HM, i);
				calculateHM_ProbabilityBase_VarHR_HM.add(new Double(probabilityBaseVarHM));
				calculateHM_ProbabilityBase_VarHR_LM.add(new Double(probabilityBaseVarLM));

				double denomBaseVarHRHM = (probabilityBaseVarHM * probabilityHM);
				double numeratorBaseVarHRHM = (probabilityBaseVarLM * probabilityLM);
				double calculateHM_BaseVarProb = 0;
				if (denomBaseVarHRHM != 0)
					calculateHM_BaseVarProb = 1 / (1 + (numeratorBaseVarHRHM / denomBaseVarHRHM));
				calculateHM_ProbabilityBase_VarHR.add(new Double(calculateHM_BaseVarProb));

				double probabilityResidualVarLM = findProbability(fittingResultResidualVarHR_LM, i);
				double probabilityResidualVarHM = findProbability(fittingResultResidualVarHR_HM, i);
				calculateHM_ProbabilityResidual_HR_HM.add(new Double(probabilityResidualVarHM));
				calculateHM_ProbabilityResidual_HR_LM.add(new Double(probabilityResidualVarLM));

				double denomResiVarHRHM = (probabilityResidualVarHM * probabilityHM);
				double numeratorResiVarHRHM = (probabilityResidualVarLM * probabilityLM);
				double calculateHM_ResidualVarProb = 0;
				if (denomResiVarHRHM != 0)
					calculateHM_ResidualVarProb = 1 / (1 + (numeratorResiVarHRHM / denomResiVarHRHM));
				calculateHM_ProbabilityResidual_HR.add(new Double(calculateHM_ResidualVarProb));
			}
			if (plotResponseCurve) {
				// Print probability distribution curves for HM
				
				plotResponseCurve(
						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR_HM
								.toArray(new Double[calculateHM_ProbabilityBase_MeanHR_HM.size()])),
						"PDF for BaseSignal Mean HR HM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
						incrementValue);

				plotResponseCurve(
						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR_LM
								.toArray(new Double[calculateHM_ProbabilityBase_MeanHR_LM.size()])),
						"PDF for BaseSignal Mean HR LM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
						incrementValue);

				plotResponseCurve(
						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR
								.toArray(new Double[calculateHM_ProbabilityBase_MeanHR.size()])),
						"Response Curves for BaseSignal Mean HR", "HR Values", "Base Mean HR", lowerValueRange,
						higherValueRange, incrementValue);

//				plotResponseCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR_HM
//								.toArray(new Double[calculateHM_ProbabilityBase_VarHR_HM.size()])),
//						"PDF for BaseSignal Var HR HM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
//						incrementValue);
//
//				plotResponseCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR_LM
//								.toArray(new Double[calculateHM_ProbabilityBase_VarHR_LM.size()])),
//						"PDF for BaseSignal Var HR LM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
//						incrementValue);
//				
//				plotResponseCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR
//								.toArray(new Double[calculateHM_ProbabilityBase_VarHR.size()])),
//						"Response Curves for BaseSignal Var HR", "HR Values", "Base Variance HR", lowerValueRange,
//						higherValueRange, incrementValue);
//				
//				plotResponseCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR_HM
//								.toArray(new Double[calculateHM_ProbabilityResidual_HR_HM.size()])),
//						"PDF for Residual Variance HR HM", "HR Values", "Base Mean HR", lowerValueRange,
//						higherValueRange, incrementValue);
//
//				plotResponseCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR_LM
//								.toArray(new Double[calculateHM_ProbabilityResidual_HR_LM.size()])),
//						"PDF for Residual Variance HR LM", "HR Values", "Base Mean HR", lowerValueRange,
//						higherValueRange, incrementValue);
//
//				plotResponseCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR
//								.toArray(new Double[calculateHM_ProbabilityResidual_HR.size()])),
//						"Response Curves for Residual HR", "HR Values", "Residual Variance HR", lowerValueRange,
//						higherValueRange, incrementValue);
				System.out.println("\n End Printing PDF curve --------");

			}

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	void createPDFCurveForUHID(SignalStructure signalMap_HM, SignalStructure signalMap_LM, boolean plotPDFCurve) {

		List<Double> base_MeanHR_HM = new ArrayList<Double>();
		List<Double> base_VarHR_HM = new ArrayList<Double>();
		List<Double> residual_HR_HM = new ArrayList<Double>();

		List<Double> base_MeanHR_LM = new ArrayList<Double>();
		List<Double> base_VarHR_LM = new ArrayList<Double>();
		List<Double> residual_HR_LM = new ArrayList<Double>();

		List<Double> calculateHM_ProbabilityBase_MeanHR_HM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR_HM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR_HM = new ArrayList<Double>();

		List<Double> calculateHM_ProbabilityBase_MeanHR_LM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR_LM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR_LM = new ArrayList<Double>();

		try {

			System.out.println("\n Printing PDF curve --------");
			RegressionResult fittingResultBaseMeanHR_HM = signalMap_HM.getFittingResultBaseMeanHR_HM();
			RegressionResult fittingResultBaseVarR_HM = signalMap_HM.getFittingResultBaseVarHR_HM();
			RegressionResult fittingResultResidualVarHR_HM = signalMap_HM.getFittingResultResidualVarHR_HM();

			RegressionResult fittingResultBaseMeanHR_LM = signalMap_LM.getFittingResultBaseMeanHR_LM();
			RegressionResult fittingResultBaseVarR_LM = signalMap_LM.getFittingResultBaseVarHR_LM();
			RegressionResult fittingResultResidualVarHR_LM = signalMap_LM.getFittingResultResidualVarHR_LM();

			Set<String> uhidKeySetToAssessHM = signalMap_HM.getBaseSignalMean().keySet();
			Iterator<String> iteratorHM = uhidKeySetToAssessHM.iterator();
			while (iteratorHM.hasNext()) {
				String uhidPatient = iteratorHM.next();
				Double meanBaseHRPatientHM = signalMap_HM.getBaseSignalMean().get(uhidPatient);
				Double varBaseHRPatientHM = signalMap_HM.getBaseSignalVar().get(uhidPatient);
				Double varResidualHRPatientHM = signalMap_HM.getResidualSignalVar().get(uhidPatient);
				base_MeanHR_HM.add(new Double(meanBaseHRPatientHM));
				base_VarHR_HM.add(new Double(varBaseHRPatientHM));
				residual_HR_HM.add(new Double(varResidualHRPatientHM));

				double probabilityBaseMeanHRHM = findProbability(fittingResultBaseMeanHR_HM, meanBaseHRPatientHM);
				calculateHM_ProbabilityBase_MeanHR_HM.add(new Double(probabilityBaseMeanHRHM));

				double probabilityBaseVarHM = findProbability(fittingResultBaseVarR_HM, varBaseHRPatientHM);
				calculateHM_ProbabilityBase_VarHR_HM.add(new Double(probabilityBaseVarHM));

				double probabilityResidualVarHM = findProbability(fittingResultResidualVarHR_HM,
						varResidualHRPatientHM);
				calculateHM_ProbabilityResidual_HR_HM.add(new Double(probabilityResidualVarHM));

			}

			Set<String> uhidKeySetToAssessLM = signalMap_LM.getBaseSignalMean().keySet();
			Iterator<String> iteratorLM = uhidKeySetToAssessLM.iterator();
			while (iteratorLM.hasNext()) {
				String uhidPatient = iteratorLM.next();
				Double meanBaseHRPatientLM = signalMap_LM.getBaseSignalMean().get(uhidPatient);
				Double varBaseHRPatientLM = signalMap_LM.getBaseSignalVar().get(uhidPatient);
				Double varResidualHRPatientLM = signalMap_LM.getResidualSignalVar().get(uhidPatient);
				base_MeanHR_LM.add(new Double(meanBaseHRPatientLM));
				base_VarHR_LM.add(new Double(varBaseHRPatientLM));
				residual_HR_LM.add(new Double(varResidualHRPatientLM));

				double probabilityBaseMeanHRLM = findProbability(fittingResultBaseMeanHR_LM, meanBaseHRPatientLM);
				calculateHM_ProbabilityBase_MeanHR_LM.add(new Double(probabilityBaseMeanHRLM));

				double probabilityBaseVarLM = findProbability(fittingResultBaseVarR_LM, varBaseHRPatientLM);
				calculateHM_ProbabilityBase_VarHR_LM.add(new Double(probabilityBaseVarLM));

				double probabilityResidualVarLM = findProbability(fittingResultResidualVarHR_LM,
						varResidualHRPatientLM);
				calculateHM_ProbabilityResidual_HR_LM.add(new Double(probabilityResidualVarLM));

			}
			if (plotPDFCurve) {
				// Print probability distribution curves for HM
				plotCurve(
						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR_HM
								.toArray(new Double[calculateHM_ProbabilityBase_MeanHR_HM.size()])),
						ArrayUtils.toPrimitive(base_MeanHR_HM.toArray(new Double[base_MeanHR_HM.size()])),
						"PDF for BaseSignal Mean HR HM", "HR Values", "Base Mean HR");

				plotCurve(
						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR_LM
								.toArray(new Double[calculateHM_ProbabilityBase_MeanHR_LM.size()])),
						ArrayUtils.toPrimitive(base_MeanHR_LM.toArray(new Double[base_MeanHR_LM.size()])),
						"PDF for BaseSignal Mean HR LM", "HR Values", "Base Mean HR");

//				plotCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR_HM
//								.toArray(new Double[calculateHM_ProbabilityBase_VarHR_HM.size()])),
//						ArrayUtils.toPrimitive(base_VarHR_HM.toArray(new Double[base_VarHR_HM.size()])),
//						"PDF for BaseSignal Var HR HM", "HR Values", "Base Mean HR");
//
//				plotCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR_LM
//								.toArray(new Double[calculateHM_ProbabilityBase_VarHR_LM.size()])),
//						ArrayUtils.toPrimitive(base_VarHR_LM.toArray(new Double[base_VarHR_LM.size()])),
//						"PDF for BaseSignal Var HR LM", "HR Values", "Base Mean HR");
//
//				plotCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR_HM
//								.toArray(new Double[calculateHM_ProbabilityResidual_HR_HM.size()])),
//						ArrayUtils.toPrimitive(residual_HR_HM.toArray(new Double[residual_HR_HM.size()])),
//						"PDF for Residual Variance HR HM", "HR Values", "Base Mean HR");
//
//				plotCurve(
//						ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR_LM
//								.toArray(new Double[calculateHM_ProbabilityResidual_HR_LM.size()])),
//						ArrayUtils.toPrimitive(residual_HR_LM.toArray(new Double[residual_HR_LM.size()])),
//						"PDF for Residual Variance HR LM", "HR Values", "Base Mean HR");

				System.out.println("\n End Printing PDF curve --------");
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	// This function will plot the PDF curve of fitted data
	void createPDFCurve(SignalStructure signalMap_HM, SignalStructure signalMap_LM) {

		int lowerValueRange = 10;
		int higherValueRange = 200;
		int incrementValue = 10;

		List<Double> calculateHM_ProbabilityBase_MeanHR_HM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR_HM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR_HM = new ArrayList<Double>();

		List<Double> calculateHM_ProbabilityBase_MeanHR_LM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityBase_VarHR_LM = new ArrayList<Double>();
		List<Double> calculateHM_ProbabilityResidual_HR_LM = new ArrayList<Double>();

		try {

			System.out.println("\n Printing PDF curve --------");
			RegressionResult fittingResultBaseMeanHR_HM = signalMap_HM.getFittingResultBaseMeanHR_HM();
			RegressionResult fittingResultBaseVarR_HM = signalMap_HM.getFittingResultBaseVarHR_HM();
			RegressionResult fittingResultResidualVarHR_HM = signalMap_HM.getFittingResultResidualVarHR_HM();

			RegressionResult fittingResultBaseMeanHR_LM = signalMap_LM.getFittingResultBaseMeanHR_LM();
			RegressionResult fittingResultBaseVarR_LM = signalMap_LM.getFittingResultBaseVarHR_LM();
			RegressionResult fittingResultResidualVarHR_LM = signalMap_LM.getFittingResultResidualVarHR_LM();

			for (int i = lowerValueRange; i < higherValueRange; i = i + incrementValue) {
				double probabilityBaseMeanHRLM = findProbability(fittingResultBaseMeanHR_LM, i);
				double probabilityBaseMeanHRHM = findProbability(fittingResultBaseMeanHR_HM, i);
				calculateHM_ProbabilityBase_MeanHR_HM.add(new Double(probabilityBaseMeanHRHM));
				calculateHM_ProbabilityBase_MeanHR_LM.add(new Double(probabilityBaseMeanHRLM));

				double probabilityBaseVarLM = findProbability(fittingResultBaseVarR_LM, i);
				double probabilityBaseVarHM = findProbability(fittingResultBaseVarR_HM, i);
				calculateHM_ProbabilityBase_VarHR_HM.add(new Double(probabilityBaseVarHM));
				calculateHM_ProbabilityBase_VarHR_LM.add(new Double(probabilityBaseVarLM));

				double probabilityResidualVarLM = findProbability(fittingResultResidualVarHR_LM, i);
				double probabilityResidualVarHM = findProbability(fittingResultResidualVarHR_HM, i);
				calculateHM_ProbabilityResidual_HR_HM.add(new Double(probabilityResidualVarHM));
				calculateHM_ProbabilityResidual_HR_LM.add(new Double(probabilityResidualVarLM));

			}

//Print probability distribution curves for HM
			plotResponseCurve(
					ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR_HM
							.toArray(new Double[calculateHM_ProbabilityBase_MeanHR_HM.size()])),
					"PDF for BaseSignal Mean HR HM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
					incrementValue);

			plotResponseCurve(
					ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_MeanHR_LM
							.toArray(new Double[calculateHM_ProbabilityBase_MeanHR_LM.size()])),
					"PDF for BaseSignal Mean HR LM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
					incrementValue);

			plotResponseCurve(
					ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR_HM
							.toArray(new Double[calculateHM_ProbabilityBase_VarHR_HM.size()])),
					"PDF for BaseSignal Var HR HM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
					incrementValue);

			plotResponseCurve(
					ArrayUtils.toPrimitive(calculateHM_ProbabilityBase_VarHR_LM
							.toArray(new Double[calculateHM_ProbabilityBase_VarHR_LM.size()])),
					"PDF for BaseSignal Var HR LM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
					incrementValue);

			plotResponseCurve(
					ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR_HM
							.toArray(new Double[calculateHM_ProbabilityResidual_HR_HM.size()])),
					"PDF for Residual Variance HR HM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
					incrementValue);

			plotResponseCurve(
					ArrayUtils.toPrimitive(calculateHM_ProbabilityResidual_HR_LM
							.toArray(new Double[calculateHM_ProbabilityResidual_HR_LM.size()])),
					"PDF for Residual Variance HR LM", "HR Values", "Base Mean HR", lowerValueRange, higherValueRange,
					incrementValue);

			System.out.println("\n End Printing PDF curve --------");

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * This method takes signals of HM and LM cases and fit them basis KTTest or KDE
	 * fitting to generate most fit regression model. Then it iterates over all UHID
	 * to find probabilities for HM and LM for each patient (base mean , base
	 * variance and residual variance). It generates log odds ratio of these
	 * probabilities and add the same into Signal Structure object as reference
	 * 
	 * @param signalMap_HM
	 * @param signalMap_LM
	 * @throws Exception
	 */
	private void signalProbabilityAndFitting(SignalStructure signalMap_HM, SignalStructure signalMap_LM)
			throws Exception {
		// Getting the original signal for the complete data set individually for HM and
		// LM
		List<Double> baseMeanSignalHRData_HM = receivelSignalToFit(signalMap_HM.getBaseSignalMean());
		List<Double> baseVarSignalHRData_HM = receivelSignalToFit(signalMap_HM.getBaseSignalVar());
		List<Double> residualVarSignalHRData_HM = receivelSignalToFit(signalMap_HM.getResidualSignalVar());

		List<Double> baseMeanSignalHRData_LM = receivelSignalToFit(signalMap_LM.getBaseSignalMean());
		List<Double> baseVarSignalHRData_LM = receivelSignalToFit(signalMap_LM.getBaseSignalVar());
		List<Double> residualVarSignalHRData_LM = receivelSignalToFit(signalMap_LM.getResidualSignalVar());

		// Build fitting Signals to find best fitting based on regression : Fit HM Data
		FitDataIntoDistribution baseMeanSignalFitDistHR_HM = new FitDataIntoDistribution(baseMeanSignalHRData_HM);
		FitDataIntoDistribution baseVarSignalFitDistHR_HM = new FitDataIntoDistribution(baseVarSignalHRData_HM);
		FitDataIntoDistribution residualVarSignalFitDistHR_HM = new FitDataIntoDistribution(residualVarSignalHRData_HM);
		RegressionResult bestResultBaseMeanHR_HM = baseMeanSignalFitDistHR_HM
				.performHistogramAndFindBestFit(RegressionConstants.BaseSignal_MEAN_HR, RegressionConstants.HM);
		RegressionResult bestResultBaseVarHR_HM = baseVarSignalFitDistHR_HM
				.performHistogramAndFindBestFit(RegressionConstants.BaseSignal_VARIANCE_HR, RegressionConstants.HM);
		RegressionResult bestResultResidualVarHR_HM = residualVarSignalFitDistHR_HM
				.performHistogramAndFindBestFit(RegressionConstants.Residual_VARIANCE_HR, RegressionConstants.HM);

		FittingResult fittingResultBaseMeanHR_HM = bestResultBaseMeanHR_HM.generateFittingResult();
		FittingResult fittingResultBaseVarHR_HM = bestResultBaseVarHR_HM.generateFittingResult();
		FittingResult fittingResultResidualVarHR_HM = bestResultResidualVarHR_HM.generateFittingResult();
		signalMap_HM.setFittingResultBaseMeanHR_HM(bestResultBaseMeanHR_HM);
		signalMap_HM.setFittingResultBaseVarHR_HM(bestResultBaseVarHR_HM);
		signalMap_HM.setFittingResultResidualVarHR_HM(bestResultResidualVarHR_HM);

		deviceDataService.insertRegressionModelIntoDatabase(fittingResultBaseMeanHR_HM);
		deviceDataService.insertRegressionModelIntoDatabase(fittingResultBaseVarHR_HM);
		deviceDataService.insertRegressionModelIntoDatabase(fittingResultResidualVarHR_HM);

		// Fit LM Data
		FitDataIntoDistribution baseMeanSignalfitDistHR_LM = new FitDataIntoDistribution(baseMeanSignalHRData_LM);
		FitDataIntoDistribution baseVarSignalFitDistHR_LM = new FitDataIntoDistribution(baseVarSignalHRData_LM);
		FitDataIntoDistribution residualVarSignalFitDistHR_LM = new FitDataIntoDistribution(residualVarSignalHRData_LM);
		RegressionResult bestResultMeanHR_LM = baseMeanSignalfitDistHR_LM
				.performHistogramAndFindBestFit(RegressionConstants.BaseSignal_MEAN_HR, RegressionConstants.LM);
		RegressionResult bestResultBaseVarHR_LM = baseVarSignalFitDistHR_LM
				.performHistogramAndFindBestFit(RegressionConstants.BaseSignal_VARIANCE_HR, RegressionConstants.LM);
		RegressionResult bestResultResidualVarHR_LM = residualVarSignalFitDistHR_LM
				.performHistogramAndFindBestFit(RegressionConstants.Residual_VARIANCE_HR, RegressionConstants.LM);

		// HS: change entity to include LM or HM model in the table
		// Save the generated regression into database
		FittingResult fittingResultBaseMeanHR_LM = bestResultMeanHR_LM.generateFittingResult();
		FittingResult fittingResultBaseVarHR_LM = bestResultBaseVarHR_LM.generateFittingResult();
		FittingResult fittingResultResidualVarHR_LM = bestResultResidualVarHR_LM.generateFittingResult();
		signalMap_LM.setFittingResultBaseMeanHR_LM(bestResultMeanHR_LM);
		signalMap_LM.setFittingResultBaseVarHR_LM(bestResultBaseVarHR_LM);
		signalMap_LM.setFittingResultResidualVarHR_LM(bestResultResidualVarHR_LM);
		// all approximated distributions are stored in database as their attributes
		// not storing KDE in database
		deviceDataService.insertRegressionModelIntoDatabase(fittingResultBaseMeanHR_LM);
		deviceDataService.insertRegressionModelIntoDatabase(fittingResultBaseVarHR_LM);
		deviceDataService.insertRegressionModelIntoDatabase(fittingResultResidualVarHR_LM);

		// Plot PDF Curves

		// Model is persisted in table, now find probability for each LM and HM patient
		// to calculate log odds ratio
		// Probabilities for HM Cases against the model
		buildProbabilitiesForFittedModel(signalMap_HM, bestResultBaseMeanHR_HM, bestResultBaseVarHR_HM,
				bestResultResidualVarHR_HM, bestResultMeanHR_LM, bestResultBaseVarHR_LM, bestResultResidualVarHR_LM);
		// Probabilities for LM Cases against the model
		buildProbabilitiesForFittedModel(signalMap_LM, bestResultBaseMeanHR_HM, bestResultBaseVarHR_HM,
				bestResultResidualVarHR_HM, bestResultMeanHR_LM, bestResultBaseVarHR_LM, bestResultResidualVarHR_LM);
	}

	public HashMap<String, DeviceGraphDataJson> retrieveBabyListMap(String type) {
		String queryBabyList = "select obj from BabyDetail as obj where recommended_status = '" + type + "'";
		List<BabyDetail> list = inicuDao.getListFromMappedObjQuery(queryBabyList);
		// populate hash map....
		HashMap<String, DeviceGraphDataJson> listMap = new HashMap<String, DeviceGraphDataJson>();
		if (!BasicUtils.isEmpty(list)) {
			for (BabyDetail obj : list) {
				DeviceGraphDataJson graphDataJson = new DeviceGraphDataJson();

				String isDaily = "hourly";

				// Window for 7 days
				long offset = 1440 * 60 * 1000 * 7;
				java.sql.Date dateofadmission = new java.sql.Date(obj.getDateofadmission().getTime() + offset);

				int hours = Integer.parseInt(obj.getTimeofadmission().substring(0, 2));
				int minutes = Integer.parseInt(obj.getTimeofadmission().substring(3, 5));
				if (obj.getTimeofadmission().substring(6, 8).equals("PM") && hours != 12) {
					hours = hours + 12;
				}
				String time = hours + ":" + minutes + ":00";

				graphDataJson.setUhid(obj.getUhid());
				graphDataJson.setFromdate(obj.getDateofadmission().toString());
				graphDataJson.setTodate(dateofadmission.toString());
				graphDataJson.setDaily(isDaily);
				graphDataJson.setStartTime(time);
				graphDataJson.setEndTime(time);

				java.sql.Timestamp startTime = new java.sql.Timestamp(obj.getDateofadmission().getTime());
				java.sql.Timestamp endTime = new java.sql.Timestamp(obj.getDateofadmission().getTime() + offset);

				DateFormat d = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");

				String entryDate = d.format(startTime);
				entryDate = entryDate + " GMT+0530 (IST)";
				String endDate = d.format(endTime);
				endDate = endDate + " GMT+0530 (IST)";

				DeviceGraphDataJson lmObjectJson = deviceDataService.getDevGraphData(graphDataJson, entryDate, endDate);
				listMap.put(obj.getUhid(), lmObjectJson);
			}
		}
		return listMap;
	}

// Finding probability through fittingResult via PDF

	public double findProbability(RegressionResult fittingResult, double meanHRPatient) {
		double probabilityValue = 0;
		// Find probability of value for given distribution
		if (fittingResult.getRegressionType().equalsIgnoreCase(RegressionConstants.LOGNORMAL)) {
			double meanFit = fittingResult.getMeanFit();
			double stdDeviationFit = fittingResult.getStandardDeviationFit();
			LogNormal logNormal = new LogNormal(meanFit, stdDeviationFit);
			probabilityValue = logNormal.pdf(meanHRPatient);
			System.out.println("Value of logNormalPDF = " + probabilityValue);
		} else if (fittingResult.getRegressionType().equalsIgnoreCase(RegressionConstants.GAMMA)) {
			Gamma gamma = new Gamma(fittingResult.getKvalue(), fittingResult.getThetaValue());
			probabilityValue = gamma.pdf(meanHRPatient);
			System.out.println("Value of gammaPDF = " + probabilityValue);
		} else if (fittingResult.getRegressionType().equalsIgnoreCase(RegressionConstants.WEIBULL)) {
			Weibull weibull = new Weibull(fittingResult.getAlphaValue(), fittingResult.getBetaValue());
			probabilityValue = weibull.pdf(meanHRPatient);
			System.out.println("Value of weibullPDF = " + probabilityValue);
		} else if (fittingResult.getRegressionType().equalsIgnoreCase(RegressionConstants.EXPONENTIAL)) {
			Exponential exponential = new Exponential(fittingResult.getMeanFit());
			probabilityValue = exponential.pdf(meanHRPatient);
			System.out.println("Value of exponentialPDF = " + probabilityValue);
		} else if (fittingResult.getRegressionType().equalsIgnoreCase(RegressionConstants.NORMAL)) {
			Normal normal = new Normal(fittingResult.getMeanFit(), fittingResult.getStandardDeviationFit());
			probabilityValue = normal.pdf(meanHRPatient);
			System.out.println("Value of normalPDF = " + probabilityValue);
		} else if (fittingResult.getRegressionType().equalsIgnoreCase(RegressionConstants.KDE)) {
			probabilityValue = fittingResult.getKernelEstimator().pdf(meanHRPatient);
			System.out.println("Value of KDE PDF = " + probabilityValue);
		}
		return probabilityValue;
	}

	/**
	 * This method takes signal map of LM and HM data. It first build a csv file for
	 * input in weka logistic regression model. Input parameters in excel are base
	 * signal mean, base signal variance and residual variance. It also appends
	 * probability of LM and HM categories and odds ration in CSV for reference,
	 * although it does not use those for logistic regression analysis
	 * 
	 * @param inputSignalMap
	 */
	public Classifier generateOddsRatioWeightsLogisticRegression(SignalStructure lmSignalMap,
			SignalStructure hmSignalMap) {
		Logistic classifier = new Logistic();
		try {

			// Code for creating file for outputData
			File trainingFile = new File(trainingDataFile);
			File testingFile = new File(testingDataFile);
			trainingFile.createNewFile();
			testingFile.createNewFile();
			FileWriter trainingWriter = new FileWriter(trainingFile);
			FileWriter testingWriter = new FileWriter(testingFile);

			// Divide the patient list into 80/20 training and testing data
			List<String> listOfLMPatients = lmSignalMap.getUhidList();
			List<String> listOfHMPatients = hmSignalMap.getUhidList();
			int trainingPercentage = 80;

			int numberOfLMPatients = listOfLMPatients.size();
			int numberOfHMPatients = listOfHMPatients.size();
			
			int trainingNumber =0;
			//this will be minimum of LM or HM patients
			if(numberOfHMPatients <numberOfLMPatients)
				trainingNumber = numberOfHMPatients;
			else
				trainingNumber = numberOfLMPatients;
			// Write HM and LM data into training and testing file
			List<List<String>> lmMPatientLists = Lists.partition(listOfLMPatients,
					(int) ((trainingPercentage * trainingNumber) / 100.0f));
			List<List<String>> hmMPatientLists = Lists.partition(listOfHMPatients,
					(int) ((trainingPercentage * trainingNumber) / 100.0f));
			// from training data calculate P(LM)/ P(HM)
			double priorKnowledge = Math.log(lmMPatientLists.get(0).size() / hmMPatientLists.get(0).size());
			
			// Class is HM or LM as last column
			// remove all probability column from logistics - index starting from 0 and keep
			// only first three values
			// along with last class column
			// Write training files
			writeDataIntoFile(trainingWriter, hmSignalMap, 
					trainingFile, "HM", hmMPatientLists.get(0), true);
			writeDataIntoFile(trainingWriter, lmSignalMap, 
					trainingFile, "LM", lmMPatientLists.get(0), false);

			// Write testing files
			HashMap<String, HighMorbidityScore> hmListPatient = writeDataIntoFile(testingWriter, hmSignalMap,
					testingFile, "HM", hmMPatientLists.get(1), true);
			HashMap<String, HighMorbidityScore> patientListMap = writeDataIntoFile(testingWriter, lmSignalMap,
					testingFile, "LM", lmMPatientLists.get(1), false);
			// Add HM and LM lists to generate one testing data map
			patientListMap.putAll(hmListPatient);
			trainingWriter.close();
			testingWriter.close();

			// Setting attributes for output file
			int classIdOut = 3;

			// Load Training Data
			Instances trainingDataSetOut = generateDataSet(trainingFile);
			// Load testing data
			Instances testingDataSetOut = generateDataSet(testingFile);

			// remove all string attributes
			trainingDataSetOut.deleteStringAttributes();
			testingDataSetOut.deleteStringAttributes();
			int numAttributesOut = trainingDataSetOut.numAttributes();

			trainingDataSetOut.setClassIndex(classIdOut);
			testingDataSetOut.setClassIndex(classIdOut);
			/** Classifier here is Logistic Regression */
			classifier.buildClassifier(trainingDataSetOut);
			
			
			Evaluation eval = new Evaluation(trainingDataSetOut);
			double[] predictions = eval.evaluateModel(classifier, testingDataSetOut);

			System.out.print(" the expression for the input data as per algorithm is ");
			System.out.println(classifier);
			System.out.print(" The eval is found right in "+eval.pctCorrect()+ "cases");
			System.out.print(" Eval results "+eval.predictions());
			
			trainingWriter = new FileWriter(trainingFile, true);
			int totalTestingPatients = testingDataSetOut.size();
			double interceptValue = (classifier.coefficients()[0][0]);
			double weightBaseMeanHR = (classifier.coefficients()[1][0]);
			double weightBaseVarianceHR = (classifier.coefficients()[2][0]);
			double weightResidualVarianceHR = (classifier.coefficients()[3][0]);
			ClassifierWeights classifierWeights = new ClassifierWeights();
			classifierWeights.setWeightBaseMeanHR(weightBaseMeanHR);
			classifierWeights.setWeightBaseVarianceHR(weightBaseVarianceHR);
			classifierWeights.setWeightResidualVarianceHR(weightResidualVarianceHR);
			classifierWeights.setInterceptValue(interceptValue);
//			deviceDataService.insertClassifierWeightsIntoDatabase(classifierWeights);
			double physiScore = 0.0;
			System.out.println("-------------PRINTING RESULTS----------------");			
			for (Map.Entry<String, HighMorbidityScore> patientEntry : patientListMap.entrySet()) {
				HighMorbidityScore highMorbidityScore = patientEntry.getValue();
				//Definition of the logistic function : https://en.wikipedia.org/wiki/Logistic_regression
				// As per wiki sigma = 1/(1+e^-t)
				physiScore = 1
						/ (1 + Math.exp(-1*(interceptValue + weightBaseMeanHR * highMorbidityScore.getBasesignalmeanhr()
								+ weightBaseVarianceHR * highMorbidityScore.getBasesignalvarhr()
								+ weightResidualVarianceHR * highMorbidityScore.getResidualsignalvarhr())));
				highMorbidityScore.setPhysiscore(physiScore);
				System.out.println("uhid="+highMorbidityScore.getUhid()+"---physiScore---" + physiScore + "recommended status="
						+ highMorbidityScore.getRecommendedStatus());
			}

			// Calculate PhysiScore for testing patients

			DataAnalyticsUtilities.writeLine(trainingWriter, Arrays.asList("Weights", "W1 Base Signal Mean (BSM)",
					"W2 Base Signal Variance(BSV)", "W3 Residual Signal Variance (RSV)", "W4 P(HM)_P(LM)"));

			DataAnalyticsUtilities.writeLine(trainingWriter, Arrays.asList(classifier.coefficients()[0][0],
					classifier.coefficients()[1][0], classifier.coefficients()[2][0], classifier.coefficients()[3][0]));

			DataAnalyticsUtilities.writeLine(trainingWriter, Arrays.asList("Intercept", "W1 Base Signal Mean (BSM)",
					"W2 Base Signal Variance(BSV)", "W3 Residual Signal Variance (RSV)", "W4 P(HM)_P(LM)"));

			trainingWriter.close();

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return classifier;
	}

	// This function writes the generated data of features into a CSV file that is
	// loaded by WEKA for
	// classifier development. It also writes the same data into a
	// HighMorbidityScore strcuture
	// that is stored in database
	private HashMap<String, HighMorbidityScore> writeDataIntoFile(FileWriter outputWriter, SignalStructure signalMap,
			File outputFile, String morbidityStatus, List<String> selectedUHID, 
			boolean writeHeader) throws IOException {
		HashMap<String, HighMorbidityScore> patientListMap = new HashMap<String, HighMorbidityScore>();
		HashMap<String, List<Float>> originalList = signalMap.getOriginalListMap();
		HashMap<String, List<Double>> probabilityBaseMeanHR = signalMap.getProbabilityBaseMeanHR();
		HashMap<String, List<Double>> probabilityBaseVarHR = signalMap.getProbabilityBaseVarHR();
		HashMap<String, List<Double>> probabilityResidualVarHR = signalMap.getProbabilityResidualVarHR();

//LM for outputData	
		if (writeHeader) {
			// write into CSV file which will be used in next step of classifier modeling
			DataAnalyticsUtilities.writeLine(outputWriter, Arrays.asList("UHID", "BSM", "BSV","RSV", "LM_HM"));
		}
		for (Map.Entry<String, List<Float>> originalEntry : originalList.entrySet()) 
		{
			if (selectedUHID.contains(originalEntry.getKey())) {
				HighMorbidityScore highMorbidityScore = new HighMorbidityScore();
				double basesignalmeanhr = probabilityBaseMeanHR.get(originalEntry.getKey()).get(2);
				highMorbidityScore.setBasesignalmeanhr(basesignalmeanhr);
				double basesignalvarhr = probabilityBaseVarHR.get(originalEntry.getKey()).get(2);
				highMorbidityScore.setBasesignalvarhr(basesignalvarhr);
				double residualsignalvarhr = probabilityResidualVarHR.get(originalEntry.getKey()).get(2);
				highMorbidityScore.setResidualsignalvarhr(residualsignalvarhr);
				highMorbidityScore.setRecommendedStatus(morbidityStatus);
				highMorbidityScore.setUhid(originalEntry.getKey());
				patientListMap.put(originalEntry.getKey(), highMorbidityScore);
				int morbidityStatusInt = 0;
				if(morbidityStatus.equalsIgnoreCase("HM"))
						morbidityStatusInt = 1;
				DataAnalyticsUtilities.writeLine(outputWriter, Arrays.asList(originalEntry.getKey(), basesignalmeanhr,
						basesignalvarhr, residualsignalvarhr, morbidityStatusInt));
			}
		}
		return patientListMap;
	}

	// This method loads data from CSV file generated from previous step of pipeline
	// and
	// create weka specific Instances object. It also load data for each patient in
	// HighMorbidityScore
	// object that will be persisted in database
	private Instances generateDataSet(File outputFile) throws IOException {
		CSVLoader predictCsvLoaderOut = new CSVLoader();
		predictCsvLoaderOut.setSource(outputFile);
		// provide to weka - type of column attribute - index starting from 1
		predictCsvLoaderOut.setStringAttributes("1");
		predictCsvLoaderOut.setNumericAttributes("2,3,4");
		predictCsvLoaderOut.setNominalAttributes("5");
		Instances predictDataSetOut = predictCsvLoaderOut.getDataSet();
		// Iterate all instances and populate HighMorbidity Object
		return predictDataSetOut;
	}

	/**
	 * This method iterates over all values of a signal and generate corresponding
	 * probability values based on fitted HM and LM model
	 * 
	 * @param inputSignalMap
	 * @param fittingResult
	 */
	public void buildProbabilitiesForFittedModel(SignalStructure inputSignalMap,
			RegressionResult baseMeanFittingResultHM, RegressionResult baseVarFittingResultHM,
			RegressionResult residualVarfittingResultHM, RegressionResult baseMeanFittingResultLM,
			RegressionResult baseVarFittingResultLM, RegressionResult residualVarfittingResultLM) {
		HashMap<String, List<Double>> probabilityBaseMeanHR = new HashMap<String, List<Double>>();
		HashMap<String, List<Double>> probabilityBaseVarHR = new HashMap<String, List<Double>>();
		HashMap<String, List<Double>> probabilityResidualVarHR = new HashMap<String, List<Double>>();
		inputSignalMap.setProbabilityBaseMeanHR(probabilityBaseMeanHR);
		inputSignalMap.setProbabilityBaseVarHR(probabilityBaseVarHR);
		inputSignalMap.setProbabilityResidualVarHR(probabilityResidualVarHR);
		Set<String> uhidKeySetToAssess = inputSignalMap.getBaseSignalMean().keySet();
		Iterator<String> iterator = uhidKeySetToAssess.iterator();
		while (iterator.hasNext()) {
			String uhidPatient = iterator.next();
			Double meanBaseHRPatient = inputSignalMap.getBaseSignalMean().get(uhidPatient);
			Double varBaseHRPatient = inputSignalMap.getBaseSignalVar().get(uhidPatient);
			Double varResidualHRPatient = inputSignalMap.getResidualSignalVar().get(uhidPatient);
			// Base Mean Probabilities
			double probabilityValueBaseMeanLM = findProbability(baseMeanFittingResultLM, meanBaseHRPatient);
			double probabilityValueBaseMeanHM = findProbability(baseMeanFittingResultHM, meanBaseHRPatient);
			double oddsRatioBaseMean = 100; //Assign big value if denominator is zero else it will pick value from log
			if (probabilityValueBaseMeanHM != 0)
				oddsRatioBaseMean = Math.log(probabilityValueBaseMeanLM / probabilityValueBaseMeanHM);
			// Base Variance Probabilities
			double probabilityValueBaseVarLM = findProbability(baseVarFittingResultLM, varBaseHRPatient);
			double probabilityValueBaseVarHM = findProbability(baseVarFittingResultHM, varBaseHRPatient);
			double oddsRatioBaseVar = 100;
			if (probabilityValueBaseVarHM != 0)
				oddsRatioBaseVar = Math.log(probabilityValueBaseVarLM / probabilityValueBaseVarHM);
//			//Residual Variance Probabilities
			double probabilityValueResidualVarLM = findProbability(residualVarfittingResultLM, varResidualHRPatient);
			double probabilityValueResidualVarHM = findProbability(residualVarfittingResultHM, varResidualHRPatient);
			double oddsRatioResidualVar = 100;
			if (probabilityValueResidualVarHM != 0)
				oddsRatioResidualVar = Math.log(probabilityValueResidualVarLM / probabilityValueResidualVarHM);

			List<Double> probabilityListBaseMean = new ArrayList<Double>();
			probabilityListBaseMean.add(probabilityValueBaseMeanLM);
			probabilityListBaseMean.add(probabilityValueBaseMeanHM);
			probabilityListBaseMean.add(oddsRatioBaseMean);

			List<Double> probabilityListBaseVar = new ArrayList<Double>();
			probabilityListBaseVar.add(probabilityValueBaseVarLM);
			probabilityListBaseVar.add(probabilityValueBaseVarHM);
			probabilityListBaseVar.add(oddsRatioBaseVar);

			List<Double> probabilityListResidualVar = new ArrayList<Double>();
			probabilityListResidualVar.add(probabilityValueResidualVarLM);
			probabilityListResidualVar.add(probabilityValueResidualVarHM);
			probabilityListResidualVar.add(oddsRatioResidualVar);

			probabilityBaseMeanHR.put(uhidPatient, probabilityListBaseMean);
			probabilityBaseVarHR.put(uhidPatient, probabilityListBaseVar);
			probabilityResidualVarHR.put(uhidPatient, probabilityListResidualVar);

		}
	}

	// For given recommendation status i.e. HM or LM retrieve data from database
	// using
	// device graph json API being used on Trends screen. It also build
	// SignalStructure
	// that contain original, basesignal and residual signal structure
	public SignalStructure retrieveResidualMap(HashMap<String, DeviceGraphDataJson> babieslistMap,
			String recommendationStatus) {
		SignalStructure signalStructure = new SignalStructure();
		HashMap<String, String> recommendationStatusUHID = new HashMap<String, String>();
		HashMap<String, List<Float>> listResidualMap = new HashMap<String, List<Float>>();
		HashMap<String, List<Float>> baseSignalMap = new HashMap<String, List<Float>>();
		HashMap<String, List<Float>> originalSignalMap = new HashMap<String, List<Float>>();
		HashMap<String, Double> baseSignalMean = new HashMap<String, Double>();
		HashMap<String, Double> baseSignalVar = new HashMap<String, Double>();
		HashMap<String, Double> residualSignalVar = new HashMap<String, Double>();

		signalStructure.setBaseSignalListMap(baseSignalMap);
		signalStructure.setOriginalListMap(originalSignalMap);
		signalStructure.setResidualListMap(listResidualMap);

		if (!BasicUtils.isEmpty(babieslistMap)) {
			Set<String> keySetToAssess = babieslistMap.keySet();
			Iterator<String> iterator = keySetToAssess.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				DeviceGraphDataJson graphJSON = babieslistMap.get(key);
				String uhid = graphJSON.getUhid();
				// if there is no data for given uhid then graphjson will be empty
				if (uhid != null && !uhid.equals("null")) {
					DescriptiveStatistics baseSignalStatistics = null;
					DescriptiveStatistics residualSignalStatistics = null;
					boolean isEnoughDataForAnalysis = false;
					// Some centers give PR while others give HR. Populate in same structure PR or
					// HR
					// As per data found in database
					if (graphJSON.getPR().getyAxis() != null
							&& graphJSON.getPR().getyAxis().size() >= TOTAL_DATA_SIZE_MEAN) {
						isEnoughDataForAnalysis = true;
						// Add the UHID into the signal structure list of UHID's
						signalStructure.getUhidList().add(uhid);
						List<Float> originalPRData = getWindowData(graphJSON.getPR().getyAxis(), TOTAL_DATA_SIZE);
						List<Float> baseSignalPRData = getRollingMean(
								getWindowData(graphJSON.getPR().getyAxis(), TOTAL_DATA_SIZE_MEAN));
						List<Float> residualSignalPRData;
						try {
							originalSignalMap.put(uhid, originalPRData);
							residualSignalPRData = subtractTwoSignals(originalPRData, baseSignalPRData);
							listResidualMap.put(uhid, residualSignalPRData);
							baseSignalMap.put(uhid, baseSignalPRData);
							baseSignalStatistics = DataAnalyticsUtilities.getSignalStatistics(baseSignalPRData);
							residualSignalStatistics = DataAnalyticsUtilities.getSignalStatistics(residualSignalPRData);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (graphJSON.getHR().getyAxis() != null
							&& graphJSON.getHR().getyAxis().size() >= TOTAL_DATA_SIZE_MEAN) {
						isEnoughDataForAnalysis = true;
						List<Float> originalHRData = getWindowData(graphJSON.getHR().getyAxis(), TOTAL_DATA_SIZE);
						List<Float> baseSignalHRData = getRollingMean(
								getWindowData(graphJSON.getHR().getyAxis(), TOTAL_DATA_SIZE_MEAN));
						List<Float> residualSignalHRData;
						try {
							originalSignalMap.put(graphJSON.getUhid(), originalHRData);
							residualSignalHRData = subtractTwoSignals(originalHRData, baseSignalHRData);
							listResidualMap.put(graphJSON.getUhid(), residualSignalHRData);
							baseSignalMap.put(graphJSON.getUhid(), baseSignalHRData);
							baseSignalStatistics = DataAnalyticsUtilities.getSignalStatistics(baseSignalHRData);
							residualSignalStatistics = DataAnalyticsUtilities.getSignalStatistics(residualSignalHRData);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (isEnoughDataForAnalysis) {
						signalStructure.setResidualListMap(listResidualMap);
						// get base signal mean & variance
						baseSignalMean.put(uhid, baseSignalStatistics.getMean());
						baseSignalVar.put(uhid, baseSignalStatistics.getVariance());
						// get residual signal variance
						residualSignalVar.put(uhid, residualSignalStatistics.getVariance());
						recommendationStatusUHID.put(uhid, recommendationStatus);
						signalStructure.setBaseSignalMean(baseSignalMean);
						signalStructure.setBaseSignalVar(baseSignalVar);
						signalStructure.setResidualSignalVar(residualSignalVar);
						signalStructure.setRecommendationStatusUHID(recommendationStatusUHID);
					} else {
						// Not enough data for this UHID - remove from data pipeline
						System.out.println("-----Not enough data for this baby. Remove this baby from analysis-------"+uhid);
					}
				}

			}
		}

		return signalStructure;
	}

	// This function iterates through the hashmap for all patients and puts the
	// values in list
	public List<Double> receivelSignalToFit(HashMap<String, Double> signalMap) {

		List<Double> objList = new ArrayList<Double>();
		for (Map.Entry<String, Double> entry : signalMap.entrySet()) {
			objList.add(entry.getValue());
		}
		return objList;
	}

	public List<Float> receiveResidualSignal(HashMap<String, List<Float>> residualListMap) {
		List<Float> objList = new ArrayList<Float>();
		if (!BasicUtils.isEmpty(residualListMap)) {
			Set<String> keySetToAssess = residualListMap.keySet();
			Iterator<String> iterator = keySetToAssess.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				List<Float> prValues = residualListMap.get(key);
				for (int i = 0; i < prValues.size(); i++) {
					if (objList.size() > i && objList.get(i) != null) {
						Float newValue = prValues.get(i);
						Float oldValue = objList.get(i);
						objList.add(i, (newValue + oldValue) / 2);
					} else {
						objList.add(i, prValues.get(i));
					}
				}
			}
		}
		return objList;
	}

	private void plotResponseCurve(double[] yAxisData, String graphTitle, String xAxisLegend, String yAxisLegend,
			int lowerValueRange, int higherValueRange, int incrementValue) throws Exception {
		double[][] data = null; // data points
		int nMax = yAxisData.length;
		data = PlotGraph.data(1, nMax);
		int counterj = 0;
		for (int i = lowerValueRange; i < higherValueRange; i = i + incrementValue) {
			data[0][counterj] = i; // x-axis data
			data[1][counterj] = yAxisData[counterj]; // y-axis data
			counterj++;
		}
		PlotGraph pg = new PlotGraph(data);
		pg.setGraphTitle(graphTitle);
		pg.setXaxisLegend(xAxisLegend);
		pg.setYaxisLegend(yAxisLegend);
		int[] pointOptions = { 4 }; // Set point option to open circles on the first graph line and filled circles
									// on the second graph line
		pg.setPoint(pointOptions);
		pg.setLine(1); // Set line option to a continuous lines and a 200 point cubic spline
						// interpolation
		pg.plot();

	}

	private void plotCurve(double[] yAxisData, double[] xAxisData, String graphTitle, String xAxisLegend,
			String yAxisLegend) throws Exception {
		double[][] data = null; // data points
		int nMax = yAxisData.length;
		data = PlotGraph.data(1, nMax);
		int counterj = 0;
		for (int i = 0; i < nMax; i = i + 1) {
			data[0][counterj] = xAxisData[counterj]; // x-axis data
			data[1][counterj] = yAxisData[counterj]; // y-axis data
			counterj++;
		}
		PlotGraph pg = new PlotGraph(data);
		pg.setGraphTitle(graphTitle);
		pg.setXaxisLegend(xAxisLegend);
		pg.setYaxisLegend(yAxisLegend);
		int[] pointOptions = { 4 }; // Set point option to open circles on the first graph line and filled circles
									// on the second graph line
		pg.setPoint(pointOptions);
		pg.setLine(1); // Set line option to a continuous lines and a 200 point cubic spline
						// interpolation
		pg.plot();

	}

	private double[] generateCurveLogOddsRatio(RegressionResult hmRegression, RegressionResult lmRegression,
			String signalType) throws Exception {
		double[] yCalculateHM = hmRegression.getCurrentRegression().getYcalc();
		Double[] yCalculateHM_D = ArrayUtils.toObject(yCalculateHM);
		double[] yCalculateLM = lmRegression.getCurrentRegression().getYcalc();
		Double[] yCalculateLM_D = ArrayUtils.toObject(yCalculateLM);
		System.out.println("Analysis for calculated signalType=" + signalType);
		DataAnalyticsUtilities.printDoubleArray(yCalculateHM_D, "HM ");
		DataAnalyticsUtilities.printDoubleArray(yCalculateLM_D, "LM ");
		double yCalculateHMMax = Collections.max(Arrays.asList(yCalculateHM_D));
		double yCalculateLMMax = Collections.max(Arrays.asList(yCalculateLM_D));

		// HS: override normalization
		yCalculateHMMax = 1;
		yCalculateLMMax = 1;
		int lengthArrayHM = yCalculateHM.length;
		int lengthArraylM = yCalculateLM.length;
		if (lengthArrayHM != lengthArraylM) {
			throw new Exception("Cant divide two distribution of unequal sizes");
		}
		double[] YCalculateCurve = new double[yCalculateHM.length];
		double[] YCalculateCurveLog = new double[yCalculateHM.length];
		for (int i = 0; i < lengthArrayHM; i++) {
			if (yCalculateLM[i] == 0 || yCalculateHM[i] == 0) {
				YCalculateCurve[i] = 0;
			} else {
				// YCalculateCurve[i] = Math.log(yCalculateHM[i]/yCalculateLM[i]);

				// HS: non log version
				double yProbHM = (yCalculateHM[i] / yCalculateHMMax);
				double yProbLM = (yCalculateLM[i] / yCalculateLMMax);
				if (yProbHM < 0.001)
					yProbHM = 0;
				else if (yProbLM < 0.001)
					yProbLM = 0.01;
				YCalculateCurve[i] = (yProbHM / yProbLM);
				YCalculateCurveLog[i] = Math.abs(Math.log(yProbHM / yProbLM));

			}
		}
		DataAnalyticsUtilities.printdoubleArray(YCalculateCurve, "odds ratio");
		DataAnalyticsUtilities.printdoubleArray(YCalculateCurveLog, "Log odds ratio");
		return YCalculateCurve;
	}

	public DescriptiveStatistics getSignalStatistics(List<Float> signal) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		// Window size is 3 hour of data
		stats.setWindowSize(signal.size());
		for (int i = 0; i < signal.size(); i++) {
			stats.addValue(signal.get(i));
		}
		return stats;
	}

	public List<Float> getWindowData(List<Float> inputSignal, int size) {
		List<Float> yWindowData = new ArrayList<Float>();
		for (int i = 0; i < inputSignal.size(); i++) {
			if (i == size)
				break;
			yWindowData.add(i, inputSignal.get(i));
		}
		return yWindowData;
	}

	public List<Float> getRollingMean(List<Float> originalSignal) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		// Window size is 3 hour of data
		stats.setWindowSize(WINDOW_SIZE);
		long nLines = 0;
		int jCounter = 0;
		List<Float> yMeanRollingData = new ArrayList<Float>();
		for (int i = 0; i < originalSignal.size(); i++) {
			nLines++;
			stats.addValue(originalSignal.get(i));
			if (nLines > WINDOW_SIZE) {
				yMeanRollingData.add(jCounter, (float) stats.getMean());
				jCounter++;
			}
		}
		return yMeanRollingData;

	}

	public List<Float> getSPO2LessThan85(List<Float> originalSignal) {
		List<Float> ySP02LessThan85Data = new ArrayList<Float>();
		int jCounter = 0;
		for (int i = 0; i < originalSignal.size(); i++) {
			float valueSpO2 = originalSignal.get(i);
			if (valueSpO2 < 85) {
				ySP02LessThan85Data.add(jCounter, valueSpO2);
				jCounter++;
			}
		}
		return ySP02LessThan85Data;

	}

	public List<Float> generatePhysiScore(HashMap<String, List<Float>> hashMap) {
		List<Float> yWindowData = new ArrayList<Float>();
		for (int i = 0; i < hashMap.size(); i++) {
			yWindowData.add(i, new Float(1));
		}
		return yWindowData;
	}

	public List<Float> subtractTwoSignals(List<Float> signal1, List<Float> signal2) throws Exception {
		if (signal1.size() != signal2.size()) {
			throw new Exception("Two list are of non-equal size so can not subtract signal1=" + signal1.size()
					+ " signal2=" + signal2.size());
		}
		List<Float> yMeanSubtractData = new ArrayList<Float>();
		for (int i = 0; i < signal1.size(); i++) {
			yMeanSubtractData.add(i, (float) (signal1.get(i) - signal2.get(i)));
		}
		return yMeanSubtractData;
	}

	/* public static void main(String[] args) {
		try {
			Classifier classifier = new Logistic();

			// For output file
			File outputFile = new File(trainingDataFile);
			// int classIdOut = 4;
			CSVLoader predictCsvLoaderOut = new CSVLoader();
			predictCsvLoaderOut.setSource(outputFile);
			// provide to weka - type of column attribute - index starting from 1
			predictCsvLoaderOut.setStringAttributes("1");
			predictCsvLoaderOut.setNumericAttributes("2,3,4");
			predictCsvLoaderOut.setNominalAttributes("5");
			Instances predictDataSetOut = predictCsvLoaderOut.getDataSet();
			Attribute testAttributeOut = predictDataSetOut.attribute(4);
			predictDataSetOut.setClass(testAttributeOut);

			// remove all string attributes
			predictDataSetOut.deleteStringAttributes();
			// Class is HM or LM as last column
			// remove all probability column from logistics - index starting from 0 and keep
			// only first three values
			// along with last class column
			/** Classifier here is Linear Regression 
			classifier.buildClassifier(predictDataSetOut);
			System.out.print(" the expression for the input data as per algorithm is ");
			System.out.println(classifier);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}*/

}
