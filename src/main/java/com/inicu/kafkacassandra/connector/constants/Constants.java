/**
 * 
 */
package com.inicu.kafkacassandra.connector.constants;

import com.inicu.deviceadapter.library.datamodel.InicuConsumerModel;

/**
 * @author sanoob
 *
 */
public class Constants {

	/*
	 * final application specific constants
	 */
	public static final String DEVICE_NAME_JSON_KEY = "device";
	public static final String DEVICE_ID_JSON_KEY = "device_id";
	public static final String DEVICE_DATA_TYPE_JSON_KEY = "data_type";
	public static final String DEVICE_DATA_JSON_KEY = "data";
	public static final String DEVICE_TIME_JSON_KEY = "timestamp";
	public static final String DEVICE_ARCH_JSON_KEY = "CPU_arcitecture";
	public static final String DEVICE_OS_JSON_KEY = "device_os ";
	public static final int MAX_WHITESPACES = 50;

	/*
	 * cassandra project specific constants in future can be populated from
	 * property file
	 * 
	 */
	public static String CASSANDRA_HOST = "localhost";
	public static int CASSANDRA_PORT = 9042;
	public static String CASSANDRA_KEYSPACE = "inicu";

	/*
	 * dynamic constants in future can be populated from property file
	 */
//	public static String KAFKA_HOST = "localhost";//"119.81.121.189";
	public static String KAFKA_HOST = "119.81.121.188";
	public static String KAFKA_PORT = "9092";
	public static String KAFKA_GROUP_ID = "inicu_device";
//	public static String KAFKA_HW_LAYER_TOPIC = "inicudevice";
	public static String KAFKA_HW_LAYER_TOPIC = "apollo_cradle";
	public static String KAFKA_SOFT_LAYER_TOPIC = "inicuintellivue";

	/*
	 * kafka consumer models
	 */
	public static final InicuConsumerModel HW_LAYER_CONSUMER = new InicuConsumerModel(KAFKA_HOST, KAFKA_PORT,
			KAFKA_HW_LAYER_TOPIC, KAFKA_GROUP_ID);
	public static final InicuConsumerModel SOFT_LAYER_CONSUMER = new InicuConsumerModel(KAFKA_HOST, KAFKA_PORT,
			KAFKA_SOFT_LAYER_TOPIC, KAFKA_GROUP_ID);

	/*
	 * JSON keys
	 * 
	 */
	public static final String SOFT_JSON_PAT_ID_KEY = "patientId";
	public static final String SOFT_JSON_EVENT_TIME_KEY = "eventTime";
	public static final String SOFT_JSON_PAT_NAME_KEY = "patientName";
	public static final String SOFT_JSON_METRIC_ID_KEY = "metricId";
	public static final String SOFT_JSON_VEND_MET_ID_KEY = "vendorMetricId";
	public static final String SOFT_JSON_INST_ID_KEY = "instanceId";
	public static final String SOFT_JSON_UNIT_ID_KEY = "unitId";
	public static final String SOFT_JSON_DEV_TIM_SEC_KEY = "deviceTimeSec";
	public static final String SOFT_JSON_DEV_TIM_NANO_KEY = "deviceTimeNano";
	public static final String SOFT_JSON_PRES_TIME_SEC_KEY = "presTimeSec";
	public static final String SOFT_JSON_PRES_TIME_NANO_KEY = "presTimeNano";
	public static final String SOFT_JSON_VALUE_KEY = "value";
	public static final String SOFT_JSON_DOMAIN_ID_KEY = "domainId";
	
	public static final String JSON_DATA_KEY_SRR = "SpontaneousRespiratoryRate";
	public static final String JSON_DATA_KEY_PeakBP = "PeakBreathingPressure mbar";
	public static final String JSON_DATA_KEY_PeepBP = "PEEPBreathingPressure mbar";
	public static final String JSON_DATA_KEY_OCCP = "OcclusionPressure";
	public static final String JSON_DATA_KEY_MinAP = "MinimalAirwayPressure";
	public static final String JSON_DATA_KEY_MeanBP = "MeanBreathingPressure mbar";
	public static final String JSON_DATA_KEY_INS = "InspO2 pct";
	public static final String JSON_DATA_KEY_PlatPRESS = "PlateauPressure mbar";

//	 JSONObject dataObj = new JSONObject("{\"device_id\":
//	 \"inicuIntelEdison\",\"data_type\": \"data\",\"device\": "
//	 + "\"intelEdison\",\"timestamp\": \"2017-01-20 09:52:09\","
//	 + "\"data\": {\"SpontaneousRespiratoryRate\": \"0\",\"RespiratoryRate\":
//	 \"31\","
//	 + "\"IntrinsicPEEPBreathingPressure\": \"9.972\",\"PeakBreathingPressure
//	 mbar\": "
//	 + "\"20\",\"PEEPBreathingPressure mbar\":
//	 \"5\",\"MinimalAirwayPressure\": \"5\","
//	 + "\"MeanBreathingPressure mbar\": \"9\",\"InspO2 pct\": \"99\"}}");

}
