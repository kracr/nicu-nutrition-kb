package com.inicu.postgres.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.serviceImpl.DeviceDataServiceImpl;
import com.inicu.models.DeviceGraphDataJson;
import com.inicu.models.VwAssesmentRespsystemFinal;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.PrescriptionDao;
import com.inicu.postgres.daoImpl.InicuDaoImpl;
import com.inicu.postgres.entities.AdmissionNotes;
import com.inicu.postgres.entities.AntenatalHistoryDetail;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BirthToNicu;
import com.inicu.postgres.entities.DeviceMonitorDetail;
import com.inicu.postgres.entities.HMObservableStatus;
import com.inicu.postgres.entities.NursingBloodGas;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaCnsAsphyxia;
import com.inicu.postgres.entities.SaCnsSeizures;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaRespApnea;
import com.inicu.postgres.entities.SaRespPneumo;
import com.inicu.postgres.entities.SaRespPphn;
import com.inicu.postgres.entities.SaRespRds;
import com.inicu.postgres.entities.SaSepsis;
import com.inicu.postgres.entities.ScreenRop;
import com.inicu.postgres.entities.ScreenUSG;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;

@Repository
public class HMLM extends Thread {

	@Autowired
	PrescriptionDao prescriptionDao;

	@Autowired
	PatientDao patientDao;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	PatientService patientService;

	@Autowired
	TestsService testsService;

