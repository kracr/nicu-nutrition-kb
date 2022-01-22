package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.DoctorBloodProducts;

public class DoctorBloodProductPojo {

	DoctorBloodProducts currentObj;
	List<DoctorBloodProducts> pastList;
	List<KeyValueObj> userList;
	List<BabyPrescription> prescriptionList;

	// get the last baby feed given
	private BabyfeedDetail lastBabyFeedObject;
	public DoctorBloodProductPojo() {
		super();
		this.currentObj = new DoctorBloodProducts();
		this.prescriptionList = new ArrayList<BabyPrescription>();
	}
	
	
	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}


	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}


	public DoctorBloodProducts getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(DoctorBloodProducts currentObj) {
		this.currentObj = currentObj;
	}

	public List<DoctorBloodProducts> getPastList() {
		return pastList;
	}

	public void setPastList(List<DoctorBloodProducts> pastList) {
		this.pastList = pastList;
	}

	public List<KeyValueObj> getUserList() {
		return userList;
	}

	public void setUserList(List<KeyValueObj> userList) {
		this.userList = userList;
	}

	public BabyfeedDetail getLastBabyFeedObject() {
		return lastBabyFeedObject;
	}

	public void setLastBabyFeedObject(BabyfeedDetail lastBabyFeedObject) {
		this.lastBabyFeedObject = lastBabyFeedObject;
	}

	@Override
	public String toString() {
		return "DoctorBloodProductPojo{" +
				"currentObj=" + currentObj +
				", pastList=" + pastList +
				", userList=" + userList +
				", prescriptionList=" + prescriptionList +
				", lastBabyFeedObject=" + lastBabyFeedObject +
				'}';
	}
}
