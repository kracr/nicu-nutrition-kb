package com.inicu.postgres.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.mail.Message.RecipientType;
import javax.persistence.Basic;

import com.inicu.models.*;
import com.inicu.postgres.daoImpl.SystematciDAOImpl;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.entities.Module;
import com.inicu.postgres.service.NotesService;
import com.inicu.postgres.utility.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.cassandra.utility.MappingConstants;
import com.inicu.hl7.data.acquisition.HL7Constants;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.PasswordService;
import com.inicu.postgres.service.UserPanelService;

import ca.uhn.hl7v2.hoh.util.repackage.Base64;
import ch.qos.logback.core.net.SyslogOutputStream;

@Repository
public class UserPanelServiceImpl implements UserPanelService {

	@Autowired
	UserServiceDAO userServiceDao;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	PasswordService passwordService;

	@Autowired
	LogsService logService;

//	@Autowired
//	NotesService notesService;

	@Autowired
	InicuPostgresUtililty inicuPostgresUtility;

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;

	ObjectMapper mapper = new ObjectMapper();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

	@Override
	public boolean validateUser(String doctorId) {
		return userServiceDao.validateUser(doctorId);
	}

	@Override
	public UserListJson getUserList(String loggedUser, String branchName) throws InicuDatabaseExeption {

		UserListJson userListJson = new UserListJson();
		// get list of reporting doctors
		String description = "Senior Doctor";
		String roleIdQuery = "Select userId From Role " + "WHERE description='" + description + "'";

		List<String> senDocId = userServiceDao.executeQuery(roleIdQuery);
		if (!BasicUtils.isEmpty(senDocId)) {
			String senDocQuery = "Select userName from UserRoles " + "WHERE roleId='" + senDocId.get(0).trim() + "' "
					+ " and branchname = '" + branchName + "'";
			List<String> listOfSenDoc = userServiceDao.executeQuery(senDocQuery);
			if (!BasicUtils.isEmpty(listOfSenDoc)) {
				userListJson.setReportingDoctor(listOfSenDoc);
			}
		}

		// get list of branch names
		String  getListofHospitals="Select branchname,hospital_type from ref_hospitalbranchname";
		List<RefHospitalbranchname> branchNameWithTypeList = userServiceDao.executeNativeQuery(getListofHospitals);
		if (!BasicUtils.isEmpty(branchNameWithTypeList)) {
			userListJson.setBranchNameWithTypeList(branchNameWithTypeList);
		}

		List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());
		if (!BasicUtils.isEmpty(branchNameList)) {
			userListJson.setBranchNameList(branchNameList);
		}

		String  getHospTypeQuery="select obj from RefHospitalbranchname as obj where hospitalType = 'Command Center'";
		List<RefHospitalbranchname> TypeList = userServiceDao.getListFromMappedObjNativeQuery(getHospTypeQuery);

		String queryRoles1 = "Select roleName from Role where roleName!='Command Center' and roleName!='Admin'";
		List<String> rolesList1 = inicuDao.getListFromMappedObjQuery(queryRoles1);
		userListJson.setRoleList(rolesList1);

		if(TypeList!=null) {
			for(RefHospitalbranchname obj: TypeList) {
				String branch = obj.getBranchname();
				if(branchName.equals(branch)) {
					String queryRoles = "Select roleName from Role where roleName!='Admin'";
					List<String> rolesList = inicuDao.getListFromMappedObjQuery(queryRoles);
					userListJson.setRoleList(rolesList);
					break;
				}
			}
		}


