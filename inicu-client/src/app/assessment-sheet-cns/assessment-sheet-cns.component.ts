import { Component, OnInit } from '@angular/core';
import { Chart } from 'angular-highcharts';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';
import {RootObject} from './rootObjCNS';
import {PrintDataObj} from '../assessment-sheet-jaundice/printDataObj';
import {CNSTimeObj} from './cnsTimeObj';
import {AsphyxiaTempObj} from './asphyxiaTempObj';
import {SeizuresTempObj} from './seizuresTempObj';
import {CnsTempObj} from './cnsTempObj';
import {DowneScoreObj} from './scoreDownes';
import {ScoreLeveneObj} from './scoreLevene';
import {ThompsonScoreObj} from './scoreThompson';
import {SarnatScoreObj} from './scoreSarnat';
import {Prescription} from './prescription';
import { AppComponent } from '../app.component';

@Component({
  selector: 'assessment-sheet-cns',
  templateUrl: './assessment-sheet-cns.component.html',
  styleUrls: ['./assessment-sheet-cns.component.css']
})
export class AssessmentSheetCnsComponent implements OnInit {
  apiData : APIurl;
	http: Http;
  inactiveMessageText : string;
  router : Router;
  childObject : any;
  cnsSystemObj : RootObject;
  printDataObj : PrintDataObj;
  cnsTimeObj : CNSTimeObj;
  cns : boolean;
  workingWeight : any;
  asphyxiaTempObj : AsphyxiaTempObj;
  hoursList : any;
  ageOnsetPreviousExceed : boolean;
  AspHourFlag : boolean;
	SeizuresHourFlag : boolean;
  rsAmplitude : string;
  rsEt : string;
  rsFio2 : string;
  rsFlowRate : string;
  rsFrequency : string;
  rsIt : string;
  rsMap : string;
  rsMechVentType : string;
  rsMv : string;
  rsPeep : string;
  rsPip : string;
  rsTv : string;
  rsVentType : string;
  rangeArr : Array<any>;
  rangePip : Array<any>;
  rangePeep : Array<any>;
  rangeFiO2 : Array<any>;
  rangeIt : Array<any>;
  rangeRate : Array<any>;
  rangeMap : Array<any>;
  range1150Arr : Array<any>;
  listTestsByCategory : Array<any>;
  rangeDecimalArr : Array<any>;
  orderSelectedText : string;
  warningMessage : string;
  responseSaveCns : any;
  vanishSubmitResponseVariable : boolean;
  prescription : Prescription;
  response: any;
  investOrderSelected : Array<any>;
	investOrderNotOrdered : Array<any>;
  whichModuleRespirstorySupport : string;
	respiratorySupportPopupView : boolean;
  isRdsValid : boolean;
  inactiveWithRespSupport : boolean;
  otherMedTableFlag : boolean;
  otherMedTableText : string;
  seizuresTempObj : SeizuresTempObj;
  seizuresPastTreatmentSelected : Array<any>;
  expanded : boolean;
  cnsTempObj : CnsTempObj;
  needforPPVTime : string;
  ivFluidVariable : boolean;
  ishistorySectionAsphyxiaVisible : boolean;
  isclinicalSectionAsphyxiaVisible : boolean;
  isActionSectionAsphyxiaVisible : boolean;
  isPlanSectionAsphyxiaVisible : boolean;
  isCauseAsphyxiaVisible : boolean;
  isProgressAsphyxiaVisible : boolean;
  isclinicalSectionSeizuresVisible : boolean;
  isActionSectionSeizuresVisible : boolean;
  isCauseSeizuresVisible : boolean;
  isProgressSeizuresVisible : boolean;
  isPlanSectionSeizuresVisible : boolean;
  TableSeizersFlag : boolean;
  otherMedTableAsphyxiaFlag : boolean;
  isrespGraph : boolean;
  tempPastPrescriptionList : Array<any>;
  cnsWhichTab : string;
  thompsonTabContent : string;
  downesTabContent : string;
  sarnatTabContent : string;
  tabContent : string;
  ordertabContent : string;
  totalCyanosis : number;
  totalRetractions : number;
  totalGrunting :number;
  totalAirEntry : number;
  totalRespiratoryRate : number;
  downesValueCalculated : number;
  totalCons : number;
  totalTone : number;
  totalSeizure : number;
  totalSucking : number;
  leveneValueCalculated : number;
  passiveMessage : string;
  loggedUser : string;
  totalLoc : number;
  totalFits : number;
  totalPosture : number;
  totalMoro : number;
  totalGrasp : number;
  totalSuck : number;
  totalRespiration : number;
  totalFontanelle : number;
  thompsonValueCalculated : number;
  fromDateTableNull : boolean;
  toDateTableNull : boolean;
  fromToTableError : boolean;
  minDate : string;
  maxDate : Date;
  isLoaderVisible : boolean;
  loaderContent : string;
  pastPrescriptionList : any;
  currentEvent : string;
  selectAll : any;
  currentDate : Date;
  constructor(http: Http, router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
    this.currentDate = new Date();
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.cnsSystemObj = new RootObject();
    this.printDataObj = new PrintDataObj;
    this.cnsTimeObj = new CNSTimeObj;
    this.cns = true;
    this.workingWeight = this.childObject.workingWeight;
    this.asphyxiaTempObj = new AsphyxiaTempObj;
    this.hoursList = [];
		for(var i=1;i<=12;++i){
			if(i<10)
				this.hoursList.push("0"+i.toString());
			else
				this.hoursList.push(i.toString());
		}
    this.warningMessage = "";
    this.ageOnsetPreviousExceed = false;
    this.AspHourFlag = false;
		this.SeizuresHourFlag = false;
    this.rsAmplitude = "";
		this.rsEt = "";
		this.rsFio2 = "";
		this.rsFlowRate ="";
		this.rsFrequency = "";
		this.rsIt = "";
		this.rsMap = "";
		this.rsMechVentType = "";
		this.rsMv = "";
		this.rsPeep = "";
		this.rsPip = "";
		this.rsTv = "";
		this.rsVentType ="";
    this.listTestsByCategory = [];
    this.rangeArr = [];
		this.rangePip=[];
    this.rangePeep = [];
    this.rangeFiO2 = [];
		this.rangeIt = [];
    this.rangeRate = [];
    this.rangeMap = [];
    this.selectAll = null;

		for(var i=10; i<=50; ++i)
			this.rangePip.push(i.toString());

		for(var i=3;i<=12;++i)
			this.rangePeep.push(i.toString());

		for(var i=21;i<=100;++i)
			this.rangeFiO2.push(i.toString());

		for (var i = 0.10; i <=0.81; i+=0.01)
			this.rangeIt.push(i.toFixed(2).toString());

		for(var i=10;i<=120;i+=1)
			this.rangeRate.push(i.toFixed(2).toString());

		for(var i=3;i<=25;++i)
			this.rangeMap.push(i.toString());

		for(var i =1;i<=100;i++){
			this.rangeArr.push(i.toString());
		}

		this.range1150Arr = [];
		for(var i =1;i<=150;i++){
			this.range1150Arr.push(i.toString());
		}

		this.rangeDecimalArr = [];
		for(var i =0.01;i<=0.90;i = i + 0.01){
			this.rangeDecimalArr.push(Math.round(i * 100) / 100);
		}
    this.investOrderSelected =[];
  	this.investOrderNotOrdered =[];
    this.whichModuleRespirstorySupport = "";
		this.respiratorySupportPopupView = false;
    this.isRdsValid = true;
    this.inactiveWithRespSupport = false;
    this.otherMedTableFlag = false;
    this.otherMedTableText = "View Current Medication";
    this.seizuresTempObj = new SeizuresTempObj();
    this.seizuresPastTreatmentSelected = [];
    this.seizuresTempObj.pastSelectedOrderInvestigationStr="";
		this.seizuresTempObj.pastSelectedCauseStr = "";
    this.expanded = false;
    this.cnsTempObj = new CnsTempObj();
    this.cnsTempObj.manageIvFluid = false;
	  this.cnsTempObj.treatmentProgressText = "";
    this.needforPPVTime ="";
    this.ivFluidVariable = false;
    this.ishistorySectionAsphyxiaVisible = false;
    this.isclinicalSectionAsphyxiaVisible = true;
    this.isActionSectionAsphyxiaVisible = false;
    this.isPlanSectionAsphyxiaVisible = false;
    this.isCauseAsphyxiaVisible = false;
    this.isProgressAsphyxiaVisible = true;
    this.isclinicalSectionSeizuresVisible = true;
  	this.isActionSectionSeizuresVisible = false;
    this.isCauseSeizuresVisible = false;
    this.isProgressSeizuresVisible = true;
    this.isPlanSectionSeizuresVisible = false;
  	this.TableSeizersFlag = false;
  	this.otherMedTableAsphyxiaFlag = false;
    this.isrespGraph = true;
    this.tempPastPrescriptionList = [];
    this.thompsonTabContent = 'thompson';
    this.downesTabContent = 'downes';
    this.sarnatTabContent = 'sarnat';
    this.tabContent = 'bind';
    this.ordertabContent = 'neuro';
    this.totalCyanosis = 0;
    this.totalRetractions = 0;
    this.totalGrunting = 0;
    this.totalAirEntry =0;
    this.totalRespiratoryRate = 0;
  	this.downesValueCalculated = 0;
    this.totalCons = 0;
    this.totalTone = 0;
    this.totalSeizure = 0;
    this.totalSucking = 0;
    this.leveneValueCalculated = 0;
    this.totalTone = 0;
    this.totalLoc = 0;
    this.totalFits = 0;
    this.totalPosture =0;
    this.totalMoro =0;
    this.totalGrasp = 0;
    this.totalSuck =0;
    this.totalRespiration = 0;
    this.totalFontanelle = 0;
		this.thompsonValueCalculated = 0;
    this.passiveMessage = "Respiratory Support is on, do you want to stop it?";
    this.pastPrescriptionList = [];
    this.currentEvent = 'Asphyxia';
    this.init();
  }

  init (){
    this.cnsTimeObj.printFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
		this.cnsTimeObj.printTo = new Date();
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
    this.setDataCns();
  }

