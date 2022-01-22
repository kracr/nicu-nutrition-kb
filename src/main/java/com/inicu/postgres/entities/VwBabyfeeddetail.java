package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vw_babyfeeddetail database table.
 * 
 */
@Entity
@Table(name="vw_babyfeeddetail")
@NamedQuery(name="VwBabyfeeddetail.findAll", query="SELECT v FROM VwBabyfeeddetail v")
public class VwBabyfeeddetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long babyfeedid;

	@Temporal(TemporalType.DATE)
	private Date creationdate;

	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	@Column(name="ivfluidml_perhr",columnDefinition="float4")
	private float ivfluidmlPerhr;

	private String ivfluidtype;
	
	@Column(name="total_ivfluids",columnDefinition="float4")
	private float totalIvfluids;

	
	@Column(columnDefinition="bool")
	private Boolean ebm;

	@Column(columnDefinition="float4")
	private float ebmperdayml;

	private String feedduration;

	@Column(columnDefinition="float4")
	private float feedvolume;
	

	@Column(columnDefinition="bool")
	private Boolean hmf;

	@Column(columnDefinition="float4")
	private float hmfperdayml;

	@Column(columnDefinition="bool")
	private Boolean lbfm;

	@Column(columnDefinition="float4")
	private float lbfmperdayml;


	@Column(columnDefinition="bool")
	private Boolean tfm;

	@Column(columnDefinition="float4")
	private float tfmperdayml;

	@Column(name="totalfeed_ml_day",columnDefinition="float4")
	private float totalfeedMlDay;

	@Column(name="totalfluid_ml_day",columnDefinition="float4")
	private float totalfluidMlDay;

	private String uhid;


	public VwBabyfeeddetail() {
	}


	public Long getBabyfeedid() {
		return babyfeedid;
	}


	public void setBabyfeedid(Long babyfeedid) {
		this.babyfeedid = babyfeedid;
	}


	public Date getCreationdate() {
		return creationdate;
	}


	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
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




	public float getIvfluidmlPerhr() {
		return ivfluidmlPerhr;
	}


	public void setIvfluidmlPerhr(float ivfluidmlPerhr) {
		this.ivfluidmlPerhr = ivfluidmlPerhr;
	}


	public String getIvfluidtype() {
		return ivfluidtype;
	}


	public void setIvfluidtype(String ivfluidtype) {
		this.ivfluidtype = ivfluidtype;
	}


	public float getTotalIvfluids() {
		return totalIvfluids;
	}


	public void setTotalIvfluids(float totalIvfluids) {
		this.totalIvfluids = totalIvfluids;
	}


	public Boolean getEbm() {
		return ebm;
	}


	public void setEbm(Boolean ebm) {
		this.ebm = ebm;
	}


	public float getEbmperdayml() {
		return ebmperdayml;
	}


	public void setEbmperdayml(float ebmperdayml) {
		this.ebmperdayml = ebmperdayml;
	}


	public String getFeedduration() {
		return feedduration;
	}


	public void setFeedduration(String feedduration) {
		this.feedduration = feedduration;
	}


	public float getFeedvolume() {
		return feedvolume;
	}


	public void setFeedvolume(float feedvolume) {
		this.feedvolume = feedvolume;
	}


	public Boolean getHmf() {
		return hmf;
	}


	public void setHmf(Boolean hmf) {
		this.hmf = hmf;
	}


	public float getHmfperdayml() {
		return hmfperdayml;
	}


	public void setHmfperdayml(float hmfperdayml) {
		this.hmfperdayml = hmfperdayml;
	}


	public Boolean getLbfm() {
		return lbfm;
	}


	public void setLbfm(Boolean lbfm) {
		this.lbfm = lbfm;
	}


	public float getLbfmperdayml() {
		return lbfmperdayml;
	}


	public void setLbfmperdayml(float lbfmperdayml) {
		this.lbfmperdayml = lbfmperdayml;
	}


	public Boolean getTfm() {
		return tfm;
	}


	public void setTfm(Boolean tfm) {
		this.tfm = tfm;
	}


	public float getTfmperdayml() {
		return tfmperdayml;
	}


	public void setTfmperdayml(float tfmperdayml) {
		this.tfmperdayml = tfmperdayml;
	}


	public float getTotalfeedMlDay() {
		return totalfeedMlDay;
	}


	public void setTotalfeedMlDay(float totalfeedMlDay) {
		this.totalfeedMlDay = totalfeedMlDay;
	}


	public float getTotalfluidMlDay() {
		return totalfluidMlDay;
	}


	public void setTotalfluidMlDay(float totalfluidMlDay) {
		this.totalfluidMlDay = totalfluidMlDay;
	}


	public String getUhid() {
		return uhid;
	}


	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	
}