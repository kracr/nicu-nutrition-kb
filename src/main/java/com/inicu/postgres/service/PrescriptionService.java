package com.inicu.postgres.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inicu.models.BabyPrescriptionObject;
import com.inicu.models.DeletedNursingMedicationModel;
import com.inicu.models.MedicalAdministrationObj;
import com.inicu.models.NursingMedicationModel;
import com.inicu.models.ResponseMessageWithResponseObject;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.MedicationPreparation;
import com.inicu.postgres.entities.VwBabyPrescription;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public interface PrescriptionService {

	BabyPrescriptionObject getBabyPrescription(String uhid, String startDate, Integer gestationDays, Integer gestationWeeks, String dateStr , Integer dol, String assessmentName,
											   String branchName) throws InicuDatabaseExeption;

	ResponseMessageWithResponseObject addBabyPrescription(List<BabyPrescription> prescriptionList)
			throws InicuDatabaseExeption;

	ResponseMessageWithResponseObject deleteBabyPrescription(BabyPrescription prescription, String loggedUserId)
			throws InicuDatabaseExeption;

	List<BabyPrescription> getPrescription(String uhid, String prescriptionType);

	List<MedicalAdministrationObj> getGivenMedicinesDetails(String uhid, String loggedUser)
			throws InicuDatabaseExeption;

	ResponseMessageWithResponseObject addGivenMedicinesDetails(String givenBy, String babyPresId, String givenTime,
			String presTime);

	List<VwBabyPrescription> getBabyPrescriptionDetails(String uhid);

	ResponseMessageWithResponseObject editBabyPrescription(BabyPrescription babyPrescription);

	NursingMedicationModel getNursingMedication(String uhid);

	ResponseMessageWithResponseObject saveNursingMedication(NursingMedicationModel medicationModel);

	ResponseMessageWithResponseObject saveNursingBloodProduct(NursingMedicationModel medicationModel);

	ResponseMessageWithResponseObject setMedicationPreparation(MedicationPreparation preparationObj);
	
	ResponseMessageWithResponseObject setNursingHeplock(NursingMedicationModel medicationModel);

	DeletedNursingMedicationModel deleteNursingMedication(String uhid, Long nursingMedicationId,String babyPresId);

	ResponseMessageWithResponseObject deleteNursingBloodProducts(String uhid, Long nursingBloodProductId);

	ResponseMessageWithResponseObject deleteHeplock(String uhid, Long nursingHeplockId);
}
