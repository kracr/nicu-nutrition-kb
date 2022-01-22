import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { AssessmentSheetRespiratoryComponent } from './assessment-sheet-respiratory/assessment-sheet-respiratory.component';
import { RespiratoryKeysPipe } from './assessment-sheet-respiratory/keysPipe';

const routes: Routes = [
  { path: 'assessment-sheet-respiratory', component: AssessmentSheetRespiratoryComponent }
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
  declarations: [ RespiratoryKeysPipe, AssessmentSheetRespiratoryComponent]

})
export class RespiratoryModule { }
