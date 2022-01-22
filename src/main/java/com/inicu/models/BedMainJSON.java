package com.inicu.models;

import java.util.List;

public class BedMainJSON {

	public String roomno;
	public List<BedJSON> bedJSON;
	public void setRoomno(String roomno)
	{
		this.roomno=roomno;
	}
	public String getRoomno()
	{
		return this.roomno;
	}
	public void setbedJSON(List<BedJSON> bedJSON)
	{
		this.bedJSON=bedJSON;
	}
	public List<BedJSON> getBedJSON()
	{
		return this.bedJSON;
	}
	
	
}
