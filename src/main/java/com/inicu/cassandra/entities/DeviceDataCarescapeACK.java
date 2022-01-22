package com.inicu.cassandra.entities;

//import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;


@Table("patient_devicedata_carescapeack")
public class DeviceDataCarescapeACK {

	@PrimaryKey("patientId")
	private String patientId;
	
	@Column("bedId")
	private String bedId;
		
	@Column("date")
	private Date date;
	
	

	@Column("data")
	private String data;

	@Column("timestamp")
	private String timestamp;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getBedId() {
		return bedId;
	}

	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	



}
