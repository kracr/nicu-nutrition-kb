package com.inicu.postgres.utility;

import org.springframework.context.ConfigurableApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;

public class BasicConstants {

	public static Properties props;
	public static final String PROPS_FILE = "inicuapplication.properties";
	protected static final String MAIL_PASSWORD = "Oxyent@123#Inicucloud";
	protected static final String MAIL_ID_TO_SEND = "inicusupport@oxyent.com";
	// public static final String MAIL_ID_RECIEVER = "inicutechsupport@oxyent.com";

	// @sourabh updated on 23rd august on call with ruchi
	public static final String MAIL_ID_RECIEVER = "inicutechsupport@inicucloud.com";
	public static final String PAGE_NURSING_NOTES = "Nursing Notes";
	public static final String PAGE_DOCTOR_NOTES = "Doctor_Notes";
	public static final String SELECT = "SELECT";
	public static final String COMMA = ",";
	public static String MESSAGE_SUCCESS = "success";

	public static String MESSAGE_FAILURE = "failure";
	public static String SCHEMA_NAME = "apollo";
	public static String BRANCH_NAME = "Moti Nagar, Delhi";
	public static String PROPS_BRANCH_NAME = "branch_name";

	public static String WORKING_DIR = System.getProperty("user.dir");
	public static String IMG_PATH = "/inicu_images/";
	public static String IMG_WEBAPP_PATH = "/target/classes/static/_images/";
	public static String AES_KEY = "nicu#$@&nicu@$^&";

	public static String COMPANY_ID = "iNICU";
	public static final String LOGGED_USER_ID = "";


	// Wowza Related Credentials
	public static String WOWZA_IP= "";
	public static int WOWZA_PORT = 8087;
	public static String WOWZA_SCHEME= "http";
	public static String WOWZA_USERNAME ="";
	public  static String WOWZA_PASSWORD = "";

	public static String PROP_WOWZA_IP = "wowza_ip";
	public static String PROP_WOWZA_PORT = "wowza_port";
	public static String PROP_WOWZA_USERNAME = "wowza_username";
	public static String PROP_WOWZA_PASSWORD = "wowza_password";

	public static String PROP_RECORDING_ENABLED = "recording_enabled";
	public static Boolean RECORDING_ENABLED = false;
	// Twilio Related Constants
	public static String PROP_TWILIO_SID = "Twilio_SID";
	public static String PROP_TWILIO_API_KEY = "Twilio_API_KEY";
	public static String PROP_TWILIO_AUTH_KEY = "Twilio_Auth_Key";
	public static String PROP_TWILIO_API_SECRET= "Twilio_API_SECRET";

	public static String PROP_DEVICE_EXCEPTION_ENABLED = "enable_device_exception_thread";

	public static String DEVICE_EXCEPTION = "device_exception";
	public static String BABY_ADMIT = "new_baby_registration";

	public static String TWILIO_SID = "";
	public static String TWILIO_API_KEY = "";
	public static String TWILIO_AUTH_KEY = "";
	public static String TWILIO_SECRET_KEY= "";

	public static String ICHR_APP_LINK = "https://tinyurl.com/y9dzmy8p";

	public static String PUSH_NOTIFICATION = "";
	public static String ADMIT_PATIENT = "";
	public static String ICHR_SCHEMA = "";

	public static boolean ICHR_SETTINGS_ENABLED = false;
	public static boolean REMOTE_VIEW_ENABLED = false;
	public static boolean WEBSOCKET_ENABLE= false;

	public static String PROP_ICHR_SCHEMA = "schema_name";
	public static String PROP_PUSH_NOTIFICATION = "push_notfication";
	public static String PROP_ADMIT_PATIENT = "admit_patient";
	public static String PROP_ICHR_SETTINGS_ENABLED= "ichr_settings_enabled";
	public static String PROP_REMOTE_VIEW_ENABLED= "remote_view_enabled";
	public static String PROP_WEBSOCKET_ENABLE= "websocket_enable";
	
