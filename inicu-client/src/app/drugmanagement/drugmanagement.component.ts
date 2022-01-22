import { Component, OnInit } from '@angular/core';
import { DrugData } from './Drugdata';
import {Http, Response} from '@angular/http';
import { APIurl } from '../../../model/APIurl';
@Component({
  selector: 'drugmanagement',
  templateUrl: './drugmanagement.component.html',
  styleUrls: ['./drugmanagement.component.css']
})
export class DrugmanagementComponent implements OnInit {
	selectedDrugTab : string;
	tab_value : string;
	drugsData : DrugData;
	formulaValue : string;
	isEdit : boolean;
	http: Http;
	apiData : APIurl;
	isOthers : boolean;
	mentionOthers : string;
 	constructor(http: Http) {
    	this.apiData = new APIurl();
		this.selectedDrugTab = 'List of Drug';
	  	this.tab_value = 'Add Drug';
	  	this.http = http;
	  	this.isEdit = false;
	  	this.formulaValue = 'Body weight x';
	}
	whichTab(tabValue){
	   	this.selectedDrugTab = tabValue;
	   	console.log(this.selectedDrugTab);
	   	if(this.selectedDrugTab  == 'List of Drug'){
	   		this.getDrugData();
	   	}
	}
	getDrugData(){
		try
        {
		    this.http.post(this.apiData.listMedicine,
	        JSON.stringify({})).subscribe(res => {
		    	this.handleDrugData(res.json());
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
	handleDrugData(responseData){
		console.log(responseData);
		this.drugsData = responseData;
		console.log(this.drugsData);
	}
	deleteDrug(medId){
		try
	        {
			    this.http.post(this.apiData.deleteMedicine+ medId + '/test' ,
		        JSON.stringify({})).subscribe(res => {
			    	this.handleDeleteDrugData(res.json());
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
		handleDeleteDrugData(responseData){
			this.drugsData = responseData;
			console.log(this.drugsData);
			alert(this.drugsData.message.message);
		}

    isEmpty(object : any) : boolean {
      if (object == null || object == '' || object == 'null' || object.length == 0) {
        return true;
      }
      else {
        return false;
      }
    };

    submitStrength(){
      var brand = this.drugsData.brand;
      //drug.edit = this.isEdit;
      if(this.isEmpty(brand.medname) || this.isEmpty(brand.brandName) || this.isEmpty(brand.brandStrength) || this.isEmpty(brand.brandStrengthUnit)){
        return;
      }
      for(var i = 0;i < this.drugsData.drugsList.length;i++){
        if(this.drugsData.drugsList[i].medname == brand.medname){
          brand.medid = this.drugsData.drugsList[i].medid;
          break;
        }
      }
      try{
        this.http.post(this.apiData.addBrand ,
          brand).subscribe(res => {
          this.handleSaveBrandData(res.json());
        },
        err => {
          console.log("Error occured.")
        });
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }

		submitDrug(){
			console.log("drug being submitted");
			console.log(this.drugsData.drug);

			var drug = this.drugsData.drug;
			drug.edit = this.isEdit;

//			console.log(loggedUserObject);
			drug.userid = 'test';

			var isSuccessful = true;
			console.log("before API call Object is");
			console.log(drug);
			if(isSuccessful){
				try
		        {
				    this.http.post(this.apiData.addMedicine ,
			        drug).subscribe(res => {
				    	this.handleSaveDrugData(res.json());
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
			else{
				console.log("Error occured.")
			}
		}
    handleSaveBrandData(responseData){
			console.log(responseData);
			alert(responseData.message.message);
			this.selectedDrugTab = 'Add Strength';
		}
		handleSaveDrugData(responseData){
			console.log(responseData);
			alert(responseData.message.message);
			this.selectedDrugTab = 'Add Drug';
		}
		updateBrand(medName){
			this.drugsData.drug.brand = medName;
		}
		formulaChange(){
			this.formulaValue = "Body weight x "+ this.drugsData.drug.suspensiondose + " " + this.drugsData.drug.suspensiondoseunit;
		}
		typeOthers(){
			console.log(this.drugsData.drug.medicationtype);
			if(this.drugsData.drug.medicationtype == "TYPE0006"){
				this.isOthers = true;
			}
			else{
				this.isOthers = false;
				this.mentionOthers = "";
			}
		}
		populateDrug(drug){
			this.isEdit = true;
			this.tab_value = 'EDIT DRUG';
			this.selectedDrugTab = 'Add Drug';
			this.drugsData.drug = drug;
		};
	ngOnInit() {
	  	this.getDrugData();
	}
}
