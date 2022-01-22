package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the outborn_medicine database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "master_address")
@NamedQuery(name = "MasterAddress.findAll", query = "SELECT s FROM MasterAddress s")
public class MasterAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long master_address_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String state;

	private String district;

	private String taluka;

	private String city;

	private String pin_code;

	public Long getMaster_address_id() {
		return master_address_id;
	}

	public void setMaster_address_id(Long master_address_id) {
		this.master_address_id = master_address_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTaluka() {
		return taluka;
	}

	public void setTaluka(String taluka) {
		this.taluka = taluka;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin_code() {
		return pin_code;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}

	@Override
	public String toString() {
		return "MasterAddress [master_address_id=" + master_address_id + ", creationtime=" + creationtime + ", state="
				+ state + ", district=" + district + ", taluka=" + taluka + ", city=" + city + ", pin_code=" + pin_code
				+ "]";
	}

}
