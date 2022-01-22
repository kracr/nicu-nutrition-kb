package com.inicu.models;

import com.inicu.postgres.entities.NursingDailyassesment;

public class NursingDailyAssesmentJSON {
	private NursingDailyassesment morningData;
	private NursingDailyassesment afterNoonData;
	private NursingDailyassesment eveningData;
	private NursingDailyassesment nightData;
	 
	public NursingDailyAssesmentJSON()
	{
		 morningData = new NursingDailyassesment();
		 afterNoonData = new NursingDailyassesment();
		 eveningData = new NursingDailyassesment();
		 nightData = new NursingDailyassesment();
	}
	
	public void setMorningData(NursingDailyassesment morningData)
	{
		this.morningData = morningData;
	}
	public NursingDailyassesment getMorningData()
	{
		return this.morningData;
	}
	public void setAfternoonData(NursingDailyassesment afterNoonData)
	{
		this.afterNoonData = afterNoonData;
	}
	public NursingDailyassesment getAfternoonData()
	{
		return this.afterNoonData;
	}
	public void setEveningData(NursingDailyassesment eveningData)
	{
		this.eveningData = eveningData;
	}
	public NursingDailyassesment getEveningData()
	{
		return this.eveningData;
	}

	public NursingDailyassesment getNightData() {
		return nightData;
	}

	public void setNightData(NursingDailyassesment nightData) {
		this.nightData = nightData;
	}
	

}
