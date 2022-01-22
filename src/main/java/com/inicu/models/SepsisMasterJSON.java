package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.SaSepsi;

public class SepsisMasterJSON {

	SaSepsi sepsis;
	
	List<SaSepsi> listSepsis;
	
	ResponseMessageObject response;
	
	SepsisDropDowns dropDown;
	
	String userId = "";
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public SepsisDropDowns getDropDown() {
		return dropDown;
	}

	public void setDropDown(SepsisDropDowns dropDown) {
		this.dropDown = dropDown;
	}

	public SaSepsi getSepsis() {
		return sepsis;
	}

	public void setSepsis(SaSepsi sepsis) {
		this.sepsis = sepsis;
	}

	public List<SaSepsi> getListSepsis() {
		return listSepsis;
	}

	public void setListSepsis(List<SaSepsi> listSepsis) {
		this.listSepsis = listSepsis;
	}
	
	
}
