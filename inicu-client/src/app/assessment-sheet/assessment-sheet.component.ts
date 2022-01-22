import { Component, OnInit } from '@angular/core';
import { DoctorPanelComponent } from '../doctor-panel/doctor-panel.component';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { APIurl } from '../../../model/APIurl';

@Component({
  selector: 'assessment-sheet',
  templateUrl: './assessment-sheet.component.html',
  styleUrls: ['./assessment-sheet.component.css']
})
export class AssessmentSheetComponent implements OnInit {
  whichAssessmentPanelTab : string;
  isLoaderVisible : boolean;
  loaderContent : string;
  childObject : any;
  response : any;
  router : Router;
  http : Http;
  apiData : APIurl;
  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.isLoaderVisible = false;
    this.loaderContent = 'Loading...';
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.response = {
      "respiratory" : false,
      "jaundice" : false,
      "infection" : false,
      "cns" : false
    }

  }
  selectTab(selectedValue){
    this.whichAssessmentPanelTab = selectedValue;
    var currentUrl = window.location.href;
    if(this.whichAssessmentPanelTab == 'respiratory' && currentUrl.includes('/respiratory/') != true){
      this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'jaundice' && currentUrl.includes('/jaundice/') != true){
      this.router.navigateByUrl('/jaundice/assessment-sheet-jaundice');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'infection' && currentUrl.includes('/infection/') != true){
      this.router.navigateByUrl('/infection/assessment-sheet-infection');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'cns' && currentUrl.includes('/cns/') != true){
      this.router.navigateByUrl('/cns/assessment-sheet-cns');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'cardiac' && currentUrl.includes('/cardiac/') != true){
      this.router.navigateByUrl('/cardiac/assessment-sheet-cardiac');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'metabolic' && currentUrl.includes('/metabolic/') != true){
      this.router.navigateByUrl('/metabolic/assessment-sheet-metabolic');
      this.isLoaderVisible = true;
    }

    if(this.whichAssessmentPanelTab == 'miscellaneous' && currentUrl.includes('/miscellaneous/') != true){
      this.router.navigateByUrl('/miscellaneous/assessment-sheet-miscellaneous');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'stableNotes' && currentUrl.includes('/stable/') != true){
      this.router.navigateByUrl('/stable/assessment-sheet-stable');
      this.isLoaderVisible = true;
    }
    if(this.whichAssessmentPanelTab == 'renal' && currentUrl.includes('/renal/') != true){
      this.router.navigateByUrl('/renal/assessment-sheet-renal');
      this.isLoaderVisible = true;
    }
  }
  changeOfRoutes(){
  this.isLoaderVisible = false;
    if( this.router.url == '/respiratory/assessment-sheet-respiratory'){
      this.whichAssessmentPanelTab = 'respiratory';
    }
    if( this.router.url == '/jaundice/assessment-sheet-jaundice'){
      this.whichAssessmentPanelTab = 'jaundice';
    }
    if( this.router.url == '/infection/assessment-sheet-infection'){
      this.whichAssessmentPanelTab = 'infection';
    }
    if( this.router.url == '/cns/assessment-sheet-cns'){
      this.whichAssessmentPanelTab = 'cns';
    }
    if( this.router.url == '/cardiac/assessment-sheet-cardiac'){
      this.whichAssessmentPanelTab = 'cardiac';
    }
    if(this.router.url == '/metabolic/assessment-sheet-metabolic'){
      this.whichAssessmentPanelTab = 'metabolic';
    }
    if(this.router.url == '/miscellaneous/assessment-sheet-miscellaneous'){
      this.whichAssessmentPanelTab = 'miscellaneous';
    }
    if(this.router.url == '/stable/assessment-sheet-stable'){
      this.whichAssessmentPanelTab = 'stableNotes';
    }
    if(this.router.url == '/renal/assessment-sheet-renal'){
      this.whichAssessmentPanelTab = 'renal';
    }
  }
  ngOnInit() {
    console.log('In the Assessment Component');

    try {
      this.response = null;
        this.http.request(this.apiData.getAssessmentsStatus + "/" + this.childObject.uhid + "/",).subscribe(res => {
            this.response = res.json();
         },
          err => {
            console.log("Error occured.")
          });
      } catch(e){
        console.log("Exception in http service:"+e);
      };

    if(JSON.parse(localStorage.getItem('eventName')) != undefined){
      var eventName = JSON.parse(localStorage.getItem('eventName'));
      console.log(eventName);
      if(eventName == 'jaundice') {
        this.selectTab('jaundice');
      } else if(eventName == 'RDS' || eventName == 'Apnea'
        || eventName == 'PPHN' || eventName == 'Pneumothorax') {
          this.selectTab('respiratory');
      } else if(eventName == 'Sepsis' || eventName == 'VAP' || eventName == 'CLABSI') {
        this.selectTab('infection');
      } else if(eventName == 'Asphyxia' || eventName == 'Seizures' || eventName == 'IVH') {
        this.selectTab('cns');
      }
    }else{
    }
    this.changeOfRoutes();
  }

}
