import { Component, OnInit } from '@angular/core';
import { Chart } from 'angular-highcharts';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';
import { RootObject } from './jaundiceMainObj';
import {BindResponse} from './bindResponse';
import {PrintDataObj} from './printDataObj';
import {JaundiceTempObj} from './jaundiceTempObj';
import { AppComponent } from '../app.component';
import { ScoreBindObj } from './scoreBindObj';
@Component({
  selector: 'assessment-sheet-jaundice',
  templateUrl: './assessment-sheet-jaundice.component.html',
  styleUrls: ['./assessment-sheet-jaundice.component.css']
})
export class AssessmentSheetJaundiceComponent implements OnInit {
  isDisable:boolean;
  enableBox:boolean;
  checked:boolean;
  chartBhutani : Chart;
  selectAll : any;
  phototherapyGraph : boolean;
  chartNice : Chart;
  chartJaundiceHour : Chart;
  chartJaundiceDay : Chart;
  apiData : APIurl;
  http: Http;
  router : Router;
  childObject : Array<any>;
  statusJaundice : RootObject;
  //loggedInUserObj : Array<any>;
  investOrderSelected : Array<any>;
  investOrderNotOrdered : Array<any>;
  jaundice : boolean;
  isJaundiceGraph : boolean;
  jaundiceId : any;
  graphData : any;
//for bhutani popup
  niceLowGraphArr : Array<any>;
  niceHighGraphArr : Array<any>;
  maxNiceValue : number;
  maxBhutaniValue : number;

  TableOrderJaundiceFlag : boolean;
  maxDate : Date;
  vanishSubmitResponseVariable : boolean;
  dateBirth : Date;
  hoursList : Array<any>;
  diffTime : number;
  weightLoss : number;
  graphCutOfScore :string;
  orderSelectedText :string;
  causeOfJaundiceSelected :string;
  pastTreatmentActionValueStr :string;
  photoStartDisabled : boolean;
  photoStopDisabled : boolean;
  photoContinueDisabled : boolean;
  exTransPlannedDisabled : boolean;
  exTransDoneDisabled : boolean;
  jaundicehours : string;
  jaundiceminutes : string;
  jaundiceTreatmentStatus : any;
  treatmentVisiblePast : boolean;
  tempObjectPlan : any;
  investigationsOrdered : any;
  jaundiceTestObjects : Array<any>;
  ageOnsetPreviousExceed : boolean;
  isClinicalVisible : boolean;
  GraphViewToggle : boolean;
  isActionVisible : boolean;
  isPlanVisible : boolean;
  isScoreVisible : boolean;
  isProgressNotesVisible :boolean;
  rdsHourFlag : boolean;
  ageAtAssessmentHourFlag : boolean;
  bindStructure : Array<any>;
  statusScore : any;
  localStatusScore : any;
  bindscoreid : string;
  totalBindScore : string;
  saveBindScore :boolean;
  bindResponse : BindResponse;
  symptomsArr : any;
  printDataObj : PrintDataObj;
  jundiceTempObject : JaundiceTempObj;
  tabContent : string;
  responseType : string;
  phototherapyGraphType : string;
  scoreBindObj : ScoreBindObj;
  warningMessage : string;
  fromDateTableNull : boolean;
  toDateTableNull : boolean;
  fromToTableError : boolean;
  minDate : string;
  isLoaderVisible : boolean;
  loaderContent : string;
  pastPrescriptionList : any;
  loggedUser : string;
  currentDate : Date;
  constructor(http: Http, router: Router) {
  	this.selectAll=false;
  	this.enableBox=false;
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.scoreBindObj = new ScoreBindObj();
    this.statusJaundice = new RootObject();
    this.currentDate = new Date();
  	//this.loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
    this.investOrderNotOrdered = [];
  	this.investOrderSelected = [];
    this.jaundice = true;
  	this.isJaundiceGraph = true;
  	this.jaundiceId;
  	this.graphData = {};
    this.selectAll = null;
//	for bhutani popup
  	this.niceLowGraphArr = [];
  	this.niceHighGraphArr = [];
    this.jaundiceTestObjects =[];
  	this.maxNiceValue = 408;
  	this.maxBhutaniValue = 168;
    this.TableOrderJaundiceFlag = false;
    this.dateBirth = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob);
    this.isClinicalVisible = true;
  	this.GraphViewToggle = false;
    this.isActionVisible = false;
    this.isPlanVisible = false;
    this.isScoreVisible = false;
    this.isProgressNotesVisible = true;
    this.rdsHourFlag = false;
    this.ageAtAssessmentHourFlag = false;
    this.hoursList = [];

		for(var i=1;i<=12;++i){
			if(i<10)
				this.hoursList.push("0"+i.toString());
			else
				this.hoursList.push(i.toString());
		}
    this.warningMessage = "";
    this.graphCutOfScore = "";
  	this.orderSelectedText = "";
  	this.causeOfJaundiceSelected = "";
  	this.pastTreatmentActionValueStr = "";
  	this.exTransPlannedDisabled = false;
  	this.exTransDoneDisabled = false;

  	this.jaundicehours = "00";
  	this.jaundiceminutes = "00";
      this.weightLoss = null;
  	this.jaundiceTreatmentStatus = {};
  	this.treatmentVisiblePast = false;
  	this.tempObjectPlan = {};
    this.ageOnsetPreviousExceed = false;

