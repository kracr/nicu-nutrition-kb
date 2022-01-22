package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingorderRd;
import com.inicu.postgres.entities.NursingorderRdsMedicine;
import com.inicu.postgres.entities.SaRespsystem;

public class NursingOrderRdJSON {
	List<InvestigationOrdered> todayInvestigationList;
	List<InvestigationOrdered> pastInvestigationList;
	
	NursingorderRd nursingOrderObj;

	SaRespsystem recentSaRespsystemObj;
	List<SaRespsystem> pastSaRespsystemList;
	
	//List<NursingorderRdsMedicine> rdsMedicineList;
	
	List<RdsMedicineJSON> rdsMedicineList;

	public List<InvestigationOrdered> getTodayInvestigationList() {
		return todayInvestigationList;
	}

	public void setTodayInvestigationList(List<InvestigationOrdered> todayInvestigationList) {
		this.todayInvestigationList = todayInvestigationList;
	}

	public List<InvestigationOrdered> getPastInvestigationList() {
		return pastInvestigationList;
	}

	public void setPastInvestigationList(List<InvestigationOrdered> pastInvestigationList) {
		this.pastInvestigationList = pastInvestigationList;
	}
	
	public NursingorderRd getNursingOrderObj() {
		return nursingOrderObj;
	}

	public void setNursingOrderObj(NursingorderRd nursingOrderObj) {
		this.nursingOrderObj = nursingOrderObj;
	}

	
	public SaRespsystem getRecentSaRespsystemObj() {
		return recentSaRespsystemObj;
	}

	public void setRecentSaRespsystemObj(SaRespsystem recentSaRespsystemObj) {
		this.recentSaRespsystemObj = recentSaRespsystemObj;
	}

	public List<SaRespsystem> getPastSaRespsystemList() {
		return pastSaRespsystemList;
	}

	public void setPastSaRespsystemList(List<SaRespsystem> pastSaRespsystemList) {
		this.pastSaRespsystemList = pastSaRespsystemList;
	}
	
	

	public List<RdsMedicineJSON> getRdsMedicineList() {
		return rdsMedicineList;
	}

	public void setRdsMedicineList(List<RdsMedicineJSON> rdsMedicineList) {
		this.rdsMedicineList = rdsMedicineList;
	}

	@Override
	public String toString() {
		return "NursingOrderRdJSON [todayInvestigationList=" + todayInvestigationList
				+ ", recentNursingOrderRdObj=" + recentSaRespsystemObj + ", pastInvestigationList="
				+ pastInvestigationList + ", pastRdOrderList=" + pastSaRespsystemList + "]";
	}

}
