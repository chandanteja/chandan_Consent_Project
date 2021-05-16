import { Component, OnInit } from '@angular/core';
import {CdkTextareaAutosize} from '@angular/cdk/text-field';
import { NgZone, ViewChild} from '@angular/core';
import {map, take} from 'rxjs/operators';
import { FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../services/register.service';
import { OtpService } from '../services/otp.service';
import { timer } from 'rxjs';
import { Observable } from 'rxjs';
import { DataSharingService } from '../services/data-sharing.service';



@Component({
  selector: 'app-demographic-form',
  templateUrl: './demographic-form.component.html',
  styleUrls: ['./demographic-form.component.css']
})


export class DemographicFormComponent implements OnInit {

  registrationForm! : FormGroup ;
  firstname! : string;
  lastname! : string; 
  showTimer : boolean = false;
  timeLeft: number = 300;
  interval!: any;
  public flag: any; // 0-> false. 1->true.
  loginEmail!: string;
  deleteEmailStatus! : Number;
   


  constructor(private _ngZone: NgZone, private router:Router, private regService: RegisterService,private otpService: OtpService, private dataSharing: DataSharingService) {}
  
  ngOnInit(): void {

    console.log("inside nginit of demographic form");
    this.registerForm();
    this.loginEmail = this.dataSharing.getEmail("EmailID");

    console.log("Inside [demographic form commponent-adduser()] Email is: ",this.loginEmail);

    this.deleteEmailStatus = this.dataSharing.deleteEmail("EmailID");
    if(this.deleteEmailStatus ==  1)
        console.log("Email deleted successfully in the data sharing object");
    else
        console.log("EMail not deleted in datasharing object");
    


  }
  @ViewChild('autosize') autosize!: CdkTextareaAutosize;


registerForm()
{
    console.log("inside registrationform");
    this.registrationForm = new FormGroup({
    firstName: new FormControl('',[Validators.required]),
    lastName: new FormControl('',[Validators.required]),
    email: new FormControl('',[Validators.required]),
    phoneNumber: new FormControl('',[Validators.required]),
    address: new FormControl('',[Validators.required]),
    age: new FormControl('',[Validators.required]),
    bloodGroup: new FormControl('',[Validators.required]),
    gender: new FormControl('',[Validators.required]),
    otp: new FormControl('',[Validators.required]),
    consent: new FormControl('',[Validators.required],),
    purpose: new FormControl('',[Validators.required],)

    })
    console.log("End of registrationform method");
}

addUser()
{
  console.log("inside adduser()");
  console.log("registration form values: ",this.registrationForm.value);
  // WHen add new partient is selected by receptionist then the operation is "CREATE" by default. So, we can hardcode here.
  // We will have different form for the update of existing patients.
  
  this.registrationForm.addControl('operation',new FormControl('CREATE')); 
  this.registrationForm.addControl('loginEmail',new FormControl(this.loginEmail)); 

  if(!(this.registrationForm.controls['otp'].valid))
  {
    alert("Please Enter OTP received on your provided Email.");
  }
  else if(!(this.registrationForm.controls['consent'].valid))
  {
      alert("Give your consent to proceed forward");
  }
  else if(!(this.registrationForm.valid))
  {
    alert("Some Details are missing, Please check before you proceed.");
  }


  if(this.registrationForm.valid)
  {

    
   // this.regService.validateOTP(this.registrationForm.controls['otp'].value,this.registrationForm.controls['email'].value).subscribe(result=>{
    
   //   console.log("Inside validateOTP component method-result: ",result);

     // if(result['status']==200){

    //    console.log("====Inside validateOTP method call in component ====:");
       
       // alert("OTP is valid");
      console.log("Before calling register() to save data");
      this.regService.register(this.registrationForm.value).subscribe(result=>{

        console.log("Inside register service result is: ",result);
       /* 
       if(result){
          console.log("Inside addUser()",result);
          alert("Added Patient Details successfully");
          
        }
        else{
          console.log("inside else of addUser")
          alert("Unable to add patient details successfully");
        }
        */
       if(result === "SUCCESS")
          {
            console.log("Inside result---- SUCCESS");
            alert("Patient data added successfully");
          }
          else if(result === "ERROR_OCCURRED_IN_SAVING_DATA")
          {
            console.log("Inside result==ERROR_OCCURRED_IN_SAVING_DATA");
            alert("Patient data not saved. Either Consent or OTP is missing. Try Saving again");
          }
          else if( result === "CONSENT_NOT_GIVEN")
          {
            console.log("Inside reuslt --- CONSENT_NOT_GIVEN");
            alert("Consent is not given. Give consent to proceed forward.");
          }
          else if(result === "INVALID_OTP")
          {
            console.log("Inside result==INVALID_OTP")
            alert("Invalid OTP. Please try with new OTP.");
          }
          else if(result === "OUT_OF_OFFICE_HOURS")
          {
            console.log("Inside OUT_OF_OFFICE_HOURS");
            alert("Out of Office hours. Not permitted to do this operation")
          }

      })
  //  }
        

     /* }
      else{ 
        alert("OTP is invalid. Retry again");
       
      }
    
    
    })*/
      
    
/*    if(this.flag==1)
    {
      console.log("inside flag==true if case");
      this.regService.register(this.registrationForm.value).subscribe(result=>{

        if(result.success){
          console.log("came into addUser()",result);
          alert(result.message);
          
        }
        else{
          console.log("inside else of addUser")
          alert(result.message);
        }

      })
    }*/

  }
}

generateOTP()
{
  this.timeLeft = 300;
  console.log("inside generateOTP()");
  console.log("Email inside generateOTP: ",this.registrationForm.controls['email'].value);
  console.log("registration form values inside generateOTP: ",this.registrationForm.value);
  if(this.registrationForm.controls['email'].valid)
  {
    
      this.otpService.getOTP(this.registrationForm.value).subscribe(result=>{

        console.log("before result of getOTP",result);
        console.log("type of result", typeof(result));

        if(result['status']==200){

          
          console.log("came into generateOTP()",result);
          
          this.showTimer = true;

          this.interval = setInterval(() => {
            if(this.timeLeft > 0) {
              this.timeLeft--;
            } else {
              this.timeLeft = 0;
            }
          },1000)

          alert("OTP Sent to the given mail ID");

          console.log("after timer...",result);
          
        }
        else{
          console.log("inside else of addUser")
          alert(result.message);
        }

      })
    }
  }
}