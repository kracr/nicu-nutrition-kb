package com.inicu.postgres.entities;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * PatientDeviceData entity class
 * @author Dipin
 * @version 1.0
 */

@Table("patientdata")
public class PatientData {
	
	@PrimaryKey("patient_id")
	private String patientId;
	
	@Column("event_time")
	private Date eventTime;
	
	@Column("patient_name")
	private String patientName;

	@Column("metric_id")
	private String metricId;
	
	@Column("vendor_metric_id")
	private String vendorMetricId;
	
	@Column("instance_id")
	private String instanceId;
	
	@Column("unit_id")
	private String unitId;
	
	@Column("device_time_sec")
	private String deviceTimeSec;
	
	@Column("device_time_nano_sec")
	private String deviceTimeNano;
	
	@Column("presentation_time_sec")
	private String presTimeSec;
	
	@Column("presentation_time_nanosec")
	private String presTimeNano;
	
	
	@Column("value")
	private String value;
	
	private String domainId; 
	
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	private PatientData pd = null;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getMetricId() {
		return metricId;
	}
	public void setMetricId(String metricId) {
		this.metricId = metricId;
	}
	public String getVendorMetricId() {
		return vendorMetricId;
	}
	public void setVendorMetricId(String vendorMetricId) {
		this.vendorMetricId = vendorMetricId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getDeviceTimeSec() {
		return deviceTimeSec;
	}
	public void setDeviceTimeSec(String deviceTimeSec) {
		this.deviceTimeSec = deviceTimeSec;
	}
	public String getDeviceTimeNano() {
		return deviceTimeNano;
	}
	public void setDeviceTimeNano(String deviceTimeNano) {
		this.deviceTimeNano = deviceTimeNano;
	}
	public String getPresTimeSec() {
		return presTimeSec;
	}
	public void setPresTimeSec(String presTimeSec) {
		this.presTimeSec = presTimeSec;
	}
	public String getPresTimeNano() {
		return presTimeNano;
	}
	public void setPresTimeNano(String presTimeNano) {
		this.presTimeNano = presTimeNano;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

}
