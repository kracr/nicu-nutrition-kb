import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { BabyPrescription } from './babyPrescription';
import { NursingMedication } from './nursingMedication';
import { ExportCsv } from '../export-csv';
import { Keyvalue } from '../userpanel/Keyvalue';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
	selector: 'nursing-medications',
	templateUrl: './nursing-medications.component.html',
	styleUrls: ['./nursing-medications.component.css']
})

export class NursingMedicationsComponent implements OnInit {
	isOrderVisible : boolean;
	isExecutionVisible : boolean;
	childObject : any;
	apiData : APIurl;
	http: Http;
	router : Router;
	uhid : string;
	workingWeight : any;
	loggedUser : string;
	selectedMedication : any;
	nursingMedicationModel : any;
	medicationMap : any;
	medicationPrepMap : any;
	assessmentMedicineMap : any;
	bloodProductMap : any;
	currentComment : string;
	oneDay : number;
	today : Date;
	minDate : Date;
	toDate : Date;
	fromDate : Date;
	fromDateNull : boolean;
	toDateNull : boolean;
	fromToError : boolean;
	fromDateTableNull : boolean;
	toDateTableNull : boolean;
	fromToTableError : boolean;
	minValidDate : string;
	maxDate : Date;
	isLoaderVisible : boolean;
  loaderContent : string;
  medType : string;
  isSubmitVisible : boolean;
	prepMsg : string;
	isMedicationOrder : boolean;
	selectedBloodProduct : any;
  selectedHeplock : any;
  selectedObjectHeplock : boolean;
	keyValue : Keyvalue;
	brands : any;
	isBrandsAvailable : boolean;
	constructor(http: Http, router : Router) {
		this.isMedicationOrder = true;
		this.isSubmitVisible = true;
		this.apiData = new APIurl();
		this.http = http;
		this.router = router;
		this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
		this.workingWeight = JSON.parse(localStorage.getItem('selectedChild')).workingWeight / 1000;
		this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.isOrderVisible = true;
		this.isExecutionVisible = true;
    this.selectedObjectHeplock = true;
		this.currentComment = "";
		this.selectedMedication = {};
		this.selectedBloodProduct = {};
    this.selectedHeplock = {};
		this.oneDay = 24 * 60 * 60 * 1000;
		this.today = new Date();
		this.minDate = new Date();
		this.maxDate = new Date();
		this.brands = [];
		this.isBrandsAvailable = false;
   	var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 0);
   	var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
   	var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
   	if(tempHr == 12 && tempMer == 'AM'){
    	tempHr = '00';
   	}else if(tempHr != 12 && tempMer == 'PM'){
   		tempHr = parseInt(tempHr) + 12;
   	}
   	var tempFullTime = tempHr +':' +tempMin+':00';
   	this.minValidDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
   	var tempPrevTime = this.minValidDate.slice(16,24);
   	this.minValidDate = this.minValidDate.replace(tempPrevTime,tempFullTime);
		this.prepMsg = null;
		this.keyValue = new Keyvalue();
	}

	closePreparation(flag : Boolean){
	    this.isSubmitVisible = true;
	    $("#PreparationOverlay").css("display", "none");
	    $("#PreparationPopup").toggleClass("showing");
			if(!flag) {
				if(this.isMedicationOrder) {
					this.selectedMedication = {};
				} else {
					this.selectedBloodProduct = {};
				}
			}
	 }

  showPreparationModal(){
	    this.isSubmitVisible = false;
	    console.log('In The Show Notification Modal');
	    $("#PreparationOverlay").css("display", "block");
	    $("#PreparationPopup").addClass("showing");
	}

	orderSectionVisible(){
		this.isOrderVisible = !this.isOrderVisible;
	}

	ExecutionSectionVisible(){
		this.isExecutionVisible = !this.isExecutionVisible;
	}

	viewCommentsPopUp(nursing_comment : string) {
		this.currentComment = nursing_comment;
		$("#OkCancelPopUp").addClass("showing");
		$("#scoresOverlay").addClass("show");
	};

	closeCommentsPopUp(){
		$("#OkCancelPopUp").removeClass("showing");
		$("#scoresOverlay").removeClass("show");
	};

	openBloodProductModal(){
		this.isMedicationOrder = false;
		this.showPreparationModal();
	}

	selectHeplock(item : any){
    this.selectedHeplock = item;
    this.selectedObjectHeplock = false;
    this.nursingMedicationModel.currentHeplockObj.solutionType = item.solutionType;
    this.nursingMedicationModel.currentHeplockObj.antiCoagulentType = item.antiCoagulentType;
    this.nursingMedicationModel.currentHeplockObj.antiCoagulentBrand = item.antiCoagulentBrand;
    this.nursingMedicationModel.currentHeplockObj.heparinTotalVolume = item.heparinTotalVolume;
    this.nursingMedicationModel.currentHeplockObj.heparinRate = item.heparinRate;
    this.nursingMedicationModel.currentHeplockObj.central_line_id = item.centralLineId;
    this.nursingMedicationModel.currentHeplockObj.orderDate = item.creationtime;
    this.nursingMedicationModel.currentHeplockObj.execution_time = new Date();
  }

  calculateRemHeplock(){
    if(!this.isEmpty(this.nursingMedicationModel.currentHeplockObj.heparinTotalVolume) && !this.isEmpty(this.nursingMedicationModel.currentHeplockObj.deliveredVolume)){
      this.nursingMedicationModel.currentHeplockObj.remainingVolume = (this.nursingMedicationModel.currentHeplockObj.heparinTotalVolume*1) - (this.nursingMedicationModel.currentHeplockObj.deliveredVolume*1);
			this.nursingMedicationModel.currentHeplockObj.remainingVolume = this.round(this.nursingMedicationModel.currentHeplockObj.remainingVolume, 1);
			if(this.nursingMedicationModel.currentHeplockObj.remainingVolume < 0){
        this.nursingMedicationModel.currentHeplockObj.remainingVolume = null;
        this.nursingMedicationModel.currentHeplockObj.deliveredVolume = null;
      }
    }
  }

  isEmpty(object : any) : boolean {
    if (object == null || object == '' || object == 'null' || object.length == 0) {
      return true;
    }
    else {
      return false;
    }
  };


	selectBloodProduct(item : any){
		if(item.nursingObj.delivered_volume == null || item.nursingObj.delivered_volume == '') {
			this.selectedBloodProduct = item;

			if(item.nursingObj.nursing_blood_product_id == null) {
				item.nursingObj.collection_date = new Date(item.collection_date);
				item.nursingObj.expiry_date = new Date(item.expiry_date);
				item.nursingObj.bag_number = item.bag_number;
				item.nursingObj.blood_group = item.blood_group;
				item.nursingObj.bag_volume = item.bag_volume;
				item.nursingObj.checked_by = item.checked_by;
				this.openBloodProductModal();
			}
			item.nursingObj.execution_time = new Date();
			this.nursingMedicationModel.currentBloodProductObj = item.nursingObj;
		}
	}

	selectMedicine(item : any, flag : boolean){
		this.isMedicationOrder = true;
		this.selectedMedication = item;
		this.nursingMedicationModel.currentEmptyObj.assessment_medicine = flag;
		if(flag) {
			this.selectedMedication.medicinename = item.med_name;
			this.minDate = new Date(this.selectedMedication.assessmenttime);
			if(item.med_name == 'Caffeine') {
				if(this.selectedMedication.dose != null && this.selectedMedication.dose != '') {
					this.nursingMedicationModel.currentEmptyObj.given_dose = this.round((this.selectedMedication.dose * this.workingWeight), 1);
				} else if(this.selectedMedication.maintainence_dose != null && this.selectedMedication.maintainence_dose != '') {
					this.nursingMedicationModel.currentEmptyObj.given_dose = this.round((this.selectedMedication.maintainence_dose * this.workingWeight), 1);
				}
			} else {
				this.nursingMedicationModel.currentEmptyObj.given_dose = this.selectedMedication.dose;
			}
			this.nursingMedicationModel.currentEmptyObj.stop_flag = false;
		} else {
			this.minDate = new Date(this.selectedMedication.startdate);
			// Modal will not open for Probiotics, Inhaled Drugs, Suppository Drugs, Nasal Saline Drops and Topical
			if(this.selectedMedication.dose == null && (this.selectedMedication.topical_frequency != null || this.selectedMedication.route != null)){
				//Calculations to be done for drops and ointment
				this.callNextDose();
				if(this.selectedMedication.isactive) {
					this.nursingMedicationModel.currentEmptyObj.stop_flag = false;
				} else {
					this.nursingMedicationModel.currentEmptyObj.stop_flag = true;
				}
			}
			else{
				this.callNextDose();
				this.minDate = new Date(this.selectedMedication.startdate);
				if(this.selectedMedication.dose_unit.substring(0,2) == "μg"){
					this.nursingMedicationModel.currentEmptyObj.given_dose = this.selectedMedication.cal_dose_volume / 1000;
				}
				else{
					this.nursingMedicationModel.currentEmptyObj.given_dose = this.selectedMedication.cal_dose_volume;
				}

				if(this.selectedMedication.isactive || this.selectedMedication.bolus) {
					this.nursingMedicationModel.currentEmptyObj.stop_flag = false;
				} else {
					this.nursingMedicationModel.currentEmptyObj.stop_flag = true;
				}

				if(this.selectedMedication.preparationObj.medication_preparation_id == null) {
					if(this.selectedMedication.route == 'PO' && this.selectedMedication.po_type == 'Tablet') {
						this.selectedMedication.preparationObj.med_volume = 1;
						this.selectedMedication.preparationObj.content_type = 'Tablet';
					}

					if(!this.isEmpty(this.nursingMedicationModel.brandList)){
						this.brands = [];
						this.isBrandsAvailable = false;
						for(var i = 0;i < this.nursingMedicationModel.brandList.length; i++){
							if(this.nursingMedicationModel.brandList[i].medid == item.medid){
								this.keyValue = new Keyvalue();
								this.keyValue.key = "" + this.nursingMedicationModel.brandList[i].refmedbrandid + "" ;
								this.keyValue.value = this.nursingMedicationModel.brandList[i].brandName;
								this.brands.push(this.keyValue);
								this.isBrandsAvailable = true;
							}
						}
					}
					this.showPreparationModal();
				}
			}
		}
	}

	callNextDose() {
		this.nursingMedicationModel.currentEmptyObj.day_number = Math.ceil(Math.abs((this.nursingMedicationModel.currentEmptyObj.given_time.getTime() - this.selectedMedication.startdate) / this.oneDay));

		if(!this.selectedMedication.bolus && this.selectedMedication.freq_type != null && this.selectedMedication.freq_type.toLowerCase() == 'intermittent') {
			var nextDoseTime = this.oneDay / this.selectedMedication.freqFactor;
			this.nursingMedicationModel.currentEmptyObj.next_dose = new Date(this.nursingMedicationModel.currentEmptyObj.given_time.getTime() + nextDoseTime);
		} else {
			this.nursingMedicationModel.currentEmptyObj.next_dose = null;
		}
	}

	getNursingMedication() {
		console.log("comming into the getNursingMedication");
		try{
			this.http.request(this.apiData.getNursingMedication + this.uhid + "/").subscribe((res: Response) => {
				this.nursingMedicationModel = res.json();
				this.generateMedicationMap();
				this.generatebloodProductMap();
			});
		}catch(e){
			console.log("Exception in http service:"+e);
		};
	}

	generateMedicationMap() {
		this.medicationMap = {};
		this.medicationPrepMap = {};
		this.assessmentMedicineMap = {};

		for(let i = 0; i < this.nursingMedicationModel.medicationPreparationList.length; i++) {
			let item = this.nursingMedicationModel.medicationPreparationList[i];
			this.medicationPrepMap[item.baby_presid] = item;
		}

		for(let i = 0; i < this.nursingMedicationModel.allMedicationList.length; i++) {
			let item = this.nursingMedicationModel.allMedicationList[i];
			this.medicationMap[item.babyPresid] = item;

			if(this.medicationPrepMap[item.babyPresid] == undefined || this.medicationPrepMap[item.babyPresid] == null) {
				item.preparationObj = Object.assign({}, this.nursingMedicationModel.medicationPreparationObj);
			} else {
				item.preparationObj = this.medicationPrepMap[item.babyPresid];
			}

			if(item.frequency == null) {
				item.freqFactor = 1;
				item.medicationFreqStr = "";
				if(item.topical_frequency != null){
					for(var j = 0; j < this.nursingMedicationModel.freqList.length; j++) {
						var freq = this.nursingMedicationModel.freqList[j];
						if(freq.freqid == item.topical_frequency) {
							item.freqFactor = freq.frequency_int;
							item.medicationFreqStr = freq.freqvalue;
						}
					}
				}
			} else {
				for(var j = 0; j < this.nursingMedicationModel.freqList.length; j++) {
					var freq = this.nursingMedicationModel.freqList[j];
					if(freq.freqid == item.frequency) {
						item.freqFactor = freq.frequency_int;
						item.medicationFreqStr = freq.freqvalue;
					}
				}
			}

			if(item.preparationObj.nursing_instruction != null && item.preparationObj.nursing_instruction != '') {
				item.nursingInstruction = item.preparationObj.nursing_instruction;
			} else {
				if(item.isactive == false && item.enddate != null) {
					item.nursingInstruction = "Stop ";
					if(item.route == 'IV' || item.route == 'IM') {
						item.nursingInstruction += "Inj. ";
					} else if(item.route == 'PO'){
						item.nursingInstruction += "Syrup ";
					}
					else{
						item.nursingInstruction += "Medicine ";
					}
					item.nursingInstruction += item.medicinename + ".";
				} else {
					item.nursingInstruction = item.instruction;
				}
			}
			var newDate = new Date();
			if(((newDate.getTime() - item.startdate)/ (1000 * 60 * 60)) > 24 && item.isactive == true){
				item.nursingInstruction = item.nursingInstruction + " (Doctor needs to assess this medicine).";
			}
		}

		for(let i = 0; i < this.nursingMedicationModel.pastAssessmentMedicineList.length; i++) {
			let item = this.nursingMedicationModel.pastAssessmentMedicineList[i];
			this.assessmentMedicineMap[item.assessmentid] = item;
		}

		for(let i = 0; i < this.nursingMedicationModel.assessmentMedicineList.length; i++) {
			let item = this.nursingMedicationModel.assessmentMedicineList[i];

			if(item.med_name == 'Caffeine') {
				item.nursingInstruction = "Give " + item.med_name;
				if(item.route == 'IV') {
					item.nursingInstruction += ' intravenously';
				} else {
					item.nursingInstruction += ' orally';
				}
				var doseStr = '';
				if(item.dose != null && item.dose != '') {
					doseStr += " Bolus Dose of " + item.dose + " (mg/kg)";
				}

				if(item.maintainence_dose != null && item.maintainence_dose != '') {
					if(doseStr != '') {
						doseStr += ' and';
					}
					doseStr += " Maintainence Dose of " + item.maintainence_dose + " (mg/kg)";
				}
				item.nursingInstruction += doseStr + ".";
			} else {
				item.nursingInstruction = "Give Surfactant - " + item.med_name + " dose of " + item.dose + " ml.";
			}
			var newDate = new Date();
			if(((newDate.getTime() - item.startdate)/ (1000 * 60 * 60)) > 24 && item.isactive == true){
				item.nursingInstruction = item.nursingInstruction + " (Doctor needs to assess this medicine).";
			}
		}

		for(let i = 0; i < this.nursingMedicationModel.pastNursingList.length; i++) {
			let item = this.nursingMedicationModel.pastNursingList[i];
			if(item.assessment_medicine == null || item.assessment_medicine == false) {
				item.doctorMedicine = this.medicationMap[item.baby_presid];
			} else {
				item.doctorMedicine = {};
				var obj = this.assessmentMedicineMap[item.baby_presid];
				item.doctorMedicine.medicinename = obj.med_name;
				item.doctorMedicine.dose = obj.dose;
				item.doctorMedicine.route = obj.route;
			}
			item.visibility = true;
		}

		this.filterDoctorMedication(null, null);
		this.nursingMedicationModel.currentEmptyObj.given_time = new Date();
	}

	getMedInstruction(item : any) {
		var instruction = "";

		var calStrength = item.preparationObj.final_strength;
		if(calStrength == null) {
			calStrength = item.preparationObj.med_strength;
		} else {
			if(item.preparationObj.med_volume != null) {
				instruction += "Mix " + this.round(item.preparationObj.med_volume, 1) + " ml of medicine in " +
				this.round((item.preparationObj.reconstituted_volume - item.preparationObj.med_volume), 1)
				+ " ml of " + item.preparationObj.reconstituted_type + ". "
			} else {
				instruction += "Mix " + this.round(item.preparationObj.content_dose, 1) + " " + item.preparationObj.content_dose_type +
				" of medicine in " + this.round(item.preparationObj.reconstituted_volume, 1) + " ml of " + item.preparationObj.reconstituted_type + ". "
			}
		}

		var medVol = null;
		var dilVol = null;
		var calculated_cal_dose_volume = null;
		if(item.dose_unit.substring(0,2) == "μg"){
			calculated_cal_dose_volume = item.cal_dose_volume / 1000;
		}
		else{
			calculated_cal_dose_volume = item.cal_dose_volume;
		}
		if(calStrength != null && calStrength != 0) {
			medVol = this.round(calculated_cal_dose_volume / calStrength, 2);
			dilVol = item.inf_volume - medVol;
			dilVol = this.round(dilVol, 2);
		} else {
			item.preparationObj.nursing_instruction = "";
			return;
		}

		if(item.bolus) {
			if(item.freq_type != null && item.freq_type.toLowerCase() == 'continuous') {
				if(item.overfill_factor != null) {
					instruction += "Give " + this.round(calculated_cal_dose_volume * item.overfill_factor, 1)
					+ " mg (" + this.round((medVol * item.overfill_factor), 1) + " ml) loading dose of "
					+ item.medicinename + " with " + this.round((dilVol * item.overfill_factor), 1);
				} else {
					instruction += "Give " + this.round(calculated_cal_dose_volume, 1) + " mg (" + this.round(medVol, 1)
					+ " ml) loading dose of " + item.medicinename + " with " + dilVol;
				}

				instruction += " ml of " + item.dilution_type + " and give over " + item.inf_time +
				" mins @ of " + this.round(item.rate, 1) + " ml/hr" + (item.overfill_factor == null ?  "." : ", with overfill factor.");
			} else if(item.route == 'IV') {
				instruction += "Inj. " + item.medicinename + " " + this.round(medVol, 1) + " ml with " +
				dilVol + " ml of " + item.dilution_type + " IV STAT as loading dose.";
			} else if(item.route == 'IM') {
				instruction += "Inj. " + item.medicinename + " " + this.round(medVol, 1) + " ml IM STAT as loading dose.";
			} else if(item.po_type == 'Syrup') {
				instruction += "Syrup " + item.medicinename + " " + this.round(medVol, 1);
				+ " ml STAT as loading dose PO.";
			} else if(item.po_type == 'Tablet') {
				instruction += "Tablet " + item.medicinename + " " + this.round(medVol, 1)
				+ " Tabs PO as STAT loading dose.";
			}
		} else {
			if(item.freq_type != null && item.freq_type.toLowerCase() == 'continuous') {

				if(item.overfill_factor != null) {
					instruction += "Give " + this.round(calculated_cal_dose_volume * item.overfill_factor, 1) + " mg (" +
					this.round((medVol * item.overfill_factor), 1) + " ml)  of " + item.medicinename + " with "
					+ this.round((dilVol * item.overfill_factor), 1);
				} else {
					instruction += "Give " + this.round(calculated_cal_dose_volume, 1) + " mg (" +
					medVol + " ml) of " + item.medicinename + " with " + dilVol;
				}

				instruction += " ml of " + item.dilution_type + " and give as continuous infusion @ of "
				+ this.round(item.rate, 1) + " ml/hr" + (item.overfill_factor == null ?  ".": ", with overfill factor.");
			} else if (item.route == 'IV') {
				if(item.overfill_factor != null) {
					instruction += "Give " + this.round(calculated_cal_dose_volume * item.overfill_factor, 1)
					+ " mg " + "(" + this.round((medVol * item.overfill_factor), 1) +
					" ml) maintenance dose of " + item.medicinename + " with " + this.round((dilVol * item.overfill_factor), 1);
				} else {
					instruction += "Give " + this.round(calculated_cal_dose_volume, 1) + " mg "
					+ "(" + medVol + " ml) maintenance dose of " + item.medicinename + " with " + dilVol;
				}
				instruction += " ml of " + item.dilution_type;
				if(item.inf_mode=='STAT' || item.inf_time == null) {
					instruction += " and give STAT as IV Push ";
				} else {
					instruction += " and give intravenously over " + item.inf_time + " mins @ of " + this.round(item.rate, 1) + " ml/hr ";
				}
				instruction += "every " + item.medicationFreqStr + (item.overfill_factor == null ?  ".": ", with overfill factor.");
			} else if (item.route == 'IM') {
				instruction += "Inj. " + item.medicinename + " " + medVol + " ml IM every " + item.medicationFreqStr + ".";
			} else if (item.po_type == 'Syrup') {
				instruction += "Syrup " + item.medicinename + " " + this.round(medVol, 1) + " ml every " + item.medicationFreqStr + " PO.";
			} else if (item.po_type == 'Tablet') {
				instruction += "Tablet " + item.medicinename + " " + medVol + " tabs PO every " + item.medicationFreqStr + ".";
			}
		}
		item.preparationObj.nursing_instruction = instruction;
		console.log(item);
	}

	filterDoctorMedication(fromDate : Date, toDate : Date) {
		this.fromDateNull = false;
		this.toDateNull = false;
		this.fromToError = false;

		if(fromDate == null && toDate == null) {
			var endDateLong = new Date().getTime() - (24 * 60 * 60 * 1000);

			for(let i = 0; i < this.nursingMedicationModel.allMedicationList.length; i++) {
				let item = this.nursingMedicationModel.allMedicationList[i];
				if((item.isactive || (!item.bolus && item.enddate >= endDateLong)) && item.isEdit == null && item.isContinueMedication == null) {
					item.visibility = true;
				} else {
					item.visibility = false;
				}
			}
		} else if (fromDate == null) {
			this.fromDateNull = true;
		} else if (toDate == null) {
			this.toDateNull = true;
		} else if(fromDate >= toDate) {
			this.fromToError = true;
		} else {
			for(let i = 0; i < this.nursingMedicationModel.allMedicationList.length; i++) {
				let item = this.nursingMedicationModel.allMedicationList[i];

				if((item.startdate <= toDate.getTime() && (item.enddate == null || item.enddate >= fromDate.getTime())) && item.isEdit == null && item.isContinueMedication == null) {
					item.visibility = true;
				} else {
					item.visibility = false;
				}
			}
		}
	}

	filterNursingMedication(fromDateTable : Date, toDateTable : Date, flag : String) {

		this.fromDateTableNull = false;
		this.toDateTableNull = false;
		this.fromToTableError = false;

		if(fromDateTable == null) {
			this.fromDateTableNull = true;
		} else if(toDateTable == null) {
			this.toDateTableNull = true;
		} else if(fromDateTable >= toDateTable) {
			this.fromToTableError = true;
		} else {
			var data = [];
			for(var i=0; i < this.nursingMedicationModel.pastNursingList.length; i++) {
				var item = this.nursingMedicationModel.pastNursingList[i];

				if(item.given_time >= fromDateTable.getTime() && item.given_time <= toDateTable.getTime()) {
					if(flag == 'table') {
						item.visibility = true;
					} else {
						var obj = Object.assign({}, item);
						obj.next_dose = new Date(obj.next_dose);
						obj.given_time = new Date(obj.given_time);
						obj.creationtime = new Date(obj.creationtime);
						obj.modificationtime = new Date(obj.modificationtime);
						data.push(obj);
					}
				} else if(flag == 'table'){
					item.visibility = false;
				}
			}
			if(flag == 'print') {
				this.printMedication(data,fromDateTable,toDateTable);
			} else if(flag == 'csv') {
				this.exportMedicationCsv(data);
			}
		}
	}

	printMedication(dataForPrint : any, fromDateTable : Date, toDateTable : Date) {
		var tempArr = [];
		tempArr = dataForPrint;
		dataForPrint = {};
		dataForPrint.data = tempArr;
		dataForPrint.whichTab = 'Medications';
    dataForPrint.from_time = fromDateTable.getTime();
    dataForPrint.to_time = toDateTable.getTime();;
    localStorage.setItem('printNursingDataObjNotes', JSON.stringify(dataForPrint));
    this.router.navigateByUrl('/nursing/nursing-print');
	}

	exportMedicationCsv(dataForCSV : any) {
		var fileName = "INICU-Nursing-Medication.csv";
		var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
		var finalCSVData = encodeURI(csvData);

		var downloadLink = document.createElement("a");
		downloadLink.setAttribute("href", finalCSVData);
		downloadLink.setAttribute("download", fileName);
		downloadLink.click();
	}

	submitObject() {
		if(!this.selectedMedication.bolus && this.selectedMedication.freq_type != null && this.selectedMedication.freq_type.toLowerCase() == 'continuous' && this.nursingMedicationModel.currentEmptyObj.given_time != null){
			if(this.nursingMedicationModel.currentEmptyObj.given_dose != null && this.nursingMedicationModel.currentEmptyObj.delivered_volume != null && this.nursingMedicationModel.currentEmptyObj.delivered_volume != '') {
				this.saveNursingMedication();
			}
			else if(this.nursingMedicationModel.currentEmptyObj.given_dose == null && this.selectedMedication.topical_frequency != null && this.nursingMedicationModel.currentEmptyObj.delivered_volume != null && this.nursingMedicationModel.currentEmptyObj.delivered_volume != ''){
				this.nursingMedicationModel.currentEmptyObj.given_dose = 0;
				this.saveNursingMedication();
			}
			else if(this.nursingMedicationModel.currentEmptyObj.given_dose == null && this.selectedMedication.topical_frequency != null && this.selectedMedication.topical_drops == null && this.selectedMedication.sachets == null && this.selectedMedication.quantity == null && this.selectedMedication.volume == null){
				this.nursingMedicationModel.currentEmptyObj.given_dose = 0;
				this.saveNursingMedication();
			}
		}
		else{
			if(this.nursingMedicationModel.currentEmptyObj.given_dose != null && this.nursingMedicationModel.currentEmptyObj.given_time != null){
				this.saveNursingMedication();
			}
			else if(this.nursingMedicationModel.currentEmptyObj.given_dose == null && this.selectedMedication.topical_frequency != null && this.nursingMedicationModel.currentEmptyObj.delivered_volume != null && this.nursingMedicationModel.currentEmptyObj.delivered_volume != ''){
				this.nursingMedicationModel.currentEmptyObj.given_dose = 0;
				this.saveNursingMedication();
			}
			else if(this.nursingMedicationModel.currentEmptyObj.given_dose == null && this.selectedMedication.topical_frequency != null && this.selectedMedication.topical_drops == null && this.selectedMedication.sachets == null && this.selectedMedication.quantity == null && this.selectedMedication.volume == null){
				this.nursingMedicationModel.currentEmptyObj.given_dose = 0;
				this.saveNursingMedication();
			}
			else if(this.nursingMedicationModel.currentEmptyObj.brand != null && this.nursingMedicationModel.currentEmptyObj.given_time != null && this.selectedMedication.freq_type == null){
				this.nursingMedicationModel.currentEmptyObj.given_dose = 0;
				this.saveNursingMedication();
			}
		}
    if(this.nursingMedicationModel.currentBloodProductObj.execution_time != null && this.nursingMedicationModel.currentBloodProductObj.delivered_volume != null && this.nursingMedicationModel.currentBloodProductObj.delivered_volume != '')
		  this.saveBloodProduct();

    if(!this.isEmpty(this.nursingMedicationModel.currentHeplockObj.deliveredVolume) && !this.isEmpty(this.nursingMedicationModel.currentHeplockObj.execution_time)){
      this.saveNursingHeplock();
    }
	}

  saveNursingHeplock(){
		this.nursingMedicationModel.currentHeplockObj.loggeduser = this.loggedUser;
		this.nursingMedicationModel.currentHeplockObj.uhid = this.uhid;

		// this.isLoaderVisible = true;
    // this.loaderContent = 'Saving...';
		try {
			this.http.post(this.apiData.saveNursingHeplock, this.nursingMedicationModel).subscribe(res => {
			// this.isLoaderVisible = false;
			// this.loaderContent = '';
				if(res.json().type=="success"){
					this.nursingMedicationModel = res.json().returnedObject;
          this.selectedHeplock = {};
					this.generateMedicationMap();
					this.generatebloodProductMap();
				}
			}, err => {
				console.log("Error occured.")
			});
		} catch(e) {
			console.log("Exception in http service:"+e);
		};
	}

	saveNursingMedication(){
		this.nursingMedicationModel.currentEmptyObj.loggeduser = this.loggedUser;
		this.nursingMedicationModel.currentEmptyObj.uhid = this.uhid;

		if(this.nursingMedicationModel.currentEmptyObj.assessment_medicine) {
			this.nursingMedicationModel.currentEmptyObj.baby_presid = this.selectedMedication.assessmentid;
			this.nursingMedicationModel.currentAssessmentMedicationObj = this.selectedMedication;
		} else {
			this.nursingMedicationModel.currentEmptyObj.baby_presid = this.selectedMedication.babyPresid;
			this.nursingMedicationModel.currentMedicationObj = this.selectedMedication;
		}

		this.isLoaderVisible = true;
    this.loaderContent = 'Saving...';
		try {
			this.http.post(this.apiData.saveNursingMedication, this.nursingMedicationModel).subscribe(res => {
			this.isLoaderVisible = false;
			this.loaderContent = '';
				if(res.json().type=="success"){
					this.nursingMedicationModel = res.json().returnedObject;
					this.selectedMedication = {};
					this.generateMedicationMap();
					this.generatebloodProductMap();
				}
			}, err => {
				console.log("Error occured.")
			});
		} catch(e) {
			console.log("Exception in http service:"+e);
		};
	}

	generatebloodProductMap() {
		this.bloodProductMap = {};
		if(this.nursingMedicationModel.pastNursingBloodProductList != null && this.nursingMedicationModel.pastNursingBloodProductList.length > 0) {
			for (var i=0; i < this.nursingMedicationModel.pastNursingBloodProductList.length; i++) {
				var item = this.nursingMedicationModel.pastNursingBloodProductList[i];
				this.bloodProductMap[item.doctor_blood_products_id] = item;
			}
		}

		for (var i=0; i < this.nursingMedicationModel.bloodProductList.length; i++) {
			var item = this.nursingMedicationModel.bloodProductList[i];
			item.assessment_time = new Date(item.assessment_time);
			if(this.bloodProductMap[item.doctor_blood_products_id] != undefined &&
				this.bloodProductMap[item.doctor_blood_products_id] != null) {
					item.nursingObj = this.bloodProductMap[item.doctor_blood_products_id];
			} else {
				item.nursingObj = Object.assign({}, this.nursingMedicationModel.emptyBloodProductObj);
			}
		}

	}

	submitCloseBloodProductPopup() {
		this.saveBloodProduct();
		this.closePreparation(true);
	}

	saveBloodProduct(){
		this.nursingMedicationModel.currentBloodProductObj.loggeduser = this.loggedUser;
		this.nursingMedicationModel.currentBloodProductObj.uhid = this.uhid;
		this.nursingMedicationModel.currentBloodProductObj.doctor_blood_products_id = this.selectedBloodProduct.doctor_blood_products_id;

		this.isLoaderVisible = true;
		this.loaderContent = 'Saving...';
		try {
			this.http.post(this.apiData.saveNursingBloodProduct, this.nursingMedicationModel).subscribe(res => {
			this.isLoaderVisible = false;
			this.loaderContent = '';
				if(res.json().type=="success"){
					this.nursingMedicationModel = res.json().returnedObject;
					this.selectedBloodProduct = {};
					this.generatebloodProductMap();
					this.generateMedicationMap();
				}
			}, err => {
				console.log("Error occured.")
			});
		} catch(e) {
			console.log("Exception in http service:"+e);
		};
	}

	medTypeChange(item : any){
		if(item.preparationObj.content_type != 'Liquid') {
			item.preparationObj.med_volume = null;
		}
		this.calMedStrength(item);
	}

	calMedStrength(item : any){
		if(item.preparationObj.content_dose != null && item.preparationObj.content_dose != 0
			&& item.preparationObj.med_volume != null && item.preparationObj.med_volume != 0) {
			item.preparationObj.med_strength = this.round((item.preparationObj.content_dose / item.preparationObj.med_volume), 1);
		} else {
			item.preparationObj.med_strength = null;
		}
		this.calReconstStrength(item);
	}

	calReconstStrength(item : any){
		if(item.preparationObj.content_dose != null && item.preparationObj.content_dose != 0
			&& item.preparationObj.reconstituted_volume != null && item.preparationObj.reconstituted_volume != 0 && item.preparationObj.reconstituted_volume != '') {
				if(item.preparationObj.content_type == 'Liquid' && item.preparationObj.reconstituted_volume <= item.preparationObj.med_volume) {
					this.prepMsg = "Reconstituted volume can't be less than Medicine content volume.";
				} else {
					this.prepMsg = null;
					item.preparationObj.final_strength = this.round((item.preparationObj.content_dose
						/ item.preparationObj.reconstituted_volume), 1);
				}
		} else {
			item.preparationObj.final_strength = null;
			item.preparationObj.reconstituted_type = null;
		}
		this.getMedInstruction(item);
	}

	setMedicationPreparation(item : any){
		if(this.selectedMedication.preparationObj.nursing_instruction == null
			|| this.selectedMedication.preparationObj.nursing_instruction == '') {
			return;
		}
		item.loggeduser = this.loggedUser;
		item.uhid = this.uhid;
		item.baby_presid = this.selectedMedication.babyPresid;
		this.isLoaderVisible = true;
    this.loaderContent = 'Saving...';
		try{
			this.http.post(this.apiData.setMedicationPreparation, item).subscribe(res => {
			this.isLoaderVisible = false;
			this.loaderContent = '';
				if(res.json().type=="success") {
					this.nursingMedicationModel = res.json().returnedObject;
					this.selectedMedication = {};
					this.closePreparation(true);
					this.generatebloodProductMap();
					this.generateMedicationMap();
				}
			},
			err => {
				console.log("Error occured.")
			});
		}
		catch(e){
			console.log("Exception in http service:"+e);
		};
	}

	round(value : any, precision : any) {
		var multiplier = Math.pow(10, precision);
		return Math.round(value * multiplier) / multiplier;
	}

	ngOnInit() {
		console.log("In Nursing Medication Panel Controller");
		this.getNursingMedication();
	}
}
