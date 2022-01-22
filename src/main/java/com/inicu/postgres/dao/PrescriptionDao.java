package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionDao {

	public List getListFromMappedObjNativeQuery(String queryStr);

	List getListFromNativeQuery(String queryStr);

	void executeInsertQuery(String query);

	void saveObject(Object obj) throws Exception;

	void updateOrDeleteQuery(String query);

	void updateOrDeleteNativeQuery(String query);
}
