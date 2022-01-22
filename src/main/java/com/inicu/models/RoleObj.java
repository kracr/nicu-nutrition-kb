package com.inicu.models;

import java.util.HashMap;

public class RoleObj {

	private String roleName;
	private HashMap<String,Boolean> status;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public HashMap<String, Boolean> getStatus() {
		return status;
	}
	public void setStatus(HashMap<String, Boolean> status) {
		this.status = status;
	}
}
