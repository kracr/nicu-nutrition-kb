import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { NursingModule } from './nursing.module';
import { NursingMedicationsComponent } from './nursing-medications/nursing-medications.component';

const routes: Routes = [
    { path: 'nursing-medications', component: NursingMedicationsComponent }
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
  declarations: [NursingMedicationsComponent]
})
export class MedicationsModule { }
