package com.inicu.models;

import com.inicu.postgres.entities.ScreenNeurological;

import javax.persistence.Transient;
import java.util.List;

public class NeurologicalMasterPojo {

	ScreenNeurological currentObj;

	List<ScreenNeurological> pastNeurologicalList;

	@Transient
	List<KeyValueObj> logoImage;

	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public NeurologicalMasterPojo() {
		super();
		this.currentObj = new ScreenNeurological();
	}

	public ScreenNeurological getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(ScreenNeurological currentObj) {
		this.currentObj = currentObj;
	}

	public List<ScreenNeurological> getPastNeurologicalList() {
		return pastNeurologicalList;
	}

	public void setPastNeurologicalList(List<ScreenNeurological> pastNeurologicalList) {
		this.pastNeurologicalList = pastNeurologicalList;
	}

	@Override
	public String toString() {
		return "NeurologicalMasterPojo [currentObj=" + currentObj + ", pastNeurologicalList=" + pastNeurologicalList
				+ "]";
	}

}
