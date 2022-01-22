import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { CnsKeysPipe } from './assessment-sheet-cns/keysPipe';
import { AssessmentSheetCnsComponent } from './assessment-sheet-cns/assessment-sheet-cns.component';

const routes: Routes = [
  { path: 'assessment-sheet-cns', component: AssessmentSheetCnsComponent }
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
  declarations: [CnsKeysPipe, AssessmentSheetCnsComponent]
})
export class CnsModule { }
