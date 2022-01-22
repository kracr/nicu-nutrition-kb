import { Component, OnInit } from '@angular/core';
import { BothGraphData } from '../growth-chart/bothGraphData';
import { Chart } from 'angular-highcharts';
import { Router } from '@angular/router';

@Component({
  selector: 'growth-chart-print',
  templateUrl: './growth-chart-print.component.html',
  styleUrls: ['./growth-chart-print.component.css']
})
export class Growthchartprint implements OnInit {
	router : Router;
	weightFiftyArray : any;
	weightNinetyArray : any;
	weightNinetySevenArray : any;
	weightTenArray : any;
	weightThreeArray : any;
	weightGestArray : any;
	weightArray : any;
	heightArray : any;
	heightFiftyArray : any;
	heightNinetyArray : any;
	heightNinetySevenArray : any;
	heightTenArray : any;
	heightThreeArray : any;
	headFiftyArray : Array<number>;
	headNinetyArray : Array<any>;
	headNinetySevenArray : any;
	headTenArray : any;
	headThreeArray : any;
	headGestArray : Array<any>;
	headArray : Array<any>;
	chartOne : Chart;
	chartTwo : Chart;
	chartThree : Chart;
	chartFour : Chart;
	chartGrowthOne : Chart;
	chartGrowthTwo : Chart;
	chartGrowthThree : Chart;
	chartGrowthFour : Chart;
	childData : any;
	today : Date;
	gestWeek : any;
	gestDays : any;
	growthChart : any;
	interWeightFiftyArray : any;
	interWeightNinetyArray : any;
	interWeightNinetyFiveArray : any;
	interWeightNinetySevenArray : any;
	interWeightTenArray : any;
	interWeightThreeArray : any;
	interWeightFiveArray : any;
	interWeightGestArray : any;
	interWeightArray : any;
	interHeightFiftyArray : any;
	interHeightNinetyArray : any;
	interHeightNinetySevenArray : any;
	interHeightTenArray : any;
	interHeightThreeArray : any;
	interHeightNinetyFiveArray : any;
	interHeightFiveArray : any;
	interHeightGestArray : any;
	interHeightArray : any;
	interHeadFiftyArray : any;
	interHeadNinetyArray : any;
	interHeadNinetySevenArray : any;
	interHeadTenArray : any;
	interHeadThreeArray : any;
	interHeadNinetyFiveArray : any;
	interHeadFiveArray : any;
	interHeadArray : any;
	interHeadGestArray : any;
	gestationDays : any;
	gestationWeeks : any;
    constructor(router : Router){
	  	this.router = router;
		this.today = new Date();
	    var dd:any = this.today.getDate();
	    var mm:any = this.today.getMonth()+1; //January is 0!
	    var yyyy   = this.today.getFullYear();
        if(dd<10){
	    	dd = '0'+dd
	    }
        if(mm<10) {
	    	mm = '0'+mm
	    }
        this.today = new Date(mm + '/' + dd + '/' + yyyy);
		this.childData = JSON.parse(localStorage.getItem('selectedChild'));
	    this.init();
	}

