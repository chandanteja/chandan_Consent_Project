import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrl, baseUrlD, baseUrlN } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DoctorFormService {

  constructor(private httpClient : HttpClient) { }

  saveDoctorForm(docDetails: any): Observable<any> {

    console.log("We are in register service- saveVital() before gng to spring boot.");
    console.log("vitalDetails inside saveVital() are: ", docDetails);

    console.log("Login email (added separately) is: ", docDetails['loginEmail']);
    console.log("purpose (added separately) is: ", docDetails['purpose']);
    console.log("Operation (added separately) is: ", docDetails['operation']);

    return this.httpClient.post(`${baseUrlD}doctorFormData`, docDetails, {responseType: "text"});
  }


}
