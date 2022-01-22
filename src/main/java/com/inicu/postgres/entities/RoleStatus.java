package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_status database table.
 * 
 */
@Entity
@Table(name="role_status")
@NamedQuery(name="RoleStatus.findAll", query="SELECT r FROM RoleStatus r")
public class RoleStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long statusid;

	private String description;

	@Column(name="status_name")
	private String statusName;

	public RoleStatus() {
	}

	public Long getStatusid() {
		return this.statusid;
	}

	public void setStatusid(Long statusid) {
		this.statusid = statusid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}