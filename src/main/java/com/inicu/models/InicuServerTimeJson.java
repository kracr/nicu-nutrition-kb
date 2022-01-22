package com.inicu.models;

public class InicuServerTimeJson {
	String currentServerTime;
	int offset;
	ResponseMessageObject response = new ResponseMessageObject();

	public String getCurrentServerTime() {
		return currentServerTime;
	}

	public void setCurrentServerTime(String currentServerTime) {
		this.currentServerTime = currentServerTime;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}	
}
