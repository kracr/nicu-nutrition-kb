import { Component, OnInit } from '@angular/core';
import { Chart } from 'angular-highcharts';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';
import { BothGraphData } from './bothGraphData';
@Component({
  selector: 'growth-chart',
  templateUrl: './growth-chart.component.html',
  styleUrls: ['./growth-chart.component.css']
})
export class GrowthChartComponent implements OnInit {
  chartOne : Chart;
  chartTwo : Chart;
  chartThree : Chart;
  chartFour : Chart;
  chartGrowthOne : Chart;
  chartGrowthTwo : Chart;
  chartGrowthThree : Chart;
  chartGrowthFour : Chart;
  apiData : APIurl;
  http: Http;
  router : Router;
  currentDate : Date;
  getData : BothGraphData;
  growthChartToggle : any;
  
  heightTrue : boolean;
  weightTrue : boolean;
  headCircumference : boolean;
  combinedTrue : boolean;
  
  interHeightTrue : boolean;
  interWeightTrue : boolean;
  interHeadCircumference : boolean;
  interCombinedTrue : boolean;
  id : any;
  
  weightFiftyArray : Array<number>;
  weightNinetyArray : Array<any>;
  weightNinetySevenArray : Array<any>;
  weightTenArray : Array<any>;
  weightThreeArray : Array<any>;
  weightGestArray : Array<any>;
  weightArray : Array<any>;

  heightArray : Array<any>;
  heightFiftyArray : Array<any>;
  heightNinetyArray : Array<any>;
  heightNinetySevenArray : Array<any>;
  heightTenArray : Array<any>;
  heightThreeArray : Array<any>;

  headFiftyArray : Array<any>;
  headNinetyArray : Array<any>;
  headNinetySevenArray : Array<any>;
  headTenArray : Array<any>;
  headThreeArray : Array<any>;
  headGestArray : Array<any>;
  headArray : Array<any>;

  interWeightFiftyArray : Array<any>;
  interWeightNinetyArray : Array<any>;
  interWeightNinetyFiveArray : Array<any>;
  interWeightNinetySevenArray : Array<any>;
  interWeightTenArray : Array<any>;
  interWeightThreeArray : Array<any>;
  interWeightFiveArray : Array<any>;
  interWeightGestArray : Array<any>;
  interWeightArray : Array<any>;

  interHeightFiftyArray : Array<any>;
  interHeightNinetyArray : Array<any>;
  interHeightNinetySevenArray : Array<any>;
  interHeightTenArray : Array<any>;
  interHeightThreeArray : Array<any>;
  interHeightNinetyFiveArray : Array<any>;
  interHeightFiveArray : Array<any>;
  interHeightGestArray : Array<any>;
  interHeightArray : Array<any>;

  interHeadFiftyArray : Array<any>;
  interHeadNinetyArray : Array<any>;
  interHeadNinetySevenArray : Array<any>;
  interHeadTenArray : Array<any>;
  interHeadThreeArray : Array<any>;
  interHeadNinetyFiveArray : Array<any>;
  interHeadFiveArray : Array<any>;
  interHeadArray : Array<any>;
  interHeadGestArray : Array<any>;
  
