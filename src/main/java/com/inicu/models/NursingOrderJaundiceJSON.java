package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.NursingOrderJaundice;

public class NursingOrderJaundiceJSON {

	List<InvestigationOrdered> todayInvestigationList;

	NursingOrderJaundice recentNursingOrderJaundiceObj;

	List<InvestigationOrdered> pastInvestigationList;
	List<NursingOrderJaundice> pastJaundiceOrderList;

	public List<InvestigationOrdered> getTodayInvestigationList() {
		return todayInvestigationList;
	}

	public void setTodayInvestigationList(List<InvestigationOrdered> todayInvestigationList) {
		this.todayInvestigationList = todayInvestigationList;
	}

	public NursingOrderJaundice getRecentNursingOrderJaundiceObj() {
		return recentNursingOrderJaundiceObj;
	}

	public void setRecentNursingOrderJaundiceObj(NursingOrderJaundice recentNursingOrderJaundiceObj) {
		this.recentNursingOrderJaundiceObj = recentNursingOrderJaundiceObj;
	}

	public List<InvestigationOrdered> getPastInvestigationList() {
		return pastInvestigationList;
	}

	public void setPastInvestigationList(List<InvestigationOrdered> pastInvestigationList) {
		this.pastInvestigationList = pastInvestigationList;
	}

	public List<NursingOrderJaundice> getPastJaundiceOrderList() {
		return pastJaundiceOrderList;
	}

	public void setPastJaundiceOrderList(List<NursingOrderJaundice> pastJaundiceOrderList) {
		this.pastJaundiceOrderList = pastJaundiceOrderList;
	}

	@Override
	public String toString() {
		return "NursingOrderJaundiceJSON [todayInvestigationList=" + todayInvestigationList
				+ ", recentNursingOrderJaundiceObj=" + recentNursingOrderJaundiceObj + ", pastInvestigationList="
				+ pastInvestigationList + ", pastJaundiceOrderList=" + pastJaundiceOrderList + "]";
	}

}
