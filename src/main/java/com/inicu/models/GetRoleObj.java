package com.inicu.models;

import java.util.List;

public class GetRoleObj {
	
	private List<String> allRoles;
	private List<String> allModules;
	private List<ManageRoleObj> values;
	
	public List<ManageRoleObj> getValues() {
		return values;
	}
	public void setValues(List<ManageRoleObj> values) {
		this.values = values;
	}
	public List<String> getAllRoles() {
		return allRoles;
	}
	public void setAllRoles(List<String> allRoles) {
		this.allRoles = allRoles;
	}
	public List<String> getAllModules() {
		return allModules;
	}
	public void setAllModules(List<String> allModules) {
		this.allModules = allModules;
	}
	
	
}
