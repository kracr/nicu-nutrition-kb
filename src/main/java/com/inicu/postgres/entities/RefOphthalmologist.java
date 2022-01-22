package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ref_ophthalmologist database table.
 * 
 */
@Entity
@Table(name = "ref_ophthalmologist")
@NamedQuery(name = "RefOphthalmologist.findAll", query = "SELECT r FROM RefBed r")
public class RefOphthalmologist implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ophthalmologist_id")
	private String ophthalmologistId;
	
	@Column(name = "ophthalmologist_name")
	private String ophthalmologistName;

	public String getOphthalmologistId() {
		return ophthalmologistId;
	}

	public void setOphthalmologistId(String ophthalmologistId) {
		this.ophthalmologistId = ophthalmologistId;
	}

	public String getOphthalmologistName() {
		return ophthalmologistName;
	}

	public void setOphthalmologistName(String ophthalmologistName) {
		this.ophthalmologistName = ophthalmologistName;
	}

	@Override
	public String toString() {
		return "RefOphthalmologist [ophthalmologistId=" + ophthalmologistId + ", ophthalmologistName="
				+ ophthalmologistName + "]";
	}

}
