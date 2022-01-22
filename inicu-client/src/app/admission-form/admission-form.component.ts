import { Component, OnInit } from '@angular/core';
import {Http, Response} from '@angular/http';
import { AdmitPatientModel } from './admitPatientModel';
import { Router } from '@angular/router';
//import { ServicesService } from '../services.service';
import { APIurl } from '../../../model/APIurl';
import { Validation } from './validation';
import { TabValue } from './tabValue';
import { DropDownObject } from './dropDown';
import { TimeOfBirth } from './timeOfBirth';
import { Reason } from './reason';
import { Examination } from './examination';
import { AntenatalTempObj } from './antenatalTempObj';
import { SumbitAdmission } from './submitFinalJSON';
import { BabyDetailObj } from './babyDetailObj';
import * as $ from 'jquery/dist/jquery.min.js';
@Component({
  selector: 'admission-form',
  templateUrl: './admission-form.component.html',
  styleUrls: ['./admission-form.component.css']
})
export class AdmissionFormComponent implements OnInit {
  admitPatientModel : AdmitPatientModel;
  isEditProfile : boolean;
  initialAssessmentDisabled : boolean;
  whichInitialTab : string;
  currentdateValue : number;
  tabValue : TabValue;
  uhidInvalid : boolean;
  validation : Validation;
  apiData : APIurl;
	loggedUser : string;
  branchName : string;
  dropDownData : DropDownObject;
  timeOfBirth : TimeOfBirth;
  tempBedArray : Array<any>;
  timeOfAdmission : TimeOfBirth;
  reason : Reason;
  Preterm : boolean;
  causePrematurity : boolean;
  feedIntolerance : boolean;
  oneDay : number;
  oneHour : number;
  responseMsg : SumbitAdmission
  antenatalTempObj : AntenatalTempObj;
  http: Http;
  downesTabContent : string;
  childObject : any;
  reasonFlag :false;
  messageMotherNumber : string;
  rangeForLmp : number[];
  prnEmptyValidation : string;
  prnExistValidation : string;
  updateRoomBed : boolean;
  obj : Examination;
  tabContentScarf : string;
  tabContentHealToEar : string;
  tempSE : any;
  responseRedirect : BabyDetailObj;
  vanishSubmitResponseVariable : boolean;
  fentonRangePopupFlag : boolean;
  fentonGestationPopupFlag : boolean;
  fentonGenderPopupFlag : boolean;
  whichAdmissionTab : string;
  rangePip:Array<number>;
  tabContentBind : string;
  rangePeep : Array<number>;
  rangeFiO2 : Array<number>;
  rangeIt : Array<number>;
  rangeRate : Array<number>;
  rangeMap : Array<number>;
  isEditButtonVisible : boolean;
  nonEditable : boolean;
  tabContentPoplitel :string;
  tabContentArm : string;
  tabContentSquare : string;
  tabContentIvh : string;
  tabContentBallard : string;
  totalCyanosis : number;
  totalRetractions : number;
  totalGrunting : number;
  whichAdmissionTabParent : string;
  totalAirEntry : number;
  antenatalErrorMessage : string;
  admissionSystem : string;
  totalRespiratoryRate : number;
  downesValueCalculated : number;
  eventName : string;
  isBirthWeightFilled : boolean;
	isGestationFilled : boolean;
  maxDate : any;
  validateTimeOfBirth : boolean;
	validateTimeOfAdmission : boolean;
  hoursList : Array<any>;
  minutesList : Array<any>
  constructor(http: Http, private router: Router) {
    // serviceTime : ServicesService
    this.apiData = new APIurl();
    this.http = http;
		this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    //this.serviceTime = new ServicesService();
    this.tabValue = new TabValue();
    this.tabContentBind = "bind";
    this.dropDownData = new DropDownObject();
    this.currentdateValue = new Date().getTime();
    this.timeOfBirth = new TimeOfBirth;
    this.validation = new Validation();
    this.validation.MotherName = false;
    this.uhidInvalid = false;
    this.whichAdmissionTabParent = 'Mother';
    this.whichAdmissionTab = 'Parent';
    this.whichInitialTab = 'Reason';
    this.admissionSystem = 'Registration';
    this.initialAssessmentDisabled = true;
    this.timeOfAdmission = new TimeOfBirth;
    this.tempBedArray = [];
    this.reason = new Reason;
    this.isBirthWeightFilled = true;
		this.isGestationFilled = true;
    this.oneDay = 24 * 60 * 60 * 1000;
    this.oneHour = 60 * 60 * 1000;
    this.Preterm = null;
    this.reasonFlag = false;
    this.antenatalErrorMessage = "";
    this.antenatalTempObj = new AntenatalTempObj;
    this.causePrematurity = true;
    this.feedIntolerance = true;
    this.responseMsg = new SumbitAdmission();
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.downesTabContent = 'downes'
    this.getEmptyRegistrationForm();
    this.dropdownLoad();
    this.vanishSubmitResponseVariable = true;
    this.rangeForLmp = [0,1,2,3,4,5,6];
    this.updateRoomBed = false;
    this.obj = new Examination;
    this.responseRedirect = new BabyDetailObj;
    this.tempSE ={};
    this.fentonRangePopupFlag = true;
    this.fentonGestationPopupFlag = true;
    this.fentonGenderPopupFlag = true;
    this.rangePip=[];
    this.rangePeep = [];
    this.rangeFiO2 = [];
    this.rangeIt = [];
    this.rangeRate = [];
    this.rangeMap = [];
    this.isEditButtonVisible = false;
    this.nonEditable = false;
    this.tabContentHealToEar = 'healToEarLabel';
    this.tabContentScarf = 'scrafLabel';
    this.tabContentPoplitel = 'poplitealLabel';
    this.tabContentArm = 'armLabel';
    this.tabContentSquare = 'squareLabel';
    this.tabContentIvh = 'postureLabel';
    this.tabContentBallard = 'neuro';
    this.totalCyanosis = 0;
    this.totalRetractions = 0;
    this.totalGrunting = 0;
    this.totalAirEntry =0;
    this.totalRespiratoryRate = 0;
    this.downesValueCalculated = 0;
    this.prnExistValidation = "";
    this.maxDate = new Date();
    this.validateTimeOfBirth = false;
		this.validateTimeOfAdmission = false;
    this.hoursList = new Array<any>();
    this.minutesList = new Array<any>();
    this.init();
  }

