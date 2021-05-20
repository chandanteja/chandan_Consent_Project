import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { DataSharingService } from '../services/data-sharing.service';


interface Role {
  value: string;
  viewValue: string;
}



@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  

  // ROles we are making static for now but after the admin module is created then we will change it to dynamic
  // where the admin can add the roles.
 
 
  /* selectedRole : string = ''; */

  roles: Role[]=[{value: 'Receptionist', viewValue: 'Receptionist'},
  {value: 'Nurse', viewValue: 'Nurse'},
  {value: 'Doctor', viewValue: 'Doctor'},
  {value: 'Patient', viewValue: 'Patient'},
  {value: 'Admin', viewValue: 'Admin'}

];

  
 
  invalidLogin: boolean=false;
  //loginEmail! : string;

  constructor(private router: Router ,private authService:AuthService, private dataSharing : DataSharingService)
  {
  //selectedRole = null;
  


  }
  ngOnInit(): void {

    console.log("inside nginit");
    this.initForm()


  }
  
formGroup! : FormGroup ;

initForm()
{
  console.log("inside initform");
  this.formGroup = new FormGroup({
      email: new FormControl('',[Validators.required]),
      password: new FormControl('',[Validators.required]),
      role: new FormControl('',[Validators.required]),
    })
}




loginProcess(){

  //console.log("Role: ",this.selectedRole);

  
  
  if(this.formGroup.valid)
  {
   // this.loginEmail = this.formGroup.value['email'];
   this.dataSharing.setEmail("EmailID",this.formGroup.value['email']);
    
   this.authService.login(this.formGroup.value).subscribe(result=>{
      
    if(result['status']==200){
        console.log("came into loginProcess()",result);
        console.log("Inside loginProcess(): username", this.formGroup.value['email']);
        console.log("Inside loginProcess(): Password", this.formGroup.value['password']);
        console.log("Role is: ",result['entity']);

              if(result['entity'] === "ROLE_RECEPTIONIST")
                 this.router.navigateByUrl('/receptionistPage');
        
              else if(result['entity'] === "ROLE_NURSE")
                 this.router.navigateByUrl('/nursePage');
        
              else if(result['entity'] === "ROLE_DOCTOR")
                 this.router.navigateByUrl('/doctorPage');
        
      }
      else{
        console.log("inside else of loginprocess")
       alert("Invalid Credentials. Please Check Credentials.");
      // This below style of routing refreshes the page after login is invalid.
      this.router.navigateByUrl('/', {skipLocationChange: true}).then( ()=>this.router.navigate(['/login']) )
      }
    })
  }
 
}

}