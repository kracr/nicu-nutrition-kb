package com.inicu.models;

public class ExportBedDetailsCSV {

	private String UHID;
	private String CRITICALITY_AT_ADMISSION;
	private String ADMIT_DATE;
	private String BED_NO;
	private String PATIENT_LEVEL;
	private String BABY_NAME;
	private String GA_AT_BIRTH;
	private String BIRTH_WEIGHT;
	private String GENDER;
	private String BRANCHNAME;
	private String BIRTH_DATE;
	private String NEOBOX;
	private String lastUpdatedAt;
	private String ADMISSIONSTATUS;
	private String DEVICENAME;
	private String BABY_TYPE;
	private String PATIENT_STATUS;
	private String BABY_NUM;
	private String ROOM_NAME;
	
	public ExportBedDetailsCSV() {
		super();
		UHID = "";
		CRITICALITY_AT_ADMISSION = "";
		ADMIT_DATE = "";
		BED_NO = "";
		PATIENT_LEVEL = "";
		BABY_NAME = "";
		GA_AT_BIRTH = "";
		BIRTH_WEIGHT = "";
		GENDER = "";
		BRANCHNAME = "";
		BIRTH_DATE = "";
		NEOBOX = "";
		ADMISSIONSTATUS = "";
		DEVICENAME = "";
		BABY_TYPE = "";
		PATIENT_STATUS = "";
		BABY_NUM = "";
		ROOM_NAME = "";
	}
	
	public String getUHID() {
		return UHID;
	}
	public void setUHID(String uHID) {
		UHID = uHID;
	}
	
	public String getCRITICALITY_AT_ADMISSION() {
		return CRITICALITY_AT_ADMISSION;
	}

	public void setCRITICALITY_AT_ADMISSION(String cRITICALITY_AT_ADMISSION) {
		CRITICALITY_AT_ADMISSION = cRITICALITY_AT_ADMISSION;
	}

	public String getADMIT_DATE() {
		return ADMIT_DATE;
	}
	public void setADMIT_DATE(String aDMIT_DATE) {
		ADMIT_DATE = aDMIT_DATE;
	}
	public String getBED_NO() {
		return BED_NO;
	}
	public void setBED_NO(String bED_NO) {
		BED_NO = bED_NO;
	}
	public String getPATIENT_LEVEL() {
		return PATIENT_LEVEL;
	}
	public void setPATIENT_LEVEL(String pATIENT_LEVEL) {
		PATIENT_LEVEL = pATIENT_LEVEL;
	}
	public String getBABY_NAME() {
		return BABY_NAME;
	}
	public void setBABY_NAME(String bABY_NAME) {
		BABY_NAME = bABY_NAME;
	}
	
	public String getGA_AT_BIRTH() {
		return GA_AT_BIRTH;
	}

	public void setGA_AT_BIRTH(String gA_AT_BIRTH) {
		GA_AT_BIRTH = gA_AT_BIRTH;
	}

	public String getBIRTH_WEIGHT() {
		return BIRTH_WEIGHT;
	}
	public void setBIRTH_WEIGHT(String bIRTH_WEIGHT) {
		BIRTH_WEIGHT = bIRTH_WEIGHT;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getBRANCHNAME() {
		return BRANCHNAME;
	}

	public void setBRANCHNAME(String bRANCHNAME) {
		BRANCHNAME = bRANCHNAME;
	}

	public String getBIRTH_DATE() {
		return BIRTH_DATE;
	}

	public void setBIRTH_DATE(String bIRTH_DATE) {
		BIRTH_DATE = bIRTH_DATE;
	}

	public String getNEOBOX() {
		return NEOBOX;
	}

	public void setNEOBOX(String nEOBOX) {
		NEOBOX = nEOBOX;
	}

	public String getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(String lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public String getADMISSIONSTATUS() {
		return ADMISSIONSTATUS;
	}

	public void setADMISSIONSTATUS(String aDMISSIONSTATUS) {
		ADMISSIONSTATUS = aDMISSIONSTATUS;
	}

	public String getDEVICENAME() {
		return DEVICENAME;
	}

	public void setDEVICENAME(String dEVICENAME) {
		DEVICENAME = dEVICENAME;
	}

	public String getBABY_TYPE() {
		return BABY_TYPE;
	}

	public void setBABY_TYPE(String bABY_TYPE) {
		BABY_TYPE = bABY_TYPE;
	}

	public String getPATIENT_STATUS() {
		return PATIENT_STATUS;
	}

	public void setPATIENT_STATUS(String pATIENT_STATUS) {
		PATIENT_STATUS = pATIENT_STATUS;
	}

	public String getBABY_NUM() {
		return BABY_NUM;
	}

	public void setBABY_NUM(String bABY_NUM) {
		BABY_NUM = bABY_NUM;
	}

	public String getROOM_NAME() {
		return ROOM_NAME;
	}

	public void setROOM_NAME(String rOOM_NAME) {
		ROOM_NAME = rOOM_NAME;
	}	
}
