import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-admission-print',
  templateUrl: './admission-print.component.html',
  styleUrls: ['./admission-print.component.css']
})
export class AdmissionPrintComponent implements OnInit {
	admitPatientModel : any;
	dropDownData : any;
	tempBedArray : any;
	isEditProfile: boolean;
  timeOfBirthStr : string;
  timeOfAdmissionStr : string;
  constructor(private router: Router) { }
  printPdf(){
	  	setTimeout(()=>{
        	window.print();
      	},3000);
      	setTimeout(()=>{
	    	if(this.isEditProfile == true){
	    		this.router.navigateByUrl('/admis/view-profile');
	    	}
	    	if(this.isEditProfile == false){
	    		this.router.navigateByUrl('/admis/admissionform');
	    	}
            
	    },3000);
	}
  ngOnInit() { 
  	this.admitPatientModel = JSON.parse(localStorage.getItem('admissionformdata'));
  	this.dropDownData = JSON.parse(localStorage.getItem('dropDownData')); 
  	this.tempBedArray = JSON.parse(localStorage.getItem('tempBedArray'));
  	this.isEditProfile = JSON.parse(localStorage.getItem('isEditProfile'));
  	console.log('The Data is');
  	console.log(this.admitPatientModel);
  	console.log(this.dropDownData);
  	console.log(this.tempBedArray);
  	if(this.admitPatientModel.babyDetailObj.timeofbirth != null){
      var timeOfBirthHr = this.admitPatientModel.babyDetailObj.timeofbirth.slice(0, 2); 
      var timeOfBirthMin = this.admitPatientModel.babyDetailObj.timeofbirth.slice(3, 5);
      var timeOfBirthMer = this.admitPatientModel.babyDetailObj.timeofbirth.slice(6, 8);
      this.timeOfBirthStr = timeOfBirthHr +':'+timeOfBirthMin+ ' '+ timeOfBirthMer;
    }
    if(this.admitPatientModel.babyDetailObj.timeofadmission != null){
      var timeOfAdmissionHr = this.admitPatientModel.babyDetailObj.timeofadmission.slice(0, 2); 
      var timeOfAdmissionMin = this.admitPatientModel.babyDetailObj.timeofadmission.slice(3, 5);
      var timeOfAdmissionMer = this.admitPatientModel.babyDetailObj.timeofadmission.slice(6, 8);
      this.timeOfAdmissionStr = timeOfBirthHr + ':' + timeOfAdmissionMin + ' '+ timeOfAdmissionMer;
    }
    
    this.printPdf();
  }

}