	public static String ESPHGHAN_HOUR = "esphghan_hour";
	public static String NEOFAX_HOUR = "neofax_hour";

	public static String ESPHGHAN_MIN = "esphghan_min";
	public static String NEOFAX_MIN = "neofax_min";

	// For SCI/DEMO/LIVE SERVERS
	public static int OFFSET_VALUE= 19800000;

	// FOR LOCAL TESTING
//	public static int OFFSET_VALUE= 0;

	// constants for entering logs
	public static String DELETE = "delete";
	public static String INSERT = "insert";
	public static String UPDATE = "update";
	public static String DASHBOARD = "dashboard";
	public static String SA_JAUNDICE = "sa_jaundice";
	public static String SA_METABOLIC = "sa_metabolic";
	public static String SA_HYPOGLYCEMIA = "sa_hypoglycemia";
	public static String SA_MISC = "sa_miscellaneous";
	public static String SA_RESP = "sa_respsystem";
	public static String SA_CNS = "sa_cns";
	public static String NEUROMUSCULARDISORDER="neuromuscular_disorders";
	public static String HYDROCEPHALUS="hydrocephalus";
	public static String SA_SEPSIS = "sa_sepsis";
	public static String SA_CARDIAC = "sa_cardiac";
	public static String SA_FEED = "sa_feeding_growth";
	public static String SA_RENAL = "sa_renal_failure";
	public static String SA_FEED_TOLERANCE = "sa_feed_intolerance";
	public static String SA_FOLLOWUP = "sa_followup";
	public static String ADD_USER = "add_user";
	public static String ADD_DRUG = "add_drugs";
	public static String Doctor_Note_Template = "doctor_note_template";
	public static String USER_LIST = "user_list_page";
	public static String BED_MANAGEMENT = "bed management";
	public static final String CURRENT_PRESCRIPTION = "current_date_prescription";
	public static final String ACTIVE_PRESCRIPTION = "active_prescription";
	public static final String PAST_PRESCRIPTION = "inactive_prescription";
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	public static final SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat dateFormatUI = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
	public static final SimpleDateFormat dateFormatAMPM = new SimpleDateFormat("hh:mm a");
	public static final SimpleDateFormat dateFormat24Time = new SimpleDateFormat("hh:mm");
	public static final SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String HOURS = "hours";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";
	public static final String BABY_PRESCRIPTION = "baby_perscription";
	public static final String HL7RECORDS_DIRECTORY_NAME = "HL7Records";
	public static final String CSVRecords_DIRECTORY_NAME = "CSVRecords";
	public static final CharSequence NA = "NA";
	public static final String PIPE = "|";
	public static final String HL7RECORDS_TRASH_DIRECTORY_NAME = "HL7TrashRecords";
	public static final String CSVRecords_TRASH_DIRECTORY_NAME = "CSVTrashRecords";
	public static final String EMPTY_OBJ_STR = "emptyObj";
	public static final String NURSING_EPISODE_EMPTY_OBJ_STR = "nursingEpisodeEmptyObj";
	public static final String PREVIOUS_DATA_STR = "previousData";
	public static final String NURSING_NOTES = "NursingNotes";
	public static final String Peripheral_Cannula = "Peripheral Cannula";
	public static final String Central_Line = "Central Line";
	public static final String Lumbar_Puncture = "Lumbar Puncture";
	public static final String Vtap = "Vtap";
	public static final String ET_Intubation = "ET Intubation";
	public static final String Procedure_Other = "Procedure Other";
	public static final String In_Born = "In Born";
	public static final String Out_Born = "Out Born";
	public static final String Sr_Doctor = "Sr. Doctor";
	public static final String Jr_Doctor = "Jr. Doctor";
	public static final String Nurse = "Nurse";
	public static final long DAY_VALUE = 24 * 60 * 60 * 1000;
	public static final long WEEK_VALUE = 7*24 * 60 * 60 * 1000;
	public static String CLIENT_TIME_ZONE = "GMT+5:30";
	public static HashMap< String,Boolean > isCapturingMap = new HashMap< String,Boolean>();
	public static HashMap< String,List<String> > cameraListMap = new HashMap< String,List<String> >();
	public static HashMap<Integer, String> monthMapping = new HashMap<Integer, String>(){
		
	};
	
