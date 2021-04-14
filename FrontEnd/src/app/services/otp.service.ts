import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseUrl } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OtpService {

  constructor(private httpClient: HttpClient) { 
      
  }

  getOTP(email : any): Observable<any>
  {
    console.log("We are in auth service before gng to spring boot.");
    console.log("email is:",email);
    return this.httpClient.post(`${baseUrl}generateOTP`,email,{responseType:"json"});
    
  }


}
