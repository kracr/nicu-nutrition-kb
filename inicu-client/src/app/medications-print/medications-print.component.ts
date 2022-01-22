import { Component, OnInit, AfterViewInit } from '@angular/core';
import { APIurl } from '../../../model/APIurl';
import { Location } from '@angular/common';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';
import { BabyPrescriptionModel } from '../medications/babyPrescriptionModel';
@Component({
  selector: 'medications-print',
  templateUrl: './medications-print.component.html',
  styleUrls: ['./medications-print.component.css']
})
export class MedicationsPrintComponent implements OnInit,AfterViewInit {
  apiData : APIurl;
  uhid : string;
  childData : any;
	loggedUser : string;
	http: Http;
	router : Router;
  gestationDays : any;
  gestationWeeks : any;
  dol : any;
	prescription : BabyPrescriptionModel;
  currentDate : Date;
  daysOfContinuation : any;
  calBrand : string;
  ironBrand : string;
  multiVitaminBrand : string;
  vitaminDBrand : string;
  mctBrand : string;

  constructor(http: Http, router : Router,private _location: Location) {
    this.apiData = new APIurl();
    this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.childData = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.http = http;
		this.router = router;
    this.gestationDays = JSON.parse(localStorage.getItem('gestationDays'));
    this.gestationWeeks = JSON.parse(localStorage.getItem('gestationWeeks'));
    this.dol = JSON.parse(localStorage.getItem('dol'));
    this.prescription = new BabyPrescriptionModel();
    this.currentDate = new Date();
    this.calBrand = null;
    this.ironBrand = null;
    this.mctBrand = null;
    this.multiVitaminBrand = null;
    this.vitaminDBrand = null;
    this.getPrescription();
    console.log(this.childData);
  }

  getPrescription() {
    try{
      this.http.request(this.apiData.getPrescription + this.uhid + "/"
        + this.loggedUser + "/" + this.gestationDays + "/" + this.gestationWeeks + "/"
        + this.dol + "/").subscribe((res: Response) => {
          console.log("prescription dropdown");
          this.prescription = res.json();
          console.log(this.prescription);
          if(this.prescription != null && this.prescription.activePrescription != null){
            for(var i=0;i<this.prescription.activePrescription.length;i++){
              var startDate = new Date(this.prescription.activePrescription[i].medicineOrderDate);
              var medContiuationDayCount = Math.abs(Math.ceil((this.currentDate.getTime() - startDate.getTime())/(1000*60*60*24)));
              if(medContiuationDayCount == 0){
                medContiuationDayCount = 1;
              }
              this.prescription.activePrescription[i].medicineDay = medContiuationDayCount;
            }
          }

          if(this.prescription != null && this.prescription.babyfeedDetailList != null){
            for(var i = 0; i < this.prescription.enAddtivesBrandNameList.length; i++) {
              if(this.prescription.babyfeedDetailList.calBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.calBrand){
                this.calBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
              }

              if(this.prescription.babyfeedDetailList.ironBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.ironBrand){
                this.ironBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
              }

              if(this.prescription.babyfeedDetailList.multiVitaminBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.multiVitaminBrand){
                this.multiVitaminBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
              }

              if(this.prescription.babyfeedDetailList.vitaminDBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.vitaminDBrand){
                this.vitaminDBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
              }

              if(this.prescription.babyfeedDetailList.mctBrand != null && this.prescription.enAddtivesBrandNameList[i].enaddtivesbrandid == this.prescription.babyfeedDetailList.mctBrand){
                this.mctBrand = this.prescription.enAddtivesBrandNameList[i].brandname;
              }
            }
          }
      });
    }catch(e){
  		console.log("Exception in http service:" + e);
  	};
  }

  printPdf = function(){
    setTimeout(window.print, 3000);
    const self = this;
    if (window.matchMedia) {
      var mediaQueryList = window.matchMedia('print');
      const redirect = (mql)=>{
        if(!mql.matches){
          if (mediaQueryList) {
            mediaQueryList.removeListener(redirect);
            
          }
          self._location.back();
        }
  
      }

      mediaQueryList.addListener(redirect);
    }
  	// setTimeout( () => {
    //   this.router.navigateByUrl('/med/medications');
    // },3000);
	};

  ngOnInit(){
  }
  ngAfterViewInit(){
    this.printPdf();
  }
}