  init(){
    // this.isEditProfile = JSON.parse(localStorage.getItem('isEditProfile'));
    // let test = JSON.parse(localStorage.getItem('selectedChild'));
    // this.getEmptyRegistrationForm();
    if(JSON.parse(localStorage.getItem('admissionformdata')) != undefined){
      this.tabValue.admissionSystem ='initial';
      this.tabValue.whichInitialTab = 'Systemic';
      this.isEditProfile = JSON.parse(localStorage.getItem('isEditProfile'));
      this.admitPatientModel = JSON.parse(localStorage.getItem('admissionformdata'));
      localStorage.removeItem('admissionformdata');
      localStorage.removeItem('dropDownData');
      localStorage.removeItem('tempBedArray');
    }

    if(localStorage.getItem('redirectBackToAdmission') != null) {
      this.redirectInit();
      console.log('redirected from assessment');
      localStorage.removeItem('eventName');
      localStorage.removeItem('redirectBackToAdmission');
    } else if (localStorage.getItem('isEditProfile') != null){
      this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
      this.isEditProfile = JSON.parse(localStorage.getItem('isEditProfile'));
      this.respSupportInit();

      if(this.isEditProfile == true){
        console.log("Comming from View Profile");
        this.initialAssessmentDisabled = false;
        this.validation.MotherName= true;
        this.validation.babyDetails= true;
        this.isEditButtonVisible = true;
        this.nonEditable = true;
        this.getRegistrationForm(this.childObject.uhid);
        this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
        // if(this.childObject.babyImage.name != null && this.childObject.babyImage.name != undefined && this.childObject.babyImage.name != ''){
        // 	this.babyImageObj.name = this.childObject.babyImage.name;
        // }
        // if(this.childObject.babyImage.name != null && this.childObject.babyImage.name != undefined && this.childObject.babyImage.name != ''){
        // 	this.babyImageObj.data = this.childObject.babyImage.data;
        // }
      } else {
      console.log("comming from Admit Patient");
      this.initialAssessmentDisabled = true;
      this.isEditButtonVisible = false;
      this.nonEditable = false;
      this.getEmptyRegistrationForm();
    }
  } else {
    console.log("Edit flag error !!");
    this.router.navigateByUrl('/dashboard');
  }


}
showFormula = function(){
  this.vanishSubmitResponseVariable = false;
  setTimeout(() => {
    this.vanishSubmitResponseVariable = true;
  }, 3000);
}

respSupportInit = function() {
  for(var i=10; i<=50; ++i) {
    this.rangePip.push(i)
  }

  for(var i=3;i<=12;++i) {
    this.rangePeep.push(i);
  }

  for(var i=21;i<=100;++i) {
    this.rangeFiO2.push(i);
  }

  for (var i = 0.10; i <=0.81; i+=0.01) {
    this.rangeIt.push(i.toFixed(2))
  }

  for(var i=10;i<=120;i+=1) {
    this.rangeRate.push(i.toFixed(2))
  }

  for(var i=3;i<=25;++i) {
    this.rangeMap.push(i)	;
  }
}

getEmptyRegistrationForm = function(){
  try
  {
    this.http.post(this.apiData.getEmptyAdvanceAdmissionForm,
      JSON.stringify({
        someParameter: 1,
        someOtherParameter: 2
      })).subscribe(res => {
        this.admitPatientModel =res.json();
        this.admitPatientModel.babyDetailObj.nicuroomno = this.childObject.room.key;
        this.admitPatientModel.babyDetailObj.nicubedno = this.childObject.bed.key;
        this.admitPatientModel.babyDetailObj.branchname = this.branchName;
        this.admitPatientModel.babyDetailObj.babyType = "Single";
        this.dropdownLoad();
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

openCriticalityInfo = function(){
  $("#criticalityOverlay").css("display", "block");
  $("#criticalityPopup").addClass("showing");
}
closeCriticalityInfo = function(){
  $("#criticalityOverlay").css("display", "none");
  $("#criticalityPopup").removeClass("showing");
}

openGestationInf = function(){
  $("#gestationOverlay").css("display", "block");
  $("#gestationPopup").addClass("showing");
}
closeGestationInfo = function(){
  $("#gestationOverlay").css("display", "none");
  $("#gestationPopup").removeClass("showing");
}

openKuppuswamyInfo = function(){
  $("#kuppuswamyOverlay").css("display", "block");
  $("#kuppuswamyPopup").addClass("showing");
}
closeKuppuswamyInfo = function(){
  $("#kuppuswamyOverlay").css("display", "none");
  $("#kuppuswamyPopup").removeClass("showing");
}

dropdownLoad = function() {
  try
  {
    this.http.request(this.apiData.admissionFormDropDowns + this.branchName + "/",
      JSON.stringify({
        someParameter: 1,
        someOtherParameter: 2
      })).subscribe(res => {
        this.dropDownData = res.json();
        console.log("The Dropdown object is");
        console.log(this.dropDownData);
        for (var i = 0; i < this.dropDownData.nicuRooms.length; i++) {
          if (this.dropDownData.nicuRooms[i].room.key == this.admitPatientModel.babyDetailObj.nicuroomno) {
            this.value = i;
            this.tempBedArray = this.dropDownData.nicuRooms[i].bed;
          }
        }
        this.roomNoLength = this.dropDownData.nicuRooms.length - 1;

        if(this.updateRoomBed) {
          for (var i = 0; i < this.dropDownData.nicuRooms.length; i++) {
            if(this.dropDownData.nicuRooms[i].room.key == this.admitPatientModel.babyDetailObj.nicuroomno) {
              this.obj.key = this.admitPatientModel.babyDetailObj.nicubedno;
              this.obj.value = this.childObject.bed.value;
              this.dropDownData.nicuRooms[i].bed.push(this.obj);
            }
          }
        }

        if(this.reasonFlag) {
          this.populateViewProfileReason();
        }

        this.dropDownData.districtList = [];
        this.dropDownData.addressList = [];
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

populateViewProfileReason = function() {
  for(var i = 0; i < this.admitPatientModel.selectedReasonList.length; i++) {
    var event = this.admitPatientModel.selectedReasonList[i];
    if(event.cause_event == 'Other') {
      this.otherReasonOfAdmission = event.cause_value;
    } else {
      for(var j = 0; j < this.dropDownData.reasonOfAdmission.length; j++) {
        if(this.dropDownData.reasonOfAdmission[j].eventName == event.cause_event) {
          var item = this.dropDownData.reasonOfAdmission[j];
          if(event.cause_value != null && event.cause_value != "") {
            for(var k = 0; k < item.causeList.length; k++) {
              var cause = item.causeList[k];
              if(event.cause_value.includes(cause.causeValue)) {
                cause.checkFlag = true;
              }
            }
          }

          if(event.cause_value_other != null && event.cause_value_other != "") {
            item.other = true;
            item.otherCause = event.cause_value_other;
          }
        }
      }
    }
  }
}

getDistrictList = function(state) {
  if(!(state == null || state == "")) {
    try
    {
      this.http.post(this.apiData.getDistrictList + "/" + state + "/",
      JSON.stringify({
        someParameter: 1,
        someOtherParameter: 2
      })).subscribe(res => {
        this.dropDownData.districtList = res.json();
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

resetDistrict = function() {
  this.admitPatientModel.personalDetailObj.add_district = "";
  this.resetCityPin();
}

getAddressList = function(state, district) {
  if(!(state == null || state == "" || district == null || district == "")) {
    try
    {
      this.http.post(this.apiData.getAddressList + "/" + state + "/" + district + "/",
      JSON.stringify({
        someParameter: 1,
        someOtherParameter: 2
      })).subscribe(res => {
        this.dropDownData.addressList = res.json();
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


resetCityPin = function() {
  this.admitPatientModel.personalDetailObj.add_city = "";
  this.admitPatientModel.personalDetailObj.add_pin = "";
}

selectPin = function() {
  for(var key in this.dropDownData.addressList) {
    if(this.admitPatientModel.personalDetailObj.add_city.trim() == key.trim()) {
      this.admitPatientModel.personalDetailObj.add_pin = this.dropDownData.addressList[key].pin_code;
      break;
    }
  }
}

changeInitialTabValue= function(value) {
  this.tabValue.whichInitialTab = value;
  this.submitRegistrationForm(false,'initial');
  if(value == 'Systemic'){
    this.populateSystemicDiagnosis();
  }
}
changeRegTabValueParent =function(value) {
  if(this.validation.MotherName== true){
    this.tabValue.whichAdmissionTabParent = value;
    this.whichAdmissionTabParent = value;
    this.whichAdmissionTab = 'Parent';
    this.tabValue.whichAdmissionTab = 'Parent';

  }
}
changeRegTabValue = function(value) {
  if(this.validation.MotherName== true || this.validation.babyDetails== true){
    this.tabValue.whichAdmissionTab = value;
    this.whichAdmissionTab = value;
  }
}

motherTabValidation = function(){
  if(this.admitPatientModel.personalDetailObj.mothername != null && this.admitPatientModel.personalDetailObj.mothername != "" && this.admitPatientModel.personalDetailObj.mothername != undefined){
    this.validation.MotherName= true;
    this.admitPatientModel.babyDetailObj.babyname = "B/O "+ this.admitPatientModel.personalDetailObj.mothername;
  }else{
    this.validation.MotherName= false;
  }
}
validateUhid = function() {
  if(this.admitPatientModel.babyDetailObj.uhid == null || this.admitPatientModel.babyDetailObj.uhid =="") {
    this.prnEmptyValidation = "PRN No. cannot be empty";
    //document.getElementById("uhid").focus();
    $("#prnNum").css("display","block");
    $("#prnLen").css("display","none");
  } else {
    if(this.admitPatientModel.babyDetailObj.uhid != undefined && this.admitPatientModel.babyDetailObj.uhid != null && this.admitPatientModel.babyDetailObj.uhid != "") {
      //document.getElementById("uhid").focus();
      this.prnEmptyValidation = "PRN No. must contains 6 characters";
      if(this.admitPatientModel.babyDetailObj.uhid.length < 6){
        $("#prnLen").css("display","block");
        $("#prnNum").css("display","none");
        return false;
      }
    }
    this.prnEmptyValidation = "";

    try
    {
      this.http.post(this.apiData.validateUhid + "/" + this.admitPatientModel.babyDetailObj.uhid + "/",
      JSON.stringify({
        someParameter: 1,
        someOtherParameter: 2
      })).subscribe(res => {
        this.validateUhidResponse = res.json();
        if(this.validateUhidResponse.type == "Success") {
          $("#prnExist").css("display","none");
          this.uhidInvalid = false;
          this.prnExistValidation = "";
        } else {
          //document.getElementById("uhid").focus();
          $("#prnExist").css("display","block");
          this.uhidInvalid = true;
          this.prnExistValidation = "UHID/ PRN already Exists";
          alert(this.prnExistValidation);
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

calFamilyIncome = function() {
  this.familyIncome = 0;
  if(!(this.admitPatientModel.personalDetailObj.mother_income == null || this.admitPatientModel.personalDetailObj.mother_income == "")) {
    this.familyIncome += (this.admitPatientModel.personalDetailObj.mother_income * 1);
  }

  if(!(this.admitPatientModel.personalDetailObj.father_income == null || this.admitPatientModel.personalDetailObj.father_income == "")) {
    this.familyIncome += (this.admitPatientModel.personalDetailObj.father_income * 1);
  }
  this.calKuppuswamyScores();
}

calKuppuswamyScores = function() {
  var education = "";
  var profession = "";
  this.admitPatientModel.personalDetailObj.kuppuswamy_class = "";
  this.admitPatientModel.personalDetailObj.kuppuswamy_score = 0;

  if(this.admitPatientModel.personalDetailObj.isMotherKuppuswamy != undefined && this.admitPatientModel.personalDetailObj.isMotherKuppuswamy != null){
    if(this.admitPatientModel.personalDetailObj.isMotherKuppuswamy) {
      education = this.admitPatientModel.personalDetailObj.mother_education;
      profession = this.admitPatientModel.personalDetailObj.mother_profession;
    } else {
      education = this.admitPatientModel.personalDetailObj.father_education;
      profession = this.admitPatientModel.personalDetailObj.father_profession;
    }

    if(education == "Professional or honors") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 7;
    } else if(education == "Graduate or postgraduate") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 6;
    } else if(education == "Intermediate or post-high school") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 5;
    } else if(education == "High School Certificate") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 4;
    } else if(education == "Middle School certificate") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 3;
    } else if(education == "Primary School certificate") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 2;
    } else if(education == "Illiterate") {
      this.admitPatientModel.personalDetailObj.kuppuswamy_score += 1;
    }

    //profession
    if(profession == "Professional") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 10;
  } else if(profession == "Semi-Professional") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 6;
  } else if(profession == "Clerical, Shop Owner, Farmer") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 5;
  } else if(profession == "Skilled Worker") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 4;
  } else if(profession == "Semi-Skilled Worker") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 3;
  } else if(profession == "Unskilled Worker") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 2;
  } else if(profession == "Unemployed") {
    this.admitPatientModel.personalDetailObj.kuppuswamy_score += 1;
  }

  //family Income
  if(this.familyIncome > 42875) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 12;
} else if(this.familyIncome > 21437) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 10;
} else if(this.familyIncome > 16077) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 6;
} else if(this.familyIncome > 10718) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 4;
} else if(this.familyIncome > 6430) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 3;
} else if(this.familyIncome > 2164) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 2;
} else if(this.familyIncome > 0){
  this.admitPatientModel.personalDetailObj.kuppuswamy_score += 1;
}

if(this.admitPatientModel.personalDetailObj.kuppuswamy_score > 25) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_class = "Upper class";
} else if(this.admitPatientModel.personalDetailObj.kuppuswamy_score > 15) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_class = "Upper middle class";
} else if(this.admitPatientModel.personalDetailObj.kuppuswamy_score > 10) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_class = "Lower middle class";
} else if(this.admitPatientModel.personalDetailObj.kuppuswamy_score > 4) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_class = "Upper lower class";
} else if(this.admitPatientModel.personalDetailObj.kuppuswamy_score > 0) {
  this.admitPatientModel.personalDetailObj.kuppuswamy_class = "Lower class";
}
}
}

babyTabValidation = function(){
  let babyDetaisValid = false;
  if(this.admitPatientModel.babyDetailObj.uhid != undefined && this.admitPatientModel.babyDetailObj.uhid != null && this.admitPatientModel.babyDetailObj.uhid != "") {
    babyDetaisValid= true ;
  }
  else{
    babyDetaisValid= false;
  }

  if(this.admitPatientModel.babyDetailObj.gender != undefined && this.admitPatientModel.babyDetailObj.gender != null && this.admitPatientModel.babyDetailObj.gender != "") {
    babyDetaisValid= true ;
  }
  else{
    if(babyDetaisValid== true){
      babyDetaisValid= false;
    }
  }

  if(this.admitPatientModel.babyDetailObj.babyType == 'Twins' || this.admitPatientModel.babyDetailObj.babyType == 'Triplets' || this.admitPatientModel.babyDetailObj.babyType == 'Quadruplets'){
    if(this.admitPatientModel.babyDetailObj.babyNumber != undefined && this.admitPatientModel.babyDetailObj.babyNumber != null && this.admitPatientModel.babyDetailObj.babyNumber != ""){
      babyDetaisValid= true ;
    }
    else{
      if(babyDetaisValid== true){
        babyDetaisValid= false;
      }
    }
  }

  if(this.admitPatientModel.babyDetailObj.inoutPatientStatus != undefined && this.admitPatientModel.babyDetailObj.inoutPatientStatus != null && this.admitPatientModel.babyDetailObj.inoutPatientStatus != "") {
    babyDetaisValid= true;
  }else{
    if(babyDetaisValid== true){
      babyDetaisValid= false;
    }
  }

  if(this.admitPatientModel.babyDetailObj.nicubedno != undefined && this.admitPatientModel.babyDetailObj.nicubedno != null && this.admitPatientModel.babyDetailObj.nicubedno != "") {
    babyDetaisValid= true;
  }else{
    if(babyDetaisValid== true){
      babyDetaisValid= false;
    }
  }
  console.log("test date" +this.admitPatientModel.babyDetailObj.dobObj);
  if(this.admitPatientModel.babyDetailObj.dobObj != undefined && this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.dobObj != "") {
    babyDetaisValid= true;
  }else{
    if(babyDetaisValid== true){
      babyDetaisValid= false;
    }
  }

  if(this.admitPatientModel.babyDetailObj.inoutPatientStatus == 'In Born') {
    if(this.timeOfBirth.hours != undefined && this.timeOfBirth.hours != null && this.timeOfBirth.hours != "") {
      babyDetaisValid= true;
    }else{
      if(babyDetaisValid== true){
        babyDetaisValid= false;
      }
    }
  }

  if(this.admitPatientModel.babyDetailObj.doaObj != undefined && this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != "") {
    babyDetaisValid= true;
  }else{
    if(babyDetaisValid== true){
      babyDetaisValid= false;
    }
  }

  if(this.timeOfAdmission.hours != undefined && this.timeOfAdmission.hours != null && this.timeOfAdmission.hours != "") {
    babyDetaisValid= true;
  }
  else{
    if(babyDetaisValid== true){
      babyDetaisValid= false;
    }
  }

  this.validation.babyDetails= babyDetaisValid ;
}

roomChange = function() {
  this.admitPatientModel.babyDetailObj.nicubedno = "";
  for (var i = 0; i < this.dropDownData.nicuRooms.length; i++) {
    if (this.dropDownData.nicuRooms[i].room.key == this.admitPatientModel.babyDetailObj.nicuroomno) {
      this.value = i;
      this.tempBedArray = this.dropDownData.nicuRooms[i].bed;
      break;
    }
  }
  this.babyTabValidation();
};

  calDayLife = function() {
    this.dateOfAdmissionEnable = false;
    if(this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.doaObj != null) {
      var firstDate = new Date(this.admitPatientModel.babyDetailObj.doaObj);
      var secondDate = new Date(this.admitPatientModel.babyDetailObj.dobObj);
      var ageValue = 0;

      if(this.admitPatientModel.babyDetailObj.dayoflifetype) {
        ageValue = Math.ceil(Math.abs((firstDate.getTime() - secondDate.getTime()) / (this.oneDay))) + 1;
      } else if(this.timeOfAdmission.hours != null && this.timeOfAdmission.hours != ''){
        firstDate.setHours(this.timeOfAdmission.hours.getHours());
        firstDate.setMinutes(this.timeOfAdmission.hours.getMinutes());

        if(this.timeOfBirth.hours != null && this.timeOfBirth.hours != '') {
          secondDate.setHours(this.timeOfBirth.hours.getHours());
          secondDate.setMinutes(this.timeOfBirth.hours.getMinutes());
        }
        ageValue = Math.round((firstDate.getTime() - secondDate.getTime()) / (this.oneHour));
      }
      this.admitPatientModel.babyDetailObj.dayoflife = ageValue + "";
    }
  };

admission_Date = function() {
  if(this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.dobObj != undefined){
    this.admitPatientModel.babyDetailObj.dobObj.setHours(0,0,0,0);
  }
  console.log("test date" +this.admitPatientModel.babyDetailObj.dobObj);
  this.admitPatientModel.babyDetailObj.dobObj = new Date(this.admitPatientModel.babyDetailObj.dobObj);

  if(this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != ""){
    this.admitPatientModel.babyDetailObj.doaObj = new Date(this.admitPatientModel.babyDetailObj.doaObj);

    if(this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.dobObj != "") {
      this.calDayLife();
    }
  }

  if(this.admitPatientModel.babyDetailObj.doaObj == undefined) {
    this.showErrorLabel = true;
  } else if(!(this.admitPatientModel.babyDetailObj.doaObj == null || this.admitPatientModel.babyDetailObj.doaObj == "")) {
    if (this.admitPatientModel.babyDetailObj.doaObj.getTime() < this.admitPatientModel.babyDetailObj.dobObj.getTime()) {
      this.showErrorLabel = true;
    }else{
      this.showErrorLabel = false;
    }
  }
  this.validateTobToa();
};

validateTobToa = function() {
  this.tobToaError = false;
  console.log("Hours" + this.timeOfBirth.hours);
  this.admitPatientModel.babyDetailObj.dobObj = new Date(this.admitPatientModel.babyDetailObj.dobObj);
  if(this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != ""){
    this.admitPatientModel.babyDetailObj.doaObj = new Date(this.admitPatientModel.babyDetailObj.doaObj);
  }
  if(!(this.admitPatientModel.babyDetailObj.dobObj == null || this.admitPatientModel.babyDetailObj.doaObj == null)) {
    if(this.admitPatientModel.babyDetailObj.dobObj.getTime() == this.admitPatientModel.babyDetailObj.doaObj.getTime()) {
      if(!((this.timeOfBirth.hours == undefined || this.timeOfBirth.hours == null)) ) {
        if(!((this.timeOfAdmission.hours == undefined || this.timeOfAdmission.hours == null))){
          this.checkFlag = false;
          /*if(this.timeOfBirth.meridium == "AM") {
          if(this.timeOfAdmission.meridium == "AM") {
          this.checkFlag = true;
        }
      } else if(this.timeOfBirth.meridium == "PM") {
      if(this.timeOfAdmission.meridium == "AM") {
      this.tobToaError = true;
    } else {
    this.checkFlag = true;
  }
}*/
let getMeridianBirth = this.convertTimeForDb(this.timeOfBirth.hours);
let getMeridianAdm = this.convertTimeForDb(this.timeOfAdmission.hours);
if(getMeridianBirth.slice(6,8) == "AM") {
  if(getMeridianAdm.slice(6,8) == "AM") {
    this.checkFlag = true;
  }
} else if(getMeridianBirth.slice(6,8) == "PM") {
  if(getMeridianAdm.slice(6,8) == "AM") {
    this.tobToaError = true;
  } else {
    this.checkFlag = true;
  }
}
if(this.checkFlag) {
  var birthHours = getMeridianBirth.slice(0,2) * 1;
  var addmissionHours = getMeridianAdm.slice(0,2) * 1;

  if(getMeridianBirth.slice(0,2) == "12") {
    birthHours = 0;
  }

  if(getMeridianAdm.slice(0,2) == "12") {
    addmissionHours = 0;
  }

  if(birthHours > addmissionHours) {
    this.tobToaError = true;
  } else if((birthHours == addmissionHours)
  && (getMeridianBirth.slice(3,5) * 1 > getMeridianAdm.slice(3,5) * 1)) {
    this.tobToaError = true;
  }
}
}
}
}
}
if(this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != ""){
  this.admitPatientModel.babyDetailObj.doaObj = new Date(this.admitPatientModel.babyDetailObj.doaObj);
}
this.babyTabValidation();
this.validatingTime();
this.calDayLife();
}

calPonderalIndex = function() {
  var poderalValue = 0;
  if(this.admitPatientModel.babyDetailObj.birthweight != null && this.admitPatientModel.babyDetailObj.birthweight != ""
  && this.admitPatientModel.babyDetailObj.birthlength != null && this.admitPatientModel.babyDetailObj.birthlength != "") {
    poderalValue = (this.admitPatientModel.babyDetailObj.birthweight * 100) /
    (this.admitPatientModel.babyDetailObj.birthlength * this.admitPatientModel.babyDetailObj.birthlength * this.admitPatientModel.babyDetailObj.birthlength);
  }
  this.admitPatientModel.babyDetailObj.ponderal_index = poderalValue;

  this.populateAdmissionStatusStr();
}

validatingTime = function(){

  if(this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.dobObj != undefined && this.timeOfBirth.hours != null && this.timeOfBirth.hours != undefined && this.timeOfBirth.hours != '' && this.admitPatientModel.babyDetailObj.dobObj != ''){
    var currentDate = new Date(this.admitPatientModel.babyDetailObj.dobObj);
    console.log(currentDate);
    currentDate.setHours(this.timeOfBirth.hours.getHours());
    currentDate.setMinutes(this.timeOfBirth.hours.getMinutes());
    // var hours = this.timeOfBirth.hours.split(":");
    // if(this.timeOfBirth.hours.includes("AM")){
    //   var minutes = hours[1].split(" AM");
    //   console.log(minutes);
    //   currentDate.setHours(hours[0]);
    //   currentDate.setMinutes(minutes[0]);
    //   if(hours[0] == "12"){
    //     currentDate.setHours("00");
    //   }
    // }
    // else{
    //   var minutes = hours[1].split(" PM");
    //   var changedHours = parseInt(hours[0]) + 12;
    //   currentDate.setMinutes(minutes[0]);
    //   if(changedHours == 24){
    //     currentDate.setHours("12");
    //   }
    //   else{
    //     currentDate.setHours(changedHours);
    //   }
    // }
    console.log(currentDate);
    console.log(this.admitPatientModel.babyDetailObj.dobObj);
    if(currentDate > new Date()){
      this.validateTimeOfBirth = true;
    }
    else{
      this.validateTimeOfBirth = false;
    }
  }

  if(this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != undefined && this.timeOfAdmission.hours != undefined && this.timeOfAdmission.hours != null && this.timeOfAdmission.hours != '' && this.admitPatientModel.babyDetailObj.doaObj != ''){
    var currentDate = new Date(this.admitPatientModel.babyDetailObj.doaObj);
    console.log(currentDate);
    currentDate.setHours(this.timeOfAdmission.hours.getHours());
    currentDate.setMinutes(this.timeOfAdmission.hours.getMinutes());
    // var hours = this.timeOfAdmission.hours.split(":");
    // if(this.timeOfAdmission.hours.includes("AM")){
    //   var minutes = hours[1].split(" AM");
    //   console.log(minutes);
    //   currentDate.setHours(hours[0]);
    //   currentDate.setMinutes(minutes[0]);
    //   if(hours[0] == "12"){
    //     currentDate.setHours("00");
    //   }
    // }
    // else{
    //   var minutes = hours[1].split(" PM");
    //   var changedHours = parseInt(hours[0]) + 12;
    //   currentDate.setMinutes(minutes[0]);
    //   if(changedHours == 24){
    //     currentDate.setHours("12");
    //   }
    //   else{
    //     currentDate.setHours(changedHours);
    //   }
    // }
    console.log(this.admitPatientModel.babyDetailObj.doaObj);
    console.log(currentDate);
    if(currentDate > new Date()){
      this.validateTimeOfAdmission = true;
    }
    else{
      this.validateTimeOfAdmission = false;
    }
  }
}

submitRegistrationForm = function(isassessmentsubmit, module){
  console.log("comming into the submitRegistrationForm");
  console.log(this.admitPatientModel);

  if(isassessmentsubmit){
		if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks == null || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks == undefined || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays == null || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays == undefined){
			this.isGestationFilled = false;
		}
		else{
			this.isGestationFilled = true;
		}
		if(this.admitPatientModel.babyDetailObj.birthweight == null || this.admitPatientModel.babyDetailObj.birthweight == undefined){
			this.isBirthWeightFilled = false;
		}
		else{
			this.isBirthWeightFilled = true;
		}
		if(this.isGestationFilled == false || this.isBirthWeightFilled == false){
			return false;
		}
	}
  // commented on 28-02-2018 as they may not know the no of dose
  /*if((this.admitPatientModel.antenatalHistoryObj.isAntenatalSteroidGiven != null && this.admitPatientModel.babyDetailObj.inoutPatientStatus=='In Born' && this.admitPatientModel.antenatalHistoryObj.isAntenatalSteroidGiven != '') &&
  ( this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose == null || this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose == '') ||
  ((this.admitPatientModel.antenatalHistoryObj.isCourseRepeated != null && this.admitPatientModel.antenatalHistoryObj.isCourseRepeated != '')
  && ( this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose == null || this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose == ''))){
    this.admissionSystem = "initial";
    this.whichInitialTab = "Antenatal";
    this.antenatalTempObj.whichantenatalTab ='Medications';
    this.antenatalErrorMessage = "Please fill Dose completely";
    return;
  }*/

  if(isassessmentsubmit) {
    this.admitPatientModel.babyDetailObj.isassessmentsubmit = true;
  } else if(this.admitPatientModel.babyDetailObj.isassessmentsubmit == null) {
    this.admitPatientModel.babyDetailObj.isassessmentsubmit = false;
  }

  if(this.admitPatientModel.personalDetailObj.fatherdob == null || this.admitPatientModel.personalDetailObj.fatherdob == undefined || this.admitPatientModel.personalDetailObj.fatherdob == ""){
    this.admitPatientModel.personalDetailObj.fatherdob = null;
  }

  if(this.admitPatientModel.personalDetailObj.motherdob == null || this.admitPatientModel.personalDetailObj.motherdob == undefined || this.admitPatientModel.personalDetailObj.motherdob == ""){
    this.admitPatientModel.personalDetailObj.motherdob = null;
  }
  console.log("test date" +this.admitPatientModel.babyDetailObj.dobObj);
  if(this.admitPatientModel.babyDetailObj.dobObj == null || this.admitPatientModel.babyDetailObj.dobObj == undefined || this.admitPatientModel.babyDetailObj.dobObj == ""){
    this.admitPatientModel.babyDetailObj.dobObj = null;
  }

  if(this.admitPatientModel.babyDetailObj.doaObj == null || this.admitPatientModel.babyDetailObj.doaObj == undefined || this.admitPatientModel.babyDetailObj.doaObj == ""){
    this.admitPatientModel.babyDetailObj.doaObj = null;
  }

  if(this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != ""){
    if(typeof(this.admitPatientModel.antenatalHistoryObj.eddTimestamp) === 'string') {
      this.admitPatientModel.antenatalHistoryObj.eddTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
    }
    this.admitPatientModel.antenatalHistoryObj.eddTimestamp.setHours(this.admitPatientModel.antenatalHistoryObj.eddTimestamp.getHours() + 6);
  }
  if(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != ""){
    if(typeof(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp) === 'string') {
      this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
    }
    this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
    this.admitPatientModel.antenatalHistoryObj.lmpTimestamp.setHours(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp.getHours() + 6);
  }

  if(this.admitPatientModel.antenatalHistoryObj.etTimestamp != null && this.admitPatientModel.antenatalHistoryObj.etTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.etTimestamp != ""){
		if(typeof this.admitPatientModel.antenatalHistoryObj.etTimestamp === 'string') {
			this.admitPatientModel.antenatalHistoryObj.etTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.etTimestamp);
		}
		this.admitPatientModel.antenatalHistoryObj.etTimestamp.setHours(this.admitPatientModel.antenatalHistoryObj.etTimestamp.getHours() + 6);
	}
  if(this.validate('submit')) {

    if (this.admitPatientModel.babyDetailObj.doaObj != null) {
      this.admitPatientModel.babyDetailObj.doaObj = new Date(this.admitPatientModel.babyDetailObj.doaObj);
      this.admitPatientModel.babyDetailObj.doaStr = this.dateformatter(this.admitPatientModel.babyDetailObj.doaObj, "gmt", "standard");
    } else {
      return;
    }

    if (this.admitPatientModel.babyDetailObj.dobObj != null) {
      this.admitPatientModel.babyDetailObj.dobObj = new Date(this.admitPatientModel.babyDetailObj.dobObj);
      console.log("test test "+ this.admitPatientModel.babyDetailObj.dobObj);
      this.admitPatientModel.babyDetailObj.dobStr = this.dateformatter(this.admitPatientModel.babyDetailObj.dobObj, "gmt", "standard");
      console.log("test test "+ this.admitPatientModel.babyDetailObj.dobStr);
    } else {
      return;
    }

    if (this.admitPatientModel.personalDetailObj.motherdob != null) {
      this.admitPatientModel.personalDetailObj.motherdob = new Date(this.admitPatientModel.personalDetailObj.motherdob);
      this.admitPatientModel.personalDetailObj.motherdobStr = this.dateformatter(this.admitPatientModel.personalDetailObj.motherdob, "gmt", "standard");
    }

    if (this.admitPatientModel.personalDetailObj.fatherdob != null) {
      this.admitPatientModel.personalDetailObj.fatherdob = new Date(this.admitPatientModel.personalDetailObj.fatherdob);
      this.admitPatientModel.personalDetailObj.fatherdobStr = this.dateformatter(this.admitPatientModel.personalDetailObj.fatherdob, "gmt", "standard");
    }
    let meridianBirth;
    let meridianAdmission;
    this.timeOfBirth.hours = new Date(this.timeOfBirth.hours);
    this.timeOfAdmission.hours = new Date(this.timeOfAdmission.hours);
    if((this.timeOfBirth.hours == undefined || this.timeOfBirth.hours == null)) {
      this.admitPatientModel.babyDetailObj.timeofbirth = "00,00,AM";
    } else {

      this.admitPatientModel.babyDetailObj.timeofbirth = this.convertTimeForDb(this.timeOfBirth.hours);
    }

    if((this.timeOfAdmission.hours == undefined || this.timeOfAdmission.hours == null)) {
      this.admitPatientModel.babyDetailObj.timeofadmission = "00,00,AM";
    } else {

      this.admitPatientModel.babyDetailObj.timeofadmission = this.convertTimeForDb(this.timeOfAdmission.hours);
    }
    if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != undefined &&this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != ''){
      this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate = new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate);
    }
    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != undefined &&this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ''){
      this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
    }
    try
    {
      this.http.post(this.apiData.submitAdvanceAdmissionForm + this.loggedUser,
      this.admitPatientModel).subscribe(res => {
        this.responseMsg = res.json();
        console.log(this.responseMsg);
        if (this.responseMsg.type == "Success") {
          this.admitPatientModel = this.responseMsg.returnedObject;
          console.log("data from submitRegistrationForm");
          console.log(this.admitPatientModel);

          if(module == 'registration') {
            console.log('registration form submit');
            this.initialAssessmentDisabled = false;
            this.tabValue.admissionSystem = 'initial';
            localStorage.setItem('isEditProfile', "true");
            this.admitToDrPanel(this.admitPatientModel.babyDetailObj.uhid, false);
            try{
              this.convertDBtoJS();
            } catch(e) {
              console.warn("Exception in save convertDBtoJS: " + e);
            }
            this.getRegistrationForm(this.admitPatientModel.babyDetailObj.uhid);
          } else if(isassessmentsubmit) {
            this.admitToDrPanel(this.admitPatientModel.babyDetailObj.uhid, true);
          } else {
            try{
              this.convertDBtoJS();
            } catch(e) {
              console.warn("Exception in save convertDBtoJS: " + e);
            }
            this.getRegistrationForm(this.admitPatientModel.babyDetailObj.uhid);
            this.admitToDrPanel(this.admitPatientModel.babyDetailObj.uhid, false);
          }
        } else {
          if(this.responseMsg.message != null) {
            // baby already admitted case
            alert(this.responseMsg.message);
            this.admitPatientModel = this.responseMsg.returnedObject;
            if(this.admitPatientModel.babyDetailObj.babydetailid != null) {
              this.processAdmissionData();
            }
          } else if(this.isEditButtonVisible) {
            alert('Error in save, last saved form retrieved!!');
            this.getRegistrationForm(this.childObject.uhid);
          } else if(this.admitPatientModel.babyDetailObj.babydetailid != null){
            alert('Error in save, last saved form retrieved!!');
            this.getRegistrationForm(this.admitPatientModel.babyDetailObj.uhid);
          } else {
            alert('Error in save, blank form retrieved!!');
            this.getEmptyRegistrationForm();
          }
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

getRegistrationForm = function(uhid) {
  console.log("comming into the getAdvanceAdmissionForm");
  try {
    this.http.post(this.apiData.getAdvanceAdmissionForm + uhid+"/",
    JSON.stringify({
      someParameter: 1,
      someOtherParameter: 2})).subscribe(res => {
      this.admitPatientModel = res.json();
      this.processAdmissionData();
    }, err => {
      console.log("Error occured.")
    });
  } catch(e) {
    console.log("Exception in http service:"+e);
  };
};

processAdmissionData = function() {
  if(this.admitPatientModel.babyDetailObj.babyType != 'Single'){
    this.isMultiple = "multiple";
  } else {
    this.isMultiple = "Single";
  }
  console.log(this.admitPatientModel);
  if(this.admitPatientModel.selectedReasonList.length > 0) {
    this.reasonFlag = true;
  }
  this.convertDBtoJS();
  this.dropdownLoad();
  this.updateRoomBed = true;
  this.calFamilyIncome();
  if(this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != "" && this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.dobObj != "") {
    this.calDayLife();
  }
}

convertDBtoJS = function(){
  console.log('in convertDBtoJS');
  console.log("test date " +this.admitPatientModel.babyDetailObj.dobObj);
  this.admitPatientModel.babyDetailObj.dobObj = this.dateformatter(this.admitPatientModel.babyDetailObj.dateofbirth, "utf", "gmt");
  this.admitPatientModel.babyDetailObj.doaObj = this.dateformatter(this.admitPatientModel.babyDetailObj.dateofadmission, "utf", "gmt");
  if(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != ""){
    this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp, "utf", "gmt");
  }
  if(this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != ""){
    this.admitPatientModel.antenatalHistoryObj.eddTimestamp = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.eddTimestamp, "utf", "gmt");
  }
  if(this.admitPatientModel.antenatalHistoryObj.etTimestamp != null && this.admitPatientModel.antenatalHistoryObj.etTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.etTimestamp != ""){
    this.admitPatientModel.antenatalHistoryObj.etTimestamp = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.etTimestamp, "utf", "gmt");
  }
  if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != undefined && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != ""){
    this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate, "utf", "gmt");
  }
  if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != undefined && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ""){
    this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate, "utf", "gmt");
  }
  if(!(this.admitPatientModel.babyDetailObj.timeofbirth == undefined || this.admitPatientModel.babyDetailObj.timeofbirth == null
    || this.admitPatientModel.babyDetailObj.timeofbirth == "")){
      var timeBirthArr = [];
      let sethourstemp;
      timeBirthArr = this.admitPatientModel.babyDetailObj.timeofbirth.split(',');
      let tempDateForTime = new Date();
      if(this.admitPatientModel.babyDetailObj.timeofbirth.slice(6,8) == "PM"){
        if(this.admitPatientModel.babyDetailObj.timeofbirth.slice(0,2)*1 < 12)
        {
          sethourstemp = this.admitPatientModel.babyDetailObj.timeofbirth.slice(0,2)*1 + 12;
        }else{
          sethourstemp = this.admitPatientModel.babyDetailObj.timeofbirth.slice(0,2)*1;
        }
      }else{
        sethourstemp = this.admitPatientModel.babyDetailObj.timeofbirth.slice(0,2)*1;
      }
      tempDateForTime.setHours(sethourstemp);
      tempDateForTime.setMinutes(this.admitPatientModel.babyDetailObj.timeofbirth.slice(3,5));
      this.timeOfBirth.hours = new Date(tempDateForTime);
    }

    if(!(this.admitPatientModel.babyDetailObj.timeofadmission == undefined || this.admitPatientModel.babyDetailObj.timeofadmission == null
      || this.admitPatientModel.babyDetailObj.timeofadmission == "")){
        var timeAdmissionArr = [];
        timeAdmissionArr = this.admitPatientModel.babyDetailObj.timeofadmission.split(',');
        let tempDateForTimeAdm = new Date();
        let sethourstempAdm;

        if(this.admitPatientModel.babyDetailObj.timeofadmission.slice(6,8) == "PM"){
          if(this.admitPatientModel.babyDetailObj.timeofadmission.slice(0,2)*1 < 12)
          {
            sethourstempAdm = this.admitPatientModel.babyDetailObj.timeofadmission.slice(0,2)*1 + 12;
          }else{
            sethourstempAdm = this.admitPatientModel.babyDetailObj.timeofadmission.slice(0,2)*1;
          }
        }else{
          sethourstempAdm = this.admitPatientModel.babyDetailObj.timeofadmission.slice(0,2)*1;
        }
        tempDateForTimeAdm.setHours(sethourstempAdm);
        tempDateForTimeAdm.setMinutes(this.admitPatientModel.babyDetailObj.timeofadmission.slice(3,5));
        this.timeOfAdmission.hours = new Date(tempDateForTimeAdm);
      }

      if(this.admitPatientModel.personalDetailObj.motherdob != null) {
        if (typeof(this.admitPatientModel.personalDetailObj.motherdob) === 'number') {
          this.admitPatientModel.personalDetailObj.motherdob = this.dateformatter(this.admitPatientModel.personalDetailObj.motherdob, "utf", "gmt");
        } else {
          this.admitPatientModel.personalDetailObj.motherdob = this.admitPatientModel.personalDetailObj.motherdob;
        }
      }

      if(this.admitPatientModel.personalDetailObj.mother_income != null && this.admitPatientModel.personalDetailObj.mother_income != "") {
        this.admitPatientModel.personalDetailObj.mother_income *= 1;
      }

      if(this.admitPatientModel.personalDetailObj.father_income != null && this.admitPatientModel.personalDetailObj.father_income != "") {
        this.admitPatientModel.personalDetailObj.father_income *= 1;
      }

      if(this.admitPatientModel.personalDetailObj.fatherdob != null) {
        if (typeof(this.admitPatientModel.personalDetailObj.fatherdob) === 'number') {
          this.admitPatientModel.personalDetailObj.fatherdob = this.dateformatter(this.admitPatientModel.personalDetailObj.fatherdob, "utf", "gmt");
        } else {
          this.admitPatientModel.personalDetailObj.fatherdob = this.admitPatientModel.personalDetailObj.fatherdob;
        }
      }

      //systemic temp obj value set here
      if(this.admitPatientModel.sysAssessmentObj.jaundice) {
      this.tempSE.jaundiceDone = true;
    }

    if(this.admitPatientModel.sysAssessmentObj.respiratorySystem == 'Yes') {
      this.tempSE.respSystemDone = true;
    }

    if(this.admitPatientModel.sysAssessmentObj.infection == 'Yes') {
      this.tempSE.infectionDone = true;
    }

    if(this.admitPatientModel.sysAssessmentObj.cns == 'Yes') {
      this.tempSE.cnsDone = true;
    }
  }


  admitToDrPanel = function(uhid, redirect) {
    console.log("comming into the admitToDrPanel");
    try{
      this.http.request(this.apiData.getChildData + uhid + "/",)
      .subscribe(res => {
        this.responseRedirect = res.json();
        if(this.responseRedirect != null && this.responseRedirect.length > 0) {
          localStorage.setItem('selectedChild', JSON.stringify(this.responseRedirect[0]));
          if(redirect) {
            this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
          }
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
validate = function(event) {
  this.messageMotherNumber = "";
  console.log(this.admitPatientModel);
  if(this.tabValue.admissionSystem == 'Registration') {
    if(this.tabValue.whichAdmissionTab == 'Parent') {
      if(this.whichAdmissionTabParent == 'Mother') {
        if(this.admitPatientModel.personalDetailObj.mothername == undefined || this.admitPatientModel.personalDetailObj.mothername == null || this.admitPatientModel.personalDetailObj.mothername == "") {
          //document.getElementById("motherName").focus();
          $("#emptymotherName").css("display","block");
          return false;
        }
        if(this.admitPatientModel.personalDetailObj.mother_phone!=null && this.admitPatientModel.personalDetailObj.mother_phone != undefined){
          this.admitPatientModel.personalDetailObj.mother_phone = this.admitPatientModel.personalDetailObj.mother_phone.toString();
          if(this.admitPatientModel.personalDetailObj.mother_phone.length != 10){
            $("#emptymotherMobileNumber").css("display","block");
            return false;
          }
        }
        if(this.admitPatientModel.personalDetailObj.mother_aadhar!=null && this.admitPatientModel.personalDetailObj.mother_aadhar != undefined){
          this.admitPatientModel.personalDetailObj.mother_aadhar = this.admitPatientModel.personalDetailObj.mother_aadhar.toString();
          if(this.admitPatientModel.personalDetailObj.mother_aadhar.length != 12){
            $("#emptymotherAdhaarNumber").css("display","block");
            return false;
          }
        }

        if(event == 'nextTab'){
          this.whichAdmissionTabParent = 'Father';
          this.tabValue.whichAdmissionTabParent = 'Father';
          return;
        }
      } else if (this.whichAdmissionTabParent == 'Father') {
        if(event == 'nextTab'){
          if(this.admitPatientModel.personalDetailObj.father_phone!=null && this.admitPatientModel.personalDetailObj.father_phone != undefined){
            this.admitPatientModel.personalDetailObj.father_phone = this.admitPatientModel.personalDetailObj.father_phone.toString();
            if(this.admitPatientModel.personalDetailObj.father_phone.length != 10){
              $("#emptyfatherMobileNumber").css("display","block");
              return false;
            }
          }
          if(this.admitPatientModel.personalDetailObj.father_aadhar!=null && this.admitPatientModel.personalDetailObj.father_aadhar != undefined){
            this.admitPatientModel.personalDetailObj.father_aadhar = this.admitPatientModel.personalDetailObj.father_aadhar.toString();
            if(this.admitPatientModel.personalDetailObj.father_aadhar.length != 12){
              $("#emptyfatherAdhaarNumber").css("display","block");
              return false;
            }
          }

          this.whichAdmissionTabParent = 'Address';
          this.tabValue.whichAdmissionTabParent = 'Address';
          return;
        }
      } else if (this.whichAdmissionTabParent == 'Address') {
        if(event == 'nextTab'){
          this.whichAdmissionTabParent = 'Emergency';
          this.tabValue.whichAdmissionTabParent = 'Emergency';
          return;
        }
      } else if (this.whichAdmissionTabParent == 'Emergency') {
        if(event == 'nextTab'){
          if(this.admitPatientModel.personalDetailObj.emergency_contactno!=null && this.admitPatientModel.personalDetailObj.emergency_contactno != undefined){
            this.admitPatientModel.personalDetailObj.emergency_contactno = this.admitPatientModel.personalDetailObj.emergency_contactno.toString();
            if(this.admitPatientModel.personalDetailObj.emergency_contactno.length != 10){
              $("#emptyemergencyMobileNumber").css("display","block");
              return false;
            }
          }

          this.whichAdmissionTab = 'Baby';
          this.tabValue.whichAdmissionTab = 'Baby';
          return;
        }
      }
    } else if(this.tabValue.whichAdmissionTab == 'Baby') {
      $("#emptyGender").css("display","none");
      $("#emptyAdmitType").css("display","none");
      $("#emptyToa").css("display","none");
      $("#emptyDob").css("display","none");
      $("#emptyHr").css("display","none");

      if(this.admitPatientModel.babyDetailObj.uhid != undefined && this.admitPatientModel.babyDetailObj.uhid != null && this.admitPatientModel.babyDetailObj.uhid != "") {
        //document.getElementById("uhid").focus();
        this.prnEmptyValidation = "PRN No. must contains 6 characters";
        if(this.admitPatientModel.babyDetailObj.uhid.length < 6){
          $("#prnLen").css("display","block");
          $("#prnNum").css("display","none");
          return false;
        }
      }

      if(this.admitPatientModel.babyDetailObj.uhid == undefined || this.admitPatientModel.babyDetailObj.uhid == null || this.admitPatientModel.babyDetailObj.uhid == "") {
        //document.getElementById("uhid").focus();
        $("#prnNum").css("display","block");
        $("#prnLen").css("display","none");
        return false;
      }

      if(this.uhidInvalid) {
        //document.getElementById("uhid").focus();
        $("#prnExist").css("display","block");
        alert(this.prnExistValidation);
        return false;
      }

      if(this.admitPatientModel.babyDetailObj.gender == undefined || this.admitPatientModel.babyDetailObj.gender == null || this.admitPatientModel.babyDetailObj.gender == "") {
        //document.getElementById("gender").focus();
        $("#emptyGender").css("display","block");
        return false;
      }

      if(this.admitPatientModel.babyDetailObj.babyType == 'Twins'){
        if(this.admitPatientModel.babyDetailObj.babyNumber == undefined || this.admitPatientModel.babyDetailObj.babyNumber == null || this.admitPatientModel.babyDetailObj.babyNumber == ""){
          $("#emptyTwintype").css("display","block");
          return false;
        }
      }
      if(this.admitPatientModel.babyDetailObj.babyType == 'Triplets'){
        if(this.admitPatientModel.babyDetailObj.babyNumber == undefined || this.admitPatientModel.babyDetailObj.babyNumber == null || this.admitPatientModel.babyDetailObj.babyNumber == ""){
          $("#emptyTwintypeTrip").css("display","block");
          return false;
        }
      }
      if(this.admitPatientModel.babyDetailObj.babyType == 'Quadruplets'){
        if(this.admitPatientModel.babyDetailObj.babyNumber == undefined || this.admitPatientModel.babyDetailObj.babyNumber == null || this.admitPatientModel.babyDetailObj.babyNumber == ""){
          $("#emptyTwintypeQuad").css("display","block");
          return false;
        }
      }

      if(this.admitPatientModel.babyDetailObj.inoutPatientStatus == undefined || this.admitPatientModel.babyDetailObj.inoutPatientStatus == null || this.admitPatientModel.babyDetailObj.inoutPatientStatus == "") {
        //document.getElementById("addmissiontype").focus();
        $("#emptyAdmitType").css("display","block");
        return false;
      }

      if(this.admitPatientModel.babyDetailObj.nicubedno == undefined || this.admitPatientModel.babyDetailObj.nicubedno == null || this.admitPatientModel.babyDetailObj.nicubedno == "") {
        //document.getElementById("bedNo").focus();
        $("#emptyBedNo").css("display","block");
        return false;
      }

      if(this.admitPatientModel.babyDetailObj.dobObj == undefined || this.admitPatientModel.babyDetailObj.dobObj == null || this.admitPatientModel.babyDetailObj.dobObj == "") {
        //document.getElementById("dob").focus();
        $("#emptyDob").css("display","block");
        return false;
      }

      if(this.validateTimeOfBirth == true){
				return false;
			}
			if(this.validateTimeOfAdmission == true){
				return false;
			}

      if(this.timeOfBirth.hours == undefined || this.timeOfBirth.hours == null || this.timeOfBirth.hours == "") {
        //document.getElementById("tobhr").focus();
        $("#emptyHr").css("display","block");
        return false;
      }

      if(this.timeOfAdmission.hours == undefined || this.timeOfAdmission.hours == null || this.timeOfAdmission.hours == "") {
        //document.getElementById("toahr").focus();
        $("#emptyToa").css("display","block");
        return false;
      }
      // \b((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))
      /*else if(this.timeOfAdmission.minutes == undefined || this.timeOfAdmission.minutes == null || this.timeOfAdmission.minutes == "") {
      document.getElementById("toamin").focus();
      $("#emptyToa").css("display","block");
      return false;
    } else if(this.timeOfAdmission.meridium == undefined || this.timeOfAdmission.meridium == null || this.timeOfAdmission.meridium == "") {
    document.getElementById("toamer").focus();
    $("#emptyToa").css("display","block");
    return false;
  }*/
  else if(this.tobToaError) {
    //document.getElementById("toahr").focus();
    $("#emptyToa").css("display","block");
    return false;
  }
  console.log("test date" +this.admitPatientModel.babyDetailObj.dobObj);
  // var str = this.admitPatientModel.babyDetailObj.dobObj;
  // var patt1 = /(([0-9])|([0-2][0-9])|([3][0-1]))\s\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\s\d{4}/;
  // if(this.admitPatientModel.babyDetailObj.dobObj != undefined && this.admitPatientModel.babyDetailObj.dobObj != null && this.admitPatientModel.babyDetailObj.dobObj != "") {
  //   if(!str.match(patt1)){
  //      	document.getElementById("dob").focus();
  //      $("#dobvalidinput").css("display","block");
  //     return false;
  //   }else{
  //     $("#dobvalidinput").css("display","none");
  //   }
  //}

  // var str = this.timeOfBirth.hours;
  // var patt1 = /[0-9][0-9][:][0-9][0-9]\s[A|P][M]/;
  // if(this.timeOfBirth.hours != undefined && this.timeOfBirth.hours != null && this.timeOfBirth.hours != "") {
  //   if(!str.match(patt1)){
  //      	document.getElementById("tobhr").focus();
  //      $("#invalidHr").css("display","block");
  //     return false;
  //   }else{
  //     	$("#invalidHr").css("display","none");
  //   }
  // }
  //
  // var str = this.admitPatientModel.babyDetailObj.doaObj;
  // var patt1 = /(([0-9])|([0-2][0-9])|([3][0-1]))\s\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\s\d{4}/;
  // if(this.admitPatientModel.babyDetailObj.doaObj != undefined && this.admitPatientModel.babyDetailObj.doaObj != null && this.admitPatientModel.babyDetailObj.doaObj != "") {
  //   if(!str.match(patt1)){
  //      	document.getElementById("doa").focus();
  //      $("#doavalidinput").css("display","block");
  //     return false;
  //   }else{
  //      	$("#doavalidinput").css("display","none");
  //   }
  // }
  //
  // var str1 = this.timeOfAdmission.hours;
  // var patt2 = /[0-9][0-9][:][0-9][0-9]\s[A|P][M]/;
  // if(this.timeOfAdmission.hours != undefined && this.timeOfAdmission.hours != null && this.timeOfAdmission.hours != "") {
  //   if(!str1.match(patt2)){
  //      	document.getElementById("toahr").focus();
  //      $("#invalidToa").css("display","block");
  //     return false;
  //   }else{
  //   	$("#invalidToa").css("display","none");
  //   }
  // }

  if(this.admitPatientModel.babyDetailObj.doaObj == undefined || this.admitPatientModel.babyDetailObj.doaObj == null || this.admitPatientModel.babyDetailObj.doaObj == "") {
    //document.getElementById("doa").focus();
    $("#emptyDOA").css("display","block");
    return false;
  } else if(this.showErrorLabel) {
    //document.getElementById("doa").focus();
    $("#emptyDOA").css("display","block");
    return false;
  }

  if(this.admitPatientModel.babyDetailObj.inoutPatientStatus == 'Out Born' && event == 'nextTab') {

    this.whichAdmissionTab = 'Transfer';
    this.tabValue.whichAdmissionTab = 'Transfer';
    return;
  } else if (event == 'nextTab') {
    this.whichAdmissionTab = 'Kuppuswamy';
    this.tabValue.whichAdmissionTab = 'Kuppuswamy';
    return;
  }
} else if (this.whichAdmissionTab == 'Transfer' && event == 'nextTab'){
  if(this.admitPatientModel.babyDetailObj.refferedNumber!=null && this.admitPatientModel.babyDetailObj.refferedNumber != undefined){
    this.admitPatientModel.babyDetailObj.refferedNumber = this.admitPatientModel.babyDetailObj.refferedNumber.toString();
    if(this.admitPatientModel.babyDetailObj.refferedNumber.length != 10){
      $("#emptyTransferMobileNumber").css("display","block");
      return false;
    }
  }
  this.whichAdmissionTab = 'Kuppuswamy';
  this.tabValue.whichAdmissionTab = 'Kuppuswamy';
  return;
} else if(this.whichAdmissionTab == 'Kuppuswamy' && event == 'nextTab') {
  this.tabValue.admissionSystem = 'initial';
  this.whichInitialTab = 'Reason';
  return;
}
} else if(this.tabValue.admissionSystem == 'initial') {
  if(this.tabValue.whichInitialTab == "Reason"  && event == 'nextTab') {
    this.tabValue.whichInitialTab = 'Antenatal';
    this.antenatalTempObj.whichantenatalTab='EDD';
    this.submitRegistrationForm(false,'initial');
  }
  else if(this.tabValue.whichInitialTab == "Birth"  && event == 'nextTab') {
    this.tabValue.whichInitialTab = 'Status';
    this.submitRegistrationForm(false,'initial');
  }
  else if(this.tabValue.whichInitialTab == "Status"  && event == 'nextTab') {
    this.tabValue.whichInitialTab = 'Exam';
    this.submitRegistrationForm(false,'initial');
  }
  else if(this.tabValue.whichInitialTab == "Exam"  && event == 'nextTab') {
    this.tabValue.whichInitialTab = 'Systemic';
    this.submitRegistrationForm(false,'initial');
  }
}
return true;
}
calParentAge = function(dateold, parentType) {
  dateold = new Date(dateold);
  var datenew = new Date();
  if(dateold == undefined || dateold.getTime() > datenew.getTime()) {
    return;
  }
  var ynew = datenew.getFullYear();
  var mnew = datenew.getMonth();
  var dnew = datenew.getDate();
  var yold = dateold.getFullYear();
  var mold = dateold.getMonth();
  var dold = dateold.getDate();
  var diff = ynew - yold;
  if (mold > mnew) {
    diff--;
  } else if (mold == mnew) {
    if (dold > dnew){
      diff--;
    }
  }

  if(parentType=='mother') {
    this.admitPatientModel.personalDetailObj.motherage = diff;
  } else if(parentType=='father') {
    this.admitPatientModel.personalDetailObj.fatherage = diff;
  }
};


convertTimeForDb = function(time){
  time = new Date(time);
  let meridianStr : string;
  let hoursStr;
  let minutesStr;
  if (time.getHours() > 12) {
    meridianStr = 'PM';
    hoursStr = time.getHours() -12;
  } else if (time.getHours() < 12) {
    meridianStr = 'AM';
    hoursStr = time.getHours();
    if (time.getHours() == 0) {
      meridianStr = 'AM';
      hoursStr = 12;
    }
  } else {
    hoursStr = time.getHours();
    meridianStr = 'PM';
  }
  if(hoursStr < 10){
    hoursStr = "0"+ hoursStr;
  }

  if(time.getMinutes() < 10){
    minutesStr ="0"+ time.getMinutes();
  }else{
    minutesStr = time.getMinutes();
  }
  return hoursStr + "," + minutesStr +"," + meridianStr;
}

prematurityAntenatal = function(flagValue) {
  this.populateAntenalStr();
  this.addRemoveReasonOfAdmission(flagValue, 'Prematurity', '', null);
}

addRemoveReasonOfAdmission = function(flagValue, event, cause, otherValue) {

  if(event == 'Prematurity'){
    this.isPreterm = flagValue;
    this.admitPatientModel.antenatalHistoryObj.prematurity = flagValue;
    this.populateAntenalStr();
  }

  var causeArr = [];
  if(this.admitPatientModel.selectedReasonList != null && this.admitPatientModel.selectedReasonList.length > 0){
    causeArr = this.admitPatientModel.selectedReasonList;
  }

  if(causeArr.length == 0) {
    var causeEmptyObj = Object.assign({}, this.admitPatientModel.reasonEmptyObj); //angular.copy(
      if(event == 'Other' && cause != undefined && cause != null && cause != "") {
      causeEmptyObj.cause_event = event;
      causeEmptyObj.cause_value = cause;
      causeArr.push(causeEmptyObj);
    } else if(cause == 'Other') {
      if(otherValue != null && otherValue != "") {
        causeEmptyObj.cause_event = event;
        causeEmptyObj.cause_value_other = otherValue;
        causeArr.push(causeEmptyObj);
      }
    } else {
      causeEmptyObj.cause_event = event;
      causeEmptyObj.cause_value = cause;
      causeArr.push(causeEmptyObj);
    }
  } else if(flagValue == true) {
    var updateNewFlag = true;
    for(var i=0;i<causeArr.length;i++){
      if(causeArr[i].cause_event == event){
        updateNewFlag = false;
        if(event == 'Other') {
          if(cause != undefined && cause != null && cause != "") {
            causeArr[i].cause_event = event;
            causeArr[i].cause_value = cause;
          } else {
            causeArr.splice(i,1);
          }
        } else if(cause == 'Other') {
          causeArr[i].cause_value_other = otherValue;
        } else {
          if(causeArr[i].cause_value == null || causeArr[i].cause_value == "") {
            causeArr[i].cause_value = cause;
          } else {
            causeArr[i].cause_value += ", " + cause;
          }
        }
      }
    }

    if(updateNewFlag) {
      var causeEmptyObj = Object.assign({}, this.admitPatientModel.reasonEmptyObj);
      if(event == 'Other' && cause != undefined && cause != null && cause != "") {
        causeEmptyObj.cause_event = event;
        causeEmptyObj.cause_value = cause;
        causeArr.push(causeEmptyObj);
      } else if(cause == 'Other') {
        if(otherValue != null && otherValue != "") {
          causeEmptyObj.cause_event = event;
          causeEmptyObj.cause_value_other = otherValue;
          causeArr.push(causeEmptyObj);
        }
      } else {
        causeEmptyObj.cause_event = event;
        causeEmptyObj.cause_value = cause;
        causeArr.push(causeEmptyObj);
      }
    }
  } else {
    for(var i=0;i<causeArr.length;i++){
      if(causeArr[i].cause_event == event){
        if(cause == 'Other') {
          causeArr[i].cause_value_other = "";
        } else {
          causeArr[i].cause_value = causeArr[i].cause_value.replace(cause,'').trim();
          if(causeArr[i].cause_value.charAt(0) == ','){
            causeArr[i].cause_value = causeArr[i].cause_value.substring(2, causeArr[i].cause_value.length);
          } else if(causeArr[i].cause_value.charAt(causeArr[i].cause_value.length - 1) == ','){
            causeArr[i].cause_value = causeArr[i].cause_value.substring(0, causeArr[i].cause_value.length - 1);
          } else {
            causeArr[i].cause_value = causeArr[i].cause_value.replace(", , ",', ');
          }
        }

        if(causeArr[i].cause_value == "null") {
          causeArr[i].cause_value = "";
        }
        if((causeArr[i].cause_value_other == null || causeArr[i].cause_value_other == "")
        && (causeArr[i].cause_value == null || causeArr[i].cause_value == "")) {
          causeArr.splice(i,1);
        }
      }
    }
  }
  this.admitPatientModel.selectedReasonList = causeArr;
  this.populateReasonStr();
  this.populateSystemicDiagnosis();
}

// reason for admission
populateReasonStr = function() {
console.log(JSON.stringify(this.admitPatientModel));
this.admitPatientModel.admissionNotesObj.reason_admission = "";
if(this.admitPatientModel.selectedReasonList != null && this.admitPatientModel.selectedReasonList.length >0) {
  for(var i=0; i < this.admitPatientModel.selectedReasonList.length; i++) {
    if(this.admitPatientModel.admissionNotesObj.reason_admission != "") {
      this.admitPatientModel.admissionNotesObj.reason_admission += ", ";
    }

    if((this.admitPatientModel.selectedReasonList[i].cause_value == undefined || this.admitPatientModel.selectedReasonList[i].cause_value == null || this.admitPatientModel.selectedReasonList[i].cause_value == "")
    && (this.admitPatientModel.selectedReasonList[i].cause_value_other == undefined || this.admitPatientModel.selectedReasonList[i].cause_value_other == null || this.admitPatientModel.selectedReasonList[i].cause_value_other == "")) {
      this.admitPatientModel.admissionNotesObj.reason_admission += this.admitPatientModel.selectedReasonList[i].cause_event;
    } else {
      this.admitPatientModel.admissionNotesObj.reason_admission += this.admitPatientModel.selectedReasonList[i].cause_event + " (";
      if(this.admitPatientModel.selectedReasonList[i].cause_value == null || this.admitPatientModel.selectedReasonList[i].cause_value == "") {
        this.admitPatientModel.admissionNotesObj.reason_admission += this.admitPatientModel.selectedReasonList[i].cause_value_other;
      } else if(this.admitPatientModel.selectedReasonList[i].cause_value_other == undefined || this.admitPatientModel.selectedReasonList[i].cause_value_other == null
        || this.admitPatientModel.selectedReasonList[i].cause_value_other == ""){
          this.admitPatientModel.admissionNotesObj.reason_admission += this.admitPatientModel.selectedReasonList[i].cause_value;
        } else {
          this.admitPatientModel.admissionNotesObj.reason_admission += this.admitPatientModel.selectedReasonList[i].cause_value + ", " + this.admitPatientModel.selectedReasonList[i].cause_value_other;
        }
        this.admitPatientModel.admissionNotesObj.reason_admission += ")";
      }
    }
  }
}

//Antenatal
changeRegTabValueAntenatal= function(value){
this.antenatalTempObj.whichantenatalTab = value;
console.log(this.admitPatientModel);
this.submitRegistrationForm(false,'initial');
}
defaultGestationRecord = function(){
  this.admitPatientModel.antenatalHistoryObj.etTimestamp = null;
  this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = null;
  this.admitPatientModel.antenatalHistoryObj.eddTimestamp = null;
  this.admitPatientModel.antenatalHistoryObj.trimesterType = null;
  this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks = null;
  this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays = null;
  this.populateAntenalStr();
}
antenatalFormSubmitOperation = function(value){
  if(value != null && value != undefined && value != ""){
    if(value=="Submit"){
      this.submitRegistrationForm(false,'initial');
    }else if(value=="Next"){
      this.antenalMoveToNextTab();
    }else{
      console.log("Antenatal operation not matched with any valid operations.");
    }
  }else{
    console.log("Antenatal operation not matched with any valid operations.");
  }
}

calculateGPAL = function(){
  var tempPALTotal  = 0;
  if(this.admitPatientModel.antenatalHistoryObj.aScore != null && this.admitPatientModel.antenatalHistoryObj.aScore != "" && this.admitPatientModel.antenatalHistoryObj.aScore != undefined){
    tempPALTotal += this.admitPatientModel.antenatalHistoryObj.aScore;
  }

  if(this.admitPatientModel.antenatalHistoryObj.pScore != null && this.admitPatientModel.antenatalHistoryObj.pScore != undefined && this.admitPatientModel.antenatalHistoryObj.pScore != ""){
    tempPALTotal += this.admitPatientModel.antenatalHistoryObj.pScore;
  }

  if(this.admitPatientModel.antenatalHistoryObj.lScore != null && this.admitPatientModel.antenatalHistoryObj.lScore != undefined && this.admitPatientModel.antenatalHistoryObj.lScore != ""){
    tempPALTotal += this.admitPatientModel.antenatalHistoryObj.lScore;
  }

  if(tempPALTotal*1 == this.admitPatientModel.antenatalHistoryObj.gScore*1){
    this.antenatalTempObj.gpalMessage = "";
  }else{
    this.antenatalTempObj.gpalMessage = "Kindly validate your G, P, A, L values";
  }
  this.populateAntenalStr();
}

calculateETLmp =  function(){

      var dayInMillis = 24*60*60*1000;
			var timeInMillisByEt = new Date(this.admitPatientModel.antenatalHistoryObj.etTimestamp).getTime();
			var lmpTimeInMillis = ((timeInMillisByEt*1) - (14*24*60*60*1000));
			var dateLmp = new Date(lmpTimeInMillis);
			console.log("Edd date value: " + dateLmp);
      this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = this.dateformatter(dateLmp, "utf", "gmt");

			// this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = this.dateformatter(dateEdd, "utf", "gmt");
			// this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp, "gmt", "default");
			// this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = this.admitPatientModel.antenatalHistoryObj.lmpTimestamp.slice(0,12);

			var timeInMillisByLmp = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp).getTime();
			var eddTimeInMillis = ((timeInMillisByLmp*1) + (280*dayInMillis));
			var dateEdd = new Date(eddTimeInMillis);
			console.log("Edd date value: " + dateEdd);
			// this.admitPatientModel.antenatalHistoryObj.eddTimestamp = this.dateformatter(dateEdd, "utf", "gmt");
			// this.admitPatientModel.antenatalHistoryObj.eddTimestamp = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.eddTimestamp, "gmt", "default");
			// this.admitPatientModel.antenatalHistoryObj.eddTimestamp = this.admitPatientModel.antenatalHistoryObj.eddTimestamp.slice(0,12);

      this.admitPatientModel.antenatalHistoryObj.eddTimestamp = this.dateformatter(dateEdd, "utf", "gmt");
      this.calculateEddLmp();

		}

calculateEddLmp  = function(){

  var dayInMillis = 24*60*60*1000;
  var add280Days =  280*24*60*60*1000;

  if(this.admitPatientModel.antenatalHistoryObj.eddBy!=null){

    if(this.admitPatientModel.antenatalHistoryObj.eddBy=='notKnown'){
      this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = null;
      this.admitPatientModel.antenatalHistoryObj.eddTimestamp = null;
      this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks = null;
      this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays = null;
    }else{
      if(this.admitPatientModel.antenatalHistoryObj.eddBy=='lmp' &&
      (this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != undefined
        && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != "")){
          var timeInMillisByLmp = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp).getTime();
          var eddTimeInMillis = ((timeInMillisByLmp*1) + (280*dayInMillis));
          var dateEdd = new Date(eddTimeInMillis);
          //this.admitPatientModel.antenatalHistoryObj.eddTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
          this.admitPatientModel.antenatalHistoryObj.eddTimestamp = this.dateformatter(dateEdd, "utf", "gmt");
        }else if(this.admitPatientModel.antenatalHistoryObj.eddBy=='usg'
        && (this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null
          && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != undefined
          && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != "")){
            var timeInMillis = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp).getTime();

            var timeInMillisRemainder = timeInMillis % dayInMillis;
            var lmpTimeInMillis = (timeInMillis-timeInMillisRemainder) +dayInMillis - add280Days;

            var dateLmp = new Date(lmpTimeInMillis);
            //this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
            this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = this.dateformatter(dateLmp, "utf", "gmt");
          }
          console.log("test date" +this.admitPatientModel.babyDetailObj.dobObj);
          if(this.admitPatientModel.babyDetailObj.dobObj!=null && this.admitPatientModel.babyDetailObj.dobObj!=''){
            this.antenatalTempObj.dobNullMessage  ="";
            var timeInMillis = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp).getTime();
            var dobInMillis = this.dateformatter(this.admitPatientModel.babyDetailObj.dobObj, "utf", "gmt").getTime();
            //var dobInMillis = new Date(this.admitPatientModel.babyDetailObj.dobObj).getTime();
            var gestInMillis = dobInMillis - (timeInMillis - add280Days);

            var noOfDays = (gestInMillis / dayInMillis);
            noOfDays = Math.round(noOfDays);
            var gestDays = noOfDays % 7;

            var gestWeeks = (noOfDays - gestDays)/7;
            if(gestWeeks > 40) {
              this.warnLmp = true;
              this.messageLmp = "Gestation can't be more than 40 weeks";
            } else if(gestWeeks <20) {
              this.warnLmp = true;
              this.messageLmp = "Gestation can't be less than 20 weeks";
            } else {
              this.messageLmp = "";
              console.log("TOTAL DAYS: "+noOfDays+"Gest Weeks: " + gestWeeks +" days: " +gestDays);
              this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks = gestWeeks;
              this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays = gestDays;
            }
          }else{
            this.antenatalTempObj.dobNullMessage = "Please enter DOB";
          }
        }
      }

      this.populateAntenalStr();
    }

    calculateSteroidLastDoseTime = function(){
      var dayInMillis = 24*60*60*1000;
      var add280Days =  280*24*60*60*1000;
      this.antenatalTempObj.firstSteroidErrorMsg = "";
      if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname!=null
        && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname!=undefined
        && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname!=""){
          if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose!=null){

            if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname=='Dexa'){
              if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose*1>4){
                this.antenatalTempObj.firstSteroidErrorMsg = "Please enter proper dose range";
              }
            }else if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname=='Beta'){
              if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose*1>2){
                this.antenatalTempObj.firstSteroidErrorMsg = "Please enter proper dose range";
              }
            }
          }
        }

        this.antenatalTempObj.secondSteroidErrorMsg = "";
        if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname!=null){
          if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose!=null){

            if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname=='Dexa'){
              if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose>4){
                this.antenatalTempObj.secondSteroidErrorMsg = "Please enter proper dose range";
              }
            }else if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname=='Beta'){
              if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose>2){
                this.antenatalTempObj.secondSteroidErrorMsg = "Please enter proper dose range";
              }
            }
          }
        }

        console.log(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate);
        //			if(!basicUtility.isEmpty(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate)
        //					&& !basicUtility.isEmpty(this.admitPatientModel.babyDetailObj.dobObj)){
        //				this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate = new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate);
        /*this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate.setHours(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.giventimehours*1);
        this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate.setMinutes(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.giventimeminutes);*/
        //}

        //if(!basicUtility.isEmpty(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail)
        //	&& !basicUtility.isEmpty(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate)
        //	&& !basicUtility.isEmpty(this.admitPatientModel.babyDetailObj.dobObj)){
        //	this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
        //	console.log(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate.getHours());
        /*this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate.setHours(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.giventimehours*1);
        this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate.setMinutes(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.giventimeminutes*1);*/
        //}
        console.log("test date" +this.admitPatientModel.babyDetailObj.dobObj);
        if(this.timeOfBirth.hours != null && this.timeOfBirth.hours != undefined && this.timeOfBirth.hours != ""){
          this.admitPatientModel.babyDetailObj.dobObj = new Date(this.admitPatientModel.babyDetailObj.dobObj);
          this.timeOfBirth.hours = new Date(this.timeOfBirth.hours);
          this.admitPatientModel.babyDetailObj.dobObj.setHours(this.timeOfBirth.hours.getHours());
          this.admitPatientModel.babyDetailObj.dobObj.setMinutes(this.timeOfBirth.hours.getMinutes());
        }
        this.admitPatientModel.babyDetailObj.dobObj = new Date(this.admitPatientModel.babyDetailObj.dobObj);
        var dobTimeInMilliSec  = this.admitPatientModel.babyDetailObj.dobObj.getTime();
        if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != null &&
          this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != undefined &&
          this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != ""){
            var givenFirstSteroid = new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate);
            var givenMedTimeInMilliSec = givenFirstSteroid.getTime();
            this.antenatalTempObj.noOfDays = ((givenMedTimeInMilliSec*1 - dobTimeInMilliSec*1) / dayInMillis);

            if(this.antenatalTempObj.noOfDays*1>=0){
              this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.isBirthLastDoseTextGT24 = true;
            }else{
              if(this.antenatalTempObj.noOfDays<=-1){
                this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.isBirthLastDoseTextGT24 = false;
              }else{
                this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.isBirthLastDoseTextGT24 = true;
              }
              this.antenatalTempObj.noOfDays = Math.round(this.antenatalTempObj.noOfDays);
              this.antenatalTempObj.noOfDays = Math.abs(this.antenatalTempObj.noOfDays);

            }
          }

          if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null &&
            this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != undefined &&
            this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ""){
              var givenSecSteroid = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
              var givenMedTimeInMilliSecSecond = givenSecSteroid.getTime();
              this.antenatalTempObj.noOfDaysSecond = ((givenMedTimeInMilliSecSecond*1 - dobTimeInMilliSec*1) / dayInMillis);

              if(this.antenatalTempObj.noOfDaysSecond*1>=0){
                this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.isBirthLastDoseTextGT24 = true;
              }else{
                if(this.antenatalTempObj.noOfDaysSecond<=-1){
                  this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.isBirthLastDoseTextGT24 = false;
                }else{
                  this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.isBirthLastDoseTextGT24 = true;
                }
                this.antenatalTempObj.noOfDaysSecond = Math.round(this.antenatalTempObj.noOfDaysSecond);
                this.antenatalTempObj.noOfDaysSecond = Math.abs(this.antenatalTempObj.noOfDaysSecond);
              }
            }

            this.populateAntenalStr();
          }
          antenalMoveToNextTab = function(){
            if(this.antenatalTempObj.whichantenatalTab != null && this.antenatalTempObj.whichantenatalTab != undefined
              && this.antenatalTempObj.whichantenatalTab !=""){
                if(this.antenatalTempObj.whichantenatalTab=='EDD'){
                  if(this.admitPatientModel.antenatalHistoryObj.eddBy == null || this.admitPatientModel.antenatalHistoryObj.eddBy == undefined){
						          return false;
                  }
                  if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks == null || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks == undefined || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays == null || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays == undefined){
                      return false;
                  }
                  this.antenatalTempObj.whichantenatalTab = 'Screening';
                  this.submitRegistrationForm(false,'initial');
                }else if(this.antenatalTempObj.whichantenatalTab=='Screening'){
                  this.antenatalTempObj.whichantenatalTab = 'Risk';
                  this.submitRegistrationForm(false,'initial');
                }else if(this.antenatalTempObj.whichantenatalTab=='Risk'){
                  this.antenatalTempObj.whichantenatalTab = 'Investigations';
                  this.submitRegistrationForm(false,'initial');
                }else if(this.antenatalTempObj.whichantenatalTab=='Investigations'){
                  this.antenatalTempObj.whichantenatalTab = 'Medications';
                  this.submitRegistrationForm(false,'initial');
                }else if(this.antenatalTempObj.whichantenatalTab=='Medications'){
                  this.antenatalTempObj.whichantenatalTab = 'Labour';
                  this.submitRegistrationForm(false,'initial');
                }
                else if(this.antenatalTempObj.whichantenatalTab=='Labour'){
                  this.whichInitialTab='Birth';
                  this.tabValue.whichInitialTab = 'Birth';
                  this.submitRegistrationForm(false,'initial');
                }
              }else{
                this.antenatalTempObj.whichantenatalTab = 'EDD';
              }
              this.calculateEddLmp();
            }

            populateAntenalStr = function(){
              console.log("Antenatal History");
              console.log(this.admitPatientModel.antenatalHistoryObj);
              var template ="";
              this.admitPatientModel.admissionNotesObj.antenatal_history ="";


              console.log("in maternalDetailNotes");
              // method to generate maternal notes
              var nameStr="";

              if(this.admitPatientModel.antenatalHistoryObj.gScore != null && this.admitPatientModel.antenatalHistoryObj.gScore != ""){
                nameStr += "G"+this.admitPatientModel.antenatalHistoryObj.gScore;
              }
              if(this.admitPatientModel.antenatalHistoryObj.pScore != null && this.admitPatientModel.antenatalHistoryObj.pScore != ""){
                nameStr += "P"+this.admitPatientModel.antenatalHistoryObj.pScore;
              }
              if(this.admitPatientModel.antenatalHistoryObj.aScore != null && this.admitPatientModel.antenatalHistoryObj.aScore != ""){
                nameStr += "A"+this.admitPatientModel.antenatalHistoryObj.aScore;
              }
              if(this.admitPatientModel.antenatalHistoryObj.lScore != null && this.admitPatientModel.antenatalHistoryObj.lScore != ""){
                nameStr += "L"+this.admitPatientModel.antenatalHistoryObj.lScore;
              }
              if(nameStr !=""){
                template += "Born to "+nameStr + " mother";
              }


              var conceptionStr = "";
              if(this.admitPatientModel.antenatalHistoryObj.conceptionType !=null){
                if(this.admitPatientModel.antenatalHistoryObj.conceptionType == 'natural'){
                  conceptionStr = " Natural Conception ";
                }
                else if(this.admitPatientModel.antenatalHistoryObj.conceptionType == 'ivf'){
                  conceptionStr = " IVF Conception ";
                  if(this.admitPatientModel.antenatalHistoryObj.conceptionDetails != null && this.admitPatientModel.antenatalHistoryObj.conceptionDetails != ""){
                    conceptionStr +=  " ("+this.admitPatientModel.antenatalHistoryObj.conceptionDetails + ")";
                  }
                }
                if(conceptionStr !=""){
                  template +=" with" +  conceptionStr  + ". ";
                }

              }else{
                if(conceptionStr =="" && template != ""){
                  template += ". ";
                }
              }



              var eddStr = "";
              var gestStr = "";
              var dateStr;
              var dayStr;
              var yearStr;
              var monthStr ;
              var dayeddStr;
              var yeareddStr ;
              var montheddStr;
              if(this.admitPatientModel.antenatalHistoryObj.eddBy != null){
                if(this.admitPatientModel.antenatalHistoryObj.eddBy == 'lmp'){
                  if(this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null && this.admitPatientModel.antenatalHistoryObj.eddTimestamp !=""){

                    if (this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null) {
                      this.admitPatientModel.antenatalHistoryObj.eddTimestampStr = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
                      this.admitPatientModel.antenatalHistoryObj.eddTimestampStr = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.eddTimestampStr, "gmt", "standard");
                    }
                    dateStr = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
                    dayeddStr =dateStr.getDate();
                    montheddStr =dateStr.getMonth() +1;
                    yeareddStr = dateStr.getFullYear();
                    eddStr += " EDD (" +dayeddStr+ "-"+ montheddStr+"-" +yeareddStr + ")"; //this.admitPatientModel.antenatalHistoryObj.eddTimestampStr
                  }
                  //	eddStr += "LMP";
                  if(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp !=""){

                  if (this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null) {
                    this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
                    this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr, "gmt", "standard");
                  }

                  dateStr = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
                  dayStr =dateStr.getDate();
                  monthStr = dateStr.getMonth() + 1;
                  console.log(dateStr.getMonth());
                  yearStr = dateStr.getFullYear();
                  eddStr += " based on LMP (" +dayStr+ "-"+ monthStr+"-" +yearStr + ")";// +this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr +
                }
                if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks !=""){
                  gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + " weeks";
                }
                if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays !=""){
                  gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays + " days";
                }
                if(gestStr != ""){
                  eddStr += ", Gestation by LMP" + gestStr;
                }
              }else if(this.admitPatientModel.antenatalHistoryObj.eddBy == 'usg'){
                //eddStr += "USG";
                gestStr = "";
                if(this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null && this.admitPatientModel.antenatalHistoryObj.eddTimestamp !=""){
                  dateStr = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
                  dayeddStr = dateStr.getDate();
                  montheddStr =dateStr.getMonth() +1;
                  yeareddStr = dateStr.getFullYear();
                  eddStr += " EDD (" +dayeddStr+ "-"+ montheddStr+"-" +yeareddStr + ")";
                }
                if(this.admitPatientModel.antenatalHistoryObj.trimesterType != null){
                  if(this.admitPatientModel.antenatalHistoryObj.trimesterType =='1'){
                    eddStr += " based on 1st Trimester" ;
                  }
                  else if(this.admitPatientModel.antenatalHistoryObj.trimesterType =='2'){
                    eddStr += " based on 2nd Trimester" ;
                  }
                  else if(this.admitPatientModel.antenatalHistoryObj.trimesterType =='3'){
                    eddStr += " based on 3rd Trimester" ;
                  }
                }
                if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks !=""){
                  gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + " weeks";
                }
                if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays !=""){
                  gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays + " days";
                }
                if(gestStr != ""){
                  eddStr += ", Gestation by USG" + gestStr;
                }
              }

              else if(this.admitPatientModel.antenatalHistoryObj.eddBy == 'ivf'){

      					if(this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null && this.admitPatientModel.antenatalHistoryObj.eddTimestamp !=""){

      						if (this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null) {
      							this.admitPatientModel.antenatalHistoryObj.eddTimestampStr = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
      							this.admitPatientModel.antenatalHistoryObj.eddTimestampStr = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.eddTimestampStr, "gmt", "standard");
      						}
      						dateStr = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
      						dayeddStr =dateStr.getDate();
      						montheddStr =dateStr.getMonth() +1;
      						yeareddStr = dateStr.getFullYear();
      						eddStr += " EDD (" +dayeddStr+ "-"+ montheddStr+"-" +yeareddStr + ")"; //this.admitPatientModel.antenatalHistoryObj.eddTimestampStr
      					}
      					//	eddStr += "LMP";
      					if(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp !=""){

      						if (this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null) {
      							this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
      							this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr, "gmt", "standard");
      						}

      						dateStr = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
      						dayStr =dateStr.getDate();
      						monthStr = dateStr.getMonth() + 1;
      						console.log(dateStr.getMonth());
      						yearStr = dateStr.getFullYear();
      						eddStr += " based on LMP (" +dayStr+ "-"+ monthStr+"-" +yearStr + ")";// +this.admitPatientModel.antenatalHistoryObj.lmpTimestampStr +
      					}
      					if(this.admitPatientModel.antenatalHistoryObj.etTimestamp != null && this.admitPatientModel.antenatalHistoryObj.etTimestamp !=""){

      						if (this.admitPatientModel.antenatalHistoryObj.etTimestamp != null) {
      							this.admitPatientModel.antenatalHistoryObj.etTimestampStr = new Date(this.admitPatientModel.antenatalHistoryObj.etTimestamp);
      							this.admitPatientModel.antenatalHistoryObj.etTimestampStr = this.dateformatter(this.admitPatientModel.antenatalHistoryObj.etTimestampStr, "gmt", "standard");
      						}
      						dateStr = new Date(this.admitPatientModel.antenatalHistoryObj.etTimestamp);
      						dayeddStr =dateStr.getDate();
      						montheddStr =dateStr.getMonth() +1;
      						yeareddStr = dateStr.getFullYear();
      						eddStr += " based on ET (" +dayeddStr+ "-"+ montheddStr+"-" +yeareddStr + ")"; //this.admitPatientModel.antenatalHistoryObj.eddTimestampStr
      					}
      					if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks !=""){
      						gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + " weeks";
      					}
      					if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays !=""){
      						gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays + " days";
      					}
      					if(gestStr != ""){
      						eddStr += ", Gestation by ET Date" + gestStr;
      					}
      				}

              else if(this.admitPatientModel.antenatalHistoryObj.eddBy == 'notKnown'){

                if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null){
                  if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks !=""){
                    gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + " weeks";
                  }
                  if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays !=""){
                    gestStr += " " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays + " days";
                  }
                  if(gestStr != ""){
                    eddStr += " Gestation" + gestStr;
                  }
                  if(this.admitPatientModel.antenatalHistoryObj.notKnownType != null && this.admitPatientModel.antenatalHistoryObj.notKnownType == 'clinical estimate'){
                    eddStr += " (by clinical estimate)";
                  } else if(this.admitPatientModel.antenatalHistoryObj.notKnownType != null && this.admitPatientModel.antenatalHistoryObj.notKnownType == 'ballard'){
                    eddStr += " (by ballard)";
                  }
                }
              }
              if(eddStr != ""){
                template += eddStr + ". ";
              }
            }


            var antenatalCheckUPStr = "";
            if(this.admitPatientModel.antenatalHistoryObj.antenatelCheckupStatus != null){
              if(this.admitPatientModel.antenatalHistoryObj.antenatelCheckupStatus == 'booked'){
                antenatalCheckUPStr +="Antenatal Checkup booked ";
                if(this.admitPatientModel.antenatalHistoryObj.isthreeantenatalcheckupdone != null && this.admitPatientModel.antenatalHistoryObj.isthreeantenatalcheckupdone){
                  antenatalCheckUPStr += "with 3 antenatal checkup done ."
                } else if(this.admitPatientModel.antenatalHistoryObj.isthreeantenatalcheckupdone != null && !this.admitPatientModel.antenatalHistoryObj.isthreeantenatalcheckupdone){
                  antenatalCheckUPStr += "with 3 antenatal checkup not done ."
                }
                else{
                  antenatalCheckUPStr += ". ";
                }
              }else if(this.admitPatientModel.antenatalHistoryObj.antenatelCheckupStatus == 'unbooked'){
                antenatalCheckUPStr +="Antenatal Checkup unbooked. ";
              }
              if(antenatalCheckUPStr != ""){
                template += antenatalCheckUPStr;
              }
            }

            var firstscreeningDetailsStr ="";
            var secscreeningDetailsStr ="";
            var thirdscreeningDetailsStr ="";
            if(this.admitPatientModel.antenatalHistoryObj.isFirsttrimesterscreening || this.admitPatientModel.antenatalHistoryObj.isSecondtrimesterscreening || this.admitPatientModel.antenatalHistoryObj.thirdtrimesterscreening){
              if(this.admitPatientModel.antenatalHistoryObj.isFirsttrimesterscreening){
                if(this.admitPatientModel.antenatalHistoryObj.firsttrimesterscreening != null && this.admitPatientModel.antenatalHistoryObj.firsttrimesterscreening != ""){
                  firstscreeningDetailsStr += "1st trimester description ("  + this.admitPatientModel.antenatalHistoryObj.firsttrimesterscreening +")";
                }
              }
              if(this.admitPatientModel.antenatalHistoryObj.isSecondtrimesterscreening){
                if(this.admitPatientModel.antenatalHistoryObj.secondtrimesterscreening != null && this.admitPatientModel.antenatalHistoryObj.secondtrimesterscreening != ""){
                  if(firstscreeningDetailsStr != ""){
                    secscreeningDetailsStr += ", 2nd trimester description ("  + this.admitPatientModel.antenatalHistoryObj.secondtrimesterscreening + ")";
                  }
                  else{
                    secscreeningDetailsStr += "2nd trimester description ("  + this.admitPatientModel.antenatalHistoryObj.secondtrimesterscreening+")";
                  }
                }
              }
              if(this.admitPatientModel.antenatalHistoryObj.isThirdtrimesterscreening){
                if(this.admitPatientModel.antenatalHistoryObj.thirdtrimesterscreening != null && this.admitPatientModel.antenatalHistoryObj.thirdtrimesterscreening != ""){
                  if(firstscreeningDetailsStr != "" || secscreeningDetailsStr != ""){
                    thirdscreeningDetailsStr += ", 3rd trimester description ("  + this.admitPatientModel.antenatalHistoryObj.thirdtrimesterscreening + ")";
                  }
                  else{
                    thirdscreeningDetailsStr += "3rd trimester description ("  + this.admitPatientModel.antenatalHistoryObj.thirdtrimesterscreening + ")";
                  }
                }
              }
              if(firstscreeningDetailsStr != "" || secscreeningDetailsStr != "" || thirdscreeningDetailsStr != ""){
                template += firstscreeningDetailsStr + secscreeningDetailsStr + thirdscreeningDetailsStr +". ";
              }
            }


            var umbilicalDopplerStr = "";
            var dopplerTypeStr = "";

            if(this.admitPatientModel.antenatalHistoryObj.umbilicalDoppler != null){
              if(this.admitPatientModel.antenatalHistoryObj.umbilicalDoppler == 'abnormal'){
                umbilicalDopplerStr += "Abnormal Umbilical Doppler";
                if(this.admitPatientModel.antenatalHistoryObj.abnormalUmbilicalDopplerType != null){
                  if(this.admitPatientModel.antenatalHistoryObj.abnormalUmbilicalDopplerType == 'Increased'){
                    dopplerTypeStr += "increased SD ratio";
                  }
                  else if(this.admitPatientModel.antenatalHistoryObj.abnormalUmbilicalDopplerType == 'Absent'){
                    dopplerTypeStr += "absent EDF";
                  }
                  else if(this.admitPatientModel.antenatalHistoryObj.abnormalUmbilicalDopplerType == 'Reverse'){
                    dopplerTypeStr += "reverse EDF";
                  }
                  if(dopplerTypeStr != ""){
                    umbilicalDopplerStr +=  " with " +dopplerTypeStr;
                  }
                }
              }else if(this.admitPatientModel.antenatalHistoryObj.umbilicalDoppler == 'normal'){
                umbilicalDopplerStr += "Normal Umbilical Doppler";
              }
              if(umbilicalDopplerStr != ""){
                template+=umbilicalDopplerStr +". ";
              }
            }

            var hoStr = "";
            if(this.admitPatientModel.antenatalHistoryObj.historyOfSmoking != null || this.admitPatientModel.antenatalHistoryObj.historyOfAlcohol != null){
              if(this.admitPatientModel.antenatalHistoryObj.historyOfSmoking  && !this.admitPatientModel.antenatalHistoryObj.historyOfAlcohol){
                hoStr += "smoking";
              }
              else if(this.admitPatientModel.antenatalHistoryObj.historyOfAlcohol && !this.admitPatientModel.antenatalHistoryObj.historyOfSmoking){
                hoStr += "alcoholism";
              }
              else if(this.admitPatientModel.antenatalHistoryObj.historyOfSmoking && this.admitPatientModel.antenatalHistoryObj.historyOfAlcohol){
                hoStr += "smoking and alcoholism";
              }
              else{
                hoStr = "";
              }
            }else{
              hoStr = "";
            }
            if(hoStr != ""){
              template += "H/O " + hoStr +". ";
            }

            var illnessStr ="";
            var infectionHistoryStr= "";
            var pormStr ="";
            if(this.admitPatientModel.antenatalHistoryObj.hypertension == true){
              if(illnessStr == ""){
                illnessStr += "Hypertension";
              }else{
                illnessStr += ", Hypertension";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.gestationalHypertension == true){
              if(illnessStr == ""){
                illnessStr += "Gestational Hypertension";
              }else{
                illnessStr += ", Gestational Hypertension";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.diabetes == true){
              if(illnessStr == ""){
                illnessStr += "Diabetes";
              }else{
                illnessStr += ", Diabetes";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.gdm){
              if(illnessStr == ""){
                illnessStr += "GDM";
              }else{
                illnessStr += ", GDM";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.chronicKidneyDisease){
              if(illnessStr == ""){
                illnessStr += "Chronic kidney Disease";
              }else{
                illnessStr += ", Chronic kidney Disease";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.hypothyroidism){
              if(illnessStr == ""){
                illnessStr += "Hypothyroidism";
              }else{
                illnessStr += ", Hypothyroidism";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.hyperthyroidism){
              if(illnessStr == ""){
                illnessStr += "Hyperthyroidism";
              }else{
                illnessStr += ", Hyperthyroidism";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.fever){
              if(illnessStr == ""){
                illnessStr += "Fever";
              }else{
                illnessStr += ", Fever";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.uti){
              if(illnessStr == ""){
                illnessStr += "UTI";
              }else{
                illnessStr += ", UTI";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.historyOfInfections){
              if(illnessStr == ""){
                illnessStr += "I/U infections";
              }else{
                illnessStr += ", I/U infections";
              }
              if(this.admitPatientModel.antenatalHistoryObj.historyOfIvInfectionText != null && this.admitPatientModel.antenatalHistoryObj.historyOfIvInfectionText != ""){
                infectionHistoryStr +=" (" + this.admitPatientModel.antenatalHistoryObj.historyOfIvInfectionText +")";
                illnessStr += " " + infectionHistoryStr;
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.prom){
              if(illnessStr == ""){
                illnessStr += "PROM";
              }else{
                illnessStr += ", PROM";
              }
              if(this.admitPatientModel.antenatalHistoryObj.promText != null && this.admitPatientModel.antenatalHistoryObj.promText != ""){
                var promStr =" (" + this.admitPatientModel.antenatalHistoryObj.promText +")";
                illnessStr += " " + promStr;
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.pprom){
              if(illnessStr == ""){
                illnessStr += "PPROM";
              }else{
                illnessStr += ", PPROM";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.prematurity){
              if(illnessStr == ""){
                illnessStr += "Prematurity";
              }else{
                illnessStr += ", Prematurity";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.chorioamniotis){
              if(illnessStr == ""){
                illnessStr += "Chorioamnionitis";
              }else{
                illnessStr += ", Chorioamnionitis";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.oligohydraminos){
              if(illnessStr == ""){
                illnessStr += "Oligohydramnios";
              }else{
                illnessStr += ", Oligohydramnios";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.polyhydraminos){
              if(illnessStr == ""){
                illnessStr += "Polyhydramnios";
              }else{
                illnessStr += ", Polyhydramnios";
              }
            }
            if(this.admitPatientModel.antenatalHistoryObj.otherRiskFactors != null && this.admitPatientModel.antenatalHistoryObj.otherRiskFactors!= ""){
              if(illnessStr == ""){
                illnessStr += this.admitPatientModel.antenatalHistoryObj.otherRiskFactors;
              }else{
                illnessStr += ", " + this.admitPatientModel.antenatalHistoryObj.otherRiskFactors;
              }
            }

            if(illnessStr != ""){
              //	template += "Associated maternal risk factors are "+illnessStr + ".";

              if(illnessStr.includes(",")){
                template += "Associated antenatal risk factors are "+illnessStr +". ";
              }
              else{
                template += "Associated antenatal risk factor is "+illnessStr +". ";
              }
            }

            var tetanusStr = "";
            if(this.admitPatientModel.antenatalHistoryObj.tetanusToxoid != null && this.admitPatientModel.antenatalHistoryObj.tetanusToxoid){
              if(this.admitPatientModel.antenatalHistoryObj.tetanusToxoidDoses != null && this.admitPatientModel.antenatalHistoryObj.tetanusToxoidDoses != ""){
                tetanusStr += this.admitPatientModel.antenatalHistoryObj.tetanusToxoidDoses + " dose of tetanus toxoid given";
              }
              if(this.admitPatientModel.antenatalHistoryObj.isresuscitationMedication != null && this.admitPatientModel.antenatalHistoryObj.isresuscitationMedication){
                if(this.admitPatientModel.antenatalHistoryObj.otherMedications != null && this.admitPatientModel.antenatalHistoryObj.otherMedications != ""){
                  tetanusStr += " and " + this.admitPatientModel.antenatalHistoryObj.otherMedications;
                }
              }
              tetanusStr +=". ";
            } else if(this.admitPatientModel.antenatalHistoryObj.tetanusToxoid != null && this.admitPatientModel.antenatalHistoryObj.tetanusToxoid == false){
              tetanusStr += "Tetanus toxoid not given. ";
            }

            if(tetanusStr != ""){
              template += tetanusStr;
            }

            var dayBetaSterStr; var dayBetaSterSecStr;
            var dateBetaSterStr; var dateBetaSterSecStr;
            var yearBetaSterStr; var yearBetaSterSecStr;
            var monthBetaSterStr ; var monthBetaSterSecStr ;
            var dayDexaSterStr; var dayDexaSterSecStr;
            var dateDexaSterStr; var dateDexaSterSecStr;
            var yearDexaSterStr; var yearDexaSterSecStr;
            var monthDexaSterStr ; var monthDexaSterSecStr ;
            var steroidsStr = "";
            if(this.admitPatientModel.antenatalHistoryObj.isAntenatalSteroidGiven!= null && this.admitPatientModel.antenatalHistoryObj.isAntenatalSteroidGiven){

              if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname == 'Dexa'){
                if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose != ""){

                  if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose == 4) {
                    steroidsStr +=  "Complete dose of Dexa given";
                  } else {
                    steroidsStr +=  "Incomplete dose of Dexa given";
                  }

                  /*steroidsStr +=  this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose +  " dose of Dexa given";
                  if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != ""){
                    dateBetaSterStr = new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate);
                    dayBetaSterStr =dateBetaSterStr.getDate();
                    monthBetaSterStr =dateBetaSterStr.getMonth() +1;
                    yearBetaSterStr = dateBetaSterStr.getFullYear();
                    steroidsStr += " on " +dayBetaSterStr+ "-"+ monthBetaSterStr+"-" +yearBetaSterStr;
                    let dateToString = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate),"gmt","default");
                    steroidsStr += " " + dateToString.slice(11,14);
                    steroidsStr += ":" + dateToString.slice(15,17);
                    steroidsStr += " " + dateToString.slice(17,20);
                  }*/
                } else {
                  steroidsStr +=  "Unknown no. of Dexa dose given";
                }
              } else if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname == 'Beta'){
                if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose != ""){

                  if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose == 2) {
                    steroidsStr +=  "Complete dose of Beta given";
                  } else {
                    steroidsStr +=  "Incomplete dose of Beta given";
                  }

                  /*steroidsStr +=  this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.numberofdose +  " dose of Beta given";
                  if(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate != ""){
                    dateDexaSterStr = new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate);
                    dayDexaSterStr =dateDexaSterStr.getDate();
                    monthDexaSterStr =dateDexaSterStr.getMonth() +1;
                    yearDexaSterStr = dateDexaSterStr.getFullYear();
                    var dateConvert = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate),"gmt","default");
                    steroidsStr += " on " +dayDexaSterStr+ "-"+ monthDexaSterStr+"-" +yearDexaSterStr;
                    let dateToString = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.givendate),"gmt","default");
                    steroidsStr += " " + dateToString.slice(11,14);
                    steroidsStr += ":" + dateToString.slice(15,17);
                    steroidsStr += " " + dateToString.slice(17,20);
                  }*/
                } else {
                  steroidsStr +=  "Unknown no. of Beta dose given";
                }
              }

              if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname == 'Dexa'){
                if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname == this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname){
                  if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != ""){

                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose == 4) {
                      steroidsStr +=  " and complete dose repeated";
                    } else {
                      steroidsStr +=  " and incomplete dose repeated";
                    }

                    /*steroidsStr += " and repeat " + this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose +  " dose given";
                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ""){
                      dateDexaSterSecStr = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
                      dayDexaSterSecStr =dateDexaSterSecStr.getDate();
                      monthDexaSterSecStr =dateDexaSterSecStr.getMonth() +1;
                      yearDexaSterSecStr = dateDexaSterSecStr.getFullYear();
                      steroidsStr += " on " +dayDexaSterSecStr+ "-"+ monthDexaSterSecStr+"-" +yearDexaSterSecStr ;
                      let dateToString = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate),"gmt","default");
                      steroidsStr += " " + dateToString.slice(11,14);
                      steroidsStr += ":" + dateToString.slice(15,17);
                      steroidsStr += " " + dateToString.slice(17,20);
                    }*/
                  } else {
                    steroidsStr +=  " and unknown no. of dose repeated";
                  }
                } else {
                  if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != ""){

                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose == 4) {
                      steroidsStr +=  " and complete dose of Dexa repeated";
                    } else {
                      steroidsStr +=  " and incomplete dose of Dexa repeated";
                    }

                    /* steroidsStr += " and repeat " + this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose +  " dose of Dexa given";
                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ""){
                      dateBetaSterSecStr = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
                      dayBetaSterSecStr =dateBetaSterSecStr.getDate();
                      monthBetaSterSecStr =dateBetaSterSecStr.getMonth() +1;
                      yearBetaSterSecStr = dateBetaSterSecStr.getFullYear();
                      steroidsStr += " on " +dayBetaSterSecStr+ "-"+ monthBetaSterSecStr+"-" +yearBetaSterSecStr;
                      let dateToString = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate),"gmt","default");
                      steroidsStr += " " + dateToString.slice(11,14);
                      steroidsStr += ":" + dateToString.slice(15,17);
                      steroidsStr += " " + dateToString.slice(17,20);
                    }*/
                  } else {
                    steroidsStr +=  " and unknown no. of Dexa dose repeated";
                  }
                }
              } else if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname == 'Beta'){
                if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.steroidname == this.admitPatientModel.antenatalHistoryObj.firstSteroidDetail.steroidname){
                  if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != ""){

                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose == 2) {
                      steroidsStr +=  " and complete dose repeated";
                    } else {
                      steroidsStr +=  " and incomplete dose repeated";
                    }

                    /* steroidsStr += " and repeat " + this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose +  " dose given";
                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ""){
                      dateBetaSterSecStr = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
                      dayBetaSterSecStr =dateBetaSterSecStr.getDate();
                      monthBetaSterSecStr =dateBetaSterSecStr.getMonth() +1;
                      yearBetaSterSecStr = dateBetaSterSecStr.getFullYear();
                      steroidsStr += " on " +dayBetaSterSecStr+ "-"+ monthBetaSterSecStr+"-" +yearBetaSterSecStr ;
                      let dateToString = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate),"gmt","default");
                      steroidsStr += " " + dateToString.slice(11,14);
                      steroidsStr += ":" + dateToString.slice(15,17);
                      steroidsStr += " " + dateToString.slice(17,20);
                    } */
                  } else {
                    steroidsStr +=  " and unknown no. of dose repeated";
                  }
                } else {
                  if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose != ""){

                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose == 2) {
                      steroidsStr +=  " and complete dose of Beta repeated";
                    } else {
                      steroidsStr +=  " and incomplete dose of Beta repeated";
                    }

                    /* steroidsStr += " and repeat " + this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.numberofdose +  " dose of Beta given";
                    if(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != null && this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate != ""){
                      dateDexaSterSecStr = new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate);
                      dayDexaSterSecStr =dateDexaSterSecStr.getDate();
                      monthDexaSterSecStr =dateDexaSterSecStr.getMonth() +1;
                      yearDexaSterSecStr = dateDexaSterSecStr.getFullYear();
                      steroidsStr += " on " +dayDexaSterSecStr+ "-"+ monthDexaSterSecStr+"-" +yearDexaSterSecStr ;
                      let dateToString = this.dateformatter(new Date(this.admitPatientModel.antenatalHistoryObj.secondSteroidDetail.givendate),"gmt","default");
                      steroidsStr += " " + dateToString.slice(11,14);
                      steroidsStr += ":" + dateToString.slice(15,17);
                      steroidsStr += " " + dateToString.slice(17,20);
                    }*/
                  } else {
                    steroidsStr +=  " and unknown no. of Beta dose repeated";
                  }
                }
              }
            } else if(this.admitPatientModel.antenatalHistoryObj.isAntenatalSteroidGiven!= null && !this.admitPatientModel.antenatalHistoryObj.isAntenatalSteroidGiven){
              steroidsStr += "Antenatal steroid not given";
            }

            if(steroidsStr != ""){
              template += steroidsStr + ". ";
            }

            if(this.admitPatientModel.antenatalHistoryObj.anitd) {
              template += "Anti D given. ";
            }

