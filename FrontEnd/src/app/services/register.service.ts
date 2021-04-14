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

  register(demoDetails: any):Observable<any> {
   
    console.log("We are in auth service before gng to spring boot.");
    console.log("type is: ", typeof(demoDetails));
    console.log("demoDetails are: ", demoDetails);
    console.log("type is: ", typeof(JSON.stringify(demoDetails)));
    console.log(this.httpClient.post(`${baseUrl}register`, demoDetails));

    return this.httpClient.post(`${baseUrl}register`,demoDetails,{responseType : "text"});
  }

  validateOTP(OTP: number, EMAIL: string):Observable<any>
  {
    console.log("Inside validateOTP service method ");
    console.log("OTP: ",OTP,"  Email: ",EMAIL);
    
    return this.httpClient.post( `${baseUrl}validateOTP`,{otp: OTP,email: EMAIL});

  }

}
