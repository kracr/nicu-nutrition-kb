package com.inicu.postgres.controller;

import com.google.gson.JsonObject;
import com.inicu.cassandra.models.AddDeviceMasterJson;
import com.inicu.cassandra.models.AddDeviceTemplateJson;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.device.utility.RegistrationConstants;
import com.inicu.models.*;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.SettingsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import static org.quartz.JobBuilder.newJob;

@RestController
public class SettingsController {

	@Autowired
	private SettingsService settingService;

	@Autowired
	DeviceDataService deviceDataService;

	@Autowired
	PatientDao patientDao;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	SettingDao settingDao;

	@Autowired
	private SimpMessagingTemplate template;

	@RequestMapping(value = "/inicu/addBed/{roomno}/{bedno}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> addBed(@PathVariable("roomno") String roomno,
																	@PathVariable String bedno, @PathVariable("loggedUser") String loggedUser,
																	@PathVariable("branchName") String branchName) {
		{
			ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
			try {
				responseMessageObj = settingService.addNicuBed(roomno, bedno, loggedUser, branchName);
				System.out.println(loggedUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

		}
	}

	@RequestMapping(value = "/inicu/getBedData/{loggedInUserId}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> getBedData(
			@PathVariable("loggedInUserId") String loggedUser, @PathVariable("branchName") String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
			responseMessageObj.setMessage("Get all data successfully");
			responseMessageObj.setReturnedObject(settingService.getBedList(loggedUser, branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/addRoom/{roomno}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> addNicuRoom(@PathVariable String roomno,
																		 @PathVariable String loggedUser, @PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.addRoom(roomno, loggedUser, branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "inicu/generateBoxDetails", method = RequestMethod.POST)
	public ResponseEntity<NewDeviceJSON> generateBoxDetails() {
		NewDeviceJSON boxJson = new NewDeviceJSON();
		try {
			RegisterDeviceDropdownJSon registeredDevices = new RegisterDeviceDropdownJSon();
			String id = settingService.generateBoxName();
			String name = RegistrationConstants.NEO.trim() + id;
			String serial = settingService.generateSerial();
			registeredDevices = deviceDataService.getRegisteredDevice().getDropDowns();
			boxJson.setDropdown(registeredDevices);
			BoxJSON box = new BoxJSON();
			box.setBox_name(name);
			box.setBox_serial(serial);
			box.setId(id);
			BoardDetailJson board1 = new BoardDetailJson();
			box.setBoard1(board1);
			BoardDetailJson board2 = new BoardDetailJson();
			box.setBoard2(board2);
			boxJson.setBox(box);
			List<String> macs = settingService.getListOfAVLmacids();
			boxJson.setMacid(macs);
			// boxJson.getBox().setBox_name(name);
			// boxJson.getBox().setBox_serial(serial);
			// boxJson.getBox().getBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<NewDeviceJSON>(boxJson, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/editBox/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> editNewBox(@RequestBody String boxdetail,
															@PathVariable String branchName) {

		ResponseMessageObject resp = new ResponseMessageObject();
		System.out.println("@@@ " + boxdetail);
		BoxJSON box = new BoxJSON(boxdetail);
		String id = settingService.addBox(box, branchName);
		if (!BasicUtils.isEmpty(box.getBoard1())) {
			System.out.println("Board Name = " + box.getBoard1().getBoardName());
			String getBoardQuery = "select obj from DeviceDetail obj where deviceserialno='" + box.getBox_serial()
					+"' and description='" + box.getBoard1().getBoardName() + "' ";
			List<DeviceDetail> listBoards = patientDao.getListFromMappedObjNativeQuery(getBoardQuery);
			DeviceDetail devDetail = new DeviceDetail();
			if (!BasicUtils.isEmpty(listBoards)) {
				devDetail = listBoards.get(0);
			}

			devDetail.setAvailable(true);
			devDetail.setDescription(box.getBoard1().getBoardName());
			devDetail.setDevicebrand(box.getBoard1().getBrandName());
			devDetail.setDevicename(box.getBoard1().getDeviceName());
			devDetail.setDeviceserialno(box.getBox_serial());
			devDetail.setDevicetype(box.getBoard1().getDeviceType());
			devDetail.setIpaddress(box.getBoard1().getMacId());
			devDetail.setInicu_deviceid(Long.valueOf(id));
			devDetail.setBranchname(branchName);
			devDetail.setUsbport(box.getBoard1().getUsbport());
			devDetail.setBaudrate(box.getBoard1().getBaudrate());
			devDetail.setParity(box.getBoard1().getParity());
			devDetail.setDatabits(box.getBoard1().getDatabits());
			devDetail.setStopbits(box.getBoard1().getStopbits());

			try {
				inicuDao.saveObject(devDetail);
				System.out.println("!!!SAVE BOARD1");
				resp.setType(BasicConstants.MESSAGE_SUCCESS);
				resp.setMessage("BOX SAVED");
			} catch (Exception e) {
				resp.setType(BasicConstants.MESSAGE_FAILURE);
				resp.setMessage("EXCEPTION");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!BasicUtils.isEmpty(box.getBoard2())) {
			System.out.println("Board Name = " + box.getBoard1().getBoardName());
			String getBoardQuery = "select obj from DeviceDetail obj where deviceserialno='" + box.getBox_serial()
					+"' and description='" + box.getBoard2().getBoardName() + "' ";
			List<DeviceDetail> listBoards = patientDao.getListFromMappedObjNativeQuery(getBoardQuery);
			DeviceDetail devDetail = new DeviceDetail();
			if (!BasicUtils.isEmpty(listBoards)) {
				devDetail = listBoards.get(0);
			}

			devDetail.setAvailable(true);
			devDetail.setDescription(box.getBoard2().getBoardName());
			devDetail.setDevicebrand(box.getBoard2().getBrandName());
			devDetail.setDevicename(box.getBoard2().getDeviceName());
			devDetail.setDeviceserialno(box.getBox_serial());
			devDetail.setDevicetype(box.getBoard2().getDeviceType());
			devDetail.setIpaddress(box.getBoard2().getMacId());
			devDetail.setInicu_deviceid(Long.valueOf(id));
			devDetail.setBranchname(branchName);
			devDetail.setUsbport(box.getBoard2().getUsbport());
			devDetail.setBaudrate(box.getBoard2().getBaudrate());
			devDetail.setParity(box.getBoard2().getParity());
			devDetail.setDatabits(box.getBoard2().getDatabits());
			devDetail.setStopbits(box.getBoard2().getStopbits());

			try {
				inicuDao.saveObject(devDetail);
				System.out.println("!!!SAVE BOARD2");
				resp.setType(BasicConstants.MESSAGE_SUCCESS);
				resp.setMessage("BOX SAVED");
			} catch (Exception e) {
				resp.setType(BasicConstants.MESSAGE_FAILURE);
				resp.setMessage("EXCEPTION");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new ResponseEntity<ResponseMessageObject>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/deleteBox/{neo_serial}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteBox(@PathVariable String neo_serial) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		boolean available = false;
		try {
			if (!BasicUtils.isEmpty(neo_serial)) {
				responseMessageObj = settingService.deleteBox(neo_serial);
			} else {
				responseMessageObj.setMessage("couldnt delete box");
				responseMessageObj.setType(BasicConstants.MESSAGE_FAILURE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveNewBox/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveNewBox(@RequestBody String boxdetail,
															@PathVariable String branchName) {
		ResponseMessageObject resp = new ResponseMessageObject();
		System.out.println("@@@ " + boxdetail);
		BoxJSON box = new BoxJSON(boxdetail);
		String boxName = "";
		if (box.isTinyNeo()) {
			boxName = "CAM-" + box.getId();
		} else {
			boxName = "neo-" + box.getId();
		}

		String SerialName = "NAP" + box.getId();
		box.setBox_name(boxName);
		box.setBox_serial(SerialName);
		String id = settingService.addBox(box, branchName);
		if (!BasicUtils.isEmpty(box.getBoard1())) {
			System.out.println("Board Name = " + box.getBoard1().getBoardName());

			if (!BasicUtils.isEmpty(box.getBoard1().getDeviceName())
					|| (!BasicUtils.isEmpty(box.getBoard1().getDeviceType()) && "Camera".equals(box.getBoard1().getDeviceType()))) {
				StringBuilder result = new StringBuilder("SELECT ref.deviceid FROM device_detail as ref WHERE ");
				if (box.isTinyNeo()) {
					result.append(" ref.tinydeviceserialno='" + SerialName);
				} else {
					result.append(" ref.deviceserialno='" + SerialName);
				}
				result.append("' and description='" + box.getBoard1().getBoardName() + "' and branchname = '" + branchName + "'");
				List<Object> finalSet = settingDao.getListFromNativeQuery(result.toString());
				if (!BasicUtils.isEmpty(finalSet)) {
					resp.setType(BasicConstants.MESSAGE_SUCCESS);
					resp.setMessage("BOX IS ALREADY SAVED");
				} else {
					DeviceDetail devDetail = new DeviceDetail();
					devDetail.setAvailable(true);
					devDetail.setDescription(box.getBoard1().getBoardName());
					devDetail.setDevicebrand(box.getBoard1().getBrandName());
					devDetail.setDevicename(box.getBoard1().getDeviceName());
					if (box.isTinyNeo()) {
						devDetail.setTinydeviceserialno(box.getBox_serial());
					} else {
						devDetail.setDeviceserialno(box.getBox_serial());
					}
					devDetail.setDevicetype(box.getBoard1().getDeviceType());
					devDetail.setIpaddress(box.getBoard1().getMacId());
					devDetail.setInicu_deviceid(Long.valueOf(id));
					devDetail.setBranchname(branchName);
					devDetail.setUsbport(box.getBoard1().getUsbport());
					devDetail.setBaudrate(box.getBoard1().getBaudrate());
					devDetail.setParity(box.getBoard1().getParity());
					devDetail.setDatabits(box.getBoard1().getDatabits());
					devDetail.setStopbits(box.getBoard1().getStopbits());

					try {
						patientDao.saveObject(devDetail);
						System.out.println("!!!SAVE BOARD1");
						resp.setType(BasicConstants.MESSAGE_SUCCESS);
						resp.setMessage("BOX SAVED");
					} catch (Exception e) {
						resp.setType(BasicConstants.MESSAGE_FAILURE);
						resp.setMessage("EXCEPTION");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		if (!BasicUtils.isEmpty(box.getBoard2())) {
			System.out.println("Board Name = " + box.getBoard2().getBoardName());
			if (!BasicUtils.isEmpty(box.getBoard2().getDeviceName())
					|| (!BasicUtils.isEmpty(box.getBoard2().getDeviceType()) && "Camera".equals(box.getBoard2().getDeviceType()))) {
				StringBuilder result = new StringBuilder("SELECT ref.deviceid FROM device_detail as ref WHERE ");
				if (box.isTinyNeo()) {
					result.append(" ref.tinydeviceserialno='" + SerialName);
				} else {
					result.append(" ref.deviceserialno='" + SerialName);
				}
				result.append("' and description='" + box.getBoard2().getBoardName() + "' and branchname = '" + branchName + "'");
				List<Object> finalSet = settingDao.getListFromNativeQuery(result.toString());
				if (!BasicUtils.isEmpty(finalSet)) {
					resp.setType(BasicConstants.MESSAGE_SUCCESS);
					resp.setMessage("BOX IS ALREADY SAVED");
				} else {
					DeviceDetail devDetail = new DeviceDetail();
					devDetail.setAvailable(true);
					devDetail.setDescription(box.getBoard2().getBoardName());
					devDetail.setDevicebrand(box.getBoard2().getBrandName());
					devDetail.setDevicename(box.getBoard2().getDeviceName());
					if (box.isTinyNeo()) {
						devDetail.setTinydeviceserialno(box.getBox_serial());
					} else {
						devDetail.setDeviceserialno(box.getBox_serial());
					}
					devDetail.setDevicetype(box.getBoard2().getDeviceType());
					devDetail.setIpaddress(box.getBoard2().getMacId());
					devDetail.setInicu_deviceid(Long.valueOf(id));
					devDetail.setBranchname(branchName);
					devDetail.setUsbport(box.getBoard2().getUsbport());
					devDetail.setBaudrate(box.getBoard2().getBaudrate());
					devDetail.setParity(box.getBoard2().getParity());
					devDetail.setDatabits(box.getBoard2().getDatabits());
					devDetail.setStopbits(box.getBoard2().getStopbits());
					try {
						patientDao.saveObject(devDetail);
						System.out.println("!!!SAVE BOARD2");
						resp.setType(BasicConstants.MESSAGE_SUCCESS);
						resp.setMessage("BOX SAVED");
					} catch (Exception e) {
						resp.setType(BasicConstants.MESSAGE_FAILURE);
						resp.setMessage("EXCEPTION");

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return new ResponseEntity<ResponseMessageObject>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/connectRegisteredDevice/{boxName}/{uhid}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<AddDeviceMasterJson> mapDevice(@PathVariable("boxName") String boxName,
														 @PathVariable("uhid") String uhid, @PathVariable("branchName") String branchName) {

		AddDeviceTemplateJson deviceData = new AddDeviceTemplateJson();
		deviceData.setUhid(uhid);
		if (!BasicUtils.isEmpty(uhid) && !BasicUtils.isEmpty(boxName)) {
			String fetchBoxIdQuery = "select obj from RefInicuBbox as obj where bboxname='" + boxName + "' ";
			List<RefInicuBbox> boxesList = patientDao.getListFromMappedObjNativeQuery(fetchBoxIdQuery);

			if (boxesList.size() > 0) {
				RefInicuBbox box = boxesList.get(0);
				deviceData.setDeviceName(box.getBboxId().intValue());
			}
			String fetchBedIdQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "' "
					+ " and activestatus='t' and admissionstatus='t' ";
			List<BabyDetail> babiesList = patientDao.getListFromMappedObjNativeQuery(fetchBedIdQuery);

			if (babiesList.size() > 0) {
				BabyDetail baby = babiesList.get(0);
				deviceData.setBedId(baby.getNicubedno());
			}

		}

		AddDeviceMasterJson masterJson = null;
		ResponseMessageObject robj = new ResponseMessageObject();
		robj.setMessage("Alert! Selected device could not be found.");
		robj.setType(BasicConstants.MESSAGE_FAILURE);
		if (!BasicUtils.isEmpty(deviceData)) {
			robj = deviceDataService.mapInicuDeviceWithPatient(deviceData, "");
			masterJson = deviceDataService.getDeviceTemplateData(deviceData.getUhid(), deviceData.getBedId(),
					branchName);
			masterJson.setResponse(robj);
		}
		return new ResponseEntity<AddDeviceMasterJson>(masterJson, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getDeviceHeartBeatUsingFromTo/", method = RequestMethod.POST)
	public ResponseEntity<List<DeviceHeartbeat>> getHeartBeatFromTimePeriod(
			@RequestBody DeviceHeartBeatModel hbFromToPeriod) {
		System.out.println(hbFromToPeriod.toString());
		System.out.println(hbFromToPeriod.getBoxSerialNo());
		System.out.println(hbFromToPeriod.getHeartBeatFrom().getTimezoneOffset());
		System.out.println(hbFromToPeriod.getHeartBeatTo());
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		List<DeviceHeartbeat> deviceHeartbeatList = null;

		try {

			responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
			deviceHeartbeatList = settingService.getDeviceHeartBeat(hbFromToPeriod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<List<DeviceHeartbeat>>(deviceHeartbeatList, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/getDeviceExceptions/{neo_serial}", method = RequestMethod.GET)
	public ResponseEntity<List<DeviceException>> getDeviceExceptions(@PathVariable("neo_serial") String boxSerialNo) {
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		List<DeviceException> deviceExceptionsList = null;
		try {
			responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
			deviceExceptionsList = settingService.getDeviceExceptions(boxSerialNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<DeviceException>>(deviceExceptionsList, HttpStatus.OK);

	}

	/*
	 * @RequestMapping(value = "/inicu/getDeviceHeartRate/{neo_serial}", method =
	 * RequestMethod.GET) public ResponseEntity<List<DeviceHeartbeat>>
	 * getDeviceHeartbeat(
	 *
	 * @PathVariable("neo_serial") String boxSerialNo) {
	 * System.out.println("looking for heartbeat for device ");
	 * ResponseMessageObject responseMessageObj = new ResponseMessageObject();
	 * List<DeviceHeartbeat>deviceHeartbeatList = null; try {
	 * responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
	 * deviceHeartbeatList = settingService.getDeviceHeartBeat(boxSerialNo);
	 * //deviceHeartbeatList = settingService.getDeviceExceptions(boxSerialNo);
	 *
	 * } catch (Exception e) { e.printStackTrace(); } return new
	 * ResponseEntity<List<DeviceHeartbeat>> (deviceHeartbeatList, HttpStatus.OK);
	 *
	 * }
	 */
	/*
	 * Get active babies with no device mapped to them
	 */
	// @RequestMapping(value = "/inicu/getUhidsNoDeviceList/", method =
	// RequestMethod.GET)
	// public ResponseEntity<List<Object>> getUhidNoDeviceList() {
	// ResponseMessageObject responseMessageObj = new ResponseMessageObject();
	// List<Object>uhidsListNoDevice = null;
	// try {
	// responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
	// uhidsListNoDevice = settingService.getUhidNoDeviceList();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return new ResponseEntity<List<Object>> (uhidsListNoDevice, HttpStatus.OK);
	//
	// }

	@RequestMapping(value = "/inicu/deleteBed/{bedid}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteNicuBed(@PathVariable String bedid,
																		   @PathVariable String loggedUser, @PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.deleteBed(bedid, loggedUser, branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/bed/changeBedStatus/{bedid}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> changeBedStatus(@PathVariable String bedid) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.changeStatus(bedid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/bed/changeBedRoomNo/{bedid}/{roomid}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> changeBedRoom(@PathVariable String bedid,
																		   @PathVariable String roomid, @PathVariable String loggedUser, @PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.roomChange(bedid, roomid, loggedUser, branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/settings/listMedicine", method = RequestMethod.POST)
	public ResponseEntity<DrugsListJson> getListMedicine() {
		DrugsListJson drugsList = new DrugsListJson();
		Medicines m = new Medicines();
		drugsList = settingService.getListOfMedicine();
		drugsList.setDrug(m);
		return new ResponseEntity<DrugsListJson>(drugsList, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/deleteRoom/{roomId}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteNicuRoom(@PathVariable String roomId,
																			@PathVariable String loggedUser, @PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.deleteRoom(roomId, loggedUser, branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/settings/deleteMedicine/{medid}/{loggedUserId}", method = RequestMethod.POST)
	public ResponseEntity<DrugsListJson> deleteMedicine(@PathVariable("medid") String medid,
														@PathVariable("loggedUserId") String loggedUserId) {

		DrugsListJson drugslist = new DrugsListJson();
		try {
			ResponseMessageObject obj = new ResponseMessageObject();
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage("Oops! could not be saved.");
			if (!BasicUtils.isEmpty(medid)) {
				obj = settingService.deleteMedicine(medid, loggedUserId);
			}
			drugslist = settingService.getListOfMedicine();

			drugslist.setMessage(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<DrugsListJson>(drugslist, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/settings/addMedicine/", method = RequestMethod.POST)
	public ResponseEntity<DrugsListJson> addMedicine(@RequestBody Medicines medicines) {
		DrugsListJson json = new DrugsListJson();
		try {
			ResponseMessageObject obj = settingService.addMedicine(medicines);
			json = settingService.getListOfMedicine();
			json.setMessage(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<DrugsListJson>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/settings/addBrand/", method = RequestMethod.POST)
	public ResponseEntity<DrugsListJson> addBrand(@RequestBody RefMedBrand brand) {
		DrugsListJson json = new DrugsListJson();
		ResponseMessageObject obj = new ResponseMessageObject();
		try {
			inicuDao.saveObject(brand);
			json = settingService.getListOfMedicine();
			obj.setMessage("Brand saved successfully");
			json.setMessage(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<DrugsListJson>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "inicu/settings/deleteAlterItemById/{alertId}",method = RequestMethod.GET)
	public GeneralResponseObject deleteAlertItemById(@PathVariable("alertId") Long alertId){
		GeneralResponseObject generalResponseObject=new GeneralResponseObject();
		try{
			generalResponseObject=settingService.deleteItemById(alertId);
		}catch (Exception e){
			e.printStackTrace();
		}
		return generalResponseObject;
	}

	@RequestMapping(value = "/inicu/settings/getsettingDetails/", method = RequestMethod.POST)
	public Object getSettingDetails(@RequestBody String details) {
		SettingMasterJsonObj obj = settingService.getSettingDetails(details);
		return obj;
	}

	@RequestMapping(value = "/inicu/settings/deleteJob/{type}/{uhid}/", method = RequestMethod.GET)
	public String deleteJob(@PathVariable("uhid") String uhid,@PathVariable("type") String type) {
		String result;
		try {
			result = deleteJobByUhid(uhid,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return  "Deleted";
	}

	public static String deleteJobByUhid(String uhid, String type) {
		String response= "Deleted";
		try {
			String companyId = BasicConstants.ICHR_SCHEMA;
			String requestType = "deleteJobWithUhid";
			// now send the notification

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("companyId", companyId));
			postParameters.add(new BasicNameValuePair("uid", uhid));
			postParameters.add(new BasicNameValuePair("requestType", requestType));
			postParameters.add(new BasicNameValuePair("jobName", type));
			postParameters.add(new BasicNameValuePair("message", null));
			postParameters.add(new BasicNameValuePair("smsMessage", null));
			postParameters.add(new BasicNameValuePair("smsReminderMsg",null));


			postParameters.add(new BasicNameValuePair("morningFromTime", null));
			postParameters.add(new BasicNameValuePair("morningToTime", null));
			postParameters.add(new BasicNameValuePair("eveningFromTime", null));
			postParameters.add(new BasicNameValuePair("eveningToTime", null));

			response = SimpleHttpClient.executeHttpPost(BasicConstants.PUSH_NOTIFICATION, postParameters);
			// now try making the post request using the ICHR android post method

			System.out.println("Notification Sent and response received is :" + response);
		}catch (Exception e){
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/inicu/settings/saveClinicalSettings/", method = RequestMethod.POST)
	public GeneralResponseObject saveClinicalSettings(@RequestBody List<ClinicalAlertSettings> clinicalSetting) {
		GeneralResponseObject obj=new GeneralResponseObject();
		try {
			 obj = settingService.saveClinicalSetting(clinicalSetting);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	@RequestMapping(value = "/inicu/settings/addSetting/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> addSetting(@RequestBody SettingMasterJsonObj settingObj) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		if (!BasicUtils.isEmpty(settingObj)) {
			rObj = settingService.saveSetting(settingObj);
		}

		return new ResponseEntity<ResponseMessageObject>(rObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/settings/savelogo/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveLogo(@RequestBody FormLogoPOJO logoObj, @PathVariable("branchName") String branchname) {

		JSONObject imgStr = new JSONObject();
		try {
			imgStr.put("image", logoObj.getData());
			imgStr.put("name", logoObj.getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ResponseMessageObject rObj = new ResponseMessageObject();

		rObj.setMessage("Oops! could not save image.");
		rObj.setType(BasicConstants.MESSAGE_FAILURE);
		if (!BasicUtils.isEmpty(imgStr)) {
			try {
//				JSONObject jsonObj = new JSONObject(imgStr);
				if (!BasicUtils.isEmpty(imgStr.get("image"))) {
					rObj = settingService.saveHospitalLogo(imgStr.get("image").toString(), branchname);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				rObj.setMessage(e.getMessage());
			}
		}
		return new ResponseEntity<ResponseMessageObject>(rObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/settings/gethospitallogo/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<KeyValueObj>> getHospitalLogo(@PathVariable String branchName) {
		List<KeyValueObj> imageData = null;
		imageData = settingService.getHospitalLogo(branchName);
		return new ResponseEntity<List<KeyValueObj>>(imageData, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getTestsDetails/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<TestsListJSON> getTestsDetails(@PathVariable String branchName) {
		TestsListJSON json = new TestsListJSON();
		try {
			json = settingService.getTestsDetails(branchName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<TestsListJSON>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveInvestigationPannel/{pannelName}/{loggedUser}/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveInvestigationPannel(
			@RequestBody List<TestPOJO> testDetails, @PathVariable String pannelName, @PathVariable String loggedUser,
			@PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.saveInvestigationPannel(pannelName, loggedUser, branchName,
					testDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/deleteInvestigationPannel/{pannelName}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteInvestigationPannel(@PathVariable String pannelName,
																					   @PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.deleteInvestigationPannel(pannelName, branchName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/editInvestigationPannel/{oldPannelName}/{newPannelName}/{loggedUser}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> editInvestigationPannel(
			@RequestBody List<TestPOJO> testDetails, @PathVariable String oldPannelName,
			@PathVariable String newPannelName, @PathVariable String loggedUser, @PathVariable String branchName) {
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		try {
			responseMessageObj = settingService.editInvestigationPannel(oldPannelName, newPannelName, loggedUser,
					branchName, testDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseMessageObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/mapTests/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<TestsListJSON> mapTests(@PathVariable String branchName,
												  @RequestBody MappedListPOJO mappedJson) {
		TestsListJSON obj = new TestsListJSON();
		try {
			ResponseMessageObject json = settingService.mapTests(mappedJson);
			obj = settingService.getTestsDetails(branchName);
			obj.setMessage(json.getMessage());
			obj.setType(json.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<TestsListJSON>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addInicuTest/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<TestsListJSON> addInicuTests(@PathVariable String branchName,
													   @RequestBody RefTestslist newTest) {
		TestsListJSON obj = new TestsListJSON();
		try {
			if (!BasicUtils.isEmpty(newTest.getAssesmentCategory()) && !BasicUtils.isEmpty(newTest.getTestname())) {
				newTest.setIsactive(true);
				String uuid = UUID.randomUUID().toString();
				newTest.setTestid(uuid);
				settingDao.saveObject(newTest);
				obj = settingService.getTestsDetails(branchName);
				obj.setMessage(BasicConstants.MESSAGE_SUCCESS);
				obj.setType("Test added successfully");
			} else {
				obj.setMessage(BasicConstants.MESSAGE_FAILURE);
				obj.setType("Parameters are unknown");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.setMessage(BasicConstants.MESSAGE_FAILURE);
			obj.setType("Parameters are not correct");
		}
		return new ResponseEntity<TestsListJSON>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getEmailData/{branchname}", method = RequestMethod.GET)
	public ResponseEntity<EmailManagementJSON> getEmailData(@PathVariable String branchname) {
		EmailManagementJSON returnObj = settingService.getEmailData(branchname);
		return new ResponseEntity<EmailManagementJSON>(returnObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/saveEmailData/", method = RequestMethod.POST)
	public ResponseEntity<EmailManagementJSON> saveEmailData(@RequestBody NotificationEmail emailObj) {
		EmailManagementJSON returnObj = settingService.saveEmailData(emailObj);
		return new ResponseEntity<EmailManagementJSON>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingOuputParameters/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<RefNursingOutputParameters> getNursingOuputParameters(
			@PathVariable("branchName") String branchName) {
		RefNursingOutputParameters returnObj = settingService.getNursingOuputParameters(branchName);
		return new ResponseEntity<RefNursingOutputParameters>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/editUhid/{oldUhid}/{newUhid}/", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> updateUhid(@PathVariable("oldUhid") String oldUhid,
															@PathVariable("newUhid") String newUhid) {
		ResponseMessageObject returnObj = settingService.updateUhid(oldUhid, newUhid);
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/displayDischargedPatient/{dischargedUhid}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<DisplayDischargedPatientJSON> displayDetails(
			@PathVariable("dischargedUhid") String dischargedUhid, @PathVariable("branchName") String branchName) {
		DisplayDischargedPatientJSON returnObj = settingService.displayDetails(dischargedUhid, branchName);
		return new ResponseEntity<DisplayDischargedPatientJSON>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/readmitPatient/{dischargedUhid}/{branchName}/{nicubedno}/{roomnumber}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> readmit(@PathVariable("dischargedUhid") String dischargedUhid,
														 @PathVariable("branchName") String branchName, @PathVariable("nicubedno") String nicubedno, @PathVariable("roomnumber") String roomnumber) {
		ResponseMessageObject returnObj = settingService.readmit(dischargedUhid, branchName, nicubedno, roomnumber);
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/checkAssessmentStatus/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<AssessmentStatusPOJO> checkAssessmentStatus(
			@PathVariable("uhid") String uhid) {
		AssessmentStatusPOJO returnObj = settingService.checkAssessmentStatus(uhid);
		return new ResponseEntity<AssessmentStatusPOJO>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/updateAssessmentStatus/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> updateAssessmentStatus(
			@PathVariable("uhid") String uhid) {
		ResponseMessageObject returnObj = settingService.updateAssessmentStatus(uhid);
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/validateDischargeOfBaby/{uhidToDischarge}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<DisplayPatientDataJSON> validateDischarge(
			@PathVariable("uhidToDischarge") String uhidToDischarge, @PathVariable("branchName") String branchName) {
		DisplayPatientDataJSON returnObj = settingService.validateDischarge(uhidToDischarge, branchName);
		return new ResponseEntity<DisplayPatientDataJSON>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/dischargePatient/{uhidToDischarge}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> dischargePatient(@PathVariable("uhidToDischarge") String uhidToDischarge,
																  @PathVariable("branchName") String branchName) {
		ResponseMessageObject returnObj = settingService.dischargePatient(uhidToDischarge, branchName);
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addHelplineNumbers/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> addHelplineNumbers(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getContactNumber()))
					obj.setContactNumber(hospital.getContactNumber());
				if (!BasicUtils.isEmpty(hospital.getHelplineNumber()))
					obj.setHelplineNumber(hospital.getHelplineNumber());
				if (!BasicUtils.isEmpty(hospital.getLactationNumber()))
					obj.setLactationNumber(hospital.getLactationNumber());
				if (!BasicUtils.isEmpty(hospital.getLactationName()))
					obj.setLactationName(hospital.getLactationName());
				if (!BasicUtils.isEmpty(hospital.getHelplineNumberSecond()))
					obj.setHelplineNumberSecond(hospital.getHelplineNumberSecond());
				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Numbers added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Numbers cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Numbers cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setShiftTimings/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setShiftTimings(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getFromTime()))
					obj.setFromTime(hospital.getFromTime());
				if (!BasicUtils.isEmpty(hospital.getToTime()))
					obj.setToTime(hospital.getToTime());
				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Time is configured successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Time cannot be configured.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Time cannot be configured.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setNursingPrintFormat", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setNursingPrintFormat(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getNursingPrintFormat()))
					obj.setNursingPrintFormat(hospital.getNursingPrintFormat());

				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Print Format added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Print Format cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Print Format cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setProgressNotesFormat", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setProgressNotesFormat(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getProgressNotesFormat()))
					obj.setProgressNotesFormat(hospital.getProgressNotesFormat());

				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Print Format added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Print Format cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Print Format cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/setNutritionalType", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setNutritionalType(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getNutritionalType()))
					obj.setNutritionalType(hospital.getNutritionalType());

				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Feeding Format added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Feeding Format cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Feeding Format cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/setreportPrint", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setreportPrint(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getReportPrint()))
					obj.setReportPrint(hospital.getReportPrint());

				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Print Format added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Print Format cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Print Format cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setPrediction", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setPrediction(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='"
					+ hospital.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(queryhospitalName);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);

				if (!BasicUtils.isEmpty(hospital.getPredictions()))
					obj.setPredictions(hospital.getPredictions());

				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Predictions added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Predictions cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Predictions cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addSpaceInPrint", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> addSpaceInPrint(@RequestBody RefHospitalbranchname hospital) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
				inicuDao.saveObject(hospital);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Print Format added successfully.");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/setNursingOutputParameters", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setNursingOutputParameters(
			@RequestBody RefNursingOutputParameters parameter) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			inicuDao.saveObject(parameter);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Parameters Format added successfully.");

		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Parameters Format cannot be added.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getApneaEventParameters/{branchName}/", method = RequestMethod.GET)
	public ResponseEntity<RefHospitalbranchname> getApneaEventParameters(
			@PathVariable("branchName") String branchName) {
		RefHospitalbranchname returnObj = settingService.getApneaEventParameters(branchName);
		return new ResponseEntity<RefHospitalbranchname>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setApneaEventParameters", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> setApneaEventParameters(@RequestBody RefHospitalbranchname parameter) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String querySetApneaEventParameters = "select obj from RefHospitalbranchname obj where branchname='"
					+ parameter.getBranchname() + "'";
			List<RefHospitalbranchname> refHospitalBranchNameList = settingDao
					.getListFromMappedObjNativeQuery(querySetApneaEventParameters);
			if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
				RefHospitalbranchname obj = refHospitalBranchNameList.get(0);
				obj.setSpo2(parameter.getSpo2());
				obj.setHr(parameter.getHr());
				obj.setDuration(parameter.getDuration());
				inicuDao.saveObject(obj);
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Numbers added successfully.");
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Numbers cannot be added.");
			}
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Numbers cannot be added.");
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	// Nurse Shift Mapping in Admin panel
	@RequestMapping(value = "/inicu/getDoctorShiftMapping/{branchName}/{dateOfShift}", method = RequestMethod.GET)
	public ResponseEntity<GeneralResponseObject> getDoctorShiftMapping(@PathVariable("branchName") String branchName,
																	  @PathVariable("dateOfShift") Timestamp dateOfShift) {
		GeneralResponseObject responseMessageObj = new GeneralResponseObject();
		NurseShiftsPOJO nurseShiftsPOJO = null;
		try {
			responseMessageObj = settingService.getDoctorShiftMapping(branchName, dateOfShift);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<GeneralResponseObject>(responseMessageObj, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/getNurseShiftMapping/{branchName}/{dateOfShift}", method = RequestMethod.GET)
	public ResponseEntity<GeneralResponseObject> getNurseShiftMapping(@PathVariable("branchName") String branchName,
																@PathVariable("dateOfShift") Timestamp dateOfShift) {
		GeneralResponseObject responseMessageObj = new GeneralResponseObject();
		NurseShiftsPOJO nurseShiftsPOJO = null;
		try {
			responseMessageObj = settingService.getNurseShiftMapping(branchName, dateOfShift);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<GeneralResponseObject>(responseMessageObj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/saveNurseShiftMapping/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveNurseShiftMapping(
			@RequestBody List<UserShiftMapping> nurseShiftList) {
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		responseMessageObj = settingService.saveNurseShiftMapping(nurseShiftList);
		return new ResponseEntity<ResponseMessageObject>(responseMessageObj, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/nurse/shiftDetails/{branchName}/{dateOfShift}/{nurseId}", method = RequestMethod.GET)
	public ResponseEntity<NurseShiftDetail> getNurseShiftDetail(@PathVariable("branchName") String branchName,
																@PathVariable("dateOfShift") String dateOfShift , @PathVariable("nurseId") Integer nurseId) {
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		NurseShiftDetail nurseShiftDetail= null;
		try {
			responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
			nurseShiftDetail = settingService.getNurseDetail(branchName, dateOfShift ,nurseId );

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<NurseShiftDetail>(nurseShiftDetail, HttpStatus.OK);

	}

	// Nurse Baby Mapping in Admin Panel

	@RequestMapping(value = "/inicu/getNurseBabyMapping/{dateOfShift}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<GeneralResponseObject> getNurseBabyMapping(@PathVariable("dateOfShift") Timestamp dateOfShift,
																   @PathVariable("branchName") String branchName) {
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		NurseBabyMasterPOJO masterPOJO = null;
		try {
			generalResponseObject = settingService.getNurseBabyMapping(dateOfShift , branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<GeneralResponseObject>(generalResponseObject, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/nurseBaby/map/{dateOfShift}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<List<NurseBabyPOJO>> getAllMappedNurseBaby(@PathVariable("dateOfShift") Timestamp dateOfShift,
																	 @PathVariable("branchName") String branchName) {
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		List<NurseBabyPOJO> masterPOJO = null;
		try {
			masterPOJO = settingService.getAllMappedNurseBaby(dateOfShift , branchName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<NurseBabyPOJO>>(masterPOJO, HttpStatus.OK);

	}



	@RequestMapping(value = "/inicu/saveNurseBabyMapping/{dateOfShift}/{branchName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveNurseBabyMapping(@PathVariable("dateOfShift") Timestamp dateOfShift ,
																	  @PathVariable("branchName") String branchName,@RequestBody List<NurseBabyPOJO> nurseBabyPOJO) {
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		responseMessageObj = settingService.saveNurseBabyMapping(nurseBabyPOJO ,dateOfShift, branchName );
		return new ResponseEntity<ResponseMessageObject>(responseMessageObj, HttpStatus.OK);
	}

	// Nurse Shift Settings in Admin Panel

	@RequestMapping(value = "/inicu/getNurseShiftSettings/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<NurseShiftSettingPOJO> getNurseShiftSettings(@PathVariable("branchName") String branchName) {
		NurseShiftSettingPOJO masterPOJO = new NurseShiftSettingPOJO();
		try {
			masterPOJO = settingService.getNurseShiftSettings(branchName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<NurseShiftSettingPOJO>(masterPOJO, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/saveNurseShiftSettings/{branchName}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveNurseShiftSettings(@PathVariable("branchName") String branchName,
																		@RequestBody NurseShiftSettings nurseShiftSettings) {
		ResponseMessageObject responseMessageObj = new ResponseMessageObject();
		try {
			if (!BasicUtils.isEmpty(nurseShiftSettings)) {
				responseMessageObj = settingService.saveNurseShiftSettings(branchName, nurseShiftSettings);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageObject>(responseMessageObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/mergeUhid/{dummyUhid}/{originalUhid}/", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> mergeUhid(@PathVariable("dummyUhid") String dummyUhid,
														   @PathVariable("originalUhid") String originalUhid) {
		ResponseMessageObject returnObj = settingService.mergeUhid(dummyUhid, originalUhid);
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/configureTempUhid/{state}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> configureTempUhid(@PathVariable("state") String state,
																   @PathVariable("branchName") String branchName) {
		ResponseMessageObject returnObj = settingService.configureTempUhid(state, branchName);
		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getMergeDetailsForUHID/{dummyUhid}/{originalUhid}/", method = RequestMethod.GET)
	public ResponseEntity<MergeUhidDetailPojo> getMergeDetailsForUHID(@PathVariable("dummyUhid") String dummyUhid,
																	  @PathVariable("originalUhid") String originalUhid) {
		MergeUhidDetailPojo returnObj = new MergeUhidDetailPojo();
		try {
			returnObj = settingService.mergeDetailsForUHID(dummyUhid, originalUhid);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<MergeUhidDetailPojo>(returnObj, HttpStatus.OK);
	}


	@RequestMapping(value = "/inicu/saveSatisfactionFormDoctor", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageObject> saveSatisfactionFormDoctor(@RequestBody SatisfactionForm form) {
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {

			inicuDao.saveObject(form);
			returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			returnObj.setMessage("Satisfaction form saved successfully.");
			
		} catch (Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Satisfaction form cannot be saved.");
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseMessageObject>(returnObj, HttpStatus.OK);
	}


	@ExceptionHandler(Exception.class)
	protected ResponseEntity<String> handleControllerException(Exception ex){
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
