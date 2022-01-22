package com.inicu.postgres.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.mail.Message.RecipientType;

import com.inicu.postgres.entities.*;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inicu.his.data.acquisition.HISConstants;
import com.inicu.his.data.acquisition.HISReceiver;
import com.inicu.models.KeyValueObj;
import com.inicu.models.LabOrdersJSON;
import com.inicu.models.LabOrdersSentPOJO;
import com.inicu.models.LabOrdersSentReport;
import com.inicu.models.MappedListPOJO;
import com.inicu.models.PatientInfoAddChildObj;
import com.inicu.models.PatientInfoAdmissonFormObj;
import com.inicu.models.PatientInfoChildDetailsObj;
import com.inicu.models.RefTestslist;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.SampleSentPOJO;
import com.inicu.models.TestItemObject;
import com.inicu.models.TestNamePOJO;
import com.inicu.models.TestResultFiltersObject;
import com.inicu.models.TestResultInputObject;
import com.inicu.models.TestResultObject;
import com.inicu.models.TestResultsPrintPOJO;
import com.inicu.models.TestResultsViewPOJO;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.SystematicDAO;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.CalendarUtility;
import com.inicu.postgres.utility.InicuDatabaseExeption;

/**
 * 
 * @author
 *
 */
@Service
public class TestsServiceImpl implements TestsService {

	@Autowired
	InicuDao inicuDao;

	@Autowired
	SystematicDAO sysDao;

	@Autowired
	PatientDao patientDao;

	public TestsServiceImpl() {
		System.out.println("TestsServiceImpl Layer");
	}

	@Override
	public List<TestItemResult> getTestItemResults(String uhid, String startdate, String enddate,
			List<String> testslist) {
		List<TestItemResult> testItemResultList = null;

		if (!uhid.isEmpty()) {
			String queryTestItems = "select t from TestItemResult t where prn='" + uhid + "' ";

			if (!startdate.isEmpty() && !enddate.isEmpty()) {
				// again check for start date < end date ??

				queryTestItems += ("and resultdate >= '" + startdate + "' and resultdate <= '" + enddate + "'");
			}
			if (testslist != null) {
				String testsListString = "( ";

				for (int i = 0; i < testslist.size(); i++) {
					// builder.append("'?',");
					testsListString += "'" + testslist.get(i) + "' ,";
				}

				StringBuilder sb = new StringBuilder(testsListString);
				sb.deleteCharAt(sb.length() - 1);

				testsListString = sb.toString() + " ) ";

				queryTestItems += " and testid in " + testsListString;
				System.out.println(testsListString);
				String query = "select resultdate,testid,to_char(creationtime, 'HH24:MI TZ') as time from test_result t "
						+ "where uhid='170302874' and resultdate >= '2017-03-23' and resultdate <= '2017-04-10'  "
						+ "group by testid,resultdate,creationtime order by resultdate desc, creationtime desc ;";

			}

			System.out.println(queryTestItems);

			testItemResultList = inicuDao.getListFromMappedObjQuery(queryTestItems);

		}

		return testItemResultList;
	}

