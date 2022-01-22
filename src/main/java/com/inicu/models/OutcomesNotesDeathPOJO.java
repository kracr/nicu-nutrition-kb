package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.DeathMedication;
import com.inicu.postgres.entities.OutcomeNotes;

public class OutcomesNotesDeathPOJO {
	
	OutcomeNotes notes;
	
	List<DeathMedication> deathMedicationList;
	
	ResponseMessageObject response ;
	

	public OutcomeNotes getNotes() {
		return notes;
	}

	public void setNotes(OutcomeNotes notes) {
		this.notes = notes;
	}

	public List<DeathMedication> getDeathMedicationList() {
		return deathMedicationList;
	}

	public void setDeathMedicationList(List<DeathMedication> deathMedicationList) {
		this.deathMedicationList = deathMedicationList;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	

	
	

}
