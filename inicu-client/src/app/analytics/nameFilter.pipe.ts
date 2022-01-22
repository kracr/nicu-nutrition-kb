import {Pipe, PipeTransform} from '@angular/core';
@Pipe({
  name: 'NamePipe',
  pure : true
})
export class NamePipe implements PipeTransform{
  transform(resultData: any, searchText: any): any {
    if(searchText == null){ return resultData; }
    else{
      return resultData.filter(function(item){
        if(item.babyName != null || item.uhid != null){
          return item.babyName.toLowerCase().indexOf(searchText.toLowerCase()) != -1 ||
            item.uhid.toLowerCase().indexOf(searchText.toLowerCase()) != -1;
        }
        else{}
      })
    }
  }
}
