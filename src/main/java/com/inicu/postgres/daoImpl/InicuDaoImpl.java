package com.inicu.postgres.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class InicuDaoImpl implements InicuDao {

	@Autowired
	InicuPostgresUtililty inicuPostgresUtililty;

	@Override
	public List<?> getListFromMappedObjQuery(String queryStr) {
		List<?> listObj = null;
		try {
			listObj = inicuPostgresUtililty.executeMappedObjectCustomizedQuery(queryStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public List<?> getListFromMappedObjRowLimitQuery(String queryStr, Integer maxCount) {
		List<?> listObj = null;
		try {
			listObj = inicuPostgresUtililty.executeMappedObjectCustomizedRowLimitQuery(queryStr, maxCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public void updateOrDeleteQuery(String query) {
		try {
			inicuPostgresUtililty.updateOrDelQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrDeleteNativeQuery(String query) {
		try {
			inicuPostgresUtililty.updateOrDelNativeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<?> saveMultipleObject(List<?> obj) throws Exception {
		return inicuPostgresUtililty.saveMultipleObject(obj);
	}

	@Override
	public Object saveObject(Object obj) throws Exception {
		return inicuPostgresUtililty.saveObject(obj);
	}

	@Override
	public void executeInsertQuery(String query) {
		inicuPostgresUtililty.executeInsertQuery(query);
	}

	@Override
	public List<?> getListFromNativeQuery(String queryStr) {
		return inicuPostgresUtililty.getListFromNativeQuery(queryStr);
	}

	@Override
	public Object persistObject(Object obj) throws Exception {

		return inicuPostgresUtililty.updateObject(obj);
	}

	@Override
	public List<?> testPostGresQuery(String query) {
		return inicuPostgresUtililty.testPostGresQuery(query);
	}

	// this function will execute every query
	@Override
	public void executeCustomeQuery(String query){
		inicuPostgresUtililty.executeDirectQuery(query);
	}

}
