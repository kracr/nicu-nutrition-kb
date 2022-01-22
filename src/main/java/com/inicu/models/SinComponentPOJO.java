package com.inicu.models;
import java.util.List;


public class SinComponentPOJO {
	Integer count;
	List<SinData> dataList;
	
	public SinComponentPOJO() {
		super();
		count = 0;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<SinData> getDataList() {
		return dataList;
	}
	public void setDataList(List<SinData> dataList) {
		this.dataList = dataList;
	}
	
	
}
