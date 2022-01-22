import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-counselling-sheet-print',
  templateUrl: './counselling-sheet-print.component.html',
  styleUrls: ['./counselling-sheet-print.component.css']
})
export class CounsellingSheetPrintComponent implements OnInit {
  totalBeds : any;
  weightTrackingList : any;
  currentDate : Date;
  constructor(private router: Router) {
    this.currentDate = new Date();
  }
  printPdf(){
	  	setTimeout(window.print, 1000);
      	setTimeout(()=>{
	    	this.router.navigateByUrl('/dash/dashboard');
    },3000);
	}
  ngOnInit() {
    console.log("in Councelling sheet Print Page");
    if(JSON.parse(localStorage.getItem('councellingData')) != undefined){
      this.totalBeds = JSON.parse(localStorage.getItem('councellingData'));
      localStorage.removeItem('councellingData');
      console.log(this.totalBeds);
    }
    if(JSON.parse(localStorage.getItem('anthroprometryData')) != undefined){
      this.weightTrackingList = JSON.parse(localStorage.getItem('anthroprometryData'));
      localStorage.removeItem('anthroprometryData');
      console.log(this.weightTrackingList);
    }
    this.printPdf();
  }

  removeDays(value) {
    return value ? value.replace(" days", "") : value;
  }

}