if(this.admitPatientModel.antenatalHistoryObj.motherBloodGroupAbo != null && this.admitPatientModel.antenatalHistoryObj.motherBloodGroupAbo != "") {
  template += "Mother's blood group is " +this.admitPatientModel.antenatalHistoryObj.motherBloodGroupAbo;
  if(this.admitPatientModel.antenatalHistoryObj.motherBloodGroupRh != null && this.admitPatientModel.antenatalHistoryObj.motherBloodGroupRh == "negative"){
    template += "- ve. ";
    if(this.admitPatientModel.antenatalHistoryObj.ict != null && this.admitPatientModel.antenatalHistoryObj.ict){
      template += "ICT positive. "
    }else if(this.admitPatientModel.antenatalHistoryObj.ict != null && !this.admitPatientModel.antenatalHistoryObj.ict){
      template += "ICT negative. "
    }
  }else if(this.admitPatientModel.antenatalHistoryObj.motherBloodGroupRh != null && this.admitPatientModel.antenatalHistoryObj.motherBloodGroupRh == "positive"){
    template += "+ ve. ";
  }else{
    template += ". ";
  }
}

if(this.admitPatientModel.antenatalHistoryObj.torch != null && this.admitPatientModel.antenatalHistoryObj.torch != ""){
  if(this.admitPatientModel.antenatalHistoryObj.torch){
    template += "TORCH is positive";
    if(this.admitPatientModel.antenatalHistoryObj.torchText!= null && this.admitPatientModel.antenatalHistoryObj.torchText != ""){
      template += " (" + this.admitPatientModel.antenatalHistoryObj.torchText + ")";
    }
    template += ". ";
  }
}else if(this.admitPatientModel.antenatalHistoryObj.torch!= null && !this.admitPatientModel.antenatalHistoryObj.torch ){
  template += "TORCH is negative";
  template += ". ";
}


