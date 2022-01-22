package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_medfrequency database table.
 * 
 */
@Entity
@Table(name="ref_medfrequency")
@NamedQuery(name="RefMedfrequency.findAll", query="SELECT r FROM RefMedfrequency r order by frequency_int ASC")
public class RefMedfrequency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String freqid;

	private String freqvalue;

	@Column(columnDefinition = "float4")
	private Float frequency_int;

	public RefMedfrequency() {
	}

	public String getFreqid() {
		return this.freqid;
	}

	public void setFreqid(String freqid) {
		this.freqid = freqid;
	}

	public String getFreqvalue() {
		return this.freqvalue;
	}

	public void setFreqvalue(String freqvalue) {
		this.freqvalue = freqvalue;
	}

	public Float getFrequency_int() {
		return this.frequency_int;
	}

	public void setFrequency_int(Float frequency_int) {
		this.frequency_int = frequency_int;
	}

}