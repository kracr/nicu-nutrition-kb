package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NursingNote;

public class NursingNotesPojo {

	List<NursingNote> pastNotesList;

	NursingNote currentNotes;

	public NursingNotesPojo() {
		super();
		this.currentNotes = new NursingNote();
	}

	public List<NursingNote> getPastNotesList() {
		return pastNotesList;
	}

	public void setPastNotesList(List<NursingNote> pastNotesList) {
		this.pastNotesList = pastNotesList;
	}

	public NursingNote getCurrentNotes() {
		return currentNotes;
	}

	public void setCurrentNotes(NursingNote currentNotes) {
		this.currentNotes = currentNotes;
	}

	@Override
	public String toString() {
		return "NursingNotesPojo [pastNotesList=" + pastNotesList + ", currentNotes=" + currentNotes + "]";
	}

}
