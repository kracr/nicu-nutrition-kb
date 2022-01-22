import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { DischargedSearch } from './dischargedSearch';
import { Patientdata } from './dischargedPatientData';
import { APIurl } from '../../../model/APIurl';
import * as $ from 'jquery';
import { TimeOfBirth } from './timeOfBirth';
import { Router } from '@angular/router';
import { TimeOfAdmission } from './timeOfAdmission';
import { PrintPatientObj } from './printPatientObj';

@Component({
  selector: 'dischargepatients',
  templateUrl: './dischargepatients.component.html',
  styleUrls: ['./dischargepatients.component.css']
})
export class DischargepatientsComponent implements OnInit {
  dischargedSearch : DischargedSearch;
  dischargedPatientData : Array<Patientdata>;
  maxDate : Date;
  minDate : Date;
  message : string;
  http: Http;
  apiData : APIurl;
  selectedChild : any;
  status : any;
  bedData : any;
  roomArr : any;
  timeOfBirth : TimeOfBirth;
  timeOfAdmission : TimeOfAdmission;
  oneDay : any;
  loggedInUserObj : any;
  nonEditable : boolean;
  value : any;
  checkFlag : boolean;
  tobToaError : boolean;
  showErrorLabel : boolean;
  printPatientObj : PrintPatientObj;
  admitPatientModel : any;
  dropDownData : any;
  isBed : string;
  branchName : string;

  constructor(http: Http , private router: Router) {
    this.apiData = new APIurl();
    this.dischargedSearch = new DischargedSearch();
    this.dischargedPatientData = new Array<Patientdata>();
    this.http = http;
    this.oneDay = 24 * 60 * 60 * 1000;
    this.loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
    this.printPatientObj = new PrintPatientObj();
    this.message = "DOA should be Greater then or equal to DOB";
    this.nonEditable = true;
    this.isBed = 'No';
    this.timeOfAdmission = new TimeOfAdmission();
    this.timeOfBirth = new TimeOfBirth();
    console.log(this.dischargedSearch);
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    this.init();
  }

  init() {
    this.maxDate = new Date();
    this.getDischargedListData();
  };

  readmitPatientPopup = function(uhid, dischargeDate,isbabyininicu) {
    this.isBed = 'No';
    if(isbabyininicu==true){
      alert("Baby is already in iNICU");
    }else {
      $("#ballardOverlay").css("display", "block");
      $("#reAdmitPopup").addClass("showing");
      this.getDropdowns(uhid);
    }
  }

  closeReAdmitPopup = function() {
    $("#ballardOverlay").css("display", "none");
    $("#reAdmitPopup").toggleClass("showing");
  }

