package com.inicu.models;

import java.sql.Timestamp;

/**
 * 
 * @author iNICU 
 *
 */
public class KeyValueObj {
	
	private Object key;
	private Object value;
	private Long occupiedCount;
	private Long vacantCount;
	private Long allCount;
	private Timestamp dateTime;
	
	
	public KeyValueObj() {
		super();
		this.key = "";
		this.value = "";
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Long getOccupiedCount() {
		return occupiedCount;
	}
	public void setOccupiedCount(Long occupiedCount) {
		this.occupiedCount = occupiedCount;
	}
	public Long getVacantCount() {
		return vacantCount;
	}
	public void setVacantCount(Long vacantCount) {
		this.vacantCount = vacantCount;
	}
	public Long getAllCount() {
		return allCount;
	}
	public void setAllCount(Long allCount) {
		this.allCount = allCount;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
}
