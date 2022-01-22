package com.inicu.his.data.acquisition;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.inicu.postgres.entities.*;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.inicu.models.RefTestslist;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.NotesDAO;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.CalendarUtility;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.RemotedbConnector;

@Repository
public class LabReceiver extends Thread {

	@Autowired
	PatientDao patientDao;

	@Autowired
	PatientService patientService;

	@Autowired
	TestsService testsService;

	@Autowired
	InicuDao inicuDoa;

	private static final Logger logger = LoggerFactory.getLogger(LabReceiver.class);
	
	@Override
	public void run(){
		boolean running = true;

		while(running){
			RemotedbConnector connector = null;
			try {
				connector = new RemotedbConnector(true);
			} catch (Exception e1) {
				System.out.println("Unable to create database connector ");
				e1.printStackTrace();
			}
			try {
					
				String queryCurrentTestsList = "SELECT obj.testid from " + BasicConstants.SCHEMA_NAME + ""+ ".ref_testslist obj where isactive='t' ";
				List<String>activeTestsList = null;
				activeTestsList = patientDao.getListFromNativeQuery(queryCurrentTestsList);
				/*String queryNewTestsCount = "SELECT count(distinct("
						+ HISConstants.TEST_UNIQUE_ID + ") from " + BasicConstants.REMOTE_TESTS_NAME + " where " + HISConstants.TEST_UNIQUE_ID
						+ " not in "
						+ " ( '" + String.join("' , '", activeTestsList) +"' );";*/
				
				try {
					//ResultSet newTestsCountRS = connector.executePostgresSelectQuery(queryNewTestsCount);
					//if(newTestsCountRS.next()){
						//int newTestsCount = newTestsCountRS.getInt(1);
						//newTestsCountRS.close();
						//if(newTestsCount>0){
							//fetch new baby data from remote DB
							String fieldsList = "";
							String queryNewTests = "";
							if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
								queryNewTests = "SELECT CTTCS_TestSet_DR TestSet, CTTCS_TestSet_DR->CTTS_Name TestSet_Name,CTTCS_ParRef TestCode, CTTCS_ParRef->CTTC_Desc TestCode_Name,CTTCS_ParRef->CTTC_Units Units,CTTCS_RowId,CTTCS_TestSet_DR->CTTS_Word_MS_Result WordReport FROM LAB.CT_TestCodeTestSet where CTTCS_TestSet_DR->CTTS_Word_MS_Result='N' and CTTCS_TestSet_DR->CTTS_ActiveFlag='Y' and CTTCS_ParRef->CTTC_ActiveFlag='Y' order by CTTCS_TestSet_DR,TestCode";

							}else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")){
								queryNewTests = "SELECT * " + " from " + BasicConstants.REMOTE_TESTS_NAME + " where " + HISConstants.TEST_UNIQUE_ID+" not in "
										+ " ( '" + String.join("' , '", activeTestsList) +"' ) and UnitID = 2;";
							}else {
								queryNewTests = "SELECT * " + " from " + BasicConstants.REMOTE_TESTS_NAME + " where " + HISConstants.TEST_UNIQUE_ID+" not in "
										+ " ( '" + String.join("' , '", activeTestsList) +"' );";
							}
							ResultSet currentTestsData = connector.executePostgresSelectQuery(queryNewTests);
							insertTests(currentTestsData);
					}
				catch (Exception e) {
						e.printStackTrace();
				}
				

				String queryCurrentUhidsList = "SELECT uhid from baby_detail where admissionstatus='t' ";
				List<String>activeUhidsList = patientDao.getListFromNativeQuery(queryCurrentUhidsList);
				String queryUhidsMaxIds = "";
				// get all uhids currently, and max labresultid for those whose lab data exists
				
				if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
					queryUhidsMaxIds = "select b.uhid,max(t."+HISConstants.LAB_FETCH_CRITERIA_INICU+") from " + BasicConstants.SCHEMA_NAME+".baby_detail as b  left join "+BasicConstants.SCHEMA_NAME+".order_test as t " + 
							"on b.uhid = t.uhid " + " where b.admissionstatus='t' group by b.uhid";
				}else{
					queryUhidsMaxIds = "select b.uhid,max(t."+HISConstants.LAB_FETCH_CRITERIA_INICU+") from " + BasicConstants.SCHEMA_NAME+".baby_detail as b  left join "+BasicConstants.SCHEMA_NAME+".test_result as t " +
							"on b.uhid = t.prn " + " where b.admissionstatus='t' group by b.uhid";
				}

				String queryUhidsMaxIdHisto = "";
				String queryUhidsMaxIdMicro = "";
				String queryUhidsMaxIdText = "";
				ResultSet resultSet = null;
				String sampleId = "";
				Map<String, String> bloodGasMap = new HashMap<>();

