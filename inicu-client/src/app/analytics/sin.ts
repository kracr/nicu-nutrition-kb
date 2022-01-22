import { SinData } from './sinData';

export class Sin {

  maxDate : Date;
  currentDate : Date;
  fromDate : Date;
  toDate : Date;
  viewType : string;
  current : SinData;
  sinSheetList : Array<SinData>;

  constructor() {
    this.maxDate = new Date();
    this.fromDate = new Date();
    this.toDate = new Date();
    this.viewType = 'Grid';
    this.current = new SinData();
    this.sinSheetList = new Array<SinData>();
  }

}
