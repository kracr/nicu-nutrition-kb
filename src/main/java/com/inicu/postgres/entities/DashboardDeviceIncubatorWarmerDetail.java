

package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the dashboard_device_incubator_warmer_detail database
 * table.
 * 
 */

@Entity
@Table(name = "dashboard_device_incubator_warmer_detail")
@NamedQuery(name = "DashboardDeviceIncubatorWarmerDetail.findAll", query = "SELECT d FROM DashboardDeviceIncubatorWarmerDetail d")
public class DashboardDeviceIncubatorWarmerDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uhid;
	
	private String beddeviceid;

	@Column(name = "air_temperature")
	private String airTemp;
	
	@Column(name = "set_air_temperature")
	private String setAirTemp;
	
	private String humidity;

	@Column(name = "set_humidity")
	private String setHumidity;
	
	@Column(name = "baby_weight")
	private String babyWeight;
	
	@Column(name = "heater_power")
	private String heaterPower;
	
	@Column(name = "temp_from_probe1")
	private String skinTemp1;

	@Column(name = "temp_from_probe2")
	private String skinTemp2;
	
	@Column(name = "ambient_temperature")
	private String ambientTemperature;
	
	@Column(name = "incubator_mode")
	private String incubatorMode;
	
	@Column(name = "warmer_mode")
	private String warmerMode;
	
	@Column(name = "warmer_set_temp")
	private String warmerSetTemp;
	
	public String getAmbientTemperature() {
		return ambientTemperature;
	}

	public void setAmbientTemperature(String ambientTemperature) {
		this.ambientTemperature = ambientTemperature;
	}

	public String getIncubatorMode() {
		return incubatorMode;
	}

	public void setIncubatorMode(String incubatorMode) {
		this.incubatorMode = incubatorMode;
	}

	public String getWarmerMode() {
		return warmerMode;
	}

	public void setWarmerMode(String warmerMode) {
		this.warmerMode = warmerMode;
	}

	public String getWarmerSetTemp() {
		return warmerSetTemp;
	}

	public void setWarmerSetTemp(String warmerSetTemp) {
		this.warmerSetTemp = warmerSetTemp;
	}

	@Column(name = "patient_control_temp")
	private String patientControlTemp;

	@Column(name = "patient_mode")
	private String patientMode;

	@Column(name = "open_bed_mode")
	private String openBedMode;

	@Column(name = "closed_bed_mode")
	private String closedBedMode;
	
	@Column(name = "o2_set_point")
	private String O2SetPoint;
	
	@Column(name = "o2_measurment")
	private String o2_measurment;
	
	@Column(name = "spo2_measurment")
	private String spo2Measurment;
	
	@Column(name = "pulse_rate_measurment")
	private String pulseRateMeasurment;
	
	
	private Timestamp starttime;


	public String getUhid() {
		return uhid;
	}


	public void setUhid(String uhid) {
		this.uhid = uhid;
	}


	public String getBeddeviceid() {
		return beddeviceid;
	}


	public void setBeddeviceid(String beddeviceid) {
		this.beddeviceid = beddeviceid;
	}


	public String getAirTemp() {
		return airTemp;
	}


	public void setAirTemp(String airTemp) {
		this.airTemp = airTemp;
	}


	public String getSetAirTemp() {
		return setAirTemp;
	}


	public void setSetAirTemp(String setAirTemp) {
		this.setAirTemp = setAirTemp;
	}


	public String getHumidity() {
		return humidity;
	}


	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}


	public String getSetHumidity() {
		return setHumidity;
	}


	public void setSetHumidity(String setHumidity) {
		this.setHumidity = setHumidity;
	}


	public String getBabyWeight() {
		return babyWeight;
	}


	public void setBabyWeight(String babyWeight) {
		this.babyWeight = babyWeight;
	}


	public String getHeaterPower() {
		return heaterPower;
	}


	public void setHeaterPower(String heaterPower) {
		this.heaterPower = heaterPower;
	}


	public String getSkinTemp1() {
		return skinTemp1;
	}


	public void setSkinTemp1(String skinTemp1) {
		this.skinTemp1 = skinTemp1;
	}


	public String getSkinTemp2() {
		return skinTemp2;
	}


	public void setSkinTemp2(String skinTemp2) {
		this.skinTemp2 = skinTemp2;
	}


	public String getPatientControlTemp() {
		return patientControlTemp;
	}


	public void setPatientControlTemp(String patientControlTemp) {
		this.patientControlTemp = patientControlTemp;
	}


	public String getPatientMode() {
		return patientMode;
	}


	public void setPatientMode(String patientMode) {
		this.patientMode = patientMode;
	}


	public String getOpenBedMode() {
		return openBedMode;
	}


	public void setOpenBedMode(String openBedMode) {
		this.openBedMode = openBedMode;
	}


	public String getClosedBedMode() {
		return closedBedMode;
	}


	public void setClosedBedMode(String closedBedMode) {
		this.closedBedMode = closedBedMode;
	}


	public String getO2SetPoint() {
		return O2SetPoint;
	}


	public void setO2SetPoint(String o2SetPoint) {
		O2SetPoint = o2SetPoint;
	}


	public String getO2_measurment() {
		return o2_measurment;
	}


	public void setO2_measurment(String o2_measurment) {
		this.o2_measurment = o2_measurment;
	}


	public String getSpo2Measurment() {
		return spo2Measurment;
	}


	public void setSpo2Measurment(String spo2Measurment) {
		this.spo2Measurment = spo2Measurment;
	}


	public String getPulseRateMeasurment() {
		return pulseRateMeasurment;
	}


	public void setPulseRateMeasurment(String pulseRateMeasurment) {
		this.pulseRateMeasurment = pulseRateMeasurment;
	}


	public Timestamp getStarttime() {
		return starttime;
	}


	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}


}