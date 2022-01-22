package com.inicu.postgres.service;

import org.springframework.stereotype.Service;

import com.inicu.models.HearingMasterPojo;
import com.inicu.models.MetabolicMasterPojo;
import com.inicu.models.MiscellaneousMasterPOJO;
import com.inicu.models.NeurologicalMasterPojo;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.models.RopMasterPojo;
import com.inicu.models.ScreeningHearingPrintJSON;
import com.inicu.models.ScreeningMetabolicPrintJSON;
import com.inicu.models.ScreeningNeurologicalPrintJSON;
import com.inicu.models.ScreeningRopPrintJSON;
import com.inicu.models.ScreeningUSGPrintJSON;
import com.inicu.models.USGMasterPojo;
import com.inicu.postgres.entities.ScreenHearing;
import com.inicu.postgres.entities.ScreenMiscellaneous;
import com.inicu.postgres.entities.ScreenNeurological;
import com.inicu.postgres.entities.ScreenRop;
import com.inicu.postgres.entities.ScreenUSG;

@Service
public interface ScreeningService {

	NeurologicalMasterPojo getNeurological(String uhid);
	
	MiscellaneousMasterPOJO getMiscellaneous(String uhid);

	ResponseMessageWithResponseObject saveNeurological(String uhid, String loggedUser,
			ScreenNeurological neurologicalObj);
	
	ResponseMessageWithResponseObject saveMiscellaneous(String uhid, String loggedUser,
			ScreenMiscellaneous miscellaneousObj);

	ScreeningNeurologicalPrintJSON getScreeningNeurologicalPrint(String uhid, String fromTime, String toTime);

	HearingMasterPojo getHearing(String uhid);

	ResponseMessageWithResponseObject saveHearing(String uhid, String loggedUser, ScreenHearing hearingObj);

	ScreeningHearingPrintJSON getScreeningHearingPrint(String uhid, String fromTime, String toTime);

	RopMasterPojo getRop(String uhid);

	ResponseMessageWithResponseObject saveRop(String uhid, String loggedUser, ScreenRop ropObj);

	ScreeningRopPrintJSON getScreeningRopPrint(String uhid, String fromTime, String toTime);

	USGMasterPojo getUSG(String uhid);

	ResponseMessageWithResponseObject saveUSG(String uhid, String loggedUser, ScreenUSG usgObj);

	ScreeningUSGPrintJSON getScreeningUSGPrint(String uhid, String fromTime, String toTime);

	MetabolicMasterPojo getMetabolic(String uhid);

	ResponseMessageWithResponseObject saveMetabolic(String uhid, String loggedUser, MetabolicMasterPojo metabolicObj);

	ScreeningMetabolicPrintJSON getScreeningMetabolicPrint(String uhid, String fromTime, String toTime);

}
