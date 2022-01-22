import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';

@Component({
  selector: 'email-management',
  templateUrl: './email-management.component.html',
  styleUrls: ['./email-management.component.css']
})
export class EmailManagementComponent implements OnInit {

  http: Http;
  router : Router;
  apiData : APIurl;
  emailData : any;
  validEmailId : string;

  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.emailData = {};
    this.validEmailId = null;
  }

  getEmailData() {
	console.log("comming into the getEmailData");
	 try{
        this.http.request(this.apiData.getEmailData).subscribe((res: Response) => {
          this.emailData = res.json();
        });
      } catch(e) {
            console.log("Exception in http service:"+e);
      };
  }

  populateItem(item : any) {
    this.emailData.currentItem = JSON.parse(JSON.stringify(item));
  }

  saveEmailData() {
		console.log("comming into the saveEmailData");
    var atpos = this.emailData.currentItem.user_email.indexOf("@");
    var dotpos = this.emailData.currentItem.user_email.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos+2 || dotpos+2 >= this.emailData.currentItem.user_email.length) {
        this.validEmailId = 'Not a valid E-Mail address';
        return;
    }else{
      this.validEmailId = null;
    }

    try{
      this.http.post(this.apiData.saveEmailData, this.emailData.currentItem).subscribe(res => {
        this.emailData = res.json();
      }, err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	}

  ngOnInit() {
    this.getEmailData();
  }

}