	@Autowired
	DeviceDataServiceImpl deviceDataServiceImpl;
	
	
	public TreeMap<Timestamp, String> getfirstHmIncidenceTime(DeviceGraphDataJson graphData, String entryDate, String endDate) {
		// TODO Auto-generated method stub
		/*
		 * TimeZone.setDefault(TimeZone.getTimeZone(CalendarUtility.
		 * INDIA_TIME_ZONE_ID));
		 */


		DeviceGraphDataJson obj = new DeviceGraphDataJson();
		List<Timestamp> fromHmTime = new ArrayList<Timestamp>();
		List<Timestamp> toHmTime = new ArrayList<Timestamp>();
		TreeMap<Timestamp, String> hmDetails = new TreeMap<Timestamp, String>();
		
		if (!entryDate.equalsIgnoreCase("undefined")) {
			if (!BasicUtils.isEmpty(graphData)) {
				String uhid = graphData.getUhid();
				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();

				System.out.println(offset + " off");
				System.out.println(entryDate);
				try {

					Date entryTimeStamp = CalendarUtility
							.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION).parse(entryDate);

					Date entryEndTimeStamp = CalendarUtility
							.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION).parse(endDate);
					System.out.println(entryTimeStamp);
					System.out.println(entryEndTimeStamp);


					System.out.println(entryTimeStamp);
					System.out.println(entryEndTimeStamp);
					System.out.println(entryTimeStamp);
					System.out.println(entryEndTimeStamp);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
					String entryTimeServerFormat = formatter.format(entryTimeStamp);
					String entryEndTimeServerFormat = formatter.format(entryEndTimeStamp);
					System.out.println(entryTimeServerFormat);
					System.out.println(entryEndTimeServerFormat);
					if (!BasicUtils.isEmpty(entryDate) && !BasicUtils.isEmpty(endDate)) {
						List<List<Object>> hmlmlFinalData = new ArrayList<>();
						if(graphData.isHmOutput()) {

							obj.setHmOutput(graphData.isHmOutput());
							//Blood Culture and NEC

							Timestamp fromNECTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toNECTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isNECStatus = false;

							Timestamp fromBloodCultureTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toBloodCultureTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isBloodCultureStatus = false;
							int sepsisStatus = -1;
							List<SaSepsis> sepsisSet = new ArrayList<>();
							String sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim()
							+ "' and assessment_time <= '" + entryTimeServerFormat + "' order by assessment_time desc";
							List<SaSepsis> sepsisSetTemp = patientDao.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
//							if(!BasicUtils.isEmpty(sepsisSetTemp)) {
//								if(sepsisSetTemp.get(0).getEventstatus().equalsIgnoreCase("yes")){
//									sepsisStatus = 1;
//								}
//								else {
//									sepsisStatus = 0;
//								}
//							}

							sepsisAssessmentQuery = "SELECT obj FROM SaSepsis as obj where uhid='" + uhid.trim() 
							+ "' and assessment_time >= '" + entryTimeServerFormat + "' and assessment_time <= '" + entryEndTimeServerFormat + "' order by assessment_time";    
							sepsisSetTemp = patientDao.getListFromMappedObjNativeQuery(sepsisAssessmentQuery);
							if(!BasicUtils.isEmpty(sepsisSetTemp))
								sepsisSet.addAll(sepsisSetTemp);

							if(!BasicUtils.isEmpty(sepsisSet)) {
								for(SaSepsis sasepsisObj : sepsisSet) {
									if(!(sasepsisObj.getEventstatus().equalsIgnoreCase("yes")) && sepsisStatus == 1) {
										sepsisStatus = 0;
										if(isNECStatus) {
											toNECTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
											isNECStatus = false;
											fromHmTime.add(new Timestamp(fromNECTime.getTime()));
											toHmTime.add(new Timestamp(toNECTime.getTime()));
										}
										if(isBloodCultureStatus) {
											toBloodCultureTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
											isBloodCultureStatus = false;
											fromHmTime.add(new Timestamp(fromBloodCultureTime.getTime()));
											toHmTime.add(new Timestamp(toBloodCultureTime.getTime()));
										}

									}
									else if((sasepsisObj.getEventstatus().equalsIgnoreCase("yes")) && (sepsisStatus == 0 || sepsisStatus == -1)) {
										sepsisStatus = 1;
									}
									if(!BasicUtils.isEmpty(sasepsisObj.getNecStatus()) && sasepsisObj.getNecStatus() && (sasepsisObj.getEventstatus().equalsIgnoreCase("yes"))){
										fromNECTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
										isNECStatus = true;
										
										hmDetails.put(fromNECTime, "NEC");								
										
									}
									if(!BasicUtils.isEmpty(sasepsisObj.getBloodCultureStatus()) && sasepsisObj.getBloodCultureStatus().equalsIgnoreCase("Positive") && (sasepsisObj.getEventstatus().equalsIgnoreCase("yes"))){
										fromBloodCultureTime = new Timestamp(sasepsisObj.getAssessmentTime().getTime());
										isBloodCultureStatus = true;
										hmDetails.put(fromBloodCultureTime, "BloodCulture");
									}

								}
							}
							if(isNECStatus) {
								toNECTime = new Timestamp(entryEndTimeStamp.getTime());
								isNECStatus = false;
								fromHmTime.add(new Timestamp(fromNECTime.getTime()));
								toHmTime.add(new Timestamp(toNECTime.getTime()));
							}
							if(isBloodCultureStatus) {
								toBloodCultureTime = new Timestamp(entryEndTimeStamp.getTime());
								isBloodCultureStatus = false;
								fromHmTime.add(new Timestamp(fromBloodCultureTime.getTime()));
								toHmTime.add(new Timestamp(toBloodCultureTime.getTime()));
							}

							//Surfactant and Ventilation
							Timestamp fromSurfactantTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toSurfactantTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isSurfactantStatus = false;

							Timestamp fromVentilationTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toVentilationTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isVentilationStatus = false;

							String respSupportQuery = "SELECT obj FROM RespSupport as obj where uhid='" + uhid.trim()+"' and creationtime>='"+entryTimeServerFormat
							+ "' order by creationtime";
							List<RespSupport> ventSetTemp = patientDao.getListFromMappedObjNativeQuery(respSupportQuery);
							if(!BasicUtils.isEmpty(ventSetTemp)) {
								for(RespSupport supportObj : ventSetTemp) {
									if(!isVentilationStatus && supportObj.getIsactive() && !BasicUtils.isEmpty(supportObj.getRsVentType()) && (supportObj.getRsVentType().equalsIgnoreCase("Mechanical Ventilation") || supportObj.getRsVentType().equalsIgnoreCase("HFO"))) {
										fromVentilationTime = new Timestamp(supportObj.getCreationtime().getTime());
										isVentilationStatus = true;
										hmDetails.put(fromVentilationTime, "Invasive Ventilation");
										
									}else if(isVentilationStatus && (!supportObj.getIsactive() || ((!BasicUtils.isEmpty(supportObj.getRsVentType())) && (supportObj.getRsVentType().equalsIgnoreCase("CPAP") || supportObj.getRsVentType().equalsIgnoreCase("High Flow O2") || supportObj.getRsVentType().equalsIgnoreCase("NIMV") || supportObj.getRsVentType().equalsIgnoreCase("Low Flow O2"))) || BasicUtils.isEmpty(supportObj.getRsVentType()))) {
										toVentilationTime = new Timestamp(supportObj.getCreationtime().getTime());
										isVentilationStatus = false;
										fromHmTime.add(new Timestamp(fromVentilationTime.getTime()));
										toHmTime.add(new Timestamp(toVentilationTime.getTime()));
									}
								}
							}

							List<SaRespRds> surfactantSet = new ArrayList<>();
							int sufactantStatus = -1;
							String surfactantAssessmentQuery = "SELECT obj FROM SaRespRds as obj where uhid='" + uhid.trim() 
							+ "' and assessment_time >= '" + entryTimeServerFormat + "' and assessment_time <= '" + entryEndTimeServerFormat + "' order by assessment_time";    
							List<SaRespRds> surfactantSetTemp = patientDao.getListFromMappedObjNativeQuery(surfactantAssessmentQuery);
							if(!BasicUtils.isEmpty(surfactantSetTemp))
								surfactantSet.addAll(surfactantSetTemp);

							if(!BasicUtils.isEmpty(surfactantSet)) {
								for(SaRespRds rdsObject : surfactantSet) {
									if(!(rdsObject.getEventstatus().equalsIgnoreCase("Yes")) && sufactantStatus == 1) {
										sufactantStatus = 0;
										if(isSurfactantStatus) {
											toSurfactantTime = new Timestamp(rdsObject.getAssessmentTime().getTime());
											isSurfactantStatus = false;
											fromHmTime.add(new Timestamp(fromSurfactantTime.getTime()));
											toHmTime.add(new Timestamp(toSurfactantTime.getTime()));
										}

									}
									else if((rdsObject.getEventstatus().equalsIgnoreCase("Yes")) && (sufactantStatus == 0 || sufactantStatus == -1)) {
										sufactantStatus = 1;
									}
									if((rdsObject.getEventstatus().equalsIgnoreCase("Yes")) && !BasicUtils.isEmpty(rdsObject.getSufactantname())){
										fromSurfactantTime = new Timestamp(rdsObject.getAssessmentTime().getTime());
										isSurfactantStatus = true;
										hmDetails.put(fromSurfactantTime, "Surfactant");
										
									}


								}
							}

							//ROP
							String ropAssessmentQuery = "SELECT obj FROM ScreenRop as obj where uhid='" + uhid.trim() 
							+ "' and screening_time >= '" + entryTimeServerFormat + "' and screening_time <= '" + entryEndTimeServerFormat + "' order by screening_time";   
							List<ScreenRop> ropSet = patientDao.getListFromMappedObjNativeQuery(ropAssessmentQuery);

							if(!BasicUtils.isEmpty(ropSet)) {
								for(ScreenRop ropObj : ropSet) {
									if(!BasicUtils.isEmpty(ropObj.getRop_left_stage()) && ((ropObj.getRop_left_stage().equalsIgnoreCase("II")) || (ropObj.getRop_left_stage().equalsIgnoreCase("III")) || (ropObj.getRop_left_stage().equalsIgnoreCase("IV")) || (ropObj.getRop_left_stage().equalsIgnoreCase("V")))
											|| (!BasicUtils.isEmpty(ropObj.getRop_right_stage()) && ((ropObj.getRop_right_stage().equalsIgnoreCase("II")) || (ropObj.getRop_right_stage().equalsIgnoreCase("III")) || (ropObj.getRop_right_stage().equalsIgnoreCase("IV")) || (ropObj.getRop_right_stage().equalsIgnoreCase("V"))))){
										fromHmTime.add(new Timestamp(ropObj.getScreening_time().getTime()));
										toHmTime.add(new Timestamp(entryEndTimeStamp.getTime()));
										hmDetails.put(ropObj.getScreening_time(), "ROP");
									}
								}
							}

							//USG
							String usgAssessmentQuery = "SELECT obj FROM ScreenUSG as obj where uhid='" + uhid.trim() 
							+ "' and screening_time >= '" + entryTimeServerFormat + "' and screening_time <= '" + entryEndTimeServerFormat + "' order by screening_time";   
							List<ScreenUSG> usgSet = patientDao.getListFromMappedObjNativeQuery(usgAssessmentQuery);

							if(!BasicUtils.isEmpty(usgSet)) {
								for(ScreenUSG usgObj : usgSet) {
									if(!BasicUtils.isEmpty(usgObj.getIvh_type()) && ((usgObj.getIvh_type().equalsIgnoreCase("IVH Grade III")) || (usgObj.getIvh_type().equalsIgnoreCase("IVH Grade IV")))){
										fromHmTime.add(new Timestamp(usgObj.getScreening_time().getTime()));
										toHmTime.add(new Timestamp(entryEndTimeStamp.getTime()));
										hmDetails.put(usgObj.getScreening_time(), "IVHGradeIIIorIV");

									}
								}
							}


							Timestamp fromInotropesTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toInotropesTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isInotropesStatus = false;

							String medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + uhid.trim() 
							+"' and medicineorderdate >= '"+ entryTimeServerFormat+ "' and medicineorderdate <= '" + entryEndTimeServerFormat + "' and isactive = 'false' and enddate is not null and "
							+ " isedit is null and iscontinuemedication is null order by medicineorderdate asc";    
							List<BabyPrescription> prescriptionList = patientDao.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
							for(BabyPrescription prescriptionObj : prescriptionList) {
								if(prescriptionObj.getEnddate().getTime() > entryTimeStamp.getTime()) {
									if(prescriptionObj.getMedicineOrderDate().getTime() >= entryTimeStamp.getTime()) {
										if(prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											fromInotropesTime = new Timestamp(prescriptionObj.getMedicineOrderDate().getTime());
											isInotropesStatus = true;
											hmDetails.put(fromInotropesTime, "Inotrope");
										}
									}
									else {
										if(prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											fromInotropesTime = new Timestamp(entryTimeStamp.getTime());
											isInotropesStatus = true;
											hmDetails.put(fromInotropesTime, "Inotrope");
										}
									}
									if(prescriptionObj.getEnddate().getTime() >= entryEndTimeStamp.getTime()) {
										if(isInotropesStatus && prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											toInotropesTime = new Timestamp(entryEndTimeStamp.getTime());
											isInotropesStatus = false;
											fromHmTime.add(new Timestamp(fromInotropesTime.getTime()));
											toHmTime.add(new Timestamp(toInotropesTime.getTime()));
										}
									}
									else{
										if(isInotropesStatus && prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
											toInotropesTime = new Timestamp(prescriptionObj.getEnddate().getTime());
											isInotropesStatus = false;
											fromHmTime.add(new Timestamp(fromInotropesTime.getTime()));
											toHmTime.add(new Timestamp(toInotropesTime.getTime()));
										}
									}
								}
							}
							medicationAssessmentQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + uhid.trim() 
							+ "' and medicineorderdate <= '" + entryEndTimeServerFormat + "' and isactive = 'true' and enddate is null order by medicineorderdate asc";   
							prescriptionList = patientDao.getListFromMappedObjNativeQuery(medicationAssessmentQuery);
							for(BabyPrescription prescriptionObj : prescriptionList) {
								if(prescriptionObj.getMedicineOrderDate().getTime() >= entryTimeStamp.getTime()) {
									if(prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
										fromInotropesTime = new Timestamp(prescriptionObj.getMedicineOrderDate().getTime());
										isInotropesStatus = true;
										hmDetails.put(fromInotropesTime, "Inotrope");
									}
								}
								else {
									if(prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
										fromInotropesTime = new Timestamp(entryTimeStamp.getTime());
										isInotropesStatus = true;
										hmDetails.put(fromInotropesTime, "Inotrope");
									}
								}
								if(isInotropesStatus && prescriptionObj.getMedicationtype().equalsIgnoreCase("TYPE0004")) {
									toInotropesTime = new Timestamp(entryEndTimeStamp.getTime());
									isInotropesStatus = false;
									fromHmTime.add(new Timestamp(fromInotropesTime.getTime()));
									toHmTime.add(new Timestamp(toInotropesTime.getTime()));
								}
							}
							//end of medication 

							//PPHN and Pneumothorax
							Timestamp fromPPHNTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toPPHNTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isPPHNStatus = false;

							Timestamp fromPneumoTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							Timestamp toPneumoTime = new Timestamp(entryTimeStamp.getTime() - (1000 * 24 * 60 * 60));
							boolean isPneumoStatus = false;

							int pphnStatus = -1;
							int pneumothoraxStatus = -1;

							List<VwAssesmentRespsystemFinal> rdsSet = new ArrayList<>();
							String rdsAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='" + uhid.trim()
							+ "' and creationtime <= '" + entryTimeServerFormat + "' order by creationtime desc";
							List<VwAssesmentRespsystemFinal> rdsSetTemp = patientDao.getListFromMappedObjNativeQuery(rdsAssessmentQuery);
//							if(!BasicUtils.isEmpty(rdsSetTemp)) {
//								for(VwAssesmentRespsystemFinal assessmentObj : rdsSetTemp) {
//									switch (assessmentObj.getEvent()) {
//
//									case "PPHN" :
//										if(pphnStatus == -1 && (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))){
//											pphnStatus = 1;
//										}
//										else if(pphnStatus == -1){
//											pphnStatus = 0;
//										}
//										break;
//									case "Pneumothorax" :
//										if(pneumothoraxStatus == -1 && (assessmentObj.getEventstatus().equalsIgnoreCase("Active"))){
//											pneumothoraxStatus = 1;
//										}
//										else if(pneumothoraxStatus == -1){
//											pneumothoraxStatus = 0;
//										}
//										break;
//									}
//								}
//							}

							rdsAssessmentQuery = "SELECT obj FROM VwAssesmentRespsystemFinal as obj where uhid='" + uhid.trim() 
							+ "' and creationtime >= '" + entryTimeServerFormat + "' and creationtime <= '" + entryEndTimeServerFormat + "' order by creationtime";   
							rdsSetTemp = patientDao.getListFromMappedObjNativeQuery(rdsAssessmentQuery);
							if(!BasicUtils.isEmpty(rdsSetTemp))
								rdsSet.addAll(rdsSetTemp);

							if(!BasicUtils.isEmpty(rdsSet)) {
								for(VwAssesmentRespsystemFinal sarespObj : rdsSet) {
									switch (rdsSet.get(0).getEvent()) {

									case "PPHN" :
										if(!(sarespObj.getEventstatus().equalsIgnoreCase("Active")) && pphnStatus == 1) {
											pphnStatus = 0;

											if(isPPHNStatus) {
												toPPHNTime = new Timestamp(sarespObj.getCreationtime().getTime());
												isPPHNStatus = false;
												fromHmTime.add(new Timestamp(fromPPHNTime.getTime()));
												toHmTime.add(new Timestamp(toPPHNTime.getTime()));
											}
										}
										else if((sarespObj.getEventstatus().equalsIgnoreCase("Active")) && (pphnStatus == 0 || pphnStatus == -1)) {
											pphnStatus = 1;
											fromPPHNTime = new Timestamp(sarespObj.getCreationtime().getTime());
											isPPHNStatus = true;
											hmDetails.put(fromPPHNTime, "PPHN");
										}
										break;
									case "Pneumothorax" :
										if(!(sarespObj.getEventstatus().equalsIgnoreCase("Active")) && pneumothoraxStatus == 1) {
											pneumothoraxStatus = 0;
											if(isPneumoStatus) {
												toPneumoTime = new Timestamp(sarespObj.getCreationtime().getTime());
												isPneumoStatus = false;
												fromHmTime.add(new Timestamp(fromPneumoTime.getTime()));
												toHmTime.add(new Timestamp(toPneumoTime.getTime()));
											}
										}
										else if((sarespObj.getEventstatus().equalsIgnoreCase("Active")) && (pneumothoraxStatus == 0 || pneumothoraxStatus == -1)) {
											pneumothoraxStatus = 1;
											fromPneumoTime = new Timestamp(sarespObj.getCreationtime().getTime());
											isPneumoStatus = true;
											hmDetails.put(fromPneumoTime, "Pneumothorax");
										}
										break;
									}
								}
							}

							
							Timestamp startTimeOffset = new Timestamp(entryTimeStamp.getTime());
							Timestamp endTimeOffset = new Timestamp(entryEndTimeStamp.getTime());

							if(fromHmTime.size() > 0 && toHmTime.size() > 0 && fromHmTime.size() == toHmTime.size()) {
								while(endTimeOffset.getTime() > startTimeOffset.getTime()) {
									boolean isHmLmStatus = false;
									for(int i = 0;i < fromHmTime.size();i ++) {
										if(fromHmTime.get(i).getTime() <= startTimeOffset.getTime() && toHmTime.get(i).getTime() >= startTimeOffset.getTime()) {
											List<Object> hmlmData = new ArrayList<>();
											Timestamp offsetTime = new Timestamp(startTimeOffset.getTime() + 19800000);
											hmlmData.add(offsetTime);
											hmlmData.add(1);
											hmlmlFinalData.add(hmlmData);
											isHmLmStatus = true;
											break;
										}
									}
									if(!isHmLmStatus) {
										List<Object> hmlmData = new ArrayList<>();
										Timestamp offsetTime = new Timestamp(startTimeOffset.getTime() + 19800000);
										hmlmData.add(offsetTime);
										hmlmData.add(0);
										hmlmlFinalData.add(hmlmData);
									}
									startTimeOffset = new Timestamp(startTimeOffset.getTime() + (60 * 1000));
								}
							}
						}
						
					}

				}catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		 return hmDetails;
	}

