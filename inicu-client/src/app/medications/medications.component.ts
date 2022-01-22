import { Component, OnInit } from '@angular/core';
import { DoctorPanelComponent } from '../doctor-panel/doctor-panel.component';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { MedDropDown } from './medDropDown';
import { BabyPrescriptionModel } from './babyPrescriptionModel';
import { BabyPrescription } from './babyPrescription';
import { Medicine } from './medicine';
import { AppComponent } from '../app.component';
import { ExportCsv } from '../export-csv';
import { Keyvalue } from '../userpanel/Keyvalue';
import * as $ from 'jquery';

@Component({
  selector: 'medications',
  templateUrl: './medications.component.html',
  styleUrls: ['./medications.component.css']
})
export class MedicationsComponent implements OnInit {
	apiData : APIurl;
	uhid : string;
	workingWeight : any;
	loggedUser : string;
	http: Http;
	router : Router;
	dropdownData : MedDropDown;
	prescription : BabyPrescriptionModel;
  minDate : string;
  maxDate : Date;
	selectedType : string;
	prescriptionFlag : string;
	startTimeHH : any;
	startTimeMM : any;
	startTimeMeridian : string;
	endDateValue : boolean;
	todayDate : Date;
	startMinDate : any;
	selectedMedicine : Medicine;
	stopMessage : any;
	medicineList : Medicine[];
	assessmentName : string;
  israteCalculated : boolean;
  gestationDays : any;
  gestationWeeks : any;
  parent_frequency : string;
  loading_dose: number;
  maintenance_dose : number;
  maintenance_frequency : string;
  dol : any;
  keyValue : Keyvalue;
  diluentListMap : any;
  diluents : any;
  indications : any;
  isDiluentsAvailable : boolean;
  indicationsAvailable : boolean;
  medEffectArray:any
  courseWhichTab : string;
  medEffect : string;
  medMonitoring : string;
  medStorage : string;
  medStorageArray : any;
  medMonitoringArray : any;
  recommendedDose : string;
  recommendedTime : string;
  interactionWarning : string;
  maximumConcentrationIV : number;
  maximumConcentrationIM : number;
  maximumConcentrationWarning : string;
  maximumConcentrationRate : number;
  maximumConcentrationRateWarning : string;
  calBrand : string;
  ironBrand : string;
  multiVitaminBrand : string;
  vitaminDBrand : string;
  mctBrand : string;
  isMedicineDisabled : boolean;
  oldMedicine : BabyPrescription;
  highlightTotalDose : boolean;
  highlightDose : boolean;
  highlightFrequency : boolean;
  selectedMedicineType : string;
  isWithMilk : boolean;
  frequencyFeedForMl : any;
	constructor(http: Http, router : Router) {
		this.apiData = new APIurl();
		this.http = http;
		this.router = router;
		this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
		this.workingWeight = JSON.parse(localStorage.getItem('selectedChild')).workingWeight;
		this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.gestationDays = JSON.parse(localStorage.getItem('gestationDays'));
    this.gestationWeeks = JSON.parse(localStorage.getItem('gestationWeeks'));
    this.dol = JSON.parse(localStorage.getItem('dol'));
		this.selectedType = '';
		this.prescriptionFlag = "current";
		this.startTimeHH = "";
		this.startTimeMM = "";
		this.startTimeMeridian = "";
		this.endDateValue = true;
		this.todayDate = new Date();
		this.startMinDate = this.formatter(this.todayDate);
		this.selectedMedicine = new Medicine();
		this.prescription = new BabyPrescriptionModel();
    this.oldMedicine = new BabyPrescription();
    this.israteCalculated = false;
    this.keyValue = new Keyvalue();
    this.diluentListMap = [];
    this.diluents = [];
    this.indications = [];
    this.isDiluentsAvailable = false;
    this.indicationsAvailable = false;
    this.recommendedDose = "";
    this.recommendedTime = "";
    this.interactionWarning = "";
    this.maximumConcentrationIV = null;
    this.maximumConcentrationIM = null;
    this.maximumConcentrationRate = null;
    this.maximumConcentrationWarning = "";
    this.maximumConcentrationRateWarning = "";
    this.selectedMedicineType = "";
    this.calBrand = null;
    this.ironBrand = null;
    this.mctBrand = null;
    this.multiVitaminBrand = null;
    this.vitaminDBrand = null;
    this.isMedicineDisabled = null;
    this.highlightTotalDose = false;
    this.highlightDose = false;
    this.highlightFrequency = false;
    this.isWithMilk = false;
    this.frequencyFeedForMl = null;
		if(this.isEmpty(this.workingWeight)){
			this.workingWeight = 1000;
		}
	}

  setFrequencyFeedForMl(medicine : BabyPrescription){
    if(medicine.medicinename == '3% Saline' || medicine.medicinename == '0.9% Normal Saline'){
      for(var i=0; i < this.prescription.dropDowns.frequency.length; i++) {
        var item = this.prescription.dropDowns.frequency[i];
        if(item.freqid == medicine.frequency){
          this.frequencyFeedForMl = (24/item.frequency_int);
        }
      }
    }
    this.calculateFeedPerMl(medicine);
    this.generateMedicineNotes(medicine);
  }

  toggleFeedPerMl(medicine : BabyPrescription){
      this.isWithMilk = !this.isWithMilk;
      this.calculateFeedPerMl(medicine);
  }

  calculateVolume(medicine : BabyPrescription){
    medicine.volume = Math.round(medicine.volumeMeq * (this.workingWeight/1000)*100)/100;
    this.generateMedicineNotes(medicine);
  }

  calculateFeedPerMl(medicine : BabyPrescription){
    if(this.frequencyFeedForMl != null){
      medicine.feedPerMl = Math.round(((medicine.volume * this.frequencyFeedForMl)/24)*100)/100;
      this.generateMedicineNotes(medicine);
    }
  }

	filterMedicines(selectedMedType : string) {
		var index = 0;
		this.medicineList = new Array<Medicine>();

		for(var i = 0; i < this.prescription.dropDowns.medicines.length; i++) {
			var medicine = this.prescription.dropDowns.medicines[i];
			if(selectedMedType == medicine.medicineTypeStr) {
				this.medicineList[index] = medicine;
				index++;
			}
		}
    this.selectedMedicine = null;
	}

	formatter(sourceDate : Date) {
		var date = new Date(date);
		var dd = date.getDate();
		var mm = date.getMonth();
		mm = mm + 1;
		var yy = date.getFullYear();
		if (dd < 10) {
		    dd = '0' + dd;
		}
		if (mm < 10) {
		    mm = '0' + mm;
		}
		return yy + "-" + mm + "-" + dd;
	}

	bolusAction(medicine : BabyPrescription) {
		medicine.freq_type =  null;
		if(medicine.bolus){
			medicine.frequency =  null;
		}
    this.calculateDose(medicine);
	}

	// frequency type - cont, stat, inter
	modeAction(medicine : BabyPrescription) {
		medicine.frequency =  null;
		if(medicine.freq_type == 'Continuous'){
			medicine.route = 'IV';
		} else {
      medicine.frequency = this.parent_frequency;
      this.prescription.currentPrescription.frequency = this.parent_frequency;
			medicine.route = null;
		}
    this.calculateDose(medicine);
	}

	routeAction(medicine : BabyPrescription) {
		if(medicine.route != 'IV'){
			medicine.rate =  null;
			if(medicine.route == 'PO') {
				medicine.po_type = 'Syrup';
			} else {
				medicine.po_type = null;
			}
		} else if(medicine.freq_type == 'Intermittent') {
			medicine.inf_mode = 'min';
			medicine.po_type = null;
		}
    medicine.feedPerMl = null;
    this.calculateDose(medicine);
	}

