package com.inicu.models;

/**
 * 
 * @author iNICU 
 *
 */
public class FeedsObj {

	
	private Boolean isEbmSelected;
	private Boolean isLbfmSelected;
	private Boolean isTfmSelected;
	private Boolean isHmfSelected;
	private Object ebm;
	private Object lbfm;
	private Object tfm;
	private Object hmf;
	private Object totalfeedMlDay;
	private Object  feedduration;
	private Object  feedvolume;
	private Object totalIvfluids;
	private Object  ivfluidtype;
	private Object  ivfluidmlPerhr;
	private Object totalfluidMlDay;
	private Object  fluidduration;
	private Object  fluidvolume;
	
//new parameter as per changed doctor notes..	
	private Boolean productmilk;
	private float productmilkperdayml;
	
	private Boolean hmfEbm;
	private float hmfEbmPerdayml;
	
	private String feedmethod;

	public FeedsObj() {
		super();
		this.isEbmSelected = false;
		this.isLbfmSelected = false;
		this.isTfmSelected = false;
		this.isHmfSelected = false;
		this.ebm = "";
		this.lbfm = "";
		this.tfm = "";
		this.hmf = "";
		this.totalfeedMlDay = "";
		this.feedduration = "";
		this.feedvolume = "";
		this.totalIvfluids="";
		this.ivfluidtype ="";
		this.ivfluidmlPerhr="";
		this.totalfluidMlDay = "";
		this.fluidduration = "";
		this.fluidvolume = "";
		this.productmilk =false;
		this.productmilkperdayml=0;
		this.hmfEbm=false;
		this.hmfEbmPerdayml=0;
		this.feedmethod ="";
	}



	/*public FeedsObj() {
		super();
		this.isEbmSelected = true;
		this.isTfmSelected =false;
		this.isLbfmSelected =false;
		this.isHmfSelected = true;
		this.ebm = "13";
		this.lbfm = "12";
		this.tfm = "";
		this.hmf = "20";
		this.totalfeedMlDay = "123";
		this.feedduration = "1";
		this.feedvolume = "12";
		this.dextroseMlDay = "12";
		this.dextroseduration = "2";
		this.dextrosevolume = "21";
		this.totalfluidMlDay = "122";
		this.fluidduration = "3";
		this.fluidvolume = "12";
	}

*/

	public Boolean getIsEbmSelected() {
		return isEbmSelected;
	}



	public void setIsEbmSelected(Boolean isEbmSelected) {
		this.isEbmSelected = isEbmSelected;
	}



	public Boolean getIsLbfmSelected() {
		return isLbfmSelected;
	}



	public void setIsLbfmSelected(Boolean isLbfmSelected) {
		this.isLbfmSelected = isLbfmSelected;
	}



	public Boolean getIsTfmSelected() {
		return isTfmSelected;
	}



	public void setIsTfmSelected(Boolean isTfmSelected) {
		this.isTfmSelected = isTfmSelected;
	}



	public Boolean getIsHmfSelected() {
		return isHmfSelected;
	}



	public void setIsHmfSelected(Boolean isHmfSelected) {
		this.isHmfSelected = isHmfSelected;
	}



	public Object getEbm() {
		return ebm;
	}



	public void setEbm(Object ebm) {
		this.ebm = ebm;
	}



	public Object getLbfm() {
		return lbfm;
	}



	public void setLbfm(Object lbfm) {
		this.lbfm = lbfm;
	}



	public Object getTfm() {
		return tfm;
	}



	public void setTfm(Object tfm) {
		this.tfm = tfm;
	}



	public Object getHmf() {
		return hmf;
	}



	public void setHmf(Object hmf) {
		this.hmf = hmf;
	}



	public Object getTotalfeedMlDay() {
		return totalfeedMlDay;
	}



	public void setTotalfeedMlDay(Object totalfeedMlDay) {
		this.totalfeedMlDay = totalfeedMlDay;
	}



	public Object getFeedduration() {
		return feedduration;
	}



	public void setFeedduration(Object feedduration) {
		this.feedduration = feedduration;
	}



	public Object getFeedvolume() {
		return feedvolume;
	}



	public void setFeedvolume(Object feedvolume) {
		this.feedvolume = feedvolume;
	}



	


	



	public Object getTotalIvfluids() {
		return totalIvfluids;
	}



	public void setTotalIvfluids(Object totalIvfluids) {
		this.totalIvfluids = totalIvfluids;
	}



	public Object getIvfluidtype() {
		return ivfluidtype;
	}



	public void setIvfluidtype(Object ivfluidtype) {
		this.ivfluidtype = ivfluidtype;
	}



	public Object getIvfluidmlPerhr() {
		return ivfluidmlPerhr;
	}



	public void setIvfluidmlPerhr(Object ivfluidmlPerhr) {
		this.ivfluidmlPerhr = ivfluidmlPerhr;
	}



	public Object getTotalfluidMlDay() {
		return totalfluidMlDay;
	}



	public void setTotalfluidMlDay(Object totalfluidMlDay) {
		this.totalfluidMlDay = totalfluidMlDay;
	}



	public Object getFluidduration() {
		return fluidduration;
	}



	public void setFluidduration(Object fluidduration) {
		this.fluidduration = fluidduration;
	}



	public Object getFluidvolume() {
		return fluidvolume;
	}



	public void setFluidvolume(Object fluidvolume) {
		this.fluidvolume = fluidvolume;
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

	
	
	
}