	@Override
	public void run() {
		try {

			//HMLMuhids hmLmUhids = new HMLMuhids();

			String queryForAllbabies = "Select obj from BabyDetail obj";
			
			List<BabyDetail> allBabyDetails = inicuDao.getListFromMappedObjQuery(queryForAllbabies);
			for(int i = 0;i<allBabyDetails.size();i++) {
					
				Date dateOnlyObj=new Date();
				
				String babyQuery = "SELECT b FROM HMObservableStatus b where uhid = '" + allBabyDetails.get(i).getUhid()+ "'";
				List<HMObservableStatus> hmList = inicuDao.getListFromMappedObjQuery(babyQuery);
				if(BasicUtils.isEmpty(hmList)) {
				
					String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='" + allBabyDetails.get(i).getUhid()
							+ "' order by creationtime desc";
					List<AdmissionNotes> listAdmissionNotes = inicuDao.getListFromMappedObjQuery(queryAdmissionNotes);
					String adminssionNotesDiagnosis = "";
					if (!BasicUtils.isEmpty(listAdmissionNotes)) {
						AdmissionNotes admNotes = listAdmissionNotes.get(0);
			
						if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
							adminssionNotesDiagnosis = admNotes.getDiagnosis();
						}
					}
					
					String timelinessQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaCnsAsphyxia> SaCnsAsphyxiaList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					if(!BasicUtils.isEmpty(SaCnsAsphyxiaList)) {
						adminssionNotesDiagnosis += "/ " + "Asphyxia";
					}
					

					timelinessQuery = "select obj from SaCnsSeizures obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaCnsSeizures> SaCnsSeizuresList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaCnsSeizuresList)) {
						adminssionNotesDiagnosis += "/ " + "Seizures";
					}

					timelinessQuery = "select obj from SaSepsis obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'"; 
					List<SaSepsis> SaSepsisList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaSepsisList)) {
						adminssionNotesDiagnosis += "/ " + "Sepsis";
					}

