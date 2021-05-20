package com.iiitb.consentmanagement2.nurse.Controller;

import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.Beans.Status;
import com.iiitb.consentmanagement2.nurse.Services.NurseAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

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
        // Also check if nurse is logged in then only use that nurse for assignment
        String actorID = nurseAvailabilityService.getAvailableNurse(actorStatus,actorRole);
        System.out.println("[NurseAvailabilityController]: After returning from getAvailableNurses");

        if(actorID == null)
        {
            System.out.println("[NurseAvailabilityController]: Inside if actorid==null");
            return "FAILED_TO_FIND_NURSE";
        }

        return actorID;
    }


    @PostMapping(path="/changeNurseStatusIdle")
    public @ResponseBody
    String changeNurseStatusToIdle(@QueryParam("actorID") String actorID)
    {
        System.out.println("[NurseAvailabilityController]: Inside changeNurseStatusToIdle() ");

        Status actorStatus = Status.IDLE;

        String result = nurseAvailabilityService.setNurseStatus(actorID,actorStatus);
        System.out.println("[NurseAvailabilityController]: After returning from setActorStatus()");

        if(result.equals("FAILED_TO_CHANGE_STATUS"))
        {
            System.out.println("[NurseAvailabilityController]: Inside if result==FAILED_TO_CHANGE_STATUS");
            return result;
        }

        return actorID;
    }

}
