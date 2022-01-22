package com.inicu.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONObject;

import com.inicu.postgres.entities.NursingBloodGas;

import java.sql.Timestamp;

public class DeviceGraphDataJson {

	DeviceGraphAxisJson SPO2;
	DeviceGraphAxisJson RR;
	DeviceGraphAxisJson HR;
	DeviceGraphAxisJson event;
	DeviceGraphAxisJson PR;  // pulse rate
	DeviceGraphAxisJson ST_HRV;
	DeviceGraphAxisJson LT_HRV;
	DeviceGraphAxisJson ST_PRV;
	DeviceGraphAxisJson LT_PRV;
	DeviceGraphAxisJson PHYSISCORE;
	DeviceGraphAxisJson PIP;
	DeviceGraphAxisJson PEEP;
	DeviceGraphAxisJson FIO2;
	DeviceGraphAxisJson MAP;
	DeviceGraphAxisJson RATE;
	DeviceGraphAxisJson HDP;

	DeviceGraphAxisJson channel1Rso2;
	DeviceGraphAxisJson channel2Rso2;
	DeviceGraphAxisJson channel3Rso2;
	DeviceGraphAxisJson channel4Rso2;

	DeviceGraphAxisJson nbpS;
	DeviceGraphAxisJson nbpD;
	DeviceGraphAxisJson nbpM;

	DeviceGraphAxisJson ibpS;
	DeviceGraphAxisJson ibpD;
	DeviceGraphAxisJson ibpM;

	DeviceGraphAxisJson cTemp;
	DeviceGraphAxisJson pTemp;
	DeviceGraphAxisJson humidity;

	List<DeviceGraphAssessmentJSON> jaundice;
	List<DeviceGraphAssessmentJSON> rds;
	List<DeviceGraphAssessmentJSON> sepsis;
	List<DeviceGraphAssessmentJSON> nec;
	List<DeviceGraphAssessmentJSON> pphn;
	List<DeviceGraphAssessmentJSON> apnea;
	List<DeviceGraphAssessmentJSON> pneumothorax;
	List<DeviceGraphAssessmentJSON> asphyxia;
	List<DeviceGraphAssessmentJSON> seizures;
	List<DeviceGraphAssessmentJSON> renalFailure;
	List<DeviceGraphAssessmentJSON> hypoglycemia;
	List<DeviceGraphAssessmentJSON> feedIntolerance;

	List<DeviceGraphAssessmentJSON> phototherapyList;
	//  Surfactant usage
	List<Timestamp> surfactantUsageList;

	// Oxygenation Index (INO)
	List<List<Object>> oxygenationIndexList;

	// Enteral Intake
	List<List<Object>> enteralIntakeList;

	// Parenteral  Intake
	List<List<Object>> parenteralIntakeList;

	// Urine Output
	List<List<Object>> urineOutputList;

	// Abdominal Girth
	List<List<Object>> abdominalGirthList;

	// Events
	List<Timestamp> seizuresEvents;
	List<Timestamp> apneaEvents;
	List<Timestamp> disaturationEvents;

	List<DeviceGraphMedicationJSON> medicineList;
	List<DeviceGraphMedicationJSON> procedureList;
	
	List<NursingBloodGas> bloodGasList;
	
	String hmlmReason;
	Timestamp predictedHmLm;

	List<List<Object>> SPO2Object;
	List<List<Object>> nSofaObject;
	List<List<Object>> inotropeObject;
	List<List<Object>> spo2fio2RatioObject;
	List<List<Object>> RRObject;
	List<List<Object>> HRObject;
	List<List<Object>> eventObject;
	List<List<Object>> HDPObject;
	List<List<Object>> PRObject;  // pulse rate
	List<List<Object>> FIO2Object;
	List<List<Object>> PEEPObject; 
	List<List<Object>> PIPObject;
	List<List<Object>> ST_HRVObject;
	List<List<Object>> LT_HRVObject;
	List<List<Object>> ST_PRVObject;
	List<List<Object>> LT_PRVObject;
	List<List<Object>> PHYSISCOREObject;
	List<List<Object>> PIObject;
	List<List<Object>> o3rSO2_1Object;
	List<List<Object>> o3rSO2_2Object;
	List<List<Object>> hmStatusObject;

