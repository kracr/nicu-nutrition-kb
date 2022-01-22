import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { AssessmentSheetRenalComponent } from './assessment-sheet-renal/assessment-sheet-renal.component';

const routes: Routes = [
  { path: 'assessment-sheet-renal', component: AssessmentSheetRenalComponent }
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
  declarations: [AssessmentSheetRenalComponent]
})
export class RenalModule { }
