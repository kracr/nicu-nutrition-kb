package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the nursing_vitalparameters database table.
 * 
 */
@Entity
@Table(name = "nursing_vitalparameters")
@NamedQuery(name = "NursingVitalparameter.findAll", query = "SELECT n FROM NursingVitalparameter n")
public class NursingVitalparameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "nn_vitalparameterid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nnVitalparameterid;

	@Column(columnDefinition = "float4")
	private Float coretemp;


	@Column(columnDefinition = "float4")
	private Float centraltemp;

	@Column(name="central_temp_source")
	private String centralTempSource;

	@Column(name="central_temp_unit")
	private String centralTempUnit;


	@Column(columnDefinition = "float4")
	private Float peripheraltemp;

	@Column(name="peripheral_temp_source")
	private String peripheralTempSource;

	@Column(name="peripheral_temp_unit")
	private String peripheralTempUnit;

	private Timestamp creationtime;

	private String cvp;
	
	@Column(name="cvp_unit" ,columnDefinition = "string")
	private String cvpUnit;

	@Column(name = "diast_bp")
	private String diastBp;

	private String etco2;

	@Column(name = "hr_rate", columnDefinition = "float4")
	private Float hrRate;

	private String lax;

	@Column(name = "mean_bp")
	private String meanBp;

	private Timestamp modificationtime;

	@Column(name = "nn_vitalparameter_time")
	private String nnVitalparameterTime;

	private String rax;

	@Column(name = "rr_rate", columnDefinition = "float4")
	private Float rrRate;

	private String spo2;

	@Column(columnDefinition = "float4")
	private Float skintemp;

	@Column(name = "right_pupil_reaction")
	private String rightPupilReaction;
	
	@Column(name = "right_pupil_size")
	private String rightPupilSize;
	
	@Column(name = "left_pupil_reaction")
	private String leftPupilReaction;
	
	@Column(name = "left_pupil_size")
	private String leftPupilSize;
	
	@Column(columnDefinition = "bool")
	private Boolean isPupilEqual;
	
	@Column(name = "syst_bp")
	private String systBp;

	private String uhid;

	@Column(name = "vp_position")
	private String vpPosition;

	@Column(name = "abd_grith")
	private String abdGrith;
	
	@Column(columnDefinition = "float4")
	private Float rbs;

	private String rbsstatus;

	public String getRbsstatus() {
		return rbsstatus;
	}

	public void setRbsstatus(String rbsstatus) {
		this.rbsstatus = rbsstatus;
	}

	private String loggeduser;

	private String ivp;

	@Column(columnDefinition = "bool")
	private Boolean hypoglycemia;

	@Column(columnDefinition = "bool")
	private Boolean hyperglycemia;

	@Column(columnDefinition = "bool", name = "symptomatic_status")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	private String cft;
	private String userDate;

	@Column(columnDefinition = "float4")
	private Float tempDifference;

	@Column(columnDefinition = "float4")
	private Float systiBp;

	@Column(columnDefinition = "float4")
	private Float diastiBp;

	@Column(columnDefinition = "float4")
	private Float meaniBp;

	private Timestamp entryDate;

	private String baby_color;

	private String baby_color_other;

	private String comments;

	private String tcb;

	private String consciousness;
	
	private String pulserate;
	
	private Integer cynosis;

	private Integer retractions;

	private Integer grunting;

	private Integer airentry;

	private Integer respiratoryrate;

	private Integer downesscore;
	
	private String humidification;
	
	@Column(name = "left_probe_site")
	private String leftProbeSite;

	@Column(name = "right_probe_site")
	private String rightProbeSite;
	
	@Transient
	private Boolean isHumidification;
	
	@Transient
	private Integer pastDownesScore;
	
	@Transient
	private Timestamp pastDownesTime;
	
	@Column(name = "round_consultants")
	private String roundConsultants;
	
	@Column(name = "round_residents")
	private String roundResidents;
	
	@Column(name = "round_specialists")
	private String roundSpecialists;
	
	@Column(name = "assessments_consultants")
	private String assessmentsConsultants;
	
	@Column(name = "assessments_residents")
	private String assessmentsResidents;
	
	@Column(name = "assessments_specialists")
	private String assessmentsSpecialists;

	public NursingVitalparameter(){
		this.centralTempUnit="C";
		this.peripheralTempUnit="C";
	}

	public String getCvpUnit() {
		return cvpUnit;
	}

	public void setCvpUnit(String cvpUnit) {
		this.cvpUnit = cvpUnit;
	}

	public String getCft() {
		return cft;
	}

	public void setCft(String cft) {
		this.cft = cft;
	}

	public String getIvp() {
		return ivp;
	}

	public void setIvp(String ivp) {
		this.ivp = ivp;
	}

	public Long getNnVitalparameterid() {
		return this.nnVitalparameterid;
	}

	public void setNnVitalparameterid(Long nnVitalparameterid) {
		this.nnVitalparameterid = nnVitalparameterid;
	}

	public Float getCoretemp() {
		return this.coretemp;
	}

	public void setCoretemp(Float coretemp) {
		this.coretemp = coretemp;
	}

	public Float getCentraltemp() {
		return centraltemp;
	}

	public void setCentraltemp(Float centraltemp) {
		this.centraltemp = centraltemp;
	}

	public Float getPeripheraltemp() {
		return peripheraltemp;
	}

	public void setPeripheraltemp(Float peripheraltemp) {
		this.peripheraltemp = peripheraltemp;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getCvp() {
		return this.cvp;
	}

	public void setCvp(String cvp) {
		this.cvp = cvp;
	}

	public String getDiastBp() {
		return this.diastBp;
	}

	public void setDiastBp(String diastBp) {
		this.diastBp = diastBp;
	}

	public String getEtco2() {
		return this.etco2;
	}

	public void setEtco2(String etco2) {
		this.etco2 = etco2;
	}

	public Float getHrRate() {
		return this.hrRate;
	}

	public void setHrRate(Float hrRate) {
		this.hrRate = hrRate;
	}

	public String getLax() {
		return this.lax;
	}

	public void setLax(String lax) {
		this.lax = lax;
	}

	public String getMeanBp() {
		return this.meanBp;
	}

	public void setMeanBp(String meanBp) {
		this.meanBp = meanBp;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnVitalparameterTime() {
		return this.nnVitalparameterTime;
	}

	public void setNnVitalparameterTime(String nnVitalparameterTime) {
		this.nnVitalparameterTime = nnVitalparameterTime;
	}

	public String getRax() {
		return this.rax;
	}

	public void setRax(String rax) {
		this.rax = rax;
	}

	public Float getRrRate() {
		return this.rrRate;
	}

	public void setRrRate(Float rrRate) {
		this.rrRate = rrRate;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public Float getSkintemp() {
		return this.skintemp;
	}

	public void setSkintemp(Float skintemp) {
		this.skintemp = skintemp;
	}

	public String getSystBp() {
		return this.systBp;
	}

	public void setSystBp(String systBp) {
		this.systBp = systBp;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVpPosition() {
		return this.vpPosition;
	}

	public void setVpPosition(String vpPosition) {
		this.vpPosition = vpPosition;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getAbdGrith() {
		return abdGrith;
	}

	public void setAbdGrith(String abdGrith) {
		this.abdGrith = abdGrith;
	}

	
	
	public Float getRbs() {
		return rbs;
	}

	public void setRbs(Float rbs) {
		this.rbs = rbs;
	}

	public Boolean getHypoglycemia() {
		return hypoglycemia;
	}

	public void setHypoglycemia(Boolean hypoglycemia) {
		this.hypoglycemia = hypoglycemia;
	}

	public Boolean getHyperglycemia() {
		return hyperglycemia;
	}

	public void setHyperglycemia(Boolean hyperglycemia) {
		this.hyperglycemia = hyperglycemia;
	}

	public Boolean getSymptomaticStatus() {
		return symptomaticStatus;
	}

	public void setSymptomaticStatus(Boolean symptomaticStatus) {
		this.symptomaticStatus = symptomaticStatus;
	}

	public String getSymptomaticValue() {
		return symptomaticValue;
	}

	public void setSymptomaticValue(String symptomaticValue) {
		this.symptomaticValue = symptomaticValue;
	}

	public Float getTempDifference() {
		return tempDifference;
	}

	public Float getSystiBp() {
		return systiBp;
	}

	public Float getDiastiBp() {
		return diastiBp;
	}

	public Float getMeaniBp() {
		return meaniBp;
	}

	public void setTempDifference(Float tempDifference) {
		this.tempDifference = tempDifference;
	}

	public void setSystiBp(Float systiBp) {
		this.systiBp = systiBp;
	}

	public void setDiastiBp(Float diastiBp) {
		this.diastiBp = diastiBp;
	}

	public void setMeaniBp(Float meaniBp) {
		this.meaniBp = meaniBp;
	}

	public String getUserDate() {
		return userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getBaby_color() {
		return baby_color;
	}

	public void setBaby_color(String baby_color) {
		this.baby_color = baby_color;
	}

	public String getBaby_color_other() {
		return baby_color_other;
	}

	public void setBaby_color_other(String baby_color_other) {
		this.baby_color_other = baby_color_other;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTcb() {
		return tcb;
	}

	public void setTcb(String tcb) {
		this.tcb = tcb;
	}

	public String getConsciousness() {
		return consciousness;
	}

	public void setConsciousness(String consciousness) {
		this.consciousness = consciousness;
	}

	public Integer getCynosis() {
		return cynosis;
	}

	public Integer getRetractions() {
		return retractions;
	}

	public Integer getGrunting() {
		return grunting;
	}

	public Integer getAirentry() {
		return airentry;
	}

	public Integer getRespiratoryrate() {
		return respiratoryrate;
	}

	public Integer getDownesscore() {
		return downesscore;
	}

	public void setCynosis(Integer cynosis) {
		this.cynosis = cynosis;
	}

	public void setRetractions(Integer retractions) {
		this.retractions = retractions;
	}

	public void setGrunting(Integer grunting) {
		this.grunting = grunting;
	}

	public void setAirentry(Integer airentry) {
		this.airentry = airentry;
	}

	public void setRespiratoryrate(Integer respiratoryrate) {
		this.respiratoryrate = respiratoryrate;
	}

	public void setDownesscore(Integer downesscore) {
		this.downesscore = downesscore;
	}

	public String getPulserate() {
		return pulserate;
	}

	public void setPulserate(String pulserate) {
		this.pulserate = pulserate;
	}

	public String getHumidification() {
		return humidification;
	}

	public Boolean getIsHumidification() {
		return isHumidification;
	}

	public void setHumidification(String humidification) {
		this.humidification = humidification;
	}

	public void setIsHumidification(Boolean isHumidification) {
		this.isHumidification = isHumidification;
	}

	public String getRightPupilReaction() {
		return rightPupilReaction;
	}

	public void setRightPupilReaction(String rightPupilReaction) {
		this.rightPupilReaction = rightPupilReaction;
	}

	public String getRightPupilSize() {
		return rightPupilSize;
	}

	public void setRightPupilSize(String rightPupilSize) {
		this.rightPupilSize = rightPupilSize;
	}

	public String getLeftPupilReaction() {
		return leftPupilReaction;
	}

	public void setLeftPupilReaction(String leftPupilReaction) {
		this.leftPupilReaction = leftPupilReaction;
	}

	public String getLeftPupilSize() {
		return leftPupilSize;
	}

	public void setLeftPupilSize(String leftPupilSize) {
		this.leftPupilSize = leftPupilSize;
	}

	public Boolean getIsPupilEqual() {
		return isPupilEqual;
	}

	public void setIsPupilEqual(Boolean isPupilEqual) {
		this.isPupilEqual = isPupilEqual;
	}

	public String getLeftProbeSite() {
		return leftProbeSite;
	}

	public void setLeftProbeSite(String leftProbeSite) {
		this.leftProbeSite = leftProbeSite;
	}

	public String getRightProbeSite() {
		return rightProbeSite;
	}

	public void setRightProbeSite(String rightProbeSite) {
		this.rightProbeSite = rightProbeSite;
	}

	public Integer getPastDownesScore() {
		return pastDownesScore;
	}

	public void setPastDownesScore(Integer pastDownesScore) {
		this.pastDownesScore = pastDownesScore;
	}

	public Timestamp getPastDownesTime() {
		return pastDownesTime;
	}

	public void setPastDownesTime(Timestamp pastDownesTime) {
		this.pastDownesTime = pastDownesTime;
	}

	public String getPeripheralTempSource() {
		return peripheralTempSource;
	}

	public void setPeripheralTempSource(String peripheralTempSource) {
		this.peripheralTempSource = peripheralTempSource;
	}

	public String getPeripheralTempUnit() {
		return peripheralTempUnit;
	}

	public void setPeripheralTempUnit(String peripheralTempUnit) {
		this.peripheralTempUnit = peripheralTempUnit;
	}

	public String getCentralTempSource() {
		return centralTempSource;
	}

	public void setCentralTempSource(String centralTempSource) {
		this.centralTempSource = centralTempSource;
	}

	public String getCentralTempUnit() {
		return centralTempUnit;
	}

	public void setCentralTempUnit(String centralTempUnit) {
		this.centralTempUnit = centralTempUnit;
	}

	public String getRoundConsultants() {
		return roundConsultants;
	}

	public void setRoundConsultants(String roundConsultants) {
		this.roundConsultants = roundConsultants;
	}

	public String getRoundResidents() {
		return roundResidents;
	}

	public void setRoundResidents(String roundResidents) {
		this.roundResidents = roundResidents;
	}

	public String getRoundSpecialists() {
		return roundSpecialists;
	}

	public void setRoundSpecialists(String roundSpecialists) {
		this.roundSpecialists = roundSpecialists;
	}

	public String getAssessmentsConsultants() {
		return assessmentsConsultants;
	}

	public void setAssessmentsConsultants(String assessmentsConsultants) {
		this.assessmentsConsultants = assessmentsConsultants;
	}

	public String getAssessmentsResidents() {
		return assessmentsResidents;
	}

	public void setAssessmentsResidents(String assessmentsResidents) {
		this.assessmentsResidents = assessmentsResidents;
	}

	public String getAssessmentsSpecialists() {
		return assessmentsSpecialists;
	}

	public void setAssessmentsSpecialists(String assessmentsSpecialists) {
		this.assessmentsSpecialists = assessmentsSpecialists;
	}

	@Override
	public String toString() {
		return "NursingVitalparameter{" +
				"nnVitalparameterid=" + nnVitalparameterid +
				", coretemp=" + coretemp +
				", centraltemp=" + centraltemp +
				", centralTempSource='" + centralTempSource + '\'' +
				", centralTempUnit='" + centralTempUnit + '\'' +
				", peripheraltemp=" + peripheraltemp +
				", peripheralTempSource='" + peripheralTempSource + '\'' +
				", peripheralTempUnit='" + peripheralTempUnit + '\'' +
				", creationtime=" + creationtime +
				", cvp='" + cvp + '\'' +
				", cvpUnit='" + cvpUnit + '\'' +
				", diastBp='" + diastBp + '\'' +
				", etco2='" + etco2 + '\'' +
				", hrRate=" + hrRate +
				", lax='" + lax + '\'' +
				", meanBp='" + meanBp + '\'' +
				", modificationtime=" + modificationtime +
				", nnVitalparameterTime='" + nnVitalparameterTime + '\'' +
				", rax='" + rax + '\'' +
				", rrRate=" + rrRate +
				", spo2='" + spo2 + '\'' +
				", skintemp=" + skintemp +
				", rightPupilReaction='" + rightPupilReaction + '\'' +
				", rightPupilSize='" + rightPupilSize + '\'' +
				", leftPupilReaction='" + leftPupilReaction + '\'' +
				", leftPupilSize='" + leftPupilSize + '\'' +
				", isPupilEqual=" + isPupilEqual +
				", systBp='" + systBp + '\'' +
				", uhid='" + uhid + '\'' +
				", vpPosition='" + vpPosition + '\'' +
				", abdGrith='" + abdGrith + '\'' +
				", rbs=" + rbs +
				", loggeduser='" + loggeduser + '\'' +
				", ivp='" + ivp + '\'' +
				", hypoglycemia=" + hypoglycemia +
				", hyperglycemia=" + hyperglycemia +
				", symptomaticStatus=" + symptomaticStatus +
				", symptomaticValue='" + symptomaticValue + '\'' +
				", cft='" + cft + '\'' +
				", userDate='" + userDate + '\'' +
				", tempDifference=" + tempDifference +
				", systiBp=" + systiBp +
				", diastiBp=" + diastiBp +
				", meaniBp=" + meaniBp +
				", entryDate=" + entryDate +
				", baby_color='" + baby_color + '\'' +
				", baby_color_other='" + baby_color_other + '\'' +
				", comments='" + comments + '\'' +
				", tcb='" + tcb + '\'' +
				", consciousness='" + consciousness + '\'' +
				", pulserate='" + pulserate + '\'' +
				", cynosis=" + cynosis +
				", retractions=" + retractions +
				", grunting=" + grunting +
				", airentry=" + airentry +
				", respiratoryrate=" + respiratoryrate +
				", downesscore=" + downesscore +
				", humidification='" + humidification + '\'' +
				", leftProbeSite='" + leftProbeSite + '\'' +
				", rightProbeSite='" + rightProbeSite + '\'' +
				", isHumidification=" + isHumidification +
				", pastDownesScore=" + pastDownesScore +
				", pastDownesTime=" + pastDownesTime +
				'}';
	}
}