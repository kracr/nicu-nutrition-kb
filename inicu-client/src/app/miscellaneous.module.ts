import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { AssessmentSheetMiscellaneousComponent } from './assessment-sheet-miscellaneous/assessment-sheet-miscellaneous.component';
import { MiscKeysPipe } from './assessment-sheet-miscellaneous/keysPipe';


const routes: Routes = [
  { path: 'assessment-sheet-miscellaneous', component: AssessmentSheetMiscellaneousComponent }
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
  declarations: [MiscKeysPipe, AssessmentSheetMiscellaneousComponent]
})
export class MiscellaneousModule { }
