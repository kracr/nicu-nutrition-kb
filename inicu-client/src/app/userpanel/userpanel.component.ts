import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Getuserlistdata } from './getUserListData';
import { Getrolevalue } from './getRoles';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { APIurl } from '../../../model/APIurl';
@Component({
  selector: 'userpanel',
  templateUrl: './userpanel.component.html',
  styleUrls: ['./userpanel.component.css']
})
export class UserpanelComponent implements OnInit {
	  selectedUserPannelTab : string;
	  isAddUser : string;
	  userObj : any;
	  userList : Array<Getuserlistdata>;
	  reportingDoctor : Array<string>;
    branchNameList : Array<string>;
	  allRoles : Array<string>;
	  allModules : Array<string>;
	  values : Array<Getrolevalue>;
	  roles : Array<string>;
	  valueList : Array<string>;
	  user: Getuserlistdata;
	  http: Http;
	  date: Date;
	  apiData : APIurl;
    branchName : string;

	  constructor(http: Http) {
	  	this.apiData = new APIurl();
	  	this.http = http;
	  	this.userList = new Array<Getuserlistdata>();
	  	this.userObj = {};
	  	this.selectedUserPannelTab = 'User List';
	  	this.isAddUser = 'Add User';
	  	this.reportingDoctor = [];
      this.branchNameList = [];
	  	this.allRoles = [];
	  	this.allModules = [];
	  	this.values = new Array<Getrolevalue>();
	  	this.roles = [];
	  	this.valueList = [];
	  	this.user = new Getuserlistdata;
	  	this.date = new Date();
      this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
	  	this.init();
	  }
	  init(){
	  	this.getUserListDataFun();
	  }
	  getUserListDataFun(){
	  	try
          {
            this.http.post(this.apiData.userList + 'test/' + this.branchName,
                JSON.stringify({
                someParameter: 1,
                someOtherParameter: 2
                })).subscribe(res => {
                    this.handleUserData(res.json());
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
	getRoleDataFun(){
	  	try
          {
            this.http.post(this.apiData.getRoles + 'test',
                JSON.stringify({
                someParameter: 1,
                someOtherParameter: 2
                })).subscribe(res => {
                    this.handleGetRole(res.json());
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
	handleGetRole(responseData: any){
		console.log(responseData);
		this.roles = responseData.allRoles;
		this.valueList = responseData.values;
	}
	handleUserData(responseData: any){
    console.log(responseData);
    this.userList = responseData.userList;
    this.reportingDoctor = responseData.reportingDoctor;
    this.branchNameList = responseData.branchNameList;
  }
	whichTab(value){
		this.selectedUserPannelTab = value;
	  	console.log(this.selectedUserPannelTab);
		if(value == 'Manage Role'){
			this.getRoleDataFun();
		}
	}

	statusChange(selectedRoleValue) {
	    console.log(selectedRoleValue);
	    try
	      {
	          this.http.post(this.apiData.setRoleStatus, selectedRoleValue).
	            subscribe(res => {
	                this.handleStatusChangeData(res.json());
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
	handleStatusChangeData(responseData){
		alert(responseData.message);
	}
	setStatus(selectedUser) {
		try
	      {
	          this.http.post(this.apiData.setStatus, {
                username: selectedUser.userName,
                status: selectedUser.status,
                userId: 'test'
                }).
	            subscribe(res => {
	                this.handleStatusData(res.json());
	           },
	            err => {
	              console.log("Error occured.")
	            }

	           );
	      }
	      catch(e){
	        console.log("Exception in http service:"+e);
	      };
		console.log(selectedUser);
	}
	handleStatusData(responseData){
		alert(responseData.message);
	}
	addUser(userDetails){
		userDetails.name = userDetails.firstName + ' ' + userDetails.lastName;
		console.log(userDetails);
		this.user = userDetails;
		try
	      {
	          this.http.post(this.apiData.addUser, this.user).
	            subscribe(res => {
	                this.handleAddUserData(res.json());
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
	handleAddUserData(returnedObj){
		console.log(returnedObj);
		alert(returnedObj.message);
	}
	editUser(userValue){
		console.log(userValue);
		this.user = userValue;
		this.user.edit = true;
		this.user.repeatPassword = this.user.password;
		this.user.firstName = this.user.name.split(' ')[0];
		this.user.lastName = this.user.name.split(' ')[1];
		console.log(this.user);
		this.selectedUserPannelTab = 'Add User';
		this.isAddUser = 'Edit User';
	}
	ngOnInit() {
	}
}