	List<List<Object>> nbpSObject;
	List<List<Object>> nbpDObject;
	List<List<Object>> nbpMObject;

	List<List<Object>> ibpSObject;
	List<List<Object>> ibpDObject;
	List<List<Object>> ibpMObject;

	List<List<Object>> centralTempObject;
	List<List<Object>> peripheralTempObject;
	List<List<Object>> humidityObject;

	List<List<Object>> MAPObject;
	List<List<Object>> FLOWRATEObject; 
	List<List<Object>> channel1Rso2Object;
	List<List<Object>> channel2Rso2Object;
	List<List<Object>> channel3Rso2Object;
	List<List<Object>> channel4Rso2Object;
	String isDaily;
	String uhid;
	String fromdate;
	String todate;
	String startTime;
	String endTime;
	Double minResidualVarianceHR;
	Double maxResidualVarianceHR;
	Double varianceResidualVarianceHR;
	
	boolean medication;
	boolean procedure;
	boolean assessment;
	boolean bloodGas;
	boolean hmOutput;
	boolean labReports;

	// variable added
	boolean surfactantUsage;
	boolean oxygenationIndex;
	boolean phototheraphy;
	boolean enteral;
	boolean parenteral;
	boolean urine;
	boolean adb;
	boolean events;

	DeviceGraphAxisJson ALL;
	//monthly, daily, hourly
	String graphType;
	
	private Timestamp entryTime;
	
	HashSet<String> labTestCategoryList = new HashSet<String>();	
	
	HashMap<String, TreeMap<Timestamp, List<Object[]>>> masterMap;
	
	VitalRangeJSON range;

	public DeviceGraphDataJson() {
		this.SPO2 = new DeviceGraphAxisJson();
		this.event = new DeviceGraphAxisJson();
		this.RR = new DeviceGraphAxisJson();
		this.HR = new DeviceGraphAxisJson();
		this.HDP = new DeviceGraphAxisJson();
		this.PR = new DeviceGraphAxisJson();
		this.ALL = new DeviceGraphAxisJson();
		this.PIP = new DeviceGraphAxisJson();
		this.PEEP = new DeviceGraphAxisJson();
		this.FIO2 = new DeviceGraphAxisJson();
		this.MAP = new DeviceGraphAxisJson();
		this.RATE = new DeviceGraphAxisJson();
		this.PHYSISCORE = new DeviceGraphAxisJson();
		this.ST_HRV = new DeviceGraphAxisJson();
		this.LT_HRV = new DeviceGraphAxisJson();
		this.ST_PRV = new DeviceGraphAxisJson();
		this.LT_PRV = new DeviceGraphAxisJson();

		this.nbpS=new DeviceGraphAxisJson();
		this.nbpD=new DeviceGraphAxisJson();
		this.nbpM=new DeviceGraphAxisJson();

		this.ibpS=new DeviceGraphAxisJson();
		this.ibpD=new DeviceGraphAxisJson();
		this.ibpM=new DeviceGraphAxisJson();

		this.cTemp=new DeviceGraphAxisJson();
		this.pTemp=new DeviceGraphAxisJson();
		this.humidity=new DeviceGraphAxisJson();

		this.surfactantUsageList=new ArrayList<>();
		this.oxygenationIndexList=new ArrayList<>();
		this.enteralIntakeList=new ArrayList<>();
		this.parenteralIntakeList=new ArrayList<>();
		this.urineOutputList=new ArrayList<>();
		this.phototherapyList=new ArrayList<>();
		this.abdominalGirthList=new ArrayList<>();
		this.seizuresEvents=new ArrayList<>();
		this.apneaEvents=new ArrayList<>();
		this.disaturationEvents=new ArrayList<>();

		this.isDaily = "hourly";
		this.graphType = "Daily";
		this.minResidualVarianceHR =0d;
		this.maxResidualVarianceHR=0d;
		this.varianceResidualVarianceHR=0d;	
	}

