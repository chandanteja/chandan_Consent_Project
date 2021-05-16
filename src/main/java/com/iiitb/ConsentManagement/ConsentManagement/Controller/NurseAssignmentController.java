package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Consent;
import com.iiitb.ConsentManagement.ConsentManagement.Services.NurseAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NurseAssignmentController {

    NurseAssignmentService nurseAssignmentService;

    @Autowired
    public NurseAssignmentController(NurseAssignmentService nurseAssignmentService)
    {
        this.nurseAssignmentService = nurseAssignmentService;
    }

    public String assignNurse(String patientID, Consent nurseConsent)
    {
        String actorID = null;

        System.out.println("[NurseAssignmentController-assignNurse]: Inside assignNurse() method");

        actorID = nurseAssignmentService.assignNurseToPatient(patientID,nurseConsent);

        System.out.println("After assigning patient with nurse came back from service ActorID is: "+ actorID);

        return actorID;
    }


}
