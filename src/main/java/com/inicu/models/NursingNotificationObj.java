package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ProcedureOther;
import com.inicu.postgres.entities.SaRespApnea;
import com.inicu.postgres.entities.*;

/**
*
*
*Purpose: Adding all other assessments in nurses to do list

*@Updated on: June 28 2019
*@author: Shweta Nichani Mohanani
*/
public class NursingNotificationObj {
	
	List<StableNote> stableNoteList;
	List<ProcedureOther> procedureOtherList;
	List<SaRespRds> rdsList;
	List<SaRespApnea> apneaList;
	List<SaRespPphn> pphnList;
	List<SaRespPneumo> pneumoList;
	List<SaJaundice> jaundiceList;
	List<SaSepsis> sepsisList;
	List<SaInfectVap> vapList;
	List<SaInfectClabsi> clabsiList;
	List<SaInfectIntrauterine> intrauterineList;
	List<SaCnsAsphyxia> asphyxiaList;
	List<SaCnsSeizures> seizuresList;
	List<SaCnsIvh> ivhList;
	List<SaHypoglycemia> hypoglycemiaList;
	List<SaRenalfailure> renalList;
	List<SaNec> necList;
	List<SaFeedIntolerance> feedList;
	List<SaMiscellaneous> miscList;
	List<SaMiscellaneous2> misc2List;
	List<SaCnsEncephalopathy> encephalopathyList;
	List<SaCnsNeuromuscularDisorders> neuroList;
	List<SaCnsHydrocephalus> hydroList;
	List<SaJaundice> jaundiceTreatmentList;
	Boolean skipOneFeed;
	List<BabyPrescription> prescriptionList;
	List<InvestigationOrdered> investigationOrdersList;
	List<DoctorBloodProducts> bloodProductsList;
	List<RespSupport> respSupportList;
	List<SaJaundice> photoTherapyList;
	String skipOneFeedEntryTimeStamp = "";
	List<CentralLine> centralLineList;
	List<BabyfeedDetail> babyfeedDetailList;
	List<SaRespPphn> respPphnList;
	List<SaShock> shockList;

	public NursingNotificationObj(){
		this.skipOneFeed=false;
	}
	public List<StableNote> getStableNoteList() {
		return stableNoteList;
	}
	public void setStableNoteList(List<StableNote> stableNoteList) {
		this.stableNoteList = stableNoteList;
	}
	public List<SaRespRds> getRdsList() {
		return rdsList;
	}
	public void setRdsList(List<SaRespRds> rdsList) {
		this.rdsList = rdsList;
	}
	public List<SaRespApnea> getApneaList() {
		return apneaList;
	}
	public void setApneaList(List<SaRespApnea> apneaList) {
		this.apneaList = apneaList;
	}
	public List<SaRespPphn> getPphnList() {
		return pphnList;
	}
	public void setPphnList(List<SaRespPphn> pphnList) {
		this.pphnList = pphnList;
	}
	public List<SaRespPneumo> getPneumoList() {
		return pneumoList;
	}
	public void setPneumoList(List<SaRespPneumo> pneumoList) {
		this.pneumoList = pneumoList;
	}
	public List<SaJaundice> getJaundiceList() {
		return jaundiceList;
	}
	public void setJaundiceList(List<SaJaundice> jaundiceList) {
		this.jaundiceList = jaundiceList;
	}
	public List<SaSepsis> getSepsisList() {
		return sepsisList;
	}
	public void setSepsisList(List<SaSepsis> sepsisList) {
		this.sepsisList = sepsisList;
	}
	public List<SaInfectVap> getVapList() {
		return vapList;
	}
	public void setVapList(List<SaInfectVap> vapList) {
		this.vapList = vapList;
	}
	public List<SaInfectClabsi> getClabsiList() {
		return clabsiList;
	}
	public void setClabsiList(List<SaInfectClabsi> clabsiList) {
		this.clabsiList = clabsiList;
	}
	public List<SaInfectIntrauterine> getIntrauterineList() {
		return intrauterineList;
	}
	public void setIntrauterineList(List<SaInfectIntrauterine> intrauterineList) {
		this.intrauterineList = intrauterineList;
	}
	public List<SaCnsAsphyxia> getAsphyxiaList() {
		return asphyxiaList;
	}
	public void setAsphyxiaList(List<SaCnsAsphyxia> asphyxiaList) {
		this.asphyxiaList = asphyxiaList;
	}
	public List<SaCnsSeizures> getSeizuresList() {
		return seizuresList;
	}
	public void setSeizuresList(List<SaCnsSeizures> seizuresList) {
		this.seizuresList = seizuresList;
	}
	public List<SaCnsIvh> getIvhList() {
		return ivhList;
	}
	public void setIvhList(List<SaCnsIvh> ivhList) {
		this.ivhList = ivhList;
	}
	public List<SaHypoglycemia> getHypoglycemiaList() {
		return hypoglycemiaList;
	}
	public void setHypoglycemiaList(List<SaHypoglycemia> hypoglycemiaList) {
		this.hypoglycemiaList = hypoglycemiaList;
	}
	public List<SaRenalfailure> getRenalList() {
		return renalList;
	}
	public void setRenalList(List<SaRenalfailure> renalList) {
		this.renalList = renalList;
	}
	public List<SaNec> getNecList() {
		return necList;
	}
	public void setNecList(List<SaNec> necList) {
		this.necList = necList;
	}
	public List<SaFeedIntolerance> getFeedList() {
		return feedList;
	}
	public void setFeedList(List<SaFeedIntolerance> feedList) {
		this.feedList = feedList;
	}
	public List<SaMiscellaneous> getMiscList() {
		return miscList;
	}
	public void setMiscList(List<SaMiscellaneous> miscList) {
		this.miscList = miscList;
	}
	public List<SaMiscellaneous2> getMisc2List() {
		return misc2List;
	}
	public void setMisc2List(List<SaMiscellaneous2> misc2List) {
		this.misc2List = misc2List;
	}
	public List<SaCnsEncephalopathy> getEncephalopathyList() {
		return encephalopathyList;
	}
	public void setEncephalopathyList(List<SaCnsEncephalopathy> encephalopathyList) {
		this.encephalopathyList = encephalopathyList;
	}
	public List<SaCnsNeuromuscularDisorders> getNeuroList() {
		return neuroList;
	}
	public void setNeuroList(List<SaCnsNeuromuscularDisorders> neuroList) {
		this.neuroList = neuroList;
	}
	public List<SaCnsHydrocephalus> getHydroList() {
		return hydroList;
	}
	public void setHydroList(List<SaCnsHydrocephalus> hydroList) {
		this.hydroList = hydroList;
	}
	public List<ProcedureOther> getProcedureOtherList() {
		return procedureOtherList;
	}
	public void setProcedureOtherList(List<ProcedureOther> procedureOtherList) {
		this.procedureOtherList = procedureOtherList;
	}

