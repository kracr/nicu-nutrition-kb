import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { MasterJSON } from './masterJSON';
import { BloodProductObj } from './bloodProductObj';
import { BloodProductTempObj } from './bloodProductTempObj';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'blood-product',
  templateUrl: './blood-product.component.html',
  styleUrls: ['./blood-product.component.css']
})
export class BloodProductComponent implements OnInit {

	apiData : APIurl;
	uhid : string;
	loggedUser : string;
 	workingWeight : number;
	http: Http;
	router : Router;
  datePipe: DatePipe;
	masterJSON : MasterJSON;
  bloodProductDetailList : BloodProductObj[];
	bloodProductTempObj : BloodProductTempObj;
	minDate : string;
	maxDate : Date;
	isPlanAssessmentVisible : boolean;
	isActionAssessmentVisible : boolean;
	isCauseAssessmentVisible : boolean;
	isProgressNotesAssessmentVisible : boolean;
	isLoaderVisible : boolean;
	isProgressNotesInactiveVisible : boolean;
	isActionReactionVisible : boolean;
	isPlanReactionVisible : boolean;
	isProgressNotesReactionVisible : boolean;
	isBloodProduct : string;
	loaderContent : string;
	causeList : any;
  branchName : string;
  printBloodProductFromDate : any;
  printBloodProductToDate : any;
  timezoneOffset : number;
	constructor(http: Http, router : Router, datePipe: DatePipe) {
		this.apiData = new APIurl();
		this.http = http;
		this.router = router;
    this.datePipe = datePipe;
		this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
		this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.workingWeight = JSON.parse(localStorage.getItem('selectedChild')).workingWeight / 1000;
    this.masterJSON = new MasterJSON();
    this.bloodProductDetailList = [];
		this.bloodProductTempObj = new BloodProductTempObj();
		this.isActionAssessmentVisible = false;
		this.isPlanAssessmentVisible = false;
		this.isCauseAssessmentVisible = false;
		this.isProgressNotesAssessmentVisible = true;
		this.isProgressNotesInactiveVisible = true;
		this.isActionReactionVisible = false;
		this.isPlanReactionVisible = false;
		this.isProgressNotesReactionVisible = true;
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    this.causeList = {};
    this.printBloodProductFromDate = JSON.parse(localStorage.getItem('selectedChild')).admitDate;
    this.printBloodProductToDate = new Date();
    this.timezoneOffset = new Date().getTimezoneOffset();
	}
	actionSectionAssessmentVisible(){
		this.isActionAssessmentVisible = !this.isActionAssessmentVisible;
	}
	planAssessmentSectionVisible(){
		this.isPlanAssessmentVisible = !this.isPlanAssessmentVisible;
	}
	causeAssessmentSectionVisible(){
		this.isCauseAssessmentVisible = !this.isCauseAssessmentVisible;
	}
	progressNotesAssessmentSectionVisible(){
		this.isProgressNotesAssessmentVisible =  !this.isProgressNotesAssessmentVisible;
	}
	progressNotesInactiveSectionVisible(){
		this.isProgressNotesInactiveVisible =  !this.isProgressNotesInactiveVisible;
	}
	actionSectionReactionVisible(){
		this.isActionReactionVisible = !this.isActionReactionVisible;
	}
	planReactionSectionVisible(){
		this.isPlanReactionVisible =  !this.isPlanReactionVisible;
	}
	progressNotesReactionSectionVisible(){
		this.isProgressNotesReactionVisible = !this.isProgressNotesReactionVisible;
	}
	getDoctorBloodProducts() {
		try{
			this.http.request(this.apiData.getDoctorBloodProducts + this.uhid + "/" + this.branchName + "/").subscribe((res: Response) => {
				console.log("getDoctorBloodProducts");
				this.masterJSON = res.json();
        this.masterJSON.currentObj.assessment_time = new Date();
        if(this.masterJSON.pastList != null){
          this.bloodProductDetailList = this.masterJSON.pastList;
        }
			});
		}catch(e){
			console.log("Exception in http service:"+e);
		};
	}

	setDoctorBloodProducts(currentObj : any,isinclude:any){
		currentObj.loggeduser = this.loggedUser;
		currentObj.uhid = this.uhid;
    	currentObj.isIncludeInPN=isinclude;
		this.isLoaderVisible = true;
    	this.loaderContent = 'Saving...';
		try{
			this.http.post(this.apiData.setDoctorBloodProducts + this.branchName + "/", currentObj).subscribe(res => {
  			this.isLoaderVisible = false;
  			this.loaderContent = '';
  			if(res.json().type=="success") {
  				this.masterJSON = res.json().returnedObject;
          this.causeList={};
         	this.masterJSON.currentObj.assessment_time = new Date();
  			}
			}, err => {
    				console.log("Error occured.")
    			});
		} catch(e) {
			console.log("Exception in http service:"+e);
		};
	}

