package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The persistent class for the ref_bed database table.
 * 
 */
@Entity
@Table(name = "ref_bed")
@NamedQuery(name = "RefBed.findAll", query = "SELECT r FROM RefBed r")
public class RefBed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "bed_seq_id", strategy = "com.inicu.postgres.utility.MedicineIDGenerator", parameters = @Parameter(name = "name", value = "BD-ref_bed-bedid"))
	@GeneratedValue(generator = "bed_seq_id")
	private String bedid;

	private String bedname;

	private String description;

	private String roomid;

	private String loggeduser;

	@Column(columnDefinition = "boolean")
	private boolean status;

	@Column(columnDefinition = "boolean")
	private boolean isactive;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtimestamp;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp removaltimestamp;
	
	String branchname;

	public RefBed() {
	}

	public String getBedid() {
		return this.bedid;
	}

	public void setBedid(String bedid) {
		this.bedid = bedid;
	}

	public String getBedname() {
		return this.bedname;
	}

	public void setBedname(String bedname) {
		this.bedname = bedname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoomid() {
		return this.roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getCreationtimestamp() {
		return creationtimestamp;
	}

	public void setCreationtimestamp(Timestamp creationtimestamp) {
		this.creationtimestamp = creationtimestamp;
	}

	public Timestamp getRemovaltimestamp() {
		return removaltimestamp;
	}

	public void setRemovaltimestamp(Timestamp removaltimestamp) {
		this.removaltimestamp = removaltimestamp;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	@Override
	public String toString() {
		return "RefBed [bedid=" + bedid + ", bedname=" + bedname + ", description=" + description + ", roomid=" + roomid
				+ ", loggeduser=" + loggeduser + ", status=" + status + ", isactive=" + isactive
				+ ", creationtimestamp=" + creationtimestamp + ", removaltimestamp=" + removaltimestamp
				+ ", branchname=" + branchname + "]";
	}

}