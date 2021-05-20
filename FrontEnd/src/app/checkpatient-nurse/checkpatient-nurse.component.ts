import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DataSharingService } from '../services/data-sharing.service';
import { PatientassignmentNurseService, PatientData } from '../services/patientassignment-nurse.service';

@Component({
  selector: 'app-checkpatient-nurse',
  templateUrl: './checkpatient-nurse.component.html',
  styleUrls: ['./checkpatient-nurse.component.css']
})
export class CheckpatientNurseComponent implements OnInit {

  form: FormGroup = new FormGroup({});
  patientData!: PatientData[];
  loginEmail!: string;
  deleteEmailStatus! : Number;



  constructor(private fb: FormBuilder, private dataSharing: DataSharingService, private patientNurseAssignment: PatientassignmentNurseService) {

    this.form = fb.group({
      

    })

   }

  ngOnInit(): void {
    console.log("inside nginit of checkPatientNurseComponent ");
  
    this.loginEmail = this.dataSharing.getEmail("EmailID");

    console.log("Inside checkpatient-nurse component Email is: ",this.loginEmail);

    this.deleteEmailStatus = this.dataSharing.deleteEmail("EmailID");
    
    if(this.deleteEmailStatus ==  1)
    {
        console.log("Email deleted successfully in the data sharing object");
        this.form.addControl('loginEmail',new FormControl(this.loginEmail)); 

    }
    else
        console.log("EMail not deleted in datasharing object");

        console.log("Before calling datasaving")
        this.patientNurseAssignment.getPatientDataForNurse(this.form.value).subscribe(result=>{

            console.log("Inside subscribe of patientNurseAssignment-CheckpatientNurseComponent ")
            this.patientData = result;

            console.log("Length of patientData assigned for nurse is: ",this.patientData.length);

            if(this.patientData.length != 0)
            {
              console.log("Patient Data is:", this.patientData);
              console.log(this.patientData[0]["patientID"]);
            }


        })
        
    }



}
