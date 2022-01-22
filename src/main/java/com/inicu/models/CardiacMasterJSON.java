package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.SaCardiac;

public class CardiacMasterJSON {

	SaCardiac cardiac;
	
	List<SaCardiac> listofCardiac;
	
	ResponseMessageObject response;
	
	CardiacDropDowns dropdowns;
	
	String userId = "";
	
	public CardiacDropDowns getDropdowns() {
		return dropdowns;
	}
	public void setDropdowns(CardiacDropDowns dropdowns) {
		this.dropdowns = dropdowns;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public SaCardiac getCardiac() {
		return cardiac;
	}
	public void setCardiac(SaCardiac cardiac) {
		this.cardiac = cardiac;
	}
	public List<SaCardiac> getListofCardiac() {
		return listofCardiac;
	}
	public void setListofCardiac(List<SaCardiac> listofCardiac) {
		this.listofCardiac = listofCardiac;
	}
	public ResponseMessageObject getResponse() {
		return response;
	}
	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}	
}
