import { Component, OnInit } from '@angular/core';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';
@Component({
  selector: 'blood-product-print',
  templateUrl: './blood-product-print.component.html',
  styleUrls: ['./blood-product-print.component.css']
})
export class BloodProductPrintComponent implements OnInit {
  apiData : APIurl;
  uhid : string;
  childData : any;
	loggedUser : string;
	http: Http;
	router : Router;
  gestationDays : any;
  gestationWeeks : any;
  bloodProductPrintList : any;
  dol : any;
  constructor(http: Http, router : Router) {
    this.printPdf();
    this.apiData = new APIurl();
    this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.childData = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.http = http;
		this.router = router;
    this.gestationDays = JSON.parse(localStorage.getItem('gestationDays'));
    this.gestationWeeks = JSON.parse(localStorage.getItem('gestationWeeks'));
    this.bloodProductPrintList = JSON.parse(localStorage.getItem('bloodProductPrintList'));
    this.dol = JSON.parse(localStorage.getItem('dol'));
    console.log(this.childData);
    console.log(this.bloodProductPrintList);
  }

  printPdf = function(){
		setTimeout(function () {
			window.print();
		}, 3000);
  	setTimeout( () => {
        	this.router.navigateByUrl('/blood/blood-product');
    },3000);
	};

  ngOnInit(){
  }
}