var investigationStr = "";
if(this.admitPatientModel.antenatalHistoryObj.ishiv){
  investigationStr += "HIV";
  if(this.admitPatientModel.antenatalHistoryObj.hivType != null && this.admitPatientModel.antenatalHistoryObj.hivType == 'positive'){
    investigationStr += " (positive)";
  } else if(this.admitPatientModel.antenatalHistoryObj.hivType != null && this.admitPatientModel.antenatalHistoryObj.hivType == 'negative'){
    investigationStr += " (negative)";
  }
}
if(this.admitPatientModel.antenatalHistoryObj.ishepb){
  if(investigationStr != ""){
    investigationStr += ", Hep B";
    if(this.admitPatientModel.antenatalHistoryObj.hepbType != null && this.admitPatientModel.antenatalHistoryObj.hepbType == 'HBsAg'){
      investigationStr += " (positive)";
    } else if(this.admitPatientModel.antenatalHistoryObj.hepbType != null && this.admitPatientModel.antenatalHistoryObj.hepbType == 'HBeAg'){
      investigationStr += " (negative)";
    }
  }else {
    investigationStr += "Hep B";
    if(this.admitPatientModel.antenatalHistoryObj.hepbType != null && this.admitPatientModel.antenatalHistoryObj.hepbType == 'HBsAg'){
      investigationStr += " (positive)";
    } else if(this.admitPatientModel.antenatalHistoryObj.hepbType != null && this.admitPatientModel.antenatalHistoryObj.hepbType == 'HBeAg'){
      investigationStr += " (negative)";
    }
  }
}
if(this.admitPatientModel.antenatalHistoryObj.vdrl){
  if(investigationStr !=""){
    investigationStr += ", VDRL";
    if(this.admitPatientModel.antenatalHistoryObj.vdrlType != null && this.admitPatientModel.antenatalHistoryObj.vdrlType == 'Reactive'){
      investigationStr += " (reactive)";
    } else if(this.admitPatientModel.antenatalHistoryObj.vdrlType != null && this.admitPatientModel.antenatalHistoryObj.vdrlType == 'Non Reactive'){
      investigationStr += " (non-reactive)";
    }
  }else{
    investigationStr += "VDRL";
    if(this.admitPatientModel.antenatalHistoryObj.vdrlType != null && this.admitPatientModel.antenatalHistoryObj.vdrlType == 'Reactive'){
      investigationStr += " (reactive)";
    } else if(this.admitPatientModel.antenatalHistoryObj.vdrlType != null && this.admitPatientModel.antenatalHistoryObj.vdrlType == 'Non Reactive'){
      investigationStr += " (non-reactive)";
    }
  }
}
if(this.admitPatientModel.antenatalHistoryObj.isOtherMeternalInvestigations){
  if(this.admitPatientModel.antenatalHistoryObj.otherMeternalInvestigations != null && this.admitPatientModel.antenatalHistoryObj.otherMeternalInvestigations != ""){
    if(investigationStr !=""){
      investigationStr += ", " + this.admitPatientModel.antenatalHistoryObj.otherMeternalInvestigations;
    }else{
      investigationStr += this.admitPatientModel.antenatalHistoryObj.otherMeternalInvestigations;
    }
  }
}
if(investigationStr !=""){
  template += "Investigations done are "+investigationStr + ".";
}

