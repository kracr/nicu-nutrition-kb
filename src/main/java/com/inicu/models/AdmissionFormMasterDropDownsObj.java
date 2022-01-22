package com.inicu.models;

import java.util.HashMap;
import java.util.List;

public class AdmissionFormMasterDropDownsObj {

	private List<KeyValueObj> admissionStatus;
	private List<KeyValueObj> criticalities;
	private List<NicuRoomObj> nicuRooms;
	private List<NicuRoomObj> nicuInactiveBeds;
	private List<KeyValueObj> nicuLevels;
	private List<KeyValueObj> typeOfAdmissions;
	private List<KeyValueObj> bloodGroups;
	private List<KeyValueObj> modeOfDeliverys;
	private List<KeyValueObj> residentDoctors;
	private List<KeyValueObj> significantMaterials;
	private List<KeyValueObj> histories;
	private List<KeyValueObj> headToToes;
	private List<KeyValueObj> examinations;
	private List<KeyValueObj> gestation;
	private List<String> hours;
	private List<String> minutes;
	private List<String> seconds;
	private List<ReasonOfAdmissionEventPOJO> reasonOfAdmission;
	private HashMap<String, String> stateList;

	public List<KeyValueObj> getAdmissionStatus() {
		return admissionStatus;
	}

	public void setAdmissionStatus(List<KeyValueObj> admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public List<KeyValueObj> getCriticalities() {
		return criticalities;
	}

	public void setCriticalities(List<KeyValueObj> criticalities) {
		this.criticalities = criticalities;
	}

	public List<NicuRoomObj> getNicuRooms() {
		return nicuRooms;
	}

	public void setNicuRooms(List<NicuRoomObj> nicuRooms) {
		this.nicuRooms = nicuRooms;
	}

	public List<KeyValueObj> getHistories() {
		return histories;
	}

	public void setHistories(List<KeyValueObj> histories) {
		this.histories = histories;
	}

	public List<KeyValueObj> getNicuLevels() {
		return nicuLevels;
	}

	public void setNicuLevels(List<KeyValueObj> nicuLevels) {
		this.nicuLevels = nicuLevels;
	}

	public List<KeyValueObj> getTypeOfAdmissions() {
		return typeOfAdmissions;
	}

	public void setTypeOfAdmissions(List<KeyValueObj> typeOfAdmissions) {
		this.typeOfAdmissions = typeOfAdmissions;
	}

	public List<KeyValueObj> getBloodGroups() {
		return bloodGroups;
	}

	public void setBloodGroups(List<KeyValueObj> bloodGroups) {
		this.bloodGroups = bloodGroups;
	}

	public List<KeyValueObj> getModeOfDeliverys() {
		return modeOfDeliverys;
	}

	public void setModeOfDeliverys(List<KeyValueObj> modeOfDeliverys) {
		this.modeOfDeliverys = modeOfDeliverys;
	}

	public List<KeyValueObj> getResidentDoctors() {
		return residentDoctors;
	}

	public void setResidentDoctors(List<KeyValueObj> residentDoctors) {
		this.residentDoctors = residentDoctors;
	}

	public List<KeyValueObj> getSignificantMaterials() {
		return significantMaterials;
	}

	public void setSignificantMaterials(List<KeyValueObj> significantMaterials) {
		this.significantMaterials = significantMaterials;
	}

	public List<KeyValueObj> getHistorys() {
		return histories;
	}

	public void setHistorys(List<KeyValueObj> historys) {
		this.histories = historys;
	}

	public List<KeyValueObj> getHeadToToes() {
		return headToToes;
	}

	public void setHeadToToes(List<KeyValueObj> headToToes) {
		this.headToToes = headToToes;
	}

	public List<KeyValueObj> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<KeyValueObj> examinations) {
		this.examinations = examinations;
	}

	public List<KeyValueObj> getGestation() {
		return gestation;
	}

	public void setGestation(List<KeyValueObj> gestation) {
		this.gestation = gestation;
	}

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<String> getMinutes() {
		return minutes;
	}

	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}

	public List<String> getSeconds() {
		return seconds;
	}

	public void setSeconds(List<String> seconds) {
		this.seconds = seconds;
	}

	public List<ReasonOfAdmissionEventPOJO> getReasonOfAdmission() {
		return reasonOfAdmission;
	}

	public void setReasonOfAdmission(List<ReasonOfAdmissionEventPOJO> reasonOfAdmission) {
		this.reasonOfAdmission = reasonOfAdmission;
	}

	public HashMap<String, String> getStateList() {
		return stateList;
	}

	public void setStateList(HashMap<String, String> stateList) {
		this.stateList = stateList;
	}

	public List<NicuRoomObj> getNicuInactiveBeds() {
		return nicuInactiveBeds;
	}

	public void setNicuInactiveBeds(List<NicuRoomObj> nicuInactiveBeds) {
		this.nicuInactiveBeds = nicuInactiveBeds;
	}

}
