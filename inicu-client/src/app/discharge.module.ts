import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DoctorModule } from './doctor.module';
import { DischargeSummaryComponent } from './discharge-summary/discharge-summary.component';
import { DischargePrintComponent } from './discharge-print/discharge-print.component';
import { CaseSummaryPrintComponent } from './case-summary-print/case-summary-print.component';
import { InvestigationKeysPipe } from './discharge-summary/investigationKeyPipe';

const routes: Routes = [
    { path: 'discharge-summary', component: DischargeSummaryComponent },
    { path: 'discharge-summary-print', component: DischargePrintComponent },
    { path: 'case-summary-print', component: CaseSummaryPrintComponent }
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
  declarations: [DischargeSummaryComponent, DischargePrintComponent, CaseSummaryPrintComponent, InvestigationKeysPipe]
})
export class DischargeModule { }
