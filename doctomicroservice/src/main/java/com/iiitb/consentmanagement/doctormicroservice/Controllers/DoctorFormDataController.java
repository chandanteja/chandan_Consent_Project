package com.iiitb.consentmanagement.doctormicroservice.Controllers;

import com.iiitb.consentmanagement.doctormicroservice.Beans.ROLE;
import com.iiitb.consentmanagement.doctormicroservice.HelperClasses.DoctorFormDetails;
import com.iiitb.consentmanagement.doctormicroservice.Services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorFormDataController {

    ActorService actorService;

    @Autowired
    public DoctorFormDataController(ActorService actorService)
    {
        this.actorService = actorService;
    }

    @PostMapping(path = "/doctorFormData", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String doctorFormDetails(@RequestBody DoctorFormDetails doctorFormDetails) {

        System.out.println("[DoctorFormDataController]: Inside doctorFormDetails()");
        System.out.println("Patient Name is: "+doctorFormDetails.getPatientName());
        System.out.println("Patient ID is: "+doctorFormDetails.getPatientID());
        System.out.println("Login Email is: "+doctorFormDetails.getLoginEmail());

        String doctorEmail = doctorFormDetails.getLoginEmail();
        String patientID = doctorFormDetails.getPatientID();

        String doctorID = actorService.getActorID(doctorFormDetails.getLoginEmail());
        ROLE actorRole = actorService.getActorRole(doctorFormDetails.getLoginEmail());

        String uri = UriComponentsBuilder.fromUriString("http://localhost:9092/api/doctorformdetails/").queryParam("doctorID",doctorID).queryParam("actorRole",actorRole).build().toString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(uri,doctorFormDetails,String.class);

        return "SUCCESS";
    }

}


