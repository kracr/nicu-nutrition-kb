package com.inicu.analytics;

import java.util.ArrayList;
import java.util.List;
import flanagan.analysis.Stat;
import flanagan.interpolation.CubicSpline;
import flanagan.math.Fmath;
import flanagan.plot.PlotGraph;
import jsat.distributions.ContinuousDistribution;
import jsat.linear.DenseVector;
import static com.inicu.analytics.DistributionSearchiNICU.getBestDistribution;

public class FitDataIntoDistribution {

	Stat a;
	ArrayList<Double> signalList = null;
	private double[] signalData = null;
	
	private double[] signals = null; // the centre of the signals for each histogram bin
	private double[] frequency = null; // the number of signals in each histogram bin
	private String title = "Log Normal Fit of data"; // data title
	private double meanCalc = 0.0; // calculated mean of the signals
	private double standardDeviationCalc = 0.0; // calculated standard deviation of the signals
	private double standardDeviationUsed = 0.0; // used standard deviation of the signals
	private double momentSkewness = 0.0; // calculated moment skewness;
	private double medianSkewness = 0.0; // calculated median skewness;
	private double quartileSkewness = 0.0; // calculated quartile skewness;
	private double excessKurtosis = 0.0; // calculated excess kurtosis;
	private ProbabilityPlotiNICU probPlot = null; // instance of probabilityPlot
	private int numberPresent = 0; // number of points in the signal
	private int nBins = 0; // number of bins in the data histogram
	private double binWidth = 0; // histogram bin width
	private int nBinPoints = 0; // number of histogram coordinates
	private double[] binXpoints = null; // histogram x-coordinates
	private double[] binYpoints = null; // histogram y-coordinates
	private double peakValue = -1; // frequency value for peak bin
	private double peakPosition = -1; // position of peak bin

	public FitDataIntoDistribution(List<Double> inSignalDataF) {
		double[] inSignalData = new double[inSignalDataF.size()];
		signalList = new ArrayList<Double>();
		int i=0;
		for(Double f : inSignalDataF)
		{
			inSignalData[i] = Math.abs(f);
			i++;
			signalList.add(f);
		}
		signalData = inSignalData;
		numberPresent = inSignalData.length;
	}

	/*public static void main(String[] arg) {

		FitDataIntoDistribution fitDist = new FitDataIntoDistribution(null);
		fitDist.performHistogramAndFindBestFit("TEST_SIGNAL","HM");

	}*/
	
	public RegressionResult performHistogramAndFindBestFit(String inSignalType,String recommendationStatus) {
		
		RegressionResult bestResult = null;
		DenseVector vec = new DenseVector(signalList);		
		//Pass any value more than 1 to use KDE. Pass O to use best fit distribution
		ContinuousDistribution distribution = getBestDistribution(vec,2);
		bestResult = bestFitRegression(distribution, recommendationStatus);
		//inSignalData vector contains the data to be fitted
		return bestResult;

	}
	//Signal Type represent base, residual or original signal
	public RegressionResult performHistogramAndFindBestFit(String inSignalType,String recommendationStatus,boolean flanagan) {
		statistics();
		// Probability plot
		probabilityPlot();
		// Distribute the data into histogram bins
		histogram(inSignalType);
		// Perform Regression
		RegressionResult exponentialResult = performExponentialRegression(inSignalType,recommendationStatus);
		RegressionResult logNormalResult = performLogNormalRegression(inSignalType,recommendationStatus);
		RegressionResult weibullResult = performWeibullRegression(inSignalType,recommendationStatus);
		RegressionResult gammaResult = performGammaRegression(inSignalType,recommendationStatus);
		RegressionResult bestResult = bestFitRegression(exponentialResult, logNormalResult, weibullResult,
				gammaResult);
		return bestResult;
	}

