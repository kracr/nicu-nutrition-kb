package com.inicu.notification;

import java.sql.Timestamp;
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

import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.CentralLine;
import com.inicu.postgres.entities.NotificationEmail;
import com.inicu.postgres.entities.PeripheralCannula;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class ProcedureEmailNotification implements Runnable {

	@Autowired
	InicuDao inicuDao;

	@Autowired
	InicuDatabaseExeption databaseException;
	
	@Autowired
	UserServiceDAO userServiceDao;

	public ProcedureEmailNotification() {
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		System.out.println("in ProcedureEmailNotification");
		List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());
		if(!BasicUtils.isEmpty(branchNameList)) {
			for(String branchName : branchNameList) {
	
				//Here also branch name
				List<String> babyUhidList = inicuDao.getListFromNativeQuery(HqlSqlQueryConstants.getAdmitBabyUHIDList(branchName));
				List<NotificationEmail> emailList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNotificationEmailList() + " where isactive ='true' and branchname = '" + branchName + "'");
				if (!(BasicUtils.isEmpty(emailList) || BasicUtils.isEmpty(babyUhidList))) {
					try {
						String message = "";
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
						}
		
						message = "<b>Total Baby Present: " + babyUhidList.size() + "\t\t Peripheral Cannula: "
								+ cannulaList.size() + "\t\t Central Line: " + centralLineList.size() + "</b>" + message;
		
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
						BasicUtils.sendMailWithMultipleType(emailMap, message, "Daily Procedure Report",
								fullHospitalName);
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
								"Sending scheduled Procedures Report Mail", BasicUtils.convertErrorStacktoString(e));
					}
				}
			}
		}
	}

}
