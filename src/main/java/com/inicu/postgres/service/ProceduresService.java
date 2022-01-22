package com.inicu.postgres.service;

import com.inicu.postgres.entities.TherapeuticHypothermia;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.inicu.models.ImagePOJO;
import com.inicu.models.ProceduresMasterPojo;
import com.inicu.models.TherapeuticHypothermiaPOJO;
import com.inicu.models.ResponseMessageWithResponseObject;

/**
 * @author Sourabh Verma
 */
@Service
public interface ProceduresService {

	ProceduresMasterPojo getProcedures(String uhid, String loggedUser, String branchName);
	
	TherapeuticHypothermiaPOJO getHypothermia(String uhid, String loggeduser, String branchName);
	String saveHypothermia(TherapeuticHypothermia object);
	ResponseMessageWithResponseObject storeImage(String imageName, String imageData);
	ImagePOJO getImage(String imageName);
	ResponseMessageWithResponseObject saveProcedures(String uhid, String loggedUser, String branchName, String procedureName,
			ProceduresMasterPojo dischargeSummaryAdvancedMasterObj);

}