	private RegressionResult bestFitRegression(ContinuousDistribution distribution, String recommendationStatus) {
		RegressionResult bestResult = null;
		if(distribution.getDistributionName().equalsIgnoreCase("Weibull"))
		{
			bestResult = new RegressionResult(distribution,RegressionConstants.WEIBULL ,recommendationStatus);
		}
		else if(distribution.getDistributionName().equalsIgnoreCase("LogNormal"))
		{
			bestResult = new RegressionResult(distribution,RegressionConstants.LOGNORMAL ,recommendationStatus);
		}
		else if(distribution.getDistributionName().equalsIgnoreCase("Gamma"))
		{		
			bestResult = new RegressionResult(distribution,RegressionConstants.GAMMA ,recommendationStatus);			
		}
		else if(distribution.getDistributionName().equalsIgnoreCase("Exponential"))
		{		
			bestResult = new RegressionResult(distribution,RegressionConstants.EXPONENTIAL ,recommendationStatus);
		}
		else if(distribution.getDistributionName().equalsIgnoreCase("Normal"))
		{		
			bestResult = new RegressionResult(distribution,RegressionConstants.NORMAL ,recommendationStatus);
		}		
		else if(distribution.getDistributionName().equalsIgnoreCase("Kernel Density Estimate"))
		{		
			bestResult = new RegressionResult(distribution,RegressionConstants.KDE ,recommendationStatus);
		}			
		
		
		return bestResult;

	}
	private RegressionResult bestFitRegression(RegressionResult exponentialResult, RegressionResult logNormalResult,
			RegressionResult weibullResult, RegressionResult gammaResult) {

		RegressionResult bestResult = null;
		if(Double.isNaN(exponentialResult.getSs()))
			exponentialResult.setSs(Double.MAX_VALUE);
		if(Double.isNaN(logNormalResult.getSs()))
			logNormalResult.setSs(Double.MAX_VALUE);
		if(Double.isNaN(gammaResult.getSs()))
			gammaResult.setSs(Double.MAX_VALUE);
		if(Double.isNaN(weibullResult.getSs()))
			weibullResult.setSs(Double.MAX_VALUE);
		
		
		if ((exponentialResult.getSs() < weibullResult.getSs()) && (exponentialResult.getSs() < logNormalResult.getSs())
				&& (exponentialResult.getSs() < gammaResult.getSs())) {
			bestResult = exponentialResult;
		} else if ((weibullResult.getSs() < exponentialResult.getSs())
				&& (weibullResult.getSs() < logNormalResult.getSs()) && (weibullResult.getSs() < gammaResult.getSs())) {
			bestResult = weibullResult;
		} else if ((logNormalResult.getSs() < exponentialResult.getSs())
				&& (logNormalResult.getSs() < weibullResult.getSs())
				&& (logNormalResult.getSs() < gammaResult.getSs())) {
			bestResult = logNormalResult;
		} else {
			bestResult = gammaResult;
		}
		//System.out.println("exponentialResult SS="+exponentialResult.getSs()+" weibullResult Ss="+weibullResult.getSs()
		//					+"gammaResult SS="+gammaResult.getSs() +"logNormalResult SS="+logNormalResult.getSs());
		// check squared errors - which one has minimum
		//Plot best fit curve.
		
		if(bestResult.equals(exponentialResult))
		{
            // Initialize data arrays for plotting
            double[][] data = PlotGraph.data(2,exponentialResult.getProbPlot().numberOfDataPoints);

            // Assign data to plotting arrays
            data[0] = exponentialResult.getProbPlot().exponentialOrderMedians;
            data[1] = exponentialResult.getProbPlot().sortedData;

            data[2] = exponentialResult.getProbPlot().exponentialOrderMedians;
            for(int i=0; i<exponentialResult.getProbPlot().numberOfDataPoints; i++){
                data[3][i] = exponentialResult.getProbPlot().exponentialLine[0] + exponentialResult.getProbPlot().exponentialLine[1]*exponentialResult.getProbPlot().exponentialOrderMedians[i];
            }

            // Create instance of PlotGraph
            PlotGraph pg = new PlotGraph(data);
            int[] points = {4, 0};
            pg.setPoint(points);
            int[] lines = {0, 3};
            pg.setLine(lines);
            pg.setXaxisLegend("Exponential Order Statistic Medians");
            pg.setYaxisLegend("Ordered Data Values");
            pg.setGraphTitle("Exponential probability plot:   gradient = " + Fmath.truncate(exponentialResult.getProbPlot().exponentialLine[1], 4) + ", intercept = "  +  Fmath.truncate(exponentialResult.getProbPlot().exponentialLine[0], 4) + ",  R = " + Fmath.truncate(exponentialResult.getProbPlot().exponentialCorrCoeff, 4));
            pg.setGraphTitle2("  mu = " + Fmath.truncate(exponentialResult.getProbPlot().exponentialParam[0], 4) + ", sigma = "  +  Fmath.truncate(exponentialResult.getProbPlot().exponentialParam[1], 4));

            // Plot
            pg.plot();

		}
		else if(bestResult.equals(weibullResult))
		{
            // Initialize data arrays for plotting
            double[][] data = PlotGraph.data(2,weibullResult.getProbPlot().numberOfDataPoints);

            // Assign data to plotting arrays
            data[0] = weibullResult.getProbPlot().weibullOrderMedians;
            data[1] = weibullResult.getProbPlot().sortedData;

            data[2] = weibullResult.getProbPlot().weibullOrderMedians;
            for(int i=0; i<weibullResult.getProbPlot().numberOfDataPoints; i++){
                data[3][i] = weibullResult.getProbPlot().weibullLine[0] + weibullResult.getProbPlot().weibullLine[1]*weibullResult.getProbPlot().weibullOrderMedians[i];
            }


            // Create instance of PlotGraph
            PlotGraph pg = new PlotGraph(data);
            int[] points = {4, 0};
            pg.setPoint(points);
            int[] lines = {0, 3};
            pg.setLine(lines);
            pg.setXaxisLegend("Weibull Order Statistic Medians");
            pg.setYaxisLegend("Ordered Data Values");
            pg.setGraphTitle("Weibull probability plot:   gradient = " + Fmath.truncate(weibullResult.getProbPlot().weibullLine[1], 4) + ", intercept = "  +  Fmath.truncate(weibullResult.getProbPlot().weibullLine[0], 4) + ",  R = " + Fmath.truncate(weibullResult.getProbPlot().weibullCorrCoeff, 4));
            pg.setGraphTitle2("  mu = " + Fmath.truncate(weibullResult.getProbPlot().weibullParam[0], 4) + ", sigma = "  +  Fmath.truncate(weibullResult.getProbPlot().weibullParam[1], 4) + ", gamma = "  +  Fmath.truncate(weibullResult.getProbPlot().weibullParam[2], 4));

            // Plot
            pg.plot();
			
		}
		else if(bestResult.equals(logNormalResult))
		{
			bestResult.getCurrentRegression().plotXY(bestResult.getCurrentRegression().getLogNormalFunction());
//	         // Initialize data arrays for plotting
//            double[][] data = PlotGraph.data(2,logNormalResult.getProbPlot().numberOfDataPoints);
//
//            // Assign data to plotting arrays
//            data[0] = logNormalResult.getProbPlot().logNormalOrderMedians;
//            data[1] = logNormalResult.getProbPlot().sortedData;
//
//            data[2] = logNormalResult.getProbPlot().logNormalOrderMedians;
//            for(int i=0; i<logNormalResult.getProbPlot().numberOfDataPoints; i++){
//                data[3][i] = logNormalResult.getProbPlot().logNormalLine[0] + logNormalResult.getProbPlot().logNormalLine[1]*logNormalResult.getProbPlot().logNormalOrderMedians[i];
//            }
//
//            // Create instance of PlotGraph
//            PlotGraph pg = new PlotGraph(data);
//            int[] points = {4, 0};
//            pg.setPoint(points);
//            int[] lines = {0, 3};
//            pg.setLine(lines);
//            pg.setXaxisLegend("Log Normal Order Statistic Medians");
//            pg.setYaxisLegend("Ordered Data Values");
//            pg.setGraphTitle("Log Normal probability plot:   gradient = " + Fmath.truncate(logNormalResult.getProbPlot().logNormalLine[1], 4) + ", intercept = "  +  Fmath.truncate(logNormalResult.getProbPlot().logNormalLine[0], 4) + ",  R = " + Fmath.truncate(logNormalResult.getProbPlot().logNormalCorrCoeff, 4));
//            pg.setGraphTitle2("  mu = " + Fmath.truncate(logNormalResult.getProbPlot().logNormalParam[0], 4) + ", sigma = "  +  Fmath.truncate(logNormalResult.getProbPlot().logNormalParam[1], 4));
//
//            // Plot
//            pg.plot();
			
		}
		
		return bestResult;
	}

