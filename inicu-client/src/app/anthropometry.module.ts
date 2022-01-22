import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NursingModule } from './nursing.module';
import { NursingAnthropometryComponent } from './nursing-anthropometry/nursing-anthropometry.component';

const routes: Routes = [
    { path: 'nursing-anthropometry', component: NursingAnthropometryComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    NursingModule,
    DateTimePickerModule
  ],
  declarations: [NursingAnthropometryComponent]
})
export class AnthropometryModule { }
