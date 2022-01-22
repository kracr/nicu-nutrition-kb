import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { AssessmentSheetInfectionComponent } from './assessment-sheet-infection/assessment-sheet-infection.component';
import { InfectionKeysPipe } from './assessment-sheet-infection/keysPipe';

const routes: Routes = [
   	{ path: 'assessment-sheet-infection', component: AssessmentSheetInfectionComponent }
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
  declarations: [InfectionKeysPipe, AssessmentSheetInfectionComponent]
})
export class InfectionModule { }