	private void statistics() {
		Stat st = new Stat(this.signalData);

		// unscaled data
		this.meanCalc = st.mean();
		this.standardDeviationCalc = st.standardDeviation();
		this.standardDeviationUsed = this.standardDeviationCalc;
		this.momentSkewness = st.momentSkewness();
		this.medianSkewness = st.medianSkewness();
		this.quartileSkewness = st.quartileSkewness();
		this.excessKurtosis = st.excessKurtosis();
	}

	// Probability plot
	private void probabilityPlot() {
		// Create an instance of ProbabilityPlot
		this.probPlot = new ProbabilityPlotiNICU(this.signalData);
	}
	
	   // Distribute data into histogram bins
    private void histogram(String inSignalType){
        double min = Fmath.minimum(this.signalData);
        double max = Fmath.maximum(this.signalData);
        double range = max - min;
        this.binWidth = Math.rint((2.0*this.standardDeviationUsed)/Math.sqrt(this.numberPresent));
        if(this.binWidth<2.0)this.binWidth = 2.0;

        this.nBins = (int)Math.round(range/this.binWidth);
        this.binWidth = range/this.nBins;

        // Calculate bin heights
        double[] binStart = new double[this.nBins];
        double[] binEnd = new double[this.nBins];
        this.signals = new double[this.nBins];
        this.frequency = new double[this.nBins];
        binStart[0] = min;
        binEnd[0] = min + this.binWidth;
        this.signals[0] = (binEnd[0] + binStart[0])/2.0;
        for(int i=1; i<this.nBins; i++){
            binStart[i] = binEnd[i-1];
            binEnd[i] = binStart[i] + this.binWidth;
            this.signals[i] = (binEnd[i] + binStart[i])/2.0;
        }
        binStart[0] *= 0.8;
        binEnd[this.nBins-1] *= 1.2;
        int ii = 0;
        for(int i=0; i<this.nBins; i++){
            this.frequency[i] = 0;
            for(int j=0; j<this.numberPresent; j++){
                if(this.signalData[j]>=binStart[i] && this.signalData[j]<binEnd[i]){
                    frequency[i] += 1.0;
                    ii++;
                }
            }
        }
        if(ii!=this.numberPresent)System.out.println("The number of points in the histogram, " + ii + ", does not equal the number of examination marks, " + this.numberPresent);
        binStart[0] /= 0.8;
        binEnd[this.nBins-1] /= 1.2;

        // Histogram points
        this.nBinPoints = 3*this.nBins + 1;
        this.binXpoints = new double[this.nBinPoints];
        this.binYpoints = new double[this.nBinPoints];
        for(int i=0; i<this.nBins; i++){
            this.binXpoints[3*i] = binStart[i];
            this.binYpoints[3*i] = 0.0;
            this.binXpoints[3*i+1] = binStart[i];
            this.binYpoints[3*i+1] = this.frequency[i];
            this.binXpoints[3*i+2] = binEnd[i];
            this.binYpoints[3*i+2] = this.frequency[i];
        }
        this.binXpoints[this.nBinPoints-1] = binEnd[this.nBins-1];
        this.binYpoints[this.nBinPoints-1] = 0.0;;

        // Peak bin
        this.peakValue = Fmath.maximum(this.frequency);
        for(int i=0; i<this.nBins; i++){
            if(this.frequency[i]==this.peakValue){
                this.peakPosition = this.signals[i];
                break;
            }
        }
		DataAnalyticsUtilities.printdoubleArray(this.frequency,"Frequency");

    }	

	
	private RegressionResult performWeibullRegression(String ininSignalType,String recommendationStatus) {
		//weibull needs more than 4 data points - check number of data points in our dataset
		RegressionResult regressionResult  = null;
		if(this.probPlot.numberOfDataPoints() > 3)
		{
			RegressioniNICU regressioniNICU = new RegressioniNICU(this.signals, this.frequency);
			regressioniNICU.fitWeibull(0, 2);
			regressionResult = new RegressionResult(regressioniNICU,RegressionConstants.WEIBULL ,recommendationStatus);
			regressionResult.setSignalType(ininSignalType);
			
			// Plot regression fit
			//plotRegressionResult(regressionResult);
		}
		else
		{
			this.probPlot = null;
			//no weibull done due to less number of parameters assign highest ss value
			regressionResult = new RegressionResult(this.probPlot, RegressionConstants.WEIBULL,recommendationStatus);
			regressionResult.setSs(Double.MAX_VALUE);
		}
		return regressionResult;
	}

