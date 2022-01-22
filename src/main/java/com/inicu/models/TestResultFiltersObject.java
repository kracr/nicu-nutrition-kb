package com.inicu.models;

public class TestResultFiltersObject {

	private String searchedUHID;
	private String searchedStartDate;
	private String searchedEndDate;
	private String selectedTestID;
	private String fromWhere;

	public String getSearchedUHID() {
		return searchedUHID;
	}
	public void setSearchedUHID(String searchedUHID) {
		this.searchedUHID = searchedUHID;
	}
	public String getSearchedStartDate() {
		return searchedStartDate;
	}
	public void setSearchedStartDate(String searchedStartDate) {
		this.searchedStartDate = searchedStartDate;
	}
	public String getSearchedEndDate() {
		return searchedEndDate;
	}
	public void setSearchedEndDate(String searchedEndDate) {
		this.searchedEndDate = searchedEndDate;
	}
	public String getSelectedTestID() {
		return selectedTestID;
	}
	public void setSelectedTestID(String selectedTestID) {
		this.selectedTestID = selectedTestID;
	}

	public String getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}
}
