package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the babyfeed_detail database table.
 * 
 */
@Entity
@Table(name = "babyfeed_detail")
@NamedQuery(name = "BabyfeedDetail.findAll", query = "SELECT b FROM BabyfeedDetail b")
public class BabyfeedDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long babyfeedid;

	private String feedText;

	private Timestamp creationtime;
	
	@Column(name = "entrydatetime")
	private Timestamp entryDateTime;
	
	private String dilution;

	@Column(columnDefinition = "bool")
	private Boolean ebm;

	@Column(columnDefinition = "Float4")
	private Float ebmperdayml;

	private String feedduration;

	private String feedmethod;

	@Column(columnDefinition = "bool")
	private Boolean feedmethod_type;

	@Transient
	private List feedmethodList;

	private String libBreastFeed;

	@Transient
	List<String> libBreastFeedList;

	private String feedtype;

	@Transient
	List<String> feedTypeList;

	private String feedTypeSecondary;

	@Transient
	List<String> feedTypeSecondaryList;

	@Transient
	private String feedtypeOther;

	@Column(columnDefinition = "Float4")
	private Float feedvolume;

	private String girvalue;

	@Column(columnDefinition = "bool")
	private Boolean formulamilk;

	private String formulamilkperdayml;

	@Column(columnDefinition = "bool")
	private Boolean hmf;
	
	@Column(name = "blood_product_message")
	private String bloodProductMessage;

	@Column(name = "hmf_ebm", columnDefinition = "bool")
	private Boolean hmfEbm;
	
	@Column(name = "stop_pn", columnDefinition = "bool")
	private Boolean stopPN;
	
	@Column(name = "stop_additional_pn", columnDefinition = "bool")
	private Boolean stopAdditionalPN;

	@Column(name = "hmf_ebm_perdayml", columnDefinition = "Float4")
	private Float hmfEbmPerdayml;

	@Column(columnDefinition = "Float4")
	private Float hmfperdayml;

	@Column(name = "ivfluidml_perhr", columnDefinition = "Float4")
	private Float ivfluidmlPerhr;

	private String ivfluidrate;

	private String ivfluidtype;

	@Column(columnDefinition = "bool")
	private Boolean lbfm;

	@Column(columnDefinition = "Float4")
	private Float lbfmperdayml;

	private Timestamp modificationtime;

	@Column(name = "is_normal_tpn", columnDefinition = "bool")
	private Boolean isNormalTpn;

	@Column(columnDefinition = "bool")
	private Boolean productmilk;

	@Column(columnDefinition = "bool")
	private Boolean isAdLibGiven;

	@Column(columnDefinition = "Float4")
	private Float productmilkperdayml;

	private String tfi;

	@Column(columnDefinition = "bool")
	private Boolean tfm;

	@Column(columnDefinition = "Float4")
	private Float tfmperdayml;

	@Column(name = "total_intake")
	private String totalIntake;

	@Column(name = "total_ivfluids", columnDefinition = "Float4")
	private Float totalIvfluids;

	@Column(name = "totalfeed_ml_day", columnDefinition = "Float4")
	private Float totalfeedMlDay;

	@Column(name = "totalfeed_ml_per_kg_day", columnDefinition = "Float4")
	private Float totalfeedMlPerKgDay;
	
	// changes...............
	@Column(name = "totalivfluid_mlkgday", columnDefinition = "Float4")
	private Float totalivfluidMlkgday;

	@Column(name = "current_dextrose_concentration", columnDefinition = "Float4")
	private Float currentDextroseConcentration;

	@Column(name = "dextrose_conc_high")
	private String dextroseConcHigh;

	@Column(name = "dextrose_conc_highvolume", columnDefinition = "Float4")
	private Float dextroseConcHighvolume;

	@Column(name = "dextrose_conc_low")
	private String dextroseConcLow;

	@Column(name = "dextrose_conc_lowvolume", columnDefinition = "Float4")
	private Float dextroseConcLowvolume;

	// changes 19th june mom.....
	@Column(columnDefinition = "bool")
	private Boolean isBolusGiven;
	
	private String ismakeDailyPN;
	
	@Column(columnDefinition = "bool")
	private Boolean isParenteralDextrose;
	
	@Column(columnDefinition = "bool")
	private Boolean isInfusion;
	
	private String bolusType;

	private String bolusMethod;

	@Transient
	List<String> bolusMethodList;
	
	@Transient
	List<String> fluidTypeList;
		
	@Column(columnDefinition = "Float4")
	private Float bolusVolume;

	private Integer bolusFrequency;

	private Integer bolus_infusiontime;

	@Column(columnDefinition = "Float4")
	private Float bolus_rate;

	@Column(columnDefinition = "Float4")
	private Float bolusTotalFeed;

	@Column(columnDefinition = "bool")
	Boolean bolus_executed;

	@Transient
	List<HashMap<String, Object>> pastBolusObject;

	@Column(columnDefinition = "float4")
	private Float pastBolusTotalFeed;

	@Column(name = "aminoacid_conc", columnDefinition = "Float4")
	private Float aminoacidConc;

	@Column(name = "aminoacid_pnvolume")
	private String aminoacidPnvolume;

	@Column(name = "aminoacid_total", columnDefinition = "Float4")
	private Float aminoacidTotal;

	@Column(columnDefinition = "float4")
	private Float enProteinEnergyRatio;

	@Column(columnDefinition = "float4")
	private Float enCarbohydratesLipidsRatio;

	@Column(columnDefinition = "float4")
	private Float totalProteinEnergyRatio;

	@Column(columnDefinition = "float4")
	private Float totalCarbohydratesLipidsRatio;

	@Column(columnDefinition = "float4")
	private Float pnProteinEnergyRatio;

	@Column(columnDefinition = "float4")
	private Float pnCarbohydratesLipidsRatio;

	@Column(name = "calcium_total", columnDefinition = "float4")
	private Float calciumTotal;

	@Column(name = "calcium_volume", columnDefinition = "float4")
	private Float calciumVolume;

	@Column(name = "calsyrup_total", columnDefinition = "float4")
	private Float calsyrupTotal;

	@Column(name = "calsyrup_volume", columnDefinition = "float4")
	private Float calsyrupVolume;

	@Column(name = "calsyrup_feed", columnDefinition = "float4")
	private Float calsyrupFeed;

	@Column(name = "vitamind_feed", columnDefinition = "float4")
	private Float vitaminDFeed;
	
	@Column(name = "iron_feed", columnDefinition = "float4")
	private Float ironFeed;

	@Column(name = "vitamina_feed", columnDefinition = "float4")
	private Float vitaminaFeed;

	@Column(name = "other_volume", columnDefinition = "float4")
	private Float otherVolume;

	@Column(name = "other_total", columnDefinition = "float4")
	private Float otherTotal;

	@Column(name = "other_feed", columnDefinition = "float4")
	private Float otherFeed;

	private Integer calsyrupDuration;
	
	private Integer mctDuration;
	
	@Column(name = "mct_total", columnDefinition = "float4")
	private Float mctTotal;

	@Column(name = "mct_volume", columnDefinition = "float4")
	private Float mctVolume;

	@Column(name = "mct_feed", columnDefinition = "float4")
	private Float mctFeed;

	private Integer ironDuration;

	private Integer vitaminaDuration;
	
	private Integer duration;

	private Integer vitaminDDuration;
	
	private Integer otherDuration;

	@Column(name = "other_additive")
	private String otherAdditive;

	@Column(columnDefinition = "bool")
	private Boolean enteral;

	@Transient
	private String enteralNotes;

	@Column(name = "hco3_total", columnDefinition = "float4")
	private Float hco3Total;

	@Column(name = "hco3_volume", columnDefinition = "float4")
	private Float hco3Volume;

	@Column(name = "iron_total", columnDefinition = "float4")
	private Float ironTotal;

	@Column(name = "iron_volume", columnDefinition = "float4")
	private Float ironVolume;

	@Column(columnDefinition = "bool")
	private Boolean isenternalgiven;

	@Column(columnDefinition = "bool")
	private Boolean isparentalgiven;

	@Column(name = "lipid_conc", columnDefinition = "float4")
	private Float lipidConc;

	@Column(name = "lipid_pnvolume")
	private String lipidPnvolume;

	@Column(name = "lipid_total", columnDefinition = "float4")
	private Float lipidTotal;

	@Column(name = "magnesium_total", columnDefinition = "float4")
	private Float magnesiumTotal;

	@Column(name = "magnesium_volume", columnDefinition = "float4")
	private Float magnesiumVolume;

	@Column(name = "mvi_total", columnDefinition = "float4")
	private Float mviTotal;

	@Column(name = "mvi_volume", columnDefinition = "float4")
	private Float mviVolume;

	@Column(columnDefinition = "bool")
	private Boolean parenteral;

	@Column(name = "phosphorous_total", columnDefinition = "float4")
	private Float phosphorousTotal;

	@Column(name = "phosphorous_volume", columnDefinition = "float4")
	private Float phosphorousVolume;

	@Column(name = "potassium_total", columnDefinition = "float4")
	private Float potassiumTotal;

	@Column(name = "potassium_volume", columnDefinition = "float4")
	private Float potassiumVolume;

	@Column(name = "sodium_total", columnDefinition = "float4")
	private Float sodiumTotal;

	@Column(name = "sodium_volume", columnDefinition = "float4")
	private Float sodiumVolume;
	
	@Column(name = "sodium_meg", columnDefinition = "float4")
	private Float sodiumMeg;

	@Column(name = "vitamina_total", columnDefinition = "float4")
	private Float vitaminaTotal;

	@Column(name = "vitamina_volume", columnDefinition = "float4")
	private Float vitaminaVolume;

	@Column(name = "vitamind_total", columnDefinition = "float4")
	private Float vitamindTotal;

	@Column(name = "vitamind_volume", columnDefinition = "float4")
	private Float vitamindVolume;

	@Column(name = "working_weight", columnDefinition = "float4")
	private Float workingWeight;

	@Column(columnDefinition = "bool")
	private Boolean additiveenteral;

	@Column(columnDefinition = "bool")
	private Boolean isAdditiveInENCalculation;

	@Column(columnDefinition = "bool")
	private Boolean additiveparenteral;

	@Column(columnDefinition = "float4")
	private Float dextroseVolumemlperday;

	@Column(columnDefinition = "float4")
	private Float totalenteralvolume;

	@Column(columnDefinition = "float4")
	private Float totalparenteralvolume;

	@Column(columnDefinition = "float4")
	private Float totalenteraAdditivelvolume;

	@Column(columnDefinition = "float4")
	private Float totalparenteralAdditivevolume;

	@Column(columnDefinition = "float4")
	private Float dextroseNetConcentration;

	@Column(columnDefinition = "float4")
	private Float osmolarity;

	@Column(columnDefinition = "bool")
	private Boolean isReadymadeSolutionGiven;

	@Column(columnDefinition = "float4")
	private Float readymadeFluidRate;

	private String readymadeFluidType;
	
	private String readymadeFluidTypeList;
	
	private String readymadeDextroseConcLowType;

	private String readymadeDextroseConcHighType;
	
	@Column(columnDefinition = "float4")
	private Float readymadeDextroseConcentration;
	
	@Column(columnDefinition = "float4")
	private Float readymadeDextroseConcLowvolume;
	
	@Column(columnDefinition = "float4")
	private Float readymadeDextroseConcHighvolume;
	
	private Integer readymadeDextroseConcLow;
	
	private Integer readymadeDextroseConcHigh;

	private String remainingfluid;

	@Column(name = "remainingfluid_mlkg")
	private String remainingfluidMlkg;

	@Column(name = "dextrose_conc")
	private String dextroseConc;

	@Column(name = "lipid_type")
	private String lipidType;

	@Column(name = "calcium_concentration_type")
	private String calciumConcentrationType;
	
	private String calBrand;
	
	private String mctBrand;

	private String ironBrand;
	
	private String multiVitaminBrand;

	private String vitaminDBrand;
	
	private String otherBrand;
	
	private String unitOther;
	
	@Column(columnDefinition = "bool")
	private Boolean isStrengthOther;
	
	@Column(name = "additional_volume", columnDefinition = "Float4")
	private Float additionalVolume;
	
	@Column(name = "additional_kcl", columnDefinition = "Float4")
	private Float additionalKCL;
	
	@Column(name = "additional_total_volume", columnDefinition = "Float4")
	private Float additionalTotalVolume;
	
	@Column(name = "additional_rate", columnDefinition = "Float4")
	private Float additionalRate;
	
	@Column(name = "additional_duration", columnDefinition = "Float4")
	private Float additionalDuration;
	
	@Column(name = "additional_pn_type")
	private String additionalPNType;
	
	@Column(columnDefinition = "bool", name = "is_revise")
	private Boolean isRevise;
	
	@Column(columnDefinition = "bool", name = "is_gir_calculate")
	private Boolean isGirCalculate;
	
	@Column(columnDefinition = "bool", name = "is_amino_rate")
	private Boolean isAminoRate;

	@Column(columnDefinition = "float4")
	private Float readymadeFluidVolume;

	@Column(columnDefinition = "float4")
	private Float readymadeTotalFluidVolume;

	@Transient
	private String feedMethodOther;

	private String libBreastFeedOther;

	@Column(name = "totalfluid_ml_day", columnDefinition = "Float4")
	private Float totalfluidMlDay;

	private String uhid;

	@Column(name = "total_medvolume", columnDefinition = "float4")
	private Float totalMedvolume;
	
	@Column(name = "total_blood_product_volume", columnDefinition = "float4")
	private Float totalBloodProductVolume;
	
	@Column(name = "total_heplock_volume", columnDefinition = "float4")
	private Float totalHeplockVolume;

	private String loggeduser;

	@Column(columnDefinition = "bool")
	private Boolean isFeedGiven;
	@Column(columnDefinition = "bool")
	private Boolean isIVMedcineGiven;

	private String isIVFluidGiven;
	
	@Transient
	private String enFeedStr;
	
	@Transient
	private String formulaType;
	
	@Transient
	private String primaryFeed;

	private String causeofnpo;

	@Transient
	private List<String> causeListOfNPO;

	@Column(name="npo_due_to_feedintolerance",columnDefinition = "bool")
	private Boolean NpoDuetoFeedIntolerance;

	@Column(name="skip_feed",columnDefinition = "bool")
	private Boolean skipFeed;

	private String episodeid;

	@Column(name="blood_product_given",columnDefinition = "bool")
	private Boolean bloodProductGiven;
	
	@Column(name="increment_feed_given",columnDefinition = "bool")
	private Boolean incrementFeedGiven;
	
	@Column(name = "target_feed", columnDefinition = "float4")
	private Float targetFeed;
	
	@Column(name = "start_feed", columnDefinition = "float4")
	private Float startFeed;
	
	@Column(name = "increment_feed_volume", columnDefinition = "float4")
	private Float incrementFeedVolume;
	
	@Column(name = "increment_frequency")
	private Integer incrementFrequency;
	
	@Column(name="cyclic_feed_given",columnDefinition = "bool")
	private Boolean cyclicFeedGiven;
	
	@Column(name = "skipped_feeds")
	private Integer skippedFeeds;
	
	@Column(name = "unskipped_feeds")
	private Integer unskippedFeeds;
	
	@Column(name = "hco3_conc", columnDefinition = "Float4")
	private Float hco3Conc;
	
	@Column(name = "potassium_conc", columnDefinition = "Float4")
	private Float potassiumConc;
	
	@Column(name = "sodium_final_phosphorus_meg", columnDefinition = "Float4")
	private Float sodiumFinalPhosphorusMeg;
	
	@Column(name = "sodium_final_nahco3_meg", columnDefinition = "Float4")
	private Float sodiumFinalNaHCO3Meg;
	
	@Column(name = "sodium_final_meg", columnDefinition = "Float4")
	private Float sodiumFinalMeg;
	
	@Column(name = "sodium_text_message")
	private String sodiumTextMessage;
	
	@Transient
	private String nutritionalType;

	@Column(name = "normal_saline", columnDefinition = "Float4")
	private Float normalSaline;

	@Column(name = "kcl", columnDefinition = "Float4")
	private Float kcl;

	@Column(name = "ors", columnDefinition = "Float4")
	private Float ors;

	@Column(name = "normal_saline_feed",columnDefinition = "Float4")
	private Float normalSalineFeed;

	@Column(name = "kcl_feed",columnDefinition = "Float4")
	private Float kclFeed;

	@Column(name = "ors_feed",columnDefinition = "Float4")
	private Float orsFeed;

	@Column(name = "normal_saline_duration")
	private String normalSalineDuration;

	@Column(name = "kcl_duration")
	private String kclDuration;

	@Column(name = "ors_duration")
	private String orsDuration;

	public BabyfeedDetail() {
		this.feedduration = null;
		this.dextroseConcHigh = "";
		this.dextroseConcLow = "";
		this.aminoacidPnvolume = "10%";
		this.lipidPnvolume = "20%";
		this.isBolusGiven = false;
		this.isReadymadeSolutionGiven = false;
		this.isFeedGiven = true;
		this.totalenteraAdditivelvolume = Float.valueOf("0");
		this.pastBolusObject = new ArrayList<HashMap<String, Object>>();
		this.pastBolusTotalFeed = new Float("0");
		this.feedmethod_type = false;
		this.isIVFluidGiven = "";
		this.duration = 24;
		this.bloodProductGiven=false;
	}

	public Boolean getNpoDuetoFeedIntolerance() {
		return NpoDuetoFeedIntolerance;
	}

	public void setNpoDuetoFeedIntolerance(Boolean npoDuetoFeedIntolerance) {
		NpoDuetoFeedIntolerance = npoDuetoFeedIntolerance;
	}

	public String getEnteralNotes() {
		return enteralNotes;
	}

	public void setEnteralNotes(String enteralNotes) {
		this.enteralNotes = enteralNotes;
	}

	public List<String> getCauseListOfNPO() {
		return causeListOfNPO;
	}

	public void setCauseListOfNPO(List<String> causeListOfNPO) {
		this.causeListOfNPO = causeListOfNPO;
	}

	public Boolean getInfusion() {
		return isInfusion;
	}

	public void setInfusion(Boolean infusion) {
		isInfusion = infusion;
	}

	public Boolean getRevise() {
		return isRevise;
	}

	public void setRevise(Boolean revise) {
		isRevise = revise;
	}

	public String getCauseofnpo() {
		return causeofnpo;
	}

	public void setCauseofnpo(String causeofnpo) {
		this.causeofnpo = causeofnpo;
	}

	public Boolean getIsReadymadeSolutionGiven() {
		return isReadymadeSolutionGiven;
	}

	public void setIsReadymadeSolutionGiven(Boolean isReadymadeSolutionGiven) {
		this.isReadymadeSolutionGiven = isReadymadeSolutionGiven;
	}

	public Boolean getIsIVMedcineGiven() {
		return isIVMedcineGiven;
	}

	public void setIsIVMedcineGiven(Boolean isIVMedcineGiven) {
		this.isIVMedcineGiven = isIVMedcineGiven;
	}

	public String getIsIVFluidGiven() {
		return isIVFluidGiven;
	}

	public void setIsIVFluidGiven(String isIVFluidGiven) {
		this.isIVFluidGiven = isIVFluidGiven;
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

	public Timestamp getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(Timestamp entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public String getDilution() {
		return this.dilution;
	}

	public void setDilution(String dilution) {
		this.dilution = dilution;
	}

	public Boolean getEbm() {
		return this.ebm;
	}

	public void setEbm(Boolean ebm) {
		this.ebm = ebm;
	}

	public Float getEbmperdayml() {
		return this.ebmperdayml;
	}

	public void setEbmperdayml(Float ebmperdayml) {
		this.ebmperdayml = ebmperdayml;
	}

	public String getFeedduration() {
		return this.feedduration;
	}

	public void setFeedduration(String feedduration) {
		this.feedduration = feedduration;
	}

	public String getFeedmethod() {
		return this.feedmethod;
	}

	public void setFeedmethod(String feedmethod) {
		this.feedmethod = feedmethod;
	}

	public Boolean getFeedmethod_type() {
		return feedmethod_type;
	}

	public void setFeedmethod_type(Boolean feedmethod_type) {
		this.feedmethod_type = feedmethod_type;
	}

	public Float getAdditionalVolume() {
		return additionalVolume;
	}

	public void setAdditionalVolume(Float additionalVolume) {
		this.additionalVolume = additionalVolume;
	}

	public Float getAdditionalKCL() {
		return additionalKCL;
	}

	public void setAdditionalKCL(Float additionalKCL) {
		this.additionalKCL = additionalKCL;
	}

	public Float getAdditionalTotalVolume() {
		return additionalTotalVolume;
	}

	public void setAdditionalTotalVolume(Float additionalTotalVolume) {
		this.additionalTotalVolume = additionalTotalVolume;
	}

	public Float getAdditionalRate() {
		return additionalRate;
	}

	public void setAdditionalRate(Float additionalRate) {
		this.additionalRate = additionalRate;
	}

	public Float getAdditionalDuration() {
		return additionalDuration;
	}

	public void setAdditionalDuration(Float additionalDuration) {
		this.additionalDuration = additionalDuration;
	}

	public String getAdditionalPNType() {
		return additionalPNType;
	}

	public void setAdditionalPNType(String additionalPNType) {
		this.additionalPNType = additionalPNType;
	}

	public Boolean getIsRevise() {
		return isRevise;
	}

	public void setIsRevise(Boolean isRevise) {
		this.isRevise = isRevise;
	}

	public String getFeedtype() {
		return this.feedtype;
	}

	public void setFeedtype(String feedtype) {
		this.feedtype = feedtype;
	}

	public Float getFeedvolume() {
		return this.feedvolume;
	}

	public void setFeedvolume(Float feedvolume) {
		this.feedvolume = feedvolume;
	}

	public Boolean getFormulamilk() {
		return this.formulamilk;
	}

	public void setFormulamilk(Boolean formulamilk) {
		this.formulamilk = formulamilk;
	}

	public String getFormulamilkperdayml() {
		return this.formulamilkperdayml;
	}

	public void setFormulamilkperdayml(String formulamilkperdayml) {
		this.formulamilkperdayml = formulamilkperdayml;
	}

	public Boolean getHmf() {
		return this.hmf;
	}

	public void setHmf(Boolean hmf) {
		this.hmf = hmf;
	}

	public Boolean getHmfEbm() {
		return this.hmfEbm;
	}

	public void setHmfEbm(Boolean hmfEbm) {
		this.hmfEbm = hmfEbm;
	}

	public Float getHmfEbmPerdayml() {
		return this.hmfEbmPerdayml;
	}

	public void setHmfEbmPerdayml(Float hmfEbmPerdayml) {
		this.hmfEbmPerdayml = hmfEbmPerdayml;
	}

	public Float getHmfperdayml() {
		return this.hmfperdayml;
	}

	public void setHmfperdayml(Float hmfperdayml) {
		this.hmfperdayml = hmfperdayml;
	}

	public Float getIvfluidmlPerhr() {
		return this.ivfluidmlPerhr;
	}

	public void setIvfluidmlPerhr(Float ivfluidmlPerhr) {
		this.ivfluidmlPerhr = ivfluidmlPerhr;
	}

	public String getIvfluidrate() {
		return this.ivfluidrate;
	}

	public void setIvfluidrate(String ivfluidrate) {
		this.ivfluidrate = ivfluidrate;
	}

	public String getIvfluidtype() {
		return this.ivfluidtype;
	}

	public void setIvfluidtype(String ivfluidtype) {
		this.ivfluidtype = ivfluidtype;
	}

	public Boolean getLbfm() {
		return this.lbfm;
	}
	
	public String getReadymadeFluidType() {
		return readymadeFluidType;
	}

	public void setReadymadeFluidType(String readymadeFluidType) {
		this.readymadeFluidType = readymadeFluidType;
	}

	public void setLbfm(Boolean lbfm) {
		this.lbfm = lbfm;
	}

	public Float getLbfmperdayml() {
		return this.lbfmperdayml;
	}

	public void setLbfmperdayml(Float lbfmperdayml) {
		this.lbfmperdayml = lbfmperdayml;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getIsNormalTpn() {
		return isNormalTpn;
	}

	public void setIsNormalTpn(Boolean isNormalTpn) {
		this.isNormalTpn = isNormalTpn;
	}

	public Boolean getProductmilk() {
		return this.productmilk;
	}

	public void setProductmilk(Boolean productmilk) {
		this.productmilk = productmilk;
	}

	public Float getProductmilkperdayml() {
		return this.productmilkperdayml;
	}

	public void setProductmilkperdayml(Float productmilkperdayml) {
		this.productmilkperdayml = productmilkperdayml;
	}

	public String getTfi() {
		return this.tfi;
	}

	public void setTfi(String tfi) {
		this.tfi = tfi;
	}

	public Boolean getTfm() {
		return this.tfm;
	}

	public void setTfm(Boolean tfm) {
		this.tfm = tfm;
	}

	public Float getTfmperdayml() {
		return this.tfmperdayml;
	}

	public void setTfmperdayml(Float tfmperdayml) {
		this.tfmperdayml = tfmperdayml;
	}

	public String getTotalIntake() {
		return this.totalIntake;
	}

	public void setTotalIntake(String totalIntake) {
		this.totalIntake = totalIntake;
	}

	public Float getTotalIvfluids() {
		return this.totalIvfluids;
	}

	public void setTotalIvfluids(Float totalIvfluids) {
		this.totalIvfluids = totalIvfluids;
	}

	public Float getTotalfeedMlDay() {
		return this.totalfeedMlDay;
	}

	public void setTotalfeedMlDay(Float totalfeedMlDay) {
		this.totalfeedMlDay = totalfeedMlDay;
	}

	public Float getTotalfluidMlDay() {
		return this.totalfluidMlDay;
	}

	public void setTotalfluidMlDay(Float totalfluidMlDay) {
		this.totalfluidMlDay = totalfluidMlDay;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Float getTotalMedvolume() {
		return totalMedvolume;
	}

	public void setTotalMedvolume(Float totalMedvolume) {
		this.totalMedvolume = totalMedvolume;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public List getFeedmethodList() {
		return feedmethodList;
	}

	public void setFeedmethodList(List feedmethodList) {
		this.feedmethodList = feedmethodList;
	}

	public String getFeedtypeOther() {
		return feedtypeOther;
	}

	public void setFeedtypeOther(String feedtypeOther) {
		this.feedtypeOther = feedtypeOther;
	}

	public Boolean getIsFeedGiven() {
		return isFeedGiven;
	}

	public void setIsFeedGiven(Boolean isFeedGiven) {
		this.isFeedGiven = isFeedGiven;
	}

	public String getGirvalue() {
		return girvalue;
	}

	public void setGirvalue(String girvalue) {
		this.girvalue = girvalue;
	}

	public Float getTotalivfluidMlkgday() {
		return totalivfluidMlkgday;
	}

	public Float getCurrentDextroseConcentration() {
		return currentDextroseConcentration;
	}

	public String getDextroseConcHigh() {
		return dextroseConcHigh;
	}

	public Float getDextroseConcHighvolume() {
		return dextroseConcHighvolume;
	}

	public String getDextroseConcLow() {
		return dextroseConcLow;
	}

	public Float getDextroseConcLowvolume() {
		return dextroseConcLowvolume;
	}

	public void setTotalivfluidMlkgday(Float totalivfluidMlkgday) {
		this.totalivfluidMlkgday = totalivfluidMlkgday;
	}

	public void setCurrentDextroseConcentration(Float currentDextroseConcentration) {
		this.currentDextroseConcentration = currentDextroseConcentration;
	}

	public void setDextroseConcHigh(String dextroseConcHigh) {
		this.dextroseConcHigh = dextroseConcHigh;
	}

	public void setDextroseConcHighvolume(Float dextroseConcHighvolume) {
		this.dextroseConcHighvolume = dextroseConcHighvolume;
	}

	public void setDextroseConcLow(String dextroseConcLow) {
		this.dextroseConcLow = dextroseConcLow;
	}

	public void setDextroseConcLowvolume(Float dextroseConcLowvolume) {
		this.dextroseConcLowvolume = dextroseConcLowvolume;
	}

	public Boolean getIsBolusGiven() {
		return isBolusGiven;
	}

	public String getBolusType() {
		return bolusType;
	}

	public String getBolusMethod() {
		return bolusMethod;
	}

	public List<String> getBolusMethodList() {
		return bolusMethodList;
	}

	public Float getBolusVolume() {
		return bolusVolume;
	}

	public Integer getBolusFrequency() {
		return bolusFrequency;
	}

	public Float getBolusTotalFeed() {
		return bolusTotalFeed;
	}

	public void setIsBolusGiven(Boolean isBolusGiven) {
		this.isBolusGiven = isBolusGiven;
	}

	public void setBolusType(String bolusType) {
		this.bolusType = bolusType;
	}

	public void setBolusMethod(String bolusMethod) {
		this.bolusMethod = bolusMethod;
	}

	public void setBolusMethodList(List<String> bolusMethodList) {
		this.bolusMethodList = bolusMethodList;
	}

	public void setBolusVolume(Float bolusVolume) {
		this.bolusVolume = bolusVolume;
	}

	public void setBolusFrequency(Integer bolusFrequency) {
		this.bolusFrequency = bolusFrequency;
	}

	public void setBolusTotalFeed(Float bolusTotalFeed) {
		this.bolusTotalFeed = bolusTotalFeed;
	}

	public Integer getBolus_infusiontime() {
		return bolus_infusiontime;
	}

	public void setBolus_infusiontime(Integer bolus_infusiontime) {
		this.bolus_infusiontime = bolus_infusiontime;
	}

	public Float getBolus_rate() {
		return bolus_rate;
	}

	public void setBolus_rate(Float bolus_rate) {
		this.bolus_rate = bolus_rate;
	}

	public Boolean getBolus_executed() {
		return bolus_executed;
	}

	public void setBolus_executed(Boolean bolus_executed) {
		this.bolus_executed = bolus_executed;
	}

	public List<String> getFeedTypeList() {
		return feedTypeList;
	}

	public Float getAminoacidConc() {
		return aminoacidConc;
	}

	public String getAminoacidPnvolume() {
		return aminoacidPnvolume;
	}

	public Float getAminoacidTotal() {
		return aminoacidTotal;
	}

	public Float getCalciumTotal() {
		return calciumTotal;
	}

	public Float getCalciumVolume() {
		return calciumVolume;
	}

	public Float getCalsyrupTotal() {
		return calsyrupTotal;
	}

	public Float getCalsyrupVolume() {
		return calsyrupVolume;
	}

	public Boolean getEnteral() {
		return enteral;
	}

	public Float getHco3Total() {
		return hco3Total;
	}

	public Float getHco3Volume() {
		return hco3Volume;
	}

	public Float getIronTotal() {
		return ironTotal;
	}

	public Float getIronVolume() {
		return ironVolume;
	}

	public Boolean getIsenternalgiven() {
		return isenternalgiven;
	}

	public Boolean getIsparentalgiven() {
		return isparentalgiven;
	}

	public Float getLipidConc() {
		return lipidConc;
	}

	public String getLipidPnvolume() {
		return lipidPnvolume;
	}

	public Float getLipidTotal() {
		return lipidTotal;
	}

	public Float getMagnesiumTotal() {
		return magnesiumTotal;
	}

	public Float getMagnesiumVolume() {
		return magnesiumVolume;
	}

	public Float getMviTotal() {
		return mviTotal;
	}

	public Float getMviVolume() {
		return mviVolume;
	}

	public Boolean getParenteral() {
		return parenteral;
	}

	public Float getPhosphorousTotal() {
		return phosphorousTotal;
	}

	public Float getPhosphorousVolume() {
		return phosphorousVolume;
	}

	public Float getPotassiumTotal() {
		return potassiumTotal;
	}

	public Float getPotassiumVolume() {
		return potassiumVolume;
	}

	public Float getSodiumTotal() {
		return sodiumTotal;
	}

	public Float getSodiumVolume() {
		return sodiumVolume;
	}

	public Float getVitaminaTotal() {
		return vitaminaTotal;
	}

	public Float getVitaminaVolume() {
		return vitaminaVolume;
	}

	public Float getVitamindTotal() {
		return vitamindTotal;
	}

	public Float getVitamindVolume() {
		return vitamindVolume;
	}

	public void setFeedTypeList(List<String> feedTypeList) {
		this.feedTypeList = feedTypeList;
	}

	public void setAminoacidConc(Float aminoacidConc) {
		this.aminoacidConc = aminoacidConc;
	}

	public void setAminoacidPnvolume(String aminoacidPnvolume) {
		this.aminoacidPnvolume = aminoacidPnvolume;
	}

	public void setAminoacidTotal(Float aminoacidTotal) {
		this.aminoacidTotal = aminoacidTotal;
	}

	public void setCalciumTotal(Float calciumTotal) {
		this.calciumTotal = calciumTotal;
	}

	public void setCalciumVolume(Float calciumVolume) {
		this.calciumVolume = calciumVolume;
	}

	public void setCalsyrupTotal(Float calsyrupTotal) {
		this.calsyrupTotal = calsyrupTotal;
	}

	public void setCalsyrupVolume(Float calsyrupVolume) {
		this.calsyrupVolume = calsyrupVolume;
	}

	public void setEnteral(Boolean enteral) {
		this.enteral = enteral;
	}

	public void setHco3Total(Float hco3Total) {
		this.hco3Total = hco3Total;
	}

	public void setHco3Volume(Float hco3Volume) {
		this.hco3Volume = hco3Volume;
	}

	public void setIronTotal(Float ironTotal) {
		this.ironTotal = ironTotal;
	}

	public void setIronVolume(Float ironVolume) {
		this.ironVolume = ironVolume;
	}

	public void setIsenternalgiven(Boolean isenternalgiven) {
		this.isenternalgiven = isenternalgiven;
	}

	public void setIsparentalgiven(Boolean isparentalgiven) {
		this.isparentalgiven = isparentalgiven;
	}

	public void setLipidConc(Float lipidConc) {
		this.lipidConc = lipidConc;
	}

	public Float getTotalfeedMlPerKgDay() {
		return totalfeedMlPerKgDay;
	}

	public void setTotalfeedMlPerKgDay(Float totalfeedMlPerKgDay) {
		this.totalfeedMlPerKgDay = totalfeedMlPerKgDay;
	}

	public void setLipidPnvolume(String lipidPnvolume) {
		this.lipidPnvolume = lipidPnvolume;
	}

	public void setLipidTotal(Float lipidTotal) {
		this.lipidTotal = lipidTotal;
	}

	public void setMagnesiumTotal(Float magnesiumTotal) {
		this.magnesiumTotal = magnesiumTotal;
	}

	public void setMagnesiumVolume(Float magnesiumVolume) {
		this.magnesiumVolume = magnesiumVolume;
	}

	public void setMviTotal(Float mviTotal) {
		this.mviTotal = mviTotal;
	}

	public void setMviVolume(Float mviVolume) {
		this.mviVolume = mviVolume;
	}

	public void setParenteral(Boolean parenteral) {
		this.parenteral = parenteral;
	}

	public void setPhosphorousTotal(Float phosphorousTotal) {
		this.phosphorousTotal = phosphorousTotal;
	}

	public void setPhosphorousVolume(Float phosphorousVolume) {
		this.phosphorousVolume = phosphorousVolume;
	}

	public void setPotassiumTotal(Float potassiumTotal) {
		this.potassiumTotal = potassiumTotal;
	}

	public void setPotassiumVolume(Float potassiumVolume) {
		this.potassiumVolume = potassiumVolume;
	}

	public void setSodiumTotal(Float sodiumTotal) {
		this.sodiumTotal = sodiumTotal;
	}

	public void setSodiumVolume(Float sodiumVolume) {
		this.sodiumVolume = sodiumVolume;
	}

	public void setVitaminaTotal(Float vitaminaTotal) {
		this.vitaminaTotal = vitaminaTotal;
	}

	public void setVitaminaVolume(Float vitaminaVolume) {
		this.vitaminaVolume = vitaminaVolume;
	}

	public void setVitamindTotal(Float vitamindTotal) {
		this.vitamindTotal = vitamindTotal;
	}

	public void setVitamindVolume(Float vitamindVolume) {
		this.vitamindVolume = vitamindVolume;
	}

	public Boolean getAdditiveenteral() {
		return additiveenteral;
	}

	public void setAdditiveenteral(Boolean additiveenteral) {
		this.additiveenteral = additiveenteral;
	}

	public Float getWorkingWeight() {
		return workingWeight;
	}

	public void setWorkingWeight(Float workingWeight) {
		this.workingWeight = workingWeight;
	}

	public Float getTotalenteralvolume() {
		return totalenteralvolume;
	}

	public void setTotalenteralvolume(Float totalenteralvolume) {
		this.totalenteralvolume = totalenteralvolume;
	}

	public Float getTotalparenteralvolume() {
		return totalparenteralvolume;
	}

	public void setTotalparenteralvolume(Float totalparenteralvolume) {
		this.totalparenteralvolume = totalparenteralvolume;
	}

	public Boolean getAdditiveparenteral() {
		return additiveparenteral;
	}

	public void setAdditiveparenteral(Boolean additiveparenteral) {
		this.additiveparenteral = additiveparenteral;
	}

	public Float getDextroseVolumemlperday() {
		return dextroseVolumemlperday;
	}

	public void setDextroseVolumemlperday(Float dextroseVolumemlperday) {
		this.dextroseVolumemlperday = dextroseVolumemlperday;
	}

	public Float getTotalenteraAdditivelvolume() {
		return totalenteraAdditivelvolume;
	}

	public Float getTotalparenteralAdditivevolume() {
		return totalparenteralAdditivevolume;
	}

	public void setTotalenteraAdditivelvolume(Float totalenteraAdditivelvolume) {
		this.totalenteraAdditivelvolume = totalenteraAdditivelvolume;
	}

	public void setTotalparenteralAdditivevolume(Float totalparenteralAdditivevolume) {
		this.totalparenteralAdditivevolume = totalparenteralAdditivevolume;
	}

	public Float getDextroseNetConcentration() {
		return dextroseNetConcentration;
	}

	public void setDextroseNetConcentration(Float dextroseNetConcentration) {
		this.dextroseNetConcentration = dextroseNetConcentration;
	}

	public Float getOsmolarity() {
		return osmolarity;
	}

	public void setOsmolarity(Float osmolarity) {
		this.osmolarity = osmolarity;
	}

	public String getLibBreastFeed() {
		return libBreastFeed;
	}

	public List<String> getLibBreastFeedList() {
		return libBreastFeedList;
	}

	public void setLibBreastFeed(String libBreastFeed) {
		this.libBreastFeed = libBreastFeed;
	}

	public void setLibBreastFeedList(List<String> libBreastFeedList) {
		this.libBreastFeedList = libBreastFeedList;
	}

	public Boolean getIsAdditiveInENCalculation() {
		return isAdditiveInENCalculation;
	}

	public void setIsAdditiveInENCalculation(Boolean isAdditiveInENCalculation) {
		this.isAdditiveInENCalculation = isAdditiveInENCalculation;
	}

	public String getFeedMethodOther() {
		return feedMethodOther;
	}

	public String getLibBreastFeedOther() {
		return libBreastFeedOther;
	}

	public void setFeedMethodOther(String feedMethodOther) {
		this.feedMethodOther = feedMethodOther;
	}

	public void setLibBreastFeedOther(String libBreastFeedOther) {
		this.libBreastFeedOther = libBreastFeedOther;
	}

	public Float getPnProteinEnergyRatio() {
		return pnProteinEnergyRatio;
	}

	public void setPnProteinEnergyRatio(Float pnProteinEnergyRatio) {
		this.pnProteinEnergyRatio = pnProteinEnergyRatio;
	}

	public Float getPnCarbohydratesLipidsRatio() {
		return pnCarbohydratesLipidsRatio;
	}

	public void setPnCarbohydratesLipidsRatio(Float pnCarbohydratesLipidsRatio) {
		this.pnCarbohydratesLipidsRatio = pnCarbohydratesLipidsRatio;
	}

	public Float getEnProteinEnergyRatio() {
		return enProteinEnergyRatio;
	}

	public Float getEnCarbohydratesLipidsRatio() {
		return enCarbohydratesLipidsRatio;
	}

	public Float getTotalProteinEnergyRatio() {
		return totalProteinEnergyRatio;
	}

	public Float getTotalCarbohydratesLipidsRatio() {
		return totalCarbohydratesLipidsRatio;
	}

	public void setEnProteinEnergyRatio(Float enProteinEnergyRatio) {
		this.enProteinEnergyRatio = enProteinEnergyRatio;
	}

	public void setEnCarbohydratesLipidsRatio(Float enCarbohydratesLipidsRatio) {
		this.enCarbohydratesLipidsRatio = enCarbohydratesLipidsRatio;
	}

	public void setTotalProteinEnergyRatio(Float totalProteinEnergyRatio) {
		this.totalProteinEnergyRatio = totalProteinEnergyRatio;
	}

	public void setTotalCarbohydratesLipidsRatio(Float totalCarbohydratesLipidsRatio) {
		this.totalCarbohydratesLipidsRatio = totalCarbohydratesLipidsRatio;
	}

	public String getFeedText() {
		return feedText;
	}

	public void setFeedText(String feedText) {
		this.feedText = feedText;
	}

	public Boolean getIsAdLibGiven() {
		return isAdLibGiven;
	}

	public void setIsAdLibGiven(Boolean isAdLibGiven) {
		this.isAdLibGiven = isAdLibGiven;
	}

	public List<HashMap<String, Object>> getPastBolusObject() {
		return pastBolusObject;
	}

	public void setPastBolusObject(List<HashMap<String, Object>> pastBolusObject) {
		this.pastBolusObject = pastBolusObject;
	}

	public Float getPastBolusTotalFeed() {
		return pastBolusTotalFeed;
	}

	public void setPastBolusTotalFeed(Float pastBolusTotalFeed) {
		this.pastBolusTotalFeed = pastBolusTotalFeed;
	}

	public String getFeedTypeSecondary() {
		return feedTypeSecondary;
	}

	public List<String> getFeedTypeSecondaryList() {
		return feedTypeSecondaryList;
	}

	public void setFeedTypeSecondary(String feedTypeSecondary) {
		this.feedTypeSecondary = feedTypeSecondary;
	}

	public void setFeedTypeSecondaryList(List<String> feedTypeSecondaryList) {
		this.feedTypeSecondaryList = feedTypeSecondaryList;
	}

	public String getRemainingfluid() {
		return remainingfluid;
	}

	public String getRemainingfluidMlkg() {
		return remainingfluidMlkg;
	}

	public String getDextroseConc() {
		return dextroseConc;
	}

	public void setRemainingfluid(String remainingfluid) {
		this.remainingfluid = remainingfluid;
	}

	public void setRemainingfluidMlkg(String remainingfluidMlkg) {
		this.remainingfluidMlkg = remainingfluidMlkg;
	}

	public void setDextroseConc(String dextroseConc) {
		this.dextroseConc = dextroseConc;
	}

	public String getLipidType() {
		return lipidType;
	}

	public void setLipidType(String lipidType) {
		this.lipidType = lipidType;
	}

	public Float getCalsyrupFeed() {
		return calsyrupFeed;
	}

	public void setCalsyrupFeed(Float calsyrupFeed) {
		this.calsyrupFeed = calsyrupFeed;
	}

	public Integer getCalsyrupDuration() {
		return calsyrupDuration;
	}

	public Integer getIronDuration() {
		return ironDuration;
	}

	public Integer getVitaminaDuration() {
		return vitaminaDuration;
	}

	public Integer getOtherDuration() {
		return otherDuration;
	}

	public void setCalsyrupDuration(Integer calsyrupDuration) {
		this.calsyrupDuration = calsyrupDuration;
	}

	public void setIronDuration(Integer ironDuration) {
		this.ironDuration = ironDuration;
	}

	public void setVitaminaDuration(Integer vitaminaDuration) {
		this.vitaminaDuration = vitaminaDuration;
	}

	public void setOtherDuration(Integer otherDuration) {
		this.otherDuration = otherDuration;
	}

	public Float getIronFeed() {
		return ironFeed;
	}

	public void setIronFeed(Float ironFeed) {
		this.ironFeed = ironFeed;
	}

	public Float getVitaminaFeed() {
		return vitaminaFeed;
	}

	public void setVitaminaFeed(Float vitaminaFeed) {
		this.vitaminaFeed = vitaminaFeed;
	}

	public Float getOtherVolume() {
		return otherVolume;
	}

	public void setOtherVolume(Float otherVolume) {
		this.otherVolume = otherVolume;
	}

	public Float getOtherTotal() {
		return otherTotal;
	}

	public void setOtherTotal(Float otherTotal) {
		this.otherTotal = otherTotal;
	}

	public Float getOtherFeed() {
		return otherFeed;
	}

	public void setOtherFeed(Float otherFeed) {
		this.otherFeed = otherFeed;
	}

	public String getOtherAdditive() {
		return otherAdditive;
	}

	public void setOtherAdditive(String otherAdditive) {
		this.otherAdditive = otherAdditive;
	}

	public String getCalciumConcentrationType() {
		return calciumConcentrationType;
	}

	public void setCalciumConcentrationType(String calciumConcentrationType) {
		this.calciumConcentrationType = calciumConcentrationType;
	}

	public Boolean getIsInfusion() {
		return isInfusion;
	}

	public void setIsInfusion(Boolean isInfusion) {
		this.isInfusion = isInfusion;
	}

	public Float getVitaminDFeed() {
		return vitaminDFeed;
	}

	public Integer getVitaminDDuration() {
		return vitaminDDuration;
	}

	public void setVitaminDFeed(Float vitaminDFeed) {
		this.vitaminDFeed = vitaminDFeed;
	}

	public void setVitaminDDuration(Integer vitaminDDuration) {
		this.vitaminDDuration = vitaminDDuration;
	}

	public String getIsmakeDailyPN() {
		return ismakeDailyPN;
	}

	public Boolean getIsParenteralDextrose() {
		return isParenteralDextrose;
	}

	public void setIsmakeDailyPN(String ismakeDailyPN) {
		this.ismakeDailyPN = ismakeDailyPN;
	}

	public void setIsParenteralDextrose(Boolean isParenteralDextrose) {
		this.isParenteralDextrose = isParenteralDextrose;
	}

	public Float getTotalBloodProductVolume() {
		return totalBloodProductVolume;
	}

	public void setTotalBloodProductVolume(Float totalBloodProductVolume) {
		this.totalBloodProductVolume = totalBloodProductVolume;
	}

	public Float getTotalHeplockVolume() {
		return totalHeplockVolume;
	}

	public void setTotalHeplockVolume(Float totalHeplockVolume) {
		this.totalHeplockVolume = totalHeplockVolume;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getBloodProductMessage() {
		return bloodProductMessage;
	}

	public void setBloodProductMessage(String bloodProductMessage) {
		this.bloodProductMessage = bloodProductMessage;
	}

	public String getCalBrand() {
		return calBrand;
	}

	public void setCalBrand(String calBrand) {
		this.calBrand = calBrand;
	}

	public String getIronBrand() {
		return ironBrand;
	}

	public void setIronBrand(String ironBrand) {
		this.ironBrand = ironBrand;
	}

	public String getMultiVitaminBrand() {
		return multiVitaminBrand;
	}

	public void setMultiVitaminBrand(String multiVitaminBrand) {
		this.multiVitaminBrand = multiVitaminBrand;
	}

	public String getVitaminDBrand() {
		return vitaminDBrand;
	}

	public void setVitaminDBrand(String vitaminDBrand) {
		this.vitaminDBrand = vitaminDBrand;
	}

	public String getOtherBrand() {
		return otherBrand;
	}

	public void setOtherBrand(String otherBrand) {
		this.otherBrand = otherBrand;
	}

	public Float getReadymadeFluidRate() {
		return readymadeFluidRate;
	}

	public void setReadymadeFluidRate(Float readymadeFluidRate) {
		this.readymadeFluidRate = readymadeFluidRate;
	}

	public String getUnitOther() {
		return unitOther;
	}

	public void setUnitOther(String unitOther) {
		this.unitOther = unitOther;
	}

	public Boolean getIsStrengthOther() {
		return isStrengthOther;
	}

	public void setIsStrengthOther(Boolean isStrengthOther) {
		this.isStrengthOther = isStrengthOther;
	}

	public Float getReadymadeFluidVolume() {
		return readymadeFluidVolume;
	}

	public void setReadymadeFluidVolume(Float readymadeFluidVolume) {
		this.readymadeFluidVolume = readymadeFluidVolume;
	}

	public Float getReadymadeTotalFluidVolume() {
		return readymadeTotalFluidVolume;
	}

	public void setReadymadeTotalFluidVolume(Float readymadeTotalFluidVolume) {
		this.readymadeTotalFluidVolume = readymadeTotalFluidVolume;
	}

	public Float getSodiumMeg() {
		return sodiumMeg;
	}

	public void setSodiumMeg(Float sodiumMeg) {
		this.sodiumMeg = sodiumMeg;
	}

	public Boolean getStopPN() {
		return stopPN;
	}

	public void setStopPN(Boolean stopPN) {
		this.stopPN = stopPN;
	}

	public Boolean getStopAdditionalPN() {
		return stopAdditionalPN;
	}

	public void setStopAdditionalPN(Boolean stopAdditionalPN) {
		this.stopAdditionalPN = stopAdditionalPN;
	}

	public Integer getMctDuration() {
		return mctDuration;
	}

	public void setMctDuration(Integer mctDuration) {
		this.mctDuration = mctDuration;
	}

	public Float getMctTotal() {
		return mctTotal;
	}

	public void setMctTotal(Float mctTotal) {
		this.mctTotal = mctTotal;
	}

	public Float getMctVolume() {
		return mctVolume;
	}

	public void setMctVolume(Float mctVolume) {
		this.mctVolume = mctVolume;
	}

	public Float getMctFeed() {
		return mctFeed;
	}

	public void setMctFeed(Float mctFeed) {
		this.mctFeed = mctFeed;
	}

	public String getMctBrand() {
		return mctBrand;
	}

	public void setMctBrand(String mctBrand) {
		this.mctBrand = mctBrand;
	}

	public Boolean getIsGirCalculate() {
		return isGirCalculate;
	}

	public void setIsGirCalculate(Boolean isGirCalculate) {
		this.isGirCalculate = isGirCalculate;
	}

	public Boolean getIsAminoRate() {
		return isAminoRate;
	}

	public void setIsAminoRate(Boolean isAminoRate) {
		this.isAminoRate = isAminoRate;
	}

	public List<String> getFluidTypeList() {
		return fluidTypeList;
	}

	public void setFluidTypeList(List<String> fluidTypeList) {
		this.fluidTypeList = fluidTypeList;
	}

	public String getReadymadeDextroseConcLowType() {
		return readymadeDextroseConcLowType;
	}

	public void setReadymadeDextroseConcLowType(String readymadeDextroseConcLowType) {
		this.readymadeDextroseConcLowType = readymadeDextroseConcLowType;
	}

	public String getReadymadeDextroseConcHighType() {
		return readymadeDextroseConcHighType;
	}

	public void setReadymadeDextroseConcHighType(String readymadeDextroseConcHighType) {
		this.readymadeDextroseConcHighType = readymadeDextroseConcHighType;
	}

	public Float getReadymadeDextroseConcentration() {
		return readymadeDextroseConcentration;
	}

	public void setReadymadeDextroseConcentration(Float readymadeDextroseConcentration) {
		this.readymadeDextroseConcentration = readymadeDextroseConcentration;
	}

	public Float getReadymadeDextroseConcLowvolume() {
		return readymadeDextroseConcLowvolume;
	}

	public void setReadymadeDextroseConcLowvolume(Float readymadeDextroseConcLowvolume) {
		this.readymadeDextroseConcLowvolume = readymadeDextroseConcLowvolume;
	}

	public Float getReadymadeDextroseConcHighvolume() {
		return readymadeDextroseConcHighvolume;
	}

	public void setReadymadeDextroseConcHighvolume(Float readymadeDextroseConcHighvolume) {
		this.readymadeDextroseConcHighvolume = readymadeDextroseConcHighvolume;
	}

	public Integer getReadymadeDextroseConcLow() {
		return readymadeDextroseConcLow;
	}

	public void setReadymadeDextroseConcLow(Integer readymadeDextroseConcLow) {
		this.readymadeDextroseConcLow = readymadeDextroseConcLow;
	}

	public Integer getReadymadeDextroseConcHigh() {
		return readymadeDextroseConcHigh;
	}

	public void setReadymadeDextroseConcHigh(Integer readymadeDextroseConcHigh) {
		this.readymadeDextroseConcHigh = readymadeDextroseConcHigh;
	}

	public String getReadymadeFluidTypeList() {
		return readymadeFluidTypeList;
	}

	public void setReadymadeFluidTypeList(String readymadeFluidTypeList) {
		this.readymadeFluidTypeList = readymadeFluidTypeList;
	}

	public String getEnFeedStr() {
		return enFeedStr;
	}

	public void setEnFeedStr(String enFeedStr) {
		this.enFeedStr = enFeedStr;
	}

	public String getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(String formulaType) {
		this.formulaType = formulaType;
	}

	public String getPrimaryFeed() {
		return primaryFeed;
	}

	public void setPrimaryFeed(String primaryFeed) {
		this.primaryFeed = primaryFeed;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Boolean getSkipFeed() {
		return skipFeed;
	}

	public void setSkipFeed(Boolean skipFeed) {
		this.skipFeed = skipFeed;
	}

	public Boolean getBloodProductGiven() {
		return bloodProductGiven;
	}

	public void setBloodProductGiven(Boolean bloodProductGiven) {
		this.bloodProductGiven = bloodProductGiven;
	}

	public Boolean getIncrementFeedGiven() {
		return incrementFeedGiven;
	}

	public void setIncrementFeedGiven(Boolean incrementFeedGiven) {
		this.incrementFeedGiven = incrementFeedGiven;
	}

	public Float getTargetFeed() {
		return targetFeed;
	}

	public void setTargetFeed(Float targetFeed) {
		this.targetFeed = targetFeed;
	}

	public Float getStartFeed() {
		return startFeed;
	}

	public void setStartFeed(Float startFeed) {
		this.startFeed = startFeed;
	}

	public Float getIncrementFeedVolume() {
		return incrementFeedVolume;
	}

	public void setIncrementFeedVolume(Float incrementFeedVolume) {
		this.incrementFeedVolume = incrementFeedVolume;
	}

	public Integer getIncrementFrequency() {
		return incrementFrequency;
	}

	public void setIncrementFrequency(Integer incrementFrequency) {
		this.incrementFrequency = incrementFrequency;
	}

	public Boolean getCyclicFeedGiven() {
		return cyclicFeedGiven;
	}

	public void setCyclicFeedGiven(Boolean cyclicFeedGiven) {
		this.cyclicFeedGiven = cyclicFeedGiven;
	}

	public Integer getSkippedFeeds() {
		return skippedFeeds;
	}

	public void setSkippedFeeds(Integer skippedFeeds) {
		this.skippedFeeds = skippedFeeds;
	}

	public Integer getUnskippedFeeds() {
		return unskippedFeeds;
	}

	public void setUnskippedFeeds(Integer unskippedFeeds) {
		this.unskippedFeeds = unskippedFeeds;
	}

	public Float getHco3Conc() {
		return hco3Conc;
	}

	public void setHco3Conc(Float hco3Conc) {
		this.hco3Conc = hco3Conc;
	}

	public Float getPotassiumConc() {
		return potassiumConc;
	}

	public void setPotassiumConc(Float potassiumConc) {
		this.potassiumConc = potassiumConc;
	}

	public Float getSodiumFinalPhosphorusMeg() {
		return sodiumFinalPhosphorusMeg;
	}

	public void setSodiumFinalPhosphorusMeg(Float sodiumFinalPhosphorusMeg) {
		this.sodiumFinalPhosphorusMeg = sodiumFinalPhosphorusMeg;
	}

	public Float getSodiumFinalNaHCO3Meg() {
		return sodiumFinalNaHCO3Meg;
	}

	public void setSodiumFinalNaHCO3Meg(Float sodiumFinalNaHCO3Meg) {
		this.sodiumFinalNaHCO3Meg = sodiumFinalNaHCO3Meg;
	}

	public Float getSodiumFinalMeg() {
		return sodiumFinalMeg;
	}

	public void setSodiumFinalMeg(Float sodiumFinalMeg) {
		this.sodiumFinalMeg = sodiumFinalMeg;
	}

	public String getSodiumTextMessage() {
		return sodiumTextMessage;
	}

	public void setSodiumTextMessage(String sodiumTextMessage) {
		this.sodiumTextMessage = sodiumTextMessage;
	}

	public String getNutritionalType() {
		return nutritionalType;
	}

	public void setNutritionalType(String nutritionalType) {
		this.nutritionalType = nutritionalType;
	}

	public Float getNormalSaline() {
		return normalSaline;
	}

	public void setNormalSaline(Float normalSaline) {
		this.normalSaline = normalSaline;
	}

	public Float getKcl() {
		return kcl;
	}

	public void setKcl(Float kcl) {
		this.kcl = kcl;
	}

	public Float getOrs() {
		return ors;
	}

	public void setOrs(Float ors) {
		this.ors = ors;
	}

	public String getNormalSalineDuration() {
		return normalSalineDuration;
	}

	public void setNormalSalineDuration(String normalSalineDuration) {
		this.normalSalineDuration = normalSalineDuration;
	}

	public String getKclDuration() {
		return kclDuration;
	}

	public void setKclDuration(String kclDuration) {
		this.kclDuration = kclDuration;
	}

	public String getOrsDuration() {
		return orsDuration;
	}

	public void setOrsDuration(String orsDuration) {
		this.orsDuration = orsDuration;
	}

	public Float getNormalSalineFeed() {
		return normalSalineFeed;
	}

	public void setNormalSalineFeed(Float normalSalineFeed) {
		this.normalSalineFeed = normalSalineFeed;
	}

	public Float getKclFeed() {
		return kclFeed;
	}

	public void setKclFeed(Float kclFeed) {
		this.kclFeed = kclFeed;
	}

	public Float getOrsFeed() {
		return orsFeed;
	}

	public void setOrsFeed(Float orsFeed) {
		this.orsFeed = orsFeed;
	}
}
