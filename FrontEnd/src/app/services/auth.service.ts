import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { baseUrl } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private http: HttpClient) { }

 
  login(credentials: any):Observable<any> {
    console.log("Inside AUthService: ",credentials.email)
    console.log("Inside AUthService: ",credentials.password)
    // Here we need to loop by sending credentials to different actor microservices like send credentials
    // to receptionist and then verify if fails then come back and send the credentials to nurse microservice and verify continue this till it succeeds.
    return this.http.post(`${baseUrl}actorlogin`,credentials)
  }
}