	public DeviceGraphAxisJson getNbpS() {
		return nbpS;
	}

	public void setNbpS(DeviceGraphAxisJson nbpS) {
		this.nbpS = nbpS;
	}

	public DeviceGraphAxisJson getNbpD() {
		return nbpD;
	}

	public void setNbpD(DeviceGraphAxisJson nbpD) {
		this.nbpD = nbpD;
	}

	public DeviceGraphAxisJson getNbpM() {
		return nbpM;
	}

	public void setNbpM(DeviceGraphAxisJson nbpM) {
		this.nbpM = nbpM;
	}

	public DeviceGraphAxisJson getIbpS() {
		return ibpS;
	}

	public void setIbpS(DeviceGraphAxisJson ibpS) {
		this.ibpS = ibpS;
	}

	public DeviceGraphAxisJson getIbpD() {
		return ibpD;
	}

	public void setIbpD(DeviceGraphAxisJson ibpD) {
		this.ibpD = ibpD;
	}

	public DeviceGraphAxisJson getIbpM() {
		return ibpM;
	}

	public void setIbpM(DeviceGraphAxisJson ibpM) {
		this.ibpM = ibpM;
	}

	public List<DeviceGraphAssessmentJSON> getPhototherapyList() {
		return phototherapyList;
	}

	public void setPhototherapyList(List<DeviceGraphAssessmentJSON> phototherapyList) {
		this.phototherapyList = phototherapyList;
	}

	public List<DeviceGraphAssessmentJSON> getHypoglycemia() {
		return hypoglycemia;
	}


	public List<DeviceGraphAssessmentJSON> getNec() {
		return nec;
	}

	public void setNec(List<DeviceGraphAssessmentJSON> nec) {
		this.nec = nec;
	}

	public void setHypoglycemia(List<DeviceGraphAssessmentJSON> hypoglycemia) {
		this.hypoglycemia = hypoglycemia;
	}



	public Double getMinResidualVarianceHR() {
		return minResidualVarianceHR;
	}



	public void setMinResidualVarianceHR(Double minResidualVarianceHR) {
		this.minResidualVarianceHR = minResidualVarianceHR;
	}



	public Double getMaxResidualVarianceHR() {
		return maxResidualVarianceHR;
	}



	public void setMaxResidualVarianceHR(Double maxResidualVarianceHR) {
		this.maxResidualVarianceHR = maxResidualVarianceHR;
	}



	public Double getVarianceResidualVarianceHR() {
		return varianceResidualVarianceHR;
	}



	public void setVarianceResidualVarianceHR(Double varianceResidualVarianceHR) {
		this.varianceResidualVarianceHR = varianceResidualVarianceHR;
	}



	public DeviceGraphAxisJson getST_HRV() {
		return ST_HRV;
	}



	public void setST_HRV(DeviceGraphAxisJson sT_HRV) {
		ST_HRV = sT_HRV;
	}


	public VitalRangeJSON getRange() {
		return range;
	}








	public void setRange(VitalRangeJSON range) {
		this.range = range;
	}








	public DeviceGraphAxisJson getLT_HRV() {
		return LT_HRV;
	}



	public void setLT_HRV(DeviceGraphAxisJson lT_HRV) {
		LT_HRV = lT_HRV;
	}



	public DeviceGraphAxisJson getPHYSISCORE() {
		return PHYSISCORE;
	}

	public void setPHYSISCORE(DeviceGraphAxisJson pHYSISCORE) {
		PHYSISCORE = pHYSISCORE;
	}

