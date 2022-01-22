import { UsageData } from './usageData';

export class Usage {

  maxDate : Date;
  currentDate : Date;
  fromDate : Date;
  toDate : Date;
  usageList : Array<any>;

  constructor() {
    this.maxDate = new Date();
    this.fromDate = new Date();
    this.toDate = new Date();
  }

}
