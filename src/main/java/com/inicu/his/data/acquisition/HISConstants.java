package com.inicu.his.data.acquisition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.inicu.postgres.utility.BasicConstants;


public class HISConstants {
	public static String LAB_UHID = "uhid";
	public static String LAB_TESTID = ""; 
	public static String LAB_ITEMID = "";
	public static String LAB_ITEMNAME = "";
	public static String LAB_ITEMVALUE = "";
	public static String LAB_UPDATED_BY = "";
	public static String LAB_RESULT_STATUS = "";
	public static String LAB_CREATED_DATE = "";
	public static String LAB_CREATED_TIME = "";
	public static String LAB_UPDATED_DATE = "";
	public static String LAB_UPDATED_TIME = "";
	public static String LAB_COLLECTION_DATE = "";
	public static String LAB_COLLECTION_TIME = "";
	public static String LAB_POSTED_DATE = "";
	public static String LAB_REPORT_DATE = "";
	public static String LAB_REPORT_TIME = "";
	public static String LAB_TEST_DETAILS_ID = "";
	public static String LAB_TESTNAME = "";
	public static String LAB_ITEMUNIT = "";
	public static String LAB_ITEM_NORMALRANGE = "";
	public static String LAB_ITEM_NORMALRANGE_UPPER = "";
	public static String LAB_FETCH_CRITERIA = "";
	public static String LAB_FETCH_CRITERIA_INICU = "";
	public static String LAB_FETCH_CRITERIA_TYPE = "";
	public static String LAB_APPROVED_DATE = "";
	public static String LAB_APPROVED_TIME = "";
	public static String LAB_CENTER_CODE = "";
	public static String TEST_UNIQUE_ID = "";
    public static String TEST_UNIQUE_NAME = "";
    public static String LAB_RESULT_TYPE = "";
    public static String LAB_REPORT_URL = "";
    public static String TEST_UNIQUE_CODE = "";
    public static String LAB_SAMPLE_TYPE = "";
    public static String LAB_DETAIL_HISTO = "";
    public static String LAB_GROSS_HISTO = "";
    public static String LAB_MICROSCOPIC_HISTO = "";
    public static String LAB_IMPRESSION_HISTO = "";
    public static String LAB_ADVICE_HISTO = "";
    public static String LAB_CLINICALHISTORY_HISTO = "";
    
    public static String LAB_OBSERVATION_NAME_MICRO = "";
    public static String LAB_VALUE_MICRO = "";
    public static String LAB_COMMENTS_MICRO = "";
    public static String LAB_UNIT_MICRO = "";
    public static String LAB_OBSERVATION_COMMENT_MICRO = "";
    public static String LAB_ORGANISM_NAME_DISPLAY_NAME_MICRO = "";
    public static String LAB_COLONY_COUNT_MICRO = "";
    public static String LAB_COLONY_COUNT_COMMENT_MICRO = "";
    public static String LAB_ANTIBIOTIC_NAME_MICRO = "";
    public static String LAB_ANTIBIOTIC_INTERPREATATION_MICRO = "";
    public static String LAB_MIC_MICRO = "";
    public static String LAB_REPORT_TYPE_MICRO = "";
    public static String LAB_DISPLAY_READING_TEXT = "";
    public static String LAB_PName = "";
    public static String LAB_Age = "";
    public static String LAB_Gender = "";
    public static String LAB_IP_NUMBER = "";
    public static String LAB_SAMPLE_ID = "";
	public static String LAB_ORDER_ID = "";

    public static String REF_LAB_ITEM_ID = "";
    public static String REF_LAB_ITEM_NAME = "";
    public static String TEST_ORDER_CATEGORY= "";
	public static String TEMPLATE_XML= "";
	public static String TEMPLATE_HTML= "";

//	public static String HIS_UHID = "uhid";
//	public static String HIS_BABYNAME = "";
//	public static String HIS_BABYGENDER = "";
//	public static String HIS_BABYDOA = "";
//	public static String HIS_BABYDOB = "";
//	public static String HIS_BABY_ADMISSION_TYPE = "";
//	public static String HIS_RESIDENT_DOCTOR = "";
//	public static String HIS_MOTHERNAME = "";
	public static List<String> HIS_FIELDS = new ArrayList<>();
	public static HashMap<String, String> HISParametersMap = new HashMap<String, String>() {
	};
	public static HashMap<String, String> HISDischargeParametersMap = new HashMap<String, String>() {
	};
	public static List<String> HIS_DISCHARGE_VIEW_FIELDS = new ArrayList<>();
	
