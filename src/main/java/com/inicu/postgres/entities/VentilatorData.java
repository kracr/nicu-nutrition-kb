package com.inicu.postgres.entities;

import java.util.Date;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * inicu.device_data
 * @author OXYENT
 *
 */
@Table("device_data")
public class VentilatorData {

	@PrimaryKey("device_time")
	private Date devTime;
	
	@Column("data")
	private String data;

	public Date getDevTime() {
		return devTime;
	}

	public void setDevTime(Date devTime) {
		this.devTime = devTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
