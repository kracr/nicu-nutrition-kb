package com.inicu.models;
import java.util.ArrayList;
import java.util.List;

public class ActiveFilterGraphDataJson {
	List<String> assesmentsList;
	List<String> medicationListAntibiotics;
	List<String> medicationListOthers;
	List<String> proceduresList;
	List<String> monitorList;	
	List<String> oximeterList;			
	List<String> ventilatorList;			
	List<String> bloodGasList;

	boolean phototheraphy;
	boolean oxygenationIndex;
	boolean surfactantUsage;
	boolean enteralIntake;
	boolean parentralIntake;
	boolean urineOutput;
	boolean abdOutput;
	boolean events;

	public ActiveFilterGraphDataJson() {
		this.assesmentsList = new ArrayList<>();
		this.medicationListAntibiotics = new ArrayList<>();
		this.medicationListOthers = new ArrayList<>();
		this.proceduresList = new ArrayList<>();
		this.monitorList = new ArrayList<>();			
		this.ventilatorList = new ArrayList<>();	
		this.oximeterList = new ArrayList<>();			
		this.bloodGasList = new ArrayList<>();

		this.phototheraphy=false;
		this.oxygenationIndex=false;
		this.surfactantUsage=false;
		this.enteralIntake=false;
		this.parentralIntake=false;
		this.urineOutput=false;
		this.abdOutput=false;
		this.events=false;
	}


	public List<String> getAssesmentsList() {
		return assesmentsList;
	}

	public void setAssesmentsList(List<String> assesmentsList) {
		this.assesmentsList = assesmentsList;
	}

	public List<String> getMedicationListAntibiotics() {
		return medicationListAntibiotics;
	}

	public void setMedicationListAntibiotics(List<String> medicationListAntibiotics) {
		this.medicationListAntibiotics = medicationListAntibiotics;
	}

	public List<String> getMedicationListOthers() {
		return medicationListOthers;
	}

	public void setMedicationListOthers(List<String> medicationListOthers) {
		this.medicationListOthers = medicationListOthers;
	}

	public List<String> getProceduresList() {
		return proceduresList;
	}

	public void setProceduresList(List<String> proceduresList) {
		this.proceduresList = proceduresList;
	}

	public List<String> getMonitorList() {
		return monitorList;
	}

	public void setMonitorList(List<String> monitorList) {
		this.monitorList = monitorList;
	}

	public List<String> getVentilatorList() {
		return ventilatorList;
	}

	public void setVentilatorList(List<String> ventilatorList) {
		this.ventilatorList = ventilatorList;
	}

	public List<String> getBloodGasList() {
		return bloodGasList;
	}

	public void setBloodGasList(List<String> bloodGasList) {
		this.bloodGasList = bloodGasList;
	}

	public List<String> getOximeterList() {
		return oximeterList;
	}

	public void setOximeterList(List<String> oximeterList) {
		this.oximeterList = oximeterList;
	}

	public boolean isPhototheraphy() {
		return phototheraphy;
	}

	public void setPhototheraphy(boolean phototheraphy) {
		this.phototheraphy = phototheraphy;
	}

	public boolean isOxygenationIndex() {
		return oxygenationIndex;
	}

	public void setOxygenationIndex(boolean oxygenationIndex) {
		this.oxygenationIndex = oxygenationIndex;
	}

	public boolean isSurfactantUsage() {
		return surfactantUsage;
	}

	public void setSurfactantUsage(boolean surfactantUsage) {
		this.surfactantUsage = surfactantUsage;
	}

	public boolean isEnteralIntake() {
		return enteralIntake;
	}

	public void setEnteralIntake(boolean enteralIntake) {
		this.enteralIntake = enteralIntake;
	}

	public boolean isParentralIntake() {
		return parentralIntake;
	}

	public void setParentralIntake(boolean parentralIntake) {
		this.parentralIntake = parentralIntake;
	}

	public boolean isUrineOutput() {
		return urineOutput;
	}

	public void setUrineOutput(boolean urineOutput) {
		this.urineOutput = urineOutput;
	}

	public boolean isAbdOutput() {
		return abdOutput;
	}

	public void setAbdOutput(boolean abdOutput) {
		this.abdOutput = abdOutput;
	}

	public boolean isEvents() {
		return events;
	}

	public void setEvents(boolean events) {
		this.events = events;
	}
}