	public DeviceGraphAxisJson getSPO2() {
		return SPO2;
	}
	public void setSPO2(DeviceGraphAxisJson sPO2) {
		SPO2 = sPO2;
	}
	public DeviceGraphAxisJson getRR() {
		return RR;
	}
	public void setRR(DeviceGraphAxisJson rR) {
		RR = rR;
	}
	public DeviceGraphAxisJson getHR() {
		return HR;
	}
	public void setHR(DeviceGraphAxisJson hR) {
		HR = hR;
	}	
	public DeviceGraphAxisJson getPR() {
		return PR;
	}
	public void setPR(DeviceGraphAxisJson pR) {
		PR = pR;
	}

	public String isDaily() {
		return isDaily;
	}

	public void setDaily(String isDaily) {
		this.isDaily = isDaily;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public DeviceGraphAxisJson getALL() {
		return ALL;
	}

	public void setALL(DeviceGraphAxisJson aLL) {
		ALL = aLL;
	}

	public Timestamp getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}

	public DeviceGraphAxisJson getST_PRV() {
		return ST_PRV;
	}

	public void setST_PRV(DeviceGraphAxisJson sT_PRV) {
		ST_PRV = sT_PRV;
	}

	public DeviceGraphAxisJson getLT_PRV() {
		return LT_PRV;
	}

	public void setLT_PRV(DeviceGraphAxisJson lT_PRV) {
		LT_PRV = lT_PRV;
	}

	public List<DeviceGraphAssessmentJSON> getSepsis() {
		return sepsis;
	}



	public void setSepsis(List<DeviceGraphAssessmentJSON> sepsis) {
		this.sepsis = sepsis;
	}

	public List<DeviceGraphAssessmentJSON> getJaundice() {
		return jaundice;
	}



	public void setJaundice(List<DeviceGraphAssessmentJSON> jaundice) {
		this.jaundice = jaundice;
	}



	public List<DeviceGraphAssessmentJSON> getRds() {
		return rds;
	}



	public void setRds(List<DeviceGraphAssessmentJSON> rds) {
		this.rds = rds;
	}



	public List<DeviceGraphAssessmentJSON> getPphn() {
		return pphn;
	}



	public void setPphn(List<DeviceGraphAssessmentJSON> pphn) {
		this.pphn = pphn;
	}



	public List<DeviceGraphAssessmentJSON> getApnea() {
		return apnea;
	}



	public void setApnea(List<DeviceGraphAssessmentJSON> apnea) {
		this.apnea = apnea;
	}



	public List<DeviceGraphAssessmentJSON> getPneumothorax() {
		return pneumothorax;
	}



	public void setPneumothorax(List<DeviceGraphAssessmentJSON> pneumothorax) {
		this.pneumothorax = pneumothorax;
	}



	public List<DeviceGraphAssessmentJSON> getAsphyxia() {
		return asphyxia;
	}



	public void setAsphyxia(List<DeviceGraphAssessmentJSON> asphyxia) {
		this.asphyxia = asphyxia;
	}



	public List<DeviceGraphAssessmentJSON> getSeizures() {
		return seizures;
	}



	public void setSeizures(List<DeviceGraphAssessmentJSON> seizures) {
		this.seizures = seizures;
	}



	public List<DeviceGraphMedicationJSON> getMedicineList() {
		return medicineList;
	}



	public void setMedicineList(List<DeviceGraphMedicationJSON> medicineList) {
		this.medicineList = medicineList;
	}



	public List<NursingBloodGas> getBloodGasList() {
		return bloodGasList;
	}



	public void setBloodGasList(List<NursingBloodGas> bloodGasList) {
		this.bloodGasList = bloodGasList;
	}



	public List<DeviceGraphMedicationJSON> getProcedureList() {
		return procedureList;
	}



	public void setProcedureList(List<DeviceGraphMedicationJSON> procedureList) {
		this.procedureList = procedureList;
	}



	public List<List<Object>> getSPO2Object() {
		return SPO2Object;
	}



	public void setSPO2Object(List<List<Object>> sPO2Object) {
		SPO2Object = sPO2Object;
	}



	public List<List<Object>> getRRObject() {
		return RRObject;
	}



	public void setRRObject(List<List<Object>> rRObject) {
		RRObject = rRObject;
	}



