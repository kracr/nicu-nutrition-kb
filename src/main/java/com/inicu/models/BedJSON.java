package com.inicu.models;

public class BedJSON {
	private String status;
	public  String bedId;
	private String bedName;
	public String roomId;
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setbedName(String bedName)
	{
		this.bedName=bedName;
	}
	public String getBedName()
	{
		return this.bedName;
	}
	public void setbedId(String bedId)
	{
		this.bedId=bedId;
	}
	public String getbedId()
	{
		return this.bedId;
	}
	public void setRoomId(String roomId)
	{
		this.roomId=roomId;
	}
	public String getRoomId()
	{
		return this.roomId;
	}
}
