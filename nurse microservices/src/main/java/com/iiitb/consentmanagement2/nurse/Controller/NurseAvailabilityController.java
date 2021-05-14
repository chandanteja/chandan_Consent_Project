package com.iiitb.consentmanagement2.nurse.Controller;

import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.Beans.Status;
import com.iiitb.consentmanagement2.nurse.Services.NurseAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NurseAvailabilityController {

    NurseAvailabilityService nurseAvailabilityService;

    @Autowired
    public NurseAvailabilityController(NurseAvailabilityService nurseAvailabilityService)
    {
        this.nurseAvailabilityService = nurseAvailabilityService;
    }


    @PostMapping(path="/findavailablenurse")
    public @ResponseBody
    String findAvailableNurses()
    {
        System.out.println("[NurseAvailabilityController]: Inside findAvailableNurse() ");

        Status actorStatus = Status.IDLE;
        ROLE actorRole = ROLE.ROLE_NURSE;

        String actorID = nurseAvailabilityService.getAvailableNurse(actorStatus,actorRole);
        System.out.println("[NurseAvailabilityController]: After returning from getAvailableNurses");

        if(actorID == null)
        {
            System.out.println("[NurseAvailabilityController]: Inside if actorid==null");
            return "FAILED_TO_FIND_NURSE";
        }

        return actorID;
    }

}