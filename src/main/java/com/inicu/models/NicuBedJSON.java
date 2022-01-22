package com.inicu.models;

import java.util.List;

public class NicuBedJSON {
	
	public KeyValueObj room;
	public List<BedJSON> bedJSON;
	public String newBed;
	
	public void setbedJSON(List<BedJSON> bedJSON)
	{
		this.bedJSON=bedJSON;
	}
	
	public List<BedJSON> getBedJSON()
	{
		return this.bedJSON;
	}
	
	public void setRoom(KeyValueObj room)
	{
		this.room=room;
	}
	
	public KeyValueObj getRoom()
	{
		return this.room;
	}
	public void setNewBed(String newBed)
	{
		this.newBed=newBed;
	}
	
	public String getNewBed()
	{
		return this.newBed;
	}
	
}
