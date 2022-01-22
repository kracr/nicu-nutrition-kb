package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.TestItemResult;

public class LabOrdersSentReport {
	private InvestigationOrdered investigationOrder;
	private List<TestItemResult> results;
	public InvestigationOrdered getInvestigationOrder() {
		return investigationOrder;
	}
	public void setInvestigationOrder(InvestigationOrdered investigationOrder) {
		this.investigationOrder = investigationOrder;
	}
	public List<TestItemResult> getResults() {
		return results;
	}
	public void setResults(List<TestItemResult> results) {
		this.results = results;
	}
	
}
