package com.inicu.models;

//import com.sun.tools.corba.se.idl.constExpr.Times;

import javax.persistence.Transient;
import java.sql.Timestamp;

public class AssessmentStatus {
	
	private Boolean respiratory;
	private Boolean jaundice;
	private Boolean cns;
	private Boolean infection;
	private Boolean renal;
	private Boolean feedIntolerance;
	private Boolean metabolic;
	private Boolean misc;
	private Boolean pain;
	private Boolean shock;

	@Transient
	private String tcb;

	@Transient
	private Timestamp lastestTcbTime;

	@Transient
	private String rbs;

	@Transient
	private Timestamp latestRbsTime;

	@Transient
	private boolean isTSB;

	@Transient
	private Timestamp labTestDateTime;
	
	public Boolean getRenal() {
		return renal;
	}
	public void setRenal(Boolean renal) {
		this.renal = renal;
	}
	public Boolean getRespiratory() {
		return respiratory;
	}
	public void setRespiratory(Boolean respiratory) {
		this.respiratory = respiratory;
	}
	public Boolean getJaundice() {
		return jaundice;
	}
	public void setJaundice(Boolean jaundice) {
		this.jaundice = jaundice;
	}
	public Boolean getCns() {
		return cns;
	}
	public void setCns(Boolean cns) {
		this.cns = cns;
	}
	public Boolean getInfection() {
		return infection;
	}
	public void setInfection(Boolean infection) {
		this.infection = infection;
	}
	public Boolean getFeedIntolerance() {
		return feedIntolerance;
	}
	public void setFeedIntolerance(Boolean feedIntolerance) {
		this.feedIntolerance = feedIntolerance;
	}
	public Boolean getMetabolic() {
		return metabolic;
	}
	public void setMetabolic(Boolean metabolic) {
		this.metabolic = metabolic;
	}
	public Boolean getMisc() {
		return misc;
	}
	public void setMisc(Boolean misc) {
		this.misc = misc;
	}
	public Boolean getPain() {
		return pain;
	}
	public void setPain(Boolean pain) {
		this.pain = pain;
	}
	public Boolean getShock() {
		return shock;
	}
	public void setShock(Boolean shock) {
		this.shock = shock;
	}

	public String getTcb() {
		return tcb;
	}

	public void setTcb(String tcb) {
		this.tcb = tcb;
	}

	public String getRbs() {
		return rbs;
	}

	public void setRbs(String rbs) {
		this.rbs = rbs;
	}

	public boolean isTSB() {
		return isTSB;
	}

	public void setTSB(boolean TSB) {
		isTSB = TSB;
	}

	public Timestamp getLabTestDateTime() {
		return labTestDateTime;
	}

	public void setLabTestDateTime(Timestamp labTestDateTime) {
		this.labTestDateTime = labTestDateTime;
	}

	public Timestamp getLastestTcbTime() {
		return lastestTcbTime;
	}

	public void setLastestTcbTime(Timestamp lastestTcbTime) {
		this.lastestTcbTime = lastestTcbTime;
	}

	public Timestamp getLatestRbsTime() {
		return latestRbsTime;
	}

	public void setLatestRbsTime(Timestamp latestRbsTime) {
		this.latestRbsTime = latestRbsTime;
	}
}
