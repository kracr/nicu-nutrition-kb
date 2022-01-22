package com.inicu.postgres.serviceImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.mail.Message.RecipientType;

import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import org.aspectj.apache.bcel.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.w3c.dom.stylesheets.LinkStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.NotesDAO;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.SystematicDAO;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.MetabolicService;
import com.inicu.postgres.service.SystematicService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class MetabolicServiceImpl implements MetabolicService {

	@Autowired
	SystematicDAO sysDAO;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	LogsService logService;

	@Autowired
	PatientDao patientDao;

	@Autowired
	NotesDAO notesDao;

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	InicuPostgresUtililty inicuPostgresUtility;

	@Autowired
	SystematicServiceImpl sysServiceImpl;

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

	ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(MetabolicServiceImpl.class);

	private void PopulateInvestigationOrdered(SaHypoglycemia saHypoglycemia)
	{
		String fetchInvestigationList = "SELECT testname FROM InvestigationOrdered as obj where uhid='"+saHypoglycemia.getUhid()+"' and assesment_type='Hypoglycemia' and assesmentid='"+saHypoglycemia.getHypoglycemiaid()+"'";
		List<String> listofTest = inicuDao.getListFromMappedObjQuery(fetchInvestigationList);
		saHypoglycemia.setOrderlist(BasicUtils.ConvertToCommaSeperatedString(listofTest));
	}
	private void PopulateCauseListMeta(SaHypoglycemia saHypoglycemia)
	{
		String causeStr = saHypoglycemia.getCauseofhypoglycemia();
		if (!BasicUtils.isEmpty(causeStr)) {
			String[] listCause = causeStr.replace(" ", "").trim().replace("[", "").replace("]", "")
					.split(",");
			ArrayList<String> listCauseArray = new ArrayList<String>(Arrays.asList(listCause));
			String tempcause = "";
			for(int i=0;i<listCauseArray.size();i++)
			{
				String fetchQuery = "SELECT causeofmetabolic from ref_causeofmetabolic where causeofmetabolicid='"+listCauseArray.get(i)+"'";

				List res = inicuDao.getListFromNativeQuery(fetchQuery);
				if(!BasicUtils.isEmpty(res))
				{
					if(i==listCauseArray.size()-1)
					{
						tempcause+=res.get(0).toString();
					}
					else
					{
						tempcause+=res.get(0).toString()+",";
					}
				}
			}
			if(tempcause!=null && tempcause.length()>0){
				tempcause = tempcause.substring(0,tempcause.length()-1);
			}
			saHypoglycemia.setCauseofhypoglycemia(tempcause);
			
		}
	}
	
	private void PopulateTreatmentMeta(SaHypoglycemia saHypoglycemia)
	{
		String TreatmentStr = saHypoglycemia.getActionType();
		if (!BasicUtils.isEmpty(TreatmentStr)) {
			String[] listTreatment = TreatmentStr.replace(" ", "").trim().replace("[", "").replace("]", "")
					.split(",");
			ArrayList<String> listTreatmentArray = new ArrayList<String>(Arrays.asList(listTreatment));
			String temptreatment = "";
			for(int i=0;i<listTreatmentArray.size();i++)
			{
				String fetchQuery = "SELECT treatment from ref_assesment_treatment where assesmenttreatmentid='"+listTreatmentArray.get(i)+"'";

				List res = inicuDao.getListFromNativeQuery(fetchQuery);
				if(!BasicUtils.isEmpty(res))
				{
					if(i==listTreatmentArray.size()-1)
					{
						temptreatment+=res.get(0).toString();
					}
					else
					{
						temptreatment+=res.get(0).toString()+",";
					}
				}
			}
			saHypoglycemia.setActionType(temptreatment);
		}
	}

	@Override
	public HypoglycemiaJSON getHypoglycemia(String uhid, String loggedUser) throws InicuDatabaseExeption {
		// get hypoglycemia event object ...
		HypoglycemiaJSON masterHypoglycemia = new HypoglycemiaJSON();

		try {
			SaHypoglycemia currentHypoglycemia = new SaHypoglycemia();
			SaHypoglycemia lastSubmitHypoglycemia = new SaHypoglycemia();

			Object ageAtOnset = 0;
			Object ageAtAssessment = 0;
			Long assessmentId = 0L;

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDate = new Date();
			System.out.println(dateFormat.format(currentDate));
			String currentDateFinal = dateFormat.format(currentDate);

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Date date = new Date();
			String queryfetchBlood = null, queryCountHypoglycemicEvents = null, cumulativeCount = null, queryDoa = null;
			String queryfetchGIRvalue = null;
			Timestamp currentDay = new Timestamp(date.getTime() - offset);
			System.out.println("Day Current : " + currentDay);
			Timestamp dayBefore = null, dateOfAdmission = null, firstActiveDate = null;
			boolean continedActive = false;
			int epCountHypoglycemia = 0;

			HashMap<Object, Object> babyBasicInformation = sysServiceImpl.getBabyInformation(uhid);
			ageAtOnset = babyBasicInformation.get(BasicConstants.AGE_AT_ONSET);
			ageAtAssessment = babyBasicInformation.get(BasicConstants.AGE_AT_ONSET);

			currentHypoglycemia.setAgeOfOnsetInHours(true);
			currentHypoglycemia.setIsAgeAtAssessmentInHours(true);
			currentHypoglycemia.setUhid(uhid);

			if (!BasicUtils.isEmpty(ageAtOnset)) {
				currentHypoglycemia.setAgeatonset(ageAtOnset.toString());
			}
			if (!BasicUtils.isEmpty(ageAtAssessment)) {
				currentHypoglycemia.setAgeAtAssessment((Integer.valueOf(ageAtAssessment.toString())));
			}

			String queryGetHypoglycemia = "select obj FROM SaHypoglycemia as obj WHERE uhid='" + uhid
					+ "' ORDER BY creationtime DESC";
			List<SaHypoglycemia> listHypoglycemia = inicuDao.getListFromMappedObjQuery(queryGetHypoglycemia);
			for(SaHypoglycemia saHypoglycemia : listHypoglycemia) {
				PopulateInvestigationOrdered(saHypoglycemia);
				PopulateTreatmentMeta(saHypoglycemia);
				PopulateCauseListMeta(saHypoglycemia);
			}

			if (!BasicUtils.isEmpty(listHypoglycemia) && listHypoglycemia.size()>0) {

				masterHypoglycemia.setListHypoglycemia(listHypoglycemia);
				lastSubmitHypoglycemia = listHypoglycemia.get(0);
				try {
					if (BasicConstants.NO.equalsIgnoreCase(lastSubmitHypoglycemia.getHypoglycemiaEventStatus())) {
						masterHypoglycemia.setInactiveProgressNote(generateInactiveNotesHypoglycemia(null, uhid, ""));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception while generating inactive note", e);
				}

				assessmentId=listHypoglycemia.get(0).getHypoglycemiaid();

				if (!BasicUtils.isEmpty(listHypoglycemia.get(0).getCountHypoglycaemicEvents()))
					currentHypoglycemia.setCountHypoglemicEvents(null);
				if (!BasicUtils.isEmpty(listHypoglycemia.get(0).getCumulativeHypoEvents()))
					currentHypoglycemia.setCumulativeHypoEvents(null);

				String calDate = sysServiceImpl.CalculateDate48HoursAgo(currentDate);

				// get past prescriptions
				masterHypoglycemia.setPrescriptionList(sysServiceImpl.getPastPrescriptionList(uhid, calDate, currentDateFinal));

				String queryInvestigationOrder = "Select obj from InvestigationOrdered obj where uhid='" + uhid
						+ "' and assesment_type='Hypoglycemia' and assesmentid='" + assessmentId
						+ "' order by creationtime desc";
				List<InvestigationOrdered> investigationOrderList = inicuDao
						.getListFromMappedObjQuery(queryInvestigationOrder);
				ArrayList<String> investOrder = new ArrayList<>();
				if (!BasicUtils.isEmpty(investigationOrderList)) {
					for (InvestigationOrdered order : investigationOrderList) {
						if (!BasicUtils.isEmpty(order.getTestname())) {
							investOrder.add(order.getTestname());
						}
					}
					currentHypoglycemia.setOrderinvestigationList(investOrder);
				}

				// set past order investigation and treatment....
				String pastInvestigationOrderStr = "";
				java.sql.Date sqlTodayDate = new java.sql.Date(new Date().getTime());
				String queryPastOrderInvestigation = "Select obj from InvestigationOrdered obj where uhid='" + uhid
						+ "' and assesmentid='" + assessmentId + "' order by creationtime desc";
				List<InvestigationOrdered> pastOrderInvestigationList = inicuDao
						.getListFromMappedObjQuery(queryPastOrderInvestigation);
				// past order investigation
				if (!BasicUtils.isEmpty(pastOrderInvestigationList)) {
					for (InvestigationOrdered order : pastOrderInvestigationList) {
						if (!BasicUtils.isEmpty(order.getTestname())) {
							if (!BasicUtils.isEmpty(pastInvestigationOrderStr)) {
								pastInvestigationOrderStr = pastInvestigationOrderStr + ", " + order.getTestname();
							} else {
								pastInvestigationOrderStr = order.getTestname();
							}
						}
					}
					currentHypoglycemia.setPastOrderInvestigationStr(pastInvestigationOrderStr);
				}

				if(listHypoglycemia.get(0).getHypoglycemiaEventStatus().equalsIgnoreCase(BasicConstants.INACTIVE)){
					dayBefore = new Timestamp(date.getTime() - (6 * 60 * 60 * 1000) - offset);
					System.out.println("Day Before : " + dayBefore);

					// fetching blood sugar
					queryfetchBlood = "SELECT obj FROM NursingVitalparameter as obj WHERE uhid = '" + uhid
							+ "' AND rbs is not null AND entryDate>='" + dayBefore + "' and entryDate<='" + currentDay
							+ "' ORDER BY entryDate DESC";

				}
				else if(listHypoglycemia.get(0).getHypoglycemiaEventStatus().equalsIgnoreCase(BasicConstants.YES)) {
					continedActive = true;
					// if continued active state then fetch the last visit rbs detail and no. of
					// hypoglycaemic events since last visit
					// baby suffers from hypoglycemia if rbs > 40.

					// rbs value from last vist
					queryfetchBlood = "SELECT obj FROM NursingVitalparameter as obj WHERE uhid='" + uhid
							+ "' AND rbs is not null AND entryDate<='" + currentDay + "' order by entryDate DESC";

					dayBefore = listHypoglycemia.get(0).getAssessmentTime();
					queryCountHypoglycemicEvents = "Select obj from NursingVitalparameter as obj where uhid='" + uhid
							+ "' AND rbs is not null AND rbs<40 AND entryDate>='" + dayBefore + "' and entryDate <='"
							+ currentDay + "'";

					queryDoa = "Select obj from BabyDetail as obj where uhid='" + uhid + "'";
					List<BabyDetail> listBabyDetail = inicuDao.getListFromMappedObjQuery(queryDoa);
					if (!BasicUtils.isEmpty(listBabyDetail)) {
						if (!BasicUtils.isEmpty(listBabyDetail.get(0).getDateofadmission()))
							dateOfAdmission = new Timestamp(listBabyDetail.get(0).getDateofadmission().getTime());
					}

					List<Integer> epCountListHypoglycemia = inicuDao.getListFromNativeQuery(
							HqlSqlQueryConstants.getMaxEpisodeOfAssessment(BasicConstants.SA_HYPOGLYCEMIA, uhid));
					if (!BasicUtils.isEmpty(epCountListHypoglycemia)) {
						epCountHypoglycemia = epCountListHypoglycemia.get(0).intValue();

					}

					List<SaHypoglycemia> firstActiveDateList = inicuDao.getListFromMappedObjQuery(
							HqlSqlQueryConstants.getFirstActiveOfGivenEpisode("SaHypoglycemia", uhid, epCountHypoglycemia));
					if (!BasicUtils.isEmpty(firstActiveDateList)) {
						firstActiveDate = firstActiveDateList.get(0).getAssessmentTime();

					}

					cumulativeCount = "Select obj from NursingVitalparameter as obj where uhid='" + uhid
							+ "' AND rbs is not null AND rbs<40 AND entryDate>='" + firstActiveDate + "' and entryDate <='"
							+ currentDay + "'";

					queryfetchGIRvalue = "SELECT obj FROM BabyfeedDetail as obj WHERE uhid='" + uhid
							+ "' AND girvalue is not null " + "AND entryDateTime>='" + dayBefore + "'";

					// keep previous age of onset boolean and value
					// ONLY if same episode is continuin
					currentHypoglycemia.setAgeatonset(listHypoglycemia.get(0).getAgeatonset());
					currentHypoglycemia.setAgeOfOnsetInHours(listHypoglycemia.get(0).getAgeOfOnsetInHours());
				}
			}
			
			String calDate = sysServiceImpl.CalculateDate48HoursAgo(currentDate);

			// get past prescriptions
			masterHypoglycemia.setPrescriptionList(sysServiceImpl.getPastPrescriptionList(uhid, calDate, currentDateFinal));


			List<SaHypoglycemia> list = getRiskPastListHypo(uhid);
			if (!BasicUtils.isEmpty(list)) {
				currentHypoglycemia.setRiskFactors(list.get(0).getRiskFactors());
			}

			List<NursingVitalparameter> listVitalInfo = null;
			if (queryfetchBlood != null) {
				listVitalInfo = inicuDao.getListFromMappedObjQuery(queryfetchBlood);
			}

			if (!BasicUtils.isEmpty(listVitalInfo)) {
				NursingVitalparameter vitalInfo = listVitalInfo.get(0);
				if (!BasicUtils.isEmpty(vitalInfo)) {
					currentHypoglycemia.setBloodSugar(vitalInfo.getRbs());
				}
			}

			List<NursingVitalparameter> listHypoCount = null;
			if (continedActive) {
				listHypoCount = inicuDao.getListFromMappedObjQuery(queryCountHypoglycemicEvents);
				if (!BasicUtils.isEmpty(listHypoCount)) {
					System.out.println(listHypoCount.size());
					currentHypoglycemia.setCountHypoglycaemicEvents(listHypoCount.size());
				}

				listHypoCount = inicuDao.getListFromMappedObjQuery(cumulativeCount);
				if (!BasicUtils.isEmpty(listHypoCount)) {
					currentHypoglycemia.setCumulativeHypoEvents(listHypoCount.size());
				}
			}

			float maxGIR = 0, minGIR = 0;
			List<BabyfeedDetail> babyFeedDetail = null;
			BabyfeedDetail feedInfoLastVisit = null;
			if (!BasicUtils.isEmpty(queryfetchGIRvalue)) {
				babyFeedDetail = inicuDao.getListFromMappedObjQuery(queryfetchGIRvalue);
				if (!BasicUtils.isEmpty(babyFeedDetail) && babyFeedDetail.size() >= 1) {
					feedInfoLastVisit = babyFeedDetail.get(0);
					if (!BasicUtils.isEmpty(feedInfoLastVisit)) {
						if (feedInfoLastVisit.getGirvalue() != null && feedInfoLastVisit.getGirvalue() != "") {
							currentHypoglycemia.setGirValue(Float.parseFloat(feedInfoLastVisit.getGirvalue()));
						}
					}
					List<Float> arr = new ArrayList<>();
					for (BabyfeedDetail feed : babyFeedDetail) {
						if (feed.getGirvalue() != null && feed.getGirvalue() != "")
							arr.add(Float.parseFloat(feed.getGirvalue()));
					}
					maxGIR = Collections.max(arr);
					minGIR = Collections.min(arr);
				}
			}

			currentHypoglycemia.setMaxGIR(maxGIR);
			currentHypoglycemia.setMinGIR(minGIR);

			masterHypoglycemia.setCurrentHypoglycemia(currentHypoglycemia);

			// get tests list from ref table...
			String queryRefTestsList = "select obj from RefTestslist as obj where not (obj.assesmentCategory like ('%None%')) order by assesmentCategory, testid asc";
			List<RefTestslist> listRefTests = inicuDao.getListFromMappedObjQuery(queryRefTestsList);
			// populate drop down hash map for the tests list...
			HashMap<Object, List<RefTestslist>> testsListMap = new HashMap<Object, List<RefTestslist>>();
			HypoglycemiaDropDownsJSON dropDown = new HypoglycemiaDropDownsJSON();
			for (RefTestslist test : listRefTests) {
				List categoryList = null;
				if (testsListMap.get(test.getAssesmentCategory()) != null) {
					categoryList = testsListMap.get(test.getAssesmentCategory());
				} else {
					categoryList = new ArrayList<RefTestslist>();
				}
				categoryList.add(test);
				testsListMap.put(test.getAssesmentCategory(), categoryList);
			}

			String queryCausesList = "Select * from ref_causeofmetabolic where event ='Hypoglycemia' order by seq";
			List<KeyValueObj> causesList = sysServiceImpl.getRefObj(queryCausesList);
			dropDown.setCauseOfHypoglycemia(causesList);
			dropDown.setRiskFactorsList(causesList);
			dropDown.setTestsList(testsListMap);
			
			String queryGetFrequencyRef = "select obj from RefMedfrequency obj order by frequency_int";
			List<RefMedfrequency> freqList = inicuDao.getListFromMappedObjQuery(queryGetFrequencyRef);
			dropDown.setMedicineFrequency(freqList);


			// Assessment Detail details
			String queryAssessmentRenal = "SELECT assesmenttreatmentid, treatment FROM ref_assesment_treatment where category='Hypoglycemia' ";
			List<KeyValueObj> assessmentRenal = sysServiceImpl.getRefObj(queryAssessmentRenal);
			dropDown.setTreatmentAction(assessmentRenal);
			masterHypoglycemia.setDropDowns(dropDown);

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));

		}
		return masterHypoglycemia;

	}

	private List<SaHypoglycemia> getRiskPastListHypo(String uhid) {
		String queryPastRiskListNec = "select obj from SaHypoglycemia as obj where uhid = '" + uhid
				+ "' and risk_factors!=null order by creationtime desc";
		List<SaHypoglycemia> pastRiskListNec = inicuDao.getListFromMappedObjQuery(queryPastRiskListNec);
		return pastRiskListNec;
	}

	@Override
	public ResponseMessageObject saveHypoglycemiaJson(HypoglycemiaJSON hypoglycemiaJSON, String userId)

			throws InicuDatabaseExeption {

		ResponseMessageObject obj = new ResponseMessageObject();
		SaHypoglycemia saHypoglycemia = hypoglycemiaJSON.getCurrentHypoglycemia();
		if (BasicUtils.isEmpty(saHypoglycemia.getEpisodeid()) && !BasicUtils.isEmpty(saHypoglycemia.getUhid())) {
			String queryAssociatedEvents = "select episodeid from sa_hypoglycemia where uhid='"
					+ saHypoglycemia.getUhid() + "' order by creationtime desc";
			List<Object[]> listAssociatedEvents = inicuDao.getListFromNativeQuery(queryAssociatedEvents);

			if (!BasicUtils.isEmpty(listAssociatedEvents)) {
				saHypoglycemia.setEpisodeid(String.valueOf(listAssociatedEvents.get(0)));
			}
		}
		Timestamp assessmentTime = saHypoglycemia.getAssessmentTime();

		if (!BasicUtils.isEmpty(assessmentTime)) {
			saHypoglycemia.setAssessmentTime(assessmentTime);
		}


		obj.setMessage("Save successfully.");
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		try {
			String uhid = saHypoglycemia.getUhid();
			if (!BasicUtils.isEmpty(saHypoglycemia.getHypoglycemiaEventStatus())
					&& !saHypoglycemia.getHypoglycemiaEventStatus().equalsIgnoreCase("Yes")) {

				SaHypoglycemia hypoglycemiaNew = new SaHypoglycemia();
				hypoglycemiaNew.setHypoglycemiaEventStatus(saHypoglycemia.getHypoglycemiaEventStatus());
				hypoglycemiaNew.setAgeatonset(saHypoglycemia.getAgeatonset());
				hypoglycemiaNew.setAgeOfOnsetInHours(saHypoglycemia.getAgeOfOnsetInHours());
				hypoglycemiaNew.setAgeAtAssessment(saHypoglycemia.getAgeAtAssessment());
				hypoglycemiaNew.setComment(saHypoglycemia.getComment());
				hypoglycemiaNew.setIsNewEntry(saHypoglycemia.getIsNewEntry());

				if (hypoglycemiaNew.getHypoglycemiaEventStatus().equalsIgnoreCase("No")) {
					hypoglycemiaNew.setAssessmentTime(saHypoglycemia.getAssessmentTime());

					if (!BasicUtils.isEmpty(saHypoglycemia.getMedicationStr())) {
						hypoglycemiaNew.setMedicationStr(saHypoglycemia.getMedicationStr());
					}

					hypoglycemiaNew.setEpisodeNumber(saHypoglycemia.getEpisodeNumber());

                    if (!BasicUtils.isEmpty(hypoglycemiaJSON)) {
                        hypoglycemiaNew.setOrderInvestigation(hypoglycemiaJSON.getOrderSelectedText());
                    }
				}

				hypoglycemiaNew.setAssessmentTime(saHypoglycemia.getAssessmentTime());
				hypoglycemiaNew.setEpisodeNumber(saHypoglycemia.getEpisodeNumber());

				saHypoglycemia = hypoglycemiaNew;
			} else {
				// when hypoglycemia is active

//        List causeOfRenalList = saRenalFailure.getCauseOfRenalList();
//        if (!BasicUtils.isEmpty(causeOfRenalList)) {
//          saRenalFailure.setCauseOfRenal(causeOfRenalList.toString());
//        }

				List actionTypeList = saHypoglycemia.getActiontypeList();
				if (!BasicUtils.isEmpty(actionTypeList)) {
					saHypoglycemia.setActionType(actionTypeList.toString());
				}

				List orderInvList = saHypoglycemia.getOrderinvestigationList();
				if (!BasicUtils.isEmpty(hypoglycemiaJSON)) {
					saHypoglycemia.setOrderInvestigation(hypoglycemiaJSON.getOrderSelectedText());

				}

				saHypoglycemia.setTreatmentActionSelected(null);

//        if (!BasicUtils.isEmpty(renalFailureJSON.getPeritonealDialysisObjectList())) {
//          List<PeritonealDialysis> peritonealDialysisObjList = renalFailureJSON
//              .getPeritonealDialysisObjectList();
//
//          peritonealDialysisObjList = (List<PeritonealDialysis>) inicuDao
//              .saveMultipleObject(peritonealDialysisObjList);
//        }

			}

			if (!BasicUtils.isEmpty(uhid)) {
				saHypoglycemia.setUhid(uhid);

				{
					if (userId != null) {
						saHypoglycemia.setLoggeduser(userId);

						NurseExecutionOrders order = new NurseExecutionOrders();

						// set progress notes
						if (!BasicUtils.isEmpty(saHypoglycemia.getComment()) && saHypoglycemia.getComment().indexOf(saHypoglycemia.getLoggeduser())==-1) {
							saHypoglycemia.setComment(
									saHypoglycemia.getComment());
							order.setLoggeduser(saHypoglycemia.getLoggeduser());
						}
						
						if(!BasicUtils.isEmpty(saHypoglycemia.getOtherPlanComments())) {
							order.setEventname("Hypoglycemia");
							order.setIsExecution(false);
							order.setOrderText(saHypoglycemia.getOtherPlanComments());
							order.setAssessmentdate(saHypoglycemia.getAssessmentTime());
							order.setUhid(saHypoglycemia.getUhid());
							inicuDao.saveObject(order);
						}

						if (!BasicUtils.isEmpty(hypoglycemiaJSON.getCurrentHypoglycemia().getIsNewEntry())) {
							if (hypoglycemiaJSON.getCurrentHypoglycemia().getIsNewEntry()) {
								saHypoglycemia.setHypoglycemiaid(null);
								; // due to list
									// is not
									// deatched..
								saHypoglycemia.setCreationtime(null);
								saHypoglycemia = (SaHypoglycemia) sysDAO.saveObject(saHypoglycemia);
							} else {
								if (saHypoglycemia.getHypoglycemiaEventStatus().equalsIgnoreCase("Inactive")) {
									try {
										String query = "update sa_hypoglycemia set comment = '" + saHypoglycemia.getComment()
												+ "' where hypoglycemiaid = '"
												+ hypoglycemiaJSON.getCurrentHypoglycemia().getHypoglycemiaid() + "'";
										inicuDao.updateOrDeleteNativeQuery(query);
									} catch (Exception ex) {
										ex.printStackTrace();
										System.out.println("Exception occured while updating progressnotes for inactive state");
									}
								} else {
									saHypoglycemia.setHypoglycemiaid(
											hypoglycemiaJSON.getCurrentHypoglycemia().getHypoglycemiaid());
									saHypoglycemia
											.setCreationtime(hypoglycemiaJSON.getCurrentHypoglycemia().getCreationtime());
									saHypoglycemia = (SaHypoglycemia) sysDAO.saveObject(saHypoglycemia);
								}

							}
						}

						if (!saHypoglycemia.getHypoglycemiaEventStatus().equalsIgnoreCase("Inactive")) {
							List<BabyPrescription> prescriptionList = hypoglycemiaJSON.getPrescriptionList();
							if (!BasicUtils.isEmpty(prescriptionList)) {
								sysServiceImpl.savePrescriptionList(null, prescriptionList, "Hypoglycemia",
										saHypoglycemia.getHypoglycemiaid(), saHypoglycemia.getAssessmentTime());
							}
						}

						// to be added later if required
//            // update past progress notes for episode number
//            if (!BasicUtils.isEmpty(renalFailureJSON.getListRenalFailure())
//                && saRenalFailure.getRenalstatus().equalsIgnoreCase("inactive")) {
//              
//              updatePastJaundiceProgressNotes(saRenalFailure, renalFailureJSON.getListRenalFailure());
//            }
//
//             for each of the order ordered, update to the database

						sysServiceImpl.saveOrderInvestigation(hypoglycemiaJSON.getDropDowns().getTestsList(),
								saHypoglycemia.getHypoglycemiaid(), saHypoglycemia.getUhid(), userId, "Hypoglycemia",
								saHypoglycemia.getAssessmentTime());

					} else {
						obj.setMessage("logged user id is coming as null");
						obj.setType(BasicConstants.MESSAGE_FAILURE);
					}

				}
			} else {
				obj.setMessage("uhid is coming as null");
				obj.setType(BasicConstants.MESSAGE_FAILURE);
			}

			// save logs
			String desc = mapper.writeValueAsString(saHypoglycemia);

			String action = BasicConstants.INSERT;

			if (saHypoglycemia.getIsEdit() != null && saHypoglycemia.getIsEdit()) {
				action = BasicConstants.UPDATE;
			}

			/**
			 * this condition to be removed once data is coming from front end.
			 */

			String loggeduser = null;
			if (!BasicUtils.isEmpty(userId)) {
				loggeduser = userId;
			} else {
				loggeduser = "1234"; // setting dummy user as of now needs to be
				// removed
			}

			String pageName = BasicConstants.HYPOGLYCEMIA;
			logService.saveLog(desc, action, loggeduser, uhid, pageName);

		} catch (

		Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("Not saved successfully");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, userId,
					saHypoglycemia.getUhid(), "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));

		}

		return obj;

	}

	public String generateInactiveNotesHypoglycemia(InicuDao inicuDaoObj, String uhid, String continuedMedication)
			throws InicuDatabaseExeption {
		if (inicuDaoObj != null) {
			inicuDao = inicuDaoObj;
		}
		// Creating Inactive Notes from here
		String progressNotesHypoglycemia = "";
		String htmlNextLine = System.getProperty("line.separator");
		try {
			// Fetching Current Episode Number for Hypoglycemia
			int epCountHypoglycemia = 1;
//      String queryEpCountHypoglycemia = "SELECT MAX(episode_number) FROM sa_hypoglycemia WHERE uhid='" + uhid
//          + "'";
			List<Integer> epCountListHypoglycemia = inicuDao.getListFromNativeQuery(
					HqlSqlQueryConstants.getMaxEpisodeOfAssessment(BasicConstants.SA_HYPOGLYCEMIA, uhid));
			if (!BasicUtils.isEmpty(epCountListHypoglycemia)) {
				epCountHypoglycemia = epCountListHypoglycemia.get(0).intValue();
				if (epCountHypoglycemia != 1) {
					progressNotesHypoglycemia = "Baby developed " + epCountHypoglycemia + " episodes of Hypoglycemia"
							+ " during hospital stay. " + htmlNextLine + " Episode 1 : ";
				}
			}

			// Old Episodes Inactive Notes List of Hypoglycemia Generating Query
			List<SaHypoglycemia> oldHypoglycemiaList = new ArrayList<SaHypoglycemia>();
			oldHypoglycemiaList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveHypoglycemiaList(uhid));
			if (!BasicUtils.isEmpty(oldHypoglycemiaList)) {
				// Old Episodes Inactive Notes of Hypoglycemia Fetching Function
				String oldInactiveNotes = getOldInactiveNotesHypoglycemia(oldHypoglycemiaList, epCountHypoglycemia);
				progressNotesHypoglycemia += oldInactiveNotes + htmlNextLine;
			}

			// Current Episode
			// progressNotesJaundice += "Episode " + epCountJaundice + " : ";
			if (epCountHypoglycemia > 1) {
				progressNotesHypoglycemia += "Episode " + epCountHypoglycemia + " : ";
			}
			List<SaHypoglycemia> hypoglycemiaList = new ArrayList<SaHypoglycemia>();
			hypoglycemiaList = inicuDao
					.getListFromMappedObjQuery(HqlSqlQueryConstants.getHypoglycemiaList(uhid, epCountHypoglycemia));

			if (!BasicUtils.isEmpty(hypoglycemiaList)) {
				// Age of Onset Data Fetching Function for Hypoglycemia
				String ageOnsetNotes = getAgeOnsetHypoglycemia(hypoglycemiaList);
				progressNotesHypoglycemia += ageOnsetNotes;

				// symptomatic or asymp notes for Hypoglycemia
				String symptomsNotes = getSymptomsNotes(hypoglycemiaList);
				progressNotesHypoglycemia += symptomsNotes;

				String symptomsDuration = getSymptomsDuration(uhid, epCountHypoglycemia);
				progressNotesHypoglycemia += symptomsDuration;

				// Risk Factors Fetching Function
				String riskFactorNotes = getRiskFactorHypoglycemia(hypoglycemiaList);
				progressNotesHypoglycemia += riskFactorNotes;

				// Investigation Order Detail Fetching Function
				String investigationOrderNotes = getInvestigationOrderHypoglycemia(hypoglycemiaList);
				progressNotesHypoglycemia += investigationOrderNotes;

				// Medication of Hypoglycemia Data Fetching Function
				String medicationNotes = getMedicationOfHypoglycemia(hypoglycemiaList);
				progressNotesHypoglycemia += medicationNotes + continuedMedication;

				progressNotesHypoglycemia += htmlNextLine;

				// calculate minimum blood sugar
				float minBloodSugar = calculateMinBloodSugar(uhid);

				// calculate cumulative hypoglycemic events
				float cumulativeCount = calculateCumulativeCountHypoglycemia(uhid);

				// CumulativeCount of hypoglycemia event for given episode
				String cumulativeCountNotes = getCumulativeCount(cumulativeCount, minBloodSugar);
				progressNotesHypoglycemia += cumulativeCountNotes;

				progressNotesHypoglycemia += htmlNextLine;

				// Nutrition notes for hypoglycemia
				String nutritionNotes = getNutritionNotes(hypoglycemiaList, epCountHypoglycemia, uhid);
				progressNotesHypoglycemia += nutritionNotes;

				// Inactive Notes End Here
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return progressNotesHypoglycemia;

	}

	// Old Episodes Inactive Notes of hypoglycemia Fetching Function
	public String getOldInactiveNotesHypoglycemia(List<SaHypoglycemia> oldHypoglycemiaList, int epCountHypoglycemia)
			throws InicuDatabaseExeption {
		String oldInactiveNotes = "";
		try {
			if (epCountHypoglycemia == 1) {
				return oldInactiveNotes;
			}

			for (int i = 0; i < oldHypoglycemiaList.size(); i++) {
				SaHypoglycemia hypoglycemiaObj = (SaHypoglycemia) oldHypoglycemiaList.get(i);
				if (hypoglycemiaObj != null && hypoglycemiaObj.getComment() != null) {
					oldInactiveNotes = hypoglycemiaObj.getComment();
				}
			}

			if (epCountHypoglycemia > 2) {
				oldInactiveNotes = oldInactiveNotes.substring(oldInactiveNotes.indexOf('\n') + 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oldInactiveNotes;
	}

	private String getNutritionNotes(List<SaHypoglycemia> hypoglycemiaList, int epCountHypoglycemia, String uhid) {

		String nutritionNotes, bolusNotes, startingGIRNotes, maxGIRNotes, durationOfDextroseNotes,
				finalNutritionIdListString;
		nutritionNotes = bolusNotes = startingGIRNotes = maxGIRNotes = durationOfDextroseNotes = finalNutritionIdListString = "";
		String fetchNutritionDetailsQuery = "";

		Timestamp firstActiveDate = null;
		List<SaHypoglycemia> firstActiveDateList = inicuDao.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getFirstActiveOfGivenEpisode("SaHypoglycemia", uhid, epCountHypoglycemia));
		if (!BasicUtils.isEmpty(firstActiveDateList)) {
			firstActiveDate = firstActiveDateList.get(0).getAssessmentTime();

		}

		Date date = new Date();
		Timestamp currentday = new Timestamp(date.getTime());
		String queryfetchGIRvalue = "SELECT obj FROM BabyfeedDetail as obj WHERE uhid='" + uhid + "'"
				+ " AND entryDateTime>='" + firstActiveDate + "' AND entryDateTime<='" + currentday
				+ "' order by entryDateTime ";
		List<BabyfeedDetail> feedList = inicuDao.getListFromMappedObjQuery(queryfetchGIRvalue);

		List<Float> girValueArr = new ArrayList<>();
		for (BabyfeedDetail feed : feedList) {
			if (feed.getGirvalue() != null && feed.getGirvalue() != "")
				girValueArr.add(Float.parseFloat(feed.getGirvalue()));
		}

		float maxGIR = 0;
		if(!BasicUtils.isEmpty(girValueArr) && girValueArr.size() > 0)
			maxGIR = Collections.max(girValueArr);

		// fetching dextrose infusion duration
		String durationString = fetchDextroseInfusionDuration(feedList);
		if(!BasicUtils.isEmpty(durationString))
		durationOfDextroseNotes = " Dextrose Infusion was given for duration " + durationString;

		List<String> finalNutritionIdList = new ArrayList<>();
		try {
			for (int i = 0; i < hypoglycemiaList.size(); i++) {
				SaHypoglycemia hypoglycemiaObj = (SaHypoglycemia) hypoglycemiaList.get(i);
				if (hypoglycemiaObj != null && hypoglycemiaObj.getHypoglycemiaid() != null) {
					String hypoglycemiaId = "'" + hypoglycemiaObj.getHypoglycemiaid().toString() + "'";
					finalNutritionIdList.add(hypoglycemiaId);
				}
			}

			if (!BasicUtils.isEmpty(finalNutritionIdList)) {
				finalNutritionIdListString = finalNutritionIdList.toString();
				finalNutritionIdListString = finalNutritionIdListString.replace("[", "");
				finalNutritionIdListString = finalNutritionIdListString.replace("]", "");
			}

			fetchNutritionDetailsQuery = "Select obj from BabyfeedDetail as obj where babyfeedid IN("
					+ finalNutritionIdListString + ") order by creationtime ";
			List<BabyfeedDetail> nutritionList = inicuDao.getListFromMappedObjQuery(fetchNutritionDetailsQuery);
			int isbolusGiven = 0;
			float girValue = 0;
			for (BabyfeedDetail nutritionObj : nutritionList) {
				if (!BasicUtils.isEmpty(nutritionObj.getIsBolusGiven())) {
					if (nutritionObj.getIsBolusGiven()) {
						isbolusGiven++;

					}

				}

				if (girValue == 0) {
					if (!BasicUtils.isEmpty(nutritionObj.getGirvalue())) {
						if (Float.parseFloat(nutritionObj.getGirvalue()) > 0)
							girValue = Float.parseFloat(nutritionObj.getGirvalue());
					}
				}

			}

			if (isbolusGiven > 0)
				bolusNotes = "Bolus was given. ";
//			else
//				bolusNotes = "Bolus was not given. ";

			if (girValue > 0)
				startingGIRNotes = " GIR given at the start of assessment was " + girValue + " mg/kg/min.";

			if (!BasicUtils.isEmpty(maxGIR) && maxGIR > 0)
				maxGIRNotes = " Max GIR recorded as " + maxGIR + " mg/kg/min.";

			nutritionNotes = bolusNotes + startingGIRNotes + maxGIRNotes + durationOfDextroseNotes;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nutritionNotes;
	}

	private String fetchDextroseInfusionDuration(List<BabyfeedDetail> feedList) {

		Timestamp startTime = null, endTime = null;
		long duration = 0;
		startTime = endTime = null;
		Date date = new Date();
		Timestamp currentday = new Timestamp(date.getTime());

		for (BabyfeedDetail babyFeed : feedList) {

			if (babyFeed.getIsReadymadeSolutionGiven()) {
				if (!BasicUtils.isEmpty(babyFeed.getReadymadeTotalFluidVolume())
						&& babyFeed.getReadymadeTotalFluidVolume() > 0 && startTime == null) {
					startTime = (Timestamp) babyFeed.getEntryDateTime();
				} else if ((BasicUtils.isEmpty(babyFeed.getReadymadeFluidVolume())
						|| (babyFeed.getReadymadeTotalFluidVolume() == 0)) && startTime != null) {
					endTime = (Timestamp) babyFeed.getEntryDateTime();

					if (!BasicUtils.isEmpty(startTime) && !BasicUtils.isEmpty(endTime)) {
						duration += (endTime.getTime() - startTime.getTime());
						startTime = null;
						endTime = null;
					}

				}

			} else if (!(babyFeed.getIsReadymadeSolutionGiven())) {
				if (!BasicUtils.isEmpty(babyFeed.getDextroseVolumemlperday())
						&& babyFeed.getDextroseVolumemlperday() > 0 && startTime == null) {
					startTime = (Timestamp) babyFeed.getEntryDateTime();
				} else if ((BasicUtils.isEmpty(babyFeed.getDextroseVolumemlperday())
						|| (babyFeed.getDextroseVolumemlperday() == 0)) && startTime != null) {

					endTime = (Timestamp) babyFeed.getEntryDateTime();

					if (!BasicUtils.isEmpty(startTime) && !BasicUtils.isEmpty(endTime)) {
						duration += (endTime.getTime() - startTime.getTime());
						startTime = null;
						endTime = null;
					}

				}

			}

		}

		if (!BasicUtils.isEmpty(startTime) && BasicUtils.isEmpty(endTime)) {
			endTime = currentday;
			duration += (endTime.getTime() - startTime.getTime());

			startTime = null;
			endTime = null;
		}

		if (!BasicUtils.isEmpty(duration))
			duration = duration / (1000 * 60);

		String durationString = duratioHelperFunction(duration);

		return durationString;
	}

	private String duratioHelperFunction(long duration) {
		int durationInHours = 0, durationInDays = 0;
		int durationInMin = 0;
		String durationInString = "";

		if (duration >= 1440) {
			if (duration == 1440) {
				durationInString = String.valueOf(duration) + "day";
				return durationInString;
			}

			durationInHours = (int) duration / 60;
			durationInMin = (int) duration % 60;

			if (durationInHours >= 24) {
				durationInDays = (durationInHours == 24) ? 1 : (durationInHours / 24);
				durationInHours = durationInHours % 24;
				if (durationInDays == 1)
					durationInString = String.valueOf(durationInDays) + " day ";
				else
					durationInString = String.valueOf(durationInDays) + " days ";

				if (durationInHours == 1)
					durationInString += String.valueOf(durationInHours) + " hour ";
				else
					durationInString += String.valueOf(durationInHours) + " hours ";
			}

		} else if (duration >= 60) {
			if (duration == 60) {
				durationInString = "1 hour";
				return durationInString;
			}
			durationInHours = (int) (duration / 60);
			durationInMin = (int) (duration % 60);
			durationInString = String.valueOf(durationInHours) + " hours " + String.valueOf(durationInMin) + " min";
		}

		return durationInString;
	}

	private String getSymptomsDuration(String uhid, int episodeNumber) {
		String durationString = "", durationInString = "";
		List<String> symptomsDurationList = new ArrayList<>();

		try {
			List<String> symptoms = Arrays.asList("apnea", "lethargy", "seizures", "rds", "hypothermia");
			long duration = 0;
			int durationInHours = 0, durationInDays = 0;
			int durationInMin = 0;

			for (int i = 0; i < symptoms.size(); i++) {
				duration = fetchSymptomDuration(symptoms.get(i), uhid, episodeNumber);
				if (duration >= 1440) {
					if (duration == 1440) {
						durationInString = String.valueOf(duration) + "day";
						return durationInString;
					}

					durationInHours = (int) duration / 60;
					durationInMin = (int) duration % 60;

					if (durationInHours >= 24) {
						durationInDays = (durationInHours == 24) ? 1 : (durationInHours / 24);
						durationInHours = durationInHours % 24;
						if (durationInDays == 1)
							durationInString = String.valueOf(durationInDays) + " day ";
						else
							durationInString = String.valueOf(durationInDays) + " days ";

						if (durationInHours == 1)
							durationInString += String.valueOf(durationInHours) + " hour ";
						else
							durationInString += String.valueOf(durationInHours) + " hours ";
					}

				} else if (duration >= 60) {
					if (duration == 60) {
						durationInString = "1 hour";
						return durationInString;
					}
					durationInHours = (int) (duration / 60);
					durationInMin = (int) (duration % 60);
					durationInString = String.valueOf(durationInHours) + " hours " + String.valueOf(durationInMin)
							+ " min";
				}

				if(!BasicUtils.isEmpty(durationInString))
				symptomsDurationList.add(durationInString);
			}

			if (!BasicUtils.isEmpty(symptomsDurationList) && symptomsDurationList.size() > 0)
				durationString = " for duration " + StringUtils.collectionToCommaDelimitedString(symptomsDurationList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return durationString;
	}

	private long fetchSymptomDuration(String symptom, String uhid, int episodeNumber) {
		Timestamp startTime = null, endTime = null;
		long duration = 0;
		startTime = endTime = null;

		String fetchDurationQuery = "Select " + symptom + ", assessmentTime from SaHypoglycemia as obj where " + symptom
				+ " IN ('new','resolved') and episodeNumber=" + episodeNumber
				+ " and hypoglycemiaEventStatus='yes' order by assessmentTime";
		List<Object[]> listHypoglycemia = inicuDao.getListFromMappedObjQuery(fetchDurationQuery);

		for (Object[] symptomAndTime : listHypoglycemia) {
			if (symptomAndTime[0].toString().equalsIgnoreCase("new") && startTime == null) {
				startTime = (Timestamp) symptomAndTime[1];
			} else if (symptomAndTime[0].toString().equalsIgnoreCase("resolved") && startTime != null) {
				endTime = (Timestamp) symptomAndTime[1];

				if (!BasicUtils.isEmpty(startTime) && !BasicUtils.isEmpty(endTime)) {
					duration += (endTime.getTime() - startTime.getTime());
					startTime = null;
					endTime = null;
				}
			}
		}

		if (!BasicUtils.isEmpty(startTime)) {
			startTime = null;
			endTime = null;
//      duration += (endTime.getTime() - startTime.getTime());

		}

		duration = duration / (1000 * 60);

		return duration;
	}

	private float calculateCumulativeCountHypoglycemia(String uhid) {
		float count = 0;
		int epCountHypoglycemia = 0;
		List<Integer> epCountListHypoglycemia = inicuDao.getListFromNativeQuery(
				HqlSqlQueryConstants.getMaxEpisodeOfAssessment(BasicConstants.SA_HYPOGLYCEMIA, uhid));
		if (!BasicUtils.isEmpty(epCountListHypoglycemia)) {
			epCountHypoglycemia = epCountListHypoglycemia.get(0).intValue();

		}

		String hypoglycemiaStatus = "yes";
		String fetchCumulativeCountQuery = "Select obj from  SaHypoglycemia as obj where uhid='" + uhid
				+ "' and hypoglycemiaEventStatus='yes' and " + "episodeNumber=" + epCountHypoglycemia
				+ " order by creationtime desc";

		List<SaHypoglycemia> listHypoglycemia = inicuDao.getListFromMappedObjQuery(fetchCumulativeCountQuery);
		if (!BasicUtils.isEmpty(listHypoglycemia)
				&& !BasicUtils.isEmpty(listHypoglycemia.get(0).getCumulativeHypoEvents())) {
			count = listHypoglycemia.get(0).getCumulativeHypoEvents();
		}
		return count;
	}

	private float calculateMinBloodSugar(String uhid) {

		float minRBS = 0;
		int epCountHypoglycemia = 0;
		List<Integer> epCountListHypoglycemia = inicuDao.getListFromNativeQuery(
				HqlSqlQueryConstants.getMaxEpisodeOfAssessment(BasicConstants.SA_HYPOGLYCEMIA, uhid));
		if (!BasicUtils.isEmpty(epCountListHypoglycemia)) {
			epCountHypoglycemia = epCountListHypoglycemia.get(0).intValue();

		}

		Timestamp firstActiveDate = null;
		List<SaHypoglycemia> firstActiveDateList = inicuDao.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getFirstActiveOfGivenEpisode("SaHypoglycemia", uhid, epCountHypoglycemia));
		if (!BasicUtils.isEmpty(firstActiveDateList)) {
			firstActiveDate = firstActiveDateList.get(0).getAssessmentTime();

		}
		Date date = new Date();
		Timestamp currentDay = new Timestamp(date.getTime());

		String minRBSQuery = "";
		minRBSQuery = "Select min(rbs) from NursingVitalparameter as obj where uhid='" + uhid
				+ "' AND rbs is not null AND rbs<40 AND entryDate>='" + firstActiveDate + "' and entryDate <='"
				+ currentDay + "'";
		List<Float> minRBSList = inicuDao.getListFromMappedObjQuery(minRBSQuery);
		if (!BasicUtils.isEmpty(minRBSList))
			minRBS = minRBSList.get(0);

		return minRBS;
	}

	private String getCumulativeCount(float cumulativeCount, float minBloodSugar) {
		String countNotes = "";
		try {
			if (!BasicUtils.isEmpty(cumulativeCount) && cumulativeCount > 0)
				countNotes = "Cumulative episodes of hypoglycemia is " + cumulativeCount;

			if (!BasicUtils.isEmpty(cumulativeCount) && cumulativeCount > 0 && !BasicUtils.isEmpty(minBloodSugar)
					&& minBloodSugar > 0)
				countNotes += " with minimum blood sugar as " + minBloodSugar + " mg/dl.";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return countNotes;

	}

	// Medication of Hypoglycemia Fetching Function
	public String getMedicationOfHypoglycemia(List<SaHypoglycemia> hypoglycemiaList) throws InicuDatabaseExeption {
		String medicationNotesHypoglycemia = "";
		List<String> finalEventIdListHypoglycemia = new ArrayList<>();
		try {
			for (int i = 0; i < hypoglycemiaList.size(); i++) {
				SaHypoglycemia hypoglycemiaObj = (SaHypoglycemia) hypoglycemiaList.get(i);
				if (hypoglycemiaObj != null && hypoglycemiaObj.getHypoglycemiaid() != null) {
					String hypoglycemiaId = "'" + hypoglycemiaObj.getHypoglycemiaid().toString() + "'";
					finalEventIdListHypoglycemia.add(hypoglycemiaId);
				}
			}

			if (!BasicUtils.isEmpty(finalEventIdListHypoglycemia)) {
				String finalEventIdListStringHypoglycemia = finalEventIdListHypoglycemia.toString();
				finalEventIdListStringHypoglycemia = finalEventIdListStringHypoglycemia.replace("[", "");
				finalEventIdListStringHypoglycemia = finalEventIdListStringHypoglycemia.replace("]", "");
				medicationNotesHypoglycemia += sysServiceImpl.getMedicationHelper(finalEventIdListStringHypoglycemia,
						BasicConstants.HYPOGLYCEMIA_STRING,hypoglycemiaList.get(0).getUhid(), false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return medicationNotesHypoglycemia;
	}

	// Investigation Order Detail Fetch Function
	public String getInvestigationOrderHypoglycemia(List<SaHypoglycemia> hypoglycemiaList)
			throws InicuDatabaseExeption {
		String investigationOrderNotesHypoglycemia = "";
		List<String> hypoglycemiaIdList = new ArrayList<String>();
		try {
			for (int i = 0; i < hypoglycemiaList.size(); i++) {
				SaHypoglycemia hypoglycemiaObj = (SaHypoglycemia) hypoglycemiaList.get(i);
				hypoglycemiaIdList.add("'" + hypoglycemiaObj.getHypoglycemiaid().toString() + "'");

			}

			if (!BasicUtils.isEmpty(hypoglycemiaIdList)) {
				String hypoglycemiaIdListString = hypoglycemiaIdList.toString();
				hypoglycemiaIdListString = hypoglycemiaIdListString.replace("[", "");
				hypoglycemiaIdListString = hypoglycemiaIdListString.replace("]", "");
				List<String> investigationOrderHelperList = new ArrayList<>();
				investigationOrderHelperList = sysServiceImpl.getInvestigationOrderHelper(hypoglycemiaIdListString,
						BasicConstants.HYPOGLYCEMIA_STRING);
				if (!BasicUtils.isEmpty(investigationOrderHelperList)) {
					Set<String> uniqueInvestigationOrderHelperList = new HashSet<String>(investigationOrderHelperList);
					String uniqueInvestigationOrderHelperListString = uniqueInvestigationOrderHelperList.toString();
					uniqueInvestigationOrderHelperListString = uniqueInvestigationOrderHelperListString.replace("[",
							"");
					uniqueInvestigationOrderHelperListString = uniqueInvestigationOrderHelperListString.replace("]",
							"");
					
					if(uniqueInvestigationOrderHelperListString.indexOf(",")!=-1) {
						uniqueInvestigationOrderHelperListString = sysServiceImpl.formatString(uniqueInvestigationOrderHelperListString);
					}

					if (uniqueInvestigationOrderHelperList.size() == 1) {
						investigationOrderNotesHypoglycemia += "Investigation done during this episode was "
								+ uniqueInvestigationOrderHelperListString + ". ";

					} else {
						investigationOrderNotesHypoglycemia += "Investigations done during this episode were "
								+ uniqueInvestigationOrderHelperListString + ". ";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return investigationOrderNotesHypoglycemia;
	}

	private String getRiskFactorHypoglycemia(List<SaHypoglycemia> hypoglycemiaList) throws InicuDatabaseExeption {
		String riskFactors = "";
		SaHypoglycemia hypoglycemiaObj = null;
		try {

			if (!BasicUtils.isEmpty(hypoglycemiaList)) {
				if (hypoglycemiaList.size() == 1)
					hypoglycemiaObj = hypoglycemiaList.get(0);
				else
					hypoglycemiaObj = hypoglycemiaList.get(hypoglycemiaList.size() - 1);

			}

			if (!BasicUtils.isEmpty(hypoglycemiaObj.getRiskFactors())) {
				if(hypoglycemiaObj.getRiskFactors().indexOf(",")!=-1) {
				 String str = sysServiceImpl.formatString(hypoglycemiaObj.getRiskFactors());
				 hypoglycemiaObj.setRiskFactors(str);
			}
				riskFactors = "The risk factors were " + hypoglycemiaObj.getRiskFactors();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return riskFactors;
	}

	private String getSymptomsNotes(List<SaHypoglycemia> hypoglycemiaList) {
		String symptomsNotes = "";
		String symptoms = "";
		List<String> symptomsList = new ArrayList<>();
		String symptomsString  = "";

		boolean done = false;
		try {
			for (int i = 0; i < hypoglycemiaList.size(); i++) {
				SaHypoglycemia hypoglycemiaObj = (SaHypoglycemia) hypoglycemiaList.get(i);
				if (hypoglycemiaObj != null && done != true && !BasicUtils.isEmpty(hypoglycemiaObj.getSymptomaticStatus())) {
					if(hypoglycemiaObj.getSymptomaticStatus() == true)
					symptomsNotes = " which was symptomatic ";

					if (!BasicUtils.isEmpty(hypoglycemiaObj.getApnea())
							&& !hypoglycemiaObj.getApnea().equalsIgnoreCase("resolved"))
						symptomsList.add("apnea");
					if (!BasicUtils.isEmpty(hypoglycemiaObj.getLethargy())
							&& !hypoglycemiaObj.getLethargy().equalsIgnoreCase("resolved"))
						symptomsList.add("lethargy");
					if (!BasicUtils.isEmpty(hypoglycemiaObj.getSeizures())
							&& !hypoglycemiaObj.getSeizures().equalsIgnoreCase("resolved"))
						symptomsList.add("seizures");
					if (!BasicUtils.isEmpty(hypoglycemiaObj.getRds())
							&& !hypoglycemiaObj.getRds().equalsIgnoreCase("resolved"))
						symptomsList.add("RDS");
					if (!BasicUtils.isEmpty(hypoglycemiaObj.getHypothermia())
							&& !hypoglycemiaObj.getHypothermia().equalsIgnoreCase("resolved"))
						symptomsList.add("hypothermia");

					done = true;

				}
			}
			
			symptomsString = symptomsList.toString().replace("[", "").replace("]", "");
			
			if(symptomsString.indexOf(",")!=-1) {
				 symptomsString = sysServiceImpl.formatString(symptomsString);
			}

			if (true && !BasicUtils.isEmpty(symptomsList))
				symptomsNotes += " with " + symptomsString + " ";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return symptomsNotes;
	}

	// Functions for Hypoglycemia
	// Age Onset Data Fetching Function
	public String getAgeOnsetHypoglycemia(List<SaHypoglycemia> hypoglycemiaList) throws InicuDatabaseExeption {
		String ageOnSetNotesHypoglycemia = "";
		boolean done = false;
		try {
			for (int i = 0; i < hypoglycemiaList.size(); i++) {
				SaHypoglycemia hypoglycemiaObj = (SaHypoglycemia) hypoglycemiaList.get(i);
				if (hypoglycemiaObj != null && done != true && hypoglycemiaObj.getAgeatonset() != null) {
					if (Integer.parseInt(hypoglycemiaObj.getAgeatonset()) == 0) {
						ageOnSetNotesHypoglycemia += "Baby developed Hypoglycemia at birth. ";
					} else {
						if (hypoglycemiaObj.getAgeOfOnsetInHours() != null
								&& hypoglycemiaObj.getAgeOfOnsetInHours() == true) {
							ageOnSetNotesHypoglycemia += "Baby developed Hypoglycemia at the age of "
									+ hypoglycemiaObj.getAgeatonset();
							if (Integer.parseInt(hypoglycemiaObj.getAgeatonset()) == 1) {
								ageOnSetNotesHypoglycemia += " hour. ";
							} else {
								ageOnSetNotesHypoglycemia += " hours. ";
							}
						} else {
							ageOnSetNotesHypoglycemia += "Baby developed Hypoglycemia at the age of "
									+ hypoglycemiaObj.getAgeatonset();
							if (Integer.parseInt(hypoglycemiaObj.getAgeatonset()) == 1) {
								ageOnSetNotesHypoglycemia += " day. ";
							} else {
								ageOnSetNotesHypoglycemia += " days. ";
							}
						}
					}
					done = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ageOnSetNotesHypoglycemia;
	}

	public GeneralResponseObject generateHypoglycemiaInactiveNote(HypoglycemiaJSON hypoglycemiaJSON) {
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try {
			if (!BasicUtils.isEmpty(hypoglycemiaJSON.getCurrentHypoglycemia())) {
				String uhid = hypoglycemiaJSON.getCurrentHypoglycemia().getUhid();
				String progressNotes = "";
				if (!BasicUtils.isEmpty(uhid)) {
					SaHypoglycemia saHypoglycemia = hypoglycemiaJSON.getCurrentHypoglycemia();

					List<BabyPrescription> prescriptionList = hypoglycemiaJSON.getPrescriptionList();
					String continuedMedication = "";
					if (!BasicUtils.isEmpty(prescriptionList)) {
						for (int i = 0; i < prescriptionList.size(); i++) {
							BabyPrescription babyPrescription = (BabyPrescription) prescriptionList.get(i);
							if (babyPrescription.getIsContinue() != null && babyPrescription.getIsContinue()
									&& babyPrescription.getContinueReason() != null
									&& babyPrescription.getIsactive()) {
								continuedMedication += babyPrescription.getMedicinename() + " ";

								if (!BasicUtils.isEmpty(babyPrescription.getDose())) {
									continuedMedication += babyPrescription.getDose() + "("
											+ babyPrescription.getDose_unit() + "/"
											+ babyPrescription.getDose_unit_time() + ") ";

								}

								continuedMedication += "prescribed on "
										+ sysServiceImpl.getDateFromTimestamp(babyPrescription.getStartdate())
										+ " and continued due to " + babyPrescription.getContinueReason() + ". ";
							}
						}
						sysServiceImpl.savePrescriptionList(null, prescriptionList, "Hypoglycemia", null,
								saHypoglycemia.getAssessmentTime());
					}

					progressNotes = generateInactiveNotesHypoglycemia(null, uhid, continuedMedication);
				}

				if (!BasicUtils.isEmpty(progressNotes)) {
					generalResponseObject=BasicUtils.getResonseObject(true,200,"Success", progressNotes);
				} else {
					generalResponseObject=BasicUtils.getResonseObject(true,204,"No Content",null);
				}

			} else {
				generalResponseObject=BasicUtils.getResonseObject(true,400,"Bad Request",null);
			}

		} catch (Exception e){
			generalResponseObject=BasicUtils.getResonseObject(false, 500 ,"Internal Server Error", null);
			System.out.printf("Exception: "+e);
			logger.error("Internal Server Error while executing generateRespSupportNote() ", e);
		}
		return generalResponseObject;
	}

//  private void validateUhid(String uhid, Object obj) {
//
//    System.out.println("Inside the validate function");
//    System.out.println(obj.getClass());
//    // TODO Auto-generated method stub
//
//  }

}
