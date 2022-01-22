package com.inicu.postgres.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.inicu.models.BabyVisitsObj;
import com.inicu.models.LabOrdersJSON;
import com.inicu.models.LabOrdersSentPOJO;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.models.SampleSentPOJO;
import com.inicu.models.TestItemObject;
import com.inicu.models.TestResultFiltersObject;
import com.inicu.models.TestResultInputObject;
import com.inicu.models.TestResultObject;
import com.inicu.models.TestResultsPrintPOJO;
import com.inicu.models.TestResultsViewPOJO;
import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.TestDetail;
import com.inicu.postgres.entities.TestItemHelp;
import com.inicu.postgres.entities.TestItemResult;



@Repository
public interface TestsService {
	
	List<TestItemResult> getTestItemResults(String uhid, String startdate, String enddate, List<String>testslist);
	
	HashMap<String, String> getTestsList() ;
	
	List<TestResultsPrintPOJO> getTestsPrint(String uhid) ;
	
	List<TestResultObject> getTestResults(String uhid, String startdate, String enddate, String selectedTestID,
                                          String fromWhere);

	List<TestResultObject> getTestResultsForProgressNotes(String uhid, String startdate, String enddate, String selectedTestID,
										  String fromWhere);

	HashMap<String, String> getTestsListMapped(String uhid, String selectedtestID, String fromWhere) ;
	
	//LinkedHashMap because order to be preserved
//	List<String> getTestsListUHID(String uhid);
	
	//Currently for Gangaram
//	List<TestItemHelp> getTestItemNames(String testid);  
	HashMap<String, List<TestItemObject>> getTestItemNames(String testid);
	//Currently for Gangaram
	ResponseMessageObject saveTestResults(TestResultInputObject testResultObj, String uhid, String loggedUser);
	
	List<InvestigationOrdered> getInvestigationOrdered(String uhid, String testId,String searchDate, String assessmentType);
	List<InvestigationOrdered> getInvestigationRecd(String uhid);

	
	
	/*
	 * Return tests list currently in INICU, 
	 * with key testname, value testid 
	 */	
	HashMap<String,String> getTestListId();
	
	/*
	 * Get individual test item results
	 * Required for Control Graph
	 */
	List<TestItemResult> getGraphData(String uhid, String itemname);
	
	LabOrdersJSON getLabOrders(String uhid);
	
	ResponseMessageObject sentSample(SampleSentPOJO sampleSent);

	// Trends Screen LabReports API
	HashSet<String> getTestCategoryList(String uhid, String fromTime, String toTime);

	HashMap<String, List<TestItemResult>> getTestItemList(String uhid, String fromTime, String toTime, String testName);

}