import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-progress-notes',
  templateUrl: './progress-notes.component.html',
  styleUrls: ['./progress-notes.component.css']
})

export class ProgressNotesComponent implements OnInit {

  isLoaderVisible : boolean;
  loaderContent : string;
  apiData : APIurl;
  uhid : string;
  loggedUser : string;
  dateObj : Date;
  http : Http;
  router : Router;
  maxDate : Date;
  minDate : string;
  response : any;
  doctorOrderObj : any;
  whichProgressNoteSelected : string;
  gestWeek : string;
  gestDays : string;
  gestationDays : number;
  gestationWeeks : number;
  weeks : any;
  ageDays : number;
  ageHours : any;
  changeValue : boolean;
  diff : number;
  lifeWeeks : number;
  ageMonth : number;
  weeksAfterDayAdd : number;
  totalDaysOfGestation : number;
  lifeDays : number;
  cgaAgeDays : number;
  orderDateObj : Date;
  progressNotesTab : string;
  pnList : Array<any>;
  bloodProductList : Array<any>;
  sodiumVolume : number;
  pnVolume : number;
  enVolume : number;
  girValue : number;
  childObject : any;
  weight : number;
  calciumVolume : number;
  potassiumTotal : number;
  magnesiumTotal : number;
  heplockVolume : number;
  medVolume : number;
  bloodVolume : number;
  maxTimeDate : Date;
  minTimeDate : Date;
  progressNoteDateFrom : any;
  progressNoteDateTo : any;
  hrDiff : number;

  constructor(http: Http, router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.router = router;
    this.apiData = new APIurl();
    this.http = http;
    this.dateObj = new Date();
    this.orderDateObj = new Date();
    this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.response = {};
    this.doctorOrderObj = {};
    this.whichProgressNoteSelected = 'Stable-Notes';
    this.ageHours = null;
    this.progressNotesTab = 'Note';
    this.pnList = [];
    this.bloodProductList = [];
    this.sodiumVolume = 0;
    this.pnVolume = 0;
    this.enVolume = 0;
    this.girValue = 0;
    this.calciumVolume = 0;
    this.weight = 1;
    this.potassiumTotal = 0;
    this.magnesiumTotal = 0;
    if(this.childObject.workingWeight != null) {
      this.weight = this.childObject.workingWeight / 1000;
    } else if(this.childObject.todayWeight != null) {
      this.weight = this.childObject.todayWeight / 1000;
    }
    this.heplockVolume = 0;
    this.medVolume = 0;
    this.bloodVolume = 0;
    this.hrDiff = 24;

  }

  ngOnInit() {
    if(JSON.parse(localStorage.getItem('printDataObj')) != undefined){
      localStorage.removeItem('printDataObj');
    }
    this.maxDate = new Date();
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM') {
      tempHr = '00';
    } else if(tempHr != 12 && tempMer == 'PM') {
      tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
    var tempPrevTime = this.minDate.slice(16,24);
    this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
    this.getDailyProgressNotes(true);
    this.getDoctorOrders();
  }

  processTimeRange(){
    var processDate = new Date(this.dateObj);
    processDate.setHours(8);
    processDate.setMinutes(0);
    var processCurrentDate = new Date(this.dateObj.getTime() + (24 * 60 * 60 * 1000));
    processCurrentDate.setHours(8);
    processCurrentDate.setMinutes(0);
    this.maxTimeDate = processCurrentDate;
    this.minTimeDate = processDate;
    this.progressNoteDateFrom = processDate;
    this.progressNoteDateTo = processCurrentDate;
  }

  getDailyProgressNotes(flag : any) {
  	console.log("comming into the getDailyProgressNotes");
    if(flag){
      this.processTimeRange();
    }
    this.hrDiff = (this.progressNoteDateTo.getTime() - this.progressNoteDateFrom.getTime()) / (1000 * 60 * 60);
    this.hrDiff = this.round(this.hrDiff, 2);
    var dateStr = this.formatDate(this.dateObj);
  	try {
        this.http.request(this.apiData.getDailyProgressNotes + this.uhid + "/" + dateStr + "/" + this.progressNoteDateFrom.getTime() + "/" + this.progressNoteDateTo.getTime() + "/").subscribe((res: Response) => {
          this.response = res.json();
          this.calculateOtherDetails();
        });
     } catch(e) {
        console.log("Exception in http service:"+e);
     };
  }

