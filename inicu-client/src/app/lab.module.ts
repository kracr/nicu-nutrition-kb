import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { LabReportsComponent } from './lab-reports/lab-reports.component';
import { LabReportsKeysPipe } from './lab-reports/keysPipe';

const routes: Routes = [
    { path: 'lab-reports', component: LabReportsComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    ChartModule,
    DoctorModule,
    DateTimePickerModule
  ],
  declarations: [LabReportsComponent, LabReportsKeysPipe],
  exports: [LabReportsComponent,LabReportsKeysPipe]
})
export class LabModule { }
