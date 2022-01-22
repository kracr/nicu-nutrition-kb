package com.inicu.models;

import java.util.List;


public class InicuBoxJson {
	
	private String boxName;
	List<BoardDetailJson> listBoardDetails;
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public List<BoardDetailJson> getListBoardDetails() {
		return listBoardDetails;
	}
	public void setListBoardDetails(List<BoardDetailJson> listBoardDetails) {
		this.listBoardDetails = listBoardDetails;
	}
}
