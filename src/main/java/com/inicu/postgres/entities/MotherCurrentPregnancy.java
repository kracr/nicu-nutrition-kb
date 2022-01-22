package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the mother_current_pregnancy database table.
 * 
 */
@Entity
@Table(name="mother_current_pregnancy")
@NamedQuery(name="MotherCurrentPregnancy.findAll", query="SELECT m FROM MotherCurrentPregnancy m")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MotherCurrentPregnancy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long currentpregnancyid;

	@Column(name="a_score")
	private String aScore;

	private String anc;

	@Column(name="antenatal_steroids")
	private String antenatalSteroids;

	@Column(name="antenatal_suplements",columnDefinition="bool")
	private Boolean antenatalSuplements;

	@Column(name="antibodies_detected",columnDefinition="bool")
	private Boolean antibodiesDetected;

	private String antibody;

	private String antigen;

	@Column(name="antinatal_vaccinations",columnDefinition="bool")
	private Boolean antinatalVaccinations;

	@Column(name="apgar_score_10min")
	private Integer apgarScore10min;

	@Column(name="apgar_score_1min")
	private Integer apgarScore1min;

	@Column(name="apgar_score_5min")
	private Integer apgarScore5min;

	@Column(name="breech_delivery",columnDefinition="bool")
	private Boolean breechDelivery;

	private String cause;

	private String cigarperday;

	@Column(name="conium_cords",columnDefinition="bool")
	private Boolean coniumCords;

	private String cordgas1;

	@Column(name="cordgas1_bex")
	private String cordgas1Bex;

	private String cordgas2;

	@Column(name="cordgas2_bex")
	private String cordgas2Bex;

	private Timestamp creationtime;

	private String csection;

	@Column(name="ctg_normal",columnDefinition="bool")
	private Boolean ctgNormal;

	@Column(name="ctgnormal_comments")
	private String ctgnormalComments;

	@Temporal(TemporalType.DATE)
	private Date dateofonset;

	@Column(name="delivery_description")
	private String deliveryDescription;

	@Column(name="delivery_outcome")
	private String deliveryOutcome;

	@Column(name="delivery_shiftedto")
	private String deliveryShiftedto;

	private String deliverymode;

	@Column(name="deliverymode_type")
	private String deliverymodeType;

	@Column(name="drinking_status",columnDefinition="bool")
	private Boolean drinkingStatus;

	@Temporal(TemporalType.DATE)
	private Date edd;

	@Column(name="fetal_distress",columnDefinition="bool")
	private Boolean fetalDistress;

	@Column(name="flatus_tube")
	private String flatusTube;

	@Column(name="freshme_corium",columnDefinition="bool")
	private Boolean freshmeCorium;

	@Column(name="g_score")
	private String gScore;
	
	
@Column(columnDefinition="bool")
	private Boolean immunized;

	private String indication;

	@Column(name="induction_method")
	private String inductionMethod;

	@Column(name="ippr_shifted",columnDefinition="bool")
	private Boolean ipprShifted;

	@Column(name="l_score")
	private String lScore;
