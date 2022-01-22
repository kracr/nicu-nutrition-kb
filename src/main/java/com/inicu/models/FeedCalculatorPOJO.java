package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.RefNutritioncalculator;

public class FeedCalculatorPOJO {

	private CaclulatorDeficitPOJO currentDeficitCal;
	
	private CaclulatorDeficitPOJO lastDeficitCal;
	
	private CaclulatorDeficitPOJO deficitCalToShow;
	
	private List<RefNutritioncalculator> refNutritionInfo;
	
	
	
	public FeedCalculatorPOJO(){
		this.deficitCalToShow = new CaclulatorDeficitPOJO();
	}

	public CaclulatorDeficitPOJO getCurrentDeficitCal() {
		return currentDeficitCal;
	}

	public CaclulatorDeficitPOJO getLastDeficitCal() {
		return lastDeficitCal;
	}

	public void setCurrentDeficitCal(CaclulatorDeficitPOJO currentDeficitCal) {
		this.currentDeficitCal = currentDeficitCal;
	}

	public void setLastDeficitCal(CaclulatorDeficitPOJO lastDeficitCal) {
		this.lastDeficitCal = lastDeficitCal;
	}

	public CaclulatorDeficitPOJO getDeficitCalToShow() {
		return deficitCalToShow;
	}

	public void setDeficitCalToShow(CaclulatorDeficitPOJO deficitCalToShow) {
		this.deficitCalToShow = deficitCalToShow;
	}

	public List<RefNutritioncalculator> getRefNutritionInfo() {
		return refNutritionInfo;
	}

	public void setRefNutritionInfo(List<RefNutritioncalculator> refNutritionInfo) {
		this.refNutritionInfo = refNutritionInfo;
	}

	
	
	
	
	
}
