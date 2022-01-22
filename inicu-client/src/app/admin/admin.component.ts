import { Component, OnInit } from '@angular/core';
import {
  Router
} from '@angular/router';
@Component({
  selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  whichAdminTab : string;
  constructor(private router: Router) {
     this.whichAdminTab = 'userPanel';
  };
  adminTab(tabValue){
    this.whichAdminTab = tabValue;
  }
  ngOnInit() {
  }

}
