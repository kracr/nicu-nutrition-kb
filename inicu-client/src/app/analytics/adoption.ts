export class Adoption {
  maxDate : Date;
  currentDate : Date;
  fromDate : Date;
  toDate : Date;
  constructor() {
    this.maxDate = new Date();
    this.fromDate = new Date(this.maxDate.getTime() - 7 * 24 * 60 * 60 * 1000);
    this.toDate = new Date();
  }

}
