package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the nursing_outputdrain database table.
 * 
 */
@Entity
@Table(name="nursing_outputdrain")
@NamedQuery(name="NursingOutputdrain.findAll", query="SELECT n FROM NursingOutputdrain n")
public class NursingOutputdrain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nn_drainid")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nnDrainid;

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

/*	@Column(name="hourly_output")
	private String hourlyOutput;
*/
	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name="nn_drain_time")
	private String nnDrainTime;

/*	@Column(name="total_output")
	private String totalOutput;
*/
	private String uhid;

	public NursingOutputdrain() {
	}

	public Long getNnDrainid() {
		return this.nnDrainid;
	}

	public void setNnDrainid(Long nnDrainid) {
		this.nnDrainid = nnDrainid;
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

	/*public String getHourlyOutput() {
		return this.hourlyOutput;
	}

	public void setHourlyOutput(String hourlyOutput) {
		this.hourlyOutput = hourlyOutput;
	}*/

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnDrainTime() {
		return this.nnDrainTime;
	}

	public void setNnDrainTime(String nnDrainTime) {
		this.nnDrainTime = nnDrainTime;
	}

/*	public String getTotalOutput() {
		return this.totalOutput;
	}

	public void setTotalOutput(String totalOutput) {
		this.totalOutput = totalOutput;
	}
*/
	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}