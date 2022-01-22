package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vw_doctornotes database table.
 * 
 */
@Entity
@Table(name="vw_doctornotes")
@NamedQuery(name="VwDoctornotes.findAll", query="SELECT v FROM VwDoctornotes v")
public class VwDoctornotes implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long doctornoteid;
	
	@Temporal(TemporalType.DATE)
	private Date creationdate;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String doctornotes;

	private String diagnosis;

	private String issues;

	private String plan;

	private String uhid;


	
	
		public VwDoctornotes() {
	}




		public Long getDoctornoteid() {
			return doctornoteid;
		}




		public void setDoctornoteid(Long doctornoteid) {
			this.doctornoteid = doctornoteid;
		}




		public Date getCreationdate() {
			return creationdate;
		}




		public void setCreationdate(Date creationdate) {
			this.creationdate = creationdate;
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




		public String getCurrentdayreport() {
			return doctornotes;
		}




		public void setCurrentdayreport(String currentdayreport) {
			this.doctornotes = currentdayreport;
		}




		public String getDiagnosis() {
			return diagnosis;
		}




		public void setDiagnosis(String diagnosis) {
			this.diagnosis = diagnosis;
		}




		public String getIssues() {
			return issues;
		}




		public void setIssues(String issues) {
			this.issues = issues;
		}




		public String getPlan() {
			return plan;
		}




		public void setPlan(String plan) {
			this.plan = plan;
		}




		public String getUhid() {
			return uhid;
		}




		public void setUhid(String uhid) {
			this.uhid = uhid;
		}

}