package com.inicu.models;

import javax.persistence.Column;

public class NeuroVitalsJSON {
	private String nnNeurovitalsTime;
	private String sedationScore;
	private String uhid;
	private Integer gcs;
	private Integer ccp;
	private Integer icp;
    private String loggeduser;
	private String leftPupilsize;
    private String leftReactivity;
    private String rightPupilsize;
	private String rightReactivity;
	
	public Integer getCcp() {
		return this.ccp;
	}

	public void setCcp(Integer ccp) {
		this.ccp = ccp;
	}
	
	public Integer getGcs() {
		return this.gcs;
	}

	public void setGcs(Integer gcs) {
		this.gcs = gcs;
	}

	public Integer getIcp() {
		return this.icp;
	}

	public void setIcp(Integer icp) {
		this.icp = icp;
	}
	public String getNnNeurovitalsTime() {
		return this.nnNeurovitalsTime;
	}

	public void setNnNeurovitalsTime(String nnNeurovitalsTime) {
		this.nnNeurovitalsTime = nnNeurovitalsTime;
	}

	public String getSedationScore() {
		return this.sedationScore;
	}

	public void setSedationScore(String sedationScore) {
		this.sedationScore = sedationScore;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	public String getLeftPupilsize() {
		return this.leftPupilsize;
	}

	public void setLeftPupilsize(String leftPupilsize) {
		this.leftPupilsize = leftPupilsize;
	}

	public String getLeftReactivity() {
		return this.leftReactivity;
	}

	public void setLeftReactivity(String leftReactivity) {
		this.leftReactivity = leftReactivity;
	}
	public String getRightPupilsize() {
		return this.rightPupilsize;
	}

	public void setRightPupilsize(String rightPupilsize) {
		this.rightPupilsize = rightPupilsize;
	}

	public String getRightReactivity() {
		return this.rightReactivity;
	}

	public void setRightReactivity(String rightReactivity) {
		this.rightReactivity = rightReactivity;
	}
	
	
}
