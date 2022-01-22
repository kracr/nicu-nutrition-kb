package com.inicu.models;

import com.inicu.postgres.entities.LocalConfigurations;

import java.util.HashMap;
import java.util.List;

public class LoginPOJO {
	
	HashMap<String, String> reference;
	List<String> branchNameList;
	List<LocalConfigurations> configurationsList;
	public HashMap<String, String> getReference() {
		return reference;
	}
	public void setReference(HashMap<String, String> reference) {
		this.reference = reference;
	}
	public List<String> getBranchNameList() {
		return branchNameList;
	}
	public void setBranchNameList(List<String> branchNameList) {
		this.branchNameList = branchNameList;
	}

	public List<LocalConfigurations> getConfigurationsList() {
		return configurationsList;
	}

	public void setConfigurationsList(List<LocalConfigurations> configurationsList) {
		this.configurationsList = configurationsList;
	}
}
