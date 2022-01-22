package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rolemanagement_final database table.
 * 
 */
@Entity
@Table(name="rolemanagement_final")
@NamedQuery(name="RolemanagementFinal.findAll", query="SELECT r FROM RolemanagementFinal r")
public class RolemanagementFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="Administrator")
	private String administrator;

	@Column(name="Front Desk")
	private String front_Desk;

	@Column(name="JrDoc")
	private String jrDoc;

	@Id
	@Column(name="module_desc")
	private String moduleDesc;

	@Column(name="Nurse")
	private String nurse;

	@Column(name="SrDoc")
	private String srDoc;

	@Column(name="Super User")
	private String super_User;

	public RolemanagementFinal() {
	}

	public String getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getFront_Desk() {
		return this.front_Desk;
	}

	public void setFront_Desk(String front_Desk) {
		this.front_Desk = front_Desk;
	}

	public String getJrDoc() {
		return this.jrDoc;
	}

	public void setJrDoc(String jrDoc) {
		this.jrDoc = jrDoc;
	}

	public String getModuleDesc() {
		return this.moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getNurse() {
		return this.nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getSrDoc() {
		return this.srDoc;
	}

	public void setSrDoc(String srDoc) {
		this.srDoc = srDoc;
	}

	public String getSuper_User() {
		return this.super_User;
	}

	public void setSuper_User(String super_User) {
		this.super_User = super_User;
	}

}