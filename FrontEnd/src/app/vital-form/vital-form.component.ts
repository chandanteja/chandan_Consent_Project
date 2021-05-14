import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Validators} from '@angular/forms';
import {FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {RegisterService} from '../services/register.service';
import {DataSharingService} from '../services/data-sharing.service';

@Component({
  selector: 'app-vital-form',
  templateUrl: './vital-form.component.html',
  styleUrls: ['./vital-form.component.css']
})
export class VitalFormComponent implements OnInit {

  vitalForm!: FormGroup;
  loginEmail!: string;
  deleteEmailStatus!: Number;

  constructor(private router: Router, private regService: RegisterService, private dataSharing: DataSharingService) {
  }

  ngOnInit(): void {

    console.log("inside nginit of vital form");
    this.vitalsForm();

    this.loginEmail = this.dataSharing.getEmail("EmailID");
    console.log("Inside [vital form commponent-adduser()] Email is: ", this.loginEmail);

    this.deleteEmailStatus = this.dataSharing.deleteEmail("EmailID");
    if (this.deleteEmailStatus == 1)
      console.log("Email deleted successfully in the data sharing object");
    else
      console.log("EMail not deleted in datasharing object");

  }

  vitalsForm() {
    console.log("inside vitalform");
    this.vitalForm = new FormGroup({
      Age: new FormControl('', [Validators.required]),
      Height: new FormControl('', [Validators.required]),
      Weight: new FormControl('', [Validators.required]),
      HeartRate: new FormControl('', [Validators.required]),
      BloodPressure: new FormControl('', [Validators.required]),
      SpO2: new FormControl('', [Validators.required]),
      ResRate: new FormControl('', [Validators.required]),
      Temperature: new FormControl('', [Validators.required])

    })
    console.log("End of vitalform method");
  }

  addVitals() {
    console.log("inside addVitals()");
    console.log("vital form values: ", this.vitalForm.value);

    this.vitalForm.addControl('operation', new FormControl('CREATE'));
    this.vitalForm.addControl('loginEmail', new FormControl(this.loginEmail));
    this.vitalForm.addControl('purpose', new FormControl('Enter Vital'));

    if (!(this.vitalForm.valid)) {
      alert("Some Details are missing, Please check before you proceed.");
    }
    if (this.vitalForm.valid) {
      console.log("Before calling saveVital() to save data");

      this.regService.saveVital(this.vitalForm.value).subscribe(result => {

        console.log("Inside register service of vital result is: ", result);

      })
    }

  }
}
