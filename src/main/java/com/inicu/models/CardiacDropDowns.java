package com.inicu.models;

import java.util.List;

public class CardiacDropDowns {

	List<KeyValueObj> chdlist;
	List<KeyValueObj> inotropeslist;
	public List<KeyValueObj> getChdlist() {
		return chdlist;
	}
	public void setChdlist(List<KeyValueObj> chdlist) {
		this.chdlist = chdlist;
	}
	public List<KeyValueObj> getInotropeslist() {
		return inotropeslist;
	}
	public void setInotropeslist(List<KeyValueObj> inotropeslist) {
		this.inotropeslist = inotropeslist;
	}
}
