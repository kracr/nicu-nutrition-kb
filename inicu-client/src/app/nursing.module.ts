import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CamModule } from './cam.module';
import { ChartModule } from 'angular-highcharts';
import { NusringPanelComponent } from './nusring-panel/nusring-panel.component';
import { NursingPrintComponent } from './nursing-print/nursing-print.component';
import { RemoveAmPmFromTimePipe} from './nursing-print/removeAmPm-pipe';

const routes: Routes = [
    { path: 'nursing-panel', component: NusringPanelComponent },
    { path: 'nursing-print', component: NursingPrintComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    ChartModule,
    CamModule,
    DateTimePickerModule
  ],
  declarations: [NusringPanelComponent,NursingPrintComponent,RemoveAmPmFromTimePipe],
  exports: [NusringPanelComponent]
})
export class NursingModule { }