	static{
		if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")){
//			LAB_UHID = "uhid";
//			LAB_TESTID = "test_id"; 
//			LAB_ITEMID = "parameter_id";
//			LAB_ITEMNAME = "parameter_name";
//			LAB_ITEMVALUE = "result";
//			LAB_UPDATED_BY = "updated_by";
//			LAB_RESULT_STATUS = "result_status";
//			LAB_CREATED_DATE = "created_date";
//			LAB_UPDATED_DATE = "updated_date";
//			LAB_COLLECTION_DATE = "collection_date";
//			LAB_POSTED_DATE = "posted_date";
//			LAB_REPORT_DATE = "report_date";
//			LAB_TEST_DETAILS_ID = "test_details_id";
//			LAB_TESTNAME = "service_name";
//			LAB_ITEMUNIT = "unit";
//			LAB_FETCH_CRITERIA = " test_details_id  ";
//			LAB_FETCH_CRITERIA_INICU = "lab_testresultid";
//			LAB_FETCH_CRITERIA_TYPE = "long";
			
//			HIS_UHID = "uhid";
//			HIS_BABYNAME = "name";
//			HIS_BABYGENDER = "gender";
//			HIS_BABYDOA = "doa";
//			HIS_BABYDOB = "dob";
//			HIS_BABY_ADMISSION_TYPE = "admissiontype";
//			HIS_RESIDENT_DOCTOR = "avneet";
			
			
			LAB_UHID = "Uhid";
			LAB_TESTID = "TestId";
			LAB_CENTER_CODE = "Labcenter";
			LAB_POSTED_DATE = "LabPosted";
			LAB_REPORT_DATE = "LabReport";
			LAB_COLLECTION_DATE = "LabCollection";
			LAB_TESTNAME = "TestName";
			LAB_ITEMID = "ItemId";
			LAB_ITEMNAME = "ItemName";
			LAB_ITEMUNIT = "ItemUnit";
			LAB_RESULT_STATUS = "ResultStatus";
			LAB_ITEMVALUE = "ResultValue";
			LAB_RESULT_TYPE = "ResultType";
			LAB_UPDATED_BY = "AuthorizedBy";
			LAB_TEST_DETAILS_ID = "TestDetailId";
			LAB_REPORT_URL = "ReportURL";
			LAB_SAMPLE_TYPE = "SampleTypeName";
			LAB_ITEM_NORMALRANGE = "DisplayReading";
			LAB_FETCH_CRITERIA = "LabReport";
			LAB_DETAIL_HISTO = "Detail";
			LAB_GROSS_HISTO = "Gross";
			LAB_MICROSCOPIC_HISTO = "Microscopic";
			LAB_IMPRESSION_HISTO = "Impression";
			LAB_ADVICE_HISTO = "Advice";
			LAB_CLINICALHISTORY_HISTO = "ClinicalHistory";
			
			LAB_OBSERVATION_NAME_MICRO = "labobservation_name";
			LAB_VALUE_MICRO = "value";
			LAB_COMMENTS_MICRO = "Comments";
			LAB_UNIT_MICRO = "Unit";
			LAB_OBSERVATION_COMMENT_MICRO = "labobservationcomment";
			LAB_ORGANISM_NAME_DISPLAY_NAME_MICRO = "OrganismNameDisplayname";
			LAB_COLONY_COUNT_MICRO = "colonycount";
			LAB_COLONY_COUNT_COMMENT_MICRO = "colonycountcomment";
			LAB_ANTIBIOTIC_NAME_MICRO = "AntibioticName";
			LAB_ANTIBIOTIC_INTERPREATATION_MICRO = "AntibioticInterpreatation";
			LAB_MIC_MICRO = "MIC";
			LAB_REPORT_TYPE_MICRO = "Reporttype";
			
			LAB_DISPLAY_READING_TEXT = "DisplayReading";

			TEST_UNIQUE_ID = "Investigation_Id";
			TEST_UNIQUE_NAME = "TestName";
			TEST_UNIQUE_CODE = "TestCode";
			
			
			LAB_FETCH_CRITERIA_INICU = "lab_report_date";
			LAB_FETCH_CRITERIA_TYPE = "timestamp";
			LAB_PName = "PName";
		    LAB_Age = "Age";
		    LAB_Gender = "Gender";
				
			HISParametersMap.put("uhid", "uhid");
			HISParametersMap.put("babyname", "name");
			HISParametersMap.put("babygender", "gender");
			HISParametersMap.put("babydoa", "doa");
			HISParametersMap.put("babydob", "dob");
			HISParametersMap.put("admissionType", "admissiontype");
			HISParametersMap.put("residentDoctor", "doctor");
			HISParametersMap.put("branchname", "centername");
			HISParametersMap.put("babytoa", "admit_time");
			HISParametersMap.put("babytob", "birth_time");
			HISDischargeParametersMap.put("hisdischargedate", "physical_discharge_date");
			HISDischargeParametersMap.put("hisdischargetime", "physical_discharge_time");
			HISDischargeParametersMap.put("hisdischargestatus", "discharge_type");
			HISDischargeParametersMap.put("uhid", "uhid");
			HISParametersMap.put("ipnumber", "visitno");
			HISParametersMap.put("primaryno", "patient_phone");
			HISParametersMap.put("address", "patient_address");
			for(String parameter: HISParametersMap.values()) {
				HIS_FIELDS.add(parameter);
			}
			for(String parameter: HISDischargeParametersMap.values()) {
				HIS_DISCHARGE_VIEW_FIELDS.add(parameter);
			}
		}
		else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("rainbow")){
			LAB_UHID = "PatientID";
			
			LAB_ITEMID = "TestCode";
			LAB_RESULT_STATUS = "ResultStatus";
			LAB_ITEMNAME = "ParameterName";
			LAB_ITEMVALUE = "ResultValue";
			LAB_REPORT_DATE = "OrderDttm";
			LAB_TESTNAME = "TestName";
			LAB_ITEM_NORMALRANGE = "ReferenceRange";
			LAB_FETCH_CRITERIA = " OrderDttm  ";
			LAB_FETCH_CRITERIA_INICU = "lab_report_date";
			LAB_FETCH_CRITERIA_TYPE = "timestamp";
			LAB_ORDER_ID = "OrderNo";
			
			TEST_UNIQUE_ID = "ItemUID";
			TEST_UNIQUE_NAME = "ItemName";
			TEST_UNIQUE_CODE = "code";
			TEST_ORDER_CATEGORY = "Ordercategory";
			TEMPLATE_XML ="TemplateXML";
			TEMPLATE_HTML= "TemplateHtml";
//			HIS_UHID = "PatientID";
//			HIS_BABYNAME = "PatientName";
//			HIS_BABYGENDER = "Gender";
//			HIS_BABYDOA = "DOA";
//			HIS_BABYDOB = "DateOfBirth";
			
			HISParametersMap.put("uhid", "PatientID");
			HISParametersMap.put("babyname", "PatientName");
			HISParametersMap.put("babygender", "Gender");
			HISParametersMap.put("babydoa", "DOA");
			HISParametersMap.put("babydob", "DateOfBirth");
			HISParametersMap.put("emailid", "EmailAddress");
			HISParametersMap.put("primaryno", "PhoneNumber");
			HISParametersMap.put("secondaryno", "MobileNumber");
			HISParametersMap.put("address", "Address");
			HISParametersMap.put("city", "City");
			HISParametersMap.put("state", "State");
			HISParametersMap.put("country", "Country");
			HISParametersMap.put("ipnumber", "EpisodeNo");
			HISParametersMap.put("babytoa", "TimeDOA");
			HISParametersMap.put("babytob", "TimeOfBirth");
            HISDischargeParametersMap.put("hisdischargedate", "DISCHARGEDATE");
            HISDischargeParametersMap.put("hisdischargestatus", "Dischargestatus");
            HISDischargeParametersMap.put("uhid", "PATIENTID");
			for(String parameter: HISParametersMap.values()) {
				HIS_FIELDS.add(parameter);
			}

            for(String parameter: HISDischargeParametersMap.values()) {
                HIS_DISCHARGE_VIEW_FIELDS.add(parameter);
            }
		}
		else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("kalawati")){
			LAB_UHID = "Patient_ID";
			LAB_TESTID = "TestID";
			LAB_ITEMID = "ItemID";
			LAB_ITEMNAME = "ItemName";
			LAB_ITEMVALUE = "ResultStatus";
			LAB_UPDATED_BY = "AuthorizedBy";
			LAB_COLLECTION_DATE = "LabCollectionDateTime";
			LAB_POSTED_DATE = "LabPostedDateTime";
			LAB_REPORT_DATE = "LabReportDateTime";
			LAB_APPROVED_DATE = "ApprovedDate";
			LAB_TEST_DETAILS_ID = "TestDetailID";
			LAB_TESTNAME = "TestName";
			LAB_ITEMUNIT = "ItemUnit";
			LAB_ITEM_NORMALRANGE = "ItemNormalRange";
			LAB_FETCH_CRITERIA = "ApprovedDate";
			LAB_CENTER_CODE = "Lab Centre Code/Name";
			TEST_UNIQUE_ID = "Investigation_Id";
			TEST_UNIQUE_NAME = "TestName";
			LAB_RESULT_TYPE = "ReportType";
			
			
			LAB_FETCH_CRITERIA_INICU = "lab_approved_date";
			LAB_FETCH_CRITERIA_TYPE = "timestamp";
			
//			HIS_UHID = "PatientID";
//			HIS_BABYNAME = "PatientName";
//			HIS_BABYGENDER = "Gender";
//			HIS_BABYDOA = "DOA";
//			HIS_BABYDOB = "DateOfBirth";
			
			HISParametersMap.put("uhid", "PatientID");
			HISParametersMap.put("babyname", "PatientName");
			HISParametersMap.put("babygender", "Gender");
			HISParametersMap.put("babydoa", "DOA");
			HISParametersMap.put("babydob", "DateOfBirth");
			HISParametersMap.put("emailid", "EmailAddress");
			HISParametersMap.put("primaryno", "PhoneNumber");
			HISParametersMap.put("secondaryno", "MobileNumber");
			HISParametersMap.put("address", "Address");
			HISParametersMap.put("city", "City");
			HISParametersMap.put("state", "State");
			HISParametersMap.put("country", "Country");
			
			for(String parameter: HISParametersMap.values()) {
				HIS_FIELDS.add(parameter);
			}
			
		}
		else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("gangaram")){
			LAB_TESTID = "TestSet_Code";
			LAB_TESTNAME = "Test_Name";
			LAB_IP_NUMBER = "Episode_No";
			LAB_RESULT_TYPE = "OrderCurrent_status";
			LAB_CENTER_CODE = "CTDEP_Name";
			LAB_CREATED_DATE = "Order_Date";
			LAB_CREATED_TIME = "Order_Time";
			LAB_COLLECTION_DATE = "Collection_Date";
			LAB_COLLECTION_TIME = "Collection_Time";
			LAB_REPORT_DATE = "Receiving_Date";
			LAB_REPORT_TIME = "Receiving_Time";
			LAB_UPDATED_DATE = "Entry_Date";
			LAB_UPDATED_TIME = "Entry_Time";
			LAB_APPROVED_DATE = "Authorisation_Date";
			LAB_APPROVED_TIME = "Authorisation_Time";
			LAB_PName = "Patient_Name";
		    LAB_Age = "Age";
		    LAB_Gender = "Gender";
			LAB_ITEMUNIT = "Units";
			TEST_UNIQUE_ID = "TestSet";
			TEST_UNIQUE_CODE = "TestSet";
			TEST_UNIQUE_NAME = "TestSet_Name";
			REF_LAB_ITEM_ID = "TestCode";
			REF_LAB_ITEM_NAME = "TestCode_Name";
			LAB_SAMPLE_ID = "SampleID";
			LAB_ITEMID = "Test_Code";
			LAB_ITEMNAME = "Test";
			LAB_ITEMVALUE = "result";

			LAB_ITEM_NORMALRANGE = "CTTCR_LowRange";
			LAB_ITEM_NORMALRANGE_UPPER = "CTTCR_HighRange";

			LAB_FETCH_CRITERIA_INICU = "lab_approved_date";
			LAB_FETCH_CRITERIA_TYPE = "timestamp";

				HISParametersMap.put("uhid", "RegNo");
				HISParametersMap.put("babyname", "Name");
				HISParametersMap.put("babygender", "Sex");
				HISParametersMap.put("fathername", "Father_name");
				HISParametersMap.put("residentDoctor", "TreatingDoctor");
				HISParametersMap.put("babydoa", "AdmissionDate");
				HISParametersMap.put("babydob", "PAPER_Dob");
				HISParametersMap.put("primaryno", "Mobile");
				HISParametersMap.put("address", "Address");
				HISParametersMap.put("ipnumber", "EpisodeNumber");
				HISParametersMap.put("babytob", "AdmissionTime");
            HISDischargeParametersMap.put("hisdischargedate", "Discharge_Date");
            HISDischargeParametersMap.put("hisdischargetime", "Discharge_Time");
            HISDischargeParametersMap.put("hisdischargestatus", "EpisodeStatus");
            HISDischargeParametersMap.put("uhid", "RegNo");
			for(String parameter: HISParametersMap.values()) {
				HIS_FIELDS.add(parameter);
			}

            for(String parameter: HISDischargeParametersMap.values()) {
                HIS_DISCHARGE_VIEW_FIELDS.add(parameter);
            }
		}
	}
	
	
	

}
