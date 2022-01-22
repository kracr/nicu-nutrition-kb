package com.inicu.postgres.controller;

import com.inicu.models.*;
import com.inicu.postgres.dao.LoginDao;
import com.inicu.postgres.daoImpl.LoginDoaImpl;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.RefHospitalbranchname;
import com.inicu.postgres.entities.User;
import com.inicu.postgres.service.LoginService;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import com.inicu.postgres.JWT.*;
import scala.None;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Dipin
 * @version 1.0
 */

@RestController
public class LoginController implements ErrorController {

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	private LoginService loginService;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	HttpSession httpSession;

	@Autowired
	PatientService patientService;

	@Autowired
	JWTAuthenticationFilter JWTFilter;

	@RequestMapping(value = "/inicu/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> loginResult(
			@Valid @RequestBody UserLoginInfoPojo userinfo,
			BindingResult bindingResult) {

		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		if (bindingResult.hasErrors()) {
			return BasicUtils.handleInvalidInputJson(userinfo, bindingResult);
		} else {
			System.out.println("valid input json: "
					+ BasicUtils.getObjAsJson(userinfo));
			httpSession.setAttribute(BasicConstants.SCHEMA_NAME,
					userinfo.getSchemaName());
		}

		Object userName=null;
		Object userPassword = null;
		Object branchName = null;
		Object branchType=null;

		try {
			userName = userinfo.getUsername();
			userPassword = userinfo.getPassword();
			branchName = userinfo.getBranchName();
			branchType=userinfo.getBranchType();
			if(branchType!=null)
			{
				responseObj.setHospitalType((String)branchType);
			}
			// Type hospital and command center
			if(branchType!=null && ((String) branchType).equalsIgnoreCase("Command Center")){
			 	// get the Branch Name
				String branchTypeReturned=loginDao.getHospitalBranchWithType((String)branchType);
				if(branchTypeReturned!=null && !BasicUtils.isEmpty(branchTypeReturned)){
					branchName=branchTypeReturned;
				}
			}
			if(branchName==null) {
				System.out.println("Login Failure..!!");
				responseObj.setType("failure");
				return new ResponseEntity<ResponseMessageWithResponseObject>(
						responseObj, HttpStatus.OK);
			}


			responseObj = (ResponseMessageWithResponseObject) loginService
					.validateUser(userName, userPassword, branchName);

					if(branchType!=null && ((String) branchType).equalsIgnoreCase("Command Center")){
						// get the Branch Name
						String  getListofHospitals="Select branchname,hospital_type,hospitalimage from ref_hospitalbranchname where hospital_type='Satellite' ";
						List<RefHospitalbranchname> branchNameWithTypeList = loginDao.executeNativeQuery(getListofHospitals);
						if (!BasicUtils.isEmpty(branchNameWithTypeList)) {
							responseObj.setBranchNameWithTypeList(branchNameWithTypeList);
						}
				   }
		} catch (Exception e) {
			responseObj.setType("failure");
			responseObj.setMessage("Exception accur during validate login !!");
			e.printStackTrace();

			return new ResponseEntity<ResponseMessageWithResponseObject>(
					responseObj, HttpStatus.OK);

		}
		if (responseObj == null
				|| responseObj.getType().equalsIgnoreCase("failure")) {
			return new ResponseEntity<ResponseMessageWithResponseObject>(
					responseObj, HttpStatus.OK);
		} else if (responseObj.getType().equalsIgnoreCase("success")) {
			// if login successful then save login details to the logindetails
			// table.
			Object loginDetailsObj = loginService.saveLoginDetails(userName,branchName.toString());
			if (loginDetailsObj != null) {
				// if User is authenticated successfully then generate a JWT token for it

				Object dataObject=responseObj.getReturnedObject();
				User user=(User)responseObj.getReturnedObject();
				BigInteger userId=user.getId();
				String userRole= user.getUserRole();
				String tokenValue=JWTFilter.getJWTtoken(userId.intValue(),(String)userName,userRole,user.getBranchName());
				if(tokenValue!=null )
				{
					responseObj.setAccess_token(tokenValue);
				}else{
					System.out.println("token Value was empty");
				}
				return new ResponseEntity<ResponseMessageWithResponseObject>(
						responseObj, HttpStatus.OK);
			} else {
				responseObj.setType("failure");
				return new ResponseEntity<ResponseMessageWithResponseObject>(
						responseObj, HttpStatus.OK);
			}
		} else {
			System.out.println("Login Failure..!!");
			responseObj.setType("failure");
			return new ResponseEntity<ResponseMessageWithResponseObject>(
					responseObj, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/inicu/logout/{userId}/{branchname}",method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageObject> logOutUser(
			@PathVariable("userId") String userId,@PathVariable("branchname") String branchname) {
		ResponseMessageObject responseObj = new ResponseMessageObject();
		Object isLogOutUser = loginService.logOutUser(userId,branchname);
		if (isLogOutUser != null) {
			responseObj.setMessage("User Logout Successfully..!!");
			responseObj.setType("success");
		} else {
			responseObj.setMessage("Logout Failed..!!");
			responseObj.setType("failure");
		}
		return new ResponseEntity<ResponseMessageObject>(responseObj,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/resetPassword",method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> resetPassword(
			@Valid @RequestBody ResetPasswordInfoPOJO userinfo,
			BindingResult bindingResult) {

		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		if (bindingResult.hasErrors()) {
			return BasicUtils.handleInvalidInputJson(userinfo, bindingResult);
		} else {
			System.out.println("valid input json: " + BasicUtils.getObjAsJson(userinfo));
		}

		Object userId = null;
		Object userPassword = null;
		Object branchName = null;

		try {
			userId = userinfo.getUserName();
			userPassword = userinfo.getCurrentPassword();
			branchName = userinfo.getBranchname();
			responseObj = (ResponseMessageWithResponseObject) loginService
					.validateUser(userId, userPassword, branchName);
		} catch (Exception e) {
			responseObj.setType("failure");
			responseObj.setMessage("Exception accur during validate login !!");
			e.printStackTrace();
			return new ResponseEntity<ResponseMessageWithResponseObject>(
					responseObj, HttpStatus.OK);
		}

		if (responseObj == null || responseObj.getType().equalsIgnoreCase("failure")) {
			return new ResponseEntity<ResponseMessageWithResponseObject>(
					responseObj, HttpStatus.OK);
		} else if (responseObj.getType().equalsIgnoreCase("success")) {
			// if login successful then save login details to the logindetails table.

			User myUser = (User) responseObj.getReturnedObject();

			String newPassword = userinfo.getNewPassword();
			String confirmPassword = userinfo.getConfirmNewPassword();

			if (newPassword.equals(confirmPassword)) {

				Object loginDetailsObj = loginService.saveResetPasswordDetails(myUser, userinfo.getNewPassword());

				User resetPasswordDetailsObj = (User) loginDetailsObj;
				if (resetPasswordDetailsObj != null) {
					responseObj.setMessage("Successfullt changed the Password");
					return new ResponseEntity<ResponseMessageWithResponseObject>(
							responseObj, HttpStatus.OK);
				} else {
					responseObj.setType("failure");
					responseObj.setMessage("Unable to save the new password in Database");
					return new ResponseEntity<ResponseMessageWithResponseObject>(responseObj, HttpStatus.OK);
				}
			} else {
				System.out.println("Login Failure..!!");
				responseObj.setType("failure");
				responseObj.setMessage("New Password and confirm Password doesn't match");
				return new ResponseEntity<ResponseMessageWithResponseObject>(
						responseObj, HttpStatus.OK);
			}
		}
		else {
			System.out.println("Login In Session ..!!");
			responseObj.setType("failure");
			responseObj.setMessage("Login Session Timeout! Login Again to reset the password");
			return new ResponseEntity<ResponseMessageWithResponseObject>(
					responseObj, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/inicu/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject>  forgotPassword(
			@Valid @RequestBody ForgotPasswordInfoPojo userinfo,
			BindingResult bindingResult){

			ResponseMessageWithResponseObject response=new ResponseMessageWithResponseObject();

			Object user=null;
			 String  useremail=userinfo.getEmailAddress();		
			 String  branchname=userinfo.getBranchname();
			
			 try {
//					 String username=userinfo.getUserName();
					  user= loginService
							.checkUserExistence(useremail,branchname);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("No Such User Found in the database");
					return new ResponseEntity<ResponseMessageWithResponseObject>(
						 response, HttpStatus.OK);
				}
			if(user!=null) {
				response.setMessage("New Password has been saved and Sent to your email");
				return new ResponseEntity<ResponseMessageWithResponseObject>(
						response, HttpStatus.OK);
			}
			response.setMessage("No User Found");
		return new ResponseEntity<ResponseMessageWithResponseObject>(
				response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDashboards/beds", method = RequestMethod.POST)
	private String getDashboardsBeds() {
		String bedsJson = "";
		try {
			byte[] bedsInByte = Files.readAllBytes(Paths
					.get("D:\\iNICU-DATA-DIR\\dashboard.txt"));
			bedsJson = new String(bedsInByte, Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bedsJson;
	}

	@RequestMapping(value = "/inicu/getInicuSettingPresference", method = RequestMethod.GET)
	public ResponseEntity<LoginPOJO> getInicuSettingPreference() {
		LoginPOJO settingPresference = loginService.getInicuSettingPreference();
		return new ResponseEntity<LoginPOJO>(settingPresference, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNotifications/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<HashMap<Object, Object>> getNotifications(@PathVariable("branchName") String branchName) {
		HashMap<Object, Object> notificationsList = loginService
				.getNotifications(branchName);
		return new ResponseEntity<HashMap<Object, Object>>(notificationsList,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/testSave/{date}", method = RequestMethod.GET)
	public void testSave(@PathVariable("date") String date) {
		// set time zone default in case of get...
		Calendar cal = Calendar.getInstance();
		// TimeZone.setDefault(TimeZone.getTimeZone(CalendarUtility.UTC_TIME_ZONE_ID));

		try {
			// String datet =
			// CalendarUtility.timeStampFormat.format(cal.getTime());
			// System.out.println(datet);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/testGet", method = RequestMethod.POST)
	public void testGet() {
		httpSession.setAttribute(BasicConstants.SCHEMA_NAME,
				"inicudbKdahSchema");
		String query = "Select obj from BabyDetail obj ";
		List<BabyDetail> babyDetailList = loginService.testService(query);

		/*
		 * httpSession.setAttribute(BasicConstants.SCHEMA_NAME,
		 * "inicudbKalawatiSchema"); String query2 =
		 * "Select obj from test obj "; List babyDetailList2 =
		 * loginService.testService(query2);
		 */
		System.out.println(babyDetailList.get(0));

	}

	private final static String ERROR_PATH = "/error";

	/**
	 * Supports the HTML Error View
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH, produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request) {
		return new ModelAndView("index");
	}

	/**
	 * Supports other formats like JSON, XML
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public ResponseEntity<Object> error(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		return new ResponseEntity<Object>(null, status);
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception ex) {
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
