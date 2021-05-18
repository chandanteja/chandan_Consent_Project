import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrl, baseUrlN } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VitalParamsService {

  constructor(private httpClient : HttpClient) { }


  saveVital(vitalDetails: any): Observable<any> {

    console.log("We are in register service- saveVital() before gng to spring boot.");
    console.log("vitalDetails inside saveVital() are: ", vitalDetails);

    console.log("Login email (added separately) is: ", vitalDetails['loginEmail']);
    console.log("purpose (added separately) is: ", vitalDetails['purpose']);
    console.log("Operation (added separately) is: ", vitalDetails['operation']);

    return this.httpClient.post(`${baseUrlN}savevitalparams`, vitalDetails, {responseType: "text"});
  }


}
