package com.inicu.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.inicu.device.utility.CheckDeviceExceptions;
import com.inicu.notification.*;
//import com.inicu.postgres.Quartz.AutowiringSpringBeanJobFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.inicu.analytics.TrainingSetController;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.device.camera.CameraThread;
import com.inicu.his.data.acquisition.HISReceiver;
import com.inicu.his.data.acquisition.LabReceiver;
import com.inicu.hl7.data.acquisition.comm.TCPServer;
import com.inicu.kafka.KafkaMessageReceiver;
import com.inicu.kafkacassandra.connector.constants.Constants;
import com.inicu.postgres.controller.HL7FileHandling;
import com.inicu.postgres.nutrionalComplaince.NutritionalOntology;
import com.inicu.postgres.nutrionalComplaince.NutritionalOntologyApollo;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.EsphaganErrors;
import com.inicu.postgres.utility.HMLM;
import com.inicu.postgres.utility.NeofaxErrors;
import com.inicu.device.geb450monitor.carescapeACK.CarescapeACKClient;
/**
 * main application to run boot application
 * 
 * @author Dipin
 * @version 1.0
 */
@EnableSpringConfigured
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.inicu")
public class InicuApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(InicuApplication.class);

	// static String startTimestamp = "2017-03-20 05:20:37.193";
	// static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss.SSS");
	// static Date currDate = null;
	// static int counter = 1;
	// static BufferedWriter bufferedWriter = null;
	//usage email Obj
	static Runnable VitalTrackerNotificationObj = null;
	static Runnable usageEmailObj = null;
	//procedure email obj
	static Runnable procedureEmailObj = null;
	static Runnable sinEmailObj = null;
	static Runnable sendAdoptionWeekObj = null;
	static Runnable dailyReviewEmailObj = null;
	static Runnable emailObj = null;
	static Runnable hmlmObj = null;
	static Runnable esphghanObj = null;
	static Runnable neofaxObj = null;

	// static ProcedureEmailNotification procedureEmailObj =null;

	public InicuApplication() {
		System.out.println("---------InicuApplication--------");
		logger.info("Logger Enabled ::");
	}

	@SuppressWarnings("deprecation")
	 public static void main(String[] args) throws Exception {
		
		InicuApplication nicuApplication = new InicuApplication();
		// currDate = formatter.parse(startTimestamp);
		// BufferedWriter bw = new BufferedWriter(new
		// FileWriter("insertQuery.txt"));
		// bufferedWriter = bw;
		BasicConstants.props = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(BasicConstants.PROPS_FILE);
			BasicConstants.props.load(input);

			String kafkaTopic = BasicConstants.props.getProperty(BasicConstants.PROP_KAFKA_CONNECTOR_TOPIC);
			if (kafkaTopic != null && !kafkaTopic.isEmpty()) {
				System.out.println("[ KAFKA TOPIC ] = " + kafkaTopic);
				Constants.KAFKA_HW_LAYER_TOPIC = kafkaTopic;
			}
			System.getProperties().put("server.port", 8383);
			BasicConstants.applicatonContext = SpringApplication.run(InicuApplication.class, args);
			// cassandra keyspace.
			if (null != BasicConstants.props.getProperty(BasicConstants.PROP_EXCEPTION_SERVER_NAME)) {
				BasicConstants.COMPANY_ID = BasicConstants.props.getProperty(BasicConstants.PROP_EXCEPTION_SERVER_NAME);
			}

			nicuApplication.runKafkaReceiver();
			nicuApplication.runHISServerReadingHL7FromDirectory();
			nicuApplication.migratePostgresDataToCassandra();
			nicuApplication.runDraegerInfinityGatewayReceiver();
			nicuApplication.fetchHISADTData();			
			nicuApplication.receiveLabIntegrationData();
			nicuApplication.runCameraThread();
			nicuApplication.sendDeviceExceptionEmails();
			nicuApplication.receivenutrionalComplaince();
			nicuApplication.runCarescapeACKGatewayReceiver();

//			nicuApplication.preparingTrainingSet();

			// Email notification for all
//			emailObj = nicuApplication.sendPeriodicNotification(EmailNotification.class);
//			sendAdoptionWeekObj = nicuApplication.sendPeriodicNotification(SendAdoptionUsageWeek.class);
//			dailyReviewEmailObj = nicuApplication.sendPeriodicNotification(DailyReviewEmailNotification.class);
//			hmlmObj = nicuApplication.sendPeriodicAnalytics(HMLM.class);
			
			esphghanObj = nicuApplication.sendEsphghan(EsphaganErrors.class);
			neofaxObj = nicuApplication.sendNeofax(NeofaxErrors.class);

			// set the URL for apollo ICHR
			String pushNotification = BasicConstants.props.getProperty(BasicConstants.PROP_PUSH_NOTIFICATION);
			if (pushNotification != null && !pushNotification.isEmpty()) {
				System.out.println("[ Push Notification ] = " + pushNotification);
				BasicConstants.PUSH_NOTIFICATION = pushNotification;
			}

			String admitPatient = BasicConstants.props.getProperty(BasicConstants.PROP_ADMIT_PATIENT);
			if (admitPatient != null && !admitPatient.isEmpty()) {
				System.out.println("[ Admit Patient ] = " + admitPatient);
				BasicConstants.ADMIT_PATIENT = admitPatient;
			}

			String schemaName = BasicConstants.props.getProperty(BasicConstants.PROP_ICHR_SCHEMA);
			if (schemaName != null && !schemaName.isEmpty()) {
				System.out.println("[ Schema Name ] = " + schemaName);
				BasicConstants.ICHR_SCHEMA = schemaName;
			}


			String wowzaIp = BasicConstants.props.getProperty(BasicConstants.PROP_WOWZA_IP);
			if (wowzaIp != null && !wowzaIp.isEmpty()) {
				System.out.println("[ Wowza IP ] = " + wowzaIp);
				BasicConstants.WOWZA_IP = wowzaIp;
			}

			String wowzaPort= BasicConstants.props.getProperty(BasicConstants.PROP_WOWZA_PORT);
			if (wowzaPort != null && !wowzaPort.isEmpty()) {
				System.out.println("[ Wowza PORT ] = " + wowzaPort);
				BasicConstants.WOWZA_PORT = Integer.parseInt(wowzaPort);
			}

			String wowza_username= BasicConstants.props.getProperty(BasicConstants.PROP_WOWZA_USERNAME);
			if (wowza_username != null && !wowza_username.isEmpty()) {
				System.out.println("[ Wowza Username ] = " + wowza_username);
				BasicConstants.WOWZA_USERNAME = wowza_username;
			}

			String wowza_password= BasicConstants.props.getProperty(BasicConstants.PROP_WOWZA_PASSWORD);
			if (wowza_password != null && !wowza_password.isEmpty()) {
				System.out.println("[ Wowza Username ] = " + wowza_password);
				BasicConstants.WOWZA_PASSWORD = wowza_password;
			}

			// Branch Name for the device exception
			String branchName = BasicConstants.props.getProperty(BasicConstants.PROPS_BRANCH_NAME);
			if (branchName != null && !branchName.isEmpty()) {
				System.out.println("[ Branch Name ] = " + branchName);
				BasicConstants.BRANCH_NAME = branchName;
			}

			// Related to Twilio
			String TwilioSID = BasicConstants.props.getProperty(BasicConstants.PROP_TWILIO_SID);
			if (TwilioSID != null && !TwilioSID.isEmpty()) {
				System.out.println("[ Twilio SID ] = " + TwilioSID);
				BasicConstants.TWILIO_SID = TwilioSID;
			}

			String TwilioAPI_KEY = BasicConstants.props.getProperty(BasicConstants.PROP_TWILIO_API_KEY);
			if (TwilioAPI_KEY != null && !TwilioAPI_KEY.isEmpty()) {
				System.out.println("[ Twilio API KEY ] = " + TwilioAPI_KEY);
				BasicConstants.TWILIO_API_KEY = TwilioAPI_KEY;
			}

			String TwilioAPI_SECRET = BasicConstants.props.getProperty(BasicConstants.PROP_TWILIO_API_SECRET);
			if (TwilioAPI_SECRET != null && !TwilioAPI_SECRET.isEmpty()) {
				System.out.println("[ Twilio API SECRET ] = " + TwilioAPI_SECRET);
				BasicConstants.TWILIO_SECRET_KEY = TwilioAPI_SECRET;
			}

			String TwilioAuth_KEY = BasicConstants.props.getProperty(BasicConstants.PROP_TWILIO_AUTH_KEY);
			if (TwilioAuth_KEY != null && !TwilioAuth_KEY.isEmpty()) {
				System.out.println("[ Twilio API SECRET ] = " + TwilioAuth_KEY);
				BasicConstants.TWILIO_AUTH_KEY = TwilioAuth_KEY;
			}

			String ichrEnabled = BasicConstants.props.getProperty(BasicConstants.PROP_ICHR_SETTINGS_ENABLED);
			if (ichrEnabled != null && !BasicUtils.isEmpty(ichrEnabled)) {
				System.out.println("[ ICHR Enabled ] = " + ichrEnabled);
				BasicConstants.ICHR_SETTINGS_ENABLED = Boolean.parseBoolean(ichrEnabled);
			}

			String remoteViewEnabled = BasicConstants.props.getProperty(BasicConstants.PROP_REMOTE_VIEW_ENABLED);
			if (ichrEnabled != null && !BasicUtils.isEmpty(remoteViewEnabled)) {
				System.out.println("[ REMOTE VIEW ENABLED ] = " + remoteViewEnabled);
				BasicConstants.REMOTE_VIEW_ENABLED = Boolean.parseBoolean(remoteViewEnabled);
			}

			String enableWebSocket = BasicConstants.props.getProperty(BasicConstants.PROP_WEBSOCKET_ENABLE);
			if(enableWebSocket!=null && !BasicUtils.isEmpty(enableWebSocket)){
				System.out.println("[ WEBSOCKET ENABLE ] = " + enableWebSocket);
				BasicConstants.WEBSOCKET_ENABLE = Boolean.parseBoolean(enableWebSocket);
			}

			String enableCameraRecording = BasicConstants.props.getProperty(BasicConstants.PROP_RECORDING_ENABLED);
			if(enableCameraRecording!=null && !BasicUtils.isEmpty(enableCameraRecording)){
				System.out.println("[ CAMERA RECORDING ENABLED ] = " + enableCameraRecording);
				BasicConstants.RECORDING_ENABLED = Boolean.parseBoolean(enableCameraRecording);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	private  void runHISServerReadingHL7FromDirectory() {
		String hisIntegration = BasicConstants.props.getProperty(BasicConstants.PROP_HL7_HIS_INTEGRATION);
		if (hisIntegration != null) {
			System.out.println("[ HIS HL7 INTEGRATION ] = " + hisIntegration);
			if (hisIntegration.equalsIgnoreCase("true")) {
				BasicConstants.applicatonContext.getBean(HL7FileHandling.class).start();
			}
		}
	}

	private void migratePostgresDataToCassandra() {
		String isMigrationActive = BasicConstants.props.getProperty(BasicConstants.PROP_MIGRATION);
		if (isMigrationActive != null && !isMigrationActive.isEmpty()) {
			System.out.println("[ MIGRATION ] = " + isMigrationActive);
			if (isMigrationActive.equalsIgnoreCase("true")) {
				DeviceDataService devData = (DeviceDataService) BasicConstants.applicatonContext
						.getBean(DeviceDataService.class);
				if (!BasicUtils.isEmpty(devData)) {
					System.out.println("starting migration");
					devData.dataMigrationService();
				}
			}
		}
	}

	private void runDraegerInfinityGatewayReceiver() {
		String isTcpListener = BasicConstants.props.getProperty(BasicConstants.PROP_INFINITY_TCP_LISTENER);
		if (isTcpListener != null && !isTcpListener.isEmpty()) {
			System.out.println("[ TCP INFINITY LISTENER ] = " + isTcpListener);
			if (isTcpListener.equalsIgnoreCase("true")) {
				String port = BasicConstants.props.getProperty(BasicConstants.PROP_INFINITY_TCP_PORT);
				if (port != null && !port.isEmpty()) {
					System.out.println("[ TCP INFINITY PORT ] = " + port);
					try {
						new Thread(new TCPServer(Integer.parseInt(port))).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void runKafkaReceiver() {
		String isKafka = BasicConstants.props.getProperty(BasicConstants.PROP_KAFKA_CONNECTOR);
		if (isKafka != null && !isKafka.isEmpty()) {
			if (isKafka.equalsIgnoreCase("true")) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							new KafkaMessageReceiver().startKafkaMessageReceiver();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}
	
	private void runCarescapeACKGatewayReceiver() {
	    String isCarescapeACK = BasicConstants.props.getProperty(BasicConstants.PROP_CARESCAPE_ACK_LISTENER);
	    if (isCarescapeACK != null && !isCarescapeACK.isEmpty()) {
	      System.out.println("[ CARESCAPE ACK LISTENER ] = " + isCarescapeACK);
	      if (isCarescapeACK.equalsIgnoreCase("true")) {
	        String port = BasicConstants.props.getProperty(BasicConstants.PROP_CARESCAPE_ACK_PORT);
	        String ipAddress = BasicConstants.props.getProperty(BasicConstants.PROP_CARESCAPE_ACK_IP);
	        if (port != null && !port.isEmpty() && ipAddress != null && !ipAddress.isEmpty()) {
	          System.out.println("[ CARESCAPE ACK PORT ] = " + port);
	          System.out.println("[ CARESCAPE ACK IP ADDRESS ] = " + ipAddress);
	          try {
//	            int i = 0;
//	            int j = 10;
//	            while (i < 5) {
//	              String messageString = "MSH|^~\\&|HL7|MMS|||202002082110" + j
//	                  + "||ORU^R01||P|2.3||||||8859/1\r" + "PID|||12810914^^^DefaultDomain^PI||\r"
//	                  + "PV1||P|NICU^BED0" + i + "^126.16.252.140\r" + "OBR|1||||||20191129140005\r"
//	                  + "OBX|1|ST|HR||170|/min|||||R\r" + "OBX|2|ST|SPO2-%||100|%|||||R\r"
//	                  + "OBX|3|ST|SPO2-R||170|/min|||||R\r" + "OBX|4|ST|TEMP||36.5||||||R\r";
//	              GEB450ParseData.parse(messageString);
//	              j = j + 3;
//	              i++;
//	              Thread.sleep(3000);
//	            }
	            new Thread(new CarescapeACKClient(ipAddress, Integer.parseInt(port))).start();
	          } catch (Exception e) {
	            e.printStackTrace();
	          }
	        }
	      }
	    }
	  }

	private void fetchHISADTData() {
		/*
		 * Fetch data from remote database and store on INICU database
		 */
		String isHISPatientIntegration = "false";
		isHISPatientIntegration = BasicConstants.props.getProperty(BasicConstants.PROP_HIS_PATIENTDATA_INTEGRATION);

		if (isHISPatientIntegration.equalsIgnoreCase("true")) {
			try {
				System.out.println("[ PATIENTDATA HIS INTEGRATION ] " + isHISPatientIntegration);
				BasicConstants.applicatonContext.getBean(HISReceiver.class).start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void receiveLabIntegrationData() {
		String isLabIntegration = "false";
		isLabIntegration = BasicConstants.props.getProperty(BasicConstants.PROP_LAB_INTEGRATION);

		if (isLabIntegration.equalsIgnoreCase("true")) {
			try {
				System.out.println("[ LAB INTEGRATION ] " + isLabIntegration);
				BasicConstants.applicatonContext.getBean(LabReceiver.class).start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	private void receivenutrionalComplaince() {
		String isNutritionalComplaince = "false";
		isNutritionalComplaince = BasicConstants.props.getProperty(BasicConstants.PROP_NUTRIONAL_COMPLAINCE);

		if (isNutritionalComplaince.equalsIgnoreCase("true")) {
			try {
				System.out.println("[ NUTRIONAL_COMPLAINCE ] " + isNutritionalComplaince);
				BasicConstants.applicatonContext.getBean(NutritionalOntologyApollo.class).start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	private void preparingTrainingSet() {
		
		try {
			BasicConstants.applicatonContext.getBean(TrainingSetController.class).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void runCameraThread() {
		// start a thread to delete previous records in camera_feed
		String camera_timeout = BasicConstants.props.getProperty(BasicConstants.IMAGE_TIMEOUT).trim();
		// System.out.println("camera_timeout " + camera_timeout);
		try {
			// System.out.println("Starting thread to delete old data from
			// camera_feed");
			/*
			 * Runnable deleteOldFeed = new Runnable() { public void run() {
			 * System.out.println("starting thread");
			 */
			BasicConstants.applicatonContext.getBean(CameraThread.class).start();
			/*
			 * } }; ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
			 * exec.scheduleAtFixedRate(deleteOldFeed , 0, Long.valueOf(camera_timeout),
			 * TimeUnit.MINUTES);
			 */

			// (//new CameraThread(camera_timeout)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Runnable sendPeriodicNotification(Class schedulerClassName) {
		
		Runnable schedulerClass = null;
		BasicConstants.CLIENT_TIME_ZONE = BasicConstants.props.getProperty("client_time_zone");		
		// Email notification for Procedures
		try {
			schedulerClass = (Runnable) BasicConstants.applicatonContext
					.getBean(schedulerClassName);
			Date current = new Date();
			Date schedule = new Date();
			schedule.setSeconds(00);
			schedule.setHours(8);
			schedule.setMinutes(45);

			int offset = (TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE)
					.getRawOffset() - TimeZone.getDefault().getRawOffset());
			long initialDelay = ((schedule.getTime() - (current.getTime() + offset)) / 1000);
			if (initialDelay < 0) {
				initialDelay += (24 * 60 * 60);
			}

//			System.out.println("time left for first Procedure email start: "
//					+ initialDelay + "secs");
			ScheduledExecutorService scheduler = Executors
					.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(schedulerClass, initialDelay,
					24 * 60 * 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedulerClass;
	}
	
	private Runnable sendNeofax(Class schedulerClassName) {
		
		Runnable schedulerClass = null;
		int neofaxHour = Integer.parseInt(BasicConstants.props.getProperty(BasicConstants.NEOFAX_HOUR));
		int neofaxMin = Integer.parseInt(BasicConstants.props.getProperty(BasicConstants.NEOFAX_MIN));

		BasicConstants.CLIENT_TIME_ZONE = BasicConstants.props.getProperty("client_time_zone");		
		// Email notification for Procedures
		try {
			schedulerClass = (Runnable) BasicConstants.applicatonContext
					.getBean(schedulerClassName);
			Date current = new Date();
			Date schedule = new Date();
			schedule.setSeconds(00);
			schedule.setHours(neofaxHour);
			schedule.setMinutes(neofaxMin);

			int offset = (TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE)
					.getRawOffset() - TimeZone.getDefault().getRawOffset());
			long initialDelay = ((schedule.getTime() - (current.getTime() + offset)) / 1000);
			if (initialDelay < 0) {
				initialDelay += (24 * 60 * 60);
			}

			System.out.println("time left for first Neofax start: "
					+ initialDelay + "secs");
			ScheduledExecutorService scheduler = Executors
					.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(schedulerClass, initialDelay,
					24 * 60 * 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedulerClass;
	}

	private Runnable sendEsphghan(Class schedulerClassName) {
	
		Runnable schedulerClass = null;
		int esphghanHour = Integer.parseInt(BasicConstants.props.getProperty(BasicConstants.ESPHGHAN_HOUR));
		int esphghanMin = Integer.parseInt(BasicConstants.props.getProperty(BasicConstants.ESPHGHAN_MIN));

		BasicConstants.CLIENT_TIME_ZONE = BasicConstants.props.getProperty("client_time_zone");		
		// Email notification for Procedures
		try {
			schedulerClass = (Runnable) BasicConstants.applicatonContext
					.getBean(schedulerClassName);
			Date current = new Date();
			Date schedule = new Date();
			schedule.setSeconds(00);
			schedule.setHours(esphghanHour);
			schedule.setMinutes(esphghanMin);
	
			int offset = (TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE)
					.getRawOffset() - TimeZone.getDefault().getRawOffset());
			long initialDelay = ((schedule.getTime() - (current.getTime() + offset)) / 1000);
			if (initialDelay < 0) {
				initialDelay += (24 * 60 * 60);
			}
	
			System.out.println("time left for first Esphghan start: "
					+ initialDelay + "secs");
			ScheduledExecutorService scheduler = Executors
					.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(schedulerClass, initialDelay,
					24 * 60 * 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedulerClass;
	}
	
	private Runnable sendPeriodicAnalytics(Class schedulerClassName) {
		
		Runnable schedulerClass = null;
		BasicConstants.CLIENT_TIME_ZONE = BasicConstants.props.getProperty("client_time_zone");		
		// Email notification for Procedures
		try {
			schedulerClass = (Runnable) BasicConstants.applicatonContext
					.getBean(schedulerClassName);
			Date current = new Date();
			Date schedule = new Date();
			schedule.setSeconds(00);
			schedule.setHours(14);
			schedule.setMinutes(05);

			int offset = (TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE)
					.getRawOffset() - TimeZone.getDefault().getRawOffset());
			long initialDelay = ((schedule.getTime() - (current.getTime() + offset)) / 1000);
			if (initialDelay < 0) {
				initialDelay += (24 * 60 * 60);
			}

//			System.out.println("time left for first HM start: "
//					+ initialDelay + "secs");
			ScheduledExecutorService scheduler = Executors
					.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(schedulerClass, initialDelay,
					24 * 60 * 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedulerClass;
	}

	private void sendDeviceExceptionEmails(){
		/*
		 * Fetch data from remote database and store on INICU database
		 */
		String deviceException = BasicConstants.props.getProperty(BasicConstants.PROP_DEVICE_EXCEPTION_ENABLED);
		if (deviceException.equalsIgnoreCase("true")) {
			try {
				System.out.println("[ DEVICE EXCEPTION ] " + deviceException);
				BasicConstants.applicatonContext.getBean(CheckDeviceExceptions.class).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(InicuApplication.class);
	}
}
