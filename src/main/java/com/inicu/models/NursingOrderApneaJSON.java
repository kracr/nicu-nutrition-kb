package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingorderApnea;
import com.inicu.postgres.entities.SaRespApnea;

public class NursingOrderApneaJSON {
	List<InvestigationOrdered> todayInvestigationList;
	List<InvestigationOrdered> pastInvestigationList;
	
	NursingorderApnea nursingOrderApnea;
	
	SaRespApnea recentSaRespApneaObj;
	List<SaRespApnea> pastSaRespApneaList;
	
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
	public NursingorderApnea getNursingOrderApnea() {
		return nursingOrderApnea;
	}
	public void setNursingOrderApnea(NursingorderApnea nursingOrderApnea) {
		this.nursingOrderApnea = nursingOrderApnea;
	}
	public SaRespApnea getRecentSaRespApneaObj() {
		return recentSaRespApneaObj;
	}
	public void setRecentSaRespApneaObj(SaRespApnea recentSaRespApneaObj) {
		this.recentSaRespApneaObj = recentSaRespApneaObj;
	}
	public List<SaRespApnea> getPastSaRespApneaList() {
		return pastSaRespApneaList;
	}
	public void setPastSaRespApneaList(List<SaRespApnea> pastSaRespApneaList) {
		this.pastSaRespApneaList = pastSaRespApneaList;
	}
	
	
}