	public List<List<Object>> getHRObject() {
		return HRObject;
	}



	public void setHRObject(List<List<Object>> hRObject) {
		HRObject = hRObject;
	}



	public List<List<Object>> getPRObject() {
		return PRObject;
	}



	public void setPRObject(List<List<Object>> pRObject) {
		PRObject = pRObject;
	}



	public List<List<Object>> getFIO2Object() {
		return FIO2Object;
	}



	public void setFIO2Object(List<List<Object>> fIO2Object) {
		FIO2Object = fIO2Object;
	}



	public List<List<Object>> getPEEPObject() {
		return PEEPObject;
	}



	public void setPEEPObject(List<List<Object>> pEEPObject) {
		PEEPObject = pEEPObject;
	}



	public List<List<Object>> getPIPObject() {
		return PIPObject;
	}



	public void setPIPObject(List<List<Object>> pIPObject) {
		PIPObject = pIPObject;
	}



	public List<List<Object>> getST_HRVObject() {
		return ST_HRVObject;
	}



	public void setST_HRVObject(List<List<Object>> sT_HRVObject) {
		ST_HRVObject = sT_HRVObject;
	}



	public List<List<Object>> getLT_HRVObject() {
		return LT_HRVObject;
	}



	public void setLT_HRVObject(List<List<Object>> lT_HRVObject) {
		LT_HRVObject = lT_HRVObject;
	}



	public List<List<Object>> getST_PRVObject() {
		return ST_PRVObject;
	}



	public void setST_PRVObject(List<List<Object>> sT_PRVObject) {
		ST_PRVObject = sT_PRVObject;
	}



	public List<List<Object>> getLT_PRVObject() {
		return LT_PRVObject;
	}



	public void setLT_PRVObject(List<List<Object>> lT_PRVObject) {
		LT_PRVObject = lT_PRVObject;
	}



	public List<List<Object>> getPHYSISCOREObject() {
		return PHYSISCOREObject;
	}



	public void setPHYSISCOREObject(List<List<Object>> pHYSISCOREObject) {
		PHYSISCOREObject = pHYSISCOREObject;
	}



	public List<List<Object>> getPIObject() {
		return PIObject;
	}



	public void setPIObject(List<List<Object>> pIObject) {
		PIObject = pIObject;
	}



	public List<List<Object>> getO3rSO2_1Object() {
		return o3rSO2_1Object;
	}



	public void setO3rSO2_1Object(List<List<Object>> o3rSO2_1Object) {
		this.o3rSO2_1Object = o3rSO2_1Object;
	}



	public List<List<Object>> getO3rSO2_2Object() {
		return o3rSO2_2Object;
	}



	public void setO3rSO2_2Object(List<List<Object>> o3rSO2_2Object) {
		this.o3rSO2_2Object = o3rSO2_2Object;
	}



	public List<List<Object>> getHmStatusObject() {
		return hmStatusObject;
	}



	public void setHmStatusObject(List<List<Object>> hmStatusObject) {
		this.hmStatusObject = hmStatusObject;
	}



	public boolean isMedication() {
		return medication;
	}



	public void setMedication(boolean medication) {
		this.medication = medication;
	}



	public boolean isProcedure() {
		return procedure;
	}



	public void setProcedure(boolean procedure) {
		this.procedure = procedure;
	}



	public boolean isAssessment() {
		return assessment;
	}



	public void setAssessment(boolean assessment) {
		this.assessment = assessment;
	}



	public boolean isBloodGas() {
		return bloodGas;
	}



	public void setBloodGas(boolean bloodGas) {
		this.bloodGas = bloodGas;
	}



	public boolean isHmOutput() {
		return hmOutput;
	}



	public void setHmOutput(boolean hmOutput) {
		this.hmOutput = hmOutput;
	}



	public List<DeviceGraphAssessmentJSON> getRenalFailure() {
		return renalFailure;
	}



	public void setRenalFailure(List<DeviceGraphAssessmentJSON> renalFailure) {
		this.renalFailure = renalFailure;
	}



