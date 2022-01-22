import { DateTimePickerModule } from 'ng-pick-datetime';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular-highcharts';
import { AdminComponent } from './admin/admin.component';
import { InvestigationKeysPipe } from './investigation-mgmnt/keysPipe';
import { UserpanelComponent } from './userpanel/userpanel.component';
import { RoommanagementComponent } from './roommanagement/roommanagement.component';
import { DrugmanagementComponent } from './drugmanagement/drugmanagement.component';
import { SettingsComponent } from './settings/settings.component';
import { SmsmanagementComponent } from './smsmanagement/smsmanagement.component';
import { InvestigationMgmntComponent } from './investigation-mgmnt/investigation-mgmnt.component';
import { EmailManagementComponent } from './email-management/email-management.component';
import { SearchPanel } from './investigation-mgmnt/searchInvestigationPanel';

const routes: Routes = [
    { path: 'admin', component: AdminComponent },
    { path: 'userpanel', component: UserpanelComponent },
    { path: 'roommanagement', component: RoommanagementComponent },
    { path: 'drugmanagement', component: DrugmanagementComponent },
    { path: 'settings', component: SettingsComponent },
    { path: 'smsmanagement', component: SmsmanagementComponent },
    { path: 'investigation', component: InvestigationMgmntComponent },
    { path: 'emailManagement', component: EmailManagementComponent },
];

@NgModule({
  imports: [
    ChartModule,
    CommonModule,
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    HttpModule,
    DateTimePickerModule
  ],
  declarations: [InvestigationKeysPipe, AdminComponent, UserpanelComponent, RoommanagementComponent, DrugmanagementComponent, SettingsComponent, SmsmanagementComponent, InvestigationMgmntComponent, EmailManagementComponent,SearchPanel]
})
export class AdmModule { }
