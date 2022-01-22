package com.inicu.postgres.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Message.RecipientType;

import ch.qos.logback.core.net.SyslogOutputStream;
import junit.framework.TestResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.models.*;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.LoginService;
import com.inicu.postgres.utility.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PrescriptionDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.service.AnalyticsService;
import com.inicu.postgres.service.GrowthChatService;
import com.inicu.postgres.service.LogsService;
import scala.collection.mutable.StringBuilder;

/**
 *
 * @author iNICU
 *
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Autowired
	InicuDao inicuDao;

	@Autowired
	PatientDao patientDao;

	@Autowired
	UserServiceDAO userServiceDao;
	
	@Autowired
	InicuDatabaseExeption databaseException;

	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;

//	@Autowired
//	LogsService logService;
//
//	@Autowired
//	UserServiceDAO userServiceDao;

	@Autowired
	GrowthChatService graph;

	@Autowired
	PrescriptionDao prescriptionDao;

	@Autowired
	NotesServiceImp systematicService;

	HashMap<String, String> jaundiceCauseMap = new HashMap<String, String>();
	HashMap<String, String> rdsCauseMap = new HashMap<String, String>();
	HashMap<String, String> sepsisCauseMap = new HashMap<String, String>();
	HashMap<String,RefZScore> RefzScoreMap;
	HashMap<String,RefIntergrowth> IntergrowthCentileMap;

	private  DecimalFormat singleDecimal = new DecimalFormat("0.0");
	private  static DecimalFormat twoDecimal = new DecimalFormat("0.00");


	public AnalyticsServiceImpl() {
		System.out.println("Analytics Service Layer");
	}

	@Override
	public BedOccupancyObj getBedOccupied(String loggedInUser) {

		BedOccupancyObj bedOccupancy = new BedOccupancyObj();

		try {

			String queryBedOccupancy = "select count(bedid) from RefBed";
			List bedCountList = inicuDao.getListFromMappedObjQuery(queryBedOccupancy);

			if (!BasicUtils.isEmpty(bedCountList)) {

				Object bedCountTotal = bedCountList.get(0);

				java.sql.Date todayDate = new Date(new java.util.Date().getTime());
				Calendar c1 = Calendar.getInstance();
				c1.setTime(todayDate);

				// get all baby that are admitted today...
				String queryBabyAdmittedToday = "select count(babydetailid) from BabyDetail where dateofadmission='"
						+ todayDate + "'";
				List babyCountTodayList = inicuDao.getListFromMappedObjQuery(queryBabyAdmittedToday);
				Object babyCountToday = babyCountTodayList.get(0);
				Float todayCountOccupied = (Float.valueOf(babyCountToday + "") / Float.valueOf(bedCountTotal + ""))
						* 100;
				Float todayCountVacant = 100 - todayCountOccupied;

				// getting baby's admitted this week
				c1.add(c1.DAY_OF_WEEK, -6);
				java.sql.Date afterWeekDate = new java.sql.Date(c1.getTime().getTime());
				String queryBabyAdmittedThisWeek = "select count(babydetailid) from BabyDetail where dateofadmission between '"
						+ afterWeekDate + "' and '" + todayDate + "'";
				List babyCountThisWeekList = inicuDao.getListFromMappedObjQuery(queryBabyAdmittedThisWeek);
				Object babyCountWeek = babyCountThisWeekList.get(0);
				Float weekCountOccupied = (Float.valueOf(babyCountWeek + "") / Float.valueOf(bedCountTotal + "")) * 100;
				Float weekCountVacant = 100 - weekCountOccupied;

				// getting baby's admitted this month
				c1.add(c1.DAY_OF_WEEK, 6);
				c1.add(c1.MONTH, -1);
				java.sql.Date afterMonthDate = new java.sql.Date(c1.getTime().getTime());
				String queryBabyAdmittedThisMonth = "select count(babydetailid) from BabyDetail where dateofadmission between '"
						+ afterMonthDate + "' and '" + todayDate + "'";
				List babyCountThisMonthList = inicuDao.getListFromMappedObjQuery(queryBabyAdmittedThisMonth);
				Object babyCountMonth = babyCountThisMonthList.get(0);
				Float monthCountOccupied = (Float.valueOf(babyCountMonth + "") / Float.valueOf(bedCountTotal + ""))
						* 100;
				Float monthCountVacant = 100 - monthCountOccupied;

				bedOccupancy.setCurrentBedOccupied(todayCountOccupied);
				bedOccupancy.setCurrentBedVacant(todayCountVacant);

				bedOccupancy.setWeeklyBedOccupied(weekCountOccupied);
				bedOccupancy.setWeeklyBedVacant(weekCountVacant);

				bedOccupancy.setMonthlyBedOccupied(monthCountOccupied);
				bedOccupancy.setMonthlyBedVacant(monthCountVacant);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser, "",
					"Get Usage Data", BasicUtils.convertErrorStacktoString(ex));
		}
		try {
//			logService.saveLog("gettting bed occupanacy", "Getting Bed Occupancy", loggedInUser, "",
//					"Analytics Bed Occupancy");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bedOccupancy;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AnalyticsUsagePojo> getAnalyticsUsage(String fromDateStr, String toDateStr, String branchName) {
		//getAnalyticsUsageGraph(fromDateStr, toDateStr, branchName);

		LinkedList<AnalyticsUsagePojo> returnList = new LinkedList<AnalyticsUsagePojo>();
		try {
			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			RefzScoreMap=null;
			IntergrowthCentileMap=null;
			List<Object[]> uhidList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageBabyListWithdoa(fromDate, toDate, branchName));

			if(!BasicUtils.isEmpty(uhidList)){
				returnList=getUsageAnalyticsData(uhidList,fromDate, toDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"getAnalyticsUsage Data", BasicUtils.convertErrorStacktoString(e));
		}
		RefzScoreMap=null;
		IntergrowthCentileMap=null;
		return returnList;
	}
//parveen 1
	@SuppressWarnings("unchecked")
	private LinkedList<AnalyticsUsagePojo> getUsageAnalyticsData(List<Object[]> babyList,Timestamp fromDate,
																 Timestamp toDate){
		HashMap<String,AnalyticsUsagePojo> analyticsUsageObjectMap;
		LinkedList<AnalyticsUsagePojo> returnList = new LinkedList<AnalyticsUsagePojo>();
		String uhidList="";
		String episodeList="";

		System.out.println("getUsageAnalyticsData start--------------");

		for(Object[] checker:babyList){
			if (uhidList.isEmpty()) {
				uhidList += "'" + checker[0] + "'";
			} else {
				uhidList += ", '" + checker[0] + "'";
			}
		}

		try{
			analyticsUsageObjectMap=new HashMap<>();
			for(Object[] obj:babyList) {

				String uhid=obj[0].toString().replace("\'","");
				String episode=obj[1].toString().replace("\'","");

				AnalyticsUsagePojo usageObj = new AnalyticsUsagePojo();
				if(!analyticsUsageObjectMap.isEmpty()) {
					if(!BasicUtils.isEmpty(analyticsUsageObjectMap.get(uhid))){
						usageObj=analyticsUsageObjectMap.get(uhid);
					}
				}

				List<Object[]> usageList = (List<Object[]>) inicuDao
						.getListFromNativeQuery(HqlSqlQueryConstants.getUsageBasicDetail(uhid, episode));

				
				//
				if (!BasicUtils.isEmpty(usageList)) {

					Object[] basicDetail = usageList.get(0);
					double losTemp = ((Double) basicDetail[4]).intValue();

					if (losTemp > 0) {

						usageObj.setUhid((String) basicDetail[0]);
						usageObj.setBabyName((String) basicDetail[1]);
						usageObj.setNicuLevel(BasicUtils.isEmpty((String) basicDetail[2]) ? "" : (String) basicDetail[2]);
						usageObj.setAdmissionStatus((String) basicDetail[3]);
						usageObj.setLengthOfStay(((Double) basicDetail[4]).intValue());
						usageObj.setCriticality(BasicUtils.isEmpty((String) basicDetail[5]) ? "" : (String) basicDetail[5]);
						//usageObj.setInitialAssessment((Integer) basicDetail[6]);
						usageObj.setNeonatologist(BasicUtils.isEmpty((String) basicDetail[7]) ? "" : (String) basicDetail[7]);
						usageObj.setEpisodeid((String) basicDetail[8]);
						usageObj.setDateofbirth(basicDetail[9] + "");
						usageObj.setDateofadmission(basicDetail[10] + "");
						if (!BasicUtils.isEmpty(basicDetail[11])) {
							usageObj.setBirthweight((Float) basicDetail[11]);
						}
						if (!BasicUtils.isEmpty(basicDetail[12])) {
							usageObj.setGestationweekbylmp((Integer) basicDetail[12]);
						}
						if (!BasicUtils.isEmpty(basicDetail[13])) {
							usageObj.setGestationdaysbylmp((Integer) basicDetail[13]);
						}
						usageObj.setGender((String) basicDetail[14]);

						//Getting the discharge date
						String date = "";
						if (!BasicUtils.isEmpty(basicDetail[15])) {
							Timestamp dischargedate = (Timestamp) basicDetail[15];
							date = "2018" + "-" + (dischargedate.getMonth() + 1) + "-" + dischargedate.getDate();
							usageObj.setDischargedDate(date);
						}

						//HIS discharge date
						if (!BasicUtils.isEmpty(basicDetail[17])) {
							usageObj.setHisdischargedate((Timestamp) basicDetail[17]);
						}

//HIS discharge date
						if (!BasicUtils.isEmpty(basicDetail[18])) {
							usageObj.setAdmissionweight(Float.toString((Float) basicDetail[18]));
						}



						if (!BasicUtils.isEmpty(basicDetail[20])) {
							usageObj.setInout_patient_status(((String) basicDetail[20]));
						}

//calculate no. of days of baby stay during selected duratoin

//						Date admissionDated = (Date) basicDetail[10];
//						Timestamp admissiondate =  new Timestamp(admissionDated.getTime()) ;

						int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
								- TimeZone.getDefault().getRawOffset();

						Calendar birthDateCal = CalendarUtility.getDateTimeFromDateAndTime((Date) basicDetail[10], (String) basicDetail[19]);
						java.util.Date birthDateCalDate = birthDateCal.getTime();
						Timestamp admissiondate =  new Timestamp(birthDateCalDate.getTime() - offset) ;





						System.out.println("admissiondate----"+admissiondate);

						Timestamp dischargedate = (Timestamp) basicDetail[15];

						System.out.println("dischargedate----"+dischargedate);


						Timestamp startDate = fromDate;

						System.out.println("startDate----"+startDate);

						Timestamp endDate = toDate;

						System.out.println("endDate----"+endDate);


						if(admissiondate.after(startDate)) {
							startDate = admissiondate;
							System.out.println("startDate changed----" + startDate);
						}

						if(dischargedate!= null && dischargedate.before(endDate)) {
							endDate = dischargedate;
							System.out.println("endDate changed----" + endDate);
						}
						double babyStayDuringPeriod = (double) ((endDate.getTime() - startDate.getTime()+60000) / (1000 * 60 * 60 * 24));

						double babyStayDuringPeriodInhrs = (double) ((endDate.getTime() - startDate.getTime()+60000) / (1000 * 60 * 60 ));

if(babyStayDuringPeriod==0) babyStayDuringPeriod++;

						System.out.println("babyStayDuringPeriod----"+babyStayDuringPeriod);

						System.out.println("babyStayDuringPeriodInhrs----"+babyStayDuringPeriodInhrs);


						usageObj.setBabyStayDuringPeriod(babyStayDuringPeriod);

						usageObj.setBabyStayDuringPeriodInhrs(babyStayDuringPeriodInhrs);



						if (!BasicUtils.isEmpty(basicDetail[16])) {
							usageObj.setHisdischargestatus((String) basicDetail[16]);
						}
						//Here calculating the fenton, intergrowth percentiles and z-index deviation at birth and discharge only
						double birthWeight = 0f;
						String gest = "";
						String days = "";
						if (!BasicUtils.isEmpty(basicDetail[11])) {
							birthWeight = (Float) basicDetail[11];
						}
						if (!BasicUtils.isEmpty(basicDetail[12])) {
							gest = basicDetail[12] + "";
						}
						if (!BasicUtils.isEmpty(basicDetail[13])) {
							days = basicDetail[13] + "";
						}
						String gender = (String) basicDetail[14];

						if (!BasicUtils.isEmpty(birthWeight) && !BasicUtils.isEmpty(gest) && !BasicUtils.isEmpty(days)) {
							double fentonBirth = 0;
							double fentonDischarge = 0;
							double intergrowthBirth = 0;
							double intergrowthDischarge = 0;
							double zscoreBirth = 0;
							double zscoreDischarge = 0;
							if (!BasicUtils.isEmpty(birthWeight) && !BasicUtils.isEmpty(gest) && !BasicUtils.isEmpty(gender)) {
								fentonBirth = graph.getFentonCentile(gender.toLowerCase(), gest, "weight", birthWeight);
								zscoreBirth = getZScore(gender.toLowerCase(), gest, "weight", birthWeight);
								if (!BasicUtils.isEmpty(days))
									intergrowthBirth = getIntergrowthCentile(gender.toLowerCase(), gest, days, "weight", birthWeight);
							}

//						calculating the percentiles at discharge
							List<BabyVisit> visitList = (List<BabyVisit>) inicuDao
									.getListFromMappedObjRowLimitQuery(HqlSqlQueryConstants.getLastAnthropometry(uhid), 1);
							if (!BasicUtils.isEmpty(visitList)) {
								BabyVisit visitObj = visitList.get(0);
								gest = visitObj.getGestationWeek() + "";
								days = visitObj.getGestationDays() + "";
								double dischargeWeight = visitObj.getCurrentdateweight();
								if (!BasicUtils.isEmpty(dischargeWeight) && !BasicUtils.isEmpty(gest) && !BasicUtils.isEmpty(gender)) {
									fentonDischarge = graph.getFentonCentile(gender.toLowerCase(), gest, "weight", dischargeWeight);
									zscoreDischarge = getZScore(gender.toLowerCase(), gest, "weight", birthWeight);
									usageObj.setDischargebirthweight((float) dischargeWeight);
									usageObj.setDischargegestationweekbylmp((Integer) visitObj.getGestationWeek());
									if (!BasicUtils.isEmpty(days)) {
										usageObj.setDischargegestationdaysbylmp((Integer) visitObj.getGestationDays());
										intergrowthDischarge = getIntergrowthCentile(gender.toLowerCase(), gest, days, "weight", dischargeWeight);
									}
									String visitDate = "";
									if (!BasicUtils.isEmpty(visitObj.getVisitdate())) {
										visitDate = visitObj.getVisitdate().toString();
										usageObj.setLastVisitDateDate(visitDate);
									}
								}
							}
							//Setting the centiles
							if (!BasicUtils.isEmpty(fentonBirth)) {
								usageObj.setFentonBirth(fentonBirth);
							}
							if (!BasicUtils.isEmpty(fentonDischarge)) {
								usageObj.setFentonDischarge(fentonDischarge);
							}
							if (!BasicUtils.isEmpty(intergrowthBirth)) {
								usageObj.setIntergrowthBirth(intergrowthBirth);
							}
							if (!BasicUtils.isEmpty(intergrowthDischarge)) {
								usageObj.setIntergrowthDischarge(intergrowthDischarge);
							}
							if (!BasicUtils.isEmpty(zscoreBirth)) {
								usageObj.setzScoreBirth(zscoreBirth);
							}
							if (!BasicUtils.isEmpty(zscoreDischarge)) {
								usageObj.setzScoreDischarge(zscoreDischarge);
							}
						}

					}



					// get initial assessment calculation started


					HashMap initialAssessmentMap = new HashMap<>();

					initialAssessmentMap.put("gestationdaysbylmp",usageObj.getGestationdaysbylmp());

					initialAssessmentMap.put("admissionweight",usageObj.getAdmissionweight());

					initialAssessmentMap.put("birthweight",usageObj.getBirthweight());


					initialAssessmentMap = getInitialAssessmentCalculation(uhid, fromDate,	toDate,initialAssessmentMap, usageObj);

					if(initialAssessmentMap!=null) {

						usageObj.setInitialAssessment(initialAssessmentMap.get("initialAssessmentScoreDisplay") != null ? (String) initialAssessmentMap.get("initialAssessmentScoreDisplay") : "");
						usageObj.setInitialAssessmentScore(initialAssessmentMap.get("initialAssessmentScore") != null ? Double.toString((Double) initialAssessmentMap.get("initialAssessmentScore")) : "");

						usageObj.setInitialEssentialAssessment(initialAssessmentMap.get("initialAssessmentEssentialScore") != null ? (String) initialAssessmentMap.get("initialAssessmentEssentialScore") : "");
						usageObj.setInitialMandatoryAssessment(initialAssessmentMap.get("initialAssessmentMandatoryScore") != null ? (String) initialAssessmentMap.get("initialAssessmentMandatoryScore") : "");


						usageObj.setReasonForAdmission((String) initialAssessmentMap.get("reasonForAdmission"));
						usageObj.setAntenatalHistory((String)initialAssessmentMap.get("antenatalHistory"));
						usageObj.setBirthToNICU((String)initialAssessmentMap.get("birthToNICU"));
						usageObj.setStatusAtAdmission((String)initialAssessmentMap.get("statusAtAdmission"));
						usageObj.setGenPhysicalExam((String)initialAssessmentMap.get("genPhysicalExam"));
						usageObj.setSystematicPhysicalExam((String)initialAssessmentMap.get("systematicPhysicalExam"));

					}


					// get initial assessment calculation end


				}



				analyticsUsageObjectMap.put(obj[0].toString(),usageObj);
			}

			// get the Device name and its brand attached to the baby
			// Fetch Device Attached to Each baby
			String deviceIdStr="";
			List<Object[]> deviceBoxName=inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getDeviceName(uhidList,fromDate,toDate));

			if(!BasicUtils.isEmpty(deviceBoxName)) {
				for(Object[] obj:deviceBoxName){

					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					String currentBox=currentObject.getBoxname();
					String boxName=obj[0].toString();
					String deviceId=obj[2].toString();

					if (!BasicUtils.isEmpty(boxName)) {
						if(currentBox == "" || currentBox==null){
							currentBox = boxName;
						}
						else{
							if(!currentBox.contains(boxName)) {
								currentBox = currentBox + "," + boxName;
							}
						}
					}

					if(!BasicUtils.isEmpty(deviceId)) {
						if (deviceIdStr.isEmpty()) {
							deviceIdStr += "'" + deviceId + "'";
						} else {
							deviceIdStr += ", '" + deviceId + "'";
						}
					}

					currentObject.setBoxname(currentBox);
					analyticsUsageObjectMap.replace(tempuhid,currentObject);

				}
			}

			// doctor Details
			List<VwDoctornotesListFinal> assessmentUsageList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getUsageAssessment(uhidList, fromDate, toDate));

			if (!BasicUtils.isEmpty(assessmentUsageList)) {
				for (VwDoctornotesListFinal outerelement : assessmentUsageList) {

					String VwUhid=outerelement.getUhid();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(VwUhid);

					int assessmentCount = 0;
					int stableNotesCount = 0;

					for (VwDoctornotesListFinal element : assessmentUsageList) {

							if(element.getUhid().equals(VwUhid)) {

								if (element.getSaEvent().compareTo("Stable Notes") == 0) {
									stableNotesCount++;
								} else {
									assessmentCount++;
								}
							}
					}

					currentObject.setAssessment(assessmentCount);
					currentObject.setStableNotes(stableNotesCount);
					analyticsUsageObjectMap.replace(VwUhid,currentObject);
				}
			}

			// Medication Count
			List<Object[]> medicationUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageMedication(uhidList, fromDate, toDate));

			if(!BasicUtils.isEmpty(medicationUsageList)) {
				for(Object[] obj:medicationUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null){
						currentObject.setMedication(obj[0].toString());
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}


			//  Nutrition Count
			List<Object[]> nutritionUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageNutrition(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(nutritionUsageList)) {
				for(Object[] obj:nutritionUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setNutrition(obj[0].toString());
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

			// Lab Order Count
			List<Object[]> labOrderUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageLabOrder(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(labOrderUsageList)) {
				for(Object[] obj:labOrderUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setLabOrder(Integer.parseInt(obj[0].toString()));
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

			// Procedure Count
			List<Object[]> procedureUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageProcedure(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(procedureUsageList)) {
				for(Object[] obj:procedureUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setProcedure(Integer.parseInt(obj[0].toString()));
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

//			 Lab report count (distinct Reports only)
			List<Object[]> reportsUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageLabReports(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(reportsUsageList)) {
				for(Object[] obj:reportsUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setLabReports(Integer.parseInt(obj[0].toString()));
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

			// Nursing Details
			List<Object[]> anthropometrytUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageAnthropometry(uhidList, fromDate, toDate));

			if (!BasicUtils.isEmpty(anthropometrytUsageList)) {

				for (Object[] outerelement : anthropometrytUsageList) {

					String VwUhid=outerelement[2].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(VwUhid);

					int anthropometryCount = 0;
					int heightCount = 0;
					int headCount = 0;

					for (Object[] element : anthropometrytUsageList) {
						if(element[2].toString().equals(VwUhid)) {
							anthropometryCount=anthropometryCount+1;
							if (element[1] != null && Float.parseFloat(element[1].toString())!=0.0) {
								heightCount++;
							}

							if (element[0] != null && Float.parseFloat(element[0].toString())!=0.0){
								headCount++;
							}
						}
					}

					if(currentObject!=null) {
						
						
						
						
						System.out.println("anthropometryCount--------------"+anthropometryCount);
						currentObject.setAnthropometry(anthropometryCount+"");
						currentObject.setHeight(heightCount);
						currentObject.setHead(headCount);

						analyticsUsageObjectMap.replace(VwUhid, currentObject);
					}
				}
			}


			

			List<Object[]> vList = inicuDao.getListFromNativeQuery(HqlSqlQueryConstants.getNursingVentilator(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(vList)) {
				for(Object[] obj:vList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setVentilatorCount(obj[0].toString());
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}
			
			
			List<Object[]> vitalUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageVitals(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(vitalUsageList)) {
				for(Object[] obj:vitalUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setVitals(obj[0].toString());
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

/*
			String babyFeedQuery = "select obj from BabyfeedDetail obj where uhid = '" + uhid + "'";
			List<BabyfeedDetail> BabyFeedDetailListQuery = prescriptionDao.getListFromMappedObjNativeQuery(babyFeedQuery);
			if(!BasicUtils.isEmpty(BabyFeedDetailListQuery)) {
				returnObj.setNutritionTotalOrders(BabyFeedDetailListQuery.size());
			}
			String intakeQuery = "select obj from NursingIntakeOutput obj where uhid = '" + uhid + "'";
			List<NursingIntakeOutput> intakeQueryList = prescriptionDao.getListFromMappedObjNativeQuery(intakeQuery);
			if(!BasicUtils.isEmpty(intakeQueryList)) {
				returnObj.setNutritionTotalExecutions(intakeQueryList.size());
			}
*/

			List<Object[]> intakeOutputUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageIntakeOutput(uhidList, fromDate, toDate));

			System.out.println("intakeOutputUsageList----------"+intakeOutputUsageList);

			if(!BasicUtils.isEmpty(intakeOutputUsageList)) {
				for(Object[] obj:intakeOutputUsageList){
					String tempuhid=obj[1].toString();

					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setIntake_output(obj[0].toString());



						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

			List<Object[]> eventUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageEvents(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(eventUsageList)) {
				for(Object[] obj:eventUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setEvents(Integer.parseInt(obj[0].toString()));
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

			List<Object[]> labSampleUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageLabSampleSent(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(labSampleUsageList)) {
				for(Object[] obj:labSampleUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null){
					currentObject.setSample_sent(Integer.parseInt(obj[0].toString()));
					analyticsUsageObjectMap.replace(tempuhid,currentObject);
					}
				}
			}

			List<Object[]> nursingNotesUsageList = inicuDao
					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageNursingNotes(uhidList, fromDate, toDate));
			if(!BasicUtils.isEmpty(nursingNotesUsageList)) {
				for(Object[] obj:nursingNotesUsageList){
					String tempuhid=obj[1].toString();
					AnalyticsUsagePojo currentObject=analyticsUsageObjectMap.get(tempuhid);
					if(currentObject!=null) {
						currentObject.setNursing_notes(Integer.parseInt(obj[0].toString()));
						analyticsUsageObjectMap.replace(tempuhid, currentObject);
					}
				}
			}

//ventilator total

//			List<Object[]> ventilatorList = inicuDao
//					.getListFromNativeQuery(HqlSqlQueryConstants.getUsageVentilator(uhidList, fromDate, toDate));
//			if(!BasicUtils.isEmpty(ventilatorList)) {
//				for(Object[] obj:ventilatorList){
//					String tempuhid=obj[1].toString();
//					VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
//					if(currentObject.getBoxName()!=null) {
//						currentObject.setVentilatorTotalCount(Integer.parseInt(obj[0].toString()));
//						vitalTrackerMap.replace(tempuhid, currentObject);
//					}
//				}
//			}


			// Total Days
			//int diffInDays = (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
		//	diffInDays=3*(diffInDays+1);

			double diffInDays = (double) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 ));
				diffInDays=((diffInDays+1)/8);

			for (Map.Entry<String,AnalyticsUsagePojo> entry : analyticsUsageObjectMap.entrySet()){
				AnalyticsUsagePojo usageObj=entry.getValue();

				if(!BasicUtils.isEmpty(usageObj.getUhid())) {
					//Calculating Length Of Stay
					long timeDifference = (toDate.getTime() - fromDate.getTime());
					double los = timeDifference / (1000 * 60 * 60 * 24);

					if (BasicUtils.isEmpty(los) || los < 1) {
						los = 1;
					}

					if (!BasicUtils.isEmpty((usageObj.getLengthOfStay() != null) && ((usageObj.getLengthOfStay()) < los))) {
						los = usageObj.getLengthOfStay();
					}

// assessments denominator

					int totalAssessmentPresent=0;


					totalAssessmentPresent = 	getAssessmentCountInaPeriod(fromDate, toDate, usageObj.getUhid() );


//						String assessmentMessageSql = HqlSqlQueryConstants.getActiveAssessmentStatus(usageObj.getUhid());
//					List<Object[]> activeAssessmentList = inicuDao.getListFromNativeQuery(assessmentMessageSql);
//					if (!BasicUtils.isEmpty(activeAssessmentList)) {
//						Iterator<Object[]> itr = activeAssessmentList.iterator();
//						while (itr.hasNext()) {
//							Object[] obj = itr.next();
//
//							totalAssessmentPresent++;
// 						}
//
//					}

					System.out.println("totalAssessmentPresent for "+usageObj.getUhid()+"------"+totalAssessmentPresent);
					double totalPassiveAssessmentPresent=0;

					String assessmentMessageSql = HqlSqlQueryConstants.getPassiveAssessmentStatus(usageObj.getUhid());
					List<Object[]>  activeAssessmentList = inicuDao.getListFromNativeQuery(assessmentMessageSql);
					if (!BasicUtils.isEmpty(activeAssessmentList)) {
						Iterator<Object[]> itr = activeAssessmentList.iterator();
						while (itr.hasNext()) {
							Object[] obj = itr.next();
							totalPassiveAssessmentPresent++;
						}

					}

// assessments denominator end

					double assessmentCount = 0;
					double stableNotesCount = 0;
					List<String> assessmentName=new ArrayList<>();

					for (VwDoctornotesListFinal element : assessmentUsageList) {


						if(element.getUhid().equals(usageObj.getUhid())) {

							if (element.getSaEvent().compareTo("Stable Notes") == 0) {
								stableNotesCount++;

							} else {
								String name=element.getSaEvent();
								if(assessmentName.contains(name)) {
									// Do nothing
								}else{
									// Add to the arrayList
									assessmentName.add(name);

								}
								assessmentCount++;
							}
						}
					}



					// Doctor Values
					double qualityAssesment = 0;
					double qualityStableAssesment = 0;

					double adoptionScore = 0;
					double doctorQualityScore = 0;
					int docDenominator = 3;

					if (usageObj.getInitialAssessmentScore() != null ) {

						double qualityInitial = Double.parseDouble(usageObj.getInitialAssessmentScore());


						System.out.println("qualityInitial  in getInitialAssessment -----"
								+ "----------"+qualityInitial);

						adoptionScore += qualityInitial;

						System.out.println("adoptionScore----------"+adoptionScore);

//						adoptionScore++;
						//doctorQualityScore++;
					}

//					if (usageObj.getAssessment() != null && usageObj.getAssessment() > 0) {
//
//					}


					boolean noAssessment = true;

					if(totalAssessmentPresent!=0 ) {
//						double totalAssess = (diffInDays*totalAssessmentPresent)+(diffInDays*totalPassiveAssessmentPresent/3);
//						totalAssess = Math.ceil(totalAssess);
//
						String assessmentStr = (int)assessmentCount + "/" + (int)totalAssessmentPresent +"";
						usageObj.setAssessmentStr(assessmentStr);


						double qualityAssessment = (assessmentCount)/( totalAssessmentPresent) > 1 ? 1 :
								(assessmentCount)/ ( totalAssessmentPresent);

						adoptionScore+=qualityAssessment;
						//qualityAssesment = usageObj.getAssessment() / los;
						noAssessment = false;


						System.out.println("adoptionScore---2-------"+adoptionScore);


					}else {
						usageObj.setAssessmentStr("-");
					}


					String stablesNotesStr = "-";

					if (noAssessment ) {

						System.out.println("noAssessment is true--------stableNotesCount--"+stableNotesCount);
						System.out.println("noAssessment is true--------diffInDays--"+diffInDays);

						double qualityAssessment = stableNotesCount/ (diffInDays/3.0)> 1 ? 1 : stableNotesCount/ (diffInDays/3.0);

						adoptionScore+=qualityAssessment;

						System.out.println("adoptionScore----3------"+adoptionScore);


						System.out.println("noAssessment is true--------qualityAssessment--"+qualityAssessment);
						System.out.println("noAssessment is true--------adoptionScore--"+adoptionScore);
						//qualityStableAssesment = usageObj.getAssessment() / los;
						stablesNotesStr = (int)stableNotesCount + "/" +(int) Math.ceil(diffInDays/3) + "";

					}

					usageObj.setStableNotesStr(stablesNotesStr);

//					if (qualityAssesment >= 1 || qualityStableAssesment >= 1 || (qualityStableAssesment + qualityAssesment >= 1)) {
//						doctorQualityScore++;
//					} else if (qualityAssesment <= 1 && qualityStableAssesment <= 1) {
//						doctorQualityScore += qualityAssesment + qualityStableAssesment;
//					}

//					if (usageObj.getMedication() != null && usageObj.getMedication() > 0) {
//						adoptionScore++;
//						double qualityMedication = (usageObj.getMedication() / los) > 1 ? 1 : (usageObj.getMedication() / los);
//						doctorQualityScore += qualityMedication;
//					}
                    double exeDocNutrition = 0;
					if (usageObj.getNutrition() != null ) {
						//adoptionScore++;

						System.out.println("usageObj.getNutrition()-----4-----"+usageObj.getNutrition());



								System.out.println("usageObj.usageObj.getBabyStayDuringPeriod()()-----4-----"+usageObj.getBabyStayDuringPeriod());



						exeDocNutrition = Double.parseDouble(usageObj.getNutrition());
						double qualityNutrition = exeDocNutrition / ((double) usageObj.getBabyStayDuringPeriod()) > 1 ? 1 : (exeDocNutrition)/ ((double) usageObj.getBabyStayDuringPeriod());

						System.out.println("qualityNutrition()-----4-----"+qualityNutrition);

						adoptionScore += qualityNutrition;



						System.out.println("adoptionScore-----4-----"+adoptionScore);

					}


					usageObj.setNutrition((int)exeDocNutrition+"/"+ (int) (double) usageObj.getBabyStayDuringPeriod()); ;

//
//					if (usageObj.getLabOrder() != null && usageObj.getLabOrder() > 0) {
//						//adoptionScore++;
//						//double qualityLab = (usageObj.getLabOrder() / los) > 1 ? 1 : (usageObj.getLabOrder() / los);
//						//doctorQualityScore += qualityLab;
//					}
//
//					if (usageObj.getProcedure() != null && usageObj.getProcedure() > 0) {
//					//	adoptionScore++;
//					//	doctorQualityScore++;
//					}

//					if (usageObj.getLabReports() != null && usageObj.getLabReports() > 0) {
//						adoptionScore++;
//					}

					if (!BasicUtils.isEmpty(usageObj.getNeonatologist())) {
					//	adoptionScore++;
					}
					double adoptionScoreTemp = 0f;

					System.out.println("adoptionScoreTemp start--"+adoptionScoreTemp);

					System.out.println("adoptionScore 5-----"+adoptionScore);


					adoptionScoreTemp = (100 * adoptionScore) / docDenominator;
					adoptionScoreTemp = Math.round(adoptionScoreTemp * 100.0) / 100.0;

					System.out.println("adoptionScoreTemp last--"+adoptionScoreTemp);

					usageObj.setDoctor_adoptionScore(adoptionScoreTemp);

					adoptionScoreTemp = (100 * doctorQualityScore) / 6;
					adoptionScoreTemp = Math.round(adoptionScoreTemp * 100.0) / 100.0;
					usageObj.setDoctor_qualityScore(adoptionScoreTemp);
					double babyStayDuringPeriod = usageObj.getBabyStayDuringPeriod();

					double babyStayDuringPeriodInhrs = usageObj.getBabyStayDuringPeriodInhrs();


					// Nurse Values
					double nurseAdoptionScore = 0;
					double nurseQualityScore = 0;

					int nursingDenominator = 2;

					// nursing ventilator starts



					Timestamp startTime = toDate;
					long respHours = 0;


					StringBuilder bpdQuery = new StringBuilder("select obj from RespSupport obj where uhid = '")
							.append( usageObj.getUhid())
							.append("' and ")
							.append(" eventname != 'initial assessment' and ")

//							.append(" creationtime >= '").append( fromDate).append("' and ")
							.append(" creationtime <= '").append( toDate)
							.append("'  order by creationtime desc   ");

					System.out.println("respHours out  loop----------"+respHours);

					Timestamp endTime = toDate;

					boolean flag = true;
					List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery.toString());
					for(RespSupport resp : bpdList) {

						System.out.println("respHours in loop----------"+respHours);
							if (resp.getIsactive()) {
								startTime = resp.getCreationtime();
								System.out.println("startTime ----------"+startTime);

								if (startTime.getTime() <= fromDate.getTime()) {
									startTime = fromDate;
									flag = false;
									System.out.println("startTime ----------"+startTime);

								}

								System.out.println("endTime  in active ----------"+endTime);

								long daysBpd = ((endTime.getTime() - startTime.getTime()+60000) / (1000 * 60 * 60));



								if(daysBpd>0)	respHours = respHours + daysBpd;



								endTime = startTime;
							} else if (!resp.getIsactive()) {
								endTime = resp.getCreationtime();
								System.out.println("endTime  in noactive ----------"+endTime);

								if (endTime.getTime() <= fromDate.getTime()) {
									endTime = fromDate;
									flag = false;
								}


							}

							if (!flag) {
									break;
							}
					}
					//occupancyObj.setRespSupportDays((int)respDays);


					if(respHours>babyStayDuringPeriodInhrs){

						respHours =(int)babyStayDuringPeriodInhrs;
					}
					double ventCount = Double.parseDouble(usageObj.getVentilatorCount());

					usageObj.setVentilatorCount((int)ventCount+"/"+(int) respHours); ;

					System.out.println("nursingDenominator out  ----------"+nursingDenominator);

					if(respHours!=0) {
	nursingDenominator++;

	double qualityVitals = ventCount/ respHours * 12 > 1 ? 1 : ventCount/ respHours*12 ;

	nurseAdoptionScore += qualityVitals;


						System.out.println("nurseAdoptionScore 1"
								+ "----------"+nurseAdoptionScore);

						System.out.println("nursingDenominator in  ----------"+nursingDenominator);


					}

					// nursing vent ends








					//	if (usageObj.getAnthropometry() != null && Double.parseDouble(usageObj.getAnthropometry()) > 0) {
						
						double anthroCount = Double.parseDouble(usageObj.getAnthropometry());

					if (anthroCount>1) anthroCount = 1;

 						System.out.println("anthroCount before"
								+ "----------"+anthroCount);


						System.out.println("babyStayDuringPeriod "
								+ "----------"+babyStayDuringPeriod);
					double qualityAnthro =0;

					Calendar cal = Calendar.getInstance();
					cal.setTime(fromDate);
					boolean monday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;

					if(monday) {
						int head = usageObj.getHead();
						if (head > 0) anthroCount++;

						int height = usageObj.getHeight();
						if (height > 0) anthroCount++;

						qualityAnthro = (anthroCount / (babyStayDuringPeriod*3- respHours*3/24)) > 1.0 ?
								1 : (anthroCount / (babyStayDuringPeriod*3- respHours*3/24));
						usageObj.setAnthropometry((int)anthroCount+"/"+(int)(babyStayDuringPeriod*3- respHours*3/24));

					}else{

						 qualityAnthro = (anthroCount / (babyStayDuringPeriod- respHours/24)) > 1.0 ?
								1 : (anthroCount / (babyStayDuringPeriod- respHours/24));
						usageObj.setAnthropometry((int)anthroCount+"/"+(int)(babyStayDuringPeriod- respHours/24));



					}
						//	nurseAdoptionScore++;
					System.out.println("qualityAnthro after"
							+ "----------"+qualityAnthro);


						nurseAdoptionScore += qualityAnthro;
						
						System.out.println("nurseAdoptionScore 2"
								+ "----------"+nurseAdoptionScore);

						nurseQualityScore += qualityAnthro;
				//	}

					//if (usageObj.getVitals() != null && Integer.parseInt(usageObj.getVitals()) > 0) {
						/*nurseAdoptionScore++;
						double qualityVitals = ((usageObj.getVitals() / (los * 12)) > 1 ? 1 : (usageObj.getVitals() / (los * 12)));
						nurseQualityScore += qualityVitals;
					*/

						double qualityVitals = ((Integer.parseInt(usageObj.getVitals() )/ (babyStayDuringPeriodInhrs /2)) > 1 ? 1 : (Integer.parseInt(usageObj.getVitals()) / (babyStayDuringPeriodInhrs /2)));
						nurseAdoptionScore += qualityVitals;


					System.out.println("nurseAdoptionScore 3"
							+ "----------"+nurseAdoptionScore);
						nurseQualityScore += qualityVitals;

						//usageObj.setVitals(Integer.parseInt(usageObj.getVitals())+"/"+(babyStayDuringPeriodInhrs /2 >= Integer.parseInt(usageObj.getVitals()) ? (int)(babyStayDuringPeriodInhrs /2) : Integer.parseInt(usageObj.getVitals())  ));
					usageObj.setVitals(Integer.parseInt(usageObj.getVitals())+"/"+ (int)(babyStayDuringPeriodInhrs /2)  );

					//}


//nursing intake starts
					//get expected executions start

					StringBuilder babyFeedQuery = new StringBuilder("select obj from BabyfeedDetail obj where uhid = '")
							.append( usageObj.getUhid()).append("' and ")
							.append(" creationtime >= '").append( fromDate).append("' and ")
							.append(" creationtime <= '").append( toDate).append("'  order by creationtime desc   ");

					long expectedExecutions = 0;

					System.out.println("expectedExecutions at start"+expectedExecutions);


							List<BabyfeedDetail> babyFeedDetailListQuery = prescriptionDao.getListFromMappedObjNativeQuery(babyFeedQuery.toString());
					if(!BasicUtils.isEmpty(babyFeedDetailListQuery)) {

						 endTime = toDate;

						for(BabyfeedDetail babyfeedDetail : babyFeedDetailListQuery) {

							System.out.println("babyfeedDetail----------"+babyfeedDetail);

							System.out.println("getEntryDateTime()----------"+babyfeedDetail.getEntryDateTime());


							long diffHours = (endTime.getTime() - babyfeedDetail.getEntryDateTime().getTime())/(60 * 60 * 1000);;

							System.out.println("diffHours----------"+diffHours);

							if(diffHours > 24 ) diffHours = 24;

							endTime = babyfeedDetail.getEntryDateTime();
							long tempExe = 0 ;

							if(babyfeedDetail.getIsparentalgiven()!=null  && babyfeedDetail.getIsparentalgiven() ){
								System.out.println("getIsparentalgiven is true----------");

								tempExe = diffHours;

							}else if( babyfeedDetail.getIsenternalgiven()!=null  && babyfeedDetail.getIsenternalgiven()){
								System.out.println("getIsenternalgiven is true----------");

								System.out.println("babyfeedDetail.getDuration() is ----------"+babyfeedDetail.getFeedduration());

								try {
									tempExe = diffHours / Long.parseLong(babyfeedDetail.getFeedduration());
								}catch(Exception e){
									e.printStackTrace();
									tempExe = diffHours/2;

								}
							}
							System.out.println("tempExe----------"+tempExe);

							expectedExecutions+=tempExe;

							System.out.println("expectedExecutions last----------"+expectedExecutions);



						}
					}

					//get expected executions end


					double intake = 0;
					if (usageObj.getIntake_output() != null ) {

						intake = Double.parseDouble(usageObj.getIntake_output());

					}

					System.out.println("nursingDenominator out  ----------"+nursingDenominator);

					if (expectedExecutions<intake) expectedExecutions =(int) intake;

					if(expectedExecutions!=0) {
						nursingDenominator++;

						double qualityIntake = ((intake / expectedExecutions) > 1 ? 1 : (intake / expectedExecutions));
						nurseAdoptionScore += qualityIntake;


						System.out.println("nurseAdoptionScore 4"
								+ "----------"+nurseAdoptionScore);
						System.out.println("nursingDenominator in  ----------"+nursingDenominator);


					}
					//nurseAdoptionScore++;
					//double qualityIntake = ((usageObj.getIntake_output() / (los * 12)) > 1 ? 1 : (usageObj.getIntake_output() / (los * 12)));
					//	nurseQualityScore += qualityIntake;



					usageObj.setIntake_output((int)intake+"/"+expectedExecutions) ;



//nutriton intake ends

					// nursing medication starts


					int totalOrders = 0;
					int totalExecutions = 0;

					StringBuilder babyMedQuery = new StringBuilder("select obj from BabyPrescription obj where uhid = '")
							.append( usageObj.getUhid()).append("' and ")
							.append(" creationtime >= '").append( fromDate).append("' and ")
							.append(" creationtime <= '").append( toDate).append("'  order by creationtime desc   ");

					 endTime = toDate;


					List<BabyPrescription> babyMedLists = prescriptionDao.getListFromMappedObjNativeQuery(babyMedQuery.toString());
					for(BabyPrescription obj : babyMedLists) {
						if(!BasicUtils.isEmpty(obj.getFreq_type())) {
							if(obj.getFreq_type().equalsIgnoreCase("Intermittent") && !BasicUtils.isEmpty(obj.getFrequency())) {
								String lowerFreqType = "";
								Float lowerFreqValue = -1f;
								String queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + obj.getFrequency()  + "'";
								List<RefMedfrequency> refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
								if(!BasicUtils.isEmpty(refMedFreqList)) {
									lowerFreqType = refMedFreqList.get(0).getFreqvalue();
									String freqList[] = lowerFreqType.split(" hr");
									lowerFreqValue = Float.parseFloat(freqList[0]);
									//lowerFreqValue = 24 / lowerFreqValue;
									if(!BasicUtils.isEmpty(obj.getEnddate())) {
										long nicuStay = ((obj.getEnddate().getTime() - obj.getStartdate().getTime()) / ( 60 * 60 * 1000));
										//nicuStay = nicuStay + 1;
										totalOrders += (nicuStay/(lowerFreqValue));
									}else {
										//if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
//											java.sql.Date d = new Date(new java.util.Date().getTime());
//											Timestamp today = new Timestamp(d.getTime());
										try {
											long nicuStay = ((endTime.getTime() - obj.getStartdate().getTime()) / (60 * 60 * 1000));

											//	nicuStay = nicuStay + 1;
											totalOrders += (nicuStay / (lowerFreqValue));

										}catch(Exception e){
											System.out.println("Exception caught :"+e);
										}
//										} else {
//											long nicuStay = (babyObj.getDischargeddate().getTime() - obj.getStartdate().getTime())
//													/ (24 * 60 * 60 * 1000);
//											nicuStay = nicuStay + 1;
//											totalOrders += (nicuStay*(lowerFreqValue));
//										}


									}
								}
							}else {
								if(obj.getFreq_type().equalsIgnoreCase("STAT"))
									totalOrders += 1;
								if(obj.getFreq_type().equalsIgnoreCase("Continuous")) {
									if(!BasicUtils.isEmpty(obj.getEnddate())) {
										long nicuStay = ((obj.getEnddate().getTime() - obj.getStartdate().getTime()) / ( 60 * 60 * 1000));
										nicuStay = nicuStay + 1;
										totalOrders += nicuStay;
									}else {
										//if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
//											java.sql.Date d = new Date(new java.util.Date().getTime());
//											Timestamp today = new Timestamp(d.getTime());
											long nicuStay = ((endTime.getTime() - obj.getStartdate().getTime()) / ( 60 * 60 * 1000));

											nicuStay = nicuStay + 1;
											totalOrders += nicuStay;
//										} else {
//											long nicuStay = (babyObj.getDischargeddate().getTime() - obj.getStartdate().getTime())
//													/ (24 * 60 * 60 * 1000);
//											nicuStay = nicuStay + 1;
//											totalOrders += nicuStay;
//										}
									}
								}
							}
						}

						StringBuilder medQuery = new StringBuilder("select obj from NursingMedication obj where baby_presid = '")
								.append( obj.getBabyPresid()).append("' and ")
								.append(" creationtime >= '").append( fromDate).append("' and ")
								.append(" creationtime <= '").append( toDate).append("'  order by creationtime desc   ");


						//String medQuery = "select obj from NursingMedication obj where baby_presid = '" + obj.getBabyPresid() + "' order by given_time asc";
						List<NursingMedication> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery.toString());
						if(!BasicUtils.isEmpty(medList)) {
							totalExecutions += medList.size();
						}
					}

//					if(totalOrders!=0)		{
//						double qualityMedication = ((totalExecutions / totalOrders) > 1 ? 1 : (totalExecutions / totalOrders));
//						nurseAdoptionScore += qualityMedication;
//
//
//						System.out.println("nurseAdoptionScore 5"
//								+ "----------"+nurseAdoptionScore);
//
//					}


					usageObj.setMedication((int)totalExecutions+"/"+(int) totalOrders); ;

					System.out.println("nursingDenominator out  ----------"+nursingDenominator);


					if(totalOrders!=0) {

							nursingDenominator++;



						double qualityMedication = ((totalExecutions / totalOrders) > 1 ? 1 : (totalExecutions / totalOrders));
						nurseAdoptionScore += qualityMedication;



						System.out.println("nurseAdoptionScore 6"
								+ "----------"+nurseAdoptionScore);
						System.out.println("nursingDenominator in  ----------"+nursingDenominator);


					}


					//nursing medication ends




//					if (usageObj.getEvents() != null && usageObj.getEvents() > 0) {
//						nurseAdoptionScore++;
//						nurseQualityScore++;
//					}
//
//					if (usageObj.getSample_sent() != null && usageObj.getSample_sent() > 0) {
//						nurseAdoptionScore++;
//						double qualityLab = (usageObj.getLabOrder() / los) > 1 ? 1 : (usageObj.getLabOrder() / los);
//						nurseQualityScore += qualityLab;
//					}


					System.out.println("nurseAdoptionScore---"
							+ "----------"+nurseAdoptionScore);

					System.out.println("nursingDenominator---"
							+ "----------"+nursingDenominator);


					adoptionScoreTemp = (100 * nurseAdoptionScore) / nursingDenominator;
					adoptionScoreTemp = Math.round(adoptionScoreTemp * 100.0) / 100.0;
					usageObj.setNurse_adoptionScore(adoptionScoreTemp);

				
					System.out.println("adoptionScoreTemp set"
							+ "----------"+adoptionScoreTemp);

					adoptionScoreTemp = (100 * nurseQualityScore) / 5;
					adoptionScoreTemp = Math.round(adoptionScoreTemp * 100.0) / 100.0;
					usageObj.setNurse_qualityScore(adoptionScoreTemp);

//					if(stableNotesCount!=0){
//
//					}

					returnList.add(usageObj);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "Test",
					"getAnalyticsUsage UHID Data", BasicUtils.convertErrorStacktoString(e));
		}

		return returnList;
	}




	private HashMap getInitialAssessmentCalculation(String uhid, Timestamp fromDate,  Timestamp toDate ,HashMap returnMap, AnalyticsUsagePojo usageObj) {

		double mandatoryFieldsCount = 0;
		double essentialFieldsCount = 0;
		double intialAssessmentMandatoryDenominator = 0;

		double intialAssessmentEssentialDenominator = 0;



		double reasonForAdmission = 0;
		double antenatalHistory = 0;
		double birthToNICU = 0;
		double statusAtAdmission = 0;
		double genPhysicalExam = 0;
		double systematicPhysicalExam = 0;

		//Fields
		String reasonForAdmissionField = "No";
		String gpalField = "No";
		String conceptionField = "No";
		String gestationByField = "No";
		String maternalMedicalDiseaseField = "No";
		String maternalInfectionsField = "No";
		String otherRiskField = "No";
		String antenatalSteroidField = "No";
		String labourField = "No";
		String presenationField = "No";
		String modeOfDeliveryField = "No";
		String birthToNICUField = "No";
		String birthWeightField = "No";
		String admissionWeightField = "No";
		String genPhysicalExamField = "No";
		String systematicPhysicalExamField = "No";


		StringBuilder reasonQuery =new StringBuilder("select obj from ReasonAdmission obj where uhid = '")
				.append(uhid)
				.append("'");
		List<ReasonAdmission> reasonAdmissionList = prescriptionDao.getListFromMappedObjNativeQuery(reasonQuery.toString());

		if( reasonAdmissionList != null && reasonAdmissionList.size()>0) {
			mandatoryFieldsCount++;
			reasonForAdmission++;
			reasonForAdmissionField="-";
		}
		intialAssessmentMandatoryDenominator++;



		boolean gpalGiven = false ;
		boolean maternalMedicalDisease = false ;
		boolean maternalInfection = false ;
		boolean otherRiskFactors = false ;




		//GPAL, Risk Factors and other antenatal factors like medications, steroids
		StringBuilder antenatalQuery =new StringBuilder( "select obj from AntenatalHistoryDetail obj where uhid = '")
				.append(uhid)
				.append("' order by creationtime desc");
		List<AntenatalHistoryDetail> antenatalList = prescriptionDao.getListFromMappedObjNativeQuery(antenatalQuery.toString());
		if(!BasicUtils.isEmpty(antenatalList)) {
			AntenatalHistoryDetail obj = antenatalList.get(0);
			if (!BasicUtils.isEmpty(obj.getgScore()) && obj.getgScore() != 0) {
				gpalGiven = true;
			}
			if (!BasicUtils.isEmpty(obj.getpScore()) && obj.getpScore() != 0) {
				gpalGiven = true;
			}
			if (!BasicUtils.isEmpty(obj.getaScore()) && obj.getaScore() != 0) {
				gpalGiven = true;
			}
			if (!BasicUtils.isEmpty(obj.getlScore()) && obj.getlScore() != 0) {
				gpalGiven = true;
			}


			if (!BasicUtils.isEmpty(usageObj.getInout_patient_status()) && "In Born".equalsIgnoreCase(usageObj.getInout_patient_status())) {

				if (!BasicUtils.isEmpty(obj.getConceptionType())) {
					essentialFieldsCount++;
					antenatalHistory++;
					conceptionField = "-";
				}
				intialAssessmentEssentialDenominator++;


				if (!BasicUtils.isEmpty(obj.getIsLabour())) {
					mandatoryFieldsCount++;
					antenatalHistory++;
					labourField = "-";

				}
				intialAssessmentMandatoryDenominator++;


				if (!BasicUtils.isEmpty(obj.getPresentationType())) {
					mandatoryFieldsCount++;
					antenatalHistory++;
					presenationField = "-";
				}
				intialAssessmentMandatoryDenominator++;


				if (!BasicUtils.isEmpty(obj.getModeOfDelivery())) {
					mandatoryFieldsCount++;
					antenatalHistory++;
					modeOfDeliveryField = "-";
				}
				intialAssessmentMandatoryDenominator++;

			}


			if (!BasicUtils.isEmpty(obj.getHypertension())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getGestationalHypertension())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getDiabetes())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getGdm())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getChronicKidneyDisease())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getHypothyroidism())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getHypertension())) {
				maternalMedicalDisease = true;
			}


			if (!BasicUtils.isEmpty(obj.getHyperthyroidism())) {
				maternalMedicalDisease = true;
			}

			if (!BasicUtils.isEmpty(obj.getNoneDisease())) {
				maternalMedicalDisease = true;
			}

			if (!BasicUtils.isEmpty(obj.getFever())) {
				maternalInfection = true;
			}


			if (!BasicUtils.isEmpty(obj.getUti())) {
				maternalInfection = true;
			}


//			if(!BasicUtils.isEmpty(obj.()) ) {
//				maternalMedicalDisease = true;			}


			if (!BasicUtils.isEmpty(obj.getNoneInfection())) {
				maternalInfection = true;
			}


			if (!BasicUtils.isEmpty(obj.getProm())) {
				otherRiskFactors = true;
			}

			if (!BasicUtils.isEmpty(obj.getPprom())) {
				otherRiskFactors = true;
			}

			if (!BasicUtils.isEmpty(obj.getPrematurity())) {
				otherRiskFactors = true;
			}

			if (!BasicUtils.isEmpty(obj.getChorioamniotis())) {
				otherRiskFactors = true;
			}

			if (!BasicUtils.isEmpty(obj.getOligohydraminos())) {
				otherRiskFactors = true;
			}

			if (!BasicUtils.isEmpty(obj.getPolyhydraminos())) {
				otherRiskFactors = true;
			}

			if (!BasicUtils.isEmpty(obj.getNoneOther())) {
				otherRiskFactors = true;
			}


			if (!BasicUtils.isEmpty(obj.getIsAntenatalSteroidGiven())) {
				mandatoryFieldsCount++;
				antenatalHistory++;
				antenatalSteroidField = "-";

			}
			intialAssessmentMandatoryDenominator++;

		}


		if( gpalGiven) {
			essentialFieldsCount++;
			antenatalHistory++;
gpalField="-";
		}
		intialAssessmentEssentialDenominator++;


		if( maternalMedicalDisease) {
			mandatoryFieldsCount++;
			antenatalHistory++;
maternalMedicalDiseaseField="-";
		}
		intialAssessmentMandatoryDenominator++;


		if( maternalInfection) {
			mandatoryFieldsCount++;
			antenatalHistory++;
maternalInfectionsField="-";
		}
		intialAssessmentMandatoryDenominator++;


		if( otherRiskFactors) {
			mandatoryFieldsCount++;
			antenatalHistory++;
otherRiskField="-";
		}
		intialAssessmentMandatoryDenominator++;


		if( returnMap.get("gestationdaysbylmp")!=null && !BasicUtils.isEmpty(returnMap.get("gestationdaysbylmp"))) {

			mandatoryFieldsCount++;
			antenatalHistory++;
gestationByField="-";
		}
		intialAssessmentMandatoryDenominator++;

		//APGAR

		boolean apgarFlag = false;
		StringBuilder birthToNicuQuery =new StringBuilder( "select obj from BirthToNicu obj where uhid = '")
				.append(uhid)
				.append("' order by creationtime desc");

		List<BirthToNicu> birthToNicuList = prescriptionDao.getListFromMappedObjNativeQuery(birthToNicuQuery.toString());
		if(!BasicUtils.isEmpty(birthToNicuList)) {
			BirthToNicu obj = birthToNicuList.get(0);
			if(!BasicUtils.isEmpty(obj.getApgarOnemin())) {
				apgarFlag = true;
			}
			if(!BasicUtils.isEmpty(obj.getApgarFivemin())) {
				apgarFlag = true;
			}
			if(!BasicUtils.isEmpty(obj.getApgarAvailable())) {
				apgarFlag = true;
			}
		}


		if (!BasicUtils.isEmpty(usageObj.getInout_patient_status()) &&  "Out Born".equalsIgnoreCase(usageObj.getInout_patient_status()) )
		{

			System.out.println("out born case");




		} else {


			System.out.println("in born case");


			if( apgarFlag) {
				mandatoryFieldsCount++;
				birthToNICU++;
				birthToNICUField="-";
			}
			intialAssessmentMandatoryDenominator++;


		}




		if( returnMap.get("birthweight")!=null && !BasicUtils.isEmpty(returnMap.get("birthweight"))) {

			mandatoryFieldsCount++;
			statusAtAdmission++;
			birthWeightField="-";
		}
		intialAssessmentMandatoryDenominator++;



		if( returnMap.get("admissionweight")!=null && !BasicUtils.isEmpty(returnMap.get("admissionweight"))) {

			mandatoryFieldsCount++;
			statusAtAdmission++;
			admissionWeightField="-";
		}
		intialAssessmentMandatoryDenominator++;


		//General Physician Examination
		StringBuilder genPhyQuery =new StringBuilder( "select obj from GenPhyExam obj where uhid = '")
				.append(uhid)
				.append("' order by creationtime desc");

		boolean genExam = false;

		List<GenPhyExam> genPhyList = prescriptionDao.getListFromMappedObjNativeQuery(genPhyQuery.toString());
		if(!BasicUtils.isEmpty(genPhyList)) {
			GenPhyExam obj = genPhyList.get(0);
			if(!BasicUtils.isEmpty(obj.getApearance())) {
				//essentialFieldsCount++;
				//genPhysicalExam++;
				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getChest()) && !obj.getChest().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getChest()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}


			//if(!BasicUtils.isEmpty(obj.getHead_neck()) && !obj.getHead_neck().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getHead_neck()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getAbdomen()) && !obj.getAbdomen().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getAbdomen()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getPalate()) && !obj.getPalate().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getPalate()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			if(!BasicUtils.isEmpty(obj.getAnal())) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			if(!BasicUtils.isEmpty(obj.getLip()) ) {
			//if(!BasicUtils.isEmpty(obj.getLip()) && !obj.getLip().equalsIgnoreCase("Normal")) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getGenitals()) && !obj.getGenitals().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getGenitals()) ) {
//
				genExam= true;
			}

		//	if(!BasicUtils.isEmpty(obj.getEyes()) && !obj.getEyes().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getEyes()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;

				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getReflexes()) && !obj.getReflexes().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getReflexes()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getSkin()) && !obj.getSkin().equalsIgnoreCase("Normal")) {
			if(!BasicUtils.isEmpty(obj.getSkin()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

			//if(!BasicUtils.isEmpty(obj.getCong_malform()) && !obj.getCong_malform().equalsIgnoreCase("No")) {
			if(!BasicUtils.isEmpty(obj.getCong_malform()) ) {
//				essentialFieldsCount++;
//				genPhysicalExam++;
				genExam= true;
			}

		}



if(	genExam 	) {
		essentialFieldsCount++;
		genPhysicalExam++;
		genPhysicalExamField="-";
	}
	intialAssessmentEssentialDenominator++;


        boolean systematicExam = false;
		// Systemic Exam Result Fetching function
		String queryBabySystemicExamResult = "select obj from  SystemicExam obj where uhid='" + uhid + "'";
		List<SystemicExam> babySystemicExamResultList = patientDao
				.getListFromMappedObjNativeQuery(queryBabySystemicExamResult);
		if (!BasicUtils.isEmpty(babySystemicExamResultList)) {
			String babySystemicExamData = null;
			SystemicExam systemicExamObj = babySystemicExamResultList.get(0);
			List<String> systemicExamDataList = new ArrayList<>();
			if (systemicExamObj != null) {
				if (systemicExamObj.getApnea() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getAsphyxia() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getClabsi() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getIvh() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getJaundice() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getPneumothorax() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getPphn() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getRds() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getSeizures() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getSepsis() != null) {
					systematicExam = true;
				}
				if (systemicExamObj.getVap() != null) {
					systematicExam = true;
				}
			}
		}
if(systematicExam 	) {
	essentialFieldsCount++;
	systematicPhysicalExam++;
	systematicPhysicalExamField="-";
}
			intialAssessmentEssentialDenominator++;

			System.out.println("essentialFieldsCount-------------"+essentialFieldsCount);
		System.out.println("intialAssessmentEssentialDenominator-------------"+intialAssessmentEssentialDenominator);
		System.out.println("mandatoryFieldsCount-------------"+mandatoryFieldsCount);
		System.out.println("intialAssessmentMandatoryDenominator-------------"+intialAssessmentMandatoryDenominator);


		double initialAssessmentEssentialScore = (( essentialFieldsCount/intialAssessmentEssentialDenominator) * 100/80)>1?1:(( essentialFieldsCount/intialAssessmentEssentialDenominator) * 100/80) ;
		double initialAssessmentMandatoryScore = (mandatoryFieldsCount  /   intialAssessmentMandatoryDenominator) ;

		double initialAssessmentScore =( (mandatoryFieldsCount / intialAssessmentMandatoryDenominator) + initialAssessmentEssentialScore )/2;


		System.out.println("initialAssessmentEssentialScore-------------"+initialAssessmentEssentialScore);
		System.out.println("initialAssessmentMandatoryScore-------------"+initialAssessmentMandatoryScore);
		System.out.println("initialAssessmentScore-------------"+initialAssessmentScore);

		String initialAssessmentEssentialScoreDisplay = singleDecimal.format(initialAssessmentEssentialScore);

		String initialAssessmentMandatoryScoreDisplay = singleDecimal.format(initialAssessmentMandatoryScore);

		returnMap.put("initialAssessmentEssentialScore",( (int) essentialFieldsCount) +"/"+  ( (int)  intialAssessmentEssentialDenominator)+" ("+initialAssessmentEssentialScoreDisplay+")");


		returnMap.put("initialAssessmentMandatoryScore",(  (int)  mandatoryFieldsCount) +"/"+  ( (int) intialAssessmentMandatoryDenominator)+" ("+initialAssessmentMandatoryScoreDisplay+")");



		String initialAssessmentScoreDisplay = singleDecimal.format(initialAssessmentScore);



		returnMap.put("initialAssessmentScoreDisplay",(int) (mandatoryFieldsCount +  essentialFieldsCount) +"/"+ (int)  (intialAssessmentEssentialDenominator+ intialAssessmentMandatoryDenominator)+" ("+initialAssessmentScoreDisplay+")");

	//	returnMap.put("initialAssessmentScore", (mandatoryFieldsCount +  essentialFieldsCount) / (intialAssessmentEssentialDenominator+ intialAssessmentMandatoryDenominator));
			returnMap.put("initialAssessmentScore",initialAssessmentScore);


		returnMap.put("systematicPhysicalExam", (int)systematicPhysicalExam+"/"+"1");

		returnMap.put("reasonForAdmission", (int)reasonForAdmission+"/"+"1");
		returnMap.put("antenatalHistory", (int)antenatalHistory+"/"+"10");
		returnMap.put("birthToNICU", (int)birthToNICU+"/"+"1");
		returnMap.put("statusAtAdmission", (int)statusAtAdmission+"/"+"2");
		returnMap.put("genPhysicalExam", (int)genPhysicalExam+"/"+"1");



		usageObj.setReasonForAdmissionField(reasonForAdmissionField);
		usageObj.setgpalField(gpalField);
		usageObj.setConceptionField(conceptionField);
		usageObj.setGestationByField(gestationByField);
		usageObj.setMaternalMedicalDiseaseField(maternalMedicalDiseaseField);
		usageObj.setMaternalInfectionsField(maternalInfectionsField);
		usageObj.setOtherRiskField(otherRiskField);
		usageObj.setAntenatalSteroidField(antenatalSteroidField);
		usageObj.setLabourField(labourField);
		usageObj.setPresenationField(presenationField);
		usageObj.setModeOfDeliveryField(modeOfDeliveryField);
		usageObj.setBirthToNICUField(birthToNICUField);
		usageObj.setBirthWeightField(birthWeightField);
		usageObj.setAdmissionWeightField(admissionWeightField);
		usageObj.setGenPhysicalExamField(genPhysicalExamField);
		usageObj.setSystematicPhysicalExamField(systematicPhysicalExamField);

		return returnMap;
	}



	Boolean checkDeviceHealth(Timestamp dt1,int offset){
		Timestamp dt2 = new Timestamp((new java.util.Date().getTime()));
		dt2 = new Timestamp(dt2.getTime() - offset);
		dt1=new Timestamp(dt1.getTime()-offset);

		long diff = dt2.getTime() - dt1.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));

//		if (diffInDays > 1) {
//			System.err.println("Difference in number of days (2) : " + diffInDays);
//			return false;
//		} else if (diffHours > 24) {
//
//			System.err.println(">24");
//			return false;
//		} else if ((diffHours == 24) && (diffMinutes >= 1)) {
//			System.err.println("minutes");
//			return false;
//		}

		if (diffMinutes >= 0 && diffMinutes<=10) {
			System.err.println("minutes");
			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VitalTracker> getVitalsData(String fromDateStr, String toDateStr, String branchName) {
		System.out.println("********TIME CHECK********" + fromDateStr);
		List<VitalTracker> returnList = new ArrayList<VitalTracker>();
		HashMap<String, VitalTracker> vitalTrackerMap=new HashMap<>();
		try {

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			List<BabyDetail> uhidCompleteList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getUsageBabyList(fromDate, toDate, branchName));

			String uhidList="";

			for(BabyDetail baby:uhidCompleteList){
				if (uhidList.isEmpty()) {
					uhidList += "'" + baby.getUhid() + "'";
				} else {
					uhidList += ", '" + baby.getUhid() + "'";
				}
			}

			if (!BasicUtils.isEmpty(uhidList)) {

				for (BabyDetail obj : uhidCompleteList) {

					VitalTracker trackerObj = new VitalTracker();

					trackerObj.setUhid(obj.getUhid());
					
					if(!BasicUtils.isEmpty(obj.getBabyNumber())) {
						String fullBabyName = obj.getBabyname() + " " + obj.getBabyNumber();
						trackerObj.setBabyname(fullBabyName);
					}else {
						trackerObj.setBabyname(obj.getBabyname());
					}
					trackerObj.setBabyAdmissionStatus(obj.getAdmissionstatus());

					Long numberOfMinutes = (Long.parseLong(toDateStr) - Long.parseLong(fromDateStr)) / (1000 * 60);

						//Retriving the date of admission
					Timestamp dateofadmission = null;
					try {
						dateofadmission = new Timestamp(obj.getDateofadmission().getTime() - offset);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try{
							int hours = Integer.parseInt(obj.getTimeofadmission().substring(0, 2));
							int minutes = Integer.parseInt(obj.getTimeofadmission().substring(3, 5));
							if (obj.getTimeofadmission().substring(6, 8).equals("PM") && hours != 12) {
								hours = hours + 12;
							}

							dateofadmission.setHours(hours);
							dateofadmission.setMinutes(minutes);
							dateofadmission.setSeconds(0);
						}catch(NumberFormatException ex){ // handle your exception
							System.out.println(ex);
						}


						//Skipping out those minutes in which baby is absent from NICU
						if (Long.parseLong(fromDateStr) < dateofadmission.getTime() && Long.parseLong(toDateStr) > dateofadmission.getTime()) {
							Long extraMinutes = (dateofadmission.getTime() - Long.parseLong(fromDateStr)) / (1000 * 60);
							System.out.println("******" + extraMinutes + "*********");
							numberOfMinutes = numberOfMinutes - extraMinutes;
						}
						if (Long.parseLong(toDateStr) < dateofadmission.getTime()) {
							numberOfMinutes = (long) 0;
						}

						if (!BasicUtils.isEmpty(obj.getDischargeddate()) && obj.getAdmissionstatus() == false && numberOfMinutes != 0) {
							if (Long.parseLong(fromDateStr) < obj.getDischargeddate().getTime() && Long.parseLong(toDateStr) > obj.getDischargeddate().getTime()) {
								Long extraMinutes = (Long.parseLong(toDateStr) - obj.getDischargeddate().getTime()) / (1000 * 60);
								numberOfMinutes = numberOfMinutes - extraMinutes;
							}
							if (Long.parseLong(fromDateStr) > obj.getDischargeddate().getTime()) {
								numberOfMinutes = (long) 0;
							}
						}
						trackerObj.setNumberOfMinutes(numberOfMinutes);
						vitalTrackerMap.put(obj.getUhid(),trackerObj);
					}

					// Fetch Device Attached to Each baby
				    String deviceIdStr="";
					List<Object[]> deviceBoxName=inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getDeviceName(uhidList,fromDate,toDate));

					if(!BasicUtils.isEmpty(deviceBoxName)) {
						for(Object[] obj:deviceBoxName){

							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							String currentBox=currentObject.getBoxName();
							String boxName=obj[0].toString();
							String deviceId=obj[2].toString();

							if (!BasicUtils.isEmpty(boxName)) {
								if(currentBox == "" || currentBox==null){
									currentBox = boxName;
								}
								else{
									if(!currentBox.contains(boxName)) {
										currentBox = currentBox + "," + boxName;
									}
								}
							}

							if(!BasicUtils.isEmpty(deviceId)) {
								if (deviceIdStr.isEmpty()) {
									deviceIdStr += "'" + deviceId + "'";
								} else {
									deviceIdStr += ", '" + deviceId + "'";
								}
							}

							currentObject.setBoxName(currentBox);
							vitalTrackerMap.replace(tempuhid,currentObject);

						}
					}

					// Fetch the Device Brand Name and Its Type
					List<Object[]> deviceBrandnames=inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getDeviceBrand(deviceIdStr));

					if(!BasicUtils.isEmpty(deviceBoxName)) {
						for(Object[] obj:deviceBoxName){

							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							String deviceId=obj[2].toString();
							String boxName=obj[0].toString();

							String vitalDeviceBoxName=currentObject.getVitalDeviceBoxName();
							String vitalDeviceType=currentObject.getVitalDeviceType();

                            String ventilatorDeviceBoxName=currentObject.getVentilatorDeviceBoxName();
                            String ventilatorDeviceType=currentObject.getVentilatorDeviceType();

							if(!BasicUtils.isEmpty(deviceId)) {
								// Looping to find the brand name for that deviceid
								for(Object[] brandObj:deviceBrandnames) {

									String deviceBrandId = brandObj[3].toString();
									if (deviceBrandId != null && deviceBrandId != "") {

										if(deviceBrandId.equalsIgnoreCase(deviceId)){

										    String deviceType=brandObj[0].toString();
										    if(deviceType.equalsIgnoreCase("Monitor")){
                                                String devicename=brandObj[0].toString()+"("+brandObj[1].toString()+ ", " + brandObj[4].toString() + ")";

                                                // Box Name
                                                if(vitalDeviceBoxName == "" || vitalDeviceBoxName==null){
                                                    vitalDeviceBoxName = boxName;
                                                }
                                                else{
                                                    if(!vitalDeviceBoxName.contains(devicename)) {
                                                        vitalDeviceBoxName = vitalDeviceBoxName + "," + boxName;
                                                    }
                                                }

                                                // Device Type
                                                if(vitalDeviceType == "" || vitalDeviceType==null){
                                                    vitalDeviceType = devicename;
                                                }
                                                else{
                                                    if(!vitalDeviceType.contains(devicename)) {
                                                        vitalDeviceType = vitalDeviceType + "," + devicename;
                                                    }
                                                }
                                            }else if(deviceType.equalsIgnoreCase("Ventilator")){
                                                String devicename=brandObj[0].toString()+"("+brandObj[1].toString()+")";

                                                // Box Name
                                                if(ventilatorDeviceBoxName == "" || ventilatorDeviceBoxName==null){
                                                    ventilatorDeviceBoxName = boxName;
                                                }
                                                else{
                                                    if(!ventilatorDeviceBoxName.contains(devicename)) {
                                                        ventilatorDeviceBoxName = ventilatorDeviceBoxName + "," + boxName;
                                                    }
                                                }

                                                // Device Type
                                                if(ventilatorDeviceType == "" || ventilatorDeviceType==null){
                                                    ventilatorDeviceType = devicename;
                                                }
                                                else{
                                                    if(!ventilatorDeviceType.contains(devicename)) {
                                                        ventilatorDeviceType = ventilatorDeviceType + "," + devicename;
                                                    }
                                                }
                                            }
										}
									}
								}
							}
							currentObject.setVentilatorDeviceBoxName(ventilatorDeviceBoxName);
							currentObject.setVentilatorDeviceType(ventilatorDeviceType);

							currentObject.setVitalDeviceBoxName(vitalDeviceBoxName);
							currentObject.setVitalDeviceType(vitalDeviceType);

							vitalTrackerMap.replace(tempuhid,currentObject);
						}
					}


					// Fetch the total Data point s received
					List<Object[]> vitalList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsageVital(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(vitalList)) {
						for(Object[] obj:vitalList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null){
									currentObject.setVitalTotalCount(Integer.parseInt(obj[0].toString()));
									
									if(!BasicUtils.isEmpty(currentObject.getNumberOfMinutes())) {
										if((Integer.parseInt(obj[0].toString()) / currentObject.getNumberOfMinutes()) > 1.1) {
											currentObject.setNumberOfMinutes(currentObject.getNumberOfMinutes() * 60);
										}
									}

									
									vitalTrackerMap.replace(tempuhid,currentObject);
							}
						}
					}

					List<Object[]> ventilatorList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsageVentilator(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(ventilatorList)) {
						for(Object[] obj:ventilatorList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setVentilatorTotalCount(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

					List<Object[]> hrList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsageHr(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(hrList)) {
						for(Object[] obj:hrList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setHrCount(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

					List<Object[]> prList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsagePr(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(prList)) {
						for(Object[] obj:prList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setPrCount(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

					List<Object[]> spo2List = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsageSpo2(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(spo2List)) {
						for(Object[] obj:spo2List){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setSpo2Count(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}


					List<Object[]> rrList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsageRr(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(rrList)) {
						for(Object[] obj:rrList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setRrCount(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

					List<Object[]> fio2List = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsageFio2(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(fio2List)) {
						for(Object[] obj:fio2List){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setFio2Count(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

					List<Object[]> pipList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsagePip(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(pipList)) {
						for(Object[] obj:pipList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setPipCount(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

					List<Object[]> peepList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getUsagePeep(uhidList, fromDate, toDate));
					if(!BasicUtils.isEmpty(peepList)) {
						for(Object[] obj:peepList){
							String tempuhid=obj[1].toString();
							VitalTracker currentObject=vitalTrackerMap.get(tempuhid);
							if(currentObject.getBoxName()!=null) {
								currentObject.setPeepCount(Integer.parseInt(obj[0].toString()));
								vitalTrackerMap.replace(tempuhid, currentObject);
							}
						}
					}

//				String sql = "SELECT v FROM VwVitalDetail v where branchname = '" + branchName + "'";

				String sql=HqlSqlQueryConstants.getLastVitalEntry(uhidList,fromDate,toDate);
				List<Object[]> dataList = inicuPostgresUtililty.executePsqlDirectQuery(sql);

				for (Map.Entry<String,VitalTracker> entry : vitalTrackerMap.entrySet()) {
					VitalTracker vitalTrackerObj = entry.getValue();

					if(vitalTrackerObj.getBoxName()==null){
						vitalTrackerObj.setNumberOfMinutes(Long.valueOf(0));
						vitalTrackerObj.setEntryDate(null);
					}

					if (offset != 0 && (vitalTrackerObj.getBoxName()!="" && vitalTrackerObj.getBoxName()!=null)) {

						for (Object[] vitalObj : dataList) {

							String uhid=vitalObj[0].toString();

							if(uhid.equalsIgnoreCase(vitalTrackerObj.getUhid())) {

								if (!BasicUtils.isEmpty(vitalObj[1]) && vitalObj[1]!=null) {

									String entryDate=vitalObj[1].toString();
									try {
										SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
										java.util.Date parsedDate = dateFormat.parse(entryDate);
										Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

										vitalTrackerObj.setEntryDate(timestamp);
										// Check the Alter Triggering logic
										if (!checkDeviceHealth(timestamp, offset) && vitalTrackerObj.getBabyAdmissionStatus() == true) {
											vitalTrackerObj.setDeviceHealth(false);
										}
									} catch(Exception e) { //this generic but you can control another types of exception
										// look the origin of excption
									}

								}else if (vitalTrackerObj.getBabyAdmissionStatus()==true){
                                    vitalTrackerObj.setDeviceHealth(false);
                                }
							}
						}
					}else if(vitalTrackerObj.getBoxName()!="" && vitalTrackerObj.getBoxName()!=null){
						for (Object[] vitalObj : dataList) {
							String uhid=vitalObj[0].toString();
							if(uhid.equalsIgnoreCase(vitalTrackerObj.getUhid())) {
								if (!BasicUtils.isEmpty(vitalObj[1]) && vitalObj[1]!=null) {
									String entryDate=vitalObj[1].toString();
									try {
										SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
										java.util.Date parsedDate = dateFormat.parse(entryDate);
										Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

										vitalTrackerObj.setEntryDate(timestamp);
										// Check the Alter Triggering logic
										if (!checkDeviceHealth(timestamp, offset) && vitalTrackerObj.getBabyAdmissionStatus() == true) {
											vitalTrackerObj.setDeviceHealth(false);
										}
									} catch(Exception e) { //this generic but you can control another types of exception
										// look the origin of excption
									}

								}else if (vitalTrackerObj.getBabyAdmissionStatus()==true){
                                    vitalTrackerObj.setDeviceHealth(false);
                                }
							}
						}
					}

					returnList.add(vitalTrackerObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"vitalTracker Data", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	public double getZScore(String gender, String gestWeek, String paramType, double paramValue) {

		int i = 0;
		double[] dataX = new double[7];
		double[] dataY = {-3, -2, -1, 0, 1, 2 ,3};
		DecimalFormat df = new DecimalFormat("#.##");
		InterpolationUtilities interpolationObj = new InterpolationUtilities();

		if (paramType.equalsIgnoreCase("weight")) {
			paramValue /= 1000;
		}

		String inputString=gender.toString()+":"+gestWeek.toString();

		if(RefzScoreMap==null){
			RefzScoreMap=new HashMap<>();
			String query = "Select v from RefZScore v";
			List<RefZScore> resultset = inicuDao.getListFromMappedObjQuery(query);

			if(!BasicUtils.isEmpty(resultset)) {
				for (RefZScore element : resultset){
					String keyString=element.getGender().toString()+":"+element.getWeeks().toString();
					RefzScoreMap.put(keyString,element);
				}
			}else{
				return 0;
			}
		}

		RefZScore ObjectTemp=RefzScoreMap.get(inputString);

		if (!BasicUtils.isEmpty(ObjectTemp) && (paramType.equalsIgnoreCase("weight"))) {
			RefZScore obj = ObjectTemp;
			dataX[0] = obj.getMinusthree();
			dataX[1] = obj.getMinustwo();
			dataX[2] = obj.getMinusone();
			dataX[3] = obj.getZero();
			dataX[4] = obj.getOne();
			dataX[5] = obj.getTwo();
			dataX[6] = obj.getThree();

			double zscore = Double.valueOf(df.format(interpolationObj.poly_interpolate(dataX, dataY, paramValue, 3)));
			if(zscore > 3) {
				zscore = 3;
			}
			else if(zscore < -3) {
				zscore = -3;
			}

			return zscore;
		}
		return 0;
	}

	public double getIntergrowthCentile(String gender, String gestWeek, String gestDays, String paramType, double paramValue) {

		int i = 0;
		double[] dataX = new double[7];
		double[] dataY = {3, 5, 10, 50, 90, 95, 97};
		DecimalFormat df = new DecimalFormat("#.##");
		InterpolationUtilities interpolationObj = new InterpolationUtilities();

		if (paramType.equalsIgnoreCase("weight")) {
			paramValue /= 1000;
		}


		String inputString=gender.toString()+":"+gestWeek.toString()+":"+gestDays.toString();

		if(IntergrowthCentileMap==null){
			IntergrowthCentileMap=new HashMap<>();
			String query = "Select v from RefIntergrowth v";
			List<RefIntergrowth> resultset = inicuDao.getListFromMappedObjQuery(query);

			if(!BasicUtils.isEmpty(resultset)) {
				for (RefIntergrowth element : resultset){
					String keyString=element.getGender().toString()+":"+element.getWeeks().toString()+":"+element.getDays().toString();
					IntergrowthCentileMap.put(keyString,element);
				}
			}else{
				return 0;
			}
		}

		RefIntergrowth ObjectTemp=IntergrowthCentileMap.get(inputString);

		if (!BasicUtils.isEmpty(ObjectTemp) && (paramType.equalsIgnoreCase("weight"))) {
			RefIntergrowth obj = ObjectTemp;

			dataX[0] = obj.getThree();
			dataX[1] = obj.getFive();
			dataX[2] = obj.getTen();
			dataX[3] = obj.getFifty();
			dataX[4] = obj.getNinety();
			dataX[5] = obj.getNinetyfive();
			dataX[6] = obj.getNinetyseven();
			return Double.valueOf(df.format(interpolationObj.poly_interpolate(dataX, dataY, paramValue, 3)));

		}
		return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, LinkedHashMap<Timestamp, Double>> getAnalyticsUsageGraph(String fromDateStr,
																					String toDateStr, String branchName) {
		HashMap<String, LinkedHashMap<Timestamp, Double>> returnObj = new HashMap<String, LinkedHashMap<Timestamp, Double>>() {
			private static final long serialVersionUID = 1L;
			{
				put("doctor", new LinkedHashMap<Timestamp, Double>());
				put("nurse", new LinkedHashMap<Timestamp, Double>());
			}
		};

		try {
			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));
			AnalyticsUsagePojo usageObj = null;
			Double doctorScore = null;
			Double nurseScore = null;

			do {
				List<Object[]> uhidList = null;
				doctorScore = 0d;
				nurseScore = 0d;
				Timestamp tempToDate = new Timestamp(fromDate.getTime() + 24 * 60 * 60 * 1000);
				uhidList = inicuDao
						.getListFromNativeQuery(HqlSqlQueryConstants.getUsageBabyListWithdoa(fromDate, tempToDate, branchName));

				if (BasicUtils.isEmpty(uhidList)) {
					returnObj.get("doctor").put(fromDate, doctorScore);
					returnObj.get("nurse").put(fromDate, nurseScore);
				} else {
					List<AnalyticsUsagePojo> usageList=getUsageAnalyticsData(uhidList,fromDate,tempToDate);
					for (AnalyticsUsagePojo obj : usageList) {
						doctorScore += obj.getDoctor_adoptionScore();
						nurseScore += obj.getNurse_adoptionScore();
					}
					returnObj.get("doctor").put(fromDate, (doctorScore / uhidList.size()));
					returnObj.get("nurse").put(fromDate, (nurseScore / uhidList.size()));
				}
				fromDate = tempToDate;
			} while (fromDate.before(toDate));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"getAnalyticsUsage Data", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OccupancyExportCsvPOJO> getExportOccupancy(String fromDateStr, String toDateStr, String branchName, AnalyticsFiltersObj analyticsFilterObj) {
		List<OccupancyExportCsvPOJO> dataItems = new ArrayList<>();
		try {
			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			String[] dischargeTypeList = analyticsFilterObj.getDischargeTypeList().split(",");
			String[] assessmentTypeList = analyticsFilterObj.getAssessmentTypeList().split(",");

			String dischargeTypeArr = null;
			for(String dischargeType : dischargeTypeList) {
				if(dischargeTypeArr != null)
					dischargeTypeArr += ", '" + dischargeType + "'";
				else if(dischargeType.length() > 0){
					dischargeTypeArr = "'" + dischargeType + "'";
				}
			}

			String query = 	"SELECT b FROM BabyDetail b where dateofadmission <= '" + toDate
					+ "' and ((hisdischargedate is null and admissionstatus is 'true') or hisdischargedate >= '" + fromDate
					+ "') and activestatus = 'true' and branchname = '" + branchName + "' and isreadmitted != 'true'";

			if(!BasicUtils.isEmpty(dischargeTypeArr)) {
				query = query +  " and hisdischargestatus IN " + " ( " + dischargeTypeArr +" ) ";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getWeightFrom())) {
				query = query + " and birthweight >= " + analyticsFilterObj.getWeightFrom() + "";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getWeightTo())) {
				query = query + " and birthweight <= " + analyticsFilterObj.getWeightTo() + "";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getGestationFrom())) {
				query = query + " and actualgestationweek >= " + analyticsFilterObj.getGestationFrom() + "";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getGestationTo())) {
				query = query + " and actualgestationweek <= " + analyticsFilterObj.getGestationTo() + "";
			}
			query += " order by dateofadmission desc";

			List<BabyDetail> uhidList = inicuDao
					.getListFromMappedObjQuery(query);
			if (!BasicUtils.isEmpty(uhidList)) {
				for (BabyDetail obj : uhidList) {
					String assessmentMessage = "";
					String assessmentQuery = "SELECT 'Jaundice' as event FROM sa_jaundice where uhid='" + obj.getUhid()
							+ "' UNION SELECT event FROM vw_assesment_respsystem_final where uhid='" + obj.getUhid()
							+ "' UNION SELECT event FROM vw_assesment_cns_final where uhid='" + obj.getUhid()
							+ "' UNION SELECT event FROM vw_assesment_infection_raw  where uhid='" + obj.getUhid() + "'";
					List<String> activeAssessmentList = inicuDao.getListFromNativeQuery(assessmentQuery);
					if (!BasicUtils.isEmpty(activeAssessmentList)) {
						for (String assessment : activeAssessmentList) {
							if(assessmentMessage == ""){
								assessmentMessage = assessment + "";
							}
							else {
								assessmentMessage += "," + assessment + "";
							}
						}
					}
					boolean isAssessmentPresent = false;

					if(assessmentTypeList.length < 1) {
						isAssessmentPresent = true;
					}
					for(String assessmentType : assessmentTypeList) {
						if(assessmentMessage.contains(assessmentType)) {
							isAssessmentPresent = true;
						}
					}

					if(isAssessmentPresent) {
						OccupancyExportCsvPOJO occupancyObj = new OccupancyExportCsvPOJO();
						occupancyObj.setUhid(obj.getUhid());
						occupancyObj.setDateofadmission(obj.getDateofadmission() + "");
						occupancyObj.setDateofbirth(obj.getDateofbirth() + "");
						occupancyObj.setBabyname(obj.getBabyname());
						occupancyObj.setInoutstatus(obj.getInoutPatientStatus());
						occupancyObj.setTimeofadmission(obj.getTimeofadmission());
						if(!BasicUtils.isEmpty(obj.getBirthweight())) {
							occupancyObj.setBirthweight(obj.getBirthweight());
						}
						occupancyObj.setGestationweeks(obj.getGestationweekbylmp());
						occupancyObj.setGestationdays(obj.getActualgestationdays());


						Timestamp startTime = null;
						Timestamp currentTime = null;

						String prevUhid = "";
						long respDays = 0;
						String bpdQuery = "select obj from RespSupport obj where uhid = '" + obj.getUhid() + "' order by creationtime";
						List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
						for(RespSupport resp : bpdList) {
							String uhid = resp.getUhid();
							if(prevUhid == "") {
								prevUhid = uhid;
								startTime = resp.getCreationtime();
							}else {
								currentTime = resp.getCreationtime();
								if(resp.getIsactive()) {
									continue;
								}else if(!resp.getIsactive()) {
									long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
									respDays = respDays + daysBpd;
									startTime = null;
									prevUhid = "";
								}
							}
						}
						occupancyObj.setRespSupportDays((int)respDays);


						if(!BasicUtils.isEmpty(obj.getHisdischargedate())) {
							occupancyObj.setHisDischargeddate(obj.getHisdischargedate() + "");
							occupancyObj.setHisDischargeStatus(obj.getHisdischargestatus()+ "");
							long days = 0;
							if(obj.getHisdischargedate().getTime() <= toDate.getTime() && obj.getDateofadmission().getTime() <= fromDate.getTime()) {
								days = (obj.getHisdischargedate().getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);
								days++;
							}
							else if(obj.getHisdischargedate().getTime() <= toDate.getTime() && obj.getDateofadmission().getTime() >= fromDate.getTime()) {
								days = (obj.getHisdischargedate().getTime() - obj.getDateofadmission().getTime()) / (1000 * 60 * 60 * 24);
							}
							else if(obj.getHisdischargedate().getTime() >= toDate.getTime() && obj.getDateofadmission().getTime() >= fromDate.getTime()) {
								days = (toDate.getTime() - obj.getDateofadmission().getTime()) / (1000 * 60 * 60 * 24);
							}
							else if(obj.getHisdischargedate().getTime() >= toDate.getTime() && obj.getDateofadmission().getTime() <= fromDate.getTime()) {
								days = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);
							}
							occupancyObj.setPatientdays(days);
						}else{
							long days = 0;
							if(obj.getDateofadmission().getTime() <= fromDate.getTime()) {
								days = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);
								days++;
							}
							else if(obj.getDateofadmission().getTime() >= fromDate.getTime()) {
								days = (toDate.getTime() - obj.getDateofadmission().getTime()) / (1000 * 60 * 60 * 24);
							}
							occupancyObj.setPatientdays(days);
						}

						java.util.Date doa = obj.getDateofadmission();
						Integer doaValue = doa.getMonth();
						String monthName = BasicConstants.monthMapping.get(doaValue);
						occupancyObj.setMonth(monthName);
						dataItems.add(occupancyObj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"getAnalyticsUsage Data", BasicUtils.convertErrorStacktoString(e));
		}

		return dataItems;
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, AnalyticsUsageHelperPOJO> getOccupancyData(String fromDateStr, String toDateStr, String branchName, AnalyticsFiltersObj analyticsFilterObj) {
		HashMap<String, AnalyticsUsageHelperPOJO> dataItems = new HashMap<>();
		try {
			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			String[] dischargeTypeList = analyticsFilterObj.getDischargeTypeList().split(",");
			String[] assessmentTypeList = analyticsFilterObj.getAssessmentTypeList().split(",");

			String dischargeTypeArr = null;
			for(String dischargeType : dischargeTypeList) {
				if(dischargeTypeArr != null)
					dischargeTypeArr += ", '" + dischargeType + "'";
				else if(dischargeType.length() > 0){
					dischargeTypeArr = "'" + dischargeType + "'";
				}
			}

//			String query = 	"SELECT b FROM BabyDetail b where dateofadmission <= '" + toDate
//					+ "' and ((hisdischargedate is null and admissionstatus is 'true') or hisdischargedate >= '" + fromDate
//					+ "') and activestatus = 'true' and branchname = '" + branchName + "' and isreadmitted != 'true'";

			String query = 	"SELECT b FROM BabyDetail b where dateofadmission <= '" + toDate
					+ "' and ((hisdischargedate is null and admissionstatus is 'true') or hisdischargedate >= '" + fromDate
					+ "') and activestatus = 'true' and branchname = '" + branchName + "'";

			if(!BasicUtils.isEmpty(dischargeTypeArr)) {
				query = query +  " and hisdischargestatus IN " + " ( " + dischargeTypeArr +" ) ";
			}
			if(!BasicUtils.isEmpty(analyticsFilterObj.getWeightFrom())) {
				query = query + " and birthweight >= " + analyticsFilterObj.getWeightFrom() + "";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getWeightTo())) {
				query = query + " and birthweight <= " + analyticsFilterObj.getWeightTo() + "";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getGestationFrom())) {
				query = query + " and actualgestationweek >= " + analyticsFilterObj.getGestationFrom() + "";
			}

			if(!BasicUtils.isEmpty(analyticsFilterObj.getGestationTo())) {
				query = query + " and actualgestationweek <= " + analyticsFilterObj.getGestationTo() + "";
			}
			query += " order by dateofadmission desc";

			List<BabyDetail> uhidList = inicuDao
					.getListFromMappedObjQuery(query);
			if (!BasicUtils.isEmpty(uhidList)) {
				for (BabyDetail obj : uhidList) {
					String assessmentMessage = "";
					String assessmentQuery = "SELECT 'Jaundice' as event FROM sa_jaundice where uhid='" + obj.getUhid()
							+ "' UNION SELECT event FROM vw_assesment_respsystem_final where uhid='" + obj.getUhid()
							+ "' UNION SELECT event FROM vw_assesment_cns_final where uhid='" + obj.getUhid()
							+ "' UNION SELECT event FROM vw_assesment_infection_raw  where uhid='" + obj.getUhid() + "'";
					List<String> activeAssessmentList = inicuDao.getListFromNativeQuery(assessmentQuery);
					if (!BasicUtils.isEmpty(activeAssessmentList)) {
						for (String assessment : activeAssessmentList) {
							if(assessmentMessage == ""){
								assessmentMessage = assessment + "";
							}
							else {
								assessmentMessage += "," + assessment + "";
							}
						}
					}
					boolean isAssessmentPresent = false;

					if(assessmentTypeList.length < 1) {
						isAssessmentPresent = true;
					}
					for(String assessmentType : assessmentTypeList) {
						if(assessmentMessage.contains(assessmentType)) {
							isAssessmentPresent = true;
						}
					}

					if(isAssessmentPresent) {
						java.util.Date doa = obj.getDateofadmission();
						Integer doaValue = doa.getMonth();
						String monthName = BasicConstants.monthMapping.get(doaValue);
						AnalyticsUsageHelperPOJO helperObj = new AnalyticsUsageHelperPOJO();
						if(dataItems.containsKey(monthName)) {
							helperObj = dataItems.get(monthName);
							helperObj.setNumberOfBabies(helperObj.getNumberOfBabies() + 1);
							if(obj.getInoutPatientStatus()!=null) {
								if (obj.getInoutPatientStatus().equalsIgnoreCase("in born")) {
									if (helperObj.getInbornCount() == null) {
										helperObj.setInbornCount(1);
									} else {
										helperObj.setInbornCount(helperObj.getInbornCount() + 1);
									}
								} else if (obj.getInoutPatientStatus().equalsIgnoreCase("out born")) {
									if (helperObj.getOutbornCount() == null) {
										helperObj.setOutbornCount(1);
									} else {
										helperObj.setOutbornCount(helperObj.getOutbornCount() + 1);
									}
								}
							}
						}
						else {
							helperObj.setNumberOfBabies(1);
							if(obj.getInoutPatientStatus().equalsIgnoreCase("in born")) {
								helperObj.setInbornCount(1);
							}
							else if(obj.getInoutPatientStatus().equalsIgnoreCase("out born")) {
								helperObj.setOutbornCount(1);
							}
						}
						dataItems.put(monthName, helperObj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"getAnalyticsUsage Data", BasicUtils.convertErrorStacktoString(e));
		}

		return dataItems;
	}


	@Override
	public HashMap<Object, Object> getInicuModuleUsageRecords(String recordDate, String loggedInUser, String uhid) {

		HashMap<Object, Object> inicuModuleRecords = new HashMap<Object, Object>();

		try {
			java.util.Date date = CalendarUtility.getDateformatutf(CalendarUtility.SERVER_CRUD_OPERATION)
					.parse(recordDate);
			String dateStr = CalendarUtility.getDateformatdb(CalendarUtility.SERVER_CRUD_OPERATION).format(date);
			/*
			 * String queryDoctorPanelUsageRecords =
			 * "select obj from VwDoctornotesUsageFinal as obj where creationtime='"
			 * +dateStr+"'"; List<VwDoctornotesUsageFinal> doctorPanelUsageList =
			 * inicuDao.getListFromMappedObjQuery(queryDoctorPanelUsageRecords);
			 */
			String queryDoctorPanelUsageRecords = " select b.uhid, "
					+ " case when b.admissionstatus=true then 'admitted' else 'discharged' end admissionstatus, "
					+ " b.babyname ,rl.levelname AS niculevel,rc.levelname AS criticality, Case when r.assesments is null then '0' else r.assesments end assesments ,"
					+ " Case when r.prescription is null then '0' else r.prescription end prescription , Case when r.babyfeed is null then '0' else r.babyfeed end babyfeed, "
					+ "Case when r.doctornotes is null then '0' else r.doctornotes end doctornotes,r.nicuuser "
					+ " from baby_detail as b LEFT OUTER JOIN vw_doctornotes_usage_final as r on b.uhid=r.uhid and r.creationtime='"
					+ dateStr + "'" + " LEFT JOIN ref_level rl ON b.niculevelno = rl.levelid "
					+ " LEFT JOIN ref_criticallevel as rc ON b.criticalitylevel = rc.crlevelid where b.dateofadmission <= '"
					+ dateStr + "' " + " and (b.dischargeddate >= '" + dateStr + "' or b.dischargeddate IS NULL)";
			List<Object[]> doctorPanelUsageList = inicuDao.getListFromNativeQuery(queryDoctorPanelUsageRecords);

			List<DoctorPanelUsageObject> doctorUsageList = new ArrayList<DoctorPanelUsageObject>();

			if (!BasicUtils.isEmpty(doctorPanelUsageList)) {
				Iterator<Object[]> iterator = doctorPanelUsageList.iterator();
				while (iterator.hasNext()) {
					DoctorPanelUsageObject doctorUsage = new DoctorPanelUsageObject();
					Object[] objectArr = iterator.next();
					doctorUsage.setUhid(objectArr[0]);
					doctorUsage.setAdmissionStatus(objectArr[1]);
					doctorUsage.setBabyName(objectArr[2]);
					doctorUsage.setNicuLevel(objectArr[3]);
					doctorUsage.setCriticality(objectArr[4]);
					doctorUsage.setAssesmentUsage(objectArr[5]);
					doctorUsage.setPrescriptionUsage(objectArr[6]);
					doctorUsage.setFeedUsage(objectArr[7]);
					doctorUsage.setDoctorNotesUsage(objectArr[8]);
					doctorUsage.setNicuUser(objectArr[9]);

					doctorUsageList.add(doctorUsage);
				}
			}

			// String queryNursingPanelUsageRecords = "select obj from
			// VwNursingnotesUsageFinal as obj where
			// creationtime='"+dateStr+"'";

				String queryNursingPanelUsageRecords = "select b.uhid,case when b.admissionstatus=true then 'admitted' else 'discharged' end admissionstatus,b.babyname, "
					+ " rl.levelname AS niculevel,rc.levelname AS criticality, "
					+ " Case when r.weight_mesaurement is null then '0' else r.weight_mesaurement end weight_mesaurement,"
					+ " Case when r.vitalparameters is null then '0' else r.vitalparameters end vitalparameters,"
					+ " Case when r.neurovitals is null then '0' else r.neurovitals end neurovitals,"
					+ " Case when r.bloodgas is null then '0' else r.bloodgas end bloodgas,"
					+ " Case when r.ventilaor is null then '0' else r.ventilaor end ventilaor,"
					+ " Case when r.intake is null then '0' else r.intake end intake,"
					+ " Case when r.bloodproducts is null then '0' else r.bloodproducts end bloodproducts,"
					+ " Case when r.output is null then '0' else r.output end output,"
					+ " Case when r.dailyassesment is null then '0' else r.dailyassesment end dailyassesment,"
					+ " Case when r.catheters is null then '0' else r.catheters end catheters,"
					+ " Case when r.nursingmisc is null then '0' else r.nursingmisc end nursingmisc,r.loggeduser,"
					+ " Case when r.medadministration is null then '0' else r.medadministration end medadministration"
					+ " from baby_detail b "
					+ " LEFT OUTER JOIN vw_nursingnotes_usage_final r on b.uhid=r.uhid and r.creationtime='" + dateStr
					+ "'" + " LEFT JOIN ref_level rl ON b.niculevelno = rl.levelid"
					+ " LEFT JOIN ref_criticallevel rc ON b.criticalitylevel = rc.crlevelid where b.dateofadmission <= '"
					+ dateStr + "' " + " and (b.dischargeddate >= '" + dateStr + "' or b.dischargeddate IS NULL)";

			List<Object[]> nursingPanelUsageList = inicuDao.getListFromNativeQuery(queryNursingPanelUsageRecords);

			List<NursingPanelUsageObject> nursingPanelUsage = new ArrayList<NursingPanelUsageObject>();
			if (!BasicUtils.isEmpty(nursingPanelUsageList)) {
				Iterator<Object[]> iterator = nursingPanelUsageList.iterator();
				while (iterator.hasNext()) {
					NursingPanelUsageObject nursingUsage = new NursingPanelUsageObject();
					Object[] objectArr = iterator.next();
					nursingUsage.setUhid(objectArr[0]);
					nursingUsage.setAdmissionStatus(objectArr[1]);
					nursingUsage.setBabyName(objectArr[2]);
					nursingUsage.setNicuLevel(objectArr[3]);
					nursingUsage.setCriticality(objectArr[4]);
					nursingUsage.setBabyVisits(objectArr[5]);
					nursingUsage.setVitalParameters(objectArr[6]);
					nursingUsage.setNeuroVitals(objectArr[7]);
					nursingUsage.setBloodGas(objectArr[8]);
					nursingUsage.setVentilatorSetting(objectArr[9]);
					nursingUsage.setIntake(objectArr[10]);
					nursingUsage.setBloodProducts(objectArr[11]);
					nursingUsage.setOutput(objectArr[12]);
					nursingUsage.setDailyAssessment(objectArr[13]);
					nursingUsage.setCatheters(objectArr[14]);
					nursingUsage.setMisc(objectArr[15]);
					if (objectArr[16] != null) {
						nursingUsage.setNicuUser(objectArr[16]);
					}
					nursingUsage.setMedAdmin(objectArr[17]);

					nursingPanelUsage.add(nursingUsage);
				}
			}

			inicuModuleRecords.put("doctorNotes", doctorUsageList);
			inicuModuleRecords.put("nursingNotes", nursingPanelUsage);

		} catch (ParseException e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser,
					uhid, "Get Usage Data", BasicUtils.convertErrorStacktoString(e));
		}
		try {
//			logService.saveLog("gettting Usage Records", "Getting Usage Record", loggedInUser, "",
//					"Analytics Usage Record");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inicuModuleRecords;
	}

	@Override
	public UsageAnalytics getUsageAnalytics(String loggedInUser, String branchName) {
		UsageAnalytics patientAnalytics = new UsageAnalytics();

		try {
			String totalPatients = "select count(distinct uhid) from BabyDetail where admissionstatus ='TRUE' and branchname = '" + branchName + "'";
			List patientCountList = inicuDao.getListFromMappedObjQuery(totalPatients);

			if (!BasicUtils.isEmpty(patientCountList)) {
				Object patientCount = patientCountList.get(0);

				patientAnalytics.setTotalPatient(Integer.valueOf(patientCount + ""));

				String admittedPatient = "select count(distinct uhid) from BabyDetail where activestatus = 'TRUE' and admissionstatus = 'TRUE' and branchname = '" + branchName + "'";
				List admPatientCountList = inicuDao.getListFromMappedObjQuery(admittedPatient);
				Object admPatientCount = admPatientCountList.get(0);

				patientAnalytics.setPatientAdmitted(Integer.valueOf(admPatientCount + ""));

				String dischargedPatient = "select count(distinct uhid) from BabyDetail where activestatus= 'TRUE' and admissionstatus = 'FALSE' and branchname = '" + branchName + "'";
				List discPatientCountList = inicuDao.getListFromMappedObjQuery(dischargedPatient);
				Object discPatientCount = discPatientCountList.get(0);

				patientAnalytics.setPatientDischarged(Integer.valueOf(discPatientCount + ""));

			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUser, "",
					"Get Patient Analytics", BasicUtils.convertErrorStacktoString(e));
		}
		try {
//			logService.saveLog("gettting Patient Analytics", "Getting Patient Analytics", loggedInUser, "",
//					"Analytics Patient Count");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientAnalytics;
	}

	/**
	 * This score needs to be computed. only when birthweight < 1000 gestation range
	 * (22,25) antenatal_steroids information is available
	 */
	@Override
	public NICHDScoreJson getnchidScore(String uhid) {

		NICHDScoreJson scoreJson = new NICHDScoreJson();
		ResponseMessageObject rObj = new ResponseMessageObject();

		String score = null;
		String query = "SELECT baby.babyname, " + "baby.gender, " + "baby.birthweight, " + "baby.gestationweekbylmp, "
				+ "baby.gestationdaysbylmp, " + "mother.antenatal_steroids " + "FROM baby_detail as baby , "
				+ "mother_current_pregnancy as mother " + "WHERE baby.uhid= '" + uhid + "' "
				+ "AND baby.uhid = mother.uhid ";

		List<Object[]> resultSet = inicuDao.getListFromNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			Object[] row = resultSet.get(0);
			if (isValidForScoreComputation(row)) {
				HttpClient httpClient = new DefaultHttpClient();
				String server = "http://" + BasicConstants.props.getProperty(BasicConstants.PROP_ANALYTICS_ADD)
						+ ":5000/sc";
				HttpPost request = new HttpPost(server);
				try {
					JSONObject obj = new JSONObject();
					obj.put("name", row[0].toString());

					String gender = row[1].toString();
					if (gender.equalsIgnoreCase("male")) {
						obj.put("gender", "m");
					} else {
						obj.put("gender", "f");
					}

					// hard coded single twin as no. We dont capture it as of
					// now 'May 8,2017'.
					obj.put("singtw", "no");

					String antst = row[5].toString();
					if (antst.equalsIgnoreCase("GIVEN")) {
						obj.put("antst", "yes");
					} else {
						obj.put("antst", "no");
					}

					Float birthweight = (Float) row[2];
					obj.put("birthwt", birthweight);

					Float gestage = null;
					Integer gestweek = (Integer) row[3];
					gestage = Float.valueOf(gestweek);

					Integer gestdays = (Integer) row[4];
					if (!BasicUtils.isEmpty(gestdays)) {
						Double addedval = (Double) (gestdays * 0.10);
						Float temp = Double.valueOf(addedval).floatValue();
						gestage = gestage + temp;
					}
					obj.put("gestage", gestage);

					StringEntity params = new StringEntity(obj.toString());
					request.addHeader("content-type", "application/json");
					request.setEntity(params);
					HttpResponse response = httpClient.execute(request);
					HttpEntity entity = response.getEntity();
					String responseString = EntityUtils.toString(entity, "UTF-8");
					System.out.println(responseString);
					score = responseString;

					JSONObject jsonObj = new JSONObject(score);

					if (jsonObj.has("NICHD Score")) {
						scoreJson.setNichdScore(jsonObj.get("NICHD Score").toString());
						rObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
						rObj.setType(BasicConstants.MESSAGE_SUCCESS);
					} else {
						if (jsonObj.has("message")) {
							rObj.setMessage(jsonObj.get("message").toString());
							rObj.setType(BasicConstants.MESSAGE_FAILURE);
						} else {
							rObj.setMessage("OOps! Something went wrong.");
							rObj.setType(BasicConstants.MESSAGE_FAILURE);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					rObj.setMessage(e.getMessage());
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
				}

			} else {
				System.out.println("not a valid condition to compute score.");
				rObj.setMessage("not a valid condition to compute score.");
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
			}
		}

		scoreJson.setResponse(rObj);
		return scoreJson;
	}

	private boolean isValidForScoreComputation(Object[] object) {
		boolean isValid = true;
		String gender = null;
		if (!BasicUtils.isEmpty(object[1])) {
			gender = object[1].toString();
		}

		Float birthweight = null;
		if (!BasicUtils.isEmpty(object[2])) {
			birthweight = (Float) object[2];
		}

		Integer gestationweek = null;
		if (!BasicUtils.isEmpty(object[3])) {
			gestationweek = (Integer) object[3];
		}

		Integer gestationday = null;
		if (!BasicUtils.isEmpty(object[4])) {
			gestationday = (Integer) object[4];
		}

		String antenatalSteroid = null;
		if (!BasicUtils.isEmpty(object[5])) {
			antenatalSteroid = object[5].toString();
		}

		if (!BasicUtils.isEmpty(birthweight) && !BasicUtils.isEmpty(gender) && !BasicUtils.isEmpty(gestationweek)
				&& !BasicUtils.isEmpty(antenatalSteroid)) {
			if (birthweight > 1000) {
				isValid = false;
			}

			if (gestationweek < 22 || gestationweek > 25) {
				isValid = false;
			}

			if (gestationweek == 25) {
				// there should not by any days.
				if (!BasicUtils.isEmpty(gestationday) && gestationday > 0) {
					isValid = false;
				}
			}
		} else {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public String getCribScore(String uhid) {
		String cribScore = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String server = "http://" + BasicConstants.props.getProperty(BasicConstants.PROP_ANALYTICS_ADD)
					+ ":5000/crib";
			HttpPost request = new HttpPost(server);
			JSONObject obj = new JSONObject();
			obj.put("uhid", uhid);
			StringEntity params = new StringEntity(obj.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println(responseString);
			cribScore = responseString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cribScore;
	}

	@Override
	public List<SinJSON> getSinSheetData(String fromDateStr, String toDateStr, String branchName) {
		Long startTimeinMillis = System.currentTimeMillis();
		List<SinJSON> returnObj = new ArrayList<SinJSON>();

		// for a particular time as per CDC guidelines instead of whole day
		long timeValue = 9 * 60 * 60 * 1000;
		Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr) + timeValue);
		Timestamp toDate = new Timestamp(Long.parseLong(toDateStr) + timeValue);
		System.out.println("From date - long: " + fromDateStr + " timestamp: " + fromDate);
		System.out.println("To date - long: " + toDateStr + " timestamp: " + toDate);

		do {
			Timestamp tempToDate = new Timestamp(fromDate.getTime() + 24 * 60 * 60 * 1000);
			SinJSON obj = getSinSheetDayWise(fromDate, tempToDate, branchName);
			returnObj.add(obj);
			fromDate = tempToDate;
		} while (fromDate.before(toDate));

		System.out.println("Time in getSinSheetData: " + (System.currentTimeMillis() - startTimeinMillis));
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	public SinJSON getSinSheetDayWise(Timestamp fromDate, Timestamp endDate, String branchName) {

		//Sin sheet can give the list of multiple elements or a single object depends on the time
		//Sin sheet gives the count(number of babies) with their uhid and babyname for every attribute which is required on analytics screen like IV, cannula, pn etc.
		SinJSON returnObj = new SinJSON();
		String uhidListStr = "";
		returnObj.setDataDate(fromDate);

		try {
			String getBabyListSql = HqlSqlQueryConstants.getSINBabyUHIDList(fromDate, fromDate, branchName);
			List<SinData> babyUhidList = inicuDao.getListFromNativeQuery(getBabyListSql);
			returnObj.getNoOfBabies().setCount(babyUhidList.size());

			String getBabyDataSql = HqlSqlQueryConstants.getSINBabyDataList(fromDate, fromDate, branchName);
			List<SinData> babyDataList = inicuDao.getListFromNativeQuery(getBabyDataSql);
			returnObj.getNoOfBabies().setDataList(babyDataList);

			if (!BasicUtils.isEmpty(babyUhidList)) {

				for (int i = 0; i < babyUhidList.size(); i++) {
					if (uhidListStr.isEmpty()) {
						uhidListStr += "'" + babyUhidList.get(i) + "'";
					} else {
						uhidListStr += ", '" + babyUhidList.get(i) + "'";
					}
				}

				String getIVAntibioticsListSql = HqlSqlQueryConstants.getSINantibiticsIVList(uhidListStr, fromDate,
						endDate);
				List<SinData> antibioticsIVList = inicuDao.getListFromNativeQuery(getIVAntibioticsListSql);
				returnObj.getIvAntibiotics().setCount(antibioticsIVList.size());
				returnObj.getIvAntibiotics().setDataList(antibioticsIVList);


				String getnonInvasiveVentilatorListSql = HqlSqlQueryConstants
						.getSINnonInvasiveVentilatorList(uhidListStr, fromDate, fromDate);
				List<SinData> nonInvasiveVentilatorList = inicuDao
						.getListFromNativeQuery(getnonInvasiveVentilatorListSql);
				returnObj.getNonInvasiveVentilator().setCount(nonInvasiveVentilatorList.size());
				returnObj.getNonInvasiveVentilator().setDataList(nonInvasiveVentilatorList);


				String getAllVentilatorListSql = HqlSqlQueryConstants.getSINAllVentilatorList(uhidListStr, fromDate,
						fromDate);
				List<SinData> allVentilatorList = inicuDao.getListFromNativeQuery(getAllVentilatorListSql);
				returnObj.getTotalVentilator().setCount(allVentilatorList.size());
				returnObj.getTotalVentilator().setDataList(allVentilatorList);


				String getCentralLineListSql = HqlSqlQueryConstants.getSINCentralLineList(uhidListStr, fromDate,
						fromDate);
				List<SinData> CentralLineList = inicuDao.getListFromNativeQuery(getCentralLineListSql);
				returnObj.getCentralLine().setCount(CentralLineList.size());
				returnObj.getCentralLine().setDataList(CentralLineList);

				String getCannulaListSql = HqlSqlQueryConstants.getSINCannulaList(uhidListStr, fromDate, fromDate);
				List<SinData> CannulaList = inicuDao.getListFromNativeQuery(getCannulaListSql);
				returnObj.getPeripheralCannula().setCount(CannulaList.size());
				returnObj.getPeripheralCannula().setDataList(CannulaList);

				String getPnFeedSql = HqlSqlQueryConstants.getSINPnFeedList(uhidListStr, fromDate, endDate);
				List<SinData> pnFeedList = inicuDao.getListFromNativeQuery(getPnFeedSql);
				returnObj.getPnFeed().setCount(pnFeedList.size());
				returnObj.getPnFeed().setDataList(pnFeedList);

				String getIVFeedSql = HqlSqlQueryConstants.getSINIVFeedList(uhidListStr, fromDate, endDate);
				List<SinData> ivFeedList = inicuDao.getListFromNativeQuery(getIVFeedSql);
				returnObj.getIvFeed().setCount(ivFeedList.size());
				returnObj.getIvFeed().setDataList(ivFeedList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get getSinSheetDayWise", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BabyDetail> getSinUHIDData(String dateStr, String branchName) {

		Timestamp fromDate = new Timestamp(Long.parseLong(dateStr));
		String getBabyListSql = HqlSqlQueryConstants.getSINBabyUHIDList(fromDate, fromDate, branchName);
		List<String> babyUhidList = inicuDao.getListFromNativeQuery(getBabyListSql);

		String uhidStr = "";
		for (int i = 0; i < babyUhidList.size(); i++) {
			if (uhidStr.isEmpty()) {
				uhidStr += "'" + babyUhidList.get(i) + "'";
			} else {
				uhidStr += ", '" + babyUhidList.get(i) + "'";
			}
		}

		List<BabyDetail> sinDataList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getSINDataList(uhidStr));
		return sinDataList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public QIJSON getQIData(String fromDateStr, String toDateStr, String fromGest, String toGest, String fromWeight,
			String toWeight, String branchName) {
		Long startTimeinMillis = System.currentTimeMillis();
		QIJSON returnObj = new QIJSON();
		try {
			int babyCount = 0;
			int mortalCount = 0;
			String uhidListStr = "";
			String mortalUhidStr = "";
			String gestCondition = "";
			String weightCondition = "";
			float patient_duration = 0f;
			int clabsiCount = 0;
			int centralLineDuration = 0;

			if (!BasicUtils.isEmpty(fromGest)) {
				gestCondition += " and gestationweekbylmp >=" + fromGest;
			}

			if (!BasicUtils.isEmpty(toGest)) {
				gestCondition += " and gestationweekbylmp <=" + toGest;
			}

			if (!BasicUtils.isEmpty(fromWeight)) {
				weightCondition += " and birthweight >=" + fromWeight;
			}

			if (!BasicUtils.isEmpty(toWeight)) {
				weightCondition += " and birthweight <=" + toWeight;
			}

			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));
			System.out.println("From date - long: " + fromDateStr + " timestamp: " + fromDate);
			System.out.println("To date - long: " + toDateStr + " timestamp: " + toDate);

			String getUhidSql = HqlSqlQueryConstants.getQIBabyUHIDList(fromDate, toDate, branchName);
			List<String> uhidList = inicuDao.getListFromNativeQuery(getUhidSql + gestCondition + weightCondition);

			if (!BasicUtils.isEmpty(uhidList)) {
				babyCount = uhidList.size();
				returnObj.setNoOfBabies(babyCount);

				for (int i = 0; i < uhidList.size(); i++) {
					if (uhidListStr.isEmpty()) {
						uhidListStr += "'" + uhidList.get(i) + "'";
					} else {
						uhidListStr += ", '" + uhidList.get(i) + "'";
					}
				}

				try {
					String getUhidIPOPSql = HqlSqlQueryConstants.getQIBabyIPOPCountList(uhidListStr);
					List<Object[]> uhidIPOPCountList = inicuDao.getListFromNativeQuery(getUhidIPOPSql);
					if (!BasicUtils.isEmpty(uhidIPOPCountList)) {
						for (int i = 0; i < uhidIPOPCountList.size(); i++) {
							Object[] obj = uhidIPOPCountList.get(i);
							if (!BasicUtils.isEmpty(obj[1])) {
								if (((String) obj[1]).equalsIgnoreCase(BasicConstants.In_Born)) {
									returnObj.setNoOfBabiesIp(((BigInteger) obj[0]).intValue());
								} else if (((String) obj[1]).equalsIgnoreCase(BasicConstants.Out_Born)) {
									returnObj.setNoOfBabiesOp(((BigInteger) obj[0]).intValue());
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				// patient dependent------
				try {
					String getMeanGestationWeightSql = HqlSqlQueryConstants.getQIMeanGestationWeightList(uhidListStr,
							fromDate, toDate);
					List<Object[]> meanGestationWeightList = inicuDao.getListFromNativeQuery(getMeanGestationWeightSql);
					if (!BasicUtils.isEmpty(meanGestationWeightList)) {
						Object[] obj = meanGestationWeightList.get(0);

						if (!BasicUtils.isEmpty(obj[1])) {
							returnObj.setMeanWeight(((Double) obj[1]).floatValue());
						}

						if (!BasicUtils.isEmpty(obj[0])) {
							BigDecimal meanGestation = (BigDecimal) obj[0];
							int weeks = meanGestation.intValue();
							int days = Math.round(((meanGestation.floatValue() - weeks) * 7));
							returnObj.setMeanGestationWeek(weeks);
							returnObj.setMeanGestationDay(days);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getPatientDurationSql = HqlSqlQueryConstants.getPatientDurationList(uhidListStr, fromDate,
							toDate);
					List<Double> patientDurationList = inicuDao.getListFromNativeQuery(getPatientDurationSql);
					if (!BasicUtils.isEmpty(patientDurationList)) {
						float avgLengthOfStay = patientDurationList.get(0).floatValue();
						returnObj.setAvgLengthOfStay(avgLengthOfStay);
						patient_duration = Math.round(avgLengthOfStay * babyCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getReadmissionRateSql = HqlSqlQueryConstants.getQIReadmissionRateList(uhidListStr, fromDate,
							toDate);
					List<BigDecimal> readmissionRateList = inicuDao.getListFromNativeQuery(getReadmissionRateSql);
					if (!BasicUtils.isEmpty(readmissionRateList)) {
						returnObj.setReadmissionRate(readmissionRateList.get(0).floatValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				// bed occupancy here
				returnObj.setBedOccupancy(calBedOccupancy(uhidListStr, fromDate, toDate, branchName));

				// QI dependent------
				try {
					String getBreastCountSql = HqlSqlQueryConstants.getQIBreastCountList(uhidListStr, fromDate, toDate);
					List<BigInteger> breastCountList = inicuDao.getListFromNativeQuery(getBreastCountSql);
					if (!BasicUtils.isEmpty(breastCountList)) {
						BigInteger breastCount = breastCountList.get(0);
						returnObj.setBreastFeedingRate((float) (breastCount.intValue() * 100) / babyCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					List<String> earlySepsisCountList = inicuDao.getListFromNativeQuery(
							HqlSqlQueryConstants.getQISepsisCountList(uhidListStr) + " and ageonsethours < 72");
					if (!BasicUtils.isEmpty(earlySepsisCountList)) {
						returnObj.setEarlyOnsetSepsisRate((float) (earlySepsisCountList.size() * 100) / babyCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					List<String> lateSepsisUHIDList = inicuDao.getListFromNativeQuery(
							HqlSqlQueryConstants.getQISepsisCountList(uhidListStr) + " and ageonsethours >= 72");
					if (!BasicUtils.isEmpty(lateSepsisUHIDList)) {
						returnObj.setLateOnsetSepsisRate((float) (lateSepsisUHIDList.size() * 100) / babyCount);

						// uhid list to sql in condition
						String sepsisUhidStr = "";
						for (int i = 0; i < lateSepsisUHIDList.size(); i++) {
							if (sepsisUhidStr.isEmpty()) {
								sepsisUhidStr += "'" + lateSepsisUHIDList.get(i) + "'";
							} else {
								sepsisUhidStr += ", '" + lateSepsisUHIDList.get(i) + "'";
							}
						}

						List<SaSepsis> sepsisList = inicuDao
								.getListFromMappedObjQuery(HqlSqlQueryConstants.getQISepsisList(sepsisUhidStr));
						if (!BasicUtils.isEmpty(sepsisList)) {
							long millisInDay = 24 * 60 * 60 * 1000;
							QISepsisPatientData currentObj = null;
							SaSepsis previousItem = sepsisList.get(0);
							String sepsisIdStr = null;
							LinkedList<QISepsisPatientData> sepsisUhidList = new LinkedList<QISepsisPatientData>();

							for (int i = 1; i < sepsisList.size(); i++) {
								SaSepsis item = sepsisList.get(i);
								if (currentObj == null) {
									// start of new episode or uhid
									currentObj = new QISepsisPatientData();
									currentObj.setUhid(previousItem.getUhid());
									currentObj.setFirstAssessment(previousItem.getAssessmentTime());
									currentObj.setAgeAtOnset(
											previousItem.getAgeatonset() + ((previousItem.getAgeinhoursdays() == null
													|| previousItem.getAgeinhoursdays()) ? " hrs" : " days"));
									sepsisIdStr = "'" + previousItem.getSasepsisid() + "'";
								}

								if (!previousItem.getUhid().equalsIgnoreCase(item.getUhid())
										|| previousItem.getEventstatus().equalsIgnoreCase("inactive")) {
									// episode or uhid change
									currentObj.setMedications(
											getSepsisMedicationStr(previousItem.getUhid(), sepsisIdStr));
									String assessmentStatus = previousItem.getEventstatus();
									currentObj.setStatusAssessment(assessmentStatus.equalsIgnoreCase("yes") ? "Active"
											: (assessmentStatus.equalsIgnoreCase("no") ? "Passive" : "Inactive"));

									Double days = Math.floor(((assessmentStatus.equalsIgnoreCase("inactive")
											? item.getAssessmentTime().getTime()
											: System.currentTimeMillis()) - currentObj.getFirstAssessment().getTime())
											/ millisInDay);
									currentObj.setDurationIndays(days.intValue() + 1);
									sepsisUhidList.add(currentObj);
									currentObj = null;
								}

								if (i == (sepsisList.size() - 1)) {
									if (currentObj == null) {
										currentObj = new QISepsisPatientData();
										currentObj.setUhid(item.getUhid());
										currentObj.setFirstAssessment(item.getAssessmentTime());
										currentObj.setAgeAtOnset(item.getAgeatonset()
												+ ((item.getAgeinhoursdays() == null || item.getAgeinhoursdays())
														? " hrs"
														: " days"));
										sepsisIdStr = "'" + item.getSasepsisid() + "'";
									} else {
										sepsisIdStr += ", '" + item.getSasepsisid() + "'";
									}
									currentObj.setMedications(getSepsisMedicationStr(item.getUhid(), sepsisIdStr));

									String assessmentStatus = item.getEventstatus();
									currentObj.setStatusAssessment(assessmentStatus.equalsIgnoreCase("yes") ? "Active"
											: (assessmentStatus.equalsIgnoreCase("no") ? "Passive" : "Inactive"));

									Double days = Math.floor(((assessmentStatus.equalsIgnoreCase("inactive")
											? item.getAssessmentTime().getTime()
											: System.currentTimeMillis()) - currentObj.getFirstAssessment().getTime())
											/ millisInDay);
									currentObj.setDurationIndays(days.intValue() + 1);
									sepsisUhidList.add(currentObj);
								} else {
									previousItem = item;
									sepsisIdStr += ", '" + previousItem.getSasepsisid() + "'";
								}
							}
							returnObj.setSepsisUhidList(sepsisUhidList);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getClabsiCountSql = HqlSqlQueryConstants.getQIClabsiCountList(uhidListStr);
					List<BigInteger> clabsiCountList = inicuDao.getListFromNativeQuery(getClabsiCountSql);
					if (!BasicUtils.isEmpty(clabsiCountList)) {
						clabsiCount = clabsiCountList.get(0).intValue();
					}

					String getCentralLineDurationSql = HqlSqlQueryConstants.getQICentralLineDurationList(uhidListStr,
							fromDate, toDate);
					List<Double> centralLineDurationList = inicuDao.getListFromNativeQuery(getCentralLineDurationSql);
					if (!BasicUtils.isEmpty(centralLineDurationList)) {
						if (centralLineDurationList.get(0) > 0) {
							centralLineDuration = centralLineDurationList.get(0).intValue();
						}
					}

					// 1) rate = (1000 * no of clabsi_event)/cl_duration
					if (clabsiCount != 0 && centralLineDuration != 0) {
						returnObj.setClabsiRate((float) ((1000 * clabsiCount) / centralLineDuration));
					}

					// 2) CL Utilzation = cl_duration/patient_duration
					if (patient_duration != 0 && centralLineDuration != 0) {
						returnObj.setCentralLineUtilization((float) ((100 * centralLineDuration) / patient_duration));
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getVentilatorUsageSql = HqlSqlQueryConstants.getQIVentilatorUsageList(uhidListStr, fromDate,
							toDate);
					List<Double> ventilatorUsageList = inicuDao.getListFromNativeQuery(getVentilatorUsageSql);
					if (!BasicUtils.isEmpty(ventilatorUsageList)) {
						Double ventilatorUsage = ventilatorUsageList.get(0);
						returnObj.setVentilatorUtilization(
								(float) (ventilatorUsage.intValue() * 100) / (patient_duration * 24));
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getAntibioticsUsageSql = HqlSqlQueryConstants.getQIAntibioticsUsageList(uhidListStr,
							fromDate, toDate);
					List<Double> antibioticsUsageList = inicuDao.getListFromNativeQuery(getAntibioticsUsageSql);
					if (!BasicUtils.isEmpty(antibioticsUsageList)) {
						Double antibioticsUsage = antibioticsUsageList.get(0);
						returnObj.setAntibioticUtilization(
								(float) (antibioticsUsage.intValue() * 100) / patient_duration);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}
			}

			String getMortalUhidSql = HqlSqlQueryConstants.getQIMortalUhidList(fromDate, toDate, branchName);
			List<String> mortalUhidList = inicuDao
					.getListFromNativeQuery(getMortalUhidSql + gestCondition + weightCondition);

			if (!BasicUtils.isEmpty(mortalUhidList)) {
				mortalCount = mortalUhidList.size();
				returnObj.setNoOfMortality(mortalCount);

				for (int i = 0; i < mortalUhidList.size(); i++) {
					if (mortalUhidStr.isEmpty()) {
						mortalUhidStr += "'" + mortalUhidList.get(i) + "'";
					} else {
						mortalUhidStr += ", '" + mortalUhidList.get(i) + "'";
					}
				}

				try {
					String getMortalUhidIPOPSql = HqlSqlQueryConstants.getQIMortalIPOPCountList(mortalUhidStr);
					List<Object[]> mortalIPOPCountList = inicuDao.getListFromNativeQuery(getMortalUhidIPOPSql);
					if (!BasicUtils.isEmpty(mortalIPOPCountList)) {
						for (int i = 0; i < mortalIPOPCountList.size(); i++) {
							Object[] obj = mortalIPOPCountList.get(i);
							if (!BasicUtils.isEmpty(obj[1])) {
								if (((String) obj[1]).equalsIgnoreCase(BasicConstants.In_Born)) {
									returnObj.setNoOfMortalityIp(((BigInteger) obj[0]).intValue());
								} else if (((String) obj[1]).equalsIgnoreCase(BasicConstants.Out_Born)) {
									returnObj.setNoOfMortalityOp(((BigInteger) obj[0]).intValue());
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				String getMortalSepsisSql = HqlSqlQueryConstants.getQIMortalSepsisList(mortalUhidStr);
				try {
					List<BigInteger> mortalSepsisList = inicuDao.getListFromNativeQuery(getMortalSepsisSql);
					if (!BasicUtils.isEmpty(mortalSepsisList)) {
						float mortalSepsis = mortalSepsisList.get(0).floatValue();
						returnObj.setMortality_sepsis((100 * mortalSepsis) / mortalCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					List<BigInteger> mortalNecList = inicuDao
							.getListFromNativeQuery(getMortalSepsisSql + " and nec_status is true");
					if (!BasicUtils.isEmpty(mortalNecList)) {
						float mortalNec = mortalNecList.get(0).floatValue();
						returnObj.setMortality_nec((100 * mortalNec) / mortalCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getMortalBPDSql = HqlSqlQueryConstants.getQIMortalBPDList(mortalUhidStr);
					List<BigInteger> mortalBPDList = inicuDao.getListFromNativeQuery(getMortalBPDSql);
					if (!BasicUtils.isEmpty(mortalBPDList)) {
						float mortalBPD = mortalBPDList.get(0).floatValue();
						returnObj.setMortality_bpd((100 * mortalBPD) / mortalCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					List<String> mortalVLBWUhidList = inicuDao
							.getListFromNativeQuery(getMortalUhidSql + " and b.birthweight < 1500");
					if (!BasicUtils.isEmpty(mortalVLBWUhidList)) {
						float mortalVLBW = mortalVLBWUhidList.size();
						returnObj.setMortality_vlbw((100 * mortalVLBW) / mortalCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

				try {
					String getMortalIVHSql = HqlSqlQueryConstants.getQIMortalIVHList(mortalUhidStr);
					List<BigInteger> mortalIVHList = inicuDao.getListFromNativeQuery(getMortalIVHSql);
					if (!BasicUtils.isEmpty(mortalIVHList)) {
						float mortalIVH = mortalIVHList.get(0).floatValue();
						returnObj.setMortality_ivh((100 * mortalIVH) / mortalCount);
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}
			}

			String getDrNurseCountSql = HqlSqlQueryConstants.getQIDrNurseCountList(toDate, branchName);
			try {
				List<Object[]> drNurseCountList = inicuDao.getListFromNativeQuery(getDrNurseCountSql);
				if (!BasicUtils.isEmpty(drNurseCountList)) {
					int doctorCount = 0;
					int nurseCount = 0;

					for (int i = 0; i < drNurseCountList.size(); i++) {
						Object[] obj = drNurseCountList.get(i);
						if (((String) obj[0]).equalsIgnoreCase(BasicConstants.Sr_Doctor)
								|| ((String) obj[0]).equalsIgnoreCase(BasicConstants.Jr_Doctor)) {
							doctorCount += ((BigInteger) obj[1]).intValue();
						} else if (((String) obj[0]).equalsIgnoreCase(BasicConstants.Nurse)) {
							nurseCount += ((BigInteger) obj[1]).intValue();
						}
					}

					returnObj.setNoOfDoctors(doctorCount);
					returnObj.setNoOfNurses(nurseCount);
				}
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
						"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
		}
		System.out.println("Time in getQISheetData: " + (System.currentTimeMillis() - startTimeinMillis));
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	private float calBedOccupancy(String uhidListStr, Timestamp fromDate, Timestamp toDate, String branchName) {
		int daysCount = 0;
		float bedCount = 0;
		float uhidCount = 0;
		float bedOccupancyRate = 0;

		String uhidSql = "";
		String bedSql = "";
		List<BigInteger> uhidCountList;
		List<BigInteger> bedCountList;
		try {
			Long startTimeinMillis = System.currentTimeMillis();
			do {
				bedCount = 0;
				uhidCount = 0;
				Timestamp tempToDate = new Timestamp(fromDate.getTime() + 24 * 60 * 60 * 1000);
				uhidSql = HqlSqlQueryConstants.getQIBabyUHIDCountList(uhidListStr, fromDate, tempToDate);
				uhidCountList = inicuDao.getListFromNativeQuery(uhidSql);
				if (!BasicUtils.isEmpty(uhidCountList)) {
					uhidCount = uhidCountList.get(0).intValue();
				}

				bedSql = HqlSqlQueryConstants.getQIBabyBedCountList(fromDate, tempToDate, branchName);
				bedCountList = inicuDao.getListFromNativeQuery(bedSql);
				if (!BasicUtils.isEmpty(bedCountList)) {
					bedCount = bedCountList.get(0).intValue();
				}

				if (bedCount != 0 && uhidCount != 0) {
					bedOccupancyRate += (100 * uhidCount) / bedCount;
				}
				daysCount++;
				fromDate = tempToDate;
			} while (fromDate.before(toDate));

			if (bedOccupancyRate != 0) {
				bedOccupancyRate /= daysCount;
			}
			System.out.println("Time in QI-Bed Occupancy: " + (System.currentTimeMillis() - startTimeinMillis));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get QISheet Bed Occupancy", BasicUtils.convertErrorStacktoString(e));
		}
		return bedOccupancyRate;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getSystemicReportDevice(String fromDateStr, String toDateStr, String uhid) {
//		SystemicReportDeviceJSON systemicObj = null;
//		List<SystemicReportDeviceJSON> systemicList = new ArrayList<SystemicReportDeviceJSON>();
		HashMap<String, Object> returnObj = new HashMap<String, Object>();
		
		try {
			
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			List<BabyDetail> uhidCompleteList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyDetailList(uhid));
			
	

			if (!BasicUtils.isEmpty(uhidCompleteList)) {
				BabyDetail obj = uhidCompleteList.get(0);

				String queryHr = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
					    + uhid.trim() + "' " + "AND starttime >= '" + fromDate
					    + "' AND starttime <= '" + toDate + "' order by starttime";
				
				List<DeviceMonitorDetail> resultSetHr = patientDao
					    .getListFromMappedObjNativeQuery(queryHr);
				
				for(DeviceMonitorDetail dev : resultSetHr) {
					dev.setActualStartTime(new Timestamp(dev.getStarttime().getTime() + offset) + "");
					dev.setActualCreationTimeTime(new Timestamp(dev.getCreationtime().getTime() + offset) + "");

				}
				
				
				
				returnObj.put("systemicList", resultSetHr);

			}
		}catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get QISheet Bed Occupancy", BasicUtils.convertErrorStacktoString(e));
		}

		
		
		return returnObj;



	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getSystemicReport(String fromDate, String toDate, String branchName,String reportType) {

		int maxJaundice = 0;
		int maxRds = 0;
		int maxSepsis = 0;
		int maxPPHN = 0;
		int maxPneumothorax = 0;
		int maxSeizures = 0;
		int maxAsphyxia = 0;
		float breastFeedRate = 0;
		float earlyOnsetSepsisFeedRate = 0;
		float lateOnsetSepsisFeedRate = 0;
		int clabsiCount = 0;
		float centralLineDuration = 0;
		float ventilatorUtilization = 0;
		float antibioticUtilization = 0;
		//fromDate = "2018-09-01 01:00:00.000";
		//toDate = "2018-12-31 23:00:00.000";
		
		

		SystemicReportJSON systemicObj = null;
		SystemicReportJSONAsphyxia systemicObjAsphyxia = null;
		List<SystemicReportJSON> systemicList = new ArrayList<SystemicReportJSON>();
		List<SystemicReportJSONAsphyxia> systemicListAsphyxia = new ArrayList<SystemicReportJSONAsphyxia>();

		
		SystemicReportApneaJSON systemicObjApnea = null;
		List<SystemicReportApneaJSON> systemicApneaList = new ArrayList<SystemicReportApneaJSON>();
		HashMap<String, Object> returnObj = new HashMap<String, Object>();
		
		Timestamp fromDateFilter = new Timestamp(Long.parseLong(fromDate));
		Timestamp toDateFilter = new Timestamp(Long.parseLong(toDate));
		
		try {
			
			List<Object> uhidList = new ArrayList<Object>();
			if(reportType.equalsIgnoreCase("All") || reportType.equalsIgnoreCase("Asphyxia")) {
				uhidList = inicuDao.getListFromNativeQuery(HqlSqlQueryConstants.getBabyUHIDList()
					+ " where dateofadmission >= '" + fromDateFilter + "' and dateofadmission < '" + toDateFilter + "' and activestatus is true and branchname = '" + branchName + "' and birthweight is not null and dischargeddate is not null and isreadmitted is false and gestationweekbylmp <= 31 and gestationweekbylmp >= 25 order by dateofadmission");
			}else {
				uhidList = inicuDao.getListFromNativeQuery(HqlSqlQueryConstants.getBabyUHIDList()
						+ " where isreadmitted is false and apnearecord is true and activestatus is true and branchname = '" + branchName + "' and birthweight is not null order by dateofadmission");
			}

//			DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
//		    java.util.Date date = inputFormat.parse(fromDate);
//
//		    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
//		    outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//		    fromDate = outputFormat.format(date);
//		    DateFormat inputFormat1 = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
//			java.util.Date date1 = inputFormat1.parse(toDate);
//
//		    DateFormat outputFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
//		    outputFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//		    toDate = outputFormat.format(date1);
//			uhidList = inicuDao.getListFromNativeQuery(HqlSqlQueryConstants.getBabyUHIDList() + " where dateofadmission > '" + fromDate + "' and dateofadmission < '" + toDate + "' and activestatus is true and branchname = '" + branchName + "' and admissionstatus is false");
			if (!BasicUtils.isEmpty(uhidList)) {

				int babyCount = uhidList.size();
				String uhidStr = "";
				Iterator<Object> itr = uhidList.iterator();
				int gestationWeeksSum = 0;
				int dischargeSum = 0;
				int IVH_REASON = 0;
				int HYPOGLYCEMIA_REASON = 0;
				int HYPERGLYCEMIA_REASON = 0;
				int HYPONATREMIA_REASON	= 0;
				int HYPERNATREMIA_REASON	= 0;
				int HYPOCALCEMIA_REASON	= 0;
				int HYPERCALCEMIA_REASON	= 0;
				int HYPOKALEMIA_REASON	= 0;
				int HYPERKALEMIA_REASON	= 0;
				int ACIDOSIS_REASON	= 0;
				int IEM_REASON	= 0;
				int FEED_INTOLERANCE_REASON	= 0;
				int OTHER_REASON	= 0;
				int PREMATURITY_REASON	= 0;
				int SEIZURES_REASON	= 0;
				int ASPHYXIA_REASON	= 0;
				int SEPSIS_REASON	= 0;
				int PNEUMOTHORAX_REASON	= 0;
				int PPHN_REASON	= 0;
				int APNEA_REASON	= 0;
				int RESPIRATORY_DISTRESS_REASON = 0;
				int JAUNDICE_REASON= 0;

				int countGestation = 0;
				int countDiscWeight = 0;

				int lessthan500 = 0;
				int between500and1000 = 0;
				int between1000and1500 = 0;
				int between1500and2000 = 0;
				int between2000and2500 = 0;
				int greaterthan2500 = 0;

				List<String> otherReasons = new ArrayList<String>();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if (uhidStr.isEmpty()) {
						uhidStr += "'" + obj[0].toString() + "'";
					} else {
						uhidStr += ", '" + obj[0].toString() + "'";
					}

					String uhid = obj[0].toString();

//					  String centralLineQuery = "select obj from CentralLine obj where uhid = '" + uhid + "'";
//					  List<CentralLine> centralLineList = prescriptionDao.getListFromMappedObjNativeQuery(centralLineQuery);
//
//						List<BabyDetail> babyList = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
//								"SELECT b FROM BabyDetail b where uhid='" + uhid + "'");
//
//					  if(!BasicUtils.isEmpty(centralLineList)) {
//						  for(CentralLine central : centralLineList) {
//							  if(!BasicUtils.isEmpty(central.getInsertion_timestamp())) {
//								  long duration = 0;
//								  if(!BasicUtils.isEmpty(central.getRemoval_timestamp())) {
//									  duration = ((central.getRemoval_timestamp().getTime() - central.getInsertion_timestamp().getTime()) / (1000 * 60 * 60 * 24));
//								  }else {
////									  duration = ((babyList.get(0).getDischargeddate().getTime() - central.getInsertion_timestamp().getTime()) / (1000 * 60 * 60 * 24));
//
//								  }
//								  centralLineDuration += duration;
//							  }
//						  }
//					  }

//					List<BabyDetail> babyList = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
//							"SELECT b FROM BabyDetail b where uhid='" + uhid + "' and dischargestatus = 'Discharge'");
//					if (!BasicUtils.isEmpty(babyList)) {
//
//						BabyDetail babyObj = babyList.get(0);
//
//
//						if (!BasicUtils.isEmpty(babyObj.getGestationweekbylmp())) {
//
//							if (!BasicUtils.isEmpty(babyObj.getDischargeddate())) {
//								countGestation = countGestation + 1;
//								int daysAfterBirth = (((Long) (babyObj.getDischargeddate().getTime()
//										- babyObj.getDateofbirth().getTime())).intValue() / (24 * 60 * 60 * 1000)) + 1;
//
//								int gestationDays = (babyObj.getGestationweekbylmp() * 7)
//										+ babyObj.getGestationdaysbylmp();
//
//								int gestaionTotalDays = daysAfterBirth + gestationDays;
//
//								if (gestaionTotalDays % 7 == 0) {
//									int gest = gestaionTotalDays / 7;
//									gestationWeeksSum += gest;
//								} else {
//									int actualDays = gestaionTotalDays % 7;
//									int gest = ((gestaionTotalDays - actualDays) / 7);
//									gestationWeeksSum += gest;
//								}
//							}
//						}
//
//						if (!BasicUtils.isEmpty(babyObj.getDischargeddate())) {
//							String dod = sdf.format(babyObj.getDischargeddate());
//							List<BabyVisit> dischargevisitList = (List<BabyVisit>) inicuDao
//									.getListFromMappedObjQuery("SELECT b FROM BabyVisit b where uhid='" + uhid + "' and visitdate='"
//											+ dod + "' order by creationtime desc");
//							if (!BasicUtils.isEmpty(dischargevisitList) && !BasicUtils.isEmpty(dischargevisitList.get(0).getCurrentdateweight())) {
//								BabyVisit visitObj = dischargevisitList.get(0);
//
//								dischargeSum += visitObj.getCurrentdateweight();
//								countDiscWeight = countDiscWeight + 1;
//								if(visitObj.getCurrentdateweight() < 500) {
//									lessthan500++;
//								}
//								else if(visitObj.getCurrentdateweight() >= 500 && visitObj.getCurrentdateweight() < 1000) {
//									between500and1000++;
//								}
//								else if(visitObj.getCurrentdateweight() >= 1000 && visitObj.getCurrentdateweight() < 1500) {
//									between1000and1500++;
//								}
//								else if(visitObj.getCurrentdateweight() >= 1500 && visitObj.getCurrentdateweight() < 2000) {
//									between1500and2000++;
//								}
//								else if(visitObj.getCurrentdateweight() >= 2000 && visitObj.getCurrentdateweight() < 2500) {
//									between2000and2500++;
//								}
//								else if(visitObj.getCurrentdateweight() >= 2500) {
//									greaterthan2500++;
//								}
//
//							}else {
//								dischargevisitList = (List<BabyVisit>) inicuDao
//										.getListFromMappedObjQuery("SELECT b FROM BabyVisit b where uhid='" + uhid + "' order by visitdate desc");
//								if (!BasicUtils.isEmpty(dischargevisitList) && !BasicUtils.isEmpty(dischargevisitList.get(0).getCurrentdateweight())) {
//									BabyVisit visitObj = dischargevisitList.get(0);
//
//									dischargeSum += visitObj.getCurrentdateweight();
//									countDiscWeight = countDiscWeight + 1;
//									if(visitObj.getCurrentdateweight() < 500) {
//										lessthan500++;
//									}
//									else if(visitObj.getCurrentdateweight() >= 500 && visitObj.getCurrentdateweight() < 1000) {
//										between500and1000++;
//									}
//									else if(visitObj.getCurrentdateweight() >= 1000 && visitObj.getCurrentdateweight() < 1500) {
//										between1000and1500++;
//									}
//									else if(visitObj.getCurrentdateweight() >= 1500 && visitObj.getCurrentdateweight() < 2000) {
//										between1500and2000++;
//									}
//									else if(visitObj.getCurrentdateweight() >= 2000 && visitObj.getCurrentdateweight() < 2500) {
//										between2000and2500++;
//									}
//									else if(visitObj.getCurrentdateweight() >= 2500) {
//										greaterthan2500++;
//									}
//
//								}
//							}
//						}
//					}
					//Reason Of Admission
//			        String reasonQuery = "select obj from ReasonAdmission obj where uhid = '" + uhid + "'";
//					List<ReasonAdmission> reasonAdmissionList = prescriptionDao.getListFromMappedObjNativeQuery(reasonQuery);
//
//
//
//					for(ReasonAdmission obj1 : reasonAdmissionList) {
//
//						switch(obj1.getCause_event()) {
//							case "IVH" :
//								IVH_REASON ++;
//								break;
//							case "Hypoglycemia" :
//								HYPOGLYCEMIA_REASON++;
//
//								break;
//							case "Hyperglycemia" :
//								HYPERGLYCEMIA_REASON++;
//								break;
//							case "Hyponatremia" :
//								HYPONATREMIA_REASON++;
//								break;
//							case "Hypernatremia" :
//								HYPERNATREMIA_REASON++;
//								break;
//							case "Hypocalcemia" :
//								HYPOCALCEMIA_REASON++;
//								break;
//							case "Hypercalcemia" :
//								HYPERCALCEMIA_REASON++;
//								break;
//							case "Hypokalemia" :
//								HYPOKALEMIA_REASON++;
//								break;
//							case "Hyperkalemia" :
//								HYPERKALEMIA_REASON++;
//								break;
//							case "Acidosis" :
//								ACIDOSIS_REASON++;
//								break;
//							case "IEM" :
//								IEM_REASON++;
//								break;
//							case "Feed Intolerance" :
//								FEED_INTOLERANCE_REASON++;
//								break;
//							case "Other" :
//								OTHER_REASON++;
//								if(!BasicUtils.isEmpty(obj1.getCause_value())) {
//									otherReasons.add(obj1.getCause_value());
//								}
//
//								break;
//							case "Prematurity" :
//								PREMATURITY_REASON++;
//								break;
//							case "Seizures" :
//								SEIZURES_REASON++;
//								break;
//							case "Asphyxia" :
//								ASPHYXIA_REASON++;
//								break;
//							case "Sepsis" :
//								SEPSIS_REASON++;
//								break;
//							case "Pneumothorax" :
//								PNEUMOTHORAX_REASON++;
//								break;
//							case "PPHN" :
//								PPHN_REASON++;
//								break;
//							case "Apnea" :
//								APNEA_REASON++;
//								break;
//							case "Respiratory Distress" :
//								RESPIRATORY_DISTRESS_REASON++;
//								break;
//							case "Jaundice" :
//								JAUNDICE_REASON++;
//								break;
//						}
//					}
				}

				// QI dependent------
				try {
//					String getBreastCountSql = "SELECT count(distinct uhid) FROM babyfeed_detail where entryDateTime between '" + fromDate + "' and '"
//							+ toDate + "' and (feedmethod like '%METHOD03%' or feedtype like '%TYPE01%') and uhid in (" + uhidStr + ")";
//					List<BigInteger> breastCountList = inicuDao.getListFromNativeQuery(getBreastCountSql);
//					if (!BasicUtils.isEmpty(breastCountList)) {
//						BigInteger breastCount = breastCountList.get(0);
//						breastFeedRate = ((float) (breastCount.intValue() * 100) / babyCount);
//					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}
//
//				try {
//					 String sepsisSql = "SELECT distinct uhid FROM vw_sepsis_earlyonset_final where uhid in (" + uhidStr + ") and ageonsethours < 72 and ageonsethours >= 0";
//					List<String> earlySepsisCountList = inicuDao.getListFromNativeQuery(sepsisSql);
//					if (!BasicUtils.isEmpty(earlySepsisCountList)) {
//						earlyOnsetSepsisFeedRate = ((float) (earlySepsisCountList.size() * 100) / babyCount);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
//					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
//							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
//				}
//
//				try {
//					 String sepsisSql = "SELECT distinct uhid FROM vw_sepsis_earlyonset_final where uhid in (" + uhidStr + ") and ageonsethours >= 72";
//					List<String> earlySepsisCountList = inicuDao.getListFromNativeQuery(sepsisSql);
//					if (!BasicUtils.isEmpty(earlySepsisCountList)) {
//						lateOnsetSepsisFeedRate = ((float) (earlySepsisCountList.size() * 100) / babyCount);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
//					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
//							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
//				}

//				String getCentralLineDurationSql = "SELECT SUM(CEIL((CASE WHEN removal_timestamp is NULL THEN CASE WHEN insertion_timestamp < '" + fromDate
//				+ "' THEN EXTRACT(EPOCH FROM (to_timestamp('" + toDate
//				+ "', 'yyyy-MM-dd HH24:MI:SS.s') - to_timestamp('" + fromDate
//				+ "', 'yyyy-MM-dd HH24:MI:SS.s'))) ELSE EXTRACT(EPOCH FROM ('" + toDate
//				+ "' - insertion_timestamp)) END ELSE CASE WHEN insertion_timestamp < '" + fromDate
//				+ "' THEN EXTRACT(EPOCH FROM (removal_timestamp - '" + fromDate
//				+ "')) ELSE EXTRACT(EPOCH FROM (removal_timestamp - insertion_timestamp)) END END) / 86400))"
//				+ " FROM central_line where uhid in (" + uhidStr + ")";
//
//				  String centralLineQuery = "select obj from CentralLine obj where uhid in (" + uhidStr + ")";
//				  List<CentralLine> centralLineList = prescriptionDao.getListFromMappedObjNativeQuery(centralLineQuery);
//
//				  if(!BasicUtils.isEmpty(centralLineList)) {
//					  for(CentralLine central : centralLineList) {
//						  if(!BasicUtils.isEmpty(central.getInsertion_timestamp())) {
//							  if(!BasicUtils.isEmpty(central.getRemoval_timestamp())) {
//								  long duration = ((central.getRemoval_timestamp().getTime() - central.getInsertion_timestamp().getTime()) / (1000 * 60 * 60 * 24));
//							  }else {
//								  long duration = (( - central.getInsertion_timestamp().getTime()) / (1000 * 60 * 60 * 24));
//
//							  }
//						  }
//					  }
//				  }
//
//
//
//				int patient_duration = 0;
//				String getPatientDurationSql =  "SELECT avg(v.stay_duration) FROM vw_baby_stay_duration v JOIN (SELECT uhid,"
//					+ " episodeid FROM baby_detail ) b on b.uhid = v.uhid and b.episodeid = v.episodeid where b.uhid in ("
//					+ uhidStr + ")";
//				List<Double> patientDurationList = inicuDao.getListFromNativeQuery(getPatientDurationSql);
//				if (!BasicUtils.isEmpty(patientDurationList)) {
//					float avgLengthOfStay = patientDurationList.get(0).floatValue();
//					patient_duration = Math.round(avgLengthOfStay * babyCount);
//				}

//				try {
//					String getClabsiCountSql = HqlSqlQueryConstants.getQIClabsiCountList(uhidStr);
//					List<BigInteger> clabsiCountList = inicuDao.getListFromNativeQuery(getClabsiCountSql);
//					if (!BasicUtils.isEmpty(clabsiCountList)) {
//						clabsiCount = clabsiCountList.get(0).intValue();
//					}
//
////					List<Double> centralLineDurationList = inicuDao.getListFromNativeQuery(getCentralLineDurationSql);
////					if (!BasicUtils.isEmpty(centralLineDurationList)) {
////						if (centralLineDurationList.get(0) > 0) {
////							centralLineDuration = centralLineDurationList.get(0).intValue();
////						}
////					}
//
//					// 1) rate = (1000 * no of clabsi_event)/cl_duration
////					if (clabsiCount != 0 && centralLineDuration != 0) {
////						returnObj.setClabsiRate((float) ((1000 * clabsiCount) / uhidList.size()));
////					}
//
//					// 2) CL Utilzation = cl_duration/patient_duration
//					if (patient_duration != 0 && centralLineDuration != 0) {
//						centralLineDuration = ((float) ((100 * centralLineDuration) / patient_duration));
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
//					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
//							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
//				}

				try {
//					String getVentilatorUsageSql = "SELECT sum(differenceinhrs) FROM vw_respiratory_usage_final where isactive is true and eventname is not null and creationtime < '"
//							+ toDate + "' and (endtime is null or endtime >= '" + fromDate + "') and uhid in (" + uhidStr + ")";
//					List<Double> ventilatorUsageList = inicuDao.getListFromNativeQuery(getVentilatorUsageSql);
//					if (!BasicUtils.isEmpty(ventilatorUsageList)) {
//						Double ventilatorUsage = ventilatorUsageList.get(0);
//						ventilatorUtilization = ((float) (ventilatorUsage.intValue() * 100) / (patient_duration * 24));
//					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
				}

//				try {
//					String getAntibioticsUsageSql = "SELECT sum(antibiotic_duration) FROM vw_antibiotic_duration where medicationtype='TYPE0001' and startdate < '"
//							+ toDate + "' and (enddate is null or enddate >= '" + fromDate + "') and uhid in (" + uhidStr + ") and antibiotic_duration >= 0 and medicationtype = 'TYPE0001'";
//					List<Double> antibioticsUsageList = inicuDao.getListFromNativeQuery(getAntibioticsUsageSql);
//					if (!BasicUtils.isEmpty(antibioticsUsageList)) {
//						Double antibioticsUsage = antibioticsUsageList.get(0);
//						antibioticUtilization = (
//								(float) (antibioticsUsage.intValue() * 100) / patient_duration);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
//					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
//							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
//				}

//				try {
//				//Timeliness Data
//					String timelinessQuery = "select obj from BabyPrescription obj where uhid in (" + uhidStr + ")";
//					List<BabyPrescription> reasonAdmissionList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					int countIsTime = 0;
//					int countTotal = 0;
//					for(BabyPrescription obj : reasonAdmissionList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getStartdate().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from BabyfeedDetail obj where uhid in (" + uhidStr + ")";
//					List<BabyfeedDetail> BabyFeedDetailList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(BabyfeedDetail obj : BabyFeedDetailList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getEntryDateTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//
//
//
//
//					timelinessQuery = "select obj from NursingBloodGas obj where uhid in (" + uhidStr + ")";
//					List<NursingBloodGas> NursingBloodGasList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingBloodGas obj : NursingBloodGasList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getEntryDate().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from NursingIntakeOutput obj where uhid in (" + uhidStr + ")";
//					List<NursingIntakeOutput> NursingIntakeOutputList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingIntakeOutput obj : NursingIntakeOutputList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getEntry_timestamp().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from NursingNote obj where uhid in (" + uhidStr + ")";
//					List<NursingNote> NursingNotesList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingNote obj : NursingNotesList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getFrom_time().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from NursingVentilaor obj where uhid in (" + uhidStr + ")";
//					List<NursingVentilaor> NursingVentilaorList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingVentilaor obj : NursingVentilaorList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getEntryDate().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from NursingVitalparameter obj where uhid in (" + uhidStr + ")";
//					List<NursingVitalparameter> NursingVitalparameterList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingVitalparameter obj : NursingVitalparameterList) {
//						countTotal++;
//						if(((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getEntryDate().getTime())) {
//							countIsTime++;
//						}
//					}
//
//
//					timelinessQuery = "select obj from StableNote obj where uhid in (" + uhidStr + ")";
//					List<StableNote> StableNoteList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(StableNote obj : StableNoteList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getEntrytime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from AssessmentMedication obj where uhid in (" + uhidStr + ")";
//					List<AssessmentMedication> AssessmentMedicationList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(AssessmentMedication obj : AssessmentMedicationList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmenttime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from NursingHeplock obj where uhid in (" + uhidStr + ")";
//					List<NursingHeplock> NursingHeplockList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingHeplock obj : NursingHeplockList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getExecution_time().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from NursingMedication obj where uhid in (" + uhidStr + ")";
//					List<NursingMedication> NursingMedicationList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(NursingMedication obj : NursingMedicationList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getGiven_time().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaCnsAsphyxia obj where uhid in (" + uhidStr + ")";
//					List<SaCnsAsphyxia> SaCnsAsphyxiaList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaCnsAsphyxia obj : SaCnsAsphyxiaList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaCnsSeizures obj where uhid in (" + uhidStr + ")";
//					List<SaCnsSeizures> SaCnsSeizuresList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaCnsSeizures obj : SaCnsSeizuresList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaSepsis obj where uhid in (" + uhidStr + ")";
//					List<SaSepsis> SaSepsisList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaSepsis obj : SaSepsisList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaJaundice obj where uhid in (" + uhidStr + ")";
//					List<SaJaundice> SaJaundiceList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaJaundice obj : SaJaundiceList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaRespApnea obj where uhid in (" + uhidStr + ")";
//					List<SaRespApnea> SaRespApneaList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaRespApnea obj : SaRespApneaList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaRespPneumo obj where uhid in (" + uhidStr + ")";
//					List<SaRespPneumo> SaRespPneumoList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaRespPneumo obj : SaRespPneumoList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaRespRds obj where uhid in (" + uhidStr + ")";
//					List<SaRespRds> SaRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaRespRds obj : SaRespRdsList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//					timelinessQuery = "select obj from SaMiscellaneous obj where uhid in (" + uhidStr + ")";
//					List<SaMiscellaneous> SaMiscellaneousList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//					countIsTime = 0;
//					countTotal = 0;
//					for(SaMiscellaneous obj : SaMiscellaneousList) {
//						countTotal++;
//						if((obj.getCreationtime().getTime() - (60 * 60 * 1000)) > obj.getAssessmentTime().getTime()) {
//							countIsTime++;
//						}
//					}
//
//
//					int u = 0;
//				}
//
//				catch (Exception e) {
//					e.printStackTrace();
//					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
//					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
//							"Get QISheetData", BasicUtils.convertErrorStacktoString(e));
//				}
//
//				//Baseline Data
//				String analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight < 500";
//				List<Double> analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_rds where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_apnea where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_pphn where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_pneumothorax where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_jaundice where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_infection_sepsis where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_cns_seizures where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_cns_asphyxia where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight < 1500";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 1500 and birthweight < 2000";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 2000 and birthweight < 2500";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 2500";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and gestationweekbylmp < 31";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and gestationweekbylmp >= 31 and gestationweekbylmp < 34";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and gestationweekbylmp >= 34 and gestationweekbylmp < 37";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and gestationweekbylmp >= 37";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and gender = 'Male'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and gender = 'Female'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and inout_patient_status = 'In Born'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and inout_patient_status = 'Out Born'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and baby_type = 'Single'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and baby_type = 'Twins'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and baby_type = 'Triplets'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'LAMA' and gender = 'Male'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'LAMA' and gender = 'Female'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight < 500 and dischargestatus = 'LAMA'" ;
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 500 and birthweight < 1000 and dischargestatus = 'LAMA'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 1000 and birthweight < 1500 and dischargestatus = 'LAMA'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 1500 and birthweight < 2000 and dischargestatus = 'LAMA'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 2000 and birthweight < 2500 and dischargestatus = 'LAMA'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and birthweight >= 2500 and dischargestatus = 'LAMA'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'LAMA' and dischargestatus = 'LAMA'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'Death'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'Discharge'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'Discharge' and gender = 'Female'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'Discharge On Request'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_detail where uhid in (" + uhidStr + ") and dischargestatus = 'Transfer'";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nurse_tasks where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM reason_admission where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM antenatal_history_detail where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM antenatal_steroid_detail where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM birth_to_nicu where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM gen_phy_exam where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM stable_notes where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_rds where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_pphn where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_apnea where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_resp_pneumothorax where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_infection_sepsis where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_jaundice where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_cns_seizures where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM sa_cns_asphyxia where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_prescription where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM babyfeed_detail where uhid in (" + uhidStr + ") and totalenteralvolume > 0";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM babyfeed_detail where uhid in (" + uhidStr + ") and totalparenteralvolume > 0";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM respsupport where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM peripheral_cannula where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM central_line where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM lumbar_puncture where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM vtap where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM et_intubation where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM et_suction where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM procedure_chesttube where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM procedure_exchange_transfusion where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM procedure_other where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM baby_visit where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_vitalparameters where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_episode where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_ventilaor where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_bloodgas where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_intake_output where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_notes where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_blood_product where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_heplock where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM nursing_medication where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM screen_usg where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM screen_rop where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM screen_neurological where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM screen_metabolic where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM screen_hearing where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//
//				analyticsQuery = "select count(distinct(uhid)) FROM doctor_blood_products where uhid in (" + uhidStr + ")";
//				analyticsQueryList = inicuDao.getListFromNativeQuery(analyticsQuery);
//

//				List<Object> jaundiceCauseList = inicuDao
//						.getListFromNativeQuery("select causeofjaundiceid, causeofjaundice from ref_causeofjaundice");
//				if (!BasicUtils.isEmpty(jaundiceCauseList)) {
//					itr = jaundiceCauseList.iterator();
//					while (itr.hasNext()) {
//						Object[] obj = (Object[]) itr.next();
//						this.jaundiceCauseMap.put(obj[0].toString(), obj[1].toString());
//					}
//				}
//
//				List<Object> rdsCauseList = inicuDao
//						.getListFromNativeQuery("select rdscauseid, rdscause from ref_rdscause");
//				if (!BasicUtils.isEmpty(rdsCauseList)) {
//					itr = rdsCauseList.iterator();
//					while (itr.hasNext()) {
//						Object[] obj = (Object[]) itr.next();
//						this.rdsCauseMap.put(obj[0].toString(), obj[1].toString());
//					}
//				}
//
//				List<Object> sepsisCauseList = inicuDao.getListFromNativeQuery(
//						"select causeofinfectionid, causeofinfection from ref_causeofinfection where event='Sepsis'");
//				if (!BasicUtils.isEmpty(sepsisCauseList)) {
//					itr = sepsisCauseList.iterator();
//					while (itr.hasNext()) {
//						Object[] obj = (Object[]) itr.next();
//						this.sepsisCauseMap.put(obj[0].toString(), obj[1].toString());
//					}
//				}
				int counter = 0;
				itr = uhidList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					List<BabyDetail> babyList = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
							"SELECT b FROM BabyDetail b where uhid='" + obj[0].toString() + "'");
//					boolean isBabyQualified = true;
//					boolean isAssessmentQualified = false;
//					if(babyList.size() != 1) {
//						isBabyQualified = false;
//					}
//					isBabyQualified = true;
//					String babyFeedQuery = "select obj from BabyfeedDetail obj where uhid = '" + obj[0].toString() + "' order by entrydatetime asc";
//					List<BabyfeedDetail> feedsList = prescriptionDao.getListFromMappedObjNativeQuery(babyFeedQuery);
//					if(BasicUtils.isEmpty(feedsList)) {
//						isBabyQualified = false;
//					}
//					String babyMedQuery = "select obj from BabyPrescription obj where uhid = '" + obj[0].toString() + "'";
//					List<BabyPrescription> medList = prescriptionDao.getListFromMappedObjNativeQuery(babyMedQuery);
//					if(BasicUtils.isEmpty(medList)) {
//						isBabyQualified = false;
//					}
//					String miscQuery = "select obj from SaMiscellaneous obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaMiscellaneous> miscList = prescriptionDao.getListFromMappedObjNativeQuery(miscQuery);
//					if(!BasicUtils.isEmpty(miscList)) {
//						isAssessmentQualified = true;
//					}
//					String stableQuery = "select obj from StableNote obj where uhid = '" + obj[0].toString() + "' order by entrytime asc";
//					List<StableNote> stableList = prescriptionDao.getListFromMappedObjNativeQuery(stableQuery);
//					if(!BasicUtils.isEmpty(stableList)) {
//						isAssessmentQualified = true;
//					}
//					String rdsQuery = "select obj from SaRespRds obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaRespRds> SaRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaRespRdsList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaRespApnea obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaRespApnea> SaRespApneaList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaRespApneaList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaRespPphn obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaRespPphn> SaRespPphnList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaRespPphnList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaRespPneumo obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaRespPneumo> SaRespPneumoList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaRespPneumoList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaSepsis obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaSepsis> SaSepsisList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaSepsisList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaJaundice obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaJaundice> SaJaundiceList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaJaundiceList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaCnsSeizures obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaCnsSeizures> SaCnsSeizuresList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaCnsSeizuresList)) {
//						isAssessmentQualified = true;
//					}
//					rdsQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
//					List<SaCnsAsphyxia> SaCnsAsphyxiaList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
//					if(!BasicUtils.isEmpty(SaCnsAsphyxiaList)) {
//						isAssessmentQualified = true;
//					}
//					if(isAssessmentQualified == false) {
//						isBabyQualified = false;
//					}
//
//					if (BasicUtils.isEmpty(babyList.get(0).getDischargeddate())) {
//						isBabyQualified = false;
//						// java.util.Date lastDate = CalendarUtility.dateFormatDB.parse("2017-09-30");
//						// long diffInDays = (lastDate.getTime() -
//						// babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);
//						// returnObj.setPatient_stay(((Long) diffInDays).intValue() + 1);
//					} else {
//						long diffInDays = (babyList.get(0).getDischargeddate().getTime() - babyList.get(0).getDateofadmission().getTime())
//								/ (24 * 60 * 60 * 1000);
//						if(((((Long) diffInDays).intValue() + 1 ) < 2) && ((((Long) diffInDays).intValue() + 1 ) >= 0)) {
//							isBabyQualified = false;
//						}
//					}


					if(reportType.equalsIgnoreCase("All")) {
						systemicObj = getSystemicReportForUhid(obj[0].toString(), obj[1].toString());
						systemicList.add(systemicObj);

						if (systemicObj.getNo_jaundice() > maxJaundice) {
							maxJaundice = systemicObj.getNo_jaundice();
						}

						if (systemicObj.getNo_rds() > maxRds) {
							maxRds = systemicObj.getNo_rds();
						}

						if (systemicObj.getNo_sepsis() > maxSepsis) {
							maxSepsis = systemicObj.getNo_sepsis();
						}

						if (systemicObj.getNo_pphn() > maxPPHN) {
							maxPPHN = systemicObj.getNo_pphn();
						}

						if (systemicObj.getNo_pneumothorax() > maxPneumothorax) {
							maxPneumothorax = systemicObj.getNo_pneumothorax();
						}

						if (systemicObj.getNo_seizures() > maxSeizures) {
							maxSeizures = systemicObj.getNo_seizures();
						}

						if (systemicObj.getNo_asphyxia() > maxAsphyxia) {
							maxAsphyxia = systemicObj.getNo_asphyxia();
						}
					}else if(reportType.equalsIgnoreCase("Asphyxia")){
						String rdsQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
						List<SaCnsAsphyxia> SaCnsAsphyxiaList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
						if(!BasicUtils.isEmpty(SaCnsAsphyxiaList)) {
							systemicObjAsphyxia = getSystemicReportForUhidAsphyxia(obj[0].toString(), obj[1].toString());
							systemicListAsphyxia.add(systemicObjAsphyxia);
						}
					}else {
						counter++;
						systemicObjApnea = getSystemicReportForUhidApnea(obj[0].toString(), obj[1].toString(),counter);
						systemicApneaList.add(systemicObjApnea);

					}
				}


			}
			
			if(reportType.equalsIgnoreCase("All")) {

				returnObj.put("maxJaundice", maxJaundice);
				returnObj.put("maxRds", maxRds);
				returnObj.put("maxSepsis", maxSepsis);
				returnObj.put("maxPPHN", maxPPHN);
				returnObj.put("maxPneumothorax", maxPneumothorax);
				returnObj.put("maxSeizures", maxSeizures);
				returnObj.put("maxAsphyxia", maxAsphyxia);
				returnObj.put("systemicList", systemicList);
				returnObj.put("breastFeedRate", breastFeedRate);
				returnObj.put("earlyOnsetSepsisFeedRate", earlyOnsetSepsisFeedRate);
				returnObj.put("lateOnsetSepsisFeedRate", lateOnsetSepsisFeedRate);
				returnObj.put("clabsiCount", clabsiCount);
				returnObj.put("centralLineDuration", centralLineDuration);
				returnObj.put("ventilatorUtilization", ventilatorUtilization);
				returnObj.put("antibioticUtilization", antibioticUtilization);
			}else if(reportType.equalsIgnoreCase("Asphyxia")) {

				returnObj.put("systemicList", systemicListAsphyxia);
			}else {
				returnObj.put("systemicList", systemicApneaList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	@SuppressWarnings("unchecked")
	private SystemicReportApneaJSON getSystemicReportForUhidApnea(String uhid, String episodeid, Integer count) {
		SystemicReportApneaJSON returnObj = new SystemicReportApneaJSON();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			List<BabyDetail> babyList = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
					"SELECT b FROM BabyDetail b where uhid='" + uhid + "' and episodeid='" + episodeid + "'");
			BabyDetail babyObj = babyList.get(0);
			
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			returnObj.setSNo(count);
			returnObj.setName(babyObj.getBabyname());
			returnObj.setUHID(uhid);
			returnObj.setCenter(babyObj.getBranchname());
			returnObj.setDOB(babyObj.getDateofbirth().toString());
			returnObj.setTOB(babyObj.getTimeofbirth().toString());
			returnObj.setDOA(babyObj.getDateofadmission().toString());
			returnObj.setTOA(babyObj.getTimeofadmission().toString());
			
			
			Timestamp emptyDischargeDate = new Timestamp((new java.util.Date().getTime()));
			
			List<BabyDetail> babyListDouble = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
					"SELECT b FROM BabyDetail b where uhid='" + uhid + "' order by creationtime desc");
			BabyDetail babyObjDouble = babyListDouble.get(0);
			
			if(!BasicUtils.isEmpty(babyObjDouble.getDischargeddate()))
				returnObj.setDOD(new Timestamp(babyObjDouble.getDischargeddate().getTime() + offset).toString());
			else {
				returnObj.setDOD(new Timestamp(emptyDischargeDate.getTime() + offset).toString());
			}
			
			if(!BasicUtils.isEmpty(babyObj.getDischargestatus()))
				returnObj.setOutcome(babyObj.getDischargestatus());
			
			returnObj.setGestation_Weeks(babyObj.getGestationweekbylmp().toString());
			returnObj.setGestation_Days(babyObj.getGestationdaysbylmp().toString());
			returnObj.setBirth_Weight_Grams(babyObj.getBirthweight().toString());
			returnObj.setGender(babyObj.getGender());
			
			Timestamp firstDateOfCaffeine = null;
			Timestamp secondDateOfCaffeine = null;
			Timestamp endDate = null;
			Timestamp firstEndDate = null;
			int countNumberOfTimeCaffeineStopped = 0;
			int totalNumberOfDaysCaffeine = 0;
			
			
			String queryGetBabyPrescription = "select obj from BabyPrescription as obj where (medicinename='Caffeine' OR medicinename='Caffeine (loading)' OR medicinename='CAPNEA') and uhid='"
					+ uhid + "' order by startdate asc";
			List<BabyPrescription> caffeineList = inicuDao.getListFromMappedObjQuery(queryGetBabyPrescription);
			if(!BasicUtils.isEmpty(caffeineList)) {
				
				returnObj.setCaffine_Given("Yes");
				returnObj.setDate_when_caffeine_started(new Timestamp(caffeineList.get(0).getStartdate().getTime() + offset).toString());
				boolean firstLoading = false;
				boolean firstMaintenance = false;
				
				boolean firstEscalation = false;
				boolean secondEscalation = false;
				boolean thirdEscalation = false;
				
				
				Timestamp secondStartDate = null;

				float dose = -1;
				
				
				for(BabyPrescription med : caffeineList) {
					
					if(firstDateOfCaffeine == null) {
						firstDateOfCaffeine = med.getStartdate();
					}
					
					if(firstEndDate != null && firstEndDate.getTime() < med.getStartdate().getTime()) {
						if((med.getStartdate().getTime() - firstEndDate.getTime()) >= (7*24*60*60*1000))
							secondStartDate = med.getStartdate();
						else if(secondStartDate == null){
							firstEndDate = null;
						}
					}
					
					if(!BasicUtils.isEmpty(med.getEnddate())) {
						
						if((BasicUtils.isEmpty(med.getIsEdit()) || (!BasicUtils.isEmpty(med.getIsEdit()) && !med.getIsEdit())) && (BasicUtils.isEmpty(med.getIsContinueMedication()) || (!BasicUtils.isEmpty(med.getIsContinueMedication()) && !med.getIsContinueMedication())) && firstEndDate == null && !med.getBolus()) {
							firstEndDate = med.getEnddate();
						}
						if((BasicUtils.isEmpty(med.getIsEdit()) || (!BasicUtils.isEmpty(med.getIsEdit()) && !med.getIsEdit())) && (BasicUtils.isEmpty(med.getIsContinueMedication()) || (!BasicUtils.isEmpty(med.getIsContinueMedication()) && !med.getIsContinueMedication())) && !med.getBolus()) {
							countNumberOfTimeCaffeineStopped++;
						}
						
						totalNumberOfDaysCaffeine += ((med.getEnddate().getTime() - med.getStartdate().getTime()) / (24*60*60*1000));
						
						if(endDate == null) {
							endDate = med.getEnddate();
						}else {
							if(endDate.getTime() <= med.getEnddate().getTime()) {
								endDate = med.getEnddate();
							}
						}
					}
					
					
					
					if(med.getBolus() == true && !firstLoading) {
						firstLoading = true;
						returnObj.setLoading_Dose_mg_per_kg(med.getDose() + " ");
						
						if(firstMaintenance) {
							if(med.getDose() > dose) {
								
								if(!firstEscalation) {
									firstEscalation = true;
									returnObj.setDate_when_dose_was_escalated_1(new Timestamp(med.getStartdate().getTime() + offset).toString());
									returnObj.setEscalated_Dose_1_mg_per_kg(med.getDose().toString() + " ");
								}else if(!secondEscalation) {
									secondEscalation = true;
									returnObj.setDate_when_dose_was_escalated_2(new Timestamp(med.getStartdate().getTime() + offset).toString());
									returnObj.setEscalated_Dose_2_mg_per_kg(med.getDose().toString() + " ");
								}else if(!thirdEscalation) {
									thirdEscalation = true;
									returnObj.setDate_when_dose_was_escalated_3(new Timestamp(med.getStartdate().getTime() + offset).toString());
									returnObj.setEscalated_Dose_3_mg_per_kg(med.getDose().toString() + " ");
								}
								
							}
							
						}
						
					}
					else if(!med.getBolus() == true && !firstMaintenance) {
						firstMaintenance = true;
						returnObj.setMaintenance_Dose_mg_per_kg(med.getDose() + " ");
						
						if(firstLoading) {
							if(med.getDose() > dose) {
								
								if(!firstEscalation) {
									firstEscalation = true;
									returnObj.setDate_when_dose_was_escalated_1(new Timestamp(med.getStartdate().getTime() + offset).toString());
									returnObj.setEscalated_Dose_1_mg_per_kg(med.getDose().toString() + " ");
								}else if(!secondEscalation) {
									secondEscalation = true;
									returnObj.setDate_when_dose_was_escalated_2(new Timestamp(med.getStartdate().getTime() + offset).toString());
									returnObj.setEscalated_Dose_2_mg_per_kg(med.getDose().toString() + " ");
								}else if(!thirdEscalation) {
									thirdEscalation = true;
									returnObj.setDate_when_dose_was_escalated_3(new Timestamp(med.getStartdate().getTime() + offset).toString());
									returnObj.setEscalated_Dose_3_mg_per_kg(med.getDose().toString() + " ");
								}
								
							}
							
						}
						
					}else {
						
						if(med.getDose() > dose) {
							if(!firstEscalation) {
								firstEscalation = true;
								returnObj.setDate_when_dose_was_escalated_1(new Timestamp(med.getStartdate().getTime() + offset).toString());
								returnObj.setEscalated_Dose_1_mg_per_kg(med.getDose().toString() + " ");
							}else if(!secondEscalation) {
								secondEscalation = true;
								returnObj.setDate_when_dose_was_escalated_2(new Timestamp(med.getStartdate().getTime() + offset).toString());
								returnObj.setEscalated_Dose_2_mg_per_kg(med.getDose().toString() + " ");
							}else if(!thirdEscalation) {
								thirdEscalation = true;
								returnObj.setDate_when_dose_was_escalated_3(new Timestamp(med.getStartdate().getTime() + offset).toString());
								returnObj.setEscalated_Dose_3_mg_per_kg(med.getDose().toString() + " " + med.getDose_unit());
							}
						}
						
					}
					dose = med.getDose();
					
				}
				
				if(secondStartDate != null) {
					returnObj.setCaffine_restarting_after_stopping("Yes" + " " + new Timestamp(secondStartDate.getTime() + offset).toString());
				}else {
					returnObj.setCaffine_restarting_after_stopping("No");
				}
				
				if(endDate != null) {
					returnObj.setDate_when_caffine_stopped(new Timestamp(endDate.getTime() + offset).toString());
					secondDateOfCaffeine = endDate;
				}
				
				if(firstEndDate != null) {
					returnObj.setDate_when_caffine_actually_stopped(new Timestamp(firstEndDate.getTime() + offset).toString());
				}
			}else {
				returnObj.setCaffine_Given("No");
			}
			
			returnObj.setNumberOfTimesCaffeineStopped(countNumberOfTimeCaffeineStopped);
			returnObj.setTotalNumberOfDaysCaffeineGiven(totalNumberOfDaysCaffeine);
			
			String queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
					+ "' and apnea='true' order by creationtime asc";
			List<NursingEpisode> pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
			
			int numberOfSpontaneous = 0;
			int numberOfStimulation = 0;
			int numberOfPPV = 0;
			
			if(!BasicUtils.isEmpty(pastNursingEpisode)) {
				returnObj.setApnea("Yes");
				returnObj.setDate_when_first_Apnea_noted(pastNursingEpisode.get(0).getCreationtime().toString());
				returnObj.setDate_when_Apnea_stopped(pastNursingEpisode.get(pastNursingEpisode.size() - 1).getCreationtime().toString());
				
				Iterator<NursingEpisode> itrEpisode = pastNursingEpisode.iterator();

				while (itrEpisode.hasNext()) {
					NursingEpisode obj = itrEpisode.next();
					if (!BasicUtils.isEmpty(obj.getRecovery())) {
						if (obj.getRecovery().equalsIgnoreCase("Spontaneous")) {
							numberOfSpontaneous++;
						} else if (obj.getRecovery().equalsIgnoreCase("Physical stimulation")) {
							numberOfStimulation++;
						} else {
							numberOfPPV++;
						}
					}
				}


				// apneaObj.setNumberOfEpisode(pastNursingEpisode.size());
				returnObj.setNumber_of_Physical_Stimulation(numberOfStimulation);
				returnObj.setNumber_of_PPV(numberOfPPV);
				returnObj.setNumber_of_Recovered_Spontaneously(numberOfSpontaneous);
				
			}else {
				returnObj.setApnea("No");
			}
			
			if(firstDateOfCaffeine != null) {

				queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
						+ "' and apnea='true' and creationtime <'"
						+ firstDateOfCaffeine + "' order by creationtime desc";
				pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
				
				if(!BasicUtils.isEmpty(pastNursingEpisode)) {
					returnObj.setNumber_of_Apneic_episodes_before_starting_caffine(pastNursingEpisode.size());
				}
				
				queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
						+ "' and event_type='Apnea' and server_time <'"
						+ firstDateOfCaffeine + "' order by server_time desc";
				List<ApneaEvent> pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
				
				if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
					returnObj.setNumber_of_Apneic_episodes_before_starting_caffine_device(pastDeviceEpisode.size());
				}
				
				if(firstEndDate != null) {
				
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and apnea='true' and creationtime >='"
							+ firstDateOfCaffeine + "' and creationtime <= '" + firstEndDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Apnea' and server_time >='"
							+  firstDateOfCaffeine + "' and server_time <= '" + firstEndDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine_device(pastDeviceEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and apnea='true' and creationtime >'"
							+ firstEndDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_after_caffine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Apnea' and server_time >'"
							+ firstEndDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_after_caffine_device(pastDeviceEpisode.size());
					}
					
					
					
				}else if(endDate != null) {
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and apnea='true' and creationtime >='"
							+ firstDateOfCaffeine + "' and creationtime <= '" + endDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and apnea='true' and creationtime >'"
							+ endDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_after_caffine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Apnea' and server_time >='"
							+  firstDateOfCaffeine + "' and server_time <= '" + endDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine_device(pastDeviceEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Apnea' and server_time >'"
							+ endDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Apneic_episodes_after_caffine_device(pastDeviceEpisode.size());
					}
				}
			}
			
			queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
					+ "' and desaturation='true' order by creationtime asc";
			pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
			
			if(!BasicUtils.isEmpty(pastNursingEpisode)) {
				returnObj.setHypoxic("Yes");
				
				returnObj.setDate_when_first_Hypoxic_noted(pastNursingEpisode.get(0).getCreationtime().toString());
				returnObj.setDate_when_Hypoxic_stopped(pastNursingEpisode.get(pastNursingEpisode.size() - 1).getCreationtime().toString());
				
			}else {
				returnObj.setHypoxic("No");
			}
			
			queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
					+ "' and bradycardia='true' order by creationtime asc";
			pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
			
			if(!BasicUtils.isEmpty(pastNursingEpisode)) {
				returnObj.setBradycardia("Yes");
				
				returnObj.setDate_when_first_Bradycardia_noted(pastNursingEpisode.get(0).getCreationtime().toString());
				returnObj.setDate_when_Bradycardia_stopped(pastNursingEpisode.get(pastNursingEpisode.size() - 1).getCreationtime().toString());
				
			}else {
				returnObj.setBradycardia("No");
			}
			
			if(firstDateOfCaffeine != null) {

				//Desat and bradycardia before nurse
				queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
						+ "' and desaturation='true' and creationtime <'"
						+ firstDateOfCaffeine + "' order by creationtime desc";
				pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
				
				if(!BasicUtils.isEmpty(pastNursingEpisode)) {
					returnObj.setNumber_of_Hypoxic_episodes_before_starting_caffeine(pastNursingEpisode.size());
				}
				
				queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
						+ "' and bradycardia='true' and creationtime <'"
						+ firstDateOfCaffeine + "' order by creationtime desc";
				pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
				
				if(!BasicUtils.isEmpty(pastNursingEpisode)) {
					returnObj.setNumber_of_Bradycardia_episodes_before_starting_caffeine(pastNursingEpisode.size());
				}
				
				//Desat and bradycardia before device
				queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
						+ "' and event_type='Desaturation' and server_time <='"
						+ firstDateOfCaffeine + "' order by server_time desc";
				List<ApneaEvent> pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
				
				if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
					returnObj.setNumber_of_Hypoxic_episodes_before_starting_caffeine_device(pastDeviceEpisode.size());
				}
				
				queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
						+ "' and event_type='Bradycardia' and server_time <='"
						+ firstDateOfCaffeine + "' order by server_time desc";
				pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
				
				if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
					returnObj.setNumber_of_Bradycardia_episodes_before_starting_caffeine_device(pastDeviceEpisode.size());
				}
				
				
				
				if(firstEndDate != null) {
				
					//Desat and Bradycardia between nurse
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and desaturation='true' and creationtime >='"
							+ firstDateOfCaffeine + "' and creationtime <= '" + firstEndDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and bradycardia='true' and creationtime >='"
							+ firstDateOfCaffeine + "' and creationtime <= '" + firstEndDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine(pastNursingEpisode.size());
					}
					
					//Desat and Bradycardia after nurse
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and desaturation='true' and creationtime >'"
							+ firstEndDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_after_caffeine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and bradycardia='true' and creationtime >'"
							+ firstEndDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_after_caffeine(pastNursingEpisode.size());
					}
					
					//Desat and Bradycardia between device
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Desaturation' and server_time >='"
							+ firstDateOfCaffeine + "' and server_time <= '" + firstEndDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device(pastDeviceEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Bradycardia' and server_time >='"
							+ firstDateOfCaffeine + "' and server_time <= '" + firstEndDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device(pastDeviceEpisode.size());
					}
					
					//Desat and Bradycardia after device
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Desaturation' and server_time >'"
							+ firstEndDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_after_caffeine_device(pastDeviceEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Bradycardia' and server_time >'"
							+ firstEndDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_after_caffeine_device(pastDeviceEpisode.size());
					}
				}else if(endDate != null) {
					//Desat and Bradycardia between nurse
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and desaturation='true' and creationtime >='"
							+ firstDateOfCaffeine + "' and creationtime <= '" + endDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and bradycardia='true' and creationtime >='"
							+ firstDateOfCaffeine + "' and creationtime <= '" + endDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine(pastNursingEpisode.size());
					}
					
					//Desat and Bradycardia after nurse
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and desaturation='true' and creationtime >'"
							+ endDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_after_caffeine(pastNursingEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from NursingEpisode as obj where uhid='" + uhid
							+ "' and bradycardia='true' and creationtime >'"
							+ endDate + "' order by creationtime desc";
					pastNursingEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastNursingEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_after_caffeine_device(pastNursingEpisode.size());
					}
					
					//Desat and Bradycardia between device
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Desaturation' and server_time >='"
							+ firstDateOfCaffeine + "' and server_time <= '" + endDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device(pastDeviceEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Bradycardia' and server_time >='"
							+ firstDateOfCaffeine + "' and server_time <= '" + endDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device(pastDeviceEpisode.size());
					}
					
					//Desat and Bradycardia after device
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Desaturation' and server_time >'"
							+ endDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Hypoxic_episodes_after_caffeine_device(pastDeviceEpisode.size());
					}
					
					queryApneaEpisodeLastAssess = "Select obj from ApneaEvent as obj where uhid='" + uhid
							+ "' and event_type='Bradycardia' and server_time >'"
							+ endDate + "' order by server_time desc";
					pastDeviceEpisode = inicuDao.getListFromMappedObjQuery(queryApneaEpisodeLastAssess);
					
					if(!BasicUtils.isEmpty(pastDeviceEpisode)) {
						returnObj.setNumber_of_Bradycardia_episodes_after_caffeine_device(pastDeviceEpisode.size());
					}
				}
			}
			
			
			if(firstDateOfCaffeine != null) {
				
				Timestamp bufferInitialTime = new Timestamp(firstDateOfCaffeine.getTime() - (6*60*60*1000));

				queryApneaEpisodeLastAssess = "Select avg(CAST(heartrate as integer)) from device_monitor_detail as obj where uhid='" + uhid
						+ "' and starttime <'"
						+ firstDateOfCaffeine + "' and starttime > '" + bufferInitialTime + "' and heartrate is not null ";
				
				List<BigDecimal> pastDeviceList = inicuDao.getListFromNativeQuery(queryApneaEpisodeLastAssess);
				if (!BasicUtils.isEmpty(pastDeviceList)) {
					BigDecimal meanHR = pastDeviceList.get(0);
					returnObj.setMean_HR_Before_starting_caffeine(meanHR.toString());
				}
				
				
				
				
				if(firstEndDate != null) {
					
					if((firstEndDate.getTime() - firstDateOfCaffeine.getTime()) > (48*60*60*1000)) {
						bufferInitialTime = new Timestamp(firstDateOfCaffeine.getTime() + (48*60*60*1000));
					}else {
						bufferInitialTime = new Timestamp(firstEndDate.getTime());
					}
				
					queryApneaEpisodeLastAssess = "Select avg(CAST(heartrate as integer)) from device_monitor_detail as obj where uhid='" + uhid
							+ "'  and starttime >='"
							+ firstDateOfCaffeine + "' and starttime <= '" + bufferInitialTime + "' and heartrate is not null";
					pastDeviceList = inicuDao.getListFromNativeQuery(queryApneaEpisodeLastAssess);
					
					if (!BasicUtils.isEmpty(pastDeviceList)) {
						BigDecimal meanHR = pastDeviceList.get(0);
						returnObj.setMean_HR_During_Caffeine(meanHR.toString());
					}
					
					bufferInitialTime = new Timestamp(firstEndDate.getTime() + (24*60*60*1000));
					
					queryApneaEpisodeLastAssess = "Select avg(CAST(heartrate as integer)) from device_monitor_detail as obj where uhid='" + uhid
							+ "'  and starttime >'"
							+ bufferInitialTime + "' and heartrate is not null";
					pastDeviceList = inicuDao.getListFromNativeQuery(queryApneaEpisodeLastAssess);
					
					if (!BasicUtils.isEmpty(pastDeviceList)) {
						BigDecimal meanHR = pastDeviceList.get(0);
						returnObj.setMean_HR_After_stopping_caffeine(meanHR.toString());
					}
				}else if(endDate != null) {
					
					if((endDate.getTime() - firstDateOfCaffeine.getTime()) > (48*60*60*1000)) {
						bufferInitialTime = new Timestamp(firstDateOfCaffeine.getTime() + (48*60*60*1000));
					}else {
						bufferInitialTime = new Timestamp(endDate.getTime());
					}
					
					queryApneaEpisodeLastAssess = "Select avg(CAST(heartrate as integer)) from device_monitor_detail as obj where uhid='" + uhid
							+ "'  and starttime >='"
							+ firstDateOfCaffeine + "' and starttime <= '" + bufferInitialTime + "' and heartrate is not null";
					pastDeviceList = inicuDao.getListFromNativeQuery(queryApneaEpisodeLastAssess);
					
					if (!BasicUtils.isEmpty(pastDeviceList)) {
						BigDecimal meanHR = pastDeviceList.get(0);
						returnObj.setMean_HR_During_Caffeine(meanHR.toString());
					}
					
					bufferInitialTime = new Timestamp(endDate.getTime() + (24*60*60*1000));
					
					queryApneaEpisodeLastAssess = "Select avg(CAST(heartrate as integer)) from device_monitor_detail as obj where uhid='" + uhid
							+ "'  and starttime >'"
							+ bufferInitialTime + "' and heartrate is not null";
					pastDeviceList = inicuDao.getListFromNativeQuery(queryApneaEpisodeLastAssess);
					
					if (!BasicUtils.isEmpty(pastDeviceList)) {
						BigDecimal meanHR = pastDeviceList.get(0);
						returnObj.setMean_HR_After_stopping_caffeine(meanHR.toString());
					}
					
					
				}
			}
			
			Timestamp startTime = null;
			Timestamp currentTime = null;
			String prevUhid = "";
			long respDays = 0;
			boolean isBpdIdentified = false;
			String respType = "";
			String bpdType = "Mild";
			String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
			for(RespSupport resp : bpdList) {
				if(!isBpdIdentified) {
					
					if(!BasicUtils.isEmpty(resp.getRsVentType())) {
						respType  = resp.getRsVentType();
					}
					if(prevUhid == "") {
						prevUhid = uhid;
						startTime = resp.getCreationtime();
					}else {
						currentTime = resp.getCreationtime();
						if(resp.getIsactive()) {
							continue;
						}else if(!resp.getIsactive()) {
							long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
							respDays = respDays + daysBpd;
							if(respDays >= 28 && !isBpdIdentified) {
								if(!BasicUtils.isEmpty(resp.getRsVentType())) {
									respType  = resp.getRsVentType();
								}
								isBpdIdentified = true;
							}
							startTime = null;
							prevUhid = "";
						}
					}
				}
			}
			
			if(isBpdIdentified) {
				if ((respType.equals("HFO")
                        || respType.equals("Mechanical Ventilation"))) {
					bpdType = "Severe";

                } else if (respType.equals("CPAP")
                        || respType.equals("Low Flow O2")
                        || respType.equals("High Flow O2")
                        || respType.equals("NIMV")) {
                	bpdType = "Moderate";
                } else {
                	bpdType = "Mild";
                }
				returnObj.setBPD_Stage("Yes " + bpdType);
				
			}else {
				returnObj.setBPD_Stage("No");
			}
			
			String screenQuery = "select obj from ScreenRop obj where uhid = '" + uhid + "'";
			List<String> stage = new ArrayList<String>();
			List<ScreenRop> ropLists = prescriptionDao.getListFromMappedObjNativeQuery(screenQuery);
			if (!BasicUtils.isEmpty(ropLists)) {
				returnObj.setROP("Yes");
				
				for(ScreenRop rop:ropLists) {
					if(!BasicUtils.isEmpty(rop.getRop_left_stage())) {
						if(!stage.contains(rop.getRop_left_stage()))
						stage.add(rop.getRop_left_stage());
					}
					if(!BasicUtils.isEmpty(rop.getRop_right_stage())){
						if(!stage.contains(rop.getRop_right_stage()))
						stage.add(rop.getRop_right_stage());
					}
				}
				
			}else {
				returnObj.setROP("No");
			}
			
			String treatmentGiven = "";
			String miscQuery = "select obj from SaMiscellaneous obj where uhid = '" + uhid + "' and (disease = 'PDA' OR disease = 'Pda')";
			List<SaMiscellaneous> SaMiscellaneousList = prescriptionDao.getListFromMappedObjNativeQuery(miscQuery);
			
			miscQuery = "select obj from SaMiscellaneous2 obj where  uhid = '" + uhid + "' and (disease = 'PDA' OR disease = 'Pda')";
			List<SaMiscellaneous2> SaMiscellaneousList2 = prescriptionDao.getListFromMappedObjNativeQuery(miscQuery);
			
			for(SaMiscellaneous misc : SaMiscellaneousList) {
				if(!BasicUtils.isEmpty(misc.getTreatment())) {
					treatmentGiven += misc.getTreatment();
				}
			}
			
			for(SaMiscellaneous2 misc : SaMiscellaneousList2) {
				if(!BasicUtils.isEmpty(misc.getTreatment())) {
					treatmentGiven += misc.getTreatment();
				}
			}
			
			if(!BasicUtils.isEmpty(SaMiscellaneousList) || !BasicUtils.isEmpty(SaMiscellaneousList2)) {
				returnObj.setPDA("Yes");
				if(!BasicUtils.isEmpty(treatmentGiven)) {
					returnObj.setPDA_Treatment_Required("Yes");
				}else {
					returnObj.setPDA_Treatment_Required("No");
				}
			}else {
				returnObj.setPDA("No");
			}
			
			
			
			
			


			
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	@SuppressWarnings("unchecked")
	private SystemicReportJSONAsphyxia getSystemicReportForUhidAsphyxia(String uhid, String episodeid) {
		SystemicReportJSONAsphyxia returnObj = new SystemicReportJSONAsphyxia();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();
		
		try {
			List<BabyDetail> babyList = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
					"SELECT b FROM BabyDetail b where uhid='" + uhid + "' and episodeid='" + episodeid + "'");
			BabyDetail babyObj = babyList.get(0);

			returnObj.setUhid(uhid);
			returnObj.setName(babyObj.getBabyname());
			
			Timestamp dateofadmission = new Timestamp(new Date(offset).getTime());
			if (!BasicUtils.isEmpty(babyObj.getDateofadmission())) {
				dateofadmission = new Timestamp(babyObj.getDateofadmission().getTime());
				if (!BasicUtils.isEmpty(babyObj.getTimeofadmission())) {
					if(babyObj.getTimeofadmission().contains(",")) {
						String[] toaArr = babyObj.getTimeofadmission().split(",");
						// "10,38,PM"
						if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
							dateofadmission.setHours(Integer.parseInt(toaArr[0]) + 12);
						} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
							dateofadmission.setHours(0);
						} else {
							dateofadmission.setHours(Integer.parseInt(toaArr[0]));
						}
						dateofadmission.setMinutes(Integer.parseInt(toaArr[1]));
					}else if(babyObj.getTimeofadmission().contains(":")) {
						String[] toaArr = babyObj.getTimeofadmission().split(":");
						// "10,38,PM"
						if (babyObj.getTimeofadmission().contains("PM") && !toaArr[0].equalsIgnoreCase("12")) {
							dateofadmission.setHours(Integer.parseInt(toaArr[0]) + 12);
						} else if (babyObj.getTimeofadmission().contains("AM") && toaArr[0].equalsIgnoreCase("12")) {
							dateofadmission.setHours(0);
						} else {
							dateofadmission.setHours(Integer.parseInt(toaArr[0]));
						}
						dateofadmission.setMinutes(0);
					}
				}
				dateofadmission = new Timestamp(dateofadmission.getTime());
			}
			
			Timestamp dateofbirth = new Timestamp(new Date(offset).getTime());
			if (!BasicUtils.isEmpty(babyObj.getDateofbirth())) {
				dateofbirth = new Timestamp(babyObj.getDateofbirth().getTime());
				if (!BasicUtils.isEmpty(babyObj.getTimeofbirth())) {
					if(babyObj.getTimeofbirth().contains(",")) {
						String[] toaArr = babyObj.getTimeofbirth().split(",");
						// "10,38,PM"
						if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
							dateofbirth.setHours(Integer.parseInt(toaArr[0]) + 12);
						} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
							dateofbirth.setHours(0);
						} else {
							dateofbirth.setHours(Integer.parseInt(toaArr[0]));
						}
						dateofbirth.setMinutes(Integer.parseInt(toaArr[1]));
					}else if(babyObj.getTimeofbirth().contains(":")) {
						String[] toaArr = babyObj.getTimeofbirth().split(":");
						// "10,38,PM"
						if (babyObj.getTimeofbirth().contains("PM") && !toaArr[0].equalsIgnoreCase("12")) {
							dateofbirth.setHours(Integer.parseInt(toaArr[0]) + 12);
						} else if (babyObj.getTimeofbirth().contains("AM") && toaArr[0].equalsIgnoreCase("12")) {
							dateofbirth.setHours(0);
						} else {
							dateofbirth.setHours(Integer.parseInt(toaArr[0]));
						}
						dateofbirth.setMinutes(0);
					}
				}
				dateofbirth = new Timestamp(dateofbirth.getTime());
			}
			long ageAtAdmission = (dateofadmission.getTime() - dateofbirth.getTime()) / (60*60*1000);
			returnObj.setHoursAtAdmission(String.valueOf(ageAtAdmission));
			
			if(!BasicUtils.isEmpty(babyObj.getGestationdaysbylmp())) {
				returnObj.setGestation_days(babyObj.getGestationdaysbylmp());
			}

			if(!BasicUtils.isEmpty(babyObj.getGestationweekbylmp())) {
				returnObj.setGestation_weeks(babyObj.getGestationweekbylmp());
			}
			
			String antenatalQuery = "select obj from AntenatalHistoryDetail obj where uhid = '" + babyObj.getUhid() + "' order by creationtime desc";
			List<AntenatalHistoryDetail> antenatalList = prescriptionDao.getListFromMappedObjNativeQuery(antenatalQuery);
			if(!BasicUtils.isEmpty(antenatalList)) {

				if(!BasicUtils.isEmpty(antenatalList.get(0).getIugr())) {
					returnObj.setIUGR(antenatalList.get(0).getIugr().toString());
				}
				if(!BasicUtils.isEmpty(antenatalList.get(0).getChorioamniotis())) {
					returnObj.setChorioamniotis(antenatalList.get(0).getChorioamniotis().toString());
				}
				if(!BasicUtils.isEmpty(antenatalList.get(0).getModeOfDelivery())) {
					returnObj.setMode_Of_Delivery(antenatalList.get(0).getModeOfDelivery());
				}
			}
			
			String 	sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' order by assessment_time asc";
			
			List<SaSepsis> sepsisListObj = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQuery);
			for(SaSepsis obj : sepsisListObj) {
				if(obj.getAssessmentTime().getTime() - dateofadmission.getTime() / (1000*60*60) <= 24) {
					if(!BasicUtils.isEmpty(obj.getBloodCultureStatus()) && obj.getBloodCultureStatus().equalsIgnoreCase("Positive")) {
						returnObj.setSepsis("Yes");
						break;
					}
				}
			}
			returnObj.setBirth_weight(babyObj.getBirthweight());
			
			String birthToNicuQuery = "select obj from BirthToNicu obj where uhid = '" + babyObj.getUhid() + "' order by creationtime desc";
			List<BirthToNicu> birthToNicuList = prescriptionDao.getListFromMappedObjNativeQuery(birthToNicuQuery);
			if(!BasicUtils.isEmpty(birthToNicuList)) {
				BirthToNicu obj = birthToNicuList.get(0);
				if(!BasicUtils.isEmpty(obj.getApgarOnemin())) {
					returnObj.setOnemin_APGAR(obj.getApgarOnemin());
				}
				if(!BasicUtils.isEmpty(obj.getApgarFivemin())) {
					returnObj.setFivemin_APGAR(obj.getApgarFivemin());
				}
			}
			
			String sarnatScoreQuery = "select obj from ScoreSarnat obj where uhid = '" + babyObj.getUhid() + "' order by creationtime asc";
			List<ScoreSarnat> scoreList = prescriptionDao.getListFromMappedObjNativeQuery(sarnatScoreQuery);
			if(!BasicUtils.isEmpty(scoreList)) {
				ScoreSarnat obj = scoreList.get(0);
				if(obj.getCreationtime().getTime() - dateofadmission.getTime() / (1000*60*60) <= 24) {
					returnObj.setSarnat_Score(obj.getSarnatScore());
				}
			}
			
			long patientMins = 0;
			if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
				java.sql.Date d = new Date(new java.util.Date().getTime());
				Timestamp today = new Timestamp(d.getTime());
				long diffInDays = ((today.getTime() - babyObj.getDateofbirth().getTime()) / (24 * 60 * 60 * 1000));

				returnObj.setAge_At_Discharge(((Long) diffInDays).intValue() + 1);
				patientMins = (((Long) diffInDays).intValue() + 1) * 1440;

			} else {
				long diffInDays = (babyObj.getDischargeddate().getTime() - babyObj.getDateofbirth().getTime())
						/ (24 * 60 * 60 * 1000);
				returnObj.setAge_At_Discharge(((Long) diffInDays).intValue() + 1);
				patientMins = (((Long) diffInDays).intValue() + 1) * 1440;
			}
			
			
			String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
			for(RespSupport resp : bpdList) {
				if(!BasicUtils.isEmpty(resp.getRsVentType()) && resp.getCreationtime().getTime() - dateofadmission.getTime() / (1000*60*60) <= 24) {
					returnObj.setVentilator_Support(resp.getRsVentType());
					break;
				}
			}
			
			String reasonAdmissionQuery = "select obj from ReasonAdmission obj where uhid = '" + uhid + "' order by creationtime";
			List<ReasonAdmission> reasonAdmissionList = prescriptionDao.getListFromMappedObjNativeQuery(reasonAdmissionQuery);
			for(ReasonAdmission reason : reasonAdmissionList) {
				if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("MAS")){
					returnObj.setMAS("Yes");
					break;
				}
			}
			
			String testResultQuery = "select obj from TestItemResult obj where prn = '" + uhid + "' order by lab_report_date asc";
			List<TestItemResult> testResultList = prescriptionDao.getListFromMappedObjNativeQuery(testResultQuery);
			for(TestItemResult test : testResultList) {
				
				if(!BasicUtils.isEmpty(test.getItemname())) {
					if(BasicUtils.isEmpty(returnObj.getSodium()) && test.getItemname().equalsIgnoreCase("SERUM SODIUM")){
						returnObj.setSodium(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getCalcium()) && test.getItemname().equalsIgnoreCase("SERUM,CALCIUM")){
						returnObj.setCalcium(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getPotassium()) && test.getItemname().equalsIgnoreCase("SERUM POTASSIUM")){
						returnObj.setPotassium(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getBloodUrea()) && (test.getItemname().equalsIgnoreCase("BLOOD UREA") || test.getItemname().equalsIgnoreCase("BLOOD. UREA"))){
						returnObj.setBloodUrea(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getSeremCreatinine()) && (test.getItemname().equalsIgnoreCase("CREATININE") || test.getItemname().equalsIgnoreCase("SERUM CREATININE"))){
						returnObj.setSeremCreatinine(test.getItemvalue() + " " + test.getItemunit());
						long ageAtCreatinine = (test.getLabReportDate().getTime() - dateofbirth.getTime()) / (60*60*1000);
						returnObj.setSeremCreatinineTimeHours(String.valueOf(ageAtCreatinine));
					}
					if(BasicUtils.isEmpty(returnObj.getPT()) && test.getItemname().equalsIgnoreCase("PROTHROMBIN TIME")){
						returnObj.setPT(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getAPTT()) && test.getItemname().equalsIgnoreCase("PATIENT VALUE") && test.getLabTestName().equalsIgnoreCase("APTT")){
						returnObj.setAPTT(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getLiver_Enzymes()) && (test.getItemname().equalsIgnoreCase("SGPT") || test.getItemname().equalsIgnoreCase("SGOT"))){
						returnObj.setLiver_Enzymes(test.getItemname() + " " + test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getINR()) && test.getItemname().equalsIgnoreCase("INTERNATIONAL NORMALIZED RATIO(INR)")){
						returnObj.setINR(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getTLC()) && test.getItemname().equalsIgnoreCase("TLC(TOTAL LEUCOCYTE COUNT)")){
						returnObj.setTLC(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getPLT()) && test.getItemname().equalsIgnoreCase("PLATELET COUNT")){
						returnObj.setPLT(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getPH()) && test.getItemname().equalsIgnoreCase("pH..")){
						returnObj.setPH(test.getItemvalue() + " " + test.getItemunit());
						long ageAtBloodGas = (test.getLabReportDate().getTime() - dateofbirth.getTime()) / (60*60*1000);
						returnObj.setBloodGasTimeHours(String.valueOf(ageAtBloodGas));
					}
					if(BasicUtils.isEmpty(returnObj.getBE()) && test.getItemname().equalsIgnoreCase("BE")){
						returnObj.setBE(test.getItemvalue() + " " + test.getItemunit());
					}
					if(BasicUtils.isEmpty(returnObj.getLactate()) && test.getItemname().equalsIgnoreCase("LACTATE")){
						returnObj.setLactate(test.getItemvalue() + " " + test.getItemunit());
					}
				}
			}
			returnObj.setPatient_type(babyObj.getInoutPatientStatus());
			
			String coolingQuery = "select obj from TherapeuticHypothermia obj where uhid = '" + uhid + "' order by creationtime";
			List<TherapeuticHypothermia> coolingList = prescriptionDao.getListFromMappedObjNativeQuery(coolingQuery);
			if(!BasicUtils.isEmpty(coolingList)) {
				returnObj.setCooling("Yes");
			}else {
				returnObj.setCooling("No");
			}
			
			String seizuresQuery = "select obj from SaCnsSeizures obj where uhid = '" + uhid + "' order by creationtime";
			List<SaCnsSeizures> seizuresList = prescriptionDao.getListFromMappedObjNativeQuery(seizuresQuery);
			if(!BasicUtils.isEmpty(seizuresList)) {
				returnObj.setSeizures("Yes");
				long ageAtSeizures = (seizuresList.get(0).getCreationtime().getTime() - dateofbirth.getTime()) / (60*60*1000);
				returnObj.setSeizuresHoursAtBirth(String.valueOf(ageAtSeizures));
			}else {
				returnObj.setSeizures("No");
			}
			
			String ffpQuery = "select obj from DoctorBloodProducts obj where uhid = '" + uhid + "' and blood_product = 'FFP' order by assessment_time";
			List<DoctorBloodProducts> ffpList = prescriptionDao.getListFromMappedObjNativeQuery(ffpQuery);
			for(DoctorBloodProducts ffp : ffpList) {
				returnObj.setFFP("Yes");
				break;
			}
			
			String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "'  and medicationtype='TYPE0004' order by startdate asc";
			List<BabyPrescription> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			for(BabyPrescription medObj : medList) {
				returnObj.setInotropes("Yes");
				break;
				
			}
			
			returnObj.setDOA(babyObj.getDateofadmission().toString());
			returnObj.setTOA(babyObj.getTimeofadmission().toString());
			returnObj.setDOB(babyObj.getDateofbirth().toString());
			returnObj.setTOB(babyObj.getTimeofbirth().toString());
			
			Timestamp emptyDischargeDate = new Timestamp((new java.util.Date().getTime()));
			
			if(!BasicUtils.isEmpty(babyObj.getDischargeddate()))
				returnObj.setDOD(new Timestamp(babyObj.getDischargeddate().getTime() + offset).toString());
			else {
				returnObj.setDOD(new Timestamp(emptyDischargeDate.getTime() + offset).toString());
			}
			
			if(!BasicUtils.isEmpty(babyObj.getDischargestatus()))
				returnObj.setOutcome(babyObj.getDischargestatus());
			
//			String deviceQuery = "select obj from DeviceMonitorDetailDump obj where uhid = '" + uhid + "'";
//			List<DeviceMonitorDetailDump> devList = prescriptionDao.getListFromMappedObjNativeQuery(deviceQuery);
//			if(!BasicUtils.isEmpty(devList)) {
//				if(devList.size() / patientMins > 0.75)
//					returnObj.setDeviceDataAvailable("Yes");
//				else
//					returnObj.setDeviceDataAvailable("No");
//			}else {
//				returnObj.setDeviceDataAvailable("No");
//			}
			
			
		}catch(Exception e) {
			
		}
		
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	private SystemicReportJSON getSystemicReportForUhid(String uhid, String episodeid) {
		SystemicReportJSON returnObj = new SystemicReportJSON();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			List<BabyDetail> babyList = (List<BabyDetail>) inicuDao.getListFromMappedObjQuery(
					"SELECT b FROM BabyDetail b where uhid='" + uhid + "' and episodeid='" + episodeid + "'");
			BabyDetail babyObj = babyList.get(0);

			returnObj.setUhid(uhid);
			returnObj.setName(babyObj.getBabyname());
			returnObj.setDob(babyObj.getDateofbirth().toString());

			String initialAbdGirth = "";
			String finalAbdGirth = "";
			String initialGastricAsp = "";
			float initialEnteralVol = 0;
			float finalEnteralVol = 0;

			String finalGastricAsp = "";
			Timestamp initalDateAbd = null;
			Timestamp finalDateAbd = null;

			//Procedures
			String procedureQuery  = "select obj from PeripheralCannula obj where uhid = '" + uhid + "'";
			List<PeripheralCannula> procedureLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(procedureLists)) {
				returnObj.setPeripheralCount(procedureLists.size());
			}
			procedureQuery  = "select obj from CentralLine obj where uhid = '" + uhid + "'";
			List<CentralLine> centralLineLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(centralLineLists)) {
				returnObj.setCentralLineCount(centralLineLists.size());
			}
			procedureQuery  = "select obj from LumbarPuncture obj where uhid = '" + uhid + "'";
			List<LumbarPuncture> lumburLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(lumburLists)) {
				returnObj.setLumbarPunctureCount(lumburLists.size());
			}
			procedureQuery  = "select obj from Vtap obj where uhid = '" + uhid + "'";
			List<Vtap> vTapLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(vTapLists)) {
				returnObj.setVTapCount(vTapLists.size());
			}
			procedureQuery  = "select obj from EtIntubation obj where uhid = '" + uhid + "'";
			List<EtIntubation> etIntubationLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(etIntubationLists)) {
				returnObj.setEtIntubationCount(etIntubationLists.size());
			}
			procedureQuery  = "select obj from EtSuction obj where uhid = '" + uhid + "'";
			List<EtSuction> etSuctionLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(etSuctionLists)) {
				returnObj.setEtSuctionCount(etSuctionLists.size());
			}
			procedureQuery  = "select obj from ProcedureChesttube obj where uhid = '" + uhid + "'";
			List<ProcedureChesttube> chestTubeLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(chestTubeLists)) {
				returnObj.setChestTubeCount(chestTubeLists.size());
			}
			procedureQuery  = "select obj from ProcedureExchangeTransfusion obj where uhid = '" + uhid + "'";
			List<ProcedureExchangeTransfusion> exchangeTransfusionLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(exchangeTransfusionLists)) {
				returnObj.setExchangeTransfusionCount(exchangeTransfusionLists.size());
			}
			procedureQuery  = "select obj from PeritonealDialysis obj where uhid = '" + uhid + "'";
			List<PeritonealDialysis> pdLists = prescriptionDao.getListFromMappedObjNativeQuery(procedureQuery);
			if(!BasicUtils.isEmpty(pdLists)) {
				returnObj.setPeritonealDialysisCount(pdLists.size());
			}
			if(uhid.equalsIgnoreCase("190600457")) {
				int s = 1;
			}

			int totalOrders = 0;
			int totalExecutions = 0;
			List<String> medicationList = new ArrayList<String>();
			String babyMedQuery  = "select obj from BabyPrescription obj where uhid = '" + uhid + "'";
			List<BabyPrescription> babyMedLists = prescriptionDao.getListFromMappedObjNativeQuery(babyMedQuery);
			for(BabyPrescription obj : babyMedLists) {
				if(!medicationList.contains(obj.getMedicinename()))
					medicationList.add(obj.getMedicinename());
				if(!BasicUtils.isEmpty(obj.getFreq_type())) {
					if(obj.getFreq_type().equalsIgnoreCase("Intermittent") && !BasicUtils.isEmpty(obj.getFrequency())) {
						String lowerFreqType = "";
						Float lowerFreqValue = -1f;
						String queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + obj.getFrequency()  + "'";
						List<RefMedfrequency> refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
						if(!BasicUtils.isEmpty(refMedFreqList)) {
							lowerFreqType = refMedFreqList.get(0).getFreqvalue();
							String freqList[] = lowerFreqType.split(" hr");
							lowerFreqValue = Float.parseFloat(freqList[0]);
							lowerFreqValue = 24 / lowerFreqValue;
							if(!BasicUtils.isEmpty(obj.getStartdate()) && !BasicUtils.isEmpty(obj.getEnddate())) {
								long nicuStay = ((obj.getEnddate().getTime() - obj.getStartdate().getTime()) / (24 * 60 * 60 * 1000));
								nicuStay = nicuStay + 1;
								totalOrders += (nicuStay*(lowerFreqValue));
							}else if(!BasicUtils.isEmpty(obj.getStartdate())){
								if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
									java.sql.Date d = new Date(new java.util.Date().getTime());
									Timestamp today = new Timestamp(d.getTime());
									long nicuStay = ((today.getTime() - obj.getStartdate().getTime()) / (24 * 60 * 60 * 1000));

									nicuStay = nicuStay + 1;
									totalOrders += (nicuStay*(lowerFreqValue));
								} else {
									long nicuStay = (babyObj.getDischargeddate().getTime() - obj.getStartdate().getTime())
											/ (24 * 60 * 60 * 1000);
									nicuStay = nicuStay + 1;
									totalOrders += (nicuStay*(lowerFreqValue));
								}


							}
						}
					}else {
						if(obj.getFreq_type().equalsIgnoreCase("STAT"))
							totalOrders += 1;
						if(obj.getFreq_type().equalsIgnoreCase("Continuous")) {
							if(!BasicUtils.isEmpty(obj.getEnddate())) {
								long nicuStay = ((obj.getEnddate().getTime() - obj.getStartdate().getTime()) / (24 * 60 * 60 * 1000));
								nicuStay = nicuStay + 1;
								totalOrders += nicuStay;
							}else {
								if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
									java.sql.Date d = new Date(new java.util.Date().getTime());
									Timestamp today = new Timestamp(d.getTime());
									long nicuStay = ((today.getTime() - obj.getStartdate().getTime()) / (24 * 60 * 60 * 1000));

									nicuStay = nicuStay + 1;
									totalOrders += nicuStay;
								} else {
									long nicuStay = (babyObj.getDischargeddate().getTime() - obj.getStartdate().getTime())
											/ (24 * 60 * 60 * 1000);
									nicuStay = nicuStay + 1;
									totalOrders += nicuStay;
								}
							}
						}
					}
				}
				String medQuery = "select obj from NursingMedication obj where baby_presid = '" + obj.getBabyPresid() + "' order by given_time asc";
				List<NursingMedication> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
				if(!BasicUtils.isEmpty(medList)) {
					totalExecutions += medList.size();
				}
			}

//			returnObj.setMedicationTotalOrders(totalOrders);
//			returnObj.setMedicationTotalExecutions(totalExecutions);
			returnObj.setMedicationsGiven(medicationList.toString());

			totalOrders = 0;
			totalExecutions = 0;

			String babyFeedQuery = "select obj from BabyfeedDetail obj where uhid = '" + uhid + "'";
			List<BabyfeedDetail> BabyFeedDetailListQuery = prescriptionDao.getListFromMappedObjNativeQuery(babyFeedQuery);
			if(!BasicUtils.isEmpty(BabyFeedDetailListQuery)) {
//				returnObj.setNutritionTotalOrders(BabyFeedDetailListQuery.size());
			}
			String intakeQuery = "select obj from NursingIntakeOutput obj where uhid = '" + uhid + "'";
			List<NursingIntakeOutput> intakeQueryList = prescriptionDao.getListFromMappedObjNativeQuery(intakeQuery);
			if(!BasicUtils.isEmpty(intakeQueryList)) {
//				returnObj.setNutritionTotalExecutions(intakeQueryList.size());
			}

			String 	asphyxiaQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + uhid + "' order by assessment_time asc";
			List<SaCnsAsphyxia> asphyxiaListObj = prescriptionDao.getListFromMappedObjNativeQuery(asphyxiaQuery);
			for(SaCnsAsphyxia obj : asphyxiaListObj) {
				if(!BasicUtils.isEmpty(obj.getSensoriumType())) {
					returnObj.setSensorium_asphyxia(obj.getSensoriumType());
				}
					
			}
			//Timeliness
			int countExecutionTime = 0;
			int countExecutionTotal = 0;
			String timelinessQuery  = "select obj from BabyPrescription obj where uhid = '" + uhid + "'";
			List<BabyPrescription> reasonAdmissionLists = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);

			int countIsTime = 0;
			int countTotal = 0;

			long timeElapsed = 0;
			if(!BasicUtils.isEmpty(reasonAdmissionLists)) {
				for(BabyPrescription obj : reasonAdmissionLists) {


					String medQuery = "select obj from NursingMedication obj where baby_presid = '" + obj.getBabyPresid() + "' order by given_time asc";
					List<NursingMedication> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
					if(!BasicUtils.isEmpty(medList)) {
						countTotal++;

						//Calculating buffer
						if(obj.getBolus() && !BasicUtils.isEmpty(obj.getStartdate())) {
							timeElapsed += ((medList.get(0).getGiven_time().getTime() - obj.getStartdate().getTime()) / (1000 * 60 * 60));
						}else if(!BasicUtils.isEmpty(obj.getFrequency()) && !BasicUtils.isEmpty(obj.getStartdate())) {
							String queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + obj.getFrequency()  + "'";
							List<RefMedfrequency> refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
							if(!BasicUtils.isEmpty(refMedFreqList)) {
								String lowerFreqType = refMedFreqList.get(0).getFreqvalue();
								String freqList[] = lowerFreqType.split(" hr");
								Integer lowerFreqValue = Integer.parseInt(freqList[0]);
								long timeToConsider = obj.getStartdate().getTime() + (lowerFreqValue * 60 * 60 * 1000);
								if(medList.get(0).getGiven_time().getTime() > timeToConsider)
									timeElapsed += ((medList.get(0).getGiven_time().getTime() - timeToConsider) / (1000 * 60 * 60));
							}
						}
					}else {
						countExecutionTime++;
					}

				}

//				if(timeElapsed != 0)
//					returnObj.setMEDICATION_HRS(String.valueOf(timeElapsed));
//				else {
//					returnObj.setMEDICATION_HRS("");
//				}
//				returnObj.setMEDICATION_TOTAL(String.valueOf(countTotal));
//				if(countTotal != 0 && timeElapsed != 0)
//				returnObj.setMEDICATION_FINAL(String.valueOf(timeElapsed/countTotal));

//			}else {
//				returnObj.setMEDICATION_HRS("");
//				returnObj.setMEDICATION_TOTAL(String.valueOf(""));
//				returnObj.setMEDICATION_FINAL(String.valueOf(""));
//			}
			}

			timeElapsed = 0;
			countExecutionTime = 0;
			countExecutionTotal = 0;
			timelinessQuery = "select obj from BabyfeedDetail obj where uhid = '" + uhid + "'";
			List<BabyfeedDetail> BabyFeedDetailList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
			countIsTime = 0;
			countTotal = 0;
			float energyDeficit = 0;
			float proteinDeficit = 0;
			float fatDeficit = 0;
//			if(!BasicUtils.isEmpty(BabyFeedDetailList)) {
//
//				for(BabyfeedDetail obj : BabyFeedDetailList) {
//
//					String medQuery = "select obj from NursingIntakeOutput obj where babyfeedid = '" + obj.getBabyfeedid() + "' order by entry_timestamp asc";
//					List<NursingIntakeOutput> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
//					if(!BasicUtils.isEmpty(medList) && (obj.getEntryDateTime().getTime() < medList.get(0).getEntry_timestamp().getTime())) {
//						timeElapsed += ((medList.get(0).getEntry_timestamp().getTime() - obj.getEntryDateTime().getTime()) / (60 * 60 * 1000));
//						countTotal++;
//
//					}
//					else {
//						countExecutionTime++;
//					}
//				}
//
//
//
//				returnObj.setNUTRITION_HRS(String.valueOf(timeElapsed));
//
//				if(countTotal != 0) {
//					returnObj.setNUTRITION_FINAL(String.valueOf(timeElapsed/countTotal));
//					returnObj.setNUTRITION_TOTAL(String.valueOf(countTotal));
//				}
//
//
//			}else {
//				returnObj.setNUTRITION_HRS("NAN");
//				returnObj.setNUTRITION_TOTAL(String.valueOf("NAN"));
//				returnObj.setNUTRITION_FINAL(String.valueOf("NAN"));
//
//			}


			timeElapsed = 0;
			timelinessQuery = "select obj from BabyDetail obj where uhid = '" + uhid + "' order by babydetailid asc";
			List<BabyDetail> babyDetailList = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
			timelinessQuery = "select obj from BabyVisit obj where uhid = '" + uhid + "' order by creationtime asc";
			List<BabyVisit> antenatalLists = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//			returnObj.setRegistration_date(babyDetailList.get(0).getCreationtime() + "");
			if(!BasicUtils.isEmpty(antenatalLists))
//				returnObj.setInitial_assessment_date(antenatalLists.get(0).getCreationtime() + "");

			if(!BasicUtils.isEmpty(antenatalLists) && antenatalLists.size() > 0 && (antenatalLists.get(0).getCreationtime().getTime() > babyDetailList.get(0).getCreationtime().getTime())) {

				timeElapsed += ((antenatalLists.get(0).getCreationtime().getTime() - babyDetailList.get(0).getCreationtime().getTime()) / (60 * 60 * 1000));


			}
//			if(timeElapsed != 0) {
//				returnObj.setRegistration_initialassessment_difference(timeElapsed);
//			}

			timeElapsed = 0;
			timelinessQuery = "select obj from BabyVisit obj where uhid = '" + uhid + "' order by creationtime asc";
			antenatalLists = prescriptionDao.getListFromMappedObjNativeQuery(timelinessQuery);
//			if(!BasicUtils.isEmpty(antenatalLists) && antenatalLists.size() > 1 && (antenatalLists.get(1).getCreationtime().getTime() > babyDetailList.get(0).getCreationtime().getTime())) {
//
//				timeElapsed += ((antenatalLists.get(1).getCreationtime().getTime() - babyDetailList.get(0).getCreationtime().getTime()) / (60 * 60 * 1000));
//				returnObj.setFirst_anthropometry_date(antenatalLists.get(1).getCreationtime() + "");
//
//			}
//			if(timeElapsed != 0) {
//				returnObj.setRegistration_anthropometry_difference(timeElapsed);
//			}





			//Table 5
			long regainbirthWeightDays = -1;
			String queryGrowthDetail = "select obj from BabyVisit as obj where uhid='" + uhid + "' order by creationtime";
			List<BabyVisit> growthNoteList = inicuDao.getListFromMappedObjQuery(queryGrowthDetail);
			if (!BasicUtils.isEmpty(growthNoteList)) {
				Float birthWeight,minWeight,dischargeWeight;
				birthWeight = minWeight = dischargeWeight  = null;
				Timestamp birthDate,dischargeDate,regainBirthWeightDate,minWeightDate;
				birthDate = dischargeDate = regainBirthWeightDate =  minWeightDate = null;
				boolean firstMinWeight,weightGain;
				firstMinWeight = weightGain = false;
				for(int i=0;i<growthNoteList.size();i++) {
					BabyVisit growthObj = growthNoteList.get(i);
					if(i==0) {
						birthDate = growthObj.getCreationtime();
						if(growthObj.getCurrentdateweight() != null) {
							minWeight = birthWeight = growthObj.getCurrentdateweight();
						}
					}
					else {
				      if(!weightGain && birthDate != null && growthObj.getCurrentdateweight() != null
				    	&& Float.compare(birthWeight, growthObj.getCurrentdateweight()) <= 0) {
				    	  regainBirthWeightDate = growthObj.getCreationtime();
				    	  regainbirthWeightDays = (growthObj.getCreationtime().getTime() - babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);

				    	  weightGain = true;
				      }else if(weightGain && birthDate != null && growthObj.getCurrentdateweight() != null
						    	&& Float.compare(birthWeight, growthObj.getCurrentdateweight()) >= 0) {
				    	  weightGain = false;
				    	  regainBirthWeightDate =  null;
				      }




				      if(i == growthNoteList.size()-1) {
				    	  dischargeDate = growthObj.getCreationtime();
				    	  if(growthObj.getCurrentdateweight() != null) {
				    		  dischargeWeight = growthObj.getCurrentdateweight();
				    	  }
				      }
					}
				}

				Float growthVelocity,meanWeight;
				Long duration;
				growthVelocity = meanWeight = 0f;
				duration = 0L;
				if(birthWeight != null && dischargeWeight != null ) {
					meanWeight = (birthWeight+dischargeWeight)/(2000);
				}

				if(dischargeDate != null && regainBirthWeightDate != null ) {
					duration = (dischargeDate.getTime() - regainBirthWeightDate.getTime()) / (1000 * 60 * 60 * 24);
				}else if(birthDate != null && dischargeDate != null) {
					duration = (dischargeDate.getTime() - birthDate.getTime()) / (1000 * 60 * 60 * 24);
				}

				if(dischargeWeight != null && birthWeight != null && meanWeight != 0f && duration != 0L ) {
					growthVelocity = (dischargeWeight - birthWeight)/(meanWeight * duration);
				}else if(dischargeWeight != null && birthWeight != null && meanWeight != 0f) {
					growthVelocity = (dischargeWeight - birthWeight)/(meanWeight * 1);

				}
//				if(regainbirthWeightDays != -1)
//					returnObj.setGrowthVelocity(String.valueOf(regainbirthWeightDays));
//				else
//					returnObj.setGrowthVelocity(String.valueOf(""));

			}

			String feedQuery = "select obj from BabyfeedDetail obj where uhid = '" + uhid + "' order by entrydatetime asc";
			List<BabyfeedDetail> feedList = prescriptionDao.getListFromMappedObjNativeQuery(feedQuery);
			float totalfluidMlDay = 0;
			int totalfluidMlDayCount = 0;
			float energy = 0;
			float protein = 0;
			float calcium = 0;
			float phosphorus = 0;
			float Vitamind = 0;
			float energyCount = 0;
			float proteinCount = 0;
			float calciumCount = 0;
			float phosphorusCount = 0;
			float VitamindCount = 0;
			for(BabyfeedDetail babyFeed : feedList) {
				if(!BasicUtils.isEmpty(babyFeed.getTotalfluidMlDay())){
					totalfluidMlDay += babyFeed.getTotalfluidMlDay();
					totalfluidMlDayCount++;
				}
//
//				FeedCalculatorPOJO calculator = new FeedCalculatorPOJO();
//
//				BabyfeedDetail babyFeedDetailsObj = babyFeed;
//
//				String nutritionCalculator = "select obj from RefNutritioncalculator obj";
//				List<RefNutritioncalculator> nutritionList = prescriptionDao.getListFromMappedObjNativeQuery(nutritionCalculator);
//				calculator.setRefNutritionInfo(nutritionList);
//
//				if(!BasicUtils.isEmpty(babyFeedDetailsObj.getWorkingWeight())) {
//
//					float energyTemp = 0;
//					float proteinTemp = 0;
//					float calciumTemp = 0;
//					float phosphorusTemp = 0;
//					float VitamindTemp = 0;
//					String weight = (babyFeedDetailsObj.getWorkingWeight()) + "";
//					CaclulatorDeficitPOJO cuurentDeficitLast = systematicService.getDeficitFeedCalculator(babyFeedDetailsObj, nutritionList,
//							weight);
//					calculator.setLastDeficitCal(cuurentDeficitLast);
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Protein"))) {
//						proteinTemp = calculator.getLastDeficitCal().getParentalIntake().get("Protein");
//					}
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Protein"))) {
//						proteinTemp += calculator.getLastDeficitCal().getEnteralIntake().get("Protein");
//					}
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Energy"))) {
//						energyTemp = calculator.getLastDeficitCal().getParentalIntake().get("Energy");
//					}
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Energy"))) {
//						energyTemp += calculator.getLastDeficitCal().getEnteralIntake().get("Energy");
//					}
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getParentalIntake().get("Calcium"))) {
//						calciumTemp = calculator.getLastDeficitCal().getParentalIntake().get("Calcium");
//					}
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Calcium"))) {
//						calciumTemp += calculator.getLastDeficitCal().getEnteralIntake().get("Calcium");
//					}
//
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Phosphorus"))) {
//						phosphorusTemp = calculator.getLastDeficitCal().getEnteralIntake().get("Phosphorus");
//					}
//					if(!BasicUtils.isEmpty(calculator.getLastDeficitCal().getEnteralIntake().get("Vitamind"))) {
//						VitamindTemp = calculator.getLastDeficitCal().getEnteralIntake().get("Vitamind");
//					}
//					if(proteinTemp > 0) {
//						proteinCount++;
//						protein += proteinTemp;
//					}
//					if(energyTemp > 0) {
//						energyCount++;
//						energy += energyTemp;
//					}
//					if(calciumTemp > 0) {
//						calciumCount++;
//						calcium += calciumTemp;
//					}
//					if(phosphorusTemp > 0) {
//						proteinCount++;
//						phosphorus += phosphorusTemp;
//					}
//					if(VitamindTemp > 0) {
//						VitamindCount++;
//						Vitamind += VitamindTemp;
//					}
//
//
//				}
			}
//			if(proteinCount > 0) {
//				returnObj.setProtein_Intake(String.valueOf(protein/proteinCount));
//
//			}
//			if(energyCount > 0) {
//				returnObj.setCalorie_Intake(String.valueOf(energy/energyCount));
//
//			}
//			if(calciumCount > 0) {
//				returnObj.setCalcium_Intake(String.valueOf(calcium/calciumCount));
//
//			}
//			if(phosphorusCount > 0) {
//				returnObj.setPhosphorus_Intake(String.valueOf(phosphorus/phosphorusCount));
//
//			}
//			if(VitamindCount > 0) {
//				returnObj.setVitaminD_Intake(String.valueOf(Vitamind/VitamindCount));
//
//			}
			if(totalfluidMlDayCount >0) {
				returnObj.setTotalFluidLimit(String.valueOf(totalfluidMlDay / totalfluidMlDayCount));
			}


			long ageAtFullFeedAll = -1;
//			float enteralVolume = -1;
//			feedQuery = "select obj from NursingIntakeOutput obj where uhid = '" + uhid + "' and abdomen_girth is not null order by creationtime asc";
//			List<NursingIntakeOutput> feedsList = prescriptionDao.getListFromMappedObjNativeQuery(feedQuery);
//			if(!BasicUtils.isEmpty(feedsList)) {
//				for(NursingIntakeOutput intake : feedsList) {
//					if(!BasicUtils.isEmpty(intake.getAbdomenGirth())) {
//						if(initialEnteralVol != 0) {
//							float diff = Float.parseFloat(intake.getAbdomenGirth()) - initialEnteralVol;
//							if(diff >= 2) {
//								returnObj.setInitialEnteralVol(diff);
//							}
//						}
//						initialEnteralVol = Float.parseFloat(intake.getAbdomenGirth());
//
//					}
//				}
//			}

			float feedVolume = -1;
			float feedVolumeperml = -1;
			List<Float> feedValues = new ArrayList<Float>();




//			returnObj.setInitialAbdGirth(initialAbdGirth);
//			returnObj.setFinalAbdGirth(finalAbdGirth);
//			returnObj.setInitialGastricAsp(initialGastricAsp);
//			returnObj.setFinalGastricAsp(finalGastricAsp);
//			returnObj.setFinalEnteralVol(finalEnteralVol);

//			if(initalDateAbd != null)
//			returnObj.setInitalDateAbd(initalDateAbd + "");
//			if(finalDateAbd != null)
//			returnObj.setFinalDateAbd(finalDateAbd + "");
			Timestamp startTime = null;
			Timestamp currentTime = null;
			String prevUhid = "";
			long respDays = 0;
			boolean isBpdIdentified = false;
			String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
//			for(RespSupport resp : bpdList) {
//				if(!isBpdIdentified) {
//					if(prevUhid == "") {
//						prevUhid = uhid;
//						startTime = resp.getCreationtime();
//					}else {
//						currentTime = resp.getCreationtime();
//						if(resp.getIsactive()) {
//							continue;
//						}else if(!resp.getIsactive()) {
//							long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
//							respDays = respDays + daysBpd;
//							if(respDays >= 28) {
//								returnObj.setBpd(String.valueOf(respDays));
//								isBpdIdentified = true;
//							}
//							startTime = null;
//							prevUhid = "";
//						}
//					}
//				}
//			}

			startTime = null;
			currentTime = null;

			prevUhid = "";
			respDays = 0;
			float mapCount = 0;

			float fio2Count = 0;

			float peep = -1;
			float peepCount = 0;

			float tv = -1;
			float tvCount = 0;
			boolean pressureControl = false;
			boolean volumeControl = false;
			long cpapTime = -1;

			bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
			if(!BasicUtils.isEmpty(bpdList)) {
				RespSupport supportObj = bpdList.get(0);
				if(!BasicUtils.isEmpty(supportObj.getRsVentType()) && supportObj.getRsVentType().equalsIgnoreCase("CPAP")) {
					cpapTime += ((supportObj.getCreationtime().getTime() -  babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000));
//					returnObj.setCpapTime(String.valueOf(cpapTime));
				}
			}

			bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
			for(RespSupport resp : bpdList) {
				if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {


					String sepsisQueries = "select obj from SaSepsis obj where uhid = '" + uhid + "' order by assessment_time asc";
					List<SaSepsis> sepsisLists = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQueries);
					if(!BasicUtils.isEmpty(sepsisLists)) {
//						returnObj.setSepsisMech("1");
					}

//					if(!BasicUtils.isEmpty(resp.getCreationtime()) && cpapTime == -1) {
//						cpapTime += ((resp.getCreationtime().getTime() -  babyObj.getDateofbirth().getTime()) / (24 * 60 * 60 * 1000));
//						returnObj.setCpapTime(String.valueOf(cpapTime));
//					}

					if(!BasicUtils.isEmpty(resp.getRsMap()) && mapCount == 0) {
//						returnObj.setMapSurfactant(resp.getRsMap());
						mapCount++;
					}
					if(!BasicUtils.isEmpty(resp.getRsFio2()) && fio2Count == 0) {
						fio2Count++;
//						returnObj.setFio2Surfactant(resp.getRsFio2());
					}

					if(!BasicUtils.isEmpty(resp.getRsTv())){
						tv += Float.parseFloat(resp.getRsTv());
						tvCount++;
					}
					if(!BasicUtils.isEmpty(resp.getRsPeep())){
						peep += Float.parseFloat(resp.getRsPeep());
						peepCount++;
					}

					if(!BasicUtils.isEmpty(resp.getRsControlType()) && resp.getRsControlType().equalsIgnoreCase("Pressure Control") && !pressureControl) {
						pressureControl = true;
					}
					if(!BasicUtils.isEmpty(resp.getRsControlType()) && resp.getRsControlType().equalsIgnoreCase("Volume Control") && !volumeControl) {
						volumeControl = true;
					}


					if(prevUhid == "") {
						prevUhid = uhid;
						startTime = resp.getCreationtime();
					}else {
						currentTime = resp.getCreationtime();
						if(resp.getIsactive()) {
							continue;
						}else if(!resp.getIsactive()) {
							long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
							respDays = respDays + daysBpd;
							startTime = null;
							prevUhid = "";
						}
					}
				}else if(!prevUhid.equalsIgnoreCase("")) {
					currentTime = resp.getCreationtime();
					long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
					respDays = respDays + daysBpd;
					startTime = null;
					prevUhid = "";
				}

			}


//			if(tv!= -1) {
//				returnObj.setTv(String.valueOf(tv/tvCount));
//			}
//			if(peep != -1) {
//				returnObj.setPeep(String.valueOf(peep/peepCount));
//			}
//			returnObj.setRespDuration((int)respDays);
//			if(pressureControl) {
//				returnObj.setPressureControl("1");
//			}else {
//				returnObj.setPressureControl("");
//			}
//			if(volumeControl) {
//				returnObj.setVolumeControl("1");
//			}else {
//				returnObj.setVolumeControl("");
//			}

//			bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
//			bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
//			for(RespSupport resp : bpdList) {
//				if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
//					int antibioticUsage = 0;
//
//					Timestamp currentDate = null;
//					String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and route ='IV' and medicationtype='TYPE0001' and startdate < '" + resp.getCreationtime() +"'order by startdate asc";
//					List<BabyPrescription> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
//					for(BabyPrescription obj : medList) {
//						if(!BasicUtils.isEmpty(obj.getMedicineOrderDate()) && !BasicUtils.isEmpty(obj.getEnddate())) {
//							if(currentDate == null || (currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {
//
//								currentDate = obj.getEnddate();
//
//								antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / (24 * 60 * 60 * 1000));
//							}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {
//
//								antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000));
//								currentDate = obj.getEnddate();
//							}
//
//						}
//					}
//					returnObj.setAntibioticDaysInvasive(String.valueOf(antibioticUsage));
//					break;
//				}
//			}
//

			String 	sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' order by assessment_time asc";
			boolean isNEC = false;
			boolean isLONS = false;
			boolean isEONS = false;
//			boolean isRiskFactors = false;
//			boolean isSymptomatic = false;
			List<SaSepsis> sepsisListObj = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQuery);
			for(SaSepsis obj : sepsisListObj) {

				if(!BasicUtils.isEmpty(obj.getBloodCultureStatus()) && obj.getBloodCultureStatus().equalsIgnoreCase("Positive")) {
					returnObj.setProvenSepsis("1");
				}
//				if(!BasicUtils.isEmpty(obj.getSepsisType()) && obj.getSepsisType().equalsIgnoreCase("Proven Sepsis")) {
//					returnObj.setProvenSepsis("1");
//				}
				if(!BasicUtils.isEmpty(obj.getAgeatonset()) && Float.parseFloat(obj.getAgeatonset()) < 72 && isLONS == false) {
					isEONS = true;
					if(!BasicUtils.isEmpty(obj.getRiskfactor())) {
					}
				}else if(!BasicUtils.isEmpty(obj.getAgeatonset()) && Float.parseFloat(obj.getAgeatonset()) >= 72 && isEONS == false){
					isLONS = true;
					if(!BasicUtils.isEmpty(obj.getSymptomaticValue())) {
					}
				}
//				String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and eventid = '" + obj.getSasepsisid() + "'  and medicationtype='TYPE0001' order by startdate asc";
//				List<BabyPrescription> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
//				for(BabyPrescription medObj : medList) {
//					if(!BasicUtils.isEmpty(medObj.getMedicinename()) && episodeSepsisAntibiotics.indexOf(medObj.getMedicinename()) == -1)
//						episodeSepsisAntibiotics.add(medObj.getMedicinename());
//					else if(BasicUtils.isEmpty(episodeSepsisAntibiotics))
//						episodeSepsisAntibiotics.add(medObj.getMedicinename());
//
//				}
			}
//			if(!BasicUtils.isEmpty(episodeSepsisAntibiotics))
//				returnObj.setAntibioticInEpisode(episodeSepsisAntibiotics.size());
//
//			if(isLONS)
//				returnObj.setLONS(1);
//			if(isEONS)
//				returnObj.setEONS(1);
//			if(isRiskFactors)
//				returnObj.setRiskFactors(1);
//			if(isSymptomatic)
//				returnObj.setSymptoms(1);


			int antibioticUsage = 0;
			String bloodGroups = "";

// 			String testResultQuery = "select obj from TestItemResult obj where prn = '" + uhid + "' order by lab_report_date asc";
// 			List<TestItemResult> testResultList = prescriptionDao.getListFromMappedObjNativeQuery(testResultQuery);
// 			if(!BasicUtils.isEmpty(testResultList)) {
// 				for(TestItemResult obj : testResultList) {
//
// 					if(!BasicUtils.isEmpty(obj.getLabTestName()) && !BasicUtils.isEmpty(obj.getItemname()) && !BasicUtils.isEmpty(obj.getItemvalue()) && !BasicUtils.isEmpty(obj.getLabReportDate())) {
// 						long diffDays = (obj.getLabReportDate().getTime() - babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);
// 						if(true) {
//
// 							if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")) {
// 								if(obj.getLabTestName().equalsIgnoreCase("C-REACTIVE PROTEIN CRP (QUANTITATIVE)") && BasicUtils.isEmpty(returnObj.getCrp())){
// 									returnObj.setCrp(obj.getItemvalue());
// 								}
// 								if(obj.getItemname().equalsIgnoreCase("TOTAL LEUCOCYTE COUNT (TLC)") && BasicUtils.isEmpty(returnObj.getTlc())){
// 									returnObj.setTlc(obj.getItemvalue());
// 								}
// //								if(obj.getItemname().equalsIgnoreCase("NEUTROPHILS") && BasicUtils.isEmpty(returnObj.getNeotrophil()) && obj.getItemunit().equalsIgnoreCase("Cells/cu.mm")){
// //									returnObj.setNeotrophil(obj.getItemvalue());
// //								}
// 								if(obj.getItemname().equalsIgnoreCase("BLOOD GROUP TYPE") && BasicUtils.isEmpty(returnObj.getBabyBloodGroup())){
// 									bloodGroups += (obj.getItemvalue());
// 								}
// 								if(obj.getItemname().equalsIgnoreCase("Rh TYPE") && BasicUtils.isEmpty(returnObj.getBabyBloodGroup())){
// 									bloodGroups += " " + (obj.getItemvalue());
// 								}
//
//
//
//
// 							}
// 							else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("kalawati")) {
// 								if(obj.getItemname().equalsIgnoreCase("CRP  RESULT") && BasicUtils.isEmpty(returnObj.getCrp())){
// 									returnObj.setCrp(obj.getItemvalue());
// 								}
// 								if(obj.getItemname().equalsIgnoreCase("TLC(TOTAL LEUCOCYTE COUNT)") && BasicUtils.isEmpty(returnObj.getTlc())){
// 									returnObj.setTlc(obj.getItemvalue());
// 								}
// //								if(obj.getItemname().equalsIgnoreCase("NEUTROPHIL%") && BasicUtils.isEmpty(returnObj.getNeotrophil())){
// //									returnObj.setNeotrophil(obj.getItemvalue());
// //								}
// 								if(obj.getItemname().equalsIgnoreCase("ABO")){
// 									bloodGroups += (obj.getItemvalue());
// 								}
// 								if(obj.getItemname().equalsIgnoreCase("RH TYPING")){
// 									bloodGroups += " " + (obj.getItemvalue());
// 								}
// 							}
// 						}
// 					}
// 				}
// 			}
			if(!BasicUtils.isEmpty(babyObj.getBloodgroup())) {
				returnObj.setBabyBloodGroup(babyObj.getBloodgroup());
			}

			Timestamp currentDate = null;
			String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and route ='IV' and medicationtype='TYPE0001' order by startdate asc";
			List<BabyPrescription> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			for(BabyPrescription obj : medList) {
				if(!BasicUtils.isEmpty(obj.getMedicineOrderDate()) && !BasicUtils.isEmpty(obj.getEnddate())) {
					if(currentDate == null || (currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {

						currentDate = obj.getEnddate();

						antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / (24 * 60 * 60 * 1000));
					}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {

						antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000));
						currentDate = obj.getEnddate();
					}

				}


			}
//			returnObj.setAntibioticDaysSepsis(String.valueOf(antibioticUsage));
			if(antibioticUsage < 5 && antibioticUsage > 0) {
				returnObj.setSuspectedSepsis("1");
			}
			if(antibioticUsage >= 5) {
				returnObj.setProbableSepsis("1");
			}

			medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicinename = 'Piperacilin Tazobactam' order by startdate asc";
			medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			if(!BasicUtils.isEmpty(medList)){
				returnObj.setAntibioticLine1(1);
			}
			medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicinename = 'Amikacin' order by startdate asc";
			medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			if(!BasicUtils.isEmpty(medList)){
				returnObj.setAntibioticLine2(1);
			}
			medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicinename = 'Meropenem' order by startdate asc";
			medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			if(!BasicUtils.isEmpty(medList)){
				returnObj.setAntibioticLine3(1);
			}
			medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicinename = 'Colisitin' order by startdate asc";
			medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			if(!BasicUtils.isEmpty(medList)){
				returnObj.setAntibioticLine4(1);
			}
			medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicinename = 'Vancomycin' order by startdate asc";
			medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
			if(!BasicUtils.isEmpty(medList)){
				returnObj.setAntibioticLine5(1);
			}



			//Consistency

			startTime = null;
			currentTime = null;

			prevUhid = "";
			long kmcDays = 0;
			long humanMilkDays = 0;
			long ageAtFeed = -1;
			long ageAtFullFeed = -1;
			float cpapTimes = 0;
			float hhfnncTime = 0;
			float invasiveTime = 0;
			if(true) {

				List<VwRespiratoryUsageFinal> respSupportList = inicuDao.getListFromMappedObjQuery(
						"SELECT s FROM VwRespiratoryUsageFinal s where uhid='" + babyObj.getUhid() + "'");
				if (!BasicUtils.isEmpty(respSupportList)) {

					for(VwRespiratoryUsageFinal respSupportObj : respSupportList) {
						if (!(BasicUtils.isEmpty(respSupportObj.getDifferenceinhrs())
								|| BasicUtils.isEmpty(respSupportObj.getRsVentType()))) {
							int currentDuration = ((Float) respSupportObj.getDifferenceinhrs()).intValue();

							if (respSupportObj.getRsVentType().equalsIgnoreCase("Low Flow O2") || respSupportObj.getRsVentType().equalsIgnoreCase("High Flow O2")) {
//								returnObj.setHHHFNNCCountVLBW(String.valueOf("1"));
								hhfnncTime += currentDuration;

							} else if (respSupportObj.getRsVentType().equalsIgnoreCase("CPAP") || respSupportObj.getRsVentType().equalsIgnoreCase("NIMV")) {
//								returnObj.setCpapCountVLBW(String.valueOf("1"));
								cpapTimes += currentDuration;
							} else if (respSupportObj.getRsVentType().equalsIgnoreCase("Mechanical Ventilation") || respSupportObj.getRsVentType().equalsIgnoreCase("HFO")) {
//								returnObj.setInvasiveCountVLBW(String.valueOf("1"));
								invasiveTime += currentDuration;
							}
						}
					}

				}
//				if(cpapTimes > 0) {
//					returnObj.setCpapDaysVLBW(String.valueOf(cpapTimes));
//				}
//				if(hhfnncTime > 0) {
//					returnObj.setHHHFNNCDaysVLBW(String.valueOf(hhfnncTime));
//				}
//				if(invasiveTime > 0) {
//					returnObj.setInvasiveDaysVLBW(String.valueOf(invasiveTime));
//				}
				String kmcQuery = "select obj from NursingVitalparameter obj where uhid = '" + uhid + "' order by entryDate asc";
				List<NursingVitalparameter> kmcList = prescriptionDao.getListFromMappedObjNativeQuery(kmcQuery);



				for(NursingVitalparameter obj : kmcList) {
					String currentUhid = obj.getUhid();
					if(prevUhid == "" && obj.getVpPosition().equalsIgnoreCase("KMC")) {
						prevUhid = uhid;
						startTime = obj.getEntryDate();
					}else if(prevUhid != ""){
						currentTime = obj.getEntryDate();
						if(obj.getVpPosition().equalsIgnoreCase("KMC")) {
							continue;
						}else if(!obj.getVpPosition().equalsIgnoreCase("KMC")) {
							long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
							kmcDays = kmcDays + daysBpd;
							startTime = null;
							prevUhid = "";
						}
					}
				}
				returnObj.setKmcDuration(String.valueOf(kmcDays));

				float primaryFeed = 0;
				float secondaryFeed = 0;
				intakeQuery = "select obj from NursingIntakeOutput obj where uhid = '" + uhid + "'";
				List<NursingIntakeOutput> intakeList = prescriptionDao.getListFromMappedObjNativeQuery(intakeQuery);
				for(NursingIntakeOutput intake : intakeList) {
					if(!BasicUtils.isEmpty(intake.getPrimaryFeedValue())) {
						primaryFeed += intake.getPrimaryFeedValue();
					}
					if(!BasicUtils.isEmpty(intake.getFormulaValue())) {
						secondaryFeed += intake.getFormulaValue();
					}
				}
				float humanMilkProportion = 0;
				float totalFeed = primaryFeed + secondaryFeed;
				if(totalFeed != 0) {
					humanMilkProportion = (primaryFeed / totalFeed) * 100;
				}

//				if(humanMilkProportion == 0)
//					returnObj.setHumanMilkDays(String.valueOf("1"));
//				else {
//					returnObj.setHumanMilkDays(String.valueOf(""));
//				}

				startTime = null;
				currentTime = null;
				prevUhid = "";

				feedQuery = "select obj from BabyfeedDetail obj where uhid = '" + uhid + "' order by entrydatetime asc";
				feedList = prescriptionDao.getListFromMappedObjNativeQuery(feedQuery);
				if(uhid.equalsIgnoreCase("RSHI.0000014934")) {
					int p =0;
				}
				boolean feedIncrement = false;
				for(BabyfeedDetail obj : feedList) {
					if(ageAtFeed == -1 && !BasicUtils.isEmpty(obj.getIsenternalgiven()) && obj.getIsenternalgiven()) {
						ageAtFeed = (obj.getEntryDateTime().getTime() - babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);
						if(ageAtFeed < 0) {
							ageAtFeed = 0;
						}
					}

					if(ageAtFullFeed == -1 && !BasicUtils.isEmpty(obj.getTotalenteralvolume()) && !BasicUtils.isEmpty(obj.getWorkingWeight())) {
						float ageFeedVolume = obj.getTotalenteralvolume() / obj.getWorkingWeight();
						if(ageFeedVolume >= 150) {
							ageAtFullFeed = (obj.getEntryDateTime().getTime() - babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);
							if(ageAtFullFeed < 0) {
								ageAtFullFeed = 0;
							}
						}
					}
					if(!BasicUtils.isEmpty(obj.getTotalenteralvolume()) && !BasicUtils.isEmpty(obj.getWorkingWeight())) {
						float ageFeedVolume = obj.getTotalenteralvolume() / obj.getWorkingWeight();
						if(ageFeedVolume <= 170 && !feedIncrement) {
							if(feedVolume == -1) {
								feedVolume = obj.getTotalenteralvolume() / obj.getWorkingWeight();
								feedVolumeperml = obj.getTotalenteralvolume();
								feedValues.add(feedVolume);
							}else {
								Float enteralVolumeperDay = obj.getTotalenteralvolume() / obj.getWorkingWeight();;
								if(feedVolume != enteralVolumeperDay && (enteralVolumeperDay - feedVolume != 0)) {
									if(obj.getTotalenteralvolume() - feedVolumeperml < 0) {
//										feedValues = new ArrayList<Float>();
//										feedValues.add(enteralVolumeperDay);

									}
									//else
									if(obj.getTotalenteralvolume() - feedVolumeperml > 0){
										feedValues.add(enteralVolumeperDay - feedVolume);
									}
									feedVolume = enteralVolumeperDay;
									feedVolumeperml = obj.getTotalenteralvolume();
								}
							}
						}
						if(ageFeedVolume > 170) {
							 feedIncrement = true;
						}
					}
				}

				if(feedValues != null && feedValues.size() > 1) {
					float values = 0;
					for(Float feed : feedValues) {
						values += feed;
					}
					returnObj.setFeedIncrement(String.valueOf(values / feedValues.size()));
				}
				respDays = 0;
				bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
				bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
				for(RespSupport resp : bpdList) {
					if(prevUhid == "") {
						prevUhid = uhid;
						startTime = resp.getCreationtime();
					}else {
						currentTime = resp.getCreationtime();
						if(resp.getIsactive()) {
							continue;
						}else if(!resp.getIsactive()) {
							long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
							respDays = respDays + daysBpd;
							startTime = null;
							prevUhid = "";
						}
					}
				}
				returnObj.setRespiratoryDaysVLBW(String.valueOf(respDays));

				antibioticUsage = 0;

				currentDate = null;
				medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and route ='IV' and medicationtype='TYPE0001' order by startdate asc";
				medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
				for(BabyPrescription obj : medList) {
					if(!BasicUtils.isEmpty(obj.getMedicineOrderDate()) && !BasicUtils.isEmpty(obj.getEnddate())) {
						if(currentDate == null || (currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {

							currentDate = obj.getEnddate();

							antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / (24 * 60 * 60 * 1000));
						}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {

							antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000));
							currentDate = obj.getEnddate();
						}

					}


				}
				returnObj.setAntibioticDaysVLBW(String.valueOf(antibioticUsage));
				
				String queryPastRiskListNec = "select obj from SaNec as obj where uhid = '" + uhid
						+ "' and riskfactor!=null order by creationtime desc";
				List<SaNec> pastRiskListNec = inicuDao.getListFromMappedObjQuery(queryPastRiskListNec);
				if(!BasicUtils.isEmpty(pastRiskListNec)) {
					returnObj.setNEC("true");
				}
				
				String queryApneaNurse = "select obj from NursingEpisode as obj where uhid = '" + uhid
						+ "' and apnea = 'true' order by creationtime desc";
				List<NursingEpisode> pastApneaList = inicuDao.getListFromMappedObjQuery(queryApneaNurse);
				if(!BasicUtils.isEmpty(pastApneaList)) {
					returnObj.setApnea("true");
				}



				if(ageAtFeed != -1)
					returnObj.setAgeAtFeed(String.valueOf(ageAtFeed));
				if(ageAtFullFeed > 0)
					returnObj.setAgeAtFullFeeds(String.valueOf(ageAtFullFeed));
				else {
//					for(BabyfeedDetail obj : feedList) {
//						if(ageAtFullFeed < 150) {
//							float fullfeeds = 0;
//							Long id = obj.getBabyfeedid();
//							String intakeOutputQuery = "select obj from NursingIntakeOutput obj where uhid = '" + uhid + "' and babyfeedid = '" + id +"'";
//							List<NursingIntakeOutput> intakeOutputList = prescriptionDao.getListFromMappedObjNativeQuery(intakeOutputQuery);
//							for(NursingIntakeOutput intakeObj : intakeOutputList) {
//								if(!BasicUtils.isEmpty(intakeObj.getActualFeed())) {
//									fullfeeds += intakeObj.getActualFeed();
//								}
//								if(!BasicUtils.isEmpty(intakeObj.getFormulaValue())) {
//									fullfeeds += intakeObj.getFormulaValue();
//								}
//								if(fullfeeds >= 150) {
//									ageAtFullFeed = (intakeObj.getEntry_timestamp().getTime() - babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);
//									if(ageAtFullFeed < 0) {
//										ageAtFullFeed = 0;
//									}
//									returnObj.setAgeAtFullFeeds(String.valueOf(ageAtFullFeed));
//									break;
//								}
//							}
//						}
//					}
				}

			}
			else {
				returnObj.setKmcDuration("");
				returnObj.setAgeAtFeed("");
				returnObj.setAgeAtFullFeeds("");
				returnObj.setHumanMilkDays("");

			}


//			if(!BasicUtils.isEmpty(babyObj.getAdmissionHeadCircumference()) && !BasicUtils.isEmpty(babyObj.getAdmissionLength())) {
//				returnObj.setAnthropometryAdmission(1);
//			}
			int firstDownes = -1;
			Long resprdsidFirst = null;
			String fio2 = "";
			String map = "";
			long surfactantTime = -1;
			boolean insureDone = false;
			boolean surfactant = false;
			String antenatalQuery = "select obj from AntenatalHistoryDetail obj where uhid = '" + babyObj.getUhid() + "' order by creationtime desc";
			List<AntenatalHistoryDetail> antenatalList = prescriptionDao.getListFromMappedObjNativeQuery(antenatalQuery);
			if(!BasicUtils.isEmpty(antenatalList)) {
				if(!BasicUtils.isEmpty(antenatalList.get(0).getConceptionType())) {
					returnObj.setConception(antenatalList.get(0).getConceptionType());
				}
				

				if(!BasicUtils.isEmpty(antenatalList.get(0).getIsAntenatalSteroidGiven()) && antenatalList.get(0).getIsAntenatalSteroidGiven().equalsIgnoreCase("true")) {
					returnObj.setRdsAntenatalSteroid("1");

				}
				if(!BasicUtils.isEmpty(antenatalList.get(0).getUmbilicalDoppler()) && antenatalList.get(0).getUmbilicalDoppler().equalsIgnoreCase("normal")) {
					returnObj.setUmbilicalDopplerVLBW("1");
				}

				if(!BasicUtils.isEmpty(antenatalList.get(0).getProm()) && antenatalList.get(0).getProm()) {
					returnObj.setPromInvasive("1");
				}
				else if(!BasicUtils.isEmpty(antenatalList.get(0).getUmbilicalDoppler()) && antenatalList.get(0).getUmbilicalDoppler().equalsIgnoreCase("abnormal")) {
					if(!BasicUtils.isEmpty(antenatalList.get(0).getAbnormalUmbilicalDopplerType())){
						if(antenatalList.get(0).getAbnormalUmbilicalDopplerType().equalsIgnoreCase("Increased")) {
							returnObj.setUmbilicalDopplerIncreasedVLBW("1");
						}
						if(antenatalList.get(0).getAbnormalUmbilicalDopplerType().equalsIgnoreCase("Reverse")) {
							returnObj.setUmbilicalDopplerReverseVLBW("1");
						}
						if(antenatalList.get(0).getAbnormalUmbilicalDopplerType().equalsIgnoreCase("Absent")) {
							returnObj.setUmbilicalDopplerAbsentVLBW("1");
						}
					}
				}
			}

			String rdsQuery = "select obj from SaRespRds obj where uhid = '" + uhid + "' order by assessment_time asc";
			List<SaRespRds> SaRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
			if(!BasicUtils.isEmpty(SaRespRdsList)) {
				returnObj.setRdsIncidence("1");



				for(SaRespRds obj : SaRespRdsList) {
					if(!BasicUtils.isEmpty(obj.getSurfactantDose()) && !BasicUtils.isEmpty(obj.getSufactantname())) {
						returnObj.setRdsSurfactant("1");
					}
				}

				respDays = 0;
				startTime = null;
				currentTime = null;
				prevUhid = "";
				bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
				bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);

			}
			
			String queryRespUsage = "select obj from RespSupport obj where uhid='" + uhid
					+ "' and creationtime <= '" + babyObj.getDischargeddate() + "' order by creationtime asc";
			//List<VwRespiratoryUsageFinal> newRespUsageList = new ArrayList<VwRespiratoryUsageFinal>();
			List<RespSupport> respUsageList = inicuDao.getListFromMappedObjQuery(queryRespUsage);
			
			Long invasiveDays = (long) 0;
			Long cpapDays = (long) 0;
			Long lowFlowDays = (long) 0;
			Long highFlowDays = (long) 0;
			Long nimvDays = (long) 0;
			
			String initialMode = "";
			startTime = null;
			for(RespSupport resp : respUsageList) {
				if(startTime==null) {
					startTime = resp.getCreationtime();
					if(!BasicUtils.isEmpty(resp.getRsVentType()) && !BasicUtils.isEmpty(resp.getEventname())) {
						initialMode = resp.getRsVentType();

					}
				}else if(!BasicUtils.isEmpty(startTime)){
					if(!BasicUtils.isEmpty(resp.getIsactive()) && resp.getIsactive() && !BasicUtils.isEmpty(resp.getRsVentType())) {
						if(initialMode.equalsIgnoreCase(resp.getRsVentType())) {
							//Insert without end
							continue;
						}else {
							//Pick last-> calculate end time and insert new without end date
							Long diffInMinutes = (resp.getCreationtime().getTime() - startTime.getTime()) / (1000 * 60 * 60);
							switch(initialMode) {
								case "High Flow O2":
									highFlowDays += diffInMinutes;
									break;
								case "Low Flow O2":
									lowFlowDays += diffInMinutes;
									break;
								case "CPAP":
									cpapDays += diffInMinutes;
									break;
								case "NIMV":
									nimvDays += diffInMinutes;
									break;
								case "HFO":
									invasiveDays += diffInMinutes;
									break;
								case "Mechanical Ventilation":
									invasiveDays += diffInMinutes;
									break;
								
							}
							startTime = resp.getCreationtime();
							initialMode = resp.getRsVentType();
						}
						
					}else if((!BasicUtils.isEmpty(startTime)) && ((!BasicUtils.isEmpty(resp.getIsactive()) && resp.getIsactive() == false) || (BasicUtils.isEmpty(resp.getIsactive())))){
						//Pick last-> calculate end time
						Long diffInMinutes = (resp.getCreationtime().getTime() - startTime.getTime()) / (1000 * 60 * 60);
						switch(initialMode) {
							case "High Flow O2":
								highFlowDays += diffInMinutes;
								break;
							case "Low Flow O2":
								lowFlowDays += diffInMinutes;
								break;
							case "CPAP":
								cpapDays += diffInMinutes;
								break;
							case "NIMV":
								nimvDays += diffInMinutes;
								break;
							case "HFO":
								invasiveDays += diffInMinutes;
								break;
							case "Mechanical Ventilation":
								invasiveDays += diffInMinutes;
								break;
							
						}
						initialMode = "";
					}
				}
			}
			
			returnObj.setInvasiveDaysVLBW(invasiveDays);
			returnObj.setCpapDaysVLBW(cpapDays);
			returnObj.setLowFlowDaysVLBW(lowFlowDays);
			returnObj.setHighFlowDaysVLBW(highFlowDays);
			returnObj.setNimvDaysVLBW(nimvDays);

//			for(RespSupport resp : bpdList) {
//				if(respDays >= 0 && !BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
//
//					if(prevUhid == "") {
//						prevUhid = uhid;
//						startTime = resp.getCreationtime();
//					}else {
//						currentTime = resp.getCreationtime();
//						if(resp.getIsactive()) {
//							continue;
//						}else if(!resp.getIsactive()) {
//							long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
//							respDays = respDays + daysBpd;
//							startTime = null;
//							prevUhid = "";
//						}
//					}
//				}else if(!prevUhid.equalsIgnoreCase("")) {
//					currentTime = resp.getCreationtime();
//					long daysBpd = ((currentTime.getTime() - startTime.getTime()) / (1000 * 24 * 60 * 60));
//					respDays = respDays + daysBpd;
//					startTime = null;
//					prevUhid = "";
//				}
//
//			}
//			if(respDays > 0) {
//				returnObj.setRdsInvasiveVentilationDays(String.valueOf(respDays));
//			}

			for(SaRespRds obj : SaRespRdsList) {


				if(!BasicUtils.isEmpty(obj.getIsinsuredone()) && !insureDone && obj.getIsinsuredone()){
					insureDone = true;
					returnObj.setInsure("1");
				}
				if(!BasicUtils.isEmpty(obj.getSurfactantDose()) && !BasicUtils.isEmpty(obj.getSufactantname()) && !surfactant) {
					returnObj.setSurfactantDose(obj.getSurfactantDose());
					surfactantTime = (obj.getAssessmentTime().getTime() - babyObj.getDateofadmission().getTime()) / (60 * 60 * 1000);
					returnObj.setSurfactantTime(String.valueOf(surfactantTime));
					
					surfactant = true;
				}

				if (!BasicUtils.isEmpty(obj.getDownesscoreid()) && firstDownes == -1) {
					List<ScoreDownes> downesList = inicuDao.getListFromMappedObjQuery(
							"SELECT s FROM ScoreDownes s where downesscoreid=" + obj.getDownesscoreid());
					if (!BasicUtils.isEmpty(downesList)) {
						firstDownes = downesList.get(0).getDownesscore();
						resprdsidFirst = obj.getResprdsid();
					}
				}
				if(!BasicUtils.isEmpty(obj.getSufactantname())) {
					String respSupportQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' and eventid = '" + obj.getResprdsid() + "'";
					List<RespSupport> respSupportList = prescriptionDao.getListFromMappedObjNativeQuery(respSupportQuery);
					if(!BasicUtils.isEmpty(respSupportList)) {
						if(!BasicUtils.isEmpty(respSupportList.get(0).getRsFio2()))
							fio2 = respSupportList.get(0).getRsFio2();
						if(!BasicUtils.isEmpty(respSupportList.get(0).getRsMap()))
							map = respSupportList.get(0).getRsMap();
					}
				}
			}

			if(resprdsidFirst != null) {
				if(!BasicUtils.isEmpty(resprdsidFirst)) {
					returnObj.setFirstDownes(String.valueOf(firstDownes));
				}
			}



			String surfactantQuery = "select obj from AssessmentMedication obj where uhid = '" + uhid + "' order by assessmenttime asc";
			List<AssessmentMedication> surfNursingList = prescriptionDao.getListFromMappedObjNativeQuery(surfactantQuery);
			if(!BasicUtils.isEmpty(surfNursingList)) {
				AssessmentMedication obj = surfNursingList.get(0);
				surfactantTime = (obj.getAssessmenttime().getTime() - babyObj.getDateofbirth().getTime()) / (60 * 60 * 1000);
//				returnObj.setSurfactantNurseTime(String.valueOf(surfactantTime));
			}

			//If mother is negative and baby is positive
			float firstPhototherapy = -1;

			float firstTcb = -1;
			float secondTcb = -1;
			startTime = null;
			currentTime = null;
			String jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + uhid + "' order by assessment_time asc";
			List<SaJaundice> jaunList = prescriptionDao.getListFromMappedObjNativeQuery(jaundiceQuery);
			if(!BasicUtils.isEmpty(jaunList)) {
				returnObj.setJaundiceOccured("1");
			}
			for(SaJaundice obj : jaunList) {

				if(!BasicUtils.isEmpty(obj.getPhototherapyvalue()) && (obj.getPhototherapyvalue().equalsIgnoreCase("Start"))) {
//					returnObj.setRiskFactorJaundice("1");
				}
				if (!BasicUtils.isEmpty(obj.getPhototherapyvalue()) && (obj.getPhototherapyvalue().equalsIgnoreCase("Start") || obj.getPhototherapyvalue().equalsIgnoreCase("Continue"))) {
					startTime = obj.getAssessmentTime();

					String babyVisitQuery = "select obj from BabyVisit obj where uhid = '" + uhid + "' and creationtime <= '"  + obj.getCreationtime() + "' order by creationtime desc";
					List<BabyVisit> visitList = prescriptionDao.getListFromMappedObjNativeQuery(babyVisitQuery);
					if(!BasicUtils.isEmpty(visitList)) {
						float weightLoss = ((babyObj.getBirthweight() - visitList.get(0).getCurrentdateweight()) / (babyObj.getBirthweight())) * 100;
//						returnObj.setWeightLoss(String.valueOf(weightLoss));
					}else {
						babyVisitQuery = "select obj from BabyVisit obj where uhid = '" + uhid + "' and creationtime >= '"  + obj.getCreationtime() + "' order by creationtime asc";
						visitList = prescriptionDao.getListFromMappedObjNativeQuery(babyVisitQuery);
						if(!BasicUtils.isEmpty(visitList)) {
							float weightLoss = ((babyObj.getBirthweight() - visitList.get(0).getCurrentdateweight()) / (babyObj.getBirthweight())) * 100;
//							returnObj.setWeightLoss(String.valueOf(weightLoss));
						}
					}

					if(!BasicUtils.isEmpty(obj.getTbcvalue()) && firstTcb == -1) {
						firstTcb = obj.getTbcvalue();
						if(!BasicUtils.isEmpty(obj.getIsphotobelowthreshold())) {
							if(obj.getIsphotobelowthreshold().equalsIgnoreCase("belowThreshold")) {
//								returnObj.setTcb(String.valueOf("1"));
							}else {
//								returnObj.setTcb(String.valueOf("0"));
							}
						}
					}
				}else if(!BasicUtils.isEmpty(obj.getPhototherapyvalue()) && obj.getPhototherapyvalue().equalsIgnoreCase("Stop") && startTime != null && firstPhototherapy == -1) {
					currentTime = obj.getAssessmentTime();
					long phototherapyDays = ((currentTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
					if(firstPhototherapy == -1) {
						returnObj.setPhototherapyDuration(String.valueOf(phototherapyDays));
					}
					if(!BasicUtils.isEmpty(obj.getTbcvalue()) && secondTcb == -1) {
						secondTcb = obj.getTbcvalue();
					}
					firstPhototherapy = 1;

				}
			}
			returnObj.setFirsttcb(firstTcb);
			returnObj.setSecondtcb(secondTcb);

		    //Screening
			String screenQuery = "select obj from ScreenRop obj where uhid = '" + uhid + "'";
			List<ScreenRop> ropLists = prescriptionDao.getListFromMappedObjNativeQuery(screenQuery);
			if (!BasicUtils.isEmpty(ropLists)) {
				returnObj.setRopCount(ropLists.size());
			}

			screenQuery = "select obj from ScreenUSG obj where uhid = '" + uhid + "'";
			List<ScreenUSG> usgLists = prescriptionDao.getListFromMappedObjNativeQuery(screenQuery);
			if (!BasicUtils.isEmpty(usgLists)) {
				returnObj.setUsgCount(usgLists.size());
			}

			screenQuery = "select obj from ScreenMetabolic obj where uhid = '" + uhid + "'";
			List<ScreenMetabolic> metabolicLists = prescriptionDao.getListFromMappedObjNativeQuery(screenQuery);
			if (!BasicUtils.isEmpty(metabolicLists)) {
				returnObj.setMetabolicCount(metabolicLists.size());
			}

			screenQuery = "select obj from ScreenHearing obj where uhid = '" + uhid + "'";
			List<ScreenHearing> hearingLists = prescriptionDao.getListFromMappedObjNativeQuery(screenQuery);
			if (!BasicUtils.isEmpty(hearingLists)) {
				returnObj.setHearingCount(hearingLists.size());
			}

			screenQuery = "select obj from ScreenNeurological obj where uhid = '" + uhid + "'";
			List<ScreenNeurological> neurologicalLists = prescriptionDao.getListFromMappedObjNativeQuery(screenQuery);
			if (!BasicUtils.isEmpty(neurologicalLists)) {
				returnObj.setNeurologicalCount(neurologicalLists.size());
			}
			long limitDol = (babyObj.getDateofadmission().getTime() - babyObj.getDateofbirth().getTime()) / (24 * 60 * 60 * 1000);
		    float birthWeight = babyObj.getBirthweight();
		    int gestationWeeks = babyObj.getGestationweekbylmp();

			java.sql.Date ultrasoundDate = new Date(new java.util.Date().getTime());
			java.sql.Date ropDate = new Date(new java.util.Date().getTime());

		    // code for Ultrasound Screen
			if (gestationWeeks < 32 || ((birthWeight)) < 1500) {
				if (limitDol <= 3) {
					long incrementalDate = 3 * 24 * 60 * 60 * 1000;
					Date newDate = new Date(babyObj.getDateofbirth().getTime() + (incrementalDate)) ;
					ultrasoundDate = new Date(newDate.getTime());
//					returnObj.setUsgDueDate(ultrasoundDate);


				} else if (limitDol > 3 && limitDol <= 7) {
					long incrementalDate = 7 * 24 * 60 * 60 * 1000;
					Date newDate =  new Date(babyObj.getDateofbirth().getTime() + (incrementalDate)) ;
					ultrasoundDate = new Date(newDate.getTime());
//					returnObj.setUsgDueDate(ultrasoundDate);
				} else if (limitDol > 7 && limitDol <= 28) {
		          long incrementalDate = 28 * 24 * 60 * 60 * 1000;
		          Date newDate =  new Date(babyObj.getDateofbirth().getTime() + (incrementalDate)) ;
				  ultrasoundDate = new Date(newDate.getTime());
//				  returnObj.setUsgDueDate(ultrasoundDate);
		        }
		    }

		    // code for ROP Screen
			if (gestationWeeks < 28 || (birthWeight) < 1200) {
				if (limitDol <= 21) {
					Date newDate =  new Date(babyObj.getDateofbirth().getTime() + (10 * 24 * 60 * 60 * 1000) + (10 * 24 * 60 * 60 * 1000) + (24 * 60 * 60 * 1000)) ;
					ropDate = new Date(newDate.getTime());
//					returnObj.setRopDueDate(ropDate);
				}
			} else if ((gestationWeeks >= 28 && gestationWeeks < 34) ||
					((birthWeight) >= 1200 && (birthWeight) < 2000)) {
				if (limitDol <= 28) {
					Date newDate =  new Date(babyObj.getDateofbirth().getTime() + (10 * 24 * 60 * 60 * 1000) + (10 * 24 * 60 * 60 * 1000) + (8 * 24 * 60 * 60 * 1000));
					ropDate = new Date(newDate.getTime());
//					returnObj.setRopDueDate(ropDate);
				}
			}
//			boolean isSepsis = false;
//			antibioticUsage = 0;
//			sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' order by assessment_time asc";
//			List<SaSepsis> sepsisLists = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQuery);
//			for(SaSepsis obj : sepsisLists) {
//				if (!BasicUtils.isEmpty(obj.getBloodCultureStatus()) && obj.getBloodCultureStatus().equalsIgnoreCase("Positive")) {
//					isSepsis = true;
//				}
//			}
//			if(BasicUtils.isEmpty(sepsisLists)) {
//				isSepsis = true;
//				returnObj.setAntibioticDaysSepsis("");
//			}
//			if(!isSepsis) {
//				currentDate = null;
//				medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and route ='IV' and medicationtype='TYPE0001' order by startdate asc";
//				medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
//				for(BabyPrescription obj : medList) {
//					if(!BasicUtils.isEmpty(obj.getMedicineOrderDate()) && !BasicUtils.isEmpty(obj.getEnddate())) {
//						if(currentDate == null || (currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {
//
//							currentDate = obj.getEnddate();
//
//							antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / (24 * 60 * 60 * 1000));
//						}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {
//
//							antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000));
//							currentDate = obj.getEnddate();
//						}
//
//					}
//				}
//				returnObj.setAntibioticDaysSepsis(String.valueOf(antibioticUsage));
//			}
//
			if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
				java.sql.Date d = new Date(new java.util.Date().getTime());
				Timestamp today = new Timestamp(d.getTime());
				long diffInDays = ((today.getTime() - babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000));

				returnObj.setPatient_stay(((Long) diffInDays).intValue() + 1);

			} else {
				long diffInDays = (babyObj.getDischargeddate().getTime() - babyObj.getDateofadmission().getTime())
						/ (24 * 60 * 60 * 1000);
				returnObj.setPatient_stay(((Long) diffInDays).intValue() + 1);
			}

			if (!BasicUtils.isEmpty(babyObj.getTimeofbirth())) {
				String[] timeArr = babyObj.getTimeofbirth().split(",");
				if (timeArr.length == 3) {
					returnObj.setTob(timeArr[0] + ":" + timeArr[1] + " " + timeArr[2]);
				}
			}
			returnObj.setGender(babyObj.getGender());
			returnObj.setDoa(babyObj.getDateofadmission().toString());
			returnObj.setOutcome(babyObj.getDischargestatus());
			returnObj.setBirth_length(babyObj.getBirthlength());
			returnObj.setBirth_hc(babyObj.getBirthheadcircumference());
			returnObj.setBirth_weight_level(babyObj.getWeight_galevel());
			returnObj.setPonderal_index(babyObj.getPonderal_index());
			if (!BasicUtils.isEmpty(babyObj.getGestationweekbylmp())) {
				if (!BasicUtils.isEmpty(babyObj.getDischargeddate())) {

					long diffInDays = (babyObj.getDischargeddate().getTime() - babyObj.getDateofadmission().getTime())
							/ (24 * 60 * 60 * 1000);
					int daysAfterBirth = (((Long) diffInDays).intValue() + 1);
					int gestationDays = (babyObj.getGestationweekbylmp() * 7)
							+ babyObj.getGestationdaysbylmp();

					int gestaionTotalDays = daysAfterBirth + gestationDays;

					if (gestaionTotalDays % 7 == 0) {
						returnObj.setDisch_gestation_weeks(gestaionTotalDays / 7);
						returnObj.setDisch_gestation_days(0);
					} else {
						int actualDays = gestaionTotalDays % 7;
						returnObj.setDisch_gestation_weeks((gestaionTotalDays - actualDays) / 7);
						returnObj.setDisch_gestation_days(actualDays);
					}
				}
			}

			if (BasicUtils.isEmpty(babyObj.getDischargeddate())) {
				// java.util.Date lastDate = CalendarUtility.dateFormatDB.parse("2017-09-30");
				// long diffInDays = (lastDate.getTime() -
				// babyObj.getDateofadmission().getTime()) / (24 * 60 * 60 * 1000);
				// returnObj.setPatient_stay(((Long) diffInDays).intValue() + 1);
			} else {
				long diffInDays = (babyObj.getDischargeddate().getTime() - babyObj.getDateofadmission().getTime())
						/ (24 * 60 * 60 * 1000);
				returnObj.setPatient_stay(((Long) diffInDays).intValue() + 1);
			}

			returnObj.setPatient_type(babyObj.getInoutPatientStatus());

			// parent table
			List<ParentDetail> parentList = (List<ParentDetail>) inicuDao.getListFromMappedObjQuery(
					"SELECT b FROM ParentDetail b where uhid='" + uhid + "' order by creationtime desc");
			if (!BasicUtils.isEmpty(parentList)) {
				ParentDetail parentObj = parentList.get(0);
				returnObj.setSe_status(parentObj.getKuppuswamy_class());
			}

			List<BabyVisit> visitList = (List<BabyVisit>) inicuDao
					.getListFromMappedObjQuery("SELECT b FROM BabyVisit b where uhid='" + uhid + "' and visitdate='"
							+ sdf.format(babyObj.getDateofadmission()) + "' order by creationtime desc");


			if (babyObj.getDischargeddate() != null) {
				String dod = sdf.format(babyObj.getDischargeddate());
				returnObj.setDod(dod);
				java.sql.Date sqlAdmissionDate = new java.sql.Date(babyObj.getDischargeddate().getTime());

				List<BabyVisit> dischargevisitList = (List<BabyVisit>) inicuDao
						.getListFromMappedObjQuery("select obj from BabyVisit obj where uhid='" + uhid + "' order by visitdate desc");
				if (!BasicUtils.isEmpty(dischargevisitList)) {
					BabyVisit visitObj = dischargevisitList.get(0);
					returnObj.setDischarge_weight(visitObj.getCurrentdateweight());
					if(!BasicUtils.isEmpty(visitObj.getAdmissionHeadCircumference())) {
						returnObj.setDischarge_hc(visitObj.getAdmissionHeadCircumference());
					}
					if(!BasicUtils.isEmpty(visitObj.getAdmissionLength())) {
						returnObj.setDischarge_length(visitObj.getAdmissionLength());
					}
				}
			}

			//Reason Of Admission
	        String reasonQuery = "select obj from ReasonAdmission obj where uhid = '" + uhid + "'";
			List<ReasonAdmission> reasonAdmissionList = prescriptionDao.getListFromMappedObjNativeQuery(reasonQuery);
			for(ReasonAdmission obj : reasonAdmissionList) {

				switch(obj.getCause_event()) {
					case "IVH" :
						returnObj.setIVH_Reason(1);
						break;
					case "Hypoglycemia" :
						returnObj.setHypoglycemia_Reason(1);

						break;
					case "Hyperglycemia" :
						returnObj.setHyperglycemia_Reason(1);
						break;
					case "Hyponatremia" :
						returnObj.setHyponatremia_Reason(1);
						break;
					case "Hypernatremia" :
						returnObj.setHypernatremia_Reason(1);
						break;
					case "Hypocalcemia" :
						returnObj.setHypocalcemia_Reason(1);
						break;
					case "Hypercalcemia" :
						returnObj.setHypercalcemia_Reason(1);
						break;
					case "Hypokalemia" :
						returnObj.setHypokalemia_Reason(1);
						break;
					case "Hyperkalemia" :
						returnObj.setHyperkalemia_Reason(1);
						break;
					case "Acidosis" :
						returnObj.setAcidosis_Reason(1);
						break;
					case "IEM" :
						returnObj.setIEM_Reason(1);
						break;
					case "Feed Intolerance" :
						returnObj.setFeed_Intolerance_Reason(1);
						break;
					case "Other" :
						returnObj.setOther_Reason(1);
						break;
					case "Prematurity" :
						returnObj.setPrematurity_Reason(1);
						break;
					case "Seizures" :
						returnObj.setSeizures_Reason(1);
						break;
					case "Asphyxia" :
						returnObj.setAsphyxia_Reason(1);
						break;
					case "Sepsis" :
						returnObj.setSepsis_Reason(1);
						break;
					case "Pneumothorax" :
						returnObj.setPneumothorax_Reason(1);
						break;
					case "PPHN" :
						returnObj.setPPHN_Reason(1);
						break;
					case "Apnea" :
						returnObj.setApnea_Reason(1);
						break;
					case "Respiratory Distress" :
						returnObj.setRespiratory_Distress_Reason(1);
						break;
					case "Jaundice" :
						returnObj.setJaundice_Reason(1);
						break;
				}
			}

			//Baby Type
			if(!BasicUtils.isEmpty(babyObj.getBabyType())) {
				switch(babyObj.getBabyType()) {
					case "Single" :
						returnObj.setSingle_Pregnancy(1);
						break;
					case "Twins" :
						returnObj.setTwins(1);
						break;
					case "Triplets" :
						returnObj.setTriplets(1);
						break;
					case "Quadruplets" :
						returnObj.setQuadruplets(1);
						break;
					}
			}

			//Gestation
			int gestationWeek = babyObj.getGestationweekbylmp();

			if(gestationWeek <= 26) {
				returnObj.setLess_than_26_week_Gestation(1);
			}else if(gestationWeek > 26 && gestationWeek <= 28) {
				returnObj.setGestation_26_28_week(1);
			}else if(gestationWeek > 28 && gestationWeek <= 32) {
				returnObj.setGestation_28_32_week(1);
			}else if(gestationWeek > 32 && gestationWeek <= 36) {
				returnObj.setGestation_32_36_week(1);
			}else if(gestationWeek > 36) {
				returnObj.setGreater_than_36_week_Gestation(1);
			}

			//GPAL, Risk Factors and other antenatal factors like medications, steroids
			antenatalQuery = "select obj from AntenatalHistoryDetail obj where uhid = '" + babyObj.getUhid() + "' order by creationtime desc";
			antenatalList = prescriptionDao.getListFromMappedObjNativeQuery(antenatalQuery);
			if(!BasicUtils.isEmpty(antenatalList)) {
				AntenatalHistoryDetail obj = antenatalList.get(0);
				if(!BasicUtils.isEmpty(obj.getgScore()) && obj.getgScore() != 0) {
					returnObj.setG(obj.getgScore());
				}
				if(!BasicUtils.isEmpty(obj.getpScore()) && obj.getpScore() != 0) {
					returnObj.setP(obj.getpScore());
				}
				if(!BasicUtils.isEmpty(obj.getaScore()) && obj.getaScore() != 0) {
					returnObj.setA(obj.getaScore());
				}
				if(!BasicUtils.isEmpty(obj.getlScore()) && obj.getlScore() != 0) {
					returnObj.setL(obj.getlScore());
				}
				if(!BasicUtils.isEmpty(obj.getHistoryOfSmoking()) && obj.getHistoryOfSmoking()) {
					returnObj.setSmoking_Risk_Factor(1);
				}
				if(!BasicUtils.isEmpty(obj.getHistoryOfAlcohol()) && obj.getHistoryOfAlcohol()) {
					returnObj.setAlcoholism_Risk_Factor(1);
				}
				if(!BasicUtils.isEmpty(obj.getHypertension()) && obj.getHypertension()) {
					returnObj.setHypertension_Risk_Factor(1);
;
				}
				if(!BasicUtils.isEmpty(obj.getGestationalHypertension()) && obj.getGestationalHypertension()) {
					returnObj.setGestational_Hypertension_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getDiabetes()) && obj.getDiabetes()) {
					returnObj.setDiabetes_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getGdm()) && obj.getGdm()) {
					returnObj.setGestational_Diabetes_Milleteus_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getChronicKidneyDisease()) && obj.getChronicKidneyDisease()) {
					returnObj.setChronic_Kidney_Disease_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getHypothyroidism()) && obj.getHypothyroidism()) {
					returnObj.setHypothyroidism_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getHyperthyroidism()) && obj.getHyperthyroidism()) {
					returnObj.setSmoking_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getFever()) && obj.getFever()) {
					returnObj.setHyperthyroidism_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getUti()) && obj.getUti()) {
					returnObj.setUTI_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getHistoryOfInfections()) && obj.getHistoryOfInfections()) {
					returnObj.setInfections_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getProm()) && obj.getProm()) {
					returnObj.setPROM_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getPprom()) && obj.getPprom()) {
					returnObj.setPprom_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getPrematurity()) && obj.getPrematurity()) {
					returnObj.setPrematurity_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getChorioamniotis()) && obj.getChorioamniotis()) {
					returnObj.setSmoking_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getOligohydraminos()) && obj.getOligohydraminos()) {
					returnObj.setChorioamniotis_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getPolyhydraminos()) && obj.getPolyhydraminos()) {
					returnObj.setPolyhydraminos_Risk_Factor(1);

				}
				if(!BasicUtils.isEmpty(obj.getHivType())) {
					returnObj.setHIV(1);

				}
				if(!BasicUtils.isEmpty(obj.getHepbType())) {
					returnObj.setHep_B(1);

				}
				if(!BasicUtils.isEmpty(obj.getVdrlType())) {
					returnObj.setVDRL(1);

				}
				if(!BasicUtils.isEmpty(obj.getMotherBloodGroupAbo()) && !BasicUtils.isEmpty(obj.getMotherBloodGroupRh())) {
					returnObj.setMother_Blood_Group(obj.getMotherBloodGroupAbo());

				}
				if(!BasicUtils.isEmpty(obj.getTorch()) && obj.getTorch()) {
					returnObj.setTORCH(1);

				}
				if(!BasicUtils.isEmpty(obj.getTetanusToxoid()) && obj.getTetanusToxoid()) {
					returnObj.setTetanus_Toxoid(1);

				}
				
				if(!BasicUtils.isEmpty(obj.getMgso4()) && obj.getMgso4().equalsIgnoreCase("Yes")) {
					returnObj.setMgso4("Yes");

				}
				if(!BasicUtils.isEmpty(obj.getIsAntenatalSteroidGiven()) && obj.getIsAntenatalSteroidGiven().equalsIgnoreCase("true")) {
					returnObj.setAntenata_Steroids(1);
					String antenatalSteroidQuery = "select obj from AntenatalSteroidDetail obj where uhid = '" + babyObj.getUhid() + "' order by creationtime asc";
					List<AntenatalSteroidDetail>  antenatalSteroidList = prescriptionDao.getListFromMappedObjNativeQuery(antenatalSteroidQuery);
					if(!BasicUtils.isEmpty(antenatalSteroidList)) {
						if(!BasicUtils.isEmpty(antenatalSteroidList.get(0).getSteroidname()))
							returnObj.setDoseName(antenatalSteroidList.get(0).getSteroidname());
						if(!BasicUtils.isEmpty(antenatalSteroidList.get(0).getNumberofdose()))
							returnObj.setNumberOfDose(antenatalSteroidList.get(0).getNumberofdose());
					}
					

				}else if(!BasicUtils.isEmpty(obj.getIsAntenatalSteroidGiven()) && obj.getIsAntenatalSteroidGiven().equalsIgnoreCase("false")) {
					returnObj.setAntenata_Steroids(0);

				}
				if(!BasicUtils.isEmpty(obj.getModeOfDelivery())) {
					returnObj.setMode_Of_Delivery(obj.getModeOfDelivery());

				}
				String bloodGroup = "";
				if(!BasicUtils.isEmpty(obj.getMotherBloodGroupAbo())) {
					bloodGroup = (obj.getMotherBloodGroupAbo());

				}
				if(!BasicUtils.isEmpty(obj.getMotherBloodGroupRh())) {
					bloodGroup += " " + (obj.getMotherBloodGroupRh());

				}

				returnObj.setMother_Blood_Group(bloodGroup);

			}
			//APGAR
			String birthToNicuQuery = "select obj from BirthToNicu obj where uhid = '" + babyObj.getUhid() + "' order by creationtime desc";
			List<BirthToNicu> birthToNicuList = prescriptionDao.getListFromMappedObjNativeQuery(birthToNicuQuery);
			if(!BasicUtils.isEmpty(birthToNicuList)) {
				BirthToNicu obj = birthToNicuList.get(0);
				if(!BasicUtils.isEmpty(obj.getApgarOnemin())) {
					returnObj.setOnemin_APGAR(obj.getApgarOnemin());

				}
				if(!BasicUtils.isEmpty(obj.getApgarFivemin())) {
					returnObj.setFivemin_APGAR(obj.getApgarFivemin());
					
					if(obj.getApgarFivemin() <= 3) {
						returnObj.setFivemin_APGAR_asphyxia(obj.getApgarFivemin());
					}

				}
				
				if(!BasicUtils.isEmpty(obj.getResuscitationPpv()) && obj.getResuscitationPpv()) {
					if((!BasicUtils.isEmpty(obj.getPpvTime()) && obj.getPpvTime().equalsIgnoreCase("secs") && !BasicUtils.isEmpty(obj.getResuscitationPpvDuration()) && Integer.parseInt(obj.getResuscitationPpvDuration()) > 60) 
							|| !BasicUtils.isEmpty(obj.getPpvTime()) && obj.getPpvTime().equalsIgnoreCase("mins")) {
						returnObj.setPpv_asphyxia(obj.getResuscitationPpvDuration() + " " + obj.getPpvTime());
						
					}
				}
			}
		
			if(!BasicUtils.isEmpty(babyObj.getAdmissionWeight())) {
				returnObj.setAdmission_weight(babyObj.getAdmissionWeight());
			}

			if(!BasicUtils.isEmpty(babyObj.getGestationdaysbylmp())) {
				returnObj.setGestation_days(babyObj.getGestationdaysbylmp());
			}

			if(!BasicUtils.isEmpty(babyObj.getGestationweekbylmp())) {
				returnObj.setGestation_weeks(babyObj.getGestationweekbylmp());
			}
			
			String query = "SELECT obj FROM NursingBloodGas as obj where uhid='" + babyObj.getUhid() + "' order by entrydate asc";
			List<NursingBloodGas> bloodGasList = prescriptionDao.getListFromMappedObjNativeQuery(query);
			if(!BasicUtils.isEmpty(bloodGasList)) {
				for(NursingBloodGas blood : bloodGasList) {
					if(!BasicUtils.isEmpty(blood.getPh()) && (Float.parseFloat(blood.getPh())) < 7) {
						returnObj.setPh_asphyxia(blood.getPh());
					}
				}
			}
			
			//Anthropometry
			if(!BasicUtils.isEmpty(babyObj.getBirthweight())) {
				returnObj.setBirth_weight(babyObj.getBirthweight());
				if(babyObj.getBirthweight() <= 500) {
					returnObj.setBW_less_than_500(1);

				}else if(babyObj.getBirthweight() > 500 && babyObj.getBirthweight() <= 999) {
					returnObj.setBW_between_500_999(1);

				}else if(babyObj.getBirthweight() > 1000 && babyObj.getBirthweight() <= 1499) {
					returnObj.setBW_bewtween_1000_1499(1);

				}else if(babyObj.getBirthweight() > 1500 && babyObj.getBirthweight() <= 1999) {
					returnObj.setBW_between_1500_1999(1);

				}else if(babyObj.getBirthweight() >= 2000) {
					returnObj.setBW_greater_than_2000(1);

				}
			}

			if(!BasicUtils.isEmpty(babyObj.getWeight_centile())) {
				returnObj.setWeight_Centile(babyObj.getWeight_centile());
			}
			if(!BasicUtils.isEmpty(babyObj.getLength_centile())) {
				returnObj.setLength_Centile(babyObj.getLength_centile());
			}
			if(!BasicUtils.isEmpty(babyObj.getHc_centile())) {
				returnObj.setHead_Circumference_Centile(babyObj.getHc_centile());
			}

			//General Physician Examination
			String genPhyQuery = "select obj from GenPhyExam obj where uhid = '" + babyObj.getUhid() + "' order by creationtime desc";
			List<GenPhyExam> genPhyList = prescriptionDao.getListFromMappedObjNativeQuery(genPhyQuery);
			if(!BasicUtils.isEmpty(genPhyList)) {
				GenPhyExam obj = genPhyList.get(0);
				if(!BasicUtils.isEmpty(obj.getApearance())) {
					returnObj.setGeneral_Apearance(obj.getApearance());
				}
				if(!BasicUtils.isEmpty(obj.getChest()) && !obj.getChest().equalsIgnoreCase("Normal")) {
					returnObj.setChest(1);
				}
				if(!BasicUtils.isEmpty(obj.getHead_neck()) && !obj.getHead_neck().equalsIgnoreCase("Normal")) {
					returnObj.setHead_and_Neck(1);
				}
				if(!BasicUtils.isEmpty(obj.getAbdomen()) && !obj.getAbdomen().equalsIgnoreCase("Normal")) {
					returnObj.setAbdomen(1);
				}
				if(!BasicUtils.isEmpty(obj.getPalate()) && !obj.getPalate().equalsIgnoreCase("Normal")) {
					returnObj.setPalate(1);
				}
				if(!BasicUtils.isEmpty(obj.getAnal())) {
					returnObj.setAnal_opening(obj.getAnal());
				}
				if(!BasicUtils.isEmpty(obj.getLip()) && !obj.getLip().equalsIgnoreCase("Normal")) {
					returnObj.setLips(1);
				}
				if(!BasicUtils.isEmpty(obj.getGenitals()) && !obj.getGenitals().equalsIgnoreCase("Normal")) {
					returnObj.setGenitals(1);
				}
				if(!BasicUtils.isEmpty(obj.getEyes()) && !obj.getEyes().equalsIgnoreCase("Normal")) {
					returnObj.setEyes(1);
				}
				if(!BasicUtils.isEmpty(obj.getReflexes()) && !obj.getReflexes().equalsIgnoreCase("Normal")) {
					returnObj.setReflexes(1);
				}
				if(!BasicUtils.isEmpty(obj.getSkin()) && !obj.getSkin().equalsIgnoreCase("Normal")) {
					returnObj.setSkin(1);
				}
				if(!BasicUtils.isEmpty(obj.getCong_malform()) && !obj.getCong_malform().equalsIgnoreCase("No")) {
					returnObj.setApparent_Congenital_malformation(1);
				}
			}

			//Procedures
//			procedureQuery = "SELECT distinct procedure_type FROM vw_procedures_usage where uhid='" + babyObj.getUhid() + "'";
//			List<String> procedureDetail = (List<String>)  prescriptionDao.getListFromNativeQuery(procedureQuery);
//			if (!BasicUtils.isEmpty(procedureDetail)) {
//				for (String procedureName : procedureDetail) {
//					switch(procedureName) {
//
//						case "Peripheral Cannula":
//							returnObj.setPeripheral_Cannula(1);
//							break;
//						case "Central Line":
//							returnObj.setCentral_Line(1);
//							break;
//						case "Lumbar Puncture":
//							returnObj.setLumbar_Puncture(1);
//							break;
//						case "VTap":
//							returnObj.setVTap(1);
//							break;
//						case "Chest Tube":
//							returnObj.setChest_Tube(1);
//							break;
//						case "ET Intubation":
//							returnObj.setET_Intubation(1);
//							break;
//						case "ET Suction":
//							returnObj.setET_Suction(1);
//							break;
//						case "Exchange Transfusion":
//							returnObj.setExchange_Transfusion(1);
//							break;
//					}
//				}
//			}

			//Downes
			String fetchDownesScore = "SELECT obj FROM ScoreDownes as obj where uhid='" + babyObj.getUhid() + "' order by creationtime asc";
			List<ScoreDownes> downesList = prescriptionDao.getListFromMappedObjNativeQuery(fetchDownesScore);
			if(!BasicUtils.isEmpty(downesList)) {
				int downesLength = downesList.size();
				returnObj.setFirst_Downes(downesList.get(0).getDownesscore());
				returnObj.setLast_Downes(downesList.get(downesLength - 1).getDownesscore());
			}
			fetchDownesScore = "SELECT max(downesscore) FROM score_downes as obj where uhid='" + babyObj.getUhid() + "'";
			List<Integer> maxDownes = prescriptionDao.getListFromNativeQuery(fetchDownesScore);
			if(!BasicUtils.isEmpty(maxDownes) && maxDownes.size() > 0) {
				if (!BasicUtils.isEmpty(maxDownes.get(0))) {
					returnObj.setHighest_Downes(maxDownes.get(0));
				}
			}

			//Surfactant
			boolean isSurfactant = false;
			String respRdsQuery = "SELECT obj FROM SaRespRds as obj where uhid='" + babyObj.getUhid() + "' order by creationtime asc";
			List<SaRespRds> rdsQList = prescriptionDao.getListFromMappedObjNativeQuery(respRdsQuery);
			for(SaRespRds obj : rdsQList) {
				if(!BasicUtils.isEmpty(obj.getSufactantname())) {
					returnObj.setSurfactant(1);
					returnObj.setSurfactantType(obj.getSufactantname());
					isSurfactant = true;
					break;
				}
			}

			//Apnea
			String respApneaQuery = "SELECT max(cummulative_apnea_on_caffeine) as one ,max(continuous_apnea_on_caffeine) as two ,max(apnea_free_days_after_caffeine) as three,max(cummulative_number_of_episodes) as four,max(cummulative_days_of_caffeine) as five FROM sa_resp_apnea as obj where uhid='" + babyObj.getUhid() + "'";
			List<Object[]> apneaList = prescriptionDao.getListFromNativeQuery(respApneaQuery);
			if(!BasicUtils.isEmpty(apneaList)) {
				Object[] apneaObject = apneaList.get(0);

//				if(!BasicUtils.isEmpty(apneaObject[0])) {
//					returnObj.setCummulative_Apnea_On_Caffeine(Integer.parseInt(apneaObject[0].toString()));
//				}
//				if(!BasicUtils.isEmpty(apneaObject[1])) {
//					returnObj.setContinuous_Apnea_On_Caffeine(Integer.parseInt(apneaObject[1].toString()));
//				}
//				if(!BasicUtils.isEmpty(apneaObject[2])) {
//					returnObj.setApnea_Free_Days_After_Caffeine(Integer.parseInt(apneaObject[2].toString()));
//				}
//				if(!BasicUtils.isEmpty(apneaObject[3])) {
//					returnObj.setCummulative_Number_Of_Episodes(Integer.parseInt(apneaObject[3].toString()));
//				}
//				if(!BasicUtils.isEmpty(apneaObject[4])) {
//					returnObj.setCummulative_Days_Of_Caffeine(Integer.parseInt(apneaObject[4].toString()));
//				}
			}

			//Nutrition
			float enDuration = 0;
			boolean enActive = false;
			boolean calciumENActive = false;
			boolean vitaminaENActive = false;
			boolean vitamindENActive = false;
			boolean mctENActive = false;
			boolean ironENActive = false;
			float ebmDuration = 0;
			boolean ebmActive = false;
			float pnDuration = 0;
			boolean pnActive = false;
			int bolusCount = 0;
			float aminoDuration = 0;
			boolean aminoActive = false;
			float lipidDuration = 0;
			boolean lipidActive = false;
			float pnAdditivesDuration = 0;
			boolean pnAdditivesActive = false;
			float dextroseDuration = 0;
			boolean dextroseActive = false;
			Timestamp enStartDate = null;
			Timestamp enEndDate = null;
			Timestamp pnStartDate = null;
			Timestamp pnEndDate = null;
			Timestamp aminoStartDate = null;
			Timestamp aminoEndDate = null;
			Timestamp lipidStartDate = null;
			Timestamp lipidEndDate = null;
			Timestamp pnAdditivesActiveStartDate = null;
			Timestamp pnAdditivesActiveEndDate = null;
			Timestamp dextroseStartDate = null;
			Timestamp dextroseEndDate = null;
			Timestamp ebmStartDate = null;
			Timestamp ebmEndDate = null;

//			feedQuery = "SELECT obj FROM BabyfeedDetail as obj where uhid='" + babyObj.getUhid() + "' and entryDateTime is not null order by entryDateTime asc";
//			feedList = prescriptionDao.getListFromMappedObjNativeQuery(feedQuery);
//			for(BabyfeedDetail obj : feedList) {
//
//				//EN Duration
//				if(!BasicUtils.isEmpty(obj.getIsenternalgiven()) && obj.getIsenternalgiven() && !enActive) {
//					enActive = true;
//					enStartDate = obj.getEntryDateTime();
//
//				}else if((BasicUtils.isEmpty(obj.getIsenternalgiven()) || (!BasicUtils.isEmpty(obj.getIsenternalgiven()) && !obj.getIsenternalgiven())) && enActive) {
//					enActive = false;
//					enEndDate = obj.getEntryDateTime();
//					long timeGap = (enEndDate.getTime() - enStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					enDuration += timeGap;
//				}
//
//				//EN Additives
//				if(!BasicUtils.isEmpty(obj.getCalsyrupTotal())) {
//					calciumENActive = true;
//				}
//				if(!BasicUtils.isEmpty(obj.getIronTotal())) {
//					ironENActive = true;
//				}
//				if(!BasicUtils.isEmpty(obj.getVitaminaTotal())) {
//					vitaminaENActive = true;
//				}
//				if(!BasicUtils.isEmpty(obj.getVitamindTotal())) {
//					vitamindENActive = true;
//				}
//				if(!BasicUtils.isEmpty(obj.getMctTotal())) {
//					mctENActive = true;
//				}
//
//				if((!BasicUtils.isEmpty(obj.getIsenternalgiven()) && obj.getIsenternalgiven()) && ((!BasicUtils.isEmpty(obj.getFeedtype()) && obj.getFeedtype().contains("TYPE01")) || (!BasicUtils.isEmpty(obj.getFeedmethod()) && obj.getFeedmethod().contains("METHOD03"))) && !ebmActive) {
//					ebmActive = true;
//					ebmStartDate = obj.getEntryDateTime();
//
//				}else if((BasicUtils.isEmpty(obj.getIsenternalgiven()) || (!BasicUtils.isEmpty(obj.getIsenternalgiven()) && !obj.getIsenternalgiven())) && ebmActive && (BasicUtils.isEmpty(obj.getFeedtype()) || (!BasicUtils.isEmpty(obj.getFeedtype()) && !obj.getFeedtype().contains("TYPE01"))) && (BasicUtils.isEmpty(obj.getFeedmethod()) || (!BasicUtils.isEmpty(obj.getFeedmethod()) && !obj.getFeedmethod().contains("METHOD03")))) {
//					ebmActive = false;
//					ebmEndDate = obj.getEntryDateTime();
//					long timeGap = (ebmEndDate.getTime() - ebmStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					ebmDuration += timeGap;
//				}
//
//				//PN Duration
//				if(!BasicUtils.isEmpty(obj.getIsparentalgiven()) && obj.getIsparentalgiven() && !pnActive) {
//					pnActive = true;
//					pnStartDate = obj.getEntryDateTime();
//
//				}else if((BasicUtils.isEmpty(obj.getIsparentalgiven()) || (!BasicUtils.isEmpty(obj.getIsparentalgiven()) && !obj.getIsparentalgiven())) && pnActive) {
//					pnActive = false;
//					pnEndDate = obj.getEntryDateTime();
//					long timeGap = (pnEndDate.getTime() - pnStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					pnDuration += timeGap;
//				}
//
//				//Bolus Count
//				if(!BasicUtils.isEmpty(obj.getIsBolusGiven()) && obj.getIsBolusGiven()) {
//					bolusCount++;
//				}
//
//				if(!BasicUtils.isEmpty(obj.getAminoacidTotal()) && !aminoActive) {
//					aminoActive = true;
//					aminoStartDate = obj.getEntryDateTime();
//
//				}else if(BasicUtils.isEmpty(obj.getAminoacidTotal()) && aminoActive) {
//					aminoActive = false;
//					aminoEndDate = obj.getEntryDateTime();
//					long timeGap = (aminoEndDate.getTime() - aminoStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					aminoDuration += timeGap;
//				}
//
//				//Lipid Duration
//				if(!BasicUtils.isEmpty(obj.getLipidTotal()) && !lipidActive) {
//					lipidActive = true;
//					lipidStartDate = obj.getEntryDateTime();
//
//				}else if(BasicUtils.isEmpty(obj.getLipidTotal()) && lipidActive) {
//					lipidActive = false;
//					lipidEndDate = obj.getEntryDateTime();
//					long timeGap = (lipidEndDate.getTime() - lipidStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					lipidDuration += timeGap;
//				}
//
//				//PN Additives Duration
//				if(!BasicUtils.isEmpty(obj.getTotalparenteralAdditivevolume()) && obj.getTotalparenteralAdditivevolume() > 0 && !pnAdditivesActive) {
//					pnAdditivesActive = true;
//					pnAdditivesActiveStartDate = obj.getEntryDateTime();
//
//				}else if((BasicUtils.isEmpty(obj.getTotalparenteralAdditivevolume()) || !BasicUtils.isEmpty(obj.getTotalparenteralAdditivevolume()) && obj.getTotalparenteralAdditivevolume() == 0) && pnAdditivesActive) {
//					pnAdditivesActive = false;
//					pnAdditivesActiveEndDate = obj.getEntryDateTime();
//					long timeGap = (pnAdditivesActiveEndDate.getTime() - pnAdditivesActiveStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					pnAdditivesDuration += timeGap;
//				}
//
//				//Dextrose Duration
//				if((!BasicUtils.isEmpty(obj.getDextroseVolumemlperday()) || !BasicUtils.isEmpty(obj.getReadymadeTotalFluidVolume())) && !dextroseActive) {
//					dextroseActive = true;
//					dextroseStartDate = obj.getEntryDateTime();
//
//				}else if((BasicUtils.isEmpty(obj.getDextroseVolumemlperday()) && BasicUtils.isEmpty(obj.getReadymadeTotalFluidVolume())) && dextroseActive) {
//					dextroseActive = false;
//					dextroseEndDate = obj.getEntryDateTime();
//					long timeGap = (dextroseEndDate.getTime() - dextroseStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					dextroseDuration += timeGap;
//				}
//			}
//			if(enActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - enStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					enDuration += timeGap;
//			}
//			if(ebmActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - ebmStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					ebmDuration += timeGap;
//			}
//			if(pnActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - pnStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					pnDuration += timeGap;
//			}
//			if(aminoActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - aminoStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					aminoDuration += timeGap;
//			}
//			if(lipidActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - lipidStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					lipidDuration += timeGap;
//			}
//			if(pnAdditivesActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - pnAdditivesActiveStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					pnAdditivesDuration += timeGap;
//			}
//			if(dextroseActive) {
//				long timeGap = (babyObj.getDischargeddate().getTime() - dextroseStartDate.getTime()) / (24 * 60 * 60 * 1000);
//				if(timeGap > 0)
//					dextroseDuration += timeGap;
//			}
//
//			if(enDuration != 0)
//				returnObj.setEnteral_Duration((int)(enDuration));
//
//			if(calciumENActive) {
//				returnObj.setCalcium_EN_Additive(1);
//			}
//
//			if(ironENActive)
//				returnObj.setIron_EN_Additive(1);
//
//
//			if(vitaminaENActive)
//				returnObj.setVitaminA_EN_Additive(1);
//
//
//			if(vitamindENActive)
//				returnObj.setVitaminD_EN_Additive(1);
//
//
//			if(mctENActive)
//				returnObj.setMCT_Oil_Additive(1);
//
//
//			if(ebmDuration != 0)
//				returnObj.setEBM_Duration((int)ebmDuration);
//
//			if(pnDuration != 0)
//				returnObj.setParenteral_Duration((int)pnDuration);
//
//			if(bolusCount != 0)
//				returnObj.setBolus_Count((int)bolusCount);
//
//			if(aminoDuration != 0)
//				returnObj.setAmino_Acid_Duration((int)aminoDuration);
//
//			if(lipidDuration != 0)
//				returnObj.setLipid_Duration((int)lipidDuration);
//
//			if(pnAdditivesDuration != 0)
//				returnObj.setPN_Additives_Duration((int)pnAdditivesDuration);
//
//			if(dextroseDuration != 0)
//				returnObj.setDextrose_Duration((int)dextroseDuration);

			//Medications
			List<String> medTypes = new ArrayList<>();
			medTypes.add("TYPE0001");
			medTypes.add("TYPE0015");
			medTypes.add("TYPE0009");
			medTypes.add("TYPE0007");
			medTypes.add("TYPE0004");
//			for(String medType : medTypes) {
//				Timestamp medStartDate = null;
//				Timestamp medEndDate = null;
//				Timestamp medRightStartDate = null;
//				Timestamp medRightEndDate = null;
//				String prescriptionQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + babyObj.getUhid() + "' and medicationtype = '" + medType +"' order by startdate asc";
//				List<BabyPrescription> prescriptionList = prescriptionDao.getListFromMappedObjNativeQuery(prescriptionQuery);
//				for(BabyPrescription obj : prescriptionList) {
//					if(!BasicUtils.isEmpty(obj.getEnddate()) && obj.getEnddate().getTime() > obj.getMedicineOrderDate().getTime()) {
//						if(medStartDate == null) {
//							medStartDate = obj.getMedicineOrderDate();
//							medEndDate = obj.getEnddate();
//						}else {
//							if(medRightStartDate == null) {
//								if(obj.getMedicineOrderDate().getTime() > medEndDate.getTime() && obj.getEnddate().getTime() > medEndDate.getTime()) {
//									medRightStartDate = obj.getMedicineOrderDate();
//									medRightEndDate = obj.getEnddate();
//								}else if(obj.getMedicineOrderDate().getTime() > medStartDate.getTime() && obj.getMedicineOrderDate().getTime() < medEndDate.getTime() && obj.getEnddate().getTime() > medEndDate.getTime()) {
//									medEndDate = obj.getEnddate();
//								}else if(obj.getMedicineOrderDate().getTime() < medStartDate.getTime() && obj.getEnddate().getTime() < medEndDate.getTime() && obj.getEnddate().getTime() > medStartDate.getTime()) {
//									medStartDate = obj.getMedicineOrderDate();
//								}else if(obj.getMedicineOrderDate().getTime() < medStartDate.getTime() && obj.getEnddate().getTime() > medEndDate.getTime()) {
//									medStartDate = obj.getMedicineOrderDate();
//									medEndDate = obj.getEnddate();
//								}
//							}else if(medRightStartDate != null) {
//								if(obj.getMedicineOrderDate().getTime() < medStartDate.getTime() && obj.getEnddate().getTime() < medEndDate.getTime() && obj.getEnddate().getTime() > medStartDate.getTime()) {
//									medStartDate = obj.getMedicineOrderDate();
//								}else if(obj.getMedicineOrderDate().getTime() > medEndDate.getTime() && obj.getMedicineOrderDate().getTime() < medRightStartDate.getTime() && obj.getEnddate().getTime() > medRightEndDate.getTime()) {
//									medRightStartDate = obj.getMedicineOrderDate();
//									medRightEndDate = obj.getEnddate();
//								}else if(obj.getMedicineOrderDate().getTime() < medStartDate.getTime() && obj.getEnddate().getTime() > medEndDate.getTime() && obj.getEnddate().getTime() < medRightStartDate.getTime()) {
//									medStartDate = obj.getMedicineOrderDate();
//									medEndDate = obj.getEnddate();
//								}else if(obj.getMedicineOrderDate().getTime() > medRightStartDate.getTime() && obj.getMedicineOrderDate().getTime() < medRightStartDate.getTime() && obj.getEnddate().getTime() > medRightEndDate.getTime()) {
//									medRightEndDate = obj.getEnddate();
//								}if((obj.getMedicineOrderDate().getTime() < medStartDate.getTime() && obj.getEnddate().getTime() > medEndDate.getTime()) || (obj.getMedicineOrderDate().getTime() > medStartDate.getTime() && obj.getMedicineOrderDate().getTime() < medEndDate.getTime() && obj.getEnddate().getTime() > medRightStartDate.getTime() && obj.getEnddate().getTime() < medRightEndDate.getTime())
//										|| (obj.getMedicineOrderDate().getTime() > medStartDate.getTime() && obj.getMedicineOrderDate().getTime() < medEndDate.getTime() && obj.getEnddate().getTime() > medRightEndDate.getTime())
//										||  (obj.getMedicineOrderDate().getTime() < medStartDate.getTime() && obj.getEnddate().getTime() > medRightStartDate.getTime() && obj.getEnddate().getTime() < medRightEndDate.getTime())) {
//									medStartDate = obj.getMedicineOrderDate();
//									medEndDate = obj.getEnddate();
//									medRightStartDate = null;
//									medRightEndDate = null;
//								}
//							}
//
//						}
//					}
//				}
//				int duration = 0;
//				if(medStartDate != null) {
//					long timeGap = (medEndDate.getTime() - medStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					duration += timeGap;
//				}
//				if(medRightStartDate != null) {
//					long timeGap = (medRightEndDate.getTime() - medRightStartDate.getTime()) / (24 * 60 * 60 * 1000);
//					duration += timeGap;
//				}
//				if(medType.equalsIgnoreCase("TYPE0001") && duration != 0) {
//					returnObj.setAntibiotics(duration);
//				}
//				else if(medType.equalsIgnoreCase("TYPE0015") && duration != 0) {
//					returnObj.setAnalgesic(duration);
//				}
//				else if(medType.equalsIgnoreCase("TYPE0009") && duration != 0) {
//					returnObj.setPulmonary_Vasodilators(duration);
//				}
//				else if(medType.equalsIgnoreCase("TYPE0007") && duration != 0) {
//					returnObj.setSedative(duration);
//				}
//				else if(medType.equalsIgnoreCase("TYPE0004") && duration != 0) {
//					returnObj.setInotropes(duration);
//				}
//			}

			//Blood Product
			List<DoctorBloodProducts> bloodProductList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getDoctorBloodProductList(babyObj.getUhid()));
			if(!BasicUtils.isEmpty(bloodProductList)) {
				returnObj.setBlood_Product(bloodProductList.size());
			}

			List<JaundiceReportJSON> jaundiceList = getJaundiceReportList(uhid);
			if (!BasicUtils.isEmpty(jaundiceList)) {
				returnObj.setIs_jaundice(true);
				returnObj.setNo_jaundice(jaundiceList.size());
				returnObj.setJaundiceList(jaundiceList);
			}

			List<RdsReportJSON> rdsList = getRdsReportList(uhid);
			if (!BasicUtils.isEmpty(rdsList)) {
				returnObj.setIs_rds(true);
				returnObj.setNo_rds(rdsList.size());
				returnObj.setRdsList(rdsList);
			}

			List<SepsisReportJSON> sepsisList = getSepsisReportList(uhid);
			if (!BasicUtils.isEmpty(sepsisList)) {
				returnObj.setIs_sepsis(true);
				returnObj.setNo_sepsis(sepsisList.size());
				returnObj.setSepsisList(sepsisList);
			}

			List<PPHNReportJSON> pphnList = getPPHNReportList(uhid);
			if (!BasicUtils.isEmpty(pphnList)) {
				returnObj.setIs_pphn(true);
				returnObj.setNo_pphn(pphnList.size());
				returnObj.setPphnList(pphnList);
			}

			List<PneumothoraxReportJSON> pneumothoraxList = getPneumothoraxReportList(uhid);
			if (!BasicUtils.isEmpty(pneumothoraxList)) {
				returnObj.setIs_pneumothorax(true);
				returnObj.setNo_pneumothorax(pneumothoraxList.size());
				returnObj.setPneumothoraxList(pneumothoraxList);
			}

			List<SeizuresReportJSON> seizuresList = getSeizuresReportList(uhid);
			if (!BasicUtils.isEmpty(seizuresList)) {
				returnObj.setIs_seizures(true);
				returnObj.setNo_seizures(seizuresList.size());
				returnObj.setSeizuresList(seizuresList);
			}

			List<AsphyxiaReportJSON> asphyxiaList = getAsphyxiaReportList(uhid);
			if (!BasicUtils.isEmpty(asphyxiaList)) {
				returnObj.setIs_asphyxia(true);
				returnObj.setNo_asphyxia(asphyxiaList.size());
				returnObj.setAsphyxiaList(asphyxiaList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<NutritionalCompliancePOJO> getNutritionalComplianceData(String branchName) {
		List<NutritionalCompliancePOJO> returnList = new ArrayList<NutritionalCompliancePOJO>();
		try {

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			List<BabyDetail> uhidCompleteList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getActiveBabies(branchName));

			if (!BasicUtils.isEmpty(uhidCompleteList)) {

				for (BabyDetail obj : uhidCompleteList) {
					Timestamp dateofbirth = null;

					NutritionalCompliancePOJO nutritionObj = new NutritionalCompliancePOJO();

					nutritionObj.setUhid(obj.getUhid());
					if(!BasicUtils.isEmpty(obj.getGender()))
						nutritionObj.setGender(obj.getGender());
					if(!BasicUtils.isEmpty(obj.getGestationdaysbylmp()))
						nutritionObj.setGestationdaysbylmp(obj.getGestationdaysbylmp());
					if(!BasicUtils.isEmpty(obj.getGestationdaysbylmp()))
						nutritionObj.setGestationweekbylmp(obj.getGestationweekbylmp());
					
					if(!BasicUtils.isEmpty(obj.getBabyNumber())) {
						String fullBabyName = obj.getBabyname() + " " + obj.getBabyNumber();
						nutritionObj.setBabyname(fullBabyName);
					}else {
						nutritionObj.setBabyname(obj.getBabyname());
					}
					
					if(!BasicUtils.isEmpty(obj.getMonoamniotic())){
						if(obj.getMonoamniotic()) {
							nutritionObj.setMono_twins("Yes");
						}
					}
					
					String query = "SELECT obj FROM NursingBloodGas as obj where uhid='" + obj.getUhid() + "' order by entrydate asc";
					List<NursingBloodGas> bloodGasList = prescriptionDao.getListFromMappedObjNativeQuery(query);
					if(!BasicUtils.isEmpty(bloodGasList)) {
						if(!BasicUtils.isEmpty(bloodGasList.get(0).getPh())) {
							nutritionObj.setPh(bloodGasList.get(0).getPh());
						}
						if(!BasicUtils.isEmpty(bloodGasList.get(0).getBe())) {
							nutritionObj.setBe(bloodGasList.get(0).getBe());
						}
					}
					
					query = "SELECT obj FROM BirthToNicu as obj where uhid='" + obj.getUhid() + "'";
					List<BirthToNicu> birthList = prescriptionDao.getListFromMappedObjNativeQuery(query);
					if(!BasicUtils.isEmpty(birthList)) {
						if(!BasicUtils.isEmpty(birthList.get(0).getResuscitationChesttubeCompressionDuration())) {
							if(!BasicUtils.isEmpty(birthList.get(0).getChestCompTime())) {
								String finalStr = birthList.get(0).getResuscitationChesttubeCompressionDuration() + " " + birthList.get(0).getChestCompTime();
								nutritionObj.setChest_compression(finalStr);

							}else {
								nutritionObj.setChest_compression(birthList.get(0).getResuscitationChesttubeCompressionDuration());
							}
						}
					}
					
					query = "SELECT obj FROM SaFeedIntolerance as obj where uhid='" + obj.getUhid() + "'";
					List<SaFeedIntolerance> intoleranceList = prescriptionDao.getListFromMappedObjNativeQuery(query);
					if(!BasicUtils.isEmpty(intoleranceList)) {
						nutritionObj.setFeed_intolerance("Yes");
					}
					
					query = "SELECT obj FROM AntenatalHistoryDetail as obj where uhid='" + obj.getUhid() + "'";
					List<AntenatalHistoryDetail> historyList = prescriptionDao.getListFromMappedObjNativeQuery(query);
					if(!BasicUtils.isEmpty(historyList)) {
						if(!BasicUtils.isEmpty(historyList.get(0).getIugr())) {
							if(historyList.get(0).getIugr()) {
								nutritionObj.setIugr("Yes");
							}
						}
					}
					
					query = "SELECT obj FROM BabyPrescription as obj where uhid='" + obj.getUhid() + "' and medicationtype='TYPE0004'";
					List<BabyPrescription> prescription = prescriptionDao.getListFromMappedObjNativeQuery(query);
					if(!BasicUtils.isEmpty(prescription)) {
						nutritionObj.setPressors("Yes");				
					}
					
					
					Timestamp dateofadmission = null;
					try {
						dateofadmission = new Timestamp(obj.getDateofadmission().getTime() - offset);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try{
						int hours = Integer.parseInt(obj.getTimeofadmission().substring(0, 2));
						int minutes = Integer.parseInt(obj.getTimeofadmission().substring(3, 5));
						if (obj.getTimeofadmission().substring(6, 8).equals("PM") && hours != 12) {
							hours = hours + 12;
						}

						dateofadmission.setHours(hours);
						dateofadmission.setMinutes(minutes);
						dateofadmission.setSeconds(0);
						
						nutritionObj.setDateofadmission(dateofadmission);
					}catch(NumberFormatException ex){ // handle your exception
						System.out.println(ex);
					}
					
					
					try {
						System.out.println(obj.getDateofbirth());
						System.out.println(offset);
						System.out.println(obj.getDateofbirth().getTime());
						dateofbirth = new Timestamp(obj.getDateofbirth().getTime() - offset);
						System.out.println(dateofbirth);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try{
						int hours = Integer.parseInt(obj.getTimeofbirth().substring(0, 2));
						int minutes = Integer.parseInt(obj.getTimeofbirth().substring(3, 5));
						if (obj.getTimeofbirth().substring(6, 8).equals("PM") && hours != 12) {
							hours = hours + 12;
						}

						dateofbirth.setHours(hours);
						dateofbirth.setMinutes(minutes);
						dateofbirth.setSeconds(0);
						
						nutritionObj.setDateofbirth(dateofbirth);
					}catch(NumberFormatException ex){ // handle your exception
						System.out.println(ex);
					}
					
					List<NutritionalCompliance> nutritionList = patientDao.getListFromMappedObjNativeQuery(
							"SELECT s FROM NutritionalCompliance s where uhid='" + obj.getUhid() + "' order by dol");
					
					List<BabyfeedDetail> feedList = inicuDao.getListFromMappedObjQuery(
							"SELECT s FROM BabyfeedDetail s where uhid='" + obj.getUhid() + "' order by entrydatetime");
					
//					HashMap<Integer,String> dolMappingEN = new HashMap<Integer,String>();
					HashMap<Integer,List<Float>> dolMappingProtein = new HashMap();
					HashMap<Integer,List<Float>> dolMappingGIR = new HashMap();
					HashMap<Integer,List<Float>> dolMappingSodium = new HashMap();
					HashMap<Integer,List<Float>> dolMappingPotassium = new HashMap();
					HashMap<Integer,List<Float>> dolMappingCalcium = new HashMap();
					for(BabyfeedDetail feed: feedList) {
						long dol = ((feed.getEntryDateTime().getTime() - dateofbirth.getTime()) / (1000 * 60 * 60 * 24));
						int finalDol = (int) dol;
						if(finalDol < 0) {
							finalDol = 0;
						}
						System.out.println("*************************************************");
						System.out.println(obj.getUhid());
						System.out.println(feed.getEntryDateTime());
						System.out.println(dateofbirth);
						System.out.println(finalDol);
						
						
//						if(!BasicUtils.isEmpty(feed.getTotalfeedMlPerKgDay()) && feed.getTotalfeedMlPerKgDay() > 0){
//							
//							if(BasicUtils.isEmpty(dolMappingEN.get(finalDol))) {
//								dolMappingEN.put(finalDol, feed.getTotalfeedMlPerKgDay().toString());
//							}else {
//								String feedValue = dolMappingEN.get(finalDol);
//								if(!feedValue.contains(feed.getTotalfeedMlPerKgDay().toString())) {
//									feedValue = feedValue + ", " + feed.getTotalfeedMlPerKgDay().toString();
//									dolMappingEN.put(finalDol, feedValue);
//								}
//							}
//							
//						}else {
//							if(BasicUtils.isEmpty(dolMappingEN.get(finalDol))) {
//								dolMappingEN.put(finalDol, "NPO");
//							}else {
//								
//								String feedValue = dolMappingEN.get(finalDol);
//								if(!feedValue.contains("NPO")) {
//									feedValue = feedValue + ", " + "NPO";
//									dolMappingEN.put(finalDol, feedValue);
//								}
//							}
//						}
						if(!BasicUtils.isEmpty(feed.getAminoacidConc())) {
							List<Float> feedAminoList = new ArrayList();
							if(BasicUtils.isEmpty(dolMappingProtein.get(finalDol))) {
								feedAminoList.add(feed.getAminoacidConc());
								dolMappingProtein.put(finalDol, feedAminoList);
							}else {
								feedAminoList = dolMappingProtein.get(finalDol);
								if(!feedAminoList.contains(feed.getGirvalue())) {
									feedAminoList.add(feed.getAminoacidConc());
									dolMappingProtein.put(finalDol, feedAminoList);
								}
							}
						}
						if(!BasicUtils.isEmpty(feed.getSodiumMeg()) && feed.getSodiumMeg() > 0) {
							List<Float> feedSodiumList = new ArrayList();
							if(BasicUtils.isEmpty(dolMappingSodium.get(finalDol))) {
								feedSodiumList.add(feed.getSodiumMeg());
								dolMappingSodium.put(finalDol, feedSodiumList);
							}else {
								feedSodiumList = dolMappingSodium.get(finalDol);
								if(!feedSodiumList.contains(feed.getSodiumMeg())) {
									feedSodiumList.add(feed.getSodiumMeg());
									dolMappingSodium.put(finalDol, feedSodiumList);
								}
							}
						}
						if(!BasicUtils.isEmpty(feed.getPotassiumVolume()) && feed.getPotassiumVolume() > 0) {
							List<Float> feedPotassiumList = new ArrayList();
							if(BasicUtils.isEmpty(dolMappingPotassium.get(finalDol))) {
								feedPotassiumList.add(feed.getPotassiumVolume());
								dolMappingPotassium.put(finalDol, feedPotassiumList);
							}else {
								feedPotassiumList = dolMappingPotassium.get(finalDol);
								if(!feedPotassiumList.contains(feed.getPotassiumVolume())) {
									feedPotassiumList.add(feed.getPotassiumVolume());
									dolMappingPotassium.put(finalDol, feedPotassiumList);
								}
							}
						}
						if(!BasicUtils.isEmpty(feed.getCalciumVolume()) && feed.getCalciumVolume() > 0) {
							List<Float> feedCalciumList = new ArrayList();
							if(BasicUtils.isEmpty(dolMappingCalcium.get(finalDol))) {
								feedCalciumList.add(feed.getCalciumVolume());
								dolMappingCalcium.put(finalDol, feedCalciumList);
							}else {
								feedCalciumList = dolMappingCalcium.get(finalDol);
								if(!feedCalciumList.contains(feed.getCalciumVolume())) {
									feedCalciumList.add(feed.getCalciumVolume());
									dolMappingCalcium.put(finalDol, feedCalciumList);
								}
							}
						}
						if(!BasicUtils.isEmpty(feed.getGirvalue())) {
							
							List<Float> feedGirList = new ArrayList();
							if(BasicUtils.isEmpty(dolMappingGIR.get(finalDol))) {
								feedGirList.add(Float.parseFloat(feed.getGirvalue()));
								dolMappingGIR.put(finalDol, feedGirList);
							}else {
								feedGirList = dolMappingGIR.get(finalDol);
								if(!feedGirList.contains(feed.getGirvalue())) {
									feedGirList.add(Float.parseFloat(feed.getGirvalue()));
									dolMappingGIR.put(finalDol, feedGirList);
								}
							}
						}
					}
					List<NutritionalCompliance> nutritionListFinal = new ArrayList<NutritionalCompliance>();
					for(NutritionalCompliance nutrition : nutritionList) {
						Integer dol = nutrition.getDol();
//						if(!BasicUtils.isEmpty(dolMappingEN.get(dol))) {
//							String feedValue = dolMappingEN.get(dol);
//							nutrition.setGiven_feed(feedValue);	
//						}
						if(!BasicUtils.isEmpty(dolMappingGIR.get(dol))) {
							List<Float> feedGirList = dolMappingGIR.get(dol);
							float girValue = 0;
							for(float fl : feedGirList) {
								girValue += fl;
							}
							
							nutrition.setGiven_cho(String.valueOf(Math.round(girValue/feedGirList.size()*100)/100));	
						}
						if(!BasicUtils.isEmpty(dolMappingProtein.get(dol))) {
							List<Float> feedProteinList = dolMappingProtein.get(dol);
							float proteinValue = 0;
							for(float fl : feedProteinList) {
								proteinValue += fl;
							}
							nutrition.setGiven_protein(String.valueOf(Math.round(proteinValue/feedProteinList.size()*100)/100));	
						}
						if(!BasicUtils.isEmpty(dolMappingSodium.get(dol))) {
							List<Float> feedSodiumList = dolMappingSodium.get(dol);
							float sodiumValue = 0;
							for(float fl : feedSodiumList) {
								sodiumValue += fl;
							}
							nutrition.setGiven_sodium(String.valueOf(Math.round(sodiumValue/feedSodiumList.size()*100)/100));	
						}
						if(!BasicUtils.isEmpty(dolMappingPotassium.get(dol))) {
							List<Float> feedPotassiumList = dolMappingPotassium.get(dol);
							float potassiumValue = 0;
							for(float fl : feedPotassiumList) {
								potassiumValue += fl;
							}
							nutrition.setGiven_potassium(String.valueOf(Math.round(potassiumValue/feedPotassiumList.size()*100)/100));	
						}
						if(!BasicUtils.isEmpty(dolMappingCalcium.get(dol))) {
							List<Float> feedCalciumList = dolMappingCalcium.get(dol);
							float calciumValue = 0;
							for(float fl : feedCalciumList) {
								calciumValue += fl;
							}
							nutrition.setGiven_calcium(String.valueOf(Math.round(calciumValue/feedCalciumList.size()*100)/100));	
						}
						if(!BasicUtils.isEmpty(nutrition.getFinal_energy()) && nutrition.getFinal_energy() > 0 && nutrition.getFinal_energy() != nutrition.getInitial_energy()) {
							nutrition.setEnergy_volume_range(nutrition.getInitial_energy() + "-" + nutrition.getFinal_energy().toString());
						}else {
							nutrition.setEnergy_volume_range(nutrition.getInitial_energy().toString());
						}
						if(!BasicUtils.isEmpty(nutrition.getSodium_upper_pn_feed_volume()) && nutrition.getSodium_upper_pn_feed_volume() > 0 && nutrition.getSodium_upper_pn_feed_volume() != nutrition.getSodium_lower_pn_feed_volume()) {
							nutrition.setSodium_volume_range(nutrition.getSodium_lower_pn_feed_volume() + "-" + nutrition.getSodium_upper_pn_feed_volume().toString());
						}else {
							nutrition.setSodium_volume_range(nutrition.getSodium_lower_pn_feed_volume().toString());
						}
						if(!BasicUtils.isEmpty(nutrition.getUpper_feed_volume()) && nutrition.getUpper_feed_volume() > 0 && nutrition.getUpper_feed_volume() != nutrition.getFeed_volume()) {
							nutrition.setFeed_volume_range(nutrition.getFeed_volume() + "-" + nutrition.getUpper_feed_volume().toString());
						}else {
							nutrition.setFeed_volume_range(nutrition.getFeed_volume().toString());
						}
						if(!BasicUtils.isEmpty(nutrition.getUpper_feed_advancement()) && nutrition.getUpper_feed_advancement() > 0 && nutrition.getUpper_feed_advancement() != (nutrition.getFeed_advancement())) {
							nutrition.setFeed_advancement_range(nutrition.getFeed_advancement() + "-" + nutrition.getUpper_feed_advancement().toString());
						}else {
							nutrition.setFeed_advancement_range(nutrition.getFeed_advancement().toString());
						}
						
						if(!BasicUtils.isEmpty(nutrition.getCho_upper_pn_feed_volume()) && nutrition.getCho_upper_pn_feed_volume() > 0 && nutrition.getCho_upper_pn_feed_volume() != (nutrition.getCho_lower_pn_feed_volume())) {
							nutrition.setCho_volume_range(nutrition.getCho_lower_pn_feed_volume() + "-" + nutrition.getCho_upper_pn_feed_volume().toString());
						}else {
							nutrition.setCho_volume_range(nutrition.getCho_lower_pn_feed_volume().toString());
						}
						if(!BasicUtils.isEmpty(nutrition.getCho_upper_pn_feed_advancement()) && nutrition.getCho_upper_pn_feed_advancement() > 0 && nutrition.getCho_upper_pn_feed_advancement() != (nutrition.getCho_lower_pn_feed_advancement())) {
							nutrition.setCho_advancement_range(nutrition.getCho_lower_pn_feed_advancement() + "-" + nutrition.getCho_upper_pn_feed_advancement().toString());
						}else {
							nutrition.setCho_advancement_range(nutrition.getCho_lower_pn_feed_advancement().toString());
						}
						
						if(!BasicUtils.isEmpty(nutrition.getProtein_upper_pn_feed_volume()) && nutrition.getProtein_upper_pn_feed_volume() > 0 && nutrition.getProtein_upper_pn_feed_volume() != (nutrition.getProtein_lower_pn_feed_volume())) {
							nutrition.setProtein_volume_range(nutrition.getProtein_lower_pn_feed_volume() + "-" + nutrition.getProtein_upper_pn_feed_volume().toString());
						}else {
							nutrition.setProtein_volume_range(nutrition.getProtein_lower_pn_feed_volume().toString());
						}
						if(!BasicUtils.isEmpty(nutrition.getProtein_upper_pn_feed_advancement()) && nutrition.getProtein_upper_pn_feed_advancement() > 0 && nutrition.getProtein_upper_pn_feed_advancement() != (nutrition.getProtein_lower_pn_feed_advancement())) {
							nutrition.setProtein_advancement_range(nutrition.getProtein_lower_pn_feed_advancement() + "-" + nutrition.getProtein_upper_pn_feed_advancement().toString());
						}else {
							nutrition.setProtein_advancement_range(nutrition.getProtein_lower_pn_feed_advancement().toString());
						}
						nutritionListFinal.add(nutrition);
						
					}
					
					nutritionObj.setComplianceData(nutritionListFinal);
					
					returnList.add(nutritionObj);
					
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Nutritional Compliance", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<AsphyxiaReportJSON> getAsphyxiaReportList(String uhid) {
		List<AsphyxiaReportJSON> returnList = new ArrayList<AsphyxiaReportJSON>();
		try {
			AsphyxiaReportJSON asphyxiaObj = new AsphyxiaReportJSON();
			List<SaCnsAsphyxia> asphyxiaList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaCnsAsphyxia s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(asphyxiaList)) {
				long startTime = 0;
				long endTime = 0;


				Iterator<SaCnsAsphyxia> itr = asphyxiaList.iterator();
				while (itr.hasNext()) {
					SaCnsAsphyxia obj = itr.next();
					if (obj.getEventstatus() == null) {
						continue;
					} else if (obj.getEventstatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							asphyxiaObj = new AsphyxiaReportJSON();
							startTime = obj.getAssessmentTime().getTime();
							if (obj.getAgeinhoursdays() == null || obj.getAgeinhoursdays()) {
								asphyxiaObj.setAsphyxia_onset(Integer.parseInt(obj.getAgeatonset()));
							} else {
								asphyxiaObj.setAsphyxia_onset(Integer.parseInt(obj.getAgeatonset()) * 24);
							}
						}

					} else if (obj.getEventstatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						asphyxiaObj.setAsphyxia_duration(((Long) durationDays).intValue());
						returnList.add(asphyxiaObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						asphyxiaObj.setAsphyxia_duration(((Long) durationDays).intValue());
					}
					returnList.add(asphyxiaObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<SeizuresReportJSON> getSeizuresReportList(String uhid) {
		List<SeizuresReportJSON> returnList = new ArrayList<SeizuresReportJSON>();
		try {
			SeizuresReportJSON seizuresObj = new SeizuresReportJSON();
			List<SaCnsSeizures> seizuresList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaCnsSeizures s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(seizuresList)) {
				long startTime = 0;
				long endTime = 0;


				Iterator<SaCnsSeizures> itr = seizuresList.iterator();
				while (itr.hasNext()) {
					SaCnsSeizures obj = itr.next();
					if (obj.getEventstatus() == null) {
						continue;
					} else if (obj.getEventstatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							seizuresObj = new SeizuresReportJSON();
							startTime = obj.getAssessmentTime().getTime();
							if (obj.getAgeinhoursdays() == null || obj.getAgeinhoursdays()) {
								seizuresObj.setSeizures_onset(Integer.parseInt(obj.getAgeatonset()));
							} else {
								int s = Integer.parseInt(obj.getAgeatonset()) * 24;
								seizuresObj.setSeizures_onset(Integer.parseInt(obj.getAgeatonset()) * 24);
							}
						}

					} else if (obj.getEventstatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						seizuresObj.setSeizures_duration(((Long) durationDays).intValue());
						returnList.add(seizuresObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						seizuresObj.setSeizures_duration(((Long) durationDays).intValue());
					}
					returnList.add(seizuresObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<PneumothoraxReportJSON> getPneumothoraxReportList(String uhid) {
		List<PneumothoraxReportJSON> returnList = new ArrayList<PneumothoraxReportJSON>();
		try {
			PneumothoraxReportJSON pneumoObj = new PneumothoraxReportJSON();
			List<SaRespPneumo> pneumoDbList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaRespPneumo s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(pneumoDbList)) {
				long startTime = 0;
				long endTime = 0;


				Iterator<SaRespPneumo> itr = pneumoDbList.iterator();
				while (itr.hasNext()) {
					SaRespPneumo obj = itr.next();
					if (obj.getEventstatus() == null) {
						continue;
					} else if (obj.getEventstatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							pneumoObj = new PneumothoraxReportJSON();
							startTime = obj.getAssessmentTime().getTime();
							if (obj.getAgeinhoursdays() == null || obj.getAgeinhoursdays()) {
								pneumoObj.setPneumothorax_onset(Integer.parseInt(obj.getAgeatonset()));
							} else {
								int s = Integer.parseInt(obj.getAgeatonset()) * 24;
								pneumoObj.setPneumothorax_onset(Integer.parseInt(obj.getAgeatonset()) * 24);
							}
						}

					} else if (obj.getEventstatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						pneumoObj.setPneumothorax_duration(((Long) durationDays).intValue());
						returnList.add(pneumoObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						pneumoObj.setPneumothorax_duration(((Long) durationDays).intValue());
					}
					returnList.add(pneumoObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<PPHNReportJSON> getPPHNReportList(String uhid) {
		List<PPHNReportJSON> returnList = new ArrayList<PPHNReportJSON>();
		try {
			PPHNReportJSON pphnObj = new PPHNReportJSON();
			List<SaRespPphn> pphnDbList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaRespPphn s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(pphnDbList)) {
				long startTime = 0;
				long endTime = 0;


				Iterator<SaRespPphn> itr = pphnDbList.iterator();
				while (itr.hasNext()) {
					SaRespPphn obj = itr.next();
					if (obj.getEventstatus() == null) {
						continue;
					} else if (obj.getEventstatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							pphnObj = new PPHNReportJSON();
							startTime = obj.getAssessmentTime().getTime();
							if (obj.getAgeinhoursdays() == null || obj.getAgeinhoursdays()) {
								pphnObj.setPphn_onset(Integer.parseInt(obj.getAgeatonset()));
							} else {
								int s = Integer.parseInt(obj.getAgeatonset()) * 24;
								pphnObj.setPphn_onset(Integer.parseInt(obj.getAgeatonset()) * 24);
							}
						}

					} else if (obj.getEventstatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						pphnObj.setPphn_duration(((Long) durationDays).intValue());
						returnList.add(pphnObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						pphnObj.setPphn_duration(((Long) durationDays).intValue());
					}
					returnList.add(pphnObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<JaundiceReportJSON> getJaundiceReportList(String uhid) {
		List<JaundiceReportJSON> returnList = new ArrayList<JaundiceReportJSON>();
		try {
			JaundiceReportJSON jaundiceObj = new JaundiceReportJSON();
			List<SaJaundice> jaundiceDbList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaJaundice s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(jaundiceDbList)) {
				long startTime = 0;
				long endTime = 0;
				long photoStartTime = 0;
				long phototherapyDuration = 0;
				HashSet<String> causeSet = null;

				Iterator<SaJaundice> itr = jaundiceDbList.iterator();
				while (itr.hasNext()) {
					SaJaundice obj = itr.next();
					if (obj.getJaundicestatus() == null) {
						continue;
					} else if (obj.getJaundicestatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							causeSet = new HashSet<String>();
							jaundiceObj = new JaundiceReportJSON();
							startTime = obj.getAssessmentTime().getTime();
							if (obj.getIsageofonsetinhours() == null || obj.getIsageofonsetinhours()) {
								jaundiceObj.setJaundice_onset(obj.getAgeofonset());
							} else {
								jaundiceObj.setJaundice_onset(obj.getAgeofonset() * 24);
							}
						}

						if (obj.getActiontype() != null) {
							if (obj.getActiontype().contains("TRE001") && obj.getPhototherapyvalue() != null) {
								if (obj.getPhototherapyvalue().equalsIgnoreCase("start") && photoStartTime == 0) {
									jaundiceObj.setJaundice_phototherapy(true);
									photoStartTime = obj.getAssessmentTime().getTime();
								} else if (obj.getPhototherapyvalue().equalsIgnoreCase("stop") && photoStartTime > 0) {
									long photoStopTime = obj.getAssessmentTime().getTime();
									phototherapyDuration += ((photoStopTime - photoStartTime) / (60 * 60 * 1000)) + 1;
									photoStartTime = 0;
								}
							}

							if (obj.getActiontype().contains("TRE002")) {
								jaundiceObj.setJaundice_exchange(true);
							}
						}

						if (obj.getCauseofjaundice() != null) {
							String cause = obj.getCauseofjaundice();
							List<String> list = Arrays.asList(cause.substring(1, cause.length() - 1).split(", "));
							Iterator<String> itrCause = list.iterator();
							while (itrCause.hasNext()) {
								String causeValue = itrCause.next();
								causeSet.add(this.jaundiceCauseMap.get(causeValue));
							}
							String causeStr = causeSet.toString();
							causeStr = causeStr.substring(1, causeStr.length() - 1);
							while (causeStr.contains(",")) {
								causeStr = causeStr.replace(",", ";");
							}
							jaundiceObj.setJaundice_cause(causeStr);
						}
					} else if (obj.getJaundicestatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						jaundiceObj.setJaundice_duration(((Long) durationDays).intValue());
						jaundiceObj.setPhototherapy_duration_hrs(((Long) phototherapyDuration).intValue());
						returnList.add(jaundiceObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						jaundiceObj.setJaundice_duration(((Long) durationDays).intValue());
					}
					jaundiceObj.setPhototherapy_duration_hrs(((Long) phototherapyDuration).intValue());
					returnList.add(jaundiceObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<RdsReportJSON> getRdsReportList(String uhid) {
		List<RdsReportJSON> returnList = new ArrayList<RdsReportJSON>();
		try {
			RdsReportJSON rdsObj = new RdsReportJSON();
			List<SaRespRds> rdsDbList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaRespRds s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(rdsDbList)) {
				long startTime = 0;
				long endTime = 0;
				Integer maxDownes = 0;
				Integer antibioticCount = 0;
				Integer antibioticDuration = 0;
				HashSet<String> causeSet = null;

				Iterator<SaRespRds> itr = rdsDbList.iterator();
				while (itr.hasNext()) {
					SaRespRds obj = itr.next();
					if (obj.getEventstatus() == null) {
						continue;
					} else if (obj.getEventstatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							causeSet = new HashSet<String>();
							rdsObj = new RdsReportJSON();
							antibioticCount = 0;
							antibioticDuration = 0;

							startTime = obj.getAssessmentTime().getTime();
							if (obj.getAgeinhoursdays() == null || obj.getAgeinhoursdays()) {
								rdsObj.setRds_onset(Integer.parseInt(obj.getAgeatonset()));
							} else {
								rdsObj.setRds_onset(Integer.parseInt(obj.getAgeatonset()) * 24);
							}
						}

						if (obj.getTreatmentaction() != null) {
							if (obj.getTreatmentaction().contains("TRE004") && obj.getSufactantname() != null) {
								if (!rdsObj.getRds_surfactant()) {
									rdsObj.setRds_surfactant(true);
									rdsObj.setAge_at_surfactant(obj.getAgeatassesment());
									rdsObj.setSurfactant_type(obj.getSufactantname());
									rdsObj.setSurfactant_dose(1);
								} else {
									rdsObj.setSurfactant_dose(rdsObj.getSurfactant_dose() + 1);
								}
							}

							if (obj.getTreatmentaction().contains("TRE005")) {
								List<VwRespiratoryUsageFinal> respSupportList = inicuDao.getListFromMappedObjQuery(
										"SELECT s FROM VwRespiratoryUsageFinal s where lower(eventname)='rds' and eventid='"
												+ obj.getResprdsid() + "'");
								if (!BasicUtils.isEmpty(respSupportList)) {
									VwRespiratoryUsageFinal respSupportObj = respSupportList.get(0);
									if (!(BasicUtils.isEmpty(respSupportObj.getDifferenceinhrs())
											|| BasicUtils.isEmpty(respSupportObj.getRsVentType()))) {
										rdsObj.setResp_support(true);
										int currentDuration = ((Float) respSupportObj.getDifferenceinhrs()).intValue();

										if (respSupportObj.getRsVentType().equalsIgnoreCase("Low Flow O2")) {
											rdsObj.setDuration_lowflow(rdsObj.getDuration_lowflow() + currentDuration);
										} else if (respSupportObj.getRsVentType().equalsIgnoreCase("High Flow O2")) {
											rdsObj.setDuration_highflow(
													rdsObj.getDuration_highflow() + currentDuration);
										} else if (respSupportObj.getRsVentType().equalsIgnoreCase("CPAP")) {
											rdsObj.setDuration_cpap(rdsObj.getDuration_cpap() + currentDuration);
										} else if (respSupportObj.getRsVentType()
												.equalsIgnoreCase("Mechanical Ventilation")) {
											rdsObj.setDuration_mv(rdsObj.getDuration_mv() + currentDuration);
										} else if (respSupportObj.getRsVentType().equalsIgnoreCase("HFO")) {
											rdsObj.setDuration_hfo(rdsObj.getDuration_hfo() + currentDuration);
										}
									}
								}
							}

							if (obj.getTreatmentaction().contains("TRE006")) {
								List<BabyPrescription> antibioticsList = inicuDao
										.getListFromMappedObjQuery("SELECT s FROM BabyPrescription s where uhid='"
												+ uhid + "' and lower(eventname)='rds' and eventid='"
												+ obj.getResprdsid() + "'");
								if (!BasicUtils.isEmpty(antibioticsList)) {
									Iterator<BabyPrescription> antiItr = antibioticsList.iterator();
									while (antiItr.hasNext()) {
										BabyPrescription antibioticObj = antiItr.next();
										if (antibioticObj.getEnddate() != null) {
											antibioticCount++;
											long duration = (((antibioticObj.getEnddate().getTime()
													- antibioticObj.getStartdate().getTime())) / (24 * 60 * 60 * 1000))
													+ 1;
											antibioticDuration += ((Long) duration).intValue();
										}
									}
								}
							}
						}

						if (!BasicUtils.isEmpty(obj.getDownesscoreid())) {
							List<ScoreDownes> downesList = inicuDao.getListFromMappedObjQuery(
									"SELECT s FROM ScoreDownes s where downesscoreid=" + obj.getDownesscoreid());
							if (!BasicUtils.isEmpty(downesList) && !BasicUtils.isEmpty(downesList.get(0).getDownesscore()) && downesList.get(0).getDownesscore() > maxDownes) {
								maxDownes = downesList.get(0).getDownesscore();
							}
						}

						if (obj.getCauseofrds() != null) {
							String cause = obj.getCauseofrds();
							List<String> list = Arrays.asList(cause.substring(1, cause.length() - 1).split(", "));
							Iterator<String> itrCause = list.iterator();
							while (itrCause.hasNext()) {
								String causeValue = itrCause.next();
								causeSet.add(this.rdsCauseMap.get(causeValue));
							}
							String causeStr = causeSet.toString();
							causeStr = causeStr.substring(1, causeStr.length() - 1);
							while (causeStr.contains(",")) {
								causeStr = causeStr.replace(",", ";");
							}
							rdsObj.setRds_cause(causeStr);
						}
					} else if (obj.getEventstatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						rdsObj.setRds_duration(((Long) durationDays).intValue());
						rdsObj.setMax_downes(maxDownes);
						rdsObj.setNo_antibiotics(antibioticCount);
						rdsObj.setAntibiotics_duration(antibioticDuration);
						returnList.add(rdsObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						rdsObj.setRds_duration(((Long) durationDays).intValue());
					}
					rdsObj.setMax_downes(maxDownes);
					rdsObj.setNo_antibiotics(antibioticCount);
					rdsObj.setAntibiotics_duration(antibioticDuration);
					returnList.add(rdsObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	private List<SepsisReportJSON> getSepsisReportList(String uhid) {
		List<SepsisReportJSON> returnList = new ArrayList<SepsisReportJSON>();
		try {
			SepsisReportJSON sepsisObj = new SepsisReportJSON();
			List<SaSepsis> sepsisDbList = inicuDao.getListFromMappedObjQuery(
					"SELECT s FROM SaSepsis s where uhid='" + uhid + "' order by assessmentTime");
			if (!BasicUtils.isEmpty(sepsisDbList)) {
				long startTime = 0;
				long endTime = 0;
				Integer antibioticCount = 0;
				Integer antibioticDuration = 0;
				HashSet<String> causeSet = null;

				Iterator<SaSepsis> itr = sepsisDbList.iterator();
				while (itr.hasNext()) {
					SaSepsis obj = itr.next();
					if (obj.getEventstatus() == null) {
						continue;
					} else if (obj.getEventstatus().equalsIgnoreCase("yes")) {
						if (startTime == 0) {
							causeSet = new HashSet<String>();
							sepsisObj = new SepsisReportJSON();
							antibioticCount = 0;
							antibioticDuration = 0;

							startTime = obj.getAssessmentTime().getTime();
							if (obj.getAgeinhoursdays() == null || obj.getAgeinhoursdays()) {
								sepsisObj.setSepsis_onset(Integer.parseInt(obj.getAgeatonset()));
							} else {
								sepsisObj.setSepsis_onset(Integer.parseInt(obj.getAgeatonset()) * 24);
							}

							if (sepsisObj.getSepsis_onset() <= 72) {
								sepsisObj.setSepsis_onset_type("Early Onset");
							} else {
								sepsisObj.setSepsis_onset_type("Late Onset");
							}
						}

						if (obj.getNecStatus() != null && obj.getNecStatus()) {
							sepsisObj.setSepsis_nec(true);
						}

						if (!BasicUtils.isEmpty(obj.getTreatmentaction())
								&& obj.getTreatmentaction().contains("TRE086")) {
							List<BabyPrescription> antibioticsList = inicuDao
									.getListFromMappedObjQuery("SELECT s FROM BabyPrescription s where uhid='" + uhid
											+ "' and eventname='Sepsis' and eventid='" + obj.getSasepsisid() + "'");
							if (!BasicUtils.isEmpty(antibioticsList)) {
								Iterator<BabyPrescription> antiItr = antibioticsList.iterator();
								while (antiItr.hasNext()) {
									BabyPrescription antibioticObj = antiItr.next();
									if (antibioticObj.getEnddate() != null) {
										antibioticCount++;
										long duration = (((antibioticObj.getEnddate().getTime()
												- antibioticObj.getStartdate().getTime())) / (24 * 60 * 60 * 1000)) + 1;
										antibioticDuration += ((Long) duration).intValue();
									}
								}
							}
						}

						if (obj.getCauseofsepsis() != null) {
							String cause = obj.getCauseofsepsis();
							List<String> list = Arrays.asList(cause.substring(1, cause.length() - 1).split(", "));
							Iterator<String> itrCause = list.iterator();
							while (itrCause.hasNext()) {
								String causeValue = itrCause.next();
								causeSet.add(this.sepsisCauseMap.get(causeValue));
							}
							String causeStr = causeSet.toString();
							causeStr = causeStr.substring(1, causeStr.length() - 1);
							while (causeStr.contains(",")) {
								causeStr = causeStr.replace(",", ";");
							}
							sepsisObj.setSepsis_cause(causeStr);
						}
					} else if (obj.getEventstatus().equalsIgnoreCase("no")) {
						endTime = obj.getAssessmentTime().getTime();
					} else if (startTime > 0 && endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						sepsisObj.setSepsis_duration(((Long) durationDays).intValue());
						sepsisObj.setNo_antibiotics(antibioticCount);
						sepsisObj.setAntibiotics_duration(antibioticDuration);
						returnList.add(sepsisObj);
						startTime = 0;
						endTime = 0;
					}
				}

				if (startTime > 0) {
					if (endTime > 0) {
						long durationDays = ((endTime - startTime) / (24 * 60 * 60 * 1000)) + 1;
						sepsisObj.setSepsis_duration(((Long) durationDays).intValue());
					}
					sepsisObj.setNo_antibiotics(antibioticCount);
					sepsisObj.setAntibiotics_duration(antibioticDuration);
					returnList.add(sepsisObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Systemic Report", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	/**
	 * @param uhid
	 * @param sepsisIdStr
	 * @return string having details of medicines specific to Sepsis
	 */
	@SuppressWarnings("unchecked")
	private String getSepsisMedicationStr(String uhid, String sepsisIdStr) {
		String medicineStr = "";
		long millisInDay = 24 * 60 * 60 * 1000;
		try {
			List<BabyPrescription> medicineList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyPrescriptionList(uhid)
							+ " and eventname = 'Sepsis' and eventid in (" + sepsisIdStr + ")");
			for (BabyPrescription medicine : medicineList) {
				Double days = Math.floor(
						((null == medicine.getEnddate() ? System.currentTimeMillis() : medicine.getEnddate().getTime())
								- medicine.getStartdate().getTime()) / millisInDay);

				medicineStr += ", " + medicine.getMedicinename()
						+ (null == medicine.getEnddate()
								? " continue for " + BasicUtils.ordinal(days.intValue() + 1) + " day"
								: " given for " + (days.intValue() + 1) + " days");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicineStr.substring(2);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HMAnalyticsPOJO getHMObservableData(String fromDateStr, String toDateStr, String branchName) {
		System.out.println("********TIME CHECK********" + fromDateStr);
		HMAnalyticsPOJO returnList = new HMAnalyticsPOJO();
		try {

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			String babyQuery = "SELECT b FROM HMObservableStatus b where dateofadmission <= '" + toDate
					+ "' and dateofadmission >= '" + fromDate + "' and status = 'HM' and branchname = '" + branchName + "' order by dateofadmission desc";
			List<HMObservableStatus> hmList = inicuDao.getListFromMappedObjQuery(babyQuery);

			babyQuery = "SELECT b FROM HMObservableStatus b where dateofadmission <= '" + toDate
					+ "' and dateofadmission >= '" + fromDate + "' and status = 'LM' and branchname = '" + branchName + "' order by dateofadmission desc";
			List<HMObservableStatus> lmList = inicuDao.getListFromMappedObjQuery(babyQuery);

			returnList.setHmList(hmList);
			returnList.setLmList(lmList);

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"vitalTracker Data", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public BaselinePOJO getBaselineData(String fromDateStr, String toDateStr, String branchName) {
		BaselinePOJO jsonArray = new BaselinePOJO();
		try {
			
			
			try {
		
				URL predict = new URL("http://127.0.0.1:8000/api/load/Moti%20Nagar,%20Delhi/"+fromDateStr + "/"+ toDateStr + "/");
			    HttpURLConnection conn = (HttpURLConnection) predict.openConnection();
			        
		       conn.setRequestMethod("GET");
		       conn.setDoOutput(true);
		       conn.connect();
		       int responseCode = conn.getResponseCode();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
		
				String output;
				System.out.println("Output from Server .... \n");
					output = br.readLine();
					JSONObject json = new JSONObject(output);
					jsonArray = new BaselinePOJO();
					jsonArray.setCount((String)json.get("Count"));
					jsonArray.setGestation((String)json.get("Gestation(Weeks)"));
					jsonArray.setBirthWeight((String)json.get("Birth Weight(gms)"));
					jsonArray.setLOS((String)json.get("lOS*(Days)"));
					jsonArray.setAdmissionWeight((String)json.get("Admission Weight(gms)"));
					jsonArray.setMale((String)json.get("Male"));
					jsonArray.setFemale((String)json.get("Female"));
					jsonArray.setAPGAROne((String)json.get("One Min APGAR"));
					jsonArray.setAPGARFive((String)json.get("Five Min APGAR"));
					jsonArray.setNVD((String)json.get("NVD"));
					jsonArray.setLSCS((String)json.get("LSCS"));
					jsonArray.setInborn((String)json.get("In Born"));
					jsonArray.setOutborn((String)json.get("Out Born"));
					jsonArray.setJaundiceTrue((String)json.get("Jaundice True"));
					jsonArray.setJaundiceFalse((String)json.get("Jaundice False"));
					jsonArray.setSepsisTrue((String)json.get("Sepsis True"));
					jsonArray.setSepsisFalse((String)json.get("Sepsis False"));
					jsonArray.setRDSTrue((String)json.get("RDS True"));
					jsonArray.setRDSFalse((String)json.get("RDS False"));
					jsonArray.setSingle((String)json.get("Single"));
					jsonArray.setMultiple((String)json.get("Multiple"));
					jsonArray.setAntenatalSteroids((String)json.get("Antenatal Steroids True"));
					jsonArray.setNoAntenatalSteroids((String)json.get("Antenatal Steroids False"));

					System.out.println(output);
				
				conn.disconnect();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public String gettingTheSignificantRiskFactor(String factor, String uhid) {
		
		try {
		String babyQuery = "SELECT b FROM BabyDetail b where uhid = '" + uhid + "'";
		List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(babyQuery);

		String antenatalQuery = "SELECT b FROM AntenatalHistoryDetail b where uhid = '" + uhid + "'";
		List<AntenatalHistoryDetail> antenatalList = inicuDao.getListFromMappedObjQuery(antenatalQuery);
		
		String birthToNicuQuery = "SELECT b FROM BirthToNicu b where uhid = '" + uhid + "'";
		List<BirthToNicu> birthToNicuQueryList = inicuDao.getListFromMappedObjQuery(birthToNicuQuery);
		
		if(factor.equalsIgnoreCase("gender_male_riskfactor")) {
			if(babyList.get(0).getGender().equalsIgnoreCase("Male")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("gender_female_riskfactor")) {
			if(babyList.get(0).getGender().equalsIgnoreCase("Female")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("gestation_fourth_quartile_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getGestationQuartile()) && babyList.get(0).getGestationQuartile()) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("gestation_rem_quartile_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getGestationQuartile()) && !babyList.get(0).getGestationQuartile()) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("energy_fourth_quartile_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getEnergyDeviation()) && babyList.get(0).getEnergyDeviation()) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("energy_rem_quartile_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getEnergyDeviation()) && !babyList.get(0).getEnergyDeviation()) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("protein_fourth_quartile_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getProteinDeviation()) && babyList.get(0).getProteinDeviation()) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("protein_rem_quartile_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getProteinDeviation()) && !babyList.get(0).getProteinDeviation()) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("medicine_not_required_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation()) && babyList.get(0).getMedicationDeviation()== -1) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("deviation_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation()) && babyList.get(0).getMedicationDeviation()== 1) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("no_deviation_riskfactor")) {
			if(!BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation()) && babyList.get(0).getMedicationDeviation()== 0) {
				return "red";
			}
		}
		else if(factor.equalsIgnoreCase("single_pregnancy_riskfactor")) {
			if(babyList.get(0).getBabyType().equalsIgnoreCase("Single")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("multiple_pregnancy_riskfactor")) {
			if(!babyList.get(0).getBabyType().equalsIgnoreCase("Single")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("inborn_riskfactor")) {
			if(babyList.get(0).getInoutPatientStatus().equalsIgnoreCase("In born")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("out_born_riskfactor")) {
			if(babyList.get(0).getInoutPatientStatus().equalsIgnoreCase("Out born")) {
				return "red";
			}
		}
		else if(factor.equalsIgnoreCase("apgar_greater_than_five_riskfactor")) {
			if(!BasicUtils.isEmpty(birthToNicuQueryList.get(0).getApgarFivemin())) {
				if(birthToNicuQueryList.get(0).getApgarFivemin() > 5) {
					return "red";
				}
			}
		}else if(factor.equalsIgnoreCase("apgar_less_than_five_riskfactor")) {
			if(!BasicUtils.isEmpty(birthToNicuQueryList.get(0).getApgarFivemin())) {
				if(birthToNicuQueryList.get(0).getApgarFivemin() <= 5) {
					return "red";
				}
			}
		}else if(factor.equalsIgnoreCase("apgar_not_available_riskfactor")) {
			if(BasicUtils.isEmpty(birthToNicuQueryList.get(0).getApgarFivemin())) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("nvd_riskfactor")) {
			if(!BasicUtils.isEmpty(antenatalList.get(0).getModeOfDelivery()) && !antenatalList.get(0).getModeOfDelivery().equalsIgnoreCase("LSCS")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("lscs_riskfactor")) {
			if(!BasicUtils.isEmpty(antenatalList.get(0).getModeOfDelivery()) && antenatalList.get(0).getModeOfDelivery().equalsIgnoreCase("LSCS")) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("maternal_diseases_true_riskfactor")) {
			if((!BasicUtils.isEmpty(antenatalList.get(0).getHypertension()) && antenatalList.get(0).getHypertension() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getGestationalHypertension()) && antenatalList.get(0).getGestationalHypertension() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getDiabetes()) && antenatalList.get(0).getDiabetes() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getGdm()) && antenatalList.get(0).getGdm() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getChronicKidneyDisease()) && antenatalList.get(0).getChronicKidneyDisease() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getHypothyroidism()) && antenatalList.get(0).getHypothyroidism() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getHyperthyroidism()) && antenatalList.get(0).getHyperthyroidism() == true)) {
				return "red";
			}
			
		}else if(factor.equalsIgnoreCase("maternal_diseases_false_riskfactor")) {
			if((!BasicUtils.isEmpty(antenatalList.get(0).getHypertension()) && antenatalList.get(0).getHypertension() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getGestationalHypertension()) && antenatalList.get(0).getGestationalHypertension() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getDiabetes()) && antenatalList.get(0).getDiabetes() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getGdm()) && antenatalList.get(0).getGdm() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getChronicKidneyDisease()) && antenatalList.get(0).getChronicKidneyDisease() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getHypothyroidism()) && antenatalList.get(0).getHypothyroidism() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getHyperthyroidism()) && antenatalList.get(0).getHyperthyroidism() == true)) {
			}else {
				return "red";
			}
		}
		else if(factor.equalsIgnoreCase("maternal_infections_true_riskfactor")) {
			if((!BasicUtils.isEmpty(antenatalList.get(0).getFever()) && antenatalList.get(0).getFever() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getUti()) && antenatalList.get(0).getUti() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getHistoryOfInfections()) && antenatalList.get(0).getHistoryOfInfections() == true)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("maternal_infections_false_riskfactor")) {
			if((!BasicUtils.isEmpty(antenatalList.get(0).getFever()) && antenatalList.get(0).getFever() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getUti()) && antenatalList.get(0).getUti() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getHistoryOfInfections()) && antenatalList.get(0).getHistoryOfInfections() == true)) {
			}else {
				return "red";
			}
		}
		else if(factor.equalsIgnoreCase("maternal_risk_factors_true_riskfactor")) {
			if((!BasicUtils.isEmpty(antenatalList.get(0).getProm()) && antenatalList.get(0).getProm() == true) || 
					(!BasicUtils.isEmpty(antenatalList.get(0).getPprom()) && antenatalList.get(0).getPprom() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getChorioamniotis()) && antenatalList.get(0).getChorioamniotis() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getOligohydraminos()) && antenatalList.get(0).getOligohydraminos() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getPolyhydraminos()) && antenatalList.get(0).getPolyhydraminos() == true)) {
				return "red";
			}

		}else if(factor.equalsIgnoreCase("maternal_risk_factors_false_riskfactor")) {
			if((!BasicUtils.isEmpty(antenatalList.get(0).getProm()) && antenatalList.get(0).getProm() == true) || 
					(!BasicUtils.isEmpty(antenatalList.get(0).getPprom()) && antenatalList.get(0).getPprom() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getChorioamniotis()) && antenatalList.get(0).getChorioamniotis() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getOligohydraminos()) && antenatalList.get(0).getOligohydraminos() == true) ||
					(!BasicUtils.isEmpty(antenatalList.get(0).getPolyhydraminos()) && antenatalList.get(0).getPolyhydraminos() == true)) {
			}else {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("rds_true_riskfactor") || factor.equalsIgnoreCase("rds_false_riskfactor") || factor.equalsIgnoreCase("mas_true_riskfactor") || factor.equalsIgnoreCase("mas_true_riskfactor")
				|| factor.equalsIgnoreCase("ttnb_true_riskfactor") || factor.equalsIgnoreCase("ttnb_true_riskfactor")) {
			
			boolean isRds = false;
			boolean isRdsTTNB = false;
			boolean isRdsMAS = false;

			String rdsQuery = "select obj from SaRespRds obj where uhid = '" + uhid + "' and episode_number = 1 and eventstatus = 'Yes' order by assessment_time asc";
			List<SaRespRds> saRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
			if(!BasicUtils.isEmpty(saRespRdsList)) {
				isRds = true;
			}


			if(isRds) {
				for(SaRespRds resp : saRespRdsList) {
					if(!BasicUtils.isEmpty(resp.getCauseofrds()) && resp.getCauseofrds().contains("RDS0001")) {
						isRdsTTNB = true;
						isRds = false;
					}
					if(!BasicUtils.isEmpty(resp.getCauseofrds()) && resp.getCauseofrds().contains("RDS0003")) {
						isRdsMAS = true;
						isRds = false;
					}
				}

				String reasonQuery = "select obj from ReasonAdmission obj where uhid = '" + uhid + "'";
				List<ReasonAdmission> reasonList = prescriptionDao.getListFromMappedObjNativeQuery(reasonQuery);
				for(ReasonAdmission reason : reasonList) {
					if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("TTNB")){
						isRdsTTNB = true;
						isRds = false;
					}
					if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("MAS")){
						isRdsMAS = true;
						isRds = false;
					}
				}
			}
			
			if(factor.equalsIgnoreCase("rds_true_riskfactor")) {
				if(isRds) {
					return "red";
				}
			
			}else if(factor.equalsIgnoreCase("rds_false_riskfactor")) {
				if(!isRds) {
					return "red";
				}
				
			}else if(factor.equalsIgnoreCase("mas_true_riskfactor")) {
				if(isRdsMAS) {
					return "red";
				}
				
			}else if(factor.equalsIgnoreCase("mas_false_riskfactor")) {
				if(!isRdsMAS) {
					return "red";
				}
				
			}else if(factor.equalsIgnoreCase("ttnb_true_riskfactor")) {
				if(isRdsTTNB) {
					return "red";
				}
				
			}else if(factor.equalsIgnoreCase("ttnb_false_riskfactor")) {
				if(!isRdsTTNB) {
					return "red";
				}
				
			}
			
			
		}else if(factor.equalsIgnoreCase("jaundice_true_riskfactor")) {
			String jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + uhid + "' and episode_number = 1 and jaundicestatus = 'Yes' and phototherapyvalue='Start' order by assessment_time asc";
			List<SaJaundice> jaunList = prescriptionDao.getListFromMappedObjNativeQuery(jaundiceQuery);
			if(!BasicUtils.isEmpty(jaunList)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("jaundice_false_riskfactor")) {
			String jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + uhid + "' and episode_number = 1 and jaundicestatus = 'Yes' and phototherapyvalue='Start' order by assessment_time asc";
			List<SaJaundice> jaunList = prescriptionDao.getListFromMappedObjNativeQuery(jaundiceQuery);
			if(BasicUtils.isEmpty(jaunList)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("sepsis_true_riskfactor")) {
			String sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
			List<SaSepsis> sepsisLists = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQuery);
			if(!BasicUtils.isEmpty(sepsisLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("sepsis_false_riskfactor")) {
			String sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
			List<SaSepsis> sepsisLists = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQuery);
			if(BasicUtils.isEmpty(sepsisLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("asphyxia_true_riskfactor")) {
			String asphyxiaQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
			List<SaCnsAsphyxia> asphyxiaLists = prescriptionDao.getListFromMappedObjNativeQuery(asphyxiaQuery);
			if(!BasicUtils.isEmpty(asphyxiaLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("asphyxia_false_riskfactor")) {
			String asphyxiaQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
			List<SaCnsAsphyxia> asphyxiaLists = prescriptionDao.getListFromMappedObjNativeQuery(asphyxiaQuery);
			if(BasicUtils.isEmpty(asphyxiaLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("pneumothorax_true_riskfactor")) {
			String pneumoQuery = "select obj from SaRespPneumo obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1 order by assessment_time asc";
			List<SaRespPneumo> pneumoLists = prescriptionDao.getListFromMappedObjNativeQuery(pneumoQuery);
			if(!BasicUtils.isEmpty(pneumoLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("pneumothorax_false_riskfactor")) {
			String pneumoQuery = "select obj from SaRespPneumo obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1 order by assessment_time asc";
			List<SaRespPneumo> pneumoLists = prescriptionDao.getListFromMappedObjNativeQuery(pneumoQuery);
			if(BasicUtils.isEmpty(pneumoLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("pphn_true_riskfactor")) {
			String pphnQuery = "select obj from SaRespPphn obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1  order by assessment_time asc";
			List<SaRespPphn> pphnLists = prescriptionDao.getListFromMappedObjNativeQuery(pphnQuery);
			if(!BasicUtils.isEmpty(pphnLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("pphn_false_riskfactor")) {
			String pphnQuery = "select obj from SaRespPphn obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1  order by assessment_time asc";
			List<SaRespPphn> pphnLists = prescriptionDao.getListFromMappedObjNativeQuery(pphnQuery);
			if(!BasicUtils.isEmpty(pphnLists)) {
				return "red";
			}
		}else if(factor.equalsIgnoreCase("invasive_true_riskfactor")) {
			String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
			for(RespSupport resp : bpdList) {
				if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
					return "red";
				}
			}
			
		}else if(factor.equalsIgnoreCase("invasive_false_riskfactor")) {
			String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
			List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
			boolean isRespSupport = false;
			for(RespSupport resp : bpdList) {
				if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
					isRespSupport = true;
				}else if(isRespSupport == false){
					isRespSupport = false;
				}
			}if(BasicUtils.isEmpty(bpdList) || isRespSupport == false) {
				return "red";
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "black";

	}
	
	public PredictLOS losFactors(String uhid, String factor) {
		PredictLOS jsonArray = new PredictLOS();
		HashMap<String, String> riskFactorsMapping = new HashMap<String, String>(){
			
		};
		String babyQuery = "SELECT b FROM BabyDetail b where uhid = '" + uhid + "'";
		List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(babyQuery);
		
		if(babyList.get(0).getGestationweekbylmp() >= 26 && babyList.get(0).getGestationweekbylmp() < 32) {
			String color = gettingTheSignificantRiskFactor(factor, uhid);
			IdentificationValue identify = new IdentificationValue();
			identify.setColorIdentification(color);
			jsonArray.setGest1(identify);
		}else if(babyList.get(0).getGestationweekbylmp() >= 32 && babyList.get(0).getGestationweekbylmp() < 34) {
			String color = gettingTheSignificantRiskFactor(factor, uhid);
			IdentificationValue identify = new IdentificationValue();
			identify.setColorIdentification(color);
			jsonArray.setGest2(identify);		
		}else if(babyList.get(0).getGestationweekbylmp() >= 34 && babyList.get(0).getGestationweekbylmp() < 37) {
			String color = gettingTheSignificantRiskFactor(factor, uhid);
			IdentificationValue identify = new IdentificationValue();
			identify.setColorIdentification(color);
			jsonArray.setGest3(identify);		
		}else if(babyList.get(0).getGestationweekbylmp() >= 37) {
			String color = gettingTheSignificantRiskFactor(factor, uhid);
			IdentificationValue identify = new IdentificationValue();
			identify.setColorIdentification(color);
			jsonArray.setGest4(identify);
		}
		
		riskFactorsMapping.put("total_count", "Total Count");
		riskFactorsMapping.put("median", "Median");
		riskFactorsMapping.put("iqr", "IQR");
		riskFactorsMapping.put("gestation_fourth_quartile_riskfactor", "Gestation Category (4th Quartile)");
		riskFactorsMapping.put("gestation_rem_quartile_riskfactor", "Gestation Category (Remaining Quartile)");
		riskFactorsMapping.put("energy_fourth_quartile_riskfactor", "Energy Category (4th Quartile)");
		riskFactorsMapping.put("energy_rem_quartile_riskfactor", "Energy Category (Remaining Quartile)");
		riskFactorsMapping.put("protein_fourth_quartile_riskfactor", "Protein Category (4th Quartile)");
		riskFactorsMapping.put("protein_rem_quartile_riskfactor", "Protein Category (Remaining Quartile)");
		riskFactorsMapping.put("gender_male_riskfactor", "Male");
		riskFactorsMapping.put("gender_female_riskfactor", "Female");
		riskFactorsMapping.put("single_pregnancy_riskfactor", "Single");
		riskFactorsMapping.put("multiple_pregnancy_riskfactor", "Multiple");
		riskFactorsMapping.put("inborn_riskfactor", "Inborn");
		riskFactorsMapping.put("out_born_riskfactor", "Outborn");
		riskFactorsMapping.put("apgar_greater_than_five_riskfactor", "APGAR Greater than 5");
		riskFactorsMapping.put("apgar_less_than_five_riskfactor", "APGAR less than 5");
		riskFactorsMapping.put("apgar_not_available_riskfactor", "APGAR Not available");
		riskFactorsMapping.put("ppv_riskfactor", "PPV");
		riskFactorsMapping.put("nvd_riskfactor", "NVD");
		riskFactorsMapping.put("lscs_riskfactor", "LSCS");
		riskFactorsMapping.put("maternal_diseases_true_riskfactor", "Maternal Disease Present");
		riskFactorsMapping.put("maternal_diseases_false_riskfactor", "Maternal Disease Absent");
		riskFactorsMapping.put("maternal_infections_true_riskfactor", "Maternal Infections Present");
		riskFactorsMapping.put("maternal_infections_false_riskfactor", "Maternal Infections Absent");
		riskFactorsMapping.put("maternal_risk_factors_true_riskfactor", "Maternal Risk Factors Present");
		riskFactorsMapping.put("maternal_risk_factors_false_riskfactor", "Maternal Risk Factors Absent");
		riskFactorsMapping.put("rds_true_riskfactor", "RDS Present");
		riskFactorsMapping.put("rds_false_riskfactor", "RDS Absent");
		riskFactorsMapping.put("mas_true_riskfactor", "MAS Present");
		riskFactorsMapping.put("mas_false_riskfactor", "MAS Absent");
		riskFactorsMapping.put("ttnb_true_riskfactor", "TTNB Present");
		riskFactorsMapping.put("ttnb_false_riskfactor", "TTNB Absent");
		riskFactorsMapping.put("jaundice_true_riskfactor", "Jaundice Present");
		riskFactorsMapping.put("jaundice_false_riskfactor", "Jaundice Absent");
		riskFactorsMapping.put("sepsis_true_riskfactor", "Sepsis Present");
		riskFactorsMapping.put("sepsis_false_riskfactor", "Sepsis Absent");
		riskFactorsMapping.put("asphyxia_true_riskfactor", "Asphyxia Present");
		riskFactorsMapping.put("asphyxia_false_riskfactor", "Asphyxia Absent");
		riskFactorsMapping.put("pphn_true_riskfactor", "PPHN Present");
		riskFactorsMapping.put("pphn_false_riskfactor", "PPHN Absent");
		riskFactorsMapping.put("pneumothorax_true_riskfactor", "Pneumothorax Present");
		riskFactorsMapping.put("pneumothorax_false_riskfactor", "Pneumothorax Absent");
		riskFactorsMapping.put("invasive_true_riskfactor", "Severe RDS (Invasive Support) Present");
		riskFactorsMapping.put("invasive_false_riskfactor", "Severe RDS (Invasive Support) Absent");		
		riskFactorsMapping.put("medicine_not_required_riskfactor", "Medication Not Required");
		riskFactorsMapping.put("deviation_riskfactor", "Dose Deviation");
		riskFactorsMapping.put("no_deviation_riskfactor", "Dose No Deviation");

		try {
			String getRiskFactor = "SELECT " + factor + " from " +
					BasicConstants.SCHEMA_NAME + ".predict_los where gest_catid = '1'";
			List<String> predictLOSData = userServiceDao.executeNativeQuery(getRiskFactor);
			if(!BasicUtils.isEmpty(predictLOSData) && predictLOSData.size() > 0 && !BasicUtils.isEmpty(predictLOSData.get(0))) {
				if(BasicUtils.isEmpty(jsonArray.getGest1())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue(predictLOSData.get(0));
					identify.setColorIdentification("grey");
					jsonArray.setGest1(identify);
				}else {
					jsonArray.getGest1().setValue(predictLOSData.get(0));
				}
			}else {
				if(BasicUtils.isEmpty(jsonArray.getGest1())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue("");
					identify.setColorIdentification("black");
					jsonArray.setGest1(identify);
				}else {
					jsonArray.getGest1().setValue("");
				}
				
		
			}
			
			getRiskFactor = "SELECT " + factor + " from " +
					BasicConstants.SCHEMA_NAME + ".predict_los where gest_catid = '2'";
			predictLOSData = userServiceDao.executeNativeQuery(getRiskFactor);
			if(!BasicUtils.isEmpty(predictLOSData) && predictLOSData.size() > 0 && !BasicUtils.isEmpty(predictLOSData.get(0))) {
				if(BasicUtils.isEmpty(jsonArray.getGest2())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue(predictLOSData.get(0));
					identify.setColorIdentification("grey");
					jsonArray.setGest2(identify);
				}else {
					jsonArray.getGest2().setValue(predictLOSData.get(0));
				}			
			}else {
				if(BasicUtils.isEmpty(jsonArray.getGest2())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue("");
					identify.setColorIdentification("black");
					jsonArray.setGest2(identify);
				}else {
					jsonArray.getGest2().setValue("");
				}
			}
			
			getRiskFactor = "SELECT " + factor + " from " +
					BasicConstants.SCHEMA_NAME + ".predict_los where gest_catid = '3'";
			predictLOSData = userServiceDao.executeNativeQuery(getRiskFactor);
			if(!BasicUtils.isEmpty(predictLOSData) && predictLOSData.size() > 0 && !BasicUtils.isEmpty(predictLOSData.get(0))) {
				if(BasicUtils.isEmpty(jsonArray.getGest3())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue(predictLOSData.get(0));
					identify.setColorIdentification("grey");
					jsonArray.setGest3(identify);
				}else {
					jsonArray.getGest3().setValue(predictLOSData.get(0));
				}
			}else {
				if(BasicUtils.isEmpty(jsonArray.getGest3())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue("");
					identify.setColorIdentification("black");
					jsonArray.setGest3(identify);
				}else {
					jsonArray.getGest3().setValue("");
				}
			}
			
			getRiskFactor = "SELECT " + factor + " from " +
					BasicConstants.SCHEMA_NAME + ".predict_los where gest_catid = '4'";
			predictLOSData = userServiceDao.executeNativeQuery(getRiskFactor);
			if(!BasicUtils.isEmpty(predictLOSData) && predictLOSData.size() > 0 && !BasicUtils.isEmpty(predictLOSData.get(0))) {
				if(BasicUtils.isEmpty(jsonArray.getGest4())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue(predictLOSData.get(0));
					identify.setColorIdentification("grey");
					jsonArray.setGest4(identify);
				}else {
					jsonArray.getGest4().setValue(predictLOSData.get(0));
				}
			}else {
				if(BasicUtils.isEmpty(jsonArray.getGest4())) {
					IdentificationValue identify = new IdentificationValue();
					identify.setValue("");
					identify.setColorIdentification("black");
					jsonArray.setGest4(identify);
				}else {
					jsonArray.getGest4().setValue("");
				}
			}
			jsonArray.setFactor(riskFactorsMapping.get(factor));
			
			
			if(!(babyList.get(0).getGestationweekbylmp() >= 26 && babyList.get(0).getGestationweekbylmp() < 32)) {
				jsonArray.getGest1().setColorIdentification("grey");
			}if(!(babyList.get(0).getGestationweekbylmp() >= 32 && babyList.get(0).getGestationweekbylmp() < 34)) {
				String color = gettingTheSignificantRiskFactor(factor, uhid);
				jsonArray.getGest2().setColorIdentification("grey");
			}if(!(babyList.get(0).getGestationweekbylmp() >= 34 && babyList.get(0).getGestationweekbylmp() < 37)) {
				String color = gettingTheSignificantRiskFactor(factor, uhid);
				jsonArray.getGest3().setColorIdentification("grey");
			}if(!(babyList.get(0).getGestationweekbylmp() >= 37)) {
				String color = gettingTheSignificantRiskFactor(factor, uhid);
				jsonArray.getGest4().setColorIdentification("grey");	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonArray;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PredictLOS> predictLOS(String branchName, String uhid) {
		List<PredictLOS> jsonArrayList = new ArrayList<PredictLOS>();
		try {
			
			try {
		
				PredictLOS jsonArray1 = losFactors(uhid, "total_count");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "median");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "iqr");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "gestation_fourth_quartile_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "gestation_rem_quartile_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "energy_fourth_quartile_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "energy_rem_quartile_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "protein_fourth_quartile_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "protein_rem_quartile_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "gender_male_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "gender_female_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "single_pregnancy_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "multiple_pregnancy_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "inborn_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "out_born_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "apgar_greater_than_five_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "apgar_less_than_five_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "apgar_not_available_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "ppv_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "nvd_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "lscs_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "maternal_diseases_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "maternal_diseases_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "maternal_infections_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "maternal_infections_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "maternal_risk_factors_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "maternal_risk_factors_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "rds_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "rds_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "mas_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "mas_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "ttnb_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "ttnb_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "jaundice_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "jaundice_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "sepsis_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "sepsis_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "asphyxia_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "asphyxia_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "pphn_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "pphn_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "pneumothorax_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "pneumothorax_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "invasive_true_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "invasive_false_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "medicine_not_required_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "deviation_riskfactor");
				jsonArrayList.add(jsonArray1);

				jsonArray1 = losFactors(uhid, "no_deviation_riskfactor");
				jsonArrayList.add(jsonArray1);
				
				
				
			    

				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return jsonArrayList;
	}



	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getCustomReportsData(String branchName, JSONObject requestJson) {

		List<Object> returnObject = new ArrayList<>();

		String inOut =null;
		String birthWeight =null;
		String gestation =null;
		String jaundiceTCB =null;
		String jaundicePT =null;



		ArrayList<String> indexArray = new ArrayList<String>(5);

		Timestamp fromDate = null;
		Timestamp toDate = null;

		if(requestJson!= null){
			try {
				fromDate =  new Timestamp(Long.parseLong(requestJson.getString("fromDate")));
			}catch(Exception e){
			}

		}

		if(requestJson!= null){
			try {
				toDate =  new Timestamp(Long.parseLong(requestJson.getString("toDate")));
			}catch(Exception e){
			}

		}

		if(requestJson!= null){
			try {
				inOut = requestJson.getString("inOut");
 			}catch(Exception e){
 			}

		}

		if(requestJson!= null){
			try {
 				birthWeight = requestJson.getString("birthWeight");
 			}catch(Exception e){
 			}

		}
		if(requestJson!= null){
			try {
 				gestation = requestJson.getString("gestation");
			}catch(Exception e){
 			}

		}
		System.out.println("birthWeight----------"+birthWeight);


		if(requestJson!= null){
			try {
				jaundiceTCB = requestJson.getString("jaundiceTCB");
			}catch(Exception e){
			}

		}
		System.out.println("jaundiceTCB----------"+jaundiceTCB);


		if(requestJson!= null){
			try {
				jaundicePT = requestJson.getString("jaundicePT");
			}catch(Exception e){
			}

		}
		System.out.println("jaundicePT----------"+jaundicePT);



		List allLists = new ArrayList();

		if( !BasicUtils.isEmpty(inOut)){

			String str[] = inOut.split(",");
			List<String> inOutList = Arrays.asList(str);
			allLists.add(inOutList);
			indexArray.add("inOut");
		}

		if( !BasicUtils.isEmpty(birthWeight)){

			String str[] = birthWeight.split(",");
			List<String> birthWeightList = Arrays.asList(str);
			allLists.add(birthWeightList);
			indexArray.add("birthWeight");

		}

		if( !BasicUtils.isEmpty(gestation)){

			String str[] = gestation.split(",");
			List<String> gestationList = Arrays.asList(str);
			allLists.add(gestationList);
			indexArray.add("gestation");

		}

		if( !BasicUtils.isEmpty(jaundiceTCB)){

			String str[] = jaundiceTCB.split(",");
			List<String> jaundiceTCBList = Arrays.asList(str);
			allLists.add(jaundiceTCBList);
			indexArray.add("jaundiceTCB");

		}

		if( !BasicUtils.isEmpty(jaundicePT)){

			String str[] = jaundicePT.split(",");
			List<String> jaundicePTList = Arrays.asList(str);
			allLists.add(jaundicePTList);
			indexArray.add("jaundicePT");
		}

		List<String> queryList = new ArrayList<String>();

		System.out.println("allLists----------"+allLists);

		System.out.println("queryList----------"+queryList);



		generateQueries(allLists, queryList, 0, "");

		System.out.println("returnList----------"+queryList);


		if(queryList!=null){

			for(String item : queryList){
				System.out.println(item);

				String str[] = item.split(";");


				Map dataMap = executeCustomQueries( branchName , indexArray , str, fromDate, toDate);

				System.out.println("dataMap--------"+dataMap);
				returnObject.add(dataMap);

			}

		}
		return returnObject;
			}






	void generateQueries(List<List> lists, List<String> result, int depth, String current) {
		if (depth == lists.size()) {
			result.add(current);
			return;
		}

		for (int i = 0; i < lists.get(depth).size(); i++) {
			generateQueries(lists, result, depth + 1, current +lists.get(depth).get(i)+";");
		}
	}


	Map executeCustomQueries(String branchName,ArrayList<String> indexArray , String strvar[], Timestamp fromDate, Timestamp toDate) {

		String inOut = null;
		String birthWeight = null;
		String gestation = null;
		String jaundiceTCB = null;
		String jaundicePT = null;



		if(indexArray.indexOf("inOut")!=-1)				inOut = strvar[indexArray.indexOf("inOut")];
		if(indexArray.indexOf("birthWeight")!=-1)	birthWeight = strvar[indexArray.indexOf("birthWeight")];
		if(indexArray.indexOf("gestation")!=-1) 	gestation = strvar[indexArray.indexOf("gestation")];
		if(indexArray.indexOf("jaundiceTCB")!=-1) 	jaundiceTCB = strvar[indexArray.indexOf("jaundiceTCB")];
		if(indexArray.indexOf("jaundicePT")!=-1) 	jaundicePT = strvar[indexArray.indexOf("jaundicePT")];






		System.out.println("inOut------------"+inOut);
		System.out.println("birthWeight------------"+birthWeight);
		System.out.println("gestation------------"+gestation);
		System.out.println("jaundiceTCB------------"+jaundiceTCB);

		Map returnMap = new HashMap();
		StringBuilder query = new StringBuilder("select distinct b.uhid, birthweight,gestationweekbylmp,inout_patient_status  from baby_detail b   ");

		query.append(" where ");

		query.append("  branchname = '").append( branchName).append("'");


		if(fromDate!=null) {
			query.append(" and dateofadmission >= '").append( fromDate).append("'");
 		}

		if(toDate!=null) {
			query.append(" and dateofadmission <= '").append( toDate).append("'");
 		}


		if(inOut!=null) {
 			query.append(" and inout_patient_status = '").append( inOut).append("'");
 			returnMap.put("filterInOut",inOut);
		}

		if(birthWeight!=null) {
			returnMap.put("filterBirthWeight",birthWeight);

			String[] str = birthWeight.split("-");
			if (Integer.parseInt(str[0])!=0)
				query.append(" and birthweight >= '").append( str[0]).append("'");

			if (Integer.parseInt(str[1])!=0)
				query.append(" and birthweight <= '").append( str[1]).append("'");
			else
				returnMap.put("filterBirthWeight","> "+str[0]);



		}


		if(gestation!=null) {

			returnMap.put("filterGestation",gestation);

			String[] str = gestation.split("-");
			if (Integer.parseInt(str[0])!=0)
				query.append(" and gestationweekbylmp >= '").append( str[0]).append("'");

			if (Integer.parseInt(str[1])!=0)
				query.append(" and gestationweekbylmp <= '").append( str[1]).append("'");
			else
				returnMap.put("filterGestation","> "+str[0]);


		}


		if(jaundiceTCB!=null) {

			returnMap.put("filterjaundiceTCB", jaundiceTCB
			);
		}

		if(jaundicePT!=null) {

			returnMap.put("filterjaundicePT", jaundicePT);
		}

			List<Object[]> babyList = (List<Object[]>) inicuDao.getListFromNativeQuery(
				query.toString());


int count = 0;
		int inCount = 0;
		int outCount = 0;

		int ptYesCount = 0;
		int ptNoCount = 0;


		int etYesCount = 0;
		int etNoCount = 0;


		int prematurity = 0;
		int TF = 0;
		int abo = 0;
		int rh = 0;


		if (!BasicUtils.isEmpty(babyList)) {

			System.out.println("total babies--------"+babyList.size());

			java.lang.StringBuilder weights = new java.lang.StringBuilder();
			java.lang.StringBuilder gestations = new java.lang.StringBuilder();
			for (Object[] babyObj : babyList) {

				if(jaundiceTCB!=null) {

					returnMap.put("filterjaundiceTCB", jaundiceTCB);

					StringBuilder jquery = new StringBuilder("select max(maxtcb) from sa_jaundice   ");

					jquery.append(" where ");


					jquery.append(" uhid= '").append(babyObj[0]).append("'");


					String[] str = jaundiceTCB.split("-");

					jquery.append(" having ");
					if (Integer.parseInt(str[0]) != 0)
						jquery.append("  max(maxtcb) >= '").append(str[0]).append("'");

					if (Integer.parseInt(str[0]) != 0 && Integer.parseInt(str[1]) != 0)
						jquery.append("  and ");


					if (Integer.parseInt(str[1]) != 0)
						jquery.append("  max(maxtcb) <= '").append(str[1]).append("'");
					else
						returnMap.put("filterjaundiceTCB", "> " + str[0]);


					List jList = (List) inicuDao.getListFromNativeQuery(
							jquery.toString());

					System.out.println("jList---------------------"+jList);
					if (BasicUtils.isEmpty(jList) || jList.size()==0) {
						System.out.println("before brk---------------------"+jList);

						continue;
					}

				}


					StringBuilder jquery = new StringBuilder("select phototherapyvalue from sa_jaundice   ");

					jquery.append(" where ");

					jquery.append(" uhid= '").append(babyObj[0]).append("'");


					jquery.append(" and phototherapyvalue!='' ");


					List jList = (List) inicuDao.getListFromNativeQuery(
							jquery.toString());
				if(jaundicePT!=null) {

					returnMap.put("filterjaundicePT", jaundicePT);

					System.out.println("jaundicePT ---------------------"+jaundicePT);

					if(jaundicePT.equals("Yes")){


						if (BasicUtils.isEmpty(jList) || jList.size()==0) {
							System.out.println("before brk---------------------"+jList);

							continue;
						}
					} else	if(jaundicePT.equals("No")){


						if (!BasicUtils.isEmpty(jList) && jList.size()>0) {
							System.out.println("before brk---------------------"+jList);

							continue;
						}
					}


				}

				System.out.println("after brk---------------------");
				count++;


				if (BasicUtils.isEmpty(jList) || jList.size()==0) {
					System.out.println("No PT---------------------"+jList);

					ptNoCount++;
				}

				if (!BasicUtils.isEmpty(jList) && jList.size()>0) {
					System.out.println("Yes PT---------------------"+jList);

					ptYesCount++;
				}


// exchangetrans start



				StringBuilder equery = new StringBuilder("select exchangetrans from sa_jaundice   ");
				equery.append(" where ");
				equery.append(" uhid= '").append(babyObj[0]).append("'");
				equery.append(" and exchangetrans=true ");


				List eList = (List) inicuDao.getListFromNativeQuery(
						equery.toString());

				if (BasicUtils.isEmpty(eList) || eList.size()==0) {

					etNoCount++;
				}

				if (!BasicUtils.isEmpty(eList) && eList.size()>0) {

					etYesCount++;

				}



				// exchangetrans end


				// rf start



				StringBuilder rfquery = new StringBuilder(" select distinct uhid,riskfactor,creationtime from sa_jaundice where riskfactor!=''    ");
				rfquery.append("  ");
				rfquery.append(" and uhid= '").append(babyObj[0]).append("'");
				rfquery.append(" order by creationtime desc; ");


				List<Object[]> rfList = (List<Object[]>) inicuDao.getListFromNativeQuery(
						rfquery.toString());

				if (!BasicUtils.isEmpty(rfList) && rfList.size()>0) {

String rf = (String) rfList.get(0)[1];

					String[] listRiskFactor = rf.replace(" ", "").trim().replace("[", "").replace("]", "")
							.split(",");
					ArrayList<String> listRiskFactorList = new ArrayList<String>(Arrays.asList(listRiskFactor));

					for (String temp : listRiskFactorList) {
						System.out.println("risk one---------"+temp);

						if("RSK00020".equals(temp)) prematurity++;
						if("RSK00009".equals(temp)) TF++;
						if("RSK00010".equals(temp)) abo++;
						if("RSK00011".equals(temp)) rh++;

					}


					System.out.println("rf------"+rf);
				}



				// rf end



				if(babyObj[1]!=null) weights.append(babyObj[1]).append(",");
				if(babyObj[2]!=null) 	gestations.append(babyObj[2]).append(",");
				if(babyObj[3]!=null && babyObj[3].equals("In Born") ) 	inCount++;
				if(babyObj[3]!=null && babyObj[3].equals("Out Born") ) outCount++;





			}

			returnMap.put("totalCount",count);
			returnMap.put("inOutCount",inCount+"/"+outCount);
			returnMap.put("ptCount",ptYesCount+"/"+ptNoCount);

			returnMap.put("etCount",etYesCount+"/"+etNoCount);
			returnMap.put("prematurity",prematurity);
			returnMap.put("rh",rh);
			returnMap.put("abo",abo);
			returnMap.put("TF",TF);



			System.out.println("weights--------"+weights);

if(!BasicUtils.isEmpty(weights.toString())) {
	//returnMap.put("weights",weights);
	returnMap.put("weightMean", mean(weights.toString()));
	returnMap.put("weightSD", calculateSD(weights.toString()));
	returnMap.put("weightMedian", calculateMedian(weights.toString()));
	returnMap.put("weightIQR", calculateIQR(weights.toString()));
	returnMap.put("weightRange", calculateRange(weights.toString()));


	returnMap.put("gestationMean", mean(gestations.toString()));
	returnMap.put("gestationSD", calculateSD(gestations.toString()));
	returnMap.put("gestationMedian", calculateMedian(gestations.toString()));
	returnMap.put("gestationIQR", calculateIQR(gestations.toString()));
	returnMap.put("gestationRange", calculateRange(gestations.toString()));

}



		}
				return returnMap;
	}

	public static String mean(String str) {
		String[] strArray = str.split(",");
		double sum = 0;
		for (int i = 0; i < strArray.length; i++) {
			sum += Float.parseFloat(strArray[i]);
		}
		return twoDecimal.format( sum / strArray.length);
	}

	public static String calculateSD(String str)
	{
		String[] strArray = str.split(",");

		double sum = 0.0, standardDeviation = 0.0;
		int length = strArray.length;
		for(String num : strArray) {
			sum += Double.parseDouble(num);
		}
		double mean = sum/length;
		for(String num: strArray) {
			standardDeviation += Math.pow(Double.parseDouble(num) - mean, 2);
		}
		return twoDecimal.format(Math.sqrt(standardDeviation/length));
	}

 	public static String calculateMedian(String str)
	{
		String[] strArray = str.split(",");

		int size = strArray.length;
		double [] arr = new double [size];
		for(int i=0; i<size; i++) {
			arr[i] = Double.parseDouble(strArray[i]);
		}

// First we sort the array
		Arrays.sort(arr);

		// check for even case
		if (size % 2 != 0)
			return twoDecimal.format((double)arr[size / 2]);

		return twoDecimal.format((double)(arr[(size - 1) / 2] + arr[size / 2]) / 2.0);

	}


	public static String calculateIQR(String str)
	{

		String[] strArray = str.split(",");

		int size = strArray.length;

		if(size>1) {
			double[] arr = new double[size];
			for (int i = 0; i < size; i++) {
				arr[i] = Double.parseDouble(strArray[i]);
			}
			Arrays.sort(arr);

			if (size==2) return twoDecimal.format(arr[1]-arr[0]);

			double Q1 = median(arr, 0, arr.length / 2 - 1);
			double Q3 = median(arr, (arr.length + 1) / 2, arr.length - 1);

			return twoDecimal.format((Q3 - Q1));

		}else{
			return twoDecimal.format(Double.parseDouble(strArray[0]));

		}


	}

	static double median(double a[],
					  int start, int end)
	{
		System.out.println("start-----------"+start);
		System.out.println("end-----------"+end);

		if ((end - start) % 2 == 0) { // odd number of elements
			return (a[(end + start) / 2]);
		} else { // even number of elements
			double value1 = a[(end + start) / 2];
			double value2 = a[(end + start) / 2 + 1];
			return (value1 + value2) / 2.0;
		}
	}

	public static String calculateRange(String str)
	{


		String[] strArray = str.split(",");

		int size = strArray.length;
		double [] arr = new double [size];
		for(int i=0; i<size; i++) {
			arr[i] = Double.parseDouble(strArray[i]);
		}

		int max = (int) getMax(arr, size);
		int min = (int)getMin(arr, size);
		int range = (int)(max - min);

		return range+" ("+min+" - "+max+")";
	}

	static double getMin(double arr[], int n)
	{
		double res = arr[0];
		for (int i = 1; i < n; i++)
			res = Math.min(res, arr[i]);
		return res;
	}

	// Function to return the maximum element from the array
	static double getMax(double arr[], int n)
	{
		double res = arr[0];
		for (int i = 1; i < n; i++)
			res = Math.max(res, arr[i]);
		return res;
	}

 	@Override
	@SuppressWarnings("unchecked")
	public String saveCustomReportsData(String branchName, JSONObject requestJson) {

		System.out.println("requestJson--------"+requestJson);
 Long crID = null;

		try {
			crID = Long.parseLong(requestJson.getString("crID"));

		}catch(Exception e){
		}

		CustomReports customReports = new CustomReports();
		customReports.setCrID(crID);

 		ObjectMapper mapper = new ObjectMapper();
		try {
			customReports = mapper.readValue(requestJson.toString(), CustomReports.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (!BasicUtils.isEmpty(crID)) {
				inicuPostgresUtililty.updateObject(customReports);
			} else{
				inicuPostgresUtililty.saveObject(customReports);
			}
			//inicuDao.saveObject(customReports);
  				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		return "";
	}



	@Override
	public List<CustomReports> getAllCustomReports( String branchName) {
 		List<CustomReports> returnObj = new ArrayList<CustomReports>();


 		StringBuilder query = new StringBuilder("select obj from CustomReports obj   order by crName ");



		returnObj = inicuDao
				.getListFromMappedObjQuery(query.toString());


		return returnObj;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public DistributionCurvesPOJO getDistributionCurves(String fromDateStr, String toDateStr, String branchName, String uhid) {
		System.out.println("********TIME CHECK********" + fromDateStr);
		DistributionCurvesPOJO returnList = new DistributionCurvesPOJO();
		HashMap<String, VitalTracker> vitalTrackerMap=new HashMap<>();
		try {
			
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
			Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));

			List<BabyDetail> uhidCompleteList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyDetailList(uhid));
			
	

			if (!BasicUtils.isEmpty(uhidCompleteList)) {
				BabyDetail obj = uhidCompleteList.get(0);

				DistributionCurvesPOJO distributionObjHR = new DistributionCurvesPOJO();
				String queryHr = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
					    + uhid.trim() + "' " + "AND starttime >= '" + fromDate
					    + "' AND starttime <= '" + toDate + "' AND heartrate is not null"
					    + " order by heartrate";
				
				List<DeviceMonitorDetail> resultSetHr = patientDao
					    .getListFromMappedObjNativeQuery(queryHr);
				
				String queryPr = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
					    + uhid.trim() + "' " + "AND starttime >= '" + fromDate
					    + "' AND starttime <= '" + toDate + "' AND pulserate is not null"
					    + " order by pulserate";
				
				List<DeviceMonitorDetail> resultSetPR = patientDao
					    .getListFromMappedObjNativeQuery(queryPr);
				
				String querySPO2 = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
					    + uhid.trim() + "' " + "AND starttime >= '" + fromDate
					    + "' AND starttime <= '" + toDate + "' AND spo2 is not null"
					    + " order by spo2";
				
				List<DeviceMonitorDetail> resultSetSPO2 = patientDao
					    .getListFromMappedObjNativeQuery(querySPO2);
				
				String queryRR = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
					    + uhid.trim() + "' " + "AND starttime >= '" + fromDate
					    + "' AND starttime <= '" + toDate + "' AND ecg_resprate is not null "
					    + " order by ecg_resprate";
				
				List<DeviceMonitorDetail> resultSetRR = patientDao
					    .getListFromMappedObjNativeQuery(queryRR);
				
				String queryMBP = "SELECT obj FROM DeviceMonitorDetail as obj WHERE " + "uhid ='"
					    + uhid.trim() + "' " + "AND starttime >= '" + fromDate
					    + "' AND starttime <= '" + toDate + "' AND mean_bp is not null "
					    + " order by mean_bp";
				
				List<DeviceMonitorDetail> resultSetMBP = patientDao
					    .getListFromMappedObjNativeQuery(queryMBP);
				
				Double minHr = 999.0;
				Double maxHr = -1.0;
				Integer minyaxis = -1;
				Integer maxyaxis = 999;
				Integer totalCount = 0;
				HashMap<Double, Double> hrMap = new HashMap<Double, Double>();
				
				Double firstRangeHR = 0.0;
				Double secondRangeHR = 0.0;
				Double thirdRangeHR = 0.0;
				Double fourthRangeHR = 0.0;
				Double fifthRangeHR = 0.0;
				Double sixthRangeHR = 0.0;

			    for (DeviceMonitorDetail dev : resultSetHr) {
				  	if (!BasicUtils.isEmpty(dev.getHeartrate())) {

				  		Double hr = Double.parseDouble(dev.getHeartrate());
				  		totalCount++;
				  		if(minHr > hr) {
				  			minHr = hr;
				  		}
				  		if(maxHr < hr) {
				  			maxHr = hr;
				  		}
				  		
				  		if(hrMap.containsKey(hr)) {
							Double value = hrMap.get(hr);
							value = value + 1;
							hrMap.put(hr, value);
				  		}else {
				  			hrMap.put(hr, 1.0);
				  		}
					}
				}
			    
			    if(minHr != 999 && maxHr != -1 && totalCount > 0) {
			    	List<List<Double>> dataHr = new ArrayList<List<Double>>();
					List<Double> pair = new ArrayList<Double>();
					for(Double key : hrMap.keySet()) {
						pair = new ArrayList<Double>();
						Double val = hrMap.get(key);
					
						pair.add(key);
						double finalVal = (double) Math.round(((val/totalCount)*100)*100)/100;
						pair.add((double)Math.round(((val/totalCount)*100)*100)/100);
						dataHr.add(pair);
						
						if(key <= 40) {
							firstRangeHR += finalVal;
						}else if(key > 40 && key <= 70) {
							secondRangeHR += finalVal;
						}else if(key > 70 && key <= 120) {
							thirdRangeHR += finalVal;
						}else if(key > 120 && key <= 170) {
							fourthRangeHR += finalVal;
						}else if(key > 170 && key <= 200) {
							fifthRangeHR += finalVal;
						}else if(key > 200) {
							sixthRangeHR += finalVal;
						}
					}
					
					DistributionData distributionDataHR = new DistributionData();
					distributionDataHR.setData(dataHr);
					distributionDataHR.setYmin(minHr);
					distributionDataHR.setYmax(maxHr);
					distributionDataHR.setXmax(100.0);
					distributionDataHR.setXmin(0.0);
					
					distributionDataHR.setFifthRange((double) Math.round(fifthRangeHR*100)/100);
					distributionDataHR.setSixthRange((double) Math.round(sixthRangeHR*100)/100);
					distributionDataHR.setFirstRange((double) Math.round(firstRangeHR*100)/100);
					distributionDataHR.setSecondRange((double) Math.round(secondRangeHR*100)/100);
					distributionDataHR.setThirdRange((double) Math.round(thirdRangeHR*100)/100);
					distributionDataHR.setFourthRange((double) Math.round(fourthRangeHR*100)/100);

				    returnList.setHeartRate(distributionDataHR);
			    }
			    
			    Double firstRangePR = 0.0;
				Double secondRangePR = 0.0;
				Double thirdRangePR = 0.0;
				Double fourthRangePR = 0.0;
				Double fifthRangePR = 0.0;
				Double sixthRangePR = 0.0;
			    
			    Double minPr = 999.0;
				Double maxPr = -1.0;
				minyaxis = -1;
				maxyaxis = 999;
			    totalCount = 0;
				HashMap<Double, Double> prMap = new HashMap<Double, Double>();
			
			    for (DeviceMonitorDetail dev : resultSetPR) {
				  	if (!BasicUtils.isEmpty(dev.getPulserate())) {

				  		Double pr = Double.parseDouble(dev.getPulserate());
				  		totalCount++;
				  		if(minPr > pr) {
				  			minPr = pr;
				  		}
				  		if(maxPr < pr) {
				  			maxPr = pr;
				  		}

				  		
				  		if(prMap.containsKey(pr)) {
							Double value = prMap.get(pr);
							value = value + 1;
							prMap.put(pr, value);
				  		}else {
				  			prMap.put(pr, 1.0);
				  		}
					}
				}
			    
			    if(minPr != 999 && maxPr != -1 && totalCount > 0) {
			    	List<List<Double>> dataPr = new ArrayList<List<Double>>();
					List<Double> pair = new ArrayList<Double>();
					for(Double key : prMap.keySet()) {
						pair = new ArrayList<Double>();
						Double val = prMap.get(key);
					
						pair.add(key);
						
						double finalVal = (double) Math.round(((val/totalCount)*100)*100)/100;
						pair.add((double)Math.round(((val/totalCount)*100)*100)/100);
						dataPr.add(pair);
						
						if(key <= 40) {
							firstRangePR += finalVal;
						}else if(key > 40 && key <= 70) {
							secondRangePR += finalVal;
						}else if(key > 70 && key <= 120) {
							thirdRangePR += finalVal;
						}else if(key > 120 && key <= 170) {
							fourthRangePR += finalVal;
						}else if(key > 170 && key <= 200) {
							fifthRangePR += finalVal;
						}else if(key > 200) {
							sixthRangePR += finalVal;
						}
					}
					
					DistributionData distributionDataPR = new DistributionData();
					distributionDataPR.setData(dataPr);
					distributionDataPR.setYmin(minPr);
					distributionDataPR.setYmax(maxPr);
					distributionDataPR.setXmax(100.0);
					distributionDataPR.setXmin(0.0);
					
					distributionDataPR.setFifthRange((double) Math.round(fifthRangePR*100)/100);
					distributionDataPR.setSixthRange((double) Math.round(sixthRangePR*100)/100);
					distributionDataPR.setFirstRange((double) Math.round(firstRangePR*100)/100);
					distributionDataPR.setSecondRange((double) Math.round(secondRangePR*100)/100);
					distributionDataPR.setThirdRange((double) Math.round(thirdRangePR*100)/100);
					distributionDataPR.setFourthRange((double) Math.round(fourthRangePR*100)/100);

				    returnList.setPulserate(distributionDataPR);
			    }
			    
			    Double firstRangeSpo2 = 0.0;
				Double secondRangeSpo2 = 0.0;
				Double thirdRangeSpo2 = 0.0;
				Double fourthRangeSpo2 = 0.0;
				Double fifthRangeSpo2 = 0.0;
			    
			    Double minSpo2 = 999.0;
				Double maxSpo2 = -1.0;
				minyaxis = -1;
				maxyaxis = 999;
			    totalCount = 0;
				HashMap<Double, Double> spo2Map = new HashMap<Double, Double>();
			
			    for (DeviceMonitorDetail dev : resultSetSPO2) {
				  	if (!BasicUtils.isEmpty(dev.getSpo2())) {

				  		Double spo2 = Double.parseDouble(dev.getSpo2());
				  		totalCount++;
				  		if(minSpo2 > spo2) {
				  			minSpo2 = spo2;
				  		}
				  		if(maxSpo2 < spo2) {
				  			maxSpo2 = spo2;
				  		}

				  		
				  		if(spo2Map.containsKey(spo2)) {
							Double value = spo2Map.get(spo2);
							value = value + 1;
							spo2Map.put(spo2, value);
				  		}else {
				  			spo2Map.put(spo2, 1.0);
				  		}
					}
				}
			    
			    if(minSpo2 != 999 && maxSpo2 != -1 && totalCount > 0) {
			    	List<List<Double>> dataSpo2 = new ArrayList<List<Double>>();
					List<Double> pair = new ArrayList<Double>();
					for(Double key : spo2Map.keySet()) {
						pair = new ArrayList<Double>();
						Double val = spo2Map.get(key);
					
						pair.add(key);
						double finalVal = (double) Math.round(((val/totalCount)*100)*100)/100;
						pair.add((double)Math.round(((val/totalCount)*100)*100)/100);
						dataSpo2.add(pair);
						
						if(key <= 80) {
							firstRangeSpo2 += finalVal;
						}else if(key > 80 && key <= 85) {
							secondRangeSpo2 += finalVal;
						}else if(key > 85 && key <= 90) {
							thirdRangeSpo2 += finalVal;
						}else if(key > 90 && key <= 95) {
							fourthRangeSpo2 += finalVal;
						}else if(key > 95) {
							fifthRangeSpo2 += finalVal;
						}
					}
					
					DistributionData distributionDataSpo2 = new DistributionData();
					distributionDataSpo2.setData(dataSpo2);
					distributionDataSpo2.setYmin(minSpo2);
					distributionDataSpo2.setYmax(maxSpo2);
					distributionDataSpo2.setXmax(100.0);
					distributionDataSpo2.setXmin(0.0);
					
					distributionDataSpo2.setFifthRange((double) Math.round(fifthRangeSpo2*100)/100);
					distributionDataSpo2.setFirstRange((double) Math.round(firstRangeSpo2*100)/100);
					distributionDataSpo2.setSecondRange((double) Math.round(secondRangeSpo2*100)/100);
					distributionDataSpo2.setThirdRange((double) Math.round(thirdRangeSpo2*100)/100);
					distributionDataSpo2.setFourthRange((double) Math.round(fourthRangeSpo2*100)/100);

				    returnList.setSpo2Rate(distributionDataSpo2);
			    }
			    
			    Double minRR = 999.0;
				Double maxRR = -1.0;
				minyaxis = -1;
				maxyaxis = 999;
			    totalCount = 0;
				HashMap<Double, Double> rrMap = new HashMap<Double, Double>();
			
				Double firstRangeRR = 0.0;
				Double secondRangeRR = 0.0;
				Double thirdRangeRR = 0.0;
				Double fourthRangeRR = 0.0;
				Double fifthRangeRR = 0.0;
				Double sixthRangeRR = 0.0;

			    for (DeviceMonitorDetail dev : resultSetRR) {
				  	if (!BasicUtils.isEmpty(dev.getEcgResprate())) {

				  		Double rr = Double.parseDouble(dev.getEcgResprate());
				  		totalCount++;
				  		if(minRR > rr) {
				  			minRR = rr;
				  		}
				  		if(maxRR < rr) {
				  			maxRR = rr;
				  		}

				  		
				  		if(rrMap.containsKey(rr)) {
							Double value = rrMap.get(rr);
							value = value + 1;
							rrMap.put(rr, value);
				  		}else {
				  			rrMap.put(rr, 1.0);
				  		}
					}
				}
			    
			    if(minRR != 999 && maxRR != -1 && totalCount > 0) {
			    	List<List<Double>> dataRR = new ArrayList<List<Double>>();
					List<Double> pair = new ArrayList<Double>();
					for(Double key : rrMap.keySet()) {
						pair = new ArrayList<Double>();
						Double val = rrMap.get(key);
					
						pair.add(key);	
						double finalVal = (double) Math.round(((val/totalCount)*100)*100)/100;
						pair.add((double)Math.round(((val/totalCount)*100)*100)/100);
						dataRR.add(pair);
						
						if(key <= 10) {
							firstRangeRR += finalVal;
						}else if(key > 10 && key <= 20) {
							secondRangeRR += finalVal;
						}else if(key > 20 && key <= 30) {
							thirdRangeRR += finalVal;
						}else if(key > 30 && key <= 40) {
							fourthRangeRR += finalVal;
						}else if(key > 40 && key <= 50) {
							fifthRangeRR += finalVal;
						}else if(key > 50) {
							sixthRangeRR += finalVal;
						}
					}
					
					DistributionData distributionDataRR = new DistributionData();
					distributionDataRR.setData(dataRR);
					distributionDataRR.setYmin(minRR);
					distributionDataRR.setYmax(maxRR);
					distributionDataRR.setXmax(100.0);
					distributionDataRR.setXmin(0.0);
					
					distributionDataRR.setFifthRange((double) Math.round(fifthRangeRR*100)/100);
					distributionDataRR.setSixthRange((double) Math.round(sixthRangeRR*100)/100);
					distributionDataRR.setFirstRange((double) Math.round(firstRangeRR*100)/100);
					distributionDataRR.setSecondRange((double) Math.round(secondRangeRR*100)/100);
					distributionDataRR.setThirdRange((double) Math.round(thirdRangeRR*100)/100);
					distributionDataRR.setFourthRange((double) Math.round(fourthRangeRR*100)/100);

				    returnList.setRespiratoryRate(distributionDataRR);
			    }
			    
			    Double minBP = 999.0;
				Double maxBP = -1.0;
				minyaxis = -1;
				maxyaxis = 999;
			    totalCount = 0;
				HashMap<Double, Double> bpMap = new HashMap<Double, Double>();
			
				Double firstRangeBP = 0.0;
				Double secondRangeBP = 0.0;
				Double thirdRangeBP = 0.0;
				Double fourthRangeBP = 0.0;
		
			    for (DeviceMonitorDetail dev : resultSetMBP) {
				  	if (!BasicUtils.isEmpty(dev.getMeanBp())) {
		
				  		Double bp = Double.parseDouble(dev.getMeanBp());
				  		totalCount++;
				  		if(minBP > bp) {
				  			minBP = bp;
				  		}
				  		if(maxBP < bp) {
				  			maxBP = bp;
				  		}
		
				  		
				  		if(bpMap.containsKey(bp)) {
							Double value = bpMap.get(bp);
							value = value + 1;
							bpMap.put(bp, value);
				  		}else {
				  			bpMap.put(bp, 1.0);
				  		}
					}
				}
			    
			    if(minBP != 999 && maxBP != -1 && totalCount > 0) {
			    	List<List<Double>> dataBP = new ArrayList<List<Double>>();
					List<Double> pair = new ArrayList<Double>();
					for(Double key : bpMap.keySet()) {
						pair = new ArrayList<Double>();
						Double val = bpMap.get(key);
					
						pair.add(key);	
						double finalVal = (double) Math.round(((val/totalCount)*100)*100)/100;
						pair.add((double)Math.round(((val/totalCount)*100)*100)/100);
						dataBP.add(pair);
						
						if(key <= 60) {
							firstRangeBP += finalVal;
						}else if(key > 60 && key < 100) {
							secondRangeBP += finalVal;
						}else if(key >= 100 && key < 140) {
							thirdRangeBP += finalVal;
						}else if(key > 140) {
							fourthRangeBP += finalVal;
						}
					}
					
					DistributionData distributionDataBP = new DistributionData();
					distributionDataBP.setData(dataBP);
					distributionDataBP.setYmin(minBP);
					distributionDataBP.setYmax(maxBP);
					distributionDataBP.setXmax(100.0);
					distributionDataBP.setXmin(0.0);
					
					distributionDataBP.setFirstRange((double) Math.round(firstRangeBP*100)/100);
					distributionDataBP.setSecondRange((double) Math.round(secondRangeBP*100)/100);
					distributionDataBP.setThirdRange((double) Math.round(thirdRangeBP*100)/100);
					distributionDataBP.setFourthRange((double) Math.round(fourthRangeBP*100)/100);
		
				    returnList.setMeanBPRate(distributionDataBP);
			    }

			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"vitalTracker Data", BasicUtils.convertErrorStacktoString(e));
		}
		return returnList;
	}

	public  int getAssessmentCountInaPeriod(Timestamp anaStarttime, Timestamp anaEndTime,String uhid ){

 		long respHours = 0;

		long totalAssessmentCount = 0;

		long aHours = 0;

		long pHours = 0;


		System.out.println("getAssessmentCountInaPeriod ----------");


		try {

			StringBuilder query = new StringBuilder("SELECT jaundicestatus,assessment_time FROM sa_jaundice where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			List<Object[]> activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
            aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
 			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);

			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_rds where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
								.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_apnea where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_pphn where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);



			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_resp_pneumothorax where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);



//			query = new StringBuilder("SELECT  eventstatus , reassestime FROM sa_resp_bpd where ")
//					.append(" uhid='").append(uhid).append("'")
//					.append(" and reassestime <='").append(anaEndTime).append("'")
//					.append(" order by  reassestime");
//
//
//			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
//			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
//			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
//			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
//			System.out.println("aHours---------------------------"+aHours);
//			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_sepsis where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_vap where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_nec where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_infection_clabsi where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);





			query = new StringBuilder("SELECT  eventstatus , timeofassessment FROM sa_infection_intrauterine where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and timeofassessment <='").append(anaEndTime).append("'")
					.append(" order by  timeofassessment desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);





			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_asphyxia where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);



			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_encephalopathy where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_hydrocephalus where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_cns_seizures where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_hypoglycemia where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  renal_status , assessment_time FROM sa_renalfailure where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_miscellaneous where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  eventstatus , assessment_time FROM sa_miscellaneous_2 where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  feed_intolerance_status , assessment_time FROM sa_feed_intolerance where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);


			query = new StringBuilder("SELECT  shockstatus , assessment_time FROM sa_shock where ")
					.append(" uhid='").append(uhid).append("'")
					.append(" and assessment_time <='").append(anaEndTime).append("'")
					.append(" order by  assessment_time desc");


			activeAssessmentList = inicuDao.getListFromNativeQuery(query.toString());
			aHours = 		 getAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
pHours = 		 getPassiveAssessmentHours( anaStarttime,  anaEndTime, activeAssessmentList );
			System.out.println("totalAssessmentCount before---------------------------"+totalAssessmentCount);
			totalAssessmentCount+=	 Math.ceil(aHours/8.0);
totalAssessmentCount+=	 Math.ceil(pHours/24.0);
			System.out.println("aHours---------------------------"+aHours);
			System.out.println("totalAssessmentCount after---------------------------"+totalAssessmentCount);




		}catch(Exception e ){


		}

return (int) totalAssessmentCount ;

	}


public long	getAssessmentHours( Timestamp anaStarttime, Timestamp anaEndTime,List<Object[]> activeAssessmentList  ){

		Timestamp  calStarttime = anaStarttime;

		Timestamp calEndtime = anaEndTime;

		Timestamp asessmentTime = null;

		long aHours = 0;

		if (!BasicUtils.isEmpty(activeAssessmentList)) {
			Iterator<Object[]> itr = activeAssessmentList.iterator();
			while (itr.hasNext()) {

				Object[] obj = itr.next();
				System.out.println("obj[0]=================="+obj[0]+"---------obj[1]"+obj[1]);

				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					java.util.Date parsedDate = dateFormat.parse(obj[1].toString());
					asessmentTime = new java.sql.Timestamp(parsedDate.getTime());

					System.out.println("asessmentTime=================="+asessmentTime);

				} catch(Exception e) { //this generic but you can control another types of exception
					// look the origin of excption
				}

				if("yes".equalsIgnoreCase((String)(obj[0]))){
					System.out.println("inside active===");

					if(asessmentTime.getTime() < anaStarttime.getTime()){
						calStarttime=anaStarttime;
					}
					else calStarttime=asessmentTime;

					aHours += ((calEndtime.getTime() - calStarttime.getTime()) / (1000 * 60 * 60));

					//long days = ((endTime.getTime() - startTime.getTime()+60000) / (1000 * 60 * 60));
					calEndtime = calStarttime;


				} else 				if("no".equalsIgnoreCase((String)(obj[0]))){


					System.out.println("inside no===");

					calEndtime = asessmentTime;

				}else 				if("inactive".equalsIgnoreCase((String)(obj[0]))){


					if(asessmentTime.getTime() <= anaStarttime.getTime()) break;


				}


				System.out.println("calEndtime.getTime() in loop----------"+calEndtime.getTime());

				System.out.println("calStarttime.getTime() in loop----------"+calStarttime.getTime());
				System.out.println("aHours in loop----------"+aHours);

				if(calEndtime.getTime() <= anaStarttime.getTime()) break;



			}

		}

		return aHours;
	}



	public long	getPassiveAssessmentHours( Timestamp anaStarttime, Timestamp anaEndTime,List<Object[]> activeAssessmentList  ){

		Timestamp  calStarttime = anaStarttime;

		Timestamp calEndtime = anaEndTime;

		Timestamp asessmentTime = null;

		long aHours = 0;

		if (!BasicUtils.isEmpty(activeAssessmentList)) {
			Iterator<Object[]> itr = activeAssessmentList.iterator();
			while (itr.hasNext()) {

				Object[] obj = itr.next();
				System.out.println("obj[0]=================="+obj[0]+"---------obj[1]"+obj[1]);

				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					java.util.Date parsedDate = dateFormat.parse(obj[1].toString());
					asessmentTime = new java.sql.Timestamp(parsedDate.getTime());

					System.out.println("asessmentTime=================="+asessmentTime);

				} catch(Exception e) { //this generic but you can control another types of exception
					// look the origin of excption
				}

				if("no".equalsIgnoreCase((String)(obj[0]))){
					System.out.println("inside passive===");

					if(asessmentTime.getTime() < anaStarttime.getTime())calStarttime=anaStarttime;
					else calStarttime=asessmentTime;

					//long days = ((endTime.getTime() - startTime.getTime()+60000) / (1000 * 60 * 60));


				} else 				if("inactive".equalsIgnoreCase((String)(obj[0]))){


					System.out.println("inside no===");

					calEndtime = asessmentTime;

				}
				if("no".equalsIgnoreCase((String)(obj[0])) || "inactive".equalsIgnoreCase((String)(obj[0]))) {
					if (calEndtime.getTime() <= anaStarttime.getTime()) break;

					aHours += ((calEndtime.getTime() - calStarttime.getTime()) / (1000 * 60 * 60));

					calEndtime = calStarttime;
					System.out.println("passive hrs in loop----------" + aHours);
				}

				if("yes".equalsIgnoreCase((String)(obj[0]))) {
					break;
				}

			}

		}

		return aHours;
	}

}

