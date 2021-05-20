import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DataSharingService } from '../services/data-sharing.service';
import { DoctorAssignmentService } from '../services/doctor-assignment.service';


interface Deparment{
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-assign-doctor',
  templateUrl: './assign-doctor.component.html',
  styleUrls: ['./assign-doctor.component.css']
})
export class AssignDoctorComponent implements OnInit {

  form: FormGroup = new FormGroup({});
  loginEmail!: string;
  deleteEmailStatus! : Number;


  departments: Deparment[]=[
    {value: 'OPD', viewValue: 'OPD'},
    {value: 'Surgery', viewValue: 'Surgery'},

    ];

  constructor(private fb: FormBuilder, private dataSharing: DataSharingService, private doctorAssignment: DoctorAssignmentService) { 
    this.form = fb.group({
      patientID: ['', [Validators.required]],
      department: ['', [Validators.required]],

    })
  }

  ngOnInit(): void {
    console.log("inside nginit of AssignDoctorComponent ");
  
    this.loginEmail = this.dataSharing.getEmail("EmailID");

    console.log("Inside AssignDoctorComponent component Email is: ",this.loginEmail);

    //this.deleteEmailStatus = this.dataSharing.deleteEmail("EmailID");
    
    // if(this.deleteEmailStatus ==  1)
    // {
    //     console.log("Email deleted successfully in the data sharing object");
    //     this.form.addControl('loginEmail',new FormControl(this.loginEmail)); 

    // }
    // else
    //     console.log("EMail not deleted in datasharing object");
  }

  assignDoctor()

  {
    
    
    console.log("Inside assignDoctor()");
    this.form.addControl('loginEmail', new FormControl(this.loginEmail));
    console.log("Form data is: ", this.form.value);
    console.log("before calling service method.")
    

    this.doctorAssignment.assignDoctorForPatient(this.form.value).subscribe(result=>{

        console.log("Inside subscribe of doctorAssignment-AssignDoctorComponent ")
        console.log("Result is: ",result);

        if(result === "FAILED_TO_FIND_DOCTOR")
        {
          console.log("Failed to find doctor. No Doctor is available");
          alert(" All Doctors are busy. Try assigning later.");
        }
        else{
          alert("Doctor ID is: "+ result);
        }

    })


  }
}
