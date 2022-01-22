import { Component, OnInit,EventEmitter } from '@angular/core';
import { AuthService } from '../auth.service';
import { AuthenticationData } from './authenticationData';
import { FormStatus } from './formStatus';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import {Http, Response} from '@angular/http';
import { Keyvalue } from '../userpanel/Keyvalue';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  authenticationData: AuthenticationData;
  formStatus : FormStatus;
  http: Http;
  apiData : APIurl;
  isLoaderVisible : boolean;
  loaderContent : string;
  branchNameList : any;
  selectedOption: string;
  keyValue : Keyvalue;
  options = [];


  constructor(public authService: AuthService, private router: Router,http: Http) {
    this.http = http;
    this.authenticationData = new AuthenticationData();
    this.formStatus = new FormStatus();
    this.apiData = new APIurl();
    this.isLoaderVisible = false;
    this.loaderContent = 'Loading...';
    this.keyValue = new Keyvalue();

  }
  doLogin = function(){
      localStorage.clear();
      this.isLoaderVisible = true;
      try
        {
          this.authenticationData.branchName = this.selectedOption;
          if(this.authenticationData.branchName != null){
            this.authenticationData.branchName = this.authenticationData.branchName.trim();
          }
          this.http.post(this.apiData.login,
            this.authenticationData).subscribe(res => {
          this.formStatus = res.json();
          console.log(res.json());
          // $rootScope.logedInUser = response.;
          // $rootScope.roleStatus = response.roleStatus;
          localStorage.setItem('logedInUser', JSON.stringify(res.json().returnedObject));
          localStorage.setItem('roleObject', JSON.stringify(res.json().roleStatus));
          var roleObject = JSON.parse(localStorage.getItem('roleObject'));
          console.log(roleObject);
          this.authService.obj.userName=this.authenticationData.username;
          this.authService.obj.branchName=this.authenticationData.branchName;
          if(res.json().type=="success"){
            this.router.navigateByUrl('/dash/dashboard');
          }
          else{
            console.log(res.json().message);
            this.isLoaderVisible = false;
          }
          },
          err => {
            console.log("Error occured.")
          }
        );
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
  getSettingReference = function(){
    try{
        this.http.request(this.apiData.getSettingReference).subscribe(res => {
          localStorage.setItem('settingReference', JSON.stringify(res.json()));
          this.branchNameList = JSON.parse(localStorage.getItem('settingReference')).branchNameList;
          for(var i=0;i<this.branchNameList.length;++i){
            this.keyValue = new Keyvalue();
            this.keyValue.key = "" + i + "";
            this.keyValue.value = this.branchNameList[i];
            this.options.push(this.keyValue);
          }
        });
      }catch(e){
          console.log("Exception in http service:"+e);
      };
    };
    ngOnInit() {
      this.getSettingReference();
    };

  print() {
    alert(this.selectedOption);
  };
}
