import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'nursing-notification',
  templateUrl: './nursing-notification.component.html',
  styleUrls: ['./nursing-notification.component.css']
})
export class NursingNotificationComponent implements OnInit {

  apiData : APIurl;
	http: Http;
	router : Router;
  uhid : string;
  doctorOrder : any;

  constructor(http: Http, router : Router) {
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
  }

  closeNotification(){
    $("#NotificationOverlay").css("display", "none");
    $("#NotificationPopup").toggleClass("showing");
  }

  showNotificationModal(){
    console.log('In The Show Notification Modal');
    $("#NotificationOverlay").css("display", "block");
    $("#NotificationPopup").addClass("showing");
  }

	getDoctorOrder() {
    var currentDate = new Date();
    if(currentDate.getHours() < 8 || (currentDate.getHours() == 8 && currentDate.getMinutes() <= 30)) {
      currentDate = new Date(currentDate.getTime() - (24 * 60 * 60 * 1000));
    }

    currentDate.setHours(8);
    currentDate.setMinutes(30);
    currentDate.setSeconds(0);
    currentDate.setMilliseconds(0);

		try{
			this.http.request(this.apiData.getDoctorOrder + this.uhid + "/" + currentDate.getTime() + "/").subscribe((res: Response) => {
				console.log("inside getDoctorOrder");
				this.doctorOrder = res.json();
        if(this.doctorOrder == null||this.doctorOrder.length == 0){}
        else{
            this.showNotificationModal();
        }
      });
		}catch(e){
			console.log("Exception in http service:"+e);
		};
	}

  ngOnInit() {
    this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.getDoctorOrder();
  }

}
