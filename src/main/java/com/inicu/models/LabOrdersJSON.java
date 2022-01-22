package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.InvestigationOrdered;

public class LabOrdersJSON {
	
	String message;
	String type;
	HashMap<Object, List<InvestigationOrdered>> labOrders;
	List<LabOrdersSentPOJO> labOrdersSent;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public HashMap<Object, List<InvestigationOrdered>> getLabOrders() {
		return labOrders;
	}
	public void setLabOrders(HashMap<Object, List<InvestigationOrdered>> labOrders) {
		this.labOrders = labOrders;
	}
	public List<LabOrdersSentPOJO> getLabOrdersSent() {
		return labOrdersSent;
	}
	public void setLabOrdersSent(List<LabOrdersSentPOJO> labOrdersSent) {
		this.labOrdersSent = labOrdersSent;
	}
	
}
