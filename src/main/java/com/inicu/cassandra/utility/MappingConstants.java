package com.inicu.cassandra.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.VentilatorData;

public class MappingConstants {

	public static HashMap<String, String> metricMap = new HashMap<String,String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put("MDC_PULS_OXIM_PLETH","plethysmogram");
			put("MDC_ECG_LEAD_I","Lead I");
			put("MDC_ECG_LEAD_II","Lead II");
			put("MDC_ECG_LEAD_III","Lead III");
			put("MDC_AWAY_CO2_ET","ETCO2");
			put("MDC_PRESS_BLD_ART_ABP","ArterialBloodPressure");
			put("MDC_PRESS_CUFF_SYS","SYS");
			put("MDC_PRESS_BLD_ART_ABP_SYS","SYS");
			put("MDC_PRESS_CUFF_DIA","DIA");
			put("MDC_PRESS_BLD_ART_ABP_DIA","DIA");
			put("MDC_ECG_HEART_RATE","HEART RATE");
			put("MDC_CO2_RESP_RATE","CO2 RESP RATE");
			put("MDC_TTHOR_RESP_RATE","ECG RESP RATE");
			put("MDC_PULS_OXIM_PULS_RATE","PULSE");
			put("MDC_PULS_OXIM_SAT_O2","SPO2");
			put("MDC_AWAY_CO2","CO2");
			put("MDC_PRESS_BLD_NONINV_MEAN","BP MEAN");
			put("MDC_RESP_RATE","ECG RESP RATE");
		}

	};


	public static HashMap<String, String> numericMetricMap = new HashMap<String,String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{

			put("MDC_PRESS_BLD_ART_ABP_SYS","SYS");
			put("MDC_PRESS_CUFF_SYS","SYS");
			put("MDC_PRESS_CUFF_DIA","DIA");
			put("MDC_PRESS_BLD_ART_ABP_DIA","DIA");
			put("MDC_ECG_HEART_RATE","HEART RATE");
			put("MDC_TTHOR_RESP_RATE","ECG RESP RATE");
			put("MDC_RESP_RATE","ECG RESP RATE");
			put("MDC_CO2_RESP_RATE","CO2 RESP RATE");
			put("MDC_AWAY_CO2_ET","ETCO2");
			put("MDC_PULS_OXIM_PULS_RATE","PULSE");
			put("MDC_PULS_OXIM_SAT_O2","SPO2");
			put("MDC_PRESS_BLD_NONINV_MEAN","BP MEAN");
			//			put("MDC_AWAY_CO2","CO2"); //doubt 
		}

	};


	public static HashMap<String,HashMap<String, String>> patientDataMap = new HashMap<String,HashMap<String,String>>();
	public static HashMap<String,HashMap<String, String>> tempPatientDataMap = new HashMap<String,HashMap<String,String>>();
	
	/**
	 * <domain id ,patient id>
	 */
	public static HashMap<String, String> patientDomainMap = new HashMap<String,String>();
	
	// parameters
	public static final String hr = "HEART RATE";
	public static final String co2 = "CO2";
	public static final String spo2 = "SPO2";
	public static final String co2RespRate = "CO2 RESP RATE";
	public static final String ecgRespRate = "ECG RESP RATE";
	public static final String pulse = "PULSE";
	public static final String sys = "SYS";
	public static final String dia = "DIA";

	
	// parameters required to configure intellivue series
	// philips data acquisition
	public static final String INTELLIVUE_APPLICATION           = "ICE_Device_Interface";
	public static final String INTELLIVUE_DOMAIN_ID             = "";
	public static final String INTELLIVUE_DEVICE_TYPE           = "IntellivueEthernet";
	public static final String INTELLIVUE_ADDRESS               = "";
	public static final String INTELLIVUE_FHIR_SERVER_NAME      = "";


	public static final String EVENT_TIME = "event_time";
	
	public static List<VentilatorData> patientVentilatorTempData = new ArrayList<>();
	public static ArrayList<String> ventilatorParams = new ArrayList<String>() {{
	    add("SpontaneousRespiratoryRate");
	    add("PeakBreathingPressure mbar");
	    add("PEEPBreathingPressure mbar");
	    add("MinimalAirwayPressure");
	    add("MeanBreathingPressure mbar");
	    add("InspO2 pct");
	    add("PlateauPressure mbar");
	    add("OcclusionPressure");
	}};

	public static String staticBpSYS = "NaN";
	public static String staticBpDIA = "NaN";
	public static String staticBpMEAN = "NaN";
}
