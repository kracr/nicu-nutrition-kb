package com.inicu.postgres.controller;

import com.inicu.postgres.entities.TherapeuticHypothermia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inicu.models.ImagePOJO;
import com.inicu.models.ProceduresMasterPojo;
import com.inicu.models.TherapeuticHypothermiaPOJO;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.postgres.service.ProceduresService;

import java.util.List;

/**
 * @author Sourabh Verma
 */

@Controller
public class ProceduresController {

	@Autowired
	ProceduresService proceduresService;

	@RequestMapping(value="/inicu/hypothermia/{uhid}/{loggedUser}/{branchName}/",method = RequestMethod.GET)
	public ResponseEntity<TherapeuticHypothermiaPOJO> getHypothermia(@PathVariable("uhid") String uhid,
																	 @PathVariable("loggedUser") String loggedUser,
																	 @PathVariable("branchName") String branchName){
		TherapeuticHypothermiaPOJO hypothermiaObject=proceduresService.getHypothermia(uhid, loggedUser, branchName);
		return new ResponseEntity<TherapeuticHypothermiaPOJO>(hypothermiaObject,HttpStatus.OK);
	}

	@RequestMapping(value="/inicu/saveHypothermia/{uhid}/{loggedUser}/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveHypothermia(@RequestBody List<TherapeuticHypothermia> hypothermiaObjectList,
																			 @PathVariable("uhid") String uhid,
																			 @PathVariable("loggedUser") String loggedUser,
																			 @PathVariable("branchName") String branchName){

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		List<TherapeuticHypothermia> ObjectList=hypothermiaObjectList;
		try {
			for(TherapeuticHypothermia obj:ObjectList) {
				proceduresService.saveHypothermia(obj);
			}
			response.setMessage("Saved Hypothermia");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getProcedures/{uhid}/{loggedUser}/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<ProceduresMasterPojo> getProcedures(@PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser, @PathVariable("branchName") String branchName) {
		ProceduresMasterPojo procedures = proceduresService.getProcedures(uhid, loggedUser, branchName);
		return new ResponseEntity<ProceduresMasterPojo>(procedures, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveProcedures/{uhid}/{loggedUser}/{branchName}/{procedureName}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveProcedures(
			@RequestBody ProceduresMasterPojo ProceduresMasterPojo, @PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser, @PathVariable("branchName") String branchName,
			@PathVariable("procedureName") String procedureName) {

		ResponseMessageWithResponseObject response = proceduresService.saveProcedures(uhid, loggedUser, branchName, procedureName,
				ProceduresMasterPojo);
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/extractImageForExchangeTransfusion/{imageName}", method = RequestMethod.GET)
	ResponseEntity<ImagePOJO> extractImage(@PathVariable("imageName") String imageName) {
		ImagePOJO imageData = null;
		try {
			imageData = proceduresService.getImage(imageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ImagePOJO>(imageData, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/storeImageForExchangeTransfusion/", method = RequestMethod.POST)
	ResponseEntity<ResponseMessageWithResponseObject> storeImage(@RequestBody ImagePOJO imageObj) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			response = proceduresService.storeImage(imageObj.getName(), imageObj.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(response, HttpStatus.OK);

	}
}
