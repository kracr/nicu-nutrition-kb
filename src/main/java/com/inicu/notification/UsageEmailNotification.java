package com.inicu.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.AnalyticsUsagePojo;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.NotificationEmail;
import com.inicu.postgres.serviceImpl.AnalyticsServiceImpl;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class UsageEmailNotification implements Runnable {

	@Autowired
	AnalyticsServiceImpl analyticsObj;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	InicuDatabaseExeption databaseException;
	
	@Autowired
	UserServiceDAO userServiceDao;

	public UsageEmailNotification() {
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		System.out.println("in UsageEmailNotification");
		List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());

		if(!BasicUtils.isEmpty(branchNameList)) {
			for(String branchName : branchNameList) {
	
				List<NotificationEmail> emailList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNotificationEmailList() + " where isactive ='true' and branchname = '" + branchName + "'");
				if (!BasicUtils.isEmpty(emailList)) {
					try {
						// will configure time-zone with DB
						Date current = new Date();
						current.setHours(9);
						current.setMinutes(0);
						int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
						long fromTime = current.getTime() + offset;
		
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

							BasicUtils.sendMailWithMultipleType(emailMap, docTable + "<br><br>" + nurseTable,
									"Daily Usage Sheet Record", fullHospitalName);
						}
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
								"Sending scheduled Usage Sheet Mail", BasicUtils.convertErrorStacktoString(e));
					}
				}
			}
		}
	}

}
