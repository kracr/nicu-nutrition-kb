package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_advice_detail database table.
 * 
 */
@Entity
@Table(name="discharge_advice_detail")
@NamedQuery(name="DischargeAdviceDetail.findAll", query="SELECT d FROM DischargeAdviceDetail d")
public class DischargeAdviceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="discharge_advice_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long dischargeAdviceId;
	

	private Timestamp creationtime;

	private String editedvalue;

	@Column(columnDefinition="bool")
	private Boolean isselected;

	private Timestamp modificationtime;

	private String uhid;
	
	@Column(name = "ref_advice_id")
	private Long refAdviceId;

	@Column(name="isdateincluded",columnDefinition = "bool")
	private Boolean dateIncluded;

	public Long getDischargeAdviceId() {
		return dischargeAdviceId;
	}

	public void setDischargeAdviceId(Long dischargeAdviceId) {
		this.dischargeAdviceId = dischargeAdviceId;
	}

	
	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEditedvalue() {
		return editedvalue;
	}

	public void setEditedvalue(String editedvalue) {
		this.editedvalue = editedvalue;
	}

	public Boolean getIsselected() {
		return isselected;
	}

	public void setIsselected(Boolean isselected) {
		this.isselected = isselected;
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

	public Long getRefAdviceId() {
		return refAdviceId;
	}

	public void setRefAdviceId(Long refAdviceId) {
		this.refAdviceId = refAdviceId;
	}


	public Boolean getDateIncluded() {
		return dateIncluded;
	}

	public void setDateIncluded(Boolean dateIncluded) {
		this.dateIncluded = dateIncluded;
	}
}