  setDataCns = function(){
    this.orderSelectedText = "";
    try
    {
      this.http.post(this.apiData.setDataCns + this.childObject.uhid + "/test",
        JSON.stringify({
          someParameter: 1,
          someOtherParameter: 2
        })).subscribe(res => {
          this.cnsSystemObj =res.json();
          this.assessmentTempObj = this.indcreaseEpisodeNumber(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory);

          this.pastPrescriptionList = [];
          if(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList.length > 0){
            for(var i = 0; i < this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList.length; i++){
              if(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i].eventname == 'Asphyxia' || this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i].eventname == 'Seizures'){
                var startDate = new Date(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i].medicineOrderDate);
                var medContiuationDayCount = Math.abs(Math.ceil((this.currentDate.getTime() - startDate.getTime())/(1000*60*60*24)));
                if(medContiuationDayCount == 0){
                  medContiuationDayCount = 1;
                }
                this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i].medicineDay = medContiuationDayCount;
                this.pastPrescriptionList.push(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i]);
              }
            }
            this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList = [];
          }
          console.log(this.cnsSystemObj);
          var seizuresNoRedirectFlag = true;
          var asphyxiaNoRedirectFlag = true;
          var date = new Date();
          this.formatAMPM(date);
          this.currentHrs = date.getHours();
          //Asphyxia age at onset enable/disable
          if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent!=null
              && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent.length>0){
            if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].eventstatus.toUpperCase()=='INACTIVE'){
              this.pastAgeInactiveAsphyxia = false;
            } else {
              this.pastAgeInactiveAsphyxia = true;
            }
          } else {
            this.pastAgeInactiveAsphyxia = false;
          }

          //seizures age at onset enable/disable
          if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents!=null
              && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents.length>0){
            if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].eventstatus.toUpperCase()=='INACTIVE'){
              this.pastAgeInactiveSeziures = false;
            } else {
              this.pastAgeInactiveSeziures = true;
            }
          } else {
            this.pastAgeInactiveSeziures = false;
          }

          if(localStorage.getItem('isComingFromPrescription')!=null){
            seizuresNoRedirectFlag = false;
            var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromPrescription'));
            if(isComingFromPrescription){
              this.setPresRedirectVariable();
            }
            localStorage.removeItem("isComingFromPrescription");
          }

          if(localStorage.getItem('switchBack') != null) {
            var target = JSON.parse(localStorage.getItem('eventSource'));
            if(target == 'asphyxia') {
              this.setAsphyxiaSwitchVariable();
              asphyxiaNoRedirectFlag = false;
            }
            localStorage.removeItem('switchBack');
          }

          if(localStorage.getItem('isInternalSwitch') != null) {
            var target = JSON.parse(localStorage.getItem('eventTarget'));
            if(target == 'IVH') {
              localStorage.setItem('switchBack',JSON.stringify(true));
              this.cnsTabVisible('intracranial hemorrhage');
              this.cnsSystemObj.systemStatus = 'yes';
              this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.eventstatus = 'yes';
            }
          }
          localStorage.removeItem("isInternalSwitch");
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.levelOfConsiousness = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.duration = 0;
          this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.outcome = 0;

          if(localStorage.getItem('admissionform') != null){
            this.cnsSystemObj.systemStatus = 'yes';
            localStorage.setItem('redirectBackToAdmission',JSON.stringify(true));
            localStorage.removeItem('admissionform');

            var eventName = JSON.parse(localStorage.getItem('eventName'));
            if(eventName == "Asphyxia") {
              this.cnsTabVisible('asphyxia');
              this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus = 'yes';
            } else if (eventName == "Seizures") {
              this.cnsTabVisible('seizures');
              this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus = 'yes';
            } else if (eventName == "IVH") {
              this.cnsTabVisible('intracranial hemorrhage');
              this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.eventstatus = 'yes';
            }
          }

          var iscomingBackFromFeed = JSON.parse(localStorage.getItem('comingBackFromFeed'));
          if(iscomingBackFromFeed!=null && (iscomingBackFromFeed==true || iscomingBackFromFeed=='true')){
            // for seizures
            // this.setSeizuresFeedsRedirectVariable();
            asphyxiaNoRedirectFlag = false;
            this.setAsphyxiaFeedsRedirectVariable();
            localStorage.removeItem("comingBackFromFeed");
            localStorage.removeItem("infoFeedNutrition");
          }

          // redirect from apnea to seizures
          if(localStorage.getItem('isComingBackFromSymptomaticOtherAssessments')!=null){
            var isRedirectBackToAssessmentSymptomatic = JSON.parse(localStorage.getItem('isComingBackFromSymptomaticOtherAssessments'));
            if(isRedirectBackToAssessmentSymptomatic){

              //for Seizures
              if(JSON.parse(localStorage.getItem('isComingFromSeizures'))){
                seizuresNoRedirectFlag = false;
                this.setSeizuresRespRedirectVariable();
              }
            }
            localStorage.removeItem("isComingBackFromSymptomaticOtherAssessments");
          }

          var isComingFromInfectionTreatment = JSON.parse(localStorage.getItem('isComingFromInfectionTreatment'));
          if(isComingFromInfectionTreatment != null){
            localStorage.removeItem("isComingFromInfectionTreatment");
            localStorage.setItem('redirectBackToInfection',JSON.stringify(true));
            this.cnsTabVisible('seizures');
            this.cnsSystemObj.systemStatus ='yes';
            this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus = 'yes';
          }else{
            localStorage.removeItem("isComingFromInfectionTreatment");
          }

          // redirect from Metabolic to seizures/asphyxia
          if(localStorage.getItem('isComingFromMetabolic')!=null){
            var isComingFromMetabolic = JSON.parse(localStorage.getItem('isComingFromMetabolic'));
            if(isComingFromMetabolic){

              //for Seizures
              if(JSON.parse(localStorage.getItem('isComingFromSeizures'))){
                seizuresNoRedirectFlag = false;
                this.setSeizuresMetabolicRedirectVariable();
              }

              //for Asphyxia
              if(JSON.parse(localStorage.getItem('isComingFromAsphyxia'))){
                asphyxiaNoRedirectFlag = false;
                this.setAsphyxiaMetabolicRedirectVariable();
              }
            }
            localStorage.removeItem('eventTarget');
            localStorage.removeItem("currentcnsSystemObj");
            localStorage.removeItem("isComingFromSeizures");
            localStorage.removeItem("isComingFromAsphyxia");
            localStorage.removeItem("isComingFromMetabolic");
          }

          // redirect from Respiratory to asphyxia
          if(localStorage.getItem('isComingFromRespiratory')!=null){
            var isComingFromRespiratory = JSON.parse(localStorage.getItem('isComingFromRespiratory'));
            if(isComingFromRespiratory){

              //for Asphyxia
              if(JSON.parse(localStorage.getItem('isComingFromAsphyxia'))){
                asphyxiaNoRedirectFlag = false;
                this.setAsphyxiaRespiratoryRedirectVariable();
              }
            }
            localStorage.removeItem('eventTarget');
            localStorage.removeItem("currentcnsSystemObj");
            localStorage.removeItem("isComingFromAsphyxia");
            localStorage.removeItem("isComingFromRespiratory");
          }


          var isComingFromInfectionTreatment = JSON.parse(localStorage.getItem('isComingFromInfectionTreatment'));
          if(isComingFromInfectionTreatment != null){
            localStorage.removeItem("isComingFromInfectionTreatment");
            localStorage.setItem('redirectBackToInfection',JSON.stringify(true));
            this.cnsTabVisible('seizures');
          }else{
            localStorage.removeItem("isComingFromInfectionTreatment");
          }

          if(seizuresNoRedirectFlag) {
            this.initTreatmentVariablesSeizures();
          }


          if(asphyxiaNoRedirectFlag) {
            this.initTreatmentVariablesAsphyxia();
          }

          // for all CNS assessment set past res support
          this.setPastRespSupport();

          //for ivh
          //this.progressNotesIvh();
          //this.creatingPastSelectedIvhText();
          //this.setPastTreatmentSelected();
          //this.setPastBloodTrans();
          this.creatingPastSelectedSeizuresText();
          this.progressNotesSeizures();
          this.progressNotesAsphyxia();
          this.creatingPastSelectedAsphyxiaText();

          if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.isactive == true){
            console.log("In the If condition for the Respiratory System");
            if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist == null){
              this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist =[];
            }
            if(!(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE075'))){
              this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.push('TRE075');
              console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist);
            }
          }

          //setting today date as new date of assessment
          this.setTodayAssessmentAsphyxia();
          this.setTodayAssessmentSeizures();
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

  assessmentTempObj = {
		"asphyxiaEpisodeNumber" : 1,
		"seizuresEpisodeNumber" : 1,
		"ivhEpisodeNumber" : 1
  };

  indcreaseEpisodeNumber = function(list){
		var asphyxiaTime = new Date();
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime != undefined
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime != null) {
			if(typeof this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime === 'number') {
				asphyxiaTime = new Date(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime);
			} else {
				asphyxiaTime = new Date(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime);
			}
		}

		var seizuresTime = new Date();
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime != undefined
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime != null) {
			if(typeof this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime === 'number') {
				seizuresTime = new Date(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime);
			} else {
				seizuresTime = new Date(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime);
			}
		}

		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.episodeNumber = 1;
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.episodeNumber = 1;

		if(list != null && list != undefined && list != ""){
			for(var index=0;index<list.length;index++){
				var obj = list[index];
				if(obj[9]=='inactive'){
					if(obj[6]=="Asphyxia" && obj[3] <= asphyxiaTime.getTime()){
						this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.episodeNumber++;
					}else if(obj[6]=="Seizures" && obj[3] <= seizuresTime.getTime()){
						this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.episodeNumber++;
					}else if(obj[6]=="IVH"){
						this.assessmentTempObj.ivhEpisodeNumber++;
					}
				}
			}
		}
		return 	this.assessmentTempObj;
	}

  bolusAction = function(item) {
		if(item.bolus){
			item.freq_type =  null;
			item.frequency =  null;
		}
		this.progressNotesAsphyxia();
		this.progressNotesSeizures();
	}

  routeAction = function(item) {
		if(item.route != 'IV' && item.route != 'PO'){
			item.rate =  null;
		}
		this.progressNotesAsphyxia();
		this.progressNotesSeizures();
	}

	freqTypeAction = function(item) {
		if(item.freq_type == 'continuous'){
			item.rate =  null;
			item.frequency =  null;
		}
		this.progressNotesAsphyxia();
		this.progressNotesSeizures();
	}

  createMedInstruction = function(item){
		this.progressNotesAsphyxia();
		this.progressNotesSeizures();
		var dose = null;
		if(this.cnsSystemObj.workingWeight != null){
			if(item.dose!=null){
				dose = item.dose * (this.cnsSystemObj.workingWeight * 1);
				if(item.frequency != null
						&& item.frequency != "") {
					for(var i=0; i < this.cnsSystemObj.dropDowns.freqListMedcines.length; i++) {
						var item = this.cnsSystemObj.dropDowns.freqListMedcines[i];
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

  redirectToPrescription = function(eventName) {
      localStorage.setItem('medicationAssessment',JSON.stringify(eventName));
			localStorage.setItem('currentCNSSystemObj',JSON.stringify(this.cnsSystemObj));
			if(eventName=="Seizures") {
				localStorage.setItem('seizuresTempObj',JSON.stringify(this.seizuresTempObj));
        localStorage.setItem('prescriptionList',JSON.stringify(this.cnsSystemObj.cnseventObject.seizuresEvent.prescriptionList));
			} else if(eventName=="Asphyxia") {
        localStorage.setItem('prescriptionList',JSON.stringify(this.cnsSystemObj.cnseventObject.asphyxiaEvent.prescriptionList));
        localStorage.setItem('cnsTempObj',JSON.stringify(this.cnsTempObj));
        localStorage.setItem('asphyxiaTempObj',JSON.stringify(this.asphyxiaTempObj));
      }
      this.router.navigateByUrl('/med/medications');
		}

  composePresStartTime = function (item,id) {
    item.startdate = new Date(item.startdate);
		if(item.startdate != undefined && item.startdate != null){
			var startDate = new Date(item.startdate);
			startDate.setHours(0,0,0,0);
			var doa = new Date(this.childObject.admitDate);
			doa.setHours(0,0,0,0);
			var element = document.getElementById(id);
			console.log("comparing dates");
			if (doa <= startDate) {
			  element.style.display = 'none';
				this.progressNotesSeizures();
				console.log('composePresStartTime');
				if(item.startTimeObject == null) {
					item.startTimeObject = {};
				}
        let tempMedHours = item.startdate.getHours();
        if(tempMedHours*1 > 12){
          tempMedHours = tempMedHours-12;
          item.startTimeObject.meridian = 'PM'
        }else if(tempMedHours <12)
          {if(tempMedHours*1 < 10){
            tempMedHours = "0"+tempMedHours;
          }
          item.startTimeObject.meridian = 'AM';
        }else{
          item.startTimeObject.meridian = 'PM';
        }
        item.startTimeObject.hours = tempMedHours;
        item.startTimeObject.minutes = item.startdate.getMinutes();
				if(item.startTimeObject.hours != null && item.startTimeObject.minutes != null && item.startTimeObject.meridian != null) {
					item.starttime = item.startTimeObject.hours + ":" + item.startTimeObject.minutes + " " + item.startTimeObject.meridian;
					if(item.startdate != null) {
						item.startdate.setMinutes(item.startTimeObject.minutes * 1);
						if(item.startTimeObject.meridian == 'AM') {
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

			} else {
				//if (element) {
					element.style.display = 'block';
					item.startdate = null;
					if(item.startTimeObject != undefined
							&& item.startTimeObject != null){
							item.startTimeObject.hours = "";
							item.startTimeObject.minutes = "";
							item.startTimeObject.meridium = "";
						}
					//}
				}
		}
	}

  ageOnsetValidationEpisode = function(diseaseName) {
		// show message for validation for current age at onset, less than previous inactive episode
		// diseaseName =  asphyxia, seizures
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory.length>0){
			if(diseaseName!=null && diseaseName!=''){
				switch(diseaseName)
				{
				case 'asphyxia':
					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent != null
							&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent.length>0)
					{
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].eventstatus=='inactive'){
							if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays==this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].ageinhoursdays){
								if(parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset)<=parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays==true && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].ageinhoursdays==false){
									diff = parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset) - parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].ageatonset)*24;

								} else{
									diff = parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset)*24 - parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].ageatonset);
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

				case 'seizures':
					if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents!=null &&  this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents.length>0)
					{
						if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].eventstatus=='inactive'){
							if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays==this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].ageinhoursdays){
								if(parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset)<=parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays==true && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].ageinhoursdays==false){
									diff = parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset) - parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].ageatonset)*24;

								} else{
									diff = parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset)*24 - parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].ageatonset);
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

  calculateCNSSystemDateTime = function(){
		console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate);
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate = this.asphyxiaTempObj.assessmentDate ;
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate==null || this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate==undefined){
			console.log("date selected error");
			return;
		}

		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");
		this.dateBirth = this.dateBirth.getTime();

		var currentAgeData = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate;
		var selectedHours = parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour);
		var selectedMins = parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMin);
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMeridiem==false && selectedHours!=12) {
			selectedHours+=12;
		} else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMeridiem && selectedHours==12) {
			selectedHours = 0;
		}

		currentAgeData.setHours(selectedHours);
		currentAgeData.setMinutes(selectedMins);
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime = currentAgeData;

		this.diffTime = currentAgeData.getTime() - this.dateBirth;
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours == true)
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment = Math.round(this.diffTime/(1000*60*60));
		else
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment = Math.floor(this.diffTime/(1000*60*60*24));
	}

  setTodayAssessmentAsphyxia = function(){
		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = new Date();
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime = new Date();
  }

  calculateAgeAtAssessmentNewAsphyxia = function(){
		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");
		// this.dateBirth = this.dateBirth.getTime();


		var timeBirthArray = this.childObject.timeOfBirth.split(',');
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
		var tempCurrentAgeData = new Date(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime)
		var currentAgeData = tempCurrentAgeData;
		this.diffTime = currentAgeData.getTime() - this.dateBirth.getTime();
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours == true)
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment = (this.diffTime)/(1000*60*60)+1;
		else
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment = (this.diffTime)/(1000*60*60*24)+0;

    this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment = parseInt(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment);
		//for episodes number.........
		this.assessmentTempObj = this.indcreaseEpisodeNumber(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory);
		this.progressNotesAsphyxia();
	}

  ageOnAssessmentCalculationAsp = function(){
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours && this.AspHourFlag){

			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment *= 24;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment += this.hourDayDiffAsp;
			this.AspHourFlag = false;
		}else if(!(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours || this.AspHourFlag)){
			this.AspHourFlag = true;
			this.hourDayDiffAsp = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment%24;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment -= this.hourDayDiffAsp;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment /= 24;
		}
	}

	ageOnAssessmentCalculationSeizures = function(){
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isageofassesmentinhours && this.SeizuresHourFlag){

			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment *= 24;
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment += this.hourDayDiffSeizures;
			this.SeizuresHourFlag = false;
		}else if(!(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isageofassesmentinhours || this.SeizuresHourFlag)){
			this.SeizuresHourFlag = true;
			this.hourDayDiffSeizures = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment%24;
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment -= this.hourDayDiffSeizures;
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment /= 24;
		}
	}

  setTodayAssessmentSeizures = function(){
		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = new Date();
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime = new Date();
	}

	calculateAgeAtAssessmentNewSeizures = function(){
		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");
		var timeBirthArray = this.childObject.timeOfBirth.split(',');
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
		var tempCurrentSeizuresAgeData = new Date(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime);
		var currentSeizuresAgeData = tempCurrentSeizuresAgeData;
		this.diffTime = currentSeizuresAgeData.getTime() - this.dateBirth.getTime();
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isageofassesmentinhours == true)
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment = (this.diffTime)/(1000*60*60)+1;
		else
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment = (this.diffTime)/(1000*60*60*24)+0;

    this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment = parseInt(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment);
		//for episodes number.........
		this.assessmentTempObj = this.indcreaseEpisodeNumber(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory);
		this.progressNotesSeizures();
	}

  formatAMPM = function(date) {
		var hours = date.getHours();
		var mins = date.getMinutes();
		if(mins*1<10) {
			mins = "0"+mins;
		}
		var ampm = hours >= 12 ? 'PM' : 'AM';
		hours = hours % 12;
		hours = hours ? hours : 12;
		this.seizuresTempObj.hours = hours+":"+mins;
		this.cnsTempObj.hours = hours+":"+mins;
		console.log(this.seizuresTempObj.hours);
		if(ampm=='PM'){
			this.seizuresTempObj.meridian = false;
			this.cnsTempObj.meridian = false;
		}else if(ampm=='AM'){
			this.seizuresTempObj.meridian = true;
			this.cnsTempObj.meridian = true;
		}
	}

  submitCnsPassiveInactive = function() {
		if(this.cnsSystemObj.systemStatus == 'no'){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory.length==0 || this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory[0][5]!='yes'){
				//this.showModal("Cannot make CNS System passive.");
        this.warningMessage = "Cannot make CNS System passive.";
        this.showWarningPopUp();
			} else{
				var problemDiseases = [];
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent!=null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent.length>0 && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].eventstatus!='inactive')
					problemDiseases.push("Asphyxia");
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents!=null && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents.length>0 && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].eventstatus!='inactive')
					problemDiseases.push("Seizures");

				if(problemDiseases.length==0){
					this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime;
					this.submitCns();
				} else{
					var msg = problemDiseases.join();
					if(problemDiseases.length>1){
						//this.showModal("Cannot make CNS System passive. "+msg+" are not inactive.");
            this.warningMessage = "Cannot make CNS System passive. "+msg+" are not inactive.";
            this.showWarningPopUp();
					}
          else{
						//this.showModal("Cannot make CNS System passive. "+msg+" is not inactive.");
            this.warningMessage = "Cannot make CNS System passive. "+msg+" is not inactive.";
            this.showWarningPopUp();
          }
				}
			}
		} else if(this.cnsSystemObj.systemStatus == 'inactive'){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory.length==0 || this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory[0][9]!='no'){
				//this.showModal("Cannot make CNS System inactive.");
        this.warningMessage = "Cannot make CNS System inactive.";
        this.showWarningPopUp();
			} else{
				this.showOkPopUp('cnsSystem','Are you sure you want to Inactivate CNS Assessment');
			}
		}

		if(this.cnsSystemObj.cnseventObject.eventName=='asphyxia'){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus=='no'){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent==null){
					//this.showModal("Cannot make Asphyxia passive.");
          this.warningMessage = "Cannot make Asphyxia passive.";
          this.showWarningPopUp();
        }
        else
					this.submitCns();
			}
			else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus=='inactive'){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent!=null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].eventstatus=='no')
					this.showOkPopUp('asphyxia','Are you Sure you want to Inactivate Asphyxia Assessment');
				else{
          this.warningMessage = "Cannot make Asphyxia inactive.";
          this.showWarningPopUp();
					//this.showModal("Cannot make Asphyxia inactive.");
				}
			}
		} else if(this.cnsSystemObj.cnseventObject.eventName=='seizures'){
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus=='no'){
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents==null){
					//this.showModal("Cannot make Seizures passive.");
          this.warningMessage = "Cannot make Seizures passive.";
          this.showWarningPopUp();
        }
        else
					this.submitCns();
			}
			else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus=='inactive'){
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents!=null && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].eventstatus=='no')
					this.showOkPopUp('seizures','Are you Sure you want to Inactivate Seizures Assessment');
				else{
					//this.showModal("Cannot make Seizures inactive.");
          this.warningMessage = "Cannot make Seizures inactive.";
          this.showWarningPopUp();
				}
			}
		}
	}

  validatingCns = function(){
		if(this.cnsWhichTab == 'asphyxia'){
			if (this.hourDayDiffAsp == null || this.hourDayDiffAsp == undefined){
				this.hourDayDiffAsp = 0;
			}

			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays == this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment < this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset){
					$("#ageatassessmentvalidinputAsp").css("display","block");
					return false;
				}
				else{
					$("#ageatassessmentvalidinputAsp").css("display","none");
					return true;
				}
			}
			else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays == false && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours == true){
				var ageAtAssessment = Object.assign({},this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment);
				ageAtAssessment -= this.hourDayDiffAsp;
				ageAtAssessment /= 24;
				ageAtAssessment = Math.round(ageAtAssessment);
				if(ageAtAssessment < this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset){
					$("#ageatassessmentvalidinputAsp").css("display","block");
					return false;
				}
				else{
					$("#ageatassessmentvalidinputAsp").css("display","none");
					return true;
				}
			}
			else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays == true && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isageofassesmentinhours == false){
				var ageAtAssessment = Object.assign({},this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment);
				ageAtAssessment *= 24;
				ageAtAssessment += this.hourDayDiffAsp;
				ageAtAssessment = Math.round(ageAtAssessment);
				if(ageAtAssessment < this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset){
					$("#ageatassessmentvalidinputAsp").css("display","block");
					return false;
				}
				else{
					$("#ageatassessmentvalidinputAsp").css("display","none");
					return true;
				}
			}
			else{
				$("#ageatassessmentvalidinputAsp").css("display","none");
				return true;
			}
		}

		else if(this.cnsWhichTab == 'seizures'){
			if (this.hourDayDiffSeizures == null || this.hourDayDiffSeizures == undefined){
				this.hourDayDiffSeizures = 0;
			}

			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays == this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isageofassesmentinhours){
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment < this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset){
					$("#ageatassessmentvalidinputSeizures").css("display","block");
					return false;
				}
				else{
					$("#ageatassessmentvalidinputSeizures").css("display","none");
					return true;
				}
			}
			else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays == false && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isageofassesmentinhours == true){
				var ageAtAssessment = Object.assign({},this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment);
				ageAtAssessment -= this.hourDayDiffSeizures;
				ageAtAssessment /= 24;
				ageAtAssessment = Math.round(ageAtAssessment);
				if(ageAtAssessment < this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset){
					$("#ageatassessmentvalidinputSeizures").css("display","block");
					return false;
				}
				else{
					$("#ageatassessmentvalidinputSeizures").css("display","none");
					return true;
				}
			}
			else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays == true && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isageofassesmentinhours == false){
				var ageAtAssessment = Object.assign({},this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment);
				ageAtAssessment *= 24;
				ageAtAssessment += this.hourDayDiffSeizures;
				ageAtAssessment = Math.round(ageAtAssessment);
				if(ageAtAssessment < this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset){
					$("#ageatassessmentvalidinputSeizures").css("display","block");
					return false;
				}
				else{
					$("#ageatassessmentvalidinputSeizures").css("display","none");
					return true;
				}
			}
			else{
				$("#ageatassessmentvalidinputSeizures").css("display","none");
				return true;
			}
		}
		else{
			return true;
		}
	}

  submitCns = function(){
		if(this.validatingCns()){
      this.isLoaderVisible = true;
      this.loaderContent = 'Loading...';
			console.log("submit running");
			this.cnsSystemObj.uhid = this.childObject.uhid;
			this.cnsSystemObj.loggedUser = this.loggedUser;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime = new Date(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime);
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime = new Date(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime);
			console.log(this.cnsSystemObj);
			this.AspHourFlag = false;
			this.SeizuresHourFlag = false;
      if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore != undefined && this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore != ''){
        if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore == 'Mild'){
          this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = 1;
        }
        if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore == 'Moderate'){
          this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = 2;
        }
        if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore == 'Severe'){
          this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = 3;
        }
      }
      try
      {
        this.http.post(this.apiData.saveCns,
          this.cnsSystemObj).subscribe(res => {
            this.vanishSubmitResponseVariable = true
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
							if(eventName == "Asphyxia") {
								assessmentComment = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.progressnotes;
							} else if (eventName == "Seizures") {
								assessmentComment = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.progressnotes;
							} else if (eventName == "IVH") {
								assessmentComment = this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.progressnotes;
							}

							localStorage.setItem('assessmentComment', JSON.stringify(assessmentComment));
							if(JSON.parse(localStorage.getItem('isComingFromView'))) {
                this.router.navigateByUrl('/admis/view-profile');
              } else {
                this.router.navigateByUrl('/admis/admissionform');
              }
							localStorage.removeItem('isComingFromView');
						}

						var redirectBackToInfection = JSON.parse(localStorage.getItem('redirectBackToInfection'));
						if(redirectBackToInfection!=null){
							localStorage.setItem('isComingFromCnsSeizures',JSON.stringify(true));
							localStorage.removeItem("redirectBackToInfection");
							this.router.navigateByUrl('/infection/assessment-sheet-infection');
						}
            this.responseSaveCns =res.json();
						this.setDataCns();

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

  stopMedication = function(map_value, actionid) {
		console.log("medication stop:"+map_value+"-"+actionid);
		if(actionid == "delete"){
      try{
        this.http.request(this.apiData.deletePrescription+'/'+ map_value + '/test',)
        .subscribe(res => {
          this.prescription = res.json();
          //this.showModal(this.prescription.message, this.prescription.type);
          this.warningMessage = this.prescription.message;
          this.showWarningPopUp();
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
		}
	}

	//below code is for get updated medication
	getPrescription = function(){
		this.startTimeHH =null;
		this.startTimeMeridian = null;
		this.startTimeMM =null;
    try{
      this.http.request(this.apiData.getPrescription+ this.childObject.uhid + "/test",)
      .subscribe(res => {
        this.response = res.json();
        console.log("get prescription");
        console.log(this.response);
        this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList = this.response.activePrescription;
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

  showOrderInvestigation = function(){
		let dropDownValue = "CNS";
		this.selectedDecease = "CNS";
		for(var obj in this.cnsSystemObj.dropDowns.testsList){
			for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
				var testName = this.cnsSystemObj.dropDowns.orders[obj][index].testname;
				if(this.investOrderNotOrdered.indexOf(testName) != -1)
					this.cnsSystemObj.dropDowns.orders[obj][index].isSelected = true;
			}
		}

		this.listTestsByCategory = this.cnsSystemObj.dropDowns.orders[dropDownValue];
		/*if(this.orderSelectedText == null || this.orderSelectedText == ""){
      if(this.listTestsByCategory != undefined && this.listTestsByCategory != null){
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
		this.investOrderNotOrdered = [];
		for(var obj in this.cnsSystemObj.dropDowns.orders){
			for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
				if(this.cnsSystemObj.dropDowns.orders[obj][index].isSelected==true){
					var testName = this.cnsSystemObj.dropDowns.orders[obj][index].testname;

					this.investOrderNotOrdered.push(testName);
					if(this.investOrderSelected.indexOf(testName) == -1)
						this.cnsSystemObj.dropDowns.orders[obj][index].isSelected = false;

				} else {
					var testName = this.cnsSystemObj.dropDowns.orders[obj][index].testname;
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
		this.orderSelectedText = "";
		this.investOrderSelected = [];
		for(var obj in this.cnsSystemObj.dropDowns.orders){
			console.log(this.cnsSystemObj.dropDowns.orders[obj]);
			for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
				if(this.cnsSystemObj.dropDowns.orders[obj][index].isSelected==true){
					if(this.orderSelectedText==''){
						this.orderSelectedText =  this.cnsSystemObj.dropDowns.orders[obj][index].testname;
					}else{
						this.orderSelectedText = this.orderSelectedText +", "+ this.cnsSystemObj.dropDowns.orders[obj][index].testname;
					}
          this.cnsSystemObj.orderSelectedText = this.orderSelectedText;
					this.investOrderSelected.push(this.cnsSystemObj.dropDowns.orders[obj][index].testname);
				} else {
					var testName = this.cnsSystemObj.dropDowns.orders[obj][index].testname;
					//investOrderSelected.splice(investOrderSelected.indexOf(testName),investOrderSelected.indexOf(testName)+1);
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");

		//this.progressNotesIvh();
		this.progressNotesSeizures();
		this.progressNotesAsphyxia();
	}

	populateTestsListByCategory = function(assessmentCategory){
		this.selectedDecease = assessmentCategory;
		console.log(assessmentCategory);
		this.listTestsByCategory = this.cnsSystemObj.dropDowns.orders[assessmentCategory];
		console.log( this.listTestsByCategory);
	}

  callRespiratorySupportPopUP = function(moduleName, flag) {
		if(flag==true)
			this.respiratorySupportPopupView = true;
		else if(flag == false)
			this.respiratorySupportPopupView = false;

		this.respiratoryTempObject = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport);
		//this.refreshRDSRespObj();
		this.whichModuleRespirstorySupport = moduleName;
		console.log("Select Respiratory Support initiated");
		$("#RespiratorySupportPopUPOverlay").css("display", "block");
		$("#RespiratorySupportPopUP").addClass("showing");
	};

	closeRespiratorySupportPopUP = function(diseaseType){

		this.flowRateMessage ="";
		this.tvMessage ="";
		this.breathRateMessage ="";
		this.etMessage ="";
		this.spoMessage ="";
		//diseaseType in 'IVH', 'Asphyxia', 'Seizures'
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport = Object.assign({},this.respiratoryTempObject);
		if(this.respiratorySupportPopupView == false)
		{
			/* null all values and resp support selected */
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType = null;

//				/* new added */
//				// rsRate rsIsEndotracheal rsTubeSize rsFixation rsSpo2 rspCO2
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTubeSize = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFixation = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsSpo2 = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rspCO2 = null;

			this.rsAmplitude = "";
			this.rsEt = "";
			this.rsFio2 = "";
			this.rsFlowRate ="";
			this.rsFrequency = "";
			this.rsIt = "";
			this.rsMap = "";
			this.rsMechVentType = "";
			this.rsMv = "";
			this.rsPeep = "";
			this.rsPip = "";
			this.rsTv = "";
			this.rsVentType ="";

			var treatmentindex ;
			// for iem resp support
			for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionIvh.length; index++){
				if(this.cnsSystemObj.dropDowns.treatmentActionIvh[index].key=="TRE067")
				{
					treatmentindex = index;
					this.cnsSystemObj.dropDowns.treatmentActionIvh[treatmentindex].flag = false;

					if(this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh!=null)
						if(this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList!=null)
							if(this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList.includes('TRE067'))
								this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList.pop('TRE067');

				}
			}

			// for seizures resp support
			for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionSeizures.length; index++){
				if(this.cnsSystemObj.dropDowns.treatmentActionSeizures[index].key=="TRE068" )
				{
					treatmentindex = index;
					this.cnsSystemObj.dropDowns.treatmentActionSeizures[treatmentindex].flag = false;
					if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures!=null)
						if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null)
							if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE068'))
								this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.pop('TRE068');

				}
			}

			// for asphyxia resp support
			for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionAsphyxia.length; index++){
				if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[index].key=="TRE075")
				{
					treatmentindex = index;
					this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[treatmentindex].flag = false;

					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia!=null)
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null)
							if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE075'))
								this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.pop('TRE075');

				}
			}
		}
		$("#RespiratorySupportPopUPOverlay").css("display", "none");
		$("#RespiratorySupportPopUP").toggleClass("showing");

		//call progress notes of ivh, seizures and asphyxia
		//this.progressNotesIvh();
		this.progressNotesSeizures();
		this.progressNotesAsphyxia();
	}

  submitRespSupport = function(diseaseType){
		console.log("function submitcommonEventsInfo.respSupport");
		//console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport);
		this.isRdsValid = this.validateRds();
		if(this.isRdsValid){
			$("#RespiratorySupportPopUPOverlay").css("display", "none");
			$("#RespiratorySupportPopUP").toggleClass("showing");

			this.saveTreatmentSeizures('respiratory');
			this.saveTreatmentAsphyxia('respiratory');
			//call progress notes of ivh, seizures and asphyxia
			//this.progressNotesIvh();
			this.progressNotesSeizures();
			this.progressNotesAsphyxia();
		}
	}

	validateRds = function(){

		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsSpo2 != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsSpo2 !=""){
			this.spoMessage="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsSpo2*1 < 0 || this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsSpo2*1 > 100){
				this.spoMessage="Range 0 to 100";
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsSpo2="";
				return false;
			}
		}
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate != ""){
			this.flowRateMessage ="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate < 4 || this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate > 25){
				this.flowRateMessage = "Range 4 to 25";
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate = "";
				return false;
			}
		}
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv!= null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv != ""){
			this.tvMessage = "";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv < 1 || this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv > 30){
				this.tvMessage = "Range 1 to 20";
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv = "";
				return false;
			}
		}
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate != ""){
			this.breathRateMessage ="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate < 10 || this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate > 100){
				this.breathRateMessage ="Range 10 to 100";
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = "";
				return false;
			}
		}
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt !=""){
			this.etMessage = "";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt < 0.1 || this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt > 0.3){
				this.etMessage = "Range 0.1 to 0.3";
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt="";
				return false;
			}
		}
		return true;
	}

	refreshValuesRespiratory = function() {
		console.log("functionvalues ");
		console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport);

		this.flowRateMessage ="";
		this.tvMessage ="";
		this.breathRateMessage ="";
		this.etMessage ="";
		this.spoMessage ="";

		// below 6 fields are text, so blank them
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv = null;

		// rsRate rsIsEndotracheal rsTubeSize rsFixation rsSpo2 rspCO2 [NEW ADDED]
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTubeSize = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFixation = null;

		switch(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType)
		{
		case 'Low Flow O2':
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";

			break;
		case 'High Flow O2':
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		case 'CPAP':
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = '3';
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;

      case 'NIMV':
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
  			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
  			this.flowRateMessage ="";
  			this.tvMessage ="";
  			this.breathRateMessage ="";
  			this.etMessage ="";
  			this.spoMessage ="";
  			break;
		case 'Mechanical Ventilation':

			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType=='SIMV')
			{
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = '10';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = '3';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = '0.10';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
			}
			else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType=='PSV')
			{
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = '10';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = '3';
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
			}
			else
			{
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = null;
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;

			}
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		case 'HFO':
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = "0.10";
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = '3';
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		default:
			console.log("issue in submitting commonEventsInfo.respSupport");
		break;

		}
		console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport);
	}

  calculateBreadthRate = function(){

		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt != undefined){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = (this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt*1 + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt*1)*60;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate.toPrecision(4);
		}
	}

  calculateET = function(){

    if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt != undefined){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt = (this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate/60 - this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt*1);
			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt = this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt.toPrecision(4);
		}
  }


	fetchValuerespSupport = function(itemValue,selectedfield){

    if (selectedfield == 'ItLeft'){
			console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt);
			console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt);
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt != null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt != undefined){
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = (this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt*1 + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt*1)*60;
				this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate.toPrecision(4);
			}
		}

		switch(selectedfield)
		{
		case 'mapLeft':
			this.mapLeft = itemValue;
			break;
		case 'mapRight':
			this.mapRight = itemValue;
			break;
		case 'Fio2Left':
			this.Fio2Left = itemValue;
			break;
		case 'Fio2Right':
			this.Fio2Right = itemValue;
			break;
		case 'PipLeft':
			this.PipLeft = itemValue;
			break;
		case 'PipRight':
			this.PipRight = itemValue;
			break;
		case 'PeepLeft':
			this.PeepLeft = itemValue;
			break;
		case 'PeepRight':
			this.PeepRight = itemValue;
			break;
		case 'ItLeft':
			this.ItLeft = itemValue;
			break;
		case 'ItRight':
			this.ItRight = itemValue;
			break;

		default:
			console.log("fetchValue: no value fetched")
			break;

		}
	}

	refreshrespSupportEndoOptions = function() {
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTubeSize = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFixation = null;
	}

	// refreshValuesRespiratory = function() {
	// 	console.log("functionvalues ");
	// 	console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport);
  //
	// 	// below 6 fields are text, so blank them
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv = null;
  //
	// 	// rsRate rsIsEndotracheal rsTubeSize rsFixation rsSpo2 rspCO2 [NEW ADDED]
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTubeSize = null;
	// 	this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFixation = null;
  //
	// 	switch(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType)
	// 	{
	// 	case 'Low Flow O2':
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
  //
	// 		break;
	// 	case 'High Flow O2':
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
	// 		break;
	// 	case 'CPAP':
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = '3';
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
  //
	// 		break;
	// 	case 'Mechanical Ventilation':
  //
	// 		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType=='SIMV')
	// 		{
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = '10';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = '3';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = '0.10';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
	// 		}
	// 		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType=='PSV')
	// 		{
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = '10';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = '3';
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
	// 		}
	// 		else
	// 		{
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = null;
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
	// 			this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
  //
	// 		}
  //
	// 		break;
	// 	case 'HFO':
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = '21';
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = "0.10";
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = '3';
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;
	// 		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
	// 		break;
	// 	default:
	// 		console.log("issue in submitting commonEventsInfo.respSupport");
	// 	break;
  //
	// 	}
	// }

	respSupportPopupYes = function() {
		$("#RespSupportYesNoPopup").removeClass("showing");
		$("#respSupportOverlay").removeClass("show");

		//for ivh
		// var symptomsArr = this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList;
		// if(symptomsArr!=null)
		// {
		// 	for(var i=0;i< symptomsArr.length;i++){
		// 		if(symptomsArr[i] =='TRE067'){
		// 			symptomsArr.splice(i,1);
    //
		// 		}
		// 	}
		// 	this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList =  symptomsArr;
		// }
    //
		// var treatmentindex = 0;
		// for(index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionIvh.length; index++){
		// 	if(this.cnsSystemObj.dropDowns.treatmentActionIvh[index].key=="TRE067")
		// 	{
		// 		treatmentindex = index;
		// 		this.cnsSystemObj.dropDowns.treatmentActionIvh[treatmentindex].flag = false;
		// 	}
		// }


		//for seizures
		var symptomsArr1 = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList;
		if(symptomsArr1!=null)
		{
			for(var i=0;i< symptomsArr1.length;i++){
				if(symptomsArr1[i] =='TRE068'){
					symptomsArr1.splice(i,1);

				}
			}
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList =  symptomsArr1;
		}

		for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionSeizures.length; index++){
			if(this.cnsSystemObj.dropDowns.treatmentActionSeizures[index].key=="TRE068")
			{
				treatmentindex = index;
				this.cnsSystemObj.dropDowns.treatmentActionSeizures[treatmentindex].flag = false;
			}
		}

		//for asphyxia
		var symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist;
		if(symptomsArr!=null)
		{
			for(var i=0;i< symptomsArr.length;i++){
				if(symptomsArr[i] =='TRE075'){
					symptomsArr.splice(i,1);

				}
			}
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist =  symptomsArr;
		}

		var treatmentindex = 0;
		for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionAsphyxia.length; index++){
			if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[index].key=="TRE075")
			{
				treatmentindex = index;
				this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[treatmentindex].flag = false;
			}
		}

		this.setTreatmentSelectedTextAsphyxia();

		this.refreshRespiratoryObject();

		// call progress notes of ivh, seizures and asphyxia
		//this.progressNotesIvh();
		this.progressNotesSeizures();
		this.progressNotesAsphyxia();

		$("#RespSupportYesNoPopup").removeClass("showing");
		$("#respSupportOverlay").removeClass("show");
	};

  showRespSupportYesNoPopUP = function(diseaseType){

		this.whichModuleRespirstorySupport = diseaseType;

		$("#RespSupportYesNoPopup").addClass("showing");
		$("#respSupportOverlay").addClass("show");
	};

	closeRespSupportYesNoPopUP = function(diseaseType){
		$("#RespSupportYesNoPopup").removeClass("showing");
		$("#respSupportOverlay").removeClass("show");

		var treatmentindex = 0;

		//for ivh
		// for(index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionIvh.length; index++){
		// 	if(this.cnsSystemObj.dropDowns.treatmentActionIvh[index].key=="TRE067" && diseaseType == "IVH")
		// 	{
		// 		treatmentindex = index;
		// 		this.cnsSystemObj.dropDowns.treatmentActionIvh[treatmentindex].flag = true;
		// 		this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList.push('TRE067');
    //
		// 	}
		// }

		//for seizures
		for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionSeizures.length; index++){
			if(this.cnsSystemObj.dropDowns.treatmentActionSeizures[index].key=="TRE068" && diseaseType == "Seizures")
			{
				treatmentindex = index;
				this.cnsSystemObj.dropDowns.treatmentActionSeizures[treatmentindex].flag = true;
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.push('TRE068');
			}
		}

		//for asphyxia
		for(var index=0 ; index<this.cnsSystemObj.dropDowns.treatmentActionAsphyxia.length; index++){
			if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[index].key=="TRE075" && diseaseType == "Asphyxia")
			{
				treatmentindex = index;
				this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[treatmentindex].flag = true;
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.push('TRE075');
				this.setTreatmentSelectedTextAsphyxia();

			}
		}

		// call progress notes of ivh, seizures and asphyxia
		//this.progressNotesIvh();
		this.progressNotesSeizures();
		this.progressNotesAsphyxia();
	};

	setPastRespSupport = function(){
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.isactive!=null
				&& this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.isactive!=false){
			//for ivh
			// for(var index=0; index<this.cnsSystemObj.dropDowns.treatmentActionIvh.length; index++){
			// 	if(this.cnsSystemObj.dropDowns.treatmentActionIvh[index].key=="TRE067"){
			// 		this.cnsSystemObj.dropDowns.treatmentActionIvh[index].flag = true;
			// 		var symptomsArr = [];
			// 		if(this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList!=null
			// 				&& this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList.length>0){
			// 			symptomsArr = this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList;
			// 		}
			// 		var flag = true;
			// 		if(symptomsArr.length == 0){
			// 			symptomsArr.push("TRE067");
			// 		}
			// 		else{
			// 			for(var i=0;i< symptomsArr.length;i++){
			// 				if("TRE067" == symptomsArr[i]){
			// 					symptomsArr.splice(i,1);
			// 					flag = false;
			// 				}
			// 			}
			// 			if(flag == true){
			// 				symptomsArr.push("TRE067");
			// 			}
			// 		}
			// 		this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.treatmentactionList = symptomsArr;
      //
			// 		this.progressNotesIvh();
			// 	}
			// }

			//for seizures
			for(var index=0; index<this.cnsSystemObj.dropDowns.treatmentActionSeizures.length; index++){
				if(this.cnsSystemObj.dropDowns.treatmentActionSeizures[index].key=="TRE068"){
					this.cnsSystemObj.dropDowns.treatmentActionSeizures[index].flag = true;
					var symptomsArr = [];
					if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
							&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.length>0){
						symptomsArr = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList;
					}
					var flag = true;
					if(symptomsArr.length == 0){
						symptomsArr.push("TRE068");
					}
					else{
						for(var i=0;i< symptomsArr.length;i++){
							if("TRE068" == symptomsArr[i]){
								symptomsArr.splice(i,1);
								flag = false;
							}
						}
						if(flag == true){
							symptomsArr.push("TRE068");
						}
					}
					this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList = symptomsArr;

					this.progressNotesSeizures();
				}
			}

			//for asphyxia
			for(var index=0; index<this.cnsSystemObj.dropDowns.treatmentActionAsphyxia.length; index++){
				if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[index].key=="TRE075"){
					this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[index].flag = true;
					var symptomsArr = [];
					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.length>0){
						symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist;
					}
					var flag = true;
					if(symptomsArr.length == 0){
						symptomsArr.push("TRE075");
					}
					else{
						for(var i=0;i< symptomsArr.length;i++){
							if("TRE075" == symptomsArr[i]){
								symptomsArr.splice(i,1);
								flag = false;
							}
						}
						if(flag == true){
							symptomsArr.push("TRE075");
						}
					}
					this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist = symptomsArr;

					this.progressNotesAsphyxia();
				}
			}
		}
	}

	refreshRespiratoryObject = function(){
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip = null;

		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsRate = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIsEndotracheal = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTubeSize = null;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFixation = null;

	}

  setPresRedirectVariable = function(){
		var cnsSystemObjl = JSON.parse(localStorage.getItem('currentCNSSystemObj'));
		if(cnsSystemObjl != null){
			this.cnsSystemObj = cnsSystemObjl;
      var prescriptionList =  JSON.parse(localStorage.getItem('prescriptionList'));

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

      var assessmentName = JSON.parse(localStorage.getItem('assessmentName'));

      if(assessmentName == 'Seizures'){
        this.cnsTabVisible('seizures');
        this.ActionSeizuresVisible();
        this.initTreatmentRedirectSeizures('Medications');
        this.seizuresTempObj = JSON.parse(localStorage.getItem('seizuresTempObj'));

        this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.medicationStr = medStr;
        this.cnsSystemObj.cnseventObject.seizuresEvent.prescriptionList = prescriptionList;

      } else if(assessmentName == 'Asphyxia') {

        this.cnsTabVisible('asphyxia');
        this.ActionAsphyxiaVisible();
        this.medicationSelectAshphyxia();
        this.cnsTempObj = JSON.parse(localStorage.getItem('cnsTempObj'));
        this.asphyxiaTempObj = JSON.parse(localStorage.getItem('asphyxiaTempObj'));

        this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.medicationStr = medStr;
        this.cnsSystemObj.cnseventObject.asphyxiaEvent.prescriptionList = prescriptionList;
      }
		}

    localStorage.removeItem('cnsTempObj');
    localStorage.removeItem('asphyxiaTempObj');
    localStorage.removeItem('seizuresTempObj');
    localStorage.removeItem("assessmentName");
		localStorage.removeItem('prescriptionList');
		localStorage.removeItem("currentCNSSystemObj");
	}

	/**********************************************************Respiratory Support End*********************************************************************************/

