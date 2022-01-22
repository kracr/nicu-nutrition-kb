import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { AppComponent } from '../app.component';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'assessment-sheet-stable',
  templateUrl: './assessment-sheet-stable.component.html',
  styleUrls: ['./assessment-sheet-stable.component.css']
})
export class AssessmentSheetStableComponent implements OnInit {

	apiData : APIurl;
	http: Http;
	router : Router;
	uhid : string;
	gestation : string;
	workingWeight : any;
	loggedUser : string;
	stableNoteData : any;
	stableTempObj : any;
	stableNoteTempObj : any;
	hoursList : Array<any>;
	minutesList : Array<any>;
	printDataObj : any;
	meanBp : any;
	gestationWeeks : any;
	systemNormalList : Array<any>;
	systemAbnormalList : Array<any>;
	tempMessage : any;
	spoMessage : any;
	hrMessage : any;
	rrMessage : any;
	diaBpMessage : any;
	sysBpMessage : any;
	fromDateTableNull : boolean;
	toDateTableNull : boolean;
	fromToTableError : boolean;
	childObject : Array<any>;
    minDate : string;
    maxDate : Date;
    isLoaderVisible : boolean;
    loaderContent : string;
    selectedDecease : string;
    investOrderSelected : Array<any>;
	investOrderNotOrdered : Array<any>;
  pastPrescriptionList : any;
  currentDate : Date;

	constructor(http: Http, router : Router) {
		this.apiData = new APIurl();
		this.http = http;
		this.router = router;
		this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
		this.gestation = JSON.parse(localStorage.getItem('selectedChild')).gestation;
		this.workingWeight = JSON.parse(localStorage.getItem('selectedChild')).workingWeight;
    this.loggedUser = 'test';
		this.hoursList = new Array<any>();
		this.minutesList = new Array<any>();
		this.systemNormalList = new Array<any>();
		this.systemAbnormalList = new Array<any>();
		this.stableTempObj = {};
		this.stableNoteTempObj = {};
		this.printDataObj = {};
		this.investOrderSelected = [];
		this.investOrderNotOrdered = [];
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.pastPrescriptionList = [];
    this.currentDate = new Date();
	}

	redirectToPrescription = function(eventName) {
		localStorage.setItem('medicationAssessment',JSON.stringify(eventName));
		localStorage.setItem('stableNoteData',JSON.stringify(this.stableNoteData));
		localStorage.setItem('stableTempObj',JSON.stringify(this.stableTempObj));
		localStorage.setItem('stableNoteTempObj',JSON.stringify(this.stableNoteTempObj));
		localStorage.setItem('prescriptionList',JSON.stringify(this.stableNoteData.note.prescriptionList));
		this.router.navigateByUrl('/med/medications');
	}

