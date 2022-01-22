import { Component, OnInit } from '@angular/core';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { DischargeTempObj } from './dischargetempObj';
import { DischargedSummaryData } from './dischargedSummaryData';
import { Router } from '@angular/router';
import * as $ from 'jquery';
@Component({
  selector: 'app-discharge-print',
  templateUrl: './discharge-print.component.html',
  styleUrls: ['./discharge-print.component.css']
})
export class DischargePrintComponent implements OnInit {
	  apiData : APIurl;
  	http: Http;
  	dischargeTempObj : DischargeTempObj;
  	dischargedSummaryData : DischargedSummaryData;
  	whichDischargeSummaryPage : string;
  	dischargeSummaryPage2WhichTab : string;
  	courseWhichTab : string;
  	isComeFromDischargeList : boolean;
    isComeFromDischargePreview : boolean;
    timeOfBirthStr : string;
    adviceOnDischarge : string;
    babyAge : string;
    isPhysicalExamination : boolean;
  	constructor(http: Http,private router: Router) {
	  	this.http = http;
	  	this.dischargeTempObj = new DischargeTempObj();
	   	this.apiData = new APIurl();
	   	this.whichDischargeSummaryPage = 'page1';
	   	this.isComeFromDischargeList = false;
      this.isComeFromDischargePreview = false;
      this.adviceOnDischarge = localStorage.getItem('adviceOnDischarge');
      this.babyAge = null;
      this.isPhysicalExamination = false;
      console.log(this.adviceOnDischarge);
	}
  getDischargedSummary(){
    var child = JSON.parse(localStorage.getItem('selectedChild'));
    if(localStorage.getItem('printPatientObj') != undefined && localStorage.getItem('printPatientObj') != null && localStorage.getItem('printPatientObj') != ''){
      child = JSON.parse(localStorage.getItem('printPatientObj'));
      console.log(localStorage);
      this.isComeFromDischargeList = true;
    }else{
      this.dischargeTempObj.dateofdischarge = new Date();
    }

	try{
        this.http.request(this.apiData.getDischargeSummary+'/'+child.episodeid+'/'+child.uhid+'/').subscribe((res: Response) => {
            this.handleResponseData(res.json());
	    });
	    }catch(e){
	        console.log("Exception in http service:"+e);
	 	};
	}
	handleResponseData(responseData){
		console.log(responseData);
		this.dischargedSummaryData = responseData;
    if(this.dischargedSummaryData.babyDetails.timeofbirth != null){
      var timeOfBirthHr = this.dischargedSummaryData.babyDetails.timeofbirth.slice(0, 2);
      var timeOfBirthMin = this.dischargedSummaryData.babyDetails.timeofbirth.slice(3, 5);
      var timeOfBirthMer = this.dischargedSummaryData.babyDetails.timeofbirth.slice(6, 8);
      this.timeOfBirthStr = timeOfBirthHr +':'+timeOfBirthMin+ ' '+ timeOfBirthMer;
    }

    this.babyAge = JSON.parse(localStorage.getItem('babyAge'));
    if(this.babyAge == null || this.babyAge == '' ||this.babyAge == '0'){
        this.babyAge = '1';
    }

    if((this.dischargedSummaryData.babyPhysicalExamData.apearance != null && this.dischargedSummaryData.babyPhysicalExamData.apearance != 'Active')
     || (this.dischargedSummaryData.babyPhysicalExamData.head_neck != null && this.dischargedSummaryData.babyPhysicalExamData.head_neck == 'Abnormal')
     || (this.dischargedSummaryData.babyPhysicalExamData.palate != null && this.dischargedSummaryData.babyPhysicalExamData.palate != 'Normal')
     || (this.dischargedSummaryData.babyPhysicalExamData.lip != null && this.dischargedSummaryData.babyPhysicalExamData.lip != 'Normal')
     || (this.dischargedSummaryData.babyPhysicalExamData.eyes != null && this.dischargedSummaryData.babyPhysicalExamData.eyes == 'Abnormal')
     || (this.dischargedSummaryData.babyPhysicalExamData.skin != null && this.dischargedSummaryData.babyPhysicalExamData.skin != 'Normal')
		 || (this.dischargedSummaryData.babyPhysicalExamData.chest != null && this.dischargedSummaryData.babyPhysicalExamData.chest == 'Abnormal')
		 || (this.dischargedSummaryData.babyPhysicalExamData.abdomen != null && this.dischargedSummaryData.babyPhysicalExamData.abdomen == 'Abnormal')
     || (this.dischargedSummaryData.babyPhysicalExamData.anal != null && this.dischargedSummaryData.babyPhysicalExamData.anal != 'Patent')
     || (this.dischargedSummaryData.babyPhysicalExamData.genitals != null && this.dischargedSummaryData.babyPhysicalExamData.genitals == 'Abnormal')
     || (this.dischargedSummaryData.babyPhysicalExamData.reflexes != null && this.dischargedSummaryData.babyPhysicalExamData.reflexes != 'Abnormal')
     || (this.dischargedSummaryData.babyPhysicalExamData.cong_malform != null && this.dischargedSummaryData.babyPhysicalExamData.cong_malform == 'Yes')){
      this.isPhysicalExamination = true;
    }

		console.log(this.dischargedSummaryData);
		setTimeout(window.print, 1000);
	    setTimeout(()=>{
	        if(this.isComeFromDischargeList == true){
	        	this.router.navigateByUrl('/discharged/dischargePatients');
	        }
        	else{
            this.router.navigateByUrl('/discharge/discharge-summary');
	    	  }
			},3000)
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
	        dd = '0'+ dd;
	      }
	      if ( mm < 10 ){
	        mm = '0'+ mm;
	      }
	      return yy + "-" + mm + "-" + dd ;
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
		ngOnInit() {
	  		this.getDischargedSummary();
	  	}
}
