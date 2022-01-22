package com.inicu.postgres.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.inicu.models.AnalyticsFiltersObj;
import com.inicu.models.AnalyticsUsageHelperPOJO;
import com.inicu.models.AnalyticsUsagePojo;
import com.inicu.models.BaselinePOJO;
import com.inicu.models.BedOccupancyObj;
import com.inicu.models.DistributionCurvesPOJO;
import com.inicu.models.HMAnalyticsPOJO;
import com.inicu.models.NICHDScoreJson;
import com.inicu.models.OccupancyExportCsvPOJO;
import com.inicu.models.PredictLOS;
import com.inicu.models.QIJSON;
import com.inicu.models.SinJSON;
import com.inicu.models.UsageAnalytics;
import com.inicu.models.VitalTracker;
import com.inicu.models.NutritionalCompliancePOJO;
import com.inicu.postgres.entities.BabyDetail;
import org.json.JSONObject;

/**
 * 
 * @author iNICU
 *
 */
public interface AnalyticsService {

	BedOccupancyObj getBedOccupied(String loggedInUser);

	HashMap<Object, Object> getInicuModuleUsageRecords(String recordDate, String loggedInUser, String uhid);

	List<AnalyticsUsagePojo> getAnalyticsUsage(String fromDateStr, String toDateStr, String branchName);

	UsageAnalytics getUsageAnalytics(String loggedInUser, String branchName);

	HashMap<String, LinkedHashMap<Timestamp, Double>> getAnalyticsUsageGraph(String fromDateStr, String toDateStr, String branchName);

	HashMap<String, AnalyticsUsageHelperPOJO> getOccupancyData(String fromDateStr, String toDateStr, String branchName, AnalyticsFiltersObj analyticsFilterObj);
	
	List<OccupancyExportCsvPOJO> getExportOccupancy(String fromDateStr, String toDateStr, String branchName, AnalyticsFiltersObj analyticsFilterObj);
	
	NICHDScoreJson getnchidScore(String uhid);

	String getCribScore(String uhid);

	List<SinJSON> getSinSheetData(String fromDate, String toDate, String branchName);

	List<BabyDetail> getSinUHIDData(String dateStr, String branchName);

	QIJSON getQIData(String fromDate, String toDate, String fromGest, String toGest, String fromWeight,
			String toWeight, String branchName);

	HashMap<String, Object> getSystemicReport(String fromDate, String toDate, String branchName, String reportType);
	
	HashMap<String, Object> getSystemicReportDevice(String fromDate, String toDate, String uhid);
	
	List<VitalTracker> getVitalsData(String fromDateStr, String toDateStr, String branchName);
	
	HMAnalyticsPOJO getHMObservableData(String fromDateStr, String toDateStr, String branchName);
	
	List<NutritionalCompliancePOJO> getNutritionalComplianceData(String branchName);
	
	BaselinePOJO getBaselineData(String fromDate, String toDate, String branchName);
	
	List<PredictLOS> predictLOS(String branchName, String uhid);

	List getCustomReportsData(String branchName, JSONObject jsonObject);

	String saveCustomReportsData(String branchName, JSONObject jsonObject);

	List getAllCustomReports(String branchName);

	DistributionCurvesPOJO getDistributionCurves(String fromDateStr, String toDateStr, String branchName, String uhid);


}
