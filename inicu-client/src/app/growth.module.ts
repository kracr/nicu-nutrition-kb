import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { GrowthChartComponent } from './growth-chart/growth-chart.component';
import { Growthchartprint} from './growth-chart-print/growth-chart-print.component';

const routes: Routes = [
    { path: 'growth-chart', component: GrowthChartComponent },
    { path: 'growth-chart-print', component: Growthchartprint }
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
  declarations: [GrowthChartComponent,Growthchartprint]
})
export class GrowthModule { }