  getDoctorOrders() {
  	console.log("comming into the getDoctorOrders");
    var dateStr = this.formatDate(this.orderDateObj);
  	try {
        this.http.request(this.apiData.getDoctorOrders + this.uhid + "/" + dateStr + "/").subscribe((res: Response) => {
          this.doctorOrderObj = res.json();
          this.calculateOtherParameters();
        });
     } catch(e) {
        console.log("Exception in http service:"+e);
     };
  }

  calculateOtherParameters = function(){
    this.girValue = 0;
    this.pnVolume = 0;
    this.enVolume = 0;
    this.potassiumTotal = 0;
    this.magnesiumTotal = 0;
    this.sodiumVolume = 0;
    this.calciumVolume = 0;


    if(this.doctorOrderObj.babyFeedObj != null && this.doctorOrderObj.babyFeedObj.length > 0){
      for(var i=0 ; i < this.doctorOrderObj.babyFeedObj.length; i++){

        // Calculate Glucose(GIR)
        if(this.doctorOrderObj.babyFeedObj[i].girvalue != null){
          this.girValue = this.doctorOrderObj.babyFeedObj[i].girvalue;
        }

        // Calculate EN Volume
        if(this.doctorOrderObj.babyFeedObj[i].totalenteralvolume != null){
          this.enVolume = this.doctorOrderObj.babyFeedObj[i].totalenteralvolume;
        }

        // Calculate PN Volume
        if(this.doctorOrderObj.babyFeedObj[i].isReadymadeSolutionGiven != null && this.doctorOrderObj.babyFeedObj[i].isReadymadeSolutionGiven == false){
          this.pnVolume = this.doctorOrderObj.babyFeedObj[i].totalparenteralAdditivevolume + this.doctorOrderObj.babyFeedObj[i].aminoacidTotal + this.doctorOrderObj.babyFeedObj[i].dextroseVolumemlperday;
        }
        if(this.doctorOrderObj.babyFeedObj[i].isReadymadeSolutionGiven != null && this.doctorOrderObj.babyFeedObj[i].isReadymadeSolutionGiven == true){
          this.pnVolume = this.doctorOrderObj.babyFeedObj[i].totalparenteralAdditivevolume + this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume;
        }

        // Calculate Potasium
        if(this.doctorOrderObj.babyFeedObj[i].potassiumVolume != null){
          this.potassiumTotal = this.doctorOrderObj.babyFeedObj[i].potassiumVolume;
        }

        // Calculate Magnesium
        if(this.doctorOrderObj.babyFeedObj[i].magnesiumVolume != null){
          this.magnesiumTotal = this.doctorOrderObj.babyFeedObj[i].magnesiumVolume * 500;
        }

        // Calculate Sodium
        try{
          this.sodiumVolume = 0;
          if(this.doctorOrderObj.babyFeedObj[i].bolusType != null && this.doctorOrderObj.babyFeedObj[i].bolusType == "saline" && this.doctorOrderObj.babyFeedObj[i].bolusTotalFeed != null){
            this.sodiumVolume = (this.doctorOrderObj.babyFeedObj[i].bolusTotalFeed * 154) / 1000;
          }

          if(this.doctorOrderObj.babyFeedObj[i].sodiumVolume != null && this.doctorOrderObj.babyFeedObj[i].sodiumVolume == "0.15" && this.doctorOrderObj.babyFeedObj[i].sodiumTotal != null){
            this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].sodiumTotal * 154) / 1000;
          }

          if(this.doctorOrderObj.babyFeedObj[i].sodiumVolume != null && this.doctorOrderObj.babyFeedObj[i].sodiumVolume == "0.5" && this.doctorOrderObj.babyFeedObj[i].sodiumTotal != null){
            this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].sodiumTotal * 513) / 1000;
          }

