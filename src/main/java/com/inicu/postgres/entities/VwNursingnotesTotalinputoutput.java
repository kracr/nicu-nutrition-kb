package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the vw_nursingnotes_totalinputoutput database table.
 * 
 */
@Entity
@Table(name="vw_nursingnotes_totalinputoutput")
@NamedQuery(name="VwNursingnotesTotalinputoutput.findAll", query="SELECT v FROM VwNursingnotesTotalinputoutput v")
public class VwNursingnotesTotalinputoutput implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date creationdate;

	private Long totalinput;

	private Long totaloutput;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String uhid;

	public VwNursingnotesTotalinputoutput() {
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Long getTotalinput() {
		return this.totalinput;
	}

	public void setTotalinput(Long totalinput) {
		this.totalinput = totalinput;
	}

	public Long getTotaloutput() {
		return this.totaloutput;
	}

	public void setTotaloutput(Long totaloutput) {
		this.totaloutput = totaloutput;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}