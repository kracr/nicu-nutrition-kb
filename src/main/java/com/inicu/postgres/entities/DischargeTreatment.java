package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_treatment database table.
 * 
 */
@Entity
@Table(name="discharge_treatment")
@NamedQuery(name="DischargeTreatment.findAll", query="SELECT d FROM DischargeTreatment d")
public class DischargeTreatment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long treatmentid;

	

	@Column(columnDefinition="bool")
	private Boolean injvitamink;

	@Column(columnDefinition="bool")
	private Boolean irondrops;

	@Column(columnDefinition="bool")
	private Boolean ivaminophylline;

	@Column(columnDefinition="bool")
	private Boolean ivantiniotics;

	@Column(columnDefinition="bool")
	private Boolean ivcalgluconate;

	@Column(columnDefinition="bool")
	private Boolean ivfluids;

	@Column(columnDefinition="bool")
	private Boolean ivlorazepam;

	@Column(columnDefinition="bool")
	private Boolean ivphenobarbitone;

	@Column(columnDefinition="bool")
	private Boolean ivphenytoin;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(columnDefinition="bool")
	private Boolean multivitamindrops;

	@Column(columnDefinition="bool")
	private Boolean sypcalcium;

	private String uhid;

	public DischargeTreatment() {
	}

	public Long getTreatmentid() {
		return this.treatmentid;
	}

	public void setTreatmentid(Long treatmentid) {
		this.treatmentid = treatmentid;
	}

	
	public Boolean getInjvitamink() {
		return this.injvitamink;
	}

	public void setInjvitamink(Boolean injvitamink) {
		this.injvitamink = injvitamink;
	}

	public Boolean getIrondrops() {
		return this.irondrops;
	}

	public void setIrondrops(Boolean irondrops) {
		this.irondrops = irondrops;
	}

	public Boolean getIvaminophylline() {
		return this.ivaminophylline;
	}

	public void setIvaminophylline(Boolean ivaminophylline) {
		this.ivaminophylline = ivaminophylline;
	}

	public Boolean getIvantiniotics() {
		return this.ivantiniotics;
	}

	public void setIvantiniotics(Boolean ivantiniotics) {
		this.ivantiniotics = ivantiniotics;
	}

	public Boolean getIvcalgluconate() {
		return this.ivcalgluconate;
	}

	public void setIvcalgluconate(Boolean ivcalgluconate) {
		this.ivcalgluconate = ivcalgluconate;
	}

	public Boolean getIvfluids() {
		return this.ivfluids;
	}

	public void setIvfluids(Boolean ivfluids) {
		this.ivfluids = ivfluids;
	}

	public Boolean getIvlorazepam() {
		return this.ivlorazepam;
	}

	public void setIvlorazepam(Boolean ivlorazepam) {
		this.ivlorazepam = ivlorazepam;
	}

	public Boolean getIvphenobarbitone() {
		return this.ivphenobarbitone;
	}

	public void setIvphenobarbitone(Boolean ivphenobarbitone) {
		this.ivphenobarbitone = ivphenobarbitone;
	}

	public Boolean getIvphenytoin() {
		return this.ivphenytoin;
	}

	public void setIvphenytoin(Boolean ivphenytoin) {
		this.ivphenytoin = ivphenytoin;
	}

	

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getMultivitamindrops() {
		return this.multivitamindrops;
	}

	public void setMultivitamindrops(Boolean multivitamindrops) {
		this.multivitamindrops = multivitamindrops;
	}

	public Boolean getSypcalcium() {
		return this.sypcalcium;
	}

	public void setSypcalcium(Boolean sypcalcium) {
		this.sypcalcium = sypcalcium;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}