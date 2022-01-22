package com.inicu.models;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TestResultObject {

	private Date resultdate;
	private Timestamp creationtime;
	private String testid;
	private Timestamp labReportDate;
	public List<TestItemObject> testItemObjectsList;
	
	public TestResultObject(Date resultdate, Timestamp creationtime, String testid)
	{
		this.resultdate = resultdate;
		this.creationtime = creationtime;
		this.testid = testid;
//		this.resultdate = null;
//		this.creationtime = null;
//		this.testid = null;
		this.testItemObjectsList = null;
	}
	public List<TestItemObject> getTestItemObjectsList() {
		return testItemObjectsList;
	}
	public void setTestItemObjectsList(List<TestItemObject> testItemObjectsList) {
		this.testItemObjectsList = testItemObjectsList;
	}
	public Date getResultdate() {
		return resultdate;
	}
	public void setResultdate(Date resultdate) {
		this.resultdate = resultdate;
	}
	public Timestamp getCreationtime() {
		return creationtime;
	}
	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}
	public String getTestid() {
		return testid;
	}
	public void setTestid(String testid) {
		this.testid = testid;
	}

	public Timestamp getLabReportDate() {
		return labReportDate;
	}

	public void setLabReportDate(Timestamp labReportDate) {
		this.labReportDate = labReportDate;
	}
}