//		..................................................Inactive popup start here......................................................................................


	showOkPopUp = function(module,inactiveMessage) {
		console.log("its ok");
		$("#OkCancelPopUp").addClass("showing");
		$("#OkCancelPopUpOverlay").addClass("show");

		if(module=='seizures'){
			//symptomatic selected text ...
			this.createSeizuresSymptomaticText();
		}

		this.inactiveMessageText = inactiveMessage;
		this.inactiveModule = module;

		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.isactive) {
			this.inactiveWithRespSupport = true;
		} else {
			this.inactiveWithRespSupport = false;
		}

	};

	closeOkPopUp = function(){
		$("#OkCancelPopUp").removeClass("showing");
		$("#OkCancelPopUpOverlay").removeClass("show");
	};

	submitInactive = function() {
		if( this.inactiveModule!=""){
			if(this.inactiveModule=="cnsSystem"){//top of the screen status
				this.cnsSystemObj.systemStatus = "inactive";
				this.submitCns();
			} else if(this.inactiveModule=="seizures"){
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus = "inactive";
				this.submitCns();
			} else if(this.inactiveModule=="ivh"){
				this.cnsSystemObj.cnseventObject.ivhEvent.currentEvent.currentIvh.eventstatus = "inactive";
				this.submitCns();
			} else if(this.inactiveModule=="asphyxia"){
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus = 'inactive';
				this.submitCns();
			}
			else if(this.inactiveModule!=null && this.inactiveModule=='apneaSymptomaticSeizuresEvent'){
				this.allowSymptomaticEventsSeizures('apnea','/respiratory/assessment-sheet-respiratory');
			}
			this.closeOkPopUp();
		}else{
      this.warningMessage = "unknown model";
      this.showWarningPopUp();
			//this.showModal("unknown model","failure");
		}

	};

