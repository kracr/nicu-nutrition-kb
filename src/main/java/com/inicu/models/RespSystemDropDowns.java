package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.Medicines;
import com.inicu.postgres.entities.RefMedfrequency;
import com.inicu.postgres.entities.RefVentilationmode;

public class RespSystemDropDowns {

	HashMap<Object, List<RefTestslist>> testsList;

	List<KeyValueObj> treatmentAction;

	List<Medicines> medicine; //medicines not attached to baby
	
	List<Medicines> allMedicine; 

	List<KeyValueObj> causeOfRds;
	List<KeyValueObj> IcdcauseOfRds;

	List<KeyValueObj> causeOfPphn;
	List<KeyValueObj> IcdcauseOfPphn;


	List<KeyValueObj> causeOfApnea;
	List<KeyValueObj> IcdcauseOfApnea;

	List<RefVentilationmode> ventMode;

	List<RefMedfrequency> medicineFrequency;

	List<KeyValueObj> respiratoryPlan;

	List<KeyValueObj> apneaPlan;

	List<String> hours;

	List<KeyValueObj> orderInvestigation;

	List<KeyValueObj> causeOfPneumo;
	List<KeyValueObj> ICdcauseOfPneumo;

	//Not in use anymore. 
	//List<Object[]> associatedEvents;
	
	List<KeyValueObj> riskFactorRds;
	
	List<KeyValueObj> riskFactorPphn;
	
	List<KeyValueObj> riskFactorPneumo;

	public List<KeyValueObj> getIcdcauseOfRds() {
		return IcdcauseOfRds;
	}

	public void setIcdcauseOfRds(List<KeyValueObj> icdcauseOfRds) {
		IcdcauseOfRds = icdcauseOfRds;
	}

	public List<KeyValueObj> getIcdcauseOfPphn() {
		return IcdcauseOfPphn;
	}

	public void setIcdcauseOfPphn(List<KeyValueObj> icdcauseOfPphn) {
		IcdcauseOfPphn = icdcauseOfPphn;
	}

	public List<KeyValueObj> getIcdcauseOfApnea() {
		return IcdcauseOfApnea;
	}

	public void setIcdcauseOfApnea(List<KeyValueObj> icdcauseOfApnea) {
		IcdcauseOfApnea = icdcauseOfApnea;
	}

	public List<KeyValueObj> getICdcauseOfPneumo() {
		return ICdcauseOfPneumo;
	}

	public void setICdcauseOfPneumo(List<KeyValueObj> ICdcauseOfPneumo) {
		this.ICdcauseOfPneumo = ICdcauseOfPneumo;
	}

