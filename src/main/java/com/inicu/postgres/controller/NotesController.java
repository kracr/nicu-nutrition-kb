
package com.inicu.postgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.models.*;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.*;
import com.inicu.postgres.utility.BasicUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//import javax.ws.rs.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author iNICU
 *
 */
@RestController
public class NotesController {
	@Autowired
	private SettingsService settingService;

	@Autowired
	PatientService patientService;

	@Autowired
	LogsService logService;

	@Autowired
	NotesService notesService;

    @Autowired
    UserPanelService userPanel;

	@Autowired
	InicuDao inicuDao;

	@RequestMapping(value = "/inicu/getDoctorOrder/{uhid}/{fromDateStr}/", method = RequestMethod.GET)
	public ResponseEntity<List<NurseExecutionOrders>> getDoctorOrder(@PathVariable("uhid") String uhid,
			@PathVariable("fromDateStr") String fromDateStr) {
		List<NurseExecutionOrders> returnObj = notesService.getDoctorOrder(uhid, fromDateStr);
		return new ResponseEntity<List<NurseExecutionOrders>>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sys/saveNursingExecution/{uhid}/{fromDateStr}", method = RequestMethod.POST)
	public ResponseEntity<List<NurseExecutionOrders>> saveNursingExecution(@RequestBody List<NurseExecutionOrders> doctorNotesObj,
			@PathVariable("uhid") String uhid, @PathVariable("fromDateStr") String fromDateStr) {
		List<NurseExecutionOrders> returnObj = notesService.saveNursingExecution(uhid, fromDateStr, doctorNotesObj);
		return new ResponseEntity<List<NurseExecutionOrders>>(returnObj, HttpStatus.OK);
	}

	// used tables are ...doctor_notes,babyfeed_detail
	@RequestMapping(value = "/inicu/addDoctorNotes/{loggedInUserId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> addDoctorNotes(
			@Valid @RequestBody DoctorNotesObj doctorNotesObj, BindingResult bindingResult,
			@PathVariable("loggedInUserId") String loggedInUserId) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		if (bindingResult.hasErrors()) {
			return BasicUtils.handleInvalidInputJson(doctorNotesObj, bindingResult);
		} else {
			try {
				// DoctorNotesObj doctorNotesObj =
				// convertJasonToDocorNotesObj(doctorNotesJsonStr);
				response = (ResponseMessageWithResponseObject) patientService.addDoctorNotes(doctorNotesObj,
						loggedInUserId);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveDoctorNotesData/{loggedInUserId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveDoctorNotes(
			@Valid @RequestBody DashboardJSON doctorNotesObj, BindingResult bindingResult,
			@PathVariable("loggedInUserId") String loggedInUserId) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		if (bindingResult.hasErrors()) {
			return BasicUtils.handleInvalidInputJson(doctorNotesObj, bindingResult);
		} else {
			try {
				// DoctorNotesObj doctorNotesObj =
				// convertJasonToDocorNotesObj(doctorNotesJsonStr);
				response = (ResponseMessageWithResponseObject) patientService.saveDoctorNotes(doctorNotesObj,
						loggedInUserId);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDoctorNotes/{patientId}/{DOB}/{DOA}/{sex}/{entryDate}/{doctorId}/{noteEntryTime}", method = RequestMethod.POST)
	public ResponseEntity<Object> getDoctorNotes(@PathVariable("patientId") String uhid,
			@PathVariable("DOB") String dob, @PathVariable("DOA") String doa, @PathVariable("sex") String sex,
			@PathVariable("doctorId") String doctorId, @PathVariable("entryDate") String entryDate,
			@PathVariable("noteEntryTime") String noteEntryTime) {
		Object doctorNotes = null;
		try {
			doctorNotes = patientService.getDoctorNotes(uhid, dob, doa, sex, doctorId, entryDate, noteEntryTime);
			System.out.println(" ##################### Json Returned for doctor notes as:  #####################\n"
					+ BasicUtils.getObjAsJson(doctorNotes));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(doctorNotes, HttpStatus.OK);
	}

	// this is for just for doctor notes
	@RequestMapping(value = "/inicu/getDoctorNotesPrint", method = RequestMethod.POST)
	public ResponseEntity<DoctorNotesPOJO> getDoctorNotesAdvanced(
			@RequestBody DoctorNotesPrintInfoPOJO doctorNotesPrintInfo) {
		DoctorNotesPOJO doctorNotesInfo = new DoctorNotesPOJO();
		try {
			doctorNotesInfo = notesService.getDoctorNotes(doctorNotesPrintInfo);
			System.out.println(" ##################### Json Returned for doctor notes as:  #####################\n"
					+ BasicUtils.getObjAsJson(doctorNotesInfo));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<DoctorNotesPOJO>(doctorNotesInfo, HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	private DoctorNotesObj convertJasonToDocorNotesObj(String jsonStr) {
		DoctorNotesObj obj = new DoctorNotesObj();
		if (jsonStr != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				obj = mapper.readValue(jsonStr, DoctorNotesObj.class);
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	@RequestMapping(value = "/inicu/getNursingNotesDropDowns", method = RequestMethod.GET)
	public ResponseEntity<NursingNotesDropDownsObj> getNotesDropDowns() {
		NursingNotesDropDownsObj dropDowns = patientService.getNursingNotesDropDowns();
		System.out
				.println(" ##################### Json Returned for nursing notes dropdowns as:  #####################\n"
						+ BasicUtils.getObjAsJson(dropDowns));
		return new ResponseEntity<NursingNotesDropDownsObj>(dropDowns, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getBloodProductsInfo/{uhid}/{loggedUser}", method = RequestMethod.GET)
	public ResponseEntity<List<VwBloodProduct>> getBloodProductsInfo(@PathVariable("uhid") String uhid) {
		// patientService.savePupilReactivity(uhid, "", "", "", "");
		List<VwBloodProduct> bloodProductsList = null;
		if (uhid == null || uhid.isEmpty()) {
			return new ResponseEntity<List<VwBloodProduct>>(bloodProductsList, HttpStatus.BAD_REQUEST);
		} else {
			bloodProductsList = patientService.getBloodProductsInfo(uhid, "");
			return new ResponseEntity<List<VwBloodProduct>>(bloodProductsList, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/inicu/addBloodProduct/{loggedInUserId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> addBloodProduct(
			@Valid @RequestBody VwBloodProduct bloodProduct, BindingResult bindingResult,
			@PathVariable("loggedInUserId") String loggedInUserId) {
		ResponseMessageWithResponseObject response = null;
		try {
			if (bindingResult.hasErrors()) {
				return BasicUtils.handleInvalidInputJson(bloodProduct, bindingResult);
			} else {
				if (bloodProduct != null) {
					response = patientService.addBloodProduct(bloodProduct, loggedInUserId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDoctorNotesDropDowns", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, List<String>>> getDoctorNotesDropDowns() {
		return new ResponseEntity<HashMap<String, List<String>>>(patientService.getDoctorNotesDropDowns(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDoctorNotesGraph/{dob}/{todayDate}/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<List<HashMap<String, String>>> getDoctorNotesGraph(@PathVariable("uhid") String uhid,
			@PathVariable("todayDate") String date, @PathVariable("dob") String dob) {
		return new ResponseEntity<List<HashMap<String, String>>>(patientService.getDoctorNotesGraph(uhid, date, dob),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/printDoctorNotes", method = RequestMethod.POST)
	public ResponseEntity<HashMap<Object, Object>> printDoctorNotes(@RequestBody AssessmentPrintInfoObject printObj) {
		return new ResponseEntity<HashMap<Object, Object>>(patientService.printDoctorNotes(printObj), HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingObservationInfo/{type}/{entryDate}/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<NursingObservationPojo> getNursingObservationInfo(@PathVariable("type") String tabType,
			@PathVariable("entryDate") String entryDate, @PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedInUser) {
		return new ResponseEntity<NursingObservationPojo>(
				notesService.getNursingObservationInfo(tabType, entryDate, uhid, loggedInUser, null), HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingDailyProgressInfo/{entryDate}/{dob}/{doa}/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<NursingDailyProgressPojo> getNursingDailyProgressInfo(
			@PathVariable("entryDate") String entryDate, @PathVariable("uhid") String uhid,
			@PathVariable("dob") String dob, @PathVariable("doa") String doa,
			@PathVariable("loggedInUser") String loggedInUser) {
		NursingDailyProgressPojo anthropometry = notesService.getAnthropometry(uhid);
		/* anthropometry.getBabyVisit().setLoggeduser(loggedInUser); */
		return new ResponseEntity<NursingDailyProgressPojo>(anthropometry, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addNursingObservationInfo/{type}/{entryDate}/{uhid}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> setNursingObservationInfo(
			@RequestBody NursingObservationPojo nursingInfo, @PathVariable("type") String type,
			@PathVariable("uhid") String uhid, @PathVariable("entryDate") String entryDate) {
		try {
			return new ResponseEntity<ResponseMessageWithResponseObject>(
					notesService.setNursingObservationInfo(type, nursingInfo, uhid, entryDate), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/inicu/nursingDropDowns", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageWithResponseObject> setNursingDropDowns() {
		return new ResponseEntity<ResponseMessageWithResponseObject>(notesService.setNursingDropDowns(), HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addBabyVisit/{entryDate}/{uhid}/{userName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveBabyVisit(
			@RequestBody NursingDailyProgressPojo babyVisit, @PathVariable("uhid") String uhid,
			@PathVariable("entryDate") String entryDate, @PathVariable("userName") String userName) {
		return new ResponseEntity<ResponseMessageWithResponseObject>(
				notesService.saveBabyVisit(babyVisit, uhid, entryDate, userName), HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAllModulesNursingNotes/{date}/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<NursingObservationPojoAll> getAllModuleNursingNotes(@PathVariable("date") String date,
			@PathVariable("uhid") String uhid) {

		return new ResponseEntity<NursingObservationPojoAll>(notesService.getModuleNursingNotesAll(date, uhid),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getTpnFeedDetails/{uhid}/{loggedUser}", method = RequestMethod.GET)
	public ResponseEntity<TpnFeedPojo> getTpnFeedDetails(@PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {
		TpnFeedPojo tpnObj = notesService.getTpnFeedDetails(uhid, loggedUser);
		List<KeyValueObj> imageData = null;

		String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
		List<BabyDetail> babyDetails = inicuDao.getListFromMappedObjQuery(query);
		String branchName = "";
		if (!BasicUtils.isEmpty(babyDetails)) {
			branchName = babyDetails.get(0).getBranchname();
		}
		imageData = settingService.getHospitalLogo(branchName);
		tpnObj.setLogoImage(imageData);
		return new ResponseEntity<TpnFeedPojo>(tpnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveTpnFeedDetails/{uhid}/{loggedUser}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveTpnFeedDetails(@RequestBody TpnFeedPojo tpnFeedObj,
			@PathVariable("uhid") String uhid, @PathVariable("loggedUser") String loggedUser) {

		return new ResponseEntity<ResponseMessageWithResponseObject>(
				notesService.saveTpnFeedDetails(tpnFeedObj, uhid, loggedUser), HttpStatus.OK);
		
	}

	@RequestMapping(value = "/inicu/getBabyHistory/{uhid}/{loggedUser}", method = RequestMethod.GET)
	public ResponseEntity<BabyHistoryObj> getBabyHistory(@PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {

		BabyHistoryObj babyHistory = notesService.getBabyHistory(uhid, loggedUser);
		return new ResponseEntity<BabyHistoryObj>(babyHistory, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAssessmentNursingOrder/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<AssessmentNursingOrderPOJO> getAssessmentNursingOrder(@PathVariable("uhid") String uhid) {
		AssessmentNursingOrderPOJO babyHistory = notesService.getAssessmentNursingOrder(uhid);
		return new ResponseEntity<AssessmentNursingOrderPOJO>(babyHistory, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveAssessmentNursingOrder/{uhid}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveAssessmentNursingOrder(
			@RequestBody AssessmentNursingOrderPOJO nursingOrderObj, @PathVariable("uhid") String uhid) {
		ResponseMessageWithResponseObject returnObj = notesService.saveAssessmentNursingOrder(uhid, nursingOrderObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

	// merged to AssessmentNursingOrder on 20th June 2017 by Sourabh Verma
	/*
	 * @RequestMapping(value = "/inicu/getJaundiceNursingOrder/{uhid}", method =
	 * RequestMethod.GET) public ResponseEntity<NursingOrderJaundiceJSON>
	 * getJaundiceNursingOrder(@PathVariable("uhid") String uhid) {
	 * NursingOrderJaundiceJSON babyHistory =
	 * notesService.getJaundiceNursingOrder(uhid); return new
	 * ResponseEntity<NursingOrderJaundiceJSON>(babyHistory, HttpStatus.OK); }
	 * 
	 * @RequestMapping(value = "/inicu/saveJaundiceNursingOrder/{uhid}", method =
	 * RequestMethod.POST) public ResponseEntity<ResponseMessageWithResponseObject>
	 * saveJaundiceNursingOrder(
	 * 
	 * @RequestBody NursingOrderJaundiceJSON nursingOrderObj, @PathVariable("uhid")
	 * String uhid) { ResponseMessageWithResponseObject returnObj =
	 * notesService.saveJaundiceNursingOrder(uhid, nursingOrderObj); return new
	 * ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	 * }
	 * 
	 * @RequestMapping(value = "/inicu/getRdsNursingOrder/{uhid}", method =
	 * RequestMethod.GET) public ResponseEntity<NursingOrderRdJSON>
	 * getRdNursingOrder(@PathVariable("uhid") String uhid) { NursingOrderRdJSON
	 * babyHistory = notesService.getRdNursingOrder(uhid); return new
	 * ResponseEntity<NursingOrderRdJSON>(babyHistory, HttpStatus.OK); }
	 * 
	 * @RequestMapping(value = "/inicu/saveRdsNursingOrder/{uhid}", method =
	 * RequestMethod.POST) public ResponseEntity<ResponseMessageWithResponseObject>
	 * saveRdsNursingOrder(
	 * 
	 * @RequestBody NursingOrderRdJSON nursingOrderObj, @PathVariable("uhid") String
	 * uhid) { ResponseMessageWithResponseObject returnObj =
	 * notesService.saveRdNursingOrder(uhid, nursingOrderObj); return new
	 * ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	 * }
	 * 
	 * @RequestMapping(value = "/inicu/getApneaNursingOrder/{uhid}", method =
	 * RequestMethod.GET) public ResponseEntity<NursingOrderApneaJSON>
	 * getApneaNursingOrder(@PathVariable("uhid") String uhid) {
	 * NursingOrderApneaJSON babyHistory = notesService.getApneaNursingOrder(uhid);
	 * return new ResponseEntity<NursingOrderApneaJSON>(babyHistory, HttpStatus.OK);
	 * }
	 * 
	 * @RequestMapping(value = "/inicu/saveApneaNursingOrder/{uhid}", method =
	 * RequestMethod.POST) public ResponseEntity<ResponseMessageWithResponseObject>
	 * saveApneaNursingOrder(
	 * 
	 * @RequestBody NursingOrderApneaJSON nursingOrderObj, @PathVariable("uhid")
	 * String uhid) { ResponseMessageWithResponseObject returnObj =
	 * notesService.saveApneaNursingOrder(uhid, nursingOrderObj); return new
	 * ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	 * }
	 */

	//	GET THE NURSING VITAL PARAMETERS FOR PATIENT OUTPUT CHART
	@RequestMapping(value="/inicu/getPatientVitalParameters/{uhid}/{fromDate}/{toDate}/{branchName}/",method=RequestMethod.GET)
	public ResponseEntity<VitalParamtersResponse> getVitalParamteters(@PathVariable("uhid") String uhid,
																	  @PathVariable("fromDate") String fromDate,
																	  @PathVariable("toDate") String toDate,
																	  @PathVariable("branchName") String branchName){
		VitalParamtersResponse returnObject=notesService.getPatientChartByUhid(uhid,fromDate,toDate);
		return new ResponseEntity<VitalParamtersResponse>(returnObject, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveNursingEpisode/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveNursingEpisode(
			@RequestBody NursingEpisode nursingEpisodeObj) {
		ResponseMessageWithResponseObject returnObj = notesService.saveNursingEpisode(nursingEpisodeObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveDeclinedApneaEvent/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveDeclinedApneaEvent(
			@RequestBody DeclinedApneaEvent declinedApneaEvent) {
		ResponseMessageWithResponseObject returnObj = notesService.saveDeclinedApneaEvent(declinedApneaEvent);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getNursingInputs/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingInputsPOJO> getNursingInputs(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingInputsPOJO returnObj = notesService.getNursingInputs(uhid, fromTime, toTime);
		return new ResponseEntity<NursingInputsPOJO>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveNursingInputs/{uhid}/{fromTime}/{toTime}/{loggedUser}", method = RequestMethod.POST)
	public ResponseEntity<NursingInputsPOJO> saveNursingInputs(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime,
			@PathVariable("loggedUser") String loggedUser, @RequestBody NursingIntakeOutput currentNursingObj) {
		NursingInputsPOJO returnObj = notesService.saveNursingInputs(uhid, fromTime, toTime, loggedUser,
				currentNursingObj);
		return new ResponseEntity<NursingInputsPOJO>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getBabyBloodGasInfoBySampleId/{uhid}/{orderId}/{loggedUser}", method = RequestMethod.GET)
	public ResponseEntity<NursingBloodGas> getBabyBloodGasInfoBySampleId(@PathVariable("uhid") String uhid,
			@PathVariable("orderId") String orderID, @PathVariable("loggedUser") String loggedUser) {
		NursingBloodGas bloodGasInfo = notesService.getBabyBloodGasInfoBySampleId(uhid, orderID);
		return new ResponseEntity<NursingBloodGas>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingVitalPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingVitalPrintJSON> getNursingVitalPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingVitalPrintJSON bloodGasInfo = notesService.getNursingVitalPrint(uhid, fromTime, toTime);
		return new ResponseEntity<NursingVitalPrintJSON>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAnthropometryPrint/{uhid}/{fromTime}/{toTime}", method = RequestMethod.GET)
	public ResponseEntity<AnthropometryPrintJSON> getAnthropometryPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		AnthropometryPrintJSON bloodGasInfo = notesService.getAnthropometryPrint(uhid, fromTime, toTime);
		return new ResponseEntity<AnthropometryPrintJSON>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingEventPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingEventPrintJSON> getNursingEventPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingEventPrintJSON bloodGasInfo = notesService.getNursingEventPrint(uhid, fromTime, toTime);
		return new ResponseEntity<NursingEventPrintJSON>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingVentilatorPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingVentilatorPrintJSON> getNursingVentilatorPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingVentilatorPrintJSON bloodGasInfo = notesService.getNursingVentilatorPrint(uhid, fromTime, toTime);
		return new ResponseEntity<NursingVentilatorPrintJSON>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingBGPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingBGPrintJSON> getNursingBGPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingBGPrintJSON bloodGasInfo = notesService.getNursingBGPrint(uhid, fromTime, toTime);
		return new ResponseEntity<NursingBGPrintJSON>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingIntakeOutputPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingIntakeOutputPrintJSON> getNursingIntakeOutputPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingIntakeOutputPrintJSON bloodGasInfo = notesService.getNursingIntakeOutputPrint(uhid, fromTime, toTime);
		return new ResponseEntity<NursingIntakeOutputPrintJSON>(bloodGasInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingNotes/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<NursingNotesPojo> getNursingNotes(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		NursingNotesPojo returnObj = notesService.getAdvNursingNotes(uhid, fromTime, toTime);
		return new ResponseEntity<NursingNotesPojo>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setNursingNotes/", method = RequestMethod.POST)
	public ResponseEntity<NursingNotesPojo> setNursingNotes(@RequestBody NursingNotesPojo nursingNotesObj) {
		NursingNotesPojo returnObj = notesService.setNursingNotes(nursingNotesObj);
		return new ResponseEntity<NursingNotesPojo>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getVitalInfoByDate/{uhid}/{entryDate}", method = RequestMethod.GET)
	public ResponseEntity<NursingVitalparameter> getVitalInfoByDate(@PathVariable("uhid") String uhid,
			@PathVariable("entryDate") String entryDate) {
		NursingVitalparameter vitalData = notesService.getVitalByDate(uhid, entryDate);
		return new ResponseEntity<NursingVitalparameter>(vitalData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getRoundAndAssessmentData/{uhid}/{entryDate}", method = RequestMethod.GET)
	public ResponseEntity<List<NursingVitalparameter>> getRoundAndAssessmentData(@PathVariable("uhid") String uhid,
			@PathVariable("entryDate") String entryDate) {
		List<NursingVitalparameter> vitalData = notesService.getRoundAndAssessmentData(uhid, entryDate);
		return new ResponseEntity<List<NursingVitalparameter>>(vitalData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getVentilatorInfoByDate/{uhid}/{entryDate}", method = RequestMethod.GET)
	public ResponseEntity<NursingVentilaor> getVentilatorInfoByDate(@PathVariable("uhid") String uhid,
			@PathVariable("entryDate") String entryDate) {
		NursingVentilaor ventilatorData = notesService.getVentilatorInfoByDate(uhid, entryDate);
		return new ResponseEntity<NursingVentilaor>(ventilatorData, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getAllNursingData/{uhid}/{fromTime}/{toTime}/{branchname}", method = RequestMethod.GET)
	public ResponseEntity<NursingAllDataPOJO> getAllNursingData(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime, @PathVariable("branchname") String branchname) {
		NursingAllDataPOJO returnObj = notesService.getAllNursingData(uhid, fromTime, toTime,branchname);
		// call to add the hospital logo to the nursing data
		List<KeyValueObj> imageData = null;

		String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
		List<BabyDetail> babyDetails = inicuDao.getListFromMappedObjQuery(query);
		String branchName = "";
		if (!BasicUtils.isEmpty(babyDetails)) {
			branchName = babyDetails.get(0).getBranchname();
		}
		imageData = settingService.getHospitalLogo(branchName);
		returnObj.setLogoImage(imageData);
		return new ResponseEntity<NursingAllDataPOJO>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDailyProgressNotes/{uhid}/{dateStr}/{fromTime}/{toTime}/{loggedInUser}/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<DailyProgressNotes> getDailyProgressNotes(@PathVariable("uhid") String uhid,
			@PathVariable("dateStr") String dateStr, @PathVariable("fromTime") String fromTime,
			@PathVariable("toTime") String toTime, @PathVariable("loggedInUser") String loggedInUser, @PathVariable("branchName") String branchName) {
		DailyProgressNotes returnObj = notesService.getDailyProgressNotes(uhid, dateStr, fromTime, toTime, branchName, loggedInUser);
		List<KeyValueObj> imageData = null;
        List<KeyValueObj> signatureImageData = null;
		imageData = settingService.getHospitalLogo(branchName);
        try {
            signatureImageData = userPanel.getImageData(loggedInUser, branchName);
            returnObj.setSignatureImage(signatureImageData);
        } catch (Exception e) {
            e.printStackTrace();
        }
		returnObj.setLogoImage(imageData);
		return new ResponseEntity<DailyProgressNotes>(returnObj, HttpStatus.OK);
	}

	// get the list of date and loggged user for all edited progress present for particular baby
	@RequestMapping(value="/inicu/getEditedNotesList/{uhid}/{branchname}/{fromwhere}",method = RequestMethod.GET)
	public ResponseEntity<GeneralResponseObject> getEditedNotesList(@PathVariable("uhid") String uhid,
																	@PathVariable("branchname") String branchname,
																	@PathVariable("fromwhere") String fromwhere){
		GeneralResponseObject returnObj=new GeneralResponseObject();
		try {
			returnObj=	notesService.getDoctorEditedNotesList(uhid, branchname, fromwhere);
		}catch (Exception e){
			returnObj=BasicUtils.getResonseObject(false,500,"Internal Server error",null);
			e.printStackTrace();
		}
		return new ResponseEntity<GeneralResponseObject>(returnObj, HttpStatus.OK);
	}

	// Controller to get the edited progress notes
	@RequestMapping(value="/inicu/getEditedDoctorProgressNote/{uhid}/{dateStr}/{loggedUser}/{branchName}/{fromwhere}",method = RequestMethod.GET)
	public ResponseEntity<GeneralResponseObject> getEditedDoctorNotes(@PathVariable("uhid") String uhid,
																	  @PathVariable("dateStr") String dateString,
																	  @PathVariable("loggedUser") String loggedUser,
																	  @PathVariable("branchName") String branchName,
																	  @PathVariable("fromwhere") String fromwhere){
		GeneralResponseObject returnObj=new GeneralResponseObject();
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date mydate=new Date(Long.parseLong(dateString));
			Timestamp timestamp = new Timestamp(mydate.getTime());

			returnObj=	notesService.getEditedDoctorNotes(uhid, timestamp, branchName, loggedUser, fromwhere);
		}catch (Exception e){
			returnObj=BasicUtils.getResonseObject(false,500,"Internal Server error",null);
			e.printStackTrace();
		}

		return new ResponseEntity<GeneralResponseObject>(returnObj, HttpStatus.OK);
	}


	// Controller to handle the post request of the edited progress notes
	@RequestMapping(value="/inicu/saveEditedDoctorProgressNote/",method = RequestMethod.POST)
	public ResponseEntity<GeneralResponseObject> saveEditedDoctorNotes(@RequestBody EditedDoctorNotes editedDoctorNotes){
		GeneralResponseObject returnObj=new GeneralResponseObject();
		try {
			returnObj=	notesService.saveEditedDoctorNotes(editedDoctorNotes);
		}catch (Exception e){
			returnObj=BasicUtils.getResonseObject(false,500,"Internal Server error",null);
			e.printStackTrace();
		}
		return new ResponseEntity<GeneralResponseObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setDailyProgressNotes/", method = RequestMethod.POST)
	public ResponseEntity<DailyProgressNotes> setDailyProgressNotes(@RequestBody DailyProgressNotes progressNote) {
		DailyProgressNotes returnObj = notesService.setDailyProgressNotes(progressNote);
		return new ResponseEntity<DailyProgressNotes>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDoctorBloodProducts/{uhid}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<DoctorBloodProductPojo> getDoctorBloodProducts(@PathVariable("uhid") String uhid, @PathVariable("branchName") String branchName) {
		DoctorBloodProductPojo returnObj = notesService.getDoctorBloodProducts(uhid, branchName);
		return new ResponseEntity<DoctorBloodProductPojo>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setDoctorBloodProducts/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> setDoctorBloodProducts(@RequestBody DoctorBloodProducts currentObj, @PathVariable("branchName") String branchName) {
		ResponseMessageWithResponseObject returnObj = notesService.setDoctorBloodProducts(currentObj, branchName);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getDoctorOrders/{uhid}/{dateStr}/", method = RequestMethod.GET)
	public ResponseEntity<DoctorOrdersPOJO> getDoctorOrders(@PathVariable("uhid") String uhid,
			@PathVariable("dateStr") String dateStr) {
		DoctorOrdersPOJO returnObj = notesService.getDoctorOrders(uhid, dateStr);
		return new ResponseEntity<DoctorOrdersPOJO>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getCgaAndDol/{uhid}/{dateString}/", method = RequestMethod.GET)
	public ResponseEntity<CgaAndDolPOPJO> getCgaAndDol(@PathVariable("uhid") String uhid,
													  @PathVariable("dateString") String dateString) {
		CgaAndDolPOPJO returnObj = notesService.getCgaAndDol(uhid, dateString);
		return new ResponseEntity<CgaAndDolPOPJO>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getNursingAnthropometryCsvData/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<List<ExportAnthropometryCsvData>> getDashboardExportData(@PathVariable("uhid") String uhid) {
		List<ExportAnthropometryCsvData> result = notesService.getAnthropometryCsvData(uhid);
		return new ResponseEntity<List<ExportAnthropometryCsvData>>(result, HttpStatus.OK);

	}

	@RequestMapping(value="/inicu/updatedPnRate/{uhid}/{updateStatus}/",method = RequestMethod.POST)
	public ResponseEntity<GeneralResponseObject> getPnRate (@PathVariable("uhid") String uhid,
															@PathVariable("updateStatus") int updateStatus,
															@RequestBody DoctorBloodProducts currentObj){
		GeneralResponseObject generalResponseObject=new GeneralResponseObject();
		try {
			float currentRate=notesService.getCalculatedPNRate(uhid,updateStatus,currentObj);
			currentRate=BasicUtils.round(currentRate,2);
			if(currentRate!=0) {
				generalResponseObject = BasicUtils.getResonseObject(true, 200, "Updated PN Rate", currentRate);
			}else{
				generalResponseObject = BasicUtils.getResonseObject(false, 304, "No feed entry found", currentRate);
			}
		} catch (Exception e) {
			generalResponseObject=BasicUtils.getResonseObject(false,500,"Internal server error",null);
			e.printStackTrace();
		}
		return new ResponseEntity<GeneralResponseObject>(generalResponseObject,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/deleteNursingNotes/{uhid}/{nursingNotesId}/", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject>  deleteNursingNotes(@PathVariable("uhid") String uhid,
			@PathVariable("nursingNotesId") Long nursingNotesId) {
		ResponseMessageObject returnObj = notesService.deleteNursingNotes(uhid, nursingNotesId);
		return new ResponseEntity<ResponseMessageObject> (returnObj, HttpStatus.OK);
	}

}
