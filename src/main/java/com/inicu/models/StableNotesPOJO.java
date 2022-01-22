package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.inicu.postgres.entities.NADCnsNote;
//import com.inicu.postgres.entities.NADCvsNote;
//import com.inicu.postgres.entities.NADGastroNote;
import com.inicu.postgres.entities.NursingEpisode;
import com.inicu.postgres.entities.RefMasterfeedmethod;
import com.inicu.postgres.entities.StableNote;

public class StableNotesPOJO {

	StableNote note;

	List<StableNote> notesList;

	List<RefMasterfeedmethod> feedMethods;
	
	HashMap<Object, List<RefTestslist>> orders;
	
	List<StableNoteEvent> nursingEventList;
		
	public StableNotesPOJO() {
		this.note = new StableNote();
		this.notesList = new ArrayList<StableNote>();
	}

	public StableNote getNote() {
		return note;
	}

	public List<StableNote> getNotesList() {
		return notesList;
	}

	public void setNote(StableNote note) {
		this.note = note;
	}

	public void setNotesList(List<StableNote> notesList) {
		this.notesList = notesList;
	}

	public List<RefMasterfeedmethod> getFeedMethods() {
		return feedMethods;
	}

	public void setFeedMethods(List<RefMasterfeedmethod> feedMethods) {
		this.feedMethods = feedMethods;
	}

	public HashMap<Object, List<RefTestslist>> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Object, List<RefTestslist>> orders) {
		this.orders = orders;
	}

	public List<StableNoteEvent> getNursingEventList() {
		return nursingEventList;
	}

	public void setNursingEventList(List<StableNoteEvent> nursingEventList) {
		this.nursingEventList = nursingEventList;
	}	
	
}
