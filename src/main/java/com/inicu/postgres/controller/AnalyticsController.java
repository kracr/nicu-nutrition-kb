package com.inicu.postgres.controller;

import java.sql.Timestamp;
import java.util.*;

import com.fasterxml.jackson.databind.node.TextNode;
import com.inicu.postgres.entities.CustomReports;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.models.AnalyticsFiltersObj;
import com.inicu.models.AnalyticsUsageHelperPOJO;
import com.inicu.models.AnalyticsUsagePojo;
import com.inicu.models.BaselinePOJO;
import com.inicu.models.BedOccupancyObj;
import com.inicu.models.DischargeSummaryAdvancedMasterObj;
import com.inicu.models.DistributionCurvesPOJO;
import com.inicu.models.HMAnalyticsPOJO;
import com.inicu.models.NutritionalCompliancePOJO;
import com.inicu.models.OccupancyExportCsvPOJO;
import com.inicu.models.PredictLOS;
import com.inicu.models.QIJSON;
import com.inicu.models.SinJSON;
import com.inicu.models.UsageAnalytics;
import com.inicu.models.VitalTracker;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.service.AnalyticsService;
import org.json.JSONObject;

import javax.validation.Valid;

/**
 * 
 * @author iNICU
 *
 */

@RestController
public class AnalyticsController {

	@Autowired
	AnalyticsService analyticsService;

	public AnalyticsController() {
		System.out.println("Analytics Controller Layer");
	}