//		.................................................Inactive popup ends here..............................................................................

  respiratoryOpenSeizures = function(){
    if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList == null
        || (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList != null && !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE068'))){
      this.callRespiratorySupportPopUP('Seizures', false);
    }
  }

  submitSystem = function() {
		console.log('submitSystem');
		var popupFlag = false;
		if(this.cnsSystemObj.cnseventObject.eventName == 'asphyxia') {
      this.currentEvent = 'Asphyxia';
      if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus == 'inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'Asphyxia'){
            this.showMedicationPopUp();
            return;
          }
        }
      }
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus == 'no'){
				popupFlag = true;
			}
		} else if(this.cnsSystemObj.cnseventObject.eventName == 'seizures') {
      this.currentEvent = 'Seizures';
      if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus == 'inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'Seizures'){
            this.showMedicationPopUp();
            return;
          }
        }
      }
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus == 'no') {
				popupFlag = true;
			}
		}

		if(popupFlag && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.isactive) {
			this.showPassiveConfirmation();
		} else {
			this.submitCnsPassiveInactive();
		}
	}

  switchToEvent = function(eventSource, eventTarget){
		if (eventTarget == "IVH" && eventSource=="asphyxia" && this.cnsTempObj.isIvhSavedAshphyxia) {
			return;
		} else {
			if(eventSource == "asphyxia") {
				this.saveTreatmentAsphyxia(eventTarget);
				localStorage.setItem('cnsTempObj',JSON.stringify(this.cnsTempObj));
				localStorage.setItem('isInternalSwitch', JSON.stringify(true));
			}

			localStorage.setItem('currentcnsSystemObj',JSON.stringify(this.cnsSystemObj));

			localStorage.setItem('eventSource',JSON.stringify(eventSource));
			localStorage.setItem('eventTarget',JSON.stringify(eventTarget));
			this.setDataCns();
		}
	}

	setAsphyxiaSwitchVariable = function(){
		console.log("after internal switch");
		this.cnsTabVisible('asphyxia');
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus = true;
		this.treatmentAsphyxiaTabsVisible = true;
		var currentcnsSystemObj = JSON.parse(localStorage.getItem('currentcnsSystemObj'));
		var cnsTempObj = JSON.parse(localStorage.getItem('cnsTempObj'));
		if(currentcnsSystemObj!=null){
			this.cnsSystemObj = currentcnsSystemObj;
			this.cnsTempObj = cnsTempObj;
			var eventTarget = JSON.parse(localStorage.getItem('eventTarget'));
			if(eventTarget == "IVH") {
				this.cnsTempObj.isIvhSavedAshphyxia = true;
				this.cnsTempObj.asphyxiaTreatmentStatus == "Change";
				this.progressNotesAsphyxia();
			}
		}
		localStorage.removeItem("isComingFromAsphyxia");
		localStorage.removeItem("cnsTempObj");
	}

  setAsphyxiaRespiratoryRedirectVariable = function(){
		console.log("after redirect from Respiratory to Asphyxia");
		this.cnsTabVisible('asphyxia');
		this.ActionAsphyxiaVisible();
		this.treatmentAsphyxiaTabsVisible = true;
		var currentcnsSystemObj = JSON.parse(localStorage.getItem('currentcnsSystemObj'));
		var cnsTempObj = JSON.parse(localStorage.getItem('cnsTempObj'));
		if(currentcnsSystemObj!=null){
			this.cnsSystemObj = currentcnsSystemObj;
			this.cnsTempObj = cnsTempObj;
			var eventTarget = JSON.parse(localStorage.getItem('eventTarget'));
			if(eventTarget == "RDS") {
				this.cnsTempObj.isRdsSavedAshphyxia = true;
				this.cnsTempObj.asphyxiaTreatmentStatus == "Change";
				this.progressNotesAsphyxia();
			} else if(eventTarget == "PPHN"){
				this.cnsTempObj.isPphnSavedAsphyxia = true;
				this.cnsTempObj.asphyxiaTreatmentStatus == "Change";
				this.progressNotesAsphyxia();
			}
		}
		localStorage.removeItem("isComingFromAsphyxia");
		localStorage.removeItem("cnsTempObj");
	}

  showPassiveConfirmation = function(){
		$("#passiveConfirmationOverlay").css("display", "block");
		$("#passiveConfirmationPopup").addClass("showing");
	}

	passiveConfirmationClose = function() {
		$("#passiveConfirmationOverlay").css("display", "none");
		$("#passiveConfirmationPopup").toggleClass("showing");
		$('body').css('overflow', 'auto');
	}

  submitInactiveSystem = function(flag) {
		console.log(flag);
		this.cnsSystemObj.cnseventObject.stopTreatmentFlag = flag;
		this.submitInactive();
	};

  submitPassiveSystem = function(flag) {
		console.log(flag);
		this.cnsSystemObj.cnseventObject.stopTreatmentFlag = flag;
		this.submitCnsPassiveInactive();
		this.passiveConfirmationClose();
	};

  initTreatmentVariablesSeizures = function(){
    console.log("initTreatmentVariablesSeizures");
    if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents != null
        && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents.length > 0){

      this.seizuresPreviousTreatment = this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0];

      if(this.seizuresPreviousTreatment.treatmentaction!=null){
        this.treatmentVisiblePastSeizures = true;
        this.treatmentSeizuresTabsVisible = false;
        this.seizuresTempObj.seizuresTreatmentStatus = "Continue";
      }else{
        this.treatmentVisiblePastSeizures = false;
        this.treatmentSeizuresTabsVisible = true;
      }
    } else {
      this.treatmentSeizuresTabsVisible = true;
      this.treatmentVisiblePastSeizures = false;
    }
    this.progressNotesSeizures();
  }

  initTreatmentRedirectSeizures = function(SeizersTreatment){
    console.log("initTreatmentRedirectSeizures");

    this.treatmentSeizuresTabsVisible = true;
//			this.seizuresTempObj.seizuresTreatmentStatus = 'Change';

    this.SeizersTreatment = SeizersTreatment;
    this.progressNotesSeizures();
  }

  continueTreatmentSeizures = function(){
    this.treatmentSeizuresTabsVisible = false;
    this.seizuresTempObj.seizuresTreatmentStatus = 'Continue';
    this.seizuresPreviousTreatment = this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents["0"];

    this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList = [];
    if(this.seizuresPreviousTreatment.treatmentaction != null && this.seizuresPreviousTreatment.treatmentaction != undefined){
      if(this.seizuresPreviousTreatment.treatmentaction.includes("TRE068")) {
        this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.push("TRE068");
      }
    }
    this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.resuscitationComments = "";
    this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.othertreatmentComments = "";
    this.progressNotesSeizures();
  }


  // set new values for treatment
  changeTreatmentSeizures = function() {
    this.treatmentSeizuresTabsVisible = true;
    this.seizuresTempObj.seizuresTreatmentStatus = 'Change';
    this.seizuresPreviousTreatment = this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents["0"];

    this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList = [];
    if(this.seizuresPreviousTreatment.treatmentaction != null && this.seizuresPreviousTreatment.treatmentaction != undefined){
      if(this.seizuresPreviousTreatment.treatmentaction.includes("TRE068")) {
        this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.push("TRE068");
      }
    }

    this.progressNotesSeizures();
  }

  saveTreatmentSeizures = function(treatmentType){

    switch(treatmentType)
    {
    case 'respiratory':
      if((this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList==null)
          || (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
              && !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE068'))){
        this.onCheckMultiCheckBoxValue('TRE068','treatment',true);
      }
      break;
    case 'resuscitation':
      if((this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList==null)
          || (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
              && !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE069'))){
        this.onCheckMultiCheckBoxValue('TRE069','treatment',true);
      }else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.resuscitationComments == null
          || this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.resuscitationComments == "") {
        this.onCheckMultiCheckBoxValue('TRE069','treatment',false);
      }
      break;
    case 'hypoglycemia':
      if((this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList==null)
          || (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
              && !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE070'))){
        this.onCheckMultiCheckBoxValue('TRE070','treatment',true);
      }
      break;
    case 'hypocalcemia':
      if((this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList==null)
          || (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
              && !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE071'))){
        this.onCheckMultiCheckBoxValue('TRE071','treatment',true);
      }
      break;
    case 'other':
      if((this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList==null)
          || (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
              && !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('other'))){
        this.onCheckMultiCheckBoxValue('other','treatment',true);
      }else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.othertreatmentComments == null
          || this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.othertreatmentComments == "") {
        this.onCheckMultiCheckBoxValue('other','treatment',false);
      }
      break;
    }
    this.progressNotesSeizures();
  }

  stopSeizuresRespSupport = function() {
    this.onCheckMultiCheckBoxValue('TRE068','treatment',false);
  }
//		-----------------------------------------------------------------------------------------

  calAgeOnsetSeizures = function() {
    if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays){

      if(this.hourDaySeizuresDiff == null || this.hourDaySeizuresDiff == undefined){
        this.hourDaySeizuresDiff = 0;
      }

      this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset *= 24;
      this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset += this.hourDaySeizuresDiff;
    }else{
      this.hourDaySeizuresDiff = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset % 24;
      this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset -= this.hourDaySeizuresDiff;
      this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset /= 24;
    }
    this.progressNotesSeizures();
  }

  calculateAgeAtAssessmentSeizures = function() {
    var meridianOnset = 0;
    if(this.seizuresTempObj.meridian != true){
      meridianOnset = 12;
    }
    if(this.seizuresTempObj.hours != undefined) {
      var timeStr = this.seizuresTempObj.hours.split(':');

      var hrs = 0;
      if(timeStr[0] * 1 != 12) {
        hrs = timeStr[0] * 1;
      }
      this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatassesment =
        (this.cnsSystemObj.ageAtOnset - this.currentHrs) + (hrs + meridianOnset);
    }
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

	onCheckMultiCheckBoxValue = function(keyValue,multiCheckBoxId,flagValue){
		var symptomsArr = [];
		if(multiCheckBoxId=="treatment"){

			if(keyValue == "TRE068" && flagValue == false){
				this.showRespSupportYesNoPopUP('Seizures');
				return;
			}

			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList != null && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList != ""){
				symptomsArr = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList;
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

			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList = symptomsArr;
			this.progressNotesSeizures();

		}else if(multiCheckBoxId=="medicine"){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.length>0){
				symptomsArr = this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList;
			}

			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
					if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
						var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
					for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
						if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
							var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
			this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList = symptomsArr;

			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.length>0){
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList == null || !this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE072')) {
					this.onCheckMultiCheckBoxValue('TRE072','treatment', true);
					this.onCheckAsphMultiCheckBoxValue('TRE077','treatmentActionAsph', true);
				}
			} else {
				this.onCheckMultiCheckBoxValue('TRE072','treatment', false);
				this.onCheckAsphMultiCheckBoxValue('TRE077','treatmentActionAsph', false);
			}
		}else if(multiCheckBoxId=="antibiotics"){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){
				symptomsArr = this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList;
			}

			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
					if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
						var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
					for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
						if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
							var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
			this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList = symptomsArr;

			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){
				if(!this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE073')) {
					this.onCheckMultiCheckBoxValue('TRE073','treatment', true);
					this.onCheckAsphMultiCheckBoxValue('TRE078','treatmentActionAsph', true);
				}
			} else {
				this.onCheckMultiCheckBoxValue('TRE073','treatment', false);
				this.onCheckAsphMultiCheckBoxValue('TRE078','treatmentActionAsph', false);
			}
		} else if(multiCheckBoxId=="cause"){
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizuresList != null && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizuresList != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizuresList != ""){
				symptomsArr = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizuresList;
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

			this.seizuresTempObj.selectedCauseStr = "";
			for(var indexList = 0; indexList<symptomsArr.length;indexList++){
				for(var indexCause=0;indexCause<this.cnsSystemObj.dropDowns.causeOfSeizures.length;indexCause++){
					if(symptomsArr[indexList]== this.cnsSystemObj.dropDowns.causeOfSeizures[indexCause].key){
						if(this.seizuresTempObj.selectedCauseStr==''){
							this.seizuresTempObj.selectedCauseStr = this.cnsSystemObj.dropDowns.causeOfSeizures[indexCause].value;
						}else{
							this.seizuresTempObj.selectedCauseStr += ", " + this.cnsSystemObj.dropDowns.causeOfSeizures[indexCause].value;
						}
					}
				}
			}
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizuresList = symptomsArr;
		} else if(multiCheckBoxId=="plan"){
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.planList != null && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.planList != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.planList != ""){
				symptomsArr = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.planList;
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
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.planList = symptomsArr;
		}
		this.progressNotesSeizures();
	}

	//      .....................................code start for redirect to symptomatic..................................................................

	okCancelPopupSymptomaticSeizures = function(symptom, url){
		if(symptom=='apneaSymptomaticSeizuresEvent'){
			if(this.seizuresTempObj.symptomatic.apnea == true) {
				this.showOkPopUp(symptom, url);
			} else {
				this.seizuresTempObj.symptomatic.apnea = false;
			}
		}
		this.createSeizuresSymptomaticText();
	}

	allowSymptomaticEventsSeizures = function(symptomaticEvent, symptomaticEventUrl){
		this.seizuresTempObj.symptomaticEvent = symptomaticEvent;
		this.seizuresTempObj.symptomaticEventUrl = symptomaticEventUrl;
		localStorage.setItem('isComingFromCNSSymptomaticEvent',JSON.stringify(true));
		localStorage.setItem('currentCnsSeizuresObject',JSON.stringify(this.cnsSystemObj));
		localStorage.setItem('seizuresTempObj',JSON.stringify(this.seizuresTempObj));
		localStorage.setItem('isComingFromSeizures', JSON.stringify(true));
		this.router.navigateByUrl(symptomaticEventUrl);
	}

	setSeizuresRespRedirectVariable = function(){
		console.log("after redirect from resp to seizures");
		this.cnsTabVisible('seizures');
		var currentCnsSeizuresObject = JSON.parse(localStorage.getItem('currentCnsSeizuresObject'));
		var seizuresTempObj = JSON.parse(localStorage.getItem('seizuresTempObj'));
		if(currentCnsSeizuresObject!=null){
			this.cnsSystemObj = currentCnsSeizuresObject;
			this.seizuresTempObj = seizuresTempObj;
		}
		localStorage.removeItem("isComingFromSeizures");
		localStorage.removeItem("currentCnsSeizuresObject");
		localStorage.removeItem("seizuresTempObj");
	}

	//      ............................code end for redirect to symptomatic..............................................

	//      ...............................code start for redirect to feeds..............................................

	redirectToFeedNutritionSeizures = function(){
		localStorage.setItem('isComingFromCNSTreatment',JSON.stringify(true));
		localStorage.setItem('currentCnsSeizuresObject',JSON.stringify(this.cnsSystemObj));
		localStorage.setItem('seizuresTempObj',JSON.stringify(this.seizuresTempObj));
		localStorage.setItem('isComingFromSeizures', JSON.stringify(true));
		this.router.navigateByUrl('/feed/nutrition');
	}

  redirectToRespiratory = function(eventSource, eventTarget){

		if (eventTarget == "RDS" && eventSource=="asphyxia" && this.cnsTempObj.isRdsSavedAshphyxia) {
			return;
		} else if (eventTarget == "PPHN" && eventSource=="asphyxia" && this.cnsTempObj.isPphnSavedAsphyxia) {
			return;
		} else {
			if(eventSource == "asphyxia") {
				this.saveTreatmentAsphyxia(eventTarget);
				localStorage.setItem('cnsTempObj',JSON.stringify(this.cnsTempObj));
				localStorage.setItem('isComingFromAsphyxia', JSON.stringify(true));
			}

			localStorage.setItem('isComingFromCNSEvent',JSON.stringify(true));
			localStorage.setItem('currentcnsSystemObj',JSON.stringify(this.cnsSystemObj));
			localStorage.setItem('eventTarget',JSON.stringify(eventTarget));
      this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
		}
	}
	//      .....................................code end for redirect to feeds..................................................................

	//      .....................................code start for setting feeds variables..................................................................
	setSeizuresFeedsRedirectVariable = function(){
		var currentCnsObj = JSON.parse(localStorage.getItem('currentCnsSeizuresObject'));
		var tempObj = JSON.parse(localStorage.getItem('seizuresTempObj'));
		if(currentCnsObj != null){
			this.cnsSystemObj = currentCnsObj;
			this.seizuresTempObj = tempObj;
			var infoFeedNutrition = JSON.parse(localStorage.getItem('infoFeedNutrition'));

			//bolus fields
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bolusType = infoFeedNutrition.bolusType;
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bolusVolume = infoFeedNutrition.bolusVolume;
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bolusTotal = infoFeedNutrition.bolusTotalFeed;

			//calcium fields
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.calciumMlkgday = infoFeedNutrition.calciumVolume;
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.calciumTotal = infoFeedNutrition.calciumTotal;

			this.cnsTabVisible('seizures');
		}
		localStorage.removeItem("isComingFromSeizures");
		localStorage.removeItem("currentCnsSeizuresObject");
		localStorage.removeItem("seizuresTempObj");
	}


	//      .....................................code end for setting feeds variables..................................................................

	createSeizuresTypeText = function() {
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.seizuresType = "";
		var tempSeizuresTypeStr = "";
		if(this.seizuresTempObj.seizuresType!=null){

			if(this.seizuresTempObj.seizuresType.Tonic==true){
				tempSeizuresTypeStr = "Tonic";
			}

			if(this.seizuresTempObj.seizuresType.Clonic==true){
				if(tempSeizuresTypeStr=='')
					tempSeizuresTypeStr = "Clonic";
				else
					tempSeizuresTypeStr += ", " +  "Clonic";
			}

			if(this.seizuresTempObj.seizuresType.tonicClonic==true){
				if(tempSeizuresTypeStr=='')
					tempSeizuresTypeStr = "Tonic Clonic";
				else
					tempSeizuresTypeStr += ", " +  "Tonic Clonic";
			}

			if(this.seizuresTempObj.seizuresType.Myoclonic==true){
				if(tempSeizuresTypeStr=='')
					tempSeizuresTypeStr = "Myoclonic";
				else
					tempSeizuresTypeStr += ", " +  "Myoclonic";
			}

			if(this.seizuresTempObj.seizuresType.Subtle==true){
				if(tempSeizuresTypeStr=='')
					tempSeizuresTypeStr = "Subtle";
				else
					tempSeizuresTypeStr += ", " +  "Subtle";
			}

			if(this.seizuresTempObj.seizuresType.othersType==true && this.seizuresTempObj.seizuresType.othersValue != null
					&& this.seizuresTempObj.seizuresType.othersValue != ''){
				if(tempSeizuresTypeStr=='')
					tempSeizuresTypeStr = this.seizuresTempObj.seizuresType.othersValue;
				else
					tempSeizuresTypeStr += ", " + this.seizuresTempObj.seizuresType.othersValue;
			}

			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.seizuresType = tempSeizuresTypeStr;
			this.progressNotesSeizures();
		}
	}

	createSeizuresSymptomaticText = function(){
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.symptomaticValue = "";
		var tempSymptomaticStr = "";
		if(this.seizuresTempObj.symptomatic!=null){
			if(this.seizuresTempObj.symptomatic.apnea==true){
				tempSymptomaticStr = "Apnea";
			}

			if(this.seizuresTempObj.symptomatic.SympTachycardia==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Tachycardia";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Tachycardia";
			}

			if(this.seizuresTempObj.symptomatic.Sympbradycardia==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Bradycardia";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Bradycardia";
			}

			if(this.seizuresTempObj.symptomatic.Sympdesaturation==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Desaturation";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Desaturation";
			}

			if(this.seizuresTempObj.symptomatic.Symppallor==true){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = "Pallor";
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Pollar";
			}

			if(this.seizuresTempObj.symptomatic.SympOther==true && this.seizuresTempObj.symptomatic.othersText != null && this.seizuresTempObj.symptomatic.othersText != ''){
				if(tempSymptomaticStr=='')
					tempSymptomaticStr = this.seizuresTempObj.symptomatic.othersText;
				else
					tempSymptomaticStr =  tempSymptomaticStr + ", " + this.seizuresTempObj.symptomatic.othersText;
			}

			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.symptomaticValue = tempSymptomaticStr;
			this.progressNotesSeizures();
		}
	}

	creatingPastSelectedSeizuresText =function(){
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents != null && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents != ""){
			var pastSeizures = this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0];

			if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations!=null
					&& this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations.length>0){
				for(var indexOrder = 0; indexOrder<this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations.length;indexOrder++){
					if(pastSeizures.sacnsseizuresid==this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations[indexOrder].assesmentid){
						if(this.seizuresTempObj.pastSelectedOrderInvestigationStr=='' || this.seizuresTempObj.pastSelectedOrderInvestigationStr==undefined){
							this.seizuresTempObj.pastSelectedOrderInvestigationStr = this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations[indexOrder].testname;
						}else{
							this.seizuresTempObj.pastSelectedOrderInvestigationStr += ", " + this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations[indexOrder].testname;
						}
					}
				}
			}
			if(pastSeizures.causeofSeizures!=null && pastSeizures.causeofSeizures!=''){
				this.seizuresTempObj.pastSelectedCauseStr = "";
				var list = pastSeizures.causeofSeizures.replace("[","").replace("]","").split(",");
				for(var indexCause = 0; indexCause<list.length;indexCause++){
					for(var indexDrop = 0; indexDrop<this.cnsSystemObj.dropDowns.causeOfSeizures.length;indexDrop++){
						if(list[indexCause].replace(" ","")==this.cnsSystemObj.dropDowns.causeOfSeizures[indexDrop].key){
							if(this.seizuresTempObj.pastSelectedCauseStr=='' || this.seizuresTempObj.pastSelectedCauseStr==undefined){
								this.seizuresTempObj.pastSelectedCauseStr = this.cnsSystemObj.dropDowns.causeOfSeizures[indexDrop].value;
							}else{
								this.seizuresTempObj.pastSelectedCauseStr += ", "+ this.cnsSystemObj.dropDowns.causeOfSeizures[indexDrop].value;
							}
						}
					}
				}
			}
		}
	}

  progressNotesSeizures = function() {
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus == "No") {
			console.log("in seizures progress notes event is no");
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.progressnotes = "Baby has no Episode of Seizures.";
			return;
		}

		console.log(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures);
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.progressnotes= "";
		var template = "";

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents ==null || this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents.length<=0 ||
				this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[0].eventstatus=='inactive'){
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.episodeNumber != null){
				template += "Episode number: "+ this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.episodeNumber + ". ";
			}
		}

		if(this.pastAgeInactiveSeziures) {
			if (this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.noofseizures > 0){
				template += "Baby had " + this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.noofseizures
				+ " episodes of Seizures since last assessment. ";
			} else {
				template += "No new episode of Seizures since last assessment. ";
			}
		} else {
			template += "Baby developed Seizures at the age of ";
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageinhoursdays){
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset*1 == 1 || this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset*1 ==0){
					template += this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset +" hr";
				}else{
					template += this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset +" hrs";
				}
			}else{
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset*1 == 1 || this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset*1 ==0){
					template += this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset +" day";
				}else{
					template += this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.ageatonset +" days";
				}
			}
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastEpisodeList != null
					&& this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastEpisodeList.length > 0) {

				var lastEvent = this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastEpisodeList[0];
				var str = "";
				if(null != lastEvent.seizureDuration) {
					str = ", duration was " + lastEvent.seizureDuration;
					if(lastEvent.seizureDuration > 1) {
						str += " mins";
					} else {
						str += " min";
					}
				}

				if(null != lastEvent.seizureType && lastEvent.seizureType != "") {
					str += ", " + lastEvent.seizureType +" Seizures";
				}

				if(lastEvent.symptomaticStatus && null != lastEvent.symptomaticValue) {
					if(lastEvent.symptomaticValue.indexOf(",")== -1) {
						str += ", associated symptoms was " + lastEvent.symptomaticValue;
					} else {
						str += ", associated symptoms were " + lastEvent.symptomaticValue;
					}
				}
      }
      template += str + ". ";
		}

		if(this.orderSelectedText != "" && this.orderSelectedText != undefined){
			if(this.orderSelectedText.indexOf(",")== -1){
				template = template + "Investigation ordered is "+this.orderSelectedText+". ";
			} else {
				template = template + "Investigation ordered are "+this.orderSelectedText+". ";
			}
		}

		var treatmentPrefix = "";
		if(this.pastAgeInactiveSeziures && this.seizuresTempObj != null && this.seizuresTempObj.seizuresTreatmentStatus != null) {
			if(this.seizuresTempObj.seizuresTreatmentStatus == 'Continue') {
				treatmentPrefix = "Treatment continue to ";
			} else {
				treatmentPrefix = "Treatment changed to ";
			}
		} else {
			treatmentPrefix = "Treatment given ";
		}

		//treatment
		var treatmentProgressNoteStr = "";

		//resp suppport
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("TRE068")!=-1){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType!=""){

				var respStr = "";
				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType!="") {
					respStr += "Mechanical Vent Type: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap!="") {
          if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType == 'CPAP') {
            respStr += "MAP/PEEP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap + ", ";
          } else {
            respStr += "MAP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap + ", ";
          }
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency!="") {
					respStr += "Frequency: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv!="") {
					respStr += "TV: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude!="") {
					respStr += "Amplitude: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2!="") {
					respStr += "FiO2: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 + " %, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate!="") {
					respStr += "Flow Rate: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate + " liters/Min, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip!="") {
					respStr += "PIP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip + " cm H2O, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep!="") {
					respStr += "PEEP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep + " cm H2O, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt!="") {
					respStr += "IT: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt + " secs, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt!="") {
					respStr += "ET: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt + " secs, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv!="") {
					respStr += "MV: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv + ", ";
				}

				if(respStr != ""){
					respStr = respStr.substring(0, (respStr.length - 2));
					treatmentProgressNoteStr += this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType + " (" + respStr + "). ";
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("TRE069")!=-1){
			treatmentProgressNoteStr +="Resuscitation Done";
			if(null != this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.resuscitationComments
					&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.resuscitationComments != '') {
				treatmentProgressNoteStr += " (" + this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.resuscitationComments + ")"
			}
		}

		// Blood sugar checked, value is normal/low(___ mg/dL)
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel != null &&
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel != "") {
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel < 40) {
				treatmentProgressNoteStr += ", Blood sugar checked, value is low ("
					+ this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel + " mg/dL)";
			} else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel > 150) {
				treatmentProgressNoteStr += ", Blood sugar checked, value is high ("
					+ this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel + " mg/dL)";
			}else {
				treatmentProgressNoteStr += ", Blood sugar checked, value is normal ("
					+ this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.bloodSugarLevel + " mg/dL)";
			}
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("TRE070")!=-1){
			treatmentProgressNoteStr += ", Hypoglycemia managed";
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.calciumLevel != null &&
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.calciumLevel != "") {
			treatmentProgressNoteStr += ", Calcium value is "
				+ this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.calciumLevel + " mg/dL";
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("TRE071")!=-1){
			treatmentProgressNoteStr += ", Hypocalcemia managed";
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("TRE072")!=-1){
			this.medicineSelectedStr="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList != null){
				for(var indexMed =0; indexMed<this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.length; indexMed++){

					if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].medicinename != null){
						//for frequency...
						var currentFrequency = "";
						for(var indexFreq=0; indexFreq < this.cnsSystemObj.dropDowns.freqListMedcines.length; indexFreq++){
							if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].frequency ==
								this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqid){
								currentFrequency = this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqvalue;
							}
						}
						if(this.medicineSelectedStr==""){
							this.medicineSelectedStr = this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}else{
							this.medicineSelectedStr = this.medicineSelectedStr + ", " + this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}
					}
				}
				if(this.medicineSelectedStr != ""){
					if(treatmentProgressNoteStr != '') {
						treatmentProgressNoteStr += ", ";
					}
					if(this.medicineSelectedStr.indexOf(",")==-1){
						treatmentProgressNoteStr += "Medicine (" + this.medicineSelectedStr + ")";
					}else{
						treatmentProgressNoteStr += "Medicines (" + this.medicineSelectedStr + ")";
					}
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("TRE073")!=-1){
			this.antibioticsSelectedStr="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList != null){
				for(var indexMed =0; indexMed<this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length; indexMed++){
					if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].medicinename != null){
						//for frequency...
						var currentFrequency = "";
						for(var indexFreq=0; indexFreq < this.cnsSystemObj.dropDowns.freqListMedcines.length; indexFreq++){
							if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].frequency ==
								this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqid){
								currentFrequency = this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqvalue;
							}
						}
						if(this.antibioticsSelectedStr==""){
							this.antibioticsSelectedStr = this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}else{
							this.antibioticsSelectedStr = this.antibioticsSelectedStr + ", " + this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}
					}
				}
				if(this.antibioticsSelectedStr != ""){
					if(treatmentProgressNoteStr != '') {
						treatmentProgressNoteStr += ", ";
					}
					if(this.antibioticsSelectedStr.indexOf(",")==-1){
						treatmentProgressNoteStr += "Antibiotic (" + this.antibioticsSelectedStr + ")";
					}else{
						treatmentProgressNoteStr += "Antibiotics (" + this.antibioticsSelectedStr + ")";
					}
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList!=null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.indexOf("other")!=-1
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.othertreatmentComments != null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.othertreatmentComments != ""){
			treatmentProgressNoteStr += ", " + this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.othertreatmentComments;
		}

    if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.medicationStr != null
      && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.medicationStr != '') {
      if(treatmentProgressNoteStr != '') {
        treatmentProgressNoteStr += ", ";
      }
      treatmentProgressNoteStr += this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.medicationStr;
    }

		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText = treatmentProgressNoteStr;
		if(treatmentProgressNoteStr != "") {
			template += treatmentPrefix + treatmentProgressNoteStr + ".";
		}

		// Cause progress note
		if(this.cnsSystemObj.dropDowns.causeOfSeizures!=null && this.cnsSystemObj.dropDowns.causeOfSeizures.length>0){
			if(this.seizuresTempObj != undefined && this.seizuresTempObj != null
					&& this.seizuresTempObj.selectedCauseStr != undefined && this.seizuresTempObj.selectedCauseStr!=''){
				var causeStr = this.seizuresTempObj.selectedCauseStr;
				if(causeStr.includes("other") && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofseizuresOther != null
						&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofseizuresOther != "") {
					causeStr = causeStr.replace("other", this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofseizuresOther);
				}

				if(this.seizuresTempObj.selectedCauseStr.includes(",")){
					template += " " + causeStr + " are most likely causes of Seizures.";
				}
				else{
					template += " " + causeStr + " is most likely cause of Seizures.";
				}
			}
		}

		//plan seizures
		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime !=null &&
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime!=""
					&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestimeType!=null){
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime == "1"){
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestimeType=='mins'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime+" min.";
				}else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestimeType=='hours'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime+" hour.";
				} else {
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime+" day.";
				}
			}
			else{
				if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestimeType=='mins'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime+" mins.";
				}else if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestimeType=='hours'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime+" hours.";
				} else {
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.reassestime+" days.";
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.otherplanComments !=null){
			template += " " + this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.otherplanComments+".";
		}

		while(template.includes('.,')) {
			template = template.replace('.,','. ');
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText.replace('.,','. ');
		}

		while(template.includes('. ,')) {
			template = template.replace('. ,','. ');
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText.replace('. ,','. ');
		}

		while(template.includes('. .')) {
			template = template.replace('. .','. ');
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText.replace('. .','. ');
		}

		while(template.includes('..')) {
			template = template.replace('..','. ');
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText.replace('..','. ');
		}

		while(template.includes('  ')) {
			template = template.replace('  ',' ');
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentSelectedText.replace('  ',' ');
		}

		console.log(template);
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.progressnotes = template;

		if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus != null && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus != ""){
			if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.eventstatus.toUpperCase()=='YES')
				this.ageOnsetValidationEpisode('seizures');
		}
	}

  currentData = function(){
		console.log(JSON.stringify(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent));
	}

  respSelectAshphyxia = function(){
		this.cnsTempObj.isRespSelectAshphyxia = true;
		this.cnsTempObj.isHypoglycemiaSelectAshphyxia = false;
		this.cnsTempObj.isIvFluidSelectAshphyxia = false;
		this.cnsTempObj.isAntibioticsSelectAshphyxia = false;
		this.cnsTempObj.isMedicationSelectAshphyxia = false;
		this.cnsTempObj.isOtherSelectAshphyxia = false;
	}
	hypoglycemiaSelectAshphyxia = function(){
		this.cnsTempObj.isRespSelectAshphyxia = false;
		this.cnsTempObj.isHypoglycemiaSelectAshphyxia = true;
		this.cnsTempObj.isIvFluidSelectAshphyxia = false;
		this.cnsTempObj.isAntibioticsSelectAshphyxia = false;
		this.cnsTempObj.isMedicationSelectAshphyxia = false;
		this.cnsTempObj.isOtherSelectAshphyxia = false;
	}
	ivFluidSelectAshphyxia = function(){
		this.cnsTempObj.isRespSelectAshphyxia = false;
		this.cnsTempObj.isHypoglycemiaSelectAshphyxia = false;
		this.cnsTempObj.isIvFluidSelectAshphyxia = true;
		this.cnsTempObj.isAntibioticsSelectAshphyxia = false;
		this.cnsTempObj.isMedicationSelectAshphyxia = false;
		this.cnsTempObj.isOtherSelectAshphyxia = false;
	}
	antibioticsSelectAshphyxia = function(){
		this.cnsTempObj.isRespSelectAshphyxia = false;
		this.cnsTempObj.isHypoglycemiaSelectAshphyxia = false;
		this.cnsTempObj.isIvFluidSelectAshphyxia = false;
		this.cnsTempObj.isAntibioticsSelectAshphyxia = true;
		this.cnsTempObj.isMedicationSelectAshphyxia = false;
		this.cnsTempObj.isOtherSelectAshphyxia = false;
	}
	medicationSelectAshphyxia = function(){
		this.cnsTempObj.isRespSelectAshphyxia = false;
		this.cnsTempObj.isHypoglycemiaSelectAshphyxia = false;
		this.cnsTempObj.isIvFluidSelectAshphyxia = false;
		this.cnsTempObj.isAntibioticsSelectAshphyxia = false;
		this.cnsTempObj.isMedicationSelectAshphyxia = true;
		this.cnsTempObj.isOtherSelectAshphyxia = false;
	}
	otherSelectAshphyxia = function(){
		this.cnsTempObj.isRespSelectAshphyxia = false;
		this.cnsTempObj.isHypoglycemiaSelectAshphyxia = false;
		this.cnsTempObj.isIvFluidSelectAshphyxia = false;
		this.cnsTempObj.isAntibioticsSelectAshphyxia = false;
		this.cnsTempObj.isMedicationSelectAshphyxia = false;
		this.isResuscitationSelectAshphyxia = false;
		this.cnsTempObj.isOtherSelectAshphyxia = true;
	}

	shockSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = true;
		this.cnsTempObj.isAcidosisSelectAshphyxia = false;
		this.cnsTempObj.isRenalSelectAshphyxia = false;
		this.cnsTempObj.isRdsSelectAshphyxia = false;
		this.cnsTempObj.isPphnSelectAshphyxia = false;
		this.cnsTempObj.isChfSelectAshphyxia = false;
		this.cnsTempObj.isIvhSelectAshphyxia = false;
	}
	acidosisSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = false;
		this.cnsTempObj.isAcidosisSelectAshphyxia = true;
		this.cnsTempObj.isRenalSelectAshphyxia = false;
		this.cnsTempObj.isRdsSelectAshphyxia = false;
		this.cnsTempObj.isPphnSelectAshphyxia = false;
		this.cnsTempObj.isChfSelectAshphyxia = false;
		this.cnsTempObj.isIvhSelectAshphyxia = false;
	}
	renalSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = false;
		this.cnsTempObj.isAcidosisSelectAshphyxia = false;
		this.cnsTempObj.isRenalSelectAshphyxia = true;
		this.cnsTempObj.isRdsSelectAshphyxia = false;
		this.cnsTempObj.isPphnSelectAshphyxia = false;
		this.cnsTempObj.isChfSelectAshphyxia = false;
		this.cnsTempObj.isIvhSelectAshphyxia = false;
	}
	rdsSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = false;
		this.cnsTempObj.isAcidosisSelectAshphyxia = false;
		this.cnsTempObj.isRenalSelectAshphyxia = false;
		this.cnsTempObj.isRdsSelectAshphyxia = true;
		this.cnsTempObj.isPphnSelectAshphyxia = false;
		this.cnsTempObj.isChfSelectAshphyxia = false;
		this.cnsTempObj.isIvhSelectAshphyxia = false;
	}
	pphnSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = false;
		this.cnsTempObj.isAcidosisSelectAshphyxia = false;
		this.cnsTempObj.isRenalSelectAshphyxia = false;
		this.cnsTempObj.isRdsSelectAshphyxia = false;
		this.cnsTempObj.isPphnSelectAshphyxia = true;
		this.cnsTempObj.isChfSelectAshphyxia = false;
		this.cnsTempObj.isIvhSelectAshphyxia = false;
	}
	chfSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = false;
		this.cnsTempObj.isAcidosisSelectAshphyxia = false;
		this.cnsTempObj.isRenalSelectAshphyxia = false;
		this.cnsTempObj.isRdsSelectAshphyxia = false;
		this.cnsTempObj.isPphnSelectAshphyxia = false;
		this.cnsTempObj.isChfSelectAshphyxia = true;
		this.cnsTempObj.isIvhSelectAshphyxia = false;
	}
	ivhSelectAshphyxia = function(){
		this.cnsTempObj.isShockSelectAshphyxia = false;
		this.cnsTempObj.isAcidosisSelectAshphyxia = false;
		this.cnsTempObj.isRenalSelectAshphyxia = false;
		this.cnsTempObj.isRdsSelectAshphyxia = false;
		this.cnsTempObj.isPphnSelectAshphyxia = false;
		this.cnsTempObj.isChfSelectAshphyxia = false;
		this.cnsTempObj.isIvhSelectAshphyxia = true;
	}
	initTreatmentVariablesAsphyxia = function(){
		console.log("initTreatmentVariablesAsphyxia");
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent != null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent.length > 0){

			this.asphyxiaPreviousTreatment = this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0];

			if(this.asphyxiaPreviousTreatment.treatmentaction!=null){
				this.treatmentVisiblePastAsphyxia = true;
				this.treatmentAsphyxiaTabsVisible = false;
				this.cnsTempObj.asphyxiaTreatmentStatus = 'Continue';
			}else{
				this.treatmentVisiblePastAsphyxia = false;
				this.treatmentAsphyxiaTabsVisible = true;
			}
		} else {
			this.treatmentAsphyxiaTabsVisible = true;
			this.treatmentVisiblePastAsphyxia = false;
		}
		this.progressNotesAsphyxia();
	}

	calAgeOnsetAsphy = function(){
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays){

			if(this.hourDayAsphyDiff == null || this.hourDayAsphyDiff == undefined){
				this.hourDayAsphyDiff = 0;
			}

			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset *= 24;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset += this.hourDayAsphyDiff;
		}else{
			this.hourDayAsphyDiff = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset % 24;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset -= this.hourDayAsphyDiff;
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset /= 24;
		}
    this.progressNotesAsphyxia();
	};
  calculateAgeAtAssessmentAsphyxia = function() {
		var meridianOnset = 0;
		if(this.cnsTempObj.meridian != true){
			meridianOnset = 12;
		}
		if(this.cnsTempObj.hours != undefined) {
			var timeStr = this.cnsTempObj.hours.split(':');

			var hrs = 0;
			if(timeStr[0] * 1 != 12) {
				hrs = timeStr[0] * 1;
			}
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatassesment =
				(this.cnsSystemObj.ageAtOnset - this.currentHrs) + (hrs + meridianOnset);
		}
	}
	onCheckMultiCheckBoxAsphyxiaValue = function(Value,multiCheckBoxId,flagValue){
		var symptomsArr = [];
		if(multiCheckBoxId=="treatment"){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != ""){
				symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}

			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist = symptomsArr;
			this.progressNotesSeizures();
			console.log("line No : 2284");
			console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist );
		}
		else if(multiCheckBoxId=="riskFactorAsphyxia"){

			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList!=null){
				symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList = symptomsArr;
			console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList);
		}else if(multiCheckBoxId=="causeOfAsphyxia") {
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause!=null){
				symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause = symptomsArr;
			console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause);
		}
		this.progressNotesAsphyxia();
	}

	continueTreatmentAsphyxia = function(){
		this.treatmentAsphyxiaTabsVisible = false;
		this.cnsTempObj.asphyxiaTreatmentStatus = 'Continue';
		this.asphyxiaPreviousTreatment = this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0];

		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist = [];
    if(this.asphyxiaPreviousTreatment.treatmentaction != null && this.asphyxiaPreviousTreatment.treatmentaction != undefined){
  		if(this.asphyxiaPreviousTreatment.treatmentaction.includes("TRE075")) {
  			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.push("TRE075");
  		}
    }
		this.progressNotesAsphyxia();
	}

	// set new values for treatment
	changeTreatmentAsphyxia = function() {
		this.treatmentAsphyxiaTabsVisible = true;
		this.cnsTempObj.asphyxiaTreatmentStatus = 'Change';
		this.asphyxiaPreviousTreatment = this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0];

		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist = [];
    if(this.asphyxiaPreviousTreatment.treatmentaction != null && this.asphyxiaPreviousTreatment.treatmentaction != undefined){
  		if(this.asphyxiaPreviousTreatment.treatmentaction.includes("TRE075")) {
  			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.push("TRE075");
  		}
    }
		this.progressNotesAsphyxia();
	}

	saveTreatmentAsphyxia = function(treatmentType){

		switch(treatmentType)
		{
		case 'respiratory':
			if((this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist==null)
					|| (this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE075'))){
				this.onCheckMultiCheckBoxAsphyxiaValue('TRE075','treatment',true);
			}
			break;
		case 'hypoglycemia':
			if((this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist==null)
					|| (this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE089'))){
				this.onCheckMultiCheckBoxAsphyxiaValue('TRE089','treatment',true);
			}
			break;

		case 'acidosis':
			this.cnsTempObj.isAcidosisSavedAshphyxia = true;
			if((this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist==null)
					|| (this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('other'))){
				this.onCheckMultiCheckBoxAsphyxiaValue('other','treatment',true);
			}
			break;

		case 'RDS':
			this.cnsTempObj.isRdsSavedAshphyxia = true;
			if((this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist==null)
					|| (this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('other'))){
				this.onCheckMultiCheckBoxAsphyxiaValue('other','treatment',true);
			}
			break;

		case 'PPHN':
			this.cnsTempObj.isPphnSavedAsphyxia = true;
			if((this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist==null)
					|| (this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('other'))){
				this.onCheckMultiCheckBoxAsphyxiaValue('other','treatment',true);
			}
			break;

		case 'IVH':
			this.cnsTempObj.isIvhSavedAshphyxia = true;
			if((this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist==null)
					|| (this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
							&& !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('other'))){
				this.onCheckMultiCheckBoxAsphyxiaValue('other','treatment',true);
			}
			break;
		}

		this.progressNotesSeizures();
	}

  createAsphyHistoryText = function(textValue){
		if(textValue!=null){
			this.needforPPVTime = textValue;
		}

		var tempHistory = "";
		if(this.cnsTempObj.history.delayedcry!=null&&this.cnsTempObj.history.delayedcry==true){
			if(tempHistory==""){
				tempHistory = "delayed cry";
			}else{
				tempHistory = tempHistory.concat(", delayed cry");
			}
		}

		if(this.cnsTempObj.history.depressedbirth!=null&&this.cnsTempObj.history.depressedbirth==true){
			if(tempHistory==""){
				tempHistory = "depressed at birth";
			}else{
				tempHistory = tempHistory.concat(", depressed at birth");
			}
		}

		if(this.cnsTempObj.history.needppv!=null&&this.cnsTempObj.history.needppv==true){
			if(tempHistory==""){
				if(this.needforPPVTime==""){
					tempHistory = "need for ppv";
				}else{
					if(this.needforPPVTime*1 < 2) {
						tempHistory = "need for ppv ("+this.needforPPVTime+" sec)";
					} else {
						tempHistory = "need for ppv ("+this.needforPPVTime+" secs)";
					}
				}
			}else{
				if(this.needforPPVTime==""){
					tempHistory = tempHistory.concat(", need for ppv");
				}else{
					if(this.needforPPVTime*1 < 2) {
						tempHistory = tempHistory.concat(", need for ppv ("+this.needforPPVTime+" sec)");
					} else {
						tempHistory = tempHistory.concat(", need for ppv ("+this.needforPPVTime+" secs)");
					}

				}
			}
		}

		if(this.cnsTempObj.history.medication!=null&&this.cnsTempObj.history.medication==true){
			if(tempHistory==""){
				tempHistory = "medications";
			}else{
				tempHistory = tempHistory.concat(", medications");
			}
		}

		if(this.cnsTempObj.history.oxyegenrequirement!=null&&this.cnsTempObj.history.oxyegenrequirement==true){
			if(tempHistory==""){
				tempHistory = "oxygen requirement";
			}else{
				tempHistory = tempHistory.concat(", oxygen requirement");
			}
		}

		if(this.cnsTempObj.history.chestcompression!=null&&this.cnsTempObj.history.chestcompression==true){
			if(tempHistory==""){
				tempHistory = "chest compression";
			}else{
				tempHistory = tempHistory.concat(", chest compression");
			}
		}

		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history = tempHistory;
		this.progressNotesAsphyxia();
	};

	createAsphySensoriumText = function(textValue){

		var tempHistory = "";
		if(this.cnsTempObj.sensorium.drowsiness!=null&&this.cnsTempObj.sensorium.drowsiness==true){
			if(tempHistory==""){
				tempHistory = "drowsiness";
			}else{
				tempHistory = tempHistory.concat(",drowsiness");
			}
		}

		if(this.cnsTempObj.sensorium.stuporse!=null&&this.cnsTempObj.sensorium.stuporse==true){
			if(tempHistory==""){
				tempHistory = "stuporse";
			}else{
				tempHistory = tempHistory.concat(",stuporse");
			}
		}

		if(this.cnsTempObj.sensorium.coma!=null&&this.cnsTempObj.sensorium.coma==true){
			if(tempHistory==""){
				tempHistory = "coma";
			}else{
				tempHistory = tempHistory.concat(",coma");
			}
		}

		console.log(tempHistory);
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sensoriumType = tempHistory;
//			console.log(this.cnsSystemObj);
		this.progressNotesAsphyxia();
	};
	/*  Thompson popup */
	thompsonScore = function() {
		$("#thompsonOverlay").css("display", "block");
		$("#thompsonScorePopup").addClass("showing");
		console.log(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.thompsonScore );
	};
	closeModalThompson = function(){
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonFlag == false){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.thompsonScore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.tone = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.loc = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.fits = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.posture = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.moro = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.grasp = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.suck = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.respiration = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.frontanelle = null;
			$("#thompsonNotValid").css("display","none");
		}
		else{
			$("#thompsonNotValid").css("display","none");
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonFlag = true;
		}
		$("#thompsonOverlay").css("display", "none");
		$("#thompsonScorePopup").toggleClass("showing");

	};
	closeModalThompsonOnSave = function(){
		if(this.checkingThompson()){
			$("#thompsonNotValid").css("display","none");
			$("#thompsonOverlay").css("display", "none");
			$("#thompsonScorePopup").toggleClass("showing");
			$("#displayThompson").css("display","table-cell");
			this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonFlag = true;
			this.progressNotesAsphyxia();
		}
		else{
			$("#thompsonNotValid").css("display","inline-block");
		}
	}
	checkingThompson = function(){
		console.log("checking downes");
		var thompsonScoreValid = true;
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.tone == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.tone == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.loc == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.loc == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.fits == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.fits == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.posture == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.posture == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.moro == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.moro == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.grasp == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.grasp == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.suck == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.suck == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.respiration == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.respiration == undefined){
			thompsonScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.frontanelle == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.frontanelle == undefined){
			thompsonScoreValid = false;
		}
		return thompsonScoreValid;
	}
	/*  Thompson Popup  */
  totalThompsonValue = function(thmopsonValue){

		console.log(thmopsonValue);
		if(thmopsonValue == "tone"){
			this.totalTone = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.tone);
		}if(thmopsonValue == "loc"){
			this.totalLoc = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.loc);
		}if(thmopsonValue == "fits"){
			this.totalFits = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.fits);
		}if(thmopsonValue == "posture"){
			this.totalPosture = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.posture);
		}if(thmopsonValue == "moro"){
			this.totalMoro = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.moro);
		}if(thmopsonValue == "grasp"){
			this.totalGrasp = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.grasp);
		}if(thmopsonValue == "suck"){
			this.totalSuck = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.suck);
		}if(thmopsonValue == "respiration"){
			this.totalRespiration = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.respiration);
		}if(thmopsonValue == "fontanelle"){
			this.totalFontanelle = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.frontanelle);
		}
		this.thompsonValueCalculated = this.totalTone + this.totalLoc + this.totalFits + this.totalPosture + this.totalMoro+ this.totalGrasp + this.totalSuck + this.totalRespiration + this.totalFontanelle;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.thompsonScore = this.thompsonValueCalculated;

	}
	//levene score save
	closeModalLeveneOnSave = function(){
		if(this.checkingLevene()){
			$("#leveneNotValid").css("display","none");
			$("#bindScorePopup").removeClass("showing");
			$("#scoresOverlay").removeClass("show");
			$("#displayLevene").css("display","table-cell");
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneFlag = true;
			this.progressNotesAsphyxia();
		}
		else{
			$("#leveneNotValid").css("display","inline-block");
		}
	}
	// end of thompson calculation

  totalLeveneValue = function(leveneValue){
		if(leveneValue == "consciousness"){
			this.totalCons = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.consciousnessscore);
		}if(leveneValue == "tone"){
			this.totalTone = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.tonescore);
		}if(leveneValue == "seizures"){
			this.totalSeizure = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.seizuresscore);
		}if(leveneValue == "suckingRespiration"){
			this.totalSucking = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.suckingRespirationscore);
		}
    var number = 0;
    if(this.totalCons > number){
      number = this.totalCons;
    }
    if(this.totalTone > number){
      number = this.totalTone;
    }
    if(this.totalSeizure > number){
      number = this.totalSeizure;
    }
    if(this.totalSucking > number){
      number = this.totalSucking;
    }
    if(number == 1){
      this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = "Mild";
    }
    if(number == 2){
      this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = "Moderate";
    }
    if(number == 3){
      this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = "Severe";
    }
  }
	// end og levene calculation
	checkingLevene = function(){
		console.log("checking downes");
		var leveneScoreValid = true;
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.consciousnessscore == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.consciousnessscore == undefined){
			leveneScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.tonescore == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.tonescore == undefined){
			leveneScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.seizuresscore == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.seizuresscore == undefined){
			leveneScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.suckingRespirationscore == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.suckingRespirationscore == undefined){
			leveneScoreValid = false;
		}
		return leveneScoreValid;
	}

	/* This is the code for levene score popup */
  showLevene = function() {
		$("#bindScorePopup").addClass("showing");
		$("#scoresOverlay").addClass("show");
		// this.getBindStructure();
	};
	closeBind = function(){
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneFlag == false){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.suckingRespirationscore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.seizuresscore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.tonescore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.consciousnessscore = null;
			$("#leveneNotValid").css("display","none");
		}
		else{
			this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneFlag = true;
			$("#leveneNotValid").css("display","none");
		}
		$("#bindScorePopup").removeClass("showing");
		$("#scoresOverlay").removeClass("show");

	};
	/*  Sarnat popup */
	checkValidSarnat = function(){
		var sarnatScoreValid = true;
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.levelOfConsiousness == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.levelOfConsiousness == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.levelOfConsiousness == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg == undefined || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg == 0){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.duration == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.duration == undefined){
			sarnatScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.outcome == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.outcome == undefined){
			sarnatScoreValid = false;
		}
		return sarnatScoreValid;
	}
	sarnatClassification = function() {
		$("#sarnatOverlay").css("display", "block");
		$("#sarnatClassificationPopup").addClass("showing");

	};
	closeModalSarnatClassification = function(){
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.isSarnatScore == false){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.sarnatScore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.levelOfConsiousness = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.duration = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.outcome = null;
			$("#sarnatOverlay").css("display", "none");
			$("#sarnatNotValid").css("display","none");
		}
		else{
			$("#sarnatOverlay").css("display", "inline-block");
			this.cnsSystemObj.cnseventObject.commonEventsInfo.isSarnatScore = true;
		}
		$("#sarnatOverlay").css("display", "none");
		$("#sarnatClassificationPopup").toggleClass("showing");
	};
	closeModalSarnatOnSave = function(){
		this.checkValidSarnat();
		if(this.checkValidSarnat()){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.isSarnatScore = true;

			$("#sarnatOverlay").css("display", "none");
			$("#sarnatClassificationPopup").toggleClass("showing");
			$("#displaySarnat").css("display","table-cell");
			$("#sarnatNotValid").css("display","none");
		}
		else{
			$("#displaySarnat").css("display","none");
			$("#sarnatNotValid").css("display","inline-block");
		}

		this.progressNotesAsphyxia();
	}
	/*  Sarnat Popup  */
	// Sarnat score calculation

  calculateSarnatValue = function(sarnatValue){
		this.sarnatMaxValue = Math.max(this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone,this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg, this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.duration , this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.outcome);
		this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.sarnatScore =this.sarnatMaxValue ;
	}
	// end of thompson calculation