  constructor(http : Http, router : Router) {
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
    this.currentDate = new Date();
    this.getData = new  BothGraphData();
    this.growthChartToggle=true;
    
    this.heightTrue = false;
    this.weightTrue = true;
    this.headCircumference = false;
    this.combinedTrue = false;
    
    this.interHeightTrue = false;
    this.interWeightTrue = true;
    this.interHeadCircumference = false;
    this.interCombinedTrue = false;
    this.id = JSON.parse(localStorage.getItem('selectedChild'));
    localStorage.setItem("growthChart",this.growthChartToggle);
    this.init();
  }
  init(){
    this.getGrowthChartData();
  }
  getGrowthChartData =function(){

    console.log(this.id.uhid);
    console.log("testng the growth Chart");
    try{
      this.http.request(this.apiData.getGrowthChart+ this.id.uhid + '/',)
      .subscribe(res => {
        this.getData = res.json();
        console.log(this.getData);
        this.printGraph();
        // this.printInterGraph();
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

getGrowthChartDataToggle = function(graphValue){
  if(graphValue == "Growth Chart"){
    this.heightTrue = false;
    this.weightTrue = true;
    this.headCircumference = false;
    this.combinedTrue = false;
    this.interHeightTrue = false;
    this.interWeightTrue = false;
    this.interHeadCircumference = false;
    this.interCombinedTrue = false;
    this.printGraph();
  }else{}
  if(graphValue == "Intergrowth Chart"){
    this.heightTrue = false;
    this.weightTrue = false;
    this.headCircumference = false;
    this.growthChartToggle = false;
    this.combinedTrue = false;
    this.interHeightTrue = false;
    this.interWeightTrue = true;
    localStorage.removeItem("growthChart");
    localStorage.setItem("growthChart",this.growthChartToggle);
    this.interHeadCircumference = false;
    this.interCombinedTrue = false;
    this.printInterGraph();
  }else{}

}
heightVisible = function() {
  this.heightTrue = true;
  this.weightTrue = false;
  this.headCircumference = false;
  this.combinedTrue = false;
}
combinedVisible = function() {
  this.heightTrue = false;
  this.weightTrue = false;
  this.headCircumference = false;
  this.combinedTrue = true;
}
weightVisible = function() {
  this.heightTrue = false;
  this.weightTrue = true;
  this.headCircumference = false;
  this.combinedTrue = false;
}
headCircumferenceVisible = function() {
  this.heightTrue = false;
  this.weightTrue = false;
  this.headCircumference = true;
  this.combinedTrue = false;
}


/*  ----For InterGrowth Chart----  */
interHeightVisible = function() {
  this.interHeightTrue = true;
  this.interWeightTrue = false;
  this.interHeadCircumference = false;
  this.interCombinedTrue = false;
}
interCombinedVisible = function() {
  this.interHeightTrue = false;
  this.interWeightTrue = false;
  this.interHeadCircumference = false;
  this.interCombinedTrue = true;
}
interWeightVisible = function() {
  this.interHeightTrue = false;
  this.interWeightTrue = true;
  this.interHeadCircumference = false;
  this.interCombinedTrue = false;
}
interHeadCircumferenceVisible = function() {
  this.interHeightTrue = false;
  this.interWeightTrue = false;
  this.interHeadCircumference = true;
  this.interCombinedTrue = false;
}

printGraph = function() {
  var tempSeries =[];
  console.log("loading data 1234567");
  this.weightFiftyArray = [];
  this.weightNinetyArray = [];
  this.weightNinetySevenArray = [];
  this.weightTenArray = [];
  this.weightThreeArray = [];
  this.weightGestArray = [];
  this.weightArray = [];

  // below array is for finding making the
  // main weight array
  if(this.getData.plotData != null && this.getData.plotData != undefined && this.getData.plotData.listGraph != null && this.getData.plotData.listGraph != undefined){
    for (var i = 0; i < this.getData.plotData.listGraph.length; i++) {

    if (!(this.getData.plotData.listGraph[i].weight == null
      || this.getData.plotData.listGraph[i].weight == ""
      || this.getData.plotData.listGraph[i].weight == "0" || this.getData.plotData.listGraph[i].weight == "0.0")) {
        this.weightArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),
        (parseFloat(this.getData.plotData.listGraph[i].weight)) ]);
      } else {
      }
    }
  }

if(this.getData.graphData != null && this.getData.graphData != undefined && this.getData.graphData.weight != null && this.getData.graphData.weight != undefined){
  for (var i = 0; i < this.getData.graphData.weight.length; i++) {
    this.weightFiftyArray.push([(parseFloat(this.getData.graphData.weight[i].gestation.weeks)),
    (parseFloat((this.getData.graphData.weight[i].fifty)) * 1000) ]);
    this.weightNinetyArray
    .push([(parseFloat(this.getData.graphData.weight[i].gestation.weeks)),
    (parseFloat((this.getData.graphData.weight[i].ninety)) * 1000) ]);
    this.weightNinetySevenArray
    .push([
    (parseFloat(this.getData.graphData.weight[i].gestation.weeks)),
    (parseFloat((this.getData.graphData.weight[i].ninetySeven)) * 1000) ]);
    this.weightTenArray
    .push([
    (parseFloat(this.getData.graphData.weight[i].gestation.weeks)),
    (parseFloat((this.getData.graphData.weight[i].ten)) * 1000) ]);
    this.weightThreeArray
    .push([
      (parseFloat(this.getData.graphData.weight[i].gestation.weeks)),
      (parseFloat((this.getData.graphData.weight[i].three)) * 1000) ]);
    }
  }
    console.log("weight array");
    console.log(this.weightFiftyArray);
    console.log(this.weightNinetyArray);
    console.log(this.weightArray);
    console.log(this.weightNinetySevenArray);
    console.log(this.weightTenArray);
    console.log(this.weightThreeArray);
    console.log("(*&^%$#@$%^&*()(*&^%$");

    // below code is for height of a baby
    this.heightFiftyArray = [];
    this.heightNinetyArray = [];
    this.heightNinetySevenArray = [];
    this.heightTenArray = [];
    this.heightThreeArray = [];
    this.heightGestArray = [];
    this.heightArray = [];

    // below array is for finding making the
    // main height array
    if(this.getData.plotData != null && this.getData.plotData != undefined && this.getData.plotData.listGraph != null && this.getData.plotData.listGraph != undefined){
      for (var i = 0; i < this.getData.plotData.listGraph.length; i++) {
      if (!(this.getData.plotData.listGraph[i].height == null
        || this.getData.plotData.listGraph[i].height == ""
        || this.getData.plotData.listGraph[i].height == "0" || this.getData.plotData.listGraph[i].height == "0.0")) {
          this.heightArray
        .push([
          (parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),
          (parseFloat(this.getData.plotData.listGraph[i].height)) ]);
        } else {}

      }
  }
    // below array is for finding making the
    // standard height array
    console.log(this.getData);
    if(this.getData.graphData != null && this.getData.graphData != undefined && this.getData.graphData.length != null && this.getData.graphData.length != undefined){

      for (var i = 0; i < this.getData.graphData.length.length; i++) {
        this.heightFiftyArray
        .push([
        (parseFloat(this.getData.graphData.length[i].gestation.weeks)),
        (parseFloat((this.getData.graphData.length[i].fifty))) ]);
        this.heightNinetyArray
        .push([
        (parseFloat(this.getData.graphData.length[i].gestation.weeks)),
        (parseFloat((this.getData.graphData.length[i].ninety))) ]);
        this.heightNinetySevenArray
        .push([
        (parseFloat(this.getData.graphData.length[i].gestation.weeks)),
        (parseFloat((this.getData.graphData.length[i].ninetySeven))) ]);
        this.heightTenArray
        .push([
        (parseFloat(this.getData.graphData.length[i].gestation.weeks)),
        (parseFloat((this.getData.graphData.length[i].ten))) ]);
        this.heightThreeArray
        .push([
        (parseFloat(this.getData.graphData.length[i].gestation.weeks)),
        (parseFloat((this.getData.graphData.length[i].three))) ]);
      }
    }

      console.log("height array");
      console.log(this.heightArray);
      console.log(this.heightFiftyArray);
      console.log(this.heightNinetyArray);
      console.log(this.heightNinetySevenArray);
      console.log(this.heightTenArray);
      console.log(this.heightThreeArray);
      console.log("(*&^%$#@$%^&*()(*&^%$");
   
      // below code is for Head Circumference of a
      // baby
      this.headFiftyArray = [];
      this.headNinetyArray = [];
      this.headNinetySevenArray = [];
      this.headTenArray = [];
      this.headThreeArray = [];
      this.headGestArray = [];
      this.headArray = [];

      // below array is for finding making the
      // main height array
      if(this.getData.plotData != null && this.getData.plotData != undefined && this.getData.plotData.listGraph != null && this.getData.plotData.listGraph != undefined){

        for (var i = 0; i < this.getData.plotData.listGraph.length; i++) {
      // this.headArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),(parseFloat(this.getData.plotData.listGraph[i].headcircum))]);
          if (!(this.getData.plotData.listGraph[i].headcircum == null
          || this.getData.plotData.listGraph[i].headcircum == ""
          || this.getData.plotData.listGraph[i].headcircum == "0" || this.getData.plotData.listGraph[i].headcircum == "0.0")) {
            this.headArray
            .push([
              (parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),
              (parseFloat(this.getData.plotData.listGraph[i].headcircum)) ]);

              console.log("imp"+parseFloat(this.getData.plotData.listGraph[i].gestation.weeks));
            } else {
              // this.getData.plotData.listGraph[i].height
              // = "";
              // this.headArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),null]);
            }
          }
      }
  // below array is for finding making the
        // standard height array
        console.log(this.getData);
        if(this.getData.graphData != null && this.getData.graphData != undefined && this.getData.graphData.headCircum != null && this.getData.graphData.headCircum != undefined){

          for (var i = 0; i < this.getData.graphData.headCircum.length; i++) {
            this.headFiftyArray
          .push([
          (parseFloat(this.getData.graphData.headCircum[i].gestation.weeks)),
          (parseFloat((this.getData.graphData.headCircum[i].fifty))) ]);
          this.headNinetyArray
          .push([
            (parseFloat(this.getData.graphData.headCircum[i].gestation.weeks)),
            (parseFloat((this.getData.graphData.headCircum[i].ninety))) ]);
            this.headNinetySevenArray
            .push([
              (parseFloat(this.getData.graphData.headCircum[i].gestation.weeks)),
              (parseFloat((this.getData.graphData.headCircum[i].ninetySeven))) ]);
              this.headTenArray
              .push([
                (parseFloat(this.getData.graphData.headCircum[i].gestation.weeks)),
                (parseFloat((this.getData.graphData.headCircum[i].ten))) ]);
                this.headThreeArray
                .push([
                  (parseFloat(this.getData.graphData.headCircum[i].gestation.weeks)),
                  (parseFloat((this.getData.graphData.headCircum[i].three))) ]);
        }
      }


      console.log("head circumference array");
      console.log(this.headArray);
      console.log(this.headFiftyArray);
      console.log(this.headNinetyArray);
      console.log(this.headNinetySevenArray);
      console.log(this.headTenArray);
      console.log(this.headThreeArray);
      console.log("(*&^%$#@$%^&*()(*&^%$");

tempSeries =  [
{
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
  yAxis : [
    {
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
    }, ],
    credits: {
      enabled: false
    },
    series: tempSeries
  });

var tempHeightChart =  [
  {
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
  } ];
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
tempHeadCircum =  [
  {
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
  } ];

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
tempWeight = [
  {
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
  } ];
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
printInterGraph = function() {
  console.log("loading data 1234567");

  this.interWeightFiftyArray = [];
  this.interWeightNinetyArray = [];
  this.interWeightNinetyFiveArray = [];
  this.interWeightNinetySevenArray = [];
  this.interWeightTenArray = [];
  this.interWeightThreeArray = [];
  this.interWeightFiveArray = [];
  this.interWeightGestArray = [];
  this.interWeightArray = [];

  // below array is for finding making the
  // main weight array
  if(this.getData.plotData != null && this.getData.plotData != undefined && this.getData.plotData.listGraph != null && this.getData.plotData.listGraph != undefined){

    for (var i = 0; i < this.getData.plotData.listGraph.length; i++) {

    if (!((this.getData.plotData.listGraph[i].weight == null ||
      this.getData.plotData.listGraph[i].weight == ""
      || this.getData.plotData.listGraph[i].weight == "0" || this.getData.plotData.listGraph[i].weight == "0.0"))&&((parseFloat(this.getData.plotData.listGraph[i].gestation.weeks))<=33)) {
        this.interWeightArray
        .push([
          (parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),
          (parseFloat(this.getData.plotData.listGraph[i].weight)) ]);
        } else {
          // this.getData.plotData.listGraph[i].height
          // = "";
          // this.weightArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),null]);
        }
      }
    }
    // below array is for finding making the
    // standard weight array
    if(this.getData.interGrowthGraphData != null && this.getData.interGrowthGraphData != undefined && this.getData.interGrowthGraphData.weight != null && this.getData.interGrowthGraphData.weight != undefined){

      for (var i = 0; i < this.getData.interGrowthGraphData.weight.length; i++) {
        this.interWeightFiftyArray
        .push([
          (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
          (parseFloat((this.getData.interGrowthGraphData.weight[i].fifty)) * 1000) ]);
          this.interWeightNinetyArray
          .push([
            (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
            (parseFloat((this.getData.interGrowthGraphData.weight[i].ninety)) * 1000) ]);
            this.interWeightNinetySevenArray
            .push([
              (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
              (parseFloat((this.getData.interGrowthGraphData.weight[i].ninetySeven)) * 1000) ]);
              this.interWeightTenArray
              .push([
                (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
                (parseFloat((this.getData.interGrowthGraphData.weight[i].ten)) * 1000) ]);
                this.interWeightThreeArray
                .push([
                  (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
                  (parseFloat((this.getData.interGrowthGraphData.weight[i].three)) * 1000) ]);
                  this.interWeightNinetyFiveArray
                  .push([
                    (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
                    (parseFloat((this.getData.interGrowthGraphData.weight[i].ninetyFive)) * 1000) ]);
                    this.interWeightFiveArray
                    .push([
                      (parseFloat(this.getData.interGrowthGraphData.weight[i].gestation.weeks)),
                      (parseFloat((this.getData.interGrowthGraphData.weight[i].five)) * 1000) ]);
      }
    }


    console.log("inter weight array");
    console.log(this.interWeightFiftyArray);
    console.log(this.interWeightNinetyArray);
    console.log(this.interWeightArray);
    console.log(this.interWeightNinetySevenArray);
    console.log(this.interWeightTenArray);
    console.log(this.interWeightThreeArray);
    console.log(this.interWeightNinetyFiveArray);
    console.log(this.interWeightFiveArray);
    console.log("2(*&^%$#@$%^&*()(*&^%$");
    // below code is for height of a baby
    this.interHeightFiftyArray = [];
    this.interHeightNinetyArray = [];
    this.interHeightNinetySevenArray = [];
    this.interHeightTenArray = [];
    this.interHeightThreeArray = [];
    this.interHeightNinetyFiveArray = [];
    this.interHeightFiveArray = [];
    this.interHeightGestArray = [];
    this.interHeightArray = [];

    // below array is for finding making the
    // main height array
    if(this.getData.plotData != null && this.getData.plotData != undefined && this.getData.plotData.listGraph != null && this.getData.plotData.listGraph != undefined){

      for (var i = 0; i < this.getData.plotData.listGraph.length; i++) {
      if (!((this.getData.plotData.listGraph[i].height == null
        || this.getData.plotData.listGraph[i].height == ""
        || this.getData.plotData.listGraph[i].height == "0" || this.getData.plotData.listGraph[i].height == "0.0"))&&((parseFloat(this.getData.plotData.listGraph[i].gestation.weeks))<=33)) {
          this.interHeightArray
          .push([
            (parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),
            (parseFloat(this.getData.plotData.listGraph[i].height)) ]);
          } else {
            // this.getData.plotData.listGraph[i].height
            // = "";
            // this.heightArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),null]);
          }

        }
      }
      // below array is for finding making the
      // standard height array
      console.log(this.getData);
if(this.getData.interGrowthGraphData != null && this.getData.interGrowthGraphData != undefined && this.getData.interGrowthGraphData.length != null && this.getData.interGrowthGraphData.length != undefined){

  for (var i = 0; i < this.getData.interGrowthGraphData.length.length; i++) {
  this.interHeightFiftyArray
  .push([
  (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
  (parseFloat((this.getData.interGrowthGraphData.length[i].fifty))) ]);
  this.interHeightNinetyArray
  .push([
  (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
  (parseFloat((this.getData.interGrowthGraphData.length[i].ninety))) ]);
  this.interHeightNinetySevenArray
  .push([
  (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
  (parseFloat((this.getData.interGrowthGraphData.length[i].ninetySeven))) ]);
  this.interHeightTenArray
  .push([
    (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
    (parseFloat((this.getData.interGrowthGraphData.length[i].ten))) ]);
    this.interHeightThreeArray
    .push([
      (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
      (parseFloat((this.getData.interGrowthGraphData.length[i].three))) ]);
      this.interHeightFiveArray
      .push([
        (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
        (parseFloat((this.getData.interGrowthGraphData.length[i].five))) ]);
        this.interHeightNinetyFiveArray
        .push([
          (parseFloat(this.getData.interGrowthGraphData.length[i].gestation.weeks)),
          (parseFloat((this.getData.interGrowthGraphData.length[i].ninetyFive))) ]);
        }
      }

      console.log("inter height array");
      console.log(this.interHeightArray);
      console.log(this.interHeightFiftyArray);
      console.log(this.interHeightNinetyArray);
      console.log(this.interHeightNinetySevenArray);
      console.log(this.interHeightTenArray);
      console.log(this.interHeightThreeArray);
      console.log(this.interHeightFiveArray);
      console.log(this.interHeightNinetyFiveArray);
      console.log("2(*&^%$#@$%^&*()(*&^%$");

      // below code is for Head Circumference of a
      // baby
      this.interHeadFiftyArray = [];
      this.interHeadNinetyArray = [];
      this.interHeadNinetySevenArray = [];
      this.interHeadTenArray = [];
      this.interHeadThreeArray = [];
      this.interHeadNinetyFiveArray = [];
      this.interHeadFiveArray = [];
      this.interHeadArray = [];
      this.interHeadGestArray = [];

      // below array is for finding making the
      // main height array
      if(this.getData.plotData != null && this.getData.plotData != undefined && this.getData.plotData.listGraph != null && this.getData.plotData.listGraph != undefined){

        for (var i = 0; i < this.getData.plotData.listGraph.length; i++) {
        // this.headArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),(parseFloat(this.getData.plotData.listGraph[i].headcircum))]);
        if (!((this.getData.plotData.listGraph[i].headcircum == null
        || this.getData.plotData.listGraph[i].headcircum == ""
        || this.getData.plotData.listGraph[i].headcircum == "0" || this.getData.plotData.listGraph[i].headcircum == "0.0"))&&((parseFloat(this.getData.plotData.listGraph[i].gestation.weeks))<=33)) {
          this.interHeadArray
          .push([
            (parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),
            (parseFloat(this.getData.plotData.listGraph[i].headcircum)) ]);
          } else {
            // this.getData.plotData.listGraph[i].height
            // = "";
            // this.headArray.push([(parseFloat(this.getData.plotData.listGraph[i].gestation.weeks)),null]);
          }
        }
      }
// below array is for finding making the
// standard height array
console.log(this.getData);
if(this.getData.interGrowthGraphData != null && this.getData.interGrowthGraphData != undefined && this.getData.interGrowthGraphData.headCircum != null && this.getData.interGrowthGraphData.headCircum != undefined){

  for (var i = 0; i < this.getData.interGrowthGraphData.headCircum.length; i++) {
  this.interHeadFiftyArray
  .push([
  (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
  (parseFloat((this.getData.interGrowthGraphData.headCircum[i].fifty))) ]);
  this.interHeadNinetyArray
  .push([
  (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
  (parseFloat((this.getData.interGrowthGraphData.headCircum[i].ninety))) ]);
  this.interHeadNinetySevenArray
  .push([
  (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
  (parseFloat((this.getData.interGrowthGraphData.headCircum[i].ninetySeven))) ]);
  this.interHeadTenArray
  .push([
    (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
    (parseFloat((this.getData.interGrowthGraphData.headCircum[i].ten))) ]);
    this.interHeadThreeArray
    .push([
      (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
      (parseFloat((this.getData.interGrowthGraphData.headCircum[i].three))) ]);
      this.interHeadNinetyFiveArray
      .push([
        (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
        (parseFloat((this.getData.interGrowthGraphData.headCircum[i].ninetyFive))) ]);
        this.interHeadFiveArray
        .push([
          (parseFloat(this.getData.interGrowthGraphData.headCircum[i].gestation.weeks)),
          (parseFloat((this.getData.interGrowthGraphData.headCircum[i].five))) ]);
        }
      }
      console.log("inter head circumference array");
      console.log(this.interHeadArray);
      console.log(this.interHeadFiftyArray);
      console.log(this.interHeadNinetyArray);
      console.log(this.interHeadNinetySevenArray);
      console.log(this.interHeadTenArray);
      console.log(this.interHeadThreeArray);
      console.log(this.interHeadNinetyFiveArray);
      console.log(this.interHeadFiveArray);
      console.log("(*&^%$#@$%^&*()(*&^%$");

      var tempSeries = [];
      tempSeries = [
			{
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
      yAxis : [
        {
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
        }, ],
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

redirectToPrint(){

  localStorage.setItem('weightFiftyArray', JSON.stringify(this.weightFiftyArray));
  localStorage.setItem('weightNinetyArray',JSON.stringify(this.weightNinetyArray));
  localStorage.setItem('weightArray',JSON.stringify(this.weightArray));
  localStorage.setItem('weightNinetySevenArray',JSON.stringify(this.weightNinetySevenArray));
  localStorage.setItem('weightTenArray', JSON.stringify(this.weightTenArray));
  localStorage.setItem('weightThreeArray', JSON.stringify(this.weightThreeArray));

  localStorage.setItem('heightArray', JSON.stringify(this.heightArray));
  localStorage.setItem('heightFiftyArray',JSON.stringify(this.heightFiftyArray));
  localStorage.setItem('heightNinetyArray',JSON.stringify(this.heightNinetyArray));
  localStorage.setItem('heightNinetySevenArray',JSON.stringify(this.heightNinetySevenArray));
  localStorage.setItem('heightTenArray',JSON.stringify(this.heightTenArray));
  localStorage.setItem('heightThreeArray',JSON.stringify(this.heightThreeArray));

  localStorage.setItem('headArray',JSON.stringify(this.headArray));
  localStorage.setItem('headFiftyArray',JSON.stringify(this.headFiftyArray));
  localStorage.setItem('headNinetyArray',JSON.stringify(this.headNinetyArray));
  localStorage.setItem('headNinetySevenArray',JSON.stringify(this.headNinetySevenArray));
  localStorage.setItem('headTenArray',JSON.stringify(this.headTenArray));
  localStorage.setItem('headThreeArray',JSON.stringify(this.headThreeArray));

  localStorage.setItem('interWeightFiftyArray',JSON.stringify(this.interWeightFiftyArray));
  localStorage.setItem('interWeightArray',JSON.stringify(this.interWeightArray));
  localStorage.setItem('interWeightNinetyArray',JSON.stringify(this.interWeightNinetyArray));
  localStorage.setItem('interWeightNinetySevenArray',JSON.stringify(this.interWeightNinetySevenArray));
  localStorage.setItem('interWeightTenArray',JSON.stringify(this.interWeightTenArray));
  localStorage.setItem('interWeightThreeArray',JSON.stringify(this.interWeightThreeArray));
  localStorage.setItem('interWeightNinetyFiveArray',JSON.stringify(this.interWeightNinetyFiveArray));
  localStorage.setItem('interWeightFiveArray',JSON.stringify(this.interWeightFiveArray));

  localStorage.setItem('interHeightArray',JSON.stringify(this.interHeightArray));
  localStorage.setItem('interHeightFiftyArray',JSON.stringify(this.interHeightFiftyArray));
  localStorage.setItem('interHeightNinetyArray',JSON.stringify(this.interHeightNinetyArray));
  localStorage.setItem('interHeightNinetySevenArray',JSON.stringify(this.interHeightNinetySevenArray));
  localStorage.setItem('interHeightTenArray',JSON.stringify(this.interHeightTenArray));
  localStorage.setItem('interHeightThreeArray',JSON.stringify(this.interHeightThreeArray));
  localStorage.setItem('interHeightFiveArray',JSON.stringify(this.interHeightFiveArray));
  localStorage.setItem('interHeightNinetyFiveArray',JSON.stringify(this.interHeightNinetyFiveArray));


  localStorage.setItem('interHeadArray',JSON.stringify(this.interHeadArray));
  localStorage.setItem('interHeadFiftyArray',JSON.stringify(this.interHeadFiftyArray));
  localStorage.setItem('interHeadNinetyArray',JSON.stringify(this.interHeadNinetyArray));
  localStorage.setItem('interHeadNinetySevenArray',JSON.stringify(this.interHeadNinetySevenArray));
  localStorage.setItem('interHeadTenArray',JSON.stringify(this.interHeadTenArray));
  localStorage.setItem('interHeadThreeArray',JSON.stringify(this.interHeadThreeArray));
  localStorage.setItem('interHeadNinetyFiveArray',JSON.stringify(this.interHeadNinetyFiveArray));
  localStorage.setItem('interHeadFiveArray',JSON.stringify(this.interHeadFiveArray));
	this.router.navigateByUrl('/growth/growth-chart-print');
}

ngOnInit() {
}

}