	public List<DeviceGraphAssessmentJSON> getFeedIntolerance() {
		return feedIntolerance;
	}



	public void setFeedIntolerance(List<DeviceGraphAssessmentJSON> feedIntolerance) {
		this.feedIntolerance = feedIntolerance;
	}



	public List<List<Object>> getMAPObject() {
		return MAPObject;
	}



	public void setMAPObject(List<List<Object>> mAPObject) {
		MAPObject = mAPObject;
	}



	public List<List<Object>> getFLOWRATEObject() {
		return FLOWRATEObject;
	}



	public void setFLOWRATEObject(List<List<Object>> fLOWRATEObject) {
		FLOWRATEObject = fLOWRATEObject;
	}








	public boolean isLabReports() {
		return labReports;
	}








	public void setLabReports(boolean labReports) {
		this.labReports = labReports;
	}















	public HashMap<String, TreeMap<Timestamp, List<Object[]>>> getMasterMap() {
		return masterMap;
	}








	public void setMasterMap(HashMap<String, TreeMap<Timestamp, List<Object[]>>> masterMap) {
		this.masterMap = masterMap;
	}








	public HashSet<String> getLabTestCategoryList() {
		return labTestCategoryList;
	}








	public void setLabTestCategoryList(HashSet<String> labTestCategoryList) {
		this.labTestCategoryList = labTestCategoryList;
	}








	public String getHmlmReason() {
		return hmlmReason;
	}


	






	public List<List<Object>> getInotropeObject() {
		return inotropeObject;
	}

	public void setInotropeObject(List<List<Object>> inotropeObject) {
		this.inotropeObject = inotropeObject;
	}

	public List<List<Object>> getSpo2fio2RatioObject() {
		return spo2fio2RatioObject;
	}

	public void setSpo2fio2RatioObject(List<List<Object>> spo2fio2RatioObject) {
		this.spo2fio2RatioObject = spo2fio2RatioObject;
	}

	public void setHmlmReason(String hmlmReason) {
		this.hmlmReason = hmlmReason;
	}








	public Timestamp getPredictedHmLm() {
		return predictedHmLm;
	}








	public void setPredictedHmLm(Timestamp predictedHmLm) {
		this.predictedHmLm = predictedHmLm;
	}








	public DeviceGraphAxisJson getPIP() {
		return PIP;
	}








	public void setPIP(DeviceGraphAxisJson pIP) {
		PIP = pIP;
	}








	public DeviceGraphAxisJson getPEEP() {
		return PEEP;
	}








	public void setPEEP(DeviceGraphAxisJson pEEP) {
		PEEP = pEEP;
	}








	public DeviceGraphAxisJson getFIO2() {
		return FIO2;
	}








	public void setFIO2(DeviceGraphAxisJson fIO2) {
		FIO2 = fIO2;
	}








	public DeviceGraphAxisJson getMAP() {
		return MAP;
	}








	public void setMAP(DeviceGraphAxisJson mAP) {
		MAP = mAP;
	}








	public DeviceGraphAxisJson getRATE() {
		return RATE;
	}








	public void setRATE(DeviceGraphAxisJson rATE) {
		RATE = rATE;
	}








	public DeviceGraphAxisJson getChannel1Rso2() {
		return channel1Rso2;
	}








	public void setChannel1Rso2(DeviceGraphAxisJson channel1Rso2) {
		this.channel1Rso2 = channel1Rso2;
	}








	public DeviceGraphAxisJson getChannel2Rso2() {
		return channel2Rso2;
	}








	public void setChannel2Rso2(DeviceGraphAxisJson channel2Rso2) {
		this.channel2Rso2 = channel2Rso2;
	}








	public DeviceGraphAxisJson getChannel3Rso2() {
		return channel3Rso2;
	}








	public void setChannel3Rso2(DeviceGraphAxisJson channel3Rso2) {
		this.channel3Rso2 = channel3Rso2;
	}