		List<UserListPojo> userListPojo = new ArrayList<>();
		List<UserInfoView> userList = userServiceDao.getUserList(loggedUser, branchName);
		if (!BasicUtils.isEmpty(userList)) {
			for (UserInfoView u : userList) {
				UserListPojo user = new UserListPojo();
				String name = u.getName();
				if (!BasicUtils.isEmpty(name)) {
					user.setName(name);
				}
				String role = u.getRoleName();
				if (!BasicUtils.isEmpty(role)) {
					user.setRole(role);
				}
				String userName = u.getUserName();
				if (!BasicUtils.isEmpty(userName)) {
					user.setUserName(userName);
				}
				String email = u.getEmail();
				if (!BasicUtils.isEmpty(email)) {
					user.setEmail(email);
				}
				BigInteger phoneNumber = u.getMobileNumber();
				if (!BasicUtils.isEmpty(phoneNumber)) {
					user.setPhoneNumber(phoneNumber.toString());
				}

				String password = u.getPassword();
				if (!BasicUtils.isEmpty(password)) {
					String decPass = passwordService.decryptPassword(password);
					if (!BasicUtils.isEmpty(decPass)) {
						user.setPassword(decPass);
					} else {
						user.setPassword(password);
					}
				}
				int status = u.getActive();
				if (!BasicUtils.isEmpty(status)) {
					if (status == 1) {
						user.setStatus(true);
					} else {

						user.setStatus(false);
					}
				}
				String repDoctor = u.getRepDoctor();
				if (!BasicUtils.isEmpty(repDoctor)) {
					user.setRepDoctor(repDoctor);
				}

				String profileName = u.getProfilePic();
				if (!BasicUtils.isEmpty(profileName)) {
					user.setProfilePic(profileName);
				}

				String signName = u.getSignature();
				if (!BasicUtils.isEmpty(signName)) {
					user.setSignature(signName);
				}

				// branchName
				String branchNameStr = u.getBranchname();
				if (!BasicUtils.isEmpty(branchNameStr)) {
					user.setBranchName(branchNameStr);
				}
				userListPojo.add(user);
			}
		}
		userListJson.setUserList(userListPojo);
		return userListJson;
	}

	@Override
	public boolean addDoctor(Doctor doc) {
		return userServiceDao.addDoctor(doc);
	}

	@Override
	public GetRoleObj getRoles() {
		return userServiceDao.getRoles();
	}
	@Override
	public ResponseMessageObject addUser(UserListPojo addUser) throws InicuDatabaseExeption {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);
		boolean success = false;
		// for adding a user successfully 3 tables need to be altered
		// inicuuser, users_usergroups, users_roles.

		// check if file exists or not.
		File imageDir = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH);
		if (!imageDir.exists()) {
			System.out.println("creating image directory:");
			try {
				imageDir.mkdir();

			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
						addUser.getLoggeduser(), "", "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
			}
		}

		String userName = addUser.getUserName();
		String branchName = addUser.getBranchName();
        String imageBranchName = branchName.replace(" ", "");
		if (!BasicUtils.isEmpty(userName)) {
			// store image
			String profileName = null, signName = null;
			try {
				Base64 decoder = new Base64();
				FileOutputStream osf;
				String profValue = addUser.getProfilePic();
				System.out.println(profValue);
				if (!BasicUtils.isEmpty(profValue)) {
					// write images onto the path

					String value = profValue.split("base64,")[1];
					byte[] imgBytes = decoder.decode(value);
					// String encodedStr =
					// java.util.Base64.getEncoder().encodeToString(imgBytes);
					// System.out.println("encoded str :"+encodedStr);
					osf = new FileOutputStream(
							new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageBranchName + "_"  + userName + "_profile.png"));
					osf.write(imgBytes);
					// osf = new FileOutputStream(new
					// File(BasicConstants.WORKING_DIR+BasicConstants.IMG_WEBAPP_PATH+userName+"_profile.png"));
					// osf.write(imgBytes);
					osf.flush();
					profileName = userName + "_profile.png";
				}

				String signValue = addUser.getSignature();
				if (!BasicUtils.isEmpty(signValue)) {
					String value = signValue.split("base64,")[1];
					byte[] signBytes = decoder.decode(value);
					osf = new FileOutputStream(
							new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageBranchName + "_" + userName + "_sign.png"));
					osf.write(signBytes);
					// osf = new FileOutputStream(new
					// File(BasicConstants.WORKING_DIR+BasicConstants.IMG_WEBAPP_PATH+userName+"_sign.png"));
					// osf.write(signBytes);
					osf.flush();
					signName = userName + "_sign.png";
				}

			} catch (Exception e) {
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				rObj.setMessage(e.getMessage());
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
						addUser.getLoggeduser(), "", "GET_IMAGE", BasicUtils.convertErrorStacktoString(e));
			}

			User user = null;
			String sql = "SELECT v FROM User v where upper(username)='" + userName.toUpperCase() + "' and branchname ='" + branchName + "'";
			List<User> userUniqueList = inicuDao.getListFromMappedObjQuery(sql);

			// user = inicuPostgresUtililty.getEntityManager().find(User.class, branchName);
			if (!BasicUtils.isEmpty(userUniqueList)) {
				user = userUniqueList.get(0);
			}
			if (addUser.isEdit()) {
				if (user == null) {
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
					rObj.setMessage("User does not exist to edit");
					return rObj;
				}
			} else {
				if (user != null) {
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
					rObj.setMessage("User already exist");
					return rObj;
				}
			}
			if (user == null) {
				user = new User();
				user.setUserName(userName);
				String maxQuery = "select max(id)+1 from inicuuser";
				List maxId = userServiceDao.executeNativeQuery(maxQuery);
				if (!BasicUtils.isEmpty(maxId)) {
					String id = maxId.get(0).toString();
					if (!BasicUtils.isEmpty(id)) {
						try {
							user.setId(new BigInteger(id));
						} catch (Exception e) {
							rObj.setType(BasicConstants.MESSAGE_FAILURE);
							rObj.setMessage("User ID could not be saved");
							e.printStackTrace();
							String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
							databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
									addUser.getLoggeduser(), "", "FAIL_OBJECT",
									BasicUtils.convertErrorStacktoString(e));
						}
					}
				}
				rObj.setMessage("New User added successfully");
			} else {
				rObj.setMessage("User is updated successfully");
			}
			// set user's properties
			boolean status = addUser.isStatus();
			if (!status) {
				// set inactive date
				user.setActive(0);
				Calendar cal = Calendar.getInstance();
				Date date = new Date(cal.getTimeInMillis());
				user.setInactiveDate(date);
			} else {
				user.setActive(1);
			}
			user.setCompanyId(BasicConstants.SCHEMA_NAME);
			String email = addUser.getEmail();
			if (!BasicUtils.isEmpty(email)) {
				user.setEmail(email);
			}

			String name = addUser.getName();
			if (!BasicUtils.isEmpty(name)) {
				String[] nameSplit = name.split(" ");
				if (nameSplit.length > 1) {
					user.setFirstName(nameSplit[0]);
					user.setLastName(nameSplit[1]);
				} else {
					user.setFirstName(name);
				}
			}

			String number = addUser.getPhoneNumber();
			if (!BasicUtils.isEmpty(number)) {
				try {
					user.setMobileNumber(new BigInteger(number));
				} catch (Exception e) {
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
					rObj.setMessage("Mobile number is not valid");
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							addUser.getLoggeduser(), "", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
				}
			}

			String password = addUser.getPassword();
			if (!BasicUtils.isEmpty(password)) {
				String encPass = passwordService.encryptPassword(password);
				if (!BasicUtils.isEmpty(encPass)) {
					user.setPassword(encPass);
				} else
					user.setPassword(password);
			}

			String repDoctor = addUser.getRepDoctor();
			if (!BasicUtils.isEmpty(repDoctor)) {
				user.setRepDoctor(repDoctor);
			}

			if (!BasicUtils.isEmpty(profileName)) {
				user.setImgPath(profileName);
			}

			if (!BasicUtils.isEmpty(signName)) {
				user.setSignPath(signName);
			}
			if (addUser.getLoggeduser() != null) {
				user.setLoggedUser(addUser.getLoggeduser());

			}
			if (addUser.getBranchName() != null) {
				user.setBranchName(addUser.getBranchName());
			} else {
				rollbackUser(userName, branchName);
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				rObj.setMessage("Please select Branch Name.");
				return rObj;
			}
			success = userServiceDao.saveObject(user);
			if (!success) {
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				rObj.setMessage("Could not save user in user Table");
				return rObj;
			}

			// add user_usergroups
			UserUserGroupsTable userGroup = null;
			String sqlusergroups = "SELECT v FROM UserUserGroupsTable v where userid='" + userName
					+ "' and branchname ='" + branchName + "'";
			List<UserUserGroupsTable> userGroupsUniqueList = inicuDao.getListFromMappedObjQuery(sqlusergroups);
			if (!BasicUtils.isEmpty(userGroupsUniqueList)) {
				userGroup = userGroupsUniqueList.get(0);
			}

			// BigInteger userGroupId = userGroup.getId();
			if (userGroup == null) {
				userGroup = new UserUserGroupsTable();

				String maxQuery = "select max(id)+1 from users_usergroups";
				List maxId = userServiceDao.executeNativeQuery(maxQuery);
				if (maxId != null) {
					String id = maxId.get(0).toString();
					if (!BasicUtils.isEmpty(id)) {
						try {
							userGroup.setId(new BigInteger(id));
						} catch (Exception e) {
							rObj.setType(BasicConstants.MESSAGE_FAILURE);
							rObj.setMessage(e.getMessage());
							e.printStackTrace();
							String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
							databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
									addUser.getLoggeduser(), "", "FAIL_OBJECT",
									BasicUtils.convertErrorStacktoString(e));
						}
					}
				}
			}

			userGroup.setUserName(userName);
			userGroup.setUesrGroupId(userName);
			userGroup.setBranchname(branchName);
			success = userServiceDao.saveObject(userGroup);

			if (!success) {
				// rollback user query and return false
				String sqlUser = "SELECT v FROM User v where username='" + userName + "' and branchname ='" + branchName
						+ "'";
				List<User> userUnique = inicuDao.getListFromMappedObjQuery(sqlUser);

				if (userUniqueList != null) {
					User delUser = userUnique.get(0);
					inicuPostgresUtililty.getEntityManager().remove(delUser);
				}
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				rObj.setMessage("Could not save user group");
				return rObj;
			}

			// add users_roles
			UserRolesTable userRoles = new UserRolesTable();
			String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + userName
					+ "' and branchname = '" + branchName + "'";
			List result = userServiceDao.executeQuery(userObjQuery);
			if (result != null && result.size() > 0) {
				userRoles = (UserRolesTable) result.get(0);
				if (userRoles == null) {
					userRoles = new UserRolesTable();
				}
			}
			String roleName = addUser.getRole();
			if (!BasicUtils.isEmpty(roleName)) {
				String query = "SELECT role FROM Role as role WHERE roleName='" + roleName + "'";
				List resultSet = userServiceDao.executeQuery(query);
				if (!BasicUtils.isEmpty(resultSet)) {
					Role roleObj = (Role) resultSet.get(0);
					userRoles.setRoleId(roleObj.getUserId());
					userRoles.setUserName(userName);
					userRoles.setBranchname(branchName);
					success = userServiceDao.saveObject(userRoles);
					if (!success) {
						rollbackUser(userName, branchName);
						rObj.setType(BasicConstants.MESSAGE_FAILURE);
						rObj.setMessage("Role could not be added.");
						return rObj;
					}
				} else {
					rollbackUser(userName, branchName);
					rObj.setType(BasicConstants.MESSAGE_FAILURE);
					rObj.setMessage("Could not find ID for the Role Name entered.");
					return rObj;
				}

			} else {
				rollbackUser(userName, branchName);
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				rObj.setMessage("Please select a valid Role.");
				return rObj;
			}

			try {
				// add logs for all entries
				String desc = mapper.writeValueAsString(user);
				String action = BasicConstants.INSERT;
				if (addUser.isEdit()) {
					action = BasicConstants.UPDATE;
				}
				/**
				 * this condition to be removed once data is coming from front end.
				 */
				String loggeduser = null;
				if (!BasicUtils.isEmpty(addUser.getLoggeduser())) {
					loggeduser = addUser.getLoggeduser();
				} else {
					loggeduser = "1234"; // setting dummy user as of now needs
					// to be removed
				}
				String pageName = BasicConstants.ADD_USER;
				logService.saveLog(desc, action, loggeduser, null, pageName);

				desc = mapper.writeValueAsString(userGroup);
				logService.saveLog(desc, action, loggeduser, null, pageName);

				desc = mapper.writeValueAsString(userRoles);
				logService.saveLog(desc, action, loggeduser, null, pageName);

			} catch (Exception e) {
				rObj.setType(BasicConstants.MESSAGE_FAILURE);
				rObj.setMessage(e.getMessage());
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
						addUser.getLoggeduser(), "", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
			}
		}
		return rObj;
	}

	private void rollbackUser(String userName, String branchName) {
		// rollback user
		// rollback user_userGroups
		String sql = "SELECT v FROM User v where username='" + userName + "' and branchname ='" + branchName + "'";
		List<User> userUniqueList = inicuDao.getListFromMappedObjQuery(sql);

		if (!BasicUtils.isEmpty(userUniqueList)) {
			User delUser = userUniqueList.get(0);
			userServiceDao.removeObject(delUser);
		}
		String delQuery = "DELETE FROM UserUserGroupsTable WHERE userName='" + userName + "' and branchname = '"
				+ branchName + "'";
		userServiceDao.updateOrDelete(delQuery);
	}

	@Override
	public boolean setUserStatus(UserStatusJSON userStatus) {
		return userServiceDao.setStatus(userStatus);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, VwVitalDetail> getDashboardVitalDetail(String branchName) throws InicuDatabaseExeption {
		String sql = "SELECT v FROM VwVitalDetail v where branchname = '" + branchName + "'";
		List<VwVitalDetail> dataList = inicuDao.getListFromMappedObjQuery(sql);

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		if (offset != 0) {
			for (VwVitalDetail vitalObj : dataList) {
				if (!BasicUtils.isEmpty(vitalObj.getEntrydate())) {
					vitalObj.setEntrydate(new Timestamp(vitalObj.getEntrydate().getTime() - offset));
				}
			}
		}

		HashMap<String, VwVitalDetail> returnMap = new HashMap<String, VwVitalDetail>();
		if (!BasicUtils.isEmpty(dataList)) {
			Iterator<VwVitalDetail> itr = dataList.iterator();
			while (itr.hasNext()) {
				VwVitalDetail obj = itr.next();
				obj.setMonitorException(null);
				obj.setVentilatorException(null);
				String bedQuery = "SELECT b FROM BedDeviceDetail b where status = 'true' and time_to is null and uhid = '"
						+ obj.getUhid() + "'";
				List<BedDeviceDetail> bedDeviceList = inicuDao.getListFromMappedObjQuery(bedQuery);
				if (!BasicUtils.isEmpty(bedDeviceList)) {
					for (int i = 0; i < bedDeviceList.size(); i++) {
						String deviceId = bedDeviceList.get(i).getDeviceid();
						String deviceQuery = "SELECT d FROM DeviceDetail d where inicu_deviceid = '" + deviceId + "'";
						List<DeviceDetail> deviceList = inicuDao.getListFromMappedObjQuery(deviceQuery);
						if (!BasicUtils.isEmpty(deviceList)) {
							String deviceType = deviceList.get(0).getDevicetype();
							String deviceName = deviceList.get(0).getDeviceserialno();
							String exceptionsQuery = "SELECT e FROM DeviceException e where deviceserialno = '"
									+ deviceName + "' order by creationtime desc";
							List<DeviceException> exceptionList = inicuDao.getListFromMappedObjQuery(exceptionsQuery);
							Timestamp currentDate = new Timestamp((new java.util.Date().getTime()));
							currentDate = new Timestamp(currentDate.getTime() - (6 * 60 * 1000));
							if (!BasicUtils.isEmpty(exceptionList)) {

								String[] exceptionParts = exceptionList.get(0).getExceptionmessage().split("##");
								if (deviceType.equalsIgnoreCase("Monitor") && !BasicUtils.isEmpty(exceptionParts)
										&& exceptionParts.length > 1) {
									if (!BasicUtils.isEmpty(obj.getStarttime()) && (obj.getStarttime()
											.getTime() < (exceptionList.get(0).getCreationtime().getTime()))) {
										obj.setMonitorException("Monitor : " + exceptionParts[0]);
									} else if (obj.getStarttime() == null) {
										obj.setMonitorException("Monitor : " + exceptionParts[0]);
									} else if (!BasicUtils.isEmpty(obj.getStarttime())
											&& (obj.getStarttime().getTime() < (currentDate.getTime()))) {
										obj.setMonitorException("Monitor : " + "TP Link is not working");
										
										if (!BasicUtils.isEmpty(obj.getMonitorlatesttime())
												&& (obj.getMonitorlatesttime().getTime() > (currentDate.getTime()))) {
											obj.setMonitorException("Monitor - " + "Previous data is getting updated");	
										}
									}
								} else if (deviceType.equalsIgnoreCase("Ventilator")
										&& !BasicUtils.isEmpty(exceptionParts) && exceptionParts.length > 1) {
									if (!BasicUtils.isEmpty(obj.getStart_time()) && (obj.getStart_time()
											.getTime() < (exceptionList.get(0).getCreationtime().getTime()))) {
										obj.setVentilatorException("Ventilator :" + exceptionParts[0]);
									} else if (obj.getStart_time() == null) {
										obj.setVentilatorException("Ventilator : " + exceptionParts[0]);
									} else if (!BasicUtils.isEmpty(obj.getStart_time())
											&& (obj.getStart_time().getTime() < (currentDate.getTime()))) {
										obj.setVentilatorException("Ventilator : " + "TP Link is not working");
										if (!BasicUtils.isEmpty(obj.getVentilatorlatesttime())
												&& (obj.getVentilatorlatesttime().getTime() > (currentDate.getTime()))) {
											obj.setVentilatorException("Ventilator - " + "Previous data is getting updated");	
										}
									}
								}
							} else {
								if (deviceType.equalsIgnoreCase("Monitor")) {
									if (!BasicUtils.isEmpty(obj.getStarttime())
											&& (obj.getStarttime().getTime() < (currentDate.getTime()))) {
										obj.setMonitorException("Monitor : " + "TP Link is not working");
										
										if (!BasicUtils.isEmpty(obj.getMonitorlatesttime())
												&& (obj.getMonitorlatesttime().getTime() > (currentDate.getTime()))) {
											obj.setMonitorException("Monitor - " + "Previous data is getting updated");	
										}
									} else if (obj.getStarttime() == null) {
										obj.setMonitorException("Monitor : " + "TP Link is not working");
									}
									
									
								} else if (deviceType.equalsIgnoreCase("Ventilator")) {
									if (!BasicUtils.isEmpty(obj.getStart_time())
											&& (obj.getStart_time().getTime() < (currentDate.getTime()))) {
										obj.setVentilatorException("Ventilator :" + "TP Link is not working");
										if (!BasicUtils.isEmpty(obj.getVentilatorlatesttime())
												&& (obj.getVentilatorlatesttime().getTime() > (currentDate.getTime()))) {
											obj.setVentilatorException("Ventilator - " + "Previous data is getting updated");	
										}
									} else if (obj.getStart_time() == null) {
										obj.setVentilatorException("Ventilator : " + "TP Link is not working");
									}
								}
							}
						}
					}
				}
				returnMap.put(obj.getUhid(), obj);
			}
		}
		return returnMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public VwVitalDetail getDashboardVitalDetailByUhid(String uhid) throws InicuDatabaseExeption {
		VwVitalDetail returnObj = new VwVitalDetail();
		String sql = "SELECT v FROM VwVitalDetail v where uhid='" + uhid + "'";
		List<VwVitalDetail> dataList = inicuDao.getListFromMappedObjQuery(sql);
		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		if (offset != 0) {
			for (VwVitalDetail vitalObj : dataList) {
				if (!BasicUtils.isEmpty(vitalObj.getEntrydate())) {
					vitalObj.setEntrydate(new Timestamp(vitalObj.getEntrydate().getTime() - offset));
				}
			}
		}
		if (!BasicUtils.isEmpty(dataList)) {
			returnObj = dataList.get(0);
		}
		return returnObj;
	}

	@Override
	public List<DashboardJSON> getDashboard(DashboardSearchFilterObj filterObj, String branchName)
			throws InicuDatabaseExeption {
		List<DashboardJSON> listOfDashJson = new ArrayList<>();
		List<Object[]> listOfBed = null;
		List<DashboardDeviceDetailView> listOfDashboard = userServiceDao.getDashboardList(filterObj, branchName);
		try {
			listOfBed = userServiceDao.executeNativeQuery("Select bedid,bedname,roomid FROM ref_bed where branchname='"+branchName+"' and isactive=true");
			System.out.println("List of Bed: " + listOfBed);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "defaultuser",
					"", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		if (!BasicUtils.isEmpty(listOfDashboard)) {
			listOfDashJson=getListOfDashboard(listOfDashboard,listOfBed);
//			for (DashboardDeviceDetailView d : listOfDashboard) {
//				DashboardJSON dash = getDashBoardObj(d, listOfBed);
//				System.out.println("Dashboard Json: " + dash);
//				listOfDashJson.add(dash);
//			}
		}
		return listOfDashJson;
	}

	@Override
	public List<DashboardJSON> getListOfDashboard(List<DashboardDeviceDetailView> listOfDashboard,List<Object[]> listOfBed) {
		List<DashboardJSON> listOfDashJson = new ArrayList<>();
		HashMap<String, DashboardJSON> listOfDashJsonMap = new HashMap<>();
		List<DashboardJSON> vacantBeds=new LinkedList<DashboardJSON>();

		String uhidList = "";
		// Cycle runs from 8AM to 8AM.
		Timestamp today = new Timestamp((new java.util.Date().getTime()));
		Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));

		today.setHours(8);
		today.setMinutes(0);
		today.setSeconds(0);

		yesterday.setHours(8);
		yesterday.setMinutes(0);
		yesterday.setSeconds(0);

		for (DashboardDeviceDetailView d : listOfDashboard) {

			DashboardJSON dash = new DashboardJSON();

			if (uhidList.isEmpty() && d.getUhid() != null) {
				uhidList += "'" + d.getUhid() + "'";
			} else if (!uhidList.isEmpty() && d.getUhid() != null) {
				uhidList += ", '" + d.getUhid() + "'";
			}

			dash.setAdmittime(d.getAdmittime());
			dash.setUhid(d.getUhid());

			if(!BasicUtils.isEmpty(d.getAudioEnabled())) {
				dash.setAudioEnabled(d.getAudioEnabled());
			}

			String comments = d.getComment();
			if (!BasicUtils.isEmpty(comments)) {
				dash.setComment(comments);
			}

			Boolean isAssessmentSubmit = d.getIsassessmentsubmit();
			if (!BasicUtils.isEmpty(isAssessmentSubmit)) {
				dash.setIsassessmentsubmit(isAssessmentSubmit);
			}

			Boolean isvacant = d.isAvailable();
			if (!BasicUtils.isEmpty(isvacant)) {
				dash.setIsvacant(isvacant);
			}

			String level = d.getPatientLevel();
			if (!BasicUtils.isEmpty(level)) {
				String[] arr = level.split("-");
				if (arr.length > 1) {
					dash.setLevel(arr[1]);
				}
			}

			Integer messages = d.getTotalMessage();
			if (!BasicUtils.isEmpty(messages)) {
				dash.setMessages(messages);
			}

			String name = d.getBabyName();
			if (!BasicUtils.isEmpty(name)) {
				dash.setName(name);
			}

			String birthWeight = d.getBirthweight();
			if (!BasicUtils.isEmpty(birthWeight)) {
				dash.setBirthWeight(birthWeight);
			}

			String gestation = d.getGestation();
			if (!BasicUtils.isEmpty(gestation)) {
				dash.setGestation(gestation);
			}

			String gender = d.getGender();
			if (!BasicUtils.isEmpty(gender)) {
				dash.setGender(gender);
			}

			String roomname = d.getRoomname();
			if (!BasicUtils.isEmpty(roomname)) {
				dash.setBabyRoom(roomname);
			}

			String episodId = d.getEpisodeid();
			if (!BasicUtils.isEmpty(episodId)) {
				dash.setEpisodeid(episodId);
			}

			String feedStr = "";
			if (!BasicUtils.isEmpty(d.getFeedDetail())) {
				feedStr = (d.getFeedDetail());
			}
			dash.setFeedDetail(feedStr);

			if (!BasicUtils.isEmpty(d.getDayoflife())) {
				dash.setDayOfLife(d.getDayoflife());
			}
			if (!BasicUtils.isEmpty(d.getWeightatadmission())) {
				dash.setWeightAtAdmission(d.getWeightatadmission());
			}
			if (!BasicUtils.isEmpty(d.getLastdayweight())) {
				dash.setLastDayWeight(d.getLastdayweight());
			}
			if (!BasicUtils.isEmpty(d.getCurrentdayweight())) {
				dash.setCurrentDayWeight(d.getCurrentdayweight());
			}
			if (!BasicUtils.isEmpty(d.getDifference())) {
				dash.setDifference(d.getDifference());
			}
			if (!BasicUtils.isEmpty(d.getSys_bp())) {
				dash.setSys_bp(d.getSys_bp());
			}

			if (!BasicUtils.isEmpty(d.getDia_bp())) {
				dash.setDia_bp(d.getDia_bp());
			}

			if (!BasicUtils.isEmpty(d.getMean_bp())) {
				dash.setMean_bp(d.getMean_bp());
			}

			if (!BasicUtils.isEmpty(d.getFio2())) {
				dash.setFio2(d.getFio2());
			}

			if (!BasicUtils.isEmpty(d.getPip())) {
				dash.setPip(d.getPip());
			}

			if (!BasicUtils.isEmpty(d.getPeep())) {
				dash.setPeep(d.getPeep());
			}

			String babyType = d.getBabyType();
			if (!BasicUtils.isEmpty(babyType)) {
				dash.setBabyType(babyType);
			}

			String babyNumber = d.getBabyNumber();
			if (!BasicUtils.isEmpty(babyNumber)) {
				dash.setBabyNumber(babyNumber);
			}

			String address = d.getAddress();
			if (!BasicUtils.isEmpty(address)) {
				address = address.replaceAll(", ,", ", ");
				address = address.replaceAll(",", "");
				dash.setAddress(address);
			}

			ImagePOJO babyImage = getImage("baby_" + d.getUhid() + "_pic.png");
			dash.setBabyImage(babyImage);

			java.util.Date dateBirth = d.getDateofbirth();
			if (!BasicUtils.isEmpty(dateBirth)) {
				try {
					dash.setDob(sdf.format(dateBirth));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// time of birth of baby
			String timeBirth = d.getTimeofbirth();
			if (!BasicUtils.isEmpty(timeBirth)) {
				try {
					dash.setTimeOfBirth(timeBirth);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				dash.setTimeOfBirth("00,00,AM");
			}


			java.util.Date date = d.getAdmitDate();
			if (!BasicUtils.isEmpty(date)) {
				try {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					java.util.Date dateUtil = cal.getTime();
					String dateStr = CalendarUtility.getDateformatutf(CalendarUtility.CLIENT_CRUD_OPERATION)
							.format(dateUtil);
					dash.setAdmitDate(dateStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (!BasicUtils.isEmpty(d.getUhid())) {
				String condition = getCriticality(d.getUhid(), d.getCondition());
				if (!BasicUtils.isEmpty(condition)) {
					dash.setCondition(condition);
				}
			}

			dash.setNursesName(null);

			String bedNo = d.getBedNo();
			String bedId = d.getBedId();

			if (!BasicUtils.isEmpty(bedNo)) {
				// set bed objects
				BedKeyValueObj bedObj = new BedKeyValueObj();
				try {
					String[] arr = bedNo.split("_");
					if (arr.length > 1) {
						bedObj.setBedNo(Integer.parseInt(arr[1]));
					}

					for (Object[] objarr : listOfBed) {
						if (objarr[0].toString().equalsIgnoreCase(bedId)) {
							bedObj.setKey(objarr[0].toString());
							bedObj.setValue(bedNo);
							// fetch roomid and value
							KeyValueObj room = new KeyValueObj();
							room.setKey(objarr[2].toString());
							// room.setValue("");

							// search room name from room id
							try {
								List resultSet = userServiceDao.executeNativeQuery(
										"Select roomname FROM ref_room WHERE roomid='" + objarr[2].toString().trim() + "'");
								if (!BasicUtils.isEmpty(resultSet)) {
									room.setValue(resultSet.get(0).toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							dash.setRoom(room);
							break;
						}
					}

					dash.setBed(bedObj);
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test", "",
							"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
				}
			}

			// set device details rounding off values.
			try {
				if (!BasicUtils.isEmpty(d.getHeartRate())) {
					String hrStr = d.getHeartRate();
					double hr = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
					dash.setHR(String.valueOf(hr));
				}

				if (!BasicUtils.isEmpty(d.getSp02())) {
					String spo2Str = d.getSp02();
					double spo2 = Math.round(Float.valueOf(spo2Str) * 10.0 / 10.0);
					dash.setSPO2(String.valueOf(spo2));
				}

				if (!BasicUtils.isEmpty(d.getCo2RespRate())) {
					String respRateStr = d.getCo2RespRate();
					double resprate = Math.round(Float.valueOf(respRateStr) * 10.0 / 10.0);
					dash.setRR(String.valueOf(resprate));
				}

				// setting RR(on dashboard) page to baby ecg rate
				if (!BasicUtils.isEmpty(d.getRespRate())) {
					String respRateStr = d.getRespRate();
					double resprate = Math.round(Float.valueOf(respRateStr) * 10.0 / 10.0);
					dash.setRR(String.valueOf(resprate));
				}

				if (!BasicUtils.isEmpty(d.getPulseRate())) {
					String pulseRateStr = d.getPulseRate();
					double pulseRate = Math.round(Float.valueOf(pulseRateStr) * 10.0 / 10.0);
					dash.setPR(String.valueOf(pulseRate));
				}

				if (!BasicUtils.isEmpty(d.getStarttime())) {
					dash.setDeviceTime(d.getStarttime().getTime());
				}

				if (!BasicUtils.isEmpty(d.getTemp())) {
					dash.setTemp(d.getTemp());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!BasicUtils.isEmpty(d.getFeedDetail())) {
				dash.setFeedDetail(d.getFeedDetail());
			}

			String enFeedStr = "";
			String uhid=dash.getUhid();

			if(uhid == null || uhid == "null"){
				System.out.println("Uhid is Null");
			}
			
			String queryParentDetail = "select obj from  ParentDetail obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<ParentDetail> parentDetail = inicuDao.getListFromMappedObjQuery(queryParentDetail);
			if (!BasicUtils.isEmpty(parentDetail) && !BasicUtils.isEmpty(parentDetail.get(0).getMother_phone())) {
				dash.setPhoneNumber(parentDetail.get(0).getMother_phone());
			}
			
			List<BabyfeedDetail> feedList = (List<BabyfeedDetail>) inicuDao.getListFromMappedObjRowLimitQuery(
					HqlSqlQueryConstants.getBabyfeedDetailList(uhid, yesterday, today), 1);
			if (!BasicUtils.isEmpty(feedList)) {
				BabyfeedDetail babyFeedObj = feedList.get(0);
				if (babyFeedObj.getIsenternalgiven() != null && babyFeedObj.getIsenternalgiven()) {

//					List<RefMasterfeedtype> refFeedTypeList = inicuDao
//							.getListFromMappedObjQuery("SELECT obj FROM RefMasterfeedtype as obj");
					//String primaryFeed = "";
					String feedMethodStr = "";
					String enfeedDetailsSql = "SELECT obj FROM EnFeedDetail as obj WHERE uhid='" + uhid
							+ "' and babyfeedid=" + babyFeedObj.getBabyfeedid() + " order by en_feed_detail_id asc";
					List<EnFeedDetail> enfeedDetailsList = inicuDao.getListFromMappedObjQuery(enfeedDetailsSql);

					if (!BasicUtils.isEmpty(enfeedDetailsList)) {
						for (int i = 0; i < enfeedDetailsList.size(); i++) {
							if (i == 0) {
								enFeedStr = enfeedDetailsList.get(i).getFeed_volume() + " (mL/feed) for "
										+ enfeedDetailsList.get(i).getNo_of_feed() + " feed";
							} else {
								enFeedStr += ", " + enfeedDetailsList.get(i).getFeed_volume() + " (mL/feed) for "
										+ enfeedDetailsList.get(i).getNo_of_feed() + " feed";
							}
						}
					}

					if (BasicUtils.isEmpty(babyFeedObj.getFeedduration())) {
						enFeedStr += " ad-lib";
					} else {
						enFeedStr += " every " + babyFeedObj.getFeedduration() + " hours";
					}

					if (!(babyFeedObj.getFeedmethod() == null || babyFeedObj.getFeedmethod().isEmpty())) {
						feedMethodStr = babyFeedObj.getFeedmethod().replace("[", "").replace("]", "").replace(", ",
								",");

						String refFeedMethodSql = "SELECT obj FROM RefMasterfeedmethod as obj";
						List<RefMasterfeedmethod> refFeedMethodList = inicuDao
								.getListFromMappedObjQuery(refFeedMethodSql);

						if (!BasicUtils.isEmpty(feedMethodStr)) {
							String[] feedMethodArr = feedMethodStr.split(",");

							for (int i = 0; i < feedMethodArr.length; i++) {
								feedMethodStr = "";
								Iterator<RefMasterfeedmethod> itr = refFeedMethodList.iterator();
								while (itr.hasNext()) {
									RefMasterfeedmethod obj = itr.next();
									if (feedMethodArr[i].trim().equalsIgnoreCase(obj.getFeedmethodid())) {
										if (enFeedStr.isEmpty()) {
											enFeedStr = obj.getFeedmethodname();
										} else {
											enFeedStr += "( " + obj.getFeedmethodname() + ")";
										}
										break;
									}
								}
							}
						}
					}
				}
			}
			dash.setEnteralStr(enFeedStr);

			//String parentalStr = "";
			String babyFeedSql = "SELECT obj FROM BabyfeedDetail as obj WHERE uhid ='" + uhid
					+ "' and entrydatetime >= '" + yesterday + "' and entrydatetime <= '" + today +"' order by entrydatetime desc";

			List<BabyfeedDetail> babyFeedList = (List<BabyfeedDetail>) inicuDao
					.getListFromMappedObjRowLimitQuery(babyFeedSql, 1);
			dash.setFeedList(babyFeedList);


			// To get the name of all antibiotics(TYPE0001) given by nurses in last 24  hours.

			String medicineListStr = "";
			String queryMedicationAll = "select obj from NursingMedication as obj where uhid = '" + uhid
					+ "' and given_time >= '" + yesterday + "' and given_time <= '" + today + "' ";

			List<NursingMedication> resultListRaw = inicuDao.getListFromMappedObjQuery(queryMedicationAll);
			if (!BasicUtils.isEmpty(resultListRaw)) {

				for (NursingMedication med : resultListRaw) {
					
					String unit = "mg/kg";
					String queryNursingMedicationAll = "select b.medicinename from " + BasicConstants.SCHEMA_NAME
							+ ".baby_prescription as b where b.uhid = '" + uhid + "' and baby_presid = '"
							+ med.getBaby_presid() + "'";
					List<String> resultListMedications = inicuDao.getListFromNativeQuery(queryNursingMedicationAll);
					
					String queryMedsUnit = "select b.dose_unit from " + BasicConstants.SCHEMA_NAME + ".baby_prescription as b where b.uhid = '" +
								uhid + "' and baby_presid = '" + med.getBaby_presid() + "'";
					List<String> unitMeds = inicuDao.getListFromNativeQuery(queryMedsUnit);
					if(!BasicUtils.isEmpty(unitMeds)) {
						 unit = unitMeds.get(0);
					}

					if (!BasicUtils.isEmpty(resultListMedications)) {
						String medicineName = resultListMedications.get(0);
						if (!BasicUtils.isEmpty(medicineName)) {
							if (medicineListStr.contains(medicineName)) {

							} else {
								if (!BasicUtils.isEmpty(medicineListStr)) {
									medicineListStr = medicineListStr + ", " + medicineName + ":"
											+ med.getDay_number();
								} else {
									medicineListStr = medicineName + ":" + med.getDay_number();
								}
							}
						}
						if(med.getGiven_dose()!=0.0) {
							medicineListStr += " Dose given : " + med.getGiven_dose() + " " + unit;
						}
						dash.setAntibioticsName(medicineListStr);
						
					}

				}
			}

			//getting medicine days of other medicines given to baby
			String queryIntakeOutputAllAdditives = "Select obj from NursingIntakeOutput obj where uhid='" + uhid
					+ "' and entry_timestamp >= '" + yesterday + "' and entry_timestamp <= '" + today
					+ "' order by entry_timestamp desc";

			List<NursingIntakeOutput> additivesIntakeOutputList = inicuDao.getListFromMappedObjQuery(queryIntakeOutputAllAdditives);
			if(!BasicUtils.isEmpty(additivesIntakeOutputList)) {
				//for (NursingIntakeOutput obj : additivesIntakeOutputList) {
				NursingIntakeOutput obj = additivesIntakeOutputList.get(0);
				if (!BasicUtils.isEmpty(obj.getCalciumVolume()))
					medicineListStr+= "Calcium: " + obj.getMedicineDay() + ", ";

				if (!BasicUtils.isEmpty(obj.getIronVolume()))
					medicineListStr+= "Iron: " + obj.getMedicineDay() + ", ";

				if (!BasicUtils.isEmpty(obj.getMvVolume()))
					medicineListStr+= "Multivitamin: " + obj.getMedicineDay() + ", ";

				if (!BasicUtils.isEmpty(obj.getMctVolume()))
					medicineListStr+= "MCT Oil: " + obj.getMedicineDay() + ", ";

				if (!BasicUtils.isEmpty(obj.getVitamindVolume()))
					medicineListStr+= "Vitamin D: " + obj.getMedicineDay();

			}
			dash.setAntibioticsName(medicineListStr);

			if(!BasicUtils.isEmpty(d.getDiagnosis())) {

				String queryRop = "select obj from ScreenRop as obj where uhid = '" + uhid + "'";
				List<ScreenRop> ropLists = inicuDao.getListFromMappedObjQuery(queryRop);
				boolean leftRop = false;
				boolean rightRop = false;
				String ropDiagnosis = "";
				if(!BasicUtils.isEmpty(ropLists)) {
					for(ScreenRop ropObject: ropLists) {
						if(ropObject.getIs_rop()!=null && ropObject.getIs_rop()==true) {
							ropDiagnosis = "ROP";
							if(ropObject.getIs_rop_left()!=null && ropObject.getIs_rop_left() == true) {
								leftRop = true;
							}
							if(ropObject.getIs_rop_right()!=null && ropObject.getIs_rop_right() == true) {
								rightRop = true;
							}
						}
					}
					
						
					if(leftRop == true && rightRop == false) {
						ropDiagnosis = "ROP Left eye";
						d.setDiagnosis(d.getDiagnosis() + "/ " + ropDiagnosis);
					}
					else if(rightRop == true && leftRop == false) {
						ropDiagnosis = "/ " + "ROP Right eye";
						d.setDiagnosis(d.getDiagnosis() + "/ " + ropDiagnosis);
					}
					else if(rightRop == true && leftRop == true) {
						ropDiagnosis = "/ " + "ROP Both eyes";
						d.setDiagnosis(d.getDiagnosis() + "/ " + ropDiagnosis);
					}
					else {
						d.setDiagnosis(d.getDiagnosis() + "/ " + ropDiagnosis);
					}
					
					
				}
				dash.setDiagnosis(d.getDiagnosis());
			}

			if(BasicUtils.isEmpty(d.getUhid())) {
				System.out.println("Not Object Found");
				vacantBeds.add(dash);
			}

			listOfDashJsonMap.put(d.getUhid(), dash);
			System.out.println("Entered");
		}

		if(!BasicUtils.isEmpty(uhidList)) {
			// To display the last ventilator entered by nurses
			String queryVentilatorToFindLast = HqlSqlQueryConstants.getLastNursingVentMode(uhidList, yesterday, today);
			List<Object[]> ventilatorList = inicuPostgresUtililty.executePsqlDirectQuery(queryVentilatorToFindLast);

			String queryVentilatorMode = HqlSqlQueryConstants.getAllVentilatorMode();
			List<RefVentilationmode> ventilatorModesList = inicuDao
					.getListFromMappedObjQuery(queryVentilatorMode);

			HashMap<String, String> ventModes = new HashMap<>();
			for (RefVentilationmode ventObj : ventilatorModesList) {
				if (ventObj.getVentmodeid() != null) {
					ventModes.put(ventObj.getVentmodeid(), ventObj.getVentilationmode());
				}
			}

			if (!BasicUtils.isEmpty(ventilatorList)) {
				for (Object[] ventObject : ventilatorList) {
					String tempUhid = ventObject[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);

					if (currentObject != null && ventObject[1] != null) {
						String ventilationMode = ventModes.get(ventObject[1].toString());
						currentObject.setVentMode(ventilationMode);
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}
				}
			}
		}

		if(!BasicUtils.isEmpty(uhidList)) {

			String queryMiscDisease = "select obj from  SaMiscellaneous obj where uhid in (" + uhidList
					+ ") order by creationtime desc";
			List<SaMiscellaneous> listMiscDisease = inicuDao.getListFromMappedObjQuery(queryMiscDisease);


			String queryMisc2Disease = "select obj from  SaMiscellaneous2 obj where uhid in (" + uhidList
					+ ") order by creationtime desc";
			List<SaMiscellaneous2> listMisc2Disease = inicuDao.getListFromMappedObjQuery(queryMisc2Disease);


			if (!BasicUtils.isEmpty(listMiscDisease)) {

				HashMap<String ,String> miscDiag=new HashMap<>();

				for (SaMiscellaneous miscObj : listMiscDisease) {

					String uhid=miscObj.getUhid();
					String miscDiseaseDiagnosis;
					if(miscDiag.get(uhid)!=null) {
						miscDiseaseDiagnosis = miscDiag.get(uhid);
					}else{
						miscDiseaseDiagnosis="";
					}

					if (!BasicUtils.isEmpty(miscObj.getDisease())) {
							miscDiseaseDiagnosis += miscObj.getDisease() + "/";
					} else {
							miscDiseaseDiagnosis += "Miscellaneous" + "/";
					}
					miscDiag.put(uhid,miscDiseaseDiagnosis);
				}

				for (Map.Entry<String, String> entry : miscDiag.entrySet()) {
					String myUhid=entry.getKey();

					if (!BasicUtils.isEmpty(myUhid) && !BasicUtils.isEmpty(entry.getValue())) {
						DashboardJSON currentObject = listOfDashJsonMap.get(myUhid);
						String diagnosisStr=currentObject.getDiagnosis();
						if (BasicUtils.isEmpty(diagnosisStr)) {
							diagnosisStr = entry.getValue();
						} else {
							diagnosisStr += "/" + entry.getValue();
						}

						currentObject.setDiagnosis(diagnosisStr);
						listOfDashJsonMap.replace(myUhid, currentObject);
					}
				}
			}
		}

		List<NursingEpisode> nursingEpisodeList=null;
		List<NursingIntakeOutput> intakeOutputList=null;
		List<Object[]> listRenal=null;
		List<VwIcdCause> pastIcdcause=null;

		if(!BasicUtils.isEmpty(uhidList)) {
			// Set the Box Name
			String queryDeviceConnect = HqlSqlQueryConstants.getDeviceDetailName(uhidList);
			List<Object[]> deviceBoxName = inicuDao.getListFromNativeQuery(queryDeviceConnect);
			if (!BasicUtils.isEmpty(deviceBoxName)) {
				for (Object[] obj : deviceBoxName) {
					String tempuhid = obj[1].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempuhid);
					String currentBox = currentObject.getBoxName();
					String boxName = "";
					String tinyBoxName = "";
					if (!BasicUtils.isEmpty(obj[0])) {
						boxName = obj[0].toString();
					}

					if (!BasicUtils.isEmpty(obj[2])) {
						tinyBoxName = obj[2].toString();
					}

					if (!BasicUtils.isEmpty(boxName)) {
						if (currentBox == "" || currentBox == null) {
							currentBox = boxName;
							currentObject.setIsDeviceConnected(true);
						} else {
							if (!currentBox.contains(boxName)) {
								currentBox = currentBox + "," + boxName;
								currentObject.setIsDeviceConnected(true);
							}
						}
					} else if (!BasicUtils.isEmpty(tinyBoxName)) {
						if (currentBox == "" || currentBox == null) {
							currentBox = tinyBoxName;
							currentObject.setIsDeviceConnected(true);
							currentObject.setTinyDeviceConnected(true);
						} else {
							if (!currentBox.contains(tinyBoxName)) {
								currentBox = currentBox + "," + tinyBoxName;
								currentObject.setIsDeviceConnected(true);
								currentObject.setTinyDeviceConnected(true);
							}
						}
					}
					currentObject.setBoxName(currentBox);

					listOfDashJsonMap.replace(tempuhid, currentObject);
				}
			}

			// Get Parent Gynaelogist
			String queryParentConnect = HqlSqlQueryConstants.queryParentConnect(uhidList);
			List<Object[]> parentDetailList = inicuDao.getListFromNativeQuery(queryParentConnect);
			if (!BasicUtils.isEmpty(parentDetailList)) {
				for (Object[] parentObject : parentDetailList) {
					String tempUhid = parentObject[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && parentObject[1] != null) {
						currentObject.setGynaecologist(parentObject[1].toString());
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}

				}
			}

			// Get Baby Doctor Name
			String queryBabyConnect = HqlSqlQueryConstants.queryBabyConnect(uhidList);
			List<Object[]> babyDetailList = inicuDao.getListFromNativeQuery(queryBabyConnect);
			if (!BasicUtils.isEmpty(babyDetailList)) {
				for (Object[] babyObject : babyDetailList) {
					String tempUhid = babyObject[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && babyObject[1] != null) {
						currentObject.setNeonatologist(babyObject[1].toString());
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}

				}
			}

			// Current Doctor Notes
			String queryDoctorNote = HqlSqlQueryConstants.getDoctoreNoteEntry(uhidList);
			List<Object[]> currentDoctorNoteList = inicuDao.getListFromNativeQuery(queryDoctorNote);
			if (!BasicUtils.isEmpty(currentDoctorNoteList)) {
				for (Object[] DoctorNote : currentDoctorNoteList) {
					String tempUhid = DoctorNote[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && DoctorNote[1] != null) {
						currentObject.setNotes(DoctorNote[1].toString());
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}
				}
			}

			// ROP Alert only by doctors(Planned)
			String queryRop = HqlSqlQueryConstants.getROPList(uhidList);
			List<Object[]> screenRopList = inicuDao.getListFromNativeQuery(queryRop);
			if (!BasicUtils.isEmpty(screenRopList)) {
				for (Object[] ScreenROP : screenRopList) {
					String tempUhid = ScreenROP[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && ScreenROP[1] != null) {
						Date tempDate = (Date) ScreenROP[1];
						String ropDate = "ROP : " + (tempDate.getYear() + 1900) + "-"
								+ (tempDate.getMonth() + 1) + "-" + tempDate.getDate()
								+ " (Planned)";
						currentObject.setRopAlert(ropDate);
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}
				}
			}

			float workingWeight = 0;
			String queryChildMaxWeight = HqlSqlQueryConstants.getChildMaxWeightList(uhidList);
			try {
				List<Object[]> resultSet = inicuDao.getListFromNativeQuery(queryChildMaxWeight);
				if (!BasicUtils.isEmpty(resultSet)) {
					for (Object[] maxWeightObject : resultSet) {
						String tempUhid = maxWeightObject[0].toString();
						DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
						if (currentObject != null && maxWeightObject[1] != null) {
							Float maxWeight = Float.parseFloat(maxWeightObject[1].toString());
							currentObject.setCurrentdateweight(maxWeight);
							currentObject.setWorkingWeight(maxWeight);
							listOfDashJsonMap.replace(tempUhid, currentObject);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test", "",
						"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(ex));
			}

			String queryCurrentBabyVisit = HqlSqlQueryConstants.getCurrentBabyVisit(uhidList);
			List<Object[]> currentBabyVisitList = inicuDao.getListFromNativeQuery(queryCurrentBabyVisit);
			if (!BasicUtils.isEmpty(currentBabyVisitList)) {
				for (Object[] currentDateWeight : currentBabyVisitList) {
					String tempUhid = currentDateWeight[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && currentDateWeight[1] != null) {
						Float maxWeight = Float.parseFloat(currentDateWeight[1].toString());
						currentObject.setCurrentdateweight(maxWeight);
						currentObject.setWorkingWeight(maxWeight);
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}
				}
			}

			try {
				String query = HqlSqlQueryConstants.getBedDeviceHistory(uhidList);
				List<Object[]> resultSet = inicuDao.getListFromNativeQuery(query);
				if (!BasicUtils.isEmpty(resultSet)) {
					for (Object[] deviceTypeObject : resultSet) {
						String tempUhid = deviceTypeObject[0].toString();
						DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
						if (!currentObject.isIsvacant()) {
							if (currentObject != null && deviceTypeObject[1] != null) {
								if (deviceTypeObject[1].toString().equalsIgnoreCase("monitor")) {
									currentObject.setEcg(true);
								} else if (deviceTypeObject[1].toString().equalsIgnoreCase("ventilator")) {
									currentObject.setOxygen(true);
								}
								listOfDashJsonMap.replace(tempUhid, currentObject);
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test", "",
						"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(ex));
			}

			// Setting the TCB Value
			String queryForTcb = HqlSqlQueryConstants.getTcbValue(uhidList, yesterday, today);
			List<Object[]> nursingVitalParametersList = inicuPostgresUtililty.executePsqlDirectQuery(queryForTcb);
			if (!BasicUtils.isEmpty(nursingVitalParametersList)) {
				for (Object[] TcbObject : nursingVitalParametersList) {
					String tempUhid = TcbObject[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && TcbObject[1] != null) {
						currentObject.setTcb(TcbObject[1].toString());
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}
				}
			}

			// Setting the RBS Value
			String queryForRBS = HqlSqlQueryConstants.getRbsValue(uhidList, yesterday, today);
			List<Object[]> nursingVitalRBSParametersList = inicuPostgresUtililty.executePsqlDirectQuery(queryForRBS);
			if (!BasicUtils.isEmpty(nursingVitalRBSParametersList)) {
				for (Object[] RBSObject : nursingVitalRBSParametersList) {
					String tempUhid = RBSObject[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(tempUhid);
					if (currentObject != null && RBSObject[1] != null) {
						currentObject.setRbs(RBSObject[1].toString());
						listOfDashJsonMap.replace(tempUhid, currentObject);
					}
				}
			}

			// To get the names of all nurses in the last 24 hours
			String queryNursingNotesAll = HqlSqlQueryConstants.getNursingNote(uhidList, yesterday, today);
			List<NursingNote> notesList = inicuDao.getListFromMappedObjQuery(queryNursingNotesAll);
			if (!BasicUtils.isEmpty(notesList)) {
				for (NursingNote note : notesList) {
					String uhid = note.getUhid();
					String loggedUser = note.getLoggeduser();
					DashboardJSON currentObject = listOfDashJsonMap.get(uhid);
					if (currentObject != null && loggedUser != null) {
						String loggedUserString = currentObject.getNursesName();
						if (!BasicUtils.isEmpty(loggedUserString)) {
							loggedUserString = loggedUserString + ", " + loggedUser;
						} else {
							loggedUserString = loggedUser;
						}
						currentObject.setNursesName(loggedUserString);
						listOfDashJsonMap.replace(uhid, currentObject);
					}
				}
			}


			// Setting the Abdomen girth
			String queryAdbgirth = HqlSqlQueryConstants.getLastVitalParametersList(uhidList, yesterday, today);
			List<Object[]> AdbList = inicuPostgresUtililty.executePsqlDirectQuery(queryAdbgirth);

			if (!BasicUtils.isEmpty(AdbList)) {
				for (Object[] AbdomeG : AdbList) {
					String uhid = AbdomeG[0].toString();
					DashboardJSON currentObject = listOfDashJsonMap.get(uhid);
					if (currentObject != null && AbdomeG[1] != null) {
						currentObject.setAbdGrith(AbdomeG[1].toString());
						listOfDashJsonMap.replace(uhid, currentObject);
					}
				}
			}

			// blood gas info
			try {
				String queryRespSupportToFindLastActive = HqlSqlQueryConstants.getLastRespSupport(uhidList, yesterday, today);

				List<Object[]> respSupportActiveList = inicuPostgresUtililty
						.executePsqlDirectQuery(queryRespSupportToFindLastActive);
				// get all after latest start...s
				if (!BasicUtils.isEmpty(respSupportActiveList)) {
					for (Object[] Resp : respSupportActiveList) {
						String uhid = Resp[0].toString();
						DashboardJSON currentObject = listOfDashJsonMap.get(uhid);
						if (currentObject != null) {
							if (!BasicUtils.isEmpty(Resp[1])) {
								String hrStr = Resp[1].toString();
								double hco3 = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
								currentObject.setHco3(String.valueOf(hco3));
							}
							if (!BasicUtils.isEmpty(Resp[2])) {
								String be = Resp[2].toString();
								double Be = Math.round(Float.valueOf(be) * 10.0) / 10.0;
								currentObject.setBe(String.valueOf(Be));
							}
							if (!BasicUtils.isEmpty(Resp[3])) {
								String co2 = Resp[3].toString();
								double Co2 = Math.round(Float.valueOf(co2) * 10.0) / 10.0;
								currentObject.setCo2(String.valueOf(Co2));
							}
							if (!BasicUtils.isEmpty(Resp[4])) {
								String ph = Resp[4].toString();
								double PH = Math.round(Float.valueOf(ph) * 10.0) / 10.0;
								currentObject.setPh(String.valueOf(PH));
							}
							if (!BasicUtils.isEmpty(Resp[5])) {
								String rs_pco2 = Resp[5].toString();
								double Rs_Pco2 = Math.round(Float.valueOf(rs_pco2) * 10.0) / 10.0;
								currentObject.setPco2(String.valueOf(Rs_Pco2));
							}
							listOfDashJsonMap.replace(uhid, currentObject);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Query to get the parameters related to apena and seisure duration
			String queryNursingEpisode = "select obj from NursingEpisode as obj where uhid in (" + uhidList
					+ ") and creationtime >= '" + yesterday + "' and creationtime <= '" + today
					+ "' order by creationtime desc";

			nursingEpisodeList= userServiceDao
					.getListFromMappedObjNativeQuery(queryNursingEpisode);

			// To get the primary feed, secondary feed and urine total in last 24 hours
			String queryIntakeOutputAll = "Select obj from NursingIntakeOutput obj where uhid in (" + uhidList
					+ ") and entry_timestamp >= '" + yesterday + "' and entry_timestamp <= '" + today
					+ "' order by entry_timestamp desc";
			intakeOutputList = inicuDao.getListFromMappedObjQuery(queryIntakeOutputAll);

			//get the Admission Notes -> Lastest entry in the table
			String queryRenalfailure = HqlSqlQueryConstants.getLastRenalFailure(uhidList, yesterday, today);
			listRenal = inicuPostgresUtililty.executePsqlDirectQuery(queryRenalfailure);

			pastIcdcause = new ArrayList<>();

			String queryicdCauseString = "Select obj from VwIcdCause as obj where uhid in (" + uhidList
					+ ") and icdcause is not null order by creationtime desc, id desc, event desc";

			pastIcdcause = inicuDao.getListFromMappedObjQuery(queryicdCauseString);
		}

		for (Map.Entry<String, DashboardJSON> entry : listOfDashJsonMap.entrySet()) {

			DashboardJSON object = entry.getValue();
			if (object.getUhid() != null) {

				int tempApneaDuration = 0;
				int tempSeizuresDuration = 0;

				if (!BasicUtils.isEmpty(nursingEpisodeList)) {
					for (NursingEpisode obj : nursingEpisodeList) {

						if(object.getUhid().equalsIgnoreCase(obj.getUhid())) {
							if (!BasicUtils.isEmpty(obj.getApnea())) {
								if (obj.getApnea() == true || obj.getApnea()) {
									tempApneaDuration = tempApneaDuration + 1;
								}
							}
							if (!BasicUtils.isEmpty(obj.getSeizures())) {
								if (obj.getSeizures() == true || obj.getSeizures()) {
									tempSeizuresDuration = tempSeizuresDuration + 1;
								}
							}
						}
					}
				}

				String otherInfo = "";
				if (object.getRbs() != "" && object.getRbs()!=null)
					otherInfo += "RBS-" + object.getRbs() + "mg/dL,";
				if (object.getTcb() != "" && object.getTcb()!=null)
					otherInfo += "TCB-" + object.getTcb() + " mg/dL,";
				if (tempApneaDuration != 0)
					otherInfo += tempApneaDuration + " Apneas,";
				if (tempSeizuresDuration != 0)
					otherInfo += tempSeizuresDuration + " Seizures,";
				if (object.getAbdGrith() != "" && object.getAbdGrith()!=null)
					otherInfo += "Adb G-" + object.getAbdGrith() + "cm";

				object.setOtherInfo(otherInfo);

				float enTotal = 0;
				float urineTotal = 0;

				double fluid_balance = 0;
				int stoolPassedCount = 0;
				double intakeValue = 0;
				double output = 0;

				// EN Value
				float primaryFeedValue = 0;
				float formulaValue = 0;
				float totalEnteralValue = 0;
				// PN Value
				float readymadeTotalFeed = 0;
				float tpn_delivered = 0;
				float lipid_delivered = 0;
				float bolus_delivered_feed = 0;
				float totalParenteralValue = 0;
				String feedText = "";

				if (!BasicUtils.isEmpty(intakeOutputList)) {

					for (NursingIntakeOutput obj : intakeOutputList) {

						if (object.getUhid().equalsIgnoreCase(obj.getUhid())) {

							if (!BasicUtils.isEmpty(obj.getPrimaryFeedValue()))
								primaryFeedValue = obj.getPrimaryFeedValue();

							if (!BasicUtils.isEmpty(obj.getFormulaValue()))
								formulaValue = obj.getFormulaValue();

							if (!BasicUtils.isEmpty(obj.getReadymadeDeliveredFeed()))
								readymadeTotalFeed = obj.getReadymadeDeliveredFeed();

							if (!BasicUtils.isEmpty(obj.getTpn_delivered()))
								tpn_delivered = obj.getTpn_delivered();

							if (!BasicUtils.isEmpty(obj.getLipid_delivered()))
								lipid_delivered = obj.getLipid_delivered();

							if (!BasicUtils.isEmpty(obj.getBolusDeliveredFeed()))
								bolus_delivered_feed = obj.getBolusDeliveredFeed();

							if (primaryFeedValue != 0)
								totalEnteralValue += primaryFeedValue;

							if (formulaValue != 0)
								totalEnteralValue += formulaValue;

							if (readymadeTotalFeed != 0)
								totalParenteralValue += readymadeTotalFeed;

							if (tpn_delivered != 0)
								totalParenteralValue += tpn_delivered;
							if (lipid_delivered != 0)
								totalParenteralValue += lipid_delivered;

							if (bolus_delivered_feed != 0)
								totalParenteralValue += bolus_delivered_feed;

							if (!BasicUtils.isEmpty(obj.getPrimaryFeedValue()))
								enTotal += obj.getPrimaryFeedValue();
							if (!BasicUtils.isEmpty(obj.getFormulaValue()))
								enTotal += obj.getFormulaValue();
							if (!BasicUtils.isEmpty(obj.getUrine()))
								urineTotal += Float.parseFloat(obj.getUrine());

							if (!BasicUtils.isEmpty(obj.getStoolPassed())) {
								if (obj.getStoolPassed() == true || obj.getStoolPassed()) {
									stoolPassedCount = stoolPassedCount + 1;
								}
							}
						}
					}

					intakeValue = totalEnteralValue + totalParenteralValue;

					if (!BasicUtils.isEmpty(enTotal)) {
						feedText = "Total feed " + enTotal + " mL. ";
					}

					if (!BasicUtils.isEmpty(urineTotal)) {
						double weight=0;
						if(object.getCurrentdateweight()!=null){
							weight= object.getCurrentdateweight() / 1000;
							double urineMlKgHr = urineTotal / (weight * 24);
							output = Math.round(urineMlKgHr * 100.0) / 100.0;
						}

						if (!BasicUtils.isEmpty(feedText)) {
							feedText = feedText + "Total urine " + urineTotal + " mL. ";
						} else {
							feedText = "Total urine" + urineTotal + " mL. ";
						}
					}

					feedText = object.getFeedDetail() + feedText;
				}

				String htmlNextLine = System.getProperty("line.separator");
				String intakeOutput = "";

				if(object.getCurrentdateweight()!=null) {
					double weight = object.getCurrentdateweight() / 1000;
					double intake = intakeValue / (weight * 24);
					if (intakeValue != 0) {
						intakeOutput += "Intake-" + (new DecimalFormat("##.##").format(((Math.round(intake * 100.0) / 100.0)) * 24)) + "ml/kg/day" + htmlNextLine;
					}
					if (output != 0 && intakeValue != 0) {
						String outputString = "Output-" + output + "ml/kg/hr";
						intakeOutput += outputString;
					}
					if (output != 0 && intakeValue == 0) {
						String outputString = "Output-" + output + " ml/kg/hr";
						intakeOutput += outputString;
					}
					if (output != 0 && intakeValue != 0)
						fluid_balance = Math.round((intake - output) * 100.0) / 100.0;
					else
						fluid_balance = 0.0;
				}

				object.setIntakeOutput(intakeOutput);
				object.setFeedDetail(feedText);
				object.setStoolCount(stoolPassedCount);
				object.setFluidBalance(fluid_balance);

				// Handling the Diagnosis
				//Renalfailure handling

				String renalDiagnosis = "";
				if (!BasicUtils.isEmpty(listRenal)) {
					for (Object[] renalObject:listRenal) {

						if (!BasicUtils.isEmpty(renalObject[0]) && !BasicUtils.isEmpty(renalObject[1]) && !BasicUtils.isEmpty(renalObject[2])) {
							String uhid = renalObject[0].toString();
							if (object.getUhid().equalsIgnoreCase(uhid)) {
								if (!BasicUtils.isEmpty(renalObject[1].toString())) {
									if (!BasicUtils.isEmpty(renalObject[2].toString())) {
										renalDiagnosis += "/" + "Renal failure with " + renalObject[2].toString();
										//					if(diagnosisStr.contains("Renal"))
										//						diagnosisStr = diagnosisStr.replaceAll("Renal", renalDiagnosis);
									}
								}
							}
						}
					}
				}

//				String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='" + object.getUhid()
//						+ "' order by creationtime desc";
//				List<AdmissionNotes> listAdmissionNotes = userServiceDao.getListFromMappedObjNativeQuery(queryAdmissionNotes);
//				String adminssionNotesDiagnosis = "";
//				if (!BasicUtils.isEmpty(listAdmissionNotes)) {
//					AdmissionNotes admNotes = listAdmissionNotes.get(0);
//
//					if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
//						adminssionNotesDiagnosis = admNotes.getDiagnosis();
//					}
//				}
//
//				if (!BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
//					adminssionNotesDiagnosis +=  "/" +object.getDiagnosis();
//					object.setDiagnosis(adminssionNotesDiagnosis);
//				}

				if(!BasicUtils.isEmpty(object.getDiagnosis())) {
					int flag = 0;
					String diagnosisStr = "";

					// diagnosis note
					diagnosisStr = object.getDiagnosis().replaceAll(",", "/");

					String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='" + object.getUhid()
							+ "' order by creationtime desc";
					List<AdmissionNotes> listAdmissionNotes = inicuDao.getListFromMappedObjQuery(queryAdmissionNotes);
					String adminssionNotesDiagnosis = "";
					if (!BasicUtils.isEmpty(listAdmissionNotes)) {
						AdmissionNotes admNotes = listAdmissionNotes.get(0);
						if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
							adminssionNotesDiagnosis = admNotes.getDiagnosis();
							adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace("/Female", "");
							adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace("/Male", "");
							adminssionNotesDiagnosis = adminssionNotesDiagnosis.replace("/Unknown", "");
						}
					}

					if (adminssionNotesDiagnosis.contains("Sepsis")) {
						adminssionNotesDiagnosis = getSepsisType(adminssionNotesDiagnosis, object.getUhid());
						flag = 1;
					}

					if (diagnosisStr.contains("Sepsis")) {
						if (flag == 0) {
							diagnosisStr = getSepsisType(diagnosisStr, object.getUhid());
						} else if (flag == 1) {

							// replace the Sepsis with empty string
							diagnosisStr = diagnosisStr.replace("Sepsis", "");
						}
					}

					diagnosisStr = adminssionNotesDiagnosis.replace(",", "/") + "/" + diagnosisStr;

					object.setDiagnosis(diagnosisStr);
				}

				// handle unique in diagnosis...
				String finalDiagnosis = object.getDiagnosis();
				String finalDiagnosisUnique = "";
				if (!BasicUtils.isEmpty(finalDiagnosis)) {
					String[] finalDiagArr = finalDiagnosis.split("/");
					for (String diag : finalDiagArr) {
						if (finalDiagnosisUnique.isEmpty()) {
							finalDiagnosisUnique = diag.replace(" ", "");
						} else {
							if (!finalDiagnosisUnique.contains(diag.replace(" ", " "))) {
								finalDiagnosisUnique += "/ " + diag.replace(" ", " ");
							}
						}
					}
					finalDiagnosisUnique = finalDiagnosisUnique.replace("Jaundice", "NNH");
					if (finalDiagnosisUnique.contains("Renal"))
						finalDiagnosisUnique = finalDiagnosisUnique.replaceAll("Renal", renalDiagnosis);
				}
				finalDiagnosisUnique = finalDiagnosisUnique.replace(",", "/");

				// get the ICD Cause
				String icdCode = "";
				if (!BasicUtils.isEmpty(pastIcdcause)) {

					for (VwIcdCause obj : pastIcdcause) {

						if(object.getUhid().equalsIgnoreCase(obj.getUhid())) {
							if (BasicUtils.isEmpty(icdCode)) {
								icdCode += obj.getIcdcause().replaceAll(",", "/").replace("[", "").replace("]", "");
							} else {
								icdCode += "/" + obj.getIcdcause().replaceAll(",", "/").replace("[", "").replace("]", "");
							}
						}
					}

					String[] icdCodeArr = icdCode.split("/");
					icdCode = "";
					for (String icdCodeStr : icdCodeArr) {
						if (icdCode.isEmpty()) {
							icdCode = icdCodeStr;
						} else if (!icdCode.contains(icdCodeStr.trim())) {
							icdCode += "/ " + icdCodeStr.trim();
						}
					}
				}

				if (!BasicUtils.isEmpty(icdCode)) {
					finalDiagnosisUnique += "\n";
					finalDiagnosisUnique += "ICD Code: " + icdCode;
				}

                if (!BasicUtils.isEmpty(finalDiagnosisUnique)) {
                    String[] daignosisArr = finalDiagnosisUnique.split("/");
                    finalDiagnosisUnique = "";
                    for (String daignosis : daignosisArr) {
                        if (finalDiagnosisUnique.isEmpty()) {
                            finalDiagnosisUnique = daignosis;
                        } else if (!finalDiagnosisUnique.contains(daignosis.trim())) {
                            finalDiagnosisUnique += "/ " + daignosis.trim();
                        }
                    }
                    object.setDiagnosis(finalDiagnosisUnique);
                }

				listOfDashJson.add(object);
			}
		}

		if(!BasicUtils.isEmpty(vacantBeds) && vacantBeds.size()>0){
			for(DashboardJSON obj:vacantBeds){
				listOfDashJson.add(obj);
			}
		}
		return listOfDashJson;
	}

	/*
	 * AnthroPrometeric progress
	 * if baby was admitted for <7 days - Weekly average growth rate = (Parameter value today - Parameter value on DOA)/(Today - DOA)
	 * if baby was admitted for >7 days - Weekly average growth rate =(Parameter value today - Parameter value 7 days back)/7
	 * If any of these values are not available then display Weekly average growth rate as NA
	 */

	@Override
	public AnthropometryProgressRate getAnthropometryGrowthRate(String uhid){

		AnthropometryProgressRate anthropometryProgressRate=new AnthropometryProgressRate();

		java.util.Date date = new java.util.Date();
		Timestamp originalTodayDate = new Timestamp(date.getTime());
		originalTodayDate.setHours(8);
		originalTodayDate.setMinutes(0);
		originalTodayDate.setSeconds(0);

		String queryCurrentBabyVisit = "select currentdateweight,currentdateheight,currentdateheadcircum from baby_visit where uhid='" + uhid
				+ "' and cast(concat(visitdate,' ',visittime) as timestamp)>='"+originalTodayDate+"' order by cast(concat(visitdate,' ',visittime) as timestamp) desc";

		List<Object[]> currentBabyVisitList = inicuDao.getListFromNativeQuery(queryCurrentBabyVisit);

		// get the baby detail Object
		String babyDetailQuery="SELECT obj FROM BabyDetail as obj where uhid='"+uhid+"' order by creationtime desc";
		List<BabyDetail> babyDetails=inicuDao.getListFromMappedObjQuery(babyDetailQuery);

		if(babyDetails!=null && babyDetails.size()>0 && currentBabyVisitList!=null && currentBabyVisitList.size()>0){

			// get the date and time of admission, length, circumference, weight
			try{
				BabyDetail babyDetail=babyDetails.get(0);

				float birthWeight=0;
				float birthoLength=0;
				float birthCircumference=0;

				if(babyDetail.getBirthweight()!=null) {
					birthWeight = babyDetail.getBirthweight();
				}
				if(babyDetail.getBirthlength()!=null) {
					birthoLength = babyDetail.getBirthlength();
				}
				if(babyDetail.getBirthheadcircumference()!=null){
					birthCircumference=babyDetail.getBirthheadcircumference();
				}

				java.util.Date dateOfBirthTemp = babyDetail.getDateofadmission();
				String timeOfBirth = babyDetail.getTimeofadmission();

				if(dateOfBirthTemp!=null) {
//					Timestamp dateOfBirth = new Timestamp(dateOfBirthTemp.getTime());

//					String[] tobStr = timeOfBirth.split(",");
//					if (tobStr[2] == "PM" && Integer.parseInt(tobStr[0]) != 12) {
//						dateOfBirth.setHours(12 + Integer.parseInt(tobStr[0]));
//					} else if (tobStr[2] == "AM" && Integer.parseInt(tobStr[0]) == 12) {
//						dateOfBirth.setHours(0);
//					} else {
//						dateOfBirth.setHours(Integer.parseInt(tobStr[0]));
//					}
//					dateOfBirth.setMinutes(Integer.parseInt(tobStr[1]));

//					long difference =  Math.abs(originalTodayDate.getTime()-dateOfBirthTemp.getTime());
//					double difference = ((originalTodayDate.getTime()-dateOfBirthTemp.getTime())/86400000)+1;
//					long daysDiff = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
//					int ageOfDays = (int) daysDiff;

					double difference = ((originalTodayDate.getTime()-dateOfBirthTemp.getTime())/86400000)+1;
					int ageOfDays = (int) Math.ceil(difference);

					Object[] currentVisit=currentBabyVisitList.get(0);

					float currentAnthroWeight = -1;
					float currentAnthroLength = -1;
					float currentAnthroCircumference = -1;

					if(currentVisit[0]!=null){
						currentAnthroWeight = Float.parseFloat(currentVisit[0].toString());
					}
					if(currentVisit[1]!=null){
						currentAnthroLength =Float.parseFloat(currentVisit[1].toString());
					}
					if(currentVisit[2]!=null){
						currentAnthroCircumference=Float.parseFloat(currentVisit[2].toString());
					}

					float finalWeight=-1;
					float finalLength=-1;
					float finalCircumference=-1;

					if(ageOfDays<=7){
						if(currentAnthroWeight!=-1) {
							finalWeight = (currentAnthroWeight - birthWeight) / ageOfDays;
						}
						if(currentAnthroLength!=-1) {
							finalLength = (currentAnthroLength - birthoLength) / ageOfDays;
						}
						if(currentAnthroCircumference!=-1) {
							finalCircumference = (currentAnthroCircumference - birthCircumference) / ageOfDays;
						}
					}else{
						java.util.Date pastSevenDaysDate = new Date(date.getTime()-24*60*60*1000*7);
						String queryBabyVisit = "select currentdateweight,currentdateheight,currentdateheadcircum,cast(concat(visitdate,' ',visittime) as timestamp) from baby_visit where uhid='" + uhid
								+ "' and visitdate <= '"+pastSevenDaysDate+"' order by cast(concat(visitdate,' ',visittime) as timestamp) desc";
						List<Object[]> babyVisitList = inicuDao.getListFromNativeQuery(queryBabyVisit);

						int countWeight=-1;
						int countLength=-1;
						int countCircumferenc=-1;

						float anthroWeight=0;
						float anthroLength=0;
						float anthroCircumference=0;

						boolean weightflag=false;
						boolean lengthflag=false;
						boolean circumferenceflag=false;

						for (Object[] visit : babyVisitList) {
							float weight = 0;
							float length = 0;
							float Circumference = 0;

							java.util.Date CurrentObjectDate=new Date(BasicUtils.toTimestampSafe(visit[3]).getTime());
							LocalDate d1 = LocalDate.parse(pastSevenDaysDate.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
							LocalDate d2 = LocalDate.parse(CurrentObjectDate.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
							Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
							long diffDays = diff.toDays();
							if(diffDays<0){
								diffDays=diffDays*(-1);
							}
							int FinalDays = (int) Math.ceil(diffDays);


							if(visit[0]!=null) {
								weight = Float.parseFloat(visit[0].toString());
							}
							if(visit[1]!=null){
								length=Float.parseFloat(visit[1].toString());
							}
							if(visit[2]!=null) {
								Circumference = Float.parseFloat(visit[2].toString());
							}

							if (weight != 0 && !weightflag) {
								countWeight=FinalDays;
								anthroWeight = weight;
								weightflag=true;
							}

							if (length != 0 && !lengthflag) {
								countLength=FinalDays;
								anthroLength=length;
								lengthflag=true;
							}

							if (Circumference != 0 && !circumferenceflag) {
								countCircumferenc=FinalDays;
								anthroCircumference=Circumference;
								circumferenceflag=true;
							}
						}

						if(currentAnthroWeight!=-1 && anthroWeight!=0) {
							int denominator=7+countWeight;
							finalWeight=(currentAnthroWeight-anthroWeight)/denominator;
							anthropometryProgressRate.setTempString("Denominator: 7+("+countWeight+")");
						}
						if(currentAnthroLength!=-1 && anthroLength!=0) {
							int denominator=7+countLength;
							finalLength=(currentAnthroLength-anthroLength)/denominator;
						}
						if(currentAnthroCircumference!=-1 && anthroCircumference!=0) {
							int denominator=7+countLength;
							finalCircumference=(currentAnthroCircumference-anthroCircumference)/denominator;
						}
					}

					if(finalWeight!=-1){
						anthropometryProgressRate.setWeightGrowthRate(BasicUtils.round(finalWeight,1));
					}

					if(finalCircumference!=-1){
						anthropometryProgressRate.setLengthGrowthRate(Math.round(finalCircumference));
					}

					if(finalLength!=-1){
						anthropometryProgressRate.setHeadCircumferenceRate(Math.round(finalLength));
					}
				}
			}catch (Exception e){
				System.out.println("Exception :" +e);
			}
		}
		return anthropometryProgressRate;
	}

	public String getSepsisType(String diagnosis,String uhid) {
		double antibioticUsage = 0;

		Timestamp currentDate = null;
		Timestamp currentDateNew = new Timestamp(new java.util.Date().getTime());
		String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicationtype='TYPE0001' order by startdate asc";
		List<BabyPrescription> medList = userServiceDao.getListFromMappedObjNativeQuery(medQuery);
		for(BabyPrescription obj : medList) {
			if(!BasicUtils.isEmpty(obj.getMedicineOrderDate())) {
				if(BasicUtils.isEmpty(obj.getEnddate())) {
					obj.setEnddate(currentDateNew);
				}
				if(currentDate == null || (!BasicUtils.isEmpty(obj.getStartdate()) && !BasicUtils.isEmpty(obj.getMedicineOrderDate()) && currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {
					currentDate = obj.getEnddate();
					antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / ( 60 * 60 * 1000));
				}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {
					antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000));
					currentDate = obj.getEnddate();
				}
			}
		}
		//returnObj.setAntibioticDaysSepsis(String.valueOf(antibioticUsage));

		if(antibioticUsage < 120 ) {
			diagnosis = diagnosis.replace("Sepsis", "Suspected Sepsis");
		}

		if(antibioticUsage >= 120) {
			diagnosis = diagnosis.replace("Sepsis", "Clinical Sepsis");
		}
		String query1 = "Select obj from SaSepsis as obj where uhid = '" + uhid + "' order by creationtime desc";
		List<SaSepsis> sepsisList = userServiceDao.getListFromMappedObjNativeQuery(query1);
		if(!BasicUtils.isEmpty(sepsisList) && sepsisList!=null) {
			if(sepsisList.get(0).getBloodCultureStatus()!=null && sepsisList.get(0).getBloodCultureStatus().equalsIgnoreCase("positive")) {
				diagnosis = diagnosis.replace("Clinical Sepsis", "Confirmed Sepsis");
				diagnosis = diagnosis.replace("Suspected Sepsis", "Confirmed Sepsis");
				diagnosis = diagnosis.replace("Sepsis", "Confirmed Sepsis");
				diagnosis = diagnosis.replace("Confirmed Confirmed","Confirmed" );
			}
		}

		return diagnosis;
	}

	@SuppressWarnings("unchecked")
	private DashboardJSON getDashBoardObj(DashboardDeviceDetailView d, List<Object[]> listOfBed) {
		DashboardJSON dash = new DashboardJSON();

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		float workingWeight = 0;

		java.util.Date date = d.getAdmitDate();
		if (!BasicUtils.isEmpty(date)) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				java.util.Date dateUtil = cal.getTime();
				String dateStr = CalendarUtility.getDateformatutf(CalendarUtility.CLIENT_CRUD_OPERATION)
						.format(dateUtil);
				dash.setAdmitDate(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		dash.setAdmittime(d.getAdmittime());

		String comments = d.getComment();
		if (!BasicUtils.isEmpty(comments)) {
			dash.setComment(comments);
		}

		Boolean isAssessmentSumbit = d.getIsassessmentsubmit();
		if (!BasicUtils.isEmpty(isAssessmentSumbit)) {
			dash.setIsassessmentsubmit(isAssessmentSumbit);
		}
		String bedNo = d.getBedNo();
		String bedId = d.getBedId();

		if (!BasicUtils.isEmpty(bedNo)) {
			// set bed objects
			BedKeyValueObj bedObj = new BedKeyValueObj();
			try {
				String[] arr = bedNo.split("_");
				if (arr.length > 1) {
					bedObj.setBedNo(Integer.parseInt(arr[1]));
				}

				for (Object[] objarr : listOfBed) {
					if (objarr[0].toString().equalsIgnoreCase(bedId)) {
						bedObj.setKey(objarr[0].toString());
						bedObj.setValue(bedNo);
						// fetch roomid and value
						KeyValueObj room = new KeyValueObj();
						room.setKey(objarr[2].toString());
						// room.setValue("");

						// search room name from room id
						try {
							List resultSet = userServiceDao.executeNativeQuery(
									"Select roomname FROM ref_room WHERE roomid='" + objarr[2].toString().trim() + "'");
							if (!BasicUtils.isEmpty(resultSet)) {
								room.setValue(resultSet.get(0).toString());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						dash.setRoom(room);
						break;
					}
				}

				dash.setBed(bedObj);
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test", "",
						"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
			}
		}

		if (!BasicUtils.isEmpty(d.getUhid())) {
			String condition = getCriticality(d.getUhid(), d.getCondition());
			if (!BasicUtils.isEmpty(condition)) {
				dash.setCondition(condition);
			}
		}

		Boolean isvacant = d.isAvailable();
		if (!BasicUtils.isEmpty(isvacant)) {
			dash.setIsvacant(isvacant);
		}

		String level = d.getPatientLevel();
		if (!BasicUtils.isEmpty(level)) {
			String[] arr = level.split("-");
			if (arr.length > 1) {
				dash.setLevel(arr[1]);
			}
		}

		Integer messages = d.getTotalMessage();
		if (!BasicUtils.isEmpty(messages)) {
			dash.setMessages(messages);
		}

		String uhid = d.getUhid();
		System.out.println("Received Uhid is :" + uhid);

		if (!BasicUtils.isEmpty(uhid)) {
			dash.setUhid(uhid);
			String queryParentConnect = "select obj from ParentDetail as obj where uhid='" + uhid + "'";
			List<ParentDetail> parentDetailList = userServiceDao.getListFromMappedObjNativeQuery(queryParentConnect);
			if (!BasicUtils.isEmpty(parentDetailList)) {
				dash.setGynaecologist(parentDetailList.get(0).getMother_gynaecologist());
			}
			String queryBabyConnect = "select obj from BabyDetail as obj where uhid='" + uhid + "' and admissionstatus = 'true' " +
					"order by creationtime desc";
			List<BabyDetail> babyDetailList = userServiceDao.getListFromMappedObjNativeQuery(queryBabyConnect);
			if (!BasicUtils.isEmpty(babyDetailList)) {
				if (!BasicUtils.isEmpty(babyDetailList.get(0).getAdmittingdoctor())) {
					dash.setNeonatologist(babyDetailList.get(0).getAdmittingdoctor());
				}

				if (!BasicUtils.isEmpty(babyDetailList.get(0).getInoutPatientStatus())) {
					dash.setInOutPatientStatus(babyDetailList.get(0).getInoutPatientStatus());
				}
			}
		}

		String boxString = "";
		if (!BasicUtils.isEmpty(uhid)) {
			String queryDeviceConnect = "select obj from BedDeviceDetail as obj where uhid='" + uhid + "' and status='"
					+ true + "'";
			List<BedDeviceDetail> bedDeviceDetail = userServiceDao.getListFromMappedObjNativeQuery(queryDeviceConnect);
			if (!BasicUtils.isEmpty(bedDeviceDetail)) {
				for (BedDeviceDetail detail : bedDeviceDetail) {
					Long boxId = detail.getBbox_device_id();
					if (!BasicUtils.isEmpty(boxId)) {
						String queryBoxNameConnect = "select obj from RefInicuBbox as obj where bbox_id='" + boxId
								+ "'";
						List<RefInicuBbox> refInicuBox = userServiceDao
								.getListFromMappedObjNativeQuery(queryBoxNameConnect);
						if (!BasicUtils.isEmpty(refInicuBox)) {
							String boxName = refInicuBox.get(0).getBboxname();
							if (!BasicUtils.isEmpty(boxName)) {
								if (boxString == "") {
									boxString = boxName;
								} else {
									boxString = boxString + "," + boxName;
								}
								dash.setIsDeviceConnected(true);
							}
						}
					}
				}
			}
		}
		
		dash.setBoxName(boxString);

		System.out.println(new java.sql.Date(new java.util.Date().getTime()) + "datesss");

		if (!BasicUtils.isEmpty(uhid)) {
			String queryDoctorNote = "select obj from DoctorNote as obj where uhid='" + uhid + "' and noteentrytime>='"
					+ new java.sql.Date(new java.util.Date().getTime()) + "' order by noteentrytime desc";
			List<DoctorNote> currentDoctorNoteList = userServiceDao.getListFromMappedObjNativeQuery(queryDoctorNote);
			if (!BasicUtils.isEmpty(currentDoctorNoteList)) {
				dash.setNotes(currentDoctorNoteList.get(0).getDoctornotes());
			}
		}

		String name = d.getBabyName();
		if (!BasicUtils.isEmpty(name)) {
			dash.setName(name);
		}

		String birthWeight = d.getBirthweight();
		if (!BasicUtils.isEmpty(birthWeight)) {
//			dash.setBirthWeight(String.valueOf(Integer.parseInt(birthWeight)));
			dash.setBirthWeight(String.valueOf(birthWeight));
		}

		String gestation = d.getGestation();
		if (!BasicUtils.isEmpty(gestation)) {
			dash.setGestation(gestation);
		}

		// ROP Alert only by doctors(Planned)
		if(!BasicUtils.isEmpty(uhid)) {
			String queryRop = "select obj from ScreenRop as obj where uhid='" + uhid + "' order by screening_time desc";
			List<ScreenRop> screenRopList = userServiceDao.getListFromMappedObjNativeQuery(queryRop);
			if (!BasicUtils.isEmpty(screenRopList)) {
				ScreenRop ropObject = screenRopList.get(0);
				if (!BasicUtils.isEmpty(ropObject.getReassess_date())) {
					String ropDate = "ROP : " + (ropObject.getReassess_date().getYear() + 1900) + "-"
							+ (ropObject.getReassess_date().getMonth() + 1) + "-" + ropObject.getReassess_date().getDate()
							+ " (Planned)";
					dash.setRopAlert(ropDate);
				}
			}
		}

		java.util.Date dateBirth = d.getDateofbirth();
		if (!BasicUtils.isEmpty(dateBirth)) {
			try {
				dash.setDob(sdf.format(dateBirth));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// time of birth of baby
		String timeBirth = d.getTimeofbirth();
		if (!BasicUtils.isEmpty(timeBirth)) {
			try {
				dash.setTimeOfBirth(timeBirth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			dash.setTimeOfBirth("00,00,AM");
		}

		Timestamp dateTimeOfBirth = BasicUtils.getDateTimeFromString(d.getDateofbirth(),d.getTimeofbirth());
		if(!BasicUtils.isEmpty(dateTimeOfBirth)){
			dash.setDateTimeOfBirth(new Timestamp(dateTimeOfBirth.getTime() - offset));
		}else{
			dash.setDateTimeOfBirth(new Timestamp(BasicUtils.getDateTimeFromString(d.getDateofbirth(),"00,00,AM").getTime() -offset));
		}

		Timestamp dateTimeOfAdmission = BasicUtils.getDateTimeFromString(d.getAdmitDate(),d.getAdmittime());
		if(!BasicUtils.isEmpty(dateTimeOfAdmission)){
			dash.setDateTimeOfAdmission(new Timestamp(dateTimeOfAdmission.getTime() - offset));
		}else{
			dash.setDateTimeOfAdmission(new Timestamp(BasicUtils.getDateTimeFromString(d.getAdmitDate(),"00,00,AM").getTime() -offset));
		}


		String gender = d.getGender();
		if (!BasicUtils.isEmpty(gender)) {
			dash.setGender(gender);
		}

		String roomname = d.getRoomname();
		if (!BasicUtils.isEmpty(roomname)) {
			dash.setBabyRoom(roomname);
		}

		String episodId = d.getEpisodeid();
		if (!BasicUtils.isEmpty(episodId)) {
			dash.setEpisodeid(episodId);
		}

		String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='" + uhid + "';";
		try {
			List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
			if (!BasicUtils.isEmpty(resultSet)) {
				Float maxWeight = resultSet.get(0);
				if (!BasicUtils.isEmpty(maxWeight)) {
					dash.setCurrentdateweight(maxWeight);
					dash.setWorkingWeight(maxWeight);
					workingWeight = maxWeight;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test", "",
					"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}

		String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid + "' and visitdate='"
				+ new java.sql.Date(new java.util.Date().getTime()) + "'";
		List<BabyVisit> currentBabyVisitList = userServiceDao.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
		if (!BasicUtils.isEmpty(currentBabyVisitList)) {
			dash.setTodayWeight(currentBabyVisitList.get(0).getCurrentdateweight());
		}

		try {
			if (!dash.isIsvacant() && dash.getUhid() != null) {
				// query to fetch if devices are connected or not.
				String query = "select devicetype from bed_device_history where uhid = '" + dash.getUhid().trim()
						+ "';";
				List<String> resultSet = userServiceDao.executeNativeQuery(query);
				if (!BasicUtils.isEmpty(resultSet)) {
					for (String s : resultSet) {
						if (!BasicUtils.isEmpty(s) && s.equalsIgnoreCase("monitor")) {
							dash.setEcg(true);
						} else if (!BasicUtils.isEmpty(s) && s.equalsIgnoreCase("ventilator")) {
							dash.setOxygen(true);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test", "",
					"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}

		String feedStr = "";
		if (!BasicUtils.isEmpty(d.getFeedDetail())) {
			feedStr = (d.getFeedDetail());
		}
		dash.setFeedDetail(feedStr);

		// Cycle runs from 8AM to 8AM.
		Timestamp today = new Timestamp((new java.util.Date().getTime()));
		Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));

		today.setHours(8);
		today.setMinutes(0);
		today.setSeconds(0);

		yesterday.setHours(8);
		yesterday.setMinutes(0);
		yesterday.setSeconds(0);

		try {
			if (!BasicUtils.isEmpty(uhid)) {

				// To display the last ventilator entered by nurses
				String queryVentilatorToFindLast = "Select obj from NursingVentilaor obj where uhid='" + uhid
						+ "' and entrydate >= '" + yesterday + "' and entrydate <= '" + today
						+ "' order by entrydate desc";
				List<NursingVentilaor> ventilatorList = inicuDao.getListFromMappedObjQuery(queryVentilatorToFindLast);
				if (!BasicUtils.isEmpty(ventilatorList)) {
					if (!BasicUtils.isEmpty(ventilatorList.get(0).getVentmode())) {
						String queryVentilatorMode = "Select obj from RefVentilationmode obj where ventmodeid='"
								+ ventilatorList.get(0).getVentmode() + "' ";
						List<RefVentilationmode> ventilatorModesList = inicuDao
								.getListFromMappedObjQuery(queryVentilatorMode);
						if (!BasicUtils.isEmpty(ventilatorModesList.get(0).getVentilationmode())) {
							dash.setVentMode(ventilatorModesList.get(0).getVentilationmode());
						}
					}
				}

				String loggedUserString = "";

				// To get the names of all nurses in the last 24 hours
				String queryNursingNotesAll = "Select obj from NursingNote obj where uhid='" + uhid
						+ "' and from_time >= '" + yesterday + "' and from_time <= '" + today + "' and to_time >= '"
						+ yesterday + "' and to_time <= '" + today + "' ";
				List<NursingNote> notesList = inicuDao.getListFromMappedObjQuery(queryNursingNotesAll);
				if (!BasicUtils.isEmpty(notesList)) {
					for (NursingNote note : notesList) {
						String loggedUser = note.getLoggeduser();
						if (loggedUserString.contains(loggedUser)) {
						} else {
							if (!BasicUtils.isEmpty(loggedUserString)) {
								loggedUserString = loggedUserString + ", " + loggedUser;
							} else {
								loggedUserString = loggedUser;
							}
						}
					}
				}
				dash.setNursesName(loggedUserString);


				today = new Timestamp(today.getTime() - offset);
				yesterday = new Timestamp(yesterday.getTime() - offset);


				// Variable to temp data
				String tempRbs = "";
				String tempTcb = "";

				// Query to get the parameters related to RBS ans TCB From NursingVitalParameter
				String queryForTcb = "select obj from NursingVitalparameter as obj where uhid='" + uhid
						+ "' and creationtime >= '" + yesterday + "' and creationtime <= '" + today
						+ "' and tcb is not null order by creationtime desc";

				List<NursingVitalparameter> nursingVitalParametersList = userServiceDao
						.getListFromMappedObjNativeQuery(queryForTcb);
				if (!BasicUtils.isEmpty(nursingVitalParametersList) && !BasicUtils.isEmpty(nursingVitalParametersList.get(0).getTcb())) {
					System.out.println("Nursuing Vital Details :" + nursingVitalParametersList);
					dash.setTcb(nursingVitalParametersList.get(0).getTcb());
					tempTcb = nursingVitalParametersList.get(0).getTcb();
				}

				String queryForRbs = "select obj from NursingVitalparameter as obj where uhid='" + uhid
						+ "' and creationtime >= '" + yesterday + "' and creationtime <= '" + today
						+ "' and rbs is not null order by creationtime desc";

				List<NursingVitalparameter> rbsResult = userServiceDao
						.getListFromMappedObjNativeQuery(queryForRbs);
				if (!BasicUtils.isEmpty(rbsResult) && !BasicUtils.isEmpty(rbsResult.get(0).getRbs())) {
					dash.setRbs(rbsResult.get(0).getRbs().toString());
					tempRbs = rbsResult.get(0).getRbs().toString();
				}

				// --------------------------------------------------------------------------

				int tempApneaDuration = 0;
				int tempSeizuresDuration = 0;

				// Query to get the parameters related to apena and seisure duration
				String queryNursingEpisode = "select obj from NursingEpisode as obj where uhid='" + uhid
						+ "' and creationtime >= '" + yesterday + "' and creationtime <= '" + today
						+ "' order by creationtime desc";
				System.out.println("Query Written:" + queryNursingEpisode);
				List<NursingEpisode> nursingEpisodeList = userServiceDao
						.getListFromMappedObjNativeQuery(queryNursingEpisode);
				if (!BasicUtils.isEmpty(nursingEpisodeList)) {

					for (NursingEpisode obj : nursingEpisodeList) {
						if (!BasicUtils.isEmpty(obj.getApnea())) {
							if (obj.getApnea() == true || obj.getApnea()) {
								tempApneaDuration = tempApneaDuration + 1;
							}
						}

						if (!BasicUtils.isEmpty(obj.getSeizures())) {
							if (obj.getSeizures() == true || obj.getSeizures()) {
								tempSeizuresDuration = tempSeizuresDuration + 1;
							}
						}
					}
//					dash.setApneaDuration(nursingEpisodeList.get(0).getApneaDuration());
//					dash.setSeizuresDuration(nursingEpisodeList.get(0).getSeizureDuration());
				}

				// -------------------------------------------------------------------------------------
				// To get the primary feed, secondary feed and urine total in last 24 hours
				String queryIntakeOutputAll = "Select obj from NursingIntakeOutput obj where uhid='" + uhid
						+ "' and entry_timestamp >= '" + yesterday + "' and entry_timestamp <= '" + today
						+ "' order by entry_timestamp desc";

				List<NursingIntakeOutput> intakeOutputList = inicuDao.getListFromMappedObjQuery(queryIntakeOutputAll);

				float enTotal = 0;
				float urineTotal = 0;

				double fluid_balance = 0;
				int stoolPassedCount = 0;
				double intakeValue = 0;
				double output = 0;
				String adbGirth = "";

				// EN Value
				float primaryFeedValue = 0;
				float formulaValue = 0;
				float totalEnteralValue = 0;
				// PN Value
				float readymadeTotalFeed = 0;
				float tpn_delivered = 0;
				float lipid_delivered = 0;
				float bolus_delivered_feed = 0;
				float totalParenteralValue = 0;

				String feedText = "";
				if (!BasicUtils.isEmpty(intakeOutputList)) {

					if (!BasicUtils.isEmpty(intakeOutputList.get(0).getAbdomenGirth())) {
						dash.setAbdGrith(intakeOutputList.get(0).getAbdomenGirth());
						adbGirth = intakeOutputList.get(0).getAbdomenGirth();
						System.out.println("ABDOMEN: " + intakeOutputList.get(0).getAbdomenGirth());
						System.out.println(
								"Last Entry :" + intakeOutputList.get(intakeOutputList.size() - 1).getAbdomenGirth());
					}

					for (NursingIntakeOutput obj : intakeOutputList) {

						if (!BasicUtils.isEmpty(obj.getPrimaryFeedValue()))
							primaryFeedValue = obj.getPrimaryFeedValue();

						if (!BasicUtils.isEmpty(obj.getFormulaValue()))
							formulaValue = obj.getFormulaValue();

						if (!BasicUtils.isEmpty(obj.getReadymadeDeliveredFeed()))
							readymadeTotalFeed = obj.getReadymadeDeliveredFeed();

						if (!BasicUtils.isEmpty(obj.getTpn_delivered()))
							tpn_delivered = obj.getTpn_delivered();

						if (!BasicUtils.isEmpty(obj.getLipid_delivered()))
							lipid_delivered = obj.getLipid_delivered();

						if (!BasicUtils.isEmpty(obj.getBolusDeliveredFeed()))
							bolus_delivered_feed = obj.getBolusDeliveredFeed();

						if (primaryFeedValue != 0)
							totalEnteralValue += primaryFeedValue;

						if (formulaValue != 0)
							totalEnteralValue += formulaValue;

						if (readymadeTotalFeed != 0)
							totalParenteralValue += readymadeTotalFeed;

						if (tpn_delivered != 0)
							totalParenteralValue += tpn_delivered;
						if (lipid_delivered != 0)
							totalParenteralValue += lipid_delivered;

						if (bolus_delivered_feed != 0)
							totalParenteralValue += bolus_delivered_feed;

						if (!BasicUtils.isEmpty(obj.getPrimaryFeedValue()))
							enTotal += obj.getPrimaryFeedValue();
						if (!BasicUtils.isEmpty(obj.getFormulaValue()))
							enTotal += obj.getFormulaValue();
						if (!BasicUtils.isEmpty(obj.getUrine()))
							urineTotal += Float.parseFloat(obj.getUrine());

						if (!BasicUtils.isEmpty(obj.getStoolPassed())) {
							if (obj.getStoolPassed() == true || obj.getStoolPassed()) {
								stoolPassedCount = stoolPassedCount + 1;
							}
						}

					}

					intakeValue = totalEnteralValue + totalParenteralValue;

					if (!BasicUtils.isEmpty(enTotal)) {
						feedText = "Total feed " + enTotal + " mL. ";
					}
					if (!BasicUtils.isEmpty(urineTotal)) {
						double weight = workingWeight / 1000;
						double urineMlKgHr = urineTotal / (weight * 24);
						output = Math.round(urineMlKgHr * 100.0) / 100.0;

						if (!BasicUtils.isEmpty(feedText)) {
							feedText = feedText + "Total urine " + urineTotal + " mL. ";
						} else {
							feedText = "Total urine" + urineTotal + " mL. ";
						}
					}
					if (!BasicUtils.isEmpty(d.getFeedDetail())) {
						dash.setFeedDetail(d.getFeedDetail());
					}
					feedText = feedStr + feedText;
				}
				String htmlNextLine = System.getProperty("line.separator");
				String intakeOutput = "";
				double weight = workingWeight / 1000;
				double intake = intakeValue / (weight * 24);
				if (intakeValue != 0) {
					intakeOutput += "Intake-" + (new DecimalFormat("##.##").format(((Math.round(intake * 100.0) / 100.0)) * 24)) + "ml/kg/day" + htmlNextLine;
				}
				if (output != 0 && intakeValue != 0) {
					String outputString = "Output-" + output + "ml/kg/hr";
					intakeOutput +=  outputString;
				}
				if (output != 0 && intakeValue == 0) {
					String outputString = "Output-" + output + " ml/kg/hr";
					intakeOutput += outputString;
				}
				if (output != 0 && intakeValue != 0)
					fluid_balance = Math.round((intake - output) * 100.0) / 100.0;
				else
					fluid_balance = 0.0;


				String enFeedStr = "";
				List<BabyfeedDetail> feedList = (List<BabyfeedDetail>) inicuDao.getListFromMappedObjRowLimitQuery(
						HqlSqlQueryConstants.getBabyfeedDetailList(uhid, yesterday, today), 1);
				if (!BasicUtils.isEmpty(feedList)) {
					BabyfeedDetail babyFeedObj = feedList.get(0);
					if (babyFeedObj.getIsenternalgiven() != null && babyFeedObj.getIsenternalgiven()) {
						List<RefMasterfeedtype> refFeedTypeList = inicuDao
								.getListFromMappedObjQuery("SELECT obj FROM RefMasterfeedtype as obj");
						//String primaryFeed = "";
						String feedMethodStr = "";

//						if (!(babyFeedObj.getFeedtype() == null || babyFeedObj.getFeedtype() == "")) {
//							String[] feedTypeArr = babyFeedObj.getFeedtype().replace("[", "").replace("]", "").split(",");
//							for (int i = 0; i < refFeedTypeList.size(); i++) {
//								if (feedTypeArr[0].equalsIgnoreCase(refFeedTypeList.get(i).getFeedtypeid())) {
//									primaryFeed = refFeedTypeList.get(i).getFeedtypename();
//								}
//							}
//						}
						String enfeedDetailsSql = "SELECT obj FROM EnFeedDetail as obj WHERE uhid='" + uhid
								+ "' and babyfeedid=" + babyFeedObj.getBabyfeedid() + " order by en_feed_detail_id asc";
						List<EnFeedDetail> enfeedDetailsList = inicuDao.getListFromMappedObjQuery(enfeedDetailsSql);

						if (!BasicUtils.isEmpty(enfeedDetailsList)) {
							for (int i = 0; i < enfeedDetailsList.size(); i++) {
								if (i == 0) {
									enFeedStr = enfeedDetailsList.get(i).getFeed_volume() + " (mL/feed) for "
											+ enfeedDetailsList.get(i).getNo_of_feed() + " feed";
								} else {
									enFeedStr += ", " + enfeedDetailsList.get(i).getFeed_volume() + " (mL/feed) for "
											+ enfeedDetailsList.get(i).getNo_of_feed() + " feed";
								}
							}
						}

						if (BasicUtils.isEmpty(babyFeedObj.getFeedduration())) {
							enFeedStr += " ad-lib";
						} else {
							enFeedStr += " every " + babyFeedObj.getFeedduration() + " hours";
						}

						if (!(babyFeedObj.getFeedmethod() == null || babyFeedObj.getFeedmethod().isEmpty())) {
							feedMethodStr = babyFeedObj.getFeedmethod().replace("[", "").replace("]", "").replace(", ",
									",");

							String refFeedMethodSql = "SELECT obj FROM RefMasterfeedmethod as obj";
							List<RefMasterfeedmethod> refFeedMethodList = inicuDao
									.getListFromMappedObjQuery(refFeedMethodSql);

							if (!BasicUtils.isEmpty(feedMethodStr)) {
								String[] feedMethodArr = feedMethodStr.split(",");

								for (int i = 0; i < feedMethodArr.length; i++) {
									feedMethodStr = "";
									Iterator<RefMasterfeedmethod> itr = refFeedMethodList.iterator();
									while (itr.hasNext()) {
										RefMasterfeedmethod obj = itr.next();
										if (feedMethodArr[i].trim().equalsIgnoreCase(obj.getFeedmethodid())) {
											if (enFeedStr.isEmpty()) {
												enFeedStr = obj.getFeedmethodname();
											} else {
												enFeedStr += "( " + obj.getFeedmethodname() + ")";
											}
											break;
										}
									}
								}
							}
						}
					}
				}


				dash.setEnteralStr(enFeedStr);

				//String parentalStr = "";
				String babyFeedSql = "SELECT obj FROM BabyfeedDetail as obj WHERE uhid ='" + uhid
						+ "' and entrydatetime >= '" + yesterday + "' and entrydatetime <= '" + today +"' order by entrydatetime desc";

				List<BabyfeedDetail> babyFeedList = (List<BabyfeedDetail>) inicuDao
						.getListFromMappedObjRowLimitQuery(babyFeedSql, 1);


				dash.setFeedList(babyFeedList);


//				if(!BasicUtils.isEmpty(babyFeedList)) {
//					if(babyFeedList.get(0).getLipidTotal()!=null && babyFeedList.get(0).getDuration()!=null) {
//						parentalStr += "Lipids @ : " + ((Math.round(babyFeedList.get(0).getLipidTotal()/babyFeedList.get(0).getDuration()) * 100)/100) + " ml/hr.";
//					}
//					//if() {
//						parentalStr += "PN @ : " + babyFeedList.get(0).getTotalparenteralAdditivevolume() +
//								babyFeedList.get(0).getReadymadeTotalFluidVolume() + babyFeedList.get(0).getAminoacidTotal() + "ml. ";
//					//}
//				}

				dash.setIntakeOutput(intakeOutput);

				String otherInfo = "";
				if (tempRbs != "" && tempRbs!=null)
					otherInfo += "RBS-" + tempRbs + "mg/dL,";
				if (tempTcb != "" && tempTcb!=null)
					otherInfo += "TCB-" + tempTcb + " mg/dL,";
				if (tempApneaDuration != 0)
					otherInfo += tempApneaDuration + " Apneas,";
				if (tempSeizuresDuration != 0)
					otherInfo += tempSeizuresDuration + " Seizures,";
				if (adbGirth != "")
					otherInfo += "Adb G-" + adbGirth + "cm";

				dash.setOtherInfo(otherInfo);
				dash.setFeedDetail(feedText);
				dash.setStoolCount(stoolPassedCount);
				dash.setFluidBalance(fluid_balance);

				// To get the name of all antibiotics(TYPE0001) given by nurses in last 24
				//				// hours.
				String medicineListStr = "";
				String queryMedicationAll = "select obj from NursingMedication as obj where uhid = '" + uhid
						+ "' and given_time >= '" + yesterday + "' and given_time <= '" + today + "' ";
				List<NursingMedication> resultListRaw = inicuDao.getListFromMappedObjQuery(queryMedicationAll);
				if (!BasicUtils.isEmpty(resultListRaw)) {

					for (NursingMedication med : resultListRaw) {

						String queryNursingMedicationAll = "select b.medicinename from " + BasicConstants.SCHEMA_NAME
								+ ".baby_prescription as b where b.uhid = '" + uhid + "' and baby_presid = '"
								+ med.getBaby_presid() + "'";
						List<String> resultListMedications = inicuDao.getListFromNativeQuery(queryNursingMedicationAll);
						if (!BasicUtils.isEmpty(resultListMedications)) {
							String medicineName = resultListMedications.get(0);
							if (!BasicUtils.isEmpty(medicineName)) {
								if (medicineListStr.contains(medicineName)) {

								} else {
									if (!BasicUtils.isEmpty(medicineListStr)) {
										medicineListStr = medicineListStr + ", " + medicineName + ":"
												+ med.getDay_number();
									} else {
										medicineListStr = medicineName + ":" + med.getDay_number();
									}
								}
							}

							//medicineListStr += " Dose given : " + med.getGiven_dose() + " mg/dose ";
							dash.setAntibioticsName(medicineListStr);
						}
					}
				}


				//getting medicine days of other medicines given to baby
				String queryIntakeOutputAllAdditives = "Select obj from NursingIntakeOutput obj where uhid='" + uhid
						+ "' and entry_timestamp >= '" + yesterday + "' and entry_timestamp <= '" + today
						+ "' order by entry_timestamp desc";

				List<NursingIntakeOutput> additivesIntakeOutputList = inicuDao.getListFromMappedObjQuery(queryIntakeOutputAllAdditives);
				if(!BasicUtils.isEmpty(additivesIntakeOutputList)) {
					//for (NursingIntakeOutput obj : additivesIntakeOutputList) {
					NursingIntakeOutput obj = additivesIntakeOutputList.get(0);
					if (!BasicUtils.isEmpty(obj.getCalciumVolume()))
						medicineListStr+= "Calcium: " + obj.getMedicineDay() + ", ";

					if (!BasicUtils.isEmpty(obj.getIronVolume()))
						medicineListStr+= "Iron: " + obj.getMedicineDay() + ", ";

					if (!BasicUtils.isEmpty(obj.getMvVolume()))
						medicineListStr+= "Multivitamin: " + obj.getMedicineDay() + ", ";

					if (!BasicUtils.isEmpty(obj.getMctVolume()))
						medicineListStr+= "MCT Oil: " + obj.getMedicineDay() + ", ";

					if (!BasicUtils.isEmpty(obj.getVitamindVolume()))
						medicineListStr+= "Vitamin D: " + obj.getMedicineDay();

				}
				dash.setAntibioticsName(medicineListStr);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// blood gas info
		try {
			if (!BasicUtils.isEmpty(uhid)) {

				String queryRespSupportToFindLastActive = "Select obj from RespSupport obj where uhid='" + uhid
						+ "' and isactive='true' order by creationtime desc";

				List<RespSupport> respSupportActiveList = inicuDao
						.getListFromMappedObjQuery(queryRespSupportToFindLastActive);
				// get all after latest start...
				if (!BasicUtils.isEmpty(respSupportActiveList)) {
					if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getHco3())) {
						String hrStr = respSupportActiveList.get(0).getHco3();
						double hco3 = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
						dash.setHco3(String.valueOf(hco3));
					}
					if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getBe())) {
						String hrStr = respSupportActiveList.get(0).getBe();
						double be = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
						dash.setBe(hrStr);
					}
					if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getCo2())) {
						String hrStr = respSupportActiveList.get(0).getCo2();
						double co2 = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
						dash.setCo2(String.valueOf(co2));
					}
					if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getph())) {
						String hrStr = respSupportActiveList.get(0).getph();
						double ph = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
						dash.setPh(String.valueOf(ph));
					}
					if (!BasicUtils.isEmpty(respSupportActiveList.get(0).getRspCO2())) {
						String hrStr = respSupportActiveList.get(0).getRspCO2();
						double pco2 = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
						dash.setPco2(String.valueOf(pco2));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// set device details rounding off values.
		try {
			if (!BasicUtils.isEmpty(d.getHeartRate())) {
				String hrStr = d.getHeartRate();
				double hr = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
				dash.setHR(String.valueOf(hr));
			}

			if (!BasicUtils.isEmpty(d.getSp02())) {
				String spo2Str = d.getSp02();
				double spo2 = Math.round(Float.valueOf(spo2Str) * 10.0 / 10.0);
				dash.setSPO2(String.valueOf(spo2));
			}

			if (!BasicUtils.isEmpty(d.getCo2RespRate())) {
				String respRateStr = d.getCo2RespRate();
				double resprate = Math.round(Float.valueOf(respRateStr) * 10.0 / 10.0);
				dash.setRR(String.valueOf(resprate));
			}

			// setting RR(on dashboard) page to baby ecg rate
			if (!BasicUtils.isEmpty(d.getRespRate())) {
				String respRateStr = d.getRespRate();
				double resprate = Math.round(Float.valueOf(respRateStr) * 10.0 / 10.0);
				dash.setRR(String.valueOf(resprate));
			}

			if (!BasicUtils.isEmpty(d.getPulseRate())) {
				String pulseRateStr = d.getPulseRate();
				double pulseRate = Math.round(Float.valueOf(pulseRateStr) * 10.0 / 10.0);
				dash.setPR(String.valueOf(pulseRate));
			}

			if (!BasicUtils.isEmpty(d.getStarttime())) {
				dash.setDeviceTime(d.getStarttime().getTime());
			}

			if (!BasicUtils.isEmpty(d.getTemp())) {
				dash.setTemp(d.getTemp());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!BasicUtils.isEmpty(d.getDayoflife())) {
			dash.setDayOfLife(d.getDayoflife());
		}
		if (!BasicUtils.isEmpty(d.getWeightatadmission())) {
			dash.setWeightAtAdmission(d.getWeightatadmission());
		}
		if (!BasicUtils.isEmpty(d.getLastdayweight())) {
			dash.setLastDayWeight(d.getLastdayweight());
		}
		if (!BasicUtils.isEmpty(d.getCurrentdayweight())) {
			dash.setCurrentDayWeight(d.getCurrentdayweight());
		}
		if (!BasicUtils.isEmpty(d.getDifference())) {
			dash.setDifference(d.getDifference());
		}

		if(!BasicUtils.isEmpty(uhid)) {
			
			
			String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<AdmissionNotes> listAdmissionNotes = inicuDao.getListFromMappedObjQuery(queryAdmissionNotes);
			String adminssionNotesDiagnosis = "";
			if (!BasicUtils.isEmpty(listAdmissionNotes)) {
				AdmissionNotes admNotes = listAdmissionNotes.get(0);

				if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
					adminssionNotesDiagnosis = admNotes.getDiagnosis();
				}
			}

			String diagnosisStr = "";

			if (!BasicUtils.isEmpty(d.getDiagnosis())) {
				diagnosisStr = d.getDiagnosis().replaceAll(",", "/");
			}
			
			int flag = 0;
			if(adminssionNotesDiagnosis.contains("Sepsis")){
				adminssionNotesDiagnosis =  getSepsisType(adminssionNotesDiagnosis,uhid);
				flag =1;
			}

			if(diagnosisStr.contains("Sepsis")) {
				if(flag == 0) {
					diagnosisStr = getSepsisType(diagnosisStr,uhid);
				}else if (flag == 1){

					// replace the Sepsis with empty string
					diagnosisStr = diagnosisStr.replace("Sepsis","");
				}
			}

			if (!BasicUtils.isEmpty(adminssionNotesDiagnosis)) {
				diagnosisStr = adminssionNotesDiagnosis.replace(",", "/") + "/" + diagnosisStr;
			}

			String queryRop = "select obj from ScreenRop as obj where uhid = '" + uhid + "'";
			List<ScreenRop> ropLists = inicuDao.getListFromMappedObjQuery(queryRop);
			boolean leftRop = false;
			boolean rightRop = false;
			String ropDiagnosis = "";
			if(!BasicUtils.isEmpty(ropLists)) {
				for(ScreenRop ropObject: ropLists) {
					if(ropObject.getIs_rop()!=null && ropObject.getIs_rop()==true) {
						ropDiagnosis = "ROP";
						if(ropObject.getIs_rop_left()!=null && ropObject.getIs_rop_left() == true) {
							leftRop = true;
						}
						if(ropObject.getIs_rop_right()!=null && ropObject.getIs_rop_right() == true) {
							rightRop = true;
						}
					}
				}
				
					
				if(leftRop == true && rightRop == false) {
					ropDiagnosis = "ROP Left eye";
					diagnosisStr+= "/ " + ropDiagnosis;
				}
				else if(rightRop == true && leftRop == false) {
					ropDiagnosis = "/ " + "ROP Right eye";
					diagnosisStr+= "/ " + ropDiagnosis;
				}
				else if(rightRop == true && leftRop == true) {
					ropDiagnosis = "/ " + "ROP Both eyes";
					diagnosisStr+= "/ " + ropDiagnosis;				}
				else {
					diagnosisStr+= "/ " + ropDiagnosis;
				}
				
				
			}

			// misc disease list

			//Renalfailure handling
			String queryRenalfailure = "Select obj from SaRenalfailure as obj where uhid='"+ uhid +
					"' order by creationtime desc";
			List<SaRenalfailure> listRenal = inicuDao.getListFromMappedObjQuery(queryRenalfailure);
			String renalDiagnosis="";
			if(!BasicUtils.isEmpty(listRenal)) {
				SaRenalfailure renalObj = listRenal.get(0);
				if(!BasicUtils.isEmpty(renalObj.getIsRenalFailure())) {
					if(!BasicUtils.isEmpty(renalObj.getUrineOutputFeature())) {

						renalDiagnosis +="/" + "Renal failure with " + renalObj.getUrineOutputFeature();
						//					if(diagnosisStr.contains("Renal"))
						//						diagnosisStr = diagnosisStr.replaceAll("Renal", renalDiagnosis);
					}
				}
			}


			//misc disease list
			String queryMiscDisease = "select obj from  SaMiscellaneous obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<SaMiscellaneous> listMiscDisease = inicuDao.getListFromMappedObjQuery(queryMiscDisease);
			String miscDiseaseDiagnosis = "";
			if (!BasicUtils.isEmpty(listMiscDisease)) {
				for (SaMiscellaneous miscObj : listMiscDisease) {

					if(!BasicUtils.isEmpty(miscObj.getDisease())){
						miscDiseaseDiagnosis += miscObj.getDisease() + "/";
					}else{
						miscDiseaseDiagnosis += "Miscellaneous" + "/";
					}

				}
				if (!BasicUtils.isEmpty(miscDiseaseDiagnosis)) {
					if (BasicUtils.isEmpty(diagnosisStr)) {
						diagnosisStr += miscDiseaseDiagnosis;
					} else {
						diagnosisStr += "/" + miscDiseaseDiagnosis;
					}
				}
			}

			// misc2 disease list
			String queryMisc2Disease = "select obj from  SaMiscellaneous2 obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<SaMiscellaneous2> listMisc2Disease = inicuDao.getListFromMappedObjQuery(queryMisc2Disease);
			String misc2DiseaseDiagnosis = "";
			if (!BasicUtils.isEmpty(listMisc2Disease)) {
				for (SaMiscellaneous2 misc2Obj : listMisc2Disease) {
					if(!BasicUtils.isEmpty(misc2Obj.getDisease())){
						misc2DiseaseDiagnosis += misc2Obj.getDisease() + "/";
					}else{
						misc2DiseaseDiagnosis += "Miscellaneous" + "/";
					}
				}
				if (!BasicUtils.isEmpty(misc2DiseaseDiagnosis)) {
					if (BasicUtils.isEmpty(diagnosisStr)) {
						diagnosisStr += misc2DiseaseDiagnosis;
					} else {
						diagnosisStr += "/" + misc2DiseaseDiagnosis;
					}
				}
			}
			if (!BasicUtils.isEmpty(diagnosisStr)) {
				String[] daignosisArr = diagnosisStr.split("/");
				diagnosisStr = "";
				for (String daignosis : daignosisArr) {
					if (diagnosisStr.isEmpty()) {
						diagnosisStr = daignosis;
					} else if (!diagnosisStr.contains(daignosis.trim())) {
						diagnosisStr += "/ " + daignosis.trim();
					}
				}
				dash.setDiagnosis(diagnosisStr);
			}

			// handle unique in diagnosis...
			String finalDiagnosis = dash.getDiagnosis();
			String finalDiagnosisUnique = "";
			if (!BasicUtils.isEmpty(finalDiagnosis)) {
				String[] finalDiagArr = finalDiagnosis.split("/");
				for (String diag : finalDiagArr) {
					if (finalDiagnosisUnique.isEmpty()) {
						finalDiagnosisUnique = diag.replace(" ", "");
					} else {
						if (!finalDiagnosisUnique.contains(diag.replace(" ", " "))) {
							finalDiagnosisUnique += "/ " + diag.replace(" ", " ");
						}
					}
				}
				finalDiagnosisUnique = finalDiagnosisUnique.replace("Jaundice", "NNH");
				if(finalDiagnosisUnique.contains("Renal"))
					finalDiagnosisUnique = finalDiagnosisUnique.replaceAll("Renal", renalDiagnosis);

			}
			finalDiagnosisUnique=finalDiagnosisUnique.replace(",","/");


			// get the ICD Cause
			String icdCode = "";
			List<VwIcdCause> pastIcdcause = new ArrayList<>();

			String queryicdCauseString = "Select obj from VwIcdCause as obj where uhid='" + uhid
					+ "' and icdcause is not null order by creationtime desc, id desc, event desc";
			pastIcdcause= inicuDao.getListFromMappedObjQuery(queryicdCauseString);

			if(!BasicUtils.isEmpty(pastIcdcause)){

				for(VwIcdCause obj : pastIcdcause){

					System.out.println(obj);
					System.out.println(obj.getIcdcause());

					if(BasicUtils.isEmpty(icdCode)){
						icdCode += obj.getIcdcause().replaceAll(",", "/").replace("[", "").replace("]", "");
					}else{
						icdCode += "/" + obj.getIcdcause().replaceAll(",", "/").replace("[", "").replace("]", "");
					}
				}

				String[] icdCodeArr = icdCode.split("/");
				icdCode = "";
				for (String icdCodeStr : icdCodeArr) {
					if (icdCode.isEmpty()) {
						icdCode = icdCodeStr;
					} else if (!icdCode.contains(icdCodeStr.trim())) {
						icdCode += "/ " + icdCodeStr.trim();
					}
				}
			}
			if(!BasicUtils.isEmpty(icdCode)) {
				finalDiagnosisUnique += "\n";
				finalDiagnosisUnique += "ICD Code: " + icdCode;
			}

//			if(!BasicUtils.isEmpty(finalDiagnosisUnique)){
//				try {
//					// replace the Gestation as super script
//					String[] arr = finalDiagnosisUnique.split("/");
//
//					if(arr.length>0) {
//						String[] secArr = arr[1].trim().split(" ");
//						System.out.println("Checking");
//
//						String tempGestation = " <var>" + secArr[0].toString() + "</var> ";
//
//						if (secArr.length > 2 && secArr[2] != null && !BasicUtils.isEmpty(secArr[2])) {
//							tempGestation += "<sup> +" + secArr[2].toString() + "</sup>";
//						} else {
//							tempGestation += "<sup> +0 </sup>";
//						}
//						arr[1] = tempGestation;
//
//						finalDiagnosisUnique = "";
//						for (int i = 0; i < arr.length; i++) {
//
//							if(arr[i].indexOf("AGA")!=-1 || arr[i].indexOf("SGA")!=-1 || arr[i].indexOf("LGA")!=-1){
//								String arrTemp = arr[i].trim().substring(0,3);
//								arr[i] = arrTemp;
//							}
//
//							if (finalDiagnosisUnique == "") {
//
//								finalDiagnosisUnique += arr[i];
//							} else {
//								finalDiagnosisUnique += "/" + arr[i];
//							}
//						}
//						System.out.println("Checking");
//					}
//				}catch(Exception e){
//					System.out.println(e.getMessage());
//				}
//			}

			dash.setDiagnosis(finalDiagnosisUnique);

		}

		// if (!BasicUtils.isEmpty(d.getDiagnosis())) {
		// if (d.getDiagnosis().contains(",")) {
		// String diagnosis = d.getDiagnosis().replace(",", ", ");
		// diagnosis = d.getDiagnosis().replace(" ", "");
		// String[] parts = diagnosis.split(",");
		// List<String> list = new ArrayList<>();
		// for(int i=0;i<parts.length;i++){
		// list.add(parts[i]);
		// }
		// Collection<String> c = list.stream().collect(Collectors.toSet());
		// diagnosis = "";
		// diagnosis = c.toString();
		// if(diagnosis.contains("[")){
		// diagnosis = diagnosis.replace("[", "");
		// }
		// if(diagnosis.contains("]")){
		// diagnosis = diagnosis.replace("]", "");
		// }
		// dash.setDiagnosis(diagnosis);
		// } else {
		// dash.setDiagnosis(d.getDiagnosis());
		// }
		// }

		if (!BasicUtils.isEmpty(d.getSys_bp())) {
			dash.setSys_bp(d.getSys_bp());
		}

		if (!BasicUtils.isEmpty(d.getDia_bp())) {
			dash.setDia_bp(d.getDia_bp());
		}

		if (!BasicUtils.isEmpty(d.getMean_bp())) {
			dash.setMean_bp(d.getMean_bp());
		}

		if (!BasicUtils.isEmpty(d.getFio2())) {
			dash.setFio2(d.getFio2());
		}

		if (!BasicUtils.isEmpty(d.getPip())) {
			dash.setPip(d.getPip());
		}

		if (!BasicUtils.isEmpty(d.getPeep())) {
			dash.setPeep(d.getPeep());
		}

		String babyType = d.getBabyType();
		if (!BasicUtils.isEmpty(babyType)) {
			dash.setBabyType(babyType);
		}

		String babyNumber = d.getBabyNumber();
		if (!BasicUtils.isEmpty(babyNumber)) {
			dash.setBabyNumber(babyNumber);
		}

		String address = d.getAddress();
		if (!BasicUtils.isEmpty(address)) {
			address = address.replaceAll(", ,", ", ");
			address = address.replaceAll(",", "");
			dash.setAddress(address);
		}

		ImagePOJO babyImage = getImage("baby_" + uhid + "_pic.png");
		dash.setBabyImage(babyImage);

		// get the anthropometry progress rates
		AnthropometryProgressRate anthropometryProgressRate=getAnthropometryGrowthRate(uhid);
		if(anthropometryProgressRate!=null){
			dash.setAnthropometryProgressRate(anthropometryProgressRate);
		}
		String queryParentDetail = "select obj from  ParentDetail obj where uhid='" + uhid
				+ "' order by creationtime desc";
		List<ParentDetail> parentDetail = inicuDao.getListFromMappedObjQuery(queryParentDetail);
		if (!BasicUtils.isEmpty(parentDetail) && !BasicUtils.isEmpty(parentDetail.get(0).getMother_phone())) {
			dash.setPhoneNumber(parentDetail.get(0).getMother_phone());
		}

		return dash;
	}

	@Override
	public boolean setStatus(ManageRoleObj manageRoleObj) {
		String modName = manageRoleObj.getModuleName();
		if (!BasicUtils.isEmpty(modName)) {
			String fetchModId = "Select mod FROM Module as mod WHERE description='" + modName.trim() + "'";
			List modList = userServiceDao.executeQuery(fetchModId);
			if (!BasicUtils.isEmpty(modList)) {
				Module mod = (Module) modList.get(0);
				if (mod != null) {
					BigInteger modId = mod.getModuleId();
					if (modId != null) {
						List<RoleObj> rolebObjList = manageRoleObj.getListOfRoleObj();
						if (!BasicUtils.isEmpty(rolebObjList)) {
							try {
								for (RoleObj role : rolebObjList) {
									String roleName = role.getRoleName();
									if (!BasicUtils.isEmpty(roleName)) {
										String mappedRoleName = BasicConstants.roleNameMAp.get(roleName.trim());
										if (!BasicUtils.isEmpty(roleName)) {
											String fetchRoleId = "Select role FROM Role as role WHERE roleName='"
													+ mappedRoleName + "'";
											List roleTable = userServiceDao.executeQuery(fetchRoleId);
											if (roleTable != null) {
												Role roleObj = (Role) roleTable.get(0);
												String roleID = roleObj.getUserId();

												// look for record if it exists
												String fetchRolMan = "Select rolMan FROM RoleManager as rolMan "
														+ "WHERE moduleId='" + modId.toString() + "' AND roleId='"
														+ roleID + "'";
												List resultSet = userServiceDao.executeQuery(fetchRolMan);
												if (resultSet != null) {
													RoleManager rolMan = (RoleManager) resultSet.get(0);
													if (rolMan == null) {
														rolMan = new RoleManager();
													}

													HashMap<String, Boolean> roleStatus = role.getStatus();
													if (!BasicUtils.isEmpty(roleStatus)) {
														Boolean readStatus = roleStatus.get("read");
														Boolean writeStatus = roleStatus.get("write");
														String statusName = "N";
														if (readStatus && writeStatus) {
															statusName = "RW";
														} else if (readStatus && !writeStatus) {
															statusName = "R";
														} else if (!readStatus && writeStatus) {
															statusName = "W";
														}

														String fetchStatusId = "SELECT statusid FROM role_status WHERE status_name ='"
																+ statusName + "'";
														List result = userServiceDao.executeNativeQuery(fetchStatusId);

														if (!BasicUtils.isEmpty(result)) {
															String id = result.get(0).toString();
															if (!BasicUtils.isEmpty(id)) {
																try {
																	rolMan.setStatusId(new BigInteger(id));
																	userServiceDao.saveObject(rolMan);
																} catch (Exception e) {
																	e.printStackTrace();
																	return false;
																}
															}
														}
													}
												}
											}
										}
									}
								}
								return true;
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public List<ExportBedDetailsCSV> getDashboardCsvData(String branchName) {
		List<DashboardDeviceDetailView> dashBoardList = new ArrayList<>();
		List<ExportBedDetailsCSV> bedList = new ArrayList<>();
		String deviceid = "";

		String fetchBedData = "SELECT dash FROM DashboardDeviceDetailView as dash WHERE uhid!=NULL and branchname = '" + branchName + "'";
		List<DashboardDeviceDetailView> resultSet = userServiceDao.executeQuery(fetchBedData);
		if (!BasicUtils.isEmpty(resultSet)) {
			for (DashboardDeviceDetailView d : resultSet) {
				
				Object deviceName = null;
				Object deviceBrand = null;
				
				String fetchNeoBoxDetails = "SELECT a.bboxname FROM ref_inicu_bbox a INNER JOIN bed_device_detail b on a.bbox_id=b.bbox_device_id" + 
						" WHERE b.uhid = '" + d.getUhid() + "' and status = '" + true + "'"; 
				Object boxname = inicuDao.getListFromNativeQuery(fetchNeoBoxDetails);
				
				String fetchDeviceNameDetails = "SELECT upper(b.bboxname),a.uhid,a.deviceid FROM bed_device_detail a " +
						"INNER JOIN ref_inicu_bbox as b " +
						"ON a.bbox_device_id=b.bbox_id " +
						"WHERE a.uhid = '" + d.getUhid() + "' and a.status = 'true'";
				List<Object[]> deviceBoxName=inicuDao.getListFromNativeQuery(fetchDeviceNameDetails);
				if(!BasicUtils.isEmpty(deviceBoxName)) {
					Object[] object = deviceBoxName.get(0);
					deviceid = object[2].toString();
					
					String getDeviceNameQuery = "SELECT devicebrand from device_detail where inicu_deviceid = '" + deviceid + "'";
					deviceBrand = inicuDao.getListFromNativeQuery(getDeviceNameQuery); 
					
					String getDeviceNameQuery1 = "SELECT devicetype from device_detail where inicu_deviceid = '" + deviceid + "'";
					deviceName = inicuDao.getListFromNativeQuery(getDeviceNameQuery1); 
				}
				
				ExportBedDetailsCSV obj = new ExportBedDetailsCSV();
				
				java.util.Date date = d.getAdmitDate();
				String toa = d.getAdmittime();
				toa = toa.replaceFirst(",", ":");
				toa = toa.replace(","," ");
				if (!BasicUtils.isEmpty(date)) {
					String dateStr = dateFormat.format(date);
					obj.setADMIT_DATE(dateStr);
					if(!BasicUtils.isEmpty(date)) {
						obj.setADMIT_DATE(dateStr + ' ' + toa);
					}
				}

				String babyName = d.getBabyName();
				if (!BasicUtils.isEmpty(babyName)) {
					obj.setBABY_NAME(babyName);
				}
				
				String babyType = d.getBabyType();
				if(!BasicUtils.isEmpty(babyType)) {
					obj.setBABY_TYPE(babyType);
				}
				
				String queryPatientStatus = "select obj from BabyDetail as obj where uhid = '" + d.getUhid() + "' and branchname = '" + branchName +  "' order by creationtime desc";
				List<BabyDetail> result = inicuDao.getListFromMappedObjQuery(queryPatientStatus);
				if(!BasicUtils.isEmpty(result)) {
					
						String patientstatus = result.get(0).getInoutPatientStatus();
						if(!BasicUtils.isEmpty(patientstatus)) {
							obj.setPATIENT_STATUS(patientstatus);
						}
						
				}

				String bedNo = d.getBedNo();
				if (!BasicUtils.isEmpty(bedNo)) {
					obj.setBED_NO(bedNo);
				}

				String weight = d.getBirthweight();
				if (!BasicUtils.isEmpty(weight)) {
					obj.setBIRTH_WEIGHT(weight);
				}

				String condition = d.getCondition();
				if (!BasicUtils.isEmpty(condition)) {
					obj.setCRITICALITY_AT_ADMISSION(condition);
				}

				String gender = d.getGender();
				if (!BasicUtils.isEmpty(gender)) {
					obj.setGENDER(gender);
				}

				String gestation = d.getGestation();
				if (!BasicUtils.isEmpty(gestation)) {
					obj.setGA_AT_BIRTH(gestation);
				}

				String level = d.getPatientLevel();
				if (!BasicUtils.isEmpty(level)) {
					obj.setPATIENT_LEVEL(level);
				}

				String uHID = d.getUhid();
				if (!BasicUtils.isEmpty(uHID)) {
					obj.setUHID(uHID);
				}
				
				String branchname = d.getBranchName();
				if(!BasicUtils.isEmpty(branchname)) {
					obj.setBRANCHNAME(branchname);
				}
				
				java.util.Date birthdate = d.getDateofbirth();
				String tob = d.getTimeofbirth();
				tob = tob.replaceFirst(",", ":");
				tob = tob.replace(","," ");
				if (!BasicUtils.isEmpty(birthdate)) {
					String dateStr = dateFormat.format(birthdate);
					obj.setBIRTH_DATE(dateStr);
					if(!BasicUtils.isEmpty(date)) {
						obj.setBIRTH_DATE(dateStr + ' ' + tob);
					}
				}
				
				if(!BasicUtils.isEmpty(boxname)) {
					obj.setNEOBOX(boxname.toString().replace("[", "").replace("]", ""));
				}else {
					obj.setNEOBOX(null);
				}
				
				Timestamp lastUpdatedTime = d.getStarttime();
				if(!BasicUtils.isEmpty(lastUpdatedTime)) {
					obj.setLastUpdatedAt(lastUpdatedTime.toString());
				}
				
				if(!BasicUtils.isEmpty(deviceName)) {
					String devBrand = deviceBrand.toString().replace("[", "").replace("]", "");
					String devName = deviceName.toString().replace("[", "").replace("]", "");
					String device = devName + " (" + devBrand + " )";
					obj.setDEVICENAME(device);
				}else {
					obj.setDEVICENAME(null);
				}
				
				obj.setADMISSIONSTATUS("TRUE");
				
				String babyNum = d.getBabyNumber();
				if(!BasicUtils.isEmpty(babyNum))
					obj.setBABY_NUM(babyNum);
				
				String nicuroom = d.getRoomname();
				if(!BasicUtils.isEmpty(nicuroom))
					obj.setROOM_NAME(nicuroom);

				bedList.add(obj);
			}
		}
		return bedList;
	}

	@Override
	public List<KeyValueObj> getImageData(String imageName, String branchname) throws InicuDatabaseExeption {
		List<KeyValueObj> imageData = new ArrayList<>();
        String imageBranchName = branchname.replace(" ", "");
		File fnew = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageBranchName + "_" + imageName + "_profile.png");
		try {
			if (fnew.exists()) {
				BufferedImage image = ImageIO.read(fnew);
				if (!BasicUtils.isEmpty(image)) {
					KeyValueObj obj = new KeyValueObj();
					String encodedStr = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(fnew.toPath()));
					obj.setKey("profile");
					obj.setValue(encodedStr);
					imageData.add(obj);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "default", "",
					"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		String query = "SELECT digitalsign FROM inicuuser WHERE username='" + imageName + "' and branchname='"+ branchname + "'";
		List<String> digitalSignResult = userServiceDao.executeNativeQuery(query);
		if (!BasicUtils.isEmpty(digitalSignResult) && !BasicUtils.isEmpty(digitalSignResult.get(0))) {
			fnew = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageBranchName + "_" + imageName + "_sign.png");
			try {
				if (fnew.exists()) {
					BufferedImage image = ImageIO.read(fnew);
					if (!BasicUtils.isEmpty(image)) {
						KeyValueObj obj = new KeyValueObj();
						String encodedStr = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(fnew.toPath()));
						obj.setKey("signature");
						obj.setValue(encodedStr);
						imageData.add(obj);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "default", "",
						"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
			}
		}

		return imageData;
	}

	@Override
	public void migrateDataToPostgres() {
		String patientId = "161203970";
		HashMap<String, String> patientDataMap = MappingConstants.patientDataMap.get(patientId);
		// System.out.println("patient id:"+patientId +" ->"+patientDataMap);
		if (!BasicUtils.isEmpty(patientDataMap)) {
			DeviceMonitorDetail devMonitor = new DeviceMonitorDetail();
			devMonitor.setBeddeviceid("123");

			String co2Resp = patientDataMap.get("CO2");
			if (!BasicUtils.isEmpty(co2Resp)) {
				devMonitor.setCo2Resprate(co2Resp);
			}

			String dia = patientDataMap.get("DIA");
			if (!BasicUtils.isEmpty(dia)) {
				devMonitor.setDiaBp(dia);
			}

			String ecgResprate = patientDataMap.get("ECG RESP RATE");
			if (!BasicUtils.isEmpty(ecgResprate)) {
				devMonitor.setEcgResprate(ecgResprate);
			}

			String etco2 = patientDataMap.get("ETCO2");
			if (!BasicUtils.isEmpty(etco2)) {
				devMonitor.setEtco2(etco2);
			}

			String heartrate = patientDataMap.get("HEART RATE");
			if (!BasicUtils.isEmpty(heartrate)) {
				devMonitor.setHeartrate(heartrate);
			}

			String pulserate = patientDataMap.get("PULSE");
			if (!BasicUtils.isEmpty(pulserate)) {
				devMonitor.setPulserate(pulserate);
			}

			String spo2 = patientDataMap.get("SPO2");
			if (!BasicUtils.isEmpty(spo2)) {
				devMonitor.setSpo2(spo2);
			}

			devMonitor.setUhid("170200060");
			userServiceDao.saveObject(devMonitor);
		}
	}

	@Override
	public List<String> getMessageList(String uhid) throws InicuDatabaseExeption {
		String getMessageListQuery = "SELECT n.uhid,n.notificationid,n.message,n.messagetype,n.notificationdate FROM notification_detail n WHERE n.uhid = '"
				+ uhid + "'";
		List result = userServiceDao.executeNativeQuery(getMessageListQuery);
		return result;
	}

	@Override
	public InfinityDeviceJsonObject getInifityDevices(String uhid, String bedid) {

		InfinityDeviceJsonObject obj = new InfinityDeviceJsonObject();

		// fetch if previous entry has already been made.
		String query = "SELECT obj FROM Hl7DeviceMapping as obj WHERE inicuBedid = '" + bedid + "'"
				+ "AND isactive='true'";
		List<Hl7DeviceMapping> resultSet = userServiceDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			Hl7DeviceMapping hl7devMap = resultSet.get(0);
			if (!BasicUtils.isEmpty(hl7devMap)) {
				obj.setIcu(hl7devMap.getHl7Roomid());
				obj.setHl7Bed(hl7devMap.getHl7Bedid());
			}
		}

		obj.setUhid(uhid);
		obj.setBedid(bedid);

		// set all beds of the patient.
		String fetchDevices = "SELECT obj FROM Hl7DeviceMapping as obj WHERE uhid = '" + uhid
				+ "' order by creationtime desc";
		List<Hl7DeviceMapping> deviceList = userServiceDao.getListFromMappedObjNativeQuery(fetchDevices);
		if (!BasicUtils.isEmpty(deviceList)) {
			obj.setPreviousBedList(deviceList);

		}

		if (!BasicUtils.isEmpty(HL7Constants.bedMap)) {

			List<KeyValueObj> icuList = new ArrayList<>();
			HashMap<String, List<String>> listOfBeds = new HashMap<>();

			for (String roomName : HL7Constants.bedMap.keySet()) {

				// set room list
				KeyValueObj keyVal = new KeyValueObj();
				keyVal.setKey(roomName);
				keyVal.setValue(roomName);
				icuList.add(keyVal);
				listOfBeds.put(roomName, HL7Constants.bedMap.get(roomName));
			}

			obj.setIcuDropDown(icuList);
			obj.setBedlist(listOfBeds);
		}

		// KeyValueObj keyVal1 = new KeyValueObj();
		// keyVal1.setKey("SICU");
		// keyVal1.setValue("SICU");
		//
		// KeyValueObj keyVal2 = new KeyValueObj();
		// keyVal2.setKey("PICU");
		// keyVal2.setValue("PICU");
		//
		// KeyValueObj keyVal3 = new KeyValueObj();
		// keyVal3.setKey("PCICU");
		// keyVal3.setValue("PCICU");
		//
		// List<KeyValueObj> icuList = new ArrayList<>();
		// icuList.add(keyVal1);
		// icuList.add(keyVal2);
		// icuList.add(keyVal3);
		//
		// obj.setIcuDropDown(icuList);
		//
		// HashMap<String,List<String>> listofBeds = new HashMap<>();
		// List<String> sicuBeds = new ArrayList<>();
		//
		// sicuBeds.add("BED001");
		// sicuBeds.add("BED002");
		// sicuBeds.add("BED003");
		//
		// listofBeds.put("SICU", sicuBeds);
		//
		// List<String> picuBeds = new ArrayList<>();
		//
		// picuBeds.add("BED006");
		// picuBeds.add("BED007");
		// picuBeds.add("BED009");
		//
		// listofBeds.put("PICU", picuBeds);
		//
		// obj.setBedlist(listofBeds);

		return obj;
	}

	@Override
	public ResponseMessageObject saveInifinityDeviceMapping(InfinityDeviceJsonObject bedDetail) {
		ResponseMessageObject rObj = new ResponseMessageObject();
		rObj.setMessage("saved successfully!");
		rObj.setType(BasicConstants.MESSAGE_SUCCESS);

		try {

			String query = "SELECT obj FROM Hl7DeviceMapping as obj WHERE hl7Bedid='" + bedDetail.getHl7Bed() + "'"
					+ " AND hl7Roomid='" + bedDetail.getIcu() + "' AND isactive = 'true'";
			List<Hl7DeviceMapping> result = userServiceDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(result)) {
				rObj.setMessage("This bed is already assigned.");
				rObj.setType("failure");
				return rObj;
			}

			// boolean update = false;
			Hl7DeviceMapping hl7DeviceMapping = new Hl7DeviceMapping();

			/*
			 * always new entry needs to be created.
			 */

			// //fetch weather data needs to be updated or created.
			// if(!BasicUtils.isEmpty(bedDetail.getBedid())){
			// String query = "SELECT obj FROM Hl7DeviceMapping as obj WHERE
			// inicuBedid='"+bedDetail.getBedid()+"'";
			// List<Hl7DeviceMapping> resultSet =
			// userServiceDao.getListFromMappedObjNativeQuery(query);
			// if(!BasicUtils.isEmpty(resultSet)){
			// Hl7DeviceMapping hl7DevMapp = resultSet.get(0);
			// hl7DeviceMapping.setCreationtime(hl7DevMapp.getCreationtime());
			// hl7DeviceMapping.setDevicemappingid(hl7DevMapp.getDevicemappingid());
			//// update = true;
			// }
			// }

			hl7DeviceMapping.setIsactive(true);
			if (!BasicUtils.isEmpty(bedDetail.getHl7Bed())) {

				hl7DeviceMapping.setHl7Bedid(bedDetail.getHl7Bed());
			}

			if (!BasicUtils.isEmpty(bedDetail.getIcu())) {

				hl7DeviceMapping.setHl7Roomid(bedDetail.getIcu());
			}

			if (!BasicUtils.isEmpty(bedDetail.getUhid())) {
				hl7DeviceMapping.setUhid(bedDetail.getUhid());
			}

			if (!BasicUtils.isEmpty(bedDetail.getBedid())) {

				hl7DeviceMapping.setInicuBedid(bedDetail.getBedid());

				String getRoomId = "SELECT obj FROM RefBed as obj WHERE bedid='" + bedDetail.getBedid() + "'";
				List<RefBed> resultSet = userServiceDao.getListFromMappedObjNativeQuery(getRoomId);

				if (!BasicUtils.isEmpty(resultSet)) {
					RefBed bed = resultSet.get(0);
					if (!BasicUtils.isEmpty(bed)) {
						if (!BasicUtils.isEmpty(bed.getRoomid())) {
							hl7DeviceMapping.setInicuRoomid(bed.getRoomid());
						}
					}
				}

			}

			userServiceDao.saveObject(hl7DeviceMapping);

		} catch (Exception e) {
			e.printStackTrace();
			rObj.setMessage("Oops! could not save.");
			rObj.setType(BasicConstants.MESSAGE_FAILURE);
		}
		return rObj;
	}

	@Override
	public HashMap<String, String> getAddSettingPreference() {
		HashMap<String, String> obj = new HashMap<>();
		String type = "";
		String query = "SELECT prefid FROM preference WHERE preference='" + BasicConstants.PREFERENCE_DEVICE + "'";
		List<String> result = userServiceDao.executeNativeQuery(query);
		if (!BasicUtils.isEmpty(result)) {
			String prefId = result.get(0);
			if (!BasicUtils.isEmpty(prefId)) {
				String fetchType = "SELECT refvalue FROM setting_reference WHERE " + "refid='" + prefId + "' "
						+ "AND category='" + BasicConstants.PREFERENCE_DEVICE + "'";

				List<String> resultSet = userServiceDao.executeNativeQuery(fetchType);
				if (!BasicUtils.isEmpty(resultSet)) {
					type = resultSet.get(0);
				}
			}
		}
		obj.put("integration_type", type);
		return obj;
	}

	@Override
	public List<DashboardJSON> searchPatients(DashboardSearchFilterObj filterobj) {

		try {
			List<DashboardJSON> searchedPatients = getDashboard(filterobj, "");
			return searchedPatients;
		} catch (InicuDatabaseExeption e) {
			e.printStackTrace();
			InicuDatabaseExeption.createExceptionObject(e.getCause() + "", "", filterobj.getSearch_text(),
					e.getMessage(), "");
			return new ArrayList<DashboardJSON>();
		}

	}

	@Override
	public void disconnectInfinityDevice(InfinityDeviceJsonObject bedDetail) {
		Timestamp timeto = new Timestamp(System.currentTimeMillis());
		if (!BasicUtils.isEmpty(bedDetail)) {
			String query = "update hl7_device_mapping " + "Set isactive = false, time_to='" + timeto + "' "
					+ "WHERE inicu_bedid = '" + bedDetail.getBedid() + "' " + "AND hl7_bedid = '"
					+ bedDetail.getHl7Bed() + "' " + "AND hl7_roomid = '" + bedDetail.getIcu() + "'";

			try {
				userServiceDao.updateOrDelQuery(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<DashboardJSON> getDashboardUser(DashboardSearchFilterObj filterObj, String uhId)
			throws InicuDatabaseExeption {
		List<DashboardJSON> listOfDashJson = new ArrayList<>();
		List<Object[]> listOfBed = null;
		List<DashboardDeviceDetailView> listOfDashboard = userServiceDao.getDashboardListUser(filterObj, uhId);

		try {
			listOfBed = userServiceDao.executeNativeQuery("Select bedid,bedname,roomid FROM ref_bed");
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "defaultuser",
					"", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		if (!BasicUtils.isEmpty(listOfDashboard)) {
			for (DashboardDeviceDetailView d : listOfDashboard) {
				DashboardJSON dash = getDashBoardObj(d, listOfBed);
				listOfDashJson.add(dash);
			}
		}
		return listOfDashJson;
	}

	@Override
	public List<DashboardJSON> getReadmitChildData(DashboardSearchFilterObj filterObj, String uhId)
			throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		List<DashboardJSON> listOfDashJson = new ArrayList<>();
		List<Object[]> listOfBed = null;
		List<ReAdmitChildDetailView> listOfDashboard = userServiceDao.getReadmitChildList(filterObj, uhId);

		try {
			listOfBed = userServiceDao.executeNativeQuery("Select bedid,bedname,roomid FROM ref_bed");
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "defaultuser",
					"", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		if (!BasicUtils.isEmpty(listOfDashboard)) {
			for (ReAdmitChildDetailView d : listOfDashboard) {
				DashboardJSON dash = new DashboardJSON();

				// for(Object[] obj : listOfBed)
				// if(obj[0].toString().equals(d.getBedId()))
				// System.out.println("bed "+d.getBedNo()+" room id
				// "+obj[2].toString());

				java.util.Date date = d.getAdmitDate();
				if (!BasicUtils.isEmpty(date)) {
					try {
						// dash.setAdmitDate(sdf.format(date));
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						java.util.Date dateUtil = cal.getTime();
						String dateStr = CalendarUtility.getDateformatutf(CalendarUtility.CLIENT_CRUD_OPERATION)
								.format(dateUtil);
						dash.setAdmitDate(dateStr);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String comments = d.getComment();
				if (!BasicUtils.isEmpty(comments)) {
					dash.setComment(comments);
				}

				String bedNo = d.getBedNo();
				String bedId = d.getBedId();
				// String bedId = d.get
				if (!BasicUtils.isEmpty(bedNo)) {
					// set bed objects
					BedKeyValueObj bedObj = new BedKeyValueObj();
					try {
						String[] arr = bedNo.split("_");
						if (arr.length > 1) {
							bedObj.setBedNo(Integer.parseInt(arr[1]));
						}

						for (Object[] objarr : listOfBed) {
							if (objarr[0].toString().equalsIgnoreCase(bedId)) {
								bedObj.setKey(objarr[0].toString());
								bedObj.setValue(bedNo);
								// fetch roomid and value
								KeyValueObj room = new KeyValueObj();
								room.setKey(objarr[2].toString());
								// room.setValue("");

								// search room name from room id
								try {
									List resultSet = userServiceDao
											.executeNativeQuery("Select roomname FROM ref_room WHERE roomid='"
													+ objarr[2].toString().trim() + "'");
									if (!BasicUtils.isEmpty(resultSet)) {
										room.setValue(resultSet.get(0).toString());
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								dash.setRoom(room);
								break;
							}
						}

						dash.setBed(bedObj);
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
								"test", "", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
					}
				}
				/*
				 * Boolean blood = d.isBlood(); if(!BasicUtils.isEmpty(blood)){
				 * dash.setBlood(blood); }
				 */

				String condition = d.getCondition();
				if (!BasicUtils.isEmpty(condition)) {
					dash.setCondition(condition);
				}

				/*
				 * Boolean ecg = d.isEcg(); if(!BasicUtils.isEmpty(ecg)){ dash.setEcg(ecg); }
				 */

				Boolean isvacant = d.isAvailable();
				if (!BasicUtils.isEmpty(isvacant)) {
					dash.setIsvacant(isvacant);
				}

				String level = d.getPatientLevel();
				if (!BasicUtils.isEmpty(level)) {
					String[] arr = level.split("-");
					if (arr.length > 1) {
						dash.setLevel(arr[1]);
					}
				}

				Integer messages = d.getTotalMessage();
				if (!BasicUtils.isEmpty(messages)) {
					dash.setMessages(messages);
				}

				/*
				 * Boolean oxygen = d.isOxygen(); if(!BasicUtils.isEmpty(oxygen)){
				 * dash.setOxygen(oxygen); }
				 */

				String uhid = d.getUhid();
				if (!BasicUtils.isEmpty(uhid)) {
					dash.setUhid(uhid);
				}

				String name = d.getBabyName();
				if (!BasicUtils.isEmpty(name)) {
					dash.setName(name);
				}

				String birthWeight = d.getBirthweight();
				if (!BasicUtils.isEmpty(birthWeight)) {
					dash.setBirthWeight(birthWeight);
				}

				String gestation = d.getGestation();
				if (!BasicUtils.isEmpty(gestation)) {
					dash.setGestation(gestation);
				}

				java.util.Date dateBirth = d.getDateofbirth();
				if (!BasicUtils.isEmpty(dateBirth)) {
					try {
						dash.setDob(sdf.format(dateBirth));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String gender = d.getGender();
				if (!BasicUtils.isEmpty(gender)) {
					dash.setGender(gender);
				}

				String roomname = d.getRoomname();
				if (!BasicUtils.isEmpty(roomname)) {
					dash.setBabyRoom(roomname);
				}

				float workingWeight = 0;
				String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='" + uhid
						+ "';";
				try {
					List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
					if (!BasicUtils.isEmpty(resultSet)) {
						Float maxWeight = resultSet.get(0);
						if (!BasicUtils.isEmpty(maxWeight)) {
							dash.setCurrentdateweight(maxWeight);
							dash.setWorkingWeight(maxWeight);
							workingWeight = maxWeight;
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "test",
							"", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(ex));
				}

				String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid
						+ "' and visitdate='" + new java.sql.Date(new java.util.Date().getTime()) + "'";
				List<BabyVisit> currentBabyVisitList = userServiceDao
						.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
				if (!BasicUtils.isEmpty(currentBabyVisitList)) {
					dash.setTodayWeight(currentBabyVisitList.get(0).getCurrentdateweight());
				}

				if (!dash.isIsvacant() && dash.getUhid() != null) {
					// query to fetch if devices are connected or not.

					String query = "select devicetype from bed_device_history where uhid = '" + dash.getUhid().trim()
							+ "';";
					List<String> resultSet = userServiceDao.executeNativeQuery(query);
					if (!BasicUtils.isEmpty(resultSet)) {
						for (String s : resultSet) {
							if (s.equalsIgnoreCase("monitor")) {
								dash.setEcg(true);
							} else if (s.equalsIgnoreCase("ventilator")) {
								dash.setOxygen(true);
							}
						}
					}
				}

				// set device details
				// rounding off values.

				try {

					if (!BasicUtils.isEmpty(d.getHeartRate())) {
						String hrStr = d.getHeartRate();
						double hr = Math.round(Float.valueOf(hrStr) * 10.0) / 10.0;
						dash.setHR(String.valueOf(hr));
					}

					if (!BasicUtils.isEmpty(d.getSp02())) {
						String spo2Str = d.getSp02();
						double spo2 = Math.round(Float.valueOf(spo2Str) * 10.0 / 10.0);
						dash.setSPO2(String.valueOf(spo2));
					}

					if (!BasicUtils.isEmpty(d.getRespRate())) {
						String respRateStr = d.getRespRate();
						double resprate = Math.round(Float.valueOf(respRateStr) * 10.0 / 10.0);
						dash.setRR(String.valueOf(resprate));
					}

					if (!BasicUtils.isEmpty(d.getPulseRate())) {
						String pulseRateStr = d.getPulseRate();
						double pulseRate = Math.round(Float.valueOf(pulseRateStr) * 10.0 / 10.0);
						dash.setPR(String.valueOf(pulseRate));
					}

					if (!BasicUtils.isEmpty(d.getCreationtime())) {
						dash.setDeviceTime(d.getCreationtime().getTime());

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				listOfDashJson.add(dash);
			}
		}
		return listOfDashJson;
	}

	@Override
	public List<WeightTrackingJSON> getWeightTrackingData(String branchName) throws InicuDatabaseExeption {

		List<WeightTrackingJSON> listTrackingWeight = new ArrayList<>();
		java.util.Date todayDate = null;
		java.util.Date yesterdayDate = null;
		String todayDateStr = "";
		String yesterdayDateStr = "";
		HashMap<String,WeightTrackingJSON> WeightTrackingMap=new HashMap<>();

		int i = 0;
		try {
			todayDate = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			todayDateStr = sdf.format(todayDate);

			yesterdayDate = new java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
			yesterdayDateStr = sdf.format(yesterdayDate);

			String fecthBabyDetails = HqlSqlQueryConstants.getActiveBabies(branchName);
			List<BabyDetail> listBabyDetails = userServiceDao.getListFromMappedObjNativeQuery(fecthBabyDetails);

			if (listBabyDetails != null && !listBabyDetails.isEmpty()) {

				// New Code Added By Umesh
				String uhidList="";
				String bedid="";
				String roomid="";

				for(BabyDetail babyObj:listBabyDetails){
					// Store Uhid
					if (uhidList.isEmpty()) {
						uhidList += "'" + babyObj.getUhid() + "'";
					} else {
						uhidList += ", '" + babyObj.getUhid() + "'";
					}

					// Store bedId
					if (bedid.isEmpty()) {
						bedid += "'" + babyObj.getNicubedno() + "'";
					} else {
						bedid += ", '" + babyObj.getNicubedno() + "'";
					}

					//Store Roomid
					if (roomid.isEmpty()) {
						roomid += "'" + babyObj.getNicuroomno() + "'";
					} else {
						roomid += ", '" + babyObj.getNicuroomno() + "'";
					}

				}

				String fetchRefBed =HqlSqlQueryConstants.getRefBed(bedid,branchName);
				List<Object[]> listRefBed = userServiceDao.executeNativeQuery(fetchRefBed);
				HashMap<String, String> refBedHash = getRefKeyValueObject(listRefBed);

				String fetchRefRoom =HqlSqlQueryConstants.getRefRoom(roomid,branchName);
				List<Object[]> listRefRoom = userServiceDao.executeNativeQuery(fetchRefRoom);
				HashMap<String, String> refRoomHash = getRefKeyValueObject(listRefRoom);

				Iterator<BabyDetail> babyDetailsIterator = listBabyDetails.iterator();

				while (babyDetailsIterator.hasNext()) {

					BabyDetail babyDetail = babyDetailsIterator.next();

					long diff = todayDate.getTime() - babyDetail.getDateofbirth().getTime();
					long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;

					WeightTrackingJSON weightTracking = new WeightTrackingJSON();

					weightTracking.setUhid(babyDetail.getUhid());
					weightTracking.setBabyname(babyDetail.getBabyname());
					weightTracking.setBabyType(babyDetail.getBabyType());
					weightTracking.setBabyNumber(babyDetail.getBabyNumber());
					weightTracking.setDateofbirth(babyDetail.getDateofbirth().toString());
					weightTracking.setNicuRoom(refRoomHash.get(babyDetail.getNicuroomno().trim()));
					weightTracking.setBedno(refBedHash.get(babyDetail.getNicubedno().trim()));

					if (babyDetail.getGestationweekbylmp() != null && babyDetail.getGestationdaysbylmp() != null) {
						weightTracking.setGestationdaysbylmp(babyDetail.getGestationdaysbylmp());
						weightTracking.setGestationweekbylmp(babyDetail.getGestationweekbylmp());
					}

					String age = BasicUtils.getBabyAge(todayDate, babyDetail.getDateofbirth());
					weightTracking.setAge(age);

					/*String dol = BasicUtils.getDayoflife(babyDetail.getDateofbirth().toString());
					weightTracking.setDol(dol);*/
					BasicUtils.setAnthropometryTrackerFields(weightTracking, babyDetail);

					String nicudays = BasicUtils.getBabyAge(todayDate, babyDetail.getDateofadmission());
					weightTracking.setNicudays(nicudays);

					if (babyDetail.getBirthweight() != null) {
						weightTracking.setBirthWeight(babyDetail.getBirthweight());
					}

					if (babyDetail.getGestationweekbylmp() != null && babyDetail.getGestationdaysbylmp() != null) {
						weightTracking.setGaAtBirth(
								(babyDetail.getGestationweekbylmp()) + "," + (babyDetail.getGestationdaysbylmp()));
					} else {
						weightTracking.setGaAtBirth(",");
					}

					if (weightTracking.getTodayWeight() != null && weightTracking.getLastWeight() != null) {
						weightTracking
								.setWeightChange(weightTracking.getTodayWeight() - weightTracking.getLastWeight());

						if (weightTracking.getWeightChange().floatValue() > 0) {
							weightTracking.setWeightIncrement(true);
						} else {
							weightTracking.setWeightIncrement(false);
						}
					}
					i++;
					WeightTrackingMap.put(babyDetail.getUhid(),weightTracking);
				}

				// Weight for Calculation
				String queryChildWeightForCal =HqlSqlQueryConstants.getBabiesWeightForCal(uhidList);
				List<Object[]> resultWeightForCalSet = userServiceDao.executeNativeQuery(queryChildWeightForCal);

				if(!BasicUtils.isEmpty(resultWeightForCalSet)) {
					for(Object[] obj:resultWeightForCalSet){
						String tempUhid=obj[1].toString();
						WeightTrackingJSON currentObject=WeightTrackingMap.get(tempUhid);
						if(currentObject!=null){
							currentObject.setWeightForCal(Float.parseFloat(obj[0].toString()));
							WeightTrackingMap.replace(tempUhid, currentObject);
						}
					}
				}

				String fetchBabyVisitAnthro =HqlSqlQueryConstants.getBabyVisitAnthropometryList(uhidList);
				List<BabyVisit> listBabyVisitAnthro = userServiceDao.getListFromMappedObjNativeQuery(fetchBabyVisitAnthro);

				if (listBabyVisitAnthro != null && !listBabyVisitAnthro.isEmpty()) {

					Iterator<BabyVisit> babyVisitIterator = listBabyVisitAnthro.iterator();
					while (babyVisitIterator.hasNext()) {

						BabyVisit babyVisit = babyVisitIterator.next();
						WeightTrackingJSON currentObject=WeightTrackingMap.get(babyVisit.getUhid());
						long diff = Long.parseLong(BasicUtils.getDayoflife(currentObject.getDateofbirth()));
						long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;

						// Head Circumference
						if (sdf.format(babyVisit.getVisitdate()).equals(todayDateStr)) {
							if (!BasicUtils.isEmpty(babyVisit.getCurrentdateheadcircum())
									&& babyVisit.getCurrentdateheadcircum() != null) {
								currentObject.setCurrentdateheadcircum(babyVisit.getCurrentdateheadcircum());
							}
							if (!BasicUtils.isEmpty(babyVisit.getCurrentdateheight())
									&& babyVisit.getCurrentdateheight() != null) {
								currentObject.setCurrentdateheight(babyVisit.getCurrentdateheight());
							}
						}

						// Current DateWeight is not Null

                        if (babyVisit.getVisitdate()!=null && sdf.format(babyVisit.getVisitdate()).equals(todayDateStr)
								&& babyVisit.getCurrentdateweight() != null
								&& currentObject.getTodayWeight() == null) {
							currentObject.setTodayWeight(babyVisit.getCurrentdateweight());

							String previousBabyVisit = HqlSqlQueryConstants.getPreviousBabyVisit(babyVisit.getUhid(), yesterdayDateStr);
							List<BabyVisit> resultPreviousBabyVisit = userServiceDao.getListFromMappedObjNativeQuery(previousBabyVisit);
							if(resultPreviousBabyVisit != null && !resultPreviousBabyVisit.isEmpty()) {
								float previousWeight = resultPreviousBabyVisit.get(0).getCurrentdateweight();
								float latestWeight = babyVisit.getCurrentdateweight();
								if(!BasicUtils.isEmpty(latestWeight) && !BasicUtils.isEmpty(previousWeight)) {
									float weightChange = latestWeight - previousWeight;
									currentObject.setWeightChange(weightChange);
									if(weightChange > 0){
										currentObject.setWeightIncrement(true);
									} else {
										currentObject.setWeightIncrement(false);
									}
								}
							}
						} else if (babyVisit.getVisitdate()!=null && babyVisit.getCurrentdateweight()!= null && sdf.format(babyVisit.getVisitdate()).equals(yesterdayDateStr)
								&& babyVisit.getCurrentdateweight() != 0
								&& currentObject.getLastWeight() == null) {
							currentObject.setLastWeight(babyVisit.getCurrentdateweight());
						}

						// GA
						if (null == currentObject.getCorrectedGa() && babyVisit.getCorrectedGa() != null) {
							String[] correctedGAArr = babyVisit.getCorrectedGa().split(",");
							if (correctedGAArr.length >= 2) {
								currentObject.setCorrectedGa((Integer.parseInt(correctedGAArr[0]) + (ageDays / 7))
										+ "," + (Integer.parseInt(correctedGAArr[1]) + (ageDays % 7)));
							} else if (correctedGAArr.length == 1) {
								currentObject.setCorrectedGa((Integer.parseInt(correctedGAArr[0]) + (ageDays / 7))
										+ "," + (ageDays % 7));
							} else if (correctedGAArr.length == 0) {
								currentObject.setCorrectedGa(",");
							}
						}

						WeightTrackingMap.replace(babyVisit.getUhid(), currentObject);
					}


					for (Map.Entry<String,WeightTrackingJSON> entry : WeightTrackingMap.entrySet()) {
						WeightTrackingJSON weightTrackingObj = entry.getValue();
						if(!BasicUtils.isEmpty(weightTrackingObj.getUhid())) {
							listTrackingWeight.add(weightTrackingObj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "defaultuser",
					"", "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}
		return listTrackingWeight;
	}

	@Override
	public ResponseMessageWithResponseObject saveWeightTrackingData(List<WeightTrackingJSON> weightTrackingList,
																	String loggedUser, String branchName) {
		// TODO Auto-generated method stub
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		java.util.Date todayDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayDateStr = sdf.format(todayDate);

		try {
			Iterator<WeightTrackingJSON> weightTrackingIterator = weightTrackingList.iterator();
			while (weightTrackingIterator.hasNext()) {
				WeightTrackingJSON weightTrackingObj = weightTrackingIterator.next();
				if (weightTrackingObj.getTodayWeight() != null) {

					String fetchLastbabyVisit = "Select obj from BabyVisit obj where uhid='"
							+ weightTrackingObj.getUhid() + "' and visitdate='" + todayDateStr
							+ "' order by creationtime desc";
					List<BabyVisit> listBabyVisit = userServiceDao.getListFromMappedObjNativeQuery(fetchLastbabyVisit);

					java.sql.Date sqlPresentDate = null;
					java.sql.Time sqlPresentTime = null;
					Timestamp currTimestamp = new Timestamp(System.currentTimeMillis() + offset);
//						entryDate = entryDate.substring(0, 24);
//						String entryTime = entryDate.substring(16, 22);
//						entryTime = entryTime + "00";
//						DateFormat readFormat = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss");
//						java.util.Date presentDate = readFormat.parse(entryDate);
					sqlPresentDate = new Date(currTimestamp.getTime());

//						DateFormat readFormatTime = new SimpleDateFormat("HH:mm:ss");
//						java.util.Date presentTime = readFormatTime.parse(entryTime);
					sqlPresentTime = new java.sql.Time(currTimestamp.getTime());


					if (listBabyVisit != null && listBabyVisit.size() > 0) {
						if (listBabyVisit.get(0).getCurrentdateweight() != null) {
							if (listBabyVisit.get(0).getCurrentdateweight().floatValue() != weightTrackingObj
									.getTodayWeight().floatValue()) {
								// BabyVisit babyVisit = new BabyVisit();
								BabyVisit babyVisit = listBabyVisit.get(0);
								// babyVisit.setUhid(weightTrackingObj.getUhid());
								// babyVisit.setLoggeduser(loggedUser);
								// babyVisit.setVisitdate(new java.util.Date());
								babyVisit.setCurrentdateweight(weightTrackingObj.getTodayWeight());
								// babyVisit.setCurrentage(weightTrackingObj.getAge());
								// babyVisit.setNicuday(weightTrackingObj.getNicudays());

								// babyVisit.setGaAtBirth(weightTrackingObj.getGaAtBirth());
								// babyVisit.setCorrectedGa(weightTrackingObj.getCorrectedGa());

								if (weightTrackingObj.getCurrentdateheadcircum() != null) {
									babyVisit.setCurrentdateheadcircum(weightTrackingObj.getCurrentdateheadcircum());
								}

								if (weightTrackingObj.getCurrentdateheight() != null) {
									babyVisit.setCurrentdateheight(weightTrackingObj.getCurrentdateheight());
								}

								if (weightTrackingObj.getGestationDays() != null) {
									babyVisit.setGestationDays(weightTrackingObj.getGestationDays());
								}

								if (weightTrackingObj.getGestationWeek() != null) {
									babyVisit.setGestationWeek(weightTrackingObj.getGestationWeek());
								}

								/*if (!BasicUtils.isEmpty(weightTrackingObj.getWeightForCal())) {
									babyVisit.setWeightForCal(weightTrackingObj.getWeightForCal());
								}*/

								String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='"
										+ weightTrackingObj.getUhid() + "';";
								List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
								if (!BasicUtils.isEmpty(resultSet)) {
									Float maxWeight = resultSet.get(0);
									if (!BasicUtils.isEmpty(maxWeight)) {
										if (maxWeight.floatValue() > weightTrackingObj.getTodayWeight().floatValue()) {
											babyVisit.setWorkingweight(maxWeight);
											babyVisit.setWeightForCal(maxWeight);
										} else {
											babyVisit.setWorkingweight(weightTrackingObj.getTodayWeight());
											babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
										}
									}
								}
								babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis() + offset));
								babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
								babyVisit.setVisitdate(sqlPresentDate);
								babyVisit.setVisittime(sqlPresentTime);
								userServiceDao.saveObject(babyVisit);
							} else {
								BabyVisit babyVisit = listBabyVisit.get(0);
								babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis() + offset));
								babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
								babyVisit.setVisitdate(sqlPresentDate);
								babyVisit.setVisittime(sqlPresentTime);
								if (weightTrackingObj.getCurrentdateheadcircum() != null) {
									babyVisit.setCurrentdateheadcircum(weightTrackingObj.getCurrentdateheadcircum());
								}

								if (weightTrackingObj.getCurrentdateheight() != null) {
									babyVisit.setCurrentdateheight(weightTrackingObj.getCurrentdateheight());
								}
								if (weightTrackingObj.getGestationDays() != null) {
									babyVisit.setGestationDays(weightTrackingObj.getGestationDays());
								}

								if (weightTrackingObj.getGestationWeek() != null) {
									babyVisit.setGestationWeek(weightTrackingObj.getGestationWeek());
								}

								/*if (!BasicUtils.isEmpty(weightTrackingObj.getWeightForCal())) {
									babyVisit.setWeightForCal(weightTrackingObj.getWeightForCal());
								}*/

								String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='"
										+ weightTrackingObj.getUhid() + "';";
								List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
								if (!BasicUtils.isEmpty(resultSet)) {
									Float maxWeight = resultSet.get(0);
									if (!BasicUtils.isEmpty(maxWeight)) {
										if (maxWeight.floatValue() > weightTrackingObj.getTodayWeight().floatValue()) {
											babyVisit.setWeightForCal(maxWeight);
										} else {
											babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
										}
									}
								} else {
									babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
								}

								userServiceDao.saveObject(babyVisit);
							}
						} else {
							// BabyVisit babyVisit = new BabyVisit();
							BabyVisit babyVisit = listBabyVisit.get(0);
							// babyVisit.setUhid(weightTrackingObj.getUhid());
							// babyVisit.setLoggeduser(loggedUser);
							// babyVisit.setVisitdate(new java.util.Date());
							babyVisit.setCurrentdateweight(weightTrackingObj.getTodayWeight());
							// babyVisit.setCurrentage(weightTrackingObj.getAge());
							// babyVisit.setNicuday(weightTrackingObj.getNicudays());

							// babyVisit.setGaAtBirth(weightTrackingObj.getGaAtBirth());
							// babyVisit.setCorrectedGa(weightTrackingObj.getCorrectedGa());

							if (weightTrackingObj.getCurrentdateheadcircum() != null) {
								babyVisit.setCurrentdateheadcircum(weightTrackingObj.getCurrentdateheadcircum());
							}

							if (weightTrackingObj.getCurrentdateheight() != null) {
								babyVisit.setCurrentdateheight(weightTrackingObj.getCurrentdateheight());
							}
							if (weightTrackingObj.getGestationDays() != null) {
								babyVisit.setGestationDays(weightTrackingObj.getGestationDays());
							}

							if (weightTrackingObj.getGestationWeek() != null) {
								babyVisit.setGestationWeek(weightTrackingObj.getGestationWeek());
							}

							/*if (!BasicUtils.isEmpty(weightTrackingObj.getWeightForCal())) {
								babyVisit.setWeightForCal(weightTrackingObj.getWeightForCal());
							}*/

							String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='"
									+ weightTrackingObj.getUhid() + "';";
							List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
							if (!BasicUtils.isEmpty(resultSet)) {
								Float maxWeight = resultSet.get(0);
								if (!BasicUtils.isEmpty(maxWeight)) {
									if (maxWeight.floatValue() > weightTrackingObj.getTodayWeight().floatValue()) {
										babyVisit.setWorkingweight(maxWeight);
										babyVisit.setWeightForCal(maxWeight);
									} else {
										babyVisit.setWorkingweight(weightTrackingObj.getTodayWeight());
										babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
									}
								}
							}
							babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis() + offset));
							babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
							babyVisit.setVisitdate(sqlPresentDate);
							babyVisit.setVisittime(sqlPresentTime);
							userServiceDao.saveObject(babyVisit);
						}
					} else {
						BabyVisit babyVisit = new BabyVisit();
						babyVisit.setUhid(weightTrackingObj.getUhid());
						babyVisit.setLoggeduser(loggedUser);
						babyVisit.setVisitdate(new java.util.Date());
						babyVisit.setCurrentdateweight(weightTrackingObj.getTodayWeight());
						babyVisit.setCurrentage(weightTrackingObj.getNicudays());
						babyVisit.setNicuday(weightTrackingObj.getNicudays());

						babyVisit.setGaAtBirth(weightTrackingObj.getGaAtBirth());
						babyVisit.setCorrectedGa(weightTrackingObj.getCorrectedGa());

						if (weightTrackingObj.getCurrentdateheadcircum() != null) {
							babyVisit.setCurrentdateheadcircum(weightTrackingObj.getCurrentdateheadcircum());
						}

						if (weightTrackingObj.getCurrentdateheight() != null) {
							babyVisit.setCurrentdateheight(weightTrackingObj.getCurrentdateheight());
						}
						if (weightTrackingObj.getGestationDays() != null) {
							babyVisit.setGestationDays(weightTrackingObj.getGestationDays());
						}

						if (weightTrackingObj.getGestationWeek() != null) {
							babyVisit.setGestationWeek(weightTrackingObj.getGestationWeek());
						}

						/*if (!BasicUtils.isEmpty(weightTrackingObj.getWeightForCal())) {
							babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
						}*/

						String queryChildMaxWeight = "select Max(currentdateweight) from baby_visit obj where uhid ='"
								+ weightTrackingObj.getUhid() + "';";
						List<Float> resultSet = userServiceDao.executeNativeQuery(queryChildMaxWeight);
						if (!BasicUtils.isEmpty(resultSet)) {
							Float maxWeight = resultSet.get(0);
							if (!BasicUtils.isEmpty(maxWeight)) {
								if (maxWeight.floatValue() > weightTrackingObj.getTodayWeight().floatValue()) {
									babyVisit.setWorkingweight(maxWeight);
									babyVisit.setWeightForCal(maxWeight);
								} else {
									babyVisit.setWorkingweight(weightTrackingObj.getTodayWeight());
									babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
								}
							} else {
								babyVisit.setWorkingweight(weightTrackingObj.getTodayWeight());
								babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
							}
						} else {
							babyVisit.setWorkingweight(weightTrackingObj.getTodayWeight());
							babyVisit.setWeightForCal(weightTrackingObj.getTodayWeight());
						}
						babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis() + offset));
						babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
						babyVisit.setVisitdate(sqlPresentDate);
						babyVisit.setVisittime(sqlPresentTime);
						userServiceDao.saveObject(babyVisit);
					}
				} else {
					String fetchLastbabyVisit = "Select obj from BabyVisit obj where uhid='"
							+ weightTrackingObj.getUhid() + "' and visitdate='" + todayDateStr
							+ "' order by creationtime desc";
					List<BabyVisit> listBabyVisit = userServiceDao.getListFromMappedObjNativeQuery(fetchLastbabyVisit);
					if (listBabyVisit != null && listBabyVisit.size() > 0) {
						BabyVisit babyVisit = listBabyVisit.get(0);
						if (weightTrackingObj.getCurrentdateheadcircum() != null) {
							babyVisit.setCurrentdateheadcircum(weightTrackingObj.getCurrentdateheadcircum());
						}

						if (weightTrackingObj.getCurrentdateheight() != null) {
							babyVisit.setCurrentdateheight(weightTrackingObj.getCurrentdateheight());
						}
						if (weightTrackingObj.getGestationDays() != null) {
							babyVisit.setGestationDays(weightTrackingObj.getGestationDays());
						}

						if (weightTrackingObj.getGestationWeek() != null) {
							babyVisit.setGestationWeek(weightTrackingObj.getGestationWeek());
						}

						if (!BasicUtils.isEmpty(weightTrackingObj.getWeightForCal())) {
							babyVisit.setWeightForCal(weightTrackingObj.getWeightForCal());
						}

						babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis() + offset));
						babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
						userServiceDao.saveObject(babyVisit);
					} else {
						if (weightTrackingObj.getCurrentdateheadcircum() != null
								|| weightTrackingObj.getCurrentdateheight() != null) {

							BabyVisit babyVisit = new BabyVisit();
							babyVisit.setUhid(weightTrackingObj.getUhid());
							babyVisit.setLoggeduser(loggedUser);
							babyVisit.setVisitdate(new java.util.Date());
							babyVisit.setCurrentage(weightTrackingObj.getNicudays());
							babyVisit.setNicuday(weightTrackingObj.getNicudays());

							babyVisit.setGaAtBirth(weightTrackingObj.getGaAtBirth());
							babyVisit.setCorrectedGa(weightTrackingObj.getCorrectedGa());

							if (weightTrackingObj.getCurrentdateheadcircum() != null) {
								babyVisit.setCurrentdateheadcircum(weightTrackingObj.getCurrentdateheadcircum());
							}
							if (weightTrackingObj.getCurrentdateheight() != null) {
								babyVisit.setCurrentdateheight(weightTrackingObj.getCurrentdateheight());
							}
							if (weightTrackingObj.getGestationDays() != null) {
								babyVisit.setGestationDays(weightTrackingObj.getGestationDays());
							}

							if (weightTrackingObj.getGestationWeek() != null) {
								babyVisit.setGestationWeek(weightTrackingObj.getGestationWeek());
							}

							if (!BasicUtils.isEmpty(weightTrackingObj.getWeightForCal())) {
								babyVisit.setWeightForCal(weightTrackingObj.getWeightForCal());
							}

							babyVisit.setCreationtime(new Timestamp(System.currentTimeMillis() + offset));
							babyVisit.setModificationtime(new Timestamp(System.currentTimeMillis()));
							userServiceDao.saveObject(babyVisit);
						}
					}

				}
			}
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Weight Tracking Data Saved Successfully..!!");
			response.setReturnedObject(getWeightTrackingData(branchName));
		} catch (Exception e) {
			e.printStackTrace();
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage(e.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "defaultuser",
					"", "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return response;
	}

	public HashMap<String, String> getRefKeyValueObject(List<Object[]> refList) {
		HashMap<String, String> refValueHash = new HashMap<>();
		if (!refList.isEmpty()) {
			Iterator<Object[]> refListIterator = refList.iterator();
			while (refListIterator.hasNext()) {
				Object[] refObj = refListIterator.next();
				refValueHash.put(refObj[0].toString(), refObj[1].toString().substring(refObj[1].toString().length() - 1,
						refObj[1].toString().length()));
			}
		}
		return refValueHash;
	}

	@SuppressWarnings("unchecked")
	private String getCriticality(String uhid, String birthCriticality) {
		RespSupport respSupportObj = null;
		Timestamp tempTime = new Timestamp(System.currentTimeMillis());

		try {
			// Critical : HFO, iNO, Inotropes
			List<RespSupport> respSupportList = (List<RespSupport>) inicuDao
					.getListFromMappedObjRowLimitQuery(HqlSqlQueryConstants.getRespSupportList(uhid, tempTime), 1);
			if (!BasicUtils.isEmpty(respSupportList) && respSupportList.get(0).getIsactive() != null
					&& respSupportList.get(0).getIsactive()) {
				respSupportObj = respSupportList.get(0);
				if (!BasicUtils.isEmpty(respSupportObj.getRsVentType())
						&& respSupportObj.getRsVentType().equalsIgnoreCase("hfo")) {
					return "CRITICAL";
				}
			}

			List<SaRespPphn> iNOList = (List<SaRespPphn>) inicuDao.getListFromMappedObjRowLimitQuery(
					HqlSqlQueryConstants.getRespPphnList(uhid) + " order by creationtime desc", 1);
			if (!BasicUtils.isEmpty(iNOList)) {
				SaRespPphn pphnObj = iNOList.get(0);
				if (pphnObj.getEventstatus().equalsIgnoreCase("yes") && pphnObj.getTreatmentaction() != null
						&& pphnObj.getTreatmentaction().contains("TRE013") && !pphnObj.getPphnIno().isEmpty()) {
					return "CRITICAL";
				}
			}

			List<BabyPrescription> inotropesList = (List<BabyPrescription>) inicuDao
					.getListFromMappedObjRowLimitQuery(HqlSqlQueryConstants.getBabyPrescriptionList(uhid)
							+ " and medicationtype='TYPE0004' and (enddate is null or enddate >'" + tempTime + "')", 1);
			if (!BasicUtils.isEmpty(inotropesList)) {
				return "CRITICAL";
			}

			// Sick : Mechanical Ventilator, Active Sepsis/NEC, Seizures
			if (respSupportObj != null && !BasicUtils.isEmpty(respSupportObj.getRsVentType())
					&& respSupportObj.getRsVentType().equalsIgnoreCase("Mechanical Ventilation")) {
				return "SICK";
			}

			List<SaSepsis> sepsisList = (List<SaSepsis>) inicuDao.getListFromMappedObjRowLimitQuery(
					HqlSqlQueryConstants.getSepsisList(uhid) + " order by creationtime desc", 1);
			if (!BasicUtils.isEmpty(sepsisList)) {
				SaSepsis sepsisObj = sepsisList.get(0);
				if (sepsisObj.getEventstatus().equalsIgnoreCase("yes")) {
					return "SICK";
				}
			}

			List<SaCnsSeizures> seizuresList = (List<SaCnsSeizures>) inicuDao.getListFromMappedObjRowLimitQuery(
					HqlSqlQueryConstants.getSeizuresList(uhid) + " order by creationtime desc", 1);
			if (!BasicUtils.isEmpty(seizuresList)) {
				SaCnsSeizures seizuresObj = seizuresList.get(0);
				if (seizuresObj.getEventstatus().equalsIgnoreCase("yes")) {
					return "SICK";
				}
			}

			// Satisfactory : Non-Invasive respiratory FiO2 < 40%
			if (respSupportObj != null && respSupportObj.getRsFio2() != null) {
				try {
					if (Float.parseFloat(respSupportObj.getRsFio2()) > 40) {
						return "SICK";
					} else {
						return "SATISFACTORY";
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			// Stable : All feeds
			List<BabyfeedDetail> feedList = (List<BabyfeedDetail>) inicuDao.getListFromMappedObjRowLimitQuery(
					HqlSqlQueryConstants.getBabyfeedList(uhid) + " order by creationtime desc", 1);
			if (!BasicUtils.isEmpty(feedList) && feedList.get(0).getIsenternalgiven() != null
					&& feedList.get(0).getIsenternalgiven()) {
				return "STABLE";
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
					"Get Current Criticality", BasicUtils.convertErrorStacktoString(e));
		}
		return birthCriticality;
	}

	@Override
	public ResponseMessageWithResponseObject storeImage(String imageName, String imageData) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		File imageDir = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH);
		if (!imageDir.exists()) {
			System.out.println("creating image directory:");
			try {
				imageDir.mkdir();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!BasicUtils.isEmpty(imageName)) {
			// store image
			try {
				Base64 decoder = new Base64();
				FileOutputStream osf;

				if (!BasicUtils.isEmpty(imageData)) {
					// write images onto the path
					String value = imageData.split("base64,")[1];
					byte[] imgBytes = decoder.decode(value);
					osf = new FileOutputStream(
							new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageName));
					osf.write(imgBytes);
					osf.flush();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		response.setType(BasicConstants.MESSAGE_SUCCESS);
		response.setMessage("Image stored successfully.");
		return response;
	}

	@Override
	public ImagePOJO getImage(String imageName) {// image name with its
		// format...default is .png

		ImagePOJO imageData = new ImagePOJO();
		File fnew = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageName);
		try {
			if (fnew.exists()) {
				BufferedImage image = ImageIO.read(fnew);
				if (!BasicUtils.isEmpty(image)) {
					String encodedStr = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(fnew.toPath()));
					encodedStr = "data:image/png;base64," + encodedStr;
					imageData.setName(imageName);
					imageData.setData(encodedStr);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "default", "",
					"FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
		}

		return imageData;

	}

	@Override
	public boolean isDeviceConnected(String uhid) {
		boolean deviceConnectedFlag = false;

		HashMap<String, String> integrationTypeMap = getAddSettingPreference();
		if (!BasicUtils.isEmpty(integrationTypeMap)) {
			String integrationType = integrationTypeMap.get("integration_type");
			if (!BasicUtils.isEmpty(integrationType)) {
				String deviceConnectedQuery = null;
				if (integrationType.equalsIgnoreCase("infinity")) {
					// for infinity fetch from hl7_device_mapping
					deviceConnectedQuery = "select count(*) from hl7_device_mapping where uhid='" + uhid
							+ "' and isactive='t'";

				} else if (integrationType.equalsIgnoreCase("inicu")) {
					deviceConnectedQuery = "select count(*) from bed_device_detail where uhid='" + uhid
							+ "' and status='t'";
				}
				List<BigInteger> resultSet = userServiceDao.executeNativeQuery(deviceConnectedQuery);
				if (!BasicUtils.isEmpty(resultSet)) {
					deviceConnectedFlag = resultSet.get(0).longValue() > 0;
				}
			}
		}
		return deviceConnectedFlag;
	}

	@Override
	public boolean raiseAlarm(Timestamp ventilatorTime, Timestamp monitorTime) {

		boolean raiseAlarmFlag = false;
		// latest time for device data -> monitor or ventilator data
		Timestamp deviceTime = null;
		if (!BasicUtils.isEmpty(ventilatorTime)) {
			deviceTime = ventilatorTime;
		}
		if (!BasicUtils.isEmpty(monitorTime)) {
			if (!BasicUtils.isEmpty(deviceTime)) {
				if (monitorTime.getTime() > deviceTime.getTime()) {
					deviceTime = monitorTime;
				}
			} else {
				deviceTime = monitorTime;
			}
		}
		if (!BasicUtils.isEmpty(deviceTime)) {
			Timestamp now = new Timestamp(System.currentTimeMillis());
			float diff = (now.getTime() - deviceTime.getTime()) / (60000);
			if (diff >= 5.0)
				raiseAlarmFlag = true;
			else
				raiseAlarmFlag = false;
		} else {
			raiseAlarmFlag = true;
		}
		// if(!BasicUtils.isEmpty(returnObj.getStart_time())){
		// Timestamp timeDevice = returnObj.getStart_time();
		// Timestamp now = new Timestamp(System.currentTimeMillis());
		// float diff =(now.getTime()-timeDevice.getTime())/60000;
		// if(diff>=5.0 && isConnected)
		// dm.setAlarm(true);
		// }else{
		// if(isConnected)
		// dm.setAlarm(true);
		// }
		return raiseAlarmFlag;
	}

	@Override
	public List<DoctorTasks> getDoctorTasks(String branchName) {
		java.util.Date todayDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayDateStr = sdf.format(todayDate);
		List<DoctorTasks> returnList = new ArrayList();
		String uhidList="";



		try {
			String babyObjQuery = "SELECT obj FROM BabyDetail obj where branchname = '" + branchName
					+ "' and admissionstatus = 'true' order by babyname";
			List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(babyObjQuery);

			for(BabyDetail babyObj:babyList){
				if (uhidList.isEmpty()) {
					uhidList += "'" + babyObj.getUhid() + "'";
				} else {
					uhidList += ", '" + babyObj.getUhid() + "'";
				}
			}

			if (!BasicUtils.isEmpty(babyList)) {
				for (BabyDetail obj : babyList) {
					String doctorTaskQuery = "SELECT obj FROM DoctorTasks obj where uhid = '" + obj.getUhid()
							+ "' order by creationtime desc";
					List<DoctorTasks> doctorTasksList = inicuDao.getListFromMappedObjQuery(doctorTaskQuery);
					String finalDiagnosisUnique = getAssessmentDetail(obj.getUhid());

					if (!BasicUtils.isEmpty(doctorTasksList)) {
						doctorTasksList.get(0).setAssessments(finalDiagnosisUnique);
						returnList.add(doctorTasksList.get(0));
					} else {
						DoctorTasks doctorObj = new DoctorTasks();
						doctorObj.setUhid(obj.getUhid());
						String babyname = obj.getBabyname();
						if (!BasicUtils.isEmpty(obj.getBabyNumber())) {
							babyname = babyname + " - " + obj.getBabyNumber();
						}
						doctorObj.setName(babyname);
						doctorObj.setVisitdate(todayDate);
						doctorObj.setAssessments(finalDiagnosisUnique);
						doctorObj.setStatusAddMedicine(false);
						doctorObj.setStatusStopMedicine(false);
						doctorObj.setStatusInvestigation(false);
						doctorObj.setStatusComments(false);
						doctorObj.setStatusReports(false);
						doctorObj.setStatusAssessments(false);
						doctorObj.setStatusFeeds(null);
						doctorObj.setIsSelected(false);

						returnList.add(doctorObj);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnList;
	}

	@Override
	public List<DoctorTasks> getNewDoctorTasks(String branchName) {
		java.util.Date todayDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayDateStr = sdf.format(todayDate);
		List<DoctorTasks> returnList = new ArrayList();

		try {
			String babyObjQuery = "SELECT obj FROM BabyDetail obj where branchname = '" + branchName
					+ "' and admissionstatus = 'true' order by babyname";
			List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(babyObjQuery);

			if (!BasicUtils.isEmpty(babyList)) {
				for (BabyDetail obj : babyList) {
					DoctorTasks doctorObj = new DoctorTasks();
					String doctorTaskBabyObjQuery = "SELECT obj FROM DoctorTasks obj where uhid = '" + obj.getUhid()
							+ "' and visitDate = '" + todayDateStr + "' order by creationtime desc";
					List<DoctorTasks> doctorTaskBabyList = inicuDao.getListFromMappedObjQuery(doctorTaskBabyObjQuery);
					if (BasicUtils.isEmpty(doctorTaskBabyList)) {
						String finalDiagnosisUnique = getAssessmentDetail(obj.getUhid());
						doctorObj.setUhid(obj.getUhid());
						String babyname = obj.getBabyname();
						if (!BasicUtils.isEmpty(obj.getBabyNumber())) {
							babyname = babyname + " - " + obj.getBabyNumber();
						}
						doctorObj.setName(babyname);
						doctorObj.setVisitdate(todayDate);
						doctorObj.setAssessments(finalDiagnosisUnique);
						doctorObj.setStatusAddMedicine(false);
						doctorObj.setStatusStopMedicine(false);
						doctorObj.setStatusInvestigation(false);
						doctorObj.setStatusComments(false);
						doctorObj.setStatusReports(false);
						doctorObj.setStatusAssessments(false);
						doctorObj.setStatusFeeds(null);
						doctorObj.setIsSelected(false);
						returnList.add(doctorObj);
					} else {
						returnList.add(doctorTaskBabyList.get(0));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnList;
	}

	// On going assessment details fetching function
	public String getAssessmentDetail(String uhid) {
		String finalDiagnosisUnique = "";
		try {
			String diagnosisQuery = "SELECT diagnosis FROM vw_sa_assesment_count_final WHERE uhid='" + uhid + "'";
			List<String> resultSet = userServiceDao.executeNativeQuery(diagnosisQuery);

			// handle unique in diagnosis...
			if (!BasicUtils.isEmpty(resultSet)) {
				String[] finalDiagArr = resultSet.get(0).split(",");
				for (String diag : finalDiagArr) {
					if (finalDiagnosisUnique.isEmpty()) {
						finalDiagnosisUnique = diag.replace(" ", "");
					} else {
						if (!finalDiagnosisUnique.contains(diag.replace(" ", ""))) {
							finalDiagnosisUnique += "/ " + diag.replace(" ", "");
						}
					}
				}
				finalDiagnosisUnique = finalDiagnosisUnique.replace("Jaundice", "Jaund");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return finalDiagnosisUnique;
	}

	@Override
	public List<NurseTasks> getNurseTasks(String branchName) {

		return getNurseTasks( branchName, null);

	}

	public List<NurseTasks> getNurseTasks(String branchName, String uhid) {
		java.util.Date todayDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayDateStr = sdf.format(todayDate);
		List<NurseTasks> returnList = new ArrayList();
		String uhidList="";
		HashMap<String,NurseTasks> NurseTasksMap=new HashMap<>();

		try {
			String babyObjQuery = "SELECT obj FROM BabyDetail obj where admissionstatus = 'true' ";

 			if(branchName!=null) 	babyObjQuery+= " and branchname = '" +branchName+"' ";

			if(uhid!=null) 	babyObjQuery+= " and uhid = '" +uhid+"' ";

			babyObjQuery+= " order by babyname";

			List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(babyObjQuery);


			for( BabyDetail babyObj:babyList){
				if (uhidList.isEmpty()) {
					uhidList += "'" +  babyObj.getUhid() + "'";
				} else {
					uhidList += ", '" + babyObj.getUhid() + "'";
				}
			}

			if (!BasicUtils.isEmpty(babyList)) {

				for (BabyDetail obj : babyList) {

					NurseTasks nurseObj;

					String nurseTaskQuery = "SELECT obj FROM NurseTasks obj where visitdate = '" + todayDateStr
							+ "' and uhid = '" + obj.getUhid() + "'";
					List<NurseTasks> nurseTasksList = inicuDao.getListFromMappedObjQuery(nurseTaskQuery);
					if (!BasicUtils.isEmpty(nurseTasksList)) {
						// Fetch the last entry
						nurseObj=nurseTasksList.get(0);
						nurseObj.setInvestigations(null);
						nurseObj.setMedications(null);
						nurseObj.setPreparationMedication(null);
						nurseObj.setIntake(null);
						nurseObj.setPreparationNutrition(null);
					} else {
						nurseObj = new NurseTasks();
						nurseObj.setUhid(obj.getUhid());
						nurseObj.setLoggedUser(obj.getLoggeduser());
						String babyname = obj.getBabyname();
						if (!BasicUtils.isEmpty(obj.getBabyNumber())) {
							babyname = babyname + " - " + obj.getBabyNumber();
						}
						nurseObj.setInvestigations(null);
						nurseObj.setMedications(null);
						nurseObj.setPreparationMedication(null);
						nurseObj.setIntake(null);
						nurseObj.setPreparationNutrition(null);
						nurseObj.setName(babyname);
						nurseObj.setVisitdate(todayDate);
						nurseObj.setStatusMedications(false);
						nurseObj.setStatusPreparationMedication(false);
						nurseObj.setStatusPreparationNutrition(false);
						nurseObj.setStatusVitals(false);
						nurseObj.setStatusVentilator(false);
						nurseObj.setStatusIntake(false);
						nurseObj.setStatusOutput(false);
						nurseObj.setStatusInvestigations(false);
						nurseObj.setIsSelected(false);
					}

					// Feeds
					String finalIntakeStr = "";
					String lastFeedQuery = "select obj from BabyfeedDetail obj where uhid ='" + obj.getUhid() + "'";
					List<BabyfeedDetail> babyFeedAllList = inicuDao.getListFromMappedObjQuery(lastFeedQuery);

					if (!BasicUtils.isEmpty(babyFeedAllList)) {


						String enString = "";
						String pnString = "";

						if (!BasicUtils.isEmpty(babyFeedAllList.get(0).getTotalenteralvolume())) {
							enString = "EN (" + babyFeedAllList.get(0).getTotalenteralvolume() + " ml)";
						}
						if (!BasicUtils.isEmpty(babyFeedAllList.get(0).getTotalparenteralvolume())) {
							pnString = "PN (" + babyFeedAllList.get(0).getTotalparenteralvolume() + " ml)";
						}
						finalIntakeStr = enString + " " + pnString;
					}
					nurseObj.setIntake(finalIntakeStr);
					nurseObj.setPreparationNutrition(finalIntakeStr);
					NurseTasksMap.put(obj.getUhid(),nurseObj);
				}


				// Investigations
				Timestamp today = new Timestamp((new java.util.Date().getTime()));
				today.setHours(8);
				today.setMinutes(0);
				Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));
				Timestamp tomorrow = new Timestamp((new java.util.Date().getTime()) + (24 * 60 * 60 * 1000));

				String queryLabOrdersList =HqlSqlQueryConstants.getLabReportList(uhidList,today,yesterday);
				List<InvestigationOrdered> investigationOrderedListRaw = inicuDao.getListFromMappedObjQuery(queryLabOrdersList);

				if(!BasicUtils.isEmpty(investigationOrderedListRaw)) {
					for(InvestigationOrdered obj:investigationOrderedListRaw){
						String tempUhid=obj.getUhid();

						NurseTasks currentObject=NurseTasksMap.get(tempUhid);
						if(currentObject!=null){
							String testOrders =currentObject.getInvestigations();
							if (testOrders == "" || testOrders== null) {
								testOrders = obj.getTestname();
							} else {
								testOrders = testOrders + ", " + obj.getTestname();
							}
							currentObject.setInvestigations(testOrders);
						}
						NurseTasksMap.replace(tempUhid, currentObject);
					}
				}

				// Medications
				String queryPrescription = "SELECT obj FROM BabyPrescription obj where uhid in (" + uhidList
						+ ") and (isactive ='true') order by startdate desc";
				List<BabyPrescription> activePrescription = inicuDao.getListFromMappedObjQuery(queryPrescription);

				if (!BasicUtils.isEmpty(activePrescription)) {
					for (BabyPrescription med : activePrescription) {

						// Local variables
						String finalMedString, finalPreparationMedString;
						List<String> preparationMedicationStringList = new ArrayList<>();

						String tempUhid=med.getUhid();
						NurseTasks currentObject=NurseTasksMap.get(tempUhid);
						finalMedString =currentObject.getMedications();
						finalPreparationMedString = currentObject.getPreparationMedication();

						String medString = "";
						medString = med.getMedicinename();
						if (!BasicUtils.isEmpty(med.getFrequency())) {
							String queryFrequency = "SELECT obj FROM RefMedfrequency obj where freqid = '"
									+ med.getFrequency() + "'";
							List<RefMedfrequency> freqResult = inicuDao.getListFromMappedObjQuery(queryFrequency);
							if (!BasicUtils.isEmpty(freqResult)) {
								medString += " (" + freqResult.get(0).getFreqvalue() + ")";
							}
						}
						if (finalMedString == "" || finalMedString==null) {
							finalMedString = medString;
						} else {
							finalMedString += ", " + medString;
						}

						if (!BasicUtils.isEmpty(med.getInstruction())) {
							preparationMedicationStringList.add(med.getInstruction());
						}

						if (!BasicUtils.isEmpty(preparationMedicationStringList)) {
							finalPreparationMedString = preparationMedicationStringList.toString();
							finalPreparationMedString = finalPreparationMedString.replace("[", "");
							finalPreparationMedString = finalPreparationMedString.replace("]", "") + ". ";
						}

						// now update the current Object
						currentObject.setMedications(finalMedString);
						currentObject.setPreparationMedication(finalPreparationMedString);
					}
				}

				for (Map.Entry<String,NurseTasks> entry : NurseTasksMap.entrySet()) {
					NurseTasks nurseObj = entry.getValue();
					if(nurseObj.getUhid()!=null){
						returnList.add(nurseObj);
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnList;
	}


	@Override
	public BabyDetailPOJO getActiveBabies(String branchName) {
		BabyDetailPOJO masterObj = new BabyDetailPOJO();
		List<BabyBasicDetail>  babyDetailList = new ArrayList<>();
		ResponseMessageObject response =  new ResponseMessageObject();
		response.setMessage(BasicConstants.MESSAGE_SUCCESS);
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		try {

			String fetchBabyQuery = "Select obj from BabyDetail as obj where branchname='"+branchName+
					"' and admissionstatus='true'";
			List<BabyDetail> babyDetails = inicuDao.getListFromMappedObjQuery(fetchBabyQuery);
			for(BabyDetail babyDetail : babyDetails) {
				BabyBasicDetail babyBasicDetail = new BabyBasicDetail();
				babyBasicDetail.setBabydetailid(babyDetail.getUhid());
				babyBasicDetail.setBabyName(babyDetail.getBabyname());
				babyBasicDetail.setBabyType(babyDetail.getBabyType());
				babyBasicDetail.setBabyNumber(babyDetail.getBabyNumber());
				babyDetailList.add(babyBasicDetail);

			}

			masterObj.setBabyList(babyDetailList);

		}catch(Exception e) {
			e.printStackTrace();
			response.setMessage(BasicConstants.MESSAGE_FAILURE);
			response.setType(BasicConstants.MESSAGE_FAILURE);

		}
		return masterObj;
	}
	
	@Override
	public List<RoleManager> getRoleStatus(String roleId, String branchName, String uhid){
		List<RoleManager> role = new ArrayList<RoleManager>();
		
		String fetchRoleQuery = "Select obj from RoleManager as obj where roleid=" + roleId;
		role = inicuDao.getListFromMappedObjQuery(fetchRoleQuery);
		
		String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='" + branchName + "'";
		List<RefHospitalbranchname> refHospitalbranchnameList = inicuDao.getListFromMappedObjQuery(refHospitalbranchnameQuery);
		if(refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0 && !BasicUtils.isEmpty(refHospitalbranchnameList.get(0).getPredictions())) {
			String prediction = refHospitalbranchnameList.get(0).getPredictions();
			role.get(0).setPrediction(prediction);
			if(prediction.equalsIgnoreCase("Yes")) {
				String fetchBabyQuery = "Select obj from BabyDetail as obj where uhid='" + uhid + "'";
				List<BabyDetail> baby = inicuDao.getListFromMappedObjQuery(fetchBabyQuery);
				if(!BasicUtils.isEmpty(baby)) {
					Integer gestation = baby.get(0).getGestationweekbylmp();
					String gestationCategory = "0";
					if(gestation < 32) {
						gestationCategory = "1";
					}else if(gestation >=32 && gestation < 34) {
						gestationCategory = "2";
					}else if(gestation >=34 && gestation < 37) {
						gestationCategory = "3";
					}else {
						gestationCategory = "4";
					}
					float days = 0;
					
					String getRiskFactor = "SELECT " + "median" + " from " +
							BasicConstants.SCHEMA_NAME + ".predict_los where gest_catid = '" + gestationCategory + "'";
					List<String> predictLOSData = userServiceDao.executeNativeQuery(getRiskFactor);
					if(!BasicUtils.isEmpty(predictLOSData) && predictLOSData.size() > 0) {
						days += Float.parseFloat(predictLOSData.get(0));
					}
					
					days += losFactors(uhid,gestationCategory,"gestation_fourth_quartile_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"gestation_rem_quartile_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"energy_fourth_quartile_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"energy_rem_quartile_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"protein_fourth_quartile_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"protein_rem_quartile_riskfactor");
					
					if(baby.get(0).getGender().equalsIgnoreCase("Male"))
						days += losFactors(uhid,gestationCategory,"gender_male_riskfactor");
					
					if(baby.get(0).getGender().equalsIgnoreCase("Female"))
						days += losFactors(uhid,gestationCategory,"gender_female_riskfactor");
					
					if(baby.get(0).getBabyType().equalsIgnoreCase("Single"))
						days += losFactors(uhid,gestationCategory,"single_pregnancy_riskfactor");
					else
						days += losFactors(uhid,gestationCategory,"multiple_pregnancy_riskfactor");
					
					if(baby.get(0).getInoutPatientStatus().equalsIgnoreCase("In born"))
						days += losFactors(uhid,gestationCategory,"inborn_riskfactor");
					else
						days += losFactors(uhid,gestationCategory,"out_born_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"apgar_greater_than_five_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"apgar_less_than_five_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"apgar_not_available_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"ppv_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"nvd_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"lscs_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"maternal_diseases_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"maternal_diseases_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"maternal_infections_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"maternal_infections_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"maternal_risk_factors_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"maternal_risk_factors_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"rds_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"rds_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"mas_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"mas_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"ttnb_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"ttnb_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"jaundice_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"jaundice_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"sepsis_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"sepsis_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"asphyxia_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"asphyxia_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"pphn_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"pphn_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"pneumothorax_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"pneumothorax_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"invasive_true_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"invasive_false_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"medicine_not_required_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"deviation_riskfactor");
					

					days += losFactors(uhid,gestationCategory,"no_deviation_riskfactor");
					
					role.get(0).setPredictionDays((int)days);
					
					String query = "SELECT obj FROM PredictHDP as obj WHERE " + "uhid ='"
							+ uhid.trim() + "' " + " order by hdptime desc";
					
					List<PredictHDP> resultSet = inicuDao.getListFromMappedObjQuery(query);

					// HDP Data
					
					if (!BasicUtils.isEmpty(resultSet) && !BasicUtils.isEmpty(resultSet.get(0).getHdpvalue())) {
						
						role.get(0).setHdpResult(resultSet.get(0).getHdpvalue());
					}
				}
			}
		}
		
		
		return role;
	}
	
	public Float losFactors(String uhid,String gestationCategory,String factor) {
		
		float daysRisk = 0;
		try {
			String getRiskFactor = "SELECT " + factor + " from " +
					BasicConstants.SCHEMA_NAME + ".predict_los where gest_catid = '" + gestationCategory + "'";
			List<String> predictLOSData = userServiceDao.executeNativeQuery(getRiskFactor);
			if(!BasicUtils.isEmpty(predictLOSData) && predictLOSData.size() > 0) {
				daysRisk = Float.parseFloat(predictLOSData.get(0));
			}
			
			String babyQuery = "SELECT b FROM BabyDetail b where uhid = '" + uhid + "'";
			List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(babyQuery);

			String antenatalQuery = "SELECT b FROM AntenatalHistoryDetail b where uhid = '" + uhid + "'";
			List<AntenatalHistoryDetail> antenatalList = inicuDao.getListFromMappedObjQuery(antenatalQuery);
			
			String birthToNicuQuery = "SELECT b FROM BirthToNicu b where uhid = '" + uhid + "'";
			List<BirthToNicu> birthToNicuQueryList = inicuDao.getListFromMappedObjQuery(birthToNicuQuery);
			
			if(factor.equalsIgnoreCase("gender_male_riskfactor")) {
				if(!babyList.get(0).getGender().equalsIgnoreCase("Male")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("gender_female_riskfactor")) {
				if(!babyList.get(0).getGender().equalsIgnoreCase("Female")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("gestation_fourth_quartile_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getGestationQuartile()) && !babyList.get(0).getGestationQuartile()) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getGestationQuartile())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("gestation_rem_quartile_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getGestationQuartile()) && babyList.get(0).getGestationQuartile()) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getGestationQuartile())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("energy_fourth_quartile_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getEnergyDeviation()) && !babyList.get(0).getEnergyDeviation()) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getEnergyDeviation())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("energy_rem_quartile_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getEnergyDeviation()) && babyList.get(0).getEnergyDeviation()) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getEnergyDeviation())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("protein_fourth_quartile_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getProteinDeviation()) && !babyList.get(0).getProteinDeviation()) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getProteinDeviation())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("protein_rem_quartile_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getProteinDeviation()) && babyList.get(0).getProteinDeviation()) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getProteinDeviation())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("medicine_not_required_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation()) && babyList.get(0).getMedicationDeviation()!= -1) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("deviation_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation()) && babyList.get(0).getMedicationDeviation()!= 1) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("no_deviation_riskfactor")) {
				if(!BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation()) && babyList.get(0).getMedicationDeviation()!= 0) {
					return 0f;
				}else if(BasicUtils.isEmpty(babyList.get(0).getMedicationDeviation())) {
					return 0f;
				}
			}
			else if(factor.equalsIgnoreCase("single_pregnancy_riskfactor")) {
				if(!babyList.get(0).getBabyType().equalsIgnoreCase("Single")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("multiple_pregnancy_riskfactor")) {
				if(babyList.get(0).getBabyType().equalsIgnoreCase("Single")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("inborn_riskfactor")) {
				if(!babyList.get(0).getInoutPatientStatus().equalsIgnoreCase("Inborn")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("out_born_riskfactor")) {
				if(!babyList.get(0).getInoutPatientStatus().equalsIgnoreCase("Outborn")) {
					return 0f;
				}
			}
			else if(factor.equalsIgnoreCase("apgar_greater_than_five_riskfactor")) {
				if(!BasicUtils.isEmpty(birthToNicuQueryList.get(0).getApgarFivemin())) {
					if(!(birthToNicuQueryList.get(0).getApgarFivemin() > 5)) {
						return 0f;
					}
				}
			}else if(factor.equalsIgnoreCase("apgar_less_than_five_riskfactor")) {
				if(!BasicUtils.isEmpty(birthToNicuQueryList.get(0).getApgarFivemin())) {
					if(!(birthToNicuQueryList.get(0).getApgarFivemin() <= 5)) {
						return 0f;
					}
				}
			}else if(factor.equalsIgnoreCase("apgar_not_available_riskfactor")) {
				if(!BasicUtils.isEmpty(birthToNicuQueryList.get(0).getApgarFivemin())) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("nvd_riskfactor")) {
				if(!BasicUtils.isEmpty(antenatalList.get(0).getModeOfDelivery()) && antenatalList.get(0).getModeOfDelivery().equalsIgnoreCase("LSCS")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("lscs_riskfactor")) {
				if(!BasicUtils.isEmpty(antenatalList.get(0).getModeOfDelivery()) && !antenatalList.get(0).getModeOfDelivery().equalsIgnoreCase("LSCS")) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("maternal_diseases_true_riskfactor")) {
				if((!BasicUtils.isEmpty(antenatalList.get(0).getHypertension()) && antenatalList.get(0).getHypertension() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getGestationalHypertension()) && antenatalList.get(0).getGestationalHypertension() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getDiabetes()) && antenatalList.get(0).getDiabetes() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getGdm()) && antenatalList.get(0).getGdm() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getChronicKidneyDisease()) && antenatalList.get(0).getChronicKidneyDisease() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getHypothyroidism()) && antenatalList.get(0).getHypothyroidism() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getHyperthyroidism()) && antenatalList.get(0).getHyperthyroidism() == true)) {
				}else {
					return 0f;

				}
				
			}else if(factor.equalsIgnoreCase("maternal_diseases_false_riskfactor")) {
				if((!BasicUtils.isEmpty(antenatalList.get(0).getHypertension()) && antenatalList.get(0).getHypertension() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getGestationalHypertension()) && antenatalList.get(0).getGestationalHypertension() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getDiabetes()) && antenatalList.get(0).getDiabetes() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getGdm()) && antenatalList.get(0).getGdm() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getChronicKidneyDisease()) && antenatalList.get(0).getChronicKidneyDisease() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getHypothyroidism()) && antenatalList.get(0).getHypothyroidism() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getHyperthyroidism()) && antenatalList.get(0).getHyperthyroidism() == true)) {
					return 0f;

				}
			}
			else if(factor.equalsIgnoreCase("maternal_infections_true_riskfactor")) {
				if((!BasicUtils.isEmpty(antenatalList.get(0).getFever()) && antenatalList.get(0).getFever() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getUti()) && antenatalList.get(0).getUti() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getHistoryOfInfections()) && antenatalList.get(0).getHistoryOfInfections() == true)) {
				}else {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("maternal_infections_false_riskfactor")) {
				if((!BasicUtils.isEmpty(antenatalList.get(0).getFever()) && antenatalList.get(0).getFever() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getUti()) && antenatalList.get(0).getUti() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getHistoryOfInfections()) && antenatalList.get(0).getHistoryOfInfections() == true)) {
					return 0f;
				}
			}
			else if(factor.equalsIgnoreCase("maternal_risk_factors_true_riskfactor")) {
				if((!BasicUtils.isEmpty(antenatalList.get(0).getProm()) && antenatalList.get(0).getProm() == true) || 
						(!BasicUtils.isEmpty(antenatalList.get(0).getPprom()) && antenatalList.get(0).getPprom() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getChorioamniotis()) && antenatalList.get(0).getChorioamniotis() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getOligohydraminos()) && antenatalList.get(0).getOligohydraminos() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getPolyhydraminos()) && antenatalList.get(0).getPolyhydraminos() == true)) {
				}else {
					return 0f;
				}

			}else if(factor.equalsIgnoreCase("maternal_risk_factors_false_riskfactor")) {
				if((!BasicUtils.isEmpty(antenatalList.get(0).getProm()) && antenatalList.get(0).getProm() == true) || 
						(!BasicUtils.isEmpty(antenatalList.get(0).getPprom()) && antenatalList.get(0).getPprom() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getChorioamniotis()) && antenatalList.get(0).getChorioamniotis() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getOligohydraminos()) && antenatalList.get(0).getOligohydraminos() == true) ||
						(!BasicUtils.isEmpty(antenatalList.get(0).getPolyhydraminos()) && antenatalList.get(0).getPolyhydraminos() == true)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("rds_true_riskfactor") || factor.equalsIgnoreCase("rds_false_riskfactor") || factor.equalsIgnoreCase("mas_true_riskfactor") || factor.equalsIgnoreCase("mas_true_riskfactor")
					|| factor.equalsIgnoreCase("ttnb_true_riskfactor") || factor.equalsIgnoreCase("ttnb_true_riskfactor")) {
				
				boolean isRds = false;
				boolean isRdsTTNB = false;
				boolean isRdsMAS = false;

				String rdsQuery = "select obj from SaRespRds obj where uhid = '" + uhid + "' and episode_number = 1 and eventstatus = 'Yes' order by assessment_time asc";
				List<SaRespRds> saRespRdsList = userServiceDao.getListFromMappedObjNativeQuery(rdsQuery);
				if(!BasicUtils.isEmpty(saRespRdsList)) {
					isRds = true;
				}


				if(isRds) {
					for(SaRespRds resp : saRespRdsList) {
						if(!BasicUtils.isEmpty(resp.getCauseofrds()) && resp.getCauseofrds().contains("RDS0001")) {
							isRdsTTNB = true;
							isRds = false;
						}
						if(!BasicUtils.isEmpty(resp.getCauseofrds()) && resp.getCauseofrds().contains("RDS0003")) {
							isRdsMAS = true;
							isRds = false;
						}
					}

					String reasonQuery = "select obj from ReasonAdmission obj where uhid = '" + uhid + "'";
					List<ReasonAdmission> reasonList = userServiceDao.getListFromMappedObjNativeQuery(reasonQuery);
					for(ReasonAdmission reason : reasonList) {
						if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("TTNB")){
							isRdsTTNB = true;
							isRds = false;
						}
						if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("MAS")){
							isRdsMAS = true;
							isRds = false;
						}
					}
				}
				
				if(factor.equalsIgnoreCase("rds_true_riskfactor")) {
					if(!isRds) {
						return 0f;
					}
				
				}else if(factor.equalsIgnoreCase("rds_false_riskfactor")) {
					if(isRds) {
						return 0f;
					}
					
				}else if(factor.equalsIgnoreCase("mas_true_riskfactor")) {
					if(!isRdsMAS) {
						return 0f;
					}
					
				}else if(factor.equalsIgnoreCase("mas_false_riskfactor")) {
					if(isRdsMAS) {
						return 0f;
					}
					
				}else if(factor.equalsIgnoreCase("ttnb_true_riskfactor")) {
					if(!isRdsTTNB) {
						return 0f;
					}
					
				}else if(factor.equalsIgnoreCase("ttnb_false_riskfactor")) {
					if(isRdsTTNB) {
						return 0f;
					}
					
				}
				
				
			}else if(factor.equalsIgnoreCase("jaundice_true_riskfactor")) {
				String jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + uhid + "' and episode_number = 1 and jaundicestatus = 'Yes' and phototherapyvalue='Start' order by assessment_time asc";
				List<SaJaundice> jaunList = userServiceDao.getListFromMappedObjNativeQuery(jaundiceQuery);
				if(BasicUtils.isEmpty(jaunList)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("jaundice_false_riskfactor")) {
				String jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + uhid + "' and episode_number = 1 and jaundicestatus = 'Yes' and phototherapyvalue='Start' order by assessment_time asc";
				List<SaJaundice> jaunList = userServiceDao.getListFromMappedObjNativeQuery(jaundiceQuery);
				if(!BasicUtils.isEmpty(jaunList)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("sepsis_true_riskfactor")) {
				String sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
				List<SaSepsis> sepsisLists = userServiceDao.getListFromMappedObjNativeQuery(sepsisQuery);
				if(BasicUtils.isEmpty(sepsisLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("sepsis_false_riskfactor")) {
				String sepsisQuery = "select obj from SaSepsis obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
				List<SaSepsis> sepsisLists = userServiceDao.getListFromMappedObjNativeQuery(sepsisQuery);
				if(!BasicUtils.isEmpty(sepsisLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("asphyxia_true_riskfactor")) {
				String asphyxiaQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
				List<SaCnsAsphyxia> asphyxiaLists = userServiceDao.getListFromMappedObjNativeQuery(asphyxiaQuery);
				if(BasicUtils.isEmpty(asphyxiaLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("asphyxia_false_riskfactor")) {
				String asphyxiaQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + uhid + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
				List<SaCnsAsphyxia> asphyxiaLists = userServiceDao.getListFromMappedObjNativeQuery(asphyxiaQuery);
				if(!BasicUtils.isEmpty(asphyxiaLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("pneumothorax_true_riskfactor")) {
				String pneumoQuery = "select obj from SaRespPneumo obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1 order by assessment_time asc";
				List<SaRespPneumo> pneumoLists = userServiceDao.getListFromMappedObjNativeQuery(pneumoQuery);
				if(BasicUtils.isEmpty(pneumoLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("pneumothorax_false_riskfactor")) {
				String pneumoQuery = "select obj from SaRespPneumo obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1 order by assessment_time asc";
				List<SaRespPneumo> pneumoLists = userServiceDao.getListFromMappedObjNativeQuery(pneumoQuery);
				if(!BasicUtils.isEmpty(pneumoLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("pphn_true_riskfactor")) {
				String pphnQuery = "select obj from SaRespPphn obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1  order by assessment_time asc";
				List<SaRespPphn> pphnLists = userServiceDao.getListFromMappedObjNativeQuery(pphnQuery);
				if(BasicUtils.isEmpty(pphnLists)) {
					return 0f;
				}
			}else if(factor.equalsIgnoreCase("pphn_false_riskfactor")) {
				String pphnQuery = "select obj from SaRespPphn obj where uhid = '" + uhid + "' and eventstatus = 'Yes' and episode_number = 1  order by assessment_time asc";
				List<SaRespPphn> pphnLists = userServiceDao.getListFromMappedObjNativeQuery(pphnQuery);
				if(BasicUtils.isEmpty(pphnLists)) {
					return 0f;
				}
			}
			
			else if(factor.equalsIgnoreCase("invasive_true_riskfactor")) {
				boolean isRespSupport = false;

				String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
				List<RespSupport> bpdList = userServiceDao.getListFromMappedObjNativeQuery(bpdQuery);
				for(RespSupport resp : bpdList) {
					if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
						isRespSupport = true;					
					}else if(isRespSupport == false){
						isRespSupport = false;
					}
				}if(BasicUtils.isEmpty(bpdList) || isRespSupport == false) {
					return 0f;
				}
				
			}else if(factor.equalsIgnoreCase("invasive_false_riskfactor")) {
				String bpdQuery = "select obj from RespSupport obj where uhid = '" + uhid + "' order by creationtime";
				List<RespSupport> bpdList = userServiceDao.getListFromMappedObjNativeQuery(bpdQuery);
				boolean isRespSupport = false;
				for(RespSupport resp : bpdList) {
					if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
						isRespSupport = true;
					}else if(isRespSupport == false){
						isRespSupport = false;
					}
				}if(isRespSupport == true) {
					return 0f;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return daysRisk;
	}


}
