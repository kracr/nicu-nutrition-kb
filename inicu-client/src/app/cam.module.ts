import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule, PlatformLocation, Location, LocationStrategy, HashLocationStrategy, PathLocationStrategy, APP_BASE_HREF} from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { CameraComponent } from './camera/camera.component';

const routes: Routes = [
  { path: 'camera', component: CameraComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    HttpModule,
    ChartModule,
    DateTimePickerModule
  ],
  declarations: [CameraComponent],
  exports: [CameraComponent]
})
export class CamModule { }
