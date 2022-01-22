export class TabValue{
  admissionSystem : string;
  whichAdmissionTab : string;
  whichAdmissionTabParent : string;
  whichInitialTab : string;
  constructor(){
    this.admissionSystem = 'Registration';
    this.whichAdmissionTab = 'Parent';
    this.whichAdmissionTabParent = 'Mother';
    this.whichInitialTab ='Reason';
  }
}
