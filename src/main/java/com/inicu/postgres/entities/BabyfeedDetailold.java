package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the babyfeed_detail database table.
 * 
 */
/*@Entity
@Table(name="babyfeed_detail")
@NamedQuery(name="BabyfeedDetail.findAll", query="SELECT b FROM BabyfeedDetail b")*/
public class BabyfeedDetailold implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long babyfeedid;

	private Timestamp creationtime;


	@Column(name="ivfluidml_perhr",columnDefinition="float4")
	private float ivfluidmlPerhr;

	private String ivfluidtype;

	@Column(name="total_ivfluids",columnDefinition="float4")
	private float totalIvfluids;

	@Column(columnDefinition="bool")
	private Boolean ebm;

	@Column(columnDefinition="float4")
	private float ebmperdayml;

	private Integer feedduration;

	@Column(columnDefinition="float4")
	private float feedvolume;

	/*	private Integer fluidduration;

	@Column(columnDefinition="float4")
	private float fluidvolume;
	 */
	@Column(columnDefinition="bool")
	private Boolean hmf;

	@Column(columnDefinition="float4")
	private float hmfperdayml;

	@Column(columnDefinition="bool")
	private Boolean lbfm;

	@Column(columnDefinition="float4")
	private float lbfmperdayml;

	private Timestamp modificationtime;

	@Column(columnDefinition="bool")
	private Boolean tfm;

	@Column(columnDefinition="float4")
	private float tfmperdayml;

	@Column(name="totalfeed_ml_day",columnDefinition="float4")
	private float totalfeedMlDay;

	@Column(name="totalfluid_ml_day",columnDefinition="float4")
	private float totalfluidMlDay;

	private String uhid;

	//new parameter after changed notes ui..

	@Column(columnDefinition="bool")
	private Boolean productmilk;

	@Column(columnDefinition="float4")
	private float productmilkperdayml;

	@Column(name="hmf_ebm",columnDefinition="bool")
	private Boolean hmfEbm;

	@Column(name="hmf_ebm_perdayml",columnDefinition="float4")
	private float hmfEbmPerdayml;

	private String feedmethod;
	
	@Column(columnDefinition="bool")
	private Boolean formulamilk;
	
	 private String formulamilkperdayml;	
	
	

	public BabyfeedDetailold() {
	}

	public Long getBabyfeedid() {
		return this.babyfeedid;
	}

	public void setBabyfeedid(Long babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
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
		return this.ebm;
	}

	public void setEbm(Boolean ebm) {
		this.ebm = ebm;
	}

	public float getEbmperdayml() {
		return this.ebmperdayml;
	}

	public void setEbmperdayml(float ebmperdayml) {
		this.ebmperdayml = ebmperdayml;
	}

	public Integer getFeedduration() {
		return this.feedduration;
	}

	public void setFeedduration(Integer feedduration) {
		this.feedduration = feedduration;
	}

	public float getFeedvolume() {
		return this.feedvolume;
	}

	public void setFeedvolume(float feedvolume) {
		this.feedvolume = feedvolume;
	}

	/*public Integer getFluidduration() {
		return this.fluidduration;
	}

	public void setFluidduration(Integer fluidduration) {
		this.fluidduration = fluidduration;
	}

	public float getFluidvolume() {
		return this.fluidvolume;
	}

	public void setFluidvolume(float fluidvolume) {
		this.fluidvolume = fluidvolume;
	}*/

	public Boolean getHmf() {
		return this.hmf;
	}

	public void setHmf(Boolean hmf) {
		this.hmf = hmf;
	}

	public float getHmfperdayml() {
		return this.hmfperdayml;
	}

	public void setHmfperdayml(float hmfperdayml) {
		this.hmfperdayml = hmfperdayml;
	}

	public Boolean getLbfm() {
		return this.lbfm;
	}

	public void setLbfm(Boolean lbfm) {
		this.lbfm = lbfm;
	}

	public float getLbfmperdayml() {
		return this.lbfmperdayml;
	}

	public void setLbfmperdayml(float lbfmperdayml) {
		this.lbfmperdayml = lbfmperdayml;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getTfm() {
		return this.tfm;
	}

	public void setTfm(Boolean tfm) {
		this.tfm = tfm;
	}

	public float getTfmperdayml() {
		return this.tfmperdayml;
	}

	public void setTfmperdayml(float tfmperdayml) {
		this.tfmperdayml = tfmperdayml;
	}

	public float getTotalfeedMlDay() {
		return this.totalfeedMlDay;
	}

	public void setTotalfeedMlDay(float totalfeedMlDay) {
		this.totalfeedMlDay = totalfeedMlDay;
	}

	public float getTotalfluidMlDay() {
		return this.totalfluidMlDay;
	}

	public void setTotalfluidMlDay(float totalfluidMlDay) {
		this.totalfluidMlDay = totalfluidMlDay;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean getProductmilk() {
		return productmilk;
	}

	public void setProductmilk(Boolean productmilk) {
		this.productmilk = productmilk;
	}

	public float getProductmilkperdayml() {
		return productmilkperdayml;
	}

	public void setProductmilkperdayml(float productmilkperdayml) {
		this.productmilkperdayml = productmilkperdayml;
	}

	public Boolean getHmfEbm() {
		return hmfEbm;
	}

	public void setHmfEbm(Boolean hmfEbm) {
		this.hmfEbm = hmfEbm;
	}

	public float getHmfEbmPerdayml() {
		return hmfEbmPerdayml;
	}

	public void setHmfEbmPerdayml(float hmfEbmPerdayml) {
		this.hmfEbmPerdayml = hmfEbmPerdayml;
	}

	public String getFeedmethod() {
		return feedmethod;
	}

	public void setFeedmethod(String feedmethod) {
		this.feedmethod = feedmethod;
	}

	public Boolean getFormulamilk() {
		return formulamilk;
	}

	public void setFormulamilk(Boolean formulamilk) {
		this.formulamilk = formulamilk;
	}

	public String getFormulamilkperdayml() {
		return formulamilkperdayml;
	}

	public void setFormulamilkperdayml(String formulamilkperdayml) {
		this.formulamilkperdayml = formulamilkperdayml;
	}


	
}