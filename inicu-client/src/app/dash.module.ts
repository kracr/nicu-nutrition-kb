import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SearchUhidName } from './dashboard/dashboardFilters';
import { CounsellingSheetFilter } from './dashboard/counsellingSheetFilter';
import { CounsellingSheetPrintComponent } from './counselling-sheet-print/counselling-sheet-print.component';
import { DoctorTaskPrintComponent } from './doctor-task-print/doctor-task-print.component';
const routes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'counselling-sheet-print', component: CounsellingSheetPrintComponent},
    { path: 'doctor-task-print', component: DoctorTaskPrintComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    DateTimePickerModule
  ],
  declarations: [DashboardComponent,
    SearchUhidName,
    CounsellingSheetFilter,
    CounsellingSheetPrintComponent,
    DoctorTaskPrintComponent
  ]
})
export class DashModule { }
