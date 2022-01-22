import { Injectable, Pipe, PipeTransform } from '@angular/core';
@Pipe({
   name: 'CnsKeysPipe',
   pure: true
})
@Injectable()
export class CnsKeysPipe implements PipeTransform{

transform(value, args:string[]):any {
    let keys = [];
    for (let key in value) {
        keys.push({key: key, value: value[key]});
    }
    return keys;
}}
