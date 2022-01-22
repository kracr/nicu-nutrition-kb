package com.inicu.postgres.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="vw_growthchat")
@NamedQuery(name="vw_growthchat.findAll", query="SELECT v FROM VwGrowthChat v")
public class VwGrowthChat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="float4")
	private Float currentdateheadcircum;
	
	@Column(columnDefinition="float4")
	private Float currentdateheight;
	
	@Column(columnDefinition="float4")
	private Float currentdateweight;

	private String uhid;

	
	private Date visitdate;
	
	@Id
	private Long visitid;

	public VwGrowthChat() {
	}

	public Float getCurrentdateheadcircum() {
		return this.currentdateheadcircum;
	}

	public void setCurrentdateheadcircum(Float currentdateheadcircum) {
		this.currentdateheadcircum = currentdateheadcircum;
	}

	public Float getCurrentdateheight() {
		return this.currentdateheight;
	}

	public void setCurrentdateheight(Float currentdateheight) {
		this.currentdateheight = currentdateheight;
	}

	public Float getCurrentdateweight() {
		return this.currentdateweight;
	}

	public void setCurrentdateweight(Float currentdateweight) {
		this.currentdateweight = currentdateweight;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Date getVisitdate() {
		return this.visitdate;
	}

	public void setVisitdate(Date visitdate) {
		this.visitdate = visitdate;
	}

	public Long getVisitid() {
		return this.visitid;
	}

	public void setVisitid(Long visitid) {
		this.visitid = visitid;
	}

}