package com.inicu.models;

import org.json.JSONException;
import org.json.JSONObject;

public class BoxJSON {
	String box_name;
	String box_serial;
	String id;
	BoardDetailJson board1;
	BoardDetailJson board2;
	boolean isTinyNeo;

	public BoxJSON(String boxdetail) {
		// TODO Auto-generated constructor stub
		try {
			JSONObject json=new JSONObject(boxdetail);
			this.box_name=json.getString("box_name");
			this.box_serial=json.getString("box_serial");
			this.id=json.getString("id");
			this.isTinyNeo = json.getBoolean("isTinyNeo");
			BoardDetailJson bdj1 =new BoardDetailJson(json.getString("board1"));
			this.board1=bdj1;
			BoardDetailJson bdj2 =new BoardDetailJson(json.getString("board2"));
			this.board2=bdj2;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public BoxJSON() {
		// TODO Auto-generated constructor stub
	}

	public BoardDetailJson getBoard2() {
		return board2;
	}
	public void setBoard2(BoardDetailJson board2) {
		this.board2 = board2;
	}
	public BoardDetailJson getBoard1() {
		return board1;
	}
	public void setBoard1(BoardDetailJson board1) {
		this.board1 = board1;
	}
	
	public String getBox_name() {
		return box_name;
	}
	public void setBox_name(String box_name) {
		this.box_name = box_name;
	}
	public String getBox_serial() {
		return box_serial;
	}
	public void setBox_serial(String box_serial) {
		this.box_serial = box_serial;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public boolean isTinyNeo() {
		return isTinyNeo;
	}

	public void setTinyNeo(boolean tinyNeo) {
		isTinyNeo = tinyNeo;
	}
}
