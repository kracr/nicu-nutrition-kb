import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-screening-print',
  templateUrl: './screening-print.component.html',
  styleUrls: ['./screening-print.component.css']
})
export class ScreeningPrintComponent implements OnInit {
  childData : any;
  gestWeek : string;
  gestDays : string;
  gestationDays : number;
  gestationWeeks : number;
  printRopList : any;
  dateAndTimeOfScreening : any;
  rop_left_plus : any;
  rop_right_plus : any;

  constructor(private router: Router) {
    this.childData = JSON.parse(localStorage.getItem('selectedChild'));
    this.gestationWeeks = JSON.parse(localStorage.getItem('gestationWeeks'));
    this.gestationDays = JSON.parse(localStorage.getItem('gestationDays'));
    if(this.childData.gestation != null){
      this.gestWeek = this.childData.gestation.substr(0, 2);
      this.gestDays = this.childData.gestation.substr(9, 1);
    }
    this.printRopList = JSON.parse(localStorage.getItem("printRopList"));
    console.log(this.printRopList);
    if(this.printRopList != null && this.printRopList != undefined && this.printRopList !=''){
      if(this.printRopList.is_rop_left != null){
        if(this.printRopList.is_rop_left){
          if(this.printRopList.rop_left_plus){
            this.rop_left_plus = "Present";
          }
          if(!this.printRopList.rop_left_plus){
            this.rop_left_plus = "Absent";
          }
        }
      }
     if(this.printRopList.is_rop_right != null){
      if(this.printRopList.is_rop_right){
        if(this.printRopList.rop_right_plus){
          this.rop_right_plus = "Present";
        }  
        if(!this.printRopList.is_rop_left){
          this.rop_left_plus = "Absent";
        }
      }
     }
    }
    this.dateAndTimeOfScreening = JSON.parse(localStorage.getItem("dateAndTimeOfScreening"));
   }

  printPdf(){
    setTimeout(window.print, 1000);
      setTimeout(()=>{
      this.router.navigateByUrl('/screen/screening');
  },3000);
  }
  
  ngOnInit() {
    this.printPdf();
    localStorage.removeItem("printRopList");
    localStorage.removeItem("dateAndTimeOfScreening");
  }

}
