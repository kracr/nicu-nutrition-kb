import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { DoctorModule } from './doctor.module';
import { SummaryNotesComponent } from './summary-notes/summary-notes.component';
import { SummaryNotesPrintComponent } from './summary-notes-print/summary-notes-print.component';
import { ProgressNotesComponent } from './progress-notes/progress-notes.component';

const routes: Routes = [
  { path: 'summary-notes', component: SummaryNotesComponent },
  { path: 'progress-notes', component: ProgressNotesComponent },
  { path: 'summary-notes-print', component: SummaryNotesPrintComponent }
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
  declarations: [SummaryNotesComponent, SummaryNotesPrintComponent, ProgressNotesComponent]
})
export class SummaryModule { }
