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

import com.inicu.postgres.utility.BasicUtils;

@Entity
@Table(name = "user_shift_mapping")
@NamedQuery(name = "UserShiftMapping.findAll", query = "Select s from UserShiftMapping s")
public class UserShiftMapping implements Serializable{

	private static final long serialVersionID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shift_mapping_id")
	private Long shiftMappingId;

	@Column(name = "user_id")
	private Long userId;
	
	private String name;

	@Column(columnDefinition = "bool")
	private Boolean shift1;

	@Column(columnDefinition = "bool")
	private Boolean shift2;

	@Column(columnDefinition = "bool")
	private Boolean shift3;

	@Column(columnDefinition = "bool")
	private Boolean shift4;

	@Column(name = "date_of_shift")
	private Timestamp dateOfShift;
	
	@Column(name = "username")
	private String username;

	@Column(name ="user_type",columnDefinition = "int")
	private int userType;
	
	public UserShiftMapping() {}

	public UserShiftMapping(Object[] arr) {
		this.shiftMappingId =BasicUtils.toLongSafe(arr[0]);
		this.userId = BasicUtils.toLongSafe(arr[3]);
		this.shift1 = (Boolean) arr[4];
		this.shift2 = (Boolean) arr[5];
		this.shift3 = (Boolean) arr[6];
		this.shift4 = (Boolean) arr[7];
		this.dateOfShift = BasicUtils.toTimestampSafe(arr[8]);
		this.name = BasicUtils.toStringSafe(arr[9]);
		this.username = BasicUtils.toStringSafe(arr[10]);
	}
	public UserShiftMapping(Long userId,String name ,String username) {
		this.userId =  userId;
		this.name = name;
		this.username =username;
		this.shiftMappingId = null;
		this.shift1 = null;
		this.shift2 = null;
		this.shift3 = null;
		this.shift4 = null;
		this.dateOfShift = null;
	}

	public Long getNurseShiftMappingId() {
		return shiftMappingId;
	}

	public void setNurseShiftMappingId(Long nurseShiftMappingId) {
		this.shiftMappingId = nurseShiftMappingId;
	}

	public Boolean getShift1() {
		return shift1;
	}

	public void setShift1(Boolean shift1) {
		this.shift1 = shift1;
	}

	public Boolean getShift2() {
		return shift2;
	}

	public void setShift2(Boolean shift2) {
		this.shift2 = shift2;
	}

	public Boolean getShift3() {
		return shift3;
	}

	public void setShift3(Boolean shift3) {
		this.shift3 = shift3;
	}

	public Boolean getShift4() {
		return shift4;
	}

	public void setShift4(Boolean shift4) {
		this.shift4 = shift4;
	}

	public Timestamp getDateOfShift() {
		return dateOfShift;
	}

	public void setDateOfShift(Timestamp dateOfShift) {
		this.dateOfShift = dateOfShift;
	}

	public static long getSerialversionid() {
		return serialVersionID;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