	showmodal(){
    	$("#ballardOverlay").css("display", "block");
    	$("#Pngmodal").addClass("showing");
	}
	closeModal(){
  		$("#ballardOverlay").css("display", "none");
    	$("#Pngmodal").addClass("showing");
	}
  calculateTotalBloodVolume () {
    if(this.masterJSON.currentObj.blood_volume_kg != null && this.masterJSON.currentObj.blood_volume_kg != '') {
      this.masterJSON.currentObj.total_volume = this.round(this.masterJSON.currentObj.blood_volume_kg * this.workingWeight, 2);
    }
    this.calculateInfusionRate();
  }

  calculateInfusionRate () {
    if(this.masterJSON.currentObj.total_volume != null && this.masterJSON.currentObj.total_volume != ''
    && this.masterJSON.currentObj.infusion_time != null && this.masterJSON.currentObj.infusion_time != '') {
      this.masterJSON.currentObj.infusion_rate = this.round(this.masterJSON.currentObj.total_volume / this.masterJSON.currentObj.infusion_time, 2);
    }
    this.populateProgressNotes();
  }

  populateCauseStr() {
    var causeStr = "";
    if(this.causeList.serveAnemiaChecked) {
      causeStr += ", Serve Anemia of Antenatal Onset";
    }

    if(this.causeList.hemorrhageChecked) {
      causeStr += ", Haemorrhage";
    }

    if(this.causeList.hemolysisChecked) {
      causeStr += ", Hemolysis";
    }

    if(this.causeList.rbcLossChecked) {
      causeStr += ", RBC Loss By Phlebotomy";
    }

    if(this.causeList.sepsisChecked) {
      causeStr += ", Sepsis";
    }

    if(this.causeList.prematurityChecked) {
      causeStr += ", Anemia of Prematurity";
    }

    if(this.causeList.other)
    {
      causeStr+=','+this.causeList.othercause;
    }

    this.masterJSON.currentObj.cause = causeStr.substring(2, causeStr.length);
    this.populateProgressNotes();
  }

