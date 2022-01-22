import { NgModule } from '@angular/core';
import { CustomPreloading } from './custom-preloading';
import { Routes, RouterModule } from '@angular/router';
import { PlatformLocation, Location, LocationStrategy, HashLocationStrategy, PathLocationStrategy, APP_BASE_HREF} from '@angular/common';
const routes: Routes = [
  { path: 'dash', loadChildren: './dash.module#DashModule'},
  { path: 'discharged', loadChildren: './discharged.module#DischargedModule'},
  { path: 'adm', loadChildren: './adm.module#AdmModule'},
  { path: 'ana', loadChildren: './ana.module#AnaModule'},
  { path: 'admis', loadChildren: './admis.module#AdmisModule'},
  { path: 'admisprint', loadChildren: './admisprint.module#AdmisprintModule'},
  { path: 'doctor', loadChildren: './doctor.module#DoctorModule'},
  { path: 'nursing', loadChildren: './nursing.module#NursingModule'},
  { path: 'discharge', loadChildren: './discharge.module#DischargeModule'},
  { path: 'respiratory', loadChildren: './respiratory.module#RespiratoryModule'},
  { path: 'jaundice', loadChildren: './jaundice.module#JaundiceModule'},
  { path: 'infection', loadChildren: './infection.module#InfectionModule'},
  { path: 'cns', loadChildren: './cns.module#CnsModule'},
  { path: 'cardiac', loadChildren: './cardiac.module#CardiacModule'},
  { path: 'metabolic', loadChildren: './metabolic.module#MetabolicModule'},
  { path: 'miscellaneous', loadChildren: './miscellaneous.module#MiscellaneousModule'},
  { path: 'stable', loadChildren: './stable.module#StableModule'},
  { path: 'renal', loadChildren: './renal.module#RenalModule'},
  { path: 'lab', loadChildren: './lab.module#LabModule'},
  { path: 'feed', loadChildren: './feed.module#FeedModule'},
  { path: 'med', loadChildren: './med.module#MedModule'},
  { path: 'blood', loadChildren: './blood.module#BloodModule'},
  { path: 'proceed', loadChildren: './proceed.module#ProceedModule'},
  { path: 'summary', loadChildren: './summary.module#SummaryModule'},
  { path: 'trends-graph', loadChildren: './trends-graph.module#TrendsGraphModule'},
  { path: 'growth', loadChildren: './growth.module#GrowthModule'},
  { path: 'screen', loadChildren: './screen.module#ScreenModule'},
  { path: 'out', loadChildren: './out.module#OutModule'},
  { path: 'cam', loadChildren: './cam.module#CamModule'},
  { path: 'device', loadChildren: './device.module#DeviceModule'},
  { path: 'doctorOrder', loadChildren: './doctor-order.module#DoctorOrderModule'},
  { path: 'anthropometry', loadChildren: './anthropometry.module#AnthropometryModule'},
  { path: 'charts', loadChildren: './charts.module#ChartsModule', data: {preload: true}},
  { path: 'nursing-lab', loadChildren: './nursing-lab.module#NursingLabModule'},
  { path: 'medications', loadChildren: './medications.module#MedicationsModule'},
  { path: 'treatment', loadChildren: './treatment.module#TreatmentModule'},
  { path: 'procedure', loadChildren: './procedures.module#ProceduresModule'},
  { path: 'notes', loadChildren: './notes.module#NotesModule'},
  { path: 'output', loadChildren: './output.module#OutputModule'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: CustomPreloading })],
  exports: [RouterModule],
  providers: [CustomPreloading]
})
export class AppRoutingModule { }
