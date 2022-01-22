package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.Medicines;
import com.inicu.postgres.entities.RefMedfrequency;

public class MetabolicSystemDropDowns {

	HashMap<Object, List<RefTestslist>> orders;

	List<KeyValueObj> treatmentAction;

	List<KeyValueObj> treatmentActionHyperglycemia;

	List<KeyValueObj> causeOfHypoglycemia;

	List<KeyValueObj> causeOfHyperglycemia;

	List<KeyValueObj> causeOfHyponatremia;

	List<KeyValueObj> treatmentActionHypernatremia;

	List<KeyValueObj> treatmentActionHyponatremia;

	List<KeyValueObj> causeOfHypernatremia;

	List<KeyValueObj> treatmentActionHypokalemia;

	List<KeyValueObj> causeOfHypokalemia;

	List<KeyValueObj> treatmentActionHyperkalemia;

	List<KeyValueObj> treatmentActionAcidosis;

	List<KeyValueObj> causeOfHyperkalemia;

	List<KeyValueObj> causeOfAcidosis;

	List<Medicines> medicine;

	List<RefMedfrequency> freqListMedcines;

	List<String> hours;

	List<String> minutes;

	List<KeyValueObj> treatmentActionIem;

	List<KeyValueObj> causeOfIem;

	List<KeyValueObj> treatmentActionHypercalcemia;

	List<KeyValueObj> causeOfHypercalcemia;

	List<KeyValueObj> treatmentActionHypocalcemia;

	List<KeyValueObj> causeOfHypocalcemia;

	public HashMap<Object, List<RefTestslist>> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Object, List<RefTestslist>> orders) {
		this.orders = orders;
	}

	public List<KeyValueObj> getTreatmentAction() {
		return treatmentAction;
	}

	public void setTreatmentAction(List<KeyValueObj> treatmentAction) {
		this.treatmentAction = treatmentAction;
	}

	public List<KeyValueObj> getTreatmentActionHyperglycemia() {
		return treatmentActionHyperglycemia;
	}

	public void setTreatmentActionHyperglycemia(List<KeyValueObj> treatmentActionHyperglycemia) {
		this.treatmentActionHyperglycemia = treatmentActionHyperglycemia;
	}

	public List<KeyValueObj> getCauseOfHypoglycemia() {
		return causeOfHypoglycemia;
	}

	public void setCauseOfHypoglycemia(List<KeyValueObj> causeOfHypoglycemia) {
		this.causeOfHypoglycemia = causeOfHypoglycemia;
	}

	public List<KeyValueObj> getCauseOfHyperglycemia() {
		return causeOfHyperglycemia;
	}

	public void setCauseOfHyperglycemia(List<KeyValueObj> causeOfHyperglycemia) {
		this.causeOfHyperglycemia = causeOfHyperglycemia;
	}

	public List<KeyValueObj> getTreatmentActionHypernatremia() {
		return treatmentActionHypernatremia;
	}

	public void setTreatmentActionHypernatremia(List<KeyValueObj> treatmentActionHypernatremia) {
		this.treatmentActionHypernatremia = treatmentActionHypernatremia;
	}

	public List<KeyValueObj> getCauseOfHypernatremia() {
		return causeOfHypernatremia;
	}

	public void setCauseOfHypernatremia(List<KeyValueObj> causeOfHypernatremia) {
		this.causeOfHypernatremia = causeOfHypernatremia;
	}

	public List<KeyValueObj> getTreatmentActionHypokalemia() {
		return treatmentActionHypokalemia;
	}

	public void setTreatmentActionHypokalemia(List<KeyValueObj> treatmentActionHypokalemia) {
		this.treatmentActionHypokalemia = treatmentActionHypokalemia;
	}

	public List<KeyValueObj> getCauseOfHypokalemia() {
		return causeOfHypokalemia;
	}

	public void setCauseOfHypokalemia(List<KeyValueObj> causeOfHypokalemia) {
		this.causeOfHypokalemia = causeOfHypokalemia;
	}

	public List<KeyValueObj> getTreatmentActionHyperkalemia() {
		return treatmentActionHyperkalemia;
	}

	public void setTreatmentActionHyperkalemia(List<KeyValueObj> treatmentActionHyperkalemia) {
		this.treatmentActionHyperkalemia = treatmentActionHyperkalemia;
	}

	public List<KeyValueObj> getCauseOfHyperkalemia() {
		return causeOfHyperkalemia;
	}

	public void setCauseOfHyperkalemia(List<KeyValueObj> causeOfHyperkalemia) {
		this.causeOfHyperkalemia = causeOfHyperkalemia;
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

	public List<String> getMinutes() {
		return minutes;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}

	public List<KeyValueObj> getCauseOfHyponatremia() {
		return causeOfHyponatremia;
	}

	public void setCauseOfHyponatremia(List<KeyValueObj> causeOfHyponatremia) {
		this.causeOfHyponatremia = causeOfHyponatremia;
	}

	public List<KeyValueObj> getTreatmentActionHyponatremia() {
		return treatmentActionHyponatremia;
	}

	public void setTreatmentActionHyponatremia(List<KeyValueObj> treatmentActionHyponatremia) {
		this.treatmentActionHyponatremia = treatmentActionHyponatremia;
	}

	public List<KeyValueObj> getTreatmentActionIem() {
		return treatmentActionIem;
	}

	public void setTreatmentActionIem(List<KeyValueObj> treatmentActionIem) {
		this.treatmentActionIem = treatmentActionIem;
	}

	public List<KeyValueObj> getCauseOfIem() {
		return causeOfIem;
	}

	public void setCauseOfIem(List<KeyValueObj> causeOfIem) {
		this.causeOfIem = causeOfIem;
	}

	public List<KeyValueObj> getTreatmentActionHypercalcemia() {
		return treatmentActionHypercalcemia;
	}

	public void setTreatmentActionHypercalcemia(List<KeyValueObj> treatmentActionHypercalcemia) {
		this.treatmentActionHypercalcemia = treatmentActionHypercalcemia;
	}

	public List<KeyValueObj> getCauseOfHypercalcemia() {
		return causeOfHypercalcemia;
	}

	public void setCauseOfHypercalcemia(List<KeyValueObj> causeOfHypercalcemia) {
		this.causeOfHypercalcemia = causeOfHypercalcemia;
	}

	public List<KeyValueObj> getTreatmentActionAcidosis() {
		return treatmentActionAcidosis;
	}

	public void setTreatmentActionAcidosis(List<KeyValueObj> treatmentActionAcidosis) {
		this.treatmentActionAcidosis = treatmentActionAcidosis;
	}

	public List<KeyValueObj> getCauseOfAcidosis() {
		return causeOfAcidosis;
	}

	public void setCauseOfAcidosis(List<KeyValueObj> causeOfAcidosis) {
		this.causeOfAcidosis = causeOfAcidosis;
	}

	public List<KeyValueObj> getTreatmentActionHypocalcemia() {
		return treatmentActionHypocalcemia;
	}

	public void setTreatmentActionHypocalcemia(List<KeyValueObj> treatmentActionHypocalcemia) {
		this.treatmentActionHypocalcemia = treatmentActionHypocalcemia;
	}

	public List<KeyValueObj> getCauseOfHypocalcemia() {
		return causeOfHypocalcemia;
	}

	public void setCauseOfHypocalcemia(List<KeyValueObj> causeOfHypocalcemia) {
		this.causeOfHypocalcemia = causeOfHypocalcemia;
	}
}
