package com.inicu.models;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.InvestigationPannel;
import com.inicu.postgres.entities.TestslistMapping;


public class TestsListJSON {
	
	HashMap<Object, List<MappedListPOJO>> inicuList;
	private List<RefTestslist> vendorLists; 
	private List<InvestigationPannel> investigationPannelList;
	private String message;
	private String type;
	

	public HashMap<Object, List<MappedListPOJO>> getInicuList() {
		return inicuList;
	}
	public void setInicuList(HashMap<Object, List<MappedListPOJO>> inicuList) {
		this.inicuList = inicuList;
	}
	public List<RefTestslist> getVendorLists() {
		return vendorLists;
	}
	public void setVendorLists(List<RefTestslist> vendorLists) {
		this.vendorLists = vendorLists;
	}
	public List<InvestigationPannel> getInvestigationPannelList() {
		return investigationPannelList;
	}
	public void setInvestigationPannelList(List<InvestigationPannel> investigationPannelList) {
		this.investigationPannelList = investigationPannelList;
	}
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
	
}