	@RequestMapping(value = "/inicu/getBedOccupancy/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<BedOccupancyObj> getBedOccupancy(@PathVariable("loggedInUser") String loggedInUser) {

		BedOccupancyObj bedOccupancy = analyticsService.getBedOccupied(loggedInUser);
		return new ResponseEntity<BedOccupancyObj>(bedOccupancy, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getInicuModuleUsageRecords/{recordDate}/{loggedInUser}/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<HashMap<Object, Object>> getInicuModuleUsageRecords(
			@PathVariable("loggedInUser") String loggedInUser, @PathVariable("recordDate") String recordDate,
			@PathVariable("uhid") String uhid) {

		HashMap<Object, Object> inicuModuleRecords = analyticsService.getInicuModuleUsageRecords(recordDate,
				loggedInUser, uhid);

		return new ResponseEntity<HashMap<Object, Object>>(inicuModuleRecords, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getUsageAnalytics/{loggedInUser}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<UsageAnalytics> getUsageAnalytics(@PathVariable("loggedInUser") String loggedInUser, 
			@PathVariable("branchName") String branchName) {
		UsageAnalytics patientAnalytics = analyticsService.getUsageAnalytics(loggedInUser, branchName);
		return new ResponseEntity<UsageAnalytics>(patientAnalytics, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getSinSheetData/{fromDate}/{toDate}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<SinJSON>> getSinSheetData(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("branchName") String branchName) {
		List<SinJSON> returnObj = analyticsService.getSinSheetData(fromDate, toDate, branchName);
		return new ResponseEntity<List<SinJSON>>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getSinUHIDData/{dateStr}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<BabyDetail>> getSinUHIDData(@PathVariable("dateStr") String dateStr, @PathVariable("branchName") String branchName) {
		List<BabyDetail> returnObj = analyticsService.getSinUHIDData(dateStr, branchName);
		return new ResponseEntity<List<BabyDetail>>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getQIData/{fromDate}/{toDate}/{fromGest}/{toGest}/{fromWeight}/{toWeight}/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<QIJSON> getQIData(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("fromGest") String fromGest,
			@PathVariable("toGest") String toGest, @PathVariable("fromWeight") String fromWeight,
			@PathVariable("toWeight") String toWeight, @PathVariable("branchName") String branchName) {
		QIJSON returnObj = analyticsService.getQIData(fromDate, toDate, fromGest, toGest, fromWeight, toWeight, branchName);
		return new ResponseEntity<QIJSON>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAnalyticsUsageGraph/{fromDate}/{toDate}/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, LinkedHashMap<Timestamp, Double>>> getAnalyticsUsageGraph(
			@PathVariable("fromDate") String fromDateStr, @PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName) {
		HashMap<String, LinkedHashMap<Timestamp, Double>> returnObj = analyticsService
				.getAnalyticsUsageGraph(fromDateStr, toDateStr, branchName);
		return new ResponseEntity<HashMap<String, LinkedHashMap<Timestamp, Double>>>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAnalyticsUsage/{fromDate}/{toDate}/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<List<AnalyticsUsagePojo>> getAnalyticsUsage(@PathVariable("fromDate") String fromDateStr,
			@PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName) {
		List<AnalyticsUsagePojo> returnObj = analyticsService.getAnalyticsUsage(fromDateStr, toDateStr, branchName);
		return new ResponseEntity<List<AnalyticsUsagePojo>>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getOccupancyData/{fromDate}/{toDate}/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, AnalyticsUsageHelperPOJO>> getOccupancyData(@RequestBody AnalyticsFiltersObj analyticsFilterObj,
			@PathVariable("fromDate") String fromDateStr, @PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName) {
		HashMap<String, AnalyticsUsageHelperPOJO> returnObj = analyticsService.getOccupancyData(fromDateStr, toDateStr, branchName, analyticsFilterObj);
		return new ResponseEntity<HashMap<String, AnalyticsUsageHelperPOJO>>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getExportOccupancy/{fromDate}/{toDate}/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<List<OccupancyExportCsvPOJO>> getExportOccupancy(@RequestBody AnalyticsFiltersObj analyticsFilterObj,
			@PathVariable("fromDate") String fromDateStr, @PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName) {
		List<OccupancyExportCsvPOJO> returnObj = analyticsService.getExportOccupancy(fromDateStr, toDateStr, branchName, analyticsFilterObj);
		return new ResponseEntity<List<OccupancyExportCsvPOJO>>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getSystemicReport/{fromDate}/{toDate}/{branchName}/{reportType}/", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, Object>> getSystemicReport(@PathVariable("fromDate") String fromDateStr,
			@PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName, @PathVariable("reportType") String reportType) {
		HashMap<String, Object> returnObj = analyticsService.getSystemicReport(fromDateStr, toDateStr, branchName,reportType);
		return new ResponseEntity<HashMap<String, Object>>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getSystemicReportDevice/{fromDate}/{toDate}/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, Object>> getSystemicReportDevice(@PathVariable("fromDate") String fromDateStr,
			@PathVariable("toDate") String toDateStr, @PathVariable("uhid") String uhid) {
		HashMap<String, Object> returnObj = analyticsService.getSystemicReportDevice(fromDateStr, toDateStr, uhid);
		return new ResponseEntity<HashMap<String, Object>>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getVitalTracker/{fromDate}/{toDate}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<VitalTracker>> getVitalTracker(@PathVariable("fromDate") String fromDateStr,
			@PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName) {
		List<VitalTracker> patientAnalytics = analyticsService.getVitalsData(fromDateStr, toDateStr, branchName);
		return new ResponseEntity<List<VitalTracker>>(patientAnalytics, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getHMLMStatus/{fromDate}/{toDate}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<HMAnalyticsPOJO> getHMLMStatus(@PathVariable("fromDate") String fromDateStr,
			@PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName) {
		HMAnalyticsPOJO patientAnalytics = analyticsService.getHMObservableData(fromDateStr, toDateStr, branchName);
		return new ResponseEntity<HMAnalyticsPOJO>(patientAnalytics, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getNutritionalComplianceData/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<List<NutritionalCompliancePOJO>> getNutritionalComplianceData(@PathVariable("branchName") String branchName) {
		List<NutritionalCompliancePOJO> patientAnalytics = analyticsService.getNutritionalComplianceData(branchName);
		return new ResponseEntity<List<NutritionalCompliancePOJO>>(patientAnalytics, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getBaselineData/{fromDate}/{toDate}/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<BaselinePOJO> getBaselineData(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("branchName") String branchName) {
		BaselinePOJO returnObj = analyticsService.getBaselineData(fromDate, toDate, branchName);
		return new ResponseEntity<BaselinePOJO>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/predictLOS/{branchName}/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<List<PredictLOS> > predictLOS(@PathVariable("branchName") String branchName, @PathVariable("uhid") String uhid) {
		List<PredictLOS> returnObj = analyticsService.predictLOS(branchName, uhid);
		return new ResponseEntity<List<PredictLOS>>(returnObj, HttpStatus.OK);
	}



	@RequestMapping(value = "/inicu/customreports/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<List<Object>> getCustomReportsData( @PathVariable("branchName") String branchName,@Valid @RequestBody String data) {
		System.out.println("data in request----------"+data);
		String response = null;
		JSONObject requestJson = null;
		try {
			requestJson = new JSONObject(data);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("requestJson in request----------"+requestJson);




		//@PathVariable("branchName") String branchName) {
		List<Object> returnObj = analyticsService.getCustomReportsData(branchName,requestJson);
		return new ResponseEntity<List<Object>>(returnObj, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/savecustomreports/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<String> saveCustomReports( @PathVariable("branchName") String branchName,@Valid @RequestBody String data) {
		System.out.println("data in request----------"+data);
		String response = null;
		JSONObject requestJson = null;
		try {
			requestJson = new JSONObject(data);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("requestJson in request----------"+requestJson);




		//@PathVariable("branchName") String branchName) {
		String str = analyticsService.saveCustomReportsData(branchName,requestJson);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/getallcustomreports/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<List<CustomReports>> getAllCustomReports(@PathVariable("branchName") String branchName) {
		List<CustomReports> returnObj = analyticsService.getAllCustomReports( branchName);
		return new ResponseEntity<List<CustomReports>>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getDistributionCurves/{fromDate}/{toDate}/{branchName}/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<DistributionCurvesPOJO> getDistributionCurves(@PathVariable("fromDate") String fromDateStr,
			@PathVariable("toDate") String toDateStr, @PathVariable("branchName") String branchName, @PathVariable("uhid") String uhid) {
		DistributionCurvesPOJO patientAnalytics = analyticsService.getDistributionCurves(fromDateStr, toDateStr, branchName, uhid);
		return new ResponseEntity<DistributionCurvesPOJO>(patientAnalytics, HttpStatus.OK);
	}


}
