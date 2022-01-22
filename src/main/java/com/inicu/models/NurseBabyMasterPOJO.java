package com.inicu.models;

import com.inicu.postgres.entities.NurseShiftSettings;

import java.util.List;

public class NurseBabyMasterPOJO {
	
	private List<NurseBaby> shift1;
	
	private List<NurseBaby> shift2;
	
	private List<NurseBaby> shift3;
	
	private List<NurseBaby> shift4;

	private NurseShiftSettings nurseShiftSettings;
	
	public List<NurseBaby> getShift1() {
		return shift1;
	}

	public void setShift1(List<NurseBaby> shift1) {
		this.shift1 = shift1;
	}

	public List<NurseBaby> getShift2() {
		return shift2;
	}

	public void setShift2(List<NurseBaby> shift2) {
		this.shift2 = shift2;
	}

	public List<NurseBaby> getShift3() {
		return shift3;
	}

	public void setShift3(List<NurseBaby> shift3) {
		this.shift3 = shift3;
	}

	public List<NurseBaby> getShift4() {
		return shift4;
	}

	public void setShift4(List<NurseBaby> shift4) {
		this.shift4 = shift4;
	}

	public NurseShiftSettings getNurseShiftSettings() {
		return nurseShiftSettings;
	}

	public void setNurseShiftSettings(NurseShiftSettings nurseShiftSettings) {
		this.nurseShiftSettings = nurseShiftSettings;
	}
}
