package com.inicu.postgres.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.utility.InicuPostgresUtililty;
@Repository
public class SettingsDaoImp implements SettingDao{
	
	@Autowired
	InicuPostgresUtililty inicuPostgersUtil;

	@Override
	public void executeInsertQuery(String query) {
		// TODO Auto-generated method stub
		inicuPostgersUtil.executeInsertQuery(query);
		
	}

	@Override
	public void saveObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		inicuPostgersUtil.saveObject(obj);
	}

	@Override
	public List getListFromMappedObjNativeQuery(String queryStr) {
		List listObj = null;
		try {
			listObj = inicuPostgersUtil
					.executeMappedObjectCustomizedQuery(queryStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public List getListFromNativeQuery(String queryStr) {
		// TODO Auto-generated method stub
		return inicuPostgersUtil.getListFromNativeQuery(queryStr);
	}
	@Override
	public void delObject(String query) throws Exception {
		// TODO Auto-generated method stub
		inicuPostgersUtil.updateOrDelQuery(query);
		
	}

	@Override
	public void delObjectNative(String query) throws Exception {
		inicuPostgersUtil.updateOrDelNativeQuery(query);
	}

	
	@Override
	public void updateNativeQuery(Object obj)throws Exception {
		inicuPostgersUtil.saveObject(obj);
	}
}
