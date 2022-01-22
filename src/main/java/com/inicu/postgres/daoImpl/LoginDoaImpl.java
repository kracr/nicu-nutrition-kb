package com.inicu.postgres.daoImpl;

import com.inicu.postgres.dao.LoginDao;
import com.inicu.postgres.entities.RefHospitalbranchname;
import com.inicu.postgres.entities.User;
import com.inicu.postgres.service.PasswordService;
import com.inicu.postgres.utility.InicuPostgresUtililty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author iNICU
 *
 */
@Repository
public class LoginDoaImpl implements LoginDao {

	@Autowired
	InicuPostgresUtililty postgresUtil;

	@Autowired
	PasswordService passwordService;

	@Autowired
	InicuPostgresUtililty inicuPostgersUtil;

	@Override
	@SuppressWarnings("unchecked")
	public Object getUserWithUserId(Object userId, Object userPassword, Object branchName, String schemaName) {

		String query = "select u from User u where upper(username)='" + userId.toString().toUpperCase() + "' and password='"
				+ userPassword.toString() + "' and active=1 and branchname = '" + branchName.toString() + "'";
		List<User> userList = null;
		try {
			userList = postgresUtil.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userList != null && userList.size() > 0) {
			User obj = userList.get(0);
			obj.setLoggedUser(obj.getUserName());
			return userList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List executeNativeQuery(String query) {
		return inicuPostgersUtil.executePsqlDirectQuery(query);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getHospitalSatelliteName() {
		String query = "select u from RefHospitalbranchname u where hospital_type='Satellite'";
		List<RefHospitalbranchname> hospitalList = null;
		try {
			hospitalList = postgresUtil.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hospitalList != null && hospitalList.size() > 0) {
			RefHospitalbranchname obj = hospitalList.get(0);
			return obj.getBranchname();
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getHospitalBranchWithType(String branchType) {
		String query = "select u from RefHospitalbranchname u where hospital_type='" + branchType.toString() + "'";
		List<RefHospitalbranchname> hospitalList = null;
		try {
			hospitalList = postgresUtil.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hospitalList != null && hospitalList.size() > 0) {
			RefHospitalbranchname obj = hospitalList.get(0);
			return obj.getBranchname();
		} else {
			return null;
		}
	}

	@Override
	public Object getUserWithUsernameOrEmail(Object useremail,Object branchName) {

		String query="select u from User u where emailaddress='" +useremail.toString()+"' and branchname='"+branchName.toString()+"' and active=1";
		
		List<User> userList = null;
		try {
			userList = postgresUtil.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userList != null && userList.size() > 0) {
			User obj = userList.get(0);
			return userList.get(0);
		}
		return null;
	}

	@Override
	public Object saveNewPassword(User user,Object newPassword){
		String encPass = passwordService.encryptPassword(newPassword.toString());
		user.setPassword(encPass);
		try {
			User result=(User)postgresUtil.saveObject(user);
			return result;
		 }catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateObject(Object obj) throws Exception {
		inicuPostgersUtil.updateObject(obj);
	}

	@Override
	public Object saveObject(Object objectToSave) {
		try {
			return postgresUtil.saveObject(objectToSave);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List getListFromNativeQuery(String queryStr) {
		List listObj = null;
		try {
			listObj = postgresUtil.executeMappedObjectCustomizedQuery(queryStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public List executeNativePsqlQuery(String queryLogout) {
		return postgresUtil.executePsqlDirectQuery(queryLogout);
	}

	@Override
	public List getListFromMappedObjNativeQuery(String queryStr) {
		List listObj = null;
		try {
			listObj = postgresUtil.executeMappedObjectCustomizedQuery(queryStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public void testDao(String query) {
		try {
			List lsit = postgresUtil.testPostGresQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getUserActiveStatus(Object username, Object password, Object branchName) {
		// TODO Auto-generated method stub
		String query = "select u from User u where userName='" + username.toString() + "' and password='"
				+ password.toString() + "' and  branchname = '" + branchName.toString() + "'";
		List<User> userList = null;
		try {
			userList = postgresUtil.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userList != null && userList.size() > 0) {
			User obj = userList.get(0);
			Object status = obj.getActive();
//			obj.setLoggedUser(obj.getUserName());
			return status;
		} else {
			return null;
		}	}
}
