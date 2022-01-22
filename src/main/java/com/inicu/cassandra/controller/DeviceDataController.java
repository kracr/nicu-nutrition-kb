package com.inicu.cassandra.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inicu.models.*;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.BabyRemoteView.RemoteViewServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.analytics.DataAnalyticsController;
import com.inicu.cassandra.entities.CameraImageStream;
import com.inicu.cassandra.models.AddDeviceMasterJson;
import com.inicu.cassandra.models.AddDeviceTemplateJson;
import com.inicu.cassandra.models.MonitorDummyDataJson;
import com.inicu.cassandra.models.MonitorJSON;
import com.inicu.cassandra.models.NewRegisteredDevicesJSON;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.device.utility.DeviceConstants;
import com.inicu.device.utility.RegistrationConstants;
import com.inicu.postgres.service.AnalyticsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

@RestController
public class DeviceDataController {

	private SimpMessagingTemplate template;

	@Autowired
	DeviceDataService deviceDataService;

	@Autowired
	AnalyticsService analyticsService;

	@Autowired
	PatientDao patientDao;
	@Autowired
	RemoteViewServiceImpl remoteViewService;

	@Autowired
	public DeviceDataController(SimpMessagingTemplate template) {
		this.template = template;
	}

	// @MessageMapping("/device")
	// @SendTo("/topic/deviceNumericData")
	// public List<PatientDataJsonObject> getDeviceNumericData(Message message)
	// throws Exception{
	// String patientId = "161203970";
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// try{
	// while(true){
	// Thread.sleep(1000);
	// fireNumericData();
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// });
	// t.start();
	// // return deviceDataService.getPatientVariableData(patientId);
	// return deviceDataService.getDummyVariableData();
	// // return deviceDataService.getStoredDeviceNumericData(patientId);
	//
	// }

	@SendTo("/topic/deviceNumericData")
	public List<PatientDataJsonObject> fireNumericData() {
		List<PatientDataJsonObject> numericData = deviceDataService.getDummyVariableData();
		if (!BasicUtils.isEmpty(this.template) && !BasicUtils.isEmpty(numericData)) {
			this.template.convertAndSend("/topic/deviceNumericData", numericData);
		}
		return numericData;
	}

	// @MessageMapping("/deviceBp")
	// @SendTo("/topic/bpData")
	// public MonitorJSON getBpData(Message message){
	// String patientId = "10003";
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// while(true){
	// try{
	// Thread.sleep(1000);
	// fireBpData();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// }
	// });
	// t.start();
	// return deviceDataService.getPatientDummyData("MDC_PRESS_BLD_ART_ABP");
	// // return
	// deviceDataService.getStoredDeviceData(patientId,"MDC_PRESS_BLD_ART_ABP");
	// // return deviceDataService.getPatientDummyData("MDC_PRESS_BLD_ART_ABP");
	// }

	@SendTo("/topic/bpData")
	public MonitorJSON fireBpData() {
		MonitorJSON bpData = deviceDataService.getPatientDummyData("MDC_PRESS_BLD_ART_ABP");
		if (!BasicUtils.isEmpty(this.template) && !BasicUtils.isEmpty(bpData)) {
			this.template.convertAndSend("/topic/bpData", bpData);
		}
		return bpData;
	}

	//
	// @MessageMapping("/devicePulse")
	// @SendTo("/topic/pulseData")
	// public MonitorJSON getPulseData(Message message){
	// String patientId = "170200060";
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// while(true){
	// try{
	// Thread.sleep(1000);
	// firePulseData();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// }
	// });
	// t.start();
	//
	// // return
	// deviceDataService.getStoredDeviceData(patientId,"MDC_PULS_OXIM_PLETH");
	// return deviceDataService.getPatientDummyData("MDC_PULS_OXIM_PLETH");
	// }

