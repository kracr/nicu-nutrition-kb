package com.inicu.postgres.serviceImpl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inicu.models.HearingMasterPojo;
import com.inicu.models.KeyValueItem;
import com.inicu.models.KeyValueObj;
import com.inicu.models.MetabolicMasterPojo;
import com.inicu.models.MiscellaneousMasterPOJO;
import com.inicu.models.NeurologicalMasterPojo;
import com.inicu.models.RefTestslist;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.models.RopMasterPojo;
import com.inicu.models.ScreeningHearingPrintJSON;
import com.inicu.models.ScreeningMetabolicPrintJSON;
import com.inicu.models.ScreeningNeurologicalPrintJSON;
import com.inicu.models.ScreeningRopPrintJSON;
import com.inicu.models.ScreeningUSGPrintJSON;
import com.inicu.models.USGMasterPojo;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.RefOphthalmologist;
import com.inicu.postgres.entities.ScreenHearing;
import com.inicu.postgres.entities.ScreenMetabolic;
import com.inicu.postgres.entities.ScreenMiscellaneous;
import com.inicu.postgres.entities.ScreenNeurological;
import com.inicu.postgres.entities.ScreenRop;
import com.inicu.postgres.entities.ScreenUSG;
import com.inicu.postgres.service.ScreeningService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Service
public class ScreeningServiceImpl implements ScreeningService {

