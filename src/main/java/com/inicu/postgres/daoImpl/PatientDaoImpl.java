package com.inicu.postgres.daoImpl;

import java.util.List;

import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.HeadtotoeExamination;
import com.inicu.postgres.entities.MaternalPasthistory;
import com.inicu.postgres.entities.MotherCurrentPregnancy;
import com.inicu.postgres.entities.ParentDetail;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class PatientDaoImpl implements PatientDao {

	@Autowired
	InicuPostgresUtililty inicuPostgersUtil;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	LogsService logService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	InicuDatabaseExeption databaseException;

	@Override
	public Boolean AdmitPatient(BabyDetail babyDetails, ParentDetail parentalDetails,
			MotherCurrentPregnancy motherDetails, BabyVisit visit, HeadtotoeExamination headToToe,
			List<MaternalPasthistory> pastHistories, String operationFrom) {
		String loggedUserID = "";
		String uhid = "";
		try {

			String desc = "";

			ParentDetail parentDetailsReturned = null;
			BabyDetail babyDetailsReturned = null;
			MotherCurrentPregnancy motherDetailsReturned = null;

			if (babyDetails.getBabydetailid() == null) {
				babyDetailsReturned = (BabyDetail) inicuPostgersUtil.updateObject(babyDetails);
			} else {
				babyDetailsReturned = (BabyDetail) inicuPostgersUtil.saveObject(babyDetails);
			}

			desc = objectMapper.writeValueAsString(babyDetailsReturned);
			if (desc.length() > 4000)
				desc = desc.substring(0, 3999);
			logService.saveLog(desc, "Saving BabyDetails", babyDetails.getLoggeduser(), babyDetails.getUhid(),
					"Admission Form");

			loggedUserID = babyDetails.getLoggeduser();
			uhid = babyDetails.getUhid();

			if (parentalDetails.getParentdetailid() == null) {
				parentDetailsReturned = (ParentDetail) inicuPostgersUtil.updateObject(parentalDetails);
			} else {
				parentDetailsReturned = (ParentDetail) inicuPostgersUtil.saveObject(parentalDetails);
			}

			desc = objectMapper.writeValueAsString(parentDetailsReturned);
			if (desc.length() > 4000)
				desc = desc.substring(0, 3999);
			logService.saveLog(desc, "Saving Parent Details", loggedUserID, uhid, "Admission Form");

			if (parentDetailsReturned.getParentdetailid() != null) {

				motherDetails.setMotherid(
						babyDetails.getUhid().toString() + "_" + parentDetailsReturned.getParentdetailid().toString());
				if (motherDetails.getMotherid() == null) {
					motherDetailsReturned = (MotherCurrentPregnancy) inicuPostgersUtil.updateObject(motherDetails);

				} else {
					motherDetailsReturned = (MotherCurrentPregnancy) inicuPostgersUtil.saveObject(motherDetails);
				}
				desc = objectMapper.writeValueAsString(motherDetails);
				if (desc.length() > 4000)
					desc = desc.substring(0, 3999);
				logService.saveLog(desc, "Saving Parent Details", loggedUserID, uhid, "Admission Form");
			} else {
				System.out.println("Mother details can not be saved..!");
			}

			visit.setAdmission_entry(true);
			if (visit.getVisitid() == null) {
				inicuPostgersUtil.updateObject(visit);
			} else {
				inicuPostgersUtil.saveObject(visit);
			}

			desc = objectMapper.writeValueAsString(visit);
			if (desc.length() > 4000)
				desc = desc.substring(0, 3999);
			logService.saveLog(desc, "Saving Baby Visit", loggedUserID, uhid, "Admission Form");

			if (headToToe.getAnomalyValue() != null) {
				if (BasicUtils.isEmpty(headToToe.getUhid())) {
					headToToe.setUhid(babyDetails.getUhid());
				}
				inicuDao.saveObject(headToToe);
				desc = objectMapper.writeValueAsString(headToToe);
				if (desc.length() > 4000)
					desc = desc.substring(0, 3999);
				logService.saveLog(desc, "Saving Head To Toe Details", loggedUserID, uhid, "Admission Form");
			}

			if (!BasicUtils.isEmpty(pastHistories)) {
				for (MaternalPasthistory pastHistory : pastHistories) {
					if (pastHistory.getPastDeliveryYear() != null) {
						if (BasicUtils.isEmpty(pastHistory.getUhid())) {
							pastHistory.setUhid(babyDetails.getUhid());
						}
						inicuDao.saveObject(pastHistory);
						desc = objectMapper.writeValueAsString(pastHistory);
						if (desc.length() > 4000)
							desc = desc.substring(0, 3999);
						logService.saveLog(desc, "Saving Baby Past History", loggedUserID, uhid, "Admission Form");
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUserID,
					uhid, "Get Usage Data", BasicUtils.convertErrorStacktoString(ex));
			return false;
		}
		return true;
	}

	@Override
	public List getListFromMappedObjNativeQuery(String queryStr) {
		List listObj = null;
		try {
			listObj = inicuPostgersUtil.executeMappedObjectCustomizedQuery(queryStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public void updateOrDeleteQuery(String query) {
		try {
			inicuPostgersUtil.updateOrDelQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object saveObject(Object obj) throws Exception {
		return inicuPostgersUtil.saveObject(obj);
	}

	@Override
	public void executeInsertQuery(String query) {
		inicuPostgersUtil.executeInsertQuery(query);
	}

	@Override
	public List getListFromNativeQuery(String queryStr) {
		return inicuPostgersUtil.getListFromNativeQuery(queryStr);
	}

	@Override
	public void updateObject(Object obj) throws Exception {
		inicuPostgersUtil.updateObject(obj);
	}

	@Override
	public void updateOrDeleteNativeQuery(String query) {
		// TODO Auto-generated method stub
		try {
			inicuPostgersUtil.updateOrDelNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
