import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { MiscellaneousObj } from './miscellaneousObj';
import { MiscTempObj } from './miscTempObj';
import * as $ from 'jquery';

@Component({
  selector: 'assessment-sheet-miscellaneous',
  templateUrl: './assessment-sheet-miscellaneous.component.html',
  styleUrls: ['./assessment-sheet-miscellaneous.component.css']
})

export class AssessmentSheetMiscellaneousComponent implements OnInit {
  router : Router;
  http : Http;
  apiData : APIurl;
  date : Date;
  datetimedialog : Date;
  datetime: Date;
  childObject : any;
  listTestsByCategory : any;
  loggedInUserObj : any;
	investOrderSelected : any;
  whichTab : string;
  investOrderNotOrdered : any;
	orderSelectedText : any;
  orderSelectedTextForMiscellaneous2 : any;
	// miscellaneousValue : boolean;
  // miscellaneous2Value : boolean;
  isclinicalSectionMiscVisible : boolean;
  isclinicalSectionMiscVisibleForMiscellaneous2 : boolean;
  miscellaneousDropDownValue : string;
  selectedDecease : string;
  hourDayDiffJaundice : any;
  ageAtAssessmentHourFlag : boolean;
  isActionSectionMiscVisible : boolean;
  isActionSectionMiscVisibleForMiscellaneous2 : boolean;
  isPlanSectionMiscVisible : boolean;
  isPlanSectionMiscVisibleForMscellaneous2 : boolean;
  isCauseMiscVisible : boolean;
  isCauseMiscVisibleForMiscellaneous2 : boolean;
  isProgressMiscVisible : boolean;
  isProgressMiscVisibleForMiscellaneous2 : boolean;
  dateBirth : any;
  currentAssessmentDateTime : any;
  diffTime : any;
  outcomeRecords : any;
  statusMiscellaneous : MiscellaneousObj;
  vanishSubmitResponseVariable : boolean;
  miscTempObj : MiscTempObj;
  printDataObj : any;
  fromDateTableNull : boolean;
  toDateTableNull : boolean;
  fromToTableError : boolean;
  minDate : string;
  maxDate : Date;
  isLoaderVisible : boolean;
  loaderContent : string;
  constructor(http: Http, router: Router) {
    this.statusMiscellaneous = new MiscellaneousObj();
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.miscTempObj = new MiscTempObj();
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
    this.investOrderSelected = [];
    this.investOrderNotOrdered = [];
    this.orderSelectedText = "";
    this.orderSelectedTextForMiscellaneous2 = "";
    // this.miscellaneousValue = true;
    // this.miscellaneous2Value = true;
    this.isclinicalSectionMiscVisible = true;
    this.isclinicalSectionMiscVisibleForMiscellaneous2 = true;
    this.ageAtAssessmentHourFlag = false;
    this.isActionSectionMiscVisible = false;
    this.isActionSectionMiscVisibleForMiscellaneous2 = false;
    this.isPlanSectionMiscVisible = false;
    this.isPlanSectionMiscVisibleForMscellaneous2 = false;
    this.isCauseMiscVisible = false;
    this.isCauseMiscVisibleForMiscellaneous2 = false;
    this.outcomeRecords = null;
    this.isProgressMiscVisible = true;
    this.isProgressMiscVisibleForMiscellaneous2 = true;
    this.printDataObj = {};
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.init();
  }

