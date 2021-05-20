import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrl, baseUrlD, baseUrlN } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  currentUrl! : any;

  constructor(private http: HttpClient) { }

 
  login(credentials: any):Observable<any> {
    console.log("Inside AUthService: ",credentials.email)
    console.log("Inside AUthService: ",credentials.password)
    console.log("Inside AUthService: ",credentials.role)

    if(credentials.role === "Receptionist")
    {
      this.currentUrl = baseUrl;
    }
    else if(credentials.role === "Nurse")
    {
      this.currentUrl = baseUrlN;
    }
    else if(credentials.role === "Doctor")
    {
      this.currentUrl = baseUrlD;
    }
    

    // Here we need to loop by sending credentials to different actor microservices like send credentials
    // to receptionist and then verify if fails then come back and send the credentials to nurse microservice and verify continue this till it succeeds.
    
    return this.http.post(`${this.currentUrl}actorlogin`,credentials)
  }
}
