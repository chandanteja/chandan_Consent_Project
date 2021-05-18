package com.iiitb.consentmanagement2.nurse.Controller;

import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.Beans.Status;
import com.iiitb.consentmanagement2.nurse.Beans.VitalParams;
import com.iiitb.consentmanagement2.nurse.Services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientVitalController {

    ActorService actorService;

    @Autowired
    public PatientVitalController(ActorService actorService)
    {
        this.actorService = actorService;
    }

    @PostMapping(path = "/savevitalparams", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addVitalParameters(@RequestBody final VitalParams vitals)
    {
       System.out.println("[PatientVitalController]: Inside addVitalParameters() ");

       System.out.println("Patient Name is: "+ vitals.getPatientName());
       System.out.println("Patient ID is: "+ vitals.getPatientID());
       System.out.println("Login email is: "+ vitals.getLoginEmail());      // email id of nurse performing operations
       System.out.println("Operation  is: "+ vitals.getOperation());

        LocalTime endTime,loginTime,operationTime,startTime;
        String actorID;
        ROLE actorType;
        Status actorStatus;

        operationTime = LocalTime.now();
        endTime = actorService.getActorEndTime(vitals.getLoginEmail());
        loginTime = actorService.getActorLoginTime(vitals.getLoginEmail());
        startTime = actorService.getActorStartTime(vitals.getLoginEmail());
        actorStatus = actorService.getActorStatus(vitals.getLoginEmail());

        System.out.println("Came here- time is: "+ operationTime);
        System.out.println("Came here- endtime is: "+ endTime);
        System.out.println("Came here- logintime is: "+ loginTime);
        System.out.println("Came here- starttime is: "+ startTime);

            if( !(operationTime.isAfter(loginTime) && operationTime.isBefore(endTime) && loginTime.isAfter(startTime)) && !actorStatus.equals(Status.BUSY) )
        {
            System.out.println("Sorry! You can't perform the operation at this time. Your login time is greater than endtime");

            return "OUT_OF_OFFICE_HOURS";

        }

        System.out.println("Came here---------1");
        actorType = actorService.getActorRole(vitals.getLoginEmail());
        actorID = actorService.getActorID(vitals.getLoginEmail());



        System.out.println("After otp validation is done. Now calling rest template");

        RestTemplate restTemplate    = new RestTemplate();
        String uri = UriComponentsBuilder.fromUriString("http://localhost:9092/api/addpatientvital/").queryParam("actorID",actorID).queryParam("actorRole",actorType).build().toString();

        System.out.println("Before calling rest controller");
        ResponseEntity<String> responseEntity =  restTemplate.postForEntity(uri,vitals,String.class);

        System.out.println("Response is: "+ responseEntity.getBody().toString());

        if(!responseEntity.getBody().toString().equals("SUCCESS"))
        {
            System.out.println("Failed to save data by consent management micro service");
            return responseEntity.getBody().toString();
        }


        return responseEntity.getBody().toString();     // SUCCESS will be returned if everything is successful.

    }
}
