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
import javax.persistence.Transient;

/**
 * The persistent class for the vtap database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "vtap")
@NamedQuery(name = "Vtap.findAll", query = "SELECT s FROM Vtap s")
public class Vtap implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long vtap_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String vtap_site;

	private String vtap_size;

	private String vtap_csf;

	@Transient
	private Object vtap_date;

	@Transient
	private Integer vtap_hrs;

	@Transient
	private Integer vtap_mins;

	@Transient
	private String vtap_meridian;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp vtap_timestamp;

	private String loggeduser;

	private String progressnotes;

	private String comments;
	
	public Vtap() {
		super();
	}

	public Long getVtap_id() {
		return vtap_id;
	}

	public void setVtap_id(Long vtap_id) {
		this.vtap_id = vtap_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVtap_site() {
		return vtap_site;
	}

	public void setVtap_site(String vtap_site) {
		this.vtap_site = vtap_site;
	}

	public String getVtap_size() {
		return vtap_size;
	}

	public void setVtap_size(String vtap_size) {
		this.vtap_size = vtap_size;
	}

	public String getVtap_csf() {
		return vtap_csf;
	}

	public void setVtap_csf(String vtap_csf) {
		this.vtap_csf = vtap_csf;
	}

	public Object getVtap_date() {
		return vtap_date;
	}

	public void setVtap_date(Object vtap_date) {
		this.vtap_date = vtap_date;
	}

	public Integer getVtap_hrs() {
		return vtap_hrs;
	}

	public void setVtap_hrs(Integer vtap_hrs) {
		this.vtap_hrs = vtap_hrs;
	}

	public Integer getVtap_mins() {
		return vtap_mins;
	}

	public void setVtap_mins(Integer vtap_mins) {
		this.vtap_mins = vtap_mins;
	}

	public String getVtap_meridian() {
		return vtap_meridian;
	}

	public void setVtap_meridian(String vtap_meridian) {
		this.vtap_meridian = vtap_meridian;
	}

	public Timestamp getVtap_timestamp() {
		return vtap_timestamp;
	}

	public void setVtap_timestamp(Timestamp vtap_timestamp) {
		this.vtap_timestamp = vtap_timestamp;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Vtap [vtap_id=" + vtap_id + ", creationtime=" + creationtime
				+ ", uhid=" + uhid + ", vtap_site=" + vtap_site
				+ ", vtap_size=" + vtap_size + ", vtap_csf=" + vtap_csf
				+ ", vtap_date=" + vtap_date + ", vtap_hrs=" + vtap_hrs
				+ ", vtap_mins=" + vtap_mins + ", vtap_meridian="
				+ vtap_meridian + ", vtap_timestamp=" + vtap_timestamp
				+ ", loggeduser=" + loggeduser + ", progressnotes="
				+ progressnotes + ", comments=" + comments + "]";
	}

}
