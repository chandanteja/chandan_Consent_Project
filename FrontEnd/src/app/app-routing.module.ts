import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DemographicFormComponent } from './demographic-form/demographic-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginPageComponent } from './login-page/login-page.component'
import { NavbarComponent } from './navbar/navbar.component';
import { ReceptionistComponent } from './receptionist/receptionist.component';
import { NurseComponent } from './nurse/nurse.component';
import { VitalFormComponent } from './vital-form/vital-form.component';
import { CheckpatientNurseComponent } from './checkpatient-nurse/checkpatient-nurse.component';
import { AssignDoctorComponent } from './assign-doctor/assign-doctor.component';
import { DoctorpageComponent } from './doctorpage/doctorpage.component';
import { DoctorFormComponent } from './doctor-form/doctor-form.component';

const routes: Routes = [
  {path:'',component:HomePageComponent},
  {path:'login',component:LoginPageComponent},
  {path:'demographicForm',component:DemographicFormComponent},
  {path:'receptionistPage',component:ReceptionistComponent},
  {path:'nursePage',component:NurseComponent},
  {path:'vitalsForm',component:VitalFormComponent},
  {path:'checkPatient',component:CheckpatientNurseComponent},
  {path:'assignDoctor',component:AssignDoctorComponent},
  
  {path:'doctorPage',component:DoctorpageComponent},
  {path:'doctorForm',component:DoctorFormComponent},

  
  
  
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
