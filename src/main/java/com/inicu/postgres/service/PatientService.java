package com.inicu.postgres.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.inicu.models.*;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.MasterAddress;
import com.inicu.postgres.entities.RefBed;
import com.inicu.postgres.entities.RefCriticallevel;
import com.inicu.postgres.entities.RefExamination;
import com.inicu.postgres.entities.RefHeadtotoe;
import com.inicu.postgres.entities.RefHistory;
import com.inicu.postgres.entities.RefLevel;
import com.inicu.postgres.entities.RefRoom;
import com.inicu.postgres.entities.RefSignificantmaterial;
import com.inicu.postgres.entities.ScoreBallard;
import com.inicu.postgres.entities.VwBloodProduct;
import com.inicu.postgres.entities.VwDischargedPatientsFinal;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public interface PatientService {
	public Object AdmitPatient(PatientInfoAdmissonFormObj admissionForm, PatientInfoAddChildObj addChild,
			PatientInfoChildDetailsObj childDetails, String doctorId, String operationFrom)
			throws ParseException, InicuDatabaseExeption;

	public PatientInfoMasterObj getPateintInfoMasterObj(String patientId, String operationFor)
			throws InicuDatabaseExeption;

	public AdmissionFormMasterDropDownsObj getAdmissionFormMasterDropDowns(String branchName);

	public Object addDoctorNotes(DoctorNotesObj doctorNotesObj, String loggedInUserId) throws InicuDatabaseExeption;

	public Object saveDoctorNotes(DashboardJSON doctorNotesObj, String loggedInUserId) throws InicuDatabaseExeption;
	
	public Boolean isExistingUhid(String string);

	public NursingNotesDropDownsObj getNursingNotesDropDowns();

	public List<VwBloodProduct> getBloodProductsInfo(String uhid, String date);

	public ResponseMessageWithResponseObject addBloodProduct(VwBloodProduct bloodProduct, String loggedInUserId)
			throws InicuDatabaseExeption;

	public Object savePupilReactivity(String uhid, String reactivity, String pupilsize, String equality,
			String comments, java.sql.Date sqlPresentDate, String loggedUserId);

	public HashMap<String, List<String>> getDoctorNotesDropDowns();

	public List<HashMap<String, String>> getDoctorNotesGraph(String uhid, String todayDate, String dob);

	public RefCriticallevel getCriticalityInfo(Object object, Object criticalityDesc);

	public RefExamination getExaminationInfo(Object examId, Object examDesc);

	public RefBed getRefBedInfo(Object objId, Object objDesc);

	public RefHeadtotoe getRefHeadToToeInfo(Object objId, Object objDesc);

	public RefHistory getRefHistoryInfo(Object objId, Object objDesc);

	public RefLevel getRefLevelInfo(Object objId, Object objDesc);

	public RefRoom getRefRoomInfo(Object objId, Object objDesc);

	public RefSignificantmaterial getRefSignificantMaterialInfo(Object objId, Object objDesc);

	public void testSaveObject(Object pres);

	public HashMap<Object, Object> printAssessmentProgressNotes(AssessmentPrintInfoObject printObj);

	public List<VwDischargedPatientsFinal> getDischargedPatients(String uhid, String startDate, String endDate, String branchName);

	public DischargedSummaryObj getDischargeSummary(String uhid, String episodeId, String fromTime, String toTime);

	public HashMap<Object, Object> getDischargeSummaryPrintRecords(String uhid);

	public ResponseMessageWithResponseObject saveDischargeSummary(DischargedSummaryObj dischargedSummary);

	public List<DashboardJSON> getHl7PatientsList();

	public ResponseMessageWithResponseObject uplodaBabyDocument(String file, String uhid, String desc);

	public List<HashMap<Object, Object>> getBabyDocuments(String string);

	public ResponseMessageWithResponseObject saveBallardScore(ScoreBallard ballardScore);

	public ResponseMessageWithResponseObject saveApgarScore(ApgarScoreObj apgarScore);

	public ResponseMessageWithResponseObject deleteBabyDocument(String docId, String loggedUserId);

	public void updateDetailsOnReadmit(BabyDetail babyDetail, String newbedno, java.sql.Date reAdmissionDate)
			throws InicuDatabaseExeption;

	public BabyDetail getBabyDetails(String uhid) throws InicuDatabaseExeption;

	List getRefObj(String query);

	Object getDoctorNotes(String uhid, String dob, String doa, String sex, String doctorId, String entryDate,
			String noteEntryTime) throws InicuDatabaseExeption;

	HashMap<Object, Object> printDoctorNotes(AssessmentPrintInfoObject printObj);

	public ResponseMessageWithResponseObject submitAdvanceAdmissionForm(AdvanceAdmitPatientPojo registrationObj,
			String loggedUser);

	public AdvanceAdmitPatientPojo getAdvanceAdmissionForm(String uhid, String loggedInUser);

	public HashMap<String, String> getDistrictList(String state);
	
	public HashMap<String, MasterAddress> getCityAllList(String state);

	public HashMap<String, MasterAddress> getAddressList(String state, String district);

	AdvanceAdmitPatientPojo getAdvanceAdmissionFormReadmit(String uhid);

	MandatoryFormPojo getMandatoryFormData(String uhid);

	ResponseMessageWithResponseObject submitMandatoryForm(MandatoryFormPojo mandatoryFormObj, String uhid,
			String loggedUser);

	GeneralResponseObject getPrintData(printDataRequestObject requestObject, String branchName, String printDate, String loggedUser);

	GeneralResponseObject getPrintBabies(String branchName,String date);
}
