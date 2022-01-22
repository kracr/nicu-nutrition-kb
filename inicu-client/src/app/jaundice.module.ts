import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { AssessmentSheetJaundiceComponent } from './assessment-sheet-jaundice/assessment-sheet-jaundice.component';
import { JaundiceKeysPipe } from './assessment-sheet-jaundice/keysPipe';
import { JaundicePrintComponent } from './jaundice-print/jaundice-print.component';

 
const routes: Routes = [
    { path: 'assessment-sheet-jaundice', component: AssessmentSheetJaundiceComponent },
    { path: 'jaundice-print', component: JaundicePrintComponent }
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
  declarations: [JaundiceKeysPipe, AssessmentSheetJaundiceComponent, JaundicePrintComponent]
})
export class JaundiceModule { }
