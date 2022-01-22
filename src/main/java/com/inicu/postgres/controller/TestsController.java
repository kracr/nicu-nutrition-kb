
package com.inicu.postgres.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.models.LabOrdersJSON;
import com.inicu.models.LabOrdersSentPOJO;
import com.inicu.models.RefTestslist;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.models.SampleSentPOJO;
import com.inicu.models.TestItemObject;
import com.inicu.models.TestResultFiltersObject;
import com.inicu.models.TestResultInputObject;
import com.inicu.models.TestResultObject;
import com.inicu.models.TestResultsPrintPOJO;
import com.inicu.models.TestResultsViewPOJO;
import com.inicu.models.TestsListJSON;
import com.inicu.models.TpnFeedPojo;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.InvestigationOrdered;
import com.inicu.postgres.entities.TestDetail;
import com.inicu.postgres.entities.TestItemHelp;
import com.inicu.postgres.entities.TestItemResult;
import com.inicu.postgres.entities.TestItemResultInicu;
import com.inicu.postgres.entities.VwBloodProduct;
import com.inicu.postgres.service.DischargeSummaryService;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.NotesService;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.SystematicService;
import com.inicu.postgres.service.TestsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;



/**
 * @author vedaant
 */
@RestController
public class TestsController {

	@Autowired
	TestsService testsService;
	
