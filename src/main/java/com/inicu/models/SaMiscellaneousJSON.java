package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaMiscellaneous;
import com.inicu.postgres.entities.SaMiscellaneous2;

public class SaMiscellaneousJSON {
	ResponseMessageObject response;
	MiscDropDownsJSON dropDowns;
	SaMiscellaneous miscellaneous;
	SaMiscellaneous2 miscellaneous2;
	List<SaMiscellaneous> listMiscellaneous;
	List<SaMiscellaneous2> listMiscellaneous2;
	String userId = "";

	//Not needed anymore
	//private List<Object[]> associatedEvents;

	List<BabyPrescription> prescriptionList;

    private String inactiveProgressNoteMisc1;

    private String inactiveProgressNoteMisc2;

	public SaMiscellaneousJSON() {
		super();
		this.miscellaneous = new SaMiscellaneous();
		this.miscellaneous2 = new SaMiscellaneous2();
		this.response = new ResponseMessageObject();
		this.listMiscellaneous = new ArrayList<SaMiscellaneous>();
		this.listMiscellaneous2 = new ArrayList<SaMiscellaneous2>();
		this.prescriptionList = new ArrayList<BabyPrescription>();

	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public MiscDropDownsJSON getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(MiscDropDownsJSON dropDowns) {
		this.dropDowns = dropDowns;
	}

	public SaMiscellaneous getMiscellaneous() {
		return miscellaneous;
	}

	public void setMiscellaneous(SaMiscellaneous miscellaneous) {
		this.miscellaneous = miscellaneous;
	}
	
	public SaMiscellaneous2 getMiscellaneous2() {
		return miscellaneous2;
	}

	public void setMiscellaneous2(SaMiscellaneous2 miscellaneous2) {
		this.miscellaneous2 = miscellaneous2;
	}

	public List<SaMiscellaneous> getListMiscellaneous() {
		return listMiscellaneous;
	}

	public void setListMiscellaneous(List<SaMiscellaneous> listMiscellaneous) {
		this.listMiscellaneous = listMiscellaneous;
	}

	public List<SaMiscellaneous2> getListMiscellaneous2() {
		return listMiscellaneous2;
	}

	public void setListMiscellaneous2(List<SaMiscellaneous2> listMiscellaneous2) {
		this.listMiscellaneous2 = listMiscellaneous2;
	}

//	public List<Object[]> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<Object[]> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

    public String getInactiveProgressNoteMisc1() {
        return inactiveProgressNoteMisc1;
    }

    public void setInactiveProgressNoteMisc1(String inactiveProgressNoteMisc1) {
        this.inactiveProgressNoteMisc1 = inactiveProgressNoteMisc1;
    }

    public String getInactiveProgressNoteMisc2() {
        return inactiveProgressNoteMisc2;
    }

    public void setInactiveProgressNoteMisc2(String inactiveProgressNoteMisc2) {
        this.inactiveProgressNoteMisc2 = inactiveProgressNoteMisc2;
    }
}
