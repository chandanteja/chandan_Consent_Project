import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  constructor() { }

  private  map = new Map<string, string>();

  setEmail(key: string,value: string) {  
    //debugger;  
    this.map.set(key,value);
    console.log("Inside setEmail of datasharing service  value of key ",key,"is: ",this.map.get(key));
  }  
  
  getEmail(key:string) : any { 
    console.log("Inside getEmail() of datasharing service");
    if(this.map.has(key) != true) 
        return "ERROR IN FETCHING EMAIL";
    
      return this.map.get(key);
     
  }
  
  deleteEmail(key:string) : Number {
    console.log("Inside delete email of data sharing service")
    if(this.map.has(key) == true)
        {
          this.map.delete(key);
          return 1; // deleted successfully
        }

        return 0; // no key to delete
      
  }

}