	public List<TestResultsPrintPOJO> getTestsPrint(String uhid) {

		List<TestResultsPrintPOJO> returnList = new ArrayList<TestResultsPrintPOJO>();

		List<TestResultsViewPOJO> testResultList = new ArrayList<TestResultsViewPOJO>();
		TreeMap<String,List<TestItemResult>> testResultData = new TreeMap<>();
		TreeMap<String,List<String>> dataItems = new TreeMap<>();

		String queryTestResult = "select obj from TestItemResult as obj where prn='"
				+ uhid + "' order by resultdate";
		List<TestItemResult> allTestResultList = inicuDao.getListFromMappedObjQuery(queryTestResult);	
		if(!BasicUtils.isEmpty(allTestResultList)){
			for(int i=0;i<allTestResultList.size();i++) {
				TestItemResult testItemResultObj = (TestItemResult)allTestResultList.get(i);
				if(testItemResultObj != null) {
					if(testItemResultObj.getResultdate() != null) {
						String testDate = (testItemResultObj.getResultdate()) + "";
						List<TestItemResult> singleDateTestDetail = new ArrayList<>();
						List<String> singleDateItemDetail = new ArrayList<>();

						if(testResultData.containsKey(testDate)) {
							singleDateTestDetail = testResultData.get(testDate);
						}
						singleDateTestDetail.add(testItemResultObj);
						testResultData.put(testDate, singleDateTestDetail);
						
						//Getting the unique item ids for every date available
						if(dataItems.containsKey(testDate)) {
							singleDateItemDetail = dataItems.get(testDate);
						}
						singleDateItemDetail.add(testItemResultObj.getItemid());
						dataItems.put(testDate, singleDateItemDetail);
					}
				}
			}
		}
		if(!BasicUtils.isEmpty(dataItems) && !BasicUtils.isEmpty(testResultData))
		{
			//window Size is used to divide the dates so that they could be rendered easily on HTML
			int windowSize = 0;
			//window number used as a key to save dates and items in hashmap
			int windowNumber = 0;
			List<String> itemList = new ArrayList<String>();
			List<String> dateList = new ArrayList<String>();
			HashMap<Integer,List<String>> parentDates = new HashMap<>();
			HashMap<Integer,Set<String>> parentItems = new HashMap<>();
			HashMap<String,TestNamePOJO> finalItemMap = new HashMap<>();

			for(String date : dataItems.keySet()) {
				windowSize++;
				List<String> items = dataItems.get(date);
				
				//making a list of items and dates on the basis of group 5
				itemList.addAll(items);
				dateList.add(date);
				TestResultsViewPOJO resultObj = new TestResultsViewPOJO();
				
				//when counting of 5 is completed then processing the result data and refreshing the date and items list created earlier
				if(windowSize == 5) {
					finalItemMap = new HashMap<>();
					windowNumber++;
					Set<String> itemUniqueList = new HashSet<String>(itemList);

					parentDates.put(windowNumber, dateList);
					parentItems.put(windowNumber, itemUniqueList);
					resultObj.setDates(dateList);
					for(String dateObj : dateList) {
						TestNamePOJO testname = new TestNamePOJO();
						List<String> itemObj = dataItems.get(dateObj);
						
						//this loop is used to insert empty test result objects in the list of respective dates so that the length of all dates would be same
						for(String item : itemUniqueList) {
							if(!itemObj.contains(item)) {
								TestItemResult newObj = new TestItemResult();
								newObj.setItemid(item);
								List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
								singleDateTestDetail.add(newObj);
								testResultData.put(dateObj, singleDateTestDetail);
							}
						}
						List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
						//this loop is used to divide the results on the basis of item name as the requirement is to show the results on the basis of tests
						for(TestItemResult obj : singleDateTestDetail) {
							if(!BasicUtils.isEmpty(obj.getItemid())) {
								List<TestItemResult> results = new ArrayList<>();

								if(finalItemMap.containsKey(obj.getItemid())) {
									testname = finalItemMap.get(obj.getItemid());
									if(!BasicUtils.isEmpty(testname.getItemResults())) {
										results = testname.getItemResults();
										results.add(obj);
									}
									if(BasicUtils.isEmpty(testname.getTestName()) && !BasicUtils.isEmpty(obj.getItemname()))
										testname.setTestName(obj.getItemname());
								}
								else {
									testname = new TestNamePOJO();
									results.add(obj);
									if(!BasicUtils.isEmpty(obj.getItemname()))
										testname.setTestName(obj.getItemname());
								}
								testname.setItemResults(results);
							
								finalItemMap.put(obj.getItemid(), testname);
							}
						}
					}
					List<TestNamePOJO> finalListItems = new ArrayList<>();
					if(!BasicUtils.isEmpty(finalItemMap)) {
						for(String itemId : finalItemMap.keySet()) {
							TestNamePOJO testResultsList = finalItemMap.get(itemId);
							finalListItems.add(testResultsList);
						}
					}
					resultObj.setItemList(finalListItems);
					testResultList.add(resultObj);
					itemList = new ArrayList<String>();
					dateList = new ArrayList<String>();
					finalItemMap = new HashMap<>();
					windowSize = 0;
				}
			}
			//condition to handle the case when the count of the dates is not multiple of 5 and it will handle the left out dates
			if(windowSize < 5 && windowSize > 0) {
				windowNumber++;
				Set<String> itemUniqueList = new HashSet<String>(itemList);
				TestResultsViewPOJO resultObj = new TestResultsViewPOJO();

				parentDates.put(windowNumber, dateList);
				parentItems.put(windowNumber, itemUniqueList);
				resultObj.setDates(dateList);

				for(String dateObj : dateList) {
					List<String> itemObj = dataItems.get(dateObj);
					for(String item : itemUniqueList) {
						if(!itemObj.contains(item)) {
							TestItemResult newObj = new TestItemResult();
							newObj.setItemid(item);
							List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
							singleDateTestDetail.add(newObj);
							testResultData.put(dateObj, singleDateTestDetail);
						}
					}
					List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
					TestNamePOJO testname = new TestNamePOJO();
					for(TestItemResult obj : singleDateTestDetail) {
						if(!BasicUtils.isEmpty(obj.getItemid())) {
							List<TestItemResult> results = new ArrayList<>();

							if(finalItemMap.containsKey(obj.getItemid())) {
								testname = finalItemMap.get(obj.getItemid());
								if(!BasicUtils.isEmpty(testname.getItemResults())) {
									results = testname.getItemResults();
									results.add(obj);
								}
								if(BasicUtils.isEmpty(testname.getTestName()) && !BasicUtils.isEmpty(obj.getItemname()))
									testname.setTestName(obj.getItemname());
							}
							else {
								testname = new TestNamePOJO();
								results.add(obj);
								if(!BasicUtils.isEmpty(obj.getItemname()))
									testname.setTestName(obj.getItemname());
							}
							testname.setItemResults(results);
						
							finalItemMap.put(obj.getItemid(), testname);
						}
					}
				}
				List<TestNamePOJO> finalListItems = new ArrayList<>();
				if(!BasicUtils.isEmpty(finalItemMap)) {
					for(String itemId : finalItemMap.keySet()) {
						TestNamePOJO testResultsList = finalItemMap.get(itemId);
						finalListItems.add(testResultsList);
					}
				}
				resultObj.setItemList(finalListItems);
				testResultList.add(resultObj);
			}
		}
		
		if(!BasicUtils.isEmpty(testResultList)){
			TestResultsPrintPOJO obj = new TestResultsPrintPOJO();
			obj.setDepartmentname("");
			obj.setResultsTest(testResultList);
			returnList.add(obj);
		}

		return returnList;
	}

	@Override
	public HashMap<String, String> getTestsList() {

		HashMap<String, String> testsListComplete = new HashMap<String, String>();
		String queryTestsList = "select t from TestDetail t ";
		List<TestDetail> testsList = null;
		testsList = inicuDao.getListFromMappedObjQuery(queryTestsList);

		for (TestDetail testDetail : testsList) {
			testsListComplete.put(testDetail.getTestid(), testDetail.getTestname());
		}

		return testsListComplete;
	}