					timelinessQuery = "select obj from SaJaundice obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaJaundice> SaJaundiceList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaJaundiceList)) {
						adminssionNotesDiagnosis += "/ " + "Jaundice";
					}

					timelinessQuery = "select obj from SaRespApnea obj  where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaRespApnea> SaRespApneaList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaRespApneaList)) {
						adminssionNotesDiagnosis += "/ " + "Apnea";
					}

					timelinessQuery = "select obj from SaRespPneumo obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaRespPneumo> SaRespPneumoList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaRespPneumoList)) {
						adminssionNotesDiagnosis += "/ " + "Pneumothorax";
					}

					timelinessQuery = "select obj from SaRespRds obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaRespRds> SaRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaRespRdsList)) {
						adminssionNotesDiagnosis += "/ " + "RDS";
					}
					
					timelinessQuery = "select obj from SaRespPphn obj where uhid = '" + allBabyDetails.get(i).getUhid() + "'";
					List<SaRespPphn> SaResppphnList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
					
					
					if(!BasicUtils.isEmpty(SaResppphnList)) {
						adminssionNotesDiagnosis += "/ " + "PPHN";
					}
					
					java.sql.Timestamp admissionTime = new java.sql.Timestamp(allBabyDetails.get(i).getDateofadmission().getTime());
					java.sql.Timestamp dischargeTime = new java.sql.Timestamp(dateOnlyObj.getTime());
					DateFormat d = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	
					String startTime = d.format(admissionTime);
					startTime = startTime + " GMT+0530 (IST)";
					String endTime = d.format(dischargeTime);
					endTime = endTime + " GMT+0530 (IST)";
					int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
							- TimeZone.getDefault().getRawOffset();
					DeviceGraphDataJson graphData = new DeviceGraphDataJson();
					graphData.setUhid(allBabyDetails.get(i).getUhid());
					graphData.setHmOutput(true);
					boolean isHM = false;
					TreeMap<Timestamp, String> hmDetails = getfirstHmIncidenceTime(graphData, startTime, endTime);
					if(!BasicUtils.isEmpty(hmDetails)) {
						for (Timestamp entry : hmDetails.keySet()) {
							if(!isHM) {
								isHM = true;
								String reason = hmDetails.get(entry);
								HMObservableStatus obj = new HMObservableStatus();
								obj.setUhid(allBabyDetails.get(i).getUhid());
								obj.setBabyname(allBabyDetails.get(i).getBabyname());
								obj.setGender(allBabyDetails.get(i).getGender());
								obj.setInout_patient_status(allBabyDetails.get(i).getInoutPatientStatus());
								
								
								Timestamp creationTime = new Timestamp(new Date().getTime());
								if (!BasicUtils.isEmpty(allBabyDetails.get(i).getDateofbirth())) {
									creationTime = new Timestamp(allBabyDetails.get(i).getDateofbirth().getTime());
									if (!BasicUtils.isEmpty(allBabyDetails.get(i).getTimeofbirth())) {
										String[] toaArr = allBabyDetails.get(i).getTimeofbirth().split(",");
										// "10,38,PM"
										if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
											creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
										} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
											creationTime.setHours(0);
										} else {
											creationTime.setHours(Integer.parseInt(toaArr[0]));
										}
										creationTime.setMinutes(Integer.parseInt(toaArr[1]));
									}
									creationTime = new Timestamp(creationTime.getTime() - offset);
									obj.setDateofbirth(creationTime);
								}
								creationTime = new Timestamp(new Date().getTime());
								if (!BasicUtils.isEmpty(allBabyDetails.get(i).getDateofadmission())) {
									creationTime = new Timestamp(allBabyDetails.get(i).getDateofadmission().getTime());
									if (!BasicUtils.isEmpty(allBabyDetails.get(i).getTimeofadmission())) {
										String[] toaArr = allBabyDetails.get(i).getTimeofadmission().split(",");
										// "10,38,PM"
										if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
											creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
										} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
											creationTime.setHours(0);
										} else {
											creationTime.setHours(Integer.parseInt(toaArr[0]));
										}
										creationTime.setMinutes(Integer.parseInt(toaArr[1]));
									}
									creationTime = new Timestamp(creationTime.getTime() - offset);
									obj.setDateofadmission(creationTime);
								}
								
								if(!BasicUtils.isEmpty(allBabyDetails.get(i).getBirthweight()))
									obj.setBirthweight(allBabyDetails.get(i).getBirthweight());
								if(!BasicUtils.isEmpty(allBabyDetails.get(i).getGestationdaysbylmp()))
									obj.setGestationdaysbylmp(allBabyDetails.get(i).getGestationdaysbylmp());
								if(!BasicUtils.isEmpty(allBabyDetails.get(i).getGestationweekbylmp()))
									obj.setGestationweekbylmp(allBabyDetails.get(i).getGestationweekbylmp());
								obj.setStatus("HM");
								obj.setHmReason(reason);
								obj.setHmDate(entry);
								obj.setBranchname(allBabyDetails.get(i).getBranchname());
								obj.setDiagnosis(adminssionNotesDiagnosis);
								inicuDao.saveObject(obj);
							}
						}
	
					}else {
						HMObservableStatus obj = new HMObservableStatus();
						obj.setUhid(allBabyDetails.get(i).getUhid());
						obj.setBabyname(allBabyDetails.get(i).getBabyname());
						obj.setGender(allBabyDetails.get(i).getGender());
						obj.setInout_patient_status(allBabyDetails.get(i).getInoutPatientStatus());
						Timestamp creationTime = new Timestamp(new Date().getTime());
						if (!BasicUtils.isEmpty(allBabyDetails.get(i).getDateofbirth())) {
							creationTime = new Timestamp(allBabyDetails.get(i).getDateofbirth().getTime());
							if (!BasicUtils.isEmpty(allBabyDetails.get(i).getTimeofbirth())) {
								String[] toaArr = allBabyDetails.get(i).getTimeofbirth().split(",");
								// "10,38,PM"
								if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
									creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
								} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
									creationTime.setHours(0);
								} else {
									creationTime.setHours(Integer.parseInt(toaArr[0]));
								}
								creationTime.setMinutes(Integer.parseInt(toaArr[1]));
							}
							creationTime = new Timestamp(creationTime.getTime() - offset);
							obj.setDateofbirth(creationTime);
						}
						creationTime = new Timestamp(new Date().getTime());
						if (!BasicUtils.isEmpty(allBabyDetails.get(i).getDateofadmission())) {
							creationTime = new Timestamp(allBabyDetails.get(i).getDateofadmission().getTime());
							if (!BasicUtils.isEmpty(allBabyDetails.get(i).getTimeofadmission())) {
								String[] toaArr = allBabyDetails.get(i).getTimeofadmission().split(",");
								// "10,38,PM"
								if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
									creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
								} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
									creationTime.setHours(0);
								} else {
									creationTime.setHours(Integer.parseInt(toaArr[0]));
								}
								creationTime.setMinutes(Integer.parseInt(toaArr[1]));
							}
							creationTime = new Timestamp(creationTime.getTime() - offset);
							obj.setDateofadmission(creationTime);
						}
						if(!BasicUtils.isEmpty(allBabyDetails.get(i).getBirthweight()))
							obj.setBirthweight(allBabyDetails.get(i).getBirthweight());
						if(!BasicUtils.isEmpty(allBabyDetails.get(i).getGestationdaysbylmp()))
							obj.setGestationdaysbylmp(allBabyDetails.get(i).getGestationdaysbylmp());
						if(!BasicUtils.isEmpty(allBabyDetails.get(i).getGestationweekbylmp()))
							obj.setGestationweekbylmp(allBabyDetails.get(i).getGestationweekbylmp());
						
						if(!BasicUtils.isEmpty(allBabyDetails.get(i).getDischargestatus()) && allBabyDetails.get(i).getDischargestatus().equalsIgnoreCase("Death")) {
							obj.setStatus("HM");
							obj.setHmReason("Death");
						}else {
							obj.setStatus("LM");
						}
						obj.setBranchname(allBabyDetails.get(i).getBranchname());
						obj.setDiagnosis(adminssionNotesDiagnosis);
						inicuDao.saveObject(obj);
					}
				}

			}
			


		} catch (Exception e) {
			e.printStackTrace();

		}
		

	}
}
