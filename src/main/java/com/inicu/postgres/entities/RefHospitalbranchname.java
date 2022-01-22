package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_hospitalbranchname")
@NamedQuery(name="RefHospitalbranchname.findAll", query="SELECT r FROM RefHospitalbranchname r")
public class RefHospitalbranchname implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String hospitalbranchid;

	private String hospitalname;
	
	private String branchname;

	private String hospitalimage;
	
	private String helplineNumber;
	
	private String helplineNumberSecond;

	private String contactNumber;
	
	private String lactationNumber;
	
	private String lactationName;
	
	private String nursingPrintFormat;
	
	private Integer fromTime;

	@Column(name="hospital_type")
	private String hospitalType;
	
	private Integer toTime;
	
	private String progressNotesFormat;
	
	private Integer spo2;

	private Integer hr;
	
	private Integer duration;
	
	@Column(name = "nursing_shift_format")
	private Integer nursingShiftFormat;
	
	@Column(name = "nutritional_type")
	private String nutritionalType;
	
	@Column(name = "report_print")
	private String reportPrint;
	
	private String predictions;

	@Column(name = "include_header_space")
	private String includeHeaderSpace;

	public String getHospitalimage() {
		return hospitalimage;
	}

	public void setHospitalimage(String hospitalimage) {
		this.hospitalimage = hospitalimage;
	}

	public String getHospitalbranchid() {
		return hospitalbranchid;
	}

	public void setHospitalbranchid(String hospitalbranchid) {
		this.hospitalbranchid = hospitalbranchid;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getHelplineNumber() {
		return helplineNumber;
	}

	public void setHelplineNumber(String helplineNumber) {
		this.helplineNumber = helplineNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getLactationNumber() {
		return lactationNumber;
	}

	public void setLactationNumber(String lactationNumber) {
		this.lactationNumber = lactationNumber;
	}

	public String getLactationName() {
		return lactationName;
	}

	public void setLactationName(String lactationName) {
		this.lactationName = lactationName;
	}

	public String getHelplineNumberSecond() {
		return helplineNumberSecond;
	}

	public void setHelplineNumberSecond(String helplineNumberSecond) {
		this.helplineNumberSecond = helplineNumberSecond;
	}

	public String getNursingPrintFormat() {
		return nursingPrintFormat;
	}

	public void setNursingPrintFormat(String nursingPrintFormat) {
		this.nursingPrintFormat = nursingPrintFormat;
	}

	public Integer getFromTime() {
		return fromTime;
	}

	public void setFromTime(Integer fromTime) {
		this.fromTime = fromTime;
	}

	public Integer getToTime() {
		return toTime;
	}

	public void setToTime(Integer toTime) {
		this.toTime = toTime;
	}

	public String getProgressNotesFormat() {
		return progressNotesFormat;
	}

	public void setProgressNotesFormat(String progressNotesFormat) {
		this.progressNotesFormat = progressNotesFormat;
	}

	public Integer getSpo2() {
		return spo2;
	}

	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}

	public Integer getHr() {
		return hr;
	}

	public void setHr(Integer hr) {
		this.hr = hr;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getNursingShiftFormat() {
		return nursingShiftFormat;
	}

	public void setNursingShiftFormat(Integer nursingShiftFormat) {
		this.nursingShiftFormat = nursingShiftFormat;
	}

	public String getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(String hospitalType) {
		this.hospitalType = hospitalType;
	}

	public String getNutritionalType() {
		return nutritionalType;
	}

	public void setNutritionalType(String nutritionalType) {
		this.nutritionalType = nutritionalType;
	}

	public String getReportPrint() {
		return reportPrint;
	}

	public void setReportPrint(String reportPrint) {
		this.reportPrint = reportPrint;
	}

	public String getPredictions() {
		return predictions;
	}

	public void setPredictions(String predictions) {
		this.predictions = predictions;
	}

	public String getIncludeHeaderSpace() {
		return includeHeaderSpace;
	}

	public void setIncludeHeaderSpace(String includeHeaderSpace) {
		this.includeHeaderSpace = includeHeaderSpace;
	}
}