	@Override
	public List<TestResultObject> getTestResultsForProgressNotes(String uhid, String startdate, String enddate, String selectedTestID, String fromWhere) {
		List<TestResultObject> testResultObjectsList = new ArrayList<TestResultObject>();
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		String selectedTestIDStr = selectedTestID.replace(",", "','");

		if (!uhid.isEmpty()) {
			String queryTestItems = "select t from TestItemResult t where prn='" + uhid + "' ";

			if (!startdate.isEmpty()) {
				// again check for start date < end date ??
				queryTestItems += ("and lab_report_date >= '" + startdate + "'");
			}
			if (!enddate.isEmpty()) {
				queryTestItems += (" and lab_report_date <= '" + enddate + "'");
			}

			if (!selectedTestID.equals("0")) {
				queryTestItems += " and testid in ('" + selectedTestIDStr + "') ";
			}

			queryTestItems += " order by lab_report_date desc";

			System.out.println(queryTestItems);

			List<TestItemResult> testItemObjectsListRaw = inicuDao.getListFromMappedObjQuery(queryTestItems);

			int length = testItemObjectsListRaw.size();

			if (length > 0) {
				TestItemResult tirRaw = testItemObjectsListRaw.get(0); // Raw data from DB

				List<TestItemObject> tioList = new ArrayList<TestItemObject>();
				Timestamp reporttime = new Timestamp(tirRaw.getLabReportDate().getTime() - offset);
				Date resultdate = tirRaw.getResultdate();
				String testid = tirRaw.getTestid();

				TestResultObject tro = new TestResultObject(resultdate, reporttime, testid);

				TestItemObject tio = new TestItemObject();

				if (tirRaw.getViewType().equalsIgnoreCase("inicu_patientlabinfo_micro")) {
					tio.setItemname(tirRaw.getLabObservationName());
					tio.setItemunit(tirRaw.getUnit());
					tio.setItemvalue(BasicUtils.isEmpty(tirRaw.getValue()) ? "" : tirRaw.getValue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));

					if (tirRaw.getLabResultStatus() != null)
						tio.setResultstatus(tirRaw.getLabResultStatus());
				} else {
					tio.setItemid(tirRaw.getItemid());
					tio.setItemname(tirRaw.getItemname());
					tio.setItemunit(tirRaw.getItemunit());
					tio.setItemvalue(BasicUtils.isEmpty(tirRaw.getItemvalue()) ? "" : tirRaw.getItemvalue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));
					tio.setNormalrange(tirRaw.getNormalrange());

					// check for template Html
					if(!BasicUtils.isEmpty(tirRaw.getTemplateHtml())){
						tio.setTextualHtml(tirRaw.getTemplateHtml());
					}

					if (tirRaw.getLabResultStatus() != null)
						tio.setResultstatus(tirRaw.getLabResultStatus());
				}

				tioList.add(tio);

				/* trying via direct list */
				for (int i = 1; i < length; ++i) {

					tirRaw = testItemObjectsListRaw.get(i);
//					System.out.println(tirRaw.getItemid());
					Timestamp rt = new Timestamp(tirRaw.getLabReportDate().getTime() - offset);
					String ti = tirRaw.getTestid();
					Date dated = tirRaw.getResultdate();

					// 0.5 sec assumption because the test result values entered by nurse will have
					// time difference in creationtime in DB.

					if (reporttime != null && rt != null) {
						if ((reporttime.equals(rt)) && (resultdate.compareTo(dated) == 0) && testid.equals(ti)) {
							// test already exists,insert item now
							TestItemObject tioTemp = new TestItemObject();
							if (tirRaw.getViewType().equalsIgnoreCase("inicu_patientlabinfo_micro")) {
								tioTemp.setItemname(tirRaw.getLabObservationName());
								tioTemp.setItemunit(tirRaw.getUnit());
								tioTemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getValue()) ? "" : tirRaw.getValue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));

								if (tirRaw.getLabResultStatus() != null)
									tioTemp.setResultstatus(tirRaw.getLabResultStatus());
							} else {
								tioTemp.setItemid(tirRaw.getItemid());
								tioTemp.setItemname(tirRaw.getItemname());
								tioTemp.setItemunit(tirRaw.getItemunit());
								tioTemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getItemvalue()) ? "" : tirRaw.getItemvalue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));
								tioTemp.setNormalrange(tirRaw.getNormalrange());
								// check for template Html
								if(!BasicUtils.isEmpty(tirRaw.getTemplateHtml())){
									tio.setTextualHtml(tirRaw.getTemplateHtml());
								}

								if (tirRaw.getLabResultStatus() != null)
									tio.setResultstatus(tirRaw.getLabResultStatus());
							}
							tioList.add(tioTemp);
						} else {
							// new testid,creationtime,date combination
							tro.setTestItemObjectsList(tioList);
							testResultObjectsList.add(tro);
							tioList = new ArrayList<TestItemObject>();

							tro = new TestResultObject(dated, rt, ti);

							TestItemObject tiotemp = new TestItemObject();
							if (tirRaw.getViewType().equalsIgnoreCase("inicu_patientlabinfo_micro")) {
								tiotemp.setItemname(tirRaw.getLabObservationName());
								tiotemp.setItemunit(tirRaw.getUnit());
								tiotemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getValue()) ? "" : tirRaw.getValue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));

								if (tirRaw.getLabResultStatus() != null)
									tiotemp.setResultstatus(tirRaw.getLabResultStatus());
							} else {
								tiotemp.setItemid(tirRaw.getItemid());
								tiotemp.setItemname(tirRaw.getItemname());
								tiotemp.setItemunit(tirRaw.getItemunit());
								tiotemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getItemvalue()) ? "" : tirRaw.getItemvalue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));
								tiotemp.setNormalrange(tirRaw.getNormalrange());

								if (tirRaw.getLabResultStatus() != null)
									tio.setResultstatus(tirRaw.getLabResultStatus());

								// check for template Html
								if(!BasicUtils.isEmpty(tirRaw.getTemplateHtml())){
									tio.setTextualHtml(tirRaw.getTemplateHtml());
								}
							}
							tioList.add(tiotemp);

							reporttime = rt;
							resultdate = dated;
							testid = ti;
						}
					}
				}
				tro.setTestItemObjectsList(tioList);
				testResultObjectsList.add(tro);
			}
		}

		return testResultObjectsList;
	}

	@Override
	public List<TestResultObject> getTestResults(String uhid, String startdate, String enddate, String selectedTestID,
												 String fromWhere) {

		List<TestResultObject> testResultObjectsList = new ArrayList<TestResultObject>();
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		if (!BasicUtils.isEmpty(selectedTestID) && !"0".equals(selectedTestID) && !"dropdown".equalsIgnoreCase(fromWhere)) {
		    String selectedTestIDStr = selectedTestID.replace(",", "','");
		    String testMappingQuery = "select t from TestslistMapping t where inicutestid in ('" + selectedTestIDStr + "')";

            String mappedTestStr = "";
		    List<TestslistMapping> testslistMappingList = inicuDao.getListFromMappedObjQuery(testMappingQuery);
            if (!BasicUtils.isEmpty(testslistMappingList)) {
                for (TestslistMapping t : testslistMappingList) {
                    if (BasicUtils.isEmpty(mappedTestStr)) {
                        mappedTestStr = t.getVendortestid();
                    } else {
                        mappedTestStr += "," + t.getVendortestid();
                    }
                }
            }
            selectedTestID = mappedTestStr.replace(",", "','");;
        }

		if (!uhid.isEmpty()) {
			String queryTestItems = "select t from TestItemResult t where prn='" + uhid + "' ";

			if (!startdate.isEmpty()) {
				// again check for start date < end date ??
				queryTestItems += ("and resultdate >= '" + startdate + "'");
			}
			if (!enddate.isEmpty()) {
				queryTestItems += (" and resultdate <= '" + enddate + "'");
			}

			if (!selectedTestID.equals("0")) {
				queryTestItems += " and testid in ('" + selectedTestID + "') ";
			}

			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
				queryTestItems += " and labApprovedDate is not null order by resultdate desc,  labReportDate desc,testid asc, itemid asc, id asc ";
			} else {
				queryTestItems += " order by resultdate desc,  labReportDate desc,testid asc, itemid asc, id asc ";
			}

			System.out.println(queryTestItems);

			List<TestItemResult> testItemObjectsListRaw = null;
			testItemObjectsListRaw = inicuDao.getListFromMappedObjQuery(queryTestItems);

			int length = testItemObjectsListRaw.size();

			if (length > 0) {
				TestItemResult tirRaw = testItemObjectsListRaw.get(0); // Raw data from DB

				List<TestItemObject> tioList = new ArrayList<TestItemObject>();
				Timestamp reporttime = new Timestamp(tirRaw.getLabReportDate().getTime() - offset);
				Date resultdate = tirRaw.getResultdate();
				String testid = tirRaw.getTestid();

				TestResultObject tro = new TestResultObject(resultdate, reporttime, testid);

				TestItemObject tio = new TestItemObject();

				if (tirRaw.getViewType().equalsIgnoreCase("inicu_patientlabinfo_micro")) {
					tio.setItemname(tirRaw.getLabObservationName());
					tio.setItemunit(tirRaw.getUnit());
					tio.setItemvalue(BasicUtils.isEmpty(tirRaw.getValue()) ? "" : tirRaw.getValue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));

					if (tirRaw.getLabResultStatus() != null)
						tio.setResultstatus(tirRaw.getLabResultStatus());
				} else {
					tio.setItemid(tirRaw.getItemid());
					tio.setItemname(tirRaw.getItemname());
					tio.setItemunit(tirRaw.getItemunit());
					tio.setItemvalue(BasicUtils.isEmpty(tirRaw.getItemvalue()) ? "" : tirRaw.getItemvalue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));
					tio.setNormalrange(tirRaw.getNormalrange());

					// check for template Html
					if(!BasicUtils.isEmpty(tirRaw.getTemplateHtml())){
						tio.setTextualHtml(tirRaw.getTemplateHtml());
					}

					if (tirRaw.getLabResultStatus() != null)
						tio.setResultstatus(tirRaw.getLabResultStatus());
				}

				tioList.add(tio);

				/* trying via direct list */
				for (int i = 1; i < length; ++i) {

					tirRaw = testItemObjectsListRaw.get(i);
//					System.out.println(tirRaw.getItemid());
					Timestamp rt = new Timestamp(tirRaw.getLabReportDate().getTime() - offset);
					String ti = tirRaw.getTestid();
					Date dated = tirRaw.getResultdate();

					// 0.5 sec assumption because the test result values entered by nurse will have
					// time difference in creationtime in DB.

					if (reporttime != null && rt != null) {
						if ((reporttime.equals(rt)) && (resultdate.compareTo(dated) == 0) && testid.equals(ti)) {
							// test already exists,insert item now
							TestItemObject tioTemp = new TestItemObject();
							if (tirRaw.getViewType().equalsIgnoreCase("inicu_patientlabinfo_micro")) {
								tioTemp.setItemname(tirRaw.getLabObservationName());
								tioTemp.setItemunit(tirRaw.getUnit());
								tioTemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getValue()) ? "" : tirRaw.getValue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));

								if (tirRaw.getLabResultStatus() != null)
									tioTemp.setResultstatus(tirRaw.getLabResultStatus());
							} else {
								tioTemp.setItemid(tirRaw.getItemid());
								tioTemp.setItemname(tirRaw.getItemname());
								tioTemp.setItemunit(tirRaw.getItemunit());
								tioTemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getItemvalue()) ? "" : tirRaw.getItemvalue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));
								tioTemp.setNormalrange(tirRaw.getNormalrange());
								// check for template Html
								if(!BasicUtils.isEmpty(tirRaw.getTemplateHtml())){
									tio.setTextualHtml(tirRaw.getTemplateHtml());
								}

								if (tirRaw.getLabResultStatus() != null)
									tio.setResultstatus(tirRaw.getLabResultStatus());
							}
							tioList.add(tioTemp);
						} else {
							// new testid,creationtime,date combination
							tro.setTestItemObjectsList(tioList);
							testResultObjectsList.add(tro);
							tioList = new ArrayList<TestItemObject>();

							tro = new TestResultObject(dated, rt, ti);

							TestItemObject tiotemp = new TestItemObject();
							if (tirRaw.getViewType().equalsIgnoreCase("inicu_patientlabinfo_micro")) {
								tiotemp.setItemname(tirRaw.getLabObservationName());
								tiotemp.setItemunit(tirRaw.getUnit());
								tiotemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getValue()) ? "" : tirRaw.getValue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));

								if (tirRaw.getLabResultStatus() != null)
									tiotemp.setResultstatus(tirRaw.getLabResultStatus());
							} else {
								tiotemp.setItemid(tirRaw.getItemid());
								tiotemp.setItemname(tirRaw.getItemname());
								tiotemp.setItemunit(tirRaw.getItemunit());
								tiotemp.setItemvalue(BasicUtils.isEmpty(tirRaw.getItemvalue()) ? "" : tirRaw.getItemvalue().replaceAll("<[^>]*>", "").replaceAll("HEAD", "").replaceAll("&nbsp;", ""));
								tiotemp.setNormalrange(tirRaw.getNormalrange());

								if (tirRaw.getLabResultStatus() != null)
									tio.setResultstatus(tirRaw.getLabResultStatus());

								// check for template Html
								if(!BasicUtils.isEmpty(tirRaw.getTemplateHtml())){
									tio.setTextualHtml(tirRaw.getTemplateHtml());
								}
							}
							tioList.add(tiotemp);

							reporttime = rt;
							resultdate = dated;
							testid = ti;
						}
					}
				}
				tro.setTestItemObjectsList(tioList);
				testResultObjectsList.add(tro);
			}
		}

		return testResultObjectsList;

	}

	@Override
	public HashMap<String, String> getTestsListMapped(String uhid, String selectedTestID, String fromWhere) {

		HashMap<String, String> testsListMapped = new HashMap<String, String>();

		if ("assessment".equalsIgnoreCase(fromWhere) && BasicUtils.isEmpty(selectedTestID)) {
			return testsListMapped;
		}

		if (!BasicUtils.isEmpty(selectedTestID) && !"0".equals(selectedTestID)) {
			String selectedTestIDStr = selectedTestID.replace(",", "','");
			String testMappingQuery = "select t from TestslistMapping t where inicutestid in ('" + selectedTestIDStr
					+ "')";

			String mappedTestStr = "";
			List<TestslistMapping> testslistMappingList = inicuDao.getListFromMappedObjQuery(testMappingQuery);
			if (!BasicUtils.isEmpty(testslistMappingList)) {
				for (TestslistMapping t : testslistMappingList) {
					if (BasicUtils.isEmpty(mappedTestStr)) {
						mappedTestStr = t.getVendortestid();
					} else {
						mappedTestStr += "," + t.getVendortestid();
					}
				}
			}
			selectedTestID = mappedTestStr.replace(",", "','");
			if ("assessment".equalsIgnoreCase(fromWhere) && BasicUtils.isEmpty(selectedTestID)) {
				return testsListMapped;
			}
		}

		List<String> testsList = null;
		String queryTestsList = "select distinct testid from test_result where prn = '" + uhid + "'";
		if (!BasicUtils.isEmpty(selectedTestID)) {
			queryTestsList += " and testid in ('" + selectedTestID + "') ";
		}
		testsList = inicuDao.getListFromNativeQuery(queryTestsList);

		HashMap<String, String> completeTestsListMapped = new HashMap<String, String>();
		completeTestsListMapped = getTestsList();
		for (String testid : testsList) {
			testsListMapped.put(testid, completeTestsListMapped.get(testid));
		}

		return testsListMapped;
	}

