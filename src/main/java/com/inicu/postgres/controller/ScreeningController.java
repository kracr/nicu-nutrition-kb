package com.inicu.postgres.controller;

import com.inicu.models.*;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.SettingsService;
import com.inicu.postgres.utility.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inicu.postgres.service.ScreeningService;

import java.util.List;

@Controller
public class ScreeningController {
	@Autowired
	private SettingsService settingService;

	@Autowired
	ScreeningService screeningService;

	@Autowired
	InicuDao inicuDao;

	@RequestMapping(value = "/inicu/getNeurological/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<NeurologicalMasterPojo> getNeurological(@PathVariable("uhid") String uhid) {
		NeurologicalMasterPojo neurological = screeningService.getNeurological(uhid);
		List<KeyValueObj> imageData = null;

		String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
		List<BabyDetail> babyDetails = inicuDao.getListFromMappedObjQuery(query);
		String branchName = "";
		if (!BasicUtils.isEmpty(babyDetails)) {
			branchName = babyDetails.get(0).getBranchname();
		}
		imageData = settingService.getHospitalLogo(branchName);
		neurological.setLogoImage(imageData);
		return new ResponseEntity<NeurologicalMasterPojo>(neurological, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveNeurological/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveNeurological(
			@RequestBody ScreenNeurological neurologicalObj, @PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject response = screeningService.saveNeurological(uhid, loggedUser,
				neurologicalObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getScreeningNeurologicalPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<ScreeningNeurologicalPrintJSON> getScreeningNeurologicalPrint(
			@PathVariable("uhid") String uhid, @PathVariable("fromTime") String fromTime,
			@PathVariable("toTime") String toTime) {
		ScreeningNeurologicalPrintJSON neurologicalPrint = screeningService.getScreeningNeurologicalPrint(uhid,
				fromTime, toTime);
		return new ResponseEntity<ScreeningNeurologicalPrintJSON>(neurologicalPrint, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getHearing/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<HearingMasterPojo> getHearing(@PathVariable("uhid") String uhid) {
		HearingMasterPojo hearing = screeningService.getHearing(uhid);
		return new ResponseEntity<HearingMasterPojo>(hearing, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveHearing/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveHearing(@RequestBody ScreenHearing HearingObj,
			@PathVariable("uhid") String uhid, @PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject response = screeningService.saveHearing(uhid, loggedUser, HearingObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getScreeningHearingPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<ScreeningHearingPrintJSON> getScreeningHearingPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		ScreeningHearingPrintJSON hearingPrint = screeningService.getScreeningHearingPrint(uhid, fromTime, toTime);
		return new ResponseEntity<ScreeningHearingPrintJSON>(hearingPrint, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getRop/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<RopMasterPojo> getRop(@PathVariable("uhid") String uhid) {
		RopMasterPojo rop = screeningService.getRop(uhid);
		return new ResponseEntity<RopMasterPojo>(rop, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveRop/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveRop(
			@PathVariable("uhid") String uhid, @PathVariable("loggedUser") String loggedUser,@RequestBody ScreenRop ropObj) {
		ResponseMessageWithResponseObject response = screeningService.saveRop(uhid, loggedUser, ropObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getScreeningRopPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<ScreeningRopPrintJSON> getScreeningRopPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		ScreeningRopPrintJSON hearingPrint = screeningService.getScreeningRopPrint(uhid, fromTime, toTime);
		return new ResponseEntity<ScreeningRopPrintJSON>(hearingPrint, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getUSG/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<USGMasterPojo> getUSG(@PathVariable("uhid") String uhid) {
		USGMasterPojo usg = screeningService.getUSG(uhid);
		return new ResponseEntity<USGMasterPojo>(usg, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveUSG/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveUSG(@RequestBody ScreenUSG usgObj,
			@PathVariable("uhid") String uhid, @PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject response = screeningService.saveUSG(uhid, loggedUser, usgObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getScreeningUSGPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<ScreeningUSGPrintJSON> getScreeningUSGPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		ScreeningUSGPrintJSON usgPrint = screeningService.getScreeningUSGPrint(uhid, fromTime, toTime);
		return new ResponseEntity<ScreeningUSGPrintJSON>(usgPrint, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getMetabolic/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<MetabolicMasterPojo> getMetabolic(@PathVariable("uhid") String uhid) {
		MetabolicMasterPojo metabolic = screeningService.getMetabolic(uhid);
		return new ResponseEntity<MetabolicMasterPojo>(metabolic, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveMetabolic/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveMetabolic(@RequestBody MetabolicMasterPojo metabolicObj,
			@PathVariable("uhid") String uhid, @PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject response = screeningService.saveMetabolic(uhid, loggedUser, metabolicObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getScreeningMetabolicPrint/{uhid}/{fromTime}/{toTime}/", method = RequestMethod.GET)
	public ResponseEntity<ScreeningMetabolicPrintJSON> getScreeningMetabolicPrint(@PathVariable("uhid") String uhid,
			@PathVariable("fromTime") String fromTime, @PathVariable("toTime") String toTime) {
		ScreeningMetabolicPrintJSON metabolicPrint = screeningService.getScreeningMetabolicPrint(uhid, fromTime,
				toTime);
		return new ResponseEntity<ScreeningMetabolicPrintJSON>(metabolicPrint, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getMiscellaneous/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<MiscellaneousMasterPOJO> getMiscellaneous(@PathVariable("uhid") String uhid) {
		MiscellaneousMasterPOJO miscellaneous = screeningService.getMiscellaneous(uhid);
		//List<KeyValueObj> imageData = null;
		//imageData = settingService.getHospitalLogo();
		//neurological.setLogoImage(imageData);
		return new ResponseEntity<MiscellaneousMasterPOJO>(miscellaneous, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveMisc/{uhid}/{loggedUser}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveMiscellanoues(
			@RequestBody ScreenMiscellaneous miscellaneousObj, @PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {
		ResponseMessageWithResponseObject response = screeningService.saveMiscellaneous(uhid, loggedUser,
				miscellaneousObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

}
