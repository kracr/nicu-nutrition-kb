package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.TestItemResult;
import com.inicu.postgres.entities.TestItemResultInicu;


//Required to enter the Lab test data entered by Gangaram
public class TestResultInputObject {
	
	
	private List<InvestigationOrdered> investigationIds;
	private List<TestItemResult> testitems;
	public List<InvestigationOrdered> getInvestigationIds() {
		return investigationIds;
	}
	public void setInvestigationIds(List<InvestigationOrdered> investigationIds) {
		this.investigationIds = investigationIds;
	}
	public List<TestItemResult> getTestitems() {
		return testitems;
	}
	public void setTestitems(List<TestItemResult> testitems) {
		this.testitems = testitems;
	}
	
	
	
	
	
	
	
	
}
