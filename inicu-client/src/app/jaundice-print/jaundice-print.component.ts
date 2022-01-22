import { Component, OnInit } from '@angular/core';
//import { Chart } from 'angular-highcharts';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'jaundice-print',
  templateUrl: './jaundice-print.component.html',
  styleUrls: ['./jaundice-print.component.css']
})
export class JaundicePrintComponent implements OnInit {
  apiData : APIurl;
  http: Http;
  router : Router;
  childData : any;
  printDataObj : any;
  printData : any;
  currentDate : Date;
  gestationWeeks : any;
  gestationDays : any;
  constructor(http: Http, router: Router) {
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
    this.childData = JSON.parse(localStorage.getItem('selectedChild'));
    this.printDataObj = JSON.parse(localStorage.getItem('assessmentPrintData'));
    console.log(this.printDataObj);
    console.log("in assesmentSheetJaundicePrintController");
    this.currentDate = new Date();
    console.log(this.currentDate);
    this.init();
  }
  printPdf(){
    setTimeout(window.print, 800);
    setTimeout(()=>{
      this.backToAssessment();
      },3000);
  }
  init(){
    this.printDataObj = JSON.parse(localStorage.getItem('printDataObj'));
    this.childData = JSON.parse(localStorage.getItem('selectedChild'));
    if(this.childData.gestation) {
      console.log("found ",this.childData.gestation);
      this.gestationWeeks = this.childData.gestation.split("Weeks");
      var days = this.childData.gestation.match("Weeks(.*)Days");
      console.log(days);
      console.log(this.gestationWeeks);
      this.gestationWeeks = parseInt(this.gestationWeeks[0]);
      this.gestationDays = parseInt(days[1]);

    }
    else {
      console.log("no found");
      this.gestationWeeks = null;
      this.gestationDays = null;
    }
    console.log(this.printDataObj);
    localStorage.removeItem('printDataObj');
    
  }
  backToAssessment = function(){
    if(this.printDataObj.whichTab == 'Respiratory Systems'){
      this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
    }
    if(this.printDataObj.whichTab == 'Jaundice'){
      this.router.navigateByUrl('/jaundice/assessment-sheet-jaundice');
    }
    if(this.printDataObj.whichTab == 'Infection'){
      this.router.navigateByUrl('/infection/assessment-sheet-infection');
    } 
    if(this.printDataObj.whichTab == 'CNS'){
      this.router.navigateByUrl('/cns/assessment-sheet-cns');
    }  
    if(this.printDataObj.whichTab == 'Stable Notes'){
      this.router.navigateByUrl('/stable/assessment-sheet-stable');
    }
    if(this.printDataObj.whichTab == 'Miscellaneous Assessment'){
      this.router.navigateByUrl('/miscellaneous/assessment-sheet-miscellaneous');
    }
  };

  ngOnInit() {
    this.printPdf();
  }

}
