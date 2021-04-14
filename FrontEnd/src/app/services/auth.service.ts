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

 /* login(credentials: any)
  {
      
      return this.http.post('http://localhost:3000/api/users/login',JSON.stringify(credentials)).pipe(map((response: any) =>{console.log(response.json())}));

  }
  */

  login(credentials: any):Observable<any> {
    console.log("Inside AUthService: ",credentials.USERNAME)
    console.log("Inside AUthService: ",credentials.PASSWORD)
    return this.http.post(`${baseUrl}login`,credentials)
  }
}
