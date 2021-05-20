package com.iiitb.consentmanagement.doctormicroservice.Controllers;


import com.iiitb.consentmanagement.doctormicroservice.Beans.ROLE;
import com.iiitb.consentmanagement.doctormicroservice.Beans.Status;
import com.iiitb.consentmanagement.doctormicroservice.Services.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorAvailabilityController {

    DoctorAvailabilityService doctorAvailabilityService;

    @Autowired
    public DoctorAvailabilityController(DoctorAvailabilityService doctorAvailabilityService)
    {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    @PostMapping(path="/findavailabledoctors")
    public @ResponseBody
    String findAvailableDoctors()
    {
        System.out.println("[DoctorAvailabilityController]: Inside findAvailableNurse() ");

        Status actorStatus = Status.IDLE;
        ROLE actorRole = ROLE.ROLE_DOCTOR;
        // Also check if nurse is logged in then only use that nurse for assignment
        String actorID = doctorAvailabilityService.getAvailableDoctor(actorStatus,actorRole);
        System.out.println("[DoctorAvailabilityController]: After returning from getAvailableDoctor");

        if(actorID == null)
        {
            System.out.println("[DoctorAvailabilityController]: Inside if actorid==null");
            return "FAILED_TO_FIND_DOCTOR";
        }

        return actorID;
    }

    @PostMapping(path="/changeDoctorStatus")
    public @ResponseBody
    String changeDoctorStatus(@QueryParam("doctorID") String doctorID)
    {
        System.out.println("Inside DoctorAvailabilityCOntroller - changeDoctorStatus");
        System.out.println("Doctor ID is:" + doctorID);
        String statusResult = doctorAvailabilityService.changeDoctorAvailableStatus(doctorID);
        if(statusResult.matches("^FAILED.*"))
        {
            System.out.println("Failed to change status");
            return "FAILED_TO_CHANGE_STATUS";
        }

        return "SUCCESS";
    }

}
