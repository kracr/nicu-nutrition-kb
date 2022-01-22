package com.inicu.postgres.dao;

import com.inicu.postgres.entities.User;

import java.util.List;


/**
 * 
 * @author Vikash Oxyent  
 *
 */
public interface LoginDao {

	Object getUserWithUserId(Object userId, Object userPassword, Object branchName, String schemaName);
	
	Object getUserWithUsernameOrEmail(Object useremail,Object branchName);

	Object saveObject(Object objectToSave);

	void updateObject(Object obj) throws Exception;

	Object saveNewPassword(User user, Object password);
	
	List executeNativePsqlQuery(String queryLogout);

	List getListFromNativeQuery(String queryStr);

	List getListFromMappedObjNativeQuery(String queryStr);

	void testDao(String query);

	String getHospitalBranchWithType(String branchType);

	List executeNativeQuery(String maxQuery);

	String getHospitalSatelliteName();
	
	Object getUserActiveStatus(Object username , Object password , Object branchName);
}
