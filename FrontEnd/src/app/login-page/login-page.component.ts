import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


interface Role {
  value: string;
  viewValue: string;
}

//interface Credentials
//{
 // userNameFrontEnd: string,
 // passWordFrontEnd: string,
 // roleFrontEnd: Role
// }

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  //credentials: Credentials={userNameFrontEnd:'',passWordFrontEnd:'',roleFrontEnd:{value:'',viewValue:''}};


  // ROles we are making static for now but after the admin module is created then we will change it to dynamic
  // where the admin can add the roles.
   selectedRole : string = '';
  roles: Role[]=[{value: 'receptionist', viewValue: 'Receptionist'},
  {value: 'nurse', viewValue: 'Nurse'},
  {value: 'doctor', viewValue: 'Doctor'},
  {value: 'patient', viewValue: 'Patient'},
  {value: 'admin', viewValue: 'Admin'}

];
  
  userName : string = '';
  password : string = '';

  invalidLogin: boolean=false;

  constructor(private router: Router ,private authService:AuthService)
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
      USERNAME: new FormControl('',[Validators.required]),
      PASSWORD: new FormControl('',[Validators.required]),
      ROLE: new FormControl('',[Validators.required]),
    })
}




loginProcess(){

  console.log("UserName: ",this.userName);
  console.log("Password: ",this.password);
  console.log("Role: ",this.selectedRole);

  
  if(this.formGroup.valid)
  {
    this.authService.login(this.formGroup.value).subscribe(result=>{
      if(result.success){
        console.log("came into loginProcess()",result);
        alert(result.message);
        
      }
      else{
        console.log("inside else of loginprocess")
        alert(result.message);
      }
    })
  }
  this.router.navigateByUrl('/demographicForm');
}

}