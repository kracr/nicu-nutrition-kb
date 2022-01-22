import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { MedicationsComponent } from './medications/medications.component';
import { MedicationsPrintComponent } from './medications-print/medications-print.component';

const routes: Routes = [
    { path: 'medications', component: MedicationsComponent },
    { path: 'medications-print', component: MedicationsPrintComponent }
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
  declarations: [MedicationsComponent,MedicationsPrintComponent]
})
export class MedModule { }
