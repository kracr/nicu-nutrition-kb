package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.*;

import javax.persistence.Transient;

public class AdvanceAdmitPatientPojo {

	BabyDetail babyDetailObj;
	ParentDetail personalDetailObj;

	ReasonAdmission reasonEmptyObj;
	List<ReasonAdmission> selectedReasonList;

	boolean ballardFlag = false;
	ScoreBallard ballardObj;
	AntenatalHistoryDetail antenatalHistoryObj;

	boolean leveneFlag = false;
	ScoreLevene leveneObj;
	RespSupport respSupportObj;
	BirthToNicu birthToNicuObj;
	OutbornMedicine medicineEmptyObj;
	List<OutbornMedicine> medicineList = new ArrayList<OutbornMedicine>();

	boolean downeFlag = false;
	ScoreDownes downesObj;
	GenPhyExam genPhyExamObj;

	SystemicExam sysAssessmentObj;

	AdmissionNotes admissionNotesObj;

	boolean systemicExamsNotesFlag=false;
    SystemicExaminationNotes systemicExaminationNotes;

	List<KeyValueObj> logoImage;
	
	List<String> educationList;
	
	List<String> occupationList;

	@Transient
	List<KeyValueObj> consultantSignatureImage;

	@Transient
	private String loggedUserFullName;
	private String branchName;
	private String fromWhere;

	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public List<KeyValueObj> getConsultantSignatureImage() {
		return consultantSignatureImage;
	}

	public void setConsultantSignatureImage(List<KeyValueObj> consultantSignatureImage) {
		this.consultantSignatureImage = consultantSignatureImage;
	}

	public AdvanceAdmitPatientPojo() {
		super();
		this.babyDetailObj = new BabyDetail();
		this.personalDetailObj = new ParentDetail();
		this.reasonEmptyObj = new ReasonAdmission();
		this.selectedReasonList = new ArrayList<ReasonAdmission>();
		this.ballardObj = new ScoreBallard();
		this.antenatalHistoryObj = new AntenatalHistoryDetail();
		this.leveneObj = new ScoreLevene();
		this.respSupportObj = new RespSupport();
		this.birthToNicuObj = new BirthToNicu();
		this.medicineEmptyObj = new OutbornMedicine();
		this.medicineList.add(medicineEmptyObj);
		this.downesObj = new ScoreDownes();
		this.genPhyExamObj = new GenPhyExam();
		this.sysAssessmentObj = new SystemicExam();
		this.admissionNotesObj = new AdmissionNotes();
		this.systemicExaminationNotes=new SystemicExaminationNotes();
		this.educationList = new ArrayList<String>();
		this.educationList.add("Profession or Honours");
		this.educationList.add("Graduate");
		this.educationList.add("Intermediate or diploma");
		this.educationList.add("High school certificate");
		this.educationList.add("Middle school certificate");
		this.educationList.add("Primary school certificate");
		this.educationList.add("Illiterate");
		
		this.occupationList = new ArrayList<String>();
		this.occupationList.add("Legislators, Senior Officials & Managers");
		this.occupationList.add("Professionals");
		this.occupationList.add("Technicians and Associate Professionals");
		this.occupationList.add("Clerks");
		this.occupationList.add("Skilled Workers and Shop & Market Sales Worker");
		this.occupationList.add("Skilled Agricultural & Fishery Workers");
		this.occupationList.add("Craft & Related Trade Workers");
		this.occupationList.add("Plant & Machine Operators and Assemblers");
		this.occupationList.add("Elementary Occupation");
		this.occupationList.add("Unemployed");
		

	}

	public boolean isSystemicExamsNotesFlag() {
		return systemicExamsNotesFlag;
	}

	public void setSystemicExamsNotesFlag(boolean systemicExamsNotesFlag) {
		this.systemicExamsNotesFlag = systemicExamsNotesFlag;
	}

	public ParentDetail getPersonalDetailObj() {
		return personalDetailObj;
	}

	public void setPersonalDetailObj(ParentDetail personalDetailObj) {
		this.personalDetailObj = personalDetailObj;
	}

	public BabyDetail getBabyDetailObj() {
		return babyDetailObj;
	}

	public void setBabyDetailObj(BabyDetail babyDetailObj) {
		this.babyDetailObj = babyDetailObj;
	}

	public ReasonAdmission getReasonEmptyObj() {
		return reasonEmptyObj;
	}

	public void setReasonEmptyObj(ReasonAdmission reasonEmptyObj) {
		this.reasonEmptyObj = reasonEmptyObj;
	}

	public List<ReasonAdmission> getSelectedReasonList() {
		return selectedReasonList;
	}

