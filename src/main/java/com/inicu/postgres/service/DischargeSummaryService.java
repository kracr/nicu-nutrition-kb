package com.inicu.postgres.service;

import org.springframework.stereotype.Service;

import com.inicu.models.DischargeSummaryAdvancedMasterObj;
import com.inicu.models.OutcomeNotesPOJO;
import com.inicu.models.OutcomesNotesDeathPOJO;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.postgres.entities.DischargeOutcome;
import com.inicu.postgres.entities.OutcomeNotes;
import com.inicu.postgres.entities.VwVaccinationsPatient;

@Service
public interface DischargeSummaryService {

	DischargeSummaryAdvancedMasterObj getDishchargedSummaryAdvanced(String uhid, String moduleName, String loggedUser);

	ResponseMessageWithResponseObject saveDishchargedSummaryAdvanced(String uhid, String moduleName,
			DischargeSummaryAdvancedMasterObj dischargeSummaryAdvancedMasterObj, String loggedUser);

	ResponseMessageWithResponseObject savePatientVaccination(VwVaccinationsPatient patientVaccinationObj);

	DischargeOutcome getDichargeOutCome(String uhid,String episodeid);

	ResponseMessageWithResponseObject saveDichargeOutCome(
			DischargeOutcome outcome);

	OutcomeNotesPOJO getOutcomeNotes(String uhid, String outcomeType);

	ResponseMessageWithResponseObject saveOutcomeNotes(OutcomeNotes outcome);

	ResponseMessageWithResponseObject saveOutcomeNotesDeath(OutcomesNotesDeathPOJO outcome);

	OutcomesNotesDeathPOJO getOutcomeNotesDeath(String uhid);

}
