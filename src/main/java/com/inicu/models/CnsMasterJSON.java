package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.VwSaCn;

public class CnsMasterJSON {

	VwSaCn cns;
	List<VwSaCn> listOfCns;
	ResponseMessageObject response;
	CnsDropDowns dropDown;
	String userId = "";
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public CnsDropDowns getDropDown() {
		return dropDown;
	}
	public void setDropDown(CnsDropDowns dropDown) {
		this.dropDown = dropDown;
	}
	public VwSaCn getCns() {
		return cns;
	}
	public void setCns(VwSaCn cns) {
		this.cns = cns;
	}
	public List<VwSaCn> getListOfCns() {
		return listOfCns;
	}
	public void setListOfCns(List<VwSaCn> listOfCns) {
		this.listOfCns = listOfCns;
	}
	public ResponseMessageObject getResponse() {
		return response;
	}
	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
}
