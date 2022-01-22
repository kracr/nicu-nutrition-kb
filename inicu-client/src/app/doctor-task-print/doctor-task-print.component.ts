import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-doctor-task-print',
  templateUrl: './doctor-task-print.component.html',
  styleUrls: ['./doctor-task-print.component.css']
})
export class DoctorTaskPrintComponent implements OnInit {
  doctorTasksList : any;
  currentDate : Date;
  constructor(private router: Router){
    this.currentDate = new Date();
    this.doctorTasksList = [];
  }

  printPdf(){
      setTimeout(window.print, 1000);
        setTimeout(()=>{
        this.router.navigateByUrl('/dash/dashboard');
    },3000);
  }
  ngOnInit() {
    console.log("in Councelling sheet Print Page");
    if(JSON.parse(localStorage.getItem('doctorViewList')) != undefined){
      this.doctorTasksList = JSON.parse(localStorage.getItem('doctorViewList'));
    }
    this.printPdf();
  }
}
