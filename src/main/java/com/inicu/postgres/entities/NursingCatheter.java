package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the nursing_catheters database table.
 * 
 */
@Entity
@Table(name="nursing_catheters")
@NamedQuery(name="NursingCatheter.findAll", query="SELECT n FROM NursingCatheter n")
public class NursingCatheter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nn_cathetersid")
	private Long nnCathetersid;

	@Column(name="catheters_day")
	private String cathetersDay;

	@Temporal(TemporalType.DATE)
	@Column(name="catheters_enddate")
	private Date cathetersEnddate;

	@Column(name="catheters_line")
	private String cathetersLine;

	@Column(name="catheters_line_type")
	private String cathetersLineType;

	@Temporal(TemporalType.DATE)
	@Column(name="catheters_startdate")
	private Date cathetersStartdate;

	private Timestamp creationtime;

	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name="nn_catheters_time")
	private String nnCathetersTime;

	private String uhid;

	public NursingCatheter() {
	}

	public Long getNnCathetersid() {
		return this.nnCathetersid;
	}

	public void setNnCathetersid(Long nnCathetersid) {
		this.nnCathetersid = nnCathetersid;
	}

	public String getCathetersDay() {
		return this.cathetersDay;
	}

	public void setCathetersDay(String cathetersDay) {
		this.cathetersDay = cathetersDay;
	}

	public Date getCathetersEnddate() {
		return this.cathetersEnddate;
	}

	public void setCathetersEnddate(Date cathetersEnddate) {
		this.cathetersEnddate = cathetersEnddate;
	}

	public String getCathetersLine() {
		return this.cathetersLine;
	}

	public void setCathetersLine(String cathetersLine) {
		this.cathetersLine = cathetersLine;
	}

	public String getCathetersLineType() {
		return this.cathetersLineType;
	}

	public void setCathetersLineType(String cathetersLineType) {
		this.cathetersLineType = cathetersLineType;
	}

	public Date getCathetersStartdate() {
		return this.cathetersStartdate;
	}

	public void setCathetersStartdate(Date cathetersStartdate) {
		this.cathetersStartdate = cathetersStartdate;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnCathetersTime() {
		return this.nnCathetersTime;
	}

	public void setNnCathetersTime(String nnCathetersTime) {
		this.nnCathetersTime = nnCathetersTime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}