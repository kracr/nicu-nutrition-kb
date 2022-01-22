import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DischargepatientsComponent } from './dischargepatients/dischargepatients.component';

const routes: Routes = [
    { path: 'dischargePatients', component: DischargepatientsComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    DateTimePickerModule
  ],
  declarations: [DischargepatientsComponent]
})
export class DischargedModule { }
