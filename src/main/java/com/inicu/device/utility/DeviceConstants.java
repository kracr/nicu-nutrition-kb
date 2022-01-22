package com.inicu.device.utility;

import java.util.HashMap;

import org.json.JSONObject;

import com.inicu.postgres.utility.BasicConstants;

public class DeviceConstants {
	//constants used while registering a device
	public static final String COMMAND_SHUTDOWN="111";
	public static final String REPLY = "response";
	
	
	public static String MAC_ID="macid";
	public static String DEVICE_ID="deviceid";
	public static String DEVICE_TYPE="machineType";
	public static String DEVICE_NAME="devicename";
	public static String DEVICE_BRAND="brandname";
	public static String DEVICE_VISMO = "vismo";
	public static String DEVICE_PHILIPS_INTELLIVUE = "intellivue";
	public static String DEVICE_PHILIPS_INTELLIVUE_MX800 = "intellivuemx800";
	public static String DEVICE_PHILIPS_SURESIGN = "suresign";
	public static String DEVICE_MASIMO_RADICAL = "radical";
	public static String DEVICE_MEDTRONIC_INVOS5100C = "invos5100c";
	public static String DEVICE_MASIMO_ROOT = "root";
	public static String DEVICE_DRAEGER_SLE5000 = "sle5000";
	public static String DEVICE_DRAEGER_EVITA = "evita";
	public static String DEVICE_DRAEGER_INFINITY_DELTA = "infinity_delta";
	public static String DEVICE_DRAEGER_INFINITY_VISTA = "infinity_vista";
	public static String DEVICE_DRAEGER_INFINITY_VISTA_XL = "infinity_vista_xl";
	public static String DEVICE_DRAEGER_VISTA120 = "vista120";
	public static String DEVICE_DRAEGER_BABYLOG8000 = "babylog8000";
	public static String DEVICE_DRAEGER_VN500 = "vn500";
	public static String DEVICE_GE_B40 = "b40";
	public static String DEVICE_RADIOMETER_ABL800 = "abl800";
	public static String DEVICE_STEPHAN_SOPHIE = "sophie";

	public static String DEVICE_NELLCOR_N560 = "n-560";
	public static String DEVICE_DRAEGER_ISOLETTE8000 = "isolette8000";
	public static String DEVICE_GE_GIRAFFE = "giraffe";
	public static String DEVICE_GE_GIRAFFE_WARMER = "giraffe_warmer";

	
	// Device Incubator and warmer Parameters
	public static String DEVICE_INCUBATOR_WARMER_Air_Temp = "AIR_TEMP";
	public static String DEVICE_INCUBATOR_WARMER_Set_Air_Temp = "SET_AIR_TEMP";
	
	public static String DEVICE_INCUBATOR_WARMER_Skin_Temp1 = "TEMP_FROM_PROB1";
	public static String DEVICE_INCUBATOR_WARMER_Skin_Temp2 = "TEMP_FROM_PROB2";
	
	public static String DEVICE_INCUBATOR_WARMER_ambientTemperature ="AMBIENT_TEMP"; 
	
	public static String DEVICE_INCUBATOR_WARMER_Humidity = "HUMIDITY";
	public static String DEVICE_INCUBATOR_WARMER_Set_Humidity = "SET_HUMIDITY";
	
	public static String DEVICE_INCUBATOR_WARMER_O2_SET_POINT = "O2_SET_POINT";
	public static String DEVICE_INCUBATOR_WARMER_O2_MEASURMENT = "O2_MEASUREMENT";
	
	public static String DEVICE_INCUBATOR_WARMER_Weight = "LAST_SCALE_WEIGHT";
	public static String DEVICE_INCUBATOR_WARMER_LiveCountFromWeight = "LiveCountFromWeight";
	public static String DEVICE_MONITOR_TEMPERATURE = "TEMPERATURE";
	public static String DEVICE_INCUBATOR_WARMER_heaterPower = "HEATER_POWER";
	public static String DEVICE_INCUBATOR_WARMER_incubatorMode = "INCUBATOR_MODE";
	public static String DEVICE_INCUBATOR_WARMER_alarm = "ALARM";

	public static String DEVICE_INCUBATOR_WARMER_warmerMode = "WARMER_MODE";
	public static String DEVICE_INCUBATOR_WARMER_WarmerSetTemperature = "WARMER_SET_TEMPERATURE";

	public static String DEVICE_INCUBATOR_WARMER_PATIENT_CONTROL_TEMP = "PATIENT_CONTROL_TEMP";
	public static String DEVICE_INCUBATOR_WARMER_PATIENT_MODE = "PATIENT_MODE";
	public static String DEVICE_INCUBATOR_WARMER_OPEN_BED_MODE = "OPEN_BED_MODE";
	public static String DEVICE_INCUBATOR_WARMER_CLOSED_BED_MODE = "CLOSED_BED_MODE";

	public static String DEVICE_INCUBATOR_WARMER_SPO2_MEASUREMENT = "SPO2_MEASUREMENT";
	public static String DEVICE_INCUBATOR_WARMER_PULSE_RATE_MEASUREMENT = "PULSE_RATE_MEASUREMENT";
	
