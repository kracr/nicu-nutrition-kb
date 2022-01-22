import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { AnalyticsComponent } from './analytics/analytics.component';
import { NamePipe } from './analytics/nameFilter.pipe';
import { UsagePrintComponent } from './usage-print/usage-print.component';
import { SinsheetPrintComponent } from './sinsheet-print/sinsheet-print.component';

const routes: Routes = [
    { path: 'analytics', component: AnalyticsComponent },
    { path: 'sinsheetPdf', component: SinsheetPrintComponent },
    { path: 'usagePdf', component: UsagePrintComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    ChartModule,
    DateTimePickerModule
  ],
  declarations: [AnalyticsComponent, NamePipe, UsagePrintComponent, SinsheetPrintComponent]
})
export class AnaModule { }
