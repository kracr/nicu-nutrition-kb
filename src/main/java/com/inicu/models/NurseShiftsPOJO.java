package com.inicu.models;

import java.util.List;
import java.util.Map;

import com.inicu.postgres.entities.UserShiftMapping;
import com.inicu.postgres.entities.NurseShiftSettings;

public class NurseShiftsPOJO {

	List<UserShiftMapping> nurseShiftList;
	List<UserShiftMapping> doctorShiftList;
	
	NurseShiftSettings nurseShiftSettings;
	
	Map<String,List<String>> constantMap;

	public List<UserShiftMapping> getNurseShiftList() {
		return nurseShiftList;
	}

	public void setNurseShiftList(List<UserShiftMapping> nurseShiftList) {
		this.nurseShiftList = nurseShiftList;
	}

	public NurseShiftSettings getNurseShiftSettings() {
		return nurseShiftSettings;
	}

	public void setNurseShiftSettings(NurseShiftSettings nurseShiftSettings) {
		this.nurseShiftSettings = nurseShiftSettings;
	}

	public Map<String, List<String>> getConstantMap() {
		return constantMap;
	}

	public void setConstantMap(Map<String, List<String>> constantMap) {
		this.constantMap = constantMap;
	}

	public List<UserShiftMapping> getDoctorShiftList() {
		return doctorShiftList;
	}

	public void setDoctorShiftList(List<UserShiftMapping> doctorShiftList) {
		this.doctorShiftList = doctorShiftList;
	}
}
