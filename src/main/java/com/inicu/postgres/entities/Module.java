package com.inicu.postgres.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity	
@Table(name="module")
public class Module {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="moduleid", nullable=false , columnDefinition="int8")
	private BigInteger moduleId ;
	
	@Column(name="description")
	String description;
	
	@Column(name="module_name")
	String moduleName;
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public BigInteger getModuleId() {
		return moduleId;
	}

	public void setModuleId(BigInteger moduleId) {
		this.moduleId = moduleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
