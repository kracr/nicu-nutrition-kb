import { Injectable, Pipe, PipeTransform } from '@angular/core';
@Pipe({
   name: 'AdmissionFormKeysPipe',
   pure: true
})
@Injectable()
export class AdmissionFormKeysPipe implements PipeTransform{

transform(value, args:string[]):any {
    let keys = [];
    for (let key in value) {
        keys.push({key: key, value: value[key]});
    }
    return keys;
}}
