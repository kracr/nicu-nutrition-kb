import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { ChartModule } from 'angular-highcharts';
import { CamModule } from './cam.module';
import { CommonModule, PlatformLocation, Location, LocationStrategy, HashLocationStrategy, PathLocationStrategy, APP_BASE_HREF} from '@angular/common';
import { DoctorPanelComponent } from './doctor-panel/doctor-panel.component';
import { AssessmentSheetComponent } from './assessment-sheet/assessment-sheet.component';
import { KeysPipe } from './summary-notes/keysPipe';


const routes: Routes = [
    { path: 'doctor-panel', component: DoctorPanelComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    ChartModule,
    CamModule,
    DateTimePickerModule
  ],
  declarations: [DoctorPanelComponent, AssessmentSheetComponent, KeysPipe],
  exports: [DoctorPanelComponent, AssessmentSheetComponent, KeysPipe]
})
export class DoctorModule { }
