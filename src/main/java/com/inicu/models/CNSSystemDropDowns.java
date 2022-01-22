package com.inicu.models;

import com.inicu.postgres.entities.Medicines;
import com.inicu.postgres.entities.RefMedfrequency;
import com.inicu.postgres.utility.BasicConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CNSSystemDropDowns {

	HashMap<Object, List<RefTestslist>> orders;

	List<Medicines> medicine;
	
	List<Medicines> allMedicine;

	List<RefMedfrequency> freqListMedcines;

	List<String> hours;

	List<String> minutes;

	List<KeyValueObj> treatmentActionSeizures;

	List<KeyValueObj> causeOfSeizures;

	List<KeyValueObj> treatmentActionIvh;

	List<KeyValueObj> causeOfIvh;

	List<KeyValueObj> treatmentActionAsphyxia;

	List<KeyValueObj> causeOfAsphyxia;

	List<KeyValueObj> treatmentActionEncephalopathy;
	List<KeyValueObj> causeOfEncephalopathy;

	List<KeyValueObj> treatmentActionNeuromuscularDisorders;

	List<KeyValueObj> causeOfNeuromusculaDisorders;

	List<KeyValueObj> treatmentActionHydrocephalus;

	List<KeyValueObj> causeOfHydrocephalus;

	//not in use anymore
	//List<Object[]> associatedEvents;

	List<KeyValueObj> riskFactorAshphysia;

	public List<KeyValueObj> getTreatmentActionEncephalopathy() {
		return treatmentActionEncephalopathy;
	}

	public void setTreatmentActionEncephalopathy(List<KeyValueObj> treatmentActionEncephalopathy) {
		this.treatmentActionEncephalopathy = treatmentActionEncephalopathy;
	}

	public List<KeyValueObj> getCauseOfEncephalopathy() {
		return causeOfEncephalopathy;
	}

	public void setCauseOfEncephalopathy(List<KeyValueObj> causeOfEncephalopathy) {
		this.causeOfEncephalopathy = causeOfEncephalopathy;
	}

	public List<KeyValueObj> getTreatmentActionNeuromuscularDisorders() {
		return treatmentActionNeuromuscularDisorders;
	}

	public void setTreatmentActionNeuromuscularDisorders(List<KeyValueObj> treatmentActionNeuromuscularDisorders) {
		this.treatmentActionNeuromuscularDisorders = treatmentActionNeuromuscularDisorders;
	}

	public List<KeyValueObj> getCauseOfNeuromusculaDisorders() {
		return causeOfNeuromusculaDisorders;
	}

	public void setCauseOfNeuromusculaDisorders(List<KeyValueObj> causeOfNeuromusculaDisorders) {
		this.causeOfNeuromusculaDisorders = causeOfNeuromusculaDisorders;
	}

	public List<KeyValueObj> getTreatmentActionHydrocephalus() {
		return treatmentActionHydrocephalus;
	}

	public void setTreatmentActionHydrocephalus(List<KeyValueObj> treatmentActionHydrocephalus) {
		this.treatmentActionHydrocephalus = treatmentActionHydrocephalus;
	}

	public List<KeyValueObj> getCauseOfHydrocephalus() {
		return causeOfHydrocephalus;
	}

	public void setCauseOfHydrocephalus(List<KeyValueObj> causeOfHydrocephalus) {
		this.causeOfHydrocephalus = causeOfHydrocephalus;
	}

	public CNSSystemDropDowns() {
		super();
		riskFactorAshphysia = new ArrayList<KeyValueObj>(){
			{
				KeyValueObj keyValue = null;
				
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF001");
				 keyValue.setValue("Teenage Mother");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF002");
				 keyValue.setValue("Prolonged rupture of membranes");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF003");
				 keyValue.setValue("Meconium-stained fluid");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF004");
				 keyValue.setValue("Multiple births");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF005");
				 keyValue.setValue("Lack of antenatal care");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF006");
				 keyValue.setValue("Low birth weight infants");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF007");
				 keyValue.setValue("Malpresentation");
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF008");
				 keyValue.setValue("Augmentation of labour with oxytocin");
				 add(keyValue);

				 keyValue = new KeyValueObj();
				 keyValue.setKey("RF009");
				 keyValue.setValue("Advanced maternal age");
				 add(keyValue);

				 keyValue = new KeyValueObj();
				 keyValue.setKey(BasicConstants.OTHERS);
				 keyValue.setValue(BasicConstants.OTHERS);
				 add(keyValue);
				 keyValue = new KeyValueObj();
				 keyValue.setKey("noneRisk");
				 keyValue.setValue("None");
				 add(keyValue);
			}
		};
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

	public List<KeyValueObj> getTreatmentActionSeizures() {
		return treatmentActionSeizures;
	}

	public void setTreatmentActionSeizures(List<KeyValueObj> treatmentActionSeizures) {
		this.treatmentActionSeizures = treatmentActionSeizures;
	}

	public List<KeyValueObj> getCauseOfSeizures() {
		return causeOfSeizures;
	}

	public void setCauseOfSeizures(List<KeyValueObj> causeOfSeizures) {
		this.causeOfSeizures = causeOfSeizures;
	}

	public List<KeyValueObj> getTreatmentActionIvh() {
		return treatmentActionIvh;
	}

	public void setTreatmentActionIvh(List<KeyValueObj> treatmentActionIvh) {
		this.treatmentActionIvh = treatmentActionIvh;
	}

	public List<KeyValueObj> getCauseOfIvh() {
		return causeOfIvh;
	}

	public void setCauseOfIvh(List<KeyValueObj> causeOfIvh) {
		this.causeOfIvh = causeOfIvh;
	}

	public List<KeyValueObj> getTreatmentActionAsphyxia() {
		return treatmentActionAsphyxia;
	}

	public void setTreatmentActionAsphyxia(List<KeyValueObj> treatmentActionAsphyxia) {
		this.treatmentActionAsphyxia = treatmentActionAsphyxia;
	}

	public List<KeyValueObj> getCauseOfAsphyxia() {
		return causeOfAsphyxia;
	}

	public void setCauseOfAsphyxia(List<KeyValueObj> causeOfAsphyxia) {
		this.causeOfAsphyxia = causeOfAsphyxia;
	}

//	public List<Object[]> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<Object[]> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

	public List<KeyValueObj> getRiskFactorAshphysia() {
		return riskFactorAshphysia;
	}

	public void setRiskFactorAshphysia(List<KeyValueObj> riskFactorAshphysia) {
		this.riskFactorAshphysia = riskFactorAshphysia;
	}

	public List<Medicines> getAllMedicine() {
		return allMedicine;
	}

	public void setAllMedicine(List<Medicines> allMedicine) {
		this.allMedicine = allMedicine;
	}

}
