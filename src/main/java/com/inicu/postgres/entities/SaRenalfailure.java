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

/**
 * The persistent class for the sa_renalfailure database table.
 * 
 */
/**
*
*
*Purpose: adding required columns in Renal assessment 

*@Updated on: June 29 2019
*@author: Shweta Nichani Mohanani
*/
@Entity
@Table(name="sa_renalfailure")
@NamedQuery(name="SaRenalfailure.findAll", query="SELECT s FROM SaRenalfailure s")
public class SaRenalfailure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long renalid;

	private String causeofrf;

	private Timestamp creationtime;

	private String maxbun;



	private String maxscreatetine;

	private String minimumurineoutput;

	private String minweight;

	private Timestamp modificationtime;

	@Column(columnDefinition="bool")
	private Boolean renalfailure;

	private String uhid;

	@Transient
	private boolean edit;

	
	
	
	@Transient
	private String creationDate;

	private String loggeduser;
	
	//change 10 march 2017
	@Transient
	private String comments;

	@Column(columnDefinition="bool")
	private Boolean iugr;

	@Column(columnDefinition="bool")
	private Boolean possibledrugtoxicity;

	@Column(columnDefinition="bool")
	private Boolean rrt;

	@Column(columnDefinition="bool")
	private Boolean sepsis;

	private String stageofaki;
	
	private String comment;
	
	////Added by ekpreet
	@Column(name="renal_status")
	private String renalstatus;
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateofbirth;

	
	@Column(name = "age_of_onset")
	private Integer ageOfOnset;
	
	@Column(name = "ageofonset_inhours",columnDefinition="bool")
	private Boolean ageOfOnsetInHours;
	
	@Column(name ="age_at_assessment")
	private Integer ageAtAssessment;
	
	@Column(name="ageatassessment_inhours" , columnDefinition="bool")
	private Boolean isAgeAtAssessmentInHours;
	
	@Column(name="urine_output" , columnDefinition="float4")
	private Float  urineOutput;
	
	@Column(name="urine_output_time")
	private Timestamp urineOutputTime;
	
	@Column(name="urine_output_time_by_doctor")
	private Timestamp urineOutputTimeByDoctor;
	
	
	@Column(name="bun_value" , columnDefinition = "float4")
	private Float bunValue;
	
	@Column(name="bun_time")
	private Timestamp bunTime; 
	
	@Column(name="bun_time_by_doctor")
	private Timestamp bunTimeByDoctor; 
	
	
	@Column(name="max_bun_value" ,columnDefinition = "float4")
	private Float maxBunValue;
	
	@Column(name="max_bun_time")
	private Timestamp maxBunTime;
	
	@Column(name="mbun_time_by_doctor")
	private Timestamp maxBunTimeByDoctor; 
	
	
	
	
	@Column(name="serum_creatanine_value",columnDefinition = "float4")
	private Float serumCreatanineValue;
	
	@Column(name="serum_creatinine_time")
	private Timestamp serumCreatinineTime;
	
	@Column(name="screatinine_time_by_doctor")
	private Timestamp serumCreatinineTimeByDoctor; 
	
	
	@Column(name="max_serum_creatinine_value",columnDefinition = "float4")
	private Float maxSerumCreatinineValue;
	
	@Column(name="max_serum_creatinine_time")
	private Timestamp maxSerumCreatinineTime;
	
	@Column(name="mserum_creatinine_time_by_doctor")
	private Timestamp maxSerumCreatinineTimeByDoctor;
	
	
	@Column(name="renal_abnormality" , columnDefinition="bool")
	private Boolean isRenalAbnormality;
	
	@Column(name="screatinine_feature")
	private String screatinineFeature;
	
	@Column(name="creatinine_clearance_value" , columnDefinition = "float4")
	private Float creatinineClearance;
	
	
	@Column(name="poor_urinary_stream")
	private String isPoorUrinaryStream;
	
	@Column(name="renal_failure", columnDefinition="bool")
	private Boolean isRenalFailure;
	
	@Column(name="urine_output_feature")
	private String urineOutputFeature;
	
	@Column(name="bun_feature")
	private String bunFeature;
	
	@Column(name="hematuria_feature")
	private String hematuriaFeature;
	
	@Column(name="episode_number")
	private Integer episodeNumber;
	
	@Column(name="tachycardia" , columnDefinition="bool")
	private Boolean isTachychardia;
	
	@Column(name="is_high_bp" , columnDefinition="bool")
	private Boolean isHighBP;
	
	@Column(name="is_low_bp",columnDefinition="bool")
	private Boolean isLowBP;
	
	@Column(name="edema" , columnDefinition="bool")
	private Boolean isEdema;
	
	@Column(name="acidosis" , columnDefinition="bool")
	private Boolean acidosis; 
	
	@Column(name="palpable_renal_mass" , columnDefinition="bool")
	private Boolean palpableRenalMass;
	
	@Column(name="renal_echo_genecity")
	private String renalEchoGenecity;
	
	@Column(name="bladder_distended" , columnDefinition="bool" )
	private Boolean isBladderDistended;
	
	@Column(name="renal_abnormal",columnDefinition="bool")
	private Boolean isRenalAbnormal;
	
	@Column(name="bladder_wall")
	private String bladderWall;
	
	@Column(name="pelvicalyceal_system")
	private String pelvicalycealSystem;
	
	@Column(name="diverticuli" , columnDefinition="bool")
	private Boolean isDiverticuli;
	
	@Column(name="ureters")
	private String ureters;
	
	@Column(name="residual_urine_value" , columnDefinition="float4")
	private Float residualUrineValue;
	
	@Column(name="medication_text")
	private String medicationText;
	
	@Column(name="actionduration")
	private Integer actionduration;

	@Column(name="isactiondurationinhours")
	private String isactiondurationinhours;

	@Column(name="plancomment")
	private String plancomment;
	
	@Column(name="palpable_renal_mass_value")
	private String palpableRenalValue;
	
	@Column(name="edema_options")
	private String edemaOptions;
	
	@Column(name="acidosis_ph_value" , columnDefinition="float4")
	private Float acidosisPhValue;
	
	//Added by ekpreet on 23rd jan 19
	
	
	@Column(name="order_investigation")
	private String orderInvestigation;
	
	@Transient
	private List orderinvestigationList;

	@Column(name="investigation_ordered_boolean" ,columnDefinition = "bool")
	private Boolean investigationOrderedBooleanValue;
	
	// for multiple treatment plans(reassess,investigation,other)
	@Transient
	private List<String> treatmentactionplanlist;
	
	@Transient
	private String pastTreatmentActionStr;
	
	@Transient
	private String treatmentActionSelected;
	
	@Transient
	private String pastOrderInvestigationStr;
	
	@Transient
	Boolean isEdit;
	
	@Column(name="associated_event")
	private String associatedEvent;
	
	@Transient
	private List actiontypeList;
	
	@Column(name="action_type")
	private String actionType;
	
	@Transient
	private List bunTestResultsList;
	
	@Transient
	private List serumCreatinineTestResults;

	private String episodeid;
	
	@Transient
	private String orderlist;
	
	@Transient
	private String CauseList;

	public String getCauseList() {
		return CauseList;
	}

	public void setCauseList(String causeList) {
		CauseList = causeList;
	}

	public String getTreatmentActionSelected() {
		return treatmentActionSelected;
	}

	public void setTreatmentActionSelected(String treatmentActionSelected) {
		this.treatmentActionSelected = treatmentActionSelected;
	}

		// added by ekpreet on 23rd Jan '19.
		@Transient
		private Boolean isNewEntry;
		
		@Transient
		private List causeOfRenalList;
		
		@Column(name="cause_of_renal")
		private String causeOfRenal;
		
		@Column(name="diverticuli_value")
		private String diverticuliValue;
		
		@Column(name="renal_abnormal_value")
		private String renalAbnormalValue;
		
		@Column(name="fena_interpretation" , columnDefinition="float4")
		private Float fenaInterpretation;
		
		@Column(name="rfi_interpretation", columnDefinition="float4")
		private Float rfiInterpretation;
		
		
		@Column(name="left_abd_value", columnDefinition="float4")
		private Float leftAbdValue;
		
		@Column(name="right_abd_value", columnDefinition="float4")
		private Float rightAbdValue;
		
		@Column(name="bladder_distended_feature")
		private String bladderDistendedFeature;
		
		
		@Column(name="plan_reassess_time", columnDefinition="float4")
		private Float planReassesTime;
		
		@Column(name="plan_hms")
		private String planHoursDaysMin;
		
		@Column(name="pre_renal_causes")
		private String preRenalCauses;
		
		@Column(name="post_renal_causes")
		private String postRenalCauses;
		
		@Column(name="intrinsic_causes")
		private String intrinsicCauses;
		
		@Column(name="acidosis_pco3_value" , columnDefinition="float4")
		private Float acidosisPco3Value;
		
		@Column(name="acidosis_hco3_value" , columnDefinition="float4")
		private Float acidosisHco3Value;
		
		@Column(name="acidosis_be_value" , columnDefinition="float4")
		private Float acidosisBeValue;
		
		@Column(name = "assessment_time")
		private Timestamp assessmentTime;
		
		@Column(name="min_bp" , columnDefinition="float4")
		private Float minBp;
		
		@Column(name="max_bp" , columnDefinition="float4")
		private Float maxBp;
		
		@Transient
		private Float babyLength;
		
		@Column(name ="urinary_sodium" , columnDefinition="float4")
		private Float urinarySodium;
		
		@Column(name = "plasma_sodium" , columnDefinition="float4")
		private Float plasmaSodium;
		
		@Column(name = "plasma_creatinine" , columnDefinition="float4")
		private Float plasmaCreatinine;
		
		@Column(name = "urinary_creatinine" , columnDefinition="float4")
		private Float urinaryCreatinine;
		
		@Column(name = "hydronephrosis" , columnDefinition="bool")
		private Boolean antenatalHydronephrosis;
		
		@Column(name="inference")
		private String inference;
		
		@Column(name="uti",columnDefinition="bool")
		private Boolean uti;
		
		@Column(name="mcu_vur",columnDefinition="bool")
		private Boolean mcuVur;
		
		@Column(name="grade_of_reflux")
		private String gradeOfReflux;
		
		@Column(name="usg_gestation")
		private Integer usgGestation;
		
		@Column(name="ant_post_diameter")
		private Integer anteriorPosteriorDiameter;
		
		@Column(name="tortuous",columnDefinition="bool")
		private Boolean tortuous;
		
		@Column(name = "bladder_volume" , columnDefinition="float4")
		private Float bladderVolume;
		
		
		public SaRenalfailure() {
			this.isNewEntry = true;
			this.ageOfOnsetInHours = true;
			this.isAgeAtAssessmentInHours = true;
		}

	
	
	
	
	
	
	
	public Boolean getAntenatalHydronephrosis() {
			return antenatalHydronephrosis;
		}

		public void setAntenatalHydronephrosis(Boolean antenatalHydronephrosis) {
			this.antenatalHydronephrosis = antenatalHydronephrosis;
		}

		public String getInference() {
			return inference;
		}

		public void setInference(String inference) {
			this.inference = inference;
		}

		public Boolean getUti() {
			return uti;
		}

		public void setUti(Boolean uti) {
			this.uti = uti;
		}

		public Boolean getMcuVur() {
			return mcuVur;
		}

		public void setMcuVur(Boolean mcuVur) {
			this.mcuVur = mcuVur;
		}

		public String getGradeOfReflux() {
			return gradeOfReflux;
		}

		public String getOrderlist() {
			return orderlist;
		}

		public void setOrderlist(String orderlist) {
			this.orderlist = orderlist;
		}

		public void setGradeOfReflux(String gradeOfReflux) {
			this.gradeOfReflux = gradeOfReflux;
		}

		public Integer getUsgGestation() {
			return usgGestation;
		}

		public void setUsgGestation(Integer usgGestation) {
			this.usgGestation = usgGestation;
		}

		public Integer getAnteriorPosteriorDiameter() {
			return anteriorPosteriorDiameter;
		}

		public void setAnteriorPosteriorDiameter(Integer anteriorPosteriorDiameter) {
			this.anteriorPosteriorDiameter = anteriorPosteriorDiameter;
		}

		public Boolean getTortuous() {
			return tortuous;
		}

		public void setTortuous(Boolean tortuous) {
			this.tortuous = tortuous;
		}

		public Float getBladderVolume() {
			return bladderVolume;
		}

		public void setBladderVolume(Float bladderVolume) {
			this.bladderVolume = bladderVolume;
		}

	public Float getBabyLength() {
		return babyLength;
	}

	public void setBabyLength(Float babyLength) {
		this.babyLength = babyLength;
	}

	public Float getMinBp() {
		return minBp;
	}

	public void setMinBp(Float minBp) {
		this.minBp = minBp;
	}

	public Float getMaxBp() {
		return maxBp;
	}

	public void setMaxBp(Float maxBp) {
		this.maxBp = maxBp;
	}

	public Timestamp getUrineOutputTimeByDoctor() {
		return urineOutputTimeByDoctor;
	}

	public void setUrineOutputTimeByDoctor(Timestamp urineOutputTimeByDoctor) {
		this.urineOutputTimeByDoctor = urineOutputTimeByDoctor;
	}

	public Timestamp getBunTimeByDoctor() {
		return bunTimeByDoctor;
	}

	public void setBunTimeByDoctor(Timestamp bunTimeByDoctor) {
		this.bunTimeByDoctor = bunTimeByDoctor;
	}

	public Timestamp getMaxBunTimeByDoctor() {
		return maxBunTimeByDoctor;
	}

	public void setMaxBunTimeByDoctor(Timestamp maxBunTimeByDoctor) {
		this.maxBunTimeByDoctor = maxBunTimeByDoctor;
	}

	public Timestamp getSerumCreatinineTimeByDoctor() {
		return serumCreatinineTimeByDoctor;
	}

	public void setSerumCreatinineTimeByDoctor(Timestamp serumCreatinineTimeByDoctor) {
		this.serumCreatinineTimeByDoctor = serumCreatinineTimeByDoctor;
	}

	public Timestamp getMaxSerumCreatinineTimeByDoctor() {
		return maxSerumCreatinineTimeByDoctor;
	}

	public void setMaxSerumCreatinineTimeByDoctor(Timestamp maxSerumCreatinineTimeByDoctor) {
		this.maxSerumCreatinineTimeByDoctor = maxSerumCreatinineTimeByDoctor;
	}

	
	public String getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(String orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public Float getUrinarySodium() {
		return urinarySodium;
	}

	public void setUrinarySodium(Float urinarySodium) {
		this.urinarySodium = urinarySodium;
	}

	public Float getPlasmaSodium() {
		return plasmaSodium;
	}

	public void setPlasmaSodium(Float plasmaSodium) {
		this.plasmaSodium = plasmaSodium;
	}

	public Float getPlasmaCreatinine() {
		return plasmaCreatinine;
	}

	public void setPlasmaCreatinine(Float plasmaCreatinine) {
		this.plasmaCreatinine = plasmaCreatinine;
	}

	public Float getUrinaryCreatinine() {
		return urinaryCreatinine;
	}

	public void setUrinaryCreatinine(Float urinaryCreatinine) {
		this.urinaryCreatinine = urinaryCreatinine;
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

	public List<String> getTreatmentactionplanlist() {
		return treatmentactionplanlist;
	}

	public void setTreatmentactionplanlist(List<String> treatmentactionplanlist) {
		this.treatmentactionplanlist = treatmentactionplanlist;
	}

	public String getPastTreatmentActionStr() {
		return pastTreatmentActionStr;
	}

	public void setPastTreatmentActionStr(String pastTreatmentActionStr) {
		this.pastTreatmentActionStr = pastTreatmentActionStr;
	}

	public String getPastOrderInvestigationStr() {
		return pastOrderInvestigationStr;
	}

	public void setPastOrderInvestigationStr(String pastOrderInvestigationStr) {
		this.pastOrderInvestigationStr = pastOrderInvestigationStr;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getAssociatedEvent() {
		return associatedEvent;
	}

	public void setAssociatedEvent(String associatedEvent) {
		this.associatedEvent = associatedEvent;
	}

	public List getActiontypeList() {
		return actiontypeList;
	}

	public void setActiontypeList(List actiontypeList) {
		this.actiontypeList = actiontypeList;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public List getCauseOfRenalList() {
		return causeOfRenalList;
	}

	public void setCauseOfRenalList(List causeOfRenalList) {
		this.causeOfRenalList = causeOfRenalList;
	}

	public String getCauseOfRenal() {
		return causeOfRenal;
	}

	public void setCauseOfRenal(String causeOfRenal) {
		this.causeOfRenal = causeOfRenal;
	}

	public String getDiverticuliValue() {
		return diverticuliValue;
	}

	public void setDiverticuliValue(String diverticuliValue) {
		this.diverticuliValue = diverticuliValue;
	}

	public String getRenalAbnormalValue() {
		return renalAbnormalValue;
	}

	public void setRenalAbnormalValue(String renalAbnormalValue) {
		this.renalAbnormalValue = renalAbnormalValue;
	}

	public Float getFenaInterpretation() {
		return fenaInterpretation;
	}

	public void setFenaInterpretation(Float fenaInterpretation) {
		this.fenaInterpretation = fenaInterpretation;
	}

	public Float getRfiInterpretation() {
		return rfiInterpretation;
	}

	public void setRfiInterpretation(Float rfiInterpretation) {
		this.rfiInterpretation = rfiInterpretation;
	}

	public Float getLeftAbdValue() {
		return leftAbdValue;
	}

	public void setLeftAbdValue(Float leftAbdValue) {
		this.leftAbdValue = leftAbdValue;
	}

	public Float getRightAbdValue() {
		return rightAbdValue;
	}

	public void setRightAbdValue(Float rightAbdValue) {
		this.rightAbdValue = rightAbdValue;
	}

	
	public Float getPlanReassesTime() {
		return planReassesTime;
	}

	public void setPlanReassesTime(Float planReassesTime) {
		this.planReassesTime = planReassesTime;
	}

	public String getPlanHoursDaysMin() {
		return planHoursDaysMin;
	}

	public void setPlanHoursDaysMin(String planHoursDaysMin) {
		this.planHoursDaysMin = planHoursDaysMin;
	}

	public String getPreRenalCauses() {
		return preRenalCauses;
	}

	public void setPreRenalCauses(String preRenalCauses) {
		this.preRenalCauses = preRenalCauses;
	}

	public String getPostRenalCauses() {
		return postRenalCauses;
	}

	public void setPostRenalCauses(String postRenalCauses) {
		this.postRenalCauses = postRenalCauses;
	}

	public String getIntrinsicCauses() {
		return intrinsicCauses;
	}

	public void setIntrinsicCauses(String intrinsicCauses) {
		this.intrinsicCauses = intrinsicCauses;
	}

		

	
	public Timestamp getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Timestamp assessmentTime) {
		this.assessmentTime = assessmentTime;
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
	public String getPlancomment() {
		return plancomment;
	}

	public void setPlancomment(String plancomment) {
		this.plancomment = plancomment;
	}

	public String getPalpableRenalValue() {
		return palpableRenalValue;
	}

	public void setPalpableRenalValue(String palpableRenalValue) {
		this.palpableRenalValue = palpableRenalValue;
	}

	public String getEdemaOptions() {
		return edemaOptions;
	}

	public void setEdemaOptions(String edemaOptions) {
		this.edemaOptions = edemaOptions;
	}

	public Float getAcidosisPhValue() {
		return acidosisPhValue;
	}

	public void setAcidosisPhValue(Float acidosisPhValue) {
		this.acidosisPhValue = acidosisPhValue;
	}

	public Float getAcidosisPco3Value() {
		return acidosisPco3Value;
	}

	public void setAcidosisPco3Value(Float acidosisPco3Value) {
		this.acidosisPco3Value = acidosisPco3Value;
	}

	public Float getAcidosisHco3Value() {
		return acidosisHco3Value;
	}

	public void setAcidosisHco3Value(Float acidosisHco3Value) {
		this.acidosisHco3Value = acidosisHco3Value;
	}

	public Float getAcidosisBeValue() {
		return acidosisBeValue;
	}

	public void setAcidosisBeValue(Float acidosisBeValue) {
		this.acidosisBeValue = acidosisBeValue;
	}

	


	
	public Long getRenalid() {
		return renalid;
	}

	public void setRenalid(Long renalid) {
		this.renalid = renalid;
	}

	public String getCauseofrf() {
		return causeofrf;
	}

	public void setCauseofrf(String causeofrf) {
		this.causeofrf = causeofrf;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getMaxbun() {
		return maxbun;
	}

	public void setMaxbun(String maxbun) {
		this.maxbun = maxbun;
	}

	public String getMaxscreatetine() {
		return maxscreatetine;
	}

	public void setMaxscreatetine(String maxscreatetine) {
		this.maxscreatetine = maxscreatetine;
	}

	public String getMinimumurineoutput() {
		return minimumurineoutput;
	}

	public void setMinimumurineoutput(String minimumurineoutput) {
		this.minimumurineoutput = minimumurineoutput;
	}

	public String getMinweight() {
		return minweight;
	}

	public void setMinweight(String minweight) {
		this.minweight = minweight;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getRenalfailure() {
		return renalfailure;
	}

	public void setRenalfailure(Boolean renalfailure) {
		this.renalfailure = renalfailure;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getIugr() {
		return iugr;
	}

	public void setIugr(Boolean iugr) {
		this.iugr = iugr;
	}

	public Boolean getPossibledrugtoxicity() {
		return possibledrugtoxicity;
	}

	public void setPossibledrugtoxicity(Boolean possibledrugtoxicity) {
		this.possibledrugtoxicity = possibledrugtoxicity;
	}

	public Boolean getRrt() {
		return rrt;
	}

	public void setRrt(Boolean rrt) {
		this.rrt = rrt;
	}

	public Boolean getSepsis() {
		return sepsis;
	}

	public void setSepsis(Boolean sepsis) {
		this.sepsis = sepsis;
	}

	public String getStageofaki() {
		return stageofaki;
	}

	public void setStageofaki(String stageofaki) {
		this.stageofaki = stageofaki;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRenalstatus() {
		return renalstatus;
	}

	public void setRenalstatus(String renalstatus) {
		this.renalstatus = renalstatus;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Integer getAgeOfOnset() {
		return ageOfOnset;
	}

	public void setAgeOfOnset(Integer ageOfOnset) {
		this.ageOfOnset = ageOfOnset;
	}

	public Boolean getAgeOfOnsetInHours() {
		return ageOfOnsetInHours;
	}

	public void setAgeOfOnsetInHours(Boolean ageOfOnsetInHours) {
		this.ageOfOnsetInHours = ageOfOnsetInHours;
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

	
	public Float getUrineOutput() {
		return urineOutput;
	}

	public void setUrineOutput(Float urineOutput) {
		this.urineOutput = urineOutput;
	}

	public Timestamp getUrineOutputTime() {
		return urineOutputTime;
	}

	public void setUrineOutputTime(Timestamp urineOutputTime) {
		this.urineOutputTime = urineOutputTime;
	}

	public Float getBunValue() {
		return bunValue;
	}

	public void setBunValue(Float bunValue) {
		this.bunValue = bunValue;
	}

	public Timestamp getBunTime() {
		return bunTime;
	}

	public void setBunTime(Timestamp bunTime) {
		this.bunTime = bunTime;
	}

	public Float getMaxBunValue() {
		return maxBunValue;
	}

	public void setMaxBunValue(Float maxBunValue) {
		this.maxBunValue = maxBunValue;
	}

	public Timestamp getMaxBunTime() {
		return maxBunTime;
	}

	public void setMaxBunTime(Timestamp maxBunTime) {
		this.maxBunTime = maxBunTime;
	}

	public Float getSerumCreatanineValue() {
		return serumCreatanineValue;
	}

	public void setSerumCreatanineValue(Float serumCreatanineValue) {
		this.serumCreatanineValue = serumCreatanineValue;
	}

	public Timestamp getSerumCreatinineTime() {
		return serumCreatinineTime;
	}

	public void setSerumCreatinineTime(Timestamp serumCreatinineTime) {
		this.serumCreatinineTime = serumCreatinineTime;
	}

	public Float getMaxSerumCreatinineValue() {
		return maxSerumCreatinineValue;
	}

	public void setMaxSerumCreatinineValue(Float maxSerumCreatinineValue) {
		this.maxSerumCreatinineValue = maxSerumCreatinineValue;
	}

	
	public Timestamp getMaxSerumCreatinineTime() {
		return maxSerumCreatinineTime;
	}

	public void setMaxSerumCreatinineTime(Timestamp maxSerumCreatinineTime) {
		this.maxSerumCreatinineTime = maxSerumCreatinineTime;
	}

	public Boolean getIsRenalAbnormality() {
		return isRenalAbnormality;
	}

	public void setIsRenalAbnormality(Boolean isRenalAbnormality) {
		this.isRenalAbnormality = isRenalAbnormality;
	}

	public String getScreatinineFeature() {
		return screatinineFeature;
	}

	public void setScreatinineFeature(String screatinineFeature) {
		this.screatinineFeature = screatinineFeature;
	}

	public Float getCreatinineClearance() {
		return creatinineClearance;
	}

	public void setCreatinineClearance(Float creatinineClearance) {
		this.creatinineClearance = creatinineClearance;
	}

	
	public Boolean getIsRenalFailure() {
		return isRenalFailure;
	}

	public void setIsRenalFailure(Boolean isRenalFailure) {
		this.isRenalFailure = isRenalFailure;
	}

	public String getUrineOutputFeature() {
		return urineOutputFeature;
	}

	public void setUrineOutputFeature(String urineOutputFeature) {
		this.urineOutputFeature = urineOutputFeature;
	}

	public String getBunFeature() {
		return bunFeature;
	}

	public void setBunFeature(String bunFeature) {
		this.bunFeature = bunFeature;
	}

	
	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public Boolean getIsTachychardia() {
		return isTachychardia;
	}

	public void setIsTachychardia(Boolean isTachychardia) {
		this.isTachychardia = isTachychardia;
	}

	public Boolean getIsHighBP() {
		return isHighBP;
	}

	public void setIsHighBP(Boolean isHighBP) {
		this.isHighBP = isHighBP;
	}

	public Boolean getIsLowBP() {
		return isLowBP;
	}

	public void setIsLowBP(Boolean isLowBP) {
		this.isLowBP = isLowBP;
	}

	public Boolean getIsEdema() {
		return isEdema;
	}

	public void setIsEdema(Boolean isEdema) {
		this.isEdema = isEdema;
	}

	public Boolean getAcidosis() {
		return acidosis;
	}

	public void setAcidosis(Boolean acidosis) {
		this.acidosis = acidosis;
	}

	public Boolean getPalpableRenalMass() {
		return palpableRenalMass;
	}

	public void setPalpableRenalMass(Boolean palpableRenalMass) {
		this.palpableRenalMass = palpableRenalMass;
	}

	public String getRenalEchoGenecity() {
		return renalEchoGenecity;
	}

	public void setRenalEchoGenecity(String renalEchoGenecity) {
		this.renalEchoGenecity = renalEchoGenecity;
	}

	
	public Boolean getIsRenalAbnormal() {
		return isRenalAbnormal;
	}

	public void setIsRenalAbnormal(Boolean isRenalAbnormal) {
		this.isRenalAbnormal = isRenalAbnormal;
	}

	public String getBladderWall() {
		return bladderWall;
	}

	public void setBladderWall(String bladderWall) {
		this.bladderWall = bladderWall;
	}

	public String getPelvicalycealSystem() {
		return pelvicalycealSystem;
	}

	public void setPelvicalycealSystem(String pelvicalycealSystem) {
		this.pelvicalycealSystem = pelvicalycealSystem;
	}

	public Boolean getIsDiverticuli() {
		return isDiverticuli;
	}

	public void setIsDiverticuli(Boolean isDiverticuli) {
		this.isDiverticuli = isDiverticuli;
	}

	public String getUreters() {
		return ureters;
	}

	public void setUreters(String ureters) {
		this.ureters = ureters;
	}

	public Float getResidualUrineValue() {
		return residualUrineValue;
	}

	public void setResidualUrineValue(Float residualUrineValue) {
		this.residualUrineValue = residualUrineValue;
	}

	public String getMedicationText() {
		return medicationText;
	}

	public void setMedicationText(String medicationText) {
		this.medicationText = medicationText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIsPoorUrinaryStream() {
		return isPoorUrinaryStream;
	}

	public void setIsPoorUrinaryStream(String isPoorUrinaryStream) {
		this.isPoorUrinaryStream = isPoorUrinaryStream;
	}

	public String getHematuriaFeature() {
		return hematuriaFeature;
	}

	public void setHematuriaFeature(String hematuriaFeature) {
		this.hematuriaFeature = hematuriaFeature;
	}

	public Boolean getIsBladderDistended() {
		return isBladderDistended;
	}

	public void setIsBladderDistended(Boolean isBladderDistended) {
		this.isBladderDistended = isBladderDistended;
	}

	public String getBladderDistendedFeature() {
		return bladderDistendedFeature;
	}

	public void setBladderDistendedFeature(String bladderDistendedFeature) {
		this.bladderDistendedFeature = bladderDistendedFeature;
	}

	public List getBunTestResultsList() {
		return bunTestResultsList;
	}

	public void setBunTestResultsList(List bunTestResultsList) {
		this.bunTestResultsList = bunTestResultsList;
	}

	public List getSerumCreatinineTestResults() {
		return serumCreatinineTestResults;
	}

	public void setSerumCreatinineTestResults(List serumCreatinineTestResults) {
		this.serumCreatinineTestResults = serumCreatinineTestResults;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}
}
		