    init(){
	    this.growthChart = localStorage.getItem("growthChart");
	    console.log("growthChart" +this.growthChart);
	    if(this.growthChart == 'true'){
			console.log("growthChart");
		    this.weightFiftyArray = JSON.parse(localStorage.getItem('weightFiftyArray'));
		    this.weightNinetyArray = JSON.parse(localStorage.getItem('weightNinetyArray'));
		    this.weightNinetySevenArray = JSON.parse(localStorage.getItem('weightNinetySevenArray'));
		    this.weightTenArray = JSON.parse(localStorage.getItem('weightTenArray'));
		    this.weightThreeArray = JSON.parse(localStorage.getItem('weightThreeArray'));
		    this.weightArray = JSON.parse(localStorage.getItem('weightArray'));
		    this.heightArray = JSON.parse(localStorage.getItem('heightArray'));
		    this.heightFiftyArray = JSON.parse(localStorage.getItem('heightFiftyArray'));
		    this.heightNinetySevenArray = JSON.parse(localStorage.getItem('heightNinetySevenArray'));
		    this.heightNinetyArray = JSON.parse(localStorage.getItem('heightNinetyArray'));
		    this.heightThreeArray = JSON.parse(localStorage.getItem('heightThreeArray'));
		    this.heightTenArray = JSON.parse(localStorage.getItem('heightTenArray'));
		    this.headArray = JSON.parse(localStorage.getItem('headArray'));
		    this.headTenArray = JSON.parse(localStorage.getItem('headTenArray'));
		    this.headThreeArray = JSON.parse(localStorage.getItem('headThreeArray'));
		    this.headNinetySevenArray = JSON.parse(localStorage.getItem('headNinetySevenArray'));
		    this.headNinetyArray = JSON.parse(localStorage.getItem('headNinetyArray'));
		    this.headFiftyArray = JSON.parse(localStorage.getItem('headFiftyArray'));
		    this.graphData();
		    
	    }
	    else if(this.growthChart == 'false'){
			this.interWeightFiftyArray = JSON.parse(localStorage.getItem('interWeightFiftyArray'));
		    this.interWeightArray = JSON.parse(localStorage.getItem('interWeightArray'));
		    this.interWeightNinetyArray = JSON.parse(localStorage.getItem('interWeightNinetyArray'));
		    this.interWeightNinetySevenArray = JSON.parse(localStorage.getItem('interWeightNinetySevenArray'));
		    this.interWeightTenArray = JSON.parse(localStorage.getItem('interWeightTenArray'));
		    this.interWeightThreeArray = JSON.parse(localStorage.getItem('interWeightThreeArray'));
		    this.interWeightTenArray = JSON.parse(localStorage.getItem('interWeightTenArray'));
		    this.interWeightThreeArray = JSON.parse(localStorage.getItem('interWeightThreeArray'));
		    this.interWeightNinetyFiveArray = JSON.parse(localStorage.getItem('interWeightNinetyFiveArray'));
		    this.interWeightFiveArray = JSON.parse(localStorage.getItem('interWeightFiveArray'));
	        this.interHeightFiftyArray = JSON.parse(localStorage.getItem('interHeightFiftyArray'));
		    this.interHeightNinetyArray = JSON.parse(localStorage.getItem('interHeightNinetyArray'));
		    this.interHeightNinetySevenArray = JSON.parse(localStorage.getItem('interHeightNinetySevenArray'));
		    this.interHeightTenArray = JSON.parse(localStorage.getItem('interHeightTenArray'));
		    this.interHeightThreeArray = JSON.parse(localStorage.getItem('interHeightThreeArray'));
		    this.interHeightNinetyFiveArray = JSON.parse(localStorage.getItem('interHeightNinetyFiveArray'));
		    this.interHeightFiveArray = JSON.parse(localStorage.getItem('interHeightFiveArray'));
		    this.interHeightArray = JSON.parse(localStorage.getItem('interHeightArray'));
	        this.interHeadFiftyArray = JSON.parse(localStorage.getItem('interHeadFiftyArray'));
		    this.interHeadNinetyArray = JSON.parse(localStorage.getItem('interHeadNinetyArray'));
		    this.interHeadNinetySevenArray = JSON.parse(localStorage.getItem('interHeadNinetySevenArray'));
		    this.interHeadTenArray = JSON.parse(localStorage.getItem('interHeadTenArray'));
		    this.interHeadThreeArray = JSON.parse(localStorage.getItem('interHeadThreeArray'));
		    this.interHeadNinetyFiveArray = JSON.parse(localStorage.getItem('interHeadNinetyFiveArray'));
		    this.interHeadFiveArray = JSON.parse(localStorage.getItem('interHeadFiveArray'));
		    this.interHeadArray = JSON.parse(localStorage.getItem('interHeadArray'));
		    this.intergraphData();
		    this.printPdf();
	    }
	   	this.gestationDays = localStorage.getItem("gestationDays");
	   	this.gestationWeeks = localStorage.getItem("gestationWeeks");
    }

