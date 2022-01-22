package com.inicu.postgres.serviceImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.mail.Message.RecipientType;

import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.UserPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.BabyPrescriptionObject;
import com.inicu.models.DeletedNursingMedicationModel;
import com.inicu.models.GivenMedicineTimeObj;
import com.inicu.models.KeyValueObj;
import com.inicu.models.MedicalAdministrationObj;
import com.inicu.models.MedicineDateTimeObj;
import com.inicu.models.MedicineDropDownsPOJO;
import com.inicu.models.NursingMedicationModel;
import com.inicu.models.NursingNotesPojo;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PrescriptionDao;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.PrescriptionService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.CalendarUtility;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	PrescriptionDao prescriptionDao;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	LogsService logService;

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	UserPanelService userPanel;
	
	String babyId;

	@Override
	public BabyPrescriptionObject getBabyPrescription(String uhid, String loggedUser, Integer gestationDays, Integer gestationWeeks, String dateStr, Integer dol, String assessmentName,
													  String branchName) throws InicuDatabaseExeption {
		BabyPrescriptionObject prescriptionObject = new BabyPrescriptionObject();
		prescriptionObject.setCurrentPrescription(new BabyPrescription());
		try {
			
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parsedDate = dateFormat.parse(dateStr);
			Timestamp dateStr1 = new java.sql.Timestamp(parsedDate.getTime());
			Timestamp dateStrOffset = new Timestamp(dateStr1.getTime() - offset);
			
			Float weight = 1000f;
			String babyVisitListQuery = "select obj from BabyVisit as obj where uhid='" + uhid + "' order by visitdate desc";
			List<BabyVisit> babyVisitDetailList = inicuDao.getListFromMappedObjQuery(babyVisitListQuery);
			if(!BasicUtils.isEmpty(babyVisitDetailList)) {
				weight = (babyVisitDetailList.get(0).getWeightForCal());
			}
			
			MedicineDropDownsPOJO dropDowns = getPrescriptionDropDowns(uhid, gestationDays, gestationWeeks, dol, weight, assessmentName);
			if (dropDowns != null) {
				prescriptionObject.setDropDowns(dropDowns);
			}

			List<BabyPrescription> activePrescription = getPrescription(uhid, BasicConstants.ACTIVE_PRESCRIPTION);
			if (activePrescription != null) {
				// check for all active medication it is executed by the nurse or not
				String medicationExecuedQuery="select obj from MedicationPreparation as obj where uhid='" + uhid + "'";
				List<MedicationPreparation> nursingMedicationList =inicuDao.getListFromMappedObjQuery(medicationExecuedQuery);
				for(BabyPrescription babyPrescription: activePrescription){
					for(MedicationPreparation nursingMedicationObject:nursingMedicationList){
						Long baby_presid= Long.valueOf(nursingMedicationObject.getBaby_presid());
						if(babyPrescription.getBabyPresid()!=null && ((babyPrescription.getBabyPresid().longValue() == baby_presid) || (babyPrescription.getRefBabyPresid()!=null && babyPrescription.getRefBabyPresid().longValue() == baby_presid)) &&
								babyPrescription.getBolus()==false && babyPrescription.getFreq_type().equalsIgnoreCase("Continuous")){
							babyPrescription.setMedicationExecuted(true);
							break;
						}
					}
				}
				prescriptionObject.setActivePrescription(activePrescription);
			}

			List<BabyPrescription> pastPrescription = getPrescription(uhid, BasicConstants.PAST_PRESCRIPTION);
			if (pastPrescription != null) {
				prescriptionObject.setPastPrescriptions(pastPrescription);
			}
			
			Timestamp todayDate,tomorrowDate;
//			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
//					- TimeZone.getDefault().getRawOffset();
			todayDate = new Timestamp(System.currentTimeMillis());
			//Set Tommorow Date's Time to 8AM
			todayDate.setHours(6);
			todayDate.setMinutes(0);
			todayDate.setSeconds(0);
			todayDate.setNanos(0);
			todayDate = new Timestamp (todayDate.getTime() - offset);
			
			tomorrowDate = new Timestamp(System.currentTimeMillis() + 86400000);
			//Set Tommorow Date's Time to 8AM
			tomorrowDate.setHours(6);
			tomorrowDate.setMinutes(0);
			tomorrowDate.setSeconds(0);
			tomorrowDate.setNanos(0);
			tomorrowDate = new Timestamp (tomorrowDate.getTime() - offset);

//			String babyFeedDetailListQuery = "select obj from BabyfeedDetail as obj where uhid='" + uhid
//					+ "' and entrydatetime BETWEEN '"  + todayDate + "' and '" + tomorrowDate+ "' order by entrydatetime desc";


			// only fetch the  last entry as per the time selected
			String babyFeedDetailListQuery = "select obj from BabyfeedDetail as obj where uhid='" + uhid
					+ "' and entrydatetime <='"+ dateStrOffset + "' order by entrydatetime desc";

			List<BabyfeedDetail> babyFeedDetailList = inicuDao.getListFromMappedObjQuery(babyFeedDetailListQuery);
			if(!BasicUtils.isEmpty(babyFeedDetailList)) {
				prescriptionObject.setBabyfeedDetailList(babyFeedDetailList.get(0));
			}

//			String querygetActiveMeds = "select obj from BabyPrescription as obj where isactive = 'true' and date(startdate) <= '" + dateStr + "' and uhid = '" + uhid + "' order by date(startdate) desc";
//			List<BabyPrescription> activeMeds = inicuDao.getListFromMappedObjQuery(querygetActiveMeds);
//			prescriptionObject.setActiveMedsList(activeMeds);
			String querygetActiveMeds = "select obj from BabyPrescription as obj where isactive = 'true' and startdate <= '" + dateStrOffset + "' and uhid = '" + uhid + "' order by startdate desc";
			String querygetActiveMeds1 = "select obj from BabyPrescription as obj where isactive = 'false' and startdate <= '" + dateStrOffset + "' and uhid = '" + uhid + "' and enddate >= '" + dateStrOffset + "'";
 			List<BabyPrescription> activeMeds = inicuDao.getListFromMappedObjQuery(querygetActiveMeds);

 			if (!BasicUtils.isEmpty(activeMeds)) {
				List<KeyValueObj> signatureImageData = null;
				for (BabyPrescription babyPrescription : activeMeds) {
					String loggedInUser = babyPrescription.getLoggeduser();
					try {
						signatureImageData = userPanel.getImageData(loggedInUser, branchName);
						babyPrescription.setSignatureImage(signatureImageData);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			String babyDetailQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "' and admissionstatus ='true' order by creationtime desc";
			List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailQuery);
			if(!BasicUtils.isEmpty(babyDetailList)) {
				String consultantName = babyDetailList.get(0).getAdmittingdoctor();
				if(!BasicUtils.isEmpty(consultantName)) {
					String[] nameArr = consultantName.split(" ");
					StringBuilder userQuery = new StringBuilder("select obj from User as obj where firstname='" + nameArr[0] + "'");
					if (nameArr.length > 1 && !BasicUtils.isEmpty(nameArr[1])) {
						userQuery.append(" and lastname = '" + nameArr[1] + "'");
					}
					List<User> userList = inicuDao.getListFromMappedObjQuery(userQuery.toString());
					if(!BasicUtils.isEmpty(userList)) {
						String consultantUsername = userList.get(0).getUserName();
						if(!BasicUtils.isEmpty(consultantUsername)) {
							List<KeyValueObj> consultantSignatureImageData = userPanel.getImageData(consultantUsername, branchName);
							prescriptionObject.setConsultantSignatureImage(consultantSignatureImageData);
						}
					}
				}
			}

 			List<BabyPrescription> inactiveMeds = inicuDao.getListFromMappedObjQuery(querygetActiveMeds1);
 			List<BabyPrescription> allMeds = new ArrayList<BabyPrescription>();
 			allMeds.addAll(activeMeds);
 			allMeds.addAll(inactiveMeds);
 			prescriptionObject.setActiveMedsList(allMeds);
 			
			
			String existingCheckSql = "select obj from BabyVisit as obj where uhid='" + uhid
					+ "' and admission_entry='true'";
			List<BabyVisit> existingCheck = inicuDao.getListFromMappedObjQuery(existingCheckSql);

			//Set EN Additive Brand Names
			List<RefEnAddtivesBrand> enAddtivesBrandName = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.ENAdditiveBrandName);
			prescriptionObject.setEnAddtivesBrandNameList(enAddtivesBrandName);
			
			//set Weight for Calculation
			String queryChildWeightForCal = "select weight_for_cal from baby_visit obj where uhid ='" + uhid
			+ "' order by creationtime desc";
			List<Float> resultWeightForCalSet = inicuDao.getListFromNativeQuery(queryChildWeightForCal);
			Float prevWeightForCal = null;
			if (!BasicUtils.isEmpty(resultWeightForCalSet)) {
				prevWeightForCal = resultWeightForCalSet.get(0);
			}
			prescriptionObject.setWeightForCal(prevWeightForCal);

			StringBuilder loggedUserFullName = null;
			String userListQuery = "select obj from User as obj where username='" + loggedUser + "' and branchname = '"
					+ branchName + "'";
			List<User> userList = inicuDao.getListFromMappedObjQuery(userListQuery);
			if(!BasicUtils.isEmpty(userList)) {
				loggedUserFullName = new StringBuilder(userList.get(0).getFirstName());
				if (!BasicUtils.isEmpty(userList.get(0).getLastName())) {
					loggedUserFullName.append(" " + userList.get(0).getLastName());
				}

				String roleId = "";
				String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + loggedUser
						+ "' and branchname = '" + branchName + "'";
				List<UserRolesTable> userRoleList = inicuDao.getListFromMappedObjQuery(userObjQuery);
				if (!BasicUtils.isEmpty(userRoleList)) {
					roleId = userRoleList.get(0).getRoleId();
				}

				if(!BasicUtils.isEmpty(roleId)) {
					if ("2".equalsIgnoreCase(roleId) || "3".equalsIgnoreCase(roleId)) {
						loggedUserFullName.insert(0, "Dr. ");
					}
				}

				prescriptionObject.setLoggedUserFullName(loggedUserFullName.toString());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"GET_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}
		return prescriptionObject;
	}

	@SuppressWarnings("unchecked")
	private MedicineDropDownsPOJO getPrescriptionDropDowns(String uhid, Integer gestationDays, Integer gestationWeeks, Integer dol, Float weight, String assessmentName) {

		// active medicines given to that patient.
		List<Object[]> activeMedList = new ArrayList<>();
		if (!BasicUtils.isEmpty(uhid)) {
			String activeMed = "select medicinename,bolus from baby_prescription where uhid='" + uhid.trim()
					+ "' and isactive = true";
			List<Object[]> result = prescriptionDao.getListFromNativeQuery(activeMed);

			if (!BasicUtils.isEmpty(result)) {
				activeMedList.addAll(result);
			}
		}

		MedicineDropDownsPOJO dropDownsList = new MedicineDropDownsPOJO();
		
		List<Medicines> changedMedList = new ArrayList<>();
		List<Medicines> masterMedList = new ArrayList<>();
		List<RefMedicine> recommendedMedList = new ArrayList<>();
		List<Medicines> medicinesList = new ArrayList<>();
		List<Medicines> medicinesList1 = new ArrayList<>();
		List<Medicines> medicinesList2 = new ArrayList<>();
		List<Medicines> typeList = new ArrayList<>();

		if(!BasicUtils.isEmpty(assessmentName)) {
			
			
			String queryGetMedicineRef = "select obj from Medicines obj where isactive='true' and ( (SUBSTRING(assessment,'" + assessmentName + "') is not null) OR (SUBSTRING(assessment,'All') is not null) ) order by medname";
			medicinesList = prescriptionDao.getListFromMappedObjNativeQuery(queryGetMedicineRef);
			
//			if (!assessmentName.equalsIgnoreCase("Stable Notes")) {
//				String queryGetMedicineRef = "select obj from Medicines obj where isactive='true' order by medname";
//				medicinesList = prescriptionDao.getListFromMappedObjNativeQuery(queryGetMedicineRef);
//			}
//
//
//			if (assessmentName.equalsIgnoreCase("Stable Notes")) {
//				String queryGetMedicineRef = "select obj from Medicines obj where isactive='true' and medicationtype in ('TYPE0037','TYPE0035','TYPE0027','TYPE0032','TYPE0023') order by medname";
//				medicinesList1 = prescriptionDao.getListFromMappedObjNativeQuery(queryGetMedicineRef);
//				
//				String queryGetMedicineRef1 = "select obj from Medicines obj where isactive='true' and medid = 'MED0000000166'";
//				medicinesList2 = prescriptionDao.getListFromMappedObjNativeQuery(queryGetMedicineRef1);
//				
//				medicinesList.addAll(medicinesList1);
//				medicinesList.addAll(medicinesList2);
//			}
		}
		
		
		// iterate medicine list to generate medicine type
		if (!BasicUtils.isEmpty(medicinesList)) {
			for (Medicines m : medicinesList) {
				masterMedList.add(m);

				String medTypeKey = m.getMedicationtype();

				if (!BasicUtils.isEmpty(medTypeKey)) {
					String getMedType = "SELECT typevalue FROM ref_medtype WHERE typeid='" + medTypeKey.trim() + "'";
					List<String> result = prescriptionDao.getListFromNativeQuery(getMedType);
					if (!BasicUtils.isEmpty(result)) {
						String val = result.get(0).toString();
						m.setMedicineTypeStr(val);
					}
				}
				if(!BasicUtils.isEmpty(dol) && !BasicUtils.isEmpty(gestationWeeks) && !BasicUtils.isEmpty(weight)) {
				
					String queryRecommendedMed = "select obj from RefMedicine obj where isactive='true' and medid = '"
							+ m.getMedid() + "' and upper_dol >= " + dol + " and lower_dol <= " + dol + " and upper_gestation >= "
							+ gestationWeeks + " and lower_gestation <= " + gestationWeeks + " and lower_weight <= " + weight + " and upper_weight >= "
							+ weight + " and bolus is null and route is null and mode is null";
					List<RefMedicine> refMedicinesList = prescriptionDao.getListFromMappedObjNativeQuery(queryRecommendedMed);
					
					if (!BasicUtils.isEmpty(refMedicinesList)) {
						recommendedMedList.add(refMedicinesList.get(0));
					}
					
					String queryRecommendedMedDose = "select obj from RefMedicine obj where isactive='true' and medid = '"
							+ m.getMedid() + "' and upper_dol >= " + dol + " and lower_dol <= " + dol + " and upper_gestation >= "
							+ gestationWeeks + " and lower_gestation <= " + gestationWeeks + " and lower_weight <= " + weight + " and upper_weight >= "
							+ weight + " and (bolus is not null or route is not null or mode is not null)";
					List<RefMedicine> refMedicinesDoseTypeList = prescriptionDao.getListFromMappedObjNativeQuery(queryRecommendedMedDose);
					if (!BasicUtils.isEmpty(refMedicinesDoseTypeList)) {
						for(RefMedicine medicine : refMedicinesDoseTypeList)
							recommendedMedList.add(medicine);
					}
				}
				
				boolean isPresent = false;
				if (!BasicUtils.isEmpty(activeMedList)) {
					for (Object[] s : activeMedList) {
						String medicineName = s[0].toString();
						if (medicineName != null && medicineName.equalsIgnoreCase(m.getMedname()) && checkBothDoseTypeGiven(activeMedList,medicineName)) {
							isPresent = true;
						}
					}
				}

				if (isPresent) {
					continue;
				}

				changedMedList.add(m);
			}
		}

		String queryGetFrequencyRef = "select obj from RefMedfrequency obj order by frequency_int desc";
		List<Medicines> freqList = prescriptionDao.getListFromMappedObjNativeQuery(queryGetFrequencyRef);

		if(!BasicUtils.isEmpty(assessmentName)) {
			String queryGetMedTypeRef = "select obj from RefMedType obj order by typevalue";
			typeList = prescriptionDao.getListFromMappedObjNativeQuery(queryGetMedTypeRef);
		}

		String queryRecommendSol = "select obj from RefMedSolutions obj";
		List<RefMedSolutions> refSolutionsList = prescriptionDao.getListFromMappedObjNativeQuery(queryRecommendSol);
		
		if (!BasicUtils.isEmpty(refSolutionsList)) {
			dropDownsList.setSolutionsList(refSolutionsList);
		}
		
		//Get neofax data from the database
		
		dropDownsList.setFrequency(freqList);
		dropDownsList.setMedicines(changedMedList);
		dropDownsList.setMedtype(typeList);
		dropDownsList.setRecommendedNeofax(recommendedMedList);
		dropDownsList.setMasterMedicineList(masterMedList);
		
		return dropDownsList;
	}

	public boolean checkBothDoseTypeGiven(List<Object[]> activeMedicines, String currentMedicine){
		boolean medicineBolusTrue  = false;
		boolean medicineBolusFalse = false;

		for (Object[] medicine: activeMedicines) {
				String  medicinename = medicine[0].toString();
				boolean bolus = (boolean)medicine[1];

				if(currentMedicine.equalsIgnoreCase(medicinename) && bolus == true){
					medicineBolusTrue = true;
				}

				if(currentMedicine.equalsIgnoreCase(medicinename) && bolus == false) {
					medicineBolusFalse = true;
				}
		}

		if(medicineBolusTrue == true && medicineBolusFalse == true) {
			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BabyPrescription> getPrescription(String uhid, String prescriptionType) {
		Timestamp dateSql = new Timestamp(new Date().getTime());
		String queryPrescription = "SELECT obj FROM BabyPrescription obj";
		if (prescriptionType.equalsIgnoreCase(BasicConstants.ACTIVE_PRESCRIPTION)) {
			queryPrescription = queryPrescription + " where uhid='" + uhid + "' and (isactive ='true' or enddate >'"
					+ dateSql + "') order by startdate desc";
		} else if (prescriptionType.equalsIgnoreCase(BasicConstants.PAST_PRESCRIPTION)) {
			queryPrescription = queryPrescription + " where uhid='" + uhid + "' and (isactive ='false' and enddate <='"
					+ dateSql + "') order by startdate desc";
		} else if (prescriptionType.equalsIgnoreCase(BasicConstants.CURRENT_PRESCRIPTION)) {
			queryPrescription = queryPrescription + " where uhid='" + uhid + "' and startdate='" + dateSql
					+ "' and isactive='true' order by startdate desc";
		}
		return prescriptionDao.getListFromMappedObjNativeQuery(queryPrescription);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getRefObj(String query) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			List<Object[]> refList = prescriptionDao.getListFromNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null && !refList.isEmpty()) {
				Iterator<Object[]> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					Object[] obj = iterator.next();
					if (obj != null && obj[0] != null)
						keyValue.setKey(obj[0]);
					if (obj != null && obj[1] != null)
						keyValue.setValue(obj[1]);
					refKeyValueList.add(keyValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;

	}

	@Override
	public ResponseMessageWithResponseObject addBabyPrescription(List<BabyPrescription> prescriptionList)
			throws InicuDatabaseExeption {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		String uhid = null;
		String loggedUser = null;
		try {
			uhid = prescriptionList.get(0).getUhid();
			loggedUser = prescriptionList.get(0).getLoggeduser();

			inicuDao.saveMultipleObject(prescriptionList);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Prescription added successfully!!");
			response.setReturnedObject(getBabyPrescription(uhid, loggedUser, null, null, null, null,null, null));

			String action = BasicConstants.INSERT;
			logService.saveLog(prescriptionList.toString(), action, null, null, BasicConstants.BABY_PRESCRIPTION);
		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, null, uhid,
					"SAVE_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject deleteBabyPrescription(BabyPrescription prescription, String loggedUserId)
			throws InicuDatabaseExeption {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			prescription.setIsactive(false);
			inicuDao.saveObject(prescription);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Medication stopped!!");
			String desc = "";
			String action = BasicConstants.DELETE;
			logService.saveLog(desc, action, loggedUserId, null, BasicConstants.BABY_PRESCRIPTION);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setMessage(ex.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUserId, "",
					"DELETE_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public List<MedicalAdministrationObj> getGivenMedicinesDetails(String uhid, String loggedUser)
			throws InicuDatabaseExeption {

		Timestamp smallestDate = null;
		List<MedicalAdministrationObj> medicalAdministrationObj = new ArrayList<MedicalAdministrationObj>();
		List<BabyPrescription> prescribedMedicinesList = getPrescription(uhid, BasicConstants.ACTIVE_PRESCRIPTION);

		// get medical prescription for given prescription id and date
		if (!BasicUtils.isEmpty(prescribedMedicinesList)) {
			try {
				Iterator<BabyPrescription> iterator = prescribedMedicinesList.iterator();

				while (iterator.hasNext()) {
					MedicalAdministrationObj medicalAdministration = new MedicalAdministrationObj();
					BabyPrescription medicinePrescription = iterator.next();
					medicalAdministration.setBabyPrescription(medicinePrescription);

					if (!BasicUtils.isEmpty(medicinePrescription.getMedicationtype())) {
						String query = "SELECT typevalue FROM ref_medtype WHERE typeid='"
								+ medicinePrescription.getMedicationtype() + "'";
						List<String> result = prescriptionDao.getListFromNativeQuery(query);
						if (!BasicUtils.isEmpty(result)) {
							String val = result.get(0).toString();
							medicinePrescription.setMedicationTypeStr(val);
						}
					}

					if (!BasicUtils.isEmpty(medicinePrescription.getFrequency())) {
						String query = "SELECT freqvalue FROM ref_medfrequency WHERE freqid='"
								+ medicinePrescription.getFrequency() + "'";
						List<String> result = prescriptionDao.getListFromNativeQuery(query);
						if (!BasicUtils.isEmpty(result)) {
							String val = result.get(0).toString();
							medicinePrescription.setMedicationFreqStr(val);
						}
					}

					// get given medicines based on active prescription id and
					// in the date range...
					if (!BasicUtils.isEmpty(medicinePrescription.getBabyPresid())) {
						Timestamp startDate = medicinePrescription.getStartdate();
						Timestamp endDate = new Timestamp(new Date().getTime());

						if (smallestDate != null) {
							if (smallestDate.compareTo(startDate) > 0) {
								smallestDate = startDate;
							}
						} else {
							smallestDate = startDate;
						}

						String query = "select obj from MedAdministration obj where babyPresid = "
								+ medicinePrescription.getBabyPresid() + " and  giventime>='" + startDate + "'";
						List<MedAdministration> medAdmistrationList = prescriptionDao
								.getListFromMappedObjNativeQuery(query);
						// if (!BasicUtils.isEmpty(medAdmistrationList)) {
						Iterator<MedAdministration> iteratorMedAdministration = medAdmistrationList.iterator();
						List<MedicineDateTimeObj> givenMedincineDateTime = new ArrayList<MedicineDateTimeObj>();
						String givenDate = "";
						while (iteratorMedAdministration.hasNext()) {
							MedicineDateTimeObj medicineDateTime = new MedicineDateTimeObj();
							List<GivenMedicineTimeObj> medicinetimeObjList = new ArrayList<GivenMedicineTimeObj>();
							MedAdministration medAdmistration = iteratorMedAdministration.next();
							givenDate = CalendarUtility.getDateformatdb(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(medAdmistration.getGiventime());
							String givenTime = CalendarUtility.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(medAdmistration.getGiventime());
							String scheduleTime = CalendarUtility
									.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(medAdmistration.getScheduletime());

							boolean flag = false;
							Iterator<MedicineDateTimeObj> givenMedicineIterator = givenMedincineDateTime.iterator();
							// checking if this date entry is already present
							for (int i = 0; i < givenMedincineDateTime.size(); i++) {
								MedicineDateTimeObj givenMedicineMap = givenMedincineDateTime.get(i);

								if (givenMedicineMap.getDate().equalsIgnoreCase(givenDate)) {
									givenMedincineDateTime.remove(i);
									medicinetimeObjList = givenMedicineMap.getTime();
									GivenMedicineTimeObj timeObj = new GivenMedicineTimeObj();
									timeObj.setGivenTime(givenTime);
									timeObj.setPresTime(scheduleTime);
									medicinetimeObjList.add(timeObj);
									medicineDateTime.setDate(givenDate);
									medicineDateTime.setTime(medicinetimeObjList);
									givenMedincineDateTime.add(i, medicineDateTime);
									flag = true;
									break;
								}
							}

							if (!flag) {
								medicineDateTime.setDate(givenDate);
								GivenMedicineTimeObj timeObj = new GivenMedicineTimeObj();
								timeObj.setGivenTime(givenTime);
								timeObj.setPresTime(scheduleTime);
								medicinetimeObjList.add(timeObj);
								medicineDateTime.setTime(medicinetimeObjList);
								givenMedincineDateTime.add(medicineDateTime);
							}
						}

						List<String> medicneFrequencyData = BasicConstants.medicineFrequency
								.get(medicinePrescription.getFrequency());
						if (!BasicUtils.isEmpty(medicneFrequencyData)) {
							medicinePrescription.setFrequency(medicneFrequencyData.get(0));
							float frequencyInt = Integer.parseInt(medicneFrequencyData.get(1));

							// completing time bubbles for each date ...(should
							// be equal to frequency)...loop
							for (int i = 0; i < givenMedincineDateTime.size(); i++) {
								MedicineDateTimeObj medicineDateTime = givenMedincineDateTime.get(i);
								// now check time here...upto frequencyInt
								int sizetime = medicineDateTime.getTime().size();
								float diff = frequencyInt - sizetime;
								for (int j = 0; j < diff; j++) {
									GivenMedicineTimeObj timeObj = new GivenMedicineTimeObj();
									medicineDateTime.getTime().add(timeObj);
								}

								givenMedincineDateTime.remove(i);
								givenMedincineDateTime.add(i, medicineDateTime);
							}

							long diff = endDate.getTime() - medicinePrescription.getStartdate().getTime();
							long diffDays = diff / (24 * 60 * 60 * 1000) + 1;

							List<MedicineDateTimeObj> sortedGivenMedincineDateTime = new ArrayList<MedicineDateTimeObj>();
							// sorting according to dates..descending order from
							// today to start date of prescription...loop
							for (int i = 0; i < diffDays; i++) {
								Timestamp tomorrow = new Timestamp(endDate.getTime() - i * 24 * 60 * 60 * 1000);
								if (endDate.compareTo(tomorrow) >= 0) {
									boolean flag = false;
									for (int j = 0; j < givenMedincineDateTime.size(); j++) {
										if (givenMedincineDateTime.get(j).getDate()
												.equalsIgnoreCase(CalendarUtility.dateFormatDB.format(tomorrow) + "")) {
											sortedGivenMedincineDateTime.add(givenMedincineDateTime.get(j));
											flag = true;
										}
									}

									if (!flag) {
										MedicineDateTimeObj tomorrowObj = new MedicineDateTimeObj();
										tomorrowObj.setDate(CalendarUtility.dateFormatDB.format(tomorrow) + "");
										List<GivenMedicineTimeObj> timeObjListTommorow = new ArrayList<GivenMedicineTimeObj>();
										for (int k = 0; k < frequencyInt; k++) {
											GivenMedicineTimeObj timeObj = new GivenMedicineTimeObj();
											timeObjListTommorow.add(timeObj);
										}
										tomorrowObj.setTime(timeObjListTommorow);
										givenMedincineDateTime.add(tomorrowObj);
										sortedGivenMedincineDateTime.add(tomorrowObj);
									}
								}
							}
							// for each date now adding prescribed time
							// according to the frequency...and start time..
							String startTime = medicinePrescription.getStarttime();
							int addTime = ((Float) (24 / frequencyInt)).intValue();
							for (int i = sortedGivenMedincineDateTime.size() - 1; i >= 0; i--) {
								Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
								List<GivenMedicineTimeObj> timeObjList = sortedGivenMedincineDateTime.get(i).getTime();
								List<GivenMedicineTimeObj> newTimeObjList = new ArrayList<GivenMedicineTimeObj>();
								for (int timeIndex = 0; timeIndex < timeObjList.size(); timeIndex++) {
									GivenMedicineTimeObj timeObj = timeObjList.get(timeIndex);
									Boolean isDayChangeFlag = false;
									if (timeIndex == 0) {
										if (i == sortedGivenMedincineDateTime.size() - 1) {
											if (timeObjList.get(timeIndex).getGivenTime().isEmpty()) {
												Date startTimeDate = CalendarUtility
														.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
														.parse(startTime);
												Calendar cal = Calendar.getInstance(); //
												cal.setTime(startTimeDate);
												timeObj.setPresTime(CalendarUtility
														.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
														.format(cal.getTime()));
											}

										} else {
											List<GivenMedicineTimeObj> prevDateTimeObjList = sortedGivenMedincineDateTime
													.get(i + 1).getTime();
											if (timeObj.getGivenTime().isEmpty()) {
												for (int prevListIndex = 0; prevListIndex < prevDateTimeObjList
														.size(); prevListIndex++) {
													if (!prevDateTimeObjList.get(prevListIndex).getGivenTime()
															.isEmpty()) {

														Date prevDateGivenTime = CalendarUtility
																.getDateformatampm(
																		CalendarUtility.CLIENT_CRUD_OPERATION)
																.parse(prevDateTimeObjList.get(prevListIndex)
																		.getGivenTime());
														Calendar cal = Calendar.getInstance(); //
														cal.setTime(prevDateGivenTime);
														cal.add(Calendar.HOUR_OF_DAY, addTime);
														timeObj.setPresTime(CalendarUtility
																.getDateformatampm(
																		CalendarUtility.CLIENT_CRUD_OPERATION)
																.format(cal.getTime()));

													}
												}

												if (timeObj.getPresTime().isEmpty()) {
													Date prevDateGivenTime = CalendarUtility
															.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
															.parse(prevDateTimeObjList
																	.get(prevDateTimeObjList.size() - 1).getPresTime());
													Calendar cal = Calendar.getInstance(); //
													cal.setTime(prevDateGivenTime);
													cal.add(Calendar.HOUR_OF_DAY, addTime);
													timeObj.setPresTime(CalendarUtility
															.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
															.format(cal.getTime()));
												}

											}
										}

									} else {

										if (timeObjList.get(timeIndex).getGivenTime().isEmpty()) {
											if (!timeObjList.get(timeIndex - 1).getGivenTime().isEmpty()
													&& !timeObjList.get(timeIndex - 1).getPresTime().isEmpty()) {
												Date prevGivenTime = CalendarUtility
														.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
														.parse(timeObjList.get(timeIndex - 1).getGivenTime());
												Calendar cal = Calendar.getInstance(); //
												cal.setTime(prevGivenTime);
												cal.add(Calendar.HOUR_OF_DAY, addTime);
												timeObj.setPresTime(CalendarUtility
														.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
														.format(cal.getTime()));

											} else {
												if (!timeObjList.get(timeIndex - 1).getPresTime().isEmpty()) {
													Date prevGivenTime = CalendarUtility
															.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
															.parse(timeObjList.get(timeIndex - 1).getPresTime());
													Calendar cal = Calendar.getInstance();
													cal.setTime(prevGivenTime);
													cal.add(Calendar.HOUR_OF_DAY, addTime);
													timeObj.setPresTime(CalendarUtility
															.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
															.format(cal.getTime()));
													// check for day change....
													System.out.println(
															cal.getTime().getDay() + " " + prevGivenTime.getDay());
													if (cal.getTime().getDay() > prevGivenTime.getDay()) {
														isDayChangeFlag = true;
													}
												}
											}
										}
									}

									int prevSize = newTimeObjList.size() - 1;
									if (!newTimeObjList.isEmpty()
											&& newTimeObjList.get(prevSize).getPresTime().contains("PM")
											&& timeObj.getPresTime().contains("AM")) {
										System.out.println("change in day..no need to add..!!");
									} else {
										if (!isDayChangeFlag)
											newTimeObjList.add(timeObj);
									}
								}
								sortedGivenMedincineDateTime.get(i).setTime(newTimeObjList);
							}
							medicalAdministration.setGivenMedincineDateTime(sortedGivenMedincineDateTime);
						}
						medicalAdministration.setSmallestStartDate(smallestDate);
					}
					medicalAdministrationObj.add(medicalAdministration);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser,
						uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(ex));
			}
		}
		return medicalAdministrationObj;
	}

	@Override
	public ResponseMessageWithResponseObject addGivenMedicinesDetails(String givenBy, String babyPresId,
			String givenTime, String presTime) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		MedAdministration medAdministration = new MedAdministration();
		medAdministration.setGivenby(givenBy);
		medAdministration.setLoggedUser(givenBy);
		medAdministration.setBabyPresid(Long.valueOf(babyPresId));
		try {
			Timestamp timeStamp = new Timestamp(new Date().getTime());
			medAdministration.setGiventime(timeStamp);

			Date presDate = CalendarUtility.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION).parse(presTime);
			presDate.setYear(timeStamp.getYear());
			presDate.setMonth(timeStamp.getMonth());
			presDate.setDate(timeStamp.getDate());
			medAdministration.setScheduletime(new Timestamp(presDate.getTime()));
			prescriptionDao.saveObject(medAdministration);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Medical Information added successfully..!!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VwBabyPrescription> getBabyPrescriptionDetails(String uhid) {
		List<VwBabyPrescription> babyPrescription = prescriptionDao
				.getListFromMappedObjNativeQuery("select obj from VwBabyPrescription obj where uhid='" + uhid + "'");
		return babyPrescription;
	}

	@Override
	public ResponseMessageWithResponseObject editBabyPrescription(BabyPrescription babyPrescription) {
		ResponseMessageWithResponseObject responseMessageWithResponseObject = new ResponseMessageWithResponseObject();
		try {
			deleteBabyPrescription(babyPrescription, "");
			babyPrescription.setBabyPresid(null);
			babyPrescription = (BabyPrescription) inicuDao.saveObject(babyPrescription);
			responseMessageWithResponseObject.setReturnedObject(babyPrescription);
			responseMessageWithResponseObject.setMessage("Prescription updated successfully");
			responseMessageWithResponseObject.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseMessageWithResponseObject;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NursingMedicationModel getNursingMedication(String uhid) {
		NursingMedicationModel returnObj = new NursingMedicationModel();

		try {
			String queryGetFrequencyRef = "select obj from RefMedfrequency obj";
			List<RefMedfrequency> freqList = inicuDao.getListFromMappedObjQuery(queryGetFrequencyRef);
			returnObj.setFreqList(freqList);

			String queryGetMedTypeRef = "select obj from RefMedType obj";
			List<RefMedType> typeList = inicuDao.getListFromMappedObjQuery(queryGetMedTypeRef);
			returnObj.setTypeList(typeList);

			List<NursingMedication> pastNursingList = inicuDao.getListFromMappedObjQuery(
					"select obj from NursingMedication obj where uhid='" + uhid + "' and flag is null order by given_time desc");
			returnObj.setPastNursingList(pastNursingList);

			List<BabyPrescription> alltMedicationList = inicuDao
					.getListFromMappedObjQuery("select obj from BabyPrescription obj where uhid='" + uhid
							+ "' order by enddate desc, startdate desc");
			
			
			for(int i = 0; i <alltMedicationList.size();i++) {
				//BabyPrescription object = alltMedicationList.get(i);
				Long id = alltMedicationList.get(i).getBabyPresid();
				
				String query = "Select obj from NursingMedication as obj where baby_presid = '" + id + "' and flag is null order by creationtime desc";
				List<NursingMedication> allNursingList = inicuDao.getListFromMappedObjQuery(query);
				if(allNursingList!=null && allNursingList.size()!=0 && allNursingList.get(0)!=null)
					alltMedicationList.get(i).setDue_date(allNursingList.get(0).getNext_dose());
				else {
					alltMedicationList.get(i).setDue_date(null);
				}
			}
			
//			List<BabyPrescription> alltMedicationList1 = inicuDao
//					.getListFromMappedObjQuery("select obj from NursingMedication obj where obj.babypresid IN (select distinct obj1.babypresid from NursingMedication obj1 where uhid ='" + uhid + "' order by creationtime desc)";
						
			returnObj.setAllMedicationList(alltMedicationList);
			
			List<RefMedBrand> finalbrandList = new ArrayList<RefMedBrand>();
			if(!BasicUtils.isEmpty(alltMedicationList)) {
				for(BabyPrescription med: alltMedicationList) {
					if(med.getIsactive()) {
						List<RefMedBrand> brandList = inicuDao
								.getListFromMappedObjQuery("select obj from RefMedBrand obj where medid='" + med.getMedid() + "'");
						if(!BasicUtils.isEmpty(brandList)) {
							finalbrandList.addAll(brandList);
						}
					}
				}
			}
			returnObj.setBrandList(finalbrandList);

			List<MedicationPreparation> medicationPreparationList = inicuDao
					.getListFromMappedObjQuery("select obj from MedicationPreparation obj where uhid='" + uhid + "'");
			returnObj.setMedicationPreparationList(medicationPreparationList);

			List<AssessmentMedication> assessmentMedicationList = inicuDao
					.getListFromMappedObjQuery("select obj from AssessmentMedication obj where uhid='" + uhid
							+ "' and nursing_action='false' order by assessmenttime desc");
			returnObj.setAssessmentMedicineList(assessmentMedicationList);

			List<AssessmentMedication> pastAssessmentMedicationList = inicuDao
					.getListFromMappedObjQuery("select obj from AssessmentMedication obj where uhid='" + uhid
							+ "' and nursing_action='true' order by assessmenttime desc");
			returnObj.setPastAssessmentMedicineList(pastAssessmentMedicationList);

			List<DoctorBloodProducts> bloodProductList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getDoctorBloodProductList(uhid)
							+ " and status='Active' order by assessment_time desc");
			returnObj.setBloodProductList(bloodProductList);
			
			List<CentralLine> centralLineList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getDoctorCentralLineList(uhid)
							+ " and removal_timestamp = " + null + " order by creationtime desc");
			
			List<CentralLine> centralLineListActual = new ArrayList<>();
			for(CentralLine obj : centralLineList){
				if(!BasicUtils.isEmpty(obj.getHeparinTotalVolume()) && !BasicUtils.isEmpty(obj.getHeparinVolume())) {
					String orderString = "";
					orderString = calculateOrderString(obj);
					obj.setOrderString(orderString);
					centralLineListActual.add(obj);
				}
			}
			returnObj.setCentralLineList(centralLineListActual);

			List<NursingBloodproduct> pastNursingBloodProductList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingBloodProductList(uhid));
			returnObj.setPastNursingBloodProductList(pastNursingBloodProductList);
			
			List<NursingHeplock> pastNursingHeplockList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getNursingHeplockList(uhid));
			returnObj.setNursingHeplockList(pastNursingHeplockList);
			
//			if(!BasicUtils.isEmpty(babyPresId)) {
//				babyId = babyPresId;
//				List<NursingMedication> nursingList = getDeletedList(babyPresId);
//				returnObj.setDeletedList(nursingList);
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getNursingMedication", BasicUtils.convertErrorStacktoString(e));
		}

		return returnObj;
	}
	
	String calculateOrderString(CentralLine obj){
		String orderString = "";
		
		if(!BasicUtils.isEmpty(obj.getSolutionType())) {
			if(obj.getSolutionType().equals("Normal Saline")){
				Float remVolume = BasicUtils.round(obj.getHeparinTotalVolume() - obj.getHeparinVolume(),1);
				orderString = "Add " + remVolume +  " ml of NS with " + BasicUtils.round(obj.getHeparinVolume(),1) + " ml of Heplock to be given at the rate of " +BasicUtils.round(obj.getHeparinRate(),1) + " ml/hr";
			}
			if(obj.getSolutionType().equals("0.25 NS in DW")){
				Float remVolume = BasicUtils.round(obj.getHeparinTotalVolume() - obj.getHeparinVolume(),1);
				Float NSVolume = BasicUtils.round(remVolume/4,1);
				Float DWVolume = BasicUtils.round(remVolume - NSVolume,1);
				orderString = "Add " + NSVolume +  " ml of NS in " + DWVolume + " ml of DW to create " + BasicUtils.round(obj.getHeparinTotalVolume(),1) + " ml of solution " + remVolume + " ml of (0.25 NS in DW) prepared solution with " + BasicUtils.round(obj.getHeparinVolume(),1) +" ml of Heplock to be given at the rate of " + BasicUtils.round(obj.getHeparinRate(),1) + " ml/hr";
			}
		}
		return orderString;
	}

	@Override
	public ResponseMessageWithResponseObject saveNursingMedication(NursingMedicationModel medicationModel) {
		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		NursingMedication nursingMedicationObj = medicationModel.getCurrentEmptyObj();

		try {
			nursingMedicationObj = (NursingMedication) inicuDao.saveObject(nursingMedicationObj);

			if (nursingMedicationObj.getAssessment_medicine() != null
					&& nursingMedicationObj.getAssessment_medicine()) {
				AssessmentMedication assessmentMedicationObj = medicationModel.getCurrentAssessmentMedicationObj();
				assessmentMedicationObj.setNursing_action(true);
				assessmentMedicationObj = (AssessmentMedication) inicuDao.saveObject(assessmentMedicationObj);
			} else {
				BabyPrescription doctorMedicationObj = medicationModel.getCurrentMedicationObj();
				if (doctorMedicationObj.getBolus()) {
					doctorMedicationObj.setIsactive(false);
					if (doctorMedicationObj.getFreq_type().equalsIgnoreCase("Continuous")
							&& !BasicUtils.isEmpty(doctorMedicationObj.getInf_time())) {
						Timestamp enddate = new Timestamp(nursingMedicationObj.getGiven_time().getTime()
								+ (doctorMedicationObj.getInf_time() * 60000));
						doctorMedicationObj.setEnddate(enddate);
					} else {
						doctorMedicationObj.setEnddate(nursingMedicationObj.getGiven_time());
					}
					inicuDao.saveObject(doctorMedicationObj);
				} else if (!BasicUtils.isEmpty(nursingMedicationObj.getNext_dose())) {
					if (BasicUtils.isEmpty(doctorMedicationObj.getDue_date())
							|| doctorMedicationObj.getDue_date().compareTo(nursingMedicationObj.getNext_dose()) < 0) {
						doctorMedicationObj.setDue_date(nursingMedicationObj.getNext_dose());
					}
					inicuDao.saveObject(doctorMedicationObj);
				}
			}

			responseObj.setMessage("Saved Successfully.");
			responseObj.setType(BasicConstants.MESSAGE_SUCCESS);
			responseObj.setReturnedObject(getNursingMedication(nursingMedicationObj.getUhid()));
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setType(BasicConstants.MESSAGE_FAILURE);
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
					nursingMedicationObj.getLoggeduser(), nursingMedicationObj.getUhid(), "saveNursingMedication",
					BasicUtils.convertErrorStacktoString(e));
		}
		return responseObj;
	}

	@Override
	public ResponseMessageWithResponseObject setMedicationPreparation(MedicationPreparation preparationObj) {
		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		try {
			preparationObj = (MedicationPreparation) inicuDao.saveObject(preparationObj);
			responseObj.setMessage("Saved Successfully.");
			responseObj.setType(BasicConstants.MESSAGE_SUCCESS);
			responseObj.setReturnedObject(getNursingMedication(preparationObj.getUhid()));
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setType(BasicConstants.MESSAGE_FAILURE);
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
					preparationObj.getLoggeduser(), preparationObj.getUhid(), "setMedicationPreparation",
					BasicUtils.convertErrorStacktoString(e));
		}
		return responseObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveNursingBloodProduct(NursingMedicationModel medicationModel) {
		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		NursingBloodproduct nursingBloodProductObj = medicationModel.getCurrentBloodProductObj();
		try {
			nursingBloodProductObj = (NursingBloodproduct) inicuDao.saveObject(nursingBloodProductObj);
			responseObj.setMessage("Saved Successfully.");
			responseObj.setType(BasicConstants.MESSAGE_SUCCESS);
			responseObj.setReturnedObject(getNursingMedication(nursingBloodProductObj.getUhid()));
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setType(BasicConstants.MESSAGE_FAILURE);
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
					nursingBloodProductObj.getLoggeduser(), nursingBloodProductObj.getUhid(), "saveNursingBloodProduct",
					BasicUtils.convertErrorStacktoString(e));
		}
		return responseObj;
	}
	
	@Override
	public ResponseMessageWithResponseObject setNursingHeplock(NursingMedicationModel medicationModel) {
		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		NursingHeplock heplockursingHeplockObj = medicationModel.getCurrentHeplockObj();
		try {
			heplockursingHeplockObj = (NursingHeplock) inicuDao.saveObject(heplockursingHeplockObj);
			responseObj.setMessage("Saved Successfully.");
			responseObj.setType(BasicConstants.MESSAGE_SUCCESS);
			responseObj.setReturnedObject(getNursingMedication(heplockursingHeplockObj.getUhid()));
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setType(BasicConstants.MESSAGE_FAILURE);
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
					heplockursingHeplockObj.getLoggeduser(), heplockursingHeplockObj.getUhid(), "saveNursingHeplock",
					BasicUtils.convertErrorStacktoString(e));
		}
		return responseObj;
	}
	
	@Override
	public DeletedNursingMedicationModel deleteNursingMedication(String uhid, Long nursingMedicationId, String babyPresId) {
		// TODO Auto-generated method stub
		DeletedNursingMedicationModel returnObj = new DeletedNursingMedicationModel();
		NursingMedicationModel nursingNotesObj = new NursingMedicationModel();
		String roleid = null;
		try {
			
			if(!BasicUtils.isEmpty(nursingMedicationId)) {
				String query = "update nursing_medication set flag = true where nursing_medication_id = '" + nursingMedicationId + "'";
				inicuDao.updateOrDeleteNativeQuery(query);
				
				String query1 = "select obj from BabyPrescription as obj where baby_presid  = '" + babyPresId + "'";
				List<BabyPrescription> doctorMedicine = inicuDao.getListFromMappedObjQuery(query1);
				if(!BasicUtils.isEmpty(doctorMedicine)) {
					if(doctorMedicine.get(0).getBolus()!=null && doctorMedicine.get(0).getBolus()) {
//						doctorMedicine.get(0).setIsactive(true);
						String updateQuery = "update baby_prescription set isactive = true where baby_presid = '" + babyPresId + "'";
						inicuDao.updateOrDeleteNativeQuery(updateQuery);
						
						String updateQuery1 = "update baby_prescription set enddate = null where baby_presid = '" + babyPresId + "'";
						inicuDao.updateOrDeleteNativeQuery(updateQuery1);
					}
				}
				
				List<NursingMedication> deleteList = getDeletedList(babyPresId);
				returnObj.setDeletedList(deleteList);
				
				
				returnObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
			}
			else {
				returnObj.setMessage("Please login with super user credentials to delete!");
//				returnObj.setStatus_code(403);
			}
		}
		catch(Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Failure in discharging baby");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	List<NursingMedication> getDeletedList(String babyPresId){
		
		String query1 = "select obj from NursingMedication as obj where baby_presid = '" +  babyPresId + "' and flag is null order by creationtime desc";		
		List<NursingMedication> nursingList = inicuDao.getListFromMappedObjQuery(query1);
		return nursingList;
	}
	
	@Override
	public ResponseMessageWithResponseObject deleteNursingBloodProducts(String uhid, Long nursingBloodProductId) {
		// TODO Auto-generated method stub
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();
		NursingMedicationModel nursingNotesObj = new NursingMedicationModel();
		String roleid = null;
		try {
			
			if(!BasicUtils.isEmpty(nursingBloodProductId)) {
				String query = "update nursing_blood_product set flag = true where nursing_blood_product_id = '" + nursingBloodProductId + "'";
				inicuDao.updateOrDeleteNativeQuery(query);
			
				returnObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
			}
			else {
				returnObj.setMessage("Please login with super user credentials to delete!");
//				returnObj.setStatus_code(403);
			}
		}
		catch(Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Failure in discharging baby");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	public ResponseMessageWithResponseObject deleteHeplock(String uhid, Long nursingHeplockId) {
		// TODO Auto-generated method stub
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();
		NursingMedicationModel nursingNotesObj = new NursingMedicationModel();
		String roleid = null;
		try {
			
			if(!BasicUtils.isEmpty(nursingHeplockId)) {
				String query = "update nursing_heplock set flag = true where nursing_heplock_id = '" + nursingHeplockId + "'";
				inicuDao.updateOrDeleteNativeQuery(query);
			
				returnObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
			}
			else {
				returnObj.setMessage("Please login with super user credentials to delete!");
//				returnObj.setStatus_code(403);
			}
		}
		catch(Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Failure in discharging baby");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

}
