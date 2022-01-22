package com.inicu.models;

public class NurseShiftSettingJSON {
	
	private Long nurseShiftSettingsId;

	private int shiftType;

	private String shift1Timings;

	private String shift2Timings;

	private String shift3Timings;

	private String shift4Timings;

	public Long getNurseShiftSettingsId() {
		return nurseShiftSettingsId;
	}

	public void setNurseShiftSettingsId(Long nurseShiftSettingsId) {
		this.nurseShiftSettingsId = nurseShiftSettingsId;
	}

	public int getShiftType() {
		return shiftType;
	}

	public void setShiftType(int shiftType) {
		this.shiftType = shiftType;
	}

	public String getShift1Timings() {
		return shift1Timings;
	}

	public void setShift1Timings(String shift1Timings) {
		this.shift1Timings = shift1Timings;
	}

	public String getShift2Timings() {
		return shift2Timings;
	}

	public void setShift2Timings(String shift2Timings) {
		this.shift2Timings = shift2Timings;
	}

	public String getShift3Timings() {
		return shift3Timings;
	}

	public void setShift3Timings(String shift3Timings) {
		this.shift3Timings = shift3Timings;
	}

	public String getShift4Timings() {
		return shift4Timings;
	}

	public void setShift4Timings(String shift4Timings) {
		this.shift4Timings = shift4Timings;
	}
	

}