	public RespSystemDropDowns() {
		super();
		this.respiratoryPlan = new ArrayList<KeyValueObj>() {

			private static final long serialVersionUID = 1L;

			{
				KeyValueObj palnObj = new KeyValueObj();
				palnObj.setKey("RDSPLAN0001");
				palnObj.setValue("Reassess");
				add(palnObj);

				/*
				 * palnObj = new KeyValueObj(); palnObj.setKey("RDSPLAN0002");
				 * palnObj.setValue("Continue same respiratory support");
				 */

				/*
				 * palnObj = new KeyValueObj(); palnObj.setKey("RDSPLAN0003");
				 * palnObj.setValue("Upgrade"); add(palnObj); palnObj = new KeyValueObj();
				 * palnObj.setKey("RDSPLAN0004"); palnObj.setValue("Downgrade"); add(palnObj);
				 */

				palnObj = new KeyValueObj();
				palnObj.setKey("RDSPLAN0005");
				palnObj.setValue("Others");
				add(palnObj);

			}
		};
		this.apneaPlan = new ArrayList<KeyValueObj>() {

			private static final long serialVersionUID = 1L;
			{
				KeyValueObj palnObj = new KeyValueObj();
				palnObj.setKey("APNPLAN0001");
				palnObj.setValue("Reassess");
				add(palnObj);
			}
		};

		causeOfPphn = new ArrayList<KeyValueObj>();
		KeyValueObj keyValue = new KeyValueObj();
		keyValue.setKey("RS001");
		keyValue.setValue("Meconium Aspiration Syndrome");
		causeOfPphn.add(keyValue);
		KeyValueObj keyValue2 = new KeyValueObj();
		keyValue2.setKey("RS002");
		keyValue2.setValue("Pneumonia");
		causeOfPphn.add(keyValue2);
		KeyValueObj keyValue3 = new KeyValueObj();
		keyValue3.setKey("RS003");
		keyValue3.setValue("Asphyxia");
		causeOfPphn.add(keyValue3);
		KeyValueObj otherKeyPphn = new KeyValueObj();
		otherKeyPphn.setKey("other");
		otherKeyPphn.setValue("Other");
		causeOfPphn.add(otherKeyPphn);

		causeOfPneumo = new ArrayList<KeyValueObj>();
		KeyValueObj keyValuePneumo = new KeyValueObj();
		keyValuePneumo.setKey("RS001");
		keyValuePneumo.setValue("Spontaneous");
		causeOfPneumo.add(keyValuePneumo);
		KeyValueObj keyValuePneumo2 = new KeyValueObj();
		keyValuePneumo2.setKey("RS002");
		keyValuePneumo2.setValue("PPV");
		causeOfPneumo.add(keyValuePneumo2);
		KeyValueObj keyValuePneumo3 = new KeyValueObj();
		keyValuePneumo3.setKey("RS003");
		keyValuePneumo3.setValue("Underlying lung disease");
		causeOfPneumo.add(keyValuePneumo3);
		KeyValueObj keyValuePneumo4 = new KeyValueObj();
		keyValuePneumo4.setKey("other");
		keyValuePneumo4.setValue("Other");
		causeOfPneumo.add(keyValuePneumo4);
		
		riskFactorRds = new ArrayList<KeyValueObj>();
		KeyValueObj keyValueRiskRds = new KeyValueObj();
		keyValueRiskRds.setKey("RSK00001");
		keyValueRiskRds.setValue("Prematurity");
		riskFactorRds.add(keyValueRiskRds);
		KeyValueObj keyValueRiskRds2 = new KeyValueObj();
		keyValueRiskRds2.setKey("RSK00002");
		keyValueRiskRds2.setValue("LSCS");
		riskFactorRds.add(keyValueRiskRds2);
		KeyValueObj keyValueRiskRds3 = new KeyValueObj();
		keyValueRiskRds3.setKey("RSK00003");
		keyValueRiskRds3.setValue("Multiple pregnancy");
		riskFactorRds.add(keyValueRiskRds3);
		KeyValueObj keyValueRiskRds4 = new KeyValueObj();
		keyValueRiskRds4.setKey("RSK00004");
		keyValueRiskRds4.setValue("Meconium stain amniotic fluid");
		riskFactorRds.add(keyValueRiskRds4);
		KeyValueObj keyValueRiskRds5 = new KeyValueObj();
		keyValueRiskRds5.setKey("RSK00005");
		keyValueRiskRds5.setValue("Asphyxia");
		riskFactorRds.add(keyValueRiskRds5);
		KeyValueObj keyValueRiskRds6 = new KeyValueObj();
		keyValueRiskRds6.setKey("RSK00006");
		keyValueRiskRds6.setValue("Metabolic");
		riskFactorRds.add(keyValueRiskRds6);
		KeyValueObj keyValueRiskRds7 = new KeyValueObj();
		keyValueRiskRds7.setKey("otherRisk");
		keyValueRiskRds7.setValue("Other");
		riskFactorRds.add(keyValueRiskRds7);
		
	}

	public List<KeyValueObj> getApneaPlan() {
		return apneaPlan;
	}

	public void setApneaPlan(List<KeyValueObj> apneaPlan) {
		this.apneaPlan = apneaPlan;
	}

	public HashMap<Object, List<RefTestslist>> getTestsList() {
		return testsList;
	}

