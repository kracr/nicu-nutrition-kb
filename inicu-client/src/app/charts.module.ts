import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { NursingModule } from './nursing.module';
import { NursingChartsComponent } from './nursing-charts/nursing-charts.component';
import { PrintParenteral } from './nursing-charts/print-parenteral/print-parenteral.component';

const routes: Routes = [
    { path: 'nursing-charts', component: NursingChartsComponent },
    { path: 'nursing-charts/print-parenteral', component: PrintParenteral }
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
    DateTimePickerModule
  ],
  declarations: [NursingChartsComponent, PrintParenteral]
})
export class ChartsModule { }
