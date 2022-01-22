package com.inicu.models;

import java.util.HashMap;
import java.util.List;

public class MiscDropDownsJSON {
	
	List<KeyValueObj> orderInvestigation;
	
	List<String> hours;
	
	List<String> minutes;
	
	HashMap<Object, List<RefTestslist>> testsList;

	public List<KeyValueObj> getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(List<KeyValueObj> orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<String> getMinutes() {
		return minutes;
	}

	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}

	public HashMap<Object, List<RefTestslist>> getTestsList() {
		return testsList;
	}

	public void setTestsList(HashMap<Object, List<RefTestslist>> testsList) {
		this.testsList = testsList;
	} 
	
	


}