	public DeviceGraphAxisJson getChannel4Rso2() {
		return channel4Rso2;
	}








	public void setChannel4Rso2(DeviceGraphAxisJson channel4Rso2) {
		this.channel4Rso2 = channel4Rso2;
	}








	public List<List<Object>> getChannel1Rso2Object() {
		return channel1Rso2Object;
	}








	public void setChannel1Rso2Object(List<List<Object>> channel1Rso2Object) {
		this.channel1Rso2Object = channel1Rso2Object;
	}








	public List<List<Object>> getChannel2Rso2Object() {
		return channel2Rso2Object;
	}








	public void setChannel2Rso2Object(List<List<Object>> channel2Rso2Object) {
		this.channel2Rso2Object = channel2Rso2Object;
	}








	public List<List<Object>> getChannel3Rso2Object() {
		return channel3Rso2Object;
	}








	public void setChannel3Rso2Object(List<List<Object>> channel3Rso2Object) {
		this.channel3Rso2Object = channel3Rso2Object;
	}








	public List<List<Object>> getChannel4Rso2Object() {
		return channel4Rso2Object;
	}








	public void setChannel4Rso2Object(List<List<Object>> channel4Rso2Object) {
		this.channel4Rso2Object = channel4Rso2Object;
	}


	public boolean isSurfactantUsage() {
		return surfactantUsage;
	}

	public void setSurfactantUsage(boolean surfactantUsage) {
		this.surfactantUsage = surfactantUsage;
	}

	public boolean isOxygenationIndex() {
		return oxygenationIndex;
	}

	public void setOxygenationIndex(boolean oxygenationIndex) {
		this.oxygenationIndex = oxygenationIndex;
	}

	public boolean isPhototheraphy() {
		return phototheraphy;
	}

	public void setPhototheraphy(boolean phototheraphy) {
		this.phototheraphy = phototheraphy;
	}

	public boolean isEnteral() {
		return enteral;
	}

	public void setEnteral(boolean enteral) {
		this.enteral = enteral;
	}

	public boolean isParenteral() {
		return parenteral;
	}

	public void setParenteral(boolean parenteral) {
		this.parenteral = parenteral;
	}

	public boolean isUrine() {
		return urine;
	}

	public void setUrine(boolean urine) {
		this.urine = urine;
	}

	public boolean isAdb() {
		return adb;
	}

	public void setAdb(boolean adb) {
		this.adb = adb;
	}

	public boolean isEvents() {
		return events;
	}

	public void setEvents(boolean events) {
		this.events = events;
	}

	public List<Timestamp> getSurfactantUsageList() {
		return surfactantUsageList;
	}

	public void setSurfactantUsageList(List<Timestamp> surfactantUsageList) {
		this.surfactantUsageList = surfactantUsageList;
	}

	public List<Timestamp> getSeizuresEvents() {
		return seizuresEvents;
	}

	public void setSeizuresEvents(List<Timestamp> seizuresEvents) {
		this.seizuresEvents = seizuresEvents;
	}

	public List<Timestamp> getApneaEvents() {
		return apneaEvents;
	}

	public void setApneaEvents(List<Timestamp> apneaEvents) {
		this.apneaEvents = apneaEvents;
	}

	public List<Timestamp> getDisaturationEvents() {
		return disaturationEvents;
	}

	public void setDisaturationEvents(List<Timestamp> disaturationEvents) {
		this.disaturationEvents = disaturationEvents;
	}

	public List<List<Object>> getOxygenationIndexList() {
		return oxygenationIndexList;
	}

	public void setOxygenationIndexList(List<List<Object>> oxygenationIndexList) {
		this.oxygenationIndexList = oxygenationIndexList;
	}

	public List<List<Object>> getEnteralIntakeList() {
		return enteralIntakeList;
	}

	public void setEnteralIntakeList(List<List<Object>> enteralIntakeList) {
		this.enteralIntakeList = enteralIntakeList;
	}

	public List<List<Object>> getParenteralIntakeList() {
		return parenteralIntakeList;
	}

