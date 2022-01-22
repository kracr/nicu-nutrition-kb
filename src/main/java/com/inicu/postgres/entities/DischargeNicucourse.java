package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_nicucourse database table.
 * 
 */
@Entity
@Table(name="discharge_nicucourse")
@NamedQuery(name="DischargeNicucourse.findAll", query="SELECT d FROM DischargeNicucourse d")
public class DischargeNicucourse implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long maternaldetailid;*/

	@Column(name="cns_detail")
	private String cnsDetail;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long courseinicuid;

	

	@Column(name="cvs_detail")
	private String cvsDetail;

	@Column(name="gi_detail")
	private String giDetail;

	@Column(name="gu_detail")
	private String guDetail;

	@Column(name="infection_detail")
	private String infectionDetail;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name="respsystem_detail")
	private String respsystemDetail;

	private String uhid;

	public DischargeNicucourse() {
	}

	
	public String getCnsDetail() {
		return this.cnsDetail;
	}

	public void setCnsDetail(String cnsDetail) {
		this.cnsDetail = cnsDetail;
	}

	public Long getCourseinicuid() {
		return this.courseinicuid;
	}

	public void setCourseinicuid(Long courseinicuid) {
		this.courseinicuid = courseinicuid;
	}

	
	public String getCvsDetail() {
		return this.cvsDetail;
	}

	public void setCvsDetail(String cvsDetail) {
		this.cvsDetail = cvsDetail;
	}

	public String getGiDetail() {
		return this.giDetail;
	}

	public void setGiDetail(String giDetail) {
		this.giDetail = giDetail;
	}

	public String getGuDetail() {
		return this.guDetail;
	}

	public void setGuDetail(String guDetail) {
		this.guDetail = guDetail;
	}

	public String getInfectionDetail() {
		return this.infectionDetail;
	}

	public void setInfectionDetail(String infectionDetail) {
		this.infectionDetail = infectionDetail;
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

	public String getRespsystemDetail() {
		return this.respsystemDetail;
	}

	public void setRespsystemDetail(String respsystemDetail) {
		this.respsystemDetail = respsystemDetail;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}