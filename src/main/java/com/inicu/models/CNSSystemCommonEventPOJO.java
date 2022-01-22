package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.ScoreDownes;
import com.inicu.postgres.entities.ScoreIVH;
import com.inicu.postgres.entities.ScorePapile;
import com.inicu.postgres.entities.ScoreSarnat;
import com.inicu.postgres.entities.ScoreThompson;
import com.inicu.postgres.entities.VwRespiratoryUsageFinal;

public class CNSSystemCommonEventPOJO {

	private List<BabyPrescription> pastPrescriptionList;

	private RespSupport respSupport;

	private List<VwRespiratoryUsageFinal> respUsage;

	private ScoreIVH scoreIvh;

	private Boolean isScoreIvh;

	private ScorePapile scorePapile;

	private Boolean isScorePapile;

	private ScoreThompson thompsonScoreObj;

	private boolean thompsonFlag = false;

	private LeveneMasterJSON leveneObj;

	private boolean leveneFlag = false;

	private ScoreSarnat sarnatScoreObj;

	private Boolean isSarnatScore;

	private List<VwAssesmentCnsFinal> pastCnsHistory;

	private Integer pastPapileScore;

	private Integer pastIvhScore;

	private boolean downeFlag = false;

	private ScoreDownes downeScoreObj;

	public CNSSystemCommonEventPOJO() {
		super();
		respSupport = new RespSupport();
		this.pastPrescriptionList = new ArrayList<BabyPrescription>();

		this.scoreIvh = new ScoreIVH();
		this.isScoreIvh = false;

		this.scorePapile = new ScorePapile();
		this.isScorePapile = false;

		this.thompsonScoreObj = new ScoreThompson();

		this.sarnatScoreObj = new ScoreSarnat();
		this.isSarnatScore = false;

		this.pastCnsHistory = new ArrayList<>();

		this.downeScoreObj = new ScoreDownes();
	}

	public List<BabyPrescription> getPastPrescriptionList() {
		return pastPrescriptionList;
	}

	public void setPastPrescriptionList(List<BabyPrescription> pastPrescriptionList) {
		this.pastPrescriptionList = pastPrescriptionList;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public ScoreIVH getScoreIvh() {
		return scoreIvh;
	}

	public void setScoreIvh(ScoreIVH scoreIvh) {
		this.scoreIvh = scoreIvh;
	}

	public Boolean getIsScoreIvh() {
		return isScoreIvh;
	}

	public void setIsScoreIvh(Boolean isScoreIvh) {
		this.isScoreIvh = isScoreIvh;
	}

	public ScorePapile getScorePapile() {
		return scorePapile;
	}

	public void setScorePapile(ScorePapile scorePapile) {
		this.scorePapile = scorePapile;
	}

	public Boolean getIsScorePapile() {
		return isScorePapile;
	}

	public void setIsScorePapile(Boolean isScorePapile) {
		this.isScorePapile = isScorePapile;
	}

	public ScoreThompson getThompsonScoreObj() {
		return thompsonScoreObj;
	}

	public void setThompsonScoreObj(ScoreThompson thompsonScoreObj) {
		this.thompsonScoreObj = thompsonScoreObj;
	}

	public boolean isThompsonFlag() {
		return thompsonFlag;
	}

	public void setThompsonFlag(boolean thompsonFlag) {
		this.thompsonFlag = thompsonFlag;
	}

	public LeveneMasterJSON getLeveneObj() {
		return leveneObj;
	}

	public void setLeveneObj(LeveneMasterJSON leveneObj) {
		this.leveneObj = leveneObj;
	}

	public boolean isLeveneFlag() {
		return leveneFlag;
	}

	public void setLeveneFlag(boolean leveneFlag) {
		this.leveneFlag = leveneFlag;
	}

	public ScoreSarnat getSarnatScoreObj() {
		return sarnatScoreObj;
	}

	public void setSarnatScoreObj(ScoreSarnat sarnatScoreObj) {
		this.sarnatScoreObj = sarnatScoreObj;
	}

	public Boolean getIsSarnatScore() {
		return isSarnatScore;
	}

	public void setIsSarnatScore(Boolean isSarnatScore) {
		this.isSarnatScore = isSarnatScore;
	}

	public List<VwAssesmentCnsFinal> getPastCnsHistory() {
		return pastCnsHistory;
	}

	public void setPastCnsHistory(List<VwAssesmentCnsFinal> pastCnsHistory) {
		this.pastCnsHistory = pastCnsHistory;
	}

	public Integer getPastPapileScore() {
		return pastPapileScore;
	}

	public void setPastPapileScore(Integer pastPapileScore) {
		this.pastPapileScore = pastPapileScore;
	}

	public Integer getPastIvhScore() {
		return pastIvhScore;
	}

	public void setPastIvhScore(Integer pastIvhScore) {
		this.pastIvhScore = pastIvhScore;
	}

	public boolean isDowneFlag() {
		return downeFlag;
	}

	public void setDowneFlag(boolean downeFlag) {
		this.downeFlag = downeFlag;
	}

	public ScoreDownes getDowneScoreObj() {
		return downeScoreObj;
	}

	public void setDowneScoreObj(ScoreDownes downeScoreObj) {
		this.downeScoreObj = downeScoreObj;
	}

	public List<VwRespiratoryUsageFinal> getRespUsage() {
		return respUsage;
	}

	public void setRespUsage(List<VwRespiratoryUsageFinal> respUsage) {
		this.respUsage = respUsage;
	}

}