	@SendTo("/topic/pulseData")
	public MonitorJSON firePulseData() {
		MonitorJSON pulseData = deviceDataService.getPatientDummyData("MDC_PULS_OXIM_PLETH");
		if (!BasicUtils.isEmpty(this.template) && !BasicUtils.isEmpty(pulseData)) {
			this.template.convertAndSend("/topic/pulseData", pulseData);
		}
		return pulseData;
	}

	// @MessageMapping("/deviceEcg")
	// @SendTo("/topic/deviceEcg")
	// public MonitorJSON getECGData(Message message){
	// String patientId = "170200060";
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// while(true){
	// try{
	// Thread.sleep(1000);
	// fireEcgData();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// }
	// });
	// t.start();
	//
	// // return deviceDataService.getPatientECGData(patientId);
	// return deviceDataService.getPatientDummyData("MDC_ECG_LEAD_II");
	// // return
	// deviceDataService.getStoredDeviceData(patientId,"MDC_ECG_LEAD_II");
	// }

	@SendTo("/topic/deviceEcg")
	public MonitorJSON fireEcgData() {
		System.out.println("Fired ECG Data");
		MonitorJSON ecgData = deviceDataService.getPatientDummyData("MDC_ECG_LEAD_II");
		if (!BasicUtils.isEmpty(this.template) && !BasicUtils.isEmpty(ecgData)) {
			this.template.convertAndSend("/topic/deviceEcg", ecgData);
		}
		return ecgData;
	}

	@RequestMapping(value = "/inicu/connectDevice/{branchName}/{entryDate}", method = RequestMethod.POST)
	public ResponseEntity<AddDeviceMasterJson> startDeviceMonitoring(@PathVariable String branchName, @RequestBody AddDeviceTemplateJson deviceData
			, @PathVariable("entryDate") String entryDate) {
		AddDeviceMasterJson masterJson = null;
		ResponseMessageObject robj = new ResponseMessageObject();
		robj.setMessage("Alert! Selected device could not be found.");
		robj.setType(BasicConstants.MESSAGE_FAILURE);
		if (!BasicUtils.isEmpty(deviceData)) {
			robj = deviceDataService.mapInicuDeviceWithPatient(deviceData, entryDate);
			masterJson = deviceDataService.getDeviceTemplateData(deviceData.getUhid(), deviceData.getBedId(), branchName);
			masterJson.setResponse(robj);
		}
		return new ResponseEntity<AddDeviceMasterJson>(masterJson, HttpStatus.OK);
	}

