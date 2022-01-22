package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.DoctorNote;
import com.inicu.postgres.entities.VwDoctornotesFinal;

public class BabyHistoryObj {

	public BabyHistoryObj() {

		this.diagnosis = new ArrayList<String>(){
			{
				add("jaundice");
				add("Hypoglycemia");
				add("HDS");
				add("Sepsis");
			}
		};

		this.medication = new ArrayList<BabyPrescription>();
		this.feed = new ArrayList<BabyfeedDetail>();
		this.doctorNotes = new ArrayList<VwDoctornotesFinal>();

	}

	List<String> diagnosis;  

	List<BabyPrescription> medication;

	List<BabyfeedDetail> feed;

	List<VwDoctornotesFinal> doctorNotes;


	public List<String> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<String> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public List<BabyPrescription> getMedication() {
		return medication;
	}

	public void setMedication(List<BabyPrescription> medication) {
		this.medication = medication;
	}

	public List<BabyfeedDetail> getFeed() {
		return feed;
	}

	public void setFeed(List<BabyfeedDetail> feed) {
		this.feed = feed;
	}

	public List<VwDoctornotesFinal> getDoctorNotes() {
		return doctorNotes;
	}

	public void setDoctorNotes(List<VwDoctornotesFinal> doctorNotes) {
		this.doctorNotes = doctorNotes;
	}


}
