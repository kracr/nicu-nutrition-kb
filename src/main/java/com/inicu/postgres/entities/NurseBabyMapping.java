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

@Entity
@Table(name = "nurse_baby_mapping")
@NamedQuery(name = "NurseBabyMapping.findAll", query = "Select s from NurseBabyMapping s")
public class NurseBabyMapping implements Serializable {

	private static final long serialVersionID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nurse_baby_mapping_id")
	private Long nurseBabyMappingId;

	@Column(name = "nurse_id")
	private Long nurseId;

	private String nurse;

	@Column(name = "nurse_username")
	private String nurseUsername;

	private String uhid;

	@Column(name = "baby_name")
	private String babyName;

	@Column(name = "shift_type")
	private String shiftType;

	@Column(name = "date_of_shift")
	private Timestamp dateOfShift;
	
	@Column(name = "baby_number")
	private String babyNumber;
	
	@Column(name = "baby_type")
	private String babyType;
	
	@Column(name = "branch_name")
	private String branchName;

	public Long getNurseBabyMappingId() {
		return nurseBabyMappingId;
	}

	public void setNurseBabyMappingId(Long nurseBabyMappingId) {
		this.nurseBabyMappingId = nurseBabyMappingId;
	}

	public Long getNurseId() {
		return nurseId;
	}

	public void setNurseId(Long nurseId) {
		this.nurseId = nurseId;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getNurseUsername() {
		return nurseUsername;
	}

	public void setNurseUsername(String nurseUsername) {
		this.nurseUsername = nurseUsername;
	}

	
	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	
	public String getShiftType() {
		return shiftType;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}

	public Timestamp getDateOfShift() {
		return dateOfShift;
	}

	public void setDateOfShift(Timestamp dateOfShift) {
		this.dateOfShift = dateOfShift;
	}

	public String getBabyNumber() {
		return babyNumber;
	}

	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}

	public String getBabyType() {
		return babyType;
	}

	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public static long getSerialversionid() {
		return serialVersionID;
	}

}
