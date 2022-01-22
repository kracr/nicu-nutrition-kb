package com.inicu.postgres.service;

import com.inicu.models.ChartMasterJson;

public interface GrowthChatService {

	public ChartMasterJson getGraphData(String uhid);

	public double getFentonCentile(String gender, String gestWeek, String paramType, double paramValue);

	public String updateCentileDetails();

}
