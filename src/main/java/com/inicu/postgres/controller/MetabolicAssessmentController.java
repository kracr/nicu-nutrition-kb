package com.inicu.postgres.controller;

import com.inicu.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.postgres.service.MetabolicService;
import com.inicu.postgres.utility.BasicUtils;

@RestController
public class MetabolicAssessmentController {
	
	@Autowired
	MetabolicService metabolicService;
	public String loggedUser = "";
	
	@RequestMapping(value = "inicu/assessment/getHypoglycemia/{uhid}/{loggedInUser}", method = RequestMethod.GET)
	public ResponseEntity<HypoglycemiaJSON> getMetabolicHypoglycemia(@PathVariable("uhid") String uhid,
			@PathVariable("loggedInUser") String loggedUser) {
		HypoglycemiaJSON json = null;
		try {
			if (!BasicUtils.isEmpty(uhid)) {
				json = metabolicService.getHypoglycemia(uhid, loggedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HypoglycemiaJSON>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/assessment/saveHypoglycemia/", method = RequestMethod.POST)
	public ResponseEntity<HypoglycemiaJSON> saveMetabolicHypoglycemia(@RequestBody HypoglycemiaJSON hypoglycemiaJSON) {
		
		HypoglycemiaJSON hypoglycemia = null;
		try {
			if (!BasicUtils.isEmpty(hypoglycemiaJSON)) {
				ResponseMessageObject response = new ResponseMessageObject();
				response = metabolicService.saveHypoglycemiaJson(hypoglycemiaJSON , hypoglycemiaJSON.getUserId());
				hypoglycemia = metabolicService.getHypoglycemia(String.valueOf(hypoglycemiaJSON.getCurrentHypoglycemia().getHypoglycemiaid()), hypoglycemiaJSON.getUserId());
				hypoglycemia.setResponse(response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HypoglycemiaJSON>(hypoglycemia, HttpStatus.OK);
	}

	@RequestMapping(value = "inicu/assessment/hypoglycemiaInactiveNote/",method = RequestMethod.POST)
	public GeneralResponseObject getHypoglycemiaInactiveNote(@RequestBody HypoglycemiaJSON hypoglycemiaJSON){
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try{
			generalResponseObject = metabolicService.generateHypoglycemiaInactiveNote(hypoglycemiaJSON);
		}catch (Exception e){
			e.printStackTrace();
		}
		return generalResponseObject;
	}

//	@RequestMapping(value = "/inicu/getMetabolicStatus/{uhid}/{loggedInUser}" , method = RequestMethod.GET)
//	public ResponseEntity<MetabolicInfoStatusPOJO> getMetabolicStatusInfo(@PathVariable("uhid") String uhid,
//			@PathVariable("loggedInUser") String loggedUser){
//		MetabolicInfoStatusPOJO infoStatus = null;
//		String hypoStatus = "";
//		try {
//			HypoglycemiaJSON hypoJson = metabolicService.getHypoglycemia(uhid, loggedUser);
//			if(!BasicUtils.isEmpty(hypoJson)) {
//				hypoStatus = hypoJson.getCurrentHypoglycemia().getHypoglycemiaEventStatus();
//				
//			}
//			
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		return new ResponseEntity<MetabolicInfoStatusPOJO>( infoStatus, HttpStatus.OK);
//		
//	}


}
