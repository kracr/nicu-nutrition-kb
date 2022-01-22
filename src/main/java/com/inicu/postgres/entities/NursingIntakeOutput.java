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
import javax.persistence.Transient;

/**
 * The persistent class for the nursing_intake_output database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "nursing_intake_output")
@NamedQuery(name = "NursingIntakeOutput.findAll", query = "SELECT s FROM NursingIntakeOutput s")
public class NursingIntakeOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursing_intakeid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private String babyfeedid;

	@Column(name = "nursing_entry_time")
	private String nursingEntryTime;

	@Column(name = "primary_feed_type")
	private String primaryFeedType;

	@Column(name = "primary_feed_value", columnDefinition = "Float4")
	private Float primaryFeedValue;

	@Column(name = "formula_type")
	private String formulaType;

	@Column(name = "formula_value", columnDefinition = "Float4")
	private Float formulaValue;

	@Column(columnDefinition = "Float4")
	private Float lipid_total_volume;

	@Column(columnDefinition = "Float4")
	private Float lipid_delivered;

	@Column(columnDefinition = "Float4")
	private Float lipid_remaining;

	@Column(columnDefinition = "Float4")
	private Float tpn_total_volume;

	@Column(columnDefinition = "Float4")
	private Float tpn_delivered;

	@Column(columnDefinition = "Float4")
	private Float tpn_remaining;

	@Column(columnDefinition = "bool")
	Boolean bolus_executed;

	@Column(name = "abdomen_girth")
	private String abdomenGirth;

	@Column(name = "gastric_aspirate")
	private String gastricAspirate;

	@Column(name = "aspirate_color")
	private String aspirateColor;

	@Column(name = "aspirate_color_other")
	private String aspirateColorOther;

	@Column(name = "urine_passed", columnDefinition = "bool")
	private Boolean urinePassed;

	private String urine;

	@Column(name = "stool_passed", columnDefinition = "bool")
	private Boolean stoolPassed;

	private String stool;
	
	@Column(name = "stool_other")
	private String stoolOther;

	private String vomit;

	private String drain;

	private String others;

	private String route;
	
	@Column(name = "site_cannula")
	private String siteCannula;
	
	@Column(name = "appearance_other")
	private String appearanceOther;

	private String appearance;
	
	@Column(name = "bolus_type")
	private String bolusType;
	
	@Column(name = "bolus_total_feed", columnDefinition = "Float4")
	private Float bolusTotalFeed;
	
	@Column(name = "bolus_remaining_feed", columnDefinition = "Float4")
	private Float bolusRemainingFeed;
	
	@Column(name = "bolus_delivered_feed", columnDefinition = "Float4")
	private Float bolusDeliveredFeed;
	
	@Column(name = "calcium_total_feed", columnDefinition = "Float4")
	private Float calciumTotalFeed;
	
	@Column(name = "calcium_remaining_feed", columnDefinition = "Float4")
	private Float calciumRemainingFeed;
	
	@Column(name = "calcium_delivered_feed", columnDefinition = "Float4")
	private Float calciumDeliveredFeed;
	
	@Column(name = "additional_pn_total_feed", columnDefinition = "Float4")
	private Float additionalPNTotalFeed;
	
	@Column(name = "additional_pn_remaining_feed", columnDefinition = "Float4")
	private Float additionalPNRemainingFeed;
	
	@Column(name = "additional_pn_delivered_feed", columnDefinition = "Float4")
	private Float additionalPNDeliveredFeed;
	
	@Column(name = "amino_total_feed", columnDefinition = "Float4")
	private Float aminoTotalFeed;
	
	@Column(name = "amino_remaining_feed", columnDefinition = "Float4")
	private Float aminoRemainingFeed;
	
	@Column(name = "amino_delivered_feed", columnDefinition = "Float4")
	private Float aminoDeliveredFeed;
	
	@Column(name = "readymade_type")
	private String readymadeType;
	
	@Column(name = "readymade_total_feed", columnDefinition = "Float4")
	private Float readymadeTotalFeed;
	
	@Column(name = "readymade_remaining_feed", columnDefinition = "Float4")
	private Float readymadeRemainingFeed;
	
	@Column(name = "readymade_delivered_feed", columnDefinition = "Float4")
	private Float readymadeDeliveredFeed;

	@Column(name = "actual_feed", columnDefinition = "Float4")
	private Float actualFeed;

	@Transient
	private String selectedPrimaryFeedType;

	private Timestamp entry_timestamp;

	@Column(name = "vomit_passed", columnDefinition = "bool")
	private Boolean vomitPassed;

	@Column(name = "vomit_color")
	private String vomitColor;

	@Column(name = "vomit_color_other")
	private String vomitColorOther;

	@Column(name = "output_comment")
	private String outputComment;

	@Column(name = "enteral_comment")
	private String enteralComment;

	@Column(name = "parenteral_comment")
	private String parenteralComment;

	@Column(name = "calcium_volume", columnDefinition = "Float4")
	private Float calciumVolume;

	@Column(name = "calcium_comment")
	private String calciumComment;

	@Column(name = "iron_volume", columnDefinition = "Float4")
	private Float ironVolume;

	@Column(name = "iron_comment")
	private String ironComment;

	@Column(name = "mv_volume", columnDefinition = "Float4")
	private Float mvVolume;

	@Column(name = "mv_comment")
	private String mvComment;
	
	@Column(name = "vitamind_volume", columnDefinition = "Float4")
	private Float vitamindVolume;

	@Column(name = "vitamind_comment")
	private String vitamindComment;
	
	@Column(name = "mct_volume", columnDefinition = "Float4")
	private Float mctVolume;

	@Column(name = "mct_comment")
	private String mctComment;

	@Column(name = "normal_saline", columnDefinition = "Float4")
	private Float normalSaline;

	@Column(name = "normal_saline_comments")
	private String normalSalineComments;

	@Column(name = "kcl", columnDefinition = "Float4")
	private Float kcl;

	@Column(name = "kcl_comments")
	private String kclComments;

	@Column(name = "ors", columnDefinition = "Float4")
	private Float ors;

	@Column(name = "ors_comments")
	private String orsComments;

	@Column(name = "other_additive")
	private String otherAdditive;

	@Column(name = "other_additive_volume", columnDefinition = "Float4")
	private Float otherAdditiveVolume;

	@Column(name = "other_additive_comment")
	private String otherAdditiveComment;
	
	@Column(name = "medicineDay")
	private Integer medicineDay;

	@Column(name="is_feed_skipped", columnDefinition = "bool")
	private Boolean isFeedSkipped;

	@Column(name = "stoma")
	private String stoma;

	@Column(name = "stoma_color")
	private String stomaColor;
	
	private String episodeid;

	@Column(name = "diaper_weight", columnDefinition = "float4")
	private Float diaperWeight;

	@Column(name = "stool_consistency")
	private String stoolConsistency;

	public NursingIntakeOutput() {
		super();
		this.route = "Tube Feed";
	}

	public Long getNursing_intakeid() {
		return nursing_intakeid;
	}

	public void setNursing_intakeid(Long nursing_intakeid) {
		this.nursing_intakeid = nursing_intakeid;
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

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getBabyfeedid() {
		return babyfeedid;
	}

	public void setBabyfeedid(String babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public String getNursingEntryTime() {
		return nursingEntryTime;
	}

	public void setNursingEntryTime(String nursingEntryTime) {
		this.nursingEntryTime = nursingEntryTime;
	}

	public String getPrimaryFeedType() {
		return primaryFeedType;
	}

	public void setPrimaryFeedType(String primaryFeedType) {
		this.primaryFeedType = primaryFeedType;
	}

	public Float getPrimaryFeedValue() {
		return primaryFeedValue;
	}

	public void setPrimaryFeedValue(Float primaryFeedValue) {
		this.primaryFeedValue = primaryFeedValue;
	}

	public String getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(String formulaType) {
		this.formulaType = formulaType;
	}

	public Float getFormulaValue() {
		return formulaValue;
	}

	public void setFormulaValue(Float formulaValue) {
		this.formulaValue = formulaValue;
	}

	public Float getLipid_total_volume() {
		return lipid_total_volume;
	}

	public void setLipid_total_volume(Float lipid_total_volume) {
		this.lipid_total_volume = lipid_total_volume;
	}

	public Float getLipid_delivered() {
		return lipid_delivered;
	}

	public void setLipid_delivered(Float lipid_delivered) {
		this.lipid_delivered = lipid_delivered;
	}

	public Float getLipid_remaining() {
		return lipid_remaining;
	}

	public void setLipid_remaining(Float lipid_remaining) {
		this.lipid_remaining = lipid_remaining;
	}

	public Float getTpn_total_volume() {
		return tpn_total_volume;
	}

	public void setTpn_total_volume(Float tpn_total_volume) {
		this.tpn_total_volume = tpn_total_volume;
	}

	public Float getTpn_delivered() {
		return tpn_delivered;
	}

	public void setTpn_delivered(Float tpn_delivered) {
		this.tpn_delivered = tpn_delivered;
	}

	public Float getTpn_remaining() {
		return tpn_remaining;
	}

	public void setTpn_remaining(Float tpn_remaining) {
		this.tpn_remaining = tpn_remaining;
	}

	public Boolean getBolus_executed() {
		return bolus_executed;
	}

	public void setBolus_executed(Boolean bolus_executed) {
		this.bolus_executed = bolus_executed;
	}

	public String getAbdomenGirth() {
		return abdomenGirth;
	}

	public void setAbdomenGirth(String abdomenGirth) {
		this.abdomenGirth = abdomenGirth;
	}

	public String getGastricAspirate() {
		return gastricAspirate;
	}

	public void setGastricAspirate(String gastricAspirate) {
		this.gastricAspirate = gastricAspirate;
	}

	public String getAspirateColor() {
		return aspirateColor;
	}

	public void setAspirateColor(String aspirateColor) {
		this.aspirateColor = aspirateColor;
	}

	public String getAspirateColorOther() {
		return aspirateColorOther;
	}

	public void setAspirateColorOther(String aspirateColorOther) {
		this.aspirateColorOther = aspirateColorOther;
	}

	public Boolean getUrinePassed() {
		return urinePassed;
	}

	public void setUrinePassed(Boolean urinePassed) {
		this.urinePassed = urinePassed;
	}

	public String getUrine() {
		return urine;
	}

	public void setUrine(String urine) {
		this.urine = urine;
	}

	public Boolean getStoolPassed() {
		return stoolPassed;
	}

	public void setStoolPassed(Boolean stoolPassed) {
		this.stoolPassed = stoolPassed;
	}

	public String getStool() {
		return stool;
	}

	public void setStool(String stool) {
		this.stool = stool;
	}

	public String getStoolOther() {
		return stoolOther;
	}

	public void setStoolOther(String stoolOther) {
		this.stoolOther = stoolOther;
	}

	public String getVomit() {
		return vomit;
	}

	public void setVomit(String vomit) {
		this.vomit = vomit;
	}

	public String getDrain() {
		return drain;
	}

	public void setDrain(String drain) {
		this.drain = drain;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public Float getActualFeed() {
		return actualFeed;
	}

	public void setActualFeed(Float actualFeed) {
		this.actualFeed = actualFeed;
	}

	public String getSelectedPrimaryFeedType() {
		return selectedPrimaryFeedType;
	}

	public void setSelectedPrimaryFeedType(String selectedPrimaryFeedType) {
		this.selectedPrimaryFeedType = selectedPrimaryFeedType;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Timestamp getEntry_timestamp() {
		return entry_timestamp;
	}

	public void setEntry_timestamp(Timestamp entry_timestamp) {
		this.entry_timestamp = entry_timestamp;
	}

	public Boolean getVomitPassed() {
		return vomitPassed;
	}

	public void setVomitPassed(Boolean vomitPassed) {
		this.vomitPassed = vomitPassed;
	}

	public String getVomitColor() {
		return vomitColor;
	}

	public void setVomitColor(String vomitColor) {
		this.vomitColor = vomitColor;
	}

	public String getVomitColorOther() {
		return vomitColorOther;
	}

	public void setVomitColorOther(String vomitColorOther) {
		this.vomitColorOther = vomitColorOther;
	}

	public String getOutputComment() {
		return outputComment;
	}

	public void setOutputComment(String outputComment) {
		this.outputComment = outputComment;
	}

	public String getEnteralComment() {
		return enteralComment;
	}

	public void setEnteralComment(String enteralComment) {
		this.enteralComment = enteralComment;
	}

	public String getParenteralComment() {
		return parenteralComment;
	}

	public void setParenteralComment(String parenteralComment) {
		this.parenteralComment = parenteralComment;
	}

	public Float getCalciumVolume() {
		return calciumVolume;
	}

	public void setCalciumVolume(Float calciumVolume) {
		this.calciumVolume = calciumVolume;
	}

	public String getCalciumComment() {
		return calciumComment;
	}

	public void setCalciumComment(String calciumComment) {
		this.calciumComment = calciumComment;
	}

	public Float getIronVolume() {
		return ironVolume;
	}

	public void setIronVolume(Float ironVolume) {
		this.ironVolume = ironVolume;
	}

	public String getIronComment() {
		return ironComment;
	}

	public void setIronComment(String ironComment) {
		this.ironComment = ironComment;
	}

	public Float getMvVolume() {
		return mvVolume;
	}

	public void setMvVolume(Float mvVolume) {
		this.mvVolume = mvVolume;
	}

	public String getMvComment() {
		return mvComment;
	}

	public void setMvComment(String mvComment) {
		this.mvComment = mvComment;
	}

	public String getOtherAdditive() {
		return otherAdditive;
	}

	public void setOtherAdditive(String otherAdditive) {
		this.otherAdditive = otherAdditive;
	}

	public Float getOtherAdditiveVolume() {
		return otherAdditiveVolume;
	}

	public void setOtherAdditiveVolume(Float otherAdditiveVolume) {
		this.otherAdditiveVolume = otherAdditiveVolume;
	}

	public String getOtherAdditiveComment() {
		return otherAdditiveComment;
	}

	public void setOtherAdditiveComment(String otherAdditiveComment) {
		this.otherAdditiveComment = otherAdditiveComment;
	}

	public String getBolusType() {
		return bolusType;
	}

	public void setBolusType(String bolusType) {
		this.bolusType = bolusType;
	}

	public Float getBolusTotalFeed() {
		return bolusTotalFeed;
	}

	public void setBolusTotalFeed(Float bolusTotalFeed) {
		this.bolusTotalFeed = bolusTotalFeed;
	}

	public Float getBolusRemainingFeed() {
		return bolusRemainingFeed;
	}

	public void setBolusRemainingFeed(Float bolusRemainingFeed) {
		this.bolusRemainingFeed = bolusRemainingFeed;
	}

	public Float getBolusDeliveredFeed() {
		return bolusDeliveredFeed;
	}

	public void setBolusDeliveredFeed(Float bolusDeliveredFeed) {
		this.bolusDeliveredFeed = bolusDeliveredFeed;
	}

	public String getReadymadeType() {
		return readymadeType;
	}

	public Float getReadymadeTotalFeed() {
		return readymadeTotalFeed;
	}

	public Float getReadymadeRemainingFeed() {
		return readymadeRemainingFeed;
	}

	public Float getReadymadeDeliveredFeed() {
		return readymadeDeliveredFeed;
	}

	public void setReadymadeType(String readymadeType) {
		this.readymadeType = readymadeType;
	}

	public void setReadymadeTotalFeed(Float readymadeTotalFeed) {
		this.readymadeTotalFeed = readymadeTotalFeed;
	}

	public void setReadymadeRemainingFeed(Float readymadeRemainingFeed) {
		this.readymadeRemainingFeed = readymadeRemainingFeed;
	}

	public void setReadymadeDeliveredFeed(Float readymadeDeliveredFeed) {
		this.readymadeDeliveredFeed = readymadeDeliveredFeed;
	}

	public Float getCalciumTotalFeed() {
		return calciumTotalFeed;
	}

	public Float getCalciumRemainingFeed() {
		return calciumRemainingFeed;
	}

	public Float getCalciumDeliveredFeed() {
		return calciumDeliveredFeed;
	}

	public void setCalciumTotalFeed(Float calciumTotalFeed) {
		this.calciumTotalFeed = calciumTotalFeed;
	}

	public void setCalciumRemainingFeed(Float calciumRemainingFeed) {
		this.calciumRemainingFeed = calciumRemainingFeed;
	}

	public void setCalciumDeliveredFeed(Float calciumDeliveredFeed) {
		this.calciumDeliveredFeed = calciumDeliveredFeed;
	}

	public Float getVitamindVolume() {
		return vitamindVolume;
	}

	public void setVitamindVolume(Float vitamindVolume) {
		this.vitamindVolume = vitamindVolume;
	}

	public String getVitamindComment() {
		return vitamindComment;
	}

	public void setVitamindComment(String vitamindComment) {
		this.vitamindComment = vitamindComment;
	}

	public Float getAdditionalPNTotalFeed() {
		return additionalPNTotalFeed;
	}

	public void setAdditionalPNTotalFeed(Float additionalPNTotalFeed) {
		this.additionalPNTotalFeed = additionalPNTotalFeed;
	}

	public Float getAdditionalPNRemainingFeed() {
		return additionalPNRemainingFeed;
	}

	public void setAdditionalPNRemainingFeed(Float additionalPNRemainingFeed) {
		this.additionalPNRemainingFeed = additionalPNRemainingFeed;
	}

	public Float getAdditionalPNDeliveredFeed() {
		return additionalPNDeliveredFeed;
	}

	public void setAdditionalPNDeliveredFeed(Float additionalPNDeliveredFeed) {
		this.additionalPNDeliveredFeed = additionalPNDeliveredFeed;
	}

	public Float getAminoTotalFeed() {
		return aminoTotalFeed;
	}

	public void setAminoTotalFeed(Float aminoTotalFeed) {
		this.aminoTotalFeed = aminoTotalFeed;
	}

	public Float getAminoRemainingFeed() {
		return aminoRemainingFeed;
	}

	public void setAminoRemainingFeed(Float aminoRemainingFeed) {
		this.aminoRemainingFeed = aminoRemainingFeed;
	}

	public Float getAminoDeliveredFeed() {
		return aminoDeliveredFeed;
	}

	public void setAminoDeliveredFeed(Float aminoDeliveredFeed) {
		this.aminoDeliveredFeed = aminoDeliveredFeed;
	}

	public Float getMctVolume() {
		return mctVolume;
	}

	public void setMctVolume(Float mctVolume) {
		this.mctVolume = mctVolume;
	}

	public String getMctComment() {
		return mctComment;
	}

	public void setMctComment(String mctComment) {
		this.mctComment = mctComment;
	}

	public String getSiteCannula() {
		return siteCannula;
	}

	public void setSiteCannula(String siteCannula) {
		this.siteCannula = siteCannula;
	}

	public String getAppearanceOther() {
		return appearanceOther;
	}

	public void setAppearanceOther(String appearanceOther) {
		this.appearanceOther = appearanceOther;
	}

	public String getAppearance() {
		return appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}
	

	public Integer getMedicineDay() {
		return medicineDay;
	}

	public void setMedicineDay(Integer medicineDay) {
		this.medicineDay = medicineDay;
	}

	public Boolean getFeedSkipped() {
		return isFeedSkipped;
	}

	public void setFeedSkipped(Boolean feedSkipped) {
		isFeedSkipped = feedSkipped;
	}

	public String getStoma() {
		return stoma;
	}

	public void setStoma(String stoma) {
		this.stoma = stoma;
	}

	public String getStomaColor() {
		return stomaColor;
	}

	public void setStomaColor(String stomaColor) {
		this.stomaColor = stomaColor;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Float getDiaperWeight() {
		return diaperWeight;
	}

	public void setDiaperWeight(Float diaperWeight) {
		this.diaperWeight = diaperWeight;
	}

	public String getStoolConsistency() {
		return stoolConsistency;
	}

	public void setStoolConsistency(String stoolConsistency) {
		this.stoolConsistency = stoolConsistency;
	}

	public Float getNormalSaline() {
		return normalSaline;
	}

	public void setNormalSaline(Float normalSaline) {
		this.normalSaline = normalSaline;
	}

	public String getNormalSalineComments() {
		return normalSalineComments;
	}

	public void setNormalSalineComments(String normalSalineComments) {
		this.normalSalineComments = normalSalineComments;
	}

	public Float getKcl() {
		return kcl;
	}

	public void setKcl(Float kcl) {
		this.kcl = kcl;
	}

	public String getKclComments() {
		return kclComments;
	}

	public void setKclComments(String kclComments) {
		this.kclComments = kclComments;
	}

	public Float getOrs() {
		return ors;
	}

	public void setOrs(Float ors) {
		this.ors = ors;
	}

	public String getOrsComments() {
		return orsComments;
	}

	public void setOrsComments(String orsComments) {
		this.orsComments = orsComments;
	}

	@Override
	public String toString() {
		return "NursingIntakeOutput{" +
				"nursing_intakeid=" + nursing_intakeid +
				", creationtime=" + creationtime +
				", modificationtime=" + modificationtime +
				", uhid='" + uhid + '\'' +
				", loggeduser='" + loggeduser + '\'' +
				", babyfeedid='" + babyfeedid + '\'' +
				", nursingEntryTime='" + nursingEntryTime + '\'' +
				", primaryFeedType='" + primaryFeedType + '\'' +
				", primaryFeedValue=" + primaryFeedValue +
				", formulaType='" + formulaType + '\'' +
				", formulaValue=" + formulaValue +
				", lipid_total_volume=" + lipid_total_volume +
				", lipid_delivered=" + lipid_delivered +
				", lipid_remaining=" + lipid_remaining +
				", tpn_total_volume=" + tpn_total_volume +
				", tpn_delivered=" + tpn_delivered +
				", tpn_remaining=" + tpn_remaining +
				", bolus_executed=" + bolus_executed +
				", abdomenGirth='" + abdomenGirth + '\'' +
				", gastricAspirate='" + gastricAspirate + '\'' +
				", aspirateColor='" + aspirateColor + '\'' +
				", aspirateColorOther='" + aspirateColorOther + '\'' +
				", urinePassed=" + urinePassed +
				", urine='" + urine + '\'' +
				", stoolPassed=" + stoolPassed +
				", stool='" + stool + '\'' +
				", stoolOther='" + stoolOther + '\'' +
				", vomit='" + vomit + '\'' +
				", drain='" + drain + '\'' +
				", others='" + others + '\'' +
				", route='" + route + '\'' +
				", siteCannula='" + siteCannula + '\'' +
				", appearanceOther='" + appearanceOther + '\'' +
				", appearance='" + appearance + '\'' +
				", bolusType='" + bolusType + '\'' +
				", bolusTotalFeed=" + bolusTotalFeed +
				", bolusRemainingFeed=" + bolusRemainingFeed +
				", bolusDeliveredFeed=" + bolusDeliveredFeed +
				", calciumTotalFeed=" + calciumTotalFeed +
				", calciumRemainingFeed=" + calciumRemainingFeed +
				", calciumDeliveredFeed=" + calciumDeliveredFeed +
				", additionalPNTotalFeed=" + additionalPNTotalFeed +
				", additionalPNRemainingFeed=" + additionalPNRemainingFeed +
				", additionalPNDeliveredFeed=" + additionalPNDeliveredFeed +
				", aminoTotalFeed=" + aminoTotalFeed +
				", aminoRemainingFeed=" + aminoRemainingFeed +
				", aminoDeliveredFeed=" + aminoDeliveredFeed +
				", readymadeType='" + readymadeType + '\'' +
				", readymadeTotalFeed=" + readymadeTotalFeed +
				", readymadeRemainingFeed=" + readymadeRemainingFeed +
				", readymadeDeliveredFeed=" + readymadeDeliveredFeed +
				", actualFeed=" + actualFeed +
				", selectedPrimaryFeedType='" + selectedPrimaryFeedType + '\'' +
				", entry_timestamp=" + entry_timestamp +
				", vomitPassed=" + vomitPassed +
				", vomitColor='" + vomitColor + '\'' +
				", vomitColorOther='" + vomitColorOther + '\'' +
				", outputComment='" + outputComment + '\'' +
				", enteralComment='" + enteralComment + '\'' +
				", parenteralComment='" + parenteralComment + '\'' +
				", calciumVolume=" + calciumVolume +
				", calciumComment='" + calciumComment + '\'' +
				", ironVolume=" + ironVolume +
				", ironComment='" + ironComment + '\'' +
				", mvVolume=" + mvVolume +
				", mvComment='" + mvComment + '\'' +
				", vitamindVolume=" + vitamindVolume +
				", vitamindComment='" + vitamindComment + '\'' +
				", mctVolume=" + mctVolume +
				", mctComment='" + mctComment + '\'' +
				", normalSaline=" + normalSaline +
				", normalSalineComments='" + normalSalineComments + '\'' +
				", kcl=" + kcl +
				", kclComments='" + kclComments + '\'' +
				", ors=" + ors +
				", orsComments='" + orsComments + '\'' +
				", otherAdditive='" + otherAdditive + '\'' +
				", otherAdditiveVolume=" + otherAdditiveVolume +
				", otherAdditiveComment='" + otherAdditiveComment + '\'' +
				", medicineDay=" + medicineDay +
				", isFeedSkipped=" + isFeedSkipped +
				", stoma='" + stoma + '\'' +
				", stomaColor='" + stomaColor + '\'' +
				", episodeid='" + episodeid + '\'' +
				", diaperWeight=" + diaperWeight +
				", stoolConsistency='" + stoolConsistency + '\'' +
				'}';
	}
}