  init(){
    this.maxDate = new Date();
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM'){
       tempHr = '00';
    }else if(tempHr != 12 && tempMer == 'PM'){
       tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).admitDate) + '';
    var tempPrevTime = this.minDate.slice(16,24);
    this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
    this.setDataMiscellaneous();
    this.miscTempObj.printFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
    this.miscTempObj.printTo = new Date();
  }

  redirectToPrescription = function(eventName) {
    localStorage.setItem('medicationAssessment',JSON.stringify(eventName));
    localStorage.setItem('statusMiscellaneous',JSON.stringify(this.statusMiscellaneous));
    localStorage.setItem('miscTempObj',JSON.stringify(this.miscTempObj));
    localStorage.setItem('prescriptionList',JSON.stringify(this.statusMiscellaneous.prescriptionList));
    localStorage.setItem('whichTab',this.whichTab);
    localStorage.setItem('investigationOrderStr',this.orderSelectedText);
    localStorage.setItem('investigationOrderStrForMiscellaneous2',this.orderSelectedTextForMiscellaneous2);
    this.router.navigateByUrl('/med/medications');
  }

  tabVisible = function(tabValue){
    console.log("tabValue = " + tabValue);
		if(tabValue == "misc"){
			this.whichTab = 'misc';
		}
    if(tabValue == "misc2"){
			this.whichTab = 'misc2';
		}
	}

  setDataMiscellaneous = function () {
    console.log(localStorage);
    if(localStorage.getItem('whichTab') != null){
      this.whichTab = localStorage.getItem('whichTab');
    }
    var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromPrescription'));
    if(isComingFromPrescription){
      this.statusMiscellaneous = JSON.parse(localStorage.getItem('statusMiscellaneous'));
      this.miscTempObj = JSON.parse(localStorage.getItem('miscTempObj'));
      this.statusMiscellaneous.prescriptionList = JSON.parse(localStorage.getItem('prescriptionList'));
      this.orderSelectedText = "";
      this.orderSelectedTextForMiscellaneous2 = "";

      var medStr = "";
      var medStartStr = "";
      var medStopStr = "";

      for(var index=0; index < this.statusMiscellaneous.prescriptionList.length; index++) {
        var medicine = this.statusMiscellaneous.prescriptionList[index];
        if(medicine.babyPresid == null) {
          medStartStr += ", " + medicine.medicinename;
        } else {
          medStopStr += ", " + medicine.medicinename;
        }
      }

      if(medStartStr != '') {
        medStr += "Started: " + medStartStr.substring(2, medStartStr.length);
      }

      if(medStopStr != '') {
        if(medStr == '') {
          medStr += "Stopped: " + medStopStr.substring(2, medStopStr.length);
        } else {
          medStr += ", Stopped: " + medStopStr.substring(2, medStopStr.length);
        }
      }

      if(this.whichTab == "misc"){
        this.tabVisible('misc');
        this.ActionMiscVisible();
        this.orderSelectedText = localStorage.getItem('investigationOrder');
        this.statusMiscellaneous.miscellaneous.medicationStr = medStr;
      }
      else{
        if(this.whichTab == "misc2"){
          this.tabVisible('misc2');
          this.ActionMiscVisibleForMiscellaneous2();
          this.orderSelectedText = localStorage.getItem('investigationOrderStrForMiscellaneous2');
          this.statusMiscellaneous.miscellaneous2.medicationStr = medStr;
        }
      }
      this.creatingProgressNotesTemplate();
      localStorage.removeItem('miscTempObj');
      localStorage.removeItem('statusMiscellaneous');
      localStorage.removeItem('prescriptionList');
      localStorage.removeItem('isComingFromPrescription');
      localStorage.removeItem('investigationOrderStr');
      localStorage.removeItem('investigationOrderStrForMiscellaneous2');

    } else {
      try{
        this.http.post(this.apiData.setDataMiscellaneous + this.childObject.uhid + "/test",).subscribe(res => {
          this.handleGetMiscData(res.json());
        },
        err => {
          console.log("Error occured.")
        });
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
	}

  handleGetMiscData(response: any){
    this.statusMiscellaneous = response;
    console.log(this.statusMiscellaneous);
    setTimeout(() => {
      this.vanishSubmitResponseVariable = false;
    }, 3000);

    this.statusMiscellaneous.miscellaneous.assessmentTime = new Date(this.maxDate);
    this.statusMiscellaneous.miscellaneous2.assessmentTime = new Date(this.maxDate);

    this.whichTab = localStorage.getItem('whichTab');

    if(this.whichTab == null || this.whichTab == 'misc'){
      for(var index = 0; index<this.statusMiscellaneous.listMiscellaneous.length;index++){
        var utcDate = this.statusMiscellaneous.listMiscellaneous[index].creationtime;
        var localDate = new Date(utcDate);
        this.statusMiscellaneous.listMiscellaneous[index].creationtime = localDate;
      }

      if(this.statusMiscellaneous.listMiscellaneous!=null && this.statusMiscellaneous.listMiscellaneous.length>0 && this.statusMiscellaneous.listMiscellaneous[0].miscellaneousstatus=='yes'){
        this.statusMiscellaneous.miscellaneous.ageofonset = this.statusMiscellaneous.listMiscellaneous[0].ageofonset;
        this.statusMiscellaneous.miscellaneous.isageofonsetinhours = this.statusMiscellaneous.listMiscellaneous[0].isageofonsetinhours;
        this.statusMiscellaneous.miscellaneous.disease = this.statusMiscellaneous.listMiscellaneous[0].disease;

      }

      if(localStorage.getItem('admissionform') != null){
        this.statusMiscellaneous.miscellaneous.miscellaneousstatus = 'yes';
        localStorage.setItem('redirectBackToAdmission',JSON.stringify(true));
        localStorage.removeItem('admissionform');
      }
    }

    if(this.whichTab == null || this.whichTab == 'misc2'){
      for(var index = 0; index<this.statusMiscellaneous.listMiscellaneous2.length;index++){
        var utcDate = this.statusMiscellaneous.listMiscellaneous2[index].creationtime;
        var localDate = new Date(utcDate);
        this.statusMiscellaneous.listMiscellaneous2[index].creationtime = localDate;
      }

      if(this.statusMiscellaneous.listMiscellaneous2!=null && this.statusMiscellaneous.listMiscellaneous2.length>0 && this.statusMiscellaneous.listMiscellaneous[0].miscellaneousstatus=='yes'){
        this.statusMiscellaneous.miscellaneous2.ageofonset = this.statusMiscellaneous.listMiscellaneous2[0].ageofonset;
        this.statusMiscellaneous.miscellaneous2.isageofonsetinhours = this.statusMiscellaneous.listMiscellaneous2[0].isageofonsetinhours;
        this.statusMiscellaneous.miscellaneous2.disease = this.statusMiscellaneous.listMiscellaneous2[0].disease;
      }

      if(localStorage.getItem('admissionform') != null){
        this.statusMiscellaneous.miscellaneous2.miscellaneousstatus = 'yes';
        localStorage.setItem('redirectBackToAdmission',JSON.stringify(true));
        localStorage.removeItem('admissionform');
      }
    }
    this.creatingProgressNotesTemplate();
    localStorage.removeItem('whichTab');
  }

  submitMiscellaneous = function(){
		//var isSuccessful = this.validateJaundice();
		this.statusMiscellaneous.miscellaneous.assessmentTime = new Date(this.statusMiscellaneous.miscellaneous.assessmentTime);
		console.log(this.statusMiscellaneous.miscellaneous.assessmentTime);


		// if(this.statusMiscellaneous.miscellaneous.miscellaneousstatus=='inactive'){
		// 	if(this.statusMiscellaneous.listMiscellaneous!=null && this.statusMiscellaneous.listMiscellaneous.length==0)
		// 		showModal("Episode of this system not yet started. Cannot make Inactive.");
		// 	else if(this.statusMiscellaneous.listMiscellaneous[0].miscellaneousstatus=='inactive'){
		// 		showModal("System is already Inactive. Cannot make Inactive again.");
		// 	} else if(this.statusMiscellaneous.listMiscellaneous[0].miscellaneousstatus!='no'){
		// 		showModal("Cannot make System Inactive. System is not Passive yet.");
		// 	}
		// 	else
		// 		this.saveMiscellaneous();
		// }
		// else if(this.statusMiscellaneous.miscellaneous.miscellaneousstatus=='no'){
		// 	if(this.statusMiscellaneous.listMiscellaneous.length==0 ){
		// 		showModal("Episode of System not yet started. Cannot make Passive.");
		// 	} else if(this.statusMiscellaneous.listMiscellaneous[0].miscellaneousstatus=='no'){
		// 		showModal("System is already passive.");
		// 	} else if(this.statusMiscellaneous.listMiscellaneous[0].miscellaneousstatus=='inactive'){
		// 		showModal("Cannot make System Passive. System is currently Inactive.");
		// 	}
		// 	else{
		// 		this.saveMiscellaneous();
		// 	}
		// }
		// else
		// 	this.saveMiscellaneous();
    this.saveMiscellaneous();
	}

  submitMiscellaneous2 = function(){
		this.statusMiscellaneous.miscellaneous2.assessmentTime = new Date(this.statusMiscellaneous.miscellaneous2.assessmentTime);
		console.log(this.statusMiscellaneous.miscellaneous2.assessmentTime);
    this.saveMiscellaneous2();
	}

	clinicalSectionMiscVisible = function(){
		this.isclinicalSectionMiscVisible = !this.isclinicalSectionMiscVisible ;
	}

  clinicalSectionMiscVisibleForMiscellaneous2 = function(){
		this.isclinicalSectionMiscVisibleForMiscellaneous2 = !this.isclinicalSectionMiscVisibleForMiscellaneous2 ;
	}

  showOrderInvestigation = function(){
    this.miscellaneousDropDownValue = "Respiratory";
    this.selectedDecease = "Respiratory";

    for(var obj in this.statusMiscellaneous.dropDowns.testsList){
      for(var index = 0; index<this.statusMiscellaneous.dropDowns.testsList[obj].length;index++){
        var testName = this.statusMiscellaneous.dropDowns.testsList[obj][index].testname;
        if(this.investOrderNotOrdered.indexOf(testName) != -1)
          this.statusMiscellaneous.dropDowns.testsList[obj][index].isSelected = true;
      }
    }

    this.listTestsByCategory = this.statusMiscellaneous.dropDowns.testsList[this.miscellaneousDropDownValue];
    /*if(this.orderSelectedText==null || this.orderSelectedText==''){
      if(this.miscellaneousDropDownValue=='Respiratory'){
        for(var index=0; index<this.listTestsByCategory.length;index++){
          this.listTestsByCategory[index].isSelected = true;
        }
      }
    }*/
    console.log("showOrderInvestigation initiated");
    $("#ballardOverlay").css("display", "block");
    $("#OrderInvestigationPopup").addClass("showing");
  }

  closeModalOrderInvestigation = function() {
    console.log("closeModalOrderInvestigation closing");
    $("#ballardOverlay").css("display", "none");
    $("#OrderInvestigationPopup").toggleClass("showing");
  };

  ageOnSetCalculation =function(changedParam){
		if(changedParam=='meridian'){
			if(this.statusMiscellaneous.miscellaneous.isageofonsetinhours ){

				if(this.hourDayDiffJaundice == null || this.hourDayDiffJaundice == undefined){
					this.hourDayDiffJaundice = 0;
				}

				this.statusMiscellaneous.miscellaneous.ageofonset *= 24;
				this.statusMiscellaneous.miscellaneous.ageofonset += this.hourDayDiffJaundice;
			}else if(!(this.statusMiscellaneous.miscellaneous.isageofonsetinhours)){
				this.hourDayDiffJaundice = this.statusMiscellaneous.miscellaneous.ageofonset%24;
				this.statusMiscellaneous.miscellaneous.ageofonset -= this.hourDayDiffJaundice;
				this.statusMiscellaneous.miscellaneous.ageofonset /= 24;
        this.statusMiscellaneous.miscellaneous.ageofonset = Math.round(this.statusMiscellaneous.miscellaneous.ageofonset);
			}
		}
		else if(changedParam=='meridiam'){

			if(this.statusMiscellaneous.miscellaneous.isageofassesmentinhours && this.ageAtAssessmentHourFlag){
				this.statusMiscellaneous.miscellaneous.ageatassesment *= 24;
				this.statusMiscellaneous.miscellaneous.ageatassesment += this.hourDayDiffAgeAtAssessment;
				this.ageAtAssessmentHourFlag = false;
			}else if(!(this.statusMiscellaneous.miscellaneous.isageofassesmentinhours || this.ageAtAssessmentHourFlag)){
				this.ageAtAssessmentHourFlag = true;
				this.hourDayDiffAgeAtAssessment = this.statusMiscellaneous.miscellaneous.ageatassesment%24;
				this.statusMiscellaneous.miscellaneous.ageatassesment -= this.hourDayDiffAgeAtAssessment;
				this.statusMiscellaneous.miscellaneous.ageatassesment /= 24;
			}
		}else{
			this.hourDayDiffAgeAtAssessment = 0;
		}

		this.creatingProgressNotesTemplate();
	};

  ageOnSetCalculationForMiscellaneous2 =function(changedParam){
		if(changedParam=='meridian'){
			if(this.statusMiscellaneous.miscellaneous2.isageofonsetinhours ){

				if(this.hourDayDiffJaundice == null || this.hourDayDiffJaundice == undefined){
					this.hourDayDiffJaundice = 0;
				}

				this.statusMiscellaneous.miscellaneous2.ageofonset *= 24;
				this.statusMiscellaneous.miscellaneous2.ageofonset += this.hourDayDiffJaundice;
			}else if(!(this.statusMiscellaneous.miscellaneous2.isageofonsetinhours)){
				this.hourDayDiffJaundice = this.statusMiscellaneous.miscellaneous2.ageofonset%24;
				this.statusMiscellaneous.miscellaneous2.ageofonset -= this.hourDayDiffJaundice;
				this.statusMiscellaneous.miscellaneous2.ageofonset /= 24;
        this.statusMiscellaneous.miscellaneous2.ageofonset = Math.round(this.statusMiscellaneous.miscellaneous2.ageofonset);
			}
		}
		else if(changedParam=='meridiam'){

			if(this.statusMiscellaneous.miscellaneous2.isageofassesmentinhours && this.ageAtAssessmentHourFlag){
				this.statusMiscellaneous.miscellaneous2.ageatassesment *= 24;
				this.statusMiscellaneous.miscellaneous2.ageatassesment += this.hourDayDiffAgeAtAssessment;
				this.ageAtAssessmentHourFlag = false;
			}else if(!(this.statusMiscellaneous.miscellaneous2.isageofassesmentinhours || this.ageAtAssessmentHourFlag)){
				this.ageAtAssessmentHourFlag = true;
				this.hourDayDiffAgeAtAssessment = this.statusMiscellaneous.miscellaneous2.ageatassesment%24;
				this.statusMiscellaneous.miscellaneous2.ageatassesment -= this.hourDayDiffAgeAtAssessment;
				this.statusMiscellaneous.miscellaneous2.ageatassesment /= 24;
			}
		}else{
			this.hourDayDiffAgeAtAssessment = 0;
		}
		this.creatingProgressNotesTemplate();
	};

  ActionMiscVisible = function(){
    this.isActionSectionMiscVisible = !this.isActionSectionMiscVisible;
  }

  ActionMiscVisibleForMiscellaneous2 = function(){
    this.isActionSectionMiscVisibleForMiscellaneous2 = !this.isActionSectionMiscVisibleForMiscellaneous2;
  }

  PlanMiscVisible = function(){
    this.isPlanSectionMiscVisible = !this.isPlanSectionMiscVisible;
  }

  PlanMiscVisibleForMiscellaneous2 = function(){
    this.isPlanSectionMiscVisibleForMscellaneous2 = !this.isPlanSectionMiscVisibleForMscellaneous2;
  }

  causeMiscSectionVisible = function(){
    this.isCauseMiscVisible = !this.isCauseMiscVisible;
  }

  causeMiscSectionVisibleForMiscellaneous2 = function(){
    this.isCauseMiscVisibleForMiscellaneous2 = !this.isCauseMiscVisibleForMiscellaneous2;
  }

  progressMiscVisible = function(){
    this.isProgressMiscVisible = !this.isProgressMiscVisible;
  }

  progressMiscVisibleForMiscellaneous2 = function(){
    this.isProgressMiscVisibleForMiscellaneous2 = !this.isProgressMiscVisibleForMiscellaneous2;
  }

  calculateAgeAtAssessmentManual = function(){
    this.dateBirth = this.childObject.dob;
    this.dateBirth = new Date(this.dateBirth)

    var timeBirthArray = this.childObject.timeOfBirth.split(',');
    var birthHour = parseInt(timeBirthArray[0]);
    var birthMin = parseInt(timeBirthArray[1]);

    if(timeBirthArray[2]=='AM'){
      if(birthHour==12)
        this.dateBirth.setHours(0);
      else
        this.dateBirth.setHours(birthHour);
    }else{
      if(birthHour==12)
        this.dateBirth.setHours(12);
      else
        this.dateBirth.setHours(birthHour+12);
    }

    this.dateBirth.setMinutes(birthMin);
    this.currentAssessmentDateTime = new Date(this.statusMiscellaneous.miscellaneous.assessmentTime);
    console.log(this.currentAssessmentDateTime);
    this.diffTime = this.currentAssessmentDateTime.getTime() - this.dateBirth.getTime();
    if(this.statusMiscellaneous.miscellaneous.isageofassesmentinhours == true)
      this.statusMiscellaneous.miscellaneous.ageatassesment = (this.diffTime/(1000*60*60))+1;
    else
      this.statusMiscellaneous.miscellaneous.ageatassesment = (this.diffTime/(1000*60*60*24))+0;

    this.statusMiscellaneous.miscellaneous.ageatassesment = parseInt(this.statusMiscellaneous.miscellaneous.ageatassesment);
    this.statusMiscellaneous.miscellaneous.ageatassesment = Math.round(this.statusMiscellaneous.miscellaneous.ageatassesment);
  }

  calculateAgeAtAssessmentManualForMiscellaneous2 = function(){
    this.dateBirth = this.childObject.dob;
    this.dateBirth = new Date(this.dateBirth)

    var timeBirthArray = this.childObject.timeOfBirth.split(',');
    var birthHour = parseInt(timeBirthArray[0]);
    var birthMin = parseInt(timeBirthArray[1]);

    if(timeBirthArray[2]=='AM'){
      if(birthHour==12)
        this.dateBirth.setHours(0);
      else
        this.dateBirth.setHours(birthHour);
    }else{
      if(birthHour==12)
        this.dateBirth.setHours(12);
      else
        this.dateBirth.setHours(birthHour+12);
    }

    this.dateBirth.setMinutes(birthMin);
    this.currentAssessmentDateTime = new Date(this.statusMiscellaneous.miscellaneous2.assessmentTime);
    console.log(this.currentAssessmentDateTime);
    this.diffTime = this.currentAssessmentDateTime.getTime() - this.dateBirth.getTime();
    if(this.statusMiscellaneous.miscellaneous2.isageofassesmentinhours == true)
      this.statusMiscellaneous.miscellaneous2.ageatassesment = (this.diffTime/(1000*60*60))+1;
    else
      this.statusMiscellaneous.miscellaneous2.ageatassesment = (this.diffTime/(1000*60*60*24))+0;

    this.statusMiscellaneous.miscellaneous2.ageatassesment = parseInt(this.statusMiscellaneous.miscellaneous2.ageatassesment);
    this.statusMiscellaneous.miscellaneous2.ageatassesment = Math.round(this.statusMiscellaneous.miscellaneous2.ageatassesment);
  }

  orderSelected = function(){
		console.log("Inside order selected function");
		this.orderSelectedText = "";
    this.orderSelectedTextForMiscellaneous2 = "";
    var tempOrderSelectedText = "";
		this.investOrderSelected = [];
		for(var obj in this.statusMiscellaneous.dropDowns.testsList){
			console.log(this.statusMiscellaneous.dropDowns.testsList[obj]);
			for(var index = 0; index<this.statusMiscellaneous.dropDowns.testsList[obj].length;index++){
				if(this.statusMiscellaneous.dropDowns.testsList[obj][index].isSelected==true){
					if(tempOrderSelectedText==''){
						tempOrderSelectedText =  this.statusMiscellaneous.dropDowns.testsList[obj][index].testname;
					}else{
						tempOrderSelectedText = tempOrderSelectedText +", "+ this.statusMiscellaneous.dropDowns.testsList[obj][index].testname;
					}

					this.investOrderSelected.push(this.statusMiscellaneous.dropDowns.testsList[obj][index].testname);
				} else {
					var testName = this.statusMiscellaneous.dropDowns.testsList[obj][index].testname;
					this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}

    if(this.whichTab == "misc"){
      this.orderSelectedText = tempOrderSelectedText;
    }
    else{
      if(this.whichTab == "misc2"){
        this.orderSelectedTextForMiscellaneous2 = tempOrderSelectedText;
      }
    }

		console.log(this.investOrderSelected);
		// showModal("Investigations order selected","success");
		this.creatingProgressNotesTemplate();
		//iterate ovver list for order list of selected...
		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");
	}


  saveMiscellaneous = function(){
    if(this.validateMiscellaneous()){
  		this.statusMiscellaneous.miscellaneous.uhid = this.childObject.uhid;
  		console.log(this.statusMiscellaneous);
  		//console.log(this.loggedInUserObj.userName);
  		this.ageAtAssessmentHourFlag = false;
  		this.statusMiscellaneous.userId = "test";
  		this.rdsHourFlag = false;
  		console.log("in save miscellaneous");
      this.isLoaderVisible = true;
      this.loaderContent = 'Saving...';
      localStorage.setItem('whichTab',this.whichTab);
      try{
        this.http.post(this.apiData.saveMiscellaneous, this.statusMiscellaneous)
        .subscribe(res => {
          this.handlePostMiscData(res.json());
        },
        err => {
          console.log("Error occured.")
        }

      );
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
	}
  handlePostMiscData = function(response: any){
    console.log("after save Miscellaneous");
    this.statusMiscellaneous = response;
    this.returnedResponse = response;
    this.vanishSubmitResponseVariable = true;
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.responseMessage = this.statusMiscellaneous.response.message;
    this.responseType = this.statusMiscellaneous.response.type;
    console.log(this.statusMiscellaneous.response.type);
    console.log(this.statusMiscellaneous.response.message);
    this.orderSelectedText = "";
    this.orderSelectedTextForMiscellaneous2 = "";
    console.log(this.returnedResponse);
    this.setDataMiscellaneous();
    this.investOrderSelected = [];
    this.investOrderNotOrdered = [];
  }

  validateMiscellaneous = function(){
   var isSuccessful = true;
   if(this.whichTab == "misc"){
     if (isSuccessful == true){
       if (this.hourDayDiffAgeAtAssessment == null || this.hourDayDiffAgeAtAssessment == undefined){
         this.hourDayDiffAgeAtAssessment = 0;
       }

       if(this.statusMiscellaneous.miscellaneous.isageofonsetinhours == this.statusMiscellaneous.miscellaneous.isageofassesmentinhours){
         if(this.statusMiscellaneous.miscellaneous.ageatassesment < this.statusMiscellaneous.miscellaneous.ageofonset){
           $("#ageatassessmentvalidinputMiscellaneous").css("display","block");
           isSuccessful = false;
           this.isLoaderVisible = false;
         }
         else{
           $("#ageatassessmentvalidinputMiscellaneous").css("display","none");
           isSuccessful = true;
         }
       }
       else if(this.statusMiscellaneous.miscellaneous.isageofonsetinhours == false && this.statusMiscellaneous.miscellaneous.isageofassesmentinhours == true){
         var ageAtAssessment = Object.assign({},this.statusMiscellaneous.miscellaneous.ageatassesment);
         ageAtAssessment -= this.hourDayDiffAgeAtAssessment;
         ageAtAssessment /= 24;
         ageAtAssessment = Math.round(ageAtAssessment);
         if(ageAtAssessment < this.statusMiscellaneous.miscellaneous.ageofonset){
           $("#ageatassessmentvalidinputMiscellaneous").css("display","block");
           isSuccessful = false;
           this.isLoaderVisible = false;
         }
         else{
           $("#ageatassessmentvalidinputMiscellaneous").css("display","none");
           isSuccessful = true;
         }
       }
       else if(this.statusMiscellaneous.miscellaneous.isageofonsetinhours == true && this.statusMiscellaneous.miscellaneous.isageofassesmentinhours == false){
         var ageAtAssessment = Object.assign({},this.statusMiscellaneous.miscellaneous.ageatassesment);
         ageAtAssessment *= 24;
         ageAtAssessment += this.hourDayDiffAgeAtAssessment;
         ageAtAssessment = Math.round(ageAtAssessment);
         if(ageAtAssessment < this.statusMiscellaneous.miscellaneous.ageofonset){
           $("#ageatassessmentvalidinputMiscellaneous").css("display","block");
           this.isLoaderVisible = true;
           isSuccessful = false;
         }
         else{
           $("#ageatassessmentvalidinputMiscellaneous").css("display","none");
           isSuccessful = true;
         }
       }
       else{
         $("#ageatassessmentvalidinputMiscellaneous").css("display","none");
         isSuccessful = true;
       }
     }

     if(this.statusMiscellaneous.miscellaneous.disease != null && this.statusMiscellaneous.miscellaneous.disease != ''){
      $("#diseasevalidinputMiscellaneous").css("display","none");
      isSuccessful =  true;
     }else{
      $("#diseasevalidinputMiscellaneous").css("display","block");
      isSuccessful = false;
      this.isLoaderVisible = false;
     }
   }
   else{
     if(this.whichTab == "misc2"){
       if (isSuccessful == true){
         if (this.hourDayDiffAgeAtAssessment == null || this.hourDayDiffAgeAtAssessment == undefined){
           this.hourDayDiffAgeAtAssessment = 0;
         }

         if(this.statusMiscellaneous.miscellaneous2.isageofonsetinhours == this.statusMiscellaneous.miscellaneous2.isageofassesmentinhours){
           if(this.statusMiscellaneous.miscellaneous2.ageatassesment < this.statusMiscellaneous.miscellaneous2.ageofonset){
             $("#ageatassessmentvalidinputMiscellaneous2").css("display","block");
             isSuccessful = false;
             this.isLoaderVisible = false;
           }
           else{
             $("#ageatassessmentvalidinputMiscellaneous2").css("display","none");
             isSuccessful = true;
           }
         }
         else if(this.statusMiscellaneous.miscellaneous2.isageofonsetinhours == false && this.statusMiscellaneous.miscellaneous2.isageofassesmentinhours == true){
           var ageAtAssessment = Object.assign({},this.statusMiscellaneous.miscellaneous2.ageatassesment);
           ageAtAssessment -= this.hourDayDiffAgeAtAssessment;
           ageAtAssessment /= 24;
           ageAtAssessment = Math.round(ageAtAssessment);
           if(ageAtAssessment < this.statusMiscellaneous.miscellaneous2.ageofonset){
             $("#ageatassessmentvalidinputMiscellaneous2").css("display","block");
             isSuccessful = false;
             this.isLoaderVisible = false;
           }
           else{
             $("#ageatassessmentvalidinputMiscellaneous2").css("display","none");
             isSuccessful = true;
           }
         }
         else if(this.statusMiscellaneous.miscellaneous2.isageofonsetinhours == true && this.statusMiscellaneous.miscellaneous2.isageofassesmentinhours == false){
           var ageAtAssessment = Object.assign({},this.statusMiscellaneous.miscellaneous2.ageatassesment);
           ageAtAssessment *= 24;
           ageAtAssessment += this.hourDayDiffAgeAtAssessment;
           ageAtAssessment = Math.round(ageAtAssessment);
           if(ageAtAssessment < this.statusMiscellaneous.miscellaneous2.ageofonset){
             $("#ageatassessmentvalidinputMiscellaneous2").css("display","block");
             this.isLoaderVisible = true;
             isSuccessful = false;
           }
           else{
             $("#ageatassessmentvalidinputMiscellaneous2").css("display","none");
             isSuccessful = true;
           }
         }
         else{
           $("#ageatassessmentvalidinputMiscellaneous2").css("display","none");
           isSuccessful = true;
         }
       }

       if(this.statusMiscellaneous.miscellaneous2.disease != null && this.statusMiscellaneous.miscellaneous2.disease != ''){
         $("#diseasevalidinputMiscellaneous2").css("display","none");
         isSuccessful =  true;
       }else{
         $("#diseasevalidinputMiscellaneous2").css("display","block");
         isSuccessful = false;
         this.isLoaderVisible = false;
       }
     }
   }
   console.log("isSuccessful = " + isSuccessful);
   return isSuccessful;
 }


  saveMiscellaneous2 = function(){
    if(this.validateMiscellaneous()){
      this.statusMiscellaneous.miscellaneous2.uhid = this.childObject.uhid;
  		console.log(this.statusMiscellaneous);
  		//console.log(this.loggedInUserObj.userName);
  		this.ageAtAssessmentHourFlag = false;
  		this.statusMiscellaneous.userId = "test";
  		this.rdsHourFlag = false;
  		console.log("in save miscellaneous");
      this.isLoaderVisible = true;
      this.loaderContent = 'Saving...';
      localStorage.setItem('whichTab',this.whichTab);
      try{
        this.http.post(this.apiData.saveMiscellaneous2, this.statusMiscellaneous)
        .subscribe(res => {
          this.handlePostMiscData(res.json());
        },
        err => {
          console.log("Error occured.")
        }

      );
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
	}

  populateTestsListByCategory = function(assessmentCategory){
		this.selectedDecease = assessmentCategory;
		console.log(assessmentCategory);
		this.listTestsByCategory = this.statusMiscellaneous.dropDowns.testsList[assessmentCategory];
		console.log( this.listTestsByCategory);
	}

  creatingProgressNotesTemplate = function() {
   //Miscellaneous 1
    var template = "";
		if(this.statusMiscellaneous.listMiscellaneous==null || this.statusMiscellaneous.listMiscellaneous.length<=0 ||
				this.statusMiscellaneous.miscellaneous.miscellaneousstatus=='inactive'){

			if(this.statusMiscellaneous.miscellaneous.isageofonsetinhours){
				if(this.statusMiscellaneous.miscellaneous.ageofonset*1 == 1 || this.statusMiscellaneous.miscellaneous.ageofonset*1 == 0){
					template += "Baby diagnosed at the age of "+this.statusMiscellaneous.miscellaneous.ageofonset + " hr. ";
				}else{
					template += "Baby diagnosed at the age of "+this.statusMiscellaneous.miscellaneous.ageofonset + " hrs. ";
				}
			}else{
				if(this.statusMiscellaneous.miscellaneous.ageofonset*1 == 1 || this.statusMiscellaneous.miscellaneous.ageofonset*1 == 0){
					template += "Baby diagnosed at the age of "+this.statusMiscellaneous.miscellaneous.ageofonset + " day. ";
				}else{
					template += "Baby diagnosed at the age of "+ this.statusMiscellaneous.miscellaneous.ageofonset +" days. ";
				}
			}
		}else{
			template += "Baby is still under observation. ";
		}

    if(this.statusMiscellaneous.miscellaneous.disease != "" && this.statusMiscellaneous.miscellaneous.disease != null){
      template += "Disease is " + this.statusMiscellaneous.miscellaneous.disease+".";
    }

		if(this.statusMiscellaneous.miscellaneous.description != "" && this.statusMiscellaneous.miscellaneous.description != null){
			template += "Description is " + this.statusMiscellaneous.miscellaneous.description+".";
		}

		if(this.orderSelectedText != ""){
			if(this.orderSelectedText.indexOf(",")==-1){
				template += "Investigation ordered is " + this.orderSelectedText+".";
			}else{
				template += "Investigation ordered are " + this.orderSelectedText+".";
			}
		}

    if(this.statusMiscellaneous.miscellaneous.medicationStr != null && this.statusMiscellaneous.miscellaneous.medicationStr != ''){
      template += this.statusMiscellaneous.miscellaneous.medicationStr +'. ';
    }

		if(this.statusMiscellaneous.miscellaneous.treatment != "" && this.statusMiscellaneous.miscellaneous.treatment != null){
			template += "Treatment given is " + this.statusMiscellaneous.miscellaneous.treatment+".";
		}

		//for plan to reassess the baby
		var planAddedd = false;
		if(this.statusMiscellaneous.miscellaneous.actionduration!=null &&
				this.statusMiscellaneous.miscellaneous.actionduration!=""
					&& this.statusMiscellaneous.miscellaneous.isactiondurationinhours!=null){
			var planAddedd = true;
			if(this.statusMiscellaneous.miscellaneous.isactiondurationinhours=='mins'){
				if(this.statusMiscellaneous.miscellaneous.actionduration* 1 == 1){
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous.actionduration +" Min "
				}else{
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous.actionduration +" Mins ";
				}
			}else if(this.statusMiscellaneous.miscellaneous.isactiondurationinhours=='hours'){
				if(this.statusMiscellaneous.miscellaneous.actionduration* 1 == 1){
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous.actionduration +" hour "
				}else{
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous.actionduration +" hours ";
				}
			} else {
				if(this.statusMiscellaneous.miscellaneous.actionduration* 1 == 1){
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous.actionduration +" day "
				}else{
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous.actionduration +" days ";
				}
			}
		}

		if(this.statusMiscellaneous.miscellaneous.planOther != null && this.statusMiscellaneous.miscellaneous.planOther != ""){
			if(planAddedd==true){
				template += "and " + this.statusMiscellaneous.miscellaneous.planOther + ". ";
			}else{
				template += this.statusMiscellaneous.miscellaneous.planOther + ". ";
			}
		}else if(planAddedd==true){
			template += ".";
		}

		if(this.statusMiscellaneous.miscellaneous.causeofmiscellaneous != "" && this.statusMiscellaneous.miscellaneous.causeofmiscellaneous != null){
			template += "The cause is  " + this.statusMiscellaneous.miscellaneous.causeofmiscellaneous+".";
		}

		if(this.statusMiscellaneous.miscellaneous.associatedevent!= null && this.statusMiscellaneous.miscellaneous.associatedevent!="") {
			template += " Associated Event is "+ this.statusMiscellaneous.miscellaneous.associatedevent+".";
		}
		this.statusMiscellaneous.miscellaneous.comment = template;

    //Miscellaneous 2
    template = "";
		if(this.statusMiscellaneous.listMiscellaneous2==null || this.statusMiscellaneous.listMiscellaneous2.length<=0 ||
				this.statusMiscellaneous.miscellaneous2.miscellaneousstatus=='inactive'){

			if(this.statusMiscellaneous.miscellaneous2.isageofonsetinhours){
				if(this.statusMiscellaneous.miscellaneous2.ageofonset*1 == 1 || this.statusMiscellaneous.miscellaneous2.ageofonset*1 == 0){
					template += "Baby diagnosed at the age of "+this.statusMiscellaneous.miscellaneous2.ageofonset + " hr. ";
				}else{
					template += "Baby diagnosed at the age of "+this.statusMiscellaneous.miscellaneous2.ageofonset + " hrs. ";
				}
			}else{
				if(this.statusMiscellaneous.miscellaneous2.ageofonset*1 == 1 || this.statusMiscellaneous.miscellaneous2.ageofonset*1 == 0){
					template += "Baby diagnosed at the age of "+this.statusMiscellaneous.miscellaneous2.ageofonset + " day. ";
				}else{
					template += "Baby diagnosed at the age of "+ this.statusMiscellaneous.miscellaneous2.ageofonset +" days. ";
				}
			}
		}else{
			template += "Baby is still under observation. ";
		}

    if(this.statusMiscellaneous.miscellaneous2.disease != "" && this.statusMiscellaneous.miscellaneous2.disease != null){
			template += "Disease is " + this.statusMiscellaneous.miscellaneous2.disease+".";
		}

		if(this.statusMiscellaneous.miscellaneous2.description != "" && this.statusMiscellaneous.miscellaneous2.description != null){
			template += "Description is " + this.statusMiscellaneous.miscellaneous2.description+".";
		}

		if(this.orderSelectedTextForMiscellaneous2 != ""){
			if(this.orderSelectedTextForMiscellaneous2.indexOf(",")==-1){
				template += "Investigation ordered is " + this.orderSelectedTextForMiscellaneous2+".";
			}else{
				template += "Investigation ordered are " + this.orderSelectedTextForMiscellaneous2+".";
			}
		}

    if(this.statusMiscellaneous.miscellaneous2.medicationStr != null && this.statusMiscellaneous.miscellaneous2.medicationStr != ''){
      template += this.statusMiscellaneous.miscellaneous2.medicationStr +'. ';
    }

		if(this.statusMiscellaneous.miscellaneous2.treatment != "" && this.statusMiscellaneous.miscellaneous2.treatment != null){
			template += "Treatment given is " + this.statusMiscellaneous.miscellaneous2.treatment+".";
		}

		//for plan to reassess the baby
		var planAddedd = false;
		if(this.statusMiscellaneous.miscellaneous2.actionduration!=null &&
				this.statusMiscellaneous.miscellaneous2.actionduration!=""
					&& this.statusMiscellaneous.miscellaneous2.isactiondurationinhours!=null){
			var planAddedd = true;
			if(this.statusMiscellaneous.miscellaneous2.isactiondurationinhours=='mins'){
				if(this.statusMiscellaneous.miscellaneous2.actionduration* 1 == 1){
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous2.actionduration +" Min "
				}else{
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous2.actionduration +" Mins ";
				}
			}else if(this.statusMiscellaneous.miscellaneous2.isactiondurationinhours=='hours'){
				if(this.statusMiscellaneous.miscellaneous2.actionduration* 1 == 1){
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous2.actionduration +" hour "
				}else{
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous2.actionduration +" hours ";
				}
			} else {
				if(this.statusMiscellaneous.miscellaneous2.actionduration* 1 == 1){
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous2.actionduration +" day "
				}else{
					template += "Plan is to reassess the baby after "+this.statusMiscellaneous.miscellaneous2.actionduration +" days ";
				}
			}
		}

		if(this.statusMiscellaneous.miscellaneous2.planOther != null && this.statusMiscellaneous.miscellaneous2.planOther != ""){
			if(planAddedd==true){
				template += "and " + this.statusMiscellaneous.miscellaneous2.planOther + ". ";
			}else{
				template += this.statusMiscellaneous.miscellaneous2.planOther + ". ";
			}
		}else if(planAddedd==true){
			template += ".";
		}

		if(this.statusMiscellaneous.miscellaneous2.causeofmiscellaneous != "" && this.statusMiscellaneous.miscellaneous2.causeofmiscellaneous != null){
			template += "The cause is  " + this.statusMiscellaneous.miscellaneous2.causeofmiscellaneous+".";
		}

		if(this.statusMiscellaneous.miscellaneous2.associatedevent!= null && this.statusMiscellaneous.miscellaneous2.associatedevent!="") {
			template += " Associated Event is "+ this.statusMiscellaneous.miscellaneous2.associatedevent+".";
		}
		this.statusMiscellaneous.miscellaneous2.comment = template;

	};

  printData(fromDateTable : Date, toDateTable : Date) {
    this.fromDateTableNull = false;
    this.toDateTableNull = false;
    this.fromToTableError = false;
    this.printDataObj.dateFrom = fromDateTable;
    this.printDataObj.dateTo= toDateTable;
    this.printDataObj.uhid = this.childObject.uhid;
    this.printDataObj.whichTab = "Miscellaneous Assessment";
    if(fromDateTable == null) {
      this.fromDateTableNull = true;
    } else if(toDateTable == null) {
      this.toDateTableNull = true;
    } else if(fromDateTable >= toDateTable) {
      this.fromToTableError = true;
    } else {
      var data = [];
      for(var i=0; i < this.statusMiscellaneous.listMiscellaneous.length; i++) {
        var item = this.statusMiscellaneous.listMiscellaneous[i];
          if(item.creationtime >= fromDateTable.getTime() && item.creationtime <= toDateTable.getTime()) {
            var obj = Object.assign({}, item);
            data.push(obj);
        }
      }
      for(var i=0; i < this.statusMiscellaneous.listMiscellaneous2.length; i++) {
        var item = this.statusMiscellaneous.listMiscellaneous2[i];
          if(item.creationtime >= fromDateTable.getTime() && item.creationtime <= toDateTable.getTime()) {
            var obj = Object.assign({}, item);
            data.push(obj);
        }
      }
      this.printDataObj.printData = data;
    }
    localStorage.setItem('printDataObj', JSON.stringify(this.printDataObj));
    this.router.navigateByUrl('/jaundice/jaundice-print');
  }

  ngOnInit() {
  }

}
