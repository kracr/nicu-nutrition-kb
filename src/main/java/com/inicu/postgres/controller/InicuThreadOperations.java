package com.inicu.postgres.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaRespApnea;
import com.inicu.postgres.entities.SaRespsystem;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@RestController
public class InicuThreadOperations extends Thread {

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired InicuPostgresUtililty inicuPostgres;

	@Override
	public void run() {

		boolean running = true;
		while (running) {
			try {
				int threadSleepTime  = assessmentInactiveOperation();
				Thread.sleep(threadSleepTime);
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
						BasicConstants.LOGGED_USER_ID, "", "Inicu Thread Operation", BasicUtils.convertErrorStacktoString(e));
			}
		}
	}

	public int assessmentInactiveOperation() throws Exception {
		int threadSleepTime  = 0;
		 java.sql.Date yesterdayDate = new java.sql.Date(new java.util.Date().getTime() - 24*3600*1000);
		 java.sql.Date todayDate = new java.sql.Date(new java.util.Date().getTime());
		 Calendar calToday  = Calendar.getInstance();
		 
		 
			 Calendar calStandard = Calendar.getInstance();
			 calStandard.set(Calendar.DAY_OF_MONTH, calStandard.get(Calendar.DAY_OF_MONTH)+1);
			 calStandard.set(Calendar.HOUR, 8);
			 calStandard.set(Calendar.MINUTE, 00);
			 calStandard.set(Calendar.SECOND, 00);
			 calStandard.set(Calendar.AM_PM, Calendar.AM);
			 System.out.println(calStandard.getTime());
			 long diffHours = (calStandard.getTime().getTime() - calToday.getTime().getTime())/(3600*1000);
			long  diffMinutes = diffHours * 60;
			long diffSeconds = diffMinutes * 60;
			
			threadSleepTime = (int) diffSeconds * 1000;		 
		 
		  
		 String queryUpdateInactiveJaundice = "select obj from SaJaundice obj where to_char(creationtime,'yyyy-MM-dd') ='"+yesterdayDate+"'"
					+ "and jaundicestatus='Inactive' and obj.uhid not in (select objToday.uhid from SaJaundice objToday where to_char(creationtime,'yyyy-MM-dd') ='"+todayDate+"'"
					+ "and jaundicestatus='Inactive')";
			List<SaJaundice> jaundiceInactiveList = inicuPostgres.executeMappedObjectCustomizedQuery(queryUpdateInactiveJaundice);
			
		for(SaJaundice jaundice : jaundiceInactiveList){
			//check if the baby is not discharge.......
			String queryBabyDischrageCheck  = "select obj from BabyDetail obj where uhid='"+jaundice.getUhid()+"' and admissionstatus='true'";
					List<BabyDetail> babyDetail = inicuPostgres.executeMappedObjectCustomizedQuery(queryBabyDischrageCheck); 
			
					if(!BasicUtils.isEmpty(babyDetail)){
						Timestamp creationTime = jaundice.getCreationtime();
						Timestamp currentTimeStamp = new Timestamp(new java.util.Date().getTime());
						Long diff = (currentTimeStamp.getTime() - creationTime.getTime())/(3600 * 1000);

						jaundice.setSajaundiceid(null);
						jaundice.setCreationtime(null);
						jaundice.setModificationtime(null);
						jaundice.setAgeofonset(jaundice.getAgeofonset() + Integer.valueOf(diff.toString()));
						inicuPostgres.saveObject(jaundice);
					}
		}
		
		//for respiratory distress inactive use case...
		String queryUpdateInactiverds = "select obj from SaRespsystem obj where to_char(creationtime,'yyyy-MM-dd') ='"+yesterdayDate+"'"
				+ "and eventstatus='Inactive' and obj.uhid not in (select objToday.uhid from SaRespsystem objToday where to_char(creationtime,'yyyy-MM-dd') ='"+todayDate+"'"
				+ "and eventstatus='Inactive')";
		List<SaRespsystem> rdsInactiveList = inicuPostgres.executeMappedObjectCustomizedQuery(queryUpdateInactiverds);

		for(SaRespsystem rds : rdsInactiveList){
			//check if the baby is not discharge.......
			String queryBabyDischrageCheck  = "select obj from BabyDetail obj where uhid='"+rds.getUhid()+"' and admissionstatus='true'";
			List<BabyDetail> babyDetail = inicuPostgres.executeMappedObjectCustomizedQuery(queryBabyDischrageCheck); 

			if(!BasicUtils.isEmpty(babyDetail)){
				Timestamp creationTime = rds.getCreationtime();
				Timestamp currentTimeStamp = new Timestamp(new java.util.Date().getTime());
				Long diff = (currentTimeStamp.getTime() - creationTime.getTime())/(3600 * 1000);

				rds.setCreationtime(null);
				rds.setRespid(null);
				rds.setAgeatonset(Integer.valueOf(rds.getAgeatonset()) + Integer.valueOf(diff.toString())+"");
				inicuPostgres.saveObject(rds);
			}
		}
		
		//for respiratory sytem apnea inactive use case...
				String queryUpdateInactiveApena = "select obj from SaRespApnea obj where to_char(creationtime,'yyyy-MM-dd') ='"+yesterdayDate+"'"
						+ "and eventstatus='Inactive' and obj.uhid not in (select objToday.uhid from SaRespApnea objToday where to_char(creationtime,'yyyy-MM-dd') ='"+todayDate+"'"
						+ "and eventstatus='Inactive')";
				List<SaRespApnea> apneaInactiveList = inicuPostgres.executeMappedObjectCustomizedQuery(queryUpdateInactiveApena);

				for(SaRespApnea apnea : apneaInactiveList){
					//check if the baby is not discharge.......
					String queryBabyDischrageCheck  = "select obj from BabyDetail obj where uhid='"+apnea.getUhid()+"' and admissionstatus='true'";
					List<BabyDetail> babyDetail = inicuPostgres.executeMappedObjectCustomizedQuery(queryBabyDischrageCheck); 

					if(!BasicUtils.isEmpty(babyDetail)){
						Timestamp creationTime = apnea.getCreationtime();
						Timestamp currentTimeStamp = new Timestamp(new java.util.Date().getTime());
						Long diff = (currentTimeStamp.getTime() - creationTime.getTime())/(3600 * 1000);

						apnea.setCreationtime(null);
						apnea.setApneaid(null);
						apnea.setAgeatonset(Integer.valueOf(apnea.getAgeatonset()) + Integer.valueOf(diff.toString())+"");
						inicuPostgres.saveObject(apnea);
					}
				}

				return threadSleepTime;
	}
	
	
	
	
}
