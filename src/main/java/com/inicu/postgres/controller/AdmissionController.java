

package com.inicu.postgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inicu.device.utility.DeviceMonitoring;
import com.inicu.models.*;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.SettingsService;
import com.inicu.postgres.service.UserPanelService;
import com.inicu.postgres.utility.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message.RecipientType;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author OXYENT
 *
 */
/*
 * contains all rest calls for user list adding doctor and managing rights
 */
@RestController
public class AdmissionController {

	@Autowired
	private SettingsService settingService;

	@Autowired
	UserPanelService userPanel;

	@Autowired
	PatientService patientService;
	
	@Autowired
	InicuDao inicuDao;

	public boolean isDataCorrected = true;

	// returns list of all the users
	@RequestMapping(value = "/inicu/userList/{doctorId}/{branchName}", method = RequestMethod.POST)
	UserListJson getUserList(@PathVariable("doctorId") String doctorId, @PathVariable("branchName") String branchName) {
		// String doctorId = "123456";
		UserListJson userList = null;
		// validate user if it has the rights to access user list.
		if (!BasicUtils.isEmpty(doctorId)) {
			boolean hasRights = true;
			hasRights = userPanel.validateUser(doctorId);
			try {
				if (hasRights) {
					userList = userPanel.getUserList(doctorId, branchName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userList;
	}

	@RequestMapping(value = "/inicu/getEmptyAdmissionForm", method = RequestMethod.POST)
	public ResponseEntity<PatientInfoMasterObj> getEmptyAdmissionForm() {

		PatientInfoMasterObj pateintMasterObj = new PatientInfoMasterObj();
		return new ResponseEntity<PatientInfoMasterObj>(pateintMasterObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/viewPatientFullProfile/{patientId}/{opFor}/{dummy}", method = RequestMethod.POST)
	public ResponseEntity<PatientInfoMasterObj> viewPatientFullProfile(@PathVariable("patientId") String patientId,
			@PathVariable("opFor") String operationFor) {
		PatientInfoMasterObj pateintMasterObj = new PatientInfoMasterObj();
		try {
			pateintMasterObj = patientService.getPateintInfoMasterObj(patientId, operationFor);
			// pateintMasterObj.setDropDowns(patientService.getAdmissionFormMasterDropDowns());
			BasicUtils.getObjAsJson(pateintMasterObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" ##################### Json Returned for view patient profile as:  #####################\n"
				+ BasicUtils.getObjAsJson(pateintMasterObj));
		return new ResponseEntity<PatientInfoMasterObj>(pateintMasterObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/patientRegistration/submitAdmissionForm/{loggedUser}/{opFrom}/{opFor}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> submitPatientAdmissionForm(
			@RequestBody String admissionDetails, @PathVariable("loggedUser") String loggedUser,
			@PathVariable("opFrom") String operationFrom, @PathVariable("opFor") String operationFor) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {

			System.out.println("######################## Input json for create Patient is: #####################\n"
					+ admissionDetails);

			JSONObject admissionFormDetailsJSON = new JSONObject(admissionDetails);
			PatientInfoAdmissonFormObj admissionFormDetails = null;
			PatientInfoAddChildObj addChildDetails = null;
			PatientInfoChildDetailsObj childDetails = null;
			// String doctorId ="NA";

			// retrieving admissionform details from admission details
			JSONObject admissionFormJsonObj = admissionFormDetailsJSON.getJSONObject("admissionForm");
			String admissionFormObjString = admissionFormJsonObj.toString();
			admissionFormDetails = convertJasonToAdmissionFormObj(admissionFormObjString);
			// retrieving admissionform details from admission details
			JSONObject addChildJsonObj = admissionFormDetailsJSON.getJSONObject("addChild");
			String addChildObjString = addChildJsonObj.toString();
			addChildDetails = convertJasonToAdmissionFormAddChildObj(addChildObjString);

			// retrieving admissionform details from admission details
			JSONObject childDetailsJsonObj = admissionFormDetailsJSON.getJSONObject("childDetails");
			String childDetailsObjString = childDetailsJsonObj.toString();
			childDetails = convertJasonToChildDetailsObj(childDetailsObjString);

			// service call to admit patient..
			// Object
			// typeOfPatient=admissionFormDetailsJSON.getJSONObject("typeOfPatient").toString();
			Object typeOfPatient = admissionFormDetailsJSON.get("typeOfPatient").toString();
			Boolean flag = false; // if flag is false than only new patient will
									// be created

			if (typeOfPatient != null && typeOfPatient.toString().equalsIgnoreCase("new"))
				flag = patientService.isExistingUhid(admissionFormDetails.getUhid().toString());
			else if (typeOfPatient != null && typeOfPatient.toString().equalsIgnoreCase("old")) {
				Boolean flag2 = patientService.isExistingUhid(admissionFormDetails.getUhid().toString());

				if (!flag2) {
					responseMessageObj.setType(BasicConstants.MESSAGE_FAILURE);
					responseMessageObj.setMessage("Try to perform edit Patient with uhid "
							+ admissionFormDetails.getUhid() + ",but not exists!!");
				}
			}

			if (!flag) {// if its old patient direct update if its new checking
						// first in db not to exist
						// and then save.

				if (!BasicUtils.isEmpty(admissionFormDetails.getUhid())
						&& !admissionFormDetails.getUhid().toString().equalsIgnoreCase("null")) {

					if (operationFor.equalsIgnoreCase("readmission")) {
						// Update admission status and add re-admission history
						// record on re-admit
						// patient
						String uhid = admissionFormDetails.getUhid().toString();

						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

						java.util.Date utilreAdmissionDate;
						utilreAdmissionDate = dateFormat.parse(admissionFormDetails.getDateOfAdmission().toString());
						java.sql.Date reAdmissionDate = new java.sql.Date(utilreAdmissionDate.getTime());

						BabyDetail babyDetail = patientService.getBabyDetails(uhid);

						if (null != babyDetail) {
							String newbedNo = admissionFormDetails.getNicuRoomNo().getKey().toString().trim();
							patientService.updateDetailsOnReadmit(babyDetail, newbedNo, reAdmissionDate);
						}

						operationFor = "admission";
					}

					Object dashboardDeviceDetailView = patientService.AdmitPatient(admissionFormDetails,
							addChildDetails, childDetails, loggedUser, operationFrom);
					responseMessageObj.setReturnedObject(dashboardDeviceDetailView);
					responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
					responseMessageObj.setMessage(
							"Patient with uhid " + admissionFormDetails.getUhid() + " created successfully!!");
					// just a thing to get the old data for temporary....
					Object uhidTemp = admissionFormDetails.getUhid();
					if (uhidTemp != null && String.valueOf(uhidTemp) != null && !String.valueOf(uhidTemp).isEmpty()) {
						PatientInfoMasterObj pateintMasterObj = patientService
								.getPateintInfoMasterObj(uhidTemp.toString(), operationFor);
						/*
						 * responseMessageObj.setAdmissionForm(pateintMasterObj) ;
						 */
					}
				} else {
					responseMessageObj.setType(BasicConstants.MESSAGE_FAILURE);
					responseMessageObj.setMessage("Uhid is coming as empty or null!!");
				}

			} else {
				responseMessageObj.setType(BasicConstants.MESSAGE_FAILURE);
				responseMessageObj
						.setMessage("Patient with uhid " + admissionFormDetails.getUhid() + " already exists!!");
			}

		} catch (Exception ex) {
			responseMessageObj.setType("failure");
			responseMessageObj.setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		// for local storage at ui end sending patient device details on
		// submit..

		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/admissionFormDropDowns/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<AdmissionFormMasterDropDownsObj> getAdmissionFormMasterDropDowns(@PathVariable("branchName") String branchName) {
		AdmissionFormMasterDropDownsObj dropDownsObj = patientService.getAdmissionFormMasterDropDowns(branchName);
		System.out.println(" ##################### Json Returned for DropDowns as:  #####################\n"
				+ BasicUtils.getObjAsJson(dropDownsObj));
		return new ResponseEntity<AdmissionFormMasterDropDownsObj>(dropDownsObj, HttpStatus.OK);
	}

	private PatientInfoAdmissonFormObj convertJasonToAdmissionFormObj(String jsonStr) {
		PatientInfoAdmissonFormObj obj = new PatientInfoAdmissonFormObj();
		if (jsonStr != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {

				obj = mapper.readValue(jsonStr, PatientInfoAdmissonFormObj.class);
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	private PatientInfoAddChildObj convertJasonToAdmissionFormAddChildObj(String jsonStr) {
		PatientInfoAddChildObj obj = new PatientInfoAddChildObj();
		if (jsonStr != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				obj = mapper.readValue(jsonStr, PatientInfoAddChildObj.class);
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	private PatientInfoChildDetailsObj convertJasonToChildDetailsObj(String jsonStr) {
		PatientInfoChildDetailsObj obj = new PatientInfoChildDetailsObj();
		if (jsonStr != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {

				obj = mapper.readValue(jsonStr, PatientInfoChildDetailsObj.class);
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	@RequestMapping(value = "/inicu/addDoctor", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> addDoctor(@RequestBody Doctor doc) {
		ResponseMessageObject obj = new ResponseMessageObject();
		boolean status = false;
		if (doc != null) {
			status = userPanel.addDoctor(doc);
		}
		if (status) {
			obj.setType(BasicConstants.MESSAGE_SUCCESS);
			obj.setMessage("Successfully added");
		} else {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("Failed to add!");
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getRoles/{doctorId}", method = RequestMethod.POST)
	public ResponseEntity<GetRoleObj> getRoles(@PathVariable("doctorId") String id) {
		// validate doctorid
		GetRoleObj role = new GetRoleObj();
		boolean hasRights = true;
		hasRights = userPanel.validateUser("");
		if (hasRights) {
			role = userPanel.getRoles();
		}
		return new ResponseEntity<>(role, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/inicu/getRoleStatus/{roleId}/{branchName}/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<List<RoleManager>> getRoleStatus(@PathVariable("roleId") String roleId, @PathVariable("branchName") String branchName, @PathVariable("uhid") String uhid) {
		// validate doctorid
		List<RoleManager> role = new ArrayList<RoleManager>();
		role = userPanel.getRoleStatus(roleId, branchName, uhid);
		return new ResponseEntity<>(role, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/addUser", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> addUser(@RequestBody UserListPojo user) {
		// boolean success = false;
		ResponseMessageObject obj = null;
		try {
			if (user != null) {
				obj = userPanel.addUser(user);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj == null) {
			obj = new ResponseMessageObject();
			obj.setMessage("Oops something went Wrong!");
			obj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setStatus", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setStatus(@RequestBody UserStatusJSON userJson) {
		boolean success = false;
		ResponseMessageObject obj = new ResponseMessageObject();
		if (!BasicUtils.isEmpty(userJson)) {
			success = userPanel.setUserStatus(userJson);
		}
		if (success) {
			obj.setType(BasicConstants.MESSAGE_SUCCESS);
			obj.setMessage("status changes successfully");
		} else {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("Failed to change status!!!");
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDashboard/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<DashboardJSON>> getDashboard(@PathVariable("branchName") String branchName) {
		List<DashboardJSON> listOfDashboard = null;
		try {
			listOfDashboard = userPanel.getDashboard(new DashboardSearchFilterObj(), branchName);
			System.out.println("getDashboard() ----------> " + isDataCorrected);
			if (isDataCorrected) {
				babyVisitDataCorrection();
				isDataCorrected = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(listOfDashboard, HttpStatus.OK);
	}

	public void babyVisitDataCorrection() {
		try {
			String sql = "SELECT v FROM BabyDetail v where admissionstatus = 'true' order by creationtime";
			List<BabyDetail> dataList = inicuDao.getListFromMappedObjQuery(sql);

			if (!BasicUtils.isEmpty(dataList)) {
				for (BabyDetail babyDetail : dataList) {
					try {
						java.util.Date dateOfB = babyDetail.getDateofbirth();
						String timeOfBirth = babyDetail.getTimeofbirth();
						Calendar birthDateCal = CalendarUtility.getDateTimeFromDateAndTime(dateOfB, timeOfBirth);
						Date date = birthDateCal.getTime();
						Time time = new Time(date.getTime());

						if (!BasicUtils.isEmpty(time)) {
							String query = "update BabyVisit set visittime='"+ time +"' where uhid='" + babyDetail.getUhid()
									+ "' and episodeid='" + babyDetail.getEpisodeid() + "' and admission_entry = 'true' order by creationtime";
							inicuDao.updateOrDeleteQuery(query);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/inicu/getDashboardVitalDetail/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, VwVitalDetail>> getDashboardVitalDetail(@PathVariable("branchName") String branchName) {
		HashMap<String, VwVitalDetail> vitalDetailList = null;
		try {
			vitalDetailList = userPanel.getDashboardVitalDetail(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HashMap<String, VwVitalDetail>>(vitalDetailList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDashboardVitalDetailByUhid/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<DeviceMonitoring> getDashboardVitalDetailByUhid(@PathVariable("uhid") String uhid) {
		VwVitalDetail returnObj = null;
		DeviceMonitoring dm = new DeviceMonitoring();
		dm.setAlarm(false);
		boolean isConnected = false;
		// get vitals
		try {
			returnObj = userPanel.getDashboardVitalDetailByUhid(uhid);
			dm.setVwVitalObj(returnObj);
			isConnected = userPanel.isDeviceConnected(uhid);

			if (isConnected == true) {
				boolean raiseAlarm = userPanel.raiseAlarm(returnObj.getStart_time(), returnObj.getStarttime());
				dm.setAlarm(raiseAlarm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<DeviceMonitoring>(dm, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/searchPatient", method = RequestMethod.GET)
	public ResponseEntity<List<DashboardJSON>> searchPatients(@RequestBody DashboardSearchFilterObj filterobj) {

		return new ResponseEntity<List<DashboardJSON>>(userPanel.searchPatients(filterobj), HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setRoleStatus", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setRoleStatus(@RequestBody ManageRoleObj manageRoleObj) {
		boolean status = false;
		ResponseMessageObject respMessage = new ResponseMessageObject();
		if (manageRoleObj != null) {
			status = userPanel.setStatus(manageRoleObj);
			if (status) {
				respMessage.setType(BasicConstants.MESSAGE_SUCCESS);
				respMessage.setMessage("status changed successfully");
			} else {
				respMessage.setType(BasicConstants.MESSAGE_FAILURE);
				respMessage.setMessage("Oops! status could not be changed.");
			}
		}
		return new ResponseEntity<>(respMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/exportCsv/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<List<ExportBedDetailsCSV>> getDashboardExportData(@PathVariable("branchName") String branchName) {
		List<ExportBedDetailsCSV> result = userPanel.getDashboardCsvData(branchName);
		return new ResponseEntity<List<ExportBedDetailsCSV>>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/getimage/{imageName}/{branchname}", method = RequestMethod.GET)
	ResponseEntity<List<KeyValueObj>> getUserImageData(@PathVariable("imageName") String imageName, @PathVariable("branchname") String branchname) {
		List<KeyValueObj> imageData = null;
		try {
			imageData = userPanel.getImageData(imageName, branchname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(imageData, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/getDischargedPatients/{branchName}", method = RequestMethod.POST)
	ResponseEntity<List<VwDischargedPatientsFinal>> getDischargedPatients(@PathVariable("branchName") String branchName,
			@RequestBody DischargeSummaryPrintFiltersObj printFilters) {
		List<VwDischargedPatientsFinal> dischargedPatients = null;
		try {

			dischargedPatients = patientService.getDischargedPatients(printFilters.getSearchedForUhid() + "",
					printFilters.getSearchedStartDate() + "", printFilters.getSearchedEndDate() + "", branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<VwDischargedPatientsFinal>>(dischargedPatients, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDischargeSummary/{episodeid}/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	ResponseEntity<DischargedSummaryObj> getDischargeSummary(@PathVariable("episodeid") String episodeId,
			@PathVariable("uhid") String uhid, @PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		DischargedSummaryObj dischargeSummary = patientService.getDischargeSummary(uhid, episodeId, fromTime, toTime);
		List<KeyValueObj> imageData = null;
        String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
        List<BabyDetail> babyDetails = inicuDao.getListFromMappedObjQuery(query);
        String branchName = "";
        if (!BasicUtils.isEmpty(babyDetails)) {
            branchName = babyDetails.get(0).getBranchname();
        }
		imageData = settingService.getHospitalLogo(branchName);
		dischargeSummary.setLogoImage(imageData);
		return new ResponseEntity<DischargedSummaryObj>(dischargeSummary, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveDischargedSummary", method = RequestMethod.POST)
	ResponseEntity<ResponseMessageWithResponseObject> saveDischargeSummary(
			@RequestBody DischargedSummaryObj dischargedSummary) {
		ResponseMessageWithResponseObject response = patientService.saveDischargeSummary(dischargedSummary);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDischargeSummaryPrintRecords/{uhid}", method = RequestMethod.GET)
	ResponseEntity<HashMap<Object, Object>> getDischargeSummaryPrintRecords(@PathVariable("uhid") String uhid) {

		HashMap<Object, Object> dischargeSummary = patientService.getDischargeSummaryPrintRecords(uhid);
		return new ResponseEntity<HashMap<Object, Object>>(dischargeSummary, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getHl7PatientsList", method = RequestMethod.GET)
	ResponseEntity<List<DashboardJSON>> getListOfHl7Patients() {

		List<DashboardJSON> hl7PatientList = patientService.getHl7PatientsList();
		return new ResponseEntity<List<DashboardJSON>>(hl7PatientList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getMessageList/{uhid}", method = RequestMethod.POST)
	ResponseEntity<List<String>> getListOfMessages(@PathVariable("uhid") String uhid) {
		List<String> returnList = null;
		try {
			returnList = userPanel.getMessageList(uhid);
		} catch (InicuDatabaseExeption e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<String>>(returnList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/inifinitydevices/{uhid}/{bedId}", method = RequestMethod.POST)
	Object getInfinityGatewayDevices(@PathVariable("uhid") String uhid, @PathVariable("bedId") String bedid) {
		System.out.println(uhid + "---" + bedid);
		InfinityDeviceJsonObject obj = new InfinityDeviceJsonObject();
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(bedid)) {
			obj = userPanel.getInifityDevices(uhid, bedid);
		} else {
			rObj.setMessage("uhid and bed id are empty.");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}

		obj.setResponse(rObj);
		return obj;
	}

	@RequestMapping(value = "/inicu/savehl7devicemapping/{uhid}/{bedid}", method = RequestMethod.POST)
	Object savehl7deviceMapping(@RequestBody InfinityDeviceJsonObject bedDetail, @PathVariable("uhid") String uhid,
			@PathVariable("bedid") String bedid) {
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setMessage("Oops! could not save.");
		obj.setType(BasicConstants.MESSAGE_FAILURE);
		if (!BasicUtils.isEmpty(bedDetail)) {
			obj = userPanel.saveInifinityDeviceMapping(bedDetail);
		}
		InfinityDeviceJsonObject devObj = new InfinityDeviceJsonObject();
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(bedid)) {
			devObj = userPanel.getInifityDevices(uhid, bedid);
		} else {
			rObj.setMessage("uhid and bed id are empty.");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}

		devObj.setResponse(obj);
		return devObj;
	}

	@RequestMapping(value = "inicu/getAddSettingPreference", method = RequestMethod.POST)
	HashMap<String, String> getAddSettingPreference() {
		return userPanel.getAddSettingPreference();
	}

	@RequestMapping(value = "/inicu/disconnectDeviceInfinity/{uhid}/{bedid}", method = RequestMethod.POST)
	Object disconnectInfinityBed(@RequestBody InfinityDeviceJsonObject bedDetail, @PathVariable("uhid") String uhid,
			@PathVariable("bedid") String bedid) {
		userPanel.disconnectInfinityDevice(bedDetail);
		InfinityDeviceJsonObject obj = new InfinityDeviceJsonObject();
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(bedid)) {
			obj = userPanel.getInifityDevices(uhid, bedid);
		} else {
			rObj.setMessage("uhid and bed id are empty.");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}

		obj.setResponse(rObj);
		return obj;
	}

	@RequestMapping(value = "inicu/uploadPatientDocument/{uhid}/{desc}", method = RequestMethod.POST) // //new
																										// annotation
																										// since
																										// 4.3
	public ResponseEntity<ResponseMessageWithResponseObject> singleFileUpload(@RequestBody String file,
			@PathVariable("uhid") String uhid, @PathVariable("desc") String desc) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		if (file.isEmpty()) {
			return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
		} else {
			response = patientService.uplodaBabyDocument(file, uhid, desc);
			return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "inicu/getBabyDocuments/{uhid}", method = RequestMethod.GET) // //new
																							// annotation
																							// since
																							// 4.3
	public ResponseEntity<List<HashMap<Object, Object>>> getBabyDocuments(@PathVariable("uhid") String uhid) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "test.pdf";
			headers.setContentDispositionFormData(filename, filename);
			List<HashMap<Object, Object>> babyDocData = patientService.getBabyDocuments(uhid);
			return new ResponseEntity<List<HashMap<Object, Object>>>(babyDocData, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<HashMap<Object, Object>>>(HttpStatus.OK);
	}

	@RequestMapping(value = "inicu/saveBallardScore", method = RequestMethod.POST) // //new
																					// annotation
																					// since
																					// 4.3
	public ResponseEntity<ResponseMessageWithResponseObject> saveBallardScore(@RequestBody ScoreBallard ballardScore) {
		ResponseMessageWithResponseObject response = patientService.saveBallardScore(ballardScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveApgarScore", method = RequestMethod.POST) // //new
																					// annotation
																					// since
																					// 4.3
	public ResponseEntity<ResponseMessageWithResponseObject> saveApgarScore(@RequestBody ApgarScoreObj apgarScore) {

		ResponseMessageWithResponseObject response = patientService.saveApgarScore(apgarScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/deleteBabyDoc/{docId}/{loggedUserId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteBabyDocument(@PathVariable("docId") String docId,
			@PathVariable("loggedUserId") String loggedUserId) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		try {
			obj = patientService.deleteBabyDocument(docId, loggedUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDashboardUser/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<List<DashboardJSON>> getDashboardUser(@PathVariable("uhid") String uhid) {
		List<DashboardJSON> listOfDashboard = null;
		try {
			listOfDashboard = userPanel.getDashboardUser(new DashboardSearchFilterObj(), uhid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(listOfDashboard, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/sendExceptionMail/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> sendExceptionMail() {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		try {
			Exception e = new NullPointerException();
			String[] receiverArray2 = { BasicConstants.MAIL_ID_RECIEVER };
			BasicUtils.sendMail(receiverArray2, RecipientType.TO, BasicUtils.convertErrorStacktoString(e),
					"Mail from sendExceptionMail API", BasicConstants.COMPANY_ID);
			obj.setMessage("Exception Mail Sent !!");
			obj.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getReadmitChildData/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<List<DashboardJSON>> getReadmitChildData(@PathVariable("uhid") String uhid) {
		List<DashboardJSON> listOfChildData = null;
		try {
			listOfChildData = userPanel.getReadmitChildData(new DashboardSearchFilterObj(), uhid);// new
																									// empty
																									// filter
																									// object
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(listOfChildData, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDashboardWeightTracking/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<WeightTrackingJSON>> getWeightTrackingData(@PathVariable("branchName") String branchName) {
		List<WeightTrackingJSON> listWeightTracking = null;
		try {
			listWeightTracking = userPanel.getWeightTrackingData(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(listWeightTracking, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveDashboardWeightTracking/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveWeightTrackingData(
			@RequestBody List<WeightTrackingJSON> weightTrackingObj, @PathVariable("loggedUser") String loggedUser, @PathVariable("branchName") String branchName) {
		ResponseMessageWithResponseObject returnObj = userPanel.saveWeightTrackingData(weightTrackingObj, loggedUser, branchName);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getEmptyAdvanceAdmissionForm", method = RequestMethod.POST)
	public ResponseEntity<AdvanceAdmitPatientPojo> getEmptyAdvanceAdmissionForm(@RequestBody String details) {
		AdvanceAdmitPatientPojo emptyObj = new AdvanceAdmitPatientPojo();

		String tempUhid = "";
		JSONObject settingInfo = null;
		try {
			settingInfo = new JSONObject(details);
			if (!BasicUtils.isEmpty(settingInfo)) {
				if (settingInfo.has("branchName")) {
					String branchName = settingInfo.getString("branchName");
					if (settingInfo.has("fromWhere")) {
						String fromWhere = settingInfo.getString("fromWhere");
						if (!BasicUtils.isEmpty(branchName) && !BasicUtils.isEmpty(fromWhere)
								&& "beforesave".equals(fromWhere)) {
							tempUhid = generateRandomUHID(branchName);
						}
					}
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		if (!BasicUtils.isEmpty(tempUhid)) {
			emptyObj.getBabyDetailObj().setUhid(tempUhid);
		}

		return new ResponseEntity<AdvanceAdmitPatientPojo>(emptyObj, HttpStatus.OK);
	}

	private String generateRandomUHID(String branchName) {
		boolean isUHIDAlreadyExists = false;
		String tempUhid = "";
		String tempUHIDQuery = "SELECT tempuhidtoggle FROM ref_hospitalbranchname WHERE branchname='" + branchName + "'";
		List<Object[]> tempUHIDQueryResultSet = inicuDao.getListFromNativeQuery(tempUHIDQuery);
		if (!BasicUtils.isEmpty(tempUHIDQueryResultSet)) {
			String tempuhidtoggle = String.valueOf(tempUHIDQueryResultSet.get(0));
			if (!BasicUtils.isEmpty(tempuhidtoggle) && "Yes".equals(tempuhidtoggle)) {
				while (!isUHIDAlreadyExists) {
					int randomNumber = new Random().nextInt((999999999 - 100000) + 1) + 100000;
					tempUhid = "TEMP." + randomNumber;
					isUHIDAlreadyExists = true;

					String uhidExistsQuery = "SELECT uhid FROM baby_detail WHERE uhid='" + tempUhid + "'";
					List<Object[]> uhidExistsResultSet = inicuDao.getListFromNativeQuery(uhidExistsQuery);
					if (!BasicUtils.isEmpty(uhidExistsResultSet)) {
						isUHIDAlreadyExists = false;
					}
				}
			}
		}
		return tempUhid;
	}

	@RequestMapping(value = "/inicu/validateUhid/{uhid}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> validateUhid(@PathVariable("uhid") String uhid) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();
		boolean flag = patientService.isExistingUhid(uhid);

		if (flag) {
			returnObj.setType("Failure");
			returnObj.setMessage("UHID already Exists !!");
		} else {
			returnObj.setType("Success");
			returnObj.setMessage("Success");
		}

		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/submitAdvanceAdmissionForm/{loggedUser}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> submitAdvanceAdmissionForm(
			@RequestBody AdvanceAdmitPatientPojo registrationObj, @PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject returnObj = patientService.submitAdvanceAdmissionForm(registrationObj,
				loggedUser);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAdvanceAdmissionForm/{uhid}/", method = RequestMethod.POST)
	public ResponseEntity<AdvanceAdmitPatientPojo> getAdvanceAdmissionForm(@PathVariable("uhid") String uhid,
																		   @RequestBody String stats) {

		String loggedInUser = "";
		if (!BasicUtils.isEmpty(stats)) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) parser.parse(stats);
			if (jsonObj.has("loggedInUser")) {
				loggedInUser = jsonObj.get("loggedInUser").toString().replaceAll("\"","");
			}
		}
		AdvanceAdmitPatientPojo returnObj = patientService.getAdvanceAdmissionForm(uhid, loggedInUser);
		List<KeyValueObj> imageData = null;

        String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
        List<BabyDetail> babyDetails = inicuDao.getListFromMappedObjQuery(query);
        String branchName = "";
        if (!BasicUtils.isEmpty(babyDetails)) {
            branchName = babyDetails.get(0).getBranchname();
        }
		imageData = settingService.getHospitalLogo(branchName);
		returnObj.setLogoImage(imageData);
		return new ResponseEntity<AdvanceAdmitPatientPojo>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAdvanceAdmissionFormReadmit/{uhid}/", method = RequestMethod.POST)
	public ResponseEntity<AdvanceAdmitPatientPojo> getAdvanceAdmissionFormReadmit(@PathVariable("uhid") String uhid) {
		AdvanceAdmitPatientPojo returnObj = patientService.getAdvanceAdmissionFormReadmit(uhid);
		return new ResponseEntity<AdvanceAdmitPatientPojo>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getCityAllList/{state}", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, MasterAddress>> getCityAllList(@PathVariable("state") String state) {
		HashMap<String, MasterAddress> addressList = patientService.getCityAllList(state);
		return new ResponseEntity<HashMap<String, MasterAddress>>(addressList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDistrictList/{state}", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, String>> getDistrictList(@PathVariable("state") String state) {
		HashMap<String, String> districtList = patientService.getDistrictList(state);
		return new ResponseEntity<HashMap<String, String>>(districtList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAddressList/{state}/{district}", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, MasterAddress>> getAddressList(@PathVariable("state") String state,
			@PathVariable("district") String district) {
		HashMap<String, MasterAddress> addressList = patientService.getAddressList(state, district);
		return new ResponseEntity<HashMap<String, MasterAddress>>(addressList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getMandatoryFormData/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<MandatoryFormPojo> getMandatoryFormData(@PathVariable("uhid") String uhid) {
		MandatoryFormPojo returnObj = patientService.getMandatoryFormData(uhid);
		return new ResponseEntity<MandatoryFormPojo>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/submitMandatoryForm/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> submitMandatoryForm(
			@RequestBody MandatoryFormPojo mandatoryFormObj, @PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject returnObj = patientService.submitMandatoryForm(mandatoryFormObj, uhid,
				loggedUser);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/extractImage/{imageName}", method = RequestMethod.GET)
	ResponseEntity<ImagePOJO> extractImage(@PathVariable("imageName") String imageName) {
		ImagePOJO imageData = null;
		try {
			imageData = userPanel.getImage(imageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ImagePOJO>(imageData, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/storeImage/", method = RequestMethod.POST)
	ResponseEntity<ResponseMessageWithResponseObject> storeImage(@RequestBody ImagePOJO imageObj) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			response = userPanel.storeImage(imageObj.getName(), imageObj.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/inicu/getDoctorDailyTasks/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<DoctorTasks>> getDoctorTasks(@PathVariable("branchName") String branchName) {
		List<DoctorTasks> doctorTasksList = null;
		try {
			doctorTasksList = userPanel.getDoctorTasks(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(doctorTasksList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getNewDoctorDailyTasks/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<DoctorTasks>> getNewDoctorTasks(@PathVariable("branchName") String branchName) {
		List<DoctorTasks> doctorTasksList = null;
		try {
			doctorTasksList = userPanel.getNewDoctorTasks(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(doctorTasksList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getNurseDailyTasks/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<NurseTasks>> getNurseTasks(@PathVariable("branchName") String branchName) {
		List<NurseTasks> nurseTasksList = null;
		try {
			nurseTasksList = userPanel.getNurseTasks(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(nurseTasksList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveDoctorDailyTasks/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<List<DoctorTasks>> saveDoctorTasks(@RequestBody List<DoctorTasks> doctorTasksList, 
			@PathVariable("branchName") String branchName, @PathVariable("loggedUser") String loggedUser) {
		try {
			if(!BasicUtils.isEmpty(doctorTasksList)) {
				for(DoctorTasks obj : doctorTasksList) {
					obj.setLoggedUser(loggedUser);
				}
				inicuDao.saveMultipleObject(doctorTasksList);
			}
			doctorTasksList = userPanel.getDoctorTasks(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(doctorTasksList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveNurseDailyTasks/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<List<NurseTasks>> saveNurseTasks(@RequestBody List<NurseTasks> nurseTasksList, 
			@PathVariable("branchName") String branchName, @PathVariable("loggedUser") String loggedUser) {
		try {
			if(!BasicUtils.isEmpty(nurseTasksList)) {
				for(NurseTasks obj : nurseTasksList) {
					obj.setLoggedUser(loggedUser);
				}
				inicuDao.saveMultipleObject(nurseTasksList);
			}
			nurseTasksList = userPanel.getNurseTasks(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(nurseTasksList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/activeBabies/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<BabyDetailPOJO> getActiveBabies(@PathVariable("branchName") String branchName) {
		BabyDetailPOJO masterPOJO = null;
		try {
			masterPOJO = userPanel.getActiveBabies(branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(masterPOJO, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/printdata/{printDate}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<GeneralResponseObject> printdata(@RequestBody printDataRequestObject requestObject,
														   @PathVariable("branchName") String branchName,
														   @PathVariable("printDate") String printDate,
														   @PathVariable("loggedUser") String loggedUser) {
		GeneralResponseObject masterPOJO = null;
		try {
			masterPOJO = patientService.getPrintData(requestObject,branchName,printDate,loggedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(masterPOJO, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/activeBabiesByDate/{printDate}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<GeneralResponseObject> activeBabiesByDate(@PathVariable("branchName") String branchName,
																	@PathVariable("printDate") String printDate) {
		GeneralResponseObject masterPOJO = null;
		try {
			masterPOJO = patientService.getPrintBabies(branchName,printDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(masterPOJO, HttpStatus.OK);
	}


}
