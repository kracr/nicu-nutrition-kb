import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { AdmissionPrintComponent } from './admission-print/admission-print.component';

const routes: Routes = [
    { path: 'admissionfrom-print', component: AdmissionPrintComponent }
];



@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    DateTimePickerModule
  ],
  declarations: [AdmissionPrintComponent]
})
export class AdmisprintModule { }