	public List<KeyValueObj> getTreatmentAction() {
		return treatmentAction;
	}

	public List<Medicines> getMedicine() {
		return medicine;
	}

	public List<KeyValueObj> getCauseOfRds() {
		return causeOfRds;
	}

	public List<KeyValueObj> getCauseOfPphn() {
		return causeOfPphn;
	}

	public void setCauseOfPphn(List<KeyValueObj> causeOfPphn) {
		this.causeOfPphn = causeOfPphn;
	}

	public List<KeyValueObj> getCauseOfApnea() {
		return causeOfApnea;
	}

	public void setCauseOfApnea(List<KeyValueObj> causeOfApnea) {
		this.causeOfApnea = causeOfApnea;
	}

	public List<RefVentilationmode> getVentMode() {
		return ventMode;
	}

	public void setTestsList(HashMap<Object, List<RefTestslist>> testsList) {
		this.testsList = testsList;
	}

	public void setTreatmentAction(List<KeyValueObj> treatmentAction) {
		this.treatmentAction = treatmentAction;
	}

	public void setMedicine(List<Medicines> medicine) {
		this.medicine = medicine;
	}

	public void setCauseOfRds(List<KeyValueObj> causeOfRds) {
		this.causeOfRds = causeOfRds;
	}

	public void setVentMode(List<RefVentilationmode> ventMode) {
		this.ventMode = ventMode;
	}

	public List<RefMedfrequency> getMedicineFrequency() {
		return medicineFrequency;
	}

	public void setMedicineFrequency(List<RefMedfrequency> medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}

	public List<KeyValueObj> getRespiratoryPlan() {
		return respiratoryPlan;
	}

	public void setRespiratoryPlan(List<KeyValueObj> respiratoryPlan) {
		this.respiratoryPlan = respiratoryPlan;
	}

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<KeyValueObj> getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(List<KeyValueObj> orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public List<KeyValueObj> getCauseOfPneumo() {
		return causeOfPneumo;
	}

	public void setCauseOfPneumo(List<KeyValueObj> causeOfPneumo) {
		this.causeOfPneumo = causeOfPneumo;
	}

//	public List<Object[]> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<Object[]> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

	public List<KeyValueObj> getRiskFactorRds() {
		return riskFactorRds;
	}

	public void setRiskFactorRds(List<KeyValueObj> riskFactorRds) {
		this.riskFactorRds = riskFactorRds;
	}
	
	public List<KeyValueObj> getRiskFactorPphn() {
		return riskFactorPphn;
	}

	public void setRiskFactorPphn(List<KeyValueObj> riskFactorPphn) {
		this.riskFactorPphn = riskFactorPphn;
	}
	
	public List<KeyValueObj> getRiskFactorPneumo() {
		return riskFactorPneumo;
	}

	public void setRiskFactorPneumo(List<KeyValueObj> riskFactorPneumo) {
		this.riskFactorPneumo = riskFactorPneumo;
	}

	@Override
	public String toString() {
		return "RespSystemDropDowns [testsList=" + testsList + ", treatmentAction=" + treatmentAction + ", medicine="
				+ medicine + ", causeOfRds=" + causeOfRds + ", causeOfPphn=" + causeOfPphn + ", causeOfApnea="
				+ causeOfApnea + ", ventMode=" + ventMode + ", medicineFrequency=" + medicineFrequency
				+ ", respiratoryPlan=" + respiratoryPlan + ", apneaPlan=" + apneaPlan + ", hours=" + hours
				+ ", orderInvestigation=" + orderInvestigation + ", causeOfPneumo=" + causeOfPneumo 
				+ ", riskFactorRds=" + riskFactorRds 
				+ ", riskFactorPphn=" + riskFactorPphn + ", riskFactorPneumo=" + riskFactorPneumo + "]";
	}

	public List<Medicines> getAllMedicine() {
		return allMedicine;
	}

	public void setAllMedicine(List<Medicines> allMedicine) {
		this.allMedicine = allMedicine;
	}
	
	

}
