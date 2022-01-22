package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the stable_notes database table.
 * 
 */
@Entity
@Table(name = "stable_notes")
@NamedQuery(name = "StableNote.findAll", query = "SELECT s FROM StableNote s")
public class StableNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String activity;

	@Column(name = "bp", columnDefinition = "float4")
	private Float systolicBp;

	@Column(name = "diastolic_bp", columnDefinition = "float4")
	private Float diaStolicBp;

	private Timestamp creationtime;

	@NotNull
	private Timestamp entrytime;

	@NotBlank
	private String episodeid;

	@Column(columnDefinition = "float4")
	private Float hr;

	private Timestamp modificationtime;

	private String notes;

	@Column(columnDefinition = "float4")
	private Float rr;

	@Column(columnDefinition = "float4")
	private Float spo2;

	@Column(name = "stool_times")
	private Integer stoolTimes;

	@Column(name = "stool_status", columnDefinition = "bool")
	private Boolean stoolStatus;

	@Column(name = "stool_type")
	private String stoolType;

	@Column(columnDefinition = "float4")
	private Float temp;

	@NotBlank
	private String uhid;

	private String crt;

	@Column(name = "urine_status", columnDefinition = "bool")
	private Boolean urineStatus;

	@Column(name = "urine_volume", columnDefinition = "float4")
	private Float urineVolume;

	@Transient
	private BabyfeedDetail babyFeed;

	@Column(name = "jaundice_normal", columnDefinition = "bool")
	private Boolean jaundiceNormal;

	@Column(name = "resp_normal", columnDefinition = "bool")
	private Boolean respNormal;
	
	@Column(name = "gastro_normal", columnDefinition = "bool")
	private Boolean gastroNormal;
	
	@Column(name = "renal_normal", columnDefinition = "bool")
	private Boolean renalNormal;
	
	@Column(name = "musculoskeletal_normal", columnDefinition = "bool")
	private Boolean musculoskeletalNormal;
	
	@Column(name = "hematology_normal", columnDefinition = "bool")
	private Boolean hematologyNormal;
	
	@Column(name = "cvs_normal", columnDefinition = "bool")
	private Boolean cvsNormal;

	@Column(name = "cns_normal", columnDefinition = "bool")
	private Boolean cnsNormal;

	@Column(name = "infection_normal", columnDefinition = "bool")
	private Boolean infectionNormal;

	@Column(name = "metabolic_normal", columnDefinition = "bool")
	private Boolean metabolicNormal;

	@Column(name = "gi_normal", columnDefinition = "bool")
	private Boolean giNormal;

	private String generalnote;

	private String plan;

	@Transient
	String feedIntake;
	
	@Transient
	List<BabyPrescription> prescriptionList;

	@Column(name = "medication_str")
	private String medicationStr;
	
	@Column(name = "hr_alarm_hi", columnDefinition = "float4")
	private Float hrAlarmHi;
	
	@Column(name = "hr_alarm_lo", columnDefinition = "float4")
	private Float hrAlarmLo;
	
	@Column(name = "spo2_alarm_hi", columnDefinition = "float4")
	private Float spo2AlarmHi;
	
	@Column(name = "spo2_alarm_lo", columnDefinition = "float4")
	private Float spo2AlarmLo;
	
	@Column(name = "ett_fixed", columnDefinition = "float4")
	private Float ettFixed;
	
	@Column(name = "ett_suction", columnDefinition = "float4")
	private Float ettSuction;
	
	@Column(name = "incubator_humidity", columnDefinition = "float4")
	private Float incubatorHumidity;
	
	@Column(name = "air_skin_mode_temp", columnDefinition = "float4")
	private Float airSkinModeTemp;
	
	@Column(name = "order_selected_text")
	private String orderSelectedText;
	
	private String loggeduser;
	
	@Column(name = "abdominal_girth", columnDefinition = "float4")
	private Float abdominalGirth;
	
	@Column(name = "vomit_color")
	private String vomitColor;

	@Column(name = "vomit_status", columnDefinition = "bool")
	private Boolean vomitStatus;

	@Column(name = "vomit_type")
	private String vomitType;
	
	@Column(name = "criticality_status")
	private String criticalityStatus;
	
	@Column(name = "scalp")
	private String scalp;
	
	@Column(name = "scalp_other")
	private String scalpOther;
	
	@Column(name = "face")
	private String face;
	
	@Column(name = "face_other")
	private String faceOther;
	
	@Column(name = "eyes_size")
	private String eyesSize;
	
	@Column(name = "eyes_size_other")
	private String eyesSizeOther;
	
	@Column(name = "eyes_reaction")
	private String eyesReaction;
	
	@Column(name = "eyes_reaction_other")
	private String eyesReactionOther;
	
	@Column(name = "nose")
	private String nose;
	
	@Column(name = "kuffman_score")
	private String kuffmanScore;
	
	@Column(name = "oral")
	private String oral;
	
	@Column(name = "oral_other")
	private String oralOther;
	
	@Column(name = "skin")
	private String skin;
	
	@Column(name = "skin_other")
	private String skinOther;
	
	@Column(name = "site_type")
	private String siteType;
	
	@Column(name = "site_type_other")
	private String siteTypeOther;
	
	@Column(name = "neck")
	private String neck;
	
	@Column(name = "neck_other")
	private String neckOther;
	
	@Column(name = "abdomen")
	private String abdomen;
	
	@Column(name = "umbilicus_cord")
	private String umbilicusCord;
	
	@Column(name = "umbilicus_cord_type")
	private String umbilicusCordType;
	
	@Column(name = "umbilicus_discharge_type")
	private String umbilicusDischargeType;
	
	@Column(name = "umbilicus_cord_other")
	private String umbilicusCordOther;
	
	@Column(name = "abdomen_other")
	private String abdomenOther;
	
	@Transient
	private String respiratoryAssessment;
	
	@Transient
	private String cnsAssessment;
	
	@Transient
	private String infectionAssessment;
	
	@Column(name = "lifethreatening")
	private String lifeThreatening;
	
	private String bleeding;
	
	@Column(name = "nad_cvs")
	private String nadCvs;
	
	@Column(name = "nad_cns")
	private String nadCns;
	
	@Column(name = "nad_gastro")
	private String nadGastro;
	
	private String provisionalTemplate;
		
	@Transient
	private Boolean isFirstNote;

	@Transient
	private String progressNotes;

	@Transient
	private Boolean isNewEntry;

	private String tcb;

	@Column(columnDefinition = "float4")
	private Float rbs;
	
	public StableNote() {
		this.babyFeed = new BabyfeedDetail();
		this.prescriptionList = new ArrayList<BabyPrescription>();
		this.hrAlarmHi = (float) 180;
		this.hrAlarmLo = (float) 100;
		this.spo2AlarmHi = (float) 95;
		this.spo2AlarmLo = (float) 85;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEpisodeid() {
		return this.episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Float getHr() {
		return this.hr;
	}

	public void setHr(Float hr) {
		this.hr = hr;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getFeedIntake() {
		return feedIntake;
	}

	public void setFeedIntake(String feedIntake) {
		this.feedIntake = feedIntake;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Float getRr() {
		return this.rr;
	}

	public void setRr(Float rr) {
		this.rr = rr;
	}

	public Float getSpo2() {
		return this.spo2;
	}

	public void setSpo2(Float spo2) {
		this.spo2 = spo2;
	}

	public Integer getStoolTimes() {
		return stoolTimes;
	}

	public void setStoolTimes(Integer stoolTimes) {
		this.stoolTimes = stoolTimes;
	}

	public Boolean getStoolStatus() {
		return this.stoolStatus;
	}

	public void setStoolStatus(Boolean stoolStatus) {
		this.stoolStatus = stoolStatus;
	}

	public String getStoolType() {
		return this.stoolType;
	}

	public void setStoolType(String stoolType) {
		this.stoolType = stoolType;
	}

	public Float getTemp() {
		return this.temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean getUrineStatus() {
		return this.urineStatus;
	}

	public void setUrineStatus(Boolean urineStatus) {
		this.urineStatus = urineStatus;
	}

	public Float getUrineVolume() {
		return this.urineVolume;
	}

	public void setUrineVolume(Float urineVolume) {
		this.urineVolume = urineVolume;
	}

	public BabyfeedDetail getBabyFeed() {
		return babyFeed;
	}

	public void setBabyFeed(BabyfeedDetail babyFeed) {
		this.babyFeed = babyFeed;
	}

	public Timestamp getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Timestamp entrytime) {
		this.entrytime = entrytime;
	}

	public Float getSystolicBp() {
		return systolicBp;
	}

	public Float getDiaStolicBp() {
		return diaStolicBp;
	}

	public void setSystolicBp(Float systolicBp) {
		this.systolicBp = systolicBp;
	}

	public void setDiaStolicBp(Float diaStolicBp) {
		this.diaStolicBp = diaStolicBp;
	}

	public Boolean getJaundiceNormal() {
		return jaundiceNormal;
	}

	public void setJaundiceNormal(Boolean jaundiceNormal) {
		this.jaundiceNormal = jaundiceNormal;
	}

	public Boolean getRespNormal() {
		return respNormal;
	}

	public void setRespNormal(Boolean respNormal) {
		this.respNormal = respNormal;
	}

	public Boolean getCnsNormal() {
		return cnsNormal;
	}

	public void setCnsNormal(Boolean cnsNormal) {
		this.cnsNormal = cnsNormal;
	}

	public Boolean getInfectionNormal() {
		return infectionNormal;
	}

	public void setInfectionNormal(Boolean infectionNormal) {
		this.infectionNormal = infectionNormal;
	}

	public String getGeneralnote() {
		return generalnote;
	}

	public void setGeneralnote(String generalnote) {
		this.generalnote = generalnote;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public Float getHrAlarmHi() {
		return hrAlarmHi;
	}

	public Float getHrAlarmLo() {
		return hrAlarmLo;
	}

	public Float getSpo2AlarmHi() {
		return spo2AlarmHi;
	}

	public Float getSpo2AlarmLo() {
		return spo2AlarmLo;
	}

	public Float getEttFixed() {
		return ettFixed;
	}

	public Float getEttSuction() {
		return ettSuction;
	}

	public Float getIncubatorHumidity() {
		return incubatorHumidity;
	}

	public Float getAirSkinModeTemp() {
		return airSkinModeTemp;
	}

	public void setHrAlarmHi(Float hrAlarmHi) {
		this.hrAlarmHi = hrAlarmHi;
	}

	public void setHrAlarmLo(Float hrAlarmLo) {
		this.hrAlarmLo = hrAlarmLo;
	}

	public void setSpo2AlarmHi(Float spo2AlarmHi) {
		this.spo2AlarmHi = spo2AlarmHi;
	}

	public void setSpo2AlarmLo(Float spo2AlarmLo) {
		this.spo2AlarmLo = spo2AlarmLo;
	}

	public void setEttFixed(Float ettFixed) {
		this.ettFixed = ettFixed;
	}

	public void setEttSuction(Float ettSuction) {
		this.ettSuction = ettSuction;
	}

	public void setIncubatorHumidity(Float incubatorHumidity) {
		this.incubatorHumidity = incubatorHumidity;
	}

	public void setAirSkinModeTemp(Float airSkinModeTemp) {
		this.airSkinModeTemp = airSkinModeTemp;
	}

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Float getAbdominalGirth() {
		return abdominalGirth;
	}

	public void setAbdominalGirth(Float abdominalGirth) {
		this.abdominalGirth = abdominalGirth;
	}

	public String getVomitColor() {
		return vomitColor;
	}

	public void setVomitColor(String vomitColor) {
		this.vomitColor = vomitColor;
	}

	public Boolean getVomitStatus() {
		return vomitStatus;
	}

	public void setVomitStatus(Boolean vomitStatus) {
		this.vomitStatus = vomitStatus;
	}

	public String getVomitType() {
		return vomitType;
	}

	public void setVomitType(String vomitType) {
		this.vomitType = vomitType;
	}

	public Boolean getGastroNormal() {
		return gastroNormal;
	}

	public void setGastroNormal(Boolean gastroNormal) {
		this.gastroNormal = gastroNormal;
	}

	public Boolean getRenalNormal() {
		return renalNormal;
	}

	public void setRenalNormal(Boolean renalNormal) {
		this.renalNormal = renalNormal;
	}

	public Boolean getMusculoskeletalNormal() {
		return musculoskeletalNormal;
	}

	public void setMusculoskeletalNormal(Boolean musculoskeletalNormal) {
		this.musculoskeletalNormal = musculoskeletalNormal;
	}

	public Boolean getHematologyNormal() {
		return hematologyNormal;
	}

	public void setHematologyNormal(Boolean hematologyNormal) {
		this.hematologyNormal = hematologyNormal;
	}

	public Boolean getCvsNormal() {
		return cvsNormal;
	}

	public void setCvsNormal(Boolean cvsNormal) {
		this.cvsNormal = cvsNormal;
	}

	public String getCriticalityStatus() {
		return criticalityStatus;
	}

	public void setCriticalityStatus(String criticalityStatus) {
		this.criticalityStatus = criticalityStatus;
	}

	public String getScalp() {
		return scalp;
	}

	public void setScalp(String scalp) {
		this.scalp = scalp;
	}

	public String getScalpOther() {
		return scalpOther;
	}

	public void setScalpOther(String scalpOther) {
		this.scalpOther = scalpOther;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getFaceOther() {
		return faceOther;
	}

	public void setFaceOther(String faceOther) {
		this.faceOther = faceOther;
	}

	public String getEyesSize() {
		return eyesSize;
	}

	public void setEyesSize(String eyesSize) {
		this.eyesSize = eyesSize;
	}

	public String getEyesSizeOther() {
		return eyesSizeOther;
	}

	public void setEyesSizeOther(String eyesSizeOther) {
		this.eyesSizeOther = eyesSizeOther;
	}

	public String getEyesReaction() {
		return eyesReaction;
	}

	public void setEyesReaction(String eyesReaction) {
		this.eyesReaction = eyesReaction;
	}

	public String getEyesReactionOther() {
		return eyesReactionOther;
	}

	public void setEyesReactionOther(String eyesReactionOther) {
		this.eyesReactionOther = eyesReactionOther;
	}

	public String getNose() {
		return nose;
	}

	public void setNose(String nose) {
		this.nose = nose;
	}

	public String getKuffmanScore() {
		return kuffmanScore;
	}

	public void setKuffmanScore(String kuffmanScore) {
		this.kuffmanScore = kuffmanScore;
	}

	public String getOral() {
		return oral;
	}

	public void setOral(String oral) {
		this.oral = oral;
	}

	public String getOralOther() {
		return oralOther;
	}

	public void setOralOther(String oralOther) {
		this.oralOther = oralOther;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getSkinOther() {
		return skinOther;
	}

	public void setSkinOther(String skinOther) {
		this.skinOther = skinOther;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getSiteTypeOther() {
		return siteTypeOther;
	}

	public void setSiteTypeOther(String siteTypeOther) {
		this.siteTypeOther = siteTypeOther;
	}

	public String getNeck() {
		return neck;
	}

	public void setNeck(String neck) {
		this.neck = neck;
	}

	public String getNeckOther() {
		return neckOther;
	}

	public void setNeckOther(String neckOther) {
		this.neckOther = neckOther;
	}

	public String getAbdomen() {
		return abdomen;
	}

	public void setAbdomen(String abdomen) {
		this.abdomen = abdomen;
	}

	public String getUmbilicusCord() {
		return umbilicusCord;
	}

	public void setUmbilicusCord(String umbilicusCord) {
		this.umbilicusCord = umbilicusCord;
	}

	public String getUmbilicusDischargeType() {
		return umbilicusDischargeType;
	}

	public void setUmbilicusDischargeType(String umbilicusDischargeType) {
		this.umbilicusDischargeType = umbilicusDischargeType;
	}

	public String getUmbilicusCordType() {
		return umbilicusCordType;
	}

	public void setUmbilicusCordType(String umbilicusCordType) {
		this.umbilicusCordType = umbilicusCordType;
	}

	public String getUmbilicusCordOther() {
		return umbilicusCordOther;
	}

	public void setUmbilicusCordOther(String umbilicusCordOther) {
		this.umbilicusCordOther = umbilicusCordOther;
	}

	public String getAbdomenOther() {
		return abdomenOther;
	}

	public void setAbdomenOther(String abdomenOther) {
		this.abdomenOther = abdomenOther;
	}

	public String getCrt() {
		return crt;
	}

	public void setCrt(String crt) {
		this.crt = crt;
	}

	public String getRespiratoryAssessment() {
		return respiratoryAssessment;
	}

	public void setRespiratoryAssessment(String respiratoryAssessment) {
		this.respiratoryAssessment = respiratoryAssessment;
	}

	public String getCnsAssessment() {
		return cnsAssessment;
	}

	public void setCnsAssessment(String cnsAssessment) {
		this.cnsAssessment = cnsAssessment;
	}

	public String getInfectionAssessment() {
		return infectionAssessment;
	}

	public void setInfectionAssessment(String infectionAssessment) {
		this.infectionAssessment = infectionAssessment;
	}
	public String getBleeding() {
		return bleeding;
	}

	public void setBleeding(String bleeding) {
		this.bleeding = bleeding;
	}
	public String getLifeThreatening() {
		return lifeThreatening;
	}

	public void setLifeThreatening(String lifeThreatening) {
		this.lifeThreatening = lifeThreatening;
	}

	public String getNadCvs() {
		return nadCvs;
	}

	public void setNadCvs(String nadCvs) {
		this.nadCvs = nadCvs;
	}

	public String getNadCns() {
		return nadCns;
	}

	public void setNadCns(String nadCns) {
		this.nadCns = nadCns;
	}

	public String getNadGastro() {
		return nadGastro;
	}

	public void setNadGastro(String nadGastro) {
		this.nadGastro = nadGastro;
	}

	public Boolean getMetabolicNormal() {
		return metabolicNormal;
	}

	public void setMetabolicNormal(Boolean metabolicNormal) {
		this.metabolicNormal = metabolicNormal;
	}

	public Boolean getGiNormal() {
		return giNormal;
	}

	public void setGiNormal(Boolean giNormal) {
		this.giNormal = giNormal;
	}

	public String getProvisionalTemplate() {
		return provisionalTemplate;
	}

	public void setProvisionalTemplate(String provisionalTemplate) {
		this.provisionalTemplate = provisionalTemplate;
	}

	public Boolean getIsFirstNote() {
		return isFirstNote;
	}

	public void setIsFirstNote(Boolean isFirstNote) {
		this.isFirstNote = isFirstNote;
	}

    public String getProgressNotes() {
        return progressNotes;
    }

    public void setProgressNotes(String progressNotes) {
        this.progressNotes = progressNotes;
    }

	public Boolean getNewEntry() {
		return isNewEntry;
	}

	public void setNewEntry(Boolean newEntry) {
		isNewEntry = newEntry;
	}

	public String getTcb() {
		return tcb;
	}

	public void setTcb(String tcb) {
		this.tcb = tcb;
	}

	public Float getRbs() {
		return rbs;
	}

	public void setRbs(Float rbs) {
		this.rbs = rbs;
	}
}