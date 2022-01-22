package com.inicu.models;

public class MappedListPOJO {

	RefTestslist inicuTest;
	RefTestslist vendorTest;
	Boolean isMapped;
	String loggedUser;
	
	public RefTestslist getInicuTest() {
		return inicuTest;
	}
	public void setInicuTest(RefTestslist inicuTest) {
		this.inicuTest = inicuTest;
	}
	public RefTestslist getVendorTest() {
		return vendorTest;
	}
	public void setVendorTest(RefTestslist vendorTest) {
		this.vendorTest = vendorTest;
	}
	public Boolean getIsMapped() {
		return isMapped;
	}
	public void setIsMapped(Boolean isMapped) {
		this.isMapped = isMapped;
	}
	public String getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	
}
