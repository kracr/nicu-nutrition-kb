package com.inicu.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.RefEnAddtivesBrand;
import com.inicu.postgres.utility.BasicConstants;

public class NursingNotesDropDownsObj {
		List<KeyValueObj> feedMethod;
		List<KeyValueObj> feedType;
		List<KeyValueObj> feedBo;//brith_color;
		List<KeyValueObj> pupilReact;
		private List<String> hours;
		private List<String> minutes;
		private List<String> seconds;
		List<KeyValueObj> catheterLine;
		List<KeyValueObj> ventilatorMode;
		List<KeyValueObj> frequencyMed;
		List<KeyValueObj> libBreastFeeding;
		List<KeyValueObj> fluidType;
	    List<KeyValueObj> causeOfNpo;
	    List<KeyValueObj> ventiAllModes;
	    List<RefEnAddtivesBrand> additivesbrandlist;

		
		List<KeyValueObj> bolusMethod;
		List<KeyValueObj> bolusType;
		private  HashMap<String, List<String>> medicineFrequency;
		
		
		public NursingNotesDropDownsObj() {
			super();
			this.bolusMethod = new ArrayList<KeyValueObj>(){
				{
					KeyValueObj bolusMethod = new KeyValueObj();
					bolusMethod.setKey("oral");
					bolusMethod.setValue("Oral Feed");
					add(bolusMethod);
					bolusMethod = new KeyValueObj();
					bolusMethod.setKey("ivFluid");
					bolusMethod.setValue("IV Fluid");
					add(bolusMethod);
					
				}
			};
			
			this.libBreastFeeding = new ArrayList<KeyValueObj>(){
				{
					KeyValueObj breastFeeding = new KeyValueObj();
					breastFeeding.setKey("DBF");
					breastFeeding.setValue("Direct Breast Feeding");
					add(breastFeeding);
					breastFeeding = new KeyValueObj();
					breastFeeding.setKey("Paladai");
					breastFeeding.setValue("Paladai");
					add(breastFeeding);
					breastFeeding = new KeyValueObj();
					breastFeeding.setKey("Spoon");
					breastFeeding.setValue("Spoon");
					add(breastFeeding);
					breastFeeding = new KeyValueObj();
					breastFeeding.setKey(BasicConstants.OTHERS);
					breastFeeding.setValue(BasicConstants.OTHERS);
					add(breastFeeding);
					
				}
			};
			
		}

	public List<KeyValueObj> getCauseOfNpo() {
		return causeOfNpo;
	}

	public void setCauseOfNpo(List<KeyValueObj> causeOfNpo) {
		this.causeOfNpo = causeOfNpo;
	}

	public List<KeyValueObj> getFeedMethod() {
			return feedMethod;
		}
		public void setFeedMethod(List<KeyValueObj> feedMethod) {
			this.feedMethod = feedMethod;
		}
		public List<KeyValueObj> getFeedType() {
			return feedType;
		}
		public void setFeedType(List<KeyValueObj> feedType) {
			this.feedType = feedType;
		}
		public List<KeyValueObj> getFeedBo() {
			return feedBo;
		}
		public void setFeedBo(List<KeyValueObj> feedBo) {
			this.feedBo = feedBo;
		}
		public List<KeyValueObj> getPupilReact() {
			return pupilReact;
		}
		public void setPupilReact(List<KeyValueObj> pupilReact) {
			this.pupilReact = pupilReact;
		}
		public List<String> getHours() {
			return hours;
		}
		public void setHours(List<String> hours) {
			this.hours = hours;
		}
		public List<String> getMinutes() {
			return minutes;
		}
		public void setMinutes(List<String> minutes) {
			this.minutes = minutes;
		}
		public List<String> getSeconds() {
			return seconds;
		}
		public void setSeconds(List<String> seconds) {
			this.seconds = seconds;
		}
		public List<KeyValueObj> getCatheterLine() {
			return catheterLine;
		}
		public void setCatheterLine(List<KeyValueObj> catheterLine) {
			this.catheterLine = catheterLine;
		}
		
		public List<KeyValueObj> getVentilatorMode() {
			return ventilatorMode;
		}
		public void setVentilatorMode(List<KeyValueObj> ventilatorMode) {
			this.ventilatorMode = ventilatorMode;
		}
		public List<KeyValueObj> getFrequencyMed() {
			return frequencyMed;
		}
		public void setFrequencyMed(List<KeyValueObj> frequencyMed) {
			this.frequencyMed = frequencyMed;
		}
		public List<KeyValueObj> getBolusMethod() {
			return bolusMethod;
		}
		public void setBolusMethod(List<KeyValueObj> bolusMethod) {
			this.bolusMethod = bolusMethod;
		}
		public HashMap<String, List<String>> getMedicineFrequency() {
			return medicineFrequency;
		}
		public void setMedicineFrequency(HashMap<String, List<String>> medicineFrequency) {
			this.medicineFrequency = medicineFrequency;
		}
		public List<KeyValueObj> getLibBreastFeeding() {
			return libBreastFeeding;
		}
		public List<KeyValueObj> getBolusType() {
			return bolusType;
		}
		public void setLibBreastFeeding(List<KeyValueObj> libBreastFeeding) {
			this.libBreastFeeding = libBreastFeeding;
		}
		public void setBolusType(List<KeyValueObj> bolusType) {
			this.bolusType = bolusType;
		}
		public List<KeyValueObj> getFluidType() {
			return fluidType;
		}
		public void setFluidType(List<KeyValueObj> fluidType) {
			this.fluidType = fluidType;
		}

		public List<KeyValueObj> getVentiAllModes() {
			return ventiAllModes;
		}

		public void setVentiAllModes(List<KeyValueObj> ventiAllModes) {
			this.ventiAllModes = ventiAllModes;
		}

		public List<RefEnAddtivesBrand> getAdditivesbrandlist() {
			return additivesbrandlist;
		}

		public void setAdditivesbrandlist(List<RefEnAddtivesBrand> additivesbrandlist) {
			this.additivesbrandlist = additivesbrandlist;
		}
		
}
