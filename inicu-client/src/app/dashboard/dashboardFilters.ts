import { Injectable, Pipe, PipeTransform } from '@angular/core';
@Pipe({ name: 'SearchUhidName',
        pure: true
 })
@Injectable()
export class SearchUhidName implements PipeTransform {
  transform(resultData: any, searchText: any): any {
    if(searchText == null || searchText == ''){ return resultData; }
    else{
      return resultData.filter(function(bed){
        if(bed.name != null || bed.uhid != null){
          return bed.name.toLowerCase().indexOf(searchText.toLowerCase()) != -1 ||
            bed.uhid.toLowerCase().indexOf(searchText.toLowerCase()) != -1;
        }else{
          return false;
        }
      })
    }
  }
}
