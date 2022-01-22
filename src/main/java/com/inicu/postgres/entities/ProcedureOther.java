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

/**
 * The persistent class for the procedure_other database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "procedure_other")
@NamedQuery(name = "ProcedureOther.findAll", query = "SELECT s FROM ProcedureOther s")

public class ProcedureOther implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long procedure_other_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;
	
	private Timestamp modificationtime;

	private String uhid;

	private String procedurename;

	@Column(name = "other_value", columnDefinition = "Float4")
	private Float otherValue;

	public Float getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(Float otherValue) {
		this.otherValue = otherValue;
	}

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	@Column(name = "other_type")
	private String otherType;

	private String othername;

	public String getOthername() {
		return othername;
	}

	public void setOthername(String othername) {
		this.othername = othername;
	}

	private String details;

	private String loggeduser;
	
	@Column(name = "et_suction_value", columnDefinition = "Float4")
	private Float etSuctionValue;
	
	@Column(name = "oral_suction_value", columnDefinition = "Float4")
	private Float oralSuctionValue;
	
	@Column(name = "chest_physiotherapy_value", columnDefinition = "Float4")
	private Float chestPhysiotherapyValue;
	
	@Column(name = "position_change_value", columnDefinition = "Float4")
	private Float positionChangeValue;
	
	@Column(name = "pulse_oximeter_value", columnDefinition = "Float4")
	private Float pulseOximeterValue;
	
	@Column(name = "abdominal_girth_value", columnDefinition = "Float4")
	private Float abdominalGirthValue;
	
	@Column(name = "stomach_aspiration_value", columnDefinition = "Float4")
	private Float stomachAspirationValue;
	
	@Column(name = "bp_measurement_value", columnDefinition = "Float4")
	private Float bpMeasurementValue;
	
	@Column(name = "cvp_measurement_value", columnDefinition = "Float4")
	private Float cvpMeasurementValue;
	
	@Column(name = "glucose_strip_value", columnDefinition = "Float4")
	private Float glucoseStripValue;
	
	@Column(name = "et_suction_type")
	private String etSuctionType;
	
	@Column(name = "oral_suction_type")
	private String oralSuctionType;
	
	@Column(name = "chest_physiotherapy_type")
	private String chestPhysiotherapyType;
	
	@Column(name = "position_change_type")
	private String positionChangeType;
	
	@Column(name = "pulse_oximeter_type")
	private String pulseOximeterType;
	
	@Column(name = "abdominal_girth_type")
	private String abdominalGirthType;
	
	@Column(name = "stomach_aspiration_type")
	private String stomachAspirationType;
	
	@Column(name = "bp_measurement_type")
	private String bpMeasurementType;
	
	@Column(name = "cvp_measurement_type")
	private String cvpMeasurementType;
	
	@Column(name = "glucose_strip_type")
	private String glucoseStripType;
	
	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp entrytime;
	
	@Column(name = "pulse_oximeter_value_comments")
	private String pulseOximeterValueComments;
	
	@Column(name = "chest_physiotherapy_comments")
	private String chestPhysiotherapyComments;

	public ProcedureOther() {
		super();
	}

	public Long getProcedure_other_id() {
		return procedure_other_id;
	}

	public void setProcedure_other_id(Long procedure_other_id) {
		this.procedure_other_id = procedure_other_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getProcedurename() {
		return procedurename;
	}

	public void setProcedurename(String procedurename) {
		this.procedurename = procedurename;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Float getEtSuctionValue() {
		return etSuctionValue;
	}

	public void setEtSuctionValue(Float etSuctionValue) {
		this.etSuctionValue = etSuctionValue;
	}

	public Float getOralSuctionValue() {
		return oralSuctionValue;
	}

	public void setOralSuctionValue(Float oralSuctionValue) {
		this.oralSuctionValue = oralSuctionValue;
	}

	public Float getChestPhysiotherapyValue() {
		return chestPhysiotherapyValue;
	}

	public void setChestPhysiotherapyValue(Float chestPhysiotherapyValue) {
		this.chestPhysiotherapyValue = chestPhysiotherapyValue;
	}

	public Float getPositionChangeValue() {
		return positionChangeValue;
	}

	public void setPositionChangeValue(Float positionChangeValue) {
		this.positionChangeValue = positionChangeValue;
	}

	public Float getPulseOximeterValue() {
		return pulseOximeterValue;
	}

	public void setPulseOximeterValue(Float pulseOximeterValue) {
		this.pulseOximeterValue = pulseOximeterValue;
	}

	public Float getAbdominalGirthValue() {
		return abdominalGirthValue;
	}

	public void setAbdominalGirthValue(Float abdominalGirthValue) {
		this.abdominalGirthValue = abdominalGirthValue;
	}

	public Float getStomachAspirationValue() {
		return stomachAspirationValue;
	}

	public void setStomachAspirationValue(Float stomachAspirationValue) {
		this.stomachAspirationValue = stomachAspirationValue;
	}

	public Float getBpMeasurementValue() {
		return bpMeasurementValue;
	}

	public void setBpMeasurementValue(Float bpMeasurementValue) {
		this.bpMeasurementValue = bpMeasurementValue;
	}

	public Float getCvpMeasurementValue() {
		return cvpMeasurementValue;
	}

	public void setCvpMeasurementValue(Float cvpMeasurementValue) {
		this.cvpMeasurementValue = cvpMeasurementValue;
	}

	public String getEtSuctionType() {
		return etSuctionType;
	}

	public void setEtSuctionType(String etSuctionType) {
		this.etSuctionType = etSuctionType;
	}

	public String getOralSuctionType() {
		return oralSuctionType;
	}

	public void setOralSuctionType(String oralSuctionType) {
		this.oralSuctionType = oralSuctionType;
	}

	public String getChestPhysiotherapyType() {
		return chestPhysiotherapyType;
	}

	public void setChestPhysiotherapyType(String chestPhysiotherapyType) {
		this.chestPhysiotherapyType = chestPhysiotherapyType;
	}

	public String getPositionChangeType() {
		return positionChangeType;
	}

	public void setPositionChangeType(String positionChangeType) {
		this.positionChangeType = positionChangeType;
	}

	public String getPulseOximeterType() {
		return pulseOximeterType;
	}

	public void setPulseOximeterType(String pulseOximeterType) {
		this.pulseOximeterType = pulseOximeterType;
	}

	public String getAbdominalGirthType() {
		return abdominalGirthType;
	}

	public void setAbdominalGirthType(String abdominalGirthType) {
		this.abdominalGirthType = abdominalGirthType;
	}

	public String getStomachAspirationType() {
		return stomachAspirationType;
	}

	public void setStomachAspirationType(String stomachAspirationType) {
		this.stomachAspirationType = stomachAspirationType;
	}

	public String getBpMeasurementType() {
		return bpMeasurementType;
	}

	public void setBpMeasurementType(String bpMeasurementType) {
		this.bpMeasurementType = bpMeasurementType;
	}

	public String getCvpMeasurementType() {
		return cvpMeasurementType;
	}

	public void setCvpMeasurementType(String cvpMeasurementType) {
		this.cvpMeasurementType = cvpMeasurementType;
	}

	public Float getGlucoseStripValue() {
		return glucoseStripValue;
	}

	public void setGlucoseStripValue(Float glucoseStripValue) {
		this.glucoseStripValue = glucoseStripValue;
	}

	public String getGlucoseStripType() {
		return glucoseStripType;
	}

	public void setGlucoseStripType(String glucoseStripType) {
		this.glucoseStripType = glucoseStripType;
	}

	public Timestamp getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Timestamp entrytime) {
		this.entrytime = entrytime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getPulseOximeterValueComments() {
		return pulseOximeterValueComments;
	}

	public void setPulseOximeterValueComments(String pulseOximeterValueComments) {
		this.pulseOximeterValueComments = pulseOximeterValueComments;
	}

	public String getChestPhysiotherapyComments() {
		return chestPhysiotherapyComments;
	}

	public void setChestPhysiotherapyComments(String chestPhysiotherapyComments) {
		this.chestPhysiotherapyComments = chestPhysiotherapyComments;
	}

	@Override
	public String toString() {
		return "ProcedureOther [procedure_other_id=" + procedure_other_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", procedurename=" + procedurename
				+ ", details=" + details + ", loggeduser=" + loggeduser + ", etSuctionValue=" + etSuctionValue
				+ ", oralSuctionValue=" + oralSuctionValue + ", chestPhysiotherapyValue=" + chestPhysiotherapyValue
				+ ", positionChangeValue=" + positionChangeValue + ", pulseOximeterValue=" + pulseOximeterValue
				+ ", abdominalGirthValue=" + abdominalGirthValue + ", stomachAspirationValue=" + stomachAspirationValue
				+ ", bpMeasurementValue=" + bpMeasurementValue + ", cvpMeasurementValue=" + cvpMeasurementValue
				+ ", glucoseStripValue=" + glucoseStripValue + ", etSuctionType=" + etSuctionType + ", oralSuctionType="
				+ oralSuctionType + ", chestPhysiotherapyType=" + chestPhysiotherapyType + ", positionChangeType="
				+ positionChangeType + ", pulseOximeterType=" + pulseOximeterType + ", abdominalGirthType="
				+ abdominalGirthType + ", stomachAspirationType=" + stomachAspirationType + ", bpMeasurementType="
				+ bpMeasurementType + ", cvpMeasurementType=" + cvpMeasurementType + ", glucoseStripType="
				+ glucoseStripType + ", entrytime=" + entrytime + ", pulseOximeterValueComments="
				+ pulseOximeterValueComments + ", chestPhysiotherapyComments=" + chestPhysiotherapyComments + "]";
	}
}
