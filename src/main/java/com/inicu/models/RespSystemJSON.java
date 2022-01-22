package com.inicu.models;

public class RespSystemJSON {

	String id;

	String causeOfRDSKey;
	
	String causeOfRDSValue;
	
	String lowestSpo2;
	
	String spo2;
	
	String maxRR;
	
	Boolean apnea;
	
	Boolean mechvent;
	
	Boolean surfactant;
	
	Boolean cld;
	
	String cpap;
	
	String dayOfCpap;
	
	String airvo;
	
	String niv;
	
	String date;
	
	String maxfio2;
	
	String maxbili;
	
	String modeOfVentilationKey;
	
	String modeOfVentilationValue;
	
	String ageOfvent;
	
    String durationOfvent;
	
	String noOfDoses;
	
	String ageAtSurfactant;
	
	String cldStageKey;
	
	String cldStageValue;
	
	Boolean pphn;
	
	Boolean pulmHaem;
	
	String reasonOfMVKey;
	
	String reasonOfMVValue;
	
	String uhid;
	
	boolean edit;
	
	//changes on 09 march 2017
	private String caffeineStopage;
	
	private String caffeineStartage;

	private String ventilationType;
	
	private String comment;
	
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getCauseOfRDSKey() {
		return causeOfRDSKey;
	}

	public void setCauseOfRDSKey(String causeOfRDSKey) {
		this.causeOfRDSKey = causeOfRDSKey;
	}

	public String getCauseOfRDSValue() {
		return causeOfRDSValue;
	}

	public void setCauseOfRDSValue(String causeOfRDSValue) {
		this.causeOfRDSValue = causeOfRDSValue;
	}

	public String getModeOfVentilationKey() {
		return modeOfVentilationKey;
	}

	public void setModeOfVentilationKey(String modeOfVentilationKey) {
		this.modeOfVentilationKey = modeOfVentilationKey;
	}

	public String getModeOfVentilationValue() {
		return modeOfVentilationValue;
	}

	public void setModeOfVentilationValue(String modeOfVentilationValue) {
		this.modeOfVentilationValue = modeOfVentilationValue;
	}

	public String getCldStageKey() {
		return cldStageKey;
	}

	public void setCldStageKey(String cldStageKey) {
		this.cldStageKey = cldStageKey;
	}

	public String getCldStageValue() {
		return cldStageValue;
	}

	public void setCldStageValue(String cldStageValue) {
		this.cldStageValue = cldStageValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getAgeOfvent() {
		return ageOfvent;
	}

	public void setAgeOfvent(String ageOfvent) {
		this.ageOfvent = ageOfvent;
	}

	public String getDurationOfvent() {
		return durationOfvent;
	}

	public void setDurationOfvent(String durationOfvent) {
		this.durationOfvent = durationOfvent;
	}

	public String getNoOfDoses() {
		return noOfDoses;
	}

	public void setNoOfDoses(String noOfDoses) {
		this.noOfDoses = noOfDoses;
	}

	public String getAgeAtSurfactant() {
		return ageAtSurfactant;
	}

	public void setAgeAtSurfactant(String ageAtSurfactant) {
		this.ageAtSurfactant = ageAtSurfactant;
	}

	public Boolean getPphn() {
		return pphn;
	}

	public void setPphn(Boolean pphn) {
		this.pphn = pphn;
	}

	public Boolean getPulmHaem() {
		return pulmHaem;
	}

	public void setPulmHaem(Boolean pulmHaem) {
		this.pulmHaem = pulmHaem;
	}

	public String getMaxfio2() {
		return maxfio2;
	}

	public void setMaxfio2(String maxfio2) {
		this.maxfio2 = maxfio2;
	}

	public String getMaxbili() {
		return maxbili;
	}

	public void setMaxbili(String maxbili) {
		this.maxbili = maxbili;
	}


	public String getLowestSpo2() {
		return lowestSpo2;
	}

	public void setLowestSpo2(String lowestSpo2) {
		this.lowestSpo2 = lowestSpo2;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getMaxRR() {
		return maxRR;
	}

	public void setMaxRR(String maxRR) {
		this.maxRR = maxRR;
	}

	public Boolean getApnea() {
		return apnea;
	}

	public void setApnea(Boolean apnea) {
		this.apnea = apnea;
	}

	public Boolean getMechvent() {
		return mechvent;
	}

	public void setMechvent(Boolean mechvent) {
		this.mechvent = mechvent;
	}

	public Boolean getSurfactant() {
		return surfactant;
	}

	public void setSurfactant(Boolean surfactant) {
		this.surfactant = surfactant;
	}

	public Boolean getCld() {
		return cld;
	}

	public void setCld(Boolean cld) {
		this.cld = cld;
	}

	public String getCpap() {
		return cpap;
	}

	public void setCpap(String cpap) {
		this.cpap = cpap;
	}

	public String getDayOfCpap() {
		return dayOfCpap;
	}

	public void setDayOfCpap(String dayOfCpap) {
		this.dayOfCpap = dayOfCpap;
	}

	public String getAirvo() {
		return airvo;
	}

	public void setAirvo(String airvo) {
		this.airvo = airvo;
	}

	public String getNiv() {
		return niv;
	}

	public void setNiv(String niv) {
		this.niv = niv;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReasonOfMVKey() {
		return reasonOfMVKey;
	}

	public void setReasonOfMVKey(String reasonOfMVKey) {
		this.reasonOfMVKey = reasonOfMVKey;
	}

	public String getReasonOfMVValue() {
		return reasonOfMVValue;
	}

	public void setReasonOfMVValue(String reasonOfMVValue) {
		this.reasonOfMVValue = reasonOfMVValue;
	}

	public String getCaffeineStopage() {
		return caffeineStopage;
	}

	public void setCaffeineStopage(String caffeineStopage) {
		this.caffeineStopage = caffeineStopage;
	}

	public String getCaffeineStartage() {
		return caffeineStartage;
	}

	public void setCaffeineStartage(String caffeineStartage) {
		this.caffeineStartage = caffeineStartage;
	}

	public String getVentilationType() {
		return ventilationType;
	}

	public void setVentilationType(String ventilationType) {
		this.ventilationType = ventilationType;
	}
	
}