//		below code is for downe Score
//		below method is used to open the downe score
	downesScore = function() {
		console.log("downes initiated");
		$("#downesOverlay").css("display", "block");
		$("#downesScorePopup").addClass("showing");
	};
//		below code is used to close the downe score
	closeModalDownes = function(){
		console.log("downes closing");
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeFlag == false){
			$("#downesNotValid").css("display","none");
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.cynosis = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.retractions = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.grunting = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.downesscore = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.airentry = null;
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.respiratoryrate = null;
		}
		else{
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeFlag = true;
		}
		$("#downesOverlay").css("display", "none");
		$("#downesScorePopup").toggleClass("showing");
	};

	//this.downes = {};
	closeModalDownesOnSave = function(){
		this.checkValidDownes();
		if(this.checkValidDownes()){
			this.cnsSystemObj.cnseventObject.commonEventsInfo.downeFlag = true;
			$("#downesNotValid").css("display","none");
			$("#downesOverlay").css("display", "none");
			$("#downesScorePopup").toggleClass("showing");
			$("#displayDownes").css("display","table-cell");
			this.progressNotesAsphyxia();
		}
		else{
			$("#downesNotValid").css("display","none");
			$("#downesNotValid").css("display","inline-block");
		}
	}
	checkValidDownes = function(){
		console.log("checking downes");
		var downesScoreValid = true;
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.cynosis == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.cynosis == undefined){
			downesScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.retractions == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.retractions == undefined){
			downesScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.grunting == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.grunting == undefined){
			downesScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.airentry == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.airentry == undefined){
			downesScoreValid = false;
		}
		else if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.respiratoryrate == null || this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.respiratoryrate == undefined){
			downesScoreValid = false;
		}
		return downesScoreValid;
	}
