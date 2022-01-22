package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inicu.postgres.entities.SaJaundice;

@Repository
public interface SystematicDAO {

	List executeObjectQuery(String query) throws Exception;

	List executeNativeQuery(String getCauseJaund) throws Exception;

	Object saveObject(Object saMetabolic)throws Exception;

	void insertnativeQuery(String query);


}
