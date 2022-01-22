package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="sa_pain")
@NamedQuery(name="SaPain.findAll", query="SELECT s FROM SaPain s")
public class SaPain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long painid;

    private Timestamp creationtime;

    private Timestamp modificationtime;

    private String uhid;

    private String loggeduser;

    @Column(name ="age_at_assessment")
    private Integer ageAtAssessment;

    @Column(name="ageatassessment_inhours" , columnDefinition="bool")
    private Boolean isAgeAtAssessmentInHours;

    @Column(name="episode_number")
    private Integer episodeNumber;

    @Column(name = "painentrytype",columnDefinition = "bool")
    private Boolean painEntryType;

    @Column(name="perioperative_pain",columnDefinition = "bool")
    private Boolean perioperativePain;

    @Column(name = "inflammatory_lesion",columnDefinition = "bool")
    private Boolean inflammatoryLesion;

    @Column(name="trauma",columnDefinition = "bool")
    private Boolean trauma;

    @Column(name="procedure_pain",columnDefinition = "bool")
    private Boolean procedurePain;

    @Column(name="other",columnDefinition = "bool")
    private Boolean other;

    @Column(name= "other_pain")
    private String otherPain;

    @Column(name="indications")
    private String indications;

    @Column(name="nips_score")
    private Integer nipsScore;

    @Column(name = "pips_score")
    private Integer pipsScore;

    @Column(name="medication_text")
    private String medicationText;

    @Column(name="order_investigation")
    private String orderInvestigation;

    @Transient
    private List orderinvestigationList;

    @Column(name="investigation_ordered_boolean" ,columnDefinition = "bool")
    private Boolean investigationOrderedBooleanValue;

    @Column(name="other_measures")
    private String otherMeasures;

    @Column(name = "measure_name")
    private String measureName;

    @Transient
    private String pastOrderInvestigationStr;

    @Column(name = "assessment_time")
    private Timestamp assessmentTime;

    @Column(name = "pain_status")
    private String painStatus;

    @Transient
    private List nonPharmacologicalMeasuresList;
    
    @Temporal(TemporalType.DATE)
    @Transient
    private Date dateofbirth;

    private Integer actionduration;

    private String isactiondurationinhours;

    @Column(name="planother")
    private String planOther;

    @Column(name="frequency_of_scoring")
    private Integer freqOfScoring;

    @Column(name="isfreuencyinhours")
    private String isFrequencyInHours;

    @Column(name="progress_notes")
    private String progressNotes;


    @Column(name="comments_plan")
    private String commentsPlan;

    private String episodeid;

    @Transient
    private String orderlist;
    
    private String comment;

	@Transient
	private List actiontypeList;
	
	@Column(name = "assessment_hour")
	private String assessmentHour;

	@Column(name = "assessment_min")
	private String assessmentMin;

	@Column(name = "assessment_meridiem", columnDefinition = "bool")
	private Boolean assessmentMeridiem;
	
	@Column(name = "others_indication_type")
	private String othersIndicationType;
	
	@Column(name = "procedure_pain_type")
	private String procedurePainType;
	
	@Column(name = "trauma_type")
	private String traumaType;
	
	@Column(name = "inflammatory_lesion_type")
	private String inflammatoryLesionType;
	
	@Column(name = "perioperative_pain_type")
	private String perioperativePainType;
	
	@Column(name = "perioperativ_pain_typ_others")
	private String perioperativePainTypeOthers;
	
	@Column(name = "procedure_pain_type_others")
	private String procedurePainTypeOthers;
	
	@Column(name = "action_string")
	private String actionString;
	
	@Transient
	private Boolean isNewEntry;
	
	private String nipsscoreid;
	
	private String pippscoreid;
	
	private Integer pippscore;
	
	private Integer nipsscore;

    public SaPain() {
        this.isAgeAtAssessmentInHours = true;
        this.isNewEntry = true;
    }

    public Long getPainid() {
        return painid;
    }

    public void setPainid(Long painid) {
        this.painid = painid;
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

    public Integer getAgeAtAssessment() {
        return ageAtAssessment;
    }

    public void setAgeAtAssessment(Integer ageAtAssessment) {
        this.ageAtAssessment = ageAtAssessment;
    }

    public Boolean getIsAgeAtAssessmentInHours() {
        return isAgeAtAssessmentInHours;
    }

    public void setIsAgeAtAssessmentInHours(Boolean isAgeAtAssessmentInHours) {
        this.isAgeAtAssessmentInHours = isAgeAtAssessmentInHours;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Boolean getPainEntryType() {
        return painEntryType;
    }

    public void setPainEntryType(Boolean painEntryType) {
        this.painEntryType = painEntryType;
    }

    public Boolean getPerioperativePain() {
        return perioperativePain;
    }

    public void setPerioperativePain(Boolean perioperativePain) {
        this.perioperativePain = perioperativePain;
    }

    public Boolean getTrauma() {
        return trauma;
    }

    public void setTrauma(Boolean trauma) {
        this.trauma = trauma;
    }

    public Boolean getProcedurePain() {
        return procedurePain;
    }

    public void setProcedurePain(Boolean procedurePain) {
        this.procedurePain = procedurePain;
    }

    public Boolean getOther() {
        return other;
    }

    public void setOther(Boolean other) {
        this.other = other;
    }

    public String getOtherPain() {
        return otherPain;
    }

    public void setOtherPain(String otherPain) {
        this.otherPain = otherPain;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }


    public String getMedicationText() {
        return medicationText;
    }

    public void setMedicationText(String medicationText) {
        this.medicationText = medicationText;
    }

    public String getOrderInvestigation() {
        return orderInvestigation;
    }

    public void setOrderInvestigation(String orderInvestigation) {
        this.orderInvestigation = orderInvestigation;
    }

    public List getOrderinvestigationList() {
        return orderinvestigationList;
    }

    public void setOrderinvestigationList(List orderinvestigationList) {
        this.orderinvestigationList = orderinvestigationList;
    }

    public Boolean getInvestigationOrderedBooleanValue() {
        return investigationOrderedBooleanValue;
    }

    public void setInvestigationOrderedBooleanValue(Boolean investigationOrderedBooleanValue) {
        this.investigationOrderedBooleanValue = investigationOrderedBooleanValue;
    }

    public String getOtherMeasures() {
        return otherMeasures;
    }

    public void setOtherMeasures(String otherMeasures) {
        this.otherMeasures = otherMeasures;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getPastOrderInvestigationStr() {
        return pastOrderInvestigationStr;
    }

    public void setPastOrderInvestigationStr(String pastOrderInvestigationStr) {
        this.pastOrderInvestigationStr = pastOrderInvestigationStr;
    }

    public Timestamp getAssessmentTime() {
        return assessmentTime;
    }

    public void setAssessmentTime(Timestamp assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    public String getPainStatus() {
        return painStatus;
    }

    public void setPainStatus(String painStatus) {
        this.painStatus = painStatus;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public Integer getActionduration() {
        return actionduration;
    }

    public void setActionduration(Integer actionduration) {
        this.actionduration = actionduration;
    }

    public String getIsactiondurationinhours() {
        return isactiondurationinhours;
    }

    public void setIsactiondurationinhours(String isactiondurationinhours) {
        this.isactiondurationinhours = isactiondurationinhours;
    }

    public String getPlanOther() {
        return planOther;
    }

    public void setPlanOther(String planOther) {
        this.planOther = planOther;
    }

    public String getIsFrequencyInHours() {
        return isFrequencyInHours;
    }

    public void setIsFrequencyInHours(String isFrequencyInHours) {
        this.isFrequencyInHours = isFrequencyInHours;
    }

    public String getProgressNotes() {
        return progressNotes;
    }

    public void setProgressNotes(String progressNotes) {
        this.progressNotes = progressNotes;
    }

    public String getCommentsPlan() {
        return commentsPlan;
    }

    public void setCommentsPlan(String commentsPlan) {
        this.commentsPlan = commentsPlan;
    }

    public String getEpisodeid() {
        return episodeid;
    }

    public void setEpisodeid(String episodeid) {
        this.episodeid = episodeid;
    }

    public String getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(String orderlist) {
        this.orderlist = orderlist;
    }

    public Integer getNipsScore() {
        return nipsScore;
    }

    public void setNipsScore(Integer nipsScore) {
        this.nipsScore = nipsScore;
    }

    public Integer getPipsScore() {
        return pipsScore;
    }

    public void setPipsScore(Integer pipsScore) {
        this.pipsScore = pipsScore;
    }

    public List getActiontypeList() {
		return actiontypeList;
	}

	public void setActiontypeList(List actiontypeList) {
		this.actiontypeList = actiontypeList;
	}

	public String getAssessmentHour() {
		return assessmentHour;
	}

	public void setAssessmentHour(String assessmentHour) {
		this.assessmentHour = assessmentHour;
	}

	public String getAssessmentMin() {
		return assessmentMin;
	}

	public void setAssessmentMin(String assessmentMin) {
		this.assessmentMin = assessmentMin;
	}

	public Boolean getAssessmentMeridiem() {
		return assessmentMeridiem;
	}

	public void setAssessmentMeridiem(Boolean assessmentMeridiem) {
		this.assessmentMeridiem = assessmentMeridiem;
	}

	public Boolean getInflammatoryLesion() {
		return inflammatoryLesion;
	}

	public void setInflammatoryLesion(Boolean inflammatoryLesion) {
		this.inflammatoryLesion = inflammatoryLesion;
	}

	public List getNonPharmacologicalMeasuresList() {
		return nonPharmacologicalMeasuresList;
	}

	public void setNonPharmacologicalMeasuresList(List nonPharmacologicalMeasuresList) {
		this.nonPharmacologicalMeasuresList = nonPharmacologicalMeasuresList;
	}

	public Integer getFreqOfScoring() {
		return freqOfScoring;
	}

	public void setFreqOfScoring(Integer freqOfScoring) {
		this.freqOfScoring = freqOfScoring;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProcedurePainType() {
		return procedurePainType;
	}

	public void setProcedurePainType(String procedurePainType) {
		this.procedurePainType = procedurePainType;
	}

	public String getTraumaType() {
		return traumaType;
	}

	public void setTraumaType(String traumaType) {
		this.traumaType = traumaType;
	}

	public String getInflammatoryLesionType() {
		return inflammatoryLesionType;
	}

	public void setInflammatoryLesionType(String inflammatoryLesionType) {
		this.inflammatoryLesionType = inflammatoryLesionType;
	}

	public String getPerioperativePainType() {
		return perioperativePainType;
	}

	public void setPerioperativePainType(String perioperativePainType) {
		this.perioperativePainType = perioperativePainType;
	}

	public String getOthersIndicationType() {
		return othersIndicationType;
	}

	public void setOthersIndicationType(String othersIndicationType) {
		this.othersIndicationType = othersIndicationType;
	}

	public String getPerioperativePainTypeOthers() {
		return perioperativePainTypeOthers;
	}

	public void setPerioperativePainTypeOthers(String perioperativePainTypeOthers) {
		this.perioperativePainTypeOthers = perioperativePainTypeOthers;
	}

	public String getProcedurePainTypeOthers() {
		return procedurePainTypeOthers;
	}

	public void setProcedurePainTypeOthers(String procedurePainTypeOthers) {
		this.procedurePainTypeOthers = procedurePainTypeOthers;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public String getActionString() {
		return actionString;
	}

	public void setActionString(String actionString) {
		this.actionString = actionString;
	}
	
	public String getNipsscoreid() {
		return nipsscoreid;
	}

	public void setNipsscoreid(String nipsscoreid) {
		this.nipsscoreid = nipsscoreid;
	}

	public String getPippscoreid() {
		return pippscoreid;
	}

	public void setPippscoreid(String pippscoreid) {
		this.pippscoreid = pippscoreid;
	}

	public Integer getPippscore() {
		return pippscore;
	}

	public void setPippscore(Integer pippscore) {
		this.pippscore = pippscore;
	}

	public Integer getNipsscore() {
		return nipsscore;
	}

	public void setNipsscore(Integer nipsscore) {
		this.nipsscore = nipsscore;
	}

	@Override
	public String toString() {
		return "SaPain [painid=" + painid + ", creationtime=" + creationtime + ", modificationtime=" + modificationtime
				+ ", uhid=" + uhid + ", loggeduser=" + loggeduser + ", ageAtAssessment=" + ageAtAssessment
				+ ", isAgeAtAssessmentInHours=" + isAgeAtAssessmentInHours + ", episodeNumber=" + episodeNumber
				+ ", painEntryType=" + painEntryType + ", perioperativePain=" + perioperativePain
				+ ", inflammatoryLesion=" + inflammatoryLesion + ", trauma=" + trauma + ", procedurePain="
				+ procedurePain + ", other=" + other + ", otherPain=" + otherPain + ", indications=" + indications
				+ ", nipsScore=" + nipsScore + ", pipsScore=" + pipsScore + ", medicationText=" + medicationText
				+ ", orderInvestigation=" + orderInvestigation + ", orderinvestigationList=" + orderinvestigationList
				+ ", investigationOrderedBooleanValue=" + investigationOrderedBooleanValue + ", otherMeasures="
				+ otherMeasures + ", measureName=" + measureName + ", pastOrderInvestigationStr="
				+ pastOrderInvestigationStr + ", assessmentTime=" + assessmentTime + ", painStatus=" + painStatus
				+ ", nonPharmacologicalMeasuresList=" + nonPharmacologicalMeasuresList + ", dateofbirth=" + dateofbirth
				+ ", actionduration=" + actionduration + ", isactiondurationinhours=" + isactiondurationinhours
				+ ", planOther=" + planOther + ", freqOfScoring=" + freqOfScoring + ", isFrequencyInHours="
				+ isFrequencyInHours + ", progressNotes=" + progressNotes + ", commentsPlan=" + commentsPlan
				+ ", episodeid=" + episodeid + ", orderlist=" + orderlist + ", comment=" + comment + ", actiontypeList="
				+ actiontypeList + ", assessmentHour=" + assessmentHour + ", assessmentMin=" + assessmentMin
				+ ", assessmentMeridiem=" + assessmentMeridiem + ", othersIndicationType=" + othersIndicationType
				+ ", procedurePainType=" + procedurePainType + ", traumaType=" + traumaType
				+ ", inflammatoryLesionType=" + inflammatoryLesionType + ", perioperativePainType="
				+ perioperativePainType + ", perioperativePainTypeOthers=" + perioperativePainTypeOthers
				+ ", procedurePainTypeOthers=" + procedurePainTypeOthers + ", actionString=" + actionString
				+ ", isNewEntry=" + isNewEntry + ", nipsscoreid=" + nipsscoreid + ", pippscoreid=" + pippscoreid
				+ ", pippscore=" + pippscore + ", nipsscore=" + nipsscore + "]";
	}
	
}

