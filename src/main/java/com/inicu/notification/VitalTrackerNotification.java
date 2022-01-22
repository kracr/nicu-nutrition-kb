package com.inicu.notification;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.VitalTracker;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.NotificationEmail;
import com.inicu.postgres.serviceImpl.AnalyticsServiceImpl;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class VitalTrackerNotification implements Runnable {
	
	@Autowired
	AnalyticsServiceImpl analyticsObj;
	
	@Autowired
	InicuDao inicuDao;
	
	@Autowired
	InicuDatabaseExeption databaseException;
	
	@Autowired
	UserServiceDAO userServiceDao;

	public VitalTrackerNotification() {
		super();
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
						// will configure time-zone with DB
						Date current = new Date();
						current.setHours(8);
						current.setMinutes(0);
						int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
						long toTime = current.getTime() - offset;
						long fromTime = toTime - (24 * 60 * 60 * 1000);
		
						String fromDateStr = "" + (fromTime);
						String toDateStr = "" + (toTime);
						
						String message = "";
						String detailedMessageHeader = "<br><table style=\"border: 1px solid black;\"><tr><td style=\"border: 1px solid black;\"><span>UHID</span></td>"
								+ "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
								+ "<td style=\"border: 1px solid black;\"><span>NEO Box Name</span></td></tr>";
						Integer troubledBabies = 0;
		;
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
								
								if(percentage < 75) {
									troubledBabies++;
									String detailedMessageData = "<tr><td style=\"border: 1px solid black;\"><span>" + vitalObj.getUhid()
									+ "</span></td>";
									detailedMessageData += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getBabyname()
									+ "</span></td>";
									detailedMessageData += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getBoxName()
									+ "</span></td></tr>";
									detailedMessageHeader += detailedMessageData;
								}
								String commonRow = "<tr><td style=\"border: 1px solid black;\"><span>" + vitalObj.getUhid()
										+ "</span></td>";
								commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getBabyname()
										+ "</span></td>";
								commonRow += "<td style=\"border: 1px solid black;\"><span>" + vitalObj.getBoxName()
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
							
							if(troubledBabies > 0) {
								message = "<br> Out of " + vitalList.size() + " babies " + "data for " + troubledBabies + " babies are not coming efficiently."
											+ "<br><br> Here is the list for those babies: <br>" + detailedMessageHeader + "<br><br>" +
											"Complete data as follows : <br>" + vitalTable;
							}
							else {
								message = vitalTable;
							}
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
									"Vital Tracker Sheet", fullHospitalName);
						}
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
								"Sending scheduled Vital Tracker Mail", BasicUtils.convertErrorStacktoString(e));
					}
				}
			}
		}
	}
}
