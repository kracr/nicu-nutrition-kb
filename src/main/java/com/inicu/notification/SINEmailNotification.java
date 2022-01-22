package com.inicu.notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.SinJSON;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.NotificationEmail;
import com.inicu.postgres.serviceImpl.AnalyticsServiceImpl;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class SINEmailNotification implements Runnable {

	@Autowired
	AnalyticsServiceImpl analyticsObj;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	InicuDatabaseExeption databaseException;
	
	@Autowired
	UserServiceDAO userServiceDao;

	public SINEmailNotification() {
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		System.out.println("in SINEmailNotification");
		
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
		
						String fromDateStr = "" + fromTime;
						String toDateStr = "" + (fromTime + BasicConstants.DAY_VALUE);
		
						List<SinJSON> sinList = analyticsObj.getSinSheetData(fromDateStr, toDateStr, branchName);
		
						if (!BasicUtils.isEmpty(sinList)) {
							String message = "";
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
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
		
							BasicUtils.sendMailWithMultipleType(emailMap, message, "Daily SIN Sheet Record",
									fullHospitalName);
						}
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
								"Sending scheduled SIN Sheet Mail", BasicUtils.convertErrorStacktoString(e));
					}
				}
			}
		}
	}

}
