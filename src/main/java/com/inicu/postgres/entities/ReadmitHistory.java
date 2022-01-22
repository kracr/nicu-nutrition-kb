package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the readmit_history database table.
 * 
 */
@Entity
@Table(name="readmit_history")
@NamedQuery(name="ReadmitHistory.findAll", query="SELECT b FROM ReadmitHistory b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadmitHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long readmitid;

	private Timestamp creationtime;
	
	private Timestamp modificationtime;
	
	private String uhid;
	
	private String nicubedno;
	
	private String nicuroomno;
	
	private String niculevelno;
	
	private String criticalitylevel;
	
	@Temporal(TemporalType.DATE)
	private Date admissiondate;
	
	@Temporal(TemporalType.DATE)
	private Date dischargeddate;
	
	@Temporal(TemporalType.DATE)
	private Date readmissiondate;

	public ReadmitHistory() {
	}
	
	public Long getReadmitid() {
		return readmitid;
	}

	public void setReadmitid(Long readmitid) {
		this.readmitid = readmitid;
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

	public Date getAdmissiondate() {
		return admissiondate;
	}

	public void setAdmissiondate(Date admissiondate) {
		this.admissiondate = admissiondate;
	}

	public Date getDischargeddate() {
		return dischargeddate;
	}

	public void setDischargeddate(Date dischargeddate) {
		this.dischargeddate = dischargeddate;
	}

	public Date getReadmissiondate() {
		return readmissiondate;
	}

	public void setReadmissiondate(Date readmissiondate) {
		this.readmissiondate = readmissiondate;
	}

	public String getNicubedno() {
		return nicubedno;
	}

	public void setNicubedno(String nicubedno) {
		this.nicubedno = nicubedno;
	}

	public String getNicuroomno() {
		return nicuroomno;
	}

	public void setNicuroomno(String nicuroomno) {
		this.nicuroomno = nicuroomno;
	}

	public String getNiculevelno() {
		return niculevelno;
	}

	public void setNiculevelno(String niculevelno) {
		this.niculevelno = niculevelno;
	}

	public String getCriticalitylevel() {
		return criticalitylevel;
	}

	public void setCriticalitylevel(String criticalitylevel) {
		this.criticalitylevel = criticalitylevel;
	}

}