if(this.admitPatientModel.antenatalHistoryObj.isLabour != null && this.admitPatientModel.antenatalHistoryObj.isLabour != undefined && this.admitPatientModel.antenatalHistoryObj.isLabour != ""){
  if(this.admitPatientModel.antenatalHistoryObj.isLabour==true){
    if(this.admitPatientModel.antenatalHistoryObj.labourType != null && this.admitPatientModel.antenatalHistoryObj.labourType != undefined && this.admitPatientModel.antenatalHistoryObj.labourType != ""){
      if(this.admitPatientModel.antenatalHistoryObj.labourType =='spontaneous'){
        template += "Spontaneous labour. ";
      }else if(this.admitPatientModel.antenatalHistoryObj.labourType =='induced'){
        template += "Induced labour. ";
      }

    }
    if(this.admitPatientModel.antenatalHistoryObj.isAugmented != null && this.admitPatientModel.antenatalHistoryObj.isAugmented){
      template += "Augmentation done. ";
    }else if(this.admitPatientModel.antenatalHistoryObj.isAugmented != null && !this.admitPatientModel.antenatalHistoryObj.isAugmented){
      template += "Augmentation not done. ";
    }
  }
}


if(this.admitPatientModel.antenatalHistoryObj.presentationType != null && this.admitPatientModel.antenatalHistoryObj.presentationType == 'Vertex'){
  template += "Presentation vertex. ";
}else if(this.admitPatientModel.antenatalHistoryObj.presentationType != null && this.admitPatientModel.antenatalHistoryObj.presentationType == 'Breech'){
  template += "Presentation breech. ";
}else if(this.admitPatientModel.antenatalHistoryObj.presentationType != null && this.admitPatientModel.antenatalHistoryObj.presentationType == 'Transverse'){
  template += "Presentation transverse. ";
}else if(this.admitPatientModel.antenatalHistoryObj.presentationType != null && this.admitPatientModel.antenatalHistoryObj.presentationType == 'Other'){
  if(this.admitPatientModel.antenatalHistoryObj.otherPresentationType != null && this.admitPatientModel.antenatalHistoryObj.otherPresentationType != ""){
    template += "Presentation " + this.admitPatientModel.antenatalHistoryObj.otherPresentationType + ". ";
  }
}

if(this.admitPatientModel.antenatalHistoryObj.modeOfDelivery != null && this.admitPatientModel.antenatalHistoryObj.modeOfDelivery == 'NVD'){
  template += "Mode of delivery NVD. ";
}else if(this.admitPatientModel.antenatalHistoryObj.modeOfDelivery != null && this.admitPatientModel.antenatalHistoryObj.modeOfDelivery == 'LSCS'){
  template += "Mode of delivery LSCS";
  if(this.admitPatientModel.antenatalHistoryObj.isLscsElective != null && this.admitPatientModel.antenatalHistoryObj.isLscsElective == true){
    template += " (Elective)";
  }else if(this.admitPatientModel.antenatalHistoryObj.isLscsElective != null && this.admitPatientModel.antenatalHistoryObj.isLscsElective ==false){
    template += " (Emergency)";
  }

  if(this.admitPatientModel.antenatalHistoryObj.lscsIndication != null && this.admitPatientModel.antenatalHistoryObj.lscsIndication != '') {
    template += ", Indication was " + this.admitPatientModel.antenatalHistoryObj.lscsIndication;
  }
  template += ". ";

  if(this.admitPatientModel.antenatalHistoryObj.anaesthesiaType !=null && this.admitPatientModel.antenatalHistoryObj.anaesthesiaType != undefined && this.admitPatientModel.antenatalHistoryObj.anaesthesiaType != ''){
    if(this.admitPatientModel.antenatalHistoryObj.anaesthesiaType != 'Other'){
      template += "Type of Anaesthesia is " + this.admitPatientModel.antenatalHistoryObj.anaesthesiaType;
    }else{
      if(this.admitPatientModel.antenatalHistoryObj.modeOfDeliveryOtherText !=null && this.admitPatientModel.antenatalHistoryObj.modeOfDeliveryOtherText != undefined && this.admitPatientModel.antenatalHistoryObj.modeOfDeliveryOtherText != ''){
        template += "Type of Anaesthesia is " + this.admitPatientModel.antenatalHistoryObj.modeOfDeliveryOtherText;
      }

    }
  }
}else if(this.admitPatientModel.antenatalHistoryObj.modeOfDelivery != null && this.admitPatientModel.antenatalHistoryObj.modeOfDelivery == 'Forceps'){
  template += "Mode of delivery Forceps. ";
}else if(this.admitPatientModel.antenatalHistoryObj.modeOfDelivery != null && this.admitPatientModel.antenatalHistoryObj.modeOfDelivery == 'Vaccum'){
  template += "Mode of delivery Vaccum. ";
}
this.admitPatientModel.admissionNotesObj.antenatal_history = template;
this.populateSystemicDiagnosis();
}


//birth to nicu
refreshValuesRespiratory = function() {
console.log("functionvalues ");
console.log(this.admitPatientModel.respSupportObj);

// below 6 fields are text, so blank them
this.admitPatientModel.respSupportObj.rsAmplitude = null;
this.admitPatientModel.respSupportObj.rsEt = null;
this.admitPatientModel.respSupportObj.rsFrequency = null;
this.admitPatientModel.respSupportObj.rsFlowRate = null;
this.admitPatientModel.respSupportObj.rsMv = null;
this.admitPatientModel.respSupportObj.rsTv = null;

this.admitPatientModel.respSupportObj.rsRate = null;
this.admitPatientModel.respSupportObj.rsBackuprate = null;
this.admitPatientModel.respSupportObj.rsTubeSize = null;
this.admitPatientModel.respSupportObj.rsFixation = null;

switch(this.admitPatientModel.respSupportObj.rsVentType)
{
  case 'Low Flow O2':
  this.admitPatientModel.respSupportObj.rsFio2 = '21';
  this.admitPatientModel.respSupportObj.rsIt = null;
  this.admitPatientModel.respSupportObj.rsMap = null;
  this.admitPatientModel.respSupportObj.rsMechVentType = null;
  this.admitPatientModel.respSupportObj.rsPeep = null;
  this.admitPatientModel.respSupportObj.rsPip = null;
  this.admitPatientModel.respSupportObj.rsIsEndotracheal = null;

  break;
  case 'High Flow O2':
  this.admitPatientModel.respSupportObj.rsFio2 = '21';
  this.admitPatientModel.respSupportObj.rsIt = null;
  this.admitPatientModel.respSupportObj.rsMap = null;
  this.admitPatientModel.respSupportObj.rsMechVentType = null;
  this.admitPatientModel.respSupportObj.rsPeep = null;
  this.admitPatientModel.respSupportObj.rsPip = null;
  this.admitPatientModel.respSupportObj.rsIsEndotracheal = null;
  break;
  case 'CPAP':
  this.admitPatientModel.respSupportObj.rsFio2 = '21';
  this.admitPatientModel.respSupportObj.rsIt = null;
  this.admitPatientModel.respSupportObj.rsMap = '3';
  this.admitPatientModel.respSupportObj.rsMechVentType = null;
  this.admitPatientModel.respSupportObj.rsPeep = null;
  this.admitPatientModel.respSupportObj.rsPip = null;
  this.admitPatientModel.respSupportObj.rsIsEndotracheal = null;

  break;
  case 'Mechanical Ventilation':
  if(this.admitPatientModel.respSupportObj.rsMechVentType=='SIMV')
  {
    this.admitPatientModel.respSupportObj.rsFio2 = '21';
    this.admitPatientModel.respSupportObj.rsPip = '10';
    this.admitPatientModel.respSupportObj.rsPeep = '3';
    this.admitPatientModel.respSupportObj.rsIt = '0.10';
    this.admitPatientModel.respSupportObj.rsMap = null;
  }
  else if(this.admitPatientModel.respSupportObj.rsMechVentType=='PSV')
  {
    this.admitPatientModel.respSupportObj.rsFio2 = '21';
    this.admitPatientModel.respSupportObj.rsPip = '10';
    this.admitPatientModel.respSupportObj.rsPeep = '3';
    this.admitPatientModel.respSupportObj.rsIt = null;
    this.admitPatientModel.respSupportObj.rsMap = null;
  }
  else
  {
    this.admitPatientModel.respSupportObj.rsFio2 = null;
    this.admitPatientModel.respSupportObj.rsPip = null;
    this.admitPatientModel.respSupportObj.rsPeep = null;
    this.admitPatientModel.respSupportObj.rsIt = null;
    this.admitPatientModel.respSupportObj.rsMap = null;
  }

  break;
  case 'HFO':
  this.admitPatientModel.respSupportObj.rsFio2 = '21';
  this.admitPatientModel.respSupportObj.rsIt = null;
  this.admitPatientModel.respSupportObj.rsMap = '3';
  this.admitPatientModel.respSupportObj.rsMechVentType = null;
  this.admitPatientModel.respSupportObj.rsPeep = null;
  this.admitPatientModel.respSupportObj.rsPip = null;
  this.admitPatientModel.respSupportObj.rsIsEndotracheal = null;
  break;
}
console.log(this.admitPatientModel.respSupportObj);
this.populateBirthToNicuStr();
}

refreshValuesTransportation = function() {
  // below 6 fields are text, so blank them
  this.admitPatientModel.birthToNicuObj.transportEt = null;
  this.admitPatientModel.birthToNicuObj.transport_flowrate = null;
  this.admitPatientModel.birthToNicuObj.transportMv = null;
  this.admitPatientModel.birthToNicuObj.transportTv = null;
  this.admitPatientModel.birthToNicuObj.transportBreathrate = null;
  this.admitPatientModel.birthToNicuObj.transportBackuprate = null;

  switch(this.admitPatientModel.birthToNicuObj.transportedIn)
  {
    case 'room air':
    this.admitPatientModel.birthToNicuObj.transportFio2 = null;
    this.admitPatientModel.birthToNicuObj.transportIt = null;
    this.admitPatientModel.birthToNicuObj.transportMap = null;
    this.admitPatientModel.birthToNicuObj.transportMechVentType = null;
    this.admitPatientModel.birthToNicuObj.transportPeep = null;
    this.admitPatientModel.birthToNicuObj.transportPip = null;
    break;
    case 'nasal CPAP':
    this.admitPatientModel.birthToNicuObj.transportFio2 = '21';
    this.admitPatientModel.birthToNicuObj.transportIt = null;
    this.admitPatientModel.birthToNicuObj.transportMap = '3';
    this.admitPatientModel.birthToNicuObj.transportMechVentType = null;
    this.admitPatientModel.birthToNicuObj.transportPeep = null;
    this.admitPatientModel.birthToNicuObj.transportPip = null;
    break;
    case 'ventilator':
    if(this.admitPatientModel.birthToNicuObj.transportMechVentType=='SIMV'){
      this.admitPatientModel.birthToNicuObj.transportFio2 = '21';
      this.admitPatientModel.birthToNicuObj.transportPip = '10';
      this.admitPatientModel.birthToNicuObj.transportPeep = '3';
      this.admitPatientModel.birthToNicuObj.transportIt = '0.10';
      this.admitPatientModel.birthToNicuObj.transportMap = null;
    } else if(this.admitPatientModel.birthToNicuObj.transportMechVentType=='PSV'){
      this.admitPatientModel.birthToNicuObj.transportFio2 = '21';
      this.admitPatientModel.birthToNicuObj.transportPip = '10';
      this.admitPatientModel.birthToNicuObj.transportPeep = '3';
      this.admitPatientModel.birthToNicuObj.transportIt = null;
      this.admitPatientModel.birthToNicuObj.transportMap = null;
    } else {
      this.admitPatientModel.birthToNicuObj.transportFio2 = null;
      this.admitPatientModel.birthToNicuObj.transportPip = null;
      this.admitPatientModel.birthToNicuObj.transportPeep = null;
      this.admitPatientModel.birthToNicuObj.transportIt = null;
      this.admitPatientModel.birthToNicuObj.transportMap = null;
    }
    break;
  }
  console.log(this.admitPatientModel.birthToNicuObj);
  this.populateBirthToNicuStr();
}

addMedicine = function(){
  var medEmptyObj = Object.assign({},this.admitPatientModel.medicineEmptyObj);
  this.admitPatientModel.medicineList.push(medEmptyObj);
}

removeLastMedicine = function() {
  this.admitPatientModel.medicineList.splice(-1,1);
}

showLevene = function() {
  $("#bindScorePopup").addClass("showing");
  $("#scoresOverlay").addClass("show");
  this.getBindStructure();
};
closeBind = function(){
  if(this.admitPatientModel.leveneFlag == false){
    this.admitPatientModel.leveneObj.levenescore = null;
    this.admitPatientModel.leveneObj.suckingRespirationscore = null;
    this.admitPatientModel.leveneObj.seizuresscore = null;
    this.admitPatientModel.leveneObj.tonescore = null;
    this.admitPatientModel.leveneObj.consciousnessscore = null;
    $("#leveneNotValid").css("display","none");
  }
  else{
    this.admitPatientModel.leveneFlag = true;
    $("#leveneNotValid").css("display","none");
  }
  $("#bindScorePopup").removeClass("showing");
  $("#scoresOverlay").removeClass("show");

};

//levene score save
closeModalLeveneOnSave = function(){
if(this.checkingLevene()){
  $("#leveneNotValid").css("display","none");
  $("#bindScorePopup").removeClass("showing");
  $("#scoresOverlay").removeClass("show");
  $("#displayLevene").css("display","table-cell");
  this.admitPatientModel.leveneFlag = true;
  this.populateBirthToNicuStr();
}
else{
  $("#leveneNotValid").css("display","inline-block");
}
}

totalLeveneValue = function(leveneValue){
  var totalCons = 0, totalTone = 0, totalSeizure = 0, totalSucking =0;
  var leveneValueCalculated = 0;
  leveneValueCalculated = Math.max(this.admitPatientModel.leveneObj.consciousnessscore, this.admitPatientModel.leveneObj.tonescore, this.admitPatientModel.leveneObj.seizuresscore, this.admitPatientModel.leveneObj.suckingRespirationscore);
  if(leveneValueCalculated == 1){
    this.admitPatientModel.leveneObj.levenescore = "mild";
  } else if(leveneValueCalculated == 2){
    this.admitPatientModel.leveneObj.levenescore = "moderate";
  } else if(leveneValueCalculated == 3){
    this.admitPatientModel.leveneObj.levenescore = "severe";
  }

}
// end of levene calculation
checkingLevene = function(){
console.log("checking downes");
var leveneScoreValid = true;
if(this.admitPatientModel.leveneObj.consciousnessscore == null || this.admitPatientModel.leveneObj.consciousnessscore == undefined){
  leveneScoreValid = false;
}
else if(this.admitPatientModel.leveneObj.tonescore == null || this.admitPatientModel.leveneObj.tonescore == undefined){
  leveneScoreValid = false;
}
else if(this.admitPatientModel.leveneObj.seizuresscore == null || this.admitPatientModel.leveneObj.seizuresscore == undefined){
  leveneScoreValid = false;
}
else if(this.admitPatientModel.leveneObj.suckingRespirationscore == null || this.admitPatientModel.leveneObj.suckingRespirationscore == undefined){
  leveneScoreValid = false;
}
return leveneScoreValid;
}

populateBirthToNicuStr = function(value){

  if(this.admitPatientModel.respSupportObj.rsIt != null && this.admitPatientModel.respSupportObj.rsIt != undefined){
    if(value == 'et'){
      this.admitPatientModel.respSupportObj.rsRate = (this.admitPatientModel.respSupportObj.rsEt*1 + this.admitPatientModel.respSupportObj.rsIt*1)*60;
			this.admitPatientModel.respSupportObj.rsRate = this.admitPatientModel.respSupportObj.rsRate.toPrecision(4);
    }
    if(value == 'breath'){
      this.admitPatientModel.respSupportObj.rsEt = (this.admitPatientModel.respSupportObj.rsRate/60 - this.admitPatientModel.respSupportObj.rsIt*1);
			this.admitPatientModel.respSupportObj.rsEt = this.admitPatientModel.respSupportObj.rsEt.toPrecision(2);
    }
  }

  if(this.admitPatientModel.birthToNicuObj.transportIt != null && this.admitPatientModel.birthToNicuObj.transportIt != undefined){
    if(value == 'et'){
      this.admitPatientModel.birthToNicuObj.transportBreathrate = (this.admitPatientModel.birthToNicuObj.transportEt*1 + this.admitPatientModel.birthToNicuObj.transportIt*1)*60;
			this.admitPatientModel.birthToNicuObj.transportBreathrate = this.admitPatientModel.birthToNicuObj.transportBreathrate.toPrecision(4);
    }
    if(value == 'breath'){
      this.admitPatientModel.birthToNicuObj.transportEt = (this.admitPatientModel.birthToNicuObj.transportBreathrate/60 - this.admitPatientModel.birthToNicuObj.transportIt*1);
			this.admitPatientModel.birthToNicuObj.transportEt = this.admitPatientModel.birthToNicuObj.transportEt.toPrecision(2);
    }
  }




  console.log(this.admitPatientModel.birthToNicuObj);
  var template="";
  this.admitPatientModel.admissionNotesObj.birth_to_inicu ="";

  var transportedTypeStr = "";
  if(this.admitPatientModel.birthToNicuObj.transportedType != null){
    if(this.admitPatientModel.birthToNicuObj.transportedType == 'cot'){
      transportedTypeStr += "in Cot";
    }else
    if(this.admitPatientModel.birthToNicuObj.transportedType == 'transport incubator'){
      transportedTypeStr += "on Incubator";
    }
    template += "Baby transported to NICU " + transportedTypeStr;
  }

  var transportedInStr ="";
  var transpotedParameter = "";
  if(this.admitPatientModel.birthToNicuObj.transportedIn == 'room air'){
    transportedInStr += "room air";
  }else if(this.admitPatientModel.birthToNicuObj.transportedIn == 'nasal CPAP'){
    transportedInStr += "CPAP";
    if(this.admitPatientModel.birthToNicuObj.transportMap != null && this.admitPatientModel.birthToNicuObj.transportMap != ""){
      transpotedParameter += " MAP/PEEP " + this.admitPatientModel.birthToNicuObj.transportMap +" cmH2O";
    }
    if(this.admitPatientModel.birthToNicuObj.transportFio2 != null && this.admitPatientModel.birthToNicuObj.transportFio2 != ""){
      if(transpotedParameter != ""){
        transpotedParameter += ", FiO2 " + this.admitPatientModel.birthToNicuObj.transportFio2 + " %";
      }
      else{
        transpotedParameter += " FiO2 " + this.admitPatientModel.birthToNicuObj.transportFio2 + " %";
      }
    }
    if(transpotedParameter != ""){
      transportedInStr += " (" + transpotedParameter +")";
    }
  }else if(this.admitPatientModel.birthToNicuObj.transportedIn == 'ventilator'){
    transportedInStr += "Ventilators";
    if(this.admitPatientModel.birthToNicuObj.transportMechVentType != null){
      if(this.admitPatientModel.birthToNicuObj.transportMechVentType =='SIMV'){
        console.log("SIMV");
        transportedInStr += " (Mode SIMV";
        transpotedParameter="";
        if(this.admitPatientModel.birthToNicuObj.transportFlowrate != null && this.admitPatientModel.birthToNicuObj.transportFlowrate != ""){
          transpotedParameter += " Flow Rate " + this.admitPatientModel.birthToNicuObj.transportFlowrate + " Litres/min";
        }
        if(this.admitPatientModel.birthToNicuObj.transportPip != null && this.admitPatientModel.birthToNicuObj.transportPip != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", PIP " + this.admitPatientModel.birthToNicuObj.transportPip +" cmH2O";
          }
          else{
            transpotedParameter += " PIP " + this.admitPatientModel.birthToNicuObj.transportPip +" cmH2O";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportPeep != null && this.admitPatientModel.birthToNicuObj.transportPeep != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", PEEP " + this.admitPatientModel.birthToNicuObj.transportPeep +" cmH2O";
          }
          else{
            transpotedParameter += " PEEP " + this.admitPatientModel.birthToNicuObj.transportPeep +" cmH2O";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportIt != null && this.admitPatientModel.birthToNicuObj.transportIt != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", IT " + this.admitPatientModel.birthToNicuObj.transportIt +" secs";
          }
          else{
            transpotedParameter += " IT " + this.admitPatientModel.birthToNicuObj.transportIt +" secs";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportEt != null && this.admitPatientModel.birthToNicuObj.transportEt != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", ET " + this.admitPatientModel.birthToNicuObj.transportEt +" secs";
          }
          else{
            transpotedParameter += " ET " + this.admitPatientModel.birthToNicuObj.transportEt +" secs";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportFio2 != null && this.admitPatientModel.birthToNicuObj.transportFio2 != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", FiO2 " + this.admitPatientModel.birthToNicuObj.transportFio2 + " %";
          }
          else{
            transpotedParameter += " FiO2 " + this.admitPatientModel.birthToNicuObj.transportFio2 + " %";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportTv != null && this.admitPatientModel.birthToNicuObj.transportTv != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", TV " + this.admitPatientModel.birthToNicuObj.transportTv + " mL/kg";
          }
          else{
            transpotedParameter += " TV " + this.admitPatientModel.birthToNicuObj.transportTv + " mL/kg";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportMv != null && this.admitPatientModel.birthToNicuObj.transportMv != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", MV " + this.admitPatientModel.birthToNicuObj.transportMv;
          }
          else{
            transpotedParameter += " MV " + this.admitPatientModel.birthToNicuObj.transportMv;
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportBreathrate != null && this.admitPatientModel.birthToNicuObj.transportBreathrate != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", Breath Rate " + this.admitPatientModel.birthToNicuObj.transportBreathrate + " Breaths/min";
          }
          else{
            transpotedParameter += " Breath Rate " + this.admitPatientModel.birthToNicuObj.transportBreathrate + "Breaths/min";
          }
        }
        transportedInStr += transpotedParameter + ")";
      }else if(this.admitPatientModel.birthToNicuObj.transportMechVentType =='PSV'){
        transportedInStr += " (Mode PSV";
        transpotedParameter="";
        if(this.admitPatientModel.birthToNicuObj.transportBackuprate != null && this.admitPatientModel.birthToNicuObj.transportBackuprate != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", Backup Rate " + this.admitPatientModel.birthToNicuObj.transportBackuprate + " Breaths/min";
          }
          else{
            transpotedParameter += " Backup Rate " + this.admitPatientModel.birthToNicuObj.transportBackuprate + " Breaths/min";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportTv != null && this.admitPatientModel.birthToNicuObj.transportTv != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", TV " + this.admitPatientModel.birthToNicuObj.transportTv + " mL/kg";
          }
          else{
            transpotedParameter += " TV " + this.admitPatientModel.birthToNicuObj.transportTv + " mL/kg";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportPip != null && this.admitPatientModel.birthToNicuObj.transportPip != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", PIP " + this.admitPatientModel.birthToNicuObj.transportPip +" cmH2O";
          }
          else{
            transpotedParameter += " PIP " + this.admitPatientModel.birthToNicuObj.transportPip +" cmH2O";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportPeep != null && this.admitPatientModel.birthToNicuObj.transportPeep != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", PEEP " + this.admitPatientModel.birthToNicuObj.transportPeep +" cmH2O";
          }
          else{
            transpotedParameter += " PEEP " + this.admitPatientModel.birthToNicuObj.transportPeep +" cmH2O";
          }
        }
        if(this.admitPatientModel.birthToNicuObj.transportFio2 != null && this.admitPatientModel.birthToNicuObj.transportFio2 != ""){
          if(transpotedParameter != ""){
            transpotedParameter += ", FiO2 " + this.admitPatientModel.birthToNicuObj.transportFio2 +" %";
          }
          else{
            transpotedParameter += " FiO2 " + this.admitPatientModel.birthToNicuObj.transportFio2 +" %";
          }
        }
        transportedInStr += transpotedParameter + ")";
      }
    }
  }
  //end of ventilators
  if(transportedInStr !=""){
  template +=" on " + transportedInStr + ". ";
}
else if(template != "" && transportedInStr == ""){
  template +=". ";
}

if(this.admitPatientModel.birthToNicuObj.criedImmediately == true || this.admitPatientModel.birthToNicuObj.criedImmediately == 'true' && this.admitPatientModel.birthToNicuObj.criedImmediately !=null){
  template +="Cried immediately after birth. ";
}else if(this.admitPatientModel.birthToNicuObj.criedImmediately == false || this.admitPatientModel.birthToNicuObj.criedImmediately == 'false' && this.admitPatientModel.birthToNicuObj.criedImmediately !=null){
  template +="Not cried immediately after birth. ";
}

var resuscitationStr = "";
if(this.admitPatientModel.birthToNicuObj.resuscitation == true || this.admitPatientModel.birthToNicuObj.resuscitation =='true' && this.admitPatientModel.birthToNicuObj.resuscitation !=null){
  resuscitationStr +="Resuscitation details";
  if(this.admitPatientModel.birthToNicuObj.initialStep != null && this.admitPatientModel.birthToNicuObj.initialStep || this.admitPatientModel.birthToNicuObj.initialStep == 'true'){
    resuscitationStr +=", initial steps performed";
  }
  if(this.admitPatientModel.birthToNicuObj.resuscitationO2 != null && this.admitPatientModel.birthToNicuObj.resuscitationO2 || this.admitPatientModel.birthToNicuObj.resuscitationO2 == 'true'){
    resuscitationStr +=", oxygen";
    if(this.admitPatientModel.birthToNicuObj.resuscitationO2Duration != "" && this.admitPatientModel.birthToNicuObj.resuscitationO2Duration != null){
      resuscitationStr += " for " + this.admitPatientModel.birthToNicuObj.resuscitationO2Duration;
      if(this.admitPatientModel.birthToNicuObj.durationO2Time != null){
        resuscitationStr +=	this.admitPatientModel.birthToNicuObj.durationO2Time;
      }
    }
  }
  if(this.admitPatientModel.birthToNicuObj.resuscitationPpv != null && this.admitPatientModel.birthToNicuObj.resuscitationPpv){
    resuscitationStr +=", PPV";
    if(this.admitPatientModel.birthToNicuObj.resuscitationPpvDuration != "" && this.admitPatientModel.birthToNicuObj.resuscitationPpvDuration != null){
      resuscitationStr += " for " + this.admitPatientModel.birthToNicuObj.resuscitationPpvDuration;
      if(this.admitPatientModel.birthToNicuObj.ppvTime != null){
        resuscitationStr +=	this.admitPatientModel.birthToNicuObj.ppvTime;
      }
    }
  }
  if(this.admitPatientModel.birthToNicuObj.resuscitationChesttubeCompression != null && this.admitPatientModel.birthToNicuObj.resuscitationChesttubeCompression){
    resuscitationStr +=", chest compression";
    if(this.admitPatientModel.birthToNicuObj.resuscitationChesttubeCompressionDuration != "" && this.admitPatientModel.birthToNicuObj.resuscitationChesttubeCompressionDuration != null){
      resuscitationStr += " for " + this.admitPatientModel.birthToNicuObj.resuscitationChesttubeCompressionDuration;
      if(this.admitPatientModel.birthToNicuObj.chestCompTime != null){
        resuscitationStr +=	this.admitPatientModel.birthToNicuObj.chestCompTime;
      }
    }
  }
  if(this.admitPatientModel.birthToNicuObj.resuscitationMedication != null && this.admitPatientModel.birthToNicuObj.resuscitationMedication){
    resuscitationStr +=", medication";
    if(this.admitPatientModel.birthToNicuObj.resuscitationMedicationDetails != null && this.admitPatientModel.birthToNicuObj.resuscitationMedicationDetails != ""){
      resuscitationStr +=" (" +this.admitPatientModel.birthToNicuObj.resuscitationMedicationDetails + ")";
    }
  }
}
if(resuscitationStr != ""){
  template +=resuscitationStr +" given. ";
}

var apgarStr="";
if(this.admitPatientModel.birthToNicuObj.apgarOnemin != null && this.admitPatientModel.birthToNicuObj.apgarOnemin != ""){
  apgarStr +="1min " + "(" +this.admitPatientModel.birthToNicuObj.apgarOnemin + ")";
}
if(this.admitPatientModel.birthToNicuObj.apgarFivemin != null && this.admitPatientModel.birthToNicuObj.apgarFivemin != ""){
  if(apgarStr == ""){
    apgarStr +="5min " + "(" +this.admitPatientModel.birthToNicuObj.apgarFivemin + ")";
  }
  else{
    apgarStr +=", 5min " + "(" +this.admitPatientModel.birthToNicuObj.apgarFivemin + ")";
  }
}