	graphData(){
   		console.log("graph func");
   		var tempSeries =  [{
			yAxis : 0,
 			name : 'Three',
	    	marker : {
	    		enabled : false
        	},
    		color : '#39bd6b',
    		data : this.weightThreeArray
	    },
		{
	  		yAxis : 0,
		  	name : 'Ten',
		  	marker : {
		    	enabled : false
	        },
	  		color : '#fb7143',
	  		data : this.weightTenArray
		},
		{
	  		yAxis : 0,
			name : 'Fifty',
			marker : {
				enabled : false
	  		},
	  		color : '#e279fc',
	  		data : this.weightFiftyArray
		},
		{
		  	yAxis : 0,
		  	name : 'Ninety',
			marker : {
				enabled : false
			},
			color : '#feb23a',
			data : this.weightNinetyArray
		},
		{
			yAxis : 0,
		  	name : 'Ninety Seven',
		  	marker : {
		    	enabled : false
		  	},
		  	color : "#74afff",
		  	data : this.weightNinetySevenArray
		},
		{
		  	yAxis : 0,
		  	name : 'Weight',
		  	color : '#ac91f9',
		  	marker : {
		    	enabled : true
		  	},
		  	enableMouseTracking : true,
		  	data : this.weightArray
		},
		{
	  		yAxis : 1,
	  		name : 'Three',
	  		marker : {
	    		enabled : false
	  		},
	  		color : '#39bd6b',
	  		data : this.heightThreeArray
		},
		{
	  		yAxis : 1,
	  		name : 'Ten',
	  		marker : {
	    		enabled : false
	  		},
	  		color : '#fb7143',
	  		data : this.heightTenArray
		},
		{
		  	yAxis : 1,
		  	name : 'Fifty',
		  	marker : {
		    	enabled : false
		  	},
		  	color : '#e279fc',
		  	data : this.heightFiftyArray
		},
		{
			yAxis : 1,
			name : 'Ninety',
			marker : {
				enabled : false
			},
			color : '#feb23a',
			data : this.heightNinetyArray
		},
		{
			yAxis : 1,
		  	name : 'Ninety Seven',
		 	marker : {
		    	enabled : false
		  	},
		  	color : "#74afff",
		  	data : this.heightNinetySevenArray
		},
		{
			yAxis : 1,
			name : 'Height',
			color : '#ac91f9',
			marker : {
				enabled : true
			},
			enableMouseTracking : true,
			data : this.heightArray
		},
		{
		  	yAxis : 2,
		  	name : 'Three',
		  	marker : {
		    	enabled : false
		  	},
		  	color : '#39bd6b',
		  	data : this.headThreeArray
		},
		{
		 	yAxis : 2,
		  	name : 'Ten',
		  	marker : {
		   		enabled : false
		  	},
		  	color : '#fb7143',
		  	data : this.headTenArray
		},
		{
		  	yAxis : 2,
		 	name : 'Fifty',
		  	marker : {
		    	enabled : false
		  	},
		  	color : '#e279fc',
		 	data : this.headFiftyArray
		},
		{
		  	yAxis : 2,
		  	name : 'Ninety',
		  	marker : {
		    	enabled : false
		  	},
		  	color : '#feb23a',
		  	data : this.headNinetyArray
		},
		{
		  	yAxis : 2,
		  	name : 'Ninety Seven',
		  	marker : {
		    	enabled : false
		  	},
		  	color : "#74afff",
		  	data : this.headNinetySevenArray
		},
		{
		  	yAxis : 2,
		  	name : 'Head Circumference',
		  	color : '#ac91f9',
		 	marker : {
		    	enabled : true
		  	},
		  	enableMouseTracking : true,
		  	data : this.headArray
		} ];

    this.chartGrowthOne = new Chart({
  		chart: {
    		type: 'spline'
  		},
  		title: {
    		text: 'Growth chart of Baby'
  		},
  		xAxis : {
    		title : {
      			text : 'Age (weeks)'
    		},
		    allowDecimals : false,
		    tickInterval : 1,
		    min : 22,
		    max : 50
  		},
  		yAxis : [{
      		height : 300,
      		offset : 0,
      		marker : {
        		enabled : false
      		},
      		title : {
        		text : 'Weight (Grams)',
        // align:
        // 'low'
      		}
    	},
    	{
	    	top : 400,
	    	offset : 0,
	      	height : 400,
	      	title : {
	        	text : 'Height (CMs)',
        // align:
        // 'low'
      		}
    	},
    	{
			top : 890,
		    offset : 0,
		    height : 400,
		   	title : {
		    	text : 'Head Circumference (CMs)',
		        // align:
		        // 'low'
		    }
    	}],
    	credits: {
      		enabled: false
    	},
    	series: tempSeries
  	});

 	var tempHeightChart = [{
    	name : 'Three',
    	marker : {
      		enabled : false
    	},
    	color : '#39bd6b',
    	data : this.heightThreeArray
  	},
  	{
    	name : 'Ten',
    	marker : {
      		enabled : false
    	},
    	color : '#fb7143',
    	data : this.heightTenArray
  	},
  	{
    	name : 'Fifty',
    	marker : {
      		enabled : false
    	},
    	color : '#e279fc',
    	data : this.heightFiftyArray
  	},
  	{
    	name : 'Ninety',
    	marker : {
      		enabled : false
    	},
    	color : '#feb23a',
    	data : this.heightNinetyArray
  	},
  	{
    	name : 'Ninety Seven',
    	marker : {
      		enabled : false
    	},
    	color : "#74afff",
    	data : this.heightNinetySevenArray
  	},
  	{
	    name : 'Height',
	    color : '#ac91f9',
	    marker : {
	    	enabled : true
	    },
	    enableMouseTracking : true,
	    data : this.heightArray
  	}];
 	this.chartGrowthFour =  new Chart({
    	chart: {
      		type: 'spline'
    	},
    	plotOptions : {
      		line : {
        		dataLabels : {
          			enabled : false
        		},
        		marker : {
          			enabled : false
        		},
        		enableMouseTracking : false
      		}
    	},
    	title : {
      		text : 'Graph For Height of a Baby'
    	},
    	xAxis : {
      		title : {
        		text : 'Age (weeks)'
      		},
      	allowDecimals : false,
      	tickInterval : 1,
      	min : 23,
      	max : 50
    	},
    	yAxis : {
      		title : {
        		text : 'Height (CMs)'
     		},
    	},
    	credits: {
      		enabled: false
    	},
    	series: tempHeightChart
  	});
  	var tempHeadCircum = [];
	tempHeadCircum =  [{
	    name : 'Three',
	    marker : {
	    	enabled : false
	    },
	    color : '#39bd6b',
	    data : this.headThreeArray
  	},
  	{
	    name : 'Ten',
	    marker : {
	    	enabled : false
	    },
	    color : '#fb7143',
	    data : this.headTenArray
  	},
  	{
    	name : 'Fifty',
    	marker : {
      		enabled : false
    	},
    	color : '#e279fc',
    	data : this.headFiftyArray
  	},
  	{
    	name : 'Ninety',
    	marker : {
      		enabled : false
    	},
    	color : '#feb23a',
    	data : this.headNinetyArray
  	},
  	{
    	name : 'Ninety Seven',
    	marker : {
      		enabled : false
    	},
   	 	color : "#74afff",
    	data : this.headNinetySevenArray
  	},
 	{
    	name : 'Head Circumference',
    	color : '#ac91f9',
    	marker : {
      		enabled : true
    	},
    	enableMouseTracking : true,
    	data : this.headArray
	}];

	this.chartGrowthThree =new Chart({
		chart : {
	    	type : 'spline'
	    },
	    plotOptions : {
	    	line : {
	        	dataLabels : {
	          		enabled : false
	        	},
	        	marker : {
	          		enabled : false
	        	},
	        	enableMouseTracking : false
	     	}
	    },
	    title : {
	    	text : 'Graph For Head Circumference of a Baby'
	    },
	    xAxis : {
	    	title : {
	        	text : 'Age (weeks)'
	      	},
	    	allowDecimals : false,
	      	tickInterval : 1,
	      	min : 23,
	      	max : 50
	    },
	    yAxis : {
	    	title : {
	        	text : 'Head Circumference (CMs)'
	      	},
	    },
	    credits: {
	    	enabled: false
	    },
	    series: tempHeadCircum
	});
    var tempWeight =[];
    tempWeight = [{
   		name : 'Three',
	    marker : {
    		enabled : false
	    },
	    color : '#39bd6b',
	    data : this.weightThreeArray
  	},
  	{
	    name : 'Ten',
	    color : '#fb7143',
	    marker : {
	    	enabled : false
	    },
	    data : this.weightTenArray
	},
	{
	    name : 'Fifty',
	    marker : {
	    	enabled : false
	    },
	    color : '#e279fc',
	    data : this.weightFiftyArray
	},
  	{
	    name : 'Ninety',
	    marker : {
	    	enabled : false
	    },
	    color : '#feb23a',
	    data : this.weightNinetyArray
  	},
  	{
	    name : 'Ninety Seven',
	    marker : {
	      	enabled : false
	    },
    	color : "#74afff",
    	data : this.weightNinetySevenArray
  	},
  	{
	    name : 'Weight',
	    color : '#ac91f9',
	    marker : {
	    	enabled : true
	    },
	    enableMouseTracking : true,
	    data : this.weightArray
  	}];
	this.chartGrowthTwo = new Chart({
		chart : {
	  		type : 'spline'
		},
		plotOptions : {
	  		line : {
	    		dataLabels : {
	      			enabled : false
	    		},
	    	marker : {
	      		enabled : false
	    	},
	    	enableMouseTracking : false
	 		}
		},
		title : {
	 		text : 'Graph For Weight'
		},
		xAxis : {
	  		title : {
	    		text : 'Age (weeks)'
	  		},
	      	allowDecimals : false,
	      	tickInterval : 1,
	     	min : 22,
	      	max : 50
		},
			yAxis : {
	 		title : {
	    		text : 'Weight (Grams)'
	  		},
		},
		credits: {
	  		enabled: false
		},
		series: tempWeight
	});
	}
	intergraphData(){
		var tempSeries = [];
		tempSeries = [{
			yAxis : 0,
			name : 'Three',
			marker : {
				enabled : false
			},
			color : '#39bd6b',
			data : this.interWeightThreeArray
			},
			{
				yAxis : 0,
				name : 'Five',
				color : '#ac91f9',
				marker : {
					enabled : false
				},
				enableMouseTracking : true,
				data : this.interWeightFiveArray
			},
			{
				yAxis : 0,
				name : 'Ten',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interWeightTenArray
			},
			{
				yAxis : 0,
				name : 'Fifty',
				marker : {
					enabled : false
				},
				color : '#e279fc',
				data : this.interWeightFiftyArray
			},
			{
				yAxis : 0,
				name : 'Ninety',
				marker : {
					enabled : false
				},
				color : '#feb23a',
				data : this.interWeightNinetyArray
			},
			{
				yAxis : 0,
				name : 'Ninety Five',
				color : '#ac91f9',
				marker : {
					enabled : false
				},
				enableMouseTracking : true,
				data : this.interWeightNinetyFiveArray
			},
			{
				yAxis : 0,
				name : 'Ninety Seven',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interWeightNinetySevenArray
			},
			{
				yAxis : 0,
				name : 'Weight',
				color : '#ac91f9',
				marker : {
					enabled : true
				},
				enableMouseTracking : true,
				data : this.interWeightArray
			},
			{
				yAxis : 1,
				name : 'Three',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeightThreeArray
			},
			{
				yAxis : 1,
				name : 'Five',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interHeightFiveArray
			},
			{
				yAxis : 1,
				name : 'Ten',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interHeightTenArray
			},
			{
				yAxis : 1,
				name : 'Fifty',
				marker : {
					enabled : false
				},
				color : '#e279fc',
				data : this.interHeightFiftyArray
			},
			{
				yAxis : 1,
				name : 'Ninety',
				marker : {
					enabled : false
				},
				color : '#feb23a',
				data : this.interHeightNinetyArray
			},
			{
				yAxis : 1,
				name : 'Ninety Five',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interHeightNinetyFiveArray
			},
			{
				yAxis : 1,
				name : 'Ninety Seven',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interHeightNinetySevenArray
			},
			{
				yAxis : 1,
				name : 'Height',
				color : '#ac91f9',
				marker : {
					enabled : true
				},
				enableMouseTracking : true,
				data : this.interHeightArray
			},
			{
				yAxis : 2,
				name : 'Three',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeadThreeArray
			},
			{
				yAxis : 2,
				name : 'Five',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeadFiveArray
			},
			{
				yAxis : 2,
				name : 'Ten',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interHeadTenArray
			},
			{
				yAxis : 2,
				name : 'Fifty',
				marker : {
					enabled : false
				},
				color : '#e279fc',
				data : this.interHeadFiftyArray
			},
			{
				yAxis : 2,
				name : 'Ninety',
				marker : {
					enabled : false
				},
				color : '#feb23a',
				data : this.interHeadNinetyArray
			},
			{
				yAxis : 2,
				name : 'Ninety Five',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeadNinetyFiveArray
			},
			{
				yAxis : 2,
				name : 'Ninety Seven',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interHeadNinetySevenArray
			},
			{
				yAxis : 2,
				name : 'Head Circumference',
				color : '#ac91f9',
				marker : {
					enabled : true
				},
				enableMouseTracking : true,
				data : this.interHeadArray
			} ];

			   var tempWeight = [];
			   var tempHeadCircum = [];
			   var tempHeight = [];
			       tempHeight = [
			{
				name : 'Three',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeightThreeArray
			},{
				name : 'Five',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeightFiveArray
			},
			{
				name : 'Ten',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interHeightTenArray
			},
			{
				name : 'Fifty',
				marker : {
					enabled : false
				},
				color : '#e279fc',
				data : this.interHeightFiftyArray
			},
			{
				name : 'Ninety',
				marker : {
					enabled : false
				},
				color : '#feb23a',
				data : this.interHeightNinetyArray
			},
			{
				name : 'Ninety Five',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeightNinetyFiveArray
			},
			{
				name : 'Ninety Seven',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interHeightNinetySevenArray
			},
			{
				name : 'Height',
				color : '#ac91f9',
				marker : {
					enabled : true
				},
				enableMouseTracking : true,
				data : this.interHeightArray
			} ];

                tempHeadCircum =  [
			{
				name : 'Three',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interHeadThreeArray
			},
			{
				name : 'Five',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interHeadFiveArray
			},
			{
				name : 'Ten',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interHeadTenArray
			},
			{
				name : 'Fifty',
				marker : {
					enabled : false
				},
				color : '#e279fc',
				data : this.interHeadFiftyArray
			},
			{
				name : 'Ninety',
				marker : {
					enabled : false
				},
				color : '#feb23a',
				data : this.interHeadNinetyArray
			},
			{
				name : 'Ninety Five',
				marker : {
					enabled : false
				},
				color : '#fb7143',
				data : this.interHeadNinetyFiveArray
			},
			{
				name : 'Ninety Seven',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interHeadNinetySevenArray
			},
			{
				name : 'Head Circumference',
				color : '#ac91f9',
				marker : {
					enabled : true
				},
				enableMouseTracking : true,
				data : this.interHeadArray
			} ];

	            tempWeight = [
			{
				name : 'Three',
				marker : {
					enabled : false
				},
				color : '#39bd6b',
				data : this.interWeightThreeArray
			},
			{
				name : 'Five',
				color : '#fb7143',
				marker : {
					enabled : false
				},
				data : this.interWeightFiveArray
			},
			{
				name : 'Ten',
				color : '#fb7143',
				marker : {
					enabled : false
				},
				data : this.interWeightTenArray
			},
			{
				name : 'Fifty',
				marker : {
					enabled : false
				},
				color : '#e279fc',
				data : this.interWeightFiftyArray
			},
			{
				name : 'Ninety',
				marker : {
					enabled : false
				},
				color : '#feb23a',
				data : this.interWeightNinetyArray
			},
			{
				name : 'Ninety Five',
				color : '#fb7143',
				marker : {
					enabled : false
				},
				data : this.interWeightNinetyFiveArray
			},
			{
				name : 'Ninety Seven',
				marker : {
					enabled : false
				},
				color : "#74afff",
				data : this.interWeightNinetySevenArray
			},
			{
				name : 'Weight',
				color : '#ac91f9',
				marker : {
					enabled : true
				},
				enableMouseTracking : true,
				data : this.interWeightArray
			} ];

	this.chartOne = new Chart({
		chart : {
        	type : 'spline'
      //  animation : false
      	},
	    title : {
        	text : 'Inter Combined Graph of a baby'
	    },
	    xAxis : {
        	title : {
            	text : 'Age (weeks)'
	        },
	        allowDecimals : false,
	        tickInterval : 1,
	        min : 24,
	        max : 33
	    },
	    yAxis : [{
      		height : 300,
      		offset : 0,
      		marker : {
    			enabled : false
      		},
      		title : {
        		text : 'Weight (Grams)',
        // align:
        // 'low'
      		}
        },
	    {
    		top : 400,
          	offset : 0,
          	height : 400,
          	title : {
            	text : 'Height (CMs)',
            // align:
            // 'low'
          	}
        },
        {
          	top : 890,
          	offset : 0,
          	height : 400,
          	title : {
            	text : 'Head Circumference (CMs)',
            // align:
            // 'low'
          	}
        },],
      	credits: {
        	enabled: false
      	},
      	series: tempSeries
	});

   	this.chartTwo = new Chart({
    	chart : {
        	type : 'spline'
      	},
      	plotOptions : {
        	line : {
          		dataLabels : {
            		enabled : false
          		},
          	marker : {
           		enabled : false
          	},
          	enableMouseTracking : false
       		}
      	},
      	title : {
        	text : 'Inter Graph For Weight'
      	},
      	xAxis : {
        	title : {
          		text : 'Age (weeks)'
	        },
	        allowDecimals : false,
	        tickInterval : 1,
	        min : 24,
	        max : 33
      	},
      	yAxis : {
        	title : {
          		text : 'Weight (Grams)'
	        },
      	},
  		credits: {
	        enabled: false
      	},
      		series: tempWeight
    });

    this.chartThree = new Chart({
		chart : {
    		type : 'spline'
	  	},
	  	plotOptions : {
		    line : {
	      		dataLabels : {
		        	enabled : false
		      	},
		     	marker : {
		        	enabled : false
		      	},
		      	enableMouseTracking : false
		    }
	  	},
	  	title : {
	    	text : 'Inter Graph For Head Circumference of a Baby'
	  	},
	  	xAxis : {
		    title : {
	      		text : 'Age (weeks)'
		    },
		    allowDecimals : false,
		    tickInterval : 1,
		    min : 24,
		    max : 33
	  	},
  		yAxis : {
		    title : {
	      		text : 'Head Circumference (CMs)'
		    },
	  	},
	  	credits: {
		    enabled: false
	  	},
	  		series: tempHeadCircum
	});

    this.chartFour = new Chart({
      	chart : {
	        type : 'spline'
      	},
      	plotOptions : {
	        line : {
          		dataLabels : {
	            	enabled : false
	          	},
	          	marker : {
	            	enabled : false
	          	},
	          	enableMouseTracking : false
        	}
      	},
      	title : {
	        text : 'Inter Graph For Height of a Baby'
      	},
      	xAxis : {
	        title : {
          		text : 'Age (weeks)'
	        },
	        allowDecimals : false,
	        tickInterval : 1,
	        min : 24,
	        max : 33
      	},
      	yAxis : {
	        title : {
          		text : 'Height (CMs)'
	        },
      	},
      	credits: {
        	enabled: false
      	},
  			series: tempHeight
    });
  	}

