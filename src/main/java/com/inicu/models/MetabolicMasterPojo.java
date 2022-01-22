package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenMetabolic;

public class MetabolicMasterPojo {

	ScreenMetabolic currentObj;

	List<KeyValueObj> orderInvestigation;

	List<RefTestslist> testsList;

	List<ScreenMetabolic> pastMetabolicList;

	public MetabolicMasterPojo() {
		super();
		this.currentObj = new ScreenMetabolic();
	}

	public ScreenMetabolic getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(ScreenMetabolic currentObj) {
		this.currentObj = currentObj;
	}

	public List<KeyValueObj> getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(List<KeyValueObj> orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public List<RefTestslist> getTestsList() {
		return testsList;
	}

	public void setTestsList(List<RefTestslist> testsList) {
		this.testsList = testsList;
	}

	public List<ScreenMetabolic> getPastMetabolicList() {
		return pastMetabolicList;
	}

	public void setPastMetabolicList(List<ScreenMetabolic> pastMetabolicList) {
		this.pastMetabolicList = pastMetabolicList;
	}

	@Override
	public String toString() {
		return "MetabolicMasterPojo [currentObj=" + currentObj + ", orderInvestigation=" + orderInvestigation
				+ ", testsList=" + testsList + ", pastMetabolicList=" + pastMetabolicList + "]";
	}

}
