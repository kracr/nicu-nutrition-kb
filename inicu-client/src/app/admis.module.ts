import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DoctorModule } from './doctor.module';
import { AdmissionFormKeysPipe } from './admission-form/keysPipe';
import { AdmissionFormComponent } from './admission-form/admission-form.component';
import { ViewProfileComponent } from './view-profile/view-profile.component';

const routes: Routes = [
    { path: 'admissionform', component: AdmissionFormComponent },
    { path: 'view-profile', component: ViewProfileComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    DoctorModule,
    DateTimePickerModule
  ],
  declarations: [AdmissionFormKeysPipe, AdmissionFormComponent, ViewProfileComponent]
})
export class AdmisModule { }
