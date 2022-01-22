import { QIData } from './qiData';

export class QI {

  maxDate : Date;
  currentDate : Date;
  fromDate : Date;
  toDate : Date;
  fromWeight : number;
  toWeight : number;
  toGestation : string;
  fromGestation : string;

  constructor() {
    this.maxDate = new Date();
    this.fromDate = new Date();
    this.toDate = new Date();
    this.toGestation = "";
    this.fromGestation = "";
  }

}
