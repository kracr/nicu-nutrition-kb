package com.inicu.models;

import java.util.List;

import javax.persistence.Transient;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.CentralLine;
import com.inicu.postgres.entities.DoctorBloodProducts;
import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.ProcedureOther;
import com.inicu.postgres.entities.RefEnAddtivesBrand;
import com.inicu.postgres.entities.RefMasterfeedmethod;
import com.inicu.postgres.entities.RefMasterfeedtype;
import com.inicu.postgres.entities.SaShock;

public class DoctorOrdersPOJO {
	List<ProcedureOther> procedureObj;
	List<BabyfeedDetail> babyFeedObj;
	List<CentralLine> centralLineObj;
	List<BabyPrescription> IVMedicineList;
	List<DoctorBloodProducts> bloodProductObj;
	List<BabyPrescription> babyPrescriptionList;
	FeedCalculatorPOJO feedCalulator;
	List<RefMasterfeedtype> refFeedTypeList;
	List<RefMasterfeedmethod> refFeedMethodList;
	List<InvestigationOrdered> invOrderedList;
	List<RefEnAddtivesBrand> addtivesbrandList;

	@Transient
	private BabyVisit basicDetails;

	public DoctorOrdersPOJO() {
		super();
	}
	public List<ProcedureOther> getProcedureObj() {
		return procedureObj;
	}
	public void setProcedureObj(List<ProcedureOther> procedureObj) {
		this.procedureObj = procedureObj;
	}
	public List<BabyfeedDetail> getBabyFeedObj() {
		return babyFeedObj;
	}
	public void setBabyFeedObj(List<BabyfeedDetail> babyFeedObj) {
		this.babyFeedObj = babyFeedObj;
	}
	public List<CentralLine> getCentralLineObj() {
		return centralLineObj;
	}
	public void setCentralLineObj(List<CentralLine> centralLineObj) {
		this.centralLineObj = centralLineObj;
	}
	public List<DoctorBloodProducts> getBloodProductObj() {
		return bloodProductObj;
	}
	public void setBloodProductObj(List<DoctorBloodProducts> bloodProductObj) {
		this.bloodProductObj = bloodProductObj;
	}
	public List<BabyPrescription> getIVMedicineList() {
		return IVMedicineList;
	}
	public void setIVMedicineList(List<BabyPrescription> iVMedicineList) {
		IVMedicineList = iVMedicineList;
	}
	public List<BabyPrescription> getBabyPrescriptionList() {
		return babyPrescriptionList;
	}
	public void setBabyPrescriptionList(List<BabyPrescription> babyPrescriptionList) {
		this.babyPrescriptionList = babyPrescriptionList;
	}
	public FeedCalculatorPOJO getFeedCalulator() {
		return feedCalulator;
	}
	public void setFeedCalulator(FeedCalculatorPOJO feedCalulator) {
		this.feedCalulator = feedCalulator;
	}
	public List<RefMasterfeedtype> getRefFeedTypeList() {
		return refFeedTypeList;
	}
	public void setRefFeedTypeList(List<RefMasterfeedtype> refFeedTypeList) {
		this.refFeedTypeList = refFeedTypeList;
	}
	public List<RefMasterfeedmethod> getRefFeedMethodList() {
		return refFeedMethodList;
	}
	public void setRefFeedMethodList(List<RefMasterfeedmethod> refFeedMethodList) {
		this.refFeedMethodList = refFeedMethodList;
	}
	public BabyVisit getBasicDetails() {
		return basicDetails;
	}
	public void setBasicDetails(BabyVisit basicDetails) {
		this.basicDetails = basicDetails;
	}
	public List<InvestigationOrdered> getInvOrderedList() {
		return invOrderedList;
	}
	public void setInvOrderedList(List<InvestigationOrdered> invOrderedList) {
		this.invOrderedList = invOrderedList;
	}
	public List<RefEnAddtivesBrand> getAddtivesbrandList() {
		return addtivesbrandList;
	}
	public void setAddtivesbrandList(List<RefEnAddtivesBrand> addtivesbrandList) {
		this.addtivesbrandList = addtivesbrandList;
	}
	
}
