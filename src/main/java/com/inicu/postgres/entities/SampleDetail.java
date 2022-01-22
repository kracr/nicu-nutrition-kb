package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the sa_resp_apnea database table.
 * 
 */
@Entity
@Table(name = "sample_detail")
@NamedQuery(name = "SampleDetail.findAll", query = "SELECT s FROM SampleDetail s")
public class SampleDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sample_detail_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String sampletype;
	
	private String comments;
	
	private String samplevolume;
	
	private String loggeduser;

	public Long getSample_detail_id() {
		return sample_detail_id;
	}

	public void setSample_detail_id(Long sample_detail_id) {
		this.sample_detail_id = sample_detail_id;
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

	public String getSampletype() {
		return sampletype;
	}

	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSamplevolume() {
		return samplevolume;
	}

	public void setSamplevolume(String samplevolume) {
		this.samplevolume = samplevolume;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	
}
