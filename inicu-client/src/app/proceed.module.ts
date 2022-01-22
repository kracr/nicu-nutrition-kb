import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { ProceduresComponent } from './procedures/procedures.component';
import { ExchangeTransfusionPrint } from './exchange-transfusion-print/exchange-transfusion-print.component';

const routes: Routes = [
    { path: 'procedures', component: ProceduresComponent },
    { path: 'exchange-transfusion-print', component: ExchangeTransfusionPrint }
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
  declarations: [ProceduresComponent,ExchangeTransfusionPrint],
  exports: [ProceduresComponent]
})
export class ProceedModule { }