//	No longer reqd
//	@Override
//	public List<String> getTestsListUHID(String uhid) {
//		
//		/*
//		 * Return testid:testname mapping for the uhid
//		 * Testname in alphabetical order
//		 */
//		
//		/*
//		HashMap<String,String> testsListBaby = new HashMap<String,String>();
//		try {
//			String query = "SELECT orderinvestigation FROM sa_jaundice "+" WHERE uhid='"+uhid+"'"
//					+ " ORDER BY modificationtime DESC LIMIT 1";
//			List<String>investigationTests = inicuDao.getListFromNativeQuery(query);
//			
//			if((investigationTests!=null) && (investigationTests.size()>0))
//			{
//				String invTestsListString = investigationTests.get(0);
//				String [] testids = invTestsListString.split(",");
//				//List<String> container = Arrays.asList(items);
//				
//				HashMap<String,String> testsListMapped = getTestsListMapped();
//				for (int i = 0; i < testids.length; i++) {
//					if(testsListMapped.containsKey(testids[i]))
//					{
//						System.out.println(testsListMapped.get(testids[i]));
//						testsListBaby.put(testids[i], testsListMapped.get(testids[i]));
//						
//					}
//					
//					
//				}				
//			}
//		} */
//		
//		List<String>testsList = new ArrayList<String>();
//		LinkedHashMap<String,String> testsListBaby = new LinkedHashMap<String,String>();
//		
//		HashMap<String,String> completeTestsListMapped = getTestsListMapped();
//		try {
//			String query = "SELECT orderinvestigation FROM sa_jaundice "+" WHERE uhid='"+uhid+"' AND orderinvestigation IS NOT NULL ";
//					
//			
//			List<String>investigationTests = inicuDao.getListFromNativeQuery(query);
//					
//			if((investigationTests!=null) && (investigationTests.size()>0))
//			{
//				
//				// babyTestsListAll: will contain dupicate entries
//				List<String>babyTestsListAll = new ArrayList<String>();
//				for (int i = 0; i < investigationTests.size(); i++) {
//					
//					
//					String invTestsListString = investigationTests.get(i);
//					
//					
//					String[] babyTestIds= invTestsListString.replace(" ", "").trim().replace("[", "").replace("]", "")
//							.split(",");
//					babyTestsListAll.addAll(Arrays.asList(babyTestIds));	
//				}
//				
//				System.out.println(babyTestsListAll);
//				
//				
//				Set<String> babyTestsList = new HashSet<>();
//				babyTestsList.addAll(babyTestsListAll);
//				
//				//HashMap to store the elements as testid:testname "5031":"BLOOD UREA"
//				HashMap<String,String> HM1 = new HashMap<String,String>();
//				
//				//HashMap to store the elements as testname:testid "BLOOD UREA":5031" 
//				HashMap<String,String> HM2 = new HashMap<String,String>();
//				
//				for (String testid : babyTestsList) {
//					if(completeTestsListMapped.containsKey(testid))
//					{
//						HM1.put(testid, completeTestsListMapped.get(testid));
//						HM2.put(completeTestsListMapped.get(testid), testid);
//					}
//				}
//				
////				System.out.println(HM1);
////				System.out.println(HM2);
//				
//				
//				ArrayList<String> testNames = new ArrayList<String>(HM1.values());
//				System.out.println(testNames);
//				testNames.sort(null);
//				
//				System.out.println(testNames);
//				for (String testname : testNames) {
//					testsListBaby.put(HM2.get(testname), testname);
//					testsList.add(HM2.get(testname));
//					
//					
//				}
//				System.out.println("Hashmap formed "+testsListBaby);
//				System.out.println("Testslist returned"+testsList);
//
////				for (int i = 0; i < babyTestsListDistinct.size(); i++) {
////					
////					{
////						System.out.println(testsListMapped.get(babyTestsListDistinct[i]));
////						testsListBaby.put(babyTestsListDistinct[i], testsListMapped.get(babyTestsListDistinct[i]));
////						
////					}
////					
////					
////				}				
//			}
//		} 
//		
//		
//		
//		catch (Exception e) {
//			System.out.println(e);
//		}
//		
//		return testsList;
//	}

