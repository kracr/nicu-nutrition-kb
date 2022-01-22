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
 * The persistent class for the nursing_blood_product database table.
 * 
 */
@Entity
@Table(name = "nursing_blood_product")
@NamedQuery(name = "NursingBloodproduct.findAll", query = "SELECT n FROM NursingBloodproduct n")
public class NursingBloodproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursing_blood_product_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private Long doctor_blood_products_id;

	private Timestamp execution_time;

	@Column(columnDefinition = "Float4")
	private Float delivered_volume;

	private Timestamp collection_date;

	private Timestamp expiry_date;

	private String bag_number;

	private String blood_group;

	private Integer bag_volume;

	private String checked_by;

	private String cross_checked_by;
	
	private String consent_form_by;

	private String comments;
	
	private Integer hr;
	private Integer spo2;
	private Integer bp;
	
	@Column(name = "respiratory_rate")
	private Integer respiratoryRate;
	
	@Column(columnDefinition = "Float4")
	private Float temp;
	
	@Column(name = "adverse_reactions")
	private String adverseReactions;
	
	@Column(name = "adverse_reactions_other")
	private String adverseReactionsOther;
	
	@Column(columnDefinition = "bool")
	private Boolean flag;

	public NursingBloodproduct() {
		super();
	}

	public Long getNursing_blood_product_id() {
		return nursing_blood_product_id;
	}

	public void setNursing_blood_product_id(Long nursing_blood_product_id) {
		this.nursing_blood_product_id = nursing_blood_product_id;
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

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Long getDoctor_blood_products_id() {
		return doctor_blood_products_id;
	}

	public void setDoctor_blood_products_id(Long doctor_blood_products_id) {
		this.doctor_blood_products_id = doctor_blood_products_id;
	}

	public Timestamp getExecution_time() {
		return execution_time;
	}

	public void setExecution_time(Timestamp execution_time) {
		this.execution_time = execution_time;
	}

	public Float getDelivered_volume() {
		return delivered_volume;
	}

	public void setDelivered_volume(Float delivered_volume) {
		this.delivered_volume = delivered_volume;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCollection_date() {
		return collection_date;
	}

	public void setCollection_date(Timestamp collection_date) {
		this.collection_date = collection_date;
	}

	public Timestamp getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Timestamp expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getBag_number() {
		return bag_number;
	}

	public void setBag_number(String bag_number) {
		this.bag_number = bag_number;
	}

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public Integer getBag_volume() {
		return bag_volume;
	}

	public void setBag_volume(Integer bag_volume) {
		this.bag_volume = bag_volume;
	}

	public String getChecked_by() {
		return checked_by;
	}

	public void setChecked_by(String checked_by) {
		this.checked_by = checked_by;
	}

	public String getCross_checked_by() {
		return cross_checked_by;
	}

	public void setCross_checked_by(String cross_checked_by) {
		this.cross_checked_by = cross_checked_by;
	}

	public Integer getHr() {
		return hr;
	}

	public void setHr(Integer hr) {
		this.hr = hr;
	}

	public Integer getSpo2() {
		return spo2;
	}

	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}

	public Integer getBp() {
		return bp;
	}

	public void setBp(Integer bp) {
		this.bp = bp;
	}

	public Integer getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(Integer respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public String getAdverseReactions() {
		return adverseReactions;
	}

	public void setAdverseReactions(String adverseReactions) {
		this.adverseReactions = adverseReactions;
	}

	public String getAdverseReactionsOther() {
		return adverseReactionsOther;
	}

	public void setAdverseReactionsOther(String adverseReactionsOther) {
		this.adverseReactionsOther = adverseReactionsOther;
	}

	public String getConsent_form_by() {
		return consent_form_by;
	}

	public void setConsent_form_by(String consent_form_by) {
		this.consent_form_by = consent_form_by;
	}
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "NursingBloodproduct [nursing_blood_product_id=" + nursing_blood_product_id + ", creationtime="
				+ creationtime + ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", loggeduser="
				+ loggeduser + ", doctor_blood_products_id=" + doctor_blood_products_id + ", execution_time="
				+ execution_time + ", delivered_volume=" + delivered_volume + ", collection_date=" + collection_date
				+ ", expiry_date=" + expiry_date + ", bag_number=" + bag_number + ", blood_group=" + blood_group
				+ ", bag_volume=" + bag_volume + ", checked_by=" + checked_by + ", cross_checked_by=" + cross_checked_by
				+ ", consent_form_by=" + consent_form_by + ", comments=" + comments + ", hr=" + hr + ", spo2=" + spo2
				+ ", bp=" + bp + ", respiratoryRate=" + respiratoryRate + ", temp=" + temp + ", adverseReactions="
				+ adverseReactions + ", adverseReactionsOther=" + adverseReactionsOther + "]";
	}

}