	printStableNoteData(fromDateTable : Date, toDateTable : Date) {
		this.fromDateTableNull = false;
	  	this.toDateTableNull = false;
	  	this.fromToTableError = false;
	  	this.printDataObj.dateFrom = fromDateTable;
	  	this.printDataObj.dateTo= toDateTable;
	    this.printDataObj.uhid = this.workingWeight.uhid;
		this.printDataObj.whichTab = "Stable Notes";
	  	if(fromDateTable == null) {
	    	this.fromDateTableNull = true;
		} else if(toDateTable == null) {
	    	this.toDateTableNull = true;
	  	} else if(fromDateTable >= toDateTable) {
	    	this.fromToTableError = true;
	  	} else {
	    	var data = [];
	    	for(var i=0; i < this.stableNoteData.notesList.length; i++) {
	      	var item = this.stableNoteData.notesList[i];
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

	showObject(){
		console.log(typeof(this.stableNoteData.note.hr));
		console.log(this.stableNoteData.note.hr);
		console.log(JSON.stringify(this.stableNoteData));
	}

	stoolNotPassed(){
		this.stableNoteData.note.stoolType = null;
	}

	urineNotPassed(){
		this.stableNoteData.note.urineVolume = null;
	}

  VomitNotPassed(){
		this.stableNoteData.note.vomitType = null;
    this.stableNoteData.note.vomitStatus = null;
	}

	getStableNoteData() {
		console.log("in getStableNoteData function");
		var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromPrescription'));
		if(isComingFromPrescription){
			this.stableNoteData = JSON.parse(localStorage.getItem('stableNoteData'));
			this.stableTempObj = JSON.parse(localStorage.getItem('stableTempObj'));
			this.stableNoteTempObj = JSON.parse(localStorage.getItem('stableNoteTempObj'));
			this.stableNoteData.note.prescriptionList =  JSON.parse(localStorage.getItem('prescriptionList'));

			var medStr = "";
			var medStartStr = "";
			var medStopStr = "";
      var medContStr = "";
      var medRevStr = "";

			for(var index=0; index < this.stableNoteData.note.prescriptionList.length; index++) {
				var medicine = this.stableNoteData.note.prescriptionList[index];
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

			this.stableNoteData.note.medicationStr = medStr;
			this.populateStableNotesStr();

			localStorage.removeItem('stableNoteData');
			localStorage.removeItem('stableTempObj');
			localStorage.removeItem('stableNoteTempObj');
			localStorage.removeItem('prescriptionList');
			localStorage.removeItem('isComingFromPrescription');
		} else {
			try{
				this.http.request(this.apiData.getStableNotes + this.uhid + "/").subscribe((res: Response) => {
					this.stableNoteData = res.json();
					this.stableNoteData.note.entrytime = new Date();
          this.isLoaderVisible = false;
          this.loaderContent = 'Saving...';
					this.stableTempObj.feedMethod = "";

          this.pastPrescriptionList = [];
          if(this.stableNoteData.note.prescriptionList != null && this.stableNoteData.note.prescriptionList.length > 0){
            for(var i = 0; i < this.stableNoteData.note.prescriptionList.length; i++){
              if(this.stableNoteData.note.prescriptionList[i].eventname == 'Stable Notes'){
                var startDate = new Date(this.stableNoteData.note.prescriptionList[i].medicineOrderDate);
                var medContiuationDayCount = Math.abs(Math.ceil((this.currentDate.getTime() - startDate.getTime())/(1000*60*60*24)));
                if(medContiuationDayCount == 0){
                  medContiuationDayCount = 1;
                }
                this.stableNoteData.note.prescriptionList[i].medicineDay = medContiuationDayCount;
                this.pastPrescriptionList.push(this.stableNoteData.note.prescriptionList[i]);
              }
            }
            this.stableNoteData.note.prescriptionList = [];
          }

					if(this.stableNoteData.note.babyFeed.feedmethod != null && this.stableNoteData.note.babyFeed.feedmethod != undefined){
						let feedMethodArr = this.stableNoteData.note.babyFeed.feedmethod.replace("[","").replace("]","").split(',');
						for(let i=0;i<feedMethodArr.length;i++ ){
							for(let index=0; index<this.stableNoteData.feedMethods.length;index++){

								if(feedMethodArr[i].trim()==this.stableNoteData.feedMethods[index].feedmethodid){
									if(this.stableTempObj.feedMethod=='')
										this.stableTempObj.feedMethod = this.stableNoteData.feedMethods[index].feedmethodname;
									else
										this.stableTempObj.feedMethod += ", "+ this.stableNoteData.feedMethods[index].feedmethodname;
								}
							}
						}
					}
					this.populateStableNotesStr();
				});
			}catch(e){
				console.log("Exception in http service:"+e);
			};
		}
	}

	saveStableNotes() {
		if(this.uhid != null && this.uhid != undefined){
			this.stableNoteData.note.uhid = this.uhid;
		} else{
			console.log("Cant able to get UHID form");
		}
		this.stableNoteData.note.episodeid = "12";
    this.isLoaderVisible = true;
    this.loaderContent = 'Saving...';
    this.stableNoteData.note.loggeduser = 'test';
		try{
			this.http.post(this.apiData.saveStableNotes, this.stableNoteData).subscribe(res => {
				if(res.json().type=="success"){
					this.getStableNoteData();
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

	calMeanBp = function(){
		if(this.stableNoteData.note.diaStolicBp != null && this.stableNoteData.note.diaStolicBp != ""
			&& this.stableNoteData.note.systolicBp != null && this.stableNoteData.note.systolicBp != ""){
			this.meanBp = ((this.stableNoteData.note.systolicBp *1) +(this.stableNoteData.note.diaStolicBp *1))/2;
			this.gestationWeeks = this.gestation.slice(0,3) * 1;
			if(this.meanBp < this.gestationWeeks){
				return false;
			} else {
				return true;
			}
		}
	}

  //To Populate the vital params when date is changed
  populateVitalInfoByDate(){
    try{
      this.http.request(this.apiData.getVitalInfoByDate + "/" + this.uhid +"/"+ this.stableNoteData.note.entrytime,)
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
    if(response.rrRate != null && response.rrRate != undefined){
      this.stableNoteData.note.rr = response.rrRate;
    }
    else{
      this.stableNoteData.note.rr = "";
    }

    if(response.hrRate != null && response.hrRate != undefined){
      this.stableNoteData.note.hr = response.hrRate;
    }
    else{
      this.stableNoteData.note.hr = "";
    }

    if(response.spo2 != null && response.spo2 != undefined){
      this.stableNoteData.note.spo2 = response.spo2;
    }
    else{
      this.stableNoteData.note.spo2 = "";
    }

    if(response.systBp != null && response.systBp != undefined){
      this.stableNoteData.note.systolicBp = response.systBp;
    }
    else{
      this.stableNoteData.note.systolicBp = "";
    }

    if(response.diastBp != null && response.diastBp != undefined){
      this.stableNoteData.note.diaStolicBp = response.diastBp;
    }
    else{
      this.stableNoteData.note.diaStolicBp = "";
    }
  }

	populateStableNotesStr() {
		console.log("stable notes");
		let template="";
		this.stableNoteData.note.notes = "";
		this.systemNormalList = new Array<any>();
		this.systemAbnormalList = new Array<any>();

		if(this.stableNoteData.note.respNormal != null){
			if(this.stableNoteData.note.respNormal == true){
				this.systemNormalList.push("Resp. System");
			}else{
				this.systemAbnormalList.push("Resp. System");
			}
		}
		if(this.stableNoteData.note.jaundiceNormal != null){
			if(this.stableNoteData.note.jaundiceNormal == true){
				this.systemNormalList.push("Jaundice");
			}else{
				this.systemAbnormalList.push("Jaundice");
			}
		}
		if(this.stableNoteData.note.infectionNormal != null){
			if(this.stableNoteData.note.infectionNormal == true){
				this.systemNormalList.push("Infection");
			}else{
				this.systemAbnormalList.push("Infection");
			}
		}
		if(this.stableNoteData.note.cnsNormal != null){
			if(this.stableNoteData.note.cnsNormal == true){
				this.systemNormalList.push("CNS");
			}else{
				this.systemAbnormalList.push("CNS");
			}
		}

		let systemNromalStr = "";
		let systemAbnormalStr = "";
		if(this.systemNormalList.length != 0 && this.systemNormalList.length>0){
			if(this.systemNormalList.length > 1){
				systemNromalStr += "No significant finding in " + this.systemNormalList[0];
				for(let i=1; i<= this.systemNormalList.length-2 ; i++){
					systemNromalStr +=	", " + this.systemNormalList[i];
				}
				systemNromalStr += " and " + this.systemNormalList[this.systemNormalList.length-1];
				systemNromalStr += ". ";
			}else{
				systemNromalStr += "No significant finding in " + this.systemNormalList[0];
				systemNromalStr += ". ";
			}
		}
		if(systemNromalStr != null && systemNromalStr != ""){
			template += systemNromalStr;
		}

		if(this.systemAbnormalList.length != 0 && this.systemAbnormalList.length>0){
			if(this.systemAbnormalList.length > 1){
				systemAbnormalStr += this.systemAbnormalList[0];
				for(let i=1; i<= this.systemAbnormalList.length-2 ; i++){
					systemAbnormalStr +=	", " + this.systemAbnormalList[i];
				}
				systemAbnormalStr += " and " + this.systemAbnormalList[this.systemAbnormalList.length-1];
				systemAbnormalStr += " are to be assessed. ";
			}else{
				systemAbnormalStr += this.systemAbnormalList[0];
				systemAbnormalStr += " is to be assessed. ";
			}
		}
		if(systemAbnormalStr != null && systemAbnormalStr != ""){
			template += systemAbnormalStr;
		}


		if(this.stableNoteData.note.activity != null && this.stableNoteData.note.activity != ""){
			template +="Baby is " + this.stableNoteData.note.activity.toLowerCase() + ". ";
		}

		let haemodynamicStable = null;
		let haemodynamicStableStr = "";
		if((this.stableNoteData.note.hr !=null && this.stableNoteData.note.hr >= 0 && this.stableNoteData.note.hr <= 350)){
			this.hrMessage = "";
			if((this.stableNoteData.note.hr !=null && this.stableNoteData.note.hr >= 120 && this.stableNoteData.note.hr <= 180)){
				haemodynamicStable = true;
			}
			else if(this.stableNoteData.note.hr !=null){
				haemodynamicStable = false;
			}
		}
		else{
			if(this.stableNoteData.note.hr ==null){

			}else{
				this.hrMessage = "Range 0-350";
				this.stableNoteData.note.hr =null;
			}
		}

		if((this.stableNoteData.note.temp !=null && this.stableNoteData.note.temp >= 0 && this.stableNoteData.note.temp < 1000)){
			this.tempMessage = "";
		}else{
			if(this.stableNoteData.note.temp ==null){

			}else{
				this.tempMessage ="Range 0-999";
				this.stableNoteData.note.temp =null;
			}
		}

		if((this.stableNoteData.note.rr !=null && this.stableNoteData.note.rr >= 0 && this.stableNoteData.note.rr <= 150)){
			this.rrMessage = "";
		}
		else{
			if(this.stableNoteData.note.rr ==null){

			}else{
				this.rrMessage = "Range 0-150";
				this.stableNoteData.note.rr =null
			}
		}

		if((this.stableNoteData.note.spo2 !=null && this.stableNoteData.note.spo2 >= 0 && this.stableNoteData.note.spo2 <= 100)){
			this.spoMessage = "";
		}
		else{
			if(this.stableNoteData.note.spo2 ==null){

			}else{
				this.spoMessage = "Range 0-100";
				this.stableNoteData.note.spo2 = null;
			}
		}

		if((this.stableNoteData.note.diaStolicBp !=null && this.stableNoteData.note.diaStolicBp >= 0 && this.stableNoteData.note.diaStolicBp < 1000)){
			this.diaBpMessage = "";
		}
		else{
			if(this.stableNoteData.note.diaStolicBp ==null){

			}else{
				this.diaBpMessage = "Range 0-999";
				this.stableNoteData.note.diaStolicBp = null;
			}
		}
		if((this.stableNoteData.note.systolicBp !=null && this.stableNoteData.note.systolicBp >= 0 && this.stableNoteData.note.systolicBp < 1000)){
			this.sysBpMessage = "";
		}
		else{
			if(this.stableNoteData.note.systolicBp ==null){

			}else{
				this.sysBpMessage = "Range 0-999";
				this.stableNoteData.note.systolicBp = null;
			}
		}

		let validMeanBp = null;
		validMeanBp = this.calMeanBp();
		if(validMeanBp != null){
			if(validMeanBp == true){
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

		// if(haemodynamicStable == true){
		// 	haemodynamicStableStr += "Vital paramaters are normal, haemodynamically stable";
		// }
		// else if(haemodynamicStable == false){
		// 	haemodynamicStableStr += "Vital paramaters are not normal, haemodynamically not stable";
		// }

    if(this.stableNoteData.note.orderSelectedText != "" && this.stableNoteData.note.orderSelectedText != undefined){
      if(this.stableNoteData.note.orderSelectedText.indexOf(",")== -1){
        template = template +"Investigation ordered is "+this.stableNoteData.note.orderSelectedText+". ";
      }
      else{
        template = template +"Investigation ordered are "+this.stableNoteData.note.orderSelectedText+". ";
      }
    }

		let vitalStr="";
		if(this.stableNoteData.note.hr != null && this.stableNoteData.note.hr != ""){
			vitalStr += "HR " + this.stableNoteData.note.hr +" bpm";
		}
		if((this.stableNoteData.note.diaStolicBp != null && this.stableNoteData.note.diaStolicBp != "") || (this.stableNoteData.note.systolicBp != null && this.stableNoteData.note.systolicBp != "")){
			if(this.stableNoteData.note.systolicBp != null && this.stableNoteData.note.systolicBp != "" && !(this.stableNoteData.note.diaStolicBp != null && this.stableNoteData.note.diaStolicBp != "")){
				if(vitalStr != ""){
					vitalStr += ", BP " + this.stableNoteData.note.systolicBp +" mmHg";
				}else{
					vitalStr += "BP " + this.stableNoteData.note.systolicBp +" mmHg";
				}
			}
			else if(this.stableNoteData.note.diaStolicBp != null && this.stableNoteData.note.diaStolicBp != "" && !(this.stableNoteData.note.systolicBp != null && this.stableNoteData.note.systolicBp != "")){
				if(vitalStr != ""){
					vitalStr += ", BP " + this.stableNoteData.note.diaStolicBp +" mmHg";
				}else{
					vitalStr += "BP " + this.stableNoteData.note.diaStolicBp +" mmHg";
				}
			}
			else{
				if(vitalStr != ""){
					vitalStr += ", BP " + this.stableNoteData.note.systolicBp + "/" + this.stableNoteData.note.diaStolicBp +" mmHg";
				}else{
					vitalStr += "BP " + this.stableNoteData.note.systolicBp + "/" + this.stableNoteData.note.diaStolicBp +" mmHg";
				}
			}
		}
		if(this.stableNoteData.note.temp != null && this.stableNoteData.note.temp != ""){
			if(vitalStr !=""){
				vitalStr += ", Temp " + this.stableNoteData.note.temp +" °C";
			}else{
				vitalStr += "Temp " + this.stableNoteData.note.temp +" °C";
			}
		}
		if(this.stableNoteData.note.rr != null && this.stableNoteData.note.rr != ""){
			if(vitalStr !=""){
				vitalStr += ", RR " + this.stableNoteData.note.rr +" bpm";
			}else{
				vitalStr += "RR " + this.stableNoteData.note.rr +" bpm";
			}
		}
		if(this.stableNoteData.note.spo2 != null && this.stableNoteData.note.spo2 != ""){
			if(vitalStr !=""){
				vitalStr += ", SpO2 " + this.stableNoteData.note.spo2 +" %";
			}else{
				vitalStr += "SpO2 " + this.stableNoteData.note.spo2 +" %";
			}
		}

		if(vitalStr != ""){
			if(vitalStr.includes(",")){
				template += "Vital parameters are " + vitalStr + ". ";
			}else{
				template += "Vital parameter is " + vitalStr +". ";
			}
		}

		if(this.stableNoteData.note.babyFeed.isenternalgiven!= null){
			if(this.stableNoteData.note.babyFeed.isenternalgiven){
				if(haemodynamicStableStr != ""){
					if(this.stableNoteData.note.babyFeed.isAdLibGiven) {
						haemodynamicStableStr += ", baby is on AD-LIB, accepting feed"
					} else if(this.stableTempObj.feedMethod != null && this.stableTempObj.feedMethod !=""
						&& (this.stableNoteData.note.babyFeed.feedduration != null && this.stableNoteData.note.babyFeed.feedduration != "")
						&& (this.stableNoteData.note.babyFeed.feedvolume != null && this.stableNoteData.note.babyFeed.feedvolume != "")){
						haemodynamicStableStr += ", accepting feed " + this.stableNoteData.note.babyFeed.feedvolume + " ml of " + this.stableTempObj.feedMethod
						+ " in every " + this.stableNoteData.note.babyFeed.feedduration + " hrs";
					} else if(this.stableTempObj.feedMethod != null && this.stableTempObj.feedMethod !=""){
						haemodynamicStableStr += ", accepting feed " + this.stableTempObj.feedMethod;
					}
				} else {
					if(this.stableNoteData.note.babyFeed.isAdLibGiven) {
						haemodynamicStableStr += "Baby is on AD-LIB, accepting feed"
					} else if(this.stableTempObj.feedMethod != null && this.stableTempObj.feedMethod !=""
						&& (this.stableNoteData.note.babyFeed.feedduration != null && this.stableNoteData.note.babyFeed.feedduration != "")
						&& (this.stableNoteData.note.babyFeed.feedvolume != null && this.stableNoteData.note.babyFeed.feedvolume != "")){
						haemodynamicStableStr += "Accepting feed " + this.stableNoteData.note.babyFeed.feedvolume + " ml of "+ this.stableTempObj.feedMethod
						+ " in every " + this.stableNoteData.note.babyFeed.feedduration + " hrs";
					} else if(this.stableTempObj.feedMethod != null && this.stableTempObj.feedMethod !=""){
						haemodynamicStableStr += ", accepting feed " + this.stableTempObj.feedMethod;
					}
				}
			}else{
				if(haemodynamicStableStr != ""){
					haemodynamicStableStr +=", NPO";
				}
				else{
					haemodynamicStableStr +="NPO";
				}
			}
		}

		if(haemodynamicStableStr != ""){
			template +=haemodynamicStableStr + ". ";
		}

		if(this.stableNoteData.note.stoolStatus != null || this.stableNoteData.note.urineStatus != null){
			if(this.stableNoteData.note.stoolStatus && !this.stableNoteData.note.urineStatus){
				template +="Passed stool";
				if(this.stableNoteData.note.stoolTimes != null && this.stableNoteData.note.stoolTimes != undefined){
					template +=" " + this.stableNoteData.note.stoolTimes + " times";
				}
				template += ". ";
			}else if(!this.stableNoteData.note.stoolStatus && this.stableNoteData.note.urineStatus){
				template +="Passed urine";
				if(this.stableNoteData.note.urineVolume != null && this.stableNoteData.note.urineVolume != ""){
					template += " ("+this.stableNoteData.note.urineVolume + " ml/kg/hr)";
				}
				template +=". ";
			}else if(this.stableNoteData.note.stoolStatus && this.stableNoteData.note.urineStatus){
				template +="Passed urine";
				if(this.stableNoteData.note.urineVolume != null && this.stableNoteData.note.urineVolume != ""){
					template += " ("+this.stableNoteData.note.urineVolume + " ml/kg/hr)";
				}
				template +=" and stool";
				if(this.stableNoteData.note.stoolTimes != null && this.stableNoteData.note.stoolTimes != undefined){
					template +=" " + this.stableNoteData.note.stoolTimes + " times";
				}
				template += ". ";
			}
		}

    if(this.stableNoteData.note.abdominalGirth != null){
      template += "Abdominal Girth recorded is " + this.stableNoteData.note.abdominalGirth + ". ";
    }

    if(this.stableNoteData.note.vomitStatus != null && this.stableNoteData.note.vomitStatus == true){
      template += "The baby had an episode of vomitting ";
      if(this.stableNoteData.note.vomitType != null){
        template += "of " + this.stableNoteData.note.vomitType + " size ";
      }
      if(this.stableNoteData.note.vomitColor != null){
        template += "of " + this.stableNoteData.note.vomitColor + " color ";
      }
      template += ". ";
    }

		if(this.stableNoteData.note.medicationStr != null && this.stableNoteData.note.medicationStr != ''){
			template += this.stableNoteData.note.medicationStr +'. ';
		}

		if(this.stableNoteData.note.generalnote != null && this.stableNoteData.note.generalnote != ''){
			template += 'General Note - ' + this.stableNoteData.note.generalnote +'. ';
		}
		if(this.stableNoteData.note.plan != null && this.stableNoteData.note.plan != ''){
			template += 'Plan - ' + this.stableNoteData.note.plan + '. ';
		}
		this.stableNoteData.note.notes = template;
	}

	isEmpty(object : any) : boolean {
		if (object == null || object == '' || object == 'null' || object.length == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	nth(d : any) {
		if (d > 3){
			if(d < 21){
				return 'th';
			}
		}

		switch (d % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	formatter = function(dateIn : Date, inFormat : string, outFormat : string){
		if(inFormat == "gmt"){
			if (outFormat == "utf") {
				let tzoffset : any = (new Date(dateIn)).getTimezoneOffset() * 60000; //offset in milliseconds
				let localISOTime : any = (new Date(new Date(dateIn).getTime() - tzoffset)).toISOString().slice(0,-1);
   				localISOTime = localISOTime+"Z";
				return localISOTime;
			} else if(outFormat == "default"){
				let date : any = new Date(dateIn);
				let tempDate : any = date+"";
				let dd : any = date.getDate();
				console.log(typeof(tempDate));
				let mm = tempDate.substring(3, 7);

				let yy = date.getFullYear();
				let h = (date.getHours() > 12) ? date.getHours() % 12 : date.getHours();
				let P = (date.getHours() >= 12) ? 'PM' : 'AM';
				let m = date.getMinutes();
				if ( dd < 10 ){
					dd = '0'+dd;
				}
				return dd+" "+mm+" "+ yy+" "+h+":"+m+" "+P ;
			}
		} else if(inFormat == "utf") {
			if (outFormat == "gmt") {
				let fullDate = new Date(dateIn);
				fullDate = new Date(fullDate.getTime() + (fullDate.getTimezoneOffset() * 60000));
				return fullDate;
			}
		}
	}
	refreshStableNotes(){
		this.getStableNoteData();
	}

	ngOnInit() {
		for(let i=1;i<=12;++i){
			if(i<10)
				this.hoursList.push("0"+i.toString());
			else
				this.hoursList.push(i.toString());
		}

		for(let i=0;i<60;++i){
			if(i<10)
				this.minutesList.push("0"+i.toString());
			else
				this.minutesList.push(i.toString());
		}
		this.stableNoteTempObj.printFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
    this.stableNoteTempObj.printTo = new Date();
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
    this.getStableNoteData();
	}

  showOrderInvestigation = function(){
    var dropDownValue = "Infection";
    this.selectedDecease = "Infection";
    for(var obj in this.stableNoteData.orders){
      for(var index = 0; index<this.stableNoteData.orders[obj].length;index++){
        var testName = this.stableNoteData.orders[obj][index].testname;
        if(this.investOrderNotOrdered.indexOf(testName) != -1)
          this.stableNoteData.orders[obj][index].isSelected = true;
      }
    }

    this.listTestsByCategory = this.stableNoteData.orders[dropDownValue];
    /*if(this.stableNoteData.note.orderSelectedText == null || this.stableNoteData.note.orderSelectedText == ''){
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
    for(var obj in this.stableNoteData.orders){
      for(var index = 0; index<this.stableNoteData.orders[obj].length;index++){
        if(this.stableNoteData.orders[obj][index].isSelected==true){
          var testName = this.stableNoteData.orders[obj][index].testname;

          this.investOrderNotOrdered.push(testName);
          if(this.investOrderSelected.indexOf(testName) == -1)
            this.stableNoteData.orders[obj][index].isSelected = false;

        } else {
          var testName = this.stableNoteData.orders[obj][index].testname;
          this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
        }
      }
    }
    console.log(this.investOrderNotOrdered);
    console.log("closeModalOrderInvestigation closing");
    $("#ballardOverlay").css("display", "none");
    $("#OrderInvestigationPopup").toggleClass("showing");
    this.populateStableNotesStr();
  };

  orderSelected = function(){
    //iterate ovver list for order list of selected...
    console.log("dsfasfdsf");
    this.stableNoteData.note.orderSelectedText = "";
    this.investOrderSelected = [];
    for(var obj in this.stableNoteData.orders){
      console.log(this.stableNoteData.orders[obj]);
      for(var index = 0; index<this.stableNoteData.orders[obj].length;index++){
        if(this.stableNoteData.orders[obj][index].isSelected==true){
          if(this.stableNoteData.note.orderSelectedText==''){
            this.stableNoteData.note.orderSelectedText =  this.stableNoteData.orders[obj][index].testname;
          }else{
            this.stableNoteData.note.orderSelectedText = this.stableNoteData.note.orderSelectedText +", "+ this.stableNoteData.orders[obj][index].testname;
          }
          this.investOrderSelected.push(this.stableNoteData.orders[obj][index].testname);
        } else {
          var testName = this.stableNoteData.orders[obj][index].testname;
          this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
          this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
        }
      }
    }
    $("#ballardOverlay").css("display", "none");
    $("#OrderInvestigationPopup").toggleClass("showing");
    this.populateStableNotesStr();

  }

  populateTestsListByCategory = function(assessmentCategory){
    this.selectedDecease = assessmentCategory;
    console.log(assessmentCategory);
    this.listTestsByCategory = this.stableNoteData.orders[assessmentCategory];
    console.log( this.listTestsByCategory);
  }

}
