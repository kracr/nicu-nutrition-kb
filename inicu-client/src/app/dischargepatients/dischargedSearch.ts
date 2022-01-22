export class DischargedSearch
{
  searchedForUhid : string;
  searchedStartDate : any;
  searchedEndDate : any;
  constructor() {
    this.searchedForUhid = null;
    this.searchedStartDate = new Date(new Date().setDate(new Date().getDate()-1));
    this.searchedEndDate = new Date();
  }
}