	public void setSelectedReasonList(List<ReasonAdmission> selectedReasonList) {
		this.selectedReasonList = selectedReasonList;
	}

	public BirthToNicu getBirthToNicuObj() {
		return birthToNicuObj;
	}

	public void setBirthToNicuObj(BirthToNicu birthToNicuObj) {
		this.birthToNicuObj = birthToNicuObj;
	}

	public boolean isLeveneFlag() {
		return leveneFlag;
	}

	public void setLeveneFlag(boolean leveneFlag) {
		this.leveneFlag = leveneFlag;
	}

	public ScoreLevene getLeveneObj() {
		return leveneObj;
	}

	public void setLeveneObj(ScoreLevene leveneObj) {
		this.leveneObj = leveneObj;
	}

	public OutbornMedicine getMedicineEmptyObj() {
		return medicineEmptyObj;
	}

	public void setMedicineEmptyObj(OutbornMedicine medicineEmptyObj) {
		this.medicineEmptyObj = medicineEmptyObj;
	}

	public List<OutbornMedicine> getMedicineList() {
		return medicineList;
	}

	public void setMedicineList(List<OutbornMedicine> medicineList) {
		this.medicineList = medicineList;
	}

	public boolean isDowneFlag() {
		return downeFlag;
	}

	public void setDowneFlag(boolean downeFlag) {
		this.downeFlag = downeFlag;
	}

	public ScoreDownes getDownesObj() {
		return downesObj;
	}

	public void setDownesObj(ScoreDownes downesObj) {
		this.downesObj = downesObj;
	}

	public GenPhyExam getGenPhyExamObj() {
		return genPhyExamObj;
	}

	public void setGenPhyExamObj(GenPhyExam genPhyExamObj) {
		this.genPhyExamObj = genPhyExamObj;
	}

	public SystemicExam getSysAssessmentObj() {
		return sysAssessmentObj;
	}

	public void setSysAssessmentObj(SystemicExam sysAssessmentObj) {
		this.sysAssessmentObj = sysAssessmentObj;
	}

	public boolean isBallardFlag() {
		return ballardFlag;
	}

	public void setBallardFlag(boolean ballardFlag) {
		this.ballardFlag = ballardFlag;
	}

	public ScoreBallard getBallardObj() {
		return ballardObj;
	}

	public void setBallardObj(ScoreBallard ballardObj) {
		this.ballardObj = ballardObj;
	}

	public AntenatalHistoryDetail getAntenatalHistoryObj() {
		return antenatalHistoryObj;
	}

	public void setAntenatalHistoryObj(AntenatalHistoryDetail antenatalHistoryObj) {
		this.antenatalHistoryObj = antenatalHistoryObj;
	}

	public RespSupport getRespSupportObj() {
		return respSupportObj;
	}

	public void setRespSupportObj(RespSupport respSupportObj) {
		this.respSupportObj = respSupportObj;
	}

	public AdmissionNotes getAdmissionNotesObj() {
		return admissionNotesObj;
	}

	public void setAdmissionNotesObj(AdmissionNotes admissionNotesObj) {
		this.admissionNotesObj = admissionNotesObj;
	}

	public SystemicExaminationNotes getSystemicExaminationNotes() {
		return systemicExaminationNotes;
	}

	public void setSystemicExaminationNotes(SystemicExaminationNotes systemicExaminationNotes) {
		this.systemicExaminationNotes = systemicExaminationNotes;
	}

	public List<String> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<String> educationList) {
		this.educationList = educationList;
	}

	public List<String> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(List<String> occupationList) {
		this.occupationList = occupationList;
	}

	public String getLoggedUserFullName() {
		return loggedUserFullName;
	}

	public void setLoggedUserFullName(String loggedUserFullName) {
		this.loggedUserFullName = loggedUserFullName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}

	@Override
	public String toString() {
		return "AdvanceAdmitPatientPojo [babyDetailObj=" + babyDetailObj + ", personalDetailObj=" + personalDetailObj
				+ ", reasonEmptyObj=" + reasonEmptyObj + ", selectedReasonList=" + selectedReasonList
				+ ", antenatalHistoryObj=" + antenatalHistoryObj + ", leveneFlag=" + leveneFlag + ", leveneObj="
				+ leveneObj + ", respSupportObj=" + respSupportObj + ", birthToNicuObj=" + birthToNicuObj
				+ ", medicineEmptyObj=" + medicineEmptyObj + ", medicineList=" + medicineList + ", downeFlag="
				+ downeFlag + ", downesObj=" + downesObj + ", genPhyExamObj=" + genPhyExamObj + ", sysAssessmentObj="
				+ sysAssessmentObj + ", admissionNotesObj=" + admissionNotesObj + ", branchName=" + branchName + "]";
	}

}
