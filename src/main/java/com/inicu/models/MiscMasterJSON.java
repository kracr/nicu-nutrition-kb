package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.SaMisc;

public class MiscMasterJSON {

	SaMisc misc;
	
	List<SaMisc> listMisc;
	
	ResponseMessageObject response;
	
	String userId="";
	
	MiscDropDowns dropDowns;

	public SaMisc getMisc() {
		return misc;
	}

	public void setMisc(SaMisc misc) {
		this.misc = misc;
	}

	public List<SaMisc> getListMisc() {
		return listMisc;
	}

	public void setListMisc(List<SaMisc> listMisc) {
		this.listMisc = listMisc;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public MiscDropDowns getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(MiscDropDowns dropDowns) {
		this.dropDowns = dropDowns;
	}
	
	
}
