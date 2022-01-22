package com.inicu.postgres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.models.ChartMasterJson;
import com.inicu.postgres.service.GrowthChatService;
import com.inicu.postgres.utility.BasicUtils;

@RestController
public class GrowthChartController {
	@Autowired
	GrowthChatService graph;

	@RequestMapping(value = "/inicu/growthChart/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<ChartMasterJson> getGraphData(@PathVariable("uhid") String uhid) {
		// List<GrowthChartJSON> listJSON =new ArrayList<GrowthChartJSON>();
		ChartMasterJson json = new ChartMasterJson();
		if (!BasicUtils.isEmpty(uhid)) {
			json = graph.getGraphData(uhid);
		} else {
			System.out.println("uhid must not be empty");
		}
		return new ResponseEntity<ChartMasterJson>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getFentonCentile/{gender}/{gestWeek}/{paramType}/{paramValue}/", method = RequestMethod.GET)
	public ResponseEntity<Double> getFentonCentile(@PathVariable("gender") String gender,
			@PathVariable("gestWeek") String gestWeek, @PathVariable("paramType") String paramType,
			@PathVariable("paramValue") double paramValue) {
		double returnValue = graph.getFentonCentile(gender, gestWeek, paramType, paramValue);

		return new ResponseEntity<Double>(returnValue, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/updateCentileDetails/", method = RequestMethod.GET)
	public ResponseEntity<String> updateCentileDetails() {
		String returnValue = graph.updateCentileDetails();
		return new ResponseEntity<String>(returnValue, HttpStatus.OK);
	}

}
