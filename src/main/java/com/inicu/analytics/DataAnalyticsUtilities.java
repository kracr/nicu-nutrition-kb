package com.inicu.analytics;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
public class DataAnalyticsUtilities {

	
private static final char DEFAULT_SEPARATOR = ',';
    
    public static void writeLine(Writer w, List<Object> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<Object> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(Object value) {

        String result = ""+value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        if (result.contains("[")) {
            result = result.replace("[", " ");
        }
        if (result.contains("]")) {
            result = result.replace("]", " ");
        }
        return result;

    }

    public static void writeLine(Writer w, List<Object> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (Object value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }
	
	public static DescriptiveStatistics getSignalStatistics(List<Float> signal)
	{
		DescriptiveStatistics stats = new DescriptiveStatistics();		
		//Window size is 3 hour of data
		stats.setWindowSize(signal.size());
		for (int i=0;i<signal.size();i++) {	    	 
			stats.addValue(signal.get(i));
		}
		return stats;
	}
	
	public static void printDoubleList(List<Double> inputValues,String lmHM)
	{
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		String printConsole = lmHM + "Values are =";
		for(Double input : inputValues)
		{
			printConsole = printConsole +" , "+ myFormatter.format(input);
		}
		System.out.println(printConsole);
	}	
	public static void printDoubleArray(Double[] inputValues,String lmHM)
	{
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		String printConsole = lmHM + "Values are =";
		for(Double input : inputValues)
		{
			printConsole = printConsole +" , "+ myFormatter.format(input);
		}
		System.out.println(printConsole);
	}
	public static void printdoubleArray(double[] inputValues,String lmHM)
	{
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		String printConsole = lmHM + "Values are =";
		for(double input : inputValues)
		{
			printConsole = printConsole +" , "+ myFormatter.format(input);
		}
		System.out.println(printConsole);
	}

	/**
	 * This method writes the SignalMap object into an excel (CSV) file
	 * @param signalMap
	 * @param writer
	 * @throws IOException
	 */
	public static void writeIntoCSV(SignalStructure signalMap, Writer writer) throws IOException {
		//Read data from Signal Structure and write to the file
		HashMap<String, List<Float>> originalList = signalMap.getOriginalListMap();
		HashMap<String, List<Float>> baseSignalList = signalMap.getBaseSignalListMap();
		HashMap<String, List<Float>> residualList = signalMap.getResidualListMap();
		HashMap<String, Double> meanBaseSignal = signalMap.getBaseSignalMean();
		HashMap<String, Double> varBaseSignal = signalMap.getBaseSignalVar();
		HashMap<String, Double> varResidualSignal = signalMap.getResidualSignalVar();
		HashMap<String, List<Double>> probabilityBaseMeanHR = signalMap.getProbabilityBaseMeanHR();
		HashMap<String, List<Double>> probabilityBaseVarMeanHR = signalMap.getProbabilityBaseVarHR();
		HashMap<String, List<Double>> probabilityResVarHR = signalMap.getProbabilityResidualVarHR();
		String hmRegressionTypeBaseMean = "";
		String hmRegressionTypeBaseVar = "";
		String hmRegressionTypeResVar = "";
		if(signalMap.getFittingResultBaseMeanHR_HM() != null)
		{
			hmRegressionTypeBaseMean = signalMap.getFittingResultBaseMeanHR_HM().getRegressionDescriptiveType();
		}
		if(signalMap.getFittingResultBaseVarHR_HM() != null)
		{
			hmRegressionTypeBaseVar = signalMap.getFittingResultBaseVarHR_HM().getRegressionDescriptiveType();
		}
		if(signalMap.getFittingResultResidualVarHR_HM() != null)
		{
			hmRegressionTypeResVar = signalMap.getFittingResultResidualVarHR_HM().getRegressionDescriptiveType();
		}		
		String lmRegressionTypeBaseMean ="";
		String lmRegressionTypeBaseVar = "";
		String lmRegressionTypeResVar = "";
		if(signalMap.getFittingResultBaseMeanHR_LM() != null)
		{
			lmRegressionTypeBaseMean = signalMap.getFittingResultBaseMeanHR_LM().getRegressionDescriptiveType();
		}
		if(signalMap.getFittingResultBaseMeanHR_LM() != null)
		{
			lmRegressionTypeBaseVar = signalMap.getFittingResultBaseVarHR_LM().getRegressionDescriptiveType();
		}
		if(signalMap.getFittingResultBaseMeanHR_LM() != null)
		{
			lmRegressionTypeResVar = signalMap.getFittingResultResidualVarHR_LM().getRegressionDescriptiveType();
		}
		
		for(Map.Entry<String, List<Float>> originalEntry  : originalList.entrySet())
		{		
//			System.out.println("originalEntry.getValue()="+originalEntry.getValue().size()+ 
//					" baseSignalList.get(originalEntry.getKey())="+baseSignalList.get(originalEntry.getKey()).size()+
//					" residualList.get(originalEntry.getKey())="+residualList.get(originalEntry.getKey()).size()+
//					" meanBaseSignal.get(originalEntry.getKey())="+1+
//					" varBaseSignal.get(originalEntry.getKey())="+1+
//					" varResidualSignal.get(originalEntry.getKey())="+1+
//					" base mean hmRegressionType ="+hmRegressionTypeBaseMean+
//					" base mean lmRegressionType ="+lmRegressionTypeBaseMean+
//					" base var hmRegressionType ="+hmRegressionTypeBaseVar+
//					" base var lmRegressionType ="+lmRegressionTypeBaseVar+
//					" residual  hmRegressionType ="+hmRegressionTypeResVar+
//					" residual lmRegressionType ="+lmRegressionTypeResVar					
//					);	
			
			
			
			DataAnalyticsUtilities.writeLine(writer, Arrays.asList(originalEntry.getKey(),
					originalEntry.getValue(),
					baseSignalList.get(originalEntry.getKey()), 
					residualList.get(originalEntry.getKey()),
					meanBaseSignal.get(originalEntry.getKey()),
					varBaseSignal.get(originalEntry.getKey()),
					varResidualSignal.get(originalEntry.getKey()),
					probabilityBaseMeanHR.get(originalEntry.getKey()),
					probabilityBaseVarMeanHR.get(originalEntry.getKey()),
					probabilityResVarHR.get(originalEntry.getKey()),
					hmRegressionTypeBaseMean,
					lmRegressionTypeBaseMean,
					hmRegressionTypeBaseVar,
					lmRegressionTypeBaseVar,
					hmRegressionTypeResVar,
					lmRegressionTypeResVar						
					));
		}		
	}
	
}
