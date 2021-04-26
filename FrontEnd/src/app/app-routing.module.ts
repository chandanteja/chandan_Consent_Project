import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DemographicFormComponent } from './demographic-form/demographic-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginPageComponent } from './login-page/login-page.component'
import { NavbarComponent } from './navbar/navbar.component';
import { ReceptionistComponent } from './receptionist/receptionist.component';
const routes: Routes = [
  {path:'',component:HomePageComponent},
  {path:'login',component:LoginPageComponent},
  {path:'demographicForm',component:DemographicFormComponent},
  {path:'receptionistPage',component:ReceptionistComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
