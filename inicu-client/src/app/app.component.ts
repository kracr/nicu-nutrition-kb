import { Component, OnInit, HostListener } from '@angular/core';

import {
  Router
} from '@angular/router';
import * as $ from 'jquery/dist/jquery.min.js';
import { AuthService } from './auth.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  router : Router;
  hideHeader : boolean;
  isloginPage : boolean;
  hideMenuButton : boolean;
  displayMenuBar : boolean;
  whichPanel : string;
  userName : string;
  branchName : string;
  constructor(router: Router,public authService:AuthService) {
      this.router = router;
      this.hideHeader = false;
      this.isloginPage = false;
      this.hideMenuButton = false;
      this.displayMenuBar = false;
      this.whichPanel = '';
      this.userName = '';
      this.branchName = '';

  }
changeOfRoutes = function(){
    if(this.router.url === ''){
      this.router.navigateByUrl('/');
    } else if(this.router.url.includes('#')){
      window.location.reload();
      this.router.navigateByUrl('/');
    } else if(this.router.url === '/error'){
      localStorage.clear();
      this.router.navigateByUrl('/');
    } else if(this.router.url==='/ana/usagePdf'){
      this.hideHeader = true;
      this.hideMenuButton = false;
    }else if(this.router.url==='/jaundice/jaundice-print'){
      this.hideHeader = true;
      this.hideMenuButton = false;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url==='/admisprint/admissionfrom-print'){
      this.hideHeader = true;
      this.hideMenuButton = false;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url==='/ana/sinsheetPdf'){
      this.hideHeader = true;
      this.hideMenuButton = false;
      document.body.style.backgroundImage = "url('')";
      console.log("you are here");
    } else if(this.router.url==='/summary/summary-notes-print'){
      this.hideHeader = true;
      this.hideMenuButton = false;
      document.body.style.backgroundImage = "url('')";
      console.log("you are here");
    } else if(this.router.url ==='/discharge/discharge-summary-print'){
      this.hideHeader = true;
      this.hideMenuButton = false;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url ==='/'){
      this.hideHeader = true;
      this.isloginPage = true;
      this.hideMenuButton = false;
      $('body').addClass('home');
      document.body.style.backgroundImage = "url('../../assets/_images/login.png')";
      document.body.style.backgroundSize = "100%";
    } else if(this.router.url ==='/dash/dashboard'){
      this.hideMenuButton = false;
      this.hideHeader = false;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url ==='/discharged/dischargePatients'){
      this.hideMenuButton = false;
      this.hideHeader = false;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url ==='/ana/analytics'){
      this.hideMenuButton = false;
      this.hideHeader = false;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url === '/adm/admin'){
      this.hideMenuButton = false;
      this.hideHeader = false;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url === '/admis/admissionform'){
      this.hideMenuButton = false;
      this.hideHeader = false;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url === '/nursing/nursing-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    } else if(this.router.url === '/charts/nursing-charts/print-parenteral'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }
    else if(this.router.url === '/dash/counselling-sheet-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }
    else if(this.router.url === '/dash/doctor-task-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }
    else if(this.router.url === '/growth/growth-chart-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }
    else if(this.router.url === '/feed/nutrition-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url === '/med/medications-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url === '/blood/blood-product-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url === '/discharge/case-summary-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url === '/proceed/exchange-transfusion-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }else if(this.router.url === '/screen/screening-print'){
      this.hideMenuButton = false;
      this.hideHeader = true;
      document.body.style.backgroundImage = "url('')";
    }
    else {
      this.hideMenuButton = false;
      this.hideHeader = false;
      document.body.style.backgroundImage = "url('')";
    }
}
      toggleSideMenu(id) {
        var menu = document.getElementById(id);
        $("#leftPanel").toggleClass( "show" );
        this.displayMenuBar = true;
        this.whichPanel = '';
      }
      logout(){
        localStorage.clear();
        this.router.navigateByUrl('');

      }
    headerTab(){
      if(JSON.parse(localStorage.getItem('selectedChild')) != undefined){
        localStorage.removeItem('selectedChild');
      }
    }
       @HostListener('mousedown', ['$event'])
        onMousedown(event) {
             if (!$(event.target).is('.multiple-select, .multiple-select>*, .multiple-select>*>*, .multiple-select>*>*>*')) {
                if(this.whichPanel == 'Doctor'){
                 this.router.navigateByUrl('/main/(mainpannelOutlet:doctorpanel/(doctorpanelOutlet:assessment/(assessmentpanelOutlet:respiratory)))');
                }
                if(this.whichPanel == 'Nursing'){
                  this.router.navigateByUrl('/main/(mainpannelOutlet:nursingpanel/(nursingpanelOutlet:anthropomery))');
                }
                $(".multiple-select").removeClass( "show" );
                this.displayMenuBar = false;
          }
        }
  ngOnInit() {
    this.changeOfRoutes();
    if(JSON.parse(localStorage.getItem('logedInUser')) != undefined){
      this.userName = JSON.parse(localStorage.getItem('logedInUser')).userName;
      this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    }
  }
}
