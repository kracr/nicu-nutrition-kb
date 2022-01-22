package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.VwDoctornotesFinal;

public class NursingDailyProgressPojo {

	private String currentage;
	private String dayAfterAdmission;
	private BabyVisit babyVisit;
	private BabyVisit birthBabyVisit;
	private List<BabyVisit> listBabyVisit;
	private List<VwBabyVisitCSVData> listcsvData;

	private AnthropometryProgressRate anthropometryProgressRate;

	public AnthropometryProgressRate getAnthropometryProgressRate() {
		return anthropometryProgressRate;
	}

	public void setAnthropometryProgressRate(AnthropometryProgressRate anthropometryProgressRate) {
		this.anthropometryProgressRate = anthropometryProgressRate;
	}

	List<VwDoctornotesFinal> doctorNotes;

	public String getCurrentage() {
		return currentage;
	}

	public String getDayAfterAdmission() {
		return dayAfterAdmission;
	}

	public BabyVisit getBabyVisit() {
		return babyVisit;
	}

	public BabyVisit getBirthBabyVisit() {
		return birthBabyVisit;
	}

	public List<BabyVisit> getListBabyVisit() {
		return listBabyVisit;
	}

	public List<VwDoctornotesFinal> getDoctorNotes() {
		return doctorNotes;
	}

	public void setCurrentage(String currentage) {
		this.currentage = currentage;
	}

	public void setDayAfterAdmission(String dayAfterAdmission) {
		this.dayAfterAdmission = dayAfterAdmission;
	}

	public void setBabyVisit(BabyVisit babyVisit) {
		this.babyVisit = babyVisit;
	}

	public void setBirthBabyVisit(BabyVisit birthBabyVisit) {
		this.birthBabyVisit = birthBabyVisit;
	}

	public void setListBabyVisit(List<BabyVisit> listBabyVisit) {
		this.listBabyVisit = listBabyVisit;
	}

	public void setDoctorNotes(List<VwDoctornotesFinal> doctorNotes) {
		this.doctorNotes = doctorNotes;
	}

	public List<VwBabyVisitCSVData> getListcsvData() {
		return listcsvData;
	}

	public void setListcsvData(List<VwBabyVisitCSVData> listcsvData) {
		this.listcsvData = listcsvData;
	}
}
