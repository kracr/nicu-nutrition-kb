package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.inicu.models.TestResultObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * The persistent class for the discharge_outcome database table.
 * 
 */
@Entity
@Table(name="discharge_outcome")
@NamedQuery(name="DischargeOutcome.findAll", query="SELECT d FROM DischargeOutcome d")
public class DischargeOutcome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name="discharge_outcome_id")
	private Long dischargeoutcomeid;


	private Timestamp creationtime;
	
	@NotNull
	private Timestamp entrytime;
	
	private Timestamp modificationtime;
	
	@Column(name="deathtime")
	private Timestamp deathDate;
	
	@NotBlank
	private String uhid;
	
	
	@NotBlank
	@Column(name="outcome_type") 
	private String outcomeType;
	
	
	@NotNull
	@Column(name="make_summary_flag",columnDefinition = "bool")
	private Boolean makeSummaryFlag;
	
	@NotBlank
	private String episodeid;
	
	@Transient
	private String reason;

	@Transient
	private Timestamp hisDischargeDate;

	@Transient
	private List<BabyPrescription> medicationList;
	
	private Timestamp appointmenttime;
	
	@Transient
	private boolean isAssessmentSubmit;
	
	@Transient
	private Timestamp minDischargeDate;
	
	@Transient
	private String lastEntryPanel;
	
	@Transient
	private String labPrintFormat;

	@Transient
	private Timestamp lastProcedureEnddate;
		
	public boolean getAssessmentSubmit() {
		return isAssessmentSubmit;
	}

	public void setAssessmentSubmit(boolean isAssessmentSubmit) {
		this.isAssessmentSubmit = isAssessmentSubmit;
	}

	public Long getDischargeoutcomeid() {
		return dischargeoutcomeid;
	}

	public void setDischargeoutcomeid(Long dischargeoutcomeid) {
		this.dischargeoutcomeid = dischargeoutcomeid;
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

	public Timestamp getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Timestamp deathDate) {
		this.deathDate = deathDate;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getOutcomeType() {
		return outcomeType;
	}

	public void setOutcomeType(String outcomeType) {
		this.outcomeType = outcomeType;
	}

	public Boolean getMakeSummaryFlag() {
		return makeSummaryFlag;
	}

	public void setMakeSummaryFlag(Boolean makeSummaryFlag) {
		this.makeSummaryFlag = makeSummaryFlag;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Timestamp getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Timestamp entrytime) {
		this.entrytime = entrytime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public List<BabyPrescription> getMedicationList() {
		return medicationList;
	}
	
	public void setMedicationList(List<BabyPrescription> medicationList) {
		this.medicationList = medicationList;
	}

	public Timestamp getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(Timestamp appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public Timestamp getHisDischargeDate() {
		return hisDischargeDate;
	}

	public void setHisDischargeDate(Timestamp hisDischargeDate) {
		this.hisDischargeDate = hisDischargeDate;
	}

	public Timestamp getMinDischargeDate() {
		return minDischargeDate;
	}

	public void setMinDischargeDate(Timestamp minDischargeDate) {
		this.minDischargeDate = minDischargeDate;
	}

	public String getLastEntryPanel() {
		return lastEntryPanel;
	}

	public void setLastEntryPanel(String lastEntryPanel) {
		this.lastEntryPanel = lastEntryPanel;
	}

	public String getLabPrintFormat() {
		return labPrintFormat;
	}

	public void setLabPrintFormat(String labPrintFormat) {
		this.labPrintFormat = labPrintFormat;
	}

	public Timestamp getLastProcedureEnddate() {
		return lastProcedureEnddate;
	}

	public void setLastProcedureEnddate(Timestamp lastProcedureEnddate) {
		this.lastProcedureEnddate = lastProcedureEnddate;
	}
}