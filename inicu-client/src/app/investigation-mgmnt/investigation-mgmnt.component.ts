import { Component, OnInit } from '@angular/core';
import { APIurl } from '../../../model/APIurl';
import {Http, Response} from '@angular/http';
import { Router } from '@angular/router';
import { InvestigationData } from './investigationData';
import { InvestigationPannelList} from './investigationPenelList';
import { AddTestObj }from './addTestObj';
import { InvestigationKeysPipe } from './keysPipe';
import * as $ from 'jquery/dist/jquery.min.js';
import { format } from 'url';
import { IfObservable } from 'rxjs/observable/IfObservable';
@Component({
  selector: 'investigation-mgmnt',
  templateUrl: './investigation-mgmnt.component.html',
  styleUrls: ['./investigation-mgmnt.component.css']
})
export class InvestigationMgmntComponent implements OnInit {
	apiData : APIurl;
	isLoaderVisible : boolean;
	loaderContent : string;
	http: Http;
	cmData : any;
	addTestObj : AddTestObj;
	selectedDecease : string;
	investigationData : InvestigationData;
	selectedPanelList : Array<any>;
	searchPanelName : string;
	panelName : string;
	editPanelName : string;
	userInfoMessage : string;
	branchName : string;
	selectedIndex : number;
	panelTestCheckbox : boolean;
	savePanelData : Array<InvestigationPannelList>;
	isUpdateButtonVisible : boolean;
	loggedUser : string;
	listTestsByCategory : Array<any>;
	panelListByCategory : Array<any>;
	constructor(http: Http, private router: Router) {
		this.isLoaderVisible = false;
		this.loaderContent = 'Saving...';
		this.apiData = new APIurl();
	  	this.http = http;
		this.listTestsByCategory = [];
		this.panelListByCategory = [];
		this.selectedPanelList = [];
		this.userInfoMessage = "";
		this.editPanelName = "";
		this.isUpdateButtonVisible = false;
	  	this.investigationData = new InvestigationData();
		this.addTestObj = new AddTestObj();
		this.searchPanelName = '';
		this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
	}

	//below code is used for get data from getTestsDetails Api
	getInvestigationData(){
		console.log('this is getInvestigation data function')
		try{
            this.http.request(this.apiData.getTestsDetails + this.branchName + "/")
              .subscribe((res: Response) => {
                console.log(res.json());
				this.investigationData = res.json();
				this.restrictToPopulateMultiplePanel();
				   
           });
          }catch(e){
            console.log("Exception in http service:"+e);
          };

	}

	// populate panel list based on panel name
	populatePanelListByCategory(index,panel){
		this.panelListByCategory = [];
		for(var i=0; i<this.investigationData.investigationPannelList.length;i++){
			if(panel == this.investigationData.investigationPannelList[i].pannelName){
				this.panelListByCategory.push(this.investigationData.investigationPannelList[i]);
			}
		}
		console.log(this.panelListByCategory);
		this.selectedIndex = index;

	}

//Create Panel Modal
	createPanelModal(whichButton){
		if(whichButton == "create panel"){
			this.isUpdateButtonVisible = false;
			this.selectedPanelList = [];
			this.panelName = "";
			this.investigationData.vendorLists.forEach(item=>{
				item.flag = false; 
			})
		} 
		if(whichButton == "edit panel"){
			this.isUpdateButtonVisible = true;
		}
		$("#createPanelModalOverlay").css("display", "block");
		$("#createPanelModal").addClass("showing");
	}

//close panel modal
	closePanelModal = function() {
		this.userInfoMessage = "";
		this.searchPanelName = '';
		$("#createPanelModalOverlay").css("display", "none");
		$("#createPanelModal").toggleClass("showing");
	};

//add and remove panel based on checkbox selected or not
	onCheckInvestigationPanel(event,value,id){
		const checked = event.target.checked; // stored checked value true or false
		let selectedPanelObj = {
		testid : id,
		testname : value
		};
		let indexToDelete = undefined;

		if (checked) {
			this.selectedPanelList.push(selectedPanelObj); // push the selectedPanelObj in array if checked
			console.log(this.selectedPanelList);
		}else{
			this.selectedPanelList.forEach((obj, currIndex) => { //Find the index of stored panel
				if(obj.testname === value) {
					indexToDelete = currIndex;
				}
			});
		}
		if(indexToDelete !== undefined) {
			this.selectedPanelList.splice(indexToDelete, 1);
			console.log(this.selectedPanelList);
		}
	}

	//below code is used for save investigation panel
	saveInvestigationPanel(){
		this.isLoaderVisible = true;
		this.loaderContent = 'Saving...';	
		try{
			this.http.post(this.apiData.saveInvestigationPanel +  this.panelName + "/" + this.loggedUser + "/" + this.branchName + "/" ,this.selectedPanelList).subscribe(res => {
				this.investigationData = res.json().returnedObject;
				this.isLoaderVisible = false;
				this.loaderContent = 'Saving...';
				this.restrictToPopulateMultiplePanel();
				console.log("Data after panel Saved ");
				var index;
				for(var i=0; i<this.savePanelData.length;i++){
					if(this.panelName == this.savePanelData[i].pannelName){
					index = i;
					break;
					}
				}
				this.populatePanelListByCategory(index,this.panelName);
			},err => {
				console.log("Error occured.")
			});
		}
		catch(e){
			console.log("Exception in http service:"+e);
		};
			
	}

