import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery/dist/jquery.min.js';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import { InfectSystemObj } from './infectSystemObj';
import { InfectionTempObj } from './infectionTempObj';
import { PrintDataObj } from './printDataObj';
import { SepsisTempObj } from './sepsisTempObj';
import { SepsisTreatmentStatus } from './sepsisTreatmentStatus';
import { VapTempObj } from './vapTempObj';
import { ClabsiTempObj } from './clabsiTempObj';
import { AssessmentTempObj } from './assessmentTempObj';
import { AppComponent } from '../app.component';
import { Symptomatic } from './symptomatic';

@Component({
  selector: 'assessment-sheet-infection',
  templateUrl: './assessment-sheet-infection.component.html',
  styleUrls: ['./assessment-sheet-infection.component.css']
})
export class AssessmentSheetInfectionComponent implements OnInit {
 	infectSystemObj: InfectSystemObj;
	infectionTempObj : InfectionTempObj;
	assessmentTempObj : AssessmentTempObj;
	sepsisTreatmentStatus : SepsisTreatmentStatus;
	sepsisTempObj : SepsisTempObj;
  warningMessage : string;
	uhid : string;
  router : Router;
  tabContentGuideline : string;
	infectionWhichTab : string;
	isclinicalSectionSepsisVisible : boolean;
	isActionSepsisVisible : boolean;
	isPlanSepsisVisible : boolean;
	isProgressSepsisVisible : boolean;
	TableSepsisFlag : boolean;
	noBindScoreAvailable : boolean;
	expanded : boolean;
	loggedUser : string;
	hourDaySepsisDiff : number;
	inactiveMessageText : string;
	inactiveModule : string;
	apiData : APIurl;
	http: Http;
	isAntibioticsSelect: boolean;
	treatmentTabsVisible : boolean;
	isAntibioticsSaved : boolean;
  isMedicationsSelect : boolean;
	isManageSeizuresSelect : boolean;
	isManageSeizuresSaved : boolean;
	isManageShockSelect : boolean;
	isManageShockSaved : boolean;
	isManageHypoglycemiaSelect : boolean;
	isManageHypoglycemiaSaved : boolean;
	isRdsSelect : boolean;
	isRdsSaved : boolean;
	isTempSelect : boolean;
	isTempSaved : boolean;
	isOtherSelect : boolean;
	isOtherSaved : boolean;
	isCauseSepsisVisible : boolean;
	earlyLateOnset : string;
	ageOnsetPreviousExceed : boolean;
	prescription : any;
	treatmentactionList : any;
	totalCyanosis : number;
	totalRetractions : number;
	totalGrunting : number;
	totalAirEntry : number;
	totalRespiratoryRate : number;
	downesValueCalculated : number;
	selectedDecease : string;
	investOrderSelected : Array<any>;
	investOrderNotOrdered : Array<any>;
	bellscoreid : number;
	localStatusScore : any;
	bindscoreid : string;
	totalBindScore : string;
	saveBellScore : boolean;
	respBell : any;
	sepsisHourFlag : boolean;
	hourDayDiffSepsis : number;
	vapPastTreatmentSelected : Array<any>;
	hourDayVapDiff : number;
	isclinicalSectionVapVisible : boolean;
	isActionVapVisible : boolean;
	isPlanVapVisible : boolean;
	isProgressVapVisible : boolean;
	vapTempObj : VapTempObj;
	clabsiTempObj : ClabsiTempObj;
	vanishSubmitResponseVariable : boolean;
	responseType : string;
	responseMessage : string;
	clabsiHourFlag : boolean;
	hourDayDiffClabsi : number;
	clabsiPastTreatmentSelected : Array<any>;
	isclinicalSectionClabsiVisible : boolean;
	isActionClabsiVisible : boolean;
	isPlanClabsiVisible : boolean;
	isProgressClabsiVisible : boolean;
	whichGuidelineVisible : string;
  hourDayDiffAgeAtAssessmentPneumo : any;
  ageAtAssessmentPneumoHourFlag : boolean;
  childObject : any;
  workingWeight : any;
  fromDateTableNull : boolean;
  toDateTableNull : boolean;
  fromToTableError : boolean;
  printDataObj : PrintDataObj;
  minDate : string;
  maxDate : Date;
  isLoaderVisible : boolean;
  loaderContent : string;
  pastAgeInactiveSepsis : boolean;
  pastPrescriptionList : any;
  currentEvent : string;
  selectAll : any;
  currentDate : Date;
 	constructor(http: Http, router: Router) {
 		this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
 		this.printDataObj = new PrintDataObj();
 		this.apiData = new APIurl();
  	this.http = http;
    this.router = router;
  	this.infectionWhichTab = '';
  	this.bellscoreid = 0;
    this.currentDate = new Date();
  	this.infectSystemObj = new InfectSystemObj();
  	this.infectionTempObj = new InfectionTempObj();
  	this.assessmentTempObj = new AssessmentTempObj();
  	this.sepsisTempObj = new SepsisTempObj();
  	this.sepsisTreatmentStatus = new SepsisTreatmentStatus();
  	this.isclinicalSectionSepsisVisible = true;
  	this.TableSepsisFlag = false;
  	this.expanded = false;
  	this.isActionSepsisVisible = false;
  	this.isPlanSepsisVisible = false;
  	this.isProgressSepsisVisible = true;
  	this.ageOnsetPreviousExceed = false;
  	this.inactiveMessageText = '';
	  this.inactiveModule = '';
	  this.treatmentTabsVisible = true;
	  this.isCauseSepsisVisible = false;
  	this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.workingWeight = (this.childObject.workingWeight / 1000);
		this.totalCyanosis = 0;
		this.totalRetractions = 0;
		this.totalGrunting = 0;
		this.totalAirEntry = 0;
		this.totalRespiratoryRate = 0;
		this.downesValueCalculated = 0;
		this.investOrderSelected = [];
    this.warningMessage = "";
		this.investOrderNotOrdered = [];
    this.ageAtAssessmentPneumoHourFlag = false;
		this.sepsisTempObj.bellStageObj.bindStructure = [];
    this.selectAll = null;
		this.sepsisTempObj.bellStageObj.statusScore = {
				"systemsignscore": null,
				"intestinalsignscore": null,
				"radiologicalsignscore": null,
				"treatmentscore": null
		};
		this.localStatusScore = {
				"systemsignscore": null,
				"intestinalsignscore": null,
				"radiologicalsignscore": null,
				"treatmentscore": null
		};
		this.bellscoreid = 0;
		this.bindscoreid = '';
		this.totalBindScore= '';
		this.saveBellScore = false;
		this.vapPastTreatmentSelected = [];
		this.isclinicalSectionVapVisible = true;
		this.isActionVapVisible = false;
		this.isPlanVapVisible = false;
		this.isProgressVapVisible = true;
		this.vapTempObj = new VapTempObj();
		this.clabsiTempObj = new ClabsiTempObj();
		this.clabsiHourFlag = false;
		this.clabsiPastTreatmentSelected = [];
		this.isclinicalSectionClabsiVisible = true;
		this.isActionClabsiVisible = false;
		this.isPlanClabsiVisible = false;
		this.isProgressClabsiVisible = true;
    this.hourDayDiffAgeAtAssessmentPneumo = 0;
    this.pastPrescriptionList = [];
    this.currentEvent = 'Sepsis';
	}
	scrollingMainSepsis(){
		$('html,body').animate({ scrollTop: $(".sepsis-Screen").offset().top},'slow');
	}
	scrollSepsis(){
		$('html,body').animate({ scrollTop: $(".Sepsis-section").offset().top},'slow');
	}
	clinicalSectionSepsisVisible(){
		this.isclinicalSectionSepsisVisible = !this.isclinicalSectionSepsisVisible ;
	};
	switchEventSepsis(){
		this.TableSepsisFlag = !this.TableSepsisFlag;
	}
	changeAbdStatus(){
		if(!this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.abdominalStatus){
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.necStatus = null;
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.abdominalValue = null;
			this.saveBellScore = false;
			this.getBellStructure(true);
			this.sepsisTempObj.bellStageObj.scoreTempTotal=null;
		}
		this.progressNotesSepsis();
	};
	changeNecStatus = function(){
		if(!this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.necStatus){
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.abdominalValue = null;

			this.saveBellScore = false;
			this.getBellStructure(true);
			this.sepsisTempObj.bellStageObj.scoreTempTotal=null;
		}

		this.progressNotesSepsis();
	}
	infectionTabVisible(tabValue){
		console.log(tabValue);
		if(tabValue == "sepsis"){
			this.infectionWhichTab = tabValue;
			this.infectSystemObj.systemStatus = 'yes';
			this.infectSystemObj.infectionEventObject.eventName =  tabValue;
		}
		if(tabValue == "vap"){
			this.infectionWhichTab = tabValue;
			this.infectSystemObj.systemStatus = 'yes';
			this.infectSystemObj.infectionEventObject.eventName =  tabValue;
		}
		if(tabValue == "clabsi"){
			this.infectionWhichTab = tabValue;
			this.infectSystemObj.systemStatus = 'yes';
			this.infectSystemObj.infectionEventObject.eventName =  tabValue;
		}
		if(tabValue == "intrauterine"){
			this.infectionWhichTab = tabValue;
		}
	};
	calAgeOnsetSepsis(){
		if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays){
			if(this.hourDaySepsisDiff == null || this.hourDaySepsisDiff == undefined){
				this.hourDaySepsisDiff = 0;
			}
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset *= 24;
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset += this.hourDaySepsisDiff;
		}else if(!(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays)){
			this.hourDaySepsisDiff = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset%24;
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset -= this.hourDaySepsisDiff;
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset /= 24;
		}
		this.progressNotesSepsis();
	};
	ageOnAssessmentCalculationSepsis = function(){
		if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isageofassesmentinhours && this.sepsisHourFlag){

				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment *= 24;
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment += this.hourDayDiffSepsis;
				this.sepsisHourFlag = false;
			}else if(!(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isageofassesmentinhours || this.sepsisHourFlag)){
				this.sepsisHourFlag = true;
				this.hourDayDiffSepsis = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment%24;
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment -= this.hourDayDiffSepsis;
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment /= 24;
			}
	};
	sepsisOtherRiskfactor = function(){
		var symptomsArr = [];
		if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList!=null){
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList.includes('other')){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther!=null){
					this.sepsisTempObj.selectedRiskStr = "";
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList !=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList != ''){
						symptomsArr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList;
					}
					for(var indexList = 0; indexList<symptomsArr.length;indexList++){
						for(var indexCause=0;indexCause<this.infectSystemObj.dropDowns.riskFactorSepsis.length;indexCause++){
							if(symptomsArr[indexList]== this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].key){
								if(symptomsArr[indexList]=="other"){
									if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther!=''){
										if(this.sepsisTempObj.selectedRiskStr==''){
											this.sepsisTempObj.selectedRiskStr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther;
										}else{
											this.sepsisTempObj.selectedRiskStr +=", " + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther;
										}
									}
								} else {
									if(this.sepsisTempObj.selectedRiskStr==''){
										this.sepsisTempObj.selectedRiskStr = this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].value;
									}else{
										this.sepsisTempObj.selectedRiskStr +=", " + this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].value;
									}
								}
							}
						}
					}
				}
			}
		}
		this.progressNotesSepsis();
	};
	createSymptomaticTextSepsis =  function(){
		this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticValue = "";
		var tempSymptomaticStr = "";
		console.log("1");
		if(this.sepsisTempObj.symptomatic!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticStatus==true){
			console.log(this.sepsisTempObj.symptomatic);
			if(this.sepsisTempObj.symptomatic.Lethargy==true){
				tempSymptomaticStr = "Lethargy";
			}

			if(this.sepsisTempObj.symptomatic.RefusaltoFeeds==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Refusal to Feeds";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Refusal to Feeds";
			}

			if(this.sepsisTempObj.symptomatic.LooseMotions==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Loose Motions";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Loose Motions";
			}

			if(this.sepsisTempObj.symptomatic.Apnea==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Apnea";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Apnea";
			}

			if(this.sepsisTempObj.symptomatic.RespiratoryDistress==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Respiratory Distress";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Respiratory Distress";


				if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.downesscore!=null
						&& this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.downesscore!=''){
					tempSymptomaticStr+=" with Downe's score of "+this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.downesscore;
				}
			}

			if(this.sepsisTempObj.symptomatic.Pneumonia==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Pneumonia";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Pneumonia";
			}

			if(this.sepsisTempObj.symptomatic.Drowsiness==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Drowsiness";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Drowsiness";
			}

			if(this.sepsisTempObj.symptomatic.Seizures==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Seizures";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Seizures";
			}

			if(this.sepsisTempObj.symptomatic.Others==true && this.sepsisTempObj.symptomatic.OthersValue != null && this.sepsisTempObj.symptomatic.OthersValue != ''){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = this.sepsisTempObj.symptomatic.OthersValue;
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " + this.sepsisTempObj.symptomatic.OthersValue;
			}

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticValue = tempSymptomaticStr;
		} else {
			console.log('2');
			this.sepsisTempObj.symptomatic = new Symptomatic();
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticValue = null;
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther = null;
		}

		this.progressNotesSepsis();
	};
	okCancelPopupSymptomaticSepsis = function(symptom, url){
		if(symptom == 'apneaSymptomaticSepsisEvent'){
			console.log(this.sepsisTempObj.symptomatic);
			if(this.sepsisTempObj.symptomatic.Apnea == true) {
				this.showOkPopUp(symptom, url);
			} else {
				this.sepsisTempObj.symptomatic.Apnea = false;
			}
		} else if(symptom == 'seizuresTreatmentSepsisEvent'){
			this.showOkPopUp(symptom, url);
		} else if(symptom == 'hypoglycemiaTreatmentSepsisEvent'){
			this.showOkPopUp(symptom, url);
		} else if(symptom == 'rdsTreatmentSepsisEvent'){
			this.showOkPopUp(symptom, url);
		}
		this.createSymptomaticTextSepsis();
	};
	showOkPopUp = function(module,inactiveMessage) {
		console.log("its ok");
		//symptomatic selected text ....
		this.createSymptomaticTextSepsis();
		this.inactiveMessageText = inactiveMessage;
		this.inactiveModule = module;
		$("#OkCancelPopUp").addClass("showing");
		$("#scoresOverlay").addClass("show");
	};
	closeOkPopUp = function(){
		$("#OkCancelPopUp").removeClass("showing");
		$("#scoresOverlay").removeClass("show");
	};
	//below code is for the Downe Score
	downesScore = function() {
		console.log("downes initiated");
		$("#downesOverlay").css("display", "block");
		$("#downesScorePopup").addClass("showing");
	};
	closeModalDownes = function(){
		if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeFlag == false){
			$("#downesNotValid").css("display","none");
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.cynosis = null;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.retractions = null;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.grunting = null;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.downesscore = null;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.airentry = null;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.respiratoryrate = null;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeFlag = false;
		}
		else{
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeFlag = true;
		}
		console.log("downes closing");
		$("#downesOverlay").css("display", "none");
		$("#downesScorePopup").toggleClass("showing");
	};
	closeModalDownesOnSave = function(){
		this.checkValidDownes();
		if(this.checkValidDownes()){
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeFlag = true;
			$("#downesNotValid").css("display","none");
			$("#downesOverlay").css("display", "none");
			$("#downesScorePopup").toggleClass("showing");
			this.createSymptomaticTextSepsis();
		}
		else{
			$("#downesNotValid").css("display","inline-block");
		}
	};
	checkValidDownes = function(){
		console.log("checking downes");
		var downesScoreValid = true;
		if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.cynosis == null || this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.cynosis == undefined){
			downesScoreValid = false;
		}
		else if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.retractions == null || this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.retractions == undefined){
			downesScoreValid = false;
		}
		else if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.grunting == null || this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.grunting == undefined){
			downesScoreValid = false;
		}
		else if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.airentry == null || this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.airentry == undefined){
			downesScoreValid = false;
		}
		else if(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.respiratoryrate == null || this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.respiratoryrate == undefined){
			downesScoreValid = false;
		}
		return downesScoreValid;
	};
  //end of the above code for downe

	getInfectionData =  function(){
		try{
       this.http.post(this.apiData.setDataInfection + this.uhid +'/' + this.loggedUser ,
       JSON.stringify({
       someParameter: 1,
       someOtherParameter: 2
       })).subscribe(res => {
         this.handleInfectionData(res.json());
       },
       err => {
         console.log("Error occured.")
       });
    }
    catch(e){
       console.log("Exception in http service:"+e);
    };
	}
	handleInfectionData = function(responseData){
	  	this.infectionTempObj.printFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
    	this.infectionTempObj.printTo = new Date();
	  	console.log(responseData);
	  	this.infectSystemObj = responseData;
      this.assessmentTempObj = this.indcreaseEpisodeNumber(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory);
	  	console.log(this.infectSystemObj);
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime = new Date();

      this.pastPrescriptionList = [];
      if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList != null && this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList.length; i++){
          if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList[i].eventname == 'Sepsis'){
            var startDate = new Date(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList[i].medicineOrderDate);
            var medContiuationDayCount = Math.abs(Math.ceil((this.currentDate.getTime() - startDate.getTime())/(1000*60*60*24)));
            if(medContiuationDayCount == 0){
              medContiuationDayCount = 1;
            }
            this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList[i].medicineDay = medContiuationDayCount;
            this.pastPrescriptionList.push(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList[i]);
          }
        }
        this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList = [];
      }

	  	if(localStorage.getItem('admissionform') != null){
  			this.infectSystemObj.systemStatus = 'Yes';
  			localStorage.setItem('redirectBackToAdmission',JSON.stringify(true));
  			localStorage.removeItem('admissionform');
  			var eventName = JSON.parse(localStorage.getItem('eventName'));
  			console.log(eventName);
  			if(eventName === "Sepsis") {
  				this.infectionTabVisible('sepsis');
  				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus = 'yes';
  			} else if (eventName === "VAP") {
  				this.infectionTabVisible('vap');
  				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.eventstatus = 'yes';
  			} else if (eventName === "CLABSI") {
  				this.infectionTabVisible('clabsi');
  				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.eventstatus = 'yes';
  			}
		  }

      if(localStorage.getItem('isComingFromInfectionSymptomaticEvent')!=null){
				var isComingFromInfectionSymptomaticEvent = JSON.parse(localStorage.getItem('isComingFromInfectionSymptomaticEvent'));
				if(isComingFromInfectionSymptomaticEvent){
					//for Sepsis
					if(JSON.parse(localStorage.getItem('isComingFromSepsis'))){
						this.setSepsisRedirectVariable();
					}
					this.initTreatmentRedirectSepsis();
				}
				localStorage.removeItem("isComingFromInfectionSymptomaticEvent");
			} else if(localStorage.getItem('isComingFromPrescription')!=null){
				var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromPrescription'));
				if(isComingFromPrescription){
					this.setPresRedirectVariable();
				}
				this.initTreatmentRedirectSepsis();
				localStorage.removeItem("isComingFromPrescription");
			} else if(localStorage.getItem('isComingFromCnsSeizures')!=null){
				var isComingFromCnsSeizures = JSON.parse(localStorage.getItem('isComingFromCnsSeizures'));
				if(isComingFromCnsSeizures){
					this.setSepsisRedirectVariable();
				}
				this.initTreatmentRedirectSepsis();
				localStorage.removeItem("isComingFromCnsSeizures");
				this.saveTreatmentSepsis('manageSeizures');
			} else if(localStorage.getItem('isComingFromMetaHypoglycemia')!=null){
				var isComingFromMetaHypoglycemia = JSON.parse(localStorage.getItem('isComingFromMetaHypoglycemia'));
				if(isComingFromMetaHypoglycemia){
					this.setSepsisRedirectVariable();
				}
				this.initTreatmentRedirectSepsis();
				localStorage.removeItem("isComingFromMetaHypoglycemia");
				this.saveTreatmentSepsis('manageHypoglycemia');
			} else if(localStorage.getItem('isComingFromRespRds')!=null){
				var isComingFromRespRds = JSON.parse(localStorage.getItem('isComingFromRespRds'));
				if(isComingFromRespRds){
					this.setSepsisRedirectVariable();
				}
				this.initTreatmentRedirectSepsis();
				localStorage.removeItem("isComingFromRespRds");
				this.saveTreatmentSepsis('rds');
			} else {
				this.initTreatmentVariablesSepsis();
			}
	  	this.progressNotesSepsis();
	  	this.progressNotesVap();
      this.progressNotesClabsi();

      //Sepsis age at onset enable/disable
      if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null){
        if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.eventstatus=='inactive'){
          this.pastAgeInactiveSepsis = false;
        }
        else {
          this.pastAgeInactiveSepsis = true;
        }
      }
      else {
       this.pastAgeInactiveSepsis = false;
      }

			this.creatingPastSelectedTextSepsis();
			this.setTreatmentSelectedTextSepsis();
      this.creatingPastSelectedTextVap();
			this.creatingPastSelectedTextClabsi();
	}

  //To Populate the vital params when date is changed
  populateVitalInfoByDate(){
    try{
      this.http.request(this.apiData.getVitalInfoByDate + "/" + this.uhid +"/"+ this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime,)
      .subscribe(res => {
        this.handleVitalInfoByDate(res.json());
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

  //To handle the response from getVitalInfoByDate API
  handleVitalInfoByDate = function(response : any){
    console.log(response);
    if(response.hrRate != null && response.hrRate != undefined){
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.heartrate = response.hrRate;
    }
    else{
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.heartrate = "";
    }

    if(response.systBp != null && response.systBp != undefined){
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockSystbp = response.systBp;
    }
    else{
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockSystbp = "";
    }

    if(response.diastBp != null && response.diastBp != undefined){
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockDiastbp = response.diastBp;
    }
    else{
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockDiastbp = "";
    }

    if(response.meanBp != null && response.meanBp != undefined){
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockBp = response.meanBp;
    }
    else{
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockBp = "";
    }
 }

  initTreatmentVariablesSepsis = function(){
			//init all tabs status
		this.isAntibioticsSelect = false;
		this.isAntibioticsSaved = false;

		this.isManageSeizuresSelect = false;
		this.isManageSeizuresSaved = false;

		this.isManageShockSelect = false;
		this.isManageShockSaved = false;

		this.isManageHypoglycemiaSelect = false;
		this.isManageHypoglycemiaSaved = false;

		this.isRdsSelect = false;
		this.isRdsSaved = false;

		this.isTempSelect = false;
		this.isTempSaved = false;

		this.isOtherSelect = false;
		this.isOtherSaved = false;

        this.isMedicationsSelect = false;

		this.treatmentTabsVisible = true;

		if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null){
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.treatmentaction!=null){
				this.treatmentVisiblePastSepsis = true;
				this.treatmentTabsVisible = false;
			}else{
				this.treatmentVisiblePastSepsis = false;
				this.treatmentTabsVisible = true;
			}
		} else {
			this.treatmentTabsVisible = true;
			this.treatmentVisiblePastSepsis = false;
		}

		this.progressNotesSepsis();
	}

  creatingPastSelectedTextSepsis() {
    if(!this.isEmpty(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj)){
      var pastSepsis = this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj;
      if(pastSepsis.symptomaticValue!=null && pastSepsis.symptomaticValue!=''){
        this.sepsisTempObj.pastSelectedSymptomaticStr = pastSepsis.symptomaticValue;
      }

      if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastInvestigationsList!=null
          && this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastInvestigationsList.length>0){
        for(var indexOrder = 0; indexOrder<this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastInvestigationsList.length;indexOrder++){
          if(this.sepsisTempObj.pastSelectedOrderInvestigationStr=='' || this.sepsisTempObj.pastSelectedOrderInvestigationStr==undefined){
            this.sepsisTempObj.pastSelectedOrderInvestigationStr = this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastInvestigationsList[indexOrder].testname;
          }else{
            this.sepsisTempObj.pastSelectedOrderInvestigationStr += ", " + this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastInvestigationsList[indexOrder].testname;
          }
        }
      }

      if(pastSepsis.treatmentaction!=null && pastSepsis.treatmentaction!=''){
        if(pastSepsis.treatmentaction.includes('TRE081')){
          this.sepsisTempObj.pastSelectedTreatmentStr = "Maintain temperature";
        }

        if(pastSepsis.treatmentaction.includes('TRE082')){
          if(this.sepsisTempObj.pastSelectedTreatmentStr=='' || this.sepsisTempObj.pastSelectedTreatmentStr==undefined){
            this.sepsisTempObj.pastSelectedTreatmentStr= "Manage respiratory distress";
          }else{
            this.sepsisTempObj.pastSelectedTreatmentStr += ", Manage respiratory distress";
          }
        }

        if(pastSepsis.treatmentaction.includes('TRE083')){
          if(this.sepsisTempObj.pastSelectedTreatmentStr=='' || this.sepsisTempObj.pastSelectedTreatmentStr==undefined){
            this.sepsisTempObj.pastSelectedTreatmentStr= "Manage hypoglycemia";
          }else{
            this.sepsisTempObj.pastSelectedTreatmentStr += ", Manage hypoglycemia";
          }
        }

        if(pastSepsis.treatmentaction.includes('TRE084')){
          if(this.sepsisTempObj.pastSelectedTreatmentStr=='' || this.sepsisTempObj.pastSelectedTreatmentStr==undefined){
            this.sepsisTempObj.pastSelectedTreatmentStr= "Manage shock";
          }else{
            this.sepsisTempObj.pastSelectedTreatmentStr += ", Manage shock";
          }
        }

        if(pastSepsis.treatmentaction.includes('TRE085')){
          if(this.sepsisTempObj.pastSelectedTreatmentStr=='' || this.sepsisTempObj.pastSelectedTreatmentStr==undefined){
            this.sepsisTempObj.pastSelectedTreatmentStr= "Manage seizures";
          }else{
            this.sepsisTempObj.pastSelectedTreatmentStr += ", Manage seizures";
          }
        }

        if(pastSepsis.treatmentaction.includes('other')){
          if(this.sepsisTempObj.pastSelectedTreatmentStr=='' || this.sepsisTempObj.pastSelectedTreatmentStr==undefined){
            this.sepsisTempObj.pastSelectedTreatmentStr= pastSepsis.treatmentOther;
          }else{
            this.sepsisTempObj.pastSelectedTreatmentStr += ", "+ pastSepsis.treatmentOther;
          }
        }
      }

			if(pastSepsis.medicationStr != null && pastSepsis.medicationStr != '') {
        if(this.sepsisTempObj.pastSelectedTreatmentStr==undefined || this.sepsisTempObj.pastSelectedTreatmentStr == ''){
          this.sepsisTempObj.pastSelectedTreatmentStr= pastSepsis.medicationStr;
        }else{
          this.sepsisTempObj.pastSelectedTreatmentStr += ", " + pastSepsis.medicationStr;
        }
			}

      if(pastSepsis.causeofsepsis!=null && pastSepsis.causeofsepsis!=''){
        this.sepsisTempObj.pastSelectedCauseStr = "";
        var list = pastSepsis.causeofsepsis.replace("[","").replace("]","").split(",");
        for(var indexCause = 0; indexCause<list.length;indexCause++){
          for(var indexDrop = 0; indexDrop<this.infectSystemObj.dropDowns.causeOfSepsis.length;indexDrop++){
            if(list[indexCause].replace(" ","")==this.infectSystemObj.dropDowns.causeOfSepsis[indexDrop].key){
              if(this.sepsisTempObj.pastSelectedCauseStr=='' || this.sepsisTempObj.pastSelectedCauseStr==undefined){
                this.sepsisTempObj.pastSelectedCauseStr = this.infectSystemObj.dropDowns.causeOfSepsis[indexDrop].value;
              }else{
                this.sepsisTempObj.pastSelectedCauseStr += ", "+ this.infectSystemObj.dropDowns.causeOfSepsis[indexDrop].value;
              }
            }
          }
        }
      }
    }
  }

	setPresRedirectVariable = function(){
		var infectionSystemObj = JSON.parse(localStorage.getItem('currentInfectionSystemObj'));
		if(infectionSystemObj != null){
			var prescriptionList = JSON.parse(localStorage.getItem('prescriptionList'));
			var sepsisTempObj = JSON.parse(localStorage.getItem('currentInfectionSepsisObj'));
			this.infectSystemObj = infectionSystemObj;
			if(sepsisTempObj != null){
				this.sepsisTempObj = sepsisTempObj;
			}

			this.infectSystemObj.infectionEventObject.sepsisEvent.prescriptionList =  prescriptionList;

      var medStr = "";
      var medStartStr = "";
      var medStopStr = "";
      var medContStr = "";
      var medRevStr = "";

      for(var index=0; index < prescriptionList.length; index++) {
        var medicine = prescriptionList[index];
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

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr = medStr;
			this.infectionTabVisible('sepsis');
			this.actionSepsisVisible();
			this.medicationsSelect();
		}

		localStorage.removeItem('prescriptionList');
		localStorage.removeItem("currentInfectionSystemObj");
		localStorage.removeItem("currentInfectionSepsisObj");
	}

  initTreatmentRedirectSepsis = function(){

			this.treatmentVisiblePastSepsis = true;
			this.treatmentTabsVisible = true;
			this.sepsisTreatmentStatus.value = 'Change';

			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != null
					&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length > 0) {
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE081')) {
					this.isTempSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE082')) {
					this.isRdsSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE083')) {
					this.isManageHypoglycemiaSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE084')) {
					this.isManageShockSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE085')) {
					this.isManageSeizuresSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE086')) {
					this.isAntibioticsSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('other')) {
					this.isOtherSelect = true;
					this.isOtherSaved = true;
				}
			}

			if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.treatmentaction!=null){
					this.treatmentVisiblePastSepsis = true;
				}else{
					this.treatmentVisiblePastSepsis = false;
				}
			} else {
				this.treatmentTabsVisible = true;
				this.treatmentVisiblePastSepsis = false;
			}

			this.progressNotesSepsis();
		}

  setSepsisRedirectVariable = function(){
			this.infectionTabVisible('sepsis');
			var currentInfectSepsisObject = JSON.parse(localStorage.getItem('currentInfectSepsisObject'));
			var sepsisTempObj = JSON.parse(localStorage.getItem('sepsisTempObj'));
			if(currentInfectSepsisObject!=null){
				this.infectSystemObj = currentInfectSepsisObject;
				this.sepsisTempObj = sepsisTempObj;

				this.isActionSepsisVisible = true;
			}
			localStorage.removeItem("isComingFromSepsis");
			localStorage.removeItem("currentInfectSepsisObject");
			localStorage.removeItem("sepsisTempObj");
	}

  changeTreatmentSepsis = function() {
			this.sepsisTreatmentStatus.value = 'Change';
			this.treatmentTabsVisible = true;
			this.sepsisTempObj.selectedTreatmentStr = '';

			if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=undefined){

				var pastSepsisObj =  this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj;

				if(pastSepsisObj.treatmentaction!=null && pastSepsisObj.treatmentaction!=''){

					var prevTreatmentList = pastSepsisObj.treatmentaction.replace("[","").replace("]","").replace(" ","").split(",");

					var symptomArr = [];

					for(var index = 0; index<prevTreatmentList.length; index++){
						if(prevTreatmentList[index].trim()!='' && prevTreatmentList[index].trim()=="TRE082"){
							symptomArr.push(prevTreatmentList[index].trim());
						}

						if(prevTreatmentList[index].trim()!='' && prevTreatmentList[index].trim()=="TRE083"){
							symptomArr.push(prevTreatmentList[index].trim());
						}

						if(prevTreatmentList[index].trim()!='' && prevTreatmentList[index].trim()=="TRE085"){
							symptomArr.push(prevTreatmentList[index].trim());
						}
					}
				}

				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList = symptomArr;

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length > 0
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes("TRE082")){
					this.isRdsSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length > 0
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes("TRE083")){
					this.isManageHypoglycemiaSaved = true;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length > 0
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes("TRE085")){
					this.isManageSeizuresSaved = true;
				}
			}

			//this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList = null;
			this.setTreatmentSelectedTextSepsis();
			this.progressNotesSepsis();
		}


	continueTreatmentSepsis = function(){
			this.treatmentTabsVisible = false;
			this.sepsisTreatmentStatus.value = 'Continue';

			var previousTreatment = this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj;

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentaction = previousTreatment.treatmentaction;
			var treatmentSepsisList = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentaction.replace("[","").replace("]","").replace(" ","").split(",");

			var symptomArr = [];

			for(var index = 0; index<treatmentSepsisList.length; index++){
				if(treatmentSepsisList[index].trim()!='' && treatmentSepsisList[index].trim()=="TRE082"){
					symptomArr.push(treatmentSepsisList[index].trim());
				}

				if(treatmentSepsisList[index].trim()!='' && treatmentSepsisList[index].trim()=="TRE083"){
					symptomArr.push(treatmentSepsisList[index].trim());
				}

				if(treatmentSepsisList[index].trim()!='' && treatmentSepsisList[index].trim()=="TRE085"){
					symptomArr.push(treatmentSepsisList[index].trim());
				}
			}

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList = symptomArr;
			this.treatmentactionList = null;

			this.isAntibioticsSelect = false;
			this.isAntibioticsSaved = false;

			this.isManageSeizuresSelect = false;
			this.isManageSeizuresSaved = false;

			this.isManageShockSelect = false;
			this.isManageShockSaved = false;

			this.isManageHypoglycemiaSelect = false;
			this.isManageHypoglycemiaSaved = false;

			this.isRdsSelect = false;
			this.isRdsSaved = false;

			this.isTempSelect = false;
			this.isTempSaved = false;

			this.isOtherSelect = false;
			this.isOtherSaved = false;

			this.setTreatmentSelectedTextSepsis();
			this.progressNotesSepsis();
	}

	setTreatmentSelectedTextSepsis = function(){
			this.sepsisTempObj.selectedTreatmentStr = "";
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null){
				for(var indexList = 0; indexList<this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length;indexList++){
					for(var indexCause=0;indexCause<this.infectSystemObj.dropDowns.treatmentActionSepsis.length;indexCause++){
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList[indexList]== this.infectSystemObj.dropDowns.treatmentActionSepsis[indexCause].key){
							if(this.infectSystemObj.dropDowns.treatmentActionSepsis[indexCause].value!="Other"){
								if(this.sepsisTempObj.selectedTreatmentStr==''){
									this.sepsisTempObj.selectedTreatmentStr = this.infectSystemObj.dropDowns.treatmentActionSepsis[indexCause].value;
								}else{
									this.sepsisTempObj.selectedTreatmentStr +=", " + this.infectSystemObj.dropDowns.treatmentActionSepsis[indexCause].value;
								}
							} else {
								if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther!=null
										&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther!=''){
									if(this.sepsisTempObj.selectedTreatmentStr==''){
										this.sepsisTempObj.selectedTreatmentStr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther;
									}else{
										this.sepsisTempObj.selectedTreatmentStr +=", " + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther;
									}
								}
							}
						}
					}
				}
			}

      var medicationStr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr;
      if(medicationStr != null && medicationStr != '') {
        if(this.sepsisTempObj.selectedTreatmentStr==''){
          this.sepsisTempObj.selectedTreatmentStr = medicationStr;
        }else{
          this.sepsisTempObj.selectedTreatmentStr +=", " + medicationStr;
        }
      }
		}
	   antibioticsSelect = function(){
			this.isAntibioticsSelect = true;
			this.isManageSeizuresSelect = false;
			this.isManageShockSelect = false;
			this.isManageHypoglycemiaSelect = false;
			this.isRdsSelect = false;
			this.isTempSelect = false;
			this.isOtherSelect = false;
            this.isMedicationsSelect = false;
		}
        medicationsSelect = function(){

            this.isAntibioticsSelect = false;
			this.isManageSeizuresSelect = false;
			this.isManageShockSelect = false;
			this.isManageHypoglycemiaSelect = false;
			this.isRdsSelect = false;
			this.isTempSelect = false;
			this.isOtherSelect = false;
		  this.isMedicationsSelect = true;
        }
        manageSeizuresSelect = function(){
			this.isAntibioticsSelect = false;
			this.isManageSeizuresSelect = true;
			this.isManageShockSelect = false;
			this.isManageHypoglycemiaSelect = false;
			this.isRdsSelect = false;
			this.isTempSelect = false;
			this.isOtherSelect = false;
            this.isMedicationsSelect = false;
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList == null
					|| !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE085')){
				this.okCancelPopupSymptomaticSepsis('seizuresTreatmentSepsisEvent','Do you want to take Seizures assessment');
			}

		}
		manageHypoglycemiaSelect = function(){
			this.isAntibioticsSelect = false;
			this.isManageSeizuresSelect = false;
			this.isManageShockSelect = false;
			this.isManageHypoglycemiaSelect = true;
			this.isRdsSelect = false;
			this.isTempSelect = false;
			this.isOtherSelect = false;
            this.isMedicationsSelect = false;
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList == null
					|| !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE083')){
				this.okCancelPopupSymptomaticSepsis('hypoglycemiaTreatmentSepsisEvent','Do you want to take Hypoglycemia assessment');
			}

		}
		rdsSelect = function(){
			this.isAntibioticsSelect = false;
			this.isManageSeizuresSelect = false;
			this.isManageShockSelect = false;
			this.isManageHypoglycemiaSelect = false;
			this.isRdsSelect = true;
			this.isTempSelect = false;
			this.isOtherSelect = false;
            this.isMedicationsSelect = false;
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList == null
					|| !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE082')){
				this.okCancelPopupSymptomaticSepsis('rdsTreatmentSepsisEvent','Do you want to take Respiratory Distress assessment');
			}
		}
		otherSelect = function(){
			this.isAntibioticsSelect = false;
			this.isManageSeizuresSelect = false;
			this.isManageShockSelect = false;
			this.isManageHypoglycemiaSelect = false;
			this.isRdsSelect = false;
			this.isTempSelect = false;
			this.isOtherSelect = true;
            this.isMedicationsSelect = false;
		}
		populateMultiCheckBox = function(id) {
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
			if (!this.expanded) {
				$(checkboxContId).toggleClass("show");
				// checkboxes.style.display = "block";
				$(checkboxContId).width(width);
				console.log(width);
				this.expanded = true;
			} else {
				$(checkboxContId).removeClass("show");
				this.expanded = false;
			}
		}

		onCheckMultiCheckBoxValueSepsis = function(keyValue,multiCheckBoxId,flagValue){
			var symptomsArr = [];
			if(multiCheckBoxId=="treatmentActionSepsis"){

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList != ''){
					symptomsArr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList;
				}
				var flag = true;
				if(symptomsArr.length == 0){
					symptomsArr.push(keyValue);
				}
				else{
					for(var i=0;i< symptomsArr.length;i++){
						if(keyValue == symptomsArr[i]){
							symptomsArr.splice(i,1);
							flag = false;
						}
					}
					if(flag == true){
						symptomsArr.push(keyValue);
					}
				}
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList = symptomsArr;
				this.setTreatmentSelectedTextSepsis();
			} else if(multiCheckBoxId=="antibiotics"){
				if(this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
						&& this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){
					symptomsArr = this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList;
				}

				if(symptomsArr.length == 0){
					for(var indexMed = 0; indexMed<this.infectSystemObj.dropDowns.medicine.length;indexMed++){
						if(this.infectSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
							var selectedMedObj = this.infectSystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.infectSystemObj.infectionEventObject.commonEventsInfo.babyPrescriptionEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							prescEmptyObj.flag = true;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					if(flagValue==true){
						for(var indexMed = 0; indexMed<this.infectSystemObj.dropDowns.medicine.length;indexMed++){
							if(this.infectSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
								var selectedMedObj = this.infectSystemObj.dropDowns.medicine[indexMed];
								var prescEmptyObj = Object.assign({},this.infectSystemObj.infectionEventObject.commonEventsInfo.babyPrescriptionEmptyObj);
								prescEmptyObj.comments = selectedMedObj.description;
								prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
								prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
								prescEmptyObj.medicinename = selectedMedObj.medname;
								prescEmptyObj.medid = selectedMedObj.medid;
								symptomsArr.push(prescEmptyObj);
							}
						}
					}else{
						for(var i=0;i<symptomsArr.length;i++){
							if(keyValue == symptomsArr[i].medid){
								symptomsArr.splice(i,1);
							}
						}
					}
				}
				this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList = symptomsArr;
				this.saveTreatmentSepsis('antibiotics');

			} else if(multiCheckBoxId=="planOfSepsis"){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentplanList != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentplanList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentplanList != ''){
					symptomsArr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentplanList;
				}
				var flag = true;
				if(symptomsArr.length == 0){
					symptomsArr.push(keyValue);
				}
				else{
					for(var i=0;i< symptomsArr.length;i++){
						if(keyValue == symptomsArr[i]){
							symptomsArr.splice(i,1);
							flag = false;
						}
					}
					if(flag == true){
						symptomsArr.push(keyValue);
					}
				}

				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentplanList = symptomsArr;
			} else if(multiCheckBoxId=="causeOfSepsis"){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisList != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisList != ''){
					symptomsArr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisList;
				}
				var flag = true;
				if(symptomsArr.length == 0){
					symptomsArr.push(keyValue);
				}
				else{
					for(var i=0;i< symptomsArr.length;i++){
						if(keyValue == symptomsArr[i]){
							symptomsArr.splice(i,1);
							flag = false;
						}
					}
					if(flag == true){
						symptomsArr.push(keyValue);
					}
				}

				this.sepsisTempObj.selectedCauseStr = "";
				for(var indexList = 0; indexList<symptomsArr.length;indexList++){
					for(var indexCause=0;indexCause<this.infectSystemObj.dropDowns.causeOfSepsis.length;indexCause++){
						if(symptomsArr[indexList]== this.infectSystemObj.dropDowns.causeOfSepsis[indexCause].key){
							if(this.infectSystemObj.dropDowns.causeOfSepsis[indexCause].value!="Other"){
								if(this.sepsisTempObj.selectedCauseStr==''){
									this.sepsisTempObj.selectedCauseStr = this.infectSystemObj.dropDowns.causeOfSepsis[indexCause].value;
								}else{
									this.sepsisTempObj.selectedCauseStr = this.sepsisTempObj.selectedCauseStr += ", " + this.infectSystemObj.dropDowns.causeOfSepsis[indexCause].value;
								}
							} else {
								if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther!=null
										&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther!=''){
									if(this.sepsisTempObj.selectedCauseStr==''){
										this.sepsisTempObj.selectedCauseStr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther;
									}else{
										this.sepsisTempObj.selectedCauseStr +=", " + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther;
									}
								}
							}
						}
					}
				}
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisList = symptomsArr;
			} else if(multiCheckBoxId=="riskFactor"){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList != ''){
					symptomsArr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList;
				}
				var flag = true;
				if(symptomsArr.length == 0){
					symptomsArr.push(keyValue);
				}
				else{
					for(var i=0;i< symptomsArr.length;i++){
						if(keyValue == symptomsArr[i]){
							symptomsArr.splice(i,1);
							flag = false;
						}
					}
					if(flag == true){
						symptomsArr.push(keyValue);
					}
				}

				this.sepsisTempObj.selectedRiskStr = "";
				for(var indexList = 0; indexList<symptomsArr.length;indexList++){
					for(var indexCause=0;indexCause<this.infectSystemObj.dropDowns.riskFactorSepsis.length;indexCause++){
						if(symptomsArr[indexList]== this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].key){
							if(this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].value!="Other"){
								if(this.sepsisTempObj.selectedRiskStr==''){
									this.sepsisTempObj.selectedRiskStr = this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].value;
								}else{
									this.sepsisTempObj.selectedRiskStr += ", " + this.infectSystemObj.dropDowns.riskFactorSepsis[indexCause].value;
								}
							} else {
								if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther!=null
										&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther!=''){
									if(this.sepsisTempObj.selectedRiskStr==''){
										this.sepsisTempObj.selectedRiskStr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther;
									}else{
										this.sepsisTempObj.selectedRiskStr +=", " + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorOther;
									}
								}
							}
						}
					}
				}
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList = symptomsArr;

				console.log(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.riskfactorList);
			} else if(multiCheckBoxId=="temperatures"){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList != ''){
					symptomsArr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList;
				}
				var flag = true;
				if(symptomsArr.length == 0){
					symptomsArr.push(keyValue);
				}
				else{
					for(var i=0;i< symptomsArr.length;i++){
						if(keyValue == symptomsArr[i]){
							symptomsArr.splice(i,1);
							flag = false;
						}
					}
					if(flag == true){
						symptomsArr.push(keyValue);
					}
				}

				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList = symptomsArr;

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList!=null
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList.includes("Hypothermia")){
					this.sepsisTempObj.isFeverDisabled = true;
					this.sepsisTempObj.isHypothermiaDisabled = false;
				} else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList!=null
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureList.includes("Fever")){
					this.sepsisTempObj.isFeverDisabled = false;
					this.sepsisTempObj.isHypothermiaDisabled = true;
				} else {
					this.sepsisTempObj.isFeverDisabled = false;
					this.sepsisTempObj.isHypothermiaDisabled = false;
				}
			}

			this.progressNotesSepsis();
		}

	submitInactive() {
		if( this.inactiveModule!=""){
			if(this.inactiveModule=="infection"){//top of the screen status
				this.submitInfection();
			} else if(this.inactiveModule=="sepsis"){
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus = "inactive";
				this.submitInfection();
			} else if(this.inactiveModule!=null && this.inactiveModule=='apneaSymptomaticSepsisEvent'){
				this.allowSymptomaticEventsSepsis('apnea','/respiratory/assessment-sheet-respiratory');
			} else if(this.inactiveModule!=null && this.inactiveModule=='seizuresTreatmentSepsisEvent'){
				this.allowSymptomaticEventsSepsis('seizures','/cns/assessment-sheet-cns');
			} else if(this.inactiveModule!=null && this.inactiveModule=='hypoglycemiaTreatmentSepsisEvent'){
				this.allowSymptomaticEventsSepsis('hypoglycemia','/metabolic/assessment-sheet-metabolic');
			} else if(this.inactiveModule!=null && this.inactiveModule=='rdsTreatmentSepsisEvent'){
				this.allowSymptomaticEventsSepsis('rds','/respiratory/assessment-sheet-respiratory');
			} else if(this.inactiveModule=="vap"){
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.eventstatus = "inactive";
				this.submitInfection();
			}
			this.closeOkPopUp();
		}
	};

	allowSymptomaticEventsSepsis(symptomaticEvent, symptomaticEventUrl){
		this.sepsisTempObj.symptomaticEvent = symptomaticEvent;
		this.sepsisTempObj.symptomaticEventUrl = symptomaticEventUrl;
		if(symptomaticEvent == 'apnea'){
			localStorage.setItem('isComingFromInfectionSymptomaticEvent',JSON.stringify(true));
		} else if(symptomaticEvent == 'seizures'){
			localStorage.setItem('isComingFromInfectionTreatment',JSON.stringify(true));
			//this.saveTreatmentSepsis("manageSeizures");
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus = 'yes';
		} else if(symptomaticEvent == 'hypoglycemia'){
			localStorage.setItem('isComingFromInfectionTreatment',JSON.stringify(true));
			//this.saveTreatmentSepsis("manageHypoglycemia");
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus = 'yes';
		} else if(symptomaticEvent == 'rds'){
			localStorage.setItem('isComingFromInfectionTreatment',JSON.stringify(true));
			//this.saveTreatmentSepsis("rds");
			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus = 'yes';
		}
		localStorage.setItem('currentInfectSepsisObject',JSON.stringify(this.infectSystemObj));
		localStorage.setItem('sepsisTempObj',JSON.stringify(this.sepsisTempObj));
		localStorage.setItem('isComingFromSepsis', JSON.stringify(true));
		this.router.navigateByUrl(symptomaticEventUrl);
	}

	redirectToPrescription = function(eventName) {
		localStorage.setItem('medicationAssessment',JSON.stringify(eventName));
		localStorage.setItem('prescriptionList',JSON.stringify(this.infectSystemObj.infectionEventObject.sepsisEvent.prescriptionList));
		localStorage.setItem('currentInfectionSystemObj',JSON.stringify(this.infectSystemObj));
		localStorage.setItem('currentInfectionSepsisObj',JSON.stringify(this.sepsisTempObj));
		this.router.navigateByUrl('/med/medications');
	}

		progressNotesSepsis = function(){

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.progressnotes= "";

			var template = "";
			if(this.infectSystemObj.ageAtOnset!=null && this.infectSystemObj.ageAtOnset!=""){

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays){
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset<72)
						this.earlyLateOnset = "Early Onset";
					else
						this.earlyLateOnset = "Late Onset";
				} else{
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset<3)
						this.earlyLateOnset = "Early Onset";
					else
						this.earlyLateOnset = "Late Onset";
				}


				if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null){

					if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.eventstatus=='inactive'){

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.episodeNumber != null){
							template += "Episode number: " + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.episodeNumber + ". ";
						}

						template += "Baby suspected to have sepsis at the age of ";
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays){
							if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 ==0){
								template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset + " hr. ";
							}else{
								template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset + " hrs "+"("+this.earlyLateOnset+")";
							}
						}else{
							if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 ==0){
								template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset + " day. ";
							}else{
								template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset +" days "+"("+this.earlyLateOnset+")";
							}
						}
					} else {
						template += "Baby continued to have sepsis";
					}
				} else {
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.episodeNumber != null){
						template += "Episode number: " + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.episodeNumber + ". ";
					}
					template += "Baby suspected to have sepsis at the age of ";
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays){
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 ==0){
							template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset + " hr. ";
						}else{
							template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset + " hrs "+"("+this.earlyLateOnset+")";
						}
					}else{
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset*1 ==0){
							template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset + " day. ";
						}else{
							template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset +" days "+"("+this.earlyLateOnset+")";
						}
					}
				}

				// sypmtomatic events
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticStatus == true){
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticValue!=null){
						template = template + " with symptoms of ";
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticStatus!=""){
							template = template + this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticValue + ". ";
						}
						else{
							template = template + ". " ;
						}
					}
				} else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.symptomaticStatus == false) {
					if(this.sepsisTempObj.selectedRiskStr!=null && this.sepsisTempObj.selectedRiskStr!=''){
						template = template + " with risk factor "+ this.sepsisTempObj.selectedRiskStr+". ";
					}
				} else {
					template = template + ". " ;
				}

				var tempText = "";
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isfeverhypo!=null){
					tempText += "Baby had";
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isfeverhypo == true){
						tempText += " fever with temperature "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureCelsius+" °C";
					} else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isfeverhypo == false){
						tempText += " hypothermia with temperature "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.temperatureCelsius+" °C";
					}
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.centralPeripheralValue!=null
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.centralPeripheralValue!=''){
					if(tempText==''){
						tempText += "Central to peripheral temperature difference is"+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.centralPeripheralValue+" °C";
					} else {
						tempText += " and Central to peripheral temperature difference "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.centralPeripheralValue+" °C";
					}
				}

				if(tempText!=''){
					template += tempText+". " ;
				}

        if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.bloodCultureStatus!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.bloodCultureStatus == "Positive"){
          template += "Blood culture is positive. ";
        }
        else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.bloodCultureStatus!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.bloodCultureStatus == "Negative"){
          template += "Blood culture is negative. ";
        }

				var shockText = "";
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shock!=null
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shock==true){
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockCft!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockCft!=''){
						if(shockText==''){
							shockText += "Shock with features of "+"CFT "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockCft+" sec";
						}
					}

					var bpText = "";
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockSystbp!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockSystbp!=''){
						if(bpText==''){
							bpText += "systolic "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockSystbp+" mmHg";
						}
					}

					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockDiastbp!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockDiastbp!=''){
						if(bpText==''){
							bpText += "diastolic "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockDiastbp+" mmHg";
						} else {
							bpText += ", "+"diastolic "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockDiastbp+" mmHg";
						}
					}

					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockBp!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockBp!=''){
						if(bpText==''){
							bpText += "mean "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockBp+" mmHg";
						} else {
							bpText += ", "+"mean "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.shockBp+" mmHg";
						}
					}

					if(shockText==''){
						if(bpText!=''){
							shockText += "Shock with features of Blood Pressure ("+bpText+")";
						}
					} else {
						if(bpText!=''){
							shockText += ", Blood Pressure ("+bpText+")";
						}
					}

					var heartRate = "";
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.heartrate!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.heartrate!=''){

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.tachycardiaStatus!=null
								&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.tachycardiaStatus==true){
							heartRate += "tachycardia (heart rate "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.heartrate+" per min)";
						} else {
							heartRate += "heart rate "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.heartrate+" per min";
						}

						if(shockText==''){
							shockText += "Shock with features of "+heartRate;
						} else {
							shockText += ", "+heartRate;
						}
					}

					var shockExtra = "";
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.poorpulses!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.poorpulses == true){
						shockExtra += " with poor pulses";
					}

					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.coldExtremities!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.coldExtremities == true){
						if(shockExtra==''){
							shockExtra += " with cold extremities";
						} else {
							shockExtra += " and cold extremities";
						}
					}

					shockText += shockExtra;
				}

				if(shockText!=''){
					template += shockText+". ";
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.abdominalStatus!=null
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.abdominalStatus==true){
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.necStatus!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.necStatus==true
							&& this.sepsisTempObj.bellStageObj.scoreTempTotal!=null
							&& this.sepsisTempObj.bellStageObj.scoreTempTotal!=''){
						template += "Abdominal distension with NEC Stage "+this.sepsisTempObj.bellStageObj.scoreTempTotal+". ";
					}
				}

				if(this.sepsisTempObj.orderSelectedText != "" && this.sepsisTempObj.orderSelectedText != undefined){
					/*if(this.sepsisTempObj.orderSelectedText.indexOf(",")== -1){
						template = template +"Investigation ordered is "+this.sepsisTempObj.orderSelectedText+". ";
					}
					else{
						template = template +"Investigation ordered are "+this.sepsisTempObj.orderSelectedText+". ";
					}*/

					template = template +"Investigation ordered : "+this.sepsisTempObj.orderSelectedText+". ";
				}

				//treatment
				var treatmentProgressNoteStr =""

					if(this.sepsisTreatmentStatus.value == 'Continue'){
						//template = template + "Treatment recommended to continue "+this.sepsisTempObj.selectedTreatmentStr+".";

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
								&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length>0){

							template +="Management of ";

							var text = "";
							if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE082')){
								text += "resipiratory distress";
							}
							if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE083')){
								if(text==''){
									text += "hypoglycemia";
								} else {
									text += ", hypoglycemia";
								}
							}
							if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE085')){
								if(text==''){
									text += "seizures";
								} else {
									text += ", seizures";
								}
							}

							template += text+" continued. ";
						}

					} else if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.length>0) || (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr != undefined && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr != '')){
						treatmentProgressNoteStr = '';
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE082')){
							if(treatmentProgressNoteStr==''){
								treatmentProgressNoteStr = "Respiratory distress";
							}
						}

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE083')){
							if(treatmentProgressNoteStr==''){
								treatmentProgressNoteStr = "Hypoglycemia";
							} else {
								treatmentProgressNoteStr+=", hypoglycemia";
							}
						}

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE084')){
							if(treatmentProgressNoteStr==''){
								treatmentProgressNoteStr = "Shock";
							} else {
								treatmentProgressNoteStr+=", shock";
							}
						}

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE085')){
							if(treatmentProgressNoteStr==''){
								treatmentProgressNoteStr = "Seizures";
							} else {
								treatmentProgressNoteStr+=", seizures";
							}
						}

						if(treatmentProgressNoteStr!=null && treatmentProgressNoteStr!=''){
							treatmentProgressNoteStr += " managed";
						}

						var antibioticStr = "";
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE086')){
							if(treatmentProgressNoteStr==''){
								antibioticStr += "Antibiotics";
							} else {
								antibioticStr+=", Antibiotics";
							}

							if(this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
									&& this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){
								antibioticStr+=" (";
								for(var index=0; index<this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length;index++){
									antibioticStr+=this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList[index].medicinename;
									if(this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList[index].dose!=null){
										antibioticStr+=" "+this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList[index].dose+" mg/dose";
									}
									if(this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList[index].route!=null){
										antibioticStr+=" with route "+this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList[index].route+", ";
									}
								}
								antibioticStr = antibioticStr.substring(0,antibioticStr.length-2);
								antibioticStr+=") given";
							}
						}
						treatmentProgressNoteStr += antibioticStr;

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr != null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr != '') {
							if(treatmentProgressNoteStr!=''){
								treatmentProgressNoteStr += ", ";
							}
							treatmentProgressNoteStr += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.medicationStr;
						}

						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('other')){
							if(treatmentProgressNoteStr==''){
								treatmentProgressNoteStr = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther;
							} else {
								treatmentProgressNoteStr+=", "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther;
							}
						}

						template += treatmentProgressNoteStr + ". ";
					}

				// Cause progress note
				if(this.infectSystemObj.dropDowns.causeOfSepsis!=null && this.infectSystemObj.dropDowns.causeOfSepsis.length>0){
					if(this.sepsisTempObj.selectedCauseStr!='' && this.sepsisTempObj.selectedCauseStr != undefined && this.sepsisTempObj.selectedCauseStr != null){

						var causeStr = this.sepsisTempObj.selectedCauseStr;
						if(causeStr.includes("other") && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther != null
								&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther != "") {
							causeStr = causeStr.replace("other", this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.causeofsepsisOther);
						}

						if(this.sepsisTempObj.selectedCauseStr.includes(",")){
							template += causeStr + " are most  likely causes of Sepsis. ";
						}
						else{
							template += causeStr + " is most  likely cause of Sepsis. ";
						}
					}
				}

				//plan seizures
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime !=null &&
						this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime!=""
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestimeType!=null){
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime == "1"){
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestimeType=='mins'){
							template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime+" min. ";
						}else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestimeType=='hours'){
							template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime+" hour. ";
						} else {
							template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime+" day. ";
						}
					}
					else{
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestimeType=='mins'){
							template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime+" mins. ";
						}else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestimeType=='hours'){
							template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime+" hours. ";
						} else {
							template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.reassestime+" days. ";
						}
					}
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.otherplanComments !=null){
					template += this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.otherplanComments + ". ";
				}

			}// end of age at onset if

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.progressnotes = template;

			//check for age onset validation
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus=='yes'){
				this.ageOnsetValidationEpisode('sepsis');
				}
			console.log(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.progressnotes);
		}
		ageOnsetValidationEpisode = function(diseaseName) {
			// show message for validation for current age at onset, less than previous inactive episode
			// diseaseName =  sepsis
			if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory.length>0){
				if(diseaseName!=null && diseaseName!=''){
					switch(diseaseName)
					{
					case 'sepsis':
						if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null)
						{
							if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.eventstatus=='inactive'){
								if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays==this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.ageinhoursdays){
									if(parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset)<=parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.ageatonset))
										this.ageOnsetPreviousExceed = true;
									else
										this.ageOnsetPreviousExceed = false;
								}
								else {
									var diff = 0;
									if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays==true && this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.ageinhoursdays==false){
										diff = parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset) - parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.ageatonset)*24;

									} else{
										diff = parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset)*24 - parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.ageatonset);
									}
									if(diff<=0)
										this.ageOnsetPreviousExceed = true;
									else
										this.ageOnsetPreviousExceed = false;
								}
							}
							else
								this.ageOnsetPreviousExceed = false;

						}
						else
							this.ageOnsetPreviousExceed = false;
						break;


					}
				}

			}
			console.log("message set"+this.ageOnsetPreviousExceed );
		}
		bolusAction = function(item) {
			if(item.bolus){
				item.freq_type =  null;
				item.frequency =  null;
			}
		}
		routeAction = function(item) {
			if(item.route != 'IV' && item.route != 'PO'){
				item.rate =  null;
			}
		}
		createMedInstruction = function(item){
			this.saveTreatmentSepsis('antibiotics');
			var dose = null;
			if(this.workingWeight != null){
				if(item.dose!=null){
					dose = item.dose * this.workingWeight;
					if(item.frequency != null
							&& item.frequency != "") {
						for(var i=0; i < this.infectSystemObj.dropDowns.freqListMedcines.length; i++) {
							var item = this.infectSystemObj.dropDowns.freqListMedcines[i];
							if(item.freqid == item.frequency){
								if(item.freqvalue != "Other") {
									dose /= item.frequency_int;
								}
							}
						}
					}
					dose = Math.round(dose * 100) / 100;
				}else{
					dose="---";
				}
			}else{
				dose = Math.round(item.dose * 100) / 100;
			}
			item.calculateddose = item.medicinename + ": " + dose + " mg/dose";
		}
		freqTypeAction = function(item) {
			if(item.freq_type == 'continuous'){
				item.rate =  null;
				item.frequency =  null;
			}
		}
		/* composePresStartTime = function (item) {
			console.log('composePresStartTime');
			if(item.startTimeObject == null) {
				item.startTimeObject = {};
			}
			if(item.startTimeObject.hours != null && item.startTimeObject.minutes != null && item.startTimeObject.meridium != null) {
				item.starttime = item.startTimeObject.hours + ":" + item.startTimeObject.minutes + " " + item.startTimeObject.meridium;
				if(item.startdate != null) {
					item.startdate.setMinutes(item.startTimeObject.minutes * 1);
					if(item.startTimeObject.meridium == 'AM') {
						if(item.startTimeObject.hours * 1 == 12) {
							item.startdate.setHours(0);
						} else {
							item.startdate.setHours(item.startTimeObject.hours * 1);
						}
					} else {
						if(item.startTimeObject.hours * 1 == 12) {
							item.startdate.setHours(12);
						} else {
							item.startdate.setHours((item.startTimeObject.hours * 1) + 12);
						}
					}
				}
			}
		} */
		switchPastMedTable = function() {
			this.otherMedTableFlag = !this.otherMedTableFlag;
		}
		getTimeStampFromNicuTimeStampFormat = function(date,hrs,min,sec,meridian){
			var timeStamp = new Date();
			if(!this.isEmpty(date)){
				timeStamp = date;
			}
			if(!this.isEmpty(hrs)){

				if(meridian=="PM"){
					if(hrs*1<12){
						hrs = hrs*1+12;

					}
				}else {
					if(hrs*1==12){
						hrs = 0;
					}
				}
				timeStamp.setHours(hrs*1);
			}

			if(!this.isEmpty(min)){
				timeStamp.setMinutes(min);
			}

			if(!this.isEmpty(sec)){
				timeStamp.setSeconds(sec);
			}

			return timeStamp;
		};

		getNicuTimeStampFromTimeStampFormat = function(timeStamp){
			var hours = timeStamp.getHours();
			var minutes = timeStamp.getMinutes();
			var seconds = timeStamp.getSeconds();
			var ampm = hours >= 12 ? 'PM' : 'AM';

			//console.log("hours "+hours+ "minutes "+minutes+"ampm "+ampm);
			hours = (hours*1) % 12;
			hours = (hours*1) ? (hours*1) : 12; // the hour '0' should be '12'

			minutes = (minutes*1) < 10 ? '0'+ minutes : minutes;
			seconds = (seconds*1) <10 ? '0' + seconds : seconds;
			var strTime = hours + ':' + minutes + ':' + ampm;

			//console.log("hours **********"+hours);
			if((hours*1)<10){
				hours = '0'+hours;
			}
			this.tempObj = {
		      "hours" : hours+'',
		      "minutes" : minutes+'',
		      "seconds" : seconds + '',
		      "meridian" : ampm,
		      "date" : timeStamp

		    };

			return this.tempObj;
		};
		navi= function(deleteBabyPrescription,actionid) {
			if(actionid == "delete"){

				if(
				deleteBabyPrescription.stopTime.date != undefined && deleteBabyPrescription.stopTime.date != null && deleteBabyPrescription.stopTime.date != '' && deleteBabyPrescription.stopTime.hours != undefined && deleteBabyPrescription.stopTime.hours != null && deleteBabyPrescription.stopTime.hours != '' && deleteBabyPrescription.stopTime.minutes != null && deleteBabyPrescription.stopTime.minutes != '' && deleteBabyPrescription.stopTime.minutes != undefined && deleteBabyPrescription.stopTime.meridium != null && deleteBabyPrescription.stopTime.meridium != undefined && deleteBabyPrescription.stopTime.meridium != ''){

				deleteBabyPrescription.enddate = this.getTimeStampFromNicuTimeStampFormat(deleteBabyPrescription.stopTime.date,deleteBabyPrescription.stopTime.hours,deleteBabyPrescription.stopTime.minutes,
							deleteBabyPrescription.stopTime.seconds,deleteBabyPrescription.stopTime.meridium);
					try
				    {
				      this.http.post(this.apiData.deleteBabyPrescription+'/'+ deleteBabyPrescription.babyPresid + '/test',
				        deleteBabyPrescription ).subscribe(res => {
				          this.prescription = res.json();;
								if(this.prescription.type=="success"){
									this.getPrescription();
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
				}else{
					for(var i=0;i<this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList.length;i++){
						if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList[i].babyPresid == deleteBabyPrescription.babyPresid){
							this.infectSystemObj.infectionEventObject.commonEventsInfo.pastPrescriptionList[i].stopMessage = "Please select medicine stop date and time";
						}
					}
				}
			}
		}
		differenceInDays = function(dateStr) {
			var oneDay = 24 * 60 * 60 * 1000;
			var firstDate = new Date();
			var secondDate = new Date(dateStr);
			var diffDays = Math.ceil(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
			return diffDays;
		}
	actionSepsisVisible = function(){
		this.isActionSepsisVisible = !this.isActionSepsisVisible ;
	};
	planSepsisVisible = function(){
		this.isPlanSepsisVisible = !this.isPlanSepsisVisible ;
	};
	progressSepsisVisible = function(){
		this.isProgressSepsisVisible = !this.isProgressSepsisVisible ;
	};
	saveTreatmentSepsis = function(treatmentType){

			switch(treatmentType)
			{
			case 'antibiotics':
				var allRequired = false;
				if(this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
						&& this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){

					var antibioticArr = this.infectSystemObj.infectionEventObject.commonEventsInfo.antibioticsBabyPrescriptionList;

					for(var index=0; index<antibioticArr.length;index++){
						if(antibioticArr[index].route!=null && antibioticArr[index].route!='' && antibioticArr[index].dose!=null && antibioticArr[index].dose!=''){
							allRequired = true;
						} else {
							allRequired = false;
							break;
						}
					}
				}

				if(allRequired){
					if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
							|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
									&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE086'))){
						this.onCheckMultiCheckBoxValueSepsis('TRE086','treatmentActionSepsis',true);
						this.isAntibioticsSaved = true;
					}
				} else {
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE086')){

						this.onCheckMultiCheckBoxValueSepsis('TRE086','treatmentActionSepsis',false);
						this.isAntibioticsSaved = false;
					}
				}

				break;
			case 'others':
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther!=null
						&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentOther!=''){

					if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
							|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
									&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('other'))){
						this.onCheckMultiCheckBoxValueSepsis('other','treatmentActionSepsis',true);
					}
					this.isOtherSaved = true;
				} else {
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
							&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('other')){

						this.onCheckMultiCheckBoxValueSepsis('other','treatmentActionSepsis',false);
						this.isOtherSelect = true;
						this.isOtherSaved = false;
					}
				}

				this.setTreatmentSelectedTextSepsis();

				break;

			case 'manageSeizures':

				if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
						|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
								&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE085'))){
					this.onCheckMultiCheckBoxValueSepsis('TRE085','treatmentActionSepsis',true);
					this.isManageSeizuresSaved = true;
				} else {
					this.onCheckMultiCheckBoxValueSepsis('TRE085','treatmentActionSepsis',false);
					this.isManageSeizuresSaved = false;
				}

				break;
			case 'manageShock':
				if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
						|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
								&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE084'))){
					this.onCheckMultiCheckBoxValueSepsis('TRE084','treatmentActionSepsis',true);
					this.isManageShockSaved = true;
				} else {
					this.onCheckMultiCheckBoxValueSepsis('TRE084','treatmentActionSepsis',false);
					this.isManageShockSaved = false;

				}
				break;
			case 'manageHypoglycemia':
				if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
						|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
								&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE083'))){
					this.onCheckMultiCheckBoxValueSepsis('TRE083','treatmentActionSepsis',true);
					this.isManageHypoglycemiaSaved = true;
				} else {
					this.onCheckMultiCheckBoxValueSepsis('TRE083','treatmentActionSepsis',false);
					this.isManageHypoglycemiaSaved = false;
				}
				break;
			case 'rds':
				if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
						|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
								&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE082'))){
					this.onCheckMultiCheckBoxValueSepsis('TRE082','treatmentActionSepsis',true);
					this.isRdsSaved = true;
				} else {
					this.onCheckMultiCheckBoxValueSepsis('TRE082','treatmentActionSepsis',false);
					this.isRdsSaved = false;
				}
				break;
			case 'temperature':
				if((this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList==null)
						|| (this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList!=null
								&& !this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList.includes('TRE081'))){
					this.onCheckMultiCheckBoxValueSepsis('TRE081','treatmentActionSepsis',true);
					this.isTempSaved = true;
				} else {
					this.onCheckMultiCheckBoxValueSepsis('TRE081','treatmentActionSepsis',false);
					this.isTempSaved = false;
				}
				break;
			}

			console.log(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.treatmentactionList);
			this.progressNotesSepsis();
		}
		causeSepsisSectionVisible = function(){
			this.isCauseSepsisVisible = !this.isCauseSepsisVisible;
		}

		totalDownesValue(downesValue){
			if(downesValue == "cyanosis"){
				this.totalCyanosis = parseInt(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.cynosis);
			}if(downesValue == "retractions"){
				this.totalRetractions = parseInt(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.retractions);
      }if(downesValue == "grunting"){
				this.totalGrunting = parseInt(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.grunting);
      }if(downesValue == "airEntry"){
				this.totalAirEntry = parseInt(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.airentry);
      }if(downesValue == "respiratoryRate"){
				this.totalRespiratoryRate = parseInt(this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.respiratoryrate);
      }
			this.downesValueCalculated = (this.totalCyanosis) + (this.totalRetractions) + (this.totalGrunting) + (this.totalAirEntry) + (this.totalRespiratoryRate) ;
			this.infectSystemObj.infectionEventObject.commonEventsInfo.downeScoreObj.downesscore = (this.downesValueCalculated);

		};

		showOrderInvestigation = function(){
			var dropDownValue = "Infection";
			this.selectedDecease = "Infection";
			for(var obj in this.infectSystemObj.dropDowns.orders){
				for(var index = 0; index<this.infectSystemObj.dropDowns.orders[obj].length;index++){
					var testName = this.infectSystemObj.dropDowns.orders[obj][index].testname;
					if(this.investOrderNotOrdered.indexOf(testName) != -1)
						this.infectSystemObj.dropDowns.orders[obj][index].isSelected = true;
				}
			}

      this.listTestsByCategory = this.infectSystemObj.dropDowns.orders[dropDownValue];
			/*if(this.sepsisTempObj.orderSelectedText == null || this.sepsisTempObj.orderSelectedText == ''){
        if(this.infectSystemObj.dropDowns.orders.Infection != undefined && this.infectSystemObj.dropDowns.orders.Infection != null){
  				for(var index=0; index<this.infectSystemObj.dropDowns.orders.Infection.length;index++){
  					this.infectSystemObj.dropDowns.orders.Infection[index].isSelected = true;
  				}
        }
			}*/
			console.log("showOrderInvestigation initiated");
			$("#ballardOverlay").css("display", "block");
			$("#OrderInvestigationPopup").addClass("showing");
		}
		closeModalOrderInvestigation = function() {
			this.investOrderNotOrdered = [];
			for(var obj in this.infectSystemObj.dropDowns.orders){
				for(var index = 0; index<this.infectSystemObj.dropDowns.orders[obj].length;index++){
					if(this.infectSystemObj.dropDowns.orders[obj][index].isSelected==true){
						var testName = this.infectSystemObj.dropDowns.orders[obj][index].testname;

						this.investOrderNotOrdered.push(testName);
						if(this.investOrderSelected.indexOf(testName) == -1)
							this.infectSystemObj.dropDowns.orders[obj][index].isSelected = false;

					} else {
						var testName = this.infectSystemObj.dropDowns.orders[obj][index].testname;
						this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
					}
				}
			}
			console.log(this.investOrderNotOrdered);
			console.log("closeModalOrderInvestigation closing");
			$("#ballardOverlay").css("display", "none");
			$("#OrderInvestigationPopup").toggleClass("showing");
		};
		orderSelected = function(){
			//iterate ovver list for order list of selected...
			console.log("dsfasfdsf");
			this.sepsisTempObj.orderSelectedText = "";
			this.investOrderSelected = [];
			for(var obj in this.infectSystemObj.dropDowns.orders){
				console.log(this.infectSystemObj.dropDowns.orders[obj]);
				for(var index = 0; index<this.infectSystemObj.dropDowns.orders[obj].length;index++){
					if(this.infectSystemObj.dropDowns.orders[obj][index].isSelected==true){
						if(this.sepsisTempObj.orderSelectedText==''){
							this.sepsisTempObj.orderSelectedText =  this.infectSystemObj.dropDowns.orders[obj][index].testname;
						}else{
							this.sepsisTempObj.orderSelectedText = this.sepsisTempObj.orderSelectedText +", "+ this.infectSystemObj.dropDowns.orders[obj][index].testname;
						}
            this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.orderSelectedText = this.sepsisTempObj.orderSelectedText;
						this.investOrderSelected.push(this.infectSystemObj.dropDowns.orders[obj][index].testname);
					} else {
						var testName = this.infectSystemObj.dropDowns.orders[obj][index].testname;
						this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
						this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
					}
				}
			}
			$("#ballardOverlay").css("display", "none");
			$("#OrderInvestigationPopup").toggleClass("showing");

			this.progressNotesSepsis();
			this.progressNotesVap();
			this.progressNotesClabsi();

		}

		populateTestsListByCategory = function(assessmentCategory){
			this.selectedDecease = assessmentCategory;
			console.log(assessmentCategory);
			this.listTestsByCategory = this.infectSystemObj.dropDowns.orders[assessmentCategory];
			console.log( this.listTestsByCategory);
		}





		showBell = function() {
			$("#bellScorePopup").addClass("showing");
			$("#bellOverlay").addClass("show");
			$("#displayBell").css("display","none");
		};
		closeBell = function(){
			if(this.sepsisTempObj.bellStageObj.saveBellScore == true){
				$("#displayBell").css("display","table-cell");
			}
			else{
				this.sepsisTempObj.bellStageObj.saveBellScore = false;
				this.getBellStructure(true);
				this.sepsisTempObj.bellStageObj.scoreTempTotal=null;
				$("#displayBell").css("display","none");
				$("#bindNotValid").css("display","none");
			}
			$("#bellScorePopup").removeClass("showing");
			$("#bellOverlay").removeClass("show");
		};

		getBellStructure(newScore) {
	  	this.noBindScoreAvailable = false;
		try{
        this.http.request(this.apiData.getBell + this.bellscoreid,)
        .subscribe(res => {
		    this.sepsisTempObj.bellStageObj.bindStructure = res.json().bellStageStatusList;
						this.sepsisTempObj.bellStageObj.scoreBindObj = res.json().scoreBellStageObj;
						for (var prop in this.sepsisTempObj.bellStageObj.statusScore) {

							this.sepsisTempObj.bellStageObj.statusScore[prop] = this.sepsisTempObj.bellStageObj.scoreBindObj[prop];
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
    Code for getting bindStructure from API
		 */
		//this.sepsisTempObj.bellStageObj.scoreTempTotal;
		scoreSelect = function(dbRefId, id, scoreValue) {
			console.log(dbRefId);
			console.log(id);
			console.log(scoreValue);

			var radioModelName;
			var indexVal;
			var checkboxId = "#" + dbRefId;
			var checkboxChecked;
			if ($(checkboxId).prop("checked") == true) {
				checkboxChecked = true;
			}
			switch (id) {
			case "SystemSign":
				radioModelName = "systemsignscore";
				indexVal = "1";
				//   console.log(this.statusScore);
				var lastMentalStatusValue = this.sepsisTempObj.bellStageObj.statusScore.systemsignscore;
				this.sepsisTempObj.bellStageObj.statusScore.systemsignscore = scoreValue;
				if (this.sepsisTempObj.bellStageObj.scoreBindObj.systemsign === null) {
					this.sepsisTempObj.bellStageObj.scoreBindObj.systemsign = dbRefId+"";
					console.log(this.sepsisTempObj.bellStageObj.scoreBindObj.systemsign);
				} else {
					if (lastMentalStatusValue != this.sepsisTempObj.bellStageObj.statusScore.systemsignscore) {
						this.sepsisTempObj.bellStageObj.scoreBindObj.systemsign = dbRefId+"";
					} else {
						this.sepsisTempObj.bellStageObj.scoreBindObj.systemsign = this.sepsisTempObj.bellStageObj.scoreBindObj.systemsign + "," + dbRefId;
					}
				}
				for(var i=0;i<this.sepsisTempObj.bellStageObj.bindStructure[0].scoreList.length;i++){
					if(this.sepsisTempObj.bellStageObj.bindStructure[0].scoreList[i].scoreValue != scoreValue){
						for(var j=0;j<this.sepsisTempObj.bellStageObj.bindStructure[0].scoreList[i].signList.length;j++){
							this.sepsisTempObj.bellStageObj.bindStructure[0].scoreList[i].signList[j].selected = false;
						}
					}
				}
				//  console.log(this.scoreBindObj.mentalstatus);
				break;
			case "IntestinalSign":
				radioModelName = "intestinalsignscore";
				indexVal = "2";
				var lastMuscleToneValue = this.sepsisTempObj.bellStageObj.statusScore.intestinalsignscore;
				this.sepsisTempObj.bellStageObj.statusScore.intestinalsignscore = scoreValue;
				if (this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsign === null) {
					this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsign = dbRefId+"";
				} else {
					if (lastMuscleToneValue != this.sepsisTempObj.bellStageObj.statusScore.intestinalsignscore) {
						this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsign = dbRefId+"";
					} else {
						this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsign = this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsign + "," + dbRefId;
					}
				}
				for(var i=0;i<this.sepsisTempObj.bellStageObj.bindStructure[1].scoreList.length;i++){
					if(this.sepsisTempObj.bellStageObj.bindStructure[1].scoreList[i].scoreValue != scoreValue){
						for(var j=0;j<this.sepsisTempObj.bellStageObj.bindStructure[1].scoreList[i].signList.length;j++){
							this.sepsisTempObj.bellStageObj.bindStructure[1].scoreList[i].signList[j].selected = false;
						}
					}
				}
				break;
			case "RadiologicalSign":
				radioModelName = "radiologicalsignscore";
				indexVal = "3";
				var lastCryPatternValue = this.sepsisTempObj.bellStageObj.statusScore.radiologicalsignscore;
				this.sepsisTempObj.bellStageObj.statusScore.radiologicalsignscore = scoreValue;
				if (this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsign === null) {
					this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsign = dbRefId+"";
				} else {
					if (lastCryPatternValue != this.sepsisTempObj.bellStageObj.statusScore.radiologicalsignscore) {
						this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsign = dbRefId+"";
					} else {
						this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsign = this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsign + "," + dbRefId;
					}
				}
				for(var i=0;i<this.sepsisTempObj.bellStageObj.bindStructure[2].scoreList.length;i++){
					if(this.sepsisTempObj.bellStageObj.bindStructure[2].scoreList[i].scoreValue != scoreValue){
						for(var j=0;j<this.sepsisTempObj.bellStageObj.bindStructure[2].scoreList[i].signList.length;j++){
							this.sepsisTempObj.bellStageObj.bindStructure[2].scoreList[i].signList[j].selected = false;
						}
					}
				}
				break;
			}

			// this.statusScore[radioModelName] = scoreValue;
			// var selected=[];
			var radioRefId;
			console.log(id);
			console.log(indexVal);
			console.log(scoreValue);
			radioRefId = id + indexVal + scoreValue;
			console.log(radioRefId);
			this.sepsisTempObj.bellStageObj.scoreBindObj.systemsignscore = this.sepsisTempObj.bellStageObj.statusScore.systemsignscore;
			this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsignscore = this.sepsisTempObj.bellStageObj.statusScore.intestinalsignscore;
			this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsignscore = this.sepsisTempObj.bellStageObj.statusScore.radiologicalsignscore;
			//this.scoreBindObj.Respirationscore = this.statusScore.Respirationscore;
			this.localStatusScore = this.sepsisTempObj.bellStageObj.statusScore;
			this.sepsisTempObj.bellStageObj.scoreTempTotal = Math.max(this.sepsisTempObj.bellStageObj.scoreBindObj.systemsignscore, this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsignscore, this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsignscore);
			if(this.sepsisTempObj.bellStageObj.scoreTempTotal == 0){
				this.sepsisTempObj.bellStageObj.scoreTempTotal ="IA";
				this.sepsisTempObj.bellStageObj.scoreBindObj.bellstagescore = 0;
			}else if(this.sepsisTempObj.bellStageObj.scoreTempTotal == 1){
				this.sepsisTempObj.bellStageObj.scoreTempTotal = "IB";
				this.sepsisTempObj.bellStageObj.scoreBindObj.bellstagescore = 1;
			}else if(this.sepsisTempObj.bellStageObj.scoreTempTotal == 2){
				this.sepsisTempObj.bellStageObj.scoreTempTotal = "IIA";
				this.sepsisTempObj.bellStageObj.scoreBindObj.bellstagescore = 2;
			}else if(this.sepsisTempObj.bellStageObj.scoreTempTotal == 3){
				this.sepsisTempObj.bellStageObj.scoreTempTotal = "IIB";
				this.sepsisTempObj.bellStageObj.scoreBindObj.bellstagescore = 3;
			}else if(this.sepsisTempObj.bellStageObj.scoreTempTotal == 4){
				this.sepsisTempObj.bellStageObj.scoreTempTotal = "IIIA";
				this.sepsisTempObj.bellStageObj.scoreBindObj.bellstagescore = 4;
			}else if(this.sepsisTempObj.bellStageObj.scoreTempTotal == 5){
				this.sepsisTempObj.bellStageObj.scoreTempTotal = "IIIB";
				this.sepsisTempObj.bellStageObj.scoreBindObj.bellstagescore = 5;
			}
		};


		/*
    End of method for change of checkbox values
		 */

		checkingBind = function(){
			var bindScoreValid = true;
			if(this.sepsisTempObj.bellStageObj.scoreBindObj.systemsignscore == null || this.sepsisTempObj.bellStageObj.scoreBindObj.systemsignscore == undefined){
				bindScoreValid = false;
			}
			else if(this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsignscore == null || this.sepsisTempObj.bellStageObj.scoreBindObj.intestinalsignscore == undefined){
				bindScoreValid = false;
			}
			else if(this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsignscore == null || this.sepsisTempObj.bellStageObj.scoreBindObj.radiologicalsignscore == undefined){
				bindScoreValid = false;
			}
			return bindScoreValid;
		}
		/*
    Function for saving Bind score
		 */

		saveBindScorefunctionlity = function(){
			this.checkingBind();
			if(this.checkingBind()){
				console.log("in if");
				$("#bindNotValid").css("display","none");
				this.sepsisTempObj.bellStageObj.saveBellScore = true;
				//console.log(this.scoreBindObj.bindscore);
				$("#bellScorePopup").removeClass("showing");
				$("#bellOverlay").removeClass("show");
				$("#displayBell").css("display","table-cell");
				//this.creatingProgressNotesTemplate();
			}
			else{
				$("#bindNotValid").css("display","inline-block");
				this.sepsisTempObj.bellStageObj.saveBellScore = false;
				$("#displayBell").css("display","none");
			}

			//console.log(this.scoreBindObj);
			this.progressNotesSepsis();
		}
		saveBellStageScore = function() {
			console.log("in save bind score");
			console.log(this.sepsisTempObj.bellStageObj.scoreBindObj);
			this.sepsisTempObj.bellStageObj.scoreBindObj.uhid = this.uhid;

			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus=='inactive'){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj==null){
					//alert("Cannot make Sepsis Inactive.");
          this.warningMessage = "Cannot make Sepsis Inactive.";
          this.showWarningPopUp();
				}
				else if((this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj!=null)&&(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj.eventstatus!='no')){
					//alert("Cannot make Sepsis Inactive. ");
          this.warningMessage = "Cannot make Sepsis Inactive. ";
          this.showWarningPopUp();
        }
				else {
					this.showOkPopUp('infection','Are you sure you want to Inactivate Sepsis Assessment');
					}
			} else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus=='no'){
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.pastEvents.pastEventObj==null){
					//alert("Cannot make Sepsis Passive. ");
          this.warningMessage = "Cannot make Sepsis Passive. ";
          this.showWarningPopUp();
				} else{
					this.submitInfection();
				}

			}
			else if(this.sepsisTempObj.bellStageObj.scoreTempTotal!=null && this.sepsisTempObj.bellStageObj.scoreTempTotal!=''){
				try
				    {
				      this.http.post(this.apiData.setBell,
				        this.sepsisTempObj.bellStageObj.scoreBindObj).subscribe(res => {
				          	this.respBell = res.json();
							this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.bellscoreid = this.respBell.returnedObject.bellstagescoreid;
							this.sepsisTempObj.bellStageObj.scoreTempTotal = null;
							this.submitInfection();
				        },

				        err => {
				          console.log("Error occured.")
				        }

				      );
				    }
				    catch(e){
				      console.log("Exception in http service:"+e);
				    };
			} else {
				this.submitInfection();
			}

		};

		submitInfection = function(){
			if(this.validatingInfection()){
        if(this.infectSystemObj.infectionEventObject.eventName == 'sepsis') {
          this.currentEvent = 'Sepsis';
          if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.eventstatus == 'inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
            for(var i = 0; i < this.pastPrescriptionList.length; i++) {
              var obj = this.pastPrescriptionList[i];
              if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'Sepsis'){
                this.showMedicationPopUp();
                return;
              }
            }
          }
        }
        this.isLoaderVisible = true;
        this.loaderContent = 'Saving...';
				console.log("submit running");
				console.log(this.infectSystemObj);
				this.infectSystemObj.uhid = this.uhid;
				this.infectSystemObj.loggedUser = this.loggedUser;
				this.sepsisHourFlag = false;
        this.ageAtAssessmentPneumoHourFlag = false;
				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime != null || this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime != undefined || this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime != ''){
					this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime = new Date(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime);
				}
				try
				    {
					    this.http.post(this.apiData.saveInfection,
					   	    this.infectSystemObj).subscribe(res => {
					   	    this.vanishSubmitResponseVariable = true;
							this.responseType = res.json().type;
							this.responseMessage = res.json().message;
							this.isLoaderVisible = false;
              this.loaderContent = '';
							setTimeout(() => {
						       this.vanishSubmitResponseVariable = false;

						    }, 3000);
						    if(JSON.parse(localStorage.getItem('redirectBackToAdmission')) != null) {
								var assessmentComment = "";
								var eventName = JSON.parse(localStorage.getItem('eventName'));
								if(eventName == "Sepsis") {
									assessmentComment = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.progressnotes;
								} else if (eventName == "VAP") {
									assessmentComment = this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.progressnotes;
								} else if (eventName == "CLABSI") {
									assessmentComment = this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.progressnotes;
								}
								localStorage.setItem('assessmentComment', JSON.stringify(assessmentComment));
								if(JSON.parse(localStorage.getItem('isComingFromView'))) {
									   this.router.navigateByUrl('/admis/view-profile');
                  				} else {
                  	  				this.router.navigateByUrl('/admis/admissionform');
								}
								localStorage.removeItem('isComingFromView');
							}else{
								this.getInfectionData();
								this.getBellStructure(true);
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

			}
		}

		validatingInfection = function(){

			if(this.infectionWhichTab == 'sepsis'){
				if (this.hourDayDiffSepsis == null || this.hourDayDiffSepsis == undefined){
					this.hourDayDiffSepsis = 0;
				}

				if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays == this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isageofassesmentinhours){
					if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment < this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset){
						$("#ageatassessmentvalidinputSepsis").css("display","block");
						return false;
					}
					else{
						$("#ageatassessmentvalidinputSepsis").css("display","none");
						return true;
					}
				}
				else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays == false && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isageofassesmentinhours == true){
					var ageAtAssessment = Object.assign({},this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment);
					ageAtAssessment -= this.hourDayDiffSepsis;
					ageAtAssessment /= 24;
					ageAtAssessment = Math.round(ageAtAssessment);
					if(ageAtAssessment < this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset){
						$("#ageatassessmentvalidinputSepsis").css("display","block");
						return false;
					}
					else{
						$("#ageatassessmentvalidinputSepsis").css("display","none");
						return true;
					}
				}
				else if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageinhoursdays == true && this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isageofassesmentinhours == false){
					var ageAtAssessment = Object.assign({},this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment);
					ageAtAssessment *= 24;
					ageAtAssessment += this.hourDayDiffSepsis;
					ageAtAssessment = Math.round(ageAtAssessment);
					if(ageAtAssessment < this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatonset){
						$("#ageatassessmentvalidinputSepsis").css("display","block");
						return false;
					}
					else{
						$("#ageatassessmentvalidinputSepsis").css("display","none");
						return true;
					}
				}
				else{
					$("#ageatassessmentvalidinputSepsis").css("display","none");
					return true;
				}
			}
			else{
				return true;
			}
		}
	// --------------  Vap Start
		calAgeOnsetVap = function(){
			if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageinhoursdays){
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset *= 24;
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset += this.hourDayVapDiff;
			}else{
				this.hourDayVapDiff = this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset % 24;
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset -= this.hourDayVapDiff;
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset /= 24;
			}

			this.progressNotesVap();
		}

		creatingPastSelectedTextVap =function(){
			if(this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastEventObj != null && this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastEventObj != undefined && this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastEventObj != ''){
				var pastVap = this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastEventObj;

				if(this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastInvestigationsList!=null
						&& this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastInvestigationsList.length>0){
					for(var indexOrder = 0; indexOrder<this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastInvestigationsList.length;indexOrder++){
						if(this.vapTempObj.pastSelectedOrderInvestigationStr=='' || this.vapTempObj.pastSelectedOrderInvestigationStr==undefined){
							this.vapTempObj.pastSelectedOrderInvestigationStr = this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastInvestigationsList[indexOrder].testname;
						}else{
							this.vapTempObj.pastSelectedOrderInvestigationStr += ", " + this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastInvestigationsList[indexOrder].testname;
						}
					}
				}
			}
		}

		progressNotesVap = function(){

			this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.progressnotes= "";

			var template = "";
			if(this.vapTempObj.isvapavailable == null || this.vapTempObj.isvapavailable != false){
				if(this.infectSystemObj.ageAtOnset!=null && this.infectSystemObj.ageAtOnset!=""){

					if(this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastEventObj!=null){
						if(this.infectSystemObj.infectionEventObject.vapEvent.pastEvents.pastEventObj.eventstatus=='inactive'){
							template = "Baby suspected to have VAP at the age of ";
							if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageinhoursdays){
								if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 ==0){
									template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset + " hr. ";
								}else{
									template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset + " hrs. ";
								}
							}else{
								if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 ==0){
									template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset + " day. ";
								}else{
									template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset +" days. ";
								}
							}
						} else {
							template = "Baby continued to have VAP. ";
						}
					} else {
						if(this.assessmentTempObj.vapEpisodeNumber != null){
							template +="Episode number: "+ this.assessmentTempObj.vapEpisodeNumber + ". ";
						}
						template += "Baby suspected to have VAP at the age of ";
						if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageinhoursdays){
							if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 ==0){
								template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset + " hr. ";
							}else{
								template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset + " hrs. ";
							}
						}else{
							if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset*1 ==0){
								template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset + " day. ";
							}else{
								template += this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatonset +" days. ";
							}
						}
					}

					if(this.sepsisTempObj.orderSelectedText != "" && this.sepsisTempObj.orderSelectedText != undefined){
						if(this.sepsisTempObj.orderSelectedText.indexOf(",")== -1){
							template = template +"Investigation ordered is "+this.sepsisTempObj.orderSelectedText+". ";
						}
						else{
							template = template +"Investigation ordered are "+this.sepsisTempObj.orderSelectedText+". ";
						}
					}

					//plan seizures
					if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime !=null &&
							this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime!=""
								&& this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestimeType!=null){
						if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime == "1"){
							if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestimeType=='mins'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime+" min. ";
							}else if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestimeType=='hours'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime+" hour. ";
							} else {
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime+" day. ";
							}
						}
						else{
							if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestimeType=='mins'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime+" mins. ";
							}else if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestimeType=='hours'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime+" hours. ";
							} else {
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.reassestime+" days. ";
							}
						}
					}

					if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.otherplanComments !=null){
						template += "Other plan is to " + this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.otherplanComments+". ";
					}

				} //end of age at onset if
			}else{
				template = "No episode of VAP.";
			}

			this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.progressnotes = template;
		}
		actionVapVisible = function(){
			this.isActionVapVisible = !this.isActionVapVisible ;
		};
		planVapVisible = function(){
			this.isPlanVapVisible = !this.isPlanVapVisible ;
		};
		progressVapVisible = function(){
			this.isProgressVapVisible = !this.isProgressVapVisible ;
		};
		refreshInfection = function(){
			this.getInfectionData();
		}
		calculateAgeAtAssessmentNewSepsis = function(){
      //To Populate the vital params when date is changed
      this.populateVitalInfoByDate();

			var dateBirth = JSON.parse(localStorage.getItem('selectedChild')).dob;
			dateBirth = new Date(dateBirth);
			var timeBirthArray = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.split(',');
			var birthHour = parseInt(timeBirthArray[0]);
			var birthMin = parseInt(timeBirthArray[1]);
			if(timeBirthArray[2]=='AM'){
				if(birthHour==12)
					dateBirth.setHours(0);
				else
					dateBirth.setHours(birthHour);
			}else{
				if(birthHour==12)
					dateBirth.setHours(12);
				else
					dateBirth.setHours(birthHour+12);
			}
			dateBirth.setMinutes(birthMin);
			var tempCurrentAgeData = new Date(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime)
			var currentAgeData = tempCurrentAgeData;
			var diffTime = currentAgeData.getTime() - dateBirth.getTime();
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.isageofassesmentinhours == true) {
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment = (diffTime/(1000*60*60))+1;
			} else {
				this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment = (diffTime/(1000*60*60*24))+0;
			}
      this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment = parseInt(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment);
			//for episodes number.........
			this.assessmentTempObj = this.indcreaseEpisodeNumber(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory);
			this.progressNotesSepsis();
		}

    calculateAgeAtAssessmentVap = function(changedParam) {

  			if(changedParam=='meridian'){
  				if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.isageofassesmentinhours && this.ageAtAssessmentPneumoHourFlag){
  					this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment *= 24;
  					this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment += this.hourDayDiffAgeAtAssessmentPneumo;
  					this.ageAtAssessmentPneumoHourFlag = false;
  				}else if(!(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.isageofassesmentinhours || this.ageAtAssessmentPneumoHourFlag)){
  					this.ageAtAssessmentPneumoHourFlag = true;
  					this.hourDayDiffAgeAtAssessmentPneumo = this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment%24;
  					this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment -= this.hourDayDiffAgeAtAssessmentPneumo;
  					this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment /= 24;
  				}
  			}else{
  				this.hourDayDiffAgeAtAssessmentPneumo = 0;
  			}
  		}


    calculateAgeAtAssessmentNewVap = function(){
			var dateBirth = JSON.parse(localStorage.getItem('selectedChild')).dob;
			dateBirth = new Date(dateBirth);
			var timeBirthArray = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.split(',');
			var birthHour = parseInt(timeBirthArray[0]);
			var birthMin = parseInt(timeBirthArray[1]);
			if(timeBirthArray[2]=='AM'){
				if(birthHour==12)
					dateBirth.setHours(0);
				else
					dateBirth.setHours(birthHour);
			}else{
				if(birthHour==12)
					dateBirth.setHours(12);
				else
					dateBirth.setHours(birthHour+12);
			}
			dateBirth.setMinutes(birthMin);
			var tempCurrentAgeData = new Date(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.assessmentTime);
			var currentAgeData = tempCurrentAgeData;
			var diffTime = currentAgeData.getTime() - dateBirth.getTime();
			if(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.isageofassesmentinhours == true) {
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment = (diffTime/(1000*60*60))+1;
			} else {
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment = (diffTime/(1000*60*60*24))+0;
			}
      this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment = parseInt(this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.ageatassesment);
			//for episodes number.........
			//this.assessmentTempObj = this.indcreaseEpisodeNumber(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory);
			//this.progressNotesSepsis();
		}

		indcreaseEpisodeNumber = function(list){
			var sepsisTime = new Date();
			if(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime != undefined
					&& this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime != null) {
				if(typeof this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime === 'number') {
					sepsisTime = new Date(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime);
				} else {
					sepsisTime = new Date(this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime);
				}
			}

			this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.episodeNumber = 1;

			if(list != undefined && list != null && list != ''){
				for(let index=0;index<list.length;index++){
					var obj = list[index];
					if(obj[7]=='inactive'){
						if(obj[4]=="Sepsis" && obj[1] <= sepsisTime.getTime()){
							this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.episodeNumber++;
						}else if(obj[4]=="VAP"){
							this.assessmentTempObj.vapEpisodeNumber++;
						}else if(obj[4]=="CLABSI"){
							this.assessmentTempObj.clabsiEpisodeNumber++;
						}
					}
				}
			}
			return 	this.assessmentTempObj;
		}
		openGuideline = function(selectedTab){
			this.whichGuidelineVisible = selectedTab ;
			$("#guidelineOverlay").css("display", "block");
			$("#guideline").addClass("showing");
		}
		closeGuideline = function(){
			$("#guidelineOverlay").css("display", "none");
			$("#guideline").removeClass("showing");

			if(this.vapTempObj.isvapavailable !=null && this.vapTempObj.isvapavailable == true){
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.isvapavailable = true;
			} else if(this.vapTempObj.isvapavailable!=null && this.vapTempObj.isvapavailable==false){
				this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.isvapavailable = false;
			}
			if(this.clabsiTempObj.isclabsiavailable!=null && this.clabsiTempObj.isclabsiavailable==true){
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.isclabsiavailable = true;
			} else if(this.clabsiTempObj.isclabsiavailable!=null && this.clabsiTempObj.isclabsiavailable==false){
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.isclabsiavailable = false;
			}
		}
	// -------------- Vap End

	submitPassiveInactive = function() {
			if(this.infectSystemObj.systemStatus==null || this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory.length==0){
				console.log("Cannot submit");
			} else if(this.infectSystemObj.systemStatus == 'no'){
				if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory.length==0)
					console.log("Cannot make Infection system passive.");
				else{
					var problemDiseases = [];
					if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory[0][7]!='inactive')
						problemDiseases.push("Sepsis");

					if(problemDiseases.length==0){
						console.log(this.infectSystemObj);
						var passiveTime = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.assessmentTime;
						this.submitInfection();

					} else{
					console.log("Cannot make Infection system passive. Sepsis is not inactive.");
					}
				}
			} else if(this.infectSystemObj.systemStatus == 'inactive'){
				if(this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory.length==0 || this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory[0][7]!='no')
				{
					console.log("Cannot make Infection System inactive.");
				} else{
					this.showOkPopUp('infection','Are you sure you want to Inactivate Infection Assessment');
				}

			}
		};
		ageOnAssessmentCalculationClabsi = function(){

			if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.isageofassesmentinhours && this.clabsiHourFlag){
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment *= 24;
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment += this.hourDayDiffClabsi;
        this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment = parseInt(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment);
        this.clabsiHourFlag = false;
			}else if(!(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.isageofassesmentinhours || this.clabsiHourFlag)){
				this.clabsiHourFlag = true;
				this.hourDayDiffClabsi = this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.ageatassesment%24;
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment -= this.hourDayDiffClabsi;
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment /= 24;
        this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment = parseInt(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment);
			}
		}
		setTodayAssessmentClabsi = function(){
			var todayDate = new Date();
			this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate = todayDate;
			var todayHours = todayDate.getHours();
			var todayMinutes = todayDate.getMinutes();
			if(todayHours<12){
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentMeridiem = true;
				if(todayHours==0)
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour = "12";
				else if(todayHours<10)
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour = '0' + todayHours.toString();
				else
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour = todayHours.toString();
			}else{
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentMeridiem = false;
				todayHours -=12;
				if(todayHours==0)
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour = "12";
				else if(todayHours<10)
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour = '0' + todayHours.toString();
				else
					this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour = todayHours.toString();
			}
			if(todayMinutes<10)
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentMin = '0' + todayMinutes.toString();
			else
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentMin = 	todayMinutes.toString();

			this.clabsiTempObj.assessmentDate = this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate;
			this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentTime = this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate;
		}

		calculateAgeAtAssessmentNewClabsi = function(){
			console.log(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate);
			if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate==null || this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate==undefined){
				console.log("date selected error");
				return;
			}
			this.dateBirth = JSON.parse(localStorage.getItem('selectedChild')).dob;
			this.dateBirth = new Date(this.dateBirth);
			var timeBirthArray = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.split(',');
			var birthHour = parseInt(timeBirthArray[0]);
			var birthMin = parseInt(timeBirthArray[1]);

			/*
			 * 12,00,AM or 00,00,AM are same, ie. 0000Hrs in 24Hr
			 * 12,00,PM or 00,00,PM are same, ie. 1200Hrs in 24Hr
			 */
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

			var currentAgeData = this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentDate;
			// var selectedHours = (this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentHour);
			// var selectedMins = (this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.currentEvent.assessmentMin);
			// if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentMeridiem==false && selectedHours!=12) {
			// 	selectedHours+=12;
			// } else if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentMeridiem && selectedHours==12) {
			// 	selectedHours = 0;
			// }
      //
			// currentAgeData.setHours(selectedHours);
			// currentAgeData.setMinutes(selectedMins);
			this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.assessmentTime = currentAgeData;

			this.diffTime = currentAgeData.getTime() - this.dateBirth.getTime();
			if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.isageofassesmentinhours == true)
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment = (this.diffTime/(1000*60*60))+1;
			else
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment = (this.diffTime/(1000*60*60*24))+0;

      this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment = parseInt(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment);
    }
		calAgeAtOnsetOnChange = function(moduleValue){
			if(moduleValue == 'Sepsis'){
				if((this.sepsisTempObj.hours!=null && this.sepsisTempObj.hours!='') || this.sepsisTempObj.hours==undefined){

					if(this.sepsisTempObj.hours*1 < 1 || this.sepsisTempObj.hours*1 > 12 || this.sepsisTempObj.hours==undefined){
						$("#invalidSepsisTime").css("display","block");
					} else if(this.sepsisTempObj.hours*1 >=1  || this.sepsisTempObj.hours*1 <= 12){
						$("#invalidSepsisTime").css("display","none");

						var timeDate = new Date();
						var meridianOnset = 0;
						if(this.sepsisTempObj.meridian!=true){
							meridianOnset = 12;
						}
						timeDate.setHours(this.sepsisTempObj.hours*1+meridianOnset);
						timeDate.setMinutes(0);
						timeDate.setSeconds(0);
						timeDate.setMilliseconds(0);

						this.infectSystemObj.infectionEventObject.sepsisEvent.currentEvent.timeofassessment = timeDate.getTime();
					}
				}

				this.progressNotesSepsis();
			} else if(moduleValue == 'Vap'){
				if((this.vapTempObj.hours!=null && this.vapTempObj.hours!='') || this.vapTempObj.hours==undefined){

					if(this.vapTempObj.hours*1 < 1 || this.vapTempObj.hours*1 > 12 || this.vapTempObj.hours==undefined){
						$("#invalidTimeVap").css("display","block");
					} else if(this.vapTempObj.hours*1 >=1  || this.vapTempObj.hours*1 <= 12){
						$("#invalidTimeVap").css("display","none");

						var timeDate = new Date();
						var meridianOnset = 0;
						if(this.vapTempObj.meridian!=true){
							meridianOnset = 12;
						}
						timeDate.setHours(this.vapTempObj.hours*1+meridianOnset);
						timeDate.setMinutes(0);
						timeDate.setSeconds(0);
						timeDate.setMilliseconds(0);

						this.infectSystemObj.infectionEventObject.vapEvent.currentEvent.timeofassessment = timeDate.getTime();
					}
				}

				this.progressNotesVap();
			} else if(moduleValue == 'Clabsi'){
				if((this.clabsiTempObj.hours!=null && this.clabsiTempObj.hours!='') || this.clabsiTempObj.hours==undefined){

					if(this.clabsiTempObj.hours*1 < 1 || this.clabsiTempObj.hours*1 > 12 || this.clabsiTempObj.hours==undefined){
						$("#invalidTimeClabsi").css("display","block");
					} else if(this.clabsiTempObj.hours*1 >=1  || this.clabsiTempObj.hours*1 <= 12){
						$("#invalidTimeClabsi").css("display","none");

						var timeDate = new Date();
						var meridianOnset = 0;
						if(this.clabsiTempObj.meridian!=true){
							meridianOnset = 12;
						}
						timeDate.setHours(this.clabsiTempObj.hours*1+meridianOnset);
						timeDate.setMinutes(0);
						timeDate.setSeconds(0);
						timeDate.setMilliseconds(0);

						this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.timeofassessment = timeDate.getTime();
					}
				}

				this.progressNotesClabsi();
			}
		}
		calculateAgeAtAssessmentClabsi = function() {
			var meridianOnset = 0;
			if(this.clabsiTempObj.meridian != true){
				meridianOnset = 12;
			}
			if(this.clabsiTempObj.hours != undefined) {
				var timeStr = this.clabsiTempObj.hours.split(':');

				var hrs = 0;

				if(timeStr[0] * 1 != 12) {
					hrs = timeStr[0] * 1;
				}
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatassesment =
					(this.infectSystemObj.ageAtOnset- this.currentHrs) + (hrs + meridianOnset);
			}
		}

		calAgeOnsetClabsi = function(){
			if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageinhoursdays){
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset *= 24;
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset += this.hourDayClabsiDiff;
			}else{
				this.hourDayClabsiDiff = this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset % 24;
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset -= this.hourDayClabsiDiff;
				this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset /= 24;
			}

			this.progressNotesClabsi();
		}

		creatingPastSelectedTextClabsi =function(){
			if(this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastEventObj != undefined && this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastEventObj != null && this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastEventObj != ''){
				var pastClabsi = this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastEventObj;

				if(this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastInvestigationsList!=null
						&& this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastInvestigationsList.length>0){
					for(var indexOrder = 0; indexOrder<this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastInvestigationsList.length;indexOrder++){
						if(this.clabsiTempObj.pastSelectedOrderInvestigationStr=='' || this.clabsiTempObj.pastSelectedOrderInvestigationStr==undefined){
							this.clabsiTempObj.pastSelectedOrderInvestigationStr = this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastInvestigationsList[indexOrder].testname;
						}else{
							this.clabsiTempObj.pastSelectedOrderInvestigationStr += ", " + this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastInvestigationsList[indexOrder].testname;
						}
					}
				}
			}
		}

		progressNotesClabsi = function(){

			this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.progressnotes= "";

			var template = "";
			if(this.clabsiTempObj.isclabsiavailable == null || this.clabsiTempObj.isclabsiavailable != false){
				if(this.infectSystemObj.ageAtOnset!=null && this.infectSystemObj.ageAtOnset!=""){

					if(this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastEventObj!=null){
						if(this.infectSystemObj.infectionEventObject.clabsiEvent.pastEvents.pastEventObj.eventstatus=='inactive'){
							template = "Baby suspected to have CLABSI at the age of ";
							if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageinhoursdays){
								if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 ==0){
									template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset + " hr. ";
								}else{
									template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset + " hrs. ";
								}
							}else{
								if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 ==0){
									template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset + " day. ";
								}else{
									template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset +" days. ";
								}
							}
						} else {
							template = "Baby continued to have CLABSI. ";
						}
					} else {
						if(this.assessmentTempObj.clabsiEpisodeNumber != null){
							template += "Episode number: " + this.assessmentTempObj.clabsiEpisodeNumber + ". ";
						}
						template += "Baby suspected to have CLABSI at the age of ";
						if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageinhoursdays){
							if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 ==0){
								template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset + " hr. ";
							}else{
								template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset + " hrs. ";
							}
						}else{
							if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 == 1 || this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset*1 ==0){
								template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset + " day. ";
							}else{
								template += this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.ageatonset +" days. ";
							}
						}
					}

					if(this.sepsisTempObj.orderSelectedText != "" && this.sepsisTempObj.orderSelectedText != undefined){
						if(this.sepsisTempObj.orderSelectedText.indexOf(",")== -1){
							template = template +"Investigation ordered is "+this.sepsisTempObj.orderSelectedText+". ";
						}
						else{
							template = template +"Investigation ordered are "+this.sepsisTempObj.orderSelectedText+". ";
						}
					}

					//plan seizures
					if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime !=null &&
							this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime!=""
								&& this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestimeType!=null){
						if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime == "1"){
							if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestimeType=='mins'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime+" min. ";
							}else if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestimeType=='hours'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime+" hour. ";
							} else {
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime+" day. ";
							}
						}
						else{
							if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestimeType=='mins'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime+" mins. ";
							}else if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestimeType=='hours'){
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime+" hours. ";
							} else {
								template += "Plan is to reassess the baby after "+this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.reassestime+" days. ";
							}
						}
					}

					if(this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.otherplanComments !=null){
						template += "Other plan is to " + this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.otherplanComments+". ";
					}

				}// end of age at onset if
			}else{
				template = "No episode of CLABSI.";
			}
			this.infectSystemObj.infectionEventObject.clabsiEvent.currentEvent.progressnotes = template;
		}
		clinicalSectionClabsiVisible = function(){
			this.isclinicalSectionClabsiVisible = !this.isclinicalSectionClabsiVisible;
		}
		actionClabsiVisible = function(){
			this.isActionClabsiVisible = !this.isActionClabsiVisible;
		}
		planClabsiVisible = function(){
			this.isPlanClabsiVisible = !this.isPlanClabsiVisible;
		}
		progressClabsiVisible = function(){
			this.isProgressClabsiVisible = !this.isProgressClabsiVisible;
		}
    showWarningPopUp() {
  		$("#OkWarningPopUp").addClass("showing");
  		$("#warningOverlay").addClass("show");
  	};

  	closeWarningPopUp(){
      this.warningMessage = "";
  		$("#OkWarningPopUp").removeClass("showing");
  		$("#warningOverlay").removeClass("show");
  	};

  	printData = function(fromDateTable : Date, toDateTable : Date){
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
    for(var i=0; i < this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory.length; i++) {
      var item = this.infectSystemObj.infectionEventObject.commonEventsInfo.pastInfectionHistory[i];
          if(item[1] >= fromDateTable.getTime() && item[1] <= toDateTable.getTime()) {
          var obj = Object.assign({}, item);
          data.push(obj);
      }
    }
    this.printDataObj.printData = data;
  }

    this.printDataObj.uhid = this.childObject.uhid;
	this.printDataObj.whichTab = "Infection";
	this.printDataObj.dateFrom = fromDateTable;
	this.printDataObj.dateTo = toDateTable;
    console.log(this.printDataObj);
	localStorage.setItem('printDataObj', JSON.stringify(this.printDataObj));
    this.router.navigateByUrl('/jaundice/jaundice-print');
	}

	isEmpty(object : any) : boolean {
		if (object == null || object == '' || object == 'null' || object.length == 0) {
			return true;
		} else {
			return false;
		}
	}

  showMedicationPopUp = function() {
    $("#medicationPopup").addClass("showing");
    $("#MedicationOverlay").addClass("show");
  };
  closeMedicationPopUp = function(){
    $("#medicationPopup").removeClass("showing");
    $("#MedicationOverlay").removeClass("show");
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
      if(obj.eventname == this.currentEvent) {
        if(obj.isactive == true && obj.enddate != null){
          obj.enddate = new Date(obj.enddate);
          obj.isactive = false;
          this.createRespectiveList(obj);
        }
        else if(obj.isactive == true && obj.isContinue != null && obj.isContinue == true && obj.continueReason != null && obj.continueReason != ''){
          obj.eventname = "Stable Notes";
          this.createRespectiveList(obj);
        }
      }
    }
    this.closeMedicationPopUp();
  }

  createRespectiveList = function(data : any){
    if(this.currentEvent == 'Sepsis'){
      this.infectSystemObj.infectionEventObject.sepsisEvent.prescriptionList.push(data);
    }
  }


	ngOnInit() {
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
		this.getInfectionData();
		this.getBellStructure(null);
  	};

};