	//Device Monitor Parameters
	public static String DEVICE_MONITOR_PULSE_RATE = "PR";
	public static String DEVICE_MONITOR_SPO2 = "SPO2";
	public static String DEVICE_MONITOR_HEART_RATE = "HR";
	public static String DEVICE_MONITOR_ECG_RESP_RATE = "ECG_RR";
	public static String DEVICE_MONITOR_CO2_RESP_RATE = "CO2_RR";
	public static String DEVICE_MONITOR_ETCO2 = "ETCO2";
	public static String DEVICE_MONITOR_SYS_BP = "SYS_BP";
	public static String DEVICE_MONITOR_DIA_BP = "DIA_BP";
	public static String DEVICE_MONITOR_MEAN_BP = "MEAN_BP";
	public static String DEVICE_MONITOR_NIBP_S = "NBP_S";
	public static String DEVICE_MONITOR_NIBP_D = "NBP_D";
	public static String DEVICE_MONITOR_NIBP_M = "NBP_M";
	public static String DEVICE_MONITOR_IBP_S = "IBP_S";
	public static String DEVICE_MONITOR_IBP_D = "IBP_D";
	public static String DEVICE_MONITOR_IBP_M = "IBP_M";
	public static String DEVICE_MONITOR_SO2_1= "o3rSO2_1";
	public static String DEVICE_MONITOR_SO2_2 = "o3rSO2_2";
	public static String DEVICE_MONITOR_PI = "PI";
	public static String DEVICE_MONITOR_PA = "PA";
	public static String DEVICE_MONITOR_PLS = "PLS";
	public static String DEVICE_MONITOR_RA = "RA";
	public static String DEVICE_MONITOR_CVP = "CVP";
	
	
	//Device Ventilator Parameters
	//New Mapping 
	public static String DEVICE_VENTILATOR_COMPLIANCE = "C";
	public static String DEVICE_VENTILATOR_RESISTANCE = "R";
	public static String DEVICE_VENTILATOR_TC = "Tc";
	public static String DEVICE_VENTILATOR_C20 = "C20";
	public static String DEVICE_VENTILATOR_TRIGGER = "Trigger";
	public static String DEVICE_VENTILATOR_RVR = "RVR";
	public static String DEVICE_VENTILATOR_TI = "TIspo";
	public static String DEVICE_VENTILATOR_TE = "TEspo";
	public static String DEVICE_VENTILATOR_MAP = "MAP";
	public static String DEVICE_VENTILATOR_PEEP = "PEEP";
	public static String DEVICE_VENTILATOR_PIP = "PIP";
	public static String DEVICE_VENTILATOR_DCO2 = "DcO2";
	public static String DEVICE_VENTILATOR_VTIM = "VTim";
	public static String DEVICE_VENTILATOR_VTHF = "VThf";
	public static String DEVICE_VENTILATOR_VT = "VT";
	public static String DEVICE_VENTILATOR_LEAK = "Leak";
	public static String DEVICE_VENTILATOR_SPONT = "Spont";
	public static String DEVICE_VENTILATOR_MV = "MV";
	public static String DEVICE_VENTILATOR_MVIM = "MVim";
	public static String DEVICE_VENTILATOR_RATE = "Rate";
	public static String DEVICE_VENTILATOR_SPO2 = "SpO2";
	public static String DEVICE_VENTILATOR_FIO2 = "FiO2";
	
	//extra parameters for sle5000
	

	public static String DEVICE_VENTILATOR_SET_CPAP = "setCpap";
	public static String DEVICE_VENTILATOR_SET_INSP_TIME = "setInspTime";
	public static String DEVICE_VENTILATOR_SET_HFO_DELTA = "setHfoDelta";
	public static String DEVICE_VENTILATOR_SET_HFO_MEAN = "setHfoMean";
	public static String DEVICE_VENTILATOR_SET_HFO_RATE = "setHfoRate";
	public static String DEVICE_VENTILATOR_VENTILATION_MODE = "VentMode";
	public static String DEVICE_VENTILATOR_SET_TERMINATION_SENSITIVITY = "setTerminationSensitivity";
	public static String DEVICE_VENTILATOR_SET_BREATH_TRIG_THRESHOLD = "setBreathTrigThreshold";
	public static String DEVICE_VENTILATOR_SET_WAVESHAPE = "setWaveshape";
	public static String DEVICE_VENTILATOR_SET_PATIENT_LEAK_ALARM = "setPatientLeakAlarm";
	public static String DEVICE_VENTILATOR_SET_APNOEA_ALARM = "setApnoeaAlarm";
	public static String DEVICE_VENTILATOR_SET_LOW_PRESSURE_ALARM = "setLowPressureAlarm";
	public static String DEVICE_VENTILATOR_SET_CYCLE_FAIL_ALARM = "setCyleFailAlarm";
	public static String DEVICE_VENTILATOR_SET_HIGH_PRESSURE_ALARM = "setHighPressureAlarm";
	public static String DEVICE_VENTILATOR_SET_LOW_TIDAL_VOL_ALARM = "setLowTidalVolAlarm";
	public static String DEVICE_VENTILATOR_SET_HIGH_TIDAL_VOL_ALARM = "setHighTidalVolAlarm";
	public static String DEVICE_VENTILATOR_SET_LOW_MINUTE_VOL_ALARM = "setLowMinuteVolAlarm";
	public static String DEVICE_VENTILATOR_SET_HIGH_MINUTE_VOL_ALARM = "setHighMinuteVolAlarm";
	public static String DEVICE_VENTILATOR_MEASURED_TOTAL_BPM = "mesTotalBpm";
	public static String DEVICE_VENTILATOR_MEASURED_CPAP = "mesCpap";
	public static String DEVICE_VENTILATOR_MEASURED_INSP_VOLUME = "mesInspVol";
	public static String DEVICE_VENTILATOR_MEASURED_EXP_VOLUME = "mesExpVol";
	public static String DEVICE_VENTILATOR_MEASURED_PIP = "mesPip";
	public static String DEVICE_VENTILATOR_MEASURED_FIO2 = "mesFio2";
	public static String DEVICE_VENTILATOR_MEASURED_HFO_DELTA_P = "mesHfoDeltaP";
	public static String DEVICE_VENTILATOR_MEASURED_HFO_MEAN = "mesHfoMean";
	public static String DEVICE_VENTILATOR_TRIGGER_COUNT = "TrigCount";
	public static String DEVICE_VENTILATOR_MEASURE_LEAK = "mesLeak";
	public static String DEVICE_VENTILATOR_MEASURED_RESISTANCE = "mesResistance";
	public static String DEVICE_VENTILATOR_MEASURED_COMPLIANCE = "mesCompliance";
	public static String DEVICE_VENTILATOR_MEASURED_C20 = "mesC20";
	public static String DEVICE_VENTILATOR_CURRENT_ALARM = "currentAlarm";

	
	//Old Mapping
//	public static String DEVICE_VENTILATOR_PIP = "PIP";
//	public static String DEVICE_VENTILATOR_PEEP = "PEEP";
//	public static String DEVICE_VENTILATOR_MAP = "MAP";
//	public static String DEVICE_VENTILATOR_COMPLIANCE = "COMPLIANCE";
//	public static String DEVICE_VENTILATOR_RESISTANCE = "RESISTANCE";
//	public static String DEVICE_VENTILATOR_FIO2 = "FIO2";
//	public static String DEVICE_VENTILATOR_TI = "TI";
	public static String DEVICE_VENTILATOR_FREQRATE = "FREQRATE";
	public static String DEVICE_VENTILATOR_TIDALVOL = "TIDALVOL";
	public static String DEVICE_VENTILATOR_MINVOL = "MINVOL";
	public static String DEVICE_VENTILATOR_FLOWPERMIN = "FLOWPERMIN";
	public static String DEVICE_VENTILATOR_NOPPM = "NOPPM";
	public static String DEVICE_VENTILATOR_MEANBP = "MEANBP";
	public static String DEVICE_VENTILATOR_OCCPRESSURE = "OCCPRESSURE";
	public static String DEVICE_VENTILATOR_PEAKBP = "PEAKBP";
	public static String DEVICE_VENTILATOR_PLATPRESSURE = "PLATPRESSURE";


