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
 * The persistent class for the nursing_notes database table.
 * 
 */
@Entity
@Table(name = "nursing_notes_history")
@NamedQuery(name = "NursingNotesHistory.findAll", query = "SELECT n FROM NursingNotesHistory n")
public class NursingNotesHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursing_notes_history_id;
	
	private Long nursing_notes_id;

	private Timestamp creationtime;

	private String uhid;

	private Timestamp from_time;

	private Timestamp to_time;

	private String nursing_notes;

	private String loggeduser;

	public NursingNotesHistory() {
		super();
	}

	public Long getNursing_notes_history_id() {
		return nursing_notes_history_id;
	}

	public void setNursing_notes_history_id(Long nursing_notes_history_id) {
		this.nursing_notes_history_id = nursing_notes_history_id;
	}

	public Long getNursing_notes_id() {
		return nursing_notes_id;
	}

	public void setNursing_notes_id(Long nursing_notes_id) {
		this.nursing_notes_id = nursing_notes_id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	@Override
	public String toString() {
		return "NursingNotesHistory [nursing_notes_history_id=" + nursing_notes_history_id + ", nursing_notes_id="
				+ nursing_notes_id + ", creationtime=" + creationtime + ", uhid=" + uhid + ", from_time=" + from_time
				+ ", to_time=" + to_time + ", nursing_notes=" + nursing_notes + ", loggeduser=" + loggeduser + "]";
	}

	
}