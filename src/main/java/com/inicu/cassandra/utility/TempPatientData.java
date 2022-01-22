package com.inicu.cassandra.utility;

import java.util.HashMap;
import java.util.Iterator;

import com.inicu.postgres.utility.BasicUtils;

public class TempPatientData implements Runnable{

//	private Numeric data;
//	private int domainId;
//	private String patientId;
//	private SampleArray sampleData;
//
//	public TempPatientData(Numeric data, int get_domain_id,String patientId, SampleArray sampleData) {
//		this.data = data;
//		this.domainId = get_domain_id;
//		this.patientId = patientId;
//		this.sampleData = sampleData;
//	}

	@Override
	public void run() {
//		String metricID = null;
//		String value = null;
//		if(!BasicUtils.isEmpty(data)){
//			metricID = data.metric_id;
//			value = String.valueOf(data.value);
//		}else{
//			metricID = sampleData.metric_id;
//			String values = "";
//			Values val = sampleData.values;
//			FloatSeq userData = val.userData;
//			Iterator itr = userData.iterator();
//			while(itr.hasNext()){
//				Object element = itr.next();
//				if(!values.isEmpty()){
//					values = values+","+element.toString();
//				}else{
//					values = element.toString();
//				}
//			}
//			value = values;
//		}
//		// store values in static map
//		if(MappingConstants.tempPatientDataMap.containsKey(patientId)){
//			HashMap<String, String> valueMap = MappingConstants.tempPatientDataMap.get(patientId);
//			valueMap.put(metricID, value);
//			MappingConstants.tempPatientDataMap.put(patientId, valueMap);
//		}else{
//			//create a new map for the data
//			HashMap<String, String> valueMap = new HashMap<>();
//			valueMap.put(metricID, value);
//			MappingConstants.tempPatientDataMap.put(patientId, valueMap);
//		}
	}
}
