import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { AssessmentSheetStableComponent } from './assessment-sheet-stable/assessment-sheet-stable.component';
import { StableKeysPipe } from './assessment-sheet-stable/keysPipe';


const routes: Routes = [
  { path: 'assessment-sheet-stable', component: AssessmentSheetStableComponent }
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
  declarations: [StableKeysPipe, AssessmentSheetStableComponent]
})
export class StableModule { }
