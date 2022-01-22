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
 * The persistent class for the sa_respsystem database table.
 * 
 */
@Entity
@Table(name="sa_respsystem")
@NamedQuery(name="SaRespsystem.findAll", query="SELECT s FROM SaRespsystem s")
public class SaRespsystem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long respid;
	
	private String uhid;
	
	private Timestamp creationtime;
	
	private String loggeduser;
	
	@Column(name="resp_system_status")
	private String respSystemStatus;
	
	private String eventstatus;
	
	private String ageatonset;
	
	@Column(columnDefinition="bool")
	private Boolean ageinhoursdays;
	
	private String downesscoreid;
	
	private String treatmentaction;
	
	@Transient private List<String> treatmentActionList;
	
	private String sufactantstatus;
	
	@Column(name="surfactant_dose")
	private String surfactantDose;
	
	private String respirattorystatus;
	
	private String respirattorytime;
	
	@Column(columnDefinition="bool")
	private Boolean respinhoursdays;
	
	private String ventbehaviour;
	
	@Column(name="ventilation_type")
	private String ventilationType;
	
	private String medicineid;
	 
	@Transient private List<String> medicineIdList;
	
	private String rdsplan;
	
	@Transient private List<String> rdsPlanList;
	
	private String reassestime;
	
	private String reasseshoursdays;
	
	private String silvermanscoreid;
	
	private String rdscause;
	
	@Transient private List<String> rdsCauseList;
	
	private String progressnotes;
	
	@Column(name="rds_plan_others")
	private String rdsPlanOthers;
	
	@Column(columnDefinition="bool",name="isventmode_upgrade")
	private Boolean isVentModeUpgrade;

	@Transient private List<String> orderInvestigationList;
	
	@Transient private List<String> orderInvestigationStringList;
	
	@Transient private String medicineString;
	
	@Transient private String nursingComments;
	
	@Column(columnDefinition="bool")
	private Boolean isinsuredone;
	
	@Column(name="rs_insure_type")
	private String rsInsureType;
	
	@Transient
	@Column(name="systolic_bp")
	private String systolicBp;
	
	@Transient
	@Column(name="oxgenation_index")
	private String oxgenationIndex;
	
	@Transient
	@Column(name="lab_preductal")
	private String labPreductal;
	
	@Transient
	@Column(name="lab_postductal")
	private String labPostductal;
	
	@Transient
	@Column(name="labile_difference")
	private String labileDifference;
	
	@Transient
	@Column(columnDefinition="bool", name="labile_oxygenation")
	private Boolean labileOxygenation;
	
	@Transient
	@Column(name="pulmonary_pressure")
	private String pulmonaryPressure;
	
	@Transient
	@Column(columnDefinition="bool")
	private Boolean transillumination;
	
	@Transient
	@Column(name="pphn_ino")
	private String pphnIno;
	
	@Transient
	@Column(name="methaemoglobin_level")
	private String methaemoglobinLevel;
	
	@Transient
	@Column(name="sildenafil")
	private String sildenafil;
	
	@Transient
	@Column(columnDefinition="bool", name="needle_aspiration")
	private Boolean needleAspiration;
	
	@Transient
	@Column(columnDefinition="bool", name="chesttube_insertion")
	private Boolean chesttubeInsertion;
	
	@Transient
	@Column(name="aspiration_plan_time")
	private Integer aspirationPlanTime;
	
	@Transient
	@Column(name="aspiration_minhrsdays")
	private String aspirationMinhrsdays;
	
	@Transient
	@Column(name="chesttube_plan_time")
	private Integer chesttubePlanTime;
	
	@Transient
	@Column(name="chesttube_minhrsdays")
	private String chesttubeMinhrsdays;
	
	@Transient
	@Column(name="inotropes")
	private String inotropes;
	
	@Transient
	@Column(name="pphn_eventstatus")
	private String pphnEventstatus;
	
	@Transient
	@Column(name="phenumothorax_eventstatus")
	private String phenumothoraxEventstatus;
	
	@Transient
	private String survantaDoseCount;
	
	public String getNursingComments() {
		return nursingComments;
	}

	public void setNursingComments(String nursingComments) {
		this.nursingComments = nursingComments;
	}

	public String getMedicineString() {
		return medicineString;
	}

	public void setMedicineString(String medicineString) {
		this.medicineString = medicineString;
	}

	public SaRespsystem() {
		super();
		this.ageinhoursdays=true;
		this.respirattorystatus ="Yes";
		this.survantaDoseCount = "1st";
	}

	public Long getRespid() {
		return respid;
	}

	public String getRespSystemStatus() {
		return respSystemStatus;
	}

	public String getEventstatus() {
		return eventstatus;
	}

	public String getAgeatonset() {
		return ageatonset;
	}

	public Boolean getAgeinhoursdays() {
		return ageinhoursdays;
	}

	public String getDownesscoreid() {
		return downesscoreid;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public String getSufactantstatus() {
		return sufactantstatus;
	}

	public String getSurfactantDose() {
		return surfactantDose;
	}

	public String getRespirattorystatus() {
		return respirattorystatus;
	}

	public String getRespirattorytime() {
		return respirattorytime;
	}

	public Boolean getRespinhoursdays() {
		return respinhoursdays;
	}

	public String getVentbehaviour() {
		return ventbehaviour;
	}

	public String getVentilationType() {
		return ventilationType;
	}

	public String getMedicineid() {
		return medicineid;
	}

	public String getRdsplan() {
		return rdsplan;
	}

	public String getReassestime() {
		return reassestime;
	}

	public String getReasseshoursdays() {
		return reasseshoursdays;
	}

	public String getSilvermanscoreid() {
		return silvermanscoreid;
	}

	public String getRdscause() {
		return rdscause;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setRespid(Long respid) {
		this.respid = respid;
	}

	public void setRespSystemStatus(String respSystemStatus) {
		this.respSystemStatus = respSystemStatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public void setAgeatonset(String ageatonset) {
		this.ageatonset = ageatonset;
	}

	public void setAgeinhoursdays(Boolean ageinhoursdays) {
		this.ageinhoursdays = ageinhoursdays;
	}

	public void setDownesscoreid(String downesscoreid) {
		this.downesscoreid = downesscoreid;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public void setSufactantstatus(String sufactantstatus) {
		this.sufactantstatus = sufactantstatus;
	}

	public void setSurfactantDose(String surfactantDose) {
		this.surfactantDose = surfactantDose;
	}

	public void setRespirattorystatus(String respirattorystatus) {
		this.respirattorystatus = respirattorystatus;
	}

	public void setRespirattorytime(String respirattorytime) {
		this.respirattorytime = respirattorytime;
	}

	public void setRespinhoursdays(Boolean respinhoursdays) {
		this.respinhoursdays = respinhoursdays;
	}

	public void setVentbehaviour(String ventbehaviour) {
		this.ventbehaviour = ventbehaviour;
	}

	public void setVentilationType(String ventilationType) {
		this.ventilationType = ventilationType;
	}

	public void setMedicineid(String medicineid) {
		this.medicineid = medicineid;
	}

	public void setRdsplan(String rdsplan) {
		this.rdsplan = rdsplan;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public void setReasseshoursdays(String reasseshoursdays) {
		this.reasseshoursdays = reasseshoursdays;
	}

	public void setSilvermanscoreid(String silvermanscoreid) {
		this.silvermanscoreid = silvermanscoreid;
	}

	public void setRdscause(String rdscause) {
		this.rdscause = rdscause;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public List<String> getTreatmentActionList() {
		return treatmentActionList;
	}

	
	public List<String> getRdsCauseList() {
		return rdsCauseList;
	}

	public void setTreatmentActionList(List<String> treatmentActionList) {
		this.treatmentActionList = treatmentActionList;
	}

	
	public List<String> getMedicineIdList() {
		return medicineIdList;
	}

	public void setMedicineIdList(List<String> medicineIdList) {
		this.medicineIdList = medicineIdList;
	}

	public void setRdsCauseList(List<String> rdsCauseList) {
		this.rdsCauseList = rdsCauseList;
	}

	public List<String> getRdsPlanList() {
		return rdsPlanList;
	}

	public void setRdsPlanList(List<String> rdsPlanList) {
		this.rdsPlanList = rdsPlanList;
	}

	public String getUhid() {
		return uhid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public List<String> getOrderInvestigationList() {
		return orderInvestigationList;
	}

	public void setOrderInvestigationList(List<String> investOrder) {
		this.orderInvestigationList = investOrder;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getRdsPlanOthers() {
		return rdsPlanOthers;
	}

	public void setRdsPlanOthers(String rdsPlanOthers) {
		this.rdsPlanOthers = rdsPlanOthers;
	}

	public Boolean getIsVentModeUpgrade() {
		return isVentModeUpgrade;
	}

	public void setIsVentModeUpgrade(Boolean isVentModeUpgrade) {
		this.isVentModeUpgrade = isVentModeUpgrade;
	}

	public List<String> getOrderInvestigationStringList() {
		return orderInvestigationStringList;
	}

	public void setOrderInvestigationStringList(
			List<String> orderInvestigationStringList) {
		this.orderInvestigationStringList = orderInvestigationStringList;
	}
	
	public Boolean getIsinsuredone() {
		return isinsuredone;
	}

	public String getRsInsureType() {
		return rsInsureType;
	}

	public void setIsinsuredone(Boolean isinsuredone) {
		this.isinsuredone = isinsuredone;
	}

	public void setRsInsureType(String rsInsureType) {
		this.rsInsureType = rsInsureType;
	}

	public String getSystolicBp() {
		return systolicBp;
	}

	public void setSystolicBp(String systolicBp) {
		this.systolicBp = systolicBp;
	}

	public String getOxgenationIndex() {
		return oxgenationIndex;
	}

	public void setOxgenationIndex(String oxgenationIndex) {
		this.oxgenationIndex = oxgenationIndex;
	}

	public String getLabPreductal() {
		return labPreductal;
	}

	public void setLabPreductal(String labPreductal) {
		this.labPreductal = labPreductal;
	}

	public String getLabPostductal() {
		return labPostductal;
	}

	public void setLabPostductal(String labPostductal) {
		this.labPostductal = labPostductal;
	}

	public String getLabileDifference() {
		return labileDifference;
	}

	public void setLabileDifference(String labileDifference) {
		this.labileDifference = labileDifference;
	}

	public Boolean getLabileOxygenation() {
		return labileOxygenation;
	}

	public void setLabileOxygenation(Boolean labileOxygenation) {
		this.labileOxygenation = labileOxygenation;
	}

	public String getPulmonaryPressure() {
		return pulmonaryPressure;
	}

	public void setPulmonaryPressure(String pulmonaryPressure) {
		this.pulmonaryPressure = pulmonaryPressure;
	}

	public Boolean getTransillumination() {
		return transillumination;
	}

	public void setTransillumination(Boolean transillumination) {
		this.transillumination = transillumination;
	}

	public String getPphnIno() {
		return pphnIno;
	}

	public void setPphnIno(String pphnIno) {
		this.pphnIno = pphnIno;
	}

	public String getMethaemoglobinLevel() {
		return methaemoglobinLevel;
	}

	public void setMethaemoglobinLevel(String methaemoglobinLevel) {
		this.methaemoglobinLevel = methaemoglobinLevel;
	}

	public String getSildenafil() {
		return sildenafil;
	}

	public void setSildenafil(String sildenafil) {
		this.sildenafil = sildenafil;
	}

	public Boolean getNeedleAspiration() {
		return needleAspiration;
	}

	public void setNeedleAspiration(Boolean needleAspiration) {
		this.needleAspiration = needleAspiration;
	}

	public Boolean getChesttubeInsertion() {
		return chesttubeInsertion;
	}

	public void setChesttubeInsertion(Boolean chesttubeInsertion) {
		this.chesttubeInsertion = chesttubeInsertion;
	}

	public Integer getAspirationPlanTime() {
		return aspirationPlanTime;
	}

	public void setAspirationPlanTime(Integer aspirationPlanTime) {
		this.aspirationPlanTime = aspirationPlanTime;
	}

	public String getAspirationMinhrsdays() {
		return aspirationMinhrsdays;
	}

	public void setAspirationMinhrsdays(String aspirationMinhrsdays) {
		this.aspirationMinhrsdays = aspirationMinhrsdays;
	}

	public Integer getChesttubePlanTime() {
		return chesttubePlanTime;
	}

	public void setChesttubePlanTime(Integer chesttubePlanTime) {
		this.chesttubePlanTime = chesttubePlanTime;
	}

	public String getChesttubeMinhrsdays() {
		return chesttubeMinhrsdays;
	}

	public void setChesttubeMinhrsdays(String chesttubeMinhrsdays) {
		this.chesttubeMinhrsdays = chesttubeMinhrsdays;
	}

	public String getInotropes() {
		return inotropes;
	}

	public void setInotropes(String inotropes) {
		this.inotropes = inotropes;
	}

	public String getPphnEventstatus() {
		return pphnEventstatus;
	}

	public void setPphnEventstatus(String pphnEventstatus) {
		this.pphnEventstatus = pphnEventstatus;
	}

	public String getPhenumothoraxEventstatus() {
		return phenumothoraxEventstatus;
	}

	public void setPhenumothoraxEventstatus(String phenumothoraxEventstatus) {
		this.phenumothoraxEventstatus = phenumothoraxEventstatus;
	}

	public String getSurvantaDoseCount() {
		return survantaDoseCount;
	}

	public void setSurvantaDoseCount(String survantaDoseCount) {
		this.survantaDoseCount = survantaDoseCount;
	}
}