package com.iiitb.ConsentManagement.ConsentManagement.Controller;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.DoctorAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorAssignmentController {

    ConsentService consentService;
    DoctorAssignmentService doctorAssignmentService;

    @Autowired
    public DoctorAssignmentController(ConsentService consentService,DoctorAssignmentService doctorAssignmentService)
    {
        this.consentService = consentService;
        this.doctorAssignmentService = doctorAssignmentService;
    }

    @PostMapping(path="/doctorassignment")
    public @ResponseBody
    String doctorAssignment(@QueryParam("nurseEmail") String nurseEmail, @QueryParam("patientID") String patientID)
    {
        System.out.println("Inside DoctorAssignmentController-doctorAssignment()");
        System.out.println("Nurse email is: "+ nurseEmail);
        System.out.println("Patient ID is: "+patientID);

        final ConsentType CONSENT_FOR_OPERATION = ConsentType.CREATE;
        final ActivityType ACTIVITY_TYPE = ActivityType.DOCTOR_CHECKUP;
        RestTemplate restTemplate = new RestTemplate();

        String uri = UriComponentsBuilder.fromUriString("http://localhost:9095/api/findavailabledoctors/").build().toString();
        System.out.println("Before sending postforentity");
        ResponseEntity<String> tempResponse = restTemplate.postForEntity(uri,null,String.class);
        System.out.println("Response is: "+tempResponse.getBody().toString());

        if(tempResponse.getBody().toString().equals("FAILED_TO_FIND_DOCTOR"))
        {
            System.out.println("Failed to find doctor. Doctor may not be available");
            return tempResponse.getBody().toString();
        }

        String doctorID = tempResponse.getBody().toString();

        // Start time shuld be null and end time shuld be null
        Consent doctorConsent = consentService.getConsentNotStarted(patientID,ACTIVITY_TYPE,CONSENT_FOR_OPERATION);
        String doctorActivityResult = doctorAssignmentService.doctorActivity(patientID,doctorConsent,doctorID);

        System.out.println("============== After coming from doctor Activity----------------");

        if(doctorActivityResult.matches("^FAILED.*"))
        {
            System.out.println("Failed to create doctor activity ");
            return "FAILED_TO_CREATE_DOCTOR_ACTIVITY";
        }


        return tempResponse.getBody().toString();
    }


}