  populateProgressNotes() {
    var noteStr = "";
    if(this.masterJSON.currentObj.status == 'Active') {
      if(this.masterJSON.currentObj.blood_product == 'Packed cell' || this.masterJSON.currentObj.blood_product == 'Whole Blood') {
        noteStr += this.masterJSON.currentObj.blood_product + " is being given ";
        if(this.masterJSON.currentObj.indication_hb != null && this.masterJSON.currentObj.indication_hb != '') {
          noteStr += " for Hb " + this.masterJSON.currentObj.indication_hb + " g/dl";
        }

        if(this.masterJSON.currentObj.indication_resp != '') {
          noteStr += " and baby is on " + this.masterJSON.currentObj.indication_resp + ". ";
        } else {
          noteStr += ". ";
        }

        if(this.masterJSON.currentObj.apneic_spell && this.masterJSON.currentObj.apnea_count != null
           && this.masterJSON.currentObj.apnea_count != '') {
          noteStr += "The apneic spell count is " + this.masterJSON.currentObj.apnea_count + ". ";
        }
      } else if(this.masterJSON.currentObj.blood_product == 'Aphereis Platelet' ||
       this.masterJSON.currentObj.blood_product == 'Platelet Concentration' || this.masterJSON.currentObj.blood_product == 'PRP') {
        noteStr += this.masterJSON.currentObj.blood_product + " is being given";
        if(this.masterJSON.currentObj.platelet_count != null && this.masterJSON.currentObj.platelet_count != '') {
          noteStr += " for platelet count " + this.masterJSON.currentObj.platelet_count + " per unit. ";
        } else {
          noteStr += ". ";
        }

        if(this.masterJSON.currentObj.bleeding != '') {
          noteStr += "Baby had " + this.masterJSON.currentObj.bleeding + " bleeding. ";
        }

        if(this.masterJSON.currentObj.surgery) {
          noteStr += "Surgery is needed. ";
        }
      } else if(this.masterJSON.currentObj.blood_product == 'FFP') {
        noteStr += this.masterJSON.currentObj.blood_product + " is being given. ";
        if(this.masterJSON.currentObj.ptt_value != null && this.masterJSON.currentObj.ptt_value != ''
          && this.masterJSON.currentObj.aptt_value != null && this.masterJSON.currentObj.aptt_value != '') {
          noteStr += " for PTT " + this.masterJSON.currentObj.ptt_value + " and APTT " + this.masterJSON.currentObj.aptt_value + ". ";
        } else if (this.masterJSON.currentObj.ptt_value != null && this.masterJSON.currentObj.ptt_value != '') {
        noteStr += " for PTT " + this.masterJSON.currentObj.ptt_value + ". ";
        } else if (this.masterJSON.currentObj.aptt_value != null && this.masterJSON.currentObj.aptt_value != '') {
          noteStr += " for APTT " + this.masterJSON.currentObj.aptt_value + ". ";
        }

        if(this.masterJSON.currentObj.bleeding != '') {
          noteStr += "Baby had " + this.masterJSON.currentObj.bleeding + " bleeding. ";
        }
      }

      if(this.masterJSON.currentObj.collection_date != null && this.masterJSON.currentObj.expiry_date != null
        && this.masterJSON.currentObj.bag_number != null && this.masterJSON.currentObj.bag_number != ''
        && this.masterJSON.currentObj.bag_volume != null && this.masterJSON.currentObj.bag_volume != ''
        && this.masterJSON.currentObj.blood_group != '' && this.masterJSON.currentObj.checked_by != '') {
          noteStr += "Blood has been checked for bag no. " + this.masterJSON.currentObj.bag_number
           + ", blood group " + this.masterJSON.currentObj.blood_group + ", total volume of "
           + this.masterJSON.currentObj.bag_volume + " ml with expiry date " +
           this.datePipe.transform(this.masterJSON.currentObj.expiry_date, 'dd-MM-yyyy') + " collected on " +
           this.datePipe.transform(this.masterJSON.currentObj.collection_date, 'dd-MM-yyyy') + " by Dr. " +
           this.masterJSON.currentObj.checked_by + ". ";
      }

      if(this.masterJSON.currentObj.blood_product != null && this.masterJSON.currentObj.blood_product != ''
       && this.masterJSON.currentObj.blood_volume_kg != null && this.masterJSON.currentObj.blood_volume_kg != ''
       && this.masterJSON.currentObj.infusion_rate != null && this.masterJSON.currentObj.infusion_rate != '') {
        noteStr += this.masterJSON.currentObj.blood_product + " " + this.masterJSON.currentObj.blood_volume_kg
         + " ml/kg (total volume " + this.masterJSON.currentObj.total_volume + " ml) over "
         + this.masterJSON.currentObj.infusion_time + " hr @ " + this.masterJSON.currentObj.infusion_rate
         + " ml/hr through " + this.masterJSON.currentObj.venous_access + " vein. ";
      }

      if(this.masterJSON.currentObj.vital_time != null && this.masterJSON.currentObj.vital_time != '') {
        noteStr += "Monitor vital every " + this.masterJSON.currentObj.vital_time + " " + this.masterJSON.currentObj.vital_time_type + ". ";
      }

      if(this.masterJSON.currentObj.plan_test != '' && this.masterJSON.currentObj.test_time != null
       && this.masterJSON.currentObj.test_time != '') {
        noteStr += "Repeat " + this.masterJSON.currentObj.plan_test + " after " +
         this.masterJSON.currentObj.test_time + " " + this.masterJSON.currentObj.test_time_type + ". ";
      }

      if(this.masterJSON.currentObj.cause != null && this.masterJSON.currentObj.cause != '') {
        if(this.masterJSON.currentObj.cause.indexOf(",") == -1) {
          noteStr += this.masterJSON.currentObj.cause + " is the most likely cause.";
        } else {
          noteStr += this.masterJSON.currentObj.cause + " are the most likely cause.";
        }
      }
    } else {
      // inactive notes here
    }

    this.masterJSON.currentObj.progress_notes = noteStr;
    console.log(this.masterJSON.currentObj);
  }

	round(value : any, precision : any) {
		var multiplier = Math.pow(10, precision);
		return Math.round(value * multiplier) / multiplier;
	}

  dateformatter = function(date : Date){
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
    return dd+"-"+mm+"-"+ yy+" "+h+":"+m+" "+P;
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

  generateBloodProductDetailList(){
    //From Date without Offset
    var fromTime = new Date(this.printBloodProductFromDate).getTime();
    console.log(fromTime);

    //From Date with Offset
    fromTime += this.timezoneOffset*60*1000;
    console.log(fromTime);

    //To Date without Offset
    var toTime = new Date(this.printBloodProductToDate).getTime();
    console.log(toTime);

    //To Date with Offset  
    toTime += this.timezoneOffset*60*1000;
    console.log(toTime);

    if(this.masterJSON.pastList.length > 0){
      this.bloodProductDetailList = [];
    }

    for(var i=0;i<this.masterJSON.pastList.length;i++){
      if(this.masterJSON.pastList[i].creationtime >= fromTime && this.masterJSON.pastList[i].creationtime <= toTime){
        this.bloodProductDetailList.push(this.masterJSON.pastList[i]);
      }
    }
  }

  showBloodProductDetail(){
    this.generateBloodProductDetailList();
    console.log(this.bloodProductDetailList);
  }

  printBloodProductDetail(){
    this.generateBloodProductDetailList();
    if(this.bloodProductDetailList != null && this.bloodProductDetailList.length > 0){
      localStorage.setItem('bloodProductPrintList',JSON.stringify(this.bloodProductDetailList));
      console.log(this.bloodProductDetailList);
      this.router.navigateByUrl('/blood/blood-product-print');
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
      this.getDoctorBloodProducts();
	}

}