  // function used to apply intelligence on the basis of mode, route, dose type
  calculateDose(medicine : BabyPrescription) {
    if(this.prescription.dropDowns.recommendedNeofax != null && this.prescription.dropDowns.recommendedNeofax.length > 0){
      for(var i = 0; i < this.prescription.dropDowns.recommendedNeofax.length; i++) {

        if((this.prescription.dropDowns.recommendedNeofax[i].medid == medicine.medid) && (this.prescription.dropDowns.recommendedNeofax[i].bolus != null || this.prescription.dropDowns.recommendedNeofax[i].route != null || this.prescription.dropDowns.recommendedNeofax[i].mode != null)) {
          var neofaxDataFound = false;
          this.recommendedDose = "";
          this.recommendedTime = "";

          if((this.prescription.currentPrescription.indication == "Clear" && this.prescription.dropDowns.recommendedNeofax[i].indication == null) || this.prescription.currentPrescription.indication == null){
            //Case 1 (When all three are not null)
            if(medicine.freq_type != null && medicine.route != null && this.prescription.dropDowns.recommendedNeofax[i].bolus != null && this.prescription.dropDowns.recommendedNeofax[i].route != null && this.prescription.dropDowns.recommendedNeofax[i].mode != null){
              if(medicine.bolus == this.prescription.dropDowns.recommendedNeofax[i].bolus && medicine.freq_type == this.prescription.dropDowns.recommendedNeofax[i].mode && medicine.route == this.prescription.dropDowns.recommendedNeofax[i].route){
                neofaxDataFound = true;
              }
            }

            //Case 2 (When route and mode are not null)
            else if(medicine.freq_type != null && medicine.route != null && this.prescription.dropDowns.recommendedNeofax[i].route != null && this.prescription.dropDowns.recommendedNeofax[i].mode != null && this.prescription.dropDowns.recommendedNeofax[i].bolus == null){
              if(medicine.freq_type == this.prescription.dropDowns.recommendedNeofax[i].mode && medicine.route == this.prescription.dropDowns.recommendedNeofax[i].route){
                neofaxDataFound = true;
              }
            }

            //Case 3 (When route and bolus are not null)
            else if(medicine.route != null && this.prescription.dropDowns.recommendedNeofax[i].bolus != null && this.prescription.dropDowns.recommendedNeofax[i].route != null && this.prescription.dropDowns.recommendedNeofax[i].mode == null){
              if(medicine.bolus == this.prescription.dropDowns.recommendedNeofax[i].bolus && medicine.route == this.prescription.dropDowns.recommendedNeofax[i].route){
                neofaxDataFound = true;
              }
            }

            //Case 3A (When mode and bolus are not null)
            else if(medicine.freq_type != null && this.prescription.dropDowns.recommendedNeofax[i].bolus != null && this.prescription.dropDowns.recommendedNeofax[i].route == null && this.prescription.dropDowns.recommendedNeofax[i].mode != null){
              if(medicine.bolus == this.prescription.dropDowns.recommendedNeofax[i].bolus && medicine.freq_type == this.prescription.dropDowns.recommendedNeofax[i].mode){
                neofaxDataFound = true;
              }
            }

            //Case 4 (When bolus is not null)
            else if(this.prescription.dropDowns.recommendedNeofax[i].bolus != null && this.prescription.dropDowns.recommendedNeofax[i].route == null && this.prescription.dropDowns.recommendedNeofax[i].mode == null){
              if(medicine.bolus == this.prescription.dropDowns.recommendedNeofax[i].bolus){
                neofaxDataFound = true;
              }
            }

            //Case 5 (When route is not null)
            else if(medicine.route != null && this.prescription.dropDowns.recommendedNeofax[i].route != null && this.prescription.dropDowns.recommendedNeofax[i].bolus == null && this.prescription.dropDowns.recommendedNeofax[i].mode == null){
              if(medicine.route == this.prescription.dropDowns.recommendedNeofax[i].route){
                neofaxDataFound = true;
              }
            }

            //Case 6 (When mode is not null)
            else if(medicine.freq_type != null && this.prescription.dropDowns.recommendedNeofax[i].mode != null && this.prescription.dropDowns.recommendedNeofax[i].bolus == null && this.prescription.dropDowns.recommendedNeofax[i].route == null){
              if(medicine.freq_type == this.prescription.dropDowns.recommendedNeofax[i].mode){
                neofaxDataFound = true;
              }
            }
          }
          if(this.prescription.currentPrescription.indication != null && this.prescription.currentPrescription.indication != "Clear"){
            if(this.prescription.dropDowns.recommendedNeofax[i].indication != null){
              if(this.prescription.currentPrescription.indication != null && this.prescription.currentPrescription.indication == this.prescription.dropDowns.recommendedNeofax[i].indication){
                neofaxDataFound = true;
                if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].bolus)){
                  this.prescription.currentPrescription.bolus = this.prescription.dropDowns.recommendedNeofax[i].bolus;
                }

                if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].mode)){
                  this.prescription.currentPrescription.freq_type = this.prescription.dropDowns.recommendedNeofax[i].mode;
                }

                if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].route)){
                  this.prescription.currentPrescription.route = this.prescription.dropDowns.recommendedNeofax[i].route;
                }
              }
              else{
                neofaxDataFound = false;
              }
            }else if(this.prescription.dropDowns.recommendedNeofax[i].indication == null && this.prescription.currentPrescription.indication != null){
              neofaxDataFound = false;
            }
          }

          if(neofaxDataFound == true){
            this.prescription.currentPrescription.dose_unit = this.prescription.dropDowns.recommendedNeofax[i].doseUnit;
            this.prescription.currentPrescription.dose = this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
            this.prescription.currentPrescription.frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
            this.prescription.currentPrescription.dose_unit_time = this.prescription.dropDowns.recommendedNeofax[i].perday;
            this.prescription.currentPrescription.inf_volume = this.prescription.dropDowns.recommendedNeofax[i].infusion_volume;
            this.prescription.currentPrescription.cal_dose_volume = (this.prescription.currentPrescription.dose/1000)*(this.workingWeight);
            this.prescription.currentPrescription.cal_dose_volume = this.round(this.prescription.currentPrescription.cal_dose_volume, 2);

            if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].mode && this.prescription.currentPrescription.freq_type == null)){
              this.prescription.currentPrescription.freq_type = this.prescription.dropDowns.recommendedNeofax[i].mode;
            }

            if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].route && this.prescription.currentPrescription.route == null)){
              this.prescription.currentPrescription.route = this.prescription.dropDowns.recommendedNeofax[i].route;
            }

            if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null && medicine.route != null && medicine.route == "IV"){
              this.prescription.currentPrescription.inf_time = this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
              this.prescription.currentPrescription.inf_mode = "min";
              medicine.inf_mode = "min";
              medicine.inf_time = this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
            }
            medicine.dose_unit = this.prescription.dropDowns.recommendedNeofax[i].doseUnit;
            medicine.dose = this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
            medicine.frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
            if(this.prescription.dropDowns.recommendedNeofax[i].lowerDose != null){
              this.recommendedDose = "(" + this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
              if(this.prescription.dropDowns.recommendedNeofax[i].upperDose != null){
                this.recommendedDose += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperDose;
              }
              this.recommendedDose += ")";
            }
            if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
              this.recommendedTime = "(" + this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
              if(this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime != null){
                this.recommendedTime += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime;
              }
              this.recommendedTime += ")";
            }
            break;
          }
          else{
            this.prescription.currentPrescription.dose_unit = null;
            this.prescription.currentPrescription.dose = null;
            this.prescription.currentPrescription.frequency = null;
            medicine.dose_unit = null;
            medicine.dose = null;
            medicine.frequency = null;
            this.prescription.currentPrescription.inf_time = null;
            medicine.inf_time = null;
            this.recommendedDose = "";
            this.recommendedTime = "";
          }
        }
      }
    }
    this.generateMedicineNotes(medicine);
  }

	infModeAction(medicine : BabyPrescription) {
		if(medicine.inf_mode == 'STAT'){
			medicine.inf_time =  null;
		}
    medicine.feedPerMl = null;
		this.generateMedicineNotes(medicine);
	}
  calculateMeqKg(medicine : BabyPrescription) {
    if(medicine.dose != null){
      medicine.cal_dose_volume = (medicine.dose/1000)*(this.workingWeight);
      medicine.cal_dose_volume = this.round(medicine.cal_dose_volume, 2);
      this.generateMedicineNotes(medicine);
    }
  }

	calculate_dose(medicine : BabyPrescription) {
    if(medicine.medicinename != null && (medicine.medicinename == '0.9% Normal Saline' || medicine.medicinename == '3% Saline')){
      this.setFrequencyFeedForMl(medicine);
    }
		if(medicine.dose != null){
			medicine.cal_dose_volume = (medicine.dose/1000)*(this.workingWeight);

			if(!medicine.bolus && medicine.dose_unit_time != null) {
				if(medicine.dose_unit_time == 'hour') {
          medicine.cal_dose_volume *= 24;
				} else if(medicine.dose_unit_time == 'min') {
					medicine.cal_dose_volume *= 60 * 24;
				} else if(medicine.dose_unit_time == 'sec') {
					medicine.cal_dose_volume *= 60 * 60 * 24;
				}
			}

			if(!this.isEmpty(medicine.frequency) && (medicine.dose_unit_time == null || medicine.dose_unit_time != 'dose')) {
				for(var i=0; i < this.prescription.dropDowns.frequency.length; i++) {
					var item = this.prescription.dropDowns.frequency[i];
					if(item.freqid == medicine.frequency){
						if(item.freqvalue != "Other") {
							medicine.cal_dose_volume /= item.frequency_int;
						}
					}
				}
			}
			medicine.cal_dose_volume = this.round(medicine.cal_dose_volume, 2);
			medicine.calculateddose = medicine.medicinename + ": " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2) + "/dose";
			this.calculate_quantity(medicine);
		}
		this.generateMedicineNotes(medicine);
	}

	calculate_quantity(medicine : BabyPrescription) {
		//if(!this.isEmpty(medicine.drug_strength)) {
		//	medicine.med_quantity = medicine.cal_dose_volume / medicine.drug_strength;
		//	medicine.med_quantity = this.round(medicine.med_quantity, 2);
			this.calculate_dil_volume(medicine);
		//}
		this.generateMedicineNotes(medicine);
	}

  medPopup(){
    if(this.selectedMedicine && (this.medEffect != null || this.medMonitoring != null || this.medStorage != null)){
      $("#CancelPopUp").addClass("showing");
      $("#medicineOverlay").addClass("show");
    }
  }

	calculate_dil_volume(medicine : BabyPrescription) {
		if(!this.isEmpty(medicine.inf_volume)) {
		// medicine.dilution_volume = medicine.inf_volume - medicine.med_quantity;
		// medicine.dilution_volume = this.round(medicine.dilution_volume, 2);
			this.calculate_rate(medicine);
			this.calculate_patient_receives(medicine);
			this.calculate_overfill_factor(medicine);
		}
		this.generateMedicineNotes(medicine);
	}

	calculate_rate(medicine : BabyPrescription) {
		if(!this.isEmpty(medicine.inf_time)) {
			medicine.rate = (medicine.inf_volume * 60) / medicine.inf_time;
			medicine.rate = this.round(medicine.rate, 2);
		}
    this.israteCalculated = false;
    if(medicine.freq_type == 'Continuous' && !medicine.bolus){
      if(!this.isEmpty(medicine.inf_volume)){
        medicine.rate = null;
        medicine.rate = medicine.inf_volume / 24;
        medicine.rate = this.round(medicine.rate, 2);
        this.israteCalculated = true;
      }
    }
		this.generateMedicineNotes(medicine);
	}

	calculate_overfill_factor(medicine : BabyPrescription) {
		if(!this.isEmpty(medicine.overfill_volume)) {
			medicine.actual_inf_volume = medicine.inf_volume + medicine.overfill_volume;
			medicine.overfill_factor = medicine.actual_inf_volume / medicine.inf_volume;

		//	medicine.actual_med_volume = medicine.med_quantity * medicine.overfill_factor;
		//	medicine.actual_med_volume = this.round(medicine.actual_med_volume, 2);
		//	medicine.actual_dil_volume = medicine.dilution_volume * medicine.overfill_factor;
		//	medicine.actual_dil_volume = this.round(medicine.actual_dil_volume, 2);

			medicine.actual_dose_volume = medicine.cal_dose_volume * medicine.overfill_factor;
			medicine.actual_dose_volume = this.round(medicine.actual_dose_volume, 2);
			this.calculate_patient_receives(medicine);
		}
		this.generateMedicineNotes(medicine);
    this.israteCalculated = false;
	}

	calculate_patient_receives(medicine : BabyPrescription) {
		if(!this.isEmpty(medicine.rate)) {
			var currentRate = medicine.inf_volume / 24;

			var dose = medicine.dose; //cal per day volume
			if(medicine.dose_unit_time == 'hour') {
				dose *= 24;
			} else if(medicine.dose_unit_time == 'min') {
        dose *= 60 * 24;
			} else if(medicine.dose_unit_time == 'sec') {
				dose *= 60 * 60 * 24;
			}
      if(medicine.freq_type == 'Continuous' && !medicine.bolus && !this.israteCalculated){
        if(!this.isEmpty(medicine.rate)){
          medicine.inf_volume = null;
          medicine.inf_volume = medicine.rate * 24;
          medicine.inf_volume = this.round(medicine.inf_volume, 2);
        }
      }

			medicine.patient_receive = (dose * medicine.rate) / currentRate;
			medicine.patient_receive = this.round(medicine.patient_receive, 2);
		}
		this.generateMedicineNotes(medicine);
	}

	addMedicine(status : string) {
    this.highlightDose = false;
    this.highlightFrequency = false;
    this.highlightTotalDose = false;
    this.isMedicineDisabled = null;
		var tempDate = this.prescription.currentPrescription.startdate;
		this.prescription.currentPrescription = new BabyPrescription();
		this.prescription.currentPrescription.startdate = tempDate;
    this.medEffect = null;
    this.medMonitoring = null;
    this.medStorage = null;
    this.recommendedDose = "";
    this.recommendedTime = "";
    this.prescription.currentPrescription.medid = this.selectedMedicine.medid;
		this.prescription.currentPrescription.medicinename = this.selectedMedicine.medname;
		this.prescription.currentPrescription.medicationtype = this.selectedMedicine.medicationtype;
		this.prescription.currentPrescription.medType = this.selectedMedicine.medicineTypeStr;
    // this.effects = this.selectedMedicine.effects;
		this.prescription.currentPrescription.bolus = false;
		this.prescription.currentPrescription.isInEditMode = false;
		this.prescription.currentPrescription.dose_unit = "mg/kg";
		this.prescription.currentPrescription.dose_unit_time = "day";
		this.prescription.currentPrescription.overfill_volume = 0;
    this.prescription.currentPrescription.isWithMilk = false;
    this.prescription.currentPrescription.volumeMeq = null;
    this.isWithMilk = false;
    this.isDiluentsAvailable = false;;
    this.indicationsAvailable = false;
    this.parent_frequency = null;
    this.loading_dose = null;
    this.maintenance_dose = null;
    this.maintenance_frequency = null;
    this.diluents = [];
    this.indications = [];
    this.interactionWarning = "";
    if(status == "refresh"){
      this.maximumConcentrationIV = null;
      this.maximumConcentrationIM = null;
      this.maximumConcentrationRate = null;
    }else{

      //Creating the string for storage, monitoring, Effects
      if(this.prescription.dropDowns.medicines != null && this.prescription.dropDowns.medicines.length > 0){
        for(var i = 0; i < this.prescription.dropDowns.medicines.length; i++) {
          if(this.prescription.dropDowns.medicines[i].medid == this.selectedMedicine.medid) {
            this.medEffect = this.selectedMedicine.effects;
            this.medMonitoring = this.selectedMedicine.monitoring;
            this.medStorage = this.selectedMedicine.storage;
            if(!this.isEmpty(this.selectedMedicine.maximumConcIV))
              this.maximumConcentrationIV = this.selectedMedicine.maximumConcIV;
            if(!this.isEmpty(this.selectedMedicine.maximumConcIM))
              this.maximumConcentrationIM = this.selectedMedicine.maximumConcIM;
            if(!this.isEmpty(this.selectedMedicine.maximumRate))
              this.maximumConcentrationRate = this.selectedMedicine.maximumRate;

            if(this.medEffect != null && this.medEffect != ""){
              var effectStr = this.medEffect.split("•");
               if(effectStr[0] == ''){
                effectStr.shift();
               }
               this.medEffectArray = effectStr;
             }else{
               this.medEffectArray = [];
             }
            if(this.medStorage != null && this.medStorage != ""){
              var storageStr = this.medStorage.split("•");
              if(storageStr[0] == ''){
                storageStr.shift();
              }
              this.medStorageArray = storageStr;
            }else{
              this.medStorageArray = [];
            }
            if(this.medMonitoring != null && this.medMonitoring != ""){
              var monitoringStr = this.medMonitoring.split("•");
              if(monitoringStr[0] == ''){
                monitoringStr.shift();
              }
              this.medMonitoringArray = monitoringStr;
            }else{
              this.medMonitoringArray = [];
            }
          }
        }
      }

      //Preparing the list of solutions available wrt Neofax
      if(this.prescription.dropDowns.solutionsList != null && this.prescription.dropDowns.solutionsList.length > 0){
        for(var i = 0; i < this.prescription.dropDowns.solutionsList.length; i++) {
          if(this.prescription.dropDowns.solutionsList[i].medid == this.selectedMedicine.medid) {
            this.keyValue = new Keyvalue();
            this.keyValue.key = "" + this.prescription.dropDowns.solutionsList[i].refmedsolutionsid + "";
            this.keyValue.value = this.prescription.dropDowns.solutionsList[i].diluenttype;
            this.prescription.currentPrescription.dilution_type = this.prescription.dropDowns.solutionsList[i].diluenttype;
            this.diluents.push(this.keyValue);
            this.isDiluentsAvailable = true;
          }
        }
      }
      if(!this.isDiluentsAvailable){
        this.diluents = this.diluentListMap;
      }

      //Populating Dose, Frequency, Unit and recommended string dose
      if(this.prescription.dropDowns.recommendedNeofax != null && this.prescription.dropDowns.recommendedNeofax.length > 0){
        for(var i = 0; i < this.prescription.dropDowns.recommendedNeofax.length; i++) {
          if(this.prescription.dropDowns.recommendedNeofax[i].medid == this.selectedMedicine.medid && this.prescription.dropDowns.recommendedNeofax[i].indication == null) {
            this.prescription.currentPrescription.dose_unit = this.prescription.dropDowns.recommendedNeofax[i].doseUnit;
            this.prescription.currentPrescription.dose = this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
            this.prescription.currentPrescription.frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
            this.prescription.currentPrescription.dose_unit_time = this.prescription.dropDowns.recommendedNeofax[i].perday;
            this.prescription.currentPrescription.inf_volume = this.prescription.dropDowns.recommendedNeofax[i].infusion_volume;
            this.prescription.currentPrescription.cal_dose_volume = (this.prescription.currentPrescription.dose/1000)*(this.workingWeight);
            this.prescription.currentPrescription.cal_dose_volume = this.round(this.prescription.currentPrescription.cal_dose_volume, 2);

            if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
              this.prescription.currentPrescription.inf_time = this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
              this.prescription.currentPrescription.inf_mode = "min";
            }

            if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].bolus)){
              this.prescription.currentPrescription.bolus = this.prescription.dropDowns.recommendedNeofax[i].bolus;
            }else{
              this.prescription.currentPrescription.bolus = false;
            }

            if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].mode)){
              this.prescription.currentPrescription.freq_type = this.prescription.dropDowns.recommendedNeofax[i].mode;
            }else{
              this.prescription.currentPrescription.freq_type = null;
            }

            if(!this.isEmpty(this.prescription.dropDowns.recommendedNeofax[i].route)){
              this.prescription.currentPrescription.route = this.prescription.dropDowns.recommendedNeofax[i].route;
            }else{
              this.prescription.currentPrescription.route = null;
            }
            //variable used as the frequency is initalized as null if the mode or route is changed.
            this.parent_frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
            if(this.prescription.dropDowns.recommendedNeofax[i].lowerDose != null){
              this.recommendedDose = "(" + this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
              if(this.prescription.dropDowns.recommendedNeofax[i].upperDose != null){
                this.recommendedDose += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperDose;
              }
              this.recommendedDose += ")";
            }
            if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
              this.recommendedTime = "(" + this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
              if(this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime != null){
                this.recommendedTime += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime;
              }
              this.recommendedTime += ")";
            }
            break;
          }
        }
      }

      //Populate Indications
      var tempIndications = [];
      if(this.prescription.dropDowns.recommendedNeofax != null && this.prescription.dropDowns.recommendedNeofax.length > 0){
        for(var i = 0; i < this.prescription.dropDowns.recommendedNeofax.length; i++) {
          if(this.prescription.dropDowns.recommendedNeofax[i].medid == this.selectedMedicine.medid && this.prescription.dropDowns.recommendedNeofax[i].indication != null && tempIndications.indexOf(this.prescription.dropDowns.recommendedNeofax[i].indication + "") == -1) {
            this.keyValue = new Keyvalue();
            this.keyValue.key = "" + this.prescription.dropDowns.recommendedNeofax[i].indication + "";
            this.keyValue.value = this.prescription.dropDowns.recommendedNeofax[i].indication;
            this.indications.push(this.keyValue);
            this.indicationsAvailable = true;
            tempIndications.push(this.prescription.dropDowns.recommendedNeofax[i].indication + "");
          }
        }
      }
      this.keyValue = new Keyvalue();
      this.keyValue.key = "Clear";
      this.keyValue.value = "Clear";
      this.indications.push(this.keyValue);

      //Drug Interactions code
      if(this.prescription.activePrescription != null && this.prescription.activePrescription.length > 0 && this.selectedMedicine.interactions != null && this.selectedMedicine.interactions != ''){
        for(var i = 0; i < this.prescription.activePrescription.length; i++){
          var interactionsList = this.selectedMedicine.interactions.replace("[","").replace("]",",").replace(" ","").split(",");
          if(interactionsList.includes(this.prescription.activePrescription[i].medicinename)){
            if(this.interactionWarning != "" && this.interactionWarning != null){
              this.interactionWarning = this.interactionWarning + ", " + this.prescription.activePrescription[i].medicinename;
            }
            else{
              this.interactionWarning = this.prescription.activePrescription[i].medicinename;
            }
          }
        }
      }
      this.generateMedicineNotes(this.prescription.currentPrescription);
      if(this.interactionWarning != "" && this.interactionWarning != null){
        $("#interactionsCancelPopUp").addClass("showing");
        $("#interactionsOverlay").addClass("show");
      }
    }
	}

  populateNeofax(item : BabyPrescription){

    this.medEffect = null;
    this.medMonitoring = null;
    this.medStorage = null;
    this.recommendedDose = "";
    this.recommendedTime = "";
    this.isDiluentsAvailable = false;
    this.indicationsAvailable = false;
    this.parent_frequency = null;
    this.loading_dose = null;
    this.maintenance_dose = null;
    this.maintenance_frequency = null;
    this.diluents = [];
    this.interactionWarning = "";
    this.maximumConcentrationIV = null;
    this.maximumConcentrationIM = null;
    this.maximumConcentrationRate = null;

    //Creating the string for storage, monitoring, Effects
    if(this.prescription.dropDowns.medicines != null && this.prescription.dropDowns.medicines.length > 0){
      for(var i = 0; i < this.prescription.dropDowns.medicines.length; i++) {
        if(this.prescription.dropDowns.medicines[i].medid == this.prescription.currentPrescription.medid) {
          this.medEffect = this.prescription.dropDowns.medicines[i].effects;
          this.medMonitoring = this.prescription.dropDowns.medicines[i].monitoring;
          this.medStorage = this.prescription.dropDowns.medicines[i].storage;
          if(!this.isEmpty(this.prescription.dropDowns.medicines[i].maximumConcIV))
            this.maximumConcentrationIV = this.prescription.dropDowns.medicines[i].maximumConcIV;
          if(!this.isEmpty(this.prescription.dropDowns.medicines[i].maximumConcIM))
            this.maximumConcentrationIM = this.prescription.dropDowns.medicines[i].maximumConcIM;
          if(!this.isEmpty(this.prescription.dropDowns.medicines[i].maximumRate))
            this.maximumConcentrationRate = this.prescription.dropDowns.medicines[i].maximumRate;

          if(this.medEffect != null && this.medEffect != ""){
            var effectStr = this.medEffect.split("•");
             if(effectStr[0] == ''){
              effectStr.shift();
             }
             this.medEffectArray = effectStr;
           }else{
             this.medEffectArray = [];
           }
          if(this.medStorage != null && this.medStorage != ""){
            var storageStr = this.medStorage.split("•");
            if(storageStr[0] == ''){
              storageStr.shift();
            }
            this.medStorageArray = storageStr;
          }else{
            this.medStorageArray = [];
          }
          if(this.medMonitoring != null && this.medMonitoring != ""){
            var monitoringStr = this.medMonitoring.split("•");
            if(monitoringStr[0] == ''){
              monitoringStr.shift();
            }
            this.medMonitoringArray = monitoringStr;
          }else{
            this.medMonitoringArray = [];
          }
        }
      }
    }

    //Preparing the list of solutions available wrt Neofax
    if(this.prescription.dropDowns.solutionsList != null && this.prescription.dropDowns.solutionsList.length > 0){
      for(var i = 0; i < this.prescription.dropDowns.solutionsList.length; i++) {
        if(this.prescription.dropDowns.solutionsList[i].medid == this.prescription.currentPrescription.medid) {
          this.keyValue = new Keyvalue();
          this.keyValue.key = "" + this.prescription.dropDowns.solutionsList[i].refmedsolutionsid + "";
          this.keyValue.value = this.prescription.dropDowns.solutionsList[i].diluenttype;
          this.diluents.push(this.keyValue);
          this.isDiluentsAvailable = true;
        }
      }
    }
    if(!this.isDiluentsAvailable){
      this.diluents = this.diluentListMap;
    }

    //Populating Dose, Frequency, Unit and recommended string dose
    if(this.prescription.dropDowns.recommendedNeofax != null && this.prescription.dropDowns.recommendedNeofax.length > 0){
      for(var i = 0; i < this.prescription.dropDowns.recommendedNeofax.length; i++) {
        if(this.prescription.dropDowns.recommendedNeofax[i].medid == this.prescription.currentPrescription.medid && this.prescription.dropDowns.recommendedNeofax[i].bolus == null && this.prescription.dropDowns.recommendedNeofax[i].route == null && this.prescription.dropDowns.recommendedNeofax[i].mode == null) {
          this.prescription.currentPrescription.dose_unit = this.prescription.dropDowns.recommendedNeofax[i].doseUnit;
          this.prescription.currentPrescription.dose = this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
          this.prescription.currentPrescription.frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
          this.prescription.currentPrescription.dose_unit_time = this.prescription.dropDowns.recommendedNeofax[i].perday;
          this.prescription.currentPrescription.inf_volume = this.prescription.dropDowns.recommendedNeofax[i].infusion_volume;
          this.prescription.currentPrescription.cal_dose_volume = (this.prescription.currentPrescription.dose/1000)*(this.workingWeight);
          this.prescription.currentPrescription.cal_dose_volume = this.round(this.prescription.currentPrescription.cal_dose_volume, 2);

          if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
            this.prescription.currentPrescription.inf_time = this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
            this.prescription.currentPrescription.inf_mode = "min";
          }
          if(this.prescription.dropDowns.recommendedNeofax[i].indication != null){
            this.keyValue = new Keyvalue();
            this.keyValue.key = "" + this.prescription.dropDowns.recommendedNeofax[i].indication + "";
            this.keyValue.value = this.prescription.dropDowns.recommendedNeofax[i].indication;
            this.indications.push(this.keyValue);
            this.indicationsAvailable = true;
          }
          this.keyValue = new Keyvalue();
          this.keyValue.key = "Clear";
          this.keyValue.value = "Clear";
          this.indications.push(this.keyValue);

          //variable used as the frequency is initalized as null if the mode or route is changed.
          this.parent_frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
          if(this.prescription.dropDowns.recommendedNeofax[i].lowerDose != null){
            this.recommendedDose = "(" + this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
            if(this.prescription.dropDowns.recommendedNeofax[i].upperDose != null){
              this.recommendedDose += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperDose;
            }
            this.recommendedDose += ")";
          }
          if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
            this.recommendedTime = "(" + this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
            if(this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime != null){
              this.recommendedTime += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime;
            }
            this.recommendedTime += ")";
          }
          break;
        }
        else if(this.prescription.dropDowns.recommendedNeofax[i].medid == this.prescription.currentPrescription.medid && this.prescription.dropDowns.recommendedNeofax[i].bolus == false && this.prescription.dropDowns.recommendedNeofax[i].route == null && this.prescription.dropDowns.recommendedNeofax[i].mode == null){
          this.prescription.currentPrescription.dose_unit = this.prescription.dropDowns.recommendedNeofax[i].doseUnit;
          this.prescription.currentPrescription.dose = this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
          this.prescription.currentPrescription.frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
          this.prescription.currentPrescription.cal_dose_volume = (this.prescription.currentPrescription.dose/1000)*(this.workingWeight);
          this.prescription.currentPrescription.cal_dose_volume = this.round(this.prescription.currentPrescription.cal_dose_volume, 2);
          this.prescription.currentPrescription.cal_dose_volume = (this.prescription.currentPrescription.dose/1000)*(this.workingWeight);
          this.prescription.currentPrescription.cal_dose_volume = this.round(this.prescription.currentPrescription.cal_dose_volume, 2);

          if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
            this.prescription.currentPrescription.inf_time = this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
            this.prescription.currentPrescription.inf_mode = "min";
          }
          //variable used as the frequency is initalized as null if the mode or route is changed.
          this.parent_frequency = this.prescription.dropDowns.recommendedNeofax[i].lowerFrequency;
          if(this.prescription.dropDowns.recommendedNeofax[i].lowerDose != null){
            this.recommendedDose = "(" + this.prescription.dropDowns.recommendedNeofax[i].lowerDose;
            if(this.prescription.dropDowns.recommendedNeofax[i].upperDose != null){
              this.recommendedDose += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperDose;
            }
            this.recommendedDose += ")";
          }
          if(this.prescription.dropDowns.recommendedNeofax[i].infusionTime != null){
            this.recommendedTime = "(" + this.prescription.dropDowns.recommendedNeofax[i].infusionTime;
            if(this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime != null){
              this.recommendedTime += "-" + this.prescription.dropDowns.recommendedNeofax[i].upperInfusionTime;
            }
            this.recommendedTime += ")";
          }
        }
      }
    }
  }

  cancelPopUp(){
    $("#CancelPopUp").removeClass("showing");
    $("#medicineOverlay").removeClass("show");
  }

  interactionsCancelPopUp(){
    $("#interactionsCancelPopUp").removeClass("showing");
    $("#interactionsOverlay").removeClass("show");
  }

  maxConcentartionsCancelPopUp(){
    $("#maxConcentartionCancelPopUp").removeClass("showing");
    $("#maxConcentartionOverlay").removeClass("show");
  }

  saveMedicineToList(medicine : BabyPrescription){
    if(this.validate()) {
      var maxConc = null;
      var routeType = null;
      this.maximumConcentrationWarning = "";
      this.maximumConcentrationRateWarning = "";
      if(!this.isEmpty(medicine.cal_dose_volume) && (!this.isEmpty(medicine.inf_volume))){
        if(!this.isEmpty(this.maximumConcentrationIV) && medicine.route == "IV"){
          routeType = "IV";
          if(medicine.dose_unit == "mg/kg")
            maxConc = (medicine.cal_dose_volume / medicine.inf_volume);
          else if(medicine.dose_unit == "μg/kg")
            maxConc = (medicine.cal_dose_volume / medicine.inf_volume) * (100);
        }
      }

      if(maxConc != null){
        if(this.maximumConcentrationRate != null && !this.isEmpty(medicine.rate)){
          if(this.maximumConcentrationRate < ((maxConc * medicine.rate) / 60)){
            this.maximumConcentrationRateWarning = "Maximum Concentration Rate is " + this.maximumConcentrationRate + " mg/min";
          }
        }
        if(routeType == "IV" && maxConc > this.maximumConcentrationIV){
          this.maximumConcentrationWarning = "Maximum Concentration is " + this.maximumConcentrationIV + " mg/ml";
          $("#maxConcentartionCancelPopUp").addClass("showing");
          $("#maxConcentartionOverlay").addClass("show");
        }
        else if(routeType == "IM" && maxConc > this.maximumConcentrationIM){
          this.maximumConcentrationWarning = "Maximum Concentration is " + this.maximumConcentrationIM + " mg/ml";
          $("#maxConcentartionCancelPopUp").addClass("showing");
          $("#maxConcentartionOverlay").addClass("show");
        }
      }


			medicine.uhid = this.uhid;
			medicine.loggeduser = this.loggedUser;
			var medStartDate = new Date(medicine.startdate);

			var hrs = medStartDate.getHours();
			var min = medStartDate.getMinutes();
			var ampm = " PM";

			if(hrs < 12) {
				ampm = " AM";
			} else if(hrs > 12) {
				hrs -= 12;
			}

			if(hrs < 10) {
				medicine.starttime = "0" + hrs + ":";
			} else {
				medicine.starttime = hrs + ":";
			}

			if(min < 10) {
				medicine.starttime += "0" + min + ampm;
			} else {
				medicine.starttime += min + ampm;
			}
			medicine.startdate = medStartDate;
			medicine.enddate = null;
			medicine.isactive = true;

      if(!((medicine.bolus && medicine.freq_type == 'Continuous') || (medicine.freq_type == 'Intermittent' && medicine.inf_mode != 'STAT'))){
        medicine.inf_time = null;
      }
      this.oldMedicine.enddate = new Date(medicine.startdate);


      var activeList = this.prescription.activePrescription;
      this.prescription.activePrescription = [];
      for(var i = 0;i < activeList.length;i++){
        if(activeList[i].babyPresid != medicine.babyPresid){
          this.prescription.activePrescription.push(activeList[i]);
        }
      }
      this.highlightDose = false;
      this.highlightFrequency = false;
      this.highlightTotalDose = false;
      this.prescription.currentPrescriptionList.push(this.oldMedicine);
      this.prescription.pastPrescriptions.push(this.oldMedicine);
      medicine.babyPresid = null;
			this.prescription.currentPrescriptionList.push(medicine);
			this.selectedMedicine = new Medicine();
			this.addMedicine('non-refresh');
		}
  }


  continueAllMedication(){
    for(var i=0; i < this.prescription.activePrescription.length;i++){
      if(this.prescription.activePrescription[i].isactive == true && (this.prescription.activePrescription[i].eventname == null || this.prescription.activePrescription[i].eventname == this.assessmentName)){
        this.prescription.currentPrescription = Object.assign({}, this.prescription.activePrescription[i]);
        this.oldMedicine = Object.assign({}, this.prescription.activePrescription[i]);
        this.populateNeofax(this.prescription.currentPrescription);
        this.prescription.currentPrescription.startdate = new Date();
        this.oldMedicine.isContinueMedication = true;
        this.oldMedicine.isEdit = false;
        this.prescription.currentPrescription.refBabyPresid = this.prescription.currentPrescription.babyPresid;
        this.oldMedicine.isactive = false;
        this.calculate_dose(this.prescription.currentPrescription);
        this.oldMedicine.enddate = new Date();
        this.prescription.currentPrescriptionList.push(this.oldMedicine);
        this.prescription.pastPrescriptions.push(this.oldMedicine);
        this.prescription.currentPrescription.babyPresid = null;
        this.prescription.currentPrescriptionList.push(this.prescription.currentPrescription);
        this.selectedMedicine = new Medicine();
      }
    }
    var activeList = this.prescription.activePrescription;
    this.prescription.activePrescription = [];
    for(var i = 0;i < activeList.length;i++){
      if(activeList[i].isactive == true && (activeList[i].eventname == null || activeList[i].eventname == this.assessmentName)){
      }
      else{
        this.prescription.activePrescription.push(activeList[i]);
      }
    }
  }

  continueMedication(item : BabyPrescription){

    this.prescription.currentPrescription = Object.assign({}, item);
    for(var i = 0; i < this.prescription.dropDowns.medicines.length; i++) {
      var medicine = this.prescription.dropDowns.medicines[i];
      if(item.medicationtype == medicine.medicationtype) {
        this.selectedMedicineType = medicine.medicineTypeStr;
        this.prescription.currentPrescription.medType = medicine.medicineTypeStr;
      }
    }
    this.oldMedicine = Object.assign({}, this.prescription.currentPrescription);
    this.populateNeofax(item);
    this.prescription.currentPrescription.startdate = new Date();
    this.isMedicineDisabled = true;
    this.oldMedicine.isContinueMedication = true;
    this.oldMedicine.isEdit = false;
    this.prescription.currentPrescription.refBabyPresid = this.prescription.currentPrescription.babyPresid;
    this.oldMedicine.isactive = false;
    this.calculate_dose(this.prescription.currentPrescription);
    if(this.oldMedicine.dose != this.prescription.currentPrescription.dose){
      this.highlightDose = true;
    }
    else{
      this.highlightDose = false;
    }
    if(this.oldMedicine.cal_dose_volume != this.prescription.currentPrescription.cal_dose_volume){
      this.highlightTotalDose = true;
    }
    else{
      this.highlightTotalDose = true;
    }
    if(this.oldMedicine.frequency != this.prescription.currentPrescription.frequency){
      this.highlightFrequency = true;
    }
    else{
      this.highlightFrequency = false;
    }
  }

  editMedication(item : BabyPrescription){
    this.prescription.currentPrescription = Object.assign({}, item);
    for(var i = 0; i < this.prescription.dropDowns.medicines.length; i++) {
      var medicine = this.prescription.dropDowns.medicines[i];
      if(item.medicationtype == medicine.medicationtype) {
        this.selectedMedicineType = medicine.medicineTypeStr;
        this.prescription.currentPrescription.medType = medicine.medicineTypeStr;
      }
    }
    this.oldMedicine = Object.assign({}, this.prescription.currentPrescription);
    this.populateNeofax(item);
    this.prescription.currentPrescription.startdate = new Date();
    this.isMedicineDisabled = false;
    this.oldMedicine.isContinueMedication = false;
    this.oldMedicine.isEdit = true;
    this.prescription.currentPrescription.refBabyPresid = this.prescription.currentPrescription.babyPresid;
    this.oldMedicine.isactive = false;
    this.calculate_dose(this.prescription.currentPrescription);
  }

	addMedicineToList(medicine : BabyPrescription) {
		if(this.validate()) {
      var maxConc = null;
      var routeType = null;
      this.maximumConcentrationWarning = "";
      this.maximumConcentrationRateWarning = "";
      if(!this.isEmpty(medicine.cal_dose_volume) && (!this.isEmpty(medicine.inf_volume))){
        if(!this.isEmpty(this.maximumConcentrationIV) && medicine.route == "IV"){
          routeType = "IV";
          if(medicine.dose_unit == "mg/kg")
            maxConc = (medicine.cal_dose_volume / medicine.inf_volume);
          else if(medicine.dose_unit == "μg/kg")
            maxConc = (medicine.cal_dose_volume / medicine.inf_volume) * (100);
        }
        // else if(!this.isEmpty(this.maximumConcentrationIM) && medicine.route == "IM"){
        //   if(medicine.dose_unit == "mg/kg")
        //     maxConc = (medicine.cal_dose_volume / medicine.inf_volume);
        //   else if(medicine.dose_unit == "μg/kg")
        //     maxConc = (medicine.cal_dose_volume / medicine.inf_volume) * (100);
        // }
      }

      if(maxConc != null){
        if(this.maximumConcentrationRate != null && !this.isEmpty(medicine.rate)){
          if(this.maximumConcentrationRate < ((maxConc * medicine.rate) / 60)){
            this.maximumConcentrationRateWarning = "Maximum Concentration Rate is " + this.maximumConcentrationRate + " mg/min";
          }
        }
        if(routeType == "IV" && maxConc > this.maximumConcentrationIV){
          this.maximumConcentrationWarning = "Maximum Concentration is " + this.maximumConcentrationIV + " mg/ml";
          $("#maxConcentartionCancelPopUp").addClass("showing");
          $("#maxConcentartionOverlay").addClass("show");
        }
        else if(routeType == "IM" && maxConc > this.maximumConcentrationIM){
          this.maximumConcentrationWarning = "Maximum Concentration is " + this.maximumConcentrationIM + " mg/ml";
          $("#maxConcentartionCancelPopUp").addClass("showing");
          $("#maxConcentartionOverlay").addClass("show");
        }
      }


			medicine.uhid = this.uhid;
			medicine.loggeduser = this.loggedUser;
			var medStartDate = new Date(medicine.startdate);

			var hrs = medStartDate.getHours();
			var min = medStartDate.getMinutes();
			var ampm = " PM";

			if(hrs < 12) {
				ampm = " AM";
			} else if(hrs > 12) {
				hrs -= 12;
			}

			if(hrs < 10) {
				medicine.starttime = "0" + hrs + ":";
			} else {
				medicine.starttime = hrs + ":";
			}

			if(min < 10) {
				medicine.starttime += "0" + min + ampm;
			} else {
				medicine.starttime += min + ampm;
			}
			medicine.startdate = medStartDate;
			medicine.enddate = null;
			medicine.isactive = true;

      if(!((medicine.bolus && medicine.freq_type == 'Continuous') || (medicine.freq_type == 'Intermittent' && medicine.inf_mode != 'STAT'))){
        medicine.inf_time = null;
      }
      medicine.medicineOrderDate = new Date(medicine.startdate);
			this.prescription.currentPrescriptionList.push(medicine);
			this.selectedMedicine = new Medicine();
			this.addMedicine('non-refresh');
		}
	}

	removeMedicineFromList(index : any) {
		this.prescription.currentPrescriptionList.splice(index, 1);
	}

	editMedicineFromList(index : any) {
		this.prescription.currentPrescription = this.prescription.currentPrescriptionList[index];
    if(this.prescription.currentPrescription.isContinueMedication == true){
      this.prescription.currentPrescription.isContinueMedication = false;
      this.prescription.currentPrescription.isEdit = true;
    }
		this.prescription.currentPrescriptionList.splice(index, 1);
	}

	generateMedicineNotes(medicine : BabyPrescription) {
		medicine.instruction = '';

		if(!(this.isEmpty(medicine.route) || this.isEmpty(medicine.freq_type) || this.isEmpty(medicine.dose))) {
      if(medicine.medicinename != 'Pot Klor' && medicine.medicinename != 'KCL Solution'){
  			if(medicine.route == 'IV' || medicine.route == 'IM'){
  				medicine.instruction += "Injection " + medicine.medicinename + " as " + medicine.route;
  			} else if(!this.isEmpty(medicine.po_type)) {
  				medicine.instruction += medicine.po_type + " " + medicine.medicinename + " ";
  			}

  			if(medicine.bolus) {
  				//medicine.instruction += " as Loading dose ";
  				if(medicine.freq_type == 'Continuous') {
  					medicine.instruction += " of " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2);
            if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
              medicine.instruction += " (" + medicine.actual_dose_volume + " " + medicine.dose_unit.substring(0,2) + " with overfill factor) ";
            }

      			if(!(this.isEmpty(medicine.inf_volume) || this.isEmpty(medicine.dilution_type))) {
              medicine.instruction += " diluted in " + medicine.inf_volume + " ml " + medicine.dilution_type ;
              if(medicine.actual_inf_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0){
                medicine.instruction += " (" + medicine.actual_inf_volume + " ml with overfill factor) ";
              }
              medicine.instruction += " as infusion ";
  	  				//medicine.instruction += " with " + medicine.dilution_type + " to prepare " + medicine.inf_volume + " ml of total infusion volume";
  					}

  					if(!(this.isEmpty(medicine.inf_time) || this.isEmpty(medicine.rate))) {
  						medicine.instruction += "over " + medicine.inf_time + " min @ " + medicine.rate + " ml/hr.";
  					}
          } else {
  //				} else if(!this.isEmpty(medicine.med_quantity)) {
  					//medicine.instruction += "in a dose of " + medicine.med_quantity;

            if(medicine.dose_unit.substring(0,2) == "μg"){
              var actual_dose = medicine.cal_dose_volume / 1000;
              actual_dose = this.round(actual_dose, 2);
              medicine.instruction += " in a dose of " + actual_dose + " " + "mg";
              if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
                medicine.instruction += " (" + medicine.actual_dose_volume + " μg with overfill factor) ";
              }
            }
            else{
              medicine.instruction += " in a dose of " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2);
              if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
                medicine.instruction += " (" + medicine.actual_dose_volume + " " + medicine.dose_unit.substring(0,2) + " with overfill factor) ";
              }
            }
  					//if(medicine.po_type=='Tablet') {
  					//	medicine.instruction += " tablet.";
  					//} else {
  					//	medicine.instruction += " ml";
  						if(medicine.route == 'IV' && !(this.isEmpty(medicine.inf_volume) || this.isEmpty(medicine.dilution_type))) {
                medicine.instruction += " diluted in " + medicine.inf_volume + " ml " + medicine.dilution_type;
                if(medicine.actual_inf_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0){
                  medicine.instruction += " (" + medicine.actual_inf_volume + " ml with overfill factor) ";
                }
                medicine.instruction += " as infusion ";
                //medicine.instruction += " with " + medicine.dilution_type + " to prepare " + medicine.inf_volume + " ml of total infusion volume";
  						}
  						medicine.instruction += ".";
  					//}
  				}
  			} else {
  				if(medicine.freq_type == 'Continuous') {
  					medicine.instruction += " infusion of " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2);
            if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
              medicine.instruction += " (" + medicine.actual_dose_volume + " " + medicine.dose_unit.substring(0,2) + " with overfill factor) ";
            }
  					if(!(this.isEmpty(medicine.inf_volume) || this.isEmpty(medicine.dilution_type))) {
              medicine.instruction += " with " + medicine.dilution_type + " to prepare " + medicine.inf_volume + " ml of total infusion volume";
              if(medicine.actual_inf_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0){
                medicine.instruction += " (" + medicine.actual_inf_volume + " ml with overfill factor) ";
              }
  					}

  					if(!this.isEmpty(medicine.rate)) {
  						medicine.instruction += ", Infusion rate @ " + medicine.rate + " ml/hr";
  					}
            if(medicine.dose_unit.substring(0,2) == "μg"){
              var actual_dose = medicine.cal_dose_volume / 1000;
              actual_dose = this.round(actual_dose, 2);
              medicine.instruction += ", total dose is " + actual_dose + " mg/day" + ".";
            }
            else{
              medicine.instruction += ", total dose is " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2) + "/day";
            }

  				} else {
  					//medicine.instruction += " as Maintenance dose ";

  					if(medicine.route == 'IV') {
              if(medicine.dose_unit.substring(0,2) == "μg"){
                var actual_dose = medicine.cal_dose_volume / 1000;
                actual_dose = this.round(actual_dose, 2);
                medicine.instruction += actual_dose + " mg";
                if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
                  medicine.instruction += " (" + medicine.actual_dose_volume + " μg with overfill factor) ";
                }
              }
              else{
                medicine.instruction += " " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2);
                if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
                  medicine.instruction += " (" + medicine.actual_dose_volume + " " + medicine.dose_unit.substring(0,2) + " with overfill factor) ";
                }
              }

  						if(!(this.isEmpty(medicine.inf_volume) || this.isEmpty(medicine.dilution_type))) {
                medicine.instruction += " diluted in " + medicine.inf_volume + " ml " + medicine.dilution_type;
                if(medicine.actual_inf_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0){
                  medicine.instruction += " (" + medicine.actual_inf_volume + " ml with overfill factor) ";
                }
                medicine.instruction += " as infusion ";
                //medicine.instruction += " with " + medicine.dilution_type + " to prepare " + medicine.inf_volume + " ml of total infusion volume";
  						}

  						if(!(medicine.inf_mode == 'STAT' || this.isEmpty(medicine.inf_time))) {
  							medicine.instruction += " over " + medicine.inf_time + " min, Infusion rate @ " + medicine.rate + " ml/hr";
  						}
  					// } else if(!this.isEmpty(medicine.med_quantity)) {
  					// 	medicine.instruction += "in a dose of " + medicine.med_quantity;
  					// 	if(medicine.po_type=='Tablet') {
  					// 		medicine.instruction += " tablet";
  					// 	} else {
  					// 		medicine.instruction += " ml";
  					// 	}
  					} else {
              if(medicine.dose_unit.substring(0,2) == "μg"){
                var actual_dose = medicine.cal_dose_volume / 1000;
                actual_dose = this.round(actual_dose, 2);
                medicine.instruction += " in a dose of " + actual_dose + " mg";
                if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
                  medicine.instruction += " (" + medicine.actual_dose_volume + " μg with overfill factor) ";
                }
              }
              else{
                medicine.instruction += " in a dose of " + medicine.cal_dose_volume + " " + medicine.dose_unit.substring(0,2);
                if(medicine.actual_dose_volume != null && medicine.overfill_volume != null && medicine.overfill_volume != 0 && medicine.route == 'IV'){
                  medicine.instruction += " (" + medicine.actual_dose_volume + " " + medicine.dose_unit.substring(0,2) + " with overfill factor) ";
                }
              }
            }

  					if(!this.isEmpty(medicine.frequency)) {
  						for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
  							if(medicine.frequency == this.prescription.dropDowns.frequency[i].freqid) {
  								medicine.instruction += " every " + this.prescription.dropDowns.frequency[i].freqvalue;
  								break;
  							}
  						}
  					}
  					medicine.instruction += ".";
  				}
  			}
      }else{
        if(medicine.medicinename == 'Pot Klor' && !this.isEmpty(medicine.cal_dose_volume)){
          var plotKlorVolume = ((15 / 20) * medicine.cal_dose_volume);
          medicine.instruction = "Administer " + plotKlorVolume + " ml of " + medicine.medicinename;
          if(!this.isEmpty(medicine.frequency)) {
            for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
              if(medicine.frequency == this.prescription.dropDowns.frequency[i].freqid) {
                var frequencySplit = this.prescription.dropDowns.frequency[i].freqvalue.split(" hr");
                var finalfloatKlorVolume = plotKlorVolume / (24 / parseInt(frequencySplit[0]));
                finalfloatKlorVolume = this.round(finalfloatKlorVolume, 2);
                medicine.instruction = "Administer " + finalfloatKlorVolume + " ml of " + medicine.medicinename + " every " + this.prescription.dropDowns.frequency[i].freqvalue;
                break;
              }
            }
          }

        }
        else if(medicine.medicinename == 'KCL Solution' && !this.isEmpty(medicine.cal_dose_volume)){
          var kclVolume = ((1 / 2) * medicine.cal_dose_volume);
          medicine.instruction = "Administer " + kclVolume + " ml of " + medicine.medicinename;
          if(!this.isEmpty(medicine.frequency)) {
            for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
              if(medicine.frequency == this.prescription.dropDowns.frequency[i].freqid) {
                var frequencySplit = this.prescription.dropDowns.frequency[i].freqvalue.split(" hr");
                var finalKclVolume = kclVolume / (24 / parseInt(frequencySplit[0]));
                finalKclVolume = this.round(finalKclVolume, 2);
                medicine.instruction = "Administer " + finalKclVolume + " ml of " + medicine.medicinename + " every " + this.prescription.dropDowns.frequency[i].freqvalue;
                break;
              }
            }
          }
        }
		}
  }
    else if(medicine.route == 'Topical' && medicine.topical_frequency != null){
      if((medicine.medicinename == 'Nasal saline drops' || medicine.medicinename == 'Gentil' || medicine.medicinename == 'Tobramycin')  && medicine.topical_drops != null){
        medicine.instruction = "Give " + medicine.topical_drops + " drops of " +  medicine.medicinename;
      }
      else if((medicine.medicinename == 'Nasal saline drops' || medicine.medicinename == 'Gentil' || medicine.medicinename == 'Tobramycin') && medicine.topical_drops == null){
        medicine.instruction = "Give " + medicine.medicinename;
      }
      else{
        medicine.instruction = "Apply " + medicine.medicinename;
      }
      for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
        if(medicine.topical_frequency == this.prescription.dropDowns.frequency[i].freqid) {
          medicine.instruction += " every " + this.prescription.dropDowns.frequency[i].freqvalue;
          break;
        }
      }
      medicine.instruction += ".";
    }
    else if(medicine.route == 'Intranasal' && medicine.topical_frequency != null){
      if((medicine.medicinename == 'Nasal saline drops' || medicine.medicinename == 'Gentil' || medicine.medicinename == 'Tobramycin')  && medicine.topical_drops != null){
        medicine.instruction = "Give " + medicine.topical_drops + " drops of " +  medicine.medicinename;
        for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
          if(medicine.topical_frequency == this.prescription.dropDowns.frequency[i].freqid) {
            medicine.instruction += " every " + this.prescription.dropDowns.frequency[i].freqvalue;
            break;
          }
        }
      }

      medicine.instruction += ".";
    }
    else if(medicine.medType == 'Probiotics' && (medicine.sachets != null || medicine.volume != null) && !this.isEmpty(medicine.topical_frequency)){
      if(medicine.medicinename != 'Protectus'){
        medicine.instruction = "Give " + medicine.sachets + " sachet of " + medicine.medicinename + " mixed with milk orally";
      }else if(medicine.medicinename == 'Protectus'){
        medicine.instruction = "Give " + medicine.volume + " ml of " + medicine.medicinename;
      }
      for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
        if(medicine.topical_frequency == this.prescription.dropDowns.frequency[i].freqid) {
          medicine.instruction += " every " + this.prescription.dropDowns.frequency[i].freqvalue;
          break;
        }
      }
      medicine.instruction += ".";
    }
    else if(medicine.medType == 'Inhaled drugs' && medicine.volume != null && !this.isEmpty(medicine.topical_frequency)){
      medicine.instruction = "Administer " + medicine.volume + " ml of " + medicine.medicinename;
      if(!this.isEmpty(medicine.inf_volume)) {
        medicine.instruction += " with " + medicine.inf_volume + "ml";
        if(!this.isEmpty(medicine.dilution_type)) {
          medicine.instruction += " of " + medicine.dilution_type + " as inhalation";
        }
      }
      for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
        if(medicine.topical_frequency == this.prescription.dropDowns.frequency[i].freqid) {
          medicine.instruction += " every " + this.prescription.dropDowns.frequency[i].freqvalue;
          break;
        }
      }
      medicine.instruction += ".";
    }
    else if(medicine.medicinename != null && (medicine.medicinename == '3% Saline' || medicine.medicinename == '0.9% Normal Saline')){
      if(medicine.volume != null){
        medicine.instruction = "Administer " + medicine.volume + " ml of " + medicine.medicinename;
        if(this.frequencyFeedForMl != null){
          if(this.frequencyFeedForMl == 1){
            medicine.instruction += " every hr";
          }
          else{
            medicine.instruction += " every " + this.frequencyFeedForMl + " hrs";
          }

          if(this.isWithMilk == true){
            medicine.instruction += " with " + medicine.feedPerMl + " ml of milk";
          }
        }
        medicine.instruction += ".";
      }
    }
    else if(medicine.medType == 'Suppository Drugs' && medicine.quantity != null && !this.isEmpty(medicine.topical_frequency)){
      medicine.instruction = "Administer " + medicine.quantity + " capsule of " + medicine.medicinename;
      for(var i = 0; i < this.prescription.dropDowns.frequency.length; i++) {
        if(medicine.topical_frequency == this.prescription.dropDowns.frequency[i].freqid) {
          medicine.instruction += " every " + this.prescription.dropDowns.frequency[i].freqvalue;
          break;
        }
      }
      medicine.instruction += ".";
    }
    else if(medicine.medType == 'Vaccinations' && medicine.route != null){
      medicine.instruction = "Administer " + medicine.medicinename + " via " + medicine.route + ".";
    }
	}

	validate(){
		if(this.prescription.currentPrescription.medicinename == null || this.prescription.currentPrescription.medicinename == ''
      || this.prescription.currentPrescription.route == null || this.prescription.currentPrescription.route == ''
      || this.prescription.currentPrescription.freq_type == null || this.prescription.currentPrescription.freq_type == ''
      || this.prescription.currentPrescription.dose == null || this.prescription.currentPrescription.dose == ''
      || this.prescription.currentPrescription.instruction == null || this.prescription.currentPrescription.instruction == '') {
        if((this.prescription.currentPrescription.topical_drops != null || this.prescription.currentPrescription.topical_frequency != null) && this.prescription.currentPrescription.instruction != null && this.prescription.currentPrescription.instruction != ''){
          return true;
        }
        else if(this.prescription.currentPrescription.medType == 'Vaccinations' && this.prescription.currentPrescription.route != null && this.prescription.currentPrescription.instruction != null && this.prescription.currentPrescription.instruction != ''){
          return true;
        }
        else if(this.prescription.currentPrescription.medicinename == '3% Saline' || this.prescription.currentPrescription.medicinename == '0.9% Normal Saline'){
                if(this.prescription.currentPrescription.route != null && this.prescription.currentPrescription.instruction != null && this.prescription.currentPrescription.instruction != ''){
                  return true;
                }
        }
        else{
          return false;
        }
		} else {
			return true;
		}
	}

	getPrescription() {
		try{
			this.http.request(this.apiData.getPrescription + this.uhid + "/" + this.loggedUser + "/" + this.gestationDays + "/" + this.gestationWeeks + "/" + this.dol + "/").subscribe((res: Response) => {
				console.log("prescription dropdown");
				this.prescription = res.json();
				console.log(this.prescription);
				this.startTimeMeridian = "";
				this.prescription.currentPrescription.startdate = new Date();
				this.processEndDateTime();

        if(this.prescription.weightForCal != null){
          this.workingWeight = this.prescription.weightForCal;
        }

				if(localStorage.getItem('medicationAssessment') != null) {
					this.assessmentName = JSON.parse(localStorage.getItem('medicationAssessment'));
					localStorage.removeItem("medicationAssessment");
					this.prescription.currentPrescriptionList = JSON.parse(localStorage.getItem('prescriptionList'));
					localStorage.removeItem("prescriptionList");
				}

        if(this.prescription != null && this.prescription.babyfeedDetailList != null){
          for(var i = 0; i < this.prescription.enAddtivesBrandNameList.length; i++) {
            if(this.prescription.babyfeedDetailList.calBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.calBrand){
              this.calBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
            }

            if(this.prescription.babyfeedDetailList.ironBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.ironBrand){
              this.ironBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
            }

            if(this.prescription.babyfeedDetailList.multiVitaminBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.multiVitaminBrand){
              this.multiVitaminBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
            }

            if(this.prescription.babyfeedDetailList.vitaminDBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.vitaminDBrand){
              this.vitaminDBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
            }

            if(this.prescription.babyfeedDetailList.mctBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.mctBrand){
              this.mctBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
            }
          }
        }
			});
		}catch(e){
			console.log("Exception in http service:"+e);
		};
	}

	backToAssessment() {
		localStorage.setItem('prescriptionList',JSON.stringify(this.prescription.currentPrescriptionList));
		localStorage.setItem('isComingFromPrescription',JSON.stringify(true));

		if(this.assessmentName == 'Jaundice') {
			this.router.navigateByUrl('/jaundice/assessment-sheet-jaundice');
		} else if(this.assessmentName == 'Sepsis') {
			this.router.navigateByUrl('/infection/assessment-sheet-infection');
		} else if(this.assessmentName == 'Stable Notes') {
			this.router.navigateByUrl('/stable/assessment-sheet-stable');
		} else if(this.assessmentName == 'Miscellaneous') {
			this.router.navigateByUrl('/miscellaneous/assessment-sheet-miscellaneous');
		} else if(this.assessmentName == 'Asphyxia' || this.assessmentName == 'Seizures') {
			localStorage.setItem('assessmentName',JSON.stringify(this.assessmentName));
			this.router.navigateByUrl('/cns/assessment-sheet-cns');
		} else if(this.assessmentName == 'RDS' || this.assessmentName == 'Apnea' || this.assessmentName == 'PPHN' || this.assessmentName == 'Pneumothorax') {
			localStorage.setItem('assessmentName',JSON.stringify(this.assessmentName));
			this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
		}
	}

	processEndDateTime() {
		for(var i = 0; i < this.prescription.activePrescription.length; i++) {
			var obj = this.prescription.activePrescription[i];
			if(obj.enddate != null) {
				obj.enddate = new Date(obj.enddate);
			}
		}
	}

	updateStopMessage(index : any) {
		this.prescription.activePrescription[index].stopMessage = "";
	}

	stopMedication(index : any) {
		var medicine = this.prescription.activePrescription[index];
		medicine.stopMessage = "";
		if(this.isEmpty(medicine.enddate)){
			medicine.stopMessage = "Please select medicine stop date and time";
		}else{
      medicine.isContinueMedication = null;
      medicine.isEdit = null;
			medicine.isactive = false;
			this.prescription.currentPrescriptionList.push(medicine);
			this.prescription.activePrescription.splice(index, 1);
		}
	}

	exportCsv() {
		try{
			this.http.request(this.apiData.exportPrescriptionCSV + this.uhid).subscribe((res: Response) => {
				let dataForCSV : any[] = res.json();

				var fileName = "Baby_Prescription_Details_" + this.uhid + ".csv";
				var csvData = "data:text/csv;charset=utf-8,"+ ExportCsv.convertToCSV(dataForCSV);
				var finalCSVData  = encodeURI(csvData);

				var downloadLink = document.createElement("a");
				downloadLink.setAttribute("href", finalCSVData);
				downloadLink.setAttribute("download", fileName);
				downloadLink.click();
			});
		}catch(e){
			console.log("Exception in http service:"+e);
		};
	}

	getDropDowns(){
		try{
			this.http.request(this.apiData.getDropdown).subscribe((res: Response) => {
				this.dropdownData = res.json();
			});
		}catch(e){
			console.log("Exception in http service:"+e);
		};
	};

	isEmpty(object : any) : boolean {
		if (object == null || object == '' || object == 'null' || object.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	round(value : any, precision : any) {
		var multiplier = Math.pow(10, precision);
		return Math.round(value * multiplier) / multiplier;
	}

  generateCurrentMedicationPrint(){
    if((this.prescription.activePrescription != null && this.prescription.activePrescription.length > 0) ||
        (this.prescription.babyfeedDetailList != null && (this.prescription.babyfeedDetailList.calsyrupTotal != null  || this.prescription.babyfeedDetailList.ironTotal !=null || this.prescription.babyfeedDetailList.vitaminaTotal !=null || this.prescription.babyfeedDetailList.vitamindTotal !=null || this.prescription.babyfeedDetailList.mctTotal !=null || this.prescription.babyfeedDetailList.sodiumTotal !=null || this.prescription.babyfeedDetailList.potassiumTotal !=null || this.prescription.babyfeedDetailList.calciumTotal !=null || this.prescription.babyfeedDetailList.phosphorousTotal !=null || this.prescription.babyfeedDetailList.mviTotal !=null || this.prescription.babyfeedDetailList.hco3Total !=null || this.prescription.babyfeedDetailList.magnesiumTotal !=null)) ){
      this.router.navigateByUrl('/med/medications-print');
    }
  }

	ngOnInit()
	{
	 	  this.maxDate = new Date();
    	var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
   		var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
   		var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
   		if(tempHr == 12 && tempMer == 'AM')
   		{
       		tempHr = '00';
    	}
    	else if(tempHr != 12 && tempMer == 'PM')
    	{
      		 tempHr = parseInt(tempHr) + 12;
   		}
        var tempFullTime = tempHr +':' +tempMin+':00';
        this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
        var tempPrevTime = this.minDate.slice(16,24);
        this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
        var diluentList = ["NS","1/2 NS","Sterile water","5% Dextrose","Distilled water","D2.5W","D5W","D7.5W","D10W","D15W","D20W","D50W","RL","Arginine hydrochloride 10%","Dex/AA","Saline","WFI","N/4 D5W"];
        for(var i=0; i < diluentList.length; i++){
          this.keyValue = new Keyvalue();
          this.keyValue.key = "" + i + "";
          this.keyValue.value = diluentList[i];
          this.diluentListMap.push(this.keyValue);
        }
    this.getDropDowns();
		this.getPrescription();
	}

}
