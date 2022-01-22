package com.inicu.postgres.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.PrescriptionDao;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class PrescriptionDaoImpl implements PrescriptionDao{
	
	@Autowired InicuPostgresUtililty inicuPostgresUtililty;
	
	@Override
	public List getListFromMappedObjNativeQuery(String queryStr) {
		List listObj = null;
		try {
			listObj = inicuPostgresUtililty
					.executeMappedObjectCustomizedQuery(queryStr);
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
	public void saveObject(Object obj) throws Exception {
		inicuPostgresUtililty.saveObject(obj);
	}

	@Override
	public void executeInsertQuery(String query) {
		inicuPostgresUtililty.executeInsertQuery(query);
	}

	@Override
	public List getListFromNativeQuery(String queryStr) {
		return inicuPostgresUtililty.getListFromNativeQuery(queryStr);
	}

}
