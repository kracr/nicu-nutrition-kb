package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;


/**
 * The persistent class for the vw_nursingnotes_output_final database table.
 * 
 */
@Entity
@Table(name="vw_nursingnotes_output_final")
@NamedQuery(name="VwNursingnotesOutputFinal.findAll", query="SELECT v FROM VwNursingnotesOutputFinal v")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VwNursingnotesOutputFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="aspirate_quantity")
	private String aspirateQuantity;

	@Column(name="aspirate_type")
	private String aspirateType;

	@Column(name="blood_letting")
	private String bloodLetting;

	@Column(name="bowel_color")
	private String bowelColor;

	@Column(name="bowel_status",columnDefinition="bool")
	private Boolean bowelStatus;

	@Column(name="bowel_type")
	private String bowelType;

	private Timestamp creationtime;

	@Column(name="drain1_input")
	private String drain1Input;

	@Column(name="drain1_output")
	private String drain1Output;

	@Column(name="drain2_input")
	private String drain2Input;

	@Column(name="drain2_output")
	private String drain2Output;

	@Column(name="drain3_input")
	private String drain3Input;

	@Column(name="drain3_output")
	private String drain3Output;

	@Column(name="hourly_drain_output")
	private Integer hourlyDrainOutput;

	@Column(name="hourly_output")
	private Integer hourlyOutput;

	@Column(name="nn_drain_time")
	private String nnDrainTime;

	@Column(name="nn_output_time")
	private String nnOutputTime;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long outputid;

	@Column(name="total_drain_output")
	private Integer totalDrainOutput;

	@Column(name="total_output")
	private Integer totalOutput;

	@Column(name="total_uo")
	private String totalUo;

	private String uhid;

	@Column(name="urine_mlkg")
	private String urineMlkg;

	@Column(name="urine_mls")
	private String urineMls;

	public VwNursingnotesOutputFinal() {
		this.bowelStatus = false;
		this.aspirateQuantity = "";
		this.aspirateType ="";
		this.bowelColor = "";
		this.bowelType = "";
	}

	public String getAspirateQuantity() {
		return this.aspirateQuantity;
	}

	public void setAspirateQuantity(String aspirateQuantity) {
		this.aspirateQuantity = aspirateQuantity;
	}

	public String getAspirateType() {
		return this.aspirateType;
	}

	public void setAspirateType(String aspirateType) {
		this.aspirateType = aspirateType;
	}

	public String getBloodLetting() {
		return this.bloodLetting;
	}

	public void setBloodLetting(String bloodLetting) {
		this.bloodLetting = bloodLetting;
	}

	public String getBowelColor() {
		return this.bowelColor;
	}

	public void setBowelColor(String bowelColor) {
		this.bowelColor = bowelColor;
	}

	public Boolean getBowelStatus() {
		return this.bowelStatus;
	}

	public void setBowelStatus(Boolean bowelStatus) {
		this.bowelStatus = bowelStatus;
	}

	public String getBowelType() {
		return this.bowelType;
	}

	public void setBowelType(String bowelType) {
		this.bowelType = bowelType;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDrain1Input() {
		return this.drain1Input;
	}

	public void setDrain1Input(String drain1Input) {
		this.drain1Input = drain1Input;
	}

	public String getDrain1Output() {
		return this.drain1Output;
	}

	public void setDrain1Output(String drain1Output) {
		this.drain1Output = drain1Output;
	}

	public String getDrain2Input() {
		return this.drain2Input;
	}

	public void setDrain2Input(String drain2Input) {
		this.drain2Input = drain2Input;
	}

	public String getDrain2Output() {
		return this.drain2Output;
	}

	public void setDrain2Output(String drain2Output) {
		this.drain2Output = drain2Output;
	}

	public String getDrain3Input() {
		return this.drain3Input;
	}

	public void setDrain3Input(String drain3Input) {
		this.drain3Input = drain3Input;
	}

	public String getDrain3Output() {
		return this.drain3Output;
	}

	public void setDrain3Output(String drain3Output) {
		this.drain3Output = drain3Output;
	}

	public Integer getHourlyDrainOutput() {
		return this.hourlyDrainOutput;
	}

	public void setHourlyDrainOutput(Integer hourlyDrainOutput) {
		this.hourlyDrainOutput = hourlyDrainOutput;
	}

	public Integer getHourlyOutput() {
		return this.hourlyOutput;
	}

	public void setHourlyOutput(Integer hourlyOutput) {
		this.hourlyOutput = hourlyOutput;
	}

	public String getNnDrainTime() {
		return this.nnDrainTime;
	}

	public void setNnDrainTime(String nnDrainTime) {
		this.nnDrainTime = nnDrainTime;
	}

	public String getNnOutputTime() {
		return this.nnOutputTime;
	}

	public void setNnOutputTime(String nnOutputTime) {
		this.nnOutputTime = nnOutputTime;
	}

	public Long getOutputid() {
		return this.outputid;
	}

	public void setOutputid(Long outputid) {
		this.outputid = outputid;
	}

	public Integer getTotalDrainOutput() {
		return this.totalDrainOutput;
	}

	public void setTotalDrainOutput(Integer totalDrainOutput) {
		this.totalDrainOutput = totalDrainOutput;
	}

	public Integer getTotalOutput() {
		return this.totalOutput;
	}

	public void setTotalOutput(Integer totalOutput) {
		this.totalOutput = totalOutput;
	}

	public String getTotalUo() {
		return this.totalUo;
	}

	public void setTotalUo(String totalUo) {
		this.totalUo = totalUo;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getUrineMlkg() {
		return this.urineMlkg;
	}

	public void setUrineMlkg(String urineMlkg) {
		this.urineMlkg = urineMlkg;
	}

	public String getUrineMls() {
		return this.urineMls;
	}

	public void setUrineMls(String urineMls) {
		this.urineMls = urineMls;
	}

}