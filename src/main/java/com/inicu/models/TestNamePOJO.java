package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.TestItemResult;

public class TestNamePOJO {
	
	String testName;
	String unit;
	List<TestItemResult> itemResults;
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public List<TestItemResult> getItemResults() {
		return itemResults;
	}
	public void setItemResults(List<TestItemResult> itemResults) {
		this.itemResults = itemResults;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
