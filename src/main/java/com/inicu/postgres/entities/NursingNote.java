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
 * The persistent class for the nursing_notes database table.
 * 
 */
@Entity
@Table(name = "nursing_notes")
@NamedQuery(name = "NursingNote.findAll", query = "SELECT n FROM NursingNote n")
public class NursingNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursing_notes_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Timestamp from_time;

	private Timestamp to_time;

	private String nursing_notes;

	private String loggeduser;
	
	@Column(columnDefinition="bool")
	private Boolean flag;

	public NursingNote() {
		super();
	}

	public Long getNursing_notes_id() {
		return nursing_notes_id;
	}

	public void setNursing_notes_id(Long nursing_notes_id) {
		this.nursing_notes_id = nursing_notes_id;
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

	public Timestamp getFrom_time() {
		return from_time;
	}

	public void setFrom_time(Timestamp from_time) {
		this.from_time = from_time;
	}

	public Timestamp getTo_time() {
		return to_time;
	}

	public void setTo_time(Timestamp to_time) {
		this.to_time = to_time;
	}

	public String getNursing_notes() {
		return nursing_notes;
	}

	public void setNursing_notes(String nursing_notes) {
		this.nursing_notes = nursing_notes;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "NursingNote [nursing_notes_id=" + nursing_notes_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", from_time=" + from_time
				+ ", to_time=" + to_time + ", nursing_notes=" + nursing_notes + ", loggeduser=" + loggeduser + "]";
	}

}