import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NursingModule } from './nursing.module';
import { NursingNotificationComponent } from './nursing-notification/nursing-notification.component';

const routes: Routes = [
    { path: 'nursing-order', component: NursingNotificationComponent }
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
  declarations: [NursingNotificationComponent]
})
export class DoctorOrderModule { }