if(this.admitPatientModel.birthToNicuObj.apgarTenmin != null && this.admitPatientModel.birthToNicuObj.apgarTenmin != ""){
  if(apgarStr ==""){
    apgarStr +="10min " + "("+this.admitPatientModel.birthToNicuObj.apgarTenmin + ")";
  }
  else{
    apgarStr += ", 10min " + "("+this.admitPatientModel.birthToNicuObj.apgarTenmin + ")";
  }
}


if(apgarStr !=""){
  template += "Apgar score : " + apgarStr  +". "
}

//Encephalopathy
var encephalopathyStr = "";
if(this.admitPatientModel.birthToNicuObj.encephalopathy != null && this.admitPatientModel.birthToNicuObj.encephalopathy == true || this.admitPatientModel.birthToNicuObj.encephalopathy == 'true'){
  encephalopathyStr +="Diagnosed with encephalopathy";
}
if(this.admitPatientModel.birthToNicuObj.levenescore != null && this.admitPatientModel.birthToNicuObj.levenescore != undefined){
  encephalopathyStr +=" with Levene score " + this.admitPatientModel.leveneObj.levenescore;
}
if(encephalopathyStr != ""){
  template += encephalopathyStr + ". ";
}

var seizuresStr = "";
if(this.admitPatientModel.birthToNicuObj.seizures != null && this.admitPatientModel.birthToNicuObj.seizures == true || this.admitPatientModel.birthToNicuObj.seizures == 'true'){
  if(this.admitPatientModel.birthToNicuObj.seizuresAgeatonset != null && this.admitPatientModel.birthToNicuObj.seizuresAgeatonset != ""){
    seizuresStr += "At the age of " + this.admitPatientModel.birthToNicuObj.seizuresAgeatonset;
  }
  if(this.admitPatientModel.birthToNicuObj.seizures_ageinhoursdays != null && this.admitPatientModel.birthToNicuObj.seizures_ageinhoursdays || this.admitPatientModel.birthToNicuObj.seizures_ageinhoursdays == 'true'){
    if(this.admitPatientModel.birthToNicuObj.seizuresAgeatonset*1 == 1 || this.admitPatientModel.birthToNicuObj.seizuresAgeatonset*1 == 0){
      seizuresStr += " hr";
    }else{
      seizuresStr += " hrs";
    }
  }else if(this.admitPatientModel.birthToNicuObj.seizures_ageinhoursdays != null && !this.admitPatientModel.birthToNicuObj.seizures_ageinhoursdays || this.admitPatientModel.birthToNicuObj.seizures_ageinhoursdays =='false'){
    if(this.admitPatientModel.birthToNicuObj.seizuresAgeatonset*1 == 1 || this.admitPatientModel.birthToNicuObj.seizuresAgeatonset*1 == 0){
      seizuresStr += " day";
    }else{
      seizuresStr += " days";
    }
  }
  if(this.admitPatientModel.birthToNicuObj.seizuresNoEpisode != null && this.admitPatientModel.birthToNicuObj.seizuresNoEpisode != ""){
    seizuresStr += " baby had " + this.admitPatientModel.birthToNicuObj.seizuresNoEpisode + " episodes of";
  }
  else{
    seizuresStr += " baby had ";
  }
  if(this.admitPatientModel.birthToNicuObj.seizuresType != null){
    if(this.admitPatientModel.birthToNicuObj.seizuresType == true){
      seizuresStr += " symptomatic";
    }
    else if(this.admitPatientModel.birthToNicuObj.seizuresType == false){
      seizuresStr += " asymptomatic";
    }
  }
  if(seizuresStr != ""){
    template += seizuresStr + " seizures. ";
  }
}

var respSupportStr = "";
var respSuupportParam ="";
if(this.admitPatientModel.birthToNicuObj.rds != null && this.admitPatientModel.birthToNicuObj.rds == true || this.admitPatientModel.birthToNicuObj.rds == 'true'){
  respSupportStr +="Baby had respiratory distress";
  console.log(this.admitPatientModel.respSupportObj.rsVentType);
  if(this.admitPatientModel.birthToNicuObj.rdsAgeatonset != null && this.admitPatientModel.birthToNicuObj.rdsAgeatonset !=""){
    respSupportStr += " at the age of "+ this.admitPatientModel.birthToNicuObj.rdsAgeatonset ;
  }
  if(this.admitPatientModel.birthToNicuObj.rds_ageinhoursdays != null && this.admitPatientModel.birthToNicuObj.rds_ageinhoursdays || this.admitPatientModel.birthToNicuObj.rds_ageinhoursdays == 'true'){
    if(this.admitPatientModel.birthToNicuObj.rdsAgeatonset*1 == 1 || this.admitPatientModel.birthToNicuObj.rdsAgeatonset*1 ==0){
      respSupportStr += " hr";
    }else{
      respSupportStr += " hrs";
    }
  }else if(this.admitPatientModel.birthToNicuObj.rds_ageinhoursdays != null && !this.admitPatientModel.birthToNicuObj.rds_ageinhoursdays || this.admitPatientModel.birthToNicuObj.rds_ageinhoursdays =='false'){
    if(this.admitPatientModel.birthToNicuObj.rdsAgeatonset*1 == 1 || this.admitPatientModel.birthToNicuObj.rdsAgeatonset*1 ==0){
      respSupportStr += " day";
    }else{
      respSupportStr += " days";
    }
  }
  if(this.admitPatientModel.birthToNicuObj.respsupport != null){
    if(this.admitPatientModel.birthToNicuObj.respsupport){
      if(this.admitPatientModel.respSupportObj.rsVentType == 'Low Flow O2'){
        respSupportStr += " and was put on " + this.admitPatientModel.respSupportObj.rsVentType;
        if(this.admitPatientModel.respSupportObj.rsFlowRate != null && this.admitPatientModel.respSupportObj.rsFlowRate != ""){
          respSuupportParam += "Flow rate " + this.admitPatientModel.respSupportObj.rsFlowRate +" Litres/min";
        }
        if(this.admitPatientModel.respSupportObj.rsFio2 != null && this.admitPatientModel.respSupportObj.rsFio2 != ""){
          if(respSuupportParam == ""){
            respSuupportParam += "FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
          }
          else{
            respSuupportParam += ", FiO2 " +this.admitPatientModel.respSupportObj.rsFio2 +" %";
          }
        }
        if(respSuupportParam != ""){
          respSupportStr += " ("+respSuupportParam +")";
        }
      } else if(this.admitPatientModel.respSupportObj.rsVentType == 'High Flow O2'){
        respSupportStr += " and was put on " + this.admitPatientModel.respSupportObj.rsVentType;
        if(this.admitPatientModel.respSupportObj.rsFlowRate != null && this.admitPatientModel.respSupportObj.rsFlowRate != ""){
          respSuupportParam += "Flow rate " + this.admitPatientModel.respSupportObj.rsFlowRate +" Litres/min";
        }
        if(this.admitPatientModel.respSupportObj.rsFio2 != null && this.admitPatientModel.respSupportObj.rsFio2 != ""){
          if(respSuupportParam == ""){
            respSuupportParam += "FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
          }
          else{
            respSuupportParam += ", FiO2 " +this.admitPatientModel.respSupportObj.rsFio2 +" %";
          }
        }
        if(respSuupportParam != ""){
          respSupportStr += " ("+respSuupportParam +")";
        }
      } else if(this.admitPatientModel.respSupportObj.rsVentType == 'CPAP'){
        respSupportStr += " and was given " + this.admitPatientModel.respSupportObj.rsVentType;
        if(this.admitPatientModel.respSupportObj.rsMap != null && this.admitPatientModel.respSupportObj.rsMap != ""){
          respSuupportParam += "MAP/PEEP " + this.admitPatientModel.respSupportObj.rsMap +" cmH2O";
        }
        if(this.admitPatientModel.respSupportObj.rsFio2 != null && this.admitPatientModel.respSupportObj.rsFio2 != ""){
          if(respSuupportParam == ""){
            respSuupportParam += "FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
          }
          else{
            respSuupportParam += ", FiO2 " +this.admitPatientModel.respSupportObj.rsFio2 +" %";
          }
        }
        if(this.admitPatientModel.respSupportObj.rsFlowRate != null && this.admitPatientModel.respSupportObj.rsFlowRate != ""){
          if(respSuupportParam == ""){
            respSuupportParam += "Flow Rate " + this.admitPatientModel.respSupportObj.rsFlowRate +" Litres/min";
          }
          else{
            respSuupportParam += ", Flow Rate " +this.admitPatientModel.respSupportObj.rsFlowRate +" Litres/min";
          }
        }
        if(respSuupportParam != ""){
          respSupportStr += " ("+respSuupportParam +")";
        }
      } else if(this.admitPatientModel.respSupportObj.rsVentType == 'Mechanical Ventilation'){
        respSupportStr += " and was put on " + this.admitPatientModel.respSupportObj.rsVentType;
        if(this.admitPatientModel.respSupportObj.rsMechVentType != null){
          if(this.admitPatientModel.respSupportObj.rsMechVentType =='SIMV'){
            console.log("SIMV");
            respSupportStr += " (Mode SIMV";
            transpotedParameter="";
            if(this.admitPatientModel.respSupportObj.rsFlowRate != null && this.admitPatientModel.respSupportObj.rsFlowRate != ""){
              respSuupportParam += " Flow Rate " + this.admitPatientModel.respSupportObj.rsFlowRate +" Litres/min";
            }
            if(this.admitPatientModel.respSupportObj.rsPip != null && this.admitPatientModel.respSupportObj.rsPip != ""){
              if(transpotedParameter != ""){
                respSuupportParam += ", PIP " + this.admitPatientModel.respSupportObj.rsPip +" cmH2O";
              }
              else{
                respSuupportParam += " PIP " + this.admitPatientModel.respSupportObj.rsPip +" cmH2O";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsPeep != null && this.admitPatientModel.respSupportObj.rsPeep != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", PEEP " + this.admitPatientModel.respSupportObj.rsPeep +" cmH2O";
              }
              else{
                respSuupportParam += " PEEP " + this.admitPatientModel.respSupportObj.rsPeep +" cmH2O";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsIt != null && this.admitPatientModel.respSupportObj.rsIt != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", IT " + this.admitPatientModel.respSupportObj.rsIt +" secs";
              }
              else{
                respSuupportParam += " IT " + this.admitPatientModel.respSupportObj.rsIt +" secs";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsEt != null && this.admitPatientModel.respSupportObj.rsEt != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", ET " + this.admitPatientModel.respSupportObj.rsEt +" secs";
              }
              else{
                respSuupportParam += " ET " + this.admitPatientModel.respSupportObj.rsEt +" secs";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsFio2 != null && this.admitPatientModel.respSupportObj.rsFio2 != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
              }
              else{
                respSuupportParam += " FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsTv != null && this.admitPatientModel.respSupportObj.rsTv != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", TV " + this.admitPatientModel.respSupportObj.rsTv +" mL/kg";
              }
              else{
                respSuupportParam += " TV " + this.admitPatientModel.respSupportObj.rsTv +" mL/kg";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsMv != null && this.admitPatientModel.respSupportObj.rsMv != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", MV " + this.admitPatientModel.respSupportObj.rsMv;
              }
              else{
                respSuupportParam += " MV " + this.admitPatientModel.respSupportObj.rsMv;
              }
            }
            if(this.admitPatientModel.respSupportObj.rsRate != null && this.admitPatientModel.respSupportObj.rsRate != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", Breath Rate " + this.admitPatientModel.respSupportObj.rsRate +" Breaths/min";
              }
              else{
                respSuupportParam += " Breath Rate " + this.admitPatientModel.respSupportObj.rsRate +" Breaths/min";
              }
            }
            respSupportStr += respSuupportParam +")";
          }else if(this.admitPatientModel.respSupportObj.rsMechVentType =='PSV'){
            respSupportStr += " (Mode PSV";
            respSuupportParam="";
            if(this.admitPatientModel.respSupportObj.rsBackuprate != null && this.admitPatientModel.respSupportObj.rsBackuprate != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", Backup Rate " + this.admitPatientModel.respSupportObj.rsBackuprate +" Breaths/min";
              }
              else{
                respSuupportParam += " Backup Rate " + this.admitPatientModel.respSupportObj.rsBackuprate +" Breaths/min";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsTv != null && this.admitPatientModel.respSupportObj.rsTv != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", TV " + this.admitPatientModel.respSupportObj.rsTv +" mL/kg";
              }
              else{
                respSuupportParam += " TV " + this.admitPatientModel.respSupportObj.rsTv +" mL/kg";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsPip != null && this.admitPatientModel.respSupportObj.rsPip != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", PIP " + this.admitPatientModel.respSupportObj.rsPip +" cmH2O";
              }
              else{
                respSuupportParam += " PIP " + this.admitPatientModel.respSupportObj.rsPip +" cmH2O";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsPeep != null && this.admitPatientModel.respSupportObj.rsPeep != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", PEEP " + this.admitPatientModel.respSupportObj.rsPeep +" cmH2O";
              }
              else{
                respSuupportParam += " PEEP " + this.admitPatientModel.respSupportObj.rsPeep +" cmH2O";
              }
            }
            if(this.admitPatientModel.respSupportObj.rsFio2 != null && this.admitPatientModel.respSupportObj.rsFio2 != ""){
              if(respSuupportParam != ""){
                respSuupportParam += ", FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
              }
              else{
                respSuupportParam += " FiO2 " + this.admitPatientModel.respSupportObj.rsFio2 +" %";
              }
            }
            respSupportStr += respSuupportParam +")";
          }
        }
      } else if(this.admitPatientModel.respSupportObj.rsVentType == 'Unknown'){
        respSupportStr += "Baby was on Unknown respiratory support"
      }
    }
    else if(!this.admitPatientModel.birthToNicuObj.respsupport || this.admitPatientModel.birthToNicuObj.respsupport == 'false'){
      this.admitPatientModel.respSupportObj.rsVentType = null;
    }
  }
}
if(respSupportStr != ""){
  template +=respSupportStr += ". ";
}
else{
  console.log("end of resp");
}

var apneaStr = "";
if(this.admitPatientModel.birthToNicuObj.apnea != null && this.admitPatientModel.birthToNicuObj.apnea == true || this.admitPatientModel.birthToNicuObj.apnea == 'true'){
  if(this.admitPatientModel.birthToNicuObj.apneaAgeatonset != null && this.admitPatientModel.birthToNicuObj.apneaAgeatonset != ""){
    apneaStr +="At the age of " + this.admitPatientModel.birthToNicuObj.apneaAgeatonset;
  }
  if(this.admitPatientModel.birthToNicuObj.apneaNoEpisode != null && this.admitPatientModel.birthToNicuObj.apneaNoEpisode != ""){
    apneaStr +=" baby had " + this.admitPatientModel.birthToNicuObj.apneaNoEpisode + " episodes of";
  }
  else{
    apneaStr +=" baby had ";
  }
  if(apneaStr != ""){
    template +=apneaStr + " apnea. ";
  }
}

var jaundiceStr = "";
if(this.admitPatientModel.birthToNicuObj.jaundice != null && this.admitPatientModel.birthToNicuObj.jaundice == true || this.admitPatientModel.birthToNicuObj.jaundice == 'true'){
  if(this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset != null && this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset != ""){
    jaundiceStr +="At the age of " + this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset;
  }
  if(this.admitPatientModel.birthToNicuObj.jaundice_ageinhoursdays != null && this.admitPatientModel.birthToNicuObj.jaundice_ageinhoursdays || this.admitPatientModel.birthToNicuObj.jaundice_ageinhoursdays =='true'){
    if(this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset*1 == 1 || this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset*1 == 0){
      jaundiceStr +=" hr";
    }else{
      jaundiceStr +=" hrs";
    }
  } else if(this.admitPatientModel.birthToNicuObj.jaundice_ageinhoursdays != null && !this.admitPatientModel.birthToNicuObj.jaundice_ageinhoursdays || this.admitPatientModel.birthToNicuObj.jaundice_ageinhoursdays == 'false'){
    if(this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset*1 == 1 || this.admitPatientModel.birthToNicuObj.jaundiceAgeatonset*1 == 0){
      jaundiceStr +=" day";
    }else{
      jaundiceStr +=" days";
    }
  }
  if(this.admitPatientModel.birthToNicuObj.tbcvalue != null && this.admitPatientModel.birthToNicuObj.tbcvalue != ""){
    jaundiceStr +=" with TcB " + this.admitPatientModel.birthToNicuObj.tbcvalue + " mg/dl";
  }
  if(jaundiceStr != ""){
    template +=jaundiceStr + " baby diagnosed with jaundice. ";
  }
  else{
    template +="Baby diagnosed with jaundice. ";
  }
}

var phototherapyStr = "";
if(this.admitPatientModel.birthToNicuObj.phototherapy != null && this.admitPatientModel.birthToNicuObj.phototherapy == true || this.admitPatientModel.birthToNicuObj.phototherapy == 'true'){
  if(this.admitPatientModel.birthToNicuObj.phototherapyDuration != null && this.admitPatientModel.birthToNicuObj.phototherapyDuration != ""){
    phototherapyStr += this.admitPatientModel.birthToNicuObj.phototherapyDuration + "duration";
  }
  if(phototherapyStr != ""){
    template +="Phototherepy given for " + phototherapyStr +". ";
  }
  else{
    template +="Phototherepy given. ";
  }
}

var ivigStr = "";
if(this.admitPatientModel.birthToNicuObj.ivig != null && this.admitPatientModel.birthToNicuObj.ivig == true || this.admitPatientModel.birthToNicuObj.ivig == 'true'){
  if(this.admitPatientModel.birthToNicuObj.ivigDose != null && this.admitPatientModel.birthToNicuObj.ivigDose != ""){
    ivigStr += this.admitPatientModel.birthToNicuObj.ivigDose;
  }
  if(ivigStr != ""){
    template +="IVIG " + ivigStr + " dose given. ";
  }
  else{
    template +="IVIG given. ";
  }
}

if(this.admitPatientModel.birthToNicuObj.exchangeTransfusion != null && this.admitPatientModel.birthToNicuObj.phototherapy == true || this.admitPatientModel.birthToNicuObj.phototherapy == 'true'){
  template +="Exchange Transfusion  done. ";
}
else{
  template += "";
}


var medicineStr = "";
console.log(this.admitPatientModel.medicineList.length);
if(this.admitPatientModel.medicineList.length>1){
  for(var i=0; i<this.admitPatientModel.medicineList.length ; i++){
    if(this.admitPatientModel.medicineList[i].medicine_name != null && this.admitPatientModel.medicineList[i].medicine_name != ""){
      if(medicineStr != ""){
        medicineStr += ", "+this.admitPatientModel.medicineList[i].medicine_name;
      }
      else{
        medicineStr += this.admitPatientModel.medicineList[i].medicine_name;
      }
    }
    if(this.admitPatientModel.medicineList[i].medicine_dose != null && this.admitPatientModel.medicineList[i].medicine_dose != ""){
      medicineStr += " dose " +this.admitPatientModel.medicineList[i].medicine_dose;
    }
  }
  if(medicineStr != ""){
    template +="Medicines " + medicineStr +" are given. ";
  }
}
else if(this.admitPatientModel.medicineList.length=1){
  if(this.admitPatientModel.medicineList[0].medicine_name != null && this.admitPatientModel.medicineList[0].medicine_name != ""){
    medicineStr += this.admitPatientModel.medicineList[0].medicine_name;
  }
  if(this.admitPatientModel.medicineList[0].medicine_dose != null && this.admitPatientModel.medicineList[0].medicine_dose != ""){
    medicineStr += " dose " +this.admitPatientModel.medicineList[0].medicine_dose;
  }
  if(medicineStr != ""){
    template +="Medicine " + medicineStr +" is given. ";
  }
}

if(this.admitPatientModel.birthToNicuObj.meconium || this.admitPatientModel.birthToNicuObj.meconium == 'true'){
  template +="Meconium passed";
  if(this.admitPatientModel.birthToNicuObj.meconiumHour !=null && this.admitPatientModel.birthToNicuObj.meconiumHour !=""){
    if(this.admitPatientModel.birthToNicuObj.meconiumHour*1 == 1 || this.admitPatientModel.birthToNicuObj.meconiumHour*1 == 0){
      template +=" after " + this.admitPatientModel.birthToNicuObj.meconiumHour + "hr. ";
    }else{
      template +=" after " + this.admitPatientModel.birthToNicuObj.meconiumHour + "hrs. ";
    }
  }
  else{
    template +=". ";
  }
}
else if(this.admitPatientModel.birthToNicuObj.meconium == false && this.admitPatientModel.birthToNicuObj.meconium != null){
  template +="Meconium not passed. ";
}

if(this.admitPatientModel.birthToNicuObj.passedUrine != null && this.admitPatientModel.birthToNicuObj.passedUrine || this.admitPatientModel.birthToNicuObj.passedUrine =='true'){
  template +="Passed urine";
  if(this.admitPatientModel.birthToNicuObj.urineOutput != null && this.admitPatientModel.birthToNicuObj.urineOutput != ""){
    template +=" ("+ this.admitPatientModel.birthToNicuObj.urineOutput +" ml/kg/hr)";
  }else{
    template +=". ";
  }
}


if(this.admitPatientModel.birthToNicuObj.otherDetails != null && this.admitPatientModel.birthToNicuObj.otherDetails != ""){
  template += this.admitPatientModel.birthToNicuObj.otherDetails;
}
this.admitPatientModel.admissionNotesObj.birth_to_inicu = template;

}

