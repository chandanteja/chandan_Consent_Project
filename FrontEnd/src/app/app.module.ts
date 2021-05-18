import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
//import { AlertModule } from 'ngx-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatButtonModule} from '@angular/material/button'; 
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
//import { NgxSpinnerModule } from "ngx-spinner";

import {MatSelectModule} from '@angular/material/select'; 
import {MatToolbarModule} from '@angular/material/toolbar'; 
import {MatCardModule} from '@angular/material/card'; 
import {MatInputModule} from '@angular/material/input'; 
import {MatFormFieldModule} from '@angular/material/form-field';
import { NavbarComponent } from './navbar/navbar.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './services/auth.service';
import { DemographicFormComponent } from './demographic-form/demographic-form.component';

 
import {MatCheckboxModule} from '@angular/material/checkbox'; 

import {MatRadioModule} from '@angular/material/radio';
import { ReceptionistComponent } from './receptionist/receptionist.component';
import { NavbarReceptionistComponent } from './navbar-receptionist/navbar-receptionist.component'; 
import { NurseComponent } from './nurse/nurse.component';
import { NavbarNurseComponent } from './navbar-nurse/navbar-nurse.component';
import { VitalFormComponent } from './vital-form/vital-form.component';
import { CheckpatientNurseComponent } from './checkpatient-nurse/checkpatient-nurse.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    NavbarComponent,
    HomePageComponent,
    DemographicFormComponent,
    ReceptionistComponent,
    NavbarReceptionistComponent,
    NurseComponent,
    NavbarNurseComponent,
    VitalFormComponent,
    CheckpatientNurseComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    //NgxSpinnerModule,
    MatRadioModule,
    MatCheckboxModule,



  //  AlertModule.forRoot()

  ],
  providers: [AuthService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
