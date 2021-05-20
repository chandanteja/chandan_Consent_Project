import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Validators} from '@angular/forms';
import {FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {RegisterService} from '../services/register.service';
import {DataSharingService} from '../services/data-sharing.service';
import { VitalParamsService } from '../services/vital-params.service';

@Component({
  selector: 'app-vital-form',
  templateUrl: './vital-form.component.html',
  styleUrls: ['./vital-form.component.css']
})
export class VitalFormComponent implements OnInit {

  vitalForm!: FormGroup;
  loginEmail!: string;
  deleteEmailStatus!: Number;

  constructor(private router: Router, private vitalsService: VitalParamsService, private dataSharing: DataSharingService) {
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
      patientName: new FormControl('', [Validators.required]),
      patientID: new FormControl('', [Validators.required]),
      age: new FormControl('', [Validators.required]),
      height: new FormControl('', [Validators.required]),
      weight: new FormControl('', [Validators.required]),
      heartRate: new FormControl('', [Validators.required]),
      bloodPressure: new FormControl('', [Validators.required]),
      spO2: new FormControl('', [Validators.required]),
      resRate: new FormControl('', [Validators.required]),
      temperature: new FormControl('', [Validators.required])

    })
    console.log("End of vitalform method");
  }

  addVitals() {
    
    console.log("inside addVitals()");
    console.log("vital form values: ", this.vitalForm.value);

    this.vitalForm.addControl('operation', new FormControl('CREATE'));    // this is create operation
    this.vitalForm.addControl('loginEmail', new FormControl(this.loginEmail));
    this.vitalForm.addControl('purpose', new FormControl('VITAL_PARAMETERS'));

    if (!(this.vitalForm.valid)) {
      alert("Some Details are missing, Please check before you proceed.");
    }

    if (this.vitalForm.valid) {
      console.log("Before calling saveVital() to save data");

      this.vitalsService.saveVital(this.vitalForm.value).subscribe(result => {

        console.log("Inside register service of vital result is: ", result);

        if(result === "SUCCESS")
        {
          console.log("Inside result---- SUCCESS");
            alert("Patient vitals data saved successfully");
        }
        else if(result === "OUT_OF_OFFICE_HOURS")
        {
          console.log("Inside OUT_OF_OFFICE_HOURS");
          alert("Out of Office hours. Not permitted to do this operation")
        }
        else
        {
            console.log("Failed to save patient Vital params");
            alert("Failed to save patient vital parameters data");
        }
        

      })
    }

  }
}
