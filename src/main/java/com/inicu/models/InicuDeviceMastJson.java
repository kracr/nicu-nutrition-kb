package com.inicu.models;

import java.util.List;

public class InicuDeviceMastJson {
	List<InicuBoxJson> listOfBoxes;
	ResponseMessageObject response = new ResponseMessageObject();

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public List<InicuBoxJson> getListOfBoxes() {
		return listOfBoxes;
	}

	public void setListOfBoxes(List<InicuBoxJson> listOfBoxes) {
		this.listOfBoxes = listOfBoxes;
	}
}
