package com.inicu.analytics;

import com.inicu.analytics.entities.FittingResult;

import flanagan.analysis.ProbabilityPlot;
import flanagan.analysis.Regression;
import flanagan.math.Fmath;
import jsat.distributions.ContinuousDistribution;
import jsat.distributions.empirical.KernelDensityEstimator;
import jsat.distributions.empirical.kernelfunc.KernelFunction;

public class RegressionResult {

    private double meanFit = 0.0;                   // best estimate mean (mu) from non-linear regression
    private double standardDeviationFit = 0.0;      // best estimate standard deviation (sigma) from non-linear regression
    private double scaleConstantFit = 0.0;          // best estimate scale constant (Ao) from non-linear regression
    private double[] calculatedFrequency = null;    // frequency values calculated for best estimates
    private double[] residuals = null;              // residuals
	private double ss = 0.0;
	private double[] bestEstimates =null;
	private String regressionType = null;
	private String regressionDescriptiveType = null;
	private String signalType = null;
	private double degreeFreedom = 0.0;
	private RegressioniNICU currentRegression = null;
	private String recommendedStatus;	
	private ProbabilityPlotiNICU probPlot =null;
	private double alphaValue = 0.0;
	private double betaValue = 0.0;
	private double kvalue;
	private KernelDensityEstimator kernelEstimator =null;
	
	public KernelDensityEstimator getKernelEstimator() {
		return kernelEstimator;
	}

	public void setKernelEstimator(KernelDensityEstimator kernelEstimator) {
		this.kernelEstimator = kernelEstimator;
	}

	public String getRegressionDescriptiveType() {
		return regressionDescriptiveType;
	}

	public void setRegressionDescriptiveType(String regressionDescriptiveType) {
		this.regressionDescriptiveType = regressionDescriptiveType;
	}

	public double getKvalue() {
		return kvalue;
	}

	public void setKvalue(double kvalue) {
		this.kvalue = kvalue;
	}

	public double getThetaValue() {
		return thetaValue;
	}

	public void setThetaValue(double thetaValue) {
		this.thetaValue = thetaValue;
	}

	private double thetaValue;
	
	
	public double getAlphaValue() {
		return alphaValue;
	}

	public void setAlphaValue(double alphaValue) {
		this.alphaValue = alphaValue;
	}

	public double getBetaValue() {
		return betaValue;
	}

	public void setBetaValue(double betaValue) {
		this.betaValue = betaValue;
	}

	public ProbabilityPlotiNICU getProbPlot() {
		return probPlot;
	}

	public void setProbPlot(ProbabilityPlotiNICU probPlot) {
		this.probPlot = probPlot;
	}

	public String getRecommendedStatus() {
		return recommendedStatus;
	}

	public void setRecommendedStatus(String recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}
	
