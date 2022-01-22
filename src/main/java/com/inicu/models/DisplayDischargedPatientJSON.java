package com.inicu.models;

import java.util.List;

public class DisplayDischargedPatientJSON {
		private String bedNumber;
		private String uhid;
		private boolean bedStatus;
		private String dischargestatus;
		private java.sql.Timestamp dischargeDate;
		private boolean isreadmitted;
		private boolean admissionstatus;
		private String message;
		List<String> availableBeds;
		List<NicuRoomObj> roomNumbers;
		
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getBedNumber() {
			return bedNumber;
		}
		public void setBedNumber(String bedNumber) {
			this.bedNumber = bedNumber;
		}
		public String getUhid() {
			return uhid;
		}
		public void setUhid(String uhid) {
			this.uhid = uhid;
		}
		public boolean isBedStatus() {
			return bedStatus;
		}
		public void setBedStatus(boolean bedStatus) {
			this.bedStatus = bedStatus;
		}
		public String getDischargestatus() {
			return dischargestatus;
		}
		public void setDischargestatus(String dischargestatus) {
			this.dischargestatus = dischargestatus;
		}
		public java.sql.Timestamp getDischargeDate() {
			return dischargeDate;
		}
		public void setDischargeDate(java.sql.Timestamp dischargeDate) {
			this.dischargeDate = dischargeDate;
		}
		public boolean isIsreadmitted() {
			return isreadmitted;
		}
		public void setIsreadmitted(boolean isreadmitted) {
			this.isreadmitted = isreadmitted;
		}
		public boolean isAdmissionstatus() {
			return admissionstatus;
		}
		public void setAdmissionstatus(boolean admissionstatus) {
			this.admissionstatus = admissionstatus;
		}
		public List<String> getAvailableBeds() {
			return availableBeds;
		}
		public void setAvailableBeds(List<String> availableBeds) {
			this.availableBeds = availableBeds;
		}
		public List<NicuRoomObj> getRoomNumbers() {
			return roomNumbers;
		}
		public void setRoomNumbers(List<NicuRoomObj> roomNumbers) {
			this.roomNumbers = roomNumbers;
		}
		
		
		
		
}
