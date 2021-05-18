package com.iiitb.consentmanagement2.nurse.Controller;

import com.iiitb.consentmanagement2.nurse.HelperClasses.BasicPatientData;
import com.iiitb.consentmanagement2.nurse.HelperClasses.NurseEmail;
import com.iiitb.consentmanagement2.nurse.Services.GetPatientAssignedToNurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NursePatientAssignment {

    GetPatientAssignedToNurse getPatientAssignedToNurse;

           @Autowired
    public NursePatientAssignment(GetPatientAssignedToNurse getPatientAssignedToNurse)
    {
        this.getPatientAssignedToNurse = getPatientAssignedToNurse;
    }


    @GetMapping(path = "/getNursePatient", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<BasicPatientData>  findPatient( final NurseEmail nurseEmail)
    {
        System.out.println("[NursePatientAssignement]: Inside findPatient()");
        System.out.println("Nurse Email is: "+ nurseEmail.getLoginEmail());
        List<BasicPatientData> basicPatientDataList = new ArrayList<>();

        BasicPatientData basicPatientData = getPatientAssignedToNurse.getPatientDataForNurse(nurseEmail);
        System.out.println("Came back from getPatientAssignedToNurse");

        if(basicPatientData == null) {
            System.out.println("Received null from getPatientDataForNurse()> Inside if condition ");
            return null;
        }

        basicPatientDataList.add(basicPatientData);

        return basicPatientDataList;
    }

}