	public static HashMap<Integer, String> hrFormatTo12hrFormatMapping = new HashMap<Integer, String>(){
		
	};

	// queries
	public static String GET_USER_DETAILS = "SELECT * FROM User";

	public static HashMap<String, String> roleNameMAp = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put("Super User", "SuperUser");
			put("Administrator", "Admin");
			put("SrDoc", "Sr. Doctor");
			put("JrDoc", "Jr. Doctor");
			put("Nurse", "Nurse");
			put("Front Desk", "Frontdesk");
		}
	};

	// application context
	public static ConfigurableApplicationContext applicatonContext;
	public static String UPLOAD_DOCUMENT_DIR = "Uploaded_Document";

	public static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static final String KAFKA_HOSTNAME = "119.81.121.189";
	public static final String KAFKA_PORT = "9092";
	public static final String KAFKA_TOPIC = "inicuintellivue";
	public static final String KAFKA_GROUP_ID = "inicu_device";
	public static final String TIME_SPLITTER = ":";

	// for medical prescription....
	public static final HashMap<String, List<String>> medicineFrequency = new HashMap<String, List<String>>() {
		{
			put("FR1", Arrays.asList("Daily once a day", "1"));
			put("FR12", Arrays.asList("Every 12 hours", "12"));
			put("FR18", Arrays.asList("Once a day", "0.75"));
			put("FR2", Arrays.asList("BD Twice a day", "2"));
			put("FR24", Arrays.asList("Every 24 hours", "24"));
			put("FR3", Arrays.asList("TDS Three times a day", "3"));
			put("FR36", Arrays.asList("Every 36 hours", ".66"));
			put("FR4", Arrays.asList("QID Four times a day", "4"));
			put("FR48", Arrays.asList("Every 48 hours", "0.5"));
			put("FR5", Arrays.asList("Q4h Every 4 hours", "6"));
			put("FR8", Arrays.asList("Q8h Every 8 hours", "8"));
			put("FR72", Arrays.asList("Every 72 hours", "0.33"));

		}
	};

	public static final List<String> nursingNotesModules = new ArrayList<String>() {
		{
			add(VITAL_PARAM);
			add(INTAKE);
			add(OUTPUT);
			add(CATHERS);
			add(BLOOD_GAS);
			add(NURO_VITALS);
			add(VENTILATOR);
			add(DAILY_ASSESSMENT);
			add(MISC);
		}
	};

	public static final String VITAL_PARAM = "vitalParameters";
	public static final String INTAKE = "Intake";
	public static final String OUTPUT = "Output";
	public static final String CATHERS = "Catheters";
	public static final String BLOOD_GAS = "bloodGas";
	public static final String NURO_VITALS = "neuroVitals";
	public static final String VENTILATOR = "ventilatorSetting";
	public static final String DAILY_ASSESSMENT = "dailyAssessment";
	public static final String MISC = "misc";
	public static final String VALUE_TYPE_SAMPLE = "sampling";
	public static final String VALUE_TYPE_NUMERIC = "numeric";

	public static final String PREFERENCE_DEVICE = "device_integration_type";
	public static final String BABY_FEED_BY_DOCTOR = "babyfeedbydoctor";
	public static final String CURRENT_BLOOD_PRODUCT = "bloodProductByDoctor";
	public static final String PREFERENCE_SUMMARY = "discharge_summary_type";
	public static final String BABY_DOCUMENTS_DIR = "Baby_Documents";
	public static final Object AGE_AT_ONSET = "ageAtOnset";
	public static final Object AGE_AT_ASSESSMENT = "ageAtAssessment"; // assessment->resp->bpd
																		// reqd
																		// ageAtAssessment
	public static final Object PMA = "PMA";
	public static final String NEC = "NEC";
	public static final Object GESTATION = "gestation";
	public static final String HYPOGLYCEMIA = "hypoglycemia";
	public static final String HYPOGLYCEMIA_STRING = "Hypoglycemia";
	public static final String HYPERGLYCEMIA = "hyperglycemia";
	public static final String HYPONATREMIA = "hyponatremia";
	public static final String HYPERNATREMIA = "hypernatremia";
	public static final String HYPERKALEMIA = "hyperkalemia";
	public static final String HYPOKALEMIA = "hypokalemia";
	public static final String HYPERCALCEMIA = "hypercalcemia";
	public static final String HYPOCALCEMIA = "hypocalcemia";
	public static final String IEM = "iem";
	public static final String ACIDOSIS = "acidosis";
	public static final String SEIZURES = "seizures";
	public static final String ENCEPHALOPATHY = "encephalopathy";
	public static final String NEUROMUSCULAR_DISORDER="neuromuscular_disorders";
	public static final String IVH = "ivh";
	public static final String ASPHYXIA = "asphyxia";
	public static final String SEPSIS = "sepsis";
	public static final String VAP = "vap";
	public static final String CLABSI = "clabsi";
	public static final String INFECTION="Infection";
	public static final String INTRAUTERINE = "intrauterine";
	public static final String SYSTEMEVENT = "system";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String INACTIVE = "inactive";
	public static final String BIRTH_WEIGHT = "birthWeight";
	public static final String ENERGY = "Energy";
	public static final String PROTEIN = "Protein";
	public static final String FAT = "Fat";
	public static final String VITAMINa = "Vitamina";
	public static final String VITAMINd = "Vitamind";
	public static final String CALCIUM = "Calcium";
	public static final String PHOSPHORUS = "Phosphorus";
	public static final String IRON = "Iron";
	public static final String CARBOHYDRATES = "Carbohydrates";
	public static final Object OTHERS = "Others";
	public static final String JAUNDICE = "Jaundice";
	public static final String BR = "<br>";
	public static final String Procedure_ChestTube = "ChestTube";
	public static final String Procedure_ExchangeTransfusion = "exchangetransfusion";
	public static final String Peritoneal_Dialysis = "Peritoneal Dialysis";
	public static final String Procedure_ETSuction = "suction";
	public static final String DEVICE_TIMEOUT = "device_data_check";
	// constants for property files
	public static String PROP_MIGRATION = "mean_value_migration";
	public static String PROP_KAFKA_HOST = "kafka_host";
	public static String PROP_KAFKA_PORT = "kafka_port";
	public static String PROP_KAFKA_GROUP_ID = "kafka_groupid";
	public static String PROP_KAFKA_CONNECTOR_TOPIC = "kafka_topic";
	public static String PROP_INFINITY_TCP_LISTENER = "infinity_tcp_listener";
	public static String PROP_INFINITY_TCP_PORT = "infinity_tcp_port";
	public static String PROP_HL7_HIS_INTEGRATION = "hl7_his_integration";
	public static String PROP_KAFKA_CONNECTOR = "start_kafka";
	public static String PROP_EXCEPTION_SERVER_NAME = "exception_server_name";
	public static String PROP_ANALYTICS_ADD = "analytics_server_address";
	public static String PROP_ANALYTICS_START = "start_analytics";
	
	public static String PROP_CARESCAPE_ACK_LISTENER = "carescape_ACK_listener";
	public static String PROP_CARESCAPE_ACK_PORT = "carescape_ACK_PORT";
	public static String PROP_CARESCAPE_ACK_IP = "carescape_ACK_IPAddress";


	public static String REG_EX_NUMBER_PATTERN = "-?\\d+(\\.\\d+)?";
	public static boolean isKafka = true;

	// rainbow details
	public static String REMOTE_IP_ADDRESS = "";
	public static String REMOTE_DATABASE_NAME = "";
	public static String REMOTE_USER = "";
	public static String REMOTE_PASSWORD = "";
	public static String REMOTE_PORT = "";
	public static long REMOTE_THREAD_FREQUENCY = 1000 * 60 * 60; // every
																	// 60mins,
																	// thread
																	// should
																	// run.
	public static String REMOTE_LABVIEW_NAME = "";
	public static String REMOTE_PATIENTVIEW_NAME = "";
	public static String REMOTE_SCHEMA = "";
	public static String REMOTE_TESTS_NAME = ""; 
	
	public static String REMOTE_LABVIEW_NAME_HISTO = "";
	public static String REMOTE_LABVIEW_NAME_MICRO = "";
	public static String REMOTE_LABVIEW_NAME_TEXT = "";
	public static String REMOTE_IP_ADDRESS_POSTGRES = "";
	public static String REMOTE_PORT_POSTGRES = "";
	public static String REMOTE_DATABASE_NAME_POSTGRES = "";
	public static String REMOTE_USER_POSTGRES = "";
	public static String REMOTE_PASSWORD_POSTGRES = "";
	public static String REMOTE_SCHEMA_POSTGRES = "";
	public static long REMOTE_THREAD_FREQUENCY_POSTGRES = 1000 * 60 * 6;
	
	public static boolean isHISData = true;

	public static Boolean HIS_PATIENT_INTEGRATION = null;
	public static String HIS_DISCHARGE_VIEW = "";
	static {
		if (SCHEMA_NAME.equalsIgnoreCase("apollo")) {
			// apollo remote database details
			REMOTE_IP_ADDRESS = "slavediagitdose.ckvwbt1syjaf.ap-south-1.rds.amazonaws.com";
			REMOTE_DATABASE_NAME = "apollo_live_new";
			REMOTE_USER = "inicu";
			REMOTE_PASSWORD = "inicu@123";
			REMOTE_PORT = "3306";
			REMOTE_THREAD_FREQUENCY = 1000 * 60 * 60; // every 30mins, thread
														// should run.
			isHISData = false;
			REMOTE_LABVIEW_NAME = "inicu_patientlabinfo";
			REMOTE_LABVIEW_NAME_HISTO = "inicu_patientlabinfo_histo";
			REMOTE_LABVIEW_NAME_MICRO = "inicu_patientlabinfo_micro";
			REMOTE_LABVIEW_NAME_TEXT = "inicu_patientlabinfo_text";
			REMOTE_PATIENTVIEW_NAME = "ahll_nicu_baby_details";
			REMOTE_TESTS_NAME = "inicu_testlabinfo";
			HIS_PATIENT_INTEGRATION = true;
			HIS_DISCHARGE_VIEW = "ahll_nicu_baby_discharge_details_new";
			
			//HIS_PATIENT_INTEGRATION = true;
			REMOTE_IP_ADDRESS_POSTGRES = "10.1.1.221";
			REMOTE_PORT_POSTGRES = "5432";
			REMOTE_DATABASE_NAME_POSTGRES = "hms";
			REMOTE_USER_POSTGRES = "nicu"; 
			REMOTE_PASSWORD_POSTGRES = "Inicu123"; 
			REMOTE_SCHEMA_POSTGRES = "ahllclinics";
			REMOTE_THREAD_FREQUENCY_POSTGRES = 1000 * 60 * 6;
			
			
		} else if (SCHEMA_NAME.equalsIgnoreCase("rainbow")) {

			// REMOTE_IP_ADDRESS = "10.10.226.45";
			// REMOTE_DATABASE_NAME = "HO_TRAINDB";
			REMOTE_IP_ADDRESS = "10.10.226.104";
			REMOTE_DATABASE_NAME = "HEALTHOBJECT";
			REMOTE_USER = "inicu";
			REMOTE_PASSWORD = "test.123$#@";
			REMOTE_PORT = "15333";
			REMOTE_THREAD_FREQUENCY = 1000 * 60 * 30; // every 30mins, thread
														// should run.
			REMOTE_TESTS_NAME = "VW_NicuTestMaster_VM6";
			REMOTE_LABVIEW_NAME = "VW_NicuPatientsOrders_VM6";
			REMOTE_PATIENTVIEW_NAME = "VW_NicuPatientsOrders_VM6";
			// no schema name reqd in case of sql server
			HIS_PATIENT_INTEGRATION = true;
			isHISData = false;
			HIS_DISCHARGE_VIEW = "VW_NicuPatientsDischarge_VM6";
		}
		
		else if (SCHEMA_NAME.equalsIgnoreCase("kalawati")) {

			// REMOTE_IP_ADDRESS = "10.10.226.45";
			// REMOTE_DATABASE_NAME = "HO_TRAINDB";
			REMOTE_IP_ADDRESS = "192.168.57.185";
			REMOTE_DATABASE_NAME = "kalawati_interface";
			REMOTE_USER = "root";
			REMOTE_PASSWORD = "123456";
			REMOTE_PORT = "3333";
			REMOTE_THREAD_FREQUENCY = 1000 * 60 * 30; // every 30mins, thread
														// should run.
			REMOTE_LABVIEW_NAME = "PatientLabInfo";
			REMOTE_PATIENTVIEW_NAME = "PatientLabInfo";
			REMOTE_TESTS_NAME = "TestLabInfo";
			isHISData = false;
			// no schema name reqd in case of sql server
			HIS_PATIENT_INTEGRATION = true;
		}
		
		else if (SCHEMA_NAME.equalsIgnoreCase("gangaram")) {

			// REMOTE_IP_ADDRESS = "10.10.226.45";
			// REMOTE_DATABASE_NAME = "HO_TRAINDB";
			REMOTE_IP_ADDRESS = "172.16.8.65";
			REMOTE_DATABASE_NAME = "TRAK";
			REMOTE_USER = "INICU";
			REMOTE_PASSWORD = "ucini";
			REMOTE_PORT = "56772";
			REMOTE_THREAD_FREQUENCY = 1000 * 60 * 30; // every 30mins, thread
														// should run.
			REMOTE_LABVIEW_NAME = "PatientLabInfo";
			REMOTE_PATIENTVIEW_NAME = "PatientLabInfo";
			REMOTE_TESTS_NAME = "TestLabInfo";
			isHISData = false;
			// no schema name reqd in case of sql server
			HIS_PATIENT_INTEGRATION = true;
		} /*else if (SCHEMA_NAME.equalsIgnoreCase("kdah")) {

			// for locally connecting on backup server
			HIS_DISCHARGE_VIEW = "ref_hospitalbranchname";


			REMOTE_LABVIEW_NAME = "PatientLabInfo";
			REMOTE_PATIENTVIEW_NAME = "PatientLabInfo";
			REMOTE_TESTS_NAME = "TestLabInfo";
			isHISData = false;


			REMOTE_IP_ADDRESS_POSTGRES = "localhost";
			REMOTE_PORT_POSTGRES = "5432";
			REMOTE_DATABASE_NAME_POSTGRES = "inicudb";
			REMOTE_USER_POSTGRES = "postgres";
			REMOTE_PASSWORD_POSTGRES = "postgres";
			REMOTE_SCHEMA_POSTGRES = "kdah";
			REMOTE_THREAD_FREQUENCY_POSTGRES = 1000 * 60 * 30;
		}*/
	}

	// public static String REMOTE_IP_ADDRESS = "10.10.226.45";
	// public static String REMOTE_DATABASE_NAME= "HO_TRAINDB";
	// public static String REMOTE_USER= "inicu";
	// public static String REMOTE_PASSWORD= "welcome@1234";
	// public static String REMOTE_PORT= "1433";
	// public static long REMOTE_THREAD_FREQUENCY = 1000*60*30; // every 30mins,
	// thread should run.
	// public static String REMOTE_LABVIEW_NAME = "VW_NicuPatientsOrders_VM6";
	// public static String REMOTE_PATIENTVIEW_NAME =
	// "VW_NicuPatientsOrders_VM6";
	// public static String REMOTE_SCHEMA= "";

	// remote database details where patient, lab details are to be fetched from
	// public static String REMOTE_IP_ADDRESS = "52.66.177.40";
	// public static String REMOTE_DATABASE_NAME= "hms";
	// public static String REMOTE_SCHEMA= "ahllclinics";
	// public static String REMOTE_USER= "nicu";
	// public static String REMOTE_PASSWORD= "";
	// public static String REMOTE_PORT= "5432";
	// public static long REMOTE_THREAD_FREQUENCY = 1000*60*30; // every 30mins,
	// thread should run.
	// public static String REMOTE_LABVIEW_NAME = "custom_ahll_lab_nicu_view";
	// public static String REMOTE_PATIENTVIEW_NAME = "ahll_nicu_baby_details";

	// for locally connecting on backup server
	// public static String REMOTE_IP_ADDRESS = "183.82.0.6";
	// public static String REMOTE_DATABASE_NAME= "hmspr115";
	// public static String REMOTE_SCHEMA= "ahllclinics_pr";
	// public static String REMOTE_USER= "postgres";
	// public static String REMOTE_PASSWORD= "";
	// public static String REMOTE_PORT= "5432";

	public static String PROP_HIS_PATIENTDATA_INTEGRATION = "patientdata_integration";
	public static String PROP_LAB_INTEGRATION = "lab_integration";
	public static String PROP_NUTRIONAL_COMPLAINCE = "nutrional_complaince";
	public static String IMAGE_TIMEOUT = "camera_old_feed_timeout";
	public static String PROP_WOWZA_SDP_URL = "wowza_sdp_url";
	public static String PROP_WOWZA_APPLICATION_NAME = "wowza_application_name";
	public static String PROP_WOWZA_STREAM_NAME = "wowza_stream_name";

	static {
		monthMapping.put(0, "Jan");
		monthMapping.put(1, "Feb");
		monthMapping.put(2, "March");
		monthMapping.put(3, "April");
		monthMapping.put(4, "May");
		monthMapping.put(5, "June");
		monthMapping.put(6, "July");
		monthMapping.put(7, "August");
		monthMapping.put(8, "Sept");
		monthMapping.put(9, "Oct");
		monthMapping.put(10, "Nov");
		monthMapping.put(11, "Dec");
	}
	
	static {
		hrFormatTo12hrFormatMapping.put(1, "1:00am");
		hrFormatTo12hrFormatMapping.put(2, "2:00am");
		hrFormatTo12hrFormatMapping.put(3, "3:00am");
		hrFormatTo12hrFormatMapping.put(4, "4:00am");
		hrFormatTo12hrFormatMapping.put(5, "5:00am");
		hrFormatTo12hrFormatMapping.put(6, "6:00am");
		hrFormatTo12hrFormatMapping.put(7, "7:00am");
		hrFormatTo12hrFormatMapping.put(8, "8:00am");
		hrFormatTo12hrFormatMapping.put(9, "9:00am");
		hrFormatTo12hrFormatMapping.put(10, "10:00am");
		hrFormatTo12hrFormatMapping.put(11, "11:00am");
		hrFormatTo12hrFormatMapping.put(12, "12:00pm");
		hrFormatTo12hrFormatMapping.put(13, "1:00pm");
		hrFormatTo12hrFormatMapping.put(14, "2:00pm");
		hrFormatTo12hrFormatMapping.put(15, "3:00pm");
		hrFormatTo12hrFormatMapping.put(16, "4:00pm");
		hrFormatTo12hrFormatMapping.put(17, "5:00pm");
		hrFormatTo12hrFormatMapping.put(18, "6:00pm");
		hrFormatTo12hrFormatMapping.put(19, "7:00pm");
		hrFormatTo12hrFormatMapping.put(20, "8:00pm");
		hrFormatTo12hrFormatMapping.put(21, "9:00pm");
		hrFormatTo12hrFormatMapping.put(22, "10:00pm");
		hrFormatTo12hrFormatMapping.put(23, "11:00pm");
		hrFormatTo12hrFormatMapping.put(00, "12:00am");
	}
}