	private RegressionResult performGammaRegression(String ininSignalType,String recommendationStatus) {		
		//Flanagan does not have gamma distribution - check with Ashish which one to use
		RegressioniNICU regressioniNICU = new RegressioniNICU(this.signals, this.frequency);
		regressioniNICU.fitGamma(0, 1);
		RegressionResult regressionResult = new RegressionResult(regressioniNICU,RegressionConstants.LOGNORMAL ,recommendationStatus);
		regressionResult.setSignalType(ininSignalType);
		// Plot regression fit
		//plotRegressionResult(regressionResult);
		return regressionResult;
	}

	private RegressionResult performExponentialRegression(String ininSignalType,String recommendationStatus) {
		
		RegressioniNICU regressioniNICU = new RegressioniNICU(this.signals, this.frequency);
		regressioniNICU.fitExponential(0, 1);
		RegressionResult regressionResult = new RegressionResult(regressioniNICU,RegressionConstants.EXPONENTIAL ,recommendationStatus);
		regressionResult.setSignalType(ininSignalType);
		
		// Plot regression fit
		//plotRegressionResult(regressionResult);
		return regressionResult;
	}

	
	
	// Perform regression
	private RegressionResult performLogNormalRegression(String ininSignalType,String recommendationStatus) {
		
		// Create an instance of Regression
		RegressioniNICU regressioniNICU = new RegressioniNICU(this.signals, this.frequency);
		regressioniNICU.setLogNormalFunction(regressioniNICU.fitLogNormalTwoPar(0));
//		this.probPlot.logNormalProbabilityPlot();
//		RegressionResult regressionResult = new RegressionResult(this.probPlot,RegressionConstants.LOGNORMAL ,recommendationStatus);
		RegressionResult regressionResult = new RegressionResult(regressioniNICU,RegressionConstants.LOGNORMAL ,recommendationStatus);
		regressionResult.setSignalType(ininSignalType);
		// Plot regression fit
		//plotRegressionResult(regressionResult);
		return regressionResult;
	}

