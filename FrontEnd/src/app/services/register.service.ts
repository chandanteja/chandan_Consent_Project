import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrl } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private httpClient : HttpClient) { }

  register(demoDetails: any):Observable<any> 
  {
   
    console.log("We are in register service- register() before gng to spring boot.");
    console.log("demoDetails inside register() are: ", demoDetails);
    
    console.log("Login email (added separately) is: ",demoDetails['loginEmail']);
    
    // console.log("type is: ", typeof(demoDetails));
    //console.log("type is: ", typeof(JSON.stringify(demoDetails)));
   // console.log(this.httpClient.post(`${baseUrl}register`, demoDetails));

    return this.httpClient.post(`${baseUrl}register`,demoDetails,{responseType : "text"});
  }

  saveVital(vitalDetails: any): Observable<any> {

    console.log("We are in register service- saveVital() before gng to spring boot.");
    console.log("vitalDetails inside saveVital() are: ", vitalDetails);

    console.log("Login email (added separately) is: ", vitalDetails['loginEmail']);
    console.log("purpose (added separately) is: ", vitalDetails['purpose']);
    // console.log("type is: ", typeof(demoDetails));
    //console.log("type is: ", typeof(JSON.stringify(demoDetails)));
    // console.log(this.httpClient.post(`${baseUrl}register`, demoDetails));

    return this.httpClient.post(`${baseUrl}enterVital`, vitalDetails, {responseType: "text"});
  }

  /*
  validateOTP(OTP: number, EMAIL: string):Observable<any>
  {
    console.log("We are in register service - validateOTP() before gng to spring boot.");
    console.log("Inside validateOTP service method ");
    console.log("OTP: ",OTP,"  Email: ",EMAIL);
    
    return this.httpClient.post( `${baseUrl}validateOTP`,{otp: OTP,email: EMAIL});

  }
  */

}
