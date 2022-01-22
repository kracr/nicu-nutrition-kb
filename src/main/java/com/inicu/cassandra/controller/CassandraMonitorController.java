package com.inicu.cassandra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.postgres.utility.BasicUtils;


@RestController
@EnableScheduling
public class CassandraMonitorController {

	public static int counter = 0;
	@Autowired
	DeviceDataService cassandraMonitorService;

	@RequestMapping(value="/inicu/connectPhlips/{domainId}",method = RequestMethod.POST)
	public void startMonitoringService(@PathVariable String domainId){
		List<String> listofId = new ArrayList<>();
		listofId.add(domainId);
		// list of id's contains domain ID.
		if(!BasicUtils.isEmpty(listofId)){
			cassandraMonitorService.startService(listofId);
			// start a thread to start migration of the data
//			cassandraMonitorService.migrateDataToPostgres();
		}
	}

//	@RequestMapping(value="/getData/{id}",method = RequestMethod.GET)
//	private List<PatientDataJsonObject> getData(@PathVariable("id") String id){
//		return cassandraMonitorService.getPatientData(id);
//	}
//	@RequestMapping(value="/getDataBP/{id}",method = RequestMethod.GET)
//	private MonitorJSON getBPData(@PathVariable("id") String id){
//		return cassandraMonitorService.getPatientBPData(id);
//	}
//	@RequestMapping(value="/getDataECG/{id}",method = RequestMethod.GET)
//	private MonitorJSON getECGData(@PathVariable("id") String id){
//		return cassandraMonitorService.getPatientECGData(id);
//	}
//	@RequestMapping(value="/getDataPulse/{id}",method = RequestMethod.GET)
//	private MonitorJSON getPulseData(@PathVariable("id") String id){
//		return cassandraMonitorService.getPatientPulseData(id);
//	}
//	@RequestMapping(value="/getDataVariable/{id}",method = RequestMethod.GET)
//	private List<PatientDataJsonObject> getVariableData(@PathVariable("id") String id){
//		return cassandraMonitorService.getPatientVariableData(id);
//	}

}
