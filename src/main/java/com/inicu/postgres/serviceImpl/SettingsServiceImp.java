package com.inicu.postgres.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Message.RecipientType;

import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.utility.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.device.utility.RegistrationConstants;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.SettingsService;

import ca.uhn.hl7v2.hoh.util.repackage.Base64;

@Repository
public class SettingsServiceImp implements SettingsService {

	@Autowired
	SettingDao settingDao;

	@Autowired
	LogsService logService;

	@Autowired
	PatientDao patientDao;
	@Autowired
	private InicuDatabaseExeption databaseException;
	@Autowired
	InicuDao inicuDao;

	ObjectMapper mapper = new ObjectMapper();

	// Initialize Notifications
	private PushNotification notifications = new PushNotification();

	@Autowired
	private SimpMessagingTemplate template;

	@Override
	public BedManagementJSON getBedList(String loggedUser, String branchName) throws InicuDatabaseExeption {
		BedManagementJSON bedJSON = new BedManagementJSON();
		List<NicuBedJSON> roomList = new ArrayList<NicuBedJSON>();
		List<BedJSON> bedList = new ArrayList<BedJSON>();
		List<KeyValueObj> roomDropdown = new ArrayList<KeyValueObj>();
		try {
			RefRoom obj;
			String query = "select obj from RefRoom obj where isactive = 'true' and branchname = '" + branchName
					+ "' order by roomid";
			List<RefRoom> refList = settingDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(refList)) {
				java.util.Iterator<RefRoom> itr = refList.iterator();
				while (itr.hasNext()) {
					NicuBedJSON nicuRoom = new NicuBedJSON();
					obj = itr.next();
					KeyValueObj room = new KeyValueObj();

					room.setKey(obj.getRoomid());
					System.out.println("room id:" + obj.getRoomid() + " , room name:" + obj.getRoomname());
					room.setValue(obj.getRoomname());

					Long[] bedCount = getOccupiedCount(room.getKey().toString(), branchName);
					if (!BasicUtils.isEmpty(bedCount)) {
						room.setOccupiedCount(bedCount[0]);
						room.setVacantCount(bedCount[1]);
						room.setAllCount(bedCount[2]);
					}

					nicuRoom.setRoom(room);
					// nicuRoom.setbedJSON(getRoomBed(room.getKey().toString()));
					bedList = getRoomBed(room.getKey().toString(), branchName);

					/*
					 * java.util.Iterator<BedJSON> itr2=bedList.iterator(); while(itr2.hasNext()) {
					 * BedJSON b=itr2.next(); System.out.println("ther is :"+b.getBedName()); }
					 */
					nicuRoom.setbedJSON(bedList);
					nicuRoom.setNewBed(getNewBedId(room.getKey().toString(), branchName));
					roomList.add(nicuRoom);
					roomDropdown.add(room);
				}

				bedJSON.setNicuRoomObj(roomList);
				bedJSON.setRoomList(roomDropdown);
				// bedJSON.setNewRoom(getNewRoomId());
				// bedJSON.setNewBed(getNewBedId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return bedJSON;

	}

	private List<BedJSON> getRoomBed(String roomId, String branchName) throws Exception {
		List<BedJSON> bedList = new ArrayList<BedJSON>();

		String query = "select obj from RefBed as obj where roomid='" + roomId
				+ "' and isactive='true' and branchname = '" + branchName + "'order by bedid";

		List<RefBed> refList = settingDao.getListFromMappedObjNativeQuery(query);

		if (refList != null) {

			java.util.Iterator<RefBed> itr = refList.iterator();
			while (itr.hasNext()) {
				RefBed obj = itr.next();
				BedJSON bed = new BedJSON();

				bed.setbedId(obj.getBedid());
				bed.setbedName(obj.getBedname());
				bed.setStatus(obj.getStatus().toString());
				bed.setRoomId(obj.getRoomid());
				bedList.add(bed);

			}

		}

		return bedList;
	}

	Long[] getOccupiedCount(String roomId, String branchName) {
		Long countOfBeds[] = new Long[3];
		String OccupiedCountquery = "select count(obj) from RefBed as obj where roomid='" + roomId
				+ "' and isactive='true' and branchname = '" + branchName + "' and status = 'false'";

		List occupiedCount = settingDao.getListFromMappedObjNativeQuery(OccupiedCountquery);

		String VacantCountquery = "select count(obj) from RefBed as obj where roomid='" + roomId
				+ "' and isactive='true' and branchname = '" + branchName + "' and status = 'true'";

		List vacantCount = settingDao.getListFromMappedObjNativeQuery(VacantCountquery);

		countOfBeds[0] = (Long) occupiedCount.get(0);
		countOfBeds[1] = (Long) vacantCount.get(0);
		countOfBeds[2] = countOfBeds[0] + countOfBeds[1];

		return countOfBeds;


	}

	@Override
	public String addBox(BoxJSON box, String branchName) {
		String id = null;
		String boardName = RegistrationConstants.NEO.trim();
		String name = box.getBox_name();
		String serial = box.getBox_serial();
		boolean isTinyDevice = box.isTinyNeo();

		try {

			String fetchboard = "SELECT ref.bbox_id FROM ref_inicu_bbox as ref WHERE ref.bboxname='"
					+ RegistrationConstants.NEO.trim() + "'";
			List<Object> resultSet = settingDao.getListFromNativeQuery(fetchboard);
			if (!BasicUtils.isEmpty(resultSet)) {
				return null;
			} else {

				String query = "SELECT MAX(bbox_id)+1 FROM ref_inicu_bbox";
				List<Object> result = settingDao.getListFromNativeQuery(query);

				if (!BasicUtils.isEmpty(result)) {
					id = result.get(0).toString().trim();
				} else {
					id = "1";
				}

				StringBuilder boxNameQuery = new StringBuilder("SELECT ref.bbox_id FROM ref_inicu_bbox as ref WHERE branchname = '" + branchName + "' and ");
				if (isTinyDevice) {
					boxNameQuery.append("ref.tinyboxname='" + name + "'");
				} else {
					boxNameQuery.append("ref.bboxname='" + name + "'");
				}
				List<Object> finalSet = settingDao.getListFromNativeQuery(boxNameQuery.toString());
				if (!BasicUtils.isEmpty(finalSet)) {
					id = finalSet.get(0).toString().trim();
				} else {
					RefInicuBbox bbox = new RefInicuBbox();
					bbox.setActive(true);
					if (isTinyDevice) {
						bbox.setTinyboxname(name);
						bbox.setBboxname("");
					} else {
						bbox.setBboxname(name);
					}
					bbox.setBboxId(Long.parseLong(id));
					bbox.setBranchname(branchName);
					patientDao.saveObject(bbox);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return id;
	}

	@Override
	public ResponseMessageWithResponseObject addNicuBed(String roomno, String bedno, String loggedUser,
														String branchName) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		BedManagementJSON bedJSON = new BedManagementJSON();

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();

		/*
		 * List<NicuRoomObj> roomList=new ArrayList<NicuRoomObj>(); String
		 * query="select max(bedid) from ref_bed";
		 */
		String bedid;
		int bedInt = Integer.parseInt(bedno);
		String bedName;
		String nicubed = "BED_" + bedno;
		boolean vacant;
		String description;
		String bedQueryTrue = "select * from ref_bed where roomid='" + roomno + "' and bedname='" + nicubed
				+ "' and isactive = 'true' and branchname = '" + branchName + "'";
		String bedQueryFalse = "select * from ref_bed where roomid='" + roomno + "' and bedname='" + nicubed
				+ "' and isactive = 'false' and branchname = '" + branchName + "'";
		String query = "update ref_bed set isactive = 'true' where roomid = '" + roomno + "' and  bedname='" + nicubed
				+ "' and branchname = '" + branchName + "'";
		List<RefBed> bedListTrue = new ArrayList<RefBed>();
		bedListTrue = settingDao.getListFromNativeQuery(bedQueryTrue);
		List<RefBed> bedListFalse = new ArrayList<RefBed>();
		bedListFalse = settingDao.getListFromNativeQuery(bedQueryFalse);

		try {

			RefBed refBed = new RefBed();
			// if(!BasicUtils.isEmpty(maxBedId))
			{
				// String bed=maxBedId.get(0).toString().substring(3);
				// int maxid=Integer.parseInt(bed)+1;
				System.out.println("the value of bedList is:" + bedListTrue);
				if (BasicUtils.isEmpty(bedListTrue) && BasicUtils.isEmpty(bedListFalse)) {

					bedName = "BED_" + bedno;
					refBed.setBedname(bedName);
					refBed.setBranchname(branchName);
					vacant = true;
					refBed.setStatus(vacant);
					description = "NICU ROOM NO " + Integer.parseInt(roomno.substring(3)) + " - BED NO " + bedno;
					refBed.setDescription(description);

					System.out.println(roomno);
					// refBed.setBedid(bedid);
					// refBed.setBedNo(Integer.parseInt(bedno));
					refBed.setIsactive(true);
					refBed.setRoomid(roomno);
					refBed.setLoggedUser(loggedUser);
					settingDao.saveObject(refBed);

					// save logs
					String desc = mapper.writeValueAsString(refBed);
					String action = BasicConstants.INSERT;
					String docid = null;
					String pageName = BasicConstants.BED_MANAGEMENT;
					logService.saveLog(desc, action, loggedUser, docid, pageName);

				} else if (!BasicUtils.isEmpty(bedListFalse)) {

					settingDao.executeInsertQuery(query);
				} else {
					response.setType(BasicConstants.MESSAGE_FAILURE);
					response.setMessage("room no already exsist");
				}
				bedJSON = getBedList(loggedUser, branchName);

			}

			response.setReturnedObject(bedJSON);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("save successful");

		} catch (Exception e) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser,
					"null", "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject addRoom(String roomname, String loggedUser, String branchName)
			throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		BedManagementJSON bedJSON = new BedManagementJSON();
		// String nicuroom="NICU_"+roomno;
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();

		List<RefRoom> roomList = new ArrayList<RefRoom>();
		List<RefRoom> roomListFalse = new ArrayList<RefRoom>();
		try {
			String updateQuery = "update ref_room set isactive = 'true' where roomname = '" + roomname
					+ "' and branchname = '" + branchName + "'";
			String query = "select * from ref_room where roomname='" + roomname
					+ "' and isactive = 'true' and branchname = '" + branchName + "'";
			String queryFalse = "select * from ref_room where roomname='" + roomname
					+ "' and isactive = 'false' and branchname = '" + branchName + "'";
			roomList = settingDao.getListFromNativeQuery(query);
			roomListFalse = settingDao.getListFromNativeQuery(queryFalse);
			// List<Object> maxRoomId=bedDao.getListFromNativeQuery(query);
			// System.out.println(maxRoomId);
			RefRoom room = new RefRoom();
			// if(!BasicUtils.isEmpty(maxRoomId))
			if (!BasicUtils.isEmpty(roomList) && BasicUtils.isEmpty(roomListFalse)) {
				response.setType(BasicConstants.MESSAGE_FAILURE);
				response.setMessage("Room No. already exsist");
			} else if (!BasicUtils.isEmpty(roomListFalse)) {
				settingDao.executeInsertQuery(updateQuery);
			} else {
				String roomid;
				/*
				 * if(Integer.parseInt(roomno)<10) { roomid="RM0"+roomno; } else {
				 * roomid="RM"+roomno; }
				 */
				// String roomname="NICU_"+roomno;
				room.setRoomname(roomname);
				String description = "NICU ROOM Name " + roomname;
				room.setDescription(description);
				room.setBranchname(branchName);
				// room.setRoomno(Integer.parseInt(roomno));
				// room.setRoomid(roomid);
				room.setIsactive(true);
				room.setLoggedUser(loggedUser);
				settingDao.saveObject(room);
				String desc = mapper.writeValueAsString(room);
				String action = BasicConstants.INSERT;
				String docid = null;
				String pageName = BasicConstants.BED_MANAGEMENT;

				logService.saveLog(desc, action, loggedUser, docid, pageName);
			}
			bedJSON = getBedList(loggedUser, branchName);
			response.setReturnedObject(bedJSON);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("save successful");
		} catch (Exception e) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public String generateBoxName() {
		// TODO Auto-generated method stub
		String id = null;

		try {
			String query = "SELECT MAX(bbox_id)+1 FROM ref_inicu_bbox";
			List<Object> result = settingDao.getListFromNativeQuery(query);

			if (!BasicUtils.isEmpty(result)) {
				id = result.get(0).toString().trim();
			} else {
				id = "1";
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return id;

	}

	@Override
	public String generateSerial() {
		// TODO Auto-generated method stub
		String id = null;
		String serial = RegistrationConstants.SERIAL.trim();

		try {
			String query = "SELECT MAX(bbox_id)+1 FROM ref_inicu_bbox";
			List<Object> result = settingDao.getListFromNativeQuery(query);

			if (!BasicUtils.isEmpty(result)) {
				id = result.get(0).toString().trim();
			} else {
				id = "1";
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		serial += id;
		return serial;

	}

	@Override
	public ResponseMessageWithResponseObject deleteBed(String bedId, String loggedUser, String branchName)
			throws InicuDatabaseExeption {
		BedManagementJSON bedJSON = new BedManagementJSON();
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		response.setMessage(" Bed Deleted successful");
		String query = "update ref_bed set isactive = 'false' where bedid='" + bedId + "' and branchname = '"
				+ branchName + "'";
		String babyQuery = "select * from ref_bed where bedid='" + bedId + "' and status = 'false' and branchname = '"
				+ branchName + "'";
		List<RefBed> bedList = new ArrayList<RefBed>();
		try {
			List<BabyDetail> babyList = settingDao.getListFromNativeQuery(babyQuery);
			if (!BasicUtils.isEmpty(babyList)) {
				response.setType(BasicConstants.MESSAGE_FAILURE);
				response.setMessage("This bed is assign to a baby so it can not deleted");
			} else {
				// settingDao.delObject(query);
				// bedList=settingDao.getListFromNativeQuery(query);
				// if(!BasicUtils.isEmpty(bedList))
				{
					settingDao.delObjectNative(query);
					// RefBed deleteBed=(RefBed)bedList.get(0);
					// deleteBed.setIsactive(false);
					// settingDao.saveObject(deleteBed);

					String desc = mapper.writeValueAsString(query);
					String action = BasicConstants.UPDATE;
					String docid = null;
					String pageName = BasicConstants.BED_MANAGEMENT;
					logService.saveLog(desc, action, loggedUser, docid, pageName);
				}
			}
			bedJSON = getBedList(loggedUser, branchName);
			response.setReturnedObject(bedJSON);
		} catch (Exception e) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("THERE IS SOME ERROR WHILE PERFORMING THIS OPERATION");
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"DELETE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	public ResponseMessageWithResponseObject changeStatus(String bedid) {
		BedManagementJSON bedJSON = new BedManagementJSON();

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		response.setMessage("save successful");
		List<NicuRoomObj> roomList = new ArrayList<NicuRoomObj>();
		String query = "select obj from RefBed as obj where bedid='" + bedid + "'";
		List<RefBed> bedInfo = settingDao.getListFromMappedObjNativeQuery(query);
		try {
			if (!BasicUtils.isEmpty(bedInfo)) {
				RefBed bed = bedInfo.get(0);
				bed.setStatus(!bed.getStatus());
				settingDao.saveObject(bed);
				// bedJSON=getBedList(loggedUser);
				response.setReturnedObject(bedJSON);
			}
		} catch (Exception e) {

			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("you can't delete that bed");
		}
		return response;
	}

	public ResponseMessageWithResponseObject roomChange(String bedId, String roomNo, String loggedUser,
														String branchName) throws InicuDatabaseExeption {
		BedManagementJSON bedJSON = new BedManagementJSON();
		boolean onDashboard = false;
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		if (BasicUtils.isEmpty(roomNo)) {

			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("can't save");
		}
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		response.setMessage("save successful");
		List<BabyDetail> babyList = new ArrayList<BabyDetail>();
		List<RefBed> bedInfo = new ArrayList<RefBed>();
		List<NicuRoomObj> roomList = new ArrayList<NicuRoomObj>();
		String query = "select obj from RefBed as obj where bedid='" + bedId + "' and branchname = '" + branchName
				+ "'";
		String babyQuery = "select obj from BabyDetail as obj where nicubedno='" + bedId + "' and branchname = '"
				+ branchName + "'";
		try {
			babyList = settingDao.getListFromMappedObjNativeQuery(babyQuery);
			bedInfo = settingDao.getListFromMappedObjNativeQuery(query);
			for (int i = 0; i < babyList.size(); i++) {
				boolean admissionStatus = babyList.get(i).getAdmissionstatus();
				if (admissionStatus == true) {
					onDashboard = true;
					break;
				}
			}
			if (!BasicUtils.isEmpty(babyList) && onDashboard) {
				response.setType(BasicConstants.MESSAGE_FAILURE);
				response.setMessage("This bed can not shift because bed is assign to baby");
			} else {
				RefBed bed = bedInfo.get(0);
				bed.setRoomid(roomNo);
				settingDao.saveObject(bed);
				String desc = mapper.writeValueAsString(bed);
				String action = BasicConstants.UPDATE;
				String docid = null;
				String pageName = BasicConstants.BED_MANAGEMENT;
				logService.saveLog(desc, action, loggedUser, docid, pageName);
			}
			bedJSON = getBedList(loggedUser, branchName);
			response.setReturnedObject(bedJSON);
		} catch (Exception e) {

			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("THERE IS SOME ERROR WHILE PERFORMING THIS OPERATION");
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	/*
	 * public String getNewRoomId() { String roomid=""; String
	 * query="select max(roomno) from ref_room"; try{
	 *
	 * BigInteger roomidInt; List<Object>
	 * maxRoomId=settingDao.getListFromNativeQuery(query);
	 * System.out.println(maxRoomId); RefRoom room=new RefRoom();
	 * if(!BasicUtils.isEmpty(maxRoomId)) {
	 * roomidInt=((BigInteger)maxRoomId.get(0)).add(new BigInteger("1"));
	 * //roomid=String.valueOf(roomidInt); System.out.println(roomidInt);
	 * roomid=String.valueOf(roomidInt); }
	 *
	 * }catch(Exception e) { System.out.println("there is problem"); } return
	 * roomid; }
	 */

	public String getNewBedId(String roomId, String branchName) throws Exception {
		String bedId = "";
		// String query="select max(bedid) from ref_bed";
		String query = "select max(cast(substring(bedname,5) as int)) from ref_bed where roomid='" + roomId
				+ "' and isactive  = 'true' and branchname = '" + branchName + "'";

		int bedIdInt;
		List<Object> maxBedId = settingDao.getListFromNativeQuery(query);
		System.out.println(maxBedId);
		RefBed room = new RefBed();
		if (!BasicUtils.isEmpty(maxBedId)) {
			// bedId=maxBedId.get(0).toString().substring(3);
			// bedIdInt=((BigInteger)bedId).add(new BigInteger("1"));
			// bedIdInt=Integer.parseInt(maxBedId.get(0).toString().substring(3))+1;
			// bedIdInt=Integer.parseInt(maxBedId.get(0).toString().substring(3))+1;
			bedIdInt = Integer.parseInt(maxBedId.get(0).toString()) + 1;
			System.out.println(bedIdInt);
			bedId = String.valueOf(bedIdInt);
		} else {
			bedId = "1";
		}

		return bedId;
	}

	@Override
	public ResponseMessageWithResponseObject deleteRoom(String roomId, String loggedUser, String branchName)
			throws InicuDatabaseExeption {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		BedManagementJSON bedJSON = new BedManagementJSON();
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		response.setMessage("delete successfully");
		int flag = 0;
		String babyQuery = "select * from baby_detail where nicuroomno='" + roomId + "' and branchname = '" + branchName
				+ "'";
		String query = "update ref_bed set isactive ='false' where roomid='" + roomId + "' and branchname = '"
				+ branchName + "'";
		String bedQuery = " update ref_room set isactive = 'false' where roomid='" + roomId + "' and branchname = '"
				+ branchName + "'";
		try {
			List<BabyDetail> babyList = settingDao.getListFromNativeQuery(babyQuery);
			if (!BasicUtils.isEmpty(babyList)) {
				response.setType(BasicConstants.MESSAGE_FAILURE);
				response.setMessage("Bed of this room assign to baby so it can't be deleted");
			} else {
				settingDao.delObjectNative(bedQuery);
				settingDao.delObjectNative(query);
				// settingDao.delObject(query);
				String desc = mapper.writeValueAsString(query);
				String action = BasicConstants.UPDATE;
				String docid = null;
				String pageName = BasicConstants.BED_MANAGEMENT;

				logService.saveLog(desc, action, loggedUser, docid, pageName);
			}
			bedJSON = getBedList(loggedUser, branchName);
			response.setReturnedObject(bedJSON);
		} catch (Exception e) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("There is error on performing this operation");
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"UPDATE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DrugsListJson getListOfMedicine() {
		DrugsListJson drugsList = new DrugsListJson();
		List<Medicines> resultSet = new ArrayList<>();
		List<Medicines> listOfMedicine = new ArrayList<>();

		try {
			RefMedBrand brandObj = new RefMedBrand();
			drugsList.setBrand(brandObj);
			String brandQuery = "SELECT obj FROM RefMedBrand AS obj";
			List<RefMedBrand> brandList = settingDao.getListFromMappedObjNativeQuery(brandQuery);
			if (!BasicUtils.isEmpty(brandList)) {
				drugsList.setBrandList(brandList);
			}

			String query = "SELECT mod FROM Medicines AS mod ORDER BY mod.medid";
			resultSet = settingDao.getListFromMappedObjNativeQuery(query);
			for (Medicines m : resultSet) {
				// fetch type and frequency

				String type = m.getMedicationtype();
				if (!BasicUtils.isEmpty(type)) {
					KeyValueObj typeobj = new KeyValueObj();
					String fetchType = "SELECT med.typevalue FROM ref_medtype as med WHERE med.typeid='" + type.trim()
							+ "'";
					List result = settingDao.getListFromNativeQuery(fetchType);
					if (!BasicUtils.isEmpty(result)) {
						String typevalue = result.get(0).toString();
						typeobj.setKey(type);
						typeobj.setValue(typevalue);
					}

					m.setTypeKeyVal(typeobj);
				}

				String freq = m.getFrequency();
				if (!BasicUtils.isEmpty(freq)) {
					KeyValueObj freqObj = new KeyValueObj();
					String fetchType = "SELECT med.freqvalue FROM ref_medfrequency as med WHERE med.freqid='"
							+ freq.trim() + "'";
					List result = settingDao.getListFromNativeQuery(fetchType);
					if (!BasicUtils.isEmpty(result)) {
						String typevalue = result.get(0).toString();
						freqObj.setKey(type);
						freqObj.setValue(typevalue);
					}

					m.setFreqKeyVal(freqObj);
				}

				if (m.isFormulaperdose()) {
					m.setEachDose("true");
				} else
					m.setEachDose("false");

				if (!BasicUtils.isEmpty(m.getOthertype())) {
					m.setMentionOthers(m.getOthertype());
				}

				listOfMedicine.add(m);
			}
			drugsList.setDrugsList(listOfMedicine);

			String fetchUnits = "SELECT unit.unitid , unit.unitvalue FROM ref_unit as unit";
			List<KeyValueObj> listOfUnits = getRefObj(fetchUnits);
			if (!BasicUtils.isEmpty(listOfUnits)) {
				drugsList.setUnitDropDown(listOfUnits);
			}

			String fetchFrequency = "SELECT freq.freqid, freq.freqvalue FROM ref_medfrequency as freq";
			List<KeyValueObj> listOfFreq = getRefObj(fetchFrequency);
			if (!BasicUtils.isEmpty(listOfFreq)) {
				drugsList.setFreqDropDown(listOfFreq);
			}

			String fetchType = "SELECT type.typeid, type.typevalue FROM ref_medtype as type where type.typevalue!='Others'";
			List<KeyValueObj> listOfType = getRefObj(fetchType);
			if (!BasicUtils.isEmpty(listOfType)) {
				// append other to the med type drop down.
				String fetchOtherType = "SELECT type.typeid, type.typevalue FROM ref_medtype as type where type.typevalue='Others'";
				KeyValueObj keyVal = new KeyValueObj();
				List<Object[]> resultOther = settingDao.getListFromNativeQuery(fetchOtherType);
				if (!BasicUtils.isEmpty(resultOther)) {
					keyVal.setKey(resultOther.get(0)[0].toString());
					keyVal.setValue(resultOther.get(0)[1].toString());
					listOfType.add(keyVal);
				}
				drugsList.setTypeDropDown(listOfType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drugsList;
	}

	@Override
	public ResponseMessageObject deleteMedicine(String medid, String loggedUserId) throws InicuDatabaseExeption {
		// delete medicine
		String query = "DELETE FROM medicine WHERE medid = '" + medid.trim() + "'";
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setType(BasicConstants.MESSAGE_FAILURE);
		try {
			settingDao.delObjectNative(query);
			obj.setType(BasicConstants.MESSAGE_SUCCESS);
			obj.setMessage("drug deleted successfully.");
			// save logs
			String desc = mapper.writeValueAsString(query);
			String action = BasicConstants.DELETE;
			logService.saveLog(desc, action, loggedUserId, null, BasicConstants.ADD_DRUG);

		} catch (Exception e) {
			obj.setMessage(e.getMessage());
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
			String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUserId, "",
					"DELETE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return obj;
	}

	@Override
	public ResponseMessageObject addMedicine(Medicines m) throws InicuDatabaseExeption {
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("Medicine saved successfully");

		// check if medicine name already exist in the database.
		List result = settingDao
				.getListFromNativeQuery("SELECT medid FROM medicine WHERE medname='" + m.getMedname().trim() + "'");
		if (!BasicUtils.isEmpty(result) && !m.isEdit()) {
			obj.setMessage("Medicine:" + m.getMedname() + " already exists.");
			obj.setType(BasicConstants.MESSAGE_FAILURE);
		} else {
			try {
				m.setIsactive(true);
				// set other type.
				if (!BasicUtils.isEmpty(m.getMedicationtype())) {
					if (m.getMedicationtype().equalsIgnoreCase("TYPE0006")) {
						if (!BasicUtils.isEmpty(m.getMentionOthers())) {
							// m.setOthertype(m.getMentionOthers());
							String fetchId = "Select typeid FROM ref_medtype order by typeid desc limit 1";
							List<String> resultId = settingDao.getListFromNativeQuery(fetchId);
							if (!BasicUtils.isEmpty(resultId)) {
								String id = resultId.get(0).toString();
								if (!BasicUtils.isEmpty(id)) {
									// tokenise the string to get the val.
									id = id.replace("TYPE", "");
									Integer intVal = Integer.parseInt(id);
									if (!BasicUtils.isEmpty(intVal)) {
										intVal = intVal + 1;
										String intValStr = String.format("%04d", intVal);
										String insertType = "insert into ref_medtype " + "values('" + "TYPE" + intValStr
												+ "','" + m.getMentionOthers() + "')";
										settingDao.executeInsertQuery(insertType);

										m.setMedicationtype("TYPE" + intValStr);
									}
								}
							}
						} else {
							obj.setType(BasicConstants.MESSAGE_FAILURE);
							obj.setMessage("Please mention other drug type.");
							return obj;
						}
					}
				}

				if (!BasicUtils.isEmpty(m.getEachDose())) {
					if (m.getEachDose().equalsIgnoreCase("true")) {
						m.setFormulaperdose(true);
					} else {
						m.setFormulaperdose(false);
					}
				} else {
					m.setFormulaperdose(false);
				}

				settingDao.saveObject(m);
				// save logs
				String desc = mapper.writeValueAsString(m);
				String action = BasicConstants.INSERT;
				if (m.isEdit()) {
					action = BasicConstants.UPDATE;
				}
				logService.saveLog(desc, action, m.getUserid(), m.getUserid(), BasicConstants.ADD_DRUG);
			} catch (Exception e) {
				obj.setType(BasicConstants.MESSAGE_FAILURE);
				e.printStackTrace();
				String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
						m.getUserid(), "", "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
			}
		}
		return obj;
	}

	public List getRefObj(String query) throws Exception {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();

		// String query = "select obj.bpid,obj.blood_product from
		// ref_blood_product obj";
		List<Object[]> refList = settingDao.getListFromNativeQuery(query);
		KeyValueObj keyValue = null;
		if (refList != null && !refList.isEmpty()) {
			Iterator<Object[]> iterator = refList.iterator();
			while (iterator.hasNext()) {
				keyValue = new KeyValueObj();
				Object[] obj = iterator.next();
				if (obj != null && obj[0] != null)
					keyValue.setKey(obj[0]);
				if (obj != null && obj[1] != null)
					keyValue.setValue(obj[1]);
				refKeyValueList.add(keyValue);
			}
		}

		return refKeyValueList;
	}

	@Override
	public GeneralResponseObject deleteItemById(Long alertId) {
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try {
			String deleteQuery = "delete from clinical_alert_settings where alert_id='" + alertId + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteQuery);

			// get the updated dropdown list
			// get the clinical alter setting
			String ClinicalAlertSettingQuery = "select obj from ClinicalAlertSettings as obj";
			List<ClinicalAlertSettings> ClinicalAlertSettingList = settingDao.getListFromMappedObjNativeQuery(ClinicalAlertSettingQuery);

			String names = "";
			if (ClinicalAlertSettingList.size() > 0) {
				for (ClinicalAlertSettings object : ClinicalAlertSettingList) {
					if (names.isEmpty()) {
						names += "'" + object.getParameterName() + "'";
					} else {
						names += ", '" + object.getParameterName() + "'";
					}
				}
			}

			// get the dropdown for the Clinical Alert Setting
			String ClinicalAlertDropdownQuery = "";
			if (!names.isEmpty()) {
				ClinicalAlertDropdownQuery = "select obj from ClinicalAlertItemDropdown as obj where clinical_item_name not in (" + names + ")";
			} else {
				ClinicalAlertDropdownQuery = "select obj from ClinicalAlertItemDropdown as obj";
			}
			List<ClinicalAlertItemDropdown> ClinicalAlertDropdownList = settingDao.getListFromMappedObjNativeQuery(ClinicalAlertDropdownQuery);

			generalResponseObject = BasicUtils.getResonseObject(true, 200, "Success", ClinicalAlertDropdownList);


		} catch (Exception e) {
			System.out.printf("Exception: " + e);
		}
		return generalResponseObject;
	}

	@Override
	public GeneralResponseObject saveClinicalSetting(List<ClinicalAlertSettings> clinicalSetting) {
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try {
			List<ClinicalAlertSettings> clinicalAlertSettings = clinicalSetting;
			if (clinicalAlertSettings.size() > 0) {
				// save the list in the database
				List<ClinicalAlertSettings> returnedResult = (List<ClinicalAlertSettings>) inicuDao.saveMultipleObject(clinicalAlertSettings);
				generalResponseObject = BasicUtils.getResonseObject(true, 200, "Success", returnedResult);
			} else {
				generalResponseObject = BasicUtils.getResonseObject(true, 304, "List Empty", null);
			}
		} catch (Exception e) {
			System.out.printf("Exception: " + e);
		}
		return generalResponseObject;
	}

	@Override
	public SettingMasterJsonObj getSettingDetails(String details) {
		SettingMasterJsonObj masterObj = new SettingMasterJsonObj();
		HashMap<String, String> deviceMap = new HashMap<>();
		HashMap<String, String> summaryMap = new HashMap<>();

		String branchName = "";
		JSONObject settingInfo = null;
		try {
			settingInfo = new JSONObject(details);
			if (!BasicUtils.isEmpty(settingInfo)) {
				if (settingInfo.has("branchName")) {
					branchName = settingInfo.getString("branchName");
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String settingQuery = "SELECT refvalue, refid, category FROM setting_reference WHERE category='"
				+ BasicConstants.PREFERENCE_DEVICE + "' " + "OR category='" + BasicConstants.PREFERENCE_SUMMARY + "'";
		List<Object[]> resultSet = settingDao.getListFromNativeQuery(settingQuery);
		if (!BasicUtils.isEmpty(resultSet)) {
			for (Object[] obj : resultSet) {
				String category = obj[2].toString();
				String categoryName = obj[0].toString();
				if (category.equalsIgnoreCase(BasicConstants.PREFERENCE_DEVICE)) {
					if (!BasicUtils.isEmpty(categoryName)) {
						deviceMap.put(categoryName, obj[1].toString());
					}
				} else {
					if (!BasicUtils.isEmpty(categoryName)) {
						summaryMap.put(categoryName, obj[1].toString());
					}
				}
			}
		}

		// iterate maps to create boolean relation of setting type
		HashMap<String, Boolean> deviceTypeMap = new HashMap<>();
		String fetchDeviceInt = "SELECT prefid, preference FROM preference WHERE preference='"
				+ BasicConstants.PREFERENCE_DEVICE + "'";
		List<Object[]> result = settingDao.getListFromNativeQuery(fetchDeviceInt);
		if (!BasicUtils.isEmpty(result)) {
			String prefid = result.get(0)[0].toString();
			if (!BasicUtils.isEmpty(prefid)) {
				for (String name : deviceMap.keySet()) {
					String id = deviceMap.get(name);
					if (id.equalsIgnoreCase(prefid)) {
						deviceTypeMap.put(name, true);
					} else {
						deviceTypeMap.put(name, false);
					}
				}
			}
		}

		HashMap<String, Boolean> summaryTypeMap = new HashMap<>();
		String fetchSummary = "SELECT prefid, preference FROM preference WHERE preference='"
				+ BasicConstants.PREFERENCE_SUMMARY + "'";
		List<Object[]> resultSummary = settingDao.getListFromNativeQuery(fetchSummary);
		if (!BasicUtils.isEmpty(resultSummary)) {
			String prefid = resultSummary.get(0)[0].toString();
			if (!BasicUtils.isEmpty(prefid)) {
				for (String name : summaryMap.keySet()) {
					String id = summaryMap.get(name);
					if (id.equalsIgnoreCase(prefid)) {
						summaryTypeMap.put(name, true);
					} else {
						summaryTypeMap.put(name, false);
					}
				}
			}
		}

		String tempUHIDQuery = "SELECT tempuhidtoggle FROM ref_hospitalbranchname WHERE branchname='" + branchName + "'";
		List<Object[]> tempUHIDQueryResultSet = settingDao.getListFromNativeQuery(tempUHIDQuery);
		if (!BasicUtils.isEmpty(tempUHIDQueryResultSet)) {
			String tempuhidtoggle = String.valueOf(tempUHIDQueryResultSet.get(0));
			masterObj.setTempuhidtoggleForHis(tempuhidtoggle);
		}

		List<String> dummyUHIDList = new ArrayList<>();
		List<String> originalUHIDList = new ArrayList<>();
		String babyDetailQuery = "select obj from BabyDetail as obj where admissionstatus = 'true' and branchname='"
				+ branchName + "'";
		List<BabyDetail> babyDetailResultSet = settingDao.getListFromMappedObjNativeQuery(babyDetailQuery);
		if (!BasicUtils.isEmpty(babyDetailResultSet)) {
			for (BabyDetail babyDetail : babyDetailResultSet) {
				if (!BasicUtils.isEmpty(babyDetail)) {
					if (babyDetail.getUhid().startsWith("TEMP.")) {
						dummyUHIDList.add(babyDetail.getUhid());
					} else {
						originalUHIDList.add(babyDetail.getUhid());
					}
				}
			}
		}


		// get the clinical alter setting
		String ClinicalAlertSettingQuery = "select obj from ClinicalAlertSettings as obj";
		List<ClinicalAlertSettings> ClinicalAlertSettingList = settingDao.getListFromMappedObjNativeQuery(ClinicalAlertSettingQuery);

		String names="";
		if(ClinicalAlertSettingList.size()>0){
			masterObj.setClinicalAlertSettingsList(ClinicalAlertSettingList);
			for (ClinicalAlertSettings object:ClinicalAlertSettingList) {

				if (names.isEmpty()) {
					names += "'" + object.getParameterName() + "'";
				} else {
					names += ", '" + object.getParameterName() + "'";
				}
			}
		}else{
			masterObj.setClinicalAlertSettingsList(new ArrayList<>());
		}

		// get the dropdown for the Clinical Alert Setting
		String ClinicalAlertDropdownQuery="";
		if(!names.isEmpty()) {
			ClinicalAlertDropdownQuery = "select obj from ClinicalAlertItemDropdown as obj where clinical_item_name not in (" + names + ")";
		}else{
			ClinicalAlertDropdownQuery = "select obj from ClinicalAlertItemDropdown as obj";
		}
		List<ClinicalAlertItemDropdown> ClinicalAlertDropdownList = settingDao.getListFromMappedObjNativeQuery(ClinicalAlertDropdownQuery);

		if(ClinicalAlertDropdownList.size()>0){
			masterObj.setClinicalAlertItemDropdownList(ClinicalAlertDropdownList);
		}else{
			masterObj.setClinicalAlertItemDropdownList(new ArrayList<>());
		}

		masterObj.setDummyUHIDList(dummyUHIDList);
		masterObj.setOriginalUHIDList(originalUHIDList);

		masterObj.setDevicesetting(deviceTypeMap);
		masterObj.setDischargesetting(summaryTypeMap);

		// fetch the UHID who has tiny box connected to it
		String uhidList = "";
		ArrayList<String> tinyUhidList= new ArrayList<>();

		String fetchDeviceNameDetails = "SELECT upper(b.tinyboxname),a.uhid,a.deviceid FROM bed_device_detail a " +
				"INNER JOIN ref_inicu_bbox as b " +
				"ON a.bbox_device_id=b.bbox_id " +
				"WHERE a.status = 'true' and (b.bboxname is null or b.bboxname = '')";

		List<Object[]> deviceBoxName=inicuDao.getListFromNativeQuery(fetchDeviceNameDetails);
		for (Object[] object:deviceBoxName){

			if(!BasicUtils.isEmpty(object)) {
				//String boxName = object[0] != null ? object[0].toString() : "";
				String tinyboxName = object[0] != null ? object[0].toString() : "";
				String uhid = object[1] != null ? object[1].toString() : "";
				String deviceName = "";

				if (!BasicUtils.isEmpty(uhid)) {
					tinyUhidList.add(uhid);
					if (uhidList.isEmpty()) {
						uhidList += "'" + uhid + "'";
					} else {
						uhidList += ", '" + uhid + "'";
					}
				}
			}
		}

		int offsetValue = BasicUtils.getOffsetValue();
		System.out.println("--------------XXXXXXX------------------");

		 System.out.println("Check -> Offset Value :"+offsetValue);


		 // Fetch the Baby timings only if the branchName is "Moti Nagar, Delhi"

		if(branchName.equalsIgnoreCase("Moti Nagar, Delhi")) {

			masterObj.setRemoteViewAvailable(true);

			// get the saved timings details if have any
			String babyViewTimingsQuery = "";
			List<BabyViewTimings> babyViewTimingsList = new ArrayList<>();

			System.out.println("List of Babies To which tiny box area connected" + uhidList);

			// get the baby names for the babies to which camera is attached;
			String babyNameStr = "SELECT uhid,babyname,baby_type,baby_number FROM baby_detail where uhid in ("+uhidList+")";
			List<Object[]> babyNamesList = inicuDao.getListFromNativeQuery(babyNameStr);

			HashMap<String,String> babyNameMap = new HashMap<>();

			if(!BasicUtils.isEmpty(babyNamesList) && babyNamesList.size()>0){

				for (Object[] babyObject :babyNamesList) {
					String uhid ="";

					if(!BasicUtils.isEmpty(babyObject[0])){
						uhid = babyObject[0].toString();

						String name ="";
						if(!BasicUtils.isEmpty(babyObject[2]) && babyObject[2].toString().equalsIgnoreCase("Twins")){
							name = babyObject[1].toString() + " " +babyObject[2].toString() + " " + babyObject[3].toString();
						}else{
							name = babyObject[1].toString();
						}
						// put the name in the map
						babyNameMap.put(uhid,name);
					}
				}
			}

			if (!BasicUtils.isEmpty(uhidList) && uhidList.length() > 0) {
				babyViewTimingsQuery = "SELECT obj from BabyViewTimings as obj where uhid in (" + uhidList + ")";
				babyViewTimingsList = inicuDao.getListFromMappedObjQuery(babyViewTimingsQuery);
			}

			// Create Array list of Baby Uhid with the morning and evening timings and then iterate over the uhid list
			List<BabyViewTimings> myBabiesList = new ArrayList<>();
			for (String uhid : tinyUhidList) {

				BabyViewTimings myObject = uhidPresentInTimingsList(uhid, babyViewTimingsList);
				if (myObject == null) {

					// Uhid not found in the timings list lets add it
					BabyViewTimings babyViewObject = new BabyViewTimings();
					babyViewObject.setUhid(uhid);
					babyViewObject.setMorningFromTime("9,00,AM");
					babyViewObject.setMorningToTime("11,00,AM");
					babyViewObject.setEveningFromTime("5,00,PM");
					babyViewObject.setEveningToTime("6,00,PM");


					if(babyNameMap.get(uhid)!=null){
						babyViewObject.setBabyName(babyNameMap.get(uhid));
					}else{
						babyViewObject.setBabyName("");
					}

					try {

						SimpleDateFormat format = new SimpleDateFormat("hh:mm a");  //if 24 hour format

						java.util.Date d1 = (java.util.Date) format.parse("9:00 AM");
						java.util.Date d2 = (java.util.Date) format.parse("11:00 AM");

						java.util.Date e1 = (java.util.Date) format.parse("5:00 PM");
						java.util.Date e2 = (java.util.Date) format.parse("6:00 PM");

						Timestamp ppstime = new Timestamp(d1.getTime() - offsetValue);
						Timestamp ppstime_2 = new Timestamp(d2.getTime() - offsetValue);

						Timestamp even_1 = new Timestamp(e1.getTime() - offsetValue);
						Timestamp even_2 = new Timestamp(e2.getTime() - offsetValue);

						babyViewObject.setDisplayMorningFromTime(ppstime);
						babyViewObject.setDisplayMorningToTime(ppstime_2);

						babyViewObject.setDisplayEveningFromTime(even_1);
						babyViewObject.setDisplayEveningToTime(even_2);
						babyViewObject.setValueEdit(false);

					}
					catch (Exception e) {
						System.out.println("Exception Caught :" + e);
					}
					// Item added to the list
					myBabiesList.add(babyViewObject);
				} else {
					// return the object with this uhid
					try {
						SimpleDateFormat format = new SimpleDateFormat("hh:mm a");  //if 24 hour format

						java.util.Date d1 = (java.util.Date) format
								.parse(BasicUtils.getTimeString(myObject.getMorningFromTime()));
						java.util.Date d2 = (java.util.Date) format
								.parse(BasicUtils.getTimeString(myObject.getMorningToTime()));

						java.util.Date e1 = (java.util.Date) format
								.parse(BasicUtils.getTimeString(myObject.getEveningFromTime()));
						java.util.Date e2 = (java.util.Date) format
								.parse(BasicUtils.getTimeString(myObject.getEveningToTime()));

						Timestamp ppstime = new Timestamp(d1.getTime() - offsetValue);
						Timestamp ppstime_2 = new Timestamp(d2.getTime() - offsetValue);

						Timestamp even_1 = new Timestamp(e1.getTime() - offsetValue);
						Timestamp even_2 = new Timestamp(e2.getTime() - offsetValue);

						myObject.setDisplayMorningFromTime(ppstime);
						myObject.setDisplayMorningToTime(ppstime_2);

						myObject.setDisplayEveningFromTime(even_1);
						myObject.setDisplayEveningToTime(even_2);
						myObject.setValueEdit(false);

						if(babyNameMap.get(uhid)!=null){
							myObject.setBabyName(babyNameMap.get(uhid));
						}else{
							myObject.setBabyName("");
						}

						myBabiesList.add(myObject);
					}
					catch (Exception e) {
						System.out.println("Exception Caught :" + e);
					}
				}
			}

			masterObj.setBabyViewList(myBabiesList);
		}

		// get the nurse shift setting
		String getQuery = "SELECT obj FROM NurseShiftSettings obj WHERE branchname ='"+branchName+"'";
		List<NurseShiftSettings> nursingShiftSettingList = inicuDao.getListFromMappedObjQuery(getQuery);

		if(nursingShiftSettingList.size()>0){
			masterObj.setNurseShiftSettings(nursingShiftSettingList.get(0));
		}else{
			NurseShiftSettings nurseShiftSettingsObject = new NurseShiftSettings();
			nurseShiftSettingsObject.setBranchname(branchName);
			masterObj.setNurseShiftSettings(nurseShiftSettingsObject);
		}

		return masterObj;
	}

	private BabyViewTimings uhidPresentInTimingsList(String uhid, List<BabyViewTimings> uhidList){
		for (BabyViewTimings babyObject:uhidList) {
			if(babyObject.getUhid().equalsIgnoreCase(uhid)){
			   return babyObject;
			}
		}
		return null;
	}

	@Override
	public ResponseMessageObject saveSetting(SettingMasterJsonObj settingObj) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		rObj.setMessage("setting saved successfully");

		HashMap<String, Boolean> devSetting = settingObj.getDevicesetting();
		if (!BasicUtils.isEmpty(devSetting)) {
			for (String name : devSetting.keySet()) {
				Boolean val = devSetting.get(name);
				if (val) {
					String query = "SELECT refid FROM setting_reference WHERE refvalue='" + name.trim() + "' "
							+ "AND category='" + BasicConstants.PREFERENCE_DEVICE + "'";
					List<Object> result = settingDao.getListFromNativeQuery(query);
					if (!BasicUtils.isEmpty(result)) {
						String refid = result.get(0).toString();
						if (!BasicUtils.isEmpty(refid)) {
							try {
								Preference pref = new Preference();
								String getPref = "SELECT obj FROM Preference as obj WHERE preference='"
										+ BasicConstants.PREFERENCE_DEVICE + "'";
								List<Preference> resultSet = settingDao.getListFromMappedObjNativeQuery(getPref);
								if (!BasicUtils.isEmpty(resultSet)) {
									Preference preference = resultSet.get(0);
									pref = preference;
									pref.setPrefid(refid);
								}
								// String updateQuery = "update preference "
								// + "set prefid='"+refid+"' "
								// + "WHERE preference =
								// 'device_integration_type'";
								settingDao.updateNativeQuery(pref);
							} catch (Exception e) {
								rObj.setType(BasicConstants.MESSAGE_FAILURE);
								rObj.setMessage(e.getMessage());
							}
						}
					}
				}
			}
		}

		HashMap<String, Boolean> summSetting = settingObj.getDischargesetting();
		if (!BasicUtils.isEmpty(summSetting)) {
			for (String name : summSetting.keySet()) {
				Boolean val = summSetting.get(name);
				if (val) {
					String query = "SELECT refid FROM setting_reference WHERE refvalue='" + name.trim() + "' "
							+ "AND category='" + BasicConstants.PREFERENCE_SUMMARY + "'";
					List<Object> result = settingDao.getListFromNativeQuery(query);
					if (!BasicUtils.isEmpty(result)) {
						String refid = result.get(0).toString();
						if (!BasicUtils.isEmpty(refid)) {
							try {
								Preference pref = new Preference();
								String getPref = "SELECT obj FROM Preference as obj WHERE preference='"
										+ BasicConstants.PREFERENCE_SUMMARY + "'";
								List<Preference> resultSet = settingDao.getListFromMappedObjNativeQuery(getPref);
								if (!BasicUtils.isEmpty(resultSet)) {
									Preference preference = resultSet.get(0);
									pref = preference;
									pref.setPrefid(refid);
								}
								settingDao.updateNativeQuery(pref);
							} catch (Exception e) {
								rObj.setType(BasicConstants.MESSAGE_FAILURE);
								rObj.setMessage(e.getMessage());
							}
						}
					}
				}
			}
		}

		return rObj;
	}

	@Override
	public ResponseMessageObject saveHospitalLogo(String imgStr, String branchname) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setMessage(BasicConstants.MESSAGE_SUCCESS);
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		File imageDir = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH);
		if (!imageDir.exists()) {
			try {
				System.out.println("creating image directory:");
				imageDir.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				rObj.setMessage(e.getMessage());
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				return rObj;
			}
		}

		FileOutputStream osf = null;
		FileOutputStream osf1 = null;
		try {
			Base64 decoder = new Base64();
			if (!BasicUtils.isEmpty(imgStr)) {
				String value = imgStr.split("base64,")[1];
				byte[] imgBytes = decoder.decode(value);
				osf = new FileOutputStream(new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH
						+ branchname + "_logo.png"));
				osf1 = new FileOutputStream(new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH
						+ BasicConstants.SCHEMA_NAME + "_logo.png"));
				osf.write(imgBytes);
				osf.flush();

				osf1.write(imgBytes);
				osf1.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rObj.setMessage(e.getMessage());
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		} finally {
			try {
				osf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return rObj;
	}

	@Override
	public List<KeyValueObj> getHospitalLogo(String branchName) {
		List<KeyValueObj> imageData = new ArrayList<>();
		File fnew = new File(
				BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + branchName + "_logo.png");
		try {
			if (fnew.exists()) {
				BufferedImage image = ImageIO.read(fnew);
				if (!BasicUtils.isEmpty(image)) {
					KeyValueObj obj = new KeyValueObj();
					String encodedStr = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(fnew.toPath()));
					obj.setKey("logo");
					obj.setValue(encodedStr);
					imageData.add(obj);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageData;
	}

	@Override
	public RegisterDeviceDropdownJSon getDropdowns() {
		// TODO Auto-generated method stub
		// setting up dropdowns
		JSONObject json = new JSONObject();
		RegisterDeviceDropdownJSon dropdown = new RegisterDeviceDropdownJSon();
		HashMap<String, List<String>> deviceBrandName = new HashMap<>();
		String fetchdevTypeBrand = "SELECT obj FROM InicuDevice as obj";
		List<InicuDevice> result = settingDao.getListFromMappedObjNativeQuery(fetchdevTypeBrand);
		if (!BasicUtils.isEmpty(result)) {
			for (InicuDevice dev : result) {
				if (!BasicUtils.isEmpty(deviceBrandName.get(dev.getBrandname()))) {
					List<String> listDev = deviceBrandName.get(dev.getBrandname());
					listDev.add(dev.getDevicename());
					deviceBrandName.put(dev.getBrandname(), listDev);
				} else {
					List<String> listDevices = new ArrayList<>();
					listDevices.add(dev.getDevicename());
					deviceBrandName.put(dev.getBrandname(), listDevices);
				}
			}
		}

		dropdown.setDeviceBrandName(deviceBrandName);

		HashMap<String, List<String>> deviceTypeBrand = new HashMap<>();
		String fetchDevRef = "select devtype.device_type, " + "dev.brandname " + "from ref_device_type as devtype "
				+ "left outer join " + "ref_inicu_devices as dev  " + "on (devtype.devicetypeid = dev.devicetypeid)";
		List<Object[]> resultDevRef = settingDao.getListFromNativeQuery(fetchDevRef);
		if (!BasicUtils.isEmpty(resultDevRef)) {
			for (Object[] obj : resultDevRef) {
				if (!BasicUtils.isEmpty(deviceTypeBrand.get(obj[0].toString()))) {
					List<String> listDev = deviceTypeBrand.get(obj[0].toString());
					if (!BasicUtils.isEmpty(obj[1])) {
						if (!listDev.contains(obj[1].toString())) {
							listDev.add(obj[1].toString());
						}
					}
					deviceTypeBrand.put(obj[0].toString(), listDev);
				} else {
					List<String> listDevices = new ArrayList<>();
					if (!BasicUtils.isEmpty(obj[1])) {
						listDevices.add(obj[1].toString());
					}
					deviceTypeBrand.put(obj[0].toString(), listDevices);
				}
			}
		}
		dropdown.setDeviceTypeBrand(deviceTypeBrand);

		return dropdown;
	}

	@Override
	public String generateNeoName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getListOfAVLmacids() {
		// TODO Auto-generated method stub
		MacID dev = new MacID();
		String query = "select macid from MacID where available='t'";
		List<String> macs = patientDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(macs)) {
			return macs;
		}
		return null;
	}

	@Override
	public boolean isMacAvailable(String macid) {
		// TODO Auto-generated method stub
		MacID dev = new MacID();
		String query = "select obj from DeviceDetail as obj where ipaddress='" + macid + "'";
		List<DeviceDetail> macs = patientDao.getListFromMappedObjNativeQuery(query);
		System.out.println("available " + macs.get(0).getAvailable());
		if (macs.get(0).getAvailable() == true) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getIp(String neo_serial) {
		// TODO Auto-generated method stub
		String query = "select ipaddress from DeviceDetail where deviceserialno='" + neo_serial + "'";
		List<String> macs = patientDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(macs)) {
			return macs.get(0);
		}
		return null;
	}

	@Override
	public ResponseMessageWithResponseObject deleteBox(String neo_serial) {
		// TODO Auto-generated method stub
		boolean flag = false;
		ResponseMessageWithResponseObject responseMessageObj = new ResponseMessageWithResponseObject();
		String queryToRemoveFromDeviceDetail = "delete from device_detail where deviceserialno='" + neo_serial + "'";
		String bboxName = "neo-" + neo_serial.substring(3, neo_serial.length());
		String queryToRemoveFromRefInicuBbox = "delete from ref_inicu_bbox where bboxname='" + bboxName + "'";
		try {
			patientDao.updateOrDeleteNativeQuery(queryToRemoveFromDeviceDetail);
			patientDao.updateOrDeleteNativeQuery(queryToRemoveFromRefInicuBbox);
		} catch (Exception e) {
			System.out.println("couldnt delete box");
			responseMessageObj.setMessage("couldnt delete box");
			responseMessageObj.setType(BasicConstants.MESSAGE_FAILURE);
			flag = true;
		}
		if (!flag) {
			responseMessageObj.setMessage("Deleted");
			responseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
		}
		return responseMessageObj;
	}

	@Override
	public ResponseMessageObject editBoxDetails(BoxJSON box) {
		// VP: Edit box details as specified in BoxJSON
		ResponseMessageObject resp = new ResponseMessageObject();

		BoardDetailJson board1 = box.getBoard1();
		if (!BasicUtils.isEmpty(board1)) {
			// board1 data to be edited
			String getBoardQuery = "Select obj FROM DeviceDetail as obj WHERE ipaddress='" + board1.getMacId() + "' ";
			List<DeviceDetail> boardObjectsList = patientDao.getListFromMappedObjNativeQuery(getBoardQuery);
			if (BasicUtils.isEmpty(boardObjectsList)) {
				// no board found with this mac id
			} else if (!BasicUtils.isEmpty(boardObjectsList)) {

				if (boardObjectsList.size() > 1) {
					// multiple boards with same MAC address
					// error message
				} else {
					DeviceDetail boardObject = boardObjectsList.get(0);
					boardObject.setAvailable(false);
					boardObject.setDevicebrand(board1.getBrandName());
					boardObject.setDevicename(board1.getDeviceName());
					boardObject.setDevicetype(board1.getDeviceType());
					try {
						patientDao.updateObject(boardObject);
						resp.setMessage("Board1 updated");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		BoardDetailJson board2 = box.getBoard2();
		if (!BasicUtils.isEmpty(board2)) {
			// board2 data to be edited
			String getBoardQuery = "Select obj FROM DeviceDetail as obj WHERE ipaddress='" + board2.getMacId() + "' ";
			List<DeviceDetail> boardObjectsList = patientDao.getListFromMappedObjNativeQuery(getBoardQuery);
			if (BasicUtils.isEmpty(boardObjectsList)) {
				// no board found with this mac id
			} else if (!BasicUtils.isEmpty(boardObjectsList)) {
				if (boardObjectsList.size() > 1) {
					// multiple boards with same MAC address
					// error message
				} else {
					DeviceDetail boardObject = boardObjectsList.get(0);
					boardObject.setAvailable(false);
					boardObject.setDevicebrand(board2.getBrandName());
					boardObject.setDevicename(board2.getDeviceName());
					boardObject.setDevicetype(board2.getDeviceType());
					try {
						patientDao.updateObject(boardObject);
						resp.setMessage(resp.getMessage() + "Board2 updated");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return resp;
	}

	@Override
	public List<DeviceException> getDeviceExceptions(String boxSerialNo) {
		// VP:
		// Get device exceptions for the box serial
		List<DeviceException> deviceExceptionsList = null;
		if (!BasicUtils.isEmpty(boxSerialNo)) {
			String deviceExceptionsListQuery = "select obj from DeviceException obj where deviceserialno='"
					+ boxSerialNo + "'";
			deviceExceptionsList = patientDao.getListFromMappedObjNativeQuery(deviceExceptionsListQuery);
		}
		return deviceExceptionsList;
	}

	@Override
	public TestsListJSON getTestsDetails(String branchName) {
		TestsListJSON tests = new TestsListJSON();

		// Fetch only iNICU tests
		String queryRefTestsList = "select obj from RefTestslist as obj where not (obj.assesmentCategory like ('%None%')) order by assesmentCategory";
		List<RefTestslist> listRefTests = inicuDao.getListFromMappedObjQuery(queryRefTestsList);
		// populate drop down hash map for the tests list...
		HashMap<Object, List<MappedListPOJO>> testsListMap = new HashMap<Object, List<MappedListPOJO>>();
		for (RefTestslist test : listRefTests) {
			List<MappedListPOJO> categoryList = null;
			MappedListPOJO map = new MappedListPOJO();
			if (testsListMap.get(test.getAssesmentCategory()) != null) {
				categoryList = testsListMap.get(test.getAssesmentCategory());
			} else {
				categoryList = new ArrayList<MappedListPOJO>();
			}
			map.setInicuTest(test);
			//map.setLoggedUser("test");
			map.setIsMapped(false);
			RefTestslist vendorEmptyList = new RefTestslist();
			map.setVendorTest(vendorEmptyList);

			String fetchType = "SELECT vendortestid FROM TestslistMapping as obj WHERE obj.inicutestid='"
					+ test.getTestid().trim() + "'";
			List result = inicuDao.getListFromMappedObjQuery(fetchType);
			if (!BasicUtils.isEmpty(result)) {

				String fetchVendor = "SELECT obj FROM RefTestslist as obj WHERE obj.testid='"
						+ result.get(0).toString().trim() + "'";
				List resultVendor = inicuDao.getListFromMappedObjQuery(fetchVendor);
				if (!BasicUtils.isEmpty(resultVendor)) {
					map.setVendorTest((RefTestslist) resultVendor.get(0));
					map.setIsMapped(true);
				}
			}
			categoryList.add(map);
			testsListMap.put(test.getAssesmentCategory(), categoryList);
		}
		tests.setInicuList(testsListMap);

		// Fetch only Vendor tests
		String queryRefTestsVendorList = "select obj from RefTestslist as obj where obj.assesmentCategory IS NULL";
		List<RefTestslist> listRefVendorTests = inicuDao.getListFromMappedObjQuery(queryRefTestsVendorList);
		tests.setVendorLists(listRefVendorTests);

		// Fetch All Investigation Pannel Details
		String queryInvestigationPannelList = "select obj from InvestigationPannel as obj where branchname='"
				+ branchName + "'";
		List<InvestigationPannel> investigationPannelList = inicuDao
				.getListFromMappedObjQuery(queryInvestigationPannelList);
		tests.setInvestigationPannelList(investigationPannelList);

		return tests;
	}

	@Override
	public ResponseMessageWithResponseObject saveInvestigationPannel(String pannelName, String loggedUser,
			String branchName, List<TestPOJO> testDetails) {
		// TODO Auto-generated method stub
		TestsListJSON testJSON = new TestsListJSON();
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			for (int i = 0; i < testDetails.size(); i++) {
				TestPOJO testDetailObj = testDetails.get(i);
				InvestigationPannel investigationPannelObj = new InvestigationPannel();
				investigationPannelObj.setLoggedUser(loggedUser);
				investigationPannelObj.setBranchName(branchName);
				investigationPannelObj.setPannelName(pannelName);
				investigationPannelObj.setTestId(testDetailObj.getTestid());
				investigationPannelObj.setTestName(testDetailObj.getTestname());
				settingDao.saveObject(investigationPannelObj);
			}
			testJSON = getTestsDetails(branchName);
			response.setReturnedObject(testJSON);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("save successful");
		} catch (Exception e) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject deleteInvestigationPannel(String pannelName, String branchName) {
		// TODO Auto-generated method stub
		TestsListJSON testJSON = new TestsListJSON();
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {
			String queryInvestigationPannelAvailable = "SELECT obj FROM InvestigationPannel as obj where pannel_name = '"
					+ pannelName + "' and branchname='" + branchName + "'";
			List<InvestigationPannel> investigationPannelAvailableList = settingDao
					.getListFromMappedObjNativeQuery(queryInvestigationPannelAvailable);
			String investigationPannelId = "";
			if (!BasicUtils.isEmpty(investigationPannelAvailableList)) {
				for (int i = 0; i < investigationPannelAvailableList.size(); i++) {
					if (investigationPannelId != "") {
						investigationPannelId += ",";
					}
					investigationPannelId += "'"
							+ investigationPannelAvailableList.get(i).getInvestigationPannelId().toString() + "'";
				}
			}

			if (investigationPannelId != "") {
				String queryForDeletingInvestigationPannel = "delete from InvestigationPannel where investigationpannelid IN ("
						+ investigationPannelId + ")";
				settingDao.delObject(queryForDeletingInvestigationPannel);
				testJSON = getTestsDetails(branchName);
				response.setReturnedObject(testJSON);
				response.setType(BasicConstants.MESSAGE_SUCCESS);
				response.setMessage("save successful");
			}
		} catch (Exception e) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject editInvestigationPannel(String oldPannelName, String newPannelName,
			String loggedUser, String branchName, List<TestPOJO> testDetails) {
		// TODO Auto-generated method stub
		TestsListJSON testJSON = new TestsListJSON();
		ResponseMessageWithResponseObject editResponseMessageObj = new ResponseMessageWithResponseObject();

		try {
			editResponseMessageObj = deleteInvestigationPannel(oldPannelName, branchName);
			editResponseMessageObj = saveInvestigationPannel(newPannelName, loggedUser, branchName, testDetails);
			testJSON = getTestsDetails(branchName);
			editResponseMessageObj.setReturnedObject(testJSON);
			editResponseMessageObj.setType(BasicConstants.MESSAGE_SUCCESS);
			editResponseMessageObj.setMessage("save successful");

		} catch (Exception e) {
			editResponseMessageObj.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
		}
		return editResponseMessageObj;
	}

	@Override
	public ResponseMessageObject mapTests(MappedListPOJO mappedJson) throws InicuDatabaseExeption {

		ResponseMessageObject obj = new ResponseMessageObject();
		try {
			if (!BasicUtils.isEmpty(mappedJson.getIsMapped())) {
				if (!BasicUtils.isEmpty(mappedJson.getInicuTest().getTestid())
						&& !BasicUtils.isEmpty(mappedJson.getVendorTest().getTestid())) {

					if (mappedJson.getIsMapped()) {
						TestslistMapping mapping = new TestslistMapping();
						mapping.setVendortestid(mappedJson.getVendorTest().getTestid());
						mapping.setInicutestid(mappedJson.getInicuTest().getTestid());
						//mappedJson.setLoggedUser("test");
						if (!BasicUtils.isEmpty(mappedJson.getLoggedUser())) {
							mapping.setLoggeduser(mappedJson.getLoggedUser());
						}
						mapping.setIsactive(true);
						if (!BasicUtils.isEmpty(mappedJson.getInicuTest().getAssesmentCategory())) {
							mapping.setAssessmentcategory(mappedJson.getInicuTest().getAssesmentCategory());
						}

						settingDao.saveObject(mapping);

						obj.setType(BasicConstants.MESSAGE_SUCCESS);
						obj.setMessage("tests mapped successfully");
					}

					else if (!mappedJson.getIsMapped()) {
						String vendorTest = mappedJson.getVendorTest().getTestid();
						String inicuTest = mappedJson.getInicuTest().getTestid();
						String query = "DELETE FROM tests_list_mapping WHERE vendortestid = '" + vendorTest.trim()
								+ "' and inicutestid = '" + inicuTest.trim() + "'";

						settingDao.delObjectNative(query);
						obj.setType(BasicConstants.MESSAGE_SUCCESS);
						obj.setMessage("tests unmapped successfully.");

					} else {
						obj.setType(BasicConstants.MESSAGE_FAILURE);
						obj.setMessage("Not proper flag is provided for mapping.");
					}
				}
			}
		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
			obj.setMessage(e.toString());
		}

		return obj;
	}

	@Override
	public List<DeviceHeartbeat> getDeviceHeartBeat(DeviceHeartBeatModel beat) {
		// TODO Auto-generated method stub
		// Get device exceptions for the box serial
		// String zone = "GMT+5:30";
		// int offset = TimeZone.getTimeZone(zone).getRawOffset() -
		// TimeZone.getDefault().getRawOffset();
		// System.out.println((new Timestamp(beat.getHeartBeatFrom().getTime() +
		// offset)));
		List<DeviceHeartbeat> devicehbList = null;
		// Timestamp dateFrom = new Timestamp(beat.getHeartBeatFrom().getTime() +
		// offset);
		// Timestamp dateTo = new Timestamp(beat.getHeartBeatTo().getTime() + offset);
		Timestamp dateFrom = beat.getHeartBeatFrom();
		Timestamp dateTo = beat.getHeartBeatTo();
		if (!BasicUtils.isEmpty(beat.getBoxSerialNo().trim())) {
			String devicehbListQuery = "select obj from DeviceHeartbeat obj where deviceserialno='"
					+ beat.getBoxSerialNo().trim() + "' and creationtime>='" + dateFrom + "' and creationtime<='"
					+ dateTo + "' order by creationtime asc";
			devicehbList = patientDao.getListFromMappedObjNativeQuery(devicehbListQuery);
		}
		return devicehbList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EmailManagementJSON getEmailData(String branchname) {
		EmailManagementJSON returnObj = new EmailManagementJSON();
		List<NotificationEmail> emailList = new ArrayList<NotificationEmail>();
		List<NotificationEmail> finalEmailList = new ArrayList<NotificationEmail>();
		List<String> userName = new ArrayList<String>();
		List<String> userEmail = new ArrayList<String>();
		
		String userQuery = "select obj from UserInfoView as obj where branchname = '" + branchname + "'";
		List<UserInfoView> usersList = inicuDao.getListFromMappedObjQuery(userQuery);
		
		if(!BasicUtils.isEmpty(usersList)) {
			for(UserInfoView obj : usersList) {
				if(!userName.contains(obj.getUserName())){
					userName.add(obj.getUserName());
					userEmail.add(obj.getEmail());
					//finalEmailList.add(obj);
				}
			}
		}
		returnObj.setUserNameList(userName);
		returnObj.setUserEmailList(userEmail);

		List<String> tempEmailsList = new ArrayList<String>();
		//returnObj.setEmailList();
		emailList = inicuDao.getListFromMappedObjQuery(
				HqlSqlQueryConstants.getNotificationEmailList() + " where branchname = '" + branchname + "' order by isactive desc, email_type desc");
		
		if(!BasicUtils.isEmpty(emailList)) {
			for(NotificationEmail obj : emailList) {
				if(!tempEmailsList.contains(obj.getUser_email())){
					tempEmailsList.add(obj.getUser_email());
					finalEmailList.add(obj);

					if((obj.getUser_email().toString().substring(obj.getUser_email().toString().indexOf("@") + 1)).equalsIgnoreCase("childhealthimprints.com") || (obj.getUser_email().toString().substring(obj.getUser_email().toString().indexOf("@") + 1)).equalsIgnoreCase("inicucloud.com")) {
						finalEmailList.remove(obj);
					}
				}
			}
		}
		returnObj.setEmailList(finalEmailList);
		return returnObj;
	}

	@Override
	public EmailManagementJSON saveEmailData(NotificationEmail emailObj) {
		try {
			String query = "update inicuuser set emailaddress = '" + emailObj.getUser_email() + "' where username = '" + emailObj.getUsername() + "'";
			//String query1 = "update vwuserinfo set emailaddress = '" + emailObj.getUser_email() + "' where username = '" + emailObj.getUsername() + "'";
			inicuDao.updateOrDeleteNativeQuery(query);
			//inicuDao.updateOrDeleteNativeQuery(query1);
			inicuDao.saveObject(emailObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getEmailData(emailObj.getBranchname());
	}

	@Override
	@SuppressWarnings("unchecked")
	public RefNursingOutputParameters getNursingOuputParameters(String branchName) {
		RefNursingOutputParameters obj = new RefNursingOutputParameters();
		String queryhospitalName = "select obj from RefNursingOutputParameters obj where branchname='" + branchName
				+ "'";
		List<RefNursingOutputParameters> refNursingOutputParametersList = patientDao
				.getListFromMappedObjNativeQuery(queryhospitalName);
		if (!BasicUtils.isEmpty(refNursingOutputParametersList)) {
			obj = refNursingOutputParametersList.get(0);
		}
		return obj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public RefHospitalbranchname getApneaEventParameters(String branchName) {
		RefHospitalbranchname obj = new RefHospitalbranchname();
		String queryGetApneaEventParamters = "select obj from RefHospitalbranchname obj where branchname='" + branchName
				+ "'";
		List<RefHospitalbranchname> RefHospitalbranchnameList = patientDao
				.getListFromMappedObjNativeQuery(queryGetApneaEventParamters);
		if (!BasicUtils.isEmpty(RefHospitalbranchnameList)) {
			obj = RefHospitalbranchnameList.get(0);
		}
		return obj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageObject updateUhid(String oldUhid, String newUhid) {

		ResponseMessageObject returnObj = new ResponseMessageObject();
		String updateUhidQuery = "";

		try {

			List<BabyDetail> babyList = patientDao
					.getListFromMappedObjNativeQuery(HqlSqlQueryConstants.getBabyDetailList(newUhid));

			if (BasicUtils.isEmpty(babyList)) {

				babyList = patientDao.getListFromMappedObjNativeQuery(HqlSqlQueryConstants.getBabyDetailList(oldUhid));

				if (!BasicUtils.isEmpty(babyList)) {
					updateUhidQuery = "update admission_notes set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update antenatal_history_detail set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update antenatal_steroid_detail set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update baby_addinfo set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update baby_detail set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update baby_prescription set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update baby_visit set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update babyfeed_detail  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update babyfeedivmed_detail set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update babytpnfeed_detail set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update bed_device_detail set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update birth_to_nicu  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update blood_products set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update central_line set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update device_bloodgas_detail_detail  set uhid =  '" + newUhid
							+ "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update device_monitor_detail  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update device_ventilator_detail set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_advice_detail  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_birthdetail  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_feeding  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_infection  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_investigation  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_metabolic  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_nicucourse set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_notes_detail set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_outcome  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_summary  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_treatment  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update discharge_ventilation  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update dischargepatient_detail  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update doctor_notes set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update et_intubation  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update gen_phy_exam set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update headtotoe_examination  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update investigation_ordered  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update lumbar_puncture  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update maternal_pasthistory set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update mother_current_pregnancy set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_bloodgas set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_bloodproducts  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_bolus  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_catheters  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_dailyassesment set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_episode  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_intake set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_intake_output  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_misc set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_neurovitals  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_notes  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_output set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_outputdrain  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_ventilaor  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_vitalparameters  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursingorder_apnea set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursingorder_assesment set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursingorder_jaundice  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursingorder_rds set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursingorder_rds_medicine  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update oralfeed_detail  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update outborn_medicine set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update parent_detail  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update peripheral_cannula set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update procedure_chesttube  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update procedure_other  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update pupil_reactivity set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update readmit_history  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update reason_admission set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update respsupport  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_acidosis  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_cardiac set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_cns set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_cns_asphyxia  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_cns_ivh set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_cns_seizures  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_feedgrowth  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_followup  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hypercalcemia set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hyperglycemia set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hyperkalemia  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hypernatremia set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hypocalcemia  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hypoglycemia  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hypokalemia set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_hyponatremia  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_iem set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_infection_clabsi  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_infection_sepsis  set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_infection_vap set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_jaundice  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_metabolic set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_misc  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_renalfailure  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_apnea  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_bpd  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_chesttube  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_others set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_pneumothorax set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_pphn set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_resp_rds  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_respsystem  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sa_sepsis  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_apgar  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_ballard  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_bellstage  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_bind set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_downes set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_hie  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_ivh  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_levene set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_pain set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_papile set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_sarnat set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_sepsis set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_silverman  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update score_thompson set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update stable_notes set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update systemic_exam  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update test_result_inicu  set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update uhidnotification set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update vtap set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update assessment_medication set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update daily_progress_notes set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update dashboard_device_monitor_detail set uhid =  '" + newUhid
							+ "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update dashboard_device_ventilator_detail set uhid =  '" + newUhid
							+ "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update dashboard_nursing_vitalparameters set uhid =  '" + newUhid
							+ "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update device_bloodgas_detail_detail set uhid =  '" + newUhid
							+ "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update doctor_blood_products set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update doctor_tasks set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update en_feed_detail set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_blood_product set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_medication set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update screen_hearing set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update screen_rop set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update screen_usg set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update screen_metabolic set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nurse_tasks set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update screen_neurological set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update et_suction set uhid =  '" + newUhid + "' where uhid = '" + oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update medication_preparation set uhid =  '" + newUhid + "' where uhid = '"
							+ oldUhid + "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update nursing_heplock set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					updateUhidQuery = "update sample_detail set uhid =  '" + newUhid + "' where uhid = '" + oldUhid
							+ "'";
					inicuDao.updateOrDeleteNativeQuery(updateUhidQuery);

					returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
					returnObj.setMessage("uhid edited successfully.");
				} else {
					returnObj.setType(BasicConstants.MESSAGE_FAILURE);
					returnObj.setMessage("This uhid does not exists.");
				}
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("uhid already exists.");
			}

		} catch (Exception e) {

			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage(e.getMessage().toString());
		}

		return returnObj;

	}

	@Override
	  public NurseShiftSettingPOJO getNurseShiftSettings(String branchName) {
	    NurseShiftSettingPOJO masterObj = new NurseShiftSettingPOJO();
	    ResponseMessageObject response = new ResponseMessageObject();
	    response.setMessage(BasicConstants.MESSAGE_SUCCESS);
	    response.setType(BasicConstants.MESSAGE_SUCCESS);
	    String shift1Timings, shift2Timings, shift3Timings, shift4Timings;
	    int shiftType = 3;
	    shift1Timings = shift2Timings = shift3Timings = shift4Timings = null;
	    NurseShiftSettingJSON nurseShiftSettingPOJO = new NurseShiftSettingJSON();
	    int nursingShiftId = 0;
	    try {

	      List<RefHospitalbranchname> refHospitalbranchname = inicuDao
	          .getListFromMappedObjQuery(HqlSqlQueryConstants.getRefHospitalDetails(branchName));
	      if (!BasicUtils.isEmpty(refHospitalbranchname)) {
	        RefHospitalbranchname hospitaldetails = refHospitalbranchname.get(0);
	        if (!BasicUtils.isEmpty(hospitaldetails.getNursingShiftFormat())) {
	          nursingShiftId = hospitaldetails.getNursingShiftFormat();
	        }
	      }

	      String nursingShiftQuery = "Select obj from NurseShiftSettings as obj where nurseShiftSettingsId ='"
	          + nursingShiftId + "'";
	      List<NurseShiftSettings> nurseShiftSettings = inicuDao.getListFromMappedObjQuery(nursingShiftQuery);
	      if (!BasicUtils.isEmpty(nurseShiftSettings)) {

	        NurseShiftSettings shiftSettings = nurseShiftSettings.get(0);

	        nurseShiftSettingPOJO.setNurseShiftSettingsId(shiftSettings.getNurseShiftSettingsId());
	        shift1Timings = createShiftTimingsString(String.valueOf(shiftSettings.getShift1From()), String.valueOf(shiftSettings.getShift1To()));
	        shift2Timings = createShiftTimingsString(String.valueOf(shiftSettings.getShift2From()), String.valueOf(shiftSettings.getShift2To()));
	        shift3Timings = createShiftTimingsString(String.valueOf(shiftSettings.getShift3From()), String.valueOf(shiftSettings.getShift3To()));
	        shift4Timings = createShiftTimingsString(String.valueOf(shiftSettings.getShift4From()), String.valueOf(shiftSettings.getShift4To()));

	        shiftType = shiftSettings.getShiftType();

	      }
	      nurseShiftSettingPOJO.setShift1Timings(shift1Timings);
	      nurseShiftSettingPOJO.setShift2Timings(shift2Timings);
	      nurseShiftSettingPOJO.setShift3Timings(shift3Timings);
	      nurseShiftSettingPOJO.setShift4Timings(shift4Timings);
	      nurseShiftSettingPOJO.setShiftType(shiftType);

	      masterObj.setNurseShiftSettings(nurseShiftSettingPOJO);

	    } catch (Exception ex) {
	      ex.printStackTrace();
	      response.setMessage(BasicConstants.MESSAGE_FAILURE);
	      response.setType(BasicConstants.MESSAGE_FAILURE);

	    }

	    return masterObj;
	  }

	  private String createShiftTimingsString(String shiftFrom, String shiftTo) {
	    String shiftTimings = "";
	    String fromTime = "";
	    String toTime = "";
	    Date fromDate = null;
	    Date toDate = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("HH");
	    try {
	      if(!BasicUtils.isEmpty(shiftFrom))
	        fromDate = sdf.parse(shiftFrom);
	      
	      if(!BasicUtils.isEmpty(shiftTo))
	        toDate = sdf.parse(shiftTo);
	      
	      SimpleDateFormat sdf2 = new SimpleDateFormat("hh aa");
	      
	      if(!BasicUtils.isEmpty(fromDate))
	      fromTime = sdf2.format(fromDate);
	      
	      if(!BasicUtils.isEmpty(toDate))
	      toTime = sdf2.format(toDate);
	      
	      
	      
	    }catch(Exception ex) {
	      
	      ex.printStackTrace();
	    }


	    shiftTimings =  fromTime + "-" + toTime;
	    shiftTimings = shiftTimings.replaceAll(" ", "").replaceAll("AM", "am").replaceAll("PM", "pm");

	    return shiftTimings;
	  }


	@Override
	public ResponseMessageObject saveNurseShiftSettings(String branchName, NurseShiftSettings nurseShiftSettings) {
		ResponseMessageObject response = new ResponseMessageObject();

//		int nursingShiftId = 0;
//		long newNurseShiftId = 0;
//
//		List<RefHospitalbranchname> refHospitalbranchname = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getRefHospitalDetails(branchName));
//		if (!BasicUtils.isEmpty(refHospitalbranchname)) {
//			RefHospitalbranchname hospitaldetails = refHospitalbranchname.get(0);
//			if (!BasicUtils.isEmpty(hospitaldetails.getNursingShiftFormat())) {
//				nursingShiftId = hospitaldetails.getNursingShiftFormat();
//			}
//		}
//
//		if (!BasicUtils.isEmpty(nursingShiftId) && nursingShiftId != 0) {
//			nurseShiftSettings.setNurseShiftSettingsId((long) nursingShiftId);
//			settings = (NurseShiftSettings) inicuDao.saveObject(nurseShiftSettings);
//
//		} else {
//			settings = (NurseShiftSettings) inicuDao.saveObject(nurseShiftSettings);
//			newNurseShiftId = settings.getNurseShiftSettingsId();
//
//			inicuDao.updateOrDeleteNativeQuery("update ref_hospitalbranchname set nursing_shift_format ="
//					+ newNurseShiftId + " " + "where branchname='" + branchName + "'");
//		}
		NurseShiftSettings settings = new NurseShiftSettings();
		try {
			settings = (NurseShiftSettings) inicuDao.saveObject(nurseShiftSettings);
			response.setMessage(BasicConstants.MESSAGE_SUCCESS);
			response.setType(BasicConstants.MESSAGE_SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setMessage(BasicConstants.MESSAGE_FAILURE);
			response.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return response;
	}

	public NurseShiftSettings getNursingShiftSetting(String branchName){
		String getQuery = "SELECT obj FROM NurseShiftSettings obj WHERE branchname ='"+branchName+"'";
		List<NurseShiftSettings> nursingShiftSettingList = inicuDao.getListFromMappedObjQuery(getQuery);

		if(nursingShiftSettingList.size()>0){
			return nursingShiftSettingList.get(0);
		}
		return null;
	}

	public List<UserShiftMapping> checkPresenceInListThenAdd(List<UserShiftMapping> list,UserShiftMapping newObject){
		boolean foundFlag = false;

		for (UserShiftMapping listItem:list) {
			if(listItem.getUserId().equals(newObject.getUserId())){
				foundFlag = true;
				break;
			}
		}

		if(!foundFlag){
			list.add(newObject);
		}

		return list;
	}

	@Override
	public GeneralResponseObject getDoctorShiftMapping(String branchName, Timestamp dateOfShift) {
		// Format the upcoming timestamp into a date format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDateObject = new Date(dateOfShift.getTime());
		String dateOfShiftTemp = sdf.format(currentDateObject);

		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		NurseShiftsPOJO nurseShiftsPOJO = new NurseShiftsPOJO();
		try {
			// get Nursing Shift Settings
			NurseShiftSettings nurseShiftSettingsObject = getNursingShiftSetting(branchName);
			nurseShiftsPOJO.setNurseShiftSettings(nurseShiftSettingsObject);

			List<UserShiftMapping> doctorShiftMappingsList = new ArrayList<>();

			String doctorMapping = "select shifts.shift_mapping_id,shifts.creationtime,shifts.modificationtime,x.user_id,shift1,shift2,shift3,shift4,date_of_shift,x.firstname,x.username" +
					" from ( Select a.id as user_id, concat(a.firstname,' ',a.lastname) as firstname,a.username" +
					" from inicuuser a left join users_roles b on a.username = b.username" +
					" where (b.roleid = '2' or b.roleid = '3') and a.active = 1 and a.branchname='" + branchName + "' and b.branchname='" +branchName+ "' order by user_id) as x" +
					" left join user_shift_mapping shifts on x.user_id = shifts.user_id where (date(shifts.date_of_shift) ='" + dateOfShiftTemp + "' or shifts.date_of_shift is NULL) and (shifts.user_type = 1 or shifts.user_type is null)";

			List<Object[]> doctorResult = inicuDao.getListFromNativeQuery(doctorMapping);
			for (Object[] arr : doctorResult) {
				UserShiftMapping objmap = new UserShiftMapping(arr);
				objmap.setUserType(1);
				doctorShiftMappingsList.add(objmap);
			}

			// Get all Doctor that are active
			String getActiveDoctorQuery = "Select a.id as user_id, concat(a.firstname,' ',a.lastname) as firstname,a.username" +
					" from inicuuser a left join users_roles b on a.username = b.username" +
					" where (b.roleid='2' or b.roleid = '3') and a.active = 1 and a.branchname='" +branchName+ "' and b.branchname='" + branchName + "' order by user_id";

			List<Object[]> getActiveDoctorQueryList = inicuDao.getListFromNativeQuery(getActiveDoctorQuery);

			for (Object[] arr : getActiveDoctorQueryList) {
				// Check if the object present in the nurseShiftMappingsList then don't add it else add it
				UserShiftMapping objmap = new UserShiftMapping(BasicUtils.toLongSafe(arr[0]),BasicUtils.toStringSafe(arr[1]),BasicUtils.toStringSafe(arr[2]));
				objmap.setUserType(1);
				doctorShiftMappingsList = checkPresenceInListThenAdd(doctorShiftMappingsList,objmap);
			}

			nurseShiftsPOJO.setNurseShiftList(null);
			nurseShiftsPOJO.setDoctorShiftList(doctorShiftMappingsList);

			Map<String, List<String>> map = getShiftLabels();
			nurseShiftsPOJO.setConstantMap(map);

			generalResponseObject.setStatus(true);
			generalResponseObject.setStatusCode(200);
			generalResponseObject.setMessage("Nurse Shift Mappings");
			generalResponseObject.setData(nurseShiftsPOJO);
		} catch (Exception ex) {
			ex.printStackTrace();
			generalResponseObject.setStatus(false);
			generalResponseObject.setStatusCode(500);
			generalResponseObject.setMessage("Internal Server Error");
			generalResponseObject.setData(nurseShiftsPOJO);
		}

		return generalResponseObject;
	}

	@Override
	public GeneralResponseObject getNurseShiftMapping(String branchName, Timestamp dateOfShift) {
		// Format the upcoming timestamp into a date format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDateObject = new Date(dateOfShift.getTime());
		String dateOfShiftTemp = sdf.format(currentDateObject);

		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		NurseShiftsPOJO nurseShiftsPOJO = new NurseShiftsPOJO();

		try {
			// get Nursing Shift Settings
			NurseShiftSettings nurseShiftSettingsObject = getNursingShiftSetting(branchName);
			nurseShiftsPOJO.setNurseShiftSettings(nurseShiftSettingsObject);

			List<UserShiftMapping> nurseShiftMappingsList = new ArrayList<>();
			String nurseMapping = "select shifts.shift_mapping_id,shifts.creationtime,shifts.modificationtime,x.user_id,shift1,shift2,shift3,shift4,date_of_shift,x.firstname,x.username" +
					" from ( Select a.id as user_id, concat(a.firstname,' ',a.lastname) as firstname,a.username" +
					" from inicuuser a left join users_roles b on a.username = b.username" +
					" where b.roleid='5' and a.active = 1 and a.branchname='" + branchName + "' and b.branchname='" +branchName+ "' order by user_id) as x" +
					" left join user_shift_mapping shifts on x.user_id = shifts.user_id where (date(shifts.date_of_shift) ='" + dateOfShiftTemp + "' or shifts.date_of_shift is NULL) and (shifts.user_type = 0 or shifts.user_type is null)";

			List<Object[]> result = inicuDao.getListFromNativeQuery(nurseMapping);
			for (Object[] arr : result) {
				UserShiftMapping objmap = new UserShiftMapping(arr);
				objmap.setUserType(0);
				nurseShiftMappingsList.add(objmap);
			}

			// Get al nurse that are active
			String getActiveNurseQuery = "Select a.id as user_id, concat(a.firstname,' ',a.lastname) as firstname,a.username" +
					" from inicuuser a left join users_roles b on a.username = b.username" +
					" where b.roleid='5' and a.active = 1 and a.branchname='" +branchName+ "' and b.branchname='" + branchName + "' order by user_id";

			List<Object[]> activeQueryList = inicuDao.getListFromNativeQuery(getActiveNurseQuery);

			for (Object[] arr : activeQueryList) {
				// Check if the object present in the nurseShiftMappingsList then don't add it else add it
				UserShiftMapping objmap = new UserShiftMapping(BasicUtils.toLongSafe(arr[0]),BasicUtils.toStringSafe(arr[1]),BasicUtils.toStringSafe(arr[2]));
				objmap.setUserType(0);
				nurseShiftMappingsList = checkPresenceInListThenAdd(nurseShiftMappingsList,objmap);
			}

			nurseShiftsPOJO.setNurseShiftList(nurseShiftMappingsList);
			nurseShiftsPOJO.setDoctorShiftList(null);

			Map<String, List<String>> map = getShiftLabels();
			nurseShiftsPOJO.setConstantMap(map);

			generalResponseObject.setStatus(true);
			generalResponseObject.setStatusCode(200);
			generalResponseObject.setMessage("Nurse Shift Mappings");
			generalResponseObject.setData(nurseShiftsPOJO);
		} catch (Exception ex) {

			ex.printStackTrace();
			generalResponseObject.setStatus(false);
			generalResponseObject.setStatusCode(500);
			generalResponseObject.setMessage("Internal Server Error");
			generalResponseObject.setData(nurseShiftsPOJO);
		}

		return generalResponseObject;
	}

	public Map<String, List<String>> getShiftLabels() {
		Map<String, List<String>> map = new HashMap<>();
		map.put("2 Shifts", new ArrayList<String>(Arrays.asList("Day", "Night")));
		map.put("3 Shifts", new ArrayList<String>(Arrays.asList("Morning", "Evening", "Night")));
		map.put("4 Shifts", new ArrayList<String>(Arrays.asList("Shift 1", "Shift 2", "Shift 3", "Shift 4")));
		return map;

	}

	@Override
	public ResponseMessageObject saveNurseShiftMapping(List<UserShiftMapping> nurseShiftList) {

		ResponseMessageObject response = new ResponseMessageObject();
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		List<UserShiftMapping> userShiftMappings = nurseShiftList;
		List<UserShiftMapping> tempList = new ArrayList<>();

		Date date = new Date();
		Timestamp currentDay = new Timestamp(date.getTime());
		currentDay.setHours(0);
		currentDay.setMinutes(0);
		currentDay.setSeconds(0);
		currentDay.setNanos(0);
		System.out.println("" + currentDay);
		Timestamp shiftDay = null;
		long counter;

		try {
			if (!BasicUtils.isEmpty(userShiftMappings)) {
				for (int i = 0; i < userShiftMappings.size(); i++) {
					if (!BasicUtils.isEmpty(userShiftMappings.get(i).getDateOfShift())) {
						shiftDay = userShiftMappings.get(i).getDateOfShift();
						if (shiftDay.before(currentDay)) {
							response.setMessage("Illegal shift day selected");
							return response;
						}

						counter = (shiftDay.getTime() - currentDay.getTime()) / (1000 * 60 * 60 * 24);
						if (counter > 3) {
							response.setMessage("Illegal shift day selected");
							return response;
						}
						tempList.add(userShiftMappings.get(i));
					}
				}
				response.setMessage("Successful");
				response.setType(BasicConstants.MESSAGE_SUCCESS);
				userShiftMappings = (List<UserShiftMapping>) inicuDao.saveMultipleObject(userShiftMappings);
			}

		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			e.printStackTrace();
			response.setMessage(BasicConstants.MESSAGE_FAILURE);
			response.setType(BasicConstants.MESSAGE_FAILURE);
		}

		return response;

	}

	private static final SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("dd-MM-yyyy");

	public static String getDateFromTimestamp(Timestamp time) {
		String date = monthDayYearformatter.format((java.util.Date) time);
		return date;
	}

	@Override
	public GeneralResponseObject getNurseBabyMapping(Timestamp dateOfShiftTemp, String branchName) {
		NurseBabyMasterPOJO masterPOJO = new NurseBabyMasterPOJO();
		GeneralResponseObject generalResponseObject = new GeneralResponseObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDateObject = new Date(dateOfShiftTemp.getTime());
			String dateOfShift = sdf.format(currentDateObject);

			// get the nurse Shift Settings
			String getQuery = "SELECT obj FROM NurseShiftSettings obj Where branchname = '" + branchName +"'";
			List<NurseShiftSettings> settings = inicuDao.getListFromMappedObjQuery(getQuery);
			if(settings.size()>0){
				masterPOJO.setNurseShiftSettings(settings.get(0));
			}

			Map<String, String> map = new HashMap<>();
			map.put("Shift 1", "shift1");
			map.put("Shift 2", "shift2");
			map.put("Shift 3", "shift3");
			map.put("Shift 4", "shift4");

			List<String> shiftTypeList = new ArrayList<>();
			shiftTypeList.addAll(map.keySet());
			List<NurseBaby> shiftOne = new ArrayList<>();
			List<NurseBaby> shiftTwo = new ArrayList<>();
			List<NurseBaby> shiftThree = new ArrayList<>();
			List<NurseBaby> shiftFour = new ArrayList<>();

			for (String shift : shiftTypeList) {
				String shiftType = shift;

				String fetchNurseDetailQuery = "SELECT DISTINCT nurseShift.user_id , nurseShift.name , nurseShift.username"
						+ " from user_shift_mapping nurseShift " + "left join nurse_baby_mapping nurseBaby on "
						+ "nurseShift.user_id = nurseBaby.nurse_id where DATE(nurseShift.date_of_shift)= '"
						+ dateOfShift + "' and user_type ='0' and  " + map.get(shiftType) + "= true";

				List<Object[]> distinctNurseList = inicuDao.getListFromNativeQuery(fetchNurseDetailQuery);

				for (Object[] nurseData : distinctNurseList) {

					NurseBaby nurseBaby = new NurseBaby();
					nurseBaby.setNurseId(BasicUtils.toLongSafe(nurseData[0]));
					nurseBaby.setNurse(BasicUtils.toStringSafe(nurseData[1]));
					nurseBaby.setNurseUsername(BasicUtils.toStringSafe(nurseData[2]));

					String fetchMappedBaby = "Select uhid , baby_name ,baby_number , baby_type from nurse_baby_mapping where shift_type='"
							+ shiftType + "' and DATE(date_of_shift)='" + dateOfShift + "' and nurse_id="
							+ BasicUtils.toLongSafe(nurseData[0]);

					List<Object[]> babyDetailObjectList = inicuDao.getListFromNativeQuery(fetchMappedBaby);
					List<BabyBasicDetail> babyDetailList = new ArrayList<>();

					for (Object[] babyDetail : babyDetailObjectList) {
						BabyBasicDetail babyBasicDetail = new BabyBasicDetail(babyDetail);
						babyDetailList.add(babyBasicDetail);
					}

					nurseBaby.setBabyList(babyDetailList);

					if (shiftType.equalsIgnoreCase("Shift 1"))
						shiftOne.add(nurseBaby);
					else if (shiftType.equalsIgnoreCase("Shift 2"))
						shiftTwo.add(nurseBaby);
					else if (shiftType.equalsIgnoreCase("Shift 3"))
						shiftThree.add(nurseBaby);
					else
						shiftFour.add(nurseBaby);
				}
			}

			masterPOJO.setShift1(shiftOne);
			masterPOJO.setShift2(shiftTwo);
			masterPOJO.setShift3(shiftThree);
			masterPOJO.setShift4(shiftFour);

			generalResponseObject.setStatus(true);
			generalResponseObject.setStatusCode(200);
			generalResponseObject.setMessage("Nurse Baby Mapping");
			generalResponseObject.setData(masterPOJO);

		} catch (Exception e) {
			e.printStackTrace();
			generalResponseObject.setStatus(false);
			generalResponseObject.setStatusCode(500);
			generalResponseObject.setMessage("Internal Server Error");
			generalResponseObject.setData(null);
		}

		return generalResponseObject;
	}

	@Override
	public ResponseMessageObject saveNurseBabyMapping(List<NurseBabyPOJO> nurseBabyPOJO, Timestamp dateOfShift,
			String branchName) {
		ResponseMessageObject response = new ResponseMessageObject();

		try {
			for (NurseBabyPOJO nurseBabyDetail : nurseBabyPOJO) {

				List<NurseDetail> nurseDetailList = new ArrayList<>();

				if(!BasicUtils.isEmpty(nurseBabyDetail.getShift1())){
					nurseDetailList.addAll(nurseBabyDetail.getShift1());
				}

				if(!BasicUtils.isEmpty(nurseBabyDetail.getShift2())){
					nurseDetailList.addAll(nurseBabyDetail.getShift2());
				}

				if(!BasicUtils.isEmpty(nurseBabyDetail.getShift3())){
					nurseDetailList.addAll(nurseBabyDetail.getShift3());
				}

				if(!BasicUtils.isEmpty(nurseBabyDetail.getShift4())){
					nurseDetailList.addAll(nurseBabyDetail.getShift4());
				}

				for (NurseDetail nurseDetail : nurseDetailList) {
					NurseBabyMapping nurseBabyMapping = new NurseBabyMapping();
					if (!BasicUtils.isEmpty(nurseDetail.getNurseBabyMappingId())){
						nurseBabyMapping.setNurseBabyMappingId(nurseDetail.getNurseBabyMappingId());
					}

					nurseBabyMapping.setUhid(nurseBabyDetail.getUhid());
					nurseBabyMapping.setBabyName(nurseBabyDetail.getBabyName());
					nurseBabyMapping.setBabyNumber(nurseBabyDetail.getBabyNumber());
					nurseBabyMapping.setBabyType(nurseBabyDetail.getBabyType());
					nurseBabyMapping.setDateOfShift(dateOfShift);
					nurseBabyMapping.setShiftType(nurseDetail.getShiftType());
					nurseBabyMapping.setNurseId(nurseDetail.getNurseId());
					nurseBabyMapping.setNurse(nurseDetail.getNurse());
					nurseBabyMapping.setNurseUsername(nurseDetail.getNurseUsername());
					nurseBabyMapping.setBranchName(branchName);
					nurseBabyMapping = (NurseBabyMapping) inicuDao.saveObject(nurseBabyMapping);
				}
			}
			response.setMessage("Saved Successfully");
			response.setType(BasicConstants.MESSAGE_SUCCESS);

		} catch (Exception e) {
			response.setMessage("Not saved successfully");
			response.setType(BasicConstants.MESSAGE_FAILURE);
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<NurseBabyPOJO> getAllMappedNurseBaby(Timestamp dateOfShiftTemp, String branchName) {
//		NurseBabyPOJO nurseBabyPOJO = new NurseBabyPOJO();
		List<NurseBabyPOJO> mappedNurseBaby = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDateObject = new Date(dateOfShiftTemp.getTime());
		String dateOfShift = sdf.format(currentDateObject);

		try {
			Map<String, String> map = new HashMap<>();
			map.put("Shift 1", "shift1");
			map.put("Shift 2", "shift2");
			map.put("Shift 3", "shift3");
			map.put("Shift 4", "shift4");

			String fetchMappedBabiesQuery = "Select DISTINCT uhid , baby_name , baby_type , baby_number from nurse_baby_mapping"
					+ " where date(date_of_shift)='" + dateOfShift + "' and branch_name ='" + branchName + "'";
			List<Object[]> babyDetails = inicuDao.getListFromNativeQuery(fetchMappedBabiesQuery);
			for (Object[] babyDetail : babyDetails) {

				NurseBabyPOJO nurseBabyPOJO = new NurseBabyPOJO();
				nurseBabyPOJO.setUhid(BasicUtils.toStringSafe(babyDetail[0]));
				nurseBabyPOJO.setBabyName(BasicUtils.toStringSafe(babyDetail[1]));
				nurseBabyPOJO.setBabyType(BasicUtils.toStringSafe(babyDetail[2]));
				nurseBabyPOJO.setBabyNumber(BasicUtils.toStringSafe(babyDetail[3]));

				
				for (String shiftType : map.keySet()) {
					List<NurseDetail> nurseDetailList = new ArrayList<>();
					String fetchNurseDetail = "Select obj from NurseBabyMapping as obj where uhid='" + BasicUtils.toStringSafe(babyDetail[0]) + "' and shiftType='"
							+ map.get(shiftType) + "'";
					List<NurseBabyMapping> listMappedBabies = inicuDao.getListFromMappedObjQuery(fetchNurseDetail);
					for (NurseBabyMapping mappedBaby : listMappedBabies) {

						NurseDetail nurseDetail = new NurseDetail();
						nurseDetail.setNurse(mappedBaby.getNurse());
						nurseDetail.setNurseUsername(mappedBaby.getNurseUsername());
						nurseDetail.setNurseId(mappedBaby.getNurseId());
						nurseDetail.setNurseBabyMappingId(mappedBaby.getNurseBabyMappingId());
						nurseDetail.setShiftType(mappedBaby.getShiftType());
						nurseDetailList.add(nurseDetail);

					}
					
					if(shiftType.equalsIgnoreCase("Shift 1"))
						nurseBabyPOJO.setShift1(nurseDetailList);
					else if(shiftType.equalsIgnoreCase("Shift 2"))
						nurseBabyPOJO.setShift2(nurseDetailList);
					else if(shiftType.equalsIgnoreCase("Shift 3"))
						nurseBabyPOJO.setShift3(nurseDetailList);
					else
						nurseBabyPOJO.setShift4(nurseDetailList);
				}
				mappedNurseBaby.add(nurseBabyPOJO);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return mappedNurseBaby;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DisplayDischargedPatientJSON displayDetails(String dischargedUhid, String branchName) {

		// to display details of discharged patient
		DisplayDischargedPatientJSON returnObj = new DisplayDischargedPatientJSON();
		List<BabyDetail> dashboardList = new ArrayList<BabyDetail>();
		try {
			returnObj.setUhid(dischargedUhid);

			String existingUhidQuery = "select obj from BabyDetail as obj where uhid = '" + dischargedUhid + "' order by creationtime";
			List<BabyDetail> list = inicuDao.getListFromMappedObjQuery(existingUhidQuery);

			if (!BasicUtils.isEmpty(list)) {

				String query = "Select obj from BabyDetail as obj where uhid = '" + dischargedUhid
						+ "' and dischargestatus != null and admissionstatus = 'f' and branchname = '" + branchName + "' order by creationtime desc";
				List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(query);

				if (!BasicUtils.isEmpty(babyDetailList)) {
					
					//in case there are two rows (one being admission status = false and one with admissionstatus = true) - that is when baby is already readmitted once.
					String query1 = "select obj from BabyDetail as obj where uhid = '" + dischargedUhid + "' and admissionstatus = 't' and branchName = '" + branchName + "' ";
					 dashboardList = inicuDao.getListFromMappedObjQuery(query1);
					
					if(BasicUtils.isEmpty(dashboardList)) {
						
						String bedNumber = babyDetailList.get(0).getNicubedno();
						returnObj.setBedNumber(bedNumber);
						returnObj.setAdmissionstatus(babyDetailList.get(0).getAdmissionstatus());
						returnObj.setDischargestatus(babyDetailList.get(0).getDischargestatus());
						returnObj.setDischargeDate(babyDetailList.get(0).getDischargeddate());
						returnObj.setIsreadmitted(babyDetailList.get(0).getIsreadmitted());
	
						String query2 = "select obj from RefBed as obj where bedid = '" + bedNumber + "'";
						List<RefBed> bedDetailList = inicuDao.getListFromMappedObjQuery(query2);
						boolean bedStatus = bedDetailList.get(0).getStatus();
						returnObj.setBedStatus(bedStatus);
						
						returnObj.setRoomNumbers(getActiveRooms(branchName));
						
					} else {
						
						returnObj.setMessage("Baby is on dashboard");
						returnObj.setAdmissionstatus(dashboardList.get(0).getAdmissionstatus());
					}
				}
				// baby has not been discharged yet
				else {
					if(list.get(0).getDischargestatus()!=null)
						returnObj.setMessage("Baby is on dashboard");
					else
						returnObj.setMessage("Baby has not yet been discharged.");
					returnObj.setAdmissionstatus(list.get(0).getAdmissionstatus());
				}
			}
			// if baby does not exist
			else {
				returnObj.setMessage("This uhid does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					dischargedUhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;

	}
	
	public List getActiveRooms(String branchName) {
		List<NicuRoomObj> nicuRooms = new ArrayList<NicuRoomObj>();
		List<KeyValueObj> beds = new ArrayList<KeyValueObj>();
		try {
			String query = "select obj from RefRoom as obj where isactive='true' and branchname = '" + branchName + "'";
			List<RefRoom> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj room = null;
			if (refList != null) {
				java.util.Iterator<RefRoom> iterator = refList.iterator();
				while (iterator.hasNext()) {
					NicuRoomObj nicuRoom = new NicuRoomObj();
					room = new KeyValueObj();
					RefRoom obj = iterator.next();
					room.setKey(obj.getRoomid());
					room.setValue(obj.getRoomname());
					nicuRoom.setRoom(room);
					nicuRoom.setBed(getActiveBeds(room.getKey().toString()));
					nicuRooms.add(nicuRoom);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nicuRooms;
	}
	
	public List getActiveBeds(String roomId) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			String query = "";

			query = "select obj from RefBed as obj where status='true' and isactive='true' and roomid='" + roomId + "'";

			List<RefBed> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null) {
				java.util.Iterator<RefBed> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					RefBed obj = iterator.next();
					keyValue.setKey(obj.getBedid());
					keyValue.setValue(obj.getBedname());
					refKeyValueList.add(keyValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}


	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageObject readmit(String dischargedUhid, String branchName, String nicubedno, String roomnumber) {

		// to readmit the discharged patient click Readmit
		ResponseMessageObject returnObj = new ResponseMessageObject();
		try {
			String nicuBedNo = "";
			String queryBabyDetails = "select obj from BabyDetail as obj where uhid = '" + dischargedUhid
					+ "' and dischargestatus != null and admissionstatus = 'f' and branchname = '" + branchName
					+ "' order by creationtime desc";
			List<BabyDetail> babyDetailsList = inicuDao.getListFromMappedObjQuery(queryBabyDetails);

			if (!BasicUtils.isEmpty(babyDetailsList)) {

				nicuBedNo = babyDetailsList.get(0).getNicubedno();
				String babyNum = babyDetailsList.get(0).getBabyNumber();
				String babydetailid = babyDetailsList.get(0).getBabydetailid().toString();

				String queryBedDetails = "select obj from RefBed as obj where bedid = '" + nicuBedNo + "'";
				List<RefBed> bedDetailsList = inicuDao.getListFromMappedObjQuery(queryBedDetails);

				boolean bedStatus = bedDetailsList.get(0).getStatus();
				boolean isActive = bedDetailsList.get(0).getIsactive();

				// if bed status is true - it is available
				if (bedStatus == true) {
					// bed is active
					if (isActive == true) {

						String query = "update baby_detail set admissionstatus = 't' where babydetailid = '"
								+ babydetailid + "'";
						inicuDao.updateOrDeleteNativeQuery(query);

						String query2 = "update ref_bed set status = 'f' where bedid = '" + nicuBedNo
								+ "' and branchname = '" + branchName + "'";
						inicuDao.updateOrDeleteNativeQuery(query2);
					}
					// bed is inactive
					else {
						dischargeStatusFalse(dischargedUhid, branchName, babydetailid, nicubedno , roomnumber);
					}

				}
				// bed status is false
				else {
					dischargeStatusFalse(dischargedUhid, branchName, babydetailid, nicubedno , roomnumber);
				}
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				returnObj.setMessage("Baby readmitted.");
			}
			// discharge failed
			else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("This baby is not discharged.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					dischargedUhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage(e.getMessage().toString());
		}
		return returnObj;

	}
	
	
	@SuppressWarnings("unchecked")
	public AssessmentStatusPOJO checkAssessmentStatus(String uhid) {
		
		AssessmentStatusPOJO returnObj = new AssessmentStatusPOJO();
		
		try {
			//checking the status of initial assessment form (isassessmentsubmit field)
			String query = "select obj from BabyDetail as obj where uhid = '" + uhid + "' order by creationtime desc";
			List<BabyDetail> list = inicuDao.getListFromMappedObjQuery(query);
			
			if(!BasicUtils.isEmpty(list)) {
				
				if(list.get(0).getAdmissionstatus() == true) {
					
					String assessmentStatus = "";
					if(list.get(0).getIsassessmentsubmit()!=null) {
						 assessmentStatus = list.get(0).getIsassessmentsubmit().toString();
					}
					String Uhid = list.get(0).getUhid();
					if(assessmentStatus == "" || assessmentStatus == null) {
						assessmentStatus = "false";
						
					}
					
					if(assessmentStatus == "false") {
						returnObj.setMessage("Initial assessment form is already editable");
					}
					returnObj.setAssessmentStatus(assessmentStatus);
					returnObj.setUhid(Uhid);
				
				
				} else {
					returnObj.setMessage("You can not change the status of this baby as the baby is already discharged");
				}
			}
			else {
				returnObj.setMessage("This UHID does not exist");
			}
		} catch(Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage(e.getMessage().toString());
		}

		return returnObj;
		
	}
	
	public ResponseMessageObject updateAssessmentStatus(String uhid) {
		
		ResponseMessageObject returnObj = new ResponseMessageObject();
		
		try {
			
			String query = "select obj from BabyDetail as obj where uhid = '" + uhid + "' order by creationtime desc";
			List<BabyDetail> list = inicuDao.getListFromMappedObjQuery(query);
			
			if(!BasicUtils.isEmpty(list)) {
				
				if(list.get(0).getIsassessmentsubmit()!=null ) {
					boolean assessmentStatus = list.get(0).getIsassessmentsubmit();
					
					String babyDetailId = list.get(0).getBabydetailid().toString();
					
					if(assessmentStatus == true) {
						
						//updating the status from true to false, meaning making the form editable
						String updateQuery = "update baby_detail set isassessmentsubmit = false where babydetailid = '" + babyDetailId + "' ";
						inicuDao.updateOrDeleteNativeQuery(updateQuery);
						
						returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
						returnObj.setMessage("The status is updated");
						
					} else {
						returnObj.setMessage("Initial assessment form is already editable.");
					}
				}
				//if the form is already editable
				else {
					returnObj.setMessage("Initial assessment form is already editable.");
				}
				
			} else {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("This UHID does not exist.");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage(e.getMessage().toString());
		}
		
		
		return returnObj;
		
	}
	
	public void dischargeStatusFalse(String dischargedUhid, String branchName, String babydetailid, String nicubedno , String roomnumber) {

//		String queryOld = "select obj from BabyDetail as obj where uhid = '" + dischargedUhid + "'";
//		List<BabyDetail> oldBabyDetails = inicuDao.getListFromMappedObjQuery(queryOld);
//		boolean admissionstatus = oldBabyDetails.get(0).getAdmissionstatus();
//		String oldbedno = oldBabyDetails.get(0).getNicubedno();
//		String oldroomNo = oldBabyDetails.get(0).getNicuroomno();
//
//		String query1 = "update ref_bed set status = 't' where bedid = '" + oldbedno + "' and roomid = '" + oldroomNo
//				+ "'";
//		inicuDao.updateOrDeleteNativeQuery(query1);
//
//		String querybeddetails = "select obj from RefBed as obj where status = 't' and branchname = '" + branchName
//				+ "' and isactive = 't'";
//		List<RefBed> bedList = inicuDao.getListFromMappedObjQuery(querybeddetails);
//
//		String roomid = bedList.get(0).getRoomid();
//		String bedid = bedList.get(0).getBedid();

		String query2 = "update ref_bed set status = 'f' where bedid = '" + nicubedno + "' and branchname = '" + branchName
				+ "'";
		inicuDao.updateOrDeleteNativeQuery(query2);

		String query = "update baby_detail set admissionstatus = 't', nicubedno = '" + nicubedno + "' , nicuroomno = '"
				+ roomnumber + "' where babydetailid = '" + babydetailid + "'";
		inicuDao.updateOrDeleteNativeQuery(query);

	}

	@Override
	public NurseShiftDetail getNurseDetail(String branchName, String dateOfShift, Integer nurseId) {
		NurseShiftDetail nurseDetail = new NurseShiftDetail();
		int nursingShiftId = 0;
		try {
			
			nurseDetail.setNurseId(nurseId);
			nurseDetail.setDateOfShift(dateOfShift);
			
			String query = "Select obj from RefHospitalbranchname as obj where branchname ='" + branchName + "'";
			List<RefHospitalbranchname> refHospitalbranchname = inicuDao.getListFromMappedObjQuery(query);
			
			if (!BasicUtils.isEmpty(refHospitalbranchname)) {
				RefHospitalbranchname hospitaldetails = refHospitalbranchname.get(0);
				if (!BasicUtils.isEmpty(hospitaldetails.getNursingShiftFormat())) {
					nursingShiftId = hospitaldetails.getNursingShiftFormat();
				}
			}

			NurseShiftSettings shiftSettings = new NurseShiftSettings();
			String nursingShiftQuery = "Select obj from NurseShiftSettings as obj where nurseShiftSettingsId ='"
					+ nursingShiftId + "'";
			List<NurseShiftSettings> nurseShiftSettings = inicuDao.getListFromMappedObjQuery(nursingShiftQuery);
			if (!BasicUtils.isEmpty(nurseShiftSettings)) {
				shiftSettings = nurseShiftSettings.get(0);
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift1From()))
//				nurseDetail.setShift1From(shiftSettings.getShift1From());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift1To()))
//				nurseDetail.setShift1To(shiftSettings.getShift1To());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift2From()))
//				nurseDetail.setShift2From(shiftSettings.getShift2From());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift2To()))
//				nurseDetail.setShift2To(shiftSettings.getShift2To());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift3From()))
//				nurseDetail.setShift3From(shiftSettings.getShift3From());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift3To()))
//				nurseDetail.setShift3To(shiftSettings.getShift3To());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift4From()))
//				nurseDetail.setShift4From(shiftSettings.getShift4From());
//
//				if(!BasicUtils.isEmpty(shiftSettings.getShift4To()))
//				nurseDetail.setShift4To(shiftSettings.getShift4To());
				
				}
			
			UserShiftMapping nurseShiftMap = new UserShiftMapping();
			String nurseShiftMapQuery = "Select obj from NurseShiftMapping as obj where nurseId ="+nurseId+" and "
					+ "DATE(dateOfShift)='"+dateOfShift+"'";
			List<UserShiftMapping> userShiftMappings = inicuDao.getListFromMappedObjQuery(nurseShiftMapQuery);
			if(!BasicUtils.isEmpty(userShiftMappings)) {
				nurseShiftMap = userShiftMappings.get(0);
				
				if(!BasicUtils.isEmpty(nurseShiftMap.getShift1()))
				nurseDetail.setShift1(nurseShiftMap.getShift1());
				
				if(!BasicUtils.isEmpty(nurseShiftMap.getShift2()))
				nurseDetail.setShift2(nurseShiftMap.getShift2());
				
				if(!BasicUtils.isEmpty(nurseShiftMap.getShift3()))
				nurseDetail.setShift3(nurseShiftMap.getShift3());
				
				if(!BasicUtils.isEmpty(nurseShiftMap.getShift4()))
				nurseDetail.setShift4(nurseShiftMap.getShift4());
			}
			

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return nurseDetail;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public DisplayPatientDataJSON validateDischarge(String uhidToDischarge, String branchName) {

		// to display details of patient which needs to be discharged
		DisplayPatientDataJSON returnObj = new DisplayPatientDataJSON();
		try {
			returnObj.setUhid(uhidToDischarge);

			String existingUhidQuery = "select obj from BabyDetail as obj where uhid = '" + uhidToDischarge + "' and branchname ='" + branchName + "' ";
			List<BabyDetail> list = inicuDao.getListFromMappedObjQuery(existingUhidQuery);

			if (!BasicUtils.isEmpty(list)) {

				String query = "Select obj from BabyDetail as obj where uhid = '" + uhidToDischarge
						+ "' and admissionstatus = 't' and branchname = '" + branchName + "' order by creationtime desc";
				List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(query);

				if (!BasicUtils.isEmpty(babyDetailList)) {
					
						String bedNumber = babyDetailList.get(0).getNicubedno();
						returnObj.setBedNumber(bedNumber);
						returnObj.setAdmissionstatus(babyDetailList.get(0).getAdmissionstatus());
						returnObj.setRoomNumber(babyDetailList.get(0).getNicuroomno());

						String query2 = "select obj from RefBed as obj where bedid = '" + bedNumber + "' and roomid = '" + babyDetailList.get(0).getNicuroomno() + "' ";
						List<RefBed> bedDetailList = inicuDao.getListFromMappedObjQuery(query2);
						boolean bedStatus = bedDetailList.get(0).getStatus();
						returnObj.setBedStatus(bedStatus);
						
						returnObj.setAdmissionstatus(babyDetailList.get(0).getAdmissionstatus());
				}
				// baby has not been discharged yet
				else {
					returnObj.setMessage("This baby is not on dashboard");
				}
			}
			// if baby does not exist
			else {
				returnObj.setMessage("This uhid does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhidToDischarge, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;

	}

	@Override
	public ResponseMessageObject dischargePatient(String uhidToDischarge, String branchName) {
		
		ResponseMessageObject returnObj = new ResponseMessageObject();
		
		try {
			
			String query = "select obj from BabyDetail as obj where uhid = '" + uhidToDischarge + "' and branchname = '" + branchName + "' and admissionstatus = 't'";
			List<BabyDetail> babyDetailsList = inicuDao.getListFromMappedObjQuery(query);
			
			if(!BasicUtils.isEmpty(babyDetailsList)) {
				
				String babyDetailId = babyDetailsList.get(0).getBabydetailid().toString();
				String nicuRoomNum = babyDetailsList.get(0).getNicuroomno();
				String nicuBedNum = babyDetailsList.get(0).getNicubedno();
				
				String updateQuery1 = "update baby_detail set admissionstatus = 'f' where babydetailid = '" + babyDetailId + "'";
				inicuDao.updateOrDeleteNativeQuery(updateQuery1);
				
				String updateQuery2 = "update ref_bed set status = 't' where bedid = '" + nicuBedNum + "' and branchname = '" + branchName + "' and roomid = '" + nicuRoomNum + "'";
				inicuDao.updateOrDeleteNativeQuery(updateQuery2);
				
				String deleteQuery = "update bed_device_detail set status = 'f' where uhid = '" + uhidToDischarge +"'";
				inicuDao.updateOrDeleteNativeQuery(deleteQuery);
				
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			}
			
		} catch(Exception e) {
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Failure in discharging baby");
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "",
					uhidToDischarge, "GET_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		// TODO Auto-generated method stub
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageObject mergeUhid(String dummyUhid, String originalUhid) {

		ResponseMessageObject returnObj = new ResponseMessageObject();
		BabyDetail hisBabyDetail = null;
		try {
			List<BabyDetail> hisBabyList = patientDao
					.getListFromMappedObjNativeQuery(HqlSqlQueryConstants.getBabyDetailList(originalUhid));
			if (!BasicUtils.isEmpty(hisBabyList)) {
				hisBabyDetail = hisBabyList.get(0);

				List<BabyDetail> inicuBabyList = patientDao
						.getListFromMappedObjNativeQuery(HqlSqlQueryConstants.getBabyDetailList(dummyUhid));
				if (!BasicUtils.isEmpty(inicuBabyList)) {
					removeBabyByUhid(originalUhid);

					String updateHISBabyBedQuery = "update ref_bed set status='true' where bedid='" + hisBabyDetail.getNicubedno() + "'";
					inicuDao.updateOrDeleteNativeQuery(updateHISBabyBedQuery);

					updateBabyHISToInicu(dummyUhid, hisBabyDetail);

					returnObj.setMessage("Merge Success.");
					returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				} else {
					returnObj.setMessage("Invalid Dummy UHID.");
					returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
				}
			} else {
				returnObj.setMessage("Invalid Original UHID.");
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			}
		} catch (Exception e) {

			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
			returnObj.setMessage("Failed to merge.");
			e.printStackTrace();
		}
		return returnObj;
	}

	private void removeBabyByUhid(String uhid) {
		String deleteUhidQuery = null;
		try {
			deleteUhidQuery = "delete from admission_form_tracker where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from admission_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from antenatal_history_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from antenatal_steroid_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from apnea_event where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from assessment_medication where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from baby_addinfo where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from en_feed_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from oralfeed_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from nursing_intake_output where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from blood_products where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from sa_feed_intolerance where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from baby_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from baby_prescription where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from baby_visit where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from babyfeed_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from birth_to_nicu where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from camera_feed where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from central_line where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from daily_progress_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from dashboard_nursing_vitalparameters where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from death_medication where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from declined_apnea_event where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_advice_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from discharge_birthdetail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from discharge_feeding where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_infection where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_investigation where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_metabolic where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_nicucourse where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_notes_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_outcome where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from discharge_summary where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from discharge_treatment where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from discharge_ventilation where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from dischargepatient_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from doctor_blood_products where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from doctor_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from doctor_tasks where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from et_intubation where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from et_suction where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from exceptionlist where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from gen_phy_exam where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from headtotoe_examination where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from high_morbidity_score where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from hm_observable_status where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from investigation_ordered where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from lumbar_puncture where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from maternal_pasthistory where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from medication_preparation where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from mother_current_pregnancy where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from nurse_baby_mapping where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nurse_tasks where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_blood_product where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_bloodgas where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_bloodproducts where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_bolus where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_catheters where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_dailyassesment where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_episode where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_heplock where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_intake where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from nursing_medication where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_misc where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_neurovitals where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_output where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_outputdrain where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_ventilaor where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursing_vitalparameters where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursingorder_apnea where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursingorder_assesment where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursingorder_jaundice where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursingorder_rds where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from nursingorder_rds_medicine where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from oralfeed_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from order_test where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from outborn_medicine where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from outcomes_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from peripheral_cannula where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from peritoneal_dialysis where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from procedure_chesttube where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from procedure_exchange_transfusion where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from procedure_other where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from pupil_reactivity where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from readmit_history where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from reason_admission where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from respsupport where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_acidosis where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cardiac where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns_asphyxia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns_encephalopathy where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns_hydrocephalus where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns_ivh where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns_neuromuscular_disorder where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_cns_seizures where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

			deleteUhidQuery = "delete from sa_feedgrowth where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_followup where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hypercalcemia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hyperglycemia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hyperkalemia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hypernatremia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hypocalcemia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hypoglycemia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hypokalemia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_hyponatremia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_iem where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_infection where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_infection_clabsi where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_infection_intrauterine where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_infection_nec where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_infection_sepsis where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_infection_vap where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_jaundice where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_metabolic where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_misc where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_miscellaneous where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_miscellaneous_2 where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_renalfailure where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_apnea where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_bpd where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_chesttube where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_others where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_pneumothorax where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_pphn where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_resp_rds where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_respsystem where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sa_sepsis where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from sample_detail where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_apgar where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_ballard where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_bellstage where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_bind where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_downes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_hie where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_ivh where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_levene where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_pain where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_papile where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_sarnat where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_sepsis where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_silverman where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from score_thompson where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from screen_hearing where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from screen_metabolic where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from screen_miscellaneous where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from screen_neurological where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from screen_rop where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from screen_usg where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from stable_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from systemic_exam where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from systemic_examination_notes where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from therapeutic_hypothermia where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from uhidnotification where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);
			deleteUhidQuery = "delete from vtap where uhid = '" + uhid + "'";
			inicuDao.updateOrDeleteNativeQuery(deleteUhidQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateBabyHISToInicu(String inicuUhid, BabyDetail hisBabyDetail) {

		String hisUhid = hisBabyDetail.getUhid();
		String hisEpisodeId = hisBabyDetail.getEpisodeid();
		String hisIpNumber = hisBabyDetail.getIpnumber();
		Date hisDateOfAdm = hisBabyDetail.getDateofadmission();
		String hisTimeOfAdm = hisBabyDetail.getTimeofadmission();

		String updateInicuBabyQuery = "";
		try {
			updateInicuBabyQuery = "update baby_detail set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' , ipnumber ='" + hisIpNumber
					+ "' , dateofadmission ='" + hisDateOfAdm + "' , timeofadmission ='" + hisTimeOfAdm + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_cns_asphyxia set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_cns_ivh set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_cns_seizures set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_hypoglycemia set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_jaundice set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_resp_apnea set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_resp_pneumothorax set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_resp_pphn set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_resp_rds set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update admission_notes set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update antenatal_history_detail set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update antenatal_steroid_detail set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update baby_visit set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_feed_intolerance set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_infection_nec set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_infection_sepsis set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_renalfailure set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update birth_to_nicu set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update discharge_outcome set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update gen_phy_exam set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update nursing_episode set uhid = '" + hisUhid + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update outborn_medicine set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update procedure_chesttube set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update reason_admission set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_cns_encephalopathy set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_cns_hydrocephalus set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_cns_neuromuscular_disorder set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_infection set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_infection_clabsi set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_infection_intrauterine set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_infection_vap set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_miscellaneous set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_miscellaneous_2 set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update sa_resp_others set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update screen_hearing set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update screen_metabolic set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update screen_neurological set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update screen_rop set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update screen_usg set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update stable_notes set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update systemic_exam set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update systemic_examination_notes set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
			updateInicuBabyQuery = "update babyfeed_detail set uhid = '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid='" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

			updateInicuBabyQuery = "update nursing_intake_output set uhid =  '" + hisUhid + "' , episodeid = '" + hisEpisodeId + "' where uhid = '" + inicuUhid + "'";
			inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update admission_form_tracker set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update apnea_event set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update assessment_medication set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update baby_addinfo set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update baby_prescription set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update blood_products set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update central_line set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update daily_progress_notes set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update dashboard_nursing_vitalparameters set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update death_medication set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update declined_apnea_event set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_advice_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_birthdetail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_feeding set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_infection set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_investigation set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_metabolic set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_nicucourse set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_notes_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            /*updateInicuBabyQuery = "update discharge_phototherapy set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);*/
            updateInicuBabyQuery = "update discharge_summary set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update discharge_treatment set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update discharge_ventilation set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update dischargepatient_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update doctor_blood_products set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update doctor_notes set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update doctor_tasks set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update en_feed_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update et_intubation set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update et_suction set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update exceptionlist set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update headtotoe_examination set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update high_morbidity_score set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update hm_observable_status set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update investigation_ordered set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update lumbar_puncture set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update maternal_pasthistory set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update medication_preparation set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update mother_current_pregnancy set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update nurse_baby_mapping set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update nurse_tasks set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_blood_product set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_bloodgas set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_bloodproducts set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_bolus set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_catheters set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_dailyassesment set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_heplock set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_intake set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_medication set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_misc set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_neurovitals set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_notes set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_output set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_outputdrain set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_ventilaor set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursing_vitalparameters set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursingorder_apnea set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursingorder_assesment set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursingorder_jaundice set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursingorder_rds set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update nursingorder_rds_medicine set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update oralfeed_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update order_test set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update outcomes_notes set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update peripheral_cannula set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update peritoneal_dialysis set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update procedure_exchange_transfusion set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update procedure_other set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update pupil_reactivity set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update readmit_history set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update respsupport set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_acidosis set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_cardiac set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_cns set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_feedgrowth set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_followup set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hypercalcemia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hyperglycemia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hyperkalemia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hypernatremia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hypocalcemia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hypokalemia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_hyponatremia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_iem set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_metabolic set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_misc set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_resp_bpd set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_resp_chesttube set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_respsystem set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sa_sepsis set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update sample_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_apgar set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_ballard set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_bellstage set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_bind set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_downes set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_hie set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_ivh set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_levene set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_pain set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_papile set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_sarnat set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_sepsis set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_silverman set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update score_thompson set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update screen_miscellaneous set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update therapeutic_hypothermia set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update uhidnotification set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update vtap set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update bed_device_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update device_bloodgas_detail_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update device_cerebral_oximeter_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);
            updateInicuBabyQuery = "update device_monitor_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update device_ventilator_detail set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

            updateInicuBabyQuery = "update hl7_device_mapping set uhid = '" + hisUhid + "' where uhid = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);


            updateInicuBabyQuery = "update test_result set prn = '" + hisUhid + "' where prn = '" + inicuUhid + "'";
            inicuDao.updateOrDeleteNativeQuery(updateInicuBabyQuery);

        } catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageObject configureTempUhid(String state, String branchName) {

		ResponseMessageObject returnObj = new ResponseMessageObject();
		if (!BasicUtils.isEmpty(state)) {
			try {
				String updateTempUHIDConfig = "update ref_hospitalbranchname set tempuhidtoggle='" + state
						+ "' where branchname='" + branchName + "'";
				inicuDao.updateOrDeleteNativeQuery(updateTempUHIDConfig);

				if ("Yes".equals(state)) {
					returnObj.setData("Yes");
				} else {
					returnObj.setData("No");
				}
				returnObj.setMessage("Successfully configured.");
				returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
			} catch (Exception e) {
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setMessage("Failed to configure.");
				e.printStackTrace();
			}
		} else {
			returnObj.setMessage("Failed to configure.");
			returnObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return returnObj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public MergeUhidDetailPojo mergeDetailsForUHID(String dummyUhid, String originalUhid) {

		MergeUhidDetailPojo mergeUhidDetailPojo = new MergeUhidDetailPojo();
		BabyDetail hisBabyDetail = null;
		BabyDetail inicuBabyDetail = null;
		try {
			List<BabyDetail> hisBabyList = patientDao
					.getListFromMappedObjNativeQuery(HqlSqlQueryConstants.getBabyDetailList(originalUhid));
			if (!BasicUtils.isEmpty(hisBabyList)) {
				hisBabyDetail = hisBabyList.get(0);

				List<BabyDetail> inicuBabyList = patientDao
						.getListFromMappedObjNativeQuery(HqlSqlQueryConstants.getBabyDetailList(dummyUhid));
				if (!BasicUtils.isEmpty(inicuBabyList)) {
					inicuBabyDetail = inicuBabyList.get(0);

					Date hisDateOfAdmission = hisBabyDetail.getDateofadmission();
					String hisTimeOfAdmission = hisBabyDetail.getTimeofadmission();

					String hisAdmDateTime = hisDateOfAdmission + " ";
					if (!BasicUtils.isEmpty(hisTimeOfAdmission)) {
						hisTimeOfAdmission = hisTimeOfAdmission.replaceFirst(",", ":");
						hisTimeOfAdmission = hisTimeOfAdmission.replace(",", " ");
						hisAdmDateTime += hisTimeOfAdmission;
					}
					mergeUhidDetailPojo.setHisDateOfAdmission(hisAdmDateTime);

					Date hisDateOfBirth = hisBabyDetail.getDateofbirth();
					String hisTimeOfBirth = hisBabyDetail.getTimeofbirth();

					String hisBirthDateTime = hisDateOfBirth + " ";
					if (!BasicUtils.isEmpty(hisTimeOfBirth)) {
						hisTimeOfBirth = hisTimeOfBirth.replaceFirst(",", ":");
						hisTimeOfBirth = hisTimeOfBirth.replace(",", " ");
						hisBirthDateTime += hisTimeOfBirth;
					}
					mergeUhidDetailPojo.setHisDateOfBirth(hisBirthDateTime);

					if (!BasicUtils.isEmpty(hisBabyDetail.getGender())) {
						mergeUhidDetailPojo.setHisGender(hisBabyDetail.getGender());
					}

					if (!BasicUtils.isEmpty(hisBabyDetail.getBabyname())) {
						mergeUhidDetailPojo.setHisPateinetName(hisBabyDetail.getBabyname());
					}

					if (!BasicUtils.isEmpty(hisBabyDetail.getAdmissionWeight())) {
						mergeUhidDetailPojo.setHisAdmissionWeight(String.valueOf(hisBabyDetail.getAdmissionWeight()));
					}

					if (!BasicUtils.isEmpty(hisBabyDetail.getBabyType())) {
						String babyType = hisBabyDetail.getBabyType();
						if (!BasicUtils.isEmpty(hisBabyDetail.getBabyNumber())) {
							babyType += " " + hisBabyDetail.getBabyNumber();
						}
						mergeUhidDetailPojo.setHisBabyType(babyType);
					}

					Date inicuDateOfAdmission = inicuBabyDetail.getDateofadmission();
					String inicuTimeOfAdmission = inicuBabyDetail.getTimeofadmission();

					String inicuAdmDateTime = inicuDateOfAdmission + " ";
					if (!BasicUtils.isEmpty(inicuTimeOfAdmission)) {
						inicuTimeOfAdmission = inicuTimeOfAdmission.replaceFirst(",", ":");
						inicuTimeOfAdmission = inicuTimeOfAdmission.replace(",", " ");
						inicuAdmDateTime += inicuTimeOfAdmission;
					}
					mergeUhidDetailPojo.setInicuDateOfAdmission(inicuAdmDateTime);

					Date inicuDateOfBirth = inicuBabyDetail.getDateofbirth();
					String inicuTimeOfBirth = inicuBabyDetail.getTimeofbirth();

					String inicuBirthDateTime = inicuDateOfBirth + " ";
					if (!BasicUtils.isEmpty(inicuTimeOfBirth)) {
						inicuTimeOfBirth = inicuTimeOfBirth.replaceFirst(",", ":");
						inicuTimeOfBirth = inicuTimeOfBirth.replace(",", " ");
						inicuBirthDateTime += inicuTimeOfBirth;
					}
					mergeUhidDetailPojo.setInicuDateOfBirth(inicuBirthDateTime);

					if (!BasicUtils.isEmpty(inicuBabyDetail.getGender())) {
						mergeUhidDetailPojo.setInicuGender(inicuBabyDetail.getGender());
					}

					if (!BasicUtils.isEmpty(inicuBabyDetail.getBabyname())) {
						mergeUhidDetailPojo.setInicuPateinetName(inicuBabyDetail.getBabyname());
					}

					if (!BasicUtils.isEmpty(inicuBabyDetail.getAdmissionWeight())) {
						mergeUhidDetailPojo.setInicuAdmissionWeight(String.valueOf(inicuBabyDetail.getAdmissionWeight()));
					}

					if (!BasicUtils.isEmpty(inicuBabyDetail.getBabyType())) {
						String babyType = inicuBabyDetail.getBabyType();
						if (!BasicUtils.isEmpty(inicuBabyDetail.getBabyNumber())) {
							babyType += " " + inicuBabyDetail.getBabyNumber();
						}
						mergeUhidDetailPojo.setInicuBabyType(babyType);
					}

					ParentDetail hisParentDetail = null;
					String patientDetailQuery = "select obj from ParentDetail obj where uhid ='" + originalUhid + "'";
					List<ParentDetail> patientDetailList = settingDao.getListFromMappedObjNativeQuery(patientDetailQuery);
					if (!BasicUtils.isEmpty(patientDetailList)) {
						hisParentDetail = patientDetailList.get(0);

						if (!BasicUtils.isEmpty(hisParentDetail)) {
							if (!BasicUtils.isEmpty(hisParentDetail.getMothername())) {
								mergeUhidDetailPojo.setHisMotherName(hisParentDetail.getMothername());
							}
							if (!BasicUtils.isEmpty(hisParentDetail.getFathername())) {
								mergeUhidDetailPojo.setHisFatherName(hisParentDetail.getFathername());
							}
						}
					}

					ParentDetail inicuParentDetail = null;
					patientDetailQuery = "select obj from ParentDetail obj where uhid ='" + dummyUhid + "'";
					List<ParentDetail> inicuParentDetailList = settingDao.getListFromMappedObjNativeQuery(patientDetailQuery);
					if (!BasicUtils.isEmpty(inicuParentDetailList)) {
						inicuParentDetail = inicuParentDetailList.get(0);
						if (!BasicUtils.isEmpty(inicuParentDetail)) {
							if (!BasicUtils.isEmpty(inicuParentDetail.getMothername())) {
								mergeUhidDetailPojo.setInicuMotherName(inicuParentDetail.getMothername());
							}
							if (!BasicUtils.isEmpty(inicuParentDetail.getFathername())) {
								mergeUhidDetailPojo.setInicuFatherName(inicuParentDetail.getFathername());
							}
						}
					}

					mergeUhidDetailPojo.setMessage("success");
					mergeUhidDetailPojo.setType(BasicConstants.MESSAGE_SUCCESS);
				} else {
					mergeUhidDetailPojo.setMessage("Invalid Dummy UHID.");
					mergeUhidDetailPojo.setType(BasicConstants.MESSAGE_FAILURE);
				}
			} else {
				mergeUhidDetailPojo.setMessage("Invalid Original UHID.");
				mergeUhidDetailPojo.setType(BasicConstants.MESSAGE_FAILURE);
			}
		} catch (Exception e) {

			mergeUhidDetailPojo.setType(BasicConstants.MESSAGE_FAILURE);
			mergeUhidDetailPojo.setMessage("Failed to fetch merge details, some exceptions occured. Try agian later.");
			e.printStackTrace();
		}
		return mergeUhidDetailPojo;
	}

	@Override
	public void sendCustomEmail(String subject,String mailContent, String branchName,String mailType) {

		String emailListQuery = HqlSqlQueryConstants.getNotificationEmailList() + " where isactive ='true' and branchname = '" + branchName + "'";

		if(mailType.equalsIgnoreCase(BasicConstants.DEVICE_EXCEPTION)){
			emailListQuery = HqlSqlQueryConstants.getNotificationEmailList() + " where isactive ='true'  and device_exception = 'true' and branchname = '" + branchName + "'";
		}

		List<NotificationEmail> emailList = inicuDao.getListFromMappedObjQuery(emailListQuery);

		if (!BasicUtils.isEmpty(emailList)) {
			try {
				HashMap<Message.RecipientType, List<String>> emailMap = new HashMap<Message.RecipientType, List<String>>() {
					private static final long serialVersionUID = 1L;
					{
						put(Message.RecipientType.TO, new ArrayList<String>());
						put(Message.RecipientType.CC, new ArrayList<String>());
					}
				};

				for (NotificationEmail item : emailList) {
					if (item.getEmail_type()) {
						emailMap.get(Message.RecipientType.TO).add(item.getUser_email());
					} else {
						emailMap.get(Message.RecipientType.CC).add(item.getUser_email());
					}
				}
				String fullHospitalName = BasicConstants.COMPANY_ID + " ( " + branchName + " )";
				BasicUtils.sendMailWithMultipleType(emailMap, mailContent, subject, fullHospitalName);
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
						"Sending scheduled Usage Sheet Mail", BasicUtils.convertErrorStacktoString(e));
			}
		}
	 }


}
