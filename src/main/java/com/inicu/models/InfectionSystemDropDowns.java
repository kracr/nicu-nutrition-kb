package com.inicu.models;

import com.inicu.postgres.entities.Medicines;
import com.inicu.postgres.entities.RefMedfrequency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfectionSystemDropDowns {
	
	HashMap<Object, List<RefTestslist>> orders;

	List<Medicines> medicine;
	
	List<Medicines> allMedicine; 

	List<RefMedfrequency> freqListMedcines;

	List<String> hours;

	List<String> minutes;

	List<KeyValueObj> treatmentActionSepsis;

	List<KeyValueObj> treatmentActionIntrauterine;

	List<KeyValueObj> treatmentActionNec;

	List<KeyValueObj> causeOfSepsis;
	List<KeyValueObj> icdCauseOfSepsis;
	List<KeyValueObj> causeOfIntrauterine;

	List<KeyValueObj> riskFactorNec;
	List<KeyValueObj> causeOfNec;
	List<KeyValueObj> necSystematicSymptom;
	List<KeyValueObj> necAbdominal;
	List<KeyValueObj> necXrayFinding;

	//not in use anymore
	//List<Object[]> associatedEvents;
	
	List<KeyValueObj> riskFactorSepsis;

	public List<KeyValueObj> getRiskFactorNec() {
		return riskFactorNec;
	}

	public void setRiskFactorNec(List<KeyValueObj> riskFactorNec) {
		this.riskFactorNec = riskFactorNec;
	}

	public List<KeyValueObj> getCauseOfNec() {
		return causeOfNec;
	}

	public void setCauseOfNec(List<KeyValueObj> causeOfNec) {
		this.causeOfNec = causeOfNec;
	}

	public List<KeyValueObj> getNecSystematicSymptom() {
		return necSystematicSymptom;
	}

	public void setNecSystematicSymptom(List<KeyValueObj> necSystematicSymptom) {
		this.necSystematicSymptom = necSystematicSymptom;
	}

	public List<KeyValueObj> getNecAbdominal() {
		return necAbdominal;
	}

	public void setNecAbdominal(List<KeyValueObj> necAbdominal) {
		this.necAbdominal = necAbdominal;
	}

	public List<KeyValueObj> getNecXrayFinding() {
		return necXrayFinding;
	}

	public void setNecXrayFinding(List<KeyValueObj> necXrayFinding) {
		this.necXrayFinding = necXrayFinding;
	}

	public List<KeyValueObj> getCauseOfIntrauterine() {
		return causeOfIntrauterine;
	}

	public void setCauseOfIntrauterine(List<KeyValueObj> causeOfIntrauterine) {
		this.causeOfIntrauterine = causeOfIntrauterine;
	}

	public InfectionSystemDropDowns() {
		super();
		// TODO Auto-generated constructor stub
		riskFactorSepsis = new ArrayList<KeyValueObj>();
		KeyValueObj keyValue = new KeyValueObj();
		keyValue.setKey("RS001");
		keyValue.setValue("PPROM");
		riskFactorSepsis.add(keyValue);
		KeyValueObj keyValue2 = new KeyValueObj();
		keyValue2.setKey("RS002");
		keyValue2.setValue("PROM");
		riskFactorSepsis.add(keyValue2);
		KeyValueObj keyValue3 = new KeyValueObj();
		keyValue3.setKey("RS003");
		keyValue3.setValue("Maternal Fever");
		riskFactorSepsis.add(keyValue3);
		KeyValueObj keyValue4 = new KeyValueObj();
		keyValue4.setKey("RS004");
		keyValue4.setValue("Maternal Infection");
		riskFactorSepsis.add(keyValue4);
		KeyValueObj keyValue5 = new KeyValueObj();
		keyValue5.setKey("RS005");
		keyValue5.setValue("Chorioamnionitis");
		riskFactorSepsis.add(keyValue5);
		KeyValueObj keyValue6 = new KeyValueObj();
		keyValue6.setKey("RS006");
		keyValue6.setValue("Low BW");
		riskFactorSepsis.add(keyValue6);
		KeyValueObj keyValue7 = new KeyValueObj();
		keyValue7.setKey("RS007");
		keyValue7.setValue("Low Gestational age");
		riskFactorSepsis.add(keyValue7);
		KeyValueObj keyValue8 = new KeyValueObj();
		keyValue8.setKey("RS008");
		keyValue8.setValue("Previous Antimicrobial Exposure");
		riskFactorSepsis.add(keyValue8);
		KeyValueObj keyValue9 = new KeyValueObj();
		keyValue9.setKey("RS009");
		keyValue9.setValue("Poor hand hygiene");
		riskFactorSepsis.add(keyValue9);
		KeyValueObj keyValue10 = new KeyValueObj();
		keyValue10.setKey("RS0010");
		keyValue10.setValue("parenteral feeding");
		riskFactorSepsis.add(keyValue10);
		KeyValueObj keyValue11 = new KeyValueObj();
		keyValue11.setKey("RS0011");
		keyValue11.setValue("CVC");
		riskFactorSepsis.add(keyValue11);
		KeyValueObj keyValue12 = new KeyValueObj();
		keyValue12.setKey("RS0012");
		keyValue12.setValue("mechanical ventilate");
		riskFactorSepsis.add(keyValue12);
		KeyValueObj keyValue13 = new KeyValueObj();
		keyValue13.setKey("RS0013");
		keyValue13.setValue("Low-5 minute APGAR scores");
		riskFactorSepsis.add(keyValue13);
		KeyValueObj keyValue14 = new KeyValueObj();
		keyValue14.setKey("other");
		keyValue14.setValue("Other");
		riskFactorSepsis.add(keyValue14);
		KeyValueObj keyValue15 = new KeyValueObj();
		keyValue15.setKey("none");
		keyValue15.setValue("None");
		riskFactorSepsis.add(keyValue15);
	}

	public List<KeyValueObj> getIcdCauseOfSepsis() {
		return icdCauseOfSepsis;
	}

	public void setIcdCauseOfSepsis(List<KeyValueObj> icdCauseOfSepsis) {
		this.icdCauseOfSepsis = icdCauseOfSepsis;
	}

	public List<KeyValueObj> getTreatmentActionNec() {
		return treatmentActionNec;
	}

	public void setTreatmentActionNec(List<KeyValueObj> treatmentActionNec) {
		this.treatmentActionNec = treatmentActionNec;
	}

	public List<KeyValueObj> getTreatmentActionIntrauterine() {
		return treatmentActionIntrauterine;
	}

	public void setTreatmentActionIntrauterine(List<KeyValueObj> treatmentActionIntrauterine) {
		this.treatmentActionIntrauterine = treatmentActionIntrauterine;
	}

	public HashMap<Object, List<RefTestslist>> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Object, List<RefTestslist>> orders) {
		this.orders = orders;
	}

	public List<Medicines> getMedicine() {
		return medicine;
	}

	public void setMedicine(List<Medicines> medicine) {
		this.medicine = medicine;
	}

	public List<RefMedfrequency> getFreqListMedcines() {
		return freqListMedcines;
	}

	public void setFreqListMedcines(List<RefMedfrequency> freqListMedcines) {
		this.freqListMedcines = freqListMedcines;
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

	public List<KeyValueObj> getTreatmentActionSepsis() {
		return treatmentActionSepsis;
	}

	public void setTreatmentActionSepsis(List<KeyValueObj> treatmentActionSepsis) {
		this.treatmentActionSepsis = treatmentActionSepsis;
	}

	public List<KeyValueObj> getCauseOfSepsis() {
		return causeOfSepsis;
	}

	public void setCauseOfSepsis(List<KeyValueObj> causeOfSepsis) {
		this.causeOfSepsis = causeOfSepsis;
	}

//	public List<Object[]> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<Object[]> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

	public List<KeyValueObj> getRiskFactorSepsis() {
		return riskFactorSepsis;
	}

	public void setRiskFactorSepsis(List<KeyValueObj> riskFactorSepsis) {
		this.riskFactorSepsis = riskFactorSepsis;
	}

	public List<Medicines> getAllMedicine() {
		return allMedicine;
	}

	public void setAllMedicine(List<Medicines> allMedicine) {
		this.allMedicine = allMedicine;
	}
	
}