    this.bindStructure = [];
  	this.statusScore = {
  			"mentalstatusscore": null,
  			"muscletonescore": null,
  			"crypatternscore": null,
  			"oculomotorscore": null
  	};
  	this.localStatusScore = {
  			"mentalstatusscore": null,
  			"muscletonescore": null,
  			"crypatternscore": null,
  			"oculomotorscore": null
  	};
   	this.bindscoreid ="";
   	this.totalBindScore="";
   	this.saveBindScore = false;
    this.bindResponse = new BindResponse();
    this.symptomsArr = [];
    this.printDataObj = new PrintDataObj;
    this.jundiceTempObject = new JaundiceTempObj;
    this.tabContent = "bind";
    this.photoStartDisabled=false;
    this.photoStopDisabled=true;
    this.photoContinueDisabled=true;
    this.isDisable=true;
    this.checked=false;
    this.pastPrescriptionList = [];
    this.init();
  }

  init(){
	 this.maxDate = new Date();
     var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
     var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
     var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
     if(tempHr == 12 && tempMer == 'AM'){
        tempHr = '00';
     }else if(tempHr != 12 && tempMer == 'PM'){
        tempHr = parseInt(tempHr) + 12;
     }
     var tempFullTime = tempHr +':' +tempMin+':00';
     this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
     var tempPrevTime = this.minDate.slice(16,24);
     this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
     this.setDataJaundice();
		 this.TimeCtrl();
		//this.getgraphData();
  }

  isEmpty(object : any) : boolean {
    if (object == null || object == '' || object == 'null' || object.length == 0) {
      return true;
    }
    else {
      return false;
    }
  };

  round(value : any, precision : any) {
    var multiplier = Math.pow(10, precision);
    return Math.round(value * multiplier) / multiplier;
  }

  calculateWeightLoss = function(){
    if(!this.isEmpty(this.childObject.todayWeight) && !this.isEmpty(this.childObject.birthWeight)){
      this.weightLoss = ((this.childObject.birthWeight - this.childObject.todayWeight) / this.childObject.birthWeight ) * 100;
      this.weightLoss = this.round(this.weightLoss, 2);
    }
  }

  TimeCtrl = function() {
		this.clock = "loading clock..."; // initialise the time variable
		this.tickInterval = 1000 // ms
		var tick = function() {
			this.clock = Date.now() // get the current time
      setTimeout(() =>
        tick , this.tickInterval);
//			$timeout(tick, this.tickInterval); // reset the timer
		};

		// Start the timer
    setTimeout(() =>
    tick , this.tickInterval);
	}

  setDataJaundice = function () {

    try
    {
      this.http.post(this.apiData.setDataJaundice+ this.childObject.uhid + "/test",
        this.cmData).subscribe(res => {
          this.statusJaundice =res.json();
          console.log(this.prescriptionList);

          this.statusJaundice.jaundice.episodeNumber = 1;
					this.statusJaundice.jaundice.episodeNumber = this.indcreaseEpisodeNumber(this.statusJaundice.listJaundice);
					this.getBindStructure(true);
			          setTimeout(() => {
			           this.vanishSubmitResponseVariable = false;
			          }, 3000);
        			this.jaundiceTempObj.printFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
    				this.jaundiceTempObj.printTo = new Date();
					var commaflag = false;

          this.pastPrescriptionList = [];
          if(this.statusJaundice.prescriptionList != null && this.statusJaundice.prescriptionList.length > 0){
            for(var i = 0; i < this.statusJaundice.prescriptionList.length; i++){
              if(this.statusJaundice.prescriptionList[i].eventname == 'Jaundice'){
                var startDate = new Date(this.statusJaundice.prescriptionList[i].medicineOrderDate);
                var medContiuationDayCount = Math.abs(Math.ceil((this.currentDate.getTime() - startDate.getTime())/(1000*60*60*24)));
                if(medContiuationDayCount == 0){
                  medContiuationDayCount = 1;
                }
                this.statusJaundice.prescriptionList[i].medicineDay = medContiuationDayCount;
                this.pastPrescriptionList.push(this.statusJaundice.prescriptionList[i]);
              }
            }
            this.statusJaundice.prescriptionList = [];
          }

					this.pastTreatmentActionValueStr = "";

					if(!(this.statusJaundice.listJaundice == null || this.statusJaundice.listJaundice[0] == null)){

						if(this.statusJaundice.listJaundice[0].phototherapyvalue != null){
							commaflag = true;
							this.pastTreatmentActionValueStr += "Phototherapy: " + this.statusJaundice.listJaundice[0].phototherapyvalue;

							if(this.statusJaundice.listJaundice[0].phototherapyvalue == "Stop") {
								this.photoStartDisabled = false;
								this.photoStopDisabled = true;
								this.photoContinueDisabled = true;
							} else if(this.statusJaundice.listJaundice[0].phototherapyvalue == "Start"){
								this.photoStartDisabled = true;
								this.photoStopDisabled = false;
								this.photoContinueDisabled = false;
							} else {
								this.photoStartDisabled = true;
								this.photoStopDisabled = false;
								this.photoContinueDisabled = false;
							}
						}
						if(this.statusJaundice.listJaundice[0].exchangetrans != null){
							if(commaflag){
								this.pastTreatmentActionValueStr += ", ";
							}
							if(this.statusJaundice.listJaundice[0].exchangetrans == true){
								this.pastTreatmentActionValueStr += "Exchange Transfusion: Planned";
								this.exTransPlannedDisabled = true;
								this.exTransDoneDisabled = false;
							} else {
								this.pastTreatmentActionValueStr += "Exchange Transfusion: Done";
								this.exTransPlannedDisabled = false;
								this.exTransDoneDisabled = true;
							}
						}
						if(this.statusJaundice.listJaundice[0].ivigvalue != null){
							if(commaflag){
								this.pastTreatmentActionValueStr += ", ";
							}
							this.pastTreatmentActionValueStr += " Intravenous Immunoglobin (IVIG) : " + this.statusJaundice.listJaundice[0].ivigvalue;
						}

						if(this.statusJaundice.listJaundice[0].medicationStr != null && this.statusJaundice.listJaundice[0].medicationStr != '') {
							if(commaflag){
								this.pastTreatmentActionValueStr += ", ";
							}
							commaflag = true;
							this.pastTreatmentActionValueStr += this.statusJaundice.listJaundice[0].medicationStr;
						}

						this.jaundiceTempObj.pastRiskFactorsStr = "";
						var lastJaundice = this.statusJaundice.listJaundice[0];
						if(lastJaundice.riskfactor!=null && lastJaundice.riskfactor!=''){
							var pastRiskFactoryList = lastJaundice.riskfactor.replace(" ","").replace("[","").replace("]","").split(",");
							if(pastRiskFactoryList !=null && pastRiskFactoryList.length>0 ){
								for(var index=0;index<pastRiskFactoryList.length;index++){
									for(var orderDrop=0;orderDrop<this.statusJaundice.dropDowns.riskFactor.length;orderDrop++){
										if(pastRiskFactoryList[index] == this.statusJaundice.dropDowns.riskFactor[orderDrop].key){
											if(this.jaundiceTempObj.pastRiskFactorsStr==""){
												this.jaundiceTempObj.pastRiskFactorsStr =  this.statusJaundice.dropDowns.riskFactor[orderDrop].value;
											}else{
												this.jaundiceTempObj.pastRiskFactorsStr = this.jaundiceTempObj.pastRiskFactorsStr + this.statusJaundice.dropDowns.riskFactor[orderDrop].value;
											}
										}
									}
								}
							}
						}

						this.jaundiceTempObj.pastCauseOfJaundice = "";
						if(lastJaundice.causeofjaundice!=null && lastJaundice.causeofjaundice!=''){
							var pastCauseOfJaundiceList = lastJaundice.causeofjaundice.replace(" ","").replace("[","").replace("]","").split(",");
							if(pastCauseOfJaundiceList !=null && pastCauseOfJaundiceList.length>0 ){
								for(var index=0;index<pastCauseOfJaundiceList.length;index++){
									for(var orderDrop=0;orderDrop<this.statusJaundice.dropDowns.causeOfJaundice.length;orderDrop++){
										if(pastCauseOfJaundiceList[index] == this.statusJaundice.dropDowns.causeOfJaundice[orderDrop].key){
											if(this.jaundiceTempObj.pastCauseOfJaundice==""){
												this.jaundiceTempObj.pastCauseOfJaundice =  this.statusJaundice.dropDowns.causeOfJaundice[orderDrop].value;
											}else{
												this.jaundiceTempObj.pastCauseOfJaundice = this.jaundiceTempObj.pastCauseOfJaundice +", " + this.statusJaundice.dropDowns.causeOfJaundice[orderDrop].value;
											}
										}
									}
								}
							}
						}
						this.whichNumberDoseIVIG = 1;
						if(this.statusJaundice.listJaundice!=null){

							for(var i=0; i < this.statusJaundice.listJaundice.length; ++i)
							{
								if(this.statusJaundice.listJaundice[i].ivigvalue!=null)
									this.whichNumberDoseIVIG +=1;

							}
						}

					}

					for(var index = 0; index<this.statusJaundice.listJaundice.length;index++){
						var utcDate = this.statusJaundice.listJaundice[index].creationtime;
						var localDate = new Date(utcDate);
						this.statusJaundice.listJaundice[index].creationtime = localDate;

					}

					var date = new Date();
					this.formatAMPM(date);
					if(this.statusJaundice.listJaundice!=null && this.statusJaundice.listJaundice.length>0 && this.statusJaundice.listJaundice[0].jaundicestatus=='Yes'){
						this.statusJaundice.jaundice.ageofonset = this.statusJaundice.listJaundice[0].ageofonset;
						this.statusJaundice.jaundice.ageinhoursdays = this.statusJaundice.listJaundice[0].ageinhoursdays;
					}
					var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromPrescription'));
					if(isComingFromPrescription!=null){
						this.statusJaundice = JSON.parse(localStorage.getItem('currentJaundiceSystemObj'));
						this.jaundiceTempObj = JSON.parse(localStorage.getItem('currentJaundiceTempObj'));
						this.statusJaundice.prescriptionList =  JSON.parse(localStorage.getItem('prescriptionList'));

            var medStr = "";
            var medStartStr = "";
            var medStopStr = "";
            var medContStr = "";
            var medRevStr = "";

            for(var index=0; index < this.statusJaundice.prescriptionList.length; index++) {
              var medicine = this.statusJaundice.prescriptionList[index];
              var route = null;
              if(medicine.route == 'IV' || medicine.route == 'IM'){
                route = " Inj. "
              }else if(medicine.route == 'PO'){
                route = " Syrup "
              }else{
                route = "";
              }
              if(medicine.isContinueMedication == true){
                medContStr += ", " + route + medicine.medicinename;
              }else if(medicine.isEdit == true){
                medRevStr += ", " + route + medicine.medicinename;
              }else if(medicine.isactive == false){
                medStopStr += ", " + route + medicine.medicinename;
              }else if(medicine.refBabyPresid == null){
                medStartStr += ", " + route + medicine.medicinename;
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

            if(medRevStr != '') {
              if(medStr == '') {
                medStr += "Revised: " + medRevStr.substring(2, medRevStr.length);
              } else {
                medStr += ", Revised: " + medRevStr.substring(2, medRevStr.length);
              }
            }

            if(medContStr != '') {
              if(medStr == '') {
                medStr += "Continued: " + medContStr.substring(2, medContStr.length);
              } else {
                medStr += ", Continued: " + medContStr.substring(2, medContStr.length);
              }
            }

						this.statusJaundice.jaundice.medicationStr = medStr;
						this.isActionVisible = true;
						if(this.statusJaundice.listJaundice.length > 0 && this.statusJaundice.listJaundice[0].actiontype!=null && this.statusJaundice.listJaundice[0].actiontype!='') {
							this.jundiceTempObject.jaundiceTreatmentStatus = 'change';
						}

						localStorage.removeItem('isComingFromPrescription');
						localStorage.removeItem('prescriptionList');
						localStorage.removeItem('currentJaundiceTempObj');
						localStorage.removeItem('currentJaundiceSystemObj');
					}

					if(localStorage.getItem('admissionform') != null){
						this.statusJaundice.jaundice.jaundicestatus = 'Yes';
						localStorage.setItem('redirectBackToAdmission',JSON.stringify(true));
						localStorage.removeItem('admissionform');
					}
					this.creatingProgressNotesTemplate();
          this.calculateWeightLoss();
					var date = new Date();
				  	this.statusJaundice.jaundice.assessmentTime = new Date();
        },
        err => {
          console.log("Error occured.")
        }

      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
//			end of above function
			this.jaundiceTempObj = {};

			this.formatAMPM = function(date) {

				var hours = date.getHours();
				var minutes = date.getMinutes();
				var ampm = hours >= 12 ? 'PM' : 'AM';
				hours = hours % 12;
				hours = hours ? hours : 12; // the hour '0' should be '12'
				minutes = minutes < 10 ? '0'+minutes : minutes;

				this.jaundiceTempObj.timeOfAssessment = hours+":"+minutes;
				// this.jaundiceTempObj.minutes = minutes;

				if(ampm=='PM'){
					this.jaundiceTempObj.meridian = false;
				}else if(ampm=='AM'){
					this.jaundiceTempObj.meridian = true;
				}
				// this.jaundiceTempObj.meridian = ampm;

				this.jaundiceTempObj.timeOfAssessmentPrev = hours;
				// this.jaundiceTempObj.minutesPrev = minutes;
				// this.jaundiceTempObj.meridianPrev = ampm;
				this.jaundiceTempObj.meridianPrev = this.jaundiceTempObj.meridian;
			}


			this.calAgeAtOnsetOnChange = function(){

				this.jaundiceTempObj.timeOfAssessment;
				this.jaundiceTempObj.meridian;
				this.statusJaundice.jaundice.dateofbirth;
				var todayDate = new Date();
				if(this.jaundiceTempObj.timeOfAssessment!=null && this.jaundiceTempObj.timeOfAssessment!=''){
					var timeArray = this.jaundiceTempObj.timeOfAssessment.split(":");
					todayDate.setHours(timeArray[0]);
					todayDate.setMinutes(timeArray[1]);

				}




				this.creatingProgressNotesTemplate();
			}
    //			below function is used to calculate the phototherapy of fom the TcB value
			this.calculatePhototherapy = function(){

				if(this.statusJaundice.jaundice.gestation!=null && this.statusJaundice.jaundice.gestation!=""){

					if(this.statusJaundice.jaundice.gestation*1<35){
						this.whichGraph = "Nice Chart";
						if(this.statusJaundice.jaundice.ageatassesment!=null && this.statusJaundice.jaundice.ageatassesment!=""){

							var ageOfAssessment = this.statusJaundice.jaundice.ageatassesment * 1;
							if(!this.statusJaundice.jaundice.isageofassesmentinhours) {
								ageOfAssessment *= 24;
							}

							var hoursMappingWithGraphValue =null;
							if(ageOfAssessment > 408){
								hoursMappingWithGraphValue = Math.round((408/6))*6;
							}else{
								hoursMappingWithGraphValue = Math.round(ageOfAssessment/6)*6;
							}

							var lowHighValueWeek = this.statusJaundice.jaundicePhototherapyGraph.niceGraph[this.statusJaundice.jaundice.gestation+" wk"];
							if(lowHighValueWeek!=null){
								this.jaundiceTempObj.chartMessage = "";
								var lowHighValueObj = lowHighValueWeek.plotData["0"][hoursMappingWithGraphValue+" hr"];

								if(this.statusJaundice.jaundice.tbcvalue!=null && this.statusJaundice.jaundice.tbcvalue!=""){
									if(this.statusJaundice.jaundice.tbcvalue*1<lowHighValueObj.high*1){
										this.whatRecommended = "Phototherapy";
										if(this.statusJaundice.jaundice.tbcvalue*1<lowHighValueObj.low*1){
											this.whatRecommended = "Phototherapy Not Required";
											this.statusJaundice.jaundice.isphotobelowthreshold = "belowThreshold";
										}else if(this.statusJaundice.jaundice.tbcvalue*1 == lowHighValueObj.low*1){
											this.statusJaundice.jaundice.isphotobelowthreshold = "threshold";
										}else{
											this.statusJaundice.jaundice.isphotobelowthreshold = "aboveThreshold";
										}

									}else{
										this.whatRecommended = "Exchange Transfusion";
										this.statusJaundice.jaundice.isphotobelowthreshold = "aboveThreshold";;
									}

									this.graphCutOfScore = lowHighValueObj.low;
								}else{
									this.statusJaundice.jaundice.isphotobelowthreshold = null;
								}
							}else{
								this.jaundiceTempObj.chartMessage = "NICE chart for the gestaion week "+this.statusJaundice.jaundice.gestation+" is not available";
							}
						}
					}else{// bhutani chart..
						this.whichGraph = "Bhutani Chart";

						var calculatedRiskFactors = ["RSK00001", "RSK00002","RSK00003","RSK00004","RSK00005","RSK00006","RSK00007","RSK00008"];
						var isCalculationEnabled = false;

						if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
							for(var i=0; i<calculatedRiskFactors.length;i++){
								if (this.statusJaundice.jaundice.riskFactorList.indexOf(calculatedRiskFactors[i]) >= 0) {
									isCalculationEnabled = true;
									break;
								}
							}
						}

						var ageOfAssessment = this.statusJaundice.jaundice.ageatassesment * 1;
						if(!this.statusJaundice.jaundice.isageofassesmentinhours) {
							ageOfAssessment *= 24;
						}

						let hoursMappingWithGraphValue : any= Math.round(ageOfAssessment/12)*12;
						if(hoursMappingWithGraphValue*1 > 168){
							hoursMappingWithGraphValue = 168;
						}
						var low =null;
						var high = null;
						if(this.statusJaundice.jaundice.gestation*1>=38){// two case
							if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
								if(isCalculationEnabled == true){
									high  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].high;
									low  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].low;
								}
								else{
									high  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].high;
									low  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].low;
								}

							}else{
								high  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].high;
								low  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].low;
							}
						}else if(this.statusJaundice.jaundice.gestation*1<=37){// two
							if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
								if(isCalculationEnabled == true){
									high  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph
									.highRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].high;
									low  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph
									.highRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].low;
								}
								else{
									high  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph
									.midRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].high;
									low  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph
									.midRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].low;
								}

							}else{
								// mid risk,...

								high  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph
								.midRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].high;
								low  = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph
								.midRisk.plotData["0"][hoursMappingWithGraphValue+" hr"].low;
							}
						}

						if(this.statusJaundice.jaundice.tbcvalue!=null && this.statusJaundice.jaundice.tbcvalue!=""){
							if(this.statusJaundice.jaundice.tbcvalue*1<high*1){
								this.whatRecommended = "Phototherapy";
								if(this.statusJaundice.jaundice.tbcvalue*1<low*1){
									this.whatRecommended = "Phototherapy Not Required";
									this.statusJaundice.jaundice.isphotobelowthreshold = "belowThreshold"; // phototherapy
									// low
									// thershold
								}else if(this.statusJaundice.jaundice.tbcvalue*1==low*1){

									this.statusJaundice.jaundice.isphotobelowthreshold = "threshold"; // phototherapy
									// low
									// thershold
								}else{
									this.statusJaundice.jaundice.isphotobelowthreshold = "aboveThreshold"; // phtotherapy
									// high
									// threshold
								}
							}else{
								this.whatRecommended = "Exchange Transfusion";
								this.statusJaundice.jaundice.isphotobelowthreshold = "aboveThreshold";
							}

							this.graphCutOfScore = low;
						}else{

							this.statusJaundice.jaundice.isphotobelowthreshold = null;
						}

					}
					this.creatingProgressNotesTemplate();
				}else{
					this.statusJaundice.jaundice.isphotobelowthreshold = null;
          this.warningMessage = "Please enter baby gestation.";
          this.showWarningPopUp();
					//this.showModal("Please enter baby gestation.","info");
				}

			}

			this.navi= function(map_value){
				console.log(map_value);
				var a = map_value;
				for(var i=0;i< this.statusJaundice.listJaundice.length;i++){
					if(this.statusJaundice.listJaundice[i].sajaundiceid == map_value){
						this.jaundiceId = i;
						this.statusJaundice.listJaundice[this.jaundiceId].edit = true;
						this.statusJaundice.listJaundice[this.jaundiceId].ageOnset = parseInt(this.statusJaundice.listJaundice[this.jaundiceId].ageOnset);
						let jaundiceDataCopy = Object.assign({},this.statusJaundice.listJaundice[this.jaundiceId]);
						this.statusJaundice.jaundice = jaundiceDataCopy;
						if(jaundiceDataCopy.assessmentTime != null && jaundiceDataCopy.assessmentTime != undefined && jaundiceDataCopy.assessmentTime !=""){
							this.statusJaundice.jaundice.assessmentTime = new Date(this.statusJaundice.jaundice.assessmentTime);
						//	this.statusJaundice.jaundice.assessmentTime = dateService.formatter(this.statusJaundice.jaundice.assessmentTime, "gmt", "default");
						}
						this.isDisable=false;
						this.statusJaundice.jaundice.isNewEntry = false;
						if(this.statusJaundice.jaundice.tbcvalue != null){
							this.statusJaundice.jaundice.tbcvalue = parseFloat(this.statusJaundice.jaundice.tbcvalue);
						}else{}
						this.getBindStructure(false);
					}
				}

				if(this.statusJaundice.listJaundice[this.jaundiceId].riskFactorList !=null){
					for(var orderInv=0;orderInv<this.statusJaundice.listJaundice[this.jaundiceId].riskFactorList.length;orderInv++){
						for(var orderDrop=0;orderDrop<this.statusJaundice.dropDowns.riskFactor.length;orderDrop++){
							if(this.statusJaundice.listJaundice[this.jaundiceId].riskFactorList[orderInv] == this.statusJaundice.dropDowns.riskFactor[orderDrop].key){
								this.statusJaundice.dropDowns.riskFactor[orderDrop].flag = true;
							}
						}
					}
				}


				if(this.statusJaundice.listJaundice[this.jaundiceId].causeofjaundiceList != null){
					for(var jaundice=0;jaundice<this.statusJaundice.listJaundice[this.jaundiceId].causeofjaundiceList.length;jaundice++){
						for(var jaundIndex=0;jaundIndex<this.statusJaundice.dropDowns.causeOfJaundice.length;jaundIndex++){
							if(this.statusJaundice.listJaundice[this.jaundiceId].causeofjaundiceList[jaundice] == this.statusJaundice.dropDowns.causeOfJaundice[jaundIndex].key){
								this.statusJaundice.dropDowns.causeOfJaundice[jaundIndex].flag = true;
							}
						}
					}
				}
				if(this.statusJaundice.listJaundice[this.jaundiceId].actiontypeList != null){
					for(var action=0;action<this.statusJaundice.listJaundice[this.jaundiceId].actiontypeList.length;action++){
						for(var actionDrop=0;actionDrop<this.statusJaundice.dropDowns.treatmentAction.length;actionDrop++){
							if(this.statusJaundice.listJaundice[this.jaundiceId].actiontypeList[action] == this.statusJaundice.dropDowns.treatmentAction[actionDrop].key){
								this.statusJaundice.dropDowns.treatmentAction[actionDrop].flag = true;
							}
						}
					}
				}
				if(this.statusJaundice.listJaundice[this.jaundiceId].orderinvestigationList !=null){
					for(var orderInv=0;orderInv<this.statusJaundice.listJaundice[this.jaundiceId].orderinvestigationList.length;orderInv++){
						for(var orderDrop=0;orderDrop<this.statusJaundice.dropDowns.orderInvestigation.length;orderDrop++){
							if(this.statusJaundice.listJaundice[this.jaundiceId].orderinvestigationList[orderInv] == this.statusJaundice.dropDowns.orderInvestigation[orderDrop].key){
								this.statusJaundice.dropDowns.orderInvestigation[orderDrop].flag = true;
							}
						}
					}
				}
				this.getNiceGraph();
				this.getBhutaniGraph();
				this.creatingProgressNotesTemplate();
			};

			this.orderInvestigationData = function(){
				if(this.statusJaundice.jaundice.isinvestigationodered != true){
					this.statusJaundice.jaundice.isinvestigationodered = true;
          this.warningMessage = "Order placed successfully";
          this.showWarningPopUp();
					//this.showModal("Your order has already been placed", "success");
				}else{
          this.warningMessage = "Your order has already been placed";
          this.showWarningPopUp();
					//this.showModal("Your order has already been placed", "info");
					this.statusJaundice.jaundice.isinvestigationodered = true;
				}
			}
			this.validateJaundice = function(){
				var isSuccessful = true;
				if(this.statusJaundice.jaundice.jaundicestatus!=null && this.statusJaundice.jaundice.jaundicestatus=="Yes") {
					if(this.statusJaundice.jaundice.jaundiceDiagnosisBy=='LabTest' && (this.statusJaundice.jaundice.tbcvalue==null || this.statusJaundice.jaundice.tbcvalue=="")){
						this.vanishSubmitResponseVariable = true
						this.responseType = "failure";
						this.responseMessage = "Please enter TcB value";
            setTimeout(() => {
             this.vanishSubmitResponseVariable = false;
            }, 3000);
						isSuccessful = false;
					}else if(this.statusJaundice.jaundice.actiontype == "Reassess"){
						if(this.statusJaundice.jaundice.actionduration == null || this.statusJaundice.jaundice.actionduration == ""){
							this.vanishSubmitResponseVariable = true
							this.responseType = "failure";
							this.responseMessage = "Please Fill Reassess Time Properly";
              setTimeout(() => {
               this.vanishSubmitResponseVariable = false;
              }, 3000);
							isSuccessful = false;
						}
						else{
							if(this.statusJaundice.jaundice.isactiondurationinhours == null || this.statusJaundice.jaundice.isactiondurationinhours == ""){
								this.vanishSubmitResponseVariable = true
								this.responseType = "failure";
								this.responseMessage = "Please Fill Reassess Time Properly";
                setTimeout(() => {
                 this.vanishSubmitResponseVariable = false;
                }, 3000);
								isSuccessful = false;
							}
						}
					} else if(this.statusJaundice.jaundice.actiontypeList != null) {
						if(this.statusJaundice.jaundice.actiontypeList.includes('TRE001') && this.statusJaundice.jaundice.phototherapyvalue == null) {
              this.warningMessage = "Please select Phototherapy Action";
              this.showWarningPopUp();
              //this.showModal("Please select Phototherapy Action", "failure");
							isSuccessful = false;
						} else if(this.statusJaundice.jaundice.actiontypeList.includes('TRE002') && this.statusJaundice.jaundice.exchangetrans == null) {
              this.warningMessage = "Please select Exchange Transfusion Action";
              this.showWarningPopUp();
              //this.showModal("Please select Exchange Transfusion Action", "failure");
							isSuccessful = false;
						} else if(this.statusJaundice.jaundice.actiontypeList.includes('TRE003') && this.statusJaundice.jaundice.ivigvalue == null) {
              this.warningMessage = "Please fill IVIG Value";
              this.showWarningPopUp();
              //this.showModal("Please fill IVIG Value", "failure");
							isSuccessful = false;
						}
					}

				}

				if (isSuccessful == true){
					if (this.hourDayDiffAgeAtAssessment == null || this.hourDayDiffAgeAtAssessment == undefined){
						this.hourDayDiffAgeAtAssessment = 0;
					}

					if(this.statusJaundice.jaundice.isageofonsetinhours == this.statusJaundice.jaundice.isageofassesmentinhours){
						if(this.statusJaundice.jaundice.ageatassesment < this.statusJaundice.jaundice.ageofonset){
							$("#ageatassessmentvalidinputJaundice").css("display","block");
							isSuccessful = false;
							this.isLoaderVisible = false;
						}
						else{
							$("#ageatassessmentvalidinputJaundice").css("display","none");
							isSuccessful = true;
						}
					}
					else if(this.statusJaundice.jaundice.isageofonsetinhours == false && this.statusJaundice.jaundice.isageofassesmentinhours == true){
						var ageAtAssessment = Object.assign({},this.statusJaundice.jaundice.ageatassesment);
						ageAtAssessment -= this.hourDayDiffAgeAtAssessment;
						ageAtAssessment /= 24;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.statusJaundice.jaundice.ageofonset){
							$("#ageatassessmentvalidinputJaundice").css("display","block");
							isSuccessful = false;
							this.isLoaderVisible = false;
						}
						else{
							$("#ageatassessmentvalidinputJaundice").css("display","none");
							isSuccessful = true;
						}
					}
					else if(this.statusJaundice.jaundice.isageofonsetinhours == true && this.statusJaundice.jaundice.isageofassesmentinhours == false){
						var ageAtAssessment = Object.assign({},this.statusJaundice.jaundice.ageatassesment);
						ageAtAssessment *= 24;
						ageAtAssessment += this.hourDayDiffAgeAtAssessment;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.statusJaundice.jaundice.ageofonset){
							$("#ageatassessmentvalidinputJaundice").css("display","block");
							this.isLoaderVisible = true;
							isSuccessful = false;
						}
						else{
							$("#ageatassessmentvalidinputJaundice").css("display","none");
							isSuccessful = true;
						}
					}
					else{
						$("#ageatassessmentvalidinputJaundice").css("display","none");
						isSuccessful = true;
					}
				}

				return isSuccessful;
			}

			this.submitJaundice = function(){
				var isSuccessful = this.validateJaundice();

				if(this.statusJaundice.jaundice.jaundicestatus == 'Inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
          for(var i = 0; i < this.pastPrescriptionList.length; i++) {
            var obj = this.pastPrescriptionList[i];
            if(obj.isactive != false && obj.eventname != 'Stable Notes'){
              this.showMedicationPopUp();
              return;
            }
          }
				}
				this.statusJaundice.jaundice.assessmentTime = new Date(this.statusJaundice.jaundice.assessmentTime);
				console.log(this.statusJaundice.jaundice.assessmentTime);
				if(isSuccessful){
					if(this.saveBindScore == true){
						console.log("save bind");
						this.setBindScoreWithJaundice();
						console.log("after bind save");
					}
					else{
						if(this.statusJaundice.jaundice.jaundicestatus=='Inactive'){
							if(this.statusJaundice.listJaundice!=null && this.statusJaundice.listJaundice.length==0){
                this.warningMessage = "Episode of Jaundice not yet started. Cannot make Inactive.";
                this.showWarningPopUp();
								//this.showModal("Episode of Jaundice not yet started. Cannot make Inactive.");
              }
							else if(this.statusJaundice.listJaundice[0].jaundicestatus=='Inactive'){
                this.warningMessage = "Jaundice is already Inactive. Cannot make Inactive again.";
                this.showWarningPopUp();
								//this.showModal("Jaundice is already Inactive. Cannot make Inactive again.");
							} else if(this.statusJaundice.listJaundice[0].jaundicestatus!='No'){
                this.warningMessage = "Cannot make Jaundice Inactive. Jaundice is not Passive yet.";
                this.showWarningPopUp();
								//this.showModal("Cannot make Jaundice Inactive. Jaundice is not Passive yet.");
							}
							else
								this.saveJaundice();
						}
						else if(this.statusJaundice.jaundice.jaundicestatus=='No'){
							if(this.statusJaundice.listJaundice.length==0 ){
                this.warningMessage = "Episode of Jaundice not yet started. Cannot make Passive.";
                this.showWarningPopUp();
								//this.showModal("Episode of Jaundice not yet started. Cannot make Passive.");
							}
              else if(this.statusJaundice.listJaundice[0].jaundicestatus=='Inactive'){
                this.warningMessage = "Cannot make Jaundice Passive. Jaundice is currently Inactive.";
                this.showWarningPopUp();
								//this.showModal("Cannot make Jaundice Passive. Jaundice is currently Inactive.");
							}
							else{
								this.saveJaundice();
							}
						}
						else
							this.saveJaundice();
					}
				}
				else{}

			}

			this.saveJaundice = function(){
			  this.isLoaderVisible = true;
				this.statusJaundice.jaundice.uhid = this.childObject.uhid;
				console.log(this.statusJaundice);
				this.statusJaundice.userId = this.loggedUser;
				this.ageAtAssessmentHourFlag = false;
				this.rdsHourFlag = false;
				console.log("in save jaundice");

        try
        {
          this.http.post(this.apiData.saveJaundice,
            this.statusJaundice).subscribe(res => {
              console.log("after save jaundice");
              if(JSON.parse(localStorage.getItem('redirectBackToAdmission')) != null) {
  						localStorage.setItem('assessmentComment', JSON.stringify(this.statusJaundice.jaundice.comment));
  						if(JSON.parse(localStorage.getItem('isComingFromView'))) {
  							this.router.navigateByUrl('/admis/view-profile');
  						} else {
  							this.router.navigateByUrl('/admis/admissionform');
  						}
  						localStorage.removeItem('isComingFromView');
  					}
  					this.statusJaundice =res.json();
              		this.returnedResponse =res.json();
              		this.isLoaderVisible = false;


  						// this.statusJaundice = response;
  						// this.returnedResponse = response;
  						this.vanishSubmitResponseVariable = true;
  						this.responseMessage = this.statusJaundice.response.message;
  						this.responseType = this.statusJaundice.response.type;
  						console.log(this.statusJaundice.response.type);
  						console.log(this.statusJaundice.response.message);
  						this.systemValue('','causeOfJaundice');
  						this.systemValue('','orderInvestigation');
  						this.systemValue('','actionType');
  						this.systemValue('','riskFactor');
  						this.orderSelectedText = "";
  						this.jundiceTempObject.treatmentActionSelected = "";
  						this.causeOfJaundiceSelected = "";
  						console.log(this.returnedResponse);
  						this.setDataJaundice();
  						this.investOrderSelected = [];
  						this.investOrderNotOrdered = [];
  						this.statusJaundice.jaundice.episodeNumber = this.indcreaseEpisodeNumber(this.statusJaundice.listJaundice);
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

		this.refreshJaundice = function(){
			this.setDataJaundice();
			this.TimeCtrl();
			//this.getgraphData();
			this.orderSelectedText = "";
		}
  }


  indcreaseEpisodeNumber = function(list){
		console.log('indcreaseEpisodeNumber');
		var episodeNumber=1;
		if(this.currentAssessmentDateTime == undefined || this.currentAssessmentDateTime == null) {
			this.currentAssessmentDateTime = new Date();
		}

		if(list!= null && list!= undefined && list!= ""){
			for(var index = 0; index < list.length; index++){
				var obj = list[index];
				if(obj.assessmentTime <= this.currentAssessmentDateTime.getTime()
						&& obj.jaundicestatus == 'Inactive'){
					episodeNumber++;
				}
			}
		}
		return episodeNumber;
	}

	ageOnsetValidationEpisode = function() {
		// show message for validation for current age at onset, less than previous inactive episode
		if(this.statusJaundice.listJaundice.length>0){
			if(this.statusJaundice.listJaundice[0].jaundicestatus=='Inactive')
			{
				if(this.statusJaundice.jaundice.isageofonsetinhours==this.statusJaundice.listJaundice[0].isageofonsetinhours){
					if(this.statusJaundice.jaundice.ageofonset<=this.statusJaundice.listJaundice[0].ageofonset)
						this.ageOnsetPreviousExceed = true;
					else
						this.ageOnsetPreviousExceed = false;
				} else {
					if(this.statusJaundice.jaundice.isageofonsetinhours==true && this.statusJaundice.listJaundice[0].isageofonsetinhours==false){
						var diff = parseInt(this.statusJaundice.jaundice.ageofonset) - parseInt(this.statusJaundice.listJaundice[0].ageofonset)*24;
						if(diff<0)
							this.ageOnsetPreviousExceed = true;
						else
							this.ageOnsetPreviousExceed = false;
					} else{
						var diff = parseInt(this.statusJaundice.jaundice.ageofonset)*24 - parseInt(this.statusJaundice.listJaundice[0].ageofonset);
						if(diff<0)
							this.ageOnsetPreviousExceed = true;
						else
							this.ageOnsetPreviousExceed = false;
					}
				}


			}
		}
	}

  calculateAgeAtAssessment = function(){
		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");
		this.dateBirth = this.dateBirth.getTime();
		var currentAgeData = new Date();
		var stringCurrentAgeData = currentAgeData.toString();
		var tempCurrentTime = stringCurrentAgeData.substring(16,24);
		var tempAgeTime = this.jaundiceTempObj.timeOfAssessment +":00";
		let mainTempAge : any= stringCurrentAgeData.replace(tempCurrentTime,tempAgeTime);
		console.log(mainTempAge);
		mainTempAge = new Date(mainTempAge);
		mainTempAge = mainTempAge.getTime();
		this.diffTime = mainTempAge - this.dateBirth;
		if(this.jaundiceTempObj.meridian == false){
			this.diffTime = Math.round(this.diffTime + ((12*60*60*1000)/2));
		}else if(this.jaundiceTempObj.meridian == true){
			this.diffTime = Math.round(this.diffTime - ((12*60*60*1000)/2));
		}
		if(this.statusJaundice.jaundice.isageofassesmentinhours == false){
			this.diffTime = Math.floor(this.diffTime / (24 * 60 * 60 * 1000));
		}else if(this.statusJaundice.jaundice.isageofassesmentinhours == true){
			this.diffTime = Math.floor(this.diffTime / (60 * 60 * 1000));
		}
		this.statusJaundice.jaundice.ageatassesment = this.diffTime;
	}

  switchOrderTableJaundice = function(){
		this.TableOrderJaundiceFlag = !this.TableOrderJaundiceFlag;
	}

  setTodayAssessment = function(){
		var todayDate = new Date();
		this.statusJaundice.jaundice.assessmentDate = todayDate;
		var todayHours = todayDate.getHours();
		var todayMinutes = todayDate.getMinutes();
		if(todayHours<12){
			this.statusJaundice.jaundice.assessmentMeridiem = true;
			if(todayHours==0)
				this.statusJaundice.jaundice.assessmentHour = "12";
			if(todayHours<10)
				this.statusJaundice.jaundice.assessmentHour = '0' + todayHours.toString();
			else
				this.statusJaundice.jaundice.assessmentHour = todayHours.toString();
		}else{
			this.statusJaundice.jaundice.assessmentMeridiem = false;
			todayHours -=12;
			if(todayHours==0)
				this.statusJaundice.jaundice.assessmentHour = "12";
			else if(todayHours<10)
				this.statusJaundice.jaundice.assessmentHour = '0' + todayHours.toString();
			else
				this.statusJaundice.jaundice.assessmentHour = todayHours.toString();
		}
		if(todayMinutes<10)
			this.statusJaundice.jaundice.assessmentMin = '0' + todayMinutes.toString();
		else
			this.statusJaundice.jaundice.assessmentMin = 	todayMinutes.toString();

		this.jaundiceTempObj.assessmentDate = this.statusJaundice.jaundice.assessmentDate;
		this.statusJaundice.jaundice.assessmentTime = this.statusJaundice.jaundice.assessmentDate;
	}

	calculateAgeAtAssessmentManual = function(){
		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");

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
		this.currentAssessmentDateTime = new Date(this.statusJaundice.jaundice.assessmentTime);
		console.log(this.currentAssessmentDateTime);
		this.diffTime = this.currentAssessmentDateTime.getTime() - this.dateBirth.getTime();
		if(this.statusJaundice.jaundice.isageofassesmentinhours == true){
			this.statusJaundice.jaundice.ageatassesment = (this.diffTime)/(1000*60*60)+1;
      this.statusJaundice.jaundice.ageatassesment = parseInt(this.statusJaundice.jaundice.ageatassesment);
    }
		else{
			this.statusJaundice.jaundice.ageatassesment = parseInt(this.diffTime)/(1000*60*60*24)+0;
      this.statusJaundice.jaundice.ageatassesment = parseInt(this.statusJaundice.jaundice.ageatassesment);
    }
		this.statusJaundice.jaundice.episodeNumber = this.indcreaseEpisodeNumber(this.statusJaundice.listJaundice);
		this.calculatePhototherapy();
	}

	jaundiceScroll = function(){
		// $('html,body').animate({
		// 	scrollTop: $(".jaundiceFirst").offset().top},'slow');
		 this.creatingProgressNotesTemplate();
	}

	redirectToPrescription = function(eventName) {
		localStorage.setItem('medicationAssessment',JSON.stringify(eventName));
		localStorage.setItem('prescriptionList',JSON.stringify(this.statusJaundice.prescriptionList));
		localStorage.setItem('currentJaundiceSystemObj',JSON.stringify(this.statusJaundice));
		localStorage.setItem('currentJaundiceTempObj',JSON.stringify(this.jaundiceTempObj));
		this.router.navigateByUrl('/med/medications');
	}

	populateJaundiceInvestigation = function() {
		var date = this.dateformatter(this.jaundiceTempObj.assessmentDate,'gmt','standard');

    try{
        this.http.request(this.apiData.getJaundiceInvestigationsRecd+this.childObject.uhid+'/'+'/'+date,)
        .subscribe(res => {
          this.investigationsOrdered = res.json();
          console.log("jaundice investigationsOrdered ");
					if(this.investigationsOrdered!=null)
						{this.populateJaundiceInvestigationForm(this.investigationsOrdered);}
         },
          err => {
            console.log("Error occured.")
          }

         );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};

  populateJaundiceInvestigationForm = function( invOrdered) {
		console.log("inside populateJaundiceInvestigationForm ");
		var loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
		var invDate = this.dateformatter(this.selectedInvDate,'gmt','standard');
		console.log(this.testItemsListComplete);
		this.jaundiceTestObjects = [];
		for(var i=0; i<invOrdered.length; ++i)
		{
			var obj = {
  			"investigationorder_time" : (invOrdered[i])[0].investigationorder_time,
  			"reportreceived_time" : (invOrdered[i])[0].reportreceived_time,
  			"itemname" : (invOrdered[i])[1].itemname,
  			"itemvalue" : (invOrdered[i])[1].itemvalue,
  			"normalrange" : (invOrdered[i])[1].normalrange
      };
      this.jaundiceTestObjects.push(obj);
		}
		console.log("jandiceTestObjects raw");
		console.log(this.jaundiceTestObjects);

	};

	creatingProgressNotesTemplate =function(from){
		// this.statusJaundice.jaundice.comment = template;
		var progressNotes = "";

		if(this.statusJaundice.listJaundice==null || this.statusJaundice.listJaundice.length<=0 ||
				this.statusJaundice.listJaundice[0].jaundicestatus=='Inactive'){

			if(this.statusJaundice.jaundice.episodeNumber != null){
				progressNotes += "Episode number: " + this.statusJaundice.jaundice.episodeNumber + ". ";
			}
			if(this.statusJaundice.jaundice.ageofonset!=null && this.statusJaundice.jaundice.ageofonset!= undefined){
				if(this.statusJaundice.jaundice.isageofonsetinhours){
					if(this.statusJaundice.jaundice.ageofonset*1 ==1 || this.statusJaundice.jaundice.ageofonset*1 ==0){
						progressNotes += "Baby noted to have jaundice at the age of "+Math.round(this.statusJaundice.jaundice.ageofonset)+" hr ";
					}else{
						progressNotes += "Baby noted to have jaundice at the age of "+Math.round(this.statusJaundice.jaundice.ageofonset)+" hrs ";
					}
				}else
					if(this.statusJaundice.jaundice.ageofonset*1 ==1 || this.statusJaundice.jaundice.ageofonset*1 ==0){
						progressNotes += "Baby noted to have jaundice at the age of "+Math.round(this.statusJaundice.jaundice.ageofonset)+" day ";
					}else{
						progressNotes += "Baby noted to have jaundice at the age of "+Math.round(this.statusJaundice.jaundice.ageofonset)+" days ";
					}
			}
      if(this.statusJaundice.motherBloodGroup != null && this.statusJaundice.motherBloodGroup != ''){
        progressNotes += ". Mother's blood group is " + this.statusJaundice.motherBloodGroup + "." ;
      }
		}else{
			progressNotes += "Baby continues to have jaundice ";
		}

		if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
			this.riskStr = "";
			for(var index=0; index<this.statusJaundice.jaundice.riskFactorList.length;index++){
				for(var indexDrop=0; indexDrop<this.statusJaundice.dropDowns.riskFactor.length;indexDrop++){
					if(this.statusJaundice.jaundice.riskFactorList[index] == this.statusJaundice.dropDowns.riskFactor[indexDrop].key){
						if(this.riskStr==""){
							this.riskStr = this.statusJaundice.dropDowns.riskFactor[indexDrop].value;
						}else{
							this.riskStr = this.riskStr +","+ this.statusJaundice.dropDowns.riskFactor[indexDrop].value;
						}

					}

				}
			}
			if(this.riskStr!=""){
				progressNotes = progressNotes + "with risk factors as "+this.riskStr+" ";
			}
		}else{
			this.riskStr = "";
		}

		if(this.statusJaundice.jaundice.tbcvalue!=null && this.statusJaundice.jaundice.tbcvalue!=''){
			if(this.statusJaundice.jaundice.tcbortsb!=null){
				if(this.statusJaundice.jaundice.tcbortsb==true){
					progressNotes =progressNotes+"with TCB of "+this.statusJaundice.jaundice.tbcvalue+"mg/dl ";
				}else{
					progressNotes =progressNotes+"with TSB of "+this.statusJaundice.jaundice.tbcvalue+"mg/dl ";
				}
			}


			if(this.statusJaundice.listJaundice!=null && this.statusJaundice.listJaundice.length>0){
				if(this.statusJaundice.jaundice.tcbortsb==false){
					if(this.statusJaundice.listJaundice[0].maxTsb*1 > this.statusJaundice.jaundice.tbcvalue*1){
						this.statusJaundice.jaundice.maxTsb = this.statusJaundice.listJaundice[0].maxTsb*1;
					}else{
						this.statusJaundice.jaundice.maxTsb = this.statusJaundice.jaundice.tbcvalue*1;
					}
					this.statusJaundice.jaundice.maxTcb = this.statusJaundice.listJaundice[0].maxTcb;
				}else{
					if(this.statusJaundice.listJaundice[0].maxTcb*1 > this.statusJaundice.jaundice.tbcvalue*1){
						this.statusJaundice.jaundice.maxTcb = this.statusJaundice.listJaundice[0].maxTcb;
					}else{
						this.statusJaundice.jaundice.maxTcb = this.statusJaundice.jaundice.tbcvalue;
					}
					this.statusJaundice.jaundice.maxTsb = this.statusJaundice.listJaundice[0].maxTsb*1;
				}
				if(this.statusJaundice.jaundice.maxTsb!=null && this.statusJaundice.jaundice.maxTsb!=''
					&& this.statusJaundice.jaundice.maxTsb*1>0){
					progressNotes =progressNotes+"(Maximum TSB "+this.statusJaundice.jaundice.maxTsb+" mg/dl) ";
				}
			}else{
				if(this.statusJaundice.jaundice.tcbortsb==false){
					this.statusJaundice.jaundice.maxTsb = this.statusJaundice.jaundice.tbcvalue*1;
					progressNotes =progressNotes+"(Maximum TSB "+this.statusJaundice.jaundice.maxTsb+" mg/dl) ";
				}else{
					this.statusJaundice.jaundice.maxTcb = this.statusJaundice.jaundice.tbcvalue;
				}


			}
		}

		if(this.whatRecommended!=null && this.whatRecommended!=''){

			if(this.whatRecommended.includes('Phototherapy')){
				if(this.statusJaundice.jaundice.isphotobelowthreshold=="threshold"){
					progressNotes =progressNotes+"which is at phototherapy threshold. "; // which
					// is
					// below
					// tranfusion
					// range
					// which is above phototherapy. //
				}else if(this.statusJaundice.jaundice.isphotobelowthreshold=="aboveThreshold"){
					progressNotes =progressNotes+"which is above phototherapy threshold. ";
				}else if(this.statusJaundice.jaundice.isphotobelowthreshold=="belowThreshold"){
					progressNotes =progressNotes+"which is below phototherapy threshold. ";
				}
			} else if(this.whatRecommended.includes('Exchange Transfusion')){
				if(this.statusJaundice.jaundice.isphotobelowthreshold=="threshold"){
					progressNotes =progressNotes+"which is at exchange transfusion threshold. "; // which
					// is
					// below
					// tranfusion
					// range
					// which is above phototherapy. //
				}else if(this.statusJaundice.jaundice.isphotobelowthreshold=="aboveThreshold"){
					progressNotes =progressNotes+"which is above exchange transfusion threshold. ";
				}else if(this.statusJaundice.jaundice.isphotobelowthreshold=="belowThreshold"){
					progressNotes =progressNotes+"which is below exchange transfusion threshold. ";
				}
			}
		}

		if(this.scoreBindObj!=null && this.scoreBindObj.bindscore!=null && this.scoreBindObj.bindscore!="" && this.saveBindScore){
			progressNotes = progressNotes+ " Bind Score is "+this.scoreBindObj.bindscore+". ";
		}

		if(this.orderSelectedText != ""){
			if(this.orderSelectedText.indexOf(",")==-1){
				progressNotes = progressNotes +" Investigation sent is "+this.orderSelectedText+". ";
			}else{
				progressNotes = progressNotes +" Investigations sent are "+this.orderSelectedText+". ";
			}
		}



		if(this.statusJaundice.jaundice.actiontypeList!=null && this.statusJaundice.jaundice.actiontypeList.indexOf("TRE001")!=-1 &&  this.statusJaundice.jaundice.phototherapyvalue!=null){
			// progressNotes = progressNotes +
			// this.statusJaundice.jaundice.phototherapyvalue.charAt(0).toUpperCase()
			// + this.statusJaundice.jaundice.phototherapyvalue.slice(1)+"
			// phototherapy"+". ";
			if(this.statusJaundice.jaundice.phototherapyvalue!=null){
				if(this.statusJaundice.jaundice.phototherapyvalue=='Start'){
					progressNotes = progressNotes + "Started on phototherapy. ";
				}else if(this.statusJaundice.jaundice.phototherapyvalue=='Continue'){
					progressNotes = progressNotes + "Continue phototherapy. ";
				}else if(this.statusJaundice.jaundice.phototherapyvalue=='Stop'){
					progressNotes = progressNotes + "Phototherapy stopped. ";
				}
			}
		}

		if(this.statusJaundice.jaundice.actiontypeList!=null &&  this.statusJaundice.jaundice.actiontypeList.indexOf('TRE002')!=-1 &&  this.statusJaundice.jaundice.exchangetrans!=null){
			if(this.statusJaundice.jaundice.exchangetrans)
				progressNotes = progressNotes +"Exchange transfusions planned. ";
			else
				progressNotes = progressNotes +" Exchange transfusions done. ";
		}

		if(this.statusJaundice.jaundice.actiontypeList!=null &&  this.statusJaundice.jaundice.actiontypeList.indexOf('TRE003')!=-1 && this.statusJaundice.jaundice.ivigvalue!=null && this.statusJaundice.jaundice.ivigvalue!=''){
			progressNotes = progressNotes+ "Give IVIG in a dose of " + this.statusJaundice.jaundice.ivigvalue+"mg/kg. " ;
		}

		if(this.statusJaundice.jaundice.medicationStr != undefined && this.statusJaundice.jaundice.medicationStr != null && this.statusJaundice.jaundice.medicationStr != '') {
			progressNotes += this.statusJaundice.jaundice.medicationStr + ". ";
		}

		if(this.statusJaundice.jaundice.otheractiontype!=null){
			progressNotes = progressNotes+ this.statusJaundice.jaundice.otheractiontype+".";
		}

		if(this.statusJaundice.jaundice.causeofjaundiceList!=null && this.statusJaundice.jaundice.causeofjaundiceList.length>0){
			this.causeOfJaundiceStr ="";
			for(var index=0; index<this.statusJaundice.jaundice.causeofjaundiceList.length;index++){
				for(var indexDrop=0; indexDrop<this.statusJaundice.dropDowns.causeOfJaundice.length;indexDrop++){
					if(this.statusJaundice.jaundice.causeofjaundiceList[index] == this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].key){
						if(this.causeOfJaundiceStr==""){
							if(this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].value=='Others'){
								if(this.statusJaundice.jaundice.otherCause!=null && this.statusJaundice.jaundice.otherCause!=''){
									this.causeOfJaundiceStr = this.statusJaundice.jaundice.otherCause;
								}else{
									this.causeOfJaundiceStr = this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].value;
								}
							}else{
								this.causeOfJaundiceStr = this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].value;
							}
						}else{
							if(this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].value=='Others'){
								if(this.statusJaundice.jaundice.otherCause!=null && this.statusJaundice.jaundice.otherCause!=''){
									this.causeOfJaundiceStr = this.causeOfJaundiceStr + ", " + this.statusJaundice.jaundice.otherCause;
								}else{
									this.causeOfJaundiceStr = this.causeOfJaundiceStr +", "+ this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].value;
								}
							}else{
								this.causeOfJaundiceStr = this.causeOfJaundiceStr +", "+ this.statusJaundice.dropDowns.causeOfJaundice[indexDrop].value;
							}

						}

					}

				}
			}

			if(this.causeOfJaundiceStr!=''){
				if(this.causeOfJaundiceStr.indexOf(",")==-1){
					progressNotes += " " + this.causeOfJaundiceStr + " is the most likely cause of the jaundice.";
				}else{
					progressNotes += " " + this.causeOfJaundiceStr + " are the most likely cause of the jaundice.";
				}
			}


		}else{
			this.causeOfJaundiceStr ="";
		}

		var planAddedd = false;

		if(this.statusJaundice.jaundice.actionduration!=null && this.statusJaundice.jaundice.actionduration!='' && this.statusJaundice.jaundice.isactiondurationinhours!=null){
			var planAddedd = true;
			if(this.statusJaundice.jaundice.isactiondurationinhours=='hours'){
				if(this.statusJaundice.jaundice.actionduration*1 ==1 || this.statusJaundice.jaundice.actionduration*1 ==0){
					progressNotes = progressNotes + " Plan is to reassess after " + this.statusJaundice.jaundice.actionduration+ " hr ";
				}else{
					progressNotes = progressNotes + " Plan is to reassess after " + this.statusJaundice.jaundice.actionduration+ " hrs ";
				}
			}else if(this.statusJaundice.jaundice.isactiondurationinhours=='days'){
				if(this.statusJaundice.jaundice.actionduration*1 ==1 || this.statusJaundice.jaundice.actionduration*1 ==0){
					progressNotes = progressNotes + " Plan is to reassess after " + this.statusJaundice.jaundice.actionduration+ " day ";
				}else{
					progressNotes = progressNotes + " Plan is to reassess after " + this.statusJaundice.jaundice.actionduration+ " days ";
				}
			}else if(this.statusJaundice.jaundice.isactiondurationinhours=='mins'){
				if(this.statusJaundice.jaundice.actionduration*1 ==1 || this.statusJaundice.jaundice.actionduration*1 ==0){
					progressNotes = progressNotes + " Plan is to reassess after " + this.statusJaundice.jaundice.actionduration+ " min ";
				}else{
					progressNotes = progressNotes + " Plan is to reassess after " + this.statusJaundice.jaundice.actionduration+ " minutes ";
				}
			}
		}else if(this.statusJaundice.jaundice.actionduration == "" || this.statusJaundice.jaundice.actionduration == null){
			this.statusJaundice.jaundice.isactiondurationinhours = null;
		}


		if(this.statusJaundice.jaundice.planOther!=null && this.statusJaundice.jaundice.planOther!=''){
			if(planAddedd==true){
				progressNotes += "and "+this.statusJaundice.jaundice.planOther+". ";
			}else{
				progressNotes += this.statusJaundice.jaundice.planOther+". ";
			}
		}else if(planAddedd==true){
			progressNotes += ".";
		}


		this.statusJaundice.jaundice.comment = progressNotes;

		// to calculate string for treatment action selected..
		this.jundiceTempObject.treatmentActionSelected ="";
		var commaflag = false;
		if(this.statusJaundice.jaundice.actiontypeList!=null && this.statusJaundice.jaundice.actiontypeList.length>0){
			if(this.statusJaundice.jaundice.actiontypeList.includes('TRE001')) {
				commaflag = true;
				if(this.statusJaundice.jaundice.phototherapyvalue == null){
					this.jundiceTempObject.treatmentActionSelected += "Phototherapy";
				}else {
					this.jundiceTempObject.treatmentActionSelected += "Phototherapy: " + this.statusJaundice.jaundice.phototherapyvalue;
				}
			}

			if(this.statusJaundice.jaundice.actiontypeList.includes('TRE002')){
				if(commaflag){
					this.jundiceTempObject.treatmentActionSelected += ", ";
				}
				commaflag = true;

				if(this.statusJaundice.jaundice.exchangetrans == null){
					this.jundiceTempObject.treatmentActionSelected += "Exchange Transfusion";
				}else if(this.statusJaundice.jaundice.exchangetrans == true){
					this.jundiceTempObject.treatmentActionSelected += "Exchange Transfusion: Planned";
				} else {
					this.jundiceTempObject.treatmentActionSelected += "Exchange Transfusion: Done";
				}
			}

			if(this.statusJaundice.jaundice.actiontypeList.includes('TRE003')){
				if(commaflag){
					this.jundiceTempObject.treatmentActionSelected += ", ";
				}
				commaflag = true;
				if(this.statusJaundice.jaundice.ivigvalue == null){
					this.jundiceTempObject.treatmentActionSelected += " Intravenous Immunoglobin (IVIG)";
				} else {
					this.jundiceTempObject.treatmentActionSelected += " Intravenous Immunoglobin (IVIG) : " + this.statusJaundice.jaundice.ivigvalue;
				}
			}

			if(this.statusJaundice.jaundice.actiontypeList.includes('others')){
				if(commaflag){
					this.jundiceTempObject.treatmentActionSelected += ", ";
				}
				if(this.statusJaundice.jaundice.otheractiontype!=null && this.statusJaundice.jaundice.otheractiontype!='' ){

					this.jundiceTempObject.treatmentActionSelected = this.jundiceTempObject.treatmentActionSelected
					+""+this.statusJaundice.jaundice.otheractiontype;
				}else{
					this.jundiceTempObject.treatmentActionSelected = this.jundiceTempObject.treatmentActionSelected+ "Other";
				}
			}
			console.log("jundiceTempObject.treatmentActionSelected: "+this.jundiceTempObject.treatmentActionSelected);
		}

		if(this.statusJaundice.jaundice.medicationStr != undefined && this.statusJaundice.jaundice.medicationStr != null && this.statusJaundice.jaundice.medicationStr != '') {
			if(commaflag){
				this.jundiceTempObject.treatmentActionSelected += ", ";
			}
			commaflag = true;
			this.jundiceTempObject.treatmentActionSelected += this.statusJaundice.jaundice.medicationStr;
		}

		this.ageOnsetValidationEpisode();
	}

	clinicalSectionVisible = function(){
		this.isClinicalVisible = !this.isClinicalVisible ;
	}

	actionSectionVisible = function(){
		this.isActionVisible = !this.isActionVisible ;
	}

	planSectionVisible = function(){
		this.isPlanVisible = !this.isPlanVisible ;
	}

	scoreSectionVisible = function(){
		this.isScoreVisible = !this.isScoreVisible ;
	}

	progressNotesSectionVisible = function(){
		this.isProgressNotesVisible = !this.isProgressNotesVisible ;
	}

  ageOnSetCalculation =function(changedParam){
		if(changedParam=='meridian'){
			if(this.statusJaundice.jaundice.isageofonsetinhours ){

				if(this.hourDayDiffJaundice == null || this.hourDayDiffJaundice == undefined){
					this.hourDayDiffJaundice = 0;
				}

				this.statusJaundice.jaundice.ageofonset *= 24;
				this.statusJaundice.jaundice.ageofonset += this.hourDayDiffJaundice;
			}else if(!(this.statusJaundice.jaundice.isageofonsetinhours)){
				this.hourDayDiffJaundice = this.statusJaundice.jaundice.ageofonset%24;
				this.statusJaundice.jaundice.ageofonset -= this.hourDayDiffJaundice;
				this.statusJaundice.jaundice.ageofonset /= 24;
			}
		}
		else if(changedParam=='meridiam'){

			if(this.statusJaundice.jaundice.isageofassesmentinhours && this.ageAtAssessmentHourFlag){
				this.statusJaundice.jaundice.ageatassesment *= 24;
				this.statusJaundice.jaundice.ageatassesment += this.hourDayDiffAgeAtAssessment;
				this.ageAtAssessmentHourFlag = false;
			}else if(!(this.statusJaundice.jaundice.isageofassesmentinhours || this.ageAtAssessmentHourFlag)){
				this.ageAtAssessmentHourFlag = true;
				this.hourDayDiffAgeAtAssessment = this.statusJaundice.jaundice.ageatassesment%24;
				this.statusJaundice.jaundice.ageatassesment -= this.hourDayDiffAgeAtAssessment;
				this.statusJaundice.jaundice.ageatassesment /= 24;
			}
		}else{
			this.hourDayDiffAgeAtAssessment = 0;
			this.hourDayDiffRds = 0;
		}

		this.creatingProgressNotesTemplate();

		this.ageOnsetValidationEpisode();
	};

  bhutaniGraph = function() {
		this.maxNiceValue = 408;
		this.maxBhutaniValue = 168;
		console.log(this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph);

		if(this.statusJaundice.jaundice.tbcvalue != null){
			this.statusJaundice.jaundice.tbcvalue = parseFloat(this.statusJaundice.jaundice.tbcvalue);
		}else{
			this.statusJaundice.jaundice.tbcvalue = 0;
		}
		console.log("bhutaniGraph");
		$("#ballardOverlay").css("display", "block");
		$("#bhutaniPopup").addClass("showing");


		if(this.statusJaundice.jaundice.gestation*1<35){ // NICE Chart..
			this.phototherapyGraph = false;
			this.phototherapyGraphType = "Nice Chart";


			if(this.statusJaundice.jaundice.ageatassesment!=null && this.statusJaundice.jaundice.ageatassesment!="" &&
					this.statusJaundice.jaundice.gestation!=null && this.statusJaundice.jaundice.gestation!=""){

				this.niceLowGraphArr = this.statusJaundice.jaundicePhototherapyGraph.niceGraph[this.statusJaundice.jaundice.gestation+" wk"]
				.graphPoints.phototherapy;
				this.niceHighGraphArr	= this.statusJaundice.jaundicePhototherapyGraph.niceGraph[this.statusJaundice.jaundice.gestation+" wk"]
				.graphPoints.exchange;

				var ageOfAssessment = this.statusJaundice.jaundice.ageatassesment * 1;
				if(!this.statusJaundice.jaundice.isageofassesmentinhours) {
					ageOfAssessment *= 24;
				}

				if(ageOfAssessment>408){
					this.maxNiceValue = ageOfAssessment;
					var tempObjLowGraph = [];
					tempObjLowGraph[0] = ageOfAssessment;
					tempObjLowGraph[1] = this.niceLowGraphArr[this.niceLowGraphArr.length-1]["1"];
					this.niceLowGraphArr.push(tempObjLowGraph);
					var tempObjHighGraph = [];
					tempObjHighGraph[0] = ageOfAssessment;
					tempObjHighGraph[1] = this.niceHighGraphArr[this.niceHighGraphArr.length-1]["1"];
					this.niceHighGraphArr.push(tempObjHighGraph);
				}else{
					this.maxNiceValue = 408;
				}

			}


			this.getNiceGraph();
		}else{

			var calculatedRiskFactors = ["RSK00001", "RSK00002","RSK00003","RSK00004","RSK00005","RSK00006","RSK00007","RSK00008"];
			var isCalculationEnabled = false;

			if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
				for(var i=0; i<calculatedRiskFactors.length;i++){
					if (this.statusJaundice.jaundice.riskFactorList.indexOf(calculatedRiskFactors[i]) >= 0) {
						isCalculationEnabled = true;
						break;
					}
				}
			}


			if(this.statusJaundice.jaundice.gestation*1>=38){
				if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
					if(isCalculationEnabled == true){
						this.riskLyingToPhtottherapy = "Phototherapy mid risk";
						this.riskLyingToExchange = "Exchange mid risk";
						this.riskGraphDataPhototherapy = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.graphPoints.phototherapy;
						this.riskGraphDataExchange = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.graphPoints.exchange;
					}
					else{
						this.riskLyingToPhtottherapy = "Phototherapy low risk";
						this.riskLyingToExchange = "Exchange low risk";
						this.riskGraphDataPhototherapy = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.graphPoints.phototherapy;
						this.riskGraphDataExchange = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.graphPoints.exchange;
					}

				}else{
					// low risk...........
					this.riskLyingToPhtottherapy = "Phototherapy low risk";
					this.riskLyingToExchange = "Exchange low risk";
					this.riskGraphDataPhototherapy = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.graphPoints.phototherapy;
					this.riskGraphDataExchange = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.lowRisk.graphPoints.exchange;
				}
			}else if(this.statusJaundice.jaundice.gestation*1<=37){// two
				if(this.statusJaundice.jaundice.riskFactorList!=null && this.statusJaundice.jaundice.riskFactorList.length>0){
					if(isCalculationEnabled == true){
						this.riskLyingToPhtottherapy = "Phototherapy high risk";
						this.riskLyingToExchange = "Exchange high risk";
						this.riskGraphDataPhototherapy = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.highRisk.graphPoints.phototherapy;
						this.riskGraphDataExchange = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.highRisk.graphPoints.exchange;
					}
					else{
						this.riskLyingToPhtottherapy = "Phototherapy mid risk";
						this.riskLyingToExchange = "Exchange mid risk";
						this.riskGraphDataPhototherapy = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.graphPoints.phototherapy;
						this.riskGraphDataExchange = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.graphPoints.exchange;
					}

					}else{
  					this.riskLyingToPhtottherapy = "Phototherapy mid risk";
  					this.riskLyingToExchange = "Exchange mid risk";
  					this.riskGraphDataPhototherapy = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.graphPoints.phototherapy;
  					this.riskGraphDataExchange = this.statusJaundice.jaundicePhototherapyGraph.bhutaniGraph.midRisk.graphPoints.exchange;
				}
			}

			var ageOfAssessment = this.statusJaundice.jaundice.ageatassesment * 1;
			if(!this.statusJaundice.jaundice.isageofassesmentinhours) {
				ageOfAssessment *= 24;
			}

			if(ageOfAssessment>168){
				this.maxBhutaniValue = ageOfAssessment;
				var tempObjLowGraph = [];
				tempObjLowGraph[0] = ageOfAssessment;
				tempObjLowGraph[1] = this.riskGraphDataPhototherapy[this.riskGraphDataPhototherapy.length-1]["1"];
				this.riskGraphDataPhototherapy.push(tempObjLowGraph);
				var tempObjHighGraph = [];
				tempObjHighGraph[0] = ageOfAssessment;
				tempObjHighGraph[1] = this.riskGraphDataExchange[this.riskGraphDataExchange.length-1]["1"];
				this.riskGraphDataExchange.push(tempObjHighGraph);
			}else{
				this.maxBhutaniValue = 168;
			}
			this.phototherapyGraphType = "Bhutani Chart";
			this.getBhutaniGraph();

			this.phototherapyGraph = true; // please name it properly..

		}

	};
	closeModalBhutani = function() {
		$("#ballardOverlay").css("display", "none");
		$("#bhutaniPopup").toggleClass("showing");
	};

  getBhutaniGraph = function(){
		var ageOfAssessment = this.statusJaundice.jaundice.ageatassesment * 1;
		if(!this.statusJaundice.jaundice.isageofassesmentinhours) {
			ageOfAssessment *= 24;
		}

    var tempBhutaniSeries =[
  		{
  			name : this.riskLyingToExchange,
  			marker : {
  				enabled : true
  				,radius: 1
  			},
  			color : "#ff0000",
  			data : this.riskGraphDataExchange
  		},{
  			name : this.riskLyingToPhtottherapy,
  			marker : {
  				enabled : true
  				,radius: 1
  			},
  			color : "#39bd6b",
  			data : this.riskGraphDataPhototherapy
  		},{
  			name : 'TcB Of Patient',
  			marker : {
  				enabled : true
  				,radius: 3
  			},
  			color : "#000000",
  			data : [[ageOfAssessment,this.statusJaundice.jaundice.tbcvalue]]
  		}
    ];


    this.chartBhutani = new Chart({
      chart: {
        type: 'spline'
      },
			title : {
				text : 'Bhutani Graph'
			},
			xAxis : {
				title : {
					text : 'Postnatal Age (in hrs)'
				},
				allowDecimals : false,
				tickInterval : 12,
				min : 0,
				max : this.maxBhutaniValue*1+24
			},
			yAxis : {
				title : {
					text : 'Total Serum Bilirubin (mg/dl)'
				}
			},
      credits: {
        enabled: false
      },
      series: tempBhutaniSeries
    });
  }

  getNiceGraph = function(){
		var ageOfAssessment = this.statusJaundice.jaundice.ageatassesment * 1;
		if(!this.statusJaundice.jaundice.isageofassesmentinhours) {
			ageOfAssessment *= 24;
		}

    var tempNiceSeries =[
			{
				name : 'Blood Transfusion',
				marker : {
					enabled : true
					,radius: 1
				},
				color : "#ff0000",
				data : this.niceHighGraphArr
			},{
				name : 'Phototherapy',
				marker : {
					enabled : true
					,radius: 1
				},
				color : '#39bd6b',
				data : this.niceLowGraphArr
			},{
				name : 'TcB Of Patient',
				marker : {
					enabled : true
					,radius: 3
				},
				color : "#000000",
				data : [[ageOfAssessment,this.statusJaundice.jaundice.tbcvalue*1],[ageOfAssessment,this.statusJaundice.jaundice.tbcvalue*1]]
			}
			];


    this.chartNice = new Chart({
      chart: {
        type: 'spline'
      },
			title : {
				text : 'Nice Graph'
			},
			xAxis : {
				title : {
					text : 'Postnatal Age (in hrs)'
				},
				allowDecimals : true,
				tickInterval : 12,
				min : 0,
				max : this.maxNiceValue*1+24
			},
			yAxis : {
				title : {
					text : 'Total Serum Bilirubin (mg/dl)'
				}
			},
      credits: {
        enabled: false
      },
      series: tempNiceSeries
    });
  }

  jaundiceView = function(){
		this.isJaundiceGraph = ! this.isJaundiceGraph;
		this.GraphViewToggle = !this.GraphViewToggle;
		this.displayJaundiceDaysGraphData();
		this.displayJaundiceHourGraphData();
	}
	switchPastGraph = function(){
		this.GraphViewToggle = !this.GraphViewToggle;
	}

  displayJaundiceDaysGraphData = function(){
		this.GraphViewToggle = !this.GraphViewToggle;
    var tempDaySeries = [{
			name : 'Jaundice',
			marker : {
				enabled : true
			},
			color : '#39bd6b',
			data : this.statusJaundice.jaundiceGraphData.days
		}];

    this.chartJaundiceDay = new Chart({
      chart: {
        type: 'spline'
      },
			title : {
				text : 'Jaundice Graph'
			},
			xAxis : {
				title : {
					text : 'Age(Days)'
				},
				allowDecimals : false,
				tickInterval : 1,
				min : 0,
				max : this.statusJaundice.jaundice.ageatassesment*1/24+20
			},
			yAxis : {
				title : {
					text : 'TcB'
				},
				allowDecimals : false,
				tickInterval : 2,
				min : 0,
				max : this.statusJaundice.maxTcB+5
			},
      credits: {
        enabled: false
      },
      series: tempDaySeries
    });
  }

  displayJaundiceHourGraphData = function(){
    var tempHourSeries = [{
			name : 'Jaundice',
			marker : {
				enabled : true
			},
			color : '#39bd6b',
			data : this.statusJaundice.jaundiceGraphData.hours
		}];

    this.chartJaundiceHour = new Chart({
      chart: {
        type: 'spline'
      },
  		title : {
  			text : 'Jaundice Graph'
  		},
  		xAxis : {
  			title : {
  				text : 'Age(Hours)'
  			},
  			allowDecimals : false,
  			tickInterval : 12,
  			min : 0,
  			max : this.statusJaundice.jaundice.ageatassesment*1+10
  		},
  		yAxis : {
  			title : {
  				text : 'TcB'
  			},
  			allowDecimals : false,
  			tickInterval : 2,
  			min : 0,
  			max : this.statusJaundice.maxTcB+5
  		},
      credits: {
        enabled: false
      },
      series: tempHourSeries
    });
	}

  showCheckboxes = function(id) {
    var expanded = false;
		console.log(id);
		var fields = id.split('-');
		var name = fields[2];
		console.log(name);
		var checkboxContId = "#checkboxes-"+ name;
		console.log(checkboxContId);
		var width = $("#multiple-selectbox-1").width();
		if (!expanded) {
			$(checkboxContId).toggleClass("show");
			// checkboxes.style.display = "block";
			$(checkboxContId).width(width);
			expanded = true;
		} else {
			$(checkboxContId).removeClass("show");
			expanded = false;
		}
	}

	systemValue = function(Value,multiCheckBoxName){

		if(multiCheckBoxName=='causeOfJaundice'){
			this.symptomsArr = this.statusJaundice.jaundice.causeofjaundiceList;
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			var flag = true;
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(Value);
			}else{
				for(var i=0;i< this.symptomsArr.length;i++){
					if(Value == this.symptomsArr[i]){
						this.symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					this.symptomsArr.push(Value);
				}
			}
			if(this.symptomsArr==null){
				this.statusJaundice.jaundice.causeofjaundiceList = [];
			}else{
				this.statusJaundice.jaundice.causeofjaundiceList = this.symptomsArr;
			}

			if(this.statusJaundice.jaundice.causeofjaundiceList.length>0 && this.statusJaundice.jaundice.causeofjaundiceList[0]!=''){
				this.causeOfJaundiceSelected = "Selected:";
			}else{
				this.causeOfJaundiceSelected = "";
			}

			console.log(this.symptomsArr);
		}else if(multiCheckBoxName=='orderInvestigation'){
			this.symptomsArr = this.statusJaundice.jaundice.orderinvestigationList;
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			var flag = true;
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(Value);
			}else{
				for(var i=0;i< this.symptomsArr.length;i++){
					if(Value == this.symptomsArr[i]){
						this.symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					this.symptomsArr.push(Value);
				}
			}

			if(this.symptomsArr==null){
				this.statusJaundice.jaundice.orderinvestigationList = [];
			}else{
				this.statusJaundice.jaundice.orderinvestigationList = this.symptomsArr;
			}

			console.log(this.symptomsArr);
		}else if(multiCheckBoxName=='actionType'){
			this.symptomsArr = this.statusJaundice.jaundice.actiontypeList;
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			var flag = true;
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(Value);
			}else{
				if(!this.symptomsArr.includes(Value))
					this.symptomsArr.push(Value);
			}



			if(this.symptomsArr==null){
				this.statusJaundice.jaundice.actiontypeList = [];
			}else{
				this.statusJaundice.jaundice.actiontypeList = this.symptomsArr;
			}
			this.totalBindScore="";

		}else if(multiCheckBoxName=='riskFactor'){
			this.symptomsArr = this.statusJaundice.jaundice.riskFactorList;
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			var flag = true;
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(Value);
			}else{
				for(var i=0;i< this.symptomsArr.length;i++){
					if(Value == this.symptomsArr[i]){
						this.symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					this.symptomsArr.push(Value);
				}
			}

			if(this.symptomsArr==null){
				this.statusJaundice.jaundice.riskFactorList = [];
			}else{
				this.statusJaundice.jaundice.riskFactorList = this.symptomsArr;
			}
			console.log(this.symptomsArr);

		}
		this.creatingProgressNotesTemplate();
		this.calculatePhototherapy();
	}

  inActiveJaundice = function(){
    this.warningMessage = "You have opted to inactivate current Jaundice Assessment for the baby";
    this.showWarningPopUp();
		//this.showModal("You have opted to inactivate current Jaundice Assessment for the baby", "info");
		this.statusJaundice.jaundice.jaundicestatus = "Inactive";
	}

	getBindStructure = function(newScore) {
		if(this.statusJaundice.jaundice.bindscoreid == null) {
			if(newScore == true){
				console.log("new Bind Score, no bindScoreId");
				var getbindscoreid = 0;
			} else {
				this.noBindScoreAvailable = true;
				this.scoreBindObj.bindscore = null;
				return;
			}
		} else {
			let getbindscoreid : any = this.statusJaundice.jaundice.bindscoreid;
		}
		this.noBindScoreAvailable = false;

    try{
        this.http.request(this.apiData.getBind + getbindscoreid,)
        .subscribe(res => {
          this.bindResponse = res.json();
          this.bindStructure = this.bindResponse.bindStatusList;
          this.scoreBindObj = this.bindResponse.scoreBindObj;
          console.log(this.bindStructure);
          console.log("the bind score object is");
          console.log(this.scoreBindObj);
          for (var prop in this.statusScore) {
            console.log(this.scoreBindObj[prop]);
            this.statusScore[prop] = this.scoreBindObj[prop];
          }
         },
          err => {
            console.log("Error occured.")
          }

         );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};
	/*
	 * End of code for getting bindStructure from API
	 */

	/*
	 * Method for change of checkbox values
	 */
	scoreSelect = function(dbRefId, id, scoreValue,ivalue) {
		console.log(dbRefId);
		console.log(id);
		console.log(scoreValue);
    console.log(ivalue);
    // if(id='musc'){
    //
    // }

    let temoIdScore = id + +ivalue;
		var radioModelName;
		var indexVal;
		var checkboxId = "#" + dbRefId;
		var checkboxChecked;
		if ($(checkboxId).prop("checked") == true) {
			checkboxChecked = true;
		}
		switch (id) {
		case "MentalStatus":
			radioModelName = "mentalstatusscore";
			indexVal = "1";
			var lastMentalStatusValue = this.statusScore.mentalstatusscore;
			this.statusScore.mentalstatusscore = scoreValue;
			if (this.scoreBindObj.mentalstatus === null) {
				this.scoreBindObj.mentalstatus = dbRefId;
			} else {
				if (lastMentalStatusValue != this.statusScore.mentalstatusscore) {
					this.scoreBindObj.mentalstatus = dbRefId;
				} else {
					this.scoreBindObj.mentalstatus = this.scoreBindObj.mentalstatus + "," + dbRefId;
				}
			}
			for(var i=0;i<this.bindStructure[0].scoreList.length;i++){
				if(this.bindStructure[0].scoreList[i].scoreValue != scoreValue){
					for(var j=0;j<this.bindStructure[0].scoreList[i].signList.length;j++){
						this.bindStructure[0].scoreList[i].signList[j].selected = false;
					}
				}
			}
			console.log(this.scoreBindObj.mentalstatus);
			break;
		case "MuscleTone":
			radioModelName = "muscletonescore";
			indexVal = "2";
			var lastMuscleToneValue = this.statusScore.muscletonescore;
			this.statusScore.muscletonescore = scoreValue;
			if (this.scoreBindObj.muscletone === null) {
				this.scoreBindObj.muscletone = dbRefId;
			} else {
				if (lastMuscleToneValue != this.statusScore.muscletonescore) {
					this.scoreBindObj.muscletone = dbRefId;
				} else {
					this.scoreBindObj.muscletone = this.scoreBindObj.muscletone + "," + dbRefId;
				}
			}
			for(var i=0;i<this.bindStructure[1].scoreList.length;i++){
				if(this.bindStructure[1].scoreList[i].scoreValue != scoreValue){
					for(var j=0;j<this.bindStructure[1].scoreList[i].signList.length;j++){
						this.bindStructure[1].scoreList[i].signList[j].selected = false;
					}
				}
			}
			break;
		case "Crypattern":
			radioModelName = "crypatternscore";
			indexVal = "3";
			var lastCryPatternValue = this.statusScore.crypatternscore;
			this.statusScore.crypatternscore = scoreValue;
			if (this.scoreBindObj.crypattern === null) {
				this.scoreBindObj.crypattern = dbRefId;
			} else {
				if (lastCryPatternValue != this.statusScore.crypatternscore) {
					this.scoreBindObj.crypattern = dbRefId;
				} else {
					this.scoreBindObj.crypattern = this.scoreBindObj.crypattern + "," + dbRefId;
				}
			}
			for(var i=0;i<this.bindStructure[2].scoreList.length;i++){
				if(this.bindStructure[2].scoreList[i].scoreValue != scoreValue){
					for(var j=0;j<this.bindStructure[2].scoreList[i].signList.length;j++){
						this.bindStructure[2].scoreList[i].signList[j].selected = false;
					}
				}
			}
			break;
		case "Oculomotororeyemovements":
			radioModelName = "oculomotorscore";
			indexVal = "4";
			var lastOculoMotorValue = this.statusScore.oculomotorscore;
			this.statusScore.oculomotorscore = scoreValue;
			if (this.scoreBindObj.oculomotor === null) {
				this.scoreBindObj.oculomotor = dbRefId;
			} else {
				if (lastOculoMotorValue != this.statusScore.oculomotorscore) {
					this.scoreBindObj.oculomotor = dbRefId;
				} else {
					this.scoreBindObj.oculomotor = this.scoreBindObj.oculomotor + "," + dbRefId;
				}
			}
			for(var i=0;i<this.bindStructure[3].scoreList.length;i++){
				if(this.bindStructure[3].scoreList[i].scoreValue != scoreValue){
					for(var j=0;j<this.bindStructure[3].scoreList[i].signList.length;j++){
						this.bindStructure[3].scoreList[i].signList[j].selected = false;
					}
				}
			}
			break;
		}
		var radioRefId;
		radioRefId = id + indexVal + scoreValue;
		console.log(radioRefId);

    // let radiobtn = document.getElementById(radioRefId);
    // radiobtn.checked = "checked";

		this.scoreBindObj.mentalstatusscore = this.statusScore.mentalstatusscore;
		this.scoreBindObj.muscletonescore = this.statusScore.muscletonescore;
		this.scoreBindObj.crypatternscore = this.statusScore.crypatternscore;
		this.scoreBindObj.oculomotorscore = this.statusScore.oculomotorscore;
		this.localStatusScore = this.statusScore;
		this.scoreBindObj.bindscore = this.scoreBindObj.mentalstatusscore + this.scoreBindObj.muscletonescore + this.scoreBindObj.crypatternscore + this.scoreBindObj.oculomotorscore;

	};

	/*
	 * End of method for change of checkbox values
	 */

	checkingBind = function(){
		console.log("checking downes");
		var bindScoreValid = true;
		if(this.scoreBindObj.mentalstatusscore == null || this.scoreBindObj.mentalstatusscore == undefined){
			bindScoreValid = false;
		}
		else if(this.scoreBindObj.muscletonescore == null || this.scoreBindObj.muscletonescore == undefined){
			bindScoreValid = false;
		}
		else if(this.scoreBindObj.crypatternscore == null || this.scoreBindObj.crypatternscore == undefined){
			bindScoreValid = false;
		}
		else if(this.scoreBindObj.oculomotorscore == null || this.scoreBindObj.oculomotorscore == undefined){
			bindScoreValid = false;
		}
		return bindScoreValid;
	}
	/*
	 * Function for saving Bind score
	 */

	saveBindScorefunctionlity = function(){
		if(this.checkingBind()){
			$("#bindNotValid").css("display","none");
			this.saveBindScore = true;
			console.log(this.scoreBindObj.bindscore);
			$("#bindScorePopup").removeClass("showing");
			$("#scoresOverlay").removeClass("show");
			this.creatingProgressNotesTemplate();
		}
		else{
			$("#bindNotValid").css("display","inline-block");
			this.saveBindScore = false;
		}
	}

  setBindScoreWithJaundice = function() {
		console.log("in save bind score");
		console.log(this.scoreBindObj);
		this.scoreBindObj.uhid = this.childObject.uhid;

    try
    {
      this.http.post(this.apiData.setBind,
        this.scoreBindObj).subscribe(res => {
          this.bindResponse =res.json();
          console.log("after saving response");
					this.statusJaundice.jaundice.bindscoreid = this.bindResponse.returnedObject.bindscoreid;
					console.log("bindscoreid: " + this.statusJaundice.jaundice.bindscoreid);
					this.saveJaundice();
					this.getBindStructure(false);
        },
        err => {
          console.log("Error occured.")
        }

      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};
	/*
	 * End of function for saving Bind score
	 */
	/*
	 * This is the code for bind score popup
	 */
	showBind = function() {
		if(this.noBindScoreAvailable) {
      this.warningMessage = "No Bind Score saved for this Jaundice Assessment!";
      this.showWarningPopUp();
			//this.showModal("No Bind Score saved for this Jaundice Assessment!","info");
		} else {
			$("#bindScorePopup").addClass("showing");
			$("#scoresOverlay").addClass("show");
		}
	};
	closeBind = function(){
		$("#bindScorePopup").removeClass("showing");
		$("#scoresOverlay").removeClass("show");
		this.saveBindScore = false;
		this.getBindStructure(true);
		this.creatingProgressNotesTemplate();
	};
//		End of the code of bind score
//		below code is for OK cancel popup
	showOkPopUp = function() {
		$("#OkCancelPopUp").addClass("showing");
		$("#scoresOverlay").addClass("show");
	};
	closeOkPopUp = function(){
		$("#OkCancelPopUp").removeClass("showing");
		$("#scoresOverlay").removeClass("show");
	};
	//		below code is for OK cancel popup
	showMedicationPopUp = function() {
		$("#medicationPopup").addClass("showing");
		$("#MedicationOverlay").addClass("show");
	};
	closeMedicationPopUp = function(){
		$("#medicationPopup").removeClass("showing");
		$("#MedicationOverlay").removeClass("show");
	};

	submitInactive = function() {
		this.statusJaundice.jaundice.jaundicestatus = "Inactive";
		this.submitJaundice();
		this.closeOkPopUp();
	};
	printJaundiceData = function(fromDateTable : Date, toDateTable : Date){
	this.fromDateTableNull = false;
  	this.toDateTableNull = false;
  	this.fromToTableError = false;
  	this.printDataObj.dateFrom = fromDateTable;
  	this.printDataObj.dateTo= toDateTable;
    this.printDataObj.uhid = this.childObject.uhid;
	this.printDataObj.whichTab = "Jaundice";
  	if(fromDateTable == null) {
    	this.fromDateTableNull = true;
	} else if(toDateTable == null) {
    	this.toDateTableNull = true;
  	} else if(fromDateTable >= toDateTable) {
    	this.fromToTableError = true;
  	} else {
    	var data = [];
    	for(var i=0; i < this.statusJaundice.listJaundice.length; i++) {
      	var item = this.statusJaundice.listJaundice[i];
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
//		below code is used for the modal popup of order investigation
	closeModalOrderInvestigation = function() {
		this.investOrderNotOrdered = [];
		for(var obj in this.statusJaundice.dropDowns.testsList){
			for(var index = 0; index<this.statusJaundice.dropDowns.testsList[obj].length;index++){
				if(this.statusJaundice.dropDowns.testsList[obj][index].isSelected==true){
					var testName = this.statusJaundice.dropDowns.testsList[obj][index].testname;

					this.investOrderNotOrdered.push(testName);
					if(this.investOrderSelected.indexOf(testName) == -1)
						this.statusJaundice.dropDowns.testsList[obj][index].isSelected = false;

				} else {
					var testName = this.statusJaundice.dropDowns.testsList[obj][index].testname;
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		console.log(this.investOrderNotOrdered);

		console.log("closeModalOrderInvestigation closing");
		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");
	};
	showOrderInvestigation = function(){
		this.jaundiceDropDownValue = "Jaundice Panel";
		this.selectedDecease = "Jaundice Panel";

		for(var obj in this.statusJaundice.dropDowns.testsList){
			for(var index = 0; index<this.statusJaundice.dropDowns.testsList[obj].length;index++){
				var testName = this.statusJaundice.dropDowns.testsList[obj][index].testname;
				if(this.investOrderNotOrdered.indexOf(testName) != -1)
					this.statusJaundice.dropDowns.testsList[obj][index].isSelected = true;
			}
		}

    this.listTestsByCategory = this.statusJaundice.dropDowns.testsList[this.jaundiceDropDownValue];
		/*if(this.orderSelectedText==null || this.orderSelectedText==''){
			if(this.jaundiceDropDownValue=='Jaundice Panel'){
				for(var index=0; index<this.listTestsByCategory.length;index++){
					this.listTestsByCategory[index].isSelected = true;
				}
			}
		}*/
		console.log("showOrderInvestigation initiated");
		$("#ballardOverlay").css("display", "block");
		$("#OrderInvestigationPopup").addClass("showing");
	}

	orderSelected = function(){
		// iterate ovver list for order list of selected...
		console.log("dsfasfdsf");
		this.orderSelectedText = "";
		this.investOrderSelected = [];
		for(var obj in this.statusJaundice.dropDowns.testsList){
			console.log(this.statusJaundice.dropDowns.testsList[obj]);
			for(var index = 0; index<this.statusJaundice.dropDowns.testsList[obj].length;index++){
				if(this.statusJaundice.dropDowns.testsList[obj][index].isSelected==true){
					if(this.orderSelectedText==''){
						this.orderSelectedText =  this.statusJaundice.dropDowns.testsList[obj][index].testname;
					}else{
						this.orderSelectedText = this.orderSelectedText +", "+ this.statusJaundice.dropDowns.testsList[obj][index].testname;
					}
          this.statusJaundice.orderSelectedText = this.orderSelectedText;

					this.investOrderSelected.push(this.statusJaundice.dropDowns.testsList[obj][index].testname);
				} else {
					var testName = this.statusJaundice.dropDowns.testsList[obj][index].testname;
					this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		console.log(this.investOrderSelected);
		this.creatingProgressNotesTemplate();
		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");

	}

//		end of the above code
	populateTestsListByCategory = function(assessmentCategory){

		this.selectedDecease = assessmentCategory;
		console.log(assessmentCategory);
		this.listTestsByCategory = this.statusJaundice.dropDowns.testsList[assessmentCategory];
		console.log( this.listTestsByCategory);
	}
	// add or remove treatmentType based on flag
	treatmentValue = function(treatmentType, flag){
		this.symptomsArr = this.statusJaundice.jaundice.actiontypeList;
		if(flag == true)
		{
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(treatmentType);
			}else{
				if(this.symptomsArr.includes(treatmentType)){

				}
				else
				{
					// find pos to insert the treatment
					this.symptomsArr.push(treatmentType);
				}
			}
			if(this.symptomsArr==null){
				this.statusJaundice.jaundice.actiontypeList = [];
			}else{
				this.statusJaundice.jaundice.actiontypeList = this.symptomsArr;
			}
			this.totalBindScore="";
		}else if(flag == false){
			// var symptomsArr = this.statusJaundice.jaundice.actiontypeList;
			if(this.symptomsArr==null)
				this.symptomsArr = [];
			else if(this.symptomsArr.includes(treatmentType))
			{
				this.symptomsArr.pop(treatmentType);
			}
		}
		this.statusJaundice.jaundice.actiontypeList = this.symptomsArr;

	}
	saveTreatment = function(treatmentType){
		switch(treatmentType)
		{
		case 'phototherapy':
			this.treatmentValue('TRE001',true);
			this.statusJaundice.jaundice.phototherapy = true;
			this.isPhototherapySelect = false;
			this.isPhototherapySaved = true;
			break;
		case 'exchTransfusion':
			this.treatmentValue('TRE002',true);
			this.isExchangeTransfusionSelect = false;
			this.isExTransfusionSaved = true;
			break;
		case 'ivig':
			this.treatmentValue('TRE003',true)
			this.isivigSelect = false;
			this.isivigSaved = true;
			break;
		case 'others':
			this.isOthersSelect = false;
			this.isOthersSaved = true;
			this.treatmentValue('others', true);
			break;

		}

		this.creatingProgressNotesTemplate();
	}
	clearTreatment = function(treatmentType){

		switch(treatmentType)
		{
		case 'TRE001':
			if(this.statusJaundice.listJaundice==null || this.statusJaundice.listJaundice.length<=0){
				this.statusJaundice.jaundice.phototherapyvalue= null;
			}else{
				this.statusJaundice.jaundice.phototherapyvalue= this.statusJaundice.listJaundice[0].phototherapyvalue;
			}

			break;
		case 'TRE002':
			if(this.statusJaundice.listJaundice==null || this.statusJaundice.listJaundice.length<=0){
				this.statusJaundice.jaundice.exchangetrans= null;
			}else{
				this.statusJaundice.jaundice.exchangetrans = this.statusJaundice.listJaundice[0].exchangetrans;
			}
			break;
		case 'TRE003':
			this.statusJaundice.jaundice.ivigvalue = null;
			break;
		case 'others':
			this.statusJaundice.jaundice.otheractiontype = null;
			break;

		}

		for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
			if(treatmentType == this.symptomsArr[i]){
				this.statusJaundice.jaundice.actiontypeList.splice(i,1);
			}
		}
		this.creatingProgressNotesTemplate() ;
	}

	// continue previous treatment values
	updateTreatment = function(updateType) {


		var previousTreatment = this.statusJaundice.listJaundice[0];
		this.statusJaundice.jaundice.actiontype = previousTreatment.actiontype;

		if(this.statusJaundice.jaundice.actiontype != null && this.statusJaundice.jaundice.actiontype != undefined && this.statusJaundice.jaundice.actiontype != ""){

			this.statusJaundice.jaundice.actiontypeList = this.statusJaundice.jaundice.actiontype.replace("]","").replace("[","").replace(" ","").replace("others,","").split(",");
			if(this.jundiceTempObject.jaundiceTreatmentStatus=='continue'){


				// phototherapy continue...
				if(this.statusJaundice.jaundice.actiontype.includes("TRE001") && previousTreatment.phototherapyvalue!=null){
					if(previousTreatment.phototherapyvalue=="Start" || previousTreatment.phototherapyvalue=="Continue"){
						this.statusJaundice.jaundice.phototherapyvalue = "Continue";
					}else{
						this.statusJaundice.jaundice.phototherapyvalue = null;// if
						// prev
						// is
						// stop
						// then
						// make
						// it
						// null.
						for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
							if(this.statusJaundice.jaundice.actiontypeList[i]=="TRE001"){
								this.statusJaundice.jaundice.actiontypeList.splice(i,1);
							}
						}
					}
				}

				if(this.statusJaundice.jaundice.actiontype.includes("TRE002") && previousTreatment.exchangetrans!=null){
					if(previousTreatment.exchangetrans==true){
						this.statusJaundice.jaundice.exchangetrans = false;
					}else{
						this.statusJaundice.jaundice.exchangetrans = null;// exchange
						// transfusion
						// stopped...
						for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
							if(this.statusJaundice.jaundice.actiontypeList[i]=="TRE002"){
								this.statusJaundice.jaundice.actiontypeList.splice(i,1);
							}
						}
					}
				}
				if(this.statusJaundice.jaundice.actiontype.includes("TRE003")){
					if(this.statusJaundice.jaundice.ivigvalue !=null && this.statusJaundice.jaundice.ivigvalue != ""){
						this.statusJaundice.jaundice.ivigvalue = previousTreatment.ivigvalue;
					}else{
						this.statusJaundice.jaundice.ivigvalue = null;
						for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
							if(this.statusJaundice.jaundice.actiontypeList[i]=="TRE003"){
								this.statusJaundice.jaundice.actiontypeList.splice(i,1);
							}
						}
					}

				}
			}else if(this.jundiceTempObject.jaundiceTreatmentStatus=='change'){
				if(this.statusJaundice.jaundice.actiontype.includes("TRE001")){
					if(this.statusJaundice.jaundice.phototherapyvalue=='Stop'){
						this.statusJaundice.jaundice.phototherapyvalue = null;
						for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
							if(this.statusJaundice.jaundice.actiontypeList[i]=="TRE001"){
								this.statusJaundice.jaundice.actiontypeList.splice(i,1);
							}
						}
					}else{
						this.statusJaundice.jaundice.phototherapyvalue = previousTreatment.phototherapyvalue;
					}
				}

				if(this.statusJaundice.jaundice.actiontype.includes("TRE002")){
					if(this.statusJaundice.jaundice.exchangetrans==true){
						this.statusJaundice.jaundice.exchangetrans = previousTreatment.exchangetrans;
					}else{
						this.statusJaundice.jaundice.exchangetrans = null;
						for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
							if(this.statusJaundice.jaundice.actiontypeList[i]=="TRE002"){
								this.statusJaundice.jaundice.actiontypeList.splice(i,1);
							}
						}
					}

				}
				if(this.statusJaundice.jaundice.actiontype.includes("TRE003")){
					if(this.statusJaundice.jaundice.ivigvalue !=null && this.statusJaundice.jaundice.ivigvalue != ""){
						this.statusJaundice.jaundice.ivigvalue = previousTreatment.ivigvalue;
					}else{
						this.statusJaundice.jaundice.ivigvalue = null;
						for(var i=0;i< this.statusJaundice.jaundice.actiontypeList.length;i++){
							if(this.statusJaundice.jaundice.actiontypeList[i]=="TRE003"){
								this.statusJaundice.jaundice.actiontypeList.splice(i,1);
							}
						}
					}

				}
			}

		}
		this.creatingProgressNotesTemplate();

	}

	// set new values for treatment
	changeTreatment = function() {
		this.jaundiceTreatmentStatus.value = 'Change';
	}

  populateMultiCheckBox = function(id) {
    var expanded = false;
		console.log(id);
		var fields = id.split('-');
		var name = fields[2];
		console.log(name);
		var checkboxContId = "#checkboxes-"+ name;
		console.log(checkboxContId);
		var width = $("#multiple-selectbox-1").width();
		if(width == null || width == 0) {
			width = $("#multiple-selectbox-"+name).width() - 2;
		}
		if (!expanded) {
			$(checkboxContId).toggleClass("show");
			// checkboxes.style.display = "block";
			$(checkboxContId).width(width);
			console.log(width);
			expanded = true;
		} else {
			$(checkboxContId).removeClass("show");
			expanded = false;
		}
	}
	onCheckPlanMultiCheckBoxValue = function(keyValue,multiCheckBoxId){

		this.symptomsArr = [];
		if(this.statusJaundice.jaundice.treatmentactionplanlist!=null)
			this.symptomsArr = this.statusJaundice.jaundice.treatmentactionplanlist ;

		if(keyValue == 'Reassess'){
			if(!this.symptomsArr.includes('Reassess')){
				this.symptomsArr.push('Reassess');
			}
			else{
				this.symptomsArr.pop('Reassess');
				this.statusJaundice.jaundice.actionduration = null;
				this.statusJaundice.jaundice.isactiondurationinhours = null;
			}
		}else if(keyValue == 'Investigation'){
			if(!this.symptomsArr.includes('Investigation')){
				this.symptomsArr.push('Investigation');
			}
			else{
				this.symptomsArr.pop('Investigation');
			}
		}else if(keyValue == 'Others'){
			if(!this.symptomsArr.includes('Others')){
				this.symptomsArr.push('Others');
			}else{
				this.symptomsArr.pop('Others');
				this.statusJaundice.jaundice.treatmentOther = null;
			}
		}
    this.creatingProgressNotesTemplate();
		this.statusJaundice.jaundice.treatmentactionplanlist = this.symptomsArr;
	}

  dateformatter = function(date : Date, inFormat : string, outFormat :string){
    if(inFormat == "gmt"){
      if (outFormat == "utf")
      {
        let tzoffset : any = (new Date(date)).getTimezoneOffset() * 60000; //offset in milliseconds
				let localISOTime : any = (new Date(new Date(date).getTime() - tzoffset)).toISOString().slice(0,-1);
				localISOTime = localISOTime+"Z";
				return localISOTime;
      }
      else if(outFormat == "standard"){
        let newdate = new Date(date);
        let dd : any = newdate.getDate();
        let mm : any = newdate.getMonth();
        mm = mm+1;
        let yy = newdate.getFullYear();
        if ( dd < 10 ){
          dd = '0'+dd;
        }
        if ( mm < 10 ){
          mm = '0'+mm;
        }
        return yy+"-"+mm+"-"+dd ;
      }
    }else if(inFormat == "utf"){
  				if (outFormat == "gmt")
  				{
  					var fullDate = new Date(date);
  					fullDate = new Date(fullDate.getTime() + (fullDate.getTimezoneOffset() * 60000));
  					return fullDate;
  				}
      }
  }

  showModal = function(message, type) {
    if(type){
    if (type=='success'){
      $("#modal-message-icon").addClass("sprite sprite-success");
    }
    else if (type=='failure'){
      $("#modal-message-icon").addClass("sprite sprite-failure");
    }
    else{
      $("#modal-message-icon").addClass("sprite sprite-info");
    }
    }
      $("#defaultOverlay").css("display", "block");
      $("#defaultModal").addClass("showing");
      document.getElementById('modalMessage').innerHTML = message;
          $('html,body').animate({
              scrollTop: 0
          }, 300);
  };
  closeModal = function() {
  $("#modal-message-icon").className = '';
    $("#defaultOverlay").css("display", "none");
    $("#defaultModal").removeClass("showing");
    document.getElementById('modalMessage').innerHTML = '';
  };

  showWarningPopUp() {
		$("#OkWarningPopUp").addClass("showing");
		$("#warningOverlay").addClass("show");
	};

	closeWarningPopUp(){
    this.warningMessage = "";
		$("#OkWarningPopUp").removeClass("showing");
		$("#warningOverlay").removeClass("show");
	};

  toggleAll = function(event) {
    var bool = true;
    if (event.target.checked) {
      bool = false;
    }
    this.checked = !bool;
    this.selectAll = !bool;

  }

  stopAllMed(selectAll : any) {
    if(this.selectAll == null || this.selectAll == false){
      this.selectAll = true;
    }
    else{
      this.selectAll = false;
    }

    for(var index=0;index<this.pastPrescriptionList.length;index++){
      this.toggleIscontinue(index);
      if(this.selectAll == true){
        this.pastPrescriptionList[index].isStopped = true;
        this.pastPrescriptionList[index].enddate = new Date();
      }
      else{
        this.pastPrescriptionList[index].isStopped = false;
        this.pastPrescriptionList[index].enddate = null;
      }
    }
  }

  setEndDate(index : any){
    if(this.pastPrescriptionList[index].isStopped == null || this.pastPrescriptionList[index].isStopped == false){
      this.pastPrescriptionList[index].isStopped = true;
    }
    else{
      this.pastPrescriptionList[index].isStopped = false;
    }
    this.updateEndDate(index);
  }

  updateEndDate(index : any){
    this.toggleIscontinue(index);
    if(this.pastPrescriptionList[index].isStopped == true){
      this.pastPrescriptionList[index].enddate = new Date();
    }
    else{
      this.pastPrescriptionList[index].enddate = null;
    }
  }

  commentBoxEnable(index : any){
    this.pastPrescriptionList[index].isContinue = true;
    this.pastPrescriptionList[index].isStopped = false;
    this.pastPrescriptionList[index].enddate = null;
    this.selectAll = false;
  }

  toggleIscontinue(index : any){
    this.pastPrescriptionList[index].isContinue = false;
    this.pastPrescriptionList[index].continueReason = "";
  }

  saveMedicine (){
    for(var i = 0; i < this.pastPrescriptionList.length; i++) {
      var obj = this.pastPrescriptionList[i];
      if(obj.eventname == 'Jaundice') {
        if(obj.isactive == true && obj.enddate != null){
          obj.enddate = new Date(obj.enddate);
          obj.isactive = false;
          this.statusJaundice.prescriptionList.push(obj);
        }
        else if(obj.isactive == true && obj.isContinue != null && obj.isContinue == true && obj.continueReason != null && obj.continueReason != ''){
          obj.eventname = "Stable Notes";
          this.statusJaundice.prescriptionList.push(obj);
        }
      }
    }
    this.closeMedicationPopUp();
  }
  ngOnInit() {

  }

}
