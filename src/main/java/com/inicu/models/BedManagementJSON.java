package com.inicu.models;

import java.util.List;

public class BedManagementJSON {
	
	public List<NicuBedJSON> nicuRoomObj;
	public List<KeyValueObj> roomList;
	public String newRoom;
	public String newBed;
	public void setNewBed(String newBed)
	{
		this.newBed=newBed;
	}
	public String getNewBed()
	{
		return this.newBed;
	}
	public void setNewRoom(String newRoom)
	{
		this.newRoom=newRoom;
	}
	public String getNewRoom()
	{
	return this.newRoom;
	}
	public void setRoomList(List<KeyValueObj> roomList)
	{
		this.roomList=roomList;
	}
	public List<KeyValueObj> getRoomList()
	{
		return this.roomList;
	}
	public void setNicuRoomObj(List<NicuBedJSON> nicuRoomObj)
	{
		this.nicuRoomObj=nicuRoomObj;
	}
   public List<NicuBedJSON> getNicuRoomObj()
   {
	   return this.nicuRoomObj;
   }
}