	//Device Cerebral Oximeter Parameters
	public static String DEVICE_CEREBRAL_OXIMETER_RSO2_1 = "channel1_rso2";
	public static String DEVICE_CEREBRAL_OXIMETER_RSO2_2 = "channel2_rso2";
	public static String DEVICE_CEREBRAL_OXIMETER_RSO2_3 = "channel3_rso2";
	public static String DEVICE_CEREBRAL_OXIMETER_RSO2_4 = "channel4_rso2";
		
	
	/**
	 * Device_Incubator_detail mapped with GE Giraffe Omnibed  parameter Sample
	 * Ge Giraffe
	 */
	public static HashMap<String, String> GEGIRAFFEParamMapper = new HashMap<>();
	static {
		GEGIRAFFEParamMapper.put("AIR_TEMP", DeviceConstants.DEVICE_INCUBATOR_WARMER_Air_Temp);
		GEGIRAFFEParamMapper.put("DET", DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Air_Temp);
		GEGIRAFFEParamMapper.put("RELATIVE_HUMIDITY", DeviceConstants.DEVICE_INCUBATOR_WARMER_Humidity);
		GEGIRAFFEParamMapper.put("HUMIDITY_SET_POINT", DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Humidity);
		GEGIRAFFEParamMapper.put("LAST_SCALE_WEIGHT", DeviceConstants.DEVICE_INCUBATOR_WARMER_Weight);
		GEGIRAFFEParamMapper.put("TEMP_FROM_PROB1", DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp1);
		GEGIRAFFEParamMapper.put("TEMP_FROM_PROB2", DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp2);		
		GEGIRAFFEParamMapper.put("PATIENT_CONTROL_TEMP", DeviceConstants.DEVICE_INCUBATOR_WARMER_PATIENT_CONTROL_TEMP);
		GEGIRAFFEParamMapper.put("PATIENT_MODE", DeviceConstants.DEVICE_INCUBATOR_WARMER_PATIENT_MODE);
		GEGIRAFFEParamMapper.put("OPEN_BED_MODE", DeviceConstants.DEVICE_INCUBATOR_WARMER_OPEN_BED_MODE);
		GEGIRAFFEParamMapper.put("CLOSED_BED_MODE", DeviceConstants.DEVICE_INCUBATOR_WARMER_CLOSED_BED_MODE);
		GEGIRAFFEParamMapper.put("O2_SET_POINT", DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_SET_POINT);
		GEGIRAFFEParamMapper.put("O2_MEASUREMENT", DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_MEASURMENT);
		GEGIRAFFEParamMapper.put("SPO2_MEASUREMENT", DeviceConstants.DEVICE_INCUBATOR_WARMER_SPO2_MEASUREMENT);
		GEGIRAFFEParamMapper.put("PULSE_RATE_MEASUREMENT", DeviceConstants.DEVICE_INCUBATOR_WARMER_PULSE_RATE_MEASUREMENT);
	}
	
	/**
	 * Device_Incubator_detail mapped with GE Giraffe warmer  parameter Sample
	 * Ge Giraffe
	 */
	public static HashMap<String, String> GEGIRAFFEWARMERParamMapper = new HashMap<>();
	static {
		GEGIRAFFEWARMERParamMapper.put("BABY_TEMPERATURE", DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp1);
		GEGIRAFFEWARMERParamMapper.put("SET_TEMPERATURE", DeviceConstants.DEVICE_INCUBATOR_WARMER_WarmerSetTemperature);
		GEGIRAFFEWARMERParamMapper.put("CURRENT_HEATER_SETTING_WARMER", DeviceConstants.DEVICE_INCUBATOR_WARMER_heaterPower);
		GEGIRAFFEWARMERParamMapper.put("WARMER_MODE", DeviceConstants.DEVICE_INCUBATOR_WARMER_warmerMode);
		GEGIRAFFEWARMERParamMapper.put("LAST_SCALE_WEIGHT", DeviceConstants.DEVICE_INCUBATOR_WARMER_Weight);
		GEGIRAFFEWARMERParamMapper.put("SPO2_MEASUREMENT", DeviceConstants.DEVICE_INCUBATOR_WARMER_SPO2_MEASUREMENT);
		GEGIRAFFEWARMERParamMapper.put("PULSE_RATE_MEASUREMENT", DeviceConstants.DEVICE_INCUBATOR_WARMER_PULSE_RATE_MEASUREMENT);	
	}
	/**
	 * DEVICE_INCUBATOR_WARMER_detail mapped with Draeger ISOLETTE8000 parameter Sample
	 * Draeger ISOLETTE8000
	 */
	public static HashMap<String, String> DRAEGERISOLETTE8000ParamMapper = new HashMap<>();
	static {

		DRAEGERISOLETTE8000ParamMapper.put("INCUBATOR_MODE", DeviceConstants.DEVICE_INCUBATOR_WARMER_incubatorMode);
		DRAEGERISOLETTE8000ParamMapper.put("ALARM", DeviceConstants.DEVICE_INCUBATOR_WARMER_alarm);

		DRAEGERISOLETTE8000ParamMapper.put("AIR_TEMP", DeviceConstants.DEVICE_INCUBATOR_WARMER_Air_Temp);
		DRAEGERISOLETTE8000ParamMapper.put("SET_AIR_TEMP", DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Air_Temp);
		DRAEGERISOLETTE8000ParamMapper.put("BABY_TEMP", DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Air_Temp);
		
		DRAEGERISOLETTE8000ParamMapper.put("AMBIENT_TEMP", DeviceConstants.DEVICE_INCUBATOR_WARMER_ambientTemperature);
		
		DRAEGERISOLETTE8000ParamMapper.put("HUMIDITY", DeviceConstants.DEVICE_INCUBATOR_WARMER_Humidity);
		DRAEGERISOLETTE8000ParamMapper.put("HUMIDITY_SET_POINT", DeviceConstants.DEVICE_INCUBATOR_WARMER_Set_Humidity);
		
		DRAEGERISOLETTE8000ParamMapper.put("O2_MEASUREMENT", DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_MEASURMENT);
		DRAEGERISOLETTE8000ParamMapper.put("O2_SET_POINT", DeviceConstants.DEVICE_INCUBATOR_WARMER_O2_SET_POINT);
		
		DRAEGERISOLETTE8000ParamMapper.put("LAST_SCALE_WEIGHT", DeviceConstants.DEVICE_INCUBATOR_WARMER_Weight);
		DRAEGERISOLETTE8000ParamMapper.put("LiveCountFromWeight", DeviceConstants.DEVICE_INCUBATOR_WARMER_LiveCountFromWeight);
		DRAEGERISOLETTE8000ParamMapper.put("HEATER_POWER", DeviceConstants.DEVICE_INCUBATOR_WARMER_heaterPower);
		DRAEGERISOLETTE8000ParamMapper.put("TEMP_FROM_PROB1", DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp1);
		DRAEGERISOLETTE8000ParamMapper.put("TEMP_FROM_PROB1", DeviceConstants.DEVICE_INCUBATOR_WARMER_Skin_Temp2);
	}
	/**
	 * device_monitor_detail mapped with nellcor n560 Series Sample nellcor n560
	 * data { "SPO2", "BPM", "PA", "Status"}
	 */

