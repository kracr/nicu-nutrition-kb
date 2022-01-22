package com.inicu.cassandra.utility;

//import ice.Numeric;
//import ice.Time_t;

public class InsertPatientDataThread implements Runnable{

//	@Autowired  
//	private DeviceDAO testDAO;
//	
//	private Numeric data;
//	
//	public InsertPatientDataThread(Numeric data) {
//		this.data = data;
//	}
//	
	@Override
	public void run() {
//		if(data!=null){
//			PatientData pd = new PatientData();
//			Date date = new Date();
//			pd.setPatientId("1");
//			
//			pd.setEventTime(date);
//			
//			String uniDevId = data.unique_device_identifier;
//			if(uniDevId!=null&&!uniDevId.isEmpty()){
//				
//			}
//			String metricId = data.metric_id;
//			if(metricId!=null&&!metricId.isEmpty()){
//				pd.setMetricId(metricId);
//			}
//			String vendorId = data.vendor_metric_id;
//			if(vendorId!=null&&!vendorId.isEmpty()){
//				pd.setVendorMetricId(vendorId);
//			}
//			int instanceId = data.instance_id;
//			pd.setInstanceId(String.valueOf(instanceId));
//
//			String unitId = data.unit_id;
//			if(unitId!=null&&!unitId.isEmpty()){
//				pd.setUnitId(unitId);
//			}
//			float value = data.value;
//			pd.setValue(String.valueOf(value));
//			
//			Time_t devTime = data.device_time;
//			if(devTime!=null){
//				int devSec = devTime.sec;
//				pd.setDeviceTimeSec(String.valueOf(devSec));
//				int devNanoSec = devTime.nanosec;
//				pd.setDeviceTimeNano(String.valueOf(devNanoSec));
//			}
//
//			Time_t presTime = data.presentation_time;
//			if(presTime!=null){
//				int presSec = presTime.sec;
//				pd.setPresTimeSec(String.valueOf(presSec));
//				int presNanoSec = presTime.nanosec;	
//				pd.setPresTimeNano(String.valueOf(presNanoSec));
//			}	
//			
//			testDAO.startService(pd);
//		}
	}

}
