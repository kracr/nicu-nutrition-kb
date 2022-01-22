package com.inicu.models;

import java.util.List;

public class KeyValueItem {
	
	private Object key;
	private Object value;
	private List<KeyValueItem> item;
	
	
	
	
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
	public List<KeyValueItem> getItem() {
		return item;
	}
	public void setItem(List<KeyValueItem> item) {
		this.item = item;
	}
	
	
	
	
	
	

}