	public String getSignalType() {
		return signalType;
	}

	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}

	public RegressioniNICU getCurrentRegression() {
		return currentRegression;
	}

	public void setCurrentRegression(RegressioniNICU currentRegression) {
		this.currentRegression = currentRegression;
	}

	public double getDegreeFreedom() {
		return degreeFreedom;
	}

	public void setDegreeFreedom(double degreeFreedom) {
		this.degreeFreedom = degreeFreedom;
	}

	public String getRegressionType() {
		return regressionType;
	}

	public void setRegressionType(String regressionType) {
		this.regressionType = regressionType;
	}

	public double[] getBestEstimates() {
		return bestEstimates;
	}

	public void setBestEstimates(double[] bestEstimates) {
		this.bestEstimates = bestEstimates;
	}

	public double getSs() {
		return ss;
	}

	public void setSs(double ss) {
		this.ss = ss;
	}

	public double getMeanFit() {
		return meanFit;
	}

	public void setMeanFit(double meanFit) {
		this.meanFit = meanFit;
	}

	public double getStandardDeviationFit() {
		return standardDeviationFit;
	}

	public void setStandardDeviationFit(double standardDeviationFit) {
		this.standardDeviationFit = standardDeviationFit;
	}

	public double getScaleConstantFit() {
		return scaleConstantFit;
	}

	public void setScaleConstantFit(double scaleConstantFit) {
		this.scaleConstantFit = scaleConstantFit;
	}

	public double[] getCalculatedFrequency() {
		return calculatedFrequency;
	}

	public void setCalculatedFrequency(double[] calculatedFrequency) {
		this.calculatedFrequency = calculatedFrequency;
	}

	public double[] getResiduals() {
		return residuals;
	}

	public void setResiduals(double[] residuals) {
		this.residuals = residuals;
	}

	public RegressionResult(ProbabilityPlotiNICU probPlot,String typeRegression,String recommendationStatus)
	{
		this.probPlot = probPlot;
		this.regressionType = typeRegression;
		if(probPlot != null)
		{
			if(typeRegression.equals(RegressionConstants.GAMMA))
			{
				this.meanFit = probPlot.normalMu();
				this.standardDeviationFit = probPlot.normalSigma();
				this.ss = probPlot.normalSumOfSquares();			
			}
			else if(typeRegression.equals(RegressionConstants.WEIBULL))
			{
				this.meanFit = probPlot.weibullMu();
				this.standardDeviationFit = probPlot.weibullSigma();
				this.ss = probPlot.weibullSumOfSquares();
			}
			else if(typeRegression.equals(RegressionConstants.EXPONENTIAL))
			{
				this.meanFit = probPlot.exponentialMu();
				this.standardDeviationFit = probPlot.exponentialSigma();
				this.ss = probPlot.exponentialSumOfSquares();
			}		
			else if(typeRegression.equals(RegressionConstants.LOGNORMAL))
			{
				this.meanFit = probPlot.logNormalMu();
				this.standardDeviationFit = probPlot.standardDeviation();
				this.ss = probPlot.logNormalSumOfSquares();
			}	
		}
	}
	public RegressionResult(ContinuousDistribution reg,String typeRegression,String recommendationStatus)
	{
		this.regressionType = reg.getDistributionName();
	    bestEstimates = reg.getCurrentVariableValues();
	    this.regressionDescriptiveType = reg.getDescriptiveName();
		if(typeRegression.equals(RegressionConstants.GAMMA))
		{
			this.kvalue = bestEstimates[0];
			this.thetaValue = bestEstimates[1];
		}
		else if(typeRegression.equals(RegressionConstants.WEIBULL))
		{
		    this.alphaValue = bestEstimates[0];
		    this.betaValue = bestEstimates[1];
		}
		else if(typeRegression.equals(RegressionConstants.EXPONENTIAL))
		{
			this.meanFit = bestEstimates[0];
		}		
		else if(typeRegression.equals(RegressionConstants.LOGNORMAL))
		{
			this.meanFit = bestEstimates[0];
			this.standardDeviationFit = bestEstimates[1];
		}	
		else if(typeRegression.equals(RegressionConstants.NORMAL))
		{
			this.meanFit = bestEstimates[0];
			this.standardDeviationFit = bestEstimates[1];
		}	
		else if(typeRegression.equals(RegressionConstants.KDE))
		{
			this.kernelEstimator = (KernelDensityEstimator)reg;
		}		
		
		
        this.recommendedStatus = recommendationStatus;
        //System.out.println("Sum of squares of residuals: "+ Fmath.truncate(ss, RegressionConstants.trunc));
	}
	
	public RegressionResult(RegressioniNICU reg,String typeRegression,String recommendationStatus)
	{
		this.currentRegression = reg;
		this.regressionType = typeRegression;
	    bestEstimates = reg.getBestEstimates();
	    this.meanFit = bestEstimates[0];
	    if(bestEstimates.length > 1)
	    {
	    	this.standardDeviationFit = bestEstimates[1];
	    	if(bestEstimates.length > 2)
	    	{
	    		this.scaleConstantFit = bestEstimates[2];
	    	}
	    }
	    this.calculatedFrequency = reg.getYcalc();
	    this.residuals = reg.getResiduals();
        this.ss = reg.getSumOfSquares();
        this.degreeFreedom = reg.getDegFree();
        this.recommendedStatus = recommendationStatus;
        //System.out.println("Sum of squares of residuals: "+ Fmath.truncate(ss, RegressionConstants.trunc));
	}
	
	public FittingResult generateFittingResult()
	{
		FittingResult fittingResult = new FittingResult();		
		fittingResult.setKvalue(this.kvalue);
		fittingResult.setThetaValue(this.thetaValue);
		fittingResult.setAlphaValue(this.alphaValue);
		fittingResult.setBetaValue(this.betaValue);
		fittingResult.setMeanFit(this.meanFit);
		fittingResult.setStandardDeviationFit(this.standardDeviationFit);
		fittingResult.setScaleConstantFit(this.scaleConstantFit);
		fittingResult.setRegressionType(this.regressionType);
		fittingResult.setSignalType(this.signalType);
		fittingResult.setDegreeFreedom(this.degreeFreedom);
		fittingResult.setRecommendedStatus(this.recommendedStatus);
		fittingResult.setSs(this.ss);
		return fittingResult;
	}

}


