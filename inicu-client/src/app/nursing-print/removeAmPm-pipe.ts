import { Injectable, Pipe, PipeTransform } from '@angular/core';
@Pipe({
    name: 'removeAmPm',
    pure: true
 })
 @Injectable()
export class RemoveAmPmFromTimePipe implements PipeTransform{

transform(value:string):any {
    console.log(value)
    if(value.search("AM") != -1){
        value = value.replace(/(:AM|AM)/g,' a');
        }else{
        value = value.replace(/(:PM|PM)/g,' p'); 
        }
    return value ;
}}