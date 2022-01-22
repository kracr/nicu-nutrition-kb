package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.RefEnAddtivesBrand;
import org.hibernate.annotations.NotFound;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public class BabyPrescriptionObject {

	private String userId;

	@NotFound
	private MedicineDropDownsPOJO dropDowns;

	@NotFound
	private BabyPrescription currentPrescription;

	@NotFound
	private List<BabyPrescription> currentPrescriptionList;

	@NotFound
	private List<BabyPrescription> activePrescription;

	@NotFound
	private List<BabyPrescription> pastPrescriptions;
	
	@NotFound
	private BabyfeedDetail babyfeedDetailList;

	@NotFound
	private List<RefEnAddtivesBrand> enAddtivesBrandNameList;

	@NotFound
	private Float weightForCal;
	
	@NotFound
	private List<BabyPrescription> activeMedsList;

	List<KeyValueObj> logoImage;

	@Transient
	List<KeyValueObj> signatureImage;

	@Transient
	List<KeyValueObj> consultantSignatureImage;

	@Transient
	private String loggedUserFullName;

	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public BabyPrescriptionObject() {
		super();
		this.currentPrescriptionList = new ArrayList<BabyPrescription>();
	}

	public MedicineDropDownsPOJO getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(MedicineDropDownsPOJO dropDowns) {
		this.dropDowns = dropDowns;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BabyPrescription getCurrentPrescription() {
		return currentPrescription;
	}

	public void setCurrentPrescription(BabyPrescription currentPrescription) {
		this.currentPrescription = currentPrescription;
	}

	public List<BabyPrescription> getCurrentPrescriptionList() {
		return currentPrescriptionList;
	}

	public void setCurrentPrescriptionList(List<BabyPrescription> currentPrescriptionList) {
		this.currentPrescriptionList = currentPrescriptionList;
	}

	public List<BabyPrescription> getActivePrescription() {
		return activePrescription;
	}

	public void setActivePrescription(List<BabyPrescription> activePrescription) {
		this.activePrescription = activePrescription;
	}

	public List<BabyPrescription> getPastPrescriptions() {
		return pastPrescriptions;
	}

	public void setPastPrescriptions(List<BabyPrescription> pastPrescriptions) {
		this.pastPrescriptions = pastPrescriptions;
	}

	public BabyfeedDetail getBabyfeedDetailList() {
		return babyfeedDetailList;
	}

	public void setBabyfeedDetailList(BabyfeedDetail babyfeedDetailList) {
		this.babyfeedDetailList = babyfeedDetailList;
	}

	public List<RefEnAddtivesBrand> getEnAddtivesBrandNameList() {
		return enAddtivesBrandNameList;
	}

	public void setEnAddtivesBrandNameList(List<RefEnAddtivesBrand> enAddtivesBrandNameList) {
		this.enAddtivesBrandNameList = enAddtivesBrandNameList;
	}

	public Float getWeightForCal() {
		return weightForCal;
	}

	public void setWeightForCal(Float weightForCal) {
		this.weightForCal = weightForCal;
	}

	public List<BabyPrescription> getActiveMedsList() {
		return activeMedsList;
	}

	public void setActiveMedsList(List<BabyPrescription> activeMedsList) {
		this.activeMedsList = activeMedsList;
	}

	public List<KeyValueObj> getSignatureImage() {
		return signatureImage;
	}

	public void setSignatureImage(List<KeyValueObj> signatureImage) {
		this.signatureImage = signatureImage;
	}

	public List<KeyValueObj> getConsultantSignatureImage() {
		return consultantSignatureImage;
	}

	public void setConsultantSignatureImage(List<KeyValueObj> consultantSignatureImage) {
		this.consultantSignatureImage = consultantSignatureImage;
	}

	public String getLoggedUserFullName() {
		return loggedUserFullName;
	}

	public void setLoggedUserFullName(String loggedUserFullName) {
		this.loggedUserFullName = loggedUserFullName;
	}

	@Override
	public String toString() {
		return "BabyPrescriptionObject [userId=" + userId + ", dropDowns=" + dropDowns + ", currentPrescription="
				+ currentPrescription + ", currentPrescriptionList=" + currentPrescriptionList + ", activePrescription="
				+ activePrescription + ", pastPrescriptions=" + pastPrescriptions + ", babyfeedDetailList="
				+ babyfeedDetailList + ", enAddtivesBrandNameList=" + enAddtivesBrandNameList + ", weightForCal=" + weightForCal + "]";
	}

}
