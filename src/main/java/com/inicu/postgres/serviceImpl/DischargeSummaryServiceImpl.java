package com.inicu.postgres.serviceImpl;

/*--
 * Author:- iNICU 
 */
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Message.RecipientType;
import javax.persistence.Basic;

import org.netlib.util.booleanW;
//import org.hsqldb.lib.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inicu.models.ActiveConcerns;
import com.inicu.models.DischargeSummaryAdvancedMasterObj;
import com.inicu.models.KeyValueObj;
import com.inicu.models.OutcomeNotesPOJO;
import com.inicu.models.OutcomesNotesDeathPOJO;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.models.TestResultObject;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.NotesDAO;
import com.inicu.postgres.dao.SystematicDAO;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.DeathMedication;
import com.inicu.postgres.entities.DeviceMonitorDetail;
import com.inicu.postgres.entities.DischargeAminophylline;
import com.inicu.postgres.entities.DischargeBirthdetail;
import com.inicu.postgres.entities.DischargeCaffeine;
import com.inicu.postgres.entities.DischargeCpap;
import com.inicu.postgres.entities.DischargeFeeding;
import com.inicu.postgres.entities.DischargeInfection;
import com.inicu.postgres.entities.DischargeInvestigation;
import com.inicu.postgres.entities.DischargeMetabolic;
import com.inicu.postgres.entities.DischargeNicucourse;
import com.inicu.postgres.entities.DischargeOutcome;
import com.inicu.postgres.entities.DischargePhototherapy;
import com.inicu.postgres.entities.DischargeSummary;
import com.inicu.postgres.entities.DischargeTreatment;
import com.inicu.postgres.entities.DischargeVentcourse;
import com.inicu.postgres.entities.DischargeVentilation;
import com.inicu.postgres.entities.DoctorBloodProducts;
import com.inicu.postgres.entities.Hl7DeviceMapping;
import com.inicu.postgres.entities.MotherCurrentPregnancy;
import com.inicu.postgres.entities.NursingVitalparameter;
import com.inicu.postgres.entities.OutcomeNotes;
import com.inicu.postgres.entities.PatientDueVaccineDtls;
import com.inicu.postgres.entities.PeritonealDialysis;
import com.inicu.postgres.entities.RefBed;
import com.inicu.postgres.entities.RefHospitalbranchname;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaHypoglycemia;
import com.inicu.postgres.entities.SaRenalfailure;
import com.inicu.postgres.entities.TestItemResult;
import com.inicu.postgres.entities.VwAssessmentDischargeFinal;
import com.inicu.postgres.entities.VwVaccinationsBrand;
import com.inicu.postgres.entities.VwVaccinationsPatient;
import com.inicu.postgres.service.DischargeSummaryService;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.CalendarUtility;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Service
public class DischargeSummaryServiceImpl implements DischargeSummaryService {

	@Autowired
	InicuDao inicuDao;

	@Autowired
	NotesDAO notesDoa;

	@Autowired
	PatientService patientService;

	@Autowired
	SystematicDAO sysDAO;

	@Autowired
	private InicuDatabaseExeption databaseException;

	SystematicServiceImpl sysObj = new SystematicServiceImpl();

