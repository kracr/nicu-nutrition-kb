package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NursingMedication;
import com.inicu.postgres.entities.RefHospitalbranchname;
import com.inicu.postgres.utility.BasicConstants;

/**
 * 
 * @author iNICU 
 *
 */
public class DeletedNursingMedicationModel {
	private String type;
	private String message;
	private Boolean status;
	private Object returnedObject;
	private List<NursingMedication> deletedList;
//	private List<RoleObj> roleStatus;
//	private String access_token;
//	private List<RefHospitalbranchname> branchNameWithTypeList;
//	private String hospitalType;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public DeletedNursingMedicationModel() {
		super();
		this.type = BasicConstants.MESSAGE_FAILURE;
		this.message = "Operation Failed";
		this.returnedObject =null;
//		this.roleStatus = null;
//		this.access_token=null;
//		this.branchNameWithTypeList=null;
//		this.hospitalType=null;
		this.status=true;
	}

//	public String getHospitalType() {
//		return hospitalType;
//	}
//
//	public void setHospitalType(String hospitalType) {
//		this.hospitalType = hospitalType;
//	}
//
//	public List<RefHospitalbranchname> getBranchNameWithTypeList() {
//		return branchNameWithTypeList;
//	}
//
//	public void setBranchNameWithTypeList(List<RefHospitalbranchname> branchNameWithTypeList) {
//		this.branchNameWithTypeList = branchNameWithTypeList;
//	}
//
//	public String getAccess_token() {
//		return access_token;
//	}
//
//	public void setAccess_token(String access_token) {
//		this.access_token = access_token;
//	}

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
	public Object getReturnedObject() {
		return returnedObject;
	}
	public void setReturnedObject(Object returnedObject) {
		this.returnedObject = returnedObject;
	}
//	public List<RoleObj> getRoleStatus() {
//		return roleStatus;
//	}
//	public void setRoleStatus(List<RoleObj> roleStatus) {
//		this.roleStatus = roleStatus;
//	}

	public List<NursingMedication> getDeletedList() {
		return deletedList;
	}

	public void setDeletedList(List<NursingMedication> nursingList) {
		this.deletedList = nursingList;
	}
	
	

}
