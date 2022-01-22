package com.inicu.postgres.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="role_manager")
public class RoleManager {


	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rolemanagerid", nullable=false , columnDefinition="bigserial")
	BigInteger roleManId;

	@Column(name="moduleid", columnDefinition="int8")
	BigInteger moduleId;

	@Column(name="roleid", columnDefinition="int8")
	BigInteger roleId;
	
	@Column(name="statusid", columnDefinition="int8")
	BigInteger statusId;
	
	@Transient
	String prediction;
	
	@Transient
	Integer predictionDays;
	
	@Transient
	Float hdpResult;

	BigInteger getRoleManId() {
		return roleManId;
	}

	public void setRoleManId(BigInteger roleManId) {
		this.roleManId = roleManId;
	}

	public BigInteger getModuleId() {
		return moduleId;
	}

	public void setModuleId(BigInteger moduleId) {
		this.moduleId = moduleId;
	}

	public BigInteger getRoleId() {
		return roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public Integer getPredictionDays() {
		return predictionDays;
	}

	public void setPredictionDays(Integer predictionDays) {
		this.predictionDays = predictionDays;
	}

	public Float getHdpResult() {
		return hdpResult;
	}

	public void setHdpResult(Float hdpResult) {
		this.hdpResult = hdpResult;
	}
	
}
