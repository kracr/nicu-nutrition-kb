package com.inicu.cassandra.entities;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("patient_ventilator_data")
public class PatientVentilatorData {

	@PrimaryKey("device_id")
	private String deviceId;
	
	@Column("device_name")
	private String deviceName;
	
	@Column("data")
	private String data;
	
	@Column("fi02")
	private Double fio2; 
	
	@Column("meanbp")
	private Double meanBp;
	
	@Column("minairwaypress")
	private Double minAirwayPress;
	
	@Column("occpressure")
	private Double occPressure;
	
	@Column("peakbp")
	private Double peakBp;
	
	@Column("peepbp")
	private Double peepBp;
	
	@Column("platpressure")
	private Double platPressure;
	
	@Column("spontrr")
	private Double spontRR;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getFio2() {
		return fio2;
	}

	public void setFio2(Double fio2) {
		this.fio2 = fio2;
	}

	public Double getMeanBp() {
		return meanBp;
	}

	public void setMeanBp(Double meanBp) {
		this.meanBp = meanBp;
	}

	public Double getMinAirwayPress() {
		return minAirwayPress;
	}

	public void setMinAirwayPress(Double minAirwayPress) {
		this.minAirwayPress = minAirwayPress;
	}

	public Double getOccPressure() {
		return occPressure;
	}

	public void setOccPressure(Double occPressure) {
		this.occPressure = occPressure;
	}

	public Double getPeakBp() {
		return peakBp;
	}

	public void setPeakBp(Double peakBp) {
		this.peakBp = peakBp;
	}

	public Double getPeepBp() {
		return peepBp;
	}

	public void setPeepBp(Double peepBp) {
		this.peepBp = peepBp;
	}

	public Double getPlatPressure() {
		return platPressure;
	}

	public void setPlatPressure(Double platPressure) {
		this.platPressure = platPressure;
	}

	public Double getSpontRR() {
		return spontRR;
	}

	public void setSpontRR(Double spontRR) {
		this.spontRR = spontRR;
	}
}
