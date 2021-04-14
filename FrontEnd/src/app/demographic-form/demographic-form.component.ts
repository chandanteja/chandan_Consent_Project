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
   


  constructor(private _ngZone: NgZone, private router:Router, private regService: RegisterService,private otpService: OtpService) {}
  
  ngOnInit(): void {

    console.log("inside nginit of demographic form");
    this.registerForm();


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
    consent: new FormControl('',[Validators.required],)

    })
    console.log("End of registrationform method");
}

addUser()
{
  console.log("inside adduser()");
  console.log("registration form values: ",this.registrationForm.value);

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
       if(result === "1")
          {
            console.log("Inside result===1");
            alert("Patient data added successfully");
          }
          else if(result === "2")
          {
            console.log("Inside result==2");
            alert("Patient data not saved. Either Consent or OTP is missing. Try Saving again");
          }
          else if( result === "3")
          {
            console.log("Inside reuslt===3");
            alert("Consent is not given. Give consent to proceed forward.");
          }
          else if(result === "4")
          {
            console.log("Inside result==4")
            alert("Invalid OTP. Please try with new OTP.");
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
          alert("OTP Sent to the given mail ID");
          this.showTimer = true;

          this.interval = setInterval(() => {
            if(this.timeLeft > 0) {
              this.timeLeft--;
            } else {
              this.timeLeft = 0;
            }
          },1000)

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