//		below code is used to calculate the downe score

	totalDownesValue = function(downesValue){
		if(downesValue == "cyanosis"){
			this.totalCyanosis = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.cynosis);
		}if(downesValue == "retractions"){
			this.totalRetractions = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.retractions);
		}if(downesValue == "grunting"){
			this.totalGrunting = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.grunting);
		}if(downesValue == "airEntry"){
			this.totalAirEntry = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.airentry);
		}if(downesValue == "respiratoryRate"){
			this.totalRespiratoryRate = parseInt(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.respiratoryrate);
		}
		this.downesValueCalculated = this.totalCyanosis + this.totalRetractions + this.totalGrunting + this.totalAirEntry + this.totalRespiratoryRate;
		this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.downesscore = this.downesValueCalculated;
	}

  populateAsphMultiCheckBox = function(id) {
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
			$(checkboxContId).width(width);
			console.log(width);
			this.expanded = true;
		} else {
			$(checkboxContId).removeClass("show");
			this.expanded = false;
		}
	}

	onCheckAsphMultiCheckBoxValue = function(keyValue,multiCheckBoxId,flagValue){
		var symptomsArr = [];
		console.log(multiCheckBoxId);
		if(multiCheckBoxId=="treatmentActionAsph"){
			this.cnsTempObj.treatmentProgressText = "";
			if(keyValue == "TRE075"){
				var resIndex;
				for(var index=0; index<this.cnsSystemObj.dropDowns.treatmentActionAsphyxia.length; index++){
					if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[index].key=="TRE075"){
						resIndex = index;
					}
				}


				if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[resIndex].flag==true){
					this.callRespiratorySupportPopUP('Asphyxia', false);

				} else if(this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[resIndex].flag == false){
					this.showRespSupportYesNoPopUP('Asphyxia');
				}
			}

			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != ""){
				symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist;
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

			if(keyValue == "TRE076"){
				if(this.cnsTempObj.manageIvFluid==true){
					this.cnsTempObj.manageIvFluid = false;
				}else{
					this.cnsTempObj.manageIvFluid = true;
				}
			}

			if(keyValue =="other"){
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.othertreatmentComments = "";
			}

			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist = symptomsArr;
			console.log(symptomsArr);

			//set progress notes text.
      if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != undefined){
  			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE074")){
  				if(this.cnsTempObj.treatmentProgressText!=null&&this.cnsTempObj.treatmentProgressText!=""
  					&&flagValue==true){
  					this.cnsTempObj.treatmentProgressText = this.cnsTempObj.treatmentProgressText + ", Resuscitation done";
  				}else{
  					this.cnsTempObj.treatmentProgressText  = "Resuscitation done";
  				}
  			}

  			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE075")){
  				if(this.cnsTempObj.treatmentProgressText!=null&&this.cnsTempObj.treatmentProgressText!=""
  					&&flagValue==true){
  					this.cnsTempObj.treatmentProgressText = this.cnsTempObj.treatmentProgressText + ", Respiratory support";
  				}else{
  					this.cnsTempObj.treatmentProgressText  = "Respiratory support";
  				}
  			}

  			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE076")){
  				if(this.cnsTempObj.treatmentProgressText!=null&&this.cnsTempObj.treatmentProgressText!=""
  					&&flagValue==true){
  					this.cnsTempObj.treatmentProgressText = this.cnsTempObj.treatmentProgressText + ", IV Fluid Managed";
  				}else{
  					this.cnsTempObj.treatmentProgressText  = "IV Fluid Managed";
  				}
  			}

  			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE079")){
  				if(this.cnsTempObj.treatmentProgressText!=null&&this.cnsTempObj.treatmentProgressText!=""
  					&&flagValue==true){
  					this.cnsTempObj.treatmentProgressText = this.cnsTempObj.treatmentProgressText + ", Shock Managed";
  				}else{
  					this.cnsTempObj.treatmentProgressText  = "Shock Managed";
  				}
  			}
      }

			this.setTreatmentSelectedTextAsphyxia();

		} else if(multiCheckBoxId=="medicine"){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.length>0){
				symptomsArr = this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList;
			}

			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
					if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
						var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
					for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
						if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
							var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
			this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList = symptomsArr;

			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.length>0){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist == null
						|| !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE077')) {
					this.onCheckMultiCheckBoxValue('TRE072','treatment', true);
					this.onCheckAsphMultiCheckBoxValue('TRE077','treatmentActionAsph', true);
				}
			} else {
				this.onCheckMultiCheckBoxValue('TRE072','treatment', false);
				this.onCheckAsphMultiCheckBoxValue('TRE077','treatmentActionAsph', false);
			}
		}else if(multiCheckBoxId=="antibiotics"){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){
				symptomsArr = this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList;
			}

			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
					if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
						var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
					for(var indexMed = 0; indexMed<this.cnsSystemObj.dropDowns.medicine.length;indexMed++){
						if(this.cnsSystemObj.dropDowns.medicine[indexMed].medid==keyValue){
							var selectedMedObj = this.cnsSystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.babyPrescriptionEmptyObj);
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
			this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList = symptomsArr;
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList!=null
					&& this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length>0){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist == null
						|| !this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE078')) {
					this.onCheckAsphMultiCheckBoxValue('TRE078','treatmentActionAsph', true);
					this.onCheckMultiCheckBoxValue('TRE073','treatment', true);
				}
			} else {
				this.onCheckMultiCheckBoxValue('TRE073','treatment', false);
				this.onCheckAsphMultiCheckBoxValue('TRE078','treatmentActionAsph', false);
			}
		}else if(multiCheckBoxId=="plan"){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionplanlist != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionplanlist != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionplanlist !=""){
				symptomsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionplanlist;
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
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionplanlist = symptomsArr;
			console.log(symptomsArr);
		}
		this.progressNotesAsphyxia();
	}

  redirectToFeedNutritionAsph = function(){
		if(this.ivFluidVariable == false){
			localStorage.setItem('isComingFromCNSTreatment',JSON.stringify(true));
			localStorage.setItem('currentCnsAsphyxiaObject',JSON.stringify(this.cnsSystemObj));
			localStorage.setItem('asphyxiaTempObj',JSON.stringify(this.cnsTempObj));
			localStorage.setItem('isComingFromAsphyxia', this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus);
      this.router.navigateByUrl('/feed/nutrition');
			this.ivFluidVariable = true;
		}else{return;}
	}
	progressNotesAsphyxia = function(){
		console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia);
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.progressnotes= "";
		var template = "";

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent ==null || this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent.length<=0 ||
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0].eventstatus=='inactive'){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.episodeNumber != null){
				template += "Episode number: "+ this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.episodeNumber + ". ";
			}
		}

		if(this.pastAgeInactiveAsphyxia) {
			template += "Baby continues to have Asphyxia";
		} else {
			template += "Baby diagnosed with Asphyxia at the age of ";
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset!=null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset!=""){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays!=null){
					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset < 2){
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays){
							template = template + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset+" hr";
						}else{
							template = template + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset+" day";
						}
					} else {
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageinhoursdays){
							template = template + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset+" hrs";
						}else{
							template = template + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ageatonset+" days";
						}
					}
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history != null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history != ""){
			template += ", with history of resuscitation ("+ this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history + "). ";
		} else {
			template += ". ";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList.length>0){
			this.riskAsphyxiaStr ="";
			for(var index=0; index<this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList.length;index++){
				for(var indexDrop=0; indexDrop<this.cnsSystemObj.dropDowns.riskFactorAshphysia.length;indexDrop++){
					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList[index] == this.cnsSystemObj.dropDowns.riskFactorAshphysia[indexDrop].key){
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorList[index]=='Others'
							&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorOther!=null){
							if(this.riskAsphyxiaStr==''){
								this.riskAsphyxiaStr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorOther;
							}else{
								this.riskAsphyxiaStr +=", " + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactorOther;
							}
						}else{
							if(this.riskAsphyxiaStr==""){
								this.riskAsphyxiaStr = this.cnsSystemObj.dropDowns.riskFactorAshphysia[indexDrop].value;
							}else{
								this.riskAsphyxiaStr = this.riskAsphyxiaStr +", "+ this.cnsSystemObj.dropDowns.riskFactorAshphysia[indexDrop].value;
							}
						}
					}
				}
			}
			if(this.riskAsphyxiaStr!=''){
				if(this.riskAsphyxiaStr.indexOf(",")==-1){
					template += "Associated risk factor is "+this.riskAsphyxiaStr.trim() + ". ";
				}else{
					template += "Associated risk factors are "+this.riskAsphyxiaStr.trim() + ". ";
				}
			}
		} else {
			this.riskAsphyxiaStr ="";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.issensorium){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sensoriumType!=null
					&&this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sensoriumType!=""){
				template += "Abnormal sensorium associated with " + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sensoriumType + ". ";
			} else {
				template += "Abnormal sensorium. ";
			}
		} else if(!this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.issensorium){
			template += "Normal sensorium. ";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isrespiration == false){
			template = template + "Shallow respiration. ";
		} else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isrespiration){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.downeFlag == true){
				template += "Fast respiration with downes' score "+this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.downesscore + ". ";
			} else {
				template += "Fast respiration. ";
			}
		}

		var associatedStr = "";
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.ishypoglycemia) {
			associatedStr = "Hypoglycemia";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isseizures) {
			if(associatedStr == "") {
				associatedStr = "Seizures";
			} else {
				associatedStr += ", Seizures";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isshock) {
			if(associatedStr == "") {
				associatedStr = "Shock";
			} else {
				associatedStr += ", Shock";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isacidosis) {
			if(associatedStr == "") {
				associatedStr = "Acidosis";
			} else {
				associatedStr += ", Acidosis";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isrenal) {
			if(associatedStr == "") {
				associatedStr = "Renal failure";
			} else {
				associatedStr += ", Renal failure";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isrds) {
			if(associatedStr == "") {
				associatedStr = "RDS";
			} else {
				associatedStr += ", RDS";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isPPHN) {
			if(associatedStr == "") {
				associatedStr = "PPHN";
			} else {
				associatedStr += ", PPHN";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isCHF) {
			if(associatedStr == "") {
				associatedStr = "CHF";
			} else {
				associatedStr += ", CHF";
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isIVH) {
			if(associatedStr == "") {
				associatedStr = "IVH";
			} else {
				associatedStr += ", IVH";
			}
		}

		if(associatedStr != "") {
			template += "Associated with " + associatedStr;
		}

		var scoreStr = "";
		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonFlag){
			scoreStr = "thompson score "+this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.thompsonScore;
		}

		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.isSarnatScore){
			if(scoreStr == "") {
				scoreStr = "sarnat staging "+this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.sarnatScore;
			} else {
				scoreStr += ", sarnat staging "+this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.sarnatScore;
			}
		}

		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneFlag){
			if(scoreStr == "") {
				scoreStr = "levene classification "+this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore;
			} else {
				scoreStr += ", levene classification "+this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore;
			}
		}

		if(scoreStr != "") {
			template += " with "  + scoreStr + ". ";
		} else {
			template += ". ";
		}

		if(this.orderSelectedText != "" && this.orderSelectedText != undefined){
			if(this.orderSelectedText.indexOf(",")== -1){
				template += "Investigation ordered is " + this.orderSelectedText + ". ";
			}
			else{
				template += "Investigation ordered are " + this.orderSelectedText + ". ";
			}
		}

		var treatmentPrefix = "";
		if(this.pastAgeInactiveAsphyxia && this.cnsTempObj != null && this.cnsTempObj.asphyxiaTreatmentStatus != null) {
			if(this.cnsTempObj.asphyxiaTreatmentStatus == 'Continue') {
				treatmentPrefix = "Treatment continue to ";
			} else {
				treatmentPrefix = "Treatment changed to ";
			}
		} else {
			treatmentPrefix = "Treatment given ";
		}

		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = "";
		var treatmentProgressNoteStr = "";

		if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport !=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport!=""){
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType!=""){

				var respStr = "";
				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType!="") {
					respStr += "Mechanical Vent Type: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMechVentType + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap!="") {
          if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType == 'CPAP') {
            respStr += "MAP/PEEP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap + ", ";
          } else {
            respStr += "MAP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMap + ", ";
          }
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency!="") {
					respStr += "Frequency: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFrequency + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv!="") {
					respStr += "TV: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsTv + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude!="") {
					respStr += "Amplitude: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsAmplitude + ", ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2!="") {
					respStr += "FiO2: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFio2 + " %, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate!="") {
					respStr += "Flow Rate: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsFlowRate + " liters/Min, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip!="") {
					respStr += "PIP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPip + " cm H2O, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep!="") {
					respStr += "PEEP: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsPeep + " cm H2O, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt!="") {
					respStr += "IT: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsIt + " secs, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt!="") {
					respStr += "ET: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsEt + " secs, ";
				}

				if(this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv!=null && this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv!="") {
					respStr += "MV: " + this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsMv + ", ";
				}

				if(respStr != ""){
					respStr = respStr.substring(0, (respStr.length - 2));
					treatmentProgressNoteStr += this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport.rsVentType + " (" + respStr + "). ";
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.indexOf("TRE089")!=-1){
			treatmentProgressNoteStr += ", Hypoglycemia managed";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.indexOf("TRE076")!=-1){
			treatmentProgressNoteStr += ", IV Fluids managed";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.indexOf("TRE078")!=-1){
			this.antibioticsSelectedStr="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList != null){
				for(var indexMed =0; indexMed<this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.length; indexMed++){
					if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].medicinename != null){
						//for frequency...
						var currentFrequency = "";
						for(var indexFreq=0; indexFreq < this.cnsSystemObj.dropDowns.freqListMedcines.length; indexFreq++){
							if(this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].frequency ==
								this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqid){
								currentFrequency = this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqvalue;
							}
						}
						if(this.antibioticsSelectedStr==""){
							this.antibioticsSelectedStr = this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}else{
							this.antibioticsSelectedStr = this.antibioticsSelectedStr + ", " + this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}
					}
				}
				if(this.antibioticsSelectedStr != ""){
					if(treatmentProgressNoteStr != '') {
						treatmentProgressNoteStr += ", ";
					}
					if(this.antibioticsSelectedStr.indexOf(",")==-1){
						treatmentProgressNoteStr += "Antibiotic (" + this.antibioticsSelectedStr + ")";
					}else{
						treatmentProgressNoteStr += "Antibiotics (" + this.antibioticsSelectedStr + ")";
					}
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.indexOf("TRE077")!=-1){
			this.medicineSelectedStr="";
			if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList != null){
				for(var indexMed =0; indexMed<this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.length; indexMed++){

					if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].medicinename != null){
						//for frequency...
						var currentFrequency = "";
						for(var indexFreq=0; indexFreq < this.cnsSystemObj.dropDowns.freqListMedcines.length; indexFreq++){
							if(this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].frequency ==
								this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqid){
								currentFrequency = this.cnsSystemObj.dropDowns.freqListMedcines[indexFreq].freqvalue;
							}
						}
						if(this.medicineSelectedStr==""){
							this.medicineSelectedStr = this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}else{
							this.medicineSelectedStr = this.medicineSelectedStr + ", " + this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].medicinename
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].dose*this.cnsSystemObj.workingWeight*1+" mg"
							+" "+this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList[indexMed].route+" "+currentFrequency;
						}
					}
				}
				if(this.medicineSelectedStr != ""){
					if(treatmentProgressNoteStr != '') {
						treatmentProgressNoteStr += ", ";
					}
					if(this.medicineSelectedStr.indexOf(",")==-1){
						treatmentProgressNoteStr += "Medicine (" + this.medicineSelectedStr + ")";
					}else{
						treatmentProgressNoteStr += "Medicines (" + this.medicineSelectedStr + ")";
					}
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.indexOf("other")!=-1){

			if(this.cnsTempObj.isAcidosisSavedAshphyxia) {
				if(treatmentProgressNoteStr != '') {
					treatmentProgressNoteStr += ", ";
				}
				treatmentProgressNoteStr += "Acidosis managed";
			}

			if(this.cnsTempObj.isRdsSavedAshphyxia) {
				if(treatmentProgressNoteStr != '') {
					treatmentProgressNoteStr += ", ";
				}
				treatmentProgressNoteStr += "RDS managed";

			}

			if(this.cnsTempObj.isPphnSavedAsphyxia) {
				if(treatmentProgressNoteStr != '') {
					treatmentProgressNoteStr += ", ";
				}
				treatmentProgressNoteStr += "PPHN managed";

			}

			if(this.cnsTempObj.isIvhSavedAshphyxia) {
				if(treatmentProgressNoteStr != '') {
					treatmentProgressNoteStr += ", ";
				}
				treatmentProgressNoteStr += "IVH managed";
			}
		}

    if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.medicationStr != null
      && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.medicationStr != '') {
        if(treatmentProgressNoteStr != '') {
          treatmentProgressNoteStr += ", ";
        }
        treatmentProgressNoteStr += this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.medicationStr;
    }

		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = treatmentProgressNoteStr;
		if(treatmentProgressNoteStr != "") {
			template += treatmentPrefix + treatmentProgressNoteStr + ".";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause.length>0){
			this.causeAsphyxiaStr ="";
			for(var index=0; index<this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause.length;index++){
				for(var indexDrop=0; indexDrop<this.cnsSystemObj.dropDowns.causeOfAsphyxia.length;indexDrop++){
					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause[index] == this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDrop].key){
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause[index]=='other'
							&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.causeofasphyxiaOther!=null){
							if(this.causeAsphyxiaStr==''){
								this.causeAsphyxiaStr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.causeofasphyxiaOther;
							}else{
								this.causeAsphyxiaStr +=", " + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.causeofasphyxiaOther;
							}
						}else{
							if(this.causeAsphyxiaStr==""){
								this.causeAsphyxiaStr = this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDrop].value;
							}else{
								this.causeAsphyxiaStr = this.causeAsphyxiaStr +", "+ this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDrop].value;
							}
						}
					}
				}
			}
			if(this.causeAsphyxiaStr!=''){
				if(this.causeAsphyxiaStr.indexOf(",")==-1){
					template += this.causeAsphyxiaStr.trim() + " is most  likely cause of Asphyxia. ";
				}else{
					template += this.causeAsphyxiaStr.trim() + " are most  likely cause of Asphyxia. ";
				}
			}
		} else {
			this.causeAsphyxiaStr ="";
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime!=null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime!=""
					&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestimeType!=null){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime == "1"){
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestimeType=='mins'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime+" min.";
				}else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestimeType=='hours'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime+" hour.";
				} else {
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime+" day.";
				}
			} else {
				if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestimeType=='mins'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime+" mins.";
				}else if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestimeType=='hours'){
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime+" hours.";
				} else {
					template += " Plan is to reassess the baby after "+this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.reassestime+" days.";
				}
			}
		}

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.otherplanComments !=null){
			template += " " + this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.otherplanComments + ".";
		}

		while(template.includes('.,')) {
			template = template.replace('.,','. ');
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText.replace('.,','. ');
		}

		while(template.includes('. ,')) {
			template = template.replace('. ,','. ');
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText.replace('. ,','. ');
		}

		while(template.includes('. .')) {
			template = template.replace('. .','. ');
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText.replace('. .','. ');
		}

		while(template.includes('..')) {
			template = template.replace('..','. ');
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText.replace('..','. ');
		}

		while(template.includes('  ')) {
			template = template.replace('  ',' ');
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentSelectedText.replace('  ',' ');
		}

		console.log(template);
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.progressnotes = template;

		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus != ""){
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.eventstatus.toUpperCase()=='YES')
				this.ageOnsetValidationEpisode('asphyxia');
		}
	}

	setAsphyxiaFeedsRedirectVariable = function(){
		var currentCnsObj = JSON.parse(localStorage.getItem('currentCnsAsphyxiaObject'));
		var tempObj = JSON.parse(localStorage.getItem('asphyxiaTempObj'));
		if(currentCnsObj != null){
			this.cnsSystemObj = currentCnsObj;
			this.cnsTempObj = tempObj;
			this.cnsTabVisible('asphyxia');
			this.cnsTempObj.asphyxiaTreatmentStatus == "Change";
			this.isActionSectionAsphyxiaVisible = true;
			this.progressNotesAsphyxia();
			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist == null){
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist =[];
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.push('TRE076');
			}else{
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.push('TRE076');
				console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist);
			}
		}
		localStorage.removeItem("isComingFromAsphyxia");
		localStorage.removeItem("currentCnsAsphyxiaObject");
		localStorage.removeItem("asphyxiaTempObj");
	}

	creatingPastSelectedAsphyxiaText = function(){
		var tempPastSelected = "";
		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent != ""){
			var pastAsphyxia = this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[0];

			if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations!=null
					&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations.length>0){
				for(var indexOrder = 0; indexOrder<this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations.length;indexOrder++){
					if(pastAsphyxia.sacnsasphyxiaid && pastAsphyxia.sacnsasphyxiaid==this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations[indexOrder].assesmentid){
						if(this.cnsTempObj.pastSelectedOrderInvestigationStr=='' || this.cnsTempObj.pastSelectedOrderInvestigationStr==undefined){
							this.cnsTempObj.pastSelectedOrderInvestigationStr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations[indexOrder].testname;
						}else{
							this.cnsTempObj.pastSelectedOrderInvestigationStr += ", " + this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations[indexOrder].testname;
						}
					}
				}
			}

			if(pastAsphyxia.causeofAsphyxia!=null && pastAsphyxia.causeofAsphyxia!=''){
				this.cnsTempObj.pastSelectedCauseStr = "";
				var list = pastAsphyxia.causeofAsphyxia.replace("[","").replace("]","").split(",");
				for(var indexCause = 0; indexCause<list.length;indexCause++){
					for(var indexDrop = 0; indexDrop<this.cnsSystemObj.dropDowns.causeOfAsphyxia.length;indexDrop++){
						if(list[indexCause].replace(" ","")==this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDrop].key){
							if(this.cnsTempObj.pastSelectedCauseStr=='' || this.cnsTempObj.pastSelectedCauseStr==undefined){
								this.cnsTempObj.pastSelectedCauseStr = this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDrop].value;
							}else{
								this.cnsTempObj.pastSelectedCauseStr += ", "+ this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDrop].value;
							}
						}
					}
				}
			}
		}
	}

	setTreatmentSelectedTextAsphyxia = function(){
		var currentSelectedTreatment = "";
    if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != undefined){
  		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE079")){
  			if(currentSelectedTreatment!=null&&currentSelectedTreatment!=""){
  				currentSelectedTreatment += ", Shock";
  			}else{
  				currentSelectedTreatment +="Shock";
  			}
  		}

  		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE078")){
  			if(currentSelectedTreatment!=null&&currentSelectedTreatment!=""){
  				currentSelectedTreatment += ", Antibiotics";
  			}else{
  				currentSelectedTreatment +="Antibiotics";
  			}
  		}

  		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE077")){
  			if(currentSelectedTreatment!=null&&currentSelectedTreatment!=""){
  				currentSelectedTreatment += ", Medication";
  			}else{
  				currentSelectedTreatment +="Medication";
  			}
  		}

  		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE076")){
  			if(currentSelectedTreatment!=null&&currentSelectedTreatment!=""){
  				currentSelectedTreatment += ", Manage IV Fluids";
  			}else{
  				currentSelectedTreatment +="Manage IV Fluids";
  			}
  		}

  		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE075")){
  			if(currentSelectedTreatment!=null&&currentSelectedTreatment!=""){
  				currentSelectedTreatment += ", Respiratory support";
  			}else{
  				currentSelectedTreatment +="Respiratory support";
  			}
  		}

  		if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes("TRE074")){
  			if(currentSelectedTreatment!=null&&currentSelectedTreatment!=""){
  				currentSelectedTreatment += ", Resuscitation";
  			}else{
  				currentSelectedTreatment +="Resuscitation";
  			}
  		}
    }
		if(currentSelectedTreatment!=null && currentSelectedTreatment!=""){
			this.cnsTempObj.currentSelectedTreatment = currentSelectedTreatment;
		}

	}

  cnsTabVisible = function(tabValue){
		if(tabValue == "asphyxia"){
			this.cnsSystemObj.cnseventObject.eventName =  "asphyxia";
			this.cnsWhichTab = tabValue;
		}
		if(tabValue == "seizures"){
			console.log(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures);
			this.cnsSystemObj.cnseventObject.eventName =  tabValue;
			this.cnsWhichTab = tabValue;
			//this.seizuresScroll();
		}
		if(tabValue == "encephalopathy"){
			this.cnsWhichTab = tabValue;
		}
		if(tabValue == "neuromuscular disorders"){
			this.cnsWhichTab = tabValue;
		}
		if(tabValue == "intracranial hemorrhage"){
			this.cnsSystemObj.cnseventObject.eventName =  "ivh";
			this.cnsWhichTab = tabValue;
		}
		if(tabValue == "hydrocephalus"){
			this.cnsWhichTab = tabValue;
		}
		if(tabValue == "neurological screening"){
			this.cnsWhichTab = tabValue;
		}
	}
	// cnsScroll = function(){
	// 	$('html,body').animate({
	// 		scrollTop: $(".cnsFirst").offset().top},'slow');
	// }
	// asphyxiaScroll = function(){
	// 	$('html,body').animate({
	// 		scrollTop: $(".asphyxiaFirst").offset().top},'slow');
	// }
  // seizuresScroll = function(){
	// 	$('html,body').animate({
	// 		scrollTop: $(".seizuresFirst").offset().top},'slow');
	// }

  historySectionAsphyxiaVisible = function(){
		this.ishistorySectionAsphyxiaVisible = !this.ishistorySectionAsphyxiaVisible;
	}

	clinicalSectionAsphyxiaVisible = function(){
		this.isclinicalSectionAsphyxiaVisible = !this.isclinicalSectionAsphyxiaVisible;
	}

	ActionAsphyxiaVisible = function(){
		this.isActionSectionAsphyxiaVisible = !this.isActionSectionAsphyxiaVisible;
	}

	PlanAsphyxiaVisible = function(){
		this.isPlanSectionAsphyxiaVisible = !this.isPlanSectionAsphyxiaVisible;
	}

	causeAsphyxiaSectionVisible = function(){
		this.isCauseAsphyxiaVisible = !this.isCauseAsphyxiaVisible;
	}

	progressAsphyxiaVisible = function(){
		this.isProgressAsphyxiaVisible = !this.isProgressAsphyxiaVisible;
	}

	clinicalSectionSeizuresVisible = function(){
		this.isclinicalSectionSeizuresVisible = !this.isclinicalSectionSeizuresVisible ;
	}

	ActionSeizuresVisible = function(){
		this.isActionSectionSeizuresVisible = !this.isActionSectionSeizuresVisible;
	}

	PlanSeizuresVisible = function(){
		this.isPlanSectionSeizuresVisible = !this.isPlanSectionSeizuresVisible;
	}

	progressSeizuresVisible = function(){
		this.isProgressSeizuresVisible = !this.isProgressSeizuresVisible;
	}

	causeSeizuresVisible = function(){
		this.isCauseSeizuresVisible = !this.isCauseSeizuresVisible;
	}

	switchEventSeizers = function() {
		this.TableSeizersFlag = !this.TableSeizersFlag;
	}
	switchPastMedTableSeizure = function() {
		if(this.seizureMedTableFlag) {
			this.seizureMedTableFlag = "View Past Medication";
		} else {
			this.otherMedTableRDSText = "Hide Past Medication";
		}
		this.seizureMedTableFlag = !this.seizureMedTableFlag;
	}

	switchPastMedTableAsphyxia = function() {
		if(this.otherMedTableAsphyxiaFlag) {
			this.otherMedTableAsphyxiaText = "View Past Medication";
		} else {
			this.otherMedTableAsphyxiaText = "Hide Past Medication";
		}
		this.otherMedTableAsphyxiaFlag = !this.otherMedTableAsphyxiaFlag;
	}

  switchPastMedTablePPHN = function() {
		if(this.otherMedTablePPHNFlag) {
			this.otherMedTablePPHNText = "View Current Medication";
		} else {
			this.otherMedTablePPHNText = "Hide Current Medication";
		}
		this.otherMedTablePPHNFlag = !this.otherMedTablePPHNFlag;
	}

	cnsView = function(){
		this.isrespGraph = ! this.isrespGraph;
	}

		//-----------------Time difference for ongoing medication entries----------------------------------------------

	differenceInDaysFunc = function (dateObj) { return this.differenceInDays(dateObj); }

  editMedication = function(medicineObj,type){
		if(type=='editModeOn' && this.tempPastPrescriptionList.length==0){
			this.tempPastPrescriptionList = Object.assign({},this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList);
		}

		var submitIndex = null;
		if(type=='editModeOn' || type=='editSubmit' || type=='editCancel'){
			for(var index=0; index<this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList.length;index++){
				if(medicineObj.babyPresid==this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[index].babyPresid){
					this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[index].isInEditMode = !(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[index].isInEditMode);
					submitIndex = index;
					if(type=='editCancel'){
						this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[index] = Object.assign({},this.tempPastPrescriptionList[index]);
					}
				}
			}
		}
		if(type=='editSubmit'){

      try
      {
        this.http.post(this.apiData.editPrescription,
          JSON.stringify(medicineObj)).subscribe(res => {
            this.prescription =res.json();
						if(this.prescription.type=="success"){
							this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[submitIndex] = Object.assign({},this.prescription.returnedObject);
							this.tempPastPrescriptionList[submitIndex] = Object.assign({},this.prescription.returnedObject);
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

	//below code is for stop medication
	navi= function(deleteBabyPrescription,actionid) {
		if(actionid == "delete"){
      deleteBabyPrescription.stopTime.date = new Date(deleteBabyPrescription.stopTime.date);
      let tempMedHours = deleteBabyPrescription.stopTime.date.getHours();
      if(tempMedHours*1 > 12){
        tempMedHours = tempMedHours-12;
        deleteBabyPrescription.stopTime.meridium = 'PM'
      }else if(tempMedHours <12)
        {if(tempMedHours*1 < 10){
          tempMedHours = "0"+tempMedHours;
        }
        deleteBabyPrescription.stopTime.meridium = 'AM';
      }else{
        deleteBabyPrescription.stopTime.meridium = 'PM';
      }

      deleteBabyPrescription.stopTime.hours = tempMedHours;
      deleteBabyPrescription.stopTime.minutes = deleteBabyPrescription.stopTime.date.getMinutes();

			if((deleteBabyPrescription.stopTime.date != null && deleteBabyPrescription.stopTime.date != undefined && deleteBabyPrescription.stopTime.date != "")
        && (deleteBabyPrescription.stopTime.hours != null && deleteBabyPrescription.stopTime.hours != undefined && deleteBabyPrescription.stopTime.hours != "")
					&& (deleteBabyPrescription.stopTime.minutes != null && deleteBabyPrescription.stopTime.minutes != undefined && deleteBabyPrescription.stopTime.minutes != "")
					&& (deleteBabyPrescription.stopTime.meridium != null && deleteBabyPrescription.stopTime.meridium != undefined && deleteBabyPrescription.stopTime.meridium != "")){

				deleteBabyPrescription.enddate = this.getTimeStampFromNicuTimeStampFormat(deleteBabyPrescription.stopTime.date,deleteBabyPrescription.stopTime.hours,deleteBabyPrescription.stopTime.minutes,
				deleteBabyPrescription.stopTime.seconds,deleteBabyPrescription.stopTime.meridium);

        try
        {
          this.http.post(this.apiData.deletePrescription+ deleteBabyPrescription.babyPresid + '/test',
            JSON.stringify(deleteBabyPrescription)).subscribe(res => {
              this.prescription =res.json();
							//this.showModal(this.prescription.message, this.prescription.type);
              this.warningMessage = this.prescription.message;
              this.showWarningPopUp();
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
				for(var i=0;i<this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList.length;i++){
					if(this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i].babyPresid == deleteBabyPrescription.babyPresid){
						this.cnsSystemObj.cnseventObject.commonEventsInfo.pastPrescriptionList[i].stopMessage = "Please select medicine stop date and time";
					}
				}
			}
		}
	}

	setPastAssessmentAsphyxia = function(date){
		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = date;
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate = todayDate;
		var todayHours = todayDate.getHours();
		var todayMinutes = todayDate.getMinutes();
		if(todayHours<12){
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMeridiem = true;
			if(todayHours==0)
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour = "12";
			else if(todayHours<10)
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour = '0' + todayHours.toString();
			else
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour = todayHours.toString();
		}else{
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMeridiem = false;
			todayHours -=12;
			if(todayHours==0)
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour = "12";
			else if(todayHours<10)
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour = '0' + todayHours.toString();
			else
				this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentHour = todayHours.toString();
		}
		if(todayMinutes<10)
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMin = '0' + todayMinutes.toString();
		else
			this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentMin = 	todayMinutes.toString();


		this.asphyxiaTempObj.assessmentDate = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate;
		this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentTime = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.assessmentDate;
	}

	setPastAssessmentSeizures = function(date){

		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = date;
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentDate = todayDate;
		var todayHours = todayDate.getHours();
		var todayMinutes = todayDate.getMinutes();
		if(todayHours<12){
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentMeridiem = true;
			if(todayHours==0)
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentHour = "12";
			else if(todayHours<10)
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentHour = '0' + todayHours.toString();
			else
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentHour = todayHours.toString();
		}else{
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentMeridiem = false;
			todayHours -=12;
			if(todayHours==0)
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentHour = "12";
			else if(todayHours<10)
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentHour = '0' + todayHours.toString();
			else
				this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentHour = todayHours.toString();
		}
		if(todayMinutes<10)
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentMin = '0' + todayMinutes.toString();
		else
			this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentMin = 	todayMinutes.toString();


		this.seizuresTempObj.assessmentDate = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentDate;
		this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentTime = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.assessmentDate;
	}

	populatePastAssessment = function(assessment){

		try{

			if(assessment[6]=="Asphyxia"){

				this.causeAsphyxiaStr = "";
				this.riskAsphyxiaStr = " ";
				for(var indexDropDown = 0; indexDropDown<this.cnsSystemObj.dropDowns.causeOfAsphyxia.length;indexDropDown++){
					this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDropDown].flag = false;
				}
				for(var index=0; index<this.cnsSystemObj.dropDowns.riskFactorAshphysia.length;index++){
					this.cnsSystemObj.dropDowns.riskFactorAshphysia[index].flag = false;
				}
				for(var obj in this.cnsSystemObj.dropDowns.orders){
					for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
						this.cnsSystemObj.dropDowns.orders[obj][index].isSelected = false;
					}
				}

				for(var indexPastResp=0;indexPastResp<this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent.length;indexPastResp++){
					if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[indexPastResp].sacnsasphyxiaid==assessment[1]){
						this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia =
						Object.assign({},this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastAsphyxiaEvent[indexPastResp]);
						console.log(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia);
						this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.isNewEntry = false;

						this.setPastAssessmentAsphyxia(new Date(assessment[3]));

						//populate causes
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.causeofAsphyxia!=null
								&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.causeofAsphyxia!=""){


							//this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause = [];
							this.causeAsphyxiaStr = "";
							var asphyxiaCauseList =[];
							asphyxiaCauseList = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.causeofAsphyxia
							.replace("[","").replace("]",",").replace(" ","").split(",");
							for(var index =0; index < asphyxiaCauseList.length;index++){
								for(var indexDropDown = 0; indexDropDown<this.cnsSystemObj.dropDowns.causeOfAsphyxia.length;indexDropDown++){
									if(asphyxiaCauseList[index].replace(" ","") == this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDropDown].key){
										//this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause.push(asphyxiaCauseList[index]);
										this.onCheckMultiCheckBoxAsphyxiaValue(this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDropDown].key,'causeOfAsphyxia',this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDropDown].value);
										this.cnsSystemObj.dropDowns.causeOfAsphyxia[indexDropDown].flag = true;

									}
								}
							}
						}

						//populate risk factors
						this.riskAsphyxiaStr = " ";
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactor != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactor != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactor != ""){
							var factorsArr = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.riskfactor.replace("[","").replace("]","").split(",");
							for(var indexFactors =0; indexFactors<factorsArr.length;indexFactors++){
								for(var index=0; index<this.cnsSystemObj.dropDowns.riskFactorAshphysia.length;index++){
									if(factorsArr[indexFactors].trim()==this.cnsSystemObj.dropDowns.riskFactorAshphysia[index].key.trim()){
										this.cnsSystemObj.dropDowns.riskFactorAshphysia[index].flag = true;
										this.onCheckMultiCheckBoxAsphyxiaValue(this.cnsSystemObj.dropDowns.riskFactorAshphysia[index].key,'riskFactorAsphyxia',this.cnsSystemObj.dropDowns.riskFactorAshphysia[index].value);

									}
								}
							}
						}

						//populate investigation orders
						var isInvestigationOrders = false;

						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations != ""){
							for(var indexOrder=0; indexOrder<this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations.length;indexOrder++){
								if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations[indexOrder].assesmentid==assessment[1]){
									isInvestigationOrders = true;
									break;
								}
								else{
									isInvestigationOrders = false;
								}
							}
							if(isInvestigationOrders == true){
								this.orderSelectedText = '';
								for(var indexOrder=0; indexOrder<this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations.length;indexOrder++){
									for(var obj in this.cnsSystemObj.dropDowns.orders){
										for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
											if(this.cnsSystemObj.dropDowns.orders[obj][index].testid==this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations[indexOrder].testslistid
													&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.pastEvents.pastInvestigations[indexOrder].assesmentid==assessment[1]){


												this.cnsSystemObj.dropDowns.orders[obj][index].isSelected = true;
												console.log(this.cnsSystemObj.dropDowns.orders[obj][index]);
												this.investOrderNotOrdered.push(this.cnsSystemObj.dropDowns.orders[obj][index].testname);
												if(this.orderSelectedText==''){
													this.orderSelectedText =  this.cnsSystemObj.dropDowns.orders[obj][index].testname;
												}else{
													this.orderSelectedText = this.orderSelectedText +", "+ this.cnsSystemObj.dropDowns.orders[obj][index].testname;
												}
											}
										}
									}
								}
								this.orderSelected();
								this.closeModalOrderInvestigation();
							}
						}

						//populate downesscore
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.downesScore != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.downesScore != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.downesScore != ""){
							let downesScoreList : DowneScoreObj = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.downesScore;

							this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.cynosis    =  downesScoreList.cynosis;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.retractions  = downesScoreList.retractions;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.grunting  = downesScoreList.grunting;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.airentry  = downesScoreList.airentry;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.respiratoryrate = downesScoreList.respiratoryrate;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.downeScoreObj.downesscore = downesScoreList.downesscore;
							//this.closeModalDownesOnSave();
						}

						//populate thompson score
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.thompsonScore != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.thompsonScore != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.thompsonScore !=""){
							let thompsonScoreList : ThompsonScoreObj = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.thompsonScore;

							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.tone    =  thompsonScoreList.tone;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.loc  = thompsonScoreList.loc;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.fits  = thompsonScoreList.fits;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.posture  = thompsonScoreList.posture;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.moro = thompsonScoreList.moro;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.grasp  = thompsonScoreList.grasp;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.suck  = thompsonScoreList.suck;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.respiration  = thompsonScoreList.respiration;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.frontanelle = thompsonScoreList.frontanelle;

							this.cnsSystemObj.cnseventObject.commonEventsInfo.thompsonScoreObj.thompsonScore = thompsonScoreList.thompsonScore;
							//this.closeModalDownesOnSave();
						}

						//populate sarnat score
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sarnatScore != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sarnatScore != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sarnatScore !=""){
							let sarnatScoreList : SarnatScoreObj = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.sarnatScore;

							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.levelOfConsiousness    =  sarnatScoreList.levelOfConsiousness;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.muscleTone  = sarnatScoreList.muscleTone;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.posture  = sarnatScoreList.posture;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.tendonReflexes  = sarnatScoreList.tendonReflexes;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.myoclonus = sarnatScoreList.myoclonus;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.moreReflex    =  sarnatScoreList.moreReflex;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.pupils  = sarnatScoreList.pupils;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.seizures  = sarnatScoreList.seizures;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.ecg  = sarnatScoreList.ecg;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.duration = sarnatScoreList.duration;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.outcome = sarnatScoreList.outcome;

							this.cnsSystemObj.cnseventObject.commonEventsInfo.sarnatScoreObj.sarnatScore = sarnatScoreList.sarnatScore;
							//this.closeModalDownesOnSave();
						}

						//populate levene score
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.leveneScore != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.leveneScore != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.leveneScore !=""){
							let leveneScoreList : ScoreLeveneObj = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.leveneScore;

							this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.consciousnessscore    =  leveneScoreList.consciousness;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.tonescore  = leveneScoreList.tonescore;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.suckingRespirationscore = leveneScoreList.suckingrespirationscore;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.seizuresscore = leveneScoreList.seizuresscore;
							this.cnsSystemObj.cnseventObject.commonEventsInfo.leveneObj.scoreLeveneObj.levenescore = leveneScoreList.levenescore;

							//this.closeModalDownesOnSave();
						}

						//populate Resuscitation Details
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history!=null
								&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history!=""){
							var tempHistory = "";
							var resuscitationList =[];
							resuscitationList = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history.split(",");
							for(var index = 0;index<resuscitationList.length;index++ ){
								if((resuscitationList[index] != null && resuscitationList[index] != undefined && resuscitationList[index] != "")
                  && resuscitationList[index]=='delayed cry'){
									this.cnsTempObj.history.delayedcry = true;
									this.cnsTempObj.history.delayedcry
									tempHistory = tempHistory.concat(", delayed cry");
								}
								if((resuscitationList[index] != null && resuscitationList[index] != undefined && resuscitationList[index] !="")
                  && resuscitationList[index].replace(" ","")=='depressed at birth'){
									this.cnsTempObj.history.depressedbirth = true;
									tempHistory = tempHistory.concat(", depressed at birth");
								}
								if((resuscitationList[index] != null && resuscitationList[index] != undefined && resuscitationList[index] !="")
                && resuscitationList[index].replace(" ","")=='medications'){
									this.cnsTempObj.history.medication = true;
									tempHistory = tempHistory.concat(", medications");
								}
								if((resuscitationList[index] != null && resuscitationList[index] != undefined && resuscitationList[index] != "")
                && resuscitationList[index].replace(" ","")=='oxygen requirement'){
									this.cnsTempObj.history.oxyegenrequirement = true;
									tempHistory = tempHistory.concat(", oxygen requirement");
								}
								if((resuscitationList[index] != undefined && resuscitationList[index] != null && resuscitationList[index] !="")
                && resuscitationList[index].replace(" ","")=='chest compression'){
									this.cnsTempObj.history.chestcompression = true;
									tempHistory = tempHistory.concat(", chest compression");
								}

								if(resuscitationList[index].match('need for ppv') != null){
									if(resuscitationList[index].match('secs') != null){
										this.needforPPVTime = resuscitationList[index].match(/\d+/)[0];

									}
									this.cnsTempObj.history.needppv = true;

								}
							}
							this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.history = tempHistory;
						}

						// populate treatment list
						this.cnsTempObj.asphyxiaTreatmentStatus = "Continue";
						if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentaction!=null
								&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentaction!=""){
							this.cnsTempObj.asphyxiaTreatmentStatus = "Change";
							this.treatmentAsphyxiaTabsVisible = true;
							this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist = [];

							this.treatmentSelectedText = "";
							var treatmentActionList =[];
							treatmentActionList = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentaction
							.replace("[","").replace("]",",").replace(" ","").split(",");
							for(var index = 0;index<treatmentActionList.length;index++ ){
								for(var indexDropDown = 0; indexDropDown<this.cnsSystemObj.dropDowns.treatmentActionAsphyxia.length;indexDropDown++){
									if((treatmentActionList[index] != null && treatmentActionList[index] != undefined && treatmentActionList[index] !="")
                  && treatmentActionList[index].replace(" ","")
											== this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[indexDropDown].key.replace(" ","")){
										this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[indexDropDown].flag = true;
										var name = this.cnsSystemObj.dropDowns.treatmentActionAsphyxia[indexDropDown].key;
										if(name != null && name != undefined && name !=""){
											name = name.trim();

											if(name=='other'){
												this.cnsTempObj.isOtherSelectAshphyxia = true;
												//this.saveTreatmentRds('rdsOther');
											}
											else if(name=='TRE076'){
												this.isIvFluidSelectAshphyxia = true;
											}
											else if(name=='TRE089'){
												this.isHypoglycemiaSelectAshphyxia = true;
											}
											else if(name=='TRE077' || name=='TRE078'){
												if(name=='TRE077'){
													this.cnsTempObj.isMedicationSelectAshphyxia = true;
												}
												else if(name=='TRE078'){
													this.cnsTempObj.isAntibioticsSelectAshphyxia = true;
												}
												else{
													var c ='1';
												}
												if(this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.babyPrescription != null && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.babyPrescription != undefined && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.babyPrescription !=""){

													this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList = []
													this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList = []

													var prescriptions = this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.babyPrescription;
													for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
														for(var indexAllMed =0; indexAllMed<this.cnsSystemObj.dropDowns.medicine.length;indexAllMed++){
															if(prescriptions[indexPrescription].medid ==
																this.cnsSystemObj.dropDowns.medicine[indexAllMed].medid){
																var prescriptionItem = prescriptions[indexPrescription];
																if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined && prescriptionItem.startdate !=""){
																	prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
																	prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
																}
																if(prescriptions[indexPrescription].medicationtype == 'TYPE0001'){
																	this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.push(
																			Object.assign({},prescriptions[indexPrescription]));
																}
																else if(prescriptions[indexPrescription].medicationtype == 'TYPE0010'){
																	this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.push(
																			Object.assign({},prescriptions[indexPrescription]));
																}

																this.cnsSystemObj.dropDowns.medicine[indexAllMed].flag =true;

															}
														}
													}
												}
											}else if(name=='TRE075'){
												this.cnsTempObj.isRespSelectAshphyxia = true;
												this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport =
													this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.respSupport;
												//this.saveTreatmentRds('rdsRespiratory');

											}
										}
									}
								}
							}
						}
					}
				}
			}
			if(assessment[6]=="Seizures"){

				for(var indexDropDown = 0; indexDropDown<this.cnsSystemObj.dropDowns.causeOfSeizures.length;indexDropDown++){
					this.cnsSystemObj.dropDowns.causeOfSeizures[indexDropDown].flag = false;
				}

				for(var obj in this.cnsSystemObj.dropDowns.orders){
					for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
						this.cnsSystemObj.dropDowns.orders[obj][index].isSelected = false;
					}
				}


				for(var indexPastResp=0;indexPastResp<this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents.length;indexPastResp++){
					if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[indexPastResp].sacnsseizuresid==assessment[1]){
						this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures =
							Object.assign({},this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastSeizuresEvents[indexPastResp]);
						this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.isNewEntry = false;

						this.setPastAssessmentSeizures(new Date(assessment[3]));

						//populate causes
						if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizures!=null
								&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizures!=""){


							//this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause = [];
							this.seizuresTempObj.selectedCauseStr = "";
							this.seizuresCauseList =[];

							this.seizuresCauseList = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.causeofSeizures
							.replace("[","").replace("]",",").replace(" ","").split(",");
							for(var index =0; index < this.seizuresCauseList.length;index++){
								for(var indexDropDown = 0; indexDropDown<this.cnsSystemObj.dropDowns.causeOfSeizures.length;indexDropDown++){
									if(this.seizuresCauseList[index].replace(" ","") == this.cnsSystemObj.dropDowns.causeOfSeizures[indexDropDown].key){


										//this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactioncause.push(asphyxiaCauseList[index]);
										this.onCheckMultiCheckBoxValue(this.cnsSystemObj.dropDowns.causeOfSeizures[indexDropDown].key,'cause',this.cnsSystemObj.dropDowns.causeOfSeizures[indexDropDown].value);
										this.cnsSystemObj.dropDowns.causeOfSeizures[indexDropDown].flag = true;

									}
								}
							}
						}

						//populate Investigation Orders
						if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations != null && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations != ""){
							for(var indexOrder=0; indexOrder<this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations.length;indexOrder++){
								if(this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations[indexOrder].assesmentid==assessment[1]){
									isInvestigationOrders = true;
									break;
								}
								else{
									isInvestigationOrders = false;
								}
							}
							if(isInvestigationOrders == true){
								this.orderSelectedText = '';
								for(var indexOrder=0; indexOrder<this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations.length;indexOrder++){
									for(var obj in this.cnsSystemObj.dropDowns.orders){
										for(var index = 0; index<this.cnsSystemObj.dropDowns.orders[obj].length;index++){
											if(this.cnsSystemObj.dropDowns.orders[obj][index].testid==this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations[indexOrder].testslistid
													&& this.cnsSystemObj.cnseventObject.seizuresEvent.pastEvents.pastInvestigations[indexOrder].assesmentid==assessment[1]){


												this.cnsSystemObj.dropDowns.orders[obj][index].isSelected = true;
												console.log(this.cnsSystemObj.dropDowns.orders[obj][index]);
												this.investOrderNotOrdered.push(this.cnsSystemObj.dropDowns.orders[obj][index].testname);
												if(this.orderSelectedText==''){
													this.orderSelectedText =  this.cnsSystemObj.dropDowns.orders[obj][index].testname;
												}else{
													this.orderSelectedText = this.orderSelectedText +", "+ this.cnsSystemObj.dropDowns.orders[obj][index].testname;
												}
											}
										}
									}
								}
								this.orderSelected();
								this.closeModalOrderInvestigation();
							}
						}

						//populate treatment list
						this.seizuresTempObj.seizuresTreatmentStatus = "Continue";
						if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentaction!=null
								&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentaction!=""){
							this.seizuresTempObj.seizuresTreatmentStatus = "Change";
							this.treatmentSeizuresTabsVisible = true;
							this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionlist = [];



							this.treatmentSelectedText = "";
							var treatmentActionList =[];
							treatmentActionList = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentaction
							.replace("[","").replace("]",",").replace(" ","").split(",");
							for(var index = 0;index<treatmentActionList.length;index++ ){
								for(var indexDropDown = 0; indexDropDown<this.cnsSystemObj.dropDowns.treatmentActionSeizures.length;indexDropDown++){
									if((treatmentActionList[index] != null && treatmentActionList[index] != undefined && treatmentActionList[index] !="")
                  && treatmentActionList[index].replace(" ","")
											== this.cnsSystemObj.dropDowns.treatmentActionSeizures[indexDropDown].key.replace(" ","")){
										this.cnsSystemObj.dropDowns.treatmentActionSeizures[indexDropDown].flag = true;
										var name = this.cnsSystemObj.dropDowns.treatmentActionSeizures[indexDropDown].key;
										if(name != null && name != undefined && name !=""){
											name = name.trim();

											if(name=='other'){
												this.SeizersTreatment = 'Others';
											}
											else if(name=='TRE069'){
												this.SeizersTreatment = 'Resuscritation';
											}
											else if(name=='TRE070'){
												this.SeizersTreatment = 'Manage Hypoglycemia';
											}
											else if(name=='TRE071'){
												this.SeizersTreatment = 'Manage Hypocalcemia';
											}
											else if(name=='TRE072' || name=='TRE073'){
												if(name=='TRE072'){
													this.SeizersTreatment = 'Medications';
												}
												else if(name=='TRE073'){
													this.SeizersTreatment = 'Antibiotics';
												}
												else{
													var c ='1';
												}
												if(this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.babyPrescription != null && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.babyPrescription != undefined && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.babyPrescription !=""){

													this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList = []
													this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList = []

													var prescriptions = this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.babyPrescription;
													for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
														for(var indexAllMed =0; indexAllMed<this.cnsSystemObj.dropDowns.medicine.length;indexAllMed++){
															if(prescriptions[indexPrescription].medid ==
																this.cnsSystemObj.dropDowns.medicine[indexAllMed].medid){
																var prescriptionItem = prescriptions[indexPrescription];
																if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined && prescriptionItem.startdate !=""){
																	prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
																	prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
																}
																if(prescriptions[indexPrescription].medicationtype == 'TYPE0001'){
																	this.cnsSystemObj.cnseventObject.commonEventsInfo.antibioticsBabyPrescriptionList.push(
																			Object.assign({},prescriptions[indexPrescription]));
																}
																else if(prescriptions[indexPrescription].medicationtype == 'TYPE0010'){
																	this.cnsSystemObj.cnseventObject.commonEventsInfo.medicationBabyPrescriptionList.push(
																			Object.assign({},prescriptions[indexPrescription]));
																}

																this.cnsSystemObj.dropDowns.medicine[indexAllMed].flag =true;
															}
														}
													}
												}
											}else if(name=='TRE068'){
												this.SeizersTreatment == 'Respiratory';
												this.cnsSystemObj.cnseventObject.commonEventsInfo.respSupport =
													this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.respSupport;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch(e){
			console.warn("Hey Shubham Gupta!  Exception in populate method: "+e);
		}
	}

  setSeizuresMetabolicRedirectVariable = function(){
		console.log("after redirect from Metabolic to seizures");
		this.cnsTabVisible('seizures');
		this.ActionSeizuresVisible();

		var currentcnsSystemObj = JSON.parse(localStorage.getItem('currentcnsSystemObj'));
		var seizuresTempObj = JSON.parse(localStorage.getItem('seizuresTempObj'));
		if(currentcnsSystemObj!=null){
			this.cnsSystemObj = currentcnsSystemObj;
			this.seizuresTempObj = seizuresTempObj;

			var eventTarget = JSON.parse(localStorage.getItem('eventTarget'));
			if(eventTarget == "hypoglycemia") {
				this.initTreatmentRedirectSeizures('Manage Hypoglycemia');
			} else if (eventTarget == "hypocalcemia") {
				this.initTreatmentRedirectSeizures('Manage Hypocalcemia');
			}
		}
		localStorage.removeItem("isComingFromSeizures");
		localStorage.removeItem("seizuresTempObj");
	}

  setAsphyxiaMetabolicRedirectVariable = function(){
		console.log("#########################");
		console.log("after redirect from Metabolic to Asphyxia");
		this.cnsTabVisible('asphyxia');
		this.ActionAsphyxiaVisible();
		this.treatmentAsphyxiaTabsVisible = true;
		var currentcnsSystemObj = JSON.parse(localStorage.getItem('currentcnsSystemObj'));
		var cnsTempObj = JSON.parse(localStorage.getItem('cnsTempObj'));
		if(currentcnsSystemObj!=null){
			this.cnsSystemObj = currentcnsSystemObj;
			this.cnsTempObj = cnsTempObj;
			var eventTarget = JSON.parse(localStorage.getItem('eventTarget'));
			if(eventTarget == "hypoglycemia") {
				this.cnsTempObj.asphyxiaTreatmentStatus == "Change";
				this.hypoglycemiaSelectAshphyxia();
				this.progressNotesAsphyxia();
			} else if(eventTarget == "acidosis"){
				this.cnsTempObj.asphyxiaTreatmentStatus == "Change";
				this.cnsTempObj.isAcidosisSavedAshphyxia = true;
				this.progressNotesAsphyxia();
			}
		}
		localStorage.removeItem("isComingFromAsphyxia");
		localStorage.removeItem("cnsTempObj");
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
	}

  redirectToMetabolic = function(eventSource, eventTarget){

		if(eventTarget == "hypoglycemia" && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList != null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE070')) {
			return;
		} else if (eventTarget == "hypoglycemia" && this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist != null
				&& this.cnsSystemObj.cnseventObject.asphyxiaEvent.currentEvent.saCnsAsphyxia.treatmentactionlist.includes('TRE089')) {
			return;
		} else if (eventTarget == "hypocalcemia" && this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList != null
				&& this.cnsSystemObj.cnseventObject.seizuresEvent.currentEvent.currentSeizures.treatmentactionList.includes('TRE071')) {
			return;
		} else if (eventTarget == "acidosis" && eventSource=="asphyxia" && this.cnsTempObj.isAcidosisSavedAshphyxia) {
			return;
		} else {
			if(eventSource == "seizures") {
				this.saveTreatmentSeizures(eventTarget);
				localStorage.setItem('seizuresTempObj',JSON.stringify(this.seizuresTempObj));
				localStorage.setItem('isComingFromSeizures', JSON.stringify(true));
			} else if(eventSource == "asphyxia") {
				this.saveTreatmentAsphyxia(eventTarget);
				localStorage.setItem('cnsTempObj',JSON.stringify(this.cnsTempObj));
				localStorage.setItem('isComingFromAsphyxia', JSON.stringify(true));
			}

			localStorage.setItem('isComingFromCNSEvent',JSON.stringify(true));
			localStorage.setItem('currentcnsSystemObj',JSON.stringify(this.cnsSystemObj));
			localStorage.setItem('eventTarget',JSON.stringify(eventTarget));
      this.router.navigateByUrl('/metabolic/assessment-sheet-metabolic');
		}
	}

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

		let tempObj = {
  		"hours" : hours,
  		"minutes" : minutes,
  		"seconds" : seconds,
  		"meridian" : ampm,
  		"date" : timeStamp,
    };

		return tempObj;
	};

  differenceInDays = function(dateStr){
    let oneDay = 24 * 60 * 60 * 1000;
		let firstDate = new Date();
		let secondDate = new Date(dateStr);
		let diffDays = Math.ceil(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
		return diffDays;
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
  refreshCNS(){
    this.init();
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
    this.printDataObj.dateFrom = fromDateTable;
    this.printDataObj.dateTo= toDateTable;
    this.printDataObj.uhid = this.childObject.uhid;
    this.printDataObj.whichTab = "CNS";
    if(fromDateTable == null) {
      this.fromDateTableNull = true;
    } else if(toDateTable == null) {
      this.toDateTableNull = true;
    } else if(fromDateTable >= toDateTable) {
      this.fromToTableError = true;
    } else {
      var data = [];
      for(var i=0; i < this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory.length; i++) {
        var item = this.cnsSystemObj.cnseventObject.commonEventsInfo.pastCnsHistory[i];
          if(item[3] >= fromDateTable.getTime() && item[3] <= toDateTable.getTime()) {
            var obj = Object.assign({}, item);
            data.push(obj);
        }
      }
      this.printDataObj.printData = data;
    }
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
    if(this.currentEvent == 'Asphyxia'){
      this.cnsSystemObj.cnseventObject.asphyxiaEvent.prescriptionList.push(data);
    }
    if(this.currentEvent == 'Seizures'){
      this.cnsSystemObj.cnseventObject.seizuresEvent.prescriptionList.push(data);
    }
  }

  ngOnInit() {
  }

}
