import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { ScreeningComponent } from './screening/screening.component';
import { ScreeningPrintComponent} from './screening-print/screening-print.component'

const routes: Routes = [
    { path: 'screening', component: ScreeningComponent },
    { path: 'screening-print', component: ScreeningPrintComponent}

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
  declarations: [ScreeningComponent,ScreeningPrintComponent]
})
export class ScreenModule { }
