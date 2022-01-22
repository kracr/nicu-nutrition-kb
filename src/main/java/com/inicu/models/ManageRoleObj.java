package com.inicu.models;

import java.util.List;

public class ManageRoleObj {

	private String moduleName;
	private List<RoleObj> listOfRole;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<RoleObj> getListOfRoleObj() {
		return listOfRole;
	}
	public void setListOfRoleObj(List<RoleObj> listOfRoleObj) {
		this.listOfRole = listOfRoleObj;
	}	
	
}
