package com.inicu.notification;

import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import com.inicu.his.data.acquisition.HISConstants;
import com.inicu.postgres.utility.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.AnalyticsUsagePojo;
import com.inicu.models.SinJSON;
import com.inicu.models.VitalTracker;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.CentralLine;
import com.inicu.postgres.entities.NotificationEmail;
import com.inicu.postgres.entities.PeripheralCannula;
import com.inicu.postgres.serviceImpl.AnalyticsServiceImpl;

@Repository
public class EmailNotification implements Runnable {
	
	@Autowired
	AnalyticsServiceImpl analyticsObj;
	
	@Autowired
	InicuDao inicuDao;
	
	@Autowired
	InicuDatabaseExeption databaseException;
	
	@Autowired
	UserServiceDAO userServiceDao;

	private static final Logger logger = LoggerFactory.getLogger(EmailNotification.class);

	public EmailNotification() {
		super();
	}
	
	public String vitalTrackerNotification(String branchName) {
		String message = "";

		try {
			// will configure time-zone with DB
			Date current = new Date();
			current.setHours(8);
			current.setMinutes(0);
			int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
			long toTime = current.getTime() - offset;
			long fromTime = toTime - (24 * 60 * 60 * 1000);

			String fromDateStr = "" + (fromTime);
			String toDateStr = "" + (toTime);
			
			String detailedMessageHeader = "<br><table style=\"border: 1px solid black;\"><tr><td style=\"border: 1px solid black;\"><span>UHID</span></td>"
					+ "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
					+ "<td style=\"border: 1px solid black;\"><span>NEO Box Name</span></td></tr>";
			Integer troubledBabies = 0;
			
			List<VitalTracker> vitalList = analyticsObj.getVitalsData(fromDateStr, toDateStr, branchName);

			if (!BasicUtils.isEmpty(vitalList)) {

				String vitalTable = "<br><table style=\"border: 1px solid black;\"><tr><td style=\"border: 1px solid black;\"><span>UHID</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>NEO Box Name</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Total Points</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Data Points Received</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Percentage</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>HR Count</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>PR Count</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>SPO2 Count</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>RR Count</span></td></tr>";

				for (VitalTracker vitalObj : vitalList) {
					double percentage = 0;
					if(vitalObj.getNumberOfMinutes() != 0) {
						percentage = ((vitalObj.getVitalTotalCount() * 100.0) / (double) vitalObj.getNumberOfMinutes());
					}
					
					DecimalFormat df2 = new DecimalFormat(".##");
					if(percentage != 0) {
						percentage = Double.parseDouble(df2.format(percentage));
					}

					String neoBoxName=vitalObj.getBoxName();
					
					//Update box name if its empty
					if(neoBoxName == null || neoBoxName == "") {
						String babyStatusQuery = "select obj from BabyDetail as obj where uhid='" + vitalObj.getUhid() + "' order by creationtime desc";
						List<BabyDetail> babyStatusList = inicuDao.getListFromMappedObjQuery(babyStatusQuery);
						if(!BasicUtils.isEmpty(babyStatusList)) {
							BabyDetail babyStatusObj = babyStatusList.get(0);
							if(babyStatusObj.getAdmissionstatus() != null) {
								if(babyStatusObj.getAdmissionstatus()) {
									neoBoxName = "Disconnected";
								}
							}
						}
					}
					
					if(percentage < 75) {
						troubledBabies++;
						String detailedMessageData = "<tr><td style=\"border: 1px solid black;\"><span>" + vitalObj.getUhid()
						+ "</span></td>";
						detailedMessageData += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getBabyname()
						+ "</span></td>";
						
						detailedMessageData += "<td style=\"border: 1px solid black;\"><span>" + neoBoxName
						+ "</span></td></tr>";
						detailedMessageHeader += detailedMessageData;
					}
					
					String commonRow = "<tr><td style=\"border: 1px solid black;\"><span>" + vitalObj.getUhid()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getBabyname()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + neoBoxName
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getNumberOfMinutes()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getVitalTotalCount()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + percentage + "%"
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getHrCount()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getPrCount()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getSpo2Count()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getRrCount()
							+ "</span></td></tr>";
					
					vitalTable += commonRow;

				}
				vitalTable += "</table>";
				detailedMessageHeader += "</table>";
				
				message += "<br><br>";
				message += "<b> Vital Tracker Sheet: </b>";
				message += "<br>";

				if(troubledBabies > 0) {
					message += "<br> Out of " + vitalList.size() + " babies " + "data for " + troubledBabies + " babies are not coming efficiently."
								+ "<br><br> Here is the list for those babies: <br>" + detailedMessageHeader + "<br><br>" +
								"Complete data as follows : <br>" + vitalTable;
				}
				else {
					message += vitalTable;
				}
				message += "<br><br>";
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Sending scheduled Vital Tracker Mail", BasicUtils.convertErrorStacktoString(e));
		}
		return message;
	}
	
	public String procedureEmailNotification(String branchName) {
		String message = "";

		try {
			List<String> babyUhidList = inicuDao.getListFromNativeQuery(HqlSqlQueryConstants.getAdmitBabyUHIDList(branchName));
			String uhidStr = "";
			for (int i = 0; i < babyUhidList.size(); i++) {
				if (uhidStr.isEmpty()) {
					uhidStr += "'" + babyUhidList.get(i) + "'";
				} else {
					uhidStr += ", '" + babyUhidList.get(i) + "'";
				}
			}

			Date current = new Date();
			current.setHours(9);
			current.setMinutes(0);
			int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
			Timestamp insertionLimit = new Timestamp(current.getTime() + offset);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a");

			List<PeripheralCannula> cannulaList = inicuDao.getListFromMappedObjQuery(
					HqlSqlQueryConstants.getProcedureCannulaEmailList(uhidStr, insertionLimit));
			if (!BasicUtils.isEmpty(cannulaList)) {
				
				message += "<b> Daily Procedure Report: </b>";
				
				message += "<br><br><b>Peripheral Cannula: </b><br><br><table style=\"border: 1px solid black;\">"
						+ "<tr><td style=\"border: 1px solid black;width:60px\"><span>Sr. No. </span></td>"
						+ "<td style=\"border: 1px solid black;width:150px\"><span>UHID</span></td>"
						+ "<td style=\"border: 1px solid black;width:100px\"><span>Site</span></td>"
						+ "<td style=\"border: 1px solid black;width:50px\"><span>Size</span></td>"
						+ "<td style=\"border: 1px solid black;width:250px\"><span>Insertion Time</span></td>"
						+ "<td style=\"border: 1px solid black;width:150px\"><span>Duration (Hrs)</span></td></tr>";

				for (int i = 0; i < cannulaList.size(); i++) {
					PeripheralCannula cannulaObj = cannulaList.get(i);
					message += "<tr><td style=\"border: 1px solid black;\"><span>" + (i + 1) + "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + cannulaObj.getUhid()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>"
							+ cannulaObj.getSite().replace("'", "") + "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + cannulaObj.getSize()
							+ "</span></td>";

					Timestamp insertionTime = new Timestamp(cannulaObj.getInsertion_timestamp().getTime() + offset);
					long duration = (insertionLimit.getTime() - insertionTime.getTime()) / (60 * 60 * 1000);

					message += "<td style=\"border: 1px solid black;\"><span>" + sdf.format(insertionTime)
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + duration + "</span></td></tr>";
				}
				message += "</table>";
			}

			List<CentralLine> centralLineList = inicuDao.getListFromMappedObjQuery(
					HqlSqlQueryConstants.getProcedureCentralLineEmailList(uhidStr, insertionLimit));
			if (!BasicUtils.isEmpty(centralLineList)) {
				message += "<br><br><b>Central Line: </b><br><br><table style=\"border: 1px solid black;\">"
						+ "<tr><td style=\"border: 1px solid black;width:60px\"><span>Sr. No. </span></td>"
						+ "<td style=\"border: 1px solid black;width:150px\"><span>UHID</span></td>"
						+ "<td style=\"border: 1px solid black;width:100px\"><span>Site</span></td>"
						+ "<td style=\"border: 1px solid black;width:50px\"><span>Size</span></td>"
						+ "<td style=\"border: 1px solid black;width:250px\"><span>Insertion Time</span></td>"
						+ "<td style=\"border: 1px solid black;width:150px\"><span>Duration (Hrs)</span></td></tr>";

				for (int i = 0; i < centralLineList.size(); i++) {
					CentralLine centralLineObj = centralLineList.get(i);
					message += "<tr><td style=\"border: 1px solid black;\"><span>" + (i + 1) + "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + centralLineObj.getUhid()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>"
							+ centralLineObj.getSite().replace("'", "") + "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + centralLineObj.getSize()
							+ "</span></td>";

					Timestamp insertionTime = new Timestamp(
							centralLineObj.getInsertion_timestamp().getTime() + offset);
					long duration = (insertionLimit.getTime() - insertionTime.getTime()) / (60 * 60 * 1000);

					message += "<td style=\"border: 1px solid black;\"><span>" + sdf.format(insertionTime)
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + duration + "</span></td></tr>";

				}
				message += "</table>";
				message += "<br><br>";

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Sending scheduled Procedures Report Mail", BasicUtils.convertErrorStacktoString(e));
		}
		return message;
	}
	
	public String sinEmailNotification(String branchName) {
		String message = "";

		try {
			// will configure time-zone with DB
			Date current = new Date();
			current.setHours(9);
			current.setMinutes(0);
			int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
			long fromTime = current.getTime() + offset;

			String fromDateStr = "" + fromTime;
			String toDateStr = "" + (fromTime + BasicConstants.DAY_VALUE);

			List<SinJSON> sinList = analyticsObj.getSinSheetData(fromDateStr, toDateStr, branchName);

			if (!BasicUtils.isEmpty(sinList)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				message += "<b> Daily SIN Sheet Record: </b>";
				
				message += "<br><br><table style=\"border: 1px solid black;\"><tr><td style=\"border: 1px solid black;\"><span>Date</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Number of Babies</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies on IV Antibiotics</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies on non-Invasive Ventilator</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies on all forms of Ventilators</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies on Central Catheters</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies with Peripheral Cannula</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies on PN</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Babies on IV Fluids</span></td></tr>";

				for (SinJSON sinObj : sinList) {
					message += "<tr><td style=\"border: 1px solid black;\"><span>"
							+ sdf.format(sinObj.getDataDate()) + "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getNoOfBabies().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getIvAntibiotics().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getNonInvasiveVentilator().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getTotalVentilator().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getCentralLine().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getPeripheralCannula().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getPnFeed().getCount()
							+ "</span></td>";
					message += "<td style=\"border: 1px solid black;\"><span>" + sinObj.getIvFeed().getCount()
							+ "</span></td></tr>";
				}
				message += "</table>";

				message += "<br><br>";
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Sending scheduled SIN Sheet Mail", BasicUtils.convertErrorStacktoString(e));
		}
		return message;
	}
	
	public String usageEmailNotification(String branchName) {
		String message = "";
		
		try {
			// will configure time-zone with DB
			Date current = new Date();
			current.setHours(8);
			current.setMinutes(0);
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			System.out.println(offset);
			long fromTime = current.getTime() - offset;

			String fromDateStr = "" + (fromTime - BasicConstants.DAY_VALUE);
			String toDateStr = "" + (fromTime);

			List<AnalyticsUsagePojo> usageList = analyticsObj.getAnalyticsUsage(fromDateStr, toDateStr, branchName);

			if (!BasicUtils.isEmpty(usageList)) {
				String docTable = "";
				String nurseTable = "";

				String commonTable = "<br><table style=\"border: 1px solid black;\"><tr><td style=\"border: 1px solid black;\"><span>UHID</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>NICU Level</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Admission Status</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Length of Stay</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Criticality</span></td>";

				docTable += "Doctor Usage" + commonTable
						+ "<td style=\"border: 1px solid black;\"><span>Initial Assessment</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Systemic Assessments</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Stable Notes</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Medication</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Nutrition</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Lab Order</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Procedure</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Neonatologist</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Adoption Score</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Quality Score</span></td></tr>";

				nurseTable += "Nurse Usage" + commonTable
						+ "<td style=\"border: 1px solid black;\"><span>Anthropometry</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Vitals</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Intake Output</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Events</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Sample Sent</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Adoption Score</span></td>"
						+ "<td style=\"border: 1px solid black;\"><span>Quality Score</span></td></tr>";

				for (AnalyticsUsagePojo usageObj : usageList) {
					String commonRow = "<tr><td style=\"border: 1px solid black;\"><span>" + usageObj.getUhid()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getBabyName()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getNicuLevel()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getAdmissionStatus()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getLengthOfStay()
							+ "</span></td>";
					commonRow += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getCriticality()
							+ "</span></td>";

					docTable += commonRow;
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getInitialAssessment()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getAssessment()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getStableNotes()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getMedication()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getNutrition()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getLabOrder()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getProcedure()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getNeonatologist()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getDoctor_adoptionScore()
							+ "</span></td>";
					docTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getDoctor_qualityScore()
							+ "</span></td></tr>";

					nurseTable += commonRow;
					nurseTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getAnthropometry()
							+ "</span></td>";
					nurseTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getVitals()
							+ "</span></td>";
					nurseTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getIntake_output()
							+ "</span></td>";
					nurseTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getEvents()
							+ "</span></td>";
					nurseTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getSample_sent()
							+ "</span></td>";
					nurseTable += "<td style=\"border: 1px solid black;\"><span>"
							+ usageObj.getNurse_adoptionScore() + "</span></td>";
					nurseTable += "<td style=\"border: 1px solid black;\"><span>" + usageObj.getNurse_qualityScore()
							+ "</span></td></tr>";

				}
				docTable += "</table>";
				nurseTable += "</table>";

				message += "<br>";
				message += "<b> Daily Usage Sheet Record: </b>";
				message += "<br>";
				message += "<br>";

				message += docTable + "<br><br>" + nurseTable + "<br><br>";
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Sending scheduled Usage Sheet Mail", BasicUtils.convertErrorStacktoString(e));
		}
		return message;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		System.out.println("in VitalTrackerNotification");

		List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());
		if(!BasicUtils.isEmpty(branchNameList)) {
			for(String branchName : branchNameList) {
	
				List<NotificationEmail> emailList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNotificationEmailList() + " where isactive ='true' and branchname = '" + branchName + "'");
				if (!BasicUtils.isEmpty(emailList)) {
					try {
							String message = "";
							
							message = usageEmailNotification(branchName);
							message += sinEmailNotification(branchName);
							message += procedureEmailNotification(branchName);
							message += vitalTrackerNotification(branchName);

							HashMap<Message.RecipientType, List<String>> emailMap = new HashMap<Message.RecipientType, List<String>>() {
								private static final long serialVersionUID = 1L;
								{
									put(Message.RecipientType.TO, new ArrayList<String>());
									put(Message.RecipientType.CC, new ArrayList<String>());
								}
							};
		
							for (NotificationEmail item : emailList) {
								if (item.getEmail_type()) {
									emailMap.get(Message.RecipientType.TO).add(item.getUser_email());
								} else {
									emailMap.get(Message.RecipientType.CC).add(item.getUser_email());
								}
							}
							
							String fullHospitalName = BasicConstants.COMPANY_ID + " ( " + branchName + " )";
		
							BasicUtils.sendMailWithMultipleType(emailMap, message,
									"Data Outcome Summary", fullHospitalName);
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
								"Sending scheduled Vital Tracker Mail", BasicUtils.convertErrorStacktoString(e));
					}
				}
			}
		}

		RemotedbConnector connector = null;
		try {
			connector = new RemotedbConnector(false);
		} catch (Exception e1) {
			System.out.println("Unable to create database connector ");
			e1.printStackTrace();
		}

		// To log HIS views.
		try {
			String query = "";
			String lineSeparator = System.getProperty("line.separator");
			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
				query = "select * from PA_Adm a, ct_loc b, rbc_departmentgroup c, ct_careprov d, pa_person e, pa_patmas f where  a.Paadm_depcode_dr=b.ctloc_rowid and b.ctloc_dep_dr=c.dep_rowid And  a.paadm_admdoccodedr=d.ctpcp_rowid1 and a.Paadm_Papmi_dr=e.paper_rowid and a. paadm_papmi_dr=f.papmi_rowid1 and a.paadm_currentward_dr->ward_code='WD-SWB-NURSERY' limit 100";
			} else {
				query = "select * from " + BasicConstants.HIS_DISCHARGE_VIEW + " limit 100";
			}

			ResultSet resultSet = connector.executePostgresSelectQuery(query);

			if (resultSet != null) {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				StringBuffer sb = null;
				while (resultSet.next()) {
					sb = new StringBuffer();
					//Print one row
					for(int i = 1 ; i <= columnsNumber; i++){
						// Print one element of a row
						String columnValue = resultSet.getString(i);
						sb.append(rsmd.getColumnName(i) + " : " + columnValue + ",  ");
					}

					logger.info(sb.toString(), lineSeparator);
					sb = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
