package com.inicu.models;

import java.io.Serializable;
import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingOrderAssesment;

public class AssessmentNursingOrderPOJO implements Serializable {

	List<InvestigationOrdered> todayInvestigationList;

	List<NursingOrderAssesment> pendingNursingOrderList;

	List<InvestigationOrdered> pastInvestigationList;

	List<NursingOrderAssesment> pastNursingOrderList;

	String nursingComments = "";

	public List<InvestigationOrdered> getTodayInvestigationList() {
		return todayInvestigationList;
	}

	public void setTodayInvestigationList(List<InvestigationOrdered> todayInvestigationList) {
		this.todayInvestigationList = todayInvestigationList;
	}

	public List<NursingOrderAssesment> getPendingNursingOrderList() {
		return pendingNursingOrderList;
	}

	public void setPendingNursingOrderList(List<NursingOrderAssesment> pendingNursingOrderList) {
		this.pendingNursingOrderList = pendingNursingOrderList;
	}

	public List<InvestigationOrdered> getPastInvestigationList() {
		return pastInvestigationList;
	}

	public void setPastInvestigationList(List<InvestigationOrdered> pastInvestigationList) {
		this.pastInvestigationList = pastInvestigationList;
	}

	public List<NursingOrderAssesment> getPastNursingOrderList() {
		return pastNursingOrderList;
	}

	public void setPastNursingOrderList(List<NursingOrderAssesment> pastNursingOrderList) {
		this.pastNursingOrderList = pastNursingOrderList;
	}

	public String getNursingComments() {
		return nursingComments;
	}

	public void setNursingComments(String nursingComments) {
		this.nursingComments = nursingComments;
	}

	@Override
	public String toString() {
		return "AssessmentNursingOrderPOJO [todayInvestigationList=" + todayInvestigationList
				+ ", pendingNursingOrderList=" + pendingNursingOrderList + ", pastInvestigationList="
				+ pastInvestigationList + ", pastNursingOrderList=" + pastNursingOrderList + ", nursingComments="
				+ nursingComments + "]";
	}

}
