package com.inicu.models;

import org.hibernate.annotations.NotFound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInfoMasterObj {
	
	private String typeOfPatient ="new";

	PatientInfoAdmissonFormObj admissionForm ;
	
	
	PatientInfoAddChildObj addChild; //as per chages this form will work as now child details form object.
	

	PatientInfoChildDetailsObj childDetails; //as per chages this object is work as maternal details form object..
	//AdmissionFormMasterDropDownsObj dropDowns;
	
	public PatientInfoMasterObj (){
		this.admissionForm=new PatientInfoAdmissonFormObj();
		this.addChild = new PatientInfoAddChildObj();
		this.childDetails = new PatientInfoChildDetailsObj();
	//	this.dropDowns = new AdmissionFormMasterDropDownsObj();
	}
	public PatientInfoAdmissonFormObj getAdmissionForm() {
		return admissionForm;
	}
	public void setAdmissionForm(PatientInfoAdmissonFormObj admissionForm) {
		this.admissionForm = admissionForm;
	}
	public PatientInfoAddChildObj getAddChild() {
		return addChild;
	}
	public void setAddChild(PatientInfoAddChildObj addChild) {
		this.addChild = addChild;
	}
	public PatientInfoChildDetailsObj getChildDetails() {
		return childDetails;
	}
	public void setChildDetails(PatientInfoChildDetailsObj childDetails) {
		this.childDetails = childDetails;
	}
	public String getTypeOfPatient() {
		return typeOfPatient;
	}
	public void setTypeOfPatient(String typeOfPatient) {
		this.typeOfPatient = typeOfPatient;
	}
	
	
	
}
