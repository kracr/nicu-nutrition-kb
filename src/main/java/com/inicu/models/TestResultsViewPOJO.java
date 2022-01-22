package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.TestItemResult;

public class TestResultsViewPOJO {

	List<String> dates;
	List<TestNamePOJO> itemList = new ArrayList<>();
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public List<TestNamePOJO> getItemList() {
		return itemList;
	}
	public void setItemList(List<TestNamePOJO> itemList) {
		this.itemList = itemList;
	}

}
