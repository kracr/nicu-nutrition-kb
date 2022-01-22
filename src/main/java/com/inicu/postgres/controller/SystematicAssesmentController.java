package com.inicu.postgres.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.postgres.service.SystematicService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@RestController
public class SystematicAssesmentController {

	@Autowired
	SystematicService sysService;
	public String loggedUser = "";

	
	//HS - work in progress
	@RequestMapping(value="/inicu/getJaundiceInvestigationsRecd/{uhid}/{date}", method = RequestMethod.GET)
	public List<InvestigationOrdered> getJaundiceInvestigationOrdered(@PathVariable("uhid") String searchUhid, @PathVariable("date") String searchDate) {
		return sysService.getJaundiceInvestigationOrdered(searchUhid,searchDate);
	}
	
	@RequestMapping(value = "/sys/jaundice/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<SysJaundJSON> getJaundiceAsses(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		SysJaundJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getJaundice(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SysJaundJSON>(json, HttpStatus.OK);
	}


	@RequestMapping(value = "inicu/assessment/saveMetabolicSystem", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveMetabolicSystem(@RequestBody AssessmentMetabolicSystemPOJO metabolicSystemObj) {
		
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			if (!BasicUtils.isEmpty(metabolicSystemObj)) {
				response = sysService.saveMetabolicSystem(metabolicSystemObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveJaundice/", method = RequestMethod.POST)
	public ResponseEntity<SysJaundJSON> saveJaundice(@RequestBody SysJaundJSON jaundice) {
		SysJaundJSON json = null;
		ResponseMessageObject obj = null;
		try {
			if (!BasicUtils.isEmpty(jaundice)) {
				obj = sysService.saveJaundice(jaundice, jaundice.getUserId());
				json = sysService.getJaundice(jaundice.getJaundice().getUhid(), jaundice.getUserId());
				json.setResponse(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SysJaundJSON>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sys/saveMiscellaneous/", method = RequestMethod.POST)
	public ResponseEntity<SaMiscellaneousJSON> saveMiscellaneous(@RequestBody SaMiscellaneousJSON miscellaneous) {
		SaMiscellaneousJSON json = null;
		ResponseMessageObject obj = null;
		try {
			if (!BasicUtils.isEmpty(miscellaneous)) {
				obj = sysService.saveMiscellaneous(miscellaneous, miscellaneous.getUserId());
				json = sysService.getMiscellaneous(miscellaneous.getMiscellaneous().getUhid(), miscellaneous.getUserId());
				json.setResponse(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SaMiscellaneousJSON>(json, HttpStatus.OK);
	}
	@RequestMapping(value = "/sys/saveMiscellaneous2/", method = RequestMethod.POST)
	public ResponseEntity<SaMiscellaneousJSON> saveMiscellaneous2(@RequestBody SaMiscellaneousJSON miscellaneous2) {
		SaMiscellaneousJSON json = null;
		ResponseMessageObject obj = null;
		try {
			if (!BasicUtils.isEmpty(miscellaneous2)) {
				obj = sysService.saveMiscellaneous2(miscellaneous2, miscellaneous2.getUserId());
				json = sysService.getMiscellaneous(miscellaneous2.getMiscellaneous2().getUhid(), miscellaneous2.getUserId());
				json.setResponse(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SaMiscellaneousJSON>(json, HttpStatus.OK);
	}
	@RequestMapping(value = "inicu/assessment/respSystem/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<AssessmentRespSystemPOJO> getRespSystem(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		AssessmentRespSystemPOJO resp = new AssessmentRespSystemPOJO();
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				resp = sysService.getRespSystem(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<AssessmentRespSystemPOJO>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "inicu/inoData/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<SaRespPphn> getInoData(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		SaRespPphn pphn = new SaRespPphn();
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				pphn = sysService.getInoData(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SaRespPphn>(pphn, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/cns/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<AssessmentCNSSystemPOJO> getCns(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser, @RequestBody String details) {

        String eventName = "";
        if (!BasicUtils.isEmpty(details)) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = (JsonObject) parser.parse(details);
            if (jsonObj.has("eventName")) {
                eventName = jsonObj.get("eventName").toString().replaceAll("\"","");
            }
        }
		AssessmentCNSSystemPOJO cnsObj = sysService.getCnsSystemObj(uhid, loggedUser, eventName);
		return new ResponseEntity<AssessmentCNSSystemPOJO>(cnsObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveCns/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveCnsSystemObj(
			@RequestBody AssessmentCNSSystemPOJO cnsSystemJson) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			if (!BasicUtils.isEmpty(cnsSystemJson)) {
				response = sysService.saveCnsSystem(cnsSystemJson);
				response.setReturnedObject(cnsSystemJson.getCNSEventObject().getEventName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/sepsis/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<SepsisMasterJSON> getSepsis(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		SepsisMasterJSON resp = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				resp = sysService.getSepsis(uhid.trim(), loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SepsisMasterJSON>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveSepsis/", method = RequestMethod.POST)
	public ResponseEntity<SepsisMasterJSON> saveSepsis(@RequestBody SepsisMasterJSON sepsisMaster) {
		SepsisMasterJSON masterJson = null;
		ResponseMessageObject response = null;
		try {
			if (!BasicUtils.isEmpty(sepsisMaster)) {

				response = sysService.saveSepsis(sepsisMaster.getSepsis(), sepsisMaster.getUserId());
				masterJson = sysService.getSepsis(sepsisMaster.getSepsis().getUhid(), sepsisMaster.getUserId());
				masterJson.setResponse(response);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		masterJson.setResponse(response);

		return new ResponseEntity<SepsisMasterJSON>(masterJson, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveResp/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveResp(
			@RequestBody AssessmentRespSystemPOJO respSystem) {

		ResponseMessageWithResponseObject resp = new ResponseMessageWithResponseObject();
		
		resp = sysService.saveRespSystem(respSystem);
		resp.setReturnedObject(respSystem.getRespSystemObject().getEventName());
		/*
		 * if(!bindingResult.hasErrors()){ return
		 * BasicUtils.handleInvalidInputJson(respSystem,bindingResult); }else{
		 */

	
		/* } */
		return new ResponseEntity<ResponseMessageWithResponseObject>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/cardiac/{uhid}/{loggedUser}", method = RequestMethod.POST)
	public ResponseEntity<CardiacMasterJSON> getCardiac(@PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {
		CardiacMasterJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getCardiac(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<CardiacMasterJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveCardiac/", method = RequestMethod.POST)
	public ResponseEntity<CardiacMasterJSON> saveCardiac(@RequestBody CardiacMasterJSON cardiacJson) {
		CardiacMasterJSON json = null;
		ResponseMessageObject response = null;
		try {
			if (!BasicUtils.isEmpty(cardiacJson)) {
				response = sysService.saveCardiac(cardiacJson.getCardiac(), cardiacJson.getUserId());
				json = sysService.getCardiac(cardiacJson.getCardiac().getUhid(), cardiacJson.getUserId());
				json.setResponse(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<CardiacMasterJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/feed/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<FeedingGrowthMasterJSON> getFeedGrowth(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		FeedingGrowthMasterJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getFeedGrowth(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<FeedingGrowthMasterJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveFeed/", method = RequestMethod.POST)
	public ResponseEntity<FeedingGrowthMasterJSON> saveFeedGrowth(@RequestBody FeedingGrowthMasterJSON feedJson) {
		ResponseMessageObject resp = new ResponseMessageObject();
		FeedingGrowthMasterJSON json = new FeedingGrowthMasterJSON();
		try {
			if (!BasicUtils.isEmpty(feedJson)) {
				resp = sysService.saveFeedGrowth(feedJson.getFeedGrowth(), feedJson.getUserId());
				json = sysService.getFeedGrowth(feedJson.getFeedGrowth().getUhid(), feedJson.getUserId());
				json.setResponse(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<FeedingGrowthMasterJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/followup/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<SaFollowupMasterJson> getFollowup(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		SaFollowupMasterJson json = new SaFollowupMasterJson();
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getFollowup(uhid.trim(), loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SaFollowupMasterJson>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveFollowup/", method = RequestMethod.POST)
	public ResponseEntity<SaFollowupMasterJson> saveFollowup(@RequestBody SaFollowupMasterJson followupjson) {
		ResponseMessageObject resp = new ResponseMessageObject();
		SaFollowupMasterJson json = new SaFollowupMasterJson();
		try {
			if (!BasicUtils.isEmpty(followupjson)) {
				resp = sysService.saveFollowup(followupjson.getFollowup(), followupjson.getUserId());
				json = sysService.getFollowup(followupjson.getFollowup().getUhid(), followupjson.getUserId());
				json.setResponse(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SaFollowupMasterJson>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/renal/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<RenalFailureJSON> getRenal(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		RenalFailureJSON renal = new RenalFailureJSON();
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				renal = sysService.getRenal(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<RenalFailureJSON>(renal, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveRenal/", method = RequestMethod.POST)
	public ResponseEntity<RenalFailureJSON> saveRenal(@RequestBody RenalFailureJSON renalJson) {
		RenalFailureJSON renal = null;
		try {
			if (!BasicUtils.isEmpty(renalJson)) {
				ResponseMessageObject resp = new ResponseMessageObject();
				resp = sysService.saveRenalJson(renalJson, renalJson.getUserId());
				renal = sysService.getRenal(renalJson.getRenalFailure().getUhid(), renalJson.getUserId());
				renal.setResponse(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<RenalFailureJSON>(renal, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/misc/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<MiscMasterJSON> getMisc(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		MiscMasterJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getMisc(uhid.trim(), loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MiscMasterJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveMisc/", method = RequestMethod.POST)
	public ResponseEntity<MiscMasterJSON> saveMisc(@RequestBody MiscMasterJSON miscJson) {
		MiscMasterJSON json = new MiscMasterJSON();
		try {
			if (!BasicUtils.isEmpty(miscJson)) {
				ResponseMessageObject resp = new ResponseMessageObject();
				resp = sysService.saveMisc(miscJson.getMisc(), miscJson.getUserId());
				json = sysService.getMisc(miscJson.getMisc().getUhid(), miscJson.getUserId());
				json.setResponse(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MiscMasterJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getSilvermanScore/{silvermanid}", method = RequestMethod.GET)
	public ResponseEntity<ScoreSilverman> getSilvermanScore(@PathVariable("silvermanid") Long silvermanId) {
		ScoreSilverman response = sysService.getSilvermanScore(silvermanId);
		return new ResponseEntity<ScoreSilverman>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDownesScore/{downesid}", method = RequestMethod.GET)
	public ResponseEntity<ScoreDownes> getDownesScore(@PathVariable("downesid") Long downesId) {
		ScoreDownes response = sysService.getDownesScore(downesId);
		return new ResponseEntity<ScoreDownes>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveSilvermanScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveSilvermanScore(
			@RequestBody ScoreSilverman silvermanScore) {
		ResponseMessageWithResponseObject response = sysService.saveSilvermanScore(silvermanScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveDownesScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveDownesScore(@RequestBody ScoreDownes downesScore) {
		ResponseMessageWithResponseObject response = sysService.saveDownesScore(downesScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getSepsisScore/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<SepsisScoreJSON> getSepsisScore(@PathVariable("uhid") String uhid) {
		SepsisScoreJSON response = sysService.getSepsisScore(uhid);
		return new ResponseEntity<SepsisScoreJSON>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveSepsisScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveSepsisScore(@RequestBody ScoreSepsis sepsisScore) {
		ResponseMessageWithResponseObject response = sysService.saveSepsisScore(sepsisScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getHIEScore/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<HIEScoreJSON> getHIEScore(@PathVariable("uhid") String uhid) {
		HIEScoreJSON response = sysService.getHIEScore(uhid);
		return new ResponseEntity<HIEScoreJSON>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveHIEScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveSepsisScore(@RequestBody ScoreHIE hieScore) {
		ResponseMessageWithResponseObject response = sysService.saveHIEScore(hieScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getIVHScore/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<IVHScoreJSON> getIVHScore(@PathVariable("uhid") String uhid) {
		IVHScoreJSON response = sysService.getIVHScore(uhid);
		return new ResponseEntity<IVHScoreJSON>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveIVHScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveIVHScore(@RequestBody ScoreIVH ivhScore) {
		ResponseMessageWithResponseObject response = sysService.saveIVHScore(ivhScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getBindScore/{bindscoreid}", method = RequestMethod.GET)
	public ResponseEntity<BindMasterJSON> getBindScore(@PathVariable("bindscoreid") Long bindscoreid) {
		BindMasterJSON response = sysService.getBindScore(bindscoreid);
		return new ResponseEntity<BindMasterJSON>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveBindScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveBindScore(@RequestBody ScoreBind bindScore) {
		ResponseMessageWithResponseObject response = sysService.saveBindScore(bindScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getPainScore/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<PainMasterJSON> getPainScore(@PathVariable("uhid") String uhid) {
		PainMasterJSON response = sysService.getPainScore(uhid);
		return new ResponseEntity<PainMasterJSON>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/savePainScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> savePainScore(@RequestBody ScorePain painScore) {
		ResponseMessageWithResponseObject response = sysService.savePainScore(painScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	
	/*@RequestMapping(value = "/inicu/printAssessmentModule/{uhid}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> getPrintAssessmentModuleData(@PathVariable("uhid")String uhid) {
		ResponseMessageWithResponseObject responseObject = new ResponseMessageWithResponseObject();
		List listPrintRecord;
		
			listPrintRecord = sysService.getPrintAssessmentModuleData(uhid);
			responseObject.setReturnedObject(listPrintRecord);
			responseObject.setMessage("get print data success");
			responseObject.setType(BasicConstants.MESSAGE_SUCCESS);
		
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseObject, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/inicu/printAssessmentModule", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> getPrintAssessmentModuleData(@Valid @RequestBody AssessmentPrintInfoObject printRecordObj,
			BindingResult bindingResult) {
		ResponseMessageWithResponseObject responseObject = new ResponseMessageWithResponseObject();
		List listPrintRecord;
		if(bindingResult.hasErrors()){
			return BasicUtils.handleInvalidInputJson(printRecordObj, bindingResult);
		}else{
			listPrintRecord = sysService.getPrintAssessmentModuleData(printRecordObj);
			responseObject.setReturnedObject(listPrintRecord);
			responseObject.setMessage("get print data success");
			responseObject.setType(BasicConstants.MESSAGE_SUCCESS);
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseObject, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sys/infection/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<AssessmentInfectionSystemPOJO> getInfection(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		AssessmentInfectionSystemPOJO infectObj = sysService.getInfectSystemObj(uhid, loggedUser);
		return new ResponseEntity<AssessmentInfectionSystemPOJO>(infectObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveInfect/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveInfectSystemObj(
			@RequestBody AssessmentInfectionSystemPOJO infectSystemJson) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			if (!BasicUtils.isEmpty(infectSystemJson)) {
				response = sysService.saveInfectSystem(infectSystemJson);
				response.setReturnedObject(infectSystemJson.getInfectionEventObject().getEventName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getBellScore/{bellscoreid}", method = RequestMethod.GET)
	public ResponseEntity<BellMasterJSON> getBellScore(@PathVariable("bellscoreid") Long bellscoreid) {
		BellMasterJSON response = sysService.getBellScore(bellscoreid);
		return new ResponseEntity<BellMasterJSON>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveBellScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveBellScore(@RequestBody ScoreBellStage bellScore) {
		ResponseMessageWithResponseObject response = sysService.saveBellScore(bellScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveStableNotes/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveStableNotes(@Valid@RequestBody StableNotesPOJO stableNote,BindingResult validationResult) {
		
		if(validationResult.hasErrors()){
			ResponseEntity<ResponseMessageWithResponseObject> validationResponse = BasicUtils.handleInvalidInputJson(stableNote, validationResult);
			return validationResponse;
		}else{
			ResponseMessageWithResponseObject response = sysService.saveStableNote(stableNote);
			return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/inicu/getStableNotes/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<StableNotesPOJO> getBellScore(@PathVariable("uhid")String uhid) {
		StableNotesPOJO stableNotesObj = sysService.getStableNote(uhid);
		return new ResponseEntity<StableNotesPOJO>(stableNotesObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sys/miscellaneous/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<SaMiscellaneousJSON> getMiscellaneous(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		SaMiscellaneousJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getMiscellaneous(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SaMiscellaneousJSON>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/getAssessmentsStatus/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<AssessmentStatus> getAssessmentsStatus(@PathVariable("uhid") String uhid) {
		AssessmentStatus assessmentStatusObj = sysService.getAssessmentsStatus(uhid);
		return new ResponseEntity<AssessmentStatus>(assessmentStatusObj, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/sys/saveFeedIntolerance/", method = RequestMethod.POST)
	public ResponseEntity<FeedIntorelanceJSON> saveFeedIntolerance(@RequestBody FeedIntorelanceJSON feedIntoleranceJson) {
		FeedIntorelanceJSON feedIntolerance = null;
		try {
			if (!BasicUtils.isEmpty(feedIntoleranceJson)) {
				ResponseMessageObject resp = new ResponseMessageObject();
				resp = sysService.saveFeedIntoleranceJson(feedIntoleranceJson, feedIntoleranceJson.getUserId());
				feedIntolerance = sysService.getFeedIntolerance(feedIntoleranceJson.getFeedIntolerance().getUhid(), feedIntoleranceJson.getUserId());
				feedIntolerance.setResponse(resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<FeedIntorelanceJSON>(feedIntolerance, HttpStatus.OK);
	}

	@RequestMapping(value="sys/saveNutritionOrder/",method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveNutritionObject(@RequestBody BabyfeedDetail babyfeedDetail){
		ResponseMessageObject responseMessageObject=new ResponseMessageObject();
		try {
			if (!BasicUtils.isEmpty(babyfeedDetail)) {
				responseMessageObject= sysService.saveNutritionObject(babyfeedDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageObject>(responseMessageObject, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/feedIntolerance/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<FeedIntorelanceJSON> getFeedIntolerance(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		FeedIntorelanceJSON feedIntolerance = new FeedIntorelanceJSON();
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				feedIntolerance = sysService.getFeedIntolerance(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<FeedIntorelanceJSON>(feedIntolerance, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/sys/pain/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<PainPOJO> getPain(@PathVariable("uhid") String uhid,
													   @PathVariable("loggedInUser") String loggedUser) {
		PainPOJO generalResponseObject=new PainPOJO();
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				generalResponseObject = sysService.getPain(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PainPOJO>(generalResponseObject, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sys/savePain/", method = RequestMethod.POST)
	public ResponseEntity<PainPOJO> savePain(@RequestBody PainPOJO pain) {
		PainPOJO json = null;
		ResponseMessageObject obj = null;
		try {
			if (!BasicUtils.isEmpty(pain)) {
				obj = sysService.savePain(pain);
				json = sysService.getPain(pain.getPain().getUhid(), pain.getPain().getLoggeduser());
				json.setResponse(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PainPOJO>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/saveNIPSScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveNIPSScore(@RequestBody ScoreNIPS NIPSScore) {
		ResponseMessageWithResponseObject response = sysService.saveNIPSScore(NIPSScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/savePIPPScore/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> savePIPPScore(@RequestBody ScorePIPP PIPPScore) {
		ResponseMessageWithResponseObject response = sysService.savePIPPScore(PIPPScore);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sys/shock/{uhid}/{loggedInUser}", method = RequestMethod.POST)
	public ResponseEntity<SysShockJSON> getShockAsses(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		SysShockJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = sysService.getShock(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SysShockJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/sys/saveShock/", method = RequestMethod.POST)
	public ResponseEntity<SysShockJSON> saveShock(@RequestBody SysShockJSON shock) {
		SysShockJSON json = null;
		ResponseMessageObject obj = null;
		try {
			if (!BasicUtils.isEmpty(shock)) {
				obj = sysService.saveShock(shock, shock.getUserId());
				json = sysService.getShock(shock.getShock().getUhid(), shock.getUserId());
				json.setResponse(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SysShockJSON>(json, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/inicu/urineOutputDuration/{uhid}/{urineOutputDuration}/", method = RequestMethod.GET)
	public ResponseEntity<Float> urineOutputDuration(@PathVariable("uhid") String uhid,
			@PathVariable("urineOutputDuration") Integer urineOutputDuration) {
		Float returnObj = 0f;
		try {
			returnObj = sysService.urineOutputDuration(uhid, urineOutputDuration);
		} catch (InicuDatabaseExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Float> (returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getJuandiceHypoglycemiaStatus/{uhid}/{status}/{assesment}", method = RequestMethod.GET)
	public ResponseEntity<AssessmentStatus> getJuandiceHypoglycemiaStatus(@PathVariable("uhid") String uhid,
																		  @PathVariable("status") String status,
																		  @PathVariable("assesment") String assesment) {
		AssessmentStatus assessmentStatusObj = sysService.getJuandiceHypoglycemiaStatus(uhid, status, assesment);
		return new ResponseEntity<AssessmentStatus>(assessmentStatusObj, HttpStatus.OK);
	}

	@RequestMapping(value = "inicu/getHematologyStatus/{uhid}/",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getHematologyStatus(@PathVariable("uhid") String uhid){
		Map<String, Object> returnObj = null;
		try{
			returnObj = sysService.getHematologyStatus(uhid);
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>> (returnObj, HttpStatus.OK);
	}

    @RequestMapping(value = "inicu/respSupport/{assesment}/{fromWhere}/",method = RequestMethod.POST)
    public GeneralResponseObject getRespSupportNote(@PathVariable("assesment") String assesment,
													@PathVariable("fromWhere") String fromWhere,
													@RequestBody AssessmentRespSystemPOJO respSystem){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
			generalResponseObject = sysService.generateRespSupportNote(assesment, fromWhere, respSystem);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }
    
    

    @RequestMapping(value = "inicu/medicationSupportInfection/{assesment}/{fromWhere}/",method = RequestMethod.POST)
    public GeneralResponseObject getRespSupportNote(@PathVariable("assesment") String assesment,
													@PathVariable("fromWhere") String fromWhere,
													@RequestBody AssessmentInfectionSystemPOJO respSystem){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
			generalResponseObject = sysService.generateMedicationSupportInfection(assesment, fromWhere, respSystem);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }
    
    
    @RequestMapping(value = "inicu/respsupportCns/{assesment}/{fromWhere}/",method = RequestMethod.POST)
    public GeneralResponseObject getRespSupportNote(@PathVariable("assesment") String assesment,
													@PathVariable("fromWhere") String fromWhere,
													@RequestBody AssessmentCNSSystemPOJO cnsSystem){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
			generalResponseObject = sysService.generateRespSupportCns(assesment, fromWhere, cnsSystem);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }

	  @RequestMapping(value = "inicu/jaundiceInactiveNote/",method = RequestMethod.POST)
    public GeneralResponseObject generateJaundiceInactiveNote(@RequestBody SysJaundJSON jaundice){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
            generalResponseObject = sysService.generateJaundiceInactiveNote(jaundice);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }

    @RequestMapping(value = "inicu/renalInactiveNote/",method = RequestMethod.POST)
    public GeneralResponseObject getRenalInactiveNote(@RequestBody RenalFailureJSON renal){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
            generalResponseObject = sysService.generateRenalInactiveNote(renal);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }

    @RequestMapping(value = "inicu/feedIntoleranceInactiveNote/",method = RequestMethod.POST)
    public GeneralResponseObject getFeedIntoleranceInactiveNote(@RequestBody FeedIntorelanceJSON feedIntolerance){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
            generalResponseObject = sysService.generateFeedIntoleranceInactiveNote(feedIntolerance);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }

	@RequestMapping(value = "inicu/miscellaneousInactiveNote/{assesment}/",method = RequestMethod.POST)
	public GeneralResponseObject getMiscellaneousInactiveNote(@PathVariable("assesment") String assesment,
															  @RequestBody SaMiscellaneousJSON saMiscellaneousJSON){
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try{
			generalResponseObject = sysService.generateMiscellaneousInactiveNote(saMiscellaneousJSON, assesment);
		}catch (Exception e){
			e.printStackTrace();
		}
		return generalResponseObject;
	}

	@RequestMapping(value = "inicu/shockInactiveNote/{fromWhere}/",method = RequestMethod.POST)
	public GeneralResponseObject getShockInactiveNote(@PathVariable("fromWhere") String fromWhere,
													  @RequestBody SysShockJSON sysShockJSON){
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try{
			generalResponseObject = sysService.generateShockInactiveNote(sysShockJSON, fromWhere);
		}catch (Exception e){
			e.printStackTrace();
		}
		return generalResponseObject;
	}
	
}