	@Autowired
	InicuDao inicuDao;

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Override
	@SuppressWarnings("unchecked")
	public NeurologicalMasterPojo getNeurological(String uhid) {
		NeurologicalMasterPojo returnObj = new NeurologicalMasterPojo();
		try {
			String neurologicalSql = HqlSqlQueryConstants.getScreeningNeurologicalList(uhid)
					+ " order by screening_time desc";
			List<ScreenNeurological> pastNeurologicalList = inicuDao.getListFromMappedObjQuery(neurologicalSql);
			returnObj.setPastNeurologicalList(pastNeurologicalList);

			// fetch the latest HC value from the baby visit table
			String query = "select currentdateheadcircum,admission_head_circumference,cast(concat(visitdate,' ',visittime) as timestamp) entrytime from baby_visit where uhid ='"+uhid+"' order by cast(concat(visitdate,' ',visittime) as timestamp) desc";
			List<Object[]> res= inicuDao.getListFromNativeQuery(query);
			if(res.size()>0){
				// fetch the last entry
				Object[] lastObject = res.get(0);
				String hc = "";
				String hc_atAdmission = "";
				Timestamp entryTime = null;

				if(!BasicUtils.isEmpty(lastObject[0])){
					hc = lastObject[0].toString();
				}

				if(!BasicUtils.isEmpty(lastObject[1])){
					hc_atAdmission = lastObject[1].toString();
				}

				if(!BasicUtils.isEmpty(lastObject[2])){
					entryTime = (Timestamp)lastObject[2];
				}

				if(hc == null || hc == ""){
					returnObj.getCurrentObj().setHeadCircumference(hc_atAdmission);
				}else{
					returnObj.getCurrentObj().setHeadCircumference(hc);
				}
				returnObj.getCurrentObj().setHcReadingTime(entryTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getNeurological in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveNeurological(String uhid, String loggedUser,
			ScreenNeurological neurologicalObj) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		try {
			inicuDao.saveObject(neurologicalObj);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Neurological Screening Saved Successfully.");
			returnObj.setReturnedObject(getNeurological(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"saveNeurological in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	@Override
	public ResponseMessageWithResponseObject saveMiscellaneous(String uhid, String loggedUser,
			ScreenMiscellaneous miscellaneousObj) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		try {
			inicuDao.saveObject(miscellaneousObj);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Neurological Screening Saved Successfully.");
			returnObj.setReturnedObject(getMiscellaneous(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"saveMiscellaneous in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	public MiscellaneousMasterPOJO getMiscellaneous(String uhid) {
		MiscellaneousMasterPOJO returnObj = new MiscellaneousMasterPOJO();

		try {
			String miscellaneousSql = HqlSqlQueryConstants.getScreeningMiscellaneousList(uhid)
					+ " order by screening_time desc";
			List<ScreenMiscellaneous> pastMiscellaneousList = inicuDao.getListFromMappedObjQuery(miscellaneousSql);
			List<KeyValueItem> screeningList = getScreeningList();
			returnObj.setScreeningList(screeningList);
			
			returnObj.setPastMiscellaneousList(pastMiscellaneousList);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getMiscellaneous in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}
	
	private List<KeyValueItem> getScreeningList() {
		// TODO Auto-generated method stub

		List<KeyValueItem> mainList = new ArrayList<KeyValueItem>();
//		KeyValueItem l31 = new KeyValueItem();
//		l31.setKey("MISC011");
//		l31.setValue("Echo");
//		l31.setItem(null);
//
//		KeyValueItem l32 = new KeyValueItem();
//		l32.setKey("MISC012");
//		l32.setValue("MRI");
//		
//		KeyValueItem l33 = new KeyValueItem();
//		l33.setKey("MISC013");
//		l33.setValue("CT");

//		List<KeyValueItem> level3 = new ArrayList<KeyValueItem>();
//		level3.add(l31);
//		level3.add(l32);
//		level3.add(l33);

		KeyValueItem l11 = new KeyValueItem();
		l11.setKey("MISC02");
		l11.setValue("X-Ray");
		l11.setItem(null);
		
		KeyValueItem l12 = new KeyValueItem();
		l12.setKey("MISC01");
		l12.setValue("Echo");
		l12.setItem(null);
		
		KeyValueItem l14 = new KeyValueItem();
		l14.setKey("MISC04");
		l14.setValue("MRI");
		l14.setItem(null);

		KeyValueItem l15 = new KeyValueItem();
		l15.setKey("MISC05");
		l15.setValue("CT");
		l15.setItem(null);
		
//		KeyValueItem l13 = new KeyValueItem();
//		l13.setKey("MISC03");
//		l13.setValue("Echocardiography");
//		l13.setItem(null);
		
		mainList.add(l11);
		mainList.add(l12);
		//mainList.add(l13);
		mainList.add(l14);
		mainList.add(l15);
		
		return mainList;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScreeningNeurologicalPrintJSON getScreeningNeurologicalPrint(String uhid, String fromTime, String toTime) {
		ScreeningNeurologicalPrintJSON returnObj = new ScreeningNeurologicalPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(Long.parseLong(toTime));

			String neurologicalSql = HqlSqlQueryConstants.getScreeningNeurologicalList(uhid)
					+ " and screening_time >= '" + fromDate + "' and screening_time <= '" + toDate
					+ "' order by screening_time desc";
			List<ScreenNeurological> neurologicalList = inicuDao.getListFromMappedObjQuery(neurologicalSql);
			returnObj.setNeurologicalList(neurologicalList);
			returnObj.setDateofAdmission(getAdmissionDate(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getScreeningNeurologicalPrint in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public HearingMasterPojo getHearing(String uhid) {
		HearingMasterPojo returnObj = new HearingMasterPojo();

		try {
			String hearingSql = HqlSqlQueryConstants.getScreeningHearingList(uhid) + " order by screening_time desc";
			List<ScreenHearing> pastHearingList = inicuDao.getListFromMappedObjQuery(hearingSql);
			returnObj.setPastHearingList(pastHearingList);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getHearing in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveHearing(String uhid, String loggedUser, ScreenHearing hearingObj) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		try {
			inicuDao.saveObject(hearingObj);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Hearing Screening Saved Successfully.");
			returnObj.setReturnedObject(getHearing(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"saveHearing in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScreeningHearingPrintJSON getScreeningHearingPrint(String uhid, String fromTime, String toTime) {
		ScreeningHearingPrintJSON returnObj = new ScreeningHearingPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(Long.parseLong(toTime));

			String hearingSql = HqlSqlQueryConstants.getScreeningHearingList(uhid) + " and screening_time >= '"
					+ fromDate + "' and screening_time <= '" + toDate + "' order by screening_time desc";
			List<ScreenHearing> hearingList = inicuDao.getListFromMappedObjQuery(hearingSql);
			returnObj.setHearingList(hearingList);
			returnObj.setDateofAdmission(getAdmissionDate(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getScreeningHearingPrint in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public RopMasterPojo getRop(String uhid) {
		RopMasterPojo returnObj = new RopMasterPojo();

		try {
			String treatmentSql = HqlSqlQueryConstants.getTreatmentList() + " where category='ROP'";
			List<KeyValueObj> treatmentRop = getRefObj(treatmentSql);
			KeyValueObj otherTreatment = new KeyValueObj();
			otherTreatment.setKey("Other");
			otherTreatment.setValue("Other");
			treatmentRop.add(otherTreatment);
			returnObj.setTreatmentAction(treatmentRop);

			String ropSql = HqlSqlQueryConstants.getScreeningRopList(uhid) + " order by screening_time desc";
			List<ScreenRop> pastRopList = inicuDao.getListFromMappedObjQuery(ropSql);
			returnObj.setPastRopList(pastRopList);
			
			//To get Ophthalmologist Name List
			String ropOphthalmologistNameListQuery = "Select obj from RefOphthalmologist as obj";
			List<RefOphthalmologist> ropOphthalmologistNameList = inicuDao.getListFromMappedObjQuery(ropOphthalmologistNameListQuery);
			returnObj.setOphthalmologistNameList(ropOphthalmologistNameList);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getROP in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveRop(String uhid, String loggedUser, ScreenRop ropObj) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		try {
			if (!BasicUtils.isEmpty(ropObj.getTreatmentList())) {
				ropObj.setTreatmentaction(ropObj.getTreatmentList().toString());
			}
			inicuDao.saveObject(ropObj);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Hearing Screening Saved Successfully.");
			returnObj.setReturnedObject(getRop(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"saveROP in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScreeningRopPrintJSON getScreeningRopPrint(String uhid, String fromTime, String toTime) {
		ScreeningRopPrintJSON returnObj = new ScreeningRopPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(Long.parseLong(toTime));

			String ropSql = HqlSqlQueryConstants.getScreeningRopList(uhid) + " and screening_time >= '" + fromDate
					+ "' and screening_time <= '" + toDate + "' order by screening_time desc";
			List<ScreenRop> ropList = inicuDao.getListFromMappedObjQuery(ropSql);
			returnObj.setRopList(ropList);
			returnObj.setDateofAdmission(getAdmissionDate(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getScreeningRopPrint in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public USGMasterPojo getUSG(String uhid) {
		USGMasterPojo returnObj = new USGMasterPojo();

		try {
			String usgSql = HqlSqlQueryConstants.getScreeningUSGList(uhid) + " order by screening_time desc";
			List<ScreenUSG> pastUSGList = inicuDao.getListFromMappedObjQuery(usgSql);
			returnObj.setPastUSGList(pastUSGList);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getUSG in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveUSG(String uhid, String loggedUser, ScreenUSG usgObj) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		try {
			inicuDao.saveObject(usgObj);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("USG Screening Saved Successfully.");
			returnObj.setReturnedObject(getUSG(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"saveUSG in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScreeningUSGPrintJSON getScreeningUSGPrint(String uhid, String fromTime, String toTime) {
		ScreeningUSGPrintJSON returnObj = new ScreeningUSGPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(Long.parseLong(toTime));

			String usgSql = HqlSqlQueryConstants.getScreeningUSGList(uhid) + " and screening_time >= '" + fromDate
					+ "' and screening_time <= '" + toDate + "' order by screening_time desc";
			List<ScreenUSG> usgList = inicuDao.getListFromMappedObjQuery(usgSql);
			returnObj.setUsgList(usgList);
			returnObj.setDateofAdmission(getAdmissionDate(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getScreeningUSGPrint in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public MetabolicMasterPojo getMetabolic(String uhid) {
		MetabolicMasterPojo returnObj = new MetabolicMasterPojo();

		try {
			String metabolicSql = HqlSqlQueryConstants.getScreeningMetabolicList(uhid)
					+ " order by screening_time desc";

			String queryOrderInvestigation = "SELECT testid, testname FROM ref_testslist where assesment_category = 'Metabolic Screening'";
			List<KeyValueObj> orderInvestigation = getRefObj(queryOrderInvestigation);
			returnObj.setOrderInvestigation(orderInvestigation);

			String queryRefTestsList = "select obj from RefTestslist as obj where obj.assesmentCategory='Metabolic Screening' order by testid asc";
			List<RefTestslist> listRefTests = inicuDao.getListFromMappedObjQuery(queryRefTestsList);
			returnObj.setTestsList(listRefTests);

			List<ScreenMetabolic> pastMetabolicList = inicuDao.getListFromMappedObjQuery(metabolicSql);
			returnObj.setPastMetabolicList(pastMetabolicList);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getMetabolic in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject saveMetabolic(String uhid, String loggedUser,
			MetabolicMasterPojo metabolicObj) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		try {
			ScreenMetabolic currentObj = metabolicObj.getCurrentObj();
			currentObj = (ScreenMetabolic) inicuDao.saveObject(currentObj);

			if (!BasicUtils.isEmpty(metabolicObj.getTestsList())) {
				saveOrderInvestigation(metabolicObj.getTestsList(), currentObj.getScreen_metabolic_id(), uhid,
						loggedUser, "Metabolic Screening", currentObj.getScreening_time());
			}

			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Metabolic Screening Saved Successfully.");
			returnObj.setReturnedObject(getMetabolic(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					"saveMetabolic in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScreeningMetabolicPrintJSON getScreeningMetabolicPrint(String uhid, String fromTime, String toTime) {
		ScreeningMetabolicPrintJSON returnObj = new ScreeningMetabolicPrintJSON();
		try {
			returnObj.setFromTime(Long.parseLong(fromTime));
			returnObj.setToTime(Long.parseLong(toTime));

			Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
			Timestamp toDate = new Timestamp(Long.parseLong(toTime));

			String metabolicSql = HqlSqlQueryConstants.getScreeningMetabolicList(uhid) + " and screening_time >= '"
					+ fromDate + "' and screening_time <= '" + toDate + "' order by screening_time desc";
			List<ScreenMetabolic> metabolicList = inicuDao.getListFromMappedObjQuery(metabolicSql);
			returnObj.setMetabolicList(metabolicList);
			returnObj.setDateofAdmission(getAdmissionDate(uhid));
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					"getScreeningMetabolicPrint in Screening", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	private Long getAdmissionDate(String uhid) {
		Long returnValue = null;
		String babySql = "select dateofadmission from baby_detail where uhid='" + uhid + "' order by creationtime";
		List<Date> babyDetail = inicuDao.getListFromNativeQuery(babySql);
		if (!BasicUtils.isEmpty(babyDetail)) {
			returnValue = babyDetail.get(0).getTime();
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	private List<KeyValueObj> getRefObj(String query) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			List<Object[]> refList = inicuDao.getListFromNativeQuery(query);
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
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"getRefObj in Screening", BasicUtils.convertErrorStacktoString(ex));
		}
		return refKeyValueList;
	}

	private void saveOrderInvestigation(List<RefTestslist> testsList, Long assessmentdid, String uhid, String userId,
			String pageName, Timestamp investigationOrderTime) throws Exception {
		for (RefTestslist tests : testsList) {
			if (tests.getIsSelected() != null && tests.getIsSelected()) {
				InvestigationOrdered investigationOrder = new InvestigationOrdered();
				investigationOrder.setAssesment_type(pageName);
				investigationOrder.setAssesmentid(String.valueOf(assessmentdid));
				investigationOrder.setUhid(uhid);
				investigationOrder.setCategory(tests.getAssesmentCategory());
				investigationOrder.setTestcode(tests.getTestcode());
				investigationOrder.setTestname(tests.getTestname());
				investigationOrder.setTestslistid(tests.getTestid());
				investigationOrder.setInvestigationorder_user(userId);
				investigationOrder.setOrder_status("ordered");
				investigationOrder.setInvestigationorder_time(investigationOrderTime);
				inicuDao.saveObject(investigationOrder);
			}
		}
	}

}
