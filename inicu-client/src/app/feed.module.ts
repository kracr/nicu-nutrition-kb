import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { NutritionComponent } from './nutrition/nutrition.component';
import { NutritionPrintComponent } from './nutrition-print/nutrition-print.component';

const routes: Routes = [
    { path: 'nutrition', component: NutritionComponent },
    { path: 'nutrition-print', component: NutritionPrintComponent }
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
  declarations: [NutritionComponent,NutritionPrintComponent]
})
export class FeedModule { }
