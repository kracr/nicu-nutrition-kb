package com.inicu.postgres.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.models.*;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.LoginDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.entities.Module;
import com.inicu.postgres.service.LoginService;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.PasswordService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.Message;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * 
 * @author iNICU
 *
 */
@Repository
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	UserServiceDAO userServiceDao;

	@Autowired
	PasswordService passwordService;
	
	PatientServiceImpl patientService;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	LogsService logService;
	ObjectMapper mapper = new ObjectMapper();

	SimpleDateFormat loginDateFormat = new SimpleDateFormat("dd-mm-yyy");
	SimpleDateFormat loginTimeFormat = new SimpleDateFormat("hh:MM:ss");

	@Override
	public Object validateUser(Object userId, Object userPassword, Object branchName) {
		Object user = null;
		if (userPassword != null) {
			String encPass = passwordService.encryptPassword(userPassword.toString());
			if (!BasicUtils.isEmpty(encPass)) {
				user = loginDao.getUserWithUserId(userId, encPass, branchName, BasicConstants.SCHEMA_NAME);
			}
		}

		if (user == null) {
			user = loginDao.getUserWithUserId(userId, userPassword, branchName, BasicConstants.SCHEMA_NAME);
			
		}
		
		
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		if (user != null) {
			response.setReturnedObject(user);
			User use2 = (User) user;
			// getting role status and setting to response object..
			response = getUserRoleModuleStatus(use2, use2.getUserName(), branchName.toString());
		} else {
			response.setType("failure");
			response.setMessage("User id or password is not correct!!");
			
			//check user active/inactive status 
			Object status = loginDao.getUserActiveStatus(userId, userPassword, branchName);
			if(!BasicUtils.isEmpty(status)) {
				if(Integer.parseInt(status.toString())== 0) {
					response.setType("failure");
					response.setMessage("This user is inactive!!");
				}else {
					response.setType("failure");
					response.setMessage("User id or password is not correct!!");	
				}
				
					
			}
			
//			String status = loginDao.get
			
			System.out.println("failed user!!");
		}
		return response;
	}

	@Override
	public Object saveResetPasswordDetails(User user,Object password)
	{
		try {
			Object checkSaveStats = loginDao.saveNewPassword(user,password.toString());
			if(checkSaveStats!=null)
			{
				System.out.print("Data saved successfully");
				return checkSaveStats;
			}else{
				System.out.print("Unable to save the new password in the database");
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Unable to save the data in the database");
		}
		return null;
	}

	@Override
	public String checkUserExistence(Object useremail,Object branchName) {
		Object user = null;
		if (useremail !=null) {
			if (!BasicUtils.isEmpty(useremail)) {
				user = loginDao.getUserWithUsernameOrEmail(useremail, branchName);
			}
		}
		if(user!=null)
		{
			User myuser=(User)user;
			String userFirstname=myuser.getFirstName();
			String userLastname=myuser.getLastName();
			String userEmail=myuser.getEmail();
			String newPassword="inicu";
			String emailMessage="";
			String subject="New Password";
			String message="Hell umesh";
			String companyId= BasicConstants.COMPANY_ID + " ( " + branchName.toString() + " )";
			Random rand = new Random();
			
			for(int i=0;i<5;i++) {
				int x = (int) rand.nextInt(10);
				newPassword += x;
			}
			emailMessage="<p><b>Dear " + userFirstname +" "+userLastname +","+"</b></p>"+
			"<p>You have just requested a password reset for your account with Email:<b>"+userEmail+"</b></p>"+
			"<p>Email:  "+ userEmail+"  and New Password :  "+newPassword+"</p><br>"+
			"<b>Regards</b>";
			String[] emails= {useremail.toString()};
			try {		
				HashMap<Message.RecipientType, List<String>> emailMap = new HashMap<Message.RecipientType, List<String>>() {
					private static final long serialVersionUID = 1L;
					{
						put(Message.RecipientType.TO, new ArrayList<String>());
					}
				};
				emailMap.get(Message.RecipientType.TO).add(useremail.toString());
				BasicUtils.sendMailWithMultipleType(emailMap, emailMessage, subject, companyId);

				// Update the new password in the database
				try {
					Object checkSaveStats = loginDao.saveNewPassword(myuser,newPassword);
					if(checkSaveStats!=null)
					{
						System.out.print("Data saved successfully");
						return "New Password has been saved and Sent to your email";
					}else{
						System.out.print("Unable to save the new password in the database");
					}
				}catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("Unable to save the data in the database");
					return "Unable to save the data in the database";
				}
			 	}catch (Exception e) {
			 		e.printStackTrace();
			 	}
	   	}
		return null;
	}

	@Override
	public Object saveLoginDetails(Object userId,String branchname) {
		Logindetail loginDetail = new Logindetail();
		try {
			loginDetail.setUserid(userId.toString());
			Date date = new Date();
			String loginDate = loginDateFormat.format(date);
			String loginTime = loginTimeFormat.format(date);
			loginDetail.setLogintime(new Timestamp(date.getTime()));
			// loginDetail.setLoginTime(loginTime);
			String loginIpAddress = BasicUtils.getIpAddress();
			loginDetail.setIpaddress(loginIpAddress);

			// set the branchname
			loginDetail.setBranchname(branchname);
			// loginDetail.setLogout(false);
			String desc = mapper.writeValueAsString(loginDetail);
			loginDetail = (Logindetail) loginDao.saveObject(loginDetail);
//			logService.saveLog(desc, BasicConstants.INSERT, userId.toString(), null, "Login Page");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return loginDetail;
	}

	@Override
	public Object logOutUser(String userId,String branchname) { // no use right now...
		try {
			Date date = new Date();
			Timestamp currentTime = new Timestamp(date.getTime());
			String queryLogout = "update logindetails set logout='true',logouttime ='" + currentTime + "' where userid='" + userId + "' " +
					"and branchname = '" + branchname + "'";
			inicuDao.updateOrDeleteNativeQuery(queryLogout);
			return "logout Successfully";
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public ResponseMessageWithResponseObject getUserRoleModuleStatus(User user, String userName, String branchName) {// its a user id ..
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		List<RoleObj> roleStatus = new ArrayList<RoleObj>();
		// get role id based on the user id ..
		String queryStr = "select obj from UserRoles obj where username='" + userName + "' and branchname = '" + branchName + "'";
		List<UserRoles> userRoleList = loginDao.getListFromNativeQuery(queryStr);

		String branchTypeStr = "select obj from RefHospitalbranchname obj where branchname = '" + branchName + "'";
		List<RefHospitalbranchname> userBranchTypeList = loginDao.getListFromNativeQuery(branchTypeStr);

		if(!BasicUtils.isEmpty(userBranchTypeList)) {
			user.setBranchType(userBranchTypeList.get(0).getHospitalType());
		}

		if (!BasicUtils.isEmpty(userRoleList)) {
			String roleId = userRoleList.get(0).getRoleId();
			user.setUserRole(roleId);
			// based on this role id now find the moduleId and statusId from the
			// role_manager table..
			String queryRoleMng = "select obj from RoleManager obj where roleId='" + roleId + "'";
			List<RoleManager> roleMngList = loginDao.getListFromNativeQuery(queryRoleMng);
			if (!BasicUtils.isEmpty(roleMngList)) {

				Iterator<RoleManager> iterator = roleMngList.iterator();
				// now iterate list and find for each module its status and put it in object to
				// be returned..
				while (iterator.hasNext()) {
					RoleObj roleObj = new RoleObj();
					RoleManager roleManager = iterator.next();
					BigInteger moduleId = roleManager.getModuleId();
					BigInteger statusId = roleManager.getStatusId();
					// now get module name based on moduleId
					String queryModule = "select obj from Module obj where moduleId=" + moduleId + "";
					List<Module> moduleList = loginDao.getListFromNativeQuery(queryModule);
					if (!BasicUtils.isEmpty(moduleList)) {
						roleObj.setRoleName(moduleList.get(0).getModuleName());
					}
					// get status based on id..
					String queryStatus = "select obj from com.inicu.postgres.entities.RoleStatus obj where statusid="
							+ statusId + "";
					List<com.inicu.postgres.entities.RoleStatus> statusList = loginDao
							.getListFromNativeQuery(queryStatus);
					if (!BasicUtils.isEmpty(statusList)) {
						// create hash map for read and write operation and set to the role status..
						String status = statusList.get(0).getStatusName();
						if (!BasicUtils.isEmpty(status)) {
							HashMap<String, Boolean> stringMap = new HashMap<>();
							if (status.equalsIgnoreCase("R")) {
								stringMap.put("read", true);
								stringMap.put("write", false);
							} else if (status.equalsIgnoreCase("W")) {
								stringMap.put("read", false);
								stringMap.put("write", true);
							} else if (status.equalsIgnoreCase("RW")) {
								stringMap.put("read", true);
								stringMap.put("write", true);
							} else {
								stringMap.put("read", false);
								stringMap.put("write", false);
							}
							roleObj.setStatus(stringMap);
						}
					}
					roleStatus.add(roleObj);
				}
			}
		}
		response.setType("success");
		response.setMessage("user is registered with this user id and correct password!!");
		response.setRoleStatus(roleStatus);
		response.setReturnedObject(user);
		return response;
	}

	@Override
	public LoginPOJO getInicuSettingPreference() {

		LoginPOJO returnObj =  new LoginPOJO();
		HashMap<String, String> reference = new HashMap<String, String>();
		String queryPreferences = "select obj from Preference as obj";
		List<Preference> preferenceList = loginDao.getListFromMappedObjNativeQuery(queryPreferences);
		if (!BasicUtils.isEmpty(preferenceList)) {

			for (Preference pref : preferenceList) {
				String getSettingPreference = "select obj from SettingReference as obj where category='"
						+ pref.getPreference().trim() + "' and refid = '" + pref.getPrefid().trim() + "'";
				List<SettingReference> settingReferenceList = loginDao
						.getListFromMappedObjNativeQuery(getSettingPreference);
				if (!BasicUtils.isEmpty(settingReferenceList)) {

					for (SettingReference settingReference : settingReferenceList) {
						reference.put(settingReference.getCategory(), settingReference.getRefvalue());
					}
				}
			}
		}
		returnObj.setReference(reference);
		List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());
		//List<KeyValueObj> branchNameList = getRefObj("select obj.hospitalbranchid,obj.branchname from " +  BasicConstants.SCHEMA_NAME + ".ref_hospitalbranchname obj");
		if (!BasicUtils.isEmpty(branchNameList)) {
			returnObj.setBranchNameList(branchNameList);
		}
		String queryConfigurations = "select obj from LocalConfigurations as obj";
		List<LocalConfigurations> queryConfigurationsList = loginDao.getListFromMappedObjNativeQuery(queryConfigurations);
		returnObj.setConfigurationsList(queryConfigurationsList);
		return returnObj;
	}
	
	public List getRefObj(String query) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			// String query = "select obj.bpid,obj.blood_product from
			// kalawati.ref_blood_product obj";
			List<Object[]> refList = patientDao.getListFromNativeQuery(query);
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}

	@Override
	public List testService(String query) {
		return inicuDao.testPostGresQuery(query);

	}

	@Override
	public InicuNotificationPOJO getInicuNotifications(String uhid) {

		InicuNotificationPOJO noificationObj = new InicuNotificationPOJO();
		// settting here notification for the tcb values..
		/*
		 * String queryJaundice = "select obj from SaJaundice obj where uhid='"
		 * +uhid+"' order by creationtime desc"; List<SaJaundice> listJaundice =
		 * inicuDao.getListFromMappedObjQuery(queryJaundice);
		 * if(!BasicUtils.isEmpty(listJaundice)){ SaJaundice jaundice =
		 * listJaundice.get(0); String actionType =jaundice.getActiontype();
		 * if(!BasicUtils.isEmpty(actionType) &&
		 * actionType.equalsIgnoreCase("Reassess")){ Integer actionDuration =
		 * jaundice.getActionduration(); if(actionDuration!=null){ Calendar cal =
		 * Calendar.getInstance(); Timestamp timeStamp = jaundice.getCreationtime();
		 * cal.setTime(timeStamp); Calendar calCurrent =Calendar.getInstance();
		 * java.util.Date timeTemp = calCurrent.getTime();
		 * if(calCurrent.getTime().after(cal.getTime())){ long diff =
		 * calCurrent.getTime().getTime()-cal.getTime().getTime(); long diffHours =
		 * diff/(3600*1000); if(jaundice.getIsactiondurationinhours()!=null &&
		 * jaundice.getIsactiondurationinhours()){
		 * if(diffHours>=jaundice.getActionduration()){
		 * dash.setTcbNotificationCount(Integer.valueOf(1));
		 * dash.setTcbNotification("Please reassess jaundice: last tcb value was "
		 * +jaundice.getTbcvalue()+""); } }else
		 * if(jaundice.getIsactiondurationinhours()!=null &&
		 * !jaundice.getIsactiondurationinhours()){
		 * if((diffHours/24)>=jaundice.getActionduration()){
		 * dash.setTcbNotificationCount(Integer.valueOf(1));
		 * dash.setTcbNotification("Please reassess jaundice: last tcb value was "
		 * +jaundice.getTbcvalue()+""); } } }
		 * 
		 * } }
		 * 
		 * }
		 */
		return null;
	}

	int calculateDateDifference(String date1,String date2){
		LocalDate d1 = LocalDate.parse(date1, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate d2 = LocalDate.parse(date2, DateTimeFormatter.ISO_LOCAL_DATE);

		Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		long diffDays = diff.toDays();
		if (diffDays < 0) {
			diffDays = diffDays * (-1);
		}
		int DOL = (int) Math.ceil(diffDays);
		return DOL;
	}

	LocalDate calculateDateAddition(String date1,int milliseconds){
		LocalDate d1 = LocalDate.parse(date1, DateTimeFormatter.ISO_LOCAL_DATE);
		d1.plusDays(milliseconds);
		return d1;
	}

	java.util.Date calcuateEndDate(BabyDetail babyDetail){
		// 34 Weeks i.e 238
		int actualGestationIndays = (babyDetail.getActualgestationweek() != null ? babyDetail.getActualgestationweek()*7 : 0) + (babyDetail.getActualgestationdays() != null ? babyDetail.getActualgestationdays() : 0);
		int daysNeedtoReach_34_weeks = 238 - actualGestationIndays;     // i.e 34* 7 = 238
		if(babyDetail.getActualgestationweek()!=null && babyDetail.getActualgestationweek() >= 34){
			daysNeedtoReach_34_weeks=0;
		}

		java.util.Date babyBirthDate = babyDetail.getDateofbirth();
		Calendar c = Calendar.getInstance();
		c.setTime(babyBirthDate);
		c.add(Calendar.DATE, daysNeedtoReach_34_weeks);

		// get the date of 34 week days one date to check the order revised/started/continued/stopped
		babyBirthDate = c.getTime();
		return babyBirthDate;
	}

	boolean CheckBabyRespSupportStatus(String uhid){
		boolean defaultValue=false;

		String respSupportIsActiveQuery="select obj from RespSupport as obj where uhid='"+uhid+"' order by creationtime desc";
		List<RespSupport> respSupportList= inicuDao.getListFromMappedObjQuery(respSupportIsActiveQuery);
		if(!BasicUtils.isEmpty(respSupportList) && respSupportList.size()>0){
			RespSupport lastObject=respSupportList.get(0);
			if(lastObject.getIsactive() || lastObject.getRsVentType()!=null){
				defaultValue = true;
			}
		}
		return defaultValue;
	}

	String getThyroidScreeningMessage(BabyDetail babyDetail,List<InvestigationOrdered> investigationOrderedList,int currentDOL){
		String thyroidMessage = "";
		int count = 0;
		StringBuilder thyroidMissingAlter = new StringBuilder();
		try {

			if(babyDetail.getUhid().equalsIgnoreCase("RSHI.0000023452")){
				System.out.println("Hello umesh");
			}

			java.util.Date babyBirthDate = babyDetail.getDateofbirth();
			java.util.Date Dol_5_Date = new java.sql.Date(babyBirthDate.getTime() + 24 * 60 * 60 * 1000 * 4);
			java.util.Date Dol_196_Date = new java.sql.Date(babyBirthDate.getTime() + 7*4*Long.valueOf(24 * 60 * 60 * 1000 *7));
			boolean isGa_lessthan_28_weeks=false;
			int actualGestationIndays = (babyDetail.getActualgestationweek() != null ? babyDetail.getActualgestationweek()*7 : 0) + (babyDetail.getActualgestationdays() != null ? babyDetail.getActualgestationdays() : 0);
			int daysNeedtoReach_28_weeks = 196 - actualGestationIndays;     // i.e 28* 7 = 196
			daysNeedtoReach_28_weeks--;
			if(babyDetail.getActualgestationweek()!=null && babyDetail.getActualgestationweek() >= 28){
				daysNeedtoReach_28_weeks=0;
			}

			if (babyDetail.getActualgestationweek()!=null && babyDetail.getActualgestationweek() <= 28){
				isGa_lessthan_28_weeks = true;
			}

			String queryLocaleDateformatQuery = "SELECT obj FROM LocalConfigurations obj";
			List<LocalConfigurations> localeDate = inicuDao.getListFromMappedObjQuery(queryLocaleDateformatQuery);

			String localDate ="dd-MM-yyyy";
			if(localeDate!=null && localeDate.size()>0){
				localDate = localeDate.get(0).getLocalDate();
			}

			Calendar c = Calendar.getInstance();
			c.setTime(babyBirthDate);
			c.add(Calendar.DATE, daysNeedtoReach_28_weeks);
			Dol_196_Date=c.getTime();

			SimpleDateFormat format1 = new SimpleDateFormat(localDate);
			System.out.println(Dol_196_Date.getTime());
			// Output "Wed Sep 26 14:23:28 EST 2012"

			String formatted = null;
			String dol_5_format = null;

			try {
				formatted = format1.format(Dol_196_Date.getTime());
				dol_5_format = format1.format(Dol_5_Date.getTime());
			} catch(Exception e) { //this generic but you can control another types of exception
				// look the origin of excption
			}


			boolean thyroidDoneAtDolFive = false;
			boolean thyroidDoneAt_28_weeks = false;
			for (InvestigationOrdered investigationOrderObject : investigationOrderedList) {

				java.util.Date senttolab_date = new java.sql.Date(investigationOrderObject.getSenttolab_time().getTime());
				int DOL = calculateDateDifference(babyBirthDate.toString(), senttolab_date.toString())+1;

				// Check Condition one - find entry for DOL#5 date - T4,T3, TSH
				if (DOL == 5) {
					// Found the entry for DOL#5 Now Check for only thyroid screening
					if (investigationOrderObject.getTestcode().equalsIgnoreCase("T3") || investigationOrderObject.getTestcode().equalsIgnoreCase("T4")
							|| investigationOrderObject.getTestcode().equalsIgnoreCase("TSH")) {
						thyroidDoneAtDolFive = true;
					}
				}

				// Check if there's no for DOL#5
				if(DOL >=6 && DOL<=195 && !thyroidDoneAtDolFive){
					if (investigationOrderObject.getTestcode().equalsIgnoreCase("T3") || investigationOrderObject.getTestcode().equalsIgnoreCase("T4")
							|| investigationOrderObject.getTestcode().equalsIgnoreCase("TSH")) {
						thyroidDoneAtDolFive = true;
					}
				}

				// Check for Second Condition - Baby is less than or equal to 28 GA at  Birth
				if(babyDetail.getActualgestationweek() <= 28){
					isGa_lessthan_28_weeks= true;
					// Thyroid screen done at DOL# 196 ?
					if(DOL == daysNeedtoReach_28_weeks){
						if (investigationOrderObject.getTestcode().equalsIgnoreCase("T3") || investigationOrderObject.getTestcode().equalsIgnoreCase("T4")
								|| investigationOrderObject.getTestcode().equalsIgnoreCase("TSH")) {
							thyroidDoneAt_28_weeks = true;
						}
					}

					// Check if the there's no entry for DOL #196
					if(DOL > daysNeedtoReach_28_weeks && !thyroidDoneAt_28_weeks){
						if (investigationOrderObject.getTestcode().equalsIgnoreCase("T3") || investigationOrderObject.getTestcode().equalsIgnoreCase("T4")
								|| investigationOrderObject.getTestcode().equalsIgnoreCase("TSH")) {
							thyroidDoneAt_28_weeks = true;
						}
					}
				}
			}

			thyroidMissingAlter.append("Thyroid Screen at (");
			int tempFlag=0;
			if (!thyroidDoneAtDolFive && currentDOL>=5) {
				count++;
				tempFlag=1;
			}

			if(!thyroidDoneAt_28_weeks && (currentDOL >= daysNeedtoReach_28_weeks) && isGa_lessthan_28_weeks){
				count=count+1;
			}

			if (count == 1 && tempFlag ==1) {
				thyroidMissingAlter.append("DOL 5) is due since "+dol_5_format);
			} else if (count == 1 && tempFlag == 0) {
				thyroidMissingAlter.append("PMA 28 Weeks) is due since "+formatted);
			}else if(count == 2){
				thyroidMissingAlter.append("DOL 5 and PMA 28 Weeks) is due");
			}
		}catch (Exception e){
			System.out.println("Got the Exception: "+e);
		}
		return count > 0 ? thyroidMissingAlter.toString(): "";
	}

	String getNeurosonogramMessage(BabyDetail babyDetail,List<InvestigationOrdered> investigationOrderedList,int currentDOL){
		StringBuilder ultrasoundMissingAlter = new StringBuilder();
		int count = 0;
		try {
			// Four boolean for four different cases
			boolean neurosonogramDoneAtDolOne = false;
			boolean neurosonogramDoneBetDolTwoandThree = false;
			boolean neurosonogramDoneBetDolFourandSeven = false;
			boolean neurosonogramDoneBetDolEightandtwentyEight = false;
			java.util.Date babyBirthDate = babyDetail.getDateofbirth();

			// Check for Neurosonogram i.e Ultrasound (testcode:DGC1355)
			for (InvestigationOrdered investigationOrder : investigationOrderedList) {

				java.util.Date senttolab_date = new java.sql.Date(investigationOrder.getSenttolab_time().getTime());
				int DOL = calculateDateDifference(babyBirthDate.toString(), senttolab_date.toString())+1;

				// Condition for all baby Whose GA at Birth is <= 34
				if (babyDetail.getActualgestationweek() <= 34) {

					// check for condition one i.e at DOL#1
					if (DOL == 1 || DOL == 0) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneAtDolOne = true;
						}
					}

					if (DOL == 2 && neurosonogramDoneAtDolOne == false) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneAtDolOne = true;
						}
					}

					// Check for case two i.e DOL#3
					if (DOL >= 2 && DOL <= 3) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneBetDolTwoandThree = true;
						}
					}

					if (DOL >= 4 && DOL <= 6 && neurosonogramDoneBetDolTwoandThree == false) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneBetDolTwoandThree = true;
						}
					}

					// Check for case three i.e DOL#7
					if (DOL >= 4 && DOL <= 7) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneBetDolFourandSeven = true;
						}
					}

					if (DOL >= 8 && DOL <= 27 && neurosonogramDoneBetDolFourandSeven == false) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneBetDolFourandSeven = true;
						}
					}

					// Check for Case four i.e DOL#28
					if (DOL >= 8 && DOL <= 28) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneBetDolEightandtwentyEight = true;
						}
					}

					if (DOL >= 29 && neurosonogramDoneBetDolEightandtwentyEight == false) {
						if (investigationOrder.getTestcode().equalsIgnoreCase("DGC1355")) {
							neurosonogramDoneBetDolEightandtwentyEight = true;
						}
					}
				}
			}

			ultrasoundMissingAlter.append("Neurosonogram (DOL# ");
			if (!neurosonogramDoneAtDolOne && currentDOL >= 1) {
				count++;
				ultrasoundMissingAlter.append("1");
			}
			if (!neurosonogramDoneBetDolTwoandThree && currentDOL >= 2) {
				if (!neurosonogramDoneAtDolOne) {
					count++;
					ultrasoundMissingAlter.append(", 3");
				} else {
					count++;
					ultrasoundMissingAlter.append("3");
				}
			}
			if (!neurosonogramDoneBetDolFourandSeven && currentDOL >= 4) {
				if (!neurosonogramDoneAtDolOne || !neurosonogramDoneBetDolTwoandThree) {
					count++;
					ultrasoundMissingAlter.append(", 7");
				} else {
					count++;
					ultrasoundMissingAlter.append("7");
				}
			}
			if (!neurosonogramDoneBetDolEightandtwentyEight && currentDOL >= 8) {
				if (!neurosonogramDoneAtDolOne || !neurosonogramDoneBetDolTwoandThree || !neurosonogramDoneBetDolFourandSeven) {
					count++;
					ultrasoundMissingAlter.append(" and 28");
				} else {
					count++;
					ultrasoundMissingAlter.append("28");
				}
			}

			if (count > 1) {
				ultrasoundMissingAlter.append(") are due.");
			} else if (count == 1) {
				ultrasoundMissingAlter.append(") is due.");
			}
		}catch (Exception e){
			System.out.println("Got the Exception:"+e);
		}

		return ((babyDetail.getGestationweekbylmp()!=null && babyDetail.getGestationweekbylmp()>34) || babyDetail.getGestationweekbylmp()==null) ? "": (count >0 ? ultrasoundMissingAlter.toString(): "");
	}

	String getProbioticsMessage(BabyDetail babyDetail,String uhid){
		String probioticsMessage="";
		java.util.Date enddate=calcuateEndDate(babyDetail);

		System.out.println("End Date Probiotics "+enddate.toString());
		// Alert to show query
		String babyPrescriptionQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + uhid + "' and medicationtype='TYPE0026' and date(startdate)<='"+enddate+"'";
		List<BabyPrescription> probioticsPrescription = inicuDao.getListFromMappedObjQuery(babyPrescriptionQuery);

		if(probioticsPrescription!=null && probioticsPrescription.size()>0){

			// Alert to dismiss query
			String babyPrescriptiondismissQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + uhid + "' and medicationtype='TYPE0026' and date(startdate)>='"+enddate+"'";
			List<BabyPrescription> probioticsPrescriptionDismiss = inicuDao.getListFromMappedObjQuery(babyPrescriptiondismissQuery);

			probioticsMessage = "Review Probiotics prescription";
			
			if (!BasicUtils.isEmpty(probioticsPrescriptionDismiss) && probioticsPrescriptionDismiss.size() > 0) {
					for (BabyPrescription babyPresObject : probioticsPrescriptionDismiss) {
						try {
							java.util.Date startDate=new Date(babyPresObject.getStartdate().getTime());

							// revise,continue and stopped case
							if ((babyPresObject.getIsactive() != null && !babyPresObject.getIsactive()) || (babyPresObject.getIsEdit() != null && babyPresObject.getIsEdit())
									|| (babyPresObject.getIsContinue() != null && babyPresObject.getIsContinue())) {
								probioticsMessage = "";
							}

							// started case i.e started the medication on the same day
							if((babyPresObject.getIsactive() != null && babyPresObject.getIsactive())){
								probioticsMessage = "";
							}
						} catch (Exception e) {
							System.out.println("Got the Exception:" + e);
						}
					}
				}
			}
		return probioticsMessage;
	}

	String getCaffeineMessage(BabyDetail babyDetail,String uhid){
		String caffeineMessage = "";
		
		java.util.Date enddate=calcuateEndDate(babyDetail);
		System.out.println("End Date Caffeine "+enddate.toString());
		// Alert to show query
		String caffeinePrescriptionQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + uhid + "' and medicationtype='TYPE0019' and date(startdate)<='"+enddate+"'";
		List<BabyPrescription> caffeinePrescriptionsList = inicuDao.getListFromMappedObjQuery(caffeinePrescriptionQuery);

		if(caffeinePrescriptionsList!=null && caffeinePrescriptionsList.size()>0){
			boolean respStatus=CheckBabyRespSupportStatus(uhid);
			if(respStatus == false) {
				caffeineMessage = "Review Caffeine prescription";

				// Alert to dismiss query
				String babyPrescriptiondismissQuery = "SELECT obj FROM BabyPrescription as obj where uhid='" + uhid + "' and medicationtype='TYPE0019' and date(startdate)>='"+enddate+"'";
				List<BabyPrescription> caffeinePrescriptionDismiss = inicuDao.getListFromMappedObjQuery(babyPrescriptiondismissQuery);

				if (!BasicUtils.isEmpty(caffeinePrescriptionDismiss) && caffeinePrescriptionDismiss.size() > 0) {
						for (BabyPrescription babyPresObject : caffeinePrescriptionDismiss) {
							try {
								java.util.Date startDate = new Date(babyPresObject.getStartdate().getTime());

								// revise,continue and stopped case
								if ((babyPresObject.getIsactive() != null && !babyPresObject.getIsactive()) || (babyPresObject.getIsEdit() != null && babyPresObject.getIsEdit())
										|| (babyPresObject.getIsContinue() != null && babyPresObject.getIsContinue())) {
									caffeineMessage = "";
								}

								// started case i.e started the medication on the same day
								if ((babyPresObject.getIsactive() != null && babyPresObject.getIsactive())) {
									caffeineMessage = "";
								}
							} catch (Exception e) {
								System.out.println("Got the Exception:" + e);
							}
						}
					}
			}
		}
		return caffeineMessage;
	}

	@Override
	public HashMap<Object, Object> getNotifications(String branchName) {

		HashMap<Object, Object> notificationObj = new HashMap<Object, Object>();
		// get notifications of the assessments that lasts greater than 12 hrs
		try {
			String queryAssessmentsGT12hrs = "select obj from VwNotificationFinal obj";
			List<VwNotificationFinal> listAssessmentslastGT12hrs = inicuDao.
					getListFromMappedObjQuery(queryAssessmentsGT12hrs);
			
			//To Fetch Unique Uhid present on dashboard
			String uhidQuery = "select obj from BabyDetail as obj where admissionstatus is 'true' and branchname='"+branchName+"'";
			List<BabyDetail> dashboardUhidList =  inicuDao.getListFromMappedObjQuery(uhidQuery);
			HashMap<String,BabyDetail> babyMap=new HashMap<>();
			List<String> uniqueUhidList = new ArrayList<>();
			for(int i=0;i<dashboardUhidList.size();i++) {
				if(!uniqueUhidList.contains(dashboardUhidList.get(i).getUhid())) {
					babyMap.put(dashboardUhidList.get(i).getUhid(),dashboardUhidList.get(i));
					uniqueUhidList.add(dashboardUhidList.get(i).getUhid());
				}
			}
			
			//From and To Dates For NursingVitalParameter
			Timestamp fromDateForNursingVitalParameter = new Timestamp(System.currentTimeMillis());
			fromDateForNursingVitalParameter.setHours(7);
			fromDateForNursingVitalParameter.setMinutes(59);
			fromDateForNursingVitalParameter.setSeconds(00);
			fromDateForNursingVitalParameter.setNanos(0);
			
			int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
					- TimeZone.getDefault().getRawOffset();
			Timestamp toDateForNursingVitalParameter = new Timestamp(System.currentTimeMillis() + offset);
			
			//From and To Dates For NursingIntakeOutput
			Timestamp fromDateForNursingIntakeOutput = new Timestamp(fromDateForNursingVitalParameter.getTime() - offset);
			Timestamp toDateForNursingIntakeOutput = new Timestamp(System.currentTimeMillis());
			
			//BP List
			List<Object> latestBpList = new ArrayList<>();
			for(int i=0;i<uniqueUhidList.size();i++) {
				String queryForBp = "select obj from NursingVitalparameter as obj where uhid='" 
							+ uniqueUhidList.get(i) + "' and entrydate >= '" + fromDateForNursingVitalParameter 
							+ "' and entrydate <= '" + toDateForNursingVitalParameter + "' order by entrydate desc";
				List<NursingVitalparameter> nursingVitalParameterBpList = inicuDao.getListFromMappedObjQuery(queryForBp);
				for(int j=0;j<nursingVitalParameterBpList.size();j++) {
					if((!BasicUtils.isEmpty(nursingVitalParameterBpList.get(j).getSystBp())) || 
					   (!BasicUtils.isEmpty(nursingVitalParameterBpList.get(j).getDiastBp())) || 
					   (!BasicUtils.isEmpty(nursingVitalParameterBpList.get(j).getMeanBp()))){
						List<Object> nursingVitalParameterBpObj = new ArrayList<>();
						nursingVitalParameterBpObj.add(nursingVitalParameterBpList.get(j).getUhid());
						String bpString = "";
						if(!BasicUtils.isEmpty(nursingVitalParameterBpList.get(j).getSystBp())){
							bpString += nursingVitalParameterBpList.get(j).getSystBp(); 
						}
						else {
							bpString += " ";
						}
						
						if(!BasicUtils.isEmpty(nursingVitalParameterBpList.get(j).getDiastBp())){
							bpString += "/" + nursingVitalParameterBpList.get(j).getDiastBp(); 
						}
						else {
							bpString += "/ ";
						}
						
						if(!BasicUtils.isEmpty(nursingVitalParameterBpList.get(j).getMeanBp())){
							bpString += "(" + nursingVitalParameterBpList.get(j).getMeanBp() + ")"; 
						}
						else {
							bpString += "( )";
						}
						nursingVitalParameterBpObj.add(bpString);					
						latestBpList.add(nursingVitalParameterBpObj);
						break;
					}
					else {
						
					}
				}
			}
			
			//RBS List
			List<Object> latestRbsList = new ArrayList<>();
			for(int i=0;i<uniqueUhidList.size();i++) {
				String queryForRbs = "select obj from NursingVitalparameter as obj where uhid='" 
							+ uniqueUhidList.get(i) + "' and rbs IS NOT NULL and entrydate >= '" + fromDateForNursingVitalParameter 
							+ "' and entrydate <= '" + toDateForNursingVitalParameter + "' order by entrydate desc";
				List<NursingVitalparameter> nursingVitalParameterRbsList = inicuDao.getListFromMappedObjQuery(queryForRbs);
				for(int j=0;j<nursingVitalParameterRbsList.size();j++) {
					if(!BasicUtils.isEmpty(nursingVitalParameterRbsList.get(j).getRbs())) {
						List<Object> nursingVitalParameterRbsObj = new ArrayList<>();
						nursingVitalParameterRbsObj.add(nursingVitalParameterRbsList.get(j).getUhid());
						nursingVitalParameterRbsObj.add(nursingVitalParameterRbsList.get(j).getRbs());
						latestRbsList.add(nursingVitalParameterRbsObj);
						break;
					}
				}
			}
			
			//Total Urine Elapsed List
			List<Object> totalUrineElapsedList = new ArrayList<>();
			double timeDiffernce = (toDateForNursingIntakeOutput.getTime() - fromDateForNursingIntakeOutput.getTime());
			timeDiffernce /= (60 * 60 * 1000);
			timeDiffernce = (double) Math.round(timeDiffernce * 100)/100;
			for(int i=0;i<uniqueUhidList.size();i++) {
				String queryForTotalUrineElapsed = "select obj from NursingIntakeOutput as obj where uhid='" 
							+ uniqueUhidList.get(i) + "' and urine IS NOT NULL and entry_timestamp >= '" + fromDateForNursingIntakeOutput 
							+ "' and entry_timestamp <= '" + toDateForNursingIntakeOutput + "' order by entry_timestamp desc";
				List<NursingIntakeOutput> nursingIntakeOutputTotalUrineElapsedList = inicuDao.
						getListFromMappedObjQuery(queryForTotalUrineElapsed);
				if(!BasicUtils.isEmpty(nursingIntakeOutputTotalUrineElapsedList)) {
					double totalUrine = 0f;
					for(int j=0;j<nursingIntakeOutputTotalUrineElapsedList.size();j++) {
						if(!BasicUtils.isEmpty(nursingIntakeOutputTotalUrineElapsedList.get(j).getUrine())) {
							totalUrine += Float.parseFloat(nursingIntakeOutputTotalUrineElapsedList.get(j).getUrine());
						}
					}
					
					if(Double.compare(totalUrine,0f) != 0) {
						totalUrine /= timeDiffernce;
						totalUrine = (double) Math.round(totalUrine * 100)/100;
					}
					
					Float weight = 0f;
					if(Double.compare(totalUrine,0f) != 0) {
						String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='"
								+ uniqueUhidList.get(i) + "' order by visitdate desc";
						List<BabyVisit> currentBabyVisitList = inicuDao.getListFromMappedObjQuery(queryCurrentBabyVisit);
						if (currentBabyVisitList != null && currentBabyVisitList.size() > 0) {
							if(currentBabyVisitList.get(0).getWeightForCal() != null) {
								weight = currentBabyVisitList.get(0).getWeightForCal();
							}
							else {
								if(currentBabyVisitList.get(0).getCurrentdateweight() != null) {
									weight = currentBabyVisitList.get(0).getCurrentdateweight();
								}	
							}
							
							weight = weight / 1000;
						}
					}
					
					if(Float.compare(weight,0f) !=0) {
						totalUrine /= weight;
						totalUrine = (double) Math.round(totalUrine * 100)/100;
					}
					
					if(Double.compare(totalUrine,0f) != 0) {
						List<Object> nursingIntakeOutputTotalUrineElapsedObj = new ArrayList<>();
						nursingIntakeOutputTotalUrineElapsedObj.add(uniqueUhidList.get(i));
						nursingIntakeOutputTotalUrineElapsedObj.add(Double.toString(totalUrine));
						totalUrineElapsedList.add(nursingIntakeOutputTotalUrineElapsedObj);
					}
				}
			}


			/*
			*  Updated by Umesh@ 29th March, 2020
			* */

			List<Object> thyroidScreeningAlert=new ArrayList<>();
			List<Object> neurosonogramAlertList=new ArrayList<>();
			List<Object> probioticsAlertList=new ArrayList<>();
			List<Object> caffeineAlertList=new ArrayList<>();

			for(int i=0;i<uniqueUhidList.size();i++) {

				// Get the Baby Object
				BabyDetail babyDetail = babyMap.get(uniqueUhidList.get(i));
				if (babyDetail != null) {
					// Calculate the DOL#5 Date
					java.util.Date todaysDate = new java.util.Date();
					java.util.Date currentDate = new java.sql.Date(todaysDate.getTime());
					java.util.Date babyBirthDate = babyDetail.getDateofbirth();
					// Curent DOL
					int currentDOL = calculateDateDifference(babyBirthDate.toString(), currentDate.toString());

					// Sent_to_lab time not null to make sure nurse has executed the doctor investigation order
					String investigationOrderQuery = "select obj from InvestigationOrdered as obj where uhid='"
							+ uniqueUhidList.get(i) + "' and senttolab_time IS NOT NULL order by senttolab_time";
					List<InvestigationOrdered> investigationOrderedList = inicuDao.getListFromMappedObjQuery(investigationOrderQuery);

					// Thyroid Alert
					String thyroidMessage=getThyroidScreeningMessage(babyDetail,investigationOrderedList,currentDOL);
					if(thyroidMessage!=null && thyroidMessage.length()>0) {
						List<Object> thyroidScreening = new ArrayList<>();
						thyroidScreening.add(uniqueUhidList.get(i));
						thyroidScreening.add(thyroidMessage);
						thyroidScreeningAlert.add(thyroidScreening);
					}

					// Neurosonogram Alert
					String ultrasoundMissingAlter=getNeurosonogramMessage(babyDetail,investigationOrderedList,currentDOL);
					if (ultrasoundMissingAlter!=null && ultrasoundMissingAlter.length()>0) {
						List<Object> ultrasound = new ArrayList<>();
						ultrasound.add(uniqueUhidList.get(i));
						ultrasound.add(ultrasoundMissingAlter);
						neurosonogramAlertList.add(ultrasound);
					}

					// Probiotics Alert
					String probioticsMessage=getProbioticsMessage(babyDetail,uniqueUhidList.get(i));
					if (probioticsMessage != null && probioticsMessage.length() > 0) {
						List<Object> probiotic = new ArrayList<>();
						probiotic.add(uniqueUhidList.get(i));
						probiotic.add(probioticsMessage);
						probioticsAlertList.add(probiotic);
					}

					// Caffeine Alert
					String caffeineMessage=getCaffeineMessage(babyDetail,uniqueUhidList.get(i));
					if (caffeineMessage!=null && caffeineMessage.length() > 0) {
						List<Object> caffeine = new ArrayList<>();
						caffeine.add(uniqueUhidList.get(i));
						caffeine.add(caffeineMessage);
						caffeineAlertList.add(caffeine);
					}
				}
			}

			notificationObj.put("assessmentGT12Hrs", listAssessmentslastGT12hrs);
			notificationObj.put("BPList", latestBpList);
			notificationObj.put("RBSList", latestRbsList);
			notificationObj.put("TotalUrineElapsedList", totalUrineElapsedList);

			notificationObj.put("ThyroidScreening", thyroidScreeningAlert);
			notificationObj.put("Neurosonogram", neurosonogramAlertList);
			notificationObj.put("Probiotics", probioticsAlertList);
			notificationObj.put("Caffeine", caffeineAlertList);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return notificationObj;
	}
}
