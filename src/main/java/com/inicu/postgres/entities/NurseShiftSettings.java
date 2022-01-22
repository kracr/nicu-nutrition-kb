package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "nurse_shift_settings")
@NamedQuery(name = "NurseShiftSettings.findAll", query = "Select s from NurseShiftSettings s")
public class NurseShiftSettings implements Serializable {

	private static final long serialVersionID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nurse_shift_settings_id")
	private Long nurseShiftSettingsId;

	@Column(name = "shift_type",columnDefinition = "short")
	private short shiftType;

	private String branchname;
	
	@Column(name = "shift1_from_timings")
	private String shift1From;
	
	@Column(name = "shift1_to_timings")
	private String shift1To;

	@Column(name = "shift2_from_timings")
	private String shift2From;

	@Column(name = "shift2_to_timings")
	private String shift2To;

	@Column(name = "shift3_from_timings")
	private String shift3From;

	@Column(name = "shift3_to_timings")
	private String shift3To;

	@Column(name = "shift4_from_timings")
	private String shift4From;

	@Column(name = "shift4_to_timings")
	private String shift4To;


	@Column(name = "shift1_duration")
	private String shift1Duration;

	@Column(name = "shift2_duration")
	private String shift2Duration;

	@Column(name = "shift3_duration")
	private String shift3Duration;

	@Column(name = "shift4_duration")
	private String shift4Duration;

	public NurseShiftSettings(){
		this.shiftType = 4;

		this.shift1From = "12 AM";
		this.shift1To = "6 AM";

		this.shift2From = "6 AM";
		this.shift2To = "12 PM";

		this.shift3From = "12 PM";
		this.shift3To = "6 PM";

		this.shift4From = "6 PM";
		this.shift4To = "12 PM";
	}

	public Long getNurseShiftSettingsId() {
		return nurseShiftSettingsId;
	}

	public void setNurseShiftSettingsId(Long nurseShiftSettingsId) {
		this.nurseShiftSettingsId = nurseShiftSettingsId;
	}

	public short getShiftType() {
		return shiftType;
	}

	public void setShiftType(short shiftType) {
		this.shiftType = shiftType;
	}

	public String getShift1From() {
		return shift1From;
	}

	public void setShift1From(String shift1From) {
		this.shift1From = shift1From;
	}

	public String getShift1To() {
		return shift1To;
	}

	public void setShift1To(String shift1To) {
		this.shift1To = shift1To;
	}

	public String getShift2From() {
		return shift2From;
	}

	public void setShift2From(String shift2From) {
		this.shift2From = shift2From;
	}

	public String getShift2To() {
		return shift2To;
	}

	public void setShift2To(String shift2To) {
		this.shift2To = shift2To;
	}

	public String getShift3From() {
		return shift3From;
	}

	public void setShift3From(String shift3From) {
		this.shift3From = shift3From;
	}

	public String getShift3To() {
		return shift3To;
	}

	public void setShift3To(String shift3To) {
		this.shift3To = shift3To;
	}

	public String getShift4From() {
		return shift4From;
	}

	public void setShift4From(String shift4From) {
		this.shift4From = shift4From;
	}

	public String getShift4To() {
		return shift4To;
	}

	public void setShift4To(String shift4To) {
		this.shift4To = shift4To;
	}

	public static long getSerialversionid() {
		return serialVersionID;
	}


	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getShift1Duration() {
		return shift1Duration;
	}

	public void setShift1Duration(String shift1Duration) {
		this.shift1Duration = shift1Duration;
	}

	public String getShift2Duration() {
		return shift2Duration;
	}

	public void setShift2Duration(String shift2Duration) {
		this.shift2Duration = shift2Duration;
	}

	public String getShift3Duration() {
		return shift3Duration;
	}

	public void setShift3Duration(String shift3Duration) {
		this.shift3Duration = shift3Duration;
	}

	public String getShift4Duration() {
		return shift4Duration;
	}

	public void setShift4Duration(String shift4Duration) {
		this.shift4Duration = shift4Duration;
	}
}
