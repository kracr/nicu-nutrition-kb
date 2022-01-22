package com.inicu.models;

import java.sql.Timestamp;
import java.util.List;

import com.inicu.postgres.entities.*;

public class DashboardJSON {

	private boolean isvacant;
	private BedKeyValueObj bed;
	private String condition;
	private int messages;
	private String admitDate;
	private String admittime;
	private String uhid;
	private String level;
	private boolean ecg;
	private boolean oxygen;
	private boolean blood;
	public String HR;	
	public String SPO2;
	public String RR;
	public String PR;
	public String name;
	private String gestation;
	private String birthWeight;
	private String dob;
	private String timeOfBirth;
	private Timestamp dateTimeOfBirth;
	private Timestamp dateTimeOfAdmission;
	private String gender;
	private String comment;
	private String babyRoom;
	private KeyValueObj room;
	private Long deviceTime;
	private Long ventilatorTime;
	private Integer tcbNotificationCount;
	private String tcbNotification;

	private String dayOfLife;
	private Float weightAtAdmission;
	private Float lastDayWeight;
	private Float currentDayWeight;
	private Float difference;
	private String feedDetail;
	private String diagnosis;
	private Boolean isassessmentsubmit;
	private String episodeid;

	private String sys_bp;
	private String dia_bp;
	private String mean_bp;
	private String fio2;
	private String pip;
	private String peep;
	private String babyType;
	private String babyNumber;
	private String address;
	private Float temp;
	private ImagePOJO babyImage;
	private String notes;
	private String ph;
	private String co2;
	private String hco3;
	private String be;
	private String pco2;
	private String boxName;
	private Boolean isDeviceConnected;
	private Boolean isTinyDeviceConnected;
	private String gynaecologist;
	private String gestationDays;
	private String gestationWeeks;
	private String gestationdaysbylmp;
	private String gestationweekbylmp;
	private String ropAlert;
	private String ultrasoundAlert;
	private String ventMode;
	private String nursesName;
	private String antibioticsName;
	private String neonatologist;
	
	// member variable created by umesh
	private String tcb;
	private String abdGrith;
	private String rbs;
	
	private float intake;
	private String output;
	private Integer stoolCount;
	private String apneaDuration;
	private String seizuresDuration;
	private double fluidBalance;
	
	private String otherInfo;
	private String intakeOutput;
	private String enteralStr;
	private List<BabyfeedDetail> feedList;

	private String inOutPatientStatus;

	private AnthropometryProgressRate anthropometryProgressRate;
	private String phoneNumber;
	private boolean audioEnabled;

	public AnthropometryProgressRate getAnthropometryProgressRate() {
		return anthropometryProgressRate;
	}

	public void setAnthropometryProgressRate(AnthropometryProgressRate anthropometryProgressRate) {
		this.anthropometryProgressRate = anthropometryProgressRate;
	}

	public String getIntakeOutput() {
		return intakeOutput;
	}

