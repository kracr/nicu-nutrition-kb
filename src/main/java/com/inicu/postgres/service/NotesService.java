package com.inicu.postgres.service;

import java.sql.Timestamp;
import java.util.List;

import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface NotesService {

	// Sourabh added on 25-04-17
	NursingDailyProgressPojo getNursingDailyProgressInfo(String entryDate, String dob, String doa, String uhid,
			String loggedInUser);

	// workspace

	NursingObservationPojo getNursingObservationInfo(String tabType, String entryDate, String uhid, String loggedInUser,
			NursingObservationPojo nursingInfoPojo);

	// end
	// --------------------------------------------------------------------------------------------------------
	// Priya workspace

	public ResponseMessageWithResponseObject setNursingObservationInfo(String tabType,
			NursingObservationPojo nursingInfo, String uhid, String entryDate);

	public ResponseMessageWithResponseObject setNursingDropDowns();

	ResponseMessageWithResponseObject saveBabyVisit(NursingDailyProgressPojo babyVisit, String uhid, String entryDate, String userName);

	NursingObservationPojoAll getModuleNursingNotesAll(String date, String uhid);

	TpnFeedPojo getTpnFeedDetails(String uhid, String loggedUser);

	ResponseMessageWithResponseObject saveTpnFeedDetails(TpnFeedPojo tpnFeedObj, String uhid, String loggedUser);

	BabyHistoryObj getBabyHistory(String uhid, String loggedUser);

	AssessmentNursingOrderPOJO getAssessmentNursingOrder(String uhid);

	ResponseMessageWithResponseObject saveAssessmentNursingOrder(String uhid,
			AssessmentNursingOrderPOJO nursingOrderObj);

	NursingOrderJaundiceJSON getJaundiceNursingOrder(String uhid);

	ResponseMessageWithResponseObject saveJaundiceNursingOrder(String uhid, NursingOrderJaundiceJSON nursingOrderObj);

	NursingOrderRdJSON getRdNursingOrder(String uhid);

	ResponseMessageWithResponseObject saveRdNursingOrder(String uhid, NursingOrderRdJSON nursingOrderObj);

	NursingOrderApneaJSON getApneaNursingOrder(String uhid);

	ResponseMessageWithResponseObject saveApneaNursingOrder(String uhid, NursingOrderApneaJSON nursingOrderObj);

	ResponseMessageWithResponseObject saveNursingEpisode(NursingEpisode nursingEpisodeObj);
	
	ResponseMessageWithResponseObject saveDeclinedApneaEvent(DeclinedApneaEvent declinedApneaEvent);

	NursingInputsPOJO getNursingInputs(String uhid, String fromTime, String toTime);

	NursingInputsPOJO saveNursingInputs(String uhid, String loggedUser, String fromTime, String toTime,
			NursingIntakeOutput currentNursingObj);

	DoctorNotesPOJO getDoctorNotes(DoctorNotesPrintInfoPOJO doctorNotesPrintInfo);

	NursingBloodGas getBabyBloodGasInfoBySampleId(String uhid, String orderID);

	NursingDailyProgressPojo getAnthropometry(String uhid);

	NursingVitalPrintJSON getNursingVitalPrint(String uhid, String fromTime, String toTime);

	AnthropometryPrintJSON getAnthropometryPrint(String uhid, String fromTime, String toTime);

	NursingEventPrintJSON getNursingEventPrint(String uhid, String fromTime, String toTime);

	NursingVentilatorPrintJSON getNursingVentilatorPrint(String uhid, String fromTime, String toTime);

	NursingBGPrintJSON getNursingBGPrint(String uhid, String fromTime, String toTime);

	NursingIntakeOutputPrintJSON getNursingIntakeOutputPrint(String uhid, String fromTime, String toTime);

	NursingNotesPojo getNursingNotes(String uhid, String fromTime, String toTime);

	NursingAllDataPOJO getAllNursingData(String uhid, String fromTime, String toTime,String branchname);

	NursingNotesPojo getAdvNursingNotes(String uhid, String fromTime, String toTime);

	NursingNotesPojo setNursingNotes(NursingNotesPojo nursingNotesObj);

	List<NurseExecutionOrders> getDoctorOrder(String uhid, String fromDateStr);

	NursingVitalparameter getVitalByDate(String uhid, String entryDate);
	
	List<NursingVitalparameter> getRoundAndAssessmentData(String uhid, String entryDate);
	
	NursingVentilaor getVentilatorInfoByDate(String uhid, String entryDate);

	DailyProgressNotes getDailyProgressNotes(String uhid, String dateStr, String fromTime, String toTime, String branchName,
											 String loggedInUser);

	DailyProgressNotes setDailyProgressNotes(DailyProgressNotes progressNote);

	DoctorBloodProductPojo getDoctorBloodProducts(String uhid, String branchName);

	ResponseMessageWithResponseObject setDoctorBloodProducts(DoctorBloodProducts currentObj, String branchName);
	
	DoctorOrdersPOJO getDoctorOrders(String uhid, String dateStr);

	CgaAndDolPOPJO getCgaAndDol(String uhid, String creationtime);
	
	List<ExportAnthropometryCsvData> getAnthropometryCsvData(String uhid);
	

	VitalParamtersResponse getPatientChartByUhid(String uhid,String fromDate,String toDate);

	float getCalculatedPNRate (String uhid,int updateRate,DoctorBloodProducts currentObj);

	GeneralResponseObject getEditedDoctorNotes(String uhid, Timestamp dateString, String branchName, String loggedUser, String fromwhere);
	GeneralResponseObject getDoctorEditedNotesList(String uhid, String branchname, String fromWhere);
	GeneralResponseObject saveEditedDoctorNotes(EditedDoctorNotes editedDoctorNotes);

	ResponseMessageObject deleteNursingNotes(String uhid, Long nursingNotesId);
	
	List<NurseExecutionOrders> saveNursingExecution(String uhid, String fromDateStr, List<NurseExecutionOrders> doctorNotesObj);

	String getSepsisType(String diagnosis,String uhid);
}