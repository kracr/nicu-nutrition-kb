package com.inicu.models;

import com.inicu.postgres.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProceduresMasterPojo {

	PeripheralCannula emptyPeripheralObj;
	List<PeripheralCannula> currentPeripheralList;
	List<PeripheralCannula> pastPeripheralList;

	CentralLine emptyCentralLineObj;
	List<CentralLine> currentCentralLineList;
	List<CentralLine> pastCentralLineList;

	LumbarPuncture emptyLumbarPunctureObj;
	List<LumbarPuncture> pastLumbarPunctureList;

	Vtap emptyVtapObj;
	List<Vtap> pastVtapList;

//	EtIntubation emptyEtIntubationObj;
	EtIntubation currentEtIntubationObj;
	List<EtIntubation> currentEtIntubationList;
	List<EtIntubation> pastEtIntubationList;
	//List<Object> pastEtIntubationTableList;
	List<EtIntubationReason> etIntubationReasonList;
	
	// chesttube Object here
	ChestTubePOJO chestTubeObj;

	PneumothoraxPOJO pneumothorax;

	// exchange transfusion Object
	Float workingWeight; 
	SaJaundice isPlanned;	
	String motherBloodGroup;

	ProcedureExchangeTransfusion exchangeTransfusionObj;
	List<ProcedureExchangeTransfusion> allExchangeTransfusionList;
	List<ProcedureExchangeTransfusion> exchangeTransfusionObjList;
	List<ProcedureExchangeTransfusion> currentExchangeTransfusionList;
	List<Object> exchangeDateList;
	List<KeyValueObj> doctorList;
	BabyDetail babyDetailObj;
	ImagePOJO bloodBagImage;

	// ET Suction
	EtSuction emptyEtSuctionObj;
	List<EtSuction> pasEtSuctionList;

	//Other
	ProcedureOther emptyOtherObj;
	List<ProcedureOther> pastOtherList;
	List<ProcedureOther> pastprocedureOtherOrdersList;
	List<KeyValueObj> orderInvestigation;

	HashMap<Object, List<RefTestslist>> testsList;
		
	//PeritonealDialysis
	
	PeritonealDialysis peritonealDialysisObj;
	List<PeritonealDialysis> allPeritonealDialysisObjList;
	List<PeritonealDialysis> peritonealDialysisObjList;
	List<PeritonealDialysis> currentPeritonealDialysisList;
	
	public ProceduresMasterPojo() {
		super();
	}

	public ProceduresMasterPojo(String uhid, String loggeduser) {
		super();
		
		this.emptyEtSuctionObj = new EtSuction();
		this.emptyEtSuctionObj.setUhid(uhid);
		this.emptyEtSuctionObj.setLoggeduser(loggeduser);
		
		this.emptyPeripheralObj = new PeripheralCannula();
		this.emptyPeripheralObj.setUhid(uhid);
		this.emptyPeripheralObj.setLoggeduser(loggeduser);

		this.currentPeripheralList = new ArrayList<PeripheralCannula>();
		this.currentPeripheralList.add(this.emptyPeripheralObj);

		this.emptyCentralLineObj = new CentralLine();
		this.emptyCentralLineObj.setUhid(uhid);
		this.emptyCentralLineObj.setLoggeduser(loggeduser);

		this.currentCentralLineList = new ArrayList<CentralLine>();
		this.currentCentralLineList.add(this.emptyCentralLineObj);

		this.emptyLumbarPunctureObj = new LumbarPuncture();
		this.emptyVtapObj = new Vtap();

//		this.emptyEtIntubationObj = new EtIntubation();
//		this.emptyEtIntubationObj.setUhid(uhid);
//		this.emptyEtIntubationObj.setLoggeduser(loggeduser);

		this.currentEtIntubationObj = new EtIntubation();
		this.currentEtIntubationObj.setUhid(uhid);
		this.currentEtIntubationObj.setLoggeduser(loggeduser);

		this.currentEtIntubationList = new ArrayList<EtIntubation>();
//		this.currentEtIntubationList.add(this.emptyEtIntubationObj);

		this.chestTubeObj = new ChestTubePOJO();
		
		this.isPlanned = new SaJaundice();
		this.workingWeight = null;
		this.exchangeTransfusionObj = new ProcedureExchangeTransfusion();
		this.allExchangeTransfusionList = new ArrayList<ProcedureExchangeTransfusion>();
		this.exchangeTransfusionObjList = new ArrayList<ProcedureExchangeTransfusion>();
		this.exchangeDateList = new ArrayList<Object>();
		this.babyDetailObj = new BabyDetail();
		this.doctorList = new ArrayList<KeyValueObj>();
		this.bloodBagImage = new ImagePOJO();

		this.emptyOtherObj = new ProcedureOther();
		this.emptyOtherObj.setUhid(uhid);
		this.emptyOtherObj.setLoggeduser(loggeduser);
	
		this.peritonealDialysisObj = new PeritonealDialysis();
		this.allPeritonealDialysisObjList = new ArrayList<PeritonealDialysis>();
		this.peritonealDialysisObjList = new ArrayList<PeritonealDialysis>();
		
	}

	public ChestTubePOJO getChestTubeObj() {
		return chestTubeObj;
	}

	public void setChestTubeObj(ChestTubePOJO chestTubeObj) {
		this.chestTubeObj = chestTubeObj;
	}

	public PeripheralCannula getEmptyPeripheralObj() {
		return emptyPeripheralObj;
	}

	public void setEmptyPeripheralObj(PeripheralCannula emptyPeripheralObj) {
		this.emptyPeripheralObj = emptyPeripheralObj;
	}

	public List<PeripheralCannula> getCurrentPeripheralList() {
		return currentPeripheralList;
	}

	public void setCurrentPeripheralList(List<PeripheralCannula> currentPeripheralList) {
		this.currentPeripheralList = currentPeripheralList;
	}

	public List<PeripheralCannula> getPastPeripheralList() {
		return pastPeripheralList;
	}

	public void setPastPeripheralList(List<PeripheralCannula> pastPeripheralList) {
		this.pastPeripheralList = pastPeripheralList;
	}

	public CentralLine getEmptyCentralLineObj() {
		return emptyCentralLineObj;
	}

	public void setEmptyCentralLineObj(CentralLine emptyCentralLineObj) {
		this.emptyCentralLineObj = emptyCentralLineObj;
	}

	public List<CentralLine> getCurrentCentralLineList() {
		return currentCentralLineList;
	}

	public void setCurrentCentralLineList(List<CentralLine> currentCentralLineList) {
		this.currentCentralLineList = currentCentralLineList;
	}

	public List<CentralLine> getPastCentralLineList() {
		return pastCentralLineList;
	}

	public void setPastCentralLineList(List<CentralLine> pastCentralLineList) {
		this.pastCentralLineList = pastCentralLineList;
	}

	public LumbarPuncture getEmptyLumbarPunctureObj() {
		return emptyLumbarPunctureObj;
	}

	public void setEmptyLumbarPunctureObj(LumbarPuncture emptyLumbarPunctureObj) {
		this.emptyLumbarPunctureObj = emptyLumbarPunctureObj;
	}

	public List<LumbarPuncture> getPastLumbarPunctureList() {
		return pastLumbarPunctureList;
	}

	public void setPastLumbarPunctureList(List<LumbarPuncture> pastLumbarPunctureList) {
		this.pastLumbarPunctureList = pastLumbarPunctureList;
	}

	public Vtap getEmptyVtapObj() {
		return emptyVtapObj;
	}

	public void setEmptyVtapObj(Vtap emptyVtapObj) {
		this.emptyVtapObj = emptyVtapObj;
	}

	public List<Vtap> getPastVtapList() {
		return pastVtapList;
	}

	public void setPastVtapList(List<Vtap> pastVtapList) {
		this.pastVtapList = pastVtapList;
	}

//	public EtIntubation getEmptyEtIntubationObj() {
//		return emptyEtIntubationObj;
//	}
//
//	public void setEmptyEtIntubationObj(EtIntubation emptyEtIntubationObj) {
//		this.emptyEtIntubationObj = emptyEtIntubationObj;
//	}

	public EtIntubation getCurrentEtIntubationObj() {
		return currentEtIntubationObj;
	}

	public void setCurrentEtIntubationObj(EtIntubation currentEtIntubationObj) {
		this.currentEtIntubationObj = currentEtIntubationObj;
	}
	
	public List<EtIntubation> getCurrentEtIntubationList() {
		return currentEtIntubationList;
	}

	public void setCurrentEtIntubationList(List<EtIntubation> currentEtIntubationList) {
		this.currentEtIntubationList = currentEtIntubationList;
	}

	public List<EtIntubation> getPastEtIntubationList() {
		return pastEtIntubationList;
	}

	public void setPastEtIntubationList(List<EtIntubation> pastEtIntubationList) {
		this.pastEtIntubationList = pastEtIntubationList;
	}
	
//	public List<Object> getPastEtIntubationTableList() {
//		return pastEtIntubationTableList;
//	}
//
//	public void setPastEtIntubationTableList(List<Object> pastEtIntubationTableList) {
//		this.pastEtIntubationTableList = pastEtIntubationTableList;
//	}


	public List<EtIntubationReason> getEtIntubationReasonList() {
		return etIntubationReasonList;
	}

	public void setEtIntubationReasonList(List<EtIntubationReason> etIntubationReasonList) {
		this.etIntubationReasonList = etIntubationReasonList;
	}
	
	public List<KeyValueObj> getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(List<KeyValueObj> orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public HashMap<Object, List<RefTestslist>> getTestsList() {
		return testsList;
	}

	public void setTestsList(HashMap<Object, List<RefTestslist>> testsList) {
		this.testsList = testsList;
	}

	public ProcedureOther getEmptyOtherObj() {
		return emptyOtherObj;
	}

	public void setEmptyOtherObj(ProcedureOther emptyOtherObj) {
		this.emptyOtherObj = emptyOtherObj;
	}

	public List<ProcedureOther> getPastOtherList() {
		return pastOtherList;
	}

	public void setPastOtherList(List<ProcedureOther> pastOtherList) {
		this.pastOtherList = pastOtherList;
	}

	public PneumothoraxPOJO getPneumothorax() {
		return pneumothorax;
	}

	public void setPneumothorax(PneumothoraxPOJO pneumothorax) {
		this.pneumothorax = pneumothorax;
	}

	public EtSuction getEmptyEtSuctionObj() {
		return emptyEtSuctionObj;
	}

	public void setEmptyEtSuctionObj(EtSuction emptyEtSuctionObj) {
		this.emptyEtSuctionObj = emptyEtSuctionObj;
	}

	public List<EtSuction> getPasEtSuctionList() {
		return pasEtSuctionList;
	}

	public void setPasEtSuctionList(List<EtSuction> pasEtSuctionList) {
		this.pasEtSuctionList = pasEtSuctionList;
	}

	public List<ProcedureOther> getPastprocedureOtherOrdersList() {
		return pastprocedureOtherOrdersList;
	}

	public void setPastprocedureOtherOrdersList(List<ProcedureOther> pastprocedureOtherOrdersList) {
		this.pastprocedureOtherOrdersList = pastprocedureOtherOrdersList;
	}
	
	public Float getWorkingWeight() {
		return workingWeight;
	}

	public void setWorkingWeight(Float workingWeight) {
		this.workingWeight = workingWeight;
	}
	
	public String getMotherBloodGroup() {
		return motherBloodGroup;
	}

	public void setMotherBloodGroup(String motherBloodGroup) {
		this.motherBloodGroup = motherBloodGroup;
	}
	
	public SaJaundice getIsPlanned() {
		return isPlanned;
	}

	public void setIsPlanned(SaJaundice isPlanned) {
		this.isPlanned = isPlanned;
	}

	public ProcedureExchangeTransfusion getExchangeTransfusionObj() {
		return exchangeTransfusionObj;
	}

	public void setExchangeTransfusionObj(ProcedureExchangeTransfusion exchangeTransfusionObj) {
		this.exchangeTransfusionObj = exchangeTransfusionObj;
	}

	public List<ProcedureExchangeTransfusion> getAllExchangeTransfusionList() {
		return allExchangeTransfusionList;
	}

	public void setAllExchangeTransfusionList(List<ProcedureExchangeTransfusion> allExchangeTransfusionList) {
		this.allExchangeTransfusionList = allExchangeTransfusionList;
	}

	public List<ProcedureExchangeTransfusion> getExchangeTransfusionObjList() {
		return exchangeTransfusionObjList;
	}

	public void setExchangeTransfusionObjList(List<ProcedureExchangeTransfusion> exchangeTransfusionObjList) {
		this.exchangeTransfusionObjList = exchangeTransfusionObjList;
	}

	public List<ProcedureExchangeTransfusion> getCurrentExchangeTransfusionList() {
		return currentExchangeTransfusionList;
	}

	public void setCurrentExchangeTransfusionList(List<ProcedureExchangeTransfusion> currentExchangeTransfusionList) {
		this.currentExchangeTransfusionList = currentExchangeTransfusionList;
	}

	public List<Object> getExchangeDateList() {
		return exchangeDateList;
	}

	public void setExchangeDateList(List<Object> exchangeDateList) {
		this.exchangeDateList = exchangeDateList;
	}

	public BabyDetail getBabyDetailObj() {
		return babyDetailObj;
	}

	public void setBabyDetailObj(BabyDetail babyDetailObj) {
		this.babyDetailObj = babyDetailObj;
	}

	public List<KeyValueObj> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(List<KeyValueObj> doctorList) {
		this.doctorList = doctorList;
	}

	public ImagePOJO getBloodBagImage() {
		return bloodBagImage;
	}

	public void setBloodBagImage(ImagePOJO bloodBagImage) {
		this.bloodBagImage = bloodBagImage;
	}

	public PeritonealDialysis getPeritonealDialysisObj() {
		return peritonealDialysisObj;
	}

	public void setPeritonealDialysisObj(PeritonealDialysis peritonealDialysisObj) {
		this.peritonealDialysisObj = peritonealDialysisObj;
	}

	public List<PeritonealDialysis> getAllPeritonealDialysisObjList() {
		return allPeritonealDialysisObjList;
	}

	public void setAllPeritonealDialysisObjList(List<PeritonealDialysis> allPeritonealDialysisObjList) {
		this.allPeritonealDialysisObjList = allPeritonealDialysisObjList;
	}

	public List<PeritonealDialysis> getPeritonealDialysisObjList() {
		return peritonealDialysisObjList;
	}

	public void setPeritonealDialysisObjList(List<PeritonealDialysis> peritonealDialysisObjList) {
		this.peritonealDialysisObjList = peritonealDialysisObjList;
	}

	public List<PeritonealDialysis> getCurrentPeritonealDialysisList() {
		return currentPeritonealDialysisList;
	}

	public void setCurrentPeritonealDialysisList(List<PeritonealDialysis> currentPeritonealDialysisList) {
		this.currentPeritonealDialysisList = currentPeritonealDialysisList;
	}	
}
