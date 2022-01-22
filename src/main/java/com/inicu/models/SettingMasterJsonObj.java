package com.inicu.models;

import com.inicu.postgres.entities.BabyViewTimings;
import com.inicu.postgres.entities.ClinicalAlertSettings;
import com.inicu.postgres.entities.ClinicalAlertItemDropdown;
import com.inicu.postgres.entities.NurseShiftSettings;

import java.util.HashMap;
import java.util.List;

public class SettingMasterJsonObj {
	
	HashMap<String, Boolean> devicesetting = new HashMap<>();
	HashMap<String, Boolean> dischargesetting = new HashMap<>();
	private String tempuhidtoggleForHis;
	List<String> dummyUHIDList;
	List<String> originalUHIDList;
	List<ClinicalAlertSettings> clinicalAlertSettingsList;
	List<ClinicalAlertItemDropdown> clinicalAlertItemDropdownList;
	List<BabyViewTimings> babyViewList;
	NurseShiftSettings nurseShiftSettings;
	private Boolean remoteViewAvailable;

	public SettingMasterJsonObj(){
		this.remoteViewAvailable = false;
	}

	public List<BabyViewTimings> getBabyViewList() {
		return babyViewList;
	}

	public void setBabyViewList(List<BabyViewTimings> babyViewList) {
		this.babyViewList = babyViewList;
	}

	public List<ClinicalAlertSettings> getClinicalAlertSettingsList() {
		return clinicalAlertSettingsList;
	}

	public void setClinicalAlertSettingsList(List<ClinicalAlertSettings> clinicalAlertSettingsList) {
		this.clinicalAlertSettingsList = clinicalAlertSettingsList;
	}

	public List<ClinicalAlertItemDropdown> getClinicalAlertItemDropdownList() {
		return clinicalAlertItemDropdownList;
	}

	public void setClinicalAlertItemDropdownList(List<ClinicalAlertItemDropdown> clinicalAlertItemDropdownList) {
		this.clinicalAlertItemDropdownList = clinicalAlertItemDropdownList;
	}

	public HashMap<String, Boolean> getDevicesetting() {
		return devicesetting;
	}
	public void setDevicesetting(HashMap<String, Boolean> devicesetting) {
		this.devicesetting = devicesetting;
	}
	public HashMap<String, Boolean> getDischargesetting() {
		return dischargesetting;
	}
	public void setDischargesetting(HashMap<String, Boolean> dischargesetting) {
		this.dischargesetting = dischargesetting;
	}

	public String getTempuhidtoggleForHis() {
		return tempuhidtoggleForHis;
	}

	public void setTempuhidtoggleForHis(String tempuhidtoggleForHis) {
		this.tempuhidtoggleForHis = tempuhidtoggleForHis;
	}

	public List<String> getDummyUHIDList() {
		return dummyUHIDList;
	}

	public void setDummyUHIDList(List<String> dummyUHIDList) {
		this.dummyUHIDList = dummyUHIDList;
	}

	public List<String> getOriginalUHIDList() {
		return originalUHIDList;
	}

	public void setOriginalUHIDList(List<String> originalUHIDList) {
		this.originalUHIDList = originalUHIDList;
	}

	public NurseShiftSettings getNurseShiftSettings() {
		return nurseShiftSettings;
	}

	public void setNurseShiftSettings(NurseShiftSettings nurseShiftSettings) {
		this.nurseShiftSettings = nurseShiftSettings;
	}

	public Boolean getRemoteViewAvailable() {
		return remoteViewAvailable;
	}

	public void setRemoteViewAvailable(Boolean remoteViewAvailable) {
		this.remoteViewAvailable = remoteViewAvailable;
	}
}
