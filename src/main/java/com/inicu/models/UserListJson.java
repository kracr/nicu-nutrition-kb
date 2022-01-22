package com.inicu.models;

import com.inicu.postgres.entities.RefHospitalbranchname;

import java.util.List;



public class UserListJson {
	List<String> reportingDoctor;
	List<UserListPojo> userList;
	List<String> branchNameList;
	List<RefHospitalbranchname> branchNameWithTypeList;
	List<String> roleList; 

	public List<String> getReportingDoctor() {
		return reportingDoctor;
	}
	public void setReportingDoctor(List<String> reportingDoctor) {
		this.reportingDoctor = reportingDoctor;
	}
	public List<UserListPojo> getUserList() {
		return userList;
	}
	public void setUserList(List<UserListPojo> userList) {
		this.userList = userList;
	}


	public List<String> getBranchNameList() {
		return branchNameList;
	}

	public void setBranchNameList(List<String> branchNameList) {
		this.branchNameList = branchNameList;
	}

	public List<RefHospitalbranchname> getBranchNameWithTypeList() {
		return branchNameWithTypeList;
	}

	public void setBranchNameWithTypeList(List<RefHospitalbranchname> branchNameWithTypeList) {
		this.branchNameWithTypeList = branchNameWithTypeList;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
}
