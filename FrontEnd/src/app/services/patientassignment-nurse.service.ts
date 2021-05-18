import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrlN } from 'src/environments/environment';

export interface PatientData{
  patientName: any;
  age: number;
  bloodGroup: any;
  patientID: any
}


@Injectable({
  providedIn: 'root'
})


export class PatientassignmentNurseService {

  temp!: Observable<any>;
  patientData!: PatientData[];
  constructor(private httpClient : HttpClient) { }

  getPatientDataForNurse(nurseEmail: any): Observable<any>
  {

    console.log("inside OrganHeartService->getPatientData");
    console.log("form data is: ", nurseEmail);
    
    console.log("Login Email is: ",nurseEmail['loginEmail']);
    this.temp = this.httpClient.get<PatientData[][]>(`${baseUrlN}getNursePatient`, {params:nurseEmail, headers:{'Accept':'application/json', 'Content-Type':'application/json'} });
    

    
    console.log("after httpClient.get call");
    console.log("Data from backend is: ", this.temp);
    
    return this.temp;

  }
}
