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

@Entity
@Table(name = "predict_hdp")
@NamedQuery(name = "PredictHDP.findAll", query = "SELECT r FROM PredictHDP r")
public class PredictHDP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long predicthdpid;

	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	private String uhid;
	
	private Timestamp hdptime;
	
	@Column(columnDefinition = "Float4")
	private Float hdpvalue;

	public Long getPredicthdpid() {
		return predicthdpid;
	}

	public void setPredicthdpid(Long predicthdpid) {
		this.predicthdpid = predicthdpid;
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

	public Timestamp getHdptime() {
		return hdptime;
	}

	public void setHdptime(Timestamp hdptime) {
		this.hdptime = hdptime;
	}

	public Float getHdpvalue() {
		return hdpvalue;
	}

	public void setHdpvalue(Float hdpvalue) {
		this.hdpvalue = hdpvalue;
	}


}