	public static HashMap<String, String> nellcorParamMapper = new HashMap<>();
	static {
		nellcorParamMapper.put("PA", DeviceConstants.DEVICE_MONITOR_PA);
		nellcorParamMapper.put("BPM", DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		nellcorParamMapper.put("SPO2", DeviceConstants.DEVICE_MONITOR_SPO2);
	}

	public static HashMap<String, String> CarescapeACKGEB450ParamMapper = new HashMap<>();
	  static {
	    CarescapeACKGEB450ParamMapper.put("NBP-S", DeviceConstants.DEVICE_MONITOR_NIBP_S);
	    CarescapeACKGEB450ParamMapper.put("NBP-D", DeviceConstants.DEVICE_MONITOR_NIBP_D);
	    CarescapeACKGEB450ParamMapper.put("NBP-M", DeviceConstants.DEVICE_MONITOR_NIBP_M);

	    CarescapeACKGEB450ParamMapper.put("HR", DeviceConstants.DEVICE_MONITOR_HEART_RATE);
	    CarescapeACKGEB450ParamMapper.put("RR", DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
	    CarescapeACKGEB450ParamMapper.put("TEMP", DeviceConstants.DEVICE_MONITOR_TEMPERATURE);
	    CarescapeACKGEB450ParamMapper.put("SPO2-R", DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
	    CarescapeACKGEB450ParamMapper.put("SPO2-%", DeviceConstants.DEVICE_MONITOR_SPO2);
	  }
	
	/**
	 * Device_Monitor_detail mapped with Vismo parameter
	 * Sample Vismo data 
	 * {"RR":12,"PR":80,"TEMP":36.5,"SPO2":98,"HR":80}
	 */
	public static HashMap<String,String> vismoParamMapper = new HashMap<>();
	static{
		vismoParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		vismoParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_CO2_RESP_RATE);
		vismoParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		vismoParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
	}
	
	 //Device_monitor_detail mapped with Draeger Infinity Delta Parameter
	 public static HashMap<String,String> draegerInfinityDeltaParamMapper = new HashMap<>();
	 static{
		 draegerInfinityDeltaParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 draegerInfinityDeltaParamMapper.put("SYSNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_S);
		 draegerInfinityDeltaParamMapper.put("DIANIBP",DeviceConstants.DEVICE_MONITOR_NIBP_D);
		 draegerInfinityDeltaParamMapper.put("MEANNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_M);
		 draegerInfinityDeltaParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 draegerInfinityDeltaParamMapper.put("SYSIBP",DeviceConstants.DEVICE_MONITOR_IBP_S);
		 draegerInfinityDeltaParamMapper.put("DIAIBP",DeviceConstants.DEVICE_MONITOR_IBP_D);
		 draegerInfinityDeltaParamMapper.put("MEANIBP",DeviceConstants.DEVICE_MONITOR_IBP_M);
		 draegerInfinityDeltaParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 draegerInfinityDeltaParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
	 }
	 
	 //Device_monitor_detail mapped with Draeger Infinity Vista Parameter
	 public static HashMap<String,String> draegerInfinityVistaParamMapper = new HashMap<>();
	 static{
		 draegerInfinityVistaParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 draegerInfinityVistaParamMapper.put("SYSNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_S);
		 draegerInfinityVistaParamMapper.put("DIANIBP",DeviceConstants.DEVICE_MONITOR_NIBP_D);
		 draegerInfinityVistaParamMapper.put("MEANNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_M);
		 draegerInfinityVistaParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 draegerInfinityVistaParamMapper.put("SYSIBP",DeviceConstants.DEVICE_MONITOR_IBP_S);
		 draegerInfinityVistaParamMapper.put("DIAIBP",DeviceConstants.DEVICE_MONITOR_IBP_D);
		 draegerInfinityVistaParamMapper.put("MEANIBP",DeviceConstants.DEVICE_MONITOR_IBP_M);
		 draegerInfinityVistaParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 draegerInfinityVistaParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
	 }
	 
	 public static HashMap<String,String> draegerInfinityVistaXLParamMapper = new HashMap<>();
	 static{
		 draegerInfinityVistaXLParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 draegerInfinityVistaXLParamMapper.put("SYSNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_S);
		 draegerInfinityVistaXLParamMapper.put("DIANIBP",DeviceConstants.DEVICE_MONITOR_NIBP_D);
		 draegerInfinityVistaXLParamMapper.put("MEANNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_M);
		 draegerInfinityVistaXLParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 draegerInfinityVistaXLParamMapper.put("SYSIBP",DeviceConstants.DEVICE_MONITOR_IBP_S);
		 draegerInfinityVistaXLParamMapper.put("DIAIBP",DeviceConstants.DEVICE_MONITOR_IBP_D);
		 draegerInfinityVistaXLParamMapper.put("MEANIBP",DeviceConstants.DEVICE_MONITOR_IBP_M);
		 draegerInfinityVistaXLParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 draegerInfinityVistaXLParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		 draegerInfinityVistaXLParamMapper.put("RA",DeviceConstants.DEVICE_MONITOR_RA);
		 draegerInfinityVistaXLParamMapper.put("PLS",DeviceConstants.DEVICE_MONITOR_PLS);
		 draegerInfinityVistaXLParamMapper.put("CVP",DeviceConstants.DEVICE_MONITOR_CVP);
	 }

	 //Device_monitor_detail mapped with Philips SureSign Parameter	 
	 
	 public static HashMap<String,String> philipsSureSignParamMapper = new HashMap<>();
	 static{
		 philipsSureSignParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		 philipsSureSignParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 philipsSureSignParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 philipsSureSignParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 philipsSureSignParamMapper.put("NBPS",DeviceConstants.DEVICE_MONITOR_NIBP_S);
		 philipsSureSignParamMapper.put("NBPD",DeviceConstants.DEVICE_MONITOR_NIBP_D);
		 philipsSureSignParamMapper.put("NBPM",DeviceConstants.DEVICE_MONITOR_NIBP_M);
	 }
	 
	 /**
	 * Device_monitor_detail mapped with Philips Intellivue parameter
	 * Sample philips intellivue data
	 * 
	 */
	/*	
	 public static HashMap<String,String> philipsIntellivueParamMapper = new HashMap<>();
	 static{
		 philipsIntellivueParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 philipsIntellivueParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 philipsIntellivueParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		 philipsIntellivueParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 philipsIntellivueParamMapper.put("SYSNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_S);
		 philipsIntellivueParamMapper.put("DIANIBP",DeviceConstants.DEVICE_MONITOR_NIBP_D);
		 philipsIntellivueParamMapper.put("MEANNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_M);
	 }
	 */
	 public static HashMap<String,String> philipsIntellivueParamMapper = new HashMap<>();
	 static{
		 philipsIntellivueParamMapper.put("MDC_PULS_OXIM_PULS_RATE",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		 philipsIntellivueParamMapper.put("MDC_PULS_OXIM_SAT_O2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 philipsIntellivueParamMapper.put("MDC_ECG_HEART_RATE",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 philipsIntellivueParamMapper.put("MDC_TTHOR_RESP_RATE",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 philipsIntellivueParamMapper.put("MDC_RESP_RATE",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 philipsIntellivueParamMapper.put("MDC_CO2_RESP_RATE",DeviceConstants.DEVICE_MONITOR_CO2_RESP_RATE);
		 philipsIntellivueParamMapper.put("MDC_PRESS_BLD_ART_ABP_SYS",DeviceConstants.DEVICE_MONITOR_SYS_BP);
		 philipsIntellivueParamMapper.put("MDC_PRESS_CUFF_SYS",DeviceConstants.DEVICE_MONITOR_SYS_BP);
		 philipsIntellivueParamMapper.put("MDC_PRESS_BLD_ART_ABP_DIA",DeviceConstants.DEVICE_MONITOR_DIA_BP);
		 philipsIntellivueParamMapper.put("MDC_PRESS_BLD_NONINV_MEAN",DeviceConstants.DEVICE_MONITOR_MEAN_BP);
		 philipsIntellivueParamMapper.put("MDC_PRESS_CUFF_DIA",DeviceConstants.DEVICE_MONITOR_DIA_BP);
//		 philipsIntellivueParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_MEAN_BP);
//		 philipsIntellivueParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_NBP_S);
//		 philipsIntellivueParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_NBP_D);
	 }
	 
	 public static HashMap<String,String> philipsIntellivueMx800ParamMapper = new HashMap<>();
	 static{
		 philipsIntellivueMx800ParamMapper.put("SPO2",DeviceConstants.DEVICE_MONITOR_SPO2);
		 philipsIntellivueMx800ParamMapper.put("HR",DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 philipsIntellivueMx800ParamMapper.put("PR",DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		 philipsIntellivueMx800ParamMapper.put("RR",DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 philipsIntellivueMx800ParamMapper.put("SYSNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_S);
		 philipsIntellivueMx800ParamMapper.put("DIANIBP",DeviceConstants.DEVICE_MONITOR_NIBP_D);
		 philipsIntellivueMx800ParamMapper.put("MEANNIBP",DeviceConstants.DEVICE_MONITOR_NIBP_M);
	 }

	 
	 /**
	  * device_monitor_detail mapped with Masimo Radical Series
	  * Sample Masimo Radical data
	  * { "SPO2", "PM", "PI", "DESAT", "IDELTA", "PVI", "ALARM","ACSALARM" }
	  */
	 public static HashMap<String,String> masimoRadicalParamMapper = new HashMap<>();
	 static{
		 masimoRadicalParamMapper.put("SPO2", DeviceConstants.DEVICE_MONITOR_SPO2);
		 masimoRadicalParamMapper.put("BPM", DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 masimoRadicalParamMapper.put("o3rSO2_1", DeviceConstants.DEVICE_MONITOR_SO2_1);
		 masimoRadicalParamMapper.put("o3rSO2_2", DeviceConstants.DEVICE_MONITOR_SO2_2);
		 masimoRadicalParamMapper.put("PI", DeviceConstants.DEVICE_MONITOR_PI);
	 }
	 
	 /**
	  * device_monitor_detail mapped with Masimo Radical Series
	  * Sample Masimo Radical data
	  * { "SPO2", "PM", "PI", "DESAT", "IDELTA", "PVI", "ALARM","ACSALARM" }
	  */
	 public static HashMap<String,String> medtronicInvos5100CParamMapper = new HashMap<>();
	 static{
		 medtronicInvos5100CParamMapper.put("channel1_rso2", DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_1);
		 medtronicInvos5100CParamMapper.put("channel2_rso2", DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_2);
		 medtronicInvos5100CParamMapper.put("channel3_rso2", DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_3);
		 medtronicInvos5100CParamMapper.put("channel4_rso2", DeviceConstants.DEVICE_CEREBRAL_OXIMETER_RSO2_4);
	 }
	 
	 /**
	  * device_monitor_detail mapped with SLE5000
	  * Sample SLE5000 Radical data
	  * { "PIP", "PEEP", "MAP", "FREQRATE", "TIDALVOL", "MINVOL", "TI","FIO2","FLOWPERMIN","NOPPM","MEANBP","OCCPRESSURE","PEAKBP","PLATPRESSURE" }
	  */
	 public static HashMap<String,String> sle5000ParamMapper = new HashMap<>();
	 static{
		 
		 sle5000ParamMapper.put("Set PIP (mbar)", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 sle5000ParamMapper.put("Measured PEEP (mbar)", DeviceConstants.DEVICE_VENTILATOR_PEEP);
		 sle5000ParamMapper.put("MAP", DeviceConstants.DEVICE_VENTILATOR_MAP);
		 sle5000ParamMapper.put("FREQRATE", DeviceConstants.DEVICE_VENTILATOR_FREQRATE);
		 sle5000ParamMapper.put("Set Tidal volume (0.2 ml)", DeviceConstants.DEVICE_VENTILATOR_TIDALVOL);
		 sle5000ParamMapper.put("Measured Minute Vol (ml)", DeviceConstants.DEVICE_VENTILATOR_MINVOL);
		 sle5000ParamMapper.put("Measured Insp time (0.01 sec)", DeviceConstants.DEVICE_VENTILATOR_TI);
		 sle5000ParamMapper.put("Set FIO2", DeviceConstants.DEVICE_VENTILATOR_FIO2);
		 sle5000ParamMapper.put("FLOWPERMIN", DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN);
		 sle5000ParamMapper.put("NOPPM", DeviceConstants.DEVICE_VENTILATOR_NOPPM);
		 sle5000ParamMapper.put("MEANBP", DeviceConstants.DEVICE_VENTILATOR_MEANBP);
		 sle5000ParamMapper.put("OCCPRESSURE", DeviceConstants.DEVICE_VENTILATOR_OCCPRESSURE);
		 sle5000ParamMapper.put("PEAKBP", DeviceConstants.DEVICE_VENTILATOR_PEAKBP);
		 sle5000ParamMapper.put("PLATPRESSURE", DeviceConstants.DEVICE_VENTILATOR_PLATPRESSURE);
		 sle5000ParamMapper.put("PEAKBP", DeviceConstants.DEVICE_VENTILATOR_PEAKBP);
		 
		 sle5000ParamMapper.put("Set CPAP (mbar)", DeviceConstants.DEVICE_VENTILATOR_SET_CPAP);
		 sle5000ParamMapper.put("Set Insp Time (0.01 sec)", DeviceConstants.DEVICE_VENTILATOR_SET_INSP_TIME);
		 sle5000ParamMapper.put("Set HFO delta (mbar)", DeviceConstants.DEVICE_VENTILATOR_SET_HFO_DELTA);
		 sle5000ParamMapper.put("Set HFO mean (mbar)", DeviceConstants.DEVICE_VENTILATOR_SET_HFO_MEAN);
		 sle5000ParamMapper.put("Set HFO rate (0.1 Hz)", DeviceConstants.DEVICE_VENTILATOR_SET_HFO_RATE);
		 sle5000ParamMapper.put("Ventilation mode", DeviceConstants.DEVICE_VENTILATOR_VENTILATION_MODE);
		 sle5000ParamMapper.put("Set termination sensitivity", DeviceConstants.DEVICE_VENTILATOR_SET_TERMINATION_SENSITIVITY);
		 sle5000ParamMapper.put("Set breath trig threshold (lpm)", DeviceConstants.DEVICE_VENTILATOR_SET_BREATH_TRIG_THRESHOLD);
		 sle5000ParamMapper.put("Set waveshape", DeviceConstants.DEVICE_VENTILATOR_SET_WAVESHAPE);
		 sle5000ParamMapper.put("Set patient leak alarm", DeviceConstants.DEVICE_VENTILATOR_SET_PATIENT_LEAK_ALARM);
		 sle5000ParamMapper.put("Set apnoea alarm (seconds)", DeviceConstants.DEVICE_VENTILATOR_SET_APNOEA_ALARM);
		 sle5000ParamMapper.put("Set low pressure alarm (0.1 mbar)", DeviceConstants.DEVICE_VENTILATOR_SET_LOW_PRESSURE_ALARM);
		 sle5000ParamMapper.put("Set cycle fail alarm (0.1 mbar)", DeviceConstants.DEVICE_VENTILATOR_SET_CYCLE_FAIL_ALARM);
		 sle5000ParamMapper.put("Set high pressure alarm (0.1 mbar)", DeviceConstants.DEVICE_VENTILATOR_SET_HIGH_PRESSURE_ALARM);
		 sle5000ParamMapper.put("Set low tidal vol alarm (0.1 ml)", DeviceConstants.DEVICE_VENTILATOR_SET_LOW_TIDAL_VOL_ALARM);
		 sle5000ParamMapper.put("Set high tidal vol alarm (0.1 ml)", DeviceConstants.DEVICE_VENTILATOR_SET_HIGH_TIDAL_VOL_ALARM);
		 sle5000ParamMapper.put("Set low minute vol alarm (ml)", DeviceConstants.DEVICE_VENTILATOR_SET_LOW_MINUTE_VOL_ALARM);
		 sle5000ParamMapper.put("Set high minute vol alarm (ml)", DeviceConstants.DEVICE_VENTILATOR_SET_HIGH_MINUTE_VOL_ALARM);
		 sle5000ParamMapper.put("Measured total BPM (breaths/min)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_TOTAL_BPM);
		 sle5000ParamMapper.put("Measured CPAP (mbar)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_CPAP);
		 sle5000ParamMapper.put("Measured Insp Volume (0.1ml)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_INSP_VOLUME);
		 sle5000ParamMapper.put("Measured exp volume (0.1ml)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_EXP_VOLUME);
		 sle5000ParamMapper.put("Measured PIP (mbar)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_PIP);
		 sle5000ParamMapper.put("Measured FiO2 ", DeviceConstants.DEVICE_VENTILATOR_MEASURED_FIO2);
		 sle5000ParamMapper.put("Measured HFO delta P (mbar)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_HFO_DELTA_P);
		 sle5000ParamMapper.put("Measured HFO mean (mbar)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_HFO_MEAN);
		 sle5000ParamMapper.put("Trigger count (breaths/min)", DeviceConstants.DEVICE_VENTILATOR_TRIGGER_COUNT);
		 sle5000ParamMapper.put("Measure Leak", DeviceConstants.DEVICE_VENTILATOR_MEASURE_LEAK);
		 sle5000ParamMapper.put("Measured resistance (0.1 mbar.sec/l)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_RESISTANCE);
		 sle5000ParamMapper.put("Measured compliance (0.1 ml/mbar)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_COMPLIANCE);
		 sle5000ParamMapper.put("Measured C20 (0.1)", DeviceConstants.DEVICE_VENTILATOR_MEASURED_C20);
		 sle5000ParamMapper.put("Current alarm", DeviceConstants.DEVICE_VENTILATOR_CURRENT_ALARM);
		 
	 }
	 
	 /**
	  * device_monitor_detail mapped with SLE5000
	  * Sample SLE5000 data
	  * {"Compliance\tLPerBar":"","SpontaneousRespiratoryRate":"4","OcclusionPressure":"6.3B7","PeakBreathingPressure mbar":"26",
	  * "PEEPBreathingPressure mbar":"5","MinimalAirwayPressure":"5","MeanBreathingPressure mbar":"10","InspO2 pct":"39"}
	  */
	 public static HashMap<String,String> draegerEvitaParamMapper = new HashMap<>();
	 static{
		 
		 draegerEvitaParamMapper.put("PIP", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 draegerEvitaParamMapper.put("PEEPBreathingPressure mbar", DeviceConstants.DEVICE_VENTILATOR_PEEP);
		 draegerEvitaParamMapper.put("MinimalAirwayPressure", DeviceConstants.DEVICE_VENTILATOR_MAP);
		 draegerEvitaParamMapper.put("FREQRATE", DeviceConstants.DEVICE_VENTILATOR_FREQRATE);
		 draegerEvitaParamMapper.put("TIDALVOL", DeviceConstants.DEVICE_VENTILATOR_TIDALVOL);
		 draegerEvitaParamMapper.put("MINVOL", DeviceConstants.DEVICE_VENTILATOR_MINVOL);
		 draegerEvitaParamMapper.put("TI", DeviceConstants.DEVICE_VENTILATOR_TI);
		 draegerEvitaParamMapper.put("FIO2", DeviceConstants.DEVICE_VENTILATOR_FIO2);
		 draegerEvitaParamMapper.put("FLOWPERMIN", DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN);
		 draegerEvitaParamMapper.put("NOPPM", DeviceConstants.DEVICE_VENTILATOR_NOPPM);
		 draegerEvitaParamMapper.put("MeanBreathingPressure mbar", DeviceConstants.DEVICE_VENTILATOR_MEANBP);
		 draegerEvitaParamMapper.put("OcclusionPressure", DeviceConstants.DEVICE_VENTILATOR_OCCPRESSURE);
		 draegerEvitaParamMapper.put("PeakBreathingPressure mbar", DeviceConstants.DEVICE_VENTILATOR_PEAKBP);
		 draegerEvitaParamMapper.put("PLATPRESSURE", DeviceConstants.DEVICE_VENTILATOR_PLATPRESSURE);
		 
	 }
	
	 public static HashMap<String,String> geb40ParamMapper = new HashMap<>();
	 static{
		 geb40ParamMapper.put("MDC_PULS_OXIM_PULS_RATE", DeviceConstants.DEVICE_MONITOR_PULSE_RATE);
		 geb40ParamMapper.put("MDC_PULS_OXIM_SAT_O2", DeviceConstants.DEVICE_MONITOR_SPO2);
		 geb40ParamMapper.put("MDC_RESP_RATE", DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 geb40ParamMapper.put("MDC_ECG_HEART_RATE", DeviceConstants.DEVICE_MONITOR_HEART_RATE);
		 geb40ParamMapper.put("MDC_PRESS_BLD_NONINV_SYS",DeviceConstants.DEVICE_MONITOR_SYS_BP);
		 geb40ParamMapper.put("MDC_PRESS_BLD_NONINV_DIA",DeviceConstants.DEVICE_MONITOR_DIA_BP);
		 geb40ParamMapper.put("MDC_PRESS_BLD_NONINV_MEAN",DeviceConstants.DEVICE_MONITOR_MEAN_BP);
	 }
	 public static HashMap<String,String> draegerVista120ParamMapper = new HashMap<>();
	 static{
		 draegerVista120ParamMapper.put("SPO2", DeviceConstants.DEVICE_MONITOR_SPO2);
		 draegerVista120ParamMapper.put("RR", DeviceConstants.DEVICE_MONITOR_ECG_RESP_RATE);
		 draegerVista120ParamMapper.put("HR", DeviceConstants.DEVICE_MONITOR_HEART_RATE);
	 }
	 /**
	  * device_ventilator_detail mapped with babylog 8000
	  * Sample babylog 8000 parameter
	  * {"PeakBreathingPressure mbar": "15","PEEPBreathingPressure mbar": "6","MeanBreathingPressure mbar": "7","InspO2 pct": "39"}
	  */
	 public static HashMap<String,String> babylog8000parameter = new HashMap<>();
	 static{

		 //New Mapping
		 babylog8000parameter.put("C", DeviceConstants.DEVICE_VENTILATOR_COMPLIANCE);
		 babylog8000parameter.put("R", DeviceConstants.DEVICE_VENTILATOR_RESISTANCE);
		 babylog8000parameter.put("Tc", DeviceConstants.DEVICE_VENTILATOR_TC);
		 babylog8000parameter.put("C20", DeviceConstants.DEVICE_VENTILATOR_C20);
		 babylog8000parameter.put("Trigger", DeviceConstants.DEVICE_VENTILATOR_TRIGGER);
		 babylog8000parameter.put("RVR", DeviceConstants.DEVICE_VENTILATOR_RVR);		 
		 babylog8000parameter.put("TIspo", DeviceConstants.DEVICE_VENTILATOR_TI);
		 babylog8000parameter.put("TEspo", DeviceConstants.DEVICE_VENTILATOR_TE);
		 babylog8000parameter.put("MAP", DeviceConstants.DEVICE_VENTILATOR_MAP);
		 babylog8000parameter.put("PEEP", DeviceConstants.DEVICE_VENTILATOR_PEEP);
		 babylog8000parameter.put("PIP", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 babylog8000parameter.put("DcO2", DeviceConstants.DEVICE_VENTILATOR_DCO2);
		 babylog8000parameter.put("VTim", DeviceConstants.DEVICE_VENTILATOR_VTIM);
		 babylog8000parameter.put("VThf", DeviceConstants.DEVICE_VENTILATOR_VTHF);
		 babylog8000parameter.put("VT", DeviceConstants.DEVICE_VENTILATOR_VT);
		 babylog8000parameter.put("Leak", DeviceConstants.DEVICE_VENTILATOR_LEAK);
		 babylog8000parameter.put("Spont", DeviceConstants.DEVICE_VENTILATOR_SPONT);
		 babylog8000parameter.put("MV", DeviceConstants.DEVICE_VENTILATOR_MV);
		 babylog8000parameter.put("MVim", DeviceConstants.DEVICE_VENTILATOR_MVIM);
		 babylog8000parameter.put("Rate", DeviceConstants.DEVICE_VENTILATOR_RATE);
		 babylog8000parameter.put("SpO2", DeviceConstants.DEVICE_VENTILATOR_SPO2);
		 babylog8000parameter.put("FiO2", DeviceConstants.DEVICE_VENTILATOR_FIO2);
		 
		 //Old Mapping
//		 babylog8000parameter.put("PeakBreathingPressure mbar", DeviceConstants.DEVICE_VENTILATOR_PIP);
//		 babylog8000parameter.put("PEEPBreathingPressure mbar", DeviceConstants.DEVICE_VENTILATOR_PEEP);
//		 babylog8000parameter.put("MeanBreathingPressure mbar", DeviceConstants.DEVICE_VENTILATOR_MAP);
//		 babylog8000parameter.put("FREQRATE", DeviceConstants.DEVICE_VENTILATOR_FREQRATE);
//		 babylog8000parameter.put("Set Tidal volume (0.2 ml)", DeviceConstants.DEVICE_VENTILATOR_TIDALVOL);
//		 babylog8000parameter.put("Measured Minute Vol (ml)", DeviceConstants.DEVICE_VENTILATOR_MINVOL);
//		 babylog8000parameter.put("Measured Insp time (0.01 sec)", DeviceConstants.DEVICE_VENTILATOR_TI);
//		 babylog8000parameter.put("InspO2 pct", DeviceConstants.DEVICE_VENTILATOR_FIO2);
//		 babylog8000parameter.put("FLOWPERMIN", DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN);
//		 babylog8000parameter.put("NOPPM", DeviceConstants.DEVICE_VENTILATOR_NOPPM);
//		 babylog8000parameter.put("MEANBP", DeviceConstants.DEVICE_VENTILATOR_MEANBP);
//		 babylog8000parameter.put("OCCPRESSURE", DeviceConstants.DEVICE_VENTILATOR_OCCPRESSURE);
//		 babylog8000parameter.put("PEAKBP", DeviceConstants.DEVICE_VENTILATOR_PEAKBP);
//		 babylog8000parameter.put("PLATPRESSURE", DeviceConstants.DEVICE_VENTILATOR_PLATPRESSURE);
	 }

	 /**
	  * device_ventilator_detail mapped with babylog VN500
	  * Sample babylog VN500 parameter
	  * {"PeakBreathingPressure mbar": "15","PEEPBreathingPressure mbar": "6","MeanBreathingPressure mbar": "7","InspO2 pct": "39"}
	  */
	 public static HashMap<String,String> draegerVN500parameter = new HashMap<>();
	 static{
		 draegerVN500parameter.put("C", DeviceConstants.DEVICE_VENTILATOR_COMPLIANCE);
		 draegerVN500parameter.put("R", DeviceConstants.DEVICE_VENTILATOR_RESISTANCE);
		 draegerVN500parameter.put("Tc", DeviceConstants.DEVICE_VENTILATOR_TC);
		 draegerVN500parameter.put("C20", DeviceConstants.DEVICE_VENTILATOR_C20);
		 draegerVN500parameter.put("Trigger", DeviceConstants.DEVICE_VENTILATOR_TRIGGER);
		 draegerVN500parameter.put("RVR", DeviceConstants.DEVICE_VENTILATOR_RVR);		 
		 draegerVN500parameter.put("TIspo", DeviceConstants.DEVICE_VENTILATOR_TI);
		 draegerVN500parameter.put("TEspo", DeviceConstants.DEVICE_VENTILATOR_TE);
		 draegerVN500parameter.put("MAP", DeviceConstants.DEVICE_VENTILATOR_MAP);
		 draegerVN500parameter.put("PEEP", DeviceConstants.DEVICE_VENTILATOR_PEEP);
		 draegerVN500parameter.put("PIP", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 draegerVN500parameter.put("DcO2", DeviceConstants.DEVICE_VENTILATOR_DCO2);
		 draegerVN500parameter.put("VTim", DeviceConstants.DEVICE_VENTILATOR_VTIM);
		 draegerVN500parameter.put("VThf", DeviceConstants.DEVICE_VENTILATOR_VTHF);
		 draegerVN500parameter.put("VT", DeviceConstants.DEVICE_VENTILATOR_VT);
		 draegerVN500parameter.put("Leak", DeviceConstants.DEVICE_VENTILATOR_LEAK);
		 draegerVN500parameter.put("Spont", DeviceConstants.DEVICE_VENTILATOR_SPONT);
		 draegerVN500parameter.put("MV", DeviceConstants.DEVICE_VENTILATOR_MV);
		 draegerVN500parameter.put("MVim", DeviceConstants.DEVICE_VENTILATOR_MVIM);
		 draegerVN500parameter.put("Rate", DeviceConstants.DEVICE_VENTILATOR_RATE);
		 draegerVN500parameter.put("SpO2", DeviceConstants.DEVICE_VENTILATOR_SPO2);
		 draegerVN500parameter.put("FiO2", DeviceConstants.DEVICE_VENTILATOR_FIO2);
	 }
	 
	 /**
	  * device_ventilator_detail mapped with stephan sophie
	  * Sample sophie parameter
//	  * {"O2":"25.0","PEEP":"4.7","Flow Offset":"-0.010238","Mode":"SIMV","Posc":"15.7","Tank Temp.":"27.8",
	  	* "Pmbar":"4.6","Pmean":"6.6","Flow":"-0.070","Pmax":"14.9","Frequenz":"40","VT":"1.500","Patient Temp.":"27.0"}
	  */
	 public static HashMap<String,String> sophieparameter = new HashMap<>();
	 static{
//		 sophieparameter.put("P", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 sophieparameter.put("Pmax", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 sophieparameter.put("O2", DeviceConstants.DEVICE_VENTILATOR_FIO2);
		 sophieparameter.put("PEEP", DeviceConstants.DEVICE_VENTILATOR_PEEP);
		 sophieparameter.put("Frequenz", DeviceConstants.DEVICE_VENTILATOR_FREQRATE);
		 sophieparameter.put("VT", DeviceConstants.DEVICE_VENTILATOR_TIDALVOL);
//		 sophieparameter.put("Mode", DeviceConstants.DEVICE_VENTILATOR_PIP);
//		 sophieparameter.put("Posc", DeviceConstants.DEVICE_VENTILATOR_PIP);
//		 sophieparameter.put("Tank Temp.", DeviceConstants.DEVICE_VENTILATOR_PIP);
//		 sophieparameter.put("Pmbar", DeviceConstants.DEVICE_VENTILATOR_PIP);
		 sophieparameter.put("Pmean", DeviceConstants.DEVICE_VENTILATOR_MAP);  // Mean Arterial Pressure
		 sophieparameter.put("Flow", DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN);
//		 sophieparameter.put("Patient Temp.", DeviceConstants.DEVICE_VENTILATOR_FLOWPERMIN);

	 }
}