	//below code is used for delete investigation panel
	deleteInvestigationPanel(){
		var panelName =  localStorage.getItem("deletePanelName");
		this.isLoaderVisible = true;
		if(panelName != "undefined" && panelName != ''){
			this.loaderContent = 'Deleting...';	
			let dataParams;
			try{
				this.http.post(this.apiData.deleteInvestigationPanel +  panelName + "/" + this.branchName + "/",dataParams).subscribe(res => {

					this.investigationData = res.json().returnedObject;
					this.isLoaderVisible = false;
					this.loaderContent = 'Deleting...';
					this.restrictToPopulateMultiplePanel();
					console.log("Data after panel delete ");
					this.panelListByCategory = [];
					this.closeInvestigationPanelModal();
					
				},err => {
					console.log("Error occured.")
				});

			}
			catch(e){
				console.log("Exception in http service:"+e);
			};
		}

		if(localStorage.getItem("deletePanelName")!= undefined){
			localStorage.removeItem("deletePanelName");
		}
	}

	//below code is used for create delete investigation panel modal
	deleteInvestigationPanelModal(panelName){
		$("#deletePanelModalOverlay").css("display", "block");
		$("#deletePanelModal").addClass("showing");
		localStorage.setItem("deletePanelName",panelName);
	}

	//below code is used for close delete investigation panel modal
	closeInvestigationPanelModal = function() {
		$("#deletePanelModalOverlay").css("display", "none");
		$("#deletePanelModal").toggleClass("showing");
	};

	//below code is used for update investigation panel
	updateInvestigationPanel(){
		this.isLoaderVisible = true;
		this.loaderContent = 'Updating...';	
		try{
			this.http.post(this.apiData.editInvestigationPanel + this.editPanelName + "/" + this.panelName + "/" + this.loggedUser + "/" + this.branchName + "/" ,this.selectedPanelList).subscribe(res => {
				this.investigationData = res.json().returnedObject;
				this.isLoaderVisible = false;
				this.loaderContent = 'Updating...';
				this.restrictToPopulateMultiplePanel();
				console.log("Data after panel update ");
				var index;
				for(var i=0; i<this.savePanelData.length;i++){
					if(this.panelName == this.savePanelData[i].pannelName){
					index = i;
					break;
					}
				}
				this.populatePanelListByCategory(index,this.panelName);
			},err => {
				console.log("Error occured.")
			});
		}
		catch(e){
			console.log("Exception in http service:"+e);
		};
		
		// this.closePanelModal();
	}

	//below code is used for edit investigation panel
	editInvestigationPanel(panel){
		this.createPanelModal("edit panel");
		this.panelName = panel;
		this.editPanelName = panel;
		var temporaryVendorList =[];
		this.selectedPanelList = [];

		this.investigationData.investigationPannelList.forEach(item=>{
			if(item.pannelName == panel){
				let selectedPanelObj = {
					testid : item.testId,
					testname : item.testName
				};			
				this.selectedPanelList.push(selectedPanelObj);
			}
		})

		if(this.selectedPanelList.length>0){
			for(var i=0; i < this.selectedPanelList.length; i++){
				temporaryVendorList.push(this.selectedPanelList[i]);
			}

			for(var i=0; i< this.investigationData.vendorLists.length;i++){
				var isSelected = false;
				for(var j=0; j < this.selectedPanelList.length; j++){
					if(this.investigationData.vendorLists[i].testname == this.selectedPanelList[j].testname){
						isSelected = true;
						break;
					}
				}	

				if(!isSelected){
					temporaryVendorList.push(this.investigationData.vendorLists[i]);
				}
			}	
			
			this.investigationData.vendorLists = temporaryVendorList;
			var i : number;
			for(i=0; i < this.selectedPanelList.length; i++){
				this.investigationData.vendorLists[i].flag = true; 
			}

			for(; i < this.investigationData.vendorLists.length; i++){
				this.investigationData.vendorLists[i].flag = false; 
			}
		}
	}

	//below code is used for restrict user to save multiple panel
	restrictToSaveMultiplePanel(operation){
		if(this.panelName != "" && this.panelName != undefined && this.panelName != null){
			if(this.investigationData.investigationPannelList.length >= 0){
				for(var i=0; i<this.investigationData.investigationPannelList.length;i++){
					if(this.panelName == this.investigationData.investigationPannelList[i].pannelName){ 
						if(operation == 'save' || (operation == 'update' && this.editPanelName != this.panelName)){
							this.userInfoMessage = "Panel " + this.panelName + " already exist."
							return;
						}
					}
				}
				if(operation == 'save'){
					this.saveInvestigationPanel();
				}
				if(operation == 'update'){
					this.updateInvestigationPanel();
				}
				this.userInfoMessage = "";
				this.editPanelName = "";
				this.searchPanelName = '';
	
			}		
		}
		this.closePanelModal();
	}

	//below code is used for restrict to populate multiple panel
	restrictToPopulateMultiplePanel(){
		this.savePanelData = [];
	// delete all duplicates from the array
		for(var index=0;index<this.investigationData.investigationPannelList.length; index++ ) {
			var isPresent = false;
			for(var j=0;j<this.savePanelData.length;j++){
				if(this.investigationData.investigationPannelList[index].pannelName == this.savePanelData[j].pannelName) {
					isPresent = true;
					break;
				}	
			}

			if(!isPresent){
				this.savePanelData.push(this.investigationData.investigationPannelList[index]);
			}	
		}
	}

	ngOnInit() {
		this.getInvestigationData();
	}

}