//status at admissionNotesObj
getFentonCentile = function (type, value,category) {
console.log("getFentonCentile");
this.populateAdmissionStatusStr();
if(value == null || value == "") {
  if(type=="weight") {
    if(category=="birth"){
      this.admitPatientModel.babyDetailObj.weight_centile = null;
      this.admitPatientModel.babyDetailObj.weight_galevel = null;
    }
    else if(category=="admn"){
      this.admitPatientModel.babyDetailObj.admissionWeightCentile = null;
      this.admitPatientModel.babyDetailObj.admissionWeightGAlevel = null;
    }

  } else if(type == "length") {
      if(category=="birth"){
        this.admitPatientModel.babyDetailObj.length_centile = null;
        this.admitPatientModel.babyDetailObj.length_galevel = null;
      }
      else if(category=="admn"){
        this.admitPatientModel.babyDetailObj.admissionLengthCentile = null;
        this.admitPatientModel.babyDetailObj.admissionLengthGaLevel = null;
      }

  } else if (type == "head") {
      if(category=="birth"){
        this.admitPatientModel.babyDetailObj.hc_centile = null;
        this.admitPatientModel.babyDetailObj.hc_galevel = null;
      }
      else if(category=="admn"){
        this.admitPatientModel.babyDetailObj.admissionHCcentile = null;
        this.admitPatientModel.babyDetailObj.admissionHCgaLevel = null;
      }

  }
} else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks == null || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks == "") {
  if(this.fentonGestationPopupFlag) {
    this.fentonGestationPopupFlag = false;
  }

} else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks < 23 || this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks > 45) {
  if(this.fentonRangePopupFlag) {
    this.fentonRangePopupFlag = false;
  }
} else {
  var gender = "";
  if(this.admitPatientModel.babyDetailObj.gender == "Male") {
    gender = "male";
  } else if(this.admitPatientModel.babyDetailObj.gender == "Female") {
    gender = "female";
  } else if(this.fentonGenderPopupFlag){
    this.fentonGenderPopupFlag = false;
    return;
  }

  try
  {
    this.http.request(this.apiData.getFentonCentile + "/" + gender + "/" + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + "/" + type + "/" + value + "/",
  ).subscribe(res => {
    let  responseFenton = Math.round(res.json());
    var level = "";
    if(responseFenton < 10) {
      level = "SGA";
    } else if(responseFenton < 97) {
      level = "AGA";
    } else {
      level = "LGA";
    }

    if(type=="weight") {
      if(category=="birth"){
        this.admitPatientModel.babyDetailObj.weight_centile = responseFenton;
        this.admitPatientModel.babyDetailObj.weight_galevel = level;
      }
      else if(category=="admn"){
        this.admitPatientModel.babyDetailObj.admissionWeightCentile = responseFenton;
        this.admitPatientModel.babyDetailObj.admissionWeightGAlevel = level;
      }

    } else if(type == "length") {
        if(category=="birth"){
          this.admitPatientModel.babyDetailObj.length_centile = responseFenton;
          this.admitPatientModel.babyDetailObj.length_galevel = level;
        }
        else if(category=="admn"){
          this.admitPatientModel.babyDetailObj.admissionLengthCentile = responseFenton;
          this.admitPatientModel.babyDetailObj.admissionLengthGaLevel = level;
        }

    } else if (type == "head") {
      if(category=="birth"){
        this.admitPatientModel.babyDetailObj.hc_centile = responseFenton;
        this.admitPatientModel.babyDetailObj.hc_galevel = level;
      }
      else if(category=="admn"){
      	this.admitPatientModel.babyDetailObj.admissionHCcentile = responseFenton;
        this.admitPatientModel.babyDetailObj.admissionHCgaLevel = level;
      }

    }
    this.populateAdmissionStatusStr();
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
populateAdmissionStatusStr = function(){
  console.log(this.admitPatientModel.babyDetailObj);
  this.admitPatientModel.admissionNotesObj.status_at_admission = "";
  var template = "";

  var str="";
  if(this.admitPatientModel.babyDetailObj.consciousness == 'Active'){
    str += "At the time of admission baby was " + this.admitPatientModel.babyDetailObj.consciousness;
  } else if(this.admitPatientModel.babyDetailObj.consciousness == 'Lethargic'){
    str += "At the time of admission baby was " + this.admitPatientModel.babyDetailObj.consciousness;
  } else if(this.admitPatientModel.babyDetailObj.consciousness == 'Stuporose'){
    str += "At the time of admission baby was " + this.admitPatientModel.babyDetailObj.consciousness;
  } else if(this.admitPatientModel.babyDetailObj.consciousness == 'Comatose'){
    str += "At the time of admission baby was " + this.admitPatientModel.babyDetailObj.consciousness;
  }

  if(str == ""){
    if(this.admitPatientModel.babyDetailObj.birthweight != null && this.admitPatientModel.babyDetailObj.birthweight != ""){
      str +="Birth weight " + this.admitPatientModel.babyDetailObj.birthweight + " gm";
      if(this.admitPatientModel.babyDetailObj.weight_galevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.weight_galevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.birthlength != null && this.admitPatientModel.babyDetailObj.birthlength != ""){
      str +=", length " + this.admitPatientModel.babyDetailObj.birthlength + " cm";
      if(this.admitPatientModel.babyDetailObj.length_galevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.length_galevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.birthheadcircumference != null && this.admitPatientModel.babyDetailObj.birthheadcircumference != ""){
      str +=", head circumference " + this.admitPatientModel.babyDetailObj.birthheadcircumference + " cm";
      if(this.admitPatientModel.babyDetailObj.hc_galevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.hc_galevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.ponderal_index != null && this.admitPatientModel.babyDetailObj.ponderal_indexponderal_index != ""){
      str +=", ponderal index " + Math.round(this.admitPatientModel.babyDetailObj.ponderal_index*10)/10 + " gm/cm3";
    }
    if(this.admitPatientModel.babyDetailObj.admissionWeight != null && this.admitPatientModel.babyDetailObj.admissionWeight != "" ){
      str +=". Admission weight " + this.admitPatientModel.babyDetailObj. admissionWeight + " gm";
      if(this.admitPatientModel.babyDetailObj.admissionWeightGAlevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.admissionWeightGAlevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.admissionLength != null && this.admitPatientModel.babyDetailObj.admissionLength != "" ){
      str +=", length " + this.admitPatientModel.babyDetailObj. admissionLength + " cm";
      if(this.admitPatientModel.babyDetailObj.admissionLengthGaLevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.admissionLengthGaLevel + ")";
      }
    }

    if(this.admitPatientModel.babyDetailObj.admissionHeadCircumference != null && this.admitPatientModel.babyDetailObj.admissionHeadCircumference != "" ){
      str +=", Admission head circumference " + this.admitPatientModel.babyDetailObj.admissionHeadCircumference + " cm";
      if(this.admitPatientModel.babyDetailObj.admissionHCgaLevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.admissionHCgaLevel + ")";
      }
    }


  }else{
    if(this.admitPatientModel.babyDetailObj.birthweight != null && this.admitPatientModel.babyDetailObj.birthweight != ""){
      str +=" with birth weight " + this.admitPatientModel.babyDetailObj.birthweight + " gm";
      if(this.admitPatientModel.babyDetailObj.weight_galevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.weight_galevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.birthlength != null && this.admitPatientModel.babyDetailObj.birthlength != ""){
      str +=", length " + this.admitPatientModel.babyDetailObj.birthlength + " cm";
      if(this.admitPatientModel.babyDetailObj.length_galevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.length_galevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.birthheadcircumference != null && this.admitPatientModel.babyDetailObj.birthheadcircumference != ""){
      str +=", head circumference " + this.admitPatientModel.babyDetailObj.birthheadcircumference + " cm";
      if(this.admitPatientModel.babyDetailObj.hc_galevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.hc_galevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.ponderal_index != null && this.admitPatientModel.babyDetailObj.ponderal_indexponderal_index != ""){
      str +=", ponderal index " + Math.round(this.admitPatientModel.babyDetailObj.ponderal_index*10)/10 + " gm/cm3";
    }
    if(this.admitPatientModel.babyDetailObj.admissionWeight != null && this.admitPatientModel.babyDetailObj. admissionWeight != ""){
      str +=". Admission weight " + this.admitPatientModel.babyDetailObj. admissionWeight + " gm";
      if(this.admitPatientModel.babyDetailObj.admissionWeightGAlevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.admissionWeightGAlevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.admissionLength != null && this.admitPatientModel.babyDetailObj.admissionLength != "" ){
      str +=", length " + this.admitPatientModel.babyDetailObj. admissionLength + " cm";
      if(this.admitPatientModel.babyDetailObj.admissionLengthGaLevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.admissionLengthGaLevel + ")";
      }
    }
    if(this.admitPatientModel.babyDetailObj.admissionHeadCircumference != null && this.admitPatientModel.babyDetailObj.admissionHeadCircumference != ""){
      str +=", Admission head circumference " + this.admitPatientModel.babyDetailObj.admissionHeadCircumference + " cm";
      if(this.admitPatientModel.babyDetailObj.admissionHCgaLevel != null){
        str += " (" +this.admitPatientModel.babyDetailObj.admissionHCgaLevel + ")";
      }
    }

  }
  if(str != "")
  {

    if(this.admitPatientModel.babyDetailObj.admissionWeight == this.admitPatientModel.babyDetailObj.birthweight && this.admitPatientModel.babyDetailObj.admissionLength == this.admitPatientModel.babyDetailObj.birthlength && this.admitPatientModel.babyDetailObj.admissionHeadCircumference == this.admitPatientModel.babyDetailObj.birthheadcircumference)
        {
          str = '';
          str += "Birth weight " + this.admitPatientModel.babyDetailObj.birthweight + " gm , birth length " + this.admitPatientModel.babyDetailObj.birthlength + " cm , birth head circumference " + this.admitPatientModel.babyDetailObj.birthheadcircumference + " cm , ponderal index " + Math.round(this.admitPatientModel.babyDetailObj.ponderal_index*10)/10 + " gm/cm3. Admission weight , admission length and admission head circumference are same as birth details";
        }
  }

  if(str != ""){
    template += str + ". ";
  }

  var haemodynamicStable = true;
  var haemodynamicStableStr = "";

  if((this.admitPatientModel.babyDetailObj.hr !=null && this.admitPatientModel.babyDetailObj.hr >= 0 && this.admitPatientModel.babyDetailObj.hr <= 350)){
    this.hrMessage = "";
    if((this.admitPatientModel.babyDetailObj.hr >= 120 && this.admitPatientModel.babyDetailObj.hr <= 180)){
      haemodynamicStableStr += "HR " + this.admitPatientModel.babyDetailObj.hr + " bpm";
    }
    else{
      haemodynamicStableStr += "HR " + this.admitPatientModel.babyDetailObj.hr + " bpm";
      if(haemodynamicStable == true){
        haemodynamicStable = false;
      }
    }
  }
  else{
    if(this.admitPatientModel.babyDetailObj.hr ==null){

    }else{
      this.hrMessage = "Range 0-350";
      this.admitPatientModel.babyDetailObj.hr =null;
    }
  }

  if((this.admitPatientModel.babyDetailObj.rr !=null && this.admitPatientModel.babyDetailObj.rr >= 0 && this.admitPatientModel.babyDetailObj.rr <= 150)){
    this.rrMessage = "";
    if((this.admitPatientModel.babyDetailObj.rr !=null && this.admitPatientModel.babyDetailObj.rr <= 120 && this.admitPatientModel.babyDetailObj.rr >= 30 ) ){
      if(haemodynamicStableStr != ""){
        haemodynamicStableStr += ", RR " + this.admitPatientModel.babyDetailObj.rr + " bpm";
      }
      else{
        haemodynamicStableStr += "RR " + this.admitPatientModel.babyDetailObj.rr + " bpm";
      }
    }
    else if(this.admitPatientModel.babyDetailObj.rr !=null){
      if(haemodynamicStableStr != ""){
        haemodynamicStableStr += ", RR " + this.admitPatientModel.babyDetailObj.rr + " bpm";
      }
      else{
        haemodynamicStableStr += "RR " + this.admitPatientModel.babyDetailObj.rr + " bpm";
      }
    }
  }
  else{
    if(this.admitPatientModel.babyDetailObj.rr ==null){

    }else{
      this.rrMessage = "Range 0-150";
      this.admitPatientModel.babyDetailObj.rr =null
    }
  }

  if((this.admitPatientModel.babyDetailObj.spo2 !=null && this.admitPatientModel.babyDetailObj.spo2 >= 0 && this.admitPatientModel.babyDetailObj.spo2 <= 100)){
    this.spoMessage = "";
    if(( this.admitPatientModel.babyDetailObj.spo2 !=null && this.admitPatientModel.babyDetailObj.spo2 >= 90 && this.admitPatientModel.babyDetailObj.spo2 <= 100) ){
      if(haemodynamicStableStr != ""){
        haemodynamicStableStr += ", SpO2 " + this.admitPatientModel.babyDetailObj.spo2 +" %";
      }
      else{
        haemodynamicStableStr += "SpO2 " + this.admitPatientModel.babyDetailObj.spo2 +" %";
      }
    }
    else if( this.admitPatientModel.babyDetailObj.spo2 !=null){
      if(haemodynamicStableStr != ""){
        haemodynamicStableStr += ", SpO2 " + this.admitPatientModel.babyDetailObj.spo2 +" %";
      }
      else{
        haemodynamicStableStr += "SpO2 " + this.admitPatientModel.babyDetailObj.spo2 +" %";
      }
    }
  }
  else{
    if(this.admitPatientModel.babyDetailObj.spo2 ==null){

    }else{
      this.spoMessage = "Range 0-100";
      this.admitPatientModel.babyDetailObj.spo2 = null;
    }
  }

  if((this.admitPatientModel.babyDetailObj.bp_lul != null && this.admitPatientModel.babyDetailObj.bp_lul >= 0 && this.admitPatientModel.babyDetailObj.bp_lul <= 150)){
    this.lulMessage = "";
  }
  else{
    if(this.admitPatientModel.babyDetailObj.bp_lul == null){

    }else{
      this.lulMessage = "Range 0-150";
      this.admitPatientModel.babyDetailObj.bp_lul = null;
    }
  }
  if((this.admitPatientModel.babyDetailObj.bp_rul != null && this.admitPatientModel.babyDetailObj.bp_rul >= 0 && this.admitPatientModel.babyDetailObj.bp_rul <= 150)){
    this.rulMessage = "";
  }
  else{
    if(this.admitPatientModel.babyDetailObj.bp_rul == null){

    }else{
      this.rulMessage = "Range 0-150";
      this.admitPatientModel.babyDetailObj.bp_rul = null;
    }
  }

  if((this.admitPatientModel.babyDetailObj.bp_lll != null && this.admitPatientModel.babyDetailObj.bp_lll >= 0 && this.admitPatientModel.babyDetailObj.bp_lll <= 150)){
    this.lllMessage = "";
  }
  else{
    if(this.admitPatientModel.babyDetailObj.bp_lll == null){

    }else{
      this.lllMessage = "Range 0-150";
      this.admitPatientModel.babyDetailObj.bp_lll = null;
    }
  }
  if((this.admitPatientModel.babyDetailObj.bp_rll != null && this.admitPatientModel.babyDetailObj.bp_rll >= 0 && this.admitPatientModel.babyDetailObj.bp_rll <= 150)){
    this.rllMessage = "";
  }
  else{
    if(this.admitPatientModel.babyDetailObj.bp_rll == null){

    }else{
      this.rllMessage = "Range 0-150";
      this.admitPatientModel.babyDetailObj.bp_rll = null;
    }
  }
  if((this.admitPatientModel.babyDetailObj.crt != null && this.admitPatientModel.babyDetailObj.crt >= 0 && this.admitPatientModel.babyDetailObj.crt <= 10)){
    this.crtMessage = "";
    if(( this.admitPatientModel.babyDetailObj.crt !=null && this.admitPatientModel.babyDetailObj.crt >= 0 && this.admitPatientModel.babyDetailObj.crt <= 3) ){
      haemodynamicStableStr += " CRT " + this.admitPatientModel.babyDetailObj.crt + " sec";
      if(haemodynamicStable == true){
        haemodynamicStable = true;
      }
      else{
        haemodynamicStable = false;
      }
    }
    else if( this.admitPatientModel.babyDetailObj.crt !=null){
      haemodynamicStable = false;
    }
  }
  else{
    if(this.admitPatientModel.babyDetailObj.crt == null){

    }else{
      this.crtMessage = "Range 0-10";
    }
  }

  if(this.admitPatientModel.babyDetailObj.bp_mean != null && this.admitPatientModel.babyDetailObj.bp_mean != ""){
    if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != ""){
      if(( this.admitPatientModel.babyDetailObj.bp_mean >= this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks) ){
        haemodynamicStableStr += " Mean " + this.admitPatientModel.babyDetailObj.bp_mean + " bpm";

        if(haemodynamicStable == true){
          haemodynamicStable = true;
        }
        else{
          haemodynamicStable = false;
        }
      }else{
        haemodynamicStable = false;
      }
    }
    else if( this.admitPatientModel.babyDetailObj.bp_mean !=null){
      haemodynamicStable = false;
    }
  }

  if(( this.admitPatientModel.babyDetailObj.bp_systolic !=null || this.admitPatientModel.babyDetailObj.bp_diastolic !=null)){
    if(this.admitPatientModel.babyDetailObj.bp_systolic ==null){
      if(this.admitPatientModel.babyDetailObj.bp_diastolic >= 0 && this.admitPatientModel.babyDetailObj.bp_diastolic <= 150){
        if(haemodynamicStableStr != ""){
          haemodynamicStableStr += ", BP " + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
        else{
          haemodynamicStableStr += "BP " + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
      }
      else{
        if(haemodynamicStableStr != ""){
          haemodynamicStableStr += ", BP " + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
        else{
          haemodynamicStableStr += "BP " + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
      }
    }
    else if(this.admitPatientModel.babyDetailObj.bp_diastolic ==null){
      if(this.admitPatientModel.babyDetailObj.bp_systolic >= 0 && this.admitPatientModel.babyDetailObj.bp_systolic <= 80){
        if(haemodynamicStableStr != ""){
          haemodynamicStableStr += ", BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "mmHg" ;
        }
        else{
          haemodynamicStableStr += "BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "mmHg" ;
        }
      }
      else{
        if(haemodynamicStableStr != ""){
          haemodynamicStableStr += ", BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "mmHg" ;
        }
        else{
          haemodynamicStableStr += "BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "mmHg" ;
        }
      }
    }
    else if(this.admitPatientModel.babyDetailObj.bp_systolic >= 0 && this.admitPatientModel.babyDetailObj.bp_systolic <= 80 &&
      this.admitPatientModel.babyDetailObj.bp_systolic !=null && this.admitPatientModel.babyDetailObj.bp_diastolic !=null
      && this.admitPatientModel.babyDetailObj.bp_diastolic >= 0 && this.admitPatientModel.babyDetailObj.bp_diastolic <= 150){
        if(haemodynamicStableStr != ""){
          haemodynamicStableStr += ", BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "/" + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
        else{
          haemodynamicStableStr += "BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "/" + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
      }
      else{
        if(haemodynamicStableStr != ""){
          haemodynamicStableStr += ", BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "/" + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
        else{
          haemodynamicStableStr += "BP " + this.admitPatientModel.babyDetailObj.bp_systolic + "/" + this.admitPatientModel.babyDetailObj.bp_diastolic + "mmHg" ;
        }
      }
    }
    if(haemodynamicStable == true && haemodynamicStableStr != ""){
      template += "Haemodynamically stable with " + haemodynamicStableStr +".";
    }
    else if(haemodynamicStable == false && haemodynamicStableStr != ""){
      template += "Haemodynamically not stable with " + haemodynamicStableStr +".";
    }
    this.admitPatientModel.admissionNotesObj.status_at_admission = template;
    this.populateSystemicDiagnosis();
  }

//general physical Examination
populateGPEStr = function() {
  var str = "";
  var normalStr = "";
  var abnormalStr = "";

  if(this.admitPatientModel.genPhyExamObj.apearance != null && this.admitPatientModel.genPhyExamObj.apearance != "") {
    str += "On General Physical Examination the baby is " + this.admitPatientModel.genPhyExamObj.apearance.toLowerCase() + ". ";
  }

  if(this.admitPatientModel.genPhyExamObj.head_neck != null){
    if(this.admitPatientModel.genPhyExamObj.head_neck == "Normal") {
      normalStr += ", Head, Neck";
    } else if(this.admitPatientModel.genPhyExamObj.head_neck_other != null && this.admitPatientModel.genPhyExamObj.head_neck_other != "") {
      abnormalStr += "Examination of Head and Neck shows " + this.admitPatientModel.genPhyExamObj.head_neck_other + ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.palate != null){
    if(this.admitPatientModel.genPhyExamObj.palate == "Normal") {
      normalStr += ", Palate";
    } else {
      abnormalStr += "Examination of Palate shows cleft. ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.lip != null){
    if(this.admitPatientModel.genPhyExamObj.lip == "Normal") {
      normalStr += ", Lips";
    } else {
      abnormalStr += "Examination of lips shows cleft";
      if(this.admitPatientModel.genPhyExamObj.lipCleftSide != null) {
        if(this.admitPatientModel.genPhyExamObj.lipCleftSide) {
          abnormalStr += " on right side";
        } else {
          abnormalStr += " on left side";
        }
      }
      abnormalStr += ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.eyes != null){
    if(this.admitPatientModel.genPhyExamObj.eyes == "Normal") {
      normalStr += ", Eyes";
    } else if(this.admitPatientModel.genPhyExamObj.eyes_other != null && this.admitPatientModel.genPhyExamObj.eyes_other != "") {
      abnormalStr += "Examination of eyes shows " + this.admitPatientModel.genPhyExamObj.eyes_other + ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.skin != null){
    if(this.admitPatientModel.genPhyExamObj.skin == "Normal") {
      normalStr += ", Skin";
    } else if(this.admitPatientModel.genPhyExamObj.skin == "Other") {
      if(this.admitPatientModel.genPhyExamObj.skin_other != null && this.admitPatientModel.genPhyExamObj.skin_other != "") {
        abnormalStr += "Examination of skin shows " + this.admitPatientModel.genPhyExamObj.skin_other + ". ";
      }
    } else {
      abnormalStr += "Examination of skin shows " + this.admitPatientModel.genPhyExamObj.skin + ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.chest != null){
    if(this.admitPatientModel.genPhyExamObj.chest == "Normal") {
      normalStr += ", Chest";
    } else if(this.admitPatientModel.genPhyExamObj.chest_other != null && this.admitPatientModel.genPhyExamObj.chest_other != "") {
      abnormalStr += "Examination of chest shows " + this.admitPatientModel.genPhyExamObj.chest_other + ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.abdomen != null){
    if(this.admitPatientModel.genPhyExamObj.abdomen == "Normal") {
      normalStr += ", Abdomen";
    } else if(this.admitPatientModel.genPhyExamObj.abdomen_other != null && this.admitPatientModel.genPhyExamObj.abdomen_other != "") {
      abnormalStr += "Examination of abdomen shows " + this.admitPatientModel.genPhyExamObj.abdomen_other + ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.genitals != null){
    if(this.admitPatientModel.genPhyExamObj.genitals == "Normal") {
      normalStr += ", Genitals";
    } else if(this.admitPatientModel.genPhyExamObj.genitals_other != null && this.admitPatientModel.genPhyExamObj.genitals_other != "") {
      abnormalStr += "Examination of genitals shows " + this.admitPatientModel.genPhyExamObj.genitals_other + ". ";
    }
  }

  if(this.admitPatientModel.genPhyExamObj.reflexes != null){
    if(this.admitPatientModel.genPhyExamObj.reflexes == "Normal") {
      normalStr += ", Reflexes";
    } else if(this.admitPatientModel.genPhyExamObj.reflexes_other != null && this.admitPatientModel.genPhyExamObj.reflexes_other != "") {
      abnormalStr += "Examination of reflexes shows " + this.admitPatientModel.genPhyExamObj.reflexes_other + ". ";
    }
  }

  //For All Normal systems,just one sentence :, Eyes, Skin , Palate, Chest , Genitals are normal
  normalStr = normalStr.substring(2, normalStr.length);
  if(normalStr != '') {
    if(normalStr.includes(',')) {
      str += normalStr + " are normal. ";
    } else {
      str += normalStr + " is normal. ";
    }
  }

  //Anal Opening is patent/invisible/abnormally placed at the level of "entered text".
  if(this.admitPatientModel.genPhyExamObj.anal != null && this.admitPatientModel.genPhyExamObj.anal != '') {
    str += "Anal opening is " + this.admitPatientModel.genPhyExamObj.anal.toLowerCase();
    if(this.admitPatientModel.genPhyExamObj.anal_other != null && this.admitPatientModel.genPhyExamObj.anal_other != '') {
      str += " at the level of " + this.admitPatientModel.genPhyExamObj.anal_other;
    }
    str += ". ";
  }

  //All Abnormal values having one sentence for each abnormal system : e.g. Examination of eyes shows ""
  if(abnormalStr != '') {
    str += abnormalStr;
  }

  //Baby has a mole (if the doctor has entered anything in other congenital abnormalities)
  if(this.admitPatientModel.genPhyExamObj.cong_malform == "Yes" && this.admitPatientModel.genPhyExamObj.cong_malform_other != null && this.admitPatientModel.genPhyExamObj.cong_malform_other != "") {
    str += "Baby has " + this.admitPatientModel.genPhyExamObj.cong_malform_other + ". ";
  }

  //The last sentence should say : No Other congenital abnormalities is found
  str += "No other congenital abnormalities found. ";

  if(this.admitPatientModel.genPhyExamObj.other != null && this.admitPatientModel.genPhyExamObj.other != "") {
    str += this.admitPatientModel.genPhyExamObj.other;
  }

  this.admitPatientModel.admissionNotesObj.gen_phy_exam = str;
}

downesScoreOpen = function() {
  console.log("downes initiated");
  $("#downesOverlay").css("display", "block");
  $("#downesScorePopup").addClass("showing");
};

//		below code is used to close the downe score
closeModalDownes = function(){
this.checkValidDownes();
if(this.checkValidDownes()){
  this.admitPatientModel.downeFlag = true;
  $("#downesNotValid").css("display","none");
  $("#downesOverlay").css("display", "none");
  $("#downesScorePopup").toggleClass("showing");
  $("#displayDownes").css("display","table-cell");
}
else{
  $("#downesNotValid").css("display","none");
  $("#downesNotValid").css("display","inline-block");
}
};


closeModalDownesOnSave = function(){
  this.checkValidDownes();
  if(this.checkValidDownes()){
    this.admitPatientModel.downeFlag = true;
    $("#downesNotValid").css("display","none");
    $("#downesOverlay").css("display", "none");
    $("#downesScorePopup").toggleClass("showing");
    $("#displayDownes").css("display","table-cell");
  }
  else{
    $("#downesNotValid").css("display","none");
    $("#downesNotValid").css("display","inline-block");
  }
}
checkValidDownes = function(){
  console.log("checking downes");
  var downesScoreValid = true;
  if(this.admitPatientModel.downesObj.cynosis == null || this.admitPatientModel.downesObj.cynosis == undefined){
    downesScoreValid = false;
  }
  else if(this.admitPatientModel.downesObj.retractions == null || this.admitPatientModel.downesObj.retractions == undefined){
    downesScoreValid = false;
  }
  else if(this.admitPatientModel.downesObj.grunting == null || this.admitPatientModel.downesObj.grunting == undefined){
    downesScoreValid = false;
  }
  else if(this.admitPatientModel.downesObj.airentry == null || this.admitPatientModel.downesObj.airentry == undefined){
    downesScoreValid = false;
  }
  else if(this.admitPatientModel.downesObj.respiratoryrate == null || this.admitPatientModel.downesObj.respiratoryrate == undefined){
    downesScoreValid = false;
  }
  return downesScoreValid;
}
//		below code is used to calculate the downe score

totalDownesValue = function(downesValue){

  console.log(downesValue);
  if(downesValue == "cyanosis"){
    this.totalCyanosis = parseInt(this.admitPatientModel.downesObj.cynosis);
  }if(downesValue == "retractions"){
    this.totalRetractions = parseInt(this.admitPatientModel.downesObj.retractions);
  }if(downesValue == "grunting"){
    this.totalGrunting = parseInt(this.admitPatientModel.downesObj.grunting);
  }if(downesValue == "airEntry"){
    this.totalAirEntry = parseInt(this.admitPatientModel.downesObj.airentry);
  }if(downesValue == "respiratoryRate"){
    this.totalRespiratoryRate = parseInt(this.admitPatientModel.downesObj.respiratoryrate);
  }
  this.downesValueCalculated = this.totalCyanosis + this.totalRetractions + this.totalGrunting + this.totalAirEntry + this.totalRespiratoryRate;
  this.admitPatientModel.downesObj.downesscore = this.downesValueCalculated;
}

//sytemic examination
whichSystematicTab = function(value){
  this.whichTabSystematicSelected = value;
  if(this.admitPatientModel.admissionNotesObj.systemic_exam != null && this.admitPatientModel.admissionNotesObj.systemic_exam != undefined){
    if (value == "Resp" && this.admitPatientModel.sysAssessmentObj.respiratorySystem == "'Yes'" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Respiratory system is normal.") != -1){
      this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("Respiratory system is normal.","");
    } else if (value == "cns" && this.admitPatientModel.sysAssessmentObj.cns == "'Yes'" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("CNS examination is normal.") != -1){
      this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("CNS examination is normal.","");
    } else if (value == "infection" && this.admitPatientModel.sysAssessmentObj.infection == "'Yes'" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("There is no evidence of infection.") != -1){
      this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("There is no evidence of infection.","");
    }
  }
}

populateSystemicDiagnosis = function(){
  var template ="";
  this.admitPatientModel.admissionNotesObj.diagnosis = "";
  if(this.admitPatientModel.babyDetailObj.babyType != null && this.admitPatientModel.babyDetailObj.babyType != undefined){
    if(this.admitPatientModel.babyDetailObj.babyType == 'Single'){
      template += "Singleton";
    }else if(this.admitPatientModel.babyDetailObj.babyType=='Twins'){
      if(this.admitPatientModel.babyDetailObj.babyNumber!=null){
        if(this.admitPatientModel.babyDetailObj.babyNumber=='I'){
          template +="Twin I";
        }else if(this.admitPatientModel.babyDetailObj.babyNumber=='II'){
          template +="Twin II";
        }
      }

    }else if(this.admitPatientModel.babyDetailObj.babyType=='Triplets'){
      if(this.admitPatientModel.babyDetailObj.babyNumber!=null){
        if(this.admitPatientModel.babyDetailObj.babyNumber=='I'){
          template +="Triplet I";
        }else if(this.admitPatientModel.babyDetailObj.babyNumber=='II'){
          template +="Triplet II";
        }else if(this.admitPatientModel.babyDetailObj.babyNumber=='III'){
          template +="Triplet III";
        }
      }

    }else if(this.admitPatientModel.babyDetailObj.babyType=='Quadruplets'){
      if(this.admitPatientModel.babyDetailObj.babyNumber!=null){
        if(this.admitPatientModel.babyDetailObj.babyNumber=='I'){
          template +="Quadruplets I";
        }else if(this.admitPatientModel.babyDetailObj.babyNumber=='II'){
          template +="Quadruplets II";
        }else if(this.admitPatientModel.babyDetailObj.babyNumber=='III'){
          template +="Quadruplets III";
        }else if(this.admitPatientModel.babyDetailObj.babyNumber=='IV'){
          template +="Quadruplets IV";
        }
      }

    }
  }
  if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != null
    && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks != ""){
      template += "/"+this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + " weeks";
    }

  if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null
    && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != ""){
      template += " "+this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays + " days";
      //				if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 < 32){
      //					template += "Very PT";
      //				}else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 >= 32 && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 < 34){
      //					template += "PT";
      //				}else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 >= 34 && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 <= 36){
      //					template += "Late PT";
      //				}else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 >= 37 && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 < 38){
      //					template += "Early PT";
      //				}else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 >= 38 && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 < 42){
      //					template += "Term";
      //				}else if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks*1 >= 42){
      //					template += "Post Term";
      //				}
      //				template +=" ("+this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks + "weeks";
      //				if(this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays != null && this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays*1 != 0){
      //					template +=" " + this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays + "days";
      //				}
      //				template +=")";
    }

    if(this.admitPatientModel.babyDetailObj.gender != "" && this.admitPatientModel.babyDetailObj.gender != null && this.admitPatientModel.babyDetailObj.gender != undefined){
      template += "/"+ this.admitPatientModel.babyDetailObj.gender;
    }


    if(this.admitPatientModel.babyDetailObj.birthweight != null){
      if(this.admitPatientModel.babyDetailObj.weight_galevel != "" && this.admitPatientModel.babyDetailObj.weight_galevel != null && this.admitPatientModel.babyDetailObj.weight_galevel != undefined){
        template += "/"+this.admitPatientModel.babyDetailObj.weight_galevel;
      }
      //				if(this.admitPatientModel.babyDetailObj.birthweight*1 < 1000){
      //					template += "/ELBW";
      //				}else if(this.admitPatientModel.babyDetailObj.birthweight*1 >= 1000 && this.admitPatientModel.babyDetailObj.birthweight*1 < 1500){
      //					template += "/VLBW";
      //				}else if(this.admitPatientModel.babyDetailObj.birthweight*1 >= 1500 && this.admitPatientModel.babyDetailObj.birthweight*1 < 2500){
      //					template += "/LBW";
      //				}else if(this.admitPatientModel.babyDetailObj.birthweight*1 >= 2500){
      //					template += "/NBW";
      //				}
    }

    if(this.admitPatientModel.admissionNotesObj.reason_admission != null && this.admitPatientModel.admissionNotesObj.reason_admission != undefined && this.admitPatientModel.admissionNotesObj.reason_admission != ""){
      template +="/"+this.admitPatientModel.admissionNotesObj.reason_admission;
    }
    /*if(this.admitPatientModel.babyDetailObj.weight_galevel != null){
    template += "/" + this.admitPatientModel.babyDetailObj.weight_galevel;
  }*/

  if(this.tempSE.jaundiceDone == true){
    template += "/Jaundice";
  }
  if(this.tempSE.respSystemDone == true){
    if(this.tempSE.rdsDone == true){
      template += "/Respiratory Distress";
    }
    if(this.tempSE.apneaDone == true){
      template += "/Apnea";
    }
    if(this.tempSE.pphnDone == true){
      template += "/PPHN";
    }
    if(this.tempSE.pneumothoraxDone == true){
      template += "/Pneumothorax";
    }
  }
  if(this.tempSE.infectionDone == true){
    if(this.tempSE.sepsisDone == true){
      template += "/Sepsis";
    }
    if(this.tempSE.vapDone == true){
      template += "/VAP";
    }
    if(this.tempSE.clabsiDone == true){
      template += "/CLABSI";
    }
  }
  if(this.tempSE.cnsDone == true){
    if(this.tempSE.asphyxiaDone == true){
      template += "/Asphyxia";
    }
    if(this.tempSE.seizuresDone == true){
      template += "/Seizures";
    }
    if(this.tempSE.ivhDone == true){
      template += "/IVH";
    }
  }

  this.admitPatientModel.admissionNotesObj.diagnosis = template;
}


createprogressNotesSystematic = function(eventName){

  var systematicTemplateStr = "";
  if(this.admitPatientModel.admissionNotesObj.systemic_exam == null || this.admitPatientModel.admissionNotesObj.systemic_exam == undefined){
    if (eventName == "resp"){
      systematicTemplateStr = "Respiratory system is normal.";
    } else if (eventName == "jaundice"){
      systematicTemplateStr = "Baby has no significant jaundice.";
    } else if (eventName == "cns"){
      systematicTemplateStr = "CNS examination is normal.";
    } else if (eventName == "infection"){
      systematicTemplateStr = "There is no evidence of infection.";
    } else if (eventName == "metabolic"){
      systematicTemplateStr = "There is no metabolic abnormaility.";
    }
  } else {
    if (eventName == "resp" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Respiratory system is normal.") == -1){
      systematicTemplateStr = "Respiratory system is normal.";
    } else if (eventName == "jaundice" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Baby has no significant jaundice.") == -1){
      this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("Baby has jaundice(no ASSESSMENT was done).","");
      systematicTemplateStr = "Baby has no significant jaundice.";
    } else if (eventName == "cns" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("CNS examination is normal.") == -1){
      systematicTemplateStr = "CNS examination is normal.";
    } else if (eventName == "infection" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("There is no evidence of infection.") == -1){
      systematicTemplateStr = "There is no evidence of infection.";
    } else if (eventName == "metabolic" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("There is no metabolic abnormaility.") == -1){
      systematicTemplateStr = "There is no metabolic abnormaility.";
    }
  }

  if(this.admitPatientModel.admissionNotesObj.systemic_exam == null || this.admitPatientModel.admissionNotesObj.systemic_exam == '') {
    this.admitPatientModel.admissionNotesObj.systemic_exam = systematicTemplateStr;
  } else {
    this.admitPatientModel.admissionNotesObj.systemic_exam += "\n" + systematicTemplateStr;
  }
}

showOkPopUp = function(eventName) {
  if(this.admitPatientModel.admissionNotesObj.systemic_exam != null && this.admitPatientModel.admissionNotesObj.systemic_exam != undefined){
    if (eventName.toLowerCase() == "jaundice" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Baby has no significant jaundice.") != -1){
      this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("Baby has no significant jaundice.","Baby has jaundice(no ASSESSMENT was done).");
    }
  }

  this.eventName = eventName;
  $("#OkCancelPopUp").addClass("showing");
  $("#redirectOverlay").addClass("show");
};

closeOkPopUp = function(){
  $("#OkCancelPopUp").removeClass("showing");
  $("#redirectOverlay").removeClass("show");
};

clickEdit = function(){
  this.isEditProfile = !this.isEditProfile;
}

respSystemNo = function () {
  this.admitPatientModel.sysAssessmentObj.rds = false;
  this.admitPatientModel.sysAssessmentObj.apnea = false;
  this.admitPatientModel.sysAssessmentObj.pphn = false;
  this.admitPatientModel.sysAssessmentObj.pneumothorax = false;
  this.createprogressNotesSystematic('resp');
}

infectionNo = function () {
  this.admitPatientModel.sysAssessmentObj.sepsis = false;
  this.admitPatientModel.sysAssessmentObj.vap = false;
  this.admitPatientModel.sysAssessmentObj.clabsi = false;
  this.createprogressNotesSystematic('infection');
}

cnsNo = function () {
  this.admitPatientModel.sysAssessmentObj.asphyxia = false;
  this.admitPatientModel.sysAssessmentObj.seizures = false;
  this.admitPatientModel.sysAssessmentObj.ivh = false;
  this.createprogressNotesSystematic('cns');
}

redirectToAssessment = function() {
  this.redirectSetLocalStorageVariable();
  if(this.eventName == 'jaundice') {
    this.router.navigateByUrl('/jaundice/assessment-sheet-jaundice');
  } else if(this.eventName == 'Respiratory Distress' || this.eventName == 'Apnea' || this.eventName == 'PPHN' || this.eventName == 'Pneumothorax') {
    this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
  } else if(this.eventName == 'Sepsis' || this.eventName == 'VAP' || this.eventName == 'CLABSI') {
    this.router.navigateByUrl('/infection/assessment-sheet-infection');
  } else if(this.eventName == 'Asphyxia' || this.eventName == 'Seizures' || this.eventName == 'IVH') {
    this.router.navigateByUrl('/cns/assessment-sheet-cns');
  }
}

redirectSetLocalStorageVariable = function() {
  let lsObj = {
    "isEditButtonVisible": this.isEditButtonVisible,
    "isEditProfile" : this.isEditProfile,
    "nonEditable" : this.nonEditable,
    "uhidInvalid" : this.uhidInvalid,
    "fentonRangePopupFlag" : this.fentonRangePopupFlag,
    "fentonGestationPopupFlag" : this.fentonGestationPopupFlag,
    "fentonGenderPopupFlag" : this.fentonGenderPopupFlag,
    "dateOfAdmissionEnable" : this.dateOfAdmissionEnable,
    "timeOfBirth" : this.timeOfBirth,
    "timeOfAdmission" : this.timeOfAdmission,
    "familyIncome" : this.familyIncome,
    "dropDownData" : this.dropDownData,
    "admitPatientModel" : this.admitPatientModel,
    "validation" : this.validation,
    "childObject" : this.childObject,
    "eventName" : this.eventName,
    "antenatalTempObj" : this.antenatalTempObj,
    "otherReasonOfAdmission" : this.otherReasonOfAdmission,
    "tempSE" : this.tempSE,
    "whichTabSystematicSelected" : this.whichTabSystematicSelected
  };

  console.log(lsObj.admitPatientModel);

  // lsObj.isEditButtonVisible = this.isEditButtonVisible;
  // lsObj.isEditProfile = this.isEditProfile;
  // lsObj.nonEditable = this.nonEditable;
  // lsObj.uhidInvalid = this.uhidInvalid;
  // lsObj.fentonRangePopupFlag = this.fentonRangePopupFlag;
  // lsObj.fentonGestationPopupFlag = this.fentonGestationPopupFlag;
  // lsObj.fentonGenderPopupFlag = this.fentonGenderPopupFlag;
  // lsObj.dateOfAdmissionEnable = this.dateOfAdmissionEnable;
  // lsObj.timeOfBirth = this.timeOfBirth;
  // lsObj.timeOfAdmission = this.timeOfAdmission;
  // lsObj.familyIncome = this.familyIncome;
  // lsObj.dropDownData = this.dropDownData;
  // lsObj.admitPatientModel = this.admitPatientModel;
  // lsObj.validation = this.validation;
  // lsObj.childObject = this.childObject;
  // lsObj.eventName  = this.eventName;
  // lsObj.antenatalTempObj = this.antenatalTempObj;
  // lsObj.otherReasonOfAdmission = this.otherReasonOfAdmission;
  // lsObj.tempSE = this.tempSE;
  // lsObj.whichTabSystematicSelected = this.whichTabSystematicSelected;

  localStorage.setItem('eventName',JSON.stringify(this.eventName));
  localStorage.setItem('admissionObj',JSON.stringify(lsObj));
  localStorage.setItem('admissionform',JSON.stringify(true));
  localStorage.setItem('isComingFromView',JSON.stringify(this.isEditButtonVisible));
}

redirectInit = function(){
  var lsObj = JSON.parse(localStorage.getItem('admissionObj'));
  if(lsObj != null) {
    this.tabValue.admissionSystem = 'initial';
    this.tabValue.whichInitialTab = 'Systemic';

    this.isEditButtonVisible = lsObj.isEditButtonVisible;
    this.isEditProfile = lsObj.isEditProfile;
    this.nonEditable = lsObj.nonEditable;
    this.uhidInvalid = lsObj.uhidInvalid;
    this.fentonRangePopupFlag = lsObj.fentonRangePopupFlag;
    this.fentonGestationPopupFlag = lsObj.fentonGestationPopupFlag;
    this.fentonGenderPopupFlag = lsObj.fentonGenderPopupFlag;
    this.dateOfAdmissionEnable = lsObj.dateOfAdmissionEnable;
    this.timeOfBirth = lsObj.timeOfBirth;
    this.timeOfAdmission = lsObj.timeOfAdmission;
    this.familyIncome = lsObj.familyIncome;
    this.dropDownData = lsObj.dropDownData;
    this.admitPatientModel = lsObj.admitPatientModel;
    this.validation = lsObj.validation;
    this.childObject = lsObj.childObject;
    this.eventName = lsObj.eventName;
    this.antenatalTempObj = lsObj.antenatalTempObj;
    this.otherReasonOfAdmission = lsObj.otherReasonOfAdmission;
    this.tempSE = lsObj.tempSE;
    this.whichTabSystematicSelected = lsObj.whichTabSystematicSelected;

    if((this.admitPatientModel.antenatalHistoryObj.eddTimestamp != null && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.eddTimestamp != "") && typeof(this.admitPatientModel.antenatalHistoryObj.eddTimestamp) === 'string') {
      this.admitPatientModel.antenatalHistoryObj.eddTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.eddTimestamp);
    }

    if((this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != null && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.lmpTimestamp != "") && typeof(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp) === 'string') {
      this.admitPatientModel.antenatalHistoryObj.lmpTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.lmpTimestamp);
    }

    if((this.admitPatientModel.antenatalHistoryObj.etTimestamp != null && this.admitPatientModel.antenatalHistoryObj.etTimestamp != undefined && this.admitPatientModel.antenatalHistoryObj.etTimestamp != "") && typeof this.admitPatientModel.antenatalHistoryObj.etTimestamp === 'string') {
			this.admitPatientModel.antenatalHistoryObj.etTimestamp = new Date(this.admitPatientModel.antenatalHistoryObj.etTimestamp);
		}

    if(this.eventName == 'Jaundice') {
      this.tempSE.jaundiceDone = true;
    } else if(this.eventName == 'Respiratory Distress') {
      this.tempSE.respSystemDone = true;
      this.tempSE.rdsDone = true;
    } else if(this.eventName == 'Apnea') {
      this.tempSE.respSystemDone = true;
      this.tempSE.apneaDone = true;
    } else if(this.eventName == 'PPHN') {
      this.tempSE.respSystemDone = true;
      this.tempSE.pphnDone = true;
    } else if(this.eventName == 'Pneunothorax') {
      this.tempSE.respSystemDone = true;
      this.tempSE.pneumothoraxDone = true;
    } else if(this.eventName == 'Sepsis') {
      this.tempSE.infectionDone = true;
      this.tempSE.sepsisDone = true;
    } else if(this.eventName == 'VAP') {
      this.tempSE.infectionDone = true;
      this.tempSE.vapDone = true;
    } else if(this.eventName == 'CLABSI') {
      this.tempSE.infectionDone = true;
      this.tempSE.clabsiDone = true;
    } else if(this.eventName == 'Asphyxia') {
      this.tempSE.cnsDone = true;
      this.tempSE.asphyxiaDone = true;
    } else if(this.eventName == 'Seizures') {
      this.tempSE.cnsDone = true;
      this.tempSE.seizuresDone = true;
    } else if(this.eventName == 'IVH') {
      this.tempSE.cnsDone = true;
      this.tempSE.ivhDone = true;
    }
    this.populateSystemicDiagnosis();
    if(localStorage.getItem('assessmentComment') != null) {
      if(this.admitPatientModel.admissionNotesObj.systemic_exam == null || this.admitPatientModel.admissionNotesObj.systemic_exam == '') {
        this.admitPatientModel.admissionNotesObj.systemic_exam = this.eventName + ": " + JSON.parse(localStorage.getItem('assessmentComment')).trim();
      } else {
        if (this.eventName == "Jaundice" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Baby has no significant jaundice.") != -1){
          this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("Baby has no significant jaundice.","");
        }
        if (this.eventName == "Jaundice" && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Baby has jaundice(no ASSESSMENT was done).") != -1){
          this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("Baby has jaundice(no ASSESSMENT was done).","");
        }

        if((this.eventName == 'Respiratory Distress' || this.eventName == 'Apnea' || this.eventName == 'PPHN' || this.eventName == 'Pneumothorax') && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("Respiratory system is normal.") != -1) {
            this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("Respiratory system is normal.","");
        }

        if((this.eventName == 'Sepsis' || this.eventName == 'VAP' || this.eventName == 'CLABSI') && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("There is no evidence of infection.") != -1) {
            this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("There is no evidence of infection.","");
        }

        if((this.eventName == 'Asphyxia' || this.eventName == 'Seizures' || this.eventName == 'IVH') && this.admitPatientModel.admissionNotesObj.systemic_exam.indexOf("CNS examination is normal.") != -1) {
            this.admitPatientModel.admissionNotesObj.systemic_exam = this.admitPatientModel.admissionNotesObj.systemic_exam.replace("CNS examination is normal.","");
        }

        this.admitPatientModel.admissionNotesObj.systemic_exam = this.eventName + ": " + JSON.parse(localStorage.getItem('assessmentComment')).trim() + "\n" + this.admitPatientModel.admissionNotesObj.systemic_exam;
      }
    }
    this.populateSystemicDiagnosis();
    this.submitRegistrationForm(false,'initial');
  } else {
    console.log("Edit flag error !!");
    this.router.navigateByUrl('/dashboard');
  }
  localStorage.removeItem('admissionObj');
}

ballardOpen = function() {
  console.log("ballard initiated");
  $("#ballardOverlay").css("display", "block");
  $("#ballardScorePopup").addClass("showing");
};
closeModalBallard = function() {
  console.log("apgar closing");
  $("#ballardOverlay").css("display", "none");
  $("#ballardScorePopup").toggleClass("showing");
  if(this.admitPatientModel.ballardFlag == false){
    this.admitPatientModel.ballardFlag = false;
    this.admitPatientModel.ballardObj.posture = null;
    this.admitPatientModel.ballardObj.squareWindow = null;
    this.admitPatientModel.ballardObj.armRecoi = null;
    this.admitPatientModel.ballardObj.poplitealAngle = null;
    this.admitPatientModel.ballardObj.scarfSign = null;
    this.admitPatientModel.ballardObj.healtoear = null;

    this.admitPatientModel.ballardObj.skin = null;
    this.admitPatientModel.ballardObj.lanugo = null;
    this.admitPatientModel.ballardObj.plantarSurface = null;
    this.admitPatientModel.ballardObj.breast = null;
    this.admitPatientModel.ballardObj.eyeEar = null;
    this.admitPatientModel.ballardObj.genitalMale = null;
    this.admitPatientModel.ballardObj.genitalFemale = null;
    this.admitPatientModel.ballardObj.totalScore = null;
    this.admitPatientModel.ballardObj.neroScore = null;
    this.admitPatientModel.ballardObj.physicalScore = null;

    $("#ballardPhysical").css("display","none");
    $("#ballardBoth").css("display","none");
    $("#ballardNeuro").css("display","none");
  }else{
    this.admitPatientModel.ballardFlag = true;
  }
};

addingBallardScore = function(){

  var neuroValid = true;
  var allNeuroTabsFilled = true;
  var bothFilled = true;
  var physicalValid = true;
  if(this.tabContentBallard == "neuro" || this.tabContentBallard == "physical"){
    if(this.admitPatientModel.ballardObj.posture == null || this.admitPatientModel.ballardObj.squareWindow == null || this.admitPatientModel.ballardObj.armRecoi == null || this.admitPatientModel.ballardObj.poplitealAngle == null || this.admitPatientModel.ballardObj.scarfSign == null || this.admitPatientModel.ballardObj.healtoear == null){
      $("#ballardPhysical").css("display","none");
      $("#ballardBoth").css("display","none");
      $("#ballardNeuro").css("display","inline-block");
      neuroValid = false;
      physicalValid = false;
      allNeuroTabsFilled = false;
    }
    if(this.admitPatientModel.ballardObj.skin == null || this.admitPatientModel.ballardObj.lanugo == null || this.admitPatientModel.ballardObj.plantarSurface == null || this.admitPatientModel.ballardObj.breast == null || this.admitPatientModel.ballardObj.eyeEar == null || (this.admitPatientModel.babyDetailObj.gender == "Male" && this.admitPatientModel.ballardObj.genitalMale == null) || (this.admitPatientModel.babyDetailObj.gender == "Female" && this.admitPatientModel.ballardObj.genitalFemale == null)){
      if(allNeuroTabsFilled == false){
        $("#ballardNeuro").css("display","none");
        $("#ballardPhysical").css("display","none");
        $("#ballardBoth").css("display","inline-block");
        neuroValid = false;
        physicalValid = false;
      }
      else{
        $("#ballardNeuro").css("display","none");
        $("#ballardBoth").css("display","none");
        $("#ballardPhysical").css("display","inline-block");
        physicalValid = false;
        neuroValid = false;
      }
    }
  }
  if(neuroValid == false || physicalValid == false){
    $("#ballardPhysical").css("display","none");
    $("#ballardBoth").css("display","inline-block");
    $("#ballardNeuro").css("display","none");
  }


  if(neuroValid == true && physicalValid == true){
    $("#ballardPhysical").css("display","none");
    $("#ballardBoth").css("display","none");
    $("#ballardNeuro").css("display","none");

    this.admitPatientModel.ballardFlag = true;
    this.closeModalBallard();
    this.calBallardGest();
  }
  else{
    this.admitPatientModel.ballardFlag = false;
  }

}


//	Below Code is for integrating the Ballard Score (Neuromuscular)
totalNeuroValue = function(neuroValue){
this.admitPatientModel.ballardObj.neroScore = 0;
if(this.admitPatientModel.ballardObj.posture != null) {
  this.admitPatientModel.ballardObj.neroScore += parseInt(this.admitPatientModel.ballardObj.posture);
}

if(this.admitPatientModel.ballardObj.squareWindow != null) {
  this.admitPatientModel.ballardObj.neroScore += parseInt(this.admitPatientModel.ballardObj.squareWindow);
}

if(this.admitPatientModel.ballardObj.armRecoi != null) {
  this.admitPatientModel.ballardObj.neroScore += parseInt(this.admitPatientModel.ballardObj.armRecoi);
}

if(this.admitPatientModel.ballardObj.poplitealAngle != null) {
  this.admitPatientModel.ballardObj.neroScore += parseInt(this.admitPatientModel.ballardObj.poplitealAngle);
}

if(this.admitPatientModel.ballardObj.scarfSign != null) {
  this.admitPatientModel.ballardObj.neroScore += parseInt(this.admitPatientModel.ballardObj.scarfSign);
}

if(this.admitPatientModel.ballardObj.healtoear != null) {
  this.admitPatientModel.ballardObj.neroScore += parseInt(this.admitPatientModel.ballardObj.healtoear);
}

this.admitPatientModel.ballardObj.totalScore = this.admitPatientModel.ballardObj.neroScore + this.admitPatientModel.ballardObj.physicalScore;
}

totalPhysicalValue = function(value){
  this.admitPatientModel.ballardObj.physicalScore = 0;

  if(this.admitPatientModel.ballardObj.skin != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.skin);
  }

  if(this.admitPatientModel.ballardObj.lanugo != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.lanugo);
  }

  if(this.admitPatientModel.ballardObj.plantarSurface != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.plantarSurface);
  }

  if(this.admitPatientModel.ballardObj.breast != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.breast);
  }

  if(this.admitPatientModel.ballardObj.eyeEar != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.eyeEar);
  }

  if(this.admitPatientModel.ballardObj.genitalMale != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.genitalMale);
  }

  if(this.admitPatientModel.ballardObj.genitalFemale != null) {
    this.admitPatientModel.ballardObj.physicalScore += parseInt(this.admitPatientModel.ballardObj.genitalFemale);
  }

  this.admitPatientModel.ballardObj.totalScore = this.admitPatientModel.ballardObj.neroScore + this.admitPatientModel.ballardObj.physicalScore;
}

calBallardGest = function() {
  if(this.admitPatientModel.ballardObj.totalScore != undefined && this.admitPatientModel.ballardObj.totalScore != null) {
    console.log("in calBallardGest");
    var value = (2 * this.admitPatientModel.ballardObj.totalScore) + 120;
    var daysFactor = value % 5;
    var gestWeeks = (value - daysFactor) / 5;
    var gestDays = Math.round(daysFactor * 1.4);
    this.admitPatientModel.antenatalHistoryObj.gestationbyLmpWeeks = gestWeeks;
    this.admitPatientModel.antenatalHistoryObj.gestationbyLmpDays = gestDays;
  }
}

reflect=function(e)
{
  if(e.target.checked)
  {
    this.admitPatientModel.babyDetailObj.admissionWeight = this.admitPatientModel.babyDetailObj.birthweight;
    this.admitPatientModel.babyDetailObj.admissionLength = this.admitPatientModel.babyDetailObj.birthlength;
    this.admitPatientModel.babyDetailObj.admissionHeadCircumference = this.admitPatientModel.babyDetailObj.birthheadcircumference;
    this.admitPatientModel.babyDetailObj.admissionWeightCentile = this.admitPatientModel.babyDetailObj.weight_centile;
    this.admitPatientModel.babyDetailObj.admissionLengthCentile = this.admitPatientModel.babyDetailObj.length_centile;
    this.admitPatientModel.babyDetailObj.admissionHCcentile = this.admitPatientModel.babyDetailObj.hc_centile;
    this.admitPatientModel.babyDetailObj.admissionWeightGAlevel = this.admitPatientModel.babyDetailObj.weight_galevel;
    this.admitPatientModel.babyDetailObj.admissionLengthGaLevel = this.admitPatientModel.babyDetailObj.length_galevel;
    this.admitPatientModel.babyDetailObj.admissionHCgaLevel = this.admitPatientModel.babyDetailObj.hc_galevel;
  }
  if(!(e.target.checked))
  {
    this.admitPatientModel.babyDetailObj.admissionWeight = null;
    this.admitPatientModel.babyDetailObj.admissionLength = null;
    this.admitPatientModel.babyDetailObj.admissionHeadCircumference = null;
    this.admitPatientModel.babyDetailObj.admissionWeightCentile = null;
    this.admitPatientModel.babyDetailObj.admissionLengthCentile = null;
    this.admitPatientModel.babyDetailObj.admissionHCcentile = null;
    this.admitPatientModel.babyDetailObj.admissionWeightGAlevel = null;
    this.admitPatientModel.babyDetailObj.admissionLengthGaLevel = null;
    this.admitPatientModel.babyDetailObj.admissionHCgaLevel = null;
  }
  this.populateAdmissionStatusStr();
}

neoruInfo = function(infoValue){
  console.log(infoValue);
  if(infoValue == "posture"){
    console.log("posture");
    // showModal("Posture : The infant is placed supine (if found prone) and the examiner waits until the infant settles into a relaxed or preferred posture. If the infant is found supine, gentle manipulation (flex if extended; extend if flexed) of the extremities will allow the infant to seek the baseline position of comfort.");
    $("#postureOverlay").css("display", "block");
    $("#posturePopup").addClass("showing");
  }
  if(infoValue == "squareWindow"){
    //    showModal("Square Window : Wrist flexibility and/or resistance to extensor stretching are responsible for the resulting angle of flexion at the wrist. The examiner straightens the infant's fingers and applies gentle pressure on the dorsum of the hand, close to the fingers. From extremely pre-term to post-term, the resulting angle between the palm of the infant hand and forearm is estimated.The appropriate square on the score sheet is selected");
    $("#sqWindowOverlay").css("display", "block");
    $("#sqWindowPopup").addClass("showing");
  }
  if(infoValue== "armRecoil"){
    //showModal("Arm Recoil : This maneuver focuses on passive flexor tone of the biceps muscle by measuring the angle of recoil following very brief extension of the upper extremity. With the infant lying supine, the examiner places one hand beneath the infant elbow for support. Taking the infant's hand, the examiner briefly sets the elbow in flexion, then momentarily extends the arm before releasing the hand. The angle of recoil to which the forearm springs back into flexion is noted, and the appropriate square is selected on the score sheet. The extremely pre-term infant will not exhibit any arm recoil. Square #4 is selected only if there is contact between the infant's fist and face. This is seen in term and post term infants. Care must be taken not to hold the arm in the extended position for a prolonged period, as this causes flexor fatigue and results in a falsely low score due to poor flexor recoil.");
    $("#armOverlay").css("display", "block");
    $("#armPopup").addClass("showing");
  }
  if(infoValue== "popliteal"){
    //showModal("Popliteal Angle : This maneuver assesses maturation of passive flexor tone about the knee joint by testing for resistance to extension of the lower extremity. With the infant lying supine, and with diaper re-moved, the thigh is placed gently on the infant's abdomen with the knee fully flexed. After the infant has relaxed into this position, the examiner gently grasps the foot at the sides with one hand while supporting the side of the thigh with the other. Care is taken not to exert pressure on the hamstrings, as this may interfere with their function. The leg is extended until a definite resistance to extension is appreciated. In some infants, hamstring contraction may be visualized during this maneuver. At this point the angle formed at the knee by the upper and lower leg is measured. Note: a) It is important that the examiner wait until the infant stops kicking actively before extending the leg. b) The prenatal frank breech position will interfere with this maneuver for the first 24 to 48 hours of age due to prolonged intrauterine flexor fatigue. The test should be repeated once recovery has occurred; alternately, a score similar to those obtained for other items in the exam may be assigned.");
    $("#poplitealOverlay").css("display", "block");
    $("#poplitealPopup").addClass("showing");
  }
  if(infoValue== "scarf"){
    //showModal("Scarf Sign : This maneuver tests the passive tone of the flexors about the shoulder girdle. With the infant lying supine, the examiner adjusts the infant's head to the midline and supports the infant's hand across the upper chest with one hand. the thumb of the examiner's other hand is placed on the infant's elbow. The examiner nudges the elbow across the chest, felling for passive flexion or resistance to extension of posterior shoulder girdle flexor muscles. The point on the chest to which the elbow moves easily prior to significant resistance is noted. Landmarks noted in order of increasing maturity are: full scarf at the level of the neck (-1); contralateral axillary line (0); contralateral nipple line (1); xyphoid process (2); ipsilateral nipple line (3); and ipsilateral axillary line (4).");
    $("#scarfOverlay").css("display", "block");
    $("#scarfPopup").addClass("showing");
  }
  if(infoValue== "healToear"){
    //showModal("Heal to Ear : This maneuver measures passive flexor tone about the pelvic girdle by testing for passive flexion or resistance to extension of posterior hip flexor muscles. The infant is placed supine and the flexed lower extremity is brought to rest on the mattress alongside the infant's trunk. The examiner supports the infant's thigh laterally alongside the body with the palm of one hand. The other hand is used to grasp the infant's foot at the sides and to pull it toward the ipsilateral ear. The examiner fells for resistance to extension of the posterior pelvic girdle flexors and notes the location of the heel where significant resistance is appreciated. Landmarks noted in order of increasing maturity include resistance felt when the heel is at or near the: ear     (-1); nose (0); chin level (1); nipple line (2); umbilical area (3); and femoral crease (4).");
    $("#healToEarOverlay").css("display", "block");
    $("#healToEarPopup").addClass("showing");
  }
}

closePosture = function(){
  $("#postureOverlay").css("display", "none");
  $("#posturePopup").removeClass("showing");
}
closeSquare = function(){
  $("#sqWindowOverlay").css("display", "none");
  $("#sqWindowPopup").removeClass("showing");
}
closeArm = function(){
  $("#armOverlay").css("display", "none");
  $("#armPopup").removeClass("showing");
}
closePopliteal = function(){
  $("#poplitealOverlay").css("display", "none");
  $("#poplitealPopup").removeClass("showing");
}
closeScarf = function(){
  $("#scarfOverlay").css("display", "none");
  $("#scarfPopup").removeClass("showing");
}
closeHealToEar = function(){
  $("#healToEarOverlay").css("display", "none");
  $("#healToEarPopup").removeClass("showing");
}
// end ballard



//for date conversion

dateformatter = function(date :Date, inFormat : string, outFormat :string){
  if(inFormat == "gmt"){
    if (outFormat == "utf")
    {
      // let tzoffset : any = (new Date(date)).getTimezoneOffset() * 60000; //offset in milliseconds
      // let dateStrObj : number= new Date(date) - tzoffset;
      // let localISOTime : any = (new Date(dateStrObj)).toISOString().slice(0,-1);
      // localISOTime = localISOTime+"Z";
      // return localISOTime;
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
    else if(outFormat == "default"){
      let newdate = new Date(date);
      let tempDate = newdate+"";
      let dd : any = newdate.getDate();
      console.log(typeof(tempDate));
      let mm : any = tempDate.substring(3, 7);

      let yy = newdate.getFullYear();
      let h = (newdate.getHours() > 12) ? newdate.getHours() % 12 : newdate.getHours();
      let P = (newdate.getHours() >= 12) ? 'PM' : 'AM';
      let m = newdate.getMinutes();
      if ( dd < 10 ){
        dd = '0'+dd;
      }
      return dd+" "+mm+" "+ yy+" "+h+":"+m+" "+P ;
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
printAdmissionForm(){
  localStorage.setItem('admissionformdata', JSON.stringify(this.admitPatientModel));
  localStorage.setItem('dropDownData', JSON.stringify(this.dropDownData));
  localStorage.setItem('tempBedArray', JSON.stringify(this.tempBedArray));
  localStorage.setItem('isCommingFrom', JSON.stringify(this.isEditProfile));

  this.router.navigateByUrl('/admisprint/admissionfrom-print');

}

validatesGPAL(value,sc){

 	if(value<0||value>99){
    	if(sc == 'gScore')
      		this.admitPatientModel.antenatalHistoryObj.gScore=null;

    	if(sc == 'pScore')
      		this.admitPatientModel.antenatalHistoryObj.pScore=null;

    	if(sc == 'aScore')
      		this.admitPatientModel.antenatalHistoryObj.aScore=null;

    	if(sc == 'lScore')
      		this.admitPatientModel.antenatalHistoryObj.lScore=null;

	}
}
ngOnInit() {
}

}
