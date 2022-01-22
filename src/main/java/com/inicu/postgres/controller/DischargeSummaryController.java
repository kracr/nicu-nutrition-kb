package com.inicu.postgres.controller;

/*--
 * Author:- iNICU 
 */
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inicu.models.DischargeSummaryAdvancedMasterObj;
import com.inicu.models.OutcomeNotesPOJO;
import com.inicu.models.OutcomesNotesDeathPOJO;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.postgres.entities.DischargeOutcome;
import com.inicu.postgres.entities.OutcomeNotes;
import com.inicu.postgres.entities.VwVaccinationsPatient;
import com.inicu.postgres.service.DischargeSummaryService;
import com.inicu.postgres.utility.BasicUtils;

@Controller
public class DischargeSummaryController {

	@Autowired
	DischargeSummaryService dischargeSummaryService;

	@RequestMapping(value = "/inicu/getDischargeSummaryAdvanced/{uhid}/{moduleName}/{loggedUser}", method = RequestMethod.GET)
	public ResponseEntity<DischargeSummaryAdvancedMasterObj> getDischargeSummarAdvanced(
			@PathVariable("uhid") String uhid, @PathVariable("loggedUser") String loggedUser,
			@PathVariable("moduleName") String moduleName) {

		DischargeSummaryAdvancedMasterObj dishargeSummary = dischargeSummaryService.getDishchargedSummaryAdvanced(uhid,
				moduleName, loggedUser);
		return new ResponseEntity<DischargeSummaryAdvancedMasterObj>(dishargeSummary, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveDischargeSummaryAdvanced/{uhid}/{moduleName}/{loggedUser}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveDischargeSummarAdvanced(
			@RequestBody DischargeSummaryAdvancedMasterObj dischargeSummaryAdvancedMasterObj,
			@PathVariable("moduleName") String moduleName, @PathVariable("loggedUser") String loggedUser,
			@PathVariable("uhid") String uhid) {

		ResponseMessageWithResponseObject response = dischargeSummaryService.saveDishchargedSummaryAdvanced(uhid,
				moduleName, dischargeSummaryAdvancedMasterObj, loggedUser);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/savePatientVaccination/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> savePatientVaccination(
			@RequestBody VwVaccinationsPatient patientVaccinationObj) {
		ResponseMessageWithResponseObject response = dischargeSummaryService
				.savePatientVaccination(patientVaccinationObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getDichargeOutCome/{uhid}/{episodeid}/", method = RequestMethod.GET)
	public ResponseEntity<DischargeOutcome> getDichargeOutCome(@PathVariable("uhid")String uhid, @PathVariable("episodeid")String episodeid) {
		DischargeOutcome outcome = dischargeSummaryService.getDichargeOutCome(uhid,episodeid);
		return new ResponseEntity<DischargeOutcome>(outcome, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveDichargeOutCome/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveDichargeOutCome(@Valid@RequestBody DischargeOutcome outcome,BindingResult validationResult) {
		if(validationResult.hasErrors()){
			return BasicUtils.handleInvalidInputJson(outcome, validationResult);
		}else{
			ResponseMessageWithResponseObject response = dischargeSummaryService.saveDichargeOutCome(outcome);
			return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
		}
	}
	
	
	// done by ekpreet 
	@RequestMapping(value = "/inicu/getOutcomeNotes/{uhid}/{outcomeType}", method = RequestMethod.GET)
	public ResponseEntity<OutcomeNotesPOJO> getOutComeNotes(@PathVariable("uhid")String uhid , @PathVariable("outcomeType") String outcomeType) {
		OutcomeNotesPOJO outcome = dischargeSummaryService.getOutcomeNotes(uhid,outcomeType);
		return new ResponseEntity<OutcomeNotesPOJO>(outcome, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveOutcomeNotes/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveOutcomeNotes(@Valid@RequestBody OutcomeNotes outcome) {
		
			ResponseMessageWithResponseObject response = dischargeSummaryService.saveOutcomeNotes(outcome);
			return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/inicu/getOutcomeNotesDeath/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<OutcomesNotesDeathPOJO> getOutcomeNotesDeath(@PathVariable("uhid")String uhid) {
		OutcomesNotesDeathPOJO outcome = dischargeSummaryService.getOutcomeNotesDeath(uhid);
		return new ResponseEntity<OutcomesNotesDeathPOJO>(outcome, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/inicu/saveOutcomeNotesDeath/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveOutcomeNotesDeath(@Valid@RequestBody OutcomesNotesDeathPOJO outcome) {
		
			ResponseMessageWithResponseObject response = dischargeSummaryService.saveOutcomeNotesDeath(outcome);
			return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
		
	}
	
	@ExceptionHandler(Exception.class)
  protected ResponseEntity<String> handleControllerException(Exception ex){

		ex.printStackTrace();
    return ResponseEntity.badRequest().body(ex.getMessage());
  }


}
