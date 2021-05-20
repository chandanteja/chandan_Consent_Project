import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrlD, baseUrlN } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class DoctorAssignmentService {

  temp!: Observable<any>;

  constructor(private httpClient : HttpClient) { }


  assignDoctorForPatient(doctorAssignmentData: any): Observable<any>
  {

    console.log("inside DoctorAssignmentService->assignDoctorForPatient");
    console.log("form data is: ", doctorAssignmentData);
    console.log("Login Email is: ",doctorAssignmentData['loginEmail']);

    this.temp = this.httpClient.post(`${baseUrlN}assigndoctor`,doctorAssignmentData,{responseType: "text"});
    
    console.log("after httpClient.post call");
    
    return this.temp;

  }

}