/////Gangaram
	@Override
	public HashMap<String, List<TestItemObject>> getTestItemNames(String testid) {

		// Currently, only jaundice results doing//for gangaram
		List<TestItemHelp> testItemsList = null;
		String queryTestsItemsList = "select t from TestItemHelp t ";
		if (testid != "")
			queryTestsItemsList += " WHERE testid='" + testid + "'";

		testItemsList = inicuDao.getListFromMappedObjQuery(queryTestsItemsList);
		HashMap<String, List<TestItemObject>> hm = new HashMap<>();
		if (!BasicUtils.isEmpty(testItemsList)) {

			for (TestItemHelp tih : testItemsList) {
				String testidCur = tih.getTestid();
				if (!hm.containsKey(testidCur)) {
					TestItemObject tio = new TestItemObject();
					tio.setItemid(tih.getItemid());
					tio.setItemname(tih.getItemname());
					tio.setItemunit(tih.getItemunit());
					tio.setItemvalue(tih.getTestvalue());
					tio.setNormalrange(tih.getNormalrange());
					List<TestItemObject> tioList = new ArrayList<TestItemObject>();
					tioList.add(tio);
					hm.put(testidCur, tioList);
				} else {
//					List<TestItemObject> tioList = hm.get(testidCur);
					TestItemObject tio = new TestItemObject();
					tio.setItemid(tih.getItemid());
					tio.setItemname(tih.getItemname());
					tio.setItemunit(tih.getItemunit());
					tio.setItemvalue(tih.getTestvalue());
					tio.setNormalrange(tih.getNormalrange());
//					tioList.add(tio);

					hm.get(testidCur).add(tio);
				}
			}
		}

		System.out.println(hm);
		return hm;
	}

	@Override
	public ResponseMessageObject saveTestResults(TestResultInputObject testResultInpObj, String uhid,
			String loggedUser) {

		ResponseMessageObject rmo = new ResponseMessageObject();

		if (testResultInpObj != null) {
			int numInvIds = testResultInpObj.getInvestigationIds().size();
			int numTestItems = testResultInpObj.getTestitems().size();

			// if(numInvIds!=numTestItems) can't check, testitems are not grouped they are
			// all in single list
			if (numInvIds > 0) {
				List<InvestigationOrdered> invOrdered = testResultInpObj.getInvestigationIds();
				for (InvestigationOrdered investigationOrdered : invOrdered) {
					investigationOrdered.setOrder_status("received");
					investigationOrdered.setReportreceived_user(loggedUser);
					investigationOrdered.setReportreceived_time(new Timestamp(System.currentTimeMillis()));
				}

				List<TestItemResult> testitems = testResultInpObj.getTestitems();
				try {
					inicuDao.saveMultipleObject(invOrdered);
					inicuDao.saveMultipleObject(testitems);
					rmo.setMessage("Success");
				} catch (Exception e1) {
					rmo.setMessage("Failure");
					e1.printStackTrace();
				}

			}

		} else
			rmo.setMessage("Failure");

		return rmo;
	}

	@Override
	public List<InvestigationOrdered> getInvestigationOrdered(String searchUhid, String searchTestId,
			String searchDate, String assessmentType) {

		// get investigations for "uhid" which are in sent state
		List<InvestigationOrdered> invOrderedList = null;
		String queryinvOrderedList = "select t from InvestigationOrdered t " + " WHERE uhid='" + searchUhid + "' ";

		if (!searchTestId.equals("0"))
			queryinvOrderedList += " AND testslistid='" + searchTestId + "' ";
		if (searchDate != "")
			queryinvOrderedList += " AND senttolab_time>='" + searchDate + " 00:00:00' " + "AND senttolab_time<='"
					+ searchDate + " 23:59:59.999' ";

        if (!BasicUtils.isEmpty(assessmentType) && !"no".equalsIgnoreCase(assessmentType)){
            queryinvOrderedList += " AND assesment_type ='" + assessmentType + "' ";
        } else {
			queryinvOrderedList += " AND order_status= 'sent' ORDER BY senttolab_time ASC ";
		}

		invOrderedList = inicuDao.getListFromMappedObjQuery(queryinvOrderedList);

		return invOrderedList;
	}

	@Override
	public List<InvestigationOrdered> getInvestigationRecd(String uhid) {

		List<InvestigationOrdered> invRecdList = null;

		String queryinvRecdList = "select t from InvestigationOrdered t " + " WHERE uhid='" + uhid + "' "
				+ " AND order_status= 'received' " + "";

		invRecdList = inicuDao.getListFromMappedObjQuery(queryinvRecdList);

		return invRecdList;

	}

	@Override
	public HashMap<String, String> getTestListId() {
		/*
		 * @vedaant working on this
		 * 
		 */
		HashMap<String, String> testsListMapped = new HashMap<String, String>();
		String queryTestsList = "select obj from RefTestslist as obj ";
		List<RefTestslist> testsList = inicuDao.getListFromMappedObjQuery(queryTestsList);
		if (!BasicUtils.isEmpty(testsList)) {
			for (RefTestslist refTestslist : testsList) {
				if (!testsListMapped.containsKey(refTestslist.getTestname())) {
					testsListMapped.put(refTestslist.getTestname(), refTestslist.getTestid());
				}

			}

		}

		return testsListMapped;
	}

	@Override
	public List<TestItemResult> getGraphData(String uhid, String itemname) {
		/*
		 * TODO: change itemname to itemid, add date parameter
		 * 
		 * 
		 */
		List<TestItemResult> testResults = null;

		String queryTestItems = "select obj from TestItemResult as obj " + " where prn='" + uhid + "' and "
				+ "itemname='" + itemname + "' " + " and itemvalue is not null and itemvalue!=''"
				+ " order by labReportDate desc ";
		testResults = inicuDao.getListFromMappedObjQuery(queryTestItems);

		// create different object and send only points
		return testResults;
	}

	@Override
	public LabOrdersJSON getLabOrders(String uhid) {

		LabOrdersJSON json = new LabOrdersJSON();

		Timestamp today = new Timestamp((new java.util.Date().getTime()));
		Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));

		String queryLabOrdersList = "select obj from InvestigationOrdered as obj where uhid = '" + uhid
				+ "' and ((investigationorder_time <= '" + today + "' and " + "investigationorder_time >= '" + yesterday
				+ "') or (issamplesent is null or issamplesent = '" + false + "'  )) order by investigationorder_time";

		List<InvestigationOrdered> investigationOrderedListRaw = null;
		investigationOrderedListRaw = inicuDao.getListFromMappedObjQuery(queryLabOrdersList);

		HashMap<Object, List<InvestigationOrdered>> ordersListMap = new HashMap<Object, List<InvestigationOrdered>>();

		for (InvestigationOrdered test : investigationOrderedListRaw) {
			List<InvestigationOrdered> investigationList = null;
			if (!BasicUtils.isEmpty(test.getInvestigationorder_time())) {
				if (!BasicUtils.isEmpty(test.getInvestigationorder_time().getTime())) {
					if (ordersListMap.get(test.getInvestigationorder_time().getTime()) != null) {
						investigationList = ordersListMap.get(test.getInvestigationorder_time().getTime());
					} else {
						investigationList = new ArrayList<InvestigationOrdered>();
					}
				} else {
					System.out.println("Time is null");
				}
				investigationList.add(test);
				ordersListMap.put(test.getInvestigationorder_time().getTime(), investigationList);
			} else {
				System.out.println("Time is null");
			}
		}

		json.setLabOrders(ordersListMap);

		queryLabOrdersList = "select distinct b.sampleid,b.investigationorder_time from " + BasicConstants.SCHEMA_NAME
				+ ".investigation_ordered as b where b.uhid = '" + uhid
				+ "' and b.sampleid is not null order by b.investigationorder_time desc";

		List<Object[]> listSampleIds = inicuDao.getListFromNativeQuery(queryLabOrdersList);

		List<LabOrdersSentPOJO> ordersSentLists = new ArrayList<LabOrdersSentPOJO>();

		for (Object[] SampleIdObject : listSampleIds) {

			String sampleId = (String) SampleIdObject[0];

			if (!BasicUtils.isEmpty(sampleId)) {

				List<LabOrdersSentReport> labOrdersSentReportList = new ArrayList<LabOrdersSentReport>();

				LabOrdersSentPOJO labOrders = new LabOrdersSentPOJO();

				String queryLabOrdersSampleIdList = "select obj from InvestigationOrdered as obj where uhid = '" + uhid
						+ "' and " + "sampleid = '" + sampleId + "' order by investigationorder_time";

				String querySamplesList = "select obj from SampleDetail as obj where sample_detail_id = '" + sampleId
						+ "'";

				investigationOrderedListRaw = null;
				investigationOrderedListRaw = inicuDao.getListFromMappedObjQuery(queryLabOrdersSampleIdList);
				List<SampleDetail> sampleDetailsListRaw = inicuDao.getListFromMappedObjQuery(querySamplesList);

				if (!BasicUtils.isEmpty(investigationOrderedListRaw)) {

					for (InvestigationOrdered test : investigationOrderedListRaw) {
						LabOrdersSentReport orderReport = new LabOrdersSentReport();
						orderReport.setInvestigationOrder(test);

						String inicuTestId = test.getTestslistid();

						String queryMapList = "select distinct vendortestid from " + BasicConstants.SCHEMA_NAME
								+ ".tests_list_mapping as b where b.inicutestid = '" + inicuTestId
								+ "' and b.vendortestid is not null";

						List<String> vendorId = inicuDao.getListFromNativeQuery(queryMapList);

						if (!BasicUtils.isEmpty(vendorId)) {
							String resultQuery = "select obj from TestItemResult as obj where testid = '"
									+ vendorId.get(0) + "' and prn = '" + uhid + "'";
							List<TestItemResult> resultListRaw = inicuDao.getListFromMappedObjQuery(resultQuery);
							if (!BasicUtils.isEmpty(resultListRaw)) {
								orderReport.setResults(resultListRaw);
							} else {
								orderReport.setResults(null);
							}
						} else {
							orderReport.setResults(null);
						}
						labOrdersSentReportList.add(orderReport);
					}
					labOrders.setOrdersList(labOrdersSentReportList);
					Timestamp sentDate = investigationOrderedListRaw.get(0).getSenttolab_time();
					labOrders.setSentdate(sentDate);

					Timestamp orderDate = investigationOrderedListRaw.get(0).getInvestigationorder_time();
					labOrders.setOrderdate(orderDate);

					if (!BasicUtils.isEmpty(sampleDetailsListRaw)) {
						labOrders.setSampletype(sampleDetailsListRaw.get(0).getSampletype());
						labOrders.setComments(sampleDetailsListRaw.get(0).getComments());
						labOrders.setSamplevolume(sampleDetailsListRaw.get(0).getSamplevolume());
						labOrders.setLoggeduser(sampleDetailsListRaw.get(0).getLoggeduser());
						labOrders.setUhid(uhid);
					}
					ordersSentLists.add(labOrders);
				}
			}
		}
		json.setLabOrdersSent(ordersSentLists);

		json.setMessage("successfully done");
		json.setType("w");

		return json;
	}

	@Override
	public ResponseMessageObject sentSample(SampleSentPOJO sampleSent) {

		ResponseMessageObject obj = new ResponseMessageObject();

		try {
			if (!BasicUtils.isEmpty(sampleSent.getOrdersList())) {
				SampleDetail sample = new SampleDetail();

				if (!BasicUtils.isEmpty(sampleSent.getComments())) {
					sample.setComments(sampleSent.getComments());
				}
				if (!BasicUtils.isEmpty(sampleSent.getSampletype())) {
					sample.setSampletype(sampleSent.getSampletype());
				}
				if (!BasicUtils.isEmpty(sampleSent.getSamplevolume())) {
					sample.setSamplevolume(sampleSent.getSamplevolume());
				}
				if (!BasicUtils.isEmpty(sampleSent.getUhid())) {
					sample.setUhid(sampleSent.getUhid());
				}
				if (!BasicUtils.isEmpty(sampleSent.getLoggeduser())) {
					sample.setLoggeduser(sampleSent.getLoggeduser());
				}
				SampleDetail newsample = (SampleDetail) patientDao.saveObject(sample);

				List<InvestigationOrdered> orders = sampleSent.getOrdersList();

				for (InvestigationOrdered order : orders) {
					order.setSenttolab_time(sampleSent.getSentdate());
					order.setSampleid(newsample.getSample_detail_id().toString());
					order.setIssamplesent(true);
					patientDao.saveObject(order);
				}
				obj.setMessage("Sample Sent successfully");
				obj.setType(BasicConstants.MESSAGE_SUCCESS);
			} else {
				obj.setType(BasicConstants.MESSAGE_FAILURE);
				obj.setMessage("No tests are available for send");
			}

		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
			obj.setMessage(e.toString());
		}

		return obj;
	}

	@Override
	public HashSet<String> getTestCategoryList(String uhid, String fromTime, String toTime) {
		HashSet<String> labTestCategoryList = new HashSet<String>();

		try {

			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Date entryTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
					.parse(fromTime);

			Date entryEndTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
					.parse(toTime);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
			String entryTimeServerFormat = formatter.format(entryTimeStamp);
			String entryEndTimeServerFormat = formatter.format(entryEndTimeStamp);

			if (!BasicUtils.isEmpty(entryTimeServerFormat) && !BasicUtils.isEmpty(entryEndTimeServerFormat)) {

				String fetchDistinctTest = "SELECT DISTINCT lab_testname,lab_report_date FROM test_result "
						+ "where prn = '" + uhid + "' and lab_report_date >= '" + entryTimeServerFormat
						+ "' and lab_report_date <= '" + entryEndTimeServerFormat
						+ "' ORDER BY lab_report_date,lab_testname ASC";
				List<Object[]> testnameAndDate = inicuDao.getListFromNativeQuery(fetchDistinctTest);
				if (!BasicUtils.isEmpty(testnameAndDate)) {
					for (int i = 0; i < testnameAndDate.size(); i++) {
						Object testname = testnameAndDate.get(i)[0];
						labTestCategoryList.add(testname.toString());

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return labTestCategoryList;
	}

	@Override
	public HashMap<String, List<TestItemResult>> getTestItemList(String uhid, String fromTime, String toTime,
			String testName) {
		HashMap<String, List<TestItemResult>> masterMap = new HashMap<>();
		try {
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();

			Date entryTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
					.parse(fromTime);

			Date entryEndTimeStamp = CalendarUtility.getDateFormatGMT(CalendarUtility.CLIENT_CRUD_OPERATION)
					.parse(toTime);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
			String entryTimeServerFormat = formatter.format(entryTimeStamp);
			String entryEndTimeServerFormat = formatter.format(entryEndTimeStamp);

			if (!BasicUtils.isEmpty(entryTimeServerFormat) && !BasicUtils.isEmpty(entryEndTimeServerFormat)) {
				String fetchDistinctTest = "SELECT DISTINCT itemname FROM test_result " + "where prn = '" + uhid
						+ "' and lab_report_date >= '" + entryTimeServerFormat + "' and lab_report_date <= '"
						+ entryEndTimeServerFormat + "' " + " and lab_testname ='" + testName + "'";
				List<Object> itemNames = inicuDao.getListFromNativeQuery(fetchDistinctTest);

				if (!BasicUtils.isEmpty(itemNames)) {
					for (Object itemname : itemNames) {
						if (!BasicUtils.isEmpty(itemname)) {
							String fetchitemDetails = "Select obj from TestItemResult as obj where prn='" + uhid
									+ "' and itemname ='" + itemname + "' and labReportDate >= '"
									+ entryTimeServerFormat + "' and labReportDate <= '" + entryEndTimeServerFormat
									+ "' and labTestName='" + testName + "' ORDER BY labReportDate ASC";
							List<TestItemResult> listTestData = inicuDao.getListFromMappedObjQuery(fetchitemDetails);

							if (masterMap.containsKey(itemname.toString())) {
								List<TestItemResult> tempList = masterMap.get(itemname.toString());
								tempList.addAll(listTestData);
								masterMap.put(itemname.toString(), null);
								masterMap.put(itemname.toString(), tempList);
							} else {
								masterMap.put(itemname.toString(), listTestData);

							}

						}

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return masterMap;
	}

}
