package com.inicu.postgres.service;

import com.inicu.models.GeneralResponseObject;
import org.springframework.stereotype.Repository;

import com.inicu.models.HypoglycemiaJSON;
import com.inicu.models.RenalFailureJSON;
import com.inicu.models.ResponseMessageObject;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public interface MetabolicService {
	
	HypoglycemiaJSON getHypoglycemia(String uhid, String loggedUser) throws InicuDatabaseExeption;

	ResponseMessageObject saveHypoglycemiaJson(HypoglycemiaJSON hypoglycemiaJSON, String userId) throws InicuDatabaseExeption;

	GeneralResponseObject generateHypoglycemiaInactiveNote(HypoglycemiaJSON hypoglycemiaJSON);

}
