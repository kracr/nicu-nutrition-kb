package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the exceptions database table.
 * 
 */
@Entity
@Table(name="exceptionlist")
@NamedQuery(name="Exceptions.findAll", query="SELECT e FROM Exceptions e")
public class Exceptions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long exceptionid;

	private Timestamp creationtime;

	@Column(name="exceptiontype")
	private String exceptionType;

	private String loggedinuser;

	private String exceptionmessage;

	private Timestamp modificationtime;

	private String uhid;
	

	public Exceptions() {
	}

	public Long getId() {
		return this.exceptionid;
	}

	public void setId(Long id) {
		this.exceptionid = id;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getExceptionType() {
		return this.exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getLoggedinuser() {
		return this.loggedinuser;
	}

	public void setLoggedinuser(String loggedinuser) {
		this.loggedinuser = loggedinuser;
	}

	public String getMessage() {
		return this.exceptionmessage;
	}

	public void setMessage(String message) {
		this.exceptionmessage = message;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}