	public void setParenteralIntakeList(List<List<Object>> parenteralIntakeList) {
		this.parenteralIntakeList = parenteralIntakeList;
	}

	public List<List<Object>> getUrineOutputList() {
		return urineOutputList;
	}

	public void setUrineOutputList(List<List<Object>> urineOutputList) {
		this.urineOutputList = urineOutputList;
	}

	public List<List<Object>> getAbdominalGirthList() {
		return abdominalGirthList;
	}

	public void setAbdominalGirthList(List<List<Object>> abdominalGirthList) {
		this.abdominalGirthList = abdominalGirthList;
	}

	public List<List<Object>> getNbpSObject() {
		return nbpSObject;
	}

	public void setNbpSObject(List<List<Object>> nbpSObject) {
		this.nbpSObject = nbpSObject;
	}

	public List<List<Object>> getNbpDObject() {
		return nbpDObject;
	}

	public void setNbpDObject(List<List<Object>> nbpDObject) {
		this.nbpDObject = nbpDObject;
	}

	public List<List<Object>> getNbpMObject() {
		return nbpMObject;
	}

	public void setNbpMObject(List<List<Object>> nbpMObject) {
		this.nbpMObject = nbpMObject;
	}

	public List<List<Object>> getIbpSObject() {
		return ibpSObject;
	}

	public void setIbpSObject(List<List<Object>> ibpSObject) {
		this.ibpSObject = ibpSObject;
	}

	public List<List<Object>> getIbpDObject() {
		return ibpDObject;
	}

	public void setIbpDObject(List<List<Object>> ibpDObject) {
		this.ibpDObject = ibpDObject;
	}

	public List<List<Object>> getIbpMObject() {
		return ibpMObject;
	}

	public void setIbpMObject(List<List<Object>> ibpMObject) {
		this.ibpMObject = ibpMObject;
	}

	public List<List<Object>> getCentralTempObject() {
		return centralTempObject;
	}

	public void setCentralTempObject(List<List<Object>> centralTempObject) {
		this.centralTempObject = centralTempObject;
	}

	public List<List<Object>> getPeripheralTempObject() {
		return peripheralTempObject;
	}

	public void setPeripheralTempObject(List<List<Object>> peripheralTempObject) {
		this.peripheralTempObject = peripheralTempObject;
	}

	public List<List<Object>> getHumidityObject() {
		return humidityObject;
	}

	public void setHumidityObject(List<List<Object>> humidityObject) {
		this.humidityObject = humidityObject;
	}

	public DeviceGraphAxisJson getcTemp() {
		return cTemp;
	}

	public void setcTemp(DeviceGraphAxisJson cTemp) {
		this.cTemp = cTemp;
	}

	public DeviceGraphAxisJson getpTemp() {
		return pTemp;
	}

	public void setpTemp(DeviceGraphAxisJson pTemp) {
		this.pTemp = pTemp;
	}

	public DeviceGraphAxisJson getHumidity() {
		return humidity;
	}

	public void setHumidity(DeviceGraphAxisJson humidity) {
		this.humidity = humidity;
	}

	public DeviceGraphAxisJson getHDP() {
		return HDP;
	}

	public void setHDP(DeviceGraphAxisJson hDP) {
		HDP = hDP;
	}

	public List<List<Object>> getHDPObject() {
		return HDPObject;
	}

	public void setHDPObject(List<List<Object>> hDPObject) {
		HDPObject = hDPObject;
	}

	public DeviceGraphAxisJson getEvent() {
		return event;
	}

	public void setEvent(DeviceGraphAxisJson event) {
		this.event = event;
	}

	public List<List<Object>> getEventObject() {
		return eventObject;
	}

	public void setEventObject(List<List<Object>> eventObject) {
		this.eventObject = eventObject;
	}

	public List<List<Object>> getnSofaObject() {
		return nSofaObject;
	}

	public void setnSofaObject(List<List<Object>> nSofaObject) {
		this.nSofaObject = nSofaObject;
	}
}