	@RequestMapping(value = "/requestSerial", method = RequestMethod.POST)
	public ResponseEntity<String> requestSerial(@RequestBody String data) {
		System.out.println(data);
		String response = null;
		JSONObject deviceInfo = null;
		try {
			deviceInfo = new JSONObject(data);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String macid = null, serial = null;
		try {
			macid = deviceInfo.getString(DeviceConstants.MAC_ID);
			serial = deviceInfo.getString(RegistrationConstants.NEO_SERIAL);
			if (serial.equalsIgnoreCase(RegistrationConstants.NEW_DEVICE_SERIAL)) {
				// save the mac id of this device in device_detal
				System.out.println("new device");
				ResponseMessageObject resp = deviceDataService.addMacidToDeviceDetail(macid);
				String type = resp.getType();
				System.out.println("new device response.type details " + type);
				if (type.equalsIgnoreCase(RegistrationConstants.REGISTERATION_ALREADY_EXISTS)) {

					String deviceid = deviceDataService.getDeviceId(resp.getMessage());
					if (!BasicUtils.isEmpty(deviceid)) {
						response = deviceid;
					}

				}
				if (type.equalsIgnoreCase(RegistrationConstants.REGISTERATION_SUCCESSFULL)) {
					response = DeviceConstants.COMMAND_SHUTDOWN;
				}
				if (type.equalsIgnoreCase(RegistrationConstants.NEO_SERIAL)) {
					String s = resp.getMessage();
					if (!BasicUtils.isEmpty(s)) {
						response = s;
					}

				}
			} else {
				// proceed with other parameters
				response = deviceDataService.getDeviceSettingForProgram(macid);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reply = new JSONObject();
		try {
			reply.put(DeviceConstants.REPLY, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(reply.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/saveDeviceExceptions", method = RequestMethod.POST)
	public void saveDeviceException(@RequestBody String jsonData) {
		System.out.println("device data exception received:: " + jsonData);
		try {
			JSONObject json = new JSONObject(jsonData);
			deviceDataService.saveDeviceDataException(json);
		} catch (JSONException e) {
			System.out.println("Device exception received is not a valid json format.");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/registerDevice", method = RequestMethod.POST)
	public ResponseEntity<String> registerDevice(@RequestBody String data) {
		System.out.println(data);
		JSONObject deviceInfo = null;
		try {
			deviceInfo = new JSONObject(data);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String macid = null, deviceid = null, devicetype = null, devicename = null;
		try {
			RegisteredDevicesJson device = new RegisteredDevicesJson();
			macid = deviceInfo.getString(DeviceConstants.MAC_ID);
			device.setMacid(macid);
			devicename = deviceInfo.getString(DeviceConstants.DEVICE_NAME);
			device.setBrandName(devicename);
			deviceid = deviceInfo.getString(DeviceConstants.DEVICE_ID);
			device.setDeviceName(deviceid);
			devicetype = deviceInfo.getString(DeviceConstants.DEVICE_TYPE);
			device.setDeviceType(devicetype);
			boolean checkMac = false;// deviceDataService.macidExists(device);
			if (!checkMac) {
				boolean devAdded = deviceDataService.savedevDetailsObject(device);
				if (devAdded)
					System.out.println("OK" + devAdded);
				JSONObject REGISTRATION = new JSONObject();
				try {
					REGISTRATION.put("registration", "active");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new ResponseEntity<String>(REGISTRATION.toString(), HttpStatus.OK);
			} else {
				System.out.println("NOT OK");
				return new ResponseEntity<String>("error", HttpStatus.EXPECTATION_FAILED);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("NOT OK");
		return new ResponseEntity<String>("Cannot register please retry with valid parameters",
				HttpStatus.EXPECTATION_FAILED);

	}

	@RequestMapping(value = "/inicu/getDeviceData/{uhid}/{bedid}/{branchName}", method = RequestMethod.POST)
	public AddDeviceMasterJson getDeviceData(@PathVariable String uhid, @PathVariable String bedid, @PathVariable String branchName) {
		AddDeviceMasterJson masterJson;
		masterJson = deviceDataService.getDeviceTemplateData(uhid, bedid, branchName);
		return masterJson;
	}

	@RequestMapping(value = "/inicu/getTestData/", method = RequestMethod.POST)
	public List<PatientDataJsonObject> getDummyData() {
		// return
		// deviceDataService.getStoredDeviceData("10003","MDC_ECG_LEAD_II");
		// return
		// deviceDataService.getPatientDummyData("MDC_PRESS_BLD_ART_ABP");
		// return null;

		deviceDataService.getDummyVentilatorData();
		return deviceDataService.showDummyVentilatorData();
		// return deviceDataService.getDummyVariableData();
	}

	@RequestMapping(value = "/inicu/getVentilatorData/", method = RequestMethod.POST)
	public void storeVentilatorData() {
		deviceDataService.getDummyVentilatorData();
	}

	/**
	 * socket to show ventilator data
	 */
	// @MessageMapping("/ventilator")
	// @SendTo("/topic/ventilatorData")
	// public List<PatientDataJsonObject> getDeviceVentilatorData(Message
	// message) throws Exception{
	// String patientId = "161203970";
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// try{
	// while(true){
	// Thread.sleep(1000);
	// fireVentilatorData();
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// });
	// t.start();
	// return deviceDataService.showDummyVentilatorData();
	// }

	@SendTo("/topic/ventilatorData")
	public List<PatientDataJsonObject> fireVentilatorData() {
		List<PatientDataJsonObject> ventilatorData = deviceDataService.showDummyVentilatorData();
		if (!BasicUtils.isEmpty(this.template) && !BasicUtils.isEmpty(ventilatorData)) {
			this.template.convertAndSend("/topic/ventilatorData", ventilatorData);
		}
		return ventilatorData;
	}

	@RequestMapping(value = "/inicu/getVentStaticData/", method = RequestMethod.POST)
	public List<PatientDataJsonObject> getVentilatorStaticData() {
		return deviceDataService.showDummyVentilatorData();
	}

	// show dummy numeric data for demo
	@RequestMapping(value = "/inicu/demoNumericData/", method = RequestMethod.POST)
	public List<PatientDataJsonObject> getDemoNumericData() {
		return deviceDataService.getDummyVariableData();
	}

	// show dummy ecg data for demo
	@RequestMapping(value = "/inicu/demoEcgData/", method = RequestMethod.POST)
	public MonitorJSON getDemoEcgData() {
		return deviceDataService.getPatientDummyData("MDC_ECG_LEAD_II");
	}

	// show dummy bp data for demo
	@RequestMapping(value = "/inicu/demoBpData/", method = RequestMethod.POST)
	public MonitorJSON getDemoBpData() {
		return deviceDataService.getPatientDummyData("MDC_PRESS_BLD_ART_ABP");
	}

	// show dummy pulse data for demo
	@RequestMapping(value = "/inicu/demoPulseData/", method = RequestMethod.POST)
	public MonitorJSON getPulseData() {
		return deviceDataService.getPatientDummyData("MDC_PULS_OXIM_PLETH");
	}

	@RequestMapping(value = "/inicu/demoMonitorData/", method = RequestMethod.POST)
	public MonitorDummyDataJson getMonitorDummyData() {
		MonitorDummyDataJson json = new MonitorDummyDataJson();
		json = deviceDataService.getAllMonitorData();
		return json;
	}

	@RequestMapping(value = "/inicu/startMigration/", method = RequestMethod.POST)
	public void dataMigration() {
		deviceDataService.dataMigrationService();
	}

	@RequestMapping(value = "/inicu/demoMonitorData/{time}/{uhid}", method = RequestMethod.POST)
	public MonitorDummyDataJson getMonitorDummy(@PathVariable("uhid") String uhid,
			@PathVariable("time") Date currentTime) {
		MonitorDummyDataJson json = new MonitorDummyDataJson();
		json = deviceDataService.getMonitorData(uhid, currentTime);
		return json;
	}

	@RequestMapping(value = "/inicu/getDeviceWaveform/{time}/{uhid}", method = RequestMethod.POST)
	public MonitorWaveformData getWaveformData(@PathVariable("uhid") String uhid,
			@PathVariable("time") Date currentTime) {
		MonitorWaveformData data = new MonitorWaveformData();
		data = deviceDataService.getDeviceWaveFormData(uhid, currentTime);
		return data;
	}

	@RequestMapping(value = "/inicu/disconnectDevice/{uhid}/{bedid}/{deviceId/{branchName}", method = RequestMethod.POST)
	public AddDeviceMasterJson disconnectDevice(@PathVariable("deviceId") String deviceId,
			@PathVariable("bedid") String bedid, @PathVariable("uhid") String uhid, @PathVariable("branchName") String branchName) {
		AddDeviceMasterJson deviceJson = null;
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj = deviceDataService.disconnectDevice(uhid, deviceId);
		deviceJson = deviceDataService.getDeviceTemplateData(uhid, bedid, branchName);
		deviceJson.setResponse(rObj);
		return deviceJson;
	}
	
	@RequestMapping(value = "/inicu/getPhysiologicalCSVData/{fromDate}/{toDate}/{uhid}/", method = RequestMethod.POST)
	public List<VitalCerebralExportPOJO> getExportVitalData(@PathVariable("fromDate") String entryDate, @PathVariable("toDate") String endDate, @PathVariable("uhid") String uhid) {
		List<VitalCerebralExportPOJO> obj = null;
		try {
			obj = deviceDataService.getExportVitalData(uhid, entryDate, endDate);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/inicu/getDeviceGraphData/{fromDate}/{toDate}/{daf}", method = RequestMethod.POST)
	public Object getDevGraphData(@RequestBody DeviceGraphDataJson graphDataJson,
			@PathVariable("fromDate") String entryDate, @PathVariable("toDate") String endDate) {
		DeviceGraphDataJson obj = null;
		try {
			System.out.println(graphDataJson);
			obj = deviceDataService.getDevGraphData(graphDataJson, entryDate, endDate);
			// generate physiscore for this data
			DataAnalyticsController analyticsController = (DataAnalyticsController) BasicConstants.applicatonContext
					.getBean(DataAnalyticsController.class);
			obj = analyticsController.execute(obj);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return obj;
	}
	
	@RequestMapping(value = "/inicu/activeFilters/{uhid}/{fromDate}/{toDate}/", method = RequestMethod.GET)
	public Object getActiveFilters(@PathVariable("uhid") String uhid, @PathVariable("fromDate") String entryDate, @PathVariable("toDate") String endDate) {
		ActiveFilterGraphDataJson activeFilterGraphDataObj = new ActiveFilterGraphDataJson();
		try {
			activeFilterGraphDataObj = deviceDataService.getActiveFiltersData(uhid,entryDate, endDate);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return activeFilterGraphDataObj;
	}
	
	@RequestMapping(value = "/getvideoURL/{uhid}/{username}", method = RequestMethod.GET)
	public String getvideoURL(@PathVariable("uhid") String uhid, @PathVariable("username") String username) {
		String videourl = "https://5e06e5cc66966.streamlock.net/vod/_definst_/mp4:NEOTINY102.stream_720p_2020-03-05-17.02.56.825-UTC_0.mp4/playlist.m3u8";
		System.out.println(uhid);
		System.out.println(username);
		
		return videourl;
	}
	
	@RequestMapping(value = "/inicu/getCameraImageTrends/", method = RequestMethod.GET)
	public List<CameraImageStream> getCameraImageTrends(@RequestParam("uhid") String uhid, @RequestParam("device_time") Timestamp deviceTime,@RequestParam(defaultValue = "false") Boolean flag) {
		List<CameraImageStream> cameraImageTrendsList = new ArrayList<>();
		try {
			cameraImageTrendsList = deviceDataService.getCameraImageTrends(uhid,deviceTime,flag);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return cameraImageTrendsList;
	}
	
	@RequestMapping(value = "/inicu/getCameraImageTrendsWithPagination/", method = RequestMethod.GET)
	public List<CameraImageStream> getCameraImageTrendsWithPagination(@RequestParam(value = "uhid") String uhid, @RequestParam(value = "device_time") Timestamp deviceTime,@RequestParam(value = "flag" ,defaultValue = "false") Boolean flag, @RequestParam(value = "page_num", defaultValue = "0") Integer pageNumber, @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize) {
		List<CameraImageStream> cameraImageTrendsWithPaginationList = new ArrayList<>();
		try {
			cameraImageTrendsWithPaginationList = deviceDataService.getCameraImageTrendsWithPagination(uhid,deviceTime,flag,pageNumber,pageSize);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return cameraImageTrendsWithPaginationList;
	}


	@RequestMapping(value = "/inicu/getregisterDevice/{branchName}", method = RequestMethod.POST)
	public Object getRegisteredDevices(@PathVariable String branchName) {
		System.out.println("inside getregisterDevice");
		// RY commenting for new device mgmt
		/*
		 * RegisterDeviceMasterJson json = new RegisterDeviceMasterJson(); json =
		 * deviceDataService.getRegisterednewDevice();
		 */
		NewRegisteredDevicesJSON json = new NewRegisteredDevicesJSON();
		json = deviceDataService.getRegisteredDevicesNew(branchName);
		return json;
	}

	@RequestMapping(value = "inicu/addRegisteredDevice", method = RequestMethod.POST)
	public Object registerDevice(@RequestBody RegisterDeviceMasterJson response) {
		RegisterDeviceMasterJson json = new RegisterDeviceMasterJson();
		if (!BasicUtils.isEmpty(response)) {
			json = deviceDataService.registerDevice(response);
		}
		return json;
	}

	@RequestMapping(value = "inicu/getInicuDevices", method = RequestMethod.POST)
	public Object getInicuDevicesDetails() {
		InicuDeviceMastJson json = new InicuDeviceMastJson();
		json = deviceDataService.getInicuDeviceDetails();
		return json;
	}

	@RequestMapping(value = "inicu/addboard/{boardname}/{macid}/{boxname}")
	public Object addBoard(@PathVariable("boardname") String boardName, @PathVariable("macid") String macid,
			@PathVariable("boxname") String boxName) {
		System.out.println(boardName + " " + boxName + " " + macid);
		ResponseMessageObject rObj = new ResponseMessageObject();
		if (!BasicUtils.isEmpty(boardName) && !BasicUtils.isEmpty(boxName) && !BasicUtils.isEmpty(macid)) {
			rObj = deviceDataService.addBeagleBoneDevices(boardName, boxName, macid);
		} else {
			rObj.setMessage("Boardname and Mac address both are mandatory.");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		InicuDeviceMastJson json = new InicuDeviceMastJson();
		json = deviceDataService.getInicuDeviceDetails();
		json.setResponse(rObj);
		return json;
	}

	@RequestMapping(value = "inicu/addBoards/{boxName}", method = RequestMethod.POST)
	public Object addBoards(@PathVariable("boxName") String boardName) {
		ResponseMessageObject resp = deviceDataService.addBoard(boardName);
		InicuDeviceMastJson json = deviceDataService.getInicuDeviceDetails();
		json.setResponse(resp);
		return json;
	}

	@RequestMapping(value = "inicu/deleteBoard/{macid}", method = RequestMethod.POST)
	public Object deleteBoard(@PathVariable("macid") String macid) {
		ResponseMessageObject resp = new ResponseMessageObject();
		resp = deviceDataService.deleteBoard(macid);

		InicuDeviceMastJson json = deviceDataService.getInicuDeviceDetails();
		json.setResponse(resp);
		return json;
	}
	
	 @RequestMapping(value="/getDeviceDetail/{boxSerialNo}/{boardName}/",method = RequestMethod.GET)
	 public Object getDeviceDetail(@PathVariable("boxSerialNo") String boxSerialNo,@PathVariable("boardName") String boardName){
		 DeviceDetail deviceDetailObject = deviceDataService.getDeviceDetail(boxSerialNo,boardName);
		 return deviceDetailObject;
	 }
	 
//	 @RequestMapping(value="/getTokenForichr/{uhid}/{deviceId}/{message}",method = RequestMethod.GET)
//	 public Object getTokenForichr(@PathVariable("uhid") String uhid, @PathVariable("deviceId") String deviceId, @PathVariable("message") String message ){
//		 JSONObject jObject = deviceDataService.getTokenForichr(uhid,deviceId,message);
//		 return jObject;
//	 }

	 @RequestMapping(value="/getLiveVideoCredentialForichr/{uhid}/",method = RequestMethod.GET)
	 public ViewBabyResponseObject getLiveVideoCredentialForichr(@PathVariable("uhid") String uhid){
		 ViewBabyResponseObject viewBabyResponseObject = new ViewBabyResponseObject();
		try {
			String videoURL = deviceDataService.getLiveVideoCredentialForichr(uhid);
			viewBabyResponseObject = remoteViewService.getLiveVideoMessage(uhid, videoURL);
		}catch (Exception e){
			System.out.println("Exception Caught:"+e);
		}
		return viewBabyResponseObject;
	}

	@RequestMapping(value="inicu/getServerTime",method = RequestMethod.POST)
	public Object getServertime(){
		 InicuServerTimeJson jsonObject = deviceDataService.getServerTime();
		 return jsonObject;
	}

	// @RequestMapping(value="inicu/closeSocket",method = RequestMethod.GET)
	// public Object closeSocket(){
	// System.out.println("socket close called");
	// String resp ="socket closed";
	// try{
	// BasicConstants.hl7Socket.close();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// return resp;
	// }

	// @RequestMapping(value="inicu/openSocket",method = RequestMethod.GET)
	// public Object openSocket(){
	// String resp ="socket opened";
	// new TestNihonKohdenMonitor().startMonitoring(null);
	// return resp;
	// }

	@RequestMapping(value = "/inicu/getBoardProperties/{unitId}", method = RequestMethod.GET)
	public Object getBoardProperties(@PathVariable("unitId") String unitId) {
		DeviceProperties dev = new DeviceProperties();
		dev.setKafka_host("192.168.9.9");
		dev.setKafka_port("2141");
		dev.setKafka_topic("inicu_device_topic");
		List<String> listDevices = new ArrayList<>();
		listDevices.add("MASIMO");
		listDevices.add("SLE5000");
		dev.setListofDevices(listDevices);
		return dev;
	}

	@RequestMapping(value = "/senddata/", method = RequestMethod.POST)
	public void getStringFromArduino(@RequestBody String data) {
		System.out.println("data received ---" + data);
		Date date = new Date();
		deviceDataService.saveDataToCassandra(date, data, "box1");
	}

	@RequestMapping(value = "/receiveData", method = RequestMethod.POST)
	public void receivceDeviceData(@RequestBody String jsonData) {
		System.out.println("data received --" + jsonData);
		Date currentdatetime = new Date();
		System.out.println("time in GMT" + currentdatetime.toGMTString());
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			JSONObject json = new JSONObject(jsonData);
			if(json != null){ 
				json.put("server_time", dateFormat.format(currentdatetime).toString());
				System.out.println("Now data received with server time --" + jsonData);
			}
			deviceDataService.saveDataReceivedFromBox(json);
		} catch (JSONException e) {
			System.out.println("Device Data Received is not a valid json format.");
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/receiveMultipleData", method = RequestMethod.POST)
	public void receivceDeviceMultipleData(@RequestBody String jsonArrayData) {
//		System.out.println("Multiple Data Received = " + jsonArrayData);
		Date currentdatetime = new Date();
		System.out.println("Time In GMT" + currentdatetime.toGMTString());
		try {
			JSONArray jsonArray = new JSONArray(jsonArrayData);
			deviceDataService.saveMultipleDataReceivedFromBox(jsonArray);
		} catch (JSONException e) {
			System.out.println("Device Data Received is not a valid json format.");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getCameraUhid/{boxName}", method = RequestMethod.GET)
	public String getCameraUhid(@PathVariable("boxName") String boxName) {
		String uhid = "";
		uhid = deviceDataService.getCameraUhid(boxName);

		if(uhid == null){
			uhid = "";
		}
		return uhid;
	}
	
	@RequestMapping(value = "/getCapturingStatus/{uhid}/", method = RequestMethod.GET)
	public String getCapturingStatus(@PathVariable("uhid") String uhid) {
		String isStartCapturing = deviceDataService.getCapturingStatus(uhid);
		if(isStartCapturing == null || isStartCapturing == "") {
			isStartCapturing = "false";
		}
		return isStartCapturing;
	}
	
	@RequestMapping(value = "inicu/setCapturingStatus/", method = RequestMethod.POST)
	public void setCapturingStatus(@RequestBody String capturingStatusString) {
		try {
			JSONObject jsonCapturingStatus = new JSONObject(capturingStatusString);
			deviceDataService.setCapturingStatus(jsonCapturingStatus);
		} catch (JSONException e) {
			System.out.println("Camera Data Received is not a valid json format.");
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/receiveCameraData/", method = RequestMethod.POST)
	public void receiveCameraData(@RequestBody String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			deviceDataService.saveImageReceivedFromBoxForCamera(json);
		} catch (JSONException e) {
			System.out.println("Camera Data Received is not a valid json format.");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/inicu/getBabyVideo/{uhid}/", method = RequestMethod.GET)
	public List<String> getBabyVideo(@PathVariable("uhid") String uhid) {
		List<String> framesList = new ArrayList<String>();
		framesList = deviceDataService.getCameraFeed(uhid);
		String videoURL = deviceDataService.getLiveVideoCredentialForichr(uhid);
		if(!BasicUtils.isEmpty(videoURL)) {
			framesList.add(videoURL);
		}
		return framesList;
	}

	@RequestMapping(value = "/receiveDataPilot", method = RequestMethod.POST)
	public void receivceDeviceDataPilot(@RequestBody String jsonData) {
		System.out.println("data received from pilot box ---" + jsonData);
		try {
			deviceDataService.saveVentDataToPilotPostgres(jsonData);
		} catch (Exception e) {
			System.out.println("Device Data Received is not a valid json format.");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/receiveMultipleDataBloodGas", method = RequestMethod.POST)
	public void receivceMutlipleBloodGasDeviceDataPostgres(@RequestBody String jsonArrayData) {
//		System.out.println("Multiple Data Received = " + jsonArrayData);
		try {
			deviceDataService.saveMutlipleBloodGasDeviceDataPostgres(jsonArrayData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/receiveDataBloodGas", method = RequestMethod.POST)
	public void receivceDeviceDataPostgres(@RequestBody String jsonData) {
		System.out.println("data received:" + jsonData);
		try {
			deviceDataService.saveDeviceDataPostgres(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/inicu/getnchidscore/{uhid}", method = RequestMethod.GET)
	public Object test(@PathVariable("uhid") String uhid) {
		NICHDScoreJson nichd = new NICHDScoreJson();
		if (BasicConstants.props.getProperty(BasicConstants.PROP_ANALYTICS_START).equalsIgnoreCase("true")) {
			nichd = analyticsService.getnchidScore(uhid);
		}
		return nichd;
	}

	@RequestMapping(value = "/inicu/getcribscore/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<String> getCribScore(@PathVariable("uhid") String uhid) {
		String cribScore = null;
		if (!BasicUtils.isEmpty(uhid)
				&& BasicConstants.props.getProperty(BasicConstants.PROP_ANALYTICS_START).equalsIgnoreCase("true")) {
			cribScore = analyticsService.getCribScore(uhid);
		}
		return new ResponseEntity<String>(cribScore, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/disconnectInicuDevice/{date}", method = RequestMethod.POST)
	public Object disconnectInicuDevice(@RequestBody AddDeviceTemplateJson device, @PathVariable("date") String date) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		if (!BasicUtils.isEmpty(device)) {
			rObj = deviceDataService.disconnectInicuDevices(device, date);
		} else {
			rObj.setMessage("Oops! device data is empty");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return rObj;
	}
	
	@RequestMapping(value = "/inicu/importDeviceData/{uhid}/{filePath}/{referenceDate}/", method = RequestMethod.POST)
	public ResponseMessageObject importDeviceData(@RequestBody JSONObject device, @PathVariable("uhid") String uhid, @PathVariable("filePath") String filePath, @PathVariable("referenceDate") String referenceDate) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		if (!BasicUtils.isEmpty(filePath)) {
			rObj = deviceDataService.importDeviceData(uhid, filePath, referenceDate);
		} else {
			rObj.setMessage("Path is not correct");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return rObj;
	}


	@RequestMapping(value = "/inicu/saveQuestionnaireResult/", method = RequestMethod.POST)
	public ResponseEntity<String> saveQuestionnaireResult(@RequestBody QuestionnaireResult device) {
		deviceDataService.saveQuestionnaireResult(device);
		return new ResponseEntity<String>("Saved", HttpStatus.OK);
	}
}
