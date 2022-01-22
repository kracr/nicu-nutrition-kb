import {Pipe, PipeTransform } from '@angular/core';
@Pipe({ name: 'SearchPanel',
    pure: true
 })

export class SearchPanel implements PipeTransform {
  transform(resultData: any, searchText: any): any {
    if(searchText == null || searchText == ''){ return resultData; }
    else{
      if(resultData !=null && resultData != undefined){
      return resultData.filter((val)=>{
        if(val.testname != null){
          return val.testname.toLowerCase().indexOf(searchText.toLowerCase()) != -1;
        }else{
          return false;
        }
      })  
      }
    }
  }
}

