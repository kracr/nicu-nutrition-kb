import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { DatePipe, APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { BloodProductComponent } from './blood-product/blood-product.component';
import { BloodProductPrintComponent } from './blood-product-print/blood-product-print.component';

const routes: Routes = [
    { path: 'blood-product', component: BloodProductComponent },
    { path: 'blood-product-print', component: BloodProductPrintComponent }
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
  providers: [DatePipe],
  declarations: [BloodProductComponent,BloodProductPrintComponent]
})
export class BloodModule { }
