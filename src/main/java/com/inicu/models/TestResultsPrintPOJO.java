package com.inicu.models;

import java.util.List;

public class TestResultsPrintPOJO {
	
	List<TestResultsViewPOJO> resultsTest;
	String departmentname;
	public List<TestResultsViewPOJO> getResultsTest() {
		return resultsTest;
	}
	public void setResultsTest(List<TestResultsViewPOJO> resultsTest) {
		this.resultsTest = resultsTest;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

}
