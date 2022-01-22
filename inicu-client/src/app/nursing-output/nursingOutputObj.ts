export class NursingOutputObj {

  maxDate : Date;
  currentDate : Date;
  fromDate : Date;
  toDate : Date;
  toDateMaxValue : Date;
  whichTab : string;

  constructor() {
    var currentDate = new Date();
    this.maxDate = currentDate;
    this.toDate = currentDate;
    this.toDateMaxValue = currentDate;

    this.fromDate = new Date();
    this.fromDate.setHours(8);
    this.fromDate.setMinutes(0);

    if(currentDate.getHours() < 8) {
      this.fromDate = new Date(this.fromDate.getTime() - (24*60*60*1000));
    }

    this.whichTab = '';
  }

}
