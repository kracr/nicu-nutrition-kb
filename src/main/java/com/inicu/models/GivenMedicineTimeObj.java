package com.inicu.models;

public class GivenMedicineTimeObj {

	
		
		String givenTime;
		String presTime;
		Boolean isDisable; 
		
		public String getGivenTime() {
			return givenTime;
		}
		public void setGivenTime(String givenTime) {
			this.givenTime = givenTime;
		}
		public String getPresTime() {
			return presTime;
		}
		public void setPresTime(String presTime) {
			this.presTime = presTime;
		}
		public GivenMedicineTimeObj() {
			super();
			this.givenTime = "";
			this.presTime = "";
			this.isDisable = false;
		}
		
		
		
	}

