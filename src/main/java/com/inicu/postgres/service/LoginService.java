package com.inicu.postgres.service;

import com.inicu.models.InicuNotificationPOJO;
import com.inicu.models.LoginPOJO;
import com.inicu.postgres.entities.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author iNICU
 *
 */
@Repository
public interface LoginService {

	
	Object validateUser(Object userId, Object userPassword, Object branchName);

	Object saveLoginDetails(Object userId,String branchname);
	
	Object checkUserExistence(Object useremail, Object branchName);

	Object saveResetPasswordDetails(User user,Object Password);

	Object logOutUser(String userId,String branchname);

	LoginPOJO getInicuSettingPreference();
	
	InicuNotificationPOJO getInicuNotifications(String uhid);

	List testService(String query);

	HashMap<Object,Object> getNotifications(String branchName);
	
	

	
}
