import { Injectable, Pipe, PipeTransform } from '@angular/core';
@Pipe({ name: 'CounsellingSheetFilter',
        pure: true
 })
@Injectable()
export class CounsellingSheetFilter implements PipeTransform {
  transform(resultData: any, searchText: any): any {    
      return resultData.filter(function(bed){
        if(searchText == null || searchText == ''){ 
          return !bed.isvacant;
        } else if(bed.name != null || bed.uhid != null){
          return bed.name.toLowerCase().indexOf(searchText.toLowerCase()) != -1 ||
            bed.uhid.toLowerCase().indexOf(searchText.toLowerCase()) != -1;
        }else{
          return false;
        }
      })
  }
}