	public List<SaJaundice> getJaundiceTreatmentList() {
		return jaundiceTreatmentList;
	}

	public void setJaundiceTreatmentList(List<SaJaundice> jaundiceTreatmentList) {
		this.jaundiceTreatmentList = jaundiceTreatmentList;
	}

	public Boolean getSkipOneFeed() {
		return skipOneFeed;
	}

	public void setSkipOneFeed(Boolean skipOneFeed) {
		this.skipOneFeed = skipOneFeed;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public List<InvestigationOrdered> getInvestigationOrdersList() {
		return investigationOrdersList;
	}

	public void setInvestigationOrdersList(List<InvestigationOrdered> investigationOrdersList) {
		this.investigationOrdersList = investigationOrdersList;
	}

	public List<DoctorBloodProducts> getBloodProductsList() {
		return bloodProductsList;
	}

	public void setBloodProductsList(List<DoctorBloodProducts> bloodProductsList) {
		this.bloodProductsList = bloodProductsList;
	}

	public List<RespSupport> getRespSupportList() {
		return respSupportList;
	}

	public void setRespSupportList(List<RespSupport> respSupportList) {
		this.respSupportList = respSupportList;
	}

	public List<SaJaundice> getPhotoTherapyList() {
		return photoTherapyList;
	}

	public void setPhotoTherapyList(List<SaJaundice> photoTherapyList) {
		this.photoTherapyList = photoTherapyList;
	}

	public String getSkipOneFeedEntryTimeStamp() {
		return skipOneFeedEntryTimeStamp;
	}

	public void setSkipOneFeedEntryTimeStamp(String skipOneFeedEntryTimeStamp) {
		this.skipOneFeedEntryTimeStamp = skipOneFeedEntryTimeStamp;
	}

	public List<CentralLine> getCentralLineList() {
		return centralLineList;
	}

	public void setCentralLineList(List<CentralLine> centralLineList) {
		this.centralLineList = centralLineList;
	}

	public List<BabyfeedDetail> getBabyfeedDetailList() {
		return babyfeedDetailList;
	}

	public void setBabyfeedDetailList(List<BabyfeedDetail> babyfeedDetailList) {
		this.babyfeedDetailList = babyfeedDetailList;
	}

	public List<SaRespPphn> getRespPphnList() {
		return respPphnList;
	}

	public void setRespPphnList(List<SaRespPphn> respPphnList) {
		this.respPphnList = respPphnList;
	}
	public List<SaShock> getShockList() {
		return shockList;
	}
	public void setShockList(List<SaShock> shockList) {
		this.shockList = shockList;
	}
	
}