	private void plotRegressionResult(RegressionResult regressionResult) {
		double[][] data = PlotGraph.data(3, 200);
		CubicSpline cs = new CubicSpline(this.signals, regressionResult.getCalculatedFrequency());
		double[] xInterp = new double[200];
		double[] yInterp = new double[200];
		double inc = (this.signals[this.nBins - 1] - this.signals[0]) / 199;
		xInterp[0] = this.signals[0];
		for (int i = 1; i < 199; i++)
			xInterp[i] = xInterp[i - 1] + inc;
		xInterp[199] = this.signals[this.nBins - 1];
		for (int i = 0; i < 200; i++)
			yInterp[i] = cs.interpolate(xInterp[i]);
		for (int i = 0; i < this.nBins; i++) {
			data[0][i] = this.signals[i];
			data[1][i] = this.frequency[i];
		}
		for (int i = 0; i < 200; i++) {
			data[2][i] = xInterp[i];
			data[3][i] = yInterp[i];
		}
		for (int i = 0; i < this.nBinPoints; i++) {
			data[4][i] = this.binXpoints[i];
			data[5][i] = this.binYpoints[i];
		}

		PlotGraph pg = new PlotGraph(data);
		int[] lopt = { 0, 3, 3 };
		pg.setLine(lopt);
		int[] popt = { 4, 0, 0 };
		pg.setPoint(popt);
		pg.setGraphTitle("Program: "+regressionResult.getRegressionType() +"  Data of Signals: " + regressionResult.getSignalType());
		pg.setGraphTitle2("Data title: " + this.title + ":  mu = "
				+ Fmath.truncate(regressionResult.getMeanFit(), RegressionConstants.trunc) + "    sigma = "
				+ Fmath.truncate(regressionResult.getStandardDeviationFit(), RegressionConstants.trunc));
		pg.setXaxisLegend("Signals");
		pg.setYaxisLegend("Frequency");
		pg.plot();
	}
}
