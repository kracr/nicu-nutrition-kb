package com.inicu.models;
/** 
 * pojo object to send the response message to the ui end of the called web service.
 * @author iNICU 
 *
 */
public class ResponseMessageObject {

	private String type;
	private String message;
	private boolean status;
	private int status_code;
	private Object data;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