@Column(columnDefinition="bool")
	private Boolean labor;

	@Column(name="labor_duration_1ststage")
	private Integer laborDuration1ststage;

	@Column(name="labor_duration_2ndstage")
	private Integer laborDuration2ndstage;

	@Temporal(TemporalType.DATE)
	private Date lmp;

	@Column(name="maternal_illness")
	private String maternalIllness;

	@Column(name="maternal_medications")
	private String maternalMedications;

	@Column(name="membrane_returned",columnDefinition="bool")
	private Boolean membraneReturned;

	@Column(name="membrane_returned_time")
	private String membraneReturnedTime;

	private String modeofonset;

	@Column(name="modeofonset_comments")
	private String modeofonsetComments;

	private Timestamp modificationtime;

	private String motherbloodgroup;

	private String motherid;

	private String others;

	@Column(name="p_score")
	private String pScore;

	private String presentation;

	@Column(columnDefinition="bool")
	private Boolean resuscitation;

	@Column(name="resuscitation_comments")
	private String resuscitationComments;

	@Column(name="smoking_status", columnDefinition="bool")
	private Boolean smokingStatus;

	@Column(name="surfactant_dose")
	private Integer surfactantDose;

	@Column(name="surfactant_status", columnDefinition="bool")
	private Boolean surfactantStatus;

	private String timeofonset;

	private String timetofirstbreath;

	private String timetoregularresps;

	private String uhid;

	private String unitperday;

	private String vaginal;

	@Column(name="vitamink_dose",columnDefinition="Float4")
	private Float vitaminkDose;

	@Column(name="vitamink_givenby")
	private String vitaminkGivenby;

	@Column(name="vitamink_status")
	private String vitaminkStatus;

	public MotherCurrentPregnancy() {
	}

	public Long getCurrentpregnancyid() {
		return this.currentpregnancyid;
	}

	public void setCurrentpregnancyid(Long currentpregnancyid) {
		this.currentpregnancyid = currentpregnancyid;
	}

	public String getAScore() {
		return this.aScore;
	}

	public void setAScore(String aScore) {
		this.aScore = aScore;
	}

	public String getAnc() {
		return this.anc;
	}

	public void setAnc(String anc) {
		this.anc = anc;
	}

	public String getAntenatalSteroids() {
		return this.antenatalSteroids;
	}

	public void setAntenatalSteroids(String antenatalSteroids) {
		this.antenatalSteroids = antenatalSteroids;
	}

	public Boolean getAntenatalSuplements() {
		return this.antenatalSuplements;
	}

	public void setAntenatalSuplements(Boolean antenatalSuplements) {
		this.antenatalSuplements = antenatalSuplements;
	}

	public Boolean getAntibodiesDetected() {
		return this.antibodiesDetected;
	}

	public void setAntibodiesDetected(Boolean antibodiesDetected) {
		this.antibodiesDetected = antibodiesDetected;
	}

	public String getAntibody() {
		return this.antibody;
	}

	public void setAntibody(String antibody) {
		this.antibody = antibody;
	}

	public String getAntigen() {
		return this.antigen;
	}

	public void setAntigen(String antigen) {
		this.antigen = antigen;
	}

	public Boolean getAntinatalVaccinations() {
		return this.antinatalVaccinations;
	}

	public void setAntinatalVaccinations(Boolean antinatalVaccinations) {
		this.antinatalVaccinations = antinatalVaccinations;
	}

	public Integer getApgarScore10min() {
		return this.apgarScore10min;
	}

	public void setApgarScore10min(Integer apgarScore10min) {
		this.apgarScore10min = apgarScore10min;
	}

	public Integer getApgarScore1min() {
		return this.apgarScore1min;
	}

	public void setApgarScore1min(Integer apgarScore1min) {
		this.apgarScore1min = apgarScore1min;
	}

	public Integer getApgarScore5min() {
		return this.apgarScore5min;
	}

	public void setApgarScore5min(Integer apgarScore5min) {
		this.apgarScore5min = apgarScore5min;
	}

	public Boolean getBreechDelivery() {
		return this.breechDelivery;
	}

	public void setBreechDelivery(Boolean breechDelivery) {
		this.breechDelivery = breechDelivery;
	}

	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getCigarperday() {
		return this.cigarperday;
	}

	public void setCigarperday(String cigarperday) {
		this.cigarperday = cigarperday;
	}

	public Boolean getConiumCords() {
		return this.coniumCords;
	}

	public void setConiumCords(Boolean coniumCords) {
		this.coniumCords = coniumCords;
	}

	public String getCordgas1() {
		return this.cordgas1;
	}

	public void setCordgas1(String cordgas1) {
		this.cordgas1 = cordgas1;
	}

	public String getCordgas1Bex() {
		return this.cordgas1Bex;
	}

	public void setCordgas1Bex(String cordgas1Bex) {
		this.cordgas1Bex = cordgas1Bex;
	}

	public String getCordgas2() {
		return this.cordgas2;
	}

	public void setCordgas2(String cordgas2) {
		this.cordgas2 = cordgas2;
	}

	public String getCordgas2Bex() {
		return this.cordgas2Bex;
	}

	public void setCordgas2Bex(String cordgas2Bex) {
		this.cordgas2Bex = cordgas2Bex;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getCsection() {
		return this.csection;
	}

	public void setCsection(String csection) {
		this.csection = csection;
	}

	public Boolean getCtgNormal() {
		return this.ctgNormal;
	}

	public void setCtgNormal(Boolean ctgNormal) {
		this.ctgNormal = ctgNormal;
	}

	public String getCtgnormalComments() {
		return this.ctgnormalComments;
	}

	public void setCtgnormalComments(String ctgnormalComments) {
		this.ctgnormalComments = ctgnormalComments;
	}

	public Date getDateofonset() {
		return this.dateofonset;
	}

	public void setDateofonset(Date dateofonset) {
		this.dateofonset = dateofonset;
	}

	public String getDeliveryDescription() {
		return this.deliveryDescription;
	}

	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}

	public String getDeliveryOutcome() {
		return this.deliveryOutcome;
	}

	public void setDeliveryOutcome(String deliveryOutcome) {
		this.deliveryOutcome = deliveryOutcome;
	}

	public String getDeliveryShiftedto() {
		return this.deliveryShiftedto;
	}

	public void setDeliveryShiftedto(String deliveryShiftedto) {
		this.deliveryShiftedto = deliveryShiftedto;
	}

	public String getDeliverymode() {
		return this.deliverymode;
	}

	public void setDeliverymode(String deliverymode) {
		this.deliverymode = deliverymode;
	}

	public String getDeliverymodeType() {
		return this.deliverymodeType;
	}

	public void setDeliverymodeType(String deliverymodeType) {
		this.deliverymodeType = deliverymodeType;
	}

	public Boolean getDrinkingStatus() {
		return this.drinkingStatus;
	}

	public void setDrinkingStatus(Boolean drinkingStatus) {
		this.drinkingStatus = drinkingStatus;
	}

	public Date getEdd() {
		return this.edd;
	}

	public void setEdd(Date edd) {
		this.edd = edd;
	}

	public Boolean getFetalDistress() {
		return this.fetalDistress;
	}

	public void setFetalDistress(Boolean fetalDistress) {
		this.fetalDistress = fetalDistress;
	}

	public String getFlatusTube() {
		return this.flatusTube;
	}

	public void setFlatusTube(String flatusTube) {
		this.flatusTube = flatusTube;
	}

	public Boolean getFreshmeCorium() {
		return this.freshmeCorium;
	}

	public void setFreshmeCorium(Boolean freshmeCorium) {
		this.freshmeCorium = freshmeCorium;
	}

	public String getGScore() {
		return this.gScore;
	}

	public void setGScore(String gScore) {
		this.gScore = gScore;
	}

	public Boolean getImmunized() {
		return this.immunized;
	}

	public void setImmunized(Boolean immunized) {
		this.immunized = immunized;
	}

	public String getIndication() {
		return this.indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getInductionMethod() {
		return this.inductionMethod;
	}

	public void setInductionMethod(String inductionMethod) {
		this.inductionMethod = inductionMethod;
	}

	public Boolean getIpprShifted() {
		return this.ipprShifted;
	}

	public void setIpprShifted(Boolean ipprShifted) {
		this.ipprShifted = ipprShifted;
	}

	public String getLScore() {
		return this.lScore;
	}

	public void setLScore(String lScore) {
		this.lScore = lScore;
	}

	public Boolean getLabor() {
		return this.labor;
	}

	public void setLabor(Boolean labor) {
		this.labor = labor;
	}

	public Integer getLaborDuration1ststage() {
		return this.laborDuration1ststage;
	}

	public void setLaborDuration1ststage(Integer laborDuration1ststage) {
		this.laborDuration1ststage = laborDuration1ststage;
	}

	public Integer getLaborDuration2ndstage() {
		return this.laborDuration2ndstage;
	}

	public void setLaborDuration2ndstage(Integer laborDuration2ndstage) {
		this.laborDuration2ndstage = laborDuration2ndstage;
	}

	public Date getLmp() {
		return this.lmp;
	}

	public void setLmp(Date lmp) {
		this.lmp = lmp;
	}

	public String getMaternalIllness() {
		return this.maternalIllness;
	}

	public void setMaternalIllness(String maternalIllness) {
		this.maternalIllness = maternalIllness;
	}

	public String getMaternalMedications() {
		return this.maternalMedications;
	}

	public void setMaternalMedications(String maternalMedications) {
		this.maternalMedications = maternalMedications;
	}

	public Boolean getMembraneReturned() {
		return this.membraneReturned;
	}

	public void setMembraneReturned(Boolean membraneReturned) {
		this.membraneReturned = membraneReturned;
	}

	public String getMembraneReturnedTime() {
		return this.membraneReturnedTime;
	}

	public void setMembraneReturnedTime(String membraneReturnedTime) {
		this.membraneReturnedTime = membraneReturnedTime;
	}

	public String getModeofonset() {
		return this.modeofonset;
	}

	public void setModeofonset(String modeofonset) {
		this.modeofonset = modeofonset;
	}

	public String getModeofonsetComments() {
		return this.modeofonsetComments;
	}

	public void setModeofonsetComments(String modeofonsetComments) {
		this.modeofonsetComments = modeofonsetComments;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getMotherbloodgroup() {
		return this.motherbloodgroup;
	}

	public void setMotherbloodgroup(String motherbloodgroup) {
		this.motherbloodgroup = motherbloodgroup;
	}

	public String getMotherid() {
		return this.motherid;
	}

	public void setMotherid(String motherid) {
		this.motherid = motherid;
	}

	public String getOthers() {
		return this.others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getPScore() {
		return this.pScore;
	}

	public void setPScore(String pScore) {
		this.pScore = pScore;
	}

	public String getPresentation() {
		return this.presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public Boolean getResuscitation() {
		return this.resuscitation;
	}

	public void setResuscitation(Boolean resuscitation) {
		this.resuscitation = resuscitation;
	}

	public String getResuscitationComments() {
		return this.resuscitationComments;
	}

	public void setResuscitationComments(String resuscitationComments) {
		this.resuscitationComments = resuscitationComments;
	}

	public Boolean getSmokingStatus() {
		return this.smokingStatus;
	}

	public void setSmokingStatus(Boolean smokingStatus) {
		this.smokingStatus = smokingStatus;
	}

	public Integer getSurfactantDose() {
		return this.surfactantDose;
	}

	public void setSurfactantDose(Integer surfactantDose) {
		this.surfactantDose = surfactantDose;
	}

	public Boolean getSurfactantStatus() {
		return this.surfactantStatus;
	}

	public void setSurfactantStatus(Boolean surfactantStatus) {
		this.surfactantStatus = surfactantStatus;
	}

	public String getTimeofonset() {
		return this.timeofonset;
	}

	public void setTimeofonset(String timeofonset) {
		this.timeofonset = timeofonset;
	}

	public String getTimetofirstbreath() {
		return this.timetofirstbreath;
	}

	public void setTimetofirstbreath(String timetofirstbreath) {
		this.timetofirstbreath = timetofirstbreath;
	}

	public String getTimetoregularresps() {
		return this.timetoregularresps;
	}

	public void setTimetoregularresps(String timetoregularresps) {
		this.timetoregularresps = timetoregularresps;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getUnitperday() {
		return this.unitperday;
	}

	public void setUnitperday(String unitperday) {
		this.unitperday = unitperday;
	}

	public String getVaginal() {
		return this.vaginal;
	}

	public void setVaginal(String vaginal) {
		this.vaginal = vaginal;
	}

	public Float getVitaminkDose() {
		return this.vitaminkDose;
	}

	public void setVitaminkDose(Float vitaminkDose) {
		this.vitaminkDose = vitaminkDose;
	}

	public String getVitaminkGivenby() {
		return this.vitaminkGivenby;
	}

	public void setVitaminkGivenby(String vitaminkGivenby) {
		this.vitaminkGivenby = vitaminkGivenby;
	}

	public String getVitaminkStatus() {
		return this.vitaminkStatus;
	}

	public void setVitaminkStatus(String vitaminkStatus) {
		this.vitaminkStatus = vitaminkStatus;
	}

}