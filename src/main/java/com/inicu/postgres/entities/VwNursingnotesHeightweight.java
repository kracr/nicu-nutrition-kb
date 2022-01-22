package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the vw_nursingnotes_heightweight database table.
 * 
 */
@Entity
@Table(name="vw_nursingnotes_heightweight")
@NamedQuery(name="VwNursingnotesHeightweight.findAll", query="SELECT v FROM VwNursingnotesHeightweight v")
public class VwNursingnotesHeightweight implements Serializable {
	private static final long serialVersionUID = 1L;

	private String currentage;
	

	@Column(columnDefinition="float4")
	private Float currentdateheight;

	@Column(columnDefinition="float4")
	private Float currentdateweight;

	@Column(columnDefinition="float4")
	private Float diffheight;

	@Column(columnDefinition="float4")
	private Float diffweight;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String uhid;

	@Temporal(TemporalType.DATE)
	private Date visitdate;

	public VwNursingnotesHeightweight() {
	}

	public String getCurrentage() {
		return this.currentage;
	}

	public void setCurrentage(String currentage) {
		this.currentage = currentage;
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

	public Float getDiffheight() {
		return this.diffheight;
	}

	public void setDiffheight(Float diffheight) {
		this.diffheight = diffheight;
	}

	public Float getDiffweight() {
		return this.diffweight;
	}

	public void setDiffweight(Float diffweight) {
		this.diffweight = diffweight;
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

	

}