    printPdf = function(){
		setTimeout(window.print, 1000);
		setTimeout( () => {
        	this.router.navigateByUrl('/growth/growth-chart');
        },3000);

	};

	ngOnInit() {
		let keysToRemove = ["weightFiftyArray","weightNinetyArray","weightNinetySevenArray",
		"weightTenArray","weightThreeArray","weightGestArray","weightArray","heightArray",
		"heightFiftyArray","heightNinetyArray","heightNinetySevenArray","heightTenArray",
		"heightThreeArray","headFiftyArray","headNinetyArray","headNinetySevenArray",
		"headTenArray","headThreeArray","headGestArray","headArray","interWeightFiftyArray",
		"interWeightNinetyArray","interWeightNinetyFiveArray","interWeightNinetySevenArray",
		"interWeightTenArray","interWeightThreeArray","interWeightFiveArray",
		"interWeightGestArray","interWeightArray","interHeightFiftyArray",
		"interHeightNinetyArray","interHeightNinetySevenArray","interHeightTenArray",
		"interHeightThreeArray","interHeightNinetyFiveArray","interHeightFiveArray",
		"interHeightGestArray","interHeightArray","interHeadFiftyArray","interHeadNinetyArray",
		"interHeadNinetySevenArray","interHeadTenArray","interHeadThreeArray","interHeadNinetyFiveArray",
		"interHeadFiveArray","interHeadArray","interHeadGestArray","growthChart"
    	];
        keysToRemove.forEach(k =>
        localStorage.removeItem(k));
        this.printPdf();

    }
}
