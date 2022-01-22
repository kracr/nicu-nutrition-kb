package com.inicu.postgres.controller;

import com.inicu.models.*;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.MedicationPreparation;
import com.inicu.postgres.entities.VwBabyPrescription;
import com.inicu.postgres.service.PrescriptionService;
import com.inicu.postgres.service.SettingsService;
import com.inicu.postgres.service.UserPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PrescriptionController {

	@Autowired
	private SettingsService settingService;

	@Autowired
	PrescriptionService prescriptionService;

	@Autowired
	UserPanelService userPanel;

	@RequestMapping(value = "/inicu/getBabyPrescription/{uhid}/{loggedUser}/{gestationDays}/{gestationWeeks}/{dateStr}/{dol}/{branchName}", method = RequestMethod.GET)
	public ResponseEntity<BabyPrescriptionObject> getBabyPrescription(@Valid @PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser, @PathVariable("gestationDays") Integer gestationDays, @PathVariable("gestationWeeks") Integer gestationWeeks, 
			@PathVariable("dateStr") String dateStr, @PathVariable("dol") Integer dol, @RequestParam("assessmentName") String assessmentName, @PathVariable("branchName") String branchName) {
		
		System.out.println("assessmentName is " + assessmentName);

		BabyPrescriptionObject prescription = new BabyPrescriptionObject();
		try {
			prescription = prescriptionService.getBabyPrescription(uhid, loggedUser, gestationDays, gestationWeeks, dateStr, dol, assessmentName, branchName);
			List<KeyValueObj> imageData = null;
			List<KeyValueObj> signatureImageData = null;
			imageData = settingService.getHospitalLogo(branchName);
			prescription.setLogoImage(imageData);
			try {
				signatureImageData = userPanel.getImageData(loggedUser, branchName);
				prescription.setSignatureImage(signatureImageData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<BabyPrescriptionObject>(prescription, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addBabyPrescription", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> addBabyPrescription(
			@RequestBody List<BabyPrescription> prescriptionList, BindingResult bindingResult) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		try {
			obj = prescriptionService.addBabyPrescription(prescriptionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);

	}

	@RequestMapping(value = "/inicu/deleteBabyPrescription/{prescriptionId}/{loggedUserId}/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteBabyPrescription(
			@RequestBody BabyPrescription prescription, @PathVariable("prescriptionId") String prescrptnId,
			@PathVariable("loggedUserId") String loggedUserId) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		try {
			obj = prescriptionService.deleteBabyPrescription(prescription, loggedUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/editBabyPrescription", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> editBabyPrescription(
			@RequestBody BabyPrescription babyPrescription) {
		ResponseMessageWithResponseObject obj = new ResponseMessageWithResponseObject();
		try {
			obj = prescriptionService.editBabyPrescription(babyPrescription);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getMedicalAdministration/{uhid}/{loggedUser}", method = RequestMethod.GET)
	public ResponseEntity<List<MedicalAdministrationObj>> getGivenMedicinesDetails(@PathVariable("uhid") String uhid,
			@PathVariable("loggedUser") String loggedUser) {
		List<MedicalAdministrationObj> medicalAdministrationObj = new ArrayList<MedicalAdministrationObj>();
		try {
			medicalAdministrationObj = prescriptionService.getGivenMedicinesDetails(uhid, loggedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<MedicalAdministrationObj>>(medicalAdministrationObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/addMedicalAdministration/{givenby}/{givenTime}/{babyPresId}/{presTime}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageWithResponseObject> addGivenMedicinesDetails(
			@PathVariable("givenby") String givenBy, @PathVariable("babyPresId") String babyPresId,
			@PathVariable("givenTime") String givenTime, @PathVariable("presTime") String presTime) {
		return new ResponseEntity<ResponseMessageWithResponseObject>(
				prescriptionService.addGivenMedicinesDetails(givenBy, babyPresId, givenTime, presTime), HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/exportPrescriptionCSV/{uhid}", method = RequestMethod.GET)
	public ResponseEntity<List<VwBabyPrescription>> getBabyPrescriptionDetails(@PathVariable("uhid") String uhid) {
		List<VwBabyPrescription> result = prescriptionService.getBabyPrescriptionDetails(uhid);
		return new ResponseEntity<List<VwBabyPrescription>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/getNursingMedication/{uhid}/", method = RequestMethod.GET)
	public ResponseEntity<NursingMedicationModel> getNursingMedication(@PathVariable("uhid") String uhid) {
		NursingMedicationModel result = prescriptionService.getNursingMedication(uhid);
		return new ResponseEntity<NursingMedicationModel>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/inicu/deleteNursingMedications/{uhid}/{nursingMedicationId}/{babyPresId}", method = RequestMethod.GET)
	public ResponseEntity<DeletedNursingMedicationModel> deleteNursingMedication(@PathVariable("uhid") String uhid, 
			@PathVariable("nursingMedicationId") Long nursingMedicationId,@PathVariable("babyPresId") String babyPresId) {
		DeletedNursingMedicationModel result = prescriptionService.deleteNursingMedication(uhid,nursingMedicationId,babyPresId);
		return new ResponseEntity<DeletedNursingMedicationModel>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/deleteNursingBloodProducts/{uhid}/{nursingBloodProductId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteNursingBloodProducts(@PathVariable("uhid") String uhid, 
			@PathVariable("nursingBloodProductId") Long nursingBloodProductId) {
		ResponseMessageWithResponseObject result = prescriptionService.deleteNursingBloodProducts(uhid,nursingBloodProductId);
		return new ResponseEntity<ResponseMessageWithResponseObject>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/inicu/deleteHeplock/{uhid}/{nursingHeplockId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessageWithResponseObject> deleteHeplock(@PathVariable("uhid") String uhid, 
			@PathVariable("nursingHeplockId") Long nursingHeplockId) {
		ResponseMessageWithResponseObject result = prescriptionService.deleteHeplock(uhid,nursingHeplockId);
		return new ResponseEntity<ResponseMessageWithResponseObject>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveNursingMedication/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveNursingMedication(
			@RequestBody NursingMedicationModel medicationModel) {
		ResponseMessageWithResponseObject obj = prescriptionService.saveNursingMedication(medicationModel);
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/saveNursingBloodProduct/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> saveNursingBloodProduct(
			@RequestBody NursingMedicationModel medicationModel) {
		ResponseMessageWithResponseObject obj = prescriptionService.saveNursingBloodProduct(medicationModel);
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/inicu/setMedicationPreparation/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> setMedicationPreparation(
			@RequestBody MedicationPreparation preparationObj) {
		ResponseMessageWithResponseObject obj = prescriptionService.setMedicationPreparation(preparationObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/setNursingHeplock/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageWithResponseObject> setNursingHeplock(@RequestBody NursingMedicationModel currentObj) {
		ResponseMessageWithResponseObject returnObj = prescriptionService.setNursingHeplock(currentObj);
		return new ResponseEntity<ResponseMessageWithResponseObject>(returnObj, HttpStatus.OK);
	}

}
