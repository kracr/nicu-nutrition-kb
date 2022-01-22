package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the device_bloodgas_detail_detail database table.
 * 
 */
@Entity
@Table(name="device_bloodgas_detail_detail")
@NamedQuery(name="DeviceBloodgasDetailDetail.findAll", query="SELECT d FROM DeviceBloodgasDetailDetail d")
public class DeviceBloodgasDetailDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_bloodgas_detail_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long deviceBloodgasDetailId;

	private Timestamp creationtime;

	@Column(name="hl7_message")
	private String hl7Message;

	private Timestamp modificationtime;

	@Column(name="sample_id")
	private String sampleId;

	private String uhid;

	@Column(name="is_processed", columnDefinition = "bool")
	private boolean isProcessed;

	public DeviceBloodgasDetailDetail() {
	}

	public Long getDeviceBloodgasDetailId() {
		return this.deviceBloodgasDetailId;
	}

	public void setDeviceBloodgasDetailId(Long deviceBloodgasDetailId) {
		this.deviceBloodgasDetailId = deviceBloodgasDetailId;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getHl7Message() {
		return this.hl7Message;
	}

	public void setHl7Message(String hl7Message) {
		this.hl7Message = hl7Message;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getSampleId() {
		return this.sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean processed) {
		isProcessed = processed;
	}
}