          if(this.doctorOrderObj.babyFeedObj[i].isReadymadeSolutionGiven == true && this.doctorOrderObj.babyFeedObj[i].readymadeFluidType != null && this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume != null){
            if(this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.45 NS in D5(N/2)" || this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.45 NS in D10(N/2)"){
              this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume * 77) / 1000;
            }
            if(this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.3 NS in D5(N/3)" || this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.3 NS in D10(N/3)"){
              this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume * 51.3) / 1000;
            }
            if(this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.2 NS in D5(N/4)" || this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.2 NS in D5(N/4)"){
              this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume * 38.5) / 1000;
            }
            if(this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.18 NS in D5(N/5)" || this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "0.18 NS in D10(N/5)"){
              this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume * 30.8) / 1000;
            }
            if(this.doctorOrderObj.babyFeedObj[i].readymadeFluidType == "ISO P(N/6)"){
              this.sodiumVolume += (this.doctorOrderObj.babyFeedObj[i].readymadeTotalFluidVolume * 23) / 1000;
            }
          }
          if(this.sodiumVolume != undefined){
            this.sodiumVolume = this.sodiumVolume / this.weight;
          }
        }catch(e){
          console.log("Exception in http service:"+e);
        };

        // Calculate Calcium
        try{
          this.calciumVolume = 0;

          //Calculating from Additives
          if(this.doctorOrderObj.babyFeedObj[i].calciumConcentrationType != null && this.doctorOrderObj.babyFeedObj[i].calciumConcentrationType == 'Calcium Gluconate' && this.doctorOrderObj.babyFeedObj[i].calciumTotal != null){
            this.calciumVolume = (9.3 * this.doctorOrderObj.babyFeedObj[i].calciumTotal);
          }
          // if(this.doctorOrderObj.feedCalulator != null && this.doctorOrderObj.feedCalulator.lastDeficitCal != null && this.doctorOrderObj.feedCalulator.lastDeficitCal.enteralIntake != null
          //   && this.doctorOrderObj.feedCalulator.lastDeficitCal.parentalIntake != null){
          //     if(this.doctorOrderObj.feedCalulator.lastDeficitCal.enteralIntake.Calcium != null){
          //       this.calciumVolume = this.calciumVolume + this.doctorOrderObj.feedCalulator.lastDeficitCal.enteralIntake.Calcium;
          //     }
          //     if(this.doctorOrderObj.feedCalulator.lastDeficitCal.parentalIntake.Calcium != null){
          //       this.calciumVolume = this.calciumVolume + this.doctorOrderObj.feedCalulator.lastDeficitCal.parentalIntake.Calcium;
          //     }
          // }

          //Calculating from EN
          if(this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay != null && this.doctorOrderObj.babyFeedObj[i].feedtype != null){
            if(this.doctorOrderObj.babyFeedObj[i].feedtype.includes('EBM & Lactodex HMF')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 125) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedtype.includes('EBM &  Similar HMF')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 142) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedtype.includes('EBM')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 25.3) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedTypeSecondary.includes('Enfamil')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 52) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedTypeSecondary.includes('Lactodex')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 136) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedTypeSecondary.includes('Dexolac')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 112.4) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedTypeSecondary.includes('Similac Neosure')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 103) / 100);
            }
            else if(this.doctorOrderObj.babyFeedObj[i].feedTypeSecondary.includes('Similac Advance')){
              this.calciumVolume += ((this.doctorOrderObj.babyFeedObj[i].totalfeedMlDay * 49) / 100);
            }
          }

          if(this.calciumVolume != undefined){
            this.calciumVolume = this.calciumVolume / this.weight;
          }
          if(this.doctorOrderObj.babyFeedObj[i].isenternalgiven && this.doctorOrderObj.babyFeedObj[i].calciumVolume != null ){
            this.calciumVolume += (this.doctorOrderObj.babyFeedObj[i].calciumVolume);
          }
        }catch(e){
          console.log("Exception in http service:"+e);
        };
      }
    }

    // Calculate Heplock Volume
    if(this.doctorOrderObj.centralLineObj != null && this.doctorOrderObj.centralLineObj.length > 0){
      for(var i=0 ; i < this.doctorOrderObj.centralLineObj.length; i++){
        this.heplockVolume = this.doctorOrderObj.centralLineObj[i].heparinVolume;
      }
    }

    // Calculate Med Volume
    if(this.doctorOrderObj.babyPrescriptionList != null && this.doctorOrderObj.babyPrescriptionList.length > 0){
      for(var i=0 ; i < this.doctorOrderObj.babyPrescriptionList.length; i++){
        this.medVolume = this.doctorOrderObj.babyPrescriptionList[i].inf_volume;
      }
    }

    // Calculate Blood Product Volume
    if(this.doctorOrderObj.bloodProductObj != null && this.doctorOrderObj.bloodProductObj.length > 0){
      for(var i=0 ; i < this.doctorOrderObj.bloodProductObj.length; i++){
        this.bloodVolume = this.doctorOrderObj.bloodProductObj[i].total_volume;
      }
    }

  }

  round(value : any, precision : any) {
    var multiplier = Math.pow(10, precision);
    return Math.round(value * multiplier) / multiplier;
  }

	setDailyProgressNotes() {
		console.log("comming into the setDailyProgressNotes");
    this.response.uhid = this.uhid;
		this.response.loggeduser = this.loggedUser;
    this.response.note_date = this.dateObj;
    this.isLoaderVisible = true;

		try{
      this.http.post(this.apiData.setDailyProgressNotes, this.response).subscribe(res => {
      	this.response = res.json();
        this.isLoaderVisible = false;
      },
      err => {
        console.log("Error occured.")
      });
    } catch(e) {
      console.log("Exception in http service:"+e);
    };
	}
  printDailyProgressNotes(){
  console.log(this.response);
  this.response.date = this.dateObj;
  this.response.type = 'Notes';
  this.response.hrDiff = this.hrDiff;
  localStorage.setItem('printDataObj', JSON.stringify(this.response));
  this.router.navigateByUrl('/summary/summary-notes-print');
  }
  printDailyDoctorOrders(){
  console.log(this.doctorOrderObj);
  this.doctorOrderObj.date = this.orderDateObj;
  this.doctorOrderObj.type = 'Orders';
  this.doctorOrderObj.pnVolume = this.pnVolume;
  this.doctorOrderObj.enVolume = this.enVolume;
  this.doctorOrderObj.girValue = this.girValue;
  this.doctorOrderObj.heplockVolume = this.heplockVolume;
  this.doctorOrderObj.medVolume = this.medVolume;
  this.doctorOrderObj.bloodVolume = this.bloodVolume;
  this.doctorOrderObj.sodiumVolume = this.sodiumVolume;
  this.doctorOrderObj.potassiumTotal = this.potassiumTotal;
  this.doctorOrderObj.magnesiumTotal = this.magnesiumTotal;
  this.doctorOrderObj.calciumVolume = this.calciumVolume;
  this.doctorOrderObj.weight = this.weight;
  localStorage.setItem('printDataObj', JSON.stringify(this.doctorOrderObj));
  this.router.navigateByUrl('/summary/summary-notes-print');
  }
  whichProgressNoteSelectedFun(value){
    this.whichProgressNoteSelected = value;
  }

  formatDate(date) {
    var d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
  }

  calculateOtherDetails = function() {
    try{
      var child = JSON.parse(localStorage.getItem('selectedChild'));
      var gestationString = child.gestation;
      console.log("calculate gestation and age");
      console.log(child.dob);
      console.log(child.timeOfBirth);

      if(this.dateObj == null || this.dateObj == undefined){
        this.dateObj = new Date();
        console.log(this.dateObj+ "set");
      }
      var obj = JSON.parse(JSON.stringify(this.dateObj));
      var newDate = Date.parse(obj);
      var tempDate = new Date(newDate);
      tempDate.setHours(0);
      tempDate.setMinutes(0);

      this.diff = tempDate.getTime() - Date.parse(child.dob);
      this.diff = (this.diff / (24 * 60 * 60 * 1000)) + 1;
      this.diff = Math.ceil(this.diff);
      this.ageDays = parseInt(this.diff);
      var dateTimeBirth = new Date(child.dob);

      var tobStr = child.timeOfBirth.split(',');
      if(tobStr[2] == 'PM' && parseInt(tobStr[0]) != 12) {
        dateTimeBirth.setHours(12 + parseInt(tobStr[0]));
      } else if (tobStr[2] == 'AM' && parseInt(tobStr[0]) == 12){
          dateTimeBirth.setHours(0);
      } else {
          dateTimeBirth.setHours(parseInt(tobStr[0]));
      }
      dateTimeBirth.setMinutes(parseInt(tobStr[1]));

      if(gestationString) {
        console.log("found ",gestationString);
        var weeks = gestationString.split("Weeks");
        var days = gestationString.match("Weeks(.*)Days");
        this.gestationWeeks = parseInt(weeks[0]);
        this.gestationDays = parseInt(days[1]);
      }
      else {
        console.log("no found");
        this.gestationWeeks = 0;
        this.gestationDays = 0;
      }

      if (this.ageDays  > 7) {
        this.lifeWeeks = (this.ageDays  / 7);
        this.lifeWeeks = parseInt(this.lifeWeeks);
        console.log(this.lifeWeeks + "this.lifeWeeks");
        this.lifeDays = this.ageDays - this.lifeWeeks * 7;
        this.gestationWeeks += this.lifeWeeks;
        this.lifeDays = parseInt(this.lifeDays);
        this.totalDaysOfGestation = this.lifeDays + this.gestationDays;
        if (this.totalDaysOfGestation > 6) {

          this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
          this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
          this.gestationWeeks += parseInt(this.weeksAfterDayAdd + "");
          this.gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");

        } else {
          console.log(this.totalDaysOfGestation + "gestationending");
          this.gestationDays = parseInt(this.totalDaysOfGestation + "");
        }
      } else {
        this.totalDaysOfGestation = this.ageDays + this.gestationDays;
        if (this.totalDaysOfGestation > 6) {
          this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
          this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
          this.gestationWeeks += parseInt(this.weeksAfterDayAdd + "");
          this.gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");

        } else {
          this.gestationDays = parseInt(this.totalDaysOfGestation + "");
        }
      }

      if(this.gestationDays == 0){
        this.gestationDays = 6;
        this.gestationWeeks = this.gestationWeeks - 1;
      }
      else{
        this.gestationDays = this.gestationDays - 1;
      }

      var tempDateobj = JSON.parse(JSON.stringify(this.dateObj));
      var dateTimeBirthobj = JSON.parse(JSON.stringify(dateTimeBirth));
      tempDateobj = Date.parse(tempDateobj);
      tempDateobj= new Date(tempDateobj);
      dateTimeBirthobj = Date.parse(dateTimeBirthobj);
      dateTimeBirthobj = new Date(dateTimeBirthobj);
      tempDateobj.setHours(0);
      tempDateobj.setMinutes(0);
      dateTimeBirthobj.setHours(0);
      dateTimeBirthobj.setMinutes(0);

      if(((this.dateObj.getTime() - tempDateobj.getTime()) - (dateTimeBirth.getTime() - dateTimeBirthobj.getTime())) < 0){
        this.ageDays = this.ageDays - 1;
      }

      // if(this.ageDays < 6) {
      //   this.ageHours = Math.round(diffHrs);
      // }

      this.response.nicuDays = "" + this.ageDays;
      //this.response.nicuHours = this.ageHours;
      this.response.gestationWeeks = this.gestationWeeks;
      this.response.gestationDays = this.gestationDays;
    }
    catch(e){
      this.gestationWeeks = null;
      this.gestationDays = null;
      console.warn("Exception in initialising the controller auto initialise method: "+e);
    }
  };
}
