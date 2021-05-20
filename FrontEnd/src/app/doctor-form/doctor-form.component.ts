import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DataSharingService } from '../services/data-sharing.service';
import { DoctorFormService } from '../services/doctor-form.service';

@Component({
  selector: 'app-doctor-form',
  templateUrl: './doctor-form.component.html',
  styleUrls: ['./doctor-form.component.css']
})
export class DoctorFormComponent implements OnInit {

  doctorForm!: FormGroup;
  loginEmail!: string;
  deleteEmailStatus!: Number;
  
  constructor(private router: Router, private dataSharing: DataSharingService, private doctorFormServic:DoctorFormService) { }

  ngOnInit(): void {

    console.log("inside nginit of vital form");
    
    this.doctForm();


    this.loginEmail = this.dataSharing.getEmail("EmailID");
    console.log("Inside [vital form commponent-adduser()] Email is: ", this.loginEmail);

    this.deleteEmailStatus = this.dataSharing.deleteEmail("EmailID");
    if (this.deleteEmailStatus == 1)
      console.log("Email deleted successfully in the data sharing object");
    else
      console.log("EMail not deleted in datasharing object");
  }

  
  doctForm() {
    
    console.log("inside doctForm");
    this.doctorForm = new FormGroup({
     
      patientName: new FormControl('', [Validators.required]),
      patientID: new FormControl('', [Validators.required]),
      
      bloodPressure: new FormControl('', [Validators.required]),
    
      temperature: new FormControl('', [Validators.required])

    })
     console.log("End of doctForm method");
  }

  saveDoctorData()
  {
    console.log("inside saveDoctorData()");
    console.log("Doctor form values: ", this.doctorForm.value);

    this.doctorForm.addControl('operation', new FormControl('CREATE'));    // this is create operation
    this.doctorForm.addControl('loginEmail', new FormControl(this.loginEmail));
    this.doctorForm.addControl('purpose', new FormControl('DOCTOR_CHECKUP'));

    if (!(this.doctorForm.valid)) {
      alert("Some Details are missing, Please check before you proceed.");

    }

    if (this.doctorForm.valid) 
    {
        console.log("Before calling saveVital() to save data");

        this.doctorFormServic.saveDoctorForm(this.doctorForm.value).subscribe(result => {

          console.log("Inside saveDoctorData of docform result is: ", result);

        })
    }

  }

}
