package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the nursing_output database table.
 * 
 */
@Entity
@Table(name="nursing_output")
@NamedQuery(name="NursingOutput.findAll", query="SELECT n FROM NursingOutput n")
public class NursingOutput implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nn_outputid")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nnOutputid;

	@Column(name="aspirate_quantity")
	private String aspirateQuantity;

	@Column(name="aspirate_type")
	private String aspirateType;

	@Column(name="blood_letting")
	private String bloodLetting;

	@Column(name="bowel_color")
	private String bowelColor;

	@Column(name="bowel_status",columnDefinition="bool")
	private Boolean bowelStatus;

	@Column(name="bowel_type")
	private String bowelType;

	private Timestamp creationtime;

	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name="nn_output_time")
	private String nnOutputTime;

	@Column(name="total_uo")
	private String totalUo;

	private String uhid;

	@Column(name="urine_mlkg")
	private String urineMlkg;

	@Column(name="urine_mls")
	private String urineMls;

	public NursingOutput() {
	}

	public Long getNnOutputid() {
		return this.nnOutputid;
	}

	public void setNnOutputid(Long nnOutputid) {
		this.nnOutputid = nnOutputid;
	}

	public String getAspirateQuantity() {
		return this.aspirateQuantity;
	}

	public void setAspirateQuantity(String aspirateQuantity) {
		this.aspirateQuantity = aspirateQuantity;
	}

	public String getAspirateType() {
		return this.aspirateType;
	}

	public void setAspirateType(String aspirateType) {
		this.aspirateType = aspirateType;
	}

	public String getBloodLetting() {
		return this.bloodLetting;
	}

	public void setBloodLetting(String bloodLetting) {
		this.bloodLetting = bloodLetting;
	}

	public String getBowelColor() {
		return this.bowelColor;
	}

	public void setBowelColor(String bowelColor) {
		this.bowelColor = bowelColor;
	}

	public Boolean getBowelStatus() {
		return this.bowelStatus;
	}

	public void setBowelStatus(Boolean bowelStatus) {
		this.bowelStatus = bowelStatus;
	}

	public String getBowelType() {
		return this.bowelType;
	}

	public void setBowelType(String bowelType) {
		this.bowelType = bowelType;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnOutputTime() {
		return this.nnOutputTime;
	}

	public void setNnOutputTime(String nnOutputTime) {
		this.nnOutputTime = nnOutputTime;
	}

	public String getTotalUo() {
		return this.totalUo;
	}

	public void setTotalUo(String totalUo) {
		this.totalUo = totalUo;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getUrineMlkg() {
		return this.urineMlkg;
	}

	public void setUrineMlkg(String urineMlkg) {
		this.urineMlkg = urineMlkg;
	}

	public String getUrineMls() {
		return this.urineMls;
	}

	public void setUrineMls(String urineMls) {
		this.urineMls = urineMls;
	}

}