	public void setIntakeOutput(String intakeOutput) {
		this.intakeOutput = intakeOutput;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	
	public float getIntake() {
		return intake;
	}

	public void setIntake(float intake) {
		this.intake = intake;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String outputString) {
		this.output = outputString;
	}

	public Integer getStoolCount() {
		return stoolCount;
	}

	public void setStoolCount(Integer stoolCount) {
		this.stoolCount = stoolCount;
	}

	public String getApneaDuration() {
		return apneaDuration;
	}

	public void setApneaDuration(String apneaDuration) {
		this.apneaDuration = apneaDuration;
	}

	public String getSeizuresDuration() {
		return seizuresDuration;
	}

	public void setSeizuresDuration(String seizuresDuration) {
		this.seizuresDuration = seizuresDuration;
	}

	public double getFluidBalance() {
		return fluidBalance;
	}

	public void setFluidBalance(double fluidBalance) {
		this.fluidBalance = fluidBalance;
	}

	public String getTcb() {
		return tcb;
	}

	public void setTcb(String tcb) {
		this.tcb = tcb;
	}

	public String getAbdGrith() {
		return abdGrith;
	}

	public void setAbdGrith(String abdGrith) {
		this.abdGrith = abdGrith;
	}

	public String getRbs() {
		return rbs;
	}

	public void setRbs(String rbs) {
		this.rbs = rbs;
	}

	public DashboardJSON() {
		super();
		this.ecg = false;
		this.oxygen = false;
		this.blood = false;
		this.messages = 2;
		this.tcbNotificationCount = 0;
		this.isDeviceConnected = false;
		this.isTinyDeviceConnected = false;
	}

	// current visit baby details
	private Float currentdateweight; // both are max weight of baby till date...

	private Float workingWeight;// both are max weight of baby till date...

	private Float todayWeight;

	public Long getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(Long deviceTime) {
		this.deviceTime = deviceTime;
	}

	public KeyValueObj getRoom() {
		return room;
	}

	public void setRoom(KeyValueObj room) {
		this.room = room;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGestation() {
		return gestation;
	}

	public void setGestation(String gestation) {
		this.gestation = gestation;
	}

	public String getBirthWeight() {
		return birthWeight;
	}

	public void setBirthWeight(String birthWeight) {
		this.birthWeight = birthWeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHR() {
		return HR;
	}

	public void setHR(String hR) {
		HR = hR;
	}

	public String getSPO2() {
		return SPO2;
	}

	public void setSPO2(String sPO2) {
		SPO2 = sPO2;
	}

	public String getRR() {
		return RR;
	}

	public void setRR(String rR) {
		RR = rR;
	}

	public String getPR() {
		return PR;
	}

	public void setPR(String pR) {
		PR = pR;
	}

	public boolean isIsvacant() {
		return isvacant;
	}

	public void setIsvacant(boolean isvacant) {
		this.isvacant = isvacant;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	public String getAdmitDate() {
		return admitDate;
	}

	public void setAdmitDate(String admitDate) {
		this.admitDate = admitDate;
	}

	public String getAdmittime() {
		return admittime;
	}

	public void setAdmittime(String admittime) {
		this.admittime = admittime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public boolean getEcg() {
		return ecg;
	}

	public void setEcg(boolean ecg) {
		this.ecg = ecg;
	}

	public boolean getOxygen() {
		return oxygen;
	}

	public void setOxygen(boolean oxygen) {
		this.oxygen = oxygen;
	}

	public boolean getBlood() {
		return blood;
	}

	public void setBlood(boolean blood) {
		this.blood = blood;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public BedKeyValueObj getBed() {
		return bed;
	}

	public void setBed(BedKeyValueObj bed) {
		this.bed = bed;
	}

	public Float getCurrentdateweight() {
		return currentdateweight;
	}

	public void setCurrentdateweight(Float currentdateweight) {
		this.currentdateweight = currentdateweight;
	}

	public String getBabyRoom() {
		return babyRoom;
	}

	public void setBabyRoom(String babyRoom) {
		this.babyRoom = babyRoom;
	}

	public Float getWorkingWeight() {
		return workingWeight;
	}

	public void setWorkingWeight(Float workingWeight) {
		this.workingWeight = workingWeight;
	}

	public Float getTodayWeight() {
		return todayWeight;
	}

	public void setTodayWeight(Float todayWeight) {
		this.todayWeight = todayWeight;
	}

	public Integer getTcbNotificationCount() {
		return tcbNotificationCount;
	}

	public void setTcbNotificationCount(Integer tcbNotificationCount) {
		this.tcbNotificationCount = tcbNotificationCount;
	}

	public String getTcbNotification() {
		return tcbNotification;
	}

	public void setTcbNotification(String tcbNotification) {
		this.tcbNotification = tcbNotification;
	}

	public String getDayOfLife() {
		return dayOfLife;
	}

	public void setDayOfLife(String dayOfLife) {
		this.dayOfLife = dayOfLife;
	}

	public Float getWeightAtAdmission() {
		return weightAtAdmission;
	}

	public void setWeightAtAdmission(Float weightAtAdmission) {
		this.weightAtAdmission = weightAtAdmission;
	}

	public Float getLastDayWeight() {
		return lastDayWeight;
	}

	public void setLastDayWeight(Float lastDayWeight) {
		this.lastDayWeight = lastDayWeight;
	}

	public Float getCurrentDayWeight() {
		return currentDayWeight;
	}

	public void setCurrentDayWeight(Float currentDayWeight) {
		this.currentDayWeight = currentDayWeight;
	}

	public Float getDifference() {
		return difference;
	}

	public void setDifference(Float difference) {
		this.difference = difference;
	}

	public String getFeedDetail() {
		return feedDetail;
	}

	public void setFeedDetail(String feedDetail) {
		this.feedDetail = feedDetail;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getTimeOfBirth() {
		return timeOfBirth;
	}

	public void setTimeOfBirth(String timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}

	public Boolean getIsassessmentsubmit() {
		return isassessmentsubmit;
	}

	public void setIsassessmentsubmit(Boolean isassessmentsubmit) {
		this.isassessmentsubmit = isassessmentsubmit;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getSys_bp() {
		return sys_bp;
	}

	public void setSys_bp(String sys_bp) {
		this.sys_bp = sys_bp;
	}

	public String getDia_bp() {
		return dia_bp;
	}

	public void setDia_bp(String dia_bp) {
		this.dia_bp = dia_bp;
	}

	public String getMean_bp() {
		return mean_bp;
	}

	public void setMean_bp(String mean_bp) {
		this.mean_bp = mean_bp;
	}

	public String getFio2() {
		return fio2;
	}

	public void setFio2(String fio2) {
		this.fio2 = fio2;
	}

	public String getPip() {
		return pip;
	}

	public void setPip(String pip) {
		this.pip = pip;
	}

	public String getPeep() {
		return peep;
	}

	public void setPeep(String peep) {
		this.peep = peep;
	}

	public String getBabyType() {
		return babyType;
	}

	public String getBabyNumber() {
		return babyNumber;
	}

	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ImagePOJO getBabyImage() {
		return babyImage;
	}

	public void setBabyImage(ImagePOJO babyImage) {
		this.babyImage = babyImage;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}

	public String getHco3() {
		return hco3;
	}

	public void setHco3(String hco3) {
		this.hco3 = hco3;
	}

	public String getBe() {
		return be;
	}

	public void setBe(String be) {
		this.be = be;
	}

	public String getPco2() {
		return pco2;
	}

	public void setPco2(String pco2) {
		this.pco2 = pco2;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public Boolean getIsDeviceConnected() {
		return isDeviceConnected;
	}

	public void setIsDeviceConnected(Boolean isDeviceConnected) {
		this.isDeviceConnected = isDeviceConnected;
	}

	public String getGynaecologist() {
		return gynaecologist;
	}

	public void setGynaecologist(String gynaecologist) {
		this.gynaecologist = gynaecologist;
	}

	public String getGestationDays() {
		return gestationDays;
	}

	public void setGestationDays(String gestationDays) {
		this.gestationDays = gestationDays;
	}

	public String getGestationWeeks() {
		return gestationWeeks;
	}

	public void setGestationWeeks(String gestationWeeks) {
		this.gestationWeeks = gestationWeeks;
	}

	public String getRopAlert() {
		return ropAlert;
	}

	public String getUltrasoundAlert() {
		return ultrasoundAlert;
	}

	public void setRopAlert(String ropAlert) {
		this.ropAlert = ropAlert;
	}

	public void setUltrasoundAlert(String ultrasoundAlert) {
		this.ultrasoundAlert = ultrasoundAlert;
	}

	public Long getVentilatorTime() {
		return ventilatorTime;
	}

	public void setVentilatorTime(Long ventilatorTime) {
		this.ventilatorTime = ventilatorTime;
	}

	public String getVentMode() {
		return ventMode;
	}

	public void setVentMode(String ventMode) {
		this.ventMode = ventMode;
	}

	public String getNursesName() {
		return nursesName;
	}

	public void setNursesName(String nursesName) {
		this.nursesName = nursesName;
	}

	public String getAntibioticsName() {
		return antibioticsName;
	}

	public void setAntibioticsName(String antibioticsName) {
		this.antibioticsName = antibioticsName;
	}

	public String getNeonatologist() {
		return neonatologist;
	}

	public void setNeonatologist(String neonatologist) {
		this.neonatologist = neonatologist;
	}

	public String getGestationdaysbylmp() {
		return gestationdaysbylmp;
	}

	public void setGestationdaysbylmp(String gestationdaysbylmp) {
		this.gestationdaysbylmp = gestationdaysbylmp;
	}

	public String getGestationweekbylmp() {
		return gestationweekbylmp;
	}

	public void setGestationweekbylmp(String gestationweekbylmp) {
		this.gestationweekbylmp = gestationweekbylmp;
	}

	public String getEnteralStr() {
		return enteralStr;
	}

	public void setEnteralStr(String enteralStr) {
		this.enteralStr = enteralStr;
	}

	public List<BabyfeedDetail> getFeedList() {
		return feedList;
	}

	public void setFeedList(List<BabyfeedDetail> feedList) {
		this.feedList = feedList;
	}

	public String getInOutPatientStatus() {
		return inOutPatientStatus;
	}

	public void setInOutPatientStatus(String inOutPatientStatus) {
		this.inOutPatientStatus = inOutPatientStatus;
	}

	public Timestamp getDateTimeOfBirth() {
		return dateTimeOfBirth;
	}

	public void setDateTimeOfBirth(Timestamp dateTimeOfBirth) {
		this.dateTimeOfBirth = dateTimeOfBirth;
	}

	public Timestamp getDateTimeOfAdmission() {
		return dateTimeOfAdmission;
	}

	public void setDateTimeOfAdmission(Timestamp dateTimeOfAdmission) {
		this.dateTimeOfAdmission = dateTimeOfAdmission;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isAudioEnabled() {
		return audioEnabled;
	}

	public void setAudioEnabled(boolean audioEnabled) {
		this.audioEnabled = audioEnabled;
	}

	public Boolean getTinyDeviceConnected() {
		return isTinyDeviceConnected;
	}

	public void setTinyDeviceConnected(Boolean tinyDeviceConnected) {
		isTinyDeviceConnected = tinyDeviceConnected;
	}
}
