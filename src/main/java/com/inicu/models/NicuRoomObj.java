package com.inicu.models;

import java.util.List;

public class NicuRoomObj {
	
	private KeyValueObj room;
	private List<KeyValueObj> bed;
	
	public KeyValueObj getRoom() {
		return room;
	}
	public void setRoom(KeyValueObj room) {
		this.room = room;
	}
	public List<KeyValueObj> getBed() {
		return bed;
	}
	public void setBed(List<KeyValueObj> bed) {
		this.bed = bed;
	}
	
}
