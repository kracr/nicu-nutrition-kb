package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_metabolic database table.
 * 
 */
@Entity
@Table(name="discharge_metabolic")
@NamedQuery(name="DischargeMetabolic.findAll", query="SELECT d FROM DischargeMetabolic d")
public class DischargeMetabolic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long jaundiceinicuid;

	@Column(columnDefinition="bool")
	private Boolean aminophylline;

	private String aminophyllineid;

	@Column(name="anemia_prematurity",columnDefinition="bool")
	private Boolean anemiaPrematurity;

	@Column(name="apnea_prematurity",columnDefinition="bool")
	private Boolean apneaPrematurity;

	@Column(name="baby_bloodgroup")
	private String babyBloodgroup;

	@Column(columnDefinition="bool")
	private Boolean caffeine;

	private String caffeineid;

	@Column(columnDefinition="bool")
	private Boolean cpap;

	@Column(columnDefinition="bool")
	private Boolean cpapid;

	

	private String dct;

	@Column(name="discharge_bilirubin")
	private String dischargeBilirubin;

	@Column(columnDefinition="bool")
	private Boolean exchangetrans;

	@Column(name="ffp_transfusion",columnDefinition="bool")
	private Boolean ffpTransfusion;

	private String g6pd;

	@Column(name="ivig_transfusion",columnDefinition="bool")
	private Boolean ivigTransfusion;

	@Column(name="max_bilirubin")
	private String maxBilirubin;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name="neonatal_jaundice",columnDefinition="bool")
	private Boolean neonatalJaundice;

	@Column(columnDefinition="bool")
	private String nooftransfusion;

	@Column(columnDefinition="bool")
	private Boolean phototherapy;

	private String phototherapyid;

	@Column(name="platelet_transfusion",columnDefinition="bool")
	private Boolean plateletTransfusion;

	@Column(name="prbc_transfusion",columnDefinition="bool")
	private Boolean prbcTransfusion;

	private String uhid;

	public DischargeMetabolic() {
	}

	
	
	public Long getJaundiceinicuid() {
		return jaundiceinicuid;
	}



	public void setJaundiceinicuid(Long jaundiceinicuid) {
		this.jaundiceinicuid = jaundiceinicuid;
	}



	public Boolean getAminophylline() {
		return this.aminophylline;
	}

	public void setAminophylline(Boolean aminophylline) {
		this.aminophylline = aminophylline;
	}

	public String getAminophyllineid() {
		return this.aminophyllineid;
	}

	public void setAminophyllineid(String aminophyllineid) {
		this.aminophyllineid = aminophyllineid;
	}

	public Boolean getAnemiaPrematurity() {
		return this.anemiaPrematurity;
	}

	public void setAnemiaPrematurity(Boolean anemiaPrematurity) {
		this.anemiaPrematurity = anemiaPrematurity;
	}

	public Boolean getApneaPrematurity() {
		return this.apneaPrematurity;
	}

	public void setApneaPrematurity(Boolean apneaPrematurity) {
		this.apneaPrematurity = apneaPrematurity;
	}

	public String getBabyBloodgroup() {
		return this.babyBloodgroup;
	}

	public void setBabyBloodgroup(String babyBloodgroup) {
		this.babyBloodgroup = babyBloodgroup;
	}

	public Boolean getCaffeine() {
		return this.caffeine;
	}

	public void setCaffeine(Boolean caffeine) {
		this.caffeine = caffeine;
	}

	public String getCaffeineid() {
		return this.caffeineid;
	}

	public void setCaffeineid(String caffeineid) {
		this.caffeineid = caffeineid;
	}

	public Boolean getCpap() {
		return this.cpap;
	}

	public void setCpap(Boolean cpap) {
		this.cpap = cpap;
	}

	public Boolean getCpapid() {
		return this.cpapid;
	}

	public void setCpapid(Boolean cpapid) {
		this.cpapid = cpapid;
	}

	

	public String getDct() {
		return this.dct;
	}

	public void setDct(String dct) {
		this.dct = dct;
	}

	public String getDischargeBilirubin() {
		return this.dischargeBilirubin;
	}

	public void setDischargeBilirubin(String dischargeBilirubin) {
		this.dischargeBilirubin = dischargeBilirubin;
	}

	public Boolean getExchangetrans() {
		return this.exchangetrans;
	}

	public void setExchangetrans(Boolean exchangetrans) {
		this.exchangetrans = exchangetrans;
	}

	public Boolean getFfpTransfusion() {
		return this.ffpTransfusion;
	}

	public void setFfpTransfusion(Boolean ffpTransfusion) {
		this.ffpTransfusion = ffpTransfusion;
	}

	public String getG6pd() {
		return this.g6pd;
	}

	public void setG6pd(String g6pd) {
		this.g6pd = g6pd;
	}

	public Boolean getIvigTransfusion() {
		return this.ivigTransfusion;
	}

	public void setIvigTransfusion(Boolean ivigTransfusion) {
		this.ivigTransfusion = ivigTransfusion;
	}

	public String getMaxBilirubin() {
		return this.maxBilirubin;
	}

	public void setMaxBilirubin(String maxBilirubin) {
		this.maxBilirubin = maxBilirubin;
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



	public Boolean getNeonatalJaundice() {
		return this.neonatalJaundice;
	}

	public void setNeonatalJaundice(Boolean neonatalJaundice) {
		this.neonatalJaundice = neonatalJaundice;
	}

	

	public String getNooftransfusion() {
		return nooftransfusion;
	}



	public void setNooftransfusion(String nooftransfusion) {
		this.nooftransfusion = nooftransfusion;
	}



	public Boolean getPhototherapy() {
		return this.phototherapy;
	}

	public void setPhototherapy(Boolean phototherapy) {
		this.phototherapy = phototherapy;
	}

	public String getPhototherapyid() {
		return this.phototherapyid;
	}

	public void setPhototherapyid(String phototherapyid) {
		this.phototherapyid = phototherapyid;
	}

	public Boolean getPlateletTransfusion() {
		return this.plateletTransfusion;
	}

	public void setPlateletTransfusion(Boolean plateletTransfusion) {
		this.plateletTransfusion = plateletTransfusion;
	}

	public Boolean getPrbcTransfusion() {
		return this.prbcTransfusion;
	}

	public void setPrbcTransfusion(Boolean prbcTransfusion) {
		this.prbcTransfusion = prbcTransfusion;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}