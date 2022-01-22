import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { NursingModule } from './nursing.module';
import { LabModule } from './lab.module';
import { LabOrdersKeysPipe } from './nursing-reports/keysPipe';
import { NursingReportsComponent } from './nursing-reports/nursing-reports.component';

const routes: Routes = [
    { path: 'nursing-reports', component: NursingReportsComponent }
];
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    ChartModule,
    HttpModule,
    NursingModule,
    LabModule,
    DateTimePickerModule
  ],
  declarations: [NursingReportsComponent,LabOrdersKeysPipe]
})
export class NursingLabModule { }
