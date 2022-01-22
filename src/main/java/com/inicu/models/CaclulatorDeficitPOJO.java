package com.inicu.models;

import java.util.HashMap;

public class CaclulatorDeficitPOJO {

	HashMap<String, Float> eshphaganIntake;
	
	HashMap<String, Float> eshphaganParenteralIntake;
	
	HashMap<String, Float> enteralIntake;
	
	HashMap<String, Float> parentalIntake ;
	
	HashMap<String, Float> total ;
	
	HashMap<String, Float> totalPerKg ;
	
	HashMap<String, Float> deficit;
	
	Float aminoEnergy;
	
	Float lipidEnergy;
	
	Float girEnergy;
	
	public CaclulatorDeficitPOJO() {
		super();
		
		this.eshphaganIntake = new HashMap<String, Float>(){
			{
				put("Energy",(float) 135);
				put("Protein",(float) 4);
				put("Fat",(float) 8);
				put("Vitamina",(float) 1000);
				put("Vitamind",(float) 1000);
				put("Calcium",(float) 140);
				put("Phosphorus",(float) 90);
				put("Iron",(float) 2);
			}
		};
		
		this.eshphaganParenteralIntake = new HashMap<String, Float>(){
			{
				put("Energy",(float) 120);
				put("Protein",(float) 4);
				put("Fat",(float) 4);
				put("Vitamina",(float) 300);
				put("Vitamind",(float) 0);
				put("Calcium",(float) 120);
				put("Phosphorus",(float) 80);
				put("Iron",(float) 0);
			}
		};
		
		this.enteralIntake = new HashMap<String, Float>(){
			{
				put("Energy",Float.valueOf("0"));
				put("Protein",Float.valueOf("0"));
				put("Fat",Float.valueOf("0"));
				put("Vitamina",Float.valueOf("0"));
				put("Vitamind",Float.valueOf("0"));
				put("Calcium",Float.valueOf("0"));
				put("Phosphorus",Float.valueOf("0"));
				put("Iron",Float.valueOf("0"));
				put("Carbohydrates",Float.valueOf("0"));
			}
		};
		
		this.parentalIntake = new HashMap<String, Float>(){
			{
				put("Energy",Float.valueOf("0"));
				put("Protein",Float.valueOf("0"));
				put("Fat",Float.valueOf("0"));
				put("Vitamina",Float.valueOf("0"));
				put("Vitamind",Float.valueOf("0"));
				put("Calcium",Float.valueOf("0"));
				put("Phosphorus",Float.valueOf("0"));
				put("Iron",Float.valueOf("0"));
			}
		};
		
		
	}

	public HashMap<String, Float> getEshphaganIntake() {
		return eshphaganIntake;
	}

	public HashMap<String, Float> getEnteralIntake() {
		return enteralIntake;
	}

	public HashMap<String, Float> getParentalIntake() {
		return parentalIntake;
	}

	public void setEshphaganIntake(HashMap<String, Float> eshphaganIntake) {
		this.eshphaganIntake = eshphaganIntake;
	}

	public void setEnteralIntake(HashMap<String, Float> enteralIntake) {
		this.enteralIntake = enteralIntake;
	}

	public void setParentalIntake(HashMap<String, Float> parentalIntake) {
		this.parentalIntake = parentalIntake;
	}

	public HashMap<String, Float> getEshphaganParenteralIntake() {
		return eshphaganParenteralIntake;
	}

	public HashMap<String, Float> getTotal() {
		return total;
	}

	public HashMap<String, Float> getTotalPerKg() {
		return totalPerKg;
	}

	public HashMap<String, Float> getDeficit() {
		return deficit;
	}

	public void setEshphaganParenteralIntake(
			HashMap<String, Float> eshphaganParenteralIntake) {
		this.eshphaganParenteralIntake = eshphaganParenteralIntake;
	}

	public void setTotal(HashMap<String, Float> total) {
		this.total = total;
	}

	public void setTotalPerKg(HashMap<String, Float> totalPerKg) {
		this.totalPerKg = totalPerKg;
	}

	public void setDeficit(HashMap<String, Float> deficit) {
		this.deficit = deficit;
	}

	public Float getAminoEnergy() {
		return aminoEnergy;
	}

	public void setAminoEnergy(Float aminoEnergy) {
		this.aminoEnergy = aminoEnergy;
	}

	public Float getLipidEnergy() {
		return lipidEnergy;
	}

	public void setLipidEnergy(Float lipidEnergy) {
		this.lipidEnergy = lipidEnergy;
	}

	public Float getGirEnergy() {
		return girEnergy;
	}

	public void setGirEnergy(Float girEnergy) {
		this.girEnergy = girEnergy;
	}
	
}