  // below function is used to finding the index of the room no value from the dropdown value
  roomChange = function() {
  this.admitPatientModel.babyDetailObj.nicubedno = "";
  for (var i = 0; i < this.dropDownData.nicuRooms.length; i++) {
    if (this.dropDownData.nicuRooms[i].room.key == this.admitPatientModel.babyDetailObj.nicuroomno) {
      this.value = i;
      this.isBed = 'Yes';
      break;
    }
  }
}

getDropdowns = function(uhid) {
  try{
    this.http.request(this.apiData.getDropDownsAdmission + this.branchName + "/")
    .subscribe((res: Response) => {
      this.dropDownData = res.json();
      this.getAdvanceAdmissionFormReadmit(uhid);
    });
  }catch(e){
    console.log("Exception in http service:"+e);
  };
}

getAdvanceAdmissionFormReadmit = function(uhid) {
  console.log("comming into the getAdvanceAdmissionFormReadmit");
  try{
    this.http.post(this.apiData.getAdvanceAdmissionFormReadmit + "/" + uhid + "/",
  ).subscribe(res => {
    this.admitPatientModel = res.json();
    console.log(this.admitPatientModel);
    this.convertDBtoJS();
    this.calDayLife();
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


convertDBtoJS = function(){
  console.log('in convertDBtoJS');
  this.admitPatientModel.babyDetailObj.dobObj = new Date(this.admitPatientModel.babyDetailObj.dateofbirth);
  this.admitPatientModel.babyDetailObj.doaObj = new Date();

  if(!(this.admitPatientModel.babyDetailObj.timeofbirth == undefined || this.admitPatientModel.babyDetailObj.timeofbirth == null
    || this.admitPatientModel.babyDetailObj.timeofbirth == "")){
      var timeBirthArr = [];
      timeBirthArr = this.admitPatientModel.babyDetailObj.timeofbirth.split(',');

      this.timeOfBirth.hours = timeBirthArr[0];
      this.timeOfBirth.minutes = timeBirthArr[1];
      this.timeOfBirth.meridium = timeBirthArr[2];
    }
  }


  calDayLife = function() {
    var firstDate = new Date();
    var secondDate = new Date(this.admitPatientModel.babyDetailObj.dobObj);
    var diffDays = Math.ceil(Math.abs((firstDate.getTime() - secondDate.getTime()) / (this.oneDay)));
    this.admitPatientModel.babyDetailObj.dayoflife = diffDays + "";
    this.validateTobToa();
  }


  validateTobToa = function() {
    this.tobToaError = false;
    if(!(this.admitPatientModel.babyDetailObj.dobObj == null || this.admitPatientModel.babyDetailObj.doaObj == null)) {
      if(new Date(this.admitPatientModel.babyDetailObj.dobObj).getTime() == new Date(this.admitPatientModel.babyDetailObj.doaObj).getTime()) {
        if(!((this.timeOfBirth.hours == undefined || this.timeOfBirth.hours == null)
        || (this.timeOfBirth.minutes == undefined || this.timeOfBirth.minutes == null)
        || (this.timeOfBirth.meridium == undefined || this.timeOfBirth.meridium == null))) {
          if(!((this.timeOfAdmission.hours == undefined || this.timeOfAdmission.hours == null)
          || (this.timeOfAdmission.minutes == undefined || this.timeOfAdmission.minutes == null)
          || (this.timeOfAdmission.meridium == undefined || this.timeOfAdmission.meridium == null))){
            this.checkFlag = false;
            if(this.timeOfBirth.meridium == "AM") {
              if(this.timeOfAdmission.meridium == "AM") {
                this.checkFlag = true;
              }
            } else if(this.timeOfBirth.meridium == "PM") {
              if(this.timeOfAdmission.meridium == "AM") {
                this.tobToaError = true;
              } else {
                this.checkFlag = true;
              }
            }
            if(this.checkFlag) {
              var birthHours = this.timeOfBirth.hours * 1;
              var addmissionHours = this.timeOfAdmission.hours * 1;

              if(this.timeOfBirth.hours == "12") {
                birthHours = 0;
              }

              if(this.timeOfAdmission.hours == "12") {
                addmissionHours = 0;
              }

              if(birthHours > addmissionHours) {
                this.tobToaError = true;
              } else if((birthHours == addmissionHours)
              && (this.timeOfBirth.minutes * 1 > this.timeOfAdmission.minutes * 1)) {
                this.tobToaError = true;
              }
            }
          }
        }
      }
    }
  }


  // below function is used for sumbit the data
submitRegistrationForm = function() {
  console.log("comming into the submitRegistrationForm");
  console.log(this.admitPatientModel);
  if(this.validate()) {
    this.admitPatientModel.babyDetailObj.doaStr = this.dateformatter(this.admitPatientModel.babyDetailObj.doaObj, "gmt", "standard");
    this.admitPatientModel.babyDetailObj.timeofadmission = this.timeOfAdmission.hours
    + "," + this.timeOfAdmission.minutes + "," + this.timeOfAdmission.meridium;

    try{
      this.http.post(this.apiData.submitAdvanceAdmissionForm + "/" +  this.loggedInUserObj.userName,this.admitPatientModel
    ).subscribe(res => {
      this.admitPatientModel = res.json();
      console.log("data from submitRegistrationForm");
      console.log(this.admitPatientModel);
      if (this.admitPatientModel.type == "Success") {
        this.router.navigate(['/dash/dashboard']);
      } else {
        alert(this.admitPatientModel.message);
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
  }else if(inFormat == "utf"){
    if (outFormat == "gmt")
    {
      var fullDate = new Date(date);
      fullDate = new Date(fullDate.getTime() + (fullDate.getTimezoneOffset() * 60000));
      return fullDate;
    }
  }
}

validate = function() {
  console.log(this.admitPatientModel);

  if(this.admitPatientModel.babyDetailObj.nicuroomno == undefined || this.admitPatientModel.babyDetailObj.nicuroomno == null || this.admitPatientModel.babyDetailObj.nicuroomno == "") {
      //document.getElementById("roomNo").focus();
      $("#emptyRoomNo").css("display","block");
      return false;
    } else {
      $("#emptyRoomNo").css("display","none");
    }

    if(this.admitPatientModel.babyDetailObj.nicubedno == undefined || this.admitPatientModel.babyDetailObj.nicubedno == null || this.admitPatientModel.babyDetailObj.nicubedno == "") {
        //document.getElementById("bedNo").focus();
        $("#emptyBedNo").css("display","block");
        return false;
      } else {
        $("#emptyBedNo").css("display","none");
      }

      if(this.timeOfAdmission.hours == undefined || this.timeOfAdmission.hours == null || this.timeOfAdmission.hours == "") {
        //document.getElementById("toahr").focus();
        $("#emptyToa").css("display","block");
        return false;
      } else if(this.timeOfAdmission.minutes == undefined || this.timeOfAdmission.minutes == null || this.timeOfAdmission.minutes == "") {
        //document.getElementById("toamin").focus();
        $("#emptyToa").css("display","block");
        return false;
      } else if(this.timeOfAdmission.meridium == undefined || this.timeOfAdmission.meridium == null || this.timeOfAdmission.meridium == "") {
        //document.getElementById("toamer").focus();
        $("#emptyToa").css("display","block");
        return false;
      } else if(this.tobToaError) {
        //document.getElementById("toahr").focus();
        $("#emptyToa").css("display","block");
        return false;
      }

      if(this.admitPatientModel.babyDetailObj.doaObj == undefined || this.admitPatientModel.babyDetailObj.doaObj == null || this.admitPatientModel.babyDetailObj.doaObj == "") {
          //document.getElementById("doa").focus();
          $("#emptyDOA").css("display","block");
          return false;
        } else if(this.showErrorLabel) {
          //document.getElementById("doa").focus();
          $("#emptyDOA").css("display","block");
          return false;
        }
        return true;
      }

      admission_Date = function() {
        if(this.admitPatientModel.babyDetailObj.doaObj == undefined) {
          //document.getElementById("doa").focus();
          this.showErrorLabel = true;
        } else if(!(this.admitPatientModel.babyDetailObj.doaObj == null || this.admitPatientModel.babyDetailObj.doaObj == "")) {
          if (this.admitPatientModel.babyDetailObj.doaObj.getTime() < this.admitPatientModel.babyDetailObj.dobObj.getTime()) {
            //document.getElementById("doa").focus();
            this.admitPatientModel.babyDetailObj.doaObj = null;
            this.showErrorLabel = true;
          }else{
            this.showErrorLabel = false;
          }
        }
        this.validateTobToa();
      }

      openCriticalityInfo = function(){
        $("#criticalityOverlay").css("display", "block");
        $("#criticalityPopup").addClass("showing");
      }
      closeCriticalityInfo = function(){
        $("#criticalityOverlay").css("display", "none");
        $("#criticalityPopup").removeClass("showing");
      }

      printDischargeSummary = function(uhid,episodeid, searchedStartDate, searchedEndDate){
        this.printPatientObj.uhid = uhid+"";
        this.printPatientObj.episodeid = episodeid+"";
        this.printPatientObj.searchedStartDate = searchedStartDate +"";
        this.printPatientObj.searchedEndDate = searchedEndDate +"";
        localStorage.setItem('printPatientObj', JSON.stringify(this.printPatientObj));
        localStorage.setItem('selectedChild.uhid', uhid);
        localStorage.setItem('searchFromDate', JSON.stringify(this.filtersObj));
        var settingRefObj = JSON.parse(localStorage.getItem('settingReference'));
        this.router.navigateByUrl('/discharge/discharge-summary-print');
      };

      getDischargedListData() {
        console.log(this.dischargedSearch);
        try
        {
          if(localStorage.getItem('printPatientObj') != undefined && localStorage.getItem('printPatientObj') != null && localStorage.getItem('printPatientObj') != ''){
            var printPatient = JSON.parse(localStorage.getItem('printPatientObj'));
            var startDate = printPatient.searchedStartDate;
            var endDate = printPatient.searchedEndDate;
            this.dischargedSearch.searchedStartDate = new Date(startDate);
            this.dischargedSearch.searchedEndDate = new Date(endDate);
            delete window.localStorage["printPatientObj"];
          }
          let headers = new Headers({ 'Content-Type': 'application/json' });
          let options = new RequestOptions({ headers: headers });
          var sendObject =  JSON.stringify(this.dischargedSearch);
          this.http.post(this.apiData.getDischargedPatients + "/" + this.branchName, sendObject, options).
          subscribe(res => {
            this.handleDischargedPatientData(res.json());
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

    handleDischargedPatientData(responseData: any){
      console.log("The Data of Discharge Patient List is");
      this.dischargedPatientData = responseData;

      console.log(this.dischargedPatientData);
    }

    ngOnInit() {
    }

  }
