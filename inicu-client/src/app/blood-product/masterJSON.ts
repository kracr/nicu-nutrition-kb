import { BloodProductObj } from './bloodProductObj';
import { User } from './user';

export class MasterJSON {
  
  currentObj: BloodProductObj;
  pastList: BloodProductObj[];
  userList : User[];

  constructor() {
    this.currentObj = new BloodProductObj();
  }
}