				if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")) {
					if(!BasicUtils.isEmpty(HISConstants.LAB_ORDER_ID)) {
						try{
							String queryBloodGas = "SELECT obj from DeviceBloodgasDetailDetail as obj where isProcessed = 'false'";
							List<DeviceBloodgasDetailDetail> deviceBloodgasDetailDetailList = patientDao.getListFromMappedObjNativeQuery(queryBloodGas);

							if (!BasicUtils.isEmpty(deviceBloodgasDetailDetailList)) {

								String sampleIdString = "";
								for (DeviceBloodgasDetailDetail d : deviceBloodgasDetailDetailList) {
									sampleIdString += d.getSampleId() + ",";
									bloodGasMap.put(d.getSampleId(), d.getHl7Message());
								}
								sampleIdString = sampleIdString.substring(0, sampleIdString.lastIndexOf(","));
								sampleIdString = sampleIdString.replace(",", "','");

								String viewQuery = "SELECT * from " + BasicConstants.REMOTE_LABVIEW_NAME + " where "
										+ HISConstants.LAB_ORDER_ID + " in ('" + sampleIdString + "') ";
								try {
									resultSet = connector.executePostgresSelectQuery(viewQuery);
									if(resultSet != null)
									{
										while(resultSet.next())
										{
											sampleId = resultSet.getString(HISConstants.LAB_ORDER_ID);
											logger.info("Order No from VW_NicuPatientsOrders_VM6 view:" + sampleId);

											setBloodGasInfo(bloodGasMap.get(sampleId), sampleId);
										}
									}
								} catch (Exception e) {
									logger.error("Exception while fetching data from view", e);
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Exception while saving blood gas info into nursing_bloodgas table from view", e);
						}
					}
				}

				List<Object[]> listUhidsMaxIds = patientDao.getListFromNativeQuery(queryUhidsMaxIds);
				for (Object[] UhidMaxresultidObject : listUhidsMaxIds) {
					String uhid = (String) UhidMaxresultidObject[0];
					String maxTestResultid = "";
					if(UhidMaxresultidObject[1]!=null) {
						maxTestResultid = UhidMaxresultidObject[1].toString();
					}

					try {
						storeLabData(connector, uhid, maxTestResultid, BasicConstants.REMOTE_LABVIEW_NAME);
						if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")) {
							// get all uhids currently, and max labresultid for those whose histo data exists
							queryUhidsMaxIdHisto = "select max("+HISConstants.LAB_FETCH_CRITERIA_INICU+") from "+BasicConstants.SCHEMA_NAME+".test_result"
									+ " where view_type = '" + BasicConstants.REMOTE_LABVIEW_NAME_HISTO + "' and prn = '" + uhid + "'";
							List<Object[]> listUhidsMaxIdsHisto = patientDao.getListFromNativeQuery(queryUhidsMaxIdHisto);
							String maxTestResultidHisto = "";
							if(!BasicUtils.isEmpty(listUhidsMaxIdsHisto) && listUhidsMaxIdsHisto.get(0)[0] != null) {
								maxTestResultidHisto = listUhidsMaxIdsHisto.get(0)[0].toString();							
							}
							storeHistoData(connector, uhid, maxTestResultidHisto, BasicConstants.REMOTE_LABVIEW_NAME_HISTO);

							// get all uhids currently, and max labresultid for those whose micro data exists
							queryUhidsMaxIdMicro ="select max("+HISConstants.LAB_FETCH_CRITERIA_INICU+") from "+BasicConstants.SCHEMA_NAME+".test_result"
									+ " where view_type = '" + BasicConstants.REMOTE_LABVIEW_NAME_MICRO + "' and prn = '" + uhid + "'";
							List<Object[]> listUhidsMaxIdsMicro = patientDao.getListFromNativeQuery(queryUhidsMaxIdMicro);
							String maxTestResultidMicro = "";
							if(!BasicUtils.isEmpty(listUhidsMaxIdsMicro) && listUhidsMaxIdsMicro.get(0)[0] != null) {
								maxTestResultidMicro = listUhidsMaxIdsMicro.get(0)[0].toString();							
							}
							storeMicroData(connector, uhid, maxTestResultidMicro, BasicConstants.REMOTE_LABVIEW_NAME_MICRO);
							// get all uhids currently, and max labresultid for those whose text data exists
							queryUhidsMaxIdText ="select max("+HISConstants.LAB_FETCH_CRITERIA_INICU+") from "+BasicConstants.SCHEMA_NAME+".test_result"
									+ " where view_type = '" + BasicConstants.REMOTE_LABVIEW_NAME_TEXT + "' and prn = '" + uhid + "'";
							List<Object[]> listUhidsMaxIdsText = patientDao.getListFromNativeQuery(queryUhidsMaxIdText);
							String maxTestResultidText = "";
							if(!BasicUtils.isEmpty(listUhidsMaxIdsText) && listUhidsMaxIdsText.get(0)[0] != null) {
								maxTestResultidText = listUhidsMaxIdsText.get(0)[0].toString();							
							}
							storeTextData(connector, uhid, maxTestResultidText, BasicConstants.REMOTE_LABVIEW_NAME_TEXT);
						}
						if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
							List<OrderTest> ordersList = inicuDoa
									.getListFromMappedObjQuery("SELECT obj FROM OrderTest obj where reportReceived = 'false'");
							for(OrderTest order : ordersList) {
								ResultSet rs = null;

								String sampleid = order.getSampleid();
								String testCode = order.getTestid();
								
								String query = "Select VISTD_RowId, VISTD_TestCode_DR Test_Code ,VISTD_TestCode_DR->CTTC_Desc Test, VISTD_TestData result from lab.EP_VisitTestSetData  "
										+ " where VISTD_ParRef='" + sampleid + "||" + testCode + "||1" + "'";
								try {
									rs = connector.executePostgresSelectQuery(query);
									System.out.println("Resultset received for "+query);

								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								try {
									if(rs!=null)
									{
										while(rs.next())
										{	
											TestItemResult tirObject = new TestItemResult();
											tirObject.setPrn(order.getUhid());
											tirObject.setTestid(testCode);
											
											List<BabyDetail> babyDetailList = inicuDoa
													.getListFromMappedObjQuery("SELECT obj FROM BabyDetail obj where uhid = '" + order.getUhid() + "'");
											BabyDetail baby = babyDetailList.get(0);
											Date currentDate = new Date();
											long dol = -1;
											if(!BasicUtils.isEmpty(baby.getDateofbirth().getTime())) {
												dol = ((currentDate.getTime() - baby.getDateofbirth().getTime()) / (1000 * 60 * 60 * 24));
											}
											if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMID)){
												tirObject.setItemid(rs.getString(HISConstants.LAB_ITEMID));	
												List<TestItemHelp> itemsList = inicuDoa
														.getListFromMappedObjQuery("SELECT obj FROM TestItemHelp obj where itemid = '" + rs.getString(HISConstants.LAB_ITEMID) +"'");
												if(!BasicUtils.isEmpty(itemsList) && !BasicUtils.isEmpty(itemsList.get(0).getItemunit()))
													tirObject.setItemunit(itemsList.get(0).getItemunit());
//												if(dol >= 0) {
//													String gender = baby.getGender();
//													if(baby.getGender().equalsIgnoreCase("Male")){
//														gender = "M";
//													}else if(baby.getGender().equalsIgnoreCase("Female")){
//														gender = "F";
//													}
//													String ageLimit = "";
//													if(dol >= 0 && dol < 1) {
//														ageLimit = "0.000-0.000";
//													}else if(dol >= 1 && dol < 2) {
//														ageLimit = "0.001-0.001";
//													}else if(dol >= 2 && dol < 3) {
//														ageLimit = "0.002-0.002";
//													}else if(dol >= 3 && dol < 4) {
//														ageLimit = "0.003-0.003";
//													}else if(dol >= 4 && dol < 5) {
//														ageLimit = "0.004-0.004";
//													}else if(dol >= 5 && dol < 6) {
//														ageLimit = "0.005-0.005";
//													}else if(dol >= 6 && dol < 10) {
//														ageLimit = "0.006-0.010";
//													}else if(dol >= 10 && dol < 15) {
//														ageLimit = "0.011-0.015";
//													}else if(dol >= 16 && dol < 10000) {
//														ageLimit = "0.016-1";
//													}
//													ResultSet rs1 = null;
//													String rangeQuery =  "select CTTCR_ParRef,CTTCR_RowId,CTTCR_Date,CTTCR_Species_DR,CTTCR_Age,CTTCR_LowRange,CTTCR_HighRange  from lab.CT_TestCodeRanges where  CTTCR_ParRef ='" + rs.getString(HISConstants.LAB_ITEMID) +  "' and CTTCR_Date = (select max(CTTCR_Date) from lab.CT_TestCodeRanges where CTTCR_Age = '" + ageLimit + "' and  CTTCR_Species_DR = '" + gender + "' and CTTCR_ParRef ='" + rs.getString(HISConstants.LAB_ITEMID) + "'' )"; 
//													try {
//														
//														rs1 = connector.executePostgresSelectQuery(rangeQuery);
//														System.out.println("Range received for "+rangeQuery);
//
//													} catch (SQLException e1) {
//														// TODO Auto-generated catch block
//														e1.printStackTrace();
//													}
//
//													try {
//														if(rs1!=null)
//														{
//															while(rs1.next())
//															{	
//																if(!BasicUtils.isEmpty(HISConstants.LAB_ITEM_NORMALRANGE) && !BasicUtils.isEmpty(HISConstants.LAB_ITEM_NORMALRANGE_UPPER) && !BasicUtils.isEmpty(rs1.getString(HISConstants.LAB_ITEM_NORMALRANGE))){
//																	String range = (rs1.getString(HISConstants.LAB_ITEM_NORMALRANGE));	
//																	if(!BasicUtils.isEmpty(rs1.getString(HISConstants.LAB_ITEM_NORMALRANGE_UPPER))) {
//																		range = "-" + rs1.getString(HISConstants.LAB_ITEM_NORMALRANGE_UPPER);
//																	}
//																	tirObject.setNormalrange(range);
//																}
//															}
//														}
//													}catch (SQLException e1) {
//														// TODO Auto-generated catch block
//														e1.printStackTrace();
//													}finally {
//														if(rs1!=null)
//															try {
//																rs1.close();
//															} catch (SQLException e) {
//																e.printStackTrace();
//															}
//													}	
//												}
											}
											if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMNAME)){
												tirObject.setItemname(rs.getString(HISConstants.LAB_ITEMNAME));							
											}
											if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMVALUE)){
												tirObject.setItemvalue(rs.getString(HISConstants.LAB_ITEMVALUE));
											}
											
											if(!BasicUtils.isEmpty(order.getDepartmentname())){
												tirObject.setLab_center_code(order.getDepartmentname());
											}

											if(!BasicUtils.isEmpty(order.getLabCreatedDate())){
												tirObject.setLabCreatedDate(order.getLabCreatedDate());
											}
											if(!BasicUtils.isEmpty(order.getLabUpdatedDate())){
												tirObject.setLabUpdatedDate(order.getLabUpdatedDate());
											}
											if(!BasicUtils.isEmpty(order.getLabCollectionDate())){
												tirObject.setLabCollectionDate(order.getLabCollectionDate());
											}
											if(!BasicUtils.isEmpty(order.getLabApprovedDate())){
												tirObject.setLabApprovedDate(order.getLabApprovedDate()); 
											}
											if(!BasicUtils.isEmpty(order.getLabReportDate())){
												tirObject.setLabReportDate(order.getLabReportDate()); //using this for showing on screen
												tirObject.setResultdate(new Date(order.getLabReportDate().getTime()));
											}
											if(!BasicUtils.isEmpty(order.getLabTestName())){
												tirObject.setLabTestName(order.getLabTestName());
											}
											
											
											
//											if(!BasicUtils.isEmpty(HISConstants.LAB_ITEM_NORMALRANGE)){
//												tirObject.setNormalrange(rs.getString(HISConstants.LAB_ITEM_NORMALRANGE)); 
//											}
											if(!BasicUtils.isEmpty(order.getSampleid())){
												tirObject.setRegno(order.getSampleid());
											}
											
											if(!BasicUtils.isEmpty(order.getBabyname())){
												tirObject.setBabyname(order.getBabyname()); 
											}
											if(!BasicUtils.isEmpty(order.getGender())){
												tirObject.setGender(order.getGender()); 
											}
											if(!BasicUtils.isEmpty(order.getAge())){
												tirObject.setAge(order.getAge()); 
											}
											tirObject.setViewType("patient_view");

											System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Lab record to be saved " + tirObject.toString());
											patientDao.saveObject(tirObject);
										}
										order.setReportReceived(true);
										patientDao.saveObject(order);
									}
									
								}catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}finally {
									if(rs!=null)
										try {
											rs.close();
										} catch (SQLException e) {
											e.printStackTrace();
										}
								}	
								
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Thread.sleep(BasicConstants.REMOTE_THREAD_FREQUENCY);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(connector!=null)
					try {
						connector.closeConnection();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
			}
		} //while end

	}

	private void storeLabData(RemotedbConnector connector, String uhid, String maxResultId, String viewType) {
		//retrieve and store lab results into iNICU db 
		ResultSet rs = null;
		
		HashMap<String,String> testsListMapped = new HashMap<String,String>(); 
		testsListMapped = testsService.getTestListId();
		//System.out.println("Tests List in database " + testsListMapped.toString());
		//condition for datetime >=
		if(uhid!=null && !uhid.isEmpty()){
			String query = "";
			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
				List<BabyDetail> babyDetailList = inicuDoa
						.getListFromMappedObjQuery("SELECT obj FROM BabyDetail obj where uhid = '" + uhid + "' and admissionstatus = 'true'");
				BabyDetail baby = babyDetailList.get(0);
				query = "select VISTS_ParRef->EPVIS_VisitNumber as SampleID,\r\n" + 
						"VISTS_TestSet_DR->CTTS_Code TestSet_Code,\r\n" + 
						"VISTS_TestSet_DR->CTTS_Name Test_Name,\r\n" + 
						"VISTS_TestSet_DR->CTTS_Department_DR->CTDEP_Name,\r\n" + 
						"VISTS_ParRef->EPVIS_Hospital_EpisodeUR_Number Episode_No,\r\n" + 
						"VISTS_ParRef->EPVIS_BloodGroup_DR->BBBG_Description BloodGroup,\r\n" + 
						"VISTS_ParRef->EPVIS_GivenName Patient_Name,\r\n" + 
						"VISTS_ParRef->EPVIS_Surname Patient_Surname,\r\n" + 
						"VISTS_ParRef->EPVIS_Species_DR->CTSP_Desc Gender,\r\n" + 
						"VISTS_ParRef->EPVIS_Age Age,\r\n" + 
						"VISTS_ParRef->EPVIS_PriorityCode_DR->CTPR_Desc Priority,\r\n" + 
						"VISTS_ParRef->EPVIS_UserSite_DR->CTUSL_Desc Order_Location,\r\n" + 
						"VISTS_ParRef->EPVIS_MTOEOrdItemDR->OEORI_TimeOrd Order_Time ,\r\n" + 
						"VISTS_ParRef->EPVIS_MTOEOrdItemDR->OEORI_Date Order_Date,\r\n" + 
						"VISTS_DateOfCollection Collection_Date,\r\n" + 
						"Cast((VISTS_TimeOfCollection*1) AS Time) Collection_Time,\r\n" + 
						"VISTS_DateOfReceive Receiving_Date,\r\n" + 
						"Cast((VISTS_TimeOfReceive*1) AS Time) Receiving_Time,\r\n" + 
						"VISTS_DateOfEntry Entry_Date ,\r\n" + 
						"CAST((VISTS_TimeOfEntry*60) AS TIME) Entry_Time,\r\n" + 
						"VISTS_DateOfAuthorisation Authorisation_Date,\r\n" + 
						"CAST((VISTS_TimeOfAuthorisation*60) AS TIME) Authorisation_Time,\r\n" + 
						"VISTS_StatusResult OrderCurrent_status from\r\n" + 
						"lab.EP_VisitTestSet where VISTS_ParRef->EPVIS_DateOfAdmission='" + baby.getDateofadmission() + "' and VISTS_ParRef->EPVIS_Hospital_EpisodeUR_Number='" + baby.getIpnumber() + "'";
				if(!BasicUtils.isEmpty(maxResultId)) {
					query += " and VISTS_DateOfReceive > '" + maxResultId+"'";
				}
			}
			else {
				query = "SELECT * from " + viewType + " where "
						+ HISConstants.LAB_UHID + "='"+uhid+"' ";

				if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")){

					// fetch the Order No From the test_result table for the current baby
					String queryOrderNo="select obj from "+BasicConstants.SCHEMA_NAME+".test_result as obj where uhid ='" + uhid + "'";
					List<TestItemResult> orderNoList = inicuDoa.getListFromMappedObjQuery(queryOrderNo);

					String OrderNo= null;
					for (TestItemResult testObject:orderNoList) {
						if(OrderNo !=null){
							OrderNo += ", '"+testObject.getOrderNo()+"'";
						}else{
							OrderNo +="'" + testObject.getOrderNo() + "'";
						}
					}

					if(!BasicUtils.isEmpty(maxResultId)){
						query += " and ( " + HISConstants.LAB_FETCH_CRITERIA + " > '" + maxResultId + "'";

						if(OrderNo != null) {
							query += " or " + HISConstants.LAB_ORDER_ID + " not in ( " + OrderNo + " ) )";
						}
					}
				}else {
					if (!BasicUtils.isEmpty(maxResultId)) {
						query += " and " + HISConstants.LAB_FETCH_CRITERIA + " > '" + maxResultId + "'";
					}
				}
			}

			try {
				rs = connector.executePostgresSelectQuery(query);
				System.out.println("Resultset received for "+query);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				if(rs!=null)
				{
					while(rs.next())
					{	
						if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
							
							
							OrderTest tirObject = new OrderTest();
	
							tirObject.setUhid(uhid);
							if(!BasicUtils.isEmpty(HISConstants.LAB_TESTID))
								tirObject.setTestid(rs.getString(HISConstants.LAB_TESTID));
							else{
								String testName = rs.getString(HISConstants.LAB_TESTNAME);
								if(testsListMapped.containsKey(testName)){
									tirObject.setTestid(testsListMapped.get(testName));
								}
							}
							
							if(!BasicUtils.isEmpty(HISConstants.LAB_TESTNAME)){
								tirObject.setLabTestName(rs.getString(HISConstants.LAB_TESTNAME));
							}						
							tirObject.setIpnumber(rs.getString(HISConstants.LAB_IP_NUMBER));
							if(!BasicUtils.isEmpty(HISConstants.LAB_RESULT_TYPE)){
								tirObject.setLabResultStatus(rs.getString(HISConstants.LAB_RESULT_TYPE)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_CENTER_CODE)){
								tirObject.setDepartmentname(rs.getString(HISConstants.LAB_CENTER_CODE)); 
							}
							if(!BasicUtils.isEmpty(rs.getString(HISConstants.LAB_CREATED_DATE)) && !BasicUtils.isEmpty(rs.getString(HISConstants.LAB_CREATED_TIME))){
								String entryDate = rs.getString(HISConstants.LAB_CREATED_DATE) + " " + rs.getString(HISConstants.LAB_CREATED_TIME);
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							    Date parsedDate = dateFormat.parse(entryDate);
							    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
								
								tirObject.setLabCreatedDate(new Timestamp(timestamp.getTime()));
							}
							if(!BasicUtils.isEmpty(rs.getString(HISConstants.LAB_COLLECTION_DATE)) && !BasicUtils.isEmpty(rs.getString(HISConstants.LAB_COLLECTION_TIME))){
								
								String entryDate = rs.getString(HISConstants.LAB_COLLECTION_DATE) + " " + rs.getString(HISConstants.LAB_COLLECTION_TIME);
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							    Date parsedDate = dateFormat.parse(entryDate);
							    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
								
								tirObject.setLabCollectionDate(timestamp);
							}
							if(!BasicUtils.isEmpty(rs.getString(HISConstants.LAB_REPORT_DATE)) && !BasicUtils.isEmpty(rs.getString(HISConstants.LAB_REPORT_TIME))){
								
								String entryDate = rs.getString(HISConstants.LAB_REPORT_DATE) + " " + rs.getString(HISConstants.LAB_REPORT_TIME);
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							    Date parsedDate = dateFormat.parse(entryDate);
							    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
								
								tirObject.setLabReportDate(timestamp); //using this for showing on screen
//								tirObject.setResultdate(new Date(rs.getTimestamp(HISConstants.LAB_REPORT_DATE).getTime()));

							}
							if(!BasicUtils.isEmpty(rs.getString(HISConstants.LAB_UPDATED_DATE)) && !BasicUtils.isEmpty(rs.getString(HISConstants.LAB_UPDATED_TIME))){
								String entryDate = rs.getString(HISConstants.LAB_UPDATED_DATE) + " " + rs.getString(HISConstants.LAB_UPDATED_TIME);
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							    Date parsedDate = dateFormat.parse(entryDate);
							    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
								tirObject.setLabUpdatedDate(timestamp);
							}
							if(!BasicUtils.isEmpty(rs.getString(HISConstants.LAB_APPROVED_DATE)) && !BasicUtils.isEmpty(rs.getString(HISConstants.LAB_APPROVED_TIME))){
								String entryDate = rs.getString(HISConstants.LAB_APPROVED_DATE) + " " + rs.getString(HISConstants.LAB_APPROVED_TIME);
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							    Date parsedDate = dateFormat.parse(entryDate);
							    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
								tirObject.setLabApprovedDate(timestamp); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_PName)){
								tirObject.setBabyname(rs.getString(HISConstants.LAB_PName)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_Gender)){
								tirObject.setGender(rs.getString(HISConstants.LAB_Gender)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_Age)){
								tirObject.setAge(rs.getString(HISConstants.LAB_Age)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_SAMPLE_ID)){
								tirObject.setSampleid(rs.getString(HISConstants.LAB_SAMPLE_ID)); 
							}
							tirObject.setReportReceived(false);
							System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Order record to be saved " + tirObject.toString());
							patientDao.saveObject(tirObject);
						}
						else {
							TestItemResult tirObject = new TestItemResult();
							
							tirObject.setPrn(rs.getString(HISConstants.LAB_UHID));
							if(!BasicUtils.isEmpty(HISConstants.LAB_TESTID))
								tirObject.setTestid(rs.getString(HISConstants.LAB_TESTID));
							else{
								String testName = rs.getString(HISConstants.LAB_TESTNAME);
								if(testsListMapped.containsKey(testName)){
									tirObject.setTestid(testsListMapped.get(testName));
								}
							}
							
							if(BasicConstants.isHISData){
								if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMID)){
									if(rs.getInt(HISConstants.LAB_ITEMID)!=0)
										tirObject.setItemid(Integer.toString(rs.getInt(HISConstants.LAB_ITEMID)));
								}
							}
							else{
								if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMID)){
									tirObject.setItemid(rs.getString(HISConstants.LAB_ITEMID));							
								}
							}
	
							if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMNAME)){
								tirObject.setItemname(rs.getString(HISConstants.LAB_ITEMNAME));							
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMVALUE)){
								tirObject.setItemvalue(rs.getString(HISConstants.LAB_ITEMVALUE));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_UPDATED_BY)){
								tirObject.setLabUpdatedBy(rs.getString(HISConstants.LAB_UPDATED_BY));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_RESULT_STATUS)){
								tirObject.setLabResultStatus(rs.getString(HISConstants.LAB_RESULT_STATUS)); //Normal, Abnormal
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_CREATED_DATE)){
								tirObject.setLabCreatedDate(rs.getTimestamp(HISConstants.LAB_CREATED_DATE));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_UPDATED_DATE)){
								tirObject.setLabUpdatedDate(rs.getTimestamp(HISConstants.LAB_UPDATED_DATE));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_COLLECTION_DATE)){
								tirObject.setLabCollectionDate(rs.getDate(HISConstants.LAB_COLLECTION_DATE));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_POSTED_DATE)){
								tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_POSTED_DATE)); //with timezone field, only this field is with timezone
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
								tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_REPORT_DATE)); //using this for showing on screen
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_TEST_DETAILS_ID)){
								tirObject.setLabTestResultId(rs.getLong(HISConstants.LAB_TEST_DETAILS_ID));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_TESTNAME)){
								tirObject.setLabTestName(rs.getString(HISConstants.LAB_TESTNAME));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMUNIT)){
								tirObject.setItemunit(rs.getString(HISConstants.LAB_ITEMUNIT));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
								tirObject.setResultdate(new Date(rs.getTimestamp(HISConstants.LAB_REPORT_DATE).getTime()));
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_ITEM_NORMALRANGE)){
								tirObject.setNormalrange(rs.getString(HISConstants.LAB_ITEM_NORMALRANGE)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_APPROVED_DATE)){
								tirObject.setLabApprovedDate(rs.getTimestamp(HISConstants.LAB_APPROVED_DATE)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_CENTER_CODE)){
								tirObject.setLab_center_code(rs.getString(HISConstants.LAB_CENTER_CODE)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_RESULT_TYPE)){
								tirObject.setResulttype(rs.getString(HISConstants.LAB_RESULT_TYPE)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_URL)){
								tirObject.setReporturl(rs.getString(HISConstants.LAB_REPORT_URL)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_SAMPLE_TYPE)){
								tirObject.setSampletype(rs.getString(HISConstants.LAB_SAMPLE_TYPE)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_PName)){
								tirObject.setBabyname(rs.getString(HISConstants.LAB_PName)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_Gender)){
								tirObject.setGender(rs.getString(HISConstants.LAB_Gender)); 
							}
							if(!BasicUtils.isEmpty(HISConstants.LAB_Age)){
								tirObject.setAge(rs.getString(HISConstants.LAB_Age)); 
							}
							tirObject.setViewType(viewType);


							// set the value of the template Xml or Template Html for the Radiology reports

							// XML format
							if(!BasicUtils.isEmpty(HISConstants.TEMPLATE_XML) && !BasicUtils.isEmpty(rs.getString(HISConstants.TEMPLATE_XML))){
								tirObject.setTemplateXml(rs.getString(HISConstants.TEMPLATE_XML));
							}

							// HTML Format
							if(!BasicUtils.isEmpty(HISConstants.TEMPLATE_HTML) && !BasicUtils.isEmpty(rs.getString(HISConstants.TEMPLATE_HTML))){
								tirObject.setTemplateHtml(rs.getString(HISConstants.TEMPLATE_HTML));
							}

							// Store the  OrderNo from the View to test_result table
							if(!BasicUtils.isEmpty(HISConstants.LAB_ORDER_ID) && !BasicUtils.isEmpty(rs.getString(HISConstants.LAB_ORDER_ID))){
								tirObject.setOrderNo(rs.getString(HISConstants.LAB_ORDER_ID));
							}

							/*
							tirObject.setLabResultStatus(rs.getString("result_status")); //Normal, Abnormal
							tirObject.setLabCreatedDate(rs.getTimestamp("created_date"));
							tirObject.setLabUpdatedBy(rs.getString("updated_by"));
							tirObject.setLabUpdatedDate(rs.getTimestamp("updated_date"));
							tirObject.setLabCollectionDate(rs.getDate("collection_date"));
	
							tirObject.setLabPostedDate(rs.getTimestamp("posted_date")); //with timezone field, only this field is with timezone
							tirObject.setLabReportDate(rs.getTimestamp("report_date")); //using this for showing on screen
							tirObject.setLabTestResultId(rs.getLong("test_details_id"));
							tirObject.setLabTestName(rs.getString("service_name"));
							tirObject.setItemunit(rs.getString("unit"));
							tirObject.setResultdate(new Date(rs.getTimestamp("report_date").getTime()));
							 */
							System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Lab record to be saved " + tirObject.toString());
							patientDao.saveObject(tirObject);
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}				
		}
	}
	
	private void storeHistoData(RemotedbConnector connector, String uhid, String maxResultId, String viewType) {
		//retrieve and store lab results into iNICU db 
		ResultSet rs = null;
		
		HashMap<String,String> testsListMapped = new HashMap<String,String>(); 
		testsListMapped = testsService.getTestListId();
		//System.out.println("Tests List in database " + testsListMapped.toString());
		//condition for datetime >=
		if(uhid!=null && !uhid.isEmpty()){
			
			String query = "SELECT * from " + viewType + " where "
					+ HISConstants.LAB_UHID + "='"+uhid+"' ";
			if(!BasicUtils.isEmpty(maxResultId)) {
				query += " and " + HISConstants.LAB_FETCH_CRITERIA + " > '" + maxResultId+"'";
			}

			try {
				rs = connector.executePostgresSelectQuery(query);
				System.out.println("Resultset Histo received for "+query);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				if(rs!=null)
				{
					while(rs.next())
					{	
						TestItemResult tirObject = new TestItemResult();

						tirObject.setPrn(rs.getString(HISConstants.LAB_UHID));
						if(!BasicUtils.isEmpty(HISConstants.LAB_TESTID))
							tirObject.setTestid(rs.getString(HISConstants.LAB_TESTID));
						else{
							String testName = rs.getString(HISConstants.LAB_TESTNAME);
							if(testsListMapped.containsKey(testName)){
								tirObject.setTestid(testsListMapped.get(testName));
							}
						}
						
						if(!BasicUtils.isEmpty(HISConstants.LAB_CENTER_CODE)){
							tirObject.setLab_center_code(rs.getString(HISConstants.LAB_CENTER_CODE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_COLLECTION_DATE)){
							tirObject.setLabCollectionDate(rs.getDate(HISConstants.LAB_COLLECTION_DATE));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_POSTED_DATE)){
							tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_POSTED_DATE)); //with timezone field, only this field is with timezone
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
							tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_REPORT_DATE)); //using this for showing on screen
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_RESULT_TYPE)){
							tirObject.setResulttype(rs.getString(HISConstants.LAB_RESULT_TYPE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_TEST_DETAILS_ID)){
							tirObject.setLabTestResultId(rs.getLong(HISConstants.LAB_TEST_DETAILS_ID));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_UPDATED_BY)){
							tirObject.setLabUpdatedBy(rs.getString(HISConstants.LAB_UPDATED_BY));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_URL)){
							tirObject.setReporturl(rs.getString(HISConstants.LAB_REPORT_URL)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_SAMPLE_TYPE)){
							tirObject.setSampletype(rs.getString(HISConstants.LAB_SAMPLE_TYPE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_TESTNAME)){
							tirObject.setLabTestName(rs.getString(HISConstants.LAB_TESTNAME));
						}
						tirObject.setViewType(viewType);
						
						if(!BasicUtils.isEmpty(HISConstants.LAB_DETAIL_HISTO)){
							tirObject.setDetail(rs.getString(HISConstants.LAB_DETAIL_HISTO));							
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_GROSS_HISTO)){
							tirObject.setGross(rs.getString(HISConstants.LAB_GROSS_HISTO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_MICROSCOPIC_HISTO)){
							tirObject.setMicroscopic(rs.getString(HISConstants.LAB_MICROSCOPIC_HISTO)); //Normal, Abnormal
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_IMPRESSION_HISTO)){
							tirObject.setImpression(rs.getString(HISConstants.LAB_IMPRESSION_HISTO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_ADVICE_HISTO)){
							tirObject.setAdvice(rs.getString(HISConstants.LAB_ADVICE_HISTO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
							tirObject.setResultdate(new Date(rs.getTimestamp(HISConstants.LAB_REPORT_DATE).getTime()));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_CLINICALHISTORY_HISTO)){
							tirObject.setClinicalHistory(rs.getString(HISConstants.LAB_CLINICALHISTORY_HISTO)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_PName)){
							tirObject.setBabyname(rs.getString(HISConstants.LAB_PName)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_Gender)){
							tirObject.setGender(rs.getString(HISConstants.LAB_Gender)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_Age)){
							tirObject.setAge(rs.getString(HISConstants.LAB_Age)); 
						}
						System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Histo record to be saved " + tirObject.toString());
						patientDao.saveObject(tirObject);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}				
		}
	}
	
	private void storeMicroData(RemotedbConnector connector, String uhid, String maxResultId, String viewType) {
		//retrieve and store lab results into iNICU db 
		ResultSet rs = null;
		
		HashMap<String,String> testsListMapped = new HashMap<String,String>(); 
		testsListMapped = testsService.getTestListId();
		//System.out.println("Tests List in database " + testsListMapped.toString());
		//condition for datetime >=
		if(uhid!=null && !uhid.isEmpty()){
			
			String query = "SELECT * from " + viewType + " where "
					+ HISConstants.LAB_UHID + "='"+uhid+"' ";
			if(!BasicUtils.isEmpty(maxResultId)) {
				query += " and " + HISConstants.LAB_FETCH_CRITERIA + " > '" + maxResultId+"'";
			}

			try {
				rs = connector.executePostgresSelectQuery(query);
				System.out.println("Resultset Micro received for "+query);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				if(rs!=null)
				{
					while(rs.next())
					{	
						TestItemResult tirObject = new TestItemResult();

						tirObject.setPrn(rs.getString(HISConstants.LAB_UHID));
						if(!BasicUtils.isEmpty(HISConstants.LAB_TESTID))
							tirObject.setTestid(rs.getString(HISConstants.LAB_TESTID));
						else{
							String testName = rs.getString(HISConstants.LAB_TESTNAME);
							if(testsListMapped.containsKey(testName)){
								tirObject.setTestid(testsListMapped.get(testName));
							}
						}
						
						if(!BasicUtils.isEmpty(HISConstants.LAB_CENTER_CODE)){
							tirObject.setLab_center_code(rs.getString(HISConstants.LAB_CENTER_CODE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_COLLECTION_DATE)){
							tirObject.setLabCollectionDate(rs.getDate(HISConstants.LAB_COLLECTION_DATE));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_POSTED_DATE)){
							tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_POSTED_DATE)); //with timezone field, only this field is with timezone
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
							tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_REPORT_DATE)); //using this for showing on screen
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
							tirObject.setResultdate(new Date(rs.getTimestamp(HISConstants.LAB_REPORT_DATE).getTime()));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_RESULT_TYPE)){
							tirObject.setResulttype(rs.getString(HISConstants.LAB_RESULT_TYPE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_TEST_DETAILS_ID)){
							tirObject.setLabTestResultId(rs.getLong(HISConstants.LAB_TEST_DETAILS_ID));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_UPDATED_BY)){
							tirObject.setLabUpdatedBy(rs.getString(HISConstants.LAB_UPDATED_BY));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_URL)){
							tirObject.setReporturl(rs.getString(HISConstants.LAB_REPORT_URL)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_SAMPLE_TYPE)){
							tirObject.setSampletype(rs.getString(HISConstants.LAB_SAMPLE_TYPE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_TESTNAME)){
							tirObject.setLabTestName(rs.getString(HISConstants.LAB_TESTNAME));
						}
						tirObject.setViewType(viewType);
						
						if(!BasicUtils.isEmpty(HISConstants.LAB_OBSERVATION_NAME_MICRO)){
							tirObject.setLabObservationName(rs.getString(HISConstants.LAB_OBSERVATION_NAME_MICRO));							
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_VALUE_MICRO)){
							String value = rs.getString(HISConstants.LAB_VALUE_MICRO);
							if(!BasicUtils.isEmpty(HISConstants.LAB_COMMENTS_MICRO)){
								value += " " + rs.getString(HISConstants.LAB_COMMENTS_MICRO);
							}
							tirObject.setValue(value);
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_UNIT_MICRO)){
							tirObject.setUnit(rs.getString(HISConstants.LAB_UNIT_MICRO)); //Normal, Abnormal
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_OBSERVATION_COMMENT_MICRO)){
							tirObject.setLabObservationComment(rs.getString(HISConstants.LAB_OBSERVATION_COMMENT_MICRO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_ORGANISM_NAME_DISPLAY_NAME_MICRO)){
							tirObject.setOrganismNameDisplayName(rs.getString(HISConstants.LAB_ORGANISM_NAME_DISPLAY_NAME_MICRO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_COLONY_COUNT_MICRO)){
							tirObject.setColonyCount(rs.getString(HISConstants.LAB_COLONY_COUNT_MICRO)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_COLONY_COUNT_COMMENT_MICRO)){
							tirObject.setColonyCountComment(rs.getString(HISConstants.LAB_COLONY_COUNT_COMMENT_MICRO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_ANTIBIOTIC_NAME_MICRO)){
							tirObject.setAntibioticName(rs.getString(HISConstants.LAB_ANTIBIOTIC_NAME_MICRO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_ANTIBIOTIC_INTERPREATATION_MICRO)){
							tirObject.setAntibioticInterpreatation(rs.getString(HISConstants.LAB_ANTIBIOTIC_INTERPREATATION_MICRO)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_MIC_MICRO)){
							tirObject.setMic(rs.getString(HISConstants.LAB_MIC_MICRO));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_TYPE_MICRO)){
							tirObject.setReportType(rs.getString(HISConstants.LAB_REPORT_TYPE_MICRO)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_PName)){
							tirObject.setBabyname(rs.getString(HISConstants.LAB_PName)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_Gender)){
							tirObject.setGender(rs.getString(HISConstants.LAB_Gender)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_Age)){
							tirObject.setAge(rs.getString(HISConstants.LAB_Age)); 
						}
						System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Micro record to be saved " + tirObject.toString());
						patientDao.saveObject(tirObject);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}				
		}
	}
	
	private void storeTextData(RemotedbConnector connector, String uhid, String maxResultId, String viewType) {
		//retrieve and store lab results into iNICU db 
		ResultSet rs = null;
		
		HashMap<String,String> testsListMapped = new HashMap<String,String>(); 
		testsListMapped = testsService.getTestListId();
		//System.out.println("Tests List in database " + testsListMapped.toString());
		//condition for datetime >=
		if(uhid!=null && !uhid.isEmpty()){
			
			String query = "SELECT * from " + viewType + " where "
					+ HISConstants.LAB_UHID + "='"+uhid+"' ";
			if(!BasicUtils.isEmpty(maxResultId)) {
				query += " and " + HISConstants.LAB_FETCH_CRITERIA + " > '" + maxResultId+"'";
			}

			try {
				rs = connector.executePostgresSelectQuery(query);
				System.out.println("Resultset Text received for "+query);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				if(rs!=null)
				{
					while(rs.next())
					{	
						TestItemResult tirObject = new TestItemResult();

						tirObject.setPrn(rs.getString(HISConstants.LAB_UHID));
						if(!BasicUtils.isEmpty(HISConstants.LAB_TESTID))
							tirObject.setTestid(rs.getString(HISConstants.LAB_TESTID));
						else{
							String testName = rs.getString(HISConstants.LAB_TESTNAME);
							if(testsListMapped.containsKey(testName)){
								tirObject.setTestid(testsListMapped.get(testName));
							}
						}
						
						if(!BasicUtils.isEmpty(HISConstants.LAB_CENTER_CODE)){
							tirObject.setLab_center_code(rs.getString(HISConstants.LAB_CENTER_CODE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_COLLECTION_DATE)){
							tirObject.setLabCollectionDate(rs.getDate(HISConstants.LAB_COLLECTION_DATE));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_POSTED_DATE)){
							tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_POSTED_DATE)); //with timezone field, only this field is with timezone
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
							tirObject.setLabReportDate(rs.getTimestamp(HISConstants.LAB_REPORT_DATE)); //using this for showing on screen
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_DATE)){
							tirObject.setResultdate(new Date(rs.getTimestamp(HISConstants.LAB_REPORT_DATE).getTime()));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_RESULT_TYPE)){
							tirObject.setResulttype(rs.getString(HISConstants.LAB_RESULT_TYPE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_TEST_DETAILS_ID)){
							tirObject.setLabTestResultId(rs.getLong(HISConstants.LAB_TEST_DETAILS_ID));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_UPDATED_BY)){
							tirObject.setLabUpdatedBy(rs.getString(HISConstants.LAB_UPDATED_BY));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_REPORT_URL)){
							tirObject.setReporturl(rs.getString(HISConstants.LAB_REPORT_URL)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_SAMPLE_TYPE)){
							tirObject.setSampletype(rs.getString(HISConstants.LAB_SAMPLE_TYPE)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_TESTNAME)){
							tirObject.setLabTestName(rs.getString(HISConstants.LAB_TESTNAME));
						}
						tirObject.setViewType(viewType);
						
						if(!BasicUtils.isEmpty(HISConstants.LAB_DISPLAY_READING_TEXT)){
							tirObject.setDisplayReading(rs.getString(HISConstants.LAB_DISPLAY_READING_TEXT));							
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_ITEMVALUE)){
							tirObject.setItemvalue(rs.getString(HISConstants.LAB_ITEMVALUE));
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_PName)){
							tirObject.setBabyname(rs.getString(HISConstants.LAB_PName)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_Gender)){
							tirObject.setGender(rs.getString(HISConstants.LAB_Gender)); 
						}
						if(!BasicUtils.isEmpty(HISConstants.LAB_Age)){
							tirObject.setAge(rs.getString(HISConstants.LAB_Age)); 
						}
						System.out.println("[" + BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: Text record to be saved " + tirObject.toString());
						patientDao.saveObject(tirObject);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}				
		}
	}

	private void insertTests(ResultSet currentTestsData) throws Exception {
		System.out.println("Insert Tests initiated ");
		
		try{
			if(currentTestsData!=null)
			{
				while(currentTestsData.next())
				{	
					
					RefTestslist testObj = new RefTestslist();
					TestItemHelp itemObj = new TestItemHelp();
					
					String queryTestsList = "SELECT obj.testid from " + BasicConstants.SCHEMA_NAME + ""+ ".ref_testslist obj where isactive='t' and testid= '" + currentTestsData.getString(HISConstants.TEST_UNIQUE_ID) + "'";
					List<String>testsList = null;
					testsList = patientDao.getListFromNativeQuery(queryTestsList);
					
					if(!BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
						testsList = null;
					}
					
					if(BasicUtils.isEmpty(testsList)) {
					
						if(!BasicUtils.isEmpty(HISConstants.TEST_UNIQUE_ID)){
							testObj.setTestid(currentTestsData.getString(HISConstants.TEST_UNIQUE_ID));							
						}
						if(!BasicUtils.isEmpty(HISConstants.TEST_UNIQUE_NAME)){
							
							if(!BasicUtils.isEmpty(HISConstants.TEST_UNIQUE_CODE)){
								testObj.setTestcode(currentTestsData.getString(HISConstants.TEST_UNIQUE_CODE));	
							}
							else{
								testObj.setTestcode(currentTestsData.getString(HISConstants.TEST_UNIQUE_NAME));	
							}
							testObj.setTestname(currentTestsData.getString(HISConstants.TEST_UNIQUE_NAME));

							// set the order category of the report
							if(!BasicUtils.isEmpty(HISConstants.TEST_ORDER_CATEGORY)){
								testObj.setOrderCategory(currentTestsData.getString(HISConstants.TEST_ORDER_CATEGORY));
							}else{
								// default type LAB
								testObj.setOrderCategory("LAB");
							}

							testObj.setIsactive(true);	
							System.out.println("Lab record to be saved " + testObj.toString());
							patientDao.saveObject(testObj);
	
						}
					}
					
					if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")) {
						
						String queryItemsList = "SELECT obj.itemid from " + BasicConstants.SCHEMA_NAME + ""+ ".ref_testitemhelp obj where itemid= '" + currentTestsData.getString(HISConstants.REF_LAB_ITEM_ID) + "'";
						List<String>itemsList = null;
						itemsList = patientDao.getListFromNativeQuery(queryItemsList);
						
						if(BasicUtils.isEmpty(itemsList)) {
							itemObj.setItemid((currentTestsData.getString(HISConstants.REF_LAB_ITEM_ID)));	

							itemObj.setTestcode(currentTestsData.getString(HISConstants.TEST_UNIQUE_NAME));	
							//item id
							itemObj.setTestid((currentTestsData.getString(HISConstants.TEST_UNIQUE_CODE)));	
							itemObj.setItemname((currentTestsData.getString(HISConstants.REF_LAB_ITEM_NAME)));
							itemObj.setItemunit((currentTestsData.getString(HISConstants.LAB_ITEMUNIT)));
							System.out.println("Item record to be saved " + testObj.toString());
							patientDao.saveObject(itemObj);
						}
					}
			
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setBloodGasInfo(String hl7Message, String sampleId) throws Exception {
		if (!BasicUtils.isEmpty(hl7Message)) {
			NursingBloodGas bloodgas = new NursingBloodGas();
			String dateStr = "";
			String dateStrTemp = "";

			String[] strArr = hl7Message.split("\\|");
			for (int j = 0; j < strArr.length; j++) {
				String str = strArr[j];
				if (strArr[j].contains("OBR") && strArr[j + 1].contains("1")) {
					System.out.println(strArr[j + 4] + " Method");
					bloodgas.setSampleMethod(strArr[j + 4]);
				}
				if (strArr[j].contains("pH^M")) {
					System.out.println(strArr[j + 2] + " pH");
					bloodgas.setPh(strArr[j + 2]);
				}
				if (strArr[j].contains("pCO2^M")) {
					System.out.println(strArr[j + 2] + " pCO2");
					bloodgas.setPco2(strArr[j + 2]);
				}
				if (strArr[j].contains("pO2^M")) {
					System.out.println(strArr[j + 2] + " pO2");
					bloodgas.setPo2(strArr[j + 2]);
				}
				if (strArr[j].contains("Na+")) {
					System.out.println(strArr[j + 2] + " Na+");
					bloodgas.setNa(strArr[j + 2]);
				}
				if (strArr[j].contains("Glu")) {
					System.out.println(strArr[j + 2] + " Glu");
					bloodgas.setGlucose(strArr[j + 2]);
				}
				if (strArr[j].contains("K+^M")) {
					System.out.println(strArr[j + 2] + " K+");
					bloodgas.setK(strArr[j + 2]);
				}
				if (strArr[j].contains("Cl-")) {
					System.out.println(strArr[j + 2] + " Cl-");
					bloodgas.setCl(strArr[j + 2]);
				}
				if (strArr[j].contains("Ca++")) {
					System.out.println(strArr[j + 2] + " Ca++");
					bloodgas.setIonized_calcium(strArr[j + 2]);
				}
				if (strArr[j].contains("Lac")) {
					System.out.println(strArr[j + 2] + " Lac");
					bloodgas.setLactate(strArr[j + 2]);
				}
				if (strArr[j].contains("gap^C")) {
					System.out.println(strArr[j + 2] + " gap^C");
					bloodgas.setAnionGap(strArr[j + 2]);
				}
				if (strArr[j].contains("SBE^C")) {
					System.out.println(strArr[j + 2] + " BE^C");
					bloodgas.setBe_ecf(strArr[j + 2]);
				}
				if (strArr[j].contains("SBC^C")) {
					System.out.println(strArr[j + 2] + " HCO-3");
					bloodgas.setHco2(strArr[j + 2]);
				}
				if (strArr[j].contains("Osm^C")) {
					System.out.println(strArr[j + 2] + " Osm^C");
					bloodgas.setOsmolarity(strArr[j + 2]);
				}
				if (strArr[j].contains("sO2^M")) {
					System.out.println(strArr[j + 2] + " sO2^M");
					bloodgas.setSpo2(strArr[j + 2]);
				}
				if (strArr[j].equals("L") && strArr[j + 3].equals("F") && dateStr != null
						&& dateStr.length() <= 0) {
					dateStr = strArr[j + 6];
				}
				if (strArr[j].equals("H") && strArr[j + 3].equals("F") && dateStr != null
						&& dateStr.length() <= 0) {
					dateStr = strArr[j + 6];
				}
				if (strArr[j].equals("N") && strArr[j + 3].equals("F") && dateStr != null
						&& dateStr.length() <= 0) {
					dateStr = strArr[j + 6];
				}
				if (strArr[j].contains("Cord Blood")) {
					System.out.println("Cord Blood Venous");
					bloodgas.setSampleType("Cord Blood Venous");
				} else if (strArr[j].contains("Arterial")) {
					System.out.println("Arterial");
					bloodgas.setSampleType("Arterial");
				} else if (strArr[j].contains("Venous")) {
					System.out.println("Venous");
					bloodgas.setSampleType("Venous");
				}
			}

			if (BasicUtils.isEmpty(dateStr)) {
				dateStr = dateStrTemp;
			}
			bloodgas.setLoggeduser("test");

			if (!BasicUtils.isEmpty(dateStr)) {
				System.out.println(dateStr + " date");

				Timestamp today = null;
				String year = dateStr.charAt(0) + "" + dateStr.charAt(1) + "" + dateStr.charAt(2) + ""
						+ dateStr.charAt(3);
				String month = dateStr.charAt(4) + "" + dateStr.charAt(5);
				String date = dateStr.charAt(6) + "" + dateStr.charAt(7);
				String hour = dateStr.charAt(8) + "" + dateStr.charAt(9);
				String min = dateStr.charAt(10) + "" + dateStr.charAt(11);
				String sec = dateStr.charAt(12) + "" + dateStr.charAt(13);
				String entryDate = year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;

				today = Timestamp.valueOf(entryDate);
				String dateString = year + "-" + month + "-" + date;

				System.out.println(today);
				System.out.println(dateString);

				bloodgas.setUserDate(dateString);
				String meridian = "AM";
				if (today.getHours() >= 12) {
					meridian = "PM";
				}
				String nn_blood_time = today.getHours() + ":" + today.getMinutes() + ":" + meridian;
				bloodgas.setNnBloodgasTime(nn_blood_time);

				String zone_time = "GMT+5:30";
				int offset = TimeZone.getTimeZone(zone_time).getRawOffset()
						- TimeZone.getDefault().getRawOffset();

				System.out.println(offset + "offset");

				bloodgas.setEntryDate((new Timestamp(today.getTime() + offset)));
				bloodgas.setCreationtime((new Timestamp(today.getTime() + offset)));
				bloodgas.setModificationtime((new Timestamp(today.getTime() + offset)));
			}

			patientDao.saveObject(bloodgas);

			String updateQuery = "update device_bloodgas_detail_detail set is_processed = 'true' where sample_id ='" + sampleId + "'";
			patientDao.updateOrDeleteNativeQuery(updateQuery);
		}
	}

}