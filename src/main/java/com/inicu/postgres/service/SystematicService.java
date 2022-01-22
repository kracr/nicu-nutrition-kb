package com.inicu.postgres.service;

import java.sql.Timestamp;

import java.util.List;
import java.util.Map;

import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public interface SystematicService {

	SysJaundJSON getJaundice(String uhid, String loggedUser) throws InicuDatabaseExeption;
	
	SaMiscellaneousJSON getMiscellaneous(String uhid, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveJaundice(SysJaundJSON jaundice, String userId) throws InicuDatabaseExeption;
	
	ResponseMessageObject saveMiscellaneous(SaMiscellaneousJSON miscellaneous, String userId) throws InicuDatabaseExeption;

	ResponseMessageObject saveMiscellaneous2(SaMiscellaneousJSON miscellaneous2, String userId) throws InicuDatabaseExeption;
	
	AssessmentRespSystemPOJO getRespSystem(String uhid, String loggedUser) throws InicuDatabaseExeption;
	
	SaRespPphn getInoData(String uhid, String loggedUser) throws InicuDatabaseExeption;

	AssessmentCNSSystemPOJO getCnsSystemObj(String uhid, String loggedUser, String eventName);

	SepsisMasterJSON getSepsis(String trim, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveSepsis(SaSepsi sepsis, String userId) throws InicuDatabaseExeption;

	// ResponseMessageObject saveResp(RespSystemJSON respSystem, String userId)
	// throws InicuDatabaseExeption;

	CardiacMasterJSON getCardiac(String uhid, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveCardiac(SaCardiac cardiac, String userId) throws InicuDatabaseExeption;

	FeedingGrowthMasterJSON getFeedGrowth(String uhid, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveFeedGrowth(SaFeedgrowth feedGrowth, String userId) throws InicuDatabaseExeption;

	SaFollowupMasterJson getFollowup(String trim, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveFollowup(SaFollowup saFollowup, String userId) throws InicuDatabaseExeption;

	RenalFailureJSON getRenal(String uhid, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveRenalJson(RenalFailureJSON renalFailureJSON, String userId) throws InicuDatabaseExeption;

	MiscMasterJSON getMisc(String trim, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveMisc(SaMisc misc, String userId) throws InicuDatabaseExeption;

	ResponseMessageWithResponseObject saveCnsSystem(AssessmentCNSSystemPOJO cnsSystemObj);

	ScoreSilverman getSilvermanScore(Long silvermanId);

	ScoreDownes getDownesScore(Long downesId);

	ResponseMessageWithResponseObject saveSilvermanScore(ScoreSilverman silvermanScore);

	ResponseMessageWithResponseObject saveDownesScore(ScoreDownes downesScore);

	SepsisScoreJSON getSepsisScore(String uhid);

	ResponseMessageWithResponseObject saveSepsisScore(ScoreSepsis sepsisScore);

	HIEScoreJSON getHIEScore(String uhid);

	ResponseMessageWithResponseObject saveHIEScore(ScoreHIE hieScore);

	ResponseMessageWithResponseObject saveIVHScore(ScoreIVH ivhScore);

	IVHScoreJSON getIVHScore(String uhid);

	BindMasterJSON getBindScore(Long bindscoreid);

	ResponseMessageWithResponseObject saveBindScore(ScoreBind bindScore);

	PainMasterJSON getPainScore(String uhid);

	ResponseMessageWithResponseObject savePainScore(ScorePain painScore);

	List getPrintAssessmentModuleData(AssessmentPrintInfoObject printRecordObject);

	RespSystemDropDowns getRespiratoryDropDowns(String uhid);

	ResponseMessageWithResponseObject saveRespSystem(AssessmentRespSystemPOJO respSystem);

	ResponseMessageWithResponseObject saveRds(AssessmentRespSystemPOJO respSystem, Timestamp entryTime);

	ResponseMessageWithResponseObject saveApnea(AssessmentRespSystemPOJO respSystem, Timestamp entryTime);

	ResponseMessageWithResponseObject saveRespOther(AssessmentRespSystemPOJO respSystem);

	ResponseMessageWithResponseObject saveRespBpd(AssessmentRespSystemPOJO respSystem);

	ResponseMessageWithResponseObject savePphn(AssessmentRespSystemPOJO respSystem, Timestamp entryTime);

	ResponseMessageWithResponseObject saveMetabolicSystem(AssessmentMetabolicSystemPOJO metabolicSystemObj);

	ResponseMessageWithResponseObject savePneumothorax(AssessmentRespSystemPOJO respSystem, Timestamp entryTime);

	
	AssessmentInfectionSystemPOJO getInfectSystemObj(String uhid, String loggedUser);

	ResponseMessageWithResponseObject saveInfectSystem(AssessmentInfectionSystemPOJO infectSystemObj);

	BellMasterJSON getBellScore(Long bellscoreid);

	ResponseMessageWithResponseObject saveBellScore(ScoreBellStage bellScore);

	List<InvestigationOrdered> getJaundiceInvestigationOrdered(String searchUhid, String searchDate);

	ResponseMessageWithResponseObject saveStableNote(StableNotesPOJO stableNote);

	StableNotesPOJO getStableNote(String uhid);

	PneumothoraxPOJO getPneumothorax(String uhid);
	
	AssessmentStatus getAssessmentsStatus(String uhid);

	FeedIntorelanceJSON getFeedIntolerance(String uhid, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveFeedIntoleranceJson(FeedIntorelanceJSON feedIntoleranceJson, String userId) throws InicuDatabaseExeption;

	ResponseMessageObject saveNutritionObject(BabyfeedDetail babyfeedDetail);
	
	PainPOJO getPain(String uhid, String loggedUser);
	
	ResponseMessageObject savePain(PainPOJO pain) throws InicuDatabaseExeption;
	
	ResponseMessageWithResponseObject saveNIPSScore(ScoreNIPS NIPSScore);

	ResponseMessageWithResponseObject savePIPPScore(ScorePIPP PIPPScore);
	
	SysShockJSON getShock(String uhid, String loggedUser) throws InicuDatabaseExeption;
	
	ResponseMessageObject saveShock(SysShockJSON shock, String userId) throws InicuDatabaseExeption;
	
	Float urineOutputDuration(String uhid, Integer urineOutputDuration) throws InicuDatabaseExeption;

	AssessmentStatus getJuandiceHypoglycemiaStatus(String uhid, String status, String assesment);

    Map<String, Object> getHematologyStatus(String uhid);

	GeneralResponseObject generateRespSupportNote(String assesment, String fromWhere, AssessmentRespSystemPOJO respSystem);

	GeneralResponseObject generateJaundiceInactiveNote(SysJaundJSON respSystem);
	GeneralResponseObject generateMedicationSupportInfection(String assesment, String fromWhere,
			AssessmentInfectionSystemPOJO respSystem);

	GeneralResponseObject generateRespSupportCns(String assesment, String fromWhere, AssessmentCNSSystemPOJO cnsSystem);

    GeneralResponseObject generateRenalInactiveNote(RenalFailureJSON renal);

    GeneralResponseObject generateFeedIntoleranceInactiveNote(FeedIntorelanceJSON feedIntolerance);

	GeneralResponseObject generateMiscellaneousInactiveNote(SaMiscellaneousJSON saMiscellaneousJSON,  String assesment);

	GeneralResponseObject generateShockInactiveNote(SysShockJSON sysShockJSON, String fromWhere);

}