	@SuppressWarnings("unchecked")
	@Override
	public DischargeSummaryAdvancedMasterObj getDishchargedSummaryAdvanced(String uhid, String moduleName,
			String loggedUser) {
		DischargeSummaryAdvancedMasterObj dischargeSummaryMasterObj = new DischargeSummaryAdvancedMasterObj();

		if (!BasicUtils.isEmpty(uhid)) {

			DischargeSummary dischargeSummary = new DischargeSummary();
			DischargeBirthdetail dischargeBirthDetails = new DischargeBirthdetail();
			BabyDetail babyDetails = new BabyDetail();

			// get page 1..
			if (moduleName.equalsIgnoreCase("printDischargeSummary") || moduleName.equalsIgnoreCase("page1")
					|| moduleName.equalsIgnoreCase("page6") || moduleName.equalsIgnoreCase("page2")
					|| moduleName.equalsIgnoreCase("page5")) {

				String queryDischargeSummary = "select obj from DischargeSummary as obj where uhid='" + uhid + "'";
				List<DischargeSummary> listDischargeSummary = inicuDao.getListFromMappedObjQuery(queryDischargeSummary);
				if (!BasicUtils.isEmpty(listDischargeSummary)) {

					dischargeSummary = listDischargeSummary.get(0);
					try {
						if (!BasicUtils.isEmpty(dischargeSummary.getFollowupDate())) {
							dischargeSummary.setFollowupDate(BasicConstants.dateFormat
									.parse(BasicConstants.dateFormat.format(dischargeSummary.getFollowupDate())));
						}
						System.out.println("date " + dischargeSummary.getFollowupDateStr());
					} catch (ParseException e) {
						e.printStackTrace();
					}

				} else {

					String queryGetBabyDetails = "select obj from BabyDetail obj where uhid='" + uhid
							+ "' order by creationtime desc";
					List<BabyDetail> babyDetailsList = inicuDao.getListFromMappedObjQuery(queryGetBabyDetails);

					if (!BasicUtils.isEmpty(babyDetailsList)) {// setting here
																// baby details
																// to the
																// discharge
																// summary...

						babyDetails = babyDetailsList.get(0);
						dischargeSummary.setActualgestation(babyDetails.getActualgestationweek() + "weeks "
								+ babyDetails.getActualgestationdays() + "days");

						Date dischargeDate = null;
						if (!BasicUtils.isEmpty(babyDetails.getDischargeddate())) {
							dischargeDate = babyDetails.getDischargeddate();
						} else {
							dischargeDate = new Date();
						}

						Date birhtDate = babyDetails.getDateofbirth();
						long diffDates = dischargeDate.getTime() - birhtDate.getTime();
						long diffDays = diffDates / (24 * 60 * 60 * 1000);
						dischargeSummary.setAgeatdischarge(diffDays + "");

						dischargeSummary.setBabyname(babyDetails.getBabyname());
						String timeStampOfAdm = CalendarUtility
								.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
								.format(babyDetails.getDateofadmission());
						dischargeSummary.setDateofadmission(Timestamp.valueOf(timeStampOfAdm));

						String timeStampOfBirth = CalendarUtility
								.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
								.format(babyDetails.getDateofbirth());
						dischargeSummary.setDateofbirth(Timestamp.valueOf(timeStampOfBirth));

						dischargeSummary.setDischargestatus(babyDetails.getDischargestatus());

						dischargeSummary.setGender(babyDetails.getGender());

						dischargeSummary.setGestationageatbirth(babyDetails.getGestationweekbylmp() + "weeks "
								+ babyDetails.getGestationdaysbylmp() + "days");

						dischargeSummary.setInoutPatientStatus(babyDetails.getInoutPatientStatus());

						dischargeSummary.setUhid(uhid);

						dischargeSummary.setWeightatbirth(babyDetails.getBirthweight() + "");

						dischargeSummary.setLengthatbirth(babyDetails.getBirthlength() + "");

						dischargeSummary.setHcircumatbirth(babyDetails.getBirthheadcircumference() + "");

					}

					String queryBabyVist = "select obj from BabyVisit obj where uhid='" + uhid
							+ "' order by creationtime asc";
					List<BabyVisit> babyVisitList = inicuDao.getListFromMappedObjQuery(queryBabyVist);
					if (!babyDetailsList.isEmpty()) {
						BabyVisit admissionVisit = babyVisitList.get(0);
						BabyVisit dischargeVisit = babyVisitList.get(babyVisitList.size() - 1);

						dischargeSummary.setWeightonadmission(admissionVisit.getCurrentdateweight() + "");

						dischargeSummary.setLengthonadmission(admissionVisit.getCurrentdateheight() + "");

						dischargeSummary.setHcircumonadmission(admissionVisit.getCurrentdateheadcircum() + "");

						dischargeSummary.setWeightondischarge(dischargeVisit.getCurrentdateweight() + "");

						dischargeSummary.setLengthondischarge(dischargeVisit.getCurrentdateheight() + "");

						dischargeSummary.setHcircumondischarge(dischargeVisit.getCurrentdateheadcircum() + "");

					}

					String queryMotherCurrentPregnancy = "select obj from MotherCurrentPregnancy obj where uhid='"
							+ uhid + "' order by creationtime asc";
					List<MotherCurrentPregnancy> motherCurrentPregnancyList = inicuDao
							.getListFromMappedObjQuery(queryMotherCurrentPregnancy);
					if (!BasicUtils.isEmpty(motherCurrentPregnancyList)) {

						MotherCurrentPregnancy motherCurrentPregnancy = motherCurrentPregnancyList.get(0);

						if (!BasicUtils.isEmpty(motherCurrentPregnancy.getEdd())) {
							String timeStampOfEDD = CalendarUtility
									.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(motherCurrentPregnancy.getEdd());
							dischargeSummary.setEdd(Timestamp.valueOf(timeStampOfEDD));
						}

						if (!BasicUtils.isEmpty(motherCurrentPregnancy.getLmp())) {
							String timeStampOfLMP = CalendarUtility
									.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(motherCurrentPregnancy.getLmp());
							dischargeSummary.setLmp(Timestamp.valueOf(timeStampOfLMP));
						}

					}
				}
				dischargeSummaryMasterObj.setDischargeSummary(dischargeSummary);
			}

			// both page 1 and 2

			if (moduleName.equalsIgnoreCase("printDischargeSummary") || moduleName.equalsIgnoreCase("page1")
					|| moduleName.equalsIgnoreCase("page2")) {
				String queryDischargeBirthdetail = "select obj from DischargeBirthdetail as obj where uhid='" + uhid
						+ "'";
				List<DischargeBirthdetail> dischargeBirthdetailList = inicuDao
						.getListFromMappedObjQuery(queryDischargeBirthdetail);
				if (!BasicUtils.isEmpty(dischargeBirthdetailList)) {
					dischargeBirthDetails = dischargeBirthdetailList.get(0);
				} else {
					String queryMotherCurrentPregnancy = "select obj from MotherCurrentPregnancy obj where uhid='"
							+ uhid + "' order by creationtime asc";
					List<MotherCurrentPregnancy> motherCurrentPregnancyList = inicuDao
							.getListFromMappedObjQuery(queryMotherCurrentPregnancy);
					if (!BasicUtils.isEmpty(motherCurrentPregnancyList)) {

						MotherCurrentPregnancy motherCurrentPregnancy = motherCurrentPregnancyList.get(0);

						if (!BasicUtils.isEmpty(motherCurrentPregnancy.getEdd())) {
							String timeStampOfEDD = CalendarUtility
									.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(motherCurrentPregnancy.getEdd());
							dischargeSummary.setEdd(Timestamp.valueOf(timeStampOfEDD));
						}

						if (!BasicUtils.isEmpty(motherCurrentPregnancy.getLmp())) {
							String timeStampOfLMP = CalendarUtility
									.getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION)
									.format(motherCurrentPregnancy.getLmp());
							dischargeSummary.setLmp(Timestamp.valueOf(timeStampOfLMP));
						}

						dischargeBirthDetails.setDeliverymode(motherCurrentPregnancy.getDeliverymode() + "");

						dischargeBirthDetails.setApgarScore1min(motherCurrentPregnancy.getApgarScore1min() + "");

						dischargeBirthDetails.setApgarScore5min(motherCurrentPregnancy.getApgarScore5min() + "");

						dischargeBirthDetails.setApgarScore10min(motherCurrentPregnancy.getApgarScore10min() + "");

					}

					String queryGetBabyDetails = "select obj from BabyDetail obj where uhid='" + uhid
							+ "' order by creationtime desc";
					List<BabyDetail> babyDetailsList = inicuDao.getListFromMappedObjQuery(queryGetBabyDetails);

					if (!BasicUtils.isEmpty(babyDetailsList)) {// setting here
																// baby details
																// to the
																// discharge
																// summary...

						babyDetails = babyDetailsList.get(0);
						if (!BasicUtils.isEmpty(babyDetails.getCry())) {
							if (babyDetails.getCry().equalsIgnoreCase("Delayed"))
								dischargeBirthDetails
										.setCry(babyDetails.getCry() + " by " + babyDetails.getTimeofcry());
							else
								dischargeBirthDetails.setCry(babyDetails.getCry() + "");
						}
					}
				}
			}

			if (dischargeBirthDetails != null) {
				dischargeSummaryMasterObj.setDischargeBirthDetails(dischargeBirthDetails);
			}

			// get page 2....
			DischargeNicucourse dischargeNicuCourse = null;

			if (moduleName.equalsIgnoreCase("printDischargeSummary") || moduleName.equalsIgnoreCase("page2")) {
				String queryDischargeNicuCourse = "select obj from DischargeNicucourse as obj where uhid='" + uhid
						+ "'";
				List<DischargeNicucourse> dischargeNicucourseList = inicuDao
						.getListFromMappedObjQuery(queryDischargeNicuCourse);
				if (!BasicUtils.isEmpty(dischargeNicucourseList)) {
					dischargeNicuCourse = dischargeNicucourseList.get(0);

					dischargeSummaryMasterObj.setDischargeNicuCource(dischargeNicuCourse);

				}
			}

			// get page 3..

			if (moduleName.equalsIgnoreCase("printDischargeSummary") || moduleName.equalsIgnoreCase("page3")) {
				DischargeVentilation dischargeVentilation = null;
				List<DischargeVentcourse> dischargeVentcourseList = null;

				String queryDischargeVentilation = "select obj from DischargeVentilation as obj where uhid='" + uhid
						+ "'";
				List<DischargeVentilation> dischargeVentilationList = inicuDao
						.getListFromMappedObjQuery(queryDischargeVentilation);
				if (!BasicUtils.isEmpty(dischargeVentilationList)) {

					dischargeVentilation = dischargeVentilationList.get(0);

					if (!BasicUtils.isEmpty(dischargeVentilation.getInotropes())) {
						String[] inotropsArray = dischargeVentilation.getInotropes().replace("[", "").replace("]", "")
								.split(",");
						for (int i = 0; i < inotropsArray.length; i++) {

							if (dischargeVentilation.getInotropesObj().get(inotropsArray[i].trim()) != null) {
								dischargeVentilation.getInotropesObj().put(inotropsArray[i].trim(), true);
							}
						}
					}

					if (!BasicUtils.isEmpty(dischargeVentilation.getSedation())) {
						String[] anagesiaSdationArray = dischargeVentilation.getSedation().replace("[", "")
								.replace("]", "").split(",");
						for (int i = 0; i < anagesiaSdationArray.length; i++) {

							if (dischargeVentilation.getAnagesiaSdationObj()
									.get(anagesiaSdationArray[i].trim()) != null) {
								dischargeVentilation.getAnagesiaSdationObj().put(anagesiaSdationArray[i].trim(), true);
							}
						}
					}

					try {
						if (!BasicUtils.isEmpty(dischargeVentilation.getCpapEnddate()))
							dischargeVentilation.setCpapEnddate(BasicConstants.dateFormat
									.parse(BasicConstants.dateFormat.format(dischargeVentilation.getCpapEnddate())));

						if (!BasicUtils.isEmpty(dischargeVentilation.getCpapStartdate()))
							dischargeVentilation.setCpapStartdate(BasicConstants.dateFormat
									.parse(BasicConstants.dateFormat.format(dischargeVentilation.getCpapStartdate())));

						if (!BasicUtils.isEmpty(dischargeVentilation.getHoodoxygenStartdate()))
							dischargeVentilation.setHoodoxygenStartdate(BasicConstants.dateFormat.parse(
									BasicConstants.dateFormat.format(dischargeVentilation.getHoodoxygenStartdate())));

						if (!BasicUtils.isEmpty(dischargeVentilation.getHoodoxygenEnddate()))
							dischargeVentilation.setHoodoxygenEnddate(BasicConstants.dateFormat.parse(
									BasicConstants.dateFormat.format(dischargeVentilation.getHoodoxygenEnddate())));

						if (!BasicUtils.isEmpty(dischargeVentilation.getNasalStartdate()))
							dischargeVentilation.setNasalStartdate(BasicConstants.dateFormat
									.parse(BasicConstants.dateFormat.format(dischargeVentilation.getNasalStartdate())));

						if (!BasicUtils.isEmpty(dischargeVentilation.getNasalEnddate()))
							dischargeVentilation.setNasalEnddate(BasicConstants.dateFormat
									.parse(BasicConstants.dateFormat.format(dischargeVentilation.getNasalEnddate())));

					} catch (ParseException e) {
						e.printStackTrace();
					}

					dischargeSummaryMasterObj.setDischargeVentilation(dischargeVentilation);

					// get printDischargeSummary discharge vent course list...
					String queryDischargeVentCourse = "select obj from DischargeVentcourse as obj where nicuventillationid='"
							+ dischargeVentilation.getNicuventillationid() + "'";
					dischargeVentcourseList = inicuDao.getListFromMappedObjQuery(queryDischargeVentCourse);

					if (!BasicUtils.isEmpty(dischargeVentcourseList)) {
						for (int i = 0; i < dischargeVentcourseList.size(); i++) {
							DischargeVentcourse dischargeVentCourse = dischargeVentcourseList.get(i);
							try {

								if (!BasicUtils.isEmpty(dischargeVentCourse.getVentStartdate()))
									dischargeVentCourse.setVentStartdate(BasicConstants.dateFormat.parse(
											BasicConstants.dateFormat.format(dischargeVentCourse.getVentStartdate())));

								if (!BasicUtils.isEmpty(dischargeVentCourse.getVentEnddate()))
									dischargeVentCourse.setVentEnddate(BasicConstants.dateFormat.parse(
											BasicConstants.dateFormat.format(dischargeVentCourse.getVentEnddate())));

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						dischargeSummaryMasterObj.setDischargeVentcourseList(dischargeVentcourseList);
					}

				}
			}

			// get page 4 ..
			if (moduleName.equalsIgnoreCase("printDischargeSummary") || moduleName.equalsIgnoreCase("page4")) {
				String queryDischargeInfection = "select obj from DischargeInfection as obj where uhid='" + uhid + "'";
				List<DischargeInfection> dischargeInfectionList = inicuDao
						.getListFromMappedObjQuery(queryDischargeInfection);

				if (!BasicUtils.isEmpty(dischargeInfectionList)) {
					for (int i = 0; i < dischargeInfectionList.size(); i++) {
						DischargeInfection dischargeInfection = dischargeInfectionList.get(i);
						try {

							if (!BasicUtils.isEmpty(dischargeInfection.getInotropes())) {
								String[] inotropsArray = dischargeInfection.getInotropes().replace("[", "")
										.replace("]", "").split(",");
								for (int j = 0; j < inotropsArray.length; j++) {

									if (dischargeInfection.getInotropesObj().get(inotropsArray[j].trim()) != null) {
										dischargeInfection.getInotropesObj().put(inotropsArray[j].trim(), true);
									}
								}
							}

							if (!BasicUtils.isEmpty(dischargeInfection.getInfectionDate()))
								dischargeInfection.setInfectionDate(BasicConstants.dateFormat.parse(
										BasicConstants.dateFormat.format(dischargeInfection.getInfectionDate())));

						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
					dischargeSummaryMasterObj.setDischargeInfectionList(dischargeInfectionList);
				}

				String queryDischargeMetabolic = "select obj from DischargeMetabolic as obj where uhid='" + uhid + "'";
				List<DischargeMetabolic> dischargeMetabolicList = inicuDao
						.getListFromMappedObjQuery(queryDischargeMetabolic);
				if (!BasicUtils.isEmpty(dischargeMetabolicList)) {
					DischargeMetabolic dischargeMetabolic = dischargeMetabolicList.get(0);

					dischargeSummaryMasterObj.setDischargeMetabolic(dischargeMetabolic);

					// get phototherapy list...
					String queryDischargePhototherapy = "select obj from DischargePhototherapy as obj where metabolicid='"
							+ dischargeMetabolic.getJaundiceinicuid() + "'";
					List<DischargePhototherapy> dischargePhototherapyList = inicuDao
							.getListFromMappedObjQuery(queryDischargePhototherapy);
					if (!BasicUtils.isEmpty(dischargePhototherapyList)) {
						for (int i = 0; i < dischargePhototherapyList.size(); i++) {
							DischargePhototherapy dischargePhotoTherapy = dischargePhototherapyList.get(i);
							try {
								if (!BasicUtils.isEmpty(dischargePhotoTherapy.getPhototherapyStartdate()))
									dischargePhotoTherapy.setPhototherapyStartdate(
											BasicConstants.dateFormat.parse(BasicConstants.dateFormat
													.format(dischargePhotoTherapy.getPhototherapyStartdate())));

								if (!BasicUtils.isEmpty(dischargePhotoTherapy.getPhototherapyEnddate()))
									dischargePhotoTherapy.setPhototherapyEnddate(
											BasicConstants.dateFormat.parse(BasicConstants.dateFormat
													.format(dischargePhotoTherapy.getPhototherapyEnddate())));

							} catch (ParseException e) {
								e.printStackTrace();
							}

						}
						dischargeSummaryMasterObj.setDischargePhototherapyList(dischargePhototherapyList);
					}
					// get caffiene list..

					String queryDischargeCaffeine = "select obj from DischargeCaffeine as obj where metabolicid='"
							+ dischargeMetabolic.getJaundiceinicuid() + "'";
					List<DischargeCaffeine> dischargeCaffeineList = inicuDao
							.getListFromMappedObjQuery(queryDischargeCaffeine);

					if (!BasicUtils.isEmpty(dischargeCaffeineList)) {
						for (int i = 0; i < dischargeCaffeineList.size(); i++) {
							DischargeCaffeine dischargeCaffeine = dischargeCaffeineList.get(i);
							try {
								if (!BasicUtils.isEmpty(dischargeCaffeine.getCaffeineStartdate()))
									dischargeCaffeine.setCaffeineStartdate(
											BasicConstants.dateFormat.parse(BasicConstants.dateFormat
													.format(dischargeCaffeine.getCaffeineStartdate())));

								if (!BasicUtils.isEmpty(dischargeCaffeine.getCaffeineEnddate()))
									dischargeCaffeine.setCaffeineEnddate(BasicConstants.dateFormat.parse(
											BasicConstants.dateFormat.format(dischargeCaffeine.getCaffeineEnddate())));
							} catch (ParseException e) {
								e.printStackTrace();
							}

						}
						dischargeSummaryMasterObj.setDischargeCaffeineList(dischargeCaffeineList);
					}
					// get aminophylline list..
					String queryDischargeAminophylline = "select obj from DischargeAminophylline as obj where metabolicid='"
							+ dischargeMetabolic.getJaundiceinicuid() + "'";
					List<DischargeAminophylline> dischargeAminophyllineList = inicuDao
							.getListFromMappedObjQuery(queryDischargeAminophylline);

					if (!BasicUtils.isEmpty(dischargeAminophyllineList)) {
						for (int i = 0; i < dischargeAminophyllineList.size(); i++) {
							DischargeAminophylline dischargeAminophylline = dischargeAminophyllineList.get(i);
							try {

								if (!BasicUtils.isEmpty(dischargeAminophylline.getAminophyllineStartdate()))
									dischargeAminophylline.setAminophyllineStartdate(
											BasicConstants.dateFormat.parse(BasicConstants.dateFormat
													.format(dischargeAminophylline.getAminophyllineStartdate())));

								if (!BasicUtils.isEmpty(dischargeAminophylline.getAminophyllineEnddate()))
									dischargeAminophylline.setAminophyllineEnddate(
											BasicConstants.dateFormat.parse(BasicConstants.dateFormat
													.format(dischargeAminophylline.getAminophyllineEnddate())));
							} catch (ParseException e) {
								e.printStackTrace();
							}

						}
						dischargeSummaryMasterObj.setDischargeAminophyllineList(dischargeAminophyllineList);
					}
					// get cpap list..
					String queryDischargeCpap = "select obj from DischargeCpap as obj where metabolicid='"
							+ dischargeMetabolic.getJaundiceinicuid() + "'";
					List<DischargeCpap> dischargeCpapList = inicuDao.getListFromMappedObjQuery(queryDischargeCpap);
					if (!BasicUtils.isEmpty(dischargeCpapList)) {
						for (int i = 0; i < dischargeCpapList.size(); i++) {
							DischargeCpap dischargeCpap = dischargeCpapList.get(i);
							try {
								if (!BasicUtils.isEmpty(dischargeCpap.getCpapStartdate()))
									dischargeCpap.setCpapStartdate(BasicConstants.dateFormat
											.parse(BasicConstants.dateFormat.format(dischargeCpap.getCpapStartdate())));

								if (!BasicUtils.isEmpty(dischargeCpap.getCpapEnddate()))
									dischargeCpap.setCpapEnddate(BasicConstants.dateFormat
											.parse(BasicConstants.dateFormat.format(dischargeCpap.getCpapEnddate())));
							} catch (ParseException e) {
								e.printStackTrace();
							}

						}
						dischargeSummaryMasterObj.setDischargeCpapList(dischargeCpapList);
					}
				}
			}
			// get page 5..
			if (moduleName.equalsIgnoreCase("printDischargeSummary") || moduleName.equalsIgnoreCase("page5")) {
				// get feedig info..
				String queryDischargeFeeding = "select obj from DischargeFeeding as obj where uhid='" + uhid + "'";
				List<DischargeFeeding> dischargeFeedingList = inicuDao.getListFromMappedObjQuery(queryDischargeFeeding);

				if (!BasicUtils.isEmpty(dischargeFeedingList)) {
					dischargeSummaryMasterObj.setDischargeFeeding(dischargeFeedingList.get(0));
				}
				// get discharge investigation info..
				String queryDischargeInvestigation = "select obj from DischargeInvestigation as obj where uhid='" + uhid
						+ "'";
				List<DischargeInvestigation> dischargeInvestigationList = inicuDao
						.getListFromMappedObjQuery(queryDischargeInvestigation);
				if (!BasicUtils.isEmpty(dischargeInvestigationList)) {
					dischargeSummaryMasterObj.setDischargeInvestigation(dischargeInvestigationList.get(0));
				}
				// get discharge treatment info..
				String queryDischargeTreatment = "select obj from DischargeTreatment as obj where uhid='" + uhid + "'";
				List<DischargeTreatment> dischargeTreatmentList = inicuDao
						.getListFromMappedObjQuery(queryDischargeTreatment);
				if (!BasicUtils.isEmpty(dischargeTreatmentList)) {
					dischargeSummaryMasterObj.setDischargeTreatment(dischargeTreatmentList.get(0));
				}

				// vaccination code
				String vaccBrandQuery = "select obj from VwVaccinationsBrand as obj";
				List<VwVaccinationsBrand> vaccBrandList = inicuDao.getListFromMappedObjQuery(vaccBrandQuery);
				dischargeSummaryMasterObj.setVaccinationBrandList(vaccBrandList);

				String patientVaccQuery = "select obj from VwVaccinationsPatient as obj where uid='" + uhid + "'";
				List<VwVaccinationsPatient> patientVaccList = inicuDao.getListFromMappedObjQuery(patientVaccQuery);

				if (null == patientVaccList || patientVaccList.isEmpty()) {
					dischargeSummaryMasterObj.setPatientVaccinationDueageMap(null);
				} else {
					Iterator<VwVaccinationsPatient> itr = patientVaccList.iterator();
					LinkedHashMap<String, List<VwVaccinationsPatient>> patientVaccinationDueageMap = new LinkedHashMap<String, List<VwVaccinationsPatient>>();
					while (itr.hasNext()) {
						try {

							VwVaccinationsPatient obj = itr.next();
							obj.setDuedateLong(obj.getDuedate().getTime());

							if (obj.getGivendate() != null) {
								obj.setGivendateLong(obj.getGivendate().getTime());
							}

							String dueAge = obj.getDueage().replace("6 ", "six").replaceAll("10 ", "ten");
							if (patientVaccinationDueageMap.containsKey(dueAge)) {
								patientVaccinationDueageMap.get(dueAge).add(obj);
							} else {
								List<VwVaccinationsPatient> dueageVaccList = new ArrayList<VwVaccinationsPatient>();
								dueageVaccList.add(obj);
								patientVaccinationDueageMap.put(dueAge, dueageVaccList);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					dischargeSummaryMasterObj.setPatientVaccinationDueageMap(patientVaccinationDueageMap);
				}
			}
		}
		return dischargeSummaryMasterObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessageWithResponseObject saveDishchargedSummaryAdvanced(String uhid, String moduleName,
			DischargeSummaryAdvancedMasterObj dischargeSummaryAdvancedMasterObj, String loggedUser) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		if (moduleName.equalsIgnoreCase("page1") || moduleName.equalsIgnoreCase("page6")
				|| moduleName.equalsIgnoreCase("page5")) {

			DischargeSummary dischargeSummary = dischargeSummaryAdvancedMasterObj.getDischargeSummary();
			// save it for page1 and page6 both
			dischargeSummary.setUhid(uhid);
			if (dischargeSummary != null) {
				try {
					inicuDao.saveObject(dischargeSummary);
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedUser, uhid, "Saving dischargeSummary part1", BasicUtils.convertErrorStacktoString(e));
				}
			}
		}

		if (moduleName.equalsIgnoreCase("page1") || moduleName.equalsIgnoreCase("page2")) {
			// saving discharge birth details.
			DischargeBirthdetail dischargeBirthDetails = dischargeSummaryAdvancedMasterObj.getDischargeBirthDetails();
			dischargeBirthDetails.setUhid(uhid);

			try {
				inicuDao.saveObject(dischargeBirthDetails);
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser,
						uhid, "Saving discharge birth details part2", BasicUtils.convertErrorStacktoString(e));
			}
		}

		// saving discharge nicu course...
		if (moduleName.equalsIgnoreCase("page2")) {
			DischargeNicucourse dischargeNicuCourse = dischargeSummaryAdvancedMasterObj.getDischargeNicuCource();
			dischargeNicuCourse.setUhid(uhid);
			try {
				inicuDao.saveObject(dischargeNicuCourse);
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser,
						uhid, "Saving discharge nicu course part3", BasicUtils.convertErrorStacktoString(e));
			}
		}

		// saving discharge ventilation details.....
		if (moduleName.equalsIgnoreCase("page3")) {
			DischargeVentilation dischargeVentilation = dischargeSummaryAdvancedMasterObj.getDischargeVentilation();
			dischargeVentilation.setUhid(uhid);
			try {

				Set<String> inotropesKeySet = dischargeVentilation.getInotropesObj().keySet();

				Iterator<String> iteratorInoTropes = inotropesKeySet.iterator();
				String inotropesStr = "";
				while (iteratorInoTropes.hasNext()) {
					String key = iteratorInoTropes.next();
					if (dischargeVentilation.getInotropesObj().get(key.trim().replace(",", ""))) {
						if (inotropesStr.isEmpty())
							inotropesStr = key;
						else
							inotropesStr = inotropesStr + "," + key;
					}
				}
				dischargeVentilation.setInotropes(inotropesStr);

				Set<String> sedationKeySet = dischargeVentilation.getAnagesiaSdationObj().keySet();

				Iterator<String> iteratorSedation = sedationKeySet.iterator();
				String sedationStr = "";
				while (iteratorSedation.hasNext()) {
					String key = iteratorSedation.next();
					if (dischargeVentilation.getAnagesiaSdationObj().get(key.trim().replace(",", ""))) {
						if (sedationStr.isEmpty())
							sedationStr = key;
						else
							sedationStr = sedationStr + "," + key;
					}
				}
				dischargeVentilation.setSedation(sedationStr);

				if (dischargeVentilation.getNicuventillationid() == null) {
					dischargeVentilation = (DischargeVentilation) inicuDao.persistObject(dischargeVentilation);
				} else
					dischargeVentilation = (DischargeVentilation) inicuDao.saveObject(dischargeVentilation);
				List<DischargeVentcourse> dischargeVentCourseList = dischargeSummaryAdvancedMasterObj
						.getDischargeVentcourseList();

				if (!BasicUtils.isEmpty(dischargeVentCourseList)) {

					Iterator<DischargeVentcourse> iterator = dischargeVentCourseList.iterator();
					while (iterator.hasNext()) {
						DischargeVentcourse dischargeVentCourse = iterator.next();
						dischargeVentCourse.setNicuventillationid(dischargeVentilation.getNicuventillationid());
						dischargeVentCourse = (DischargeVentcourse) inicuDao.saveObject(dischargeVentCourse);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// saving discharge investigation...
		if (moduleName.equalsIgnoreCase("page4")) {
			List<DischargeInfection> dischargeInfectionList = dischargeSummaryAdvancedMasterObj
					.getDischargeInfectionList();
			if (!BasicUtils.isEmpty(dischargeInfectionList)) {
				Iterator<DischargeInfection> iterator = dischargeInfectionList.iterator();
				while (iterator.hasNext()) {

					DischargeInfection dischargeInfection = iterator.next();
					dischargeInfection.setUhid(uhid);
					try {
						Set<String> inotropesKeySet = dischargeInfection.getInotropesObj().keySet();

						Iterator<String> iteratorInoTropes = inotropesKeySet.iterator();
						String inotropesStr = "";
						while (iteratorInoTropes.hasNext()) {
							String key = iteratorInoTropes.next();
							if (dischargeInfection.getInotropesObj().get(key.trim().replace(",", ""))) {
								if (inotropesStr.isEmpty())
									inotropesStr = key;
								else
									inotropesStr = inotropesStr + "," + key;
							}
						}
						dischargeInfection.setInotropes(inotropesStr);

						inicuDao.saveObject(dischargeInfection);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			// saving discharge metabolic...page4

			DischargeMetabolic dischargeMetabolic = dischargeSummaryAdvancedMasterObj.getDischargeMetabolic();
			try {
				dischargeMetabolic.setUhid(uhid);
				if (dischargeMetabolic.getJaundiceinicuid() == null)
					dischargeMetabolic = (DischargeMetabolic) inicuDao.persistObject(dischargeMetabolic);
				else
					dischargeMetabolic = (DischargeMetabolic) inicuDao.saveObject(dischargeMetabolic);

				List<DischargePhototherapy> dischargePhototherapyList = dischargeSummaryAdvancedMasterObj
						.getDischargePhototherapyList();
				if (!BasicUtils.isEmpty(dischargePhototherapyList)) {
					Iterator<DischargePhototherapy> iterator = dischargePhototherapyList.iterator();
					while (iterator.hasNext()) {

						DischargePhototherapy dischargePhototherapy = iterator.next();
						dischargePhototherapy.setMetabolicid(dischargeMetabolic.getJaundiceinicuid());
						inicuDao.saveObject(dischargePhototherapy);
					}
				}

				List<DischargeCaffeine> dischargeCaffieneList = dischargeSummaryAdvancedMasterObj
						.getDischargeCaffeineList();
				if (!BasicUtils.isEmpty(dischargeCaffieneList)) {
					Iterator<DischargeCaffeine> iterator = dischargeCaffieneList.iterator();
					while (iterator.hasNext()) {

						DischargeCaffeine dischargeCaffiene = iterator.next();
						dischargeCaffiene.setMetabolicid(dischargeMetabolic.getJaundiceinicuid());
						// inicuDao.saveObject(dischargeCaffiene);
					}
				}

				List<DischargeAminophylline> dischargeAminophyllineList = dischargeSummaryAdvancedMasterObj
						.getDischargeAminophyllineList();
				if (!BasicUtils.isEmpty(dischargeAminophyllineList)) {
					Iterator<DischargeAminophylline> iterator = dischargeAminophyllineList.iterator();
					while (iterator.hasNext()) {

						DischargeAminophylline dischargeAminophylline = iterator.next();
						dischargeAminophylline.setMetabolicid(dischargeMetabolic.getJaundiceinicuid());
						inicuDao.saveObject(dischargeAminophylline);
					}
				}

				List<DischargeCpap> dischargeCpapList = dischargeSummaryAdvancedMasterObj.getDischargeCpapList();
				if (!BasicUtils.isEmpty(dischargeCpapList)) {
					Iterator<DischargeCpap> iterator = dischargeCpapList.iterator();
					while (iterator.hasNext()) {

						DischargeCpap dischargeCpap = iterator.next();
						dischargeCpap.setMetabolicid(dischargeMetabolic.getJaundiceinicuid());
						inicuDao.saveObject(dischargeCpap);
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		// saving discharge feeding..
		if (moduleName.equalsIgnoreCase("page5")) {
			DischargeFeeding dischargeFeeding = dischargeSummaryAdvancedMasterObj.getDischargeFeeding();
			dischargeFeeding.setUhid(uhid);
			if (dischargeFeeding != null) {
				try {
					inicuDao.saveObject(dischargeFeeding);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// saving discharge investigation...
			DischargeInvestigation dischargeInvestigation = dischargeSummaryAdvancedMasterObj
					.getDischargeInvestigation();
			try {
				dischargeInvestigation.setUhid(uhid);
				inicuDao.saveObject(dischargeInvestigation);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// saving discharge treatment...
			DischargeTreatment dischargeTreatment = dischargeSummaryAdvancedMasterObj.getDischargeTreatment();
			try {
				dischargeTreatment.setUhid(uhid);
				inicuDao.saveObject(dischargeTreatment);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (moduleName.equalsIgnoreCase("page6")) {
			// on discharge patient update respective tables like baby_Details
			// add discharge changes to the baby details and free the bed...
			String queryGetBabyDetails = "select obj from BabyDetail obj where uhid='" + uhid + "'";
			List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(queryGetBabyDetails);
			if (babyDetailList != null && babyDetailList.size() > 0) {
				BabyDetail babyDetails = babyDetailList.get(0);
				babyDetails.setAdmissionstatus(false);
				babyDetails.setDischargestatus(
						dischargeSummaryAdvancedMasterObj.getDischargeSummary().getDischargestatus());
				babyDetails.setDischargeddate(
						dischargeSummaryAdvancedMasterObj.getDischargeSummary().getDateofdischarge());
				try {
					// fetch setting preferences
					String type = "";
					String query = "SELECT prefid FROM preference WHERE preference='" + BasicConstants.PREFERENCE_DEVICE
							+ "'";
					List<String> result = inicuDao.getListFromNativeQuery(query);
					if (!BasicUtils.isEmpty(result)) {
						String prefId = result.get(0);
						if (!BasicUtils.isEmpty(prefId)) {
							String fetchType = "SELECT refvalue FROM setting_reference WHERE " + "refid='" + prefId
									+ "' " + "AND category='" + BasicConstants.PREFERENCE_DEVICE + "'";

							List<String> resultSet = inicuDao.getListFromNativeQuery(fetchType);
							if (!BasicUtils.isEmpty(resultSet)) {
								type = resultSet.get(0);
							}
						}
					}
					// check if type is infinity then disconnect device
					if (!type.isEmpty() && type.equalsIgnoreCase("infinity")) {
						// System.out.println(type);
						String checkDeviceConnectedQuery = "SELECT obj FROM Hl7DeviceMapping as obj WHERE inicuBedid = '"
								+ babyDetails.getNicubedno() + "'" + "AND isactive='true'";
						List<Hl7DeviceMapping> resultSet = inicuDao
								.getListFromMappedObjQuery(checkDeviceConnectedQuery);
						if (resultSet != null && resultSet.size() > 0) {
							// device is connected
							// disconnect the device
							Timestamp timeto = new Timestamp(System.currentTimeMillis());
							String disconnectQuery = "update hl7_device_mapping " + "Set isactive = false, time_to='"
									+ timeto + "' " + "WHERE inicu_bedid = '" + babyDetails.getNicubedno() + "' "
									+ "AND hl7_bedid = '" + resultSet.get(0).getHl7Bedid() + "' " + "AND hl7_roomid = '"
									+ resultSet.get(0).getHl7Roomid() + "'";

							inicuDao.updateOrDeleteNativeQuery(disconnectQuery);

						}
					}
					inicuDao.saveObject(babyDetailList.get(0));
					String queryRefBed = "select obj from RefBed obj where bedid='" + babyDetails.getNicubedno() + "'";
					List<RefBed> refBedList = inicuDao.getListFromMappedObjQuery(queryRefBed);
					if (refBedList != null && refBedList.size() > 0) {
						refBedList.get(0).setStatus(true);
						inicuDao.saveObject(refBedList.get(0));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		response.setMessage("information of " + moduleName + " saved successfully..!!");
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessageWithResponseObject savePatientVaccination(VwVaccinationsPatient patientVaccinationObj) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			PatientDueVaccineDtls pdvdObj = new PatientDueVaccineDtls();

			pdvdObj.setPatientduervaccineid(patientVaccinationObj.getPatientduervaccineid());
			pdvdObj.setCreationtime(patientVaccinationObj.getCreationtime());
			pdvdObj.setUid(patientVaccinationObj.getUid());
			pdvdObj.setVaccineinfoid(patientVaccinationObj.getVaccineinfoid());
			pdvdObj.setDuedate(patientVaccinationObj.getDuedate());
			pdvdObj.setGivendate(patientVaccinationObj.getGivendate());
			pdvdObj.setAdministratedby(patientVaccinationObj.getAdministratedby());
			pdvdObj.setBrandid(patientVaccinationObj.getBrandid());

			pdvdObj = (PatientDueVaccineDtls) inicuDao.saveObject(pdvdObj);

			String queryViewObj = "select obj from VwVaccinationsPatient obj where patientduervaccineid="
					+ pdvdObj.getPatientduervaccineid();
			List<VwVaccinationsPatient> patientVaccinationList = inicuDao.getListFromMappedObjQuery(queryViewObj);

			patientVaccinationObj = patientVaccinationList.get(0);

			patientVaccinationObj.setDuedateLong(patientVaccinationObj.getDuedate().getTime());

			if (patientVaccinationObj.getGivendate() != null) {
				patientVaccinationObj.setGivendateLong(patientVaccinationObj.getGivendate().getTime());
			}

			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Patient Vaccination Saved Successfully..!!");
			response.setReturnedObject(patientVaccinationObj);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					patientVaccinationObj.getUid(), "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public DischargeOutcome getDichargeOutCome(String uhid,String episodeid) {
		DischargeOutcome outcome = null;
		try {
			String assessmentMessage = "";
			String query = "select obj from DischargeOutcome obj where uhid='" + uhid + "'";
			List<DischargeOutcome> outcomeList = inicuDao.getListFromMappedObjQuery(query);

			// Validation if any system remains active or passive
			String assessmentMessageSql = HqlSqlQueryConstants.getDischargeBabyAssessmentStatus(uhid);
			List<Object[]> activeAssessmentList = inicuDao.getListFromNativeQuery(assessmentMessageSql);
			if (!BasicUtils.isEmpty(activeAssessmentList)) {
				Iterator<Object[]> itr = activeAssessmentList.iterator();
				while (itr.hasNext()) {
					Object[] obj = itr.next();
					assessmentMessage = assessmentMessage.concat(obj[0] + ", ");
				}
				if (!assessmentMessage.isEmpty()) {
					assessmentMessage = assessmentMessage.substring(0, assessmentMessage.lastIndexOf(","));
					if (assessmentMessage.contains(",")) {
						assessmentMessage += " assessments are active.";
					} else {
						assessmentMessage += " assessment is active.";
					}
				}
			}

			String procedureMessage = "";
			String procedureMessageSql = HqlSqlQueryConstants.getDischargeBabyProcedureStatus(uhid);
			List<String> activeProcedureList = inicuDao.getListFromNativeQuery(procedureMessageSql);
			if (!BasicUtils.isEmpty(activeProcedureList)) {
				Iterator<String> procedureItr = activeProcedureList.iterator();
				while (procedureItr.hasNext()) {
					String obj = procedureItr.next();
					procedureMessage = procedureMessage.concat(obj + ", ");
				}
				if (!procedureMessage.isEmpty()) {
					procedureMessage = procedureMessage.substring(0, procedureMessage.lastIndexOf(","));
					if (procedureMessage.contains(",")) {
						procedureMessage += " procedures are active.";
					} else {
						procedureMessage += " procedure is active.";
					}
				}
			}

			if (!BasicUtils.isEmpty(procedureMessage)) {
				assessmentMessage += " "+ procedureMessage;
			}

			if (!BasicUtils.isEmpty(outcomeList)) {
				outcome = outcomeList.get(0);
				outcome.setReason(assessmentMessage);
			} else {
				outcome = new DischargeOutcome();
				outcome.setReason(assessmentMessage);
			}

			List<BabyPrescription> medicationListForDischarge = new ArrayList<BabyPrescription>();
			medicationListForDischarge = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getMedicationListForDischargeSummary(uhid));
			outcome.setMedicationList(medicationListForDischarge);

			int offset = (TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE)
					.getRawOffset() - TimeZone.getDefault().getRawOffset());
			String queryStr = "select obj from BabyDetail obj where uhid='" + uhid + "' order by creationtime desc";
			List<BabyDetail> babyDetail = inicuDao.getListFromMappedObjQuery(queryStr);
			if(babyDetail != null && !babyDetail.isEmpty()){
				//outcome.setHisDischargeDate((Timestamp) hisDischargeDate.get(0));
				Timestamp hisdischargedate = babyDetail.get(0).getHisdischargedate();
				if (!BasicUtils.isEmpty(hisdischargedate)) {
					Timestamp hisDischargeDateTimeStamp = new Timestamp(hisdischargedate.getTime() - offset);
					outcome.setHisDischargeDate(hisDischargeDateTimeStamp);
				}

				if(!BasicUtils.isEmpty(babyDetail.get(0))) {
					outcome.setAssessmentSubmit(babyDetail.get(0).getIsassessmentsubmit());
				}
				
				String branchName = babyDetail.get(0).getBranchname();
				if(branchName != null && branchName != "") {
					String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
					List<RefHospitalbranchname> refHospitalbranchnameList = inicuDao.getListFromMappedObjQuery(refHospitalbranchnameQuery);
					if(refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0) {
						if(!BasicUtils.isEmpty(refHospitalbranchnameList.get(0).getReportPrint())){
							outcome.setLabPrintFormat(refHospitalbranchnameList.get(0).getReportPrint());
						}
					}
				}
			}
			
//			String queryStr1 = "select obj from VwAssessmentDischargeFinal as obj where uhid = '" + uhid + "' and episodeid = '" + episodeid + "'";

			// Changed the Query to fetch all other assessment where episode id not present
			String queryStr1 = "select obj from VwAssessmentDischargeFinal as obj where uhid = '" + uhid + "' and creationtime is not null order by creationtime desc";
			List<VwAssessmentDischargeFinal> dischargeDateObj = inicuDao.getListFromMappedObjQuery(queryStr1);

			if(dischargeDateObj!=null && !dischargeDateObj.isEmpty()) {
				Timestamp minDischargeDateTimestamp = null;
				String lastEntryFrom = null;
				Timestamp disDate = null;
				//System.out.println("obj");
				VwAssessmentDischargeFinal lastObject = dischargeDateObj.get(0);
				//outcome.setMinDischargeDate(lastObject.getCreationtime());
				if(lastObject!=null && !BasicUtils.isEmpty(lastObject)) {
					disDate = lastObject.getCreationtime();
					lastEntryFrom = lastObject.getCategory();
					minDischargeDateTimestamp = new Timestamp(disDate.getTime());
//					 if(lastObject.getCategory().equalsIgnoreCase("Doctor Blood Product")){
//						 disDate = new Timestamp(lastObject.getCreationtime().getTime() - 19800000);
//					 }
					outcome.setMinDischargeDate(disDate);
					outcome.setLastEntryPanel(lastEntryFrom);
				}
			}

			// Anthropometry
//			String babyvisitQuery = "select 'Anthropometry',concat(baby_visit.visitdate,' ',baby_visit.visittime) :: timestamp from baby_visit where uhid='"+ uhid +"' order by concat(baby_visit.visitdate,' ',baby_visit.visittime) :: timestamp desc limit 1";
//			List<Object[]> babyvisitQueryList = (List<Object[]>)  inicuDao.getListFromNativeQuery(babyvisitQuery);
//
//			if(babyvisitQueryList!=null && babyvisitQueryList.size()>0){
//				// set the name of the last procedure done with its endtime
//				Object[] lastObject = babyvisitQueryList.get(0);
//
//				String name = lastObject[0].toString();
//				Timestamp timeValue = (Timestamp) lastObject[1];
//				if(timeValue!=null) {
//					int bool = (outcome.getMinDischargeDate()).compareTo(timeValue);
//					if(bool<1){
//						outcome.setMinDischargeDate(timeValue);
//						outcome.setLastEntryPanel(name);
//					}
//				}
//			}


			// Vitals
			String vitalsQuery = "select 'Vitals',entrydate from nursing_vitalparameters where uhid='"+ uhid +"' order by entrydate desc limit 1";
			List<Object[]> vitalsQueryList = (List<Object[]>)  inicuDao.getListFromNativeQuery(vitalsQuery);

			if(vitalsQueryList!=null && vitalsQueryList.size()>0){
				// set the name of the last procedure done with its endtime
				Object[] lastObject = vitalsQueryList.get(0);

				String name = lastObject[0].toString();
				Timestamp timeValue = (Timestamp) lastObject[1];
//				Timestamp minDischargeDateTimestamp = new Timestamp(procedureEnddate.getTime() + offset);
				if(timeValue!=null) {
					int bool = (outcome.getMinDischargeDate()).compareTo(timeValue);
					if(bool<1){
						outcome.setMinDischargeDate(timeValue);
						outcome.setLastEntryPanel(name);
					}
				}
			}

			// Events
			String eventQuery = "select 'Events',creationtime from nursing_episode where uhid='"+ uhid +"' order by creationtime desc limit 1";
			List<Object[]> eventQueryList = (List<Object[]>)  inicuDao.getListFromNativeQuery(eventQuery);

			if(eventQueryList!=null && eventQueryList.size()>0){
				// set the name of the last procedure done with its endtime
				Object[] lastObject = eventQueryList.get(0);

				String name = lastObject[0].toString();
				Timestamp timeValue = (Timestamp) lastObject[1];
//				Timestamp minDischargeDateTimestamp = new Timestamp(procedureEnddate.getTime() + offset);
				if(timeValue!=null) {
					int bool = (outcome.getMinDischargeDate()).compareTo(timeValue);
					if(bool<1){
						outcome.setMinDischargeDate(timeValue);
						outcome.setLastEntryPanel(name);
					}
				}
			}

			// Blood Gas
			String bloodGasQuery = "select 'Blood Gas',creationtime from nursing_bloodgas where uhid='"+ uhid +"' order by creationtime desc limit 1";
			List<Object[]> bloodGasQueryList = (List<Object[]>)  inicuDao.getListFromNativeQuery(bloodGasQuery);

			if(bloodGasQueryList!=null && bloodGasQueryList.size()>0){
				// set the name of the last procedure done with its endtime
				Object[] lastObject = bloodGasQueryList.get(0);

				String name = lastObject[0].toString();
				Timestamp timeValue = (Timestamp) lastObject[1];
//				Timestamp minDischargeDateTimestamp = new Timestamp(procedureEnddate.getTime() + offset);
				if(timeValue!=null) {
					int bool = (outcome.getMinDischargeDate()).compareTo(timeValue);
					if(bool<1){
						outcome.setMinDischargeDate(timeValue);
						outcome.setLastEntryPanel(name);
					}
				}
			}


			// Ventilator
			String ventilatorQuery = "select 'Blood Gas',creationtime from nursing_bloodgas where uhid='"+ uhid +"' order by creationtime desc limit 1";
			List<Object[]> ventilatorQueryList = (List<Object[]>)  inicuDao.getListFromNativeQuery(ventilatorQuery);

			if(ventilatorQueryList!=null && ventilatorQueryList.size()>0){
				// set the name of the last procedure done with its endtime
				Object[] lastObject = ventilatorQueryList.get(0);

				String name = lastObject[0].toString();
				Timestamp timeValue = (Timestamp) lastObject[1];
//				Timestamp minDischargeDateTimestamp = new Timestamp(procedureEnddate.getTime() + offset);
				if(timeValue!=null) {
					int bool = (outcome.getMinDischargeDate()).compareTo(timeValue);
					if(bool<1){
						outcome.setMinDischargeDate(timeValue);
						outcome.setLastEntryPanel(name);
					}
				}
			}


			// get the procedure
			String procedureQuery = "select procedure_type,endtime from vw_procedures_usage where uhid='"+ uhid +"' and endtime is not null order by endtime desc limit 1";
			List<Object[]> procedureDetailList = (List<Object[]>)  inicuDao.getListFromNativeQuery(procedureQuery);

			if(procedureDetailList!=null && procedureDetailList.size()>0){
				// set the name of the last procedure done with its endtime
				Object[] lastObject = procedureDetailList.get(0);

				String procedureName = lastObject[0].toString();
				Timestamp procedureEnddate = (Timestamp) lastObject[1];
//				Timestamp minDischargeDateTimestamp = new Timestamp(procedureEnddate.getTime() + offset);
				if(procedureEnddate!=null) {
					int bool = (outcome.getMinDischargeDate()).compareTo(procedureEnddate);
					if(bool<1){
						outcome.setMinDischargeDate(procedureEnddate);
						outcome.setLastEntryPanel(procedureName);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return outcome;
	}

	@Override
	public ResponseMessageWithResponseObject saveDichargeOutCome(DischargeOutcome outcome) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			List<BabyPrescription> prescriptionList = outcome.getMedicationList();
			if (!BasicUtils.isEmpty(prescriptionList)) {
				sysObj.savePrescriptionList(inicuDao, prescriptionList, "", null, outcome.getEntrytime());
			}
			outcome = (DischargeOutcome) inicuDao.saveObject(outcome);
			response.setMessage("Discharge outcome saved successfully.");
			response.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private static final SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("dd-MM-yyyy");

	// Get Date from Timestamp
	public static String getDateFromTimestamp(Timestamp time) {
		String date = monthDayYearformatter.format((java.util.Date) time);
		return date;
	}

	@Override
	public OutcomeNotesPOJO getOutcomeNotes(String uhid, String outcomeType) {
		// TODO Auto-generated method stub
		OutcomeNotesPOJO masterObj = new OutcomeNotesPOJO();
		OutcomeNotes notes = new OutcomeNotes();
		ResponseMessageObject response = new ResponseMessageObject();
		response.setMessage(BasicConstants.MESSAGE_SUCCESS);
		response.setType("Fetching data successful");
		notes.setUhid(uhid);
		if (outcomeType.equalsIgnoreCase("LAMA") || outcomeType.equalsIgnoreCase("TRANSFER")
				|| outcomeType.equalsIgnoreCase("DISCHARGE") || outcomeType.equalsIgnoreCase("DEATH")
				|| outcomeType.equalsIgnoreCase("DOR")) {

			notes.setOutcome(outcomeType);

			try {
				String spo2 = "";

				float temp = 0f, meanBp = 0f, heartRate = 0f, rr = 0f;
				String fetchTempQuery = "Select obj from NursingVitalparameter as obj where uhid='" + uhid
						+ "' order by entrydate desc";
				List<NursingVitalparameter> tempList = inicuDao.getListFromMappedObjQuery(fetchTempQuery);
				if (!BasicUtils.isEmpty(tempList) && !BasicUtils.isEmpty(tempList.get(0))
						&& !BasicUtils.isEmpty(tempList.get(0).getCentraltemp()))
					temp = tempList.get(0).getCentraltemp();
				notes.setTemp(temp);

				String fetchLiveDataQuery = "Select obj from DeviceMonitorDetail as obj where uhid='" + uhid
						+ "' order by starttime desc";
				List<DeviceMonitorDetail> deviceMonitorDetails = inicuDao.getListFromMappedObjQuery(fetchLiveDataQuery);
				DeviceMonitorDetail deviceMonitorDetail = null;
				if (!BasicUtils.isEmpty(deviceMonitorDetails)) {

					if (!BasicUtils.isEmpty(deviceMonitorDetails.get(0))) {
						deviceMonitorDetail = deviceMonitorDetails.get(0);

						if (!BasicUtils.isEmpty(deviceMonitorDetail.getHeartrate())) {
							heartRate = Float.parseFloat(deviceMonitorDetail.getHeartrate());
							notes.setHeartrate(heartRate);
						}

						if (!BasicUtils.isEmpty(deviceMonitorDetail.getMeanBp())) {
							meanBp = Float.parseFloat(deviceMonitorDetail.getMeanBp());
							notes.setMeanBp(meanBp);
						}

						if (!BasicUtils.isEmpty(deviceMonitorDetail.getEcgResprate())) {
							rr = Float.parseFloat(deviceMonitorDetail.getEcgResprate());
							notes.setRr(rr);
						}

						if (!BasicUtils.isEmpty(deviceMonitorDetail.getSpo2())) {
							spo2 = deviceMonitorDetail.getSpo2();
							notes.setSpo2(spo2);
						}

					}

				}

				String respStatusQuery = "Select obj from RespSupport as obj where uhid='" + uhid
						+ "' order by creationtime desc";
				List<RespSupport> listRespSuppport = inicuDao.getListFromMappedObjQuery(respStatusQuery);
				if (!BasicUtils.isEmpty(listRespSuppport)) {
					if (!BasicUtils.isEmpty(listRespSuppport.get(0).getIsactive())) {
						if (listRespSuppport.get(0).getIsactive().equals(false))
							notes.setRespiratoryStatus("Respiratory Status is normal in room air");
						else {
							if (!BasicUtils.isEmpty(listRespSuppport.get(0).getRsVentType()))
								notes.setRespiratoryStatus(listRespSuppport.get(0).getRsVentType());

						}

					}
				}

				boolean isEnteral = false, isParenteral = false, adlibGiven = false;
				String feedQuery = "select obj from BabyfeedDetail as obj where uhid='" + uhid
						+ "' order by entrydatetime desc";
				List<BabyfeedDetail> babyFeedList = inicuDao.getListFromMappedObjQuery(feedQuery);
				if (!BasicUtils.isEmpty(babyFeedList) && !BasicUtils.isEmpty(babyFeedList.get(0))) {

					BabyfeedDetail babyFeedDetail = babyFeedList.get(0);

					if (!BasicUtils.isEmpty(babyFeedDetail.getIsenternalgiven()))
						isEnteral = babyFeedDetail.getIsenternalgiven();

					if (!BasicUtils.isEmpty(babyFeedDetail.getIsparentalgiven()))
						isParenteral = babyFeedDetail.getIsparentalgiven();

					if (!BasicUtils.isEmpty(babyFeedDetail.getIsAdLibGiven()))
						adlibGiven = babyFeedDetail.getIsAdLibGiven();

					notes.setAdlibGiven(adlibGiven);
					notes.setEnteralGiven(isEnteral);
					notes.setParenteralGiven(isParenteral);

					if (isEnteral) {

						if (!BasicUtils.isEmpty(babyFeedDetail.getFeedmethod())) {
							String feedMethodString = babyFeedDetail.getFeedmethod().replace("[", "");
							feedMethodString = feedMethodString.replace("]", "");
							String[] feedMethodArray = feedMethodString.split(",");
							List<String> feedMethodList = new ArrayList<String>(feedMethodArray.length);
							Collections.addAll(feedMethodList, feedMethodArray);
							if (!BasicUtils.isEmpty(feedMethodList)) {
								List<KeyValueObj> masterMethod = patientService.getRefObj(
										"select obj.feedmethodid,obj.feedmethodname from ref_masterfeedmethod obj");
								String feedMethodText = "";
								for (int j = 0; j < feedMethodList.size(); j++) {
									for (int i = 0; i < masterMethod.size(); i++) {
										if (feedMethodList.get(j).trim()
												.equalsIgnoreCase(masterMethod.get(i).getKey().toString().trim())) {
											if (feedMethodText.isEmpty()) {
												feedMethodText = masterMethod.get(i).getValue().toString();
											} else {
												feedMethodText = feedMethodText + ", "
														+ masterMethod.get(i).getValue().toString();
											}
										}
									}
								}

								if (!feedMethodText.isEmpty()) {
									notes.setMode(feedMethodText);
								}
							}

						}

						if (!BasicUtils.isEmpty(babyFeedDetail.getFeedtype())) {
							String feedTypeString = babyFeedDetail.getFeedtype().replace("[", "");
							feedTypeString = feedTypeString.replace("]", "");
							String[] feedTypeArray = feedTypeString.split(",");
							List<String> feedTypeList = new ArrayList<String>(feedTypeArray.length);
							Collections.addAll(feedTypeList, feedTypeArray);
							if (!BasicUtils.isEmpty(feedTypeList)) {
								List<KeyValueObj> masterType = patientService.getRefObj(
										"select obj.feedtypeid,obj.feedtypename from ref_masterfeedtype obj");
								String feedTypeText = "";
								for (int j = 0; j < feedTypeList.size(); j++) {
									for (int i = 0; i < masterType.size(); i++) {
										if (feedTypeList.get(j).trim()
												.equalsIgnoreCase(masterType.get(i).getKey().toString().trim())) {
											if (feedTypeText.isEmpty()) {
												feedTypeText = masterType.get(i).getValue().toString();
											} else {
												feedTypeText = feedTypeText + ", "
														+ masterType.get(i).getValue().toString();
											}
										}
									}
								}

								if (!feedTypeText.isEmpty()) {
									notes.setType(feedTypeText);
								}
							}

						}

						if (!BasicUtils.isEmpty(babyFeedDetail.getFeedduration())) {
							notes.setFrequency(babyFeedDetail.getFeedduration());
						}

						if (!BasicUtils.isEmpty(babyFeedDetail.getFeedvolume())) {
							notes.setVolumePerFeed(babyFeedDetail.getFeedvolume());
						}

						if (isParenteral) {

							if (!BasicUtils.isEmpty(babyFeedDetail.getIsReadymadeSolutionGiven())
									&& babyFeedDetail.getIsReadymadeSolutionGiven()) {
								if (!BasicUtils.isEmpty(babyFeedDetail.getReadymadeFluidType())) {
									notes.setFluidType(babyFeedDetail.getReadymadeFluidType());
								}
							} else if (!BasicUtils.isEmpty(babyFeedDetail.getIsReadymadeSolutionGiven())
									&& !(babyFeedDetail.getIsReadymadeSolutionGiven())) {
								if (!BasicUtils.isEmpty(babyFeedDetail.getCurrentDextroseConcentration()))
									notes.setActualDextroseConc("" + babyFeedDetail.getCurrentDextroseConcentration());
							}

						}

					} else if (!isEnteral && isParenteral) {

						if (!BasicUtils.isEmpty(babyFeedDetail.getIsReadymadeSolutionGiven())
								&& babyFeedDetail.getIsReadymadeSolutionGiven()) {
							if (!BasicUtils.isEmpty(babyFeedDetail.getReadymadeFluidType())) {
								notes.setFluidType(babyFeedDetail.getReadymadeFluidType());
							}
						} else if (!BasicUtils.isEmpty(babyFeedDetail.getIsReadymadeSolutionGiven())
								&& !(babyFeedDetail.getIsReadymadeSolutionGiven())) {
							if (!BasicUtils.isEmpty(babyFeedDetail.getCurrentDextroseConcentration()))
								notes.setActualDextroseConc("" + babyFeedDetail.getCurrentDextroseConcentration());
						}
					} else if (isEnteral && !isParenteral && babyFeedDetail.getIsAdLibGiven()) {
						if (babyFeedDetail.getFeedmethod().contains("Palladai"))
							notes.setMode("Palladai");
					}
				}

				String queryGrowthDetail = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' order by creationtime";
				List<BabyVisit> growthNoteList = inicuDao.getListFromMappedObjQuery(queryGrowthDetail);
				System.out.println("Check size" + growthNoteList.size());
				System.out.println(growthNoteList.size() - 1);
				float sumVelocity = 0f;
				for (int i = 1; i <= 3; i++) {
					if (i > 1) {
						if (growthNoteList.size() >= 1 && !BasicUtils.isEmpty(growthNoteList.size() - 1)) {
							growthNoteList.remove(growthNoteList.size() - 1);
						}

						System.out.println(growthNoteList);
					}

					sumVelocity += getGrowthVelocity(growthNoteList);
					System.out.println("" + sumVelocity);

				}

				float avgVelocity = sumVelocity / 3;
				DecimalFormat df = new DecimalFormat("##.###");
				avgVelocity = Float.parseFloat(df.format(avgVelocity));
				notes.setGrowthVelocity(avgVelocity);

				String treatmentQuery = "Select obj from RespSupport as obj where isactive='true' and uhid='" + uhid
						+ "' order by creationtime desc";
				List<RespSupport> treatmentList = inicuDao.getListFromMappedObjQuery(treatmentQuery);
				String rsVentType = "", treatmentEvent = "";

				if (!BasicUtils.isEmpty(treatmentList)) {
					if (!BasicUtils.isEmpty(treatmentList.get(0).getRsVentType()))
						;
					rsVentType = treatmentList.get(0).getRsVentType();
					if (rsVentType.equalsIgnoreCase("Mechanical Ventilation")) {
						if (!BasicUtils.isEmpty(treatmentList.get(0).getRsMechVentType()))
							rsVentType += treatmentList.get(0).getRsMechVentType();
					}

					if (!BasicUtils.isEmpty(treatmentList.get(0).getEventname()))
						treatmentEvent = treatmentList.get(0).getEventname();

				}

				String eventListQuery = "Select distinct(eventname)  from baby_prescription where eventname is not null";
				List<String> eventNames = inicuDao.getListFromNativeQuery(eventListQuery);
				List<ActiveConcerns> activeConcernsList = new ArrayList<>();
				List<String> medicationList = new ArrayList<>();
				ActiveConcerns activeConcern;
				String medication = "";
				System.out.println(eventNames);

				for (int i = 0; i < eventNames.size(); i++) {
					medicationList = inicuDao
							.getListFromNativeQuery(HqlSqlQueryConstants.getActiveMedications(eventNames.get(i), uhid));
					if (!BasicUtils.isEmpty(medicationList)) {
						medication = String.join(" , ", medicationList);
					}

					activeConcern = new ActiveConcerns();
					activeConcern.setEventName(eventNames.get(i));
					if (!BasicUtils.isEmpty(medication))
						activeConcern.setMedication(medication);
					if (eventNames.get(i).equalsIgnoreCase(treatmentEvent))
						activeConcern.setTreatment(rsVentType);

					if (!BasicUtils.isEmpty(medication))
						activeConcernsList.add(activeConcern);

				}

				System.out.println(activeConcernsList.toString());

				for (int i = 0; i < activeConcernsList.size(); i++) {
					System.out.println("" + activeConcernsList.get(i).getMedication());
					if (activeConcernsList.get(i).getMedication() == null
							&& activeConcernsList.get(i).getMedication() == null)
						activeConcernsList.remove(i);
				}
				masterObj.setActiveConcernsList(activeConcernsList);

				////////////////////////////

				masterObj.setNotes(notes);

			} catch (Exception e) {
				response.setType(BasicConstants.MESSAGE_FAILURE);
				response.setMessage(e.getMessage());
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, outcomeType,
						uhid, "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));

			}

		} else {
			response.setMessage(BasicConstants.MESSAGE_FAILURE);
			response.setType("Wrong parameters sent");
		}

		masterObj.setResponse(response);

		return masterObj;
	}

	private float getGrowthVelocity(List<BabyVisit> growthNoteList) {

		Float growthVelocity = 0f;

		if (!BasicUtils.isEmpty(growthNoteList)) {
			Float birthWeight, minWeight, dischargeWeight;
			birthWeight = minWeight = dischargeWeight = null;

			Timestamp birthDate, dischargeDate, regainBirthWeightDate, minWeightDate;
			birthDate = dischargeDate = regainBirthWeightDate = minWeightDate = null;
			boolean firstMinWeight, weightDrop;
			firstMinWeight = weightDrop = false;
			for (int i = 0; i < growthNoteList.size(); i++) {
				BabyVisit growthObj = growthNoteList.get(i);
				if (i == 0) {
					birthDate = growthObj.getCreationtime();
					if (growthObj.getCurrentdateweight() != null) {
						minWeight = birthWeight = growthObj.getCurrentdateweight();
					}

				} else {
					if (weightDrop && birthDate != null && growthObj.getCurrentdateweight() != null
							&& Float.compare(birthWeight, growthObj.getCurrentdateweight()) <= 0) {
						regainBirthWeightDate = growthObj.getCreationtime();
						weightDrop = false;
					}

					if (minWeight != null && growthObj.getCurrentdateweight() != null
							&& Float.compare(minWeight, growthObj.getCurrentdateweight()) > 0) {
						minWeight = growthObj.getCurrentdateweight();
						minWeightDate = growthObj.getCreationtime();
						if (!firstMinWeight) {
							weightDrop = true;
							firstMinWeight = true;
						}
					}

					if (i == growthNoteList.size() - 1) {
						dischargeDate = growthObj.getCreationtime();
						if (growthObj.getCurrentdateweight() != null) {
							dischargeWeight = growthObj.getCurrentdateweight();
						}

					}
				}
			}

			Float meanWeight;
			Long duration;
			growthVelocity = meanWeight = 0f;
			duration = 0L;
			if (birthWeight != null && dischargeWeight != null) {
				meanWeight = (birthWeight + dischargeWeight) / (2000);
			}

			if (dischargeDate != null && regainBirthWeightDate != null) {
				duration = (dischargeDate.getTime() - regainBirthWeightDate.getTime()) / (1000 * 60 * 60 * 24);
			}

			if (dischargeWeight != null && birthWeight != null && meanWeight != 0f && duration != 0L) {
				growthVelocity = (dischargeWeight - birthWeight) / (meanWeight * duration);
			}

		}

		return growthVelocity;
	}

	@Override
	public ResponseMessageWithResponseObject saveOutcomeNotes(OutcomeNotes outcome) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			String query = "select obj from OutcomeNotes as obj where uhid = '" + outcome.getUhid() + "'";
			List<OutcomeNotes> outcomeNotesList = inicuDao.getListFromMappedObjQuery(query);
			if(!BasicUtils.isEmpty(outcomeNotesList)) {
				
				String deleteQuery = "delete from outcomes_notes where uhid = '" + outcome.getUhid() + "'";
				inicuDao.updateOrDeleteNativeQuery(deleteQuery);
			}
			outcome = (OutcomeNotes) inicuDao.saveObject(outcome);
			response.setMessage("Outcome notes saved successfully");
			response.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject saveOutcomeNotesDeath(OutcomesNotesDeathPOJO outcome) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		List<DeathMedication> deathMedications = outcome.getDeathMedicationList();
		OutcomeNotes notes = outcome.getNotes();
		try {
			String query = "select obj from OutcomeNotes as obj where uhid = '" + notes.getUhid() + "'";
			List<OutcomeNotes> outcomeNotesList = inicuDao.getListFromMappedObjQuery(query);
			if(!BasicUtils.isEmpty(outcomeNotesList)) {
				
				String deleteQuery = "delete from outcomes_notes where uhid = '" + notes.getUhid() + "'";
				inicuDao.updateOrDeleteNativeQuery(deleteQuery);
			}
			if (!BasicUtils.isEmpty(deathMedications)) {

				deathMedications = (List<DeathMedication>) inicuDao.saveMultipleObject(deathMedications);
//				masterPOJO.setDeathMedicationList(deathMedications); 

			}
			//outcome.getNotes().setOutcome("Death");
			if (!BasicUtils.isEmpty(notes)) {
				notes = (OutcomeNotes) inicuDao.saveObject(notes);
//				masterPOJO.setNotes(notes);
			}

			response.setMessage(BasicConstants.MESSAGE_SUCCESS);
			response.setType(BasicConstants.MESSAGE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(BasicConstants.MESSAGE_FAILURE);
			response.setType(BasicConstants.MESSAGE_FAILURE);

		}
		return response;
	}

	@Override
	public OutcomesNotesDeathPOJO getOutcomeNotesDeath(String uhid) {
		OutcomesNotesDeathPOJO outcomesNotesDeathPOJO = new OutcomesNotesDeathPOJO();
		List<DeathMedication> deathMedicationList = new ArrayList<>();
		String progressNote = "";
		ResponseMessageObject response = new ResponseMessageObject();
		response.setMessage(BasicConstants.MESSAGE_SUCCESS);
		response.setType("Fetching data successful");

		OutcomeNotes outcome = null;
		OutcomeNotes notes = new OutcomeNotes();
		try {

			String notesQuery = "Select obj from OutcomeNotes as obj where uhid='" + uhid
					+ "' order by creationtime desc";

			List<OutcomeNotes> notesList = inicuDao.getListFromMappedObjQuery(notesQuery);
			if (!BasicUtils.isEmpty(notesList)) {
				notes = notesList.get(0);
			}

			String deathMedicationListQuery = " Select obj from DeathMedication as obj where uhid='" + uhid + "'";
			deathMedicationList = inicuDao.getListFromMappedObjQuery(deathMedicationListQuery);
			if (!BasicUtils.isEmpty(deathMedicationList)) {
				outcomesNotesDeathPOJO.setDeathMedicationList(deathMedicationList);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setMessage(BasicConstants.MESSAGE_FAILURE);
			response.setType(BasicConstants.MESSAGE_FAILURE);
		}
		outcomesNotesDeathPOJO.setNotes(notes);
		outcomesNotesDeathPOJO.setResponse(response);
		return outcomesNotesDeathPOJO;
	}

}