	@Autowired
	SystematicService sysService;
	
		
	@RequestMapping(value="/inicu/getTestResults", method = RequestMethod.POST)
	ResponseEntity<List<TestResultObject>> getTestResults(@RequestBody TestResultFiltersObject searchFilters){
		List<TestResultObject> testResults = null;
		
		try {

			List<String>testListTemp = null;
			String selectedtestID = "0"; // selected test id default;
			if(searchFilters.getSelectedTestID()!=null)
				selectedtestID = searchFilters.getSelectedTestID();
			testResults = testsService.getTestResults(searchFilters.getSearchedUHID()+"", searchFilters.getSearchedStartDate()+"", searchFilters.getSearchedEndDate()+"", selectedtestID,
					searchFilters.getFromWhere());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<TestResultObject>>(testResults, HttpStatus.OK);
	}
	

	@RequestMapping(value="/inicu/getTestsList", method = RequestMethod.GET)
	ResponseEntity<HashMap<String, String>> getTestsList(){
		HashMap<String, String> testsList = null;
		
		try {
			testsList = testsService.getTestsList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HashMap<String, String>>(testsList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/inicu/getTestsPrint/{uhid}/", method = RequestMethod.GET)
	ResponseEntity<List<TestResultsPrintPOJO>> getTestsPrint(@PathVariable("uhid") String uhid){
		List<TestResultsPrintPOJO> testsListMapped = new ArrayList<>();
		try {
			testsListMapped = testsService.getTestsPrint(uhid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<TestResultsPrintPOJO>>(testsListMapped, HttpStatus.OK);
	}
	
	@RequestMapping(value="/inicu/getTestsListMapped/{uhid}/{selectedtestID}/{fromWhere}", method = RequestMethod.GET)
	ResponseEntity<HashMap<String, String> > getTestsListMapped(@PathVariable("uhid") String uhid,
																@PathVariable("selectedtestID") String selectedtestID,
																@PathVariable("fromWhere") String fromWhere){
		HashMap<String,String> testsListMapped = new HashMap<String,String>();
//		try {
//			testsService.temporaryFunction();
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			testsListMapped = testsService.getTestsListMapped(uhid, selectedtestID, fromWhere);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HashMap<String, String>>(testsListMapped, HttpStatus.OK);
	}
	
//	@RequestMapping(value="/inicu/getTestsListUHID/{uhid}", method = RequestMethod.GET)
//	ResponseEntity<List<String>> getTestsListUHID(@PathVariable("uhid") String uhid){
//		List<String> testsList = new ArrayList<String>();
//		try {
//			testsList = testsService.getTestsListUHID(uhid);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<List<String>>(testsList, HttpStatus.OK);
//	}
	
	
	//Currently jaundice,Gangaram
//	@RequestMapping(value="/inicu/getTestItemsList/", method = RequestMethod.GET)
//	ResponseEntity<List<TestItemHelp>> getTestItemNames(){
//		List<TestItemHelp> testsItemsList = null;
//		try {
//			
//			testsItemsList = testsService.getTestItemNames("");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<List<TestItemHelp>>(testsItemsList, HttpStatus.OK);
//	}
	
	@RequestMapping(value="/inicu/getTestItemsList/", method = RequestMethod.GET)
	ResponseEntity<HashMap<String, List<TestItemObject>>> getTestItemNames(){
		HashMap<String, List<TestItemObject>> hm = new HashMap<>();
		try {
			
			hm = testsService.getTestItemNames("");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HashMap<String, List<TestItemObject>> >(hm, HttpStatus.OK);
	}
	
//	@RequestMapping(value="/inicu/getTestItemsList/{testid}", method = RequestMethod.GET)
//	ResponseEntity<List<TestItemHelp>> getTestItemNames(@PathVariable("testid") String testid){
//		List<TestItemHelp> testsItemsList = null;
//		try {
//			
//			testsItemsList = testsService.getTestItemNames(testid);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<List<TestItemHelp>>(testsItemsList, HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/inicu/saveTestResults/{uhid}/{loggeduser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveTestResults(@RequestBody String testInputResultString,
			@PathVariable("uhid") String uhid, @PathVariable("loggeduser") String loggeduser) {
		
		ResponseMessageObject response = new ResponseMessageObject();
		System.out.println("Recevied input string  "+testInputResultString);
		
		
		if (uhid == null || uhid.isEmpty() || loggeduser==null || loggeduser.isEmpty()) 
			return new ResponseEntity<ResponseMessageObject>(response, HttpStatus.BAD_REQUEST);
		
		try {
			
			
		    ObjectMapper mapper = new ObjectMapper();
//		    HashMap tio1 = mapper.readValue(testInputResultString,HashMap.class);
		    TestResultInputObject testResultInpObject = mapper.readValue(testInputResultString, TestResultInputObject.class);
		    
		    System.out.println("TestResultInputObject formed "+testResultInpObject);
		    			
		    response = (ResponseMessageObject) testsService.saveTestResults(testResultInpObject, uhid, loggeduser);
//			logService.saveLog("Adding labtest entry data.", "insert", nursingNotesObj.getLoggedInUserId().toString(),
//					nursingNotesObj.getUhid().toString(), BasicConstants.PAGE_NURSING_NOTES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageObject>(response, HttpStatus.OK);
	}
	
	//Currently jaundice,Gangaram
		@RequestMapping(value="/inicu/getInvestigationsOrdered/{searchedUhid}/{searchedTestid}/{searchedDate}/{assessmentType}", method = RequestMethod.GET)
		ResponseEntity<List<InvestigationOrdered>> getInvOrdered(@PathVariable("searchedUhid") String searchedUhid,
				@PathVariable("searchedTestid") String searchedTestid,
				@PathVariable("searchedDate") String searchedDate,
				@PathVariable("assessmentType") String assessmentType){
			List<InvestigationOrdered> invOrderedList = null;
			
			String testId   = searchedTestid; String searchDate = searchedDate;
			if(BasicUtils.isEmpty(searchedTestid))
				testId = "0";
			if(BasicUtils.isEmpty(searchedDate))
				searchDate = "";
			if(searchedUhid!=null && !searchedUhid.isEmpty())
			{				
				try {
					invOrderedList = testsService.getInvestigationOrdered(searchedUhid, testId, searchDate, assessmentType);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			return new ResponseEntity<List<InvestigationOrdered>>(invOrderedList, HttpStatus.OK);
		}
	
		@RequestMapping(value="/inicu/getInvestigationsRecd/{uhid}/{date}", method = RequestMethod.GET)
		ResponseEntity<List<InvestigationOrdered>> getInvRecd(@PathVariable("uhid") String uhid){
			List<InvestigationOrdered> invRecdList = null;
			if(uhid!=null && !uhid.isEmpty())
			{
				try {
					invRecdList = testsService.getInvestigationRecd(uhid);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			return new ResponseEntity<List<InvestigationOrdered>>(invRecdList, HttpStatus.OK);
		}
		
		@RequestMapping(value="/inicu/getTestItemResults/{uhid}/{itemname}/", method = RequestMethod.GET)
		ResponseEntity<List<TestItemResult>> getGraphData(@PathVariable("uhid") String uhid,
				@PathVariable("itemname") String itemname){
			
			List<TestItemResult> testItems = null;
			if(uhid!=null && !uhid.isEmpty()){
				testItems =testsService.getGraphData(uhid, itemname);
			}
			
			return new ResponseEntity<List<TestItemResult>>(testItems, HttpStatus.OK);
		}
		
		@RequestMapping(value="/inicu/getLabOrders/{uhid}/", method = RequestMethod.GET)
		public ResponseEntity<LabOrdersJSON> getLabOrders(@PathVariable("uhid") String uhid){
			
			LabOrdersJSON json = new LabOrdersJSON();
			if(uhid!=null && !uhid.isEmpty()){
				json = testsService.getLabOrders(uhid);
				json.setMessage(BasicConstants.MESSAGE_SUCCESS);
				json.setType("Data retrieved successfully");
			}
			
			return new ResponseEntity<LabOrdersJSON>(json , HttpStatus.OK);
		}
		
		@RequestMapping(value="/inicu/sentSample/", method = RequestMethod.POST)
		public ResponseEntity<LabOrdersJSON> postLabOrders(@RequestBody SampleSentPOJO sampleSent){
			
			
			ResponseMessageObject obj = testsService.sentSample(sampleSent);
			LabOrdersJSON json = new LabOrdersJSON();
			if(sampleSent.getUhid()!=null && !sampleSent.getUhid().isEmpty()){
				json = testsService.getLabOrders(sampleSent.getUhid());
				json.setMessage(obj.getMessage());
				json.setType(obj.getType());
			}
			
			return new ResponseEntity<LabOrdersJSON>(json , HttpStatus.OK);
		}
		
		@RequestMapping(value="/inicu/getTestCategoryList/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
		ResponseEntity<HashSet<String>> getLabReportTestCategoryList(@PathVariable("uhid") String uhid,
				@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime){
			HashSet<String> labReportList = new HashSet<>();
			try {
				labReportList = testsService.getTestCategoryList(uhid, fromTime , toTime);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<HashSet<String>>(labReportList, HttpStatus.OK);
		}
		
		@RequestMapping(value="/inicu/getItemListForGivenTest/{uhid}/{fromTime}/{toTime}/{testName}/", method = RequestMethod.GET)
		ResponseEntity<HashMap<String,List<TestItemResult>>> getItemListForGivenTest(@PathVariable("uhid") String uhid,
				@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime
				,@PathVariable("testName") String testName){
			HashMap<String,List<TestItemResult>> labReportTestItemList = new HashMap<>();
			try {
				labReportTestItemList = testsService.getTestItemList(uhid, fromTime , toTime , testName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<HashMap<String,List<TestItemResult>>>(labReportTestItemList, HttpStatus.OK);
		}
		

		
}
