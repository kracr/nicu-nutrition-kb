package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface InicuDao {

	List getListFromMappedObjQuery(String queryGetBabyDetails);

	List<?> getListFromMappedObjRowLimitQuery(String queryGetBabyDetails, Integer maxCount);

	List getListFromNativeQuery(String queryStr);

	void executeInsertQuery(String query);

	List<?> saveMultipleObject(List<?> obj) throws Exception;

	Object saveObject(Object obj) throws Exception;

	Object persistObject(Object obj) throws Exception;

	void updateOrDeleteQuery(String query);

	void updateOrDeleteNativeQuery(String query);

	List testPostGresQuery(String query);
	void executeCustomeQuery(String query);

}
