package com.inicu.cassandra.models;

public class MonitorJSON {
	String value;
	String time;
	public String getValue()
	{
		return this.value;
	}
	public void setValue(String value)
	{
		this.value=value;
	}
	
	public String getTime()
	{
		return this.time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	
}
